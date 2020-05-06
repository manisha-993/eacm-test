/**
 * (c) Copyright International Business Machines Corporation, 2003,2004,2005,2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.xalan;

/**
 * Interface required by XMLABR to get the data an ABR makes available to the report
 * @author cstolpe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface Data {
	/**
	 * Getter method
     * @return field
     */
    DataView getDataView();
	/**
	 * Setter method
     * @param dv
     * @return true if dv is not null and in some cases of the class is initialized
     */
    boolean setDataView(DataView dv);
}
