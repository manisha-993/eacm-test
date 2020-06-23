//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.JTable;

import com.ibm.eacm.edit.EditController;
import com.ibm.eacm.edit.form.FormCellPanel;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.XMLAttribute;

/**
 * this class is used to display a xml text editor in a dialog
 * @author Wendy Stimpson
 */
//$Log: XMLCellEditor.java,v $
//Revision 1.2  2013/08/14 16:58:05  wendy
//paste has cell listener to support type after paste
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class XMLCellEditor extends AttrCellEditor 
{
	private static final long serialVersionUID = 1L;

	private XMLEditorPanel xmlEditor = null;
	private EditController editController = null;

	//	 added for formeditor
	/* (non-Javadoc)
	 * @see com.ibm.eacm.editor.AttrCellEditor#getFormCellPanel()
	 */
	public FormCellPanel getFormCellPanel(){
		return xmlEditor.getFormCellPanel();
	}

	/**
	 * constructor for form editor
	 * @param ec
	 * @param f
	 */
	public XMLCellEditor(EditController ec, FormCellPanel f){
		super(false);
		xmlEditor = new XMLEditorPanel(this);
		xmlEditor.setFormCellPanel(f);
		editController = ec;
	}
	//	end added for formeditor
	
	/**
	 * constructor for grid and vertical edit
	 * @param ec
	 */
	public XMLCellEditor(EditController ec){
		super(false);
		xmlEditor = new XMLEditorPanel(this);
		editController = ec;
	}

	/**
	 * use editor rules when pasting
	 * @param pasteObj 
	 * @return
	 */
	public boolean paste(Object pasteObj,boolean editOpen){
		return false;
	}

	/**
	 * does this cell have the lock - need to override lock check for editors that can be view only
	 * @return
	 */
	protected boolean isCellLocked(){
		return true;
	}
	/**
	 * called if the user cancels the edit, should hide the dialog here
	 * @see javax.swing.AbstractCellEditor#cancelCellEditing()
	 */
	public void cancelCellEditing() { 
		if(editController!=null){ // will be null if used in pdg
			editController.enableActionsAndRestore();
		}
		super.cancelCellEditing(); 
	}
  /**
	 * called if the user clicks on a different cell, should hide the dialog here
	 * returning true tells the table to use the partially edited value, that it is valid
	 * @see javax.swing.AbstractCellEditor#stopCellEditing()
	 */
	public boolean stopCellEditing() { 
		if(xmlEditor.isPopupShowing()){
			return false;
		}
		if(editController!=null){ // will be null if used in pdg
			editController.enableActionsAndRestore();
		}
		return super.stopCellEditing(); 
	}
	/**
     * called if the user clicks on a different cell, must check before putting up dialog in form
     */	
	protected boolean canStopCellEditing(){
		if(xmlEditor.isPopupShowing()){
			return false;
		}
		return true;
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
				if(editController!=null){ // will be null if used in pdg
					editController.disableActionsAndWait();
				}
				// reload the editor
				xmlEditor.setEditable(isCellEditable());
				xmlEditor.setAttribute(getAttribute());
				// show it
				xmlEditor.showPopup();
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
			//	xmlEditor.preloadKeyEvent(anEvent);
		}
	}
	/**
	 * determine background color based on meta requirements
	 * @param _s
	 * @return
	 */
	protected Color getBackground(String _s) {
		if(!this.isCellEditable()){
			return Color.WHITE;
		}
		return super.getBackground(_s);
	}
	/**
	 * return the edited value
	 * @see javax.swing.CellEditor#getCellEditorValue()
	 */
	public Object getCellEditorValue() {
		return xmlEditor.getEditorValue(); 
	}
	/* (non-Javadoc)
	 * return the component for use in joptionpane
	 * @see com.ibm.eacm.editor.AttrCellEditor#getComponent()
	 */
	public Component getComponent() {
		return xmlEditor;
	}

	/**
	 * return a component to edit the cell, the editor completely replaces the renderer when editing is in progress
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
	 */
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		//set the value in the editor here
		if (value instanceof XMLAttribute) {
			setAttribute((XMLAttribute) value);
		}
		return xmlEditor;
	} 
	/**
	 * set the attribute to use for this editor execution
	 * @param _ean
	 */
	public void setAttribute(EANAttribute _ean) {
		super.setAttribute(_ean);
		xmlEditor.setEditable(isCellEditable());
		xmlEditor.setAttribute(_ean);
	}
	/**
	 * release memory
	 */
	public void dereference(){
		editController = null;
		xmlEditor.dereference();
		xmlEditor = null;

		super.dereference();
	}
}
