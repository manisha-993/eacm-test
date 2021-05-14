//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JTabbedPane;


/*********************************************************************
 * This is used for select window menu
 * @author Wendy Stimpson
 */
//$Log: WindowAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class WindowAction extends EACMAction
{
	private JTabbedPane tabbedPane = null;
	private int tabId = 0;
	private static final long serialVersionUID = 1L;
	public WindowAction(JTabbedPane tp,String text,int tabid) {
		tabId = tabid-1;
		putValue(Action.NAME, text);
		setActionKey(""+tabId);
		tabbedPane = tp;
	}
	public void actionPerformed(ActionEvent e) {
		tabbedPane.setSelectedIndex(tabId);
	}
	public void dereference(){
		super.dereference();
		tabbedPane = null;
	}
}

