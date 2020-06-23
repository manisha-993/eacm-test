/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.1  2002/11/07
 * @author Anthony C. Liberto
 *
 * $Log: ETable.java,v $
 * Revision 1.2  2009/04/08 20:00:11  wendy
 * Sort needs more than column name to find the attribute
 *
 * Revision 1.1  2007/04/18 19:45:11  wendy
 * Reorganized JUI module
 *
 * Revision 1.2  2005/09/12 19:03:15  tony
 * JTest Modifications
 *
 * Revision 1.1.1.1  2005/09/09 20:38:04  tony
 * This is the initial load of OPICM
 *
 * Revision 1.10  2005/09/08 17:59:07  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.9  2005/04/05 17:29:50  tony
 * MN_23318121
 *
 * Revision 1.8  2005/02/01 22:26:40  tony
 * ColumnFilter
 *
 * Revision 1.7  2005/01/31 20:47:48  tony
 * JTest Second Pass
 *
 * Revision 1.6  2005/01/26 22:17:57  tony
 * JTest Modifications
 *
 * Revision 1.5  2005/01/18 19:04:06  tony
 * pivot modifications
 *
 * Revision 1.4  2005/01/14 20:32:12  tony
 * pivot
 *
 * Revision 1.3  2004/11/16 22:25:02  tony
 * improved sorting and resizing logic to improve table
 * performance.
 *
 * Revision 1.2  2004/03/25 23:37:20  tony
 * cr_216041310
 *
 * Revision 1.1.1.1  2004/02/10 16:59:47  tony
 * This is the initial load of OPICM
 *
 * Revision 1.9  2003/12/30 21:39:11  tony
 * 53482
 *
 * Revision 1.8  2003/10/31 17:30:48  tony
 * 52783
 *
 * Revision 1.7  2003/08/14 15:34:59  tony
 * 51761
 *
 * Revision 1.6  2003/06/02 16:45:30  tony
 * 51024 added outOfRangeException, thus preventing multiple
 * messages.
 *
 * Revision 1.5  2003/05/09 16:51:28  tony
 * updated filter icon enabling for filter.
 *
 * Revision 1.4  2003/04/21 20:03:13  tony
 * enhanced scroll logic to improve flag editor.
 *
 * Revision 1.3  2003/04/14 21:38:24  tony
 * updated table Logic.
 *
 * Revision 1.2  2003/04/04 16:39:53  tony
 * added dump logic to tables and table models.
 *
 * Revision 1.1.1.1  2003/03/03 18:03:51  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2002/11/07 16:58:11  tony
 * added/adjusted copyright statement
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.TransferObject;
import COM.ibm.eannounce.objects.*;
import com.ibm.eannounce.exception.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public interface ETable {
    //	public abstract void createPopupMenu();
    //	public abstract void createTableMenu();
    /**
     * init
     * @author Anthony C. Liberto
     */
    void init();
    /**
     * resizeAndPaint
     * @param _b
     * @author Anthony C. Liberto
     */
    void resizeAndPaint(boolean _b);
    /**
     * modelChanged
     * @param _type
     * @author Anthony C. Liberto
     */
    void modelChanged(int _type);

    /**
     * sort
     * @author Anthony C. Liberto
     */
    void sort();
    /**
     * sort
     * @param _ascending
     * @author Anthony C. Liberto
     */
    void sort(boolean _ascending);
    /**
     * sort
     * @param _column
     * @param _ascending
     * @author Anthony C. Liberto
     */
    void sort(int _column, boolean _ascending);
    //tablesort	public abstract void sort(Comparator _c);
    /**
     * sort
     * @param _colArray
     * @param _ascArray
     * @author Anthony C. Liberto
     */
    void sort(int[] _colArray, boolean[] _ascArray); //MSort

    /**
     * setSortable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setSortable(boolean _b);
    /**
     * isSortable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isSortable();
    /**
     * isMultiColumn
     * @return
     * @author Anthony C. Liberto
     */
    boolean isMultiColumn();
    /**
     * isColumnVisible
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    boolean isColumnVisible(int _col);
    /**
     * isRowVisible
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    boolean isRowVisible(int _row);
    /**
     * isValidCell
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    boolean isValidCell(int _row, int _col);

    /**
     * getCellRenderer
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    TableCellRenderer getCellRenderer(int _row, int _col);
    /**
     * getCellEditor
     * @param _row
     * @param _col
     * @return
     * @author Anthony C. Liberto
     */
    TableCellEditor getCellEditor(int _row, int _col);

    /**
     * setRowHeight
     * @param _row
     * @param _height
     * @author Anthony C. Liberto
     */
    void setRowHeight(int _row, int _height);
    /**
     * getRowHeight
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    int getRowHeight(int _row);

    /**
     * finishEditing
     * @author Anthony C. Liberto
     */
    void finishEditing();
    /**
     * export
     * @return
     * @author Anthony C. Liberto
     */
    String export();
    /**
     * print
     * @author Anthony C. Liberto
     */
    void print();

    /**
     * getSelectedObject
     * @return
     * @author Anthony C. Liberto
     */
    Object getSelectedObject();
    //cr_1310	public abstract Object[] getSelectedObjects(boolean _new) throws outOfRangeException;
    //cr_1310	public abstract Object[] getVisibleObjects(boolean _new) throws outOfRangeException;

    /**
     * getSelectedObjects
     *
     * @param _new
     * @param _maxEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    Object[] getSelectedObjects(boolean _new, boolean _maxEx) throws OutOfRangeException; //cr_1310

    /**
     * getVisibleObjects
     *
     * @param _new
     * @param _maxEx
     * @throws com.ibm.eannounce.exception.OutOfRangeException
     * @return
     * @author Anthony C. Liberto
     */
    Object[] getVisibleObjects(boolean _new, boolean _maxEx) throws OutOfRangeException; //cr_1310

    /**
     * getVisibleColumnNames
     * @return
     * @author Anthony C. Liberto
     */
    Object[] getVisibleColumnNames();
    /**
     * getSelectedVisibleRows
     * @return
     * @author Anthony C. Liberto
     */
    int[] getSelectedVisibleRows();
    /**
     * showInformation
     * @author Anthony C. Liberto
     */
    void showInformation();
    //	public abstract OPICMSoftLockItem[] getSelectedOPICMSoftLockItems();

    /**
     * setSearchable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setSearchable(boolean _b);
    /**
     * showFind
     * @author Anthony C. Liberto
     */
    void showFind();
    /**
     * isSearchable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isSearchable();
    /**
     * setReplaceable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setReplaceable(boolean _b);
    /**
     * isReplaceable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isReplaceable();
    /**
     * hasFound
     * @return
     * @author Anthony C. Liberto
     */
    boolean hasFound(); //20020301
    /**
     * findValue
     * @param find
     * @param Multi
     * @param strCase
     * @param increment
     * @param _replace
     * @author Anthony C. Liberto
     */
    void findValue(String find, boolean Multi, boolean strCase, int increment, boolean _replace);
    /**
     * replaceValue
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     * @author Anthony C. Liberto
     */
    void replaceValue(String find, String replace, boolean Multi, boolean strCase, int increment);
    /**
     * replaceNextValue
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     * @author Anthony C. Liberto
     */
    void replaceNextValue(String find, String replace, boolean Multi, boolean strCase, int increment);
    /**
     * replaceAllValue
     * @param find
     * @param replace
     * @param Multi
     * @param strCase
     * @param increment
     * @author Anthony C. Liberto
     */
    void replaceAllValue(String find, String replace, boolean Multi, boolean strCase, int increment);
    /**
     * resetTouch
     * @author Anthony C. Liberto
     */
    void resetTouch(); //21825
    /**
     * resetFound
     * @author Anthony C. Liberto
     */
    void resetFound();

    /**
     * isFiltered
     * @return
     * @author Anthony C. Liberto
     */
    boolean isFiltered();
    /**
     * showFilter
     * @author Anthony C. Liberto
     */
    void showFilter();
    /**
     * filter
     * @author Anthony C. Liberto
     */
    void filter();
    /**
     * resetFilter
     * @author Anthony C. Liberto
     */
    void resetFilter();
    /**
     * getColumnKey
     * @param _header
     * @return
     * @author Anthony C. Liberto
     */
    String getColumnKey(String _header);
    //53482	public abstract void setFilterGroup(FilterGroup _group);
    /**
     * setFilterGroup
     * @param _group
     * @param _filter
     * @author Anthony C. Liberto
     */
    void setFilterGroup(FilterGroup _group, boolean _filter); //53482
    /**
     * setColFilterGroup
     * @param _group
     * @param _filter
     * @author Anthony C. Liberto
     */
    void setColFilterGroup(FilterGroup _group, boolean _filter); //53482
    /**
     * getFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    FilterGroup getFilterGroup();
	/**
	 * getColFilterGroup
	 * @return
	 * @author Anthony C. Liberto
	 */
    FilterGroup getColFilterGroup();
    /**
     * setFilterable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setFilterable(boolean _b);
    /**
     * isFilterable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isFilterable();
    /**
     * isRowFiltered
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    boolean isRowFiltered(int _row);

    /**
     * setCopyable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setCopyable(boolean _b);
    /**
     * isCopyable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isCopyable();
    /**
     * copy
     * @return
     * @author Anthony C. Liberto
     */
    TransferObject copy();
    /**
     * cut
     * @return
     * @author Anthony C. Liberto
     */
    TransferObject cut();
    /**
     * paste
     * @author Anthony C. Liberto
     */
    void paste();
    /**
     * canPaste
     * @return
     * @author Anthony C. Liberto
     */
    boolean canPaste();

    /**
     * resizeCells
     * @author Anthony C. Liberto
     */
    void resizeCells();
    /**
     * resizeColumn
     * @param _column
     * @author Anthony C. Liberto
     */
    void resizeColumn(int _column);
    /**
     * resizeRow
     * @param _row
     * @author Anthony C. Liberto
     */
    void resizeRow(int _row);

    /**
     * selectAll
     * @author Anthony C. Liberto
     */
    void selectAll();
    /**
     * invertSelection
     * @author Anthony C. Liberto
     */
    void invertSelection();
    /**
     * moveColumn
     * @param _left
     * @author Anthony C. Liberto
     */
    void moveColumn(boolean _left);

    //	public abstract void saveColumnOrder();
    //	public abstract void loadColumnOrder();
    //	public abstract void setColumnOrderable(boolean _b);
    //	public abstract boolean isColumnOrderable();
    /**
     * repaint
     * @author Anthony C. Liberto
     */
    void repaint();

    //	public abstract void addPopupMenu(String _s, ActionListener _al);
    //	public abstract void addTableMenu(String _s, ActionListener _al, int _key, int _mod, boolean _enabled);
    //	public abstract JMenu getTableMenu();
    /**
     * setPopupDisplayable
     * @param _b
     * @author Anthony C. Liberto
     */
    void setPopupDisplayable(boolean _b);
    /**
     * isPopupDisplayable
     * @return
     * @author Anthony C. Liberto
     */
    boolean isPopupDisplayable();
    /**
     * dereference
     * @author Anthony C. Liberto
     */
    void dereference();
    /**
     * getUIPrefKey
     * @return
     * @author Anthony C. Liberto
     */
    String getUIPrefKey(); //21643
    /**
     * dump
     * @param _brief
     * @return
     * @author Anthony C. Liberto
     */
    String dump(boolean _brief);
    /**
     * cancelEdit
     * @author Anthony C. Liberto
     */
    void cancelEdit();
    /**
     * isEditing
     * @return
     * @author Anthony C. Liberto
     */
    boolean isEditing();
    /**
     * getRowSelectableTable
     * @return
     * @author Anthony C. Liberto
     */
    RowSelectableTable getRowSelectableTable(); //51761
    /**
     * isEmpty
     * @return
     * @author Anthony C. Liberto
     */
    boolean isEmpty(); //52783
    /**
     * hasData
     * @return
     * @author Anthony C. Liberto
     */
    boolean hasData(); //52783
    /**
     * isPivot
     * @return
     * @author Anthony C. Liberto
     */
    boolean isPivot();
    /**
     * pivot
     * @author Anthony C. Liberto
     */
    void pivot(); //pivot
    /**
     * pivotResynch
     * @author Anthony C. Liberto
     */
    void pivotResynch(); //pivot

    /**
     * getIndexFromHeader
     *
     * @param _s
     * @return
     * @author Anthony C. Liberto
     */
    //int getIndexFromHeader(String _s);
}
