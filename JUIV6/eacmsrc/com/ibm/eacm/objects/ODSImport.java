//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2014  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.

package com.ibm.eacm.objects;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jopendocument.dom.ODValueType;
import org.jopendocument.dom.spreadsheet.Cell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import com.ibm.eacm.edit.Importable;
import com.ibm.eacm.preference.PrefMgr;


/**********************************************************************************
* This class is used to import values from a ods spreadsheet to populate new entityitems
* The first row contains the attribute codes
* Requirements:
* - Attributes must be in correct order if they have filters,
* like Hardware -> System -> Base (COFCAT -> COFSUBCAT ->COFGRP)
* - Cell formatting for numeric values is required for proper parsing, like decimal points
* - Dates will be converted into EACM format - YYYY-MM-DD
* - Flag values must be correct case and separated by ';'
* - Done in a createaction
* RCQ 288700
* @author Wendy Stimpson
*/
//$Log: ODSImport.java,v $
//Revision 1.1  2014/01/22 20:37:14  wendy
//RCQ 288700
//
public class ODSImport implements EACMGlobals, Importable
{
	private static final DateFormat DATE_DF = new SimpleDateFormat("yyyy-MM-dd");
	private static final Logger logger;
	static {
		logger = Logger.getLogger(EDIT_PKG_NAME);
		logger.setLevel(PrefMgr.getLoggerLevel(EDIT_PKG_NAME, Level.INFO));
	}
	
	private SpreadSheet spreadSheet = null;
	private Vector<String> warningMsgsVct = new Vector<String>(1);

	/**
	 * Constructor ODSImport
	 *
	 * @param filename String ods spreadsheet file name
	 *
	 * @exception IOException
	 *
	 */
	public ODSImport(String filename) throws IOException
	{
		// Load the file.
		File file = new File(filename);
		spreadSheet = SpreadSheet.createFromFile(file);
	}

	/**
	 * release memory
	 */
	public void dereference() {
		spreadSheet = null;
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
	 * Read in the spreadsheet and create new entities
	 *
	 */
	public void process(Importable importable) throws IOException
	{
		if(spreadSheet.getSheetCount()>1){
			addWarning("More than one sheet found.  Only the first one will be used.");
		}

		Sheet sheet = spreadSheet.getSheet(0);

        //Get row count and column count
        int nColCount = sheet.getColumnCount();
        int nRowCount = sheet.getRowCount();
        
        logger.log(Level.FINE,"Rows: "+nRowCount+" Cols: "+nColCount);
        Cell<?> cell = null;
        
        //get the attributes
        String attrCodes[] = new String[nColCount]; // first row in ss must be the attribute codes
		for (int i=0; i<attrCodes.length; i++){
			attrCodes[i]=null;
		}
        
        for(int nColIndex = 0;nColIndex < nColCount; nColIndex++) {
        	cell = sheet.getImmutableCellAt(nColIndex, 0);
        	attrCodes[nColIndex] = cell.getValue().toString().trim().toUpperCase();
        	logger.log(Level.FINE, "CELL col="+ (nColIndex+1)+ " ATTRCODE=" + attrCodes[nColIndex]);
        }
        
		// were all attrcodes filled in?
		for(int i=0;i<attrCodes.length;i++){
			if(attrCodes[i].length()==0){
				// cant have any empty cells in the attribute code row
				throw new IOException("ODSImport Error: Missing attribute code in cell "+(i+1));
			}
		}
		
    	String valueArray[] = new String[attrCodes.length];
		
        //Iterating through each row of the selected sheet
        for(int nRowIndex = 1; nRowIndex < nRowCount; nRowIndex++) {
        	// clear the value array
    		for (int i=0; i<valueArray.length; i++){
    			valueArray[i]=null;
    		}
    		
        	//Iterating through each column
        	for(int nColIndex = 0; nColIndex < nColCount; nColIndex++) {
        		cell = sheet.getImmutableCellAt(nColIndex, nRowIndex);
        		String value = "";
        		
        		ODValueType valuetype = cell.getValueType();
        		if(valuetype==null){ // will be null if the cell is empty
        			valuetype = ODValueType.STRING;
        		}
        		switch(valuetype){
				case DATE:
					// make sure it is in yyyy-MM-dd format
					value = DATE_DF.format((Date)cell.getValue());
					break;
				// handle all others as textvalues
				case STRING:
				case FLOAT:
				case TIME:
				case BOOLEAN:
				case CURRENCY:
				case PERCENTAGE:
				default:
					value = cell.getTextValue();
					break;
        		}
        		
        		logger.log(Level.FINE, "CELL ["+nRowIndex+","+ (nColIndex+1)+"] VALUE=" + value+ " valuetype=" + valuetype);
				valueArray[nColIndex] = value.trim();
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
				EANBusinessRuleException bre = importable.processXLSImport(nRowIndex,attrCodes, valueArray);
				if (bre != null && bre.getErrorCount()>0){
					// add row info and hang onto any error messages for this row
					addWarning(bre, nRowIndex);
				}
			}else{
				//account for attribute row +1
				logger.log(Level.FINE, "Entity will not be created, no cell values found for ROW "+(nRowIndex+1));
			}
        }
	}
	
	/**
     * processXLSImport import from Excel xls ss or from Apache OpenDocument ods file
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
			ODSImport hssf = new ODSImport(args[ 0 ]);
			hssf.process(hssf);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}

