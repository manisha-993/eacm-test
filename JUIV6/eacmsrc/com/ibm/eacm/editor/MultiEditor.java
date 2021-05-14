//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.Color;
import java.awt.Window;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import java.awt.event.KeyEvent;

import javax.accessibility.AccessibleContext;

import javax.swing.*;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.MetaFlag;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;
import com.ibm.eacm.preference.ColorPref;
import com.ibm.eacm.rend.LabelRenderer;

import com.ibm.eacm.edit.form.FormCellPanel;
import com.ibm.eacm.edit.form.FormTable;

/**
 * this class is used to edit multiflags.  it is a panel that will be rendered as a multiline label
 * it will popup a popupmenu or dialog based on preferences
 * @author Wendy Stimpson
 */
//$Log: MultiEditor.java,v $
//Revision 1.4  2013/07/31 16:55:11  wendy
//add png files
//
//Revision 1.3  2013/07/26 17:26:33  wendy
//change close icon
//
//Revision 1.2  2013/07/19 17:51:39  wendy
//pass window parent into mf cell editor
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class MultiEditor extends JPanel implements EditorComponent {
	private static final long serialVersionUID = 1L;

	private KeyEvent preloadkey = null;
	private MultiPopup popup = null;
	private MultiCellEditor multiCellEd = null;
	private CancelAction cancelAction = null;
	private AcceptAction acceptAction = null;

	private LabelRenderer editRenderLbl = null; // use this for better performance

	// added for formeditor
	private FormCellPanel fcp = null; // added for formeditor

	/* (non-Javadoc)
	 * @see com.ibm.eacm.editor.EditorComponent#getFormCellPanel()
	 */
	public FormCellPanel getFormCellPanel() { 
		return fcp;
	}
	
	/**
	 * get the panel to be used in the form
	 * @return
	 */
	protected MultiPopupPanel getFormEditor(){
		return (MultiPopupPanel)popup;
	}
	/**
	 * constructor
	 * used in forms
	 * @param cellEd
	 * @param f
	 */
	public MultiEditor(MultiCellEditor cellEd,FormCellPanel f) {
		super(new BorderLayout());
		cancelAction = new CancelAction(true);
		acceptAction = new AcceptAction(true);
		fcp=f;

		multiCellEd = cellEd;

		popup = new MultiPopupPanel(this, cancelAction, acceptAction);

		setFocusable(false);
		setRequestFocusEnabled(false); 

		initAccessibility("accessible.multiEditor");
	}
	// added for formeditor
	
	/**
	 * used in vertical and horizontal tables
	 * constructor
	 * @param parent
	 * @param cellEd
	 */
	public MultiEditor(Window parent, MultiCellEditor cellEd) {
		super(new BorderLayout());
		cancelAction = new CancelAction(false);
		acceptAction = new AcceptAction(false);
		editRenderLbl = new LabelRenderer(); // use this for better performance
		
		multiCellEd = cellEd;

		add(editRenderLbl,BorderLayout.WEST);

		if (BehaviorPref.isVertFlagFrame()) { 
			popup = new MultiPopupDialog(parent,this,cancelAction,acceptAction);
		} else {
			popup = new MultiPopupMenu(this,cancelAction,acceptAction);
		}

		setFocusable(false);
		setRequestFocusEnabled(false); 

		initAccessibility("accessible.multiEditor");
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#getBackground()
	 */
	public Color getBackground() {
		if(multiCellEd!=null){
			return multiCellEd.getBackground("");
		}
		return ColorPref.getOkColor();
	}

	/**
	 * discardChanges
	 * /
	protected void discardChanges() {
		multiCellEd.cancelCellEditing(); // must tell listeners that edit was cancelled
	}

	/**
	 * acceptChanges
	 */
	protected void acceptChanges() {
		multiCellEd.stopCellEditing(); // must tell listeners that edit has stopped
	}

	/**
     * load the metaflags - called from paste
     * @param mf
     */
	protected void loadMetaFlags(MetaFlag[] mf) {
		popup.loadMetaFlags(mf);
	}
	
	/**
	 * showPopup
	 */
	protected void showPopup() {
		popup.loadMetaFlags((MetaFlag[])multiCellEd.getAttribute().get());
		popup.setDescription(multiCellEd.getAttribute().getMetaAttribute().getLongDescription());
	    
		popup.showPopup(this);
		if(preloadkey!=null){
			popup.processPopupKey(preloadkey);
			preloadkey = null;
		}
	}
	/**
	 * showPopup
	 * @param _ke
	 */
	protected void preloadKeyEvent(KeyEvent _ke) {
		preloadkey = _ke;
	}
	/**
	 * hidePopup
	 */
	protected void hidePopup() {
		popup.hidePopup();
	}

	/**
	 * return metaflags from this editor
	 * @return
	 */
	protected MetaFlag[] getEditorValue() {
		return popup.getMetaFlags();
	}

	/**
	 * setAttribute to be used in form
	 * @param _att
	 */
	protected void setAttribute(EANAttribute _att) {
		if(popup instanceof MultiPopupPanel){
			popup.loadMetaFlags((MetaFlag[])multiCellEd.getAttribute().get());
			popup.setDescription(multiCellEd.getAttribute().getMetaAttribute().getLongDescription());
		}
	}

	/**
	 * release memory
	 */
	protected void dereference() {
		fcp = null;
		
		cancelAction.dereference();
		cancelAction = null;

		acceptAction.dereference();
		acceptAction = null;

		initAccessibility(null);

		preloadkey = null;
		multiCellEd = null;
		
		if(editRenderLbl != null){
			editRenderLbl.dereference();
			editRenderLbl = null;
		}

		popup.dereference();
		popup = null;

		removeAll();
		setUI(null);
	}

	/**
	 * set text to display in the renderer when the editor opens (only visible if dialog doesnt cover cell)
	 * @param curFlagDescs
	 */
	protected void setRendererText(String curFlagDescs) {
		if(editRenderLbl != null){
			editRenderLbl.setText(curFlagDescs);
		}
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


	/**
	 * these actions are shared with the popupmenu and the dialog flavor of the multieditor
	 *
	 */
	private class CancelAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private static final String NAME="cnclme";
		CancelAction(boolean isForm){
			super(Utils.getResource(NAME));
			String ttName = NAME;
			if(isForm){
				ttName = ttName+"Form";
			}
			String value = Utils.getToolTip(ttName);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}

			putValue(Action.SMALL_ICON, Utils.getImageIcon("closex.png")); 
			putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(NAME)));
		}
		public void actionPerformed(ActionEvent e) {
			// this is needed if user was editing a different control and left a invalid value
			if(getFormCellPanel()!=null){
				FormTable ft = getFormCellPanel().getFormTable();
				// make sure it is not stopping this editor
				if(ft.getCurrentEditor()!=getFormCellPanel()){
					if(!ft.canStopEditing()){
						return; 
					}
				}
			}
			
			multiCellEd.cancelCellEditing(); // must tell listeners that edit was cancelled
		}
		void dereference(){
			putValue(Action.NAME, null);
			putValue(Action.SHORT_DESCRIPTION, null);
			putValue(Action.SMALL_ICON,null);
			putValue(Action.MNEMONIC_KEY,null);
		}
	}
	private class AcceptAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private static final String NAME="accptme";
		AcceptAction(boolean isForm){
			super(Utils.getResource(NAME));
			String ttName = NAME;
			if(isForm){
				ttName = ttName+"Form";
			}
			String value = Utils.getToolTip(ttName);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
			putValue(Action.SMALL_ICON, Utils.getImageIcon("save.gif")); 
			putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(NAME)));
		}
		public void actionPerformed(ActionEvent e) {
			// this is needed if user was editing a different control and left a invalid value
			if(getFormCellPanel()!=null){
				FormTable ft = getFormCellPanel().getFormTable();
				// make sure it is not stopping this editor
				if(ft.getCurrentEditor()!=getFormCellPanel()){
					if(!ft.canStopEditing()){
						return; 
					}
				}
			}
			
			acceptChanges();
		}
		void dereference(){
			putValue(Action.NAME, null);
			putValue(Action.SHORT_DESCRIPTION, null);
			putValue(Action.SMALL_ICON,null);
			putValue(Action.MNEMONIC_KEY,null);
		}
	}
}

