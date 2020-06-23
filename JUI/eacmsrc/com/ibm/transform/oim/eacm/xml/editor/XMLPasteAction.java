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
import java.awt.event.*;

/******************************************************************************
* This will paste at any supported level.  It will go up the hierarchy at the caret
* position until an element is found that will contain the first tag of the html, if
* other tags are part of the paste, they are not checked at this time.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLPasteAction.java,v $
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
class XMLPasteAction extends HTMLEditorKit.InsertHTMLTextAction
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";

    private XMLEditorPane editor;
    // table used to find tag object for tag name
    // top level structure tags for pasting only
    private static final Hashtable TAG_TBL;
    static {
        TAG_TBL = new Hashtable();
        TAG_TBL.put("ol",HTML.Tag.OL);
        TAG_TBL.put("ul",HTML.Tag.UL);
        TAG_TBL.put("p",HTML.Tag.P);
        TAG_TBL.put("pre",HTML.Tag.PRE);
    }

    XMLPasteAction(String html, XMLEditorPane editorx)
    {
        super("XMLPasteAction", html, HTML.Tag.BODY, null);
        this.editor = editorx;
    }

    // this is used to make sure the html to be pasted is valid structure
    // the parser can create structure that isn't really correct like <ol>text</ol>
    void verifyXML()
    {
        XMLDocument doc = (XMLDocument)getHTMLDocument(editor);
        int offset, endPos;
        javax.swing.text.Element paragraph;
        doc.setVerifyXMLOnly(true);
        offset = editor.getSelectionStart();
        endPos = Math.min(editor.getSelectionEnd(),doc.getLength());
        paragraph = getParagraphElement(editor, offset);

        try{
            int origParagraphEnd = paragraph.getEndOffset();

            // if at start.. use insertBeforeStart()
            if (offset == paragraph.getStartOffset())
            {
                // Insert the HTML specified as string before the start of the given element.
                doc.insertBeforeStart(paragraph,html);
            }
            else { // if at end.. use insertAfterEnd()
                if (endPos == origParagraphEnd ||   // at end
                    (endPos+1 == origParagraphEnd)) // or before terminating new line
                {
                    // Insert the HTML specified as a string after the end of the given element.
                    doc.insertAfterEnd(paragraph,html);
                    // if this is done at the end of the document, an extra paragraph tag is added
                    // at the end.. the endPos > length when insertAtEnd() runs
                }
                else    // split the current element
                {
                    super.actionPerformed(null);
                }
            }
        }catch(Exception ble) {
            System.err.println("exc "+ble);
            ble.printStackTrace(System.err);}
        finally{
            doc.setVerifyXMLOnly(false);
        }
    }

    /**
     * get the element to use as insert point
     * @param editorx
     * @param offset
     * @return
     */
    protected javax.swing.text.Element getParagraphElement(JEditorPane editorx, int offset)
    {
        boolean found=false;
        javax.swing.text.Element parent;
        HTMLDocument doc = getHTMLDocument(editorx);
        javax.swing.text.Element paragraph = doc.getParagraphElement(offset);

        // get first tag from html to be pasted
        String tags[] = XMLGenerator.getStructureTags(html);
        String tagName = tags[0];
//System.err.println("paste tagname "+tagName);
        addTag=(HTML.Tag)TAG_TBL.get(tagName);

        parent = paragraph.getParentElement();
        outerloop:while(!parent.getName().equals(HTML.Tag.BODY.toString()))
        {
            // get tag from paragraph, find out if it can have the html as content
            HTML.Tag curTag = (HTML.Tag)paragraph.getAttributes().getAttribute(StyleConstants.NameAttribute);
//System.err.println("paste curtag "+curTag);

            Vector contentsVct = ((XMLEditorKit)(editor.getEditorKit())).getContentElements(curTag.toString());
            // check nested vector of elements
            for (int i=0;i<contentsVct.size(); i++)
            {
                javax.swing.text.html.parser.Element nestedElem =
                    (javax.swing.text.html.parser.Element)contentsVct.elementAt(i);
                if (nestedElem.getName().equals(tagName))
                {
                    // current tag will support inserted html
//System.err.println("curtag will support tagname");
                    parentTag = curTag;

                    // make sure all tags can be inserted into this parent, if not, use body
                    if (parentTag!=HTML.Tag.BODY)
                    {
                        // check each tag for being valid content
                        Vector parentContentsVct = ((XMLEditorKit)(editor.getEditorKit())).getContentElements(parentTag.toString());
                        // check nested vector of elements
                        tagloop:for (int x=0; x<tags.length; x++)
                        {
                            javax.swing.text.Element parent2;
                            for (int ii=0;ii<parentContentsVct.size(); ii++)
                            {
                                javax.swing.text.html.parser.Element nestedElem2 =
                                (javax.swing.text.html.parser.Element)parentContentsVct.elementAt(ii);
                                if (nestedElem2.getName().equals(tags[x]))
                                {
                                    // current tag will support inserted html
                                    continue tagloop;
                                }
                            }
                            // move up to body parent
                            parent2 = paragraph;
                            while(!parent2.getName().equals(HTML.Tag.HTML.toString()))
                            {
                                paragraph = parent2;  // find top-level parent
                                parent2 = parent2.getParentElement();
                            }
                            parentTag = HTML.Tag.BODY;
                            break;
                        }
                    }

                    //return paragraph;
                    found =true;
                    break outerloop;
                }
            }

            paragraph = parent;  // find top-level parent
            parent = parent.getParentElement();
        }

        // set parent tag to paragraph's parent
        if (!found) {
            parentTag = (HTML.Tag)parent.getAttributes().getAttribute(StyleConstants.NameAttribute);
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
        HTMLDocument doc = getHTMLDocument(editor);
        int offset = editor.getSelectionStart();
        int endPos = Math.min(editor.getSelectionEnd(),doc.getLength());
        javax.swing.text.Element paragraph = getParagraphElement(editor, offset);

//System.err.println("pasting html: "+html);
//System.err.print("pasting into addtag: "+addTag+" parenttag "+parentTag+" into paragraph "+paragraph);
        try{
// does this still work.. ???
            int origParagraphEnd;
            // if paragraph is a table, it must not be split v111
            if (paragraph.getName().equals(HTML.Tag.TABLE.toString()))
            {
                int trId = paragraph.getElementIndex(offset);
                if (trId >= paragraph.getElementCount()/2) // halfway thru so paste after table
                {
                    // halfway through cells?
                    int tdId = paragraph.getElement(trId).getElementIndex(offset);
                    if (tdId >= paragraph.getElement(trId).getElementCount()/2){ // halfway thru so paste after table
                        endPos = paragraph.getEndOffset();
                    }
                    else{
                        offset = paragraph.getStartOffset();
                    }
                }
                else {
                    offset = paragraph.getStartOffset();
                }
            }

            origParagraphEnd = paragraph.getEndOffset();
            editor.getUndoEditMgr().setStartReplaceEdit("addition");

            if (parentTag==HTML.Tag.LI)  // inserting into an li tag
            {
                boolean done = false;
                boolean extraStruct;
                int prevlen,curlen;
                // if at start.. use insertBeforeStart()
                if (offset == paragraph.getStartOffset())
                {
                    if (addTag!=HTML.Tag.P ||
                        addTag!=HTML.Tag.PRE)
                    {
                        extraStruct = doc.getText(endPos, 1).equals(XMLEditor.NEWLINE_STR);
                        // force a p-implied before pasted list
                        doc.insertString(offset, " ",
                                ((StyledEditorKit)editor.getEditorKit()).getInputAttributes());

                        // move after inserted space
                        editor.setSelectionStart(offset+1);
                        editor.setSelectionEnd(endPos+1);

                        prevlen = doc.getLength();
                        super.actionPerformed(ae);
                        curlen = doc.getLength();

                        doc.remove(offset,1);

                        // if inserted before a new line, remove the extra structure created by split
                        if (extraStruct){
                            doc.remove(offset+(curlen-prevlen)-1,1);
                        }

                        //editor.getUndoEditMgr().setEndReplaceEdit();
                        //return;
                        done = true;
                    }
                }
                if(!done){
                    extraStruct = doc.getText(endPos, 1).equals(XMLEditor.NEWLINE_STR);

                    prevlen = doc.getLength();
                    // split the current element
                    super.actionPerformed(ae);
                    curlen = doc.getLength();
                    // if inserted before a new line, remove the extra structure created by split
                    if (extraStruct){
                        doc.remove(offset+(curlen-prevlen)-1,1);
                    }
                }
            }
            else // not inserting into an LI tag
            {
                // if at start.. use insertBeforeStart()
                if (offset == paragraph.getStartOffset())
                {
                    // Insert the HTML specified as string before the start of the given element.
                    doc.insertBeforeStart(paragraph,html);
                    editor.setCaretPosition(offset);  // move cursor to insert location
                }
                else  // if at end.. use insertAfterEnd()
                    if (endPos == origParagraphEnd ||   // at end
                        (endPos+1 == origParagraphEnd)) // or before terminating new line
                    {
                        // Insert the HTML specified as a string after the end of the given element.
                        doc.insertAfterEnd(paragraph,html);
                        // if this is done at the end of the document, an extra paragraph tag is added
                        // at the end.. the endPos > length when insertAtEnd() runs

                        editor.setCaretPosition(offset+1);  // move cursor to insert location
                    }
                    else    // split the current element
                    {
                        super.actionPerformed(ae);
                    }
            }
        }catch(Exception ble) {
            System.err.println("exc "+ble);
            ble.printStackTrace(System.err);
        }
        editor.getUndoEditMgr().setEndReplaceEdit();
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
}
