// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.text.html.parser.*;

/******************************************************************************
* This is derived to provide a modified StyledDocument class.  It provides
* XML actions.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLEditorKit.java,v $
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.5  2006/05/10 14:43:00  wendy
// Change e-announce to EACM
//
// Revision 1.4  2006/01/25 18:59:04  wendy
// AHE copyright
//
// Revision 1.3  2005/12/16 15:52:13  wendy
// Changes for DQA for td, th tags
//
// Revision 1.2  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:19  tony
// This is the initial load of OPICM
//
//
class XMLEditorKit extends HTMLEditorKit implements XMLEditorGlobals
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    // get the default DTD
    static final DTD DEFAULT_DTD = XMLDefaultParserDelegator.getDefaultDTD();
    private static final int TOKEN_THRESHOLD=100;

    private static Hashtable parserHash = new Hashtable();  // one parser delegator per DTD type
    private String dtdName=null;  // if defaulted, must change how
                            // all dtd actions are obtained...

/*
From HtmlEditorKit:
    private static final Action[] defaultActions = {
    new InsertHTMLTextAction("InsertTable", INSERT_TABLE_HTML,
                 HTML.Tag.BODY, HTML.Tag.TABLE),
    new InsertHTMLTextAction("InsertTableRow", INSERT_TABLE_HTML,
                 HTML.Tag.TABLE, HTML.Tag.TR,
                 HTML.Tag.BODY, HTML.Tag.TABLE),
    new InsertHTMLTextAction("InsertTableDataCell", INSERT_TABLE_HTML,
                 HTML.Tag.TR, HTML.Tag.TD,
                 HTML.Tag.BODY, HTML.Tag.TABLE),

    new InsertHTMLTextAction("InsertUnorderedListItem", INSERT_UL_HTML,
                 HTML.Tag.UL, HTML.Tag.LI,
                 HTML.Tag.BODY, HTML.Tag.UL),

    new InsertHTMLTextAction("InsertOrderedListItem", INSERT_OL_HTML,
                 HTML.Tag.OL, HTML.Tag.LI,
                 HTML.Tag.BODY, HTML.Tag.OL),

    new InsertHRAction(),
    new InsertHTMLTextAction("InsertPre", INSERT_PRE_HTML,
                 HTML.Tag.BODY, HTML.Tag.PRE),
    };

*/
    private Hashtable allDtdActions = new Hashtable();

    void dereference()
    {
        dtdName = null;
        allDtdActions.clear();
        allDtdActions = null;
    }

    static final Hashtable ACTION_NAMES_TBL;
    private static final Vector ATTR_ACTION_NAMES_VCT, DEP_ACTION_NAMES_VCT;
    static {
        // this is used to match tag to action(s) that is used to create the tag in the editor
        ACTION_NAMES_TBL = new Hashtable();
        ATTR_ACTION_NAMES_VCT = new Vector();
        DEP_ACTION_NAMES_VCT = new Vector();
        ACTION_NAMES_TBL.put("b", FONT_B_ACTION);
        ACTION_NAMES_TBL.put("i", FONT_I_ACTION);
        ACTION_NAMES_TBL.put("u", FONT_U_ACTION);
        ACTION_NAMES_TBL.put("strong", FONT_STRONG_ACTION);
        ACTION_NAMES_TBL.put("em",FONT_EM_ACTION);
        ACTION_NAMES_TBL.put(PUBLISH_TAG_ATTR,INSERT_WEBONLY_ACTION+"|"+INSERT_PRINTONLY_ACTION);
        // save attribute action names
        ATTR_ACTION_NAMES_VCT.addElement(FONT_B_ACTION);
        ATTR_ACTION_NAMES_VCT.addElement(FONT_I_ACTION);
        ATTR_ACTION_NAMES_VCT.addElement(FONT_U_ACTION);
        ATTR_ACTION_NAMES_VCT.addElement(FONT_STRONG_ACTION);
        ATTR_ACTION_NAMES_VCT.addElement(FONT_EM_ACTION);
        ATTR_ACTION_NAMES_VCT.addElement(INSERT_BR_ACTION);  // here because it is dependent on parent tag

        ACTION_NAMES_TBL.put("h1",H1_ACTION);
        ACTION_NAMES_TBL.put("h2",H2_ACTION);
        ACTION_NAMES_TBL.put("h3",H3_ACTION);
        ACTION_NAMES_TBL.put("h4",H4_ACTION);
        ACTION_NAMES_TBL.put("h5",H5_ACTION);
        ACTION_NAMES_TBL.put("h6",H6_ACTION);

        ACTION_NAMES_TBL.put("table",INSERT_TABLE_ACTION);  //v111
        ACTION_NAMES_TBL.put("tr",INSERT_TR_TD_ACTION+"|"+DELETE_TR_TD_ACTION+"|"+
                TOGGLE_BORDER_ACTION+"|"+TOGGLE_TH_ACTION);  //v111

        // td tag is not supported as a separate action, so register it to
        // prevent err msg 'XMLEditorKit:************Missing action for element!! td' and th
        ACTION_NAMES_TBL.put("td",INSERT_TR_TD_ACTION); //v111
        ACTION_NAMES_TBL.put("th",INSERT_TR_TD_ACTION);  //v111
        ACTION_NAMES_TBL.put("sc",INSERT_SPECCHAR_ACTION);

        ACTION_NAMES_TBL.put("ul",INSERT_UL_ACTION
            +"|"+INSERT_NESTED_UL_ACTION);  // nestedlist
        ACTION_NAMES_TBL.put("ol",INSERT_OL_ACTION
            +"|"+INSERT_NESTED_OL_ACTION);  // nestedlist
        ACTION_NAMES_TBL.put("li",INSERT_LI_ACTION+"|"+INSERT_PARENT_LI_ACTION);
        // add dependent action names
        DEP_ACTION_NAMES_VCT.addElement(INSERT_NESTED_P_ACTION);
        DEP_ACTION_NAMES_VCT.addElement(INSERT_NESTED_PRE_ACTION);

//nestedlist
        DEP_ACTION_NAMES_VCT.addElement(INSERT_LI_ACTION);
        DEP_ACTION_NAMES_VCT.addElement(INSERT_PARENT_LI_ACTION);
        DEP_ACTION_NAMES_VCT.addElement(INSERT_NESTED_UL_ACTION);
        DEP_ACTION_NAMES_VCT.addElement(INSERT_NESTED_OL_ACTION);

        DEP_ACTION_NAMES_VCT.addElement(INSERT_TR_TD_ACTION); //v111
        DEP_ACTION_NAMES_VCT.addElement(DELETE_TR_TD_ACTION); //v111
        DEP_ACTION_NAMES_VCT.addElement(TOGGLE_BORDER_ACTION); //v111
        DEP_ACTION_NAMES_VCT.addElement(TOGGLE_TH_ACTION); //v111

        ACTION_NAMES_TBL.put("p",INSERT_P_ACTION+"|"+INSERT_NESTED_P_ACTION);
        ACTION_NAMES_TBL.put("pre",INSERT_PRE_ACTION+"|"+INSERT_NESTED_PRE_ACTION);//v11

        ACTION_NAMES_TBL.put("br",INSERT_BR_ACTION);
//        ACTION_NAMES_TBL.put("hr",INSERT_BR_ACTION);
    }

    Action getDtdAction(String name) { return (Action)allDtdActions.get(name);}

    private void createDTDActions()
    {
        // create all possible dtd actions, not all will be used by a specific dtd but they are
        // needed to instantiate the menu items properly
        // add font style action used by b, i, u
        allDtdActions.put(FONT_B_ACTION,new BoldAction());
        allDtdActions.put(FONT_I_ACTION,new ItalicAction());
        allDtdActions.put(FONT_U_ACTION,new UnderlineAction());
        allDtdActions.put(FONT_STRONG_ACTION,new StrongAction());
        allDtdActions.put(FONT_EM_ACTION,new EmphasisAction());

        // add html actions p
        allDtdActions.put(INSERT_P_ACTION,
            new ReplaceXMLAction(INSERT_P_ACTION, HTML.Tag.BODY, HTML.Tag.P));
        allDtdActions.put(INSERT_NESTED_P_ACTION,
                new InsertNestedPAction(INSERT_NESTED_P_ACTION,HTML.Tag.P));

        // add html actions pre - v11
        allDtdActions.put(INSERT_PRE_ACTION,
            new ReplaceXMLAction(INSERT_PRE_ACTION, HTML.Tag.BODY, HTML.Tag.PRE));
        allDtdActions.put(INSERT_NESTED_PRE_ACTION,
                new InsertNestedPAction(INSERT_NESTED_PRE_ACTION,HTML.Tag.PRE));

        // add ol, ul
        allDtdActions.put(INSERT_UL_ACTION,
            new XMLReplaceListAction(INSERT_UL_ACTION, HTML.Tag.UL));
        allDtdActions.put(INSERT_OL_ACTION,
            new XMLReplaceListAction(INSERT_OL_ACTION, HTML.Tag.OL));

        // add li
//nestedlist
        allDtdActions.put(INSERT_LI_ACTION,
            new InsertXMLListItemAction(INSERT_LI_ACTION));
        allDtdActions.put(INSERT_PARENT_LI_ACTION,
            new InsertXMLParentListItemAction(INSERT_PARENT_LI_ACTION));
        allDtdActions.put(INSERT_NESTED_UL_ACTION,
            new NestedXMLListAction(INSERT_NESTED_UL_ACTION, HTML.Tag.UL));
        allDtdActions.put(INSERT_NESTED_OL_ACTION,
            new NestedXMLListAction(INSERT_NESTED_OL_ACTION, HTML.Tag.OL));
// end nestedlist

        // add html header actions h1-h6
        // these actions will allow user to create different level headings
        allDtdActions.put(H1_ACTION,
            new ReplaceXMLAction(H1_ACTION, HTML.Tag.BODY, HTML.Tag.H1));
        allDtdActions.put(H2_ACTION,
            new ReplaceXMLAction(H2_ACTION, HTML.Tag.BODY, HTML.Tag.H2));
        allDtdActions.put(H3_ACTION,
            new ReplaceXMLAction(H3_ACTION, HTML.Tag.BODY, HTML.Tag.H3));
        allDtdActions.put(H4_ACTION,
            new ReplaceXMLAction(H4_ACTION, HTML.Tag.BODY, HTML.Tag.H4));
        allDtdActions.put(H5_ACTION,
            new ReplaceXMLAction(H5_ACTION, HTML.Tag.BODY, HTML.Tag.H5));
        allDtdActions.put(H6_ACTION,
            new ReplaceXMLAction(H6_ACTION, HTML.Tag.BODY, HTML.Tag.H6));

        // add insert html single actions br
        allDtdActions.put(INSERT_BR_ACTION,new InsertBRAction());

        // add publish tag attribute actions
        allDtdActions.put(INSERT_WEBONLY_ACTION,new PublishAttrAction(INSERT_WEBONLY_ACTION));
        allDtdActions.put(INSERT_PRINTONLY_ACTION,new PublishAttrAction(INSERT_PRINTONLY_ACTION));

        // add table actions v111
        allDtdActions.put(INSERT_TABLE_ACTION,new InsertTableAction());
        allDtdActions.put(INSERT_TR_TD_ACTION,new InsertTableRowColAction());
        allDtdActions.put(DELETE_TR_TD_ACTION,new DeleteTableRowColAction());
        allDtdActions.put(TOGGLE_BORDER_ACTION,new ToggleBorderAction());
        allDtdActions.put(TOGGLE_TH_ACTION,new XMLToggleTHAction());

        //INSERT_SPECCHAR_ACTION
        allDtdActions.put(INSERT_SPECCHAR_ACTION,new InsertSpecialAction());
    }

    /***********************************************************************************
    * This is used to add a list item.  It is only enabled when the user is inside a list
    * It will add a list item to the current list.
    */
    private static class InsertXMLListItemAction extends InsertHTMLTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertXMLListItemAction(String name)
        {
            super(name, "<li></li>", null, HTML.Tag.LI);
        }

        /*******************************
         * get the parent element to use for insertion of list item
         * @param editor
         * @param offset
         * @return
         */
        protected javax.swing.text.Element getParentElement(JEditorPane editor, int offset)
        {
            HTMLDocument doc = getHTMLDocument(editor);
            javax.swing.text.Element paragraph = doc.getParagraphElement(offset);  // p-implied

            while(!paragraph.getName().equals(HTML.Tag.LI.toString()))
            {
                 // find list item parent
                paragraph = paragraph.getParentElement();
            }

            return paragraph.getParentElement();  // li->ol or ul
        }

        /***********************
         * get start offset
         * @param editor
         * @return
         */
        protected int getStartOffset(JEditorPane editor)
        {
            return editor.getSelectionStart();
        }

        /*****************
         * get caret pos
         * @param editor
         * @param offset
         * @return
         */
        protected int getCaretPos(JEditorPane editor, int offset)
        {
            int pos;
            HTMLDocument doc = getHTMLDocument(editor);
            javax.swing.text.Element paragraph = doc.getParagraphElement(offset);  // p-implied

            while(!paragraph.getName().equals(HTML.Tag.LI.toString()))
            {
                 // find list item parent
                paragraph = paragraph.getParentElement();
            }

            if (offset==paragraph.getStartOffset()){ // cursor at start of list
                pos = offset; //return offset;
            }
            else {
                pos = offset+1;
            }

            return pos;//offset+1;
        }
        /**
         * Inserts the HTML into the document.
         *
         * @param ae the event
         */
        public void actionPerformed(ActionEvent ae)
        {
            XMLDocument doc;
            int offset, caretPos;
            javax.swing.text.Element parent;

            JEditorPane editor = getEditor(ae);
            if (editor!=null) {
                doc = (XMLDocument)getHTMLDocument(editor);
                offset = getStartOffset(editor);
                caretPos = getCaretPos(editor, offset);
                parent = getParentElement(editor, offset);

                // build the html to be inserted
                // requires <ol><li>.... not just <li> because of dtd requirements
                html = "<"+parent.getName()+"><"+addTag+"></"+addTag+"></"+parent.getName()+">";

                try{
                    // always group this because it involves a hidden remove()
                    String elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(addTag.toString());
                    ((XMLEditorPane)editor).getUndoEditMgr().setStartReplaceEdit(elemInfo);

                    // Adds the given element in the parent with the contents specified as
                    // an HTML string.
                    doc.addInnerHTML(parent, addTag, html, offset);

                    ((XMLEditorPane)editor).getUndoEditMgr().setEndReplaceEdit();  // undo as one action

                    editor.setCaretPosition(caretPos);
                }
                catch(Exception ble)
                {
                    System.err.println("InsertXMLListItemAction exception: "+ble);
                    ble.printStackTrace(System.err);
                }
            }
        }
    }

    /*******************************************************************************
    * This is used to add a list item to the parent list.  It is only enabled when
    * the user is inside a list.
    * It will find the parent to add the list item too, not the current list.
    * Current list will not be split, parent list item is added after current list
    */
    private static class InsertXMLParentListItemAction extends InsertXMLListItemAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertXMLParentListItemAction(String name)
        {
            super(name);
        }

        /******************************
         * get start offset
         * @param editor
         * @return
         */
        protected int getStartOffset(JEditorPane editor)
        {
            int offset= editor.getSelectionStart();
            //HTMLDocument doc = getHTMLDocument(editor);

            javax.swing.text.Element nestedParent = super.getParentElement(editor,offset);

            // force parent list item to go after the nested list
            return nestedParent.getEndOffset()-1;
        }

        /*******************************
         * get the parent element to use for insertion of list item
         * @param editor
         * @param offset
         * @return
         */
        protected javax.swing.text.Element getParentElement(JEditorPane editor, int offset)
        {
            javax.swing.text.Element nestedParent = super.getParentElement(editor,offset);

            // get nesting parent
            return nestedParent.getParentElement().getParentElement();  // li->ol or ul
        }

        /*****************
         * get caret pos
         * @param editor
         * @param offset
         * @return
         */
        protected int getCaretPos(JEditorPane editor, int offset)
        {
            return offset+1; // position cursor after nested list
        }
    }

    /*******************************************************************************
    * This is used to insert nested lists. ParentTag is determined by cursor location.
    * No conversion of structure, just insertion of a nested list, user must build from
    * there.  Nested list will be inserted after all content in a list item, no matter
    * where the cursor is at the time the action is invoked.
    */
    private static class NestedXMLListAction extends InsertHTMLTextAction
    {
    	private static final long serialVersionUID = 1L;
    	NestedXMLListAction(String name, HTML.Tag addTag)
        {
            super(name, "<"+addTag.toString()+"></"+addTag.toString()+">", null, addTag);
            html = "<"+addTag.toString()+"><li></li></"+addTag.toString()+">";
        }

        /******************************
         * get start offset
         * @param editor
         * @return
         */
        protected int getStartOffset(XMLEditorPane editor)
        {
            int offset= editor.getSelectionStart();
            HTMLDocument doc = getHTMLDocument(editor);
            javax.swing.text.Element parent = doc.getParagraphElement(offset);

            //javax.swing.text.Element parent = paragraph;
            /*if(parent.getName().equals(HTML.Tag.IMPLIED.toString())||
               parent.getName().equals(HTML.Tag.P.toString())) // look for nested p
            {
                parent = parent.getParentElement();  // get <li>?
            }*/
            while(!parent.getName().equals(HTML.Tag.LI.toString()))
            {
                //paragraph = parent;  // find list item parent
                parent = parent.getParentElement();
            }

            // but if li has children, must find end of child (p-implied) containing the offset
            return parent.getElement(parent.getElementIndex(offset)).getEndOffset()-1; // get before newline
        }

        /*******************************************
         * get the element to use for replacement
         * @param editor
         * @param offset
         * @return
         */
        protected javax.swing.text.Element getParagraphElement(JEditorPane editor, int offset)
        {
            HTMLDocument doc = getHTMLDocument(editor);
            javax.swing.text.Element paragraph = doc.getParagraphElement(offset);

            javax.swing.text.Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.LI.toString()))
            {
                paragraph = parent;  // find list item parent
                parent = parent.getParentElement();
            }

            return paragraph;
        }

        /**
         * Inserts the HTML into the document.
         *
         * @param ae the event
         */
        public void actionPerformed(ActionEvent ae)
        {
            XMLDocument doc;
            int offset;
            javax.swing.text.Element paragraph;

            JEditorPane editor = getEditor(ae);
            if (editor!=null) {
                doc = (XMLDocument)getHTMLDocument(editor);
                // this will always find the entire element, selection of parts is ignored
                offset = getStartOffset((XMLEditorPane)editor);

                paragraph = getParagraphElement(editor, offset);

                // parent is the list item
                parentTag = (HTML.Tag)paragraph.getParentElement().getAttributes().getAttribute(StyleConstants.NameAttribute);

                try{
                    boolean addedSpace = false;
                    String elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(addTag.toString());
                    ((XMLEditorPane)editor).getUndoEditMgr().setStartReplaceEdit(elemInfo);

                    // if the list item does not have any content, the p-implied is not
                    // inserted between the li and ol, add a space to force it
                    if (paragraph.getParentElement().getEndOffset()-
                        paragraph.getParentElement().getStartOffset()==1)
                    {
                        System.err.println("*******special handling nested list to be inserted in empty li!!");
                        editor.setSelectionStart(paragraph.getParentElement().getStartOffset());
                        editor.replaceSelection(" ");
                        offset++;  // account for added space
                        addedSpace = true;
                    }

                    // insert the html
                    editor.setSelectionStart(offset);

                    super.actionPerformed(ae);

                    // since the html must be inserted before the new line, an extra new line
                    // element is created, remove it
                    doc.remove(offset+1, 1);
                    if (addedSpace) {
                        doc.remove(offset-1, 1);
                    }

                    ((XMLEditorPane)editor).getUndoEditMgr().setEndReplaceEdit();  // undo as one action
                }catch(Exception ble) {
                    System.err.println("exc "+ble);
                    ble.printStackTrace(System.err);}
            }
        }
    }

// nested structure todo:
// copy of subset of structure for paste.. done but major testing needed with write of doc
// check web and catalog preview output.. probably doesn't handle nested lists properly
// if anything can be nested in a table, more work is needed

    /**
    * Generic class for insertion at top level, everything is inserted at parentTag level.
    */
    private static class ReplaceXMLAction extends InsertHTMLTextAction
    {
    	private static final long serialVersionUID = 1L;
    	private String htmlTag;
        ReplaceXMLAction(String name, HTML.Tag parentTag, HTML.Tag addTag)
        {
            super(name, "<"+addTag.toString()+"></"+addTag.toString()+">",  parentTag, addTag);
            this.htmlTag = addTag.toString();
        }

        private String buildXML(XMLEditorPane editor, int offset, int endPos)
        {
            String xml= "<"+htmlTag+"></"+htmlTag+">";
            // build the html to be inserted
            // if there is selection, the contents and format are copied
            if (offset!=endPos)
            {
                try{
                    // get formatted text without structure
                    // the array will have one element for each p-implied or p structure's text
                    // each array element will end in a new line, these will be discarded
                    // for p and h1-h6 by parser, pre will maintain them
                    XMLWriter w = new XMLWriter(getHTMLDocument(editor), offset,endPos-offset);
                    StringBuffer[] array = w.writeFormattedText();
                    StringBuffer sb = new StringBuffer("<"+htmlTag+">");
                    for (int i=0; i<array.length;i++){
                        sb.append(array[i].toString());
                    }

                    sb.append("</"+htmlTag+">");
                    xml = sb.toString();
                    sb.setLength(0);
                }
                catch(Exception ble)
                {
                    System.err.println("ReplaceXMLAction: exc "+ble);
                    ble.printStackTrace(System.err);
                }
            }

/* orig
            // build the html to be inserted
            // if there is selection, the contents and structure are copied
            // invalid structure for the new element, will be discarded in the Reader
            // we can't just pull content, because br are part of content and not
            // transferred properly
            // this assumes that invalid tags will be thrown away.. it will be a problem
            // if the tags are not invalid, yet they should be discarded.. how to know
            // when to discard?  probably would need to be a separate action
            // for example, p in list item.. or any nested content
            if (offset!=endPos)
            {
                // get contents and structure to maintain stylized text (with <br /> as <br>
                String data = XMLGenerator.replaceEmptyXML(((XMLEditorPane)editor).
                                    getStructureAndText(offset,endPos-offset));
                // must handle case where start at p and select other tags then press p
                // remove all of this type of tag then surround it
                data = XMLGenerator.removeTag(data, htmlTag.toString());
                // removal of <li> will allow <pre> to maintain separate lines that were list items
                data = XMLGenerator.removeTag(data, "li"); //v11

                // build the html to insert
//              if (!data.startsWith("<"+htmlTag))
                    xml = "<"+htmlTag+">"+data+"</"+htmlTag+">";
//              else
//                  xml = data;  // already enclosed in this tag.. happens if p is used inside p
            }

*/
            return xml;
        }

        /**************************
         * get the element to use for replacement
         * @param editor
         * @param offset
         * @return
         */
        private javax.swing.text.Element getParagraphElement(JEditorPane editor, int offset)
        {
            HTMLDocument doc = getHTMLDocument(editor);
            javax.swing.text.Element paragraph = doc.getParagraphElement(offset);

            javax.swing.text.Element parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.BODY.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }

            return paragraph;
        }

        /******************************
         * get start offset
         * @param editor
         * @return
         */
        private int getStartOffset(XMLEditorPane editor)
        {
            int offset = editor.getSelectionStart();
            HTMLDocument doc = getHTMLDocument(editor);
            // this will get the list item's range
            javax.swing.text.Element paragraph = doc.getParagraphElement(offset);
//System.err.print("getstartoffset offset: "+offset+" paragraph: "+paragraph);
            // force action to occur after this element
            if (offset!=0 && //if caret at 0, allow insert there
                offset ==paragraph.getEndOffset()-1 &&
                paragraph.getEndOffset()<doc.getLength()) {
                offset++;
            }

            return offset;//editor.getSelectionStart();
        }
        private int getEndOffset(XMLEditorPane editor)
        {
            int endPos = editor.getSelectionEnd();
            HTMLDocument doc = getHTMLDocument(editor);
            // this will get the list item's range
            javax.swing.text.Element paragraph = doc.getParagraphElement(endPos);
            // adjust to entire paragraph if the only thing missing is the term newline
            // this prevents the terminating newline from creating another paragraph
            // consisting of only a new line
//System.err.print("getendoffset endpos: "+endPos+" paragraph: "+paragraph);
            if (paragraph.getEndOffset()-paragraph.getStartOffset()>1 && // not empty
                endPos ==paragraph.getEndOffset()-1 &&
                endPos<doc.getLength()) { // don't select term p
                endPos++;
            }
            return endPos;
        }

// fixme.. should any part of listitem be selected?  should entire list item be used?

        // find out if nested structure is selected
        private javax.swing.text.Element getLowestElementWithOffsets(javax.swing.text.Element paragraph,
            int offset, int endPos)
        {
            int childId;
            javax.swing.text.Element elem;
//System.err.print("getLowestElementWithOffsets entered with "+paragraph);
            if (paragraph.isLeaf()){
                elem = paragraph; //return paragraph;
            }
            else{
                if (paragraph.getStartOffset()==offset &&
                    (paragraph.getEndOffset()==endPos || paragraph.getEndOffset()==endPos-1))
                {
    //System.err.print("entire paragraph is selected "+paragraph);
                    elem = paragraph; //return paragraph;
                }
                else {
                    childId = paragraph.getElementIndex(offset);
                    elem = getLowestElementWithOffsets(paragraph.getElement(childId),offset,endPos);
                }
            }
            return elem;
        }

        /**
         * Inserts the HTML into the document.
         *
         * @param ae the event
         */
        public void actionPerformed(ActionEvent ae)
        {
            XMLDocument doc;
            int offset, endPos,delLen;
            javax.swing.text.Element paragraph,endParagraph;
            boolean wasEndOfDoc = false;
            JEditorPane editor = getEditor(ae);
            if (editor!=null) {
                doc = (XMLDocument)getHTMLDocument(editor);
                offset = getStartOffset((XMLEditorPane)editor);//editor.getSelectionStart();
                endPos = getEndOffset((XMLEditorPane)editor);//Math.min(editor.getSelectionEnd(),doc.getLength());
                paragraph = getParagraphElement(editor, offset);
                // make sure we get the last selected element, not start of next
                endParagraph = getParagraphElement(editor, editor.getSelectionEnd()-1);//endPos-1);

                delLen = endPos-offset;  // length to be deleted
                if ((endPos== endParagraph.getEndOffset()-1 )&&
                    endParagraph.getEndOffset()>doc.getLength())
                {
                    endPos=doc.getLength();
                    wasEndOfDoc=true;
                }

                // if we are at the end of the document, do not refer to previous element
                // can happen when inserting list at terminating p
                if (wasEndOfDoc && paragraph.getStartOffset()>endParagraph.getStartOffset()){
                    endParagraph = paragraph;
                }

                // build the html to be inserted
                html = buildXML((XMLEditorPane)editor, offset, endPos);

    //System.err.println("inserting html: "+html);
    //System.err.println("parentTag: "+parentTag);
    //System.err.print("paragraph: "+paragraph);
    //System.err.print("endparagraph: "+endParagraph);
                try{
                    int origlen;
                    // always group this because some methods involve a hidden remove()
                    String elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(addTag.toString());
                    ((XMLEditorPane)editor).getUndoEditMgr().setStartReplaceEdit(elemInfo);

                    if (offset!=endPos &&
                        offset == paragraph.getStartOffset() &&  // selection is entire element
                        (endPos == paragraph.getEndOffset() ||   // at end
                            endPos+1 == paragraph.getEndOffset()))  // or before terminating new line
                    {
    //System.err.println("calling setOuterhtml");
                        // Replaces the given element in the parent with the contents specified as
                        // an HTML string.
                        doc.setOuterHTML(paragraph, html);
                        ((XMLEditorPane)editor).getUndoEditMgr().setEndReplaceEdit();  // undo as one action
                        editor.setCaretPosition(offset);  // move cursor to insert location
                        //return;
                    }
                    else {
                        origlen = doc.getLength();
                        if (offset == paragraph.getStartOffset())
                        {
        //System.err.println("calling insertBeforeStart");
                            // Insert the HTML specified as string before the start of the given element.
                            doc.insertBeforeStart(paragraph,html);
                            // if any blanks were at the end of structure, they are not re-rendered!!
                            // Remove old.
                            if (offset!=endPos)
                            {
                                // calculate start of removal range.  paragraph inserted before may
                                // no longer be part of the structure
                                endPos = offset+(doc.getLength()-origlen);
                                if (wasEndOfDoc)
                                {
                                    endPos--;
                                    delLen++;
                                }
                                if ((delLen+endPos)>doc.getLength()){ // error, can't remove that much
                                    delLen=doc.getLength()-endPos;
                                }
        //System.err.println("insertBeforeStart remove dellen: "+delLen+" endPos: "+endPos);
                                // remove contents, it was replaced with the new structure and text
                                doc.remove(endPos, delLen);
                            }

                            editor.setCaretPosition(offset);  // move cursor to insert location
                        }
                        else  // if at end.. use insertAfterEnd()
                            if (endPos == endParagraph.getEndOffset()||
                                endPos==doc.getLength())    // at end of document
                            {
        //System.err.print("\ninsertAfterEnd offset: "+offset+" endpos: "+endPos+" endParagraph: "+endParagraph);
                                int newPoffset = endParagraph.getEndOffset();  // get it before removal

                                javax.swing.text.Element elem = doc.getParagraphElement(endPos);
                                if (elem.getEndOffset()-1==elem.getStartOffset() &&
                                    elem.getName().equals(HTML.Tag.IMPLIED.toString())){
                                    newPoffset--;
                                }

                                // Insert the HTML specified as a string after the end of the given element.
                                doc.insertAfterEnd(endParagraph,html);
                                // if this is done at the end of the document, an extra paragraph tag is added
                                // at the end.. the endPos > length when insertAtEnd() runs

                                editor.setCaretPosition(newPoffset);  // move cursor to insert location

                                // Remove old.
                                if (offset!=endPos)
                                {
                                    int len = delLen -1;
                                    // if the start paragraph element is last list item, adjust offset for nl
                                    if (paragraph.getName().equals("ol") ||
                                        paragraph.getName().equals("ul"))
                                    {
                                        javax.swing.text.Element child = getLowestElementWithOffsets(paragraph,offset,endPos);
                                        if (child.getStartOffset()==offset &&
                                            child.getEndOffset()-1<=endPos)
                                        {
        //System.err.println("lowest child is to be completely removed after insertAfterEnd");
                                            offset--;
                                            len++;
                                        }
                                    }

                                    // if the start paragraph element is in table, restore delLen
                                    if (paragraph.getName().equals("table")){
                                        len++;
                                    }

                                    if (wasEndOfDoc) {
                                        len++;  // account for xtra newline
                                    }
                                    // remove contents, it was replaced with the new structure and text
                                    doc.remove(offset, len);
                                }
                            }
                            else    // split the current element
                            {
                                boolean removeDone = false;
                                if (offset!=endPos &&  // remove before if not crossing element boundaries
                                    endParagraph.getName().equals(paragraph.getName()))// same type list to same list
                                {
                                    // remove contents, it will be replaced with the new structure and text
                                    // must be careful here if the selection is a nested structure
                                    if (endParagraph==paragraph) // same parent
                                    {
                                        // if entire child is to be removed, adjust offset to include
                                        // preceding term new line otherwise any structure that follows
                                        // is pulled up into the structure that should have been deleted
                                        javax.swing.text.Element child = getLowestElementWithOffsets(paragraph,offset,endPos);
                                        if (child.getStartOffset()==offset &&
                                            child.getEndOffset()==endPos)
                                        {
        //System.err.println("lowest child is to be completely removed before split.. removing offset "+(offset-1)+" len: "+delLen);
                                            doc.remove(offset-1, delLen);
                                            removeDone=true;
                                        }
                                    }

                                    if (!removeDone)
                                    {
        //System.err.println("before split.. removing offset "+offset+" len: "+delLen);
                                        doc.remove(offset, delLen);
                                    }
                                    removeDone=true;

                                    //v111 if crossed tables or in one table, insert between the tables
                                    // this is kind of strange but it preserves table structure
                                    // and why would a user ever want to do this anyway
                                    if (paragraph.getName().equals(HTML.Tag.TABLE.toString()))
                                    {
                                        offset = paragraph.getEndOffset();
                                    }
                                }
                                else  // no selection
                                {
                                    //v111 if in a table, insert after the table
                                    // this is kind of strange but it preserves table structure
                                    // and why would a user ever want to do this anyway
                                    if (paragraph.getName().equals(HTML.Tag.TABLE.toString()))
                                    {
                                        offset = paragraph.getEndOffset();
                                        endPos=offset;  // maintain no selection
                                    }
                                }

                                editor.setSelectionStart(offset);

        //System.err.println("split....");
                                super.actionPerformed(ae);
                                if (offset!=endPos && !removeDone)
                                {
                                    endPos = offset+(doc.getLength()-origlen);
        //System.err.println("split.. removing endpos "+(endPos)+" len: "+delLen);
                                    // remove contents, it was replaced with the new structure and text
                                    doc.remove(endPos, delLen);
                                }
                            }

                        ((XMLEditorPane)editor).getUndoEditMgr().setEndReplaceEdit();  // undo as one action
                    }
                }catch(Exception ble) {
                    System.err.println("exc "+ble);
                    ble.printStackTrace(System.err);}
            }
        }
    }

/*
// Replaces the children of the given element with the contents specified as an HTML string.
// setInnerHTML(Element elem, String htmlText)

// Replaces the given element in the parent with the contents specified as an HTML string.
// setOuterHTML(Element elem, String htmlText)

// Inserts the HTML specified as a string at the start of the element.
// insertAfterStart(Element elem, String htmlText)

// Inserts the HTML specified as a string at the end of the element.
// insertBeforeEnd(Element elem, String htmlText)

// Inserts the HTML specified as string before the start of the given element.
// insertBeforeStart(Element elem, String htmlText)

// Inserts the HTML specified as a string after the end of the given element.
// insertAfterEnd(Element elem, String htmlText)

*/

    /**
     * This will always nest if possible.  At actionPerformed time it will determine
     * the parent HTML.Tag based on the paragraph element at the selection start.
     * There is no way to turn off this nested tag without deleting the contents of
     * the tag.. the user must delete characters as opposed to selection and turn off nested <p>
     */
    private static class InsertNestedPAction extends InsertHTMLTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertNestedPAction(String name, HTML.Tag addTag) {
            super(name, "<"+addTag+"></"+addTag+">", HTML.Tag.BODY, addTag);
        }

        private void setParentTag(HTMLDocument doc, int offset)
        {
            javax.swing.text.Element elem = doc.getParagraphElement(offset);
            while(elem!=null && !elem.getName().equals(HTML.Tag.LI.toString())){// &&
                //!elem.getName().equals(HTML.Tag.TD.toString())) // future insert in a table?
                elem = elem.getParentElement();
            }

            if (elem!=null){
                parentTag = (HTML.Tag)elem.getAttributes().getAttribute(StyleConstants.NameAttribute);
            }
            else{
                parentTag = HTML.Tag.BODY;  // this should not happen!!
            }
        }

        private String buildXML(JEditorPane editor, int offset, int endPos)
        {
            String xml= "<"+addTag+"></"+addTag+">";
            // build the html to be inserted
            // if there is selection, the contents and format are copied
            if (offset!=endPos)
            {
                try{
                    // get formatted text without structure
                    // the array will have one element for each p-implied or p structure's text
                    // each array element will end in a new line, these will be discarded
                    // for p, pre will maintain them
                    XMLWriter w = new XMLWriter(getHTMLDocument(editor), offset,endPos-offset);
                    StringBuffer[] array = w.writeFormattedText();
                    StringBuffer sb = new StringBuffer("<"+addTag+">");
                    for (int i=0; i<array.length;i++){
                        sb.append(array[i].toString());
                    }

                    sb.append("</"+addTag+">");
                    xml = sb.toString();
                    sb.setLength(0);
                }
                catch(Exception ble)
                {
                    System.err.println("ReplaceXMLAction: exc "+ble);
                    ble.printStackTrace(System.err);
                }
            }
            return xml;
        }

        /**
         * Inserts the HTML into the document.
         *
         * @param ae the event
         */
        public void actionPerformed(ActionEvent ae)
        {
            JEditorPane editor = getEditor(ae);
            if (editor != null)
            {
                String elemInfo;
                HTMLDocument doc = getHTMLDocument(editor);
                int offset = editor.getSelectionStart();
                int endPos = Math.min(editor.getSelectionEnd(),doc.getLength());

                setParentTag(doc, offset);

                html = buildXML(editor, offset,endPos);

                elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(addTag.toString());
                ((XMLEditorPane)editor).getUndoEditMgr().setStartReplaceEdit(elemInfo);

                // if there is selection, move it into the new paragraph
                if (offset!=endPos)
                {
                    try {
                        // remove contents, it will be replaced with the new structure and text
                        doc.remove(offset, endPos - offset);
                    }catch(BadLocationException ble) {
                        System.out.println(ble.getMessage());  // jtest req
                    }
                }

                super.actionPerformed(ae);
                if (offset==0) // an extra structure is left behind at offset=0
                {
                    try {
                        doc.remove(offset,1);
                    }catch(BadLocationException ble) {
                        System.out.println(ble.getMessage());  // jtest req
                    }
                }

                ((XMLEditorPane)editor).getUndoEditMgr().setEndReplaceEdit();
            }
        }
    }

    XMLEditorKit()
    {
        createDTDActions();
    }

    /**
     * clone
     *
     * @return
     */
    public Object clone() {
        return new XMLEditorKit();
    }

    /**
     * Called when the kit is being installed into the
     * a JEditorPane.
     *
     * @param c the JEditorPane
     */
    public void install(JEditorPane c) {
        super.install(c);
    }

    /**
     * Create an uninitialized text storage model
     * that is appropriate for this type of editor.
     *
     * @return the model
     */
    public Document createDefaultDocument()
    {
        XMLDocument doc;
        // get the default style sheet, it is static, one for all
        // documents.  it is loaded from the default.css file in the swing
        // jar file.
        StyleSheet ss = new StyleSheet();  // get a private copy for the document

        // only valid in 1.3
        ss.addStyleSheet(getStyleSheet());

        doc = new XMLDocument(ss);

/*markup tests
        Style defaultStyle = ss.getStyle(StyleContext.DEFAULT_STYLE);
        XMLDocument doc = new XMLDocument(ss);

        Style deleteStyle = ss.addStyle(XMLEditor.DeletedStyle,null);
        StyleConstants.setStrikeThrough(deleteStyle,true);
        StyleConstants.setForeground(deleteStyle,java.awt.Color.red);
        deleteStyle.addAttribute(XMLEditor.MarkupMode,XMLEditor.DeletedStyle);
        Style insertStyle = ss.addStyle(XMLEditor.InsertedStyle,null);
        StyleConstants.setItalic(insertStyle,true);
        StyleConstants.setForeground(insertStyle,java.awt.Color.blue);
        insertStyle.addAttribute(XMLEditor.MarkupMode,XMLEditor.InsertedStyle);

//System.out.println("adding rules!!");
//      ss.addRule("h1 { color: blue; font-size: 24; }"+
//                     "  p.italicBold {font-style : italic;  font-weight : bold; }");

end markup tests*/

        // unknown tags and comments will be ignored, elements will not
        // be created for them
        //doc.setPreservesUnknownTags(false);

        doc.setParser(getParser());
        doc.setAsynchronousLoadPriority(4);
        doc.setTokenThreshold(TOKEN_THRESHOLD);

        return doc;
    }

    // input attributes are not getting reset when undo runs
    // select some chars, press bold, press undo and until the 'currentrun' changes bold is
    // not removed from the input attribute set, so any thing added will have those attributes
    void restoreInputAttributeSet()
    {
        createInputAttributes(getCharacterAttributeRun(), getInputAttributes());
    }

    // set the DTD to use with this editor kit
    // create the parserdelegate if it doesn't already exist
    void setDTDName(String dtdNamex)
    {
        Parser parser;
        if (dtdName==null) {
            dtdName = DEFAULT_DTDNAME;
        }
        this.dtdName = dtdNamex;
        parser = (Parser)parserHash.get(dtdName);
        if (parser == null)
        {
            parser = new XMLParserDelegator(dtdName);
            // at this point the name of the dtd actually used may not match dtdname.  this could
            // happen if the dtd specified could not be found.  the default is being used
            this.dtdName = ((XMLParserDelegator)parser).getDTDName();
            parserHash.put(this.dtdName,parser);
        }
    }
    String getDTDName() { return (dtdName==null?"None":dtdName);}

    /**
     * Fetch the parser to use for reading HTML streams.
     * @return Parser
     */
    protected Parser getParser()
    {
        Parser parser;
        if (dtdName==null) { // will be null when editor is instantiated
            parser = super.getParser();
        }
        else{
            parser = (Parser)parserHash.get(dtdName);
        }
        return parser;
    }

    // get the valid content for the specified element
    // vector of javax.swing.text.html.parser.Element is returned
    Vector getContentElements(String elemName)
    {
        Parser parser = getParser();
        Vector tmp;
        if (parser instanceof XMLParserDelegator)
        {
            tmp= ((XMLParserDelegator)parser).getContentElements(elemName);
        }
        else { // parser is the default parser.. the editor control is getting instantiated
            tmp= new Vector(1);
        }
        return tmp;
    }

    // get the list of all supported elements for the current dtd
    String[] getSupportedElements()
    {
        Parser parser = getParser();
        String[] tmp;
        if (parser instanceof XMLParserDelegator)
        {
            tmp = ((XMLParserDelegator)parser).getSupportedElements();
        }
        else { // parser is the default parser.. the editor control is getting instantiated
            tmp = null;
        }
        return tmp;
    }

    // get number of nested levels allowed
    int getNumberNestedLevels()
    {
        int num=0;
        Parser parser = getParser();
        if (parser instanceof XMLParserDelegator)
        {
            num= ((XMLParserDelegator)parser).getNumberNestedLevels();
        }
        //else {  // parser is the default parser.. the editor control is getting instantiated
        //    return 0;
        //}
        return num;
    }

    // get the element for the specified name
    javax.swing.text.html.parser.Element getElement(String elemName)
    {
        Parser parser = getParser();
        javax.swing.text.html.parser.Element elem =null;
        if (parser instanceof XMLParserDelegator)
        {
            elem =((XMLParserDelegator)parser).getElement(elemName);
        }
        //else { // parser is the default parser.. the editor control is getting instantiated
        //    return null;
        //}
        return elem;
    }

    // is the element supported by the current DTD
    boolean elementExists(String name)
    {
        Parser parser = getParser();
        boolean exists = false;
        if (parser instanceof XMLParserDelegator)
        {
            exists = ((XMLParserDelegator)parser).elementExists(name);
        }
        //else {  // parser is the default parser.. the editor control is getting instantiated
        //    return false;
        //}
        return exists;
    }
    // get list of action names supported by this dtd
    // action names correspond to the element name.. 'ol' has an action name of 'INSERT_OL_ACTION'
    // some of the elements share the same action such as 'p' and '?'
    private String[] getSupportedActionNames()
    {
        String array[];
        // must get list before parse is done, unknown tags have elements created for them!
        String elemArray[] = getSupportedElements();
        Vector vct = new Vector(1);
        for(int i=0; i<elemArray.length; i++)
        {
            StringTokenizer st;
            // get the action name used for this element name
            String name = (String)ACTION_NAMES_TBL.get(elemArray[i]);
            if (name==null)
            {
                System.err.println("XMLEditorKit:getSupportedActionNames() ************Missing action for element!! "+elemArray[i]);
                continue;
            }
            // check if this is a compound action name
            st = new StringTokenizer(name,"|");
            while(st.hasMoreTokens())
            {
                String token = st.nextToken();
                if (!vct.contains(token))  // don't need duplicate action names
                {
                    // must verify nested paragraphs are supported
                    if (token.equals(INSERT_NESTED_P_ACTION)||
                        token.equals(INSERT_NESTED_PRE_ACTION))
                    {
                        // make sure that p or pre is valid in li
                        Vector elemVct = getContentElements("li");
                        for (int ii=0; ii<elemVct.size(); ii++)
                        {
                            javax.swing.text.html.parser.Element elem =
                                (javax.swing.text.html.parser.Element)elemVct.elementAt(ii);
                            if (elem.name.equals(elemArray[i]))
                            {
                                vct.addElement(token);
                                break;
                            }
                        }
                    }
                    else {
                        vct.addElement(token);
                    }
                }
            }

        }
        array= new String[vct.size()];
        vct.copyInto(array);
        elemArray = null;
        vct.clear();
        vct = null;

        return array;
    }

    /**
     * Fetches the command list for the editor.  This is
     * the list of actions supported by the dtd. There are 3 sets of actions: basic, attribute and
     * dependent.  Basic are always enabled if the user can edit, attribute and dependent are context
     * sensitive.  Attribute actions are determined by the paragraph element, dependent actions are
     * determined by the parental hierarchy.
     *
     * @return the command list
     */
    Hashtable getDTDActions()
    {
        Hashtable dtdTbl = new Hashtable();
        // get list of action names supported by this dtd
        String keys[]=getSupportedActionNames();
        dtdTbl.put(BASIC_DTD, new HashSet());
        dtdTbl.put(ATTRIBUTE_DTD, new HashSet());
        dtdTbl.put(DEPENDENT_DTD, new HashSet());

        for (int i=0; i<keys.length; i++)
        {
            // get corresponding action for the key
            Action a = (Action)allDtdActions.get(keys[i]);
            if (a==null)
            {
                System.err.println("XMLEditorKit:**********Missing action for "+keys[i]);
                continue;
            }
            // put it into the correct bucket
            if (DEP_ACTION_NAMES_VCT.contains(keys[i]))
            {
                Set names = (Set) dtdTbl.get(DEPENDENT_DTD);
                names.add(a);
            }
            else
            if (ATTR_ACTION_NAMES_VCT.contains(keys[i]))
            {
                Set names = (Set) dtdTbl.get(ATTRIBUTE_DTD);
                names.add(a);
            }
            else  // must be basic action, except publish actions put them here but do not enable them in editor with all other basic actions
            {
                Set names = (Set) dtdTbl.get(BASIC_DTD);
                names.add(a);
            }
        }

        return dtdTbl;
    }

    /**
     * Fetches the command list for the editor.  This is
     * the list of all actions supported by the dtd.
     *
     * @return the command list
     */
    Action[] getAllDTDActions()
    {
        Action array[];
        Vector dtdActionsVct = new Vector(1);
        // the editor control is getting instantiated
        // return all possible actions
        for (Enumeration e = allDtdActions.elements(); e.hasMoreElements();) {
            dtdActionsVct.addElement(e.nextElement());
        }

        array= new Action[dtdActionsVct.size()];
        dtdActionsVct.copyInto(array);
        return array;
    }

    /**
     * Write content from a document to the given stream
     * in a format appropriate for this kind of content handler.
     *
     * @param out  The stream to write to
     * @param doc The source for the write.
     * @param pos The location in the document to fetch the
     *   content.
     * @param len The amount to write out.
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document.
     */
    public void write(Writer out, Document doc, int pos, int len)
    throws IOException, BadLocationException
    {
        // prevent formatting
        XMLWriter w = new XMLWriter(out, (HTMLDocument)doc, pos, len);
        w.write();
    }

    // this is only used to get the DTD supplied by the Java HTML pkg
    // character references and some elements are copied from the default
    // to the dynamically defined DTDs
    private static class XMLDefaultParserDelegator extends ParserDelegator
    {
    	private static final long serialVersionUID = 1L;
    	// this would not be necessary if the DTD was not private in ParserDelegator
        // ParserDelegator is used to read the DTD supplied with the html pkg
        static DTD getDefaultDTD()
        {
            DTD defDtd = null;
            String nm = "html32";
            try {
                setDefaultDTD();  // set static dtd in base class to prevent reloads
                defDtd = createDTD(DTD.getDTD(nm),nm);
            }catch(IOException ioe)
            {
                System.err.println("Could not get default dtd: " + nm+" "+ioe.getMessage());
            }
            return defDtd;
        }
    }

    private static class PublishAttrAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	PublishAttrAction(String name) {
            super(name);
        }

        private boolean checkChildrenForPublish(javax.swing.text.Element elem)
        {
            boolean ispub=false;
            // check list items for publish values
            for (int x=0; x<elem.getElementCount(); x++)
            {
                AttributeSet elemAttrs;
                javax.swing.text.Element child = elem.getElement(x);
                if (child.isLeaf()) {
                    //return false;
                    ispub=false;
                    break;
                }
                elemAttrs =child.getAttributes();
                if (elemAttrs.isDefined(PUBLISH_TAG_ATTR))
                {
                    if (!elemAttrs.getAttribute(PUBLISH_TAG_ATTR).equals("none")){
                        //return true;
                        ispub=true;
                        break;
                    }
                }
                if (checkChildrenForPublish(child)){
                    //return true;
                    ispub=true;
                    break;
                }
            }

            return ispub;//false;
        }

        private boolean checkParentForPublish(javax.swing.text.Element parent)
        {
            AttributeSet elemAttrs;
            boolean ispub = false;
            // break recursion, lists can only be contained by other lists or the body
            if (parent.getName().equals(HTML.Tag.BODY.toString())){
                ispub=false;
                //return false;
            }
            else {
                // check parent for publish values
                elemAttrs =parent.getAttributes();
                if (elemAttrs.isDefined(PUBLISH_TAG_ATTR) &&
                     (!elemAttrs.getAttribute(PUBLISH_TAG_ATTR).equals("none"))){
                        //return true;
                        ispub=true;

                }
                else {
                    if (checkParentForPublish(parent.getParentElement())){
                        //return true;
                        ispub=true;
                    }
                }
            }

            return ispub;//false;
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            SimpleAttributeSet attr;
            XMLEditorPane editor;
            Action webOnlyAction;
            Action printOnlyAction;
            int p0,p1;
            XMLDocument doc;
            String name;
            boolean warnUser=false;
//          String msg = null;
            String[] msgArray = null;
            javax.swing.text.Element[] pubElems;

            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))) {
                //return;
            }
            else {
                editor = (XMLEditorPane)target;
                webOnlyAction = ((XMLEditorKit)editor.getEditorKit()).getDtdAction(INSERT_WEBONLY_ACTION);
                printOnlyAction = ((XMLEditorKit)editor.getEditorKit()).getDtdAction(INSERT_PRINTONLY_ACTION);

                p0 = editor.getSelectionStart();
                doc = (XMLDocument)editor.getDocument();
                p1 = Math.min(editor.getSelectionEnd(),doc.getLength());

                // get the name of this action
                name = (String)getValue(Action.NAME);
                // if the user selected an entire list with list items already set to a publish value
                // popup a dialog warning them that list items will be blanked out if the
                // list is set
                pubElems = doc.getPublishAttributeControllingTags(p0,p1);

                for(int i=0; i<pubElems.length; i++)
                {
    //              msg = "One or more list items have a Publish tag attribute value. \n"+
    //              "Setting the Publish attribute for the entire list will remove\n"+
    //              "the attribute from the individual list items.\n\n"+
    //              "Do you want to continue?";
                    String[] array = {
                        "One or more list items (children) have a Publish tag attribute value.",
                        "Setting the Publish attribute for the entire list will remove",
                        "the attribute from the individual list items.",
                        " ",
                        "Do you want to continue?"
                        };

                    javax.swing.text.Element elem = pubElems[i];
                    if (!(elem.getName().equals("ol") ||
                            elem.getName().equals("ul") ||
                            elem.getName().equals("li"))){
                        continue;
                    }

                    // check list items for publish values
                    if (checkChildrenForPublish(elem))
                    {
                        warnUser=true;
                        msgArray = array;
                        break;
                    }

                    if (checkParentForPublish(elem.getParentElement()))
                    {
    //                  msg = "The list has a Publish tag attribute value. \n"+
    //                  "Setting the Publish attribute for the list item will remove\n"+
    //                  "the attribute from the entire list.\n\n"+
    //                  "Do you want to continue?";
                        String[] array2 = {
                            "The list (parent) has a Publish tag attribute value.",
                            "Setting the Publish attribute for the list item will remove",
                            "the attribute from the entire list.",
                            " ",
                            "Do you want to continue?"
                            };
                        msgArray = array2;
                        warnUser=true;
                        break;
                    }
                }
                if (warnUser)
                {
    //              int rc = JOptionPane.showConfirmDialog(editor, msgArray,
    //                  XMLEditorGlobals.APP_NAME+" XML Editor Confirm Publish", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    String accdesc[] = {
                        "Press Yes set publish attribute.",
                        "Press No cancel publish attribute."
                        };
                    int rc = XMLEditor.showAccessibleDialog(editor.getParent(),//editor, jre 1.4.0 positions dialog incorrectly
                            XMLEditorGlobals.APP_NAME+" XML Editor Confirm Publish",
                            JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION,
                            "Confirm publish action", msgArray, accdesc);
                    java.awt.Toolkit.getDefaultToolkit().beep();
                    msgArray = null;
                    accdesc = null;

                    editor.setFocusInEditor();  // applet does not restore focus

                    if (rc!=JOptionPane.YES_OPTION){ // cancel this action
                        return;
                    }
                }

                attr = new SimpleAttributeSet();
                if (name.equals(INSERT_WEBONLY_ACTION))  // adding webonly
                {
                    attr.addAttribute(PUBLISH_TAG_ATTR,WEBONLY_TAG_ATTR);
                    putValue(Action.NAME,"Remove "+INSERT_WEBONLY_ACTION);
                    printOnlyAction.putValue(Action.NAME,INSERT_PRINTONLY_ACTION);
                }
                else
                if (name.equals(INSERT_PRINTONLY_ACTION))  // adding printonly
                {
                    attr.addAttribute(PUBLISH_TAG_ATTR,PRINTONLY_TAG_ATTR);
                    putValue(Action.NAME,"Remove "+INSERT_PRINTONLY_ACTION);
                    webOnlyAction.putValue(Action.NAME,INSERT_WEBONLY_ACTION);
                }
                else
                if (name.equals("Remove "+INSERT_WEBONLY_ACTION))  // removing webonly
                {
                    attr.addAttribute(PUBLISH_TAG_ATTR,"none");
                    putValue(Action.NAME,INSERT_WEBONLY_ACTION);
                    printOnlyAction.putValue(Action.NAME,INSERT_PRINTONLY_ACTION);
                }
                else
                if (name.equals("Remove "+INSERT_PRINTONLY_ACTION))  // removing printonly
                {
                    attr.addAttribute(PUBLISH_TAG_ATTR,"none");
                    putValue(Action.NAME,INSERT_PRINTONLY_ACTION);
                    webOnlyAction.putValue(Action.NAME,INSERT_WEBONLY_ACTION);
                }

                // group changes or just set undo/redo msg
                editor.getUndoEditMgr().setStartReplaceEdit(name);

                doc.setPublishAttributes(p0, p1 - p0, attr, false);

                if (warnUser)  // list items had publish values, remove them now
                {
                    attr.addAttribute(PUBLISH_TAG_ATTR,"none");

                    for(int i=0; i<pubElems.length; i++)
                    {
                        javax.swing.text.Element elem = pubElems[i];
                        if (!(elem.getName().equals("ol") ||
                                elem.getName().equals("ul") ||
                                elem.getName().equals("li"))){
                            continue;
                        }

                        // if there is nested content, must check any children or parents
                        resetChildrenPublishAttr(elem, doc, attr);
                        resetParentPublishAttr(elem.getParentElement(), doc, attr);
                    }
                }

                editor.getUndoEditMgr().setEndReplaceEdit();
                updateStatusBarMsg(editor);
            }
        }

        private void resetChildrenPublishAttr(javax.swing.text.Element elem, XMLDocument doc,
                AttributeSet attr)
        {
            // check list items for publish values
            for (int x=0; x<elem.getElementCount(); x++)
            {
                AttributeSet elemAttrs;
                javax.swing.text.Element child = elem.getElement(x);
                // p-implied and content can not have publish attributes, skip them
                if (child.getName().equals(HTML.Tag.IMPLIED.toString())){
                    continue;
                }
                elemAttrs =child.getAttributes();
                if (elemAttrs.isDefined(PUBLISH_TAG_ATTR))
                {
                    if (!elemAttrs.getAttribute(PUBLISH_TAG_ATTR).equals("none"))
                    {
                        doc.setPublishAttributes(child.getStartOffset(),
                            child.getEndOffset()-child.getStartOffset(),
                            attr, false);
                    }
                }
                resetChildrenPublishAttr(child, doc, attr);
            }
        }
        private void resetParentPublishAttr(javax.swing.text.Element parent, XMLDocument doc,
                AttributeSet attr)
        {
            AttributeSet elemAttrs;
            // break recursion, lists can only be contained by other lists or the body
            if (!parent.getName().equals(HTML.Tag.BODY.toString())){
                elemAttrs =parent.getAttributes();
                if (elemAttrs.isDefined(PUBLISH_TAG_ATTR))
                {
                    if (!elemAttrs.getAttribute(PUBLISH_TAG_ATTR).equals("none"))
                    {
                        doc.setPublishAttributes(parent.getStartOffset(),
                            parent.getEndOffset()-parent.getStartOffset(),
                            attr, false);
                    }
                }

                resetParentPublishAttr(parent.getParentElement(), doc, attr);
            }
        }
    }


    static void updateStatusBarMsg(XMLEditorPane editor)
    {
        // update status bar message, but must get parent
        Container parent = editor.getParent();
        while(parent !=null)
        {
            if (parent instanceof XMLEditor)
            {
                ((XMLEditor)parent).setStatusBarMsg();
                break;//return;
            }
            parent = parent.getParent();
        }
    }

/*
From StyledEditorKit:
    public static class BoldAction extends StyledTextAction {

    public BoldAction() {
        super("font-bold");
    }

        public void actionPerformed(ActionEvent e) {
        JEditorPane editor = getEditor(e);
        if (editor != null) {
        StyledEditorKit kit = getStyledEditorKit(editor);
        MutableAttributeSet attr = kit.getInputAttributes();
        boolean bold = (StyleConstants.isBold(attr)) ? false : true;
        SimpleAttributeSet sas = new SimpleAttributeSet();
        StyleConstants.setBold(sas, bold);
        setCharacterAttributes(editor, sas, false);
        }
    }
    }
     protected final void setCharacterAttributes(JEditorPane editor,
                          AttributeSet attr, boolean replace) {
        int p0 = editor.getSelectionStart();
        int p1 = editor.getSelectionEnd();
        if (p0 != p1) {
        StyledDocument doc = getStyledDocument(editor);
        doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
        }
        StyledEditorKit k = getStyledEditorKit(editor);
        MutableAttributeSet inputAttributes = k.getInputAttributes();
        if (replace) {
        inputAttributes.removeAttributes(inputAttributes);
        }
        inputAttributes.addAttributes(attr);
    }
    */
    private static class StrongAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	StrongAction() {
            super(FONT_STRONG_ACTION);
            putValue(XML_CONTROL_KEY,"strong");// used to control enablement
        }

        public void actionPerformed(ActionEvent e)
        {
            StyledEditorKit kit;
            MutableAttributeSet attr;
            boolean bold;
            SimpleAttributeSet sas;
            JEditorPane editor = getEditor(e);
            if ((editor == null) || (!editor.isEditable())
                || (!(editor instanceof XMLEditorPane))) {
                //return;
            }
            else {
                // this uses the font-weight attribute to control 'strong'
                // it mimics 'bold' the XMLwriter will write it out as <strong>
                // this way we can still use the StyleConstants.isBold() and setBold()
                kit = getStyledEditorKit(editor);
                attr = kit.getInputAttributes();

                bold = (StyleConstants.isBold(attr)) ? false : true;
                sas = new SimpleAttributeSet();
                StyleConstants.setBold(sas, bold);
                sas.addAttribute(HTML.Tag.STRONG,SimpleAttributeSet.EMPTY);

                setCharacterAttributes(editor, sas, false);

                // make sure the actions reflect the latest changes
                ((XMLEditorPane)editor).ignoreCaret(false);
            }
        }
    }

    private static class EmphasisAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	EmphasisAction() {
            super(FONT_EM_ACTION);
            putValue(XML_CONTROL_KEY,"em");// used to control enablement
        }

        public void actionPerformed(ActionEvent e)
        {
            StyledEditorKit kit;
            MutableAttributeSet attr;
            boolean italic;
            SimpleAttributeSet sas;
            JEditorPane editor = getEditor(e);
            if ((editor == null) || (!editor.isEditable())
                || (!(editor instanceof XMLEditorPane))){
                //return;
            }
            else {
                // this uses the font-weight attribute to control 'em'
                // it mimics 'italic' the XMLwriter will write it out as <italic>
                // this way we can still use the StyleConstants.isItalic() and setItalic()
                kit = getStyledEditorKit(editor);
                attr = kit.getInputAttributes();

                italic = (StyleConstants.isItalic(attr)) ? false : true;
                sas = new SimpleAttributeSet();
                StyleConstants.setItalic(sas, italic);
                sas.addAttribute(HTML.Tag.EM,SimpleAttributeSet.EMPTY);

                setCharacterAttributes(editor, sas, false);

                // make sure the actions reflect the latest changes
                ((XMLEditorPane)editor).ignoreCaret(false);
            }
        }
    }

    /*********************************************************************
    * Derived to handle updating status bar
    */
    private static class BoldAction extends StyledEditorKit.BoldAction
    {
    	private static final long serialVersionUID = 1L;
    	BoldAction()
        {
            putValue(XML_CONTROL_KEY,"b");// used to control enablement
        }
        /**********************************************
         * Toggles the bold attribute.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))){
                //return;
            }else {
                super.actionPerformed(e);

                // make sure the actions reflect the latest changes
                ((XMLEditorPane)target).ignoreCaret(false);
            }
        }
    }

    /*********************************************************************
    * Derived to handle updating status bar
    */
    private static class ItalicAction extends StyledEditorKit.ItalicAction
    {
    	private static final long serialVersionUID = 1L;
    	ItalicAction()
        {
            putValue(XML_CONTROL_KEY,"i");// used to control enablement
        }
        /**********************************************
         * Toggles the italic attribute.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))) {
                //return;
            }
            else {
                super.actionPerformed(e);

                // make sure the actions reflect the latest changes
                ((XMLEditorPane)target).ignoreCaret(false);
            }
        }
    }
    /*********************************************************************
    * Derived to handle updating status bar
    */
    private static class UnderlineAction extends StyledEditorKit.UnderlineAction
    {
    	private static final long serialVersionUID = 1L;
    	UnderlineAction()
        {
            putValue(XML_CONTROL_KEY,"u");// used to control enablement
        }

        /**********************************************
         * Toggles the underline attribute.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))){
                //return;
            }else {
                super.actionPerformed(e);

                // make sure the actions reflect the latest changes
                ((XMLEditorPane)target).ignoreCaret(false);
            }
        }
    }

    /**
    // top line doesn't work because dtd says br is only valid in p or li.. so it fails html parser
    //      super(INSERT_BR_ACTION, "<br>", null, HTML.Tag.IMPLIED, null, null,//);//,can't access this constructor in HTMLEditorKit
    // this works but adds the html directly, not just inserting the <br>
    //      super(INSERT_BR_ACTION);, "<p><br></p>", null, null, null, null,//);//,can't access this constructor in HTMLEditorKit
    // insert the break but a <p> will be inserted before and after it
    // <p>Yo|u</p> with br inserted will become <p>Yo</p><p><br /></p><p>u</p>
     * can't use InsertHTMLTextAction because it requires parsing and <br> must be inside
     * other html or have a parent and this causes the surrounding tags to be inserted too.
     * <br> must be inserted as a leaf element so we must do it directly
     */
    private static class InsertBRAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertBRAction() {
            super(INSERT_BR_ACTION);
            putValue(XML_CONTROL_KEY,"br");  // used to control enablement
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))){
                //return;
            }
            else {
                final XMLEditorPane editor = (XMLEditorPane)target;
                XMLDocument doc = (XMLDocument)editor.getDocument();
                final int offset = editor.getSelectionStart();
                int endPos = Math.min(editor.getSelectionEnd(),doc.getLength());
                // disable caret until action is complete to avoid excessive
                // parsing and activation/deactivation of actions through caret listener
                editor.ignoreCaret(true);

                try {
                    // must use the input attributeset so bold is maintained
                    MutableAttributeSet attr = ((StyledEditorKit)editor.getEditorKit()).getInputAttributes();
                    editor.getCaret().setVisible(false);  // avoid flash
                    // if there is a selection, remove it first
                    if (offset != endPos) {
                        editor.getUndoEditMgr().setStartReplaceEdit("replacement");
                        doc.remove(offset, endPos - offset);
                    }
                    else  {// force end of compound text edit.. undo as separate units
                        editor.getUndoEditMgr().endCompoundTextEdit();
                    }

                    // insert the break
                    doc.insertBreak(offset, attr);
                    if (offset != endPos) {
                        editor.getUndoEditMgr().setEndReplaceEdit();
                    }
                    else  {// force end of compound text edit.. undo as separate units
                        editor.getUndoEditMgr().endCompoundTextEdit();
                    }

                    // cursor moved after the space, view must not have taken affect yet
                    // reset cursor after this completes when view is ready
                    SwingUtilities.invokeLater(new Runnable()
                        {
                            public void run()
                            {
                                editor.setCaretPosition(offset+1);
                                editor.getCaret().setVisible(true);
                                editor.ignoreCaret(false);
                            }
                        });
                }
                catch (BadLocationException bl) {
                    System.out.println(bl.getMessage());
                }
            }
        }
    }

    private static class ToggleBorderAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	ToggleBorderAction() {
            super(TOGGLE_BORDER_ACTION);
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            XMLEditorPane editor;
            XMLDocument doc;
            javax.swing.text.Element paragraph;
            SimpleAttributeSet sas;
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))) {
                //return;
            }
            else {
                editor = (XMLEditorPane)target;

                doc = (XMLDocument)editor.getDocument();
                paragraph = doc.getParagraphElement(editor.getSelectionStart());
                while(paragraph!=null && !paragraph.getName().equals(HTML.Tag.TABLE.toString()))
                {
                    paragraph = paragraph.getParentElement();
                }

                sas = new SimpleAttributeSet();
                if (paragraph.getAttributes().getAttribute(HTML.Attribute.BORDER)!=null)
                {
                    // if it has a border, turn it off
                    if (paragraph.getAttributes().getAttribute(HTML.Attribute.BORDER).toString().equals("1")) {
                        sas.addAttribute(HTML.Attribute.BORDER,"0");
                    }
                    else{
                        sas.addAttribute(HTML.Attribute.BORDER,"1");
                    }
                }
                else // border not specified to turn it on
                {
                    sas.addAttribute(HTML.Attribute.BORDER,"1");
                }

                doc.setParagraphAttributes(paragraph, sas,false);

                editor.setFocusInEditor();  // applet does not return focus to the editor
            }
        }
    }

    private static class InsertTableAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertTableAction() {
            super(INSERT_TABLE_ACTION);
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            XMLEditorPane editor;
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))){
                //return;
            }
            else {
                editor = (XMLEditorPane)target;

                if (editor.getSelectionStart()==editor.getSelectionEnd())
                {
                    XMLTableWizard theWiz = new XMLTableWizard(editor,XMLTableWizard.CREATE);
                    theWiz.show();
                    // it is modal, so dereference now
                    theWiz.dereference();
                }
                else  // user has made a selection, build the table from that
                {
                     // ask user if selection should be used in the table
                    String msgs[] = {
                        "Convert selected structure into a table?"
                        };

                    String accdesc[] = {
                        "Press Yes to create a table with one row for each line of text.",
                        "Press No to create an empty table.",
                        "Press Cancel to prevent table creation."
                        };
                    int result;
                    java.awt.Toolkit.getDefaultToolkit().beep();

                    result = XMLEditor.showAccessibleDialog(editor.getParent(),//editor, jre 1.4.0 positions dialog incorrectly
                        "Verify Table creation",
                        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION,
                        "Verify table creation with selected text.", msgs, accdesc);

                    msgs = null;
                    accdesc = null;

                    switch(result)
                    {
                    case JOptionPane.YES_OPTION:
                        {
                            XMLTableCreatePanel tblmgr = new XMLTableCreatePanel(editor);
                            tblmgr.createTableUsingSelection();
                            tblmgr.dereference();
                            break;
                        }
                    case JOptionPane.NO_OPTION:
                        {
                            XMLTableWizard theWiz = new XMLTableWizard(editor,XMLTableWizard.CREATE);
                            theWiz.show();
                            // it is modal, so dereference now
                            theWiz.dereference();
                            break;
                        }
                    case JOptionPane.CANCEL_OPTION:
                    default:
                        break;
                    }
                }

                editor.setFocusInEditor();  // applet does not return focus to the editor
            }
        }
    }

    private static class InsertTableRowColAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertTableRowColAction() {
            super(INSERT_TR_TD_ACTION);
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            XMLEditorPane editor;
            XMLTableWizard theWiz;
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))){
                //return;
            }else {
                editor = (XMLEditorPane)target;

                theWiz = new XMLTableWizard(editor,XMLTableWizard.INSERT_ROWCOL);
                theWiz.show();
                // it is modal, so dereference now
                theWiz.dereference();

                editor.setFocusInEditor();  // applet does not return focus to the editor
            }
        }
    }

    private static class DeleteTableRowColAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	DeleteTableRowColAction() {
            super(DELETE_TR_TD_ACTION);
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            XMLEditorPane editor;
            XMLTableWizard theWiz;
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))){
                //return;
            }else{
                editor = (XMLEditorPane)target;

                theWiz = new XMLTableWizard(editor,XMLTableWizard.DELETE_ROWCOL);
                theWiz.show();
                // it is modal, so dereference now
                theWiz.dereference();

                editor.setFocusInEditor();  // applet does not return focus to the editor
            }
        }
    }

    private class InsertSpecialAction extends StyledTextAction
    {
    	private static final long serialVersionUID = 1L;
    	InsertSpecialAction() {
            super(INSERT_SPECCHAR_ACTION);
        }

        /**
         * The operation to perform when this action is triggered.
         *
         * @param e the action event
         */
        public void actionPerformed(ActionEvent e)
        {
            XMLEditorPane editor;
            Parser parser;
            JTextComponent target = getTextComponent(e);
            if ((target == null) || (!target.isEditable())
                || (!(target instanceof XMLEditorPane))) {
                //return;
            }else {
                editor = (XMLEditorPane)target;

                parser = getParser();
                if (parser instanceof XMLParserDelegator)
                {
                    javax.swing.DefaultListModel specCharVct = ((XMLParserDelegator)parser).getSupportedEntities();
                    XMLSpecialCharDialog dialog = new XMLSpecialCharDialog(editor, specCharVct);
                    dialog.show();
                    dialog.dereference();
                }
                //else parser is the default parser.. the editor control is getting instantiated
            }
        }
    }

/*
From HTMLEditorKit:
*/
/*    public void insertHTML(HTMLDocument doc, int offset, String html,
               int popDepth, int pushDepth,
               HTML.Tag insertTag) throws BadLocationException, IOException
    {
System.out.println("insertHTML:: offset: "+offset+" html: "+html+" popdepth: "+popDepth+" pushDepth: "+
pushDepth+" tag: "+insertTag);
        Parser p = getParser();
        if (p == null) {
            throw new IOException("Can't load parser");
        }
        if (offset > doc.getLength()) {
            throw new BadLocationException("Invalid location", offset);
        }

        ParserCallback receiver = doc.getReader(offset, popDepth, pushDepth,insertTag);
        Boolean ignoreCharset = (Boolean)doc.getProperty("IgnoreCharsetDirective");
        p.parse(new StringReader(html), receiver, (ignoreCharset == null) ?
                                                    false : ignoreCharset.booleanValue());
        receiver.flush();
    }*/


    // **
    // * InsertHTMLTextAction can be used to insert an arbitrary string of HTML
    // * into an existing HTML document. At least two HTML.Tags need to be
    // * supplied. The first Tag, parentTag, identifies the parent in
    // * the document to add the elements to. The second tag, addTag,
    // * identifies the first tag that should be added to the document as
    // * seen in the HTML string. One important thing to remember, is that
    // * the parser is going to generate all the appropriate tags, even if
    // * they aren't in the HTML string passed in.<p>
    // * For example, lets say you wanted to create an action to insert
    // * a table into the body. The parentTag would be HTML.Tag.BODY,
    // * addTag would be HTML.Tag.TABLE, and the string could be something
    // * like &lt;table&gt;&lt;tr&gt;&lt;td&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;.
    // * <p>There is also an option to supply an alternate parentTag and
    // * addTag. These will be checked for if there is no parentTag at
    // * offset.
    // * /
/*    public static class InsertHTMLTextAction extends HTMLTextAction {
    public InsertHTMLTextAction(String name, String html,
                    HTML.Tag parentTag, HTML.Tag addTag) {
        this(name, html, parentTag, addTag, null, null);
    }

    public InsertHTMLTextAction(String name, String html,
                    HTML.Tag parentTag,
                    HTML.Tag addTag,
                    HTML.Tag alternateParentTag,
                    HTML.Tag alternateAddTag) {
        this(name, html, parentTag, addTag, alternateParentTag,
         alternateAddTag, true);
    }

    InsertHTMLTextAction(String name, String html,
                    HTML.Tag parentTag,
                    HTML.Tag addTag,
                    HTML.Tag alternateParentTag,
                    HTML.Tag alternateAddTag,
                    boolean adjustSelection) {
        super(name);
        this.html = html;
        this.parentTag = parentTag;
        this.addTag = addTag;
        this.alternateParentTag = alternateParentTag;
        this.alternateAddTag = alternateAddTag;
        this.adjustSelection = adjustSelection;
    }

    // **
    // * A cover for HTMLEditorKit.insertHTML. If an exception is
    // * thrown it is wrapped in a RuntimeException and thrown.
    // *
    protected void insertHTML(JEditorPane editor, HTMLDocument doc,
                  int offset, String html, int popDepth,
                  int pushDepth, HTML.Tag addTag) {
        try {
            getHTMLEditorKit(editor).insertHTML(doc, offset, html,
                            popDepth, pushDepth,
                            addTag);
        } catch (IOException ioe) {
        throw new RuntimeException("Unable to insert: " + ioe);
        } catch (BadLocationException ble) {
        throw new RuntimeException("Unable to insert: " + ble);
        }
    }

    // **
    // * This is invoked when inserting at a boundary. It determines
    // * the number of pops, and then the number of pushes that need
    // * to be performed, and then invokes insertHTML.
    // * @since 1.3
    // *
    protected void insertAtBoundary(JEditorPane editor, HTMLDocument doc,
                    int offset, javax.swing.text.Element insertElement,
                    String html, HTML.Tag parentTag,
                    HTML.Tag addTag)
    {
//System.out.println("inserthtmlaction: insertAtBoundary() offset "+offset+" html: "+html+" parentTag: "+
//parentTag+" addTag: "+addTag+" insertElem: "+insertElement);

        // Find the common parent.
        javax.swing.text.Element e;
        javax.swing.text.Element commonParent;
        boolean isFirst = (offset == 0);

        if (offset > 0 || insertElement == null)
        {
            e = doc.getDefaultRootElement();
//System.out.print("inserthtmlaction: insertAtBoundry() defaultRoot: "+e);
            while (e != null && e.getStartOffset() != offset && !e.isLeaf())
            {
                e = e.getElement(e.getElementIndex(offset));
//System.out.print("inserthtmlaction: insertAtBoundry() finding parent: "+e);
            }
            commonParent = (e != null) ? e.getParentElement() : null;
        }
        else
        {
            // If inserting at the origin, the common parent is the
            // insertElement.
            commonParent = insertElement;
        }
//System.out.print("inserthtmlaction: insertAtBoundry() commonParent: "+commonParent);
        if (commonParent != null)
        {
            // Determine how many pops to do.
            int pops = 0;
            int pushes = 0;
            if (isFirst && insertElement != null)
            {
                e = commonParent;
                while (e != null && !e.isLeaf()) {
                    e = e.getElement(e.getElementIndex(offset));
//System.out.print("inserthtmlaction: insertAtBoundry() isFirst && !insertElem get2leaf e: "+e);
                    pops++;
                }
//System.out.println("inserthtmlaction: insertAtBoundry() isFirst && !insertElem get2leaf pops: "+pops);
            }
            else
            {
                e = commonParent;
                offset--;
                while (e != null && !e.isLeaf()) {
                    e = e.getElement(e.getElementIndex(offset));
                    pops++;
                }
//System.out.println("inserthtmlaction: insertAtBoundry() !isFirst, offset-- get2leaf pops: "+pops);

                // And how many pushes
                e = commonParent;
                offset++;
                while (e != null && e != insertElement) {
                    e = e.getElement(e.getElementIndex(offset));
                    pushes++;
                }
//System.out.println("inserthtmlaction: insertAtBoundry() !isFirst, offset get2element pushes: "+pushes);
            }
            pops = Math.max(0, pops - 1);

//System.out.println("Calling insertHtml!!!!");
            // And insert!
            insertHTML(editor, doc, offset, html, pops, pushes, addTag);
        }
    }

    // **
    // * If there is an Element with name <code>tag</code> at
    // * <code>offset</code>, this will invoke either insertAtBoundary
    // * or <code>insertHTML</code>. This returns true if there is
    // * a match, and one of the inserts is invoked.
    // * /
    boolean insertIntoTag(JEditorPane editor, HTMLDocument doc,
                  int offset, HTML.Tag tag, HTML.Tag addTag)
    {
//System.out.println("inserthtmlaction: insertIntoTag() parenttag: "+tag+" offset: "+offset+
//" addtag: "+addTag);

        // Find the deepest element at offset matching parent tag starting at root
        javax.swing.text.Element e = findElementMatchingTag(doc, offset, tag);
//System.out.println("inserthtmlaction: insertIntoTag() elemMatching parentTag: "+e);
        if (e != null && e.getStartOffset() == offset)
        {
//System.out.println("inserthtmlaction: insertIntoTag() inserting at start boundary of parent");
            insertAtBoundary(editor, doc, offset, e, html, tag, addTag);
            return true;
        }
        else if (offset > 0)
        {
    // Depth is number of elements, starting at the deepest leaf at offset-1, needed
    // to get to an element representing parent tag. This will be -1 if no element is found,
    // or 0 if the parent of the leaf at offset-1 represents parent tag.
            int depth = elementCountToTag(doc, offset - 1, tag);
//System.out.println("inserthtmlaction: insertIntoTag() after elementCountTo parentTag() depth "+depth);
            if (depth != -1)
            {
//System.out.println("inserthtmlaction: insertIntoTag() inserting "+html);
                insertHTML(editor, doc, offset, html, depth, 0, addTag);
                return true;
            }
        }
        return false;
    }

    // **
    // * Called after an insertion to adjust the selection.
    // *
    void adjustSelection(JEditorPane pane, HTMLDocument doc,
                 int startOffset, int oldLength) {
        int newLength = doc.getLength();
        if (newLength != oldLength && startOffset < newLength) {
        if (startOffset > 0) {
            String text;
            try {
            text = doc.getText(startOffset - 1, 1);
            } catch (BadLocationException ble) {
            text = null;
            }
            if (text != null && text.length() > 0 &&
            text.charAt(0) == '\n') {
            pane.select(startOffset, startOffset);
            }
            else {
            pane.select(startOffset + 1, startOffset + 1);
            }
        }
        else {
            pane.select(1, 1);
        }
        }
    }

    // **
    // * Inserts the HTML into the document.
    // *
    // * @param ae the event
    // *
    public void actionPerformed(ActionEvent ae) {
        JEditorPane editor = getEditor(ae);
        if (editor != null) {
        HTMLDocument doc = getHTMLDocument(editor);
        int offset = editor.getSelectionStart();
        int length = doc.getLength();
//System.out.println("inserthtmlaction: actionPerf() adjustSelection: "+adjustSelection+" offset: "+offset+
//" length: "+length);
        boolean inserted;
        // Try first choice
        if (!insertIntoTag(editor, doc, offset, parentTag, addTag) &&
            alternateParentTag != null) {
            // Then alternate.
            inserted = insertIntoTag(editor, doc, offset,
                         alternateParentTag,
                         alternateAddTag);
        }
        else {
            inserted = true;
        }
        if (adjustSelection && inserted) {
            adjustSelection(editor, doc, offset, length);
        }
        }
//System.out.println("inserthtmlaction: actionPerf() exiting");
    }

    // HTML to insert.
    protected String html;
    // Tag to check for in the document.
    protected HTML.Tag parentTag;
    // Tag in HTML to start adding tags from.
    protected HTML.Tag addTag;
    // Alternate Tag to check for in the document if parentTag is
    // not found.
    protected HTML.Tag alternateParentTag;
    // Alternate tag in HTML to start adding tags from if parentTag
    // is not found and alternateParentTag is found.
    protected HTML.Tag alternateAddTag;
    // True indicates the selection should be adjusted after an insert.
    boolean adjustSelection;
 }
 */
/*

    // **
    // * An abstract Action providing some convenience methods that may
    // * be useful in inserting HTML into an existing document.
    // * <p>NOTE: None of the convenience methods obtain a lock on the
    // * document. If you have another thread modifying the text these
    // * methods may have inconsistant behavior, or return the wrong thing.
    // * /
    public static abstract class HTMLTextAction extends StyledTextAction {
    public HTMLTextAction(String name) {
        super(name);
    }

    // **
    // * @return HTMLDocument of <code>e</code>.
    // * /
    protected HTMLDocument getHTMLDocument(JEditorPane e) {
        Document d = e.getDocument();
        if (d instanceof HTMLDocument) {
        return (HTMLDocument) d;
        }
        throw new IllegalArgumentException("document must be HTMLDocument");
    }

    // **
    // * @return HTMLEditorKit for <code>e</code>.
    // * /
        protected HTMLEditorKit getHTMLEditorKit(JEditorPane e) {
        EditorKit k = e.getEditorKit();
        if (k instanceof HTMLEditorKit) {
        return (HTMLEditorKit) k;
        }
        throw new IllegalArgumentException("EditorKit must be HTMLEditorKit");
    }

    // **
    // * Returns an array of the Elements that contain <code>offset</code>.
    // * The first elements corresponds to the root.
    // * /
    protected Element[] getElementsAt(HTMLDocument doc, int offset) {
        return getElementsAt(doc.getDefaultRootElement(), offset, 0);
    }

    // **
    // * Recursive method used by getElementsAt.
    // * /
    private Element[] getElementsAt(Element parent, int offset,
                    int depth) {
        if (parent.isLeaf()) {
        Element[] retValue = new Element[depth + 1];
        retValue[depth] = parent;
        return retValue;
        }
        Element[] retValue = getElementsAt(parent.getElement
              (parent.getElementIndex(offset)), offset, depth + 1);
        retValue[depth] = parent;
        return retValue;
    }

    // **
    // * Returns number of elements, starting at the deepest leaf, needed
    // * to get to an element representing <code>tag</code>. This will
    // * return -1 if no elements is found representing <code>tag</code>,
    // * or 0 if the parent of the leaf at <code>offset</code> represents
    // * <code>tag</code>.
    // * /
    protected int elementCountToTag(HTMLDocument doc, int offset,
                    HTML.Tag tag) {
        int depth = -1;
        Element e = doc.getCharacterElement(offset);
        while (e != null && e.getAttributes().getAttribute
           (StyleConstants.NameAttribute) != tag) {
        e = e.getParentElement();
        depth++;
        }
        if (e == null) {
        return -1;
        }
        return depth;
    }

    // **
    // * Returns the deepest element at <code>offset</code> matching
    // * <code>tag</code>.
    // *
    protected Element findElementMatchingTag(HTMLDocument doc, int offset,
                         HTML.Tag tag) {
        Element e = doc.getDefaultRootElement();
        Element lastMatch = null;
        while (e != null) {
        if (e.getAttributes().getAttribute
           (StyleConstants.NameAttribute) == tag) {
            lastMatch = e;
        }
        e = e.getElement(e.getElementIndex(offset));
        }
        return lastMatch;
    }
    }


*/
/*  private static final ViewFactory theFactory = new XMLViewFactory();
    public ViewFactory getViewFactory() { return theFactory; }
    // prevent some tags from getting displayed in the editor
    public static class XMLViewFactory extends HTMLEditorKit.HTMLFactory
    {
        public View create(javax.swing.text.Element elem)
        {
//          Object tag = elem.getAttributes().getAttribute(
//                      StyleConstants.NameAttribute);
View view = super.create(elem);
System.out.print("XMLEditorKit:: Created view "+view+" for elem: "+elem+" parent: "+elem.getParentElement());
            return view;//super.create(elem);
        }
    }  // end XMLViewFactory
*/
}

