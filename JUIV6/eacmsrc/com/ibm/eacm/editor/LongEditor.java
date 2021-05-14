//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.preference.ColorPref;
import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.EANAttribute;

/**
 * this class is used to display a long text editor in a dialog or form
 * @author Wendy Stimpson
 */
//$Log: LongEditor.java,v $
//Revision 1.2  2013/08/14 16:49:06  wendy
//check max length when text is entered
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class LongEditor extends JPanel  {
	private static final long serialVersionUID = 1L;
	private LongCellEditor longCellEd;
	private KeyEvent preloadkey = null;
	private LongTextArea textArea = null;
	private LongPopupDialog popup = null;

	private LabelRenderer editRenderLbl = null; // use this for better performance

	//==========added for formeditor
	public FormCellPanel getFormCellPanel() { return textArea.getFormCellPanel();}// added for formeditor
	public JComponent getEditorComponent() { return textArea;}
	
	/**
	 * for use in formedit - no popup
	 * @param lce
	 * @param f
	 */
	protected LongEditor(LongCellEditor lce,FormCellPanel f){
		super(new BorderLayout());

		longCellEd = lce;
		textArea = new LongTextArea(this);
		textArea.setFormCellPanel(f);

		setFocusable(false);
		setRequestFocusEnabled(false); 
	}
	//==========end added for formeditor
	/**
	 * for use in vertical and grid edit
	 * @param lce
	 */
	protected LongEditor(LongCellEditor lce){
		super(new BorderLayout());

		longCellEd = lce;
		editRenderLbl = new LabelRenderer();
		
		add(editRenderLbl,BorderLayout.WEST);

		textArea = new LongTextArea(this);
		popup = new LongPopupDialog(this,textArea);		

		setFocusable(false);
		setRequestFocusEnabled(false); 

		//initAccessibility("accessible.longEditor");
	}
	/**
	 * update editor with this attribute
	 * @param ean
	 */
	protected void setAttribute(EANAttribute ean) {
		String text = "";
		if(ean!=null){
			text = ean.toString();
		}

		if(popup !=null){
			editRenderLbl.setText(Routines.convertToMultilineHTML(Routines.addLineWraps(text, 80))); // this will be in the cell

			if(ean!=null){
				popup.setTitle(ean.getLongDescription());
			}else{
				popup.setTitle("LongText Editor");
			}
			popup.setEditorValue(text); // this is the editor itself
		}else{
			int caret = textArea.getCaretPosition();
			textArea.setDisplay(text);
			if(caret>text.length()){ // dont go past end
				caret = 0;
			}
			textArea.setCaretPosition(caret);
		}
	}
	/**
	 * set view only
	 * @param b
	 */
	protected void setEditable(boolean b){
		popup.setEditable(b);
	}
	/**
	 * hide the dialog
	 */
	protected void hidePopup(){
		popup.hidePopup();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground() {
		if(longCellEd!=null){
			return longCellEd.getBackground("");
		}
		return ColorPref.getLockColor();
	}
	/**
	 * show the dialog
	 */
	protected void showPopup() {
		if(preloadkey!=null){
			popup.preloadKeyEvent(preloadkey);
			preloadkey = null;
		}
		
		popup.showPopup();
	}
	/**
	 * preloadKeyEvent
	 * @param ke
	 */
	protected void preloadKeyEvent(KeyEvent ke) {
		preloadkey = ke;
	}
	
	/**
	 * get current value
	 * @return
	 */
	protected Object getEditorValue() {
		return getText();
	}
	
	/**
	 * get text value
	 * @return
	 */
	protected String getText() {
		String txt = textArea.getText();
		if(txt==null){
			txt="";
		}
		return txt;
	}
	
	/**
	 * get selected text
	 * @return
	 */
	protected String getSelectedText() {
		return textArea.getSelectedText();
	}
	
	/**
	 * do any character validation as user keys it in
	 * @param addedStr
	 * @return
	 */
	protected boolean charValidation(String addedStr){
		return longCellEd.charValidation(getText(), addedStr);
	}
	
    /**
     * called if the user cancels the edit, should hide the dialog here
     * @see javax.swing.AbstractCellEditor#cancelCellEditing()
     */
	protected void cancelCellEditing() { 
		hidePopup();
		// restore original text
		textArea.setText(longCellEd.getAttribute().get().toString());
		longCellEd.cancelCellEditing(); 
	}
    /**
     * called if the user clicks on a different cell, should hide the dialog here
     * returning true tells the table to use the partially edited value, that it is valid
     * @see javax.swing.AbstractCellEditor#stopCellEditing()
     */
	protected boolean stopCellEditing() { 
		boolean ok = longCellEd.stopCellEditing();
		if(ok){
			hidePopup();
		}
		return ok; 
	}
	
	/**
	 * release memory
	 */
	protected void dereference(){
		//initAccessibility(null);
		preloadkey = null;
		longCellEd = null;
		
		if(editRenderLbl!=null){
			editRenderLbl.dereference();
			editRenderLbl = null;
		}

		if(popup != null){
			popup.dereference();
			popup = null;
		}

		textArea.dereference();
		textArea=null;
		
		removeAll();
		setUI(null);
	}
}
