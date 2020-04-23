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
* WWPRTPRODSTRUCTABR class generates xml for PRODSTRUCT RPQ
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
// WWPRTPRODSTRUCTABR.java,v
// Revision 1.5  2009/08/09 01:31:41  wendy
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
// Revision 1.2  2008/01/30 19:39:15  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/11/30 22:18:06  wendy
// Init for WWPRT ABRs
//
public class WWPRTPRODSTRUCTABR extends EPIMSABRBase
{
    private static final Vector FIRSTFINAL_XMLMAP_VCT;
    private static final Vector CHGFINAL_XMLMAP_VCT;
    private static final Hashtable ATTR_OF_INTEREST_TBL;
    private static final String PROPERTIES_FNAME = "WWPRTPRODSTRUCTABR_AOI.properties";
    static {
    	// read list of attributes of interest from a properties file
		ATTR_OF_INTEREST_TBL = new Hashtable();
		loadAttrOfInterest(PROPERTIES_FNAME,ATTR_OF_INTEREST_TBL);
		
        /*ATTR_OF_INTEREST_TBL.put("FEATURE",new String[]{"COUNTRYLIST","FCTYPE","FIRSTANNDATE","MKTGNAME","ZEROPRICE"});
        ATTR_OF_INTEREST_TBL.put("PRODSTRUCT",new String[]{"INSTALL","ORDERCODE"});*/

        FIRSTFINAL_XMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("WWPRTNotification");

        FIRSTFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","RPQ"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLIdElem("Id"));
        topElem.addChild(new SAPLFixedElem("Change","no"));
        topElem.addChild(new SAPLNotificationElem("NotificationTime"));
        topElem.addChild(new SAPLEnterpriseElem("Enterprise"));

        CHGFINAL_XMLMAP_VCT = new Vector();  // set of elements
        topElem = new SAPLElem("WWPRTNotification");

        CHGFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLFixedElem("EntityType","RPQ"));
        topElem.addChild(new SAPLIdElem("EntityId"));
        topElem.addChild(new SAPLIdElem("Id"));
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
    * This ABR is only queued whenever a PRODSTRUCT is a RPQ and the STATUS = Final.
    *
	* A.	Resend
	*
	* If SYSFEEDRESEND = 'Yes' (Yes) then
	* 	If ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED) | 402 (Placeholder) then
	* 		If STATUS = 'Final' (0020) then
	* 			Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
	* 			Done
	* 		Else
	* 			ErrorMessage NDN(PRODSTRUCT) 'a RPQ was queued to resend data; however, it is not Final'
	* 		End If
	* 		Set SYSFEEDRESEND = 'No' (No)
	* 		Done
	* 	Else
	* 		Set SYSFEEDRESEND = 'No' (No)
	* 		ErrorMessage NDN(PRODSTRUCT) 'a TMF was queued to resend data; however, it is not a RPQ'
	* 		Done
	* 	End If
	* Else
	* 	Continue with the next paragraph in this spec.
	* End If
	*
	* B.	First Time
    *
    * This notification occurs whenever the status of a PRODSTRUCT is changed to Final the first time.
    * PRODSTRUCT.STATUS = Final (0020)
    *
    * C.	Subsequent Times
    *
    * The notification occurs when there is a change in information within the PRODSTRUCT structure
    * that is of interest to WWPRT and Status was changed to Final other than the first time.
    *
    * Let T1 = DTS of this Final and T2 = DTS of the prior Final. Then a change of interest is a change
    * in attribute value listed.
    *
    * FEATURE.COUNTRYLIST
    * FEATURE.FCTYPE
    * FEATURE.FIRSTANNDATE
    * FEATURE.MKTGNAME
    * FEATURE.ZEROPRICE
    * PRODSTRUCT.INSTALL
    * PRODSTRUCT.ORDERCODE
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
            //if Change of Interest to wwprt then
            if (changeOfInterest()) {
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
	* 	If ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED) | 402 (Placeholder) then
	* 		If STATUS = 'Final' (0020) then
	* 			Send notification with <NotificationTime> = DTS (current VALFROM of STATUS)
	* 			Done
	* 		Else
	* 			ErrorMessage NDN(PRODSTRUCT) 'a RPQ was queued to resend data; however, it is not Final'
	* 		End If
	* 		Set SYSFEEDRESEND = 'No' (No)
	* 		Done
	* 	Else
	* 		Set SYSFEEDRESEND = 'No' (No)
	* 		ErrorMessage NDN(PRODSTRUCT) 'a TMF was queued to resend data; however, it is not a RPQ'
	* 		Done
	* 	End If
	* Else
	* 	Continue with the next paragraph in this spec.
	* End If
	*
    */
	protected void handleResend(EntityItem rootEntity, String statusFlag) throws
	java.sql.SQLException, MiddlewareException, javax.xml.parsers.ParserConfigurationException,
	javax.xml.transform.TransformerException, COM.ibm.eannounce.objects.EANBusinessRuleException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, java.io.IOException
	{
		if (!STATUS_FINAL.equals(statusFlag)){ //If STATUS != 'Final' (0020)
			//RPQ_NOT_FINAL = a RPQ was queued to resend data; however, it is not Final.
			addError("RPQ_NOT_FINAL", null);
			return;
		}

		//If ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED) | 402 (Placeholder) {}else
		//ErrorMessage NDN(PRODSTRUCT) 'a TMF was queued to resend data; however, it is not a RPQ'
		//NOT_RPQ = a TMF was queued to resend data; however, it is not a RPQ.
        if (!checkRPQ()){
			addError("NOT_RPQ", null);
        	return;
		}

		//	Notify
		notifyAndSetStatus(null);
	}

	/**********************************
	* 	If ValueOf(PRODSTRUCT-u: FEATURE.FCTYPE) = 120 (RPQ ILISTED) | 130 (RPQ-PLISTED) | 402 (Placeholder) then
	* 	Else
	* 		ErrorMessage NDN(PRODSTRUCT) 'a RPQ was queued to resend data; however, it is not Final'
	*/
	private boolean checkRPQ() throws java.sql.SQLException,MiddlewareException
	{
		String lastdts = epimsAbr.getLastFinalDTS(); // really queued DTS now

		boolean isRPQ = false;
		// set lastfinal date in profile
		Profile lastprofile = epimsAbr.getProfile().getNewInstance(epimsAbr.getDB());
		lastprofile.setValOnEffOn(lastdts, lastdts);

		String VEname = getVeName();
		// create VE for lastfinal time
		EntityList lastFinalList = epimsAbr.getDB().getEntityList(lastprofile,
			new ExtractActionItem(null, epimsAbr.getDB(),lastprofile,VEname),
			new EntityItem[] { new EntityItem(null, lastprofile, epimsAbr.getEntityType(), epimsAbr.getEntityID()) });

		 // debug display list of groups
		addDebug("checkRPQ dts: "+lastdts+" extract: "+VEname+NEWLINE +
			PokUtils.outputList(lastFinalList));

		//  Notify if is RPQ
		EntityGroup eg = lastFinalList.getEntityGroup("FEATURE");
		EntityItem eiFEATURE = eg.getEntityItem(0);
		String strFCTYPE = epimsAbr.getAttributeFlagEnabledValue(eiFEATURE, "FCTYPE");
		addDebug(eiFEATURE.getKey()+" FCTYPE "+strFCTYPE);
		if (("120".equals(strFCTYPE) || "130".equals(strFCTYPE) || "402".equals(strFCTYPE))) {
			addDebug(eiFEATURE.getKey()+" is RPQ or placeholder");
			isRPQ = true;
		}

		lastFinalList.dereference();

		return isRPQ;
	}

    /**********************************
    * attributes to check for any changes
    */
	protected Hashtable getAttrOfInterest() { return ATTR_OF_INTEREST_TBL;}

    /**********************************
    * get hashtable with entitylist converted to strings.  key is entityitem key
    * value is the concatenated list of all attributes of interest.
    * Let T1 = DTS of this Final and T2 = DTS of the prior Final. Then a change of interest is a change
    * in attribute value listed.
    *
    * FEATURE.COUNTRYLIST
    * FEATURE.FCTYPE
    * FEATURE.FIRSTANNDATE
    * FEATURE.MKTGNAME
    * FEATURE.ZEROPRICE
    * PRODSTRUCT.INSTALL
    * PRODSTRUCT.ORDERCODE
    *
    */
    protected Hashtable getStringRep(EntityList list, Hashtable attrOfInterest) {
        addDebug(NEWLINE+"getStringRep: entered for "+list.getProfile().getValOn());
        Hashtable listTbl = new Hashtable();
        EntityGroup eg =list.getParentEntityGroup();
        String attrlist[] = (String[])attrOfInterest.get(eg.getEntityType());
        for (int e=0; e<eg.getEntityItemCount(); e++) // parent PRODSTRUCT here
        {
            EntityItem theItem = eg.getEntityItem(e);
			String str = epimsAbr.generateString(theItem, attrlist);
			addDebug("getStringRep: put "+theItem.getKey()+" "+str);
			listTbl.put(theItem.getKey(),str);
        }

        eg =list.getEntityGroup("FEATURE");
        attrlist = (String[])attrOfInterest.get(eg.getEntityType());
        for (int e=0; e<eg.getEntityItemCount(); e++)
        {
            EntityItem theItem = eg.getEntityItem(e);
			String str = epimsAbr.generateString(theItem, attrlist);
			addDebug("getStringRep: put "+theItem.getKey()+" "+str);
			listTbl.put(theItem.getKey(),str);
        }

        return listTbl;
    }

    /**********************************
    * get the name of the VE to use
    */
    protected String getVeName() { return "EXRPT3FM";} // share with chglog, only need feat-ps-mdl

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
