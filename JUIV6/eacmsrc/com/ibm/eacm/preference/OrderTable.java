//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;


import com.ibm.eacm.table.BaseTable;
import COM.ibm.eannounce.objects.*;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

/**
 * This is the table used to set column order preferences for entitygroups
 * @author Wendy Stimpson
 */
// $Log: OrderTable.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class OrderTable extends BaseTable implements AncestorListener {
	private static final long serialVersionUID = 1L;
	private OrderTableModel otm = new OrderTableModel();
    private int iMoveRow = -1;
    private int iX = -1;
    private int iY = -1;
    private boolean bDragged = false;
    private boolean bMoving = false;
    private OrderMouseListener oml = null;
    private OrderKeyListener okl = null;
    private com.ibm.eacm.editor.PickListCellEditor pickListEd =null;

    /**
     * orderTable
     */
    public OrderTable() {
		super.setModel(otm);

        init();
        pickListEd = new com.ibm.eacm.editor.PickListCellEditor();
    }

    protected void init() {
    	super.init();

    	setSurrendersFocusOnKeystroke(true); // for accessibility, let user edit using keystrokes

        setColumnSelectionAllowed(true);

        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        createOrderListener();
        addAncestorListener(this);

    	// dont allow sort
    	setAutoCreateRowSorter(false);
    	setRowSorter(null);
    }
    /**
     * get the accessibility resource key
     * @return
     */
    protected String getAccessibilityKey() {
    	return "accessible.ordTable";
    }
    /**
     * release memory
     */
    public void dereference() {
        resetMove();
        removeOrderListener();
        otm.dereference();
        otm = null;

        pickListEd.dereference();
        pickListEd = null;
        removeAncestorListener(this);
        super.dereference();

        oml = null;
        okl = null;
    }

    /**
     * @see javax.swing.JTable#getCellEditor(int, int)
     */
    public TableCellEditor getCellEditor(int r, int c) {
        TableCellEditor tce = null;

        Object o = otm.get(convertRowIndexToModel(r), convertColumnIndexToModel(c));
        if (o instanceof SimplePicklistAttribute) {
            SimplePicklistAttribute spa = (SimplePicklistAttribute) o;
            if (spa.isMultiple()) {
    			Logger.getLogger(APP_PKG_NAME).log(Level.WARNING,"Multiple SimplePicklist Attributes currently not supported");
            } else {
            	pickListEd.setPickListAttribute(spa);
            	tce =pickListEd;
            }
        }

        return tce;
    }

    /**
     * updateOrderModel - called on event thread when an entity group is selected
     * @param ctm
     */
    protected void updateOrderModel(MetaColumnOrderTable ctm) {
        otm.updateModel(ctm);
        if (ctm != null) {
            createDefaultColumnsFromModel();
            resizeCells();
            if (getRowCount() > 0) {
                setRowSelectionInterval(0, 0);
                requestFocus();
            }
            if (getColumnCount() > 0) {
                setColumnSelectionInterval(0, 0);
            }
        }
    }

    /**
     * commit - called on worker thread
     */
    protected void commit(Component c) {
        otm.commit(c);
    }

    /**
     * setUpdateDefaults - called on worker thread
     * @param b
     */
    protected void setUpdateDefaults(boolean b) {
        otm.setUpdateDefaults(b);
    }

    /**
     * rollbackDefault - called on worker thread
     */
    protected boolean rollbackDefault(Component c) {
        return otm.rollbackDefault(c);
    }

    /**
     * rollback - done on worker thread
     */
    protected boolean rollback(Component c) {
        return otm.rollback(c);
    }
    /**
     * done on dispatch thread
     */
    protected void modelChangedUpdate(){
    	otm.modelChangedUpdate();
    }
    /*
     Overridden Methods
    */
    /**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     */
    public void changeSelection(int r, int c, boolean toggle, boolean extend) {
        if (bMoving) {
        	extend = false;
        }

        super.changeSelection(r, c, toggle, extend);
    }

    private void moveRow(int increment) {
        int rows = getRowCount();
        int oldPos = getSelectedRow();
        if (oldPos >= 0 && oldPos < rows) {
            int newPos = oldPos + increment;
            if (newPos >= 0 && newPos < rows) {
                moveRow(oldPos, newPos);
                scrollToRow(newPos);
            }
        }
        bMoving = false;
    }

    private void moveRow(int row, int newPos) {
        if(newPos >= 0) {
            otm.moveRow(row, newPos);
        }

    }

    private void resetMove() {
        iMoveRow = -1;
        iX = -1;
        iY = -1;
        bDragged = false;
        bMoving = false;
    }

    private void paintDrag(MouseEvent me) {
        paintTrack();
        iX = me.getX();
        iY = me.getY();
        BasicGraphicsUtils.drawDashedRect(getGraphics(), iX, iY, 30, getRowHeight());
    }

    private void paintTrack() {
        if (iX >= 0 && iY >= 0) {
            paintImmediately(iX, iY, 30, getRowHeight());
            iX = -1;
            iY = -1;
        }
    }

    /**
     * @see java.awt.Component#repaint()
     */
    public void repaint() {
        if (!bDragged) {
            super.repaint();
        }
    }
    /**
     * selectCell
     * @param pt
     */
    private void selectCell(Point pt) {
        int row = rowAtPoint(pt);
        int col = columnAtPoint(pt);
        if (row >= 0 && row < getRowCount()) {
            setRowSelectionInterval(row, row);
        }
        if (col >= 0 && col < getColumnCount()) {
            setColumnSelectionInterval(col, col);
        }
    }

    /**
     * createOrderListener
     */
    private void createOrderListener() {
    	oml = new OrderMouseListener();
    	okl = new OrderKeyListener();

        addMouseListener(oml);
        addMouseMotionListener(oml);
        addKeyListener(okl);
    }

    /**
     * removeOrderListener
     */
    private void removeOrderListener() {
        if (oml != null) {
            removeMouseListener(oml);
            removeMouseMotionListener(oml);
        }
        if (okl != null) {
            removeKeyListener(okl);
        }
    }

    private class OrderKeyListener extends KeyAdapter {
        /**
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */
        public void keyPressed(KeyEvent ke) {
            if (ke.isControlDown()) {
                int keyCode = ke.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    bMoving = true;
                    moveRow(-1);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    bMoving = true;
                    moveRow(1);
                }
            }
        }
    }

    private class OrderMouseListener extends MouseInputAdapter {
        /**
         * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
         */
        public void mouseDragged(MouseEvent me) {
            Point pt = me.getPoint();
            if(bDragged) {
                int row = rowAtPoint(pt);
                int col = columnAtPoint(pt);
                bMoving = true;
                if (isEnabled()) {
                    paintDrag(me);
                } else {
                    paintTrack();
                }
                if (isValidCell(row, col)) {
                    scrollRectToVisible(getCellRect(row, col, false));
                }
            }
        }

        /**
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         */
        public void mousePressed(MouseEvent me) {
            Point pt = me.getPoint();
            selectCell(pt);
            if (isEnabled()) {
            	iMoveRow = rowAtPoint(pt);
            	bDragged = true;
            }
        }

        /**
         * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         */
        public void mouseReleased(MouseEvent me) {
        	Point pt = me.getPoint();
        	if (isEnabled()) {
        		int iRowPosition = rowAtPoint(pt);
        		if (iMoveRow!=-1 && // single and double clicks can send 2 mouseReleased events
        				iMoveRow != iRowPosition) {
        			moveRow(iMoveRow, iRowPosition);
        		}
        		resetMove();
        		repaint();
        	}
        }
    }

    /***
     * used with accessibility
     */
    protected String getContext() {
		return "";
	}

    // used when preference panel is changed
	public void ancestorAdded(AncestorEvent event) {}
	public void ancestorMoved(AncestorEvent event) {}
	public void ancestorRemoved(AncestorEvent event) {
		editingStopped(null); // clear any editors
	}
	protected boolean hasChanges(){
	  	boolean chgs = false;
    	if (otm != null) {
			chgs = otm.hasChanges();
		}
    	return chgs;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Sortable#getTableTitle()
	 */
	public String getTableTitle() {
		return "Column Order";
	}
}
