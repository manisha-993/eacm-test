// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

/*****************************************************************************
* Special character, now a separate class to satisfy jtest
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLSpecialChar.java,v $
// Revision 1.2  2013/07/18 18:43:51  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:58  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:20  tony
// This is the initial load of OPICM
//
class XMLSpecialChar implements Comparable<Object>
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    private String def;
    private String name;
    private String value;
    XMLSpecialChar(String v, String n, String d)
    {
        name = n;
        def = d;
        value = v;
    }
    String getName() {
        return name;
    }
    String getValue() {
        return value;
    }
    String getDef() {
        return def;
    }
    /*******************************************
     * get string value
     * @return String
     */
    public String toString()
    {
        return value+" "+name+" -- "+def+" --";
    }
    /*******************************************
     * compare
     * @param o Object
     * @return int
     */
    public int compareTo(Object o)
    {
        int valuex=0;
        if (name.compareToIgnoreCase(((XMLSpecialChar)o).name.toLowerCase())==0)
        {
            // same char just upper or lower case, group together with lowercase first
            if (name.charAt(0)>((XMLSpecialChar)o).name.charAt(0)){
                //return -1;
                valuex = -1;
            }
            else{
                //return 1;
                valuex = 1;
            }
        }

        if (valuex==0) {
            valuex = name.toLowerCase().compareTo(((XMLSpecialChar)o).name.toLowerCase());
        }
        return valuex;
    }
}
