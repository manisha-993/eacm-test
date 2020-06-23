//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.DateCellEditor;

import COM.ibm.eannounce.objects.EANAttribute;
/***************
 * 
 * Form date editor and renderer
 * @author Wendy Stimpson
 */
//$Log: DateCellPanel.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class DateCellPanel extends FormCellPanel {
	private static final long serialVersionUID = 1L;
	private DateCellEditor tce = null;
	private FormLabelRenderer renderer = null;

	/**
	 * constructor
	 * @param key
	 * @param att
	 * @param ft
	 */
	public DateCellPanel(String key, EANAttribute att, FormTable ft) {
		super(key, att,ft);

		renderer = new FormLabelRenderer();
		tce = new DateCellEditor(false);
		tce.setAttribute(att);
		tce.setFormCellPanel(this);

		((JTextField)tce.getComponent()).setBorder(UIManager.getBorder(EMPTY_BORDER_KEY));

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
     * perform the cut action, text editors must override this
     */
    protected void cut(){
    	tce.cut();
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
	 * @see java.awt.Component#setBounds(int, int, int, int)
	 */
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		// do this here after size is known
		Rectangle rect = this.getBounds();
		rect.width = rect.width-5; // move button over a little bit
		tce.getComponent().setBounds(rect); // needed to display button
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
	//--- needed for copy/paste support
    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keystroke
     */
    protected void registerEACMAction(EACMAction act, KeyStroke keystroke){
     	tce.registerEACMAction(act, keystroke);
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    protected void unregisterEACMAction(EACMAction act,KeyStroke keystroke){
     	tce.unregisterEACMAction(act, keystroke);
    }

	/**
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
			selection = ((JFormattedTextField)getEditorComponent()).getSelectedText(); 
			all = ((JFormattedTextField)getEditorComponent()).getText();
		}
		
		if(all.equals(selection)){
			return getAttribute();
		}
		return selection;
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
		tce.getComponent().addKeyListener(getFormTable());
		tce.getComponent().addFocusListener(getFormTable());
		tce.getComponent().addMouseListener(getFormTable());
		tce.addCellEditorListener(getFormTable());
		tce.addDocumentListener(getFormTable());
		
		renderer.addMouseListener(getFormTable());
		renderer.addFocusListener(getFormTable());
	}


	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#requestFocusInWindow()
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
		tce.setAttribute(this.getAttribute()); //force a reload like if a value was converted to uppercase
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
	/**
     * called if the user clicks on a different cell, must check before putting up dialog in form
     */	
	protected boolean canStopCellEditing(){
		return tce.canStopCellEditing();
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
		tce.getComponent().removeKeyListener(getFormTable());
		tce.getComponent().removeFocusListener(getFormTable());
		tce.getComponent().removeMouseListener(getFormTable());
		tce.removeCellEditorListener(getFormTable());

		renderer.removeMouseListener(getFormTable());
		renderer.removeFocusListener(getFormTable());
		
		tce.dereference();
		tce = null;

		renderer.dereference();
		renderer = null;

		super.dereference();
	}
}
