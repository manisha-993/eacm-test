//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.preference;

import javax.swing.JPanel;

/**
 * this is used by all preference items in the preference dialog
 * @author Wendy Stimpson
 */
// $Log: Preferencable.java,v $
// Revision 1.1  2012/09/27 19:39:15  wendy
// Initial code
//
public interface Preferencable {
	JPanel getButtonPanel();
	void dereference();
	void isClosing();
	boolean hasChanges();
	void updateFromPrefs();
}
