// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import COM.ibm.opicmpdh.middleware.*;
import java.util.*;

/**********************************************************************************
* SWPRODSTRUCTABRSTATUS class
*
* From "SG FS ABR Data Quality 20080502.doc"
*
*
SWPRODSTRUCTABRSTATUS_class=COM.ibm.eannounce.abr.sg.SWPRODSTRUCTABRSTATUS
SWPRODSTRUCTABRSTATUS_enabled=true
SWPRODSTRUCTABRSTATUS_idler_class=A
SWPRODSTRUCTABRSTATUS_keepfile=true
SWPRODSTRUCTABRSTATUS_report_type=DGTYPE01
SWPRODSTRUCTABRSTATUS_vename=DQVESWPRODSTRUCT
SWPRODSTRUCTABRSTATUS_CAT1=RPTCLASS.SWPRODSTRUCTABRSTATUS
SWPRODSTRUCTABRSTATUS_CAT2=
SWPRODSTRUCTABRSTATUS_CAT3=RPTSTATUS
SWPRODSTRUCTABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
*/
// SWPRODSTRUCTABRSTATUS.java,v
// Revision 1.20  2009/07/30 18:41:27  wendy
// Moved BH DQ ABRs to diff pkg
//
// Revision 1.18  2008/05/29 15:53:50  wendy
// Backout queueing ADSABRSTATUS
//
// Revision 1.17  2008/05/06 21:23:24  wendy
// Changes for SG FS ABR Data Quality 20080506.doc
//
// Revision 1.16  2008/05/01 12:02:46  wendy
// updates for SG FS ABR Data Quality 20080430.doc
//
// Revision 1.15  2008/04/24 21:33:27  wendy
// "SG FS ABR Data Quality 20080422.doc" spec updates
//
// Revision 1.14  2008/04/11 17:57:02  wendy
// changes for spec SG FS ABR Data Quality 200803xx.doc
//
// Revision 1.13  2008/02/13 19:58:50  wendy
// Make ABRWAITODSx into constants, allow easier change in future
//
// Revision 1.12  2007/12/19 17:02:57  wendy
// Always need VE for navigation name
//
// Revision 1.11  2007/10/25 21:17:39  wendy
// Spec chgs
//
// Revision 1.10  2007/10/23 17:47:12  wendy
// Spec changes
//
// Revision 1.9  2007/10/15 20:22:35  wendy
// QueueSAPL when PDHDOMAIN is not in list of domains
//
// Revision 1.8  2007/09/14 17:43:55  wendy
// Updated for GX
//
// Revision 1.7  2007/08/17 16:02:10  wendy
// RQ0713072645 - support for converged products. Another PDHDOMAIN was added.
// from 'SG FS xSeries ABRs 20070803.doc'
//
// Revision 1.6  2007/06/20 12:06:31  wendy
// Added mising 'GeographyList' level TIR74BL5E
//
// Revision 1.5  2007/06/13 21:18:22  wendy
// TIR73RS77 -XML Mapping updated
//
// Revision 1.4  2007/05/16 17:19:15  wendy
// TIR 7STP5L - Routing of XML correction required, spec chg
//
// Revision 1.3  2007/05/01 21:00:55  wendy
// TIR72PGQ9 VE won't pull MODELPROJA from (SW)PRODSTRUCT root, use 2 VEs
//
// Revision 1.2  2007/04/20 21:48:59  wendy
// RQ0417075638 updates
//
// Revision 1.1  2007/04/02 17:41:10  wendy
// Init for SAPL abr
//
public class SWPRODSTRUCTABRSTATUS extends DQABRSTATUS
{
	private Object args[] = new Object[3];

	/**********************************
	* always check if not final, but need navigation name from model and fc
	*/
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**********************************
	* complete abr processing when status is already final; (dq was final too)
	*D.	STATUS = Final
	*1.	Set SAPLABRSTATUS = 0020 (Queued)
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
	* complete abr processing after status moved to final; (status was r4r)
D.	STATUS changed to Final

1.	QueueSAPL
2.	Set EPIMSABRSTATUS =  &ABRWAITODS
3.	Set ADSABRSTATUS = 0020 (Queued)

	*
	*Note:  SWFEATURE does not flow to WWPRT
	*/
	protected void completeNowFinalProcessing() throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		queueSapl();
        setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", ABRWAITODS);
//        setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", ABR_QUEUED);
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
	*A.	STATUS = Draft | Change Request
	*
	*1.	SWPRODSTRUCT-d: MODEL then MODEL: SWPRODSTRUCT-u: SWFEATURE UNIQUE (FEATURECODE)
	*ErrorMessage LD(SWFEATURE) NDN(SWFEATURE) 'is not unique in this' LD(MODEL)
	*2.	IF ValueOf(SWPRODSTRUCT-u: SWFEATURE.FCTYPE) = 140 (PRPQ)  THEN ELSE  IF CompareAll(SWPRODSTRUCT.SAPL) = (90 (N/A) | 10 (Not Ready)) THEN
	*a.	CountOf( SWPRODSTRUCTAVAIL-d: AVAIL  => 1 WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) )
	*ErrorMessage 'does not have a Planned Availability'
3.	CompareAll(SWPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final) or 0040 (Ready for Review)
ErrorMessage LD(MODEL) 'Status is not Ready for Review or Final'
4.	CompareAll(SWPRODSTRUCT-u: SWFEATURE.STATUS) = 0020 (Final) or 0040 (Ready for Review)
ErrorMessage LD(FEATURE 'Status is not Ready for Review or Final'

	*B.	STATUS = Ready for Review
	*
	*1.	All checks from 'STATUS = Draft | Change Request'
	*2.	CompareAll(SWPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
	*ErrorMessage LD(MODEL) 'Status is not Final'
	*3.	CompareAll(SWPRODSTRUCT-u: SWFEATURE.STATUS) = 0020 (Final)
	*ErrorMessage LD(FEATURE 'Status is not Final'
	*4.	IF ValueOf(SWPRODSTRUCT-u: SWFEATURE.FCTYPE) = 140 (PRPQ)  THEN ELSE  IF CompareAll(SWPRODSTRUCT.SAPL) = (90 (N/A) | 10 (Not Ready)) THEN
	*a.	SWPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
	*ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
	*b.	MIN(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE; SWFEATURE.WITHDRAWDATEEFF_T) => CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 149 (Last Order)
	*ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) has a Last Order Availability later than the Model or SW Feature'.
	*c.	IF NotNull(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE) OR NotNull(SWPRODSTRUCT-d: SWFEATURE.WITHDRAWDATE) THEN AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	*ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
	*d.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN (SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
	*ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'. Note: the AVAIL is the one with the AVAILTYPE = 149.
e.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL -d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) or 143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
ErrorMessage  LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).
C.	STATUS changed to Ready for Review

1.	Set ADSABRSTATUS = 0020 (Queued)
D.	STATUS changed to Final

1.	QueueSAPL
2.	Set EPIMSABRSTATUS =  &ABRWAITODS
3.	Set ADSABRSTATUS = 0020 (Queued)

	*/
    protected void doDQChecking(EntityItem rootEntity, String statusFlag) throws Exception
    {
		//1.	All checks from 'STATUS = Draft | Change Request'
		checkAllFeatures(); // always do this check
		// from SG FS ABR Data Quality 200803xx.doc do first check if draft or chgreq
		//2.	IF ValueOf(SWPRODSTRUCT-u: SWFEATURE.FCTYPE) = 140 (PRPQ)  THEN ELSE
		//	IF CompareAll(SWPRODSTRUCT.SAPL) = (90 (N/A) | 10 (Not Ready)) THEN
		//		a.	CountOf( SWPRODSTRUCTAVAIL-d: AVAIL  => 1 WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) )
		//		ErrorMessage 'does not have a Planned Availability'
		//if status is R4R do the rest of these checks
		//4.	IF ValueOf(SWPRODSTRUCT-u: SWFEATURE.FCTYPE) = 140 (PRPQ)  THEN ELSE
		//	IF CompareAll(SWPRODSTRUCT.SAPL) = (90 (N/A) | 10 (Not Ready)) THEN
		//		a.	SWPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
		//		ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
		//		b.	MIN(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE; SWFEATURE.WITHDRAWDATEEFF_T) => CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 149 (Last Order)
		//		ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) has a Last Order Availability later than the Model or SW Feature'.
		//		c.	IF NotNull(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE) OR NotNull(SWPRODSTRUCT-d: SWFEATURE.WITHDRAWDATE) THEN AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN (PRODSTRUCT: OOFAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
		//		ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
		//		d.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN (SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
		//		ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'. Note: the AVAIL is the one with the AVAILTYPE = 149.
		//		e.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL -d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) or 143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
		//		ErrorMessage  LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).
		checkRPQAvails(rootEntity,statusFlag);

		if(STATUS_DRAFT.equals(statusFlag) || STATUS_CHGREQ.equals(statusFlag)) // 'Draft or Ready for Review'
		{
	        EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0); // has to exist
	        EntityItem swfcItem = m_elist.getEntityGroup("SWFEATURE").getEntityItem(0); // has to exist

			//3.	CompareAll(SWPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final) or 0040 (Ready for Review)
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
			//4.	CompareAll(SWPRODSTRUCT-u: SWFEATURE.STATUS) = 0020 (Final) or 0040 (Ready for Review)
			//ErrorMessage LD(SWFEATURE 'Status is not Ready for Review or Final'
			status = getAttributeFlagEnabledValue(swfcItem , "STATUS");
			addDebug(swfcItem.getKey()+" check status "+status);
			if (status==null){
				status = STATUS_FINAL;
			}

			if (!STATUS_FINAL.equals(status) && !STATUS_R4REVIEW.equals(status)){
				addDebug(swfcItem.getKey()+" is not Final or R4R");
				//NOT_R4R_FINAL_ERR = {0} {1} is not Ready for Review or Final.
				args[0] = swfcItem.getEntityGroup().getLongDescription();
				args[1] = getNavigationName(swfcItem);
				addError("NOT_R4R_FINAL_ERR",args);
			}
		}

		if(STATUS_R4REVIEW.equals(statusFlag)) // 'Ready for Review to Final'
		{
			//2.CompareAll(SWPRODSTRUCT-d: MODEL.STATUS) = 0020 (Final)
			//ErrorMessage LD(MODEL) ' is not Final'
			checkStatus("MODEL");

			//3.CompareAll(SWPRODSTRUCT-d: SWFEATURE.STATUS) = 0020 (Final)
			//ErrorMessage LD(SWFEATURE) ' is not Final'
			checkStatus("SWFEATURE");
		}
	}

    /**********************************
    *
Wendy   i think u can pull things tied to a feature when prodstruct is root..
bnair@us.ibm.c...   right, u can
Wendy   but seems like u cant pull things tied to a model when prodstruct is root
bnair@us.ibm.c...   right, because the entry which seeds the trsnavigate always says root is 'UP' when the root entity is a relator
bnair@us.ibm.c...   so thats a limitation with gbl8000
    */
	private void checkAllFeatures() throws Exception
	{
        Profile profile = m_elist.getProfile();
        String VeName = "DQVESWPRODSTRUCT2";

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
			if (!psItem.getEntityType().equals("SWPRODSTRUCT")){
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

		mdlList.dereference();
	}

	/********************************
	* this checks the FCTYPE of the SWFEATURE, if it is not an RPQ
	* it checks the SAPL value of the root SWPRODSTRUCT
	* if it is NA or not ready, AVAIL checks are needed
	*/
    private void checkRPQAvails(EntityItem rootEntity, String statusFlag) throws java.sql.SQLException, MiddlewareException
	{
		// check FCTYPE
		Set fctypeSet = new HashSet();
		//SWFEATURE.FCTYPE) = 140 (RPQ)
		fctypeSet.add("140");

		EntityItem featItem = m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
		addDebug("checkRPQAvails checking "+featItem.getKey());
		//4.	IF ValueOf(SWPRODSTRUCT-u: SWFEATURE.FCTYPE) = 140 (PRPQ)  THEN ELSE
		// check FCTYPE
		if (!PokUtils.contains(featItem, "FCTYPE", fctypeSet)){
			addDebug("checkRPQAvails "+featItem.getKey()+" is NOT an RPQ");
			// is not an RPQ
			// look at avails
			EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

			// get SAPL value
			String sapl = getAttributeFlagEnabledValue(rootEntity, "SAPL"); // (sw)prodstruct
			addDebug("checkRPQAvails checking entity: "+rootEntity.getKey()+" sapl "+sapl);
			Set saplSet = new HashSet();
			saplSet.add(SAPL_NOTREADY);
			saplSet.add(SAPL_NA);
			//IF CompareAll(PRODSTRUCT.SAPL) = ('N/A' (90) | 'Not Ready' (10)) THEN
			if (PokUtils.contains(rootEntity, "SAPL", saplSet)){
				addDebug("checkRPQAvails "+rootEntity.getKey()+" has SAPL of 90 or 10");

				//a.	CountOf( SWPRODSTRUCTAVAIL-d: AVAIL  => 1 WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) )
				//ErrorMessage 'does not have a Planned Availability'
				int plannedAvailCnt=0;
				for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
					EntityItem avail = (EntityItem)availGrp.getEntityItem(ai);
					addDebug("checkRPQAvails looking for planned avail; checking: "+avail.getKey());
					if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
						plannedAvailCnt++;
					}
				}
				if (plannedAvailCnt==0){
					//ErrorMessage 'does not have a Planned Availability'
					//NO_PLANNEDAVAIL_ERR = does not have a Planned Availability
					addError("NO_PLANNEDAVAIL_ERR",null);
				}

				// the rest of these are only done if R4R
				if(STATUS_R4REVIEW.equals(statusFlag)){ // 'Ready for Review to Final'

					//a.	SWPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
					//ErrorMessage LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
					checkAnnDate(rootEntity, availGrp);

					//b.MIN(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE; SWFEATURE.WITHDRAWDATEEFF_T) => CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 149 (Last Order)
					//ErrorMessage has a Last Order Availability later than the Model or SW Feature'.
					checkMinWDate(rootEntity, availGrp);

					//c.	IF NotNull(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE) OR NotNull(SWPRODSTRUCT-d: SWFEATURE.WITHDRAWDATE) THEN
					// AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN
					//  (SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
					//ErrorMessage 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
					checkLastOrderWDCountries(rootEntity,availGrp);

					//d.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN
					//(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
					//ErrorMessage LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'. Note: the AVAIL is the one with the AVAILTYPE = 149.
					checkPlannedAvailWDCountries(availGrp);

					//e.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL -d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146
					//(Planned Availability) or 143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
					//ErrorMessage  LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).
					checkAvailDates(availGrp);
				}

			}// end SAPL is NA or NOTREADY
			else{
				addDebug("checkRPQAvails "+rootEntity.getKey()+" is not SAPL of 90 or 10");
			}
		}else{
			addDebug("checkRPQAvails "+featItem.getKey()+" is an RPQ");
		}

		fctypeSet.clear();
	}

    /***********************************************
e.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL -d: AVAIL.EFFECTIVEDATE WHERE ValueOf(AVAILTYPE) = 146
(Planned Availability) or 143 (First Order)) => (AVAILANNA: ANNOUNCEMENT.ANNDATE)
ErrorMessage  LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT) LD(AVAIL) NDN(AVAIL) "is earlier than" LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) LD(ANNDATE).
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
    *b.	SWPRODSTRUCT-u:MODEL.ANNDATE <= CompareAll(AVAIL.EFFECTIVEDATE) WHERE AVAIL.AVAILTYPE = 146 (Planned Availability)
    *ErrorMessage 'has a Planned Availability earlier than the' LD(MODEL) LD(ANNDATE).
    */
	private void checkAnnDate(EntityItem rootEntity,EntityGroup availGrp)
		throws java.sql.SQLException, MiddlewareException
	{
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
    	String annDate = PokUtils.getAttributeValue(mdlItem, "ANNDATE",", ", "", false);
		addDebug("checkAnnDate "+mdlItem.getKey()+" ("+annDate+")");

		if (annDate.length()>0){
			for (int ai=0; ai<availGrp.getEntityItemCount(); ai++){ // look at avails
				EntityItem avail = (EntityItem)availGrp.getEntityItem(ai);
				addDebug("checkAnnDate looking for planned avail; checking: "+avail.getKey());
				if(PokUtils.isSelected(avail, "AVAILTYPE", PLANNEDAVAIL)){
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
	}

    /**********************************
    *c.	MIN(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE; SWFEATURE.WITHDRAWDATEEFF_T) => CompareAll(AVAIL.EFFECTIVEDATE)
    * WHERE AVAIL.AVAILTYPE = 149 (Last Order)
    * ErrorMessage has a Last Order Availability later than the Model or SW Feature'.
    */
	private void checkMinWDate(EntityItem rootEntity,EntityGroup availGrp)
		throws java.sql.SQLException, MiddlewareException
	{
		Vector tmp = new Vector(2);
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
    	String mdlDate = PokUtils.getAttributeValue(mdlItem, "WITHDRAWDATE",", ", null, false);
		EntityItem featItem = m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
    	String fcDate = PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T",", ", null, false);
		addDebug("checkMinWDate "+featItem.getKey()+" ("+fcDate+") "+
			mdlItem.getKey()+" ("+mdlDate+")");

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
	*d.	IF NotNull(SWPRODSTRUCT-u: MODEL.WITHDRAWDATE) OR NotNull(SWPRODSTRUCT-d: SWFEATURE.WITHDRAWDATE) THEN
	*AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability) IN
	*(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order)
	*ErrorMessage 'is being withdrawn and is available in a Country that does not have a Last Order' LD(AVAIL).
    */
	private void checkLastOrderWDCountries(EntityItem rootEntity,EntityGroup availGrp)
		throws java.sql.SQLException, MiddlewareException
	{
		EntityItem mdlItem = m_elist.getEntityGroup("MODEL").getEntityItem(0);
    	String mdlDate = PokUtils.getAttributeValue(mdlItem, "WITHDRAWDATE",", ", null, false);
		EntityItem featItem = m_elist.getEntityGroup("SWFEATURE").getEntityItem(0);
    	String fcDate = PokUtils.getAttributeValue(featItem, "WITHDRAWDATEEFF_T",", ", null, false);
		addDebug("checkLastOrderWDCountries "+featItem.getKey()+" ("+fcDate+") "+
			mdlItem.getKey()+" ("+mdlDate+")");

		if (mdlDate != null || fcDate != null){
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
	*e.	AllValuesOf(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 149 (Last Order) IN
	*(SWPRODSTRUCT: SWPRODSTRUCTAVAIL-d: AVAIL.COUNTRYLIST WHERE ValueOf(AVAILTYPE) = 146 (Planned Availability)
	*ErrorMessage LD(AVAIL) NDN(AVAIL) 'is being withdrawn from a country that does not have a planned availability'.
	*Note: the AVAIL is the one with the AVAILTYPE = 149.
	*
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

    /***********************************************
    *  Get ABR description
    *
    *@return java.lang.String
    */
    public String getDescription()
    {
        String desc =  "SWPRODSTRUCT ABR";
        return desc;
    }

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getABRVersion()
    {
        return "1.20";
    }
}
