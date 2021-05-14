//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.SwingUtilities;

import com.ibm.eacm.actions.EACMAction;
/*********************************************************************
 * reset all user preferences
 * @author Wendy Stimpson
 */
//$Log: ResetPreferencesAction.java,v $
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
class ResetPreferencesAction extends EACMAction {
    private static final long serialVersionUID = 1L;
    private PrefDialog prefDialog = null;
 
    /**
     * constructor
     * @param fd
     */
    ResetPreferencesAction(PrefDialog fd) {
        super(RESETPREFS_ACTION);
        prefDialog = fd;
    }
 
    /**
     * release memory
     */
    public void dereference(){
        prefDialog=null;
        super.dereference();
    }
    /* (non-Javadoc)
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
    	// get preference node
    	Preferences prefNode = Preferences.userNodeForPackage(PrefMgr.class);
    	try {
			prefNode.removeNode();
	        prefNode.flush(); 
	        // this causes this to be executed on the event dispatch thread
	        // after actionPerformed returns
	        SwingUtilities.invokeLater(new Runnable() {
	        	public void run() {
	        		prefDialog.resetAllPreferences();
	        	}
	        });
		} catch (BackingStoreException bse) {
			Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, bse.getMessage(),bse);
		}
    }     
}
 