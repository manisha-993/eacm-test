//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.Action;

import com.ibm.eacm.*;
import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is used for bookmark request
 * @author Wendy Stimpson
 */
//$Log: BookmarkAction.java,v $
//Revision 1.3  2015/03/05 02:20:01  stimpsow
//correct keybd accelerators
//
//Revision 1.2  2013/09/13 18:17:43  wendy
//show frame if iconified
//
//Revision 1.1  2012/09/27 19:39:14  wendy
//Initial code
//
public class BookmarkAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Navigate nav=null;
	/**
	 * @param n
	 */
	public BookmarkAction(Navigate n) {
		super(BOOKMARK_ACTION,KeyEvent.VK_B, Event.CTRL_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("bookmark.gif"));
		nav = n;
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		nav=null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//make sure one isnt already open
		com.ibm.eacm.ui.BookmarkFrame bmd = EACM.getEACM().getBookMark();
		if (bmd==null){
			bmd = new com.ibm.eacm.ui.BookmarkFrame();
			bmd.setActionItem(nav.getNavAction(),nav.getNavigationHistory());
			bmd.setVisible(true);
			bmd.requestFocus(); // jumps behind gui without this
		}else{
			if(bmd.getState()==com.ibm.eacm.ui.BookmarkFrame.ICONIFIED){
				bmd.setState(com.ibm.eacm.ui.BookmarkFrame.NORMAL);
			}
			bmd.toFront();
		}			
	}
}