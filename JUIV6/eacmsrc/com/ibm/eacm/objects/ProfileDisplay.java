//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * used to display profile in a control
 * @author Wendy Stimpson
 */
// $Log: ProfileDisplay.java,v $
// Revision 1.1  2012/09/27 19:39:13  wendy
// Initial code
//
public class ProfileDisplay {
	private Profile prof = null;

	/**
     * profileDisplay
     * @param _prof
     */
    public ProfileDisplay(Profile _prof) {
		prof = _prof;
	}

	/**
     * getProfile
     * @return
     */
    public Profile getProfile() {
		return prof;
	}

	/**
     * @see java.lang.Object#toString()
     */
    public String toString() {
		return prof.getOPName();
	}
}
