//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//WWSEOALWRABR02.java,v
//Revision 1.5  2008/01/30 19:39:17  wendy
//Cleanup RSA warnings
//
//Revision 1.4  2006/03/03 19:23:30  bala
//remove reference to Constants.CSS
//
//Revision 1.3  2006/02/09 20:42:52  joan
//fixes
//
//Revision 1.2  2005/07/28 21:11:15  joan
//fixes
//
//Revision 1.1  2005/07/27 22:30:01  joan
//add new abr
//
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

/**
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class WWSEOALWRABR02 extends PokBaseABR {
  /**
  *  Execute ABR.
  *
  */

  // Class constants
  public final static String ABR = new String("WWSEOALWRABR02");

  EntityGroup m_egParent = null;
  EntityItem m_ei = null;

  public void execute_run() {
      try {
      // if it's the first time, build the report header
      StringBuffer sb = new StringBuffer();    
      start_ABRBuild();
      // Build the report header
      buildReportHeaderII();

      m_egParent = m_elist.getParentEntityGroup();
      m_ei = m_egParent.getEntityItem(0);
      println("<br><b>WWSEO ALWR: " + m_ei.getKey() + "</b>");

      printNavigateAttributes(m_ei, m_egParent, true);
      setReturnCode(PASS);

      WWSEOALWRPDG pdg = new WWSEOALWRPDG(null, m_db, m_prof, "WWSEOALWRPDG");

      pdg.setEntityItem(m_ei);
      pdg.setABReList(m_elist);
      pdg.executeAction(m_db, m_prof);
      sb = pdg.getActivities();
      println("</br></br/><b>Generated Data:</b>");
      println("<br/>" + sb.toString());
      } catch (LockPDHEntityException le) {
        setReturnCode(UPDATE_ERROR);
        println(
          "<h3><font color=red>" +
            ERR_IAB1007E +
            "<br />" +
            le.getMessage() +
            "</font></h3>");
          logError(le.getMessage());
      } catch (UpdatePDHEntityException le) {
        setReturnCode(UPDATE_ERROR);
          println(
            "<h3><font color=red>UpdatePDH error: " +
            le.getMessage() +
            "</font></h3>");
          logError(le.getMessage());
      } catch (SBRException _sbrex) {
      String strError = _sbrex.toString();
      int i = strError.indexOf("(ok)");
      if (i < 0) {
        setReturnCode(UPDATE_ERROR);
        println("<h3><font color=red>Generate Data error: " + replace(strError, "\n", "<br>") + "</font></h3>");
            logError(_sbrex.toString());
      } else {
        strError = strError.substring(0,i);
        println(replace(strError, "\n", "<br>"));
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
            "<br /><b>" +
            buildMessage(
            MSG_IAB2016I,
            new String[]{
            getABRDescription(),
            (getReturnCode() == PASS ? "Passed" : "Failed")}) +
            "</b>");

      log(
              buildLogMessage(
              MSG_IAB2016I,
              new String[]{
              getABRDescription(),
              (getReturnCode() == PASS ? "Passed" : "Failed")}));

          // set DG title
          String strDgName = m_ei.toString();
          if (strDgName.length() > 64) {
        strDgName = strDgName.substring(0,64);
      }
          setDGTitle(strDgName);
          setDGRptName(ABR);

        // set DG submit string
        setDGString(getABRReturnCode());
        printDGSubmitString();      //Stuff into report for subscription and notification

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

    while (_s.length() > 0 && iTab >=0) {
      sResult = sResult + _s.substring(0, iTab) + _s2;
      _s = _s.substring(iTab+_s1.length());
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
      return "WWSEO ALWR No CD ABR.";
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

  public String getRevision() {
      return new String("1.5");
  }

  public static String getVersion() {
      return ("WWSEOALWRABR02.java,v 1.5 2008/01/30 19:39:17 wendy Exp");
  }

  public String getABRVersion() {
      return "WWSEOALWRABR02.java";
  }
}
