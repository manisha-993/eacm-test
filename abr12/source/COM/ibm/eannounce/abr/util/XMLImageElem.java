// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import java.sql.ResultSet;
import java.util.Hashtable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

import com.ibm.transform.oim.eacm.diff.DiffEntity;

/*******************************************************************************
 * Class used to hold info and structure to be generated for the xml feed for
 * abrs. Checks for deleted or updated entity
 */
// $Log: XMLImageElem.java,v $
// Revision 1.5  2015/01/26 15:53:39  wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.4  2011/08/22 11:11:44  guobin
// A non compliant code issues for Image abr
//
// Revision 1.3  2011/02/11 08:51:57  guobin
// update the connect and the query sql
//
// Revision 1.2  2011/01/18 10:12:35  guobin
// change the string name
//
// Revision 1.1  2011/01/18 05:09:05  guobin
// create image content for IMAGE
//
// Revision 1.1 2008/04/17 19:37:53 wendy
// Init for
// - CQ00003539-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
// - CQ00005096-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add
// Category MM and Images
// - CQ00005046-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC -
// Support CRAD in BHC
// - CQ00005045-WI - BHC 3.0 Support - Feed of ZIPSRSS product info to BHC -
// Upgrade/Conversion Support
// - CQ00006862-WI - BHC 3.0 Support - Support for Services Data UI
//
//
public class XMLImageElem extends XMLElem {
	/***************************************************************************
	 * Constructor for ACTIVITY elements
	 * 
	 * <ACTIVITY> </ACTIVITY> 2 MODEL Activity "Delete":"Update"
	 * 
	 * @param nname
	 *            String with name of node to be created
	 */
	public XMLImageElem() {
		super(null);
	}

	/***************************************************************************
	 * Create a node for this element and add to the parent and any children
	 * this node has
	 * 
	 * @param dbCurrent
	 *            Database
	 * @param table
	 *            Hashtable of Vectors of DiffEntity
	 * @param document
	 *            Document needed to create nodes
	 * @param parent
	 *            Element node to add this node too
	 * @param parentItem
	 *            DiffEntity - parent to use if path is specified in
	 *            XMLGroupElem, item to use otherwise
	 * @param debugSb
	 *            StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, Hashtable table,
			Document document, Element parent, DiffEntity parentItem,
			StringBuffer debugSb)
			throws COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException,
			COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
			java.rmi.RemoteException, java.io.IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		ResultSet rs = null;
		String strBASE64Encoder = "";
		ReturnStatus returnStatus = new ReturnStatus( -1);
		try {			
			rs = dbCurrent.callGBL7553(returnStatus, "blob", parentItem.getCurrentEntityItem().getProfile().getEnterprise(), parentItem.getCurrentEntityItem().getEntityType(), parentItem.getCurrentEntityItem().getEntityID(), " ", 1);
			while (rs.next()) {
				byte[] byteBlobValue = rs.getBytes("AttributeValue");
				BASE64Encoder encoder = new BASE64Encoder();
				strBASE64Encoder = encoder.encode(byteBlobValue);
			}
			
		} catch (Exception e) {
			ABRUtil.append(debugSb,"XMLImageElem addElements()error: "+e.getMessage());
			e.printStackTrace();			
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				dbCurrent.commit();
				dbCurrent.freeStatement();
				dbCurrent.isPending();
			} catch (Exception e) {
				ABRUtil.append(debugSb,"XMLImageElem addElements(), unable to close. "+e);
			}
		}
		createNodeSet(document, parent, strBASE64Encoder, debugSb);
	}
	
	/**
	 * create nodeset of IMAGE CONTENTS
	 * 
	 * @param document
	 * @param parent
	 * @param debugSb
	 */
	private void createNodeSet(Document document, Element parent,
			String CONTENTS, StringBuffer debugSb) {
		Element child = (Element) document.createElement("IMAGECONTENTS"); // create
																			// COUNTRYAUDIENCEELEMENT
		child.appendChild(document.createTextNode("" + CONTENTS));
		parent.appendChild(child);
	}	

}
