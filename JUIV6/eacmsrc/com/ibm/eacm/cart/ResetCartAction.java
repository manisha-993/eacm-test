//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.cart;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
/*********************************************************************
 * This is used to reset the workfolder
 * @author Wendy Stimpson
 */
//$Log: ResetCartAction.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class ResetCartAction extends EACMAction implements PropertyChangeListener 
{
	private CartList cart = null;
	private static final long serialVersionUID = 1L;
	private Window parent = null;
	
	/**
	 * @param ct
	 * @param win
	 */
	public ResetCartAction(CartList ct, Window win) {
		super(RESETCART_ACTION);
		setEnabled(false);
		cart = ct;
		parent = win;
		cart.addPropertyChangeListener(RESETCART_ACTION,this);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	    int reply =  com.ibm.eacm.ui.UI.showConfirmOkCancel(parent, Utils.getResource("msg24016"));
		//popup or menu call this to remove all items from cart
	    if (reply == OK_BUTTON){
        	cart.clearAll();
        }
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		cart.removePropertyChangeListener(RESETCART_ACTION,this);
		super.dereference();
		cart = null;
	}
	
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		setEnabled(((Boolean)event.getNewValue()));
	} 	
}