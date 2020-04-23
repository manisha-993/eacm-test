// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
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
* WWPRTANNABR class generates xml for ANNOUNCEMENT
*
* From "SG FS ABR WWPRT Notification 20080326.doc"
* MN 35084789 - support resend of lost notification messages.
* This ABR gets triggered by the Data Quality ABR only when the ANNOUNCEMENT has a STATUS = Final.
*
* If this is the first time that the ABR is Queued, then a notification is sent to WWPRT.
* If this is not the first time that the ABR is Queued, then a notification is sent only
* if an attribute of interest was changed.
*
* <?xml version = "1.0" ?>
* <WWPRTNotification>
*    <EntityType>LSEO/LSEOBUNDLE/ANNOUNCEMENT/RPQ</Entity Type>
*    <EntityId>(the entity id value)</EntityId>
*    <Id>seoid/seo bundle id/annnumber/PRODSTRUCT entityid</Id>
*    <Change>no/yes</Change>
*    <NotificationTime>(Timestamp of the notification)</NotificationTime>
*    <Enterprise>(enterprise value)</Enterprise>
* </WWPRTNotification>
*
* where 'Change' is set as follows:
* First Time for this notification:  'No'
* Subsequent notifications:  'Yes'
*
* Notes:the notification is for one EntityType and EntityId
* RPQ = PRODSTRUCT
*
*/
// WWPRTANNABR.java,v
// Revision 1.5  2009/08/09 01:31:40  wendy
// Dont need to hang onto properties file after load attr list
//
// Revision 1.4  2009/08/07 18:29:12  wendy
// Move AttributesOfInterest to properties file
//
// Revision 1.3  2008/04/08 12:27:44  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR WWPRT Notification 20080407.doc"
//
// Revision 1.2  2008/01/30 19:39:14  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/12/03 19:28:07  wendy
// Init for WWPRT ABRs
//
public class WWPRTANNABR extends EPIMSABRBase
{
    private static final Vector FIRSTFINAL_XMLMAP_VCT;
    private static final Vector CHGFINAL_XMLMAP_VCT;
    private static final Hashtable ATTR_OF_INTEREST_TBL;
    private static final String PROPERTIES_FNAME = "WWPRTANNABR_AOI.properties";
    
	private static final String[] INDEPENDENT_OF_PRICEDFC = new String[]{"MODELAVAIL","LSEOAVAIL","LSEOBUNDLEAVAIL",
		"LSEOBUNDLELSEO", "LSEO","LSEOBUNDLE", "AVAIL"};
	private static final String[] PRICED_FCREL = new String[]{"OOFAVAIL","LSEOPRODSTRUCT","WWSEOPRODSTRUCT"};

    static {
   	 
    	// read list of attributes of interest from a properties file
		ATTR_OF_INTEREST_TBL = new Hashtable();
		loadAttrOfInterest(PROPERTIES_FNAME,ATTR_OF_INTEREST_TBL);
		
        /*ATTR_OF_INTEREST_TBL.put("MODEL",new String[]{"MACHTYPEATR","MODELATR","INSTALL","PROJCDNAM"});
        ATTR_OF_INTEREST_TBL.put("FEATURE",new String[]{"FEATURECODE","FCTYPE","ZEROPRICE","PRICEDFEATURE"});
        ATTR_OF_INTEREST_TBL.put("LSEO",new String[]{"SEOID","COUNTRYLIST","PRCINDC","ZEROPRICE"});
        ATTR_OF_INTEREST_TBL.put("WWSEOPRODSTRUCT",new String[]{"CONFQTY"});
        ATTR_OF_INTEREST_TBL.put("LSEOPRODSTRUCT",new String[]{"CONFQTY"});
        ATTR_OF_INTEREST_TBL.put("LSEOBUNDLE",new String[]{"SEOID","COUNTRYLIST"});
        ATTR_OF_INTEREST_TBL.put("LSEOBUNDLELSEO",new String[]{"LSEOQTY"});
        ATTR_OF_INTEREST_TBL.put("AVAIL",new String[]{"AVAILTYPE"});*/

        FIRSTFINAL_XMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("WWPRTNotification");
        FIRSTFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","ANNOUNCEMENT"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("Id","ANNOUNCEMENT","ANNNUMBER",true));
        topElem.addChild(new SAPLFixedElem("Change","no"));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));

        CHGFINAL_XMLMAP_VCT = new Vector();  // set of elements
        topElem = new SAPLElem("WWPRTNotification");

        CHGFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","ANNOUNCEMENT"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("Id","ANNOUNCEMENT","ANNNUMBER",true));
        topElem.addChild(new SAPLFixedElem("Change","yes"));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));
    }

    /**********************************
    * get the name(s) of the MQ properties file to use
    */
    protected Vector getMQPropertiesFN() {
        Vector vct = new Vector(1);
        vct.add(WWPRTMQSERIES);
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
    * execute the derived class
    * This ABR is queued only when STATUS = Final.
    *
    * The logical business data model states that an Announcement (ANNOUNCEMENT) has an
    * announcement date (ANNDATE) on which the availability (AVAIL) of 'offerings' is announced.
    * 'Offerings' that can be announced will have an availability (AVAIL) of a given type (AVAILTYPE),
    * an effective date (EFFECTIVEDATE), and a list of countries (COUNTRYLIST). 'Offerings' include:
    * MODEL, FEATUREs in a MODEL (PRODSTRUCT), LSEOs, and LSEOBUNDLEs. The types of availability
    * (AVAILTYPE) are:
    * Code	LongDescription
    * 143	First Order
    * 146	Planned Availability
    * 149	Last Order
    * 150	Last Return
    * 151	End of Service
    * 152	End of Dev Support
    * 153	Last Initial Order
    * 200	End of Marketing
    *
    * There are different types of Announcements (ANNTYPE):
    *
    * Code	LongDescription
    * 11	Other - Change to Business Term or Condition
    * 12	End Of Life - Change to End Of Service Date
    * 13	End Of Life - Discontinuance of service
    * 14	End Of Life - Withdrawal from mktg
    * 15	Other - Statement of Intent
    * 16	End Of Life - Both
    * 17	Other - Vision
    * 18	Change
    * 19	New
    *
    *
    * A.	Resend
    *
    * If SYSFEEDRESEND = 'Yes' (Yes) then
    * 	If ANNTYPE <> 'New' (19) then
    * 		Set SYSFEEDRESEND = 'No' (No)
    * 		ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) 'is not New; however it was queued to resend data'.
    * 		Done
    * 	End If
    * 	If the ANNOUNCEMENT does not have an AVAIL where AVAILTYPE = 'Planned Availability' (146) then
    * 		Set SYSFEEDRESEND = 'No' (No)
    * 		ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) 'was queued to resend data; however, there are no AVAILabilities that are of type Planned Availability'.
    * 		Done
    * 	End If
    * 	If STATUS = 'Final' (0020) then
    * 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
    * 		Done
    * 	Else
    * 		ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) 'was queued to resend data; however, it is not Final'
    * 	End If
    * 	Set SYSFEEDRESEND = 'No' (No)
    * 	Done
    * Else
    * 	Continue with the next paragraph in this specification.
    * End If
    *
    * B.	First Time
    *
    * This notification occurs whenever the status of a 'New' Announcement is changed to Final the first
    * time and is announcing the 'Planned Availability' (146) of one or more 'offerings'.
    * ANNOUNCEMENT.ANNSTATUS = Final (0020)
    * ANNOUNCEMENT.ANNTYPE = New (19)
    * AVAIL.AVAILTYPE = Planned Availability (146)
    *
    *
    * C.	Subsequent Times
    *
    * This notification occurs when there is a change in information within an announcement structure
    * that is of interest to WWPRT and the announcement status is Final (not the first time) and the
    * Announcement meets the criteria in A above.
    *
    * Let T1 = DTS of this Final and T2 = DTS of the prior Final. Then a change of interest is a change
    * in attribute value listed or in structure.
    *
    * The VE defining the structure of interest follows:
    *
    * Lev	Entity1	RelType	Relator	Entity2	Dir
    * 0	ANNOUNCEMENT	Assoc	ANNAVAILA	AVAIL	D
    * 1	MODEL	Relator	MODELAVAIL	AVAIL	U
    * 1	PRODSTRUCT	Relator	OOFAVAIL	AVAIL	U
    * 2	FEATURE	Relator	PRODSTRUCT	MODEL	U
    * 1	LSEO	Relator	LSEOAVAIL	AVAIL	U
    * 1	LSEOBUNDLE	Relator	LSEOBUNDLEAVAIL	AVAIL	U
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
    * Note:  in the preceding VE, the level 2 FEATURE is found as follows:
    * Start with ANNOUNCEMENT and find AVAILs
    * For an AVAIL, find the PRODSTRUCT
    * For a PRODSTRUCT, find the FEATURE
    *
    * Do not consider the FEATUREs found for the MODEL in level 1 via PRODSTRUCT.
    *
    * The changes of interest within this structure are filtered by Feature if applicable. Features (FEATURE)
    * are considered only if they are priced (PRICEDFEATURE = Yes (100). Items that add/remove Features are
    * indicated via an asterisk.
    *
    * All LSEOs in an Announcement are considered independent of the value of the Zero Priced Indicator
    *(ZEROPRICE) and Priced Indicator (PRCINDC).
    *
    * The changes that are watched are:
    *
    * 	New (added) or removed (deleted) relators
    * MODELAVAIL
    * OOFAVAIL *
    * LSEOAVAIL
    * LSEOBUNDLEAVAIL
    *
    * 	New (added) or removed (deleted) relators
    * LSEOPRODSTRUCT *
    * WWSEOPRODSTRUCT *
    * LSEOBUNDLELSEO
    *
    * 	New entities
    * AVAIL where AVAILTYPE = Planned Availability (146)
    *
    * The changes in data of interest within this structure are:
    *
    * MODEL - only for level 1 (from MODELAVAIL)
    * MACHTYPEATR
    * MODELATR
    * INSTALL
    * PROJCDNAM
    *
    * FEATURE
    * FEATURECODE
    * FCTYPE
    * ZEROPRICE
    * PRICEDFEATURE
    *
    * LSEO
    * SEOID
    * COUNTRYLIST
    * PRCINDC
    * ZEROPRICE
    *
    * LSEOBUNDLE
    * SEOID
    * COUNTRYLIST
    *
    * WWSEOPRODSTRUCT
    * CONFQTY
    *
    * LSEOPRODSTRUCT
    * CONFQTY
    *
    * LSEOBUNDLELSEO
    * LSEOQTY
    *
    * AVAIL
    * AVAILTYPE only if change to or from Planned Availability (146)
    * 	Note that this is a FLAG
    */
    protected void execute() throws Exception
    {
        // make sure the STATUS is Final
        EntityItem rootEntity = epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);

        String statusFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "ANNSTATUS");
       	String sysfeedFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "SYSFEEDRESEND");
  
		addDebug("execute: "+rootEntity.getKey()+" ANNSTATUS: "+
			PokUtils.getAttributeValue(rootEntity, "ANNSTATUS",", ", "", false)+" ["+statusFlag+"] sysfeedFlag: "+
			sysfeedFlag);

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

            if (checkForAvails())
            {
				notifyAndSetStatus(null);
			}else{
				addDebug("No AVAIL with AVAILTYPE=planned avail found");
			}
        }else{
            addDebug("More than one transition to Final found, check for change of interest.");
            //If Change of Interest to wwprt then
            if (changeOfInterest())
            {
                //  Notify
                notifyAndSetStatus(null);
            }else{
                addDebug("No change of interest found");
            }
        }
    }

	/**********************************
	* A.	Resend
	*
	* If SYSFEEDRESEND = 'Yes' (Yes) then
	* 	If ANNTYPE <> 'New' (19) then
	* 		Set SYSFEEDRESEND = 'No' (No)
	* 		ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) 'is not New; however it was queued to resend data'.
	* 		Done
	* 	End If
	* 	If the ANNOUNCEMENT does not have an AVAIL where AVAILTYPE = 'Planned Availability' (146) then
	* 		Set SYSFEEDRESEND = 'No' (No)
	* 		ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) 'was queued to resend data; however, there are no AVAILabilities that are of type Planned Availability'.
	* 		Done
	* 	End If
	* 	If STATUS = 'Final' (0020) then
	* 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
	* 		Done
	* 	Else
	* 		ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) 'was queued to resend data; however, it is not Final'
	* 	End If
	* 	Set SYSFEEDRESEND = 'No' (No)
	* 	Done
    * Else
    */
	protected void handleResend(EntityItem rootEntity, String statusFlag) throws
	java.sql.SQLException, MiddlewareException, javax.xml.parsers.ParserConfigurationException,
	javax.xml.transform.TransformerException, COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, java.io.IOException
	{
		String annType = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "ANNTYPE");
		addDebug("annType: "+annType);
		if (!"19".equals(annType)){ //If ANNTYPE <> 'New' (19) then
			//ErrorMessage LD(ANNOUNCEMENT) NDN(ANNOUNCEMENT) 'is not New; however it was queued to resend data'.
			//ANN_NOT_NEW = is not New; however it was queued to resend data.
			addError("ANN_NOT_NEW", null);
			return;
		}

		if (!STATUS_FINAL.equals(statusFlag)){ //If STATUS != 'Final' (0020)
			//RESEND_NOT_FINAL = was queued to resend data; however, it is not Final.
			addError("RESEND_NOT_FINAL", null);
			return;
		}

		//If the ANNOUNCEMENT does not have an AVAIL where AVAILTYPE = 'Planned Availability' (146) then
		//ANN_NO_AVAIL = was queued to resend data; however, there are no AVAILabilities that are of type Planned Availability.
        if (!checkForAvails()){
			addError("ANN_NO_AVAIL", null);
        	return;
		}

		//	Notify
		notifyAndSetStatus(null);
	}

	private boolean checkForAvails() throws java.sql.SQLException,MiddlewareException
	{
		String lastdts = epimsAbr.getLastFinalDTS(); // really queued DTS now

		boolean availFnd = false;
		// set lastfinal date in profile
		Profile lastprofile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
		lastprofile.setValOnEffOn(lastdts, lastdts);

		String VEname = getVeName();
		// create VE for lastfinal time
		EntityList lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
			new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
			new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

		 // debug display list of groups
		addDebug("checkForAvails dts: "+lastdts+" extract: "+VEname+NEWLINE +
			PokUtils.outputList(lastFinalList));

		//  Notify if at least one planned avail exists
		EntityGroup availGrp = lastFinalList.getEntityGroup("AVAIL");
		availFnd = availGrp.getEntityItemCount()>0;

		lastFinalList.dereference();

		return availFnd;
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
		boolean availFnd = false;  // must have at least one planned avail

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
        // create VE for lastfinal time
        EntityList lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
            new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
            new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

         // debug display list of groups
        addDebug("changeOfInterest dts: "+lastdts+" extract: "+VEname+NEWLINE +
            PokUtils.outputList(lastFinalList));

        // set prior date in profile
        Profile profile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
        profile.setValOnEffOn(priordts, priordts);

        // create VE for prior time
        EntityList list = epimsAbr.getDB().getEntityList(profile,
            new ExtractActionItem(null, epimsAbr.getDB(),profile,VEname),
            new EntityItem[] { new EntityItem(null, profile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

         // debug display list of groups
        addDebug("changeOfInterest dts: "+priordts+" extract: "+VEname+NEWLINE +
            PokUtils.outputList(list));

		//  do checks if at least one planned avail exists in t1 or t2 - VE is filtered
		EntityGroup availGrp = lastFinalList.getEntityGroup("AVAIL");
		if (availGrp.getEntityItemCount()>0){
			availFnd = true;
		}else{
			addDebug("No AVAIL with AVAILTYPE=planned avail found in last final");
			availGrp = list.getEntityGroup("AVAIL");
			if (availGrp.getEntityItemCount()>0){
				availFnd = true;
			}else{
				addDebug("No AVAIL with AVAILTYPE=planned avail found in prior final");
			}
		}

		if (availFnd){
	        // get the attributes as one string for each entityitem key
	        Hashtable currlistRep = getStringRep(lastFinalList, getAttrOfInterest());
	        Hashtable prevlistRep = getStringRep(list, getAttrOfInterest());

	        hadChgs = changeOfInterest(currlistRep, prevlistRep);
	        currlistRep.clear();
	        prevlistRep.clear();
		}

        list.dereference();
        lastFinalList.dereference();

        return hadChgs;
    }

    /**********************************
    * attributes to check for any changes
    */
    protected Hashtable getAttrOfInterest() { return ATTR_OF_INTEREST_TBL;}

    /**********************************
    * get hashtable with entitylist converted to strings.  key is entityitem key
    * value is the concatenated list of all attributes of interest.
    * The changes of interest within this structure are filtered by Feature if applicable.
    * Features (FEATURE) are considered only if they are priced (PRICEDFEATURE = Yes (100). Items
    * that add/remove Features are indicated via an asterisk. The changes that are watched are:
    *
    * 	New (added) or removed (deleted) relators
    * MODELAVAIL
    * OOFAVAIL *
    * LSEOAVAIL
    * LSEOBUNDLEAVAIL
    *
    * 	New (added) or removed (deleted) relators
    * LSEOPRODSTRUCT *
    * WWSEOPRODSTRUCT *
    * LSEOBUNDLELSEO
    *
    * The changes in data of interest within this structure are:
    *
    * MODEL - only for level 1 (from MODELAVAIL)
    * MACHTYPEATR
    * MODELATR
    * INSTALL
    * PROJCDNAM
    *
    * FEATURE
    * FEATURECODE
    * FCTYPE
    * ZEROPRICE
    * PRICEDFEATURE
    *
    * LSEO
    * SEOID
    * COUNTRYLIST
    * PRCINDC
    * ZEROPRICE
    *
    * LSEOBUNDLE
    * SEOID
    * COUNTRYLIST
    *
    * WWSEOPRODSTRUCT
    * CONFQTY
    *
    * LSEOPRODSTRUCT
    * CONFQTY
    *
    * LSEOBUNDLELSEO
    * LSEOQTY
    *
    * AVAIL
    * AVAILTYPE only if change to or from Planned Availability (146)
    * 	Note that this is a FLAG
    */
    protected Hashtable getStringRep(EntityList list, Hashtable attrOfInterest) {
        addDebug(NEWLINE+"getStringRep: entered for "+list.getProfile().getValOn());
        Hashtable listTbl = new Hashtable();
        EntityGroup eg = null;
        String attrlist[] = null;

        // check structure for relators not restricted to priced features
        for (int i=0; i<INDEPENDENT_OF_PRICEDFC.length; i++){
			eg =list.getEntityGroup(INDEPENDENT_OF_PRICEDFC[i]);
			attrlist = (String[])attrOfInterest.get(eg.getEntityType());
			for (int e=0; e<eg.getEntityItemCount(); e++)
			{
				//StringBuffer sb = new StringBuffer();
				EntityItem theItem = eg.getEntityItem(e);
				String str = epimsAbr.generateString(theItem, attrlist);
				addDebug("getStringRep: put "+theItem.getKey()+" "+str);
				listTbl.put(theItem.getKey(),str);
			}
		}

		// check model thru modelavail
        attrlist = (String[])attrOfInterest.get("MODEL");
		Vector mdlVct = PokUtils.getAllLinkedEntities(list.getEntityGroup("AVAIL") , "MODELAVAIL", "MODEL");
		addDebug("getStringRep: Model thru modelavail mdlVct "+mdlVct.size());
		for (int entityCount = 0; entityCount < mdlVct.size(); entityCount++) {
			EntityItem theItem = (EntityItem) mdlVct.elementAt(entityCount);
			String str = epimsAbr.generateString(theItem, attrlist);
			addDebug("getStringRep: put "+theItem.getKey()+" "+str);
			listTbl.put(theItem.getKey(),str);
        }

        // only look at structure tied to priced features
        eg =list.getEntityGroup("FEATURE");
        for (int e=0; e<eg.getEntityItemCount(); e++)
        {
            EntityItem featItem = eg.getEntityItem(e);
            String str = null;
            String priced = epimsAbr.getAttributeFlagEnabledValue(featItem, "PRICEDFEATURE");
            addDebug(featItem.getKey()+" PRICEDFEATURE: "+priced);
            if ("100".equals(priced)){
				for (int i=0; i<PRICED_FCREL.length; i++){
					// check the oofavail, lseoprodstruct or wwseoprodstruct to this feature
					Vector psVct = getAllLinkedEntities(featItem, PRICED_FCREL[i]);
					if (psVct.size()>0){
						// only look at FEATUREs thru these relators
						if (!listTbl.containsKey(featItem.getKey())){
							attrlist = (String[])attrOfInterest.get(eg.getEntityType());
							str = epimsAbr.generateString(featItem, attrlist);
							addDebug("getStringRep: put "+featItem.getKey()+" "+str);
							listTbl.put(featItem.getKey(),str);
						}
					}else{
						// skip all those features from MODELAVAIL->MODEL->PRODSTRUCT->FEATURE
						addDebug("getStringRep: "+featItem.getKey()+" was not found thru "+PRICED_FCREL[i]);
					}
					for (int p=0; p<psVct.size(); p++){
						EntityItem psitem = (EntityItem)psVct.elementAt(p);
						attrlist = (String[])attrOfInterest.get(psitem.getEntityType());
						str = epimsAbr.generateString(psitem, attrlist);
						addDebug("getStringRep: put "+psitem.getKey()+" "+str);
						listTbl.put(psitem.getKey(),str);
					}
					psVct.clear();
				}
            }
        }

        return listTbl;
    }

    /********************************************************************************
    * Find entities of the destination type linked to the EntityItems in the source
    *
    * @param entityItem EntityItem
    * @param destType   String EntityType to match
    * @returns Vector of EntityItems
    */
    private Vector getAllLinkedEntities(EntityItem entityItem, String destType)
    {
        // find entities thru '"PRODSTRUCT"' relators
        Vector destVct = new Vector(1);
        for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
        {
            EANEntity entityLink = entityItem.getDownLink(ui);
            if (entityLink.getEntityType().equals("PRODSTRUCT"))
            {
                // check for destination entity as a uplink for wwseops or lseops
                for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
                // check for destination entity as a dnlink for oofavail
                for (int i=0; i<entityLink.getDownLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getDownLink(i);
                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
                        destVct.addElement(entity); }
                }
            }
        }

        return destVct;
    }

    /**********************************
    * get the name of the VE to use
    */
    protected String getVeName() { return "WWPRTANNVE1";}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.5";
    }
}
