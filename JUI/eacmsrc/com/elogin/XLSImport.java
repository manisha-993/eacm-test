// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.elogin;

import java.io.IOException;
import java.io.FileInputStream;
import java.util.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.usermodel.*;

import COM.ibm.eannounce.objects.EANBusinessRuleException;
import com.ibm.eannounce.eforms.edit.Importable;


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
 */
// $Log: XLSImport.java,v $
// Revision 1.4  2008/02/28 16:13:04  wendy
// Correct output of errors if more than one row has the same error
//
// Revision 1.3  2008/02/20 16:42:10  wendy
// add comment
//
// Revision 1.2  2008/02/20 15:47:07  wendy
// Make formats constants
//
// Revision 1.1  2008/02/19 16:32:52  wendy
// Add support for importing an XLS file
//
public class XLSImport implements Importable
{
    private HSSFWorkbook hssfworkbook = null;
    private Vector warningMsgsVct = new Vector(1);
    private static final DateFormat DATE_DF = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat SINGLE_DECIMAL_DF = new DecimalFormat("0.0");
    private static final DecimalFormat DOUBLE_DECIMAL_DF =  new DecimalFormat("0.00");

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
		Vector tmp = new Vector(1);
		addWarning(tmp,bre.getMessage());
		warningMsgsVct.addAll(tmp);
		tmp.clear();
	}
		
	private void addWarning(Vector vct, String str) {
        StringTokenizer st = new StringTokenizer(str, "\n");
        while (st.hasMoreTokens()) {
            String msg = st.nextToken();
			if (!vct.contains(msg)){
				vct.add(msg);
				System.err.println("XLSImport:warning "+msg);
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
			System.err.println("XLSImport.formatNumberDateCell "+attrCode+" formatindex is out of range: "+formatIndex);
		}

     	// Is it a date?
     	if(HSSFDateUtil.isADateFormat(formatIndex,format) &&
     			HSSFDateUtil.isValidExcelDate(value)) {
     		// Format as a EACM date
     		Date d = HSSFDateUtil.getJavaDate(value, false);     		
	        return DATE_DF.format(d);
     	} else {
//System.err.println("numeric format "+format);
     		if(format == "General") {
     			// Some sort of wierd default, assume it was meant to be int
     			// user really should format the cells but attempt to make a guess
				Double valDbl = new Double(value); // avoid casting to (int)
                int ivalue = valDbl.intValue();
                String strValue = Integer.toString(ivalue);
//System.err.println("numeric format strValue "+strValue);
                if (value>0 && ivalue==0){ // integer truncated it
					System.err.println("XLSImport.formatNumberDateCell "+attrCode+" numeric format as integer "+
							strValue+" did not match double value "+value+", trying decimal format");
					// try this as a decimal
					strValue = SINGLE_DECIMAL_DF.format(value);
					// check to see if this was rounded up
					Double test = new Double(strValue);
					if (!test.equals(valDbl)){
						System.err.println("XLSImport.formatNumberDateCell "+attrCode+" numeric format as single decimal "+
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

		java.util.Iterator rowItr = sheet.rowIterator();
		int rowCount = -1;
		while (rowItr.hasNext()) {
			boolean getAttrCodes = false;
			HSSFRow row  = (HSSFRow)rowItr.next();
			int rowNum = row.getRowNum()+1; // +1 for logical location
			int firstCell = -1;
			String valueArray[] = null;

	//		System.out.println("ROW " + rowNum);
			if (attrCodes == null) {
				attrCodes = new String[row.getPhysicalNumberOfCells()];
				for (int i=0; i<attrCodes.length; i++){
					attrCodes[i]=null;
				}
				getAttrCodes = true;
			}else{
				rowCount++; // just data rows, not attribute code row
				if (valueArray==null){
					valueArray = new String[attrCodes.length];
				}
				for (int i=0; i<valueArray.length; i++){
					valueArray[i]=null;
				}
			}
			java.util.Iterator cellItr = row.cellIterator();
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
//System.err.println("numeric "+rowNum+" cell: "+(cellNum+1));
					value = formatNumberDateCell(cell,attrCodes[cellNum]+"["+rowNum+"]");
					break;
				case HSSFCell.CELL_TYPE_STRING:
//System.err.println("string "+rowNum+" cell: "+(cellNum+1));
					value = cell.getRichStringCellValue().getString();
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					continue;
				default:
					addWarning("Unsupported cell type ["+cell.getCellType()+"] found in row: "+rowNum+" cell: "+(cellNum+1));
//System.err.println("unknown "+rowNum+" cell: "+(cellNum+1)+" "+cell);
				continue;
				}
//System.out.println("CELL col="+ (cellNum+1)+ " VALUE=" + value);
				if (getAttrCodes){
					attrCodes[cellNum] = value.trim().toUpperCase();
				}else{
					valueArray[cellNum] = value.trim();
				}
			}// end cell iterator loop
			if (valueArray != null){
				// load all of these values for this row into EACM
				EANBusinessRuleException bre = importable.processXLSImport(rowCount,attrCodes, valueArray);
				if (bre != null && bre.getErrorCount()>0){
					// add row info and hang onto any error messages for this row
					addWarning(bre, rowCount);
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
    			System.out.println("loading "+attrCode+" with "+value);
    		}
    	}
    	return null;
    }
    /**
     * importString import tab delimited file, this was the original form of import
     * @param _s String[] attribute codes
     */
    public void importString(String[] _s) {}

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
}

