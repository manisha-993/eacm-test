// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/******************************************************************************
* This is used to create a table
* It must be modal to work with the java ui for e-announce.
* IT MUST be reinstantiated each time or it will not be modal in an applet!!!
*
* The new requirement for V1.1.1 is the support of a simple table.
*    a default unchangeable cells and borders
*    no nesting of codes except with respect to the table
*        Bold
*        Italic
*    the user can specify the number of rows and columns
*    the user can add or remove rows and columns
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLTableWizard.java,v $
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.2  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.1.1.1  2005/09/09 20:39:21  tony
// This is the initial load of OPICM
//
//
class XMLTableWizard extends JDialog implements XMLEditorGlobals, KeyEventPostProcessor
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    private JButton[] buttons = new JButton[2];

    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";

    static final int CREATE=1;
    static final int INSERT_ROWCOL=2;
    static final int DELETE_ROWCOL=3;
    private JPanel thePanel = null;

    void dereference()
    {
        EventListener listeners[]=null;
        ((XMLDereferencible)thePanel).dereference();
        thePanel=null;
        for (int x=0; x<buttons.length; x++)
        {
            listeners = buttons[x].getListeners(ActionListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                buttons[x].removeActionListener((ActionListener)listeners[i]);
            }
        }

        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);

        XMLEditor.dereferenceContainer(this);
    }

    XMLTableWizard(XMLEditorPane editor, int type)
    {
        super(JOptionPane.getFrameForComponent(editor),"",true);
        init(editor,type);
    }
    private void init(XMLEditorPane editor, int type)
    {
        // create buttons
        JPanel buttonPanel = new JPanel(false);
        JButton button = new JButton("OK");
        buttonPanel.setLayout(new javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout(true,4));
        button.setMnemonic('O');
        button.getAccessibleContext().setAccessibleDescription("Press OK to execute request.");
        buttons[0]=button;
        button.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(button);
        button = new JButton("Cancel");
        button.setMnemonic('C');
        button.getAccessibleContext().setAccessibleDescription("Press Cancel to close dialog.");
        //button.setToolTipText("Press Cancel to close dialog");
        buttons[1]=button;
        button.setMargin(new Insets(2, 4, 2, 4));
        button.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JComponent comp = (JComponent)e.getSource();
                    // must be close or cancel
                    ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
                    ((JDialog)comp.getTopLevelAncestor()).dispose();
                }
            });
        buttonPanel.add(button);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));// top, left, bottom, and right insets

        switch(type)
        {
        case INSERT_ROWCOL:
            setTitle("Insert Row/Column");
            thePanel = new XMLTableRowColPanel(buttons[0],editor,true);
            break;
        case DELETE_ROWCOL:
            setTitle("Delete Table/Element");
            thePanel = new XMLTableRowColPanel(buttons[0],editor,false);
            break;
        default:
        case CREATE:
            setTitle("Create a Table");
            thePanel = new XMLTableCreatePanel(buttons[0],editor);
            break;
        }

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);

        getContentPane().add(thePanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(buttons[0]);

        pack();
        setSize(getPreferredSize());
        setLocationRelativeTo(editor.getParent());//editor); jre 1.4.0 positions dialog incorrectly
        setResizable(false);
    }

    /*********************************************************************
    * process key events
    * @param e KeyEvent
    * @return boolean
    */
    public boolean postProcessKeyEvent(KeyEvent e)
    {
        if (e.getID()==KeyEvent.KEY_RELEASED &&
            e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
        	setVisible(false);
            dispose();
        }
        return false;
    }
}
