//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;

import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.BlobCellEditor;

import COM.ibm.eannounce.objects.EANAttribute;
/***************
 * 
 * Form Blob editor and renderer
 * @author Wendy Stimpson
 */ 
//$Log: BlobCellPanel.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class BlobCellPanel extends FormCellPanel {
	private static final long serialVersionUID = 1L;
	private BlobCellEditor tce;
	
	/**
	 * constructor
	 * @param key
	 * @param att
	 * @param ft
	 */
	public BlobCellPanel(String key, EANAttribute att, FormTable ft) {
		super(key, att, ft);

		tce = new BlobCellEditor(this,getFormTable().getEditController());
		tce.setAttribute(att);

		addListeners();
	}
	
	/**
	 * this is needed for spell check and paste
	 * @return
	 */
	public AttrCellEditor getCellEditor(){
		return tce;
	}
	
	/**
	 * this must be done after the base class is init, cell components are not created until after
	 * base class inits
	 */
	private void addListeners(){
		Component jta = getEditorComponent();

		jta.addMouseListener(getFormTable());
		jta.addFocusListener(getFormTable());
		
		tce.addCellEditorListener(getFormTable());
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadEditor()
	 */
	protected void loadEditor(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;
		
		add(getEditorComponent(),gbca);
		// enable the attach action if possible
		tce.updateAttachAction();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadRenderer()
	 */
	protected void loadRenderer(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;

		add(getEditorComponent(),gbca);
		// enable the attach action if possible
		tce.updateAttachAction();
	}

	/**
	 * get the item selected
	 * @return
	 */
	protected Object getSelection() { 
		return getAttribute();
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#getEditorComponent()
	 */
	protected Component getEditorComponent(){
		return tce.getComponent();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#requestFocusInWindow()
	 */
	public boolean requestFocusInWindow() {
		if(isDisplayOnly()){
			return super.requestFocusInWindow();
		}else{
			// put focus in the edit control
			Component jta = tce.getComponent();
			return jta.requestFocusInWindow();
		}
	}


	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setForeground(java.awt.Color)
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (tce !=null){
			tce.setForeground(fg);
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setFont(java.awt.Font)
	 */
	public void setFont(Font font) {
		super.setFont(font);
		if (tce !=null){
			tce.setFont(font);
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setOpaque(boolean)
	 */
	public void setOpaque(boolean isOpaque) {
		super.setOpaque(isOpaque);
		if (tce !=null){
			tce.setOpaque(isOpaque);
		}
	}
	/* (non-Javadoc)
	 * return color based on meta rules
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground() {
		if(tce==null || isDisplayOnly()){
			return super.getBackground();
		}
		return tce.getBackground();
	}
	/**
	 * return the edited value
	 */
	protected Object getCellEditorValue(){
		return tce.getCellEditorValue();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#getAttribute()
	 */
	protected EANAttribute getAttribute(){
		return tce.getAttribute();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#refreshDisplay()
	 */
	protected void refreshDisplay() {
		tce.setAttribute(this.getAttribute()); //force a reload 
	}

	/**
	 * called if the user clicks on a different cell, should hide the dialog here
	 * returning true tells the table to use the partially edited value, that it is valid
	 * @see javax.swing.AbstractCellEditor#stopCellEditing()
	 */	
	protected boolean stopCellEditing(){
		return tce.stopCellEditing();
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#cancelCellEditing()
	 */
	protected void cancelCellEditing() {
		tce.cancelCellEditing();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#dereference()
	 */
	public void dereference(){
		Component jta = getEditorComponent();
		jta.removeMouseListener(getFormTable());
		jta.removeFocusListener(getFormTable());
		tce.removeCellEditorListener(getFormTable());

		tce.dereference();
		tce = null;

		super.dereference();
	}
}
