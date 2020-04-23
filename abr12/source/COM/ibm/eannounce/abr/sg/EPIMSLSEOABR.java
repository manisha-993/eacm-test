// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import com.ibm.transform.oim.eacm.util.*;
import java.util.*;
/**********************************************************************************
* EPIMSLSEOABR class generates xml for LSEO
*
* From "SG FS ABR ePIMS Notification 20080326.doc"
* This ABR is queued whenever an LSEOs or LSEOBUNDLEs is moved to Final by the Data Quality ABR.
* This ABR will notify ePIMS in three cases:
* 1.	The first time the data moves to Final.
* 2.	Subsequent times the data moves to Final and a change of interest was made.
* 3.	Resend a lost notification
*
*
* If SYSFEEDRESEND = 'Yes' (Yes) then
* 	If STATUS = 'Final' (0020) then
* 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
* 	Else
* 		ErrorMessage LD(LSEO) NDN(LSEO) 'was queued to resend data; however, it is not Final'
* 	End If
* 	Set SYSFEEDRESEND = 'No' (No)
* 	Done
* Else
* Continue with the next paragraph in this spec.
* End If
*
* If this is the first time that the ABR is Queued, then a notification is sent to ePIMS.
* If this is not the first time that the ABR is Queued, then a notification is sent only
* if an attribute of interest was changed.
*
* The XML for the first time
* <?xml version = "1.0" ?>
* <LseoFinalNotification>
* 	<EntityType>LSEO</EntityType>
* 	<EntityId>(the entity id value)</EntityId>
* 	<LseoId>(the LSEO id value)</LseoId>
* 	<NotificationTime>(Timestamp of the notification)</NotificationTime>
* 	<Enterprise>(enterprise value)</Enterprise>
* </LseoFinalNotification>
*
* The XML for the subsequent time(s)
* <?xml version = "1.0" ?>
* <LseoChangedNotification>
* 	<EntityType>LSEO</EntityType>
* 	<EntityId>(the entity id value)*</EntityId>
* 	<LseoId>(the LSEO id value)*</LseoId>
* 	<NotificationTime>(Timestamp of the notification)</NotificationTime>
* 	<Enterprise>(enterprise value)</Enterprise>
* </LseoChangedNotification>
*
* where
* -	(the entity id value) is the PDH entityid for the LSEO
* -	(the LSEO id value) is the value for LSEO.SEOID
* -	(Timestamp of the notification) is the VALFROM for LSEO.STATUS = Final (0020) that queued the ABR
* -	(enterprise value) is the PDH enterprise for the LSEO
*
*/
// EPIMSLSEOABR.java,v
// Revision 1.12  2009/08/09 01:31:40  wendy
// Dont need to hang onto properties file after load attr list
//
// Revision 1.11  2009/08/07 18:29:12  wendy
// Move AttributesOfInterest to properties file
//
// Revision 1.10  2008/07/23 13:42:12  wendy
// output warning if ve for country check is not defined
//
// Revision 1.9  2008/07/22 21:51:13  wendy
// restore country check
//
// Revision 1.8  2008/06/26 13:52:28  wendy
// backout country check for now, VE is not getting promoted yet
//
// Revision 1.7  2008/06/25 13:25:03  wendy
// CQ00006088-WI - LA CTO Support - The requirement is to not feed LA products to ePIMS.
//
// Revision 1.6  2008/05/27 14:28:59  wendy
// Clean up RSA warnings
//
// Revision 1.5  2008/04/08 12:27:18  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR ePIMS Notification 20080407.doc"
//
// Revision 1.4  2008/03/17 19:48:55  wendy
// Add output of 'no change of interest' to tm log
//
// Revision 1.3  2007/11/30 22:07:46  wendy
// cleanup
//
// Revision 1.2  2007/11/28 22:58:07  wendy
// merged with WWPRT abr base class
//
// Revision 1.1  2007/11/16 21:09:03  nancy
// GX and 30b version support
//
public class EPIMSLSEOABR extends EPIMSABRBase
{
    private static final Vector FIRSTFINAL_XMLMAP_VCT;
    private static final Vector CHGFINAL_XMLMAP_VCT;
    private static final Hashtable ATTR_OF_INTEREST_TBL;
    private static final String PROPERTIES_FNAME = "EPIMSLSEOABR_AOI.properties";

/*
EPIMSLSEOVE1	0	LSEO		LSEOGAA				GENERALAREA	D
EPIMSLSEOVE1	0	LSEO		LSEOAVAIL			AVAIL	D
EPIMSLSEOVE1	0	LSEO		LSEOPRODSTRUCT		PRODSTRUCT	D
EPIMSLSEOVE1	0	LSEO		LSEOSWPRODSTRUCT	SWPRODSTRUCT	D
EPIMSLSEOVE1	1	AVAIL		AVAILANNA			ANNOUNCEMENT	D
EPIMSLSEOVE1	1	AVAIL		AVAILGAA			GENERALAREA	D
EPIMSLSEOVE1	1	FEATURE		PRODSTRUCT			MODEL	D
EPIMSLSEOVE1	1	SWFEATURE	SWPRODSTRUCT		MODEL	D

EPIMSLSEOVE2	0	WWSEO		WWSEOLSEO			LSEO	U
EPIMSLSEOVE2	1	WWSEO		WWSEOPRODSTRUCT		PRODSTRUCT	D
EPIMSLSEOVE2	1	WWSEO		WWSEOSWPRODSTRUCT	SWPRODSTRUCT	D
EPIMSLSEOVE2	1	WWSEO		WWSEOPROJA			PROJ	D
EPIMSLSEOVE2	2	FEATURE		PRODSTRUCT			MODEL	D
EPIMSLSEOVE2	2	SWFEATURE	SWPRODSTRUCT		MODEL	D

EPIMSLSEOVE3	0	WWSEO		WWSEOLSEO			LSEO	U
EPIMSLSEOVE3	1	MODEL		MODELWWSEO			WWSEO	U
EPIMSLSEOVE3	2	MODEL		MODELAVAIL			AVAIL	D
EPIMSLSEOVE3	3	AVAIL		AVAILGAA			GENERALAREA	D



x	ANNOUNCEMENT	ANNDATE
x	ANNOUNCEMENT	ANNNUMBER
x	AVAIL	AVAILTYPE
x	FEATURE	FEATURECODE
x	GENERALAREA	SLEORG
x	LSEO	ACCTASGNGRP
x	LSEO	EANCD
x	LSEO	FLFILSYSINDC
x	LSEO	JANCD
x	LSEO	LSEOPUBDATEMTRGT
x	LSEO	LSEOUNPUBDATEMTRGT
x	LSEO	PRODHIERCD
x	LSEO	SAPASSORTMODULE
x	LSEO	SEOID
x	LSEO	UPCCD
i	LSEO	COUNTRYLIST
i	LSEO	TAXCD
i	LSEO	XXPARTNO
x	LSEOPRODSTRUCT	CONFQTY
x	LSEOSWPRODSTRUCT	SWCONFQTY
x	MODEL	COFCAT
x	MODEL	COFSUBCAT
x	MODEL	MACHTYPEATR
x	MODEL	MODELATR
x	MODEL	MODELORDERCODE
x	PROJ	DIV
x	SWFEATURE	FEATURECODE
x	WWSEO	MATRLGRP1
x	WWSEO	MATRLGRP3
x	WWSEO	PRCFILENAM
x	WWSEO	PRODHIERCD
x	WWSEO	PRODMGRCD
x	WWSEO	SEOID
x	WWSEO	SEOORDERCODE
x	WWSEO	SPECBID
i	WWSEO	MKTGNAME
i	WWSEO	UNSPSCCD
i	WWSEO	UNSPSCCDUOM
x	WWSEOPRODSTRUCT	CONFQTY
x	WWSEOSWPRODSTRUCT	SWCONFQTY

*/
    static {
    	// read list of attributes of interest from a properties file
		ATTR_OF_INTEREST_TBL = new Hashtable();
		loadAttrOfInterest(PROPERTIES_FNAME,ATTR_OF_INTEREST_TBL);
		
		/*ATTR_OF_INTEREST_TBL.put("ANNOUNCEMENT",new String[]{"ANNDATE","ANNNUMBER"});
		ATTR_OF_INTEREST_TBL.put("AVAIL",new String[]{"AVAILTYPE"});
		ATTR_OF_INTEREST_TBL.put("FEATURE",new String[]{"FEATURECODE"});
		ATTR_OF_INTEREST_TBL.put("GENERALAREA",new String[]{"SLEORG"});
		ATTR_OF_INTEREST_TBL.put("LSEO",new String[]{"ACCTASGNGRP","EANCD","FLFILSYSINDC",
			"JANCD","LSEOPUBDATEMTRGT","LSEOUNPUBDATEMTRGT","PRODHIERCD","SAPASSORTMODULE",
			"SEOID","UPCCD","COUNTRYLIST","TAXCD","XXPARTNO"});
		ATTR_OF_INTEREST_TBL.put("LSEOPRODSTRUCT",new String[]{"CONFQTY"});
		ATTR_OF_INTEREST_TBL.put("LSEOSWPRODSTRUCT",new String[]{"SWCONFQTY"});
		ATTR_OF_INTEREST_TBL.put("MODEL",new String[]{"COFCAT",
			"COFSUBCAT","MACHTYPEATR","MODELATR","MODELORDERCODE"});
		ATTR_OF_INTEREST_TBL.put("PROJ",new String[]{"DIV"});
		ATTR_OF_INTEREST_TBL.put("SWFEATURE",new String[]{"FEATURECODE"});
		ATTR_OF_INTEREST_TBL.put("WWSEO",new String[]{"MATRLGRP1","MATRLGRP3","PRCFILENAM",
			"PRODHIERCD","PRODMGRCD","SEOID","SEOORDERCODE","SPECBID","MKTGNAME",
			"UNSPSCCD","UNSPSCCDUOM"});
		ATTR_OF_INTEREST_TBL.put("WWSEOPRODSTRUCT",new String[]{"CONFQTY"});
		ATTR_OF_INTEREST_TBL.put("WWSEOSWPRODSTRUCT",new String[]{"SWCONFQTY"});
		*/

        FIRSTFINAL_XMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("LseoFinalNotification");

        FIRSTFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","LSEO"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("LseoId","LSEO","SEOID",true));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));

        CHGFINAL_XMLMAP_VCT = new Vector();  // set of elements
        topElem = new SAPLElem("LseoChangedNotification");

        CHGFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","LSEO"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("LseoId","LSEO","SEOID",true));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));
    }
    
	//private EntityList ve1list = null;

    /**********************************
    * get the name(s) of the MQ properties file to use
	*/
    protected Vector getMQPropertiesFN() {
        Vector vct = new Vector(1);
        vct.add(EPIMSMQSERIES);
        return vct;
    }

    /**********************************
    * get xml object mapping
    */
    protected Vector getXMLMap(boolean isFirst) {
		if (isFirst) {
			return FIRSTFINAL_XMLMAP_VCT;
		}else{
			return CHGFINAL_XMLMAP_VCT;
		}
	}

    /**********************************
    * execute the derived class - 30b version
    * This is a complete replacement for the current ePIMS Notification ABRs and is a small subset of the function requested by the referenced base document.
    *
    * This ABR is queued whenever an LSEOs or LSEOBUNDLEs is moved to Final by the Data Quality ABR.
    * This ABR will notify ePIMS in three cases:
	* 1.	The first time the data moves to Final.
	* 2.	Subsequent times the data moves to Final and a change of interest was made.
	* 3.	Resend a lost notification
	* If SYSFEEDRESEND = 'Yes' (Yes) then
	* 	If STATUS = 'Final' (0020) then
	* 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
	* 	Else
	* 		ErrorMessage LD(LSEO) NDN(LSEO) 'was queued to resend data; however, it is not Final'
	* 	End If
	* 	Set SYSFEEDRESEND = 'No' (No)
	* 	Done
	* Else
	* Continue with the next paragraph in this spec.
	* End If
    */
    protected void execute() throws Exception
	{
		// make sure the STATUS is Final
		EntityItem rootEntity = epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);

       	String statusFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "STATUS");
       	String sysfeedFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "SYSFEEDRESEND");
       	
		addDebug("execute: "+rootEntity.getKey()+" STATUS: "+
			PokUtils.getAttributeValue(rootEntity, "STATUS",", ", "", false)+" ["+statusFlag+"] sysfeedFlag: "+
			sysfeedFlag);

		// check for country
		if (!validCountry()){
			//COUNTRY_NOT_LISTED = Offering country was not in the ABR's list of Countries, notification will not be sent.
			String msg = epimsAbr.getBundle().getString("COUNTRY_NOT_LISTED");
			addOutput(msg);
			return;
		}

		if (SYSFEEDRESEND_YES.equals(sysfeedFlag)){
			resendSystemFeed(rootEntity, statusFlag);
			return;
		}

		if (!STATUS_FINAL.equals(statusFlag)){
			//ERROR_NOT_FINAL = Status was not Final.
			addError("ERROR_NOT_FINAL", null);
			return;
		}

		if (epimsAbr.isFirstFinal()){
			addDebug("Only one transition to Final found, must be first.");
			//	Notify ePIMS: LSEO
			notifyAndSetStatus(null);
		}else{
			addDebug("More than one transition to Final found, check for change of interest.");
			//If LSEO: Change of Interest to ePIMS then
			if (changeOfInterest())
			{
				//	Notify ePIMS: LSEO
				notifyAndSetStatus(null);
			}else{
//				 NO_CHG_FOUND = No change of interest found.
				String msg = epimsAbr.getBundle().getString("NO_CHG_FOUND");
				addOutput(msg);
				D.ebug("EPIMSABRSTATUS:LSEO "+msg);
			}
		}
	}

	/*********************************
	*
	* 	If STATUS = 'Final' (0020) then
	* 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
	* 	Else
	* 		ErrorMessage LD(LSEO) NDN(LSEO) 'was queued to resend data; however, it is not Final'
	* 	End If
	* 	Set SYSFEEDRESEND = 'No' (No)
	* 	Done
	*/
	protected void handleResend(EntityItem rootEntity, String statusFlag) throws
	java.sql.SQLException, MiddlewareException, javax.xml.parsers.ParserConfigurationException,
	javax.xml.transform.TransformerException, COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, java.io.IOException
	{
		if (!STATUS_FINAL.equals(statusFlag)){
			//RESEND_NOT_FINAL = was queued to resend data; however, it is not Final.
			addError("RESEND_NOT_FINAL", null);
			return;
		}

		//	Notify ePIMS: LSEO
		notifyAndSetStatus(null);
	}

    /**********************************
    * execute the derived class - GX on hold for now
    * /
    protected void executeGX() throws Exception
	{
		// make sure the STATUS is Final
		EntityItem rootEntity = epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);
		// check value of STATUS attribute
        String epimsFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "EPIMSSTATUS");
        if (epimsFlag == null || epimsFlag.length()==0){
			epimsFlag = NOT_READY;
		}

       	String statusFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "STATUS");
		addDebug("execute: "+rootEntity.getKey()+" STATUS: "+
			PokUtils.getAttributeValue(rootEntity, "STATUS",", ", "", false)+" ["+statusFlag+"] "+
			"EPIMSSTATUS: "+
			PokUtils.getAttributeValue(rootEntity, "EPIMSSTATUS",", ", null, false)+" ["+epimsFlag+"] ");

		if (!STATUS_FINAL.equals(statusFlag)){
			addError("ERROR_NOT_FINAL", null);
			return;
		}

		EntityItem wwseoItem = epimsAbr.getEntityList().getEntityGroup("WWSEO").getEntityItem(0);
		String specbid = epimsAbr.getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
		addDebug("execute: "+wwseoItem.getKey()+" SPECBID: "+specbid+" EPIMSSTATUS: "+epimsFlag);

        EPWQGenerator epwqGen = new EPWQGenerator(epimsAbr);

		// everything but WAIT needs this second VE - EPIMSLSEOVE1
		if (!(epimsFlag.equals(WAITING) || epimsFlag.equals(WAIT_AGAIN))){
			String VEname = "EPIMSLSEOVE1";
			ve1list = epimsAbr.getDB().getEntityList(epimsAbr.getProfile(),
				new ExtractActionItem(null, epimsAbr.getDB(),epimsAbr.getProfile(),VEname),
				new EntityItem[] { new EntityItem(null, epimsAbr.getProfile(), epimsAbr.getEntityType(), epimsAbr.getEntityID()) });
			addDebug("execute dts: "+epimsAbr.getProfile().getValOn()+" extract: "+VEname+NEWLINE +
				PokUtils.outputList(ve1list));
		}

		//The processing by this ABR is a function of the Current Value of EPIMSSTATUS.
		if (epimsFlag.equals(NOT_READY)){
			doNotReadyChecks(rootEntity, wwseoItem,	SPECBID_YES.equals(specbid), epwqGen);
		}else if (epimsFlag.equals(SENT)){
			doSentChecks(rootEntity, wwseoItem,	SPECBID_YES.equals(specbid), epwqGen);
		}else if (epimsFlag.equals(SENT_AGAIN)){
			doSentAgainChecks(rootEntity, wwseoItem,SPECBID_YES.equals(specbid), epwqGen);
		}else if (epimsFlag.equals(RELEASED) || epimsFlag.equals(PROMOTED)){
			doPromotedOrReleasedChecks(rootEntity, wwseoItem, SPECBID_YES.equals(specbid), epwqGen);
		}else if (epimsFlag.equals(WAITING) || epimsFlag.equals(WAIT_AGAIN)){
			doWaitChecks(epimsFlag);
		}else{
			addDebug("execute: Unsupported value for EPIMSSTATUS "+epimsFlag);
		}

		if (ve1list!=null){
			ve1list.dereference();
		}
	}

    /**********************************
	* If EPIMSSTATUS = 'Not Ready' then
	* 	LSEO: Check Contents
	* 	If (Part1 or Part2 or Part3) = 'Waiting' then
	* 		LSEO.EPIMSSTATUS = 'Waiting'
	* 		Exit
	* 	Else
	* 		Notify ePIMS: LSEO
	* 		LSEO.EPIMSSTATUS = 'Sent'
	* 		Exit
	* 	End if
	* End if
	* /
	private void doNotReadyChecks(EntityItem rootEntity, EntityItem wwseoItem,
		boolean isSpecBid, EPWQGenerator epwqGen) throws Exception
	{
		addDebug("doNotReadyChecks ");
		// check contents
		String part1Status = checkWWSEO(rootEntity, wwseoItem, isSpecBid,epwqGen);
		String part2Status = checkWWSEOLSEOFeatures(ve1list, isSpecBid,epwqGen);
		String part3Status = checkModel(rootEntity, isSpecBid, epwqGen);
		if (part1Status.equals(WAITING) || part2Status.equals(WAITING) ||
			part3Status.equals(WAITING))
		{
			//LSEO.EPIMSSTATUS = 'Waiting'
			setEPIMSSTATUS(WAITING);
		}else{
			//Notify ePIMS: LSEO and LSEO.EPIMSSTATUS = 'Sent'
			notifyAndSetStatus(SENT);
		}
	}

    /**********************************
    * check for any changes in structure or specified attr between current chg to final and prior chg
    */
	protected boolean changeOfInterest()
	throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		boolean hadChgs = false;
		String lastdts = epimsAbr.getLastFinalDTS();
		String priordts = epimsAbr.getPriorFinalDTS();

		if (lastdts.equals(priordts)){
			addDebug("changeOfInterest Only one transition to Final found, no changes can exist.");
			return hadChgs;
		}

		// set lastfinal date in profile
	    Profile lastprofile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
        lastprofile.setValOnEffOn(lastdts, lastdts);

        // pull VE
		// get VE name
		String VEname = getVeName();
		// create VE3 for lastfinal time
		EntityList lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
			new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
			new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

		 // debug display list of groups
		addDebug("changeOfInterest dts: "+lastdts+" extract: "+VEname+NEWLINE +
			PokUtils.outputList(lastFinalList));

		// set prior date in profile
	    Profile profile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
        profile.setValOnEffOn(priordts, priordts);

		// create VE3 for prior time
		EntityList list = epimsAbr.getDB().getEntityList(profile,
			new ExtractActionItem(null, epimsAbr.getDB(),profile,VEname),
			new EntityItem[] { new EntityItem(null, profile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

		 // debug display list of groups
		addDebug("changeOfInterest dts: "+priordts+" extract: "+VEname+NEWLINE +
			PokUtils.outputList(list));

		// get the attributes as one string for each entityitem key
		Hashtable currlistRep = getStringRep(lastFinalList, ATTR_OF_INTEREST_TBL);
		Hashtable prevlistRep = getStringRep(list, ATTR_OF_INTEREST_TBL);

		hadChgs = changeOfInterest(currlistRep, prevlistRep);

		list.dereference();
		lastFinalList.dereference();
		currlistRep.clear();
		prevlistRep.clear();

		if (!hadChgs){
			// now look at VE1
			VEname = "EPIMSLSEOVE1";
			lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
				new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
				new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });
			addDebug("changeOfInterest dts: "+lastprofile.getValOn()+" extract: "+VEname+NEWLINE +
				PokUtils.outputList(lastFinalList));

			// create VE1 for prior time
			list = epimsAbr.getDB().getEntityList(profile,
				new ExtractActionItem(null, epimsAbr.getDB(),profile,VEname),
				new EntityItem[] { new EntityItem(null, profile, epimsAbr.getEntityType(),
					epimsAbr.getEntityID()) });

		 	// debug display list of groups
			addDebug("changeOfInterest dts: "+priordts+" extract: "+VEname+NEWLINE +
				PokUtils.outputList(list));

			currlistRep = getStringRep(lastFinalList, ATTR_OF_INTEREST_TBL);
			prevlistRep = getStringRep(list, ATTR_OF_INTEREST_TBL);

			hadChgs = changeOfInterest(currlistRep, prevlistRep);

			list.dereference();
			lastFinalList.dereference();
			currlistRep.clear();
			prevlistRep.clear();

			if (!hadChgs){
				// now pull VE2 for curtime and priortime
				VEname = "EPIMSLSEOVE2";
				lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
					new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
					new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });
				addDebug("changeOfInterest dts: "+lastprofile.getValOn()+" extract: "+VEname+NEWLINE +
					PokUtils.outputList(lastFinalList));

				// create VE2 for prior time
				list = epimsAbr.getDB().getEntityList(profile,
					new ExtractActionItem(null, epimsAbr.getDB(),profile,VEname),
					new EntityItem[] { new EntityItem(null, profile, epimsAbr.getEntityType(),
						epimsAbr.getEntityID()) });

				// debug display list of groups
				addDebug("changeOfInterest dts: "+priordts+" extract: "+VEname+NEWLINE +
					PokUtils.outputList(list));

				currlistRep = getStringRep(lastFinalList, ATTR_OF_INTEREST_TBL);
				prevlistRep = getStringRep(list, ATTR_OF_INTEREST_TBL);

				hadChgs = changeOfInterest(currlistRep, prevlistRep);

				lastFinalList.dereference();
				list.dereference();
				currlistRep.clear();
				prevlistRep.clear();
			}
		}
		return hadChgs;
	}

	/**********************************
	 * check for specified country in the abr's countrylist
	 * CQ00006088-WI
	 * 	LA CTO Support - The requirement is to not feed LA products to ePIMS.
	 * A.	General Availability LSEOs
	 * 	If the parent WWSEO is not 'Special Bid', SPECBID = 'N' (11457), then the LSEO has an
	 * AVAILability (one or more) with an AVAILTYPE = “Planned Availability” (146). If any country
	 * listed in the properties file exists in any of the AVAILabilities Country List (AVAIL.COUNTRYLIST),
	 * then a notification should be sent to ePIMS.
	 * B.	Custom or Special Bid LSEOs
	 * 	If the parent WWSEO is a 'Special Bid', SPECBID = 'Y' (11458), then the LSEO Country List
	 * (COUNTRYLIST) is checked. If any country listed in the properties file exists in the LSEO.COUNTRYLIST,
	 * then a notification should be sent to ePIMS.
	 *
	 */
	private boolean validCountry()
	throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException
	{
		boolean inlist = false;
		String lastdts = epimsAbr.getLastFinalDTS();

		// set lastfinal date in profile
		Profile lastprofile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
		lastprofile.setValOnEffOn(lastdts, lastdts);

		// pull VE
		// get VE name
		String VEname = "EPIMSLSEOVE4";
		EntityList list = epimsAbr.getDB().getEntityList(lastprofile,
				new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
				new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

		// debug display list of groups
		addDebug("validCountry dts: "+lastdts+" extract: "+VEname+NEWLINE +
				PokUtils.outputList(list));

		// get root entity
		EntityItem rootEntity = list.getParentEntityGroup().getEntityItem(0);
		EntityGroup wwseoGrp = list.getEntityGroup("WWSEO");
		if (wwseoGrp == null){
			inlist = true;
			addDebug("WARNING: VE "+VEname+" is not defined!");
		}else{
			EntityItem wwseoItem = wwseoGrp.getEntityItem(0);
			String specbid = epimsAbr.getAttributeFlagEnabledValue(wwseoItem, "SPECBID");
			addDebug(wwseoItem.getKey()+" SPECBID: "+specbid);
			if ("11457".equals(specbid)){  // is No
				Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOAVAIL", "AVAIL");
				Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE",
						DQABRSTATUS.PLANNEDAVAIL);//Planned Availability
				addDebug("WWSEO not a specbid- chk avails availVct.size "+availVct.size()+" plannedavailVector.size "+plannedavailVector.size());
				for (int i=0; i<plannedavailVector.size(); i++){
					EntityItem availitem = (EntityItem)plannedavailVector.elementAt(i);
					inlist = epimsAbr.checkABRCountryList(availitem);
					if (inlist){ // one in list is good enough
						break;
					}
				}
				availVct.clear();
				plannedavailVector.clear();
			}else{
				inlist = epimsAbr.checkABRCountryList(rootEntity);
			}
		}

		list.dereference();

		return inlist;
	}

    /**********************************
	* If EPIMSSTATUS = 'Sent' then
	*	If LSEO.COUNTRYLIST changed since 'Sent' then
	*		LSEO: Check Contents
	*		If (Part1 or Part2 or Part3) = 'Waiting' then
	*			LSEO.EPIMSSTATUS = 'Waiting Again'
	*			Exit
	*		Else
	*			If LSEO: Change of Interest to ePIMS then
	*				Notify ePIMS: LSEO
	*				LSEO.EPIMSSTATUS = 'Sent Again'
	*			End if
	*			Exit
	*		End if
	*	Else
	*		If LSEO: Change of Interest to ePIMS then
	*			Notify ePIMS: LSEO
	*			LSEO.EPIMSSTATUS = 'Sent Again'
	*		End if
	*		Exit
	*	End if
	* End if
	*
	* /
	private void doSentChecks(EntityItem rootEntity, EntityItem wwseoItem,
		boolean isSpecBid, EPWQGenerator epwqGen) throws Exception
	{
		// get COUNTRYLIST timestamp
        Vector ctryDtsVct = epimsAbr.getChangeTimes("COUNTRYLIST", null);
        addDebug("doSentChecks Sent lastfinaldts: "+epimsAbr.getLastFinalDTS()+
        	" COUNTRYLIST dts: "+ctryDtsVct.firstElement());

        if (ctryDtsVct.firstElement().toString().compareTo(epimsAbr.getLastFinalDTS())>0){ // ctry chgd since sent
           	addDebug("COUNTRYLIST has changed since last final");
			// check contents
			String part1Status = checkWWSEO(rootEntity, wwseoItem, isSpecBid,epwqGen);
			String part2Status = checkWWSEOLSEOFeatures(ve1list, isSpecBid,epwqGen);
			String part3Status = checkModel(rootEntity, isSpecBid, epwqGen);
			if (part1Status.equals(WAITING) || part2Status.equals(WAITING) ||
				part3Status.equals(WAITING))
			{
				//LSEO.EPIMSSTATUS = 'Waiting Again'
				setEPIMSSTATUS(WAIT_AGAIN);
			}else{
				//If LSEO: Change of Interest to ePIMS then
				if (changeOfInterest())
				{
					//	Notify ePIMS: LSEO and LSEO.EPIMSSTATUS = 'Sent Again'
					notifyAndSetStatus(SENT_AGAIN);
				}else{
					addDebug("doSentChecks No change of interest found");
				}
			}
		}else{ // ctry was not chg'd since last 'sent'
           	addDebug("COUNTRYLIST has not changed since last final");
			//If LSEO: Change of Interest to ePIMS then
			if (changeOfInterest())
			{
				//	Notify ePIMS: LSEO and LSEO.EPIMSSTATUS = 'Sent Again'
				notifyAndSetStatus(SENT_AGAIN);
			}else{
				addDebug("doSentChecks No change of interest found");
			}
		}
		ctryDtsVct.clear();
	}

    /**********************************
	* If EPIMSSTATUS = 'Sent Again' then
	* 	If LSEO.COUNTRYLIST changed since 'Sent' or 'Sent Again' then
	* 		LSEO: Check Contents
	* 		If (Part1 or Part2 or Part3) = 'Waiting' then
	* 			LSEO.EPIMSSTATUS = 'Waiting Again'
	* 			Exit
	* 		Else
	* 			If LSEO: Change of Interest to ePIMS then
	* 				Notify ePIMS: LSEO
	*				LSEO.EPIMSSTATUS = 'Sent Again'
	* 			End if
	* 			Exit
	* 		End if
	* 	Else
	* 		If LSEO: Change of Interest to ePIMS then
	* 			Notify ePIMS: LSEO
	*			LSEO.EPIMSSTATUS = 'Sent Again'
	* 		End if
	* 		Exit
	* 	End if
	* End if
	*
	* /
	private void doSentAgainChecks(EntityItem rootEntity, EntityItem wwseoItem,
		boolean isSpecBid, EPWQGenerator epwqGen) throws Exception
	{
		// get COUNTRYLIST timestamp
        Vector ctryDtsVct = epimsAbr.getChangeTimes("COUNTRYLIST", null);
        addDebug("doSentAgainChecks lastfinaldts: "+epimsAbr.getLastFinalDTS()+
        	" COUNTRYLIST dts: "+ctryDtsVct.firstElement());

        if (ctryDtsVct.firstElement().toString().compareTo(epimsAbr.getLastFinalDTS())>0){ // ctry chgd since sent
           	addDebug("COUNTRYLIST has changed since last final");
			// check contents
			String part1Status = checkWWSEO(rootEntity, wwseoItem, isSpecBid,epwqGen);
			String part2Status = checkWWSEOLSEOFeatures(ve1list, isSpecBid,epwqGen);
			String part3Status = checkModel(rootEntity, isSpecBid, epwqGen);
			if (part1Status.equals(WAITING) || part2Status.equals(WAITING) ||
				part3Status.equals(WAITING))
			{
				//LSEO.EPIMSSTATUS = 'Waiting Again'
				setEPIMSSTATUS(WAIT_AGAIN);
			}else{
				//If LSEO: Change of Interest to ePIMS then
				if (changeOfInterest())
				{
					//	Notify ePIMS: LSEO and LSEO.EPIMSSTATUS = 'Sent Again'
					notifyAndSetStatus(SENT_AGAIN);
				}else{
					addDebug("doSentAgainChecks No change of interest found");
				}
			}
		}else{ // ctry was not chg'd since last 'sent'
           	addDebug("COUNTRYLIST has not changed since last final");
			//If LSEO: Change of Interest to ePIMS then
			if (changeOfInterest())
			{
				//	Notify ePIMS: LSEO and LSEO.EPIMSSTATUS = 'Sent Again'
				notifyAndSetStatus(SENT_AGAIN);
			}else{
				addDebug("doSentAgainChecks No change of interest found");
			}
		}

		ctryDtsVct.clear();
	}

    /**********************************
	* If EPIMSSTATUS = 'Promoted' or 'Released' then
	* 	If LSEO.COUNTRYLIST changed since 'Promoted' or 'Released' then
	* 		LSEO: Check Contents
	* 		If (Part1 or Part2 or Part3) = 'Waiting' then
	* 			LSEO.EPIMSSTATUS = 'Waiting Again'
	* 			Exit
	* 		Else
	* 			If LSEO: Change of Interest to ePIMS then
	* 				Notify ePIMS: LSEO
	* 				LSEO.EPIMSSTATUS = 'Sent Again'
	* 			End if
	* 			Exit
	* 		End if
	* 	Else
	* 		If LSEO: Change of Interest to ePIMS then
	* 			Notify ePIMS: LSEO
	*			LSEO.EPIMSSTATUS = 'Sent Again'
	* 		End if
	* 		Exit
	* 	End if
	* End if
	*
	* /
	private void doPromotedOrReleasedChecks(EntityItem rootEntity, EntityItem wwseoItem,
		boolean isSpecBid, EPWQGenerator epwqGen) throws Exception
	{
		// get COUNTRYLIST timestamp
        Vector ctryDtsVct = epimsAbr.getChangeTimes("COUNTRYLIST", null);
        addDebug("doPromotedOrReleasedChecks lastfinaldts: "+epimsAbr.getLastFinalDTS()+
        	" COUNTRYLIST dts: "+ctryDtsVct.firstElement());

        if (ctryDtsVct.firstElement().toString().compareTo(epimsAbr.getLastFinalDTS())>0){ // ctry chgd since sent
           	addDebug("COUNTRYLIST has changed since last final");
			// check contents
			String part1Status = checkWWSEO(rootEntity, wwseoItem, isSpecBid,epwqGen);
			String part2Status = checkWWSEOLSEOFeatures(ve1list, isSpecBid,epwqGen);
			String part3Status = checkModel(rootEntity, isSpecBid, epwqGen);
			if (part1Status.equals(WAITING) || part2Status.equals(WAITING) ||
				part3Status.equals(WAITING))
			{
				//LSEO.EPIMSSTATUS = 'Waiting Again'
				setEPIMSSTATUS(WAIT_AGAIN);
			}else{
				//If LSEO: Change of Interest to ePIMS then
				if (changeOfInterest())
				{
					//	Notify ePIMS: LSEO and LSEO.EPIMSSTATUS = 'Sent Again'
					notifyAndSetStatus(SENT_AGAIN);
				}else{
					addDebug("doPromotedOrReleasedChecks No change of interest found");
				}
			}
		}else{ // ctry was not chg'd since last 'sent'
           	addDebug("COUNTRYLIST has not changed since last final");
			//If LSEO: Change of Interest to ePIMS then
			if (changeOfInterest())
			{
				//	Notify ePIMS: LSEO and LSEO.EPIMSSTATUS = 'Sent Again'
				notifyAndSetStatus(SENT_AGAIN);
			}else{
				addDebug("doPromotedOrReleasedChecks No change of interest found");
			}
		}
		ctryDtsVct.clear();
	}

    /**********************************
	* Note:  the following applies to the remaining two cases where
	* If LSEO.EPIMSSTATUS = 'Waiting' or 'Waiting Again'.
	*	If LSEO: Change of Interest to ePIMS then
	*		Notify ePIMS: LSEO
	*		If LSEO.EPIMSSTATUS = 'Waiting' then
	*			LSEO.EPIMSSTATUS = 'Sent'
	*		Else
	*			LSEO.EPIMSSTATUS = 'Sent Again'
	*		End if
	*	End if
	* End if
	* /
	private void doWaitChecks(String epimsFlag) throws Exception
	{
		addDebug("doWaitChecks ");
		//If LSEO: Change of Interest to ePIMS then
		if (changeOfInterest())	{
			//	Notify ePIMS: LSEO
			if (epimsFlag.equals(WAITING)){
				//LSEO.EPIMSSTATUS = 'Sent'
				notifyAndSetStatus(SENT);
			}else{
				//LSEO.EPIMSSTATUS = 'Sent Again'
				notifyAndSetStatus(SENT_AGAIN);
			}
		}else{
			addDebug("doWaitChecks No change of interest found");
		}
	}

    /**********************************
    * A.	Part 1 - LSEO’s WWSEO
    *
    * WWSEOLSEO:WWSEO as w
    *
    * If w.SPECBID = Yes (11458) then
    * 	If w.EPIMSSTATUS = 'Released' then
    * 		Part1 = 'Released'
    * 	Else
    * 		Add LSEO to EPwaitingQueue for w.EPIMSSTATUS = 'Released'
    * 		Part1 = 'Waiting'
    * 	End if
    * Else
    * 	If w.EPIMSSTATUS <> 'Released' or 'Promoted' then
    * 		Add LSEO to EPwaitingQueue for w.EPIMSSTATUS = 'Promoted'
    * 		Part1 = 'Waiting'
    * 	Else
    * 		Part1 = w.EPIMSSTATUS
    * 	End if
    * End if
    *
    * EPwaitingQueue (EPWAITINGQUEUE)
    * EPWAITET	Set = 'LSEO'
    * EPWAITEID	Set = entityid
    * EPONET		Set = w.entitytype
    * EPONEID		Set = w.entityid
    * EPMACHTYPE	not set
    * EPMODELATR	not set
    * EPFEATURE	not set
    * EPSALESORG	not set
    * EPSTATUS	see above
    *
    * /
	private String checkWWSEO(EntityItem rootEntity, EntityItem wwseoItem,
		boolean isSpecBid, EPWQGenerator epwqGen)
	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		java.rmi.RemoteException
	{
		String epimsStatus = WAITING;

		// get value of WWSEO.EPIMSSTATUS attribute
		String epimsFlag = epimsAbr.getAttributeFlagEnabledValue(wwseoItem, "EPIMSSTATUS");
		if (epimsFlag == null){
			epimsFlag = NOT_READY;
		}
		addDebug("checkWWSEO "+wwseoItem.getKey()+" EPIMSSTATUS: "+epimsFlag+" for specbid: "+isSpecBid);

		if (isSpecBid){  // is Yes
			if (epimsFlag.equals(RELEASED)){
				epimsStatus = RELEASED;
			}else{
				addToQueue(rootEntity,wwseoItem,epwqGen,RELEASED);
			}
		}else{  // is Not specbid
			if (!(epimsFlag.equals(RELEASED) || epimsFlag.equals(PROMOTED))){
				addToQueue(rootEntity,wwseoItem,epwqGen,PROMOTED);
			}else{
				epimsStatus = epimsFlag;
			}
		}

		addDebug("checkWWSEO returning: "+epimsStatus);

		return epimsStatus;
	}

	/**********************************
	* Check part3
	* C.	Part 3 - LSEO’s Model
	*
	* WWSEOLSEO:MODELWWSEO:MODEL as m
	* m:MODELAVAIL:AVAILGAA:GENERALAREA as so
	* EPMODEL as epm
	*
	* Requirements
	* 	m.MACHTYPEATR
	* 	m.MODELATR
	* 	so.SLEORG
	*
	* If epm.EPSTATUS exists where m.MACHTYPEATR = epm.EPMACHTYPE and
	* 	m.MODELATR = epm.EPMODEL  and so.SLEOG = epmEPSALESORG for each unique so.SLEOG then
	*
	* 	If w.SPECBID = Yes (11458) then
	* 		If epm.EPSTATUS = 'Released' then
	* 			Part3 = 'Released'
	* 		Else
	* 			Add LSEO to EPwaitingQueue for EPSTATUS = 'Released'
	* 			Part3 = 'Waiting'
	* 		End if
	* 	Else
	* 		If epm.EPSTATUS <> 'Released' or 'Promoted' then
	* 			Add LSEO to EPwaitingQueue for EPSTATUS = 'Promoted'
	* 			Part3 = 'Waiting'
	* 		Else
	* 			Part3 = epm.EPSTATUS
	* 		End if
	* 	End if
	* Else
	* 	If w.SPECBID = Yes (11458) then
	* 		Add LSEO to EPwaitingQueue for m. with EPSTATUS = 'Released'
	* 	Else
	* 		Add LSEO to EPwaitingQueue for m. with EPSTATUS = 'Promoted'
	* 	End if
	* 	Part3 = 'Waiting'
	* End if
	*
	* EPwaitingQueue (EPWAITINGQUEUE)
	* EPWAITET	Set = 'LSEO'
	* EPWAITEID	Set = entityid
	* EPONET		Set = epm.entitytype
	* EPONEID		Set = epm.entityid
	* EPMACHTYPE	not set
	* EPMODELATR	not set
	* EPFEATURE	not set
	* EPSALESORG	not set
	* EPSTATUS	see above
	*
	* /
	private String checkModel(EntityItem rootEntity, boolean isSpecBid, EPWQGenerator epwqGen)
	throws
		java.sql.SQLException,
		COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
		COM.ibm.eannounce.objects.SBRException,
		COM.ibm.eannounce.objects.EANBusinessRuleException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		java.rmi.RemoteException
	{
		String epimsStatus = WAITING;
		EntityGroup mdlgrp = epimsAbr.getEntityList().getEntityGroup("MODEL");
		EntityItem modelItem = mdlgrp.getEntityItem(0);
		addDebug("checkModel entered "+modelItem.getKey()+" for specbid: "+isSpecBid);
		String mapMT = "map_EPMACHTYPE=" +  pdgUtility.getAttrValue(modelItem, "MACHTYPEATR")+ ";";
		String mapMdl = "map_EPMODELATR=" +  pdgUtility.getAttrValue(modelItem, "MODELATR")+ ";";
		Vector sleorgVct = new Vector(1);
		boolean isWaiting = false;
		boolean foundEPM = false;

		// look at each generalarea from MODELAVAIL:AVAILGAA:GENERALAREA
		Vector availVector = PokUtils.getAllLinkedEntities(mdlgrp, "MODELAVAIL", "AVAIL");
		Vector genareaVector = PokUtils.getAllLinkedEntities(availVector, "AVAILGAA", "GENERALAREA");

		for (int i=0; i<genareaVector.size(); i++){
			EntityItem genItem = (EntityItem)genareaVector.elementAt(i);
			String sleorg = pdgUtility.getAttrValue(genItem, "SLEORG");
			addDebug("checkModel "+genItem.getKey()+" SLEORG: "+sleorg);
			if (sleorgVct.contains(sleorg)){
				continue;
			}
			sleorgVct.add(sleorg);
			// search for an EPMODEL
			StringBuffer sb = new StringBuffer(mapMT+mapMdl);
        	sb.append("map_EPSALESORG=" +  sleorg);
			String strSai = "SRDEPMODEL";
			/ *
			EPMODEL entity
				EPCOFCAT		T	Model Category
				EPMACHTYPE		T	Machine Type
				EPMODELABR		A	EPMODEL ABR
				EPMODELATR		T	Model
				EPNOTIFYTIME	T	Notification Time
				EPSALESORG		T	Sales Org
				EPSTATUS		T	ePIMs Status
				PDHDOMAIN
			* /
			EntityItem[] aei = epSearch(sb,strSai, "EPMODEL");
			if (aei==null || aei.length==0){ // no EPMODEL found
				addDebug("checkModel no EPMODEL found for "+sb);
			}else{
				foundEPM = true;
				// found an EPMODEL
				EntityItem epmdl = aei[0];
				String epFlag = epimsAbr.getAttributeFlagEnabledValue(epmdl, "EPSTATUS");
				if (epFlag == null){
					epFlag = NOT_READY;
				}
				addDebug("checkModel found "+epmdl.getKey()+" EPSTATUS: "+epFlag);
				if (isSpecBid){  // is Yes
					if (epFlag.equals(RELEASED)){
						epimsStatus = RELEASED;
					}else{
						addToQueue(rootEntity,modelItem,epwqGen,RELEASED);
						isWaiting = true;
					}
				}else{  // is Not specbid
					if (!(epFlag.equals(RELEASED) || epFlag.equals(PROMOTED))){
						addToQueue(rootEntity,modelItem,epwqGen,PROMOTED);
						isWaiting = true;
					}else{
						epimsStatus = epFlag;
					}
				}
			}
		}// end generalarea loop

		sleorgVct.clear();
		availVector.clear();
		genareaVector.clear();

		if (isWaiting){
			epimsStatus = WAITING;
		}
		if (!foundEPM){
			if (isSpecBid){  // is Yes
				addToQueue(rootEntity,modelItem,epwqGen,RELEASED);
			}else{  // is Not specbid
				addToQueue(rootEntity,modelItem,epwqGen,PROMOTED);
			}
		}

		addDebug("checkModel returning: "+epimsStatus);

		return epimsStatus;
	}


    /**********************************
    * get the name of the VE to use
    */
    protected String getVeName() { return "EPIMSLSEOVE3";}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.12";
    }
}
