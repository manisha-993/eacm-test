// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import java.util.*;
import com.ibm.transform.oim.eacm.util.*;
import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;

/**********************************************************************************
* PRODSTRUCTABRSTATUS
*
* From "SG FS ABR Data Quality 20080422.doc"
*
* The Product Structure (PRODSTRUCT) is part of a complex data structure and hence a
* Virtual Entity (VE) may be used to describe the applicable structure for the Data
* Quality checks and another VE for the SAPL feed.
*
* There may be a SAPL XML Feed to OIDH (LEDGER) of this data which is determined
* after the data quality checks (if any) are complete.
*
* The ABR produces a Report as described in section XXI. ABR Reports.
*
* The ABR sets its unique attribute (PRODSTRUCTABRSTATUS)
PRODSTRUCTABRSTATUS_class=COM.ibm.eannounce.abr.sg.PRODSTRUCTABRSTATUS
PRODSTRUCTABRSTATUS_enabled=true
PRODSTRUCTABRSTATUS_idler_class=A
PRODSTRUCTABRSTATUS_keepfile=true
PRODSTRUCTABRSTATUS_report_type=DGTYPE01
PRODSTRUCTABRSTATUS_vename=DQVEPRODSTRUCT
PRODSTRUCTABRSTATUS_CAT1=RPTCLASS.PRODSTRUCTABRSTATUS
PRODSTRUCTABRSTATUS_CAT2=
PRODSTRUCTABRSTATUS_CAT3=RPTSTATUS
PRODSTRUCTABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
*/

//PRODSTRUCTABRSTATUS.java,v
//Revision 1.31  2008/08/05 19:32:01  wendy
//Increase size of args array
//
//Revision 1.30  2008/05/29 15:53:50  wendy
//Backout queueing ADSABRSTATUS
//
//Revision 1.29  2008/05/27 14:28:59  wendy
//Clean up RSA warnings
//
//Revision 1.28  2008/05/05 21:55:36  wendy
//Use search to improve performance for checkAllFeatures()
//
//Revision 1.27  2008/05/01 12:02:46  wendy
//updates for SG FS ABR Data Quality 20080430.doc
//
//Revision 1.26  2008/04/24 21:13:47  wendy
//"SG FS ABR Data Quality 20080422.doc" spec updates
//
//Revision 1.25  2008/02/13 19:58:49  wendy
//Make ABRWAITODSx into constants, allow easier change in future
//
//Revision 1.24  2008/01/21 17:26:55  wendy
//Default null status to final
//
//Revision 1.23  2007/12/19 17:05:14  wendy
//VE is always needed for navigation name
//
//Revision 1.22  2007/12/12 17:00:25  wendy
//spec chg "Changed the value used to queue WWPRT since it uses VIEWs in the "legacy" ODS (i.e. not the "approved data ods")"
//
//Revision 1.21  2007/11/27 22:20:04  wendy
//SG FS ABR Data Quality 20071127.doc chgs
//
//Revision 1.20  2007/11/16 22:17:21  nancy
//Chgs for spec "SG FS ABR Data Quality 20071115.doc"
//
//Revision 1.19  2007/10/25 20:38:28  wendy
//Spec chgs
//
//Revision 1.18  2007/10/23 17:47:12  wendy
//Spec changes
//
//Revision 1.17  2007/09/14 17:43:55  wendy
//Updated for GX
//
//Revision 1.16  2007/08/17 16:02:10  wendy
//RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
//from 'SG FS xSeries ABRs 20070803.doc'
//
//Revision 1.15  2007/06/20 12:06:31  wendy
//Added mising 'GeographyList' level TIR74BL5E
//
//Revision 1.14  2007/06/18 18:31:45  wendy
//TIR 747QEJ- XML Mapping SS updated
//
//Revision 1.13  2007/06/13 21:18:22  wendy
//TIR73RS77 -XML Mapping updated
//
//Revision 1.12  2007/05/16 17:19:15  wendy
//TIR 7STP5L - Routing of XML correction required, spec chg
//
//Revision 1.11  2007/05/01 21:00:55  wendy
//TIR72PGQ9 VE won't pull MODELPROJA from (SW)PRODSTRUCT root, use 2 VEs
//
//Revision 1.10  2007/04/20 21:48:59  wendy
//RQ0417075638 updates
//
//Revision 1.9  2007/04/02 17:38:50  wendy
//Modified for SAPL
//
//Revision 1.8  2007/02/22 21:11:50  joan
//fixes
//
//Revision 1.7  2006/10/27 16:44:32  joan
//changes
//
//Revision 1.6  2006/09/06 23:56:58  joan
//add notification in report
//
//Revision 1.5  2006/08/30 16:17:46  joan
//changes
//
//Revision 1.4  2006/06/27 18:12:34  joan
//changes
//
//Revision 1.3  2006/06/26 20:00:43  joan
//changes
//
//Revision 1.2  2006/06/26 16:41:51  joan
//add debug
//
//Revision 1.1  2006/06/24 17:25:08  joan
//add new abr
//
//

public class PRODSTRUCTABRSTATUS extends DQABRSTATUS
{
	private static final String NOT_READY="EPNOT";
	private Object args[] = new Object[8];

	/**********************************
	* always check if not final, but need navigation name from model and fc
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**********************************
	* complete abr processing when status is already final; (dq was final too)
	* D.	Status = Final
	*
	* Data Quality checking is not required; however, the data may need to be sent via XML to SAPL.
	* This occurs when data is sent to SAPL but there is a downstream failure which requires the XML
	* to be sent again. The UI allows the setting of SAPL to Send which will queue this ABR.
	*
	* 1.	Set SAPLABRSTATUS = 0020 (Queued)
	*/
	protected void doAlreadyFinalProcessing() {
        setFlagValue(m_elist.getProfile(),"SAPLABRSTATUS", SAPLABR_QUEUED);
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
    * complete abr processing after status moved to final; (status was chgreq)
	* C.	STATUS changed to Final
	* 1.IF PRODSTRUCT.EPIMSSTATUS = EPNOT (Not Ready) THEN Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	* 2.IF ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED) | 402 (Placeholder) THEN
	*	 	a.	DeriveEXTRACTRPQ
	*		b.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*	ELSE
	*		a.	IF (PRODSTRUCT: OOFAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
	*	(PRODSTRUCT: OOFAVAIL-d: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
	*	Set (PRODSTRUCT: OOFAVAIL-D: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) =ABRWAITODS2 (Wait for ODS Data)
	3.	Set ADSABRSTATUS = 0020 (Queued)
	*/
    protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
        setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2);
//        setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);

		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		// check value of EPIMSSTATUS attribute
		String epimsFlag = getAttributeFlagEnabledValue(rootEntity, "EPIMSSTATUS");
		if (epimsFlag == null || epimsFlag.length()==0){
			epimsFlag = NOT_READY;
		}
        addDebug("completeNowFinalProcessing:: EPIMSSTATUS "+epimsFlag);
		if (epimsFlag.equals(NOT_READY)){
        	setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS);
		}

		EntityGroup eg = m_elist.getEntityGroup("FEATURE");
		EntityItem eiFEATURE = eg.getEntityItem(0);
		String strFCTYPE = getAttributeFlagEnabledValue(eiFEATURE, "FCTYPE");
		addDebug(eiFEATURE.getKey()+" FCTYPE "+strFCTYPE);
		if (("120".equals(strFCTYPE) || "130".equals(strFCTYPE) || "402".equals(strFCTYPE))) {
			addDebug(eiFEATURE.getKey()+" is RPQ or placeholder");
			//a.	DeriveEXTRACTRPQ
			deriveEXTRACTRPQ(eiFEATURE); // support 30b
			//b.	Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
        	setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", ABRWAITODS2);
		}else{
			//	a.	IF (PRODSTRUCT: OOFAVAIL-d: AVAILANNA: ANNOUNCEMENT.STATUS = 0020 (Final) and
			//	(PRODSTRUCT: OOFAVAIL-d: AVAILANNA: ANNOUNCEMENT.ANNTYPE) = 19 (New) then
			//	Set (PRODSTRUCT: OOFAVAIL-D: AVAILANNA: ANNOUNCEMENT.WWPRTABRSTATUS) = ABRWAITODS2 (Wait for ODS Data)
			eg = m_elist.getEntityGroup("ANNOUNCEMENT");
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
    }

	/**********************************
	* A.	STATUS = Draft | Change Request
	*
	*1.	PRODSTRUCT-d: MODEL then PRODSTRUCT-u: FEATURE UNIQUE (FEATURECODE)
	*ErrorMessage LD(FEATURE) NDN(FEATURE) 'is not unique in this' LD(MODEL)
	*2.	PRODSTRUCT-d: MODEL then
	*MODELMACHINETYPEA: MACHTYPE then MACHTYPE: MACHINETYPEMODELA: MODEL  then MODEL: PRODSTRUCT-u: FEATURE SameEntity (FEATURECODE)
	*ErrorMessage 'is not identical within this' LD(MACHTYPE)
	*
	*B.	STATUS = Ready for Review
	*1.	All Checks from the preceding section:  Draft | Change Request
	*2.	CompareAll(PRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
	*ErrorMessage LD(MODEL) 'Status is not Final'
	*3.	CompareAll(PRODSTRUCT-u: FEATURE.STATUS) = 0020 (Final)
	*ErrorMessage LD(FEATURE) 'Status is not Final'
	*4.	IF ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED)| 402 (Placeholder) THEN ELSE
	*	IF CompareAll(PRODSTRUCT.SAPL) = ('N/A' (90) | 'Not Ready' (10)) THEN
	*		a.	CountOf( OOFAVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
	*		ErrorMessage 'does not have a Planned Availability'
	*		b.	MAX(PRODSTRUCT.ANNDATE; MODEL.ANNDATE; FEATURE.FIRSTANNDATE) <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	*		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
	*		c.	MIN(PRODSTRUCT.WITHDRAWDATE; MODEL.WITHDRAWDATE; FEATURE.WITHDRAWDATEEFF_T) => CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 149 (Last Order)
	*		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) has a Last Order Availability later than the Model or Feature'.
	*		d.	IF NotNull(PRODSTRUCT.WITHDRAWDATEEFF_T) OR NotNull(MODEL.WITHDRAWDATE) OR NotNull(PRODSTRUCT.WITHDRAWDATE) THEN AllValuesOf(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	*		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
	*		e.	AllValuesOf(PRODSTRUCT: OFFAVAIL-D: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
	*		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'. Note: the AVAIL is the one with the AVAILTYPE = 149.
	*
			f.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)) IN (PRODSTRUCT-u:  FEATURE.COUNTRYLIST
			ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) 'is available in a country that is not in the' LD(FEATURE) LD(COUNTRYLIST).
			g.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) or 143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
			ErrorMessage  LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).

C.	STATUS changed to Ready for Review

1.	Set ADSABRSTATUS = 0020 (Queued)

	*D.	STATUS changed to Final
	*
	*1. Set WWPRTABRSTATUS = ABRWAITODS2 (Wait for ODS Data)
	*2.	IF PRODSTRUCT.EPIMSSTATUS = EPNOT (Not Ready) THEN Set EPIMSABRSTATUS =  ABRWAITODS (Wait for ODS Data)
	3.	Set ADSABRSTATUS = 0020 (Queued)

	E.	STATUS = Final
	*
	*1.	Set SAPLABRSTATUS = 0020 (Queued)
	*/
	protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
	{
		checkAllFeatures(); // always do this check

		if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
		{
			// just look at MODEL and FEATURE for this PRODSTRUCT from first VE
			//2.	CompareAll(PRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
			//ErrorMessage LD(MODEL) ' is not Final'
			checkStatus("MODEL");

			//3.	CompareAll(PRODSTRUCT-d: FEATURE.STATUS) = 0020 (Final)
			//ErrorMessage LD(FEATURE) ' is not Final'
			checkStatus("FEATURE");

			//4.	IF ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED)| 402 (Placeholder) THEN ELSE
			//	IF CompareAll(PRODSTRUCT.SAPL) = ('N/A' (90) | 'Not Ready' (10)) THEN
			//		a.	CountOf( OOFAVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
			//		ErrorMessage 'does not have a Planned Availability'
			//		b.	MAX(PRODSTRUCT.ANNDATE; MODEL.ANNDATE; FEATURE.FIRSTANNDATE) <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
			//		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
			//		c.	MIN(PRODSTRUCT.WITHDRAWDATE; MODEL.WITHDRAWDATE; FEATURE.WITHDRAWDATEEFF_T) => CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 149 (Last Order)
			//		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) has a Last Order Availability later than the Model or Feature'.
			//		d.	IF NotNull(PRODSTRUCT.WITHDRAWDATEEFF_T) OR NotNull(MODEL.WITHDRAWDATE) OR NotNull(PRODSTRUCT.WITHDRAWDATE) THEN AllValuesOf(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
			//		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
			//		e.	AllValuesOf(PRODSTRUCT: OFFAVAIL-D: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
			//		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'. Note: the AVAIL is the one with the AVAILTYPE = 149.
			//		f.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability))
			//		IN (PRODSTRUCT-u:  FEATURE.COUNTRYLIST
			//		ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) 'is available in a country that is not in the' LD(FEATURE) LD(COUNTRYLIST).
			//		g.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) or
			//		143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
			//		ErrorMessage  LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).
			checkRPQAvails(rootEntity);
		}
	}

	/********************************
	* this checks the FCTYPE of the FEATURE, if it is not an RPQ
	* it checks the SAPL value of the root PRODSTRUCT
	* if it is NA or not ready, AVAIL checks are needed
	*/
    private void checkRPQAvails(EntityItem rootEntity) throws java.sql.SQLException, MiddlewareException
	{
		// check FCTYPE
		Set fctypeSet = new HashSet();
		//FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED)| 402 (Placeholder)
		fctypeSet.add("120");
		fctypeSet.add("130");
		fctypeSet.add("402");

		EntityItem featItem = m_elist.getEntityGroup("FEATURE").getEntityItem(0);
		addDebug("checkRPQAvails checking "+featItem.getKey());
		//4.IF ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED)| 402 (Placeholder) THEN ELSE
		// check FCTYPE
		if (!PokUtils.contains(featItem, "FCTYPE", fctypeSet)){
			addDebug("checkRPQAvails "+featItem.getKey()+" is NOT an RPQ");
			// is not an RPQ
			// look at avails
			EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

			// get SAPL value
			String sapl = getAttributeFlagEnabledValue(rootEntity, "SAPL"); // prodstruct
			addDebug("checkRPQAvails checking entity: "+rootEntity.getKey()+" sapl "+sapl);
			Set saplSet = new HashSet();
			saplSet.add(SAPL_NOTREADY);
			saplSet.add(SAPL_NA);
			//IF CompareAll(PRODSTRUCT.SAPL) = ('N/A' (90) | 'Not Ready' (10)) THEN
			if (PokUtils.contains(rootEntity, "SAPL", saplSet)){
				addDebug("checkRPQAvails "+rootEntity.getKey()+" has SAPL of 90 or 10");

				//a.	CountOf( OOFAVAIL-d: AVAIL)  => 1 WHERE ValueOf(AVAILTYPE = 146 (Planned Availability)
				//ErrorMessage 'does not have a Planned Availability'
				Vector plannedAvailVct = new Vector(1);
				for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
					EntityItem avail = (EntityItem)availGrp.getEntityItem(ai);
					addDebug("checkRPQAvails looking for planned avail; checking: "+avail.getKey());
					if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
						plannedAvailVct.add(avail);
					}
				}
				if (plannedAvailVct.size()==0){
					//ErrorMessage 'does not have a Planned Availability'
					//NO_PLANNEDAVAIL_ERR = does not have a Planned Availability
					addError("NO_PLANNEDAVAIL_ERR",null);
				}

				//b.MAX(PRODSTRUCT.ANNDATE; MODEL.ANNDATE; FEATURE.FIRSTANNDATE) <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
				//ErrorMessage 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
				checkMaxAnnDate(rootEntity, availGrp);

				//c.MIN(PRODSTRUCT.WITHDRAWDATE; MODEL.WITHDRAWDATE; FEATURE.WITHDRAWDATEEFF_T) => CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 149 (Last Order)
				//ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) has a Last Order Availability later than the Model or Feature'.
				checkMinWDate(rootEntity, availGrp);

				//d.IF NotNull(PRODSTRUCT.WITHDRAWDATEEFF_T) OR NotNull(MODEL.WITHDRAWDATE) OR NotNull(PRODSTRUCT.WITHDRAWDATE) THEN
				//AllValuesOf(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN
				//	(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
				//ErrorMessage 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
				checkLastOrderWDCountries(rootEntity,availGrp);

				//e.AllValuesOf(PRODSTRUCT: OFFAVAIL-D: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN
				//	(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
				//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'. Note: the AVAIL is the one with the AVAILTYPE = 149.
				checkPlannedAvailWDCountries(availGrp);

				//f.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability))
				//IN (PRODSTRUCT-u:  FEATURE.COUNTRYLIST
				//ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) 'is available in a country that is not in the' LD(FEATURE) LD(COUNTRYLIST).
				if (plannedAvailVct.size()>0){
					checkCountry(featItem, plannedAvailVct);
				}

				//g.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) or
				//143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
				//ErrorMessage  LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).
				checkAvailDates(availGrp);

				plannedAvailVct.clear();
			}// end SAPL is NA or NOTREADY
			else{
				addDebug("checkRPQAvails "+rootEntity.getKey()+" is not SAPL of 90 or 10");
			}
		}else{
			addDebug("checkRPQAvails "+featItem.getKey()+" is an RPQ");
		}

		fctypeSet.clear();
	}

    /**********************************
	f.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability))
	IN (PRODSTRUCT-u:  FEATURE.COUNTRYLIST
	ErrorMessage LD(AVAIL) NDN(AVAIL) 'is available in a country that is not in the' LD(FEATURE) LD(COUNTRYLIST).
    */
	private void checkCountry(EntityItem featItem, Vector plannedAvailVct)
		throws java.sql.SQLException, MiddlewareException
	{
		// get features countrylist
		HashSet featSet = new HashSet();
		EANFlagAttribute fAtt = (EANFlagAttribute)featItem.getAttribute("COUNTRYLIST");
		addDebug("checkCountry "+featItem.getKey()+" COUNTRYLIST "+
			PokUtils.getAttributeFlagValue(featItem, "COUNTRYLIST")+NEWLINE);
		if (fAtt!=null && fAtt.toString().length()>0){
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
			for (int i = 0; i < mfArray.length; i++){
				// get selection
				if (mfArray[i].isSelected()){
					featSet.add(mfArray[i].getFlagCode());
				}  // metaflag is selected
			}// end of flagcodes
		}

		for (int ai=0; ai<plannedAvailVct.size(); ai++){ // look at mdlavails
			EntityItem avail = (EntityItem)plannedAvailVct.elementAt(ai);
			fAtt = (EANFlagAttribute)avail.getAttribute("COUNTRYLIST");
			addDebug("checkCountry "+avail.getKey()+" COUNTRYLIST "+
				PokUtils.getAttributeFlagValue(avail, "COUNTRYLIST")+NEWLINE);
			if (fAtt!=null && fAtt.toString().length()>0){
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
				for (int i = 0; i < mfArray.length; i++){
					// get selection
					if (mfArray[i].isSelected()){
						addDebug("checkCountry "+avail.getKey()+" COUNTRYLIST "+mfArray[i].getFlagCode());
						if (!featSet.contains(mfArray[i].getFlagCode())){
							//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is available in a country that is not in the' LD(FEATURE) LD(COUNTRYLIST).
							//NO_CTRY_MATCH = {0} {1} is available in a country that is not in the {2} {3}.
							args[0] = avail.getEntityGroup().getLongDescription();
							args[1] = getNavigationName(avail);
							args[2] = featItem.getEntityGroup().getLongDescription();
							args[3] = PokUtils.getAttributeDescription(featItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
							addError("NO_CTRY_MATCH",args);
							break;
						}
					}  // metaflag is selected
				}// end of flagcodes
			}
		}

		featSet.clear();
	}

    /***********************************************
	g.	AllValuesOf(PRODSTRUCT: OFFAVAIL-d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) or
	143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
	ErrorMessage  LD(PRODSTRUCT) NDN(PRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).
	*/
	private void checkAvailDates(EntityGroup availGrp) throws java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException
	{

		for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
			EntityItem avail = availGrp.getEntityItem(ai);
			String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
			String availtype = getAttributeFlagEnabledValue(avail, "AVAILTYPE");
			addDebug("checkAvailDates "+avail.getKey()+" EFFECTIVEDATE: "+effDate+" AVAILTYPE: "+availtype);
			if((PLANNEDAVAIL.equals(availtype) || FIRSTORDERAVAIL.equals(availtype))
				&& effDate.length()>0){
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

    /**********************************
    *b.MAX(PRODSTRUCT.ANNDATE; MODEL.ANNDATE; FEATURE.FIRSTANNDATE) <= CompareAll(OOFAVAIL.AVAIL.EFFECTIVEDATE)
	*	WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	* ErrorMessage 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
    */
	private void checkMaxAnnDate(EntityItem rootEntity,EntityGroup availGrp)
		throws java.sql.SQLException, MiddlewareException
	{
		Vector tmp = new Vector(3);
    	String psDate = PokUtils.getAttributeValue(rootEntity, "ANNDATE",", ", null, false);
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
    	String mdlDate = PokUtils.getAttributeValue(mdlItem, "ANNDATE",", ", null, false);
		EntityItem featItem = m_elist.getEntityGroup("FEATURE").getEntityItem(0);
    	String fcDate = PokUtils.getAttributeValue(featItem, "FIRSTANNDATE",", ", null, false);
		addDebug("checkMaxAnnDate "+featItem.getKey()+" ("+fcDate+") "+rootEntity.getKey()+" ("+psDate+") "+
			mdlItem.getKey()+" ("+mdlDate+")");

		if (psDate != null){
	    	tmp.add(psDate);
		}
		if (mdlDate != null){
    		tmp.add(mdlDate);
		}
		if (fcDate != null){
	    	tmp.add(fcDate);
		}

		if (tmp.size()>0){
			Collections.sort(tmp);
			String annDate = tmp.lastElement().toString();
			tmp.clear();
			addDebug("checkMaxAnnDate looking for planned avail; before "+annDate);

			for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
				EntityItem avail = (EntityItem)availGrp.getEntityItem(ai);
				addDebug("checkMaxAnnDate looking for planned avail; checking: "+avail.getKey());
				if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
					String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
					addDebug("checkMaxAnnDate plannedavail "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
					if (effDate.length()>0 && annDate.compareTo(effDate)>0){
						//EARLY_PLANNEDAVAIL_ERR = has a Planned Availability earlier than the {0} {1}
						args[0] = mdlItem.getEntityGroup().getLongDescription();
						args[1] = PokUtils.getAttributeDescription(mdlItem.getEntityGroup(), "ANNDATE", "ANNDATE");
						addError("EARLY_PLANNEDAVAIL_ERR",args);
					}
				}
			}
		}
	}

    /**********************************
    *c.	MIN(PRODSTRUCT.WITHDRAWDATE; MODEL.WITHDRAWDATE; FEATURE.WITHDRAWDATEEFF_T) => CompareAll(OOFAVAIL:AVAIL.EFFECTIVEDATE)
    *	WHERE AVAIL.AVAILTYPE = 149 (Last Order)
	* ErrorMessage has a Last Order Availability later than the Model or Feature.
    */
	private void checkMinWDate(EntityItem rootEntity,EntityGroup availGrp)
		throws java.sql.SQLException, MiddlewareException
	{
		Vector tmp = new Vector(3);
    	String psDate = PokUtils.getAttributeValue(rootEntity, "WITHDRAWDATE",", ", null, false);
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
    	String mdlDate = PokUtils.getAttributeValue(mdlItem, "WITHDRAWDATE",", ", null, false);
		EntityItem featItem = m_elist.getEntityGroup("FEATURE").getEntityItem(0);
    	String fcDate = PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T",", ", null, false);
		addDebug("checkMinWDate "+featItem.getKey()+" ("+fcDate+") "+rootEntity.getKey()+" ("+psDate+") "+
			mdlItem.getKey()+" ("+mdlDate+")");

		if (psDate != null){
	    	tmp.add(psDate);
		}
		if (mdlDate != null){
    		tmp.add(mdlDate);
		}
		if (fcDate != null){
    		tmp.add(fcDate);
		}

		if (tmp.size()>0){
			Collections.sort(tmp);
			String wdDate = tmp.firstElement().toString();
			tmp.clear();
			addDebug("checkMinWDate looking for lastorder avail; after "+wdDate);

			for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
				EntityItem avail = (EntityItem)availGrp.getEntityItem(ai);
				addDebug("checkMinWDate looking for lastorder avail; checking: "+avail.getKey());
				if(PokUtils.isSelected(avail, "AVAILTYPE", LASTORDERAVAIL)){
					String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
					addDebug("checkMinWDate lastorder "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
					if (effDate.length()>0 && effDate.compareTo(wdDate)>0){
						//LATE_LASTORDERAVAIL_ERR =has a Last Order Availability later than the {0} or {1}.
						args[0] = mdlItem.getEntityGroup().getLongDescription();
						args[1] = featItem.getEntityGroup().getLongDescription();
						addError("LATE_LASTORDERAVAIL_ERR",args);
					}
				}
			}
		}
	}

    /**********************************
	*d.	IF NotNull(FEATURE.WITHDRAWDATEEFF_T) OR NotNull(MODEL.WITHDRAWDATE) OR NotNull(PRODSTRUCT.WITHDRAWDATE) THEN
	* AllValuesOf(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN
	*	(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	* ErrorMessage 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
    */
	private void checkLastOrderWDCountries(EntityItem rootEntity,EntityGroup availGrp)
		throws java.sql.SQLException, MiddlewareException
	{
    	String psDate = PokUtils.getAttributeValue(rootEntity, "WITHDRAWDATE",", ", null, false);
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
    	String mdlDate = PokUtils.getAttributeValue(mdlItem, "WITHDRAWDATE",", ", null, false);
		EntityItem featItem = m_elist.getEntityGroup("FEATURE").getEntityItem(0);
    	String fcDate = PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T",", ", null, false);
		addDebug("checkLastOrderWDCountries "+featItem.getKey()+" ("+fcDate+") "+rootEntity.getKey()+" ("+psDate+") "+
			mdlItem.getKey()+" ("+mdlDate+")");

		if (psDate != null || mdlDate != null || fcDate != null){
			ArrayList lastOrderAvailCtry = new ArrayList();
			ArrayList plannedAvailCtry = new ArrayList();

			for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
				EntityItem avail = availGrp.getEntityItem(ai);
				addDebug("checkLastOrderWDCountries checking avail: "+avail.getKey());
				if(PokUtils.isSelected(avail, "AVAILTYPE", LASTORDERAVAIL)){ // last order
					addDebug("checkLastOrderWDCountries lastorder "+avail.getKey());
					EANFlagAttribute ctrylist = (EANFlagAttribute)avail.getAttribute("COUNTRYLIST");
					if (ctrylist != null) {
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
						for (int im = 0; im < mfArray.length; im++) {
							// get selection
							if (mfArray[im].isSelected() && !lastOrderAvailCtry.contains(mfArray[im].getFlagCode())) {
								lastOrderAvailCtry.add(mfArray[im].getFlagCode());
							}
						} //end for
					} //end of null chk

				}else if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
					addDebug("checkLastOrderWDCountries plannedavail "+avail.getKey());
					EANFlagAttribute ctrylist = (EANFlagAttribute)avail.getAttribute("COUNTRYLIST");
					if (ctrylist != null) {
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
						for (int im = 0; im < mfArray.length; im++) {
							// get selection
							if (mfArray[im].isSelected()&& !plannedAvailCtry.contains(mfArray[im].getFlagCode())) {
								plannedAvailCtry.add(mfArray[im].getFlagCode());
							}
						} //end for
					} //end of null chk
				}
			} // end avails

			addDebug("checkLastOrderWDCountries all plannedavail countries "+plannedAvailCtry);
			addDebug("checkLastOrderWDCountries all lastorderavail countries "+lastOrderAvailCtry);
			// each lastorder avail country must have a plannedavail too
			if (!lastOrderAvailCtry.containsAll(plannedAvailCtry)) {
				// ErrorMessage 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
				//NO_LASTORDER_ERR= is being withdrawn and is available in a Country that does not have a Last Order {0}
				args[0] = availGrp.getLongDescription();
				addError("NO_LASTORDER_ERR",args);
			}
			lastOrderAvailCtry.clear();
			plannedAvailCtry.clear();
		}
	}

    /**********************************
	*e.	AllValuesOf(PRODSTRUCT: OFFAVAIL-D: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN
	*	(PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
	*ErrorMessage LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'.
	*Note: the AVAIL is the one with the AVAILTYPE = 149.
    */
	private void checkPlannedAvailWDCountries(EntityGroup availGrp)
		throws java.sql.SQLException, MiddlewareException
	{
		ArrayList plannedAvailCtry = new ArrayList();

		// get all planned avail countries
		for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
			EntityItem avail = availGrp.getEntityItem(ai);
			addDebug("checkPlannedAvailWDCountries checking avail: "+avail.getKey());
			if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
				addDebug("checkPlannedAvailWDCountries plannedavail "+avail.getKey());
				EANFlagAttribute ctrylist = (EANFlagAttribute)avail.getAttribute("COUNTRYLIST");
				if (ctrylist != null) {
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected()&& !plannedAvailCtry.contains(mfArray[im].getFlagCode())) {
							plannedAvailCtry.add(mfArray[im].getFlagCode());
						}
					} //end for
				} //end of null chk
			}
		} // end avails

		addDebug("checkPlannedAvailWDCountries all plannedavail countries "+plannedAvailCtry);

		for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
			EntityItem avail = availGrp.getEntityItem(ai);
			addDebug("checkPlannedAvailWDCountries checking avail: "+avail.getKey());
			if(PokUtils.isSelected(avail, "AVAILTYPE", LASTORDERAVAIL)){ // last order
				ArrayList lastOrderAvailCtry = new ArrayList();
				EANFlagAttribute ctrylist = (EANFlagAttribute)avail.getAttribute("COUNTRYLIST");
				if (ctrylist != null) {
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
					for (int im = 0; im < mfArray.length; im++) {
						// get selection
						if (mfArray[im].isSelected() && !lastOrderAvailCtry.contains(mfArray[im].getFlagCode())) {
							lastOrderAvailCtry.add(mfArray[im].getFlagCode());
						}
					} //end for
				} //end of null chk
				addDebug("checkPlannedAvailWDCountries all lastorder "+avail.getKey()+" countries "+lastOrderAvailCtry);
				// each plannedavail avail country must have a lastorder too
				if (!plannedAvailCtry.containsAll(lastOrderAvailCtry)) {
					// ErrorMessage LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'.
					//NO_PLANNEDAVAIL_ERR2= {0} {1} is being withdrawn from a country that does not have a planned availability.
					args[0] = availGrp.getLongDescription();
					args[1] = getNavigationName(avail);
					addError("NO_PLANNEDAVAIL_ERR2",args);
				}
				lastOrderAvailCtry.clear();

			}
		} // end avails

		plannedAvailCtry.clear();
	}

    /**********************************************************************************
	Ref:  XXIII. PRODSTRUCT  A. STATUS = Draft | Change Request ;  checks 1 and 2

	Given the PRODSTRUCT that the ABR is running for.

	Search for
	Machine Type using PRODSTRUCT-d: MODEL.MACHTYPEATR
	Feature Code using PRODSTRUCT-u: FEATURE.FEATURECODE

	Find duplicate PRODSTRUCT-u: FEATURE.FEATURECODE

	Given a set of duplicates, then
	If MODEL is duplicate, then ErrorMessage LD(FEATURE) NDN(FEATURE) 'is not unique in this' LD(MODEL)
	else, if PRODSTRUCT-u: FEATURE SameEntity (FEATURECODE)
	ErrorMessage "is not identical within this" LD(MACHTYPE)
	also - list the Feature Code that is not identical and if available, the PRODSTRUCT ndn (there can be several of these)

	Search Action that you can use:
	SRDPRODSTRUCTV

    *@param item EntityItem
    */
    protected void checkAllFeatures()
    throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    COM.ibm.eannounce.objects.SBRException
    {
		PDGUtility pdgUtility = new PDGUtility();
        EntityItem origmdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0); // has to exist
        EntityItem origfcItem = m_elist.getEntityGroup("FEATURE").getEntityItem(0); // has to exist

        StringBuffer sb = new StringBuffer();
        sb.append("map_FEATURE:FEATURECODE=" + PokUtils.getAttributeValue(origfcItem, "FEATURECODE",", ", "", false)+";");
        sb.append("map_MODEL:MACHTYPEATR=" +getAttributeFlagEnabledValue(origmdlItem, "MACHTYPEATR"));

        addDebug("checkAllFeatures using SRDPRODSTRUCTV searching for "+sb.toString());

		//must cross domains
		EntityList list = pdgUtility.dynaSearchIIForEntityList(m_db, m_prof, null, "SRDPRODSTRUCTV",
			"PRODSTRUCT", sb.toString());

		addDebug("checkAllFeatures search results "+PokUtils.outputList(list));

		EntityGroup grp = list.getEntityGroup("FEATURE");
		if (grp.getEntityItemCount()>1){
			for (int i=0; i<grp.getEntityItemCount(); i++){
				EntityItem featItem = grp.getEntityItem(i);
				//if PRODSTRUCT-u: FEATURE SameEntity (FEATURECODE)
				//	ErrorMessage "is not identical within this" LD(MACHTYPE)
				//NOT_IDENTICAL_ERR = {0} &quot;{1}&quot; is not identical within this {2}
				args[0] = grp.getLongDescription();
				args[1] = getNavigationName(featItem);
				args[2] = PokUtils.getAttributeDescription(m_elist.getEntityGroup("MODEL"), "MACHTYPEATR", "MACHTYPEATR");
				addError("NOT_IDENTICAL_ERR",args);
			}
		}else{
			// check for dupe prodstruct relators between the same model and feature
			EntityItem featItem = grp.getEntityItem(0);
			addDebug("checkAllFeatures featItem "+featItem.getKey());

			Hashtable dupeTbl = new Hashtable();
			for (int i=0; i<featItem.getDownLinkCount(); i++){
				EntityItem psitem = (EntityItem)featItem.getDownLink(i);
				for (int d=0; d<psitem.getDownLinkCount(); d++){
					EntityItem mdlitem = (EntityItem)psitem.getDownLink(d);
					addDebug("checkAllFeatures adding psitem "+psitem.getKey()+" to vct for mdlitem "+mdlitem.getKey());
					Vector psVct = (Vector)dupeTbl.get(mdlitem.getKey());
					if (psVct==null){
						psVct = new Vector(1);
						dupeTbl.put(mdlitem.getKey(),psVct);
					}
					psVct.add(psitem);
				}
			}
			for (Enumeration e = dupeTbl.keys(); e.hasMoreElements();) {
				String mdlkey = (String)e.nextElement();
				Vector psVct = (Vector)dupeTbl.get(mdlkey);
				EntityItem mdlItem = list.getEntityGroup("MODEL").getEntityItem(mdlkey);
				if (psVct.size()>1){
					for (int p=0; p<psVct.size(); p++){
						EntityItem psitem = (EntityItem)psVct.elementAt(p);
						addDebug("checkAllFeatures mdlItem "+mdlItem.getKey()+" duplicate psitem "+psitem.getKey());
						//1.PRODSTRUCT-d: MODEL then PRODSTRUCT-u: FEATURE UNIQUE (FEATURECODE)
						//ErrorMessage LD(PRODSTRUCT) NDN(PRODSTRUCT) 'is not unique in this' LD(MODEL)
						//NOT_UNIQUE_ERR = {0} {1} is not unique in this {2}

						args[0] = psitem.getEntityGroup().getLongDescription();
						args[1] = getTMFNavigationName(psitem, mdlItem, featItem);
						args[2] = mdlItem.getEntityGroup().getLongDescription();
						addError("NOT_UNIQUE_ERR",args);
					}
					psVct.clear();
				}

			}
			dupeTbl.clear();
		}

		list.dereference();
	}

    /**********************************
    *
Wendy   i think u can pull things tied to a feature when prodstruct is root..
bnair@us.ibm.c...   right, u can
Wendy   but seems like u cant pull things tied to a model when prodstruct is root
bnair@us.ibm.c...   right, because the entry which seeds the trsnavigate always says root is 'UP' when the root entity is a relator
bnair@us.ibm.c...   so thats a limitation with gbl8000
    * /
	private void checkAllFeaturesOrig() throws Exception
	{
        Profile profile = m_elist.getProfile();
        String VeName = "DQVEPRODSTRUCT2";

        EntityItem origmdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0); // has to exist

        // entityitem passed in is adjusted in extract, we don't want that so create new one
        EntityList mdlList = m_db.getEntityList(profile,
                new ExtractActionItem(null, m_db, profile, VeName),
                new EntityItem[] { new EntityItem(null, profile, "MODEL", origmdlItem.getEntityID()) });
        addDebug("checkAllFeatures:: Extract "+VeName+NEWLINE+PokUtils.outputList(mdlList));

        EntityItem mdlItem = mdlList.getParentEntityGroup().getEntityItem(0);

		Vector fcVct = new Vector();
		for (int i=0; i<mdlItem.getUpLinkCount(); i++){ // look at prodstructs for this model
			EntityItem psItem = (EntityItem)mdlItem.getUpLink(i);
			if (!psItem.getEntityType().equals("PRODSTRUCT")){
				addDebug("checkAllFeatures skipping uplink "+psItem.getKey());
				continue;
			}
			for (int f=0; f<psItem.getUpLinkCount(); f++){ // look at features for this ps
				EntityItem featItem = (EntityItem)psItem.getUpLink(f);
				// look at all features for this model thru each prodstruct
				String fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false);
				addDebug("checkAllFeatures checking "+psItem.getKey()+" -- "+featItem.getKey()+" fcode: "+fcode);
				if (fcVct.contains(fcode)){
					//1.	PRODSTRUCT-d: MODEL then PRODSTRUCT-u: FEATURE UNIQUE (FEATURECODE)
					//ErrorMessage LD(FEATURE) NDN(FEATURE) 'is not unique in this' LD(MODEL)
					//NOT_UNIQUE_ERR = {0} {1} is not unique in this {2}
					args[0] = featItem.getEntityGroup().getLongDescription();
					args[1] = getNavigationName(featItem);
					args[2] = mdlItem.getEntityGroup().getLongDescription();
					addError("NOT_UNIQUE_ERR",args);
				}else{
					fcVct.add(fcode);
				}
			} // end prodstruct uplinks
		} // end model uplinks

		fcVct.clear();

		/*Find the parent MACHTYPE and get its MODELs and then get its FEATUREs
		(with the same FEATURE CODE) it has to be the same instance of FEATURE.
		* /
		//2.	PRODSTRUCT-d: MODEL then
		//MODELMACHINETYPEA: MACHTYPE then MACHTYPE: MACHINETYPEMODELA: MODEL
		//							  then MODEL: PRODSTRUCT-u: FEATURE SameEntity (FEATURECODE)
		//ErrorMessage 'is not identical within this' LD(MACHTYPE)
		Hashtable fcTbl = new Hashtable();
        EntityGroup mtGrp = mdlList.getEntityGroup("MACHTYPE");
        for (int i=0; i<mtGrp.getEntityItemCount(); i++){
			EntityItem mtItem = mtGrp.getEntityItem(i);
    		Vector mdlVct = PokUtils.getAllLinkedEntities(mtItem, "MACHINETYPEMODELA", "MODEL");
    		Vector featVct = PokUtils.getAllLinkedEntities(mdlVct, "PRODSTRUCT", "FEATURE");
			addDebug("checkAllFeatures checking features for "+mtItem.getKey());
			for (int f=0; f<featVct.size(); f++){ // look at features for this MACHTYPE
				EntityItem featItem = (EntityItem)featVct.elementAt(f);
				// look at all features for this model thru each prodstruct
				String fcode = PokUtils.getAttributeValue(featItem, "FEATURECODE",", ", "", false);
				addDebug("checkAllFeatures checking if same entity "+featItem.getKey()+" fcode: "+fcode);
				if (fcTbl.containsKey(fcode)){
//					String itemkey = (String)fcTbl.get(fcode);
					EntityItem savedFeatItem = (EntityItem)fcTbl.get(fcode);
					String itemkey = savedFeatItem.getKey();
					if (!itemkey.equals(featItem.getKey())){
						//ErrorMessage 'is not identical within this' LD(MACHTYPE)
						//NOT_IDENTICAL_ERR = is not identical within this {0}
						args[0] = mtGrp.getLongDescription();
						addError("NOT_IDENTICAL_ERR",args);
						//output duplicate fcodes
						//DUPLICATE_ERR = Duplicate Feature Code &quot;{0}&quot; found in: {1}
						args[0] = fcode;
						args[1] = getTMFInfo(savedFeatItem, featItem);
						addError("DUPLICATE_ERR",args);
					}
				}else{
//					fcTbl.put(fcode,featItem.getKey());
					fcTbl.put(fcode,featItem);
				}
			} // end prodstruct uplinks
		}

		fcTbl.clear();
		mdlList.dereference();
	}

	//DUPLICATE_ERR = Duplicate Feature Code &quot;{0}&quot; found in: {1}
	private String getTMFInfo(EntityItem savedFeatItem, EntityItem featItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer sb = new StringBuffer();
		// get all tmf for first feature
		Vector dnlinkVct = savedFeatItem.getDownLink();
		for (int i=0; i<dnlinkVct.size(); i++){
			EntityItem dnlink = (EntityItem)dnlinkVct.elementAt(i);
			if (dnlink.getEntityType().equals("PRODSTRUCT")){
				//find the model
				for (int d=0; d<dnlink.getDownLinkCount(); d++){
					EntityItem mdlItem = (EntityItem)dnlink.getDownLink(d);
					if (mdlItem.getEntityType().equals("MODEL")){
						sb.append("<br />"+
						getTMFNavigationName(dnlink, mdlItem, savedFeatItem)+" - "+savedFeatItem.getKey());
					}
				}
			}
		}
		// get all tmf for second feature
		dnlinkVct = featItem.getDownLink();
		for (int i=0; i<dnlinkVct.size(); i++){
			EntityItem dnlink = (EntityItem)dnlinkVct.elementAt(i);
			if (dnlink.getEntityType().equals("PRODSTRUCT")){
				//find the model
				for (int d=0; d<dnlink.getDownLinkCount(); d++){
					EntityItem mdlItem = (EntityItem)dnlink.getDownLink(d);
					if (mdlItem.getEntityType().equals("MODEL")){
						sb.append("<br />"+
						getTMFNavigationName(dnlink, mdlItem, featItem)+" - "+featItem.getKey());
					}
				}
			}
		}
		return sb.toString();
	}*/

	/**********************************
	* Q.	DeriveEXTRACTRPQ
	*
	* Note:  this is unchanged (copied) from the current 30b Production ABR derivation.
	*
	* The following information will be inserted (updated if it already exists) into a new entity (EXTRACTRPQ - attribute derivation is shown  below):
	* EXTDTS = Date/Time of insert
	* EXTPRODSTRUCTEID = PRODSTRUCT.Entityid
	* MACHTYPEATR = MODEL.MACHTYPEATR
	* MODELATR = MODEL.MODELATR
	* ORDERCODE = PRODSTRUCT.ORDERCODE
	* FEATURECODE = FEATURE.FEATURECODE
	* COUNTRYLIST = FEATURE.COUNTRYLIST
	* FCTYPE = FEATURE.FCTYPE
	* RPQANNNUMBER = 'RPQ' & RIGHT('00000000000000' & CHAR(feature.entityid),15)
	* EXTMODELEID = MODEL.entityid
	* EXTREATUREEID = FEATURE.entityid
	* ZEROPRICE = FEATURE.ZEROPRICE
	* INVENTORYGROUP = FEATURE.INVENTORYGROUP
	* INSTALL = PRODSTRUCT.INSTALL
	* MKTGNAME = FEATURE. MKTGNAME
	* FIRSTANNDATE = FEATURE. FIRSTANNDATE
	*/
    private void deriveEXTRACTRPQ(EntityItem _eiFEATURE)
   	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		try{
			OPICMList infoList = new OPICMList();
			String strFileName = "PDGtemplates/PRODSTRUCTABRSTATUS1.txt";
			PDGUtility m_utility = new PDGUtility();

			EntityItem _eiPROD = m_elist.getParentEntityGroup().getEntityItem(0);
			EntityGroup eg = m_elist.getEntityGroup("MODEL");
			EntityItem _eiMODEL = eg.getEntityItem(0);

			infoList.put("TIMESTAMP", m_elist.getProfile().getValOn());
			infoList.put("PRODSTRUCT", _eiPROD);
			infoList.put("MODEL", _eiMODEL);
			infoList.put("FEATURE", _eiFEATURE);
			infoList.put("PRODID", _eiPROD.getEntityID() + "");
			infoList.put("FEATUREID", _eiFEATURE.getEntityID() + "");
			infoList.put("MODELID", _eiMODEL.getEntityID() + "");
			String str1 = "00000000000000" + _eiFEATURE.getEntityID();
			int iL = str1.length();
			if (iL > 15) {
				str1 = str1.substring(iL-15);
			}
			infoList.put("ANN", "RPQ" + str1);
			m_prof = m_utility.setProfValOnEffOn(m_db,m_prof);
			TestPDGII pdgObject = new TestPDGII(m_db, m_prof, null, infoList, strFileName );
			StringBuffer sbMissing = pdgObject.getMissingEntities();
			addDebug("deriveEXTRACTRPQ "+_eiFEATURE.getKey()+" "+_eiPROD.getKey()+" "+
				_eiMODEL.getKey()+" sbmissing: "+sbMissing);
			m_utility.putCreateAction("EXTRACTRPQ", "CREXTRACTRPQ1");
			m_utility.putSearchAction("EXTRACTRPQ", "SRDEXTRACTRPQ1");
			m_utility.generateData(m_db, m_prof, sbMissing, _eiPROD);
		}catch(COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException ex){
            ex.printStackTrace(System.out);
            throw new MiddlewareException(ex.getMessage());
        }catch(COM.ibm.eannounce.objects.SBRException ex){
            ex.printStackTrace(System.out);
            throw new MiddlewareException(ex.getMessage());
        }
	}

   /**
    *  Get ABR description
    *
    *@return    java.lang.String
    */
   public String getDescription() {
       return "PRODSTRUCTABRSTATUS ABR";
   }

    /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
        return new String("1.31");
    }

    /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
        return ("PRODSTRUCTABRSTATUS.java,v 1.31 2008/08/05 19:32:01 wendy Exp");
    }

    /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
        return "PRODSTRUCTABRSTATUS.java";
    }
}
