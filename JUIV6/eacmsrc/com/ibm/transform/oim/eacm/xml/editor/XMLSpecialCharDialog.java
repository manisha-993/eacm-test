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
* This is used to provide list of special characters (character entity references from dtd).
* It must be modal to work with the java ui for e-announce.
* IT MUST be reinstantiated each time or it will not be modal in an applet!!!
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLSpecialCharDialog.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
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
//
class XMLSpecialCharDialog extends JDialog implements KeyEventPostProcessor
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private XMLEditorPane editor=null;
    private JList list=null;
    private JButton btnOk=null, btnCancel=null;

    void dereference()
    {
        EventListener listeners[] = btnOk.getListeners(ActionListener.class);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);

        ((XMLDerefList)list).dereference();
        list = null;

        for(int i=0; i<listeners.length; i++) {
            btnOk.removeActionListener((ActionListener)listeners[i]);
        }
        btnOk = null;
        listeners = btnCancel.getListeners(ActionListener.class);
        for(int i=0; i<listeners.length; i++) {
            btnCancel.removeActionListener((ActionListener)listeners[i]);
        }
        btnCancel = null;

        editor = null;

        getRootPane().setDefaultButton(null);
        XMLEditor.dereferenceContainer(this);
        getContentPane().setLayout(null);
        getContentPane().removeAll();
    }

    XMLSpecialCharDialog(XMLEditorPane editorx, DefaultListModel model)
    {
        super(JOptionPane.getFrameForComponent(editorx),    "Special Characters",true);

        this.editor = editorx;

        list = new XMLDerefList(model);
        setupList();
        init();
    }
    private void init()
    {
        JPanel thePanel = new JPanel(false);
        JPanel panel = new JPanel(false);

        // create buttons
        JPanel buttonPanel = new JPanel(false);
        buttonPanel.setLayout(new javax.swing.plaf.basic.BasicOptionPaneUI.ButtonAreaLayout(true,4));
        btnOk = new JButton("OK");
        btnOk.setMnemonic('O');
        btnOk.getAccessibleContext().setAccessibleDescription("Press OK to insert selected character.");
        btnOk.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(btnOk);
        btnOk.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    // insert the character
                    insertCharacter();
                }
            });
        btnCancel = new JButton("Cancel");
        btnCancel.setMnemonic('C');
        btnCancel.getAccessibleContext().setAccessibleDescription("Press Cancel to close dialog.");
        btnCancel.setMargin(new Insets(2, 4, 2, 4));
        buttonPanel.add(btnCancel);
        btnCancel.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JComponent comp = (JComponent)e.getSource();
                    // must be close or cancel
                    ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
                    ((JDialog)comp.getTopLevelAncestor()).dispose();

                    editor.setFocusInEditor();  // applet does not return focus to the editor
                }
            });
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 2, 0));// top, left, bottom, and right insets

        thePanel.setLayout(new BoxLayout(thePanel, BoxLayout.Y_AXIS));
        thePanel.setBorder(BorderFactory.createEmptyBorder(6, 4, 0, 4));// top, left, bottom, and right insets
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel("Select character to insert: ", JLabel.LEFT));
        panel.add(Box.createHorizontalGlue());
        thePanel.add(panel);
        thePanel.add(Box.createRigidArea(new Dimension(0, 3)));
        thePanel.add(new JScrollPane(list));

        getContentPane().add(thePanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        //setSize(new Dimension((int)getPreferredSize().getWidth()+80,(int)getPreferredSize().getHeight()));
        setSize(getPreferredSize());
        setLocationRelativeTo(editor.getParent());//editor); jre 1.4.0 positions dialog incorrectly

        getRootPane().setDefaultButton(btnOk);

        getAccessibleContext().setAccessibleDescription("XML Special characters dialog.");
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);

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

    private void insertCharacter()
    {
        XMLSpecialChar selItem = (XMLSpecialChar) list.getSelectedValue();
        editor.replaceSelection(selItem.getValue());
        setVisible(false);
        dispose();
    }
    /*********************************************************************
    * Setup list control.
    */
    private void setupList()
    {
        // by default JLists displays 8 items
        // use setVisibleRowCount(xx) to change it
        // restrict it to one selection
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Allow double clicks to execute caret placement
        // this is needed if the user clicks on a previously selected item
        list.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    if (evt.getClickCount()==2)
                    {
                        insertCharacter();
                    }
                }
            });

        list.addKeyListener(new KeyAdapter()   {
                // used for accessibility and reaction to enter key press
                public void keyPressed(KeyEvent e)
                {
                    boolean done = false;
                    JList jlist = (JList)e.getSource();
                    if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    {
                        XMLSpecialChar selItem = (XMLSpecialChar) jlist.getSelectedValue();
                        if (selItem==null)
                        {
                            int selid = jlist.getLeadSelectionIndex();
                            if (selid==-1) {
                                selid = 0; }
                            jlist.setSelectedIndex(selid);
                            //return;
                            done = true;
                        }else{
                            insertCharacter();
                            e.consume();
                        }
                    }
                    if (!done){
                        // scroll to start of section that corresponds to the typed letter
                        if (e.getKeyCode() >= KeyEvent.VK_A &&
                            e.getKeyCode() <= KeyEvent.VK_Z)
                        {
                            for(int i = 0; i < jlist.getModel().getSize(); i++)
                            {
                                XMLSpecialChar selItem = (XMLSpecialChar)jlist.getModel().getElementAt(i);

    //                            if (selItem.getName().toUpperCase().charAt(0)== (char)e.getKeyCode())
                                if (Character.getNumericValue(selItem.getName().toUpperCase().charAt(0))== e.getKeyCode())
                                {
                                    jlist.setSelectedIndex(i);
                                    jlist.ensureIndexIsVisible(i);
                                    break;//return;
                                }
                            }
                        }
                    }
                }
        });

        list.setSelectedIndex(0);
        list.getAccessibleContext().setAccessibleName("special characters");
        list.getAccessibleContext().setAccessibleDescription("list of HTML special characters");

        // control how text is laid out
        list.setCellRenderer(new SCCellRenderer());
    }

    private class SCCellRenderer extends DefaultListCellRenderer
    {
    	private static final long serialVersionUID = 1L;
    	private static final int VALUE_LEN=27;
        private static final int NAME_LEN=78;

        /*****************
        * This is the only method defined by ListCellRenderer.  We just
        * reconfigure the Jlabel each time we're called.
        * @param jlist List
        * @param value Object
        * @param index int
        * @param iss boolean
        * @param chf boolean
        * @return Component
        */
        public Component getListCellRendererComponent(
            JList jlist,
            Object value,   // value to display
            int index,      // cell index
            boolean iss,    // is the cell selected
            boolean chf)    // the list and the cell have the focus
        {
            FontMetrics metrics;
            /* The DefaultListCellRenderer class will take care of
            * the JLabels text property, it's foreground and background
            * colors, and so on.
            */
            super.getListCellRendererComponent(jlist, value, index, iss, chf);

            // Build formatted string
            metrics = jlist.getFontMetrics(jlist.getFont());
            if (value instanceof XMLSpecialChar)
            {
                XMLSpecialChar xsc = (XMLSpecialChar)value;
                StringBuffer sb = new StringBuffer(xsc.getValue());
                while(metrics.stringWidth(sb.toString())<VALUE_LEN) {
                    sb.append(" ");
                }
                sb.append(xsc.getName());
                while(metrics.stringWidth(sb.toString())<NAME_LEN){
                    sb.append(" ");
                }
                sb.append(" -- "+xsc.getDef()+" --");

                setText(sb.toString());
            }

            return this;
        }
    }
}
