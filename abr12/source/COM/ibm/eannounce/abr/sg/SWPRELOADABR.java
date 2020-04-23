package COM.ibm.eannounce.abr.sg;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Hashtable;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.eannounce.abr.util.StringUtils;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class SWPRELOADABR extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private String ffFileName = null;
	private String ffPathName = null;
	private String dir = null;
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private static final String PRTPATH = "prtpath";

	private static final String MACHTYPEATR = "MACHTYPEATR";
	private static final String MODELATR = "MODELATR";
	private static final String MKTGNAME = "MKTGNAME";
	private static final String COFCAT = "COFCAT";
	private static final String COFSUBCAT = "COFSUBCAT";
	private static final String COFGRP = "COFGRP";
	private static final String COFSUBGRP = "COFSUBGRP";
	private static final String PRELOADSWINDC = "PRELOADSWINDC";
	private static final String ANNDATE = "ANNDATE";
	private static final String WITHDRAWDATE = "WITHDRAWDATE";
	boolean hasData = false;
	private static final String[] COLUMN_ARR = new String[] { MACHTYPEATR,
			MODELATR, MKTGNAME, COFCAT, COFSUBCAT, COFGRP, COFSUBGRP,
			PRELOADSWINDC, ANNDATE, WITHDRAWDATE };
	private static final Hashtable COLUMN_LENGTH;
	static {
		COLUMN_LENGTH = new Hashtable();
		COLUMN_LENGTH.put(MACHTYPEATR, "4");
		COLUMN_LENGTH.put(MODELATR, "3");
		COLUMN_LENGTH.put(MKTGNAME, "260");
		COLUMN_LENGTH.put(COFCAT, "10");
		COLUMN_LENGTH.put(COFSUBCAT, "50");
		COLUMN_LENGTH.put(COFGRP, "10");
		COLUMN_LENGTH.put(COFSUBGRP, "35");
		COLUMN_LENGTH.put(PRELOADSWINDC, "5");
		COLUMN_LENGTH.put(ANNDATE, "10");
		COLUMN_LENGTH.put(WITHDRAWDATE, "10");
	}

	// private final static String QUERY =
	// "Select machtypeatr,modelatr,mktgname,cofcat,cofsubcat,cofgrp,cofsubgrp,PRELOADSWINDC,ANNDATE,WITHDRAWDATE from price.model where nlsid=1";
	private final static String QUERY = "select * from price.VPRELOADEDSW";

	/*
	 * The Files will be named as follows: 1. 'SWPRELOADABR' 2. The DB2 DTS for
	 * T2 with spaces and special characters replaced with an underscore 3.
	 * '.csv'
	 */
	private void setFileName() {
		// FILE_PREFIX=SWPRELOADABR_
		String fileprefix = ABRServerProperties.getFilePrefix(m_abri
				.getABRCode());
		// ABRServerProperties.getOutputPath()
		StringBuffer sb = new StringBuffer(fileprefix.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".csv");
		dir = ABRServerProperties
				.getValue(m_abri.getABRCode(), PRTPATH, "/Dgq");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		ffFileName = sb.toString();
		ffPathName = dir + ffFileName;
		// addDebug("**** mmiotto ffPathName: " + ffPathName + " ffFileName: " +
		// ffFileName);
	}

	public void execute_run() {
		addDebug("---------------ABR started run ");
		String HEADER = "<head>"
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
		String HEADER2 = "<table>" + NEWLINE
				+ "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE
				+ "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE
				+ "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE
				+ "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;

		String header1 = "";
		boolean creatFile = true;

		MessageFormat msgf;
		String abrversion = "";

		Object[] args = new String[10];

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			start_ABRBuild(false);
			setDGTitle("SWPRELOADABR report");
			setDGString(getABRReturnCode());
			setDGRptName("SWPRELOADABR"); // Set the report name
			setDGRptClass("SWPRELOADABR"); // Set the report class
			// Default set to pass
			setReturnCode(PASS);
			generateFlatFile();
			sendMail(ffPathName);

		} catch (Exception e) {
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
			
		} finally {
			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			sb.append(creatFile ? "generated the SWPRELOADABR report file successful "
					: "generated the SWPRELOADABR report file faild");
			sb.append(",");
			if (!hasData)
				sb.append("No Such Data!");
			else
				sb.append("Generated the SWPRELOADABR report file successful ");
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

	private void generateFlatFile(/* EntityItem rootEntity */)
			throws IOException, MiddlewareException, SQLException {
		// generate file name
		setFileName();
		FileOutputStream fos = new FileOutputStream(ffPathName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");

		// build the csv file
		String dtsDb = getNow(); // format this
		String date = dtsDb.substring(0, 10);
		String time = dtsDb.substring(11, 19);
		String sec = dtsDb.substring(19);
		time = time.replace('.', ':');
		String dts = date + " " + time + sec;

		Connection conn = m_db.getPDHConnection();
		PreparedStatement ps = conn.prepareStatement(QUERY,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();

		if (rs.last()) {
			int count = rs.getRow();
			wOut.write("EACM" + dts + " " + count);
			wOut.write(NEW_LINE);
			rs.first();
		}
		
		//Write column name
		for (int i = 0; i < COLUMN_ARR.length; i++) {
			wOut.write(COLUMN_ARR[i]);
			wOut.write(",");
		}
		wOut.write(NEW_LINE);

		if (rs.getRow() > 0) {
			hasData = true;
			do {
				for (int i = 0; i < COLUMN_ARR.length; i++) {
					if (i < 2) {
						wOut.write("\t");
					}
					wOut.write(getValue(rs.getString(COLUMN_ARR[i]),
							COLUMN_ARR[i]));
					wOut.write(",");
				}
				wOut.write(NEW_LINE);
				wOut.flush();
			} while (rs.next());
		}
		wOut.flush();

	}

	protected String getValue(String columnValue, String columnNmae) {
		if (columnValue == null)
			columnValue = "";
		
		//Deal with special characters
		if (columnValue.indexOf(",") > -1) {
			if (columnValue.indexOf("\"") > -1) {
				columnValue = StringUtils.replace(columnValue, "\"", "\"\"");
			}
			columnValue = "\"" + columnValue + "\"";
		}

		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnNameLength = Integer.parseInt(COLUMN_LENGTH.get(columnNmae)
				.toString());
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

	public String getDescription() {
		return "SWPRELOADABR";
	}

	public String getABRVersion() {
		return "1.1";
	}

	// Send mail
	public void sendMail(String file) throws Exception {

		FileInputStream fisBlob = null;
		try {
			fisBlob = new FileInputStream(file);
			int iSize = fisBlob.available();
			byte[] baBlob = new byte[iSize];
			fisBlob.read(baBlob);
			setAttachByteForDG(baBlob);
			getABRItem().setFileExtension("csv");
			addDebug("Sending mail for file " + file);
		} catch (IOException e) {
			addDebug("sendMail IO Exception");
		}
		finally {
			if (fisBlob != null) {
				fisBlob.close();
			}
		}

	}

	protected void addDebug(String msg) {
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
	}
}