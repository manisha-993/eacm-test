//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.SwingUtilities;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.RestoreActionTab;
import com.ibm.eacm.tabs.TabPanel;


/*********************************************************************
 * This is used for restore entity request
 * @author Wendy Stimpson
 */
//$Log: NavRestoreAction.java,v $
//Revision 1.2  2015/03/05 02:20:01  stimpsow
//correct keybd accelerators
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
public class NavRestoreAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Navigate nav=null;
	public NavRestoreAction(Navigate n) {
		super(RESTORE_ACTION, KeyEvent.VK_9, Event.ALT_MASK,true);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("restore.gif"));
		nav = n;
	}
	public void dereference(){
		super.dereference();
		nav=null;
	}
	public void actionPerformed(ActionEvent e) {
		if (!viewRestoreExist()) {
			nav.disableActionsAndWait();
			//	this causes this to be executed on the event dispatch thread
			// after actionPerformed returns
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					RestoreActionTab tab = new RestoreActionTab(nav.getProfile());
					EACM.getEACM().addTab(nav.getNavController(), tab); 
					tab.requestFocusInWindow();

					nav.enableActionsAndRestore();
				}
			});
		}  	
    }
    private boolean viewRestoreExist() {
        for (int i = 0; i < EACM.getEACM().getTabCount(); ++i) {
        	TabPanel etab = EACM.getEACM().getTab(i);
            if (etab instanceof RestoreActionTab){
                if (((RestoreActionTab) etab).viewRestoreExist()) {
                	EACM.getEACM().setSelectedIndex(i);
                    return true;
                }
            }
        }
        return false;
    }
}