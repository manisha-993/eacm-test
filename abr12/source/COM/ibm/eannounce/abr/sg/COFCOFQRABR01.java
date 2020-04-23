//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//COFCOFQRABR01.java,v
//Revision 1.17  2008/01/30 19:39:14  wendy
//Cleanup RSA warnings
//
//Revision 1.16  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.15  2006/01/24 16:59:48  yang
//Jtest Changes
//
//Revision 1.14  2003/11/07 01:20:40  yang
//Adding setDGRptClass
//
//Revision 1.13  2003/09/18 19:50:57  yang
//adding bala's stuff to finally {
//
//Revision 1.12  2003/06/18 15:25:43  dave
//M005 mod
//
//Revision 1.11  2003/06/06 17:04:28  dave
//fixing report triggering
//
//Revision 1.10  2003/06/05 16:13:41  yang
//Updated M0001
//
//Revision 1.9  2003/06/05 16:07:24  yang
//Updated to new version
//
//Revision 1.8  2003/06/04 03:53:08  dave
//un Staticing getABRVersion
//
//Revision 1.7  2003/06/04 03:44:25  dave
//minor syntax
//
//Revision 1.6  2003/06/04 03:41:43  dave
//adding getABRVersion
//
//Revision 1.5  2003/06/04 01:45:47  dave
//more commonizing on routines
//
//Revision 1.4  2003/06/03 23:28:33  dave
//commonizing setControlBlock
//
//Revision 1.3  2003/06/03 19:14:10  dave
//commonized T1,T2 and display attributes
//
//Revision 1.2  2003/06/03 19:11:35  yang
//fixed feedback 51091: rule M0005
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.7  2003/06/03 18:37:54  dave
//test
//
//Revision 1.6  2003/06/03 18:17:37  dave
//preping for common T1 and T2 processing
//
//Revision 1.5  2003/06/03 18:06:42  yang
//*** empty log message ***
//
//Revision 1.4  2003/05/30 19:55:06  yang
//Updated S0002
//
//Revision 1.3  2003/05/28 00:30:25  naomi
//added output on checkM0001
//

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
public class COFCOFQRABR01 extends PokBaseABR {

  //ABR
  /**
   * ABR
   *
   */
  public final static String ABR = new String("COFCOFQRABR01");

  /**
   * COFCOFMGMTGRP
   *
   */
  public final static String COFCOFMGMTGRP = new String("COFCOFMGMTGRP");
  /**
   * COFCOFSTATUS
   *
   */
  public final static String COFCOFSTATUS = "COFCOFSTATUS";

  /**
   *  Execute ABR.
   */

  private EntityGroup m_egParent = null; //root COFCOFMGMTGRP
  private EntityItem m_eiParent = null; //root COFCOFMGMTGRP
  /**
   * execute_run
   *
   * @author Owner
   */
  public void execute_run() {
    try {

      start_ABRBuild();
      //Get the root COFCOFMGMTGRP
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
      if (!checkM0005(m_eiParent, "COFMEMBERCOFOMG")) {
        setReturnCode(FAIL);
      }
      if (!checkM0001(m_eiParent)) {
        setReturnCode(FAIL);
      }

      if (getReturnCode() == PASS) {
        // Change the status...
        setFlagValue(
          m_eiParent,
          COFCOFSTATUS,
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
        setDGRptName("COFCOFQRABR01"); //Set the report name
        setDGRptClass("COFCOFQRABR01"); //Set the report class
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
    return new String("1.17");
  }

  /**
   * getVersion
   *
   * @return
   * @author Owner
   */
  public static String getVersion() {
    return ("COFCOFQRABR01.java,v 1.17 2008/01/30 19:39:14 wendy Exp");
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
