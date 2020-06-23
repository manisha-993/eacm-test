// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.util.*;

/*****************************************************************************
* This interface is to display XML errors in initial load or paste.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLErrorListener.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.2  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//
//
public interface XMLErrorListener extends EventListener
{
    /***********************************************************************
    * This is called when the XML DTD parser has flagged an error.
    * @param errMsg String
    * @param pos int
    */
    void handleError(String errMsg, int pos);
}
