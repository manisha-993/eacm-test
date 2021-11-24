// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2021  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg.adsxmlbh1;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;

import java.util.*;
import java.sql.*;
import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;

import org.w3c.dom.*;

import com.ibm.transform.oim.eacm.util.PokUtils;

/**********************************************************************************
 * This is an extract and feed of Meta Data with the source being
 * MetaDescription table.
 */

public class SVCMODIERPABRSTATUSABR extends XMLMQAdapter {

	private String CACEHSQL = "select XMLMESSAGE from cache.XMLIDLCACHE where XMLENTITYTYPE = 'SVCMOD' and XMLENTITYID = ?  and XMLCACHEVALIDTO > current timestamp with ur";


	



	/**********************************
	 * create xml and write to queue
	 */
	public void processThis(ADSABRSTATUS abr, Profile profileT1, Profile profileT2, EntityItem rootEntity)
			throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException, ParserConfigurationException,
			java.rmi.RemoteException, COM.ibm.eannounce.objects.EANBusinessRuleException,
			COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException, IOException,
			javax.xml.transform.TransformerException, MissingResourceException{
		// step1 get the attribute of rootEntity(?XML Product Price Setup?
		// (XMLPRODPRICESETUP) entity)
		// the same value with the value of ADSDTS from rootEntity
		// because in the ADSABRSTAUTS, t1DTS = PokUtils.getAttributeValue(rootEntity,
		// "ADSDTS",",", "", false);
		String t1DTS = profileT1.getValOn();

		String t2DTS = profileT2.getValOn();
		abr.addDebug("SVCMODIERPABRSTATUSABR process t1DTS=" + t1DTS);
		abr.addDebug("SVCMODIERPABRSTATUSABR process t2DTS=" + t2DTS);
		Connection connection = abr.getDB().getODS2Connection();
		PreparedStatement statement =  connection.prepareStatement(CACEHSQL);
		statement.setInt(1, rootEntity.getEntityID());
		ResultSet resultSet = statement.executeQuery();
		String xml = null;
		while (resultSet.next()) {
			xml = resultSet.getString("XMLMESSAGE");
		}
		abr.addOutput(xml);
	

	}

	

	/**
	 * convert null to ""
	 * 
	 * @param fromValue
	 * @return
	 */

	private String convertValue(String fromValue) {
		return fromValue == null ? "" : fromValue.trim();
	}

	


	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getVersion() {
		return "1.1";
	}

	
	

	private boolean isValidCond(String attr) {
		return attr != null && attr.trim().length() > 0;
	}




	@Override
	public String getMQCID() {
		// TODO Auto-generated method stub
		return null;
	}
}
