/*
 * (c) Copyright International Business Machines Corporation, 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.sg.abr;

import com.ibm.transform.oim.eacm.xalan.DataView;
import com.ibm.transform.oim.eacm.xalan.ReturnCode;

/**
 * This is for the AdvanceStatus ABR. Implement this interface if there are any checks to be performed when DATAQUALITY=Draft
 * @author cstolpe
 */
public interface DraftChecks extends ReturnCode {
	/**
	 * The checks to be performed
     * @param dv
     * @return
     */
    boolean draftChecks(DataView dv);
}
