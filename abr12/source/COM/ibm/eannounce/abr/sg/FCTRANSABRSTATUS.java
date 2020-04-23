// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import java.util.*;

/**********************************************************************************
* FCTRANSABRSTATUS class
*
* From "SG FS ABR Data Quality 20080422.doc"
*
A.	STATUS = Draft | Change Request

1.	CountOf(FEATURETRNAVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
ErrorMessage 'must have at least one Planned Availability'

B.	STATUS = Ready for Review

1.	All of the checks for 'STATUS = Draft | Change Request'
2.	AllValuesOf(FEATURETRNAVAIL -d: AVAIL.EFFECTIVEDATE) => FCTRANSACTION.ANNDATE WHERE AVAIL.AVAILTYPE = 146 (Planned Availability) OR 143 (First Order)
ErrorMessage LD(FCTRANSACTION) NDN(FCTRANSACTION) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)

C.	Status changed to Ready for Review
1.	Set ADSABRSTATUS = 0020 (Queued)
D.	Status changed to Final

1.	Set ADSABRSTATUS = 0020 (Queued)
E.	STATUS = Final

Data Quality checks are not required

*
FCTRANSABRSTATUS_class=COM.ibm.eannounce.abr.sg.FCTRANSABRSTATUS
FCTRANSABRSTATUS_enabled=true
FCTRANSABRSTATUS_idler_class=A
FCTRANSABRSTATUS_keepfile=true
FCTRANSABRSTATUS_read_only=true
FCTRANSABRSTATUS_report_type=DGTYPE01
FCTRANSABRSTATUS_vename=ADSFCTRANSACTION
FCTRANSABRSTATUS_CAT1=RPTCLASS.FCTRANSABRSTATUS
FCTRANSABRSTATUS_CAT2=
FCTRANSABRSTATUS_CAT3=RPTSTATUS
FCTRANSABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
*/
// FCTRANSABRSTATUS.java,v
// Revision 1.5  2009/07/30 18:41:27  wendy
// Moved BH DQ ABRs to diff pkg
//
// Revision 1.3  2008/05/29 15:53:50  wendy
// Backout queueing ADSABRSTATUS
//
// Revision 1.2  2008/05/01 12:02:45  wendy
// updates for SG FS ABR Data Quality 20080430.doc
//
// Revision 1.1  2008/04/29 12:34:32  wendy
// Init DQ ABRs
//
public class FCTRANSABRSTATUS extends DQABRSTATUS
{
    private Object args[] = new Object[5];

    /**********************************
    * always needed
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
    *C. STATUS changed to Final
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
    *   'Change Request to Ready for Review' and from 'Ready for Review to Final'
    */
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		// get AVAIL from FEATURETRNAVAIL
		Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "FEATURETRNAVAIL", "AVAIL");

		// 1.	CountOf(FEATURETRNAVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
		// ErrorMessage 'must have at least one Planned Availability'
		Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
		addDebug("checkAvailDates "+rootEntity.getKey()+" availVct "+availVct.size()+" plannedavailVector "+plannedavailVector.size());
		if (plannedavailVector.size()==0)
		{
			//MINIMUM_ERR = must have at least one {0}
			args[0] = "Planned Availability";
			addError("MINIMUM_ERR",args);
		}

        if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
        {
            //1.	All of the checks for 'STATUS = Draft | Change Request'
			//2.	AllValuesOf(FEATURETRNAVAIL -d: AVAIL.EFFECTIVEDATE) => FCTRANSACTION.ANNDATE WHERE AVAIL.AVAILTYPE = 146 (Planned Availability) OR 143 (First Order)
			//ErrorMessage LD(FCTRANSACTION) NDN(FCTRANSACTION) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
            checkAvailDates(rootEntity,availVct);
        }//end of r4r
        availVct.clear();
        plannedavailVector.clear();
    }

    /***********************************************
	*2.	AllValuesOf(FEATURETRNAVAIL -d: AVAIL.EFFECTIVEDATE) => FCTRANSACTION.ANNDATE
	* WHERE AVAIL.AVAILTYPE = 146 (Planned Availability) OR 143 (First Order)
	*ErrorMessage LD(FCTRANSACTION) NDN(FCTRANSACTION) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
	*/
	private void checkAvailDates(EntityItem rootEntity,Vector availVct) throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		String annDate = getAttributeValue(rootEntity, "ANNDATE", "");
		addDebug("checkAvailDates "+rootEntity.getKey()+" ANNDATE: "+annDate+" availVct: "+availVct.size());

		for (int ai=0; ai<availVct.size(); ai++){ // look at avails
			EntityItem avail = (EntityItem)availVct.elementAt(ai);
			String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
			String availtype = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
			addDebug("checkAvailDates "+avail.getKey()+" EFFECTIVEDATE: "+effDate+" AVAILTYPE: "+availtype);
			// 2.	AllValuesOf(FEATURETRNAVAIL-d: AVAIL.EFFECTIVEDATE) => FCTRANSACTION.ANNDATE where
			//	 	AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
			if((PLANNEDAVAIL.equals(availtype) || FIRSTORDERAVAIL.equals(availtype))
				&& effDate.length()>0 && effDate.compareTo(annDate)<0){
				// LD(FCTRANSACTION) NDN(FCTRANSACTION) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
				//LATER_DATE_ERR = {0} {1} {2} is later than the {3} {4}
				args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "ANNDATE", "ANNDATE");
				args[1] = "";
				args[2] = "";
				args[3] = avail.getEntityGroup().getLongDescription();
				args[4] = getNavigationName(avail);
				addError("LATER_DATE_ERR",args);
			}
		}
	}

    /**********************************
    * class has a different status attribute
    */
    protected String getStatusAttrCode() { return "STATUS";}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "FCTRANSACTION ABR.";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.5";
    }
}
