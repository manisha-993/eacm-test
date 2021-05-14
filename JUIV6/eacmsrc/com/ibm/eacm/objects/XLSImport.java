//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2012  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package com.ibm.eacm.objects;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.*;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import com.ibm.eacm.edit.Importable;
import com.ibm.eacm.preference.PrefMgr;


/**********************************************************************************
 * This class is used to import values from a xls workbook to populate new entityitems
 * The xls file must be a workbook, not a worksheet and the first row contains the attribute codes
 * for the column
 * Requirements:
 * - Source MUST be a workbook
 * - Attributes must be in correct order if they have filters,
 * like Hardware -> System -> Base (COFCAT -> COFSUBCAT ->COFGRP)
 * - Cell formatting for numeric values is required for proper parsing, like decimal points
 * - Dates will be converted into EACM format - YYYY-MM-DD
 * - Flag values must be correct case and separated by ';'
 * - Done in a createaction
 * @author Wendy Stimpson
 */
//$Log: XLSImport.java,v $
//Revision 1.7  2019/01/18 06:50:51  dlwlan
//Story 1940765? Import function supported for 97-2003 version of excel only
//
//Revision 1.6  2015/06/10 17:47:24  stimpsow
//verify number of physical cells match the real data
//
//Revision 1.5  2013/08/19 15:09:22  wendy
//add msg when formula is found in cell
//
//Revision 1.4  2013/07/29 19:35:51  wendy
//if all values are empty do not create a row
//
//Revision 1.3  2013/07/23 17:57:47  wendy
//Add more checks and logging
//
//Revision 1.2  2013/07/18 18:24:23  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:12  wendy
//Initial code
//
public class XLSImport implements EACMGlobals, Importable
{
	private static final DateFormat DATE_DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final DecimalFormat SINGLE_DECIMAL_DF = new DecimalFormat("0.0");
	private static final DecimalFormat DOUBLE_DECIMAL_DF =  new DecimalFormat("0.00");

	private static final Logger logger;
	static {
		logger = Logger.getLogger(EDIT_PKG_NAME);
		logger.setLevel(PrefMgr.getLoggerLevel(EDIT_PKG_NAME, Level.INFO));
	}
	
	private HSSFWorkbook hssfworkbook = null;
	private Vector<String> warningMsgsVct = new Vector<String>(1);

	/**
	 * Constructor XLSImport
	 *
	 * @param filename String xls workbook file name
	 *
	 * @exception IOException
	 *
	 */

	public XLSImport(String filename)
	throws IOException
	{
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));

		hssfworkbook = new HSSFWorkbook(fs);
	}

	/**
	 * release memory
	 */
	public void dereference() {
		hssfworkbook = null;
		warningMsgsVct.clear();
		warningMsgsVct = null;
	}
	/**
	 * get warning messages
	 */
	public String getWarnings() {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<warningMsgsVct.size(); i++){
			if (sb.length()>0){
				sb.append("\n");
			}
			sb.append(warningMsgsVct.elementAt(i).toString());
		}
		return sb.toString();
	}

	private void addWarning(EANBusinessRuleException bre, int rowCount){
		// add row info
		warningMsgsVct.add("\nError(s) found in row "+(rowCount+1));
		// hang onto any error messages for this row, they may be duplicates of another row
		// so gather them here
		Vector<String> tmp = new Vector<String>(1);
		addWarning(tmp,bre.getMessage());
		warningMsgsVct.addAll(tmp);
		tmp.clear();
	}

	private void addWarning(Vector<String> vct, String str) {
		StringTokenizer st = new StringTokenizer(str, "\n");
		while (st.hasMoreTokens()) {
			String msg = st.nextToken();
			if (!vct.contains(msg)){
				vct.add(msg);
				logger.log(Level.WARNING, msg);
			}
		}
	}

	private void addWarning(String str) {
		addWarning(warningMsgsVct, str);
	}

	/**
	 * Formats a number or date cell
	 */
	private static String formatNumberDateCell(HSSFCell cell, String attrCode) {
		short formatIndex = cell.getCellStyle().getDataFormat();
		double value = cell.getNumericCellValue();
		String format = "0.0"; // maintain leading 0 before decimal point
		if (formatIndex < HSSFDataFormat.getNumberOfBuiltinBuiltinFormats()){
			format = HSSFDataFormat.getBuiltinFormat(formatIndex);
		}else{
			logger.log(Level.SEVERE, attrCode+" formatindex is out of range: "+formatIndex);
		}

		// Is it a date?
		if(HSSFDateUtil.isADateFormat(formatIndex,format) &&
				HSSFDateUtil.isValidExcelDate(value)) {
			// Format as a EACM date
			Date d = HSSFDateUtil.getJavaDate(value, false);
			return DATE_DF.format(d);
		} else {
			logger.log(Level.FINE, "numeric format "+format);
			if(format == "General") {
				// Some sort of wierd default, assume it was meant to be int
				// user really should format the cells but attempt to make a guess
				Double valDbl = new Double(value); // avoid casting to (int)
				int ivalue = valDbl.intValue();
				String strValue = Integer.toString(ivalue);
				logger.log(Level.FINE, "numeric format strValue "+strValue);
				if (value>0 && ivalue==0){ // integer truncated it
					logger.log(Level.SEVERE, attrCode+" numeric format as integer "+
							strValue+" did not match double value "+value+", trying decimal format");
					// try this as a decimal
					strValue = SINGLE_DECIMAL_DF.format(value);
					// check to see if this was rounded up
					Double test = new Double(strValue);
					if (!test.equals(valDbl)){
						logger.log(Level.FINE, attrCode+" numeric format as single decimal "+
								strValue+" did not match double value "+value+", trying double decimal format");
						// try as 2 digits
						strValue = DOUBLE_DECIMAL_DF.format(value);
					}
				}
				return strValue;
			}

			// Format as a number
			DecimalFormat df = new DecimalFormat(format);
			return df.format(value);
		}
	}

	/**
	 * Read in the workbook and create new entities
	 *
	 */
	public void process(Importable importable) throws IOException
	{
		if(hssfworkbook.getNumberOfSheets()>1){
			addWarning("More than one sheet found.  Only the first one will be used.");
		}

		HSSFSheet sheet = hssfworkbook.getSheetAt(0);
		String attrCodes[] = null; // first row in ss must be the attribute codes

		Iterator<?> rowItr = sheet.rowIterator();
		int rowCount = -1;
		while (rowItr.hasNext()) {
			boolean getAttrCodes = false;
			HSSFRow row  = (HSSFRow)rowItr.next();
			int rowNum = row.getRowNum()+1; // +1 for logical location
			int firstCell = -1;
			String valueArray[] = null;

			logger.log(Level.FINE, "ROW " + rowNum);
			if (attrCodes == null) {
				attrCodes = new String[row.getPhysicalNumberOfCells()];
				for (int i=0; i<attrCodes.length; i++){
					attrCodes[i]=null;
				}
				getAttrCodes = true;
				logger.log(Level.FINE, "Found " + attrCodes.length+" attrcodes");
			}else{
				rowCount++; // just data rows, not attribute code row
				if (valueArray==null){
					valueArray = new String[attrCodes.length];
				}
				for (int i=0; i<valueArray.length; i++){
					valueArray[i]=null;
				}
			}
			Iterator<?> cellItr = row.cellIterator();
			while (cellItr.hasNext()) {
				HSSFCell cell  = (HSSFCell)cellItr.next();
				int cellNum = cell.getCellNum(); // must use physical location
				String value = null;

				if (getAttrCodes){
					if (firstCell == -1){
						firstCell = cellNum;
					}else{
						if ((++firstCell) != cellNum){
							for (int i=0; i<attrCodes.length; i++){
								attrCodes[i]=null;
							}
							// cant have any empty cells in the attribute code row
							throw new IOException("XLSImport Error: Missing attribute code in cell "+(cellNum+1));
						}
					}
				}

				switch (cell.getCellType())
				{
				case HSSFCell.CELL_TYPE_NUMERIC:
					logger.log(Level.FINE, "numeric "+rowNum+" cell: "+(cellNum+1));
					value = formatNumberDateCell(cell,attrCodes[cellNum]+"["+rowNum+"]");
					break;
				case HSSFCell.CELL_TYPE_STRING:
					logger.log(Level.FINE, "string "+rowNum+" cell: "+(cellNum+1));
					value = cell.getRichStringCellValue().getString();
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					continue;
				case HSSFCell.CELL_TYPE_FORMULA:
					addWarning("Unsupported FORMULA cell type found in row: "+rowNum+" cell: "+(cellNum+1));
					String formula = "formula";;
					try{
						formula = cell.toString(); // get exception if workbook is invalid
					}catch(Exception x){
						logger.log(Level.WARNING,"Error trying to read cell formula: ",x); 
					}
					logger.log(Level.WARNING, "unknown "+rowNum+" cell: "+(cellNum+1)+" "+formula);
                    continue;
				default:
					addWarning("Unsupported cell type ["+cell.getCellType()+"] found in row: "+rowNum+" cell: "+(cellNum+1));
					logger.log(Level.WARNING, "unknown "+rowNum+" cell: "+(cellNum+1)+" "+cell);
					continue;
				}
				
				if (getAttrCodes){
					logger.log(Level.FINE, "CELL col="+ (cellNum+1)+ " ATTRCODE=" + value);
					attrCodes[cellNum] = value.trim().toUpperCase();
				}else{
					logger.log(Level.FINE, "CELL col="+ (cellNum+1)+ " VALUE=" + value);
					valueArray[cellNum] = value.trim();
				}
			}// end cell iterator loop
			if (valueArray != null){
				// were all attrcodes filled in?
				for(int i=0;i<attrCodes.length;i++){
					if(attrCodes[i]==null){
						// cant have any empty cells in the attribute code row
						throw new IOException("XLSImport Error: Missing attribute code in cell "+(i+1));
					}
				}
		
				// were any values found?
				boolean fnd = false;
				for(int i=0;i<valueArray.length;i++){
					if(valueArray[i]!=null && valueArray[i].trim().length()>0){
						fnd = true;
						break;
					}
				}
				if(fnd){
					// load all of these values for this row into EACM
					EANBusinessRuleException bre = importable.processXLSImport(rowCount,attrCodes, valueArray);
					if (bre != null && bre.getErrorCount()>0){
						// add row info and hang onto any error messages for this row
						addWarning(bre, rowCount);
					}
				}else{
					logger.log(Level.FINE, "Entity will not be created, no cell values found for ROW "+rowNum);
				}
			}
						
			if (getAttrCodes){
				// verify the getPhysicalNumberOfCells() matches the attribute codes filled in.. sometimes it is way off
				// were all attrcodes filled in?
				for(int i=0;i<attrCodes.length;i++){
					if(attrCodes[i]==null){
						logger.log(Level.SEVERE, "physical number of cells did not match the data. Only "+i+ 
								" ATTRCODEs of "+attrCodes.length+" found");

						String tmp[] = new String[i];
						for(int t=0;t<i;t++){
							tmp[t] = attrCodes[t];
						}
						attrCodes = tmp; // replace it, it didnt have as many cells filled in
					}
				}
			}
		} // end row iterator loop
	}
	/**
	 * processXLSImport import from Excel xls ss
	 * @param index int counter into number of rows in ss
	 * @param attrCodes String[] attribute codes
	 * @param attrValue String[], some may be null
	 */
	public EANBusinessRuleException processXLSImport(int index, String[] attrCodes, String[] attrValue)
	{
		for (int i=0; i<attrValue.length; i++){
			String value = attrValue[i];
			if (value !=null){ // was a value in this cell in the ss
				String attrCode = attrCodes[i];
				logger.log(AlwaysLevel.ALWAYS, "loading "+attrCode+" with "+value);
			}
		}
		return null;
	}

	/**
	 * Method main
	 *
	 * @param args
	 *
	 */
	public static void main(String [] args)
	{
		try	{
			XLSImport hssf = new XLSImport(args[ 0 ]);
			hssf.process(hssf);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * importFromFile
	 *
	 * @param _c
	 */
	public static void importFromFile(Importable importable) {
		logger.log(Level.FINE, "entered with "+importable);
        int reply = -1;
        Component comp = null;
        if (importable instanceof Component){
        	comp = (Component)importable;
        }

		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		String userdir = System.getProperty("user.dir");
		//userdir = "C:\\dev\\eanc30\\export4.xls"; //testing only remove this
		chooser.setAcceptAllFileFilterUsed(false);
		if (userdir!=null){
			chooser.setCurrentDirectory(new File(userdir));
		}
		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			private String getExtension(File f) {
				String ext ="";
				if(f != null) {
					String filename = f.getName();
					int i = filename.lastIndexOf('.');
					if(i>0 && i<filename.length()-1) {
						ext= filename.substring(i).toLowerCase();
					}
				}
				return ext;
			}
			public boolean accept(File f) {
				if (f.isDirectory()){
					return true;
				}
				String extension = getExtension(f);
				if (extension.equals(".xls")) {
					return true;
				} else if (extension.equals(".xlsx")) {
					return true;
				} else {
					return false;
				}
			}

			//The description of this filter
			public String getDescription() {
				return "Microsoft Excel Workbook (*.xls and *.xlsx)";
			}
		});

        reply = chooser.showOpenDialog(comp);
        if (reply == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            if (file != null) {
            	try	{
            		// if user selected xls file then use new import code
                    if (file.getName().toLowerCase().endsWith(".xls")||file.getName().toLowerCase().endsWith(".xlsx")) {
                    	XLSImport xlsImport = new XLSImport(file.getAbsolutePath());
                    	xlsImport.process(importable);
                    	String warning = xlsImport.getWarnings();
                    	// display warnings
                    	if (warning.length()>0){
                    	//	com.ibm.eacm.ui.UI.showMessage(null,"warning-title", JOptionPane.WARNING_MESSAGE, "warning-acc",
        					//		warning);
                    		com.ibm.eacm.ui.UI.showWarning(null,warning);
                    	}
                    	xlsImport.dereference();
                    }
        		}
        		catch (IOException e){
        			com.ibm.eacm.ui.UI.showException(comp,e);
        		}
            }
        }
	}
}

