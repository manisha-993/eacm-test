//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.edit.form;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.UIManager;


import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.edit.SpellCheckHandler;
import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.LongCellEditor;

import COM.ibm.eannounce.objects.EANAttribute;

/***************
 * 
 * Form LongText editor and renderer
 * @author Wendy Stimpson
 */
//$Log: LongCellPanel.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class LongCellPanel extends FormCellPanel {
	private static final long serialVersionUID = 1L;
	private static final Dimension cellsize = new Dimension(300, 105);
	
	private LongCellEditor tce = null;
	private FormLongRenderer renderer = null;
	private JScrollPane jsp = null;
	
	/**
	 * constructor
	 * @param key
	 * @param att
	 * @param ft
	 */
	public LongCellPanel(String key, EANAttribute att, FormTable ft) {
		super(key, att,ft);

		renderer = new FormLongRenderer();
	
		tce = new LongCellEditor(this);
		tce.setAttribute(att);

		((JTextArea)tce.getComponent()).setBorder(UIManager.getBorder(EMPTY_BORDER_KEY));

		addListeners();
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#getSpellCheckHandler()
	 */
	protected SpellCheckHandler getSpellCheckHandler(){
		return tce.getSpellCheckHandler();
	}
    /**
     * perform the cut action, text editors must override this
     */
    protected void cut(){
    	((JTextArea)tce.getComponent()).cut();
    }
    
	/**
	 * this must be done after the base class is init, cell components are not created until after
	 * base class inits
	 */
	private void addListeners(){
		// this is the component that will be used for editing
		Component jta = tce.getComponent();

		jta.addKeyListener(getFormTable());
		jta.addFocusListener(getFormTable());
		jta.addMouseListener(getFormTable());
		tce.addCellEditorListener(getFormTable());
		tce.addDocumentListener(getFormTable());
		
		renderer.addMouseListener(getFormTable());
		renderer.addFocusListener(getFormTable());		
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadEditor()
	 */
	protected void loadEditor(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;

		if(jsp ==null){
			jsp = new JScrollPane(tce.getComponent());
			jsp.setSize(cellsize);
			jsp.setPreferredSize(cellsize);
			jsp.setMaximumSize(cellsize);
	  		jsp.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE); // improve perf, reduce flicker
		}else{
			jsp.removeMouseListener(getFormTable());
			
			jsp.setViewportView(tce.getComponent());
		}

		JTextArea jta = (JTextArea)tce.getComponent();
		jta.setCaretPosition(0);
		
		add(jsp,gbca);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadRenderer()
	 */
	protected void loadRenderer(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;

		// this really just loads the attribute value
		renderer.getRendererComponent(this, this.getAttribute().toString(), this.isFocusOwner());
		if(jsp ==null){
			jsp = new JScrollPane(renderer);
			jsp.setSize(cellsize);
			jsp.setPreferredSize(cellsize);
			jsp.setMaximumSize(cellsize);
	  		jsp.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE); // improve perf, reduce flicker
		}else{
			jsp.setViewportView(renderer);
		}
		
		//this is needed when user double clicks to get lock
		jsp.addMouseListener(getFormTable());

		add(jsp,gbca);
	}

	//--- needed for copy/paste support
    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * If more than one binding exists for the key, only the first valid one found is used. 
     * Input maps are checked in this order:
     * 1.The focused component's WHEN_FOCUSED input map.
     * 2.The focused component's WHEN_ANCESTOR_OF_FOCUSED_COMPONENT input map.
     * 3.The WHEN_ANCESTOR_OF_FOCUSED_COMPONENT input maps of the focused component's parent, and then its 
     * parent's parent, and so on, continuing up the containment hierarchy. Note: Input maps for disabled 
     * components are skipped.
     * 4.The WHEN_IN_FOCUSED_WINDOW input maps of all the enabled components in the focused window are searched. 
     * Because the order of searching the components is unpredictable, avoid duplicate WHEN_IN_FOCUSED_WINDOW bindings!
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
			selection = ((JTextArea)getEditorComponent()).getSelectedText();
			all = ((JTextArea)getEditorComponent()).getText();
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
	 * this is needed for spell check
	 * @return
	 */
	public AttrCellEditor getCellEditor(){
		return tce;
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
		
		//reload the attribute
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
		Component jta = tce.getComponent();

		jta.removeKeyListener(getFormTable());
		jta.removeFocusListener(getFormTable());
		jta.removeMouseListener(getFormTable());
		tce.removeCellEditorListener(getFormTable());
		
		renderer.removeMouseListener(getFormTable());
		renderer.removeFocusListener(getFormTable());
		
		if(jsp !=null){
			jsp.removeAll();
			jsp.setUI(null);
			jsp.removeMouseListener(getFormTable());
			jsp = null;
		}

		tce.dereference();
		tce = null;

		renderer.dereference();
		renderer = null;

		super.dereference();
	}
}
