//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.cart;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
/*********************************************************************
 * This is used to add selected items to the workfolder
 * it is enabled/disabled by the cart when the cartable has selected data
 * @author Wendy Stimpson
 */
//$Log: AddSelected2CartAction.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class AddSelected2CartAction extends EACMAction implements PropertyChangeListener 
{
	private static final long serialVersionUID = 1L;
	private CartList cart = null;
	
	/**
	 * @param ct
	 */
	public AddSelected2CartAction(CartList ct) {
        super(ADD2CART_ACTION,KeyEvent.VK_A, Event.CTRL_MASK + Event.SHIFT_MASK);
        putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("wg_plus.gif"));
       
		cart = ct;
	
		//must disable it while loading cart
		cart.addPropertyChangeListener(ADD2CART_ACTION,this);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(cart !=null){
			ok = cart.cartableHasSelection();
		}
		return ok; 
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//popup or menu call this to add selected item to cart	
		cart.addSelectedToCart();
	} 	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		cart.removePropertyChangeListener(ADD2CART_ACTION,this);
		
		super.dereference();
		cart = null;
	}
	
	/* (non-Javadoc)
	 * called by the cart
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		setEnabled((Boolean)event.getNewValue());
	}
}
