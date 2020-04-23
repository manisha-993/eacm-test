// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.util.*;


/**********************************************************************************
* MODELABRSTATUS class
*
* From "SG FS ABR Data Quality 20080722.doc"
*
* The Model (MODEL) is part of a complex data structure and hence a Virtual Entity (VE)
* is used to describe the applicable structure for the Data Quality checks and another
* VE for the SAPL feed.
*
* Process
* 	There may be a SAPL XML Feed to OIDH (LEDGER) of this data which is determined after
* the data quality checks (if any) are complete.
*
* The ABR produces a Report
*
* The ABR sets its unique attribute (MODELABRSTATUS)
MODELABRSTATUS_class=COM.ibm.eannounce.abr.sg.MODELABRSTATUS
MODELABRSTATUS_enabled=true
MODELABRSTATUS_idler_class=A
MODELABRSTATUS_keepfile=true
MODELABRSTATUS_report_type=DGTYPE01
MODELABRSTATUS_vename=EXRPT3MODEL1
MODELABRSTATUS_CAT1=RPTCLASS.MODELABRSTATUS
MODELABRSTATUS_CAT2=
MODELABRSTATUS_CAT3=RPTSTATUS
MODELABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
*/
// MODELABRSTATUS.java,v
// Revision 1.43  2009/10/12 12:33:02  wendy
// restore correct code level
//
// Revision 1.40  2008/07/23 13:26:10  wendy
// MN36320529 - Incorrectly checks MODELs that are not Hardware and requires a hardware feature.
//
// Revision 1.39  2008/05/29 15:53:49  wendy
// Backout queueing ADSABRSTATUS
//
// Revision 1.38  2008/05/06 21:23:47  wendy
// changes for SG FS ABR Data Quality 20080506.doc
//
// Revision 1.37  2008/05/01 12:02:45  wendy
// updates for SG FS ABR Data Quality 20080430.doc
//
// Revision 1.36  2008/04/24 20:54:32  wendy
// "SG FS ABR Data Quality 20080422.doc" spec updates
//
// Revision 1.35  2008/04/11 19:13:10  wendy
// changes for spec SG FS ABR Data Quality 200803xx.doc
//
// Revision 1.34  2008/02/13 19:58:49  wendy
// Make ABRWAITODSx into constants, allow easier change in future
//
// Revision 1.33  2008/01/21 17:26:55  wendy
// Default null status to final
//
// Revision 1.32  2007/12/19 17:18:37  wendy
// VE not needed if already FINAL
//
// Revision 1.31  2007/12/12 17:00:24  wendy
// spec chg "Changed the value used to queue WWPRT since it uses VIEWs in the "legacy" ODS (i.e. not the "approved data ods")"
//
// Revision 1.30  2007/11/27 22:07:31  wendy
// SG FS ABR Data Quality 20071127.doc chgs and TIR79CNTQ
//
// Revision 1.29  2007/11/16 22:17:21  nancy
// Chgs for spec "SG FS ABR Data Quality 20071115.doc"
//
// Revision 1.28  2007/10/25 20:06:32  wendy
// Spec changes
//
// Revision 1.27  2007/10/23 17:47:12  wendy
// Spec changes
//
// Revision 1.26  2007/10/15 20:22:35  wendy
// QueueSAPL when PDHDOMAIN is not in list of domains
//
// Revision 1.25  2007/09/14 17:43:55  wendy
// Updated for GX
//
// Revision 1.24  2007/08/17 16:02:10  wendy
// RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
// from 'SG FS xSeries ABRs 20070803.doc'
//
// Revision 1.23  2007/06/18 18:31:44  wendy
// TIR 747QEJ- XML Mapping SS updated
//
// Revision 1.22  2007/06/13 21:18:21  wendy
// TIR73RS77 -XML Mapping updated
//
// Revision 1.21  2007/05/16 17:19:15  wendy
// TIR 7STP5L - Routing of XML correction required, spec chg
//
// Revision 1.20  2007/05/04 13:40:05  wendy
// RQ022507238 chgs
//
// Revision 1.19  2007/04/20 21:48:59  wendy
// RQ0417075638 updates
//
// Revision 1.18  2007/04/02 17:38:50  wendy
// Modified for SAPL
//
public class MODELABRSTATUS extends DQABRSTATUS
{
    private Object[] args = new String[10];

  	/**********************************
	* needs VE for all except already final
	*/
	protected boolean isVEneeded(String statusFlag) {
		return !(STATUS_FINAL.equals(statusFlag));
	}

	/**********************************
	* complete abr processing when status is already final; (dq was final too)
	* E.	Status = Final
	*
	* Data Quality checking is not required; however, the data may need to be sent via XML to SAPL.
	* This occurs when data is sent to SAPL but there is a downstream failure which requires the XML
	* to be sent again. The UI allows the setting of SAPL to Send which will queue this ABR.
	*
	* 1.	Set SAPLABRSTATUS = 0020 (Queued)
	2.	Set ADSABRSTATUS = 0020 (Queued)
	*/
	protected void doAlreadyFinalProcessing() {
        setFlagValue(m_elist.getProfile(),"SAPLABRSTATUS", SAPLABR_QUEUED);
//        setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
	}

    /**********************************
    * complete abr processing after status moved to readyForReview; (status was chgreq)
	* D.	STATUS changed to Ready for Review
	* 1.	Set COMPATGENABR = 0020 (Queued)
	* 2.	PropagateOStoWWSEO
	3.	Set ADSABRSTATUS = 0020 (Queued)
    */
    protected void completeNowR4RProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
         setFlagValue(m_elist.getProfile(),"COMPATGENABR", ABR_QUEUED);
         propagateOStoWWSEO(m_elist.getParentEntityGroup().getEntityItem(0),m_elist.getEntityGroup("WWSEO"));
//         setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
    }

	/**********************************
	* complete abr processing after status moved to final; (status was r4r)
	*	C.	STATUS changed to Final
	*1.	IF FirstTime(LSEOBUNDLE.STATUS = 0020 (Final)) THEN  ELSE obtain the value for SAPASSORTMODULE
	*	that was in effect at the 'last DTS that STATUS was Final' and set SAPASSORTMODULEPRIOR equal to
	*	that value.
	*2.	PropagateOStoWWSEO
	*3.	QueueSAPL
	*4.	Set COMPATGENABR = 0020 (Queued)
	*5.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*6.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	7.	Set ADSABRSTATUS = 0020 (Queued)
	*8.	IF (MODEL: MODELAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	* (MODEL: MODELAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	* Set (MODEL: MODELAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
        checkAssortModule();
        propagateOStoWWSEO(m_elist.getParentEntityGroup().getEntityItem(0),m_elist.getEntityGroup("WWSEO"));
		queueSapl();
        setFlagValue(m_elist.getProfile(),"COMPATGENABR", ABR_QUEUED);
        setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2);
        setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS);
//        setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);

		EntityGroup	eg = m_elist.getEntityGroup("ANNOUNCEMENT");
		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem annItem = eg.getEntityItem(i);
			String annstatus = getAttributeFlagEnabledValue(annItem, "ANNSTATUS");
			String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
			addDebug(annItem.getKey()+" status "+annstatus+" type "+anntype);
            if(annstatus == null || annstatus.length()==0) {
                annstatus = STATUS_FINAL;
            }
			if (STATUS_FINAL.equals(annstatus) && "19".equals(anntype)){
				addDebug(annItem.getKey()+" is Final and New");
				setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2,annItem);
			}
		}
	}

	/**********************************
	* complete abr processing after status moved to final; (status was r4r) for
	* those ABRs that have domains that are not in the list of domains
	*/
	protected void completeNowFinalProcessingForOtherDomains() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		queueSapl();
	}

	/**********************************
	* From "SG FS ABR Data Quality 20071024.doc"
	* A.	STATUS = Draft | Change Request
	*
1.	IF ValueOf(MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODEL.COFSUBCAT) = 126 (System) THEN
	a.	CountOf(MDLCGMDL-u: MODELCG) => 1
	ErrorMessage 'must be in at least one' LD(MODELCG)
2. IF ValueOf(MODEL.COFCAT) = 102 (Service) THEN
	a.	CountOf(MDLCGMDL-u: MODELCG) = 0
	ErrorMessage 'is a Service and must not be in a' LD(MODELCG)
	b.	CountOf(SEOCGSEO-u: SEOCG) = 0
	ErrorMessage 'is a Service and must not be in a' LD(SEOCG)
3.	CountOf( MODEL.AVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
	ErrorMessage 'must have at least one Planned Availability'
	*
	*B.	STATUS = Ready for Review
	*
1.	Draft | Change Request Criteria
2.	IF ValueOf(MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODEL.COFSUBCAT) = 126 (System) THEN
	a.	CountOf(MODELIMG-d: IMG) => 1
		ErrorMessage 'must have at least one' LD(IMG)
	b.	CountOf(MODELWARR-d. WARR) => 1
		ErrorMessage 'must have at least one' LD(WARR)
3.	IF ValueOf(MODEL.COFCAT) = 100 (Hardware) THEN
	a.	 MODELMONITOR.QTY * CountOf(MODELMONITOR-d: MONITOR) <= 1
		ErrorMessage 'has more than one' LD(MONITOR)
	b.	AllValuesOf(MODELAVAIL-d: AVAIL.ANNCODENAME) IN (PRODSTRUCT-u: OOFAVAIL-d: AVAIL.ANNCODENAME) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
		ErrorMessage LD(MODEL) NDN(MODEL) LD(AVAIL) NDN(AVAIL) LD(ANNCODENAME) "does not announce any" LD(FEATURE)
	c.	IF NotNull(PRODSTRUCT.ANNDATE) THEN
		-	 ValueOf(PRODSTRUCT.ANNDATE) >= (PRODSTRUCT-u: FEATURE.FIRSTANNDATE)
		ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(FEATURE) LD(FIRSTANNDATE)
		-	 ValueOf(PRODSTRUCT.ANNDATE) >= (MODEL.ANNDATE)
		ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(MODEL) LD(ANNDATE)
		-	AllValuesOf(OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT.ANNDATE) WHERE ValueOf(AVAIL.AVAILTYPE) = 146 (Planned Availability)
		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
	d.	ELSE
		-	AllValuesOf (OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT-u: FEATURE.FIRSTANNDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
		ErrorMessage LD(FEATURE) NDN(PRODSTRUCT) LD(FIRSTANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
4.	AllValuesOf(MODELAVAIL-d: AVAIL.EFFECTIVEDATE) => MODEL.ANNDATE WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	ErrorMessage LD(MODEL) NDN(MODEL) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
5.	AllValuesOf(PRODSTRUCT-u: OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => (PRODSTRUCT-u: OOFAVAIL-d: AVAILANNA-a: ANNOUNCEMENT.ANNDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than the" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE)
6.	AllValuesOf(PRODSTRUCT-u: OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => (PRODSTRUCT-u: OOFAVAIL-d: AVAILANNA-a: ANNOUNCEMENT.ANNDATE) WHERE AVAIL.AVAILTYPE = 143 (First Order)
	ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than the" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE)

	*
	*C.	STATUS changed to Ready for Review
	*1.	Set COMPATGENABR = 0020 (Queued)
	*2.	PropagateOStoWWSEO
	3.	Set ADSABRSTATUS = 0020 (Queued)
	*
	*D.	STATUS changed to Final
	*1.	IF FirstTime(MODEL.STATUS = 0020 (Final)) THEN  ELSE obtain the value for SAPASSORTMODULE that was in effect at the 'last DTS that STATUS was Final' and set SAPASSORTMODULEPRIOR equal to that value.
	*2.	PropagateOStoWWSEO
	*3.	QueueSAPL
	*4.	Set COMPATGENABR = 0020 (Queued)
	*5.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*6.	Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	*7.	IF (MODEL: MODELAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	* (MODEL: MODELAVAIL: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	* Set (MODEL: MODELAVAIL: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
	*
	*E.	Status = Final
	*Data Quality checking is not required; however, the data may need to be sent via XML to SAPL. This occurs when data is sent to SAPL but there is a downstream failure which requires the XML to be sent again. The UI allows the setting of SAPL to Send which will queue this ABR.
	*1.	QueueSAPL
	*/
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		int cnt =0;
		String modelCOFCAT = getAttributeFlagEnabledValue(rootEntity, "COFCAT");
		if(modelCOFCAT == null)	{
			modelCOFCAT = "";
		}
		addDebug(rootEntity.getKey()+" COFCAT: "+modelCOFCAT);

		// always do some of these checks now
		if(HARDWARE.equals(modelCOFCAT)) {
			checkHardware(rootEntity,statusFlag);
		}

		if(SERVICE.equals(modelCOFCAT)) {
			//2.	IF ValueOf(MODEL.COFCAT) = 102 (Service) THEN
			//	a.	CountOf(MDLCGMDL-u: MODELCG) = 0
			//	ErrorMessage 'is a Service and must not be in a' LD(MODELCG)
			cnt = getCount("MDLCGMDL");
			if(cnt > 0)	{
				EntityGroup eGrp = m_elist.getEntityGroup("MODELCG");
				//SVC_SEOCG_ERR = is a Service and must not be in a {0}
				args[0] = eGrp.getLongDescription();
				addError("SVC_SEOCG_ERR",args);
			}
			//	b.	CountOf(SEOCGSEO-u: SEOCG) = 0
			//	ErrorMessage 'is a Service and must not be in a' LD(SEOCG)
			cnt = getCount("SEOCGSEO");
			if(cnt > 0)	{
				EntityGroup eGrp = m_elist.getEntityGroup("SEOCG");
				//SVC_SEOCG_ERR = is a Service and must not be in a {0}
				args[0] = eGrp.getLongDescription();
				addError("SVC_SEOCG_ERR",args);
			}
		} // end svc model

		//always 3.CountOf( MODELAVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
		//ErrorMessage 'must have at least one Planned Availability'
		//only R4R does this
		//4.AllValuesOf(MODELAVAIL-d: AVAIL.EFFECTIVEDATE) => MODEL.ANNDATE WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
		//ErrorMessage LD(MODEL) NDN(MODEL) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
		checkModelAvails(rootEntity, statusFlag);

        if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
        {
			//5.	AllValuesOf(PRODSTRUCT-u: OOFAVAIL-d: AVAIL.EFFECTIVEDATE) =>
			//	(PRODSTRUCT-u: OOFAVAIL-d: AVAILANNA-a: ANNOUNCEMENT.ANNDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
			//ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than the" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE)
			//6.	AllValuesOf(PRODSTRUCT-u: OOFAVAIL-d: AVAIL.EFFECTIVEDATE) =>
			//	(PRODSTRUCT-u: OOFAVAIL-d: AVAILANNA-a: ANNOUNCEMENT.ANNDATE) WHERE AVAIL.AVAILTYPE = 143 (First Order)
			//ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than the" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE)
			checkAvailDates();
		}
	}

    /***********************************************
	b.	AllValuesOf(MODELAVAIL-d: AVAIL.ANNCODENAME) IN (PRODSTRUCT-u: OOFAVAIL-d: AVAIL.ANNCODENAME)
	WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	ErrorMessage LD(MODEL) NDN(MODEL) LD(AVAIL) NDN(AVAIL) LD(ANNCODENAME) "does not announce any" LD(FEATURE)
	*/
	private void checkAnn(EntityItem rootEntity) throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		// get AVAIL from MODELAVAIL
		Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "MODELAVAIL", "AVAIL");
		Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
		addDebug("checkAnn "+rootEntity.getKey()+" availVct "+availVct.size()+" plannedavailVector "+plannedavailVector.size());

		EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
		// get AVAIL from OOFAVAIL
		Vector psavailVct = PokUtils.getAllLinkedEntities(psGrp, "OOFAVAIL", "AVAIL");
		Vector plannedPsAvailVector = PokUtils.getEntitiesWithMatchedAttr(psavailVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
		addDebug("checkAnn psavailVct: "+psavailVct.size()+" plannedPsAvailVector: "+plannedPsAvailVector.size());

		Hashtable mdlTbl = new Hashtable();
		HashSet psSet = new HashSet();
		for (int ai=0; ai<plannedavailVector.size(); ai++){ // look at mdlavails
			EntityItem avail = (EntityItem)plannedavailVector.elementAt(ai);
			EANFlagAttribute fAtt = (EANFlagAttribute)avail.getAttribute("ANNCODENAME");
			addDebug("modelavail "+avail.getKey()+" ANNCODENAME "+
				PokUtils.getAttributeFlagValue(avail, "ANNCODENAME")+NEWLINE);
			if (fAtt!=null && fAtt.toString().length()>0){
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						mdlTbl.put(mfArray[i].getFlagCode(), avail);
					}  // metaflag is selected
				}// end of flagcodes
			}
		}
		for (int ai=0; ai<plannedPsAvailVector.size(); ai++){ // look at psavails
			EntityItem avail = (EntityItem)plannedPsAvailVector.elementAt(ai);
			EANFlagAttribute fAtt = (EANFlagAttribute)avail.getAttribute("ANNCODENAME");
			addDebug("psavail "+avail.getKey()+" ANNCODENAME "+
				PokUtils.getAttributeFlagValue(avail, "ANNCODENAME")+NEWLINE);
			if (fAtt!=null && fAtt.toString().length()>0){
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						psSet.add(mfArray[i].getFlagCode());
					}  // metaflag is selected
				}// end of flagcodes
			}
		}

		// look for a match in prodstruct->plannedavail to the model->plannedavail
		Iterator itr = mdlTbl.keySet().iterator();
		while(itr.hasNext()) {
			String anncodename = (String) itr.next();
			if(!psSet.contains(anncodename))  {
				EntityItem avail = (EntityItem)mdlTbl.get(anncodename);

				//	ErrorMessage LD(AVAIL) NDN(AVAIL) LD(ANNCODENAME) "does not announce any" LD(FEATURE)
				//NO_ANNOUNCE_FEATURE = {0} {1} {2} does not announce any {3}
				args[0] = avail.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(avail);
				args[2] = PokUtils.getAttributeDescription(avail.getEntityGroup(), "ANNCODENAME", "ANNCODENAME");
				args[3] = m_elist.getEntityGroup("FEATURE").getLongDescription();
				addError("NO_ANNOUNCE_FEATURE",args);
			}
		}

		mdlTbl.clear();
		psSet.clear();
	}

    /***********************************************
	5.	AllValuesOf(PRODSTRUCT-u: OOFAVAIL-d: AVAIL.EFFECTIVEDATE) =>
		(PRODSTRUCT-u: OOFAVAIL-d: AVAILANNA-a: ANNOUNCEMENT.ANNDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than the" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE)
	6.	AllValuesOf(PRODSTRUCT-u: OOFAVAIL-d: AVAIL.EFFECTIVEDATE) =>
		(PRODSTRUCT-u: OOFAVAIL-d: AVAILANNA-a: ANNOUNCEMENT.ANNDATE) WHERE AVAIL.AVAILTYPE = 143 (First Order)
	ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than the" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE)
	*/
	private void checkAvailDates() throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
        for(int i = 0; i < psGrp.getEntityItemCount(); i++) {
			EntityItem prodstructItem = psGrp.getEntityItem(i);
			// get AVAIL from OOFAVAIL
			Vector availVct = PokUtils.getAllLinkedEntities(prodstructItem, "OOFAVAIL", "AVAIL");
			addDebug("checkAvailDates "+prodstructItem.getKey()+" availVct: "+availVct.size());

			for (int ai=0; ai<availVct.size(); ai++){ // look at avails
				EntityItem avail = (EntityItem)availVct.elementAt(ai);
				String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
				String availtype = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
				addDebug("checkAvailDates "+avail.getKey()+" EFFECTIVEDATE: "+effDate+" AVAILTYPE: "+availtype);
				if((PLANNEDAVAIL.equals(availtype) || FIRSTORDERAVAIL.equals(availtype))
					&& effDate.length()>0){

					// get ANNOUNCEMENT from AVAILANNA
					Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
					addDebug("checkAvailDates "+avail.getKey()+" annVct: "+annVct.size());
					for (int a=0; a<annVct.size(); a++){
						EntityItem annitem = (EntityItem)annVct.elementAt(a);
						String annDate = PokUtils.getAttributeValue(annitem, "ANNDATE",", ", "", false);
						addDebug("checkAvailDates "+annitem.getKey()+" annDate: "+annDate);
						if(annDate.length()>0 && effDate.compareTo(annDate)<0){
							//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is earlier than' LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE)
							//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
							args[0] = avail.getEntityGroup().getLongDescription()+" "+ getNavigationName(avail);
							args[1] = annitem.getEntityGroup().getLongDescription();
							args[2] = getNavigationName(annitem);
							args[3] = PokUtils.getAttributeDescription(annitem.getEntityGroup(), "ANNDATE", "ANNDATE");
							addError("EARLY_DATE_ERR",args);
						}
					}
					annVct.clear();
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
        String desc =  "MODEL ABR";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.43";
    }

    /***********************************************
    * MODEL.COFCAT=Hardware
    MN36320529 - Incorrectly checks MODELs that are not Hardware and requires a hardware feature.
    *
1.	IF ValueOf(MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODEL.COFSUBCAT) = 126 (System) THEN
	a.	CountOf(MDLCGMDL-u: MODELCG) => 1
	ErrorMessage 'must be in at least one' LD(MODELCG)
if R4R
2.	IF ValueOf(MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODEL.COFSUBCAT) = 126 (System) THEN
	a.	CountOf(MODELIMG-d: IMG) => 1
	ErrorMessage 'must have at least one' LD(IMG)
	b.	CountOf(MODELWARR-d. WARR) => 1
	ErrorMessage 'must have at least one' LD(WARR)

3.	IF ValueOf(MODEL.COFCAT) = 100 (Hardware) THEN
	a.	 MODELMONITOR.QTY * CountOf(MODELMONITOR-d: MONITOR) <= 1
	ErrorMessage 'has more than one' LD(MONITOR)
	b.	AllValuesOf(MODELAVAIL-d: AVAIL.ANNCODENAME) IN (PRODSTRUCT-u: OOFAVAIL-d: AVAIL.ANNCODENAME) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	ErrorMessage LD(MODEL) NDN(MODEL) LD(AVAIL) NDN(AVAIL) LD(ANNCODENAME) "does not announce any" LD(FEATURE)
	c.	IF NotNull(PRODSTRUCT.ANNDATE) THEN
	-	 ValueOf(PRODSTRUCT.ANNDATE) >= (PRODSTRUCT-u: FEATURE.FIRSTANNDATE)
	ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(FEATURE) LD(FIRSTANNDATE)
	-	 ValueOf(PRODSTRUCT.ANNDATE) >= (MODEL.ANNDATE)
	ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(MODEL) LD(ANNDATE)
	-	AllValuesOf(OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT.ANNDATE) WHERE ValueOf(AVAIL.AVAILTYPE) = 146 (Planned Availability)
	ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
	d.	ELSE
	-	AllValuesOf (OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT-u: FEATURE.FIRSTANNDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	ErrorMessage LD(FEATURE) NDN(PRODSTRUCT) LD(FIRSTANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)

    */
    private void checkHardware(EntityItem rootEntity, String statusFlag) throws MiddlewareException, java.sql.SQLException
    {
		String modelCOFSUBCAT = getAttributeFlagEnabledValue(rootEntity, "COFSUBCAT");

		if(modelCOFSUBCAT == null) {
			modelCOFSUBCAT = "";
		}
		addDebug("checkHardware "+rootEntity.getKey()+" COFSUBCAT: "+modelCOFSUBCAT);
		if(SYSTEM.equals(modelCOFSUBCAT)) {
			int cnt=0;
			if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
			{
				//2.	IF ValueOf(MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODEL.COFSUBCAT) = 126 (System) THEN
				//a.	CountOf(MODELIMG-d: IMG) => 1
				//ErrorMessage 'must have at least one' LD(IMG)
				cnt = getCount("MODELIMG");
				if(cnt == 0)	{
					EntityGroup eGrp = m_elist.getEntityGroup("IMG");
					//MINIMUM_ERR = must have at least one {0}
					args[0] = eGrp.getLongDescription();
					addError("MINIMUM_ERR",args);
				}
				//b.	CountOf(MODELWARR-d. WARR) => 1
				//ErrorMessage 'must have at least one' LD(WARR)
				cnt = getCount("MODELWARR");
				if(cnt == 0)	{
					EntityGroup eGrp = m_elist.getEntityGroup("WARR");
					//MINIMUM_ERR = must have at least one {0}
					args[0] = eGrp.getLongDescription();
					addError("MINIMUM_ERR",args);
				}
			}
			// do this for R4R or chgreq or draft
			//1.a.	CountOf(MDLCGMDL-u: MODELCG) => 1
			//ErrorMessage 'must be in at least one' LD(MODELCG)
			cnt = getCount("MDLCGMDL");
			if(cnt == 0)	{
				EntityGroup eGrp = m_elist.getEntityGroup("MODELCG");
				//MUST_BE_IN_ATLEAST_ONE_ERR = must be in at least one {0}
				args[0] = eGrp.getLongDescription();
				addError("MUST_BE_IN_ATLEAST_ONE_ERR",args);
			}
		}

		if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
		{
			//3.IF ValueOf(MODEL.COFCAT) = 100 (Hardware) THEN
			//		- MODELMONITOR.QTY * CountOf(MODELMONITOR-d: MONITOR) <= 1
			//		ErrorMessage 'has more than one' LD(MONITOR)
			int cnt = getCount("MODELMONITOR");
			if(cnt > 1)	{
				EntityGroup eGrp = m_elist.getEntityGroup("MONITOR");
				//MORE_THAN_ONE_ERR = Has more than one {0}
				args[0] = eGrp.getLongDescription();
				addError("MORE_THAN_ONE_ERR",args);
			}

			//b.	AllValuesOf(MODELAVAIL-d: AVAIL.ANNCODENAME) IN (PRODSTRUCT-u: OOFAVAIL-d: AVAIL.ANNCODENAME)
			//	WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
			//ErrorMessage LD(MODEL) NDN(MODEL) LD(AVAIL) NDN(AVAIL) LD(ANNCODENAME) "does not announce any" LD(FEATURE)
			checkAnn(rootEntity);

			//	c.IF (PRODSTRUCT.ANNDATE) <> Null THEN
			//	 	-ValueOf(PRODSTRUCT.ANNDATE) >= (PRODSTRUCT-u: FEATURE.FIRSTANNDATE)
			// 		ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(FEATURE) LD(FIRSTANNDATE)
			// 		-ValueOf(PRODSTRUCT.ANNDATE) >= (MODEL.ANNDATE)
			// 		ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(MODEL) LD(ANNDATE)
			//		- AllValuesOf(OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT.ANNDATE) WHERE ValueOf(AVAIL.AVAILTYPE) = 146 (Planned Availability)
			//		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
			//	d.	ELSE (PRODSTRUCT.ANNDATE is null)
			//		-	AllValuesOf (OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT-u: FEATURE.FIRSTANNDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
			//		ErrorMessage LD(FEATURE) NDN(PRODSTRUCT) LD(FIRSTANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
			checkAnnDate(rootEntity);
		}
    }

    /***********************************************
	*3	a.	IF (PRODSTRUCT.ANNDATE) <> Null THEN
	*		-	ValueOf(PRODSTRUCT.ANNDATE) >= (PRODSTRUCT-u: FEATURE.FIRSTANNDATE)
	*		ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(FEATURE) LD(FIRSTANNDATE)
	*		-	ValueOf(PRODSTRUCT.ANNDATE) >= (MODEL.ANNDATE)
	*		ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(ANNDATE)
	*		-	AllValuesOf(OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT.ANNDATE) WHERE ValueOf(AVAIL.AVAILTYPE) = 146 (Planned Availability)
	*		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
	*		-	MODELMONITOR.QTY * CountOf(MODELMONITOR-d: MONITOR) <= 1
	*		ErrorMessage 'has more than one' LD(MONITOR)
	*	b.	ELSE (PRODSTRUCT.ANNDATE is null)
	*		-	AllValuesOf (OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT-u: FEATURE.FIRSTANNDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	*		ErrorMessage LD(FEATURE) NDN(PRODSTRUCT) LD(FIRSTANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
	*
    */
    private void checkAnnDate(EntityItem rootEntity) throws MiddlewareException, java.sql.SQLException
    {
        String modelAnnDate = getAttributeValue(rootEntity, "ANNDATE", "");
		addDebug("checkAnnDate "+rootEntity.getKey()+" ANNDATE: "+modelAnnDate);
		EntityGroup psGrp = m_elist.getEntityGroup("PRODSTRUCT");
        for(int i = 0; i < psGrp.getEntityItemCount(); i++)
        {
			EntityItem prodstructItem = psGrp.getEntityItem(i);
			// get AVAIL from OOFAVAIL
			Vector availVct = PokUtils.getAllLinkedEntities(prodstructItem, "OOFAVAIL", "AVAIL");
			String annDate = getAttributeValue(prodstructItem, "ANNDATE", "");
			addDebug("checkAnnDate "+prodstructItem.getKey()+" ANNDATE: "+annDate+" availVct: "+availVct.size());
			EntityItem featureItem = (EntityItem) getUpLinkEntityItem(prodstructItem, "FEATURE");
			String featureFirstAnnDate = getAttributeValue(featureItem, "FIRSTANNDATE", "");
			addDebug("checkAnnDate "+featureItem.getKey()+" FIRSTANNDATE: "+featureFirstAnnDate);

			//a.IF (PRODSTRUCT.ANNDATE) <> Null THEN
			if(annDate.length()>0)
			{
				//ValueOf(PRODSTRUCT.ANNDATE) >= (PRODSTRUCT-u: FEATURE.FIRSTANNDATE)
				//ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(FEATURE) LD(FIRSTANNDATE)
				if(featureFirstAnnDate.length()>0 && annDate.compareTo(featureFirstAnnDate) < 0)
				{
					//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
					args[0] = psGrp.getLongDescription()+" "+
						PokUtils.getAttributeDescription(psGrp, "ANNDATE", "ANNDATE");
					args[1] = featureItem.getEntityGroup().getLongDescription();
					args[2] = PokUtils.getAttributeDescription(featureItem.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE");
					args[3] = "";
					addError("EARLY_DATE_ERR",args);
				}

				//ValueOf(PRODSTRUCT.ANNDATE) >= (MODEL.ANNDATE)
				//ErrorMessage LD(PRODSTRUCT) LD(ANNDATE) 'is earlier than the' LD(MODEL) LD(ANNDATE)
				if(modelAnnDate.length()>0 && annDate.compareTo(modelAnnDate) < 0)
				{
					//EARLY_DATE_ERR = {0} is earlier than the {1} {2} {3}
					args[0] = psGrp.getLongDescription()+" "+
						PokUtils.getAttributeDescription(psGrp, "ANNDATE", "ANNDATE");
					args[1] = rootEntity.getEntityGroup().getLongDescription();
					args[2] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "ANNDATE", "ANNDATE");
					args[3] = "";
					addError("EARLY_DATE_ERR",args);
				}

				//-	AllValuesOf(OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT.ANNDATE) WHERE ValueOf(AVAIL.AVAILTYPE) = 146 (Planned Availability)
				//ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
				for (int ai=0; ai<availVct.size(); ai++){ // look at avails
					EntityItem avail = (EntityItem)availVct.elementAt(ai);
					String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
					String availtype = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
					addDebug("checkAnnDate "+avail.getKey()+" EFFECTIVEDATE: "+effDate+" AVAILTYPE: "+availtype);
					// check availtype too
					if(PLANNEDAVAIL.equals(availtype) &&
						effDate.length()>0 && effDate.compareTo(annDate)<0){
						// 	ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(ANNDATE) 'is later than' LD(AVAIL) NDN(AVAIL)
						//LATER_DATE_ERR = {0} {1} {2} is later than the {3} {4}
						args[0] = prodstructItem.getEntityGroup().getLongDescription();
						args[1] = getNavigationName(prodstructItem);
						args[2] = PokUtils.getAttributeDescription(prodstructItem.getEntityGroup(), "ANNDATE", "ANNDATE");
						args[3] = avail.getEntityGroup().getLongDescription();
						args[4] = getNavigationName(avail);
						addError("LATER_DATE_ERR",args);
					}
				}
			}else{ // no PRODSTRUCT.ANNDATE
				addDebug("No ANNDATE found for "+prodstructItem.getKey());
				//b.	ELSE (PRODSTRUCT.ANNDATE is null)
				//-	AllValuesOf (OOFAVAIL-d: AVAIL.EFFECTIVEDATE) => ValueOf(PRODSTRUCT-u: FEATURE.FIRSTANNDATE)
				// WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
				//ErrorMessage LD(FEATURE) NDN(PRODSTRUCT) LD(FIRSTANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
				if(featureFirstAnnDate.length()>0){
					for (int ai=0; ai<availVct.size(); ai++){ // look at avails
						EntityItem avail = (EntityItem)availVct.elementAt(ai);
						addDebug("checkAnnDate no PRODSTRUCT.ANNDATE checking "+avail.getKey());
						if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
							String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
							addDebug("checkAnnDate plannedavail "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
							if (effDate.length()>0 && effDate.compareTo(featureFirstAnnDate)<0){
								//ErrorMessage LD(FEATURE) NDN(PRODSTRUCT) LD(FIRSTANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
								//LATER_DATE_ERR = {0} {1} {2} is later than the {3} {4}
								args[0] = featureItem.getEntityGroup().getLongDescription();
								args[1] = getNavigationName(prodstructItem);
								args[2] = PokUtils.getAttributeDescription(featureItem.getEntityGroup(), "FIRSTANNDATE", "FIRSTANNDATE");
								args[3] = avail.getEntityGroup().getLongDescription();
								args[4] = getNavigationName(avail);
								addError("LATER_DATE_ERR",args);
							}
						} //end of plannedavail
					} // end avails
				} // FIRSTANNDATE exists
			}
        }
    }

    /***********************************************
    *3.	CountOf( MODELAVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
    *ErrorMessage 'must have at least one Planned Availability'
    R4R only
    *4.AllValuesOf(MODELAVAIL-d: AVAIL.EFFECTIVEDATE) => MODEL.ANNDATE WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	*ErrorMessage LD(MODEL) NDN(MODEL) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
    */
    private void checkModelAvails(EntityItem rootEntity, String statusFlag) throws MiddlewareException, java.sql.SQLException
    {
		// get AVAIL from MODELAVAIL
		Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "MODELAVAIL", "AVAIL");
		Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);//Planned Availability
		addDebug("checkModelAvails "+rootEntity.getKey()+" availVct "+availVct.size()+" plannedavailVector "+plannedavailVector.size());
		availVct.clear();
		if (plannedavailVector.size()==0)
        {
			//MINIMUM_ERR = must have at least one {0}
			args[0] = "Planned Availability";
			addError("MINIMUM_ERR",args);
        }else if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final' do these checks too
        {
			// check dates
        	String modelAnnDate = getAttributeValue(rootEntity, "ANNDATE", "");
			addDebug("checkModelAvails "+rootEntity.getKey()+" ANNDATE: "+modelAnnDate);
			if(modelAnnDate.length()>0){
				for (int i=0; i<plannedavailVector.size(); i++){
					EntityItem avail = (EntityItem)plannedavailVector.elementAt(i);
					String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
					addDebug("checkModelAvails "+avail.getKey()+" EFFECTIVEDATE: "+effDate);

					if(effDate.length()>0 && effDate.compareTo(modelAnnDate)<0){
						// ErrorMessage LD(MODEL) NDN(MODEL) LD(ANNDATE) 'is later than the' LD(AVAIL) NDN(AVAIL)
						//LATER_DATE_ERR = {0} {1} {2} is later than the {3} {4}
						args[0] = "";//dupes rootEntity.getEntityGroup().getLongDescription();
						args[1] = "";//dupes getNavigationName(rootEntity);
						args[2] = PokUtils.getAttributeDescription(rootEntity.getEntityGroup(), "ANNDATE", "ANNDATE");
						args[3] = avail.getEntityGroup().getLongDescription();
						args[4] = getNavigationName(avail);
						addError("LATER_DATE_ERR",args);
					}
				}
			}
			plannedavailVector.clear();
		}
    }

    /********************************************************************************
    *
    *
    * @param r EntityItem
    * @param destType String
    * @returns EANEntity
    */
    private EANEntity getUpLinkEntityItem(EntityItem r, String destType)
    {
        EANEntity entity = null;
        for(int i = 0; i < r.getUpLinkCount(); i++) {
            EANEntity eai = r.getUpLink(i);
            if(eai.getEntityType().equals(destType)) {
                entity = eai;
                break;
            }
        }

        return entity;
    }
}
