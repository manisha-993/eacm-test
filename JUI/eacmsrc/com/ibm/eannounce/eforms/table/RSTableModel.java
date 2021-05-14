// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2003, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
 *
 * @version 1.2  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: RSTableModel.java,v $
 * Revision 1.11  2009/09/01 17:12:42  wendy
 * removed useless code
 *
 * Revision 1.10  2009/05/28 13:50:52  wendy
 * Performance cleanup
 *
 * Revision 1.9  2009/05/26 13:43:48  wendy
 * Performance cleanup
 *
 * Revision 1.8  2009/04/15 20:22:18  wendy
 * improve sort performance
 *
 * Revision 1.7  2009/03/10 21:21:38  wendy
 * MN38666284 - CQ00022911:Creating elements from search not linking back to Workgroup
 *
 * Revision 1.6  2008/08/02 02:07:10  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.5  2008/04/29 19:18:22  wendy
 * MN35270066 VEEdit rewrite
 *
 * Revision 1.4  2008/02/21 21:16:22  wendy
 * comments
 *
 * Revision 1.3  2008/02/13 21:08:35  wendy
 * Use lockitem from eaccess when possible
 *
 * Revision 1.2  2007/11/23 19:27:38  couto
 * Preventing null pointer exception.
 *
 * Revision 1.1  2007/04/18 19:45:12  wendy
 * Reorganized JUI module
 *
 * Revision 1.4  2005/11/04 22:58:47  tony
 * adjusted
 *
 * Revision 1.3  2005/10/31 17:22:00  tony
 * VEEdit_Iteration2
 * Added create logic.
 *
 * Revision 1.2  2005/09/12 19:03:16  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:07  tony
 * This is the initial load of OPICM
 *
 * Revision 1.35  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.34  2005/06/02 16:46:25  tony
 * JSTT-6CYHKV
 *
 * Revision 1.33  2005/03/29 21:53:49  tony
 * added logic to help test display functionality
 *
 * Revision 1.32  2005/03/03 22:19:05  tony
 * cr_FlagUpdate
 * improved functionality
 *
 * Revision 1.31  2005/02/03 21:26:14  tony
 * JTest Format Third Pass
 *
 * Revision 1.30  2005/02/03 16:22:33  tony
 * column filtering
 *
 * Revision 1.29  2005/02/01 22:26:40  tony
 * ColumnFilter
 *
 * Revision 1.28  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.27  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.26  2005/01/25 22:56:05  tony
 * MN TBD-DK
 *
 * Revision 1.25  2005/01/18 19:04:05  tony
 * pivot modifications
 *
 * Revision 1.24  2005/01/14 20:32:12  tony
 * pivot
 *
 * Revision 1.23  2005/01/10 22:30:29  tony
 * multiple edit from whereUsed.
 *
 * Revision 1.22  2005/01/05 23:47:16  tony
 * 6554
 * added edit capability to whereused action.
 *
 * Revision 1.21  2004/11/22 18:43:03  tony
 * added hasTable functionality
 *
 * Revision 1.20  2004/11/18 22:07:00  tony
 * improved logic to prevent call from is child is parent
 * when no row information exists, thus preventing a
 * write to the log file.
 *
 * Revision 1.19  2004/11/16 22:25:02  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.18  2004/11/09 21:26:33  tony
 * isIndicateRelations()
 *
 * Revision 1.17  2004/11/08 19:02:38  tony
 * adjusted sorting to always use the tableMcomparator.
 *
 * Revision 1.16  2004/11/05 22:05:05  tony
 * searchable picklist
 *
 * Revision 1.15  2004/11/04 22:30:45  tony
 * *** empty log message ***
 *
 * Revision 1.14  2004/11/04 14:43:07  tony
 * searchable picklist
 *
 * Revision 1.13  2004/08/02 21:41:18  tony
 * improved null catching.
 *
 * Revision 1.12  2004/07/29 22:39:30  tony
 * dwb_20040726
 * array locking improvement.
 *
 * Revision 1.11  2004/06/30 17:05:13  tony
 * updated picklist capability for feature matrix.
 *
 * Revision 1.10  2004/06/25 17:31:19  tony
 * bluePageCreate
 *
 * Revision 1.9  2004/06/24 20:29:26  tony
 * TIR USRO-R-SWWE-629MHH
 *
 * Revision 1.8  2004/06/22 18:07:50  tony
 * EditRelator Parent/Child Indicator
 *
 * Revision 1.7  2004/06/17 14:53:36  tony
 * relator_edit
 *
 * Revision 1.6  2004/06/08 20:42:20  tony
 * 5ZPTCX.2
 *
 * Revision 1.5  2004/04/12 17:18:42  tony
 * MN_18698482
 *
 * Revision 1.4  2004/03/15 16:05:40  tony
 * 53719
 *
 * Revision 1.3  2004/02/27 18:53:41  tony
 * removed display statements.
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.52  2004/01/15 19:01:20  tony
 * dwb_20040115
 * updated scrollpane header logic on matrix.
 *
 * Revision 1.51  2003/12/22 21:38:16  tony
 * 53451
 *
 * Revision 1.50  2003/12/17 16:46:11  tony
 * 52910
 *
 * Revision 1.49  2003/10/29 00:13:05  tony
 * removed System.out. statements.
 *
 * Revision 1.48  2003/10/20 19:03:02  tony
 * 52624
 *
 * Revision 1.47  2003/10/20 16:37:15  tony
 * memory update.  Updated dereference logic to close memory gaps
 *
 * Revision 1.46  2003/10/17 22:46:16  tony
 * fixed null pointers
 *
 * Revision 1.45  2003/10/15 15:57:15  tony
 * simplified logic.
 *
 * Revision 1.44  2003/10/14 20:41:24  tony
 * 51832
 *
 * Revision 1.43  2003/10/03 23:32:48  tony
 * accessibility update.
 *
 * Revision 1.42  2003/10/01 17:09:31  tony
 * 52446
 *
 * Revision 1.41  2003/09/26 14:58:48  tony
 * 52345
 *
 * Revision 1.40  2003/09/24 15:45:00  tony
 * removed System.out
 *
 * Revision 1.39  2003/09/23 16:27:58  tony
 * 52345
 *
 * Revision 1.38  2003/09/22 19:38:19  tony
 * 52342
 *
 * Revision 1.37  2003/09/17 19:21:00  tony
 * 52298
 *
 * Revision 1.36  2003/09/16 18:19:13  tony
 * 52275
 *
 * Revision 1.35  2003/09/11 21:45:52  tony
 * bookmark filter.
 *
 * Revision 1.34  2003/09/11 18:09:25  tony
 * acl_20030911
 * updated addRow logic to sort based on a boolean.
 *
 * Revision 1.33  2003/09/05 20:19:26  tony
 * cleaned-up code.
 *
 * Revision 1.32  2003/09/04 18:25:41  tony
 * 52052
 *
 * Revision 1.31  2003/08/28 17:48:21  tony
 * meta compatibility update.
 *
 * Revision 1.30  2003/08/22 16:40:02  tony
 * general search
 *
 * Revision 1.29  2003/08/21 22:21:15  tony
 * 51876
 *
 * Revision 1.28  2003/08/18 22:46:37  tony
 * middleware update.  updated logic for middleware changes.
 *
 * Revision 1.27  2003/08/15 20:10:08  tony
 * added comment on dwb_20030814
 *
 * Revision 1.26  2003/08/14 22:33:59  tony
 * dwb_20030814 lock update.
 *
 * Revision 1.25  2003/08/13 21:05:02  tony
 * added test logic.
 *
 * Revision 1.24  2003/07/17 17:23:12  tony
 * 51480
 *
 * Revision 1.23  2003/06/27 20:09:11  tony
 * updated logic to prevent validation when in search mode
 * for certain rules.
 * updated messageing.
 *
 * Revision 1.22  2003/06/25 19:14:19  tony
 * joan.20030625 -- updated logic to match middleware changes
 *
 * Revision 1.21  2003/06/19 16:09:43  tony
 * 51298 -- limited capability when working in the past.
 * updated past date logic.
 *
 * Revision 1.20  2003/06/10 16:46:49  tony
 * 51260
 *
 * Revision 1.19  2003/06/05 17:08:46  tony
 * simplified commit logic.
 *
 * Revision 1.18  2003/06/03 19:51:53  tony
 * 51052
 *
 * Revision 1.17  2003/05/30 21:09:24  tony
 * updated messaging logic to popup on top
 * of the currently active Component.
 *
 * Revision 1.16  2003/05/21 15:38:11  tony
 * updated table logic to allow for the table and model
 * to always know the specific type of table.  Based on a
 * table constant.
 *
 * Revision 1.15  2003/05/16 00:30:55  tony
 * 50696
 *
 * Revision 1.14  2003/05/14 19:04:14  tony
 * removed trace statements
 *
 * Revision 1.13  2003/05/13 22:45:05  tony
 * 50616
 * Switched keys from a string to a pointer to the
 * EANFoundation.
 *
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
//import com.ibm.eannounce.einterface.ETableModel;
import java.awt.Component;
import java.util.*;
import javax.swing.event.*;
import javax.swing.table.TableModel;
import javax.swing.event.TableModelListener;
/**
 *
 * @author Anthony C. Liberto
 */
public class RSTableModel implements TableModel, 
EAccessConstants {
    private Object object = null;
    private RowSelectableTable table = null;
    private EANFoundation[] ean = null;
    private EventListenerList listenerList = new EventListenerList();
    private boolean ascending = true;
    private int sortCol = 0;
    private HashMap hashRow = new HashMap();
    private HashMap hashHidden = new HashMap(); //19950

    private int rowHeaderIndex = -1;

    private int iType = -1;
    //tablesort	private Comparator lastComparator = null;
    private boolean bSearch = false; //52624
    private SortDetail sDetails = new SortDetail();
//    private boolean[] sortBoolean = null; //tablesort
    private boolean bPivot = false; //pivot

    /**
     * rsTableModel
     * @param _o
     * @param _table
     * @author Anthony C. Liberto
     */
    public RSTableModel(Object _o, RowSelectableTable _table) {
        this(_o, _table, TABLE_DEFAULT);
    }

    /**
     * rsTableModel
     * @param _o
     * @param _table
     * @param _type
     * @author Anthony C. Liberto
     */
    public RSTableModel(Object _o, RowSelectableTable _table, int _type) {
        setType(_type);
        setObject(_o);
        setTable(_table);
        if (isType(TABLE_MATRIX)) { //dwb_20040115
            setLongDescription(true); //dwb_20040115
        } //dwb_20040115
        ean = getRowKeyArray(); //22195
        modelChanged(TableModelEvent.INSERT);
    }

    /**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     */
    public static EAccess eaccess() {
        return EAccess.eaccess();
    }

    /**
     * getWhereUsedGroup
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public WhereUsedGroup getWhereUsedGroup(String _s) {
        if (object instanceof WhereUsedList) {
            return ((WhereUsedList) object).getWhereUsedGroup(_s);
        }
        return null;
    }

    /**
     * setType
     * @param _type
     * @author Anthony C. Liberto
     */
    public void setType(int _type) {
        iType = _type;
    }

    /**
     * isType
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isType(int _i) {
        return iType == _i;
    }

    /**
     * setObject
     * @author Anthony C. Liberto
     * @param _o
     */
    public void setObject(Object _o) {
        object = _o;
    }

    /**
     * setTable
     * @param _table
     * @author Anthony C. Liberto
     */
    public void setTable(RowSelectableTable _table) {
        if (table != null && _table != null && isType(TABLE_RESTORE)) { //53719
            if (table.hasFilteredRows()) { //53719
                _table.setFilterGroup(table.getFilterGroup()); //53719
			} else if (table.hasFilteredCols()) {						//filter_col
				_table.setColFilterGroup(table.getColFilterGroup());	//filter_col
            } //53719
        } //53719
        table = _table;
    }

    /**
     * getFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getFilterGroup() {
        if (table != null) {
            FilterGroup fg = table.getFilterGroup();
            if (fg == null) {
                try {
                    fg = new FilterGroup(null, eaccess().getActiveProfile(), table);
                    //System.out.println("RSTableModel.fg1.isColumnFilter: " + fg.isColumnFilter());
                    setFilterGroup(fg);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                }
            }
            return fg;
        }
        return null;
    }

    /**
     * setFilterGroup
     * @param _group
     * @author Anthony C. Liberto
     */
    public void setFilterGroup(FilterGroup _group) {
        if (table != null) {
            table.setFilterGroup(_group);
        }
    }

    /**
     * isFiltered
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFiltered() {
        if (table != null) {
			return table.hasFilteredRows() || table.hasFilteredCols();	//filter_col
//filter_col            return table.hasFilteredRows();
        }
        return false;
    }

    /**
     * filter
     * @author Anthony C. Liberto
     */
    public void filter() {
        table.setUseFilter(true);
        table.refresh();
        //acl_20030911		synchronize(TableModelEvent.UPDATE);
        synchronize(TableModelEvent.UPDATE, true); //acl_20030911
    }

    /**
     * refresh
     * @author Anthony C. Liberto
     */
    public void refresh() {
        table.refresh();
        //acl_20030911		synchronize(TableModelEvent.UPDATE);			//50696
        synchronize(TableModelEvent.UPDATE, true); //acl_20030911
    }

    /**
     * resetKeys
     * @author Anthony C. Liberto
     */
    public void resetKeys() {
        ean = getRowKeyArray();
    }

    /**
     * resetFilter
     * @author Anthony C. Liberto
     */
    public void resetFilter() {
        //System.out.println("RSTableModel.resetting Filter");
        //System.out.println("RSTableModel rowCount after: " + getRowCount());
        table.setUseFilter(false);
        refresh();
        //System.out.println("RSTableModel rowCount after: " + getRowCount());
        //acl_20030911		synchronize(TableModelEvent.UPDATE);
        //redundant synchronize(TableModelEvent.UPDATE,true);		//acl_20030911
    }

    /**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    public RowSelectableTable getTable() {
        return table;
    }

    /**
     * updateModel
     * @param _table
     * @author Anthony C. Liberto
     */
    public void updateModel(RowSelectableTable _table) {
        int changeEvent = TableModelEvent.UPDATE; //gen_search
        if (table == null) { //gen_search
            changeEvent = TableModelEvent.INSERT; //gen_search
        } //gen_search
        updateModel(_table, changeEvent);
        return;
    }

    /**
     * updateModel
     * @param _table
     * @param _event
     * @author Anthony C. Liberto
     */
    public void updateModel(RowSelectableTable _table, int _event) {
        setTable(_table);
        ean = getRowKeyArray(); //22195
        modelChanged(_event);
        return;
    }

    /**
     * getRowKeyArray
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation[] getRowKeyArray() {
        if (table != null) {
            EANFoundation[] tmp = table.getTableRowsAsArray();
            return tmp;
        }
        return null;
    }

    /**
     * getObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object getObject() {
        return object;
    }

    /**
     * getTableTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTableTitle() {
        if (table != null) {
            if (bPivot) { //pivot
                return table.getTableTitle() + " (Pivot)"; //pivot
            } //pivot
            return table.getTableTitle();
        }
        return null;
    }

    /**
     * getTitle
     * @return
     * @author Anthony C. Liberto
     */
    public String getTitle() {
        return object.toString();
    }

    /**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     * @author Anthony C. Liberto
     */
    public Class getColumnClass(int col) {
        if (bPivot) { //pivot
            return table.getRowClass(col); //pivot
        } //pivot
        return table.getColumnClass(col);
    }

    /**
     * getRowClass
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public Class getRowClass(int _row) {
        if (bPivot) { //pivot
            return table.getColumnClass(_row); //pivot
        } //pivot
        return table.getRowClass(_row);
    }

    /**
     * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
     * @author Anthony C. Liberto
     */
    public void addTableModelListener(TableModelListener l) {
        listenerList.add(TableModelListener.class, l);
    }

    /**
     * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
     * @author Anthony C. Liberto
     */
    public void removeTableModelListener(TableModelListener l) {
        listenerList.remove(TableModelListener.class, l);
    }

    /**
     * modelChanged
     * @author Anthony C. Liberto
     */
    public void modelChanged() {
        modelChanged(TableModelEvent.UPDATE);
    }

    /**
     * modelChanged
     *
     * @param _type
     * @author Anthony C. Liberto
     */
    public void modelChanged(int _type) {
        tableModelChanged(0, getRowCount() - 1, TableModelEvent.ALL_COLUMNS, _type);
    }

    /**
     * removeRow
     * @author Anthony C. Liberto
     * @param _o
     */
    public void removeRow(Object _o) {
    }

    /**
     * tableModelChanged
     * @param startRow
     * @param endRow
     * @param col
     * @param type
     * @author Anthony C. Liberto
     */
    private void tableModelChanged(int startRow, int endRow, int col, int type) {
        TableModelEvent event = null;
        if (startRow < 0) {
            startRow = 0;
        }
        if (endRow < 0) {
            endRow = getRowCount() - 1;
        }
        event = new TableModelEvent(this, startRow, endRow, col, type);
        fireTableChanged(event);
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
    }

    /**
     * @see javax.swing.table.TableModel#getColumnName(int)
     * @author Anthony C. Liberto
     */
    public String getColumnName(int _col) {
        if (isType(TABLE_HORIZONTAL) || isType(TABLE_NAVIGATE)) { // relator_edit
			if (isIndicateRelations()) { //TIR USRO-R-SWWE-629MHH
                if (hasRows()) {
                    if (isChild(0, _col)) { // relator_edit
                        if (bPivot) { //pivot
                            return INDICATE_CHILD + table.getRowHeader(_col); //pivot
                        } //pivot
                        return INDICATE_CHILD + table.getColumnHeader(_col); // relator_edit
                    } else if (isParent(0, _col)) { // relator_edit
                        if (bPivot) { //pivot
                            return INDICATE_PARENT + table.getRowHeader(_col); //pivot
                        } //pivot
                        return INDICATE_PARENT + table.getColumnHeader(_col); // relator_edit
                    } else { // relator_edit
                        if (bPivot) { //pivot
                            return table.getRowHeader(_col); //pivot
                        } //pivot
                        return table.getColumnHeader(_col); // relator_edit
                    } // relator_edit
                }
            } else { //TIR USRO-R-SWWE-629MHH
                if (bPivot) { //pivot
                    return table.getRowHeader(_col); //pivot
                } //pivot
                return table.getColumnHeader(_col); //TIR USRO-R-SWWE-629MHH
            } // relator_edit
        } // relator_edit
        if (bPivot) { //pivot
            if (isType(TABLE_MATRIX)) { //pivot
                return table.getRowHeaderMatrix(_col); //pivot
            } //pivot
            return table.getRowHeader(_col); //pivot
        } //pivot
        return table.getColumnHeader(_col);
    }

    /**
     * @see javax.swing.table.TableModel#getColumnCount()
     * @author Anthony C. Liberto
     */
    public int getColumnCount() {
        if (table == null) {
            return 0;
        }
        if (bPivot) { //pivot
            if (ean != null) { //pivot
                return ean.length; //pivot
            } else if (object == null) { //pivot
                return 0; //pivot
            } else if (table == null) { //pivot
                return 0; //pivot
            } //pivot
            return table.getRowCount(); //pivot
        } //pivot
        return table.getColumnCount();
    }

    /**
     * @see javax.swing.table.TableModel#getRowCount()
     * @author Anthony C. Liberto
     */
    public int getRowCount() {
        if (bPivot) { //pivot
            if (table == null) { //pivot
                return 0; //pivot
            } //pivot
            return table.getColumnCount(); //pivot
        } //pivot
        if (ean != null) { //21408
            return ean.length;
        } //21408
        if (object == null) {
            return 0;
        }
        if (table == null) {
            return 0;
        }
        return table.getRowCount();
    }

    /**
     * isHidden
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isHidden(String _key) { //19950
        return hashHidden.containsKey(_key); //19950
    } //19950

    /**
     * setRowHeight
     *
     * @param _i
     * @param _height
     * @author Anthony C. Liberto
     */
    public void setRowHeight(int _i, int _height) {
        if (bPivot) { //pivot
            setRowHeight(table.getColumnKey(_i), _height); //pivot
        } else { //pivot
            setRowHeight(table.getRowKey(_i), _height);
        } //pivot
        return;
    }

    /**
     * setRowHeight
     * @param _key
     * @param _height
     * @author Anthony C. Liberto
     */
    public void setRowHeight(String _key, int _height) {
        if (_height >= 0) {
            hashRow.put(_key, new Integer(_height));

        } else {
            hashRow.remove(_key);
        }
        return;
    }

    /**
     * getRowHeight
     *
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public int getRowHeight(int _i) {
        String key = null;
        if (table == null) { //21549
            return -1;
        } //21549
        //pivot		String key = table.getRowKey(_i);
        if (bPivot) { //pivot
            key = table.getColumnKey(_i); //pivot
        } else { //pivot
            key = table.getRowKey(_i); //pivot
        } //pivot
        return getRowHeight(key);
    }

    /**
     * getRowHeight
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    public int getRowHeight(String _key) {
        if (hashRow.containsKey(_key)) {
            return ((Integer) hashRow.get(_key)).intValue();
        }
        return -1;
    }

    /**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(int _r, int _c) {
        //51298		if (!table.canEdit()) {
        if (isSearch()) { //52624
            return table.canEdit(); //52624
        } else if (!eaccess().isEditable(table)) { //51298
            return false;
        }
        if (hasLock(_r, _c)) {
            return isEditable(_r, _c);
        }
        return false;
    }

    /**
     * isDynaTable
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isDynaTable() { //dyna
        if (table != null) {
            return table.isDynaTable(); //dyna
        }
        return false;
    } //dyna

    /**
     * isEditable
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isEditable(int _r, int _c) {
        if (table != null) {
            if (bPivot) { //pivot
                return table.isEditable(_c, _r); //pivot
            } //pivot
            return table.isEditable(_r, _c);
        }
        return false;
    }

    /**
     * hasLock
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasLock(int _r, int _c) {
        if (table == null) {
            return false;
        }
        /* cant assume this because relator attr is editable 
         * if (isType(TABLE_MATRIX)) { //21843
            return true; //21843
        } else 
        */if (isType(TABLE_BOOLEAN)) { //gen_search
            return true; //gen_search
        } else if (isType(TABLE_MAINTENANCE)) {
			return true;
		}

        try {
            if (bPivot) { //pivot
                return table.hasLock(_c, _r, eaccess().getLockOwner(), getActiveProfile()); //pivot
            } //pivot
            return table.hasLock(_r, _c, eaccess().getLockOwner(), getActiveProfile());
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * isCellLocked
     *
     * @param _r
     * @param _c
     * @param _acquireLock
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCellLocked(int _r, int _c, boolean _acquireLock) {
        return isCellLocked(_r, _c, getLockList(), getActiveProfile(), _acquireLock);
    }

    /**
     * isPasteable
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPasteable(int _r, int _c) {
        //pivot		Object o = table.getEANObject(_r,_c);
        Object o = null; //pivot
        if (bPivot) { //pivot
            o = table.getEANObject(_c, _r); //pivot
        } else { //pivot
            o = table.getEANObject(_r, _c); //pivot
        } //pivot
        if (o instanceof SingleFlagAttribute) {
            return false;
        } else if (o instanceof StatusAttribute) {
            return false;
        } else if (o instanceof MultiFlagAttribute) {
            return false;
        } else if (o instanceof LongTextAttribute) {
            return true;
        } else if (o instanceof BlobAttribute) {
            return false;
        } else if (o instanceof TextAttribute) {
            return true;
        }
        return false;
    }

    /**
     * isCellLocked
     * @param _r
     * @param _c
     * @param _ll
     * @param _prof
     * @param _acquireLock
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isCellLocked(int _r, int _c, LockList _ll, Profile _prof, boolean _acquireLock) {
        //try {
            //EntityItem lockOwnerEI = new EntityItem(null, _prof, Profile.OPWG_TYPE, _prof.getOPWGID());
            EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
            if (bPivot) { //pivot
                return eaccess().dBase().isCellLocked(table, _c, _r, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL, Routines.getClientTime(), _acquireLock); //pivot
            } //pivot
            return eaccess().dBase().isCellLocked(table, _r, _c, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL, Routines.getClientTime(), _acquireLock);
        //} catch (MiddlewareRequestException _mre) {
          //  _mre.printStackTrace();
        //}
       //return false;
    }

    /**
     * lockRow
     * @param _r
     * @param _ll
     * @param _prof
     * @author Anthony C. Liberto
     */
    public void lockRow(int _r, LockList _ll, Profile _prof) {
        //dwb_20030814		table.resetLockGroup(_r, _ll);
        /*
         the table.resetLockGroup(_r,_ll);
         call allows for ability to acquire a lock after an initial lock failure.
         eg.
         	Bob has a lock on br:1
         	Ned tries to lock br:1
         		(lock fails)
         	Bob release's lock on br:1
         	Ned tries to lock br:1
         		Ned's lock will fail because it failed before.
         	NOTE:  with line in Ned's lock will succeed.
         */
       // Profile prof = getActiveProfile();
        try {
        	EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
            //EntityItem lockOwnerEI = new EntityItem(null, prof, Profile.OPWG_TYPE, prof.getOPWGID());
            eaccess().dBase().lock(table, _r, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * unlockRow
     * @param _r
     * @param _ll
     * @param _prof
     * @author Anthony C. Liberto
     */
    public void unlockRow(int _r, LockList _ll, Profile _prof) {
        //try {
            EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
            	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getActiveProfile().getOPWGID());
            if (lockOwnerEI != null){
            	eaccess().dBase().unlock(table, _r, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
            }
        //} catch (MiddlewareRequestException _mre) {
          //  _mre.printStackTrace();
        //}
    }

    /**
     * removeRow
     * @param _r
     * @author Anthony C. Liberto
     */
    public void removeRow(int _r) {
//        String key = table.getRowKey(_r);
        table.removeRow(_r);
        synchronize(_r, _r, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
    }

    /**
     * removeRows
     * @param _r
     * @author Anthony C. Liberto
     */
    public void removeRows(int[] _r) {
        int ii = _r.length;
        for (int i = 0; i < ii; ++i) {
            table.removeRow(_r[i]);
        }
        table.refresh();
        synchronize(0, getRowCount(), TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
    }

    /**
     * setParentItem
     * @param _r
     * @param _ei
     * @author Anthony C. Liberto
     */
    public void setParentItem(int _r, EntityItem _ei) {
        table.setParentEntityItem(_r, _ei);
    }

    /**
     * getColumnKey
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public String getColumnKey(int _i) {
        if (bPivot) { //pivot
            return table.getRowKey(_i); //pivot
        } //pivot
        return table.getColumnKey(_i);
    }

    /**
     * getColumnIndex
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public int getColumnIndex(String _s) {
        if (table != null) {
        	if (bPivot) { //pivot
        		return table.getRowIndex(_s); //pivot
        	} //pivot
        	return table.getColumnIndex(_s);
        }
        return -1;
    }

    /**
     * getModelRowIndex
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public int getModelRowIndex(int _i) {
    	if (table == null) {
    		return -1;
    	}
        String key = null;
        if (bPivot) { //pivot
            key = table.getColumnKey(_i); //pivot
            return table.getColumnIndex(key); //pivot
        } //pivot
        key = getRowKey(_i);
        return table.getRowIndex(key);
    }

    /**
     * getRowKey
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    public String getRowKey(int _i) {
        if (bPivot) { //pivot
            return table.getColumnKey(_i); //pivot
        } //pivot
        if (ean == null || _i < 0 || _i >= ean.length) {
            return null;
        }
        return ean[_i].getKey();
    }

    /**
     * getRowIndex
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public int getRowIndex(String _s) {
        if (table != null) { //prevent NPE
        	if (bPivot) { //pivot
        		return table.getColumnIndex(_s); //pivot
        	} //pivot
        	return table.getRowIndex(_s);
        }
        return -1;
    }

    /**
     * getViewRowIndex
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    public int getViewRowIndex(String _s) {
        int ii = ean.length;
        for (int i = 0; i < ii; ++i) {
            if (bPivot) { //pivot
                if (table != null) {
                	return table.getColumnIndex(_s); //pivot
                } else {
                	return -1;
                }
            } //pivot
            if (ean[i].getKey().equals(_s)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
        Object out = null;
        if (isType(TABLE_MATRIX)) {
            if (bPivot) { //pivot
                return table.getMatrixValue(_c, _r); //pivot
            } //pivot
            return table.getMatrixValue(_r, _c);
        }
        if (bPivot) { //pivot
            out = table.get(_c, _r); //pivot
        } else {
	        out = table.get(_r, _c);
		}
//		if (out != null) {
//			System.out.println("***   GB2   ***");
//			System.out.println("    retrieve: (" + _r + ", " + _c + ")");
//			System.out.println("    value   : " + out.toString());
//		}
		return out;
    }

    /**
     * getValueAt
     * @param _r
     * @param _c
     * @param _longDesc
     * @return
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c, boolean _longDesc) {
        if (isType(TABLE_MATRIX)) {
            if (bPivot) { //pivot
                return table.getMatrixValue(_c, _r); //pivot
            } //pivot
            return table.getMatrixValue(_r, _c);
        }

        if (bPivot) { //pivot
            return table.get(_c, _r, _longDesc); //pivot
        } //pivot

        return table.get(_r, _c, _longDesc);
    }

    /**
     * getLockGroup
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    public LockGroup getLockGroup(int _r, int _c) { //lock_update
        if (bPivot) { //pivot
            return table.getLockGroup(_c, _r); //pivot
        } //pivot
        return table.getLockGroup(_r, _c); //lock_update
    } //lock_update

    /**
     * setValueAt
     * @deprecated
     * @param _att
     * @param _c
     * @param _r
     */
    public void setValueAt(Object _att, int _r, int _c) {
        putValueAt(_att, _r, _c, null); //acl_20021015
    } //acl_20021015

    /**
     * putValueAt
     * @param _att
     * @param _r
     * @param _c
     * @param _comp
     * @return
     * @author Anthony C. Liberto
     */
    public boolean putValueAt(Object _att, int _r, int _c, Component _comp) { //acl_20021015
        try {
            if (isType(TABLE_MATRIX)) {
                //System.out.println("RSTableModel.putValueAt trying to put to matrix table");
                if (bPivot) { //pivot
                    table.putMatrixValue(_c, _r, _att); //pivot
                } else { //pivot
                    table.putMatrixValue(_r, _c, _att);
                } //pivot
            } else if (isType(TABLE_BOOLEAN)) { //gen_search
                if (bPivot) { //pivot
                    table.put(_c, _r, _att); //pivot
                } else { //pivot
                    table.put(_r, _c, _att); //gen_search
                } //pivot
            } else {
                if (bPivot) { //pivot
                    table.put(_c, _r, _att); //pivot
                } else { //pivot
                    table.put(_r, _c, _att);
                } //pivot
            }
        } catch (EANBusinessRuleException _bre) {
            _bre.printStackTrace();
            //51260			eaccess().setMessage(_bre.toString());
            //51260			eaccess().showError(null);
            eaccess().showException(_bre, _comp, ERROR_MESSAGE, OK); //51260
            return false;
        }
        return true;
    }

    /**
     * getEANObject
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected EANFoundation getEANObject(int _r, int _c) {
		EANFoundation out = null;
        if (isType(TABLE_MATRIX)) {
            return null;
        } else if (isType(TABLE_BOOLEAN)) {
            return null;
        }
        if (bPivot) { //pivot
            out = table.getEANObject(_c, _r); //pivot
        } else {
	        out = table.getEANObject(_r, _c);
		}

//		if (out != null) {
//			System.out.println("***   GB2   ***");
//			System.out.println("    class: " + out.getClass().getName());
//			System.out.println("    value: " + out.toString());
//		}
		return out;
    }

    /**
     * getRow
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public Object getRow(int _row) {
        if (_row < 0 || _row >= getRowCount()) {
            return null;
        }
        if (bPivot) { //pivot
            return table.getColumn(_row); //pivot
        } //pivot
        return getRowByKey(ean[_row].getKey());
    }

    /**
     * isNew
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isNew(int _row) { //19937
        Object o = getRow(_row); //19937
        if (o instanceof EntityItem) { //19937
            return ((EntityItem) o).isNew(); //19937
        }
        return false; //19937
    } //19937

    /**
     * isNewModelRow
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isNewModelRow(int _row) {
        Object o = getModelRow(_row);
        if (o instanceof EntityItem) {
            return ((EntityItem) o).isNew();
        }
        return false;
    }

    /**
     * getRowByKey
     * @author Anthony C. Liberto
     * @return Object
     * @param _key
     */
    public Object getRowByKey(String _key) {
    	if (_key!=null){
    		try { //52298
    			if (bPivot) { //pivot
    				return table.getColumn(_key); //pivot
    			} //pivot
    			return table.getRow(_key);
    		} catch (Exception _x) { //52298
    			_x.printStackTrace(); //55298
    		} //52298
    	}else{
    		System.err.println("##RSTableModel.getRowByKey _key is null!!");
    	}
        return null; //52298
    }

    /**
     * getModelRow
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public Object getModelRow(int _row) {
        if (bPivot) { //pivot
            return table.getColumn(_row); //pivot
        } //pivot
        return table.getRow(_row);
    }

    /**
     * getModelColumn
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getModelColumn(int _col) { //22411
        if (bPivot) { //pivot
            return table.getRow(_col); //pivot
        } //pivot
        return table.getColumn(_col); //22411
    } //22411

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort() {
        //		sort(sortCol, ascending);
        int ii = getColumnCount(); //sortAll
        int[] col = new int[ii]; //sortAll
        boolean[] b = new boolean[ii]; //sortAll
        for (int i = 0; i < ii; ++i) { //sortAll
            col[i] = i; //sortAll
            b[i] = ascending; //sortAll
        } //sortAll
        sort(col, b); //sortAll
    }

    /**
     * sort
     *
     * @param _direction
     * @author Anthony C. Liberto
     */
    public void sort(boolean _direction) {
        sort(sortCol, _direction);
    }

    /**
     * sortHeader
     * @param _direction
     * @author Anthony C. Liberto
     */
    protected void sortHeader(boolean _direction) {
        int[] i = new int[1];
        boolean[] b = new boolean[1];
        sortCol = -1;
        ascending = _direction;
        //acl_20041108		sort(new tableComparator(sortCol,ascending));
        i[0] = sortCol; //acl_20041108
        b[0] = ascending; //acl_20041108
        //tablesort		sort(new tableMComparator(i,b));				//acl_20041108
        sort(i, b); //tablesort
    }

    /**
     * sort
     *
     * @param _column
     * @param _direction
     * @author Anthony C. Liberto
     */
    public void sort(int _column, boolean _direction) {
        int [] i = new int [1];
        boolean[] b = new boolean[1];
        if (_column < 0 || _column >= getColumnCount()) {
            _column = 0;
        }
        sortCol = _column;
        ascending = _direction;
        //acl_20041108		sort(new tableComparator(sortCol, ascending));
        i[0] = sortCol; //acl_20041108
        b[0] = ascending; //acl_20041108
        //tablesort		sort(new tableMComparator(i,b));				//acl_20041108
        sort(i, b);
    }

    /**
     * setSort
     * @param _sort
     * @param _modelChanged
     * @author Anthony C. Liberto
     */
    protected void setSort(EANFoundation[] _sort, boolean _modelChanged) {
        //		ean = _sort;
        if (_sort != null && table != null) {
            table.synchronizeRows(_sort);
            ean = _sort;
        }
        if (_modelChanged) {
            modelChanged();
        }
    }

    /**
     * getKeys
     * @return
     * @author Anthony C. Liberto
     */
    protected EANFoundation[] getKeys() {
        return ean;
    }

    /**
     * getColumnFromHeader
     * @param s
     * @return
     * @author Anthony C. Liberto
     * /
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
     * getColumn
     *
     * @param _column
     * @return
     * @author Anthony C. Liberto
     */
    public EANFoundation getColumn(int _column) {
        return getColumnByKey(getColumnKey(_column));
    }

    /**
     * getColumnByKey
     * @author Anthony C. Liberto
     * @param _key
     * @return EANFoundation
     */
    public EANFoundation getColumnByKey(String _key) {
        if (bPivot) { //pivot
            return table.getRow(_key); //pivot
        } //pivot
        return table.getColumn(_key);
    }

    /**
     * addColumn
     * @author Anthony C. Liberto
     * @param _o
     */
    public void addColumn(Object _o) {
    }

    /**
     * addColumn
     * @param _ei
     * @author Anthony C. Liberto
     */
    protected void addColumn(EntityItem[] _ei) { //23551
        //		if (bPivot) {													//pivot
        //			table.addRow(_ei);											//pivot
        //		} else {														//pivot
        table.addColumn(_ei); //23551
        //		}																//pivot
        modelChanged(); //23551
    } //23551

    /**
     * dereference
     *
     * @author Anthony C. Liberto
     */
    public void dereference() {
        EventListener[] l = listenerList.getListeners(TableModelListener.class);
        for (int i = 0; i < l.length; ++i) {
            listenerList.remove(TableModelListener.class, l[i]);
        }
        l = null;
        listenerList = null;
        object = null;
        ean = null;

        hashRow.clear();
        hashRow = null;

        hashHidden.clear(); //19950
        hashHidden = null; //19950

        //tablesort		lastComparator = null;
        sDetails.reset(); //tablesort
        sDetails = null; //tablesort
        table = null;
    }
    /*
     *rollback
     */
    /**
     * rollback
     * @author Anthony C. Liberto
     */
    protected void rollback() {
        table.rollback();
        modelChanged();
    }

    /**
     * rollback
     * @param _rowKey
     * @author Anthony C. Liberto
     */
    protected void rollback(String _rowKey) {
        //pivot		int row = table.getRowIndex(_rowKey);
        int row = 0; //pivot
        if (bPivot) { //pivot
            row = table.getColumnIndex(_rowKey); //pivot
        } else { //pivot
            row = table.getRowIndex(_rowKey); //pivot
        } //pivot
        table.rollback(row);
        modelChanged();
    }

    /**
     * rollback
     * @param _rowKey
     * @param _colKey
     * @author Anthony C. Liberto
     */
    protected void rollback(String _rowKey, String _colKey) {
        int row = table.getRowIndex(_rowKey);
        int col = table.getColumnIndex(_colKey);
        if (bPivot) { //pivot
            table.rollback(col, row); //pivot
        } else { //pivot
            table.rollback(row, col);
        } //pivot
        modelChanged();
    }

    /**
     * rollbackMatrix
     * @author Anthony C. Liberto
     */
    protected void rollbackMatrix() {
        table.rollbackMatrix();
        modelChanged();
    }

    //acl_20030911	public void addRow(boolean _modelChanged) {
    /**
     * addRow
     *
     * @param _modelChanged
     * @param _sort
     * @author Anthony C. Liberto
     */
    public void addRow(boolean _modelChanged, boolean _sort) { //acl_20030911
        //		if (bPivot) {												//pivot
        //			table.addColumn();										//pivot
        //		} else {													//pivot
    	boolean success=false; //MN38666284 - CQ00022911
		if (table != null) {
	        success = table.addRow();
		} else {
			System.out.println("RSTableModel.addRow why is the table null?");
		}
		//		}															//pivot
		if (success){
			if (_modelChanged) {
				//acl_20030911			synchronize(TableModelEvent.INSERT);
				synchronize(TableModelEvent.INSERT, _sort); //acl_20030911
			} else {
				//acl_20030911			synchronize(DO_NOTHING);
				synchronize(DO_NOTHING, _sort); //acl_20030911
			}
		}else{
			eaccess().setMessage("Error adding row, see log for details");
			eaccess().showError(null);
		}
    }

    /**
     * addRow
     * VEEdit_Iteration2
     *
     * @param _key
     * @param _modelChanged
     * @param _sort
     * @author Anthony C. Liberto
     */
    protected void addRow(String _key,boolean _modelChanged, boolean _sort) {
		if (table != null) {
	        table.addRow(_key);
		}
        if (_modelChanged) {
            synchronize(TableModelEvent.INSERT, _sort);
        } else {
            synchronize(DO_NOTHING, _sort);
        }
    }

    /**
     * synchronize
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean synchronize() {
        return false;
    }

    //acl_20030911	public void synchronize(int _i) {
    /**
     * synchronize
     * @param _i
     * @param _sort
     * @author Anthony C. Liberto
     */
    protected void synchronize(int _i, boolean _sort) { //acl_20030911
        if ((_i == TableModelEvent.INSERT || _i == DO_NOTHING) && !_sort) { //52342
            EANFoundation[] tmp = getRowKeyArray(); //52342
            System.arraycopy(ean, 0, tmp, 0, ean.length); //52342
            ean = tmp; //52342
        } else { //52342
            ean = getRowKeyArray();
        } //52342
        if (_i != DO_NOTHING) {
            modelChanged(_i);
        }
        if (_sort) { //acl_20030911
            sortLast();
        } //acl_20030911
    }

    /**
     * synchronize
     * @param _r
     * @param _rr
     * @param _cols
     * @param _action
     * @author Anthony C. Liberto
     */
    private void synchronize(int _r, int _rr, int _cols, int _action) {
        ean = getRowKeyArray();
        if (_action != DO_NOTHING) {
            tableModelChanged(_r, _rr, _cols, _action);
        }
        sortLast();
    }

    /*
     * helptext
     */
    /**
     * getHelp
     * @param _rowKey
     * @param _colKey
     * @return
     * @author Anthony C. Liberto
     */
    protected String getHelp(String _rowKey, String _colKey) {
        if (bPivot) { //pivot
            return table.getHelp(table.getColumnIndex(_rowKey), table.getRowIndex(_rowKey)); //pivot
        } //pivot
        return table.getHelp(table.getRowIndex(_rowKey), table.getColumnIndex(_colKey));
    }

    /**
     * sort
     * @param _c
     * @param _d
     * @author Anthony C. Liberto
     */
    protected void sort(int[] _c, boolean[] _d) { //MSort
        for (int i = 0; i < _c.length; i++) {
        	//no-op for performance 
        	if (rsTableComparator.NO_OP_ID ==_c[i]){
        		continue;
        	}
            if (_c[i] < -1 || _c[i] >= getColumnCount()) {
                _c[i] = 0;
            }
        }
 
        //tablesort		sort(new tableMComparator(_c, _d));
        sDetails.setDetails(_c, _d); //tablesort
        sortTable(_c, _d, iType); //tablesort
    }

    /**
     * isLongDescriptionEnabled
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isLongDescriptionEnabled() {
        return table.isLongDescriptionEnabled();
    }

    /**
     * setLongDescription
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setLongDescription(boolean _b) {
        table.setLongDescription(_b);
    }
    /*
     * row header
     */
    /**
     * setRowHeaderIndex
     * @param _i
     * @author Anthony C. Liberto
     */
    protected void setRowHeaderIndex(int _i) {
        table.setRowHeaderIndex(_i);
        rowHeaderIndex = _i;
    }

    /**
     * getRowHeaderIndex
     * @return
     * @author Anthony C. Liberto
     */
    protected int getRowHeaderIndex() {
        return rowHeaderIndex;
    }

    /**
     * getRowHeader
     * @return
     * @author Anthony C. Liberto
     */
    protected String[] getRowHeader() {
        if (table != null) {
            int ii = getRowCount();
            Vector v = new Vector(); //19938
            int xx = -1;
            String[] out = null;
            for (int i = 0; i < ii; ++i) {
                String key = getRowKey(i);
				if (isType(TABLE_MATRIX)) {
                    if (bPivot) { //pivot
                        v.add(table.getColumnHeader(table.getColumnIndex(key))); //pivot
                    } else { //pivot
                        v.add(table.getRowHeaderMatrix(table.getRowIndex(key)));
                    } //pivot
                } else if (getRowHeight(key) > 0) {
                    if (bPivot) { //pivot
                        v.add(table.getColumnHeader(table.getColumnIndex(key))); //pivot
                    } else { //pivot
                        v.add(table.getRowHeader(table.getRowIndex(key))); //19938
                    } //pivot
                } else if (isNew(table.getRowIndex(key))) { //MN TBD-DK
                    if (bPivot) { //MN TBD-DK
                        v.add(table.getColumnHeader(table.getColumnIndex(key))); //MN TBD-DK
                    } else { //MN TBD-DK
                        v.add(table.getRowHeader(table.getRowIndex(key))); //MN TBD-DK
                    } //MN TBD-DK
                }
            }
            xx = v.size(); //19938
            if (xx <= 0) {
                return null;
            } //19938
            out = new String[xx]; //19938
            //52446		for (int x=0;x<xx;++x)				//19938
            //52446			out[x] = (String)v.get(x);		//19938
            for (int x = 0; x < xx; ++x) { //52446
                out[x] = Routines.truncate((String) v.get(x), 64); //52446
            } //52446
            return out; //19938
        }
        return null;
    }

    /*
     * where used
     */
    /**
     * removeLink
     * @param _s
     * @param _c
     * @author Anthony C. Liberto
     */
    protected void removeLink(String[] _s, Component _c) {
        int ii = _s.length;
        int[] iArray = new int[ii];
        for (int i = 0; i < ii; ++i) {
            if (bPivot) { //pivot
                iArray[i] = table.getColumnIndex(_s[i]); //pivot
            } else { //pivot
                iArray[i] = table.getRowIndex(_s[i]);
            } //pivot
        }
        if (eaccess().dBase().unlink(table, iArray, _c)) {
            synchronize(0, getRowCount(), TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE); //51480
        } //23982
    }

    //51876 	public EANFoundation[] link(String[] _s, EntityItem[] _ei, Component _c) {
    /**
     * link
     * @param _s
     * @param _ei
     * @param _linkType
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected EANFoundation[] link(String[] _s, EntityItem[] _ei, String _linkType, Component _c) { //51876
        int ii = _s.length;
        int[] iArray = new int[ii];
        EANFoundation[] raEan = null;
        for (int i = 0; i < ii; ++i) {
            if (bPivot) { //pivot
                iArray[i] = table.getColumnIndex(_s[i]); //pivot
            } else { //pivot
                iArray[i] = table.getRowIndex(_s[i]);
            } //pivot
        }
        raEan = eaccess().dBase().link(table, iArray, _ei, _linkType, _c);
        updateModel(table, TableModelEvent.INSERT);
        return raEan;
    }

    /**
     * create
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityList create(String _key) {
        //bluePageCreate??
        return eaccess().dBase().create(table, table.getRowIndex(_key));
    }

    //joan.20030625	public WhereUsedList whereUsed(String _key) {
    //joan.20030625		return table.getWhereUsedList(table.getRowIndex(_key),null,getRemoteDatabaseInterface(),getActiveProfile());
    /**
     * whereUsed
     * @param _key
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected WhereUsedList whereUsed(String _key, Component _c) { //joan.20030625
        return eaccess().dBase().getWhereUsedList(table, _key, _c); //joan.20030625
    }

    //joan.20030625	public EANActionItem[] getActionItemsAsArray(String _key) {
    //joan.20030625		return getActionItemsAsArray(table.getRowIndex(_key));
    /**
     * getActionItemsAsArray
     * @param _key
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected EANActionItem[] getActionItemsAsArray(String _key, Component _c) { //joan.20030625
        return getActionItemsAsArray(table.getRowIndex(_key), _c); //joan.20030625
    }

    //joan.20030625	public EANActionItem[] getActionItemsAsArray(int _row=) {
    //joan.20030625		Object[] o = table.getActionItemsAsArray(_row);
    /**
     * getActionItemsAsArray
     * @param _row
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected EANActionItem[] getActionItemsAsArray(int _row, Component _c) { //joan.20030625
        Object[] o = eaccess().dBase().getActionItemsAsArray(table, _row, _c); //joan.20030625
        if (o != null) {
            int ii = o.length;
            EANActionItem[] out = new EANActionItem[ii];
            for (int i = 0; i < ii; ++i) {
                out[i] = (EANActionItem) o[i];
            }
            return out;
        }
        return null;
    }

    /*
     * generic
     */
    /**
     * hasChanges
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean hasChanges() {
        if (table != null) {
            return table.hasChanges();
        }
        return false;
    }

    /**
     * canEdit
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean canEdit() {
        //51298		return table.canEdit();
        return eaccess().isEditable(table); //51298
    }

    /**
     * generatePickList
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityList generatePickList(String _key) {
        int row = table.getRowIndex(_key);
        int col = table.getColumnIndex(_key);
        if (bPivot) { //pivot
            if (col >= 0) { //pivot
                return generatePickList(col); //pivot
            } //pivot
            if (row >= 0) { //pivot
                return generatePickList(row); //pivot
            } //pivot
        } else { //pivot
            if (row >= 0) {
                return generatePickList(row);
            }
            if (col >= 0) {
                return generatePickList(col);
            }
        }
        return null;
        //3.0a		return eaccess().dBase().generatePicklist(table,_key,isType(TABLE_MATRIX));
    }

    /**
     * generatePickList
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityList generatePickList(int _row) {
        return eaccess().dBase().generatePicklist(table, _row, isType(TABLE_MATRIX));
    }

    /**
     * getRemoteDatabaseInterface
     * @return
     * @author Anthony C. Liberto
     * /
    public RemoteDatabaseInterface getRemoteDatabaseInterface() {
        return eaccess().getRemoteDatabaseInterface();
    }

    /**
     * getActiveProfile
     * @return
     * @author Anthony C. Liberto
     */
    protected Profile getActiveProfile() {
        return eaccess().getActiveProfile();
    }

    /**
     * appendLog
     * @param _message
     * @author Anthony C. Liberto
     * /
    public void appendLog(String _message) {
        EAccess.appendLog(_message);
    }

    /**
     * getLockList
     * @return
     * @author Anthony C. Liberto
     */
    protected LockList getLockList() {
        return eaccess().getLockList();
    }

    /**
     * commit
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected Exception commit(Component _c) {
        return eaccess().dBase().commit(table, _c);
    }

    /**
     * dump
     * @param _sb
     * @param _brief
     * @return
     * @author Anthony C. Liberto
     */
    protected StringBuffer dump(StringBuffer _sb, boolean _brief) {
        _sb.append("TableModel.getRowCount(): " + getRowCount() + RETURN);
        _sb.append("TableModel.getColumnCount(): " + getColumnCount() + RETURN);
        if (!_brief) {
            int rr = getRowCount();
            int cc = getColumnCount();
            int lastCol = (cc - 1);
            for (int r = 0; r < rr; ++r) {
                _sb.append("Row(" + r + "): ");
                for (int c = 0; c < cc; ++c) {
                    Object o = getValueAt(r, c);
                    if (o != null) {
                        _sb.append(o.toString());
                    }
                    if (c != lastCol) {
                        _sb.append(", ");
                    }
                }
                _sb.append(RETURN);
            }
            _sb.append(RETURN + "Object..." + RETURN);
            if (object instanceof WhereUsedList) {
                _sb.append("WhereUsedList..." + RETURN);
                _sb.append(((WhereUsedList) object).dump(_brief));
            } else if (object instanceof EntityList) {
                _sb.append("EntityList..." + RETURN);
                _sb.append(((EntityList) object).dump(_brief));
            } else if (object instanceof MatrixList) {
                _sb.append("MatrixList..." + RETURN);
                _sb.append(((MatrixList) object).dump(_brief));
            }
        }
        return _sb;
    }

    /*
     51052
    */
    /*
     tablesort
    	public Comparator getLastComparator() {
    		return lastComparator;
    	}
    */
    /*
     52052
     */
    /**
     * getModelKey
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    protected String getModelKey(int _i) {
        if (bPivot) { //pivot
            return table.getColumnKey(_i); //pivot
        } //pivot
        return table.getRowKey(_i);
    }

    /*
     52345
     */
    /**
     * removeAll
     * @author Anthony C. Liberto
     */
    protected void removeAll() {
        int ii = table.getRowCount();
        for (int i = (ii - 1); i >= 0; --i) {
            table.removeRow(i);
        }
        table.refresh();
        //pivot		synchronize(0,getRowCount(),TableModelEvent.ALL_COLUMNS,TableModelEvent.DELETE);
        synchronize(0, table.getRowCount(), TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE); //pivot
    }

    /*
     51832
     */
    /**
     * removeRows
     * @param _s
     * @author Anthony C. Liberto
     */
    protected void removeRows(String[] _s) {
        if (_s == null) {
            ean = getRowKeyArray();
            modelChanged(TableModelEvent.INSERT);
            sortLast();
        } else {
            int ii = _s.length;
            int rr = ean.length;
            for (int i = 0; i < ii; ++i) {
                for (int r = 0; r < rr; ++r) {
                    if (ean[r] != null) {
                        if (ean[r].getKey().equals(_s[i])) {
                            ean[r] = null;

                        }
                    }
                }
            }
            ean = compact(ean);
            tableModelChanged(0, ean.length - 1, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        }
        return;
    }

    private EANFoundation[] compact(EANFoundation[] _ean) {
        if (_ean != null) {
            Vector v = new Vector();
            int ii = _ean.length;
            for (int i = 0; i < ii; ++i) {
                if (_ean[i] != null) {
                    v.add(_ean[i]);
                }
            }
            if (!v.isEmpty()) {
                return (EANFoundation[]) v.toArray(new EANFoundation[v.size()]);
            }
        }
        return null;
    }

    /*
     52624
     */
    /**
     * setSearch
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setSearch(boolean _b) {
        bSearch = _b;
    }

    /**
     * isSearch
     * @return
     * @author Anthony C. Liberto
     */
    private boolean isSearch() {
        return bSearch;
    }

    /*
     MN_18698482
    */
    /**
     * duplicateRow
     * @param _row
     * @param _copies
     * @author Anthony C. Liberto
     */
    protected void duplicateRow(int _row, int _copies) {
        table.duplicateRow(_row, _copies);
    }

    /*
     5ZPTCX.2
     */
    /**
     * link
     * @param _lai
     * @param _parent
     * @param _child
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected Object link(LinkActionItem _lai, EntityItem[] _parent, EntityItem[] _child, Component _c) {
        Object o = eaccess().dBase().link(table, _lai, _parent, _child, _c);
        updateModel(table, TableModelEvent.INSERT);
        return o;
    }

    /*
     relator_edit
     */
    /**
     * isChild
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isChild(int _r, int _c) {
        boolean out = false;
        if (table != null) {
            if (bPivot) { //pivot
                out = table.isChildAttribute(_c, _r); //pivot
            } else { //pivot
                out = table.isChildAttribute(_r, _c);
            } //pivot
        }
        return out;
    }

    /**
     * isParent
     * @param _r
     * @param _c
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isParent(int _r, int _c) {
        boolean out = false;
        if (table != null) {
            if (bPivot) { //pivot
                out = table.isParentAttribute(_c, _r); //pivot
            } else { //pivot
                out = table.isParentAttribute(_r, _c);
            } //pivot
        }
        return out;
    }
    /*
     TIR USRO-R-SWWE-629MHH
     */
    /**
     * isIndicateRelations
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isIndicateRelations() {
        return false;
    }

    /*
     dwb_20040726
     */
    /**
     * lockRows
     * @param _r
     * @param _ll
     * @param _prof
     * @author Anthony C. Liberto
     */
    protected void lockRows(int[] _r, LockList _ll, Profile _prof) {
       //Profile prof = getActiveProfile();
        try {
            //EntityItem lockOwnerEI = new EntityItem(null, prof, Profile.OPWG_TYPE, prof.getOPWGID());
            EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
            eaccess().dBase().lock(table, _r, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * unlockRows
     * @param _r
     * @param _ll
     * @param _prof
     * @author Anthony C. Liberto
     */
    protected void unlockRows(int[] _r, LockList _ll, Profile _prof) {
      //  try {
            EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
            	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getActiveProfile().getOPWGID());
            if (lockOwnerEI != null){
            	eaccess().dBase().unlock(table, _r, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
            }
        //} catch (MiddlewareRequestException _mre) {
          //  _mre.printStackTrace();
        //}
    }

    /*
     tablesort
     */
    /**
     * sortLast
     * @author Anthony C. Liberto
     */
    protected void sortLast() {
        sort(sDetails);
    }

    /**
     * sort
     * @param _detail
     * @author Anthony C. Liberto
     */
    private void sort(SortDetail _detail) {
        if (_detail != null) {
            sortTable(_detail.getColumns(), _detail.getDirection(), iType);
        }
    }

    /**
     * getSortDetails
     * @return
     * @author Anthony C. Liberto
     */
    protected SortDetail getSortDetails() {
        return sDetails;
    }

    /**
     * sortTable
     *
     * @param _i
     * @param _d
     * @param _iType
     * @author Anthony C. Liberto
     */
    protected void sortTable(int[] _i, boolean[] _d, int _iType) {
        if (table != null && _i != null && _d != null) {
            if (_i.length == 1 && _i[0] == -1) {
                if (bPivot) {
                    pivotHeaderSort(_i, _d, _iType == TABLE_MATRIX);
                } else {
                    headerSort(_i, _d, _iType == TABLE_MATRIX);
                }
            } else {
                table.bSort(_i, _d, _iType);
            }
            refreshModel();
        }
    }

    /**
     * refreshModel
     * @author Anthony C. Liberto
     */
    private void refreshModel() {
        ean = getRowKeyArray();
        modelChanged();
    }

    private boolean hasRows() {
        if (table != null) {
            return (table.getRowCount() > 0);
        }
        return false;
    }

    /*
     TIR USRO-R-DWES-66UMGM
     */
    /**
     * hasTable
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean hasTable() {
        return table != null;
    }

    /*
     6554
     */
    /**
     * edit
     * @param _keys
     * @return
     * @author Anthony C. Liberto
     */
    protected EntityList edit(String[] _keys) {
        if (_keys != null) {
            int ii = _keys.length;
            int[] rows = new int[ii];
            for (int i = 0; i < ii; ++i) {
                if (bPivot) { //pivot
                    rows[i] = table.getColumnIndex(_keys[i]); //pivot
                } else { //pivot
                    rows[i] = table.getRowIndex(_keys[i]);
                } //pivot
            }
            return eaccess().dBase().edit(table, rows);
        }
        return null;
    }

    /*
     pivot
     */
    /**
     * isPivot
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isPivot() {
        return bPivot;
    }

    /**
     * setPivot
     * @param _b
     * @author Anthony C. Liberto
     */
    protected void setPivot(boolean _b) {
        if (bPivot != _b) {
            bPivot = _b;
            modelChanged();
        }
    }

    /**
     * pivot
     * @author Anthony C. Liberto
     */
    protected void pivot() {
        setPivot(!bPivot);
    }

    /**
     * lockColumn
     * @param _r
     * @param _ll
     * @param _prof
     * @author Anthony C. Liberto
     * /
    public void lockColumn(int _r, LockList _ll, Profile _prof) {
        Profile prof = getActiveProfile();
        try {
            EntityItem lockOwnerEI = new EntityItem(null, prof, Profile.OPWG_TYPE, prof.getOPWGID());
            eaccess().dBase().lock(table, _r, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * unlockColumn
     * @param _r
     * @param _ll
     * @param _prof
     * @author Anthony C. Liberto
     * /
    public void unlockColumn(int _r, LockList _ll, Profile _prof) {
        //try {
            EntityItem lockOwnerEI = eaccess().getLockOwner(); // use hashtable if possible
            	//new EntityItem(null, getActiveProfile(), Profile.OPWG_TYPE, getActiveProfile().getOPWGID());
            if (lockOwnerEI != null){
            	eaccess().dBase().unlock(table, _r, _ll, _prof, lockOwnerEI, LockGroup.LOCK_NORMAL);
            }
        //} catch (MiddlewareRequestException _mre) {
        //    _mre.printStackTrace();
        //}
    }

    /**
     * removeColumn
     * @param _r
     * @author Anthony C. Liberto
     * /
    public void removeColumn(int _r) {
//        String key = table.getRowKey(_r);
        table.removeRow(_r);
        synchronize(_r, _r, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
    }

    /**
     * removeColumns
     * @param _r
     * @author Anthony C. Liberto
     * /
    public void removeColumns(int[] _r) {
        int ii = _r.length;
        for (int i = 0; i < ii; ++i) {
            table.removeRow(_r[i]);
        }
        table.refresh();
        synchronize(0, getRowCount(), TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE);
        return;
    }*/

    private void pivotHeaderSort(int[] _i, boolean[] _d, boolean _b) {
        EANFoundation[] tmpEan = table.getTableColumnsAsArray();
        EComparator comp = new EComparator(_d[0]) {
            public Object getObject(Object _o, int _index) {
                if (_o != null && _o instanceof EANFoundation) {
                    return table.getColumnHeader((EANFoundation) _o);
                }
                return null;
            }
        };
        Arrays.sort(tmpEan, comp);
        table.synchronizeColumns(tmpEan);
        return;
    }

    private void headerSort(int[] _i, boolean[] _d, boolean _b) {
        EANFoundation[] tmpEan = getRowKeyArray();
        TableComparator headCompare = new TableComparator(_i[0], _d[0]);
        headCompare.setTable(table);
        headCompare.setType(iType);
        Arrays.sort(tmpEan, headCompare);
        table.synchronizeRows(tmpEan);
        return;
    }
/*
 filter_col
 */
    /**
     * getColFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    protected FilterGroup getColFilterGroup() {
        if (table != null) {
            FilterGroup fg = table.getColFilterGroup();
            if (fg == null) {
                try {
                    fg = new FilterGroup(null, eaccess().getActiveProfile(), table,true);
                    //System.out.println("RSTableModel.fg2.isColumnFilter: " + fg.isColumnFilter());
                    setColFilterGroup(fg);
                } catch (MiddlewareRequestException _mre) {
                    _mre.printStackTrace();
                }
            }
            return fg;
        }
        return null;
    }

    /**
     * setColFilterGroup
     * @param _group
     * @author Anthony C. Liberto
     */
    protected void setColFilterGroup(FilterGroup _group) {
        if (table != null) {
            table.setColFilterGroup(_group);
        }
    }
}
