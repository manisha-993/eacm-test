/**
 * Copyright (c) 2002-2003 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 *
 * @version 1.2  2002/04/11
 * @author Anthony C. Liberto
 *
 * $Log: BookmarkTableModel.java,v $
 * Revision 1.3  2009/09/01 17:28:47  wendy
 * removed useless code and cleanup
 *
 * Revision 1.2  2008/01/30 16:26:57  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.1  2007/04/18 19:45:11  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:38:03  tony
 * This is the initial load of OPICM
 *
 * Revision 1.8  2005/09/08 17:59:06  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.7  2005/02/01 22:26:40  tony
 * ColumnFilter
 *
 * Revision 1.6  2005/01/31 20:47:47  tony
 * JTest Second Pass
 *
 * Revision 1.5  2005/01/26 22:17:56  tony
 * JTest Modifications
 *
 * Revision 1.4  2004/08/11 21:47:42  tony
 * 5ZKL3K performance enhancement.
 *
 * Revision 1.3  2004/08/11 21:24:24  tony
 * 5ZKL3K
 *
 * Revision 1.2  2004/02/26 21:53:17  tony
 * remoteDatabaseInterface centralization.
 *
 * Revision 1.1.1.1  2004/02/10 16:59:45  tony
 * This is the initial load of OPICM
 *
 * Revision 1.16  2004/01/09 22:23:02  tony
 * cr_1210035324
 *
 * Revision 1.15  2004/01/09 00:42:44  tony
 * cr_1210035324
 * Bookmarks generate a replayable history
 *
 * Revision 1.14  2003/10/29 00:14:29  tony
 * removed System.out. statements.
 *
 * Revision 1.13  2003/09/15 20:49:54  tony
 * 52238
 *
 * Revision 1.12  2003/06/19 23:13:40  tony
 * 51217
 *
 * Revision 1.11  2003/06/12 22:23:41  tony
 * 1.2h modification enhancements.
 *
 * Revision 1.10  2003/06/03 22:48:52  tony
 * 51084
 *
 * Revision 1.9  2003/04/22 21:04:20  tony
 * 50423
 *
 * Revision 1.8  2003/04/18 20:08:15  tony
 * 50408
 *
 * Revision 1.7  2003/04/17 23:14:15  tony
 * 50406
 *
 * Revision 1.6  2003/04/15 20:17:25  tony
 * added bookmark limit.
 *
 * Revision 1.5  2003/04/11 20:02:30  tony
 * added copyright statements.
 *
 */
package com.ibm.eannounce.eforms.table;
import com.elogin.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.util.Arrays;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class BookmarkTableModel extends DefaultTableModel implements EAccessConstants {
	private static final long serialVersionUID = 1L;
	private BookmarkGroupTable ctm = null;
	private AbstractTableComparator comparator = new AbstractTableComparator(0,true);

	/**
     * bookmarkTableModel
     * @author Anthony C. Liberto
     */
    public BookmarkTableModel() {
	}

	/**
     * dereference
     * @author Anthony C. Liberto
     */
    public void dereference() {
		ctm = null;
		comparator = null;
	}

	/**
     * eaccess
     * @return
     * @author Anthony C. Liberto
     * /
    public EAccess eaccess() {
		return EAccess.eaccess();
	}*/

	/**
     * updateModel
     * @param _ctm
     * @author Anthony C. Liberto
     */
    protected void updateModel(BookmarkGroupTable _ctm) {
//50423		if (ctm != null) {											//50406
//50408			fireTableRowsDeleted(0,getRowCount()-1);				//50406
		if (ctm != null && getRowCount() >= 1) {						//50423
			fireTableRowsDeleted(0,Math.max(getRowCount()-1,0));		//50408
		}																//50406
		ctm = _ctm;
		sort();															//51084
		modelChanged(TableModelEvent.INSERT);
	}

	/**
     * @see javax.swing.table.TableModel#getRowCount()
     * @author Anthony C. Liberto
     */
    public int getRowCount() {
		if (ctm != null) {
			return ctm.getRowCount();
		}
		return 0;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnCount()
     * @author Anthony C. Liberto
     */
    public int getColumnCount() {
		if (ctm != null) {
			return ctm.getColumnCount();
		}
		return 0;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnName(int)
     * @author Anthony C. Liberto
     */
    public String getColumnName(int _i) {
		if (ctm != null) {
			return ctm.getColumnHeader(_i);
		}
		return null;
	}

	/**
     * @see javax.swing.table.TableModel#getColumnClass(int)
     * @author Anthony C. Liberto
     */
    public Class getColumnClass(int _i) {
		if (ctm != null) {
			return ctm.getColumnClass(_i);
		}
		return String.class;
	}

	/**
     * @see javax.swing.table.TableModel#isCellEditable(int, int)
     * @author Anthony C. Liberto
     */
    public boolean isCellEditable(int _r,int _c) {
		return false;
	}

	/**
     * getRow
     * @param _row
     * @return
     * @author Anthony C. Liberto
     */
    protected Object getRow(int _row) {
		return ctm.getRow(_row);
	}

	/**
     * deleteRow
     * @param _key
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean deleteRow(String _key) {														//52238
		return deleteRow(_key,true);															//52238
	}																							//52238

	/**
     * deleteRow
     * @param _key
     * @param _sort
     * @return
     * @author Anthony C. Liberto
     */
    private boolean deleteRow(String _key, boolean _sort) {										//52238
		int row = ctm.getRowIndex(_key);
		if (EAccess.eaccess().dBase().deleteBookmark(ctm,_key)) {
			fireTableRowsDeleted(row,row);
			if (_sort) {																			//52238
				sort();
			}																			//51084
			return true;
		}
		return false;
	}

	/**
     * rollback
     * @author Anthony C. Liberto
     * /
    public void rollback() {
		return;
	}*/

	/**
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     * @author Anthony C. Liberto
     */
    public Object getValueAt(int _r, int _c) {
		if (ctm != null) {
			return ctm.getValueAt(_r,_c);
		}
		return null;
	}

	/**
     * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
     * @author Anthony C. Liberto
     */
    public void setValueAt(Object _o, int _r, int _c) {
	}

	/**
     * modelUpdate
     * @author Anthony C. Liberto
     * /
    public void modelUpdate() {
		modelChanged(TableModelEvent.UPDATE);
		return;
	}*/

    /**
     * modelChanged
     * @param _type
     * @author Anthony C. Liberto
     */
    private void modelChanged(int _type) {
		tableModelChanged(0,Math.max(0,getRowCount()-1),TableModelEvent.ALL_COLUMNS, _type);
	}

    /**
     * tableModelChanged
     * @param _start
     * @param _end
     * @param _col
     * @param _type
     * @author Anthony C. Liberto
     */
    private void tableModelChanged(int _start, int _end, int _col, int _type) {
		TableModelEvent event = null;
        if (_start < 0) {
			_start = 0;
		}
		if (_end < 0) {
			_end = getRowCount()-1;
		}
		event = new TableModelEvent(this, _start, _end, _col, _type);
		fireTableChanged(event);
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
			for (int r=0;r<rr;++r) {
				_sb.append("Row(" + r + "): ");
				for (int c=0;c<cc;++c) {
					Object o = getValueAt(r,c);
					if (o != null) {
						_sb.append(o.toString());
					}
					if (c != lastCol) {
						_sb.append(", ");
					}
				}
				_sb.append(RETURN);
			}
		}
		return _sb;
	}

/*
 50364
*/
	/**
     * deleteRows
     * @param _keys
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean deleteRows(String[] _keys) {
		if (_keys != null) {
			int ii = _keys.length;
			int[] rows = new int[ii];
			for (int i=0;i<ii;++i) {
				rows[i] = ctm.getRowIndex(_keys[i]);
			}
			Arrays.sort(rows);
			if (EAccess.eaccess().dBase().deleteBookmarks(ctm,_keys)) {
				for (int i=ii-1;i>=0;--i) {
					fireTableRowsDeleted(rows[i],rows[i]);
				}
				sort();																			//51084
				return true;
			}
		}
		return false;
	}

/*
 51084
 */
    /**
    * sort
    * @author Anthony C. Liberto
    */
    private void sort() {
		sort(comparator);
	}

	private void sort(AbstractTableComparator _c) {
		if (_c != null) {
			_c.sort(ctm);
		}
	}

	/**
     * sort
     * @param _c
     * @param _d
     * @author Anthony C. Liberto
     */
	protected void sort(int[] _c, boolean[] _d) {
		for (int i=0; i < _c.length; i++) {
			if (_c[i] < 0 || _c[i] >= getColumnCount()) {
                _c[i] = 0;
			}
		}
		comparator.set(_c,_d);
		sort(comparator);
		modelChanged(TableModelEvent.UPDATE);
	}

	/**
     * save
     * @param _item
     * @return
     * @author Anthony C. Liberto
     */
    private boolean save(BookmarkItem _item) {
		if (_item != null) {
			int row = getRowCount()-1;
			if (EAccess.eaccess().dBase().saveBookmark(ctm,_item)) {
				tableModelChanged(row,row,TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT);
				sort();																			//51084
				return true;
			}
		}
		return false;
	}

	/**
     * renameBookmark
     * @param _oldBook
     * @param _desc
     * @author Anthony C. Liberto
     */
    protected void renameBookmark(BookmarkItem _oldBook, String _desc) {
		if (_oldBook != null && _desc != null) {
			RemoteDatabaseInterface rdi = EAccess.eaccess().getRemoteDatabaseInterface();
			BookmarkItem newItem = null;

			try {
				newItem = ctm.addNewBookmarkItem(rdi.getBookmarkedActionItem(_oldBook.getProfile(),
                        _oldBook.getActionItemKey(),_oldBook.getUserDescription()),_desc);
			} catch (Exception _ex) {
				_ex.printStackTrace();
			}
			if (newItem != null) {
				if (deleteRow(_oldBook.getKey(),false)) {		//52238
					if (!save(newItem)) {						//52238
						save(_oldBook);							//52238
					}											//52238
				}												//52238
			}
		}
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
    protected boolean addBookmarkItem(EANActionItem _item, EANActionItem[] _items, String _desc) {
		BookmarkItem item = EAccess.eaccess().dBase().addBookmarkItem(ctm,_item,_items,_desc);
		return save(item);
	}

	/**
     * getRowKey
     * @param _i
     * @return
     * @author Anthony C. Liberto
     */
    protected String getRowKey(int _i) {
		if (ctm != null) {
			return ctm.getRowKey(_i);
		}
		return null;
	}

	/**
     * getFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    protected FilterGroup getFilterGroup() {
		if (ctm != null) {
			FilterGroup fg = ctm.getFilterGroup();
			if (fg == null) {
				try {
					fg = new FilterGroup(null,EAccess.eaccess().getActiveProfile(),ctm);
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
     * getColFilterGroup
     * @return
     * @author Anthony C. Liberto
     */
    protected FilterGroup getColFilterGroup() {
		if (ctm != null) {
			FilterGroup fg = ctm.getColFilterGroup();
			if (fg == null) {
				try {
					fg = new FilterGroup(null,EAccess.eaccess().getActiveProfile(),ctm,true);
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
     * setFilterGroup
     * @param _group
     * @author Anthony C. Liberto
     */
    protected void setFilterGroup(FilterGroup _group) {
		if (ctm != null) {
			ctm.setFilterGroup(_group);
		}
	}

	/**
     * setColFilterGroup
     * @param _group
     * @author Anthony C. Liberto
     */
    protected void setColFilterGroup(FilterGroup _group) {
		if (ctm != null) {
			ctm.setColFilterGroup(_group);
		}
	}

	/**
     * isFiltered
     * @return
     * @author Anthony C. Liberto
     */
    protected boolean isFiltered() {
		if (ctm != null) {
			return ctm.hasFilteredRows();
		}
		return false;
	}

	/**
     * filter
     * @author Anthony C. Liberto
     */
    protected void filter() {
		ctm.setUseFilter(true);
		refresh();
	}

	/**
     * refresh
     * @author Anthony C. Liberto
     */
    private void refresh() {
		ctm.refresh();
		modelChanged(TableModelEvent.UPDATE);
		sort();
	}


	/**
     * resetFilter
     * @author Anthony C. Liberto
     */
    protected void resetFilter() {
		ctm.setUseFilter(false);
		refresh();
	}

	/**
     * hasFilterGroup
     * @return
     * @author Anthony C. Liberto
     * /
    public boolean hasFilterGroup() {
		return ctm.hasFilterGroup();
	}*/

	/**
     * getTable
     * @return
     * @author Anthony C. Liberto
     */
    protected BookmarkGroupTable getTable() {
		return ctm;
	}
}
