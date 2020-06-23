//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.ui.UI;

/*********************************************************************
 * export user preferences
 * @author Wendy Stimpson
 */
//$Log: ExportPreferencesAction.java,v $
//Revision 1.2  2013/01/25 20:39:28  wendy
//Add error title
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
class ExportPreferencesAction extends EACMAction {
    private static final long serialVersionUID = 1L;
    private JFileChooser fileChooser;
    private ActionListener actListener = null;
    private PrefDialog prefDialog = null;
 
    /**
     * constructor
     * @param fd
     */
    ExportPreferencesAction(PrefDialog fd) {
        super(EXPORTPREFS_ACTION);
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
        //prefDialog.saveCurrentEdit();
        // this causes this to be executed on the event dispatch thread
        // after actionPerformed returns
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                fileChooser = new JFileChooser();
 
                fileChooser.setFileFilter(new FileFilter() {
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().endsWith(".xml");
                    }
 
                    public String getDescription() {
                        return Utils.getResource("pref.xml");//pref.xml = XML file
                    }
                });
                actListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        File file = fileChooser.getSelectedFile();
                        if(file!=null){
                            // get preference node
                            Preferences prefNode = Preferences.userNodeForPackage(PrefMgr.class);
                            FileOutputStream fout = null;      
                            try {
                                fout = new FileOutputStream(file);
                                prefNode.exportSubtree(fout);
                                fout.flush();
                            } catch (Exception ioe) {
                            	Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, ioe.getMessage(),ioe);
                                UI.showException(prefDialog, ioe,"exportpref.err-title");
                            } finally {
                                try {
                                    if (fout != null) {
                                        fout.close(); 
                                    }
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                    }
                };
                fileChooser.addActionListener(actListener);
                fileChooser.showSaveDialog(prefDialog);
                fileChooser.removeActionListener(actListener);
                actListener = null;
                fileChooser = null;
            }
        });
    }     
}
 