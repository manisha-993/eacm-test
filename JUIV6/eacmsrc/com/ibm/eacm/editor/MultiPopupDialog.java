//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Window;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import COM.ibm.eannounce.objects.MetaFlag;
import com.ibm.eacm.objects.EACMGlobals;
import com.ibm.eacm.objects.Utils;
/**
 * this class is used to display a multiflag editor in a dialog
 * @author Wendy Stimpson
 */
//$Log: MultiPopupDialog.java,v $
//Revision 1.2  2013/07/19 17:51:39  wendy
//pass window parent into mf cell editor
//
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class MultiPopupDialog extends JDialog implements MultiPopup,  WindowListener 
,KeyListener, EACMGlobals {
	private static final long serialVersionUID = 1L;
	
	private MultiFlagCBList list = null;
	private JScrollPane scroll = null;
	private Dimension scrollSize = new Dimension(300,200);
	private JPanel pnlButton = new JPanel(new BorderLayout());
    private JButton btnSave = null;
    private JButton btnCncl = null;
    private boolean mfSet[];
	
	private JLabel descriptionLbl = new JLabel("flag description here");
	private JLabel typeAheadLbl = new JLabel("");

	private JPanel lblPanel = new JPanel(new BorderLayout());

    /**
     * @param parent
     * @param me
     * @param cnclAct
     * @param acceptAct
     */
    public MultiPopupDialog(Window parent, MultiEditor me, Action cnclAct, Action acceptAct) {//DEFAULT_MODALITY_TYPE
    	super(parent, "MultipleFlag Editor",JDialog.ModalityType.APPLICATION_MODAL);
        
        lblPanel.add(descriptionLbl,BorderLayout.NORTH);
        lblPanel.add(typeAheadLbl,BorderLayout.SOUTH); 
		typeAheadLbl.setVisible(Utils.isArmed(ENHANCED_FLAG_EDIT));
		      
    	list = new MultiFlagCBList(me,this);
    	scroll = new JScrollPane(list);
		scroll.setColumnHeaderView(lblPanel);
    	
    	btnCncl = new JButton(cnclAct);
		btnSave = new JButton(acceptAct);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
	    init();
	    
		list.addKeyListener(this);
	}
 
	private void init() {
		setFocusable(false);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll,BorderLayout.CENTER);
		
		btnSave.setIcon(null); // dont use icon from action
		btnCncl.setIcon(null); // dont use icon from action
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

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#setDescription(java.lang.String)
     */
    public void setDescription(String desc) {
		descriptionLbl.setText(desc);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#loadMetaFlags(COM.ibm.eannounce.objects.MetaFlag[])
     */
    public void loadMetaFlags(MetaFlag[] mf) {
    	mfSet = new boolean[mf.length];
		for (int i=0;i<mf.length;++i) {
			mfSet[i] = mf[i].isSelected();
		}
    	
		list.loadFlags(mf);
	}
    
    /* (non-Javadoc)
     * return the flags, selection is held in the flag
     * to the editor so that it can update the RowSelectableTable
     * @see com.ibm.eacm.editor.MultiPopup#getMetaFlags()
     */
    public MetaFlag[] getMetaFlags() {
    	return list.getMetaFlags();
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#dereference()
     */
    public void dereference() {
    	dispose();
    	
		removeWindowListener(this);
	
		mfSet = null;
		
		scroll.removeAll();
		scroll.setUI(null);
		scroll = null;

		pnlButton.removeAll();
		pnlButton.setUI(null);
		pnlButton = null;
		
		btnSave.setAction(null);
		btnSave.setUI(null);
		btnSave = null;
		
		btnCncl.setAction(null);
		btnCncl.setUI(null);
		btnCncl = null;
		
		list.removeKeyListener(this);
		list.dereference();
		list=null;
		
		scrollSize = null;
		descriptionLbl.removeAll();
		descriptionLbl.setUI(null);
		descriptionLbl = null;
		
		typeAheadLbl.removeAll();
		typeAheadLbl.setUI(null);
		typeAheadLbl = null;
		
		lblPanel.removeAll();
		lblPanel.setUI(null);
		lblPanel = null;
		
		removeAll();
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#hidePopup()
     */
    public void hidePopup() {
		super.setVisible(false);
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#showPopup(java.awt.Component)
     */
    public void showPopup(Component _c) {
    	if(this.isShowing()){
    		return;
    	}
		pack();
		setLocationRelativeTo(_c);
		super.setVisible(true);
		list.requestFocus();
	}

    public void windowActivated(WindowEvent _we) {}
    public void windowClosed(WindowEvent _we) {}
    public void windowDeactivated(WindowEvent _we) {}
    public void windowDeiconified(WindowEvent _we) {}
    public void windowIconified(WindowEvent _we) {}
    public void windowOpened(WindowEvent _we) {}

	/**
	 * Invoked when the user attempts to close the window
	 * from the window's system menu.
	 */
    public void windowClosing(WindowEvent _we) {
    	boolean chgsMade = false;
    	MetaFlag mf[] = getMetaFlags();
		for (int i=0;i<mfSet.length;++i) {
			if(mfSet[i] != mf[i].isSelected()){
				chgsMade = true;
				break;
			}
		}
    	
		int r = com.ibm.eacm.ui.UI.NO_BUTTON;
		if(chgsMade){
    	// msg23035 = Would you like to save flag changes?
			r = com.ibm.eacm.ui.UI.showConfirmYesNoCancel(this,Utils.getResource("msg23035"));
		}
		if (r == com.ibm.eacm.ui.UI.YES_BUTTON){  
	 		btnSave.doClick();
	 	}else if (r == com.ibm.eacm.ui.UI.NO_BUTTON){
	 		btnCncl.doClick();
	 	}
	}

    /* (non-Javadoc)
     * called when user types a character to open the editor
     * @see com.ibm.eacm.editor.MultiPopup#processPopupKey(java.awt.event.KeyEvent)
     */
    public void processPopupKey(KeyEvent _ke) {
		list.scrollToCharacter(_ke.getKeyChar());
	}

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#setLabelText(java.lang.String)
     */
    public void setLabelText(String _s){
    	typeAheadLbl.setText(_s);
    }
    public String getLabelText(){
    	return typeAheadLbl.getText();
    }
    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent _ke) {
    	int keyCode = _ke.getKeyCode();
    	if (keyCode == KeyEvent.VK_ENTER) {
    		btnSave.doClick();
    		return;
    	} 
    	if (keyCode == KeyEvent.VK_ESCAPE) {
    		btnCncl.doClick();
    		return;
    	}
    	list.keyPressed(_ke);
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {
    	list.keyTyped(e);
    }

}
