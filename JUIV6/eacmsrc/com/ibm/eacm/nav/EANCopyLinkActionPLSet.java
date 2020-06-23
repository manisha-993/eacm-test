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
 * used for the collection of Navigate picklist copylink actions, it will be used with a button and 
 * menu on the NavPickFrame
 * @author Wendy Stimpson
 */
// $Log: EANCopyLinkActionPLSet.java,v $
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class EANCopyLinkActionPLSet extends EANActionSet {
	private static final long serialVersionUID = 1L;
	private NavPickFrame navpickFrame = null;
	
	/**
	 * create action to hold all NAV picklist copylink actions - 
	 */
	public EANCopyLinkActionPLSet(NavPickFrame npf){
		super(COPYLINK_ACTION,KeyEvent.VK_C, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("copyLink.gif"));
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
				singleAction = new EANCopyLinkActionPL(eai[0],navpickFrame.getNavigate(),this);
				addAction(singleAction);
			}
			popup.add(singleAction);	 // share one action
		}else{
			for (int i=0;i<eai.length;++i) { // must build this for the menu
				EANAction act = getEANAction(eai[i].getActionItemKey());
				if(act==null){
					act = new EANCopyLinkActionPL(eai[i],navpickFrame.getNavigate(),this);		
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
