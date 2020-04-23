// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;
import java.util.*;


/**
LSEOABRSTATUS_class=COM.ibm.eannounce.abr.sg.LSEOABRSTATUS
LSEOABRSTATUS_enabled=true
LSEOABRSTATUS_idler_class=A
LSEOABRSTATUS_keepfile=true
LSEOABRSTATUS_report_type=DGTYPE01
LSEOABRSTATUS_vename=EXRPT3LSEO1
LSEOABRSTATUS_CAT1=RPTCLASS.LSEOABRSTATUS
LSEOABRSTATUS_CAT2=
LSEOABRSTATUS_CAT3=RPTSTATUS
LSEOABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390

*
* LSEOABRSTATUS.java,v
* Revision 1.36  2009/02/05 13:42:49  wendy
* CQ00016165 - Automated QSM feed from ePIMS HW to support the late change request from BIDS
*
* Revision 1.35  2008/09/22 15:09:27  wendy
* CQ00006066-WI updates
*
* Revision 1.34  2008/04/24 19:51:42  wendy
* "SG FS ABR Data Quality 20080422.doc" spec updates
*
* Revision 1.33  2008/04/11 20:12:10  wendy
* changes for spec SG FS ABR Data Quality 200803xx.doc
*
* Revision 1.32  2008/03/13 18:21:01  wendy
* Correct country check, root must be subset in some cases
*
* Revision 1.31  2008/02/13 19:58:49  wendy
* Make ABRWAITODSx into constants, allow easier change in future
*
* Revision 1.30  2008/01/30 19:39:16  wendy
* Cleanup RSA warnings
*
* Revision 1.29  2008/01/21 17:26:55  wendy
* Default null status to final
*
* Revision 1.28  2008/01/20 23:22:22  wendy
* Updates for 'SG FS ABR Data Quality 20080118.doc'
*
* Revision 1.27  2007/12/19 17:11:28  wendy
* VE needed when already final too
*
* Revision 1.26  2007/12/12 17:00:24  wendy
* spec chg "Changed the value used to queue WWPRT since it uses VIEWs in the "legacy" ODS (i.e. not the "approved data ods")"
*
* Revision 1.25  2007/11/27 22:34:31  wendy
* SG FS ABR Data Quality 20071127.doc chgs
*
* Revision 1.24  2007/11/26 13:13:25  wendy
* Chgs for spec "SG FS ABR Data Quality 20071115.doc"
*
* Revision 1.23  2007/10/25 21:20:15  wendy
* Spec chgs
*
* Revision 1.22  2007/10/24 21:01:54  wendy
* spec chgs
*
* Revision 1.21  2007/10/23 17:47:12  wendy
* Spec changes
*
* Revision 1.20  2007/09/14 17:43:55  wendy
* Updated for GX
*
* Revision 1.19  2007/08/17 16:02:10  wendy
* RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
* from 'SG FS xSeries ABRs 20070803.doc'
*
* Revision 1.18  2007/05/04 13:40:05  wendy
* RQ022507238 chgs
*
*/
/**********************************************************************************
* LSEOABRSTATUS class
*
* From "SG FS ABR Data Quality 20080916.doc"
A.	STATUS = Draft | Change Request

1.	ValueOf(WWSEO.STATUS) = 0040 (Ready for Review) or 0020 (Final)
ErrorMessage  'has a' LD(WWSEO) NDN(WWSEO) 'that is not Ready for Review or Final'
2.	CountOf(WWSEOLSEO-u.WWSEO) = 1
ErrorMessage  'must have only one parent' LD(WWSEO)
3.	IF ValueOf( WWSEOLSEO-u: WWSEO.SPECBID) = 11458 (Yes) THEN CountOf(LSEOAVAIL:AVAIL) = 0
ErrorMessage  'is a Special Bid, but it has an' LD(AVAIL)
4.	IF ValueOf( WWSEOLSEO-u: WWSEO.SPECBID) = 11457 (No) THEN
	a.	CountOf(LSEOAVAIL-d.AVAIL) > 0 where AVAIL.AVAILTYPE = 146 (Planned Availability)
	ErrorMessage  'is not a Special Bid, but it does not have an' LD(AVAIL)
	b.	AllValuesOf(LSEOAVAIL-d:AVAIL. COUNTRYLIST) IN (LSEO.COUNTRYLIST)
	ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) NDN(LSEO) LD(COUNTRYLIST).
5.	AllValuesOf(LSEOPRODSTRUCT-d:PRODSTRUCT. WITHDRAWDATE) > NOW()
ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
6.	AllValuesOf(LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL:AVAIL.EFFECTIVEDATE) > NOW() WHERE AVAIL.AVAILTYPE = 149 (Last Order).
ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
7.	AllValuesOf(LSEOSWPRODSTRUCT-d: SWPRODSTRUCT: SWPRODSTRUCTAVAIL:AVAIL.EFFECTIVEDATE > NOW() WHERE AVAIL.AVAILTYPE = 149 (Last Order).
ErrorMessage 'references a withdrawn' LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT)
8.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) THEN CountOf(WWSEOLSEO-u: WWSEOPRODSTRUCT) + CountOf(LSEOPRODSTRUCT) > 0

B.	STATUS = Ready for Review

1.	Draft | Change Request Criteria
2.	LSEO.SAPASSORTMODULE must exist for NLSID = 1
	ErrorMessage  'is not a Special Bid, but it does not have a value for' LD(SAPASSORTMODULE)

3.	ValueOf(WWSEO.STATUS) = 0020 (Final)
ErrorMessage  'has a' LD(WWSEO) NDN(WWSEO) 'that is not Final'
4.	AllValuesOf(LSEOPRODSTRUCT-d: PRODSTRUCT.STATUS) = 0020 (Final)
ErrorMessage NDN(PRODSTRUCT) 'is not Final'.
5.	AllValuesOf(LSEOSWPRODSTRUCT-d: SWPRODSTRUCT.STATUS) = 0020 (Final)
ErrorMessage NDN(SWPRODSTRUCT) 'is not Final'.
6.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEO.LSEOPUBDATEMTRGT where AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(LSEOPUBDATEMTRGT).
7.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEO.LSEOUNPUBDATEMTRGT where AVAIL.AVAILTYPE= 149 (Last Order)
ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(LSEOUNPUBDATEMTRGT).
8.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEO. LSEOUNPUBDATEMTRGT where AVAIL.AVAILTYPE= 149 (Last Order)
ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(LSEOUNPUBDATEMTRGT).

*C.	STATUS changed to Final
*
*1.	IF FirstTime(LSEO.STATUS = 0020 (Final)) THEN  ELSE obtain the value for SAPASSORTMODULE that
*was in effect at the 'last DTS that STATUS was Final' and Set SAPASSORTMODULEPRIOR equal to that value.
*2.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
*3.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
*
*/
public class LSEOABRSTATUS extends DQABRSTATUS
{
	private Object args[] = new Object[5];

	/**********************************
	* must be domain of interest and ready4review
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**********************************
	* complete abr processing when status is already final; (dq was final too)
	*D.	STATUS = Final
	*
	*1.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*2.	IF ValueOf( WWSEOLSEO-u: WWSEO.SPECBID) = 11457 (No) THEN
	*	a.	IF (LSEO: LSEOAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	*	(LSEO: LSEOAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	*	Set (LSEO: LSEOAVAILAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
	*ELSE
	*	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*3.	IF (LSEOBUNDLELSEO-u: LSEOBUNDLE.STATUS) = 0020 (Final) then
	* 	Set (LSEOBUNDLELSEO-u: LSEOBUNDLE.EPIMSABRSTATUS) = ABRWAITODS (Wait for ODS Data)
	*
	*/
	protected void doAlreadyFinalProcessing() {
		doFinalProcessing();
	}

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*C.	STATUS changed to Final
	*
	*1.	IF FirstTime(LSEO.STATUS = 0020 (Final)) THEN  ELSE obtain the value for SAPASSORTMODULE that
	*was in effect at the 'last DTS that STATUS was Final' and Set SAPASSORTMODULEPRIOR equal to that value.
	*2.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*3.	IF ValueOf( WWSEOLSEO-u: WWSEO.SPECBID) = 11457 (No) THEN
	*	a.	IF (LSEO: LSEOAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	*		(LSEO: LSEOAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	*		Set (LSEO: LSEOAVAILAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
	*
b.	IF (LSEO: LSEOAVAIL-d: AVAIL.AVAILTYPE) =  146 (Planned Availability) and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final) and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report) 
c.	IF (LSEO: LSEOAVAIL-d: AVAIL.AVAILTYPE) =  149 (Last Order) and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final) and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) =  14 ("End Of Life - Withdrawal from mktg") then Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)

	*	ELSE
	*	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*	b.	Set QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)
	*4.	IF (LSEOBUNDLELSEO-u: LSEOBUNDLE.STATUS) = 0020 (Final) then
	*	Set (LSEOBUNDLELSEO-u: LSEOBUNDLE.EPIMSABRSTATUS) = ABRWAITODS (Wait for ODS Data)
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		checkAssortModule();
		doFinalProcessing();
	}

	/**********************************
	* complete abr processing after status moved to final or was already final
	*1.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*2.	IF ValueOf( WWSEOLSEO-u: WWSEO.SPECBID) = 11457 (No) THEN
	*	a.	IF (LSEO: LSEOAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	*		(LSEO: LSEOAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	*		Set (LSEO: LSEOAVAILAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
	*CQ00006066-WI
	*	b.	IF (LSEO: LSEOAVAIL-d: AVAIL.AVAILTYPE) =  146 (Planned Availability) and 
	*	(AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020 (Final) and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New)
	* 	then Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report) 
	* 	c.	IF (LSEO: LSEOAVAIL-d: AVAIL.AVAILTYPE) =  149 (Last Order) and (AVAIL: AVAILANNA: ANNOUNCEMENT.STATUS) = 0020
	* 	(Final) and (AVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) =  14 ("End Of Life - Withdrawal from mktg") then
	* 	Set (AVAIL: AVAILANNA: ANNOUNCEMENT. QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report)
	*	ELSE
	*	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*	b.	Set QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report) CQ00006066-WI
	*3.	IF (LSEOBUNDLELSEO-u: LSEOBUNDLE.STATUS) = 0020 (Final) then
	*	Set (LSEOBUNDLELSEO-u: LSEOBUNDLE.EPIMSABRSTATUS) = ABRWAITODS (Wait for ODS Data)
	*/
	private void doFinalProcessing()
	{
		setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS);

		String specbid ="";
		EntityGroup eg = m_elist.getEntityGroup("WWSEO");

		if (eg.getEntityItemCount()>0){ // checks may not have been done
			EntityItem wwseoItem = eg.getEntityItem(0);
			specbid = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
			addDebug(wwseoItem.getKey()+" SPECBID: "+specbid);
		}

		if ("11457".equals(specbid)){  // is No
			eg = m_elist.getEntityGroup("ANNOUNCEMENT");
			for (int i=0; i<eg.getEntityItemCount(); i++){
				EntityItem annItem = eg.getEntityItem(i);
				String annstatus = getAttributeFlagEnabledValue(annItem, "ANNSTATUS");
				String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
				addDebug(annItem.getKey()+" status "+annstatus+" type "+anntype);
				if (annstatus==null || annstatus.length()==0){
					annstatus = STATUS_FINAL;
				}
				if (STATUS_FINAL.equals(annstatus)&& "19".equals(anntype)){
					addDebug(annItem.getKey()+" is Final and New");
					setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2,annItem);					
				}
			}
			//CQ00006066-WI go thru AVAILs to ANN	
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
		}else{
			//	a.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	        setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2);
	        //b.	Set QSMRPTABRSTATUS= QSM2XPERWEEK (Load QSM Change Report) CQ00006066-WI
	      	//setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", QSM2XPERWEEK);	   
	      	//CQ00016165
			setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValue("QSMRPTABRSTATUS"));
		}

		eg = m_elist.getEntityGroup("LSEOBUNDLE");
		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem item = eg.getEntityItem(i);
			String status = getAttributeFlagEnabledValue(item, "STATUS");
			addDebug(item.getKey()+" status "+status);
			if (status==null || status.length()==0){
				status = STATUS_FINAL;
			}
			if (STATUS_FINAL.equals(status)){
				setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS,item);
			}
		}
	}

    /**********************************
	* Note the ABR is only called when
	* DATAQUALITY transitions from 'Draft to Ready for Review',
	*	'Change Request to Ready for Review' and from 'Ready for Review to Final'
	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		//'Draft to Ready for Review' or 'Change Request to Ready for Review'
		// always do this now
		int cnt = getCount("WWSEOLSEO");
		if(cnt == 1) {
			EntityItem wwseoItem = m_elist.getEntityGroup("WWSEO").getEntityItem(0);
			//1.ValueOf(WWSEO.STATUS) = 0040 (Ready for Review) or 0020 (Final)
			//ErrorMessage  'has a' LD(WWSEO) NDN(WWSEO) 'that is not Ready for Review or Final'
			String status = getAttributeFlagEnabledValue(wwseoItem , "STATUS");
			addDebug(wwseoItem.getKey()+" check status "+status);
			if (status==null){
				status = STATUS_FINAL;
			}

			if (!STATUS_FINAL.equals(status) && !STATUS_R4REVIEW.equals(status)){
				addDebug(wwseoItem.getKey()+" is not Final or R4R");
				//NOT_R4R_FINAL_ERR = {0} {1} is not Ready for Review or Final.
				args[0] = wwseoItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(wwseoItem);
				addError("NOT_R4R_FINAL_ERR",args);
			}

			String specbid = getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
			addDebug(wwseoItem.getKey()+" SPECBID: "+specbid);
			if ("11457".equals(specbid)){  // is No
				//4.IF WWSEOLSEO-u: WWSEO.SPECBID = 11457 (No) THEN
				//a.	CountOf(LSEOAVAIL-d.AVAIL) > 0 where AVAIL.AVAILTYPE = 146 (Planned Availability)
				//ErrorMessage  'is not a Special Bid, but it does not have an' LD(AVAIL)
				//NOT_SPECBID_AVAIL_ERR = is not a Special Bid, but it does not have an {0}
				Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOAVAIL", "AVAIL");
				Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
				if (plannedavailVector.size()==0){
					args[0] = m_elist.getEntityGroup("AVAIL").getLongDescription();
					addError("NOT_SPECBID_AVAIL_ERR",args);
				}
				//b.	AllValuesOf(LSEOAVAIL-d:AVAIL. COUNTRYLIST) IN (LSEO.COUNTRYLIST)
				//ErrorMessage LD(AVAIL) NDN(AVAIL) LD(COUNTRYLIST) 'has a Country that is not in the' LD(LSEO) LD(COUNTRYLIST).
				checkCountry("LSEOAVAIL","D", true);

				//c.	IF WWSEOLSEO-u: WWSEO.SPECBID = 11457 (No) THEN  LSEO.SAPASSORTMODULE must exist for NLSID = 1
				//ErrorMessage  'is not a Special Bid, but it does not have a value for' LD(SAPASSORTMODULE)
				//NOT_SPECBID_VALUE_ERR = is not a Special Bid, but it does not have a value for {0}
				EANTextAttribute att = (EANTextAttribute)rootEntity.getAttribute("SAPASSORTMODULE");
				//true if information for the given NLSID is contained in the Text data
				if (att==null || (!((EANTextAttribute)att).containsNLS(1))) {
					args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
					addError("NOT_SPECBID_VALUE_ERR",args);
				} // end attr has this language
				availVct.clear();
				plannedavailVector.clear();
			}else{ //SPECBID=Yes
				int cnt2 = getCount("LSEOAVAIL");
				//3.	IF WWSEOLSEO-u: WWSEO.SPECBID = 11458 (Yes) THEN CountOf(LSEOAVAIL.AVAIL) = 0
				//ErrorMessage  'is a Special Bid, but it has an' LD(AVAIL)
				//SPECBID_AVAIL_ERR = is a Special Bid, but it has an {0}
				if (cnt2!=0){
					args[0] = m_elist.getEntityGroup("AVAIL").getLongDescription();
					addError("SPECBID_AVAIL_ERR",args);
				}
			}// end wwseo specbid=yes

			String strNow = m_db.getDates().getNow().substring(0, 10);
			//5.	AllValuesOf(LSEOPRODSTRUCT-d:PRODSTRUCT. WITHDRAWDATE) > NOW()
			//ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
			//WITHDRAWN_ERR = references a withdrawn {0} {1}
			Vector psvct =PokUtils.getAllLinkedEntities(rootEntity, "LSEOPRODSTRUCT", "PRODSTRUCT");
			for (int m=0; m<psvct.size(); m++){
				EntityItem psitem = (EntityItem)psvct.elementAt(m);
				String wdDate = PokUtils.getAttributeValue(psitem, "WITHDRAWDATE",", ", "", false);
				addDebug(psitem.getKey()+" WITHDRAWDATE: "+wdDate+" strNow: "+strNow);
				if (wdDate.length()>0 && wdDate.compareTo(strNow)<=0){
					args[0] = psitem.getEntityGroup().getLongDescription();
					args[1] = getNavigationName(psitem);
					addError("WITHDRAWN_ERR",args);
				}
			}
			psvct.clear();

			//6.	AllValuesOf(LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL:AVAIL.EFFECTIVEDATE > NOW()
			//	WHERE AVAIL.AVAILTYPE = 149 (Last Order).
			//ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
			checkWDAvails(rootEntity,"LSEOPRODSTRUCT","PRODSTRUCT","OOFAVAIL",strNow);

			//7.	AllValuesOf(LSEOSWPRODSTRUCT-d: SWPRODSTRUCT: SWPRODSTRUCTAVAIL:AVAIL.EFFECTIVEDATE > NOW()
			//	WHERE AVAIL.AVAILTYPE = 149 (Last Order).
			//ErrorMessage 'references a withdrawn' LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT)
			checkWDAvails(rootEntity,"LSEOSWPRODSTRUCT","SWPRODSTRUCT","SWPRODSTRUCTAVAIL",strNow);

			//8.	IF ValueOf(MODELWWSEO-u: MODEL.COFCAT) = 100 (Hardware) THEN
			//	CountOf(WWSEOLSEO-u: WWSEOPRODSTRUCT) + CountOf(LSEOPRODSTRUCT) > 0
			//	ErrorMessage 'must have at least one' LD(PRODSTRUCT)
			int wwseoPS = m_elist.getEntityGroup("WWSEOPRODSTRUCT").getEntityItemCount();
			int lseoPS = m_elist.getEntityGroup("LSEOPRODSTRUCT").getEntityItemCount();
			Vector mdlvct =PokUtils.getAllLinkedEntities(wwseoItem, "MODELWWSEO", "MODEL");
			for (int m=0; m<mdlvct.size(); m++){ // there should really only be one
				EntityItem mdlItem = (EntityItem)mdlvct.elementAt(m);
				String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
				addDebug(mdlItem.getKey()+" COFCAT: "+modelCOFCAT+" wwseoPS: "+wwseoPS+" lseoPS: "+lseoPS);
				// is it hw?
				if(HARDWARE.equals(modelCOFCAT)){
					if (wwseoPS+lseoPS ==0){
						args[0] = m_elist.getEntityGroup("PRODSTRUCT").getLongDescription();
						//MINIMUM_ERR = must have at least one {0}
						addError("MINIMUM_ERR",args);
					}
				}
			}

			mdlvct.clear();

			if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
			{
				if ("11457".equals(specbid)){  // is No
					//2.	IF WWSEOLSEO-u: WWSEO.SPECBID = 11457 (No) THEN  LSEO.SAPASSORTMODULE must exist for NLSID = 1
					//ErrorMessage  'is not a Special Bid, but it does not have a value for' LD(SAPASSORTMODULE)
					//NOT_SPECBID_VALUE_ERR = is not a Special Bid, but it does not have a value for {0}
					EANTextAttribute att = (EANTextAttribute)rootEntity.getAttribute("SAPASSORTMODULE");
					//true if information for the given NLSID is contained in the Text data
					if (att==null || (!((EANTextAttribute)att).containsNLS(1))) {
						args[0] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "SAPASSORTMODULE", "SAPASSORTMODULE");
						addError("NOT_SPECBID_VALUE_ERR",args);
					} // end attr has this language
				}

				//Status is Ready for Review
				//3.	ValueOf(WWSEO.STATUS) = 0020 (Final)
				//ErrorMessage  'has a' LD(WWSEO) NDN(WWSEO) 'that is not Final'
				checkStatus(wwseoItem);

				//4.	AllValuesOf(LSEOPRODSTRUCT-d: PRODSTRUCT.STATUS) = 0020 (Final) OR Empty
				//ErrorMessage NDN(PRODSTRUCT) 'is not Final'.
				Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOPRODSTRUCT", "PRODSTRUCT");
				addDebug("PRODSTRUCT from LSEOPRODSTRUCT found: "+psVct.size());
				checkStatus(psVct);
				psVct.clear();

				//5.	AllValuesOf(LSEOSWPRODSTRUCT-d: SWPRODSTRUCT.STATUS) = 0020 (Final) OR Empty
				//ErrorMessage NDN(SWPRODSTRUCT) 'is not Final'.
				psVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOSWPRODSTRUCT", "SWPRODSTRUCT");
				addDebug("SWPRODSTRUCT from LSEOSWPRODSTRUCT found: "+psVct.size());
				checkStatus(psVct);
				psVct.clear();

				//6.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEO.LSEOPUBDATEMTRGT where AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
				//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(LSEOPUBDATEMTRGT).
				//7.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEO.LSEOUNPUBDATEMTRGT where AVAIL.AVAILTYPE= 149 (Last Order)
				//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(LSEOUNPUBDATEMTRGT).
				checkAvailDates(rootEntity);
			}//end of else if(STATUS_R4REVIEW.equals(status))
		}else{
			EntityGroup eGrp = m_elist.getEntityGroup("WWSEO");
			args[0] = eGrp.getLongDescription();
			//1.	CountOf(WWSEOLSEO-u.WWSEO) = 1
			//ErrorMessage  'must have only one parent' LD(WWSEO)
			//REQUIRES_ONE_PARENT_ERR = must have only one parent {0}
			addError("REQUIRES_ONE_PARENT_ERR",args);
		}
	}

    /***********************************************
	*6.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEO.LSEOPUBDATEMTRGT where
	*	AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
	*ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(LSEOPUBDATEMTRGT).
	*7.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEO.LSEOUNPUBDATEMTRGT where
	*	AVAIL.AVAILTYPE= 149 (Last Order)
	*ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(LSEOUNPUBDATEMTRGT).
	*/
	private void checkAvailDates(EntityItem rootEntity) throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOAVAIL", "AVAIL");
		String pubDate = getAttributeValue(rootEntity, "LSEOPUBDATEMTRGT", "");
		String unpubDate = getAttributeValue(rootEntity, "LSEOUNPUBDATEMTRGT", "");
		addDebug("checkAvailDates "+rootEntity.getKey()+" LSEOPUBDATEMTRGT: "+pubDate+" LSEOUNPUBDATEMTRGT: "+
			unpubDate+" availVct: "+availVct.size());

		for (int ai=0; ai<availVct.size(); ai++){ // look at avails
			EntityItem avail = (EntityItem)availVct.elementAt(ai);
			String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
			String availtype = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
			addDebug("checkAvailDates "+avail.getKey()+" EFFECTIVEDATE: "+effDate+" AVAILTYPE: "+availtype);
			// 5.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) => LSEO. LSEOPUBDATEMTRGT where
			//	 	AVAIL.AVAILTYPE= 146 (Planned Availability) or 143 (First Order)
			if (pubDate.trim().length()>0){
				if((PLANNEDAVAIL.equals(availtype) || FIRSTORDERAVAIL.equals(availtype))
					&& effDate.length()>0 && effDate.compareTo(pubDate)<0){
					//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(LSEOPUBDATEMTRGT).
					//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
					args[0] = avail.getEntityGroup().getLongDescription()+" "+ getNavigationName(avail);
					args[1] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "LSEOPUBDATEMTRGT", "LSEOPUBDATEMTRGT");
					args[2] = "";
					args[3] = "";
					addError("EARLY_DATE_ERR",args);
				}
			}
			//6.	AllValuesOf(LSEOAVAIL-d: AVAIL.EFFECTIVEDATE) <= LSEO. LSEOUNPUBDATEMTRGT where
			//	AVAIL.AVAILTYPE= 149 (Last Order)
			if (unpubDate.trim().length()>0){
				if(LASTORDERAVAIL.equals(availtype)	&& effDate.length()>0 && effDate.compareTo(unpubDate)>0){
					//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is later than' LD(LSEOUNPUBDATEMTRGT).
					//LATER_DATE_ERR = {0} {1} {2} is later than the {3} {4}
					args[0] = avail.getEntityGroup().getLongDescription();
					args[1] = getNavigationName(avail);
					args[2] = "";
					args[3] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "LSEOUNPUBDATEMTRGT", "LSEOUNPUBDATEMTRGT");
					args[4] = "";
					addError("LATER_DATE_ERR",args);
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
        String desc =  "LSEO ABR.";

        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.36";
    }

}
