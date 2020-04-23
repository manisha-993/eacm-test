// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.util.*;

/**
LSEOBDLABRSTATUS_class=COM.ibm.eannounce.abr.sg.LSEOBDLABRSTATUS
LSEOBDLABRSTATUS_enabled=true
LSEOBDLABRSTATUS_idler_class=A
LSEOBDLABRSTATUS_keepfile=true
LSEOBDLABRSTATUS_report_type=DGTYPE01
LSEOBDLABRSTATUS_vename=EXRPT3LSEOBDL1
LSEOBDLABRSTATUS_CAT1=RPTCLASS.LSEOBDLABRSTATUS
LSEOBDLABRSTATUS_CAT2=
LSEOBDLABRSTATUS_CAT3=RPTSTATUS
LSEOBDLABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390

*/

/**********************************************************************************
* LSEOBDLABRSTATUS class
*
* From "SG FS ABR Data Quality 20081016.doc"
*A.	STATUS = Draft | Change Request
*
*1.	CountOf(LSEOBUNDLELSEO-d: LSEO) => 2
*ErrorMessage 'must have at least two' LD(LSEO)
*2.	ValueOf(LSEOBUNDLELSEO-d: LSEO.STATUS) = 0020 (Final) or 0040 (Ready for Review)
*ErrorMessage 'has a' LD(LSEO) NDN(LSEO) 'that is not Ready for Review or Final'
*3.	If 100 (Hardware) or 101 (Software) NOT IN ValueOf(LSEOBUNDLE. BUNDLETYPE) then
*	a.	CountOf(SEOGCBDL-u: SEOCG) = 0
*	ErrorMessage “a Service Bundle is in” LD(SEOCG) 
*ELSE
*	a. CountOf(SEOCGBDL-u: SEOCG)  => 1
*	ErrorMessage 'has to be in a least one' LD(SEOCG)
*4.	AllValuesOf(LSEOBUNDLE.COUNTRYLIST) IN (LSEOBUNDLELSEO-d: LSEO.COUNTRYLIST)
*ErrorMessage 'has a Country in its' LD(COUNTRYLIST) 'that is not in 'LD(LSEO) NDN(LSEO) LD(COUNTRYLIST)
*5.	ValidateOSLevel
*6.	ValidateBUNDLETYPE
7.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11458 (Yes) THEN CountOf(LSEOBUNDLEAVAIL-d. AVAIL) = 0
 ErrorMessage  'is a Special Bid, but it has an' LD(AVAIL)
8.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11457 (No) THEN
a.	CountOf(LSEOBUNDLEAVAIL-d. AVAIL) > 0
ErrorMessage  'is not a Special Bid, but it does not have an' LD(AVAIL)
b.	AllValuesOf(LSEOBUNDLEAVAIL-d:AVAIL. COUNTRYLIST) IN (LSEOBUNDLE.COUNTRYLIST)
ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST).

*B.	STATUS = Ready for Review
*
*1.	Draft | Change Request Criteria
*2.	ValueOf(LSEOBUNDLELSEO-d: LSEO.STATUS) = 0020 (Final)
*ErrorMessage 'has a' LD(LSEO) NDN(LSEO) 'that is not Final'
*3.	CompareAll(LSEOBUNDLELSEO-d: LSEO.LSEOPUBDATEMTRGT) <= LSEOBUNDLE.BUNDLPUBDATEMTRGT
*ErrorMessage LD(BUNDLPUBDATEMTRGT) 'is earlier than' LD(LSEO) NDN(LSEO) LD(LSEOPUBDATEMTRGT)
*4.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11457 (No) THEN
*a.	LSEOBUNDLE.SAPASSORTMODULE must exist for NLSID = 1
*ErrorMessage  'is not a Special Bid, but it does not have a value for' LD(SAPASSORTMODULE)
5.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEOBUNDLE. BUNDLPUBDATEMTRGT where AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(BUNDLPUBDATEMTRGT).
6.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEOBUNDLE. BUNDLUNPUBDATEMTRGT where AVAIL.AVAILTYPE= 149 (Last Order)
ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(BUNDLUNPUBDATEMTRGT).

*
*C.	STATUS changed to Final
*
*1.	IF FirstTime(LSEOBUNDLE.STATUS = 0020 (Final)) THEN  ELSE obtain the value for SAPASSORTMODULE that was in effect at the 'last DTS that STATUS was Final' and set SAPASSORTMODULEPRIOR equal to that value.
*2.	Set COMPATGENABR = 0020 (Queued)
*3.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
*4.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
*
*D.	STATUS changed to Ready for Review
*
*1.	Set COMPATGENABR = 0020 (Queued)
*
*E.	STATUS = Final
*
*1.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
*2.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11457 (No) THEN
*	a.	IF (LSEOBUNDLE: LSEOBUNDLEAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
*	(LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) THEN
*	Set (LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
*ELSE
*	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
*
*/
//LSEOBDLABRSTATUS.java,v
//Revision 1.33  2009/02/05 13:42:49  wendy
//CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
//
//Revision 1.32  2009/01/28 17:34:12  wendy
//MN38219508 include SW in SVC bundle check
//
//Revision 1.31  2008/10/16 18:21:11  wendy
//CQ00012177-RQ more updates - support Services that are Special Bids
//
//Revision 1.30  2008/09/22 15:08:16  wendy
//CQ00006066-WI updates
//
//Revision 1.29  2008/04/24 20:23:05  wendy
//"SG FS ABR Data Quality 20080422.doc" spec updates
//
//Revision 1.28  2008/04/11 19:19:12  wendy
//changes for spec SG FS ABR Data Quality 200803xx.doc
//
//Revision 1.27  2008/03/13 18:21:01  wendy
//Correct country check, root must be subset in some cases
//
//Revision 1.26  2008/02/13 19:58:49  wendy
//Make ABRWAITODSx into constants, allow easier change in future
//
//Revision 1.25  2008/01/21 17:26:55  wendy
//Default null status to final
//
//Revision 1.24  2007/12/12 17:00:24  wendy
//spec chg "Changed the value used to queue WWPRT since it uses VIEWs in the "legacy" ODS (i.e. not the "approved data ods")"
//
//Revision 1.23  2007/11/27 22:27:39  wendy
//SG FS ABR Data Quality 20071127.doc chgs
//
//Revision 1.22  2007/11/16 22:17:21  nancy
//Chgs for spec "SG FS ABR Data Quality 20071115.doc"
//
//Revision 1.21  2007/10/25 18:38:23  wendy
//Spec updates
//
//Revision 1.20  2007/10/24 15:37:05  wendy
//split into 2 VEs
//
//Revision 1.19  2007/10/23 17:47:12  wendy
//Spec changes
//
//Revision 1.18  2007/09/14 17:43:55  wendy
//Updated for GX
//
public class LSEOBDLABRSTATUS  extends DQABRSTATUS
{
    private Object[] args = new String[5];
	private EntityList mdlList;
	//BUNDLETYPE	100	Hardware
	//BUNDLETYPE	101	Software
	//BUNDLETYPE	102	Service

	private static final String HARDWARE="100";
	private static final String SOFTWARE="101";
	private static final Set TESTSET;
	static{
		TESTSET = new HashSet();
		TESTSET.add(HARDWARE);
		TESTSET.add(SOFTWARE);//MN38219508
	}
	/**********************************
	* check all states
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**********************************
	* complete abr processing when status is already final; (dq was final too)
	*E.	STATUS = Final
	*
	*1.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*2.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11457 (No) THEN
	*	a.	IF (LSEOBUNDLE: LSEOBUNDLEAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	*	(LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) THEN
	*	Set (LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
	*ELSE
	*	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*/
	protected void doAlreadyFinalProcessing() {
		doFinalProcessing(m_elist.getParentEntityGroup().getEntityItem(0));
	}

    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
    *D.	STATUS changed to Ready for Review
	*1.	Set COMPATGENABR = 0020 (Queued)
    */
    protected void completeNowR4RProcessing()throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
         setFlagValue(m_elist.getProfile(),"COMPATGENABR", ABR_QUEUED);
    }

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*C.	STATUS changed to Final
	*1.	IF FirstTime(LSEOBUNDLE.STATUS = 0020 (Final)) THEN  ELSE obtain the value for SAPASSORTMODULE
	*that was in effect at the 'last DTS that STATUS was Final' and set SAPASSORTMODULEPRIOR equal to
	*that value.
	*2.	Set COMPATGENABR = 0020 (Queued)
	*3.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*4.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11457 (No) THEN
	*	a.	IF (LSEOBUNDLE: LSEOBUNDLEAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	*		(LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	*		Set (LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2
	*	ELSE
	*	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		checkAssortModule();
		setFlagValue(m_elist.getProfile(),"COMPATGENABR", ABR_QUEUED);

		doFinalProcessing(m_elist.getParentEntityGroup().getEntityItem(0));
	}

	/**********************************
	* complete abr processing when status is already final or just set to final
	*
	*1.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*2.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11457 (No) THEN
	*	a.	IF (LSEOBUNDLE: LSEOBUNDLEAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	*	(LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) THEN
	*	Set (LSEOBUNDLE: LSEOBUNDLEAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
	* CQ00006066-WI
b.	IF (LSEOBUNDLE: LSEOBUNDLEAVAIL-d: AVAIL.AVAILTYPE) =  146 (Planned Availability) and
(AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final) and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New)
then Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)
c.	IF (LSEOBUNDLE: LSEOBUNDLEAVAIL-d: AVAIL.AVAILTYPE) =  149 (Last Order) and
(AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final) and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) =  14
("End Of Life - Withdrawal from mktg")
then Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)

	*ELSE
	*	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*CQ00006066-WI
b.	Set QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)

	*/
	private void doFinalProcessing(EntityItem rootEntity)
	{
        setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS);

		String specbid = getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		addDebug(rootEntity.getKey()+" SPECBID: "+specbid);
		if ("11457".equals(specbid)){  // is No
			EntityGroup eg = m_elist.getEntityGroup("ANNOUNCEMENT");
			for (int i=0; i<eg.getEntityItemCount(); i++){
				EntityItem annItem = eg.getEntityItem(i);
				String annstatus = getAttributeFlagEnabledValue(annItem, "ANNSTATUS");
				String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
				addDebug(annItem.getKey()+" status "+annstatus+" type "+anntype);
				if (annstatus==null || annstatus.length()==0){
					annstatus = STATUS_FINAL;
				}
				if (STATUS_FINAL.equals(annstatus) && "19".equals(anntype)){
					addDebug(annItem.getKey()+" is Final and New");
        			setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2,annItem);
				}
			}
//			CQ00006066-WI go thru AVAILs to ANN
			eg = m_elist.getEntityGroup("AVAIL");
			for (int ai=0; ai<eg.getEntityItemCount(); ai++){
				EntityItem availItem = eg.getEntityItem(ai);
				String availType = getAttributeFlagEnabledValue(availItem, "AVAILTYPE");
				addDebug(availItem.getKey()+" type "+availType);
				//IF AVAILTYPE =  146 (Planned Availability) or 149 (Last Order)
				if ("146".equals(availType) || "149".equals(availType)) {
					Vector annVct= PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", "ANNOUNCEMENT");
					for (int i = 0; i < annVct.size(); i++) {
						EntityItem ei = (EntityItem)annVct.elementAt(i);
						String status = getAttributeFlagEnabledValue(ei, "ANNSTATUS");
						String annType = getAttributeFlagEnabledValue(ei, "ANNTYPE");
						addDebug(ei.getKey() + " status " + status + " type " + annType);
						if (status==null || status.length()==0){
							status = STATUS_FINAL;
						}
						//and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final)
						if (STATUS_FINAL.equals(status)) {
							if ("146".equals(availType)&& "19".equals(annType)) { //PlannedAvail and New ann
								addDebug(ei.getKey()+" is Final and New");
								//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK, ei);//CQ00006066-WI
								//CQ00016165
								setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), ei);
							}
							if ("149".equals(availType)&& "14".equals(annType)) { // LastOrderAvail and EOL ann CQ00006066-WI
								addDebug(ei.getKey()+" is Final and EOL");
								//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK, ei);
								//CQ00016165
								setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"), ei);
							}
						}
					}
				}
			}
		}
		else{
        	setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2);
        	//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK);//CQ00006066-WI
			//CQ00016165
			setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"));    	
		}
	}

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*/
    protected void doDQChecking(EntityItem rootEntity, String status) throws Exception
    {
		//1.	Draft | Change Request Criteria done for Ready For Review too

		//Status is Draft or Change
		//1.	CountOf(LSEOBUNDLELSEO-d: LSEO) => 2
		//ErrorMessage 'must have at least two' LD(LSEO)
		int cnt = getCount("LSEOBUNDLELSEO");
		if(cnt <2) {
			//MINIMUM_TWO_ERR = must have at least two {0}
			args[0] = m_elist.getEntityGroup("LSEO").getLongDescription();
			addError("MINIMUM_TWO_ERR",args);
		}
		//2.	ValueOf(LSEOBUNDLELSEO-d: LSEO.STATUS) = 0020 (Final) or 0040 (Ready for Review)
		//ErrorMessage 'has a' LD(LSEO) NDN(LSEO) 'that is not Ready for Review or Final'
		checkLSEOStatus(status);

		//orig 3.	If 100 (Hardware) NOT IN ValueOf(LSEOBUNDLE. BUNDLETYPE) then 
		//MN38219508
		//3.If 100 (Hardware) or 101 (Software) NOT IN ValueOf(LSEOBUNDLE. BUNDLETYPE) then	
		//	a.	CountOf(SEOGCBDL-u: SEOCG) = 0
		//	ErrorMessage “a Service Bundle is in” LD(SEOCG) 
		//	ELSE
		//	a. CountOf(SEOCGBDL-u: SEOCG)  => 1
		//	ErrorMessage 'has to be in a least one' LD(SEOCG)
		cnt = getCount("SEOCGBDL");
		if (!PokUtils.contains(rootEntity, "BUNDLETYPE", TESTSET)){
			if(cnt !=0) {
				//SVC_SEOCG_ERR = is a Service and must not be in a {0}
				args[0] = m_elist.getEntityGroup("SEOCG").getLongDescription();
				addError("SVC_SEOCG_ERR",args);
			}
		}else{
			if(cnt ==0) {
				//MUST_BE_IN_ATLEAST_ONE_ERR = must be in at least one {0}
				args[0] = m_elist.getEntityGroup("SEOCG").getLongDescription();
				addError("MUST_BE_IN_ATLEAST_ONE_ERR",args);
			}
		}

		//4.	AllValuesOf(LSEOBUNDLE.COUNTRYLIST) IN (LSEOBUNDLELSEO-d: LSEO.COUNTRYLIST)
		//ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) NDN(LSEO) LD(COUNTRYLIST).
		checkCountry("LSEOBUNDLELSEO","D", false);

		// get VE2 to go from WWSEO-MODELWWSEO-MODEL and other MODEL links
		getModelVE(rootEntity);

		//5.	ValidateOSLevel
		validateOS(rootEntity);

		//6.	ValidateBUNDLETYPE
		validateBUNDLETYPE(rootEntity);

//7.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11458 (Yes) THEN CountOf(LSEOBUNDLEAVAIL-d. AVAIL) = 0
// 	ErrorMessage  'is a Special Bid, but it has an' LD(AVAIL)
//8.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11457 (No) THEN
//	a.	CountOf(LSEOBUNDLEAVAIL-d. AVAIL) > 0
//	ErrorMessage  'is not a Special Bid, but it does not have an' LD(AVAIL)
//	b.	AllValuesOf(LSEOBUNDLEAVAIL-d:AVAIL. COUNTRYLIST) IN (LSEOBUNDLE.COUNTRYLIST)
//	ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST).

		String specbid = getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		addDebug(rootEntity.getKey()+" SPECBID: "+specbid);
		if ("11457".equals(specbid)){  // is No
			//a.	CountOf(LSEOBUNDLEAVAIL-d. AVAIL) > 0
			//ErrorMessage  'is not a Special Bid, but it does not have an' LD(AVAIL)
			Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOBUNDLEAVAIL", "AVAIL");
			Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
			if (plannedavailVector.size()==0){
				args[0] = m_elist.getEntityGroup("AVAIL").getLongDescription();
				//NOT_SPECBID_AVAIL_ERR = is not a Special Bid, but it does not have an {0}
				addError("NOT_SPECBID_AVAIL_ERR",args);
			}

			//b.	AllValuesOf(LSEOBUNDLEAVAIL-d:AVAIL. COUNTRYLIST) IN (LSEOBUNDLE.COUNTRYLIST)
			//ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEOBUNDLE) NDN(LSEOBUNDLE) LD(COUNTRYLIST).
			checkCountry("LSEOBUNDLEAVAIL","D", true);
		}else{ // specbid=yes
			cnt = getCount("LSEOBUNDLEAVAIL");
			//4/7.	IF ValueOf(LSEOBUNDLE.SPECBID) = 11458 (Yes) THEN CountOf(LSEOBUNDLEAVAIL-d. AVAIL) = 0
			// ErrorMessage  'is a Special Bid, but it has an' LD(AVAIL)
			//SPECBID_AVAIL_ERR = is a Special Bid, but it has an {0}
			if (cnt!=0){
				args[0] = m_elist.getEntityGroup("AVAIL").getLongDescription();
				addError("SPECBID_AVAIL_ERR",args);
			}
		}

 		if(STATUS_R4REVIEW.equals(status)) // 'Ready for Review to Final'
		{
			//3.	CompareAll(LSEOBUNDLELSEO-d: LSEO.LSEOPUBDATEMTRGT) <= LSEOBUNDLE.BUNDLPUBDATEMTRGT
			//ErrorMessage LD(BUNDLPUBDATEMTRGT) 'is earlier than' LD(LSEO) NDN(LSEO) LD(LSEOPUBDATEMTRGT)
			checkDates(rootEntity);

			if ("11457".equals(specbid)){  // is No
				//c.	LSEOBUNDLE.SAPASSORTMODULE must exist for NLSID = 1
				//ErrorMessage  'is not a Special Bid, but it does not have a value for' LD(SAPASSORTMODULE)
				EANTextAttribute att = (EANTextAttribute)rootEntity.getAttribute("SAPASSORTMODULE");
				//true if information for the given NLSID is contained in the Text data
				if (att==null || (!((EANTextAttribute)att).containsNLS(1))) {
					args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
					addError("NOT_SPECBID_VALUE_ERR",args);
				} // end attr has this language
			}
			//5.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEOBUNDLE. BUNDLPUBDATEMTRGT where AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
			//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(BUNDLPUBDATEMTRGT).
			//6.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEOBUNDLE. BUNDLUNPUBDATEMTRGT where AVAIL.AVAILTYPE= 149 (Last Order)
			//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(BUNDLUNPUBDATEMTRGT).
			checkAvailDates(rootEntity);
		}

		mdlList.dereference(); // not needed any more
	}

    /**********************************
    * Must have MODELWWSEO in second VE because end up with SWPRODSTRUCTs and FEATUREs from this MODEL
    * when all you want is SWPRODSTRUCTs from WWSEOPRODSTRUCT
    */
    private void getModelVE(EntityItem rootEntity) throws Exception
    {
        String VeName = "EXRPT3LSEOBDL2";

        mdlList = m_db.getEntityList(m_elist.getProfile(),
                new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
                new EntityItem[] { new EntityItem(null, m_elist.getProfile(), rootEntity.getEntityType(), rootEntity.getEntityID()) });
        addDebug("getModelVE:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));
	}

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "LSEOBUNDLE ABR.";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.33";
    }

    /***********************************************
    *2.	ValueOf(LSEOBUNDLELSEO-d: LSEO.STATUS) = 0020 (Final) or 0040 (Ready for Review)
	* ErrorMessage 'has a' LD(LSEO) NDN(LSEO) 'that is not Ready for Review or Final'
	*or
	*2.	ValueOf(LSEOBUNDLELSEO-d: LSEO.STATUS) = 0020 (Final)
	*ErrorMessage 'has a' LD(LSEO) NDN(LSEO) 'that is not Final'
    *
    */
    private void checkLSEOStatus(String status) throws java.sql.SQLException, MiddlewareException
    {
		EntityGroup lseoGrp = m_elist.getEntityGroup("LSEO");
        for(int i = 0; i < lseoGrp.getEntityItemCount(); i++)
        {
            EntityItem lseoItem = lseoGrp.getEntityItem(i);
            String lseoStatus = getAttributeFlagEnabledValue(lseoItem, "STATUS");
            addDebug("checkLSEOStatus "+lseoItem.getKey()+" lseoStatus: "+lseoStatus);
            if(lseoStatus == null || lseoStatus.length()==0) {
                lseoStatus = STATUS_FINAL;
            }
			if(STATUS_R4REVIEW.equals(status)){ // 'Ready for Review to Final'
				if(!STATUS_FINAL.equals(lseoStatus)) {
					args[0] = lseoGrp.getLongDescription();
					args[1] = getNavigationName(lseoItem);
					//ONE_NOT_FINAL_ERR = has a {0} {1} that is not Final.
					addError("ONE_NOT_FINAL_ERR",args);
				}
			} else{
				if(STATUS_FINAL.equals(lseoStatus) || STATUS_R4REVIEW.equals(lseoStatus)) {
				}else{
					args[0] = lseoGrp.getLongDescription();
					args[1] = getNavigationName(lseoItem);
					//ONE_NOT_R4RFINAL_ERR = has a {0} {1} that is not Ready for Review or Final.
					addError("ONE_NOT_R4RFINAL_ERR",args);
				}
			}
        }
    }

    /***********************************************
    *3.	CompareAll(LSEOBUNDLELSEO-d: LSEO.LSEOPUBDATEMTRGT) <= LSEOBUNDLE.BUNDLPUBDATEMTRGT
    *ErrorMessage LD(BUNDLPUBDATEMTRGT) 'is earlier than' LD(LSEO) NDN(LSEO) LD(LSEOPUBDATEMTRGT)
    */
    private void checkDates(EntityItem rootEntity) throws java.sql.SQLException, MiddlewareException
    {
        String bundlPubDateTarget = getAttributeValue(rootEntity, "BUNDLPUBDATEMTRGT", "");
        addDebug("checkDates "+rootEntity.getKey()+" BUNDLPUBDATEMTRGT: "+bundlPubDateTarget);
        if (bundlPubDateTarget.length()==0) {
			return;
		}

		EntityGroup lseoGrp = m_elist.getEntityGroup("LSEO");
        for(int i = 0; i < lseoGrp.getEntityItemCount(); i++)
        {
            EntityItem lseoItem = lseoGrp.getEntityItem(i);
            String pubDateTarget = getAttributeValue(lseoItem, "LSEOPUBDATEMTRGT", "");
        	addDebug("checkDates "+lseoItem.getKey()+" LSEOPUBDATEMTRGT: "+pubDateTarget);
            if(pubDateTarget.length()>0 && pubDateTarget.compareTo(bundlPubDateTarget) > 0)
            {
				args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "BUNDLPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
				args[1] = lseoGrp.getLongDescription();
				args[2] = getNavigationName(lseoItem);
				args[3] = PokUtils.getAttributeDescription(lseoGrp, "LSEOPUBDATEMTRGT", "LSEOPUBDATEMTRGT");

				//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
				addError("EARLY_DATE_ERR",args);
            }
        }
    }

    /***********************************************
	*5.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEOBUNDLE. BUNDLPUBDATEMTRGT where
	* 	AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
	*ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(BUNDLPUBDATEMTRGT).
	*6.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEOBUNDLE. BUNDLUNPUBDATEMTRGT where
	*	AVAIL.AVAILTYPE= 149 (Last Order)
	*ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(BUNDLUNPUBDATEMTRGT).
	*/
	private void checkAvailDates(EntityItem rootEntity) throws java.sql.SQLException, MiddlewareException
	{
		Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOBUNDLEAVAIL", "AVAIL");
		String pubDate = getAttributeValue(rootEntity, "BUNDLPUBDATEMTRGT", "");
		String unpubDate = getAttributeValue(rootEntity, "BUNDLUNPUBDATEMTRGT", "");
		addDebug("checkAvailDates "+rootEntity.getKey()+" BUNDLPUBDATEMTRGT: "+pubDate+" BUNDLUNPUBDATEMTRGT: "+
			unpubDate+" availVct: "+availVct.size());

		for (int ai=0; ai<availVct.size(); ai++){ // look at avails
			EntityItem avail = (EntityItem)availVct.elementAt(ai);
			String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
			String availtype = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
			addDebug("checkAvailDates "+avail.getKey()+" EFFECTIVEDATE: "+effDate+" AVAILTYPE: "+availtype);
			// 5.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEOBUNDLE. BUNDLPUBDATEMTRGT where
			//	 	AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
			if (pubDate.trim().length()>0){
				if((PLANNEDAVAIL.equals(availtype) || FIRSTORDERAVAIL.equals(availtype))
					&& effDate.length()>0 && effDate.compareTo(pubDate)<0){
					//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(BUNDLPUBDATEMTRGT).
					//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
					args[0] = avail.getEntityGroup().getLongDescription()+" "+ getNavigationName(avail);
					args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "BUNDLPUBDATEMTRGT", "BUNDLPUBDATEMTRGT");
					args[2] = "";
					args[3] = "";
					addError("EARLY_DATE_ERR",args);
				}
			}
			//6.	AllValuesOf(LSEOBUNDLEAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEOBUNDLE. BUNDLUNPUBDATEMTRGT where
			//	AVAIL.AVAILTYPE= 149 (Last Order)
			if (unpubDate.trim().length()>0){
				if(LASTORDERAVAIL.equals(availtype)	&& effDate.length()>0 && effDate.compareTo(unpubDate)>0){
					//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(BUNDLUNPUBDATEMTRGT).
					//LATER_DATE_ERR = {0} {1} {2} is later than the {3} {4}
					args[0] = avail.getEntityGroup().getLongDescription();
					args[1] = getNavigationName(avail);
					args[2] = "";
					args[3] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "BUNDLUNPUBDATEMTRGT", "BUNDLUNPUBDATEMTRGT");
					args[4] = "";
					addError("LATER_DATE_ERR",args);
				}
			}
		}
	}

    /***********************************************
    *5.For LSEOBUNDLEs, verify that its OSLEVEL matches the OS found on a
    *	'ValueMetric' SWFEATURE that is part of the software LSEO.
	*
	*Find the WWSEO where LSEOBUNDLELSEO:WWSEOLSEO: MODELWWSEO:MODEL.COFCAT = 101 (Software)
	*& MODEL.APPLICATIONTYPE = 33 (OperatingSystem) THEN
	*	IF WWSEO:WWSEOSWPRODSTRUCT:SWFEATURE.OS <> LSEOBUNDLE.OSLEVEL WHERE
	*		SWFEATURE.SWFCCAT = 319 (ValueMetric) THEN
	*		ErrorMessage LD(OSLEVEL) 'Does not match the software LSEOs OS'
	*END IF
	*ELSE
	*EXIT
	*END IF
    *
    */
    private void validateOS(EntityItem rootEntity) throws MiddlewareException
    {
		String oslvlAttr = "OSLEVEL";
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(oslvlAttr);
        if (metaAttr==null) {
           addDebug("validateOS ERROR:Attribute "+oslvlAttr+" NOT found in LSEOBUNDLE META data.");
           return;
        }

    	ArrayList bdlOS = new ArrayList();
		//get the oslevel from the bundle
		EANFlagAttribute rootOS = (EANFlagAttribute)rootEntity.getAttribute(oslvlAttr);
		if (rootOS != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) rootOS.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					bdlOS.add(mfArray[i].getFlagCode());
				}
			}
		}

	    addDebug("validateOS "+rootEntity.getKey()+" oslevel "+bdlOS);

	    // get MODEL from VE2  LSEOBUNDLELSEO-d: WWSEOLSEO-u: MODELWWSEO-u: MODEL
		EntityGroup mdlGrp = mdlList.getEntityGroup("MODEL");
		// find MODEL.COFCAT = 101 (Software) & MODEL.APPLICATIONTYPE = 33 (OperatingSystem)
		for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
			EntityItem mdlItem = mdlGrp.getEntityItem(i);
            String cofcat = getAttributeFlagEnabledValue(mdlItem , "COFCAT");
            String appType = getAttributeFlagEnabledValue(mdlItem , "APPLICATIONTYPE");
            addDebug("validateOS "+mdlItem.getKey()+" COFCAT: "+cofcat+" APPLICATIONTYPE: "+appType);
            if ("101".equals(cofcat)&& "33".equals(appType)){
				// find WWSEO for this MODEL
				Vector wwseoVct2 = PokUtils.getAllLinkedEntities(mdlItem, "MODELWWSEO", "WWSEO");
            	// find SWFEATURE for this WWSEO in VE1
            	EntityGroup wwseoGrp = m_elist.getEntityGroup("WWSEO");
            	Vector wwseoVct = new Vector(1);
            	for (int w=0; w<wwseoVct2.size(); w++){
					EntityItem wwseoItem2 = (EntityItem)wwseoVct2.elementAt(w);
					EntityItem wwseoItem1 = wwseoGrp.getEntityItem(wwseoItem2.getKey());
            		wwseoVct.add(wwseoItem1);
				}
				wwseoVct2.clear();
            	Vector swpsVct = PokUtils.getAllLinkedEntities(wwseoVct, "WWSEOSWPRODSTRUCT", "SWPRODSTRUCT");
            	addDebug("validateOS "+mdlItem.getKey()+" wwseoVct "+wwseoVct.size()+" swpsVct: "+swpsVct.size());
            	// find SWFEATURE.SWFCCAT = 319 (ValueMetric)
            	for (int w=0; w<swpsVct.size(); w++){
					EntityItem swpsItem = (EntityItem)swpsVct.elementAt(w);
            		addDebug("validateOS "+swpsItem.getKey()+" uplinkcnt: "+swpsItem.getUpLinkCount());
            		for (int u=0; u<swpsItem.getUpLinkCount(); u++){
						EntityItem swfcItem = (EntityItem)swpsItem.getUpLink(u);
						if (swfcItem.getEntityType().equals("SWFEATURE")){
							String swfccat = getAttributeFlagEnabledValue(swfcItem, "SWFCCAT");
							addDebug("validateOS "+swfcItem.getKey()+" SWFCCAT: "+swfccat);
							if ("319".equals(swfccat)){
								ArrayList featOS = new ArrayList();
								//get the oslevel from the feature
								EANFlagAttribute featOSattr = (EANFlagAttribute)swfcItem.getAttribute(oslvlAttr);
								if (featOSattr != null) {
									// Get the selected Flag codes.
									MetaFlag[] mfArray = (MetaFlag[]) featOSattr.get();
									for (int im = 0; im < mfArray.length; im++) {
										// get selection
										if (mfArray[im].isSelected()) {
											featOS.add(mfArray[im].getFlagCode());
										}
									}
								}
								addDebug("validateOS ValueMetric "+swfcItem.getKey()+" oslevel "+featOS);
								if (!(bdlOS.containsAll(featOS) && featOS.containsAll(bdlOS))) {
									//OSLEVEL_ERR = {0} Does not match the software LSEOs OS
									args[0] = PokUtils.getAttributeDescription(swfcItem.getEntityGroup(), oslvlAttr, oslvlAttr);
									addError("OSLEVEL_ERR",args);
								}
								featOS.clear();
							}
						}
					}
				}
				swpsVct.clear();
				wwseoVct.clear();
			}
		}
		bdlOS.clear();
    }

	/*******************************************************
	*K.	ValidateBUNDLETYPE
	*
	*Syntax:  ValidateBUNDLETYPE
	*
	*For LSEOBUNDLEs, verify that its BUNDLETYPE is consistent with the types of children LSEOs.
	*
	*1.	AllValuesOf(LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO u: MODEL.COFCAT) IN LSEOBUNDLE.LSEOBUNDLETYPE
	*ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'has a' LD(MODEL) NDN(MODEL) 'that is not reflected in' LD(BUNDLETYPE).
	*2.	AllValuesOf(LSEOBUNDLE.LSEOBUNDLETYPE) IN  LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO-u: MODEL.COFCAT
	*ErrorMessage LD(LSOBUNDLE) NDN(LSEOBUNDLE) LD(BUNDLETYPE) 'has a type that does not match any of the' LD(LSEO).
	*/
    private void validateBUNDLETYPE(EntityItem rootEntity) throws java.sql.SQLException, MiddlewareException
    {
		String attrCode = "BUNDLETYPE";
		ArrayList mdlTypes = new ArrayList();
		ArrayList bdlTypes = new ArrayList();
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(attrCode);
        if (metaAttr==null) {
           addDebug("validateBUNDLETYPE ERROR:Attribute "+attrCode+" NOT found in LSEOBUNDLE META data.");
           return;
        }

		EANFlagAttribute bdlTypeAttr = (EANFlagAttribute)rootEntity.getAttribute(attrCode);
		if (bdlTypeAttr != null) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) bdlTypeAttr.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					bdlTypes.add(mfArray[i].getFlagCode());
				}
			}
		}

	    addDebug("validateBUNDLETYPE "+rootEntity.getKey()+" bdlTypes "+bdlTypes);

	    // get MODEL from VE2  LSEOBUNDLELSEO-d: WWSEOLSEO-u: MODELWWSEO-u: MODEL
		EntityGroup mdlGrp = mdlList.getEntityGroup("MODEL");
		for (int i=0; i<mdlGrp.getEntityItemCount(); i++){
			EntityItem mdlItem = mdlGrp.getEntityItem(i);

			String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
			if(modelCOFCAT == null)	{
				modelCOFCAT = "";
			}
			addDebug("validateBUNDLETYPE "+mdlItem.getKey()+" COFCAT: "+modelCOFCAT);
			if (!bdlTypes.contains(modelCOFCAT)){
				//1.	AllValuesOf(LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO u: MODEL.COFCAT) IN LSEOBUNDLE.LSEOBUNDLETYPE
				//ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'has a' LD(MODEL) NDN(MODEL) 'that is not reflected in' LD(BUNDLETYPE).
				//MODEL_TYPE_ERR= has a {0} {1} that is not reflected in {2}.
				args[0] = mdlItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(mdlItem);
				args[2] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), attrCode, attrCode);
				addError("MODEL_TYPE_ERR",args);
			}
			mdlTypes.add(modelCOFCAT);
		} // end mdl group


	    addDebug("validateBUNDLETYPE all mdlTypes "+mdlTypes);
		if (!mdlTypes.containsAll(bdlTypes)) {
			//2.	AllValuesOf(LSEOBUNDLE.LSEOBUNDLETYPE) IN  LSEOBUNDLELSEO-u: WWSEOLSEO-u: MODELWWSEO-u: MODEL.COFCAT
			//ErrorMessage LD(LSOBUNDLE) NDN(LSEOBUNDLE) LD(BUNDLETYPE) 'has a type that does not match any of the' LD(LSEO).
			//BDLE_TYPE_ERR= {0} has a type that does not match any of the {1}.
			args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), attrCode, attrCode);
			args[1] = m_elist.getEntityGroup("LSEO").getLongDescription();
			addError("BDLE_TYPE_ERR",args);
		}
		mdlTypes.clear();
		bdlTypes.clear();
	}
}
