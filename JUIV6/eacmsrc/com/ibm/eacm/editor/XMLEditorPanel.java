//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package com.ibm.eacm.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.accessibility.AccessibleContext;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.ColorPref;
import com.ibm.eacm.rend.LabelRenderer;

import COM.ibm.eannounce.objects.EANAttribute;

/**
* this class is used to manage an xml text editor in a frame
* it renders the value in the table cell or form cell
* it launches the xmleditor and gets the updated value from it
* @author Wendy Stimpson
*/
//$Log: XMLEditorPanel.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class XMLEditorPanel extends JPanel implements EditorComponent {
	private static final long serialVersionUID = 1L;
	private XMLCellEditor xmlCellEd;
	private KeyEvent preloadkey = null;
	private XMLPopupFrame popup = null;
	private LabelRenderer editRenderLbl = new LabelRenderer(); // use this for better performance

	// added for formeditor
	private FormCellPanel fcp = null; 
	
	/**
	 * added for formeditor
	 * used when this is part of a form
	 * @param f
	 */
	void setFormCellPanel(FormCellPanel f) {
		fcp=f;
	}
	/* (non-Javadoc)
	 * added for formeditor
	 * @see com.ibm.eacm.editor.EditorComponent#getFormCellPanel()
	 */
	public FormCellPanel getFormCellPanel() { 
		return fcp;
	}
	/**
	 * added for formeditor
	 * used for launching the xml editor when mouse is clicked in the form cell
	 * @return
	 */
	public XMLCellEditor getXMLCellEditor() { 
		return xmlCellEd;
	}
	
	/**
	 * constructor
	 * @param lce
	 */
	protected XMLEditorPanel(XMLCellEditor lce){
		super(new BorderLayout());
		
		xmlCellEd = lce;
		
		//put the label content at the top in the cell
		editRenderLbl.setVerticalAlignment(SwingConstants.TOP);

		add(editRenderLbl,BorderLayout.CENTER);
		
		popup = new XMLPopupFrame(this);		

		setFocusable(false);
		setRequestFocusEnabled(false); 

		initAccessibility("accessible.XMLEditor");
	}
	/**
	 * update editor with this attribute
	 * @param _ean
	 */
	protected void setAttribute(EANAttribute _ean) {
		String text = "";
		if(_ean!=null){
			text = _ean.toString();
		}
		
		editRenderLbl.setText(Routines.convertToMultilineHTML(text)); // this will be in the cell
		popup.setAttribute(_ean); // this is the editor itself
	}
	/**
	 * used to set view only
	 * @param b
	 */
	protected void setEditable(boolean b){
		popup.setEditable(b);
	}
	/**
	 * hide the xmleditor
	 */
	protected void hidePopup(){
		popup.hidePopup();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground() {
		if(xmlCellEd!=null){
			return xmlCellEd.getBackground("");
		}
		return ColorPref.getLockColor();
	}
	
	/**
	 * dont close the editor if the popup is open
	 * @return
	 */
	protected boolean isPopupShowing(){
		return popup.isShowing();
	}
	
	/**
	 * show the xmleditor
	 */
	protected void showPopup() {
		// actually load the editor now
		popup.loadAttribute();//(xmlCellEd.getAttribute()); 
		
		if(preloadkey!=null){
			popup.preloadKeyEvent(preloadkey);
			preloadkey = null;
		}
		
		popup.showPopup();
	}
	/**
	 * preloadKeyEvent
	 * @param _ke
	 */
	protected void preloadKeyEvent(KeyEvent _ke) {
		preloadkey = _ke;
	}
	
	/**
	 * get current value
	 * @return
	 */
	protected String getEditorValue() {
		return popup.getEditorValue();
	}
	
  /**
   * called if the user cancels the edit, should hide the dialog here
   * @see javax.swing.AbstractCellEditor#cancelCellEditing()
   */
	protected void cancelCellEditing() { 
		hidePopup();
		if(fcp!=null){
			fcp.getFormTable().requestFocusInWindow();
		}
		xmlCellEd.cancelCellEditing(); 
	}
  /**
   * called if the user clicks on a different cell, should hide the dialog here
   * returning true tells the table to use the partially edited value, that it is valid
   * @see javax.swing.AbstractCellEditor#stopCellEditing()
   */
	protected boolean stopCellEditing() { 
		hidePopup();
		if(fcp!=null){
			fcp.getFormTable().requestFocusInWindow();
		}
		return xmlCellEd.stopCellEditing(); 
	}
	
	/**
	 * release memory
	 */
	protected void dereference(){
		initAccessibility(null);
		fcp = null;
		
		preloadkey = null;
		xmlCellEd = null;
		
		editRenderLbl.dereference();
		editRenderLbl = null;

		popup.dereference();
		popup = null;

		removeAll();
		setUI(null);
	}
	/**
     * initAccessibility 
     * @param _s
     */
    private void initAccessibility(String _s) {
    	AccessibleContext ac = getAccessibleContext();
    	if (ac != null) {
    		if (_s == null) {
    			ac.setAccessibleName(null);
    			ac.setAccessibleDescription(null);
    		} else {
    			String strAccessible = Utils.getResource(_s);
    			ac.setAccessibleName(strAccessible);
    			ac.setAccessibleDescription(strAccessible);
    		}
    	}
	}
}
