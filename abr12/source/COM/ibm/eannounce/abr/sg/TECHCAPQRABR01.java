//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//TECHCAPQRABR01.java,v
//Revision 1.47  2006/03/03 19:23:30  bala
//remove reference to Constants.CSS
//
//Revision 1.46  2006/01/24 17:11:53  yang
//Jtest Changes
//
//Revision 1.45  2003/11/07 01:22:38  yang
//Adding setDGRptClass
//
//Revision 1.44  2003/09/18 19:51:03  yang
//adding bala's stuff to finally {
//
//Revision 1.43  2003/06/10 16:17:36  yang
//Updated M0008
//
//Revision 1.42  2003/06/06 17:04:29  dave
//fixing report triggering
//
//Revision 1.41  2003/06/05 16:20:00  dave
//going after COF...
//
//Revision 1.40  2003/06/05 02:06:42  dave
//Status Attribute Checking
//
//Revision 1.39  2003/06/04 19:58:11  dave
//last one
//
//Revision 1.38  2003/06/04 19:56:14  dave
//we are almost there
//
//Revision 1.37  2003/06/04 19:49:11  dave
//is it back on track?
//
//Revision 1.36  2003/06/04 19:46:09  dave
//more traction
//
//Revision 1.35  2003/06/04 18:18:07  dave
//more consolidations
//
//Revision 1.34  2003/06/04 18:10:13  yang
//fixed error messages
//
//Revision 1.33  2003/06/04 16:51:46  yang
//Test
//
//Revision 1.32  2003/06/04 16:43:53  dave
//update
//
//Revision 1.31  2003/06/04 15:32:00  dave
//commonizing POFPOF
//
//Revision 1.30  2003/06/04 14:20:43  dave
//more commonizing
//
//Revision 1.29  2003/06/04 14:03:55  dave
//syntax
//
//Revision 1.28  2003/06/04 14:00:21  dave
//syntax
//
//Revision 1.27  2003/06/04 13:56:07  dave
//commonizing change report
//
//Revision 1.26  2003/06/04 05:37:56  dave
//promoted M0008 into the pokbaseabr class
//
//Revision 1.25  2003/06/04 05:18:28  dave
//fixing return code
//
//Revision 1.24  2003/06/04 04:50:09  dave
//making sure change report comes out properly
//
//Revision 1.23  2003/06/04 04:37:13  dave
//some final stuff for TECHCAPABILITY
//
//Revision 1.22  2003/06/04 04:18:59  dave
//triggering getNow
//
//Revision 1.21  2003/06/04 03:53:10  dave
//un Staticing getABRVersion
//
//Revision 1.20  2003/06/04 03:44:27  dave
//minor syntax
//
//Revision 1.19  2003/06/04 03:23:08  dave
//more fixes
//
//Revision 1.18  2003/06/04 03:16:01  dave
//more fixes
//
//Revision 1.17  2003/06/04 03:02:54  dave
//missing paren
//
//Revision 1.16  2003/06/04 02:59:55  dave
//found nullpointer
//
//Revision 1.15  2003/06/04 02:53:50  dave
//more trace and info to logs
//
//Revision 1.14  2003/06/04 02:36:46  dave
//more changes
//
//Revision 1.13  2003/06/04 02:32:48  dave
//trying to catch null pointer
//
//Revision 1.12  2003/06/04 02:24:17  dave
//syntax
//
//Revision 1.11  2003/06/04 02:22:19  dave
//finding null pointer
//
//Revision 1.10  2003/06/04 02:05:26  dave
//cleaning up banner for correct version control
//
//Revision 1.9  2003/06/04 01:45:47  dave
//more commonizing on routines
//
//Revision 1.8  2003/06/03 23:44:34  dave
//more syntax fixes
//
//Revision 1.7  2003/06/03 23:40:53  dave
//more fixes
//
//Revision 1.6  2003/06/03 23:36:35  dave
//syntax
//
//Revision 1.5  2003/06/03 23:28:34  dave
//commonizing setControlBlock
//
//Revision 1.4  2003/06/03 23:20:50  dave
//syntax
//
//Revision 1.3  2003/06/03 23:18:56  dave
//syntax
//
//Revision 1.2  2003/06/03 23:16:14  dave
//massive reorg for common routines
//
//Revision 1.1.1.1  2003/06/03 19:02:25  dave
//new 1.1.1 abr
//
//Revision 1.10  2003/06/03 18:12:17  dave
//commonizing
//routines
//
//Revision 1.9  2003/05/30 22:52:56  gregg
//cleanup...
//
//Revision 1.8  2003/05/30 22:40:56  gregg
//refreshClassifications
//
//Revision 1.7  2003/05/29 01:29:37  naomi
//added setControlBlock();
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
public class TECHCAPQRABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = "TECHCAPQRABR01";

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("TECHCAPQRABR01.java,v 1.47 2006/03/03 19:23:30 bala Exp");
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

  //TECHCAPSTATUS Constants
  /**
     * TECHCAPSTATUS
     *
     */
    public final static String TECHCAPSTATUS = "TECHCAPSTATUS";

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

      //Get Some Vital information
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

      // Default the thing to pass...
      setReturnCode(PASS);

      displayHeader(m_egParent, m_eiParent);

      if (!checkM0008(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }

      //
      // If return code is PASS update the statusnd the prior status is "Change (Ready for Review)" or "Change (Complete)"
      // then, produce a Change Report

      if (getReturnCode() == PASS) {
        // Change the status...
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

      // Print to the report..
      // Then print to the log file
      //
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
      le.printStackTrace();
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>UpdatePDH error: "
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
      le.printStackTrace();
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

    } finally {
      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName("TECHCAPQRABR01"); //Set the report name
      setDGRptClass("TECHCAPQRABR01"); //Set the report class
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
    return "This checks to see if the Techncal Capability Record has a valid Parent/Child classification setting in both directions.  If successfull, it will <br/>"
        + "transition the status and printa change report if it was currently in a change request state.</br>";
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
    return new String("1.47");
  }
}
