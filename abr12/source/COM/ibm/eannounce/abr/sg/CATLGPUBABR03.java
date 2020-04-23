// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2007  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//CATLGPUBABR03.java,v
//Revision 1.17  2008/01/30 19:39:17  wendy
//Cleanup RSA warnings
//
//Revision 1.16  2008/01/04 16:45:32  wendy
//MN33416775 handling of salesstatus chgs
//
//Revision 1.15  2007/09/28 17:25:57  wendy
//limit len in mw instead
//
//Revision 1.13  2007/08/17 19:16:54  wendy
//Added more debug output
//
//Revision 1.12  2007/05/04 13:30:08  wendy
//RQ022507373 and MN31580435 updates
//
//Revision 1.11  2006/10/03 16:19:18  joan
//work on performance
//
//Revision 1.10  2006/09/27 21:21:13  joan
//changes
//
//Revision 1.9  2006/09/27 15:51:49  joan
//changes
//
//Revision 1.8  2006/09/26 18:42:06  joan
//working on catlgpub
//
//Revision 1.7  2006/09/26 01:23:55  joan
//fixes
//
//Revision 1.6  2006/09/26 00:55:44  joan
//add catupdatedby
//
//Revision 1.5  2006/09/19 22:02:15  joan
//changes
//
//Revision 1.4  2006/03/03 19:23:26  bala
//remove reference to Constants.CSS
//
//Revision 1.3  2006/03/02 22:26:56  joan
//fixes
//
//Revision 1.2  2006/01/24 17:00:44  yang
//Jtest Changes
//
//Revision 1.1  2005/12/21 00:20:23  joan
//add new abr
//
//Revision 1.5  2005/12/07 21:01:43  joan
//fixes
//
//Revision 1.4  2005/12/07 16:02:54  joan
//fixes
//
//Revision 1.3  2005/11/29 23:44:09  joan
//fixes
//
//Revision 1.2  2005/10/31 23:54:05  joan
//fixes
//
//Revision 1.1  2005/10/18 22:06:52  joan
//add new abr
//
//

package COM.ibm.eannounce.abr.sg;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.eannounce.abr.util.*;
import java.util.*;
import java.io.*;
import java.text.*;

/**
 * LSEO publish abr
 *@author     Administrator
 *@created    August 30, 2002
 */
public class CATLGPUBABR03 extends PokBaseABR {
	// Class constants
	private final static String ABR = "CATLGPUBABR03";
	private PDGUtility m_utility = new PDGUtility();
	private EntityItem m_ei = null;
    private static final int I64=64;
	/**
	 * STARTDATE
	 *
	 */
	private final static String STARTDATE = "1980-01-01-00.00.00.000000";
	private int m_iDays = 0;
	private int m_iChunk = 100;
	private boolean m_bSearch = false; // set in properties file if want search done for catlgpub if extract doesnt return them
	/**
	 * execute_run
	 *
	 * @author Owner
	 */
	public void execute_run() {
		String strNow = null;
		String strCATLGPUBLSEOLASTRUN = null;
		String strOFFCOUNTRY = null;
		EntityGroup eg = null;
		CATLGPUBLSEOPDG pdg = null;
		try {
			readPropertyFile();
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
			printHeader(strNow);
			
			eg = new EntityGroup(null,m_db,m_prof,m_abri.getEntityType(),"Edit",false);
			m_ei = new EntityItem(eg,m_prof,m_db,m_abri.getEntityType(),m_abri.getEntityID());

            println("<p class=\"ibm-intro ibm-alternate-three\"><em>Catalog Country: " + m_ei.getKey() + "</em></p>");

			printNavigateAttributes(m_ei, eg);

			// check CATLGCNTRY attributes
			strCATLGPUBLSEOLASTRUN =	m_utility.getAttrValue(m_ei, "CATLGPUBLSEOLASTRUN");
			if (strCATLGPUBLSEOLASTRUN == null || strCATLGPUBLSEOLASTRUN.length() <= 0) {
				OPICMList attList = new OPICMList();
				strCATLGPUBLSEOLASTRUN = STARTDATE;
				attList.put("CATLGPUBLSEOLASTRUN","CATLGPUBLSEOLASTRUN=" + strCATLGPUBLSEOLASTRUN);
				m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
			} else {
				if (!m_utility.isDateFormat(strCATLGPUBLSEOLASTRUN)) {
			  		println("<p>CATLGPUBLSEOLASTRUN is not in date format 1980-01-01 or 1980-01-01-00.00.00.000000</p>");
			  		setReturnCode(FAIL);
				} else {
			  		if (strCATLGPUBLSEOLASTRUN.length() == 10) {
						OPICMList attList = new OPICMList();
						strCATLGPUBLSEOLASTRUN = strCATLGPUBLSEOLASTRUN + "-00.00.00.000000";
						attList.put("CATLGPUBLSEOLASTRUN","CATLGPUBLSEOLASTRUN="+ strCATLGPUBLSEOLASTRUN);
						m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
			  		}
				}
			}

			strOFFCOUNTRY = m_utility.getAttrValue(m_ei, "OFFCOUNTRY");
			if (strOFFCOUNTRY == null || strOFFCOUNTRY.length() <= 0) {
				println("<p>OFFCOUNTRY is blank.</p>");
				setReturnCode(FAIL);
			}
			if (getReturnCode() == PASS) {
				String domains = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomains(m_abri.getABRCode());
				log(ABR+" generating data using domains: "+domains+" chunk: "+m_iChunk+
					" days: "+m_iDays+" useSrch: "+m_bSearch);
				pdg = new CATLGPUBLSEOPDG(null, m_db, m_prof, domains);
				pdg.m_strEntityType = "LSEO";
				pdg.setUpdatedBy(ABR + getRevision() + strNow);
				pdg.setEntityItem(m_ei);
				pdg.setChunkSize(m_iChunk);
				pdg.setDoSearch(m_bSearch);
				if (m_iDays <= 0) {
					OPICMList attList = new OPICMList();
					pdg.executeAction(m_db, m_prof);
					log("CATLGPUBABR03 finish generating data");
        			println("<!-- "+pdg.getDebugInfo()+"-->");

					attList.put("CATLGPUBLSEOLASTRUN", "CATLGPUBLSEOLASTRUN=" + strNow);
					m_utility.updateAttribute(m_db, m_prof, m_ei, attList);
				} else {
					boolean bStop = false;
					int iD1 =0;
					String strEndTime;
					String strLastRunAtt = "CATLGPUBLSEOLASTRUN";
					String strLastRunTime = strCATLGPUBLSEOLASTRUN;
					log(ABR + " in using date range mode");
					println("<!--"+ABR + " in using date range mode-->");
					if (strLastRunTime.substring(0,4).equals("1980")) {
						strLastRunTime = m_utility.getEarliestTime(m_db, m_prof, "LSEO");
					}
        			pdg.setUseDateRange(true);
					pdg.setScanAll(false);  // set to false first

        			strEndTime = m_utility.getDate(strLastRunTime.substring(0, 10), m_iDays) + "-23.59.59.000000";
        			iD1 = m_utility.longDateCompare(strEndTime, strNow);
					if (iD1 == PDGUtility.LATER && !bStop) {
						log(ABR + " setting end time to now");
						println("<!-- "+ABR + " setting end time to now-->");
						strEndTime = strNow;
						bStop = true;
						iD1 = PDGUtility.EQUAL;
						pdg.setScanAll(true);
					}

					while (iD1 != PDGUtility.LATER) {
						OPICMList attList = new OPICMList();
						pdg.setStartTime(strLastRunTime);
						pdg.setEndTime(strEndTime);

						log(ABR + " last run time: " + strLastRunTime + ", end time: " + strEndTime);
						println("<!-- "+ABR + " last run time: " + strLastRunTime + ", end time: " + strEndTime+"-->");

						pdg.executeAction(m_db, m_prof);
        				println("<!-- "+pdg.getDebugInfo()+"-->");

						attList.put(strLastRunAtt, strLastRunAtt + "=" + strEndTime);
						m_utility.updateAttribute(m_db, m_prof, m_ei, attList);

						strLastRunTime=strEndTime;
						strEndTime = m_utility.getDate(strLastRunTime.substring(0, 10), m_iDays) + "-23.59.59.000000";

						iD1 = m_utility.longDateCompare(strEndTime, strNow);
						if (iD1 == PDGUtility.LATER && !bStop) {
							strEndTime = strNow;
							bStop = true;
							iD1 = PDGUtility.EQUAL;
							pdg.setScanAll(true);
						}
						m_utility.memory(true);
					}
         		}
			}// end all ok so far

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
            if (pdg!=null){
        		println("<!-- "+pdg.getDebugInfo()+"-->");
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
            if (pdg!=null){
        		println("<!-- "+pdg.getDebugInfo()+"-->");
			}
		}
       	finally {
			String strDgName = null;
			String msg =buildMessage(
				MSG_IAB2016I,
				new String[] { getABRDescription(), (getReturnCode() == PASS ? "Passed" : "Failed")});
      		println("<br /><b>"+msg+"</b>");
      		log(msg);
			// set DG title
			strDgName = getABRDescription() + ":"
				+ getEntityType()
				+ ":"
				+ getEntityID();
			if (strDgName.length() > I64) {
				strDgName = strDgName.substring(0, I64);
			}
			setDGTitle(strDgName);
			setDGRptName(ABR);

			// set DG submit string
			setDGString(getABRReturnCode());

			println(EACustom.getTOUDiv());

     	 	// Tack on the DGString
     		printDGSubmitString();
  		    //Stuff into report for subscription and notification
	        buildReportFooter();    // Print </html>

			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}
    	}
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
    return "Catalog Offering Publication For LSEO ABR";
  }

  /**
     * getRevision
     *
     * @return
     * @author Owner
     */
    public String getRevision() {
    	return "1.17";
  }

  /** removed because of jtest error: "Avoid hiding inherited "static" member methods. [OOP.AHSM]"
     * getVersion
     *
     * @return
     * @author Owner
     *
    public static String getVersion() {
    return ("CATLGPUBABR03.java,v 1.17 2008/01/30 19:39:17 wendy Exp");
  }*/

  /**
     * getABRVersion
     *
     * @return
     * @author Owner
     */
    public String getABRVersion() {
    	return "CATLGPUBABR03.java "+getRevision();
  }

	/******************************************
	 * readPropertyFile reads the property file aspect.properties
	 */
	private void readPropertyFile()  {
	    Properties properties = new Properties();
	    boolean bFoundProperties = true;
	    D.ebug("Reading catlgpubabr.properties");
	    try {
			File file = new File("./" + "catlgpubabr.properties");
			if ( !file.exists() || !file.canRead( ) ) {
				System.out.println("Can't read " + file.getAbsolutePath() );

				bFoundProperties = false;
			} else {
		    	properties.load(new FileInputStream(file));
			}
    	} catch( IOException e)   {
      		System.out.println("Error reading catlgpubabr.properties "+e);
      		bFoundProperties = false;
    	}

    	if (bFoundProperties) {
			m_iDays = Integer.parseInt(properties.getProperty("CATLGPUBABR03.DAYS", "0"));
			m_iChunk = Integer.parseInt(properties.getProperty("CATLGPUBABR03.CHUNK", "100"));
			m_bSearch = properties.getProperty("CATLGPUBABR03.SEARCH", "false").equalsIgnoreCase("true");
		}

  	} //readPropertyFile

}
