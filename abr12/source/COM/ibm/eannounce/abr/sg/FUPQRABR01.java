//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//FUPQRABR01.java,v
//Revision 1.17  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.16  2006/01/24 16:53:58  yang
//Jtest Changes
//
//Revision 1.15  2003/11/07 01:21:55  yang
//Adding setDGRptClass
//
//Revision 1.14  2003/09/18 19:52:07  yang
//adding bala's stuff to finally {
//
//Revision 1.13  2003/07/02 20:17:19  yang
//update setFlagValue to include children
//
//Revision 1.12  2003/06/18 15:42:15  dave
//syntax
//
//Revision 1.11  2003/06/18 15:25:43  dave
//M005 mod
//
//Revision 1.10  2003/06/18 15:20:16  dave
//modifiying M0005 to look for target children
//
//Revision 1.9  2003/06/18 01:06:37  dave
//checking ORDEROF
//
//Revision 1.8  2003/06/08 20:48:06  yang
//fixes to syntax
//
//Revision 1.7  2003/06/08 20:46:30  yang
//Updated to new Version
//
//Revision 1.6  2003/06/04 03:53:09  dave
//un Staticing getABRVersion
//
//Revision 1.5  2003/06/04 03:44:26  dave
//minor syntax
//
//Revision 1.4  2003/06/04 03:41:44  dave
//adding getABRVersion
//
//Revision 1.3  2003/06/03 23:28:34  dave
//commonizing setControlBlock
//
//Revision 1.2  2003/06/03 19:33:59  dave
//more consolidation
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.12  2003/06/03 18:17:38  dave
//preping for common T1 and T2 processing
//
//Revision 1.11  2003/06/02 18:47:49  minhthy
//fixed checkV0001, checkH0003
//
//Revision 1.10  2003/06/02 15:55:17  dave
//putting a stacktrace in to see the line number we are failing
//on
//
//Revision 1.9  2003/05/29 19:54:34  minhthy
//fixed funtion checkV0001
//
//Revision 1.8  2003/05/29 17:21:41  naomi
//fix S0002
//
//Revision 1.7  2003/05/27 20:21:03  naomi
//fixed checkS0001_GC
//
//Revision 1.6  2003/05/27 16:26:50  naomi
//fix checkM0003
//
//Revision 1.5  2003/05/23 18:48:28  naomi
//fixed isStatusOK
//
//Revision 1.4  2003/05/16 20:03:57  naomi
//fixed output message
//
//Revision 1.3  2003/04/30 20:28:13  naomi
//add POF for S0001
//
//Revision 1.2  2003/04/24 21:13:05  naomi
//added setControlBlock()
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
//import java.io.*;
//import java.sql.*;

/**
 * FUPQRABR1
 *
 * @author Owner
 */
public class FUPQRABR01 extends PokBaseABR {

  // S0002 only check for PUBLICATION , RULES
  /**
     * c_hshEntities
     *
     */
    public static final Hashtable c_hshEntities = new Hashtable();
  static {
    c_hshEntities.put("TECHCAPABILITY", "HI");
  }

  //ABR
  /**
     * ABR
     *
     */
    public final static String ABR = new String("FUPQRABR01");

  private EntityGroup m_egParent = null; //root FUP
  private EntityItem m_eiParent = null; //root FUP

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    EntityGroup eg = null;
    EntityGroup m_egChild = null;
    EntityGroup m_egChild1 = null;
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

      if (!checkS0001(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkS0002(m_eiParent, c_hshEntities)) {
        setReturnCode(FAIL);
      }
      // we need to find the order offerings to run M0003 against.
      eg = m_elist.getEntityGroup("ORDEROF");
      for (int ii = 0; ii < eg.getEntityItemCount(); ii++) {
        EntityItem ei = eg.getEntityItem(ii);
        if (!checkM0003(ei)) {
          setReturnCode(FAIL);
        }
      }

      // We need to check the children of FUPFUPMGMTGRP
      eg = m_elist.getEntityGroup("FUPFUPMGMTGRP");
      for (int ii = 0; ii < eg.getEntityItemCount(); ii++) {
        EntityItem ei = eg.getEntityItem(ii);
        if (!checkM0005(ei, "FUPMEMBERFUPOMG")) {
          setReturnCode(FAIL);
        }
      }

      // We need to check the children of FUPFUPMGMTGRP
      eg = m_elist.getEntityGroup("FUPPOFMGMTGRP");
      for (int ii = 0; ii < eg.getEntityItemCount(); ii++) {
        EntityItem ei = eg.getEntityItem(ii);
        if (!checkM0005(ei, "POFMEMBERFUPOMG")) {
          setReturnCode(FAIL);
        }
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

      if (getReturnCode() == PASS) {
        // Change the status...
        setFlagValue(
          m_eiParent,
          getStatusAttributeCode(m_eiParent),
          getNextStatusCode(m_eiParent));
        m_egChild = m_elist.getEntityGroup("FUPFUPMGMTGRP");
        for (int i = 0; i < m_egChild.getEntityItemCount(); i++) {
          EntityItem m_eiChild = (m_egChild.getEntityItem(i));
          setFlagValue(
            m_eiChild,
            getStatusAttributeCode(m_eiChild),
            getNextStatusCode(m_eiChild));
        }

        m_egChild1 =
          m_elist.getEntityGroup("FUPPOFMGMTGRP");
        for (int i = 0; i < m_egChild1.getEntityItemCount(); i++) {
          EntityItem m_eiChild1 = (m_egChild1.getEntityItem(i));
          setFlagValue(
            m_eiChild1,
            getStatusAttributeCode(m_eiChild1),
            getNextStatusCode(m_eiChild1));
        }

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

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      //Everything is fine...so lets pass
      //setReturnCode(PASS);
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("FUPQRABR01"); //Set the report name
      setDGRptClass("FUPQRABR01"); //Set the report class
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
    return ("FUPQRABR01.java,v 1.17 2006/03/03 19:23:29 bala Exp");
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
