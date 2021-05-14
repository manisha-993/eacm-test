/**
 * Copyright (c) 2001 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * 	@version 1.1  2002-03-13
 *	@author Joan Tran
 *
 * $Log: HWPermutationTableModel.java,v $
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:05  tony
 * This is the initial load of OPICM
 *
 * Revision 1.5  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.4  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.3  2005/01/31 20:47:46  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2004/02/10 16:59:46  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1.1.1  2003/03/03 18:03:50  tony
 * This is the initial load of OPICM
 *
 * Revision 1.1  2002/10/10 22:20:24  joan
 * add HWPDG
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class HWPermutationTableModel implements TableModel {
    /**
     * listenerList
     */
    private EventListenerList listenerList = new EventListenerList();
    private HWPermutationItem[] cg = null;
    private final int columns = 2;
    private SortObject[] sortObj = null;
    private int sortCol = -1;
    private int[] rowLocs = null;
    private boolean sortAscending = true;
    private String[] names = { "Select", "Description" };

    /**
     * constuct a mFlagObjectTableModel
     *
     * @param cG
     */
    public HWPermutationTableModel(HWPermutationItem[] cG) {
        setMFlagObject(cG);
        modelChanged();
        return;
    }

    /**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
        listenerList = null;
        cg = null;
        sortObj = null;
        rowLocs = null;
        names = null;
        return;
    }
    /**
     * set the displayflagobject for the table
     */
    private void setMFlagObject(HWPermutationItem[] cG) {
        cg = cG;
        return;
    }

    /**
     * get the metaflag object
     *
     * @return HWPermutationItem[]
     */
    public HWPermutationItem[] getHWPermutationItems() {
        return cg;
    }

    /**
     * get the column class for the given column
     *
     * @return Class
     * @param c
     */
    public Class getColumnClass(int c) {
        HWPermutationItem odi = getRow(0);
        if (odi != null) {
            if (c == 0) {
                return odi.getSelected().getClass();

            } else if (c == 1) {
                return odi.toString().getClass();
            }
        }
        return new String().getClass();
    }

    /**
     * add table model listener
     *
     * @param l
     */
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);
        return;
    }

    /**
     * remove table model listener
     *
     * @param l
     */
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
        return;
    }

    /**
     * notify of model changed
     */
    public void modelChanged() {
        tableModelChanged(0, getRowCount() - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE);
        return;
    }

    /**
     * table model changed
     *
     * @param col
     * @param endRow
     * @param startRow
     * @param type
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
     * fire table changed
     *
     * @param e
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
     * get column location from header
     *
     * @return int
     * @param s
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
     * get the column name for specified column
     *
     * @return String
     * @param col
     */
    public String getColumnName(int col) {
        return names[col];
    }

    /**
     * get the number of columns to display
     *
     * @return int
     */
    public int getColumnCount() {
        return columns;
    }

    /**
     * get the number of rows
     *
     * @return int
     */
    public int getRowCount() {
        if (cg == null) {
            return 0;
        }
        return cg.length;
    }

    /**
     * is the cell editable
     *
     * @return boolean
     * @param c
     * @param r
     */
    public boolean isCellEditable(int r, int c) {
        if (c == 0) {
            return true;
        }
        return false;
    }

    /**
     * get object at specified location
     *
     * @return Object
     * @param c
     * @param r
     */
    public Object getValueAt(int r, int c) {
        HWPermutationItem odi = getRow(r);
        if (odi != null) {
            if (c == 0) {
                return odi.getSelected();

            } else if (c == 1) {
                return odi.toString();
            }

        }
        return null;
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
        HWPermutationItem odi = getRow(r, b);
        if (odi != null) {
            if (c == 0) {
                return odi.getSelected();

            } else if (c == 1) {
                return odi.toString();
            }

        }
        return null;
    }

    /**
     * get the display flag item for the row
     *
     * @return HWPermutationItem
     * @param row
     */
    public HWPermutationItem getRow(int row) {
        return getRow(row, true);
    }

    /**
     * get teh display flag item for the row and adjust if necessary
     *
     * @return HWPermutationItem
     * @param adjust
     * @param row
     */
    public HWPermutationItem getRow(int row, boolean adjust) {
        int r = getRowInt(row);
        if (adjust) {
            return cg[r];
        }
        return cg[row];
    }

    /**
     * get array of displayflagItems for flag group
     *
     * @return HWPermutationItem[]
     */
    public HWPermutationItem[] getRows() {
        int ii = getRowCount();
        HWPermutationItem[] out = new HWPermutationItem[ii];
        for (int i = 0; i < ii; ++i) {
            out[i] = getRow(i);
        }
        return out;
    }

    /**
     * toggle selection for the row
     *
     * @param r
     */
    public void toggle(int r) {
        HWPermutationItem dfi = getRow(r);
        if (dfi.isSelected()) {
            dfi.setSelected(false);

        } else {
            dfi.setSelected(true);
        }
        return;
    }

    /**
     * set the value at the specified location
     *
     * @param att
     * @param c
     * @param r
     */
    public void setValueAt(Object att, int r, int c) {
        if (c == 0) {
            HWPermutationItem dfi = getRow(r);
            dfi.setSelected(((Boolean) att).booleanValue());
        }
        return;
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
        int ii = enty.length;
        sortObj = enty;
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
}
