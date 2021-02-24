package COM.ibm.eannounce.abr.sg;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class CCINRPTSTATUS extends PokBaseABR {
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
	private static final String CCINPRTPATH = "ccinrptpath";
	private static final String CCININIPATH = "ccininipath";
	private static final String SFTPSCRPATH = "sftpscrpath";
	private static final String CCINATR = "CCINATR";
	private static final String CCIN_CODENAME = "CCIN_CODENAME";
	private static final String CCIN_INTERNALNAME = "CCIN_INTERNALNAME";
	private static final String FEATURECODE = "FEATURECODE";
	private static final String FC_COMNAME = "FC_COMNAME";	
	private static final String[] COLUMN_ARR = new String[] { CCINATR,
		CCIN_CODENAME, CCIN_INTERNALNAME, FEATURECODE, FC_COMNAME};
	
	// m_elist.getParentEntityGroup().getEntityItem(0)
	private final static String QUERY = "select * from OPICM.CCIN_FEATURE_V with ur";

	private void setFileName() throws IOException {
		// FILE_PREFIX=CCIN_
		String fileprefix = ABRServerProperties.getFilePrefix(m_abri
				.getABRCode());
		// ABRServerProperties.getOutputPath()
		// ABRServerProperties.get
		StringBuffer sb = new StringBuffer(fileprefix.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(dts + ".xls");
		dir = ABRServerProperties.getValue(m_abri.getABRCode(), CCINPRTPATH,
				"/Dgq");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		ffFileName = sb.toString();
		ffPathName = dir + ffFileName;
		// addDebug("ffPathName: " + ffPathName + " ffFileName: " + ffFileName);
	}

	public void execute_run() {
		// TODO Auto-generated method stub
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
				+ "<tr><th>Result: </th><td>{4}</td></tr>" + NEWLINE
				+ "</table>" + "<!-- {5} -->" + NEWLINE;
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
			setDGTitle("CCINRPTSTATUS report");
			setDGString(getABRReturnCode());
			setDGRptName("CCINRPTSTATUS"); // Set the report name
			setDGRptClass("CCINRPTSTATUS"); // Set the report class
		
			setFileName();
			creatFile = createExcel(getRecords(),ffPathName);
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
			sb.append(creatFile ? "generated the CCIN report file successful "
					: "generated the CCIN report file faild");
			sb.append(",");
			sb.append(sentFile ? "send the CCIN report file successful "
					: "sent the CCIN report file faild");
			if (!sentFile&&lineStr!=null)
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

	public List getRecords() throws MiddlewareException,Exception{

		List list = new ArrayList();
		Connection conn = m_db.getODS2Connection();
		//Connection conn = m_db.getPDHConnection();
		PreparedStatement ps = conn.prepareStatement(QUERY,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			String[] record = new String[6];
			record[0] = rs.getString(1);
			record[1] = rs.getString(2);
			record[2] = rs.getString(3);
			record[3] = rs.getString(4);
			record[4] = rs.getString(5);
			record[5] = rs.getString(6);
			list.add(record);
		}
		return list;
	}
	
	public static  boolean createExcel(List list, String filepath) {

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");    
        HSSFRow row = sheet.createRow((int) 0);
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

        HSSFCell cell = null;
        for (int i = 0; i < COLUMN_ARR.length; i++) {
            cell = row.createCell((short) i);
            cell.setCellValue(COLUMN_ARR[i]);
            cell.setCellStyle(style);
        }
        try {
        	for (int i=0;i<list.size();i++) {
        		row = sheet.createRow((int) i + 1);
        		String[] record = (String[]) list.get(i);
        		for (int j = 0; j < COLUMN_ARR.length; j++) {
        			row.createCell((short) j).setCellValue(record[j]);
        		}
                FileOutputStream fout = new FileOutputStream(filepath);
                wb.write(fout);
                fout.close();
            }
        	return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }      
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
		// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String cmd = ABRServerProperties.getValue(m_abri.getABRCode(),
				SFTPSCRPATH, null) + " -f " + fileName;
		String ccinipath = ABRServerProperties.getValue(m_abri.getABRCode(),
				CCININIPATH, null);
		if (ccinipath != null)
			cmd += " -i " + ccinipath;
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
		return "CCINRPTSTATUS";
	}

	/***********************************************
	 * Get the version
	 * 
	 * @return java.lang.String
	 */
	public String getABRVersion() {
		return "1.0";
	}
}

