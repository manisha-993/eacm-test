//  (c) Copyright International Business Machines Corporation, 2001
//  All Rights Reserved.</pre>
//
//$Log: ECCMCATLGPUBABR04.java,v $
//Revision 1.14  2008/01/30 19:27:19  wendy
//Cleanup RSA warnings
//
//Revision 1.13  2006/06/06 15:34:17  joan
//fixes
//
//Revision 1.12  2006/06/06 15:07:50  joan
//fixes
//
//Revision 1.11  2006/06/05 17:50:47  joan
//changes
//
//Revision 1.10  2006/05/22 16:59:13  joan
//changes
//
//Revision 1.9  2006/05/22 16:40:42  joan
//fixes
//
//Revision 1.8  2006/05/22 15:39:26  joan
//add method
//
//Revision 1.7  2006/05/21 20:36:51  joan
//changes
//
//Revision 1.6  2006/05/18 00:09:27  joan
//change
//
//Revision 1.5  2006/05/17 22:04:03  joan
//changes
//
//Revision 1.4  2006/05/17 00:07:41  joan
//changes
//
//Revision 1.3  2006/04/24 22:35:01  joan
//fixes
//
//Revision 1.2  2006/04/20 17:56:14  joan
//changes
//
//Revision 1.1  2006/04/20 17:53:35  joan
//change directory
//

package COM.ibm.eannounce.abr.pcd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.*;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;

/**
 * ECCMCATLGPUBABR04
 *
 *@author     Administrator
 *@created    August 30, 2002
 */
public class ECCMCATLGPUBABR04 extends PokBaseABR {
    /**
    *  Execute ABR.
    *
    */

    // Class constants
    public final static String ABR = new String("ECCMCATLGPUBABR04");
    private final static String ATT_LASTRUN = new String("CATLGCBPUBLASTRUN");
    private final static String ATT_OFFCOUNTRY = new String("OFFCOUNTRY");
    private final static String QUEUE = new String("ECCMCATLGPUBABR08");
    private PDGUtility m_utility = new PDGUtility();
    private EntityItem m_ei = null;
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
       // String strCurrentDate = null;
        EntityGroup eg = null;
        String strOFFCOUNTRY = null;
        String strDgName = null;
        String strLASTRUN = null;
        String RETURN = System.getProperty("line.separator");
        try {
            start_ABRBuild(false);
            setReturnCode(PASS);
            buildReportHeaderII();

            dp = m_db.getDates();
            strNow = dp.getNow();
            //strCurrentDate = strNow.substring(0, 10);
            eg = new EntityGroup(null, m_db, m_prof, m_abri.getEntityType(), "Edit", false);
            m_ei = new EntityItem(eg, m_prof, m_db, m_abri.getEntityType(), m_abri.getEntityID());

            println("<br><b>Catalog Country: " + m_ei.getKey() + "</b>");
            printNavigateAttributes(m_ei, eg, true);

            // check CATLGCNTRY attributes
            strLASTRUN = m_utility.getAttrValue(m_ei, ATT_LASTRUN);
            if (strLASTRUN == null || strLASTRUN.length() <= 0) {
                OPICMList attList = new OPICMList();
                attList.put(ATT_LASTRUN,ATT_LASTRUN + "=" + STARTDATE);
                m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
            } else {
                if (!m_utility.isDateFormat(strLASTRUN)) {
                    println(ATT_LASTRUN + " is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000");
                    setReturnCode(FAIL);
                } else {
                if (strLASTRUN.length() == 10) {
                    OPICMList attList = new OPICMList();
                    attList.put(ATT_LASTRUN, ATT_LASTRUN + "=" + strLASTRUN + "-00.00.00.000000");
                    m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
                }
            }
        }
		m_prof = m_utility.setProfValOnEffOn(m_db, m_prof);
		m_ei = new EntityItem(eg, m_prof, m_db, m_abri.getEntityType(), m_abri.getEntityID());
        strOFFCOUNTRY = m_utility.getAttrValue(m_ei, "OFFCOUNTRY");
        if (strOFFCOUNTRY == null || strOFFCOUNTRY.length() <= 0) {
            println("OFFCOUNTRY is blank.");
            setReturnCode(FAIL);
        }

		String strOFFCOUNTRY1 = m_utility.getAttrValueDesc(m_ei, ATT_OFFCOUNTRY);

		String[] aGA = m_utility.getFlagCodeForExactDesc(m_db, m_prof, "GENAREANAME", strOFFCOUNTRY1);
		if (aGA == null || aGA.length <= 0) {
			D.ebug(D.EBUG_SPEW,getABRVersion() + " unable to find GENAREANAME for desc: " + strOFFCOUNTRY1);
            println("Unable to find GENAREANAME for desc: " + strOFFCOUNTRY1);
            setReturnCode(FAIL);
		}

        if (getReturnCode() == PASS) {
			String strGA = aGA[0];

	       	String[] aeiCB = getCBs(m_db, m_prof, strGA);

			for (int i=0; i < aeiCB.length; i++) {
				String strEI = aeiCB[i];
				StringTokenizer st = new StringTokenizer(strEI, ":");
				if (st.countTokens() == 2) {
					String strEntityType = st.nextToken();
					int iEntityID = Integer.parseInt(st.nextToken().trim());
					m_utility.queueEI(m_db, m_prof, strEntityType, iEntityID, QUEUE);
				}
			}
			removeCATCB (m_db, m_prof, "CATCB");
            OPICMList attList = new OPICMList();
            attList.put(ATT_LASTRUN,ATT_LASTRUN + "=" + strNow);
            m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
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

	private String[] getCBs(Database _db, Profile _prof, String _strGA) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		// Initialize some SP specific objects needed in this method
		String strTraceBase = " ECCMCATLGPUBPDG getCBs method";
		ResultSet rs = null;

		// Pull some profile info...
		String strEnterprise = _prof.getEnterprise();

		DatePackage dpNow = _db.getDates();
		String strNow = dpNow.getNow();
		Hashtable returnList = new Hashtable();
		Vector vReturn = new Vector();
		String[] aeiReturn = null;


		/*
		CB was last updated since the last run for this CATLGCNTRY.CATLGCBPUBLASTRUN
		TARG_ANN_DATE_CB < NOW() + 15 Days “logical OR” CBSOLSTATUS = Ready for Review (0040)
		logical OR” CBSOLSTATUS = Final (0020)

		*/

		String strSQL1 =
			"SELECT " +
			" F.EntityType  as EntityType " +
			",F.EntityID  as EntityID " +
			"FROM opicm.FLAG F " +
			"INNER JOIN opicm.Entity E ON " +
			"    E.Enterprise= ? " +
			"AND E.EntityType = F.EntityType " +
			"AND E.EntityID = F.entityID " +
			"AND E.ValFrom >= ? " +
			"AND E.ValTo > current timestamp " +
			"AND E.EffFrom >= ? " +
			"AND E.EffTo > current timestamp " +
			"WHERE F.Enterprise = ? " +
			"AND F.EntityType = 'CB' " +
			"AND F.AttributeCode = 'GENAREANAME' " +
			"AND F.AttributeValue = ? " +
			"AND F.ValFrom <= current timestamp AND  current timestamp < F.ValTo " +
			"AND F.EffFrom <= current timestamp AND current timestamp < F.EffTo " ;

		PreparedStatement ps = null;

		Connection con = _db.getPDHConnection();
		String strDate = m_utility.getDate(strNow.substring(0, 10), 15);
		String strLastRun = m_utility.getAttrValue(m_ei, "CATLGCBPUBLASTRUN");
        try {
			D.ebug(D.EBUG_DETAIL, strTraceBase + " setting:" + strEnterprise + ":" + strDate + ":" + _strGA + ":" + strLastRun);
			D.ebug(D.EBUG_DETAIL, strTraceBase + " strSQL1:" + strSQL1);
            ps = con.prepareStatement(strSQL1);
            //MyETs
            ps.setString(1,strEnterprise);
            ps.setString(2,strLastRun);
            ps.setString(3,strLastRun);
            ps.setString(4,strEnterprise);
			ps.setString(5,_strGA);

            rs = ps.executeQuery();

			D.ebug(D.EBUG_DETAIL, strTraceBase + " executed SQL1.");

			while(rs.next()) {
				String strEntityType = rs.getString(1).trim();
				int iEntityID = rs.getInt(2);
				//D.ebug(D.EBUG_DETAIL,strTraceBase + " answer 1: " + strEntityType  + ":" + iEntityID + ":");
				String strEIKey = strEntityType + ":" + iEntityID;
				if (returnList.get(strEIKey) == null) {
					returnList.put(strEIKey, strEIKey);
					vReturn.addElement(strEIKey);
				}
			}
			D.ebug(D.EBUG_DETAIL, strTraceBase + " vReturn 1 size: " + vReturn.size());

		} finally {
			if(rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			_db.commit();
		    _db.freeStatement();
		    _db.isPending();

		}

		aeiReturn = new String[vReturn.size()];
		vReturn.toArray(aeiReturn);
		return aeiReturn;
	}

	private void removeCATCB (Database _db, Profile _prof, String _strEntityType) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
		/*
		Instances of CATCB should be removed whenever the instance has:
		not been updated for the past 30 days
		and PUBTO < NOW() + 30 days
		*/
		String strTraceBase = "ECCMCATLGPUBPDG removeCATCB method " + _strEntityType;
		DatePackage dp = _db.getDates();
		String strNow = dp.getNow();
		String strCurrentDate = strNow.substring(0, 10);

		StringBuffer sb = new StringBuffer();
		String strOFFCOUNTRY1 = m_utility.getAttrValueDesc(m_ei, ATT_OFFCOUNTRY);

		String[] aGA = m_utility.getFlagCodeForExactDesc(_db, _prof, "GENAREANAME", strOFFCOUNTRY1);
		if (aGA == null || aGA.length <= 0) {
			D.ebug(D.EBUG_SPEW,strTraceBase + " unable to find GENAREANAME for desc: " + strOFFCOUNTRY1);
			return;
		}

		String strGA = aGA[0];

		sb.append("map_GENAREANAME=" +  strGA);
        String strSai =  "SRDCATCB1";
		EntityItem[] aeiCAT = m_utility.dynaSearch(_db, _prof, null, strSai, _strEntityType, sb.toString());
		if (aeiCAT != null) {
			for (int i=0; i < aeiCAT.length; i++) {
				EntityItem eiCAT = aeiCAT[i];

				String strPT = m_utility.getAttrValue(eiCAT, "PUBTO");
				if (strPT.length() > 0) {
					int iDCPT = m_utility.dateCompare(strPT, m_utility.getDate(strCurrentDate, 30));
					if (iDCPT == PDGUtility.EARLIER) {
						_prof = m_utility.setProfValOnEffOn(_db, _prof);
						// check for last update
						EntityChangeHistoryGroup echg = new EntityChangeHistoryGroup(_db,_prof, eiCAT);
						EntityChangeHistoryItem echi = (EntityChangeHistoryItem)m_utility.getCurrentChangeItem(echg);
						String strChangeDate = echi.getChangeDate();

						D.ebug(D.EBUG_SPEW,strTraceBase + " checking last update CAT: " + eiCAT.getKey());
						int iDate1 = m_utility.dateCompare(m_utility.getDate(strChangeDate.substring(0,10), 30), strCurrentDate);
						if (iDate1 == PDGUtility.EARLIER) {
							D.ebug(D.EBUG_SPEW,strTraceBase + " deactivate : " + eiCAT.getKey());
							_prof = m_utility.setProfValOnEffOn(_db, _prof);
							EANUtility.deactivateEntity(_db, _prof, eiCAT);

						}
					}
				}
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
    return "Catalog Offering Publication For CB ABR";
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
    return new String("$Revision: 1.14 $");
  }

  /**
     * getVersion
     *
     * @return
     * @author Owner
     */
    public static String getVersion() {
    return ("$Id: ECCMCATLGPUBABR04.java,v 1.14 2008/01/30 19:27:19 wendy Exp $");
  }

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    return "ECCMCATLGPUBABR04.java";
  }
}
