//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//AFIRMT430AABR02.java,v
//Revision 1.7  2006/03/03 19:23:25  bala
//remove reference to Constants.CSS
//
//Revision 1.6  2006/01/24 16:43:07  yang
//Jtest Changes
//
//Revision 1.5  2005/02/08 18:29:13  joan
//changes for Jtest
//
//Revision 1.4  2004/10/10 20:54:28  bala
//to update AFPDGERRORMSG on PDG error
//
//Revision 1.3  2004/09/10 20:22:36  joan
//fixes
//
//Revision 1.2  2004/09/01 22:36:38  joan
//fixes
//
//Revision 1.1  2004/09/01 15:49:44  joan
//add new abrs
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
 * AFIRMT430AABR02
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class AFIRMT430AABR02 extends PokBaseABR {
  /**
   *  Execute ABR.
   */

  // Class constants
  public final static String ABR = new String("AFIRMT430AABR02");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
  //private final EntityItem m_eiFUP = null;
  //private final int m_iExeCount = 0;
  //private PDGUtility m_utility = new PDGUtility();

  /**
   *  Description of the Method
   */
  public void execute_run() {
    SWSUBSPRODINI30APDG pdg = null;
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
        "<br><b>Software Subscription Data Generator Request: "
          + m_ei.getKey()
          + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);

      //============= run the PDG to generate data ==========================================
      try {
        if (getReturnCode() == PASS) {
          log("AFIRMT430AABR02 generating data");
          pdg =
            new SWSUBSPRODINI30APDG(
              null,
              m_db,
              m_prof,
              "SWSUBSPRODINI30APDG");
          pdg.setEntityItem(m_ei);
          pdg.executeAction(m_db, m_prof);
          log("AFIRMT430AABR02 finish generating data");
        }
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

  /**
   *  Description of the Method
   *
   *@param  _s   Description of the Parameter
   *@param  _s1  Description of the Parameter
   *@param  _s2  Description of the Parameter
   *@return      Description of the Return Value
   */
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
    return "Software Subscription Product Initial ABR.";
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
   *  Gets the revision attribute of the AFIRMT430AABR02 object
   *
   *@return    The revision value
   */
  public String getRevision() {
    return new String("1.7");
  }

  /**
   *  Gets the version attribute of the AFIRMT430AABR02 class
   *
   *@return    The version value
   */
  public static String getVersion() {
    return ("AFIRMT430AABR02.java,v 1.7 2006/03/03 19:23:25 bala Exp");
  }

  /**
   *  Gets the aBRVersion attribute of the AFIRMT430AABR02 object
   *
   *@return    The aBRVersion value
   */
  public String getABRVersion() {
    return "AFIRMT430AABR02.java";
  }
}
