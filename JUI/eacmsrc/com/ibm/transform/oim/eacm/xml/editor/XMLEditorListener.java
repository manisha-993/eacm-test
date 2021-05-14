// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.util.*;

/*****************************************************************************
* This interface is to allow submission to the e-announce db thru the applet or UI
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLEditorListener.java,v $
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
public interface XMLEditorListener extends EventListener
{
    /***********************************************************************
    * This is called when the user wishes to submit completed xml to the
    * e-announce database.  Returning true will make the control uneditable, no
    * further updates will be allowed.
    *
    * For example:
    *
    *   try
    *   {
    *       // get the completed XML
    *       String = xmlEditor.getCompletedXML();
    *       // store it in e-announce
    *   }
    *   catch(IOException ioe) { do something }
    *@return boolean
    */
    boolean updateRequested();

    /***********************************************************************
    * This is called when close or x is pressed
    * It has no meaning for the applet.
    */
    void editorClosing();

    /***********************************************************************
    * This is called when the user has pressed F1 requesting help text on
    * the attribute.  Returning null will display "No Information Available."
    * @return String
    */
    String attributeHelpTextRequested();
}
