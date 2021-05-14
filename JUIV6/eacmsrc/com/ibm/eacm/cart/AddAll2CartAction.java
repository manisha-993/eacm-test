//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.cart;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
/*********************************************************************
 * This is used to add all items to the workfolder
 * @author Wendy Stimpson
 */
//$Log: AddAll2CartAction.java,v $
//Revision 1.1  2012/09/27 19:39:18  wendy
//Initial code
//
public class AddAll2CartAction extends EACMAction implements PropertyChangeListener 
{
	private CartList cart = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param ct
	 */
	public AddAll2CartAction(CartList ct) {
        super(ADDALL2CART_ACTION);
        putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("wg_plusa.gif"));
        
		cart = ct;
		//must disable it while loading cart
		cart.addPropertyChangeListener(ADDALL2CART_ACTION,this);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(cart !=null){
			ok = cart.hasAnyToCart();
		}
		return ok; 
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		cart.addAllToCart();
	} 
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		cart.removePropertyChangeListener(ADDALL2CART_ACTION,this);
		
		super.dereference();
		cart = null;
	}
	
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		setEnabled((Boolean)event.getNewValue());
	} 
}