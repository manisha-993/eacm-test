//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;


import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;

import com.ibm.eacm.objects.Utils;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaEntity;
import COM.ibm.eannounce.objects.EditActionItem;
import COM.ibm.eannounce.objects.EntityItem;

/**
 * this is used for one EANActionItem
 * @author Wendy Stimpson
 */
// $Log: EANAction.java,v $
// Revision 1.4  2013/05/07 18:25:27  wendy
// another perf update for large amt of data
//
// Revision 1.3  2013/05/01 18:35:15  wendy
// perf updates for large amt of data
//
// Revision 1.2  2013/02/05 18:23:21  wendy
// throw/handle exception if ro is null
//
// Revision 1.1  2012/09/27 19:39:16  wendy
// Initial code
//
public abstract class EANAction extends EACMAction {
	private static final long serialVersionUID = 1L;
	private EANActionItem actionItem = null;
	private EANActionSet actionSetParent = null;
	private String purpose = null;

	public EANAction(EANActionItem act, String p, EANActionSet parent) {
		String name = act.toString();
		if (Utils.isTestMode()) {
			name+= " (" + act.getActionItemKey() + ")";
		} 
		putValue(Action.NAME, name);
		actionItem = act;
		actionSetParent = parent;
		purpose=p;
		setActionKey(actionItem.getActionItemKey());
	}
	
	
	public String getPurpose() {return purpose;}
	
	protected EANActionSet getParentActionSet() { return actionSetParent;}
	
	public boolean isSetEnabled(){
		return getParentActionSet().isEnabled();
	}
	
	protected void outputStartInfo(){
		Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"****    actionPerformed(" + actionItem.getKey() + " on " + actionItem.getDataSourceAsString() + ")    ****");
		if (actionItem.hasAdditional()) {
			Logger.getLogger(APP_PKG_NAME).log(Level.FINER,"****    performAdditional(" + actionItem.getAdditional() + ")    ****");
		}
	}
	protected void outputDebug(EntityItem[] eia){
		if (Utils.isDebug()) {
			Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"performing action (" + actionItem.getKey() + ") on...");
			Logger.getLogger(APP_PKG_NAME).log(Level.FINE," using root: " + actionItem.useRootEI());
			if(eia!=null){
				for (int i = 0; i < eia.length; ++i) {
					if (eia[i] != null) {
						Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"  ei[" + i + "]: " + eia[i].getKey());
						if (eia[i].hasUpLinks()) {
							int xx = eia[i].getUpLinkCount();
							for (int x = 0; x < xx; ++x) {
								Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"    uplink(" + x + " of " + xx + "): " + eia[i].getUpLink(x).getKey());
							}
						}
						if (eia[i].hasDownLinks()) {
							int xx = eia[i].getDownLinkCount();
							for (int x = 0; x < xx; ++x) {
								Logger.getLogger(APP_PKG_NAME).log(Level.FINE,"    downlink(" + x + " of " + xx + "): " + eia[i].getDownLink(x).getKey());
							}
						}
					}
				}
			}
		}else{
			//output brief info
			Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"performing action (" + actionItem.getKey() + ") on...");
			if(eia!=null){
				for (int i = 0; i < eia.length; ++i) {
					if (eia[i] != null) {
						Logger.getLogger(APP_PKG_NAME).log(Level.INFO,"  ei[" + i + "]: " + eia[i].getKey());
					}
				}
			}
		}
	}
	 /**
     * find the entities that will be used for edit, they will be passed to the editcontroller
     * so they can be updated with the changes - ie.. find MODEL when entity passed in is WGMODELA
     *
     * @param  ai   
     * @param  aei  
     * @return       
     */
    protected static EntityItem[] findEditItems(EditActionItem ai, EntityItem[] aei) {

        EntityItem[] aeiEdit = null;

        // Modify if to see if it is an entityonly in hiding
        ai.modifyActionItem(aei[0]);
        
        EANList el = new EANList(EANMetaEntity.LIST_SIZE);

        for (int ii = 0; ii < aei.length; ii++) {

        	// OK if we are dealing with Entities only.. lets ensure that what was
        	// passed matches the Edit Target Entity
        	if (ai.isEntityOnly()) {
        		// We may have to shift right to find the proper entitytype
        		if (ai.getTargetEntity().equals(aei[ii].getEntityType())) {
        			el.put(aei[ii]);
        		} else {
        			EntityItem ei = (EntityItem) aei[ii].getDownLink(0);
        			if (ei != null) {
        				if (ai.getTargetEntity().equals(ei.getEntityType())) {
        					el.put( ei);
        				}
        			}
        		}
        	} else {

        		// We should be dealing w/ a relator .. so lets take a risk
        		// Not so fast there babaloui
        		EntityItem eiRelator = aei[ii];
        		EntityItem eiE1 = (EntityItem) aei[ii].getUpLink(0);
        		EntityItem eiE2 = (EntityItem) aei[ii].getDownLink(0);
        		// If everything is fine.. we have a good relator
        		if (eiE1 != null && eiE2 != null && eiRelator != null) {
        			el.put(eiRelator);
        		}
        	}
        }

        aeiEdit = new EntityItem[el.size()];
        el.copyTo(aeiEdit);
        
        // release memory
        el.clear();
        el = null;
    
        return aeiEdit;
    }
    
	public EANActionItem getEANActionItem(){
		return actionItem;
	}
	public void dereference(){
		super.dereference();
		actionItem = null;
		actionSetParent = null;
	}
}
