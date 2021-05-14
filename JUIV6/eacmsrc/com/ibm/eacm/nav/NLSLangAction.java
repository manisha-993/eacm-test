//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.nav;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import COM.ibm.opicmpdh.transactions.NLSItem;

import com.ibm.eacm.EACM;
import com.ibm.eacm.actions.*;
import com.ibm.eacm.tabs.TabPanel;


/**
 * this is used for Languages, one per NLSItem.
 * @author Wendy Stimpson
 */
// $Log: NLSLangAction.java,v $
// Revision 1.1  2012/09/27 19:39:14  wendy
// Initial code
//
public class NLSLangAction extends EACMAction{
	private static final long serialVersionUID = 1L;
	private NLSItem nls=null;
	/**
	 * @param n
	 */
	public NLSLangAction(NLSItem n) {
		nls = n;
		putValue(Action.NAME, nls.toString());
		setActionKey(""+nls.getNLSID());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
    	TabPanel eTab = EACM.getEACM().getCurrentTab();
        if(eTab!=null){
        	eTab.setReadLanguage(nls);
        }
	}
	/* (non-Javadoc)
	 * @see com.ibm.eacm.actions.EACMAction#dereference()
	 */
	public void dereference(){
		super.dereference();
		nls = null;
	}
}
