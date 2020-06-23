//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.actions.EANActionSet;
import com.ibm.eacm.ui.ScrollPopupMenu;

/*******
 *
 * used for the collection of EANActions, like all Navigate actions on a dropdown menu
 * @author Wendy Stimpson
 */
//$Log: EANActionMenu.java,v $
//Revision 1.2  2013/11/06 21:40:07  wendy
//register keystroke for menu, accelerators are not directly supported
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class EANActionMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	private JPopupMenu wgpopup=null;
	/**
	 * need to support large numbers of wg and roles, if the menu is too long, it does not scroll
	 * seems like a java6 bug
	 * @param str
	 * @param visiblecnt
	 */
	public EANActionMenu(String str, int visiblecnt) {
        super(str);
        wgpopup = new ScrollPopupMenu();
        ((ScrollPopupMenu)wgpopup).setMaxVisibleItems(visiblecnt);
	}
	public JMenuItem add(JMenuItem menuItem) {
		return getPopupMenu().add(menuItem);
	}

	public EANActionMenu(EANActionSet actset) {
        super(actset);
        registerEACMAction(actset);
	}
	
    /**
     * must add JComponent bindings or action is not invoked
     * this is needed to register the accelerators for this action
     * @param actset
     * @param keyCode
     * @param modifiers
     */
    private void registerEACMAction(EACMAction actset){
        KeyStroke ks = (KeyStroke)actset.getValue(Action.ACCELERATOR_KEY);
        if(ks!=null){
        	setMnemonic(ks.getKeyCode());
            getInputMap().put(ks, actset.getActionKey());
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ks, actset.getActionKey());
    	    getActionMap().put(actset.getActionKey(), actset);
        }
    }
    public void dereference(){
    	if(getAction() !=null){
    		unregisterEACMAction((EACMAction)getAction());
    	}
    }
    /**
     * call this when dereferencing components that registered copy or paste actions
     * @param actset
     */
    private void unregisterEACMAction(EACMAction actset){
    	KeyStroke ks = (KeyStroke)actset.getValue(Action.ACCELERATOR_KEY);
    	if(ks!=null){
    		getInputMap().remove(ks);
    		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).remove(ks);
    		getActionMap().remove(actset.getActionKey());
    	}
    }

	private boolean wasDereferenced(){
		return wgpopup ==null && getAction()==null;
	}
    /**
     *
     * @see javax.swing.JMenuItem#getAccelerator()
     */
    public KeyStroke getAccelerator() {
    	if (getAction()==null){
    		return null;
    	}
        return (KeyStroke)getAction().getValue(Action.ACCELERATOR_KEY);
    }
	public JPopupMenu getPopupMenu() {
	    JPopupMenu popup = wgpopup;
	    if(getAction()!=null){
	    	popup =((EANActionSet)getAction()).getPopup();
	    }
		popup.setInvoker(this); // must do this or button steals ownership
		if (popupListener==null){
			popupListener = createWinListener(popup);
			popup.addPopupMenuListener(new PopupMenuListener() {
				public void popupMenuWillBecomeVisible(PopupMenuEvent pme) {
				}
				public void popupMenuWillBecomeInvisible(PopupMenuEvent pme) {
				}
				public void popupMenuCanceled(PopupMenuEvent pme) {
					fireMenuCanceled();
				}
			});
		}
		return popup;
	}

	public int getItemCount() {
		if (wasDereferenced()){ // popup was dereferenced
			return 0;
		}
		return ((ScrollPopupMenu) getPopupMenu()).getMenuItemCount();
	}
	public JMenuItem getItem(int i) {
		return (JMenuItem) ((ScrollPopupMenu) getPopupMenu()).getMenuItem(i);
	}
	public boolean isPopupMenuVisible() {
		return getPopupMenu().isVisible();
	}
    public void remove(int pos) {
    	if (!wasDereferenced()){ // popup was dereferenced
    		((ScrollPopupMenu) getPopupMenu()).remove(pos);
		}
    }
    public void remove(Component c) {
     	if (!wasDereferenced()){ // popup was dereferenced
     		((ScrollPopupMenu) getPopupMenu()).remove(c);
     	}
    }
    public void remove(JMenuItem item) {
    	if (!wasDereferenced()){ // popup was dereferenced
    		((ScrollPopupMenu) getPopupMenu()).remove(item);
    	}
    }
    public void removeAll() {
    	if (!wasDereferenced()){ // popup was dereferenced
    		((ScrollPopupMenu) getPopupMenu()).removeAll();
    	}
    	if(this.wgpopup!=null){
    		 ((ScrollPopupMenu)wgpopup).dereference();
    		 wgpopup =null;
    	}
    }
}
