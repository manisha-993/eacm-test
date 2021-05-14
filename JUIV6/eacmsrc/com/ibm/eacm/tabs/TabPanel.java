//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.tabs;

import java.util.*;

import javax.swing.*;

import COM.ibm.eannounce.objects.EANActionItem;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Utils;

/**
 * base panel for all tabs
 * @author Wendy Stimpson 
 */
//$Log: TabPanel.java,v $
//Revision 1.2  2013/07/18 18:45:25  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:23  wendy
//Initial code
//
public abstract class TabPanel extends DataActionPanel
{
	private static final long serialVersionUID = 1L;
	private TabPanel parTab = null; 
	private Vector<TabPanel> childTabVct = new Vector<TabPanel>(); 
    private Profile parentProfile = null; 

    /**
     * release memory
     */
    public void dereference(){
      	super.dereference();

    	parentProfile = null;
		
		if (parTab != null) {
			parTab.removeChildTab(this);
			parTab = null;
		}
		if (childTabVct!= null){
			while (!childTabVct.isEmpty()) {
				TabPanel tab = (TabPanel) childTabVct.remove(0);
				tab.setParentTab(getParentTab());
			}
			childTabVct = null;
		}
    }

    /**
     * change the read language to this nlsitem
     * @param nls
     */
    public void setReadLanguage(NLSItem nls){
    	Profile prof = getProfile();
    	if (prof != null) {
    		prof.setReadLanguage(nls);
    		EACM.getEACM().setStatus(prof); 
    		refresh();
    	}	
    }
	/**
	 * create all of the actions, they are shared between toolbar and menu
	 */
	public void createActions() {}
	
    /**
     * get the title for the tab
     * @return
     */
    public abstract String getTableTitle();
 
    
    /**
     * called when this tab is selected
     */
    public void select(){}
    /**
     * called when user has selected a different tab and this one is no longer selected
     */
    public void deselect() {}
    
    /**
     * popupCart
     * implemented by navcontroller and wusedactiontab
     */
    public void popupCart() {}
    
    /**
     * does this tab contain all of this data
     * prevent user from opening multiple tabs with the same action and data so lock and updates remain in sync
     * @param prof
     * @param ei
     * @param eai
     * @return
     */
    public boolean tabExistsWithAll(Profile prof, EntityItem[] ei, EANActionItem eai){ return false;}
    /**
     * does this tab contain some of this data
     * prevent user from opening multiple tabs with the same action and data so lock and updates remain in sync
     * @param prof
     * @param ei
     * @param eai
     * @return
     */
    public boolean tabExistsWithSome(Profile prof, EntityItem[] ei, EANActionItem eai){ return false;}
    
    /**
     * @param prof
     */
    public void setParentProfile(Profile prof) {
        parentProfile = prof;
    }

    /**
     * getParentProfile
     *
     * @return
     */
    public Profile getParentProfile() {
        if (parentProfile != null) { 
            return parentProfile;
        } 
        return getProfile(); 
    }

    /**
     * getParentTab
     * @return
     */
    public TabPanel getParentTab() {
        return parTab;
    }
    /**
     * setParentTab
     * @param tab
     */
    public void setParentTab(TabPanel tab) {
        parTab = tab;
        if (parTab != null) {
            parTab.addChildTab(this);
        }
    }
    /**
     * addChildTab
     * @param tab
     */
    private void addChildTab(TabPanel tab) {
        childTabVct.add(tab);
    }
    /**
     * removeChildTab
     * @param tab
     * @return
     */
    public TabPanel removeChildTab(TabPanel tab) {
        if (childTabVct.remove(tab)) {
            return tab;
        }
        return null;
    }
    /**
     * getTabToolTipText
     * @return
     */
    public abstract String getTabToolTipText();
    /**
     * getTabTitle
     * @return
     */
    public abstract String getTabTitle();
    /**
     * getTabMenuTitle
     * @return
     */
    public abstract String getTabMenuTitle();
    /**
     * get the resource key for the tab icon
     */
	protected abstract String getTabIconKey();
	/**
	 * get the icon for the tab
	 * @return
	 */
	public Icon getTabIcon(){
		return Utils.getImageIcon(EACMProperties.getProperty(getTabIconKey()));
	}
}
