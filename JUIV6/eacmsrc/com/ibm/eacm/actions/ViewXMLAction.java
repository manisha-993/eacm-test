//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2014  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.ibm.eacm.mw.RMIMgr;
import com.ibm.eacm.nav.Navigate;

import java.awt.Desktop;
import java.awt.Event;
import java.awt.Window;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;


/*********************************************************************
* This is used to view xml from cache in the BHODS - RCQ285768
* @author Wendy Stimpson
*/
//$Log: ViewXMLAction.java,v $
//Revision 1.1  2014/02/24 15:08:10  wendy
//RCQ285768 - view cached XML in JUI
//
public class ViewXMLAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Navigate nav=null;
	private Window owner = null;
	private boolean hasODS = false;
	private boolean hasBrowser = false;
	
	/**
	 * constructor
	 * @param win
	 * @param n
	 */
	public ViewXMLAction(Window win, Navigate n) {
		super(VIEWXML_ACTION,KeyEvent.VK_1, Event.CTRL_MASK + Event.ALT_MASK);
		
		nav = n;
		owner = win;
		try {
			hasODS = RMIMgr.getRmiMgr().getRemoteDatabaseInterface().hasODS();
		} catch (Exception e) {
			Logger.getLogger(NAV_PKG_NAME).log(Level.SEVERE,"Exception "+e,e);
		}
		
		// this will launch the system default browser 
		if(Desktop.isDesktopSupported()){
			Desktop dt = Desktop.getDesktop();   
			hasBrowser = dt.isSupported(Desktop.Action.BROWSE);
		}
		Level msglvl = Level.INFO;
		if(!hasBrowser || !hasODS){
			msglvl = Level.WARNING;
		}
		Logger.getLogger(NAV_PKG_NAME).log(msglvl,"hasODS "+hasODS+" hasBrowser "+hasBrowser);

		setEnabled(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractAction#setEnabled(boolean)
	 */
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && hasODS && hasBrowser);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		nav=null;
		owner = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				com.ibm.eacm.ui.ViewXMLDialog bmd =  new com.ibm.eacm.ui.ViewXMLDialog(owner,nav);
				bmd.setVisible(true);
			}
		});
	}
}