/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: BookmarkTable.java,v $
 * Revision 1.3  2009/04/08 20:00:11  wendy
 * Sort needs more than column name to find the attribute
 *
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:11  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.12  2005/04/05 17:29:50  tony
 * MN_23318121
 *
 * Revision 1.11  2005/03/28 18:54:53  tony
 * 23318121 -- sort on converted column index
 *
 * Revision 1.10  2005/02/09 18:55:24  tony
 * Scout Accessibility
 *
 * Revision 1.9  2005/02/03 21:26:13  tony
 * JTest Format Third Pass
 *
 * Revision 1.8  2005/02/01 22:26:40  tony
 * ColumnFilter
 *
 * Revision 1.7  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.5  2005/01/18 19:04:05  tony
 * pivot modifications
 *
 * Revision 1.4  2005/01/14 20:32:12  tony
 * pivot
 *
 * Revision 1.3  2004/08/11 21:47:42  tony
 * 5ZKL3K performance enhancement.
 *
 * Revision 1.2  2004/08/11 21:24:24  tony
 * 5ZKL3K
 *
 * Revision 1.1.1.1  2004/02/10 16:59:44  tony
 * This is the initial load of OPICM
 *
 * Revision 1.13  2004/01/09 00:42:44  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.12  2004/01/05 17:00:51  tony
 * acl_20040104
 * updated logic to prevent null pointer on close tab.
 *
 * Revision 1.11  2003/11/25 22:29:56  tony
 * accessibility
 *
 * Revision 1.10  2003/11/11 00:42:21  tony
 * accessibility update, added convenience method to table.
 *
 * Revision 1.9  2003/06/19 23:13:40  tony
 * 51217
 *
 * Revision 1.8  2003/06/13 17:32:52  tony
 * 51255 resize on refresh issue.
 *
 * Revision 1.7  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.6  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import com.ibm.eannounce.eobjects.SortColumn;
import com.ibm.eannounce.erend.*;
import com.ibm.eannounce.exception.*;
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
public class BookmarkTable extends GTable implements ETable {
	private static final long serialVersionUID = 1L;
	private static BookmarkTableModel tm = new BookmarkTableModel();
    /**
     * foundItem
     */
    private EVector foundItem = new EVector();
    private FoundSingleRenderer foundRend = new FoundSingleRenderer();

    /**
     * bookmarkTable
     * @author Anthony C. Liberto
     */
    public BookmarkTable() {
        super(tm);
        init();
        return;
    }

    /**
     * init
     *
     * @author Anthony C. Liberto
     */
    public void init() {
        setUseDefined(true);
        setShowGrid(false);
        setRowMargin(0);
        initAccessibility("accessible.bookTable");
        //		setBorder(new LineBorder(Color.black,1));
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(true);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        getTableHeader().setReorderingAllowed(true);
        //50364		setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //50364
        setDefaultRenderer(Object.class, new SingleRenderer());
        return;
    }

    /**
     * refreshAppearance
     * @author Anthony C. Liberto
     */
    public void refreshAppearance() {
        setRowHeight(getFont());
        resizeCells();
        return;
    }

    /**
     * updateModel
     * @param _ctm
     * @author Anthony C. Liberto
     */
    public void updateModel(BookmarkGroupTable _ctm) {
        FilterGroup fg = null;
        if (tm != null && tm.isFiltered()) {
            fg = tm.getFilterGroup();
        }
        tm.updateModel(_ctm);
        createDefaultColumnsFromModel();
        resizeCells();
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
        }
        if (getColumnCount() > 0) {
            setColumnSelectionInterval(0, 0);
        }
        if (fg != null) {
            tm.setFilterGroup(fg);
            filter();
        }
        return;
    }

    /**
     * getBookmarkItem
     * @return
     * @author Anthony C. Liberto
     */
    public BookmarkItem getBookmarkItem() {
        return getBookmarkItem(getSelectedRow());
    }

    /**
     * getBookmarkItem
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public BookmarkItem getBookmarkItem(int _row) {
        if (tm != null) {
            if (_row >= 0 && _row < getRowCount()) {
                Object o = tm.getRow(_row);
                if (o != null && o instanceof BookmarkItem) {
                    return (BookmarkItem) o;
                }
            }
        }
        return null;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#dereference()
     * @author Anthony C. Liberto
     */
    public void dereference() {
        tm.dereference();
        tm = null;
        return;
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
        if (_e != null && _e instanceof MouseEvent) {
            if (((MouseEvent) _e).getClickCount() == 2) {
                doubleClick();
            }
        }
        return false;
    }
    /**
     * @see javax.swing.JTable#changeSelection(int, int, boolean, boolean)
     * @author Anthony C. Liberto
     */
    public void changeSelection(int _row, int _col, boolean _toggle, boolean _extend) {
        selectClick();
        super.changeSelection(_row, _col, _toggle, _extend);
        return;
    }

    /**
     * checkChanges
     * @author Anthony C. Liberto
     */
    public void checkChanges() {
    }
    /**
     * doubleClick
     * @author Anthony C. Liberto
     */
    public void doubleClick() {
    }
    /**
     * selectClick
     * @author Anthony C. Liberto
     */
    public void selectClick() {
    }

    /**
     * isValidSelection
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isValidSelection() {
        int index = getSelectedRow();
        if (index >= 0 && index < getRowCount()) {
            return true;
        }
        return false;
    }
    /*
     cr_1210035324
    	public boolean addBookmarkItem(EANActionItem _item, String _desc) {
    		if (tm.addBookmarkItem(_item,_desc)) {
    			refreshTable(true);
    			return true;
    		}
    		return false;
    	}
    */
    /**
     * removeBookmark
     * @param _item
     * @author Anthony C. Liberto
     */
    public void removeBookmark(BookmarkItem[] _item) {
        return;
    }

    /**
     * removeBookmark
     * @param _item
     * @author Anthony C. Liberto
     */
    public void removeBookmark(BookmarkItem _item) {
        if (tm.deleteRow(_item.getKey())) {
            refreshTable(true);
        }
        return;
    }

    /**
     * refreshTable
     * @param _resize
     * @author Anthony C. Liberto
     */
    public void refreshTable(boolean _resize) {
        resizeAndRepaint();
        if (_resize) {
            resizeCells();
        }
        clearSelection();
        repaint();
        return;
    }

    /**
     * commit
     * @author Anthony C. Liberto
     */
    public void commit() {
    }
    /**
     * rollback
     * @author Anthony C. Liberto
     */
    public void rollback() {
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
     *
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

    /*
     50364
    */
    /**
     * isMultipleSelection
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMultipleSelection() {
        return getSelectedRowCount() > 1;
    }

    /**
     * getBookmarkItems
     * @return
     * @author Anthony C. Liberto
     */
    public BookmarkItem[] getBookmarkItems() {
        return getBookmarkItems(getSelectedRows());
    }

    /**
     * getBookmarkItems
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public BookmarkItem[] getBookmarkItems(int[] _row) {
        if (tm != null) {
            int ii = _row.length;
            BookmarkItem[] out = new BookmarkItem[ii];
            for (int i = 0; i < ii; ++i) {
                out[i] = getBookmarkItem(_row[i]);
            }
            return out;
        }
        return null;
    }

    /**
     * removeBookmarks
     * @param _items
     * @author Anthony C. Liberto
     */
    public void removeBookmarks(BookmarkItem[] _items) {
        if (_items != null) {
            int ii = _items.length;
            String[] keys = new String[ii];
            for (int i = 0; i < ii; ++i) {
                keys[i] = new String(_items[i].getKey());
            }
            if (tm.deleteRows(keys)) {
                refreshTable(true);
            }
        }
        return;
    }

    /*
     51217
     */
    /**
     * renameBookmark
     * @param _oldBook
     * @param _desc
     * @author Anthony C. Liberto
     */
    public void renameBookmark(BookmarkItem _oldBook, String _desc) {
        tm.renameBookmark(_oldBook, _desc);
        return;
    }

    /*
     accessibility
     */
    /**
     * getAccessibleValueAt
     *
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public Object getAccessibleValueAt(int _row, int _col) {
        return getValueAt(_row, _col);
    }

    /**
     * getAccessibleColumnNameAt
     *
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    public String getAccessibleColumnNameAt(int _col) {
        return getColumnName(_col);
    }

    /**
     * getAccessibleTableDescription
     * @author Anthony C. Liberto
     * @return String
     * @param _code
     */
    public String getAccessibleTableDescription(String _code) {
        return getString(_code);
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
    /*
     cr_1210035324
     */
    /**
     * addBookmarkItem
     * @param _item
     * @param _items
     * @param _desc
     * @return
     * @author Anthony C. Liberto
     */
    public boolean addBookmarkItem(EANActionItem _item, EANActionItem[] _items, String _desc) {
        if (tm.addBookmarkItem(_item, _items, _desc)) {
            refreshTable(true);
            return true;
        }
        return false;
    }

    /*
     5ZKL3K
     */
    /**
     * showSort
     * @author Anthony C. Liberto
     */
    public void showSort() {
        eaccess().show((Component) this, SORT_PANEL, false);
        return;
    }

    /**
     * getVisibleColumnNames
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getVisibleColumnNames() {
        int cc = getColumnCount();
        Vector v = new Vector();
        SortColumn[] out = null;
        for (int c = 0; c < cc; ++c) {
            if (isColumnVisible(c)) {
                v.add(new SortColumn(getColumnName(c),c));
            }
        }
        out = new SortColumn[v.size()];
        return v.toArray(out);
    }

    /**
     * sort
     *
     * @param _i
     * @param _d
     * @author Anthony C. Liberto
     */
    public void sort(int[] _i, boolean[] _d) {
//        int[] c = null;													//MN_23318121
        if (isSortable()) {
            if (!isBusy()) {
                setBusy(true);
                tm.sort(_i, _d);
                setBusy(false);
            }
        }
        return;
    }

    /**
     * showFind
     *
     * @author Anthony C. Liberto
     */
    public void showFind() {
        eaccess().show((Component) this, FIND_PANEL, false);
        return;
    }

    /**
     * hasFound
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean hasFound() {
        return !foundItem.isEmpty();
    }
    /**
     * findValue
     * @author Anthony C. Liberto
     * @param find
     * @param Multi
     * @param strCase
     * @param increment
     * @param _forReplace
     */
    public void findValue(String find, boolean Multi, boolean strCase, int increment, boolean _forReplace) {
        String str = null;
        if (!isSearchable()) {
            return;
        }
        System.out.println("findValue(" + find + ", " + strCase + ")");
        str = getNextValue(find, Multi, strCase, increment, getSelectedRow(), getSelectedColumn(), _forReplace);
        System.out.println("    " + str);
        return;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#setFound(int, int)
     * @author Anthony C. Liberto
     */
    protected void setFound(int r, int c) {
        String key = getKey(r);
        foundItem.putMap(key, Integer.toString(convertColumnIndexToModel(c)));
        findPt.x = r;
        findPt.y = c;
        setRowSelectionInterval(r, r);
        setColumnSelectionInterval(c, c);
        scrollRectToVisible(getCellRect(r, c, true));
        return;
    }

    /**
     * @see javax.swing.JTable#getCellRenderer(int, int)
     * @author Anthony C. Liberto
     */
    public TableCellRenderer getCellRenderer(int _r, int _c) {
        boolean isFound = isFound(_r, _c);
        TableCellRenderer rend = super.getCellRenderer(_r, _c);
        if (isFound) {
            return foundRend;
        }
        return rend;
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isFound(int, int)
     * @author Anthony C. Liberto
     */
    protected boolean isFound(int r, int c) {
        String key = getKey(r);
        if (key == null || foundItem == null) {
            return false;
        }
        if (!foundItem.containsKey(key)) {
            return false;
        }
        return foundItem.mapContains(key, Integer.toString(convertColumnIndexToModel(c)));
    }

    /**
     * isFound
     * @author Anthony C. Liberto
     * @return boolean
     * @param str
     * @param find
     * @param caseSensitive
     */
    protected boolean isFound(String str, String find, boolean caseSensitive) {
        if (caseSensitive) {
            if (str.indexOf(find) != -1) {
                return true;
            }
        } else {
            if (str.toUpperCase().indexOf(find.toUpperCase()) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * getKey
     * @param i
     * @return
     * @author Anthony C. Liberto
     */
    public String getKey(int i) {
        if (tm != null) {
            return tm.getRowKey(i);
        }
        return null;
    }

    /**
     * resetFound
     *
     * @author Anthony C. Liberto
     */
    public void resetFound() {
        foundItem.clear();
        repaint();
        requestFocus();
        return;
    }

    /**
     * showFilter
     *
     * @author Anthony C. Liberto
     */
    public void showFilter() {
        eaccess().show((Component) this, FILTER_PANEL, false);
        return;
    }
    /**
     * setFilterGroup
     * @author Anthony C. Liberto
     * @param _group
     * @param _filter
     */
    public void setFilterGroup(FilterGroup _group, boolean _filter) {
        System.out.println("setFilterGroup(" + _filter + ")");
        tm.setFilterGroup(_group);
        if (_filter) {
            filter();
        }
        return;
    }
    /**
     * setFilterGroup
     * @author Anthony C. Liberto
     * @param _group
     * @param _filter
     */
    public void setColFilterGroup(FilterGroup _group, boolean _filter) {
        System.out.println("setFilterGroup(" + _filter + ")");
        tm.setColFilterGroup(_group);
        if (_filter) {
            filter();
        }
        return;
    }

    /**
     * getFilterGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getFilterGroup() {
        return tm.getFilterGroup();
    }

    /**
     * getColFilterGroup
     *
     * @return
     * @author Anthony C. Liberto
     */
    public FilterGroup getColFilterGroup() {
        return tm.getColFilterGroup();
    }

    /**
     * getBookmarkTable
     * @return
     * @author Anthony C. Liberto
     */
    public BookmarkGroupTable getBookmarkTable() {
        return tm.getTable();
    }

    /**
     * filter
     *
     * @author Anthony C. Liberto
     */
    public void filter() {
        tm.filter();
        resizeAndRepaint();
        setFilter(isFiltered());
        clearSelection();
        if (getRowCount() > 0) {
            if (getColumnCount() > 0) {
                setRowSelectionInterval(0, 0);
                setColumnSelectionInterval(0, 0);
                scrollToRow(0);
            }
        }
        return;
    }
    /**
     * scrollToRow
     * @param _row
     * @author Anthony C. Liberto
     */
    public void scrollToRow(int _row) {
        if (isValidCell(_row, 0)) {
            scrollRectToVisible(getCellRect(_row, 0, true));
        }
        return;
    }

    /**
     * scrollToColumn
     * @param _col
     * @author Anthony C. Liberto
     */
    public void scrollToColumn(int _col) {
        if (isValidCell(0, _col)) {
            scrollRectToVisible(getCellRect(0, _col, true));
        }
        return;
    }

    /**
     * resetFilter
     *
     * @author Anthony C. Liberto
     */
    public void resetFilter() {
        tm.resetFilter();
        resizeAndRepaint();
        setFilter(isFiltered());
        return;
    }

    /**
     * resizeAndPaint
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void resizeAndPaint(boolean _b) {
    }

    /**
     * modelChanged
     *
     * @param _i
     * @author Anthony C. Liberto
     */
    public void modelChanged(int _i) {
    }

    /**
     * sort
     *
     * @author Anthony C. Liberto
     */
    public void sort() {
    }

    /**
     * sort
     *
     * @param _ascending
     * @author Anthony C. Liberto
     */
    public void sort(boolean _ascending) {
    }

    /**
     * sort
     *
     * @param _column
     * @param _ascending
     * @author Anthony C. Liberto
     */
    public void sort(int _column, boolean _ascending) {
    }

    /**
     * sort
     *
     * @param _c
     * @author Anthony C. Liberto
     */
    public void sort(Comparator _c) {
    }


    /**
     * setSortable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSortable(boolean _b) {
    }

    /**
     * isSortable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSortable() {
        return true;
    }

    /**
     * isMultiColumn
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isMultiColumn() {
        return true;
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isColumnVisible(int)
     * @author Anthony C. Liberto
     */
    public boolean isColumnVisible(int _col) {
        return true;
    }

    /**
     * isRowVisible
     *
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isRowVisible(int _row) {
        return true;
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isValidCell(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isValidCell(int r, int c) {
        if (r < 0 || r >= getRowCount()) {
            return false;
        } else if (c < 0 || c >= getColumnCount()) {
            return false;
        }
        return true;
    }

    /**
     * export
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String export() {
        return null;
    }

    /**
     * print
     *
     * @author Anthony C. Liberto
     */
    public void print() {
    }

    /**
     * getSelectedObject
     *
     * @return
     * @author Anthony C. Liberto
     */
    public Object getSelectedObject() {
        return null;
    }

    /**
     * getSelectedObjects
     *
     * @param _new
     * @param _maxEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getSelectedObjects(boolean _new, boolean _maxEx) throws OutOfRangeException {
        return null;
    }

    /**
     * getVisibleObjects
     *
     * @param _new
     * @param _maxEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    public Object[] getVisibleObjects(boolean _new, boolean _maxEx) throws OutOfRangeException {
        return null;
    }

    /**
     * getSelectedVisibleRows
     *
     * @return
     * @author Anthony C. Liberto
     */
    public int[] getSelectedVisibleRows() {
        return null;
    }

    /**
     * showInformation
     *
     * @author Anthony C. Liberto
     */
    public void showInformation() {
    }

    /**
     * setSearchable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setSearchable(boolean _b) {
    }

    /**
     * isSearchable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isSearchable() {
        return true;
    }

    /**
     * setReplaceable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setReplaceable(boolean _b) {
    }

    /**
     * isReplaceable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isReplaceable() {
        return false;
    }

    /**
     * resetTouch
     *
     * @author Anthony C. Liberto
     */
    public void resetTouch() {
    }
    /**
     * replaceValue
     * @author Anthony C. Liberto
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     */
    public void replaceValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
    }
    /**
     * replaceNextValue
     * @author Anthony C. Liberto
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     */
    public void replaceNextValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
    }
    /**
     * replaceAllValue
     * @author Anthony C. Liberto
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     */
    public void replaceAllValue(String find, String replace, boolean Multi, boolean strCase, int increment) {
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isRowFiltered(int)
     * @author Anthony C. Liberto
     */
    public boolean isRowFiltered(int r) {
        return false;
    }

    /**
     * isFiltered
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFiltered() {
        return tm.isFiltered();
    }

    /**
     * @see com.ibm.eannounce.eForms.table.GTable#setFilter(boolean)
     * @author Anthony C. Liberto
     */
    public void setFilter(boolean _filter) {
        setFilterOn(_filter);
        return;
    }

    /**
     * getColumnKey
     * @author Anthony C. Liberto
     * @return String
     * @param _header
     */
    public String getColumnKey(String _header) {
        return null;
    }

    /**
     * setFilterable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFilterable(boolean _b) {
    }

    /**
     * isFilterable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isFilterable() {
        return true;
    }

    /**
     * setCopyable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setCopyable(boolean _b) {
    }

    /**
     * isCopyable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isCopyable() {
        return false;
    }

    /**
     * copy
     *
     * @return
     * @author Anthony C. Liberto
     */
    public TransferObject copy() {
        return null;
    }

    /**
     * cut
     *
     * @return
     * @author Anthony C. Liberto
     */
    public TransferObject cut() {
        return null;
    }

    /**
     * paste
     *
     * @author Anthony C. Liberto
     */
    public void paste() {
    }

    /**
     * canPaste
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean canPaste() {
        return false;
    }

    /**
     * invertSelection
     *
     * @author Anthony C. Liberto
     */
    public void invertSelection() {
    }

    /**
     * moveColumn
     *
     * @param _left
     * @author Anthony C. Liberto
     */
    public void moveColumn(boolean _left) {
    }

    /**
     * setPopupDisplayable
     *
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setPopupDisplayable(boolean _b) {
    }

    /**
     * isPopupDisplayable
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPopupDisplayable() {
        return false;
    }

    /**
     * getUIPrefKey
     *
     * @return
     * @author Anthony C. Liberto
     */
    public String getUIPrefKey() {
        return null;
    }

    /**
     * cancelEdit
     *
     * @author Anthony C. Liberto
     */
    public void cancelEdit() {
    }
    /**
     * @see javax.swing.JTable#isEditing()
     * @author Anthony C. Liberto
     */
    public boolean isEditing() {
        return false;
    }
 
   /**
    * getRowSelectableTable
    *
    * @return
    * @author Anthony C. Liberto
    */
    public RowSelectableTable getRowSelectableTable() {
        return null;
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#isEmpty()
     * @author Anthony C. Liberto
     */
    public boolean isEmpty() {
        return false;
    }
    /**
     * @see com.ibm.eannounce.eForms.table.GTable#hasData()
     * @author Anthony C. Liberto
     */
    public boolean hasData() {
        return false;
    }

    /**
     * setFilterOn
     * @param _b
     * @author Anthony C. Liberto
     */
    public void setFilterOn(boolean _b) {
    }

    /**
     * isPivot
     *
     * @return
     * @author Anthony C. Liberto
     */
    public boolean isPivot() {
		return false;
    }

    /**
     * pivot
     *
     * @author Anthony C. Liberto
     */
    public void pivot() {
    } //pivot

    /**
     * pivotResynch
     *
     * @author Anthony C. Liberto
     */
    public void pivotResynch() {
    } //pivot

    /**
     * getIndexFromHeader
     *
     * @param _s
     * @return
     * @author Anthony C. Liberto
     * /
    public int getIndexFromHeader(String _s) {
		if (_s != null) {
			TableColumnModel tcm = getColumnModel();
	        if (tcm != null) {
		        int iIndex = ((RSTableColumnModel)tcm).getIndexFromHeader(_s);
		        return convertColumnIndexToModel(iIndex);
			}
		}
		return 0;
	}*/
}
