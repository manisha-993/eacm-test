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

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.RowSelectableTable;

import com.ibm.eacm.mw.DBUtils;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.PrintUtilities;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.toolbar.DefaultToolbarLayout;

import com.ibm.eacm.actions.CopyAction;
import com.ibm.eacm.actions.CutAction;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.EntityDataAction;
import com.ibm.eacm.actions.PasteAction;
import com.ibm.eacm.edit.form.FormTable;
/***************
 * this is the form editor
 * @author Wendy Stimpson
 */
//$Log: FormEditor.java,v $
//Revision 1.3  2013/12/04 22:02:36  wendy
//check for haslocks when looking at haschanges
//
//Revision 1.2  2013/11/07 18:09:40  wendy
//Add FillCopyEntity action
//
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class FormEditor extends EditorPanel implements EACMGlobals {
	private static final long serialVersionUID = 1L;
    private FormTable table = null;
	private EntityItem currentItem = null;
	private MouseListener mouseListener;

	public Findable getFindable() {
		return table;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#print()
	 */
	protected void print() throws PrinterException{
	  	PrintUtilities.print(table);
	}
    /**
     * formEditor
     * @param ei
     * @param ec
     */
    protected FormEditor(EntityItem ei, EditController ec) {
        super(ec, DefaultToolbarLayout.EDIT_FORM_BAR, ec.getRecordToggle());

        currentItem = ei;
    	table = new FormTable(getEditController(),currentItem);

	    // register our copy and paste actions for this table
		EACMAction act = getEditController().getAction(COPY_ACTION);
		if (act!=null){
			table.registerEACMAction(act, KeyEvent.VK_C, Event.CTRL_MASK);
		}
		act = getEditController().getAction(PASTE_ACTION);
		if (act!=null){
			table.registerEACMAction(act, KeyEvent.VK_V, Event.CTRL_MASK);
		}

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

        setTitle(this.getEditController().getEgRstTable().getTableTitle());

        addDataChangePropertyListener(table);

        table.addPropertyChangeListener(DATALOCKED_PROPERTY,(UnlockAction)ec.getAction(UNLOCK_ACTION));
    	table.addPropertyChangeListener(DATALOCKED_PROPERTY,(LockAction)getEditController().getAction(LOCK_ACTION));
    }

	/**
	 * createPopupMenu
	 */
	private void createPopupMenu() {
		popup = new JPopupMenu();

		popup.add(getEditController().getAction(FINDREP_ACTION));
		popup.add(getEditController().getAction(FINDNEXT_ACTION));
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

    /* (non-Javadoc)
     * set the table in the actions that need it
     * @see com.ibm.eacm.edit.EditorPanel#updateTableActions()
     */
    protected void updateTableActions(){
    	// reset any actions that need an RSTTable2
		this.getEditController().updateTableActions(null);
    	// load formtable into EntityDataAction
    	EntityDataAction action = (EntityDataAction)getEditController().getAction(ENTITYDATA_ACTION);
    	action.setFormTable(table);

     	CutAction cutaction = (CutAction)getEditController().getAction(CUT_ACTION);
     	cutaction.setFormTable(table);

     	CopyAction copyaction = (CopyAction)getEditController().getAction(COPY_ACTION);
     	copyaction.setFormTable(table);

    	PasteAction pasteaction = (PasteAction)getEditController().getAction(PASTE_ACTION);
    	pasteaction.setFormTable(table);

    	//disable these actions, not valid in form
		EACMAction act =  this.getEditController().getAction(FILLCOPY_ACTION);
		if (act!=null){
			((FillCopyAction)act).setTable(null);
		}
		act =  this.getEditController().getAction(FILLCOPYENTITY_ACTION);
		if (act!=null){
			((FillCopyEntityAction)act).setTable(null);
		}

		act =  this.getEditController().getAction(FILLAPPEND_ACTION);
		if (act!=null){
			((FillAppendAction)act).setTable(null);
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
		return currentItem.isNew();
	}
    /**
     * refresh
     */
    protected void refreshEditor() {
		EANFoundation ean = getEditController().getEgRstTable().getRow(getRecordKey());
		setEntityItem((EntityItem)ean);
    }

    /**
     * lock - done on bg thread
     */
    protected void lock() {
        table.lock();
    }
	/* (non-Javadoc)
	 * done on event dispatch thread
	 * @see com.ibm.eacm.edit.EditorPanel#checkLockStatus()
	 */
	protected void checkLockStatus() {
		table.checkLockStatus();
		repaint();
	}
    /**
     * used by deactivateattraction to enable it
     * @return
     */
    protected boolean hasEditableAttrSelected() {
    	return table.hasEditableAttrSelected();
    }
    /**
     * deactivate the selected attributes
     */
    protected void deactivateAttribute(){
    	table.deactivateAttribute();
    }
    /**
     * unlock - called on bg thread
     *
     */
    protected void unlock(Vector<Integer> locksVct) {
        table.unlockRows();
        table.refreshForm();
    }
    /**
     * called on event dispatch thread when done updating editors
     */
    protected void resizeAndRepaint() {
    	//what is this here? table.resizeCells();
    	repaint();
    }

	/* (non-Javadoc)
	 * selection is not required for this, only an existing lock
	 * @see com.ibm.eacm.edit.EditorPanel#hasLocks()
	 */
	protected boolean hasLocks(){
		if(currentItem!=null && !currentItem.isNew()){
			return table.isLocked();
		}
		return false;
	}

    /**
     * getHelpText
     *
     * @return
     */
    protected String getHelpText() {
        EANMetaAttribute meta = table.getCurrentEANMetaAttribute();
        if (meta != null) {
            return meta.getHelpValueText();
        }
        return Utils.getResource("nia");
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
     *
     */
    public void dereference() {
		table.unregisterEACMAction(getEditController().getAction(PASTE_ACTION),KeyEvent.VK_V, Event.CTRL_MASK);
		table.unregisterEACMAction(getEditController().getAction(COPY_ACTION),KeyEvent.VK_C, Event.CTRL_MASK);
		table.unregisterEACMAction(getEditController().getAction(CUT_ACTION),KeyEvent.VK_X, Event.CTRL_MASK);

    	table.removeMouseListener(mouseListener);
    	mouseListener = null;
    	removeDataChangePropertyListener(table);

    	table.removePropertyChangeListener(DATALOCKED_PROPERTY,(UnlockAction)getEditController().getAction(UNLOCK_ACTION));
    	table.removePropertyChangeListener(DATALOCKED_PROPERTY,(LockAction)getEditController().getAction(LOCK_ACTION));

    	table.dereference();
    	table = null;
        currentItem = null;

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
     * reload the current item
     */
    protected void formRefresh() {
        table.partialRefresh();
    }

    /* (non-Javadoc)
     * this just checks for an error, it does not put up a dialog because of focus and threading issues
     * it will also return false if spellcheck is required and not done yet
     * @see com.ibm.eacm.edit.EditorPanel#canStopEditing()
     */
    protected boolean canStopEditing() {
        return table.canStopEditing();
    }

    /* (non-Javadoc)
     * this will put up the error dialog and invoke spell check if needed
     * @see com.ibm.eacm.edit.EditorPanel#stopCellEditing()
     */
    protected boolean stopCellEditing() {
        return table.stopCellEditing();
    }

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#cancelCurrentEdit()
	 */
	protected void cancelCurrentEdit(){
		table.cancelCurrentEdit();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#terminateEditor()
	 */
	protected void terminateEditor(){
		table.terminateEditor();
	}

    /**
     * getRecordKey
     *
     * @return
     */
    protected String getRecordKey() {
        return currentItem.getKey();
    }
    /**
     * get Selected attribute Key
     *
     * @return
     */
    public String getSelectionKey() {
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
     * setSelection
     * @param colKey
     * @param recKey
     */
    protected void setSelection(String recKey, String colKey) {
		if (recKey != null) {
			if(currentItem !=null  && !currentItem.getKey().equals(recKey)){
				currentItem = (EntityItem)getEditController().getEgRstTable().getRow(recKey);
				updateRSTModel(currentItem);
			}else{
				// allow for changes made in different editortype that may cause display changes
				refreshDisplay();
			}
		}
		table.setSelection(colKey);
    }

	/**
	 * determine when the lock action can be executed
	 * @return
	 */
	protected boolean canLock() {
		if(getEditController().isEditable()){
			if(currentItem!=null && !currentItem.isNew()){
				return !table.isLocked();
			}
		}
		return false;
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.edit.EditorPanel#rollback()
     */
    protected void rollback() {
        RowSelectableTable rst = getEditController().getEgRstTable();
        rst.rollback();
        table.refreshAll();

     	// only different values will actually fire an event - make sure actions listening
		// reflect the correct state
		table.firePropertyChange(DATACHANGE_PROPERTY, true, false);
    }

    /* (non-Javadoc)
     * row is the entity, so rollback everything
     * @see com.ibm.eacm.edit.EditorPanel#rollbackRow()
     */
    protected void rollbackRow() {
    	rollback();
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.edit.EditorPanel#rollbackSingle(java.lang.String)
     */
    protected void rollbackSingle(String attrKey) {
    	table.rollbackSingle(attrKey);

    	// only different values will actually fire an event - make sure actions listening
    	// reflect the correct state
    	table.firePropertyChange(DATACHANGE_PROPERTY, true, false);
    }

    /**
     * commit
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
        return (hasLocks() && table.hasChanges()) || currentItem.isNew();
    }
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#getCurrentEntityItem()
	 */
	protected EntityItem getCurrentEntityItem() {return currentItem;}

    /* (non-Javadoc)
     * @see com.ibm.eacm.edit.EditorPanel#hasNewSelected()
     */
    protected boolean hasNewSelected() {
    	return currentItem.isNew();
    }

    /* (non-Javadoc)
     * must disable/enable table/editors
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);
    	table.setEnabled(enabled);
    }
    /**
     * getEntityItems
     *
     * @return
     */
    protected EntityItem[] getSelectedEntityItems() {
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
			// this is needed when multiple entities are under edit (user can toggle between them)
			if (!ean.getKey().equals(getRecordKey())){
				currentItem = (EntityItem) ean;
				table.updateModel(currentItem);
				// set key in recordtoggle
				getEditController().getRecordToggle().setCurrentKey(ean.getKey());
			}
			if(columnKey!=null){
				table.setSelection(columnKey);
			}
		}
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
     * refresh the display - look at all attributes and lock state
     */
    protected void refreshDisplay() {
        table.refreshForm();//must look at locks too
        table.setEnabled(true); // make sure it is reflecting the correct state
    }

	/* (non-Javadoc)
	 * called when the editor tab is selected
	 * @see com.ibm.eacm.edit.EditorPanel#select()
	 */
	protected void select(){
		// this can happen if user unlocks in the lockactiontab
    	if(!hasLocks()){
    		table.cancelCurrentEdit();
    	}
		table.select();
	}

    /**
     * getSelectedKeys
     *
     * @return
     */
    protected String[] getSelectedKeys() {
        String[] out = new String[1];
        out[0] = currentItem.getKey();
        return out;
    }

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#getSelectedEANMetaAttribute()
	 */
	protected EANMetaAttribute getSelectedEANMetaAttribute(){
    	return table.getCurrentEANMetaAttribute();
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
	 * called by spellcheck action, to disable required
	 * @return
	 */
	protected SpellCheckHandler getSpellCheckHandler(){
		return table.getSpellCheckHandler();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.EditorPanel#getSelectedEAN()
	 */
	protected EANFoundation getSelectedEAN() {
		return this.currentItem;
	}
	/**
	 * used by attribute history action
	 * @return
	 */
	protected EANAttribute getSelectedAttribute(){
		return table.getSelectedAttribute();
	}
 }

