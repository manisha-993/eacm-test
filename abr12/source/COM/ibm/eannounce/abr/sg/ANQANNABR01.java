//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//ANQANNABR01.java,v
//Revision 1.14  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.13  2006/03/13 16:52:34  couto
//Changed description.
//
//Revision 1.12  2006/03/13 16:48:13  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader is now a local method.
//
//Revision 1.11  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.10  2006/01/24 16:43:56  yang
//Jtest Changes
//
//Revision 1.9  2005/02/08 18:29:14  joan
//changes for Jtest
//
//Revision 1.8  2003/11/07 01:20:08  yang
//Adding setDGRptClass
//
//Revision 1.7  2003/09/18 19:50:32  yang
//adding bala's stuff to finally {
//
//Revision 1.6  2003/06/19 19:26:16  yang
//added function setControlBlock()
//
//Revision 1.5  2003/06/04 03:53:07  dave
//un Staticing getABRVersion
//
//Revision 1.4  2003/06/04 03:44:24  dave
//minor syntax
//
//Revision 1.3  2003/06/04 03:41:43  dave
//adding getABRVersion
//
//Revision 1.2  2003/06/03 23:28:32  dave
//commonizing setControlBlock
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.3  2003/05/22 18:50:12  naomi
//fixed output message
//
//Revision 1.2  2003/01/15 01:53:39  naomi
//fix output
//
//Revision 1.1  2003/01/14 20:57:53  naomi
//check in
//
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.sql.*;

/**
 *  Description of the Class
 *  The ABR will be triggered once daily between the hours of 12:01AM and 2:00AM by a system chron job.
 *  This ABR sets ANCYCLESTATUS to ‘Announced’.
 *  Return Code
 *  Pass
 *
 *@author     Naomi
 *@created    January 13,2003
 */
public class ANQANNABR01 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   */

  //ABR
  public final static String ABR = "ANQANNABR01";

  //ANNOUNCEMENT Entity
  /**
     * Announcement
     *
     */
  public final static String ANNOUNCEMENT = "ANNOUNCEMENT";
  //Announcement

  //ANNOUNCEMENT Entity -- Attributes
  /**
     * Ancyclestatus
     *
     */
  public final static String ANCYCLESTATUS = "ANCYCLESTATUS";
  // S //Announcement Lifecycle Status
  /**
     * Annnumber
     *
     */
  public final static String ANNNUMBER = "ANNNUMBER";
  // T //Announcement Number
  /**
     * Anncodename
     *
     */
  public final static String ANNCODENAME = "ANNCODENAME";
  // U //Announcement Code Name
  /**
     * AnnTitle
     *
     */
  public final static String ANNTITLE = "ANNTITLE";
  // T //Announcement Title
  /**
     * Anndate
     *
     */
  public final static String ANNDATE = "ANNDATE";
  // T //Announce Date

  //ANNOP Relator -- Entity1Type:ANNOUNCEMENT, Entity2Type:OP
  /**
     * Annop
     *
     */
  public final static String ANNOP = "ANNOP";
  //e-announce Team Member

  //ANNOP Relator -- Attributes
  /**
     * AnnRoleType
     *
     */
  public final static String ANNROLETYPE = "ANNROLETYPE";
  //U  Announcement Role Type (Executive Approval Name)

  //OP Entity -- Attributes
  /**
     * Username
     *
     */
  public final static String USERNAME = "USERNAME";
  //T //User Name

  // Class constants
  /**
     * Def not populated HTML
     *
     */
  public final static String DEF_NOT_POPULATED_HTML =
    "** Not Populated **";

  private EntityGroup m_egParent = null; //ANNOUNCEMENT
  private EntityGroup m_egANNOP = null; //ANNOP

  private EntityItem m_eiParent = null; //ANNOUNCEMENT
  private EntityItem m_eiANNOP = null; //ANNOP
  private EntityItem m_eiOP = null; //OP

  private final String m_strRelProMgmt = "Released to Production Management";
  //ANCYCLESTATUS  Ststus Attribute (AttributeValue 116)
  private final String m_strAnnounceValue = "117";
  //ANCYCLESTATUS  Ststus Attribute (Announced)

  private final String m_strANNROLETYPE = "Releasing Executive";
  //ANNROLETYPE    (AttributeValue 5)

  ControlBlock m_cbOn = null;
  String m_strNow = null;
  String m_strForever = null;

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    try {
      start_ABRBuild();

      // Build the report header
      //buildReportHeader();
	  buildRptHeader();

      setControlBlock();

      //Get the root ANNOUNCEMENT
      m_egParent = m_elist.getParentEntityGroup();
      m_eiParent = m_egParent.getEntityItem(0);

      setDGTitle(setDGName(m_eiParent, ABR));

      logMessage(
        "************ Root Entity Type and id "
          + getEntityType()
          + ":"
          + getEntityID());

      if (m_egParent == null) {
        logMessage("********** 1 ANNOUNCEMENT Not found");
        setReturnCode(FAIL);

      } else {
        processRootEntity();
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
      //setReturnCode(PASS);
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("ANQANNABR01"); //Set the report name
      setDGRptClass("ANQANNABR01"); //Set the report class
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
   *  Get the getRevision
   *
   *@return    java.lang.String
   */
  public String getRevision() {
    return "1.14";
  }

  /**
   *  Process the root Entity
   *
   * @throws COM.ibm.eannounce.abr.util.LockPDHEntityException
   * @throws COM.ibm.eannounce.abr.util.UpdatePDHEntityException
   * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   */
  public void processRootEntity()
    throws
    LockPDHEntityException,
    UpdatePDHEntityException,
    Exception,
    SQLException,
    MiddlewareException {
    String strStatus = null;
    String strTodayDate = null;
    String strANNROLETYPE = null;
    String strAnnExcAppName = null;

    logMessage(
      "****************** Entity Type returned is "
        + m_eiParent.getEntityType()
        + " ei:"
        + m_eiParent.getEntityID());

    if (m_eiParent.getEntityType().equals(ANNOUNCEMENT)) {

      strStatus =
        getAttributeValue(
          getEntityType(),
          getEntityID(),
          ANCYCLESTATUS,
          "<em>" + DEF_NOT_POPULATED_HTML + "</em>");

      //println("ANCYCLESTATUS strStatus is " + strStatus + "\n");
      logMessage("****** ANCYCLESTATUS strStatus is " + strStatus);

      strTodayDate = m_strNow.substring(0, 10).trim();
      println("<h1><b>e-announce report of Announcement</b></h1><br />");
      println("<h1>" + strTodayDate + "</h1><br /><br />");

      if (strStatus.equals(m_strRelProMgmt)) {

        //Get the ANNOP
        m_egANNOP = m_elist.getEntityGroup(ANNOP);
        printAttribute();

        if (m_egANNOP != null && m_egANNOP.isRelator()) {
          boolean found = false;

          for (int i = 0; i < m_egANNOP.getEntityItemCount(); i++) {

            m_eiANNOP = m_egANNOP.getEntityItem(i);
            strANNROLETYPE =
              getAttributeValue(
                m_eiANNOP.getEntityType(),
                m_eiANNOP.getEntityID(),
                ANNROLETYPE,
                "<em>None</em>");

            //println("strANNROLETYPE is " + strANNROLETYPE + "\n");
            //println("************ Entity Type and id " + m_eiANNOP.getEntityType() + ":" + m_eiANNOP.getEntityID() + "\n");
            //logMessage("************ Entity Type and id " + m_eiANNOP.getEntityType() + ":" + m_eiANNOP.getEntityID());

            if (strANNROLETYPE.equals(m_strANNROLETYPE)) {
              m_eiOP = (EntityItem) m_eiANNOP.getDownLink(0);
              strAnnExcAppName =
                getAttributeValue(
                  m_eiOP.getEntityType(),
                  m_eiOP.getEntityID(),
                  USERNAME,
                  "<em>None</em>");

              println("<tr><td class=\"PsgLabel2\">Executive Approval Name</td>");
              println(
                "<td class=\"PsgText2\">"
                  + strAnnExcAppName
                  + "</td></tr>");
              found = true;
            }
          }

          if (!found) {
            println("<tr><td class=\"PsgLabel2\">Executive Approval Name</td>");
            println("<td class=\"PsgText2\"><em>None</em></td></tr>");
          }

          println("</table><br /><br />");
          setReturnCode(PASS);

        } else {
          println(
            "<b>"
              + "No relator for e-announce Team Member is defined</b><br /><br />");
          logMessage("No relator e-announce Team Member is defined");
        }
        setFlagValue(ANCYCLESTATUS);

      } else {
        println(
          "<b>"
            + "Announcement Lifecycle Status"
            + " is not "
            + m_strRelProMgmt
            + "</b><br /><br />");
        logMessage(
          "Announcement Lifecycle Status"
            + " is not "
            + m_strRelProMgmt);
      }
    } //end of if
  } //end of processRootEntity()

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#setControlBlock()
   * @author Administrator
   */
  public void setControlBlock() {
    m_strNow = getNow();
    m_strForever = getForever();
    m_cbOn =
      new ControlBlock(
        m_strNow,
        m_strForever,
        m_strNow,
        m_strForever,
        m_prof.getOPWGID(),
        m_prof.getTranID());
  }

  /**
     * setFlagValue
     *
     * @param _sAttributeCode
     * @author Administrator
     */
  public void setFlagValue(
    String _sAttributeCode) {

    String strAttributeValue = null;

    if (_sAttributeCode.equals(ANCYCLESTATUS)) {
      strAttributeValue = m_strAnnounceValue;
    }

    //println("****** strAttributeValue set to: " + strAttributeValue);
    logMessage("****** strAttributeValue set to: " + strAttributeValue);

    if (strAttributeValue != null) {
      try {
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

        SingleFlag sf =
          new SingleFlag(
            m_prof.getEnterprise(),
            rek.getEntityType(),
            rek.getEntityID(),
            _sAttributeCode,
            strAttributeValue,
            1,
            m_cbOn);
        Vector vctAtts = new Vector();
        Vector vctReturnsEntityKeys = new Vector();

        if (sf != null) {
          vctAtts.addElement(sf);

          rek.m_vctAttributes = vctAtts;
          vctReturnsEntityKeys.addElement(rek);

          m_db.update(m_prof, vctReturnsEntityKeys, false, false);
          m_db.commit();
        }
      } catch (COM.ibm.opicmpdh.middleware.MiddlewareException e) {
        logMessage("setFlagValue: " + e.getMessage());
      } catch (Exception e) {
        logMessage("setFlagValue: " + e.getMessage());
      }
    }
  }

  /**
     * PrintAttribute
     *
     * @author Administrator
     */
  public void printAttribute() {
    String strAnnNumber = null;
    String strAnnCodeName = null;
    String strAnnTitle = null;
    String strAnnDate = null;
    String strTodayDate = null;

    strAnnNumber =
      getAttributeValue(
        getEntityType(),
        getEntityID(),
        ANNNUMBER,
        "<em>" + DEF_NOT_POPULATED_HTML + "</em>");
    strAnnCodeName =
      getAttributeValue(
        getEntityType(),
        getEntityID(),
        ANNCODENAME,
        "<em>" + DEF_NOT_POPULATED_HTML + "</em>");
    strAnnTitle =
      getAttributeValue(
        getEntityType(),
        getEntityID(),
        ANNTITLE,
        "<em>" + DEF_NOT_POPULATED_HTML + "</em>");
    strAnnDate =
      getAttributeValue(
        getEntityType(),
        getEntityID(),
        ANNDATE,
        "<em>" + DEF_NOT_POPULATED_HTML + "</em>");

    strTodayDate = m_strNow.substring(0, 10).trim();
    println("<h1>The following Announcement has been updated to</h1><br />");
    println(
      "<h1>the Status of 'Announced' as of ("
        + strTodayDate
        + ")</h1><br /><br />");

    println("<table summary=\"" + m_egParent.getLongDescription() + "\" width=\"100%\">");
    println("<tr>");
    println(
      "<td><h1><b>"
        + m_egParent.getLongDescription()
        + "</b></h1>");
    println("</td></tr>");
    println("</table>");

    println("<table summary=\"layout\" width=\"100%\">");
    println("<tr><td class=\"PsgLabel2\">Announcement Number</td>");
    println("<td class=\"PsgText2\">" + strAnnNumber + "</td></tr>");

    println("<tr><td class=\"PsgLabel2\">Announcement Code Name</td>");
    println("<td class=\"PsgText2\">" + strAnnCodeName + "</td></tr>");

    println("<tr><td class=\"PsgLabel2\">Announcement Title</td>");
    println("<td class=\"PsgText2\">" + strAnnTitle + "</td></tr>");

    println("<tr><td class=\"PsgLabel2\">Announce Date</td>");
    println("<td class=\"PsgText2\">" + strAnnDate + "</td></tr>");
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("ANQANNABR01.java,v 1.14 2006/03/13 19:42:03 couto Exp");
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
	  println(EACustom.getMetaTags("ANQANNABR01.java"));
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
