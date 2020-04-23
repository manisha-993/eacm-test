// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
FCABRSTATUS_class=COM.ibm.eannounce.abr.sg.FCABRSTATUS
FCABRSTATUS_enabled=true
FCABRSTATUS_idler_class=A
FCABRSTATUS_keepfile=true
FCABRSTATUS_read_only=true
FCABRSTATUS_report_type=DGTYPE01
FCABRSTATUS_vename=EXRPT3FEATURE1
FCABRSTATUS_CAT1=RPTCLASS.FCABRSTATUS
FCABRSTATUS_CAT2=
FCABRSTATUS_CAT3=RPTSTATUS
FCABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390

 *
 * FCABRSTATUS.java,v
 * Revision 1.25  2009/07/30 18:41:27  wendy
 * Moved BH DQ ABRs to diff pkg
 *
 * Revision 1.23  2008/05/29 15:53:50  wendy
 * Backout queueing ADSABRSTATUS
 *
 * Revision 1.22  2008/05/01 12:02:45  wendy
 * updates for SG FS ABR Data Quality 20080430.doc
 *
 * Revision 1.21  2008/04/24 12:08:45  wendy
 * "SG FS ABR Data Quality 20080422.doc" spec updates
 *
 * Revision 1.20  2008/01/30 19:39:16  wendy
 * Cleanup RSA warnings
 *
 * Revision 1.19  2007/12/19 17:36:52  wendy
 * do domain check for ve here now
 *
 * Revision 1.18  2007/11/27 22:14:16  wendy
 * SG FS ABR Data Quality 20071127.doc chgs
 *
 * Revision 1.17  2007/10/25 18:15:03  wendy
 * Added comments
 *
 * Revision 1.16  2007/10/23 17:47:12  wendy
 * Spec changes
 *
 * Revision 1.15  2007/09/14 17:43:55  wendy
 * Updated for GX
 *
 * Revision 1.14  2007/08/17 16:02:09  wendy
 * RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
 * from 'SG FS xSeries ABRs 20070803.doc'
 *
 * Revision 1.13  2006/03/16 18:24:20  anhtuan
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
 * Revision 1.9  2006/02/23 03:33:52  anhtuan
 * AHE compliant.
 *
 * Revision 1.8  2006/02/22 04:00:59  anhtuan
 * Use PokUtils.
 *
 * Revision 1.7  2006/01/27 16:29:40  anhtuan
 * Check for null values of DATAQUALITY and/or STATUS.
 *
 * Revision 1.6  2006/01/26 14:59:17  anhtuan
 * AHE copyright.
 *
 * Revision 1.5  2006/01/26 01:57:58  anhtuan
 * Updated specs.
 *
 * Revision 1.3  2005/08/18 00:00:06  anhtuan
 * Fix resource file problem.
 *
 * Revision 1.2  2005/08/04 23:22:50  anhtuan
 * Use resource file.
 *
 * Revision 1.1  2005/07/19 16:20:16  anhtuan
 * Initial version.
 *
 *
 *
 *
 * </pre>
 *
 *@author     Anhtuan Nguyen
 *@created    July 1, 2005
 */
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import java.util.*;

/**********************************************************************************
* FCABRSTATUS class
* From "SG FS ABR Data Quality 20080422.doc"
*
* A.	STATUS = Draft | Change
*
* Criteria
* 1.	None
*
* B.	STATUS = Ready for Review
*
* Criteria
* 1.	FEATUREMONITOR.QTY * CountOf( FEATUREMONITOR-d: MONITOR)  < = 1
* ErrorMessage  'has more than one' LD(MON)
* 2.	IF ValueOf(FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED) | 402 (Placeholder) THEN
* 		ELSE IF NotNull(FEATURE. WITHDRAWDATEEFF_T ) THEN
* 			a.	CompareAll(PRODSTRUCT: OOFAVAIL-d: AVAIL) <= FEATURE. WITHDRAWDATEEFF_T WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
* 			ErrorMessage LD(FEATURE) NDN(FEATURE) LD(WITHDRAWDATEEFF_T) 'is earlier than' LD(AVAIL) NDN(AVAIL)
* 			b.	AllValuesOf(PRODSTRUCT: OOFAVAIL-d: AVAIL WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
* 			ErrorMessage LD(FEATURE) NDN(FEATURE) LD(WITHDRAWDATEEFF_T) 'exists and is available in a Country that does not have a Last Order' LD(AVAIL).
*C.	Status changed to Ready for Review

1.	Set ADSABRSTATUS = 0020 (Queued)
D.	Status changed to Final

1.	Set ADSABRSTATUS = 0020 (Queued)
E.	STATUS = Final

Data Quality checks are not required

*/
public class FCABRSTATUS extends DQABRSTATUS
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
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        addDebug(rootEntity.getKey()+" status now final");
//		setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
    }

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*/
	protected void doDQChecking(EntityItem rootEntity, String status) throws Exception
    {
		//'Draft to Ready for Review' or 'Change Request to Ready for Review'
		if(STATUS_DRAFT.equals(status) || STATUS_CHGREQ.equals(status))
		{
			//Advance STATUS to match DATAQUALITY (diff flag codes)
            addDebug("No checking required, status is draft or change request");
		}
		else if(STATUS_R4REVIEW.equals(status)) // 'Ready for Review to Final'
		{
			//Status is Ready for Review
			//1.	FEATUREMONITOR.QTY * CountOf( FEATUREMONITOR-d: MONITOR)  < = 1
			int cnt = getCount("FEATUREMONITOR");
			if(cnt > 1)	{
				EntityGroup monGrp = m_elist.getEntityGroup("MONITOR");
				//ErrorMessage  has more than one LD(MONITOR)
				//MORE_THAN_ONE_ERR = Has more than one {0}
				Object args[] = new Object[]{monGrp.getLongDescription()};
				addError("MORE_THAN_ONE_ERR",args);
			}

			// check FCTYPE
			Set fctypeSet = new HashSet();
			fctypeSet.add("120");
			fctypeSet.add("130");
			fctypeSet.add("402");
			//(FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED) | 402 (Placeholder)
			if (!PokUtils.contains(rootEntity, "FCTYPE", fctypeSet)){
				addDebug(rootEntity.getKey()+" was NOT an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));
				checkLastOrderAvailCountry(rootEntity, "WITHDRAWDATEEFF_T");
			}else{
				addDebug(rootEntity.getKey()+" was an RPQ FCTYPE: "+getAttributeFlagEnabledValue(rootEntity, "FCTYPE"));
			}

			fctypeSet.clear();
		}//end of else if(STATUS_R4REVIEW.equals(status))
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "FEATURE ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.25";
    }
}
