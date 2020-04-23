//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//OOFQRABR01.java,v
//Revision 1.25  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.24  2006/01/24 16:50:39  yang
//Jtest Changes
//
//Revision 1.23  2004/03/23 21:01:59  joan
//*** empty log message ***
//
//Revision 1.22  2003/11/07 01:22:12  yang
//Adding setDGRptClass
//
//Revision 1.21  2003/09/18 19:52:19  yang
//adding bala's stuff to finally {
//
//Revision 1.20  2003/06/28 00:47:11  yang
//syntax
//
//Revision 1.19  2003/06/28 00:38:28  yang
//syntax
//
//Revision 1.18  2003/06/28 00:24:06  yang
//syntax
//
//Revision 1.17  2003/06/27 23:21:31  yang
//syntax
//
//Revision 1.16  2003/06/27 22:53:45  yang
//test
//
//Revision 1.15  2003/06/27 22:44:21  yang
//test
//
//Revision 1.14  2003/06/27 22:10:46  yang
//syntax
//
//Revision 1.13  2003/06/27 21:37:46  yang
//changed V0002
//
//Revision 1.12  2003/06/27 18:11:45  minhthy
//minor changed V0002
//
//Revision 1.11  2003/06/09 17:23:25  yang
//Adding StackTrace
//
//Revision 1.10  2003/06/07 20:33:55  dave
//more syntax
//
//Revision 1.9  2003/06/07 20:27:24  dave
//syntax
//
//Revision 1.8  2003/06/07 20:23:40  dave
//attempting OOFQABR01
//
//Revision 1.7  2003/06/04 03:53:09  dave
//un Staticing getABRVersion
//
//Revision 1.6  2003/06/04 03:44:26  dave
//minor syntax
//
//Revision 1.5  2003/06/04 03:41:45  dave
//adding getABRVersion
//
//Revision 1.4  2003/06/03 23:28:34  dave
//commonizing setControlBlock
//
//Revision 1.3  2003/06/03 23:16:14  dave
//massive reorg for common routines
//
//Revision 1.2  2003/06/03 19:33:59  dave
//more consolidation
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.31  2003/06/03 18:17:38  dave
//preping for common T1 and T2 processing
//
//Revision 1.30  2003/06/02 22:05:39  dave
//trace
//
//Revision 1.29  2003/05/30 20:25:16  naomi
//fix V0001 -check root entity
//
//Revision 1.27  2003/05/30 00:23:13  naomi
//fixed M0003,  V0001
//
//Revision 1.26  2003/05/27 20:19:22  naomi
//fixed checkS0001_GC
//
//Revision 1.25  2003/05/27 16:05:20  naomi
//change to call V0002 first
//
//Revision 1.23  2003/05/23 00:24:02  naomi
//fix checkV0002
//
//Revision 1.22  2003/05/16 20:04:56  naomi
//fixed output message
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
//import java.util.*;
import java.io.*;
//import java.sql.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Owner
 */
public class OOFQRABR01 extends PokBaseABR {

  //ABR
  /**
     * ABR
     *
     */
    public final static String ABR = new String("OOFQRABR01");

  private EntityGroup m_egParent = null;
  private EntityItem m_eiParent = null;

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

      // Default the thing to pass...
      setReturnCode(PASS);

      displayHeader(m_egParent, m_eiParent);

      if (!checkA0001(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkA0002(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkS0001(m_eiParent)) {
        setReturnCode(FAIL);
      }
      // For now.. check them all.. until all the statuses can be entered
      if (!checkS0002(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0002(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0003(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0007(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0008(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkH0003(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkH0006(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkV0001(m_eiParent)) {
        setReturnCode(FAIL);
      }

      if (!checkFCFormat(m_eiParent)) {
        setReturnCode(FAIL);
      }

      if ((getFlagCode(m_eiParent, "OOFCAT").equals("100")
            && getFlagCode(m_eiParent, "OOFSUBCAT").equals("500")
            && getFlagCode(m_eiParent, "OOFGRP").equals("405")
            && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("100")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("506")
          && getFlagCode(m_eiParent, "OOFGRP").equals("405")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("101")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("500")
          && getFlagCode(m_eiParent, "OOFGRP").equals("405")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("100")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("501")
          && getFlagCode(m_eiParent, "OOFGRP").equals("600")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("100")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("501")
          && getFlagCode(m_eiParent, "OOFGRP").equals("601")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("100")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("501")
          && getFlagCode(m_eiParent, "OOFGRP").equals("602")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("100")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("501")
          && getFlagCode(m_eiParent, "OOFGRP").equals("603")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("100")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("501")
          && getFlagCode(m_eiParent, "OOFGRP").equals("604")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))
        || (getFlagCode(m_eiParent, "OOFCAT").equals("100")
          && getFlagCode(m_eiParent, "OOFSUBCAT").equals("501")
          && getFlagCode(m_eiParent, "OOFGRP").equals("405")
          && getFlagCode(m_eiParent, "OOFSUBGRP").equals("405"))) {

        if (!checkV0002(m_eiParent)) {
          setReturnCode(FAIL);
        }
      } else {
        println("<br><br><b><I>Not Checking V0002:</I> FEATURECODE is not a valid attribute. </b>");
      }
      if (getReturnCode() == PASS) {
        // Change the status...
        setFlagValue(
          m_eiParent,
          getStatusAttributeCode(m_eiParent),
          getNextStatusCode(m_eiParent));
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
      //setReturnCode(PASS);
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("OOFQRABR01"); //Set the report name
      setDGRptClass("OOFQRABR01"); //Set the report class
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
    //return Constants.IAB3053I + "<br /><br />" + Constants.IAB3050I;
    return "<br /><br />";
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
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("OOFQRABR01.java,v 1.25 2006/03/03 19:23:29 bala Exp");
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
