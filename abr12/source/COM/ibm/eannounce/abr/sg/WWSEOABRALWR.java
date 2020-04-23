//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//WWSEOABRALWR.java,v
//Revision 1.5  2006/03/08 17:12:17  joan
//fixes
//
//Revision 1.4  2006/03/03 19:23:30  bala
//remove reference to Constants.CSS
//
//Revision 1.3  2006/02/20 17:43:09  joan
//change System.out to D.ebug
//
//Revision 1.2  2006/02/09 20:42:51  joan
//fixes
//
//Revision 1.1  2006/02/07 23:58:06  joan
//add new abr
//
//Revision 1.3  2006/01/24 17:10:54  yang
//Jtest Changes
//
//Revision 1.2  2005/07/28 21:11:15  joan
//fixes
//
//Revision 1.1  2005/07/27 22:30:01  joan
//add new abr
//
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
 * WWSEOABRALWR
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class WWSEOABRALWR extends PokBaseABR {
    /**
    *  Execute ABR.
    *
    */

    // Class constants
    public final static String ABR = new String("WWSEOABRALWR");
    public final static String CDG_EG = new String("CDG");
    public final static String CDENTITY_EG = new String("CDENTITY");
    public final static String ATT_XXPARTNO = new String("XXPARTNO");

    private EntityGroup m_egParent = null;
    private EntityItem m_ei = null;

    /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
        WWSEOABRALWRPDG pdg;
        StringBuffer sb = new StringBuffer();;
        PDGUtility utility = new PDGUtility();;
        String strDgName = null;
        EntityGroup egCDG = null;
        EntityGroup egCDENTITY = null;
        String RETURN = System.getProperty("line.separator");
        try {
            start_ABRBuild();
            // Build the report header
            buildReportHeaderII();

            m_egParent = m_elist.getParentEntityGroup();
            m_ei = m_egParent.getEntityItem(0);
            println("<br><b>WWSEO: " + m_ei.getKey() + "</b>");

            printNavigateAttributes(m_ei, m_egParent, true);
            setReturnCode(PASS);
            //System.out.println(ABR + "m_elist : " +  m_elist.dump(false));

		    String strXXPARTNO = utility.getAttrValue(m_ei, ATT_XXPARTNO);
		    if (strXXPARTNO == null || strXXPARTNO.length() <= 0) {
				setReturnCode(FAIL);
				println("<br /><font color=red>Failed. XXPARTNO is blank.</font>");
			}

            egCDG = m_elist.getEntityGroup(CDG_EG);
            if (egCDG != null) {
                if (egCDG.getEntityItemCount() > 1) {
                    setReturnCode(FAIL);
                    println("<br /><font color=red>Failed. There are more than one CDGs linked to WWSEO.</font>");
                    println("<br/></br/><b>Country Designator Group(s):</b>");
                    for (int i = 0; i < egCDG.getEntityItemCount(); i++) {
                        EntityItem ei = egCDG.getEntityItem(i);
                        println("</br/><LI> " + ei.toString());
                    }
                } else if (egCDG.getEntityItemCount() <= 0) {
                    setReturnCode(FAIL);
                    println("<br /><font color=red>Failed. There are no CDG linked to WWSEO.</font>");
                }
            } else {
                println("EntityGroup CDG is null\n");
                setReturnCode(FAIL);
            }

            egCDENTITY = m_elist.getEntityGroup(CDENTITY_EG);
            if (egCDENTITY != null) {
                if (egCDENTITY.getEntityItemCount() <= 0) {
                    setReturnCode(FAIL);
                    println("<br /><font color=red>Failed. There are no CDENTITYs.</font>");
                }
            } else {
                println("EntityGroup CDENTITY is null\n");
                setReturnCode(FAIL);
            }

            if (getReturnCode() == PASS) {
                pdg = new WWSEOABRALWRPDG(null, m_db, m_prof, "WWSEOABRALWRPDG");
                pdg.setEntityItem(m_ei);
                pdg.setABReList(m_elist);
                pdg.executeAction(m_db, m_prof);
                sb = pdg.getActivities();
                println("</br></br/><b>Generated Data:</b>");
                println("<br/>" + sb.toString());
            }
        } catch (LockPDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println( "<h3><font color=red>" + ERR_IAB1007E + "<br />" + le.getMessage() + "</font></h3>");
            logError(le.getMessage());
        } catch (UpdatePDHEntityException le) {
            setReturnCode(UPDATE_ERROR);
            println( "<h3><font color=red>UpdatePDH error: " + le.getMessage() + "</font></h3>");
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
            println("<br /><b>" + buildMessage(MSG_IAB2016I,
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
    return "WWSEO ALWR With CD ABR.";
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
    return new String("1.5");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("WWSEOABRALWR.java,v 1.5 2006/03/08 17:12:17 joan Exp");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "WWSEOABRALWR.java";
  }
}
