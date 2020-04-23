//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//COFOOFQRABR01.java,v
//Revision 1.18  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.17  2006/01/24 17:02:56  yang
//Jtest Changes
//
//Revision 1.16  2003/11/07 01:20:46  yang
//Adding setDGRptClass
//
//Revision 1.15  2003/09/18 19:51:21  yang
//adding bala's stuff to finally {
//
//Revision 1.14  2003/06/27 16:35:22  yang
//more syntax
//
//Revision 1.13  2003/06/27 16:19:41  yang
//syntax
//
//Revision 1.12  2003/06/27 15:51:33  yang
//Add condition to A0001
//
//Revision 1.11  2003/06/06 19:20:57  dave
//adding avail to the hashtables
//
//Revision 1.10  2003/06/06 17:04:28  dave
//fixing report triggering
//
//Revision 1.9  2003/06/06 16:57:59  yang
//Updated checkM0004Child
//
//Revision 1.8  2003/06/06 00:09:56  yang
//Updated to new version
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
//Revision 1.5  2003/06/03 18:17:37  dave
//preping for common T1 and T2 processing
//
//Revision 1.4  2003/05/28 00:10:59  naomi
//fix checkM0004 output
//
//Revision 1.3  2003/05/27 18:17:06  naomi
//added message M0004
//
//Revision 1.2  2003/05/23 18:45:31  naomi
//fixed isStatusOK
//
//Revision 1.1  2003/05/16 20:02:12  naomi
//check in
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.io.*;
//import java.sql.*;

/**
 *  Description of the Class
 *  The ABR checks to see if the management groups are ready to move to Complete status.
 *  It is ready if all of its children are at a Complete status
 *  Return Code
 *  Pass -- if all of its children are at a Complete status
 *          and Child classification is compatible with the management group classification.
 *  Fail -- if no matching found.
 *
 */

public class COFOOFQRABR01 extends PokBaseABR {

  // S0002 only check for PUBLICATION , RULES
  /**
     * c_hshEntities
     *
     */
    public static final Hashtable c_hshEntities = new Hashtable();
  static {

    c_hshEntities.put("AVAIL", "HI");
    c_hshEntities.put("ORDEROF", "HI");

  }

  //ABR
  /**
     * ABR
     *
     */
    public final static String ABR = new String("COFOOFQRABR01");

  private EntityGroup m_egParent = null; //root COFOOFMGMTGRP
  private EntityItem m_eiParent = null; //root COFOOFMGMTGRP

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
      m_eiParent =
        (m_egParent == null ? null : m_egParent.getEntityItem(0));

      if (m_egParent == null) {
        logMessage(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:1: m_egParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      if (m_eiParent == null) {
        logMessage(
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

      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Setup Complete:"
          + m_eiParent.getEntityType()
          + ":"
          + m_eiParent.getEntityID());

      // Here is the check
      displayHeader(m_egParent, m_eiParent);
      setReturnCode(PASS);

      if (!checkM0007(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0008(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0004Child(m_eiParent)) {
        setReturnCode(FAIL);
      }

      if (!checkA0002(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkA0003(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkS0002(m_eiParent, c_hshEntities)) {
        setReturnCode(FAIL);
      }

      if ((getFlagCode(m_eiParent, "COFOOFMGCAT").equals("100")
                    && getFlagCode(m_eiParent, "COFOOFMGSUBCAT").equals("452")
                    && getFlagCode(m_eiParent, "COFOOFMGGRP").equals("400")
                    && getFlagCode(m_eiParent, "COFOOFMGSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "COFOOFMGCAT").equals("100")
          && getFlagCode(m_eiParent, "COFOOFMGSUBCAT").equals("453")
          && getFlagCode(m_eiParent, "COFOOFMGGRP").equals("400")
          && getFlagCode(m_eiParent, "COFOOFMGSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "COFOOFMGCAT").equals("100")
          && getFlagCode(m_eiParent, "COFOOFMGSUBCAT").equals("208")
          && getFlagCode(m_eiParent, "COFOOFMGGRP").equals("304")
          && getFlagCode(m_eiParent, "COFOOFMGSUBGRP").equals("400"))
        || (getFlagCode(m_eiParent, "COFOOFMGCAT").equals("100")
          && getFlagCode(m_eiParent, "COFOOFMGSUBCAT").equals("208")
          && getFlagCode(m_eiParent, "COFOOFMGGRP").equals("304")
          && getFlagCode(m_eiParent, "COFOOFMGSUBGRP").equals("402"))
        || (getFlagCode(m_eiParent, "COFOOFMGCAT").equals("100")
          && getFlagCode(m_eiParent, "COFOOFMGSUBCAT").equals("208")
          && getFlagCode(m_eiParent, "COFOOFMGGRP").equals("304")
          && getFlagCode(m_eiParent, "COFOOFMGSUBGRP").equals("404"))) {

        if (!checkA0001(m_eiParent)) {
          setReturnCode(FAIL);
        }
      }
      /* If return code is PASS and the prior status is "Change (Ready for Review)" or "Change (Complete)"
      * then, produce a Change Report
      */
      if (getReturnCode() == PASS) {
        setFlagValue(
          m_eiParent,
          getStatusAttributeCode(m_eiParent),
          getNextStatusCode(m_eiParent));
        // Insert the change report here
        if (isChangeRev(m_eiParent) || isChangeFinal(m_eiParent)) {
          setNow();
          println(processChangeReport(getT1(m_eiParent), getNow()));
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
      // Report this error to both the datbase log and the PrintWriter
      println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      println("" + exc);
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
      setDGString(getABRReturnCode());
      setDGRptName("COFOOFQRABR01"); //Set the report name
      setDGRptClass("COFOOFQRABR01"); //Set the report class
      printDGSubmitString();
      //Stuff into report for subscription and notification
      buildReportFooter();
      if (!isReadOnly()) {
        clearSoftLock();
      }
    }
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
    return "<br /><br />";
  }

  /**
     * getStyle
     *
     * @return
     * @author Owner
     */
    protected String getStyle() {
    return "";
  }

  /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
    return new String("1.18");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("COFOOFQRABR01.java,v 1.18 2006/03/03 19:23:27 bala Exp");
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
