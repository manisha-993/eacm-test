//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.editor;


import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.Utils;
import com.ibm.transform.oim.eacm.xml.editor.*;

/**
* this class is used to display an xml text editor in a frame
* @author Wendy Stimpson
*/
//$Log: XMLPopupFrame.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class XMLPopupFrame extends JFrame implements XMLEditorListener
{
	private static final long serialVersionUID = 1L;

	private XMLEditor xeditor = null;
	private XMLEditorPanel xmlEditorPanel = null;
	private EANAttribute attr = null;
	private String xmlvalue=null;
	private boolean reallyEditable = true;
	
	/**
	 * constructor
	 * @param le
	 */
	public XMLPopupFrame(XMLEditorPanel le) {//DEFAULT_MODALITY_TYPE
		//super((java.awt.Window)null, "XML Editor",JDialog.ModalityType.APPLICATION_MODAL);
		super("XML Editor");//,JDialog.ModalityType.APPLICATION_MODAL);

		xmlEditorPanel = le;
		// the xmleditor will add toolbar and content to this frame
		xeditor = new XMLEditor(this.getRootPane(),EACM.getEACM().getDictionary());
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		this.setSize(this.getPreferredSize());
		setResizable(true);
	}
	
	/**
	 * release memory
	 */
	protected void dereference() {
		dispose();

		xeditor.dereference();
		xeditor = null;

		xmlEditorPanel = null;

		attr = null;
		xmlvalue=null;
		
		removeAll();
	}

	/**
	 * set view only - override attr capability
	 * @param b
	 */
	protected void setEditable(boolean b){
		reallyEditable = b;
	}
	
	/**
	 * get the current value in the editor
	 * @return
	 */
	protected String getEditorValue() {
		return xmlvalue;
	}

	/**
	 * preloadKeyEvent
	 * @param _ke
	 * 	
	 */
	protected void preloadKeyEvent(KeyEvent _ke) {
	}

	/**
	 * update editor with this attribute
	 * @param _ean
	 */
	protected void setAttribute(EANAttribute ean) {
		attr = ean;
		if (attr != null) {
			xmlvalue = (String) attr.get(); // give this something if used in a form and stopcellediting is called but the editor
							  // was not invoked
		}else{
			xmlvalue = null;
		}
	}
	protected void setAttributeorig(EANAttribute ean) {
		boolean bSpellCheck = false;
		String dtd = "default.dtd";
		boolean editable = false;
		String value = "";
		attr = ean;
		if (attr != null) {
			EANMetaAttribute meta = attr.getMetaAttribute(); 
			editable = attr.isEditable();
			value = (String) attr.get();
			if (meta != null) { 
				bSpellCheck = meta.isSpellCheckable();
				dtd = meta.getXMLDTD();
			} 
			xmlvalue = value; // give this something if used in a form and stopcellediting is called but the editor
							  // was not invoked
			setTitle(Utils.getResource("xml.panel") + " -- " + attr.getLongDescription()); 
		}else{
			setTitle(Utils.getResource("xml.panel"));
			xmlvalue = null;
		}

		xeditor.loadXML(value, reallyEditable && editable, dtd,bSpellCheck); 
	}
	
	/**
	 * load the editor itself, done just before showing it
	 */
	protected void loadAttribute(){
		boolean bSpellCheck = false;
		String dtd = "default.dtd";
		boolean editable = false;
		String value = "";
		if (attr != null) {
			EANMetaAttribute meta = attr.getMetaAttribute(); 
			editable = attr.isEditable();
			value = (String) attr.get();
			if (meta != null) { 
				bSpellCheck = meta.isSpellCheckable();
				dtd = meta.getXMLDTD();
			} 
			xmlvalue = value; // give this something if used in a form and stopcellediting is called but the editor
							  // was not invoked
			setTitle(Utils.getResource("xml.panel") + " -- " + attr.getLongDescription()); 
		}else{
			setTitle(Utils.getResource("xml.panel"));
			xmlvalue = null;
		}

		xeditor.loadXML(value, reallyEditable && editable, dtd,bSpellCheck); 
	}

	/**
	 * hide the editor popup
	 * called when the edit is cancelled or stopped (save is done in the xmleditor)
	 */
	protected void hidePopup() {
		super.setVisible(false);
	}

	/**
	 * show the editor popup
	 * called when the shouldselectcell() is invoked
	 */
	protected void showPopup() {
		if(this.isShowing()){
			return;
		}
		pack();
		setLocationRelativeTo(xmlEditorPanel);
		super.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xml.editor.XMLEditorListener#attributeHelpTextRequested()
	 */
	public String attributeHelpTextRequested() {
		if (attr!=null){
			return attr.getHelp("");
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xml.editor.XMLEditorListener#editorClosing()
	 */
	public void editorClosing() {
		xmlEditorPanel.cancelCellEditing();
	}

	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xml.editor.XMLEditorListener#updateRequested()
	 */
	public boolean updateRequested() {
		try {
			xmlvalue = xeditor.getCompletedXML();
			xmlEditorPanel.stopCellEditing();
		} catch (IOException e) {
			xmlvalue = null;
			com.ibm.eacm.ui.UI.showException(null, e);
			return false;
		}
		return true;
	}
}
