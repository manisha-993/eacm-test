//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.KeyStroke;

import com.ibm.eacm.actions.EACMAction;

import COM.ibm.eannounce.objects.MetaFlag;


/**
 * this class is used to display a multiflag editor in a form, it also is used to render the value too
 * @author Wendy Stimpson
 */
//$Log: MultiPopupPanel.java,v $
//Revision 1.1  2012/09/27 19:39:21  wendy
//Initial code
//
public class MultiPopupPanel extends JPanel implements KeyListener,MultiPopup {
	private static final long serialVersionUID = 1L;
	private MultiFlagCBList list = null;
	private JScrollPane scroll = null;
	private JPanel pnlButton = null;
    private JButton btnSave = null;
    private JButton btnCncl = null;
	
    /**
     * used with form
     * @param me
     * @param cnclAct
     * @param acceptAct
     */
    public MultiPopupPanel(MultiEditor me, Action cnclAct, Action acceptAct) {
    	super(new BorderLayout());
          
    	list = new MultiFlagCBList(me,this);
    	scroll = new JScrollPane(list);

    	btnCncl = new JButton(cnclAct);
		btnSave = new JButton(acceptAct);
	    init();
	    
		list.addKeyListener(this);
	}
    /**
     * must override JComponent bindings or EACM copy and paste action are not invoked
     * with standard keybindings
     * @param act
     * @param keystroke
     */
    public void registerEACMAction(EACMAction act, KeyStroke keystroke){
    	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystroke, act.getActionKey());
    	getActionMap().put(act.getActionKey(), act);	
    	list.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keystroke, act.getActionKey());
     	list.getInputMap().put(keystroke, act.getActionKey());
    	list.getActionMap().put(act.getActionKey(), act);	
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param act
     * @param keystroke
     */
    public void unregisterEACMAction(EACMAction act,KeyStroke keystroke){
      	getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keystroke);
    	getActionMap().remove(act.getActionKey());
      	list.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(keystroke);
      	list.getInputMap().remove(keystroke);
    	list.getActionMap().remove(act.getActionKey());
    }
    /**
     * does the 'editor' have focus?
     * @return
     */
    public boolean editorIsActive(){
    	return list.hasFocus();
    }
    
    /* (non-Javadoc)
     * @see javax.swing.JComponent#requestFocusInWindow()
     */
    public boolean requestFocusInWindow() {
    	if(list != null){
    		return list.requestFocusInWindow();
    	}
        return super.requestFocusInWindow();
    }

	/**
	 * initialize the panel
	 */
	private void init() {
		setFocusable(false);

		add(scroll,BorderLayout.CENTER);
		
		btnSave.setIcon(null); // dont use icon from action
		btnCncl.setIcon(null); // dont use icon from action

		pnlButton = new JPanel(new GridLayout(1, 2, 5, 5));
		pnlButton.add(btnSave);
		pnlButton.add(btnCncl);
		//form has buttons on the top
		add(pnlButton,BorderLayout.NORTH);

		scroll.setRequestFocusEnabled(false);
		scroll.getHorizontalScrollBar().setRequestFocusEnabled(false);
		scroll.getVerticalScrollBar().setRequestFocusEnabled(false);

		scroll.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);		
	}	

    /* (non-Javadoc)
     * this is needed so a double click on list or buttons when disabled can get the lock
     * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
     */
    public synchronized void addMouseListener(MouseListener l) {
    	super.addMouseListener(l);
    	list.addMouseListener(l);
    	btnSave.addMouseListener(l);
    	btnCncl.addMouseListener(l);
    }
    /* (non-Javadoc)
     * @see java.awt.Component#removeMouseListener(java.awt.event.MouseListener)
     */
    public synchronized void removeMouseListener(MouseListener l) {
    	super.removeMouseListener(l);
    	list.removeMouseListener(l);
       	btnSave.removeMouseListener(l);
    	btnCncl.removeMouseListener(l);
    }

    /* (non-Javadoc)
     * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
     */
    public synchronized void addFocusListener(FocusListener l) {
    	list.addFocusListener(l);
    	btnSave.addFocusListener(l);
    	btnCncl.addFocusListener(l);
    }
    /* (non-Javadoc)
     * @see java.awt.Component#removeFocusListener(java.awt.event.FocusListener)
     */
    public synchronized void removeFocusListener(FocusListener l) {
    	list.removeFocusListener(l);
    	btnSave.removeFocusListener(l);
    	btnCncl.removeFocusListener(l);
    }
    /* (non-Javadoc)
     * @see javax.swing.JComponent#setEnabled(boolean)
     */
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);
    	btnSave.getAction().setEnabled(enabled);
    	btnCncl.getAction().setEnabled(enabled);
    	list.setEnabled(enabled);
    }

    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#loadMetaFlags(COM.ibm.eannounce.objects.MetaFlag[])
     */
    public void loadMetaFlags(MetaFlag[] mf) {
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
		
		removeAll();
		setUI(null);
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

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {}

    /* (non-Javadoc)
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
    	list.keyTyped(e);
    }

    // implemented the interface for the other methods, these do nothing
	public void hidePopup() {}

	public void processPopupKey(KeyEvent _ke) {}

	public void showPopup(Component _c) {}
    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#setLabelText(java.lang.String)
     */
    public void setLabelText(String _s){}
    /* (non-Javadoc)
     * @see com.ibm.eacm.editor.MultiPopup#getLabelText()
     */
    public String getLabelText(){
    	return "";
    }
    public void setDescription(String desc) {}
}
