//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//ANQRLSEABR01.java,v
//Revision 1.44  2006/03/13 19:42:03  couto
//Fixed copyright info.
//
//Revision 1.43  2006/03/13 14:33:25  couto
//Changed layout, using EACustom methods. Changes for Jtest.
//Fixed br tags. Chaged font tags. Fixed table header.
//buildReportHeader and printNavigateAttributes are now local methods.
//
//Revision 1.42  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.41  2006/01/24 16:42:56  yang
//Jtest Changes
//
//Revision 1.40  2005/02/08 18:29:14  joan
//changes for Jtest
//
//Revision 1.39  2003/11/07 01:20:27  yang
//Adding setDGRptClass
//
//Revision 1.38  2003/09/18 19:50:51  yang
//adding bala's stuff to finally {
//
//Revision 1.37  2003/06/17 20:46:51  yang
//additional syntax
//
//Revision 1.36  2003/06/17 20:42:19  yang
//syntax
//
//Revision 1.35  2003/06/17 20:36:00  yang
//more stuff on controlblock
//
//Revision 1.34  2003/06/17 20:27:33  yang
//test
//
//Revision 1.33  2003/06/17 20:10:35  yang
//Added countrolBlock()
//
//Revision 1.32  2003/06/17 20:02:18  yang
//*** empty log message ***
//
//Revision 1.31  2003/06/17 19:36:51  yang
//*** empty log message ***
//
//Revision 1.30  2003/06/17 19:14:46  yang
//syntax
//
//Revision 1.29  2003/06/17 18:08:55  yang
//got rid of println()
//
//Revision 1.28  2003/06/17 18:00:38  yang
//syntax
//
//Revision 1.21  2003/06/16 18:32:59  yang
//syntax
//
//Revision 1.20  2003/06/16 17:33:11  yang
//Added USERNAME to abr
//
//Revision 1.19  2003/06/13 22:11:57  yang
//more syntax
//
//Revision 1.18  2003/06/13 22:02:28  yang
//syntax
//
//Revision 1.17  2003/06/13 21:54:08  yang
//updated getNow()
//
//Revision 1.16  2003/06/13 21:47:22  yang
//syntax
//
//Revision 1.15  2003/06/13 21:39:48  yang
//*** empty log message ***
//
//Revision 1.14  2003/06/13 21:27:02  minhthy
//*** empty log message ***
//
//Revision 1.13  2003/06/13 21:07:39  minhthy
//updated getNow() for isPastDate()
//
//Revision 1.12  2003/06/13 20:53:22  yang
//syntax
//
//Revision 1.11  2003/06/13 20:25:54  minhthy
//*** empty log message ***
//
//Revision 1.10  2003/06/13 20:23:49  minhthy
//testing isPastDate()
//
//Revision 1.9  2003/06/13 17:00:44  yang
//syntax
//
//Revision 1.8  2003/06/13 16:49:11  yang
//Adding StackTrace
//
//Revision 1.7  2003/06/04 03:53:08  dave
//un Staticing getABRVersion
//
//Revision 1.6  2003/06/04 03:44:25  dave
//minor syntax
//
//Revision 1.5  2003/06/04 03:41:43  dave
//adding getABRVersion
//
//Revision 1.4  2003/06/04 01:45:47  dave
//more commonizing on routines
//
//Revision 1.3  2003/06/03 23:28:33  dave
//commonizing setControlBlock
//
//Revision 1.2  2003/06/03 19:33:58  dave
//more consolidation
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.13  2003/06/03 18:17:36  dave
//preping for common T1 and T2 processing
//
//Revision 1.12  2003/05/23 23:06:06  naomi
//added more checking per spec change
//
//Revision 1.11  2003/04/18 00:07:25  naomi
//check the case ANCYCLESTATUS = Approved
//since Workflow action filters an entity in v1.1.1
//
//Revision 1.10  2002/12/12 23:20:54  minhthy
////reRun Extract Action after DB updated
//
//Revision 1.9  2002/11/13 19:56:07  naomi
//fixed Fail logic
//
//Revision 1.8  2002/11/13 18:59:08  naomi
//fixed to display reason for failure
//enable to queue SENDTOPM
//
//Revision 1.7  2002/11/06 22:04:01  naomi
//remove setReturnCode(PASS) in the finally clause
//
//Revision 1.6  2002/11/04 21:56:57  naomi
//fix output
//


/*
 *  Description of the Class
 *  The ABR checks to see if the Announcement is ready to ‘Release to Production Management’.
 *  If ANCYCLESTATUS is ‘Approved with Risk’, the ABR checks to see
 *  if at least one Announcement Review instance exists
 *  with a ANREVIEWDEF of ‘PDT Leadership Review’ and  ANNREVIEW STATUS of ‘Approved’ or ‘Proceed with Risk’.
 *  This ABR also tests that ANNOUNCEMENT Attributes
 *  Executive Approval Date - Actual, Executive Approval, and Executive Approval Name have been completed.
 *  Return Code
 *  Pass -- then set ANCYCLESTATUS to ‘Release to Production Management’ and queue RFAHWABR01.
 *  Fail -- if no matching Announcement found.
 *
 *@author     Naomi
 *@created    October 28,2002
 */

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.io.*;
import java.sql.*;


/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Administrator
 */
public class ANQRLSEABR01 extends PokBaseABR {
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2002, 2006  All Rights Reserved.";
	
  /**
   *  Execute ABR.
   */

  //ABR
  public final static String ABR = "ANQRLSEABR01";

  //ANNOUNCEMENT Entity
  /**
     * ANNOUNCEMENT
     *
     */
  public final static String ANNOUNCEMENT = "ANNOUNCEMENT";
  /**
   * description
   *
   */
  public final static String ANNOUNCEMENT_DESC = "Announcement";

  //ANNOUNCEMENT Entity -- Attributes
  /**
     * ANCYCLESTATUS
     *
     */
  public final static String ANCYCLESTATUS = "ANCYCLESTATUS";
  // S
  /**
     * ANCYCLESTATUS_DESC
     *
     */
  public final static String ANCYCLESTATUS_DESC =
    "Announcement Lifecycle Status";

  /**
     * EXECAPPRDATE_A
     *
     */
  public final static String EXECAPPRDATE_A = "EXECAPPRDATE_A";
  // T
  /**
     * EXECAPPRDATE_A_DESC
     *
     */
  public final static String EXECAPPRDATE_A_DESC =
    "Executive approval date - Actual";

  /**
     * EXECAPPREADY
     *
     */
  public final static String EXECAPPREADY = "EXECAPPREADY";
  // U   Executive Approved
  /**
     * EXECAPPREADY_DESC
     *
     */
  public final static String EXECAPPREADY_DESC =
    "Executive Approval Ready";

  /**
     * ANNNUMBER
     *
     */
  public final static String ANNNUMBER = "ANNNUMBER"; // T
  /**
     * ANNDATE
     *
     */
  public final static String ANNDATE = "ANNDATE"; // T
  /**
     * ANNTYPE
     *
     */
  public final static String ANNTYPE = "ANNTYPE"; // U
  /**
     * ANNTITLE
     *
     */
  public final static String ANNTITLE = "ANNTITLE"; // T
  /**
     * ANNDESC
     *
     */
  public final static String ANNDESC = "ANNDESC"; // X
  /**
     * INEANN
     *
     */
  public final static String INEANN = "INEANN";
  // U Announcement released via e-announce

  //ANNREVIEW Entity
  /**
     * ANNREVIEW
     *
     */
  public final static String ANNREVIEW = "ANNREVIEW";
  /**
     * ANNREVIEW_DESC
     *
     */
  public final static String ANNREVIEW_DESC =
    "Annoucement Review";

  //ANNREVIEW Entity -- Attributes
  /**
     * ANNREVIEW
     *
     */
  public final static String ANREVIEW = "ANREVIEW"; // S

  /**
     * ANNREVIEWDEF
     *
     */
  public final static String ANNREVIEWDEF = "ANNREVIEWDEF"; // U
  /**
     * ANNREVIEWDEF_DESC
     *
     */
  public final static String ANNREVIEWDEF_DESC =
    "Announce Review Definition";

  //ANNOP Relator -- Entity1Type:ANNOUNCEMENT, Entity2Type:OP
  /**
     * ANNOP
     *
     */
  public final static String ANNOP = "ANNOP";

  //ANNOP Relator -- Attributes
  /**
     * ANNROLETYPE
     *
     */
  public final static String ANNROLETYPE = "ANNROLETYPE";
  // U  Executive Approval Name
  /**
     * ANNROLETYPE_DESC
     *
     */
  public final static String ANNROLETYPE_DESC =
    "Announcement Role Type";

  //OP Entity -- Attributes
  /**
     * USERNAME
     *
     */
  public final static String USERNAME = "USERNAME";
  // T  User Name

  //RFAHWABR01 ABR
  /**
     * RFAHWABR01
     *
     */
  public final static String RFAHWABR01 = "RFAHWABR01";
  //queue Bala's ABR

  // Class constants
  /**
     * DEF_NOT_POPULATED_HTML
     *
     */
  public final static String DEF_NOT_POPULATED_HTML =
    "** Not Populated **";
  /**
     * NULL
     *
     */
  public final static String NULL = "";

  private EntityGroup m_egParent = null; //ANNOUNCEMENT
  private EntityGroup m_egANNREV = null; //ANNREVIEW
  private EntityGroup m_egANNOP = null; //OP

  private EntityItem m_eiParent = null; //ANNOUNCEMENT

  private final String m_strAppRiskValue = "Approved with Risk";
  //ANCYCLESTATUS  Ststus Attribute (AttributeValue 115)
  private final String m_strAppValue_ANN = "Approved";
  //ANCYCLESTATUS  Ststus Attribute (AttributeValue 114)

  private final String m_strAppValue = "Approved";
  //ANREVIEW       Status Attribute (AttributeValue 115)
  private final String m_strProcRiskValue = "Proceed with Risk";
  //ANREVIEW       Status Attribute (AttributeValue 116)
  private final String m_strPDTLeadValue = "PDT Leadership Review";
  //ANNREVIEWDEF   Unique Flag      (AttributeValue 103)
  private final String m_strYesValue = "Yes";
  //EXECAPPREADY   Unique Flag      (AttributeValue 1385)
  //INEANN         Unique Flag      (AttributeValue 10)
  private final String m_strNewValue = "New";
  //ANNTYPE        Unique Flag      (AttributeValue 19)
  private final String m_strRelExeRevValue = "Releasing Executive";
  //ANNROLETYPE    Unique Flag      (AttributeValue 5)

  private final String m_strRelProMgmtValue = "116";
  //ANCYCLESTATUS  Ststus Attribute (Released to Production Management)
  private final String m_strSENDTOPMValue = "0020";
  //RFAHWABR01       Bala's ABR       (Queued)

  private boolean m_bPass = false;
  private ControlBlock m_cbOn = null;
  private String m_strNow = null;
  private String m_strForever = null;

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    EANAttribute att = null;
    String strANNDesc = null;
    StringWriter writer = null;
    String x = null;
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
        println(
          "<h1><b>"
            + m_egParent.getLongDescription()
            + "</b></h1><br />");
        processRootEntity(m_egParent, ANNOUNCEMENT);
        if (getReturnCode() == FAIL) {
          //println(displayAttributes(m_eiParent, m_egParent, true));
          println(dispNavAttributes(m_eiParent, m_egParent));
          att = m_eiParent.getAttribute("ANNDESC");
          strANNDesc =
            (att != null
              ? att.toString()
              : "<em>** Not Populated **</em>");
          println(
            "<br /><table summary=\"Announcement Description\" width=\"100%\"><tr><td class=\"PsgLabel\">Announcement Description</td></tr><tr><td class=\"PsgText\">"
              + strANNDesc
              + "</td></tr></table>");
        }
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

      println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      println("" + exc);

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
      exc.printStackTrace();

      writer = new StringWriter();
      exc.printStackTrace(new PrintWriter(writer));
      x = writer.toString();
      println(x);

      logMessage(
        "Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      logMessage("" + exc);

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      //Everything is fine...so lets pass
      //setReturnCode(PASS);
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("ANQRLSEABR01"); //Set the report name
      setDGRptClass("ANQRLSEABR01"); //Set the report class
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
    return "1.44";
  }

  /**
   *  Process the root Entity
   *
   * @param _eg
   * @param _sEntityType
   * @throws COM.ibm.eannounce.abr.util.LockPDHEntityException
   * @throws COM.ibm.eannounce.abr.util.UpdatePDHEntityException
   * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
   * @throws java.lang.Exception
   * @throws java.sql.SQLException
   */
  public void processRootEntity(
    EntityGroup _eg,
    String _sEntityType)
    throws
    LockPDHEntityException,
    UpdatePDHEntityException,
    Exception,
    SQLException,
    MiddlewareException {

    String strStatus = null;
    EntityItem entity = _eg.getEntityItem(0);

    logMessage(
      "****************** Entity Type returned is "
        + entity.getEntityType());

    if (entity.getEntityType().equals(_sEntityType)) {
      logMessage(
        "****************** Entitytype is " + entity.getEntityType());

      strStatus =
        getAttributeValue(
          getEntityType(),
          getEntityID(),
          ANCYCLESTATUS,
          "<em>" + DEF_NOT_POPULATED_HTML + "</em>");

      //println("ANCYCLESTATUS strStatus is " + strStatus);
      logMessage("****** ANCYCLESTATUS strStatus is " + strStatus);

      if (strStatus.equals(m_strAppRiskValue)) {

        //Get the ANNREVIEW
        m_egANNREV = m_elist.getEntityGroup(ANNREVIEW);
        logMessage(
          "************ Root Entity Type and id "
            + getEntityType()
            + ":"
            + getEntityID());

        if (m_egANNREV == null) {
          logMessage("********** 2 ANNREVIEW Not found");
          setReturnCode(FAIL);

        } else {
          processEntityStatus(
            m_egANNREV,
            ANNREVIEW,
            ANREVIEW);

          //println("processRootEntity m_bPass " +  m_bPass);
          logMessage("****** processRootEntity m_bPass " + m_bPass);

          if (m_bPass) {

            String strAnnExeAppDateAct = null;
            String strAnnExeApp = null;
            String strAnnIneAnn = null;
            String strAnnType = null;
            String strAnnExeAppName = null;
            String strUserName = null;

            strAnnExeAppDateAct =
              getAttributeValue(
                getEntityType(),
                getEntityID(),
                EXECAPPRDATE_A + NULL);
            strAnnExeApp =
              getAttributeValue(
                getEntityType(),
                getEntityID(),
                EXECAPPREADY,
                "<em>" + DEF_NOT_POPULATED_HTML + "</em>");
            strAnnIneAnn =
              getAttributeValue(
                getEntityType(),
                getEntityID(),
                INEANN,
                "<em>" + DEF_NOT_POPULATED_HTML + "</em>");
            strAnnType =
              getAttributeValue(
                getEntityType(),
                getEntityID(),
                ANNTYPE,
                "<em>" + DEF_NOT_POPULATED_HTML + "</em>");
            //strUserName = getAttributeValue(getEntityType(), getEntityID(), USERNAME, "<em>" + DEF_NOT_POPULATED_HTML + "</em>");

            //println("strAnnExeAppDateAct is <em>" + strAnnExeAppDateAct + "</em>");
            //println("strAnnExeApp is " + strAnnExeApp);
            //println("strUserName is " + strUserName);
            logMessage(
              "****** EXECAPPRDATE_A is " + strAnnExeAppDateAct);
            logMessage("****** EXECAPPREADY is " + strAnnExeApp);
            logMessage("****** INEANN is " + strAnnIneAnn);
            logMessage("****** ANNTYPE is " + strAnnType);
            //logMessage("****** USERNAME is " + strUserName);

            //Get the ANNOP Relator
            m_egANNOP = m_elist.getEntityGroup(ANNOP);
            logMessage(
              "********** 3 Entity Type and id "
                + getEntityType()
                + ":"
                + getEntityID());

            if (m_egANNOP == null) {
              logMessage("********** 3 ANNOP Not found");
              setReturnCode(FAIL);

            } else {
              strAnnExeAppName =
                getRelatorAttributeValue(m_egANNOP, ANNOP);
              strUserName =
                getRelatorUserNameValue(m_egANNOP, ANNOP);

              //println("ANNROLETYPE is " + strAnnExeAppName);
              logMessage("ANNROLETYPE is " + strAnnExeAppName);
            }

            //println("isPastDate: " + isPastDate(strAnnExeAppDateAct));

            if (!(isPastDate(strAnnExeAppDateAct)
                                && strAnnExeApp.length() > 0
                                && strAnnExeApp.equals(m_strYesValue)
                                && // compare if value is "Yes"
                                strAnnExeAppName
                != null
                                && strUserName != null
                                && strAnnIneAnn.equals(m_strYesValue)
                                && strAnnType.equals(m_strNewValue))) {

              //println("isPastDate(strAnnExeAppDateAct) " +  isPastDate(strAnnExeAppDateAct.trim()));
              //logMessage("****** isPastDate(strAnnExeAppDateAct) " +  isPastDate(strAnnExeAppDateAct));

              if (!isPastDate(strAnnExeAppDateAct)) {
                println(
                  "<br /><b>Fail:"
                    + EXECAPPRDATE_A_DESC
                    + " is not today or past</b><br />");
              }

              if (!(strAnnExeApp.length() > 0
                                    && strAnnExeApp.equals(m_strYesValue))) {
                println(
                  "<br /><b>Fail:"
                    + EXECAPPREADY_DESC
                    + " is not "
                    + m_strYesValue
                    + "</b><br />");
              }

              /*
                 if( strAnnExeAppName == null)
                  println("<br /><b>Fail:" + ANNROLETYPE_DESC + " is not " + m_strRelExeRevValue + "</b><br />");   */

              if (strUserName == null) {
                println("<br /><b>Fail:the User Name is null</b><br />");
              }

              if (!strAnnIneAnn.equals(m_strYesValue)
                || !strAnnType.equals(m_strNewValue)) {
                println("<br /><b>Fail:the Announcement must be a 'New' e-announce Announcement</b><br /><br />");
              }

              setReturnCode(FAIL);
              m_bPass = false;

            } else {
              setReturnCode(PASS);

              setFlagValue(
                ANCYCLESTATUS);
              setFlagValue(
                RFAHWABR01);

              //reRun Extract Action after DB updated
              m_prof.setValOn(getValOn());
              m_prof.setEffOn(getEffOn());
              start_ABRBuild();

              printPassMessage();
            }

          } //end of if(m_bPass) -- Pass
          else {
            println("<br /><b>Fail:Announcement review not exist</b><br /><br />");
            setReturnCode(FAIL);
          }
        }
      } // added for V1.1.1 since WorkFlow action filters ANCYCLESTATUS = Approved also
      else if (strStatus.equals(m_strAppValue_ANN)) {
        setReturnCode(PASS);
        setFlagValue(ANCYCLESTATUS);
        setFlagValue(RFAHWABR01);

        //reRun Extract Action after DB updated
        m_prof.setValOn(getValOn());
        m_prof.setEffOn(getEffOn());
        start_ABRBuild();

        printPassMessage();

      } else {
        println(
          "<br /><b>Fail:"
            + ANCYCLESTATUS_DESC
            + " is not '"
            + m_strAppRiskValue
            + "' or '"
            + m_strAppValue_ANN
            + "'</b><br /><br />");
        setReturnCode(FAIL);
      }
    }
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
   * @param _sAttributeCode
   * @author Administrator
   */
  public void setFlagValue(
    String _sAttributeCode) {

    //println("****** _sEntityType: " + _sEntityType);
    //println("****** _iEntityID: " + _iEntityID);

    String strAttributeValue = null;

    if (_sAttributeCode.equals(ANCYCLESTATUS)) {
      //println("****** _sAttributeCode1: " + _sAttributeCode);
      strAttributeValue = m_strRelProMgmtValue;

    } else if (_sAttributeCode.equals(RFAHWABR01)) {
      //println("****** _sAttributeCode2: " + _sAttributeCode);
      strAttributeValue = m_strSENDTOPMValue;

    } else {

      strAttributeValue = null;
    }
    //println("****** strAttributeValue set to: " + strAttributeValue);
    logMessage("****** strAttributeValue set to: " + strAttributeValue);

    if (strAttributeValue != null) {
      try {
        //println("****** m_prof: " + m_prof);
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

        //println("****** m_cbOn: " + m_cbOn);
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
   *  Find at least one ANNREVIEW entity that matches the condition and process the entity.
   *
   * @param _eg
   * @param _sEntityType
   * @param _sStatusAttribute
   */
  public void processEntityStatus(
    EntityGroup _eg,
    String _sEntityType,
    String _sStatusAttribute) {
    String strStatus = null;
    String strANNREVIEWDEF = null;

    m_bPass = false;

    for (int i = 0; i < _eg.getEntityItemCount(); i++) {

      EntityItem entity = _eg.getEntityItem(i);
      logMessage(
        "****************** Entity Type returned is "
          + entity.getEntityType()
          + " ei:"
          + i);

      if (entity.getEntityType().equals(_sEntityType)) {
        logMessage(
          "****************** Entitytype is "
            + entity.getEntityType());

        strStatus =
          getAttributeValue(
            entity.getEntityType(),
            entity.getEntityID(),
            _sStatusAttribute,
            "<em>" + DEF_NOT_POPULATED_HTML + "</em>");
        strANNREVIEWDEF =
          getAttributeValue(
            entity.getEntityType(),
            entity.getEntityID(),
            ANNREVIEWDEF,
            "<em>" + DEF_NOT_POPULATED_HTML + "</em>");

        //println("ANREVIEW status is " +  strStatus);
        //println("ANNREVIEWDEF is " +  strANNREVIEWDEF);
        //println("in for loop: " + i + " " + m_bPass);

        logMessage("****** ANREVIEW status is " + strStatus);
        logMessage("****** ANNREVIEWDEF is " + strANNREVIEWDEF);
        logMessage("****** in for loop: " + i + " " + m_bPass);

        if ((strStatus.equals(m_strAppValue)
                        || strStatus.equals(m_strProcRiskValue))
          && strANNREVIEWDEF.equals(m_strPDTLeadValue)) {

          m_bPass = true;
          //println("processEntityStatus m_bPass is " +  m_bPass);
          logMessage(
            "****** processEntityStatus m_bPass is " + m_bPass);

        }
      }
    } //end of for loop

    if (!m_bPass && strStatus != null && strANNREVIEWDEF != null) {
      if (!strANNREVIEWDEF.equals(m_strPDTLeadValue)) {
        println(
          "<br /><b>Fail:"
            + ANNREVIEWDEF_DESC
            + " is not "
            + m_strPDTLeadValue
            + "</b><br />");
      }

      if (!(strStatus.equals(m_strAppValue)
          || strStatus.equals(m_strProcRiskValue))) {
        println(
          "<br /><b>Fail:"
            + ANNREVIEW_DESC
            + " is not "
            + m_strAppValue
            + " or "
            + m_strProcRiskValue
            + "</b><br /><br />");
      }

      setReturnCode(FAIL);
    }
  }

  /**
   * getRelatorAttributeValue return a String of "Releasing Executive"
   * if ANNROLETYPE attribute value in all ANNOP relator is "Releasing Executive".
   * Otherwise, return null
   *
   * @return String
   * @param _eg
   * @param _sEntityType
   */
  public String getRelatorAttributeValue(
    EntityGroup _eg,
    String _sEntityType) {

    String strAnnExeAppName = null;
    boolean finish = false;
    for (int i = 0; i < _eg.getEntityItemCount(); i++) {

      EntityItem entity = _eg.getEntityItem(i);
      logMessage(
        "****************** Entity Type returned is "
          + entity.getEntityType()
          + " ei:"
          + i);

      if (entity != null
        && entity.getEntityType().equals(_sEntityType)) {
        logMessage(
          "****************** Entitytype is "
            + entity.getEntityType());
        logMessage(
          "****************** _eg.getMetaAttributeCount is "
            + _eg.getMetaAttributeCount());

        for (int j = 0; j < _eg.getMetaAttributeCount(); j++) {

          EANMetaAttribute ma = _eg.getMetaAttribute(j);
          logMessage("****************** ma is" + ma.toString());
          logMessage(
            "****************** getAttributeCode is 1"
              + ma.getAttributeCode());
          logMessage(
            "****************** ANNROLETYPE is 1" + ANNROLETYPE);

          if (ma.getAttributeCode().equals(ANNROLETYPE)
            && ma != null) {
			EANAttribute att =
			  entity.getAttribute(ma.getAttributeCode());
            logMessage(
              "****************** getAttributeCode is "
                + ma.getAttributeCode());
            logMessage(
              "****************** ANNROLETYPE is " + ANNROLETYPE);            
            if (att != null) {
              strAnnExeAppName = att.toString();
              if (strAnnExeAppName.equals(m_strRelExeRevValue)) {
                // compare if value is "Releasing Executive"
                logMessage(
                  "****************** strAnnExeAppName is "
                    + strAnnExeAppName);
                logMessage(
                  "****************** m_strRelExeRevValue is "
                    + m_strRelExeRevValue);
                //return strAnnExeAppName;
                finish = true;
                break;
              }
            }
          }
        } //end of for loop
        if (finish) {
        	break;
        }
      }
    } //end of for loop
    return strAnnExeAppName;
  }

  /**
   * getRelatorUserNameValue return a String value of "User Name"
   * if USERNAME attribute value on OP using the relator ANNOP is null, then return null.
   *
   * @return String
   * @param _eg
   * @param _sEntityType
   */

  public String getRelatorUserNameValue(
    EntityGroup _eg,
    String _sEntityType) {
    EntityItem entANNOP = null;
    EntityItem eiC = null;
    String strUserName = null;
    boolean finish = false;

    for (int i = 0; i < _eg.getEntityItemCount(); i++) {
      entANNOP = _eg.getEntityItem(i);
      logMessage(
        "****************** Relator Type returned is "
          + entANNOP.getEntityType()
          + " entANNOP: "
          + i);

      if (entANNOP != null) {
        if (entANNOP.getEntityType().equals(_sEntityType)) {
          logMessage(
            "****************** Relator type is "
              + entANNOP.getEntityType());
          logMessage(
            "****************** _eg.getMetaAttributeCount is "
              + _eg.getMetaAttributeCount());

          if (_eg.isRelator()) {
            for (int ii = 0;
              ii < entANNOP.getDownLinkCount();
              ii++) {
              eiC = (EntityItem) entANNOP.getDownLink(ii);

              if (eiC != null) {
                if (eiC.getEntityType().equals("OP")) {
                  strUserName =
                    getAttributeValue(
                      eiC.getEntityType(),
                      eiC.getEntityID(),
                      USERNAME,
                      DEF_NOT_POPULATED_HTML);
                  logMessage(
                    "****************** EntityType is "
                      + eiC.getEntityType());
                  logMessage(
                    "****************** EntityID is "
                      + eiC.getEntityID());
                  logMessage(
                    "****************** strUserName is "
                      + strUserName);

                  if (strUserName
                    .equals(DEF_NOT_POPULATED_HTML)) {
                    //return null;
                    finish = true;
                    break;
                  }
                }
              }
            }
            if (finish) {
            	break;
            }
          }
        }
      }
    } //end of for loop
	//return strUserName;
    return (finish ? null : strUserName);    
  }

  /**
   * isPastDate return true if the effDate is the current date or a past date
   *
   * @param _strEffDate the value of EffDate (ex) "LSCRSINTNPRICEEFFDATE"
   * @return boolean
   */
  public boolean isPastDate(String _strEffDate) {
    //println("_strEffDate: " + _strEffDate);
    int iyear;
    int imonth;
    int iday;
    int iyearNow;
    int imonthNow;
    int idayNow;
    boolean ret = false;


    if (_strEffDate == null || _strEffDate.equals("")) {
      logMessage("****** effDateValue is null");
      //return false;
    } else {

      //println("please get to here!!!: " + _strEffDate);
      //println("m_prof.getNow(): " + getNow());
      //println("m_prof.getNow().substring(0,4): " + getNow().substring(0,4));
      //println("m_prof.getNow().substring(5,7): " + getNow().substring(5,7));
      //println("m_prof.getNow().substring(8,10): " + getNow().substring(8,10));

      String strYearNow = getNow().substring(0, 4);
      String strMonthNow = getNow().substring(5, 7);
      String strdayNow = getNow().substring(8, 10);

      String strYear = null;
      String strMonth = null;
      String strday = null;

      //println("_strEffDate.length(): " + _strEffDate.length());
      if (_strEffDate.length() > 9) {
        strYear = _strEffDate.substring(0, 4);
        strMonth = _strEffDate.substring(5, 7);
        strday = _strEffDate.substring(8, 10);
        //println("strYear: " + strYear + ", strMonth: " + strMonth + ", strday: " + strday);
      } else {
        strYear = _strEffDate.substring(0, 4);
        strMonth = _strEffDate.substring(5, 7);
        strday = _strEffDate.substring(8);
      }

      iyear = Integer.parseInt(strYear);
      imonth = Integer.parseInt(strMonth);
      iday = Integer.parseInt(strday);

      iyearNow = Integer.parseInt(strYearNow);
      imonthNow = Integer.parseInt(strMonthNow);
      idayNow = Integer.parseInt(strdayNow);

      //println("DateValue is " +  iyear + "-" + imonth +  "-" + iday );
      //println("m_strTimeStampNow is " +  iyearNow + "-" + imonthNow +  "-" + idayNow );
      logMessage(
        "****** DateValue is " + iyear + "-" + imonth + "-" + iday);
      logMessage(
        "****** m_strTimeStampNow is "
          + iyearNow
          + "-"
          + imonthNow
          + "-"
          + idayNow);

      if (iyear < iyearNow) {
        //println("is a Past Date");
        logMessage("is a Past Date");
        //return true;
        ret = true;
      }
      if (!ret) {
	      if (iyear == iyearNow) {
	        if (imonth < imonthNow) {
	          //println("is a Past Date");
	          logMessage("****** is a Past Date");
	          //return true;
	          ret = true;
	        }
	        if (!ret) {
		        if (imonth == imonthNow) {
		          if (iday <= idayNow) {
		            //println("is a Past Date");
		            logMessage("****** is a Past Date");
		            //return true;
		            ret = true;
		          }
		        }
	        }
	      }
	      if (!ret) {
		      //println("is a Future Date");
		      logMessage("****** is a Future Date");
		      //return false;
		      ret = false;
	      }
      }
    }
    return ret;
  } //end of isPastDate

  /**
     * printPassMessage
     *
     * @author Administrator
     */
  public void printPassMessage() {
    println("<br /><b>send to Production Management</b><br /><br />");
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("ANQRLSEABR01.java,v 1.44 2006/03/13 19:42:03 couto Exp");
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
		println(EACustom.getMetaTags("ANQRLSEABR01.java"));
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
	
	private String dispNavAttributes(EntityItem _ei, EntityGroup _eg){
	   StringBuffer strBuffer = new StringBuffer();
	   strBuffer.append("<br /><br /><table summary=\"layout\" width=\"100%\">" + NEW_LINE);
	   strBuffer.append("<tr><td class=\"PsgLabel\" width=\"35%\">Navigation Attribute</td><td class=\"PsgLabel\" width=\"65%\">Value</td></tr>");
	   for(int i=0;i < _eg.getMetaAttributeCount();i++){
		  EANMetaAttribute ma = _eg.getMetaAttribute(i);
		  EANAttribute att = _ei.getAttribute(ma.getAttributeCode());
		  if(ma.isNavigate()) {
			 strBuffer.append("<tr><td class=\"PsgText\" width=\"35%\">"+ ma.getLongDescription()+"</td><td class=\"PsgText\" width=\"65%\">" +(att == null || att.toString().length() ==0 ? DEF_NOT_POPULATED_HTML : att.toString()) + "</td></tr>");
		  }
	   }
	   strBuffer.append("</table>");
	   return strBuffer.toString();
   }	

}
