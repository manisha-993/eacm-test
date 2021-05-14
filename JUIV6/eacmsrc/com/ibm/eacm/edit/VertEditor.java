//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;


import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.util.Vector;

import javax.swing.JPopupMenu;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.VEEditItem;

import COM.ibm.eannounce.objects.RowSelectableTable;

import com.ibm.eacm.actions.CopyAction;
import com.ibm.eacm.actions.CutAction;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.PasteAction;
import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;

import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.table.VertTable;


/**************
 *
 * this is used for displaying a vertical editor
 * @author Wendy Stimpson
 */
public class VertEditor extends EditorPanel
{
	private static final long serialVersionUID = 1L;
	private VertTable table = null;
	private EntityItem currentItem = null;
	private MouseListener mouseListener;

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#getFindable()
	 */
	public Findable getFindable() {
		return table;
	}
	/**
	 * created by the EditController
	 *
	 * @param ei
	 * @param ec
	 */
	protected VertEditor(EntityItem ei, EditController ec) {
		super( ec, DefaultToolbarLayout.EDIT_VERT_BAR, ec.getRecordToggle());
		currentItem = ei;
		table = new VertTable(getEditController(),currentItem);
	    table.getSelectionModel().addListSelectionListener(getEditController()); // get notified when rows chg
	    table.getColumnModel().getSelectionModel().addListSelectionListener(getEditController()); // get notified when cols chg

	    // register our copy and paste actions for this table
		EACMAction act = getEditController().getAction(COPY_ACTION);
		if (act!=null){
			table.registerEACMAction(act, KeyEvent.VK_C, Event.CTRL_MASK);
		}
		act = getEditController().getAction(PASTE_ACTION);
		if (act!=null){
			table.registerEACMAction(act, KeyEvent.VK_V, Event.CTRL_MASK);
		}
	/*	act = getEditController().getAction(CUT_ACTION);
		if (act!=null){
			table.registerEACMAction(act, KeyEvent.VK_X, Event.CTRL_MASK);
		}*/
		createScrollPane(table);
        createPopupMenu();

		mouseListener = new MouseAdapter() {
			public void mouseReleased(MouseEvent evt) {
				if (evt.isPopupTrigger()) {
					popup.show(evt.getComponent(), evt.getX(), evt.getY());
				}
			}
		};
        table.addMouseListener(mouseListener);

		setTitle(getEditController().getEgRstTable().getTableTitle());

	    addDataChangePropertyListener(table);

		table.addPropertyChangeListener(DATALOCKED_PROPERTY,(UnlockAction)ec.getAction(UNLOCK_ACTION));
		table.addPropertyChangeListener(DATALOCKED_PROPERTY,(LockAction)ec.getAction(LOCK_ACTION));
	    table.addPropertyChangeListener(TCE_PROPERTY,(CutAction)ec.getAction(CUT_ACTION));
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
	}

	/**
	 * needed to put focus on the table
	 */
	public boolean requestFocusInWindow() {
		if (table != null) {
			return table.requestFocusInWindow();
		} else {
			return super.requestFocusInWindow();
		}
	}

	/**
	 * called to change the RowSelectableTable in the tablemodel
	 *
	 * @param ei
	 */
	protected void updateRSTModel(EntityItem ei){
		currentItem = ei;
		table.updateModel(ei);
		table.refreshClassification();

		// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		table.firePropertyChange(DATACHANGE_PROPERTY, true, false);
	}

	/**
	 * isNew
	 *
	 * @return
	 */
	protected boolean isNew() {
		if(currentItem==null){ // could happen if new row is deleted in the vertical editor
			return false;
		}
		return currentItem.isNew();
	}

	/**
	 * refresh
	 */
	protected void refreshEditor() {
		//why do this before?
		//table.resizeCells();
		//table.revalidate();
		//table.repaint();
		EANFoundation ean = getEditController().getEgRstTable().getRow(getRecordKey());
		setEntityItem((EntityItem)ean);
		table.resizeCells();
		table.revalidate();
		table.repaint();
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

	/*
	 * lock and unlock
	 */
	/**
	 * lock - done on bg thread
	 */
	protected void lock() {
		table.lockRows();
	}
	/* (non-Javadoc)
	 * done on event dispatch thread
	 * @see com.ibm.eacm.edit.EditorPanel#checkLockStatus()
	 */
	protected void checkLockStatus() {
		table.checkLockStatus();
		repaint();
	}

	/* (non-Javadoc)
	 * selection is not required for this, only an existing lock and not new
	 * @see com.ibm.eacm.edit.EditorPanel#hasLocks()
	 */
	protected boolean hasLocks(){
//		EntityItem ei = table.getCurrentEntityItem(); // null if was new and was deleted in editctrller
		if(currentItem!=null && !currentItem.isNew()){
			for (int r=0; r<table.getRowCount(); r++){
				for(int c=0; c<table.getColumnCount(); c++){
					if(table.isCellEditable(r, c) && // lock prodstruct also locks the model, check if really editable
							table.isCellLocked(r, c)){
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * unlock - done on background thread
	 *
	 */
	protected void unlock(Vector<Integer> locksVct) {
		table.unlockRows();
	}

	/**
	 * determine when the lock action can be executed
	 * @return
	 */
	protected boolean canLock() {
		if(getEditController().isEditable()){
			if(currentItem!=null && !currentItem.isNew()){
				for (int r=0; r<table.getRowCount(); r++){
					if(!table.isCellLocked(r, 1)){
						return true;
					}
				}
			}
		}
		return false;
	}

    /**
     * called on event dispatch thread when done updating editors
     */
    protected void resizeAndRepaint() {
    	table.resizeCells();
    	repaint();
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
	 * @see com.ibm.eacm.edit.EditorPanel#stopCellEditing()
	 */
	protected boolean stopCellEditing(){
		return table.stopCellEditing();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#canStopEditing()
	 */
	protected boolean canStopEditing() {
		return table.canStopEditing();
	}

	protected BaseTable getJTable() { return table;}

	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		table.setEnabled(enabled);
	}

	/**
	 * called from chooseToSave to load the entity item into form or vertical editors or select the row in
	 * grid editor
	 * @param ei
	 */
	protected void setEntityItem(EntityItem ei){
		updateRSTModel(ei);
		// set key in recordtoggle
		getEditController().getRecordToggle().setCurrentKey(ei.getKey());
	}

	/**
	 * dereference
	 */
	public void dereference() {
		currentItem = null;

		table.unregisterEACMAction(getEditController().getAction(PASTE_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
		table.unregisterEACMAction(getEditController().getAction(COPY_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);

    	table.getSelectionModel().removeListSelectionListener(getEditController()); // get notified when rows chg
    	table.getColumnModel().getSelectionModel().removeListSelectionListener(getEditController()); // get notified when cols chg
    	table.removeMouseListener(mouseListener);
    	mouseListener = null;

	    removeDataChangePropertyListener(table);

        table.removePropertyChangeListener(DATALOCKED_PROPERTY,(UnlockAction)getEditController().getAction(UNLOCK_ACTION));
		table.removePropertyChangeListener(DATALOCKED_PROPERTY,(LockAction)getEditController().getAction(LOCK_ACTION));
	    table.removePropertyChangeListener(TCE_PROPERTY,(CutAction)getEditController().getAction(CUT_ACTION));

		table.dereference();
		table = null;

		super.dereference();
	}

	/**
	 * setParentItem
	 * @param parent
	 */
	protected void setParentItem(EntityItem parent) {
		RowSelectableTable rst = getEditController().getEgRstTable();
		int r = rst.getRowIndex(currentItem.getKey());
		rst.setParentEntityItem(r, parent);
	}

	/**
	 * getRecordKey
	 *
	 * @return
	 */
	protected String getRecordKey() {
		if(currentItem==null){
			return null;
		}
		return currentItem.getKey();
	}

	/**
	 * load the table with the recordkey and select the attribute with the selkey
	 * @param recKey
	 * @param selKey
	 */
	protected void setSelection(String recKey, String selKey) {
		if (recKey != null) {
			if(currentItem !=null  && !currentItem.getKey().equals(recKey)){
				currentItem = (EntityItem)getEditController().getEgRstTable().getRow(recKey);
				updateRSTModel(currentItem);
			}

		}
		table.setSelection(selKey);
	}

	/**
	 * rollback entire row for this entity
	 */
	protected void rollback() {
		int row = table.getSelectedRow();
		EntityItem curItem = getCurrentEntityItem();
		int mdlid = getEditController().getEgRstTable().getRowIndex(curItem.getKey());

		getEditController().getEgRstTable().rollback(mdlid);
		updateRSTModel(curItem);

		// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		table.firePropertyChange(DATACHANGE_PROPERTY, true, false);
		if(row==-1){
			row = 0;
		}
		table.setRowSelectionInterval(row, row);
		table.setColumnSelectionInterval(1, 1);
		table.scrollToRowCol(row,1);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#getCurrentEntityItem()
	 */
	protected EntityItem getCurrentEntityItem() {return currentItem;}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#duplicate(int)
	 */
	protected void duplicate(int copies){
		RowSelectableTable rst = getEditController().getEgRstTable();
        int iRow = rst.getRowIndex(getCurrentEntityItem().getKey());
        rst.duplicateRow(iRow, copies);
	}
	    
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#rollbackRow()
	 */
	protected void rollbackRow() {
		rollback();
	}

	/**
	 * rollbackSingle
	 *
	 */
	protected void rollbackSingle(String attrKey) {
		table.rollbackSingle();
	}

	/**
	 * commit the current changes to the pdh
	 *
	 * @return
	 */
	protected void commit() throws Exception {
		DBUtils.commit(currentItem);
	}

	/**
	 * hasChanges only looks at the one item in this editor
	 *
	 * @return
	 */
	protected boolean hasChanges() {
		if(currentItem!=null && currentItem.isNew()){
			return true;
		}
		if(currentItem==null ||  // could happen if new row is deleted in the vertical editor
				!hasLocks()){// default values are not rolled back when unlocked
			return false;
		}
		return currentItem.hasChanges() || table.hasChangedAttrSelected();
	}

    protected boolean hasNewSelected() {
		EntityItem ei = getCurrentEntityItem(); // null if was new and was deleted in editctrller
		boolean ok = false;
		if(ei!=null){
			if(ei instanceof VEEditItem){
				ok = ((VEEditItem)ei).isAllNew();
			}else{
				ok = ei.isNew();
			}
		}
		return ok;
    }


	/**
	 * getEntityItems
	 *
	 * @return
	 */
	protected EntityItem[] getSelectedEntityItems() {
		if(currentItem==null){
			return null;
		}
		EntityItem[] out = new EntityItem[1];
		out[0] = currentItem;
		return out;
	}

	/**
	 * moveToError
	 * @param ebre
	 */
	protected void moveToError(EANBusinessRuleException bre) {
		//get entityitem affected, is it currently loaded in this editor?
		Object o = bre.getObject(0);
		EntityItem ei = null;
		String attrcode = null;
		String columnKey = null;
		if (o instanceof EANAttribute) {
			EANAttribute att = (EANAttribute) o;
			ei = (EntityItem) att.getParent();
			attrcode = att.getAttributeCode();
			columnKey = Utils.getAttributeKey(ei, attrcode);
		} else if (o instanceof EntityItem) {
			ei = (EntityItem) o;
		}
		EANFoundation ean = findEntityItem(ei);
		if(ean!=null){
			if (!ean.getKey().equals(getRecordKey())){
				currentItem = (EntityItem) ean;
				table.updateModel(currentItem);
				table.resizeCells();
				// set key in recordtoggle
				getEditController().getRecordToggle().setCurrentKey(ean.getKey());
			}
			//table.moveToError((EntityItem) ean,attrcode);
			if(columnKey!=null){
				table.setSelection(columnKey);
			}
		}

		//  toggleRecord(table.getKey());
	}

	private EANFoundation findEntityItem(EntityItem ei) {
		EANFoundation ean = getEditController().getEgRstTable().getRow(ei.getKey());
		if (ean == null) {
			EntityItem eip = (EntityItem) ei.getUpLink(0);

			if (eip==null){  // try downlink if trying to create a SWFEATURE at MODEL uplink will be null
				eip = (EntityItem) ei.getDownLink(0);
			}
			if(eip!=null) {
				ean = getEditController().getEgRstTable().getRow(eip.getKey());
			}
		}

		return ean;
	}

    /**
	 * cancelCurrentEdit
	 */
	protected void cancelCurrentEdit() {
		table.cancelCurrentEdit();
	}

    /**
     * refresh the display
     */
    protected void refreshDisplay() {
        table.resizeCells();
    }

	/**
	 * getSelectedKeys
	 *
	 * @return
	 */
	protected String[] getSelectedKeys() {
		if (currentItem != null) {
			String[] out = new String[1];
			out[0] = currentItem.getKey();
			return out;
		}
		return null;
	}

	/**
	 * nlsRefresh
	 *
	 */
	protected void nlsRefresh() {
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
		return this.currentItem;
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

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#hasHiddenAttributes()
	 */
	protected boolean hasHiddenAttributes(){
		if (currentItem != null) {
			int iDispRows = table.getRowCount();
			int iTotalRows = currentItem.getActualRowListCount();
			return iDispRows < iTotalRows;
		}

		return false;
	}

	protected boolean isEditing(){
		return table.isEditing();
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
		if(currentItem==null){ // could happen if new row is deleted in the vertical editor
			return false;
		}
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
	 * only text and longtext will have a spellcheck handler
	 * @return
	 */
	protected SpellCheckHandler getSpellCheckHandler(){
		return table.getSpellCheckHandler();
	}
}