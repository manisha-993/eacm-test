// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.beans.*; //Property change stuff
import javax.swing.text.*;

/******************************************************************************
* This is used to display html parsing error messages.  The
* list control is also shared in the editorPane to record messages.
* It must be modal to work with the java ui for e-announce.
* IT MUST be reinstantiated each time or it will not be modal in an applet!!!
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLErrorDialog.java,v $
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:09  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.4  2006/05/10 14:43:01  wendy
// Change e-announce to EACM
//
// Revision 1.3  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//
//
class XMLErrorDialog extends JDialog implements KeyEventPostProcessor
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private JOptionPane optionPane=null;
    private XMLEditor editor=null;
    private AccessibleTextArea ta=null;
    private JList list=null;
    private JButton btnOk=null;
    private JScrollPane listScrollPane=null;
    private PropertyChangeListener pcl=null;

    private Highlighter highlighter = new DefaultHighlighter();
    private Highlighter.HighlightPainter painter = new XMLErrorHighlightPainter(Color.red);

    void dereference()
    {
        Highlighter.Highlight[] highlights;
        EventListener listeners[];
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);

        // Remove any existing highlights for last error
        highlights = highlighter.getHighlights();
        for (int i = 0; i < highlights.length; i++)
        {
            Highlighter.Highlight h = highlights[i];
            if (h.getPainter() instanceof XMLErrorHighlightPainter)
            {
                highlighter.removeHighlight(h);
            }
            highlights[i]=null;
        }

        highlighter = null;

        ta.dereference();
        ta = null;

        ((XMLDerefList)list).dereference();
        list = null;

        listScrollPane.getUI().uninstallUI(listScrollPane);
        listScrollPane = null;

        listeners = btnOk.getListeners(ActionListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            btnOk.removeActionListener((ActionListener)listeners[i]);
        }
        btnOk = null;

        XMLEditor.dereferenceContainer(this);
        editor = null;
        painter = null;

        optionPane.removePropertyChangeListener(pcl);
        optionPane.getUI().uninstallUI(optionPane);
        optionPane.setLayout(null);

        listeners = optionPane.getListeners(FocusListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            optionPane.removeFocusListener((FocusListener)listeners[i]);
        }
        optionPane = null;
        pcl=null;

        listeners = getListeners(WindowListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeWindowListener((WindowListener)listeners[i]);
            listeners[i]=null;
        }
        getRootPane().setDefaultButton(null);

        getContentPane().setLayout(null);
        getContentPane().removeAll();
    }

    XMLErrorDialog(final XMLEditor xeditor, DefaultListModel model,
            String msg2, String questionableXML)
    {
        super(JOptionPane.getFrameForComponent(xeditor),
            XMLEditorGlobals.APP_NAME+" XML Editor XML Parser Error",true);

        this.editor = xeditor;
        init(model,msg2, questionableXML);
    }
    private void init(DefaultListModel model, String msg2, String questionableXML)
    {
        AccessibleDialogMsg adm1;
        Object[] array;
        Object[] options;

        list = new XMLDerefList(model);
        setupList();
        list.getAccessibleContext().setAccessibleName("errors");
        list.getAccessibleContext().setAccessibleDescription("list of parser errors");

//      String msg = "Errors were found in the XML.\n"+msg2+"\n";
        listScrollPane = new JScrollPane(list);

        // textarea
        ta = new AccessibleTextArea(questionableXML);
        ta.setHighlighter(highlighter);
        ta.setCaretPosition(0);

//        Object[] array = { msg, new JScrollPane(list), new JScrollPane(ta)};
        adm1 = new AccessibleDialogMsg("Errors were found in the XML.  "+msg2);
//        Object[] array = {  adm1, " ", new JScrollPane(list), new JScrollPane(ta)};
        array = new Object[]{  adm1, " ", listScrollPane, new JScrollPane(ta)};

        btnOk = new JButton("OK");

        btnOk.setMnemonic(btnOk.getText().charAt(0));
        btnOk.getAccessibleContext().setAccessibleDescription("Press OK to close dialog.");

        options = new Object[]{btnOk};

        optionPane = new JOptionPane(array, JOptionPane.ERROR_MESSAGE, JOptionPane.YES_OPTION,
            null, options, options[0]);
        getContentPane().add(optionPane);
        array = null;
        options=null;
//        msg = null;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent we) {
                    /*
                     * Instead of directly closing the window,
                     * change the JOptionPane's value property.
                     */
                    we=null; // jtest req
                    optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
                }
            });

        btnOk.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e) {
                    optionPane.setValue(new Integer(JOptionPane.CLOSED_OPTION));
                }
            });

        pcl = new PropertyChangeListener()
        {
            public void propertyChange(PropertyChangeEvent e)
            {
                String prop = e.getPropertyName();
                if (isVisible() &&
                    (e.getSource() == optionPane) &&
                    (prop.equals(JOptionPane.VALUE_PROPERTY) ||
                        prop.equals(JOptionPane.INPUT_VALUE_PROPERTY)))
                {
                    Object value = optionPane.getValue();
                    if (value == JOptionPane.UNINITIALIZED_VALUE) {
                        //ignore reset
                        //return;
                    }
                    else {
                        // Reset the JOptionPane's value.
                        // If you don't do this, then if the user
                        // presses the same button next time, no
                        // property change event will be fired.
                        optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);

    //                    if (value.equals(btnCancelStr) || value.equals(new Integer(JOptionPane.CLOSED_OPTION)))
                        if (value.equals(new Integer(JOptionPane.CLOSED_OPTION)))
                        {
                            XMLErrorDialog.this.setVisible(false);
                            XMLErrorDialog.this.dispose();
                            // user closed dialog or clicked cancel
                            editor.setFocusInEditor();
                        }
                    }
                }
            }
        };
        optionPane.addPropertyChangeListener(pcl);

        pack();
        setSize(getPreferredSize());
        setLocationRelativeTo(editor);

        // tabbing to the text area does not work properly with Accessibility, can't
        // get it to read the text at the caret location, prevent tabbing into text area
        getRootPane().setDefaultButton(btnOk);
        list.setSelectedIndex(0); // jre 1.4.0
        list.requestFocus();// jre 1.4.0

        getAccessibleContext().setAccessibleDescription("XML Parser Error dialog.");

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);// jre 1.4.0
    }


// jre 1.4.0
    /*********************************************************************
    * process key events
    * @param e KeyEvent
    * @return boolean
    */
    public boolean postProcessKeyEvent(KeyEvent e)
    {
//System.err.println("getFocusOwner() "+KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner());
        if (e.getID()==KeyEvent.KEY_PRESSED)
        {
            if (e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                //allow button to get focus and close (act like the default button it is)
                btnOk.requestFocus();
                btnOk.doClick();
                e.consume();
            }
        }
        return false;
    }

    /*********************************************************************
    * Setup list control used with error dialog.
    */
    private void setupList()
    {
        // by default JLists displays 8 items
        // use setVisibleRowCount(xx) to change it
        // restrict it to one selection
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // don't use this with moveToError().. this transfers focus immediately and the list item
        // is not accessible.  the only way to move to the textarea is with a mouse
        // click or after pressing enter.. tab will move to the textarea but will not
        // pick up text at the caret location, only the first line seems to be accessed
        list.addListSelectionListener(new ListSelectionListener()
            {
                public void valueChanged(ListSelectionEvent evt)
                {
                    //if already selected this won't be invoked

                    // this is entered for mouse down with a deselection event and a selection event
                    // mouse up sends an event with isAdjusting=false
                    // there are problems with swing if multiple selections are allowed, but we
                    // are forcing single selection
                    if (!evt.getValueIsAdjusting())
                    {
                        JList src = (JList)evt.getSource();
                        XMLErrorObject selItem =(XMLErrorObject) src.getSelectedValue();
                        if (selItem!=null) {
                            markError(selItem.getPosition());
                        }
                    }
                }
            });

        // Allow double clicks to execute caret placement
        // this is needed if the user clicks on a previously selected item
        list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    //if (evt.getClickCount()==2)
                    {
                        JList src = (JList)evt.getSource();
                        XMLErrorObject selItem = (XMLErrorObject) src.getSelectedValue();
                        moveToError(selItem.getPosition());
                    }
                }
            });
    }

    // locate error in the xml in the textarea
    private void moveToError(int position)
    {
        //ta.requestFocus();
        //ta.getCaret().setVisible(true);
        // position is bad sometimes at end of doc
        position = Math.min(position,ta.getDocument().getLength());
        ta.setCaretPosition(position);

        markError(position);
    }

    private void markError(int position)
    {
        Highlighter.Highlight[] highlights;
        Highlighter highlighter2 = ta.getHighlighter();
        // position is bad sometimes at end of doc
        position = Math.min(position,ta.getDocument().getLength());

        // Remove any existing highlights for last error
        highlights = highlighter2.getHighlights();
        for (int i = 0; i < highlights.length; i++)
        {
            Highlighter.Highlight h = highlights[i];
            if (h.getPainter() instanceof XMLErrorHighlightPainter)
            {
                highlighter2.removeHighlight(h);
            }
        }

        try {
            //highlight this error
            highlighter2.addHighlight(position, position+1, painter);
        }
        catch(javax.swing.text.BadLocationException ble) {
            System.out.println(ble.getMessage());  // jtest req
        }
    }

    private static class AccessibleTextArea extends JTextArea
    {
    	private static final long serialVersionUID = 1L;
    	AccessibleTextArea(String txt)
        {
            super(txt,10,0);
            setEditable(false);
            setLineWrap(true);
            setWrapStyleWord(true);
            setFocusable(false); //jre 1.4.0

//          getAccessibleContext().setAccessibleDescription("xml content");
// bug someplace.. if i set these values, the svk reader does not read the textarea
// properly, it always gets the first line and then the line with the caret..why?
//          getAccessibleContext().setAccessibleName("xml");
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

        void dereference()
        {
            EventListener listeners[] = getListeners(KeyListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                removeKeyListener((KeyListener)listeners[i]);
            }
            listeners = getListeners(FocusListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                removeFocusListener((FocusListener)listeners[i]);
                listeners[i]=null;
            }

            setHighlighter(null);
            accessibleContext = null;
            getUI().uninstallUI(this);
        }
    }
    /*********************************************************************
    * This is used with JOptionPane and text messages.  This will allow
    * Accessibility to read the information.  It will look like a label.  The
    * user can tab from line to line and hear the text.  They can then tab to
    * the buttons.
    */
    private static class AccessibleDialogMsg extends JTextField
    {
    	private static final long serialVersionUID = 1L;
    	AccessibleDialogMsg(String msg)
        {
            super(msg);
            setBackground(UIManager.getColor("Label.background"));
            setEditable(false);
            setBorder(null);
            getAccessibleContext().setAccessibleDescription("message line");
            getAccessibleContext().setAccessibleName("message");
        }
        void dereference()
        {
            getAccessibleContext().setAccessibleDescription(null);
            getAccessibleContext().setAccessibleName(null);
            accessibleContext = null;
        }
    }
}
