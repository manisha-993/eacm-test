//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2015  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.sg.rfc;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import COM.ibm.eannounce.abr.util.ABRUtil;
import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

import com.ibm.transform.oim.eacm.util.PokUtils;

public class RFCABRSTATUS extends PokBaseABR {
	
	protected static final String NEWLINE = "\n";
	
	private StringBuffer rptSb = new StringBuffer();
	private static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("RFCABRSTATUS");

	private ResourceBundle rsBundle;
	private RfcAbrFactory rfcAbrFactory;
	private String navName;
	private Object[] args = new String[10];
	private String t2DTS = "&nbsp;";  // T2
	private MessageFormat msgf;

	public RFCABRSTATUS() {
		rfcAbrFactory = new RfcAbrFactory();
		rfcAbrFactory.setRfcAbrStatus(this);
	}

	/**
	 * Execute ABR.
	 */
	public void execute_run() {
		long startTime = System.currentTimeMillis();
		
		try {
			start_ABRBuild(false); // don't pull VE yet
			
			// get properties file for the base class
			rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

			// Default set to pass
			setReturnCode(PASS);
			
			// get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
			m_elist = m_db.getEntityList(m_prof, new ExtractActionItem(null, m_db, m_prof, "dummy"), 
					new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });
				
			// get root ANNOUNCEMENT from VE
			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
						
			navName = getNavigationName(rootEntity);
			
			try {
				RfcAbr rfcAbr = rfcAbrFactory.getRfcTypeAbr(getEntityType());
				rfcAbr.processThis();
				addOutput("Promoted " + rootEntity.getKey() + " successfully");
			} catch (RfcAbrException e) {
				e.printStackTrace();
				addOutput(e.getMessage());
				setReturnCode(FAIL);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		} catch (Exception e) {
			StringWriter exBuf = new StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(INTERNAL_ERROR);
            e.printStackTrace(new PrintWriter(exBuf));
            // Put exception into document
            args[0] = e.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE);
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE);
            logError("Exception: "+e.getMessage());
            logError(exBuf.getBuffer().toString());
			e.printStackTrace();
		}  finally {
			if (t2DTS.equals("&nbsp;")){
            	t2DTS= getNow();
            }
			setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if (!isReadOnly()) {
                clearSoftLock();
            }
            addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - startTime));
		}

		// build report
		buildReport();
		// release memory
		
	}
	
	public String getDescription() {
		return getClass().getName();
	}

	public String getABRVersion() {
		return "";
	}
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * add msg to report output
	 */
	protected void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}
	
	/**
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }
    
    /**
     * add debug msg as comment to report output
     */
	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= DEBUG_LVL) {
			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
		}
	}
	
	private void buildReport() {
		// Print everything up to </html>
		// Insert Header into beginning of report
		println(EACustom.getDocTypeHtml());
		buildHeader();
		println(rptSb.toString());
		printDGSubmitString();
		println(EACustom.getTOUDiv());
        buildReportFooter();
	}
	
	private void buildHeader() {
		String HEADER1 = "<head>"
				+ EACustom.getMetaTags(getDescription())
				+ NEWLINE
				+ EACustom.getCSS()
				+ NEWLINE
				+ EACustom.getTitle("{0} {1}")
				+ NEWLINE
				+ "</head>"
				+ NEWLINE
				+ "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv()
				+ NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>"
				+ NEWLINE;
		msgf = new MessageFormat(HEADER1);
		args[0] = getShortClassName(getClass());
		args[1] = navName;
		String header1 = msgf.format(args);
		println(header1);
		
		String HEADER2 = "<table>"+NEWLINE +
		"<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
		"<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
		"<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
		"<tr><th>Date/Time: </th><td>{3}</td></tr>"+NEWLINE +
		"<tr><th>Action Taken: </th><td>{4}</td></tr>"+NEWLINE+
		"</table>"+NEWLINE+
		"<!-- {5} -->" + NEWLINE;
		msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = t2DTS;
		args[4] = "RFC ABR<br/>" + (getReturnCode() == PASS ? "Pass" : "Failed");
		args[5] = getABRVersion();
		String header2 = msgf.format(args);		
		println(header2);
	}
	
	
	/**
	 * Get Name based on navigation attributes
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
		EANList metaList = eg.getMetaAttribute(); // iterator does not maintain navigate order
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			navName.append(" ");
		}
		return navName.toString();
	}

}
