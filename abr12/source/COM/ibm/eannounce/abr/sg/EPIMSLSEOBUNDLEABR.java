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
* EPIMSLSEOBUNDLEABR class generates xml for LSEOBUNDLE
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
* <LseoBundleFinalNotification>
*   <EntityType>LSEOBUNDLE</EntityType>
*   <EntityId>(the entity id value)</EntityId>
*   <LseoBundleId>(the LSEOBUNDLE id value)</LseoBundleId>
*   <NotificationTime>(Timestamp of the notification)</NotificationTime>
*   <Enterprise>(enterprise value)</Enterprise>
* </LseoBundleFinalNotification>
*
* The XML for the subsequent time(s)
* <?xml version = "1.0" ?>
* <LseoBundleChangedNotification>
*   <EntityType>LSEOBUNDLE</EntityType>
*   <EntityId>(the entity id value)*</EntityId>
*   <LseoBundleId>(the LSEOBUNDLE id value)*</LseoBundleId>
*   <NotificationTime>(Timestamp of the notification)</NotificationTime>
*   <Enterprise>(enterprise value)</Enterprise>
* </LseoBundleChangedNotification>
*
* where
* - (the entity id value) is the PDH entityid for the LSEOBUNDLE
* - (the LSEO id value) is the value for LSEOBUNDLE.SEOID
* - (Timestamp of the notification) is the VALFROM for LSEOBUNDLE.STATUS = Final (0020) that queued the ABR
* - (enterprise value) is the PDH enterprise for the LSEOBUNDLE
*
*/
// EPIMSLSEOBUNDLEABR.java,v
// Revision 1.12  2009/08/09 01:31:40  wendy
// Dont need to hang onto properties file after load attr list
//
// Revision 1.11  2009/08/07 20:18:16  wendy
// RCQ00055687-WI ServicePac Simple Bundle Enablement
//
// Revision 1.10  2008/07/22 22:07:43  wendy
// remove unused import
//
// Revision 1.9  2008/07/22 21:51:13  wendy
// restore country check
//
// Revision 1.8  2008/06/26 13:54:29  wendy
// backout country check for now, VE for LSEO is not getting promoted yet
//
// Revision 1.7  2008/06/25 13:25:03  wendy
// CQ00006088-WI - LA CTO Support - The requirement is to not feed LA products to ePIMS.
//
// Revision 1.6  2008/04/08 12:27:19  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR ePIMS Notification 20080407.doc"
//
// Revision 1.5  2008/03/18 14:43:32  wendy
// Correct LSEOBUNDLE tag
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
public class EPIMSLSEOBUNDLEABR extends EPIMSABRBase
{
    private static final Vector FIRSTFINAL_XMLMAP_VCT;
    private static final Vector CHGFINAL_XMLMAP_VCT;
    private static final Hashtable ATTR_OF_INTEREST_TBL;
    private static final String PROPERTIES_FNAME = "EPIMSLSEOBUNDLEABR_AOI.properties";
    
    /*
	EPIMSLSEOBUNDLEVE	LSEOBUNDLEAVAIL	D	0
	EPIMSLSEOBUNDLEVE	LSEOBUNDLEGAA	D	0
	EPIMSLSEOBUNDLEVE	LSEOBUNDLELSEO	D	0
	EPIMSLSEOBUNDLEVE	LSEOBUNDLEPROJA	D	0
    EPIMSLSEOBUNDLEVE	AVAILANNA		D	1
	EPIMSLSEOBUNDLEVE	AVAILGAA		D	1
	*/

/*
attr of interest
i		LSEOBUNDLE	TAXCD
i		LSEOBUNDLE	XXPARTNO
x		ANNOUNCEMENT	ANNDATE
x		ANNOUNCEMENT	ANNNUMBER
x		AVAIL	AVAILTYPE
x		GENERALAREA	SLEORG
x		LSEOBUNDLE	ACCTASGNGRP
x		LSEOBUNDLE	BUNDLPUBDATEMTRGT
x		LSEOBUNDLE	BUNDLUNPUBDATEMTRGT
x		LSEOBUNDLE	EANCD
x		LSEOBUNDLE	FLFILSYSINDC
x		LSEOBUNDLE	JANCD
x		LSEOBUNDLE	MATRLGRP1
x		LSEOBUNDLE	MATRLGRP3
x		LSEOBUNDLE	PRCFILENAM
x		LSEOBUNDLE	PRODHIERCD
x		LSEOBUNDLE	SAPASSORTMODULE
x		LSEOBUNDLE	SEOID
i		LSEOBUNDLE	TAXCD
x		LSEOBUNDLE	UPCCD
i		LSEOBUNDLE	XXPARTNO
x		LSEOBUNDLELSEO	LSEOQTY
x		PROJ	DIV
*/
    static {
    	// read list of attributes of interest from a properties file
		ATTR_OF_INTEREST_TBL = new Hashtable();
		loadAttrOfInterest(PROPERTIES_FNAME,ATTR_OF_INTEREST_TBL);

		/*ATTR_OF_INTEREST_TBL.put("ANNOUNCEMENT",new String[]{"ANNDATE","ANNNUMBER"});
		ATTR_OF_INTEREST_TBL.put("AVAIL",new String[]{"AVAILTYPE"});
		ATTR_OF_INTEREST_TBL.put("GENERALAREA",new String[]{"SLEORG"});
		ATTR_OF_INTEREST_TBL.put("LSEOBUNDLE",new String[]{"ACCTASGNGRP","BUNDLPUBDATEMTRGT",
			"BUNDLUNPUBDATEMTRGT","EANCD","FLFILSYSINDC","JANCD","MATRLGRP1","MATRLGRP3",
			"PRCFILENAM","PRODHIERCD","SAPASSORTMODULE","SEOID","TAXCD","UPCCD","XXPARTNO"});
		ATTR_OF_INTEREST_TBL.put("LSEOBUNDLELSEO",new String[]{"LSEOQTY"});
		ATTR_OF_INTEREST_TBL.put("PROJ",new String[]{"DIV"});*/

        FIRSTFINAL_XMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("LseoBundleFinalNotification");

        FIRSTFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","LSEOBUNDLE"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("LseoBundleId","LSEOBUNDLE","SEOID",true));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));

        CHGFINAL_XMLMAP_VCT = new Vector();  // set of elements
        topElem = new SAPLElem("LseoBundleChangedNotification");

        CHGFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","LSEOBUNDLE"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("LseoBundleId","LSEOBUNDLE","SEOID",true));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));
        
    }
     
    private EntityList lastFinalList = null;

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
	* 		ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'was queued to resend data; however, it is not Final'
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
			lastFinalList.dereference();
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
			//	Notify ePIMS: LSEOBUNDLE
			notifyAndSetStatus(null);
		}else{
			addDebug("More than one transition to Final found, check for change of interest.");
			//If LSEOBUNDLE: Change of Interest to ePIMS then
			if (changeOfInterest())
			{
				//	Notify ePIMS: LSEOBUNDLE
				notifyAndSetStatus(null);
			}else{
				// NO_CHG_FOUND = No change of interest found.
				String msg = epimsAbr.getBundle().getString("NO_CHG_FOUND");
				addOutput(msg);
				D.ebug("EPIMSABRSTATUS:LSEOBUNDLE "+msg);
			}
		}
		lastFinalList.dereference();
	}

	/*********************************
	* 	If STATUS = 'Final' (0020) then
	* 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
	* 	Else
	* 		ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'was queued to resend data; however, it is not Final'
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
        addDebug("execute "+rootEntity.getKey()+" STATUS: "+
            PokUtils.getAttributeValue(rootEntity, "STATUS",", ", "", false)+" ["+statusFlag+"] "+
            "EPIMSSTATUS: "+
            PokUtils.getAttributeValue(rootEntity, "EPIMSSTATUS",", ", null, false)+" ["+epimsFlag+"] ");

        if (!STATUS_FINAL.equals(statusFlag)){
			addError("ERROR_NOT_FINAL", null);
            return;
        }

        String specbid = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "SPECBID");
        addDebug("execute "+rootEntity.getKey()+" SPECBID: "+specbid+" EPIMSSTATUS: "+epimsFlag);

        EPWQGenerator epwqGen = new EPWQGenerator(epimsAbr);

        //The processing by this ABR is a function of the Current Value of EPIMSSTATUS.
        if (epimsFlag.equals(NOT_READY)){
            doNotReadyChecks(rootEntity, SPECBID_YES.equals(specbid), epwqGen);
        }else if (epimsFlag.equals(SENT)){
            doSentChecks(rootEntity, SPECBID_YES.equals(specbid), epwqGen);
        }else if (epimsFlag.equals(WAITING)){
            doWaitChecks(rootEntity, SPECBID_YES.equals(specbid), epwqGen);
        }else{
            addDebug("execute: Unsupported value for EPIMSSTATUS "+epimsFlag);
        }
    }

    /**********************************
    * If EPIMSSTATUS = 'Not Ready' then
    *   If LSEOBUNDLE: Check Contents = 'Waiting' then
    *       LSEOBUNDLE.EPIMSSTATUS = 'Waiting'
    *       Exit
    *   Else
    *       Notify ePIMS: LSEOBUNDLE
    *       LSEOBUNDLE.EPIMSSTATUS = 'Sent'
    *       Exit
    *   End if
    * End if
    * /
    private void doNotReadyChecks(EntityItem rootEntity,
        boolean isSpecBid, EPWQGenerator epwqGen) throws Exception
    {
		addDebug("doNotReadyChecks ");
        // check contents
        String contents = checkContents(rootEntity, isSpecBid,epwqGen);
        if (contents.equals(WAITING))
        {
            //LSEOBUNDLE.EPIMSSTATUS = 'Waiting'
            setEPIMSSTATUS(WAITING);
        }else{
            //Notify ePIMS: LSEOBUNDLE and LSEOBUNDLE.EPIMSSTATUS = 'Sent'
            notifyAndSetStatus(SENT);
        }
    }

    /**********************************
    * If EPIMSSTATUS = 'Sent' then
    *   If LSEOBUNDLE.COUNTRYLIST changed since 'Sent' then
    *   	If LSEOBUNDLE: Check Contents = 'Waiting' then
    *           LSEOBUNDLE.EPIMSSTATUS = 'Waiting'
    *           Exit
    *       Else
    *           If LSEOBUNDLE: Change of Interest to ePIMS then
    *               Notify ePIMS: LSEOBUNDLE
	*				LSEOBUNDLE.EPIMSSTATUS = 'Sent Again'
    *           End if
    *           Exit
    *       End if
    *   Else
    *       If LSEOBUNDLE: Change of Interest to ePIMS then
    *           Notify ePIMS: LSEOBUNDLE
	*			LSEOBUNDLE.EPIMSSTATUS = 'Sent Again'
    *       End if
    *       Exit
    *   End if
    * End if
    * /
    private void doSentChecks(EntityItem rootEntity,
        boolean isSpecBid, EPWQGenerator epwqGen) throws Exception
    {
        // get COUNTRYLIST timestamp
        Vector ctryDtsVct = epimsAbr.getChangeTimes("COUNTRYLIST", null);

        addDebug("doSentChecks lastfinal dts: "+epimsAbr.getLastFinalDTS()+
        	" COUNTRYLIST dts: "+ctryDtsVct.firstElement());
        if (ctryDtsVct.firstElement().toString().compareTo(epimsAbr.getLastFinalDTS())>0){ // ctry chgd since sent
           	addDebug("COUNTRYLIST has changed since last final");
            // check contents
        	String contents = checkContents(rootEntity, isSpecBid,epwqGen);
            if (contents.equals(WAITING))
            {
                //LSEOBUNDLE.EPIMSSTATUS = 'Waiting'
                setEPIMSSTATUS(WAITING);
            }else{
                //If LSEOBUNDLE: Change of Interest to ePIMS then
                if (changeOfInterest())
                {
                    //  Notify ePIMS: LSEOBUNDLE and LSEOBUNDLE.EPIMSSTATUS = 'Sent Again'
                    notifyAndSetStatus(SENT_AGAIN);
                }else{
            	    addDebug("doSentChecks No change of interest found");
            	}
            }
        }else{ // ctry was not chg'd since last 'sent'
           	addDebug("COUNTRYLIST has not changed since last final");
            //If LSEOBUNDLE: Change of Interest to ePIMS then
            if (changeOfInterest())
            {
                //  Notify ePIMS: LSEOBUNDLE and LSEOBUNDLE.EPIMSSTATUS = 'Sent Again'
                notifyAndSetStatus(SENT_AGAIN);
            }else{
                addDebug("doSentChecks No change of interest found");
            }
        }
        ctryDtsVct.clear();
    }

    /**********************************
    * Note:  the following applies to the remaining case where
    * If LSEOBUNDLE.EPIMSSTATUS = 'Waiting'
    *   If LSEOBUNDLE: Check Contents <> 'Waiting' then
    *		If LSEOBUNDLE: Change of Interest to ePIMS then
    *			Notify ePIMS: LSEOBUNDLE
    *			LSEOBUNDLE.EPIMSSTATUS = 'Sent'
    * 		End if
    * End if
    * /
    private void doWaitChecks(EntityItem rootEntity, boolean isSpecBid, EPWQGenerator epwqGen) throws Exception
    {
		addDebug("doWaitChecks ");
        String contents = checkContents(rootEntity, isSpecBid,epwqGen);
		if (!contents.equals(WAITING))
		{
			//If LSEOBUNDLE: Change of Interest to ePIMS then
			if (changeOfInterest())
			{
				//  Notify ePIMS: LSEOBUNDLE and LSEOBUNDLE.EPIMSSTATUS = 'Sent'
				notifyAndSetStatus(SENT);
            }else{
                addDebug("doWaitChecks No change of interest found");
            }
		}
    }

    /**********************************
    * XV.	LSEOBUNDLE: Check Contents
    *
    *LSEOBUNDLE:LSEO as s
    *
    *If SPECBID = Yes (11458) then
    *	If all s.EPIMSSTATUS = 'Released' then
    *		LSEOBUNDLE: Check Contents = 'Released'
    *	Else
    *		For each s.EPIMSSTATUS <> 'Released' then
    *			Add LSEOBUNDLE to EPwaitingQueue for s.EPIMSSTATUS = 'Released'
    *			LSEOBUNDLE: Check Contents = 'Waiting'
    *	End if
    *Else
    *	If all s.EPIMSSTATUS = 'Released' or 'Promoted' then
    *		LSEOBUNDLE: Check Contents = 'Promoted'
    *	Else
    *		For each s.EPIMSSTATUS <> 'Released' or 'Promoted' then
    *			Add LSEOBUNDLE to EPwaitingQueue for s.EPIMSSTATUS = 'Promoted'
    *			LSEOBUNDLE: Check Contents = 'Waiting'
    *	End if
    *End if
    *
    *EPwaitingQueue (EPWAITINGQUEUE)
    *EPWAITET	Set = 'LSEOBUNDLE'
    *EPWAITEID	Set = entityid
    *EPONET		Set = s.entitytype
    *EPONEID	Set = s.entityid
    *EPMACHTYPE	not set
    *EPMODELATR	not set
    *EPFEATURE	not set
    *EPSALESORG	not set
    *EPSTATUS	see above
    *
    * /
    private String checkContents(EntityItem rootEntity,
        boolean isSpecBid, EPWQGenerator epwqGen)
    throws
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
        java.rmi.RemoteException
    {
        String epimsStatus = null;
		addDebug("checkContents entered isSpecBid "+isSpecBid);

		EntityGroup egrp = epimsAbr.getEntityList().getEntityGroup("LSEO");

		if (isSpecBid){  // is Yes
			epimsStatus = RELEASED;
			for (int i=0; i<egrp.getEntityItemCount(); i++){
				EntityItem pitem = egrp.getEntityItem(i);
				String p_epimsFlag = epimsAbr.getAttributeFlagEnabledValue(pitem, "EPIMSSTATUS");
				if (p_epimsFlag == null){
					p_epimsFlag = NOT_READY;
				}
				addDebug("checkContents "+pitem.getKey()+" EPIMSSTATUS: "+
					PokUtils.getAttributeValue(pitem, "EPIMSSTATUS",", ", NOT_READY, false)+
					" ["+p_epimsFlag+"] ");
				if (!p_epimsFlag.equals(RELEASED)){
					epimsStatus = WAITING;
					addToQueue(rootEntity,pitem,epwqGen,RELEASED);
				}
			}
		}else{ // not a specbid
			epimsStatus = PROMOTED;
			for (int i=0; i<egrp.getEntityItemCount(); i++){
				EntityItem pitem = egrp.getEntityItem(i);
				String p_epimsFlag = epimsAbr.getAttributeFlagEnabledValue(pitem, "EPIMSSTATUS");
				if (p_epimsFlag == null){
					p_epimsFlag = NOT_READY;
				}
				addDebug("checkContents "+pitem.getKey()+" EPIMSSTATUS: "+
					PokUtils.getAttributeValue(pitem, "EPIMSSTATUS",", ", NOT_READY, false)+
					" ["+p_epimsFlag+"] ");
				if (!(p_epimsFlag.equals(RELEASED) || p_epimsFlag.equals(PROMOTED))){
					epimsStatus = WAITING;
					addToQueue(rootEntity,pitem,epwqGen,PROMOTED);
				}
			}
		}

		if(egrp.getEntityItemCount()==0){ // DQ ABR should prevent this
			addDebug("checkContents no LSEO found, force waiting");
			epimsStatus = WAITING;
		}

		addDebug("checkContents returning: "+epimsStatus);

        return epimsStatus;
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
			addDebug("changeOfInterest: Only one transition to Final found, no changes can exist.");
			return hadChgs;
		}

		// set lastfinal date in profile
	    //Profile lastprofile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
        //lastprofile.setValOnEffOn(lastdts, lastdts);

        // pull VE
		// get VE name
		String VEname = getVeName();
        //EntityList lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
		//	new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
		//	new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

		 // debug display list of groups
		//addDebug("changeOfInterest dts: "+lastdts+" extract: "+VEname+NEWLINE +
		//	PokUtils.outputList(lastFinalList));


		// set old date in profile
	    Profile profile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
        profile.setValOnEffOn(priordts, priordts);
		// create VE
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
		//lastFinalList.dereference();
		currlistRep.clear();
		prevlistRep.clear();

		return hadChgs;
	}

    /**********************************
    * check for specified country in the abr's countrylist
    * CQ00006088-WI
	* 	LA CTO Support - The requirement is to not feed LA products to ePIMS.
    * C.	General Availability LSEOBUNDLEs
    *	If the LSEOBUNDLE is not 'Special Bid', SPECBID = 'N' (11457), then the LSEOBUNDLE has
	* an AVAILability (one or more) with an AVAILTYPE = 'Planned Availability' (146). If any
	* country listed in the properties file exists in any of the AVAILabilities Country List
	* (AVAIL.COUNTRYLIST), then a notification should be sent to ePIMS.
	* D.	Custom or Special Bid LSEOBUNDLEs
	*	If the LSEOBUNDLE is a 'Special Bid', SPECBID = 'Y' (11458), then the LSEOBUNDLE Country
	* List (COUNTRYLIST) is checked. If any country listed in the properties file exists in the
	* LSEOBUNDLE.COUNTRYLIST, then a notification should be sent to ePIMS.
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
		String VEname = getVeName();
        lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
			new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
			new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

		 // debug display list of groups
		addDebug("validCountry dts: "+lastdts+" extract: "+VEname+NEWLINE +
			PokUtils.outputList(lastFinalList));

		// get root entity
		EntityItem rootEntity = lastFinalList.getParentEntityGroup().getEntityItem(0);
		String specbid = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		addDebug(rootEntity.getKey()+" SPECBID: "+specbid);
		if ("11457".equals(specbid)){  // is No
			// check AVAILs
			Vector availVct = PokUtils.getAllLinkedEntities(rootEntity, "LSEOBUNDLEAVAIL", "AVAIL");
			Vector plannedavailVector = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", DQABRSTATUS.PLANNEDAVAIL);//Planned Availability
			addDebug(" not a specbid- chk avails availVct.size "+availVct.size()+" plannedavailVector.size "+plannedavailVector.size());
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

		return inlist;
	}

    /**********************************
    * get the name of the VE to use
    */
    protected String getVeName() { return "EPIMSLSEOBUNDLEVE";}

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
