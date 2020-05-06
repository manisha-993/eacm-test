// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.util;

import java.util.*;
import java.io.*;
import java.text.*;

/**********************************************************************************
 * This class will build a fixed format table.  It must be enclosed in <pre> tags
 * to maintain formatting.
 *
 */
// $Log: FixedFormatTable.java,v $
// Revision 1.2  2006/01/25 18:31:30  wendy
// AHE copyright
//
// Revision 1.1  2005/12/08 22:17:18  wendy
// Init for use with FeatureMatrix in Sales Manual rpt
//
//
public class FixedFormatTable
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";
    private Vector strVct = null;
    private String initStr = "";
    private int tblWidth = 0;

    /**
     * Constructor
     * width is max width of the table, table will be padded with blanks to reach that length
     *
     *@param  width  maximum width of the table
     */
    public FixedFormatTable(int width)
    {
        strVct = new Vector(1);
        setTableWidth(width);
    }

    /**
     * Reset the table.  setTableWidth() must be called to init the table again
     */
    public void clear()
    {
        strVct.clear();
        initStr = "";
        tblWidth=0;
    }

    /**
     * Init the table
     *@param width int
     */
    public void setTableWidth(int width)
    {
        String blanks =
        "                                                                                "; // 80 chars
        StringBuffer sb= new StringBuffer(blanks);

        clear();

        tblWidth = width;
        // set init string
        while(sb.length()<tblWidth) {
            sb.append(blanks); }
        if(sb.length()>tblWidth) {  // don't exceed table width
            sb.delete(tblWidth, sb.length());
        }
        initStr = sb.toString();
    }

    /**
     * Get row count
     *@return int
     */
    public int getRowCount()
    {
        return strVct.size();
    }
    /**
     * Get row length to determine how much room is left on the row
     *@param rowNumber int
     *@return int
     */
    public int getRowLength(int rowNumber)
    {
        int len=0;
        if (strVct.size()+1>rowNumber){
            // only trim trailing blanks, need to know how much room is left on the row
            String rowText = strVct.elementAt(rowNumber-1).toString();
            StringCharacterIterator sci = new StringCharacterIterator(rowText);
            char ch = sci.last();
            int last = 0;
            while(ch != CharacterIterator.DONE)
            {
                if(ch!=' '){
                    break;
                }
                last++;
                ch = sci.previous();
            }

            len = rowText.length()-last;
        }
        return len;
    }
    /**
     * Set content into the table row.
     *
     *@param  rowNumber row to insert content into, 1-based
     *@param  startPos  starting position to insert value, 1-based
     *@param  value     char to insert
     *@throws IOException if start position exceeds table max width
     */
    public void setRowContent(int rowNumber, int startPos, char value) throws IOException
    {
        char data[] = {value};
        setRowContent(rowNumber, startPos, new String(data));
    }

    /**
     * Set content into the table row.
     *
     *@param  rowNumber row to insert content into, 1-based
     *@param  startPos  starting position to insert value, 1-based
     *@param  value     String to insert, could be truncated to fit max width
     *@throws IOException if start position exceeds table max width
     */
    public void setRowContent(int rowNumber, int startPos, String value) throws IOException
    {
        StringBuffer sb;
        // check for startpos exceeding tblWidth
        if(startPos>tblWidth) {
            throw new IOException("Start position ("+startPos+") exceeds table width ("+tblWidth+").");
        }

        while(strVct.size()<rowNumber)
        {
            strVct.addElement(new StringBuffer(initStr));
        }

        sb = (StringBuffer)strVct.elementAt(rowNumber-1);
        if (value==null) {
            value="";
        }

        // stringbuffer is 0-based
        startPos--;
        sb.replace(startPos, startPos+value.length(), value);
        if(sb.length()>tblWidth) {  // don't exceed table width
            sb.delete(tblWidth, sb.length());
        }
    }

    /**
     * Set content into the table row.
     *
     *@param  rowNumber row to insert content into, 1-based
     *@param  startPos  starting position to insert value, 1-based
     *@param  endPos    ending position to insert value, 1-based
     *@param  value     String to insert, could be truncated to fit end-start length
     *@throws IOException if start position exceeds table max width
     */
    public void setRowContent(int rowNumber, int startPos, int endPos, String value) throws IOException
    {
        StringBuffer sb;
        // check for startpos exceeding tblWidth
        if(startPos>tblWidth) {
            throw new IOException("Start position ("+startPos+") exceeds table width ("+tblWidth+").");
        }

        while(strVct.size()<rowNumber)
        {
            strVct.addElement(new StringBuffer(initStr));
        }

        sb = (StringBuffer)strVct.elementAt(rowNumber-1);
        if (value==null) {
            value="";
        }

        // if value length exceeds specified length, truncate it
        if (value.length() > (endPos-startPos+1))
        {
            value = value.substring(0,(endPos-startPos+1));
        }

        // stringbuffer is 0-based
        sb.replace(startPos-1, startPos+value.length(), value);
        if(sb.length()>tblWidth) {  // don't exceed table width
            sb.delete(tblWidth, sb.length());
        }
    }

    /**
     * Append content from one table to this table
     *
     *@param  ffTbl FixedFormatTable
     */
    public void append(FixedFormatTable ffTbl)
    {
        // set all new rows to this table's length and append
        for (int i=0; i<ffTbl.strVct.size(); i++){
            StringBuffer sb = (StringBuffer)ffTbl.strVct.elementAt(i);
            if(sb.length()>tblWidth) {  // don't exceed table width
                sb.delete(tblWidth, sb.length());
            }
            strVct.addElement(sb);
        }
    }

    /**
     * Get the generated table.
     * trim trailing blanks
     *@return String table that must be enclosed in <pre> to maintain formatting
     */
    public String getTable()
    {
        StringBuffer sb = new StringBuffer();

        for (int i=0; i<strVct.size(); i++){
            String txt = strVct.elementAt(i).toString();
            int curLen = getRowLength(i+1);  // index is 1 based
            sb.append(txt.substring(0,curLen)+PokUtils.NEWLINE);
        }

        return sb.toString();
    }

}
