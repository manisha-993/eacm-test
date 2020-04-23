//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//FCINFCINTCHECKABR01.java,v
//Revision 1.4  2006/03/03 19:23:28  bala
//remove reference to Constants.CSS
//
//Revision 1.3  2006/01/24 16:54:37  yang
//Jtest Changes
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
 * FCINFCINTCHECKABR01
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class FCINFCINTCHECKABR01 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("FCINFCINTCHECKABR01");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    EntityItem[] aeiFrom;
    EntityItem[] aeiTo;
    StringBuffer sb;
    String strFeature = null;
    String strFeature2 = null;
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
      println("<br><b>Feature in Feature: " + m_ei.getKey() + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);
      strFeature =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FEATURECODE");
      strFeature2 =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "FEATURECODE2");
      strInventory =
        getAttributeValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "INVENTORYGROUP");

      if (strFeature == null || strFeature2 == null) {
        setReturnCode(FAIL);
        sbError.append(
          "Error:Feature code and Contained Feature Code can not be empty.");

      }

      if (getReturnCode() == PASS) {
        //For the FCINFC, the FEATURECODE should not equal FEATURECODE2

        if (strFeature.equals(strFeature2)) {
          setReturnCode(FAIL);
          sbError.append(
            "Error:Feature Code are the same with Contained Feature Code.\n");
        }
        //There must exist a FEATURE with FEATURECODE and INVENTORYGROUP

        sb = new StringBuffer();
        sb.append("map_FEATURECODE=" + strFeature + ";");
        sb.append("map_INVENTORYGROUP=" + strInventory);
        aeiFrom =
          utility.dynaSearch(
            m_db,
            m_prof,
            m_ei,
            "SRDFEATURE3",
            "FEATURE",
            sb.toString());

        if (aeiFrom == null || aeiFrom.length <= 0) {
          setReturnCode(FAIL);
          sbError.append(
            "Error:There's no FEATURE with feature code "
              + strFeature
              + " and inventory group "
              + strInventory
              + RETURN);
        }

        //There must exist a FEATURE with FEATURECODE2 and INVENTORYGROUP
        sb = new StringBuffer();
        sb.append("map_FEATURECODE=" + strFeature2 + ";");
        sb.append("map_INVENTORYGROUP=" + strInventory);
        aeiTo =
          utility.dynaSearch(
            m_db,
            m_prof,
            m_ei,
            "SRDFEATURE3",
            "FEATURE",
            sb.toString());

        if (aeiTo == null || aeiTo.length <= 0) {
          setReturnCode(FAIL);
          sbError.append(
            "Error:There's no FEATURE with feature code "
              + strFeature2
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
    return "Feature in Feature Integrity Check ABR.";
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
    return ("FCINFCINTCHECKABR01.java,v 1.4 2006/03/03 19:23:28 bala Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "FCINFCINTCHECKABR01.java";
  }
}
