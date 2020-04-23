// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.abr.sg;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import java.text.*;;

/**********************************************************************************
* GENSALESMANABR runs the PDG as an ABR to generate the Sales manual report
*
GENSALESMANABR_class=COM.ibm.eannounce.abr.sg.GENSALESMANABR
GENSALESMANABR_enabled=true
GENSALESMANABR_idler_class=A
GENSALESMANABR_keepfile=true
GENSALESMANABR_read_only=true
GENSALESMANABR_report_type=DGTYPE01
GENSALESMANABR_vename=EXTGENSALES

VE EXTGENSALES is not defined, it isn't needed, just the root PDGSM1 entity is needed here so a dummy VE works fine
*/
// GENSALESMANABR.java,v
// Revision 1.3  2007/02/10 00:34:28  wendy
// Always get rpt from pdg code
//
// Revision 1.2  2007/01/17 19:18:51  wendy
// Changed a method in PDG for CR0425066856/RQ1103063724
//
// Revision 1.1  2006/10/02 17:36:03  wendy
// Init for CR2440, convert sales manual rpt to PDG
//

public class GENSALESMANABR extends PokBaseABR
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";

    private static final char[] FOOL_JTEST = {'\n'};
    private static final String NEWLINE = new String(FOOL_JTEST);

    private static final int MAX_TITLE_LEN = 64;

    private MessageFormat msgf;
    private Object[] args = new String[10];

	/***********************************************
	* execute_run
	*/
	public void execute_run() {
		String dgName = "Generate Sales Manual";
		String results = "";
		try {
			GENSALESMANPDG thePdg;
			EntityItem pdgItem;
			// pull the extract
			start_ABRBuild();

			setReturnCode(PASS);

			pdgItem = m_elist.getParentEntityGroup().getEntityItem(0); // get root PDGSM1
			dgName = pdgItem.toString();
			if (dgName.length() > MAX_TITLE_LEN) {
				dgName = dgName.substring(0, MAX_TITLE_LEN);
			}

			//============= run the PDG to generate data ==========================================
			log("GENSALESMANABR generating data for "+pdgItem.getKey()+" "+dgName);
			thePdg = new GENSALESMANPDG(null,	m_db, m_prof, "GENSALESMANPDG");
			thePdg.setEntityItem(pdgItem);
			thePdg.executeAction(m_db, m_prof);
			results = getResults(pdgItem,thePdg);
			log("GENSALESMANABR finish generating data");
		} catch(SBRException sbrexc) {
			String errorMsg = sbrexc.toString();
            setReturnCode(FAIL);
            msgf = new MessageFormat("<pre>Error:<br />{0}</pre>");
            args[0] = errorMsg;
			results = getErrorHtml(dgName, msgf.format(args));
        	logError(errorMsg);
    	} catch(Throwable exc) {
			StringBuffer rptSb = new StringBuffer();
            java.io.StringWriter exBuf = new java.io.StringWriter();
            setReturnCode(FAIL);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            msgf = new MessageFormat("<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>");
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE);
            msgf = new MessageFormat("<pre>{0}</pre>");
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE);
			results = getErrorHtml(dgName, rptSb.toString());
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
    	} finally {
			// set DG title
			setDGTitle(dgName);
			setDGRptName(getShortClassName(getClass()));
			// make sure the lock is released
			if (!isReadOnly()) {
				clearSoftLock();
			}

			println(results); // Output the Report or any errors

			printDGSubmitString();
			buildReportFooter(); // Print </body></html>
		}
	}

	/***********************************************
	* get the report from the PDG
	*/
	private String getResults(EntityItem pdgItem,GENSALESMANPDG thePdg) {
		int bodyid = -1;
		// get value from PDG
		String results = thePdg.getSalesManualRpt(m_prof);

		// remove the closing body and html tags, need to add DG submit comment
		bodyid = results.indexOf("</body>");
		if (bodyid>0) {
			results = results.substring(0,bodyid);
		}else{
			if (results.length()==0){
            	logError("GENSALESMANABR "+pdgItem.getKey()+" AFPDGRESULT attribute should be set and is empty!");
			}
		}

		return results;
	}

	/***********************************************
	* put the error into AHE compliant html
	*/
	private String getErrorHtml(String dgName, String errorMsg) {
		StringBuffer sb = new StringBuffer();
        String HEADER = "<head>"+
            EACustom.getMetaTags(getDescription()) + NEWLINE +
            EACustom.getCSS() + NEWLINE +
            EACustom.getTitle("{0} {1}") + NEWLINE +
            "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
            EACustom.getMastheadDiv() + NEWLINE +
            "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE+
			"<p><b>Date: </b>{2}<br /><b>User: </b>{3} ({4})<br />"+
			"<b>email: </b>{5} <br /><b>Description: </b>{6}</p>"+NEWLINE+
            "<!-- {7} -->" + NEWLINE;
        msgf= new MessageFormat(HEADER);
        args[0] = getShortClassName(getClass());
        args[1] = dgName + " Failed";
		args[2] = getNow();
		args[3] = m_prof.getOPName();
		args[4] = m_prof.getRoleDescription();
		args[5] = m_prof.getEmailAddress();
		args[6] = getDescription();
		args[7] = getABRVersion();

		sb.append(EACustom.getDocTypeHtml()); //Output the doctype and html

        sb.append(msgf.format(args) + NEWLINE);
        sb.append(errorMsg + NEWLINE);
		sb.append(EACustom.getTOUDiv() + NEWLINE);

		return sb.toString();
	}

	/***********************************************
	* Get ABR description
	*
	* @return String
	*/
	public String getDescription() {
		return "Sales Manual Generation ABR.";
	}

	/***********************************************
	* getABRVersion
	*
	* @return String
	*/
	public String getABRVersion() {
		return "1.3";
	}
}
