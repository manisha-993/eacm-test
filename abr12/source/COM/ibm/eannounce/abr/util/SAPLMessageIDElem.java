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
* for SAPLABRSTATUS abrs need to generate a unique id
*/
// $Log: SAPLMessageIDElem.java,v $
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLMessageIDElem extends SAPLElem
{
	private static final int IDLEN=10;
    /**********************************************************************************
    * Constructor for MessageID element
    *
    *4  <ebi:MessageID> EA00000000  <ebi:MessageID>
    *
    */
    public SAPLMessageIDElem()
    {
        super("ebi:MessageID",null,null,false);
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
        String value = getIncrement(dbCurrent,list, debugSb);
        elem.appendChild(document.createTextNode(value));
        parent.appendChild(elem);

        // add any children
        for (int c=0; c<childVct.size(); c++){
            SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,list, document,elem,debugSb);
        }
    }
    /**********************************************************************************
    * The <ebi:MessageID> is of the form EAnnnnnnnn where nnnnnnnn is an integer
    * incremented by one for every message sent. There are several options that development can
    * choose from to support this:
    * 1.	A special EntityType and use the 'highid' table in the PDH==>used this option
    * 2.	A single instance of a control EntityType and AttributeCode
    * 3.	Development proposed and accepted alternative
    *
	*@param dbCurrent Database
	*@param list EntityList
	*@param debugSb StringBuffer for debug output
    */
    private String getIncrement(Database dbCurrent,EntityList list, StringBuffer debugSb)
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
		int id = dbCurrent.getNextEntityID(list.getProfile(), "CHQSAPLHIGHID");

		StringBuffer sb = new StringBuffer(""+id);
		while(sb.length()<IDLEN){
			sb.insert(0,"0");
		}
		// make sure you dont exceed len, not likely.. but just in case
		if (sb.length()>IDLEN){
			int excess = sb.length()-IDLEN;
			String right = sb.substring(excess);
			debugSb.append("SAPLMessageIDElem:getIncrement: Warning: id len exceeded max["+
				IDLEN+"],"+sb+" was truncated."+NEWLINE);
			sb = new StringBuffer(right);
		}

		return "EA"+sb.toString();
	}
}
