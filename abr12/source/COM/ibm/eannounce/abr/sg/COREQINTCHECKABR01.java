//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//COREQINTCHECKABR01.java,v
//Revision 1.7  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.6  2006/01/24 17:07:00  yang
//Jtest Changes
//
//Revision 1.5  2004/12/09 21:32:46  joan
//fixes
//
//Revision 1.4  2004/12/03 22:36:31  joan
//fixes for search
//
//Revision 1.3  2004/11/04 23:35:30  joan
//adjust messages
//
//Revision 1.2  2004/09/21 23:47:16  joan
//fixes
//
//Revision 1.1  2004/09/21 00:31:25  joan
//add abr
//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
//import java.util.*;
//import java.io.*;

/**
 * COREQINTCHECKABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class COREQINTCHECKABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("COREQINTCHECKABR01");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    String strMT = null;
    String strModel = null;
    String strFeature = null;
    String strInventory = null;
    String strDgName = null;
    String RETURN = System.getProperty("line.separator");
    try {
      // if it's the first time, build the report header
      StringBuffer sbError = new StringBuffer();
      PDGUtility utility = new PDGUtility();
      start_ABRBuild();
      // Build the report header
      buildReportHeaderII();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);
      println("<br><b>CoReq: " + m_ei.getKey() + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);
      strMT =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "MACHTYPEATR");
      strModel =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "MODELATR");
      strFeature =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FEATURECODE");
      strInventory =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "INVENTORYGROUP");

      if (strMT == null
        || strModel == null
        || strFeature == null
        || strInventory == null) {
        setReturnCode(FAIL);
        sbError.append(
          "Error:Machine type, model, feature code, and Inventory Group can not be empty.");

      }

      if (getReturnCode() == PASS) {
        //There must exist a PRODSTRUCT with a MODEL and FEATURE
        StringBuffer sb = new StringBuffer();
        sb.append("map_MODEL:MACHTYPEATR=" + strMT + ";");
        sb.append("map_MODEL:MODELATR=" + strModel + ";");
        sb.append("map_FEATURE:FEATURECODE=" + strFeature + ";");
        sb.append("map_FEATURE:INVENTORYGROUP=" + strInventory);
        EntityItem[] aei =
          utility.dynaSearchII(
            m_db,
            m_prof,
            m_ei,
            "SRDPRODSTRUCT03",
            "PRODSTRUCT",
            sb.toString());

        if (aei == null || aei.length <= 0) {
          setReturnCode(FAIL);
          sbError.append(
            "Error:There's no PRODUCTSTRUCT with a MODEL with machine type "
              + strMT
              + " and model "
              + strModel
              + " and FEATURE with feature code "
              + strFeature
              + " and inventory group "
              + strInventory
              + RETURN);
        }
      }

      if (getReturnCode() == FAIL && sbError.toString().length() > 0) {
        println("<h3><font color=red>" + sbError.toString() + "</h3>");
        OPICMList list = new OPICMList();
        list.put("ABRRESULTS", "ABRRESULTS=" + sbError.toString());
        utility.updateAttribute(m_db, m_prof, m_ei, list);
      } else if (getReturnCode() == PASS) {
        println("<h3><font color=red>" + sbError.toString() + "</h3>");
        OPICMList list = new OPICMList();
        list.put("ABRRESULTS", "ABRRESULTS=Passed:OK");
        utility.updateAttribute(m_db, m_prof, m_ei, list);
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
    return "CoReq Integrity Check ABR.";
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
    return new String("1.7");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("COREQINTCHECKABR01.java,v 1.7 2006/03/03 19:23:27 bala Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "COREQINTCHECKABR01.java";
  }
}
