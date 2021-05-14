/**
 * Copyright (c) 2002-2004 International Business Machines Corp., Ltd.
 * All Rights Reserved.
 * Licensed for use in connection with IBM business only.
 *
 * cr_6303
 *
 * @version 1.2  2004/03/12
 * @author Anthony C. Liberto
 *
 * $Log: ProfileDisplay.java,v $
 * Revision 1.1  2007/04/18 19:42:18  wendy
 * Reorganized JUI module
 *
 * Revision 1.1.1.1  2005/09/09 20:37:40  tony
 * This is the initial load of OPICM
 *
 * Revision 1.4  2005/09/08 17:58:57  tony
 * Adjusted Package information for compatibility with
 * XML editor per AHE
 *
 * Revision 1.3  2005/02/02 17:30:22  tony
 * JTest Second Pass
 *
 * Revision 1.2  2005/01/27 23:18:17  tony
 * JTest Formatting
 *
 * Revision 1.1  2004/03/12 23:07:47  tony
 * cr_6303
 * send bookmark to a friend.
 *
 *
 */
package com.elogin;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Anthony C. Liberto
 */
public class ProfileDisplay {
	private Profile prof = null;

	/**
     * profileDisplay
     * @param _prof
     * @author Anthony C. Liberto
     */
    public ProfileDisplay(Profile _prof) {
		prof = _prof;
	}

	/**
     * getProfile
     * @return
     * @author Anthony C. Liberto
     */
    public Profile getProfile() {
		return prof;
	}

	/**
     * @see java.lang.Object#toString()
     * @author Anthony C. Liberto
     */
    public String toString() {
		return prof.getOPName();
	}
}
