/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * 	@version 1.1  2002-03-13
 *	@author Joan Tran
 *
 * $Log: PDGInfoTableModel.java,v $
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:06  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.2  2003/10/29 00:14:43  tony
 * removed System.out. statements.
 *
 * Revision 1.1  2003/06/19 20:01:49  joan
 * initial load
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import java.util.*;
import javax.swing.table.*;
import javax.swing.event.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class PDGInfoTableModel implements TableModel {
    /**
     * listener
     */
    protected EventListenerList listenerList = new EventListenerList();
    private RowSelectableTable m_rst = null;
    private SortObject[] sortObj = null;
    private int sortCol = 0;
    private int[] rowLocs = null;
    private boolean sortAscending = true;

    /**
     * PDGInfoTableModel
     * constuct a mFlagObjectTableModel
     *
     * @param _rst
     */
    public PDGInfoTableModel(RowSelectableTable _rst) {
        setTable(_rst);
        modelChanged();
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        listenerList = null;
        sortObj = null;
        rowLocs = null;
        return;
    }

    private void setTable(RowSelectableTable _rst) {
        m_rst = _rst;
    }

    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getTable() {
        return m_rst;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     * @author Anthony C. Liberto
     */
    public Class getColumnClass(int c) {
        Object o = m_rst.getMatrixValue(0, c);
        if (o != null) {
            return o.getClass();
        }
        return new String().getClass();
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
     * modelChanged
     * @author Anthony C. Liberto
     */
    public void modelChanged() {
        tableModelChanged(0, getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
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
        TableModelEvent event = new TableModelEvent(this, startRow, endRow, col, type);
        if (startRow < 0) {
            startRow = 0;
        }
        if (endRow < 0) {
            endRow = getRowCount() - 1;
        }
        fireTableChanged(event);
        return;
    }

    /**
     * fireTableChanged
     * @param e
     * @author Anthony C. Liberto
     */
    public void fireTableChanged(TableModelEvent e) {
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == TableModelListener.class) {
                ((TableModelListener) listeners[i + 1]).tableChanged(e);
            }
        }
        return;
    }

    /**
     * getColumnFromHeader
     * @param s
     * @return
     * @author Anthony C. Liberto
     */
    public int getColumnFromHeader(String s) {
        int ii = getColumnCount();
        for (int i = 0; i < ii; ++i) {
            if (s.equals(getColumnName(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     * @author Anthony C. Liberto
     */
    public String getColumnName(int col) {
        return m_rst.getColumnHeader(col);
    }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     * @author Anthony C. Liberto
     */
    public int getColumnCount() {
        return m_rst.getColumnCount();
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     * @author Anthony C. Liberto
     */
    public int getRowCount() {
        return m_rst.getRowCount();
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(int r, int c) {
        return m_rst.isMatrixEditable(r, c);
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int r, int c) {
        return m_rst.getMatrixValue(r, c);
    }

    /**
     * get object at specified location and adjst if necessary
     *
     * @return Object
     * @param b
     * @param c
     * @param r
     */
    public Object getValueAt(int r, int c, boolean b) {
        return m_rst.getMatrixValue(r, c);
    }

    /**
     * get the display flag item for the row
     *
     * @return Object
     * @param row
     */
    public Object getRow(int row) {
        return getRow(row, true);
    }

    /**
     * get teh display flag item for the row and adjust if necessary
     *
     * @return Object
     * @param adjust
     * @param row
     */
    public Object getRow(int row, boolean adjust) {
        int r = getRowInt(row);
        if (adjust) {
            return m_rst.getRow(r);
        }
        return m_rst.getRow(row);
    }

    /**
     * getRowHeaders
     * @return
     * get array of displayflagItems for flag group
     */
    public String[] getRowHeaders() {
        Vector v = new Vector();
        int xx = -1;
        String[] out = null;
        for (int i = 0; i < getRowCount(); ++i) {
            v.add(m_rst.getRowHeaderMatrix(i));
        }

        xx = v.size();
        if (xx <= 0) {
            return null;
        }
        out = new String[xx];
        for (int x = 0; x < xx; ++x) {
            out[x] = (String) v.get(x);
        }
        return out;
    }

    /**
     * toggle selection for the row
     *
     * @param c
     * @param r
     */
    public void toggle(int r, int c) {
        Object o = getValueAt(r, c);
        if (o instanceof Boolean) {
            boolean b = ((Boolean) o).booleanValue();
            if (b) {
                setValueAt(new Boolean(false), r, c);
            } else {
                setValueAt(new Boolean(true), r, c);
            }
        }
    }

    /**
     * set the value at the specified location
     *
     * @param att
     * @param c
     * @param r
     */
    public void setValueAt(Object att, int r, int c) {
        m_rst.putMatrixValue(r, c, att);
    }
    /**
     * conver index from view to model
     *
     * @return int
     * @param i
     */
    protected int convertModelToView(int i) {
        if (rowLocs == null || i < 0 || i >= rowLocs.length) {
            return i;
        }
        return rowLocs[i];
    }

    /**
     * get the sortobject for the table
     *
     * @return sortObject[]
     * @param c
     */
    public SortObject[] getSortObject(int c) {
        int rowCount = getRowCount();
        SortObject[] enty = new SortObject[rowCount];
        for (int r = 0; r < rowCount; ++r) {
            Object o = getValueAt(r, c, false);
            enty[r] = new SortObject(o.toString(), r);
        }
        return enty;
    }

    /**
     * get teh sortobject for the table
     *
     * @return sortObject[]
     */
    public SortObject[] getSortObj() {
        return sortObj;
    }

    /**
     * set teh srot object for the table and provide key informaiton back in
     *
     * @param enty
     */
    public void setSort(SortObject[] enty) {
        int ii = -1;
        sortObj = enty;
        ii = enty.length;
        rowLocs = new int[ii];
        for (int i = 0; i < ii; ++i) {
            rowLocs[sortObj[i].getModel()] = i;
        }
        return;
    }

    /**
     * convert the row from the view to the model
     *
     * @return int
     * @param r
     */
    public int getRowInt(int r) {
        if (r < 0) {
            return r;

        } else if (sortObj == null) {
            return r;
        } else if (r >= sortObj.length) {
            return r;
        }
        try {
            return sortObj[r].getModel();
        } catch (NullPointerException npe) {
            EAccess.report(npe,false);
            return r;
        }
    }

    /**
     * get teh column that was sorted on
     *
     * @return int
     */
    public int getSortCol() {
        return sortCol;
    }

    /**
     * set sort direction
     *
     * @param b
     */
    public void setAscending(boolean b) {
        sortAscending = b;
        return;
    }

    /**
     * is sort ascending
     *
     * @return boolean
     */
    public boolean isAscending() {
        return sortAscending;
    }

    /**
     * set the column to sort
     *
     * @param i
     */
    public void setSortCol(int i) {
        sortCol = i;
        return;
    }

    /**
     * sort
     */
    public void sort() {
        SortObject[] so = getSortObject(getSortCol());
        Arrays.sort(so, new EComparator(isAscending()));
        setSort(so);
        return;
    }

    /**
     * sort
     *
     * @param asc
     * @param col
     */
    public void sort(int col, boolean asc) {
        setSortCol(col);
        setAscending(asc);
        sort();
        return;
    }

    /**
     * getTableTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTableTitle() {
        return m_rst.getTableTitle();
    }

}
