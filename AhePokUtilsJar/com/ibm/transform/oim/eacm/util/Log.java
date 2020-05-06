/**
 * (c) Copyright International Business Machines Corporation, 2003,2004,2005,2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.util;

/**
 * This interface is implemented by classes which use a Logger instance to set the identifyer used in the log
 * <pre>
 * $Revision: 1.1 $
 * </re>
 * @author cstolpe
 */
public interface Log {
	/**
	 * Set the identifier used by this log. Expect job ID or session ID
     * @param anIdentifier
     * @return
     */
    boolean setIdentifier(String anIdentifier);

	/**
	 * Get the identifier used by this log. Expect job ID or session ID
	 * @return String identifier
	 */
	String getIdentifier();
}
