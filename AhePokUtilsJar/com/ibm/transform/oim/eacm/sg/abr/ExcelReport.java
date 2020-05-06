/* 
 * (c) Copyright International Business Machines Corporation, 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.sg.abr;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;
import com.ibm.transform.oim.eacm.xalan.BinaryReport;
import com.ibm.transform.oim.eacm.xalan.Data;
import com.ibm.transform.oim.eacm.xalan.DataView;
import com.ibm.transform.oim.eacm.xalan.ODSConnection;
import com.ibm.transform.oim.eacm.xalan.ReturnCode;

/**
 * This class will produce an Excel Spreadsheet for the Current and Approved views
 * <pre>
 * $Log: ExcelReport.java,v $
 * Revision 1.8  2008/08/26 21:19:15  wendy
 * Updated ReturnCode interface with message
 *
 * Revision 1.7  2007/01/11 13:31:04  chris
 * switch to _A versions of two LINK views
 *
 * Revision 1.6  2007/01/10 21:36:05  chris
 * Remove unwanted change
 *
 * Revision 1.5  2006/12/21 18:12:25  chris
 * Added a tab with a query of the processs lock table
 *
 * Revision 1.4  2006/12/20 18:38:04  chris
 * Hardcode check for SPMLAPPROVEDABR attributecode
 *
 * Revision 1.3  2006/12/19 18:33:51  chris
 * Fix date formatting for TIR 6WKJWR
 *
 * Revision 1.2  2006/12/19 18:01:43  chris
 * Fix missing last column on every tab for TIR's 6WKQW7, 6WL3ZD, 6WHMQP
 *
 * Revision 1.1  2006/12/19 17:08:18  chris
 * Design change for TIR 6WJMRP to prevent abr from running when DMNET is running
 *
 * </pre>
 * @author cstolpe
 */
public class ExcelReport implements Log, ReturnCode, Data, BinaryReport, ODSConnection {

	public static void main(String[] args) {
		final String signature = ".main(String[] args): ";
		Logger cronLog = new Logger();
		ExcelReport xls = new ExcelReport();
		StringBuffer identifier = new StringBuffer("cron job");
		FileOutputStream fso = null;
		Connection con = null;

		cronLog.setIdentifier(identifier.toString());
		try {
			Class.forName(System.getProperty("DatabaseDriver","COM.ibm.db2.jdbc.app.DB2Driver"));
			con = DriverManager.getConnection(
				System.getProperty("ODSDatabaseURL","jdbc:db2:SAMPLE"),
				System.getProperty("ODSUserID","nouser"),
				System.getProperty("ODSPassword","nopassword")
			);
			xls.setCurrentData(Boolean.valueOf(System.getProperty("current","true")).booleanValue());
			xls.setODSconnection(con);
			InetAddress host = InetAddress.getLocalHost();
			identifier.append(" on ");
			identifier.append(host.getCanonicalHostName());
			cronLog.setIdentifier(identifier.toString());
			xls.setIdentifier(identifier.toString());

			if (!xls.initialize()) {
				cronLog.print(xls.getClass().getName());
				cronLog.print(signature);
				cronLog.println("ERROR: Failed to create the spreadsheet.");
			}
			
			fso = new FileOutputStream(System.getProperty("rptFile"));
			xls.getWorkbook().write(fso);
		} catch (UnknownHostException e) {
			cronLog.print(xls.getClass().getName());
			cronLog.print(signature);
			cronLog.print("WARNING - ");
			cronLog.println(e.getMessage());
		} catch (FileNotFoundException e) {
			cronLog.print(xls.getClass().getName());
			cronLog.print(signature);
			cronLog.println(e.getMessage());
		} catch (IOException e) {
			cronLog.print(xls.getClass().getName());
			cronLog.print(signature);
			cronLog.println(e.getMessage());
		} catch (SQLException e) {
			cronLog.print(xls.getClass().getName());
			cronLog.print(signature);
			cronLog.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			cronLog.print(xls.getClass().getName());
			cronLog.print(signature);
			cronLog.println(e.getMessage());
		}
		finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					cronLog.print(xls.getClass().getName());
					cronLog.print(signature);
					cronLog.println(e.getMessage());
				}
			}
			if (fso != null) {
				try {
					fso.close();
				} catch (IOException e) {
					cronLog.print(xls.getClass().getName());
					cronLog.print(signature);
					cronLog.println(e.getMessage());
				}
			}
		}
		
	}
	
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private String[][] available = null;
	private String[][] current = null;
	private boolean currentData = true;
	private DataView dv = new DataView();
	private boolean hasPassed = false;
	private Logger log = new Logger();
	private Connection spml;
	private HSSFWorkbook wb = new HSSFWorkbook();
	private final HSSFCellStyle cellStyle = wb.createCellStyle();
	private final String runAllowedSQL = "SELECT PROCESS_NAME,START_TIME FROM EACM.IFMLOCK WHERE PROCESS_ID=20 AND STATUS > 0";
	private final String runStartSQL = "UPDATE EACM.IFMLOCK SET STATUS=1, START_TIME=CURRENT TIMESTAMP WHERE PROCESS_ID=30";
	private final String runEndSQL = "UPDATE EACM.IFMLOCK SET STATUS=0, END_TIME=CURRENT TIMESTAMP WHERE PROCESS_ID=30";
	private String processName = "CATODS";
	private String processStartTime = "9999-12-31-00.00.00.000000";
	private boolean processRunning = false;
	
	public ExcelReport() {	
		current = new String[][] {
			{"BUSINESS_TRIGGER",               "SELECT * FROM SPML.BUSINESS_TRIGGER"},
			{"SERVICE_SOLUTION_LINE",          "SELECT * FROM SPML.SERVICE_SOLUTION_LINE"},
			{"SERVICE_SOLUTION_LINE_LOCALIZ",  "SELECT * FROM SPML.SERVICE_SOLUTION_LINE_LOCALIZ"},
			{"INFRA_INDUS_SOLUTION",           "SELECT * FROM SPML.INFRA_INDUS_SOLUTION"},
			{"SERVICE",                        "SELECT * FROM SPML.SERVICE"},
			{"SERVICE_SOLUTION_LOCALIZATION",  "SELECT * FROM SPML.SERVICE_SOLUTION_LOCALIZATION"},
			{"SERVICE_SOLUTION_TRIGGER_LINK",  "SELECT * FROM SPML.SERVICE_SOLUTION_TRIGGER_LINK"},
			{"SERVICE_SOLUTION_LINK",          "SELECT * FROM SPML.SERVICE_SOLUTION_LINK"},
			{"PROJ_PLANNED_CHNG_SERVICE_SOLU", "SELECT * FROM SPML.PROJ_PLANNED_CHNG_SERVICE_SOLU"},
			{"GLOBAL_BRAND_TABLE",             "SELECT * FROM SPML.GLOBAL_BRAND_TABLE"},
			{"EXTRA_INFO",                     "SELECT PROCESS_NAME,START_TIME,END_TIME FROM EACM.IFMLOCK WHERE PROCESS_ID=20"}
		};
		available = new String[][] {
			{current[0][0], current[0][1]}, // always current
			{current[1][0], "SELECT * FROM SPML.SERVICE_SOLUTION_LINE_A"},
			{current[2][0], "SELECT * FROM SPML.SERVICE_SOLUTION_LINE_LOCALIZ_A"},
			{current[3][0], "SELECT * FROM SPML.INFRA_INDUS_SOLUTION_A"},
			{current[4][0], "SELECT * FROM SPML.SERVICE_A"},
			{current[5][0], "SELECT * FROM SPML.SERVICE_SOLUTION_LOCALIZATION_A"},
			{current[6][0], "SELECT * FROM SPML.SERVICE_SOLUTION_TRIGGER_LINK_A"},
			{current[7][0], "SELECT * FROM SPML.SERVICE_SOLUTION_LINK_A"},
			{current[8][0], current[8][1]},
			{current[9][0], current[9][1]},  // always current
			{current[10][0], current[10][1]}  // always current
		};
	}
	
	/**
	 * Creates the 
	 * @return true if no problems
	 */
	private boolean createSheet(String name, String sql) {
		final String signature = ".createSheet(): ";
		boolean result = true;
		HSSFSheet sheet = wb.createSheet(name);
		wb.setSheetName(wb.getSheetIndex(name), name);//, HSSFWorkbook.ENCODING_UTF_16);
		int rowIndex = 0;
		try {
			Statement query = spml.createStatement();
			ResultSet rows = query.executeQuery(sql);
			ResultSetMetaData meta = rows.getMetaData();
			int nColumns = meta.getColumnCount();
			HSSFRow row = sheet.createRow(rowIndex);
			for (int columnIndex = 1; columnIndex <= nColumns; columnIndex++) {
				short cellIndex = (short)(columnIndex - 1);
				HSSFCell cell = row.createCell(cellIndex);
				//cell.setCellValue(meta.getColumnLabel(columnIndex));
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell.setCellValue(new HSSFRichTextString(meta.getColumnLabel(columnIndex)));
			}
			while (rows.next()) {
				rowIndex = rows.getRow();
				row = sheet.createRow(rowIndex);
				for (short columnIndex = 1; columnIndex <= nColumns; columnIndex++) {
					short cellIndex = (short)(columnIndex - 1);
					HSSFCell cell = row.createCell(cellIndex);
					switch (meta.getColumnType(columnIndex)) {
						case Types.INTEGER :
							cell.setCellValue(rows.getInt(columnIndex));
							break;

						case Types.DATE :
							java.sql.Date dTmp = rows.getDate(columnIndex);
							if (!rows.wasNull()) {
								cell.setCellValue(dTmp);
								cell.setCellStyle(cellStyle);
							}
							break;

						default :
							String sTmp = rows.getString(columnIndex);
							if (!rows.wasNull()) {
								//cell.setCellValue(sTmp);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					            cell.setCellValue(new HSSFRichTextString(sTmp));
							}
							break;
					}
				}
			}
			rows.close();
			query.close();
		} catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.print(name);
			log.print(",");
			log.print(sql);
			log.print(" ");
			log.println(e.getMessage());
			HSSFRow errRow = sheet.createRow(rowIndex++);
			/*errRow.createCell((short)0).setCellValue(name);
			errRow.createCell((short)1).setCellValue(sql);
			errRow.createCell((short)1).setCellValue(e.getMessage());*/
			errRow.createCell((short)0).setCellValue(new HSSFRichTextString(name));
			errRow.createCell((short)1).setCellValue(new HSSFRichTextString(sql));
			errRow.createCell((short)1).setCellValue(new HSSFRichTextString(e.getMessage()));
		}
		return result;
	}
	
	/**
	 * Implements the BinaryReport interface
	 * @return an byte[] of the HSSFWorkbook
	 */
	public byte[] getBytes() {
		byte[] result = null;
		if (bos.size() > 0 && !processRunning) {
			result = bos.toByteArray();
		}
		return result;
	}
	/**
	 * Implements the BinaryReport interface
	 * @return an String for the file extension
	 */
	public String getExtension() {
		return "xls";
	}

	/**
	 * Implements the getter method of the DataView interface
	 * @return the DataView instance or the default empty DataView
	 */
	public DataView getDataView() {
		return dv;
	}

	/**
	 * Implements the getter method of the Log interface
	 * @return String log identifier (Job ID or Session ID)
	 */
	public String getIdentifier() {
		return log.getIdentifier();
	}
	
	/**
	 * Getter method for the workbook
	 * @return HSSFWorkbook the Excel workbook
	 */
	public HSSFWorkbook getWorkbook() {
		return wb;
	}

	/**
	 * Implements the ReturnCode interface
	 * @return true if the Excel Spreadsheet was successfully created
	 */
	public boolean hasPassed() {
		return hasPassed;
	}
	/* (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.ReturnCode#getMessage()
	 */
	public String getMessage(){
		return "";
	}
	private boolean initialize() {
		final String signature = ".initialize(): ";
		boolean result = spml != null;
		String[][] tabAndSQL;
		Statement query = null;
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

		try {
			query = spml.createStatement();
			// This query will only return a row if CATODS is running
			ResultSet row = query.executeQuery(runAllowedSQL);
			processRunning = row.next();
			if (processRunning) {
				processName = row.getString(1);
				processStartTime = row.getString(2);
			}
			else {
				if (query.executeUpdate(runStartSQL) == 0) {
					log.print(getClass().getName());
					log.print(signature);
					log.println("process id 30 is not in eacm.ifmlock table");
				}
			}
		}
		catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		if (result) {
			if (isCurrentData()) {
				tabAndSQL = current;
			}
			else {
				tabAndSQL = available;
			}
			
			for (int tabIndex = 0; tabIndex < tabAndSQL.length; tabIndex++) {
				result &= createSheet(tabAndSQL[tabIndex][0], tabAndSQL[tabIndex][1]);
			}
			
			try {
				wb.write(bos);
			} catch (IOException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
			}
		}
		else {
			log.print(getClass().getName());
			log.print(signature);
			log.println(" no Connection to the ODS was provided.");
		}
		try {
			if (query == null) {
				query = spml.createStatement();
			}
			if (query.executeUpdate(runEndSQL) == 0) {
				log.print(getClass().getName());
				log.print(signature);
				log.println("process id 30 is not in eacm.ifmlock table");
			}
		}
		catch (SQLException e) {
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		hasPassed = result;
		return result;
	}
	
	/**
	 * Getter method for the currentData field
	 * @return true if we wan't the current data and false if we only wan't approved data
	 */
	public boolean isCurrentData() {
		return currentData;
	}

	/**
	 * Setter method for the currentData field
	 * if isCurrent is true you will get the current data 
	 * otherwise you wil only get approved data 
	 * @param isCurrent
	 */
	public void setCurrentData(boolean isCurrent) {
		currentData = isCurrent;
	}

	/**
	 * Implements the setter method of the DataView interface
	 * @param aDataView a replacement DataView object
	 * @return boolean true of parameter is not null
	 */
	public boolean setDataView(DataView aDataView) {
		boolean result = aDataView != null;
		if (result) {
			dv = aDataView;
			dv.addGroupToData(dv.getEntityType());
			if ("SPMLAPPROVEDABR".equals(dv.getAttributeCodeOfABR())) {
				setCurrentData(false);
			}
			result &= initialize();

		}
		return result;
	}

	/**
	 * Implements the setter method of the Log interface
	 * @param anIdentifier String (Job ID or Session ID)
	 */
	public boolean setIdentifier(String anIdentifier) {
		return log.setIdentifier(anIdentifier);
	}

	/**
	 * Implements the setter method of the ODSConnection interface
	 * @param con the java.sql.Connection
	 * @return true if conn is not null
	 */
	public boolean setODSconnection(Connection con) {
		spml = con;
		return con != null;
	}

	/**
	 * @return
	 */
	public String getProcessName() {
		return processName;
	}

	/**
	 * @return
	 */
	public boolean isProcessRunning() {
		return processRunning;
	}

	/**
	 * @return
	 */
	public String getProcessStartTime() {
		return processStartTime;
	}

}
