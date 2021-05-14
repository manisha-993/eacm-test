//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;


import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EANAction;
import com.ibm.eacm.actions.EANActionSet;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.NavController;

import COM.ibm.eannounce.objects.CGActionItem;
import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.WorkflowActionItem;


/**
 * this is used for one Navigate EANActionItem
 * @author Wendy Stimpson
 */
// $Log: EANNavActionBase.java,v $
// Revision 1.2  2013/07/18 20:06:19  wendy
// throw outofrange exception from base class and catch in derived action classes
//
// Revision 1.1  2012/09/27 19:39:14  wendy
// Initial code
//
public abstract class EANNavActionBase extends EANAction {
	private static final long serialVersionUID = 1L;
	private Navigate parent=null;

	public void dereference(){
		super.dereference();
		parent = null;
	}

	protected EANNavActionBase(EANActionItem act,Navigate n,String purpose,EANActionSet p) {
		super(act,purpose,p);
		parent = n;
	}

	/**
	 * get entity items for the action, no selection is allowed for some actions
	 * @param nullEIAllowed
	 * @return
	 * @throws OutOfRangeException
	 */
	protected EntityItem[] getEntityItems(boolean nullEIAllowed) throws OutOfRangeException {
	    EntityItem[] ei = parent.getSelectedEntityItems(false, !(getEANActionItem() instanceof WorkflowActionItem));

        if(!nullEIAllowed && (ei==null ||ei.length==0)){
			// msg11006 = Selected Action could not be completed because no Entity(s) were selected.
			com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11006"));
			ei = null;
        }

        return ei;
	}

	protected boolean checkValidSingleInput(EntityItem []ei){
        if (getEANActionItem().isSingleInput()) {
            if (ei == null || ei.length != 1) {
            	//msg30a1 = The Selected Action is Single Select Only.\nPlease Select Only a Single Entity.
            	com.ibm.eacm.ui.UI.showErrorMessage(parent,Utils.getResource("msg30a1"));
                return false;
            }
        }
        return true;
	}

	protected EntityItem[] getParentEntityItems() {
        EntityGroup tmpParent = parent.getParentEntityGroup();
        if (tmpParent != null) {
            return tmpParent.getEntityItemsAsArray();
        }
        return null;
    }

	/**
	 * why do many actions have to call this?
	 * @param _ei
	 */
	protected void clearAction(EntityItem[] _ei) {
		if (getEANActionItem().isClearTargetChangeGroupEnabled()) {
			if (_ei != null) {
				if (CGActionItem.clearTargetChangeGroupForAll(EACM.getEACM().getActiveProfiles(),_ei)) {
					getNavController().resetChangeGroup();
					//msg3018.0 = Change Group {0} has been deactivated.
					com.ibm.eacm.ui.UI.showFYI(parent, Utils.getResource("msg3018.0", getNavController().getChangeGroupTagText()));
				}
			}
		}
	}

	protected boolean validEntityItems(EntityItem[] _ei) {
        if (_ei == null || _ei.length == 0) {
            return false;
        }

        for (int i = 0; i < _ei.length; ++i) {
            if (_ei[i] == null) {
                return false;
            }
        }
        return true;
    }


	protected Navigate getNavigate(){
		return parent;
	}
	protected NavController getNavController(){
		return parent.getNavController();
	}
}
