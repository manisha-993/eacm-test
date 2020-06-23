//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.UIManager;

import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.FlagCellEditor;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.EANAttribute;
/***************
 * 
 * Form single flag editor and renderer
 * @author Wendy Stimpson
 */
//$Log: FlagCellPanel.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class FlagCellPanel extends FormCellPanel {
	private static final long serialVersionUID = 1L;
	private FlagCellEditor tce = null;
	private FormLabelRenderer renderer = null;

	/**
	 * constructor
	 * @param key
	 * @param att
	 * @param ft
	 */
	public FlagCellPanel(String key, EANAttribute att, FormTable ft) {
		super(key, att,ft);

		renderer = new FormLabelRenderer();
		tce = new FlagCellEditor(false);
		tce.setFormCellPanel(this);
		tce.setAttribute(att);

		((JComboBox)tce.getComponent()).setBorder(UIManager.getBorder(EMPTY_BORDER_KEY));

		addListeners();
	}

	/**
	 * this is needed for spell check and paste
	 * @return
	 */
	public AttrCellEditor getCellEditor(){
		return tce;
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadEditor()
	 */
	protected void loadEditor(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;
		gbca.gridx=0;
		gbca.gridy=0;

		add(tce.getComponent(),gbca);
		// this label isnt seen.. it just forces the editor to the top of the cell
		gbca.weighty = 1;
		gbca.gridy=1;
		add(new JLabel() ,gbca);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadRenderer()
	 */
	protected void loadRenderer(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;
		gbca.gridx=0;
		gbca.gridy=0;

		add(renderer.getRendererComponent(this, this.getAttribute().toString(), this.isFocusOwner()) ,gbca);
		// this label isnt seen.. it just forces the renderer to the top of the cell
		gbca.weighty = 1;
		gbca.gridy=1;
		add(new JLabel() ,gbca);
	}

	/**
	 * needed for copy/paste support
	 * get the item selected
	 * @return
	 */
	protected Object getSelection() { 
		String selection = null;
		String all = null;
		if(isDisplayOnly()){
			selection = renderer.getSelectedText();
			all = renderer.getText();
		}else{
			//use attribute if in edit mode
			selection = "";
			all = "";
		}
		
		if(all.equals(selection)){
			return getAttribute();
		}
		return selection;
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#getEditorComponent()
	 */
	protected Component getEditorComponent(){
		return tce.getComponent();
	}
	/**
	 * this must be done after the base class is init, cell components are not created until after
	 * base class inits
	 */
	private void addListeners(){
		// needed when not using enhanced flag
		if (!Utils.isArmed(ENHANCED_FLAG_EDIT)) {
			((JComboBox)tce.getComponent()).addMouseListener(getFormTable());
			((JComboBox)tce.getComponent()).addFocusListener(getFormTable());
			((JComboBox)tce.getComponent()).addActionListener(getFormTable()); //needed to save the edit
			((JComboBox)tce.getComponent()).addKeyListener(getFormTable());
		}else{
			// needed for enhancedflag
			((JComboBox)tce.getComponent()).getEditor().getEditorComponent().addMouseListener(getFormTable());
			((JComboBox)tce.getComponent()).getEditor().getEditorComponent().addFocusListener(getFormTable());		
			((JComboBox)tce.getComponent()).getEditor().getEditorComponent().addKeyListener(getFormTable());
		}
		renderer.addMouseListener(getFormTable());
		renderer.addFocusListener(getFormTable());
		
		tce.addCellEditorListener(getFormTable());
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#requestFocusInWindow()
	 */
	public boolean requestFocusInWindow() {
		if(isDisplayOnly()){
			return super.requestFocusInWindow();
		}else{
			// put focus in the edit control
			return tce.getComponent().requestFocusInWindow();
		}
	}
	
	/* (non-Javadoc)
	 * match ui values set in the form
	 * @see javax.swing.JComponent#setBackground(java.awt.Color)
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
		renderer.getRendererComponent(this, this.getAttribute().toString(), this.isFocusOwner());
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
		super.dereference(); // this must be first
		
	    if (!Utils.isArmed(ENHANCED_FLAG_EDIT)) {
	    	((JComboBox)tce.getComponent()).removeFocusListener(getFormTable());
			((JComboBox)tce.getComponent()).removeMouseListener(getFormTable());
	    	((JComboBox)tce.getComponent()).removeActionListener(getFormTable());
			((JComboBox)tce.getComponent()).removeKeyListener(getFormTable());
	    }else{
			((JComboBox)tce.getComponent()).getEditor().getEditorComponent().removeMouseListener(getFormTable());
			((JComboBox)tce.getComponent()).getEditor().getEditorComponent().removeFocusListener(getFormTable());
			((JComboBox)tce.getComponent()).getEditor().getEditorComponent().removeKeyListener(getFormTable());
	    }
		renderer.removeMouseListener(getFormTable());
		renderer.removeFocusListener(getFormTable());
		
		tce.removeCellEditorListener(getFormTable());

		tce.dereference();
		tce = null;

		renderer.dereference();
		renderer = null;
	}
}
