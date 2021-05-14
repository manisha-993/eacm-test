//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;


import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.rend.LockedBooleanRend;
import com.ibm.eacm.rend.RowHeaderRenderer;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

/******************************************************************************
* This is used for the table in the pdg info dialog -  untested
* @author Wendy Stimpson
*/
// $Log: PDGInfoTable.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
public class PDGInfoTable extends RSTTable implements KeyListener {
	private static final long serialVersionUID = 1L;

    /**
     * textCol
     */
    private final int textCol = 1;
    private LockedBooleanRend bRenderer = new LockedBooleanRend();

	private JList rowheadlist = null;

	/**
	 * allow derived classes to provide a different rsttablemodel
	 * @return
	 */
	protected RSTTableModel createTableModel(){ 
		return new PDGInfoTableModel(this);
	}

	public JList getRowHeader(){
		if(rowheadlist==null){
			FontMetrics fm = getFontMetrics(getFont());
			RowHeaderRenderer rrend = new RowHeaderRenderer(this);
			rowheadlist = new JList(((PDGInfoTableModel)getModel()).getRowHeaders());
			int w = widthOf(fm,getHeader()) + 15;
			for (int i=0;i<((PDGInfoTableModel)getModel()).getRowHeaders().size();++i) {
				w = Math.max(w,widthOf(fm,((PDGInfoTableModel)getModel()).getRowHeaders().elementAt(i)));
			}
			rrend.setMinWidth(w);
			rowheadlist.setCellRenderer(rrend);
			//white below list if this isnt done 
			rowheadlist.setBackground(getTableHeader().getBackground());
		}
		return rowheadlist;
	}
	private int widthOf(FontMetrics fm, Object o) {
		if (o != null) {
			return fm.stringWidth(o.toString()) + 7;
		}
		return 27;
	}
	public JLabel getHeader(){
		if (rowHeaderLabel==null){
			rowHeaderLabel = new RowHeaderLabel(getTableTitle());
		}
		return rowHeaderLabel;
	}
    /**
     * @param rst
     * @param p
     */
    public PDGInfoTable(RowSelectableTable rst, Profile p) {
        super(rst,p);

        init();
    }


    /**
     */
    public void dereference() {
        removeKeyListener(this);
        
        bRenderer.dereference();
        bRenderer=null;
        if(rowheadlist!=null){
        	rowheadlist.removeAll();
        	rowheadlist = null;
        }
        super.dereference();
    }

    /**
     * @see javax.swing.JTable#getCellRenderer(int, int)
     */
    public TableCellRenderer getCellRenderer(int r, int c) {
        boolean isLocked = isCellEditable(r, c);
        if (!isLocked && getColumnClass(c).getName().equals("java.lang.Boolean")) {
            return bRenderer;
        }

        return getDefaultRenderer(getColumnClass(c));
    }

    /**
     */
    protected void init() {
    	super.init();

        setColumnSelectionAllowed(true);

        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addKeyListener(this);
     
        setOpaque(false);
        
        resizeCells();
    }
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.pdgTable";
	}

    /**
     */
    private void selectString(char c) {
        String s = String.valueOf(c).toLowerCase();
        int row = getSelectedRow() + 1;
        if (row >= getRowCount()) {
            row = 0;
        }
        int r = findString(s, row);
        if (r >= 0) {
            setRowSelectionInterval(r, r);
            setColumnSelectionInterval(textCol, textCol);
            scrollRectToVisible(getCellRect(r, 0, true));
            repaint();
        }
    }

    private int findString(String s, int startRow) {
        String curStr = ((String) getValueAt(startRow, textCol)).toLowerCase();
        if (curStr.startsWith(s)) {
            return startRow;
        }

        int compares = curStr.compareToIgnoreCase(s);
        if (compares > 0) {
            for (int r = 0; r < startRow; ++r) {
                if (doesStringExist(r, s)) {
                    return r;
                }
            }
        } else if (compares < 0) {
            for (int r = startRow + 1; r < getRowCount(); ++r) {
                if (doesStringExist(r, s)) {
                    return r;
                }
            }
        }
        return -1;
    }

    private boolean doesStringExist(int r, String s) {
        String curStr = (String) getValueAt(r, textCol);

        return (curStr.compareToIgnoreCase(s) >= 0);
    }


    /**
     */
    public void resizeCells() {
        if (!BehaviorPref.canSize(getRowCount())) { //auto_sort/size
            return; //auto_sort/size
        } //auto_sort/size

        FontMetrics fm = getFontMetrics(getFont());
        for (int c = 0; c < getColumnCount(); ++c) {
        	int Width = getWidth(fm, getColumnName(c));
        	for (int r = 0; r < getRowCount(); ++r) {
        		Object o = getValueAt(r, c); 
        		if (o instanceof Boolean) {
        			Width = Math.max(Width, 40);
        		} else {
        			Width = Math.max(Width, getWidth(fm, o));
        		}
        	}

        	TableColumn tc = getColumnModel().getColumn(c);
            tc.setWidth(Width);
            tc.setPreferredWidth(Width);
            tc.setMinWidth(Width);
            tc.setMaxWidth(Width);
        }
    }

    private int getWidth(FontMetrics fm, Object o) {
        int w = 10;
        if (o !=null) {
        	w += fm.stringWidth(o.toString());
        }
        return w;
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent kea) {
        if (kea.getKeyCode() == KeyEvent.VK_ESCAPE) {
        } else if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
        } else if (kea.getKeyCode() == KeyEvent.VK_SPACE) {
            repaint();
            kea.consume();
            return;
        }
        int code = kea.getKeyCode();
        if (code != KeyEvent.VK_UP && code != KeyEvent.VK_DOWN && code != KeyEvent.VK_RIGHT && code != KeyEvent.VK_LEFT) {
            selectString(kea.getKeyChar());
            kea.consume();
        }
    }

    public void keyPressed(KeyEvent kea) {  }
    public void keyTyped(KeyEvent kea) { }

	private class PDGInfoTableModel extends RSTTableModel {
		private static final long serialVersionUID = 1L;

		public PDGInfoTableModel(RSTTable table) {
			super(table);
		}
	    public Class<?> getColumnClass(int c) {
	        Object o = getRSTable().getMatrixValue(0, c);
	        if (o != null) {
	            return o.getClass();
	        }
	        return String.class;
	    }
	    /**
	     * @see javax.swing.table.TableModel#isCellEditable(int, int)
	     */
	    public boolean isCellEditable(int r, int c) {
	        return getRSTable().isMatrixEditable(r, c);
	    }
	    Vector<String> getRowHeaders() {
	        Vector<String> v = new Vector<String>();
	        for (int i = 0; i < getRowCount(); ++i) {
	            v.add(getRSTable().getRowHeaderMatrix(i));
	        }
	        return v;
	    }
	    /**
	     * @see javax.swing.table.TableModel#getValueAt(int, int)
	     */
	    public Object getValueAt(int r, int c) {
	        return getRSTable().getMatrixValue(r, c);
	    }
	    public Object getValueAt(int r, int c, boolean b) {
	        return getRSTable().getMatrixValue(r, c);
	    }
	}
}
