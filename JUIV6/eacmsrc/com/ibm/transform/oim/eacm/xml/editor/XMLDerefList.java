// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*; //Property change stuff

/******************************************************************************
* Jlist that can be dereferenced. jtest requires a separate class
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLDerefList.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
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

class XMLDerefList extends JList
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    XMLDerefList(DefaultListModel model)
    {
        super(model);
    }
    void dereference()
    {
        EventListener listeners[];
        if (accessibleContext!=null)
        {
            removePropertyChangeListener((PropertyChangeListener)accessibleContext);
            getSelectionModel().removeListSelectionListener((ListSelectionListener)accessibleContext);
            getModel().removeListDataListener((ListDataListener)accessibleContext);
            accessibleContext = null;
        }

        listeners = getListeners(KeyListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeKeyListener((KeyListener)listeners[i]);
        }
        listeners = getListeners(MouseListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeMouseListener((MouseListener)listeners[i]);
        }

        listeners = getListeners(ListSelectionListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeListSelectionListener((ListSelectionListener)listeners[i]);
        }
        listeners = getListeners(FocusListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeFocusListener((FocusListener)listeners[i]);
        }

        setAutoscrolls(false);
        getUI().uninstallUI(this);
    }
}
