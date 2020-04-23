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
* EPIMSMODELABR class generates xml for LSEO
*
* From "SG FS ABR ePIMS Notification 20071212.doc"
* C.	MODEL
*
* IF MODEL.COFCAT = 102 (Service) then
* 	Send a Notification Message for MODEL
* Else
* 	Nothing to do
* End
*
* 1.	ePIMS XML Notification Message for MODEL
*
* <?xml version="1.0" encoding="UTF-8"?>
* <ChangeNotification>
* <EntityType id="%1">%2</EntityType>
* <Action>%3</Action>
* <Timestamp>%4</Timestamp>
* </ChangeNotification>
*
* Where
* 	%1 is the entity id for the MODEL
* 	%2 is the text string: MODEL
* 	%3 is the text string: EPIMSMODELSVC
* 	%4 is the VALFROM for the ABR was set to Queued
*
*/
// EPIMSMODELABR.java,v
// Revision 1.3  2008/04/08 12:27:19  wendy
// MN 35084789 - support resend of lost notification messages.
// and MN 35178533 - ePIMS lost some geography data
// "SG FS ABR ePIMS Notification 20080407.doc"
//
// Revision 1.2  2008/01/30 19:39:15  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/12/13 19:58:30  wendy
// 30b version support "SG FS ABR ePIMS Notification 20071212.doc"
//
public class EPIMSMODELABR extends EPIMSABRBase
{
    private static final Vector FIRSTFINAL_XMLMAP_VCT;
    private static final String SERVICE = "102";

    static {
        FIRSTFINAL_XMLMAP_VCT = new Vector();  // set of elements
        SAPLElem topElem = new SAPLElem("ChangeNotification");

        FIRSTFINAL_XMLMAP_VCT.addElement(topElem);
         // level2
        topElem.addChild(new SAPLIdElem("EntityType","MODEL","id"));
        topElem.addChild(new SAPLFixedElem("Action","EPIMSMODELSVC"));
        topElem.addChild(new SAPLNotificationElem("Timestamp"));
    }

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
        return FIRSTFINAL_XMLMAP_VCT;
    }

    /**********************************
    * execute the derived class - 30b version
    *
    * This ABR is queued whenever a MODEL is moved to Final by the Data Quality ABR.
    */
    protected void execute() throws Exception
    {
        // make sure the STATUS is Final
        EntityItem rootEntity = epimsAbr.getEntityList().getParentEntityGroup().getEntityItem(0);

        String statusFlag = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "STATUS");
        addDebug("execute: "+rootEntity.getKey()+" STATUS: "+
            PokUtils.getAttributeValue(rootEntity, "STATUS",", ", "", false)+" ["+statusFlag+"] ");

        if (!STATUS_FINAL.equals(statusFlag)){
            addError("STATUS was not 'Final'");
            return;
        }

		String modelCOFCAT = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "COFCAT");
		if(modelCOFCAT == null)	{
			modelCOFCAT = "";
		}
		addDebug(rootEntity.getKey()+" COFCAT: "+modelCOFCAT);

		if(SERVICE.equals(modelCOFCAT)) {
			//  Notify ePIMS: MODEL
			notifyAndSetStatus(null);
		}
    }

	/*********************************
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

		String modelCOFCAT = epimsAbr.getAttributeFlagEnabledValue(rootEntity, "COFCAT");
		if(modelCOFCAT == null)	{
			modelCOFCAT = "";
		}
		addDebug(rootEntity.getKey()+" COFCAT: "+modelCOFCAT);

		if(SERVICE.equals(modelCOFCAT)) {
			//  Notify ePIMS: MODEL
			notifyAndSetStatus(null);
		}
	}

    /**********************************
    * get the name of the VE to use
    */
    protected String getVeName() { return "dummy";}

    /***********************************************
    *  Get the version
    *
    *@return java.lang.String
    */
    public String getVersion()
    {
        return "1.3";
    }
}
