//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//$Log: ECCMCATLGPUBABR05.java,v $
//Revision 1.11  2008/01/30 19:27:19  wendy
//Cleanup RSA warnings
//
//Revision 1.10  2006/06/05 18:33:52  joan
//changes
//
//Revision 1.9  2006/06/05 17:50:47  joan
//changes
//
//Revision 1.8  2006/05/19 21:00:06  joan
//fixes
//
//Revision 1.7  2006/05/19 17:29:58  joan
//fixes
//
//Revision 1.6  2006/05/19 17:25:35  joan
//changes
//
//Revision 1.5  2006/05/17 23:33:47  joan
//fixes
//
//Revision 1.4  2006/05/17 23:27:15  joan
//changes
//
//Revision 1.3  2006/05/17 23:24:13  joan
//fixes
//
//Revision 1.2  2006/05/17 23:04:33  joan
//changes
//
//Revision 1.1  2006/05/17 00:07:41  joan
//changes
//

package COM.ibm.eannounce.abr.pcd;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

/**
 * ECCMCATLGPUBABR05
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class ECCMCATLGPUBABR05 extends PokBaseABR {
    /**
    *  Execute ABR.
    *
    */

    // Class constants
    public final static String ABR = new String("ECCMCATLGPUBABR05");
    private EntityItem m_ei = null;
    private PDGUtility m_utility = new PDGUtility();
    /**
     * STARTDATE
     *
     */
    public final static String STARTDATE = "1980-01-01-00.00.00.000000";
    public final static String FOREVER = "9999-12-31-00.00.00.000000";
    /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
        DatePackage dp = null;
        String strNow = null;
        //String strCurrentDate = null;
        EntityGroup eg = null;

        String strDgName = null;
        String RETURN = System.getProperty("line.separator");
        try {
            start_ABRBuild(false);
            setReturnCode(PASS);
            buildReportHeaderII();

            dp = m_db.getDates();
            strNow = dp.getNow();
            //strCurrentDate = strNow.substring(0, 10);
            String strDate = m_utility.getDate(strNow.substring(0, 10), 15);
            eg = new EntityGroup(null, m_db, m_prof, m_abri.getEntityType(), "Edit", false);
            m_ei = new EntityItem(eg, m_prof, m_db, m_abri.getEntityType(), m_abri.getEntityID());

            println("<br><b>" + m_ei.getKey() + "</b>");
            printNavigateAttributes(m_ei, eg, true);

			String strGA = m_utility.getAttrValueDesc(m_ei, "GENAREANAME");
			String[] aOC = m_utility.getFlagCodeForExactDesc(m_db, m_prof, "OFFCOUNTRY", strGA);
			if (aOC == null || aOC.length <= 0) {
				System.out.println(getABRVersion() + " unable to find OFFCOUNTRY for desc: " + strGA);
				setCreateDGEntity(false);
				setReturnCode(FAIL);
			} else {
				String strOffCountry = aOC[0];
				if (!isCATLGCNTRYExist(m_db, m_prof, strOffCountry)) {
					setCreateDGEntity(false);
					setReturnCode(FAIL);
					System.out.println(getABRVersion() + " no CATLGCNTRY exists for " + strOffCountry);
				}
			}

			if (getReturnCode() == PASS) {

				if (m_ei.getEntityType().equals("CCTO")) {
					// check •	CCOSOLTARGANNDATE < NOW() + 15 Days
					//“logical OR” CCOSOLSTATUS = Ready for Review (0040)
					//“logical OR” CCOSOLSTATUS = Final (0020)
					String strCCOSOLTARGANNDATE = m_utility.getAttrValue(m_ei, "CCOSOLTARGANNDATE");
					String strCCOSOLSTATUS = m_utility.getAttrValue(m_ei, "CCOSOLSTATUS");
					int iDC = m_utility.dateCompare(strCCOSOLTARGANNDATE, strDate);
					if (iDC == PDGUtility.EARLIER || strCCOSOLSTATUS.equals("0040") || strCCOSOLSTATUS.equals("0020")) {
						setReturnCode(PASS);
						log("CATLGPUBABR05 generating data");
						CATCCTOPDG pdg = new CATCCTOPDG(null, m_db, m_prof, "CATCCTOPDG");
						pdg.setEntityItem(m_ei);
						pdg.executeAction(m_db, m_prof);
						log("CATLGPUBABR05 finish generating data");
					} else {
						setCreateDGEntity(false);
						setReturnCode(FAIL);
					}

				} else if (m_ei.getEntityType().equals("CSOL")) {
					//TARG_ANN_DATE_CT < NOW() + 15 Days
					//“logical OR” CSOLSTATUS = Ready for Review (0040)
					//“logical OR” CSOLSTATUS = Final (0020)

					String strTARG_ANN_DATE_CT = m_utility.getAttrValue(m_ei, "TARG_ANN_DATE_CT");
					String strCSOLSTATUS = m_utility.getAttrValue(m_ei, "CSOLSTATUS");
					int iDC = m_utility.dateCompare(strTARG_ANN_DATE_CT, strDate);
					if (iDC == PDGUtility.EARLIER || strCSOLSTATUS.equals("0040") || strCSOLSTATUS.equals("0020")) {
						setReturnCode(PASS);
						log("CATLGPUBABR05 generating data");
						CATCSOLPDG pdg = new CATCSOLPDG(null, m_db, m_prof, "CATCSOLPDG");
						pdg.setEntityItem(m_ei);
						pdg.executeAction(m_db, m_prof);
						log("CATLGPUBABR05 finish generating data");

					} else {
						setCreateDGEntity(false);
						setReturnCode(FAIL);
					}

				} else if (m_ei.getEntityType().equals("CVAR")) {
					//TARGANNDATE_CVAR < NOW() + 15 Days
					//“logical OR” STATUS_CVAR = Ready for Review (0040)
					//“logical OR” STATUS_CVAR = Final (0020)
					String strTARGANNDATE_CVAR = m_utility.getAttrValue(m_ei, "TARGANNDATE_CVAR");
					String strSTATUS_CVAR = m_utility.getAttrValue(m_ei, "STATUS_CVAR");
					int iDC = m_utility.dateCompare(strTARGANNDATE_CVAR, strDate);
					if (iDC == PDGUtility.EARLIER || strSTATUS_CVAR.equals("0040") || strSTATUS_CVAR.equals("0020")) {
						setReturnCode(PASS);
						log("CATLGPUBABR05 generating data");
						CATCVARPDG pdg = new CATCVARPDG(null, m_db, m_prof, "CATCVARPDG");
						pdg.setEntityItem(m_ei);
						pdg.executeAction(m_db, m_prof);
						log("CATLGPUBABR05 finish generating data");

					} else {
						setCreateDGEntity(false);
						setReturnCode(FAIL);
					}

				} else if (m_ei.getEntityType().equals("CB")) {
					//TARG_ANN_DATE_CB < NOW() + 15 Days
					//“logical OR” CBSOLSTATUS = Ready for Review (0040)
					//“logical OR” CBSOLSTATUS = Final (0020)
					String strTARG_ANN_DATE_CB = m_utility.getAttrValue(m_ei, "TARG_ANN_DATE_CB");
					String strCBSOLSTATUS = m_utility.getAttrValue(m_ei, "CBSOLSTATUS");
					int iDC = m_utility.dateCompare(strTARG_ANN_DATE_CB, strDate);
					if (iDC == PDGUtility.EARLIER || strCBSOLSTATUS.equals("0040") || strCBSOLSTATUS.equals("0020")) {
						setReturnCode(PASS);
						log("CATLGPUBABR05 generating data");
						CATCBPDG pdg = new CATCBPDG(null, m_db, m_prof, "CATCBPDG");
						pdg.setEntityItem(m_ei);
						pdg.executeAction(m_db, m_prof);
						log("CATLGPUBABR05 finish generating data");
					} else {
						setCreateDGEntity(false);
						setReturnCode(FAIL);
					}
				}
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

			log(buildLogMessage(
			  MSG_IAB2016I,
			  new String[] {
				getABRDescription(),
				(getReturnCode() == PASS ? "Passed" : "Failed")}));

		  // set DG title
			strDgName = getABRDescription()
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

   private boolean isCATLGCNTRYExist(Database _db, Profile _prof, String _strOffCountry) {
	   try {
			StringBuffer sb = new StringBuffer();
			sb.append("map_OFFCOUNTRY=" +  _strOffCountry);

			String strSai =  new String("SRDCATLGCNTRY01");
			EntityItem[] aeiCATLGCNTRY = m_utility.dynaSearch(_db, _prof, null, strSai, "CATLGCNTRY", sb.toString());
			if (aeiCATLGCNTRY == null || aeiCATLGCNTRY.length <= 0) {
				return false;
			} else {
				return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
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
    return "Catalog Offering Publication For CSOL ABR";
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
    return new String("$Revision: 1.11 $");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("$Id: ECCMCATLGPUBABR05.java,v 1.11 2008/01/30 19:27:19 wendy Exp $");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "ECCMCATLGPUBABR05.java";
  }
}
