//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//MODELABRSS.java,v
//Revision 1.19  2008/01/30 19:39:15  wendy
//Cleanup RSA warnings
//
//Revision 1.18  2007/02/07 17:49:35  yang
//minor change to log file
//
//Revision 1.15  2006/06/27 23:15:04  yang
//more syntax
//
//Revision 1.14  2006/06/27 22:58:03  yang
//minor fix
//
//Revision 1.13  2006/06/27 18:03:48  yang
//syntax
//
//Revision 1.12  2006/06/27 17:38:43  yang
//remove the output file on the file system
//
//Revision 1.11  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.10  2006/01/24 16:47:39  yang
//Jtest Changes
//
//Revision 1.9  2005/11/21 21:43:56  yang
//minor changes to logs
//
//Revision 1.8  2005/10/27 21:02:59  yang
//changed attribute names per wayne's request
//
//Revision 1.7  2005/10/26 20:33:29  yang
//we are using extract from given root entity not from properties file
//
//Revision 1.6  2005/09/28 22:45:11  yang
//syntax
//
//Revision 1.5  2005/09/08 22:48:26  joan
//fixes
//
//Revision 1.4  2005/09/08 22:29:53  yang
//changed GernerateXL
//
//Revision 1.3  2005/09/07 22:10:36  joan
//fixes
//
//Revision 1.2  2005/09/07 19:54:13  yang
//initial load
//

//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//MODELABRSS.java,v
//Revision 1.19  2008/01/30 19:39:15  wendy
//Cleanup RSA warnings
//
//Revision 1.18  2007/02/07 17:49:35  yang
//minor change to log file
//
//Revision 1.15  2006/06/27 23:15:04  yang
//more syntax
//
//Revision 1.14  2006/06/27 22:58:03  yang
//minor fix
//
//Revision 1.13  2006/06/27 18:03:48  yang
//syntax
//
//Revision 1.12  2006/06/27 17:38:43  yang
//remove the output file on the file system
//
//Revision 1.11  2006/03/03 19:23:29  bala
//remove reference to Constants.CSS
//
//Revision 1.10  2006/01/24 16:47:39  yang
//Jtest Changes
//
//Revision 1.9  2005/11/21 21:43:56  yang
//minor changes to logs
//
//Revision 1.8  2005/10/27 21:02:59  yang
//changed attribute names per wayne's request
//
//Revision 1.7  2005/10/26 20:33:29  yang
//we are using extract from given root entity not from properties file
//
//Revision 1.6  2005/09/28 22:45:11  yang
//syntax
//
//Revision 1.5  2005/09/08 22:48:26  joan
//fixes
//
//Revision 1.4  2005/09/08 22:29:53  yang
//changed GernerateXL
//
//Revision 1.2  2005/09/07 19:54:13  yang
//initial load
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.objects.*;
//import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileWriter;
//import java.util.ArrayList;
//import java.util.List;

//import org.apache.poi.ddf.*;
//import org.apache.poi.dev.*;
//import org.apache.poi.hpsf.*;
//import org.apache.poi.util.*;
//import org.apache.poi.hssf.dev.*;
//import org.apache.poi.hssf.eventmodel.*;
//import org.apache.poi.hssf.eventusermodel.*;
//import org.apache.poi.hssf.model.*;
//import org.apache.poi.hssf.record.*;
//import org.apache.poi.hssf.usermodel.*;
//import org.apache.poi.hssf.util.*;
//import org.apache.poi.poifs.common.*;
//import org.apache.poi.poifs.dev.*;
//import org.apache.poi.poifs.eventfilesystem.*;
//import org.apache.poi.poifs.filesystem.*;
//import org.apache.poi.poifs.property.*;
//import org.apache.poi.poifs.storage.*;

/**
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 *
 * @author Owner
 */
public class MODELABRSS extends PokBaseABR {
  /**
  *  Execute ABR.
  */

  //ABR
  public final static String ABR = new String("MODELABRSS");

  //AVAIL Attributes must exist:
  /**
   * EFFECTIVEDATE
   *
   */
  public final static String EFFECTIVEDATE = new String("EFFECTIVEDATE");
  /**
   * AVAILTYPE
   *
   */
  public final static String AVAILTYPE = new String("AVAILTYPE");
  /**
   * GENAREASELECTION
   *
   */
  public final static String GENAREASELECTION =
    new String("GENAREASELECTION");

  //CMPNT Attributes must exist:
  /**
   * MKTGNAME
   *
   */
  public final static String MKTGNAME = new String("MKTGNAME");
  /**
   * COMPONENTID
   *
   */
  public final static String COMPONENTID = new String("COMPONENTID");
  /**
   * DESCRIPTION
   *
   */
  public final static String DESCRIPTION = new String("DESCRIPTION");
  /**
   * OVERVIEWABSTRACT
   *
   */
  public final static String OVERVIEWABSTRACT =
    new String("OVERVIEWABSTRACT");
  /**
   * DISTRCODE
   *
   */
  public final static String DISTRCODE = new String("DISTRCODE");
  /**
   * SVCCAT
   *
   */
  public final static String SVCCAT = new String("SVCCAT");
  /**
   * VAE
   *
   */
  public final static String VAE = new String("VAE");

  private EntityGroup m_egParent = null; //root MODEL
  private EntityItem m_eiParent = null; //root MODEL

  //private final String m_strParentAnnCodeNameValue = null;
  //MODEL     Value of Parent MODEL

  /**
   * execute_run
   *
   * @author Owner
   */
  public void execute_run() {
    //DatePackage dbNow = null;
    //String strNow = null;
    EntityGroup eg = null;
    EntityItem ei = null;
    EntityItem[] aei;
    ExtractActionItem eaItem = null;
    EntityList elist = null;
    GenerateXL xl;
    String strRPTEXCELVE = null;

    try {

      start_ABRBuild(false);
      setReturnCode(PASS);
      //dbNow = m_db.getDates();
     // strNow = dbNow.getNow();

      logMessage(
        "My root entitytype and entityid: "
          + m_abri.getEntityType()
          + m_abri.getEntityID());
      eg =
        new EntityGroup(
          null,
          m_db,
          m_prof,
          m_abri.getEntityType(),
          "Edit");
      logMessage(
        "stuff needed to build the ei********************"
          + eg
          + m_prof
          + m_db);
      ei =
        new EntityItem(
          eg,
          m_prof,
          m_db,
          m_abri.getEntityType(),
          m_abri.getEntityID());
      logMessage("start ei Dump********************");
      logMessage(ei.dump(false));
      logMessage("end ei Dump********************");

      if (ei != null) {
        EANAttribute[] atts = new EANAttribute[1];
        atts[0] = ei.getAttribute("RPTEXCELVE");
        logMessage("atts[0]:" + atts[0]);

        if (atts[0] == null) {
          logMessage("<br>Your RPTEXCELVE attribute is empty");
          setReturnCode(FAIL);
        } else {
          strRPTEXCELVE = atts[0].toString();

          aei = new EntityItem[] { ei };

          eaItem =
            new ExtractActionItem(
              null,
              m_db,
              m_prof,
              strRPTEXCELVE);
          elist =
            new EntityList(
              m_db,
              m_prof,
              eaItem,
              aei,
              m_abri.getEntityType());
          logMessage("elist VE Dump********************");
          logMessage(elist.dump(true));
          logMessage("End elist VE Dump********************");

          m_egParent = elist.getParentEntityGroup();
          m_eiParent = m_egParent.getEntityItem(0);

          logMessage(
            ABR
              + ":"
              + getVersion()
              + ":Request to Work on Entity:"
              + m_eiParent.getEntityType()
              + ":"
              + m_eiParent.getEntityID());

          xl =
            new GenerateXL(
              elist,
              m_eiParent,
              ABR
              + "-"
              + m_eiParent.getEntityType()
              + "-"
              + m_eiParent.getEntityID(),
              m_db,
              m_prof,
              "SRDXLENTITY",
              strRPTEXCELVE,
              true);
          setAttachByteForDG(xl.getWBBytes());
//          logMessage("xl: " + xl);
//          str = xl.toString();
//          logMessage("str" + str);
//          logMessage("print xl sheet: " + str);
//          logMessage(xl.toString());

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
        }
      }

      buildReportHeader();
      setControlBlock();
      setDGTitle(setDGName(m_eiParent, ABR));

      displayHeader(m_egParent, m_eiParent);

      log(
        buildLogMessage(
          MSG_IAB2016I,
          new String[] {
            getABRDescription(),
            (getReturnCode() == PASS ? "Passed" : "Failed")}));

    } catch (LockPDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      logMessage(
        "<h3>"
          + ERR_IAB1007E
          + "<br />"
          + le.getMessage()
          + "</font></h3>");
      logError(le.getMessage());
    } catch (UpdatePDHEntityException le) {
      setReturnCode(UPDATE_ERROR);
      logMessage(
        "<h3>UpdatePDH error: " + le.getMessage() + "</font></h3>");
      logError(le.getMessage());
    } catch (Exception exc) {
      // Report this error to both the datbase log and the PrintWriter
      logMessage(
        "Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      logMessage("" + exc);

      // don't overwrite an update exception
      if (getABRReturnCode() != UPDATE_ERROR) {
        setReturnCode(INTERNAL_ERROR);
      }

      exc.printStackTrace();

//      writer = new StringWriter();
//      exc.printStackTrace(new PrintWriter(writer));
//      x = writer.toString();
//      logMessage("stacktrace() x" + x);

      logMessage(
        "Error in " + m_abri.getABRCode() + ":" + exc.getMessage());
      logMessage("" + exc);

    } finally {
      //Everything is fine...so lets pass
      if (getReturnCode() == PASS) {
        setReturnCode(PASS);
        // set DG submit string
        setDGString(getABRReturnCode());
        setDGRptName("MODELABRSS"); //Set the report name
        setDGRptClass("MODELABRSS"); //Set the report class
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
  }

  /**
   * printFAILmessage
   *
   * @param _eg
   * @author Owner
   */
  public void printFAILmessage(EntityGroup _eg) {
    logMessage(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " is not at final status.</b>");
    setReturnCode(FAIL);

  }
  /**
   * printWARNINGmessage
   *
   * @param _eg
   * @author Owner
   */
  public void printWARNINGmessage(EntityGroup _eg) {
    logMessage(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " does not exist (Warning - Pass)</b>");

  }

  /**
   * printFAILmessage_2
   *
   * @param _eg
   * @author Owner
   */
  public void printFAILmessage_2(EntityGroup _eg) {
    logMessage(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status</b>");
    setReturnCode(FAIL);

  }
  /**
   * printWARNINGmessage_2
   *
   * @param _eg
   * @author Owner
   */
  public void printWARNINGmessage_2(EntityGroup _eg) {
    logMessage(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be 'Final'status (Warning - Pass)</b>");

  }

  /**
   * printFAILmessage_3
   *
   * @param _eg
   * @author Owner
   */
  public void printFAILmessage_3(EntityGroup _eg) {
    logMessage(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review'</b>");
    setReturnCode(FAIL);

  }
  /**
   * printWARNINGmessage_3
   *
   * @param _eg
   * @author Owner
   */
  public void printWARNINGmessage_3(EntityGroup _eg) {
    logMessage(
      "<br><br><b>"
        + _eg.getLongDescription()
        + " needs to be at least 'Ready for Final Review' (Warning - Pass)</b>");

  }

  /**
   * checkMODELstatus
   *
   * @param column
   */

  public void checkMODELstatus() {
    m_egParent = m_elist.getParentEntityGroup();
    m_eiParent = m_egParent.getEntityItem(0);

    if (m_egParent == null) {
      logMessage("********** 1 Model Not found");
      setReturnCode(FAIL);
    } else {
      logMessage(m_eiParent.dump(false));
      for (int i = 0; i < m_egParent.getEntityItemCount(); i++) {
        //logMessage("m_egParent.getEntityItem(i):" + m_egParent.getEntityItem(i));
        EntityItem ei = m_egParent.getEntityItem(i);

        logMessage(
          "m_egParent.getEntityItem(i) for Model:"
            + m_egParent.getEntityItem(i));
        logMessage("ei for Model:" + ei.dump(true));

      }
    }
  } //checkMODELstatus

  /**
   * This is a generic display header - This will now include status info as well.
   *
   * @param _eg
   * @param _ei
   */
  public void displayHeader(EntityGroup _eg, EntityItem _ei) {
    if (_eg != null && _eg != null) {
      logMessage(
        "<FONT SIZE=6><b>"
          + _eg.getLongDescription()
          + "</b></FONT><br>");
      //          logMessage(displayStatuses(_ei, _eg));
      logMessage(displayNavAttributes(_ei, _eg));
    }
  }

  /**
   *  Sets the controlBlock attribute of the MODELABRSS object
   */
  public void setControlBlock() {
    m_strNow = getNow();
    m_strForever = getForever();
    m_cbOn =
      new ControlBlock(
        m_strNow,
        m_strForever,
        m_strNow,
        m_strForever,
        m_prof.getOPWGID(),
        m_prof.getTranID());
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
    //return Constants.IAB3053I + "<br />" + Constants.IAB3050I;
    return "<b>Model Missing Data and Forms Report</b>";
  }

  /**
   * getStyle
   *
   * @return
   * @author Owner
   */
  protected String getStyle() {
    // Print out the PSG stylesheet
    return "";
  }

  /**
   *  Get the getRevision
   *
   *@return    java.lang.String
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
    return ("MODELABRSS.java,v 1.19 2008/01/30 19:39:15 wendy Exp");
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
