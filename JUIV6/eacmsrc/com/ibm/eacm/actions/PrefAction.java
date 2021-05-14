//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;


import java.awt.event.ActionEvent;

import com.ibm.eacm.*;
import com.ibm.eacm.preference.PrefDialog;

/*********************************************************************
 * This is used for preference dialog request
 * @author Wendy Stimpson
 */
//$Log: PrefAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class PrefAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public PrefAction() {
		super(PREF_ACTION);
	}
	public void actionPerformed(ActionEvent e) {
    	EACM.getEACM().setWaitCursor();
        PrefDialog prefDialog = new PrefDialog(EACM.getEACM());
        prefDialog.setVisible(true);
        EACM.getEACM().setDefaultCursor();
	}
}