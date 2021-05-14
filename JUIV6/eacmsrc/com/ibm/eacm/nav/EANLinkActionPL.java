//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.nav;



import java.awt.event.ActionEvent;


import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.preference.BehaviorPref;

import COM.ibm.eannounce.objects.*;


/**
* this is used for link from navpickframe
* @author Wendy Stimpson
*/
//$Log: EANLinkActionPL.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class EANLinkActionPL extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANLinkActionPL(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_LINK,p);
	}

	public void actionPerformed(ActionEvent e) {
		((EANLinkActionPLSet)getParentActionSet()).getNavPickFrame().disableActionsAndWait();
        EntityItem[] navItems = null;
        EntityItem[] dataItems = null;
        try {
            navItems = getNavigate().getSelectedEntityItems(false, true);
            dataItems = ((EANLinkActionPLSet)getParentActionSet()).getSelectedEntityItems();

            if (navItems == null) {
            	com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11021.0",
            			Utils.getResource("parent")));
        		((EANLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
                return;
            }
            if (dataItems == null) {
            	com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11021.0",
            			Utils.getResource("child")));
        		((EANLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
                return;
            }

            LinkActionItem lai = (LinkActionItem)getEANActionItem();

            MetaLink mLink = lai.getMetaLink();
            if (mLink == null) {
        		((EANLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
                return; //TODO need error msg
            }

            lai.setLinkType(LinkActionItem.TYPE_DEFAULT);
            lai.setCopyCount(1); //22874
            setOptions(lai, BehaviorPref.getLinkTypeKey());

            getNavigate().link(lai,dataItems);
			//moved to tab because if pickframe is closed during link it gets all messed up
        } catch (OutOfRangeException _range) {
        	com.ibm.eacm.ui.UI.showFYI(getNavigate(),_range);
    		((EANLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
        }
        navItems = null;
        dataItems = null;

	}

	private void setOptions(LinkActionItem _lai, String _s) {
		if (_s.equals("NODUPES")) {
			_lai.setOption(LinkActionItem.OPT_NODUPES);
		} else if (_s.equals("REPLACEALL")) {
			_lai.setOption(LinkActionItem.OPT_REPLACEALL);
		} else {
			_lai.setOption(LinkActionItem.OPT_DEFAULT);
		}
	}
}
