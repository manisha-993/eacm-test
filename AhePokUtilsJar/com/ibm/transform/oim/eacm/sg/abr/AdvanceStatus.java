/**
 * (c) Copyright International Business Machines Corporation, 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.sg.abr;

import java.util.HashMap;
import java.util.Map;

import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;
import com.ibm.transform.oim.eacm.xalan.Data;
import com.ibm.transform.oim.eacm.xalan.DataView;
import com.ibm.transform.oim.eacm.xalan.ReturnCode;

/**
 * <pre>
 * This is a stub class. 
 * To implement the real checks:
 * 1) Parameterize this class
 * 2) Subclass this class 
 * 3) Copy the source to new classes
 * 
 * $Log: AdvanceStatus.java,v $
 * Revision 1.2  2008/08/26 21:07:06  wendy
 * Updated ReturnCode interface
 *
 * Revision 1.1  2006/10/19 21:21:56  chris
 * Taxonomy ABR's
 *
 * </pre>
 * @author cstolpe
 */
public class AdvanceStatus implements Log, ReturnCode, Data {
	private final String CHANGE = "0050";
	private Object checkingClass = new StubChecks();
	private String dataQuality = "";
	private final Map dataQualityToStatus = new HashMap();
	private final String DRAFT = "DRAFT";
	private DataView dv = null;
	
	private EntityGroup eg = null;
	private EntityItem ei = null;
	private final String FINAL = "FINAL";
	private boolean hasPassed = false;
	private boolean isChangeRequest = false;
	private boolean isChangeRequestChecksOK = false;
	private boolean isDraft = false;
	private boolean isDraftChecksOK = false;
	private boolean isFinal = false;
	private boolean isFinalChecksOK = false;
	private boolean isITS = false;
	private boolean isReadyForReview = false;
	private boolean isReadyForReviewChecksOK = false;
	private final Logger log = new Logger();
	private EANMetaFlagAttribute mfaDATAQUALITY = null;
	private EANMetaFlagAttribute mfaPDHDOMAIN = null;
	private EANMetaFlagAttribute mfaSTATUS = null;
	private final String REVIEW = "REVIEW";
	private String status = "";
	private final Map statusTransitions = new HashMap();
	
	/**
     * Constructor to initialize the maps
     *
     */
    public AdvanceStatus() {
		dataQualityToStatus.put(DRAFT,  "0010");
		dataQualityToStatus.put(FINAL,  "0020");
		dataQualityToStatus.put(REVIEW, "0040");
		dataQualityToStatus.put(CHANGE,   "0050");
		statusTransitions.put("0010","0040");
		statusTransitions.put("0020","0020");
		statusTransitions.put("0040","0020");
		statusTransitions.put("0050","0040");
	}
	
	/**
	 * This is only called when we know PDHDOMAIN=ITS
	 * If the ABR checking is “Passed”, then the ABR will set STATUS = DATAQUALITY.
	 * If the ABR checking is “Failed”, then the ABR will set DATAQUALTY = “its prior value”.
	 * For any value of DATAQUALITY not itemized below, 
	 * the ABR should set its attribute to “Passed” (0030)
	 * without doing anything else.
	 * Only Ready For Review and Final have checks
	 * @return true if attributes exist and operations succeed
	 */
	private boolean checkDataQuality() {
		// This is only called when we know PDHDOMAIN=ITS
		boolean result = true;
		final String signature = ".checkDataQuality(): ";
		
		EANAttribute attDATAQUALITY = ei.getAttribute(mfaDATAQUALITY.getAttributeCode());
		if (attDATAQUALITY != null) {
			EANFlagAttribute fDATAQUALITY = (EANFlagAttribute) attDATAQUALITY;
		
			isFinal = fDATAQUALITY.isSelected(FINAL);
			isReadyForReview = fDATAQUALITY.isSelected(REVIEW);
			isDraft = fDATAQUALITY.isSelected(DRAFT);
			isChangeRequest = fDATAQUALITY.isSelected(CHANGE);

			if (isFinal && checkingClass instanceof FinalChecks) {
				FinalChecks checks = (FinalChecks) checkingClass; 
				result &= checks.finalChecks(dv);
				isFinalChecksOK = checks.hasPassed();
				hasPassed = isFinalChecksOK;
				result &= setStatus(fDATAQUALITY, isFinalChecksOK);
			}
			else if (isReadyForReview && checkingClass instanceof ReadyForReviewChecks) {
				ReadyForReviewChecks checks = (ReadyForReviewChecks) checkingClass;
				result &= checks.readyForReviewChecks(dv);
				isReadyForReviewChecksOK = checks.hasPassed();
				hasPassed = isReadyForReviewChecksOK;
				result &= setStatus(fDATAQUALITY, isReadyForReviewChecksOK);
			}
			else if (isDraft && checkingClass instanceof DraftChecks){
				DraftChecks checks = (DraftChecks) checkingClass;
				result &= checks.draftChecks(dv);
				isDraftChecksOK = checks.hasPassed();
				hasPassed = isDraftChecksOK;
				result &= setStatus(fDATAQUALITY, isDraftChecksOK);
			}
			else if (isDraft && checkingClass instanceof ChangeRequestChecks){
				ChangeRequestChecks checks = (ChangeRequestChecks) checkingClass;
				result &= checks.changeRequestChecks(dv);
				isChangeRequestChecksOK = checks.hasPassed();
				hasPassed = isChangeRequestChecksOK;
				result &= setStatus(fDATAQUALITY, isChangeRequestChecksOK);
			}
		}
		else {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.print(mfaDATAQUALITY);
			log.println(" is null");
		}
		hasPassed &= result;
		return result;
	}
	
	/**
	 * Check that the Entity has meta for PDHDOMAIN, DATAQUALITY, and STATUS. 
	 * Also check that they are all flags.
	 * This method also initializes ei, and eg.
	 * @return true if meta exists and is correct type
	 */
	private boolean checkMeta() {
		final String signature = ".checkMeta(): ";
		boolean result = true;
		
		eg = dv.getParentEntityGroup();
		result &= eg != null;
		if (result) {
			result &= eg.getEntityItemCount() > 0;
			dv.addGroupToData(eg.getEntityType());
			if (result) {
				EANMetaAttribute ma = null;
				ei = eg.getEntityItem(0);
				ma = eg.getMetaAttribute("DATAQUALITY");
				if (ma != null && ma instanceof EANMetaFlagAttribute) {
					mfaDATAQUALITY = (EANMetaFlagAttribute) ma;
				}
				else {
					log.print(getClass().getName());
					log.print(signature);
					log.print("DATAQUALITY not found in the meta for ");
					log.print(dv.getEntityType());
					log.println(" or is not a flag attribute");
					result = false;
				}

				ma = eg.getMetaAttribute("STATUS");
				if (ma != null && ma instanceof EANMetaFlagAttribute) {
					mfaSTATUS = (EANMetaFlagAttribute) ma;
				}
				else {
					log.print(getClass().getName());
					log.print(signature);
					log.print("PDHDOMAIN not found in the meta for ");
					log.print(dv.getEntityType());
					log.println(" or is not a flag attribute");
					result = false;
				}

				ma = eg.getMetaAttribute("PDHDOMAIN");
				if (ma != null && ma instanceof EANMetaFlagAttribute) {
					mfaPDHDOMAIN = (EANMetaFlagAttribute) ma;
				}
				else {
					log.print(getClass().getName());
					log.print(signature);
					log.print("STATUS not found in the meta for ");
					log.print(dv.getEntityType());
					log.println(" or is not a flag attribute");
					result = false;
				}
			}
		}
		
		return result;
	}

	/**
	 * The value DataQuality was set to
	 * @return
	 */
	public String getDataQuality() {
		return dataQuality;
	}

	/**
	 * Implements getter method of Data interface
	 * @see com.ibm.transform.oim.eacm.xalan.Data#getDataView()
	 */
	public DataView getDataView() {
		return dv;
	}

	/**
	 * Getter method of Log interface
	 * @return String identifier
	 */
	public String getIdentifier() {
		return log.getIdentifier();
	}

	/**
	 * The value Status was set to
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * The end result of the ABR
	 * @return true if checks passed and updates succeded
	 * @see com.ibm.transform.oim.eacm.xalan.ReturnCode#hasPassed()
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
	 * TODO add appropriate comment
	 * @return true
	 * @see com.ibm.transform.oim.eacm.xalan.Init#initialize()
	 */
	private boolean initialize() {
		final String signature = ".initialize(): ";
		boolean result = checkMeta();
		if (result) {
			EANAttribute attPDHDOMAIN = ei.getAttribute(mfaPDHDOMAIN.getAttributeCode());
			if (attPDHDOMAIN != null) {
				EANFlagAttribute fPDHDOMAIN = (EANFlagAttribute) attPDHDOMAIN;
				EANAttribute attSTATUS = ei.getAttribute(mfaSTATUS.getAttributeCode());
				if (attSTATUS != null) {
					isITS = fPDHDOMAIN.isSelected("ITS");
					if (isITS) {
						// The data quality checks only apply when PDHDOMAIN = “ITS” (ITS). 
						result &= checkDataQuality();
					}
					else {
						// If the PDHDOMAIN <> “ITS”, the ABR advances the “status” and 
						// sets its attribute to “Passed”.

						EANFlagAttribute faSTATUS = (EANFlagAttribute) attSTATUS;
						hasPassed = true;
						result &= dv.setFlagByCode(
							mfaSTATUS.getAttributeCode(), 
							(String) statusTransitions.get(
								faSTATUS.getFirstActiveFlagCode()));
						
					}				
				}
				else {
					result = false;
					log.print(getClass().getName());
					log.print(signature);
					log.print(mfaSTATUS);
					log.println(" is null"); 
				}
			}
			else {
				result = false;
				log.print(getClass().getName());
				log.print(signature);
				log.print(mfaPDHDOMAIN);
				log.println(" is null"); 
			}
		}
		hasPassed &= result;
		return result;
	}

	/**
	 * Field accessor
	 * @return
	 */
	public boolean isChangeRequest() {
		return isChangeRequest;
	}

	/**
	 * Field accessor
	 * @return
	 */
	public boolean isChangeRequestChecksOK() {
		return isChangeRequestChecksOK;
	}

	/**
	 * Field accessor
	 * @return
	 */
	public boolean isDraft() {
		return isDraft;
	}

	/**
	 * Field accessor
	 * @return
	 */
	public boolean isDraftChecksOK() {
		return isDraftChecksOK;
	}

	/**
	 * Getter for the DATAQUALITY is Final test
	 * @return true if DATAQUALITY is Final
	 */
	public boolean isFinal() {
		return isFinal;
	}

	/**
	 * Field accessor
	 * @return
	 */
	public boolean isFinalChecksOK() {
		return isFinalChecksOK;
	}

	/**
	 * Getter for the PDHDOMAIN is ITS test
	 * @return true if PDHDOMAIN is ITS
	 */
	public boolean isITS() {
		return isITS;
	}

	/**
	 * Getter for the DATAQUALITY is Ready For Review test
	 * @return true if DATAQUALITY is Ready For Review
	 */
	public boolean isReadyForReview() {
		return isReadyForReview;
	}

	/**
	 * Field accessor
	 * @return
	 */
	public boolean isReadyForReviewChecksOK() {
		return isReadyForReviewChecksOK;
	}

	/**
	 * The class that implements one or more of the following interfaces
	 * DraftChecks, ReadyForReviewChecks, FinalChecks, ChangeRequestChecks
	 * @param aClass
	 */
	public void setCheckingClass(Object aClass) {
		checkingClass = aClass;
	}

	/**
     * Implements setter method of Data interface
     *
     * @param aDV
     * @return boolean
     */
	public boolean setDataView(DataView aDV) {
		boolean result = aDV != null;
		if (result) {
			dv = aDV;
			result = initialize();
		}
		return result;
	}

	/**
	 * Implements Log interface
	 * @param anIdentifier String
	 * @return boolean true if anIdentifier is not null
	 */
	public boolean setIdentifier(String anIdentifier) {
		return log.setIdentifier(anIdentifier);
	}

	private boolean setStatus(EANFlagAttribute fDATAQUALITY, boolean checksOK) {
		boolean result = false;
		if (checksOK) {
			// If the ABR checking is “Passed”, then the ABR will set STATUS = DATAQUALITY.
			String flagCode = (String) dataQualityToStatus.get(fDATAQUALITY.getFirstActiveFlagCode());
			status = mfaSTATUS.getMetaFlag(flagCode).getLongDescription();
			result = dv.setFlagValue(mfaSTATUS.getAttributeCode(), flagCode);
		}
		else {
			// If the ABR checking is “Failed”, then the ABR will set DATAQUALTY = “its prior value”.
			String flagCode = dv.getPriorFlagCode(mfaDATAQUALITY.getAttributeCode());
			if (FINAL.equals(flagCode)) {
				// Don't revert to final when checks fail
				flagCode = CHANGE;
			}
			if (flagCode != null) {
				dataQuality = mfaDATAQUALITY.getMetaFlag(flagCode).getLongDescription();
				result = dv.setFlagValue(mfaDATAQUALITY.getAttributeCode(), flagCode);
			}
		}
		return result;
	}

}
