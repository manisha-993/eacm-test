//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//CHECKABR.java,v
//Revision 1.7  2008/01/30 19:39:15  wendy
//Cleanup RSA warnings
//
//Revision 1.6  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.5  2006/01/24 17:00:19  yang
//Jtest Changes
//
//Revision 1.4  2005/01/25 18:18:00  yang
//updated log message
//
//Revision 1.3  2004/05/20 17:52:16  yang
//more fixes for FB53854
//
//Revision 1.2  2004/05/19 20:10:03  yang
//change report to add getLongDescription() FB53854
//
//Revision 1.1  2004/04/29 21:29:22  yang
//new abr CHEACKABR
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.Comparator;
import java.util.*;
import java.io.*;
//import java.sql.*;

/**
 * CHECKABR
 *
 * @author Owner
 */
public class CHECKABR extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = new String("CHECKABR");

  //AVAIL Attributes must exist:
  /**
   * EFFECTIVEDATE
   *
   */
  public final static String EFFECTIVEDATE = new String("EFFECTIVEDATE");
  /**
   * AVAILTYPE
   *
   */
  public final static String AVAILTYPE = new String("AVAILTYPE");
  /**
     * GENAREASELECTION
     *
     */
  public final static String GENAREASELECTION =
    new String("GENAREASELECTION");

  //CMPNT Attributes must exist:
  /**
   * MKTGNAME
   *
   */
  public final static String MKTGNAME = new String("MKTGNAME");
  /**
   * COMPONENTID
   *
   */
  public final static String COMPONENTID = new String("COMPONENTID");
  /**
   * DESCRIPTION
   *
   */
  public final static String DESCRIPTION = new String("DESCRIPTION");
  /**
   * OVERVIEWABSTRACT
   *
   */
  public final static String OVERVIEWABSTRACT =
    new String("OVERVIEWABSTRACT");
  /**
   * DISTROCODE
   *
   */
  public final static String DISTRCODE = new String("DISTRCODE");
  /**
   * SVCCAT
   *
   */
  public final static String SVCCAT = new String("SVCCAT");
  /**
   * VAE
   *
   */
  public final static String VAE = new String("VAE");

  /**
   * c_hshWarningEntities
   *
   */
  public static final Hashtable c_hshWarningEntities = new Hashtable();

  static {

    c_hshWarningEntities.put("ANNOUNCEMENT", "ANNOUNCEMENT");
    c_hshWarningEntities.put("ANNDELIVERABLE", "ANNDELIVERABLE");
    c_hshWarningEntities.put("ANNPROJ", "ANNPROJ");
    c_hshWarningEntities.put("ANNPROJREV", "ANNPROJREV");
    c_hshWarningEntities.put("ANNREVIEW", "ANNREVIEW");
    c_hshWarningEntities.put("ANNTOCONFIG", "ANNTOCONFIG");
    c_hshWarningEntities.put("CATINCL", "CATINCL");
    c_hshWarningEntities.put("FEATURERELCMPNT", "FEATURERELCMPNT");
    c_hshWarningEntities.put("FEATURERELSOF", "FEATURERELSOF");
    c_hshWarningEntities.put("CONFIGURATOR", "CONFIGURATOR");
    c_hshWarningEntities.put("FINOF", "FINOF");
    c_hshWarningEntities.put("GENERALAREA", "GENERALAREA");
    c_hshWarningEntities.put("CMPNTRELFEATURE", "CMPNTRELFEATURE");
    c_hshWarningEntities.put("CMPNTRELCMPNT", "CMPNTRELCMPNT");
    c_hshWarningEntities.put("CMPNTRELSOF", "CMPNTRELSOF");
    c_hshWarningEntities.put("SOFRELCMPNT", "SOFRELCMPNT");
    c_hshWarningEntities.put("SOFRELFEATURE", "SOFRELFEATURE");
    c_hshWarningEntities.put("ORGANUNIT", "ORGANUNIT");
    c_hshWarningEntities.put("PARAMETERCODE", "PARAMETERCODE");
    c_hshWarningEntities.put("FEATURERELFEATURE", "FEATURERELFEATURE");
    c_hshWarningEntities.put("SOFRELSOF", "SOFRELSOF");
    c_hshWarningEntities.put("CMPNTSALESCNTCTOP", "CMPNTSALESCNTCTOP");
    c_hshWarningEntities.put("FEATRSALESCNTCTOP", "FEATRSALESCNTCTOP");
    c_hshWarningEntities.put("ANNOP", "ANNOP");
    c_hshWarningEntities.put("SOFADMINOP", "SOFADMINOP");
    c_hshWarningEntities.put("SOFSALESCNTCTOP", "SOFSALESCNTCTOP");

  }

  private EntityGroup m_egParent = null; //root ANNOUNCEMENT
  private EntityItem m_eiParent = null; //root ANNOUNCEMENT

  /**
   * execute_run
   *
   * @author Owner
   */
  public void execute_run() {
    StringWriter writer = null;
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

      //Checking status from Announcement

      checkANNOUNCEMENTstatus();
      checkAVAILstatus();
      checkCHANNELstatus();
      checkCMPNTstatus();
      checkEMEATRANSLATIONstatus();
      checkFEATUREstatus();
      checkGBTstatus();
      checkOFDEVLPROJstatus();
      checkPRICEINFOstatus();
      checkSOFstatus();
      checkWarningStatus();

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
        setDGRptName("CHECKABR"); //Set the report name
        setDGRptClass("CHECKABR"); //Set the report class
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
   * checkWarningStatus
   *
   * @author Owner
   */
  public void checkWarningStatus() {
    EntityGroup _eg = null;

    Collection col = c_hshWarningEntities.values();
    String[] str = (String[]) col.toArray(new String[col.size()]);
    Arrays.sort(str, new myComparator());
    //              logMessage("***col***" + col);
    //              logMessage("***str***" + str);
    //              logMessage("***str.length***" + str.length);

    println("<br><br><b><U>Missing Forms</b></U>:");

    for (int v = 0; v < str.length; ++v) {
      System.out.println(v + " of " + str.length + " is: " + str[v]);
      _eg = m_elist.getEntityGroup(str[v]);

      if (_eg != null && _eg.getEntityItemCount() == 0) {
        //                  logMessage("***_eg.()***" + _eg.dump(true));
        //                  logMessage("***_eg.getEntityItemCount()***" + _eg.getEntityItemCount());
        println("<br>" + _eg.getLongDescription());
      }
      if (_eg != null && _eg.getEntityItemCount() > 0) {
        //                  logMessage("***_eg.getEntityItemCount() > 0***" + _eg.getEntityItemCount());
        //                  logMessage("***These _eg exist***" + _eg.getEntityType());
      } else {
        System.out.println(
          "This Entity is not included in the Entity Checklist "
            + str[v]);
      }
    }
  } //checkWarningStatus

  /**
   * myComparator
   *
   * @author Owner
   */
  private class myComparator implements Comparator {
    public int compare(Object _s1, Object _s2) {
      EntityGroup _eg1 = null;
      EntityGroup _eg2 = null;
      String s1 = null;
      String s2 = null;
      if (_s1 == null || _s2 == null) {
        return -1;
      }
      _eg1 = m_elist.getEntityGroup(_s1.toString());
      _eg2 = m_elist.getEntityGroup(_s2.toString());
      s1 = (_eg1 == null) ? "" : _eg1.getLongDescription();
      s2 = (_eg2 == null) ? "" : _eg2.getLongDescription();
      return s1.compareToIgnoreCase(s2);
    }
  }

  /**
     * checkANNOUNCEMENTstatus
     *
     * @author Owner
     */
    public void checkANNOUNCEMENTstatus() {
    m_egParent = m_elist.getParentEntityGroup();
    m_eiParent = m_egParent.getEntityItem(0);

    if (m_egParent == null) {
      logMessage("********** 1 Announcement Not found");
      setReturnCode(FAIL);
    } else {
      //              logMessage(m_eiParent.dump(false));
      for (int i = 0; i < m_egParent.getEntityItemCount(); i++) {
        //logMessage("m_egParent.getEntityItem(i):" + m_egParent.getEntityItem(i));
        EntityItem ei = m_egParent.getEntityItem(i);

        //                      logMessage("m_egParent.getEntityItem(i) for ANNOUNCEMENT:" + m_egParent.getEntityItem(i));
        //                      logMessage("ei for ANNOUNCEMENT:" + ei.dump(true));

        if (ei != null) {
          EANAttribute[] atts = new EANAttribute[8];
          atts[0] = ei.getAttribute("ANNCODENAMEU");
          atts[1] = ei.getAttribute("ANNDATE");
          atts[2] = ei.getAttribute("ANNNUMBER");
          atts[3] = ei.getAttribute("ANNTITLE");
          atts[4] = ei.getAttribute("ANNTYPE");
          atts[5] = ei.getAttribute("ATAGLANCE");
          atts[6] = ei.getAttribute("DESCRIPTION");
          atts[7] = ei.getAttribute("OVERVIEWABSTRACT");

          //                      logMessage("atts[0]:" + atts[0]);
          //                      logMessage("atts[1]:" + atts[1]);
          //                      logMessage("atts[2]:" + atts[2]);
          //                      logMessage("atts[3]:" + atts[3]);
          //                      logMessage("atts[4]:" + atts[4]);
          //                      logMessage("atts[5]:" + atts[5]);
          //                      logMessage("atts[6]:" + atts[6]);
          //                      logMessage("atts[7]:" + atts[7]);
          if ((atts[0] == null)
            || (atts[1] == null)
            || (atts[2] == null)
            || (atts[3] == null)
            || (atts[4] == null)
            || (atts[5] == null)
            || (atts[6] == null)
            || (atts[7] == null)) {
            println(
              "<br><br><b>"
                + m_egParent.getLongDescription()
                + " Entity ID: "
                + ei.getEntityID()
                + " "
                + atts[2]
                + " - Missing Data</b>");
            //                          println(displayAttributes(ei, m_egParent, true));
          }
          if (atts[0] == null) {
            println("<br>Announcement Code Name");
            setReturnCode(FAIL);
          }
          if (atts[1] == null) {
            println("<br>Announce Date");
            setReturnCode(FAIL);
          }
          if (atts[2] == null) {
            println("<br>Announcement Number");
            setReturnCode(FAIL);
          }
          if (atts[3] == null) {
            println("<br>Title");
            setReturnCode(FAIL);
          }
          if (atts[4] == null) {
            println("<br>Announcement Type");
            setReturnCode(FAIL);
          }
          if (atts[5] == null) {
            println("<br>At A Glance");
            setReturnCode(FAIL);
          }
          if (atts[6] == null) {
            println("<br>Description");
            setReturnCode(FAIL);
          }
          if (atts[7] == null) {
            println("<br>Overview/Abstract");
            setReturnCode(FAIL);
          }
        }
      }
    }
  } //checkANNOUNCEMENTstatus

  /**
     * checkAVAILstatus
     *
     * @author Owner
     */
    public void checkAVAILstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("AVAIL");
    String strStatus = null;
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Effective Date");
        setReturnCode(FAIL);
      } else {

        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[2];
            atts[0] = ei.getAttribute("EFFECTIVEDATE");
            atts[1] = ei.getAttribute("COMNAME");
            //logMessage("atts[0]:" + atts[0]);
            //logMessage("atts[1]:" + atts[1]);
            strStatus = (atts[0].toString());
            //logMessage("strStatus for AVAIL:" + strStatus);
            if (strStatus.equals("")) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + atts[1]
                  + " - Missing Data</b>");
              //println(displayAttributes(ei, _eg, true));
              println("<br>Effective Date");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkAVAILstatus

  /**
     * checkCHANNELstatus
     *
     * @author Owner
     */
    public void checkCHANNELstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("CHANNEL");
    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for CHANNEL:"
          + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Channel Name");
        println("<br>Channel Type");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {

          EntityItem ei = _eg.getEntityItem(i);

          //                  logMessage("ei for CHANNEL:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[3];
            atts[0] = ei.getAttribute("CHANNELNAME");
            atts[1] = ei.getAttribute("CHANNELTYPE");

            //                  logMessage("atts[0] for CHANNEL:" + atts[0]);
            //                  logMessage("atts[1] for CHANNEL:" + atts[1]);
            if ((atts[0] == null) || (atts[1] == null)) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + atts[0]
                  + " - Missing Data</b>");
              //                      println(displayAttributes(ei, _eg, true));
            }
            if (atts[0] == null) {
              println("<br>Channel Name");
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br>Channel Type");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkCHANNELstatus

  /**
     * checkCMPNTstatus
     *
     * @author Owner
     */
    public void checkCMPNTstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("CMPNT");

    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Component Id");
        println("<br>Description");
        println("<br>Marketing Name");
        println("<br>Overview/Abstract");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          //                  logMessage("_eg.getEntityItem(i) for CMPNT:" + _eg.getEntityItem(i));
          //                  logMessage("ei for CMPNT:" + ei.dump(false));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[5];
            atts[0] = ei.getAttribute("COMPONENTID");
            atts[1] = ei.getAttribute("DESCRIPTION");
            atts[2] = ei.getAttribute("MKTGNAME");
            atts[3] = ei.getAttribute("OVERVIEWABSTRACT");
            //logMessage("atts[0]:" + atts[0]);
            //logMessage("atts[1]:" + atts[1]);
            //logMessage("atts[2]:" + atts[2]);
            //logMessage("atts[3]:" + atts[3]);
            if ((atts[0] == null)
              || (atts[1] == null)
              || (atts[2] == null)
              || (atts[3] == null)) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + atts[2]
                  + " - Missing Data</b>");
              //println(displayAttributes(ei, _eg, true));
            }
            if (atts[0] == null) {
              println("<br>Component Id");
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br>Description");
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br>Marketing Name");
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br>Overview/Abstract");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkCMPNTstatus

  /**
     * checkEMEATRANSLATIONstatus
     *
     * @author Owner
     */
    public void checkEMEATRANSLATIONstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("EMEATRANSLATION");
    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for EMEATRANSLATION:"
          + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Language");
        println("<br>Proposal Insert ID");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {

          EntityItem ei = _eg.getEntityItem(i);

          //                  logMessage("ei for EMEATRANSLATION:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[3];
            atts[0] = ei.getAttribute("LANGUAGES");
            atts[1] = ei.getAttribute("PROPOSALINSERTID");
            atts[2] = ei.getAttribute("PROPOSALINSERTID");

            //                  logMessage("atts[0] for EMEATRANSLATION:" + atts[0]);
            //                  logMessage("atts[1] for EMEATRANSLATION:" + atts[1]);
            //                  logMessage("atts[2] for EMEATRANSLATION:" + atts[2]);
            if ((atts[0] == null) || (atts[1] == null)) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + atts[2]
                  + " - Missing Data</b>");
              //println(displayAttributes(ei, _eg, true));
            }
            if (atts[0] == null) {
              println("<br>Language");
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br>Proposal Insert ID");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkEMEATRANSLATIONstatus

  /**
     * checkFEATUREstatus
     *
     * @author Owner
     */
    public void checkFEATUREstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("FEATURE");

    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Description");
        println("<br>Feature Number");
        println("<br>Marketing Name");
        println("<br>Overview/Abstract");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          //                  logMessage("_eg.getEntityItem(i) for FEATURE:" + _eg.getEntityItem(i));
          //                  logMessage("ei for FEATURE:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[4];
            atts[0] = ei.getAttribute("DESCRIPTION");
            atts[1] = ei.getAttribute("FEATURENUMBER");
            atts[2] = ei.getAttribute("MKTGNAME");
            atts[3] = ei.getAttribute("OVERVIEWABSTRACT");
            //                  logMessage("atts[0] for feature:" + atts[0]);
            //                  logMessage("atts[1] for feature:" + atts[1]);
            //                  logMessage("atts[2] for feature:" + atts[2]);
            //                  logMessage("atts[3] for feature:" + atts[3]);
            //                  logMessage("atts[4] for feature:" + atts[4]);
            if ((atts[0] == null)
              || (atts[1] == null)
              || (atts[2] == null)
              || (atts[3] == null)) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + atts[2]
                  + " - Missing Data</b>");
              //println(displayAttributes(ei, _eg, true));
            }
            if (atts[0] == null) {
              println("<br>Description");
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br>Feature Number");
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br>Marketing Name");
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br>Overview/Abstract");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkFEATUREstatus

  /**
     * checkGBTstatus
     *
     * @author Owner
     */
    public void checkGBTstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("GBT");
    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for GBT:" + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>SAP Primary Brand Code");
        println("<br>SAP Product Family Code");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {

          EntityItem ei = _eg.getEntityItem(i);

          //                  logMessage("ei for GBT:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[3];
            atts[0] = ei.getAttribute("SAPPRIMBRANDCODE");
            atts[1] = ei.getAttribute("SAPPRODFAMCODE");
            atts[2] = ei.getAttribute("GBNAME");

            //                  logMessage("atts[0] for GBT:" + atts[0]);
            //                  logMessage("atts[1] for GBT:" + atts[1]);
            //                  logMessage("atts[2] for GBT:" + atts[2]);
            if ((atts[0] == null) || (atts[1] == null)) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + atts[2]
                  + " - Missing Data</b>");
              //println(displayAttributes(ei, _eg, true));
            }
            if (atts[0] == null) {
              println("<br>SAP Primary Brand Code");
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br>SAP Product Family Code");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkGBTstatus

  /**
     * checkOFDEVLPROJstatus
     *
     * @author Owner
     */
    public void checkOFDEVLPROJstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("OFDEVLPROJ");
    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Project Number");
        setReturnCode(FAIL);
      } else {

        logMessage(
          "****_eg.getEntityItemCount() for OFDEVLPROJ:"
            + _eg.getEntityItemCount());
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          EntityItem ei = _eg.getEntityItem(i);
          if (ei != null) {
            EANAttribute att = ei.getAttribute("PROJNUMBER");
            EANAttribute att1 = ei.getAttribute("NAME");
            //                  logMessage("****att for OFDEVLPROJ:" + att);
            if (att == null) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + att1
                  + " - Missing Data</b>");
              //println(displayAttributes(ei, _eg, true));
              println("<br>Project Number");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkOFDEVLPROJstatus

  /**
     * checkPRICEINFOstatus
     *
     * @author Owner
     */
    public void checkPRICEINFOstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("PRICEFININFO");

    if (_eg != null) {

      logMessage(
        "_eg.getEntityItemCount() for PRICEFININFO:"
          + _eg.getEntityItemCount());
      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Billing Application");
        println("<br>Charges");
        println("<br>Contract Close Fee");
        println("<br>L/P Fee");
        println("<br>Remarketing Discount");
        setReturnCode(FAIL);
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          //                  logMessage("_eg.getEntityItem(i) for PRICEFININFO:" + _eg.getEntityItem(i));
          //                  logMessage("ei for PRICEFININFO:" + ei.dump(true));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[6];
            atts[0] = ei.getAttribute("BILLINGAPP");
            atts[1] = ei.getAttribute("CHARGES");
            atts[2] = ei.getAttribute("CONTRACTCLOSEFEE");
            atts[3] = ei.getAttribute("LPFEE");
            atts[4] = ei.getAttribute("REMKTGDISCOUNT");
            atts[5] = ei.getAttribute("COMNAME");
            //                  logMessage("atts[0] for PRICEINFO:" + atts[0]);
            //                  logMessage("atts[1] for PRICEINFO:" + atts[1]);
            //                  logMessage("atts[2] for PRICEINFO:" + atts[2]);
            //                  logMessage("atts[3] for PRICEINFO:" + atts[3]);
            //                  logMessage("atts[4] for PRICEINFO:" + atts[4]);
            //                  logMessage("atts[5] for PRICEINFO:" + atts[5]);
            if ((atts[0] == null)
              || (atts[1] == null)
              || (atts[2] == null)
              || (atts[3] == null)
              || (atts[4] == null)) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + " "
                  + atts[5]
                  + " - Missing Data</b>");
              //println(displayAttributes(ei, _eg, true));
            }
            if (atts[0] == null) {
              println("<br>Billing Application");
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br>Charges");
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br>Contract Close Fee");
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br>L/P Fee");
              setReturnCode(FAIL);
            }
            if (atts[4] == null) {
              println("<br>Remarketing Discount");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkPRICEINFOstatus

  /**
     * checkSOFstatus
     *
     * @author Owner
     */
    public void checkSOFstatus() {
    EntityGroup _eg = m_elist.getEntityGroup("SOF");

    if (_eg != null) {

      if (_eg.getEntityItemCount() == 0) {
        println(
          "<br><br><b>"
            + _eg.getLongDescription()
            + " - Missing Data</b>");
        println("<br>Description");
        println("<br>Marketing Name");
        println("<br>Offering Identification Number");
        println("<br>Overview/Abstract");
        setReturnCode(FAIL);
        //                      logMessage("SOF getReturnCode" + getReturnCode());
      } else {
        for (int i = 0; i < _eg.getEntityItemCount(); i++) {
          //logMessage("_eg.getEntityItem(i):" + _eg.getEntityItem(i));
          EntityItem ei = _eg.getEntityItem(i);

          //                  logMessage("_eg.getEntityItem(i) for SOF:" + _eg.getEntityItem(i));
          //                  logMessage("ei for SOF:" + ei.dump(false));

          if (ei != null) {
            EANAttribute[] atts = new EANAttribute[4];
            atts[0] = ei.getAttribute("DESCRIPTION");
            atts[1] = ei.getAttribute("MKTGNAME");
            atts[2] = ei.getAttribute("OFIDNUMBER");
            atts[3] = ei.getAttribute("OVERVIEWABSTRACT");
            //                  logMessage("atts[0] for SOF:" + atts[0]);
            //                  logMessage("atts[1] for SOF:" + atts[1]);
            //                  logMessage("atts[2] for SOF:" + atts[2]);
            //                  logMessage("atts[3] for SOF:" + atts[3]);
            if ((atts[0] == null)
              || (atts[1] == null)
              || (atts[2] == null)
              || (atts[3] == null)) {
              println(
                "<br><br><b>"
                  + _eg.getLongDescription()
                  + " Entity ID: "
                  + ei.getEntityID()
                  + atts[1]
                  + " - Missing Data</b>");
              //                      println(displayAttributes(ei, _eg, true));
            }
            if (atts[0] == null) {
              println("<br>Description");
              setReturnCode(FAIL);
            }
            if (atts[1] == null) {
              println("<br>Marketing Name");
              setReturnCode(FAIL);
            }
            if (atts[2] == null) {
              println("<br>Offering Identification Number");
              setReturnCode(FAIL);
            }
            if (atts[3] == null) {
              println("<br>Overview/Abstract");
              setReturnCode(FAIL);
            }
          }
        }
      }
    }
  } //checkSOFstatus

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
   *  Sets the controlBlock attribute of the CHECKABR object
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
    return "<b>Announcement Missing Data and Forms Report</b>";
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
    return new String("1.7");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("CHECKABR.java,v 1.7 2008/01/30 19:39:15 wendy Exp");
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
