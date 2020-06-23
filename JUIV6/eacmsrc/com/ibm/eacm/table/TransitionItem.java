//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.table;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANBusinessRuleException;
import COM.ibm.eannounce.objects.StateTransitionException;

/**
 *  used for fillpaste and attribute transitions
 * @author Wendy Stimpson
 */
// $Log: TransitionItem.java,v $
// Revision 1.1  2012/09/27 19:39:12  wendy
// Initial code
//
class TransitionItem {
	private EANAttribute origEAN = null;
	private EANAttribute newEAN = null;

	/**
	 * 
	 * @param orig
	 * @param newti
	 */
	TransitionItem(EANAttribute orig, EANAttribute newti) {
		origEAN = orig;
		newEAN = newti;
	}

	/**
	 * release memory
	 */
	void dereference() {
		origEAN = null;
		newEAN = null;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		return "orig: " + origEAN.getKey() + " new: " + newEAN.getKey();
	}

	/**
	 * process
	 * @param showerr
	 * @return
	 */
	boolean process(boolean showerr) {
		try {
			newEAN.put(origEAN.get());
			return true;
		} catch (StateTransitionException ste) {
			if (showerr) {
				com.ibm.eacm.ui.UI.showException(null,ste);
			}
		} catch (EANBusinessRuleException bre) {
			com.ibm.eacm.ui.UI.showException(null,bre);
		}
		return false;
	}
}
