//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.table;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.*;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.*;
import com.ibm.eacm.rend.LabelRenderer;

/**
 * table for managing bookmarks
 * @author Wendy Stimpson
 */
//$Log: BookmarkTable.java,v $
//Revision 1.3  2013/07/29 18:38:58  wendy
//Turn off autosort, update classifications after edit and resize cells
//
//Revision 1.2  2013/07/18 19:51:00  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class BookmarkTable extends BaseTable
implements Findable
{
	private static final long serialVersionUID = 1L;
	private BookmarkTableModel tm = new BookmarkTableModel();
    private Profile profile;
	private EACMAction loadAction = null;

    private Set<String> foundSet = new HashSet<String>();  // key is rowkey+columnkey
    private LabelRenderer foundRend = null;

    /**
     * constructor
     */
    public BookmarkTable(EACMAction act) {
        setModel(tm);
        loadAction = act;
        foundRend = new LabelRenderer();
        foundRend.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);

        init();
    }

    /**
     * init
     *
     */
    protected void init() {
    	super.init();
        setShowGrid(false);

        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    }

    /**
     * get the accessibility resource key
     * @return
     */
    protected String getAccessibilityKey() {
    	return "accessible.bookTable";
    }

    /**
     * called when the tablemodel is refreshed or inited
     * @param ctm
     * @param prof
     */
    public void updateModel(BookmarkGroupTable ctm,Profile prof) {
    	profile= prof;
        FilterGroup fg = tm.getFilterGroup(profile);

        tm.updateModel(ctm);

        createDefaultColumnsFromModel();
        setSortKeys();

        resizeCells();
        if (getRowCount() > 0) {
            setRowSelectionInterval(0, 0);
            setColumnSelectionInterval(0, 0);
        }

        tm.setFilterGroup(fg);
    }

    private void setSortKeys(){
    	List<? extends SortKey> origsortKeys = null;
		if(getRowSorter() != null){
			origsortKeys = ((TableRowSorter<?>)getRowSorter()).getSortKeys();
		}
    	if (origsortKeys !=null && origsortKeys.size()>0){
    		return; // keep the sort already specified
    	}

    	// must be done after the columns are setup in the model
        //The precedence of the columns in the sort is indicated by the order of the sort keys in the sort key list.
        List <RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // user description
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.ASCENDING)); // action item description
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.ASCENDING)); // role description
        setSortKeys(sortKeys);
    }

    /**
     * getBookmarkItem for selected row
     * @return
     */
    public BookmarkItem getBookmarkItem() {
        return getBookmarkItem(getSelectedRow());
    }

    /**
     * getBookmarkItem
     * @param _row
     * @return
     */
    private BookmarkItem getBookmarkItem(int viewRowIndex) {
        if (viewRowIndex >= 0 && viewRowIndex < getRowCount()) {
            return tm.getRowItem(this.convertRowIndexToModel(viewRowIndex));
        }
        return null;
    }

    /**
     * release memory
     */
    public void dereference() {
    	loadAction = null;
        profile = null;

        tm.dereference();
        tm = null;
        foundRend.dereference();
        foundRend = null;

        foundSet.clear();
        foundSet = null;
        super.dereference();
    }

    /**
     * allow user to double click a cell and load that bookmark
     * @see javax.swing.JTable#editCellAt(int, int, java.util.EventObject)
     */
    public boolean editCellAt(int r, int c, EventObject e) {
        if (e instanceof MouseEvent) {
            if (((MouseEvent) e).getClickCount() == 2) {
            	loadAction.actionPerformed(null);
            }
        }
        return false;
    }

    /**
     * refreshTable
     */
    public void refreshTable() {
    	resizeCells();

        clearSelection();
        repaint();
    }

    /**
     * dump
     *
     * @param brief
     * @return
     */
    public String dump(boolean brief) {
        StringBuffer sb = new StringBuffer();
        sb.append("Table.getRowCount(): " + getRowCount() + RETURN);
        sb.append("Table.getColumnCount(): " + getColumnCount() + RETURN);
        sb.append(RETURN + "TableModel...." + RETURN);
        sb.append(tm.dump(brief));
        return sb.toString();
    }

    /**
     * isMultipleSelection
     * @return
     */
    public boolean isMultipleSelection() {
        return getSelectedRowCount() > 1;
    }

    /**
     * getBookmarkItems
     * @return
     */
    public BookmarkItem[] getBookmarkItems() {
        return getBookmarkItems(getSelectedRows());
    }

    /**
     * getBookmarkItems
     * @param row
     * @return
     */
    private BookmarkItem[] getBookmarkItems(int[] row) {
    	BookmarkItem[] out = null;
    	out = new BookmarkItem[row.length];
    	for (int i = 0; i < row.length; ++i) {
    		out[i] = getBookmarkItem(row[i]);
    	}
    	return out;
    }

    /**
     * addBookmarkItem to the bookmarkgrouptable, it is not in the pdh yet
     *
     * @return BookmarkItem
     * @param bgt
     * @param desc
     * @param item
     * @param items
     */
    public static BookmarkItem addBookmarkItem(com.ibm.eacm.ui.BookmarkFrame owner, BookmarkGroupTable bgt, EANActionItem actitem, EANActionItem[] items, String desc) {
        BookmarkItem item = null;
        if (actitem != null && desc != null) {
            try {
                item = bgt.addNewBookmarkItem(actitem, desc);
                if (items != null) {
                    item.setActionHistory(items);
                }
            } catch (MiddlewareRequestException mre) {
            	com.ibm.eacm.ui.UI.showException(owner,mre, "mw.err-title");
            } catch (DuplicateBookmarkException dup) {
            	com.ibm.eacm.ui.UI.showException(owner,dup, "bookmark.err-title");
            }
        }
        return item;
    }

//====================================================================
    /**
     * getBookmarkTable
     * @return
     */
    public BookmarkGroupTable getBookmarkTable() {
        return tm.getTable();
    }

    /**
     * this is done on the event thread
     */
    public void updatedTbl(){
    	tm.updatedTbl();
      	refreshTable();
    }
    /**
     * this is done on the event thread after the row is deleted
     * @param row - these are already modelindexes because they were found using keys
     */
    public void fireRowDeleted(int rows[]){
    	for (int i=rows.length-1;i>=0;--i) {
		 	tm.fireRowDeleted(rows[i]);
		}
    }

    /**
     * return a different renderer if cell is marked as found
     * @see javax.swing.JTable#getCellRenderer(int, int)
     */
    public TableCellRenderer getCellRenderer(int r, int c) {
    	TableCellRenderer rend = null;
        boolean isFound = isFound(r, c);
        if (isFound) {
        	rend= foundRend;
        }else{
        	rend = super.getCellRenderer(r, c);
        }
        return rend;
    }

//  ====================================================================
//  Filterable support
	/* (non-Javadoc) need this here
	 * @see com.ibm.eacm.objects.Filterable#getFilterableTable()
	 */
	public Object getFilterableTable(){
		return getBookmarkTable();
	}

    /* (non-Javadoc) need this here
     * this is used for saving and restoring FilterGroup from serial preferences
     * @see com.ibm.eacm.objects.Filterable#getUIPrefKey()
     */
    public String getUIPrefKey() {
        return "Bookmark";
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.Filterable#setFilterGroup(COM.ibm.eannounce.objects.FilterGroup)
     */
    public void setFilterGroup(FilterGroup group) {
        // convert this to RowFilter and apply to TableRowSorter
    	tm.setFilterGroup(group);// set it but do not refresh() and run it
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.objects.Filterable#getFilterGroup()
     */
    public FilterGroup getFilterGroup() {
        return tm.getFilterGroup(profile);
    }

//  ====================================================================
    // used for find/replace
    /**
     * @param viewRowIndex
     * @param viewColIndex
     */
    protected void setFound(int viewRowIndex, int viewColIndex) {
        String key = null;
        if (viewRowIndex >= 0 && viewRowIndex < getRowCount()) {
        	key = tm.getRowKey(this.convertRowIndexToModel(viewRowIndex));
        }
        if(key==null){
        	return;
        }
        int colkey = convertColumnIndexToModel(viewColIndex);
        foundSet.add(key+colkey);

        setRowSelectionInterval(viewRowIndex, viewRowIndex);
        setColumnSelectionInterval(viewColIndex, viewColIndex);

        // an attempt to get focus border to paint.. doesnt seem to work?
		//getSelectionModel().setLeadSelectionIndex(r);
		//getColumnModel().getSelectionModel().setLeadSelectionIndex(c);

        scrollRectToVisible(getCellRect(viewRowIndex, viewColIndex, true));
    }

    /**
     * was this cell marked as found
     * @param viewRowIndex
     * @param viewColIndex
     * @return
     */
    private boolean isFound(int viewRowIndex, int viewColIndex) {
        String key = null;
        if (viewRowIndex >= 0 && viewRowIndex < getRowCount()) {
        	key = tm.getRowKey(convertRowIndexToModel(viewRowIndex));
        }
        if (key == null || foundSet.isEmpty()) {
            return false;
        }
        int colkey = convertColumnIndexToModel(viewColIndex);
        return foundSet.contains(key+colkey);
    }

    /**
     * reset Found keys
     *
     */
    public void resetFound() {
        foundSet.clear();
        repaint();
        requestFocus();
    }

    /**
     * does this cell have the specified value?
     * @param findValue
     * @param useCase
     * @param r
     * @param c
     * @return
     */
    private boolean searchCell(String findValue, boolean useCase, int r, int c) {
    	boolean found = false;
    	Object o = getValueAt(r, c);
    	if (o != null) {
    		found = Routines.isFound(o.toString(),findValue,useCase);
    	}

    	return found;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#findValue(java.lang.String, boolean, boolean, boolean, boolean)
	 */
	public boolean findValue(String findValue, boolean isMulti, boolean useCase, boolean isDown, boolean isWrap) {
		boolean found = false;
		int row = getSelectedRow();
		int col = getSelectedColumn();

		if(this.getRowCount()==0 || this.getColumnCount()==0){
			return found;
		}
		if (row<0){ // nothing selected
			row=0;
		}
		if (col<0){
			col=0;
		}

		if (!isFound(row, col)){
			// look in current cell
			found = searchCell(findValue, useCase, row, col);
		}

		if (!found){
			int startCol = col;
			int startRow = row;
			if(isDown){
				if(isMulti){
					// look in rest of current row
					for (int c=startCol+1; c<getColumnCount()&& !found; c++){
						found = searchCell(findValue, useCase, row, c);
						col = c;
					}

					// look at all following rows and all columns
					for (int c=0; c<getColumnCount()&& !found; c++){
						for (int r=startRow+1;r<getRowCount() && !found;r++) {
							found = searchCell(findValue, useCase, r, c);
							col = c;
							row = r;
						}
					}
				}else{ // just this column
					for (int r=startRow+1;r<getRowCount() && !found;++r) {
						found = searchCell(findValue, useCase, r, col);
						row = r;
					}
				}
				if (!found && isWrap){
				    UIManager.getLookAndFeel().provideErrorFeedback(null);
					// go from last row,col to startRow, startCol
					if(isMulti){
						// look at all previous rows and all columns
						colloop:for (int c=0; c<getColumnCount() && !found; c++){
							for (int r=0;r<getRowCount() && !found;r++) {
								found = searchCell(findValue, useCase, r, c);
								col = c;
								row = r;
								if (r==startRow && c == startCol){
									break colloop;
								}
							}
						}
					}else{ // just this column
						for (int r=0;r<startRow && !found;++r) {
							found = searchCell(findValue, useCase, r, col);
							row = r;
						}
					}
				}// end iswrap
			}// end is down
			else{
				if(isMulti){
					// look in rest of current row
					for (int c = startCol-1; c>=0 && !found; --c){
						found = searchCell(findValue, useCase, row, c);
						col = c;
					}
					// look at all preceding rows
					for (int c = getColumnCount()-1; c>=0 && !found; --c){
						for (int r = startRow-1;r>=0 && !found;--r) {
							found = searchCell(findValue, useCase, r, c);
							col = c;
							row = r;
						}
					}
				}else{ // just this column
					for (int r = startRow-1;r>=0 && !found;--r) {
						found = searchCell(findValue, useCase, r, col);
						row = r;
					}
				}
				if (!found && isWrap){
				    UIManager.getLookAndFeel().provideErrorFeedback(null);
					// go from last row,col to startRow, startCol
					if(isMulti){
						// look at all preceeding rows and all columns
						colloop:for (int c=getColumnCount()-1; c>=0 && !found; c--){
							for (int r=getRowCount()-1;r>=0 && !found;r--) {
								found = searchCell(findValue, useCase, r, c);
								col = c;
								row = r;
								if (r==startRow && c == startCol){
									break colloop;
								}
							}
						}
					}else{ // just this column
						for (int r=getRowCount()-1;r>startRow && !found;--r) {
							found = searchCell(findValue, useCase, r, col);
							row = r;
						}
					}
				}// end iswrap
			}// end is up
		}

		if (found){
			setFound(row,col);
			repaint();
		}
		return found;
	}

	/*
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#hasFound()
	 */
	public boolean hasFound() {
		return !foundSet.isEmpty();
	}

	/* used to enable the check multiple columns checkbox
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isMultiColumn()
	 */
	public boolean isMultiColumn() {
		return true;
	}

	/* do not enable any replace function
	 * (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#isReplaceable()
	 */
	public boolean isReplaceable() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Findable#replaceValue(java.lang.String, java.lang.String, boolean)
	 */
	public int replaceValue(String findValue, String replace, boolean useCase) {
		return NOT_FOUND;
	}

    public String getLockInformation() { return null;}

    public int replaceNextValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown, boolean isWrap)
    {
    	return NOT_FOUND;
    }

    public void replaceAllValue(String findValue, String replace, boolean isMulti, boolean useCase, boolean isDown){
    }

	/* (non-Javadoc)
	 * @see com.ibm.eacm.objects.Sortable#getTableTitle()
	 */
	public String getTableTitle() {
		return "Bookmark";
	}

}
