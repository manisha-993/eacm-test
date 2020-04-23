//javac -classpath $HOME/abrgenerator/script/ibmwebas.jar:.:./db2java.zip:/usr/java_dev2/jre/lib/rt.jar:$HOME/generator/source/middleware.jar PokBaseABR.java > errors
//
// (c) Copyright International Business Machines Corporation, 2001
// All Rights Reserved.

// $Log: XLATEABR01.java,v $
// Revision 1.63  2006/06/27 02:46:54  yang
// adding abr print message
//
// Revision 1.62  2006/03/03 19:24:12  bala
// remove reference to Constants.CSS
//
// Revision 1.61  2006/01/25 17:47:11  yang
// Jtest changes
//
// Revision 1.60  2005/01/25 17:23:38  yang
// updated log message
//
// Revision 1.59  2004/11/05 18:36:57  yang
// update package ID
//
// Revision 1.58  2004/11/05 00:08:58  joan
// adjust code for PackageID constructor change
//
// Revision 1.57  2003/11/07 01:23:24  yang
// Adding setDGRptClass
//
// Revision 1.56  2003/09/18 19:31:48  yang
// add bala stuff to finally {
//
// Revision 1.55  2003/09/11 23:33:14  bala
// fix compile
//
// Revision 1.54  2003/09/11 23:20:02  bala
// backout unintended commit
//
// Revision 1.53  2003/09/11 22:43:30  bala
// add subscription sections to the abrs
//
// Revision 1.52  2003/08/21 21:41:58  yang
// fix null pointer
//
// Revision 1.51  2003/08/21 21:23:15  yang
// add log Message
//
// Revision 1.50  2003/08/18 22:11:40  yang
// syntax
//
// Revision 1.49  2003/08/18 22:02:05  yang
// syntax
//
// Revision 1.48  2003/08/18 21:44:16  yang
// syntax
//
// Revision 1.47  2003/08/18 20:54:07  yang
// added METAXLATEGRPSTATUS to update = Final
//
// Revision 1.46  2003/08/14 21:29:01  yang
// syntax
//
// Revision 1.45  2003/08/14 20:48:44  yang
// more syntax
//
// Revision 1.44  2003/08/14 20:36:32  yang
// syntax
//
// Revision 1.43  2003/08/12 23:31:16  yang
// updated setFlagValue
//
// Revision 1.42  2003/08/12 19:59:34  yang
// more syntax
//
// Revision 1.41  2003/08/12 19:40:43  yang
// syntax
//
// Revision 1.40  2003/08/08 19:38:34  dave
// more changes to syntax
//
// Revision 1.39  2003/08/08 19:29:58  dave
// syntax
//
// Revision 1.38  2003/08/08 19:25:38  dave
// Fixeing XLATEABR01
//
// Revision 1.37  2003/08/02 00:40:40  bala
// remove printDGSubmitString so that it prints only in the finally
//
// Revision 1.36  2003/08/01 22:34:46  bala
// streamline printDGSubmitString moved it up to abstracttask
//
// Revision 1.35  2003/08/01 00:56:55  bala
// fix the DGsubmit
//
// Revision 1.34  2003/08/01 00:19:27  bala
// move up dgsubmit so that it prints first
//
// Revision 1.33  2003/07/31 22:38:19  bala
// plug in DGsubmit header
//
// Revision 1.32  2003/07/17 19:17:27  yang
// syntax
//
// Revision 1.31  2003/07/17 18:43:22  yang
// addition log to pId
//
// Revision 1.30  2003/07/16 23:58:10  yang
// adding log files
//
// Revision 1.29  2003/07/16 23:23:13  yang
// Add putPDHPackage
//
// Revision 1.27  2003/07/15 20:52:02  yang
// Adding log
//

package COM.ibm.eannounce.abr.pcd;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.opicmpdh.translation.*;
//import COM.ibm.opicmpdh.translation.Translation.*;
import java.util.*;
import java.io.*;
//import java.sql.*;

/**
 *  Description of the Class
 *
 *@author     Administrator
 *@created    August 1, 2003
 */
public class XLATEABR01 extends PokBaseABR {
  /**
   *  Execute ABR.
   */

  //ABR
  public final static String ABR = new String("XLATEABR01");

  // Class constants
  /**
   *  Description of the Field
   */
  public final static String DEF_NOT_POPULATED_HTML =
    new String("** Not Populated **");
  /**
   *  Description of the Field
   */
  public final static String NULL = new String("");

  /**
   *  Description of the Field
   */
  public final static String XLATEGRP = new String("XLATEGRP");
  /**
     * METAXLATEGRP
     *
     */
    public final static String METAXLATEGRP = new String("METAXLATEGRP");
  /**
   *  Description of the Field
   */
  public final static String XLATEGRP_DESC = new String("Translation Group");
  /**
   *  Description of the Field
   */
  public final static String XLATEGRPSTATUS = new String("XLATEGRPSTATUS");
  /**
     * METAXLATEGRPSTATUS
     *
     */
    public final static String METAXLATEGRPSTATUS =
    new String("METAXLATEGRPSTATUS");

  /**
     * XLSTATUS2
     *
     */
    public final static String XLSTATUS2 = new String("XLSTATUS2");
  /**
     * XLSTATUS3
     *
     */
    public final static String XLSTATUS3 = new String("XLSTATUS3");
  /**
     * XLSTATUS4
     *
     */
    public final static String XLSTATUS4 = new String("XLSTATUS4");
  /**
     * XLSTATUS5
     *
     */
    public final static String XLSTATUS5 = new String("XLSTATUS5");
  /**
     * XLSTATUS6
     *
     */
    public final static String XLSTATUS6 = new String("XLSTATUS6");
  /**
     * XLSTATUS7
     *
     */
    public final static String XLSTATUS7 = new String("XLSTATUS7");
  /**
     * XLSTATUS8
     *
     */
    public final static String XLSTATUS8 = new String("XLSTATUS8");
  /**
     * XLSTATUS9
     *
     */
    public final static String XLSTATUS9 = new String("XLSTATUS9");
  /**
     * XLSTATUS10
     *
     */
    public final static String XLSTATUS10 = new String("XLSTATUS10");
  /**
     * XLSTATUS11
     *
     */
    public final static String XLSTATUS11 = new String("XLSTATUS11");
  /**
     * XLSTATUS12
     *
     */
    public final static String XLSTATUS12 = new String("XLSTATUS12");
  /**
     * XLSTATUS13
     *
     */
    public final static String XLSTATUS13 = new String("XLSTATUS13");

  /**
     * METAXLSTATUS2
     *
     */
    public final static String METAXLSTATUS2 = new String("METAXLSTATUS2");
  /**
     * METAXLSTATUS3
     *
     */
    public final static String METAXLSTATUS3 = new String("METAXLSTATUS3");
  /**
     * METAXLSTATUS4
     *
     */
    public final static String METAXLSTATUS4 = new String("METAXLSTATUS4");
  /**
     * METAXLSTATUS5
     *
     */
    public final static String METAXLSTATUS5 = new String("METAXLSTATUS5");
  /**
     * METAXLSTATUS6
     *
     */
    public final static String METAXLSTATUS6 = new String("METAXLSTATUS6");
  /**
     * METAXLSTATUS7
     *
     */
    public final static String METAXLSTATUS7 = new String("METAXLSTATUS7");
  /**
     * METAXLSTATUS8
     *
     */
    public final static String METAXLSTATUS8 = new String("METAXLSTATUS8");
  /**
     * METAXLSTATUS9
     *
     */
    public final static String METAXLSTATUS9 = new String("METAXLSTATUS9");
  /**
     * METAXLSTATUS10
     *
     */
    public final static String METAXLSTATUS10 = new String("METAXLSTATUS10");
  /**
     * METAXLSTATUS11
     *
     */
    public final static String METAXLSTATUS11 = new String("METAXLSTATUS11");
  /**
     * METAXLSTATUS12
     *
     */
    public final static String METAXLSTATUS12 = new String("METAXLSTATUS12");
  /**
     * METAXLSTATUS13
     *
     */
    public final static String METAXLSTATUS13 = new String("METAXLSTATUS13");

  //private final String strStatus = null;
  //private final String strXLLANGUAGES = null;

  //private EntityGroup m_egParent = null; //XLATEGRP
  //private EntityGroup m_egANNREV = null; //ANNREVIEW
  //private EntityGroup m_egANNOP = null; //OP

  //private EntityItem m_eiParent = null; //XLATEGRP

  private final String m_strRelProMgmtValue = "F0030";
  //XLATEGRPSTATUS  Ststus Attribute (Released to Production Management)
  private final String m_strRelProMgmtValue1 = "F0030";
  //METAXLATEGRPSTATUS  Ststus Attribute (Released to Production Management)

  private final String m_strXLSTATUS2Value = "XL20";
  //XLSTATUS2  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS3Value = "XL20";
  //XLSTATUS3  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS4Value = "XL20";
  //XLSTATUS4  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS5Value = "XL20";
  //XLSTATUS5  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS6Value = "XL20";
  //XLSTATUS6  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS7Value = "XL20";
  //XLSTATUS7  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS8Value = "XL20";
  //XLSTATUS8  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS9Value = "XL20";
  //XLSTATUS9  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS10Value = "XL20";
  //XLSTATUS10  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS11Value = "XL20";
  //XLSTATUS10  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS12Value = "XL20";
  //XLSTATUS10  Ststus Attribute (Awaiting Translation)
  private final String m_strXLSTATUS13Value = "XL20";
  //XLSTATUS10  Ststus Attribute (Awaiting Translation)

  //private final boolean m_bPass = false;

  /**
   *  Description of the Method
   */
  public void execute_run() {
    EntityGroup eg;
    EntityItem ei;
    EANFlagAttribute fa;
    String strBillingCode;
    EANAttribute att;
    int isFlagClassID;
    PackageID pkID;
    TranslationPackage tpk;
    TranslationDataRequest tdrThis;
    StringWriter writer;
    String x;
    try {

      start_ABRBuild(false);
      buildReportHeader();
      setControlBlock();
      logMessage(
        "************ Root Entity Type and id "
          + getEntityType()
          + ":"
          + getEntityID());
      setReturnCode(PASS);

      // Lets get the entity information here
      eg =
        m_db.getEntityGroup(m_prof, m_abri.getEntityType(), "Edit");
      ei =
        new EntityItem(
          eg,
          m_prof,
          m_db,
          m_abri.getEntityType(),
          m_abri.getEntityID());

      fa =
        (EANFlagAttribute) ei.getAttribute("BILLINGCODE");
      strBillingCode = "";
      if (fa != null) {
        strBillingCode = fa.getFirstActiveFlagCode();
      }
      if (strBillingCode.equals("PCD")) {
        strBillingCode = "";
      }

      //    logMessage("**********EntityItem: " + ei.dump(false));
      println(
        "<b><tr><td class=\"PsgText\" width=\"100%\" >"
          + getEntityType()
          + ":</b></td><b><td class=\"PsgText\" width=\"100%\">"
          + getEntityID()
          + "</b></td>");
      printNavigateAttributes(ei, eg, true);
      //    EntityItem _eiXLATEGRP = m_db.refreshEntityItem(m_prof, _eg, _ei);
      //    logMessage("**********EntityItem _eiXLATEGRP: " + _eiXLATEGRP.dump(false));
      if (eg.getEntityType().equals("XLATEGRP")) {
        logMessage("**********Using Entity #1:" + getEntityType());
        att = ei.getAttribute("XLLANGUAGES");

        if (att != null) {

          EANFlagAttribute ema = (EANFlagAttribute) att;
          MetaFlag[] amf = (MetaFlag[]) ema.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              String sFlagClass = amf[f].getFlagCode().trim();
              logMessage(
                "*************** sFlagClass: " + sFlagClass);
              if (sFlagClass.equals("")) {
                setReturnCode(FAIL);
              } else if (sFlagClass.equals("2")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS2);
              } else if (sFlagClass.equals("3")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS3);
              } else if (sFlagClass.equals("4")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS4);
              } else if (sFlagClass.equals("5")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS5);
              } else if (sFlagClass.equals("6")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS6);
              } else if (sFlagClass.equals("7")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS7);
              } else if (sFlagClass.equals("8")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS8);
              } else if (sFlagClass.equals("9")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS9);
              } else if (sFlagClass.equals("10")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS10);
              } else if (sFlagClass.equals("11")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS11);
              } else if (sFlagClass.equals("12")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS12);
              } else if (sFlagClass.equals("13")) {
                setFlagValue(
                  XLATEGRP,
                  getEntityID(),
                  XLSTATUS13);
              }
              isFlagClassID = Integer.parseInt(sFlagClass);
              pkID =
                new PackageID(
                  getEntityType(),
                  getEntityID(),
                  isFlagClassID,
                  "TRANSOUTBOUNDX" + isFlagClassID,
                  m_strNow,
                  strBillingCode);
              logMessage(
                "*************** pkID: " + pkID.toString());
              tpk =
                Translation.generatePDHPackage(
                  m_db,
                  m_prof,
                  pkID);
              //          logMessage("*************** tpk: " + tpk.dump());
              COM
                .ibm
                .opicmpdh
                .translation
                .Translation
                .putPDHPackage(
                m_db,
                m_prof,
                tpk);
            }
          }
        } else {
          setReturnCode(FAIL);
          println("*The Translation Package you're working with does not have any Requested Languages Selected");

        }

        if (getReturnCode() == PASS) {
          setFlagValue(XLATEGRP, getEntityID(), XLATEGRPSTATUS);
        }

      } else {
        logMessage("**********Using Entity #2:" + getEntityType());
        att = ei.getAttribute("XLLANGUAGES");

        if (att != null) {

          EANFlagAttribute ema = (EANFlagAttribute) att;
          MetaFlag[] amf = (MetaFlag[]) ema.get();
          for (int f = 0; f < amf.length; f++) {
            if (amf[f].isSelected()) {
              String sFlagClass = amf[f].getFlagCode().trim();
              logMessage(
                "***************   sFlagClass: " + sFlagClass);
              if (sFlagClass.equals("")) {
                setReturnCode(FAIL);
              } else if (sFlagClass.equals("2")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS2);
              } else if (sFlagClass.equals("3")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS3);
              } else if (sFlagClass.equals("4")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS4);
              } else if (sFlagClass.equals("5")) {
                //System.out.println("We are getting here"+sFlagClass);
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS5);
              } else if (sFlagClass.equals("6")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS6);
              } else if (sFlagClass.equals("7")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS7);
              } else if (sFlagClass.equals("8")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS8);
              } else if (sFlagClass.equals("9")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS9);
              } else if (sFlagClass.equals("10")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS10);
              } else if (sFlagClass.equals("11")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS11);
              } else if (sFlagClass.equals("12")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS12);
              } else if (sFlagClass.equals("13")) {
                setFlagValue(
                  METAXLATEGRP,
                  getEntityID(),
                  METAXLSTATUS13);
              }
              isFlagClassID = Integer.parseInt(sFlagClass);
              pkID =
                new PackageID(
                  getEntityType(),
                  getEntityID(),
                  isFlagClassID,
                  "TRANSOUTBOUNDX" + isFlagClassID,
                  m_strNow,
                  strBillingCode);
              logMessage(
                "***************   pkID: " + pkID.toString());
              tpk =
                Translation.generatePDHPackage(
                  m_db,
                  m_prof,
                  pkID);
              //                logMessage("***************   tpk: " + tpk.dump());
              COM
                .ibm
                .opicmpdh
                .translation
                .Translation
                .putPDHPackage(
                m_db,
                m_prof,
                tpk);
              tdrThis =
                tpk.getDataRequest();
              //                logMessage("***************   tpk.getDataRequest: " + tpk.getDataRequest());
            }
          }
        } else {
          setReturnCode(FAIL);
        }

        if (getReturnCode() == PASS) {
          setFlagValue(
            METAXLATEGRP,
            getEntityID(),
            METAXLATEGRPSTATUS);
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
        "<h3><font color=red>"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>UpdatePDH error: "
          + le.getMessage()
          + "</font></h3>");
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
      setDGRptName("XLATEABR01"); //Set the report name
      setDGRptClass("XLATEABR01"); //Set the report class
      printDGSubmitString();
      //Stuff into report for subscription and notification
      // Tack on the DGString
      buildReportFooter();
      // make sure the lock is released
      if (!isReadOnly()) {
        clearSoftLock();
      }
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
   *  Get the getRevision
   *
   *@return    java.lang.String
   */
  public String getRevision() {
    return new String("$Revision: 1.63 $");
  }

  /**
   *  Sets the controlBlock attribute of the XLATEABR01 object
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
   *  Sets the flagValue attribute of the XLATEABR01 object
   *
   *@param  _sEntityType     The new flagValue value
   *@param  _iEntityID       The new flagValue value
   *@param  _sAttributeCode  The new flagValue value
   */
  public void setFlagValue(
    String _sEntityType,
    int _iEntityID,
    String _sAttributeCode) {

    //println("****** _sEntityType: " + _sEntityType);
    //println("****** _iEntityID: " + _iEntityID);

    String strAttributeValue = null;

    if (_sAttributeCode.equals(XLATEGRPSTATUS)) {
      logMessage("****** XLATEGRPSTATUS set to: " + XLATEGRPSTATUS);
      strAttributeValue = m_strRelProMgmtValue;

    } else if (_sAttributeCode.equals(METAXLATEGRPSTATUS)) {
      //println("****** METAXLATEGRPSTATUS: " + _sAttributeCode);
      logMessage(
        "****** METAXLATEGRPSTATUS set to: " + METAXLATEGRPSTATUS);
      strAttributeValue = m_strRelProMgmtValue1;

    } else if (_sAttributeCode.equals(XLSTATUS2)) {
      logMessage("****** XLSTATUS2 set to: " + XLSTATUS2);
      strAttributeValue = m_strXLSTATUS2Value;

    } else if (_sAttributeCode.equals(XLSTATUS3)) {
      logMessage("****** XLSTATUS3 set to: " + XLSTATUS3);
      strAttributeValue = m_strXLSTATUS3Value;

    } else if (_sAttributeCode.equals(XLSTATUS4)) {
      logMessage("****** XLSTATUS4 set to: " + XLSTATUS4);
      strAttributeValue = m_strXLSTATUS4Value;

    } else if (_sAttributeCode.equals(XLSTATUS5)) {
      logMessage("****** XLSTATUS5 set to: " + XLSTATUS5);
      strAttributeValue = m_strXLSTATUS5Value;

    } else if (_sAttributeCode.equals(XLSTATUS6)) {
      logMessage("****** XLSTATUS6 set to: " + XLSTATUS6);
      strAttributeValue = m_strXLSTATUS6Value;

    } else if (_sAttributeCode.equals(XLSTATUS7)) {
      logMessage("****** XLSTATUS7 set to: " + XLSTATUS7);
      strAttributeValue = m_strXLSTATUS7Value;

    } else if (_sAttributeCode.equals(XLSTATUS8)) {
      logMessage("****** XLSTATUS8 set to: " + XLSTATUS8);
      strAttributeValue = m_strXLSTATUS8Value;

    } else if (_sAttributeCode.equals(XLSTATUS9)) {
      logMessage("****** XLSTATUS9 set to: " + XLSTATUS9);
      strAttributeValue = m_strXLSTATUS9Value;

    } else if (_sAttributeCode.equals(XLSTATUS10)) {
      logMessage("****** sXLSTATUS10 set to: " + XLSTATUS10);
      strAttributeValue = m_strXLSTATUS10Value;

    } else if (_sAttributeCode.equals(XLSTATUS11)) {
      logMessage("****** sXLSTATUS11 set to: " + XLSTATUS11);
      strAttributeValue = m_strXLSTATUS11Value;

    } else if (_sAttributeCode.equals(XLSTATUS12)) {
      logMessage("****** sXLSTATUS12 set to: " + XLSTATUS12);
      strAttributeValue = m_strXLSTATUS12Value;

    } else if (_sAttributeCode.equals(XLSTATUS13)) {
      logMessage("****** sXLSTATUS13 set to: " + XLSTATUS13);
      strAttributeValue = m_strXLSTATUS13Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS2)) {
      logMessage("****** sMETAXLSTATUS2 set to: " + METAXLSTATUS2);
      strAttributeValue = m_strXLSTATUS2Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS3)) {
      logMessage("****** sMETAXLSTATUS3 set to: " + METAXLSTATUS3);
      strAttributeValue = m_strXLSTATUS3Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS4)) {
      logMessage("****** sMETAXLSTATUS4 set to: " + METAXLSTATUS4);
      strAttributeValue = m_strXLSTATUS4Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS5)) {
      logMessage("****** sMETAXLSTATUS5 set to: " + METAXLSTATUS5);
      strAttributeValue = m_strXLSTATUS5Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS6)) {
      logMessage("****** sMETAXLSTATUS6 set to: " + METAXLSTATUS6);
      strAttributeValue = m_strXLSTATUS6Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS7)) {
      logMessage("****** sMETAXLSTATUS7 set to: " + METAXLSTATUS7);
      strAttributeValue = m_strXLSTATUS7Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS8)) {
      logMessage("****** sMETAXLSTATUS8 set to: " + METAXLSTATUS8);
      strAttributeValue = m_strXLSTATUS8Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS9)) {
      logMessage("****** sMETAXLSTATUS9 set to: " + METAXLSTATUS9);
      strAttributeValue = m_strXLSTATUS9Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS10)) {
      logMessage("****** sMETAXLSTATUS10 set to: " + METAXLSTATUS10);
      strAttributeValue = m_strXLSTATUS10Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS11)) {
      logMessage("****** sMETAXLSTATUS11 set to: " + METAXLSTATUS11);
      strAttributeValue = m_strXLSTATUS11Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS12)) {
      logMessage("****** sMETAXLSTATUS12 set to: " + METAXLSTATUS12);
      strAttributeValue = m_strXLSTATUS12Value;

    } else if (_sAttributeCode.equals(METAXLSTATUS13)) {
      logMessage("****** sMETAXLSTATUS13 set to: " + METAXLSTATUS13);
      strAttributeValue = m_strXLSTATUS13Value;

    } else {

      strAttributeValue = null;
    }
    //println("****** strAttributeValue set to: " + strAttributeValue);
    //logMessage("****** strAttributeValue set to: " + strAttributeValue);

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
   *  Description of the Method
   */
  public void printPassMessage() {
    println("<br /><b>send to Production Management</b><br /><br />");
  }

  /**
   *  Gets the version attribute of the XLATEABR01 class
   *
   *@return    The version value
   */
  public static String getVersion() {
    return ("$Id: XLATEABR01.java,v 1.63 2006/06/27 02:46:54 yang Exp $");
  }

  /**
   *  Gets the aBRVersion attribute of the XLATEABR01 object
   *
   *@return    The aBRVersion value
   */
  public String getABRVersion() {
    return getVersion();
  }

}
