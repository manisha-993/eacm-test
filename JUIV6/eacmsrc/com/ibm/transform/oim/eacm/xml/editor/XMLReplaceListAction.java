// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.text.html.*;
import java.awt.event.*;
import java.util.Vector;

/******************************************************************************
* This is used to manipulate and create lists. The entire list item is affected, not part of it.
* If some part of every list item is selected, the entire list is affected.
* This action does not insert any html.  It modifies the structure by changing the name attribute
* and reassigning children.  It can do this because there is no modification of new lines or
* the underlying data model, only the element structure is adjusted.
*
* Nested p can not be converted into nested lists.  There is a problem with the required
* p-implied before the list.  A new line would have to be added and that involves modifying the
* data model, not just structure.  The user can create a nested list and copy the text into
* each nested list item, then delete the nested paragraph.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLReplaceListAction.java,v $
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.3  2008/09/08 12:49:46  wendy
// LS TA Iris IDL - requires nested lists now
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
class XMLReplaceListAction extends HTMLEditorKit.InsertHTMLTextAction
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";

// nested structure todo:
// copy of subset of structure for paste.. done but major testing needed with write of doc
// check web and catalog preview output.. probably doesn't handle nested lists properly
// if anything can be nested in a table, more work is needed

    XMLReplaceListAction(String name, HTML.Tag addTag)
    {
        super(name, "<"+addTag+"><li></li></"+addTag+">", null, addTag);
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
        javax.swing.text.Element paragraph = doc.getParagraphElement(offset);

        javax.swing.text.Element parent = paragraph;
        if(parent.getName().equals(HTML.Tag.IMPLIED.toString())||
            parent.getName().equals(HTML.Tag.P.toString())) // look for nested p
        {
            parent = parent.getParentElement();  // get <li>?
        }

        if (parent.getName().equals(HTML.Tag.BODY.toString())){// too far up hierarchy
            parent = paragraph;  // restore it
        }

        // if ol or ul is pressed in any element, the entire element will be converted to
        // a list item.. unless the element is already a list then it is converted to a p
        offset = parent.getStartOffset();

//System.err.print("xmllist: getstartoffset() selStart: "+editor.getSelectionStart()+" offset: "+offset+" parent: "+parent);
        return offset;
    }

    /*******************************
     * get end offset
     * @param editor
     * @return
     */
    protected int getEndOffset(XMLEditorPane editor)
    {
        HTMLDocument doc = getHTMLDocument(editor);
        int endPos = Math.min(editor.getSelectionEnd(),doc.getLength());
        javax.swing.text.Element paragraph = doc.getParagraphElement(endPos);
        javax.swing.text.Element parent = paragraph;
        if(parent.getName().equals(HTML.Tag.IMPLIED.toString())||
            parent.getName().equals(HTML.Tag.P.toString())) // look for nested p
        {
            parent = parent.getParentElement();  // get <li>?
        }

        if (parent.getName().equals(HTML.Tag.BODY.toString())){// too far up hierarchy
            parent = paragraph;  // restore it
        }

        // if ol or ul is pressed in any element, the entire element will be converted to
        // a list item.. unless the element is already a list
        endPos = parent.getEndOffset();

        // at this point it is possible that the element's end offset exceeds the document length
//System.err.print("xmllist: getendoffset() endpos: "+endPos+" parent: "+parent);
        return endPos;
    }

    /****************************************
     * get the element to use for replacement
     * @param editor
     * @param offset
     * @return
     */
    protected javax.swing.text.Element getParagraphElement(JEditorPane editor, int offset)
    {
        boolean found=false;
        javax.swing.text.Element parent;
        HTMLDocument doc = getHTMLDocument(editor);
        javax.swing.text.Element paragraph = doc.getParagraphElement(offset);

        if(paragraph.getName().equals(HTML.Tag.IMPLIED.toString())||
            paragraph.getName().equals(HTML.Tag.P.toString())) // look for nested p
        {
            if (paragraph.getParentElement().getName().equals(HTML.Tag.LI.toString())) {
                //return paragraph.getParentElement().getParentElement();
                paragraph = paragraph.getParentElement().getParentElement();
                found=true;
            }// get ol or ul
        }

        if(!found){
            parent = paragraph;
            while(!parent.getName().equals(HTML.Tag.BODY.toString()))
            {
                paragraph = parent;  // find top-level parent
                parent = parent.getParentElement();
            }
        }

        return paragraph;
    }

    // xmleditor will query this to see if action should be enabled or not
    boolean isValid(JEditorPane editor)
    {
        //XMLDocument doc = (XMLDocument)getHTMLDocument(editor);
        // this will always find the entire element, selection of parts is ignored
        int offset = getStartOffset((XMLEditorPane)editor);
        int endPos = getEndOffset((XMLEditorPane)editor);

        javax.swing.text.Element paragraph =  getParagraphElement(editor, offset);
        javax.swing.text.Element parent =  paragraph.getParentElement();
       
        boolean validpos = (!(parent.getEndOffset()<endPos));
        //return (!(parent.getEndOffset()<endPos)); //LS TA Iris IDL - requires nested lists now
        if (validpos){
        	// problem with converting a list to a paragraph.. that is making it a paragraph inside a nested
        	// list, invalidates dtd -  check if p are allowed in parent 
        	if (offset >= paragraph.getStartOffset() &&  // selection is in one list element
                endPos <= paragraph.getEndOffset() &&    // but could be a just a subset of list items
         	    addTag.toString().equals(paragraph.getName()))
        	{
        		validpos=false;
        		// allow the list to be turned off if the parent can have a p tag
        		if (parent.getName().equals("li")){
        			paragraph = parent.getParentElement();
        		}else{
        			paragraph = parent;
        		}
        		// get the content that is valid for this tag - can it have a paragraph
        		Vector elemVct = ((XMLEditorKit)editor.getEditorKit()).getContentElements(paragraph.getName());

        		// verify the content for this element supports this attribute action
        		// that is, that bold can be a value for text in this element
        		for (int ii=0; ii<elemVct.size(); ii++) {
        			javax.swing.text.html.parser.Element child =
        				(javax.swing.text.html.parser.Element)elemVct.elementAt(ii);
        			if (child.name.equals("p")){
        				validpos=true;
        				break;
        			}
        		}
        	}	           
        }
        return validpos; 
    }

    private boolean canConvert(XMLDocument doc, int offset, int endPos)
    {
        boolean ok = true;
        int elemEndPos;
        // check each element in the selection, tables and pre can not be converted
        javax.swing.text.Element elem = doc.getParagraphElement(offset);
        // if it is p-implied don't save it, just get parent
        if (elem.getName().equals(HTML.Tag.IMPLIED.toString())){
            elem = elem.getParentElement();
        }

        if (elem.getName().equals(HTML.Tag.TD.toString()) ||
            elem.getName().equals(HTML.Tag.TH.toString()) ||
            elem.getName().equals(HTML.Tag.PRE.toString())) {
            //return false;
            ok=false;
        }else{
            elemEndPos = elem.getEndOffset();
            while(elemEndPos<endPos)
            {
                elem = doc.getParagraphElement(elemEndPos);
                // if it is p-implied don't save it, just get parent
                if (elem.getName().equals(HTML.Tag.IMPLIED.toString())) {
                    elem = elem.getParentElement();
                }
                if (elem.getName().equals(HTML.Tag.TD.toString()) ||
                    elem.getName().equals(HTML.Tag.TH.toString()) ||
                    elem.getName().equals(HTML.Tag.PRE.toString())) {
                    //return false;
                    ok=false;
                    break;
                }

                elemEndPos = elem.getEndOffset();
            }
        }

        return ok;//true;
    }

    private void createList(JEditorPane editor, XMLDocument doc, int offset, int endPos)
    {
        try{
            boolean insertBefore;
            javax.swing.text.Element paragraph;
            StringBuffer sb;
            // get formatted text without structure
            // the array will have one element for each
            // p-implied or p structure's text (pre will be combined into one element)
            XMLWriter w = new XMLWriter(doc, offset,endPos-offset);
            StringBuffer[] contentArray = w.writeFormattedText();
            //javax.swing.text.Element endElement = getParagraphElement(editor, endPos-1);
//System.err.print("endelem "+endElement);
            javax.swing.text.Element startElement = getParagraphElement(editor, offset);
//System.err.print("startelem "+startElement);

            // remove selected text
            // if user selects list items at end of a list and some other structure
            // the list item is not completely deleted
            if ((startElement.getName().equals(HTML.Tag.OL.toString()) ||
                    startElement.getName().equals(HTML.Tag.UL.toString())) &&
                startElement.getStartOffset()!= offset) // entire elem will not be deleted
            {
                offset--;
//System.err.println("******** adjust offset ");
            }

            insertBefore = (startElement.getStartOffset()==offset);
//System.err.println("createlist calling remove offset "+offset+" len: "+(endPos-offset));
            doc.remove(offset, endPos-offset);

            paragraph = getParagraphElement(editor, offset);

            // build html string
            sb = new StringBuffer("<"+addTag+">");
            for (int c=0; c<contentArray.length; c++){
                sb.append("<li>"+contentArray[c]+"</li>");
            }

            sb.append("</"+addTag+">");

            // Insert the HTML specified as string before the start of the given element.
            if (insertBefore) {
                doc.insertBeforeStart(paragraph,sb.toString());
            }
            else {
            // Insert the HTML specified as a string after the end of the given element.
                doc.insertAfterEnd(paragraph,sb.toString());
            }

        }catch(Exception ble) {
            System.err.println("exc "+ble);
            ble.printStackTrace(System.err);}
    }

    /**
     * Inserts the HTML into the document.
     *
     * @param ae the event
     */
    public void actionPerformed(ActionEvent ae)
    {
        XMLDocument doc;
        int offset,endPos;
        JEditorPane editor = getEditor(ae);
        javax.swing.text.Element paragraph;
        if (editor!=null) {
            doc = (XMLDocument)getHTMLDocument(editor);
            // this will always find the entire element, selection of parts is ignored
            offset = getStartOffset((XMLEditorPane)editor);
            endPos = getEndOffset((XMLEditorPane)editor);

            paragraph = getParagraphElement(editor, offset);

    //System.err.println("\nreplacexmllist: offset: "+offset+" endPos: "+endPos);
    //System.err.print("paragraph: "+paragraph);
            try{
                String elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(addTag.toString());
                javax.swing.text.Element parent;
                int endId;

                if (offset >= paragraph.getStartOffset() &&  // selection is in one list element
                    endPos <= paragraph.getEndOffset() &&    // but could be a just a subset of list items
                    (paragraph.getName().equals(HTML.Tag.UL.toString())||
                        paragraph.getName().equals(HTML.Tag.OL.toString())))
                {
                    // if action cmd matches the current paragraph name, the list is converted to p(s)
                    if (paragraph.getName().equals(ae.getActionCommand()))
                    {
                        elemInfo = (String)XMLDocument.TAG_NAME_TBL.get(HTML.Tag.P.toString());
                        ((XMLEditorPane)editor).getUndoEditMgr().setStartReplaceEdit(elemInfo);
                        doc.changeListStructure(paragraph, offset, endPos, HTML.Tag.P);
                        editor.setCaretPosition(offset);  // move cursor to insert location
                    }
                    else // change list to new list type
                    {
                        ((XMLEditorPane)editor).getUndoEditMgr().setStartReplaceEdit(elemInfo);
                        doc.changeListStructure(paragraph, offset, endPos, addTag);
                    }

                    // make sure the actions reflect the latest changes
                    ((XMLEditorPane)editor).ignoreCaret(false);
                    ((XMLEditorPane)editor).getUndoEditMgr().setEndReplaceEdit();  // undo as one action

                    //return;
                }else {
                    ((XMLEditorPane)editor).getUndoEditMgr().setStartReplaceEdit(elemInfo);
                    parent = paragraph.getParentElement();

                    // converting p(s) to list or combining lists into one list
                    endId = parent.getElementIndex(endPos-1); // endpos is set to end of selected elem, -1 to get it
                    // if end of doc, add a new p before altering structure
                    if (parent.getElement(endId).getEndOffset()>doc.getLength())
                    {
                        editor.setCaretPosition(doc.getLength());
                        editor.replaceSelection(XMLEditor.NEWLINE_STR);
                    }

                    // can't just convert structure if selection has pre or table in it
                    if (canConvert(doc, offset, endPos)){
                        doc.createListStructure(parent, offset, endPos, addTag);
                    }
                    else
                    {
        //System.err.println("table or pre found.. can't convert directly addtag: "+addTag);
                        createList(editor, doc, offset, endPos);
                    }

                    editor.setCaretPosition(offset);  // move cursor to first elem location

                    // make sure the actions reflect the latest changes
                    ((XMLEditorPane)editor).ignoreCaret(false);

                    ((XMLEditorPane)editor).getUndoEditMgr().setEndReplaceEdit();  // undo as one action
                }
            }catch(Exception ble) {
                System.err.println("exc "+ble);
                ble.printStackTrace(System.err);}
        }
    }
}
