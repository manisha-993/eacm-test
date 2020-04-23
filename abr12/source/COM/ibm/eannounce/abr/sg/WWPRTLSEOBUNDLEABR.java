// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;
import COM.ibm.opicmpdh.middleware.*;

import java.util.*;

/**********************************************************************************
* WWPRTLSEOBUNDLEABR class generates xml for LSEOBUNDLE
*
* From "SG FS ABR WWPRT Notification 20080326.doc"
* This ABR is only queued whenever a PRODSTRUCT is a RPQ and the STATUS = Final.
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
// WWPRTLSEOBUNDLEABR.java,v
// Revision 1.6  2009/08/09 01:31:40  wendy
// Dont need to hang onto properties file after load attr list
//
// Revision 1.5  2009/08/07 18:29:12  wendy
// Move AttributesOfInterest to properties file
//
// Revision 1.4  2008/04/08 12:27:44  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR WWPRT Notification 20080407.doc"
//
// Revision 1.3  2008/01/30 19:39:17  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2007/12/03 16:58:03  wendy
// Save key with attribute values
//
// Revision 1.1  2007/11/30 22:14:21  wendy
// Init for WWPRT ABRs
//
public class WWPRTLSEOBUNDLEABR extends EPIMSABRBase
{
    private static final Vector FIRSTFINAL_XMLMAP_VCT;
    private static final Vector CHGFINAL_XMLMAP_VCT;
    private static final Hashtable ATTR_OF_INTEREST_TBL;
    private static final String PROPERTIES_FNAME = "WWPRTLSEOBUNDLEABR_AOI.properties";

    static {
    	// read list of attributes of interest from a properties file
		ATTR_OF_INTEREST_TBL = new Hashtable();
		loadAttrOfInterest(PROPERTIES_FNAME,ATTR_OF_INTEREST_TBL);
		
        /*ATTR_OF_INTEREST_TBL.put("FEATURE",new String[]{"FEATURECODE","FCTYPE","ZEROPRICE","PRICEDFEATURE"});
        ATTR_OF_INTEREST_TBL.put("LSEO",new String[]{"SEOID","COUNTRYLIST"});
        ATTR_OF_INTEREST_TBL.put("WWSEOPRODSTRUCT",new String[]{"CONFQTY"});
        ATTR_OF_INTEREST_TBL.put("LSEOPRODSTRUCT",new String[]{"CONFQTY"});*/

        FIRSTFINAL_XMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("WWPRTNotification");

        FIRSTFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","LSEOBUNDLE"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("Id","LSEOBUNDLE","SEOID",true));
        topElem.addChild(new SAPLFixedElem("Change","no"));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));

        CHGFINAL_XMLMAP_VCT = new Vector();  // set of elements
        topElem = new SAPLElem("WWPRTNotification");

        CHGFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","LSEOBUNDLE"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLElem("Id","LSEOBUNDLE","SEOID",true));
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
    * The logical business data model supports custom LSEOBUNDLESs that are not announced.
    * These are indicated as Special Bid.
    * LSEOBUNDLE.SPECBID = Yes (11458)
    *
    * A.	Resend
    *
    * If SYSFEEDRESEND = 'Yes' (Yes) then
    * 	If ValueOf( LSEOBUNDLE.SPECBID) = 11457 (No) then
    * 		Set SYSFEEDRESEND = 'No' (No)
    * 		ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'was queued to resend data; however, it is not a Special Bid'
    * 		Done
    * 	End If
    * 	If STATUS = 'Final' (0020) then
    * 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
    * 		Done
    * 	Else
    * 		ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'was queued to resend data; however, it is not Final'
    * 	End If
    * 	Set SYSFEEDRESEND = 'No' (No)
    * 	Done
    * Else
    * 	Continue with the next paragraph in this spec.
    * End If
    *
    * B.	First Time
    *
    * This notification occurs whenever the status of a LSEOBUNDLE is changed to Final the first time.
    * LSEOBUNDLE.STATUS = Final (0020)
    *
    * C.	Subsequent Times
    *
    * The notification occurs when there is a change in information within the LSEOBUNDLE structure that
    * is of interest to WWPRT and Status was changed to Final other than the first time.
    *
    * Let T1 = DTS of this Final and T2 = DTS of the prior Final. Then a change of interest is a change
    * in attribute value listed or in structure.
    *
    * The VE defining the structure of interest follows:
    *
    * Lev	Entity1	RelType	Relator	Entity2	Dir
    * 1	LSEOBUNDLE	Relator	LSEOBUNDLELSEO	LSEO	D
    * 2	LSEO	Relator	LSEOPRODSTRUCT	PRODSTRUCT	D
    * 3	FEATURE	Relator	PRODSTRUCT	MODEL	U
    * 2	WWSEO	Relator	WWSEOLSEO	LSEO	U
    * 3	WWSEO	Relator	WWSEOPRODSTRUCT	PRODSTRUCT	D
    * 4	FEATURE	Relator	PRODSTRUCT	MODEL	U
    *
    * The changes of interest within this structure are filtered by Feature if applicable. Features
    * (FEATURE) are considered only if they are priced (PRICEDFEATURE = Yes (100). Items that add/remove
    * Features are indicated via an asterisk. The changes of interest within this structure are:
    *
    * 	New (added) or removed (deleted) relators
    * LSEOBUNDLELSEO
    * LSEOPRODSTRUCT *
    * WWSEOPRODSTRUCT *
    *
    * 	The changes in data of interest within this structure are:
    *
    * FEATURE
    * 	FEATURECODE
    * 	FCTYPE
    * 	ZEROPRICE
    * 	PRICEDFEATURE
    *
    * LSEO
    * 	SEOID
    * 	COUNTRYLIST
    *
    * WWSEOPRODSTRUCT
    * 	CONFQTY
    *
    * LSEOPRODSTRUCT
    * 	CONFQTY
    *
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

		if (SYSFEEDRESEND_YES.equals(sysfeedFlag)){
			resendSystemFeed(rootEntity, statusFlag);
			return;
		}

        if (!STATUS_FINAL.equals(statusFlag)){
			addError("ERROR_NOT_FINAL", null);
            return;
        }

		if (epimsAbr.isFirstFinal()){
            addDebug("Only one transition to Final found, must be first.");
            //  Notify
            notifyAndSetStatus(null);
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
    * 	If ValueOf( LSEOBUNDLE.SPECBID) = 11457 (No) then
    * 		Set SYSFEEDRESEND = 'No' (No)
    * 		ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'was queued to resend data; however, it is not a Special Bid'
    * 		Done
    * 	End If
    * 	If STATUS = 'Final' (0020) then
    * 		Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
    * 		Done
    * 	Else
    * 		ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'was queued to resend data; however, it is not Final'
    * 	End If
    * 	Set SYSFEEDRESEND = 'No' (No)
    * 	Done
    * Else
	* 	Continue with the next paragraph in this spec.
	* End If
    */
	protected void handleResend(EntityItem rootEntity, String statusFlag) throws
	java.sql.SQLException, MiddlewareException, javax.xml.parsers.ParserConfigurationException,
	javax.xml.transform.TransformerException, COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, java.io.IOException
	{
		//If ValueOf( LSEOBUNDLE.SPECBID) = 11457 (No) then
		//ErrorMessage LD(LSEOBUNDLE) NDN(LSEOBUNDLE) 'was queued to resend data; however, it is not a Special Bid'
		//NOT_SPECBID = was queued to resend data; however, it is not a Special Bid.
		String specbid = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "SPECBID");
		addDebug(rootEntity.getKey()+" specbid: "+specbid);

        if (!SPECBID_YES.equals(specbid)){
			addError("NOT_SPECBID", null);
        	return;
		}

		if (!STATUS_FINAL.equals(statusFlag)){ //If STATUS != 'Final' (0020)
			//RESEND_NOT_FINAL = was queued to resend data; however, it is not Final.
			addError("RESEND_NOT_FINAL", null);
			return;
		}

		//	Notify
		notifyAndSetStatus(null);
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
    * LSEOBUNDLELSEO
    * LSEOPRODSTRUCT *
    * WWSEOPRODSTRUCT *
    *
    * The changes in data of interest within this structure are:
    *
    * FEATURE
    *   FEATURECODE
    *   FCTYPE
    *   ZEROPRICE
    *   PRICEDFEATURE
    *
    * LSEO
    *   SEOID
    *   COUNTRYLIST
    *
    * WWSEOPRODSTRUCT
    *   CONFQTY
    *
    * LSEOPRODSTRUCT
    *   CONFQTY
    *
    */
    protected Hashtable getStringRep(EntityList list, Hashtable attrOfInterest) {
        addDebug(NEWLINE+"getStringRep: entered for "+list.getProfile().getValOn());
        Hashtable listTbl = new Hashtable();
        // check structure for LSEOBUNDLELSEO
        EntityGroup eg =list.getEntityGroup("LSEOBUNDLELSEO");
        for (int e=0; e<eg.getEntityItemCount(); e++)
        {
            EntityItem theItem = eg.getEntityItem(e);
			addDebug("getStringRep: put "+theItem.getKey());
			listTbl.put(theItem.getKey(),theItem.getKey());
        }

        eg =list.getEntityGroup("LSEO");
        String attrlist[] = (String[])attrOfInterest.get(eg.getEntityType());
        for (int e=0; e<eg.getEntityItemCount(); e++)
        {
            EntityItem theItem = eg.getEntityItem(e);
			String str = epimsAbr.generateString(theItem, attrlist);
			addDebug("getStringRep: put "+theItem.getKey()+" "+str);
			listTbl.put(theItem.getKey(),str);
        }
        // only look at structure tied to priced features
        eg =list.getEntityGroup("FEATURE");
        for (int e=0; e<eg.getEntityItemCount(); e++)
        {
            EntityItem featItem = eg.getEntityItem(e);
            String priced = epimsAbr.getAttributeFlagEnabledValue(featItem, "PRICEDFEATURE");
            addDebug(featItem.getKey()+" PRICEDFEATURE: "+priced);
            if ("100".equals(priced)){
                attrlist = (String[])attrOfInterest.get(eg.getEntityType());
				String str = epimsAbr.generateString(featItem, attrlist);
                addDebug("getStringRep: put "+featItem.getKey()+" "+str);
                listTbl.put(featItem.getKey(),str);

                // check the lseoprodstruct or wwseoprodstruct to this feature
                Vector psVct = getAllLinkedEntities(featItem, "LSEOPRODSTRUCT");
                for (int p=0; p<psVct.size(); p++){
                    EntityItem psitem = (EntityItem)psVct.elementAt(p);
                    attrlist = (String[])attrOfInterest.get(psitem.getEntityType());
					str = epimsAbr.generateString(psitem, attrlist);
                    addDebug("getStringRep: put "+psitem.getKey()+" "+str);
                    listTbl.put(psitem.getKey(),str);
                }
                psVct.clear();
                psVct = getAllLinkedEntities(featItem, "WWSEOPRODSTRUCT");
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
                // check for destination entity as a uplink
                for (int i=0; i<entityLink.getUpLinkCount(); i++)
                {
                    EANEntity entity = entityLink.getUpLink(i);
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
    protected String getVeName() { return "WWPRTLSEOBDLVE1";}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.6";
    }
}
