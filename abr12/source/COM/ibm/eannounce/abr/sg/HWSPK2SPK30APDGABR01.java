//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//HWSPK2SPK30APDGABR01.java,v
//Revision 1.4  2008/01/30 19:39:16  wendy
//Cleanup RSA warnings
//
//Revision 1.3  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.2  2006/01/24 16:52:04  yang
//Jtest Changes
//
//Revision 1.1  2004/09/28 22:15:37  bala
//check in
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
//import java.util.*;
//import java.io.*;

/**
 * HWSPK2SPK30APDGABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class HWSPK2SPK30APDGABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("HWSPK2SPK30APDGABR01");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
 
  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    ACTHWSPK2SPK30APDG pdg;
    String strDgName = null;
    String RETURN = System.getProperty("line.separator");
    try {
      // if it's the first time, build the report header
      start_ABRBuild();
      // Build the report header
      buildReportHeaderII();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);
      println(
        "<br><b>Hardware ServerPackage Upgrade request: "
          + m_ei.getKey()
          + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);

      //============= run the PDG to generate data ==========================================
      if (getReturnCode() == PASS) {
        log("HWSPK2SPK30APDGABR01 generating data");
        pdg =
          new ACTHWSPK2SPK30APDG(
            null,
            m_db,
            m_prof,
            "ACTHWSPK2SPK30APDG");
        pdg.setEntityItem(m_ei);
        pdg.executeAction(m_db, m_prof);
        log("HWSPK2SPK30APDGABR01 finish generating data");
      }
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
    } catch (SBRException _sbrex) {
      String strError = _sbrex.toString();
      int i = strError.indexOf("(ok)");
      if (i < 0) {
        setReturnCode(UPDATE_ERROR);
        println(
          "<h3><font color=red>Generate Data error: "
            + replace(strError, RETURN, "<br>")
            + "</font></h3>");
        logError(_sbrex.toString());
      } else {
        strError = strError.substring(0, i);
        println(replace(strError, RETURN, "<br>"));
      }
    } catch (Exception exc) {
      // Report this error to both the datbase log and the PrintWriter
      println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      println("" + exc);
      exc.printStackTrace();
      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
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

      // set DG title
      strDgName = m_ei.toString();
      if (strDgName.length() > 64) {
        strDgName = strDgName.substring(0, 64);
      }
      setDGTitle(strDgName);
      setDGRptName(ABR);

      // set DG submit string
      setDGString(getABRReturnCode());
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

  private String replace(String _s, String _s1, String _s2) {
    String sResult = "";
    int iTab = _s.indexOf(_s1);

    while (_s.length() > 0 && iTab >= 0) {
      sResult = sResult + _s.substring(0, iTab) + _s2;
      _s = _s.substring(iTab + _s1.length());
      iTab = _s.indexOf(_s1);
    }
    sResult = sResult + _s;
    return sResult;
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
    return "Hardware Processor Upgrade ABR.";
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
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
    return new String("1.4");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("HWSPK2SPK30APDGABR01.java,v 1.4 2008/01/30 19:39:16 wendy Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "HWSPK2SPK30APDGABR01.java";
  }
}
