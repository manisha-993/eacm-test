// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.*;
import javax.swing.text.*;
import com.wallstreetwise.app.jspell.gui.*;

/******************************************************************************
* This is used to integrate JSpell with the WYSIWYG editor. It is required because
* the JSpell replaces the entire contents of the document.
* This is requires the construction of a new Document and any undo/redo changes
* are lost.
* Also JSpell does not make it's changes on the event thread.. this causes some
* intermittent errors in repainting the cursor and selected text.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLJSpellWrapper.java,v $
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
class XMLJSpellWrapper extends JSpellSwingTextComponentWrapper
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private XMLEditorPane editor;
    private String theData =null;

    void dereference()
    {
        editor = null;
        theData = null;
    }

    XMLJSpellWrapper(JTextComponent text)
    {
        super(null); // don't pass component in.. no way to dereference it later
        editor =(XMLEditorPane)text;
    }

    /*********************************************************************
    * this is only called once at the start of each spell check session
    * @return String
    */
    public String getText()
    {
        // can't go to the document because tags will not be emitted and
        // the replace is done with all of the text.  getText() will go through
        // the XMLWriter.
        // hang on to the string here..

        // convert all <br /> to <br> because the parser doesn't handle them properly
        // when reloaded (after spelling correction the entire contents is replaced)
        // xmlwriter will save any <br> as <br />\n (but no added new line in <pre>)
        theData = XMLGenerator.replaceEmptyXML(editor.getText());

        return theData;
    }

    /*********************************************************************
    * jspell replaces the entire contents.. not just the misspelled word!!
    * it holds onto the entire text and replaces the entire thing for each word.
    * this means the XMLdocument must be replaced so previous undo/redo is no longer valid
    * @param string String
    */
    public void setText(String string)
    {
        // indicate the page is loading
        editor.setWaitCursors();
        // document will be replaced so suspend the undo mgr
        editor.getUndoEditMgr().suspend();
        // replace string held onto here..
        theData = string;
        // leave <br> as <br> because they are going directly into the control
        // note:  jspell replaces the entire contents.. not just the misspelled word!!
        // can not use replaceSelection because jspell supports 'replace All'
        editor.setText(string);
        // notify undo mgr that something has changed
        editor.getUndoEditMgr().changesMade();
        // document was replaced so restart the undo mgr
        editor.getUndoEditMgr().restart();

        editor.restoreCursors();
    }

    /*********************************************************************
    * deselect
    */
    public void deselect()
    {
        // set on the event dispatch thread to prevent leftover selection and cursor paint
        if (SwingUtilities.isEventDispatchThread())
        {
            editor.select(0,0);
            // this shows caret blinking but if user presses 'stop' the other way hides caret
            editor.getCaret().setSelectionVisible(false);
            editor.repaint();  // jspell is leaving image of dialog sometimes
//          editor.getCaret().setVisible(false);
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            editor.select(0,0);
                            editor.getCaret().setSelectionVisible(false);
                            editor.repaint();  // jspell is leaving image of dialog sometimes
//                          editor.getCaret().setVisible(false);
                        }
                });
            }catch(Exception e){
                System.out.println(e.getMessage()); // jtest req
            }
        }
    }
    /*********************************************************************
    * select
    * @param begin int
    * @param end int
    */
    public void select(final int begin, final int end)
    {
        final int adjBegin = getAdjustedPosition(begin, end);

        // set on the event dispatch thread to prevent leftover selection and cursor paint
        if (SwingUtilities.isEventDispatchThread())
        {
            editor.select(adjBegin,adjBegin+(end-begin));
//          editor.getCaret().setSelectionVisible(true);
            editor.getCaret().setVisible(true);
        }
        else
        {
            try{
                SwingUtilities.invokeAndWait(new Runnable() {
                        public void run() {
                            editor.select(adjBegin,adjBegin+(end-begin));
    //                      editor.getCaret().setSelectionVisible(true);
                            editor.getCaret().setVisible(true);
                        }
                });
            }catch(Exception e){
                System.out.println(e.getMessage());  // jtest req
            }
        }
    }

    private int getAdjustedPosition(int position, int end)
    {
        StringBuffer sb;
        int startTagId;
        boolean inPre = false;

        // make sure spell check isn't holding onto extra whitespace.. selection will be off even
        // though replacement will be in the proper place (because entire document is replaced)
//        String badWord = theData.substring(position,end);
        String badWord = XMLGenerator.getSubString(theData,position,end);
        String curData = XMLGenerator.replaceEmptyXML(editor.getText());
        /* jtest flags indexOf() but it is ok
        Disallows using 'String.indexOf ()' with 'String.substring ()'.
        Note: If the algorithm implemented by the method is not String parsing, then
        StringTokenizer cannot be used and this rule's error message should be ignored.
        */
        int badID = curData.indexOf(badWord,position-badWord.length()); //look near the position
        if (badID==-1){
            badID = curData.indexOf(badWord);  // find first occurence
        }
        if (badID!=position)
        {
            int badID2 = curData.lastIndexOf(badWord);
            if (badID2!=-1)
            {
                // find badword closest to jspell location
                if (Math.abs(position-badID2)<Math.abs(position-badID)){
                    badID=badID2;
                }
            }
        }

        // consecutive white space will be a problem.. because it will be rendered without extra
        // spaces.. this can only happen if space is inside a tag and outside..
//      StringBuffer sb = new StringBuffer(theData.substring(0,position));
        sb = new StringBuffer(//curData.substring(0,badID));
            XMLGenerator.getSubString(curData, 0,badID));
        // remove tags
        startTagId = sb.toString().indexOf("<");
        //if XMLWriter does not output a new line after <br> it will have to be accounted for here
        // must do so if <br> is inside a <pre> (new line won't be added) v11
        // when </pre> is encountered, a new line will not precede it because pre content can not
        // be altered
        while(startTagId!=-1)
        {
            int endTagId = sb.toString().indexOf(">",startTagId);
//            if (sb.substring(startTagId,endTagId+1).equals("<pre>")){
            if (XMLGenerator.getSubString(sb,startTagId,endTagId+1).equals("<pre>")){
                inPre = true;
            }

            // when </pre> is encountered, a new line will not precede it because pre content can not
            // be altered leave one char to account for this
//            if (sb.substring(startTagId,endTagId+1).equals("</pre>"))
            if (XMLGenerator.getSubString(sb,startTagId,endTagId+1).equals("</pre>"))
            {
                inPre = false;
                sb.delete(startTagId,endTagId);
            }
            else  {  // if <br> is inside a <pre> leave 1 char (new line won't be added) v11
//                if (inPre && sb.substring(startTagId,endTagId+1).equals("<br>")) {
                if (inPre && XMLGenerator.getSubString(sb,startTagId,endTagId+1).equals("<br>")) {
                    sb.delete(startTagId,endTagId);
                }
                else {
                    sb.delete(startTagId,endTagId+1);
                }
            }

            startTagId = sb.toString().indexOf("<");
        }

        // remove character references such as &nbsp;
        startTagId = sb.toString().indexOf("&");
        while(startTagId!=-1)
        {
            int endTagId = sb.toString().indexOf(";",startTagId);
            sb.delete(startTagId,endTagId);  // leave one to account for replacement character
            startTagId = sb.toString().indexOf("&");
        }

        return sb.length();
    }
}
