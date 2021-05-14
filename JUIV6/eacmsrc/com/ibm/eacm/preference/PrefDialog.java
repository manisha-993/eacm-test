//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import java.awt.event.ActionEvent;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.*;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.ui.EACMDialog;

/******************************************************************************
* This is used to display the preferences dialog.  
* @author Wendy Stimpson
*/
// $Log: PrefDialog.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//

public class PrefDialog extends EACMDialog 
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    
    private PrefMgr prefMgr = null;
      
    public PrefDialog(EACM eacm)  {
        super(eacm,"pref.panel",JDialog.ModalityType.APPLICATION_MODAL);

    	closeAction = new CloseAction(this);
    	
    	addAction(new ExportPreferencesAction(this));
    	addAction(new ImportPreferencesAction(this));
       	addAction(new ResetPreferencesAction(this));
    	
        createMenuBar();
        prefMgr = new PrefMgr(closeAction);
      
        getContentPane().add(prefMgr);

        finishSetup(eacm);
    }
     
    private void createMenuBar() {
        menubar = new JMenuBar();
        createFileMenu();
     
        setJMenuBar(menubar);
    }
    private void createFileMenu(){
    	JMenu mnuFile = new JMenu(Utils.getResource(FILE_MENU));
    	mnuFile.setMnemonic(Utils.getMnemonic(FILE_MENU));
    	mnuFile.add(closeAction);
    	mnuFile.addSeparator();
    	mnuFile.add(getAction(EXPORTPREFS_ACTION));
    	mnuFile.add(getAction(IMPORTPREFS_ACTION));
    	mnuFile.add(getAction(RESETPREFS_ACTION));
    	
    	menubar.add(mnuFile);
    }
    /**
     * import application prefrences
     * @param fin
     * @throws BackingStoreException
     * @throws IOException
     * @throws InvalidPreferencesFormatException
     */
    void importPreferences(FileInputStream fin) throws BackingStoreException, IOException, InvalidPreferencesFormatException{
    	// clear any previous preferences
    	Preferences.userNodeForPackage(PrefMgr.class).removeNode();
    	Preferences.userNodeForPackage(PrefMgr.class).flush();
    	
    	Preferences.importPreferences(fin);
    	//refresh the panels
    	prefMgr.updateFromPrefs();
    }
    
    /**
     * all preferences were reset, reload panels
     */
    void resetAllPreferences(){
    	//refresh the panels
    	prefMgr.updateFromPrefs();
    }
    /**
     * release memory
     */
    public void dereference() {
    	super.dereference();
    	
    	if (prefMgr!=null){
    		prefMgr.dereference();
    		prefMgr = null;
    	}
    }
    
    private class CloseAction extends CloseDialogAction
    {
		private static final long serialVersionUID = 1L;
		private static final String CMD = "clse";
		CloseAction(EACMDialog dialog) {
            super(dialog,CMD);
		}

		public void actionPerformed(ActionEvent e) {
			java.util.Vector<String> chgsVct =prefMgr.hasChanges();
			if (chgsVct.size()>0){
				// confirm with user about closing
				//pref.changes.found=Changes were found in the following:
				//pref.changes.question=Do you really want to close?
				StringBuffer sb = new StringBuffer(Utils.getResource("pref.changes.found")+RETURN);
				for (int i=0; i<chgsVct.size(); i++){
					sb.append(chgsVct.elementAt(i)+RETURN);
				}
				sb.append(Utils.getResource("pref.changes.question"));
				int r = com.ibm.eacm.ui.UI.showConfirmOkCancel(PrefDialog.this, sb.toString());
				chgsVct.clear();
			 	if (r==CLOSED || r==CANCEL_BUTTON){ // dont close
		    		return;
		    	}
			}
			prefMgr.isClosing();
			
			super.actionPerformed(e);
		} 	
		public void windowActivated(WindowEvent e) {
			prefMgr.requestFocusInWindow(); // restore focus so ESC can close window
		}
    }
}
