//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.table;



import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

import java.awt.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;


/**
 * where used table
 * @author Wendy Stimpson
 */
//$Log: WUsedTable.java,v $
//Revision 1.6  2013/09/19 22:00:02  wendy
//control sort when a row is updated
//
//Revision 1.5  2013/08/29 18:03:17  wendy
//check for relator id>0 in hasEntity
//
//Revision 1.4  2013/07/29 18:38:58  wendy
//Turn off autosort, update classifications after edit and resize cells
//
//Revision 1.3  2013/07/25 19:43:55  wendy
//make sure there is a row selected
//
//Revision 1.2  2012/12/03 19:40:31  wendy
//keep pickframe tofront if exception dialog is displayed
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class WUsedTable extends RSTTable
{
	private static final long serialVersionUID = 1L;

	private static final int ENTITY_DISPLAY_NAME = Integer.parseInt(WhereUsedItem.RELATEDENTITY);//4;

	private LabelRenderer childRenderer = null;
	private LabelRenderer childFoundRenderer = null;
	private LabelRenderer parentRenderer = null;
	private LabelRenderer parentFoundRenderer = null;
	private LabelRenderer assocRenderer = null;
	private LabelRenderer assocFoundRenderer = null;

    protected void setSortKeys(){
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
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // root nav name
        sortKeys.add(new RowSorter.SortKey(2, SortOrder.DESCENDING)); // direction
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING)); // entity description
        sortKeys.add(new RowSorter.SortKey(4, SortOrder.ASCENDING)); // entity display name
        setSortKeys(sortKeys);
    }

	protected RSTTableModel createTableModel(){
		return new WUsedTableModel(this);
	}


    /**
     * @param wList
     */
    public WUsedTable(WhereUsedList wList) {
    	super(wList.getTable(),wList.getProfile());

		assocRenderer = new LabelRenderer();
		assocRenderer.setColorKeys(PREF_COLOR_ASSOC, null);
		assocRenderer.setSelectionColorKeys(PREF_COLOR_ASSOC, PREF_COLOR_WU_BG);
		assocRenderer.setFocusBorderKeys(WUFOCUS_BORDER_KEY, null);
		assocFoundRenderer = new LabelRenderer();
		assocFoundRenderer.setFocusBorderKeys(FOUND_FOCUS_BORDER_KEY, FOUND_BORDER_KEY);
		childFoundRenderer = assocFoundRenderer; // same rendering as assocFound

		childRenderer = new LabelRenderer();
		childRenderer.setColorKeys(PREF_COLOR_CHILD, null);
		childRenderer.setSelectionColorKeys(PREF_COLOR_CHILD, PREF_COLOR_WU_BG);
		childRenderer.setFocusBorderKeys(WUFOCUS_BORDER_KEY, null);

		parentRenderer = new LabelRenderer();
		parentRenderer.setColorKeys(PREF_COLOR_PARENT, null);
		parentRenderer.setSelectionColorKeys(PREF_COLOR_PARENT, PREF_COLOR_WU_BG);
		parentRenderer.setFocusBorderKeys(WUFOCUS_BORDER_KEY, null);

		parentFoundRenderer = assocFoundRenderer; // same rendering as assocFound

		init();
	}
	/**
	 * get the accessibility resource key
	 * @return
	 */
	protected String getAccessibilityKey() {
		return "accessible.usedTable";
	}
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleRowNameAt(int)
     */
    public String getAccessibleRowNameAt(int _row) {
		Object o = getAccessibleValueAt(_row,0);
		if (o != null) {
			return o.toString();
		}
		return super.getAccessibleRowNameAt(_row);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.table.BaseTable#getAccessibleAttributeType(int, int)
     */
    protected String getAccessibleAttributeType(int _row, int _col) {
		Object o = getAccessibleValueAt(_row,3);
		if (o != null) {
			return o.toString();
		}
		return super.getAccessibleAttributeType(_row,_col);
	}
	/**
	 * used to enable actions
     * hasEntity
     * @return
     */
    public boolean hasEntity() {
    	return hasEntity(getSelectedRow()); // indexes are from table view
	}

	/**
     * hasEntity
     * @param viewRowIndex - view index
     * @return
     */
    public boolean hasEntity(int viewRowIndex) {
    	if(viewRowIndex==-1){
    		return false;
    	}
    	WhereUsedItem wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(convertRowIndexToModel(viewRowIndex));
    	if(wui==null){
    		return false;
    	}
    	//Object o = wui.get(WhereUsedItem.RELATEDENTITY, true);
    	//return Routines.have(o); // more is needed - prodstruct returns '::' and passes this test but doesnt exist
    	EntityItem rei = wui.getRelatedEntityItem();
    	EntityItem relei = wui.getRelatorEntityItem();
    	return (rei !=null && rei.getEntityID()>0 && relei != null && relei.getEntityID()>0);
	}

    /**
     * getCellRenderer
     *
     * @param viewRowIndex
     * @param viewColIndex
     * @return
     */
    public TableCellRenderer getCellRenderer(int viewRowIndex, int viewColIndex) {
    	String type = null;

    	WhereUsedItem wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(convertRowIndexToModel(viewRowIndex));
    	Object o = null;
    	if(wui != null){ //null should not happen
    		o=	wui.get(WhereUsedItem.DIRECTION, true);
    	}
    	if (o!=null){
    		type = o.toString();
    	}

    	if (isFound(viewRowIndex,viewColIndex)) {
    		if (WhereUsedGroup.CHILD.equalsIgnoreCase(type)) {
    			return childFoundRenderer;
    		} else if (WhereUsedGroup.ASSOC.equalsIgnoreCase(type)) {
    			return assocFoundRenderer;
    		} else {
    			return parentFoundRenderer;
    		}
    	}
    	if (WhereUsedGroup.CHILD.equalsIgnoreCase(type)) {
    		return childRenderer;
    	} else if (WhereUsedGroup.ASSOC.equalsIgnoreCase(type)) {
    		return assocRenderer;
    	} else {
    		return parentRenderer;
    	}
    }

    /**
     * dereference
     */
    public void dereference() {
		if (childRenderer != null) {
			childRenderer.dereference();
			childRenderer = null;
		}
		if (assocRenderer != null) {
			assocRenderer.dereference();
			assocRenderer = null;
		}
		if (assocFoundRenderer != null) {
			assocFoundRenderer.dereference();
			assocFoundRenderer = null;
		}
		childFoundRenderer = null;
		if (parentRenderer != null) {
			parentRenderer.dereference();
			parentRenderer = null;
		}

		parentFoundRenderer = null;
;
		super.dereference();
	}

	/**
     * getCurrentRelatedEntityItem
     * @return
     */
    private EntityItem getCurrentRelatedEntityItem() {
    	if(getSelectedRow()!=-1){
    		int row = convertRowIndexToModel(getSelectedRow()); // indexes are from table view
    		WhereUsedItem wi = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(row);
    		if (wi != null) {
    			return wi.getRelatedEntityItem();
    		}
    	}
		return null;
	}

    /**
     * historyInterface
     */
	public EntityItem getHistoryEntityItem(){
		return getCurrentRelatedEntityItem();
	}
	public EntityItem getHistoryRelatorItem(){
		EntityItem curitem = null;
		if(getSelectedRow()!=-1){
			int row = convertRowIndexToModel(getSelectedRow()); // indexes are from table view
			WhereUsedItem wi = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(row);
			if (wi != null) {
				curitem = wi.getRelatorEntityItem();
			}else{
				//msg6000.1 =No Parent Relator found in this action.
				com.ibm.eacm.ui.UI.showFYI(null, Utils.getResource("msg6000.1"));
			}
		}
		return curitem;
	}
    /**
     * getInformation
     *
     * @return
     */
    public String getInformation() {
    	WhereUsedItem wui = null;
    	if(getSelectedRow()!=-1){
    		int r = convertRowIndexToModel(getSelectedRow()); // indexes are from table view
    		wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(r);
    	}
		if (wui == null) {
			return Utils.getResource("nia");
		}
		return getInformation("Original", wui.getOriginalEntityItem()) + NEWLINE + NEWLINE +
		getInformation("Relator", wui.getRelatorEntityItem()) + NEWLINE+ NEWLINE +
        getInformation("Related", wui.getRelatedEntityItem());
	}

	private String getInformation (String _s, EntityItem _ei) {
		if (_ei == null) {
			return "";
		}
		return  _s + "_key: " + _ei.getKey() +
		NEWLINE + _s + "_EntityType: " + _ei.getEntityType() +
		NEWLINE + _s + "_EntityID: " + _ei.getEntityID();
//				NEWLINE + _s + "_Value: " + _ei.toString();
	}

	/**
     * the first selected row determines the actions supported
     * do any of the selected rows match the relatortype and direction and have an entity to act on
     * @return
     */
    public boolean selectionHasActionEntities() {
    	WhereUsedItem wui = getSelectedWhereUsedItem();
    	if(wui!=null){
      		String curKey = "";
    		EntityItem relator = wui.getRelatorEntityItem();
    		if (relator != null) {
    			curKey = relator.getEntityType() + wui.getDirection();
    		}

    		int[] _rows = getSelectedRows(); // indexes are from table view
    		for (int i=0;i<_rows.length;++i) {
    	    	int row = convertRowIndexToModel(_rows[i]);
    	    	WhereUsedItem wui2 = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(row);
    	    	String nextKey = "";
        		relator = wui2.getRelatorEntityItem();
        		if (relator != null) {
        			nextKey = relator.getEntityType() + wui2.getDirection();
        		}

    			if (curKey.equals(nextKey) && hasEntity(_rows[i])){
    				return true;
    			}
    		}
    	}
    	return false;
	}

	/**
     * check if selected rows have any entities
     * @return
     */
    public boolean selectionHasEntities() {
    	int[] _rows = getSelectedRows(); // indexes are from table view
    	for (int i=0;i<_rows.length;++i) {
    		if (hasEntity(_rows[i])){
    			return true;
    		}
    	}
    	return false;
	}
    /**
     * does any row have data
     * @return
     */
    public boolean hasAnyEntities() {
    	for (int i=0;i<this.getRowCount();++i) {
    		if (hasEntity(i)){
    			return true;
    		}
    	}
    	return false;
	}

    /**
     * get the WhereUsedItem for the first selected row
     * @return
     */
    public WhereUsedItem getSelectedWhereUsedItem(){
    	if(getSelectedRow()==-1){
    		return null;
    	}
    	int row = convertRowIndexToModel(getSelectedRow());
    	return ((WUsedTableModel)getModel()).getWhereUsedItemForRow(row);
    }

    /**
     * get the WhereUsedItem for the row
     * @param viewRowIndex
     * @return
     */
    public WhereUsedItem getWhereUsedItem(int viewRowIndex){
    	WhereUsedItem wui = null;
    	if (viewRowIndex >=0 && viewRowIndex < getRowCount()) {
    		int row = convertRowIndexToModel(viewRowIndex);
    		wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(row);
    	}
    	return wui;
    }
    /**
     * validate keys for removal with the user
     * @param _keys
     * @return
     */
    public String[] validateKeys(String[] _keys) {
    	String[] out = null;

    	int iReply = CLOSED;
    	Vector<String> v = new Vector<String>();
    	if(_keys.length==1){ // it will be valid because action is not enabled unless it is
    		int row = ((RSTTableModel)getModel()).getRowIndex(_keys[0]);
	    	// column may be hidden or exceed columncount, so go directly to the model
    		EANFoundation ef = ((RSTTableModel)getModel()).getEANObject(row, ENTITY_DISPLAY_NAME);

    		String info = ef.getParent().getLongDescription()+" - "+ef.getLongDescription();
    		//msg12009.0 = Remove link:\n   {0}?
    		iReply = com.ibm.eacm.ui.UI.showConfirmOkCancel(null,
    				Utils.getResource("msg12009.0",info));
    		if (iReply == OK_BUTTON) {
    			v.add(_keys[0]);
    		} else if (iReply == CLOSED || iReply==CANCEL_BUTTON) { // user cancelled
    		}
    	}else{
    		for (int i = 0; i < _keys.length; ++i) {
    			int row = ((RSTTableModel)getModel()).getRowIndex(_keys[i]);
    			WhereUsedItem wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(row);
    			Object o = wui.get(WhereUsedItem.RELATEDENTITY, true);
    			if (Routines.have(o)) { // might not have a value if multiple rows with diff entity types are selected
    				// and one of the rows doesnt have an entity
    				if (iReply == ALL_BUTTON) {
    					v.add(_keys[i]);
    				} else {
    			    	// column may be hidden, so go directly to the model
    					EANFoundation ef = ((RSTTableModel)getModel()).getEANObject(row, ENTITY_DISPLAY_NAME);
    					//msg12009.0 = Remove link:\n   {0}?
    			  		String info = ef.getParent().getLongDescription()+" - "+ef.getLongDescription();
    					iReply = com.ibm.eacm.ui.UI.showConfirmYesNoAllCancel(null,
    							Utils.getResource("msg12009.0",info));
    					if (iReply == YES_BUTTON || iReply == ALL_BUTTON) {
    						v.add(_keys[i]);
    					} else if (iReply == CLOSED || iReply==CANCEL_BUTTON) { // user cancelled
    						v.clear();
    						break;
    					}
    				}
    			}
    		}
    	}

    	if (!v.isEmpty()){
    		out = new String[v.size()];
    		v.copyInto(out);
    	}

        return out;
    }

    /**
     * this is called on the background thread
     * removeLink
     * @param _keys
     */
    public boolean removeLink(Profile prof,String[] _keys) {
    	boolean b = ((WUsedTableModel)getModel()).removeLink(prof,_keys);
    	if(b){
    		removeFound(_keys);
    	}
    	return b;
    }

    /* (non-Javadoc)
     * called on evt thread
     * @see com.ibm.eacm.table.RSTTable#updateTable()
     */
    public void updateTable(){
    	((WUsedTableModel)getModel()).refresh();

    	super.updateTable();
    }
    
    /* (non-Javadoc)
     * @see com.ibm.eacm.table.RSTTable#updateTableWithSelectedRows()
     */
    public void updateTableWithSelectedRows(){
      	((WUsedTableModel)getModel()).refresh(false);
		super.updateTableWithSelectedRows();
    }
	/**
     * getWhereUsedItems from selected rows
     * @return
     */
    public WhereUsedItem[] getWhereUsedItems() {
		int[] rows = getSelectedRows(); // these are from the view
		WhereUsedItem[] wi = new WhereUsedItem[rows.length];
		for (int r=0;r<rows.length;++r) {
			wi[r] = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(this.convertRowIndexToModel(rows[r]));
		}
		return wi;
	}

	/**
     * link
     * @param _lai
     * @param _parent
     * @param _child
     * @param prof
     * @param _c
     * @return
     */
    public Object linkChain(LinkActionItem _lai, EntityItem[] _parent, EntityItem[] _child, Profile prof, Component _c) {
    	return ((WUsedTableModel)getModel()).linkChain(_lai,_parent,_child,prof, _c);
    }

    /**
     * called on worker thread
     * @param itemsToLink
     * @param prof
     * @param comp
     * @return
     */
    public EANFoundation[] link(EntityItem[] itemsToLink, Profile prof,Component comp) {
    	return ((WUsedTableModel)getModel()).link(getSelectedKeys(), itemsToLink, BehaviorPref.getLinkTypeKey(), prof, comp);
    }

    /**
     * called on event thread after link
     * @param added
     */
    public void updateTable(EANFoundation added){
    	((WUsedTableModel)getModel()).refresh();

		// select first one added
		int rowid = ((RSTTableModel)getModel()).getRowIndex(added.getKey());
		int cvtrowid = this.convertRowIndexToView(rowid);
		setRowSelectionInterval(cvtrowid,cvtrowid); // if nothing is selected, it returns a number exceeding rowcount

		refreshTable(true);

	    scrollToRow(cvtrowid);
    }

    /**
     * get entityitems based on EANFoundation keys
     * @param _keys
     * @return
     */
    public EntityItem[] getEntityItems(String[] _keys) {
    	EntityItem[] ei = new EntityItem[ _keys.length];
    	for (int i = 0; i < _keys.length; ++i) {
    		WhereUsedItem wui = (WhereUsedItem)((WUsedTableModel)getModel()).getEANFoundationByKey(_keys[i]);
    		ei[i]=wui.getRelatedEntityItem();
    	}

        return ei;
    }
    /**
     * @param viewRowIndex
     * @return
     */
    public EntityItem getRelatedEntityItem(int viewRowIndex) {
    	EntityItem ei = null;
    	if (viewRowIndex >=0 && viewRowIndex < getRowCount()) {
    		if (hasEntity(viewRowIndex)){
    			WhereUsedItem wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(convertRowIndexToModel(viewRowIndex));
    			ei =  wui.getRelatedEntityItem();
    		}
    	}
    	return ei;
    }

    /**
     * @param mdlRowIndex
     * @return
     */
    public EntityItem getRelatedItemForMdlIndex(int mdlRowIndex) {
    	EntityItem ei = null;
    	if (mdlRowIndex >=0 && mdlRowIndex < getRowCount()) {
    		WhereUsedItem wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(mdlRowIndex);
    		ei =  wui.getRelatedEntityItem();
    	}
    	return ei;
    }
    /**
     * assumption these are valid model indexes
     * @param mdlRowIndex
     * @return
     */
    public EntityItem[] getRelatedItemForMdlIndexes(int mdlRows[]) {
    	EntityItem ei[] = new EntityItem[mdlRows.length];
    	for(int i=0; i<mdlRows.length; i++) {
    		WhereUsedItem wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(mdlRows[i]);
    		ei[i] =  wui.getRelatedEntityItem();
    	}
    	return ei;
    }

	/**
     * getEntityType
     * @param _row
     * @return
     */
    private String getEntityType(int viewRowIndex) {
    	String s = null;
    	if (viewRowIndex >=0 && viewRowIndex < getRowCount()) {
    		WhereUsedItem wui = ((WUsedTableModel)getModel()).getWhereUsedItemForRow(convertRowIndexToModel(viewRowIndex));
    		Object o = wui.get(WhereUsedItem.RELATEDENTITYTYPE, true);
    		if (o!=null){
    			s = o.toString();
    		}
    	}
    	return s;
	}

    /**
     * check that all selected rows are the same type and only return the keys for those with data in the vct
     * @param vct
     * @return
     */
    public boolean getMatchingSelectedKeys(Vector<String> vct) {
    	boolean allRowsSameType=true;
    	int firstrow = getSelectedRow();
		String key = getEntityType(firstrow);
		if (key != null) {
			int[] rows = getSelectedRows(); // indexes are from table view
			for (int i=0;i<rows.length;++i) {
				String str = getEntityType(rows[i]);
				if (key.equals(str)) {
					if (hasEntity(rows[i])) {
						vct.add(((WUsedTableModel)getModel()).getRowKey(convertRowIndexToModel(rows[i])));
					}
				}else{
					allRowsSameType = false;
					vct.clear();
					break;
				}
			}
		}
		return allRowsSameType;
    }

    public String getUIPrefKey() {
    	return "WUSED";
    }

    /* (non-Javadoc)
     * wu table is never editable, stop it here
     * @see javax.swing.JTable#isCellEditable(int, int)
     */
    public boolean isCellEditable(int _r, int _c) {
    	return false;
    }
}

