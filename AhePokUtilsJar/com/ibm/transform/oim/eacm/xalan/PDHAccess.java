/**
 * (c) Copyright International Business Machines Corporation, 2003,2004,2005,2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.xalan;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.Profile;

/**
 * Classes implement this interface if they need to do DB queries 
 * @author cstolpe
 */
public interface PDHAccess {
	/**
	 * Database setter method.
	 * This is called after object instantiation by XSLReportABR
	 * @return true if initialization successfull
	 */
	boolean setDatabase(Database database);

	/**
	 * Profile setter method.
	 * This is called after object instantiation by XSLReportABR
	 * @return true if initialization successfull
	 */
	boolean setProfile(Profile profile);

	/**
	 * Dereference all middleware objects.
	 * This is called by the style sheet extension when it the dereference method is called
	 * @return true if initialization successfull
	 */
	boolean dereference();

}
