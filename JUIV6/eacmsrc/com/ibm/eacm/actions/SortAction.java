//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.actions;

import java.awt.event.ActionEvent;
import com.ibm.eacm.objects.*;
import java.awt.Window;
import javax.swing.SwingUtilities;


/*********************************************************************
 * This is used for sort requests
 * @author Wendy Stimpson
 */
//$Log: SortAction.java,v $
//Revision 1.1  2012/09/27 19:39:16  wendy
//Initial code
//
public class SortAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Sortable sortable=null;
	private Window owner = null;
	
	/**
	 * constructor
	 * @param win
	 * @param n
	 */
	public SortAction(Window win, Sortable n) {
		super(SORT_ACTION);
		sortable = n;
		owner = win;
		setEnabled(true);
	}
	
	/**
	 * called when table to sort is changed
	 * @param s
	 */
	public void setSortable(Sortable s){
		sortable=s; // navigate table changes
		setEnabled(true);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.AbstractAction#setEnabled(boolean)
	 */
	public void setEnabled(boolean newValue) {
		super.setEnabled(newValue && sortable !=null);
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();

		sortable=null;
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
				com.ibm.eacm.ui.SortDialog bmd =  new com.ibm.eacm.ui.SortDialog(owner,sortable);
				bmd.setVisible(true);
			}
		});
	}
}