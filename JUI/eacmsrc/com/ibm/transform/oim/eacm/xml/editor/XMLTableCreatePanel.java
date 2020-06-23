// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.text.html.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/******************************************************************************
* This is used to create a table
* It must be modal to work with the java ui for e-announce.
* IT MUST be reinstantiated each time or it will not be modal in an applet!!!
*
*The new requirement for V1.1.1 is the support of a simple table.
*    a default unchangeable cells and borders.. what does this mean?
*(i want to create tbl with border so it can be seen.. then allow user to turn it off..)
*    no nesting of codes except with respect to the table
*        Bold
*        Italic
*    the user can specify the number of rows and columns
*    the user can add or remove rows and columns
*
* th
*This stands for table header. Header cells are identical to data cells in all respects,
*with the exception that header cells are in a bold font, and have a default ALIGN=center.
*
*
*undo does not restore cell width!!. add some text to a cell.. it grows, undo and text goes away but cell stays large
*not saved when re-rendered.. is just a space
*how to handle selection?  wordpro asks user and creates a table with one row per line of text
*word creates a table with one row per line of text
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLTableCreatePanel.java,v $
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.4  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.3  2006/01/10 15:00:45  wendy
// Changes for table accessibility DQA requirements
//
// Revision 1.2  2005/12/16 15:52:14  wendy
// Changes for DQA for td, th tags
//
// Revision 1.1.1.1  2005/09/09 20:39:20  tony
// This is the initial load of OPICM
//
//
class XMLTableCreatePanel extends JPanel implements XMLEditorGlobals, XMLDereferencible
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    private XMLEditorPane editor=null;
    private JSpinner rowSpinner=null, colSpinner=null;
    private JButton okButton=null;
    private CreateAction createAction = new CreateAction();

    /*********************************************
    * release memory
    */
    public void dereference()
    {
        editor = null;
        createAction = null;
        if (rowSpinner!=null)
        {
            EventListener listeners[] = ((JSpinner.DefaultEditor)rowSpinner.getEditor()).getTextField().getListeners(KeyListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                ((JSpinner.DefaultEditor)rowSpinner.getEditor()).getTextField().removeKeyListener((KeyListener)listeners[i]);
            }
        }

        rowSpinner = null;
        if (colSpinner!=null)
        {
            EventListener listeners[] = ((JSpinner.DefaultEditor)colSpinner.getEditor()).getTextField().getListeners(KeyListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                ((JSpinner.DefaultEditor)colSpinner.getEditor()).getTextField().removeKeyListener((KeyListener)listeners[i]);
            }
        }
        colSpinner = null;
        okButton=null;
    }

    XMLTableCreatePanel(JButton button, XMLEditorPane editora)
    {
        super(false);
        this.editor = editora;
        okButton = button;
        init();
    }
    private void init()
    {
        JLabel rowLabel = null;
        JPanel rowPanel = new JPanel(false);
        JPanel colPanel = new JPanel(false);
        JLabel colLabel = new JLabel("Number of Columns: ", JLabel.LEFT);

        // build the table gui
        getAccessibleContext().setAccessibleDescription("Build an HTML table.");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));

//SpinnerNumberModel(int value, int minimum, int maximum, int stepSize)
        rowSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        ((JSpinner.DefaultEditor)rowSpinner.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor)rowSpinner.getEditor()).getTextField().setBackground(Color.WHITE);
        ((JSpinner.DefaultEditor)rowSpinner.getEditor()).getTextField().
            getAccessibleContext().setAccessibleDescription("Enter number of rows.");
        rowSpinner.setMaximumSize(rowSpinner.getPreferredSize());
        ((JSpinner.DefaultEditor)rowSpinner.getEditor()).getTextField().
            setToolTipText("Maximum value is "+((SpinnerNumberModel)rowSpinner.getModel()).getMaximum());
        ((JSpinner.DefaultEditor)rowSpinner.getEditor()).getTextField().addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        okButton.doClick();
                    }
                }
            });


        rowLabel = new JLabel("Number of Rows: ", JLabel.LEFT);
        rowLabel.setLabelFor(rowSpinner);
        rowLabel.setDisplayedMnemonic('R');
        rowPanel.add(rowLabel);

        rowPanel.add(Box.createHorizontalGlue());
        rowPanel.add(rowSpinner);

        colPanel.setLayout(new BoxLayout(colPanel, BoxLayout.X_AXIS));

//SpinnerNumberModel(int value, int minimum, int maximum, int stepSize)
        colSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        ((JSpinner.DefaultEditor)colSpinner.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor)colSpinner.getEditor()).getTextField().setBackground(Color.WHITE);
        ((JSpinner.DefaultEditor)colSpinner.getEditor()).getTextField().
            getAccessibleContext().setAccessibleDescription("Enter number of columns.");
        colSpinner.setMaximumSize(colSpinner.getPreferredSize());
        ((JSpinner.DefaultEditor)colSpinner.getEditor()).getTextField().
            setToolTipText("Maximum value is "+((SpinnerNumberModel)colSpinner.getModel()).getMaximum());
        ((JSpinner.DefaultEditor)colSpinner.getEditor()).getTextField().addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)
                {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        okButton.doClick();
                    }
                }
            });

        colLabel.setLabelFor(colSpinner);
        colLabel.setDisplayedMnemonic('L');
        colPanel.add(colLabel);
        colPanel.add(Box.createHorizontalGlue());
        colPanel.add(colSpinner);

        add(rowPanel);
        add(colPanel);

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10)); // top, left, bottom, and right insets

        okButton.addActionListener(createAction);
    }

    // this constructor is used when a table needs to be added and text is selected
    XMLTableCreatePanel(XMLEditorPane editora)
    {
        super(false);
        this.editor = editora;
        createAction = new CreateAction();
    }

    private Element getStartElement(XMLDocument doc)
    {
        int offset= editor.getSelectionStart();
        javax.swing.text.Element paragraph = doc.getParagraphElement(offset);
        javax.swing.text.Element parent = paragraph;
        if(parent.getName().equals(HTML.Tag.IMPLIED.toString())||
            parent.getName().equals(HTML.Tag.P.toString())) // look for nested p
        {
            parent = parent.getParentElement();  // get <li>?
        }

        if (parent.getName().equals(HTML.Tag.BODY.toString())) {// too far up hierarchy
            parent = paragraph;  // restore it
        }

//System.err.print("createtbl: getstartoffset() selStart: "+editor.getSelectionStart()+" offset: "+offset+" parent: "+parent);
        return parent;
    }

    private Element getEndElement(XMLDocument doc)
    {
        int endPos = Math.min(editor.getSelectionEnd(),doc.getLength());
        javax.swing.text.Element paragraph = doc.getParagraphElement(endPos);
        javax.swing.text.Element parent = paragraph;
        if(parent.getName().equals(HTML.Tag.IMPLIED.toString())||
            parent.getName().equals(HTML.Tag.P.toString())) // look for nested p
        {
            parent = parent.getParentElement();  // get <li>?
        }

        if (parent.getName().equals(HTML.Tag.BODY.toString())) {// too far up hierarchy
            parent = paragraph;  // restore it
        }

        // at this point it is possible that the element's end offset exceeds the document length
//System.err.print("createtbl: getendoffset() endpos: "+endPos+" parent: "+parent);
        return parent;
    }

    void createTableUsingSelection()
    {
        XMLDocument doc = (XMLDocument)editor.getDocument();
        Element startElement = getStartElement(doc);
//System.err.print("createTableUsingSelection:: startelem "+startElement);
        // if new table is pressed in any element, the entire element will be converted to
        // a table cell
        int offset = startElement.getStartOffset();
        int caretPos=offset;
        Element endElement = getEndElement(doc);
        // if new tbl is pressed in any element, the entire element will be converted to
        // a table cell
        int endPos = Math.min(doc.getLength(),endElement.getEndOffset());//getEndOffset(doc));//    editor.getSelectionEnd());
//System.err.println("createtable entry offset "+offset+" endPos: "+endPos+" doclen: "+doc.getLength());
        try{
            // get formatted text without structure
            // the array will have one element for each
            // p-implied or p structure's text
            XMLWriter w = new XMLWriter(doc, offset,endPos-offset);//pos, len);
            StringBuffer[] array = w.writeFormattedText();

            editor.getUndoEditMgr().setStartReplaceEdit("table");
            // remove selected text
            // if user selects list items at end of a list but not entire list, the content of structure
            // is incorrectly pulled up into list
            if (endElement.getName().equals(HTML.Tag.LI.toString())&&
                endElement.getParentElement().getEndOffset()==endPos)
            {
                // selection is at the end of a list
                if (startElement.getParentElement()==endElement.getParentElement() &&
                    startElement.getParentElement().getStartOffset()<offset) // not at the start of a list so adjust remove offset
                {
                    offset--;
                    endPos--;
//System.err.println("******** adjust offset ");
                }
            }else
                // if user selects list items at end of a list and some other structure
                // the list item is not completely deleted
                if ((startElement.getParentElement().getName().equals(HTML.Tag.OL.toString()) ||
                        startElement.getParentElement().getName().equals(HTML.Tag.UL.toString())) &&
                    startElement.getParentElement().getStartOffset()!= offset && // entire elem will not be deleted
                    startElement.getParentElement()!=endElement.getParentElement())
                {
                    offset--;
//System.err.println("******** adjust offset2 ");
                }

//System.err.println("createtable calling remove offset "+offset+" len: "+(endPos-offset));
            doc.remove(offset, endPos-offset);

            // move to start of deleted area
            editor.setCaretPosition(caretPos);

            // create the table
            createAction.createTable(array.length,1,array);
        }
        catch(Exception ble)
        {
            System.err.println("createTableUsingSelection: exc "+ble);
            ble.printStackTrace(System.err);
        }

        editor.getUndoEditMgr().setEndReplaceEdit();
    }

    /*********************************************************************
    * This is create table action
    */
    private class CreateAction extends AbstractAction
    {
    	private static final long serialVersionUID = 1L;
    	CreateAction() {
            super("OK");
        }

        // get the element to use for replacement
        private javax.swing.text.Element getParagraphElement(XMLDocument doc, int offset)
        {
            javax.swing.text.Element paragraph = doc.getParagraphElement(offset);
            javax.swing.text.Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.BODY.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            return paragraph;
        }

        // if content is not equal to null, a table will be created with 1 col and a row for
        // each value in content
        void createTable(int numRows, int numCols, StringBuffer[] contentArray)
        {
            XMLDocument doc = (XMLDocument)editor.getDocument();
            int offset = editor.getSelectionStart();
            javax.swing.text.Element paragraph = getParagraphElement(doc, offset);

            // build html string
            StringBuffer sb = new StringBuffer("<table border=\"1\" summary=\""+TABLE_LAYOUT+"\">");
            for (int r = 0; r<numRows;r++)
            {
                sb.append("<tr>");
                for (int c=0; c<numCols; c++)
                {
                    if (contentArray==null) {
                        sb.append("<"+"td"+"></td>");
                    }
                    else {
                        sb.append("<"+"td"+">"+contentArray[r]+"</td>");
                    }
                }
                sb.append("</tr>");
            }
            sb.append("</table>");

            try{
                boolean insertBefore = (paragraph.getStartOffset()==offset);

                // Insert the HTML specified as string before the start of the given element.
                if (insertBefore)
                {
                    offset = paragraph.getStartOffset(); // get location to move cursor to after insert
                    doc.insertBeforeStart(paragraph,sb.toString());
                }
                else
                {
                    offset = paragraph.getEndOffset(); // get location to move cursor to after insert
                    // Insert the HTML specified as a string after the end of the given element.
                    doc.insertAfterEnd(paragraph,sb.toString());
                }

            }catch(Exception ble) {
                System.err.println("Table create exc "+ble);
                ble.printStackTrace(System.err);
            }

            editor.setCaretPosition(offset);  // move cursor to insert location
        }

        public void actionPerformed(ActionEvent e)
        {
            int numRows = 0;
            int numCols = 0;
            JComponent comp = null;
            XMLDocument doc = (XMLDocument)editor.getDocument();
            int offset = editor.getSelectionStart();
            javax.swing.text.Element paragraph = doc.getParagraphElement(offset);
            javax.swing.text.Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.BODY.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            numRows = ((SpinnerNumberModel)rowSpinner.getModel()).getNumber().intValue();
            numCols = ((SpinnerNumberModel)colSpinner.getModel()).getNumber().intValue();

            editor.getUndoEditMgr().setStartReplaceEdit("table");
            createTable(numRows, numCols, null);
            editor.getUndoEditMgr().setEndReplaceEdit();

            comp = (JComponent)e.getSource();
            // must be close or cancel
            ((JDialog)comp.getTopLevelAncestor()).hide();
            ((JDialog)comp.getTopLevelAncestor()).dispose();
        }
    }
}
