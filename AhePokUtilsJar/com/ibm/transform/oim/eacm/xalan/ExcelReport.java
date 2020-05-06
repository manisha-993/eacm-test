/* 
 * (c) Copyright International Business Machines Corporation, 2006
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.xalan;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.*;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;

/**
 * This class will produce an Excel Spreadsheet for the Current and Approved views
 * <pre>
 * $Log: ExcelReport.java,v $
 * Revision 1.6  2015/06/01 13:57:54  wangyul
 * RCQ00276438-WI /RCQ00188990-RQ Storage SS
 *
 * Revision 1.6  2015/06/01 21:46:15  luis
 * RCQ00276438-WI /RCQ00188990-RQ Storage SS - created new tab when over max row number
 * 
 * Revision 1.5  2008/08/26 21:16:15  wendy
 * Add output error msg and use updated poi jar
 *
 * Revision 1.4  2007/07/16 17:37:41  chris
 * Fixes for unit test
 *
 * Revision 1.3  2007/07/03 16:45:27  chris
 * some more tweaks
 *
 * Revision 1.2  2007/07/03 13:08:46  chris
 * changes for pricing reports
 *
 * Revision 1.1  2007/03/07 00:21:17  chris
 * new
 *
 *
 * </pre>
 * @author cstolpe
 */
public class ExcelReport implements Log, ReturnCode, Data, BinaryReport, JDBCConnection {
	
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private int dependentProcessID = -1;
	private String dependentProcessName = "FUBAR";
	private boolean dependentProcessRunning = false;
	private String dependentProcessStartTime = "9999-12-31-00.00.00.000000";
	private DataView dv = new DataView();
	private boolean hasPassed = false;
	private Hashtable jdbcCon = new Hashtable();
	private Logger log = new Logger();
	private int processID = -1;
	private final String runAllowedSQL = "SELECT PROCESS_NAME,START_TIME FROM EACM.IFMLOCK WHERE PROCESS_ID=? AND STATUS > 0";
	private final String runEndSQL = "UPDATE EACM.IFMLOCK SET STATUS=0, END_TIME=CURRENT TIMESTAMP WHERE PROCESS_ID=?";
	private final String runStartSQL = "UPDATE EACM.IFMLOCK SET STATUS=1, START_TIME=CURRENT TIMESTAMP WHERE PROCESS_ID=?";
	private Vector sqlType = new Vector();
	private Vector tabNames = new Vector();
	private Vector tabSQL = new Vector();
	private HSSFWorkbook wb = new HSSFWorkbook();
	private final HSSFCellStyle cellStyle = wb.createCellStyle();
	private StringBuffer messageSb=new StringBuffer();
	
	public ExcelReport() {	
	}
	
	/**
	 * @param tabName the label for the spreadsheet tab
	 * @param sql the sql for the spreadsheet tab
	 * @param con should be ODS or PDH
	 * @return true if both arguments are not null
	 */
	public boolean addTabForSQL(String tabName, String sql, String con) {
		final String signature = ".addTabForSQL(String,String,String): ";
		boolean result = tabName != null && sql != null && con != null;
		
		if (result) {
			if (jdbcCon.containsKey(con)) {
				tabNames.addElement(tabName);
				tabSQL.addElement(sql);
				sqlType.addElement(con);
			}
			else {
				log.print(getClass().getName());
				log.print(signature);
				log.print("skipping ");
				log.print(tabName);
				log.print(",");
				log.print(sql);
				log.print(",");
				log.print(con);
				log.println(" third argument can only be PDH or ODS");
				result = false;
				addErrorMessage(tabName+","+sql+","+con+" third argument can only be PDH or ODS");
			}
		}
		else {
			log.print(getClass().getName());
			log.print(signature);
			log.print(tabName);
			log.print(", ");
			log.print(sql);
			log.print(", ");
			log.println(con);
		}
		return result;
	}
	
	/**
	 * Creates the 
	 * @return true if no problems
	 */
	private boolean createSheet(String name, String sql, String conName) {
		final String signature = ".createSheet(String.String,String): ";
		boolean result = jdbcCon.containsKey(conName);

		int rowIndex = 0;
		HSSFSheet sheet = wb.createSheet(name);
		//wb.setSheetName(wb.getSheetIndex(name), name, HSSFWorkbook.ENCODING_UTF_16);
		wb.setSheetName(wb.getSheetIndex(name), name);
		// RCQ00276438-WI /RCQ00188990-RQ Storage SS
		int maxRow = 65530;
		int sheetNameNum = 2;
		Vector sheetTitles = new Vector();
		
		if (result) {
			try {
				Connection con = (Connection) jdbcCon.get(conName);
				Statement query = con.createStatement();
				if (query != null) {
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
			            sheetTitles.add(meta.getColumnLabel(columnIndex));
					}
					while (rows.next()) {
						rowIndex++;
						// RCQ00276438-WI /RCQ00188990-RQ Storage SS
						if (rowIndex > maxRow) {
							log.print(getClass().getName());
							log.print(signature);
							log.print("processed ");
							log.print(rowIndex);
							log.print(" for ");
							log.print(name);
							log.print(", more than max Row ");
							log.print(maxRow);
							log.print(", create a new sheet name ");
							log.print(name);
							log.print(" (");
							log.print(sheetNameNum);
							log.println(")");
							
							sheet = wb.createSheet(name + " (" + sheetNameNum + ")");
							sheetNameNum++;
							rowIndex = 0;
							
							row = sheet.createRow(rowIndex);
							for (int columnIndex = 0; columnIndex < sheetTitles.size(); columnIndex++) {
								short cellIndex = (short)(columnIndex);
								HSSFCell cell = row.createCell(cellIndex);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					            cell.setCellValue(new HSSFRichTextString((String)sheetTitles.get(columnIndex)));
							}
							rowIndex++;
						}
						
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
					if (rowIndex > 50000) {
						log.print(getClass().getName());
						log.print(signature);
						log.print("processed ");
						log.print(rowIndex);
						log.print(" for ");
						log.println(name);
					}
					
					rows.close();
					query.close();
				}
			} catch (SQLException e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(name);
				log.print(",");
				log.print(sql);
				log.print(" ");
				log.println(e.getMessage());
				e.printStackTrace();
				result = false;
				addErrorMessage(sql+" "+e.getMessage());
			}
		}
		else {
			log.print(getClass().getName());
			log.print(signature);
			log.print(name);
			log.print(",");
			log.print(sql);
			log.print(" this SQL requires a ");
			log.print(conName);
			log.println(" connection and it was not found");
			result = false;
			addErrorMessage(sql+" this SQL requires a "+conName+" connection and it was not found");
		}
		
		return result;
	}
	
	/**
	 * Implements the BinaryReport interface
	 * @return an byte[] of the HSSFWorkbook
	 */
	public byte[] getBytes() {
		byte[] result = null;
		if (bos.size() > 0 && !dependentProcessRunning) {
			result = bos.toByteArray();
		}
		return result;
	}

	/**
	 * Implements the getter method of the DataView interface
	 * @return the DataView instance or the default empty DataView
	 */
	public DataView getDataView() {
		return dv;
	}

	/**
	 * @return
	 */
	public String getDependentProcessName() {
		return dependentProcessName;
	}

	/**
	 * @return
	 */
	public String getDependentProcessStartTime() {
		return dependentProcessStartTime;
	}
	/**
	 * Implements the BinaryReport interface
	 * @return an String for the file extension
	 */
	public String getExtension() {
		return "xls";
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
		return messageSb.toString();
	}
	private void addMessage(String msg){
		if (messageSb.length()>0){
			messageSb.append("\n");
		}
		messageSb.append(msg);
	}
	private void addErrorMessage(String msg){
		addMessage("Error: "+msg);
	}
	/**
	 * If processID is set that means another process may be dependent on the process completing
	 * If dependentProcessID is set then we cannot run if that process is running
	 * @return true is everything goes as planned
	 */
	private boolean initialize() {
		final String signature = ".initialize(): ";
		// need at least one connection to run queries
		boolean result = !jdbcCon.isEmpty();
		boolean canRun = true;
		PreparedStatement query = null;
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));

		try {		
			if (dependentProcessID > -1) {
				if (jdbcCon.contains("PDH")) {
					query = ((Connection) jdbcCon.get("PDH")).prepareStatement(runAllowedSQL);
					query.setInt(1, dependentProcessID);
					// This query will only return a row if CATODS is running
					ResultSet row = query.executeQuery();
					dependentProcessRunning = row.next();
					if (dependentProcessRunning) {
						dependentProcessName = row.getString(1);
						dependentProcessStartTime = row.getString(2);
						result = false;
					}
				}
				else {
					log.print(getClass().getName());
					log.print(signature);
					log.println(" a PDH connection is required to check the eacm.ifmlock table");
					result = false;
				}
			}
			canRun &= result;
			if (processID > -1 && canRun) { 
				if (jdbcCon.containsKey("PDH")) {
					query = ((Connection) jdbcCon.get("PDH")).prepareStatement(runStartSQL);
					query.setInt(1, processID);
					if (query.executeUpdate() == 0) {
						log.print(getClass().getName());
						log.print(signature);
						log.print("process id ");
						log.print(processID);
						log.println(" is not in eacm.ifmlock table");
						result = false;
					}
				}
				else {
					log.print(getClass().getName());
					log.print(signature);
					log.println(" a PDH connection is required to set the process start in the eacm.ifmlock table");
					result = false;
				}
			}
			canRun &= result;
		}
		catch (SQLException e) {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
		}
		if (result) {
			int nTabs = tabNames.size();
			for (int tabIndex = 0; tabIndex < nTabs; tabIndex++) {
				result &= createSheet((String)tabNames.get(tabIndex), (String)tabSQL.get(tabIndex), (String) sqlType.get(tabIndex));
			}
			
			try {
				wb.write(bos);
			} catch (IOException e) {
				result = false;
				log.print(getClass().getName());
				log.print(signature);
				log.println(e.getMessage());
				addErrorMessage(e.getMessage());
			}
		}
		else {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.println(" no Connection to the ODS or PDH was provided.");
			addErrorMessage(" no Connection to the ODS or PDH was provided.");
		}
		try {
			if (processID > -1 && canRun) {
				if (jdbcCon.containsKey("PDH")) {
					query = ((Connection) jdbcCon.get("PDH")).prepareStatement(runEndSQL);
					query.setInt(1, processID);
					if (query.executeUpdate() == 0) {
						log.print(getClass().getName());
						log.print(signature);
						log.println("process id 30 is not in eacm.ifmlock table");
					}
				}
			}
		}
		catch (SQLException e) {
			result = false;
			log.print(getClass().getName());
			log.print(signature);
			log.println(e.getMessage());
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
		hasPassed = result;
		return result;
	}

	/**
	 * @return
	 */
	public boolean isDependentProcessRunning() {
		return dependentProcessRunning;
	}

	/**
	 * (non-Javadoc)
	 * @see com.ibm.transform.oim.eacm.xalan.JDBCConnection#setConnection(java.sql.Connection, java.lang.String)
	 */
	public boolean setConnection(Connection con, String name) {
		boolean result = con != null && name != null;
		if (result) {
			jdbcCon.put(name,con);
		}
		return result;
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
			result &= initialize();

		}
		return result;
	}

	/**
	 * @param i
	 */
	public void setDependentProcessID(int i) {
		dependentProcessID = i;
	}

	/**
	 * @param string
	 */
	public void setDependentProcessName(String string) {
		dependentProcessName = string;
	}

	/**
	 * Implements the setter method of the Log interface
	 * @param anIdentifier String (Job ID or Session ID)
	 */
	public boolean setIdentifier(String anIdentifier) {
		return log.setIdentifier(anIdentifier);
	}

	/**
	 * @param i
	 */
	public void setProcessID(int i) {
		processID = i;
	}

}
