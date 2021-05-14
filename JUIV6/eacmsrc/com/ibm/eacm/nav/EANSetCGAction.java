//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;


import java.awt.event.ActionEvent;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.objects.OutOfRangeException;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * this is used for set changegroup actions
 * @author Wendy Stimpson
 */
//$Log: EANSetCGAction.java,v $
//Revision 1.2  2013/07/18 20:06:18  wendy
//throw outofrange exception from base class and catch in derived action classes
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class EANSetCGAction extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANSetCGAction(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_CHANGE_GROUP,p);
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

			getNavigate().disableActionsAndWait(); //disable all other actions and also set wait cursor

			Profile[] pArray = EACM.getEACM().getActiveProfiles();
			Profile pActive = getNavigate().getProfile();
			((CGActionItem)getEANActionItem()).setChangeGroup(ei[0], pArray, pActive);
			EACM.getEACM().setActiveProfile(pActive);

			getNavigate().getAction(CGRP_ACTION).setEnabled(true);

			getNavigate().getNavController().setChangeGroupTagText(ei[0].toString());	//cgLabel

			getNavigate().enableActionsAndRestore();
		}
	}
}
