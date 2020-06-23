// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2012 All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.eacm.objects;

/**
 * Name and column index are needed for sort.  Name alone is not enough because multiple entities may
 * be in one row, with duplicate column names like 'Domain'
 * @author Wendy Stimpson
 */
//$Log: SortColumn.java,v $
//Revision 1.2  2013/07/18 18:24:23  wendy
//fix compiler warnings
//
//Revision 1.1  2012/09/27 19:39:13  wendy
//Initial code
//
//
public class SortColumn implements Comparable<Object> {
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2012  All Rights Reserved.";
 
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";
    
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
    /*******************************************
     * compare
     * @param o Object
     * @return int
     */
    public int compareTo(Object o)
    {
    	return (name+colIndex).compareTo(((SortColumn)o).name+((SortColumn)o).colIndex);
    }
}
