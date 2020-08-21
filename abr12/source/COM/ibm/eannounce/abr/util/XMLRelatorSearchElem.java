// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.util;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;

import org.w3c.dom.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.ibm.transform.oim.eacm.diff.*;
import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
 * Class used to hold info and structure to be generated for the xml feed for
 * abrs. search must be used to find values for these nodes
 */
// $Log: XMLRelatorSearchElem.java,v $
// Revision 1.3 2015/01/26 15:53:39 wangyul
// fix the issue PR24222 -- SPF ADS abr string buffer
//
// Revision 1.2 2013/06/27 12:52:16 guobin
// Fixed CQ-227442 BH - reduce number of FC transaction records required, was
// approved by BH W1 CCB via BH W1 CQ-152329
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

public class XMLRelatorSearchElem extends XMLSearchElem {
	private String upNodeName;

	/**********************************************************************************
	 * Constructor for search value elements
	 *
	 * @param downnname
	 *            String with name of first node to be created- for model
	 * @param upnname
	 *            String with name of second node to be created- for fc
	 * @param srchAct
	 *            String with search action name
	 * @param stype
	 *            String with entitytype to find
	 */
	public XMLRelatorSearchElem(String downnname, String upnname, String srchAct, String stype) {
		super(downnname, srchAct, stype, true);
		upNodeName = upnname;
	}

	/**********************************************************************************
	 * Create a node for this element and add to the parent and any children
	 * this node has
	 *
	 * @param dbCurrent
	 *            Database
	 * @param list
	 *            EntityList
	 * @param document
	 *            Document needed to create nodes
	 * @param parent
	 *            Element node to add this node too
	 * @param debugSb
	 *            StringBuffer for debug output
	 */
	public void addElements(Database dbCurrent, EntityList list, Document document, Element parent,
			EntityItem parentItem, StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException, java.rmi.RemoteException, java.io.IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);
		// search to find entity needed for entityid
		String value = CHEAT;
		String upvalue = CHEAT;
		boolean emptymod = false;
		String machtype = null;
		String mod = null;
		String code = null;
		String fsql = "select entityid as FEATUREID from opicm.text where attributecode = 'FEATURECODE' and  ATTRIBUTEVALUE ='?' and effto>current timestamp and valto>current timestamp with ur";
		String psql = "select r.entity1id as FEATUREID,r.entity2id as MODELID from opicm.flag mf inner join opicm.text mt "
				+ "on mt.entityid = mf.entityid and mf.entitytype = mt.entitytype and mf.ATTRIBUTECODE='MACHTYPEATR' and mt.ATTRIBUTECODE='MODELATR'"
				+ "inner join opicm.relator r on r.entitytype='PRODSTRUCT'  AND r.entity2id = mf.entityid "
				+ "inner join opicm.text ff on ff.ATTRIBUTECODE='FEATURECODE'  and ff.entityid=r.entity1id "
				+ "where   mf.ATTRIBUTEVALUE='?'  AND mt.ATTRIBUTEVALUE='?' and ff.ATTRIBUTEVALUE ='?'  and mf.effto>current timestamp and mf.valto>current timestamp and mt.effto>current timestamp and mt.valto>current timestamp and r.effto>current timestamp and r.valto>current timestamp and ff.effto>current timestamp and ff.valto>current timestamp with ur";
		
		Connection connection = dbCurrent.getPDHConnection();
		PreparedStatement statement =  null;
		// do speical check, if rootentity is FEATURE Transaction and
		// FromModel/ToModel is empty ,then Modelentityid = empty and
		// Featureentityid using Featurecode search.
		if ("FCTRANSACTION".equals(parentItem.getEntityType()) && "FROMMODELENTITYID".equals(nodeName)) {
			String frommodel = PokUtils.getAttributeValue(parentItem, "FROMMODEL", ", ", CHEAT, false);
			if (CHEAT.equals(frommodel)) {
				emptymod = true;
				// MODELENITYID is empty, FEATUREENTITYID has same value ,
				// according to FEATURECODE to search.

				statement = connection.prepareStatement(fsql);
				 code = PokUtils.getAttributeValue(parentItem, "FROMFEATURECODE", ", ", CHEAT, false);
				 statement.setString(1, code);  
			}
			else {
				 machtype = PokUtils.getAttributeValue(parentItem, "FROMMACHTYPE", ", ", CHEAT, false);
				 mod = PokUtils.getAttributeValue(parentItem, "FROMMODEL", ", ", CHEAT, false);
				 code = PokUtils.getAttributeValue(parentItem, "FROMFEATURECODE", ", ", CHEAT, false);
			}
		}
		if ("FCTRANSACTION".equals(parentItem.getEntityType()) && "TOMODELENTITYID".equals(nodeName)) {
			String tomodel = PokUtils.getAttributeValue(parentItem, "TOMODEL", ", ", CHEAT, false);
			if (CHEAT.equals(tomodel)) {
				// MODELENITYID is empty, FEATUREENTITYID has same value ,
				// according to FEATURECODE to search.
				emptymod = true;
				statement = connection.prepareStatement(fsql);
				 code = PokUtils.getAttributeValue(parentItem, "TOFEATURECODE", ", ", CHEAT, false);
				 statement.setString(1, code);  
			}else {
				 machtype = PokUtils.getAttributeValue(parentItem, "TOMACHTYPE", ", ", CHEAT, false);
				 mod = PokUtils.getAttributeValue(parentItem, "TOMODEL", ", ", CHEAT, false);
				 code = PokUtils.getAttributeValue(parentItem, "TOFEATURECODE", ", ", CHEAT, false);
			}
		}
		if (!emptymod) {
			statement = connection.prepareStatement(psql);
			
			 statement.setString(1, machtype);  
			 statement.setString(2, mod);  
			 statement.setString(3, code); 
		}

		 ResultSet resultSet= statement.executeQuery();
		 while (resultSet.next()) {
			 try {
				 upvalue = resultSet.getString("FEATUREID");
				 
			} catch (Exception e) {
				// TODO: handle exception
			}
			 try {
				 value= resultSet.getString("MODELID");
				 
			} catch (Exception e) {
				// TODO: handle exception
			}
			 
			
			break;
		}
		elem.appendChild(document.createTextNode(value));
		parent.appendChild(elem);

		elem = (Element) document.createElement(upNodeName);
		elem.appendChild(document.createTextNode(upvalue));
		parent.appendChild(elem);

		// add any children
		for (int c = 0; c < childVct.size(); c++) {
			XMLElem childElem = (XMLElem) childVct.elementAt(c);
			childElem.addElements(dbCurrent, list, document, elem, parentItem, debugSb);
		}
	}

	/**********************************************************************************
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
	public void addElements(Database dbCurrent, Hashtable table, Document document, Element parent,
			DiffEntity parentItem, StringBuffer debugSb) throws COM.ibm.eannounce.objects.EANBusinessRuleException,
			java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareRequestException, java.rmi.RemoteException, java.io.IOException,
			COM.ibm.opicmpdh.middleware.MiddlewareException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException {
		Element elem = (Element) document.createElement(nodeName);
		addXMLAttrs(elem);
		// search to find entity needed for entityid
		String value = CHEAT;
		String upvalue = CHEAT;
		boolean emptymod = false;
		EntityItem item = parentItem.getCurrentEntityItem();
		if (parentItem.isDeleted()) {
			item = parentItem.getPriorEntityItem();
		}

		try {
			// do speical check, if rootentity is FEATURE Transaction and
			// FromModel/ToModel is empty ,then Modelentityid = empty and
			// Featureentityid using Featurecode search.
			if ("FCTRANSACTION".equals(parentItem.getEntityType()) && "FROMMODELENTITYID".equals(nodeName)) {
				String frommodel = PokUtils.getAttributeValue(item, "FROMMODEL", ", ", CHEAT, false);
				if (CHEAT.equals(frommodel)) {
					emptymod = true;
					// MODELENITYID is empty, FEATUREENTITYID has same value ,
					// according to FEATURECODE to search.

					EntityItem[] feaarry = SearchFeature(item, dbCurrent, "FROMFEATURECODE", debugSb);
					if (feaarry != null && feaarry.length > 0) {
						EntityItem feaitem = feaarry[0];
						upvalue = "" + feaitem.getEntityID();
					}

				}
			}
			if ("FCTRANSACTION".equals(parentItem.getEntityType()) && "TOMODELENTITYID".equals(nodeName)) {
				String tomodel = PokUtils.getAttributeValue(item, "TOMODEL", ", ", CHEAT, false);
				if (CHEAT.equals(tomodel)) {
					// MODELENITYID is empty, FEATUREENTITYID has same value ,
					// according to FEATURECODE to search.
					emptymod = true;
					EntityItem[] feaarry = SearchFeature(item, dbCurrent, "TOFEATURECODE", debugSb);
					if (feaarry != null && feaarry.length > 0) {
						EntityItem feaitem = feaarry[0];
						upvalue = "" + feaitem.getEntityID();
					}

				}
			}
			if (!emptymod) {
				EntityItem[] aei = doSearch(dbCurrent, item, debugSb);
				if (aei != null && aei.length > 0) {
					EntityItem psitem = aei[0];
					EntityItem dnitem = (EntityItem) psitem.getDownLink(0);
					value = "" + dnitem.getEntityID();
					EntityItem upitem = (EntityItem) psitem.getUpLink(0);
					upvalue = "" + upitem.getEntityID();
				}
			}
		} catch (COM.ibm.eannounce.objects.SBRException sbre) {
			throw new MiddlewareException(sbre.toString());
		}

		elem.appendChild(document.createTextNode(value));
		parent.appendChild(elem);

		elem = (Element) document.createElement(upNodeName);
		elem.appendChild(document.createTextNode(upvalue));
		parent.appendChild(elem);

		// add any children
		for (int c = 0; c < childVct.size(); c++) {
			XMLElem childElem = (XMLElem) childVct.elementAt(c);
			childElem.addElements(dbCurrent, table, document, elem, parentItem, debugSb);
		}
	}

	/**
	 * do search setup entity
	 * 
	 * @param item,
	 *            db, debugsb
	 * @return EntityItem[]
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws MiddlewareBusinessRuleException
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareShutdownInProgressException
	 * @throws RemoteException
	 * @throws EANBusinessRuleException
	 * @throws IOException
	 */
	private EntityItem[] SearchFeature(EntityItem item, Database dbCurrent, String attrcode, StringBuffer debugSb)
			throws MiddlewareBusinessRuleException, MiddlewareRequestException, SQLException, MiddlewareException,
			MiddlewareShutdownInProgressException, RemoteException, EANBusinessRuleException, IOException {
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		// get attribute of EXTXMLFEED Entitys
		// search for the EXTXMLFEED by XMLSETUPTYPE='Production' and
		///////////////////////////////////////////////////////////////////////////////////////////////////// XMLENTITYTYPE='Root
		///////////////////////////////////////////////////////////////////////////////////////////////////// Entity
		///////////////////////////////////////////////////////////////////////////////////////////////////// Type'
		/////////////////////////////////////////////////////////////////////////////////////////////////////
		EntityItem[] FeatureArray = null;
		Profile m_prof = item.getProfile();

		String searchAction = "SRDFEATURE";
		String srchType = "FEATURE";
		Vector attrVct = new Vector();
		Vector valVct = new Vector();

		attrVct.add("FEATURECODE");
		String feacode = PokUtils.getAttributeValue(item, attrcode, ", ", CHEAT, false);
		ABRUtil.append(debugSb, "searchFeature from/to featurecode=" + feacode + "\n");
		valVct.add(feacode);
		try {
			FeatureArray = ABRUtil.doSearch(dbCurrent, m_prof, searchAction, srchType, false, attrVct, valVct, debugSb);

		} catch (Exception e) {
			ABRUtil.append(debugSb, "doSearch error=" + e.getMessage() + "\n");
			e.printStackTrace();
		}
		return FeatureArray;
		////////////////////////////////////////////////////////////////////////////////////////////////////////
		// search end
		/////////////////////////////////////////////////////////////////////////////////////////////////////////
	}
}
