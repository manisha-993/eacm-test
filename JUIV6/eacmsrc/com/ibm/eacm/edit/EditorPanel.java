//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.print.PrinterException;

import java.beans.PropertyChangeListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;


import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.EANFoundation;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.RowSelectableTable;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Findable;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.table.BaseTable;
import com.ibm.eacm.toolbar.ComboItem;
import com.ibm.eacm.toolbar.EACMToolbar;
import com.ibm.eacm.toolbar.ToolbarLayout;

/**
 * base class used for form, grid and vertical editors
 * @author Wendy Stimpson
 */
//$Log: EditorPanel.java,v $
//Revision 1.7  2015/01/05 19:15:34  stimpsow
//use Theme for background colors
//
//Revision 1.6  2013/10/10 21:40:01  wendy
//allow reset record for new entity
//
//Revision 1.5  2013/07/26 17:30:41  wendy
//support duplication of multiple rows
//
//Revision 1.4  2013/07/18 18:33:20  wendy
//fix compiler warnings
//
//Revision 1.3  2013/05/07 18:25:26  wendy
//another perf update for large amt of data
//
//Revision 1.2  2013/05/01 18:35:14  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:19  wendy
//Initial code
//
public abstract class EditorPanel extends JPanel implements EACMGlobals {
	private static final long serialVersionUID = 1L;

	/**
     * these actions are enabled based on data changes
     */
    private static final String[] DATA_CHG_PROP_ACTIONS = new String[]{
        SAVERECORD_ACTION, SAVEALL_ACTION,RESETALLATTR_ACTION,
        RESETONEATTR_ACTION, RESETRECORD_ACTION,DEACTIVATEATTR_ACTION,SPELLCHK_ACTION
    };
    
	private EACMToolbar toolBar = null;
    protected JScrollPane scroll = null;
    protected JPopupMenu popup = null;
    private EditController editCtrl = null;
    private TitledBorder titleBorder = new TitledBorder(LineBorder.createBlackLineBorder());
    
    /**
     * add all of these actions as propertychangelisteners to the table
     * @param table
     */
    protected void addDataChangePropertyListener(Component table){
    	for(int i=0; i<DATA_CHG_PROP_ACTIONS.length; i++){
    		table.addPropertyChangeListener(DATACHANGE_PROPERTY,
        		(PropertyChangeListener)editCtrl.getAction(DATA_CHG_PROP_ACTIONS[i]));
    	} 
    }
    /**
     * remove all of these actions as propertychangelisteners from the table
     * @param table
     */
    protected void removeDataChangePropertyListener(Component table){
    	for(int i=0; i<DATA_CHG_PROP_ACTIONS.length; i++){
    		table.removePropertyChangeListener(DATACHANGE_PROPERTY,
        		(PropertyChangeListener)editCtrl.getAction(DATA_CHG_PROP_ACTIONS[i]));
    	} 
    }
    /**
     * @param _ec
     * @param _tBarLayout
     * @param _obj
     */
    protected EditorPanel(EditController _ec, ComboItem _tBarLayout, RecordToggle _obj) {
        super(new BorderLayout());
        editCtrl = _ec;
   
        // create the toolbar for this type of editor
        toolBar = new EACMToolbar(ToolbarLayout.getToolbarLayout(_tBarLayout), _obj, editCtrl.getActionTbl());
    }
    
    /**
     * get the edit controller
     * @return
     */
    protected EditController getEditController() {return editCtrl;}
    /**
     * used by editors when values have changed and user is closing the tab or releasing the lock
     * called by okToClose
	 * @see com.ibm.eacm.edit.EditorPanel#chooseToSave()
	 */
	private boolean chooseToSave() { 
		RowSelectableTable rst = getEditController().getEgRstTable();
		EANFoundation[] ean = rst.getTableRowsAsArray();

		for (int i = 0; i < ean.length; i++) {
			EntityItem ei = (EntityItem) ean[i];
			if (ei.hasChanges()) {
				setEntityItem(ei); 

				//updtMsg2 = Table values have changed. Would you like to save the change(s)?
				int action  = com.ibm.eacm.ui.UI.showConfirmYesNoCancel(null,Utils.getResource("updtMsg2"));
				if (action==YES_BUTTON){ 
					// this will commit and move to any error
					if (!getEditController().commit()) { 
						return false; 
					} 
				}else if (action==NO_BUTTON|| action==CLOSED){                
				}else if (action==CANCEL_BUTTON){
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * called from chooseToSave to load the entity item into form or vertical editors or select the row in
	 * grid editor
	 * @param ei
	 */
	protected abstract void setEntityItem(EntityItem ei);
    /**
     * can stop edit and continue with next action
     * if spellcheck is required but not done this will return false
     * @return
     */
    protected abstract boolean canStopEditing();
    
	/**
	 * needed for tableheader fix, form editor doesnt have a table
	 * @return
	 */
	protected BaseTable getJTable() { return null;}
	
	protected abstract void print() throws PrinterException;
	/**
	 * derived classes must determine when the unlock action can be enabled
	 * @return
	 */
	protected abstract boolean hasLocks();
	
	/**
	 * derived classes must also determine when the lock action can be executed
	 * like if row selection is needed
	 * @return
	 */
	protected boolean canLock() { 
		return editCtrl.isEditable();
	}
	
    /**
     * lock - implemented by derived classes
     */
    protected abstract void lock(); // lock on background thread
    protected abstract void checkLockStatus(); // finish lock on event dispatch thread
    
    /**
     * unlock - implemented by derived classes
     */
    protected abstract void unlock(Vector<Integer> locksVct); // done on bg thread
    
    /**
     * get row ids for changed entities.  vertical and form editors will only have the one item
     * used for unlock action
     * @return vector of view row ids
     */
	protected Vector<Integer> getSelectedChangedRowIds(){
    	Vector<Integer> vct = new Vector<Integer>();
    	if(hasChanges()){
    		vct.add(new Integer(0));
    	}
    	return vct;
	}
	
    /**
     * get keys for changed entities.  vertical and form editors will only have the one item
     * used for determining what was committed - used for saverecord
     * @return vector of view row ids
     */
	protected Vector<String> getSelectedChangedKeys(){
    	Vector<String> vct = new Vector<String>();
    	if(hasChanges()){
    		vct.add(getCurrentEntityItem().getKey());
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
    	if(hasChanges()){
    		vct.add(getCurrentEntityItem().getKey());
    	}
    	return vct;
	}
	/**
	 * rollback selected row - vertical is entire entity
	 * @return
	 */
	protected boolean canResetRecord(){
		return //!isNew() && 	
				editCtrl.getEgRstTable().getRowCount()>0;
	}
	
    protected boolean isNew() { return false;}
    /**
     * get row ids for changed entities.  vertical and form editors will only have the one item
     * used for resetall action
     * @return vector of view row ids
     */
	protected Vector<Integer> getChangedRowIds(){
    	return getSelectedChangedRowIds();
	}
	
	/**
     * get row ids for locked entities.  vertical and form editors will only have the one item
     * used for unlock action
	 * @return
	 */
	protected Vector<Integer> getSelectedLockedIds(){
    	Vector<Integer> vct = new Vector<Integer>();
    	if(hasLocks()){
    		vct.add(new Integer(0));
    	}
    	return vct;
	}
    
    /**
     * 
     * called by saveworker in unlock action
     * @param chgsvct
     */
    protected void commitRows(Vector<Integer>chgsvct)throws Exception {
    	commit(); // formeditor and vertical editor would commit everything
    }
    
    /**
     * called on event dispatch thread when done updating editors
     */
    protected void resizeAndRepaint() {
    	repaint();
    }
    
    /**
     * only meaningful in grid editor
     * called by unlockaction.choosetosave
     * @param rowid
     */
    protected void selectRow(int rowid){}
    
    /**
     * rollback everything, selection not required in grideditor
     * new rows will be removed - called from resetall action
     */
    protected void rollback() {}

    /**
     * rollback a single entity, selection required in grideditor
     */
    protected void rollbackRow() {}

    /**
     * rollback an attribute
     * @param attrKey
     */
    protected void rollbackSingle(String attrKey) {}
    /**
     * 
     * called by unlock action
     * @param chgsvct
     */
    protected void rollbackRows(Vector<Integer>chgsvct){
    	rollback();  // formeditor and vertical editor would roll everything back
    }
     
    /**
     * okToClose
     *
     * @return
     */
    protected boolean okToClose() {
        // check for any change
        if (editCtrl.hasMasterChanges()) {
            int action = CLOSED;
            if (editCtrl.getEntityItemCount() > 1) {
            	//updtMsg3 = Some of the entities have changed.
            	action = com.ibm.eacm.ui.UI.showConfirmAllNoneChooseCancel(this,Utils.getResource("updtMsg3"));
                if (action == ALL_BUTTON) {
            		// this will commit and move to any error
                	boolean b = getEditController().commitAll();
                	if(b){
                		getEditController().commitCompleted();
                	}
                    return b;
                } else if (action == CHOOSE_BUTTON) {
                    return chooseToSave();
                }else if (action == NONE_BUTTON) {
                    return true;
                } 
                return false;
            } else {
            	//updtMsg2 = Table values have changed. Would you like to save the change(s)?
            	action=  com.ibm.eacm.ui.UI.showConfirmYesNoCancel(this, Utils.getResource("updtMsg2"));
            	if (action == YES_BUTTON){
            		// this will commit and move to any error
            		return getEditController().commit();
            	} else if (action ==NO_BUTTON){
            		return true;
            	}
            	return false;
            }
        }
        return true;
    }
 
    /**
     * RecordToggle is shared between Vertical and Form editors, make sure it is visible
     */
    protected void restoreRecordToggle() {
    	toolBar.restoreRecordToggle();
    }
    /**
     * called to change the RowSelectableTable in the tablemodel - used for Vertical and Form editors only
     * @param ei
     */
    protected void updateRSTModel(EntityItem ei){}
    protected EntityItem getCurrentEntityItem() {return null;}
    protected EntityItem[] getSelectedEntityItems() {return null;}
    
    /**
     * allow editor to do the duplication, multiple rows may be selected
     * @param copies
     */
    protected void duplicate(int copies){}
    
    protected boolean hasChanges() { return false;}
    /**
     * this is needed for grid, and saveall when the change is not on a selected row
     * @return
     */
    protected boolean hasAnyChanges() { 
    	return hasChanges();
    }
    
    protected boolean hasNewSelected() { return false;}
    /**
     * used by resetsingleaction to enable it
     * @return
     */
    protected boolean hasChangedAttrSelected() { return false;}
    
    /**
     * called on bg thread from saverecord action
     * @throws Exception
     */
    protected void commit() throws Exception {} 
    
	/**
	 * derived editors must update actions with their 'table'
	 */
	protected abstract void updateTableActions();
	
	/**
	 * derived classes need to resize cells or refresh renderers
	 */
	protected abstract void refreshDisplay();
    
	/**
	 * end the current edit and restore the original value
	 */
	protected void cancelCurrentEdit(){ }
	
	/**
	 * form editor needs to know when to stop all edit checks, like when deleting a new entityitem and
	 * a required field does not have a value, there is no value to restore so canceledit is not enough
	 */
	protected void terminateEditor(){}
	
	/**
	 * stop the current edit and accept the value, an error dialog may popup
	 * @return
	 */
	protected boolean stopCellEditing(){
    	return false;
    } 
    
    /**
     * setTitle
     * @param _s
     */
    protected void setTitle(String _s) {
        titleBorder.setTitle(_s);
        if (editCtrl != null) {
            editCtrl.setName(_s);
        }
        scroll.repaint();
    }

    /**
     * createScrollPane
     * @param _table
     */
    protected void createScrollPane(JComponent _table) {
    	scroll = new JScrollPane(_table);
    	Dimension d = new Dimension(250, 250);
        scroll.setBorder(titleBorder);
        scroll.setSize(d);
        scroll.setPreferredSize(d);
        scroll.setMinimumSize(UIManager.getDimension("eannounce.minimum"));
        add(scroll,BorderLayout.CENTER);
        
        add(toolBar.getAlignment(), toolBar);
    }
    
    /**
     * move to the error in this exception
     * @param _ebre
     */
    protected void moveToError(EANBusinessRuleException _ebre) {}
    
    /**
     * deactivate the selected attributes
     */
    protected abstract void deactivateAttribute();
    /**
     * used by deactivateattraction to enable it
     * @return
     */
    protected boolean hasEditableAttrSelected() { return false;}
    
    /**
     * used by FillPasteAction to enable it
     * @return
     */
    protected boolean canFillPaste() {return false;}
    
    /**
     * used by FillPasteAction
     */
    protected void fillPaste() {}
    
    /**
     * used by FillPasteAction - fire table events and repaing
     */
    protected void finishAction() {}
    
	/**
	 * get the metaattribute, needed for flag maintenance
	 * @return
	 */
	protected abstract EANMetaAttribute getSelectedEANMetaAttribute();
	
	/**
	 * can spell check be done on the selected attributes
	 * @return
	 */
	protected abstract boolean isSpellCheckable();

	/**
	 * execute spell check on this handler
	 * only text and longtext will have a spellcheck handler
	 * @return
	 */
	protected SpellCheckHandler getSpellCheckHandler(){
		return null;
	}
	
	/**
	 * used for parent selector, need selected entityitem, only 1
	 * @return
	 */
	protected abstract EANFoundation getSelectedEAN();
	
	/**
	 * used by attribute history action
	 * @return
	 */
	protected abstract EANAttribute getSelectedAttribute();
	
	protected boolean hasFiltered() {
        return false;
    }

	protected void select(){}

	protected boolean hasHiddenAttributes(){
    	return false;
    }

    protected void setParentItem(EntityItem _item) {}

    protected String[] getSelectedKeys() {return null;}

    /**
     * hide or show the specified toolbar button
     *
     * @param key
     * @param visible
     */
    protected void setToolBarButtonVisible(String key, boolean visible) {
        if (toolBar != null) {
            // this controls button visibility 
        	toolBar.setVisible(key, visible);
        }

    }


    /**
     * refresh - overridden by derived classes, no point to call editcontroller, it just calls the editors
     *
     */
    protected void refreshEditor() {}

    /**
     * getHelpText
     *
     * @return
     */
    protected String getHelpText() { return Utils.getResource("nia"); }

    /**
     * getRecordKey
     *
     * @return
     */
    protected String getRecordKey() {return "";}

    /**
     * setSelection
     *
     * @param colKey
     * @param recKey
     */
    protected void setSelection(String recKey, String colKey) {}
    protected String getSelectionKey(){
    	return null;
    }
    
    /**
     * dereference
     *
     */
    public void dereference() {        
        titleBorder = null;

        if(toolBar!=null){
        	toolBar.dereference();
        	toolBar = null;
        }

        if (scroll != null) {
            scroll.removeAll();
            scroll.setUI(null);
            scroll = null;
        }

        if (popup != null) {
        	removePopup();
            popup = null;
        }

        editCtrl = null;
    }
	private void removePopup(){
		for (int ii=0; ii<popup.getComponentCount(); ii++) {
			Component comp = popup.getComponent(ii);
			if (comp instanceof JMenuItem) {// separators are null
				EACM.closeMenuItem((JMenuItem)comp);
			}  
		}
		popup.setUI(null);
		popup.removeAll();
	}
	abstract protected Findable getFindable();
	
	/**
	 * user changed background color, update all components
	 */
	protected void updateComponentsUI(){
		SwingUtilities.updateComponentTreeUI(this);
		if(popup !=null){
			SwingUtilities.updateComponentTreeUI(popup);
		}
		if(toolBar !=null){
			SwingUtilities.updateComponentTreeUI(toolBar);
		}
		if(getJTable() != null){
			SwingUtilities.updateComponentTreeUI(getJTable());
		}
	}
}
