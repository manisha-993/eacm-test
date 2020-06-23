//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.editor;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultCaret;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.Utils;

/**
 * this class is used to display a long text editor in a dialog
 * @author Wendy Stimpson
 */
//$Log: LongPopupDialog.java,v $
//Revision 1.3  2013/10/23 19:21:33  wendy
//dialog occasionally went behind JUI, make it the owner
//
//Revision 1.2  2013/08/14 16:45:38  wendy
//scroll to caret as text is added
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class LongPopupDialog extends JDialog 
{
	private static final long serialVersionUID = 1L;
	
	private LongTextArea textArea = null;
	private JScrollPane scroll = null;
	private Dimension scrollSize = new Dimension(300, 400);
	private JPanel pnlButton = new JPanel(new BorderLayout());
	private JButton btnSave = null;
	private JButton btnCncl = null;
	private LongEditor longEditor = null;
	private String initValue = null;
	private CloseAction closeAction = null;
	private CancelAction cancelAction = null;
	private AcceptAction acceptAction = null;
	
	/**
	 * constructor
	 * @param le
	 */
	protected LongPopupDialog(LongEditor le,LongTextArea lta) {//DEFAULT_MODALITY_TYPE
		//super((java.awt.Window)null, "LongText Editor",JDialog.ModalityType.APPLICATION_MODAL);
		super(EACM.getEACM(), "LongText Editor",JDialog.ModalityType.APPLICATION_MODAL); //sometimes after paste the dialog goes behind the JUI

		longEditor = le;
		closeAction = new CloseAction();
		textArea = lta;
		scroll = new JScrollPane(textArea);
		// scroll to caret
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		cancelAction = new CancelAction();
		acceptAction = new AcceptAction();
		
		btnCncl = new JButton(cancelAction);
		btnSave = new JButton(acceptAction);
		btnCncl.setMnemonic((char)((Integer)btnCncl.getAction().getValue(Action.MNEMONIC_KEY)).intValue());
		btnSave.setMnemonic((char)((Integer)btnSave.getAction().getValue(Action.MNEMONIC_KEY)).intValue());

		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(closeAction);
		
        // allow escape to close dialog
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE), "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", closeAction);
        
		init();

		setResizable(true);
	}
	
	/**
	 * initialize
	 */
	private void init() {
		setFocusable(false);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll,BorderLayout.CENTER);

		pnlButton.add(btnSave,BorderLayout.WEST);
		pnlButton.add(btnCncl,BorderLayout.EAST);
		getContentPane().add(pnlButton,BorderLayout.SOUTH);
		scroll.setRequestFocusEnabled(false);
		scroll.getHorizontalScrollBar().setRequestFocusEnabled(false);
		scroll.getVerticalScrollBar().setRequestFocusEnabled(false);

		scroll.setSize(scrollSize);
		scroll.setPreferredSize(scrollSize);
		scroll.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);		
	}
	
	/**
	 * release memory
	 */
	protected void dereference() {
		dispose();

		removeWindowListener(closeAction);
		
	    getRootPane().getActionMap().put("ESCAPE", null);
		closeAction = null;

		scroll.removeAll();
		scroll.setUI(null);
		scroll = null;

		pnlButton.removeAll();
		pnlButton.setUI(null);
		pnlButton = null;

		cancelAction.dereference();
		cancelAction = null;

		acceptAction.dereference();
		acceptAction = null;
		
		btnSave.setAction(null);
		btnSave.setUI(null);
		btnSave = null;

		btnCncl.setAction(null);
		btnCncl.setUI(null);
		btnCncl = null;

		textArea=null;

		scrollSize = null;
		longEditor = null;
		initValue=null;
		
		removeAll();
	}

	/**
	 * set view only
	 * @param b
	 */
	protected void setEditable(boolean b){
		textArea.setEditable(b);
		btnSave.getAction().setEnabled(b);
	}

	/**
	 * preloadKeyEvent
	 * @param ke
	 * 	
	 */
	protected void preloadKeyEvent(KeyEvent ke) {
	  	if(textArea.isEditable()){
	  		textArea.append(new String(new char[]{ke.getKeyChar()}));
	  		textArea.setCaretPosition(textArea.getDocument().getLength());
	  	}
	}
	/**
	 * set the initial value for the editor
	 * @param txt
	 */
	protected void setEditorValue(String txt) {
		initValue = txt;
		textArea.setDisplay(txt);
		textArea.setCaretPosition(0);
	}

	/**
	 * hide the editor popup
	 */
	protected void hidePopup() {
		super.setVisible(false);
	}

	/**
	 * show the editor popup
	 */
	protected void showPopup() {
		if(this.isShowing()){
			return;
		}
		pack();
		setLocationRelativeTo(longEditor);
		super.setVisible(true);
		textArea.requestFocusInWindow();
	}

	
	private class CloseAction extends AbstractAction implements WindowListener {
		private static final long serialVersionUID = 1L;
		public void actionPerformed(ActionEvent e) {	
			// the close operation was overridden, hide here
			if(textArea.getText().equals(initValue)){ // no changes were made
				btnCncl.doClick();
			}else{
				// msg23036 = Would you like to save changes?
				int r = com.ibm.eacm.ui.UI.showConfirmYesNoCancel(LongPopupDialog.this,Utils.getResource("msg23036"));
				if (r == com.ibm.eacm.ui.UI.YES_BUTTON){  
					btnSave.doClick();
				}else if (r == com.ibm.eacm.ui.UI.NO_BUTTON){
					btnCncl.doClick();
				}
			}
		} 
		 /**
	     * Invoked when the user attempts to close the window
	     * from the window's system menu.
	     */
		public void windowClosing(WindowEvent e) {
			actionPerformed(null);	
		}

	    /**
	     * Invoked when a window has been closed as the result
	     * of calling dispose on the window.
	     */
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}
	}
	private class CancelAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private static final String NAME="cnclme";
		CancelAction(){
			super(Utils.getResource(NAME));
			String value = Utils.getToolTip(NAME);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}

			putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(NAME)));
		}
		public void actionPerformed(ActionEvent e) {
			longEditor.cancelCellEditing(); // must tell listeners that edit was cancelled
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
		private static final String NAME="okme";
		AcceptAction(){
			super(Utils.getResource(NAME));
			String value = Utils.getToolTip(NAME);
			if (value!=null){
				putValue(Action.SHORT_DESCRIPTION, value);
			}
			putValue(Action.MNEMONIC_KEY, new Integer(Utils.getMnemonic(NAME)));
		}
		public void actionPerformed(ActionEvent e) {
			longEditor.stopCellEditing(); // must tell listeners that edit has stopped
		}
		void dereference(){
			putValue(Action.NAME, null);
			putValue(Action.SHORT_DESCRIPTION, null);
			putValue(Action.SMALL_ICON,null);
			putValue(Action.MNEMONIC_KEY,null);
		}
	}
}
