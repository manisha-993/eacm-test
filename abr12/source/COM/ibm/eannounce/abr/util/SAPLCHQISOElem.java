// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

import java.io.*;

import org.w3c.dom.*;

/**********************************************************************************
*  Class used to hold info and structure to be generated for the xml feed
* for SAPLABRSTATUS abrs
* this class will map the nlsid to CHQISONLSIDMAP and fill in node for it
*
* For each Read NLSID of the Profile that has a 'Description' in a matching NLSID, create an
* instance of the Description. To obtain the 'DescriptionLanguage', use the NLSID to match
* CHQISONLSIDMAP.CHQNLSID and then use CHQISONLSID.CHQISOLANG from the same instance as the
* 'DescriptionLanguage'.
*/
// $Log: SAPLCHQISOElem.java,v $
// Revision 1.4  2008/02/19 17:18:25  wendy
// Cleanup RSA warnings
//
// Revision 1.3  2007/05/04 17:31:39  wendy
// Only generate tabs for nls section if values exist in that nls
//
// Revision 1.2  2007/04/20 14:58:33  wendy
// RQ0417075638 updates
//
// Revision 1.1  2007/04/02 17:38:17  wendy
// Support classes for SAPL xml generation
//

public class SAPLCHQISOElem extends SAPLElem
{
/*
	CHQISONLSIDMAP	40	CHQIBMCTRYLANG
	CHQISONLSIDMAP	50	CHQIBMCTRYLANGDESC
	CHQISONLSIDMAP	60	CHQISOLANG
	CHQISONLSIDMAP	70	CHQISOLANG2
	CHQISONLSIDMAP	80	CHQISOLANG3B
	CHQISONLSIDMAP	90	CHQISOLANGDESC
	CHQISONLSIDMAP	20	CHQNLSID
	CHQISONLSIDMAP	30	CHQNLSIDDESC
	CHQISONLSIDMAP	900	ID
	CHQISONLSIDMAP	10	NAME
*/
    /**********************************************************************************
    * Constructor for CHQISONLSIDMAP NLSID elements
    *
    *Description Language   4   <DescriptionLanguage>   NLSID ==> CHQISONLSIDMAP.CHQNLSID : CHQISONLSID.CHQISOLANG
    *
    *@param nname String with name of node to be created
    */
    public SAPLCHQISOElem(String nname)
    {
        super(nname,null,null,false);
    }

    /**********************************************************************************
    * Create a node for this element add to the parent and any children this node has
    *
	*@param dbCurrent Database
	*@param list EntityList
	*@param document Document needed to create nodes
	*@param parent Element node to add this node too
	*@param debugSb StringBuffer for debug output
    */
    public void addElements(Database dbCurrent, EntityList list, Document document, Element parent,
        StringBuffer debugSb)
    throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        //NLSItem nlsitem = list.getProfile().getReadLanguage();
        //String nlsid = ""+nlsitem.getNLSID();
        // do a search action for CHQISONLSIDMAP.CHQNLSID using nlsid
        String CHQISOLANG = getCHQISO(dbCurrent, list.getProfile());

        // create elem using CHQISOLANG for text node
        Element elem = (Element) document.createElement(nodeName);
        addXMLAttrs(elem);
        elem.appendChild(document.createTextNode(CHQISOLANG));
        parent.appendChild(elem);

        // add any children (this node doesn't have any but leave here for consistency)
        for (int c=0; c<childVct.size(); c++){
            SAPLElem childElem = (SAPLElem)childVct.elementAt(c);
            childElem.addElements(dbCurrent,list, document,elem,debugSb);
        }
    }

	/**********************************************************************************
	* This should return false because it isnt part of a nls sensitive attribute
	*
	*@param list EntityList
	*@param debugSb StringBuffer for debug output
	*/
	protected boolean hasNodeValueForNLS(EntityList list, StringBuffer debugSb)
	{
		boolean hasvalue=false;
		return hasvalue;
	}

    /*************************************************************************************
    * Find the CHQISONLSIDMAP with CHQNLSID matching NLSID.  Return CHQISONLSIDMAP.CHQISOLANG
    *
    *@param dbCurrent       Database object
    *@param profile         Profile object
    *
    *@return String
    *@throws COM.ibm.eannounce.objects.EANBusinessRuleException
    *@throws java.sql.SQLException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
    *@throws java.rmi.RemoteException
    *@throws IOException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    */
    private String getCHQISO(Database dbCurrent, Profile profile)
        throws COM.ibm.eannounce.objects.EANBusinessRuleException,
        java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
        COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        java.rmi.RemoteException,
        IOException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        String actionName="SRDCHQISONLSIDMAP";
        NLSItem nlsitem = profile.getReadLanguage();
        String nlsid = ""+nlsitem.getNLSID();
        String isoCode="No CHQISONLSIDMAP match found for "+nlsitem+" ("+nlsid+")";
        RowSelectableTable searchTable;
        EntityList list;
        EntityGroup eg;

        // find any entity ids with this type that match the partnumber using a dynamic search
        SearchActionItem sai = new SearchActionItem(null, dbCurrent, profile, actionName);
        sai.setCheckLimit(false);  // allow more than 2500

        searchTable = sai.getDynaSearchTable(dbCurrent);
        if (searchTable==null){
            throw new IOException("Error using "+actionName+" search action.  No searchtable returned.");
        }

        String keyStr = "CHQISONLSIDMAP:CHQNLSID";
        int row = searchTable.getRowIndex(keyStr);
        if (row < 0) {
            row = searchTable.getRowIndex(keyStr + ":C");
        }
        if (row < 0) {
            row = searchTable.getRowIndex(keyStr + ":R");
        }
        if (row != -1) {
            searchTable.put(row, 1, nlsid);
        }else{
            throw new IOException("Error can't find "+keyStr+" in searchtable.");
        }

        searchTable.commit(dbCurrent);

        list = sai.executeAction(dbCurrent,profile);

        eg = list.getEntityGroup("CHQISONLSIDMAP");
        // group will be null if no matches are found
        if (eg!=null&&eg.getEntityItemCount() >0)
        {
			// find right one.. 1 and 13 are returned for nlsid=1
			for(int i=0; i<eg.getEntityItemCount(); i++){
				EntityItem chqItem = eg.getEntityItem(i);
				if (nlsid.equals(PokUtils.getAttributeValue(chqItem, "CHQNLSID","","",false))){
					//CHQISOLANG is not a nav attr.. so must get it now
					eg = new EntityGroup(null, dbCurrent, profile, chqItem.getEntityType(), "Edit", false);
					chqItem = new EntityItem(eg, profile, dbCurrent, chqItem.getEntityType(), chqItem.getEntityID());

					isoCode = PokUtils.getAttributeValue(chqItem, "CHQISOLANG","",CHEAT,false);
					break;
				}
			}
        }
        try{
            list.dereference();
        }catch(Exception e){
            // get nullptr sometimes.. ignore it
            System.out.println(e);
        }

        return isoCode;
    }
}



