//
// Copyright (c) 2003, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: AbstractTableModel.java,v $
// Revision 1.21  2010/02/21 18:22:08  wendy
// Add dereference
//
// Revision 1.20  2008/01/31 22:56:01  wendy
// Cleanup RSA warnings
//
// Revision 1.19  2005/02/09 22:25:42  dave
// some comment changes
//
// Revision 1.18  2005/02/09 22:21:18  dave
// more Jtest Cleanup
//
// Revision 1.17  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.16  2005/02/01 21:38:39  gregg
// setAllColsVisible
//
// Revision 1.15  2003/06/19 23:14:12  gregg
// remove replaceRow/ColObject methods
//
// Revision 1.14  2003/06/10 22:44:03  gregg
// sync w/ 111
//
// Revision 1.13  2003/06/10 19:36:40  gregg
// replaceRowObject, replaceColumnObject (sync w/ v111)
//
// Revision 1.12  2003/05/11 02:04:39  dave
// making EANlists bigger
//
// Revision 1.11  2003/04/01 18:24:38  gregg
// deletes+sorts
//
// Revision 1.10  2003/04/01 00:26:07  gregg
// getRowIndex
//
// Revision 1.9  2003/03/31 22:27:54  gregg
// updates for commit,delete table methods
//
// Revision 1.8  2003/03/31 21:53:43  gregg
// class cast fix for putTableRow
//
// Revision 1.7  2003/03/22 00:48:40  gregg
// getColumnIndex method
//
// Revision 1.6  2003/03/13 22:51:11  gregg
// rollback
//
// Revision 1.5  2003/03/12 22:06:15  gregg
// isCellEditable method + moved all common table logic up into Abstract
//
// Revision 1.4  2003/03/10 21:00:10  gregg
// refreshTable() method --> refresh() for consistency
//
// Revision 1.3  2003/03/10 17:49:14  gregg
// inner classes implement Serializable
//
// Revision 1.2  2003/03/08 00:49:41  gregg
// end-of-the-day commit (lots-o-changes)
//
// Revision 1.1  2003/03/07 22:13:19  gregg
// initial load
//
//

package COM.ibm.eannounce.objects;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.io.Serializable;

/**
 * AbstractTableModel
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractTableModel implements Serializable {

    /**************************************************
     *  Inner-Class Definitions TableRow, TableColumn *
     *  This is an abstract Class so these guys have to be protected 
     *  to be reused
     **************************************************/
    protected class TableRow implements Comparable, Serializable {
        static final long serialVersionUID = 1L;
        private boolean m_bVisible = true;
        private int m_iAbsoluteIndex = -1;
        private EANObject m_objRef = null;

        void dereference(){
        	m_objRef = null;
        }
        /**
         * TableRow
         *
         * @param _objRef
         * @param _iAbsoluteIndex
         *  @author David Bigelow
         */
        TableRow(EANObject _objRef, int _iAbsoluteIndex) {
            m_objRef = _objRef;
            m_iAbsoluteIndex = _iAbsoluteIndex;
        }
        /**
         * Comparable inrelation to other TableRow's indexes. Object comparing to MUST be a TableRow.
         *
         * @param _obj
         * @return int
         */
        public int compareTo(Object _obj) {
            TableRow compRow = (TableRow) _obj;
            if (this.getAbsoluteIndex() < compRow.getAbsoluteIndex()) {
                return -1;
            } else if (this.getAbsoluteIndex() > compRow.getAbsoluteIndex()) {
                return 1;
            }
            // else equals
            return 0;
        }
        /**
         * getKey
         *
         * @return
         *  @author David Bigelow
         */
        public String getKey() {
            return getObject().getKey();
        }
        /**
         * getObject
         *
         * @return
         *  @author David Bigelow
         */
        public EANObject getObject() {
            return m_objRef;
        }
        /**
         * getAbsoluteIndex
         *
         * @return
         *  @author David Bigelow
         */
        public int getAbsoluteIndex() {
            return m_iAbsoluteIndex;
        }
        /**
         * isVisible
         *
         * @return
         *  @author David Bigelow
         */
        public boolean isVisible() {
            return m_bVisible;
        }
        /**
         * setAbsoluteIndex
         *
         * @param _i
         *  @author David Bigelow
         */
        public void setAbsoluteIndex(int _i) {
            m_iAbsoluteIndex = _i;
        }
        /**
         * setVisible
         *
         * @param _b
         *  @author David Bigelow
         */
        public void setVisible(boolean _b) {
            m_bVisible = _b;
        }

    }
    
    /**
     * Manage our Column properties
     *  This is an abstract Class so these guys have to be protected 
     *  to be reused
     */
    protected class TableColumn implements Comparable, Serializable {
        static final long serialVersionUID = 1L;
        private boolean m_bVisible = true;
        private int m_iAbsoluteIndex = -1;
        private EANObject m_objRef = null;
        void dereference(){
        	m_objRef = null;
        }
        /**
         * TableColumn
         *
         * @param _objRef
         * @param _iAbsoluteIndex
         *  @author David Bigelow
         */
        TableColumn(EANObject _objRef, int _iAbsoluteIndex) {
            m_objRef = _objRef;
            m_iAbsoluteIndex = _iAbsoluteIndex;
        }
        /**
         * Comparable inrelation to other TableColumn's indexes. Object comparing to MUST be a TableColumn.
         *
         * @param _obj
         * @return int
         */
        public int compareTo(Object _obj) {
            TableColumn compCol = (TableColumn) _obj;
            if (this.getAbsoluteIndex() < compCol.getAbsoluteIndex()) {
                return -1;
            } else if (this.getAbsoluteIndex() > compCol.getAbsoluteIndex()) {
                return 1;
            }
            // else equals
            return 0;
        }
        /**
         * getKey
         *
         * @return
         *  @author David Bigelow
         */
        public String getKey() {
            return getObject().getKey();
        }
        /**
         * getObject
         *
         * @return
         *  @author David Bigelow
         */
        public EANObject getObject() {
            return m_objRef;
        }
        /**
         * getAbsoluteIndex
         *
         * @return
         *  @author David Bigelow
         */
        public int getAbsoluteIndex() {
            return m_iAbsoluteIndex;
        }
        /**
         * isVisible
         *
         * @return
         *  @author David Bigelow
         */
        public boolean isVisible() {
            return m_bVisible;
        }
        /**
         * setAbsoluteIndex
         *
         * @param _i
         *  @author David Bigelow
         */
        public void setAbsoluteIndex(int _i) {
            m_iAbsoluteIndex = _i;
        }
        /**
         * setVisible
         *
         * @param _b
         *  @author David Bigelow
         */
        public void setVisible(boolean _b) {
            m_bVisible = _b;
        }

    }
    /*******************************************
     *  End Inner-Classes                      *
     *******************************************/
    /**
    * @serial
    */
    static final long serialVersionUID = 1L;
    // Hashtables maintain a 'master' list
    private Hashtable m_hashRows = null;
    private Hashtable m_hashCols = null;
    // Vectors are what we see as a 'visible' table AND in order
    private Vector m_vctVisRows = null;
    private Vector m_vctVisCols = null;

    public void dereference(){
    	if (m_vctVisRows != null){
    		m_vctVisRows.clear();
    		m_vctVisRows = null;
    	}
     	if (m_vctVisCols != null){
     		m_vctVisCols.clear();
     		m_vctVisCols = null;
    	}
     	if (m_hashRows!=null){
     		for (Enumeration e = m_hashRows.elements(); e.hasMoreElements();){
     			TableRow tr = (TableRow)e.nextElement(); 
     			tr.dereference();
     		}
     		m_hashRows.clear();
     		m_hashRows = null;
     	}
     	if (m_hashCols!=null){
     		for (Enumeration e = m_hashCols.elements(); e.hasMoreElements();){
     			TableColumn tr = (TableColumn)e.nextElement(); 
     			tr.dereference();
     		}
     		m_hashCols.clear();
     		m_hashCols = null;
     	}
    }
    /**
     * AbstractTableModel
     *
     * @param _elRows
     * @param _elCols
     *  @author David Bigelow
     */
    public AbstractTableModel(EANList _elRows, EANList _elCols) {
        buildTable(_elRows, _elCols);
    }

    /**
     * Encapsulate all Table-building here so we can rebuild on rollback
     *
     * @param _elRows
     * @param _elCols 
     */
    protected void buildTable(EANList _elRows, EANList _elCols) {
        m_hashRows = new Hashtable(_elRows.size());
        m_hashCols = new Hashtable(_elCols.size());
        m_vctVisRows = new Vector();
        m_vctVisCols = new Vector();
        //setup rows
        for (int i = 0; i < _elRows.size(); i++) {
            putRow(_elRows.getAt(i));
        }
        //setup cols
        for (int i = 0; i < _elCols.size(); i++) {
            putCol(_elCols.getAt(i));
        }
        m_vctVisRows = orderRowsByAbsIndex(m_vctVisRows);
        m_vctVisCols = orderColsByAbsIndex(m_vctVisCols);
    }

    /******************
     * public methods *
     ******************/

    ///// TOOK THESE FROM SIMPLE TABLE MODEL (MOVED 'EM DOWN A LEVEL ).....///////

    /**
     * This is really just toString() on get(_iRow, _iCol)
     *
     * @param _iRow
     * @param _iCol
     * @return String
     */
    public String getValueAt(int _iRow, int _iCol) {
        EANFoundation ef = get(_iRow, _iCol);
        if (ef == null) {
            return null;
        }
        return ef.toString();
    }

    /**
     * Is the indicated cell Editable?
     *
     * @param _iRowIndex
     * @param _iColumnIndex
     * @return boolean
     */
    public boolean isCellEditable(int _iRowIndex, int _iColumnIndex) {
        String strColKey = null;
        EANTableRowTemplate etrt = getRow(_iRowIndex);
        if (etrt == null) {
            return false;
        }
        strColKey = getColumnKey(_iColumnIndex);
        return etrt.isEditable(strColKey);
    }

    /**
     * get
     *
     * @param _iRow
     * @param _iCol
     * @return
     *  @author David Bigelow
     */
    public EANFoundation get(int _iRow, int _iCol) {
        EANTableRowTemplate etrt = getRow(_iRow);
        return etrt.getEANObject(getColumnKey(_iCol));
    }

    /**
     * getColumn
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EANFoundation getColumn(int _i) {
        return (EANFoundation) getVisibleColObjectAt(_i);
    }

    /**
     * getColumn
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public EANFoundation getColumn(String _strKey) {
        return (EANFoundation) getColObject(_strKey);
    }

    /**
     * getColumnClass
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public Class getColumnClass(int _i) {
        return getColumn(_i).getClass();
    }

    /**
     * getColumnHeader
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getColumnHeader(int _i) {
        return getColumn(_i).getLongDescription();
    }

    /**
     * getColumnKey
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getColumnKey(int _i) {
        return getColumn(_i).getKey();
    }

    /**
     * getColumnName
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getColumnName(int _i) {
        return getColumnKey(_i);
    }

    /**
     * The index of the column.
     *
     * @return the visible index of the column, or -1 if not found.
     * @param _strKey 
     */
    public int getColumnIndex(String _strKey) {
        // index of VISIBLE column
        for (int i = 0; i < getColumnCount(); i++) {
            if (getColumn(i).getKey().equals(_strKey)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * The index of the row.
     *
     * @return the visible index of the row, or -1 if not found.
     * @param _strKey 
     */
    public int getRowIndex(String _strKey) {
        // index of VISIBLE column
        for (int i = 0; i < getRowCount(); i++) {
            if (getRow(i).getKey().equals(_strKey)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * getRow
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public EANTableRowTemplate getRow(int _i) {
        return (EANTableRowTemplate) getVisibleRowObjectAt(_i);
    }

    /**
     * getRow
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    public EANTableRowTemplate getRow(String _strKey) {
        return (EANTableRowTemplate) getRowObject(_strKey);
    }

    /**
     * getRowKey
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public String getRowKey(int _i) {
        return getRow(_i).getKey();
    }

    ////////////////////////////////////////////////////////////////////////////

    /**
     * getRowList
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getRowList() {
        EANList elRows = new EANList(EANMetaEntity.LIST_SIZE);
        for (int i = 0; i < getVisibleTableRows().size(); i++) {
            elRows.put(((TableRow) getVisibleTableRows().elementAt(i)).getObject());
        }
        return elRows;
    }

    /**
     * getColumnList
     *
     * @return
     *  @author David Bigelow
     */
    public EANList getColumnList() {
        EANList elCols = new EANList(EANMetaEntity.LIST_SIZE);
        for (int i = 0; i < getVisibleTableColumns().size(); i++) {
            elCols.put(((TableColumn) getVisibleTableColumns().elementAt(i)).getObject());
        }
        return elCols;
    }

    /**
     * getRowCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getRowCount() {
        return getVisibleTableRows().size();
    }

    /**
     * getColumnCount
     *
     * @return
     *  @author David Bigelow
     */
    public int getColumnCount() {
        return getVisibleTableColumns().size();
    }

    /**
     * setColVisible
     *
     * @param _strKey
     * @param _bVisible
     *  @author David Bigelow
     */
    public void setColVisible(String _strKey, boolean _bVisible) {
        TableColumn col = (TableColumn) getAllTableColumns().get(_strKey);
        if (col == null) {
            return;
        }
        col.setVisible(_bVisible);
    }

    /**
     * setAllRowsVisible
     *
     *  @author David Bigelow
     */
    public void setAllRowsVisible() {
        Enumeration enumRows = getAllTableRows().elements();
        while (enumRows.hasMoreElements()) {
            TableRow row = (TableRow) enumRows.nextElement();
            row.setVisible(true);
        }
    }

    /**
     * setAllColsVisible
     *
     *  @author David Bigelow
     */
    public void setAllColsVisible() {
        Enumeration enumCols = getAllTableColumns().elements();
        while (enumCols.hasMoreElements()) {
            TableColumn col = (TableColumn) enumCols.nextElement();
            col.setVisible(true);
        }
    }

    /**
     * no need for 'refresh()' on this guy!! We are directly manipulating our visible list.
     *
     * @concurrency $none
     * @param _iOld
     * @param _iNew 
     */
    public synchronized void moveRow(int _iOld, int _iNew) {
        //** Basically, we need to be aware that this list could be filtered (hidden rows), in which case,
        //   row@index 'x' may NOT have an absolute index of 'x'.
        //   So, we need to keep the same set of absolute idexes in our visible list - by doing some swapping and
        //   re-indexing (of absolute indexes). Remember, we can only swap absolute indexes of Rows in our
        //   VISIBLE list.
        //
        //   This way, when the list becomes 'unfiltered', the previously filtered Rows will show up in the right
        //   place -- between swapped Rows.
        //
        //   --> Save off all absolute indexes in our DISPLAYABLE list
        int[] arrayAbsoluteIndexes = new int[getVisibleTableRows().size()];
        for (int iRow = 0; iRow < getVisibleTableRows().size(); iRow++) {
            arrayAbsoluteIndexes[iRow] = getVisibleTableRow(iRow).getAbsoluteIndex();
        }
        //NOW, we can do our tricky remove, insertElementAt, a la Tony's proven code...
        getVisibleTableRows().insertElementAt(getVisibleTableRows().remove(_iOld), _iNew);
        //...ok, now re-apply our OG absolute indexes...
        for (int iRow = 0; iRow < getVisibleTableRows().size(); iRow++) {
            getVisibleTableRow(iRow).setAbsoluteIndex(arrayAbsoluteIndexes[iRow]);
        }
    }

    /************************
     * protected sub-routines *
     *
     * @param _i
     * @return EANObject
     ************************/

    protected EANObject getVisibleRowObjectAt(int _i) {
        return getVisibleTableRow(_i).getObject();
    }

    /**
     * getVisibleColObjectAt
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected EANObject getVisibleColObjectAt(int _i) {
        return getVisibleTableColumn(_i).getObject();
    }

    // WILL grab hidden Rows
    /**
     * getRowObject
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    protected EANObject getRowObject(String _strKey) {
        TableRow row = (TableRow) getAllTableRows().get(_strKey);
        if (row == null) {
            return null;
        }
        return row.getObject();
    }

    // WILL grab hidden Cols
    /**
     * getColObject
     *
     * @param _strKey
     * @return
     *  @author David Bigelow
     */
    protected EANObject getColObject(String _strKey) {
        TableColumn col = (TableColumn) getAllTableColumns().get(_strKey);
        if (col == null) {
            return null;
        }
        return col.getObject();
    }

    /**
     * removeTableRow
     *
     * @param _row
     *  @author David Bigelow
     */
    protected void removeTableRow(TableRow _row) {
        // 1) remove from master list...
        getAllTableRows().remove(_row.getKey());
        // 2) refresh 'coz removes are nasty
        refresh();
    }

    /**
     * removeTableColumn
     *
     * @param _col
     *  @author David Bigelow
     */
    protected void removeTableColumn(TableColumn _col) {
        // 1) remove from master list...
        getAllTableColumns().remove(_col.getKey());
        // 2) refresh 'coz removes are nasty
        refresh();
    }

    /**
     * Replace the underlying TableRow's object WITHOUT changing any row properties.
     * Do nothing if row not found.
     */
    /*
       protected void replaceRowObject(String _strOldKey, EANObject _newObjRef) {
           TableRow row = (TableRow)getAllTableRows().get(_strOldKey);
           if(row == null) {
               return;
           }
           // key might change...
           getAllTableRows().remove(row);
           row.setObject(_newObjRef);
           getAllTableRows().put(row.getKey(),row);
           //
       }
       */

    /**
     * Replace the underlying TableColumn's object WITHOUT changing any column properties.
     * Do nothing if row not found.
     *
     * @param _obj 
     */
    /*
       protected void replaceColumnObject(String _strOldKey, EANObject _newObjRef) {
           TableColumn col = (TableColumn)getAllTableColumns().get(_strOldKey);
           if(col == null) {
               return;
           }
           col.setObject(_newObjRef);
           // key might change...
           getAllTableColumns().remove(col);
           col.setObject(_newObjRef);
           getAllTableColumns().put(col.getKey(),col);
           //
       }
       */

    /////
    // EANObject flavors
    /////
    protected void putRow(EANObject _obj) {
        TableRow row = new TableRow(_obj, getAllTableRows().size());
        putTableRow(row);
    }

    /**
     * putCol
     *
     * @param _obj
     *  @author David Bigelow
     */
    protected void putCol(EANObject _obj) {
        TableColumn col = new TableColumn(_obj, getAllTableColumns().size());
        putTableColumn(col);
    }

    /////
    // TableRow/Col flavors
    /////
    /**
     * putTableRow
     *
     * @param _row
     *  @author David Bigelow
     */
    protected void putTableRow(TableRow _row) {
        getAllTableRows().put(_row.getKey(), _row);
        if (_row.isVisible()) {
            getVisibleTableRows().addElement(_row);
        }
    }

    /**
     * putTableColumn
     *
     * @param _col
     *  @author David Bigelow
     */
    protected void putTableColumn(TableColumn _col) {
        getAllTableColumns().put(_col.getKey(), _col);
        if (_col.isVisible()) {
            getVisibleTableColumns().addElement(_col);
        }
    }

    /**
     * getVisibleTableRow
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected TableRow getVisibleTableRow(int _i) {
        return (TableRow) getVisibleTableRows().elementAt(_i);
    }

    /**
     * getVisibleTableColumn
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    protected TableColumn getVisibleTableColumn(int _i) {
        return (TableColumn) getVisibleTableColumns().elementAt(_i);
    }

    /**
     * getVisibleTableRows
     *
     * @return
     *  @author David Bigelow
     */
    protected Vector getVisibleTableRows() {
        return m_vctVisRows;
    }

    /**
     * getVisibleTableColumns
     *
     * @return
     *  @author David Bigelow
     */
    protected Vector getVisibleTableColumns() {
        return m_vctVisCols;
    }

    /**
     * getAllTableRows
     *
     * @return
     *  @author David Bigelow
     */
    protected Hashtable getAllTableRows() {
        return m_hashRows;
    }

    /**
     * getAllTableColumns
     *
     * @return
     *  @author David Bigelow
     */
    protected Hashtable getAllTableColumns() {
        return m_hashCols;
    }

    /*********
     *
     *********/

    /**
     * Apply any changes made to the table.
     * - what's visible
     * - order by indexes
     */
    public void refresh() {
        Enumeration enumRows = getAllTableRows().elements();
        Enumeration enumCols = getAllTableColumns().elements();
        // 1) reset visible vectors
        m_vctVisRows = new Vector();
        m_vctVisCols = new Vector();
        // 2) recalculate absolute indexes (something could have been removed, etc...)
        recalcAbsoluteRowIndexes();
        // 3) go through and add ONLY visible rows/cols
        while (enumRows.hasMoreElements()) {
            TableRow row = (TableRow) enumRows.nextElement();
            if (row.isVisible()) {
                getVisibleTableRows().addElement(row);
            }
        }
        while (enumCols.hasMoreElements()) {
            TableColumn col = (TableColumn) enumCols.nextElement();
            if (col.isVisible()) {
                getVisibleTableColumns().addElement(col);
            }
        }
        // 4) now order visible
        m_vctVisRows = orderRowsByAbsIndex(m_vctVisRows);
        m_vctVisCols = orderColsByAbsIndex(m_vctVisCols);
    }

    /**
     * Order our visible rows by their respective indexes.
     */
    private Vector orderRowsByAbsIndex(Vector _vctRows) {
        TableRow[] rowsArray = new TableRow[_vctRows.size()];
        _vctRows.copyInto(rowsArray);
        Arrays.sort(rowsArray);
        _vctRows = new Vector(rowsArray.length);
        for (int i = 0; i < rowsArray.length; i++) {
            _vctRows.addElement(rowsArray[i]);
        }
        return _vctRows;
    }

    /**
     * Order our visible columns by their respective indexes.
     */
    private Vector orderColsByAbsIndex(Vector _vctCols) {
        TableColumn[] colsArray = new TableColumn[_vctCols.size()];
        _vctCols.copyInto(colsArray);
        Arrays.sort(colsArray);
        _vctCols = new Vector(colsArray.length);
        for (int i = 0; i < colsArray.length; i++) {
            _vctCols.addElement(colsArray[i]);
        }
        return _vctCols;
    }

    /**
     * Re-order our absolute indexes from 0-->size()-1
     */
    private void recalcAbsoluteRowIndexes() {
        // 1) get ALL rows as unordered Vector
        Vector vctAllRows = toVector(getAllTableRows());
        // 2) order this Vector by Rows' Absolute Indexes -- in case of remove, these could be missing some, but they
        //    will fall in sequential order!
        vctAllRows = orderRowsByAbsIndex(vctAllRows);
        // 3) now set Absolute Indexes according to REAL Row order
        for (int i = 0; i < vctAllRows.size(); i++) {
            // should carry through to hashtable by reference...
            ((TableRow) vctAllRows.elementAt(i)).setAbsoluteIndex(i);
        }
    }

    /**
     * Get a Hashtable in the form of a Vector
     */
    private Vector toVector(Hashtable _hashtable) {
        Vector v = new Vector();
        Enumeration eNum = _hashtable.elements();
        while (eNum.hasMoreElements()) {
            v.addElement(eNum.nextElement());
        }
        return v;
    }

}
