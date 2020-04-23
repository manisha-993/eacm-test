//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//POFQRABR01.java,v
//Revision 1.23  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.22  2006/01/24 16:49:46  yang
//Jtest Changes
//
//Revision 1.21  2003/11/07 01:22:26  yang
//Adding setDGRptClass
//
//Revision 1.20  2003/09/18 19:52:33  yang
//adding bala's stuff to finally {
//
//Revision 1.19  2003/07/02 20:39:10  yang
//updated setFlagValue to include children
//
//Revision 1.18  2003/06/18 15:44:02  dave
//removed old M0005 reference
//
//Revision 1.17  2003/06/18 15:42:15  dave
//syntax
//
//Revision 1.16  2003/06/18 15:30:39  dave
//some syntax fixing
//
//Revision 1.15  2003/06/06 17:04:29  dave
//fixing report triggering
//
//Revision 1.14  2003/06/05 18:43:12  dave
//syntax
//
//Revision 1.13  2003/06/05 18:21:56  dave
//some minor fixes
//
//Revision 1.12  2003/06/05 18:12:18  minhthy
//updated version
//
//Revision 1.19  2003/06/05 02:50:09  dave
//cleanup on )
//
//Revision 1.18  2003/06/05 02:46:58  dave
//more hashtable control
//
//Revision 1.17  2003/06/05 02:37:18  dave
//cleanup
//
//Revision 1.16  2003/06/05 02:22:20  dave
//more updates
//
//Revision 1.15  2003/06/05 02:17:09  dave
//more status simplification
//
//Revision 1.14  2003/06/04 19:56:14  dave
//we are almost there
//
//Revision 1.13  2003/06/04 19:49:11  dave
//is it back on track?
//
//Revision 1.12  2003/06/04 19:46:09  dave
//more traction
//
//Revision 1.11  2003/06/04 18:18:06  dave
//more consolidations
//
//Revision 1.10  2003/06/04 15:49:13  dave
//almost there
//
//Revision 1.9  2003/06/04 15:44:28  dave
//more fixes to syntax
//
//Revision 1.8  2003/06/04 15:32:00  dave
//commonizing POFPOF
//
//Revision 1.6  2003/06/04 03:53:10  dave
//un Staticing getABRVersion
//
//Revision 1.5  2003/06/04 03:44:26  dave
//minor syntax
//
//Revision 1.4  2003/06/04 03:41:45  dave
//adding getABRVersion
//
//Revision 1.3  2003/06/03 23:28:34  dave
//commonizing setControlBlock
//
//Revision 1.2  2003/06/03 19:33:59  dave
//more consolidation
//
//Revision 1.1.1.1  2003/06/03 19:02:25  dave
//new 1.1.1 abr
//
//Revision 1.6  2003/06/03 18:17:38  dave
//preping for common T1 and T2 processing
//
//Revision 1.5  2003/06/03 01:16:06  yang
//Updated M0005
//
//Revision 1.5  2003/06/02 22:05:39  dave
//trace
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
 * POFQRABR01
 *
 * @author Owner
 */
public class POFQRABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  // S0002 only check for PUBLICATION , RULES
  public static final Hashtable c_hshEntities = new Hashtable();
  static {
    c_hshEntities.put("PUBLICATION", "HI");
    c_hshEntities.put("RULES", "HI");
  }

  //ABR
  /**
   * ABR
   *
   */
  public final static String ABR = "POFQRABR01";

  private EntityGroup m_egParent = null; //root POFPOFMGMTGRP
  private EntityItem m_eiParent = null; //root POFPOFMGMTGRP

  /**
   * execute_run
   *
   * @author Owner
   */
  public void execute_run() {
    EntityGroup eg = null;
    EntityGroup m_egChild = null;
    try {

      start_ABRBuild();
      //Get the root POFPOFMGMTGRP
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
      if (!checkM0007(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0008(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }

      eg = m_elist.getEntityGroup("POFPOFMGMTGRP");
      for (int ii = 0; ii < eg.getEntityItemCount(); ii++) {
        EntityItem ei = eg.getEntityItem(ii);
        if (!checkM0005(ei, "POFMEMBERPOFOMG")) {
          setReturnCode(FAIL);
        }
      }

      if (!checkM0020(m_eiParent)) {
        setReturnCode(FAIL);
      }

      if (getReturnCode() == PASS) {
        // Change the status...
        setFlagValue(
          m_eiParent,
          getStatusAttributeCode(m_eiParent),
          getNextStatusCode(m_eiParent));

        m_egChild = m_elist.getEntityGroup("POFPOFMGMTGRP");
        for (int i = 0; i < m_egChild.getEntityItemCount(); i++) {
          EntityItem m_eiChild = (m_egChild.getEntityItem(i));
          setFlagValue(
            m_eiChild,
            getStatusAttributeCode(m_eiChild),
            getNextStatusCode(m_eiChild));
        }

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

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      //Everything is fine...so lets pass
      if (getReturnCode() == PASS) {
        setReturnCode(PASS);
        // set DG submit string
        setDGString(getABRReturnCode());
        setDGRptName("POFQRABR01"); //Set the report name
        setDGRptClass("POFQRABR01"); //Set the report class
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
    return "<br />This needs to be defined <br />";
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
    return new String("1.23");
  }

  /**
   * getVersion
   *
   * @return
   * @author Owner
   */
  public static String getVersion() {
    return ("POFQRABR01.java,v 1.23 2006/03/03 19:23:29 bala Exp");
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
