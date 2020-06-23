//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import com.ibm.eacm.objects.PrintUtilities;

/*********************************************************************
 * This is used for print page setup
 * @author Wendy Stimpson
 */
//$Log: PageSetupAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class PageSetupAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public PageSetupAction() {
		super(PAGESETUP_ACTION);
	}
	public void actionPerformed(ActionEvent e) {
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				PrintUtilities.pageSetup();
			}
		});
	}
}