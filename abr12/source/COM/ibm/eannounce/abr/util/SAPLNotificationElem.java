// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for EPIMS abrs.
*/
// $Log: SAPLNotificationElem.java,v $
// Revision 1.3  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2007/10/17 12:57:14  wendy
// Use date from profile for better performance
//
// Revision 1.1  2007/10/09 20:56:30  wendy
// Init for GX EPIMS outbound ABRs
//
//

public class SAPLNotificationElem extends SAPLElem
{
   // private static final String STATUS_FINAL = "0020";

    /**********************************************************************************
    * Constructor for Enterprise value elements
    *
    *2  <NotificationTime>(Timestamp of the notification)</NotificationTime>
    *@param nname String with name of node to be created
    */
    public SAPLNotificationElem(String nname)
    {
        super(nname,null,null,false);
    }

    /**********************************************************************************
    * Create a node for this element and add to the parent and any children this node has
    *
    *@param dbCurrent Database
    *@param list EntityList
    *@param document Document needed to create nodes
    *@param parent Element node to add this node too
    *@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent,EntityList list, Document document, Element parent,
        StringBuffer debugSb)
    throws
        COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        java.io.IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        // profile will be set to last final DTS, use it from there now
        String value = list.getProfile().getValOn();
        //getNotificationTime(dbCurrent,list,debugSb);
        // use value from profile
        elem.appendChild(document.createTextNode(value));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,list, document,elem,debugSb);
        }
    }

    /**********************************************************************************
    * get last time STATUS attribute went final.
    *
    *
    private String getNotificationTime(Database dbCurrent,EntityList list,StringBuffer debugSb)
    throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    {
		String dts="";

        EntityItem rootEntity = list.getParentEntityGroup().getEntityItem(0);
		EANAttribute att = rootEntity.getAttribute("STATUS");
		if (att != null) {
			AttributeChangeHistoryGroup achg = new AttributeChangeHistoryGroup(dbCurrent,
				list.getProfile(), att);
			if (achg.getChangeHistoryItemCount()>0){
				for (int i=achg.getChangeHistoryItemCount()-1; i>=0; i--)
				{
					AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)achg.getChangeHistoryItem(i);
					debugSb.append("getNofificationTime "+rootEntity.getKey()+" isActive: "+
						achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
						achi.getChangeDate()+" flagcode: "+achi.getFlagCode()+NEWLINE);
					if (STATUS_FINAL.equals(achi.getFlagCode())){
						dts = achi.getChangeDate();
						break;
					}
				}
			} // has history items
			else{
				debugSb.append("getNofificationTime "+rootEntity.getKey()+" has no chghistory for STATUS");
			}
		}// status attr !=null
		else{
			debugSb.append("getNofificationTime "+rootEntity.getKey()+" has null STATUS");
		}

		return dts;
    }*/
}
