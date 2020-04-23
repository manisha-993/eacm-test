//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//FUPPOFQRABR01.java,v
//Revision 1.18  2008/01/30 19:39:15  wendy
//Cleanup RSA warnings
//
//Revision 1.17  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.16  2006/01/24 16:53:44  yang
//Jtest Changes
//
//Revision 1.15  2003/11/07 01:21:49  yang
//Adding setDGRptClass
//
//Revision 1.14  2003/09/18 19:51:09  yang
//adding bala's stuff to finally {
//
//Revision 1.13  2003/06/18 15:37:22  dave
//syntax
//
//Revision 1.12  2003/06/06 17:04:29  dave
//fixing report triggering
//
//Revision 1.11  2003/06/05 02:46:58  dave
//more hashtable control
//
//Revision 1.10  2003/06/05 02:37:18  dave
//cleanup
//
//Revision 1.9  2003/06/05 02:22:19  dave
//more updates
//
//Revision 1.8  2003/06/05 02:17:09  dave
//more status simplification
//
//Revision 1.7  2003/06/05 00:55:49  yang
//Updated to new version
//
//Revision 1.11  2003/06/04 23:11:21  yang
//Updated S0002

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
//import java.util.*;
//import java.io.*;
//import java.sql.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Owner
 */
public class FUPPOFQRABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = new String("FUPPOFQRABR01");

  /**
     * FUPPOFMGMTGRP
     *
     */
  public final static String FUPPOFMGMTGRP = new String("FUPPOFMGMTGRP");
  /**
     * FUPPOFSTATUS
     *
     */
  public final static String FUPPOFSTATUS = new String("FUPPOFSTATUS");

  private EntityGroup m_egParent = null; //root FUPPOFMGMTGRP
  
  private EntityItem m_eiParent = null; //root FUPPOFMGMTGRP
  
  /**
   * execute_run
   *
   * @author Owner
   */
  public void execute_run() {
    try {

      start_ABRBuild();
      //Get the root FUPPOFMGMTGRP
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

      if (!checkS0002(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0007(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0008(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0005(m_eiParent, "POFMEMBERFUPOMG")) {
        setReturnCode(FAIL);
      }

      if (getReturnCode() == PASS) {
        // Change the status...
        setFlagValue(
          m_eiParent,
          FUPPOFSTATUS,
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
        setDGRptName("FUPPOFQRABR01"); //Set the report name
        setDGRptClass("FUPPOFQRABR01"); //Set the report class
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
    return new String("1.18");
  }

  /**
   * getVersion
   *
   * @return
   * @author Owner
   */
  public static String getVersion() {
    return ("FUPPOFQRABR01.java,v 1.18 2008/01/30 19:39:15 wendy Exp");
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
