//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//MKTGCOMPATABR01.java,v
//Revision 1.10  2008/01/30 19:39:17  wendy
//Cleanup RSA warnings
//
//Revision 1.9  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.8  2006/01/24 16:45:53  yang
//Jtest Changes
//
//Revision 1.7  2005/12/07 21:01:43  joan
//fixes
//
//Revision 1.6  2005/12/07 16:02:54  joan
//fixes
//
//Revision 1.5  2005/09/07 22:10:36  joan
//fixes
//
//Revision 1.4  2005/09/07 19:06:09  joan
//fixes
//
//Revision 1.3  2005/09/06 16:50:38  joan
//changes
//
//Revision 1.2  2005/09/02 17:10:48  joan
//fixes
//
//Revision 1.1  2005/09/02 16:59:39  joan
//add new abr
//
//Revision 1.1  2005/09/01 16:52:13  joan
//add new file
//
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
//import java.util.*;
//import java.io.*;

/**
 * MKTGCOMPATABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class MKTGCOMPATABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("MKTGCOMPATABR01");
  private PDGUtility m_utility = new PDGUtility();
  private EntityItem m_ei = null;
  /**
     * STARTDATE
     *
     */
    public final static String STARTDATE = "1980-01-01-00.00.00.000000";
  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    DatePackage dp = null;
    EntityGroup eg = null;
    MKTGCOMPATPDG pdg;
    OPICMList attList;
    String strNow = null;
    //String strCurrentDate = null;
    String strCATLGMKTGLASTRUN = null;
    String strOFFCOUNTRY = null;
    String strDgName = null;
    String RETURN = System.getProperty("line.separator");
    try {
      start_ABRBuild(false);
      setReturnCode(PASS);
      buildReportHeaderII();

      dp = m_db.getDates();
      strNow = dp.getNow();
      //strCurrentDate = strNow.substring(0, 10);
      eg =
        new EntityGroup(
          null,
          m_db,
          m_prof,
          m_abri.getEntityType(),
          "Edit",
          false);
      m_ei =
        new EntityItem(
          eg,
          m_prof,
          m_db,
          m_abri.getEntityType(),
          m_abri.getEntityID());

      println("<br><b>Catalog Country: " + m_ei.getKey() + "</b>");
      printNavigateAttributes(m_ei, eg, true);

      // check CATLGCNTRY attributes
      strCATLGMKTGLASTRUN =
        m_utility.getAttrValue(m_ei, "CATLGMKTGLASTRUN");
      if (strCATLGMKTGLASTRUN == null
        || strCATLGMKTGLASTRUN.length() <= 0) {
        attList = new OPICMList();
        attList.put(
          "CATLGMKTGLASTRUN",
          "CATLGMKTGLASTRUN=" + STARTDATE);

        m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
      } else {
        if (!m_utility.isDateFormat(strCATLGMKTGLASTRUN)) {
          println("CATLGMKTGLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000");
          setReturnCode(FAIL);
        } else {
          if (strCATLGMKTGLASTRUN.length() == 10) {
            attList = new OPICMList();
            attList.put(
              "CATLGMKTGLASTRUN",
              "CATLGMKTGLASTRUN="
                + strCATLGMKTGLASTRUN
                + "-00.00.00.000000");

            m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
          }
        }
      }

      strOFFCOUNTRY = m_utility.getAttrValue(m_ei, "OFFCOUNTRY");
      if (strOFFCOUNTRY == null || strOFFCOUNTRY.length() <= 0) {
        println("OFFCOUNTRY is blank.");
        setReturnCode(FAIL);
      }
      if (getReturnCode() == PASS) {
        log("MKTGCOMPATABR01 generating data");
        pdg =
          new MKTGCOMPATPDG(null, m_db, m_prof, "MKTGCOMPATPDG");
        pdg.setEntityItem(m_ei);
        pdg.executeAction(m_db, m_prof);
        log("MKTGCOMPATABR01 finish generating data");
        dp = m_db.getDates();
        strNow = dp.getNow();

        attList = new OPICMList();
        attList.put("CATLGMKTGLASTRUN", "CATLGMKTGLASTRUN=" + strNow);

        m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
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
      strDgName =
        getABRDescription()
        + ":"
        + m_abri.getEntityType()
        + ":"
        + m_abri.getEntityID();
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
    return "Marketing Compatibility ABR";
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
    return new String("1.10");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("MKTGCOMPATABR01.java,v 1.10 2008/01/30 19:39:17 wendy Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "MKTGCOMPATABR01.java";
  }
}
