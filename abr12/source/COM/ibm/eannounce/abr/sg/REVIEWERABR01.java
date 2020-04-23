//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
// REVIEWERABR01.java,v
// Revision 1.67  2008/01/30 19:39:17  wendy
// Cleanup RSA warnings
//
// Revision 1.66  2006/03/03 19:23:29  bala
// remove reference to Constants.CSS
//
// Revision 1.65  2006/01/24 16:49:33  yang
// Jtest Changes
//
// Revision 1.64  2005/01/25 18:23:36  yang
// updated log message
//
// Revision 1.63  2004/09/22 01:16:44  yang
// minor changes
//
// Revision 1.62  2004/09/22 00:44:26  yang
// fix null Pointer
//
// Revision 1.61  2004/09/11 16:42:23  yang
// Addin other checks
//
// Revision 1.60  2004/08/23 21:14:25  yang
// minor syntax
//
// Revision 1.59  2004/08/23 17:59:09  yang
// adding new abr
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.io.*;
//import java.sql.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Owner
 */
public class REVIEWERABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = new String("REVIEWERABR01");

  //ANNREVIEW Attributes:
  /**
     * ANNCODENAME
     *
     */
    public final static String ANNCODENAME = new String("ANNCODENAME");
  /**
     * ANNREVIEWSTATUS
     *
     */
    public final static String ANNREVIEWSTATUS = new String("ANNREVIEWSTATUS");
  /**
     * ANNREVIEWDEF
     *
     */
    public final static String ANNREVIEWDEF = new String("ANNREVIEWDEF");
  /**
     * ANREVIEW
     *
     */
    public final static String ANREVIEW = new String("ANREVIEW");
  /**
     * ANREVDATE
     *
     */
    public final static String ANREVDATE = new String("ANREVDATE");
  /**
     * ANREVIEWCOMMENT
     *
     */
    public final static String ANREVIEWCOMMENT = new String("ANREVIEWCOMMENT");
  /**
     * ANREVIEWDESCRIPTION
     *
     */
    public final static String ANREVIEWDESCRIPTION =
    new String("ANREVIEWDESCRIPTION");

  //Reviewer Attributes:
  /**
     * NAME
     *
     */
    public final static String NAME = new String("NAME");
  /**
     * ROLECODE
     *
     */
    public final static String ROLECODE = new String("ROLECODE");
  /**
     * INTERNETID
     *
     */
    public final static String INTERNETID = new String("INTERNETID");
  /**
     * REVIEWERSTATUS
     *
     */
    public final static String REVIEWERSTATUS = new String("REVIEWERSTATUS");

  //OP Attributes:
  /**
     * FIRSTNAME
     *
     */
    public final static String FIRSTNAME = new String("FIRSTNAME");
  /**
     * LASTNAME
     *
     */
    public final static String LASTNAME = new String("LASTNAME");
  /**
     * USERNAME
     *
     */
    public final static String USERNAME = new String("USERNAME");

  private EntityGroup m_egParent = null; //root ANNREVIEW
  private EntityItem m_eiParent = null; //root ANNREVIEW

  //private final String m_strParentAnnCodeNameValue = null;
  //ANNCODENAME     Value of Parent ANNREVIEW

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    StringWriter writer;
    String x = null;
    try {

      start_ABRBuild();
      m_egParent = m_elist.getParentEntityGroup();
      m_eiParent = m_egParent.getEntityItem(0);

      if (m_egParent == null) {
        println(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:1: m_egParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      if (m_eiParent == null) {
        println(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:2: m_eiParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }

      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Request to Work on Entity:"
          + m_eiParent.getEntityType()
          + ":"
          + m_eiParent.getEntityID());
      buildReportHeader();
      setControlBlock();
      setDGTitle(setDGName(m_eiParent, ABR));

      displayHeader(m_egParent, m_eiParent);

      // Default the ABR to pass...
      setReturnCode(PASS);

      //Checking status

      checkANNREVIEWstatus();
      //          checkREVIEWERstatus();
      //          checkOPstatus();
      //          checkOPWGstatus();

      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Setup Complete:"
          + m_eiParent.getEntityType()
          + ":"
          + m_eiParent.getEntityID());

      logMessage("***getReturnCode()***" + getReturnCode());
      if (getReturnCode() == PASS) {
        //reRun Extract Action after DB updated
        m_prof.setValOn(getValOn());
        m_prof.setEffOn(getEffOn());
        start_ABRBuild();
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
        "<h3>"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println("<h3>UpdatePDH error: " + le.getMessage() + "</font></h3>");
      logError(le.getMessage());
    } catch (Exception exc) {
      // Report this error to both the datbase log and the PrintWriter
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

    } finally {
      //Everything is fine...so lets pass
      if (getReturnCode() == PASS) {
        setReturnCode(PASS);
      // set DG submit string
                setDGString(getABRReturnCode());
                setDGRptName("REVIEWERABR01"); //Set the report name
                setDGRptClass("REVIEWERABR01"); //Set the report class
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
  }
  /**
     * printFAILmessage
     *
     * @param _eg
     * @author Owner
     */
    public void printFAILmessage(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " is not at final status.</b>");
    setReturnCode(FAIL);
  }
  /**
     * printWARNINGmessage
     *
     * @param _eg
     * @author Owner
     */
    public void printWARNINGmessage(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " does not exist (Warning - Pass)</b>");
  }
  /**
     * printFAILmessage_2
     *
     * @param _eg
     * @author Owner
     */
    public void printFAILmessage_2(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status</b>");
    setReturnCode(FAIL);
  }
  /**
     * printWARNINGmessage_2
     *
     * @param _eg
     * @author Owner
     */
    public void printWARNINGmessage_2(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status (Warning - Pass)</b>");
  }
  /**
     * printFAILmessage_3
     *
     * @param _eg
     * @author Owner
     */
    public void printFAILmessage_3(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review'</b>");
    setReturnCode(FAIL);
  }
  /**
     * printWARNINGmessage_3
     *
     * @param _eg
     * @author Owner
     */
    public void printWARNINGmessage_3(EntityGroup _eg) {
    println(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");
  }

  /**
     * checkANNREVIEWstatus
     *
     * @author Owner
     */
    public void checkANNREVIEWstatus() {
    String strStatus = null;
    String strStatus1 = null;
    String strStatus2 = null;
    String strStatus3 = null;
    String strStatus4 = null;
    String strStatus5 = null;
    String strStatus6 = null;
    String strStatus7 = null;
    String strStatus8 = null;
    String strStatus9 = null;
    String strStatus10 = null;
    String strStatus11 = null;
    String strStatus12 = null;
    String strStatus13 = null;
    String strStatus14 = null;

    m_egParent = m_elist.getParentEntityGroup();
    m_eiParent = m_egParent.getEntityItem(0);

    if (m_egParent == null) {
      logMessage("********** 1 Announcement Review Not found");
      setReturnCode(FAIL);
    } else {
      for (int i = 0; i < m_egParent.getEntityItemCount(); i++) {
        //logMessage("m_egParent.getEntityItem(i):" + m_egParent.getEntityItem(i));
        EntityItem ei = m_egParent.getEntityItem(i);

        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[15];
          atts[0] = ei.getAttribute("ANREVASAREVIEW");
          atts[1] = ei.getAttribute("ANREVCANDNREVIEW");
          atts[2] = ei.getAttribute("ANREVCDREVIEW");
          atts[3] = ei.getAttribute("ANREVDCMREVIEW");
          atts[4] = ei.getAttribute("ANREVDIREVIEW");
          atts[5] = ei.getAttribute("ANREVGARREVIEW");
          atts[6] = ei.getAttribute("ANREVIGFREVIEW");
          atts[7] = ei.getAttribute("ANREVIMCREVIEW");
          atts[8] = ei.getAttribute("ANREVIPMTREVIEW");
          atts[9] = ei.getAttribute("ANREVLGLREVIEW");
          atts[10] = ei.getAttribute("ANREVMKTGREVIEW");
          atts[11] = ei.getAttribute("ANREVODMREVIEW");
          atts[12] = ei.getAttribute("ANREVPDTREVIEW");
          atts[13] = ei.getAttribute("ANREVPRCREVIEW");
          atts[14] = ei.getAttribute("ANREVSANDDREVIEW");

          //                      logMessage("atts[0]:" + atts[0]);
          //                      logMessage("atts[1]:" + atts[1]);
          //                      logMessage("atts[2]:" + atts[2]);
          //                      logMessage("atts[3]:" + atts[3]);
          //                      logMessage("atts[4]:" + atts[4]);
          //                      logMessage("atts[5]:" + atts[5]);
          //                      logMessage("atts[6]:" + atts[6]);
          //                      logMessage("atts[7]:" + atts[7]);
          //                      logMessage("atts[8]:" + atts[8]);
          //                      logMessage("atts[9]:" + atts[9]);
          //                      logMessage("atts[10]:" + atts[10]);
          //                      logMessage("atts[11]:" + atts[11]);
          //                      logMessage("atts[12]:" + atts[12]);
          //                      logMessage("atts[13]:" + atts[13]);
          //                      logMessage("atts[14]:" + atts[14]);

          strStatus = (atts[0].toString());
          strStatus1 = (atts[1].toString());
          strStatus2 = (atts[2].toString());
          strStatus3 = (atts[3].toString());
          strStatus4 = (atts[4].toString());
          strStatus5 = (atts[5].toString());
          strStatus6 = (atts[6].toString());
          strStatus7 = (atts[7].toString());
          strStatus8 = (atts[8].toString());
          strStatus9 = (atts[9].toString());
          strStatus10 = (atts[10].toString());
          strStatus11 = (atts[11].toString());
          strStatus12 = (atts[12].toString());
          strStatus13 = (atts[13].toString());
          strStatus14 = (atts[14].toString());

          //                      logMessage("strStatus for ANNREVIEW:" + strStatus);
          //                      logMessage("strStatus1 for ANNREVIEW:" + strStatus1);
          //                      logMessage("strStatus2 for ANNREVIEW:" + strStatus2);
          //                      logMessage("strStatus3 for ANNREVIEW:" + strStatus3);
          //                      logMessage("strStatus4 for ANNREVIEW:" + strStatus4);
          //                      logMessage("strStatus5 for ANNREVIEW:" + strStatus5);
          //                      logMessage("strStatus6 for ANNREVIEW:" + strStatus6);
          //                      logMessage("strStatus7 for ANNREVIEW:" + strStatus7);
          //                      logMessage("strStatus8 for ANNREVIEW:" + strStatus8);
          //                      logMessage("strStatus9 for ANNREVIEW:" + strStatus9);
          //                      logMessage("strStatus10 for ANNREVIEW:" + strStatus10);
          //                      logMessage("strStatus11 for ANNREVIEW:" + strStatus11);
          //                      logMessage("strStatus12 for ANNREVIEW:" + strStatus12);
          //                      logMessage("strStatus13 for ANNREVIEW:" + strStatus13);
          //                      logMessage("strStatus14 for ANNREVIEW:" + strStatus14);

          println(
            "<br><br><b>"
              + m_egParent.getLongDescription()
              + " Entity ID: "
              + ei.getEntityID());
          println(displayAttributes(ei, m_egParent, true));
          if (strStatus.equals("Required")) {
            logMessage("****ANREVASAREVIEW equals Required");
            checkOPWGASAstatus();
          }
          if (strStatus1.equals("Required")) {
            logMessage("****ANREVCANDNREVIEW equals Required");
            checkOPWGCNstatus();
          }
          if (strStatus2.equals("Required")) {
            logMessage("****ANREVCDREVIEW equals Required");
            checkOPWGCDstatus();
          }
          if (strStatus3.equals("Required")) {
            logMessage("****ANREVDCMREVIEW equals Required");
            checkOPWGDCMstatus();
          }
          if (strStatus4.equals("Required")) {
            logMessage("****ANREVDIREVIEW equals Required");
            checkOPWGDIstatus();
          }
          if (strStatus5.equals("Required")) {
            logMessage("****ANREVGARREVIEW equals Required");
            checkOPWGGARstatus();
          }
          if (strStatus6.equals("Required")) {
            logMessage("****ANREVIGFREVIEW equals Required");
            checkOPWGIGFstatus();
          }
          if (strStatus7.equals("Required")) {
            logMessage("****ANREVIMCREVIEW equals Required");
            checkOPWGIMCstatus();
          }
          if (strStatus8.equals("Required")) {
            logMessage("****ANREVIPMTREVIEW equals Required");
            checkOPWGIPMTstatus();
          }
          if (strStatus9.equals("Required")) {
            logMessage("****ANREVLGLREVIEW equals Required");
            checkOPWGLEGALstatus();
          }
          if (strStatus10.equals("Required")) {
            logMessage("****ANREVMKTGREVIEW equals Required");
            checkOPWGMRstatus();
          }
          if (strStatus11.equals("Required")) {
            logMessage("****ANREVODMREVIEW equals Required");
            checkOPWGODMstatus();
          }
          if (strStatus12.equals("Required")) {
            logMessage("****ANREVPDTREVIEW equals Required");
            checkOPWGPDTstatus();
          }
          if (strStatus13.equals("Required")) {
            logMessage("****ANREVPRCREVIEW equals Required");
            checkOPWGPRstatus();
          }
          if (strStatus14.equals("Required")) {
            logMessage("****ANREVSANDDREVIEW equals Required");
            checkOPWGWWSDstatus();
          }
        }
      }
    }
  } //checkANNREVIEWstatus

  /**
     * checkREVIEWERstatus
     *
     * @author Owner
     */
    public void checkREVIEWERstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("REVIEWER");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[3];
          atts[0] = ei.getAttribute(NAME);
          atts[1] = ei.getAttribute(ROLECODE);
          atts[2] = ei.getAttribute(INTERNETID);
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);
          logMessage("atts[2]:" + atts[2]);
          println(
            "<br><br><b>"
              + _eg.getLongDescription()
              + " Entity ID: "
              + ei.getEntityID());
          println(displayAttributes(ei, _eg, true));
        }
      }
    }
  } //checkREVIEWERstatus

  /**
     * checkOPWGASAstatus
     *
     * @author Owner
     */
    public void checkOPWGASAstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("ASA")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "ASA",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGASAstatus

  /**
     * checkOPWGCNstatus
     *
     * @author Owner
     */
    public void checkOPWGCNstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;
    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("CN")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "CN",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGCNstatus

  /**
     * checkOPWGCDstatus
     *
     * @author Owner
     */
    public void checkOPWGCDstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;
    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("CD")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "CD",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGCDstatus

  /**
     * checkOPWGDCMstatus
     *
     * @author Owner
     */
    public void checkOPWGDCMstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("DCM")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "DCM",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                                                .ibm
                                                .opicmpdh
                                                .middleware
                                                .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                                    .ibm
                                    .opicmpdh
                                    .middleware
                                    .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGDCMstatus

  /**
     * checkOPWGDIstatus
     *
     * @author Owner
     */
    public void checkOPWGDIstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;
    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("DI")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                                        + " "
                                        + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "DI",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGDIstatus

  /**
     * checkOPWGGARstatus
     *
     * @author Owner
     */
    public void checkOPWGGARstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("GAR")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "GAR",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGGARstatus

  /**
     * checkOPWGIGFstatus
     *
     * @author Owner
     */
    public void checkOPWGIGFstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("IGF")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "IGF",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                                    .ibm
                                    .opicmpdh
                                    .middleware
                                    .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGIGFstatus

  /**
     * checkOPWGIMCstatus
     *
     * @author Owner
     */
    public void checkOPWGIMCstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("IMC")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                                        + " "
                                        + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "IMC",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGIMCstatus

  /**
     * checkOPWGIPMTstatus
     *
     * @author Owner
     */
    public void checkOPWGIPMTstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("IPMT")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "IPMT",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGIPMTstatus

  /**
     * checkOPWGLEGALstatus
     *
     * @author Owner
     */
    public void checkOPWGLEGALstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("LEGAL")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "LEGAL",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGLEGALstatus

  /**
     * checkOPWGMRstatus
     *
     * @author Owner
     */
    public void checkOPWGMRstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("MR")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "MR",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGMRstatus

  /**
     * checkOPWGODMstatus
     *
     * @author Owner
     */
    public void checkOPWGODMstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("ODM")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "ODM",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGODMstatus

  /**
     * checkOPWGPDTstatus
     *
     * @author Owner
     */
    public void checkOPWGPDTstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("PDT")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "PDT",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                        .ibm
                        .opicmpdh
                        .middleware
                        .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                  .ibm
                  .opicmpdh
                  .middleware
                  .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGPDTstatus

  /**
     * checkOPWGPRstatus
     *
     * @author Owner
     */
    public void checkOPWGPRstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("PR")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                      + " "
                      + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "PR",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                          .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                                                .ibm
                                                .opicmpdh
                                                .middleware
                                                .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                                    .ibm
                                    .opicmpdh
                                    .middleware
                                    .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGPRstatus

  /**
     * checkOPWGWWSDstatus
     *
     * @author Owner
     */
    public void checkOPWGWWSDstatus() {
    EANAttribute[] opatts;
    ReturnEntityKey rek = null;
    String strStatus = null;
    String stropattsStatus = null;
    String stropattsStatus1 = null;
    String stropattsStatus2 = null;
    Text tx = null;
    Text tx1 = null;
    Text tx2 = null;
    SingleFlag sf = null;
    Vector vctAtts;
    Vector vctReturnsEntityKeys;
    OPICMList ol;
    Relator relator1;
    Vector vctAtts1 = null;
    ReturnRelatorKey rek1 = null;
    Vector vctReturnsEntityKeys1 = null;

    EntityGroup _eg = m_elist.getEntityGroup("OPWG");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[2];
          atts[0] = ei.getAttribute("NAME");
          atts[1] = ei.getAttribute("ROLECODE");
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);

          strStatus = (atts[1].toString());
          logMessage("strStatus for OPWG:" + strStatus);

          if (strStatus.equals("WWSD")) {
            println(
              "<br><br><b>"
                + _eg.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID());
            println(displayAttributes(ei, _eg, true));

            for (int j = 0; j < ei.getDownLinkCount(); j++) {
              EntityItem eiRelator =
                (EntityItem) ei.getDownLink(0);
              logMessage("***eiRelator***:" + eiRelator);
              opatts = new EANAttribute[3];
              opatts[0] = eiRelator.getAttribute("FIRSTNAME");
              opatts[1] = eiRelator.getAttribute("LASTNAME");
              opatts[2] = eiRelator.getAttribute("USERTOKEN");
              logMessage("opatts[0]:" + opatts[0]);
              logMessage("opatts[1]:" + opatts[1]);
              logMessage("opatts[2]:" + opatts[2]);

              stropattsStatus = (opatts[0].toString());
              stropattsStatus1 = (opatts[1].toString());
              stropattsStatus2 = (opatts[2].toString());

              if (eiRelator != null) {
                try {
                  EntityItem eiParm =
                    new EntityItem(
                      null,
                      m_prof,
                      "REVIEWER",
                      0);
                  logMessage("eiParm:" + eiParm);
                  rek =
                    new ReturnEntityKey(
                      "REVIEWER",
                      0,
                      true);
                  logMessage("rek:" + rek);
                  tx =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      INTERNETID,
                      stropattsStatus2,
                      1,
                      m_cbOn);
                  tx1 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      NAME,
                      stropattsStatus
                                        + " "
                                        + stropattsStatus1,
                      1,
                      m_cbOn);
                  tx2 =
                    new Text(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      0,
                      ROLECODE,
                      "WWSD",
                      1,
                      m_cbOn);
                  sf =
                    new SingleFlag(
                      m_prof.getEnterprise(),
                      "REVIEWER",
                      rek.getEntityID(),
                      REVIEWERSTATUS,
                      "0010",
                      1,
                      m_cbOn);
                  logMessage("tx:" + tx);
                  logMessage("tx1:" + tx1);
                  logMessage("tx2:" + tx2);
                  logMessage("sf:" + sf);

                  vctAtts = new Vector();
                  vctReturnsEntityKeys = new Vector();

                  if (tx != null) {
                    vctAtts.addElement(tx);
                    vctAtts.addElement(tx1);
                    vctAtts.addElement(tx2);
                    vctAtts.addElement(sf);
                    logMessage("vctAtts:" + vctAtts);
                    rek.m_vctAttributes = vctAtts;
                    logMessage(
                      "rek.m_vctAttributes:"
                        + rek.m_vctAttributes);
                    vctReturnsEntityKeys.addElement(rek);

                    ol =
                      m_db.update(
                        m_prof,
                        vctReturnsEntityKeys,
                        false,
                        false);
                    logMessage("ol:" + ol);
                    rek = (ReturnEntityKey) ol.getAt(0);
                    logMessage(
                      "***rek.stuff()"
                        + rek.m_strEntityType
                        + rek.m_iEntityID);
                    logMessage(
                      "***rek.stuff()1"
                        + rek.m_vctAttributes);
                    logMessage(
                      "***acl A " + rek.getReturnID());
                    m_db.commit();
                    logMessage(
                      "eiParm.getEntityID()"
                        + eiParm.getEntityType()
                        + " "
                        + eiParm.getEntityID());

                    if (rek != null) {
                      try {
                        EntityItem eiRelator1 =
                          new EntityItem(
                            null,
                            m_prof,
                            "ANNREVREVIEWER",
                            0);
                        logMessage(
                          "eiRelator1:" + eiRelator1);
                        //                              ReturnEntityKey rek1 = new ReturnEntityKey("ANNREVREVIEWER", 0, true);
                        //                                  logMessage("rek1:" + rek1);
                        rek1 =
                          new ReturnRelatorKey(
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            true);
                        logMessage("rek1:" + rek1);
                        relator1 =
                          new Relator(
                            m_prof.getEnterprise(),
                            "ANNREVREVIEWER",
                            0,
                            "ANNREVIEW",
                            m_eiParent
                                                    .getEntityID(),
                            "REVIEWER",
                            rek.getReturnID(),
                            m_cbOn);
                        logMessage(
                          "relator1:" + relator1);

                        vctAtts1 = new Vector();
                        vctReturnsEntityKeys1 =
                          new Vector();

                        if (relator1 != null) {
                          vctAtts1.addElement(
                            relator1);
                          logMessage(
                            "vctAtts1:" + vctAtts1);
                          //                                  rek1.m_vctAttributes = vctAtts1;
                          //                                      logMessage("rek1.m_vctAttributes:" + rek1.m_vctAttributes);
                          vctReturnsEntityKeys1
                            .addElement(
                            rek1);
                          m_db.update(
                            m_prof,
                            vctReturnsEntityKeys1,
                            false,
                            false);
                          m_db.commit();
                        }
                      } catch (
                        COM
                                                .ibm
                                                .opicmpdh
                                                .middleware
                                                .MiddlewareException e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      } catch (Exception e) {
                        logMessage(
                          "setReviewerValue: "
                            + e.getMessage());
                      }
                    }
                  }
                } catch (
                  COM
                                    .ibm
                                    .opicmpdh
                                    .middleware
                                    .MiddlewareException e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                } catch (Exception e) {
                  logMessage(
                    "setReviewerValue: " + e.getMessage());
                }
              }
            }
          }
        }
      }
    }
  } //checkOPWGWWSDstatus

  /**
     * checkOPstatus
     *
     * @author Owner
     */
    public void checkOPstatus() {

    EntityGroup _eg = m_elist.getEntityGroup("OP");
    if (_eg != null) {

      for (int i = 0; i < _eg.getEntityItemCount(); i++) {
        EntityItem ei = _eg.getEntityItem(i);
        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[3];
          atts[0] = ei.getAttribute(FIRSTNAME);
          atts[1] = ei.getAttribute(LASTNAME);
          atts[2] = ei.getAttribute(USERNAME);
          logMessage("atts[0]:" + atts[0]);
          logMessage("atts[1]:" + atts[1]);
          logMessage("atts[2]:" + atts[2]);
          println(
            "<br><br><b>"
              + _eg.getLongDescription()
              + " Entity ID: "
              + ei.getEntityID());
          println(displayAttributes(ei, _eg, true));
        }
      }
    }
  } //checkOPstatus

  /**
     * This is a generic display header - This will now include status info as well.
     *
     * @param _eg
     * @param _ei
     */
  public void displayHeader(EntityGroup _eg, EntityItem _ei) {
    if (_eg != null && _eg != null) {
      println(
        "<FONT SIZE=6><b>"
          + _eg.getLongDescription()
          + "</b></FONT><br>");
      //          println(displayStatuses(_ei, _eg));
      println(displayNavAttributes(_ei, _eg));
    }
  }

  /**
   *  Sets the controlBlock attribute of the REVIEWERABR01 object
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
     * getABREntityDesc
     *
     * @param entityType
     * @param entityId
     * @return
     * @author Owner
     */
    protected String getABREntityDesc(String entityType, int entityId) {
    return null;
  }

  /**
     * getDescription
     *
     * @return
     * @author Owner
     */
    public String getDescription() {
    //return Constants.IAB3053I + "<br />" + Constants.IAB3050I;
    return "<b>Announcement Review ABR Report</b>";
  }

  /**
     * getStyle
     *
     * @return
     * @author Owner
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
    return new String("1.67");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("REVIEWERABR01.java,v 1.67 2008/01/30 19:39:17 wendy Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return getVersion();
  }

}

/*                          EntityItem[] aeiANNREVIEW = getEntityItemAsArray(ei);
                            CreateActionItem cai = new CreateActionItem(null, m_db, m_prof, "CRREVIEWER");
                            EntityList el = new EntityList(m_db, m_prof, cai, ei);


                            RowSelectableTable rst = getRowSelectableTable(el);
                            int row = rst.getRowCount() -1;

                            EntityItem ek = (EntityItem)rst.getRow(row);
                            RowSelectableTable tmpTable = ei.getEntityItemTable();

                            int r = tmpTable.getRowIndex("attrcode");

                            if (r >= 0 && r < tmpTable.getRowCount()) {
                                EANFoundation ean = tmpTable.getEANObject(r,1);
                                if (ean instanceof EANAttribute) {
                                    EANAttribute att = (EANAttribute) ean;
                                    EANMetaAttribute ma = att.getMetaAttribute();
                                    if (att instanceof EANTextAttribute) {
                                        try {
                                            EANTextAttribute ta = (EANTextAttribute)att;
                                            ta.put("value");
                                        } catch (EANBusinessRuleException bre) {
                                            bre.printStackTrace();
                                        }
                                    }
                                }

                            }
*/

/*                                  OPICMList ol = m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                                        logMessage("ol:" + ol);
                                    Object _obj = ol.getAt(0);
                                        logMessage("0bj" + _obj.toString());
                                        if (_obj instanceof EntityItem) {
                                        EntityItem m_ei = (EntityItem) _obj;
                                            logMessage("EntityItem"+m_ei.getKey());
                                        } else {
                                        EntityItem m1_ei = (EntityItem) _obj;
                                        logMessage("EntityItem recast"+m1_ei.getKey());
                                            }
*/
