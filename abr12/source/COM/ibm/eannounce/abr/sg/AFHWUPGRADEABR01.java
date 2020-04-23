//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//AFHWUPGRADEABR01.java,v
//Revision 1.12  2006/03/03 19:23:24  bala
//remove reference to Constants.CSS
//
//Revision 1.11  2006/01/24 16:37:59  yang
//J-test
//
//Revision 1.10  2005/02/08 18:29:12  joan
//changes for Jtest
//
//Revision 1.9  2004/03/18 00:49:03  joan
//spelling
//
//Revision 1.8  2004/03/18 00:44:10  joan
//remove recursive call
//
//Revision 1.7  2003/10/07 23:25:36  joan
//change for subs.
//
//Revision 1.6  2003/10/07 21:45:56  joan
//fix fb
//
//Revision 1.5  2003/10/02 15:09:57  joan
//fb fix
//
//Revision 1.4  2003/09/23 22:58:19  joan
//add changes
//
//Revision 1.3  2003/09/18 16:29:57  joan
//fix error
//
//Revision 1.2  2003/09/11 22:24:08  joan
//fb fixes
//
//Revision 1.1  2003/09/10 18:10:02  joan
//initial load
//
//Revision 1.1  2003/09/09 20:31:23  joan
//add HWUPGRADE abr
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

/**
 * AFHWUPGRADEABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class AFHWUPGRADEABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String DEF_NOT_POPULATED_HTML = new String("");
  /**
     * ABR
     *
     */
  public final static String ABR = new String("AFHWUPGRADEABR01");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
  private boolean m_bPDGComplete = false;
  private int m_iExeCount = 0;
  private HWUpgradePIPDG m_pdg = null;

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    String strError = null;
    int i = 0;
    String strDgName = null;
    String RETURN = System.getProperty("line.separator");
    try {
      m_iExeCount++;

      // if it's the first time, build the report header
      if (m_iExeCount == 1) {
        start_ABRBuild();
        // Build the report header
        buildReportHeaderII();

        m_egParent = m_elist.getParentEntityGroup();
        m_ei = m_egParent.getEntityItem(0);
        println(
          "<br><b>Hardware Upgrade PDG Request: "
            + m_ei.getKey()
            + "</b>");

        printNavigateAttributes(m_ei, m_egParent, true);

        setReturnCode(PASS);
      }

      //============= run the PDG to generate data ==========================================
      if (getReturnCode() == PASS) {
        log("AFHWUPGRADEABR01 generating data");
        if (m_iExeCount == 1) {
          log("AFHWUPGRADEABR01 first time generating");
          m_pdg = new HWUpgradePIPDG(null, m_db, m_prof, "HWUPPIPDG");
          m_pdg.setEntityItem(m_ei);
          m_pdg.executeAction(m_db, m_prof);
        } else {
          log("AFHWUPGRADEABR01 " + m_iExeCount + " time generating");
          m_pdg.checkMissingDataAgain(m_db, m_prof, true);
        }

        //it gets to the end of generating data
        if (m_iExeCount > 1) {
          m_pdg.savePDGStatus(m_db, m_prof);
        }
        m_bPDGComplete = true;
        log("AFHWUPGRADEABR01 finish generating data");
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
      m_bPDGComplete = true;
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
    } catch (UpdatePDHEntityException le) {
      m_bPDGComplete = true;
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>UpdatePDH error: "
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
    } catch (SBRException _sbrex) {
      m_bPDGComplete = true;
      strError = _sbrex.toString();
      i = strError.indexOf("(ok)");
      if (i < 0) {
        setReturnCode(UPDATE_ERROR);
        println(
          "<h3><font color=red>Generate Data error: "
            + replace(strError, RETURN, "<br>")
            + "</font></h3>");
        logError(_sbrex.toString());
      } else {
        strError = strError.substring(0, i);
        println("<h3> " + replace(strError, RETURN, "<br>") + "</h3>");
      }
    } catch (Exception exc) {
      m_bPDGComplete = true;
      // Report this error to both the datbase log and the PrintWriter
      println("Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      println("" + exc);
      exc.printStackTrace();
      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      // this is to check if it's time out in the middle of generating data.
      // if so, run again.
      if (!m_bPDGComplete) {
        setReturnCode(INTERNAL_ERROR);
        println(
          "<h3><font color=red>Generating Data is not complete. "
            + "</font></h3>");
        //        execute_run();
      }

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
    return "Hardware Upgrade Proc|? to Proc|? ABR.";
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
     * @author Administrator
     */
  public String getRevision() {
    return new String("1.12");
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("AFHWUPGRADEABR01.java,v 1.12 2006/03/03 19:23:24 bala Exp");
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
   */
  public String getABRVersion() {
    return "AFHWUPGRADEABR01.java";
  }
}
