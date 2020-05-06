//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2006, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.util;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

/**********************************************************************************
* This class contains utility methods used by Withdrawn Summary report and Launch Summary
* Report. It is used to find entityitems announced or withdrawn in a date range.
*/
//$Log: WDLaunchSummary.java,v $
//Revision 1.4  2008/10/22 12:42:22  wendy
//JAWS and CI162 updates
//
//Revision 1.3  2006/04/07 14:45:16  sergio
//Make th id unique for webking
//
//Revision 1.2  2006/01/26 14:32:04  couto
//Added AHE copyright info.
//
//Revision 1.1  2006/01/25 14:46:37  couto
//Initial OIM 3.0b version.
//
//

public class WDLaunchSummary {

	/**
	 * object copyright statement
	 */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006, 2006  All Rights Reserved.";

	/**
	 * cvs revision number
	 */
	public static final String VERSION = "$Revision: 1.4 $";

	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	private static final String NO_DATA = "---";
	private static final int MAX_ROWS = 60; // max rows per table
	private static final String WD_LAUNCH_MODELS = "1";
	private static final String LSEO_WD_ATTR = "LSEOUNPUBDATEMTRGT";
	private static final String LSEOBUNDLE_WD_ATTR = "BUNDLUNPUBDATEMTRGT";

	/**
	 * Database to be used
	 */
	private Database dbCurrent;

	/**
	 * Profile
	 */
	private Profile profile;

	/**
	 * Beginning of date range
	 */
	private String fromDate;

	/**
	 * End of date range
	 */
	private String toDate;

	/**
	 * Vector with WWSEO Items
	 */
	private Vector wwseoVct = new Vector(1);

	/**
	 * Vector with LSEO Items
	 */
	private Vector lseoVct = new Vector(1);

	/**
	 * EntityList
	 */
	private EntityList list = null;

	/**
	 * Constructor
	 * @param  dbCur	Database
	 * @param  prof		Profile
	 * @param  from		String
	 * @param  to		String
	 */
	public WDLaunchSummary(Database dbCur, Profile prof, String from, String to) {
		dbCurrent = dbCur;
		profile = prof;
		fromDate = from;
		toDate = to;
	}

	/**
	 * Used do release memory
	 */
	public void dereference() {
		dbCurrent = null;
		profile = null;
		fromDate = null;
		toDate = null;
		if (list != null) {
			list.dereference();
		}
		wwseoVct.clear();
		lseoVct.clear();
	}

	/********************************************************************************
	* Build all the data for the MODEL
	*
	*@param  attrCode		Attribute code to check the date
	*@param  extractName	Extract Name to be used
	*@param  bundle     	ResourceBundle
	*@param  out			Output writer
	*@throws MiddlewareRequestException
	*@throws MiddlewareException
	*@throws MiddlewareShutdownInProgressException
	*@throws SQLException
	*@throws IOException
	*/
	public void getWDLaunchModelData(String attrCode, String extractName, ResourceBundle bundle,
		javax.servlet.jsp.JspWriter out)
		throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException,
		IOException
	{

		Vector modelVct = new Vector(1);
		RootComparator rootComparator = new RootComparator();

		//Get all models for this WG
	  	EntityList modelList = EntityList.getEntityList(dbCurrent, profile,
	  		new NavActionItem(null, dbCurrent, profile, "NAVWGMODEL"),
	  		new EntityItem[] { new EntityItem(null, profile, "WG", profile.getWGID()) });

		//Get all attributes for these models
		EntityGroup modelGrp = modelList.getEntityGroup("MODEL");
		EntityList dummyList = dbCurrent.getEntityList(profile,
			new ExtractActionItem(null, dbCurrent, profile, "DUMMY"), modelGrp.getEntityItemsAsArray());

		out.println("<!-- EntityList for NAVWGMODEL contains the following entities: ");
		out.println(PokUtils.outputList(modelList));
		out.println("-->");
		out.flush();

		//Get models that meet the date range for this attribute
	  	modelGrp = dummyList.getParentEntityGroup();
	  	for (int counter = 0; counter < modelGrp.getEntityItemCount(); counter++) {
		  	EntityItem model = modelGrp.getEntityItem(counter);
		  	if (checkModelWDLaunch(model, attrCode, out)) {
			  	modelVct.addElement(model);
		  	}
	  	}
		Collections.sort(modelVct, rootComparator); //sort on MACHTYPEATR and MODELATR

		//build output for Model
		if (modelVct.size() > 0) {
			EntityItem[] eiArray = new EntityItem[modelVct.size()];
			modelVct.copyInto(eiArray);
			list = dbCurrent.getEntityList(profile,
				new ExtractActionItem(null, dbCurrent, profile, extractName), eiArray);

			out.println("<!-- EntityList for "+extractName+" contains the following entities: ");
			out.println(PokUtils.outputList(list));
			out.println("-->");

			modelGrp = list.getParentEntityGroup();
			for (int counter = 0; counter < modelGrp.getEntityItemCount(); counter++) {
				EntityItem model = modelGrp.getEntityItem(counter);
				//output model info
				out.println("<h3 title='"+model.getKey()+"'>" + PokUtils.getAttributeValue(model, "MACHTYPEATR", "", "") + "-" +
					PokUtils.getAttributeValue(model, "MODELATR", "", "") + "</h3>");
				wwseoVct = PokUtils.getAllLinkedEntities(model, "MODELWWSEO", "WWSEO");
				Collections.sort(wwseoVct, rootComparator); //sort on COMNAME
				out.println(getWWSEOData(wwseoVct, list.getEntityGroup("WWSEO"), bundle,
					PokUtils.getAttributeValue(model, "MACHTYPEATR", "", "") + "-" +
					PokUtils.getAttributeValue(model, "MODELATR", "", "")+counter));
				out.println("<br />");
				out.flush();
				out.println(getWDLaunchLSEOData(WD_LAUNCH_MODELS, null, bundle,
					PokUtils.getAttributeValue(model, "MACHTYPEATR", "", "") + "-" +
					PokUtils.getAttributeValue(model, "MODELATR", "", "")+counter));
				out.println("<br />");
				out.flush();
				out.println(getWDLaunchLSEOBundleData(WD_LAUNCH_MODELS, null, null, bundle,
					PokUtils.getAttributeValue(model, "MACHTYPEATR", "", "") + "-" +
					PokUtils.getAttributeValue(model, "MODELATR", "", "")+counter));
				out.println("<br />");
				out.flush();
			}
			for (int ie = 0; ie < eiArray.length; ie++) {
				eiArray[ie] = null;
			}
		}else{
			out.println("<p>" + bundle.getString("Text_NoDataFound") + "</p>");
		}

		//release memory
		modelList.dereference();
		dummyList.dereference();
		modelVct.clear();
	} //end of getWDLaunchWWSEOData method

	/********************************************************************************
	* Build the LSEO table data
	*
	*@param  displayOptions String used to get the display option for the report (models or lseo/lseobundle)
	*@param  attrCode		Attribute code to check the date
	*@param  bundle     	ResourceBundle
	*@param  idTitle		used for uniqued id tags
	*@return String with data for the report
	*@throws MiddlewareRequestException
	*@throws MiddlewareException
	*@throws MiddlewareShutdownInProgressException
	*@throws SQLException
	*/
	public String getWDLaunchLSEOData(String displayOptions, String attrCode, ResourceBundle bundle, String idTitle)
		throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException
	{

		StringBuffer sb = new StringBuffer();
		String caption = "";
		String thHeader = "";
		EntityList lseoList = null;
		EntityGroup lseoGrp;
		RootComparator rootComparator = new RootComparator();
		sb.append("<table summary=\"LSEO Info\" width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\">" + NEWLINE);

		if (WD_LAUNCH_MODELS.equals(displayOptions)) { //Models
			caption = "<caption class=\"orange-med\">" + bundle.getString("Label_AffectedLSEOs") + "</caption>" + NEWLINE;
			sb.append(caption);
			lseoGrp = list.getEntityGroup("LSEO");
			lseoVct = PokUtils.getAllLinkedEntities(wwseoVct, "WWSEOLSEO", "LSEO");
		}
		else { //LSEO's and LSEO Bundles
			if (LSEO_WD_ATTR.equals(attrCode)) { //Withdrawn
				caption = "<caption class=\"orange-med\">" + bundle.getString("Label_WithdrawnLSEOs") + "</caption>" + NEWLINE;
			}
			else { //Launch
				caption = "<caption class=\"orange-med\">" + bundle.getString("Label_AnnouncedLSEOs") + "</caption>" + NEWLINE;
			}
			sb.append(caption);
			lseoList = getLSEOEntities();
			lseoGrp = lseoList.getEntityGroup("LSEO");
			for (int counter = 0; counter < lseoGrp.getEntityItemCount(); counter++) {
				EntityItem lseo = lseoGrp.getEntityItem(counter);
				if (checkLSEOWDLaunch(lseo, attrCode, sb)) {
					lseoVct.addElement(lseo);
				}
			}
		}
		sb.append("<colgroup><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/></colgroup>" + NEWLINE); //moved line to avoid webking error
		Collections.sort(lseoVct, rootComparator); //sort on COMNAME
		String uniqueID = idTitle + "LSEO";

		if (lseoVct.size() > 0) {
			thHeader = getLSEOHeader(lseoGrp, "th", uniqueID);
			sb.append(thHeader);
			for (int counter = 0; counter < lseoVct.size(); counter++) {
				EntityItem lseo = (EntityItem) lseoVct.elementAt(counter);
				if ((counter % MAX_ROWS == 0) && (counter != 0)) {
					//Close the table and open a new one
					sb.append("</table>" + NEWLINE);
					sb.append("<br />" + NEWLINE);  // separate the tables
					sb.append("<table summary=\"LSEO Info\" width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\">" + NEWLINE);
					sb.append(caption);
					sb.append("<colgroup><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/></colgroup>" + NEWLINE);
					uniqueID = idTitle + "LSEO"+counter;
					thHeader = getLSEOHeader(lseoGrp, "th", uniqueID);
					sb.append(thHeader);
				}
				sb.append(getLSEOInfo(lseo, counter, uniqueID));
			}
		}
		else {
			sb.append(getLSEOHeader(lseoGrp, "td" , uniqueID));
			sb.append("<tr><td colspan=\"4\">" + bundle.getString("Text_NoDataFound") + "</td></tr>" + NEWLINE);
		}
		sb.append("</table>" + NEWLINE);

		if (lseoList != null) { //release memory
			lseoList.dereference();
		}

		return sb.toString();
	} //end of getWDLaunchLSEOData method

	/********************************************************************************
	* Build the LSEOBundle table data
	*
	*@param  displayOptions String used to get the display option for the report (models or lseo/lseobundle)
	*@param  attrCode		Attribute code to check the date
	*@param  extractName	Extract Name to be used
	*@param  bundle     	ResourceBundle
	*@param  idTitle		used for uniqued id tags
	*@return String with data for the report
 	*@throws MiddlewareRequestException
	*@throws MiddlewareException
	*@throws MiddlewareShutdownInProgressException
	*@throws SQLException
	*/
	public String getWDLaunchLSEOBundleData(String displayOptions, String attrCode, String extractName,
		ResourceBundle bundle, String idTitle)
		throws MiddlewareRequestException, MiddlewareException, MiddlewareShutdownInProgressException, SQLException
	{

		StringBuffer sb = new StringBuffer();
		String caption = "";
		Vector lseoBundleVct = new Vector();
		String thHeader = "";
		EntityList lseoBundleList = null;
		EntityGroup lseoBundleGrp;
		RootComparator rootComparator = new RootComparator();
		sb.append("<table summary=\"LSEO Bundle Info\" width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\">" + NEWLINE);

		if (WD_LAUNCH_MODELS.equals(displayOptions)) { //Models
			caption = "<caption class=\"orange-med\">" + bundle.getString("Label_AffectedLSEOBundles") + "</caption>" + NEWLINE;
			sb.append(caption);
			lseoBundleGrp = list.getEntityGroup("LSEOBUNDLE");
			lseoBundleVct = PokUtils.getAllLinkedEntities(lseoVct, "LSEOBUNDLELSEO", "LSEOBUNDLE");
		}
		else { //LSEO's and LSEO Bundles
			lseoBundleList = getLSEOBundleEntities(extractName);
			if (LSEOBUNDLE_WD_ATTR.equals(attrCode)) { //Withdrawn
				caption = "<caption class=\"orange-med\">" + bundle.getString("Label_WithdrawnLSEOBundles") + "</caption>" + NEWLINE;
			}
			else { //Launch
				caption = "<caption class=\"orange-med\">" + bundle.getString("Label_AnnouncedLSEOBundles") + "</caption>" + NEWLINE;
			}
			sb.append(caption);
			lseoBundleGrp = lseoBundleList.getEntityGroup("LSEOBUNDLE");
			for (int counter = 0; counter < lseoBundleGrp.getEntityItemCount(); counter++) {
				EntityItem lseoBundle = lseoBundleGrp.getEntityItem(counter);
				if (checkLSEOWDLaunch(lseoBundle, attrCode, sb)) {
					lseoBundleVct.addElement(lseoBundle);
				}
			}
		}
		sb.append("<colgroup><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/></colgroup>" + NEWLINE);
		Collections.sort(lseoBundleVct, rootComparator); //sort on COMNAME
		String uniqueID = idTitle + "LSEOBUNDLE";

		if (lseoBundleVct.size() > 0) {
			thHeader = getLSEOBundleHeader(lseoBundleGrp, "th", uniqueID);
			sb.append(thHeader);
			for (int counter = 0; counter < lseoBundleVct.size(); counter++) {
				EntityItem lseoBundle = (EntityItem) lseoBundleVct.elementAt(counter);
				if ((counter % MAX_ROWS == 0) && (counter != 0)) {
					//Close the table and open a new one
					sb.append("</table>" + NEWLINE);
					sb.append("<br />" + NEWLINE);  // separate the tables
					sb.append("<table summary=\"LSEO Bundle Info\" width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\">" + NEWLINE);
					sb.append(caption);
					sb.append("<colgroup><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/></colgroup>" + NEWLINE);
					uniqueID = idTitle + "LSEOBUNDLE"+counter;
					thHeader = getLSEOBundleHeader(lseoBundleGrp, "th", uniqueID);
					sb.append(thHeader);
				}
				sb.append(getLSEOBundleInfo(lseoBundle, counter, uniqueID));
			}
		}
		else {
			sb.append(getLSEOBundleHeader(lseoBundleGrp, "td", uniqueID));
			sb.append("<tr><td colspan=\"4\">" + bundle.getString("Text_NoDataFound") + "</td></tr>" + NEWLINE);
		}
		sb.append("</table>" + NEWLINE);

		if (lseoBundleList != null) {//		release memory
			lseoBundleList.dereference();
		}

		return sb.toString();
	} //end of getWDLaunchLSEOBundleData method

	/*************************************************************************************
	* Get all LSEO entities
	*
	*@return EntityList of LSEO EntityItems from search
	*/
	private EntityList getLSEOEntities() throws MiddlewareRequestException, SQLException, MiddlewareException
	{
		EntityList lseoList = null;
		// do a search to find all LSEO
		String actionName="SRDLSEO";
		SearchActionItem saitem = new SearchActionItem(null, dbCurrent, profile, actionName);
		saitem.setCheckLimit(false);  // allow more than 2500
		lseoList = saitem.executeAction(dbCurrent, profile);

		return lseoList;
	} //end getLSEOEntities method

	/*************************************************************************************
	* Get all LSEOBundle entities
	*
	*@param  extractName	Extract Name to be used
	*@return EntityList of LSEOBundle EntityItems from search
	*/
	private EntityList getLSEOBundleEntities(String extractName) throws MiddlewareRequestException, SQLException,
		MiddlewareException
	{
		EntityList lseoBundleList = dbCurrent.getEntityList(profile,
			new ExtractActionItem(null, dbCurrent, profile, extractName),
			new EntityItem[] { new EntityItem(null, profile, "WG", profile.getWGID()) });

		return lseoBundleList;
	} //end getLSEOBundleEntities method

	/********************************************************************************
	* Checks if a Model was withdrawn or launched between the input dates
	*
	*@param  model      EntityItem
	*@param	 attrCode	Attribute Code
	*@param  out		output writer
	*@return true or false
	*@throws IOException
	*/
	private boolean checkModelWDLaunch(EntityItem model, String attrCode, javax.servlet.jsp.JspWriter out)
		throws IOException {

		boolean check = false;
		String date = PokUtils.getAttributeValue(model, attrCode, "", null);
		// verify date range
		if (date != null) {
			if ((date.compareTo(fromDate)>=0) && (date.compareTo(toDate)<=0)) {
				check = true;
			}
			else{
				out.println("<!-- "+model.getKey()+" did not meet date range. "+attrCode+" was: "+date+" -->");
			}
		}
		else{
			out.println("<!-- "+model.getKey()+" "+attrCode+" was null -->");
		}

		return check;
	} //end checkModelWDLaunch method

	/********************************************************************************
	* Checks if a LSEO or LSEOBundle is withdrawal or launch
	*
	*@param  item		EntityItem
	*@param	 attrCode	Attribute Code
	*@param  sb			StringBuffer
	*@return true or false
	*/
	private boolean checkLSEOWDLaunch(EntityItem item, String attrCode, StringBuffer sb) {

		boolean check = false;
		String date = PokUtils.getAttributeValue(item, attrCode, "", null);
		// verify date range
		if (date != null) {
			if ((date.compareTo(fromDate)>=0) && (date.compareTo(toDate)<=0)) {
				check = true;
			}
			else{
				sb.append("<!-- "+item.getKey()+" did not meet date range. "+attrCode+" was: "+date+" -->" + NEWLINE);
			}
		}
		else{
			sb.append("<!-- "+item.getKey()+" "+attrCode+" was null -->" + NEWLINE);
		}

		return check;
	} //end checkLSEOWDLaunch method

	/********************************************************************************
	* Build the WWSEO table data
	*
	*@param  wwseoVt		Vector with wwseo data
	*@param  wwseoGrp		Entity Group used to get the header
	*@param  bundle     	ResourceBundle
	*@return String with data for the report
	*/
	private String getWWSEOData(Vector wwseoVt, EntityGroup wwseoGrp, ResourceBundle bundle, String idTitle)
	{
		StringBuffer sb = new StringBuffer();
		String uniqueID = idTitle + "WWSEOInfo";
		String thHeader = getWWSEOHeader(wwseoGrp, "th", uniqueID );
		//getting WWSEO header
		sb.append("<table summary=\"WWSEO Info\" width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\">" + NEWLINE);
		sb.append("<caption class=\"orange-med\">" + bundle.getString("Label_AffectedWWSEOs") + "</caption>" + NEWLINE);
		sb.append("<colgroup><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/></colgroup>" + NEWLINE);

		if (wwseoVt.size() > 0) {
			sb.append(thHeader);
			for (int counter = 0; counter < wwseoVt.size(); counter++) {
				EntityItem wwseo = (EntityItem) wwseoVt.elementAt(counter);
				if ((counter % MAX_ROWS == 0) && (counter != 0)) {
					//Close the table and open a new one
					sb.append("</table>" + NEWLINE);
					sb.append("<br />" + NEWLINE);  // separate the tables
					sb.append("<table summary=\"WWSEO Info\" width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\">" + NEWLINE);
					sb.append("<caption class=\"orange-med\">" + bundle.getString("Label_AffectedWWSEOs") + "</caption>" + NEWLINE);
					sb.append("<colgroup><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/><col width=\"25%\"/></colgroup>" + NEWLINE);
					uniqueID = idTitle + "WWSEOInfo"+counter;
					thHeader = getWWSEOHeader(wwseoGrp, "th", uniqueID );
					sb.append(thHeader);
				}
				sb.append(getWWSEOInfo(wwseo, counter, uniqueID));
			}
		}
		else {
			sb.append(getWWSEOHeader(wwseoGrp, "td", uniqueID ));
			sb.append("<tr><td colspan=\"4\">" + bundle.getString("Text_NoDataFound") + "</td></tr>" + NEWLINE);
		}
		sb.append("</table>" + NEWLINE);

		return sb.toString();
	} //end getWWSEOData method

	/********************************************************************************
	* Build the WWSEO Header for the output table
	*
	*@param  wwseoGrp           Entity Group used in the header
	*@param  cellType           Flag - td or th
	*@return String with the table header
	*/
	private String getWWSEOHeader(EntityGroup wwseoGrp, String cellType, String uniqueIDTitle) {
		StringBuffer header = new StringBuffer();
		String attrCode = "COMNAME";
		String desc = PokUtils.getAttributeDescription(wwseoGrp, attrCode, attrCode);
		boolean useID= cellType.equals("th");

		header.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">" + NEWLINE);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"COMNAME" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "SEOID";
		desc = PokUtils.getAttributeDescription(wwseoGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"SEOID" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "SPECBID";
		desc = PokUtils.getAttributeDescription(wwseoGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"SPECBID" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "WWMASTERINDX";
		desc = PokUtils.getAttributeDescription(wwseoGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"WWMASTERINDX" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+"></tr>" + NEWLINE);

		return header.toString();
	} //end getWWSEOHeader method

	/********************************************************************************
	* Build the LSEO Header for the output table
	*
	*@param  lseoGrp            Entity Group used in the header
	*@param  cellType           Flag - td or th
	*@return String with the table header
	*/
	private String getLSEOHeader(EntityGroup lseoGrp, String cellType, String uniqueIDTitle) {
		StringBuffer header = new StringBuffer();
		boolean useID= cellType.equals("th");

		String attrCode = "COMNAME";
		String desc = PokUtils.getAttributeDescription(lseoGrp, attrCode, attrCode);

		header.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">" + NEWLINE);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"COMNAME" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "SEOID";
		desc = PokUtils.getAttributeDescription(lseoGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"SEOID" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "LSEOPUBDATEMTRGT";
		desc = PokUtils.getAttributeDescription(lseoGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"LSEOPUBDATEMTRGT" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "LSEOUNPUBDATEMTRGT";
		desc = PokUtils.getAttributeDescription(lseoGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"LSEOUNPUBDATEMTRGT" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+"></tr>" + NEWLINE);

		return header.toString();
	} //end getLSEOHeader method

	/********************************************************************************
	* Build the LSEOBundle Header for the output table
	*
	*@param  lseoBundleGrp      Entity Group used in the header
	*@param  cellType           Flag - td or th
	*@return String with the table header
	*/
	private static String getLSEOBundleHeader(EntityGroup lseoBundleGrp, String cellType, String uniqueIDTitle) {
		StringBuffer header = new StringBuffer();
		String attrCode = "COMNAME";
		String desc = PokUtils.getAttributeDescription(lseoBundleGrp, attrCode, attrCode);
		boolean useID= cellType.equals("th");

		header.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">" + NEWLINE);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"COMNAME" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "SEOID";
		desc = PokUtils.getAttributeDescription(lseoBundleGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"SEOID" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "BUNDLPUBDATEMTRGT";
		desc = PokUtils.getAttributeDescription(lseoBundleGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"BUNDLPUBDATEMTRGT" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+">" + NEWLINE);
		attrCode = "BUNDLUNPUBDATEMTRGT";
		desc = PokUtils.getAttributeDescription(lseoBundleGrp, attrCode, attrCode);
		header.append("<"+cellType+" style=\"text-align:center;font-weight:bold;\"");
		if (useID) {
			header.append(" id=\"BUNDLUNPUBDATEMTRGT" + uniqueIDTitle + "\"");
		}
		header.append(">"+desc+"</"+cellType+"></tr>" + NEWLINE);

		return header.toString();
	} //end getLSEOBundleHeader method

	/********************************************************************************
	* Get All Info for a specific wwseo
	*
	*@param  wwseo          wwseo EntityItem
	*@param  lineCnt        line counter
	*@return String with WWSEO info
	*/
	private static String getWWSEOInfo(EntityItem wwseo, int lineCnt, String idTitle) {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr class=\"" + (lineCnt++%2!=0?"even":"odd") + "\">" + NEWLINE);
		sb.append("<td headers=\"COMNAME" +  idTitle + "\">"+PokUtils.getAttributeValue(wwseo,"COMNAME","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"SEOID" +  idTitle + "\">"+PokUtils.getAttributeValue(wwseo,"SEOID","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"SPECBID" +  idTitle + "\">"+PokUtils.getAttributeValue(wwseo,"SPECBID","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"WWMASTERINDX" +  idTitle + "\">"+PokUtils.getAttributeValue(wwseo,"WWMASTERINDX","",NO_DATA,true)+"</td></tr>"+NEWLINE);

		return sb.toString();

	} //end getWWSEOInfo method

	/********************************************************************************
	* Get All Info for a specific lseo
	*
	*@param  lseo           lseo EntityItem
	*@param  lineCnt        line counter
	*@return String with LSEO info
	*/
	private static String getLSEOInfo(EntityItem lseo, int lineCnt, String idTitle) {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr class=\"" + (lineCnt++%2!=0?"even":"odd") + "\">" + NEWLINE);
		sb.append("<td headers=\"COMNAME" +  idTitle + "\">"+PokUtils.getAttributeValue(lseo,"COMNAME","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"SEOID" +  idTitle + "\">"+PokUtils.getAttributeValue(lseo,"SEOID","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"LSEOPUBDATEMTRGT" +  idTitle + "\">"+PokUtils.getAttributeValue(lseo,"LSEOPUBDATEMTRGT","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"LSEOUNPUBDATEMTRGT" +  idTitle + "\">"+PokUtils.getAttributeValue(lseo,"LSEOUNPUBDATEMTRGT","",NO_DATA,true)+"</td></tr>"+NEWLINE);

		return sb.toString();

	} //end getLSEOInfo method

	/********************************************************************************
	* Get All Info for a specific lseoBundle
	*
	*@param  lseoBundle     lseoBundle EntityItem
	*@param  lineCnt        line counter
	*@return String with LSEOBundle info
	*/
	private static String getLSEOBundleInfo(EntityItem lseoBundle, int lineCnt, String idTitle) {
		StringBuffer sb = new StringBuffer();

		sb.append("<tr class=\"" + (lineCnt++%2!=0?"even":"odd") + "\">" + NEWLINE);
		sb.append("<td headers=\"COMNAME" +  idTitle + "\">"+PokUtils.getAttributeValue(lseoBundle,"COMNAME","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"SEOID" +  idTitle + "\">"+PokUtils.getAttributeValue(lseoBundle,"SEOID","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"BUNDLPUBDATEMTRGT" +  idTitle + "\">"+PokUtils.getAttributeValue(lseoBundle,"BUNDLPUBDATEMTRGT","",NO_DATA,true)+"</td>"+NEWLINE);
		sb.append("<td headers=\"BUNDLUNPUBDATEMTRGT" +  idTitle + "\">"+PokUtils.getAttributeValue(lseoBundle,"BUNDLUNPUBDATEMTRGT","",NO_DATA,true)+"</td></tr>"+NEWLINE);

		return sb.toString();

	} //end getLSEOBundleInfo method

	/**
	 * used to control display order for MODEL or WWSEO or LSEO or LSEOBundle
	 */
	private static class RootComparator implements java.util.Comparator
 	{
		public int compare(Object o1, Object o2) {
			int ret;
			if (o1 instanceof EntityItem && o2 instanceof EntityItem) {
				ret = compareTarget((EntityItem) o1, (EntityItem) o2);
			}
			else {
				ret = 0;
			}
			return ret;
		}

		private int compareTarget(EntityItem o1, EntityItem o2)
		{
			String nfo1 = "";
			String nfo2 = "";
			if (o1.getEntityType().equals("MODEL")){
				nfo1 = PokUtils.getAttributeValue(o1, "MACHTYPEATR","", "") + " " +
					PokUtils.getAttributeValue(o1, "MODELATR","", "");

				nfo2 = PokUtils.getAttributeValue(o2, "MACHTYPEATR","", "") + " " +
					PokUtils.getAttributeValue(o2, "MODELATR","", "");
			}
			else { //WWSEO or LSEO or LSEOBundle
				nfo1 = PokUtils.getAttributeValue(o1, "COMNAME","", "",false);
				nfo2 = PokUtils.getAttributeValue(o2, "COMNAME","", "",false);
			}
			return nfo1.compareTo(nfo2);
		}
	} //end RootComparator class
}
