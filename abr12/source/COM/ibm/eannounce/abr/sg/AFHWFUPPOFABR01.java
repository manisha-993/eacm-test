//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//AFHWFUPPOFABR01.java,v
//Revision 1.5  2006/03/03 19:23:24  bala
//remove reference to Constants.CSS
//
//Revision 1.4  2006/01/24 16:36:40  yang
//J-test
//
//Revision 1.3  2005/02/08 18:29:12  joan
//changes for Jtest
//
//Revision 1.2  2003/12/12 21:28:52  joan
//adjust code
//
//Revision 1.1  2003/12/09 17:31:48  joan
//initial load
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

/**
 * AFHWFUPPOFABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class AFHWFUPPOFABR01 extends PokBaseABR {
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
  public final static String ABR = new String("AFHWFUPPOFABR01");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
  private HWFUPPOFPDG m_pdg = null;

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    String strDgName = null;
    String RETURN = System.getProperty("line.separator");
    try {
      start_ABRBuild();
      // Build the report header
      buildReportHeaderII();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);
      println(
        "<br><b>Hardware FUP/POF PDG Request: "
          + m_ei.getKey()
          + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);

      setReturnCode(PASS);

      //============= run the PDG to generate data ==========================================
      if (getReturnCode() == PASS) {
        log("AFHWFUPPOFABR01 generating data");
        m_pdg = new HWFUPPOFPDG(null, m_db, m_prof, "HWFUPPOFPDG");
        m_pdg.setEntityItem(m_ei);
        m_pdg.executeAction(m_db, m_prof);
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
        println("<h3> " + replace(strError, RETURN, "<br>") + "</h3>");
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
    return "Hardware FUP/POF ABR 01.";
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
    return new String("1.5");
  }

  /**
     * getVersion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("AFHWFUPPOFABR01.java,v 1.5 2006/03/03 19:23:24 bala Exp");
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
   */
  public String getABRVersion() {
    return "AFHWFUPPOFABR01.java";
  }
}
