//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.wused;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;

import COM.ibm.eannounce.objects.*;
import com.ibm.eacm.actions.*;

import com.ibm.eacm.objects.OutOfRangeException;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.ui.PickFrame;



/******************************************************************************
* This is used to display the link picklist for whereused
* @author Wendy Stimpson
*/
// $Log: WUPickFrame.java,v $
// Revision 1.3  2013/09/25 11:04:29  wendy
// get valid types in init() to enable link properly
//
// Revision 1.2  2013/09/19 15:09:14  wendy
// IN4311970:  Avails linked to incorrect product structures
//
// Revision 1.1  2012/09/27 19:39:25  wendy
// Initial code
//
public class WUPickFrame extends PickFrame
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.3 $";
    
    private Vector<String> validTypesVct;
    /**
     * @param used
     * @param list
     */
    public WUPickFrame(WUsedActionTab used, EntityList list)  {
        super(used,list);
        
        // try to select the valid tab
        if(tabbedPane.getTabCount()>1){
        	for(int i=0;i<tabbedPane.getTabCount(); i++){
        		if(validTypesVct.contains(tabbedPane.getEntityGroup(i).getEntityType())){
        			tabbedPane.setSelectedIndex(i);
        			break;
        		}
        	}
        }
    }
    
    /**
     * init frame components
     */
    protected void init() {
    	super.init();
        validTypesVct = ((WUsedActionTab)actionTab).getValidTypes();
    }
    
    public void dereference(){
    	super.dereference();

    	validTypesVct.clear();
    	validTypesVct = null;
    }
	/* (non-Javadoc)
	 * @see com.ibm.eacm.ui.EACMFrame#disableActions()
	 */
	protected void disableActions(){
		super.disableActions();

		tabbedPane.setEnabled(false);
		tabbedPane.getTable().setEnabled(false);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.ui.EACMFrame#refreshActions()
	 */
	protected void refreshActions() {
		tabbedPane.setEnabled(true);
		tabbedPane.getTable().setEnabled(true);

		//LINK_ACTION - no linkactionitem is necessary
		getAction(LINK_ACTION).setEnabled(true);//tabbedPane.hasSelection());
		
		super.refreshActions();
    }
    /**
     * createToolbar using same actions as menus
     */
    protected void createToolbar() {
    	super.createToolbar();

    	tBar.add(getAction(LINK_ACTION));
    	tBar.addSeparator();
       	tBar.add(getAction(FINDREP_ACTION));
      	tBar.add(getAction(FILTER_ACTION));
    }
    protected void createActionMenu() {
    	JMenu actMnu = new JMenu(Utils.getResource(ACTIONS_MENU));
    	actMnu.setMnemonic(Utils.getMnemonic(ACTIONS_MENU));
    	addLocalActionMenuItem(actMnu, LINK_ACTION);
    	menubar.add(actMnu);
    }
    /**
     * create all of the actions, they are shared between toolbar and menu
     */
    protected void createActions() {
    	super.createActions();

        //LINK_ACTION
        EACMAction act = new LinkAction();
        addAction(act);
    }
    
	public void stateChanged(ChangeEvent _ce) {
		super.stateChanged(_ce);
		//LINK_ACTION - no linkactionitem is necessary
		getAction(LINK_ACTION).setEnabled(true);
	}
    /**
     * get LinkActionItem from the currently selected tab
     * @return
     *
     */
    private LinkActionItem getLinkActionItem() {
    	EntityGroup eg = tabbedPane.getEntityGroup();
    	ActionGroup ag = eg.getActionGroup();
    	if (ag != null) {
    		for (int i = 0; i < ag.getActionItemCount(); ++i) {
    			EANActionItem item = ag.getActionItem(i);
    			if (item instanceof LinkActionItem) {
    				return (LinkActionItem) item;
    			}
    		}
    	}

    	return null;
    }

    //================================================================

	private class LinkAction extends EACMAction {
		private static final long serialVersionUID = 1L;
		LinkAction() {
			super(LINK_ACTION, KeyEvent.VK_L, Event.CTRL_MASK);
		    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("link.gif"));
		    setEnabled(false);
		}

		protected boolean canEnable(){ 
			//LINK_ACTION - no linkactionitem is necessary
			EntityGroup eg = tabbedPane.getEntityGroup();
			boolean validlink = false;
			if(eg !=null && validTypesVct != null && tabbedPane.hasSelection()){
				validlink = validTypesVct.contains(eg.getEntityType());
			}
			return validlink; 
		}
		
		public void actionPerformed(ActionEvent e) {
			disableActionsAndWait();

			LinkActionItem lai = getLinkActionItem();
			if (lai != null && lai.hasChainAction()) {
				EntityItem[] selItems = null;
				try {
					selItems = tabbedPane.getSelectedEntityItems(false, true);

					((WUsedActionTab)actionTab).linkChain(selItems,lai);
					//moved to tab because if pickframe is closed during link it gets all messed up
				} catch (OutOfRangeException range) { //crActChain
					com.ibm.eacm.ui.UI.showFYI(WUPickFrame.this,range);
				} //crActChain
				selItems = null; //crActChain
				closeAction.actionPerformed(null);
			} else {
				EntityItem[] dataItems = null;
				try {
					dataItems = tabbedPane.getSelectedEntityItems(false, true);
					((WUsedActionTab)actionTab).link(dataItems);
					//moved to tab because if pickframe is closed during link it gets all messed up
				} catch (OutOfRangeException range) {
					com.ibm.eacm.ui.UI.showFYI(WUPickFrame.this,range);
					enableActionsAndRestore();
				}
				dataItems = null;
			}
		}
	}
}
