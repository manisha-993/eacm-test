//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//CPMODEL2POFABR02.java,v
//Revision 1.9  2008/01/30 19:39:16  wendy
//Cleanup RSA warnings
//
//Revision 1.8  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.7  2006/01/24 17:07:52  yang
//Jtest Changes
//
//Revision 1.6  2005/01/12 01:24:35  joan
//fixes
//
//Revision 1.5  2005/01/11 23:20:16  joan
//fixes
//
//Revision 1.4  2004/11/29 23:42:39  bala
//fixes
//
//Revision 1.3  2004/11/24 21:15:45  bala
//disable check where it allows only 1 FEATURE
//
//Revision 1.2  2004/09/16 23:05:07  bala
//fix
//
//Revision 1.1  2004/09/09 23:05:36  bala
//checkin
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
//import java.io.*;

/**
 * CPMODEL2POFABR02
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class CPMODEL2POFABR02 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("CPMODEL2POFABR02");
  /**
     * HARDWARE
     *
     */
    public final static String HARDWARE = new String("100");
  /**
     * SOFTWARE
     *
     */
    public final static String SOFTWARE = new String("101");
  //  public final static String FEATURECODE = new String("500");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    //EntityGroup egFUP =null;
    Vector vFUP;
    ACTCPMODEL2POFPDG pdg;
    StringBuffer sb;
    String strDgName = null;
    String RETURN = System.getProperty("line.separator");
    try {
      // if it's the first time, build the report header
      start_ABRBuild();
      // Build the report header
      buildReportHeaderII();
      //        logMessage("***********************************Printing Entity List");
      //        logMessage(m_elist.dump(false));
      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);
      println("<br><b>Model: " + m_ei.getKey() + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);

      // check if there's any FEATURE exist
      //egFUP = m_elist.getEntityGroup("FEATURE");
      logMessage("**************************Before getParentEntityIds");
      vFUP = new Vector();
      vFUP =
        getParentEntityIds(
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FEATURE",
          "PRODSTRUCT");
      if (vFUP.size() <= 0) {
        println("<br /><font color=red>Failed. There are no FEATURES/font>");
        setReturnCode(FAIL);
      }

      //============= run the PDG to generate data ==========================================
      if (getReturnCode() == PASS) {
        log("CPMODEL2POFABR02 generating data");
        pdg =
          new ACTCPMODEL2POFPDG(
            null,
            m_db,
            m_prof,
            "ACTCPMODEL2POFPDG");
        pdg.setEntityItem(m_ei);
        pdg.setABReList(m_elist);
        pdg.setExcludeCopy("001");
        //The sp will not copy the attribute values
        pdg.executeAction(m_db, m_prof);
        sb = pdg.getActivities();
        println("</br></br/><b>Generated Data:</b>");
        println("<br/>" + sb.toString());

        log("CPMODEL2POFABR02 finish generating data");
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
    return "Hardware Copy OOF To POF ABR.";
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
    return new String("1.9");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("CPMODEL2POFABR02.java,v 1.9 2008/01/30 19:39:16 wendy Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "CPMODEL2POFABR02.java";
  }
}
