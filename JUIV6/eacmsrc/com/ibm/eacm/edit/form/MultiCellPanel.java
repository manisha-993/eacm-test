//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.KeyStroke;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.MultiCellEditor;
import com.ibm.eacm.editor.MultiPopupPanel;

import COM.ibm.eannounce.objects.EANAttribute;
/***************
*
* Form Multiflag editor and renderer
* @author Wendy Stimpson
*/
//$Log: MultiCellPanel.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class MultiCellPanel extends FormCellPanel {
	private static final long serialVersionUID = 1L;
	private MultiCellEditor tce = null;

	/**
	 * constructor
	 * @param key
	 * @param att
	 * @param ft
	 */
	public MultiCellPanel(String key, EANAttribute att, FormTable ft) {
		super(key, att,ft);

		tce = new MultiCellEditor(this);
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

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadEditor()
	 */
	protected void loadEditor(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;

		// change background too
		((MultiPopupPanel)tce.getComponent()).setEnabled(true);

		add(tce.getComponent(),gbca);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadRenderer()
	 */
	protected void loadRenderer(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;

		// change background too
		((MultiPopupPanel)tce.getComponent()).setEnabled(false);

		add(tce.getComponent(),gbca);
	}

	//--- needed for copy/paste support
    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keystroke
     */
    protected void registerEACMAction(EACMAction act, KeyStroke keystroke){
    	((MultiPopupPanel)getEditorComponent()).registerEACMAction(act, keystroke);
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    protected void unregisterEACMAction(EACMAction act,KeyStroke keystroke){
      	((MultiPopupPanel)getEditorComponent()).unregisterEACMAction(act, keystroke);
    }

	/**
	 * get the item selected for copy
	 * @return
	 */
	protected Object getSelection() {
		// user may have changed flag selection but didnt press one of the buttons.. force any changes into the
		// attribute first
		stopCellEditing();

		return getAttribute();
	}
	//--- end needed for copy/paste support

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
		MultiPopupPanel jta = ((MultiPopupPanel)tce.getComponent());

		jta.addKeyListener(getFormTable());
		jta.addFocusListener(getFormTable());
		jta.addMouseListener(getFormTable());
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
			MultiPopupPanel jta = (MultiPopupPanel)tce.getComponent();
			return jta.requestFocusInWindow();
		}
	}

	/**
	 * is this editor currently selected and is editable
	 * @return
	 */
	protected boolean editorIsActive(){
		if(isDisplayOnly()){
			return false;
		}
		MultiPopupPanel multi = (MultiPopupPanel)getEditorComponent();
		return multi.editorIsActive();
	}

	/* (non-Javadoc)
	 * match ui values set in the form
	 * @see javax.swing.JComponent#setForeground(java.awt.Color)
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (tce !=null){
			tce.setForeground(fg);
		}
	}
	public void setFont(Font font) {
		super.setFont(font);
		if (tce !=null){
			tce.setFont(font);
		}
	}
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
	protected EANAttribute getAttribute(){
		return tce.getAttribute();
	}
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
		MultiPopupPanel jta = (MultiPopupPanel)tce.getComponent();

		jta.removeKeyListener(getFormTable());
		jta.removeFocusListener(getFormTable());
		jta.removeMouseListener(getFormTable());

		tce.removeCellEditorListener(getFormTable());

		tce.dereference();
		tce = null;

		super.dereference();
	}
}
