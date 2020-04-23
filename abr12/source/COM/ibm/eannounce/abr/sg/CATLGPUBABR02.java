// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//CATLGPUBABR02.java,v
//Revision 1.28  2008/09/09 12:52:08  wendy
//Cleanup RSA warnings
//
//Revision 1.27  2008/05/29 21:13:21  wendy
//MN35543346 - dont update custimize in this abr
//
//Revision 1.26  2008/01/04 16:45:32  wendy
//MN33416775 handling of salesstatus chgs
//
//Revision 1.25  2007/05/04 13:30:54  wendy
//RQ022507373 and MN31580435 updates
//
//Revision 1.24  2007/02/12 19:13:57  wendy
//Changed no SS found from fail to pass
//
//Revision 1.23  2006/12/18 14:19:35  wendy
//MN30141434, 30208266, 30237329, 30173561, 30262285
//default a null PUBTO to '9999-12-31'
//always update CATLGPUBs, just report errors
//only do error chk3 for LSEO with ss='ZJ' and after all other ss have been checked
//use country filter too to find LSEOBUNDLE for LSEO
//handle ss for SWMODEL as well as MODEL
//use first 4 chars from MATNR in search, don't find flag code
//AHE and jtest modifications
//
//Revision 1.22  2006/09/26 18:42:06  joan
//working on catlgpub
//
//Revision 1.21  2006/09/26 01:29:35  joan
//fixes
//
//Revision 1.20  2006/09/26 01:23:55  joan
//fixes
//
//Revision 1.19  2006/09/26 00:55:44  joan
//add catupdatedby
//
//Revision 1.18  2006/08/30 19:37:41  joan
//changes
//
//Revision 1.17  2006/08/24 00:31:41  joan
//changes
//
//Revision 1.16  2006/08/18 22:28:38  joan
//fixes
//
//Revision 1.15  2006/08/09 21:27:39  joan
//fixes
//
//Revision 1.14  2006/08/09 20:47:11  joan
//fixes
//
//Revision 1.13  2006/03/10 18:20:32  joan
//fixes
//
//Revision 1.12  2006/03/09 17:43:54  joan
//fixes
//
//Revision 1.11  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.10  2006/01/24 17:00:32  yang
//Jtest Changes
//
//Revision 1.9  2005/12/23 18:49:59  joan
//fixes
//
//Revision 1.8  2005/12/07 21:01:43  joan
//fixes
//
//Revision 1.7  2005/11/16 17:29:55  joan
//fixes
//
//Revision 1.6  2005/11/14 18:43:58  joan
//fixes
//
//Revision 1.5  2005/11/02 18:21:10  joan
//fixes
//
//Revision 1.4  2005/11/02 00:44:09  joan
//fixes
//
//Revision 1.3  2005/11/01 17:22:21  joan
//fixes
//
//Revision 1.2  2005/10/31 23:54:05  joan
//fixes
//
//Revision 1.1  2005/10/31 22:35:53  joan
//add abr
//
//Revision 1.1  2005/10/18 22:06:52  joan
//add new abr
//
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import java.sql.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.text.*;

/**
 * CATLGPUBABR02 for salesstatus checks
 *
 *@author     Administrator
 *@created    August 30, 2005
 */
public class CATLGPUBABR02 extends PokBaseABR {
	private final static String STARTDATE = "1980-01-01-00.00.00.000000";
	private final static String FOREVER = "9999-12-31-00.00.00.000000";

    // Class constants
    private final static String ABR = "CATLGPUBABR02";

    //private static final String ATT_CATCUSTIMIZE = "CATCUSTIMIZE";
    private static final String ATT_CATSEOID = "CATSEOID";
    private static final String ATT_CATADDTOCART = "CATADDTOCART";
    private static final String ATT_CATBUYABLE = "CATBUYABLE";
    private static final String ATT_CATHIDE = "CATHIDE";
    private static final String ATT_CATSALESSTATUS = "CATSALESSTATUS";
    private static final String ATT_CATOFFTYPE = "CATOFFTYPE";
    private static final String ATT_CATMACHTYPE = "CATMACHTYPE";
    private static final String ATT_CATMODEL = "CATMODEL";

    private static final String ATT_COFCAT = "COFCAT";

	private static final String HW = "100";
	private static final String SW = "101";
	private static final String SVC = "102";

    private static final String ATT_PT = "PUBTO";
    private static final String ATT_SEOID = "SEOID";
    private static final String ATT_CATWORKFLOW = "CATWORKFLOW";
    private static final String ATT_CATLGOFFPUBLASTRUN = "CATLGOFFPUBLASTRUN";

    private static final String ATT_OFFCOUNTRY = "OFFCOUNTRY";

    private static final int I64=64;

    private PDGUtility m_utility = new PDGUtility();
    private String strUpdatedBy=null;
    private String strOFFCOUNTRY = null;
    private String strCurrentDate = null;
    private String strCATLGOFFPUBLASTRUN = null;
    private int updateCount = 0;
    private boolean outputWarning = false;
	private static final String SS_EXTRACTNAME = "EXCATLGPUBVE";
	private Hashtable sskeyTbl = new Hashtable();// hang onto keys to ss, prevent getting list over and over

    /**
     * execute_run
     *
     * @author Owner
     */
    public void execute_run() {
        try {
        	EntityGroup eg = null;
        	EntityItem m_ei = null;
        	String strNow = null;
			OPICMList attList;
        	String strOCDesc = null;

            start_ABRBuild(false);
            println(EACustom.getDocTypeHtml()); // Output the doctype and html

            println("<head>"+EACustom.NEWLINE+
	            EACustom.getMetaTags(getDescription())+EACustom.NEWLINE+
                EACustom.getCSS()+EACustom.NEWLINE+
            	EACustom.getTitle(getDescription())+EACustom.NEWLINE+
                "</head>"+EACustom.NEWLINE+
                "<body id=\"ibm-com\">");

            println(EACustom.getMastheadDiv());

            setReturnCode(PASS);

            strNow = m_db.getDates().getNow();
            strCurrentDate = strNow.substring(0, 10);
            strUpdatedBy = ABR + getRevision() + strNow;

			// set the profile to the current time and use it everywhere
			m_prof = m_utility.setProfValOnEffOn(m_db,m_prof);

            printHeader(strNow);

            // abr entitytype=CATLGCNTRY
            eg = new EntityGroup(null,m_db, m_prof, m_abri.getEntityType(), "Edit", false);
            m_ei = new EntityItem(eg, m_prof, m_db, m_abri.getEntityType(), m_abri.getEntityID());

            println("<p class=\"ibm-intro ibm-alternate-three\"><em>Catalog Country: " + m_ei.getKey() + "</em></p>");

            printNavigateAttributes(m_ei, eg);

            // check CATLGCNTRY attributes
            // set CATLGOFFPUBLASTRUN if not set yet or not ISO date format
            strCATLGOFFPUBLASTRUN = m_utility.getAttrValue(m_ei, ATT_CATLGOFFPUBLASTRUN);
            if (strCATLGOFFPUBLASTRUN == null || strCATLGOFFPUBLASTRUN.length() <= 0) {
		        attList = new OPICMList();
		        strCATLGOFFPUBLASTRUN = STARTDATE;
		        attList.put(ATT_CATLGOFFPUBLASTRUN,"CATLGOFFPUBLASTRUN=" + strCATLGOFFPUBLASTRUN);
		        m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
            } else {
                if (!m_utility.isDateFormat(strCATLGOFFPUBLASTRUN)) {
                    println("<h2>CATLGOFFPUBLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000.</h2>");
                    setReturnCode(FAIL);
                } else {
                    if (strCATLGOFFPUBLASTRUN.length() == 10) {
			            attList = new OPICMList();
				        strCATLGOFFPUBLASTRUN = strCATLGOFFPUBLASTRUN + "-00.00.00.000000";
			            attList.put(ATT_CATLGOFFPUBLASTRUN,"CATLGOFFPUBLASTRUN=" + strCATLGOFFPUBLASTRUN);
			            m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
                    }
                }
            }

            strOFFCOUNTRY = m_utility.getAttrValue(m_ei, ATT_OFFCOUNTRY);
            strOCDesc = m_utility.getAttrValueDesc(m_ei, ATT_OFFCOUNTRY);
            if (strOFFCOUNTRY == null || strOFFCOUNTRY.length() <= 0) {
                println("<h2>"+ATT_OFFCOUNTRY+" is blank.</h2>");
                setReturnCode(FAIL);
            }

            println("<!-- CATLGOFFPUBLASTRUN:"+strCATLGOFFPUBLASTRUN+" OFFCOUNTRY: " + strOCDesc+" -->");

            if (getReturnCode() == PASS) {
				/* Scan all SALES_STATUS for change (i.e. LASTUPDATED > CATLGOFFPUBLASTRUN
				(if CATLGOFFPUBLASTRUN doesn’t have a value, assume 1980-01-01) and the column 'MarkedforDeletion' is 'N'.
				*/
        		EANList list = null;
				// get the countrycode for this catlgcntry.offcountry
                ExtractActionItem xaiCATLGCNTRYGAA = new ExtractActionItem(null, m_db, m_prof, "EXTCATLGCNTRYGAA1");
                String strGENAREACODE = m_utility.getGENAREACODE(m_db, m_prof, xaiCATLGCNTRYGAA, m_ei, strOFFCOUNTRY);
                String strGENAREACODEDESC = "";
                if (strGENAREACODE.indexOf(":") > 0) {
                    int iColon = strGENAREACODE.indexOf(":");
                    strGENAREACODEDESC = strGENAREACODE.substring(iColon + 1);
                    strGENAREACODE = strGENAREACODE.substring(0, iColon);
                }
                // get all salesstatus records for OFFCOUNTRY's countrycode using
                // select * FROM EACM.SALESORG_COUNTRY SC
                // INNER JOIN EACM.SALES_STATUS SS ON SS.SALESORG = SC.SALESORG WHERE SC.COUNTRYCODE = :countrycode
                // and then filter for VARCONDTYPE=SWMODEL, MODEL or SEO, MARKEDFORDELETION=N and LASTUPDATED > catlgcntry.CATLGOFFPUBLASTRUN
                list = getFilteredSalesStatus(strGENAREACODEDESC,strCurrentDate);
                if (list.size() <= 0) {
                    println("<h2>No updated salesstatus records found for SEO, MODEL or SWMODEL with offering country: " + strOCDesc +
                    	", GENAREACODE:" + strGENAREACODEDESC+"</h2>");
                	//setReturnCode(FAIL); // not sure this is really a failure but allow notification
        		}else{
					Vector lseoToCheckVct = new Vector();
					println("<!-- Found "+list.size()+" salesstatus records found SEO, MODEL or SWMODEL with offering country: " + strOCDesc +
							", GENAREACODE:" + strGENAREACODEDESC+" -->");

					ExtractActionItem xai = new ExtractActionItem(null,m_db, m_prof,SS_EXTRACTNAME);

					// this list has ssi.VARCONDTYPE=LSEO or MODEL or SWMODEL, MFD=No and ssi.LASTUPDATED > catlgcntry.CATLGOFFPUBLASTRUN
					for (int i = 0; i < list.size(); i++) {
						Vector catlgpubVct;
						SalesStatusItem ssi = (SalesStatusItem) list.getAt(i);
						String strMATERIALSTATUS = ssi.getMATERIALSTATUS();

						/*search for all CATLGPUB matching this salesstatus record
						For each change, find the corresponding instance of CATLGPUB based on
						COUNTRYCODE (CATLGPUB.OFFCOUNTRY),
						VARCONDTYPE (CATLGPUB.CATOFFTYPE) and
						MATNR (from CATLGPUB based on CATOFFTYPE, match either CATSEOID or CATMACHTYPE & CATMODEL)
						*/
						catlgpubVct = findCatlgpub(ssi);
						println("<!-- Checked SS["+i+"] "+ssi.dump(true)+" found "+catlgpubVct.size()+" CATLGPUB -->");
						for (int j = 0; j < catlgpubVct.size(); j++) {
							String strPUBTO = null;
							String strCATOFFTYPE = null;
							int iDC2 = 0;
							EntityItem eiCATLGPUB = (EntityItem)catlgpubVct.elementAt(j);
							D.ebug(D.EBUG_SPEW, ABR + " found " + eiCATLGPUB.getKey());
							// if null it means in the future.. then the checks will not work because iDC2=ILLEGALARGUMENT for null or ""
							strPUBTO = m_utility.getAttrValue(eiCATLGPUB, ATT_PT);
							if (strPUBTO.length()==0){ //wss
								strPUBTO = FOREVER.substring(0, 10);
							}
							strCATOFFTYPE = m_utility.getAttrValue(eiCATLGPUB, ATT_CATOFFTYPE);
							println("<!-- found " + eiCATLGPUB.getKey()+" ["+j+"] curDate: "+strCurrentDate+
								" PUBTO: "+strPUBTO+" "+ATT_CATOFFTYPE+": "+strCATOFFTYPE+" -->");
							iDC2 = m_utility.dateCompare(strPUBTO, strCurrentDate);
							// ZJ means withdrawn by RDh, if PUBTO is after today then flag this as an error
							if (strMATERIALSTATUS.equals("ZJ")){
								if(iDC2 == PDGUtility.LATER) {  // error type 1
									String msg = "Sales Status Withdraw prior to the Publish To date. "+
										"<br />SALES_STATUS.MATERIALSTATUS: " + strMATERIALSTATUS;
									printAllAttributes(msg,eiCATLGPUB);
									setReturnCode(FAIL);
								}
								if (strCATOFFTYPE.equals("LSEO")) { // check for error type3
									String strCATSEOID = m_utility.getAttrValue(eiCATLGPUB, ATT_CATSEOID);
									// this means the LSEO was withdrawn so check the parent LSEOBUNDLE for withdrawn too
									// do this error check after looking at all SS because the update for the
									// bundle may be later in the list and an invalid error would be generated
									//checkParentLseoBundleCatlgpub(strCATSEOID, strMATERIALSTATUS);
									if(!lseoToCheckVct.contains(strCATSEOID)){ //just add this one once
										lseoToCheckVct.add(strCATSEOID);
									}
								}
							}// end status=ZJ
							else {
								// anything but ZJ means still valid by RDh, if PUBTO is before today then flag this as an error
								if (iDC2 == PDGUtility.EARLIER) {  // error type 2
									String msg = "Sales Status is not Withdraw after the Publish To date. "+
										"<br />SALES_STATUS.MATERIALSTATUS: " + strMATERIALSTATUS;
									printAllAttributes(msg, eiCATLGPUB);
									setReturnCode(FAIL);
								}
							} // end status!=ZJ

							/*
	Wayne Kehrli	also - if SalesStatus produces error ---- when = "ZJ"; it should update the 3 flags --- Linda thinks that it does
	Wayne Kehrli	if SaleStatus <> "ZJ" and there is an error - then you should also update the 3 flags --- again, Linda thinks that it does
							*/
							//always try to update now - this may pull one VE depending on the
							// value of ss, all CATLGPUB for this ss will use the values found in the VE
							updateCatlgpub(eiCATLGPUB, strMATERIALSTATUS, xai);
						} // end CATLGPUB vct loop

						catlgpubVct.clear();  // release memory
					}// end ss loop

					if (lseoToCheckVct.size()>0){
						for (int i=0; i<lseoToCheckVct.size(); i++){
							String strCATSEOID = (String) lseoToCheckVct.elementAt(i);
							// do this error check after looking at all SS because the update for the
							// bundle may be later in the list and an invalid error would be generated
							checkParentLseoBundleCatlgpub(strCATSEOID, "ZJ");
						}

						lseoToCheckVct.clear();
					}

					list.clear(); // release memory
				}  // some ss found
            } // end all ok so far

			// Upon completion of the ABR, set CATLGOFFPUBLASTRUN = the DTS from the server obtained at
			// the beginning execution of the ABR.
			attList = new OPICMList();
			attList.put(ATT_CATLGOFFPUBLASTRUN,"CATLGOFFPUBLASTRUN=" + strNow);
			m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
			sskeyTbl.clear();

			println("<!-- Updated a total of "+updateCount+" CATLGPUB entities -->");
        } catch (SBRException _sbrex) {
            String strError = _sbrex.toString();
            int i = strError.indexOf("(ok)");
            if (i < 0) {
                setReturnCode(UPDATE_ERROR);
                println("<h3><span style=\"color:#c00; font-weight:bold;\">Generate Data error: <pre>" +
                	strError+ "</pre></span></h3>");
                logError(_sbrex.toString());
            } else {
                strError = strError.substring(0, i);
                println("<pre>"+strError+"</pre>");
            }
        } catch (Throwable exc) {
        	Object[] args = new String[1];
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            MessageFormat msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(FAIL);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = exc.getMessage();
            println(msgf.format(args));
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            println(msgf.format(args));
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
        } finally {
        	String strDgName = null;
        	String msg = buildMessage(MSG_IAB2016I,new String[] {getABRDescription(),(getReturnCode() == PASS ? "Passed" : "Failed")}) ;
            println("<p><b>"+ msg+ "</b></p>");
            log(msg);

          // set DG title
            strDgName = getABRDescription() + ":" + getEntityType() + ":" + getEntityID();
            if (strDgName.length() > I64) {
                strDgName = strDgName.substring(0, I64);
            }
            setDGTitle(strDgName);
            setDGRptName(ABR);

            // set DG submit string
            setDGString(getABRReturnCode());

			println(EACustom.getTOUDiv());

      		//Stuff into report for subscription and notification
      		// Tack on the DGString
            printDGSubmitString();
	        buildReportFooter();    // Print </html>

	      // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
        }
    }

	/********************
	* copied from PDGUtility.getSalesStatusForCountry, added varcondtype, markedfordeletion and date check
	* filtering at this level to reduce memory and improve performance.  Filtering at the SP level is really
	* the way to do it, but do it here for now.
	RQ022507373 and MN31580435
Wayne Kehrli	2 different criteria to include
1) . LASTUPDATED > CATLGOFFPUBLASTRUN (if CATLGOFFPUBLASTRUN doesn’t have a value, assume 1980-01-01) and
the column 'MarkedforDeletion' is 'N' and MATERIALSTATUSDATE is less than or equal to NOW()
2) Also include all SALES_STATUS where MATERIALSTATUSDATE is equal to NOW() + 1 day and the column 'MarkedforDeletion' is 'N'
Wendy	criteria 1 will find all those that are recently updated and have an effective date of today or in the past
criteria 2 will find any that have an effective date of tomorrow
	*/
	private EANList getFilteredSalesStatus(String _strGenareaCode, String strCurrentDate)
		throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException
	{
		String strTraceBase = ABR+" getFilteredSalesStatus method ";
		// Initialize some SP specific objects needed in this method
		ReturnDataResultSet rdrs = null;
		ResultSet rs = null;
		ReturnStatus returnStatus = new ReturnStatus(-1);
		EANList returnList = new EANList();
		String tomorrow = m_utility.getDate(strCurrentDate, 1);

		D.ebug(D.EBUG_SPEW,strTraceBase + _strGenareaCode+" currentDate:"+strCurrentDate+" tomorrow:"+tomorrow);
		try {
			rs = m_db.callGBL9305(returnStatus, _strGenareaCode);
			rdrs = new ReturnDataResultSet(rs);
		} finally {
			if(rs!=null){
				rs.close();
			}
			rs = null;
			m_db.commit();
			m_db.freeStatement();
			m_db.isPending();
		}
		if(rdrs!=null) {
			for (int ii = 0; ii < rdrs.size(); ii++) {
				String strMATNR = rdrs.getColumn(ii,0).trim();
				String strVARCOND = rdrs.getColumn(ii,1).trim();
				String strVARCONDTYPE = rdrs.getColumn(ii,2).trim();
				String strSALESORG = rdrs.getColumn(ii,3).trim();
				String strMATERIALSTATUS = rdrs.getColumn(ii,4).trim();
				String strMATERIALSTATUSDATE = rdrs.getColumn(ii,5).trim();
				String strLASTUPDATED = rdrs.getColumn(ii,6).trim();
				String strMARKEDFORDELETION = rdrs.getColumn(ii,7).trim();
				m_db.debug(D.EBUG_SPEW, strTraceBase + " gbl9305 result:" + strMATNR + ":" + strVARCOND
						+ ":" + strVARCONDTYPE + ":" + strSALESORG + ":" + strMATERIALSTATUS
						+ ":" + strMATERIALSTATUSDATE + ":" + strLASTUPDATED + ":" + strMARKEDFORDELETION);

				if (strVARCONDTYPE.equalsIgnoreCase("MODEL") ||
					strVARCONDTYPE.equalsIgnoreCase("SWMODEL") ||
					strVARCONDTYPE.equalsIgnoreCase("SEO")) {
					if (strMARKEDFORDELETION.equals("N")){	// use those SS that are MFD=No
						boolean addSS = false;
						// 1) if LASTUPDATED > catlgcntry.CATLGOFFPUBLASTRUN and MATERIALSTATUSDATE is less than or equal to NOW()
						int iDC1 = m_utility.longDateCompare(strLASTUPDATED, strCATLGOFFPUBLASTRUN);
						if (iDC1 == PDGUtility.LATER &&
							(strMATERIALSTATUSDATE.compareTo(strCurrentDate)<=0)) {
							addSS = true;
						}
						//2) Also include all SALES_STATUS where MATERIALSTATUSDATE is equal to NOW() + 1 day
						if (strMATERIALSTATUSDATE.equals(tomorrow)){
							addSS = true;
						}
						if (addSS){
							String strKey = strMATNR + strVARCOND + strVARCONDTYPE + strSALESORG;
							if (returnList.get(strKey) == null) {
								SalesStatusItem ssi = new SalesStatusItem(m_prof, strKey);
								ssi.setMATNR(strMATNR);
								ssi.setVARCOND(strVARCOND);
								ssi.setVARCONDTYPE(strVARCONDTYPE);
								ssi.setSALESORG(strSALESORG);
								ssi.setMATERIALSTATUS(strMATERIALSTATUS);
								ssi.setMATERIALSTATUSDATE(strMATERIALSTATUSDATE);
								ssi.setLASTUPDATED(strLASTUPDATED);
								ssi.setMARKEDFORDELETION(strMARKEDFORDELETION);
								returnList.put(ssi);
							}
						}
					}
				}
			}
		}
		return returnList;
	}

  	/*********************
   	* check parent LSEOBUNDLE for this LSEO (CATLGPUB.CATOFFTYPE)
	* If the change applies to a LSEO (CATLGPUB.CATOFFTYPE),
	* then find the LSEO entity where the SEOID = CATLGPUB.CATSEOID
	* and find the parent LSEOBUNDLEs.
	* Then find the corresponding instances of CATLGPUB
	* (where CATOFFTYPE = 'LSEOBUNDLE' and CATSEOID=LSEOBUNDLE.SEOID).
	* Report an error for these CATLGPUB instances if the CATLGPUB>NOW().
	*
   	* this should only be done for MATERIALSTATUS='ZJ'
   	*@param strCATSEOID String from CATLGPUB.CATSEOID
   	*@param strMATERIALSTATUS String MATERIALSTATUS from salesstatus record, only used in msg
   	*/
    private void checkParentLseoBundleCatlgpub(String strCATSEOID, String strMATERIALSTATUS)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.SBRException
    {
		EntityItem[] aeiLSEO;
		String strSai = "SRDLSEO1";
		StringBuffer sb = new StringBuffer();
		sb.append("map_SEOID=" + strCATSEOID);
		// find the LSEO matching this seoid
		aeiLSEO = m_utility.dynaSearch(m_db,m_prof, null, strSai, "LSEO", sb.toString());
		if (aeiLSEO != null && aeiLSEO.length > 0) {
			ExtractActionItem xaiLSEO = new ExtractActionItem(null,m_db, m_prof,"EXTLSEO1");
			// get the parent LSEOBUNDLEs
			EntityList elLSEO = EntityList.getEntityList(m_db,m_prof,xaiLSEO,aeiLSEO);
			EntityGroup egLSEOBUNDLE = elLSEO.getEntityGroup("LSEOBUNDLE");
			// output some debug info
			for (int i=0; i<elLSEO.getParentEntityGroup().getEntityItemCount(); i++) {
				EntityItem lseoitem = elLSEO.getParentEntityGroup().getEntityItem(i);
				println("<!-- found "+lseoitem.getKey()+" for CATSEOID: " + strCATSEOID+" -->");
			}

			if (egLSEOBUNDLE != null) {
				for (int b = 0; b < egLSEOBUNDLE.getEntityItemCount(); b++) {
					EntityItem[] aeiLSEOBDLCATLGPUB;
					EntityItem eiLSEOBUNDLE = egLSEOBUNDLE.getEntityItem(b);
					String strLSEOBDLSEOID = m_utility.getAttrValue(eiLSEOBUNDLE, ATT_SEOID);
					println("<!-- found parent "+eiLSEOBUNDLE.getKey()+" for child LSEO.CATSEOID: " + strCATSEOID+" -->");

					// search for CATLGPUB for parent LSEOBUNDLE
					sb = new StringBuffer();
					sb.append("map_OFFCOUNTRY=" + strOFFCOUNTRY + ";"); //wss country should be part of search
					sb.append("map_CATSEOID=" + strLSEOBDLSEOID + ";");
					sb.append("map_CATOFFTYPE=BUNDLE");
					strSai = "SRDCATLGPUB1";
					aeiLSEOBDLCATLGPUB = m_utility.dynaSearch(m_db, m_prof, null, strSai,"CATLGPUB", sb.toString());
					// check the date on the LSEOBUNDLE's CATLGPUBs
					if (aeiLSEOBDLCATLGPUB != null && aeiLSEOBDLCATLGPUB.length > 0) {
						for (int k = 0; k < aeiLSEOBDLCATLGPUB.length; k++) {
							int iDC4=0;
							EntityItem eiLSEOBDLCATLGPUB = aeiLSEOBDLCATLGPUB[k];
							String strPUBTO2 = m_utility.getAttrValue(eiLSEOBDLCATLGPUB,ATT_PT);
							if (strPUBTO2.length()==0){ //wss
								strPUBTO2 = FOREVER.substring(0, 10);
							}

							println("<!-- parent "+eiLSEOBUNDLE.getKey()+" has " + eiLSEOBDLCATLGPUB.getKey()+" curDate: "+
								strCurrentDate+" PUBTO: "+strPUBTO2+" SEOID: "+strLSEOBDLSEOID+" -->");
							iDC4 = m_utility.dateCompare(strPUBTO2, strCurrentDate);
							if (iDC4 == PDGUtility.LATER) {  // error type 3
								String msg = "LSEO Sales Status Withdraw prior to the LSEOBUNDLE. "+
									"<br />SALES_STATUS.MATERIALSTATUS: " + strMATERIALSTATUS;
								printAllAttributes(msg, eiLSEOBDLCATLGPUB);
								setReturnCode(FAIL);
							}
						} // end CATLGPUB for LSEOBUNDLE loop
					}// end some CATLGPUB found for LSEOBUNDLE.CATSEOID
					else{
						println("<!-- NO CATLGPUB found for " + sb+" -->");
					}
				} // end LSEOBUNDLE loop
			}// end LSEOBUNDLE group found
			else{
				println("<h2>Error: VE for EXTLSEO1 is missing LSEOBUNDLE group </h2>");
				setReturnCode(FAIL);
			}

			elLSEO.dereference();  // release memory
		}// end some LSEO found for CATSEOID
		else{
			println("<!-- NO LSEO found for " + sb+" -->");
		}
	}

  	/*********************
   	* find the CATLGPUB for this salesstatus record
   	*.For each change, find the corresponding instance of CATLGPUB based on COUNTRYCODE (CATLGPUB.OFFCOUNTRY),
   	* VARCONDTYPE (CATLGPUB.CATOFFTYPE) and MATNR (from CATLGPUB based on CATOFFTYPE,
   	* match either CATSEOID or CATMACHTYPE & CATMODEL)
   	*@param ssi SalesStatusItem
   	*@return Vector of CATLGPUB
   	*/
	private Vector findCatlgpub(SalesStatusItem ssi)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.SBRException
	{
		Vector catlgpubVct = new Vector();
		String strMATNR = ssi.getMATNR().trim();
		String strVARCONDTYPE = ssi.getVARCONDTYPE();
		StringBuffer sb = new StringBuffer();

		D.ebug( D.EBUG_SPEW, ABR  + " info from SalesStatusItem: " + ssi.dump(true));
		if (strVARCONDTYPE.equalsIgnoreCase("MODEL")|| strVARCONDTYPE.equalsIgnoreCase("SWMODEL")) {
			if (strMATNR.length() >= 4) {
				// wayne says to use the MATNR directly, not the flag code
				// and this doesn't work because CATMACHTYPE is a text attr, flagcode is never found!!
				//String[] astrMachType = m_utility.getFlagCodeForLikedDesc(m_db, m_prof, ATT_CATMACHTYPE, strMATNR.substring(0, 4));
				//if (astrMachType!=null && astrMachType.length > 0) {
//					sb.append("map_CATMACHTYPE=" + astrMachType[0] + ";");
					sb.append("map_CATMACHTYPE=" + strMATNR.substring(0, 4) + ";");
					sb.append("map_CATMODEL=" + strMATNR.substring(4) + ";");
					sb.append("map_OFFCOUNTRY=" + strOFFCOUNTRY + ";");
					sb.append("map_CATOFFTYPE=MODEL");
					// search for CATLGPUB
					doCatlgpubSearch(sb,catlgpubVct);
				//} else {
				//	D.ebug(D.EBUG_SPEW, ABR + " unable to find machtype for : " + strMATNR.substring(0, 4));
				//	println("<!-- unable to find "+strVARCONDTYPE+" "+ATT_CATMACHTYPE+" flagcode for "+strMATNR.substring(0, 4)+" -->");
				//}
			}else{
				println("<!--findCatlgpub: skipping "+strVARCONDTYPE+" with MATNR<4 "+strMATNR+" -->");
			}
		} else if (strVARCONDTYPE.equalsIgnoreCase("SEO")) {
			StringBuffer sb2 = new StringBuffer();
			sb.append("map_CATSEOID=" + strMATNR + ";");
			sb.append("map_OFFCOUNTRY=" + strOFFCOUNTRY + ";");
			sb.append("map_CATOFFTYPE=LSEO");
			// search for CATLGPUB
			doCatlgpubSearch(sb,catlgpubVct);

			sb2.append("map_CATSEOID=" + strMATNR + ";");
			sb2.append("map_OFFCOUNTRY=" + strOFFCOUNTRY + ";");
			sb2.append("map_CATOFFTYPE=BUNDLE");
			// search for CATLGPUB
			doCatlgpubSearch(sb2,catlgpubVct);
		}

		return catlgpubVct;
	}

  	/*********************
   	* search for CATLGPUB specified in string
   	*@param sb StringBuffer with search criteria
   	*/
   	private void doCatlgpubSearch(StringBuffer sb, Vector catlgpubVct)
		throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
		COM.ibm.eannounce.objects.SBRException
	{
		String strSai = "SRDCATLGPUB1";
		EntityItem[] aeiCATLGPUB1 = null;

		D.ebug(D.EBUG_SPEW, ABR + " looking for CATLGPUB matching: " + sb);
		aeiCATLGPUB1 = m_utility.dynaSearch(m_db, m_prof, null, strSai, "CATLGPUB", sb.toString());
		if (aeiCATLGPUB1 != null && aeiCATLGPUB1.length > 0) {
			for (int j = 0; j < aeiCATLGPUB1.length; j++) {
				EntityItem eiCATLGPUB = aeiCATLGPUB1[j];
				catlgpubVct.addElement(eiCATLGPUB);
				aeiCATLGPUB1[j]=null;
			}
		}else{
			D.ebug(D.EBUG_SPEW, ABR + " Unable to find CATLGPUB for " + sb);
			println("<!--doCatlgpubSearch: Unable to find CATLGPUB for " + sb+" -->");
		}
	}

  	/*********************
   	*  update this CATLGPUB if changes are found
   	*@param eiCATLGPUB CATLGPUB entityitem
   	*@param strMATERIALSTATUS String MATERIALSTATUS from salesstatus record
   	*/
    private void updateCatlgpub(EntityItem eiCATLGPUB, String strMATERIALSTATUS,
    			ExtractActionItem xai)
    	throws COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    	java.sql.SQLException,
    	COM.ibm.eannounce.objects.SBRException
    {
		OPICMList attL = new OPICMList();
		String strBuyable = "";
		String strHide = "";
		String strCart = "";
		//String strCustomize = null;

		//MN33416775
		if (SalesStatusInfo.isSimpleSS(strMATERIALSTATUS)){
			SalesStatusInfo sshbac = SalesStatusInfo.getSalesStatusInfo(strMATERIALSTATUS,"");//MN33416775
            println("<!--updateCatlgpub: sskey: " + strMATERIALSTATUS+" values "+sshbac+" -->");
			// custimize not part of simple ss
			strCart = sshbac.getAddToCart(); //MN33416775
			strBuyable = sshbac.getBuyable(); //MN33416775
			strHide = sshbac.getHide(); //MN33416775
		}else{
			// must pull related info for this CATLGPUB
			SalesStatusInfo sshbac = findSalesStatusInfo(eiCATLGPUB, strMATERIALSTATUS,	xai);

			//strCustomize = sshbac.getCustimize(); //MN33416775
			strCart = sshbac.getAddToCart(); //MN33416775
			strBuyable = sshbac.getBuyable(); //MN33416775
			strHide = sshbac.getHide(); //MN33416775
		}

	/*MN33416775
		// more efficient to get all at once
		String strMtrnCodes = (String)PDGUtility.m_hshMTRNCODE.get(strMATERIALSTATUS);
		if (strMtrnCodes != null && strMtrnCodes.length() > 0) {
			StringTokenizer st = new StringTokenizer(strMtrnCodes, ",");
			if (st.countTokens() == 3) {
				strBuyable = st.nextToken();
				strHide = st.nextToken();
				strCart = st.nextToken();
			}
		}*/
	/*MN35543346 - dont change customize once it is set
		if (strCustomize != null){
			if (!m_utility.getAttrValue(eiCATLGPUB,ATT_CATCUSTIMIZE).equals(strCustomize)) {
				attL.put(ATT_CATCUSTIMIZE, ATT_CATCUSTIMIZE + "=" + strCustomize);
			}
		}
		*/

		if (!m_utility.getAttrValue(eiCATLGPUB,ATT_CATSALESSTATUS).equals(strMATERIALSTATUS)) {
			attL.put(ATT_CATSALESSTATUS, ATT_CATSALESSTATUS + "=" + strMATERIALSTATUS);
		}

		if (!m_utility.getAttrValue(eiCATLGPUB,ATT_CATADDTOCART).equals(strCart)) {
			attL.put(ATT_CATADDTOCART, ATT_CATADDTOCART + "=" + strCart);
		}

		if (!m_utility.getAttrValue(eiCATLGPUB, ATT_CATBUYABLE).equals(strBuyable)) {
			attL.put(ATT_CATBUYABLE, ATT_CATBUYABLE + "=" + strBuyable);
		}

		if (!m_utility.getAttrValue(eiCATLGPUB,ATT_CATHIDE).equals(strHide)) {
			attL.put(ATT_CATHIDE,ATT_CATHIDE + "=" + strHide);
		}

		if (attL.size() > 0) {
			String strWorkFlow = m_utility.getAttrValue(eiCATLGPUB,ATT_CATWORKFLOW);

			if (strWorkFlow.equals("Override")) {
				attL.put(ATT_CATWORKFLOW,"CATWORKFLOW=SalesStatusOverride");
			} else if (strWorkFlow.equals("Accept")) {
				attL.put(ATT_CATWORKFLOW,"CATWORKFLOW=Change");
			} else if (strWorkFlow.equals("New")) {
				attL.put(ATT_CATWORKFLOW,"CATWORKFLOW=Change");
			}
			if(strUpdatedBy != null && strUpdatedBy.length() > 0) {
				attL.put("CATUPDATEDBY", "CATUPDATEDBY=" + strUpdatedBy);
			}

			println("<!--updateCatlgpub: Updating "+eiCATLGPUB.getKey()+" using "+attL+" -->");
			m_utility.updateAttribute(m_db,m_prof,eiCATLGPUB,attL);
			updateCount++;
		}else{
			println("<!--updateCatlgpub: NO changes found for "+eiCATLGPUB.getKey()+" -->");
		}
	}

	/************
	* 1.	MODEL.COFCAT = {Hardware, Software, Service}
	*	LSEO via WWSEOLSEO via MODELWWSEO to MODEL.COFCAT
	*	LSEOBUNDLE.BUNDLETYPE
	*		If not specified, then Hardware
	*		If one of the values = Hardware, then Hardware
	*		else IF one of the values = Software, then Software
	*		else Service
	*
	* 2.	LSEO.PRCINDC = Yes implies Priced
	* 	LSEOBUNDLE assumed to be Priced
	* 3.	SPECBID = Yes implies Custom
	* 	LSEO found on WWSEO; LSEOBUNDLE
	*
	*/
	private SalesStatusInfo findSalesStatusInfo(EntityItem eiCATLGPUB, String strMATERIALSTATUS,
		ExtractActionItem xai)
		throws COM.ibm.opicmpdh.middleware.MiddlewareException,
		COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
    	java.sql.SQLException
	{
		// must pull related info for this CATLGPUB
		String cofcat="";
		String priced="";
		String specbid="";
		String offeringid = "";
		EntityItem[] aei = new EntityItem[] {eiCATLGPUB};

		String offtype = m_utility.getAttrValue(eiCATLGPUB,ATT_CATOFFTYPE);
		println("<!--findSalesStatusInfo: "+eiCATLGPUB.getKey()+" offtype: " + offtype+" ss: "+strMATERIALSTATUS+" -->");
		if ("MODEL".equals(offtype)){
			offeringid = m_utility.getAttrValue(eiCATLGPUB,ATT_CATMACHTYPE)+
				m_utility.getAttrValue(eiCATLGPUB,ATT_CATMODEL);
		}else{
			offeringid = m_utility.getAttrValue(eiCATLGPUB,ATT_CATSEOID)+
				m_utility.getAttrValue(eiCATLGPUB,ATT_CATSEOID);
		}

		String sskey = (String) sskeyTbl.get(offeringid);
		if (sskey==null){
			// get the parent LSEOBUNDLE/LSEO/MODEL try one extract for all
			EntityList list = EntityList.getEntityList(m_db,m_prof,xai,aei);
			println("<!--findSalesStatusInfo: Extract "+SS_EXTRACTNAME+" " + com.ibm.transform.oim.eacm.util.PokUtils.outputList(list)+" -->");

			EntityGroup eg = null;
			if ("MODEL".equals(offtype)){
				eg = list.getEntityGroup("MODEL");
				if (eg.getEntityItemCount()==0) {
					println("<!--findSalesStatusInfo: ERROR no "+eg.getEntityType()+" found -->");
				} else {
					EntityItem ei = eg.getEntityItem(0);
					cofcat = m_utility.getAttrValue(ei, ATT_COFCAT);
					println("<!--findSalesStatusInfo: using "+ei.getKey()+" cofcat: "+cofcat+" -->");
				}
				sskey = cofcat;
			}else if ("BUNDLE".equals(offtype)){
				priced = "yes"; //LSEOBUNDLE assumed to be Priced- PRCINDC yes
				eg = list.getEntityGroup("LSEOBUNDLE");
				if (eg.getEntityItemCount()==0) {
					println("<!--findSalesStatusInfo: ERROR no "+eg.getEntityType()+" found -->");
				} else {
					EntityItem ei = eg.getEntityItem(0);
					specbid = m_utility.getAttrValue(ei, "SPECBID");
					// determine HW, SW or SVC from BUNDLETYPE, it is F multiflag
					//BUNDLETYPE	100	Hardware
					//BUNDLETYPE	101	Software
					//BUNDLETYPE	102	Service
					String btype = m_utility.getAttrValue(ei, "BUNDLETYPE");
					if (btype.length()==0){ // default to hw
						cofcat = HW;
					}else{
						if (btype.indexOf(HW) != -1){ // if HW selected, use that
							cofcat = HW;
						}else if (btype.indexOf(SW) != -1){ // if SW selected, use that
							cofcat = SW;
						}else{
							cofcat = SVC;
						}
					}
					println("<!--findSalesStatusInfo: using "+ei.getKey()+" specbid: "+specbid+
						" cofcat: "+cofcat+" from btype: "+btype+" -->");
				}

				sskey = cofcat+":"+priced+":"+specbid;
			}else{ // must be LSEO
				eg = list.getEntityGroup("MODEL");
				if (eg.getEntityItemCount()==0) {
					println("<!--findSalesStatusInfo: ERROR no "+eg.getEntityType()+" found -->");
				} else {
					EntityItem ei = eg.getEntityItem(0);
					cofcat = m_utility.getAttrValue(ei, ATT_COFCAT);
					println("<!--findSalesStatusInfo: using "+ei.getKey()+" cofcat: "+cofcat+" -->");
				}
				eg = list.getEntityGroup("WWSEO");
				if (eg.getEntityItemCount()==0) {
					println("<!--findSalesStatusInfo: ERROR no "+eg.getEntityType()+" found -->");
				} else {
					EntityItem ei = eg.getEntityItem(0);
					specbid = m_utility.getAttrValue(ei, "SPECBID");
					println("<!--findSalesStatusInfo: using "+ei.getKey()+" specbid: "+specbid+" -->");
				}
				eg = list.getEntityGroup("LSEO");
				if (eg.getEntityItemCount()==0) {
					println("<!--findSalesStatusInfo: ERROR no "+eg.getEntityType()+" found -->");
				} else {
					EntityItem ei = eg.getEntityItem(0);
					priced = m_utility.getAttrValue(ei, "PRCINDC");
					println("<!--findSalesStatusInfo: using "+ei.getKey()+" priced: "+priced+" -->");
				}

				sskey = cofcat+":"+priced+":"+specbid;
			}
			sskeyTbl.put(offeringid, sskey);
			list.dereference();
		}else{ // already got list, so use this
			println("<!--findSalesStatusInfo: sskey "+sskey+" already found for "+offeringid+" -->");
		}

		SalesStatusInfo sshbac = SalesStatusInfo.getSalesStatusInfo(strMATERIALSTATUS,sskey);
		println("<!--findSalesStatusInfo: sskey: "+strMATERIALSTATUS+":" + sskey+" returned "+sshbac+" -->");

		return sshbac;
	}
  	/*********************
   	*  print the attribute information for this entity
   	*/
    private void printAllAttributes(String msg, EntityItem entity) {
        int lineCnt=0;
        EntityGroup eg = entity.getEntityGroup();
        if (!outputWarning) { // just output once at top
        	outputWarning = true;
    	    println("<h2>These attribute values are listed before salesstatus updates were applied.</h2>");
		}
        println("<h2 class=\"orange-med\">"+msg+"</h2>");
        println("<table width=\"100%\">");
		println("<tr style=\"background-color:#cef;\"><th width=\"50%\" title=\""+
			entity.getKey()+"\">Attribute Description</th>"+
			"<th width=\"50%\">Attribute Value</th></tr>");

		for(int i=0;i<eg.getMetaAttributeCount();i++){
			EANMetaAttribute ma = eg.getMetaAttribute(i);
            EANAttribute att = entity.getAttribute(ma.getAttributeCode());
            println(
                "<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+
                "<td width=\"50%\" >"
                + ma.getLongDescription()
                + "</td><td width=\"50%\">"
                + (att == null || att.toString().length() == 0 ? "** Not Populated **" : att.toString())
                + "</td></tr>");
        }
        println("</table><br />");
    }

    /*********************
    **Get Nav Attributes of an Entity
    **
    */
	private void printNavigateAttributes(EntityItem entity, EntityGroup eg){
		int lineCnt=0;
		println("<table width=\"100%\">");
		println("<tr style=\"background-color:#cef;\"><th width=\"50%\">Navigation Attribute</th>"+
			"<th width=\"50%\">Attribute Value</th></tr>");
		for(int i=0;i<eg.getMetaAttributeCount();i++){
			EANMetaAttribute ma = eg.getMetaAttribute(i);
			if(ma.isNavigate()){
				EANAttribute att = entity.getAttribute(ma.getAttributeCode());
				println(
					"<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+
					"<td width=\"50%\" >"
					+ ma.getLongDescription()
					+ "</td><td width=\"50%\">"
					+ (att == null || att.toString().length() == 0 ? "** Not Populated **" : att.toString())
					+ "</td></tr>");
			}
		}
		println("</table>");
	}

  	/*********************
   	*  print the heading information about this abr
   	*/
    private void printHeader(String strNow)  {
		println("<table width=\"750\" cellpadding=\"0\" cellspacing=\"0\">");
		println("<tr><th width=\"25%\">ABRName: </th><td>" +
			getABRCode() + "</td></tr>");
		println("<tr><th width=\"25%\">Abr Version: </th><td>" +
			getABRVersion() + "</td></tr>");
		println("<tr><th width=\"25%\">Enterprise: </th><td>" +
			getEnterprise() + "</td></tr>");
		println("<tr><th width=\"25%\">Valid Date: </th><td>" +
			m_prof.getValOn() + "</td></tr>");
		println("<tr><th width=\"25%\">Effective Date: </th><td>" +
			m_prof.getEffOn() + "</td></tr>");
		println("<tr><th width=\"25%\">Date Generated: </th><td>" +
			strNow + "</td></tr>" +
			"</table>");
		println("<h3>Description: </h3><p>" + getDescription() + "</p><hr>");
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
		return "Sales Status ABR";
	}

	/**
	* getRevision
	*
	* @return
	* @author Owner
	*/
	public String getRevision() {
		return "1.28";
	}

	/** removed because of jtest error: "Avoid hiding inherited "static" member methods. [OOP.AHSM]"
	* getVersion
	*
	* @return
	* @author Owner
	*
	public static String getVersion() {
	return ("CATLGPUBABR02.java,v 1.28 2008/09/09 12:52:08 wendy Exp");
	}*/

	/**
	* getABRVersion
	*
	* @return
	* @author Owner
	*/
	public String getABRVersion() {
		return "CATLGPUBABR02.java "+getRevision();
	}
}
