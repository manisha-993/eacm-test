// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
SWFCABRSTATUS_class=COM.ibm.eannounce.abr.sg.SWFCABRSTATUS
SWFCABRSTATUS_enabled=true
SWFCABRSTATUS_idler_class=A
SWFCABRSTATUS_keepfile=true
SWFCABRSTATUS_read_only=true
SWFCABRSTATUS_report_type=DGTYPE01
SWFCABRSTATUS_vename=EXRPT3SWFEATURE1
SWFCABRSTATUS_CAT1=RPTCLASS.SWFCABRSTATUS
SWFCABRSTATUS_CAT2=
SWFCABRSTATUS_CAT3=RPTSTATUS
SWFCABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390


 *
 * SWFCABRSTATUS.java,v
 * Revision 1.23  2009/07/30 18:41:27  wendy
 * Moved BH DQ ABRs to diff pkg
 *
 * Revision 1.21  2008/05/29 15:53:50  wendy
 * Backout queueing ADSABRSTATUS
 *
 * Revision 1.20  2008/05/01 12:02:46  wendy
 * updates for SG FS ABR Data Quality 20080430.doc
 *
 * Revision 1.19  2008/01/30 19:39:15  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.18  2007/12/19 17:36:52  wendy
 * do domain check for ve here now
 *
 * Revision 1.17  2007/10/25 18:16:20  wendy
 * Added comments
 *
 * Revision 1.16  2007/10/23 17:47:12  wendy
 * Spec changes
 *
 * Revision 1.15  2007/09/14 17:43:55  wendy
 * Updated for GX
 *
 * Revision 1.14  2007/08/17 16:02:10  wendy
 * RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
 * from 'SG FS xSeries ABRs 20070803.doc'
 *
 * Revision 1.13  2006/03/16 18:24:21  anhtuan
 * Fix null pointer exception.
 *
 * Revision 1.12  2006/03/14 05:01:12  anhtuan
 * Fix Jtest. Jtest does not allow multiple returns from a method.
 *
 * Revision 1.11  2006/03/10 17:08:33  anhtuan
 * Updated Specs 30b FS xSeries ABRs 20060309.doc.
 *
 * Revision 1.10  2006/02/25 21:23:58  anhtuan
 * Remove redundant stuff.
 *
 * Revision 1.9  2006/02/23 03:33:53  anhtuan
 * AHE compliant.
 *
 * Revision 1.8  2006/02/22 04:00:59  anhtuan
 * Use PokUtils.
 *
 * Revision 1.7  2006/01/27 16:29:41  anhtuan
 * Check for null values of DATAQUALITY and/or STATUS.
 *
 * Revision 1.6  2006/01/26 15:03:04  anhtuan
 * AHE copyright.
 *
 * Revision 1.5  2006/01/26 01:59:48  anhtuan
 * Updated specs.
 *
 * Revision 1.3  2005/08/18 00:01:13  anhtuan
 * Fix resource file problem.
 *
 * Revision 1.2  2005/08/04 23:24:18  anhtuan
 * Use resource file.
 *
 * Revision 1.1  2005/07/19 16:20:52  anhtuan
 * Initial version.
 *
 *
 *@author     Anhtuan Nguyen
 *@created    July 1, 2005
 */
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import java.util.*;

/**********************************************************************************
* SWFCABRSTATUS class
*
* from SG FS ABR Data Quality 20071024.doc
*
* A.	STATUS = Draft | Change Request
*
*None
*
*B.	STATUS = Ready for Review
*
*1.	IF ValueOf(SWFEATURE.FCTYPE) = 140 (PRPQ) THEN ELSE IF NotNull(SWFEATURE. WITHDRAWDATEEFF_T ) THEN
*a.	CompareAll(SWPRODSTUCT: SWPRODSTRUCTAVAIL-d: AVAIL.EFFECTIVEDATE) <= SWFEATURE.WITHDRAWDATEEFF_T
*		WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
*ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) LD(WITHDRAWDATEEFF_T) 'is earlier than' LD(AVAIL) NDN(AVAIL)
*b.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
*		IN (SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
*ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) LD(WITHDRAWDATEEFF_T) 'exists and is available in a Country that does not have a Last Order' LD(AVAIL).
*
C.	STATUS changed to Ready for Review

1.	Set ADSABRSTATUS = 0020 (Queued)

D.	STATUS changed to Final

1.	Set ADSABRSTATUS = 0020 (Queued)

*/
public class SWFCABRSTATUS extends DQABRSTATUS
{
	/**********************************
	* must be xseries or convergedproduct and ready4review
	*/
	protected boolean isVEneeded(String statusFlag) {
		return domainInList() && STATUS_R4REVIEW.equals(statusFlag);
	}

    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
	* C.	Status changed to Ready for Review
1.	Set ADSABRSTATUS = 0020 (Queued)
    */
    protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
//         setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
    }

    /**********************************
    * complete abr processing after status moved to final; (status was r4r)
    *D. STATUS changed to Final
    *
    *1.	Set ADSABRSTATUS = 0020 (Queued)
    */
    protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
//		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
    }

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*1.	IF ValueOf(SWFEATURE.FCTYPE) = 140 (PRPQ) THEN ELSE IF NotNull(SWFEATURE. WITHDRAWDATEEFF_T ) THEN
	*a.	CompareAll(SWPRODSTUCT: SWPRODSTRUCTAVAIL-d: AVAIL.EFFECTIVEDATE) <= SWFEATURE.WITHDRAWDATEEFF_T
	*		WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	*ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) LD(WITHDRAWDATEEFF_T) 'is earlier than' LD(AVAIL) NDN(AVAIL)
	*b.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
	*		IN (SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST) WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	*ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) LD(WITHDRAWDATEEFF_T) 'exists and is available in a Country that does not have a Last Order' LD(AVAIL).
	*
	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		if (STATUS_R4REVIEW.equals(statusFlag)){
			// check FCTYPE
			Set fctypeSet = new HashSet();
			fctypeSet.add("140");
			//(SWFEATURE.FCTYPE) = 140 (PRPQ)
			if (!PokUtils.contains(rootEntity, "FCTYPE", fctypeSet)){
				addDebug(rootEntity.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));
				checkLastOrderAvailCountry(rootEntity, "WITHDRAWDATEEFF_T");
			}else{
				addDebug(rootEntity.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));
			}
			fctypeSet.clear();

		}else{
        	addDebug("No checking required");
		}
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SWFEATURE ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.23";
    }
}
