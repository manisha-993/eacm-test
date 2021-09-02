// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
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
// $Log: ADSPRICEABR.java,v $
// Revision 1.27  2019/05/27 15:17:52  xujianbo
// Story 1987505-Add chunk function for EACM price - Development fix the build issue
//
// Revision 1.26  2019/05/27 12:46:15  xujianbo
// Story 1987505-Add chunk function for EACM price - Development
//
// Revision 1.25 2013/12/11 08:15:00 guobin
// xsd validation for generalarea, reconcile, wwcompat and price XML
//
// Revision 1.24 2013/08/26 12:34:26 wangyulo
// RTC WI 986855 with title RCQ00255719: Provide GTS prices in the EACM feed to
// e-Pricer - DIVISION FILTER FOR PRODUCT FEED
//
// Revision 1.23 2013/06/04 20:11:14 praveen
// Fix TMF (Feature) query to pull SVCMOD prices
//
// Revision 1.22 2013/04/22 14:00:19 wangyulo
// fix the out of memory problem by the limit the size of prices with the
// parameter ADSABRSTATUS_PRICE_ENTITY_LIMIT
//
// Revision 1.21 2013/04/15 17:07:18 praveen
// Fix SWTMF query
//
// Revision 1.20 2013/02/26 16:04:44 wangyulo
// fix the defect 899006 that Can not filter by offering type for WWPRT outbound
//
// Revision 1.19 2013/01/17 03:15:35 praveen
// Tune EACM price queries
//
// Revision 1.18 2013/01/16 16:10:20 wangyulo
// fix the defect 874393 for the performance issue for the EACM format price
//
// Revision 1.17 2013/01/15 14:04:21 wangyulo
// update the code for the ADSPUTO problem
//
// Revision 1.16 2013/01/14 16:24:35 wangyulo
// Build request for the defect 871116 and remove the valto check for the price
// entity table
//
// Revision 1.15 2012/08/31 16:02:32 wangyulo
// update for the RCQ00212274-WI BH W1 R1 - H5 support - DCUT
//
// Revision 1.14 2012/07/30 12:54:45 wangyulo
// uncomment the references to ADSABRSTATUS.USERXML_OFF_LOG in ADSPRICEABR
//
// Revision 1.13 2012/07/24 20:57:06 wendy
// Removed references to ADSABRSTATUS.USERXML_OFF_LOG for now, ADSABRSTATUS
// could not go to production yet
//
// Revision 1.12 2012/07/17 16:57:16 wangyulo
// fix the outbound price for the end tag of the wwprt type
//
// Revision 1.11 2012/07/13 13:20:32 wangyulo
// Build request for the performance of the outbound price
//
// Revision 1.10 2012/07/03 07:53:00 wangyulo
// Fixed defect 751817--SIT testing with RDX - Performance issue, fix the column
// and order by in the select sql for wwprt type
//
// Revision 1.9 2012/06/08 07:32:05 wangyulo
// Build Request for the wwprt pricexml of the outbound price - defect 737778
//
// Revision 1.8 2012/05/21 12:22:56 wangyulo
// add the limit of the size of price record in one MQ message
//
// Revision 1.7 2012/04/26 13:30:14 wangyulo
// Defect 710663 a error found for WWPRT outbound, should use the short
// description of ADSPRICETYPE
//
// Revision 1.6 2012/04/24 13:04:51 wangyulo
// fixed the defect 697075 - chg code to limit the number of price records in
// each MQ msg
//
// Revision 1.5 2012/03/27 07:53:51 wangyulo
// add NONEPDHDOMAIN for Setting for "No PDHDOMAIN filter" for Product Price
// Outbound
//
// Revision 1.4 2012/03/15 06:38:56 wangyulo
// Change for the Outbound price base on the BH FS Product Price Outbound XML
// 20120301.doc
// 1. Add PUB_TO and PDHDOMAIN filter for the WWPRT outbound price
// 2. update the filter for the attribute ACTION of the price(the ACTION value
// should be 'I')
//
// Revision 1.2 2011/12/14 02:24:23 guobin
// Update the Version V Mod M for the ADSABR
//
// Revision 1.1 2011/11/03 16:09:56 guobin
// Add for the Outbound Price
//
// Init for
// updates for spec "SG FS Product Price Outbound XML 20110608.doc"
//
public class ADSPRICEABR extends XMLMQAdapter {
	private static final String NONEPDHDOMAIN;
	private static final String ALLPRICETYPE;
	private static final String NONEPRICETYPE;
	private static final String OFFERINGTYPE;
	private String MQCID = "";
	/**
	 * the PRICE_ENTITY_LIMIT is used to limit the size of the price group list
	 * table to avoid the out of memory. the PRICE_MQ_LIMIT is used to limit the
	 * size of price records in one price message(Level 2) that send the MQ. the
	 * PRICE_MESSAGE_COUNT is used to record the count of the price message.
	 */
	private static final int PRICE_ENTITY_LIMIT;
	private static final int PRICE_MQ_LIMIT;
	protected static int PRICE_MESSAGE_COUNT;
	protected static int PRICE_RECORD_COUNT;
	private int max = -1;
	private long ALL_WWPRT_SEND_TIME = 0;
	private static String offeringtypes = "";
	static {
		String entitylimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_PRICE_ENTITY_LIMIT", "50000");
		String pricemqlimit = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_PRICE_MQ_LIMIT", "1000");
		PRICE_ENTITY_LIMIT = Integer.parseInt(entitylimit);
		PRICE_MQ_LIMIT = Integer.parseInt(pricemqlimit);
		NONEPDHDOMAIN = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_NONEPDHDOMAIN", "$None$");
		ALLPRICETYPE = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_ALLPRICETYPE", "$ALL$");
		NONEPRICETYPE = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_NONEPRICETYPE", "$NONE$");
		OFFERINGTYPE = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_OFFERINGTYPE", "SEO,EEE,OSP");

		String offeringtype[] = OFFERINGTYPE.split(",");
		for (int i = 0; i < offeringtype.length; i++) {
			if (i == 0)
				offeringtypes = "'" + offeringtype[i] + "'";
			else
				offeringtypes = offeringtypes + ",'" + offeringtype[i] + "'";
		}

	}

	private boolean isSended = false;

	/**
	 * A price record that is to be feed to a downstream system is one where:
	 * INSERT_TS is later than the value for ADSDTS (aka T1) VALID_TO is greater
	 * than or equal DTS that the ABR started (aka T2) STATUS is ?Active?
	 * (ADSPPABRSTATUS) ENTITYTYPE = ADSOFFTYPE COUNTRY = ADSCOUNTRY
	 * PRICE_XML/pricetype = ADSPRICETYPE ADSOFFCAT see the following table
	 * 
	 * ENTITYTYPE ADSOFFCAT MODEL ValueOf(COFCAT) FEATURE Hardware SWFEATURE
	 * Software FCTRANSACTION Hardware MODELCONVERT Hardware
	 */

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
		abr.addDebug("ADSPRICEABR process t1DTS=" + t1DTS);
		abr.addDebug("ADSPRICEABR process t2DTS=" + t2DTS);
		/**
		 * INIT the price message count for the reconcile
		 */
		PRICE_MESSAGE_COUNT = 0;
		PRICE_RECORD_COUNT = 0;

		/**
		 * ADSPPFORMAT : WWPRT and EACM
		 */
		String ADSPPFORMAT = getDescription(rootEntity, "ADSPPFORMAT", "long");// flag--> long get long description

		if (ADSPPFORMAT != null && "WWPRT".equals(ADSPPFORMAT)) {
			MQCID = "PRICE";
		} else if ("EACM".equals(ADSPPFORMAT)) {
			MQCID = "PRODUCT_PRICE_UPDATE";
		}

		/**
		 * 1. ADSOFTYPE is the EACM entity type used to filter the prices that are to be
		 * fed to the downstream system. If null (i.e. empty ? note that ?NULL? is not
		 * empty), then all are considered MODEL FEATURE SWFEATURE FCTRANSACTION
		 * MODELCONVERT NULL
		 */
		String ADSOFFTYPE = getDescription(rootEntity, "ADSOFFTYPE", "long");
		/**
		 * 2. ADSOFFCAT is the EACM offering category used to filter the prices that are
		 * to be fed to the downstream system. If null, then all are considered. The
		 * values are: Hardware Software Service
		 */
		String ADSOFFCAT = getDescription(rootEntity, "ADSOFFCAT", "long");
		/**
		 * 3. ADSCOUNTRY is the ISO 2 character country code used to filter the prices
		 * that are to be fed to the downstream system. If null, then all are
		 * considered.
		 *
		 */
		String ADSCOUNTRY = getMultiDescription(rootEntity, "ADSCOUNTRY", "long");

		/**
		 * 4. ADSPRICETYPE is the WWPRT 3 character price type used to filter the prices
		 * that are to be fed to the downstream system. If null, then all are
		 * considered.
		 *
		 */
		String ADSPRICETYPE = getMultiDescription(rootEntity, "ADSPRICETYPE", "flag");
		/**
		 * 5. ADSPPABRSTATUS is the attribute used to manage (queue) the Product Price
		 * ABR that generates the XML
		 */
		String ADSPPABRSTATUS = getDescription(rootEntity, "ADSPPABRSTATUS", "long");

		/**
		 * 6. PDHDOMAIN
		 */
		// String ADSPDHDOMAIN = getDescription(rootEntity, "PDHDOMAIN","long");
		Hashtable ADSPDHDOMAIN = getDescriptionTable(rootEntity, "PDHDOMAIN", "long");

		/**
		 * 7. ADSPUBTO need add the meta on the GUI
		 */
		String ADSPUBTO = PokUtils.getAttributeValue(rootEntity, "PUBTO", ",", "", false);// getDescription(rootEntity,
																							// "PUBTO","long");

		/**
		 * 8. ADSXPRICETYPE should only be valid when ADSPRICETYPE = $ALL$ We need the
		 * H5 price types that need to be excluded. They would be allowed values for
		 * ADSXPRICETYPE when ADSPRICETYPE = $ALL$
		 */
		String ADSXPRICETYPE = getMultiDescription(rootEntity, "ADSXPRICETYPE", "flag");

		/**
		 * TODO 9. DIVTEXT
		 */
		String ADSDIVTEXT = PokUtils.getAttributeValue(rootEntity, "DIVTEXT", ",", "", false);

		/**
		 * PRICEIDLMAXMSG
		 */
		String maxMsg = PokUtils.getAttributeValue(rootEntity, "PRICEIDLMAXMSG", ",", "", false);
		if (isValidCond(maxMsg)) {
				max = Integer.parseInt(maxMsg);
				// rptSb.append("<br/>XMLIDLMAXMSG = "+ maxMsg +" <br/>");
				abr.addDebug("XMLIDLMAXMSG = " + maxMsg);
		}

		// step 2 find price according to the attriutevalue
		abr.addDebug("ADSPRICEABR0 ADSPPFORMAT=" + ADSPPFORMAT);
		abr.addDebug("ADSPRICEABR2 ADSOFFTYPE=" + ADSOFFTYPE);
		abr.addDebug("ADSPRICEABR2 ADSOFFCAT=" + ADSOFFCAT);
		abr.addDebug("ADSPRICEABR3 ADSCOUNTRY=" + ADSCOUNTRY);
		abr.addDebug("ADSPRICEABR4 ADSPRICETYPE=" + ADSPRICETYPE);
		abr.addDebug("ADSPRICEABR5 ADSPPABRSTATUS=" + ADSPPABRSTATUS);
		abr.addDebug("ADSPRICEABR6 ADSPDHDOMAIN=" + ADSPDHDOMAIN);
		abr.addDebug("ADSPRICEABR7 ADSPUBTO=" + ADSPUBTO);
		abr.addDebug("ADSPRICEABR8 ADSXPRICETYPE=" + ADSXPRICETYPE);
		abr.addDebug("ADSPRICEABR9 ADSDIVTEXT=" + ADSDIVTEXT);
		abr.addDebug("ADSPRICEABR10 PRICEIDLMAXMSG=" + maxMsg);
		Hashtable priceTable = new Hashtable();

		priceTable = getPriceTable(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSOFFCAT, ADSCOUNTRY, ADSPRICETYPE, rootEntity,
				profileT2, ADSPDHDOMAIN, ADSPUBTO, ADSXPRICETYPE, ADSDIVTEXT);

		abr.addDebug("priceTable.size():" + priceTable.size());
		abr.addDebug("isSended:" + isSended);
		if (priceTable.size() == 0 && isSended == false) {
			// NO_CHANGES_FND=No Changes found for {0}
			abr.addXMLGenMsg("NO_CHANGES_FND", "PRICE");
		} else if (priceTable.size() == 0 && isSended == true) {
			// Do nothing
		} else {
			// 10 WWPRT 20 EACM
			if (ADSPPFORMAT != null && "WWPRT".equals(ADSPPFORMAT)) {
				sendWWPRTXML(abr, profileT2, rootEntity, priceTable);
			} else {
				sendEACMXML(abr, profileT2, rootEntity, priceTable);
			}

			abr.addXMLGenMsg("SUCCESS", "total " + PRICE_MESSAGE_COUNT + " Messsages for the price.");
			abr.addXMLGenMsg("SUCCESS", "total " + PRICE_RECORD_COUNT + " records for the price.");
		}

		if (ADSPPFORMAT != null && "WWPRT".equals(ADSPPFORMAT)) {
			abr.addDebug("Sending WWPRT MQ message total takes time: " + ALL_WWPRT_SEND_TIME + " ms");
		}
	}

	/**
	 * get Price vector
	 * 
	 * @param abr
	 * @param t1DTS
	 * @param t2DTS
	 * @param ADSOFFTYPE
	 * @param ADSOFFCAT
	 * @param ADSCOUNTRY
	 * @param ADSPRICETYPE
	 * @param ADSPPABRSTATUS
	 * @return
	 * @throws java.sql.SQLException
	 * @throws NumberFormatException
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 * @throws MissingResourceException
	 * @throws DOMException
	 */
	private Hashtable getPriceTable(ADSABRSTATUS abr, String ADSPPFORMAT, String t1DTS, String ADSOFFTYPE,
			String ADSOFFCAT, String ADSCOUNTRY, String ADSPRICETYPE, EntityItem rootEntity, Profile profileT2,
			Hashtable ADSPDHDOMAIN, String ADSPUBTO, String ADSXPRICETYPE, String ADSDIVTEXT)
			throws java.sql.SQLException, NumberFormatException, MiddlewareRequestException, MiddlewareException,
			DOMException, MissingResourceException, ParserConfigurationException, TransformerException {
		Hashtable priceTable = new Hashtable();// contain all the price data
		StringBuffer sqlSb = null;
		try {
			// connection = setupConnection();

			if (ADSPPFORMAT != null && !"".equals(ADSPPFORMAT)) {
				if ("WWPRT".equals(ADSPPFORMAT)) {
					sqlSb = new StringBuffer();

					// Story 1987505 Add chunk function for EACM price - Development
					if (maxLimited())
						sqlSb.append(PRICE_WWPRT_SQL_LIMITED);
					else {
						sqlSb.append(PRICE_WWPRT_SQL);
					}
					getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
							ADSXPRICETYPE, sqlSb);

					priceTable = processWWPRTPrice(priceTable, abr, sqlSb.toString(), rootEntity, profileT2);
				} else if ("EACM".equals(ADSPPFORMAT)) {
					/**
					 *
					 * MODEL FEATURE SWFEATURE FCTRANSACTION MODELCONVERT NULL ''
					 */
					// ~ sqlSb.append(PRICE_EACM_SQL);
					if ("MODEL".equals(ADSOFFTYPE)) {
						sqlSb = new StringBuffer();
						sqlSb.append(SQL_MODEL);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, ADSDIVTEXT);

					} else if ("FEATURE".equals(ADSOFFTYPE)) {
						sqlSb = new StringBuffer();
						sqlSb.append(SQL_FEATURE);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, ADSDIVTEXT);

					} else if ("SWFEATURE".equals(ADSOFFTYPE)) {
						sqlSb = new StringBuffer();
						sqlSb.append(SQL_SWFEATURE);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");
					} else if ("FCTRANSACTION".equals(ADSOFFTYPE)) {
						sqlSb = new StringBuffer();
						sqlSb.append(SQL_FCTRANSACTION);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");
					} else if ("MODELCONVERT".equals(ADSOFFTYPE)) {
						sqlSb = new StringBuffer();
						sqlSb.append(SQL_MODELCONVERT);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");
					} else if ("NULL".equals(ADSOFFTYPE)) {
						sqlSb = new StringBuffer();
						sqlSb.append(SQL_LSEO);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");
					} else if ("".equals(ADSOFFTYPE)) {
						sqlSb = new StringBuffer();
						sqlSb.append(SQL_MODEL);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, ADSDIVTEXT);

						sqlSb = new StringBuffer();
						sqlSb.append(SQL_FEATURE);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, ADSDIVTEXT);

						sqlSb = new StringBuffer();
						sqlSb.append(SQL_SWFEATURE);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");

						sqlSb = new StringBuffer();
						sqlSb.append(SQL_MODELCONVERT);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");

						sqlSb = new StringBuffer();
						sqlSb.append(SQL_LSEO);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");

						sqlSb = new StringBuffer();
						sqlSb.append(SQL_ESW);
						getQuerySql(abr, ADSPPFORMAT, t1DTS, ADSOFFTYPE, ADSCOUNTRY, ADSPRICETYPE, profileT2, ADSPUBTO,
								ADSXPRICETYPE, sqlSb);
						priceTable = processEACMPrice(priceTable, abr, sqlSb.toString(), ADSPPFORMAT, ADSOFFCAT,
								rootEntity, profileT2, ADSPDHDOMAIN, "");

					}
				} else {
					return new Hashtable();// other value of ADSPPFORMAT
				}
			} else {
				return new Hashtable();// no value of ADSPPFORMAT
			}

		} finally {

			// when use the connection = abr.getDB().getODSConnection(),
			// please do not close the connection
			// closeConnection(connection);
		}
		return priceTable;
	}

	/**
	 * @param abr
	 * @param sql
	 * @param rootEntity
	 * @param profileT2
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws DOMException
	 * @throws MissingResourceException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */
	private Hashtable processWWPRTPrice(Hashtable priceTable, ADSABRSTATUS abr, String sql, EntityItem rootEntity,
			Profile profileT2) throws MiddlewareException, SQLException, DOMException, MissingResourceException,
			ParserConfigurationException, TransformerException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		connection = abr.getDB().getODSConnection();
		statement = connection.prepareStatement(sql);
		abr.addDebug("processWWPRTPrice sql:" + sql);
		result = statement.executeQuery();

		// work for the count limit
		String skey = "";
		boolean needClear = false;
		/**
		 * performance issue move the definition of the Variables out of the cycle
		 */
		String wwprtPRODUCTENTITYID = "";
		String pricexml = "";
		Vector temp = null;
		int result_size = 0;
		int limit_size = 0;
		while (result.next()) {
			/**
			 * start count limit when priceTable is more than PRICE_ENTITY_LIMIT records and
			 * when the next key is not equal the current key, send the price data and clear
			 * the pricetable, the left price data will send later in the processThis
			 */
			if (result_size >= PRICE_ENTITY_LIMIT) {
				abr.addDebug("ADSPRICEABR WWPRT format clear priceTable result_size=" + result_size + " \r\n");
				needClear = true;
				result_size = 0;
			}
			wwprtPRODUCTENTITYID = convertValue(result.getString("ID"));
			// change for the column PRICEXML
			pricexml = result.getString("PRICEXML");
			abr.addDebug("processWWPRTPrice PRICEXML:" + pricexml);
			skey = wwprtPRODUCTENTITYID.trim();
			temp = (Vector) priceTable.get(skey);
			if (temp != null && temp.size() > 0 && !needClear) {
				temp.add(pricexml);
				priceTable.put(skey, temp);
			} else {
				needClear = clearPriceTable(abr, "WWPRT", rootEntity, profileT2, priceTable, needClear);
				temp = new Vector();
				temp.add(pricexml);
				priceTable.put(skey, temp);
			}
			result_size++;
			if (maxLimited()) {
				limit_size++;
				if (limit_size >= max) {
					String dstDate = result.getString("INSERT_TS");
					abr.setT2DTS(dstDate);
					abr.addDebug("The count of msg has reached the max msg definition in the setup entity: " + max);
					abr.addDebug("Set the last run date as : " + dstDate);
					return priceTable;
				}

			}

		}
		if (result != null) {
			result.close();
			result = null;
		}
		if (statement != null) {
			statement.close();
			statement = null;
		}
		return priceTable;

	}

	/**
	 *
	 * * ADSABRSTATUS abr, String ADSPPFORMAT, String t1DTS, String ADSOFFTYPE,
	 * String ADSOFFCAT, String ADSCOUNTRY, String ADSPRICETYPE,EntityItem
	 * rootEntity,Profile profileT2,Hashtable ADSPDHDOMAIN, String ADSPUBTO, String
	 * ADSXPRICETYPE
	 * 
	 * @param abr
	 * @param sql
	 * @param ADSOFFCAT
	 * @param rootEntity
	 * @param profileT2
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws DOMException
	 * @throws MissingResourceException
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 */

	private Hashtable processEACMPrice(Hashtable priceTable, ADSABRSTATUS abr, String sql, String ADSPPFORMAT,
			String ADSOFFCAT, EntityItem rootEntity, Profile profileT2, Hashtable ADSPDHDOMAIN, String ADSDIVTEXT)
			throws MiddlewareException, SQLException, DOMException, MissingResourceException,
			ParserConfigurationException, TransformerException {

		abr.addDebug("SQL:" + sql);
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		connection = abr.getDB().getODSConnection();
		statement = connection.prepareStatement(sql);
		result = statement.executeQuery();
		int count = 0;
		// work for the count limit
		String skey = "";
		boolean needClear = false;
		/**
		 * performance issue move the definition of the Variables out of the cycle
		 */
		// String wwprtPRODUCTENTITYID = "";
		String PRODUCTOFFERINGTYPE = "";
		String OFFERING_TYPE = "";
		String MACHTYPE = "";
		String MODEL = "";
		String FEATURECODE = "";
		String FROMMACHTYPE = "";
		String FROMMODEL = "";
		String FROMFEATURECODE = "";
		String SEOID = "";
		String OFFERING = "";
		String STARTDATE = "";

		String CURRENCY = "";
		String CABLETYPE = "";
		String CABLEID = "";
		String RELEASETS = "";
		String PRICEVALUE = "";

		String PRICEPOINTTYPE = "";
		String PRICEPOINTVALUE = "";
		String COUNTRY = "";
		String PRICETYPE = "";
		String ONSHORE = "";

		String ENDDATE = "";
		String PRICEVALUEUSD = "";
		String FACTOR = "";
		String ACTIVITY = "";
		String DIV = "";

		// new add start
		String PRODUCTENTITYTYPE = "";
		int iPRODUCTENTITYID = 0;
		String PRODUCTENTITYID = "";
		String PRICEPOINTENTITYTYPE = "";
		String PRICEPOINTENTITYID = "";
		// new add end

		String PDHDOMAIN = "";
		String PRODUCTCATEGORY = "";
		String MKTGNAME = "";
		String INVNAME = "";
		boolean isESW = false;

		PriceInfo priceInfo = null;
		Vector temp = null;
		int result_size = 0;
		int limit_size = 0;

		// TODO
		Hashtable DIVTable = getDIVTEXTTable(ADSDIVTEXT);
		while (result.next()) {

			/**
			 * start count limit when priceTable is more than PRICE_ENTITY_LIMIT records and
			 * when the next key is not equal the current key, send the price data and clear
			 * the pricetable, the left price data will send later in the processThis
			 */
			if (result_size >= PRICE_ENTITY_LIMIT) {
				abr.addDebug("ADSPRICEABR EACM format clear priceTable result_size=" + result_size + " \r\n");
				needClear = true;
				result_size = 0;
			}
			// wwprtPRODUCTENTITYID = convertValue(result.getString("ID"));

			PRODUCTOFFERINGTYPE = convertValue(result.getString("PRODUCTOFFERINGTYPE"));
			OFFERING_TYPE = convertValue(result.getString("OFFERING_TYPE"));
			MACHTYPE = convertValue(result.getString("MACHTYPEATR"));
			MODEL = convertValue(result.getString("MODELATR"));
			FEATURECODE = convertValue(result.getString("FEATURECODE"));
			FROMMACHTYPE = convertValue(result.getString("FROM_MACHTYPEATR"));// FROM_MACHTYPEATR
			FROMMODEL = convertValue(result.getString("FROM_MODELATR"));
			FROMFEATURECODE = convertValue(result.getString("FROM_FEATURECODE"));// FROM_FEATURECODE
			SEOID = convertValue(result.getString("PARTNUM"));// SEOID -->PARTNUM
			OFFERING = convertValue(result.getString("OFFERING"));
			STARTDATE = convertValue(result.getString("START_DATE"));

			CURRENCY = convertValue(result.getString("CURRENCY"));
			CABLETYPE = convertValue(result.getString("CABLETYPE"));// add the column to db
			CABLEID = convertValue(result.getString("CABLEID"));// add the column to db
			RELEASETS = convertValue(result.getString("RELEASE_TS"));
			PRICEVALUE = convertValue(result.getString("PRICE_VALUE"));
			abr.addDebug("PRICETYPE:" + PRICETYPE);
			abr.addDebug("result.getString(\"PRICE_VALUE\"):" + result.getString("PRICE_VALUE"));
			PRICEPOINTTYPE = convertValue(result.getString("PRICE_POINT_TYPE"));
			PRICEPOINTVALUE = convertValue(result.getString("PRICE_POINT_VALUE"));
			COUNTRY = convertValue(result.getString("COUNTRY"));
			PRICETYPE = convertValue(result.getString("PRICE_TYPE"));
			ONSHORE = convertValue(result.getString("ONSHORE"));

			ENDDATE = convertValue(result.getString("END_DATE"));
			PRICEVALUEUSD = convertValue(result.getString("PRICE_VALUE_USD"));
			FACTOR = convertValue(result.getString("FACTOR"));
			ACTIVITY = XMLElem.UPDATE_ACTIVITY;
			if (!"".equals(ADSDIVTEXT)) {
				DIV = convertValue(result.getString("DIV"));
			}

			// new add start
			PRODUCTENTITYTYPE = "";
			iPRODUCTENTITYID = 0;
			PRODUCTENTITYID = "";
			PRICEPOINTENTITYTYPE = "";
			PRICEPOINTENTITYID = "";
			// new add end

			PDHDOMAIN = "";
			PRODUCTCATEGORY = "";
			MKTGNAME = "";
			INVNAME = "";

			count++;

			/**
			 * Primary Key +OFFERING +PRICE_POINT_TYPE +PRICE_POINT_VALUE +PRICE_TYPE
			 * +COUNTRY +ONSHORE +END_DATE
			 */

			PDHDOMAIN = convertValue(result.getString("PDHDOMAIN"));
			PRODUCTCATEGORY = convertValue(result.getString("PRODUCTCATEGORY"));
			MKTGNAME = convertValue(result.getString("MKTGNAME"));
			INVNAME = convertValue(result.getString("INVNAME"));

			// RCQ00206104 WI- EACM - LA prices to support the LA CBS project and WW ESW
			// prices
			isESW = false;
			if ("EEE".equals(OFFERING_TYPE) || "OSP".equals(OFFERING_TYPE)) {
				isESW = true;
			}

			PRODUCTOFFERINGTYPE = convertValue(result.getString("PRODUCTOFFERINGTYPE"));
			PRODUCTENTITYTYPE = convertValue(result.getString("PRODUCTENTITYTYPE"));

			if (isESW) {
				PRODUCTENTITYID = "";
			} else {
				iPRODUCTENTITYID = result.getInt("PRODUCTENTITYID");
				PRODUCTENTITYID = String.valueOf(iPRODUCTENTITYID);
			}

			PRICEPOINTENTITYTYPE = convertValue(result.getString("PRICEPOINTENTITYTYPE"));
			PRICEPOINTENTITYID = convertValue(result.getString("PRICEPOINTENTITYID"));

			// PRODUCTCATEGORY LSEOBUNDLE Derived
			if ("LSEOBUNDLE".equals(PRODUCTCATEGORY)) {
				PRODUCTCATEGORY = getBUNDLETYPE(abr.getDatabase(), rootEntity, iPRODUCTENTITYID, "LSEOBUNDLE");
			}

			// compare with the filter of ADSOFFCAT
			if (ADSOFFCAT != null && !"".equals(ADSOFFCAT)) {
				if (!PRODUCTCATEGORY.equals(ADSOFFCAT)) {
					if (maxLimited()) {
						limit_size++;
						if (limit_size >= max) {
							String dstDate = result.getString("INSERT_TS");
							abr.setT2DTS(dstDate);
							abr.addDebug("The count of msg has reached the max msg definition in the setup entity: " + max);
							abr.addDebug("Set the last run date as : " + dstDate);
							return priceTable;
						}

					}
					continue;
				}
			}

			/**
			 * TODO add div filter
			 */
			if (!"".equals(ADSDIVTEXT)) {
				if (!DIVTable.containsKey(DIV)) {
					if (maxLimited()) {
						limit_size++;
						if (limit_size >= max) {
							String dstDate = result.getString("INSERT_TS");
							abr.setT2DTS(dstDate);
							abr.addDebug("The count of msg has reached the max msg definition in the setup entity: " + max);
							abr.addDebug("Set the last run date as : " + dstDate);
							return priceTable;
						}

					}
					continue;
				}
			}

			/**
			 * add ADSPDHDOMAIN to filter the PDHDOMAIN
			 */
			if (ADSPDHDOMAIN.size() > 0) {
				if (isESW) {
					/**
					 * ESW always has no domain, so not need to check the domain, always true
					 */
				} else if (ADSPDHDOMAIN.size() == 1 && ADSPDHDOMAIN.containsKey(NONEPDHDOMAIN)) {
					/**
					 * always true
					 */
				} else if (PDHDOMAIN == null || "".equals(PDHDOMAIN)) {
					continue;
				} else if (!ADSPDHDOMAIN.containsKey(PDHDOMAIN)) {
					if (maxLimited()) {
						limit_size++;
						if (limit_size >= max) {
							String dstDate = result.getString("INSERT_TS");
							abr.setT2DTS(dstDate);
							abr.addDebug("The count of msg has reached the max msg definition in the setup entity: " + max);
							abr.addDebug("Set the last run date as : " + dstDate);
							return priceTable;
						}

					}
					continue;
				}
			}

			skey = PDHDOMAIN + "|" + PRODUCTCATEGORY + "|" + PRODUCTOFFERINGTYPE + "|" + COUNTRY + "|" + PRICETYPE;
			// abr.addDebug("ADSPRICEABR histroty historyPrice =
			// "+historyPrice.size()+"\r\n");
			abr.addDebug("PRICETYPE:" + PRICETYPE);
			priceInfo = new PriceInfo(PRODUCTOFFERINGTYPE, MACHTYPE, MODEL, FEATURECODE, FROMMACHTYPE, FROMMODEL,
					FROMFEATURECODE, SEOID, OFFERING, STARTDATE, CURRENCY, CABLETYPE, CABLEID, RELEASETS, PRICEVALUE,
					PRICEPOINTTYPE, PRICEPOINTVALUE, COUNTRY, PRICETYPE, ONSHORE, ENDDATE, PRICEVALUEUSD, FACTOR,
					ACTIVITY, PDHDOMAIN, PRODUCTCATEGORY, PRODUCTENTITYTYPE, PRODUCTENTITYID, PRICEPOINTENTITYTYPE,
					PRICEPOINTENTITYID, MKTGNAME, INVNAME, null);

			// abr.addDebug("ADSPRICEABR priceInfo.historyPrice =
			// "+priceInfo.HISTORYPRICE+"\r\n");

			temp = (Vector) priceTable.get(skey);
			if (temp != null && temp.size() > 0 && !needClear) {
				temp.add(priceInfo);
				priceTable.put(skey, temp);
			} else {
				needClear = clearPriceTable(abr, ADSPPFORMAT, rootEntity, profileT2, priceTable, needClear);
				temp = new Vector();
				temp.add(priceInfo);
				priceTable.put(skey, temp);
			}
			result_size++;
			if (maxLimited()) {
				limit_size++;
				if (limit_size >= max) {
					String dstDate = result.getString("INSERT_TS");
					abr.setT2DTS(dstDate);
					abr.addDebug("The count of msg has reached the max msg definition in the setup entity: " + max);
					abr.addDebug("Set the last run date as : " + dstDate);
					return priceTable;
				}

			}
		}
		if (result != null) {
			result.close();
			result = null;
		}
		if (statement != null) {
			statement.close();
			statement = null;
		}

		return priceTable;

	}
	/**
	 * @param abr
	 * @param ADSPPFORMAT
	 * @param t1DTS
	 * @param ADSCOUNTRY
	 * @param ADSPRICETYPE
	 * @param profileT2
	 * @param ADSPUBTO
	 * @param ADSXPRICETYPE
	 * @param sqlSb
	 */
	private void getQuerySql(ADSABRSTATUS abr, String ADSPPFORMAT, String t1DTS, String ADSOFFTYPE, String ADSCOUNTRY,
			String ADSPRICETYPE, Profile profileT2, String ADSPUBTO, String ADSXPRICETYPE, StringBuffer sqlSb){
		// getPriced(abr,t1DTS,t2DTS,ADSOFFTYPE,ADSOFFCAT,ADSCOUNTRY,ADSPRICETYPE,ADSPPABRSTATUS);
		// new change If empty, then all are considered. If not empty, then this value
		// must be <= PRICE.INSERT_TS
		// INSERT_TS is later than the value for ADSDTS (aka T1) and less than T2.
		String t2DTS = profileT2.getValOn();
		abr.addDebug(" ADSPRICEABR t2DTS=" + t2DTS + "\r\n");

		if (t1DTS != null && t1DTS.length() == 10) {
			sqlSb.append(" AND PRICE.INSERT_TS>=concat('").append(t1DTS)
					.append("','-00.00.00.000000') and  PRICE.INSERT_TS<='").append(t2DTS).append("'  \r\n");
		} else {
			sqlSb.append(" AND PRICE.INSERT_TS>='").append(t1DTS).append("' and  PRICE.INSERT_TS<='").append(t2DTS)
					.append("'  \r\n");
		}

		abr.addDebug("ADSPRICEABR INSERT_TS cause is PRICE.INSERT_TS>='" + t1DTS + "' and  PRICE.INSERT_TS<='" + t2DTS
				+ "\r\n");

		if (ADSCOUNTRY != null && !"".equals(ADSCOUNTRY)) {
			if (ADSCOUNTRY.indexOf(",") > -1) {
				sqlSb.append(" AND PRICE.COUNTRY IN(").append(ADSCOUNTRY).append(") \r\n");
			} else {
				sqlSb.append(" AND PRICE.COUNTRY =").append(ADSCOUNTRY).append(" \r\n");
			}

		}
		// Defect 779469 RCQ00212274-WI BH W1 R1 - H5 support - DCUT
		if (ADSPRICETYPE != null && !"".equals(ADSPRICETYPE)) {
			if (ADSPRICETYPE.indexOf(ALLPRICETYPE) > -1) {
				if (ADSXPRICETYPE != null && !"".equals(ADSXPRICETYPE)) {
					if (ADSXPRICETYPE.indexOf(NONEPRICETYPE) == -1) {
						if (ADSXPRICETYPE.indexOf(",") > -1) {
							sqlSb.append(" AND PRICE.PRICE_TYPE NOT IN(").append(ADSXPRICETYPE).append(") \r\n");
						} else {
							sqlSb.append(" AND PRICE.PRICE_TYPE<>").append(ADSXPRICETYPE).append(" \r\n");
						}
					}
				}
			} else {
				if (ADSPRICETYPE.indexOf(",") > -1) {
					sqlSb.append(" AND PRICE.PRICE_TYPE IN(").append(ADSPRICETYPE).append(") \r\n");
				} else {
					sqlSb.append(" AND PRICE.PRICE_TYPE=").append(ADSPRICETYPE).append(" \r\n");
				}
			}

		}
		if ("EACM".equals(ADSPPFORMAT)) {
			/**
			 * add ADSPUBTO to filter the history of price
			 */
			if (ADSPUBTO != null && !"".equals(ADSPUBTO)) {
				sqlSb.append(" AND PRICE.END_DATE>=date('").append(ADSPUBTO).append("') \r\n");
			}
		}
		if ("WWPRT".equals(ADSPPFORMAT)) {
			/**
			 * 11. PUBTO is an optional DATE. If specified, then “VALID_TO?? must meet the
			 * following criteria: IF PUBTO is Empty “VALID_TO?? is greater than or equal
			 * DTS that the ABR started (aka T2) IF PUBTO is NOT Empty “VALID_TO?? => PUBTO
			 */
			if (ADSPUBTO != null && !"".equals(ADSPUBTO)) {
				sqlSb.append(" AND PRICE.END_DATE>=date('").append(ADSPUBTO).append("') \r\n");
			} else {
				sqlSb.append(" AND PRICE.END_DATE>=date('").append(t2DTS).append("') \r\n");
			}

			// RCQ00206104 WI- EACM - LA prices to support the LA CBS project and WW ESW
			// prices

			if (ADSOFFTYPE != null && !"".equals(ADSOFFTYPE)) {
				if ("NULL".equals(ADSOFFTYPE)) {
					sqlSb.append(" AND PRICE.OFFERING_TYPE in('',").append(offeringtypes).append(") \r\n");
				} else {
					sqlSb.append(" AND PRICE.OFFERING_TYPE='").append(ADSOFFTYPE).append("' \r\n");
				}
			}
			// Fix defect 751817 SIT testing with RDX - Performance issue
			// remove the order by to improve the proformance
			// sqlSb.append(" ORDER BY
			// OFFERING,PRICE_POINT_TYPE,PRICE_POINT_VALUE,PRICE_TYPE,COUNTRY,END_DATE DESC
			// ");
		}
		// Story 1987505 Add chunk function for EACM price - Development
		if (maxLimited()) {
			sqlSb.append("ORDER BY PRICE.INSERT_TS ASC").append(" \r\n");
			sqlSb.append(" FETCH FIRST " + max + " ROWS ONLY").append(" \r\n");
		}
		sqlSb.append(" WITH UR");
		abr.addDebug(" ADSPRICEABR process Query SQL=\r\n" + sqlSb.toString());
	}

	/**
	 * @param abr
	 * @param ADSPPFORMAT
	 * @param rootEntity
	 * @param profileT2
	 * @param priceTable
	 * @param needClear
	 * @return
	 * @throws ParserConfigurationException
	 * @throws DOMException
	 * @throws TransformerException
	 * @throws MissingResourceException
	 */
	public boolean clearPriceTable(ADSABRSTATUS abr, String ADSPPFORMAT, EntityItem rootEntity, Profile profileT2,
			Hashtable priceTable, boolean needClear)
			throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
		if (needClear) {
			if (ADSPPFORMAT != null && "WWPRT".equals(ADSPPFORMAT)) {
				sendWWPRTXML(abr, profileT2, rootEntity, priceTable);
			} else {
				sendEACMXML(abr, profileT2, rootEntity, priceTable);
			}
			priceTable.clear();
			isSended = true;
			needClear = false;
			System.gc();
		}
		return needClear;
	}

	/**
	 *
	 * @param dbCurrent
	 * @param item
	 * @param entityid
	 * @param entitytype
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String getBUNDLETYPE(Database dbCurrent, EntityItem item, int entityid, String entitytype)
			throws MiddlewareRequestException, SQLException, MiddlewareException {
		EntityList m_elist = dbCurrent.getEntityList(item.getProfile(),
				new ExtractActionItem(null, dbCurrent, item.getProfile(), "dummy"),
				new EntityItem[] { new EntityItem(null, item.getProfile(), entitytype, entityid) });
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		String COFCAT = "";

		String attrcode = "BUNDLETYPE";
		EANFlagAttribute fAtt = (EANFlagAttribute) rootEntity.getAttribute(attrcode);
		if (fAtt != null && fAtt.toString().length() > 0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				// get multi cofcat
				if (mfArray[i].isSelected()) {
					if (COFCAT.equals(CHEAT)) {
						COFCAT = (mfArray[i].toString());
					} else {
						COFCAT = COFCAT + "," + (mfArray[i].toString());
					}
				}
			}
		}
		if (COFCAT.indexOf("Hardware") > -1) {
			COFCAT = "Hardware";
		} else if (COFCAT.indexOf("Software") > -1) {
			COFCAT = "Software";
		} else {
			COFCAT = "Service";
		}
		return COFCAT;
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

	private void sendWWPRTXML(ADSABRSTATUS abr, Profile profileT2, EntityItem rootEntity, Hashtable priceTable)
			throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
		abr.addDebug("sendWWPRTXML found " + priceTable.size() + " PRICE");
		// Vector mqVct = getMQPropertiesFN(rootEntity,abr);
		Vector mqVct = getPeriodicMQ(rootEntity);
		if (mqVct == null) {
			abr.addDebug("ADSPRICEABR: No MQ properties files, nothing will be generated.");
			// NOT_REQUIRED = Not Required for {0}.
			abr.addXMLGenMsg("NOT_REQUIRED", "PRICE");
		} else {
			// step 3 filter and send MQ
			// DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// DocumentBuilder builder = factory.newDocumentBuilder();

			Enumeration Keys = priceTable.keys();
			int priceTableCount = 0;
			String key = "";
			Vector priceVct = null;
			StringBuffer pricexml = null;

			int price_mq_count = 0;
			int price_mq_all_count = 0;

			while (Keys.hasMoreElements()) {
				PRICE_MESSAGE_COUNT = PRICE_MESSAGE_COUNT + 1;
				key = (String) Keys.nextElement();
				priceVct = (Vector) priceTable.get(key);

				// Document document = builder.newDocument();
				// Element parent = createWWPRTParent(key, document);
				pricexml = new StringBuffer();
				createWWPRTHead(key, pricexml);

				// create price for each one found
				price_mq_count = 0;
				price_mq_all_count = 0;
				for (int i = 0; i < priceVct.size(); i++) {
					PRICE_RECORD_COUNT = PRICE_RECORD_COUNT + 1;
					// change for the pricexml
					// parent.appendChild(document.createTextNode((String)priceVct.elementAt(i)));
					pricexml.append((String) priceVct.elementAt(i));
					// code for the PRICE_MQ_LIMIT
					price_mq_count = price_mq_count + 1;
					price_mq_all_count = price_mq_all_count + 1;
					if (price_mq_count == PRICE_MQ_LIMIT && price_mq_all_count != priceVct.size()) {

						sendPriceWWPRTMessage(abr, mqVct, pricexml);
						// after send, reset the document and the count and add the price message count
						// document = builder.newDocument();
						// parent = createWWPRTParent(key, document);
						pricexml = new StringBuffer();
						createWWPRTHead(key, pricexml);
						price_mq_count = 0;
						PRICE_MESSAGE_COUNT = PRICE_MESSAGE_COUNT + 1;
						priceTableCount = priceTableCount + 1;
					}

				} // end for
					// send one price xml
				sendPriceWWPRTMessage(abr, mqVct, pricexml);

			}
			// release memory
			priceTable.clear();
		}

	}

	private void createWWPRTHead(String key, StringBuffer pricexml) {
		pricexml.append("<wwprttxn xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\"");
		pricexml.append(key);
		pricexml.append("\" type=\"price\" xsi:noNamespaceSchemaLocation=\"price.xsd\">");
	}

	/**
	 * @param abr
	 * @param mqVct
	 * @param document
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws MissingResourceException
	 */
	private void sendPriceWWPRTMessage(ADSABRSTATUS abr, Vector mqVct, StringBuffer pricexml)
			throws ParserConfigurationException, TransformerException, MissingResourceException {
		long t1 = System.currentTimeMillis();
		// String xml = abr.transformWWPRTXML(this, document);
		String xml = pricexml.toString() + "</wwprttxn>";

		// new added
		String entitytype = "";
		if (xml.indexOf("offeringtype") != -1) {
			entitytype = "XMLPRODPRICESETUP_W2";
		} else {
			entitytype = "XMLPRODPRICESETUP_W1";
		}
		boolean ifpass = false;
		String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_" + entitytype + "_XSDNEEDED", "NO");
		if ("YES".equals(ifNeed.toUpperCase())) {
			String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
					"_" + entitytype + "_XSDFILE", "NONE");
			if ("NONE".equals(xsdfile)) {
				abr.addError("there is no xsdfile for " + entitytype + " defined in the propertyfile ");
			} else {
				long rtm = System.currentTimeMillis();
				Class cs = this.getClass();
				StringBuffer debugSb = new StringBuffer();
				ifpass = ABRUtil.validatexml(cs, debugSb, xsdfile, xml);
				if (debugSb.length() > 0) {
					String s = debugSb.toString();
					if (s.indexOf("fail") != -1)
						abr.addError(s);
					abr.addOutput(s);
				}
				long ltm = System.currentTimeMillis();
				abr.addDebugComment(D.EBUG_DETAIL, "Time for validation: " + Stopwatch.format(ltm - rtm));
				if (ifpass) {
					abr.addDebug("the xml for " + entitytype + " passed the validation");
				}
			}
		} else {
			abr.addOutput("the xml for " + entitytype + " doesn't need to be validated");
			ifpass = true;
		}

		// new added end

		// add flag(new added)
		if (xml != null && ifpass) {
			if (!ADSABRSTATUS.USERXML_OFF_LOG) {
				abr.addDebug("ADSPRICEABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + xml + ADSABRSTATUS.NEWLINE);
			}
			abr.notify(this, "PRICE", xml, mqVct);
			long t2 = System.currentTimeMillis();
			if (!ADSABRSTATUS.USERXML_OFF_LOG) {
				abr.addDebug("Sending one wwprt message takes time:" + (t2 - t1) + " ms");
			}
			ALL_WWPRT_SEND_TIME = ALL_WWPRT_SEND_TIME + (t2 - t1);
		}
	}

	/**
	 * @param abr
	 * @param mqVct
	 * @param document
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws MissingResourceException
	 */
	private void sendPriceMessage(ADSABRSTATUS abr, Vector mqVct, Document document)
			throws ParserConfigurationException, TransformerException, MissingResourceException {
		String xml = abr.transformXML(this, document);
		abr.addDebug("xml:" + xml);
		// new added
		boolean ifpass = false;
		// String entitytype = rootEntity.getEntityType();
		String entitytype = "XMLPRODPRICESETUP_E";
		String ifNeed = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
				"_" + entitytype + "_XSDNEEDED", "NO");
		if ("YES".equals(ifNeed.toUpperCase())) {
			String xsdfile = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS",
					"_" + entitytype + "_XSDFILE", "NONE");
			if ("NONE".equals(xsdfile)) {
				abr.addError("there is no xsdfile for " + entitytype + " defined in the propertyfile ");
			} else {
				long rtm = System.currentTimeMillis();
				Class cs = this.getClass();
				StringBuffer debugSb = new StringBuffer();
				ifpass = ABRUtil.validatexml(cs, debugSb, xsdfile, xml);
				if (debugSb.length() > 0) {
					String s = debugSb.toString();
					if (s.indexOf("fail") != -1)
						abr.addError(s);
					abr.addOutput(s);
				}
				long ltm = System.currentTimeMillis();
				abr.addDebugComment(D.EBUG_DETAIL, "Time for validation: " + Stopwatch.format(ltm - rtm));
				if (ifpass) {
					abr.addDebug("the xml for " + entitytype + " passed the validation");
				}
			}
		} else {
			abr.addOutput("the xml for " + entitytype + " doesn't need to be validated");
			ifpass = true;
		}

		// new added end

		// add flag(new added)
		if (xml != null && ifpass) {
			if (!ADSABRSTATUS.USERXML_OFF_LOG) {
				abr.addDebug("ADSPRICEABR: Generated MQ xml:" + ADSABRSTATUS.NEWLINE + xml + ADSABRSTATUS.NEWLINE);
			}
			abr.addDebug("xml:" + xml);
			abr.notify(this, "PRICE", xml, mqVct);
		}
	}

	/**
	 * send EACM XML
	 * 
	 * @param abr
	 * @param profileT2
	 * @param rootEntity
	 * @param priceVct
	 * @throws ParserConfigurationException
	 * @throws DOMException
	 * @throws TransformerException
	 * @throws MissingResourceException
	 */
	private void sendEACMXML(ADSABRSTATUS abr, Profile profileT2, EntityItem rootEntity, Hashtable priceTable)
			throws ParserConfigurationException, DOMException, TransformerException, MissingResourceException {
		abr.addDebug("sendEACMXML found " + priceTable.size() + " PRICE");

		// Vector mqVct = getMQPropertiesFN(rootEntity,abr);
		Vector mqVct = getPeriodicMQ(rootEntity);
		if (mqVct == null) {
			abr.addDebug("ADSPRICEABR: No MQ properties files, nothing will be generated.");
			// NOT_REQUIRED = Not Required for {0}.
			abr.addXMLGenMsg("NOT_REQUIRED", "PRICE");
		} else {
			// step 3 filter and send MQ
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// create one XLATEELEMENT for each one found
			Enumeration Keys = priceTable.keys();
			int priceTableCount = 0;
			String key = "";
			Vector priceVct = null;
			PriceInfo price = null;

			Document document = null;
			Element PRODUCTLIST = null;
			Element elem;

			int price_mq_count = 0;
			int price_mq_all_count = 0;
			Element PRODUCTELEMENT = null;

			// Vector historyVector = null;
			// PriceInfo _price = null;

			while (Keys.hasMoreElements()) {
				PRICE_MESSAGE_COUNT = PRICE_MESSAGE_COUNT + 1;
				key = (String) Keys.nextElement();
				priceVct = (Vector) priceTable.get(key);

				price = (PriceInfo) priceVct.get(0);

				document = builder.newDocument(); // Create
				PRODUCTLIST = createEACMParent(profileT2, price, document);

				price_mq_count = 0;
				price_mq_all_count = 0;
				for (int i = 0; i < priceVct.size(); i++) {
					PRICE_RECORD_COUNT = PRICE_RECORD_COUNT + 1;
					price = (PriceInfo) priceVct.elementAt(i);

					// 3 PRODUCTELEMENT
					PRODUCTELEMENT = (Element) document.createElement("PRODUCTELEMENT");
					PRODUCTLIST.appendChild(PRODUCTELEMENT);

					// 4 PRODUCTENTITYTYPE
					elem = (Element) document.createElement("PRODUCTENTITYTYPE");
					elem.appendChild(document.createTextNode(price.PRODUCTENTITYTYPE));
					PRODUCTELEMENT.appendChild(elem);

					// 4 PRODUCTENTITYID
					elem = (Element) document.createElement("PRODUCTENTITYID");
					elem.appendChild(document.createTextNode(price.PRODUCTENTITYID));
					PRODUCTELEMENT.appendChild(elem);

					// 4 PRICEPOINTENTITYTYPE
					elem = (Element) document.createElement("PRICEPOINTENTITYTYPE");
					elem.appendChild(document.createTextNode(price.PRICEPOINTENTITYTYPE));
					PRODUCTELEMENT.appendChild(elem);

					// 4 PRICEPOINTENTITYID
					elem = (Element) document.createElement("PRICEPOINTENTITYID");
					elem.appendChild(document.createTextNode(price.PRICEPOINTENTITYID));
					PRODUCTELEMENT.appendChild(elem);

					// From Dave 2011-11-03 even though the COLUMNs might be MACHTYPEATR and
					// MODELATR
					// -- the XML tags should be MACHTYPE and MODEL
					// 4 MACHTYPE
					elem = (Element) document.createElement("MACHTYPE");
					elem.appendChild(document.createTextNode(price.MACHTYPE));
					PRODUCTELEMENT.appendChild(elem);
					// 4 MODEL
					elem = (Element) document.createElement("MODEL");
					elem.appendChild(document.createTextNode(price.MODEL));
					PRODUCTELEMENT.appendChild(elem);
					// 4 FEATURECODE
					elem = (Element) document.createElement("FEATURECODE");
					elem.appendChild(document.createTextNode(price.FEATURECODE));
					PRODUCTELEMENT.appendChild(elem);
					// 4 FROMMACHTYPE
					elem = (Element) document.createElement("FROMMACHTYPE");
					elem.appendChild(document.createTextNode(price.FROMMACHTYPE));
					PRODUCTELEMENT.appendChild(elem);
					// 4 FROMMODEL
					elem = (Element) document.createElement("FROMMODEL");
					elem.appendChild(document.createTextNode(price.FROMMODEL));
					PRODUCTELEMENT.appendChild(elem);
					// 4 FROMFEATURECODE
					elem = (Element) document.createElement("FROMFEATURECODE");
					elem.appendChild(document.createTextNode(price.FROMFEATURECODE));
					PRODUCTELEMENT.appendChild(elem);
					// 4 SEOID
					elem = (Element) document.createElement("SEOID");
					elem.appendChild(document.createTextNode(price.SEOID));
					PRODUCTELEMENT.appendChild(elem);
					// 4 MKTGNAME
					elem = (Element) document.createElement("MKTGNAME");
					elem.appendChild(document.createTextNode(price.MKTGNAME));
					PRODUCTELEMENT.appendChild(elem);
					// 4 INVNAME
					elem = (Element) document.createElement("INVNAME");
					elem.appendChild(document.createTextNode(price.INVNAME));
					PRODUCTELEMENT.appendChild(elem);
					// 4 PRICELIST
					Element PRICELIST = (Element) document.createElement("PRICELIST");
					PRODUCTELEMENT.appendChild(PRICELIST);

					printPriceInfo(price, document, PRICELIST);
					// //add start the history price
					// historyVector = price.HISTORYPRICE;
					// if(historyVector!=null && historyVector.size()>0){
					// for(int h=0;h< historyVector.size();h++){
					// _price = (PriceInfo)historyVector.get(h);
					// printPriceInfo(_price, document, PRICELIST);
					// }
					// }
					// //Add end the history price

					// code for the PRICE_MQ_LIMIT
					price_mq_count = price_mq_count + 1;
					price_mq_all_count = price_mq_all_count + 1;
					if (price_mq_count == PRICE_MQ_LIMIT && price_mq_all_count != priceVct.size()) {
						sendPriceMessage(abr, mqVct, document);
						// after send, reset the document and the count and add the price message count
						document = builder.newDocument();
						PRODUCTLIST = createEACMParent(profileT2, price, document);
						price_mq_count = 0;
						PRICE_MESSAGE_COUNT = PRICE_MESSAGE_COUNT + 1;
						priceTableCount = priceTableCount + 1;
					}
				}

				// release memory
				price.dereference();
				sendPriceMessage(abr, mqVct, document);

			}

			// release memory
			priceTable.clear();
		}

	}

	/**
	 * @param profileT2
	 * @param price
	 * @param document
	 * @return
	 * @throws DOMException
	 */
	private Element createEACMParent(Profile profileT2, PriceInfo price, Document document) throws DOMException {
		Element parent = (Element) document.createElement("PRODUCT_PRICE_UPDATE");
		parent.setAttribute("xmlns", "http://w3.ibm.com/xmlns/ibmww/oim/eannounce/ads/PRODUCT_PRICE_UPDATE");

		// <!--PRODUCT_PRICE_UPDATE Version V Mod M-->
		parent.appendChild(document.createComment("PRODUCT_PRICE_UPDATE Version " + XMLVERSION10 + " Mod " + XMLMOD10));
		// create the root
		document.appendChild(parent);

		// Level 2 DTSOFMSG
		Element elem = (Element) document.createElement("DTSOFMSG");
		elem.appendChild(document.createTextNode(profileT2.getEndOfDay()));
		parent.appendChild(elem);
		// 2 ACTIVITY
		elem = (Element) document.createElement("ACTIVITY");
		elem.appendChild(document.createTextNode(price.ACTIVITY));
		parent.appendChild(elem);
		// 2 ACTIVITY
		elem = (Element) document.createElement("PDHDOMAIN");
		elem.appendChild(document.createTextNode(price.PDHDOMAIN));
		parent.appendChild(elem);
		// 2 PRODUCTCATEGORY
		elem = (Element) document.createElement("PRODUCTCATEGORY");
		elem.appendChild(document.createTextNode(price.PRODUCTCATEGORY));
		parent.appendChild(elem);
		// 2 PRODUCTOFFERINGTYPE
		elem = (Element) document.createElement("PRODUCTOFFERINGTYPE");
		elem.appendChild(document.createTextNode(price.PRODUCTOFFERINGTYPE));
		parent.appendChild(elem);
		// 2 PRODUCTLIST
		Element PRODUCTLIST = (Element) document.createElement("PRODUCTLIST");
		parent.appendChild(PRODUCTLIST);
		return PRODUCTLIST;
	}

	/**
	 * @param price
	 * @param document
	 * @param PRICELIST
	 * @throws DOMException
	 */
	private void printPriceInfo(PriceInfo price, Document document, Element PRICELIST) throws DOMException {
		Element elem;
		// 5 price
		Element priceELEMENT = (Element) document.createElement("price");
		PRICELIST.appendChild(priceELEMENT);

		// LEVEL 6 OFFERING
		elem = (Element) document.createElement("offering");
		elem.appendChild(document.createTextNode(price.OFFERING));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("startdate");
		elem.appendChild(document.createTextNode(price.STARTDATE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("currency");
		elem.appendChild(document.createTextNode(price.CURRENCY));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("cabletype");
		elem.appendChild(document.createTextNode(price.CABLETYPE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("cableid");
		elem.appendChild(document.createTextNode(price.CABLEID));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("releasets");
		elem.appendChild(document.createTextNode(price.RELEASETS));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("pricevalue");
		elem.appendChild(document.createTextNode(price.PRICEVALUE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("pricepointtype");
		elem.appendChild(document.createTextNode(price.PRICEPOINTTYPE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("pricepointvalue");
		elem.appendChild(document.createTextNode(price.PRICEPOINTVALUE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("country");
		elem.appendChild(document.createTextNode(price.COUNTRY));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("pricetype");
		elem.appendChild(document.createTextNode(price.PRICETYPE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("onshore");
		elem.appendChild(document.createTextNode(price.ONSHORE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("enddate");
		elem.appendChild(document.createTextNode(price.ENDDATE));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("pricevalueusd");
		elem.appendChild(document.createTextNode(price.PRICEVALUEUSD));
		priceELEMENT.appendChild(elem);

		elem = (Element) document.createElement("factor");
		elem.appendChild(document.createTextNode(price.FACTOR));
		priceELEMENT.appendChild(elem);
	}

	/**
	 * get the description of the item
	 * 
	 * @param item
	 * @param code
	 * @return
	 */
	private String getDescription(EntityItem item, String code, String type) {
		String value = "";
		EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute(code);
		if (fAtt != null && fAtt.toString().length() > 0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					if (type.equals("short")) {
						sb.append(mfArray[i].getShortDescription());
					} else if (type.equals("long")) {
						sb.append(mfArray[i].getLongDescription());
					} else if (type.equals("flag")) {
						sb.append(mfArray[i].getFlagCode());
					} else {
						sb.append(mfArray[i].toString());
					}
				}
			} //
			value = sb.toString();
		}
		return value;
	}

	/**
	 * get the description of the item
	 * 
	 * @param item
	 * @param code
	 * @return
	 */
	private String getMultiDescription(EntityItem item, String code, String type) {
		String value = "";
		EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute(code);
		if (fAtt != null && fAtt.toString().length() > 0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					if (sb.length() > 0) {
						sb.append(",");
					}
					if (type.equals("short")) {
						sb.append("'").append(mfArray[i].getShortDescription()).append("'");
					} else if (type.equals("long")) {
						sb.append("'").append(mfArray[i].getLongDescription()).append("'");
					} else if (type.equals("flag")) {
						sb.append("'").append(mfArray[i].getFlagCode()).append("'");
					} else {
						sb.append("'").append(mfArray[i].toString()).append("'");
					}
				}
			} //
			value = sb.toString();
		}
		return value;
	}

	/**
	 *
	 * @param item
	 * @param code
	 * @param type
	 * @return
	 */
	private Hashtable getDescriptionTable(EntityItem item, String code, String type) {
		Hashtable htable = new Hashtable();
		EANFlagAttribute fAtt = (EANFlagAttribute) item.getAttribute(code);
		if (fAtt != null && fAtt.toString().length() > 0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
			for (int i = 0; i < mfArray.length; i++) {
				// get selection
				if (mfArray[i].isSelected()) {
					if (type.equals("short")) {
						htable.put(mfArray[i].getShortDescription(), mfArray[i].getShortDescription());
					} else if (type.equals("long")) {
						htable.put(mfArray[i].getLongDescription(), mfArray[i].getLongDescription());
					} else if (type.equals("flag")) {
						htable.put(mfArray[i].getFlagCode(), mfArray[i].getFlagCode());
					} else {
						htable.put(mfArray[i].toString(), mfArray[i].toString());
					}
				}
			} //
		}
		return htable;
	}

	/**
	 * getDivText
	 * 
	 * @param item
	 * @param code
	 * @param type
	 * @return
	 */
	private Hashtable getDIVTEXTTable(String DIVTEXT) {
		Hashtable DIVTable = new Hashtable();
		if (!"".equals(DIVTEXT)) {
			StringTokenizer str = new StringTokenizer(DIVTEXT, ",");
			String DIVvalue = "";
			while (str.hasMoreTokens()) {
				DIVvalue = str.nextToken();
				if ("".equals(DIVTEXT)) {
					return new Hashtable();
				} else {
					DIVTable.put(DIVvalue, DIVvalue);
				}
			}
		}
		return DIVTable;
	}

	/**********************************
	 *
	 * A. MQ-Series CID
	 */
	public String getMQCID() {
		return MQCID;
	}

	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getVersion() {
		return "1.2";
	}

	// private static final String PRICE_EACM_HISTORY_SQL =
	// " SELECT \r\n"+
	// " PRICE.OFFERING AS OFFERING, \r\n"+
	// " PRICE.START_DATE AS START_DATE, \r\n"+
	// " PRICE.CURRENCY AS CURRENCY, \r\n"+
	// " PRICE.CABLETYPE AS CABLETYPE, \r\n"+
	// " PRICE.CABLEID AS CABLEID, \r\n"+
	// " PRICE.RELEASE_TS AS RELEASE_TS, \r\n"+
	// " PRICE.PRICE_VALUE AS PRICE_VALUE, \r\n"+
	// " PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE, \r\n"+
	// " PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE, \r\n"+
	// " PRICE.COUNTRY AS COUNTRY, \r\n"+
	// " PRICE.PRICE_TYPE AS PRICE_TYPE, \r\n"+
	// " PRICE.ONSHORE AS ONSHORE, \r\n"+
	// " PRICE.END_DATE AS END_DATE, \r\n"+
	// " PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD, \r\n"+
	// " PRICE.FACTOR AS FACTOR \r\n"+
	// " FROM PRICE.PRICE AS PRICE \r\n"+
	// " WHERE \r\n"+
	// " PRICE.ACTION = 'I' AND \r\n"+
	// " PRICE.END_DATE<>'12/31/9999' \r\n";

	// private static final String PRICE_EACM_SQL =
	// " SELECT \r\n"+
	// " PRICE.ID AS ID, \r\n"+
	// " PRICE.INSERT_TS AS INSERT_TS, \r\n"+
	// " '' AS PDHDOMAIN, \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='MOD' THEN '' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN '' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN '' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='SWF' THEN '' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='WSF' THEN '' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FUP' THEN 'Hardware' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='MUP' THEN 'Hardware' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='TFU' THEN 'Hardware' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='TMU' THEN 'Hardware' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='' THEN '' \r\n"+
	// " ELSE '' \r\n"+
	// " END AS PRODUCTCATEGORY, \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='MOD' THEN 'MODEL' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN 'FEATURE' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN 'FEATURE' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='SWF' THEN 'SW FEATURE' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='WSF' THEN 'WW SW FEATURE' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FUP' THEN 'Feature Upgrade' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='MUP' THEN 'Model Upgrade' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='TFU' THEN 'Type Feature Upgrade' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='TMU' THEN 'Type Model Upgrade' \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='' THEN \r\n"+
	// " case when PRICE.OFFERING_TYPE='' THEN 'SEO' \r\n"+
	// " when PRICE.OFFERING_TYPE='SEO' THEN 'SEO' \r\n"+
	// " else '' end \r\n"+
	// " ELSE '' \r\n"+
	// " END AS PRODUCTOFFERINGTYPE, \r\n"+
	// " PRICE.OFFERING_TYPE AS OFFERING_TYPE, \r\n"+
	// " '' AS PRODUCTENTITYTYPE, \r\n"+
	// " '' AS PRODUCTENTITYID, \r\n"+
	// " '' AS PRICEPOINTENTITYTYPE, \r\n"+
	// " '' AS PRICEPOINTENTITYID, \r\n"+
	// " PRICE.MACHTYPEATR AS MACHTYPEATR, \r\n"+
	// " PRICE.MODELATR AS MODELATR, \r\n"+
	// " PRICE.FEATURECODE AS FEATURECODE, \r\n"+
	// " PRICE.FROM_MACHTYPEATR AS FROM_MACHTYPEATR, \r\n"+
	// " PRICE.FROM_MODELATR AS FROM_MODELATR, \r\n"+
	// " PRICE.FROM_FEATURECODE AS FROM_FEATURECODE, \r\n"+
	// " PRICE.PARTNUM AS PARTNUM, \r\n"+
	// " '' AS MKTGNAME, \r\n"+
	// " '' AS INVNAME, \r\n"+
	// " PRICE.OFFERING AS OFFERING, \r\n"+
	// " PRICE.START_DATE AS START_DATE, \r\n"+
	// " PRICE.CURRENCY AS CURRENCY, \r\n"+
	// " PRICE.CABLETYPE AS CABLETYPE, \r\n"+
	// " PRICE.CABLEID AS CABLEID, \r\n"+
	// " PRICE.RELEASE_TS AS RELEASE_TS, \r\n"+
	// " PRICE.PRICE_VALUE AS PRICE_VALUE, \r\n"+
	// " PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE, \r\n"+
	// " PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE, \r\n"+
	// " PRICE.COUNTRY AS COUNTRY, \r\n"+
	// " PRICE.PRICE_TYPE AS PRICE_TYPE, \r\n"+
	// " PRICE.ONSHORE AS ONSHORE, \r\n"+
	// " PRICE.END_DATE AS END_DATE, \r\n"+
	// " PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD, \r\n"+
	// " PRICE.FACTOR AS FACTOR \r\n"+
	// " FROM PRICE.PRICE AS PRICE \r\n"+
	// " WHERE \r\n"+
	// " PRICE.ACTION = 'I' AND \r\n"+
	// " PRICE.END_DATE='12/31/9999' \r\n";

	private static final String PRICE_WWPRT_SQL = "  SELECT                                                                       \r\n"
			+ "     PRICE.ID AS ID,                                                           \r\n"
			+ "     PRICE.PRICEXML AS PRICEXML                                                \r\n"
			+ "   FROM PRICE.PRICE AS PRICE                                                   \r\n"
			+ "   WHERE                                                                       \r\n"
			+ "     PRICE.ACTION = 'I'                                                        \r\n";
	private static final String PRICE_WWPRT_SQL_LIMITED = "  SELECT                                                                       \r\n"
			+ "     PRICE.ID AS ID,                                                           \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n"
			+ "     PRICE.PRICEXML AS PRICEXML                                                \r\n"
			+ "   FROM PRICE.PRICE AS PRICE                                                   \r\n"
			+ "   WHERE                                                                       \r\n"
			+ "     PRICE.ACTION = 'I'                                                        \r\n";

	private static String SQL_MODEL = "  SELECT                                                                       \r\n"
			+ "    PRICE.ID AS ID,                                                            \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n"
			+ "    MODEL.PDHDOMAIN AS PDHDOMAIN,                                              \r\n"
			+ "    MODEL.COFCAT AS PRODUCTCATEGORY,                                           \r\n"
			+ "    PRICE.OFFERING_TYPE AS PRODUCTOFFERINGTYPE,                                \r\n"
			+ "    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n"
			+ "    'MACHTYPE' AS PRODUCTENTITYTYPE,                                           \r\n"
			+ "    MODEL.MACHID AS PRODUCTENTITYID,                                           \r\n"
			+ "    'MODEL' AS PRICEPOINTENTITYTYPE,                                           \r\n"
			+ "    MODEL.MDLID AS PRICEPOINTENTITYID,                                         \r\n"
			+ "    MODEL.MACHTYPEATR AS MACHTYPEATR,                                          \r\n"
			+ "    MODEL.MODELATR AS MODELATR,                                                \r\n"
			+ "    '' AS FEATURECODE,                                                         \r\n"
			+ "    '' AS FROM_MACHTYPEATR,                                                    \r\n"
			+ "    '' AS FROM_MODELATR,                                                       \r\n"
			+ "    '' AS FROM_FEATURECODE,                                                    \r\n"
			+ "    '' AS PARTNUM,                                                             \r\n"
			+ "    MODEL.MKTGNAME AS MKTGNAME,                                                \r\n"
			+ "    MODEL.INVNAME AS INVNAME,                                                  \r\n"
			+ "    PRICE.OFFERING AS OFFERING,                                                \r\n"
			+ "    PRICE.START_DATE AS START_DATE,                                            \r\n"
			+ "    PRICE.CURRENCY AS CURRENCY,                                                \r\n"
			+ "    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n"
			+ "    PRICE.CABLEID AS CABLEID,                                                  \r\n"
			+ "    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n"
			+ "    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n"
			+ "    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n"
			+ "    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n"
			+ "    PRICE.COUNTRY AS COUNTRY,                                                  \r\n"
			+ "    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n"
			+ "    PRICE.ONSHORE AS ONSHORE,                                                  \r\n"
			+ "    PRICE.END_DATE AS END_DATE,                                                \r\n"
			+ "    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n"
			+ "    PRICE.FACTOR AS FACTOR,                                                    \r\n"
			+ "    MODEL.DIV AS DIV                                                           \r\n"
			+ "  FROM PRICE.PRICE AS PRICE                                                    \r\n"
			+ "  INNER JOIN PRICE.MDLINFO AS MODEL ON                                         \r\n"
			+ "    PRICE.MACHTYPEATR = MODEL.MACHTYPEATR AND                                  \r\n"
			+ "    PRICE.MODELATR = MODEL.MODELATR                                            \r\n"
			+ "  WHERE                                                                        \r\n"
			+ "    PRICE.ACTION = 'I' AND                                                     \r\n"
			+ "    PRICE.PRICE_POINT_TYPE='MOD'                                               \r\n";

	private static String SQL_FEATURE = "  SELECT                                                                                          \r\n"
			+ "    PRICE.ID AS ID,                                                                               \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                                                 \r\n"
			+ "    TMF.PDHDOMAIN AS PDHDOMAIN,                                                                   \r\n"
			+ "    TMF.COFCAT AS PRODUCTCATEGORY,                                                                \r\n"
			+ "    TMF.OFFTYPE AS PRODUCTOFFERINGTYPE,                                                           \r\n"
			+ "    TMF.OFFTYPE AS OFFERING_TYPE,                                                                 \r\n"
			+ "    TMF.PRODTYPE AS PRODUCTENTITYTYPE,                                                            \r\n"
			+ "    TMF.MACHID AS PRODUCTENTITYID,                                                                \r\n"
			+ "    TMF.FEATTYPE AS PRICEPOINTENTITYTYPE,                                                         \r\n"
			+ "    TMF.FEATID AS PRICEPOINTENTITYID,                                                             \r\n"
			+ "    TMF.MACHTYPEATR AS MACHTYPEATR,                                                               \r\n"
			+ "    PRICE.MODELATR AS MODELATR,                                                                   \r\n"
			+ "    TMF.FEATURECODE AS FEATURECODE,                                                               \r\n"
			+ "                                                                                                  \r\n"
			+ "    '' AS FROM_MACHTYPEATR,                                                                       \r\n"
			+ "    '' AS FROM_MODELATR,                                                                          \r\n"
			+ "    '' AS FROM_FEATURECODE,                                                                       \r\n"
			+ "    '' AS PARTNUM,                                                                                \r\n"
			+ "    TMF.MKTGNAME AS MKTGNAME,                                                                     \r\n"
			+ "    TMF.BHINVNAME AS INVNAME,                                                                     \r\n"
			+ "    PRICE.OFFERING AS OFFERING,                                                                   \r\n"
			+ "    PRICE.START_DATE AS START_DATE,                                                               \r\n"
			+ "    PRICE.CURRENCY AS CURRENCY,                                                                   \r\n"
			+ "    PRICE.CABLETYPE AS CABLETYPE,                                                                 \r\n"
			+ "    PRICE.CABLEID AS CABLEID,                                                                     \r\n"
			+ "    PRICE.RELEASE_TS AS RELEASE_TS,                                                               \r\n"
			+ "    PRICE.PRICE_VALUE AS PRICE_VALUE,                                                             \r\n"
			+ "    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                                   \r\n"
			+ "    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                                                 \r\n"
			+ "    PRICE.COUNTRY AS COUNTRY,                                                                     \r\n"
			+ "    PRICE.PRICE_TYPE AS PRICE_TYPE,                                                               \r\n"
			+ "    PRICE.ONSHORE AS ONSHORE,                                                                     \r\n"
			+ "    PRICE.END_DATE AS END_DATE,                                                                   \r\n"
			+ "    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                                     \r\n"
			+ "    PRICE.FACTOR AS FACTOR,                                                                       \r\n"
			+ "    TMF.DIV AS DIV                                                                                \r\n"
			+ "  FROM PRICE.PRICE AS PRICE                                                                       \r\n"
			+ "  INNER JOIN PRICE.TMF TMF ON                                                                     \r\n"
			+ "  PRICE.MACHTYPEATR = TMF.MACHTYPEATR AND                                                         \r\n"
			+ "  PRICE.FEATURECODE = TMF.FEATURECODE                                                             \r\n"
			+ "  WHERE                                                                                           \r\n"
			+ "    PRICE.ACTION = 'I' AND                                                                        \r\n"
			+ "    PRICE.PRICE_POINT_TYPE in ('FEA','RPQ')                                                       \r\n";

	// private static String SQL_FEATURE =
	// " SELECT \r\n"+
	// " PRICE.ID AS ID, \r\n"+
	// " PRICE.INSERT_TS AS INSERT_TS, \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE FLAG.FLAGDESCRIPTION END)
	// \r\n"+
	// " ELSE FEATURE.PDHDOMAIN END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE FEATURE.PDHDOMAIN END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS PDHDOMAIN, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE 'Service' END) \r\n"+
	// " ELSE MODEL.COFCAT END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE MODEL.COFCAT END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS PRODUCTCATEGORY, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE 'SVCMOD' END) \r\n"+
	// " ELSE PRICE.OFFERING_TYPE END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE PRICE.OFFERING_TYPE END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS PRODUCTOFFERINGTYPE, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE 'SVCMOD' END) \r\n"+
	// " ELSE 'MACHTYPE' END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE 'MACHTYPE' END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS PRODUCTENTITYTYPE, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE SVCMOD.ENTITYID END)
	// \r\n"+
	// " ELSE MACHTYPE.ENTITYID END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE MACHTYPE.ENTITYID END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS PRODUCTENTITYID, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE 'PRCPT' END) \r\n"+
	// " ELSE 'FEATURE' END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE 'FEATURE' END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS PRICEPOINTENTITYTYPE, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE PRCPT.ENTITYID END) \r\n"+
	// " ELSE FEATURE.ENTITYID END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE FEATURE.ENTITYID END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS PRICEPOINTENTITYID, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN PRICE.MACHTYPEATR ELSE
	// SVCMOD.SMACHTYPEATR END)\r\n"+
	// " ELSE MACHTYPE.MACHTYPEATR END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN PRICE.MACHTYPEATR \r\n"+
	// " ELSE MACHTYPE.MACHTYPEATR END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS MACHTYPEATR, \r\n"+
	// " \r\n"+
	// " PRICE.MODELATR AS MODELATR, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN PRICE.FEATURECODE ELSE '' END)
	// \r\n"+
	// " ELSE FEATURE.FEATURECODE END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN PRICE.FEATURECODE \r\n"+
	// " ELSE FEATURE.FEATURECODE END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS FEATURECODE, \r\n"+
	// " \r\n"+
	// " '' AS FROM_MACHTYPEATR, \r\n"+
	// " '' AS FROM_MODELATR, \r\n"+
	// " '' AS FROM_FEATURECODE, \r\n"+
	// " '' AS PARTNUM, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE PRCPT.MKTGNAME END) \r\n"+
	// " ELSE FEATURE.MKTGNAME END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE FEATURE.MKTGNAME END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS MKTGNAME, \r\n"+
	// " \r\n"+
	// " CASE \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='FEA' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN \r\n"+
	// " (CASE WHEN PRCPT.ENTITYID IS NULL THEN NULL ELSE PRCPT.INVNAME END) \r\n"+
	// " ELSE FEATURE.BHINVNAME END) \r\n"+
	// " WHEN PRICE.PRICE_POINT_TYPE='RPQ' THEN \r\n"+
	// " (CASE WHEN PRODSTRUCT.ENTITYID IS NULL THEN NULL \r\n"+
	// " ELSE FEATURE.BHINVNAME END) \r\n"+
	// " ELSE NULL \r\n"+
	// " END AS INVNAME, \r\n"+
	// " \r\n"+
	// " PRICE.OFFERING AS OFFERING, \r\n"+
	// " PRICE.START_DATE AS START_DATE, \r\n"+
	// " PRICE.CURRENCY AS CURRENCY, \r\n"+
	// " PRICE.CABLETYPE AS CABLETYPE, \r\n"+
	// " PRICE.CABLEID AS CABLEID, \r\n"+
	// " PRICE.RELEASE_TS AS RELEASE_TS, \r\n"+
	// " PRICE.PRICE_VALUE AS PRICE_VALUE, \r\n"+
	// " PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE, \r\n"+
	// " PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE, \r\n"+
	// " PRICE.COUNTRY AS COUNTRY, \r\n"+
	// " PRICE.PRICE_TYPE AS PRICE_TYPE, \r\n"+
	// " PRICE.ONSHORE AS ONSHORE, \r\n"+
	// " PRICE.END_DATE AS END_DATE, \r\n"+
	// " PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD, \r\n"+
	// " PRICE.FACTOR AS FACTOR \r\n"+
	// " FROM PRICE.PRICE AS PRICE \r\n"+
	// " LEFT JOIN PRICE.MODEL AS MODEL ON \r\n"+
	// " PRICE.MACHTYPEATR = MODEL.MACHTYPEATR \r\n"+
	// " LEFT JOIN PRICE.MACHTYPE AS MACHTYPE ON \r\n"+
	// " MODEL.MACHTYPEATR = MACHTYPE.MACHTYPEATR \r\n"+
	// " LEFT JOIN PRICE.FEATURE AS FEATURE ON \r\n"+
	// " PRICE.FEATURECODE = FEATURE.FEATURECODE \r\n"+
	// " LEFT JOIN OPICM.RELATOR AS REL ON \r\n"+
	// " REL.ENTITYTYPE = 'PRODSTRUCT' AND \r\n"+
	// " REL.ENTITY1ID = FEATURE.ENTITYID AND \r\n"+
	// " REL.ENTITY2ID = MODEL.ENTITYID AND \r\n"+
	// " REL.VALTO > CURRENT TIMESTAMP AND \r\n"+
	// " REL.EFFTO > CURRENT TIMESTAMP \r\n"+
	// " LEFT JOIN PRICE.PRODSTRUCT AS PRODSTRUCT ON \r\n"+
	// " PRODSTRUCT.ENTITYID = REL.ENTITYID \r\n"+
	// " LEFT JOIN PRICE.SVCMOD AS SVCMOD ON \r\n"+
	// " PRICE.MACHTYPEATR = SVCMOD.SMACHTYPEATR \r\n"+
	// " LEFT JOIN PRICE.FLAG AS FLAG ON \r\n"+
	// " FLAG.ENTITYID=SVCMOD.ENTITYID AND \r\n"+
	// " FLAG.ENTITYTYPE='SVCMOD' AND \r\n"+
	// " FLAG.ATTRIBUTECODE='PDHDOMAIN' \r\n"+
	// " LEFT JOIN OPICM.RELATOR AS SVCMODCHRGCOMP ON \r\n"+
	// " SVCMODCHRGCOMP.ENTITY1ID = SVCMOD.ENTITYID AND \r\n"+
	// " SVCMODCHRGCOMP.ENTITY1TYPE='SVCMOD' AND \r\n"+
	// " SVCMODCHRGCOMP.ENTITYTYPE='SVCMODCHRGCOMP' AND \r\n"+
	// " SVCMODCHRGCOMP.VALTO > CURRENT TIMESTAMP AND \r\n"+
	// " SVCMODCHRGCOMP.EFFTO > CURRENT TIMESTAMP \r\n"+
	// " LEFT JOIN OPICM.RELATOR AS CHRGCOMPPRCPT ON \r\n"+
	// " CHRGCOMPPRCPT.ENTITY1ID = SVCMODCHRGCOMP.ENTITY2ID AND \r\n"+
	// " CHRGCOMPPRCPT.ENTITYTYPE='CHRGCOMPPRCPT' AND \r\n"+
	// " CHRGCOMPPRCPT.VALTO > CURRENT TIMESTAMP AND \r\n"+
	// " CHRGCOMPPRCPT.EFFTO > CURRENT TIMESTAMP \r\n"+
	// " LEFT JOIN PRICE.PRCPT AS PRCPT ON \r\n"+
	// " PRCPT.ENTITYID = CHRGCOMPPRCPT.ENTITY2ID AND \r\n"+
	// " PRCPT.PRCPTID = PRICE.MODELATR \r\n"+
	// " WHERE \r\n"+
	// " PRICE.ACTION = 'I' AND \r\n"+
	// " PRICE.END_DATE='12/31/9999' AND \r\n"+
	// " PRICE.OFFERING_TYPE = 'FEATURE' AND \r\n"+
	// " PRICE.PRICE_POINT_TYPE in ('FEA','RPQ') AND \r\n"+
	// " ( \r\n"+
	// " ( \r\n"+
	// " SVCMOD.ENTITYID IS NOT NULL AND \r\n"+
	// " SVCMODCHRGCOMP.ENTITYID IS NOT NULL AND \r\n"+
	// " CHRGCOMPPRCPT.ENTITYID IS NOT NULL AND \r\n"+
	// " PRCPT.ENTITYID IS NOT NULL \r\n"+
	// " ) OR \r\n"+
	// " ( \r\n"+
	// " MACHTYPE.ENTITYID IS NOT NULL AND \r\n"+
	// " MODEL.ENTITYID IS NOT NULL AND \r\n"+
	// " FEATURE.ENTITYID IS NOT NULL AND \r\n"+
	// " PRODSTRUCT.ENTITYID IS NOT NULL \r\n"+
	// " ) \r\n"+
	// " ) \r\n";

	private static String SQL_SWFEATURE = "  SELECT                                                                       \r\n"
			+ "    PRICE.ID AS ID,                                                            \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n"
			+ "    SWTMF.PDHDOMAIN AS PDHDOMAIN,                                              \r\n"
			+ "    SWTMF.COFCAT AS PRODUCTCATEGORY,                                           \r\n"
			+ "    PRICE.OFFERING_TYPE AS PRODUCTOFFERINGTYPE,                                \r\n"
			+ "    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n"
			+ "    'MODEL' AS PRODUCTENTITYTYPE,                                              \r\n"
			+ "    SWTMF.MDLID AS PRODUCTENTITYID,                                            \r\n"
			+ "    'SWFEATURE' AS PRICEPOINTENTITYTYPE,                                       \r\n"
			+ "    SWTMF.SWFEATID AS PRICEPOINTENTITYID,                                      \r\n"
			+ "    SWTMF.MACHTYPEATR AS MACHTYPEATR,                                          \r\n"
			+ "    SWTMF.MODELATR AS MODELATR,                                                \r\n"
			+ "    SWTMF.FEATURECODE AS FEATURECODE,                                          \r\n"
			+ "    '' AS FROM_MACHTYPEATR,                                                    \r\n"
			+ "    '' AS FROM_MODELATR,                                                       \r\n"
			+ "    '' AS FROM_FEATURECODE,                                                    \r\n"
			+ "    '' AS PARTNUM,                                                             \r\n"
			+ "    SWTMF.SWFEATDESC AS MKTGNAME,                                              \r\n"
			+ "    SWTMF.BHINVNAME AS INVNAME,                                                \r\n"
			+ "    PRICE.OFFERING AS OFFERING,                                                \r\n"
			+ "    PRICE.START_DATE AS START_DATE,                                            \r\n"
			+ "    PRICE.CURRENCY AS CURRENCY,                                                \r\n"
			+ "    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n"
			+ "    PRICE.CABLEID AS CABLEID,                                                  \r\n"
			+ "    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n"
			+ "    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n"
			+ "    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n"
			+ "    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n"
			+ "    PRICE.COUNTRY AS COUNTRY,                                                  \r\n"
			+ "    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n"
			+ "    PRICE.ONSHORE AS ONSHORE,                                                  \r\n"
			+ "    PRICE.END_DATE AS END_DATE,                                                \r\n"
			+ "    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n"
			+ "    PRICE.FACTOR AS FACTOR                                                     \r\n"
			+ "  FROM PRICE.PRICE AS PRICE                                                    \r\n"
			+ "  INNER JOIN PRICE.SWTMF AS SWTMF ON                                           \r\n"
			+ "    PRICE.OFFERING=CONCAT(SWTMF.MACHTYPEATR,SWTMF.MODELATR) AND                \r\n"
			+ "    PRICE.PRICE_POINT_VALUE=SWTMF.FEATURECODE                                  \r\n"
			+ "  WHERE                                                                        \r\n"
			+ "    PRICE.ACTION = 'I' AND                                                     \r\n"
			+ "    PRICE.PRICE_POINT_TYPE in ('SWF','WSF')                                    \r\n";

	private static String SQL_FCTRANSACTION = "  SELECT                                                                       \r\n"
			+ "    PRICE.ID AS ID,                                                            \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n"
			+ "    FCTRANSACTION.PDHDOMAIN AS PDHDOMAIN,                                      \r\n"
			+ "    'Hardware' AS PRODUCTCATEGORY,                                             \r\n"
			+ "    CASE                                                                       \r\n"
			+ "        WHEN PRICE.PRICE_POINT_TYPE='FUP' THEN 'Feature Upgrade'               \r\n"
			+ "        WHEN PRICE.PRICE_POINT_TYPE='TFU' THEN 'Type Feature Upgrade'          \r\n"
			+ "        ELSE ''                                                                \r\n"
			+ "    END AS PRODUCTOFFERINGTYPE,                                                \r\n"
			+ "    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n"
			+ "    'FCTRANSACTION' AS PRODUCTENTITYTYPE,                                      \r\n"
			+ "    FCTRANSACTION.ENTITYID AS PRODUCTENTITYID,                                 \r\n"
			+ "    '' AS PRICEPOINTENTITYTYPE,                                                \r\n"
			+ "    '' AS PRICEPOINTENTITYID,                                                  \r\n"
			+ "    PRICE.MACHTYPEATR AS MACHTYPEATR,                                          \r\n"
			+ "    FCTRANSACTION.TOMODEL AS MODELATR,                                         \r\n"
			+ "    PRICE.FEATURECODE AS FEATURECODE,                                          \r\n"
			+ "    PRICE.FROM_MACHTYPEATR AS FROM_MACHTYPEATR,                                \r\n"
			+ "    FCTRANSACTION.FROMMODEL AS FROM_MODELATR,                                  \r\n"
			+ "    PRICE.FROM_FEATURECODE AS FROM_FEATURECODE,                                \r\n"
			+ "    '' AS PARTNUM,                                                             \r\n"
			+ "    '' AS MKTGNAME,                                                            \r\n"
			+ "    '' AS INVNAME,                                                             \r\n"
			+ "    PRICE.OFFERING AS OFFERING,                                                \r\n"
			+ "    PRICE.START_DATE AS START_DATE,                                            \r\n"
			+ "    PRICE.CURRENCY AS CURRENCY,                                                \r\n"
			+ "    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n"
			+ "    PRICE.CABLEID AS CABLEID,                                                  \r\n"
			+ "    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n"
			+ "    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n"
			+ "    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n"
			+ "    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n"
			+ "    PRICE.COUNTRY AS COUNTRY,                                                  \r\n"
			+ "    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n"
			+ "    PRICE.ONSHORE AS ONSHORE,                                                  \r\n"
			+ "    PRICE.END_DATE AS END_DATE,                                                \r\n"
			+ "    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n"
			+ "    PRICE.FACTOR AS FACTOR                                                     \r\n"
			+ "  FROM PRICE.PRICE AS PRICE                                                    \r\n"
			+ "  INNER JOIN PRICE.FCTRANSACTION AS FCTRANSACTION ON                           \r\n"
			+ "    CAST(PRICE.FROM_MACHTYPEATR AS INTEGER)=  FCTRANSACTION.FROMMACHTYPE AND   \r\n"
			+ "    PRICE.FROM_FEATURECODE = FCTRANSACTION.FROMFEATURECODE AND                 \r\n"
			+ "    CAST(PRICE.MACHTYPEATR AS INTEGER) = FCTRANSACTION.TOMACHTYPE AND          \r\n"
			+ "    PRICE.FEATURECODE = FCTRANSACTION.TOFEATURECODE                            \r\n"
			+ "  WHERE                                                                        \r\n"
			+ "    PRICE.ACTION = 'I' AND                                                     \r\n"
			+ "    PRICE.OFFERING_TYPE = 'FCTRANSACTION' AND                                  \r\n"
			+ "    PRICE.PRICE_POINT_TYPE in('FUP','TFU')                                     \r\n";

	private static String SQL_MODELCONVERT = "  SELECT                                                                       \r\n"
			+ "    PRICE.ID AS ID,                                                            \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n"
			+ "    MODELCONVERT.PDHDOMAIN AS PDHDOMAIN,                                       \r\n"
			+ "    'Hardware' AS PRODUCTCATEGORY,                                             \r\n"
			+ "    CASE                                                                       \r\n"
			+ "        WHEN PRICE.PRICE_POINT_TYPE='MUP' THEN 'Model Upgrade'                 \r\n"
			+ "        WHEN PRICE.PRICE_POINT_TYPE='TMU' THEN 'Type Model Upgrade'            \r\n"
			+ "        ELSE ''                                                                \r\n"
			+ "    END AS PRODUCTOFFERINGTYPE,                                                \r\n"
			+ "    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n"
			+ "                                                                               \r\n"
			+ "    'MODELCONVERT' AS PRODUCTENTITYTYPE,                                       \r\n"
			+ "    MODELCONVERT.ENTITYID AS PRODUCTENTITYID,                                  \r\n"
			+ "    '' AS PRICEPOINTENTITYTYPE,                                                \r\n"
			+ "    '' AS PRICEPOINTENTITYID,                                                  \r\n"
			+ "                                                                               \r\n"
			+ "    PRICE.MACHTYPEATR AS MACHTYPEATR,                                          \r\n"
			+ "    PRICE.MODELATR AS MODELATR,                                                \r\n"
			+ "    '' AS FEATURECODE,                                                         \r\n"
			+ "    PRICE.FROM_MACHTYPEATR AS FROM_MACHTYPEATR,                                \r\n"
			+ "    PRICE.FROM_MODELATR AS FROM_MODELATR,                                      \r\n"
			+ "    '' AS FROM_FEATURECODE,                                                    \r\n"
			+ "    '' AS PARTNUM,                                                             \r\n"
			+ "    '' AS MKTGNAME,                                                            \r\n"
			+ "    '' AS INVNAME,                                                             \r\n"
			+ "    PRICE.OFFERING AS OFFERING,                                                \r\n"
			+ "    PRICE.START_DATE AS START_DATE,                                            \r\n"
			+ "    PRICE.CURRENCY AS CURRENCY,                                                \r\n"
			+ "    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n"
			+ "    PRICE.CABLEID AS CABLEID,                                                  \r\n"
			+ "    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n"
			+ "    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n"
			+ "    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n"
			+ "    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n"
			+ "    PRICE.COUNTRY AS COUNTRY,                                                  \r\n"
			+ "    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n"
			+ "    PRICE.ONSHORE AS ONSHORE,                                                  \r\n"
			+ "    PRICE.END_DATE AS END_DATE,                                                \r\n"
			+ "    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n"
			+ "    PRICE.FACTOR AS FACTOR                                                     \r\n"
			+ "  FROM PRICE.PRICE AS PRICE                                                    \r\n"
			+ "  INNER JOIN PRICE.MODELCONVERT AS MODELCONVERT ON                             \r\n"
			+ "    CAST(PRICE.FROM_MACHTYPEATR AS INTEGER)=  MODELCONVERT.FROMMACHTYPE AND    \r\n"
			+ "    PRICE.FROM_MODELATR = MODELCONVERT.FROMMODEL AND                           \r\n"
			+ "    CAST(PRICE.MACHTYPEATR AS INTEGER) = MODELCONVERT.TOMACHTYPE AND           \r\n"
			+ "    PRICE.MODELATR = MODELCONVERT.TOMODEL                                      \r\n"
			+ "  WHERE                                                                        \r\n"
			+ "    PRICE.ACTION = 'I' AND                                                     \r\n"
			+ "    PRICE.OFFERING_TYPE = 'MODELCONVERT' AND                                   \r\n"
			+ "    PRICE.PRICE_POINT_TYPE in ('MUP','TMU')                                    \r\n";

	private static String SQL_LSEO = "  SELECT                                                                       \r\n"
			+ "    PRICE.ID AS ID,                                                            \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n"
			+ "    LSEO.PDHDOMAIN AS PDHDOMAIN,                                               \r\n"
			+ "    LSEO.COFCAT AS PRODUCTCATEGORY,                                            \r\n"
			+ "    LSEO.SEOTYPE AS PRODUCTOFFERINGTYPE,                                       \r\n"
			+ "    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n"
			+ "    LSEO.SEOTYPE AS PRODUCTENTITYTYPE,                                         \r\n"
			+ "    LSEO.ENTITYID AS PRODUCTENTITYID,                                          \r\n"
			+ "    '' AS PRICEPOINTENTITYTYPE,                                                \r\n"
			+ "    '' AS PRICEPOINTENTITYID,                                                  \r\n"
			+ "    LSEO.MACHTYPEATR AS MACHTYPEATR,                                           \r\n"
			+ "    LSEO.MODELATR AS MODELATR,                                                 \r\n"
			+ "    '' AS FEATURECODE,                                                         \r\n"
			+ "    '' AS FROM_MACHTYPEATR,                                                    \r\n"
			+ "    '' AS FROM_MODELATR,                                                       \r\n"
			+ "    '' AS FROM_FEATURECODE,                                                    \r\n"
			+ "    LSEO.SEOID AS PARTNUM,                                                     \r\n"
			+ "    LSEO.MKTGDESC AS MKTGNAME,                                                 \r\n"
			+ "    LSEO.PRCFILENAM AS INVNAME,                                                \r\n"
			+ "    PRICE.OFFERING AS OFFERING,                                                \r\n"
			+ "    PRICE.START_DATE AS START_DATE,                                            \r\n"
			+ "    PRICE.CURRENCY AS CURRENCY,                                                \r\n"
			+ "    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n"
			+ "    PRICE.CABLEID AS CABLEID,                                                  \r\n"
			+ "    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n"
			+ "    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n"
			+ "    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n"
			+ "    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n"
			+ "    PRICE.COUNTRY AS COUNTRY,                                                  \r\n"
			+ "    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n"
			+ "    PRICE.ONSHORE AS ONSHORE,                                                  \r\n"
			+ "    PRICE.END_DATE AS END_DATE,                                                \r\n"
			+ "    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n"
			+ "    PRICE.FACTOR AS FACTOR                                                     \r\n"
			+ "  FROM PRICE.PRICE AS PRICE                                                    \r\n"
			+ "  JOIN PRICE.LSEOINFO AS LSEO                                                  \r\n"
			+ "  ON PRICE.PARTNUM=LSEO.SEOID                                                  \r\n"
			+ "  WHERE                                                                        \r\n"
			+ "    PRICE.ACTION = 'I' AND                                                     \r\n"
			+ "    PRICE.PRICE_POINT_TYPE in('','SEO')                                        \r\n";

	// RCQ00206104 WI- EACM - LA prices to support the LA CBS project and WW ESW
	// prices
	private static String SQL_ESW = "  SELECT                                                                       \r\n"
			+ "    PRICE.ID AS ID,                                                            \r\n"
			+ "    PRICE.INSERT_TS AS INSERT_TS,                                              \r\n"
			+ "    '' AS PDHDOMAIN,                                                           \r\n"
			+ "    PRICE.OFFERING_TYPE AS PRODUCTCATEGORY,                                    \r\n"
			+ "    '' AS PRODUCTOFFERINGTYPE,                                                 \r\n"
			+ "    PRICE.OFFERING_TYPE AS OFFERING_TYPE,                                      \r\n"
			+ "                                                                               \r\n"
			+ "    '' AS PRODUCTENTITYTYPE,                                                   \r\n"
			+ "    '' AS PRODUCTENTITYID,                                                     \r\n"
			+ "    '' AS PRICEPOINTENTITYTYPE,                                                \r\n"
			+ "    '' AS PRICEPOINTENTITYID,                                                  \r\n"
			+ "                                                                               \r\n"
			+ "    '' AS MACHTYPEATR,                                                         \r\n"
			+ "    '' AS MODELATR,                                                            \r\n"
			+ "    '' AS FEATURECODE,                                                         \r\n"
			+ "    '' AS FROM_MACHTYPEATR,                                                    \r\n"
			+ "    '' AS FROM_MODELATR,                                                       \r\n"
			+ "    '' AS FROM_FEATURECODE,                                                    \r\n"
			+ "    '' AS PARTNUM,                                                             \r\n"
			+ "    '' AS MKTGNAME,                                                            \r\n"
			+ "    '' AS INVNAME,                                                             \r\n"
			+ "    PRICE.OFFERING AS OFFERING,                                                \r\n"
			+ "    PRICE.START_DATE AS START_DATE,                                            \r\n"
			+ "    PRICE.CURRENCY AS CURRENCY,                                                \r\n"
			+ "    PRICE.CABLETYPE AS CABLETYPE,                                              \r\n"
			+ "    PRICE.CABLEID AS CABLEID,                                                  \r\n"
			+ "    PRICE.RELEASE_TS AS RELEASE_TS,                                            \r\n"
			+ "    PRICE.PRICE_VALUE AS PRICE_VALUE,                                          \r\n"
			+ "    PRICE.PRICE_POINT_TYPE AS PRICE_POINT_TYPE,                                \r\n"
			+ "    PRICE.PRICE_POINT_VALUE AS PRICE_POINT_VALUE,                              \r\n"
			+ "    PRICE.COUNTRY AS COUNTRY,                                                  \r\n"
			+ "    PRICE.PRICE_TYPE AS PRICE_TYPE,                                            \r\n"
			+ "    PRICE.ONSHORE AS ONSHORE,                                                  \r\n"
			+ "    PRICE.END_DATE AS END_DATE,                                                \r\n"
			+ "    PRICE.PRICE_VALUE_USD AS PRICE_VALUE_USD,                                  \r\n"
			+ "    PRICE.FACTOR AS FACTOR                                                     \r\n"
			+ "  FROM PRICE.PRICE AS PRICE                                                    \r\n"
			+ "  WHERE                                                                        \r\n"
			+ "    PRICE.ACTION = 'I' AND                                                     \r\n"
			+ "    PRICE.OFFERING_TYPE in ('EEE','OSP') AND                                   \r\n"
			+ "    PRICE.PRICE_POINT_TYPE =''                                                 \r\n";

	private static class PriceInfo {
		String PRODUCTOFFERINGTYPE = XMLElem.CHEAT;
		String MACHTYPE = XMLElem.CHEAT;
		String MODEL = XMLElem.CHEAT;
		String FEATURECODE = XMLElem.CHEAT;
		String FROMMACHTYPE = XMLElem.CHEAT;

		String FROMMODEL = XMLElem.CHEAT;
		String FROMFEATURECODE = XMLElem.CHEAT;
		String SEOID = XMLElem.CHEAT;
		String OFFERING = XMLElem.CHEAT;
		String STARTDATE = XMLElem.CHEAT;

		String CURRENCY = XMLElem.CHEAT;
		String CABLETYPE = XMLElem.CHEAT;
		String CABLEID = XMLElem.CHEAT;
		String RELEASETS = XMLElem.CHEAT;
		String PRICEVALUE = XMLElem.CHEAT;

		String PRICEPOINTTYPE = XMLElem.CHEAT;
		String PRICEPOINTVALUE = XMLElem.CHEAT;
		String COUNTRY = XMLElem.CHEAT;
		String PRICETYPE = XMLElem.CHEAT;
		String ONSHORE = XMLElem.CHEAT;

		String ENDDATE = XMLElem.CHEAT;
		String PRICEVALUEUSD = XMLElem.CHEAT;
		String FACTOR = XMLElem.CHEAT;
		String ACTIVITY = XMLElem.CHEAT;
		String PDHDOMAIN = XMLElem.CHEAT;

		String PRODUCTCATEGORY = XMLElem.CHEAT;
		String PRODUCTENTITYTYPE = XMLElem.CHEAT;
		String PRODUCTENTITYID = XMLElem.CHEAT;
		String PRICEPOINTENTITYTYPE = XMLElem.CHEAT;
		String PRICEPOINTENTITYID = XMLElem.CHEAT;

		String MKTGNAME = XMLElem.CHEAT;
		String INVNAME = XMLElem.CHEAT;
		// Vector HISTORYPRICE = null;

		PriceInfo(String PRODUCTOFFERINGTYPE, String MACHTYPE, String MODEL, String FEATURECODE, String FROMMACHTYPE,
				String FROMMODEL, String FROMFEATURECODE, String SEOID, String OFFERING, String STARTDATE,
				String CURRENCY, String CABLETYPE, String CABLEID, String RELEASETS, String PRICEVALUE,
				String PRICEPOINTTYPE, String PRICEPOINTVALUE, String COUNTRY, String PRICETYPE, String ONSHORE,
				String ENDDATE, String PRICEVALUEUSD, String FACTOR, String ACTIVITY, String PDHDOMAIN,
				String PRODUCTCATEGORY, String PRODUCTENTITYTYPE, String PRODUCTENTITYID, String PRICEPOINTENTITYTYPE,
				String PRICEPOINTENTITYID, String MKTGNAME, String INVNAME, Vector historyPrice) {
			//
			if (PRODUCTOFFERINGTYPE != null) {
				this.PRODUCTOFFERINGTYPE = PRODUCTOFFERINGTYPE.trim();
			}
			if (MACHTYPE != null) {
				this.MACHTYPE = MACHTYPE.trim();
			}
			if (MODEL != null) {
				this.MODEL = MODEL.trim();
			}
			if (FEATURECODE != null) {
				this.FEATURECODE = FEATURECODE.trim();
			}
			if (FROMMACHTYPE != null) {
				this.FROMMACHTYPE = FROMMACHTYPE.trim();
			}

			if (FROMMODEL != null) {
				this.FROMMODEL = FROMMODEL.trim();
			}
			if (FROMFEATURECODE != null) {
				this.FROMFEATURECODE = FROMFEATURECODE.trim();
			}
			if (SEOID != null) {
				this.SEOID = SEOID.trim();
			}
			if (OFFERING != null) {
				this.OFFERING = OFFERING.trim();
			}
			if (STARTDATE != null) {
				this.STARTDATE = STARTDATE.trim();
			}

			if (CURRENCY != null) {
				this.CURRENCY = CURRENCY.trim();
			}
			if (CABLETYPE != null) {
				this.CABLETYPE = CABLETYPE.trim();
			}
			if (CABLEID != null) {
				this.CABLEID = CABLEID.trim();
			}
			if (RELEASETS != null) {
				this.RELEASETS = RELEASETS.trim();
			}
			if (PRICEVALUE != null) {
				this.PRICEVALUE = PRICEVALUE.trim();
			}

			if (PRICEPOINTTYPE != null) {
				this.PRICEPOINTTYPE = PRICEPOINTTYPE.trim();
			}
			if (PRICEPOINTVALUE != null) {
				this.PRICEPOINTVALUE = PRICEPOINTVALUE.trim();
			}
			if (COUNTRY != null) {
				this.COUNTRY = COUNTRY.trim();
			}
			if (PRICETYPE != null) {
				this.PRICETYPE = PRICETYPE.trim();
			}
			if (ONSHORE != null) {
				this.ONSHORE = ONSHORE.trim();
			}

			if (ENDDATE != null) {
				this.ENDDATE = ENDDATE.trim();
			}
			if (PRICEVALUEUSD != null) {
				this.PRICEVALUEUSD = PRICEVALUEUSD.trim();
			}
			if (FACTOR != null) {
				this.FACTOR = FACTOR.trim();
			}
			if (ACTIVITY != null) {
				this.ACTIVITY = ACTIVITY.trim();
			}
			if (PDHDOMAIN != null) {
				this.PDHDOMAIN = PDHDOMAIN.trim();
			}

			if (PRODUCTCATEGORY != null) {
				this.PRODUCTCATEGORY = PRODUCTCATEGORY.trim();
			}
			if (PRODUCTENTITYTYPE != null) {
				this.PRODUCTENTITYTYPE = PRODUCTENTITYTYPE.trim();
			}
			if (PRODUCTENTITYID != null) {
				this.PRODUCTENTITYID = PRODUCTENTITYID.trim();
			}
			if (PRICEPOINTENTITYTYPE != null) {
				this.PRICEPOINTENTITYTYPE = PRICEPOINTENTITYTYPE.trim();
			}
			if (PRICEPOINTENTITYID != null) {
				this.PRICEPOINTENTITYID = PRICEPOINTENTITYID.trim();
			}

			if (MKTGNAME != null) {
				this.MKTGNAME = MKTGNAME.trim();
			}
			if (INVNAME != null) {
				this.INVNAME = INVNAME.trim();
			}
			// if (historyPrice!= null){ this.HISTORYPRICE = historyPrice;}
		}

		void dereference() {
			PRODUCTOFFERINGTYPE = null;
			MACHTYPE = null;
			MODEL = null;
			FEATURECODE = null;
			FROMMACHTYPE = null;

			FROMMODEL = null;
			FROMFEATURECODE = null;
			SEOID = null;
			OFFERING = null;
			STARTDATE = null;

			CURRENCY = null;
			CABLETYPE = null;
			CABLEID = null;
			RELEASETS = null;
			PRICEVALUE = null;

			PRICEPOINTTYPE = null;
			PRICEPOINTVALUE = null;
			COUNTRY = null;
			PRICETYPE = null;
			ONSHORE = null;

			ENDDATE = null;
			PRICEVALUEUSD = null;
			FACTOR = null;
			ACTIVITY = null;
			PDHDOMAIN = null;

			PRODUCTCATEGORY = null;
			PRODUCTENTITYTYPE = null;
			PRODUCTENTITYID = null;
			PRICEPOINTENTITYTYPE = null;
			PRICEPOINTENTITYID = null;

			MKTGNAME = null;
			INVNAME = null;
			// HISTORYPRICE = null;

		}
	}

	private boolean isValidCond(String attr) {
		return attr != null && attr.trim().length() > 0;
	}

	public boolean maxLimited() {
		return max > -1;
	}
}
