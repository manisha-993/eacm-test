//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import com.ibm.eacm.nav.Navigate;
import com.ibm.eacm.objects.*;

import java.awt.Window;

import javax.swing.Action;
import javax.swing.SwingUtilities;

/*********************************************************************
 * This is used for filter requests
 * @author Wendy Stimpson
 */
//$Log: FilterAction.java,v $
//Revision 1.2  2013/05/01 18:35:15  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:17  wendy
//Initial code
//
public class FilterAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Filterable filterable=null;
	private Window owner = null;
	private Navigate nav = null; // support back nav reloading filters
	
	/**
	 * constructor
	 * @param win
	 * @param n
	 */
	public FilterAction(Window win, Filterable n) {
		super(FILTER_ACTION,KeyEvent.VK_F8,0);
	    putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("fltr.gif"));
	
		filterable = n;
		owner = win;
		setEnabled(true);
	}
	
	/**
	 * used for back nav reload of filters
	 * @param n
	 */
	public void setNavigate(Navigate n){
		nav = n;
	}
	/**
	 * navigate table changes
	 * @param f
	 */
	public void setFilterable(Filterable f){ 
		filterable = f;
		setEnabled(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractAction#setEnabled(boolean)
	 */
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && filterable !=null);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();

		filterable=null;
		owner = null;
		nav = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// this causes this to be executed on the event dispatch thread
		// after actionPerformed returns
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
			   	com.ibm.eacm.ui.FilterDialog bmd =  new com.ibm.eacm.ui.FilterDialog(owner,filterable);
			   	bmd.setNavigate(nav);
		    	bmd.setVisible(true);
			}
		});
	}
}