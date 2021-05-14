package com.ibm.eacm.table;

import java.util.List;

import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.ibm.eacm.preference.BehaviorPref;

import COM.ibm.eannounce.objects.RowSelectableTable;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * table used for edits when data is displayed horizontally
 *
 */
//$Log: HorzTable.java,v $
//Revision 1.3  2013/09/19 22:01:46  wendy
//turn off sort on model change
//
//Revision 1.2  2013/07/29 18:41:58  wendy
//add cvs logging
//
public abstract class HorzTable extends RSTTable {
	private static final long serialVersionUID = 1L;

	/**
	 * @param table
	 * @param prof
	 */
	public HorzTable(RowSelectableTable table, Profile prof) {
		super(table, prof);
	}
	
	/**
	 * init
	 *
	 */
	protected void init() {
		super.init();

		// users dont want the edit to autosort
		if(getRowSorter()!=null){
			((TableRowSorter<?>)getRowSorter()).setSortsOnUpdates(BehaviorPref.isEditAutoSort());
		}
	}

	/* (non-Javadoc)
	 * called by sortdialog when user has specified desired sort
	 * and when mouse is clicked on the rowheaderlabel
	 * @see com.ibm.eacm.objects.Sortable#sort(List <RowSorter.SortKey> list);
	 */
	public void sort(List <RowSorter.SortKey> list){
		// grid editor should not sort automatically
		if(getRowSorter()==null){
			setRowSorter(new TableRowSorter<TableModel>(getModel()));
		}

		super.sort(list);
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.table.RSTTable#resizeAfterEdit(int, int)
	 */
	protected void resizeAfterEdit(int row, int col) { 
		resizeColumnAfterEdit(col);
	}

}
