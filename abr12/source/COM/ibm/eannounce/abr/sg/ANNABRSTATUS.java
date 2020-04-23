// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
/**
ANNABRSTATUS_class=COM.ibm.eannounce.abr.sg.ANNABRSTATUS
ANNABRSTATUS_enabled=true
ANNABRSTATUS_idler_class=A
ANNABRSTATUS_keepfile=true
ANNABRSTATUS_read_only=true
ANNABRSTATUS_report_type=DGTYPE01
ANNABRSTATUS_vename=EXRPT3ANNDQ
ANNABRSTATUS_CAT1=RPTCLASS.ANNABRSTATUS
ANNABRSTATUS_CAT2=
ANNABRSTATUS_CAT3=RPTSTATUS
ANNABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390

 *
 * ANNABRSTATUS.java,v
 * Revision 1.35  2009/02/05 13:42:49  wendy
 * CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
 *
 * Revision 1.34  2008/09/22 15:10:57  wendy
 * CQ00006066-WI updates
 *
 * Revision 1.33  2008/04/24 19:25:14  wendy
 * Added check for ANNDATE exists
 *
 * Revision 1.32  2008/04/24 11:47:26  wendy
 * "SG FS ABR Data Quality 20080422.doc" spec updates
 *
 * Revision 1.31  2008/03/13 18:21:01  wendy
 * Correct country check, root must be subset in some cases
 *
 * Revision 1.30  2008/02/13 19:58:49  wendy
 * Make ABRWAITODSx into constants, allow easier change in future
 *
 * Revision 1.29  2008/01/21 17:26:55  wendy
 * Default null status to final
 *
 * Revision 1.28  2008/01/20 23:22:22  wendy
 * Updates for 'SG FS ABR Data Quality 20080118.doc'
 *
 * Revision 1.27  2007/12/12 17:18:16  couto
 * Chgs for spec "SG FS ABR Data Quality 20071211.doc"
 *
 * Revision 1.26  2007/11/28 16:47:35  couto
 * Chgs for spec "SG FS ABR Data Quality 20071127.doc"
 *
 * Revision 1.25  2007/11/26 13:56:20  wendy
 * removed status check from completeNowFinalProcessing
 *
 * Revision 1.24  2007/11/16 22:17:22  nancy
 * Chgs for spec "SG FS ABR Data Quality 20071115.doc"
 *
 * Revision 1.23  2007/10/26 12:18:30  couto
 * Updates per spec changes.
 *
 * Revision 1.22  2007/10/24 21:01:37  wendy
 * Use base class method
 *
 * Revision 1.21  2007/10/23 17:46:22  wendy
 * only check status on model thru modelavail
 *
 * Revision 1.20  2007/09/14 17:43:54  wendy
 * Updated for GX
 *
 * Revision 1.19  2007/08/20 12:21:28  wendy
 * make sure correct ve is used
 *
 * Revision 1.18  2007/08/17 16:02:09  wendy
 * RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
 * from 'SG FS xSeries ABRs 20070803.doc'
 *
 *
 * Revision 1.1  2005/07/19 16:20:25  anhtuan
 * Initial version.
 */
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.util.*;

/**********************************************************************************
* ANNABRSTATUS class
*
* From "SG FS ABR Data Quality 20080916.doc"
*
*
* A.	STATUS = Draft | Change
*
* Criteria = None
*
* B.	STATUS = Ready for Review
*
* 1.	ValueOf( ANNAVAILA: OOFAVAIL-d: PRODSTRUCT.STATUS) = 0020 (Final)
* ErrorMessage NDN(PRODSTRUCT) 'is not Final'
* 2.	ValueOf( ANNAVAILA: LSEOAVAIL-d: LSEO.STATUS) = 0020 (Final)
* ErrorMessage LD(LSEO) NDN(LSEO) 'is not Final'
* 3.	ValueOf( ANNAVAILA: LSEOBUNDLEAVAIL_d. LSEOBUNDLE.STATUS) = 0020 (Final)
* ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE)  'is not Final'
* 4.	ValueOf( ANNAVAILA: MODELAVAIL-d: MODEL.STATUS) = 0020 (Final)
* ErrorMessage LD(MODEL) NDN(MODEL) 'is not Final'
* 5.	AllValuesOf(ANNAVAILA:AVAIL-d. COUNTRYLIST) IN (ANNOUNCEMENT.COUNTRYLIST)
* ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(COUNTRYLIST).
* 6.	ValueOf( ANNAVAILA: AVAIL.STATUS) = 0020 (Final)
* ErrorMessage LD(AVAIL) NDN(AVAIL) 'is not Final'.
7.	ValueOf(ANNAVAILA: AVAIL,EFFECTIVEDATE => ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE= 146 (Planned Availability) and ANNOUNCEMENT.ANNTYPE= 19 (New)
ErrorMessage LD(AVAIL) NDN(AVAIL) LD(EFFECTIVEDATE) 'is earlier than the' LD(ANNOUNCEMENT).
8.	ValueOf(ANNAVAILA: AVAIL,EFFECTIVEDATE => ANNOUNCEMENT.ANNDATE where AVAIL.AVAILTYPE= 143 (First Order) and ANNOUNCEMENT.ANNTYPE= 19 (New)
ErrorMessage LD(AVAIL) NDN(AVAIL) LD(EFFECTIVEDATE) 'is earlier than the' LD(ANNOUNCEMENT).

*
* Virtual Entity
* Lev	Entity1	RelType	Relator	Entity2	Dir
* 0	ANNOUNCEMENT	Assoc	ANNAVAILA	AVAIL	D
* 1	PRODSTRUCT	Relator	OOFAVAIL	AVAIL	U
* 1	LSEO	Relator	LSEOAVAIL	AVAIL	U
* 1	LSEOBUNDLE	Relator	LSEOBUNDLEAVAIL	AVAIL	U
* 1	MODEL	Relator	MODELAVAIL	AVAIL	U
* 2	FEATURE	Relator	PRODSTRUCT	MODEL	U
* the rest are no longer needed?
* 2	LSEO	Relator	LSEOPRODSTRUCT	PRODSTRUCT	D
* 3	FEATURE	Relator	PRODSTRUCT	MODEL	U
* 2	LSEO	Relator	WWSEOLSEO	WWSEO	U
* 3	WWSEO	Relator	WWSEOPRODSTRUCT	PRODSTRUCT	D
* 4	FEATURE	Relator	PRODSTRUCT	MODEL	U
* 2	LSEOBUNDLE	Relator	LSEOBUNDLELSEO	LSEO	D
* 3	LSEO	Relator	LSEOPRODSTRUCT	PRODSTRUCT	D
* 4	FEATURE	Relator	PRODSTRUCT	MODEL	U
* 3	LSEO	Relator	WWSEOLSEO	WWSEO	U
* 4	WWSEO	Relator	WWSEOPRODSTRUCT	PRODSTRUCT	D
* 5	FEATURE	Relator	PRODSTRUCT	MODEL	U
*
*/
public class ANNABRSTATUS extends DQABRSTATUS
{
	private static final String ETYPES_CHKD[] = new String[] {"PRODSTRUCT","LSEO","LSEOBUNDLE","AVAIL"};
	private Object args[] = new Object[5];

	/**********************************
	* must be ready4review
	*/
	protected boolean isVEneeded(String statusFlag) {
		return STATUS_R4REVIEW.equals(statusFlag);
	}

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*C.	STATUS changed to Final
	*
	*1.	If ANNTYPE = 19 (New) then Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*2.	If ANNTYPE = 19 (New) and (ANNOUNCEMENT: ANNAVAILA: AVAIL.AVAILTYPE) = 146 (Planned Availability)
	*	and (ANNOUNCEMENT: ANNAVAILA: LSEOAVAIL-u: LSEO.STATUS) = 0020 (Final)
	*	THEN Set (ANNOUNCEMENT: ANNAVAILA: LSEOAVAIL-u: LSEO.EPIMSABRSTATUS) = ABRWAITODS (Wait for ODS Data)
	*3.	If ANNTYPE = 19 (New) and (ANNOUNCEMENT: ANNAVAILA: AVAIL.AVAILTYPE) = 146 (Planned Availability)
	*	and (ANNOUNCEMENT: ANNAVAILA: LSEOBUNDLEAVAIL-u: LSEOBUNDLE.STATUS) = 0020 (Final)
	*	THEN Set((ANNOUNCEMENT: ANNAVAILA: LSEOBUNDLEAVAIL-u: LSEOBUNDLE.EPIMSABRSTATUS) = ABRWAITODS (Wait for ODS Data)
	*4.	If ANNTYPE = 19 (New) or 14 ("End Of Life - Withdrawal from mktg") then Set 
	*	QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		String anntype = getAttributeFlagEnabledValue(rootEntity, "ANNTYPE");
		addDebug(rootEntity.getKey()+" status now final type "+anntype);
		//If ANNTYPE = 19 (New)
		if ("19".equals(anntype)){
			addDebug(rootEntity.getKey()+" is Final and New");
			//Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
			setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2);
			EntityGroup egrp = m_elist.getEntityGroup("AVAIL");
			for (int i = 0; i < egrp.getEntityItemCount(); i++) {
				EntityItem avail = egrp.getEntityItem(i);
				String availType = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
				addDebug(avail.getKey()+" type "+availType);
				//ANNOUNCEMENT: ANNAVAILA: AVAIL.AVAILTYPE) = 146 (Planned Availability)
				if (PLANNEDAVAIL.equals(availType)) {
					verifyStatusAndSetEPIMSABRStatus(avail, "LSEOAVAIL", "LSEO");
					verifyStatusAndSetEPIMSABRStatus(avail, "LSEOBUNDLEAVAIL", "LSEOBUNDLE");
				}
			}
			//CQ00006066-WI 			
			//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK, rootEntity);
			//CQ00016165
			setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), rootEntity);
		} //end if ANNTYPE = 19 (New)
		//	If ANNTYPE = 14 (EOL)
		if ("14".equals(anntype)){
			// CQ00006066-WI 
			//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK, rootEntity);
			//CQ00016165
			setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), rootEntity);
		}		 
	}

	/**
	 * Verify the entity STATUS and set the ABRSTATUS
	 */
	private void verifyStatusAndSetEPIMSABRStatus(EntityItem avail, String relator, String entityType) {
		Vector entityVct = PokUtils.getAllLinkedEntities(avail, relator, entityType);
		for (int entityCount = 0; entityCount < entityVct.size(); entityCount++) {
			EntityItem ei = (EntityItem) entityVct.elementAt(entityCount);
			String status = getAttributeFlagEnabledValue(ei, "STATUS");
			addDebug(ei.getKey() + " status " + status);
			if (status==null || status.length()==0){
				status = STATUS_FINAL;
			}
			//(ANNOUNCEMENT: ANNAVAILA: LSEOAVAIL-u: LSEO.STATUS) = 0020 (Final)
			//or
			//(ANNOUNCEMENT: ANNAVAILA: LSEOBUNDLEAVAIL-u: LSEOBUNDLE.STATUS) = 0020 (Final)
			if (STATUS_FINAL.equals(status)) {
				addDebug(ei.getKey()+ " is Final");
				//Set (ANNOUNCEMENT: ANNAVAILA: LSEOAVAIL-u: LSEO.EPIMSABRSTATUS) = ABRWAITODS (Wait for ODS Data)
				//or
				//Set((ANNOUNCEMENT: ANNAVAILA: LSEOBUNDLEAVAIL-u: LSEOBUNDLE.EPIMSABRSTATUS) = ABRWAITODS (Wait for ODS Data)
				setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS, ei);
			}
		}
		entityVct.clear();
	}

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
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
			//Object args[] = new Object[2];

			//Status is Ready for Review
			//1.	ValueOf( ANNAVAILA: OOFAVAIL-u: PRODSTRUCT.STATUS) = 0020 (Final) OR Empty
			//ErrorMessage NDN(PRODSTRUCT) 'is not Final'
			//2.	ValueOf( ANNAVAILA: LSEOAVAIL-u: LSEO.STATUS) = 0020 (Final)
			//ErrorMessage LD(LSEO) NDN(LSEO) 'is not Final'
			//3.	ValueOf( ANNAVAILA: LSEOBUNDLEAVAIL_u. LSEOBUNDLE.STATUS) = 0020 (Final)
			//ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE)  'is not Final'
			//6.	ValueOf( ANNAVAILA: AVAIL.STATUS) = 0020 (Final)
			//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is not Final'.
			for (int i=0; i<ETYPES_CHKD.length; i++){
				checkStatus(ETYPES_CHKD[i]);
			}
			// only look at MODEL thru MODELAVAIL
			//4.	ValueOf( ANNAVAILA: MODELAVAIL-u: MODEL.STATUS) = 0020 (Final)
			//ErrorMessage LD(MODEL) NDN(MODEL) 'is not Final'
			EntityGroup egrp = m_elist.getEntityGroup("AVAIL");
			Vector mdlVct = PokUtils.getAllLinkedEntities(egrp, "MODELAVAIL", "MODEL");
			addDebug("MODEL thru MODELAVAIL size: "+mdlVct.size());
			checkStatus(mdlVct);
			mdlVct.clear();

			// 5. AllValuesOf(ANNAVAILA:AVAIL-d. COUNTRYLIST) IN (ANNOUNCEMENT.COUNTRYLIST)
			// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(COUNTRYLIST).
			checkCountry("ANNAVAILA","D", true);

			//7.ValueOf(ANNAVAILA: AVAIL,EFFECTIVEDATE => ANNOUNCEMENT.ANNDATE where
			//			AVAIL.AVAILTYPE= 146 (Planned Availability) and ANNOUNCEMENT.ANNTYPE= 19 (New)
			// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(EFFECTIVEDATE) 'is earlier than the' LD(ANNOUNCEMENT).
			//8.ValueOf(ANNAVAILA: AVAIL,EFFECTIVEDATE => ANNOUNCEMENT.ANNDATE where
			//		AVAIL.AVAILTYPE= 143 (First Order) and ANNOUNCEMENT.ANNTYPE= 19 (New)
			// ErrorMessage LD(AVAIL) NDN(AVAIL) LD(EFFECTIVEDATE) 'is earlier than the' LD(ANNOUNCEMENT).
			checkAvailDates(rootEntity);

		}//end of r4r
    }

    /***********************************************
	*7.ValueOf(ANNAVAILA: AVAIL,EFFECTIVEDATE => ANNOUNCEMENT.ANNDATE where
	*		AVAIL.AVAILTYPE= 146 (Planned Availability) and ANNOUNCEMENT.ANNTYPE= 19 (New)
	* ErrorMessage LD(AVAIL) NDN(AVAIL) LD(EFFECTIVEDATE) 'is earlier than the' LD(ANNOUNCEMENT).
	*8.ValueOf(ANNAVAILA: AVAIL,EFFECTIVEDATE => ANNOUNCEMENT.ANNDATE where
	*		AVAIL.AVAILTYPE= 143 (First Order) and ANNOUNCEMENT.ANNTYPE= 19 (New)
	* ErrorMessage LD(AVAIL) NDN(AVAIL) LD(EFFECTIVEDATE) 'is earlier than the' LD(ANNOUNCEMENT).
	*/
	private void checkAvailDates(EntityItem rootEntity) throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		String anntype = getAttributeFlagEnabledValue(rootEntity, "ANNTYPE");
		addDebug("checkAvailDates "+rootEntity.getKey()+" anntype "+anntype);
		//If ANNTYPE = 19 (New)
		if (!"19".equals(anntype)){
			return;
		}
		Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "ANNAVAILA", "AVAIL");
		String annDate = getAttributeValue(rootEntity, "ANNDATE", "");
		addDebug("checkAvailDates "+rootEntity.getKey()+" ANNDATE: "+annDate+" availVct: "+availVct.size());

		if (annDate.trim().length()==0){
			//MUST_HAVE_ERR = must have a {0}
			args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "ANNDATE", "ANNDATE");
			addError("MUST_HAVE_ERR",args);
			return;
		}

		for (int ai=0; ai<availVct.size(); ai++){ // look at avails
			EntityItem avail = (EntityItem)availVct.elementAt(ai);
			String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
			String availtype = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
			addDebug("checkAvailDates "+avail.getKey()+" EFFECTIVEDATE: "+effDate+" AVAILTYPE: "+availtype);
			// 7,8.	AllValuesOf(ANNAVAILA-d: AVAIL.EFFECTIVEDATE) => ANNOUNCEMENT.ANNDATE where
			//	 	AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
			if((PLANNEDAVAIL.equals(availtype) || FIRSTORDERAVAIL.equals(availtype))
				&& effDate.length()>0 && effDate.compareTo(annDate)<0){
				//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(ANNOUNCEMENT).
				//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
				args[0] = avail.getEntityGroup().getLongDescription()+" "+ getNavigationName(avail)+" "+
					PokUtils.getAttributeDescription(avail.getEntityGroup(), "EFFECTIVEDATE", "EFFECTIVEDATE");
				args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "ANNDATE", "ANNDATE");
				args[2] = "";
				args[3] = "";
				addError("EARLY_DATE_ERR",args);
			}
		}
	}

	/**********************************
	* class has a different status attribute
	*/
	protected String getStatusAttrCode() { return "ANNSTATUS";}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "ANNOUNCEMENT ABR.";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.35";
    }
}
