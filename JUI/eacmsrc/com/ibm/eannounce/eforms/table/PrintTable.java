/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/14
 * @author Anthony C. Liberto
 *
 * $Log: PrintTable.java,v $
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/04 18:16:49  tony
 * JTest Formatter Fourth Pass
 *
 * Revision 1.4  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.7  2003/09/04 14:56:11  tony
 * 52029
 *
 * Revision 1.6  2003/07/16 21:08:28  tony
 * updated printing logic to improve
 * functionality.
 *
 * Revision 1.5  2003/06/25 23:45:45  tony
 * updated printTableBorder
 *
 * Revision 1.4  2003/04/15 20:17:48  tony
 * adjusted rendering.
 *
 * Revision 1.3  2003/04/15 17:30:53  tony
 * added border to printTable.
 *
 * Revision 1.2  2003/04/15 16:45:44  tony
 * updated rendering to allow for multiple line printing
 *
 * Revision 1.1  2003/04/14 21:39:33  tony
 * added printTable
 *
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import com.ibm.eannounce.erend.*;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class PrintTable extends JTable implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private PrintTableModel ptm = null;
	private ERowRend rowRend = new ERowRend();
	private LongRend rend = new LongRend();

	/**
     * printTable
     * @param _model
     * @author Anthony C. Liberto
     */
    public PrintTable(RSTableModel _model) {
		super();
		ptm = new PrintTableModel(_model);
		setModel(ptm);
		init();
		return;
	}

	/**
     * @see javax.swing.JTable#getCellRect(int, int, boolean)
     * @author Anthony C. Liberto
     */
    public Rectangle getCellRect(int _r, int _c, boolean _space) {
		Rectangle cellFrame = new Rectangle();
		cellFrame.height = getRowHeight(_r);
		cellFrame.width = getColumnModel().getColumn(_c).getWidth();
		for (int i=0;i<_r;i++) {
			cellFrame.y += getRowHeight(i);
		}
		for (int i=0;i<_c;i++) {
			cellFrame.x += getColumnModel().getColumn(i).getWidth();
		}
		if (_space) {
			Dimension spacing = getIntercellSpacing();
			cellFrame.setBounds(cellFrame.x + spacing.width/2, cellFrame.y + spacing.height/2, cellFrame.width - spacing.width, cellFrame.height - spacing.height);
		}
		return cellFrame;
	}

	/**
     * @see javax.swing.JTable#getCellRenderer(int, int)
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
		if (_r == 0) {
			return rowRend;
		}
		return rend;
    }

	/**
     * init
     * @author Anthony C. Liberto
     */
    public void init() {
		setRowMargin(0);
		setBorder(UIManager.getBorder("eannounce.printBorder"));
		setColumnSelectionAllowed(false);
		setRowSelectionAllowed(true);
		setAutoResizeMode(AUTO_RESIZE_OFF);
		getTableHeader().setReorderingAllowed(false);
		return;
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		if (ptm != null) {
			ptm.dereference();
			ptm = null;
		}
		removeAll();
		removeNotify();
		return;
	}


	/**
     * @see javax.swing.JTable#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
		if (_r == 0) {
			return ptm.getColumnName(_c);
		}
		return ptm.getValueAt(ptm.getRowIndex(ptm.getRowKey(_r-1)),convertColumnIndexToModel(_c));
	}

	private Dimension resizeCells() {
		int cc = getColumnCount();
		int rr = getRowCount();
		int[] height = new int[rr];
		int[] width = new int[cc];
		int h = -1;
        int w = -1;
        for (int r=0;r<rr;++r) {
			for (int c=0;c<cc;++c) {
				Object o = getValueAt(r,c);
				String str = (o == null) ? "" : o.toString();
				height[r] = Math.max(height[r], getMultiLineHeight(str));
				width[c] = Math.max(width[c], getMultiLineWidth(str));
			}
		}
		w = processWidth(width);
		h = processHeight(height);
		return new Dimension(w,h);
	}

	/**
     * getMultiLineHeight
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    public int getMultiLineHeight(String _str) {
        String str = Routines.splitString(_str,80);
		return getHeight(str);
	}

	/**
     * getMultiLineWidth
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    protected int getMultiLineWidth(String _str) {
		String str = Routines.splitString(_str,80);
		return getWidth(str);
	}

	/**
     * processHeight
     * @param _height
     * @return
     * @author Anthony C. Liberto
     */
    protected int processHeight(int[] _height) {
		int rr = _height.length;
		int out = 0;
		FontMetrics fm = getFontMetrics(getFont());
		int h = fm.getHeight();
		for (int r=0;r<rr;++r) {
			int height = (Math.max(1,_height[r])) * h;
			setRowHeight(r, height);
			out += height;
		}
		return out;
	}

	/**
     * getHeight
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    public int getHeight(String _str) {
		return Routines.getCharacterCount(_str,RETURN);
	}

	private int getWidth(String[] _str) {
		FontMetrics fm = getFontMetrics(getFont());
		int ii = _str.length;
		int w = 0;
		for (int i=0;i<ii;++i) {
			w = Math.max(w,fm.stringWidth(_str[i]));
		}
		return w;
	}

	/**
     * getWidth
     * @param _str
     * @return
     * @author Anthony C. Liberto
     */
    protected int getWidth(String _str) {
		FontMetrics fm = null;
        if (_str.indexOf('\n') >= 0) {
			return getWidth(Routines.getStringArray(_str,RETURN));
		}
		fm = getFontMetrics(getFont());
		return fm.stringWidth(Routines.truncate(_str,164));
	}

	/**
     * processWidth
     * @param _width
     * @return
     * @author Anthony C. Liberto
     */
    protected int processWidth(int[] _width) {
		int out = 0;
		int cc = _width.length;
		for (int c=0;c<cc;++c) {
			TableColumn tc = getColumnModel().getColumn(c);
			int w = _width[c];
			w += 10;
			out += w;
			tc.setWidth(w);
			tc.setPreferredWidth(w);
		}
		return out;
	}

	/**
     * getUIPrefKey
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() { return null;}
	/**
     * moveToError
     * @param bre
     * @author Anthony C. Liberto
     */
    public void moveToError(EANBusinessRuleException bre) {}
	/**
     * actionPerformed
     * @param _ae
     * @author Anthony C. Liberto
     */
    public void actionPerformed(ActionEvent _ae) {}
	/**
     * replaceString
     * @param _s1
     * @param _s2
     * @param _i1
     * @param _i2
     * @return
     * @author Anthony C. Liberto
     */
    public boolean replaceString(String _s1,String _s2,int _i1,int _i2) {return false;}

	private class PrintTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private RSTableModel mod = null;
		private EventListenerList listenerList = new EventListenerList();

		/**
         * printTableModel
         * @param _mod
         * @author Anthony C. Liberto
         */
        private PrintTableModel(RSTableModel _mod) {
			super();
			mod = _mod;
			modelChanged(TableModelEvent.INSERT);
			return;
		}

		/**
         * @see javax.swing.table.TableModel#getColumnName(int)
         * @author Anthony C. Liberto
         */
        public String getColumnName(int _i) {
			if (mod != null) {
				return mod.getColumnName(_i);
			}
			return null;
		}

		/**
         * @see javax.swing.table.TableModel#getColumnClass(int)
         * @author Anthony C. Liberto
         */
        public Class getColumnClass(int _i) {
			if (mod != null) {
				return mod.getColumnClass(_i);
			}
			return null;
		}

		/**
         * @see javax.swing.table.TableModel#getColumnCount()
         * @author Anthony C. Liberto
         */
        public int getColumnCount() {
			if (mod != null) {
				return mod.getColumnCount();
			}
			return 0;
		}

		/**
         * @see javax.swing.table.TableModel#getRowCount()
         * @author Anthony C. Liberto
         */
        public int getRowCount() {
			if (mod != null) {
				return mod.getRowCount() + 1;
			}
			return 0;
		}

		/**
         * @see javax.swing.table.TableModel#isCellEditable(int, int)
         * @author Anthony C. Liberto
         */
        public boolean isCellEditable(int _r, int _c) {
			return false;
		}

		/**
         * getRowKey
         * @param _i
         * @return
         * @author Anthony C. Liberto
         */
        public String getRowKey(int _i) {
			return mod.getRowKey(_i);
		}

		/**
         * getRowIndex
         * @param _s
         * @return
         * @author Anthony C. Liberto
         */
        public int getRowIndex(String _s) {
			return mod.getRowIndex(_s);
		}

		/**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         * @author Anthony C. Liberto
         */
        public Object getValueAt(int _r, int _c) {
			Object o = mod.getValueAt(_r, _c, true);
			if (o == null) {
				return "";
			}
			return o;
		}

		/**
         * dereference
         * @author Anthony C. Liberto
         */
        public void dereference() {
			mod = null;
			return;
		}

		/**
         * modelChanged
         * @param _type
         * @author Anthony C. Liberto
         */
        public void modelChanged(int _type) {
			tableModelChanged(0,getRowCount()-1,TableModelEvent.ALL_COLUMNS, _type);
			return;
		}

		/**
         * tableModelChanged
         * @param startRow
         * @param endRow
         * @param col
         * @param type
         * @author Anthony C. Liberto
         */
        public void tableModelChanged(int startRow, int endRow, int col, int type) {
			TableModelEvent event = null;
            if (startRow < 0) {
				startRow = 0;
			}
			if (endRow < 0) {
				endRow = getRowCount()-1;
			}
			event = new TableModelEvent(this, startRow, endRow, col, type);
			fireTableChanged(event);
			return;
		}

		/**
         * @see javax.swing.table.AbstractTableModel#fireTableChanged(javax.swing.event.TableModelEvent)
         * @author Anthony C. Liberto
         */
        public void fireTableChanged(TableModelEvent e) {
			Object[] listeners = listenerList.getListenerList();
			for (int i = listeners.length-2; i>=0; i-=2) {
				if (listeners[i]==TableModelListener.class) {
					((TableModelListener)listeners[i+1]).tableChanged(e);
				}
			}
			return;
		}

		/**
         * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
         * @author Anthony C. Liberto
         */
        public void addTableModelListener(TableModelListener l) {
			listenerList.add(TableModelListener.class, l);
			return;
		}

		/**
         * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
         * @author Anthony C. Liberto
         */
        public void removeTableModelListener(TableModelListener l) {
			listenerList.remove(TableModelListener.class, l);
			return;
		}

		/**
         * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
         * @author Anthony C. Liberto
         */
        public void setValueAt(Object _o, int _r, int _c) {}
		/**
         * @see javax.swing.table.AbstractTableModel#findColumn(java.lang.String)
         * @author Anthony C. Liberto
         */
        public int findColumn(String _name) {return -1;}
		/**
         * @see javax.swing.table.AbstractTableModel#fireTableCellUpdated(int, int)
         * @author Anthony C. Liberto
         */
        public void fireTableCellUpdated(int _r, int _c) {}
		/**
         * @see javax.swing.table.AbstractTableModel#fireTableDataChanged()
         * @author Anthony C. Liberto
         */
        public void fireTableDataChanged() {}
		/**
         * @see javax.swing.table.AbstractTableModel#fireTableRowsDeleted(int, int)
         * @author Anthony C. Liberto
         */
        public void fireTableRowsDeleted(int _r1, int _r2) {}
		/**
         * @see javax.swing.table.AbstractTableModel#fireTableRowsInserted(int, int)
         * @author Anthony C. Liberto
         */
        public void fireTableRowsInserted(int _r1, int _r2) {}
		/**
         * @see javax.swing.table.AbstractTableModel#fireTableRowsUpdated(int, int)
         * @author Anthony C. Liberto
         */
        public void fireTableRowsUpdated(int _r1, int _r2) {}
		/**
         * @see javax.swing.table.AbstractTableModel#fireTableStructureChanged()
         * @author Anthony C. Liberto
         */
        public void fireTableStructureChanged() {}
	}

/*
 52029
 */
	/**
     * updateFont
     * @param _f
     * @author Anthony C. Liberto
     */
    public void updateFont(Font _f) {
        Dimension d = resizeCells();
		setFont(_f);
		setSize(d);
		setPreferredSize(d);
		resizeAndRepaint();
		return;
	}
}
