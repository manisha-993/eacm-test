// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import COM.ibm.opicmpdh.middleware.*;
import java.util.*;

/**********************************************************************************
* SVCPRODSTRUCTABRSTATUS class
*
* From "SG FS ABR Data Quality 20080506.doc"
*
XXIX.   SVCPRODSTRUCT

A.  STATUS = Draft | Change Request

1.	CompareAll(SVCPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final) or 0040 (Ready for Review)
ErrorMessage LD(MODEL) 'Status is not Ready for Review or Final'
2.	CompareAll(SVCPRODSTRUCT-u: SVCFEATURE.STATUS) = 0020 (Final) or 0040 (Ready for Review)
ErrorMessage LD(SVCFEATURE) 'Status is not Ready for Review or Final'

B.  STATUS = Ready for Review

1.  All checks from 'STATUS = Draft | Change Request'
2.  CompareAll(SVCPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
ErrorMessage LD(MODEL) 'Status is not Final'
3.  CompareAll(SVCPRODSTRUCT-u: SVCFEATURE.STATUS) = 0020 (Final)
ErrorMessage LD(SVCFEATURE) 'Status is not Final'
4.   SVCPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
ErrorMessage LD(SVCPRODSTRUCT) NDN(SVCPRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE)

C.  STATUS changed to Ready for Review

1.  Set ADSABRSTATUS = 0020 (Queued)
D.  STATUS changed to Final

1.  Set ADSABRSTATUS = 0020 (Queued)

*
SVCPRODSTRUCTABRSTATUS_class=COM.ibm.eannounce.abr.sg.SVCPRODSTRUCTABRSTATUS
SVCPRODSTRUCTABRSTATUS_enabled=true
SVCPRODSTRUCTABRSTATUS_idler_class=A
SVCPRODSTRUCTABRSTATUS_keepfile=true
SVCPRODSTRUCTABRSTATUS_report_type=DGTYPE01
SVCPRODSTRUCTABRSTATUS_vename=DQVESWPRODSTRUCT
SVCPRODSTRUCTABRSTATUS_CAT1=RPTCLASS.SVCPRODSTRUCTABRSTATUS
SVCPRODSTRUCTABRSTATUS_CAT2=
SVCPRODSTRUCTABRSTATUS_CAT3=RPTSTATUS
SVCPRODSTRUCTABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
*/
// SVCPRODSTRUCTABRSTATUS.java,v
// Revision 1.7  2009/07/30 18:41:27  wendy
// Moved BH DQ ABRs to diff pkg
//
// Revision 1.5  2008/05/29 15:53:50  wendy
// Backout queueing ADSABRSTATUS
//
// Revision 1.4  2008/05/07 19:35:21  wendy
// remove already final processing
//
// Revision 1.3  2008/05/07 19:27:39  wendy
// Always get VE
//
// Revision 1.2  2008/05/06 21:23:24  wendy
// Changes for SG FS ABR Data Quality 20080506.doc
//
// Revision 1.1  2008/05/01 12:34:04  wendy
// Init DQ ABR
//
public class SVCPRODSTRUCTABRSTATUS extends DQABRSTATUS
{
	private Object args[] = new Object[3];

	/**********************************
	* always check if not final, but need navigation name from model and fc
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
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
D.	STATUS changed to Final

1.	Set ADSABRSTATUS = 0020 (Queued)

	*
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
//        setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
	}

    /**********************************
A.  STATUS = Draft | Change Request
1.	CompareAll(SVCPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final) or 0040 (Ready for Review)
ErrorMessage LD(MODEL) 'Status is not Ready for Review or Final'
2.	CompareAll(SVCPRODSTRUCT-u: SVCFEATURE.STATUS) = 0020 (Final) or 0040 (Ready for Review)
ErrorMessage LD(SVCFEATURE) 'Status is not Ready for Review or Final'

B.  STATUS = Ready for Review

1.  All checks from 'STATUS = Draft | Change Request'
2.  CompareAll(SVCPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
ErrorMessage LD(MODEL) 'Status is not Final'
3.  CompareAll(SVCPRODSTRUCT-u: SVCFEATURE.STATUS) = 0020 (Final)
ErrorMessage LD(SVCFEATURE) 'Status is not Final'
4.   SVCPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
ErrorMessage LD(SVCPRODSTRUCT) NDN(SVCPRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE)

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		if(STATUS_DRAFT.equals(statusFlag) || STATUS_CHGREQ.equals(statusFlag)) // 'Draft or Ready for Review'
		{
	        EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0); // has to exist
	        EntityItem fcItem = m_elist.getEntityGroup("SVCFEATURE").getEntityItem(0); // has to exist

			//3.	CompareAll(SVCPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final) or 0040 (Ready for Review)
			//ErrorMessage LD(MODEL) 'Status is not Ready for Review or Final'
			String status = getAttributeFlagEnabledValue(mdlItem , "STATUS");
			addDebug(mdlItem.getKey()+" check status "+status);
			if (status==null){
				status = STATUS_FINAL;
			}

			if (!STATUS_FINAL.equals(status) && !STATUS_R4REVIEW.equals(status)){
				addDebug(mdlItem.getKey()+" is not Final or R4R");
				//NOT_R4R_FINAL_ERR = {0} {1} is not Ready for Review or Final.
				args[0] = mdlItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(mdlItem);
				addError("NOT_R4R_FINAL_ERR",args);
			}
			//4.	CompareAll(SVCPRODSTRUCT-u: SVCFEATURE.STATUS) = 0020 (Final) or 0040 (Ready for Review)
			//ErrorMessage LD(SVCFEATURE 'Status is not Ready for Review or Final'
			status = getAttributeFlagEnabledValue(fcItem , "STATUS");
			addDebug(fcItem.getKey()+" check status "+status);
			if (status==null){
				status = STATUS_FINAL;
			}

			if (!STATUS_FINAL.equals(status) && !STATUS_R4REVIEW.equals(status)){
				addDebug(fcItem.getKey()+" is not Final or R4R");
				//NOT_R4R_FINAL_ERR = {0} {1} is not Ready for Review or Final.
				args[0] = fcItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(fcItem);
				addError("NOT_R4R_FINAL_ERR",args);
			}
		}

		if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
		{
			//2.CompareAll(SVCPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
			//ErrorMessage LD(MODEL) ' is not Final'
			checkStatus("MODEL");

			//3.CompareAll(SVCPRODSTRUCT-d: SVCFEATURE.STATUS) = 0020 (Final)
			//ErrorMessage LD(SVCFEATURE) ' is not Final'
			checkStatus("SVCFEATURE");

			//4.SVCPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
			//ErrorMessage LD(SVCPRODSTRUCT) NDN(SVCPRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE)
			checkAnnDate();
		}
	}

    /**********************************
//4.   SVCPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
//ErrorMessage LD(SVCPRODSTRUCT) NDN(SVCPRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE)
    */
	private void checkAnnDate()
		throws java.sql.SQLException, MiddlewareException
	{
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
    	String annDate = PokUtils.getAttributeValue(mdlItem, "ANNDATE",", ", "", false);
		addDebug("checkAnnDate "+mdlItem.getKey()+" ("+annDate+")");

		if (annDate.length()>0){
			// look at avails
			EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
			Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availGrp, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability

			for (int ai=0; ai<plannedavailVector.size(); ai++){ // look at avails
				EntityItem avail = (EntityItem)plannedavailVector.elementAt(ai);
				addDebug("checkAnnDate looking for planned avail; checking: "+avail.getKey());
				String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
				addDebug("checkAnnDate plannedavail "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
				if (effDate.length()>0 && annDate.compareTo(effDate)>0){
					//EARLY_PLANNEDAVAIL_ERR = has a Planned Availability earlier than the {0} {1}
					args[0] = mdlItem.getEntityGroup().getLongDescription();
					args[1] = PokUtils.getAttributeDescription(mdlItem.getEntityGroup(), "ANNDATE", "ANNDATE");
					addError("EARLY_PLANNEDAVAIL_ERR",args);
				}
			}
		}
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SVCPRODSTRUCT ABR";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.7";
    }
}
