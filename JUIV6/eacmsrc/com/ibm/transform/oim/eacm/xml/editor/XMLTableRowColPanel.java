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
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/******************************************************************************
* This is used to create a table
* It must be modal to work with the java ui for e-announce.
* IT MUST be reinstantiated each time or it will not be modal in an applet!!!
*
*The new requirement for V1.1.1 is the support of a simple table.
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
// $Log: XMLTableRowColPanel.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:09  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.2  2005/12/16 15:52:14  wendy
// Changes for DQA for td, th tags
//
// Revision 1.1.1.1  2005/09/09 20:39:20  tony
// This is the initial load of OPICM
//
//
class XMLTableRowColPanel extends JPanel implements XMLEditorGlobals, XMLDereferencible,
    ActionListener//, KeyEventPostProcessor
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private XMLEditorPane editor=null;
    // insert elements
    private JSpinner numSpinner=null;
    private JRadioButton rowRB=null, colRB=null, beforeRB=null, afterRB=null, allRB=null;
    private JButton okButton=null;
    private InsertXMLTableRowAction rowAction=null;
    private int maxRows=0, maxCols=0; // FB50417

    /*********************************************
    * release memory
    */
    public void dereference()
    {
//      KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventPostProcessor(this);
        editor = null;

        if (numSpinner!=null)
        {
            EventListener listeners[] = ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().getListeners(KeyListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().removeKeyListener((KeyListener)listeners[i]);
            }
        }

        numSpinner = null;
        if (allRB!=null)
        {
            EventListener listeners[] = allRB.getListeners(ChangeListener.class);
            for(int i=0; i<listeners.length; i++)
            {
                allRB.removeChangeListener((ChangeListener)listeners[i]);
            }

            allRB=null;
        }

        // FB50417
        if (rowRB!=null) {
            rowRB.removeActionListener(this);
        }
        if (colRB!=null) {
            colRB.removeActionListener(this);
        }        // end FB50417

        rowRB=null;
        colRB=null;
        beforeRB=null;
        afterRB=null;
        okButton = null;
        rowAction = null;
    }
    // this constructor is used when a row needs to be added when tab is pressed at start or end of table
    XMLTableRowColPanel(XMLEditorPane editora)
    {
        super(false);
        this.editor = editora;
        rowAction = new InsertXMLTableRowAction();
    }
    // this is called when tab is pressed at start or end of a table
    void insertRow(boolean before)
    {
        rowAction.insertRow(before, 1);
    }

    XMLTableRowColPanel(JButton button, XMLEditorPane editora, boolean isInsert)
    {
        super(false);
        this.editor = editora;
        okButton=button;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        init(isInsert);
    }
    private void init(boolean isInsert)
    {
        JPanel insertPanel = new JPanel(false);
        JPanel fieldPanel = new JPanel(false);
        ButtonGroup bg = new ButtonGroup();

        insertPanel.setLayout(new GridLayout(0, 1));
        rowRB = new JRadioButton("Row",true);
        rowRB.setMnemonic('R');
        colRB = new JRadioButton("Column");
        colRB.setMnemonic('L');
        bg.add(rowRB);
        bg.add(colRB);
        insertPanel.add(rowRB);
        insertPanel.add(colRB);

        fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.X_AXIS));
//      fieldPanel.setLayout(new GridLayout(1, 0));
//GridLayout simply makes a bunch of components equal in size and displays them in the requested number of rows and columns.

        //SpinnerNumberModel(int value, int minimum, int maximum, int stepSize)
        numSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().setBackground(Color.WHITE);
        numSpinner.setMaximumSize(numSpinner.getPreferredSize());
        ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().addKeyListener(new KeyAdapter()
            {
                public void keyPressed(KeyEvent e)  {
                    if (e.getKeyCode()==KeyEvent.VK_ENTER) {
                        okButton.doClick();
                    }
                }
            });

        if (isInsert)
        {
            JLabel numLabel = new JLabel("Number to Insert: ", JLabel.LEFT);
            numLabel.setLabelFor(numSpinner);
            numLabel.setDisplayedMnemonic('N');
            fieldPanel.add(numLabel);
            insertPanel.setBorder(BorderFactory.createTitledBorder("Insert Type"));
            ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().
                getAccessibleContext().setAccessibleDescription("Enter number to insert.");
            ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().
                setToolTipText("Maximum value is "+((SpinnerNumberModel)numSpinner.getModel()).getMaximum());
            buildInsert();

            // unique insert panel has already been added, separate them
            add(Box.createRigidArea(new Dimension(0, 5)));
        }
        else
        {
            JLabel numLabel = null;
            allRB = new JRadioButton("Entire Table");
            allRB.setMnemonic('T');
            allRB.addChangeListener(new ChangeListener()
                {
                    public void stateChanged(ChangeEvent e)
                    {
                        numSpinner.setEnabled(!allRB.isSelected());
                    }
                });

            bg.add(allRB);
            insertPanel.add(allRB);
            numLabel = new JLabel("Number to Delete: ", JLabel.LEFT);
            numLabel.setLabelFor(numSpinner);
            numLabel.setDisplayedMnemonic('N');
            fieldPanel.add(numLabel);
            insertPanel.setBorder(BorderFactory.createTitledBorder("Delete"));
            ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().
                getAccessibleContext().setAccessibleDescription("Enter number to delete.");
            buildDelete();
            //FB50417
            // if deleting, add listeners to radio buttons to set the max number of items
            rowRB.addActionListener(this);
            colRB.addActionListener(this);
            // calculate the maximum number of items that can be deleted
            getTableBounds(editor);
            ((SpinnerNumberModel)numSpinner.getModel()).setValue(new Integer(1));
            ((SpinnerNumberModel)numSpinner.getModel()).setMaximum(new Integer(maxRows));  // rows is initially selected
            ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().
                setToolTipText("Maximum value is "+maxRows);
        }

        // separate label and textfield with a rigid area -- an invisible lightweight component used to add space between components
        fieldPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        fieldPanel.add(numSpinner);
        // Glue is an invisible lightweight component that grows as necessary to absorb any extra space in its container.
        fieldPanel.add(Box.createHorizontalGlue());

        insertPanel.add(fieldPanel);

        add(insertPanel);

        setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5)); // top, left, bottom, and right insets
//      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventPostProcessor(this);
    }

/*  public boolean postProcessKeyEvent(KeyEvent e)
    {
        if (e.getID()==KeyEvent.KEY_RELEASED)
        {
            int keyCode = e.getKeyCode();
            if (keyCode==KeyEvent.VK_ENTER) // was enter key
            {
                // the following needed for accessibility
                if (rowRB!=null && rowRB.hasFocus())
                {
                    rowRB.doClick();
                }else
                if (colRB!=null && colRB.hasFocus())
                {
                    colRB.doClick();
                }else
                if (beforeRB!=null && beforeRB.hasFocus())
                {
                    beforeRB.doClick();
                }else
                if (afterRB!=null && afterRB.hasFocus())
                {
                    afterRB.doClick();
                }else
                if (allRB!=null && allRB.hasFocus())
                {
                    allRB.doClick();
                }
            }
        }
        return false;
    }
*/

    /*********************************************************************
    * used for delete and setting max number of items FB50417
    * @param e ActionEvent
    */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==rowRB)
        {
            ((SpinnerNumberModel)numSpinner.getModel()).setValue(new Integer(1));
            ((SpinnerNumberModel)numSpinner.getModel()).setMaximum(new Integer(maxRows));
            ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().
                setToolTipText("Maximum value is "+maxRows);
        }
        if (e.getSource()==colRB)
        {
            ((SpinnerNumberModel)numSpinner.getModel()).setValue(new Integer(1));
            ((SpinnerNumberModel)numSpinner.getModel()).setMaximum(new Integer(maxCols));
            ((JSpinner.DefaultEditor)numSpinner.getEditor()).getTextField().
                setToolTipText("Maximum value is "+maxCols);
        }
    }

    private void buildDelete()
    {
        // add rows or columns
        getAccessibleContext().setAccessibleDescription("Delete rows or columns from an HTML table.");

        rowRB.getAccessibleContext().setAccessibleDescription("Delete a row.");
        colRB.getAccessibleContext().setAccessibleDescription("Delete a column.");

        // create buttons
        okButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Action act = null;
                    JComponent comp = null;
                    if (rowRB.isSelected()) {
                        act = new DeleteXMLTableRowAction();
                    }
                    else if (colRB.isSelected()) {
                        act = new DeleteXMLTableColAction();
                    }
                    else {
                        act = new DeleteXMLTableAction();
                    }

                    act.actionPerformed(e);

                    comp = (JComponent)e.getSource();
                    // must be close or cancel
                    ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
                    ((JDialog)comp.getTopLevelAncestor()).dispose();
                }
            });
    }

    private void buildInsert()
    {
        JPanel locPanel = new JPanel(false);
        ButtonGroup bg = new ButtonGroup();

        // add rows or columns
        getAccessibleContext().setAccessibleDescription("Add rows or columns to an HTML table.");

        rowRB.getAccessibleContext().setAccessibleDescription("Insert a new row.");
        colRB.getAccessibleContext().setAccessibleDescription("Insert a new column.");
        colRB.setMnemonic('L');

        locPanel.setLayout(new GridLayout(0, 1));
        beforeRB = new JRadioButton("Before selected row/column",true);
        beforeRB.getAccessibleContext().setAccessibleDescription("Insert before selected row/column.");
        beforeRB.setMnemonic('b');
        afterRB = new JRadioButton("After selected row/column");
        afterRB.getAccessibleContext().setAccessibleDescription("Insert after selected row/column.");
        afterRB.setMnemonic('a');
        bg.add(beforeRB);
        bg.add(afterRB);
        locPanel.add(beforeRB);
        locPanel.add(afterRB);
        locPanel.setBorder(BorderFactory.createTitledBorder("Insert Location"));

        add(locPanel);

        // create buttons
        okButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    Action act = null;
                    JComponent comp = null;
                    if (rowRB.isSelected()) {
                        act = new InsertXMLTableRowAction();
                    }
                    else {
                        act = new InsertXMLTableColAction();
                    }

                    act.actionPerformed(e);

                    comp = (JComponent)e.getSource();
                    // must be close or cancel
                    ((JDialog)comp.getTopLevelAncestor()).setVisible(false);
                    ((JDialog)comp.getTopLevelAncestor()).dispose();
                }
            });
    }

    private void getTableBounds(JEditorPane jeditor) // FB50417
    {
        XMLDocument doc = (XMLDocument)jeditor.getDocument();
        int offset = jeditor.getSelectionStart();
        Element tr = doc.getParagraphElement(offset);

        Element table = tr;
        while(!table.getName().equals(HTML.Tag.TABLE.toString()))
        {
            tr = table;  // find top-level parent
            table = table.getParentElement();
        }

        maxRows = table.getElementCount()-table.getElementIndex(offset);
        maxCols = tr.getElementCount()-tr.getElementIndex(offset);
        //System.err.println("tr "+tr+" table: "+table);
        //System.err.println("td.id "+tr.getElementIndex(offset)+" tr.id: "+table.getElementIndex(offset));
        //System.err.println("maxrows "+maxRows+" maxcols: "+maxCols);
    }

    // this is only enabled when the user is inside a table
    private class InsertXMLTableRowAction extends HTMLEditorKit.InsertHTMLTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertXMLTableRowAction()
        {
            super(INSERT_TR_ACTION, "<tr><"+"td"+">???</td></tr>", HTML.Tag.TABLE, HTML.Tag.TR);
        }

        private String buildXML(XMLEditorPane xeditor, int offset)//, int endPos)
        {
            //XMLDocument doc = (XMLDocument)xeditor.getDocument();
            javax.swing.text.Element rowParagraph = getParagraphElement(xeditor, offset);
            // get number of td cells
            int numCells = rowParagraph.getElementCount();

            // requires <table><tr>.... not just <tr> because of dtd requirements
            StringBuffer sb = new StringBuffer("<table><tr>");
            for (int c=0; c<numCells; c++)
            {
                sb.append("<"+"td"+"></td>");
            }
            sb.append("</tr></table>");
            return sb.toString();
        }

        // get the element to use for insertion point
        private javax.swing.text.Element getParagraphElement(JEditorPane jeditor, int offset)
        {
            XMLDocument doc = (XMLDocument)jeditor.getDocument();
            javax.swing.text.Element rowParagraph = doc.getParagraphElement(offset);

            javax.swing.text.Element parent = rowParagraph;
            while(!parent.getName().equals(HTML.Tag.TABLE.toString()))
            {
                rowParagraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            return rowParagraph;
        }

        public void actionPerformed(ActionEvent ae)
        {
            insertRow(beforeRB.isSelected(),
                ((SpinnerNumberModel)numSpinner.getModel()).getNumber().intValue());
        }

        // called when tab is pressed at start or end of a table
        void insertRow(boolean before, int numRows)
        {
            String thXml = null;
            String elemInfo = null;
            int trId = 0;
            boolean isHeadRow = false;

            XMLDocument doc = (XMLDocument)editor.getDocument();
            int offset = editor.getSelectionStart();
            javax.swing.text.Element row = getParagraphElement(editor, offset);
            // build the html to be inserted
            html = buildXML(editor, offset);//, offset);
            thXml = html;

            // always group this
            elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(addTag.toString());
            editor.getUndoEditMgr().setStartReplaceEdit(elemInfo);

            trId = row.getParentElement().getElementIndex(row.getStartOffset());
            isHeadRow = (before && trId==0 && row.getElement(0).getName().equals(HTML.Tag.TH.toString()));
            if (isHeadRow)
            {
                XMLToggleTHAction action = null;
                // build html for new first row
                StringBuffer sb = new StringBuffer(html);
                XMLGenerator.replaceText(sb,"<"+"td","<"+"th");
                XMLGenerator.replaceText(sb,"</td","</th");
                thXml = sb.toString();
                // must remove th from first row
                action =  (XMLToggleTHAction)((XMLEditorKit)editor.getEditorKit()).
                    getDtdAction(TOGGLE_TH_ACTION);
                action.toggleHeader(editor);
            }
            try{
                int caretPos= 0;
                int insertOffset = 0;
                javax.swing.text.Element table = null;
                if (before) {
                    insertOffset = row.getStartOffset();
                }
                else {
                    insertOffset = Math.min(row.getEndOffset(),doc.getLength());
                }
                caretPos=insertOffset;
                if (row.getEndOffset()>doc.getLength()) {// term newline is in row
                    caretPos = row.getEndOffset();
                }

                // Adds the given element in the parent with the contents specified as
                // an HTML string.
                table = row.getParentElement();
                for (int i=0; i<numRows; i++)
                {
                    // last one in will be on top
                    if (i==numRows-1 && isHeadRow) {
                        doc.addInnerHTML(table, addTag, thXml, insertOffset);
                    }
                    else {
                        doc.addInnerHTML(table, addTag, html, insertOffset);
                    }
                }

                editor.setCaretPosition(caretPos);  // move cursor to insert location
            }
            catch(Exception ble)
            {
                System.err.println("InsertXMLTableRowAction: "+ble);
                ble.printStackTrace(System.err);
            }
            finally
            {
                editor.getUndoEditMgr().setEndReplaceEdit();  // undo as one action
            }
        }
    }

    private class InsertXMLTableColAction extends HTMLEditorKit.InsertHTMLTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertXMLTableColAction()
        {
            super(INSERT_TD_ACTION, "<table><"+"tr"+"><"+"td></td></tr></table>", HTML.Tag.TR, HTML.Tag.TD);
        }

        // get the element to use for insertion point
        private javax.swing.text.Element getParagraphElement(JEditorPane jeditor, int offset)
        {
            XMLDocument doc = (XMLDocument)jeditor.getDocument();
            Element paragraph = doc.getParagraphElement(offset);

            Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.TR.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            return paragraph;
        }

        public void actionPerformed(ActionEvent ae)
        {
            XMLDocument doc = (XMLDocument)getHTMLDocument(editor);
            int offset = editor.getSelectionStart();
            Element paragraph = getParagraphElement(editor, offset); // get td
            try{
                int caretPos = 0;
                Element parent = null;
                int tdId = 0;
                Element table = null;
                String xml = null;
                HTML.Tag addXmlTag = null;

                // always group this because it involves a hidden remove()
                String elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(addTag.toString());
                editor.getUndoEditMgr().setStartReplaceEdit(elemInfo);

                caretPos = offset;

                // Adds the given element in the parent with the contents specified as
                // an HTML string.
                parent = paragraph.getParentElement();  // parent = tr
//System.err.println("paragraph "+paragraph);
//System.err.println("addtag: "+addTag+" parent "+parent);
                // parent is <tr>
                // must find index of this td tag and insert a td tag in each tr tag
                tdId = parent.getElementIndex(paragraph.getStartOffset()); //  Gets the child element index closest to the given offset.
//System.err.println("id "+tdId+" elem(id): "+parent.getElement(tdId));

                table = parent.getParentElement();
                xml = html; // default to td insert
                addXmlTag = addTag;
                // add a cell to each row in the table
                for (int i=0; i<table.getElementCount(); i++)
                {
                    int insertOffset = 0;
                    int numCols = 0;
                    Element trChild = table.getElement(i);
//System.err.println("trchild "+trChild+" elemcnt: "+trChild.getElementCount());
                    Element tdChild = trChild.getElement(tdId);
//System.err.print("tdchild "+tdChild);

                    if (i==0) // set caret pos at first inserted cell
                    {
                        // if first row is a table header, insert th instead of td
                        if (tdChild.getName().equals(HTML.Tag.TH.toString()))
                        {
                            xml = "<table><"+"tr"+"><"+"th"+"></th></tr></table>";
                            addXmlTag = HTML.Tag.TH;
                        }

                        if (beforeRB.isSelected()) {
                            caretPos = tdChild.getStartOffset();
                        }
                        else {
                            caretPos = tdChild.getEndOffset();
                        }
                    }
                    else
                    {
                        xml = html;
                        addXmlTag = addTag;
                    }

                    if (beforeRB.isSelected()) {
                        insertOffset = tdChild.getStartOffset();
                    }
                    else {
                        insertOffset = Math.min(tdChild.getEndOffset(),doc.getLength());
                    }

                    // insert the number of columns requested
                    numCols = ((SpinnerNumberModel)numSpinner.getModel()).getNumber().intValue();
                    for (int xi=0; xi<numCols; xi++)
                    {
//System.err.println("inserting xml "+xml);
                        doc.addInnerHTML(trChild, addXmlTag, xml, insertOffset);
                    }
                }

                editor.getUndoEditMgr().setEndReplaceEdit();  // undo as one action

                editor.setCaretPosition(caretPos);  // move cursor to insert location
            }
            catch(Exception ble)
            {
                System.err.println("InsertXMLTableRowAction: "+ble);
                ble.printStackTrace(System.err);
            }
        }
    }

    private class DeleteXMLTableColAction extends StyledEditorKit.StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	DeleteXMLTableColAction() {
            super(DELETE_TD_ACTION);
        }

        private javax.swing.text.Element getParagraphElement(JEditorPane jeditor, int offset)
        {
            XMLDocument doc = (XMLDocument)jeditor.getDocument();
            Element paragraph = doc.getParagraphElement(offset);

            Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.TR.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            return paragraph;
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            int offset = 0;
            Element paragraph = null;
            XMLDocument doc = (XMLDocument)editor.getDocument();
            doc.setDeleteTableCell(true);
            offset = editor.getSelectionStart();
            paragraph = getParagraphElement(editor, offset); // get table cell
            try{
                Element parent = null;
                int tdId = 0;
                Element table = null;
                int numDel = 0;

                int caretPos = paragraph.getStartOffset();  // move cursor to start location

                // always group this because of extra removes
                editor.getUndoEditMgr().setStartReplaceEdit("remove column");

                parent = paragraph.getParentElement();  // parent = tr
                // parent is <tr>
                // must find index of this td tag and remove a td tag from each tr tag
                tdId = parent.getElementIndex(paragraph.getStartOffset()); //  Gets the child element index closest to the given offset.
//System.err.println("id "+tdId+" elem(id): "+parent.getElement(tdId));

                table = parent.getParentElement();

                numDel = Math.min(parent.getElementCount()-tdId,
                    ((SpinnerNumberModel)numSpinner.getModel()).getNumber().intValue()); // can't delete more than the table contains

                // delete the number of cols requested
                if (parent.getElementCount()>1)
                {
//System.err.println("MORE than one col in the table");
                    if (tdId>0) {
                        caretPos=table.getElement(0).getElement(tdId-1).getStartOffset();
                    }
                    else {
                        caretPos=table.getElement(0).getElement(tdId).getStartOffset();
                    }
                }

                if (tdId==0 && parent.getElementCount()==numDel) // remove the entire table
                {
                    int len = 0;
                    int start = 0;
                    caretPos = table.getStartOffset();  // move cursor to start location
                    len = table.getEndOffset()-table.getStartOffset();
                    start = table.getStartOffset();

                    if (table.getEndOffset()>doc.getLength()) // no extra new line
                    {
                        // insert a paragraph to restore term new line
                        try{
                            doc.insertAfterEnd(table,"<p>X</p>");
                            // previous will split table with single cell table containing term new line
                            doc.remove(doc.getLength()-1,1); // remove single cell table
                            // now remove the X
                            doc.remove(doc.getLength()-1,1);
                        }catch(Exception ble) {
                            System.err.println("exc "+ble);
                            ble.printStackTrace(System.err);
                        }
                    }
                    // remove the table
                    doc.remove(start, len);
                }
                else
                {
                    // remove the table col(s) in each row
                    for (int r=0; r<table.getElementCount(); r++)
                    {
                        Element row = table.getElement(r); //  Gets the child element
                        for (int xi=0; xi<numDel; xi++)
                        {
                            // there will be one less element each time, so use tdId over again
                            Element col = row.getElement(tdId);
    //System.err.print("tdId: "+tdId+" row#: "+r+" rowcnt: "+row.getElementCount()+" deleting col "+col);
                            int len = col.getEndOffset()-col.getStartOffset();
                            int start = col.getStartOffset();

                            // if this is the last cell.. don't remove the terminating new line
                            if(tdId+1==row.getElementCount())
                            {
    //System.err.println("LAST column in row tdId "+tdId+" xi: "+xi+" numDel: "+numDel);
                                start--;
                            }
                            // remove this cell
                            doc.remove(start, len);
                        }
                    }
                }

                caretPos=Math.min(caretPos,doc.getLength());

                editor.setCaretPosition(caretPos);  // move cursor to start location
            } catch (BadLocationException ble) {
                System.err.println("exc "+ble);
                ble.printStackTrace(System.err);
            }
            finally
            {
                doc.setDeleteTableCell(false);
                editor.getUndoEditMgr().setEndReplaceEdit();  // undo as one action
                XMLEditorKit.updateStatusBarMsg(editor);
            }
        }
    }

    private class DeleteXMLTableRowAction extends StyledEditorKit.StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	DeleteXMLTableRowAction() {
            super(DELETE_TR_ACTION);
        }

        private javax.swing.text.Element getParagraphElement(JEditorPane jeditor, int offset)
        {
            XMLDocument doc = (XMLDocument)jeditor.getDocument();
            Element paragraph = doc.getParagraphElement(offset);

            Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.TABLE.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            return paragraph;
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            int offset = 0;
            Element paragraph = null;
            XMLDocument doc = (XMLDocument)editor.getDocument();
            doc.setDeleteTableCell(true);
            offset = editor.getSelectionStart();
            paragraph = getParagraphElement(editor, offset); // get table row

            try{
                int caretPos = paragraph.getStartOffset();  // move cursor to start location
                int len = paragraph.getEndOffset()-paragraph.getStartOffset();
                int start = paragraph.getStartOffset();
                Element parent = null;
                int trId = 0;
                int numDel = 0;

                // always group this because of extra removes
                editor.getUndoEditMgr().setStartReplaceEdit("remove row");

                parent = paragraph.getParentElement();  // get table

                // must find index of this <tr>
                trId = parent.getElementIndex(paragraph.getStartOffset()); //  Gets the child element index closest to the given offset.
                numDel = Math.min(parent.getElementCount()-trId,
                    ((SpinnerNumberModel)numSpinner.getModel()).getNumber().intValue());// can't delete more than the table contains

                // delete the number of rows requested
                // accumulate the length to remove
                for (int xi=1; xi<numDel; xi++)
                {
                    Element row = parent.getElement(trId+xi); //  Gets the child element
                    len+=row.getEndOffset()-row.getStartOffset();
                }

                if (parent.getElementCount()==1) // only one row in the table
                {
//System.err.println("ONLY one row in the table!!");
                    // if term new line is in table, this must be handled differently
                    if (parent.getEndOffset()>doc.getLength()) // no extra new line
                    {
                        // insert a paragraph to restore term new line
                        try{
                            doc.insertAfterEnd(parent,"<p>X</p>");
                            // previous will split table with single cell table containing term new line
                            doc.remove(doc.getLength()-1,1); // remove single cell table
                            // now remove the X
                            doc.remove(doc.getLength()-1,1);
                        }catch(Exception ble) {
                            System.err.println("exc "+ble);
                            ble.printStackTrace(System.err);
                        }
                    }
                }
                else
                {
//  System.err.println("MORE than one row in the table");
                    // last row in the table is not properly deleted unless term new line of previous row is included
                    if (trId+numDel==parent.getElementCount())
                    {
//  System.err.println("LAST row in table");
                        start--; // get term new line of prev row
                        caretPos--;
                    }
                }

                // remove the table row(s)
                doc.remove(start, len);

                caretPos=Math.min(caretPos,doc.getLength());

                editor.setCaretPosition(caretPos);  // move cursor to start location
            } catch (BadLocationException ble) {
                System.err.println("exc "+ble);
                ble.printStackTrace(System.err);
            }
            finally
            {
                doc.setDeleteTableCell(false);
                editor.getUndoEditMgr().setEndReplaceEdit();  // undo as one action
                XMLEditorKit.updateStatusBarMsg(editor);
            }
        }
    }

    private class DeleteXMLTableAction extends StyledEditorKit.StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	DeleteXMLTableAction() {
            super(DELETE_TABLE_ACTION);
        }

        private javax.swing.text.Element getParagraphElement(JEditorPane jeditor, int offset)
        {
            XMLDocument doc = (XMLDocument)jeditor.getDocument();
            Element paragraph = doc.getParagraphElement(offset);

            Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.TABLE.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            return parent;
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            XMLDocument doc = (XMLDocument)editor.getDocument();
            int offset = editor.getSelectionStart();
            Element paragraph = getParagraphElement(editor, offset); // get table

            try{
                int caretPos = paragraph.getStartOffset();  // move cursor to start location
                int len = paragraph.getEndOffset()-paragraph.getStartOffset();
                int start = paragraph.getStartOffset();

                // always group this because of extra removes
                editor.getUndoEditMgr().setStartReplaceEdit("remove table");

                if (paragraph.getEndOffset()>doc.getLength()) // no extra new line
                {
                    // insert a paragraph to restore term new line
                    try{
                        doc.insertAfterEnd(paragraph,"<p>X</p>");
                        // previous will split table with single cell table containing term new line
                        doc.remove(doc.getLength()-1,1); // remove single cell table
                        // now remove the X
                        doc.remove(doc.getLength()-1,1);
                    }catch(Exception ble) {
                        System.err.println("exc "+ble);
                        ble.printStackTrace(System.err);
                    }
                }
                // remove the table
                doc.remove(start, len);
                caretPos=Math.min(caretPos,doc.getLength());

                editor.getUndoEditMgr().setEndReplaceEdit();  // undo as one action
                XMLEditorKit.updateStatusBarMsg(editor);
                editor.setCaretPosition(caretPos);  // move cursor to start location
            } catch (BadLocationException ble) {
                System.out.println(ble.getMessage());  // jtest req
            }
        }
    }

}
