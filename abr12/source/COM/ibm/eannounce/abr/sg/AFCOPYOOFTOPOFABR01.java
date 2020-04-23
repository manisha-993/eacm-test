//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//AFCOPYOOFTOPOFABR01.java,v
//Revision 1.7  2008/01/30 19:39:15  wendy
//Cleanup RSA warnings
//
//Revision 1.6  2006/03/03 19:23:24  bala
//remove reference to Constants.CSS
//
//Revision 1.5  2006/01/24 16:36:09  yang
//J-test
//
//Revision 1.4  2005/02/08 18:29:12  joan
//changes for Jtest
//
//Revision 1.3  2003/12/10 21:30:34  joan
//add new abr
//
//Revision 1.2  2003/11/20 21:35:36  joan
//some changes
//
//Revision 1.1  2003/11/18 19:08:43  joan
//add AFCOPYOOFTOPOFABR01
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;

/**
 * AFCOPYOOFTOPOFABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class AFCOPYOOFTOPOFABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("AFCOPYOOFTOPOFABR01");
  /**
   * Hardware
   *
   */
  public final static String HARDWARE = new String("100");
  /**
   * Software
   *
   */
  public final static String SOFTWARE = new String("101");
  /**
   * Featurecode
   *
   */
  public final static String FEATURECODE = new String("500");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    EntityGroup egFUP = null;
    Vector vFUP = null;
    HWCOPYOFTOPOFPDG pdg = null;
    StringBuffer sb = null;
    String strFlagCode = null;
    String strDgName = null;
    String RETURN = System.getProperty("line.separator");
    try {
      // if it's the first time, build the report header
      start_ABRBuild();
      // Build the report header
      buildReportHeaderII();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);
      println("<br><b>Order Offering: " + m_ei.getKey() + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);

      // check OOFCAT = Hardware or Software
      strFlagCode =
        getAttributeFlagEnabledValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "OOFCAT")
            .trim();
      if (!(strFlagCode.equals(HARDWARE)
                    || strFlagCode.equals(SOFTWARE))) {
        println("</br/><font color=red>Failed. the Order Offering is not in ‘Hardware/Software' Category. </font>");
        setReturnCode(FAIL);
      }

      // check OOFSUBCAT = FeatureCode
      strFlagCode =
        getAttributeFlagEnabledValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "OOFSUBCAT")
            .trim();
      if (!strFlagCode.equals(FEATURECODE)) {
        println("</br/><font color=red>Failed. the Order Offering is not in ‘FeatureCode’ SubCategory. </font>");
        setReturnCode(FAIL);
      }

      // check if there's one and only one FUP exist
      egFUP = m_elist.getEntityGroup("FUP");
      vFUP =
        getChildrenEntityIds(
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FUP",
          "OOFFUP");
      if (vFUP.size() <= 0) {
        println("<br /><font color=red>Failed. There is no FUP</font>");
        setReturnCode(FAIL);
      } else if (vFUP.size() > 1) {
        println("<br /><font color=red>Failed. There is not one and only one FUP.</font>");
        setReturnCode(FAIL);
        println("<br/></br/><b>Function Point(s):</b>");
        for (int i = 0; i < vFUP.size(); i++) {
          int iID = ((Integer) vFUP.elementAt(i)).intValue();
          EntityItem ei =
            egFUP.getEntityItem(egFUP.getEntityType() + iID);
          println("</br/><LI> " + ei.toString());
        }
      }

      //============= run the PDG to generate data ==========================================
      if (getReturnCode() == PASS) {
        log("AFCOPYOOFTOPOFABR01 generating data");
        pdg =
          new HWCOPYOFTOPOFPDG(
            null,
            m_db,
            m_prof,
            "HWCOPYOFTOPOFPDG");
        pdg.setEntityItem(m_ei);
        pdg.setABReList(m_elist);
        pdg.setExcludeCopy("0");
        pdg.executeAction(m_db, m_prof);
        sb = pdg.getActivities();
        println("</br></br/><b>Generated Data:</b>");
        println("<br/>" + sb.toString());

        log("AFCOPYOOFTOPOFABR01 finish generating data");
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
     * @author Administrator
     */
  public String getRevision() {
    return new String("1.7");
  }

  /**
     * getversion
     *
     * @return
     * @author Administrator
     */
  public static String getVersion() {
    return ("AFCOPYOOFTOPOFABR01.java,v 1.7 2008/01/30 19:39:15 wendy Exp");
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
   */
  public String getABRVersion() {
    return "AFCOPYOOFTOPOFABR01.java";
  }
}
