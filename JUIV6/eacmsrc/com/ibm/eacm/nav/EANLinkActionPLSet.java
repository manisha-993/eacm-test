//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.Event;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import COM.ibm.eannounce.objects.EntityItem;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;


/*******
 * 
 * used for the collection of Navigate picklist link actions, it will be used with a button and 
 * menu on the NavPickFrame
 * @author Wendy Stimpson
 */
// $Log: EANLinkActionPLSet.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public class EANLinkActionPLSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private NavPickFrame navpickFrame = null;
	
	/**
	 * create action to hold all NAV picklist link actions - 
	 */
	public EANLinkActionPLSet(NavPickFrame npf){
		super(NAVLINK_ACTIONSET,KeyEvent.VK_L, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("link.gif"));
	    navpickFrame = npf;
	}
	
	protected NavPickFrame getNavPickFrame() {
		return navpickFrame;
	}
	protected EntityItem[] getSelectedEntityItems() throws OutOfRangeException {
		return navpickFrame.getSelectedEntityItems();
	}
	protected void addActions() {
		if(eai.length==1){
			singleAction = getEANAction(eai[0].getActionItemKey());
			if (singleAction==null){
				singleAction = new EANLinkActionPL(eai[0],navpickFrame.getNavigate(),this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANLinkActionPL(eai[i],navpickFrame.getNavigate(),this);		
					addAction(act);
				}

				popup.add(act);	
			}
		}
	}
	
	public void dereference(){
		super.dereference();
		navpickFrame = null;
	}

    public void setEnabled(boolean newValue) {
    	super.setEnabled(newValue && shouldEnable());
    }
	@Override
	protected boolean shouldEnable() {
		boolean enable = eai!=null && eai.length>0;
		return enable;
	}
}
