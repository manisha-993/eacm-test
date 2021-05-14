/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: OrderTable.java,v $
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.6  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.5  2005/02/09 18:55:24  tony
 * Scout Accessibility
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
 * Revision 1.22  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.21  2003/12/01 17:42:40  tony
 * accessibility
 *
 * Revision 1.20  2003/11/11 00:42:22  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.19  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.18  2003/05/15 15:32:41  tony
 * improved functionality on the table.
 *
 * Revision 1.17  2003/05/06 00:07:19  tony
 * 50468
 *
 * Revision 1.16  2003/04/25 20:37:32  tony
 * adjusted orderTable capabilities to allow for
 * update of column order before login is complete.
 *
 * Revision 1.15  2003/04/25 19:20:07  tony
 * added border to tables
 *
 * Revision 1.14  2003/04/15 17:31:34  tony
 * changed to e-announce.focusborder
 *
 * Revision 1.13  2003/04/11 20:02:31  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.table;
import com.ibm.eannounce.eforms.editor.simple.*;
import com.ibm.eannounce.erend.SingleRenderer;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class OrderTable extends GTable {
	private static final long serialVersionUID = 1L;
	private static OrderTableModel tm = new OrderTableModel();
    private int iMoveRow = -1;
    private int iX = -1;
    private int iY = -1;
    private boolean bDragged = false;
    private boolean bMoving = false;
    private OrderMouseListener oml = null;
    private OrderKeyListener okl = null;

    private SimpleListEditor list = new SimpleListEditor();

    /**
     * orderTable
     * @author Anthony C. Liberto
     */
    public OrderTable() {
        super(tm);
        init();
        return;
    }

    private void init() {
        setRowMargin(0);
        initAccessibility("accessible.ordTable");
        setBorder(UIManager.getBorder("eannounce.focusborder"));
        setColumnSelectionAllowed(true);
        setRowSelectionAllowed(true);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(true);
        setDefaultRenderer(Object.class, new SingleRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setBorder(UIManager.getBorder("eannounce.focusBorder"));
        createOrderListener();
        return;
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        resizeCells();
        return;
    }

    /**
     * getTableWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableWidth() {
        TableColumnModel tcm = getColumnModel();
        return tcm.getTotalColumnWidth();
    }

    /**
     * getTableHeight
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableHeight() {
        int height = getRowHeight();
        int rows = getRowCount();
        return (height * rows);
    }

    /**
     * updateOrderModel
     * @param _ctm
     * @author Anthony C. Liberto
     */
    public void updateOrderModel(MetaColumnOrderTable _ctm) {
        tm.updateModel(null, TableModelEvent.DELETE);
        if (_ctm != null) {
            tm.updateModel(_ctm, TableModelEvent.INSERT);
            createDefaultColumnsFromModel();
            resizeCells();
            if (hasRows()) {
                setRowSelectionInterval(0, 0);
                requestFocus();
            }
            if (hasColumns()) {
                setColumnSelectionInterval(0, 0);
            }
        }
        return;
    }

    /**
     * setUpdateDefaults
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUpdateDefaults(boolean _b) {
        tm.setUpdateDefaults(_b);
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#dereference()
     * @author Anthony C. Liberto
     */
    public void dereference() {
        resetMove();
        removeOrderListener();
        tm.dereference();
        tm = null;
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#setFound(int, int)
     * @author Anthony C. Liberto
     */
    protected void setFound(int row, int c) {
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isFound(int, int)
     * @author Anthony C. Liberto
     */
    protected boolean isFound(int row, int col) {
        return false;
    }
    /**
     * isFound
     * @author Anthony C. Liberto
     * @return boolean
     * @param caseSensitive
     * @param find
     * @param str
     */
    protected boolean isFound(String str, String find, boolean caseSensitive) {
        return false;
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isRowFiltered(int)
     * @author Anthony C. Liberto
     */
    protected boolean isRowFiltered(int r) {
        return false;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getString(int, int, boolean)
     * @author Anthony C. Liberto
     */
    protected String getString(int _r, int _c, boolean _forReplace) {
        if (isValidCell(_r, _c)) {
            Object o = getValueAt(_r, _c);
            if (o != null) {
                return o.toString();
            }
        }
        return "";
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#processWidth(int[])
     * @author Anthony C. Liberto
     */
    protected int processWidth(int[] _width) {
        int out = 0;
        int cc = _width.length;
        for (int c = 0; c < cc; ++c) {
            TableColumn tc = getColumnModel().getColumn(c);
            int w = Math.max(getHeaderWidth(tc), _width[c]) + 10;
            out += w;
            tc.setWidth(w);
            tc.setPreferredWidth(w);
        }
        return out;
    }

    /**
     * getHeaderWidth
     * @param _tc
     * @return
     * @author Anthony C. Liberto
     */
    protected int getHeaderWidth(TableColumn _tc) {
        Object o = _tc.getHeaderValue();
        String str = "";
        if (o != null) {
            str = o.toString();
        }
        return getWidth(str);
    }

    /*
     editing
    */
    /**
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     * @author Anthony C. Liberto
     */
    public boolean editCellAt(int _r, int _c, EventObject _e) {
        TableCellEditor tce = null;
        editingCanStop();
        if (!isCellEditable(_r, _c)) {
            return false;
        }
        if (_e != null && _e instanceof MouseEvent) {
            if (((MouseEvent) _e).getClickCount() != 2) {
                return false;
            }
        }
        tce = getCellEditor(_r, _c);
        if (tce != null && tce.isCellEditable(_e)) {
            editorComp = prepareEditor(tce, _r, _c);
            if (editorComp == null) {
                removeEditor();
                return false;
            } else if (editorComp instanceof SimpleEditor) {
                editorComp.setBounds(getCellRect(_r, _c, false));
                add(editorComp);
                editorComp.validate();
                setCellEditor(tce);
                setEditingRow(_r);
                setEditingColumn(_c);
                tce.addCellEditorListener(this);
                repaint();
                return true;
            }
        }
        return false;
    }
    /**
     * getFoundation
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation getFoundation(int _r, int _c) {
        Object o = getEANObject(_r, _c);
        if (o instanceof EANFoundation) {
            return (EANFoundation) o;
        }
        return null;
    }

    /**
     * getEANObject
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public Object getEANObject(int _r, int _c) {
        return tm.get(_r, _c);
    }

    /**
     * @see javax.swing.JTable#getCellEditor(int, int)
     * @author Anthony C. Liberto
     */
    public TableCellEditor getCellEditor(int _r, int _c) {
        TableCellEditor tce = null;
        Object o = getFoundation(_r, _c);
        if (o instanceof SimplePicklistAttribute) {
            SimplePicklistAttribute spa = (SimplePicklistAttribute) o;
            if (spa.isMultiple()) {
                appendLog("Multiple SimplePicklist Attributes currently not supported");
            } else {
                list.setFoundation((EANFoundation) o);
                tce = list;
            }
        }
        return tce;
    }

    /**
     * @see javax.swing.event.CellEditorListener#editingStopped(javax.swing.event.ChangeEvent)
     * @author Anthony C. Liberto
     */
    public void editingStopped(ChangeEvent _ce) {
        TableCellEditor editor = getCellEditor();
        if (editor != null) {
            Object value = editor.getCellEditorValue();
            if (editor instanceof SimpleEditor) {
                setValueAt(value, getEditingRow(), getEditingColumn());
                removeEditor();
            }
        }
        return;
    }

    /**
     * @see javax.swing.JTable#removeEditor()
     * @author Anthony C. Liberto
     */
    public void removeEditor() {
        TableCellEditor edit = getCellEditor();
        Rectangle cellRect = null;
        if (edit != null) {
            edit.removeCellEditorListener(this);
            requestFocus();
            if (editorComp != null) {
                remove(editorComp);
            }
            cellRect = getCellRect(editingRow, editingColumn, false);
            setCellEditor(null);
            setEditingColumn(-1);
            setEditingRow(-1);
            editorComp = null;
            repaint(cellRect);
            checkChanges();
        }
        return;
    }

    /**
     * checkChanges
     * @author Anthony C. Liberto
     */
    public void checkChanges() {
    }

    /**
     * hasChanges
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasChanges() {
        return tm.hasChanges();
    }

    /**
     * commit
     * @author Anthony C. Liberto
     */
    public void commit() {
        tm.commit(this);
        checkChanges();
        return;
    }

    /**
     * rollbackDefault
     * @author Anthony C. Liberto
     */
    public void rollbackDefault() {
        tm.rollbackDefault();
        checkChanges();
        return;
    }

    /**
     * rollback
     * @author Anthony C. Liberto
     */
    public void rollback() {
        tm.rollback();
        checkChanges();
        return;
    }

    /*
     Overridden Methods
    */
    /**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     * @author Anthony C. Liberto
     */
    public void changeSelection(int _r, int _c, boolean _toggle, boolean _extend) {
        if (bMoving) {
            super.changeSelection(_r, _c, _toggle, false);
        } else {
            super.changeSelection(_r, _c, _toggle, _extend);
        }
        return;
    }

    /*
     Moves
    */
    private void moveRow(int _increment) {
        int rows = -1;
        int oldPos = -1;
        if (!isOrderable()) {
            return;
        }
        rows = getRowCount();
        oldPos = getSelectedRow();
        if (oldPos >= 0 && oldPos < rows) {
            int newPos = oldPos + _increment;
            if (newPos >= 0 && newPos < rows) {
                moveRow(oldPos, newPos);
                setRowSelectionInterval(newPos, newPos);
                scrollRectToVisible(getCellRect(newPos, Math.max(0, getSelectedColumn()), false));
            }
        }
        bMoving = false;
        return;
    }

    private void moveRow(int _row, int _newPos) {
        if (isOrderable() && _newPos >= 0) {
            tm.moveRow(_row, _newPos);
            setRowSelectionInterval(_newPos, _newPos);
            checkChanges();
        }
        return;
    }
/*
    private void moveRows(int[] _rows, int _newPos) {
        if (isOrderable() && _newPos >= 0 && _rows != null) {
            tm.moveRows(_rows, _newPos);
            checkChanges();
        }
        return;
    }
*/
    /*
     Support Move Methods
    */
    /**
     * isOrderable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isOrderable() {
        return tm.isOrderable();
    }

    /**
     * setOrderable
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setOrderable(boolean _b) {
        resetMove();
        tm.setOrderable(_b);
        return;
    }

    /**
     * isMoveableColumn
     * @param _convert
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMoveableColumn(boolean _convert) {
        return isMoveableColumn(getSelectedColumn(), _convert);
    }

    /**
     * isMoveableColumn
     * @param _i
     * @param _convert
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMoveableColumn(int _i, boolean _convert) {
        if (_convert) {
            _i = convertColumnIndexToModel(_i);
        }
        return tm.isMoveableColumn(_i);
    }

    private void resetMove() {
        iMoveRow = -1;
        iX = -1;
        iY = -1;
        bDragged = false;
        bMoving = false;
        return;
    }

    /*
     Paint
    */
    private void paintDrag(MouseEvent _me) {
        paintTrack();
        iX = _me.getX();
        iY = _me.getY();
        BasicGraphicsUtils.drawDashedRect(getGraphics(), iX, iY, 30, getRowHeight());
        return;
    }

    private void paintTrack() {
        if (iX >= 0 && iY >= 0) {
            paintImmediately(iX, iY, 30, getRowHeight());
            iX = -1;
            iY = -1;
        }
        return;
    }

    /**
     * @see java.awt.Component#repaint()
     * @author Anthony C. Liberto
     */
    public void repaint() {
        if (!bDragged) {
            super.repaint();
        }
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isHorizontalTableFormat()
     * @author Anthony C. Liberto
     */
    public boolean isHorizontalTableFormat() {
        return true;
    }

    /**
     * dump
     * @param _brief
     * @return
     * @author Anthony C. Liberto
     */
    public String dump(boolean _brief) {
        StringBuffer sb = new StringBuffer();
        sb.append("Table.getRowCount(): " + getRowCount() + RETURN);
        sb.append("Table.getColumnCount(): " + getColumnCount() + RETURN);
        sb.append(RETURN + "TableModel...." + RETURN);
        sb = tm.dump(sb, _brief);
        return sb.toString();
    }

    /**
     * selectCell
     * @param _pt
     * @author Anthony C. Liberto
     */
    protected void selectCell(Point _pt) {
        int row = rowAtPoint(_pt);
        int col = columnAtPoint(_pt);
        if (row >= 0 && row < getRowCount()) {
            setRowSelectionInterval(row, row);
        }
        if (col >= 0 && col < getColumnCount()) {
            setColumnSelectionInterval(col, col);
        }
        return;
    }

    /**
     * adjustSelectedRow
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void adjustSelectedRow(int _i) {
        int row = getSelectedRow() + _i;
        if (row >= 0 && row < getRowCount()) {
            setRowSelectionInterval(row, row);
        }
        return;
    }

    /**
     * adjustSelectedCol
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void adjustSelectedCol(int _i) {
        int col = getSelectedColumn() + _i;
        if (col >= 0 && col < getColumnCount()) {
            setColumnSelectionInterval(col, col);
        }
        return;
    }

    /*
     Listeners
    */
    /**
     * createOrderListener
     * @author Anthony C. Liberto
     */
    protected void createOrderListener() {
        if (oml == null) {
            oml = new OrderMouseListener();
        }
        if (okl == null) {
            okl = new OrderKeyListener();
        }
        addMouseListener(oml);
        addMouseMotionListener(oml);
        addKeyListener(okl);
        return;
    }

    /**
     * removeOrderListener
     * @author Anthony C. Liberto
     */
    protected void removeOrderListener() {
        if (oml == null) {
            oml = new OrderMouseListener();
        }
        if (okl == null) {
            okl = new OrderKeyListener();
        }
        removeMouseListener(oml);
        removeMouseMotionListener(oml);
        removeKeyListener(okl);
        return;
    }

    private class OrderKeyListener extends KeyAdapter {
        /**
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         * @author Anthony C. Liberto
         */
        public void keyPressed(KeyEvent _ke) {
            if (_ke.isControlDown()) {
                int keyCode = _ke.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {
                    bMoving = true;
                    moveRow(-1);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    bMoving = true;
                    moveRow(1);
                }
            } else {
                int keyCode = _ke.getKeyCode();
                if (keyCode == KeyEvent.VK_UP || (_ke.isShiftDown() && (keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_TAB))) {
                    adjustSelectedRow(-1);
                } else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_ENTER || keyCode == KeyEvent.VK_TAB) {
                    adjustSelectedRow(1);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    adjustSelectedCol(-1);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    adjustSelectedCol(1);
                }
            }
            return;
        }
    }

    private class OrderMouseListener extends MouseInputAdapter {
        /**
         * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseDragged(MouseEvent _me) {
            Point pt = _me.getPoint();
            if (isOrderable() && bDragged) {
                int row = rowAtPoint(pt);
                int col = columnAtPoint(pt);
                bMoving = true;
                if (isMoveableColumn(col, true)) {
                    paintDrag(_me);
                } else {
                    paintTrack();
                }
                if (isValidCell(row, col)) {
                    scrollRectToVisible(getCellRect(row, col, false));
                }
            }
            return;
        }

        /**
         * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mousePressed(MouseEvent _me) {
            Point pt = _me.getPoint();
            selectCell(pt);
            if (isOrderable()) {
                if (isMoveableColumn(columnAtPoint(pt), true)) {
                    iMoveRow = rowAtPoint(pt);
                    bDragged = true;
                }
            }
            return;
        }

        /**
         * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
         * @author Anthony C. Liberto
         */
        public void mouseReleased(MouseEvent _me) {
            if (isOrderable()) {
                Point pt = _me.getPoint();
                if (isMoveableColumn(columnAtPoint(pt), true)) {
                    int iRowPosition = rowAtPoint(pt);
                    if (iMoveRow != iRowPosition) {
                        moveRow(iMoveRow, iRowPosition);
                    }
                    resetMove();
                    repaint();
                }
            }
            return;
        }
    }

    /*
     50468
    */
    /**
     * setUseDefined
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setUseDefined(boolean _b) {
        super.setUseDefined(_b);
        list.setUseDefined(_b);
        return;
    }

    /**
     * setRowHeight
     * @author Anthony C. Liberto
     * @param _font
     */
    public void setRowHeight(Font _font) {
        return;
    }

    /**
     * @see javax.swing.JTable#createDefaultTableHeader()
     * @author Anthony C. Liberto
     */
    protected JTableHeader createDefaultTableHeader() {
        return new JTableHeader(columnModel);
    }

    /*
     acl_20040104
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isDereferenced()
     * @author Anthony C. Liberto
     */
    public boolean isDereferenced() {
        return tm == null;
    }
}
