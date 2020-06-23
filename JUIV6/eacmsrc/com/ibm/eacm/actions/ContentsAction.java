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
 * This is used to for contents information
 * @author Wendy Stimpson
 */
//$Log: ContentsAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class ContentsAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public ContentsAction() {
		super(CONTENTS_ACTION);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		EACM.getEACM().disableActionsAndWait();
		
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//contentsURL = http://eannounce.pok.ibm.com/Education/1.1/
				ShellControl.launchURL(EACMProperties.getProperty("contentsURL"));
				EACM.getEACM().enableActionsAndRestore();
			}
		});
	}
}

