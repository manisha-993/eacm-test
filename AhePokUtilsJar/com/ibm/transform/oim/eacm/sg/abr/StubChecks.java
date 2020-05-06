/*
 * (c) Copyright International Business Machines Corporation, 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.sg.abr;

import com.ibm.transform.oim.eacm.xalan.DataView;

/**
 * For any value of DATAQUALITY not itemized below, 
 * the ABR should set its attribute to “Passed” (0030) 
 * without doing anything else.
 * 
 * @author cstolpe
 *
 */
public class StubChecks implements ReadyForReviewChecks, FinalChecks {
	private boolean hasPassed = false;

	/**
     * Implements the FinalChecks interface
     *
     * @return true if there was no system error during the checks
     * @param dv 
     */
	public boolean finalChecks(DataView dv) {
		hasPassed = true;
		return true;
	}

	/**
	 * Implements ReturnCode interface
	 * @return true if the checks passed
	 */
	public boolean hasPassed() {
		return hasPassed;
	}
	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.ReturnCode#getMessage()
	 */
	public String getMessage(){
		return "";
	}
	/**
     * Implements the ReadyForReviewChecks interface
     *
     * @return true if there was no system error during the checks
     * @param dv 
     */
	public boolean readyForReviewChecks(DataView dv) {
		hasPassed = true;
		return true;
	}

}
