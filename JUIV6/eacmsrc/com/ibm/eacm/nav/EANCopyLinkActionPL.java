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
 * this is used for copylink from navpickframe
 * @author Wendy Stimpson
 */
//$Log: EANCopyLinkActionPL.java,v $
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class EANCopyLinkActionPL extends EANNavActionBase {
	private static final long serialVersionUID = 1L;

	public EANCopyLinkActionPL(EANActionItem act,Navigate n,EANActionSet p) {
		super(act,n,ACTION_PURPOSE_LINK,p);
	}

	public void actionPerformed(ActionEvent e) {
		((EANCopyLinkActionPLSet)getParentActionSet()).getNavPickFrame().disableActionsAndWait();
		EntityItem[] navItems = null;
		EntityItem[] dataItems = null;
		try {

			navItems = getNavigate().getSelectedEntityItems(false, true);
			dataItems = ((EANCopyLinkActionPLSet)getParentActionSet()).getSelectedEntityItems();

			if (navItems == null) {
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11021.0",
						Utils.getResource("parent")));
				((EANCopyLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
				return;
			}
			if (dataItems == null) {
				com.ibm.eacm.ui.UI.showErrorMessage(getNavigate(),Utils.getResource("msg11021.0",
						Utils.getResource("child")));
				((EANCopyLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
				return;
			}

			LinkActionItem lai = (LinkActionItem)getEANActionItem();

			MetaLink mLink = lai.getMetaLink();
			if (mLink == null) {
				((EANCopyLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
				return; //TODO need error msg?
			}
			int copies = com.ibm.eacm.ui.UI.showIntSpinner(null,Utils.getResource("msg3013"),1, 1, 99, 1);
			if(copies<=0){
				return;
			}
			lai.setLinkType(LinkActionItem.TYPE_COPY);
			lai.setCopyCount(copies);
			setOptions(lai, BehaviorPref.getLinkTypeKey());

			getNavigate().link(lai,dataItems);
			//moved to tab because if pickframe is closed during link it gets all messed up
		} catch (OutOfRangeException _range) {
			com.ibm.eacm.ui.UI.showFYI(getNavigate(),_range);
			((EANCopyLinkActionPLSet)getParentActionSet()).getNavPickFrame().enableActionsAndRestore();
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
