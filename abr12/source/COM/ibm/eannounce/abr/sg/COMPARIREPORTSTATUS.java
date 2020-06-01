package COM.ibm.eannounce.abr.sg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import COM.ibm.eannounce.abr.util.EACustom;
import COM.ibm.eannounce.abr.util.PokBaseABR;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

public class COMPARIREPORTSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private String reportFileName = null;
	private String reportDir = null;

	private String dataDir = null;
	private String backuptDir = null;
	private String lineStr = null;
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);
	private Object[] args = new String[10];
	private static final String SUCCESS = "SUCCESS";
	private static final String FAILD = "FAILD";
	private static final String DATAPATH = "_datapath";
	private static final String BACKUPPATH = "_backuppath";
	private static final String REPORTPATH = "_reportpath";
	private String dataPath = null;
	// m_elist.getParentEntityGroup().getEntityItem(0)
	private String annNumber = null;
	int rows = 0;
	int maxlen = 0;
	private static Map sheetMap = null;
	private Map sheetDataMap = null;
	private Map dbDataMap = null;
	public static final String MODEL = "MODEL";
	public static final String FEATURE = "FEATURE";
	public static final String MCONV = "MCONV";
	public static final String FCONV = "FCONV";
	public static final String UPDATEFC = "UPDATEFC";
	public static Map mappingMap = null;
	public static Map keysMap = null;

	private String annDate = null;
	private String annWDDate = null;
	private String gaDate = null;
	// public Map countryMap = null;
	HSSFWorkbook workbook = new HSSFWorkbook();
	HSSFSheet sheet = workbook.createSheet("Comparison Result");
	private String[] sheets = new String[] { MODEL, FEATURE, UPDATEFC, MCONV, FCONV };
	Map map = new HashMap();
	static {
		keysMap = new HashMap();
		List list = new ArrayList();
		list.add("MT");
		list.add("Model");
		keysMap.put(MODEL, list);
		list = new ArrayList();
		list.add("MTM");
		list.add("Feature");
		keysMap.put(FEATURE, list);
		list = new ArrayList();
		list.add("MTM");
		list.add("Feature");
		keysMap.put(UPDATEFC, list);
		list = new ArrayList();
		list.add("From MT");
		list.add("From Model");
		list.add("To MT");
		list.add("To Model");
		keysMap.put(MCONV, list);
		list = new ArrayList();
		list.add("From MT");
		list.add("From Model");
		list.add("To MT");
		list.add("To Model");
		list.add("From Feature");
		list.add("To Feature");
		list.add("From FC");
		list.add("To FC");
		keysMap.put(FCONV, list);

		sheetMap = new HashMap();
		sheetMap.put("Model data", MODEL);
		sheetMap.put("Model Data", MODEL);
		sheetMap.put("Updates to FCs", UPDATEFC);
		sheetMap.put("Feature data", FEATURE);
		sheetMap.put("Feature Data", FEATURE);
		// Feature Data
		sheetMap.put("MConv", MCONV);
		// Model Conv
		// Model Conv
		sheetMap.put("Model Conv", MCONV);
		sheetMap.put("FConv", FCONV);
		// FC Conv
		// FC Conv
		sheetMap.put("FC Conv", FCONV);

		// ---------------------
		mappingMap = new HashMap();
		Map modelMap = new HashMap();
		mappingMap.put(MODEL, modelMap);
		modelMap.put("MT", "MACHTYPEATR");
		modelMap.put("Model", "MODELATR");
		// modelMap.put("GEO/Country restriction", "COUNTRYLIST");
		// modelMap.put("GA Date (if different)", "EFFECTIVEDATE");
		modelMap.put("Marketing Name", "MKTGNAME");
		modelMap.put("Machine type name", "MODMKTGDESC");
		modelMap.put("Price File Name", "INVNAME");
		modelMap.put("Profit Center", "PRFTCTR");
		modelMap.put("BH Prod Hier", "BHPRODHIERCD");
		modelMap.put("BH AAG", "BHACCTASGNGRP");
		modelMap.put("Priced", "PRCINDC");
		modelMap.put("Special Bid", "SPECBID");
		modelMap.put("Order Code", "MODELORDERCODE");
		modelMap.put("Customer Setup", "INSTALL");
		modelMap.put("LIC", "LICNSINTERCD");
		modelMap.put("MLC", "MACHLVLCNTRL");

		modelMap.put("GA Date", "GENAVAILDATE");
		modelMap.put("Ann date", "ANNDATE");
		modelMap.put("Ann WD date", "WITHDRAWDATE");

		// modelMap.put("Warranty Master ID","
		// ");??????????????????????????????????????/
		modelMap.put("Product ID", "PRODID");
		modelMap.put("IBM Global Financing", "IBMCREDIT");
		modelMap.put("ICR Category", "ICRCATEGORY");
		modelMap.put("Volume Discount Eligible (IVO)", "VOLUMEDISCOUNTELIG");
		modelMap.put("Function Class", "FUNCCLS");
		modelMap.put("Education Purchase Eligibility", "EDUCPURCHELIG");
		modelMap.put("Integrated Product", "INTEGRATEDMODEL");
		modelMap.put("Plan of Manufacture", "PLNTOFMFR");
		modelMap.put("Graduated Charge", "GRADUATEDCHARGE");
		modelMap.put("Annual Maintenance (Maint. Billing period)", "ANNUALMAINT");
		modelMap.put("EMEA Brand Code", "EMEABRANDCD");
		modelMap.put("IBM Hourly Service Rate Classification", "CECSPRODKEY");
		// modelMap.put("Primary Brand Code"," ");
		// modelMap.put("Product Family Code"," ");
		modelMap.put("SW preload", "PRELOADSWINDC");
		modelMap.put("Machine Rate Category", "MACHRATECATG");
		modelMap.put("Maintenance Billing Indicator", "MAINTANNBILLELIGINDC");
		modelMap.put("No Charge Maintenance", "NOCHRGMAINTINDC");
		modelMap.put("CE / CSC Product Key", "CECSPRODKEY");
		modelMap.put("Product Support Code", "PRODSUPRTCD");
		modelMap.put("Retain Indicator", "RETANINDC");
		modelMap.put("System Identification Unit", "SYSIDUNIT");
		modelMap.put("WWOCCODE (GBT)", "WWOCCODE");
		// modelMap.put("Phantom Model"," ");
		modelMap.put("UNSPSC", "UNSPSCCD");
		modelMap.put("UNSPSC UOM", "UNSPSCCDUOM");
		modelMap.put("Planning Relevant", "PLANRELEVANT");
		modelMap.put("Project Code Name", "PROJCDNAM");
		modelMap.put("Category", "COFCAT");
		modelMap.put("Subcategory", "COFSUBCAT");
		modelMap.put("Group", "COFGRP");
		modelMap.put("Subgroup", "COFSUBGRP");
		modelMap.put("Comp Dev Cat", "COMPATDVCCAT");
		modelMap.put("Comp Dev Subcat", "COMPATDVCSUBCAT");
		modelMap.put("Internal Name", "INTERNALNAME");
		modelMap.put("WD Effect Date", "WTHDRWEFFCTVDATE");

		Map featureMap = new HashMap();
		mappingMap.put(FEATURE, featureMap);

		featureMap.put("MTM", "MTM");
		featureMap.put("Feature", "FEATURECODE");
		featureMap.put("GEO/Country restriction", "COUNTRYLIST");
		featureMap.put("GA date (if different)", "EFFECTIVEDATE");
		featureMap.put("HW Feat Cat", "HWFCCAT");
		featureMap.put("HW Feat Subcat", "HWFCSUBCAT");
		featureMap.put("Price File Name (28 chars. Max)", "INVNAME");
		featureMap.put("Marketing Name", "MKTGNAME");
		featureMap.put("Maintenance Price", "MAINTPRICE");
		featureMap.put("Priced", "PRICEDFEATURE");
		featureMap.put("FC Marketing Description", "FCMKTGDESC");
		featureMap.put("Ordercode", "ORDERCODE");
		featureMap.put("Customer Setup", "INSTALL");
		featureMap.put("Returned Parts MES", "RETURNEDPARTS");
		featureMap.put("Initial Order Max", "INITORDERMAX");
		featureMap.put("System Max", "SYSTEMMAX");
		featureMap.put("System Min", "SYSTEMMIN");
		featureMap.put("Prereq", "PREREQ");
		featureMap.put("Coreq", "COREQUISITE");
		featureMap.put("Limitations", "LMTATION");
		featureMap.put("Compatibility", "COMPATIBILITY");
		featureMap.put("Comments", "COMMENTS");
		featureMap.put("Cable Order", "CBLORD");
		featureMap.put("Configurator Flag Override", "CONFIGURATORFLAG");
		featureMap.put("Bulk MES", "BULKMESINDC");
		featureMap.put("Warranty", "WARRSVCCOVR");
		// featureMap.put("WD Effect Date (if different)", "EFFECTIVEDATE");

		// GA date GENAVAILDATE,ANNDATE,WTHDRWEFFCTVDATE,WITHDRAWDATE
		//
		featureMap.put("GA date", "GENAVAILDATE");
		featureMap.put("Ann date", "ANNDATE");
		featureMap.put("WD Effect Date", "WTHDRWEFFCTVDATE");
		featureMap.put("Ann WD date", "WITHDRAWDATE");

		Map updataMap = new HashMap();
		mappingMap.put(UPDATEFC, updataMap);
		updataMap.put("MTM", "MTM");
		updataMap.put("Feature", "FEATURECODE");
		updataMap.put("Returned Parts MES", "RETURNEDPARTS");
		updataMap.put("Initial Order Max", "INITORDERMAX");
		updataMap.put("System Max", "SYSTEMMAX");
		updataMap.put("System Min", "SYSTEMMIN");
		updataMap.put("Prereq", "PREREQ");
		updataMap.put("Coreq", "COREQUISITE");
		updataMap.put("Limitations", "LMTATION");
		updataMap.put("Compatibility", "COMPATIBILITY");
		updataMap.put("Comment", "COMMENTS");
		updataMap.put("Cable Order", "CBLORD");
		updataMap.put("Bulk MES", "BULKMESINDC");

		Map fconvMap = new HashMap();

		fconvMap.put("From MT", "FROMMACHTYPE");
		fconvMap.put("From Model", "FROMMODEL");
		fconvMap.put("From Feature", "FROMFEATURECODE");
		fconvMap.put("From FC", "FROMFEATURECODE");
		fconvMap.put("To MT", "TOMACHTYPE");
		fconvMap.put("To Model", "TOMODEL");
		fconvMap.put("To Feature", "TOFEATURECODE");
		fconvMap.put("To FC", "TOFEATURECODE");
		fconvMap.put("Feature Transaction Category", "FTCAT");
		fconvMap.put("Returned Parts MES", "RETURNEDPARTS");
		fconvMap.put("Zero Price", "ZEROPRICE");
		fconvMap.put("WD Effect Date", "WTHDRWEFFCTVDATE");
		// WITHDRAWDATE,ANNDATE,GENAVAILDATE
		fconvMap.put("Ann WD date", "WITHDRAWDATE");
		fconvMap.put("Ann date", "ANNDATE");
		fconvMap.put("GA date", "GENAVAILDATE");
		mappingMap.put(FCONV, fconvMap);
		Map mconvMap = new HashMap();
		mappingMap.put(MCONV, mconvMap);
		mconvMap.put("From MT", "FROMMACHTYPE");
		mconvMap.put("From Model", "FROMMODEL");
		mconvMap.put("To MT", "TOMACHTYPE");
		mconvMap.put("To Model", "TOMODEL");
		mconvMap.put("Returned Parts MES", "RETURNEDPARTS");
		mconvMap.put("WD Effect Date", "WTHDRWEFFCTVDATE");
		// WTHDRWEFFCTVDATE,WITHDRAWDATE,ANNDATE,GENAVAILDATE
		mconvMap.put("WD Effect Date", "WTHDRWEFFCTVDATE");
		mconvMap.put("Ann WD date", "WITHDRAWDATE");
		mconvMap.put("Ann date", "ANNDATE");
		mconvMap.put("GA date", "GENAVAILDATE");

	}

	private void setFileName() throws IOException {
		// FILE_PREFIX=EACM_TO_QSM_

		reportDir = ABRServerProperties.getValue(m_abri.getABRCode(), REPORTPATH, "/Dgq");
		if (!reportDir.endsWith("/")) {
			reportDir = reportDir + "/";
		}
		dataDir = ABRServerProperties.getValue(m_abri.getABRCode(), DATAPATH, "/Dgq");
		if (!reportDir.endsWith("/")) {
			reportDir = reportDir + "/";
		}
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		backuptDir = ABRServerProperties.getValue(m_abri.getABRCode(), BACKUPPATH, "/Dgq");
		reportFileName = reportDir + "dbdata" + dts + ".xls";
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

		dbDataMap = new HashMap();
		sheetDataMap = new HashMap();

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			start_ABRBuild(false);
			setDGTitle(getDescription() + " report");
			setDGString(getABRReturnCode());
			setDGRptName(getDescription()); // Set the report name
			setDGRptClass(getDescription()); // Set the report class
			// test();
			generateFlatFile();
			sendMail(reportFileName);
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
			sb.append(creatFile ? "generated the COMPARIREPORTSTATUS report file successful "
					: "generated the  COMPARIREPORTSTATUS file faild");
			sb.append(",");
			sb.append(sentFile ? "send the COMPARIREPORTSTATUS report file successful "
					: "sent the COMPARIREPORTSTATUS report file faild");
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

	private void test(/* EntityItem rootEntity */) throws IOException, MiddlewareException, SQLException {
		// generate file name
		// setFileName();
		sheetDataMap = new HashMap();
		backuptDir = "C:\\Users\\JianBoXu\\Desktop\\test\\backup\\";
		dataDir = "C:\\Users\\JianBoXu\\Desktop\\test\\data\\";
		reportFileName = "C:\\Users\\JianBoXu\\Desktop\\test\\report\\report.xls";
		FileOutputStream fos = new FileOutputStream(reportFileName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");
		File file = new File(dataDir);
		Pattern pattern = Pattern.compile("(\\d{5})");
		if (file != null && file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				Matcher matcher = pattern.matcher(files[i].getName());
				if (matcher.find()) {
					annNumber = matcher.group(1);
					// loadDateFormDB();
					testData();
					loadDataFromExcel(files[i]);

					String report = compare();
					// wOut.write(report);
					File tFile = new File(backuptDir + files[i].getName());
					files[i].renameTo(tFile);
				}
			}

		}

		// m_db.getODS2Connection();
		// build the text file
		workbook.write(fos);
		wOut.write(NEW_LINE);
		wOut.flush();

		wOut.flush();
		System.out.println("DONE!");
	}

	private void generateFlatFile(/* EntityItem rootEntity */) throws IOException, MiddlewareException, SQLException {
		// generate file name
		setFileName();
		FileOutputStream fos = new FileOutputStream(reportFileName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");
		File file = new File(dataDir);
		Pattern pattern = Pattern.compile("(\\d{5})");
		if (file != null && file.isDirectory()) {
			File[] files = file.listFiles();
			addDebug("File number:" + files.length);
			for (int i = 0; i < files.length; i++) {
				addDebug("processing:" + files[i]);
				Matcher matcher = pattern.matcher(files[i].getName());
				if (matcher.find()) {
					annNumber = matcher.group(1);
					loadDateFormDB();
					loadDataFromExcel(files[i]);
					String report = compare();
					// wOut.write(report);
					File tFile = new File(backuptDir + files[i].getName());
					files[i].renameTo(tFile);
					addDebug("processed:" + files[i]);
				}
			}

		}

		// m_db.getODS2Connection();
		// build the text file
		workbook.write(fos);
		wOut.write(NEW_LINE);
		wOut.flush();

		wOut.flush();
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
		return "COMPARIREPORTSTATUS";
	}

	public String compare() {

		StringBuffer report = new StringBuffer();
		HSSFRow row = sheet.createRow(rows++);
		HSSFCellStyle yellow = workbook.createCellStyle();
		yellow.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		yellow.setFillForegroundColor(HSSFColor.YELLOW.index);
		HSSFCellStyle green = workbook.createCellStyle();
		green.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		green.setFillForegroundColor(HSSFColor.GREEN.index);
		HSSFCellStyle red = workbook.createCellStyle();
		red.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		red.setFillForegroundColor(HSSFColor.RED.index);
		HSSFCellStyle blue = workbook.createCellStyle();
		blue.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		blue.setFillForegroundColor(HSSFColor.BLUE.index);
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setCellValue("AnnNumber:" + annNumber);
		cell.setCellStyle(yellow);
		for(int s=1;s<maxlen;s++){
			 row.createCell((short) s).setCellStyle(yellow);
		}
		// report.append("AnnNumber:" + annNumber + "\r\n");
		for (int i = 0; i < sheets.length; i++) {
			String[][] sheetData = (String[][]) sheetDataMap.get(sheets[i]);
			String[][] dbData = (String[][]) dbDataMap.get(sheets[i]);
			Map mapping = (Map) mappingMap.get(sheets[i]);
			Map sheetIndexMap = new HashMap();
			Map mapingIndexMap = new HashMap();
			// set the mapping between excel data and db data
			/*
			 * addDebug("sheets[i]:"+sheets[i]);
			 * addDebug(String.valueOf(sheetData == null || dbData == null));
			 * addDebug(String.valueOf("sheetData:" +sheetData));
			 * addDebug(String.valueOf("dbData:" +dbData));
			 */
			if (sheetData == null || dbData == null)
				continue;

			report.append("Type:" + sheets[i] + "\r\n");
			row = sheet.createRow(rows++);
			row.createCell((short) 0).setCellValue("Type:" + sheets[i]);
			int col = 0;
			row = sheet.createRow(rows++);
			// find the mapping between sheetdata and dbdata
			for (int l = 0; l < sheetData[0].length; l++) {
				String sheetCol = sheetData[0][l];
				if (sheetCol == null || sheetCol.trim().equals("") || sheetCol.contains("Action"))
					continue;
				report.append(sheetCol);
				// row = sheet.createRow(rows++);

				row.createCell((short) col++).setCellValue(sheetCol);
				if (l == sheetData[0].length - 1) {
					row.createCell((short) col++).setCellValue("Result");
					report.append(",Result\r\n");
				} /*
					 * else { report.append(","); }
					 */
				sheetIndexMap.put(sheetCol, l + "");
				for (int m = 0; m < dbData[0].length; m++) {
					String dbCol = dbData[0][m];
					if (dbCol == null || dbCol.trim().equals(""))
						continue;
					if (dbCol.trim().equals(mapping.get(sheetCol))) {
						mapingIndexMap.put(sheetCol, m + "");
						break;
					}
				}

			}

			List keyList = (List) keysMap.get(sheets[i]);
			// start to compare
			// Map keydata = new HashMap();

			// ---------------------

			// loop for sheet data
			for (int l = 1; l < sheetData.length; l++) {

				boolean allNull = true;
				// check if all the data is null
				for (int j = 0; j < sheetData[l].length; j++) {
					if (sheetData[l][j] != null && !sheetData[l][j].trim().equals("")) {
						allNull = false;
						break;
					}
				}
				if (sheets[i].equals(MODEL))
					addDebug("l:" + l);

				if (allNull)
					continue;

				row = sheet.createRow(rows++);
				col = 0;

				for (int m = 1; m < dbData.length; m++) {

					int c = 0;
					int t = 0;
					if (sheets[i].equals(MODEL))
						addDebug("m:" + m);
					// search the data in dbData
					for (Iterator iterator = keyList.iterator(); iterator.hasNext();) {

						String key = iterator.next().toString();
						if (sheetIndexMap.get(key) == null)
							continue;
						int index = Integer.parseInt(sheetIndexMap.get(key).toString());
						int dbindex = Integer.parseInt(mapingIndexMap.get(key).toString());

						if (sheetData[l][index] != null && !sheetData[l][index].trim().equals("")) {
							// keydata.put(key, sheetData[l][index]);

							String sd = sheetData[l][index];
							String dd = dbData[m][dbindex];
							t++;
							if (sd != null && dd != null && !sd.trim().equals("") && sd.trim().equals(dd.trim())) {
								c++;
							}
						}

					}

					if (t > 0 && t == c) {
						// search the right data according to the key
						// find the data
						boolean match = true;
						String mismatchStr = null;

						for (int j = 1; j < sheetData[0].length; j++) {
							// if the sheet data is blank will ingnore to
							// compare
							/*
							 * if (sheets[i].equals(MODEL)) {
							 * addDebug("sheetData[0][j]:" +sheetData[0][j]); }
							 * if(sheetData[0][j].equals("WWOCCODE (GBT)")){
							 * addDebug("sheetData[0][j]:" +sheetData[0][j]); }
							 */
							if (sheetData[0][j] == null || sheetData[0][j].trim().equals("")) {

								continue;
							}

							String data = sheetData[l][j];

							if (mapingIndexMap.get(sheetData[0][j]) == null) {
								if (sheets[i].equals(MODEL)) {
									addDebug(sheetData[0][j] + ":all null:" + col);
								}
								row.createCell((short) col++).setCellValue(data == null ? "" : data + "");
								report.append(data == null ? "," : data + ",");
								continue;
							}
							int index = Integer.parseInt(mapingIndexMap.get(sheetData[0][j]).toString());
							String dbdata = dbData[m][index];

							if (sheets[i].equals(MODEL)) {
								addDebug("Compare:" + "Data:" + sheetData[0][j] + ":" + data + " dbData:"
										+ dbData[m][index] + ":" + dbdata);
							}

							if (data != null && dbdata != null && !dbdata.trim().equals("")
									&& dbdata.trim().equals(data.trim())) {
								// c++;

							} else {
								if (mismatchStr == null) {
									mismatchStr = sheetData[0][j];
								} else {
									mismatchStr = mismatchStr + "|" + sheetData[0][j];
								}
								match = false;
							}
							if (sheets[i].equals(MODEL)) {
								addDebug(sheetData[0][j] + "setdata" + col);
							}
							row.createCell((short) col++).setCellValue((data == null ? "" : data) + "");
							report.append((data == null ? "" : data) + ",");
						}
						if (match) {
							if (sheets[i].equals(MODEL)) {
								addDebug("Match");

							}
							cell = row.createCell((short) col++);
							cell.setCellStyle(green);
							cell.setCellValue("All Match");
							report.append("All Match");
						} else {
							if (sheets[i].equals(MODEL)) {
								addDebug("Mis Matchï¼š" + mismatchStr);

							}
							cell = row.createCell((short) col++);
							cell.setCellStyle(red);
							//row.createCell((short) col++)
							cell.setCellValue(mismatchStr);
							report.append(mismatchStr);
						}
						report.append("\r\n");
						break;
					} else {
						
						if (m == dbData.length - 1) {
							// not found

							for (int j = 1; j < sheetData[0].length; j++) {
								if (sheetData[0][j] == null || sheetData[0][j].trim().equals(""))
									continue;
								String data = sheetData[l][j];
								report.append((data == null ? "" : data) + ",");
								row.createCell((short) col++).setCellValue((data == null ? "" : data) + "");
							}
							report.append("Not found\r\n");
							cell = row.createCell((short) col++);
							cell.setCellStyle(blue);
							cell.setCellValue("Not found");
							//row.createCell((short) col++).setCellValue("Not found");
						}
					}
				}
			}
			rows++;
		}

		return report.toString();

	}

	public void loadDataFromExcel(File excel) {

		try {
			// String encoding = "GBK";

			if (excel.isFile() && excel.exists()) {

				String[] split = excel.getName().split("\\.");
				HSSFWorkbook wb;
				if ("xls".equals(split[1])) {
					FileInputStream fis = new FileInputStream(excel);
					wb = new HSSFWorkbook(fis);
					int sheetlen = wb.getNumberOfSheets();

					for (int i = 0; i < sheetlen; i++) {

						String sheetName = wb.getSheetName(i);
						addDebug("sheetName:" + sheetName);
						if (sheetName != null && sheetName.contains("Announcement Info")) {

							HSSFSheet hssfSheet = wb.getSheetAt(i);
							String title = hssfSheet.getRow(0).getCell((short) 0).getStringCellValue();

							int col = 0;
							if (title != null && title.contains("Withdrawal")) {
								col = 2;

							} else {
								col = 1;
							}
							for (int row = 1; row < hssfSheet.getLastRowNum(); row++) {

								if (hssfSheet.getRow(row) == null)
									continue;
								String key = getCellData(hssfSheet.getRow(row).getCell((short) 0)).toString();
								addDebug("key:" + key);
								if (key.toLowerCase().trim().equals("ann date:")) {
									annDate = getCellData(hssfSheet.getRow(row).getCell((short) col)).toString();
									addDebug("annDate:" + annDate);
								} else if (key.toLowerCase().trim().equals("ann wd date:")) {
									annWDDate = getCellData(hssfSheet.getRow(row).getCell((short) col)).toString();
									addDebug("annWDDate:" + annWDDate);
								} else if (key.toLowerCase().trim().equals("ga date:")) {
									gaDate = getCellData(hssfSheet.getRow(row).getCell((short) col)).toString();
								}

							}
							break;
						}

					}

					for (int i = 0; i < sheetlen; i++) {

						String currentSheet = getType(wb.getSheetName(i));
						// addDebug("currentSheet:"+wb.getSheetName(i)+":"+currentSheet);
						// System.out.println("currentSheet:"+wb.getSheetName(i)+":"+currentSheet);
						if (currentSheet == null)
							continue;
						HSSFSheet hssfSheet = wb.getSheetAt(i);
						if (hssfSheet == null)
							continue;
						int frowNum = hssfSheet.getFirstRowNum();
						int lrowNum = hssfSheet.getLastRowNum();
						int fcolNum = hssfSheet.getRow(frowNum).getFirstCellNum();
						int lcolNum = hssfSheet.getRow(frowNum).getLastCellNum();
						int rowlen = lrowNum - frowNum + 1;
						int collen = lcolNum - fcolNum;
						if (currentSheet.equals(MODEL)) {
							collen += 1;
						} else if (currentSheet.equals(MCONV) || currentSheet.equals(FCONV)) {
							collen += 3;
						} else if (currentSheet.equals(FEATURE)) {
							collen += 2;
						}
						if (rowlen < 2)
							continue;

						String[][] data = new String[rowlen][collen];
						for (int l = frowNum; l <= lrowNum; l++) {
							for (int m = fcolNum; m < lcolNum; m++) {
								HSSFCell cell = hssfSheet.getRow(l) == null ? null
										: hssfSheet.getRow(l).getCell((short) m);
								/*
								 * if (cell != null)
								 * cell.setCellType(HSSFCell.CELL_TYPE_STRING);
								 */
								String dataStr = getCellData(cell);
								/*
								 * if (cell != null && cell.getCellType() ==
								 * HSSFCell.CELL_TYPE_NUMERIC) { dataStr = (int)
								 * cell.getNumericCellValue() + ""; } else {
								 * HSSFRichTextString text = cell == null ? null
								 * : cell.getRichStringCellValue(); dataStr =
								 * text == null ? null : text.getString(); }
								 */

								data[l - frowNum][m - fcolNum] = dataStr;

							}

							// modelMap.put("Ann date", " ANNDATE");
							// modelMap.put("Ann WD date", "WITHDRAWDATE");

							if (!isAllNull(data[l - frowNum])) {

								if (currentSheet.equals(MODEL)) {
									if (l == frowNum) {
										data[l - frowNum][collen - 1] = "Ann date";
									} else {
										if (!isAllNull(data[l - frowNum])) {
											data[l - frowNum][collen - 1] = annDate;
										}
									}
								} else if (currentSheet.equals(FEATURE)) {

									if (l == frowNum) {
										data[l - frowNum][collen - 1] = "Ann WD date";
										data[l - frowNum][collen - 2] = "Ann date";

									} else {
										if (!isAllNull(data[l - frowNum])) {
											data[l - frowNum][collen - 1] = annWDDate;
											data[l - frowNum][collen - 2] = annDate;
										}

									}
								} else if (currentSheet.equals(MCONV) || currentSheet.equals(FCONV)) {
									if (l == frowNum) {
										data[l - frowNum][collen - 1] = "Ann WD date";
										data[l - frowNum][collen - 2] = "Ann date";
										data[l - frowNum][collen - 3] = "GA date";

									} else {
										if (!isAllNull(data[l - frowNum])) {
											data[l - frowNum][collen - 1] = annWDDate;
											data[l - frowNum][collen - 2] = annDate;
											data[l - frowNum][collen - 3] = gaDate;
										}

									}

								}
							}
						}

						sheetDataMap.put(currentSheet, data);
						maxlen = Math.max(maxlen, data[0].length);
						/*
						 * addDebug(sheetDataMap.toString());
						 * System.out.println(sheetDataMap.toString());
						 */
					}

					/*
					 * wb.getSheetAt(1).getRow(1); short s = 1;
					 * wb.getSheetAt(1).getRow(1).getCell(s).getStringCellValue(
					 * );
					 */
					// new HSSFWorkbook(fis)
				} else {
					System.out.println("File type error!");
					return;
				}
			}
		}

		// Set<String> set = new HashSet<String>();
		/*
		 * int firstRowIndex = sheet.getFirstRowNum() + 1; //
		 * Ã§Â¬Â¬Ã¤Â¸â‚¬Ã¨Â¡Å’Ã¦ËœÂ¯Ã¥Ë†â€”Ã¥ï¿½ï¿½Ã¯Â¼Å’Ã¦â€°â‚¬Ã¤Â»Â¥Ã¤Â¸ï¿½Ã¨Â¯Â» int lastRowIndex =
		 * sheet.getLastRowNum(); System.out.println("firstRowIndex: " +
		 * firstRowIndex); System.out.println("lastRowIndex: " + lastRowIndex);
		 * 
		 * File txt = new File(dataPath); BufferedWriter writer = new
		 * BufferedWriter(new FileWriter(txt));
		 * 
		 * for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
		 * // Ã©ï¿½ï¿½Ã¥Å½â€ Ã¨Â¡Å’ System.out.println("rIndex: " + rIndex); Row row =
		 * sheet.getRow(rIndex); if (row != null) { int firstCellIndex =
		 * row.getFirstCellNum();
		 * 
		 * int lastCellIndex = row.getLastCellNum(); String id =
		 * row.getCell(firstCellIndex + 9) == null ? "" :
		 * row.getCell(firstCellIndex + 9).toString(); String flag =
		 * row.getCell(firstCellIndex + 10).toString(); String value1 =
		 * row.getCell(firstCellIndex + 5) == null ? "" :
		 * row.getCell(firstCellIndex + 5).toString(); String value2 =
		 * row.getCell(firstCellIndex + 8) == null ? "" :
		 * row.getCell(firstCellIndex + 8).toString(); value1 =
		 * value1.replace(".0", ""); } } }
		 */
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static boolean isAllNull(String[] data) {
		if (data == null || data.length < 2)
			return true;
		for (int i = 1; i < data.length; i++) {
			if (!(data[i] == null || data[i].trim().equals(""))) {
				return false;
			}
		}
		return true;

	}

	private static String getCellData(HSSFCell cell) {
		if (cell == null)
			return "";
		String value = "";
		DecimalFormat df = new DecimalFormat("0");// æ ¼å¼�åŒ–number Stringå­—ç¬¦ä¸²
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// æ—¥æœŸæ ¼å¼�åŒ–
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getRichStringCellValue().getString();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			short style = cell.getCellStyle().getDataFormat();
			// System.out.println("style:" + style);
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				if (date != null) {
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else {
					value = "";
				}
			}

			else {
				value = new DecimalFormat("0").format(cell.getNumericCellValue());
			}
			break;

		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		default:
			value = cell.toString();
			break;
		}
		return value == null ? "" : value.trim();
	}

	public void loadDateFormDB() {
		loadFeature();
		loadModel();
		loadFeatureTrans();
		// loadTMF();
		loadModelCovert();
		loadUpdate();
	}

	public void testData() {
		// 1,2,
		dbDataMap = new HashMap();
		String[][] data = new String[][] {
				{ "MACHTYPEATR", "MODELATR", "COUNTRYLIST", "EFFECTIVEDATE", "MKTGNAME", "MODMKTGDESC", "INVNAME",
						"PRFTCTR", "BHPRODHIERCD", "BHACCTASGNGRP", "PRCINDC", "SPECBID", "MODELORDERCODE", "INSTALL",
						"LICNSINTERCD", "MACHLVLCNTRL", "PRODID", "IBMCREDIT", "ICRCATEGORY", "VOLUMEDISCOUNTELIG",
						"FUNCCLS", "EDUCPURCHELIG", "INTEGRATEDMODEL", "PLNTOFMFR", "GRADUATEDCHARGE", "ANNUALMAINT",
						"EMEABRANDCD", "CECSPRODKEY", "PRELOADSWINDC", "MACHRATECATG", "MAINTANNBILLELIGINDC",
						"NOCHRGMAINTINDC", "CECSPRODKEY", "PRODSUPRTCD", "RETANINDC", "SYSIDUNIT", "WWOCCODE",
						"UNSPSCCD", "UNSPSCCDUOM", "PLANRELEVANT", "PROJCDNAM", "COFCAT", "COFSUBCAT", "COFGRP",
						"COFSUBGRP", "COMPATDVCCAT", "COMPATDVCSUBCAT", "INTERNALNAME", "WTHDRWEFFCTVDATE", "ANNDATE" },
				{ "3592", "E08", "COUNTRYLIST0", "EFFECTIVEDATE0", "MKTGNAME0", "MODMKTGDESC0", "INVNAME0", "PRFTCTR0",
						"BHPRODHIERCD0", "BHACCTASGNGRP0", "PRCINDC0", "SPECBID0", "MODELORDERCODE0", "INSTALL0",
						"LICNSINTERCD0", "MACHLVLCNTRL0", "PRODID0", "IBMCREDIT0", "ICRCATEGORY0",
						"VOLUMEDISCOUNTELIG0", "FUNCCLS0", "EDUCPURCHELIG0", "INTEGRATEDMODEL0", "PLNTOFMFR0",
						"GRADUATEDCHARGE0", "ANNUALMAINT0", "EMEABRANDCD0", "CECSPRODKEY0", "PRELOADSWINDC0",
						"MACHRATECATG0", "MAINTANNBILLELIGINDC0", "NOCHRGMAINTINDC0", "CECSPRODKEY0", "PRODSUPRTCD0",
						"RETANINDC0", "SYSIDUNIT0", "WWOCCODE0", "UNSPSCCD0", "UNSPSCCDUOM0", "PLANRELEVANT0",
						"PROJCDNAM0", "COFCAT0", "COFSUBCAT0", "COFGRP0", "COFSUBGRP0", "COMPATDVCCAT0",
						"COMPATDVCSUBCAT0", "INTERNALNAME0", "WTHDRWEFFCTVDATE0", "2018-10-09" },
				{ "MACHTYPEATR1", "MODELATR1", "COUNTRYLIST1", "EFFECTIVEDATE1", "MKTGNAME1", "MODMKTGDESC1",
						"INVNAME1", "PRFTCTR1", "BHPRODHIERCD1", "BHACCTASGNGRP1", "PRCINDC1", "SPECBID1",
						"MODELORDERCODE1", "INSTALL1", "LICNSINTERCD1", "MACHLVLCNTRL1", "PRODID1", "IBMCREDIT1",
						"ICRCATEGORY1", "VOLUMEDISCOUNTELIG1", "FUNCCLS1", "EDUCPURCHELIG1", "INTEGRATEDMODEL1",
						"PLNTOFMFR1", "GRADUATEDCHARGE1", "ANNUALMAINT1", "EMEABRANDCD1", "CECSPRODKEY1",
						"PRELOADSWINDC1", "MACHRATECATG1", "MAINTANNBILLELIGINDC1", "NOCHRGMAINTINDC1", "CECSPRODKEY1",
						"PRODSUPRTCD1", "RETANINDC1", "SYSIDUNIT1", "WWOCCODE1", "UNSPSCCD1", "UNSPSCCDUOM1",
						"PLANRELEVANT1", "PROJCDNAM1", "COFCAT1", "COFSUBCAT1", "COFGRP1", "COFSUBGRP1",
						"COMPATDVCCAT1", "COMPATDVCSUBCAT1", "INTERNALNAME1", "WTHDRWEFFCTVDATE1", "2018-09-11" },
				{ "MACHTYPEATR2", "MODELATR2", "COUNTRYLIST2", "EFFECTIVEDATE2", "MKTGNAME2", "MODMKTGDESC2",
						"INVNAME2", "PRFTCTR2", "BHPRODHIERCD2", "BHACCTASGNGRP2", "PRCINDC2", "SPECBID2",
						"MODELORDERCODE2", "INSTALL2", "LICNSINTERCD2", "MACHLVLCNTRL2", "PRODID2", "IBMCREDIT2",
						"ICRCATEGORY2", "VOLUMEDISCOUNTELIG2", "FUNCCLS2", "EDUCPURCHELIG2", "INTEGRATEDMODEL2",
						"PLNTOFMFR2", "GRADUATEDCHARGE2", "ANNUALMAINT2", "EMEABRANDCD2", "CECSPRODKEY2",
						"PRELOADSWINDC2", "MACHRATECATG2", "MAINTANNBILLELIGINDC2", "NOCHRGMAINTINDC2", "CECSPRODKEY2",
						"PRODSUPRTCD2", "RETANINDC2", "SYSIDUNIT2", "WWOCCODE2", "UNSPSCCD2", "UNSPSCCDUOM2",
						"PLANRELEVANT2", "PROJCDNAM2", "COFCAT2", "COFSUBCAT2", "COFGRP2", "COFSUBGRP2",
						"COMPATDVCCAT2", "COMPATDVCSUBCAT2", "INTERNALNAME2", "WTHDRWEFFCTVDATE2", "2018-09-11" },
				{ "MACHTYPEATR3", "MODELATR3", "COUNTRYLIST3", "EFFECTIVEDATE3", "MKTGNAME3", "MODMKTGDESC3",
						"INVNAME3", "PRFTCTR3", "BHPRODHIERCD3", "BHACCTASGNGRP3", "PRCINDC3", "SPECBID3",
						"MODELORDERCODE3", "INSTALL3", "LICNSINTERCD3", "MACHLVLCNTRL3", "PRODID3", "IBMCREDIT3",
						"ICRCATEGORY3", "VOLUMEDISCOUNTELIG3", "FUNCCLS3", "EDUCPURCHELIG3", "INTEGRATEDMODEL3",
						"PLNTOFMFR3", "GRADUATEDCHARGE3", "ANNUALMAINT3", "EMEABRANDCD3", "CECSPRODKEY3",
						"PRELOADSWINDC3", "MACHRATECATG3", "MAINTANNBILLELIGINDC3", "NOCHRGMAINTINDC3", "CECSPRODKEY3",
						"PRODSUPRTCD3", "RETANINDC3", "SYSIDUNIT3", "WWOCCODE3", "UNSPSCCD3", "UNSPSCCDUOM3",
						"PLANRELEVANT3", "PROJCDNAM3", "COFCAT3", "COFSUBCAT3", "COFGRP3", "COFSUBGRP3",
						"COMPATDVCCAT3", "COMPATDVCSUBCAT3", "INTERNALNAME3", "WTHDRWEFFCTVDATE3", "2018-09-11" },
				{ "MACHTYPEATR4", "MODELATR4", "COUNTRYLIST4", "EFFECTIVEDATE4", "MKTGNAME4", "MODMKTGDESC4",
						"INVNAME4", "PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4",
						"MODELORDERCODE4", "INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4",
						"ICRCATEGORY4", "VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4",
						"PLNTOFMFR4", "GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4",
						"PRELOADSWINDC4", "MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4",
						"PRODSUPRTCD4", "RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4",
						"PLANRELEVANT4", "PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4",
						"COMPATDVCCAT4", "COMPATDVCSUBCAT4", "INTERNALNAME4", "WTHDRWEFFCTVDATE4", "2018-09-11" },
				{ "9846", "AC3", "COUNTRYLIST4", "EFFECTIVEDATE4",
						"IBM FlashSystem 9110 Utility Model SFF NVMe Control Enclosure", "IBM FlashSystem 9100",
						"INVNAME4", "PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4",
						"MODELORDERCODE4", "INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4",
						"ICRCATEGORY4", "VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4",
						"PLNTOFMFR4", "GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4",
						"PRELOADSWINDC4", "MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4",
						"PRODSUPRTCD4", "RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4",
						"PLANRELEVANT4", "PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4",
						"COMPATDVCCAT4", "COMPATDVCSUBCAT4", "INTERNALNAME4", "1/18/2020", "2018-09-11" },
				{ "9846", "AE3", "COUNTRYLIST4", "EFFECTIVEDATE4",
						"IBM FlashSystem 9110 Utility Model SFF NVMe Control Enclosure", "IBM FlashSystem 9100",
						"INVNAME4", "PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4",
						"MODELORDERCODE4", "INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4",
						"ICRCATEGORY4", "VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4",
						"PLNTOFMFR4", "GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4",
						"PRELOADSWINDC4", "MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4",
						"PRODSUPRTCD4", "RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4",
						"PLANRELEVANT4", "PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4",
						"COMPATDVCCAT4", "COMPATDVCSUBCAT4", "INTERNALNAME4", "2018-09-11", "1/18/2020" },
				{ "9848", "AE3", "COUNTRYLIST4", "EFFECTIVEDATE4",
						"IBM FlashSystem Utility Model SFF NVMe Control Enclosure", "IBM FlashSystem", "INVNAME4",
						"PRFTCTR4", "BHPRODHIERCD4", "BHACCTASGNGRP4", "PRCINDC4", "SPECBID4", "MODELORDERCODE4",
						"INSTALL4", "LICNSINTERCD4", "MACHLVLCNTRL4", "PRODID4", "IBMCREDIT4", "ICRCATEGORY4",
						"VOLUMEDISCOUNTELIG4", "FUNCCLS4", "EDUCPURCHELIG4", "INTEGRATEDMODEL4", "PLNTOFMFR4",
						"GRADUATEDCHARGE4", "ANNUALMAINT4", "EMEABRANDCD4", "CECSPRODKEY4", "PRELOADSWINDC4",
						"MACHRATECATG4", "MAINTANNBILLELIGINDC4", "NOCHRGMAINTINDC4", "CECSPRODKEY4", "PRODSUPRTCD4",
						"RETANINDC4", "SYSIDUNIT4", "WWOCCODE4", "UNSPSCCD4", "UNSPSCCDUOM4", "PLANRELEVANT4",
						"PROJCDNAM4", "COFCAT4", "COFSUBCAT4", "COFGRP4", "COFSUBGRP4", "COMPATDVCCAT4",
						"COMPATDVCSUBCAT4", "INTERNALNAME4", "1/18/2020", "2018-09-11" } };

		dbDataMap.put(MODEL, data);

		data = new String[][] {
				{ "MTM", "FEATURECODE", "COUNTRYLIST", "EFFECTIVEDATE", "HWFCCAT", "HWFCSUBCAT", "INVNAME", "MKTGNAME",
						"MAINTPRICE", "PRICEDFEATURE", "FCMKTGDESC", "ORDERCODE", "INSTALL", "RETURNEDPARTS",
						"INITORDERMAX", "SYSTEMMAX", "SYSTEMMIN", "PREREQ", "COREQUISITE", "LMTATION", "COMPATIBILITY",
						"COMMENTS", "CBLORD", "CONFIGURATORFLAG", "BULKMESINDC", "WARRSVCCOVR", "EFFECTIVEDATE" },
				{ "MTM0", "FEATURECODE0", "COUNTRYLIST0", "EFFECTIVEDATE0", "HWFCCAT0", "HWFCSUBCAT0", "INVNAME0",
						"MKTGNAME0", "MAINTPRICE0", "PRICEDFEATURE0", "FCMKTGDESC0", "ORDERCODE0", "INSTALL0",
						"RETURNEDPARTS0", "INITORDERMAX0", "SYSTEMMAX0", "SYSTEMMIN0", "PREREQ0", "COREQUISITE0",
						"LMTATION0", "COMPATIBILITY0", "COMMENTS0", "CBLORD0", "CONFIGURATORFLAG0", "BULKMESINDC0",
						"WARRSVCCOVR0", "EFFECTIVEDATE0" },
				{ "MTM1", "FEATURECODE1", "COUNTRYLIST1", "EFFECTIVEDATE1", "HWFCCAT1", "HWFCSUBCAT1", "INVNAME1",
						"MKTGNAME1", "MAINTPRICE1", "PRICEDFEATURE1", "FCMKTGDESC1", "ORDERCODE1", "INSTALL1",
						"RETURNEDPARTS1", "INITORDERMAX1", "SYSTEMMAX1", "SYSTEMMIN1", "PREREQ1", "COREQUISITE1",
						"LMTATION1", "COMPATIBILITY1", "COMMENTS1", "CBLORD1", "CONFIGURATORFLAG1", "BULKMESINDC1",
						"WARRSVCCOVR1", "EFFECTIVEDATE1" },
				{ "MTM2", "FEATURECODE2", "COUNTRYLIST2", "EFFECTIVEDATE2", "HWFCCAT2", "HWFCSUBCAT2", "INVNAME2",
						"MKTGNAME2", "MAINTPRICE2", "PRICEDFEATURE2", "FCMKTGDESC2", "ORDERCODE2", "INSTALL2",
						"RETURNEDPARTS2", "INITORDERMAX2", "SYSTEMMAX2", "SYSTEMMIN2", "PREREQ2", "COREQUISITE2",
						"LMTATION2", "COMPATIBILITY2", "COMMENTS2", "CBLORD2", "CONFIGURATORFLAG2", "BULKMESINDC2",
						"WARRSVCCOVR2", "EFFECTIVEDATE2" },
				{ "MTM3", "FEATURECODE3", "COUNTRYLIST3", "EFFECTIVEDATE3", "HWFCCAT3", "HWFCSUBCAT3", "INVNAME3",
						"MKTGNAME3", "MAINTPRICE3", "PRICEDFEATURE3", "FCMKTGDESC3", "ORDERCODE3", "INSTALL3",
						"RETURNEDPARTS3", "INITORDERMAX3", "SYSTEMMAX3", "SYSTEMMIN3", "PREREQ3", "COREQUISITE3",
						"LMTATION3", "COMPATIBILITY3", "COMMENTS3", "CBLORD3", "CONFIGURATORFLAG3", "BULKMESINDC3",
						"WARRSVCCOVR3", "EFFECTIVEDATE3" },
				{ "MTM4", "FEATURECODE4", "COUNTRYLIST4", "EFFECTIVEDATE4", "HWFCCAT4", "HWFCSUBCAT4", "INVNAME4",
						"MKTGNAME4", "MAINTPRICE4", "PRICEDFEATURE4", "FCMKTGDESC4", "ORDERCODE4", "INSTALL4",
						"RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4",
						"LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "CONFIGURATORFLAG4", "BULKMESINDC4",
						"WARRSVCCOVR4", "EFFECTIVEDATE4" },
				{ "MTM4", "FEATURECODE4", "COUNTRYLIST4", "EFFECTIVEDATE4", "HWFCCAT4", "HWFCSUBCAT4", "INVNAME4",
						"MKTGNAME4", "MAINTPRICE4", "PRICEDFEATURE4", "FCMKTGDESC4", "ORDERCODE4", "INSTALL4",
						"RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4",
						"LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "CONFIGURATORFLAG4", "BULKMESINDC4",
						"WARRSVCCOVR4", "EFFECTIVEDATE4" },
				{ "9846-AC3", "0983", "COUNTRYLIST4", "EFFECTIVEDATE4", "HWFCCAT4", "HWFCSUBCAT4", "INVNAME4",
						"MKTGNAME4", "MAINTPRICE4", "PRICEDFEATURE4", "FCMKTGDESC4", "ORDERCODE4", "INSTALL4",
						"RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4", "COREQUISITE4",
						"LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "CONFIGURATORFLAG4", "BULKMESINDC4",
						"WARRSVCCOVR4", "EFFECTIVEDATE4" }

		};
		dbDataMap.put(FEATURE, data);
		data = new String[][] {
				{ "MTM", "FEATURECODE", "RETURNEDPARTS", "INITORDERMAX", "SYSTEMMAX", "SYSTEMMIN", "PREREQ",
						"COREQUISITE", "LMTATION", "COMPATIBILITY", "COMMENTS", "CBLORD", "BULKMESINDC" },
				{ "MTM0", "FEATURECODE0", "RETURNEDPARTS0", "INITORDERMAX0", "SYSTEMMAX0", "SYSTEMMIN0", "PREREQ0",
						"COREQUISITE0", "LMTATION0", "COMPATIBILITY0", "COMMENTS0", "CBLORD0", "BULKMESINDC0" },
				{ "MTM1", "FEATURECODE1", "RETURNEDPARTS1", "INITORDERMAX1", "SYSTEMMAX1", "SYSTEMMIN1", "PREREQ1",
						"COREQUISITE1", "LMTATION1", "COMPATIBILITY1", "COMMENTS1", "CBLORD1", "BULKMESINDC1" },
				{ "MTM2", "FEATURECODE2", "RETURNEDPARTS2", "INITORDERMAX2", "SYSTEMMAX2", "SYSTEMMIN2", "PREREQ2",
						"COREQUISITE2", "LMTATION2", "COMPATIBILITY2", "COMMENTS2", "CBLORD2", "BULKMESINDC2" },
				{ "MTM3", "FEATURECODE3", "RETURNEDPARTS3", "INITORDERMAX3", "SYSTEMMAX3", "SYSTEMMIN3", "PREREQ3",
						"COREQUISITE3", "LMTATION3", "COMPATIBILITY3", "COMMENTS3", "CBLORD3", "BULKMESINDC3" },
				{ "MTM4", "FEATURECODE4", "RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4",
						"COREQUISITE4", "LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "BULKMESINDC4" },
				{ "MTM4", "FEATURECODE4", "RETURNEDPARTS4", "INITORDERMAX4", "SYSTEMMAX4", "SYSTEMMIN4", "PREREQ4",
						"COREQUISITE4", "LMTATION4", "COMPATIBILITY4", "COMMENTS4", "CBLORD4", "BULKMESINDC4" } };
		dbDataMap.put(UPDATEFC, data);
		data = new String[][] {
				{ "FROMMACHTYPE", "FROMMODEL", "FROMFEATURECODE", "TOMACHTYPE", "TOMODEL", "TOFEATURECODE", "FTCAT",
						"RETURNEDPARTS", "ZEROPRICE", "WTHDRWEFFCTVDATE" },
				{ "FROMMACHTYPE0", "FROMMODEL0", "FROMFEATURECODE0", "TOMACHTYPE0", "TOMODEL0", "TOFEATURECODE0",
						"FTCAT0", "RETURNEDPARTS0", "ZEROPRICE0", "WTHDRWEFFCTVDATE0" },
				{ "FROMMACHTYPE1", "FROMMODEL1", "FROMFEATURECODE1", "TOMACHTYPE1", "TOMODEL1", "TOFEATURECODE1",
						"FTCAT1", "RETURNEDPARTS1", "ZEROPRICE1", "WTHDRWEFFCTVDATE1" },
				{ "FROMMACHTYPE2", "FROMMODEL2", "FROMFEATURECODE2", "TOMACHTYPE2", "TOMODEL2", "TOFEATURECODE2",
						"FTCAT2", "RETURNEDPARTS2", "ZEROPRICE2", "WTHDRWEFFCTVDATE2" },
				{ "FROMMACHTYPE3", "FROMMODEL3", "FROMFEATURECODE3", "TOMACHTYPE3", "TOMODEL3", "TOFEATURECODE3",
						"FTCAT3", "RETURNEDPARTS3", "ZEROPRICE3", "WTHDRWEFFCTVDATE3" },
				{ "FROMMACHTYPE4", "FROMMODEL4", "FROMFEATURECODE4", "TOMACHTYPE4", "TOMODEL4", "TOFEATURECODE4",
						"FTCAT4", "RETURNEDPARTS4", "ZEROPRICE4", "WTHDRWEFFCTVDATE4" },
				{ "FROMMACHTYPE4", "FROMMODEL4", "FROMFEATURECODE4", "TOMACHTYPE4", "TOMODEL4", "TOFEATURECODE4",
						"FTCAT4", "RETURNEDPARTS4", "ZEROPRICE4", "WTHDRWEFFCTVDATE4" } };
		dbDataMap.put(FCONV, data);

		data = new String[][] {
				{ "FROMMACHTYPE", "FROMMODEL", "TOMACHTYPE", "TOMODEL", "RETURNEDPARTS", "WTHDRWEFFCTVDATE" },
				{ "FROMMACHTYPE0", "FROMMODEL0", "TOMACHTYPE0", "TOMODEL0", "RETURNEDPARTS0", "WTHDRWEFFCTVDATE0" },
				{ "FROMMACHTYPE1", "FROMMODEL1", "TOMACHTYPE1", "TOMODEL1", "RETURNEDPARTS1", "WTHDRWEFFCTVDATE1" },
				{ "FROMMACHTYPE2", "FROMMODEL2", "TOMACHTYPE2", "TOMODEL2", "RETURNEDPARTS2", "WTHDRWEFFCTVDATE2" },
				{ "FROMMACHTYPE3", "FROMMODEL3", "TOMACHTYPE3", "TOMODEL3", "RETURNEDPARTS3", "WTHDRWEFFCTVDATE3" },
				{ "FROMMACHTYPE4", "FROMMODEL4", "TOMACHTYPE4", "TOMODEL4", "RETURNEDPARTS4", "WTHDRWEFFCTVDATE4" },
				{ "FROMMACHTYPE4", "FROMMODEL4", "TOMACHTYPE4", "TOMODEL4", "RETURNEDPARTS4", "WTHDRWEFFCTVDATE4" } };

		dbDataMap.put(MCONV, data);
	}

	public void loadModel() {
		try {
			String sql = "select distinct av.entityid as AVAILID,m.MACHTYPEATR,m.MODELATR,av.EFFECTIVEDATE,m.MKTGNAME,m.INVNAME,m.PRFTCTR,m.BHPRODHIERCD,m.BHACCTASGNGRP,m.PRCINDC,m.SPECBID,m.MODELORDERCODE,m.INSTALL,m.LICNSINTERCD,"
					+ "m.MACHLVLCNTRL,m.PRODID,m.IBMCREDIT,m.ICRCATEGORY,m.VOLUMEDISCOUNTELIG,m.FUNCCLS,m.WITHDRAWDATE,m.GENAVAILDATE,m.ANNDATE,m.MODMKTGDESC,m.WTHDRWEFFCTVDATE,m.WWOCCODE,m.UNSPSCCDUOM,"
					+ "gm.EDUCPURCHELIG,gm.INTEGRATEDMODEL,gm.PLNTOFMFR,gm.GRADUATEDCHARGE,gm.ANNUALMAINT,gm.EMEABRANDCD,"
					+ "m.CECSPRODKEY,m.PRELOADSWINDC,m.MACHRATECATG,m.MAINTANNBILLELIGINDC,m.NOCHRGMAINTINDC,m.CECSPRODKEY,m.PRODSUPRTCD,m.RETANINDC,m.SYSIDUNIT,m.UNSPSCCD,m.PLANRELEVANT,m.PROJCDNAM,m.COFCAT,m.COFSUBCAT,m.COFGRP,m.COFSUBGRP,m.COMPATDVCCAT,m.COMPATDVCSUBCAT,m.INTERNALNAME "
					+ "from opicm.announcement ann "
					+ "join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 "
					+ "join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid "
					+ "join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 "
					+ "left join opicm.relator r3 on r3.entitytype='MODELGEOMOD' and r3.entity1id=m.entityid "
					+ "left join opicm.geomod gm on gm.entityid=r3.entity2id and gm.nlsid=1 "
					+ "where ann.annnumber= ?  and ann.nlsid=1 with ur";

			// select f.attributevalue as COUNTRYLIST from opicm.flag f on
			// f.entitytype='AVAIL' and f.entityid=av.entityid and
			// f.attributecode='COUNTRYLIST' with ur
			Connection connection = getDatabase().getODS2Connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, annNumber);
			ResultSet resultSet = statement.executeQuery();
			String[][] data = processResult(resultSet);
			dbDataMap.put(MODEL, data);
			addDebug("loadModel sql:" + sql);
			/*
			 * String id = ""; //countryMap = new HashMap();
			 * 
			 * for (int a = 0; a < data.length; a++) { id += data[a][0]; if (a <
			 * data.length - 1) { id += ","; } }
			 * 
			 * String idsql =
			 * "select f.attributevalue as COUNTRYLIST,f.attributecode from opicm.flag f where f.entitytype='AVAIL' and in("
			 * + id + ") and f.attributecode='COUNTRYLIST' with ur";
			 * 
			 * statement = connection.prepareStatement(sql); resultSet =
			 * statement.executeQuery(); while (resultSet.next()) {
			 * 
			 * String idStr = resultSet.getString(1); String country =
			 * resultSet.getString(2); String countrys = countryMap.get(idStr)
			 * == null ? "" : countryMap.get(idStr).toString();
			 * 
			 * countryMap.put(idStr, countrys + country); }
			 */

		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadFeature() {
		try {
			String sql = "select concat(concat(m.MACHTYPEATR,'-'),m.MODELATR) as MTM,f.FEATURECODE,av.EFFECTIVEDATE,f.HWFCCAT,f.HWFCSUBCAT,f.INVNAME,f.MKTGNAME,f.MAINTPRICE,f.PRICEDFEATURE,"
					+ "lf.attributevalue as FCMKTGDESC,"
					+ "ps.ORDERCODE,ps.INSTALL,ps.RETURNEDPARTS,ps.INITORDERMAX,ps.SYSTEMMAX,ps.SYSTEMMIN,ps.GENAVAILDATE,ps.ANNDATE,ps.WTHDRWEFFCTVDATE,ps.WITHDRAWDATE,ps.WARRSVCCOVR,"
					+ "p1.attributevalue as PREREQ,p2.attributevalue as COREQUISITE,p3.attributevalue as LMTATION,p4.attributevalue as COMPATIBILITY,p5.attributevalue as COMMENTS,p6.attributevalue as CBLORD,"
					+ "f.CONFIGURATORFLAG,ps.BULKMESINDC " + "from opicm.announcement ann "
					+ "join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 "
					+ "join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid "
					+ "join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 "
					+ "join opicm.relator r2 on r2.entitytype='PRODSTRUCT' and r2.entity2id=m.entityid "
					+ "join opicm.feature f on f.entityid=r2.entity1id and f.nlsid=1 "
					+ "join opicm.prodstruct ps on ps.entityid=r2.entityid and ps.nlsid=1 "
					+ "left join opicm.longtext lf on lf.entityid=f.entityid and lf.attributecode='FCMKTGDESC' and lf.entitytype='FEATURE' "
					+ "left join opicm.longtext p1 on p1.entityid=ps.entityid and p1.attributecode='PREREQ' and p1.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p2 on p2.entityid=ps.entityid and p2.attributecode='COREQUISITE' and p2.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p3 on p3.entityid=ps.entityid and p3.attributecode='LMTATION' and p3.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p4 on p4.entityid=ps.entityid and p4.attributecode='COMPATIBILITY' and p4.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p5 on p5.entityid=ps.entityid and p5.attributecode='COMMENTS' and p5.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p6 on p6.entityid=ps.entityid and p6.attributecode='CBLORD' and p6.entitytype='PRODSTRUCT' "
					+ "where ann.annnumber=? with ur";
			Connection connection = getDatabase().getODS2Connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, annNumber);
			ResultSet resultSet = statement.executeQuery();
			String[][] data = processResult(resultSet);
			dbDataMap.put(FEATURE, data);
			addDebug("loadFeature sql:" + sql);
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadTMF111() {
		try {
			String sql = "select concat(m.MACHTYPEATR,m.MODELATR),f.FEATURECODE,"
					+ "ps.RETURNEDPARTS,ps.INITORDERMAX,ps.SYSTEMMAX,ps.SYSTEMMIN," +
					// --ps.PREREQ,ps.COREQUISITE,ps.LMTATION,ps.COMPATIBILITY,ps.COMMENTS,ps.CBLORD,"+
					"ps.BULKMESINDC " + "from opicm.announcement ann "
					+ "join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 "
					+ "join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid "
					+ "join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 "
					+ "join opicm.relator r2 on r2.entitytype='PRODSTRUCT' and r2.entity2id=m.entityid "
					+ "join opicm.feature f on f.entityid=r2.entity1id  and f.nlsid=1 "
					+ "join opicm.prodstruct ps on ps.entityid=r2.entityid and ps.nlsid=1 "
					+ "where ann.annnumber=? with ur";
			Connection connection = getDatabase().getODS2Connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, annNumber);
			ResultSet resultSet = statement.executeQuery();
			String[][] data = processResult(resultSet);
			dbDataMap.put("", data);

		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadUpdate() {
		try {
			String sql = "select concat(concat(m.MACHTYPEATR,'-'),m.MODELATR) as MTM,f.FEATURECODE,"
					+ "ps.RETURNEDPARTS,ps.INITORDERMAX,ps.SYSTEMMAX,ps.SYSTEMMIN,"
					+ "p1.attributevalue as PREREQ,p2.attributevalue as COREQUISITE,p3.attributevalue as LMTATION,p4.attributevalue as COMPATIBILITY,p5.attributevalue as COMMENTS,p6.attributevalue as CBLORD,"
					+ "ps.BULKMESINDC " + "from opicm.announcement ann "
					+ "join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 "
					+ "join opicm.relator r on r.entity1type='MODEL' and r.entity2type='AVAIL' and r.entity2id=av.entityid "
					+ "join opicm.model m on m.entityid=r.entity1id and m.nlsid=1 "
					+ "join opicm.relator r2 on r2.entitytype='PRODSTRUCT' and r2.entity2id=m.entityid "
					+ "join opicm.feature f on f.entityid=r2.entity1id  and f.nlsid=1 "
					+ "join opicm.prodstruct ps on ps.entityid=r2.entityid and ps.nlsid=1 "
					+ "left join opicm.longtext p1 on p1.entityid=ps.entityid and p1.attributecode='PREREQ' and p1.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p2 on p2.entityid=ps.entityid and p2.attributecode='COREQUISITE' and p2.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p3 on p3.entityid=ps.entityid and p3.attributecode='LMTATION' and p3.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p4 on p4.entityid=ps.entityid and p4.attributecode='COMPATIBILITY' and p4.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p5 on p5.entityid=ps.entityid and p5.attributecode='COMMENTS' and p5.entitytype='PRODSTRUCT' "
					+ "left join opicm.longtext p6 on p6.entityid=ps.entityid and p6.attributecode='CBLORD' and p6.entitytype='PRODSTRUCT' "
					+ "where ann.annnumber=? with ur";
			Connection connection = getDatabase().getODS2Connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, annNumber);
			ResultSet resultSet = statement.executeQuery();
			// resultSet.getMetaData()connection;

			String[][] data = processResult(resultSet);
			dbDataMap.put(UPDATEFC, data);
			addDebug("loadUpdate sql:" + sql);
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[][] processResult(ResultSet resultSet) throws SQLException {

		int length = resultSet.getMetaData().getColumnCount();
		// resultSet.getMetaData()connection;
		String[][] data = null;
		String[] d = new String[length];
		List list = new ArrayList();
		for (int i = 1; i <= length; i++) {
			d[i - 1] = resultSet.getMetaData().getColumnName(i);
		}
		int size = 0;
		list.add(d);
		while (resultSet.next()) {
			d = new String[length];
			for (int i = 1; i <= length; i++) {
				d[i - 1] = resultSet.getString(i);
			}
			list.add(d);
		}
		size = list.size();
		data = new String[size + 1][length];
		for (int i = 0; i < list.size(); i++) {
			data[i] = (String[]) list.get(i);
		}

		return data;
	}

	public void loadModelCovert() {
		try {
			String sql = "select mc.FROMMACHTYPE,mc.FROMMODEL,mc.TOMACHTYPE,mc.TOMODEL,mc.RETURNEDPARTS,mc.WTHDRWEFFCTVDATE,mc.WITHDRAWDATE,mc.ANNDATE,mc.GENAVAILDATE "
					+ "from opicm.announcement ann "
					+ "join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 "
					+ "join opicm.relator r on r.entity1type='MODELCONVERT' and r.entity2type='AVAIL' and r.entity2id=av.entityid "
					+ "join opicm.modelconvert mc on mc.entityid=r.entity1id and mc.nlsid=1 "
					+ "where ann.annnumber=? with ur";
			Connection connection = getDatabase().getODS2Connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, annNumber);
			ResultSet resultSet = statement.executeQuery();
			String[][] data = processResult(resultSet);
			dbDataMap.put(MCONV, data);
			addDebug("loadModelCovert sql:" + sql);
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadFeatureTrans() {
		try {
			String sql = "select ft.FROMMACHTYPE,ft.FROMMODEL,ft.FROMFEATURECODE,ft.TOMACHTYPE,ft.TOMODEL,ft.TOFEATURECODE,ft.FTCAT,ft.RETURNEDPARTS,ft.ZEROPRICE,ft.WITHDRAWDATE,ft.ANNDATE,ft.GENAVAILDATE,ft.WTHDRWEFFCTVDATE "
					+ "from opicm.announcement ann "
					+ "join opicm.avail av on av.anncodename=ann.anncodename and av.nlsid=1 "
					+ "join opicm.relator r on r.entity1type='FCTRANSACTION' and r.entity2type='AVAIL' and r.entity2id=av.entityid "
					+ "join opicm.fctransaction ft on ft.entityid=r.entity1id and ft.nlsid=1 "
					+ "where ann.annnumber=? with ur";
			Connection connection = getDatabase().getODS2Connection();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, annNumber);
			ResultSet resultSet = statement.executeQuery();
			String[][] data = processResult(resultSet);
			dbDataMap.put(FCONV, data);
			addDebug("loadFeatureTrans sql:" + sql);
		} catch (MiddlewareException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getType(String key) {
		return sheetMap.get(key) == null ? null : sheetMap.get(key).toString();
	}

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
			// TODO: handle exception
			addDebug("sendMail IO Exception");
		}

		finally {
			if (fisBlob != null) {
				fisBlob.close();
			}
		}

	}

	/**********************************
	 * add debug info as html comment
	 */
	protected void addDebug(String msg) {
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
	}

	public static void main(String[] args) throws IOException, MiddlewareException, SQLException {
		// String fileName =
		// "C:\\Users\\JianBoXu\\Desktop\\eacm\\comparion\\data\\1.xls";
		// new COMPARIREPORTSTATUS().loadDataFromExcel(fileName);
		/*
		 * Pattern pattern = Pattern.compile("(\\d{5})"); String filename =
		 * "2018-06-29_Storage HWW RFA 74091 v0.xls"; Matcher matcher =
		 * pattern.matcher(filename); System.out.println(matcher.find());
		 * System.out.println(matcher.group(1));
		 */
		new COMPARIREPORTSTATUS().test();
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
