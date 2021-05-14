//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;


import java.awt.event.ActionEvent;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.cart.CartList;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.*;

/**
 * this is used for link from navigate 
 *@author Wendy Stimpson
 */
//$Log: EANLinkAction.java,v $
//Revision 1.5  2013/08/28 13:58:14  wendy
//make sure linkwizard is displayed
//
//Revision 1.4  2013/07/18 20:06:18  wendy
//throw outofrange exception from base class and catch in derived action classes
//
//Revision 1.3  2013/03/12 18:12:11  wendy
//put link in worker
//
//Revision 1.2  2013/02/11 23:20:42  wendy
//add error msgs
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class EANLinkAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANLinkAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_LINK,p);
	}

	public void actionPerformed(ActionEvent e) {
		outputStartInfo();

		EntityItem[] ei = null;
		try{
			ei = getEntityItems(false);
        } catch (OutOfRangeException range) {
        	com.ibm.eacm.ui.UI.showFYI(getNavigate(),range);
        	return;
        }
        if(!checkValidSingleInput(ei)){ // make sure this action has correct number ei selected
        	return;
        }
		if (ei != null) {
			outputDebug(ei);
			LinkActionItem link = (LinkActionItem)getEANActionItem();
			MetaLink mLink =link.getMetaLink();

			if (mLink == null) {										
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11007.1",link.getKey()));
				return;													
			}		
			EntityGroup eg1 = getParentEntityGroup(link);
			if (eg1 == null) { 
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11007.6"));
				return; 
			}
			EntityGroup eg2 = getChildEntityGroup(link);
			if(eg2==null){
				return;
			}

			//make sure one isnt already open
			LinkWizard bmd = EACM.getEACM().getLinkWizard();
			if(bmd==null){
				bmd = new LinkWizard();
				if(bmd.updateWizard(link, ei, eg1, eg2, getNavigate())){
					bmd.setVisible(true);
				}
			}else{
				if(bmd.updateWizard(link, ei, eg1, eg2, getNavigate())){
					bmd.setVisible(true); //make sure it is visible
					bmd.toFront();
				}else{
					bmd.closeWhenLinkIsDone();
					//bmd.setVisible(false);
					//bmd.dispose();
					//bmd.dereference();
				}
			}    
		}
	}

	private EntityGroup getParentEntityGroup(LinkActionItem link) {
		MetaLink mLink =link.getMetaLink();
		if (link.useParents()) {
			return getNavigate().getParentEntityGroup();
		} else if (link.isOppSelect()) { 
			return getNavigate().getEntityGroup(mLink.getEntity2Type()); 
		} else {
			return getNavigate().getEntityGroup(mLink.getEntity1Type());
		}
	}
	private EntityGroup getChildEntityGroup(LinkActionItem link) {
		MetaLink mLink =link.getMetaLink();
		if (link.useCart()) {
			if (getNavigate().isMultipleNavigate()) {
				if (link.isOppSelect()) {  
					return getOpposingParentEntityGroup(mLink);  
				} else {  
					return getOpposingChildEntityGroup(mLink);
				}  
			} else {
				if (link.isOppSelect()) {  
					return getParentEntityGroupFromCart(mLink, "msg11007.3");  
				} else {  
					return getChildEntityGroupFromCart(mLink, "msg11007.3");
				}  
			}
		} else {
			if (link.isOppSelect()) {  
				return getParentEntityGroupFromNavigate(mLink);  
			} else {  
				return getChildEntityGroupFromNavigate(mLink);
			}  
		}
	}
	private EntityGroup getOpposingParentEntityGroup(MetaLink _mLink) {
		Navigate nav2 = getNavigate().getOpposingNavigate();
		EntityGroup eg = null;
		if (nav2 == null) {
			return getParentEntityGroupFromCart(_mLink,"msg11007.3");
		}
		eg = nav2.getEntityGroup(_mLink.getEntity1Type());
		if (eg == null || eg.getEntityItemCount() == 0) {
			return getParentEntityGroupFromCart(_mLink, "msg11007.5");
		}
		return eg;
	}
	private EntityGroup getParentEntityGroupFromCart(MetaLink _mLink, String _msgCode) {
		CartList cart = getNavigate().getCart();
		String e1 = null;
		EntityGroup eg = null;
		String desc = null;
		if (cart == null) {
			return null;
		}

		e1 = _mLink.getEntity1Type();
		eg = cart.getEntityGroup(e1);
		desc = _mLink.getEntity1Description();
		if (eg == null) {
			com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource(_msgCode,
					(desc == null) ? e1 : desc));
		}
		return eg;
	}
	private EntityGroup getOpposingChildEntityGroup(MetaLink _mLink) {
		Navigate nav2 = getNavigate().getOpposingNavigate();
		EntityGroup eg = null;
		if (nav2 == null) {
			return getChildEntityGroupFromCart(_mLink,  "msg11007.3");
		}
		eg = nav2.getEntityGroup(_mLink.getEntity2Type());

		if (eg == null || eg.getEntityItemCount() == 0) { 
			return getChildEntityGroupFromCart(_mLink, "msg11007.5");
		}
		return eg;
	}
	private EntityGroup getChildEntityGroupFromCart(MetaLink _mLink, String _msgCode) {
		CartList cart = getNavigate().getCart();
		EntityGroup eg = null;
		if (cart == null) {
			return null;
		}

		eg = cart.getEntityGroup(_mLink.getEntity2Type());
		if (eg == null) {
			com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource(_msgCode,
					_mLink.getEntity2Description()));
		}
		return eg;
	}
	private EntityGroup getParentEntityGroupFromNavigate(MetaLink mLink) {
		EntityGroup eg = null;
		String type = mLink.getEntity1Type();
		if(type==null){
			//msg11007.2p = Invalid Link Action, please inform Support.\nInvalid parent type {0}
			com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11007.2p",
					mLink.getEntityType()));
		}else{
			eg = getNavigate().getEntityGroup(type);
			if (eg == null) {
				//msg11007.4p = Invalid Link Action, please inform Support.\nInvalid EntityList parent {1}.({0})
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11007.4p",
						mLink.getEntity1Type(),mLink.getEntity1Description()));
			}
		}
		return eg;
	}
	private EntityGroup getChildEntityGroupFromNavigate(MetaLink mLink) {
		EntityGroup eg = null;
		String type = mLink.getEntity2Type();
		if(type==null){
			//msg11007.2c = Invalid Link Action, please inform Support.\nInvalid child type for {0}
			com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11007.2c",
					mLink.getEntityType()));
		}else{
			eg = getNavigate().getEntityGroup(type);
			if (eg == null) {
				//msg11007.4c = Invalid Link Action, please inform Support.\nInvalid EntityList child {1}.({0})
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11007.4c",
						mLink.getEntity2Type(), mLink.getEntity2Description()));
			}
		}
		return eg;
	}
}
