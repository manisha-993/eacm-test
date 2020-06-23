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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
 * import user preferences
 * @author Wendy Stimpson
 */
//$Log: ImportPreferencesAction.java,v $
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
class ImportPreferencesAction extends EACMAction {
    private static final long serialVersionUID = 1L;
    private JFileChooser fileChooser;
    private ActionListener actListener = null;
    private PrefDialog prefDialog = null;
 
    /**
     * constructor
     * @param fd
     */
    ImportPreferencesAction(PrefDialog fd) {
        super(IMPORTPREFS_ACTION);
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
       // prefDialog.cancelCurrentEdit();
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
                        return Utils.getResource("pref.xml");//pref.xml=XML file
                    }
                });
                actListener = new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        File file = fileChooser.getSelectedFile();
                        if(file!=null){
                            FileInputStream fin = null;      
                            try {
                                fin = new FileInputStream(file);
                                prefDialog.importPreferences(fin);
                            } catch (Exception ioe) {
                            	Logger.getLogger(APP_PKG_NAME).log(Level.SEVERE, ioe.getMessage(),ioe);
                            } finally {
                                try {
                                    if (fin != null) {
                                        fin.close(); 
                                    }
                                } catch (IOException ioe) {
                                    ioe.printStackTrace();
                                }
                            }
                        }
                    }
                };
                fileChooser.addActionListener(actListener);
 
                fileChooser.showOpenDialog(prefDialog);
 
                fileChooser.removeActionListener(actListener);
                actListener = null;
                fileChooser = null;
            }
        });
    } 
}
