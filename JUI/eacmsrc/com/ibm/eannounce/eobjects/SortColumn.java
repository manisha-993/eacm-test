// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2009 All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eannounce.eobjects;

/**
 * Name and column index are needed for sort.  Name alone is not enough because multiple entities may
 * be in one row, with duplicate column names like 'Domain'
 *
 */
//$Log: SortColumn.java,v $
//Revision 1.1  2009/04/08 19:57:49  wendy
//Sort needs more than column name to find the attribute
//
public class SortColumn {
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2009  All Rights Reserved.";
 
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.1 $";
    
	private String name=null;
	private int colIndex = 0;
	public SortColumn(String n, int i){
		name = n;
		colIndex = i;
	}
	public String toString() {
		return name;
	}
	public int getColumnIndex() { return colIndex;}
}
