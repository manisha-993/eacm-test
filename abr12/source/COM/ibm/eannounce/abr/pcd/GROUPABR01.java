//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//$Log: GROUPABR01.java,v $
//Revision 1.6  2006/03/03 19:24:11  bala
//remove reference to Constants.CSS
//
//Revision 1.5  2006/01/25 17:45:31  yang
//Jtest changes
//
//Revision 1.4  2005/01/31 16:30:05  joan
//make changes for Jtest
//
//Revision 1.3  2005/01/27 16:39:54  joan
//changes for Jtest
//
//Revision 1.2  2004/05/13 18:11:09  bala
//checkin for Joan
//
//Revision 1.1.2.1  2004/05/12 22:26:26  joan
//initial load
//

package COM.ibm.eannounce.abr.pcd;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
//import java.io.*;

/**
 * Javadoc header
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class GROUPABR01 extends PokBaseABR {
  // Class constants
  /**
     * ABR
     *
     */
  public final static String ABR = new String("GROUPABR01");
  /**
     * YES
     *
     */
  public final static String YES = new String("0010");
  /**
     * SYSTEM
     *
     */
  public final static String SYSTEM = new String("0080");

  private EntityGroup m_egParent = null;
  private EntityItem m_ei = null;
  private EANList m_elGOA = new EANList();
  private StringBuffer m_sbMTDESC = null;

  /**
   * @see COM.ibm.opicmpdh.middleware.taskmaster.AbstractTask#execute_run()
   * @author Administrator
   */
  public void execute_run() {
    String strOFTYPE;
    EntityGroup egGOA;
    Vector vGOA;
    try {

      start_ABRBuild();
      m_sbMTDESC = new StringBuffer();
      buildReportHeaderII();
      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);
      println("<br><b>Order Offering: " + m_ei.getKey() + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);

      setReturnCode(PASS);

      //==== check OF entities =================
      strOFTYPE =
        getAttributeFlagEnabledValue(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "OFFERINGTYPE")
        .trim();
      if (strOFTYPE.equals(SYSTEM)) {
        println("<br /><font color=red>Failed. OF TYPE = SYSTEM</font>");
        setReturnCode(FAIL);
      }

      //==== check GOA entities =================
      log("GROUPABR01 checking GOA");
      egGOA = m_elist.getEntityGroup("GOA");

      vGOA =
        getChildrenEntityIds(
          m_elist,
          m_ei.getEntityType(),
          m_ei.getEntityID(),
          "GOA",
          "OFGOA");
      if (vGOA.size() <= 0) {
        println("<br /><font color=red>Failed. There is no GOA</font>");
        setReturnCode(FAIL);
      }

      for (int j = 0; j < vGOA.size(); j++) {
        int iID = ((Integer) vGOA.elementAt(j)).intValue();
        EntityItem eiGOA = egGOA.getEntityItem("GOA" + iID);
        m_elGOA.put(eiGOA);
        Vector vOF =
          getParentEntityIds(
            eiGOA.getEntityType(),
            eiGOA.getEntityID(),
            "OF",
            "OFGOA");
        if (vOF.size() > 1) {
          println("<br /><font color=red>Failed. GOA links to more than one OF.</font>");
          setReturnCode(FAIL);
        }
      }

      //==== check attribute COMPUB of relators OFCPGOS  =================
      if (getReturnCode() == PASS) {
        log("GROUPABR01 checking check attribute COMPUB of relators OFCPGOS");
        for (int i = 0; i < m_ei.getDownLinkCount(); i++) {
          EntityItem eiOFCPGOS = (EntityItem) m_ei.getDownLink(i);
          if (eiOFCPGOS.getEntityType().equals("OFCPGOS")) {
            String strCOMPUB =
              getAttributeFlagEnabledValue(
                m_elist,
                eiOFCPGOS.getEntityType(),
                eiOFCPGOS.getEntityID(),
                "COMPUB")
              .trim();
            if (strCOMPUB.equals(YES)) {
              // go down to CPGOS
              EntityItem eiCPGOS =
                (EntityItem) eiOFCPGOS.getDownLink(0);
              EntityGroup egCPG = m_elist.getEntityGroup("CPG");
              Vector vCPG =
                getParentEntityIds(
                  eiCPGOS.getEntityType(),
                  eiCPGOS.getEntityID(),
                  "CPG",
                  "CPGCPGOS");

              for (int j = 0; j < vCPG.size(); j++) {
                int iID =
                  ((Integer) vCPG.elementAt(j)).intValue();
                EntityItem eiCPG =
                  egCPG.getEntityItem("CPG" + iID);
                String strMTPUB =
                  getAttributeFlagEnabledValue(
                    m_elist,
                    eiCPG.getEntityType(),
                    eiCPG.getEntityID(),
                    "MTPUB")
                  .trim();
                if (strMTPUB.equals(YES)) {
                  String strMTDESC =
                    getAttributeValue(
                      m_elist,
                      eiCPG.getEntityType(),
                      eiCPG.getEntityID(),
                      "MACHTYPEDESC");
                  m_sbMTDESC.append(strMTDESC + "<br>");
                }
              }
            }
          }
        }
      }

      if (getReturnCode() == PASS) {
        if (m_sbMTDESC.toString().length() > 0) {
          PDGUtility util = new PDGUtility();
          for (int l = 0; l < m_elGOA.size(); l++) {
            EntityItem eiGOA = (EntityItem) m_elGOA.getAt(l);
            OPICMList attList = new OPICMList();
            attList.put(
              "GOASYSUNITPREQ_LT",
              "GOASYSUNITPREQ_LT=" + m_sbMTDESC.toString());
            util.updateAttribute(m_db, m_prof, eiGOA, attList);
          }
        } else {
          log("MACHTYPEDESC is blank.");
        }
      }

      println(
        "<br/><br /><b>"
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
      setReturnCode(UPDATE_ERROR);
      println(
        "<h3><font color=red>Generate Data error: "
          + _sbrex.toString()
          + "</font></h3>");
      logError(_sbrex.toString());
    } catch (Exception exc) {
      // Report this error to both the datbase log and the PrintWriter
      println(
        "<br/>Error in "
          + m_abri.getABRCode()
          + ":"
          + exc.getMessage());
      println("" + exc);
      exc.printStackTrace();
      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      // set DG title
      String strNavName =
        getABREntityDesc(m_ei.getEntityType(), m_ei.getEntityID());
      String strABRAttrDesc = getMetaAttributeDescription(m_ei, ABR);

      String strDgName = strABRAttrDesc + " for " + strNavName;
      if (strDgName.length() > 64) {
        strDgName = strDgName.substring(0, 64);
      }

      setDGTitle(strDgName);

      // set DG submit string
      setDGString(getABRReturnCode());
      setDGRptName(ABR);
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
   *  Get ABR description
   *
   *@return    java.lang.String
   */
  public String getDescription() {
    return "The purpose of this ABR is to set GOA's GOASYSUNITPREQ_LT";
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
    return new String("$Revision: 1.6 $");
  }

  /**
   * getVersion
   *
   * @return
   * @author Administrator
   */
  public static String getVersion() {
    return ("$Id: GROUPABR01.java,v 1.6 2006/03/03 19:24:11 bala Exp $");
  }

  /**
   * @see COM.ibm.eannounce.abr.util.PokBaseABR#getABRVersion()
   * @author Administrator
   */
  public String getABRVersion() {
    return ("GROUPABR01.java,v 1.2");
  }
}
