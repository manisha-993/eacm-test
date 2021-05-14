// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.accessibility.*;
/******************************************************************************
* This is used to display webonly, printonly content and tags in the wysiwyg editor.
* It must be modal to work with the java ui for e-announce.
* IT MUST be reinstantiated each time or it will not be modal in an applet!!!
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLDisplayDialog.java,v $
// Revision 1.2  2008/01/30 16:27:09  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:47  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/05/10 14:43:00  wendy
// Change e-announce to EACM
//
// Revision 1.2  2006/01/25 18:59:03  wendy
// AHE copyright
//
// Revision 1.1.1.1  2005/09/09 20:39:15  tony
// This is the initial load of OPICM
//
//
class XMLDisplayDialog extends JDialog implements XMLEditorGlobals, KeyEventPostProcessor
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    private JButton btnOk;
    private XMLEditor editor;

    void dereference()
    {
        EventListener listeners[]= btnOk.getListeners(ActionListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            btnOk.removeActionListener((ActionListener)listeners[i]);
        }

        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);

        XMLEditor.dereferenceContainer(this);
        editor = null;
    }

    XMLDisplayDialog(XMLEditor editorx, String completeXML, int viewType,
            Dimension editSize, String dtdName)//, Action prtAction)
    {
        super(JOptionPane.getFrameForComponent(editorx),"",true);
        editor = editorx;
        btnOk = new JButton("OK");

        init(completeXML, viewType, editSize, dtdName);
    }
    private void init(String completeXML, int viewType, Dimension editSize, String dtdName)
    {
        String curXML = null;
        AccessibleViewPane view =null;
        JPanel buttonPanel = null;
        JPanel thePanel = null;

        switch(viewType)
        {
        case WEBONLY_VIEW_TYPE:
            setTitle(XMLEditorGlobals.APP_NAME+" XML Editor "+WEB_ONLY_CONTENT+" View");
            getAccessibleContext().setAccessibleDescription("View Web Only content.");
            break;
        case PRINTONLY_VIEW_TYPE:
            setTitle(XMLEditorGlobals.APP_NAME+" XML Editor "+PRINT_ONLY_CONTENT+" View");
            getAccessibleContext().setAccessibleDescription("View Print Only content.");
            break;
        case TAG_VIEW_TYPE:
            setTitle(XMLEditorGlobals.APP_NAME+" XML Editor "+TAG_CONTENT);
            getAccessibleContext().setAccessibleDescription("View XML source.");
            break;
        default:
            break;
        }

        // <br /> will be converted to <br>
        completeXML=XMLGenerator.replaceEmptyXML(completeXML);

        // convert xml before loading, remove appropriate publish attributes
        curXML = XMLGenerator.generateViewXML(completeXML,viewType);

        //JEditorPane view = new JEditorPane();
        view  = new AccessibleViewPane();
        //view.setEditable(false);
        // if switching to tagview, the contenttype and editorkit must be plain text
        if (viewType==TAG_VIEW_TYPE)
        {
            view.setContentType("text/plain");
            view.setEditorKit(new StyledEditorKit());
        }
        else
        {
            view.setContentType("text/html");
            // xmleditor kit needed to get xmldocument and render nested lists
            // properly (li - ul needs a p-implied between them)
            view.setEditorKit(new XMLEditorKit());
            ((XMLEditorKit)view.getEditorKit()).setDTDName(dtdName);
            // force a document with the specified dtd parser
            view.setDocument(view.getEditorKit().createDefaultDocument());
            curXML = "<body>"+curXML+"</body>";
        }

        view.setText(curXML);

        curXML = null;
    //  view.setEnabled(false);  // allow ok button to get focus
//      if (viewType==TAG_VIEW_TYPE)
        view.setCaretPosition(0);
//      else
//          view.setCaretPosition(1);  // avoid hidden <head> not a problem if using xmleditorkit

        // create buttons
        buttonPanel = new JPanel(false);
        buttonPanel.setLayout(new javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout(true,4));
        btnOk.setMnemonic('O');
        btnOk.getAccessibleContext().setAccessibleDescription("Press OK to close dialog.");
        btnOk.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(btnOk);
        btnOk.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JComponent comp = (JComponent)e.getSource();
                    // must be close or cancel
                    ((JDialog)comp.getTopLevelAncestor()).hide();
                    ((JDialog)comp.getTopLevelAncestor()).dispose();

                    editor.setFocusInEditor();  // applet does not return focus to the editor
                }
            });

/*
if (prtAction !=null)
{
        JButton button = new JButton((String)prtAction.getValue(Action.NAME),
                                    (Icon)prtAction.getValue(Action.SMALL_ICON));
        button.setHorizontalTextPosition(JButton.CENTER);
        button.setVerticalTextPosition(JButton.BOTTOM);
        button.setEnabled(prtAction.isEnabled());
        button.setAction(prtAction);
        buttonPanel.add(button);
}
*/
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 2, 0));// top, left, bottom, and right insets

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);

        thePanel = new JPanel(false);
        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.X_AXIS));
        thePanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 0, 2));// top, left, bottom, and right insets
        thePanel.add(new JScrollPane(view));

        getContentPane().add(thePanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(btnOk);

        pack();
        setSize(editSize);
        setLocationRelativeTo(editor);
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
            hide();
            dispose();

            editor.setFocusInEditor();  // applet does not return focus to the editor
        }
        return false;
    }

    /*********************************************************************
    * This is used with JOptionPane and text messages.  This will allow
    * Accessibility to read the information.  It will look like a label.  The
    * user can tab from line to line and hear the text.  They can then tab to
    * the buttons.
    */
    private static class AccessibleViewPane extends JEditorPane implements FocusListener
    {
    	private static final long serialVersionUID = 1L;
    	AccessibleViewPane()
        {
            super();
            //setEditable(false); term new line not rendered properly if done here
            addFocusListener(this);
            //getAccessibleContext().setAccessibleDescription("message line");
            getAccessibleContext().setAccessibleName("view contents");

            addKeyListener(new KeyAdapter()
                {
                    // used to allow tab to button
                    public void keyPressed(KeyEvent e)
                    {
                        // don't allow tabs to get through.. they have no meaning to html
                        if (e.getKeyCode()==KeyEvent.VK_TAB)
                        {
                            JRootPane root = ((JDialog)getTopLevelAncestor()).getRootPane();
                            JButton defBut = root.getDefaultButton();
                            defBut.requestFocus();
                            e.consume();
                        }
                        else
                        if (e.getKeyCode() == KeyEvent.VK_ENTER)
                        {
                            //allow button to get focus and close (act like the default button it is)
                            JRootPane root = ((JDialog)getTopLevelAncestor()).getRootPane();
                            JButton defBut = root.getDefaultButton();
                            defBut.requestFocus();
                            defBut.doClick();
                            e.consume();
                        }
                    }
                });
        }

        public void focusGained(FocusEvent e) // Invoked when a component gains the keyboard focus
        {
            getCaret().setVisible(true);
            setEditable(false); // do it here because if done earlier, the terminating new line
            // is not rendered properly, it is drawn in the middle of the editorPane
        }

        public void focusLost(FocusEvent e) //Invoked when a component loses the keyboard focus.
        {
            getCaret().setVisible(false);
        }
        public AccessibleContext getAccessibleContext() {
            if (accessibleContext==null) {
                accessibleContext = new AccessibleView();
            }
            return accessibleContext;
        }

        private class AccessibleView extends AccessibleJEditorPane {
        	private static final long serialVersionUID = 1L;
        	public AccessibleText getAccessibleText() {
                return new AccessibleTextViewSupport();
            }
        }
        private class AccessibleTextViewSupport extends JEditorPaneAccessibleHypertextSupport
        {
        	private static final long serialVersionUID = 1L;
        	//Given a point in local coordinates, return the zero-based index of the character under that Point.
            public int getIndexAtPoint(Point p)
            {
                // this sees to come in with the wrong value, y is off, if not changed, the index
                // is calculated to the same for short lines and the svk tool kit will not get the text!!!
                // jre with fix for editor bug has this problem.. not the svk tool
                // 1.3.1 fix only!!
                Point p2 = new Point(p.x,p.y-1);
                int f = super.getIndexAtPoint(p2);
                return f;
            }
        }
    }
}
