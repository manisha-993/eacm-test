//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.util.Vector;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import COM.ibm.eannounce.objects.*;

import com.ibm.eacm.actions.CopyAction;
import com.ibm.eacm.actions.CutAction;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.PasteAction;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.GridTable;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;

/**
 * this is the grid editor for multiple entities at one time
 * @author Wendy Stimpson
 */
//$Log: GridEditor.java,v $
//Revision 1.11  2014/09/10 13:28:46  wendy
//IN5515352 - apply parent to all selected rows in grid editor
//
//Revision 1.10  2013/11/15 17:39:30  wendy
//dont refreshmodel after adding row - user wants rows at bottom
//
//Revision 1.9  2013/11/07 18:09:40  wendy
//Add FillCopyEntity action
//
//Revision 1.8  2013/10/23 19:32:56  wendy
//IN4426481 - Function "Duplicate" - user wants new rows at bottom
//
//Revision 1.7  2013/10/10 21:40:48  wendy
//allow reset record for new entity
//
//Revision 1.6  2013/07/29 18:21:36  wendy
//do not canceledit on new entities upon select()
//
//Revision 1.5  2013/07/26 17:30:41  wendy
//support duplication of multiple rows
//
//Revision 1.4  2013/05/08 15:33:55  wendy
//add copy and paste to popup menu
//
//Revision 1.3  2013/05/01 18:35:14  wendy
//perf updates for large amt of data
//
//Revision 1.2  2013/04/04 14:55:24  wendy
//add check for cell iseditable when checking locks
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public class GridEditor extends EditorPanel
{
	private static final long serialVersionUID = 1L;
	private GridTable table = null;
	private MouseListener mouseListener;

	public Findable getFindable() {
		return table;
	}
	/**
	 * grid editor constructor
	 * @param ec
	 */
	protected GridEditor(EditController ec) {
		super(ec, DefaultToolbarLayout.EDIT_HORZ_BAR, null);

		table = new GridTable(getEditController().getEntityList(), getEditController().getEgRstTable(), getEditController());
		table.getSelectionModel().addListSelectionListener(getEditController()); // get notified when rows chg
		table.getColumnModel().getSelectionModel().addListSelectionListener(getEditController()); // get notified when cols chg

		createScrollPane(table);
		setTitle(getEditController().getEgRstTable().getTableTitle());
		createPopupMenu();

		mouseListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		};
		table.addMouseListener(mouseListener);

		addDataChangePropertyListener(table);

		table.addPropertyChangeListener(DATALOCKED_PROPERTY,(UnlockAction)ec.getAction(UNLOCK_ACTION));
		table.addPropertyChangeListener(DATALOCKED_PROPERTY,(LockAction)ec.getAction(LOCK_ACTION));
		table.addPropertyChangeListener(TCE_PROPERTY,(CutAction)ec.getAction(CUT_ACTION));

		// register our copy and paste actions for this table
		EACMAction act = getEditController().getAction(COPY_ACTION);
		if (act!=null){
			table.registerEACMAction(act, KeyEvent.VK_C, Event.CTRL_MASK);
		}
		act = getEditController().getAction(PASTE_ACTION);
		if (act!=null){
			table.registerEACMAction(act, KeyEvent.VK_V, Event.CTRL_MASK);
		}
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#print()
	 */
	protected void print() throws PrinterException{
		table.print();
	}

	/**
	 * createPopupMenu
	 */
	private void createPopupMenu() {
		popup = new JPopupMenu();

		popup.add(getEditController().getAction(MOVECOL_LEFT_ACTION));
		popup.add(getEditController().getAction(MOVECOL_RIGHT_ACTION));
		popup.addSeparator();

		popup.add(getEditController().getAction(FINDREP_ACTION));
		popup.add(getEditController().getAction(FINDNEXT_ACTION));
		popup.add(getEditController().getAction(HIDECOL_ACTION));
		popup.add(getEditController().getAction(UNHIDECOL_ACTION));
		popup.addSeparator();
		popup.add(getEditController().getAction(SORT_ACTION));
		popup.add(getEditController().getAction(FILTER_ACTION));
		popup.addSeparator();
		popup.add(getEditController().getAction(SELECTALL_ACTION));
		popup.add(getEditController().getAction(SELECTINV_ACTION));
		popup.add(getEditController().getAction(COPY_ACTION));
		popup.add(getEditController().getAction(PASTE_ACTION));
	}
	/**
	 * called from freezeaction.setenabled
	 * @return
	 */
	protected int getSelectedColumnCount(){
		return table.getSelectedColumnCount();
	}

	/**
	 * get the key for the entityitem currently selected, or row 0 if nothing is selected
	 *
	 * @return
	 */
	protected String getRecordKey() {
		return table.getRecordKey();
	}

	/**
	 * select entity and attribute - called when user switches editors
	 * or rolls back an attribute
	 * @param recKey
	 * @param selKey
	 */
	protected void setSelection(String recKey, String selKey) {
		table.setSelection(recKey, selKey);
	}

	/**
	 * freeze selected column
	 *
	 */
	protected void freeze() {
		scroll.setRowHeaderView(table.getRowHeader(table.getSelectedColumn()));
		scroll.setCorner(JScrollPane.UPPER_LEFT_CORNER, table.getHeader());
	}
	/**
	 * remove frozen column
	 *
	 */
	protected void thaw() {
		scroll.setRowHeaderView(null);
	}
	/**
	 * is this currently frozen
	 * called by thaw action.setenabled
	 * @return
	 */
	protected boolean isFrozen(){
		JViewport vp = scroll.getRowHeader();
		if(vp!=null){
			return vp.getView()!=null;
		}
		return false;
	}
	protected BaseTable getJTable() { return table;}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		table.setEnabled(enabled);
	}


	/*
	 * lock and unlock
	 */
	/**
	 * lock on bg thread - called by lockaction
	 */
	protected void lock() {
		table.lockRows();
	}
	/* (non-Javadoc)
	 * show lock results on event thread
	 * @see com.ibm.eacm.edit.EditorPanel#checkLockStatus()
	 */
	protected void checkLockStatus() {
		table.checkLockStatus();
		repaint();
	}

	/* (non-Javadoc)
	 * selection is required for this, with an existing lock and not new
	 * @see com.ibm.eacm.edit.EditorPanel#hasLocks()
	 */
	protected boolean hasLocks(){
		int[] rr = table.getSelectedRows();
		for (int r=0; r<rr.length; r++){
			int viewrow = rr[r];
			for(int c=0; c<table.getColumnCount(); c++){
				if(table.isCellEditable(viewrow, c) && // lock prodstruct also locks the model, check if really editable
						table.isCellLocked(viewrow, c)){
					if(!((EntityItem)table.getSelectedItem(viewrow)).isNew()){
						return true;
					}
				}
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * selection is required, return all locked row ids, not new rows
	 * @see com.ibm.eacm.edit.EditorPanel#getSelectedLockedIds()
	 */
	protected Vector<Integer> getSelectedLockedIds(){
		Vector<Integer> vct = new Vector<Integer>();
		int[] rr = table.getSelectedRows();
		rowloop:for (int r=0; r<rr.length; r++){
			int viewid = rr[r];
			for(int c=0; c<table.getColumnCount(); c++){
				if(table.isCellLocked(viewid, c)){
					if(!((EntityItem)table.getSelectedItem(viewid)).isNew()){
						vct.add(new Integer(viewid));
						continue rowloop;
					}
				}
			}
		}
		return vct;
	}
	/**
	 * get row ids for changed entities.  vertical and form editors will only have the one item
	 * used for resetall action
	 * @return vector of view row ids
	 */
	protected Vector<Integer> getChangedRowIds(){
		Vector<Integer> viewRowsVct = new Vector<Integer>();
		for (int r=0; r<table.getRowCount(); r++){
			EntityItem ean = (EntityItem)table.getSelectedItem(r);
			if(ean.hasChanges()){
				//default values are not rolled back when unlocked
				for(int c=0; c<table.getColumnCount(); c++){
					if(table.isCellLocked(r, c)){
						viewRowsVct.add(new Integer(r));
						break;
					}
				}
			}else{
				//look for attr chgs that have not been put into the entity yet
				for(int c=0; c<table.getColumnCount(); c++){
					if(table.hasNewChanges(r, c)){
						viewRowsVct.add(new Integer(r));
						break;
					}
				}
			}
		}

		return viewRowsVct;
	}
	protected Vector<Integer> getSelectedNewRowIds(){
		Vector<Integer> viewRowsVct = new Vector<Integer>();
		int rr[] = table.getSelectedRows();
		for (int r=0; r<rr.length; r++){
			EntityItem ean = (EntityItem)table.getSelectedItem(rr[r]);
			if(ean.isNew()){
				viewRowsVct.add(new Integer(rr[r]));
			}
		}
		return viewRowsVct;
	}
	/* (non-Javadoc)
	 * selection is required for this, only return rowids for those that have changes
	 * @see com.ibm.eacm.edit.EditorPanel#getSelectedChangedRowIds()
	 */
	protected Vector<Integer> getSelectedChangedRowIds(){
		int[] rr = table.getSelectedRows();
		Vector<Integer> vct = new Vector<Integer>();
		for (int r=0; r<rr.length; r++){
			EntityItem ean = (EntityItem)table.getSelectedItem(rr[r]);
			if(ean.hasChanges()){
				vct.add(new Integer(rr[r]));
			}
		}
		return vct;
	}
	
    /**
     * get keys for changed entities.  vertical and form editors will only have the one item
     * used for determining what was commited
     * @return vector of view row ids
     */
	protected Vector<String> getSelectedChangedKeys(){
		int[] rr = table.getSelectedRows();
		Vector<String> vct = new Vector<String>();
		for (int r=0; r<rr.length; r++){
			EntityItem ean = (EntityItem)table.getSelectedItem(rr[r]);
			if(ean.hasChanges()){
				vct.add(ean.getKey());
			}
		}
		return vct;
	}
    /**
     * get keys for changed entities.  vertical and form editors will only have the one item
     * used for determining what was committed - used for saveall
     * @return vector of view row ids
     */
	protected Vector<String> getChangedKeys(){
		Vector<String> vct = new Vector<String>();
		for (int r=0; r<table.getRowCount(); r++){
			EntityItem ean = (EntityItem)table.getSelectedItem(r);
			if(ean.hasChanges()){
				vct.add(ean.getKey());
			}
		}
		return vct;
	}

	/**
	 * rollback selected rows
	 * @return
	 */
	protected boolean canResetRecord(){
		//get selected items
		int rr[] = table.getSelectedRows();
		for(int i=0; i<rr.length; i++){
			EntityItem ei = (EntityItem)table.getSelectedItem(rr[i]);
			if (ei!=null //&& !ei.isNew() 
					&& ei.hasChanges()){
				return true;
			}
		}
		return false;
	}

	/**
	 * only meaningful in grid editor
	 * called by unlockaction.choosetosave
	 * highlight the specified row
	 * @param viewrowid
	 */
	protected void selectRow(int viewrowid){
		table.selectRow(viewrowid);
	}

	/**
	 * unlock - done on bg thread - called by unlock action
	 *
	 */
	protected void unlock(Vector<Integer> locksVct) {
		table.unlockRows(locksVct);
	}
	/**
	 * determine when the lock action can be executed
	 * @return
	 */
	protected boolean canLock() {
		if(getEditController().isEditable()){
			return table.hasSelectedUnlockedRows();
		}
		return false;
	}

	/**
	 * needed to put focus in the table
	 */
	public boolean requestFocusInWindow() {
		if (table != null) {
			return table.requestFocusInWindow();
		} else {
			return super.requestFocusInWindow();
		}
	}


	/**
	 * refresh
	 *
	 */
	protected void refreshEditor() {
		table.resizeCells();
		table.revalidate();
		table.repaint();
	}


	/**
	 * getHelpText
	 *
	 * @return
	 */
	protected String getHelpText() {
		return table.getHelpText();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#canStopEditing()
	 */
	protected boolean canStopEditing() {
		return table.canStopEditing();
	}

	/**
	 * called from chooseToSave to load the entity item into form or vertical editors or select the row in
	 * grid editor
	 * @param ei
	 */
	protected void setEntityItem(EntityItem ei){
		table.selectRow(ei);
	}

	/**
	 * dereference
	 *
	 */
	public void dereference() {
		table.getSelectionModel().removeListSelectionListener(getEditController()); // get notified when rows chg
		//    table.getColumnModel().addColumnModelListener(this); // get notified when cols chg

		table.getColumnModel().getSelectionModel().removeListSelectionListener(getEditController()); // get notified when cols chg

		table.removeMouseListener(mouseListener);
		mouseListener = null;

		removeDataChangePropertyListener(table);

		table.removePropertyChangeListener(DATALOCKED_PROPERTY,(UnlockAction)getEditController().getAction(UNLOCK_ACTION));
		table.removePropertyChangeListener(DATALOCKED_PROPERTY,(LockAction)getEditController().getAction(LOCK_ACTION));
		table.removePropertyChangeListener(TCE_PROPERTY,(CutAction)getEditController().getAction(CUT_ACTION));

		table.unregisterEACMAction(getEditController().getAction(PASTE_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
		table.unregisterEACMAction(getEditController().getAction(COPY_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);

		table.dereference();
		table = null;

		super.dereference();
	}

	/**
	 * setParentItem this only does the first item selected, need to do all
	 * @param parent
	 *
	protected void setParentItem(EntityItem parent) {
		EntityItem currentItem = (EntityItem)getSelectedEAN();
		RowSelectableTable rst = getEditController().getEgRstTable();
		int r = rst.getRowIndex(currentItem.getKey());
		rst.setParentEntityItem(r, parent);
	}*/
    /**
     * IN5515352
     * setParentItem on ALL selected rows
     * @param parent
     */
    protected void setParentItem(EntityItem parent) {
        int[] rr = table.getSelectedRows();
		RowSelectableTable rst = getEditController().getEgRstTable();
        for (int i = 0; i < rr.length; i++) {
    		EntityItem currentItem = (EntityItem)table.getSelectedItem(rr[i]);
    		int r = rst.getRowIndex(currentItem.getKey());
    		rst.setParentEntityItem(r, parent);
        }
    }

	/**
	 * rollback everything that has changes, removing any new rows
	 *
	 */
	protected void rollback() {
		// get all changed rowids
		Vector<Integer> viewRowsVct = getChangedRowIds();

		// reset any rows to default values and remove new rows
		table.rollbackRows(viewRowsVct);

		viewRowsVct.clear();
	}


	/**
	 * removing any new rows
	 *
	 */
	protected void removeNew() {
		// get all changed rowids
		Vector<Integer> viewRowsVct = getSelectedNewRowIds();

		// remove new rows
		table.rollbackRows(viewRowsVct);

		viewRowsVct.clear();
		// refresh the rst and the implicators list
		refreshModel();
	}

	/* (non-Javadoc)
	 * called by ResetRecordAction on evt thread
	 * @see com.ibm.eacm.edit.EditorPanel#rollbackRow()
	 */
	protected void rollbackRow() {
		// rollback all selected rows
		// get all changed rowids
		Vector<Integer> chgsIdVct = getSelectedChangedRowIds();
		for (int i=0; i<chgsIdVct.size(); i++){
			int viewrow = chgsIdVct.elementAt(i).intValue();
			table.rollbackRow(viewrow);
		}
		table.resizeCells();
		chgsIdVct.clear();
	}

	/* (non-Javadoc)
	 * called by unlock action when user doesnt want to save chgs
	 * @see com.ibm.eacm.edit.EditorPanel#rollbackRows(java.util.Vector)
	 */
	protected void rollbackRows(Vector<Integer>chgsvct){
		cancelCurrentEdit();
		table.rollbackRows(chgsvct);

		/*	for(int i=0; i<chgsvct.size(); i++){
    		// get viewrow index for this modelindex
    		int viewrow = table.convertRowIndexToView(chgsvct.elementAt(i));
        	table.rollbackRow(viewrow);
    	}this does one at a time and moves selection - do it in one swoop
		 */
	}
	/**
	 *
	 * called by unlock action
	 * @param chgsvct
	 */
	protected void commitRows(Vector<Integer>chgsvct)throws Exception {
		//String[] keys = new String[chgsvct.size()];
		//chgsvct.copyInto(keys);
		//int	mdlrows[] = table.getRowIndexes(keys);

		for(int i=0; i<chgsvct.size(); i++){
			// get viewrow index for this modelindex
			int viewrow = table.convertRowIndexToView(chgsvct.elementAt(i));
			EntityItem ei = (EntityItem)table.getSelectedItem(viewrow);
			DBUtils.commit(ei);
		}
	}

	/**
	 * called on event dispatch thread when done updating editors
	 */
	protected void resizeAndRepaint() {
		table.resizeCells();
		repaint();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#rollbackSingle(java.lang.String)
	 */
	protected void rollbackSingle(String attrKey) {
		table.rollbackSingle();
	}

	/**
	 * called by editctrl when new row is added - or deleted
	 * must update the table model references
	 */
	protected void refreshModel(){
		table.clearSelection(); // clear any selection, it may have been deleted
		table.refresh();
	}
	/**
	 * called by editctrl when new row is added - highlight the first cell in the newly added row
	 */
	protected void selectModelRow(int mdlrowid){
		table.selectModelRow(mdlrowid);
	}
	/**
	 * get selected EntityItems
	 *
	 * @return
	 */
	protected EntityItem[] getSelectedEntityItems(){
		int[] rr = table.getSelectedRows();
		EntityItem[] eai = null;
		Vector<EntityItem> vct = new Vector<EntityItem>();
		for (int r=0; r<rr.length; r++){
			EntityItem ean = (EntityItem)table.getSelectedItem(rr[r]);
			vct.add(ean);
		}
		eai = new EntityItem[vct.size()];
		vct.copyInto(eai);

		return eai;
	}
	protected EntityItem getCurrentEntityItem() {
		return (EntityItem)table.getSelectedItem();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#duplicate(int)
	 */
	protected void duplicate(int copies){
    	//support multiple rows
		RowSelectableTable rst = getEditController().getEgRstTable();

        int[] rows = table.getSelectedRows();
        int iLastRow = table.getRowCount();

		for (int r=0;r<rows.length;++r) {
			String rowkey = table.getEANKey(rows[r]);
			if (rowkey != null) {
				rst.duplicateRow(table.getRowIndex(rowkey), copies);
			}
		}
	//	refreshModel(); // this sorts and puts at top - user wants them all at the end

        rowsAdded(iLastRow); //IN4426481 - Function "Duplicate" put new rows at bottom)
	}
	/**
	 * added rows, user wants to put them at the end - dont allow sort to be done
	 * @param firstNewRow
	 */
	protected void rowsAdded(int firstNewRow) {
	    int col = table.getSelectedColumn();
		table.rowsAdded(firstNewRow); 
		table.setRowSelectionInterval(firstNewRow, firstNewRow);
		if(col<0){
			col = 0;
		}
		table.scrollToRowCol(firstNewRow,col);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#moveToError(COM.ibm.eannounce.objects.EANBusinessRuleException)
	 */
	protected void moveToError(EANBusinessRuleException ebre) {
		table.moveToError(ebre);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#commit()
	 */
	protected void commit() throws Exception {
		//get selected items
		int rr[] = table.getSelectedRows();
		for(int i=0; i<rr.length; i++){
			EntityItem ei = (EntityItem)table.getSelectedItem(rr[i]);
			if (ei!=null && ei.hasChanges()){
				DBUtils.commit(ei);
			}
		}
	}

	/* (non-Javadoc)
	 * does any selected item have a change
	 * @see com.ibm.eacm.edit.EditorPanel#hasChanges()
	 */
	protected boolean hasChanges() {
		boolean chgs = false;
		if(hasLocks()){ // default values are not rolled back when unlocked
			chgs = table.hasChangedAttrSelected();
			if(!chgs){
				//get selected items
				int rr[] = table.getSelectedRows();
				for(int i=0; i<rr.length; i++){
					EntityItem ei = (EntityItem)table.getSelectedItem(rr[i]);
					if (ei!=null && (ei.hasChanges() || ei.isNew())){
						chgs = true;
						break;
					}
				}
			}
		}else{
			//get selected items
			int rr[] = table.getSelectedRows();
			for(int i=0; i<rr.length; i++){
				EntityItem ei = (EntityItem)table.getSelectedItem(rr[i]);
				if (ei!=null && ei.isNew()){
					chgs = true;
					break;
				}
			}
		}
		
		return chgs;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#hasAnyChanges()
	 */
	protected boolean hasAnyChanges() { 
		Vector<Integer>vct = getChangedRowIds();
		boolean chgs = vct.size()>0;
		vct.clear();
		return chgs;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#hasNewSelected()
	 */
	protected boolean hasNewSelected() {
		boolean ok = false;
		//get selected items
		int rr[] = table.getSelectedRows();
		for(int i=0; i<rr.length; i++){
			EntityItem ei = (EntityItem)table.getSelectedItem(rr[i]);
			if(ei!=null){
				if(ei instanceof VEEditItem){
					ok = ((VEEditItem)ei).isAllNew();
				}else{
					ok = ei.isNew();
				}
				if(ok){
					break;
				}
			}
		}
		return ok;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#isNew()
	 */
	protected boolean isNew() {
		//get selected items
		int rr[] = table.getSelectedRows();
		if(rr.length==1){
			EntityItem ei = (EntityItem)table.getSelectedItem(rr[0]);
			if (ei!=null && ei.isNew()){
				return true;
			}
		}
		return false;
	}


	/**
	 * getSelectedKeys
	 *
	 * @return
	 */
	protected String[] getSelectedKeys() {
		if (table != null) {
			return table.getSelectedKeys();
		}
		return null;
	}

	/**
	 * refresh the display
	 */
	protected void refreshDisplay() {
		table.resizeCells();
	}

	/**
	 * used by attribute history action
	 * @return
	 */
	protected EANAttribute getSelectedAttribute(){
		return table.getSelectedAttribute();
	}

	protected boolean hasFiltered() {
		return table.isFiltered();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#getSelectedEAN()
	 */
	protected EANFoundation getSelectedEAN() {
		return table.getSelectedItem();
	}

	/* (non-Javadoc)
	 * called when the editor tab is selected
	 * @see com.ibm.eacm.edit.EditorPanel#select()
	 */
	protected void select(){
		// this can happen if user unlocks in the lockactiontab
		if(table.isEditing() && !hasLocks() && !isNew()){
			table.cancelCurrentEdit();
		}
	}

	protected boolean hasHiddenAttributes(){
		return table.hasHiddenAttributes();
	}

	protected void cancelCurrentEdit(){
		table.cancelCurrentEdit();
	}
	protected boolean stopCellEditing(){
		if(table.isEditing()){
			return table.getCellEditor().stopCellEditing();
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#getSelectedEANMetaAttribute()
	 */
	protected EANMetaAttribute getSelectedEANMetaAttribute(){
		return table.getSelectedEANMetaAttribute();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#deactivateAttribute()
	 */
	protected void deactivateAttribute(){
		table.deactivateAttribute();
		table.firePropertyChange(DATACHANGE_PROPERTY, true, false);
	}
	/**
	 * used by deactivateattraction to enable it
	 * @return
	 */
	protected boolean hasEditableAttrSelected() {
		return table.hasEditableAttrSelected();
	}
	/**
	 * used by FillPasteAction to enable it
	 * @return
	 */
	protected boolean canFillPaste() {
		return table.canFillPaste();
	}
	/**
	 * used by FillPasteAction
	 */
	protected void fillPaste() {
		table.fillPaste();
	}
	/**
	 * used by FillPasteAction - fire table events and repaint
	 */
	protected void finishAction() {
		table.finishAction();
	}

	protected String getSelectionKey(){
		return table.getSelectionKey();
	}
	/* (non-Javadoc)
	 * only used by ResetSingleAction
	 * @see com.ibm.eacm.edit.EditorPanel#hasChangedAttrSelected()
	 */
	protected boolean hasChangedAttrSelected() {
		return table.hasChangedAttrSelected();
	}

	/**
	 * can spell check be done on the selected attributes
	 * @return
	 */
	protected boolean isSpellCheckable(){
		return table.isSpellCheckable();
	}

	/**
	 * execute spell check on this handler
	 * only text and longtext will have a spellcheck handler
	 * @return
	 */
	protected SpellCheckHandler getSpellCheckHandler(){
		return table.getSpellCheckHandler();
	}

	protected void updateTableActions() {
		this.getEditController().updateTableActions(table);
		EACMAction act =  this.getEditController().getAction(COPY_ACTION);
		if (act!=null){
			((CopyAction)act).setTable(table);
		}
		act =  this.getEditController().getAction(PASTE_ACTION);
		if (act!=null){
			((PasteAction)act).setTable(table);
		}
		act =  this.getEditController().getAction(CUT_ACTION);
		if (act!=null){
			((CutAction)act).setTable(table);
		}
		act =  this.getEditController().getAction(FILLCOPY_ACTION);
		if (act!=null){
			((FillCopyAction)act).setTable(table);
		}
		
		act =  this.getEditController().getAction(FILLCOPYENTITY_ACTION);
		if (act!=null){
			((FillCopyEntityAction)act).setTable(table);
		}

		act =  this.getEditController().getAction(FILLAPPEND_ACTION);
		if (act!=null){
			((FillAppendAction)act).setTable(table);
		}
	}
}