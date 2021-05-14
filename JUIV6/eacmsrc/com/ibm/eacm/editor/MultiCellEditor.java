//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.eacm.editor;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.objects.PasteData;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;
/**
 * this class is used to edit MultiFlag Attributes
 * @author Wendy Stimpson
 */
public class MultiCellEditor extends AttrCellEditor 
{
	private static final long serialVersionUID = 1L;
	private MultiEditor multiEditor = null;
	
	//===============added for using in a form
	/* (non-Javadoc)
	 * @see com.ibm.eacm.editor.AttrCellEditor#getFormCellPanel()
	 */
	public FormCellPanel getFormCellPanel(){// added for formeditor
		return multiEditor.getFormCellPanel();
	}

    /**
     * determine background color based on meta requirements
     * @param _s
     * @return
     */
    protected Color getBackground(String _s) {
    	//if this is in a form, the ((MultiPopupPanel) multiEditor.getFormEditor()) is used to render too
    	if(multiEditor.getFormCellPanel() != null && !getComponent().isEnabled()){
    		return getFormCellPanel().getBackground();
    	}
    	return super.getBackground(_s);
    }

	public void setForeground(Color fg) {
		multiEditor.getFormEditor().setForeground(fg);
	}
	public void setFont(Font font) {
		multiEditor.getFormEditor().setFont(font);
	}
    public void setOpaque(boolean isOpaque) {
    	multiEditor.getFormEditor().setOpaque(isOpaque);
    }
	//===============added for using in a form
	
	/**
	 * constructor for form edit
	 */
	public MultiCellEditor(FormCellPanel f){
		super(false);
		multiEditor = new MultiEditor(this, f);
	}

	/**
	 * @param parent
	 * @param insearch
	 */
	public MultiCellEditor(Window parent, boolean insearch){
		super(insearch);
		multiEditor = new MultiEditor(parent,this); 
	}
	
	/**
	 * use editor rules when pasting
	 * @param pasteObj 
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		if(pasteObj instanceof PasteData){
			PasteData pd = (PasteData)pasteObj;
			if(!pd.getAttrCode().equals(attr.getAttributeCode())){
			    UIManager.getLookAndFeel().provideErrorFeedback(null);
				//msg80000 = Invalid value "{0}" specified for "{1}"
			    Object value = pd.getValue();
			    String strvalue = value.toString();
			    if(value instanceof MetaFlag[]){
			    	MetaFlag[] mf = (MetaFlag[])value;
			    	for(int i=0; i<mf.length; i++){
			    		if(mf[i].isSelected()){
			    			strvalue = mf[i].toString();
			    			break; 
			    		}
			    	}
			    }
				com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",strvalue,
						attr.getLongDescription()));
				return false;
			}
			multiEditor.loadMetaFlags((MetaFlag[])pd.getValue());
			return true;
		}
		if(pasteObj instanceof String){
			int matchcnt = 0;
		    MetaFlag[] mf =  (MetaFlag[])attr.get();
		    for(int i=0; i<mf.length; i++){
		    	mf[i].setSelected(false);
		    }
		    String[] strArray = Routines.splitString(pasteObj.toString(),NEWLINE);
		    for (int is=0; is<strArray.length; is++){
		    	String str = strArray[is].trim();
		    	if(str.startsWith("*")){
		    		str = str.substring(1).trim();
		    	}
			    for(int i=0; i<mf.length; i++){	
			    	if(mf[i].toString().equals(str)){
				    	mf[i].setSelected(true);
				    	matchcnt++;
				    	break;
			    	}
			    }
		    }
		
			if(matchcnt!=strArray.length){
			    UIManager.getLookAndFeel().provideErrorFeedback(null);
				//msg80000 = Invalid value "{0}" specified for "{1}"
				com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",pasteObj.toString(),
						attr.getLongDescription()));
				return false;
			}
			
			multiEditor.loadMetaFlags(mf);
			return true;
		}
		
	    UIManager.getLookAndFeel().provideErrorFeedback(null);
		//msg80000 = Invalid value "{0}" specified for "{1}"
		com.ibm.eacm.ui.UI.showErrorMessage(null, Utils.getResource("msg80000",pasteObj.toString(),
				attr.getLongDescription()));
		return false;
	}

    /**
     * called if the user cancels the edit, should hide the dialog here
     * @see javax.swing.AbstractCellEditor#cancelCellEditing()
     */
	public void cancelCellEditing() { 
		multiEditor.hidePopup();
		super.cancelCellEditing(); 
	}
    /**
     * called if the user clicks on a different cell, should hide the dialog here
     * returning true tells the table to use the partially edited value, that it is valid
     * @see javax.swing.AbstractCellEditor#stopCellEditing()
     */
	public boolean stopCellEditing() { 
		multiEditor.hidePopup();
		return super.stopCellEditing(); 
	}
    /* (non-Javadoc)
     * return the component for use in joptionpane or form
     * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
     */
    public Component getComponent() {
    	Component formEd = multiEditor.getFormEditor();
    	if(formEd !=null){
    		return formEd;
    	}
    	return multiEditor;
    }

	/**
     * called after the editor component is installed, initiate edit here, like show a dialog
     * not sure if this is still called, from JTable.editCellAt()
     * 					note that as of Java 2 platform v1.2, the call to
     *                  <code>shouldSelectCell</code> is no longer made
     * use it to pass the keybd event to the editor..
     * at com.ibm.eacm.editor.FlagCellEditor.shouldSelectCell(FlagCellEditor.java:71)
	at javax.swing.plaf.basic.BasicTableUI$Handler.adjustSelection(BasicTableUI.java:1101)
	at javax.swing.plaf.basic.BasicTableUI$Handler.mousePressed(BasicTableUI.java:1025)
     * @see javax.swing.AbstractCellEditor#shouldSelectCell(java.util.EventObject)
     */
    public boolean shouldSelectCell(EventObject anEvent) { 
  		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				multiEditor.showPopup();
			}
		});
    	return true; 
    }
	
    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.AttrCellEditor#preloadKeyEvent(java.awt.event.KeyEvent)
     */
    protected void preloadKeyEvent(KeyEvent anEvent){
    	int keyCode = anEvent.getKeyCode();
    	if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9 || 
    			keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z ||
    			keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) {
    		// forward keystroke to editor
    		multiEditor.preloadKeyEvent(anEvent);
    	}
    }
	
    /**
     * return the edited value
     * @see javax.swing.CellEditor#getCellEditorValue()
     */
    public Object getCellEditorValue() {
    	return multiEditor.getEditorValue(); 
    }
      
	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//set the value in the editor here
		if (value instanceof EANFlagAttribute) {
			setAttribute((EANFlagAttribute) value);
		}
		return multiEditor;
	} 
	/**
	 * set the attribute to use for this editor execution
	 * @param _ean
	 */
	public void setAttribute(EANAttribute _ean) {
		super.setAttribute(_ean);

		multiEditor.setAttribute(_ean);

		if(rstTable!=null){
			String key = rstTable.getEANKey(viewRow);

			int colid = 1;
			String parenttype = "";
			if(_ean.getParent() instanceof EntityItem){
				parenttype=((EntityItem)_ean.getParent()).getEntityType();
			}
			
			// colkey will be FEATURE:PRICEDFEATURE:C
			String attrkey = parenttype+":"+_ean.getAttributeCode()+":";
						
			for(int i=0;i<rstTable.getRSTable().getColumnCount();i++){
				if(rstTable.getRSTable().getColumnKey(i).indexOf(attrkey)!=-1){
					colid=i;
					break;
				}
			}
			
			//singletable gets newline delimited list of flags (att.tostring does same thing)
			//!singletable (or horztable) gets first flag in list
			Object o = rstTable.getRSTable().get(rstTable.getRSTable().getRowIndex(key), colid, true); 
			if (o instanceof String) {
				multiEditor.setRendererText(Routines.convertToMultilineHTML((String) o));
			}	
		}
	}

	/**
	 * release memory
	 */
	public void dereference(){
		multiEditor.dereference();
		multiEditor = null;
    	
    	super.dereference();
	}
}
