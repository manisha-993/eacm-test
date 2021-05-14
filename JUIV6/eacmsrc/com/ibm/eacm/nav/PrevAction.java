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

import com.ibm.eacm.actions.EACMAction;
import com.ibm.eacm.objects.Utils;

/*********************************************************************
 * This is used for moving back in history, it is also an actionlistener for the navhistbox
 * @author Wendy Stimpson
 */
//$Log: PrevAction.java,v $
//Revision 1.2  2013/05/01 18:35:13  wendy
//perf updates for large amt of data
//
//Revision 1.1  2012/09/27 19:39:15  wendy
//Initial code
//
public class PrevAction extends EACMAction
{
	private static final long serialVersionUID = 1L;
	private Navigate nav=null;
	private NavHistBox historyCombo = null;
	
	/**
	 * constructor
	 * @param n
	 * @param nhb
	 */
	public PrevAction(Navigate n,NavHistBox nhb) {
		super(PREV_ACTION, KeyEvent.VK_LEFT, Event.SHIFT_MASK);
		putValue(Action.LARGE_ICON_KEY, Utils.getImageIcon("left.gif"));
		nav = n;
		historyCombo = nhb;
		setEnabled(false);
	}

	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#canEnable()
	 */
	protected boolean canEnable(){ 
		boolean ok = false;
		if(historyCombo !=null){
			ok = historyCombo.getSelectedIndex()!=0;
		}
		return ok; 
	}
	
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		nav=null;
		historyCombo = null;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == historyCombo){
			setEnabled(true);
		}else{
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					nav.gotoPrev();
				}
			});
		}
	}
}