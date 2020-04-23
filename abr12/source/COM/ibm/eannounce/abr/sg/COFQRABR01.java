//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//COFQRABR01.java,v
//Revision 1.19  2006/03/03 19:23:27  bala
//remove reference to Constants.CSS
//
//Revision 1.18  2006/01/24 17:02:40  yang
//Jtest Changes
//
//Revision 1.17  2003/11/07 01:20:54  yang
//Adding setDGRptClass
//
//Revision 1.16  2003/09/18 19:51:27  yang
//adding bala's stuff to finally {
//
//Revision 1.15  2003/07/02 18:14:59  yang
//Updated setFlagValue to include the children
//
//Revision 1.14  2003/06/06 17:04:28  dave
//fixing report triggering
//
//Revision 1.13  2003/06/06 16:58:19  minhthy
//change checkM0004() to checkM0004GrandChild()
//
//Revision 1.12  2003/06/05 21:53:47  yang
//Updated S0002
//
//Revision 1.11  2003/06/05 16:34:12  dave
//more fixes to syntax
//
//Revision 1.10  2003/06/05 16:20:00  dave
//going after COF...
//
//Revision 1.9  2003/06/04 15:00:04  yang
//Added StackTrace
//
//Revision 1.8  2003/06/04 03:53:08  dave
//un Staticing getABRVersion
//
//Revision 1.7  2003/06/04 03:44:25  dave
//minor syntax
//
//Revision 1.6  2003/06/04 03:41:44  dave
//adding getABRVersion
//
//Revision 1.5  2003/06/03 23:28:33  dave
//commonizing setControlBlock
//
//Revision 1.4  2003/06/03 19:47:31  yang
//fixed feedback 51110
//
//Revision 1.3  2003/06/03 19:41:52  yang
//fixed merge
//
//Revision 1.2  2003/06/03 19:33:58  dave
//more consolidation
//
//Revision 1.1.1.1  2003/06/03 19:02:24  dave
//new 1.1.1 abr
//
//Revision 1.15  2003/06/03 18:17:37  dave
//preping for common T1 and T2 processing
//
//Revision 1.14  2003/05/30 20:13:42  yang
//Updated S0002
//
//Revision 1.13  2003/05/29 01:33:19  naomi
//fix output M0007, M0008
//
//Revision 1.11  2003/05/27 19:02:59  naomi
//fixed checkS0001_GC
//
//Revision 1.10  2003/05/23 22:30:43  naomi
//fixed isStatusOK
//
//Revision 1.9  2003/05/22 18:30:30  yang
//*** empty log message ***
//

//Revision 1.16  2003/04/16 22:07:30  yang

//Modify ABR to new spec

//

//

//Revision 1.1  2002/09/11 21:08:36  bala

//check in

//

package COM.ibm.eannounce.abr.sg;

//import COM.ibm.opicmpdh.middleware.*;
//import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.io.*;
//import java.sql.*;

/**
 * COFQRABR01
 *
 * @author Owner
 */
public class COFQRABR01 extends PokBaseABR {

  // S0002 only check for PUBLICATION , RULES
  /**
     * c_hshEntities
     *
     */
    public static final Hashtable c_hshEntities = new Hashtable();
  static {
    c_hshEntities.put("BPEXHIBIT", "HI");
    c_hshEntities.put("CATINCL", "HI");
    c_hshEntities.put("CHANNEL", "HI");
    c_hshEntities.put("COMMERCIALOF", "HI");
    c_hshEntities.put("CRYPTO", "HI");
    c_hshEntities.put("ENVIRINFO", "HI");
    c_hshEntities.put("IVOCAT", "HI");
    c_hshEntities.put("ORDERINFO", "HI");
    c_hshEntities.put("ORGANUNIT", "HI");
    c_hshEntities.put("PACKAGING", "HI");
    c_hshEntities.put("PRICEFININFO", "HI");
    c_hshEntities.put("PUBLICATION", "HI");
    c_hshEntities.put("PUBTABLE", "HI");
    c_hshEntities.put("RULES", "HI");
    c_hshEntities.put("SHIPINFO", "HI");
    c_hshEntities.put("TECHCAPABILITY", "HI");
  }

  /**
     * ABR
     *
     */
    public final static String ABR = new String("COFQRABR01");

  private EntityGroup m_egParent = null; //root COMMERCIALOF
  private EntityItem m_eiParent = null; //root COMMERCIALOF

  /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
    EntityGroup m_egChild = null;
    EntityGroup m_egChild1 = null;
    StringWriter writer;
    String x = null;

    try {

      start_ABRBuild();
      m_egParent = m_elist.getParentEntityGroup();
      m_eiParent =
        (m_egParent == null ? null : m_egParent.getEntityItem(0));

      if (m_egParent == null) {
        logMessage(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:1: m_egParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      if (m_eiParent == null) {
        logMessage(
          ABR
            + ":"
            + getVersion()
            + ":ERROR:2: m_eiParent cannot be established.");
        setReturnCode(FAIL);
        return;
      }
      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Request to Work on Entity:"
          + m_eiParent.getEntityType()
          + ":"
          + m_eiParent.getEntityID());
      buildReportHeader();
      setControlBlock();
      setDGTitle(setDGName(m_eiParent, ABR));

      logMessage(
        ABR
          + ":"
          + getVersion()
          + ":Setup Complete:"
          + m_eiParent.getEntityType()
          + ":"
          + m_eiParent.getEntityID());

      // Here is the check
      displayHeader(m_egParent, m_eiParent);
      setReturnCode(PASS);

      if (!checkM0007(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0008(m_egParent, m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0001(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkM0004GrandChild(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkS0001(m_eiParent)) {
        setReturnCode(FAIL);
      }
      if (!checkS0002(m_eiParent, c_hshEntities)) {
        setReturnCode(FAIL);
      }

      /* If return code is PASS and the prior status is "Change (Ready for Review)" or "Change (Complete)"
      * then, produce a Change Report
      */
      if (getReturnCode() == PASS) {
        setFlagValue(
          m_eiParent,
          getStatusAttributeCode(m_eiParent),
          getNextStatusCode(m_eiParent));
        m_egChild = m_elist.getEntityGroup("COFCOFMGMTGRP");
        for (int i = 0; i < m_egChild.getEntityItemCount(); i++) {
          EntityItem m_eiChild = (m_egChild.getEntityItem(i));
          setFlagValue(
            m_eiChild,
            getStatusAttributeCode(m_eiChild),
            getNextStatusCode(m_eiChild));
        }

        m_egChild1 =
          m_elist.getEntityGroup("COFOOFMGMTGRP");
        for (int i = 0; i < m_egChild1.getEntityItemCount(); i++) {
          EntityItem m_eiChild1 = (m_egChild1.getEntityItem(i));
          setFlagValue(
            m_eiChild1,
            getStatusAttributeCode(m_eiChild1),
            getNextStatusCode(m_eiChild1));
        }

        // Insert the change report here
        if (isChangeRev(m_eiParent) || isChangeFinal(m_eiParent)) {
          setNow();
          println(processChangeReport(getT1(m_eiParent), getNow()));
        }
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

      writer = new StringWriter();
      exc.printStackTrace(new PrintWriter(writer));
      x = writer.toString();
      println(x);
      logMessage(
        "Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      logMessage("" + exc);
      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }
    } finally {
      //Everything is fine...so lets pass
      setDGString(getABRReturnCode());
      setDGRptName("COFQRABR01"); //Set the report name
      setDGRptClass("COFQRABR01"); //Set the report class
      printDGSubmitString();
      //Stuff into report for subscription and notification
      buildReportFooter();
      if (!isReadOnly()) {
        clearSoftLock();
      }
    }
  }

  /**
     * getABREntityDesc
     *
     * @param entityType
     * @param entityId
     * @return
     * @author Owner
     */
    protected String getABREntityDesc(String entityType, int entityId) {
    return null;
  }

  /**
     * getDescription
     *
     * @return
     * @author Owner
     */
    public String getDescription() {
    return "<br /><br />";
  }

  /**
     * getStyle
     *
     * @return
     * @author Owner
     */
    protected String getStyle() {
    return "";
  }

  /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
    return new String("1.19");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("COFQRABR01.java,v 1.19 2006/03/03 19:23:27 bala Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return getVersion();
  }

}
