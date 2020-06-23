//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import com.ibm.eacm.EACM;
import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.ShellControl;

/*********************************************************************
 * This is used for more information
 * @author Wendy Stimpson
 */
//$Log: FMIAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class FMIAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public FMIAction() {
		super(FMI_ACTION);
	}
	public void actionPerformed(ActionEvent e) {
		EACM.getEACM().disableActionsAndWait();
		
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// fmiURL = http://eannounce.pok.ibm.com
				ShellControl.launchURL(EACMProperties.getProperty("fmiURL"));
				EACM.getEACM().enableActionsAndRestore();
			}
		});
	
	}
}

