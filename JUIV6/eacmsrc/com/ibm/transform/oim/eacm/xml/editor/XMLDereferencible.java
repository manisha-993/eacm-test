// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

/*****************************************************************************
* This interface is used to notify objects when they need to dereference memory
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLDereferencible.java,v $
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
// Revision 1.1  2007/04/18 19:47:47  wendy
// Reorganized JUI module
//
// Revision 1.2  2006/01/25 18:59:03  wendy
// AHE copyright
//
// Revision 1.1.1.1  2005/09/09 20:39:15  tony
// This is the initial load of OPICM
//
public interface XMLDereferencible
{
    /***********************************************************************
    * Called when the class needs to release references.
    */
    void dereference();
}
