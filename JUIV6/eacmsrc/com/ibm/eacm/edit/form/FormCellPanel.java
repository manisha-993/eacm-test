//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit.form;

import java.awt.Component;
import java.awt.GridBagLayout;
import java.util.EventObject;

import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.SpellCheckHandler;
import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.BlobCellEditor;
import com.ibm.eacm.editor.PasteEditor;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Routines;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.LongTextAttribute;
import COM.ibm.eannounce.objects.MetaFlag;
import COM.ibm.eannounce.objects.TextAttribute;


/***************
 * 
 * Editor panel used in form, it wraps the celleditors
 * it has a renderer when not editable
 * assumption, if table has lock, then only need to check if attr is editable
 * table getting lock needs to check profile.date and rst editability
 * 
 * idea is render when not 'editable'
 * use editor when can edit - swap them at this panel level
 * @author Wendy Stimpson
 */
//$Log: FormCellPanel.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public abstract class FormCellPanel extends JPanel implements EACMGlobals{
	private static final long serialVersionUID = 1L;

	private String rstKey = null;
	private boolean displayOnly = false;
	private FormTable formTable = null;
	private boolean uneditableOverride = false; // need a way to force uneditable if form specifies it
	private boolean found = false;
	
	/**
	 * constructor
	 * @param key
	 * @param att
	 * @param ft
	 */
	protected FormCellPanel(String key, EANAttribute att, FormTable ft){
		super(new GridBagLayout());
		rstKey = key;
		formTable = ft;
	}

    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keystroke
     */
    protected void registerEACMAction(EACMAction act, KeyStroke keystroke) {}
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    protected void unregisterEACMAction(EACMAction act,KeyStroke keystroke){}
    
    /**
     * perform the cut action, text editors must override this
     */
    protected void cut(){}
    
	/**
	 * force this editor to always be uneditable
	 */
	public void setUneditableOverride(){
		uneditableOverride = true;
	}

	/**
	 * put a border around the 'current' editor, a mouse click on the renderer will highlight it
	 * focus may not be transferred if the panel isdisplayonly, this allows user to get information
	 * on the attribute without going into edit mode
	 * @param isCurrent
	 */
	protected void highlightEditor(boolean isCurrent){
//		if(normalBorder == null){
			//init border, must be done after derived classes have been instantiated, they control the border
//			normalBorder = getBorder();
//		}
		if(isCurrent){
			if (isFound()) {
				setBorder(com.ibm.eacm.ui.FoundLineBorder.FOUND_FOCUS_BORDER);
			}else{ 
				setBorder(UIManager.getBorder(ETCHED_BORDER_KEY));
			}
		}else{
			setBorder(getCurrentBorder());
		}
	}

	/**
	 * @return
	 */
	public FormTable getFormTable(){ return formTable;}
	
    /* (non-Javadoc)
     * must disable/enable table/editors
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);

    	if(getEditorComponent() != null){
    		getEditorComponent().setEnabled(enabled);
    	}
    }
    
	/**
	 * release memory
	 */
	public void dereference(){
		rstKey = null;
		formTable = null;
		removeAll();
		setUI(null);
	}

	/**
	 * get the key used to find this attribute in the rowselectabletable
	 * @return
	 */
	public String getRSTKey() { return rstKey; }
	
	/**
	 * return the edited value
	 */
	protected abstract Object getCellEditorValue();

	/**
	 * allow dbl mouse click or ctrl-enter to launch xml editor
	 * @param anEvent
	 * @return
	 */
	public boolean shouldSelectCell(EventObject anEvent){
		return true;
	}

	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @return
	 */
	protected abstract Component getEditorComponent();
	
	/**
	 * this is needed for spell check and paste
	 * @return
	 */
	public abstract AttrCellEditor getCellEditor();
	/**
	 * called if the user clicks on a different cell, should hide the dialog here
	 * returning true tells the table to use the partially edited value, that it is valid
	 * @see javax.swing.AbstractCellEditor#stopCellEditing()
	 */	
	protected abstract boolean stopCellEditing();
	protected abstract void cancelCellEditing();
	protected boolean canStopCellEditing(){
		return true;
	}
	/**
	 * only text and longtext will have a spellcheck handler
	 * @return
	 */
	protected SpellCheckHandler getSpellCheckHandler(){
		return null;
	}
	/**
	 * is this editor currently selected and is editable
	 * @return
	 */
	protected boolean editorIsActive(){
		return (!isDisplayOnly()) && getEditorComponent().hasFocus();
	}
	
	/**
	 * get the attribute used in this editor
	 * @return
	 */
	protected abstract EANAttribute getAttribute();
	
	/**
	 * get the item selected - if all is selected return the attribute
	 * otherwise it is just the String or nothing
	 * @return
	 */
	protected Object getSelection() { return null; }
	
	/**
	 * get information for the selected attribute
	 * @return
	 */
	protected String getInformation() {
		return Routines.getInformation(getAttribute());
	}

	protected abstract void refreshDisplay();
	
    /* (non-Javadoc)
     * @see java.awt.Component#toString()
     */
    public String toString() {
        return getRSTKey();
    }
	/**
	 * were any changes made to this attribute already, they were 'put' into the entityitem
	 * @return
	 */
	protected boolean hasPostedChanges(){
		return getAttribute().hasChanges();
	}
	
	/**
	 * were any changes just made in this editor
	 * @return
	 */
	protected boolean hasNewChanges(){
		Object obj = getCellEditorValue();
		Object orig = getAttribute().get();
		String curvalue="";
		String origvalue="";
		if(obj !=null){
			if(obj instanceof MetaFlag[]){
				MetaFlag[] mf = (MetaFlag[])obj;
				StringBuilder sb = new StringBuilder();
				for (int i=0; i<mf.length; i++){
					if(mf[i].isSelected()){
						sb.append(mf[i].getFlagCode());
					}
				}
				curvalue = sb.toString();
				if(orig !=null){
					//get original value
					mf = (MetaFlag[])orig;
					sb = new StringBuilder();
					for (int i=0; i<mf.length; i++){
						if(mf[i].isSelected()){
							sb.append(mf[i].getFlagCode());
						}
					}
					origvalue = sb.toString();
				}
			}else{
				curvalue = obj.toString();
				if(curvalue.equals(BlobCellEditor.NOBLOB_UPDATE)){
					curvalue = origvalue;
				}
				origvalue=orig.toString();
			}
		}

		return !origvalue.equals(curvalue);
	}
	
	/**
	 * load the editor or the renderer
	 */
	public void updateMode(){
		boolean canEdit = !uneditableOverride;
		if(canEdit){
			canEdit = formTable.isLocked(getRSTKey()) && getAttribute().isEditable();
		}
	
		displayOnly = !canEdit;
		removeAll();	// remove the current renderer or editor
		refreshDisplay(); // put current attribute value into the control
		if(displayOnly){ // load renderer
			loadRenderer();
		}else{ //load editor
			loadEditor();
		}
		setBorder(getCurrentBorder());
	}
	
	protected abstract void loadEditor();
	protected abstract void loadRenderer();
	
	protected Border getCurrentBorder() {
		if (isFound()) {
			return com.ibm.eacm.ui.FoundLineBorder.FOUND_BORDER;
		} 
		return UIManager.getBorder("TextField.border");
	}	
	
	/**
	 * isDisplayOnly
	 *
	 * @return
	 */
	public boolean isDisplayOnly() {
		return displayOnly;
	}
	
	public boolean isFound() {
		return found;
	}

	/**
	 * resetFound
	 *
	 */
	public void resetFound() {
		if (found) {
			found = false;
			setBorder(getCurrentBorder());
		}
	}
	

	/**
	 * @param oldValue
	 * @param newValue
	 */
	public void replace(String oldValue, String newValue) {	
		TableCellEditor editor = getCellEditor();
		if (editor != null && editor.isCellEditable(null)) {
			EANAttribute att = getAttribute();
			String newStr = newValue;
			if (att instanceof LongTextAttribute) {
				newStr = Routines.replace((String) ((LongTextAttribute) att).get(), oldValue, newValue);
			} else if (att instanceof TextAttribute) {
				newStr = Routines.replace((String) ((TextAttribute) att).get(), oldValue, newValue);
			}
			
			// use editor for validation
			if (editor instanceof PasteEditor){
				if(((PasteEditor)editor).paste(newStr,false)){
					if(editor.stopCellEditing()){
						found = true; // highlight this cell
						setBorder(getCurrentBorder());
					}else{
						editor.cancelCellEditing();
					}
				}
			}
		}	
	}

	public boolean find(String value, boolean useCase) {
		Object obj = getCellEditorValue();
		if(obj !=null){
			String txt = obj.toString();
			if(obj instanceof MetaFlag[]){
				MetaFlag[] mf = (MetaFlag[])obj;
				StringBuilder sb = new StringBuilder();
				for(int i=0;i<mf.length;i++){
					sb.append(mf[i].toString());
				}
				txt = sb.toString();
			}
			boolean b = Routines.find(txt, value, useCase); 
			if(b){
				found = b;
				setBorder(getCurrentBorder());
			}
		}
		return found;
	}
}
