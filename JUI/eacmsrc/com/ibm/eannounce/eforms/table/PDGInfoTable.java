/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * @version 1.1
 * @author Joan Tran
 *
 * $Log: PDGInfoTable.java,v $
 * Revision 1.2  2008/01/30 16:26:58  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.5  2004/11/16 22:25:02  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.4  2004/10/22 22:14:43  tony
 * auto_sort/size
 *
 * Revision 1.3  2004/10/12 22:43:37  joan
 * fixes for TIR
 *
 * Revision 1.2  2004/08/09 20:32:06  joan
 * adjust for PDG
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.3  2003/10/24 21:11:29  tony
 * 52719
 *
 * Revision 1.2  2003/07/18 00:11:16  joan
 * fix horz. bar
 *
 * Revision 1.1  2003/06/19 20:01:49  joan
 * initial load
 *
 */

package com.ibm.eannounce.eforms.table;
import com.ibm.eannounce.eobjects.*;
import com.ibm.eannounce.erend.LockedBooleanRend;
import COM.ibm.eannounce.objects.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class PDGInfoTable extends GTable implements KeyListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private PDGInfoTableModel cgtm = null;
    /**
     * textCol
     */
    private final int textCol = 1;
    //private RowSelectableTable m_rst = null;
    private ERowList m_rList = new ERowList();
    private LockedBooleanRend bRenderer = new LockedBooleanRend();

    /**
     * PDGInfoTable
     * @param _rst
     * @author Anthony C. Liberto
     */
    public PDGInfoTable(RowSelectableTable _rst) {
        super();
        cgtm = new PDGInfoTableModel(_rst);
        //m_rst = _rst;
        setModel(cgtm);
        init();
        m_rList.setFixedCellHeight(-1);
        m_rList.setGTable(this);
        return;
    }

    /**
     * PDGInfoTable
     * @author Anthony C. Liberto
     */
    public PDGInfoTable() {
        super();
        init();
        return;
    }

    /**
     * refreshTable
     * @param _rst
     * @author Anthony C. Liberto
     */
    public void refreshTable(RowSelectableTable _rst) {
        System.out.println("PDGInfoTable refreshTable: ");

        cgtm = new PDGInfoTableModel(_rst);
        setModel(cgtm);
        resizeCells();
        cgtm.modelChanged();
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#dereference()
     * @author Anthony C. Liberto
     */
    public void dereference() {
        if (cgtm != null) {
            cgtm.dereference();
        }
        cgtm = null;
        return;
    }

    /**
     * @see javax.swing.JTable#getCellRenderer(int, int)
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
//        System.out.println("PDGInfoTable getCellRenderer _r: " + _r + ", _c: " + _c);

        boolean isLocked = isCellEditable(_r, _c);
        System.out.println("PDGInfoTable getCellRenderer isLocked: " + isLocked);
        System.out.println("PDGInfoTable getCellRenderer cgtm.getColumnClass(_c): " + cgtm.getColumnClass(_c).getName());

        if (!isLocked && cgtm.getColumnClass(_c).getName().equals("java.lang.Boolean")) {

            return bRenderer;
        }

        return getDefaultRenderer(cgtm.getColumnClass(_c));
    }

    /**
     * init();
     * @author Anthony C. Liberto
     */
    public void init() {
        JTableHeader th = null;
        setRowMargin(0);
        setShowGrid(true);
        setColumnSelectionAllowed(true);
        resizeCells();
        setAutoResizeMode(AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        addKeyListener(this);
        th = getTableHeader();
        th.addMouseMotionListener(this);
        setOpaque(false);
        return;
    }

    /**
     * getTableWidth
     * @return
     * @author Anthony C. Liberto
     */
    public int getTableWidth() {
//        int tableWidth = 0;
        JTableHeader th = getTableHeader();
        TableColumnModel tcm = th.getColumnModel();
        return tcm.getTotalColumnWidth();
    }

    /**
     * getSelectedItem
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSelectedItem() {
        return cgtm.getRow(getSelectedRow());
    }

    /**
     * getInformation
     * @param r
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    public String getInformation(int r, int c) {
        return getString("nia");
    }

    /**
     * reset
     * @author Anthony C. Liberto
     */
    public void reset() {
        return;
    }

    /**
     * @see javax.swing.JTable#getSelectedRows()
     * @author Anthony C. Liberto
     */
    public int[] getSelectedRows() {
        LinkedList l = new LinkedList();
 //       int xx = getRowCount();
        int ii = -1;
        int[] out = null;
 //       for (int x = 0; x < xx; ++x) {
            //			if (cgtm.getRow(x).isSelected()) {
            //				l.add(new Integer(x));
            //			}
//        }
        ii = l.size();
        out = new int[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = ((Integer) l.get(i)).intValue();
        }
        return out;
    }

    //	public Object getHWUpgradeItem(int r) {
    //		return cgtm.getRow(r);
    //	}

    /**
     * sort
     * @author Anthony C. Liberto
     */
    public void sort() {
        cgtm.sort(textCol, true);
    }

    /**
     * getModelRow
     * @param r
     * @return
     * @author Anthony C. Liberto
     */
    public int getModelRow(int r) {
        return cgtm.getRowInt(r);
    }

    /**
     * preView
     * @param row
     * @author Anthony C. Liberto
     */
    public void preView(int row) {
        return;
    }

    /**
     * isValidString
     * @param c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValidString(char c) {
        String s = String.valueOf(c).toLowerCase();
        int row = getSelectedRow() + 1;
        int r = -1;
        if (row >= getRowCount()) {
            row = 0;
        }
        r = findString(s, row);
        if (r >= 0) {
            setRowSelectionInterval(r, r);
            setColumnSelectionInterval(textCol, textCol);
            scrollRectToVisible(getCellRect(r, 0, true));
            repaint();
            return true;
        }
        return false;
    }

    private int findString(String s, int startRow) {
        String curStr = ((String) getValueAt(startRow, textCol)).toLowerCase();
        int rows = -1;
        int compares = -1;
        if (curStr.startsWith(s)) {
            return startRow;
        }
        rows = getRowCount();
        compares = curStr.compareToIgnoreCase(s);
        if (compares > 0) {
            for (int r = 0; r < startRow; ++r) {
                if (doesStringExist(r, s, true)) {
                    return r;
                }
            }
        } else if (compares < 0) {
            for (int r = startRow + 1; r < rows; ++r) {
                if (doesStringExist(r, s, true)) {
                    return r;
                }
            }
        }
        return -1;
    }

    private boolean doesStringExist(int r, String s, boolean negative) {
        String curStr = (String) getValueAt(r, textCol);
        if (negative) {
            if (curStr.compareToIgnoreCase(s) >= 0) {
                return true;
            }
        } else {
            if (curStr.compareToIgnoreCase(s) <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * grabFocus
     * @param i
     * @author Anthony C. Liberto
     */
    public void grabFocus(int i) {
        int r = getSelectedRow() + i;
        if (r >= getRowCount() || r < 0) {
            return;
        }
        preView(r);
        setRowSelectionInterval(r, r);
        return;
    }

    /**
     * hidePopup
     * @author Anthony C. Liberto
     */
    public void hidePopup() {
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#resizeCells()
     * @author Anthony C. Liberto
     */
    public void resizeCells() {
        //System.out.println("PDGInfoTable resizeCells: ");
        int rr = getRowCount();
        int cc = -1;
        FontMetrics fm = null;
        int Width = 0;
        TableColumn tc = null;
        if (!eaccess().canSize(rr)) { //auto_sort/size
            return; //auto_sort/size
        } //auto_sort/size
        cc = getColumnCount();
        fm = getFontMetrics(getFont());
        for (int c = 0; c < cc; ++c) {
            if (isColumnVisible(c)) {
                Width = getWidth(fm, getColumnName(c));
                for (int r = 0; r < rr; ++r) {
                    //tableresize					Object o = getValueAt(r,c);
                    Object o = getDirectValueAt(r, c); //tableresize
                    if (o instanceof Boolean) {
                        Width = Math.max(Width, 40);
                    } else {
                        Width = Math.max(Width, getWidth(fm, o));
                    }
                }
            } else {
                Width = 0;
            }
            tc = getColumnModel().getColumn(c);
            tc.setWidth(Width);
            tc.setPreferredWidth(Width);
            tc.setMinWidth(Width);
            tc.setMaxWidth(Width);
        }
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isColumnVisible(int)
     * @author Anthony C. Liberto
     */
    public boolean isColumnVisible(int col) {
        return true;
    }

    private int getWidth(FontMetrics fm, Object o) {
        int w = 10;
        if (o instanceof String) {
            w += fm.stringWidth((String) o);

        } else if (o instanceof Integer) {
            w += fm.stringWidth(((Integer) o).toString());
        }
        return w;
    }

    /**
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyReleased(KeyEvent kea) {
        int code = -1;
        if (kea.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //			if (tb != null)
            //				tb.cancelCellEditing();
        } else if (kea.getKeyCode() == KeyEvent.VK_ENTER) {
            //			if (tb != null)
            //				tb.stopCellEditing();
        } else if (kea.getKeyCode() == KeyEvent.VK_SPACE) {
            //			if (getSelectedRow() != -1)
            //				cgtm.toggle(getSelectedRow());
            repaint();
            kea.consume();
            return;
        }
        code = kea.getKeyCode();
        if (code != KeyEvent.VK_UP && code != KeyEvent.VK_DOWN && code != KeyEvent.VK_RIGHT && code != KeyEvent.VK_LEFT) {
            isValidString(kea.getKeyChar());
            kea.consume();
        }
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#getString(int, int, boolean)
     * @author Anthony C. Liberto
     */
    public String getString(int r, int c, boolean _b) {
        return "Default String";
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#setFound(int, int)
     * @author Anthony C. Liberto
     */
    public void setFound(int row, int c) {
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isFound(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isFound(int row, int col) {
        return false;
    }
    /**
     * isFound
     *
     * @author Anthony C. Liberto
     * @return boolean
     * @param caseSensitive
     * @param find
     * @param str
     */
    public boolean isFound(String str, String find, boolean caseSensitive) {
        return false;
    }
    /**
     * isRowHidden
     * @param r
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRowHidden(int r) {
        return false;
    }

    /**
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyPressed(KeyEvent kea) {
    }
    /**
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     * @author Anthony C. Liberto
     */
    public void keyTyped(KeyEvent kea) {
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseMoved(MouseEvent e) {
        if (getCursor().getType() != Cursor.WAIT_CURSOR) {
            JTableHeader th = getTableHeader();
            th.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
     * @author Anthony C. Liberto
     */
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isRowFiltered(int)
     * @author Anthony C. Liberto
     */
    public boolean isRowFiltered(int _r) {
        return false;
    }

    /**
     * refreshList
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList refreshList() {
        m_rList.refresh(cgtm.getRowHeaders());
        m_rList.setHeaderValue(cgtm.getTableTitle());
        m_rList.revalidate(); //52719
        return m_rList;
    }

    /**
     * getList
     * @return
     * @author Anthony C. Liberto
     */
    public ERowList getList() {
        return m_rList;
    }

    /**
     * getTableTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTableTitle() {
        return cgtm.getTableTitle();
    }

    /*
     acl_20040104
     */
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isDereferenced()
     * @author Anthony C. Liberto
     */
    public boolean isDereferenced() {
        return cgtm == null;
    }

}
