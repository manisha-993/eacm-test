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

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.UIManager;
import com.ibm.eacm.editor.AttrCellEditor;
import com.ibm.eacm.editor.XMLCellEditor;

import COM.ibm.eannounce.objects.EANAttribute;
/***************
* 
* Form XML editor and renderer
* @author Wendy Stimpson
*/
//$Log: XMLCellPanel.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class XMLCellPanel extends FormCellPanel {
	private static final long serialVersionUID = 1L;
	private static final Dimension cellsize = new Dimension(300, 105);
	
	private XMLCellEditor tce = null;
	private JScrollPane jsp = null;
	private FormXMLRenderer renderer = null;

	/**
	 * constructor
	 * @param key
	 * @param att
	 * @param ft
	 */
	public XMLCellPanel(String key, EANAttribute att, FormTable ft) {
		super(key, att,ft);
	
		tce = new XMLCellEditor(getFormTable().getEditController(),this);
		tce.setAttribute(att);

		((JPanel)tce.getComponent()).setBorder(UIManager.getBorder(EMPTY_BORDER_KEY));

		renderer = new FormXMLRenderer();
		
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
		tce.addCellEditorListener(getFormTable());
		
		// this is needed to launch the xml editor
		renderer.addMouseListener(getFormTable());
		renderer.addFocusListener(getFormTable());
		renderer.addKeyListener(getFormTable()); // allow ctrl-enter to launch editor
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#loadEditor()
	 */
	protected void loadEditor(){
		// force this to fill entire panel
		GridBagConstraints gbca = new GridBagConstraints();
		gbca.fill = GridBagConstraints.BOTH;
		gbca.weightx = 1;

		// this really just loads the attribute value
		renderer.getRendererComponent(this, this.getAttribute().toString(), this.isFocusOwner());
		renderer.setBackground(tce.getComponent().getBackground());
		if(jsp ==null){
			jsp = new JScrollPane(renderer);
			jsp.setSize(cellsize);
			jsp.setPreferredSize(cellsize);
			jsp.setMaximumSize(cellsize);
	  		jsp.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE); // improve perf, reduce flicker
		}else{
			jsp.removeMouseListener(getFormTable());
			
			jsp.setViewportView(renderer);
		}

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
			// same component would have selection
			selection = renderer.getSelectedText();
			all = renderer.getText();
		}
		
		if(all.equals(selection)){
			return getAttribute();
		}
		return selection;
	}
	//--- end needed for copy/paste support
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#requestFocusInWindow()
	 */
	public boolean requestFocusInWindow() {
		if(isDisplayOnly()){
			return super.requestFocusInWindow();
		}else{
			// put focus in the edit control
			return renderer.requestFocusInWindow();
		}
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.edit.form.FormCellPanel#getEditorComponent()
	 */
	protected Component getEditorComponent(){
		return tce.getComponent();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setForeground(java.awt.Color)
	 */
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (tce !=null){
			tce.getComponent().setForeground(fg);
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setFont(java.awt.Font)
	 */
	public void setFont(Font font) {
		super.setFont(font);
		if (tce !=null){
			tce.getComponent().setFont(font);
		}
	}
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#setOpaque(boolean)
	 */
	public void setOpaque(boolean isOpaque) {
		super.setOpaque(isOpaque);
		if (tce !=null){
			((JComponent)tce.getComponent()).setOpaque(isOpaque);
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
		renderer.setBackground(tce.getComponent().getBackground());
		//must do this to see all changes and get scrollbars when text changes
		if(jsp!=null) {
			jsp.setViewportView(renderer);//tce.getComponent());
			jsp.revalidate();
		}
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
		tce.removeCellEditorListener(getFormTable());
		renderer.removeFocusListener(getFormTable());
		renderer.removeMouseListener(getFormTable());
		renderer.removeKeyListener(getFormTable());
		
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
