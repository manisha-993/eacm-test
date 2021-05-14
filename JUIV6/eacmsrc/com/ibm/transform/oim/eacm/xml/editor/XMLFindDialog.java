// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import java.awt.*;

/******************************************************************************
* This is used to display the find/replace dialog.  The
* find panel is re-used to maintain persistence of find/replace settings.
* It must be modal to work with the java ui for e-announce.
* IT MUST be reinstantiated each time or it will not be modal in an applet!!!
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLFindDialog.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/05/10 14:43:01  wendy
// Change e-announce to EACM
//
// Revision 1.2  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//
//
class XMLFindDialog extends JDialog
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    private KeyEventPostProcessor proc;
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    XMLFindDialog(XMLEditorPane editor, XMLFindReplaceMgr findPanel, boolean onlyFind)
    {
        super(JOptionPane.getFrameForComponent(editor),XMLEditorGlobals.APP_NAME+" XML Editor Find/Replace",true);

        // the panel will be built once and reused
        findPanel.enableReplace(!onlyFind);

        getContentPane().add(findPanel);
        proc=findPanel;

        pack();
        setLocationRelativeTo(editor.getParent());//editor); jre 1.4.0 positions dialog incorrectly
        setSize(getPreferredSize());
        setResizable(false);
    }
    void dereference()
    {
        proc=null;
    }
    /***************************
     * display window
     */
    public void setVisible(boolean display)
    {
    	if (display){
    		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(proc);
    	}else{
    		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(proc);
    	}
        super.setVisible(display);
    }
}
