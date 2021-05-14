//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.ibm.eacm.*;
import com.ibm.eacm.objects.Routines;
import com.ibm.eacm.objects.Utils;
import com.ibm.eacm.tabs.TabPanel;

/*********************************************************************
 * This is used for data information request - help
 * @author Wendy Stimpson
 */
//$Log: AttrHelpAction.java,v $
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class AttrHelpAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	public AttrHelpAction() {
		super(ATTRHELP_ACTION,KeyEvent.VK_F1, 0);
	}
	public void actionPerformed(ActionEvent e) {	   
		String str = null;
		TabPanel eTab = EACM.getEACM().getCurrentTab();
		if (eTab != null) {
			str = eTab.getHelpText();
			// split it at 80 char boundaries
			str = Routines.addLineWraps(str, 80);
		}
		com.ibm.eacm.ui.UI.showFYI(null,(str != null && str.trim().length()>0) ? str : Utils.getResource("nia"));
	}
}