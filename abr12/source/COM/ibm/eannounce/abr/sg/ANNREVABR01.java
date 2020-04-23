//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//ANNREVABR01.java,v
//Revision 1.12  2008/01/30 19:39:16  wendy
//Cleanup RSA warnings
//
//Revision 1.11  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.10  2006/03/13 13:25:33  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. buildReportHeader is now a local method.
//
//Revision 1.9  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.8  2006/01/24 16:43:40  yang
//Jtest Changes
//
//Revision 1.7  2005/02/08 18:29:14  joan
//changes for Jtest
//
//Revision 1.6  2003/11/07 01:20:01  yang
//Adding setDGRptClass
//
//Revision 1.5  2003/09/18 19:50:26  yang
//adding bala's stuff to finally {
//
//Revision 1.4  2003/06/04 03:53:07  dave
//un Staticing getABRVersion
//
//Revision 1.3  2003/06/04 03:44:24  dave
//minor syntax
//
//Revision 1.2  2003/06/04 03:41:43  dave
//adding getABRVersion
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.32  2003/06/02 19:38:29  dave
//minor adjustments
//
//Revision 1.31  2002/12/12 01:10:00  minhthy
//rerun Extract action after DB Updated
//
//Revision 1.30  2002/11/07 18:59:02  minhthy
//removed strANREV
//
//Revision 1.29  2002/10/31 18:34:49  naomi
//added setDGName
//
//Revision 1.28  2002/10/29 02:00:37  minhthy
//fixed strStatus 's condition
//
//Revision 1.27  2002/10/29 01:59:06  minhthy
//*** empty log message ***
//
//Revision 1.22  2002/10/17 00:34:46  minhthy
//updated ANNREVIEW column
//
//Revision 1.21  2002/09/28 00:28:57  naomi
//SetReturnCode to PASS after commit
//Set GetEntityGroup to "ANNOUNCEMENT"
//
//Revision 1.20  2002/09/27 18:06:20  naomi
//apply metachages
//Process Change (1) thru (4)   --> Process Change 1  thru 4
//Executive Review --> PDT Leadership Review
//fix from "Ready For Review" to "Cancelled" based on the spec
//
//Revision 1.19  2002/09/19 20:43:26  naomi
//Added buildReportHeader(),setDGTitle() and replaced getEntityGroup() with getParentEntityGroup()
//
//Revision 1.18  2002/09/16 21:19:40  naomi
//added the Class description
//
//Revision 1.17  2002/09/11 21:15:17  bala
//remove getTable function as its not used anymore...also getAllChildren
//
//Revision 1.16  2002/09/06 16:45:45  bala
//removing DG update code...will be done by taskmaster from now on
//
//Revision 1.15  2002/09/04 20:47:10  bala
//table formatting correction
//
//Revision 1.14  2002/09/03 21:57:49  bala
//disabling import of taskmaster package since its folded into middleware,
//
//Revision 1.13  2002/08/27 01:43:43  bala
//fix flag update to DGEntity
//
//Revision 1.12  2002/08/26 18:22:23  bala
//debug
//
//Revision 1.11  2002/08/26 18:20:29  bala
//more debug statemenbts
//
//Revision 1.10  2002/08/26 18:02:22  bala
//debug
//
//Revision 1.9  2002/08/26 17:41:28  bala
//Debug
//
//Revision 1.8  2002/08/26 17:30:23  bala
//fix typo
//
//Revision 1.7  2002/08/26 17:29:01  bala
//remove the "this" in the Error handling
//
//Revision 1.6  2002/08/26 17:26:33  bala
//fix compile error
//
//Revision 1.5  2002/08/26 17:24:13  bala
//more compile error
//
//Revision 1.4  2002/08/26 17:11:31  bala
//fix compile error
//
//Revision 1.3  2002/08/26 17:09:12  bala
//fix compile errors
//
//Revision 1.2  2002/08/23 23:30:49  bala
//Cleared Typo, added CVS Logging and changed getVersion to getRevision
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
//import COM.ibm.opicmpdh.taskmaster.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

import java.sql.SQLException;
import java.util.*;
//import java.io.*;

/**
 *  Description of the Class
 *  This ABR handles the case where ANREVIEW is set to Review by the ODM.
 *  and produces a report for the Announcement Review entity.
 *  The ABR will set the ANREVIEW from "Review" to the appropriate status
 *  based on the attribute ANCYCLESTATUS in the parent Announcement entity.
 *  Return Code
 *  Pass
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class ANNREVABR01 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  	/**
   	 *  Execute ABR.
     *
     */
  	public final static String ABR = "ANNREVABR01";

  	private EntityGroup m_egParent = null;
  	private EntityItem m_ei = null;

  	/**
     * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   	 * @author Administrator
     */
  	public void execute_run() {
    	String strStatus = null;
    	String strANNCODENAME = null;
    	EntityGroup annGroup = null;
    	String strANREVDEF = null;
    	EntityGroup rstAnnReview= null;
    	EANAttribute attr= null;

    	try {
      		start_ABRBuild();

      		// Build the report header
			//buildReportHeader();
			buildRptHeader();

		  	m_egParent = m_elist.getParentEntityGroup();
		  	m_ei = m_egParent.getEntityItem(0);
		
		  	setDGTitle(setDGName(m_ei, ABR));
		
		  	/*
		   	 *  ANNPROJ
		   	 *  |
		   	 *  ANNREVIEW ** We are here at this point
		     *  |
		     *  ANNOUNCEMENT
		     *  Get the announcement columns
		     *  Set the ANNREVIEW to value based on ANCYCLESTATUS
		   	 */
		
		  	setReturnCode(PASS);
		
		  	annGroup = m_elist.getEntityGroup("ANNOUNCEMENT");

      		if (annGroup != null) {
        		EntityItem entity = annGroup.getEntityItem(0);

        		logMessage(
          			"ANNOUNCEMENT EntityItem Count: "
            			+ annGroup.getEntityItemCount());
        		logMessage(
          			"ANNOUNCEMENT: "
            			+ entity.getEntityType()
            			+ ", "
            			+ getEntityID());

        		//GET ANNCODENAME FROM ANNOUNCEMENT
        		attr = entity.getAttribute("ANNCODENAME");
        		if (attr != null) {
          			MetaFlag[] amf = (MetaFlag[]) attr.get();
          			for (int i = 0; i < amf.length; i++) {
            			if (amf[i].isSelected()) {
              				strANNCODENAME = amf[i].getFlagCode();
            			}
          			}

        		}

        		//GET ANCYCLESTATUS FROM ANNOUNCEMENT
        		strStatus =
          			getAttributeValue(
            			entity.getEntityType(),
            			entity.getEntityID(),
            			"ANCYCLESTATUS",
            			"<em>** Not Populated ** </em>");

      		}

      		logMessage("ANNCODENAME from ANNOUNCEMENT: " + strANNCODENAME);
      		logMessage("ANCYCLESTATUS from ANNOUNCEMENT " + strStatus);

      		// Perform Checks

      		logMessage("******************After getting all the Announcement Attributes");

      		if (strStatus != null) {
        		if (strStatus.equals("Draft")) {
          			strANREVDEF = "101"; // Preliminary Review

        		} else if (
          			strStatus.equals("Ready for Final Review")
                    || strStatus.equals("Change(Final Review)")) {
          			strANREVDEF = "102"; // Final Review

        		} else if (
          			strStatus.equals("Approved")
                    || strStatus.equals("Approved with Risk")
                    || strStatus.equals("Announced")
                    || strStatus.equals("Released to Production Management")
                    || strStatus.equals("Change (Approved)")
                    || strStatus.equals("Change (Approved w/Risk)")
                    || strStatus.equals("Change (Released)")
                    || strStatus.equals("Change (Announced)")) {
          			strANREVDEF = "103"; //PDT Leadership Review
        		}
      		}

      		//print report ANNREVIEW
      		rstAnnReview = m_elist.getParentEntityGroup();
      		if (rstAnnReview != null) {

        		EntityItem entity1 = rstAnnReview.getEntityItem(0);
        		setSingleFlag(
          			entity1.getEntityType(),
          			entity1.getEntityID(),
          			"ANNCODENAME",
          			strANNCODENAME);
        		setSingleFlag(
          			entity1.getEntityType(),
          			entity1.getEntityID(),
          			"ANNREVIEWDEF",
          			strANREVDEF);

        		//reRun Extract Action after DB updated
        		m_prof.setValOn(getValOn());
        		m_prof.setEffOn(getEffOn());
        		start_ABRBuild();

        		rstAnnReview = m_elist.getParentEntityGroup();

        		if (rstAnnReview != null) {
					println("<br /><br /><b>Annoucement Review:</b>");
          			entity1 = rstAnnReview.getEntityItem(0);
          			println(
            			getNavigateAttrAndOtherAttr("Announcement Review", entity1, rstAnnReview, 0));
        		}
      		} else {
        		logMessage("Could not find ANNREVIEW");
        		setReturnCode(FAIL);
      		}

      		//print report ANNOUNCEMENT
      		annGroup = m_elist.getEntityGroup("ANNOUNCEMENT");
      		if (annGroup != null) {
        		EntityItem entity = annGroup.getEntityItem(0);
				println("<br /><br /><b>Annoucement:</b>");
				println("<table summary=\"Announcement\" width=\"100%\">");
				println("<tr><th class=\"PsgLabel\" id=\"attribute\">Navigation Attributes</th>");
				println("<th class=\"PsgLabel\" id=\"value\">Attribute Value</th></tr>");				
        		for (int i = 0; i < annGroup.getMetaAttributeCount(); i++) {
          			EANMetaAttribute ma = annGroup.getMetaAttribute(i);
          			if (ma.isNavigate()) {
            			EANAttribute att =
              				entity.getAttribute(ma.getAttributeCode());
            			println(
              				"<tr><td headers=\"attribute\" class=\"PsgText\">"
                				+ ma.getLongDescription()
                				+ "</td><td headers=\"value\" class=\"PsgText\">"
                				+ (att == null
                  					|| att.toString().length() == 0
                                ? "**Not Populated**"
                                : att.toString())
                				+ "</td></tr>");
          			}
        		}
				println("</table>" + NEW_LINE + "<br />");
      		} else {
        		logMessage("Could not find ANNOUCEMENT");
        		setReturnCode(FAIL);
      		}

      		println(
        		"<br /><b>"
          			+ buildMessage(
            			MSG_IAB2016I,
            			new String[] {
              				getABRDescription(),
              				(getReturnCode() == PASS ? "Passed" : "Failed")})
          			+ "</b>");

      		log(
        		buildLogMessage(
          			MSG_IAB2016I,
          			new String[] {
            			getABRDescription(),
            			(getReturnCode() == PASS ? "Passed" : "Failed")}));
    	} catch (LockPDHEntityException le) {
      		setReturnCode(UPDATE_ERROR);
      		println(
        		"<h3 style=\"color:#c00; font-weight:bold;\">"
          			+ ERR_IAB1007E
          			+ "<br />"
          			+ le.getMessage()
          			+ "</h3>");
      		logError(le.getMessage());
    	} catch (UpdatePDHEntityException le) {
      		setReturnCode(UPDATE_ERROR);
      		println(
        		"<h3 style=\"color:#c00; font-weight:bold;\">UpdatePDH error: "
          			+ le.getMessage()
          			+ "</h3>");
      		logError(le.getMessage());
    	} catch (Exception exc) {
      		// Report this error to both the datbase log and the PrintWriter
      		println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      		println("" + exc);

      		// don't overwrite an update exception
      		if (getABRReturnCode() != UPDATE_ERROR) {
        		setReturnCode(INTERNAL_ERROR);
      		}
    	} finally {
      		//Everything is fine...so lets pass
      		if (getReturnCode() == PASS) {
        		setReturnCode(PASS);
      		}
      		// set DG submit string
      		setDGString(getABRReturnCode());
      		setDGRptName("ANNREVABR01"); //Set the report name
      		setDGRptClass("ANNREVABR01"); //Set the report class
      		// make sure the lock is released
      		if (!isReadOnly()) {
        		clearSoftLock();
      		}
    	}
    	
		//Print everything up to </html>
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter();    // Print </html>
  	}
  	/*
  	**Get Navigate Attribute follow by the rest of the Attribute
  	**
  	*/
  	private String getNavigateAttrAndOtherAttr(
    	String title, 
    	EntityItem entity,
    	EntityGroup eg,
    	int index) {
    	StringBuffer strBuffer = new StringBuffer();
    	if (index == 0) {
			strBuffer.append("<br /><table summary=\"" + title + "\" width=\"100%\">" + NEW_LINE);
			strBuffer.append("<tr>" + NEW_LINE +
				"<th class=\"PsgLabel\" id=\"description\">Attribute Description</th>" + NEW_LINE +
				"<th class=\"PsgLabel\" id=\"attrvalue\">Attribute Value</th>" + NEW_LINE + "</tr>" + NEW_LINE);						
      		/*strBuffer.append(
        		"<tr><td class=\"PsgLabel\">Attribute Description</td><td class=\"PsgLabel\">Attribute Value</td></tr>");*/
    	}

    	for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
      		EANMetaAttribute ma = eg.getMetaAttribute(i);
      		EANAttribute att = entity.getAttribute(ma.getAttributeCode());
      		String strAtt = null;
      		if (att != null && att.toString().length() > 0) {
        		strAtt = att.toString();
      		}
      		if (ma.isNavigate()) {
        		strBuffer.append(
          			"<tr><td headers=\"description\" class=\"PsgText\">"
            			+ ma.getLongDescription()
            			+ "</td><td headers=\"attrvalue\" class=\"PsgText\">"
            			+ (strAtt == null ? "** Not Populated **" : strAtt)
            			+ "</td></tr>");
      		}
    	}
    	for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
      		EANMetaAttribute ma = eg.getMetaAttribute(i);
      		EANAttribute att = entity.getAttribute(ma.getAttributeCode());
      		String strAtt = null;
      		if (att != null && att.toString().length() > 0) {
        		strAtt = att.toString();
      		}
      		if (!ma.isNavigate()) {
        		strBuffer.append(
          			"<tr><td headers=\"description\" class=\"PsgText\">"
            			+ ma.getLongDescription()
            			+ "</td><td headers=\"value\" class=\"PsgText\">"
            			+ (strAtt == null ? "** Not Populated **" : strAtt)
            			+ "</td></tr>");
      		}
    	}

    	if (index == 0) {
      		strBuffer.append("</table>");
    	}
    	return strBuffer.toString();

  	}

  	/*
  	**Get  Attributes of an Entity
  	**
  	*/
  	/*private String getAttributes(
    	EntityItem entity,
    	EntityGroup eg,
    	boolean navigate,
    	int index) {
    	StringBuffer strBuffer = new StringBuffer();
    	strBuffer.append("<br /><table width=\"100%\">" + NEW_LINE);
    	if (index == 0) {
      		strBuffer.append(
        		"<tr><td class=\"PsgLabel\">Attribute Description</td><td class=\"PsgLabel\">Attribute Value</td></tr>");
    	}

    	for (int i = 0; i < eg.getMetaAttributeCount(); i++) {
      		EANMetaAttribute ma = eg.getMetaAttribute(i);
      		EANAttribute att = entity.getAttribute(ma.getAttributeCode());
      		if (navigate) {
        		if (ma.isNavigate()) {
          			strBuffer.append(
            			"<tr><td class=\"PsgText\">"
              				+ ma.getLongDescription()
              				+ "</td><td class=\"PsgText\">"
              				+ (att == null
                				? "** Not Populated **"
                				: att.toString())
              				+ "</td></tr>");

        		} else {
          			strBuffer.append(
            			"<tr><td class=\"PsgText\">"
              				+ ma.getLongDescription()
              				+ "</td><td class=\"PsgText\">"
              				+ (att == null
                				? "** Not Populated **"
                				: att.toString())
              				+ "</td></tr>");
        		}
      		}

    	}
    	strBuffer.append("</table>");
    	return strBuffer.toString();

  	}*/

  	// copy one text attribute value from an entity to another entity
  	/**
     * setSingleFlag
     *
     * @param _sEntityType
     * @param _iEntityID
     * @param _sAttributeCode
     * @param _sAttributeValue
     * @author Administrator
     */
  	public void setSingleFlag(
    	String _sEntityType,
    	int _iEntityID,
    	String _sAttributeCode,
    	String _sAttributeValue) {

    	try {
      		println(
        		"****** "
          			+ _sEntityType
          			+ ":"
          			+ _iEntityID
          			+ " "
          			+ _sAttributeCode);

      		if (_sAttributeValue != null) {

        		EntityItem eiParm =
          			new EntityItem(
            			null,
            			m_prof,
            			getEntityType(),
            			getEntityID());
        		ReturnEntityKey rek =
          			new ReturnEntityKey(
            			eiParm.getEntityType(),
            			eiParm.getEntityID(),
            			true);
        		DatePackage dbNow = m_db.getDates();
        		String strNow = dbNow.getNow();
        		String strForever = dbNow.getForever();

        		ControlBlock cbOn =
          			new ControlBlock(
            			strNow,
            			strForever,
            			strNow,
            			strForever,
            			m_prof.getOPWGID(),
            			m_prof.getTranID());
        		SingleFlag sf =
          			new SingleFlag(
            			m_prof.getEnterprise(),
            			rek.getEntityType(),
            			rek.getEntityID(),
            			_sAttributeCode,
            			_sAttributeValue,
            			1,
            			cbOn);
        		Vector vctAtts = new Vector();
        		Vector vctReturnsEntityKeys = new Vector();

        		if (sf != null) {
          			vctAtts.addElement(sf);

          			rek.m_vctAttributes = vctAtts;
          			vctReturnsEntityKeys.addElement(rek);

          			m_db.update(m_prof, vctReturnsEntityKeys, false, false);
          			m_db.commit();
        		}
      		}
    	} catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
      		logMessage("set Text Value: " + e.getMessage());
    	} catch (Exception e) {
      		logMessage("set Text Value:" + e.getMessage());
    	}
  	}

  	/**
   	 *  Get the entity description to use in error messages
     *
   	 *@param  entityType  Description of the Parameter
   	 *@param  entityId    Description of the Parameter
   	 *@return             String
   	 */
  	protected String getABREntityDesc(String entityType, int entityId) {
	    return null;
  	}

  	/**
   	 *  Get ABR description
   	 *
   	 *@return    java.lang.String
   	 */
  	public String getDescription() {
    	//return Constants.IAB3053I + "<br /><br />" + Constants.IAB3050I;
    	return "<br /><br />";
  	}

  	/**
   	 *  Get any style that should be used for this page. Derived classes can
   	 *  override this to set styles They must include the <style>...</style> tags
   	 *
   	 *@return    String
   	 */
  	protected String getStyle() {
    	// Print out the PSG stylesheet
    	return "";
  	}

  	/**
     * getRevision
     *
     * @return
     * @author Administrator
     */
  	public String getRevision() {
    	return "1.12";
  	}
  	/**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  	public static String getVersion() {
    	return ("ANNREVABR01.java,v 1.12 2008/01/30 19:39:16 wendy Exp");
  	}
  	/**
   	 * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   	 * @author Administrator
   	 */
  	public String getABRVersion() {
    	return getVersion();
  	}
  
  	/*
   	 *  builds the standard header and submits it to the passed PrintWriter
   	 */
  	private void buildRptHeader() throws SQLException, MiddlewareException{

	  	String strVersion = getVersion();
	  	String astr1[] = new String[]{getABRDescription(), strVersion, getEnterprise()};
	  	String strMessage1 = buildMessage(MSG_IAB0001I, astr1);
	
	  	// output html header
	  	//String abrName = getShortClassName(getClass());
	  	//println("<html>\n<head>\n<title>" + abrName + "</title>\n" +
	  	//	getStyle() + "</head>\n<body>\n");
		
	  	println(EACustom.getDocTypeHtml());
	  	println("<head>");
	  	println(EACustom.getMetaTags("ANNREVABR01.java"));
	  	println(EACustom.getCSS());
	  	println(EACustom.getTitle(getShortClassName(getClass())));
	  	println("</head>");
	  	println("<body id=\"ibm-com\">");
	  	println(EACustom.getMastheadDiv());
	  
		println("<p class=\"ibm-intro ibm-alternate-three\"><em>" + strMessage1 + "</em></p>");

	  	println("<table summary=\"layout\" width=\"750\" cellpadding=\"0\" cellspacing=\"0\">" + NEW_LINE +
		  	"<tr><td width=\"25%\"><b>Abr Version: </b></td><td>" +
		  	getABRVersion() + "</td></tr>" + NEW_LINE +
		  	"<tr><td width=\"25%\"><b>Valid Date: </b></td><td>" +
		  	getValOn() + "</td></tr>" + NEW_LINE +
		  	"<tr><td width=\"25%\"><b>Effective Date: </b></td><td>" +
		  	getEffOn() + "</td></tr>" + NEW_LINE +
		  	"<tr><td width=\"25%\"><b>Date Generated: </b></td><td>" +
		  	getNow() + "</td></tr>" + NEW_LINE +
		  	"</table>");
	  	println("<h3>Description: </h3>" + getDescription() + NEW_LINE + "<hr />" + NEW_LINE);

  	}

}
