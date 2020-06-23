//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2014  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.edit;

import java.awt.Event;
import java.awt.event.*;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;


/*********************************************************************
* This is used to import new rows from an Apache OpenDocument ss
* RCQ 288700
* @author Wendy Stimpson
*/
//$Log: ImportODSAction.java,v $
//Revision 1.2  2014/01/24 18:43:59  wendy
//disable if jar file is missing
//
//Revision 1.1  2014/01/22 20:38:16  wendy
//RCQ 288700
//
public class ImportODSAction extends EACMAction
{
	private EditController editCtrl = null;
	private static final long serialVersionUID = 1L;
	
	/**
	 * @param ed
	 */
	public ImportODSAction(EditController ed) {
		super(IMPORTODS_ACTION,KeyEvent.VK_7, Event.CTRL_MASK);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("ods.png"));
	    editCtrl = ed;
	    super.setEnabled(false);
	    
	    try{
	    	// hack to make sure jar file is in the classpath
	    	@SuppressWarnings("unused")
			org.jopendocument.dom.ODValueType odv = org.jopendocument.dom.ODValueType.DATE;
	    }catch(java.lang.NoClassDefFoundError err){
	    	Logger.getLogger(EDIT_PKG_NAME).log(Level.SEVERE,
	    			"NoClassDefFoundError: jOpenDocument-1.3.jar is missing. Cannot find: "+err.getMessage());
	    	editCtrl = null;
	    }
	}
	/**
	 * derived classes should override for conditional checks needed before enabling
	 * @return
	 */
	protected boolean canEnable(){ 
		return editCtrl!=null && editCtrl.isCreatable();
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (!editCtrl.getCurrentEditor().canStopEditing()) { // end any current edits
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					//display problem to user
					editCtrl.getCurrentEditor().stopCellEditing();
				}
			});
			return;
		}
		editCtrl.disableActionsAndWait();
		
		final String fn = getImportFileName();
		if(fn !=null){
			// this causes this to be executed on the event dispatch thread
			// after actionPerformed returns
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					// import ss into new rows
					editCtrl.importODSTable(fn);
					editCtrl.enableActionsAndRestore();	
					editCtrl.getCurrentEditor().requestFocusInWindow();
				}
			});
		}else{
			editCtrl.enableActionsAndRestore();	
		}
	}


	/**
	 * getImportFileName
	 *
	 * @return
	 */
	private String getImportFileName() {
		int reply = -1;

		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		String userdir = System.getProperty("user.dir");
		chooser.setAcceptAllFileFilterUsed(false);
		if (userdir!=null){
			chooser.setCurrentDirectory(new File(userdir));
		}
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			private String getExtension(File f) {
				String ext ="";
				if(f != null) {
					String filename = f.getName();
					int i = filename.lastIndexOf('.');
					if(i>0 && i<filename.length()-1) {
						ext= filename.substring(i).toLowerCase();
					}
				}
				return ext;
			}
			public boolean accept(File f) {
				if (f.isDirectory()){
					return true;
				}
				String extension = getExtension(f);
				if (extension.equals(".ods")) {
					return true;
				} else {
					return false;
				}
			}

			//The description of this filter
			public String getDescription() {
				return "Apache OpenDocument (*.ods)";
			}
		});

		reply = chooser.showOpenDialog(editCtrl);
		if (reply == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (file != null) {
				// if user selected ods file then use new import code
				if (file.getName().toLowerCase().endsWith(".ods")) {
					return file.getAbsolutePath();
				}

			}
		}
		return null;
	}
}

