//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//FCTRANSINTCHECKABR01.java,v
//Revision 1.6  2006/03/03 19:23:28  bala
//remove reference to Constants.CSS
//
//Revision 1.5  2006/01/24 16:50:57  yang
//Jtest Changes
//
//Revision 1.4  2004/12/09 21:32:46  joan
//fixes
//
//Revision 1.3  2004/12/03 22:36:31  joan
//fixes for search
//
//Revision 1.2  2004/11/04 23:35:30  joan
//adjust messages
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
 * FCTRANSINTCHECKABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class FCTRANSINTCHECKABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("FCTRANSINTCHECKABR01");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    EntityItem[] aeiFrom;
    StringBuffer sb;
    EntityItem[] aeiTo;
    String strFromMT = null;
    String strFromModel = null;
    String strFromFeature = null;
    String strMT = null;
    String strModel = null;
    String strToFeature = null;
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
      println("<br><b>Feature Transaction: " + m_ei.getKey() + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);
      strFromMT =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FROMMACHTYPE");
      strFromModel =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FROMMODEL");
      strFromFeature =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FROMFEATURECODE");
      strMT =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "TOMACHTYPE");
      strModel =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "TOMODEL");
      strToFeature =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "TOFEATURECODE");

      if (strFromMT == null
        || strFromModel == null
        || strFromFeature == null
        || strMT == null
        || strModel == null
        || strToFeature == null) {
        setReturnCode(FAIL);
        sbError.append(
          "Error:From machine type, model, feature code and To Machine type, model, feature code can not be empty.");

      }

      if (getReturnCode() == PASS) {
        //For the FCTRANSACTION, check that FROMMODEL & FROMFEATURECODE does not equal TOMODEL & TOFEATURECODE

        if (strFromFeature.equals(strToFeature)
          && strFromModel.equals(strModel)) {
          setReturnCode(FAIL);
          sbError.append(
            "Error:From Model and Feature Code are the same with To Model and Feature Code.\n");
        }
        //There must exist a PRODSTRUCT with a from MODEL and From FEATURE

        sb = new StringBuffer();
        sb.append("map_MODEL:MACHTYPEATR=" + strFromMT + ";");
        sb.append("map_MODEL:MODELATR=" + strFromModel + ";");
        sb.append("map_FEATURE:FEATURECODE=" + strFromFeature);
        aeiFrom =
          utility.dynaSearchII(
            m_db,
            m_prof,
            m_ei,
            "SRDPRODSTRUCT03",
            "PRODSTRUCT",
            sb.toString());

        if (aeiFrom == null || aeiFrom.length <= 0) {
          setReturnCode(FAIL);
          sbError.append(
            "Error:There's no PRODUCTSTRUCT with a MODEL with machine type "
              + strFromMT
              + " and model "
              + strFromModel
              + " and FEATURE with feature code "
              + strFromFeature
              + RETURN);
        }

        //There must exist a PRODSTRUCT with a To MODEL and To FEATURE
        sb = new StringBuffer();
        sb.append("map_MODEL:MACHTYPEATR=" + strMT + ";");
        sb.append("map_MODEL:MODELATR=" + strModel + ";");
        sb.append("map_FEATURE:FEATURECODE=" + strToFeature);
        aeiTo =
          utility.dynaSearchII(
            m_db,
            m_prof,
            m_ei,
            "SRDPRODSTRUCT03",
            "PRODSTRUCT",
            sb.toString());

        if (aeiTo == null || aeiTo.length <= 0) {
          setReturnCode(FAIL);
          sbError.append(
            "Error:There's no PRODUCTSTRUCT with a MODEL with machine type "
              + strMT
              + " and model "
              + strModel
              + " and FEATURE with feature code "
              + strToFeature
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
    return "Feature Transaction Integrity Check ABR.";
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
    return new String("1.6");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("FCTRANSINTCHECKABR01.java,v 1.6 2006/03/03 19:23:28 bala Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "FCTRANSINTCHECKABR01.java";
  }
}
