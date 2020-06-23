// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

/******************************************************************************
* This class is used for Errors in the XHTML
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLErrorObject.java,v $
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.4  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.3  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.2  2005/09/14 14:57:18  wendy
// Fix revision tag
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//

class XMLErrorObject
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private int pos;
    private String msg;

    int getPosition()   {return pos;}
    /**********************
     * is equal
     * @param obj
     * @return boolean
     */
    public boolean equals(Object obj)
    {
        boolean same = false;
        if (obj instanceof XMLErrorObject)
        {
            XMLErrorObject errobj = (XMLErrorObject)obj;
            same = (errobj.msg.equals(msg) && errobj.pos==pos);
        }
        return same;
    }
    /*******************************
     * use in hashtables
     * @return int
     */
    public int hashCode() {
        return pos;
    }

    XMLErrorObject(String smsg, int ipos)
    {
        this.msg = smsg;
        this.pos = Math.max(ipos,0);
    }
    /*************************************
     * string
     * @return String
     */
    public String toString() { return msg+getPosition();}
}

