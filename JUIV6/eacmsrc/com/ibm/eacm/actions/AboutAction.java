//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;

import com.ibm.eacm.objects.EACMProperties;
import com.ibm.eacm.objects.Utils;
import com.ibm.eannounce.version.Version;


/*********************************************************************
 * This is used to display information about EACM
 * @author Wendy Stimpson
 */
//$Log: AboutAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class AboutAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public AboutAction() {
		super(ABOUT_ACTION);
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		com.ibm.eacm.ui.UI.showFYI(null,EACMProperties.getProperty("eannounce.version") + 
        		RETURN + Utils.getResource("copyright") + RETURN + Version.getVersion());
	}
}

