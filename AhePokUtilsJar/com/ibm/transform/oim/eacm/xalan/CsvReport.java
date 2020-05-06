/* 
 * (c) Copyright International Business Machines Corporation, 2015
 * All Rights Reserved.
 */
package com.ibm.transform.oim.eacm.xalan;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Hashtable;
import java.util.Vector;

import com.ibm.transform.oim.eacm.util.Log;
import com.ibm.transform.oim.eacm.util.Logger;

/**
 * 
 * This class will produce a csv file for the Current and Approved views
 * RCQ00337768-RQ Report for Price Calc limits to 65K
 * 
 * The delimiter defaults to ';' but can be overridden in the Configuration.xml file  
 * change the Configuration.xml file
 * 
 * 
 * in Configuration.xml
        <Entity type="EXTPRCCALCRPT">
            <Attribute code="PRCFEATUREABR">
                <Style xsl="/com/ibm/transform/oim/eacm/xalan/style/ExcelReport.xsl" zip="true">
                    <UsesConnection>PDH</UsesConnection>
                    <DGTitle>PriceCalc - Features Report - US and CA</DGTitle>
                    <XSLParam name="dgTitle">PriceCalc - Features Report - US and CA</XSLParam>
                    <XSLParam name="xmlabr">
                        <Obj type="com.ibm.transform.oim.eacm.xalan.XMLABR">
                            <Invoke method="setABRCode">
                             	<!-- Obj type="com.ibm.transform.oim.eacm.xalan.ExcelReport" -->
                                <Obj type="com.ibm.transform.oim.eacm.xalan.CsvReport"> 
                                    <Invoke method="addTabForSQL">
                                        <Obj>FEATURE</Obj>
                                        <Obj>SELECT * FROM price.PRCFEATURE WITH UR</Obj>
                                        <Obj>PDH</Obj>
                                    </Invoke>
                                    <Invoke method="setDataView">
                                        <Obj type="com.ibm.transform.oim.eacm.xalan.DataView">
                                            <!-- Invoke method="setDelimiter">
                                                <Obj>,</Obj>
                                            </Invoke -->
                                            <Invoke method="initialize" />
                                        </Obj>
                                    </Invoke>
                                </Obj>
                            </Invoke>
                        </Obj>
                    </XSLParam>
                </Style>
            </Attribute>
 ...
        </Entity>

 * 
 * $Log: CsvReport.java,v $
 * Revision 1.2  2015/02/06 18:57:04  stimpsow
 * Added support for internal quotes, current data does not contain quotes
 *
 * Revision 1.1  2015/01/29 12:54:07  stimpsow
 * RCQ00337768-RQ Report for Price Calc limits to 65K
 *
 */
public class CsvReport implements Log, ReturnCode, Data, BinaryReport, JDBCConnection {
	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);
	
	private ByteArrayOutputStream bos = new ByteArrayOutputStream();
	private PrintWriter pwriter = new PrintWriter(bos,true);
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
	private StringBuffer messageSb=new StringBuffer();
	private StringBuffer csvSb=new StringBuffer();

	public CsvReport() {	
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
	 * Creates the csv
	 * @return true if no problems
	 */
	private boolean createCsv(String name, String sql, String conName) {
		final String signature = ".createCsv(String.String,String): ";
		boolean result = jdbcCon.containsKey(conName);

		if (result) {
			String delimiter = dv.getDelimiter();
			try {
				Connection con = (Connection) jdbcCon.get(conName);
				Statement query = con.createStatement();
				if (query != null) {
					ResultSet rows = query.executeQuery(sql);
					ResultSetMetaData meta = rows.getMetaData();

					int nColumns = meta.getColumnCount();
					for (int columnIndex = 1; columnIndex <= nColumns; columnIndex++) {
						if(csvSb.length()>0){
							csvSb.append(delimiter);
						}
						csvSb.append(meta.getColumnLabel(columnIndex));
					}
					csvSb.append(NEWLINE);
					
					int rowcount=0;
					while (rows.next()) {
						StringBuffer rowSb = new StringBuffer();
						for (short columnIndex = 1; columnIndex <= nColumns; columnIndex++) {
							if(columnIndex>1){
								rowSb.append(delimiter);
							}
							switch (meta.getColumnType(columnIndex)) {
								case Types.INTEGER :
									int val = rows.getInt(columnIndex);
									if (!rows.wasNull()) {
										rowSb.append(new Integer(val));
									}else{
										rowSb.append("");
									}
									break;
	
								case Types.DATE :
									java.sql.Date dTmp = rows.getDate(columnIndex);
									if (!rows.wasNull()) {
										// convert to date format
										rowSb.append(dTmp.toString());
									}else{
										rowSb.append("");
									}
									break;
	
								default :
									String sTmp = rows.getString(columnIndex);
									if (!rows.wasNull()) {
										if(sTmp.indexOf('"')!=-1){
											// must make these ""
											sTmp = insertQuotes(sTmp);
										}
										rowSb.append("\""+sTmp.trim()+"\"");
									}else{
										rowSb.append("");
									}
									break;
							}
						}
						rowSb.append(NEWLINE);
						csvSb.append(rowSb);

						rowcount++;
					}
					if (rowcount > 50000) {
						log.print(getClass().getName());
						log.print(signature);
						log.print("processed ");
						log.print(rowcount);
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
			catch (Exception e) {
				log.print(getClass().getName());
				log.print(signature);
				log.print(name);
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
	 * the string contains internal ", these must be duplicated ""
	 * @return
	 */
	private String insertQuotes(String str){
		StringBuffer sb = new StringBuffer();
        StringCharacterIterator sci = new StringCharacterIterator(str);
        char ch = sci.first();
        while(ch != CharacterIterator.DONE) {
        	sb.append(ch);
        	if(ch == '"'){
             	sb.append('"');
        	}
            ch = sci.next();
        }
    
		return sb.toString();
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
		return "csv";
	}

	/**
	 * Implements the getter method of the Log interface
	 * @return String log identifier (Job ID or Session ID)
	 */
	public String getIdentifier() {
		return log.getIdentifier();
	}
	
	/**
	 * Implements the ReturnCode interface
	 * @return true if the csv file was successfully created
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
				result &= createCsv((String)tabNames.get(tabIndex), (String)tabSQL.get(tabIndex), (String) sqlType.get(tabIndex));
			}
			
			pwriter.write(csvSb.toString());
			pwriter.flush();
			pwriter.close();
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
