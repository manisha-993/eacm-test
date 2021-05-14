package COM.ibm.eannounce.abr.sg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.PokUtils;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.LockPDHEntityException;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.UpdatePDHEntityException;
import COM.ibm.eannounce.abr.util.XLRow;
import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
import COM.ibm.eannounce.objects.EANAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class IBIRPTSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private String ffFileName = null;
	private String ffPathName = null;
	private String dir = null;
	private String lineStr = null;
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private Object[] args = new String[10];
	private static final String SUCCESS = "SUCCESS";
	private static final String FAILD = "FAILD";
	private static final String IBIPRTPATH = "ibiprtpath";
	private static final String IBIINIPATH = "ibiinipath";
	private static final String SFTPSCRPATH = "sftpscrpath";
	private static final String MACHTYPEATR = "MACHTYPEATR";
	private static final String MODELATR = "MODELATR";
	private static final String MKTGNAME = "MKTGNAME";
	private static final String INSTALL = "INSTALL";
	private static final String COFCAT = "COFCAT";
	private static final String COFSUBCAT = "COFSUBCAT";
	private static final String COFSUBGRP = "COFSUBGRP";
	private static final String ANNDATE = "ANNDATE";
	private static final String WITHDRAWDATE = "WITHDRAWDATE";
	private static final String WTHDRWEFFCTVDATE = "WTHDRWEFFCTVDATE";
	private static final String DIV = "DIV";
	private static final String STATUS = "STATUS";
	private static final String[] COLUMN_ARR = new String[] { MACHTYPEATR, MODELATR, MKTGNAME, INSTALL, COFCAT,
			COFSUBCAT, COFSUBGRP, ANNDATE, WITHDRAWDATE, WTHDRWEFFCTVDATE, DIV, STATUS };
	private static final Hashtable COLUMN_LENGTH;
	static {
		COLUMN_LENGTH = new Hashtable();
		COLUMN_LENGTH.put(MACHTYPEATR, "4");
		COLUMN_LENGTH.put(MODELATR, "3");
		COLUMN_LENGTH.put(MKTGNAME, "128");
		COLUMN_LENGTH.put(INSTALL, "128");
		COLUMN_LENGTH.put(COFCAT, "30");
		COLUMN_LENGTH.put(COFSUBCAT, "128");
		COLUMN_LENGTH.put(COFSUBGRP, "128");
		COLUMN_LENGTH.put(ANNDATE, "10");
		COLUMN_LENGTH.put(WITHDRAWDATE, "10");
		COLUMN_LENGTH.put(WTHDRWEFFCTVDATE, "10");
		COLUMN_LENGTH.put(DIV, "128");
		COLUMN_LENGTH.put(STATUS, "20");
	}
	// m_elist.getParentEntityGroup().getEntityItem(0)
	private final static String QUERY = "select m.machtypeatr,m.modelatr,m.mktgname,m.install,m.cofcat,m.cofsubcat,m.cofsubgrp,m.anndate,m.withdrawdate,m.WTHDRWEFFCTVDATE,S.div,m.status from price.model m join price.SGMNTACRNYM S on m.PRFTCTR=S.PRFTCTR where m.nlsid=1  and m.cofcat='Hardware' and m.status in ('Final','Ready for Review') and s.nlsid=1 and "
			+ "(S.DIV not in ('71','20','2P','2N','30','36 IBM eBusiness Hosting Services','46','8F','C3','G2','J6','K6 - IBM Services for Managed Applications','M3','MW','Q9') or S.div is null) and m.INSTALL='CE' with ur";

	private void setFileName() throws IOException {
		// FILE_PREFIX=EACM_TO_QSM_
		String fileprefix = ABRServerProperties.getFilePrefix(m_abri.getABRCode());
		// ABRServerProperties.getOutputPath()
		// ABRServerProperties.get
		StringBuffer sb = new StringBuffer(fileprefix.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".txt");
		dir = ABRServerProperties.getValue(m_abri.getABRCode(), IBIPRTPATH, "/Dgq");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		ffFileName = sb.toString();
		ffPathName = dir + ffFileName;
		// addDebug("ffPathName: " + ffPathName + " ffFileName: " + ffFileName);
	}

	public void execute_run() {
		// TODO Auto-generated method stub
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
				+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv() + NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
				+ NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Result: </th><td>{4}</td></tr>"
				+ NEWLINE + "</table>" + "<!-- {5} -->" + NEWLINE;
		MessageFormat msgf;
		String abrversion = "";
		Object[] args = new String[10];
		String header1 = "";
		boolean creatFile = true;
		boolean sentFile = true;

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			start_ABRBuild(false);
			setDGTitle("IBIRPTSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("IBIRPTSTATUS"); // Set the report name
			setDGRptClass("IBIRPTSTATUS"); // Set the report class
			generateFlatFile();
			sentFile = exeFtpShell(ffPathName);
			setReturnCode(PASS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// println(e.toString());
			setReturnCode(FAIL);
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = e.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + e.getMessage());
			logError(exBuf.getBuffer().toString());
			// was an error make sure user gets report
			setCreateDGEntity(true);
			creatFile = false;

		}

		// getReturnCode();
		finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			sb.append(creatFile ? "generated the IBI report file successful " : "generated the IBI report file faild");
			sb.append(",");
			sb.append(sentFile ? "send the IBI report file successful " : "sent the IBI report file faild");
			if (!sentFile)
				sb.append(lineStr.replaceFirst(FAILD, ""));
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();

			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();

			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}

	}

	private void generateFlatFile(/* EntityItem rootEntity */) throws IOException, MiddlewareException, SQLException {
		// generate file name
		setFileName();
		FileOutputStream fos = new FileOutputStream(ffPathName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");

		// build the text file
		String dtsDb = getNow(); // format this
		String date = dtsDb.substring(0, 10);
		String time = dtsDb.substring(11, 19);
		String sec = dtsDb.substring(19);
		time = time.replace('.', ':');
		String dts = date + " " + time + sec;

		Connection conn = m_db.getPDHConnection();
		PreparedStatement ps = conn.prepareStatement(QUERY, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();

		if (rs.last()) {
			int count = rs.getRow();
			wOut.write("EACM" + dts + count);
			wOut.write(NEW_LINE);
			// println("EACM" + dts + " " + count);
			rs.first();
		}
		do {

			for (int i = 0; i < COLUMN_ARR.length; i++) {

				wOut.write(getValue(rs.getString(COLUMN_ARR[i]), COLUMN_ARR[i]));
			}
			wOut.write(NEW_LINE);
			wOut.flush();
		} while (rs.next());
		wOut.flush();
	}

	protected String getValue(String columnValue, String columnNmae) {
		if (columnValue == null)
			columnValue = "";
		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnNameLength = Integer.parseInt(COLUMN_LENGTH.get(columnNmae).toString());
		if (columnValueLength == columnNameLength)
			return columnValue;
		if (columnValueLength > columnNameLength)
			return columnValue.substring(0, columnNameLength);

		return columnValue + getBlank(columnNameLength - columnValueLength);
	}

	protected String getBlank(int count) {
		StringBuffer sb = new StringBuffer();
		while (count > 0) {
			sb.append(" ");
			count--;
		}
		return sb.toString();
	}

	public boolean exeFtpShell(String fileName) {
		// String cmd =
		// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log
		// testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String cmd = ABRServerProperties.getValue(m_abri.getABRCode(), SFTPSCRPATH, null) + " -f " + fileName;
		String ibiinipath = ABRServerProperties.getValue(m_abri.getABRCode(), IBIINIPATH, null);
		if (ibiinipath != null)
			cmd += " -i " + ibiinipath;
		Runtime run = Runtime.getRuntime();
		String result = "";
		BufferedReader br = null;
		BufferedInputStream in = null;
		try {
			Process p = run.exec(cmd);
			if (p.waitFor() != 0) {
				return false;
			}
			in = new BufferedInputStream(p.getInputStream());
			br = new BufferedReader(new InputStreamReader(in));
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
				if (lineStr.indexOf(FAILD) > -1) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result == null ? false : true;
	}

	public String getDescription() {
		return "IBIRPTSTATUS";
	}

	/***********************************************
	 * Get the version
	 * 
	 * @return java.lang.String
	 */
	public String getABRVersion() {
		return "1.14";
	}
}
