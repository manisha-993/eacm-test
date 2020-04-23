// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
AVAILABRSTATUS_class=COM.ibm.eannounce.abr.sg.AVAILABRSTATUS
AVAILABRSTATUS_enabled=true
AVAILABRSTATUS_idler_class=A
AVAILABRSTATUS_keepfile=true
AVAILABRSTATUS_read_only=true
AVAILABRSTATUS_report_type=DGTYPE01
AVAILABRSTATUS_vename=EXRPT3AVAILDQ

 *
 * AVAILABRSTATUS.java,v
 * Revision 1.13  2009/02/05 13:42:49  wendy
 * CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
 *
 * Revision 1.12  2008/09/22 15:10:00  wendy
 * CQ00006066-WI updates
 *
 * Revision 1.11  2008/08/04 17:07:39  wendy
 * Only queue entities thru specific relators when gone to final
 *
 * Revision 1.10  2008/08/04 15:49:28  wendy
 * MN36455631 only check FEATURE.COUNTRYLIST thru OOFAVAIL
 *
 * Revision 1.9  2008/05/27 14:18:52  wendy
 * Clean up RSA warnings
 *
 * Revision 1.8  2008/05/07 14:41:28  wendy
 * Changes for SG FS ABR Data Quality 20080506.doc
 *
 * Revision 1.7  2008/03/13 18:21:01  wendy
 * Correct country check, root must be subset in some cases
 *
 * Revision 1.6  2008/02/13 19:58:49  wendy
 * Make ABRWAITODSx into constants, allow easier change in future
 *
 * Revision 1.5  2008/01/21 17:26:55  wendy
 * Default null status to final
 *
 * Revision 1.4  2007/12/12 17:18:16  couto
 * Chgs for spec "SG FS ABR Data Quality 20071211.doc"
 *
 * Revision 1.3  2007/11/28 11:42:32  couto
 * Chgs for spec "SG FS ABR Data Quality 20071127.doc"
 *
 * Revision 1.2  2007/11/27 17:57:35  couto
 * Chgs for spec "SG FS ABR Data Quality 20071115.doc"
 *
 * Revision 1.1  2007/09/14 17:41:32  wendy
 * New GX DQ ABR
 *
 */
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import java.util.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
* AVAILABRSTATUS class
*
* From "SG FS ABR Data Quality 20080916.doc"
* A.    STATUS = Draft | Change
*
* Criteria = None
*
* B.    STATUS = Ready for Review
*
1.	AllValuesOf(AVAIL.COUNTRYLIST) IN (LSEOAVAIL-u: LSEO.COUNTRYLIST)
ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) NDN(LSEO) LD(COUNTRYLIST).
2.	AllValuesOf(AVAIL.COUNTRYLIST) IN (LSEOBUNDLEAVAIL-u: LSEOBUNDLE.COUNTRYLIST)
ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST).
3.	AllValuesOf(AVAIL.COUNTRYLIST) IN (OOFAVAIL-u: PRODSTRUCT-u: FEATURE.COUNTRYLIST)
ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(FEATURE) NDN(PRODSTRUCT) LD(COUNTRYLIST).

* Virtual Entity
* Lev	Entity1	RelType	Relator	Entity2	Dir
* 0	LSEO		Relator	LSEOAVAIL	AVAIL	U
* 0	LSEOBUNDLE	Relator	LSEOBUNDLEAVAIL	AVAIL	U
* 0 PRODSTRUCT	Relator OOFAVAIL	AVAIL	U
* 0 MODEL		Relator MODELAVAIL	AVAIL	U
*/
public class AVAILABRSTATUS extends DQABRSTATUS
{
    /**********************************
    * ready4review
    */
    protected boolean isVEneeded(String statusFlag) {
		return STATUS_R4REVIEW.equals(statusFlag);
    }

    /**********************************
	* complete abr processing after status moved to final; (status was r4r)

SVCPRODSTRUCTAVAIL	SVCPRODSTRUCT	AVAIL
SWPRODSTRUCTAVAIL	SWPRODSTRUCT	AVAIL

C.	STATUS changed to Final

1.	IF (AVAIL: OOFAVAIL-u: PRODSTRUCT. STATUS) = 0020 (Final) then
	Set (AVAIL: OOFAVAIL-u: PRODSTRUCT. PRODSTRUCTABRSTATUS) = 0020 (Queued)
new2.	IF (AVAIL: SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT. STATUS) = 0020 (Final) then
	Set (AVAIL: SWPRODSTRUCTAVAIL-u: SWPRODSTRUCT. SWPRODSTRUCTABRSTATUS) = 0020 (Queued)
new3.	IF (AVAIL: SVCPRODSTRUCTAVAIL-u: SVCPRODSTRUCT. STATUS) = 0020 (Final) then
	Set (AVAIL: SVCPRODSTRUCTAVAIL-u: SVCPRODSTRUCT. SVCPRODSTRUCTABRSTATUS) = 0020 (Queued)
4.	IF (AVAIL: MODELAVAIL-u: MODEL. STATUS) = 0020 (Final) then
	Set (AVAIL: MODELAVAIL-u: MODEL. MODELABRSTATUS) = 0020 (Queued)
5.	IF (AVAIL: LSEOAVAIL-u: LSEO. STATUS) = 0020 (Final) then
	Set (AVAIL: LSEOAVAIL-u: LSEO. LSEOABRSTATUS) = 0020 (Queued)
6.	IF (AVAIL: LSEOBUNDLEAVAIL-u: LSEOBUNDLE. STATUS) = 0020 (Final) then
	Set (AVAIL: LSEOBUNDLEAVAIL-u: LSEOBUNDLE. LSEOBDLABRSTATUS) = 0020 (Queued)
7.	IF AVAILTYPE =  146 (Planned Availability) and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final)
	and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	Set (AVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2
//CQ00006066-WI 
8.	IF AVAILTYPE =  146 (Planned Availability) and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final) 
	and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then 
	Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report) 
9.	IF AVAILTYPE =  149 (Last Order) and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final) 
	and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) =  14 ("End Of Life - Withdrawal from mktg") then 
	Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)

	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		verifyStatusAndSetABRStatus("OOFAVAIL","PRODSTRUCT", "PRODSTRUCTABRSTATUS");
		verifyStatusAndSetABRStatus("SWPRODSTRUCTAVAIL","SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
		verifyStatusAndSetABRStatus("SVCPRODSTRUCTAVAIL","SVCPRODSTRUCT", "SVCPRODSTRUCTABRSTATUS");
		verifyStatusAndSetABRStatus("MODELAVAIL","MODEL", "MODELABRSTATUS");
		verifyStatusAndSetABRStatus("LSEOAVAIL","LSEO", "LSEOABRSTATUS");
		verifyStatusAndSetABRStatus("LSEOBUNDLEAVAIL","LSEOBUNDLE", "LSEOBDLABRSTATUS");

        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		String availType = getAttributeFlagEnabledValue(rootEntity, "AVAILTYPE");
		addDebug(rootEntity.getKey()+" type "+availType);
		//IF AVAILTYPE =  146 (Planned Availability) or 149 (Last Order) 
		if ("146".equals(availType) || "149".equals(availType)) { //CQ00006066-WI
			EntityGroup egrp = m_elist.getEntityGroup("ANNOUNCEMENT");
			for (int i = 0; i < egrp.getEntityItemCount(); i++) {
				EntityItem ei = egrp.getEntityItem(i);
				String status = getAttributeFlagEnabledValue(ei, "ANNSTATUS");
				String annType = getAttributeFlagEnabledValue(ei, "ANNTYPE");
				addDebug(ei.getKey() + " status " + status + " type " + annType);
				if (status==null || status.length()==0){
					status = STATUS_FINAL;
				}
				//and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final)
				//and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New)
				if (STATUS_FINAL.equals(status)) {					
					if ("146".equals(availType)&& "19".equals(annType)) { //PlannedAvail and New ann
						addDebug(ei.getKey()+" is Final and New");
						setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2, ei);
						//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK, ei);//CQ00006066-WI
						//CQ00016165
						setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), ei);
					}
					if ("149".equals(availType)&& "14".equals(annType)) { // LastOrderAvail and EOL ann CQ00006066-WI
						addDebug(ei.getKey()+" is Final and EOL");
						//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK, ei);
						// CQ00016165
						setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), ei);
					}
				}
			}
		}
	}

	/**
	 * Verify the entity STATUS and set the ABRSTATUS thru a particular relator
	 */
	private void verifyStatusAndSetABRStatus(String reltype, String entityType, String ABRStatus) {
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		Vector vct =PokUtils.getAllLinkedEntities(rootEntity, reltype, entityType);
		for (int i = 0; i < vct.size(); i++) {
			EntityItem ei = (EntityItem)vct.elementAt(i);
			String status = getAttributeFlagEnabledValue(ei, "STATUS");
			addDebug(ei.getKey()+" status "+status);
			if (status==null || status.length()==0){
				status = STATUS_FINAL;
			}
			if (STATUS_FINAL.equals(status)){
				setFlagValue(m_elist.getProfile(),ABRStatus, ABR_QUEUED, ei);
			}
		}
	}

    /**********************************
    * Note the ABR is only called when
    * DATAQUALITY transitions from 'Draft to Ready for Review',
    *   'Change Request to Ready for Review' and from 'Ready for Review to Final'
    */
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		//'Draft to Ready for Review' or 'Change Request to Ready for Review'
		if(STATUS_DRAFT.equals(statusFlag) || STATUS_CHGREQ.equals(statusFlag))
		{
			//Advance STATUS to match DATAQUALITY (diff flag codes)
			addDebug("No checking required, status is draft or change request");
		}
		else if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
		{
			//Status is Ready for Review
			// AllValuesOf(AVAIL.COUNTRYLIST) IN (LSEOAVAIL-u: LSEO.COUNTRYLIST)
			// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) NDN(LSEO) LD(COUNTRYLIST).
			addDebug("Checking LSEOAVAIL countries");
			checkCountry("LSEOAVAIL","U", false);
			// AllValuesOf(AVAIL.COUNTRYLIST) IN (LSEOBUNDLEAVAIL-u: LSEOBUNDLE.COUNTRYLIST)
			// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST).
			addDebug("Checking LSEOBUNDLEAVAIL countries");
			checkCountry("LSEOBUNDLEAVAIL","U", false);
			//3.	AllValuesOf(AVAIL.COUNTRYLIST) IN (OOFAVAIL-u: PRODSTRUCT-u: FEATURE.COUNTRYLIST)
			//ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(FEATURE) NDN(PRODSTRUCT) LD(COUNTRYLIST).
			checkFeatureCountry();
		}

    }

    /**********************************************************************************
3.	AllValuesOf(AVAIL.COUNTRYLIST) IN (OOFAVAIL-u: PRODSTRUCT-u: FEATURE.COUNTRYLIST)
ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(FEATURE) NDN(PRODSTRUCT) LD(COUNTRYLIST).
    */
    private void checkFeatureCountry() throws java.sql.SQLException, MiddlewareException
    {
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

    	ArrayList rootCtry = new ArrayList();
		//get the list of countries from the announcement
		EANFlagAttribute rootCtrylist = (EANFlagAttribute)rootEntity.getAttribute("COUNTRYLIST");
		if (rootCtrylist != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) rootCtrylist.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					rootCtry.add(mfArray[i].getFlagCode());
				}
			}
		}

	    addDebug("checkFeatureCountry "+rootEntity.getKey()+" countries "+rootCtry);

		//Look at each FEATURE thru OOFAVAIL only
	    Vector vct =PokUtils.getAllLinkedEntities(m_elist.getEntityGroup("OOFAVAIL"), "PRODSTRUCT", "FEATURE");
		for (int i = 0; i < vct.size(); i++) {
			EntityItem fcitem = (EntityItem)vct.elementAt(i);
			checkCountry(rootEntity, fcitem, rootCtry);
		}// end each feature

		vct.clear();

		rootCtry.clear();
	}

	private void checkCountry(EntityItem rootEntity, EntityItem depitem,ArrayList rootCtry)
	throws java.sql.SQLException, MiddlewareException
	{
		Object[] args = new String[6];
		EntityGroup depGrp = depitem.getEntityGroup();
		ArrayList depCtry = new ArrayList();

		EANFlagAttribute ctrylist = (EANFlagAttribute)depitem.getAttribute("COUNTRYLIST");
		if (ctrylist != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
			for (int im = 0; im < mfArray.length; im++) {
				// get selection
				if (mfArray[im].isSelected()) {
					depCtry.add(mfArray[im].getFlagCode());
				}
			} //end for
		} //end of null chk

		addDebug("checkCountry "+depitem.getKey()+" countries "+depCtry);

		//if ann does not have all avails countries, it is an error
		// root countries must be a subset of other item countries
		boolean	ok = depCtry.containsAll(rootCtry);

		if (!ok) {
 			// root must be subset of item
			args[0] = "";//info is repeated rootEntity.getEntityGroup().getLongDescription();
			args[1] = "";//getNavigationName(rootEntity);
			args[2] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			args[3] = depGrp.getLongDescription();
			args[4] = getNavigationName(depitem);
			args[5] = PokUtils.getAttributeDescription(depGrp, "COUNTRYLIST", "COUNTRYLIST");

			addError("COUNTRY_MISMATCH_ERR",args);
		}

		depCtry.clear();
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "AVAIL ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.13";
    }
}
