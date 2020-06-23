// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.CSS;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.undo.UndoableEdit;


/******************************************************************************
* This is derived to provide a modified HTMLDocument class.  It is required to
* modify the join behavior of the ElementBuffer used to manage structure.  The
* base implementation did not merge most structure types causing the view to
* be built in error.. allowing things such as caret on one line, and backspace
* removing characters on the previous line.
*
* HTMLReader must be subclassed and basically rewritten to support dynamic DTDs.
* Insertion of HTML has been modified to work properly.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLDocument.java,v $
// Revision 1.2  2013/07/18 18:43:51  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.2  2008/01/30 16:27:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/04/18 19:47:47  wendy
// Reorganized JUI module
//
// Revision 1.5  2006/01/25 18:59:03  wendy
// AHE copyright
//
// Revision 1.4  2005/12/16 15:52:13  wendy
// Changes for DQA for td, th tags
//
// Revision 1.3  2005/10/12 12:48:56  wendy
// Conform to new jtest configuration
//
// Revision 1.2  2005/09/20 16:01:59  tony
// CR092005410
// Ability to add middleware location on the fly.
//
// Revision 1.1.1.1  2005/09/09 20:39:16  tony
// This is the initial load of OPICM
//
//
public class XMLDocument extends HTMLDocument implements XMLEditorGlobals
{
	private static final long serialVersionUID = 1L;
	/** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    private XMLUndoEditMgr undoEditMgr;
    private boolean verifyXMLOnly = false;
    private static final int TOOL_TIP_LEN=20;

    void setVerifyXMLOnly(boolean b) {verifyXMLOnly=b;}

    void setUndoEditMgr( XMLUndoEditMgr m) { undoEditMgr=m;}

    void dereference()
    {
        EventListener listeners[] = null;
        removeUndoableEditListener(undoEditMgr);
        undoEditMgr = null;

        listeners = getListeners(XMLErrorListener.class);
        for(int i=0; i<listeners.length; i++)
        {
            removeXMLErrorListener((XMLErrorListener)listeners[i]);
            listeners[i]=null;
        }

        buffer = null;
        putProperty("IgnoreCharsetDirective", null);
        putProperty(DefaultEditorKit.EndOfLineStringProperty,null);
    }

    static final Hashtable<String, String> TAG_NAME_TBL;  // used for status bar messages
    static {
        TAG_NAME_TBL = new Hashtable<String, String>();
        TAG_NAME_TBL.put("p","Paragraph");
        TAG_NAME_TBL.put("pre","Preformatted Text"); //v11
        TAG_NAME_TBL.put("ol","Ordered List");
        TAG_NAME_TBL.put("ul","Unordered List");
        TAG_NAME_TBL.put("li","List Item");
        TAG_NAME_TBL.put("br","Break");
        TAG_NAME_TBL.put("h1","Heading 1");
        TAG_NAME_TBL.put("h2","Heading 2");
        TAG_NAME_TBL.put("h3","Heading 3");
        TAG_NAME_TBL.put("h4","Heading 4");
        TAG_NAME_TBL.put("h5","Heading 5");
        TAG_NAME_TBL.put("h6","Heading 6");
        TAG_NAME_TBL.put("table","Table");
        TAG_NAME_TBL.put("tr","Table Row");
        TAG_NAME_TBL.put("td","Table Column"); // can't work at cell level, insert an entire column
        TAG_NAME_TBL.put("th","Table Head");
    }

    //
    // Provided for inner class access.
    //

   /*
     * state defines whether the document is a frame document
     * or not.
     */
//    private boolean preservesUnknownTags = true;

    /**
     * The parser that is used when inserting html into the existing
     * document.
     */
    //private HTMLEditorKit.Parser parser;

    /**
     * Used for inserts when a null AttributeSet is supplied.
     */
    private static AttributeSet contentAttributeSet;

    private static char[] NEWLINE;
    private static final String NEWLINE_STR;

    static {
        contentAttributeSet = new SimpleAttributeSet();
        ((MutableAttributeSet)contentAttributeSet).addAttribute(StyleConstants.NameAttribute,HTML.Tag.CONTENT);
        NEWLINE = new char[1];
        NEWLINE[0] = '\n';
        NEWLINE_STR = new String(NEWLINE);
    }

// ** This section is used to determine caret location with respect to elements for enabling actions

    // get the tag controlling this location for determining valid tag or element attributes
    // h1-6 can not allow bold, others must check if publish is allowed
    // assumption:: any structure insertion is valid because it will split the current structure!!
    // so only need to verify 'attribute' actions, these include text actions like bold
    Element[] getAttributeControllingTags(int startPos, int endPos)
    {
        Vector<Element> tagVct = new Vector<Element>();
        Element array[] = null;
        // get corresponding structure element from the document for this location
        // find parents.. ignoring <content> and <p-implied>, character element will be content or br
        Element elem = getParagraphElement(startPos);
        // if it is p-implied don't save it, just get parent
        if (elem.getName().equals(HTML.Tag.IMPLIED.toString())){
            elem = elem.getParentElement(); }

        tagVct.addElement(elem);

        if (startPos!=endPos)
        {
            int elemEndPos = elem.getEndOffset();
            while(elemEndPos<endPos)  // this avoids selecting paragraph following main selection(cursor is on following line)
            {
                elem = getParagraphElement(elemEndPos);
                // if it is p-implied don't save it, just get parent
                if (elem.getName().equals(HTML.Tag.IMPLIED.toString())) {
                    elem = elem.getParentElement();}
                tagVct.addElement(elem);
                elemEndPos = elem.getEndOffset();
            }
        }

        array= new Element[tagVct.size()];
        tagVct.copyInto(array);
        tagVct.clear();
        tagVct=null;
        return array;
    }

    // get the tag controlling this location for determining valid publish tag attributes
    Element[] getPublishAttributeControllingTags(int startPos, int endPos)
    {
        Vector<Element> tagVct = new Vector<Element>();
        boolean inOneElem = false;
        Element array[] = null;
        // get corresponding structure element from the document for this location
        // find parents.. ignoring <content> and <p-implied>, character element will be content or br
        Element elem = getParagraphElement(startPos);
        // if it is p-implied don't save it, just get parent
        if (elem.getName().equals(HTML.Tag.IMPLIED.toString())) {
            elem = elem.getParentElement(); }

        tagVct.addElement(elem);
        inOneElem = elem.getStartOffset()<=startPos && elem.getEndOffset()>=endPos;
        if (startPos!=endPos)
        {
            int elemEndPos = elem.getEndOffset();
            while(elemEndPos<endPos)  // this avoids selecting paragraph following main selection(cursor is on following line)
            {
                elem = getParagraphElement(elemEndPos);
                // if it is p-implied don't save it, just get parent
                if (elem.getName().equals(HTML.Tag.IMPLIED.toString())) {
                    elem = elem.getParentElement();}

                tagVct.addElement(elem);
                elemEndPos = elem.getEndOffset();
            }

            // now see if all children of a particular parent have been selected
            if (!inOneElem) { // allow list item to be set if only child of a list
                tagVct = getSelectionTags(tagVct); }
        }

        array= new Element[tagVct.size()];
        tagVct.copyInto(array);
        tagVct.clear();
        tagVct=null;
        return array;
    }

    // if all of the children of a particular element are selected, the parent element is selected
    // this is used to set publish attributes
    private Vector<Element> getSelectionTags(Vector<Element> tagVct)
    {
        Hashtable<Element, Integer> parentTbl = new Hashtable<Element, Integer>();
        Vector<Element> selTagVct = new Vector<Element>();
        Vector<Element> removeTagVct = new Vector<Element>();
        Integer count =null;
        for (int i=0;i<tagVct.size(); i++)
        {
            Element parentElem= null;
            Element elem=(Element)tagVct.elementAt(i);
            if (elem.getName().equals("body"))  // at top level, could happen if text is not in a <p>
            {
                continue;
            }

            parentElem=elem.getParentElement();
            if (parentElem.getName().equals("body"))  // at top level
            {
                continue;
            }
            count = (Integer)parentTbl.get(parentElem);
            if (count==null)
            {
                if (parentElem.getElementCount()==1)
                {
                    selTagVct.addElement(parentElem);
                    // hang on to elements to remove from input tagVct
                    removeTagVct.addElement(elem);
                }
                else {
                    // add this parent to the hashtable
                    parentTbl.put(parentElem, new Integer(1));
                }
            }
            else
            {
                // increment count, if matches children total, then add parent to selected vct
                int cnt = (count.intValue())+1;
                parentTbl.put(parentElem, new Integer(cnt));
                if (cnt==parentElem.getElementCount())
                {
                    selTagVct.addElement(parentElem);
                    parentTbl.remove(parentElem);
                    // hang on to elements to remove from input tagVct
                    for (int x=0; x<cnt; x++) {
                        removeTagVct.addElement(parentElem.getElement(x)); }
                }
            }
        }

        if (selTagVct.size()>0)   // parents were found, do they have completely selected parents?
        {
            // recurse to grandparents
            selTagVct = getSelectionTags(selTagVct);
        }

        // remove used elements from tagVct that had all siblings selected
        for (int i=0; i<removeTagVct.size(); i++) {
            tagVct.remove(removeTagVct.elementAt(i)); }

        // add unused elements back into return vct, their parents were not completely selected or they
        // already were at the body level.. sorted here by start offset
        for (int i=0;i<tagVct.size(); i++)
        {
            Element elem = (Element)tagVct.elementAt(i);

            int id=0;
            for(; id<selTagVct.size(); id++)
            {
                Element elem2 = (Element)selTagVct.elementAt(id);
                if (elem2.getStartOffset()> elem.getStartOffset()) {
                    break;
                }
            }
            // store them in start offset order
            selTagVct.insertElementAt(elem,id);
        }

        removeTagVct.clear();
        removeTagVct = null;
        parentTbl.clear();
        parentTbl = null;

        return selTagVct;
    }

    // get the controlling tags and return them for things like listitems or table rows/cells
    Vector getDependentControllingTags(int startPos)
    {
        Vector<String> tagVct = new Vector<String>();

        // use start location.. ignore selection for now because insertions do not handle selections
        // get corresponding structure element from the document for this location
        // find parents.. ignoring <content> and <p-implied>, character element will be content or br
        Element elem = getParagraphElement(startPos);
        while(elem!=null && !elem.getName().equals("html"))
        {
            // if it is p-implied don't save it, just get parent
            if (elem.getName().equals(HTML.Tag.IMPLIED.toString())) {
                elem = elem.getParentElement(); }

            tagVct.addElement(elem.getName());
            elem = elem.getParentElement();  // get the parent, it also controls the possible tags
        }

        return tagVct;
    }

    // get the information on the control tag for display in status bar
    String getControlTagInfo(int startPos, int endPos, AttributeSet inputAttr, String delimiter)
    {
        StringBuffer sb = new StringBuffer();
        String elemInfo = null;
        StringBuffer elemInfoSb = new StringBuffer();
        if (startPos==endPos)  // no selection
        {
            Element elem = getCharacterElement(startPos);  // <br> is a leaf (character element)
            Stack<String> tagstk = new Stack<String>();
            boolean isHeading = false;
            int infoLen=0;
            // go up until body building string
            while(elem!=null && !elem.getName().equals("body"))
            {
                AttributeSet attr = null;
                elemInfoSb.setLength(0);
                // if it is p-implied or content don't save it, just get parent
                if (elem.getName().equals(HTML.Tag.IMPLIED.toString()) ||
                    elem.getName().equals(HTML.Tag.CONTENT.toString()))
                {
                    elem = elem.getParentElement();
                    continue;
                }
                elemInfo = (String)TAG_NAME_TBL.get(elem.getName());
                if (elemInfo==null) { // missing from TAG_NAME_TBL
                    elemInfo = elem.getName();
                }

                elemInfoSb.append(elemInfo);

                if(elem.getName().equals("h1")||elem.getName().equals("h2")||elem.getName().equals("h3")||
                    elem.getName().equals("h4")||elem.getName().equals("h5")||elem.getName().equals("h6")) {
                    isHeading=true;}

                // add element index in parent
                if (!elem.getName().equals(HTML.Tag.BR.toString()))
                {
                    Element parent = elem.getParentElement();
                    for (int i=0; i<parent.getElementCount(); i++){
                        if (parent.getElement(i)==elem)
                        {
                            elemInfoSb.append("["+(i+1)+"]");
                            break;
                        }
                    }
                }

                // does this element have any publish attributes
                attr = elem.getAttributes();
                if (attr.isDefined(PUBLISH_TAG_ATTR)) // only look at the current attributeset
                {
                    if (!attr.getAttribute(PUBLISH_TAG_ATTR).equals("none")) {
                        elemInfoSb.append(" ("+PUBLISH_TAG_ATTR+"="+attr.getAttribute(PUBLISH_TAG_ATTR).toString()+")");
                    }
                }

                tagstk.push(elemInfoSb.toString());
                elem = elem.getParentElement();
            }

            while(!tagstk.empty())
            {
                String info = (String)tagstk.pop();
                if (sb.length()>0) {
                    sb.append(" - ");
                }

                infoLen+=info.length();
                // limit the length of the tool tip, if too long it is not rendered properly
                if (sb.length()>0 && infoLen > TOOL_TIP_LEN && delimiter!=null)
                {
                    infoLen=0;
                    sb.append(delimiter);
                }
                sb.append(info);
            }

            if (sb.length()==0) {
                sb.append("No Tag"); }

            // add any formatting information that may not be visible such as bold, italic or underline
            // but need to get it from the input attribute set in the editorkit.
            // don't indicate bold for content in heading tags
            if (!isHeading&& inputAttr.isDefined(CSS.Attribute.FONT_WEIGHT)) // bold
            {
                // normal is the toggle from bold, don't put it in the status bar message
                if (!inputAttr.getAttribute(CSS.Attribute.FONT_WEIGHT).toString().equals("normal"))
                {
                    sb.append(" - "+inputAttr.getAttribute(CSS.Attribute.FONT_WEIGHT).toString());
                }
            }
            if (inputAttr.isDefined(CSS.Attribute.FONT_STYLE))  // italic
            {
                String style=inputAttr.getAttribute(CSS.Attribute.FONT_STYLE).toString().trim();
                if (style.length()>0) {
                    sb.append(" - "+style); }
            }
            if (inputAttr.isDefined(CSS.Attribute.TEXT_DECORATION)) // underline or line-through
            {
                String style = inputAttr.getAttribute(CSS.Attribute.TEXT_DECORATION).toString().trim();
                if (style.length()>0) {
                    sb.append(" - "+style); }
            }
            tagstk.clear();
            tagstk=null;
        }
        else  // there is a selection
        {
            // get all of the top level elements selected
            Element selElem[] = getPublishAttributeControllingTags(startPos,endPos);
            sb.append("Selected ");
            for (int i=0; i<selElem.length; i++)
            {
                AttributeSet attr = null;
                elemInfoSb.setLength(0);
                if (i>0 && delimiter!=null){
                    sb.append(delimiter);}

                elemInfo = (String)TAG_NAME_TBL.get(selElem[i].getName());
                if (elemInfo==null)  {// missing from TAG_NAME_TBL
                    elemInfo = selElem[i].getName();}

                elemInfoSb.append(elemInfo);

                // add element index in parent.. sort of misleading.. dont' add now
                /*Element parent = selElem[i].getParentElement();
                for (int i2=0; i2<parent.getElementCount(); i2++)
                    if (parent.getElement(i2)==selElem[i])
                    {
                        elemInfo+="["+(i2+1)+"]";
                        break;
                    }
                */

                // does this element have any publish attributes
                attr = selElem[i].getAttributes();
                if (attr.isDefined(PUBLISH_TAG_ATTR)) // only look at the current attributeset
                {
                    if (!attr.getAttribute(PUBLISH_TAG_ATTR).equals("none")) {
                        elemInfoSb.append(" ("+PUBLISH_TAG_ATTR+"="+attr.getAttribute(PUBLISH_TAG_ATTR).toString()+")");
                    }
                }

                sb.append(" - "+elemInfoSb.toString());
            }
// for debug add endpos element
/*Element elem = getParagraphElement(endPos);
// if it is p-implied don't save it, just get parent
if (elem.getName().equals(HTML.Tag.IMPLIED.toString()))
elem = elem.getParentElement();
if (elem.getStartOffset()==endPos)
sb.append(" :: "+elem.getName());
*/
        }

        return sb.toString();
    }

    /*************************************************************************
    * base implementation will set bold in the closing leaf (new line content only)
    * this causes problems when written out, extra <b>\n</b> is generated
    * from defaultstyleddocument
    * @param offset  int
    * @param length  int
    * @param s       AttributeSet
    * @param replace boolean
    */
    public void setCharacterAttributes(int offset, int length, AttributeSet s, boolean replace)
    {
        try {
            AttributeSet sCopy = null;
            DefaultDocumentEvent changes = null;
            int lastEnd = Integer.MAX_VALUE;

            writeLock();
            changes = new DefaultDocumentEvent(offset, length, DocumentEvent.EventType.CHANGE);

            // split elements that need it
            buffer.change(offset, length, changes);

            sCopy = s.copyAttributes();

            // PENDING(prinz) - this isn't a very efficient way to iterate
            for (int pos = offset; pos < (offset + length); pos = lastEnd)
            {
                Element run = getCharacterElement(pos);
                MutableAttributeSet attr = null;
                lastEnd = run.getEndOffset();
                // WSS added to prevent text attributes in terminating new line
                if(run.getEndOffset()-run.getStartOffset()==1)
                {
                    Element parent = run.getParentElement();
                    if (parent.getEndOffset()==run.getEndOffset())
                    {
//System.err.println("XMLDocument:setCharacterAttributes() ignoring run: "+run+" with parent: "+parent);
                        continue;
                    }
                }
                attr = (MutableAttributeSet) run.getAttributes();
                changes.addEdit(new AttributeUndoableEdit(run, sCopy, replace));
                if (replace) {
                    attr.removeAttributes(attr);
                }

                attr.addAttributes(s);
            }
            changes.end();
            fireChangedUpdate(changes);
            fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
        } finally {
            writeUnlock();
        }
    }

/* XMLDocument */
    XMLDocument(StyleSheet styles)
    {
        super(styles);

        // WSS use ours for cursor fix!!!!...
        buffer = new XMLElementBuffer(createDefaultRoot());

        // always ignore charset directive, such as in meta
        //<META http-equiv=Content-Type content="text/html; charset=Shift_JIS">
        // this avoids charsetchanged exceptions if found in meta tag
        // but we should never have a meta tag in our html
        putProperty("IgnoreCharsetDirective", new Boolean(true));
        undoEditMgr = null;  // make jtest happy
    }
    /**
     * Fetches the reader for the parser to use to load the document
     * with HTML.
     * @param pos int
     * @return HTMLEditorKit.ParserCallback
     */
    public HTMLEditorKit.ParserCallback getReader(int pos) {
        XMLReader reader = new XMLReader(pos);
        return reader;
    }

    /**
     * Fetches the reader for the parser to use to load the document
     * with HTML.
     * @param pos       int
     * @param popDepth  int the number of ElementSpec.EndTagTypes to generate before
     *        inserting
     * @param pushDepth int the number of ElementSpec.StartTagTypes with a direction
     *        of ElementSpec.JoinNextDirection that should be generated
     *        before inserting, but after the end tags have been generated
     * @param insertTag HTML.Tag the first tag to start inserting into document
     * @return HTMLEditorKit.ParserCallback
     */
    public HTMLEditorKit.ParserCallback getReader(int pos, int popDepth, int pushDepth,  HTML.Tag insertTag) {
        return getReader(pos, popDepth, pushDepth, insertTag, true);
    }

    /**
     * Fetches the reader for the parser to use to load the document
     * with HTML.
     * @param pos       int
     * @param popDepth   int the number of ElementSpec.EndTagTypes to generate before
     *        inserting
     * @param pushDepth  int the number of ElementSpec.StartTagTypes with a direction
     *        of ElementSpec.JoinNextDirection that should be generated
     *        before inserting, but after the end tags have been generated
     * @param insertTag  HTML.Tag the first tag to start inserting into document
     * @param insertInsertTag  boolean false if all the Elements after insertTag should
     *        be inserted; otherwise insertTag will be inserted
     * @return HTMLEditorKit.ParserCallback
     */
//MAJOR problem with a derived class.. can't override this, not public or protected
// x_insertHTML() requires this.. and constructor too!!!
    HTMLEditorKit.ParserCallback getReader(int pos, int popDepth,
        int pushDepth, HTML.Tag insertTag, boolean insertInsertTag)
    {
        XMLReader reader = new XMLReader(pos, popDepth, pushDepth,
            insertTag, insertInsertTag, false, true);
        return reader;
    }

    /**
     * Inserts new elements in bulk.  This is how elements get created
     * in the document.  The parsing determines what structure is needed
     * and creates the specification as a set of tokens that describe the
     * edit while leaving the document free of a write-lock.  This method
     * can then be called in bursts by the reader to acquire a write-lock
     * for a shorter duration (i.e. while the document is actually being
     * altered).
     *
     * @param offset int the starting offset
     * @param attr AttributeSet the element data
     * @exception BadLocationException  if the given position does not
     *   represent a valid location in the associated document.
     */
/*    protected void insert(int offset, ElementSpec[] data) throws BadLocationException {
System.out.println("insert: add html structure at offset: "+offset);
StringBuffer sb = new StringBuffer();
for(int i=0; i<data.length; i++)
{
System.out.println("insert() elemspec["+i+"] "+data[i]+" offset: "+
                data[i].getOffset()+" attr: "+data[i].getAttributes());
ElementSpec es = data[i];
if (es.getLength() > 0) {
    String str = new String(es.getArray(), es.getOffset(),  es.getLength());
    System.out.println("content *"+str+"*");
  sb.append(es.getArray(), es.getOffset(),  es.getLength());
}
}

System.out.println("insert(): total content to be inserted *"+sb.toString()+"*");

        super.insert(offset, data);
//dump(//System.out);
    }
*/
    // Adjustment of structure is needed when a break is inserted.  A break is modeled as
    // a leaf.  Using the html input methods, it is wrapped in some container such as <p> first and
    // is not inserted as a true break but as a container with a break.  To get around this it
    // must be directly inserted as content, and then modify the parent structure to contain it.
    //
    // this is running before the space is inserted... so it must replicate the parser to
    // build the correct ElementSpecs to do the proper insert....
    void insertBreak(int offset, AttributeSet attr) throws BadLocationException
    {
        SimpleAttributeSet newAttrs = null;
        ElementSpec es = null;
        ElementSpec[] data =new ElementSpec[1];
        char[] one = new char[1];
        Vector<ElementSpec> parseBuffer = new Vector<ElementSpec>();

        // find out where cursor is and build element structure to insert a break
        // a space will get inserted into the content, the view will render a new line
        Element paragraph = getParagraphElement(offset);

        AttributeSet pattr = paragraph.getAttributes();  // attributes have the html tag name
        // must handle when inserting at the start of an element, middle or end of an element
        // start and end are problematic because without adjusting structure the element at that
        // position is just expanded, at start, the break is added to the previous element

        // Character attributes should come from actual insertion point. so things like bold get carried forward
        Element run = paragraph.getElement(paragraph.getElementIndex(offset));
        AttributeSet cattr = run.getAttributes();
        one[0] = ' ';

        if (attr == null) {
            attr = SimpleAttributeSet.EMPTY;
        }

        // make a copy of the input attributeset so things like bold get maintained
        if (offset!=(paragraph.getEndOffset()-1)) { // use input attributes if at end
            attr = cattr;  // use character attributes at cursor
        }

        newAttrs = new SimpleAttributeSet(attr);
        newAttrs.addAttribute(StyleConstants.NameAttribute,HTML.Tag.BR);

        es = new ElementSpec(newAttrs, ElementSpec.ContentType, one, 0, 1);
        data[0] =es;

        if (offset!=0 && paragraph.getStartOffset()==offset) // at the end of prev and start of this one
        {
            // get preceeding element
            Element prevParagraph = getParagraphElement(offset-1);
            // build specs
            createSpecsForInsertBreakAfterNewline(paragraph, prevParagraph, pattr, parseBuffer, offset);//, offset-1);
        }

        parseBuffer.addElement(es);
        data = new ElementSpec[parseBuffer.size()];
        parseBuffer.copyInto(data);
        parseBuffer.clear();
        parseBuffer = null;

        super.insert(offset,data);
    }

    /**
     * This is called by insertBreak when inserting after a new line(end of paragraph).
     * It generates, in <code>parseBuffer</code>, ElementSpecs that will
     * position the stack in <code>paragraph</code>.<p>
     */
    private void createSpecsForInsertBreakAfterNewline(Element paragraph,
        Element prevParagraph, AttributeSet pattr, Vector<ElementSpec> parseBuffer, int offset)//, int endOffset)
    {
        // Need to find the common parent of pParagraph and paragraph.
        if(paragraph.getParentElement() == prevParagraph.getParentElement())
        {
            Element parent = null;
            // The simple (and common) case that prevParagraph and
            // paragraph have the same parent.
            ElementSpec spec = new ElementSpec(pattr, ElementSpec.EndTagType);
            parseBuffer.addElement(spec);
            spec = new ElementSpec(pattr, ElementSpec.StartTagType);
            parseBuffer.addElement(spec);
            if(prevParagraph.getEndOffset() != offset)
            {
                spec.setDirection(ElementSpec.JoinFractureDirection);
                //return;
            }else {
                parent = prevParagraph.getParentElement();
                // if paragraph is is not the last sibling join next??
                if((parent.getElementIndex(offset) + 1) < parent.getElementCount())
                {
                    spec.setDirection(ElementSpec.JoinNextDirection);
                }
                // if paragraph is the eod paragraph, use join next or structure is not
                // created properly
                if (offset==getLength())
                {
                    spec.setDirection(ElementSpec.JoinNextDirection);
                }
            }
        }
        else
        {
            // Will only happen for text with more than 2 levels.
            // Find the common parent of a paragraph and prevParagraph
            Vector<Element> leftParents = new Vector<Element>();
            Vector<Element> rightParents = new Vector<Element>();
            int leftIndex = -1;
            Element e = prevParagraph;
            while(e != null)
            {
                leftParents.addElement(e);
                e = e.getParentElement();
            }
            e = paragraph;
            while(e != null && (leftIndex = leftParents.indexOf(e)) == -1)
            {
                rightParents.addElement(e);
                e = e.getParentElement();
            }
            if(e != null)
            {
                ElementSpec spec = null;
                // e identifies the common parent.
                // Build the ends.
                for(int counter = 0; counter < leftIndex; counter++)
                {
                    parseBuffer.addElement(new ElementSpec(null, ElementSpec.EndTagType));
                }
                // And the starts.
                for(int counter = rightParents.size() - 1; counter >= 0; counter--)
                {
                    spec = new ElementSpec(((Element)rightParents.elementAt(counter)).getAttributes(),
                        ElementSpec.StartTagType);
                    if(counter > 0)
                    {
                        spec.setDirection(ElementSpec.JoinNextDirection);
                    }
                    parseBuffer.addElement(spec);
                }
                // If there are right parents, then we generated starts
                // down the right subtree and there will be an element to
                // join to.
                if(rightParents.size() > 0)
                {
                    spec.setDirection(ElementSpec.JoinNextDirection);
                }
                // No right subtree, e.getElement(endOffset) is a
                // leaf. There will be a facture.
            }
        }
    }


// insertUpdate() is used to modify structure after characters are entered (typing or
// pasting without structure).  If '\n' was found, a new paragraph is created as structure.  So if text was pasted
// with several newlines, each line of text would be put into it's own paragraph.  This is done
// after the actual text is inserted into the content.  The original structure elements offsets
// are Position objects so the original elements just grow.. incorrectly until insertUpdate()
// adjusts the structure.
    /**
     * Updates document structure as a result of text insertion.  This
     * will happen within a write lock.  This implementation simply
     * parses the inserted content for line breaks and builds up a set
     * of instructions for the element buffer.
     *
     * @param chng a description of the document change
     * @param attr the attributes
     */
    protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr)
    {
//System.out.println("insertUpdate: only debug.. no chgs must be made here"); // when new content is entered.....
//System.err.println("DefStyDoc:insertUpdate: entered inputAttrSet: "); // when new content is entered.....
//showAttributes(System.err,attr);
        ((XMLElementBuffer)buffer).keybdUpdate(true);
        super.insertUpdate( chng, attr );
        ((XMLElementBuffer)buffer).keybdUpdate(false);
    }
/*
        if(attr == null) {
            attr = contentAttributeSet;
        }

        // If this is the composed text element, merge the content attribute to it
        else if (attr.isDefined(StyleConstants.ComposedTextAttribute)) {
            ((MutableAttributeSet)attr).addAttributes(contentAttributeSet);
        }

//from defaultStyledDocument... if i make any changes below here.. there will be a problem with updatebidi()
// in the abstractDocument base class. i can't run its insertUpdate() without running
// defaultStyledDocument.insertUpdate()
        int offset = chng.getOffset();
        int length = chng.getLength();
        if (attr == null) {
            attr = SimpleAttributeSet.EMPTY;
        }

        // Paragraph attributes should come from point after insertion.
        // You really only notice this when inserting at a paragraph
        // boundary.
        Element paragraph = getParagraphElement(offset + length);
        AttributeSet pattr = paragraph.getAttributes();
//System.err.println("DefStyDoc::insertUpdate: paragraph at orig caret insert pt: "+paragraph);
//showAttributes(pattr);
        // Character attributes should come from actual insertion point.
        Element pParagraph = getParagraphElement(offset);
//System.err.println("DefStyDoc::insertUpdate: paragraph actual insert pt: "+pParagraph);
        Element run = pParagraph.getElement(pParagraph.getElementIndex
                                            (offset));
        int endOffset = offset + length;
        boolean insertingAtBoundry = (run.getEndOffset() == endOffset);
        AttributeSet cattr = run.getAttributes();
//System.err.println("DefStyDoc::insertUpdate: run actual insert pt: "+run);
//showAttributes(cattr);

        try {
            Segment s = new Segment();
            Vector parseBuffer = new Vector();
            ElementSpec lastStartSpec = null;
            boolean insertingAfterNewline = false;
            short lastStartDirection = ElementSpec.OriginateDirection;
            // Check if the previous character was a newline.
            if (offset > 0) {
                getText(offset - 1, 1, s);
                if (s.array[s.offset] == '\n') {
                    // Inserting after a newline.
                    insertingAfterNewline = true;
                    lastStartDirection = createSpecsForInsertAfterNewline
                                  (paragraph, pParagraph, pattr, parseBuffer,
                                   offset, endOffset);
                    for(int counter = parseBuffer.size() - 1; counter >= 0;
                        counter--) {
                        ElementSpec spec = (ElementSpec)parseBuffer.
                                            elementAt(counter);
                        if(spec.getType() == ElementSpec.StartTagType) {
                            lastStartSpec = spec;
                            break;
                        }
                    }
                }
            }
            // If not inserting after a new line, pull the attributes for
            // new paragraphs from the paragraph under the insertion point.
            if(!insertingAfterNewline)
                pattr = pParagraph.getAttributes();

            getText(offset, length, s);
            char[] txt = s.array;
            int n = s.offset + s.count;
            int lastOffset = s.offset;

            for (int i = s.offset; i < n; i++) {
                if (txt[i] == '\n') {
                    int breakOffset = i + 1;
                    parseBuffer.addElement(
                        new ElementSpec(attr, ElementSpec.ContentType,
                                               breakOffset - lastOffset));
                    parseBuffer.addElement(
                        new ElementSpec(null, ElementSpec.EndTagType));
                    lastStartSpec = new ElementSpec(pattr, ElementSpec.
                                                   StartTagType);
                    parseBuffer.addElement(lastStartSpec);
                    lastOffset = breakOffset;
                }
            }
            if (lastOffset < n) {
                parseBuffer.addElement(
                    new ElementSpec(attr, ElementSpec.ContentType,
                                           n - lastOffset));
            }

            ElementSpec first = (ElementSpec) parseBuffer.firstElement();

            int docLength = getLength();

            // Check for join previous of first content.
            if(first.getType() == ElementSpec.ContentType &&
               cattr.isEqual(attr)) {
                first.setDirection(ElementSpec.JoinPreviousDirection);
            }

            // Do a join fracture/next for last start spec if necessary.
            if(lastStartSpec != null) {
                if(insertingAfterNewline) {
                    lastStartSpec.setDirection(lastStartDirection);
                }
                // Join to the fracture if NOT inserting at the end
                // (fracture only happens when not inserting at end of
                // paragraph).
                else if(pParagraph.getEndOffset() != endOffset) {
                    lastStartSpec.setDirection(ElementSpec.
                                               JoinFractureDirection);
                }
                // Join to next if parent of pParagraph has another
                // element after pParagraph, and it isn't a leaf.
                else {
                    Element parent = pParagraph.getParentElement();
                    int pParagraphIndex = parent.getElementIndex(offset);
                    if((pParagraphIndex + 1) < parent.getElementCount() &&
                       !parent.getElement(pParagraphIndex + 1).isLeaf()) {
                        lastStartSpec.setDirection(ElementSpec.
                                                   JoinNextDirection);
                    }
                }
            }

            // Do a JoinNext for last spec if it is content, it doesn't
            // already have a direction set, no new paragraphs have been
            // inserted or a new paragraph has been inserted and its join
            // direction isn't originate, and the element at endOffset
            // is a leaf.
            if(insertingAtBoundry && endOffset < docLength) {
                ElementSpec last = (ElementSpec) parseBuffer.lastElement();
                if(last.getType() == ElementSpec.ContentType &&
                   last.getDirection() != ElementSpec.JoinPreviousDirection &&
                   ((lastStartSpec == null && (paragraph == pParagraph ||
                                               insertingAfterNewline)) ||
                    (lastStartSpec != null && lastStartSpec.getDirection() !=
                     ElementSpec.OriginateDirection))) {
                    Element nextRun = paragraph.getElement(paragraph.
                                           getElementIndex(endOffset));
                    // Don't try joining to a branch!
                    if(nextRun.isLeaf() &&
                       attr.isEqual(nextRun.getAttributes())) {
                        last.setDirection(ElementSpec.JoinNextDirection);
                    }
                }
            }
            // If not inserting at boundary and there is going to be a
            // fracture, then can join next on last content if cattr
            // matches the new attributes.
            else if(!insertingAtBoundry && lastStartSpec != null &&
                    lastStartSpec.getDirection() ==
                    ElementSpec.JoinFractureDirection) {
                ElementSpec last = (ElementSpec) parseBuffer.lastElement();
                if(last.getType() == ElementSpec.ContentType &&
                   last.getDirection() != ElementSpec.JoinPreviousDirection &&
                   attr.isEqual(cattr)) {
                    last.setDirection(ElementSpec.JoinNextDirection);
                }
            }

            // Check for the composed text element. If it is, merge the character attributes
            // into this element as well.
            if (isComposedTextAttributeDefined(attr)) {
                ((MutableAttributeSet)attr).addAttributes(cattr);
                ((MutableAttributeSet)attr).addAttribute(AbstractDocument.ElementNameAttribute,
                                                         AbstractDocument.ContentElementName);
            }

            ElementSpec[] spec = new ElementSpec[parseBuffer.size()];
            parseBuffer.copyInto(spec);
            buffer.insert(offset, length, spec, chng);
        } catch (BadLocationException bl) {
        }


//        super.insertUpdate( chng, attr );

// from AbstractDocument .. but can't access it!!!! super.super fails..
//        if( getProperty(I18NProperty).equals( Boolean.TRUE ) )
//            updateBidi( chng );
    }
*/
    /**
     * This is called by insertUpdate when inserting after a new line.
     * It generates, in <code>parseBuffer</code>, ElementSpecs that will
     * position the stack in <code>paragraph</code>.<p>
     * It returns the direction the last StartSpec should have (this doesn't
     * necessarily create the last start spec).
     */
/*    short createSpecsForInsertAfterNewline(Element paragraph,
                    Element pParagraph, AttributeSet pattr, Vector parseBuffer,
                                                 int offset, int endOffset)
    {
        // Need to find the common parent of pParagraph and paragraph.
        if(paragraph.getParentElement() == pParagraph.getParentElement())
        {
            // The simple (and common) case that pParagraph and
            // paragraph have the same parent.
            ElementSpec spec = new ElementSpec(pattr, ElementSpec.EndTagType);
            parseBuffer.addElement(spec);
            spec = new ElementSpec(pattr, ElementSpec.StartTagType);
            parseBuffer.addElement(spec);
            if(pParagraph.getEndOffset() != endOffset)
            {
                return ElementSpec.JoinFractureDirection;
            }

            Element parent = pParagraph.getParentElement();
            if((parent.getElementIndex(offset) + 1) < parent.getElementCount())
            {
                return ElementSpec.JoinNextDirection;
            }
        }
        else
        {
            // Will only happen for text with more than 2 levels.
            // Find the common parent of a paragraph and pParagraph
            Vector leftParents = new Vector();
            Vector rightParents = new Vector();
            Element e = pParagraph;
            while(e != null)
            {
                leftParents.addElement(e);
                e = e.getParentElement();
            }
            e = paragraph;
            int leftIndex = -1;
            while(e != null && (leftIndex = leftParents.indexOf(e)) == -1)
            {
                rightParents.addElement(e);
                e = e.getParentElement();
            }
            if(e != null)
            {
                // e identifies the common parent.
                // Build the ends.
                for(int counter = 0; counter < leftIndex; counter++)
                {
                    parseBuffer.addElement(new ElementSpec(null, ElementSpec.EndTagType));
                }
                // And the starts.
                ElementSpec spec = null;
                for(int counter = rightParents.size() - 1; counter >= 0; counter--)
                {
                    spec = new ElementSpec(((Element)rightParents.elementAt(counter)).getAttributes(),
                                   ElementSpec.StartTagType);
                    if(counter > 0)
                    {
                        spec.setDirection(ElementSpec.JoinNextDirection);
                    }
                    parseBuffer.addElement(spec);
                }
                // If there are right parents, then we generated starts
                // down the right subtree and there will be an element to
                // join to.
                if(rightParents.size() > 0)
                {
                    return ElementSpec.JoinNextDirection;
                }
                // No right subtree, e.getElement(endOffset) is a
                // leaf. There will be a facture.
                return ElementSpec.JoinFractureDirection;
            }
            // else: Could throw an exception here, but should never get here!
        }

        return ElementSpec.OriginateDirection;
    }

    /**
     * Replaces the contents of the document with the given
     * element specifications.  This is called before insert if
     * the loading is done in bursts.  This is the only method called
     * if loading the document entirely in one burst.
     */
/*    protected void create(ElementSpec[] data) {
    */
/*//System.out.println("elements before create():");
//        dump(System.out);
//System.out.println("XMLDocument::create() elemspec.len "+data.length);
//        for(int i=0; i<data.length; i++) {
//System.out.println("create() elemspec["+i+"] "+data[i]+" offset: "+
//            data[i].getOffset()+" attr: "+data[i].getAttributes());}
    */
//      super.create(data);
////System.out.println("elements after create():");
//dump(//System.out);
//    }


    /** Must be here to access create() in my ElementBuffer because ElementBuffer.create() is not public
     * Initialize the document to reflect the given element
     * structure (i.e. the structure reported by the
     * <code>getDefaultRootElement</code> method.  If the
     * document contained any data it will first be removed.
     */
// if i use this and load some xml.. the control dies with badlocation and length error, stateinvariant??
// but ok if only <p></p> is loaded???
   /* protected void create(ElementSpec[] data)
    {
dump(//System.out);
//System.out.println("XMLDocument::create() elemspec.len "+data.length);
for(int i=0; i<data.length; i++)
//System.out.println("create() elemspec["+i+"] "+data[i]+" offset: "+
data[i].getOffset()+" attr: "+data[i].getAttributes());
        try {
            if (getLength() != 0) {
                remove(0, getLength());
            }
            writeLock();

            // install the content
            Content c = getContent();
            int n = data.length;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < n; i++) {
                ElementSpec es = data[i];
                if (es.getLength() > 0) {
                    sb.append(es.getArray(), es.getOffset(),  es.getLength());
                }
            }
            UndoableEdit cEdit = c.insertString(0, sb.toString());

//System.out.println("XMLDocument::create sb *"+sb.toString()+"*");
            // build the event and element structure
            int length = sb.length();
            DefaultDocumentEvent evnt =
                new DefaultDocumentEvent(0, length, DocumentEvent.EventType.INSERT);
            evnt.addEdit(cEdit);
            ((XMLElementBuffer)buffer).createStructure(length, data, evnt);

            // update bidi (possibly)
            super.insertUpdate(evnt, null);

            // notify the listeners
            evnt.end();
            fireInsertUpdate(evnt);
            fireUndoableEditUpdate(new UndoableEditEvent(this, evnt));
        } catch (BadLocationException ble) {
//            throw new StateInvariantError("problem initializing");
            throw new RuntimeException("problem initializing");
        } finally {
            writeUnlock();
        }

    }*/

    /**
     * Sets attributes for a paragraph.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @ param offset the offset into the paragraph (must be at least 0)
     * @ param length the number of characters affected (must be at least 0)
     * @ param s the attributes
     * @ param replace whether to replace existing attributes, or merge them
     */
/*    public void setParagraphAttributes1111(int offset, int length, AttributeSet s,
                       boolean replace) {
    try {
        writeLock();
        // Make sure we send out a change for the length of the paragraph.
        int end = Math.min(offset + length, getLength());
        Element e = getParagraphElement(offset);
        offset = e.getStartOffset();
        e = getParagraphElement(end);
        length = Math.max(0, e.getEndOffset() - offset);
        DefaultDocumentEvent changes =
        new DefaultDocumentEvent(offset, length,
                     DocumentEvent.EventType.CHANGE);
        AttributeSet sCopy = s.copyAttributes();
        int lastEnd = Integer.MAX_VALUE;
        for (int pos = offset; pos <= end; pos = lastEnd) {
        Element paragraph = getParagraphElement(pos);
        if (lastEnd == paragraph.getEndOffset()) {
            lastEnd++;
        }
        else {
            lastEnd = paragraph.getEndOffset();
        }
        MutableAttributeSet attr =
            (MutableAttributeSet) paragraph.getAttributes();
        changes.addEdit(new AttributeUndoableEdit(paragraph, sCopy, replace));
        if (replace) {
            attr.removeAttributes(attr);
        }
        attr.addAttributes(s);
        }
        changes.end();
        fireChangedUpdate(changes);
        fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
    } finally {
        writeUnlock();
    }
    }
*/
// WSS copy of setParagraphAttributes but gets above p-implied paragraph element
// to set/reset table attributes.  The view was not properly rebuilt so an extra
// ElementEdit is created to force redraw
    void setParagraphAttributes(Element paragraph, AttributeSet s, boolean replace)
    {
        try {
            DefaultDocumentEvent changes =  null;
            AttributeSet sCopy = null;
            MutableAttributeSet attr = null;
            ElementEdit ee = null;

            writeLock();
            // Make sure we send out a change for the length of the paragraph.
            changes =  new DefaultDocumentEvent(paragraph.getStartOffset(),
                paragraph.getEndOffset()-paragraph.getStartOffset(),
                DocumentEvent.EventType.CHANGE);

            sCopy = s.copyAttributes();
            attr =  (MutableAttributeSet) paragraph.getAttributes();

            changes.addEdit(new AttributeUndoableEdit(paragraph, sCopy, replace));
            if (replace) {
                attr.removeAttributes(attr);
            }

            attr.addAttributes(s);

//System.err.println("setParagraphAttributes: attr to be used");
//  showAttributes(System.err,attr);
            // border is replaced but not rendered properly!!!!!
            // need to completely remove the table and reinsert it.. or make the code think that is what happened
            ee = new ElementEdit((BranchElement)paragraph.getParentElement(),
                paragraph.getParentElement().getElementIndex(paragraph.getStartOffset()),
                new Element[] {paragraph}, new Element[] {paragraph});
            changes.addEdit(ee);

            changes.end();
            fireChangedUpdate(changes);
            fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
        } finally {
            writeUnlock();
        }
    }

// WSS copy of setParagraphAttributes
// to set/reset table-row attributes.  The view was not properly rebuilt so an extra
// ElementEdit is created to force redraw
    void setParagraphChildAttributes(Element paragraph, AttributeSet s, boolean replace)
    {
        try {
            DefaultDocumentEvent changes =  null;
            ElementEdit ee = null;

            writeLock();
            // Make sure we send out a change for the length of the paragraph.
            changes =  new DefaultDocumentEvent(paragraph.getStartOffset(),
                paragraph.getEndOffset()-paragraph.getStartOffset(),
                DocumentEvent.EventType.CHANGE);

            for (int i=0; i<paragraph.getElementCount(); i++)
            {
                Element child = paragraph.getElement(i);
                AttributeSet sCopy = s.copyAttributes();
                MutableAttributeSet attr =  (MutableAttributeSet) child.getAttributes();

                changes.addEdit(new AttributeUndoableEdit(child, sCopy, replace));
                if (replace) {
                    attr.removeAttributes(attr);
                }

                attr.addAttributes(s);
//System.err.println("setParagraphChildAttributes: attr to be used");
//  showAttributes(System.err,attr);

                if (s.getAttribute(StyleConstants.NameAttribute)==HTML.Tag.TH)
                {
                    // remove font-weight=normal character attributes
                    // th tag is not rendered properly if font-weight=normal
                    int lastEnd = Integer.MAX_VALUE;
                    for (int pos = child.getStartOffset(); pos < child.getEndOffset(); pos = lastEnd)
                    {
                        MutableAttributeSet runAttr = null;
                        Object fwObj = null;
                        Element run = getCharacterElement(pos);
                        lastEnd = run.getEndOffset();
                        // prevent text attributes in terminating new line
                        if(run.getEndOffset()-run.getStartOffset()==1)
                        {
                            Element parent = run.getParentElement();
                            if (parent.getEndOffset()==run.getEndOffset())
                            {
//System.err.println("XMLDocument:setCharacterAttributes() ignoring run: "+run+" with parent: "+parent);
                                continue;
                            }
                        }
                        runAttr = (MutableAttributeSet) run.getAttributes();

//System.err.println("\nrun attrset ");
//showAttributes(System.err,runAttr);
                        // if attr contains font-weight=normal, remove it
                        fwObj = runAttr.getAttribute(CSS.Attribute.FONT_WEIGHT);
                        if (fwObj!=null && fwObj.toString().equals("normal"))
                        {
                            AttributeSet runCopy = runAttr.copyAttributes();
                            changes.addEdit(new AttributeUndoableEdit(run, runCopy, replace));

                            runAttr.removeAttribute(CSS.Attribute.FONT_WEIGHT);
                            if (runAttr.getAttribute(HTML.Tag.B)!=null) {
                                runAttr.removeAttribute(HTML.Tag.B);}
//System.err.println("run attrset after removeattr() ");
//showAttributes(System.err,runAttr);
                        }
                    }
                } // end turn on th
            }

            // td/th is replaced but not rendered properly!!!!!
            // need to completely remove the table and reinsert it.. or make the code think that is what happened
            ee = new ElementEdit((BranchElement)paragraph.getParentElement(),
                paragraph.getParentElement().getElementIndex(paragraph.getStartOffset()),
                new Element[] {paragraph}, new Element[] {paragraph});
            changes.addEdit(ee);

            changes.end();
            fireChangedUpdate(changes);
            fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
        } finally {
            writeUnlock();
        }
    }

// WSS copy of setParagraphAttributes but gets above p-implied paragraph element
// to set/reset publish attributes
    void setPublishAttributes(int offset, int length, AttributeSet s, boolean replace)
    {
        try {
            DefaultDocumentEvent changes = null;
            AttributeSet sCopy = null;
            Element e =null;
            Element[] pubElems =null;
            writeLock();
            // this will get all of the elements to have publish set
            pubElems = getPublishAttributeControllingTags(offset, offset+length);

            // Make sure we send out a change for the length of the paragraph.
            offset = pubElems[0].getStartOffset();
            e = pubElems[pubElems.length-1];  // last element
            length = Math.max(0, e.getEndOffset() - offset);
            changes = new DefaultDocumentEvent(offset, length, DocumentEvent.EventType.CHANGE);
            sCopy = s.copyAttributes();
            for (int i=0; i<pubElems.length; i++)
            {
                Element paragraph = pubElems[i];
                MutableAttributeSet attr = (MutableAttributeSet) paragraph.getAttributes();
                changes.addEdit(new AttributeUndoableEdit(paragraph, sCopy, replace));
                if (replace) {
                    attr.removeAttributes(attr);
                }
                attr.addAttributes(s);
            }
            changes.end();

            fireChangedUpdate(changes);
            fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
        } finally {
            writeUnlock();
        }
    }

    /**
     * Creates a document leaf element that directly represents
     * text (doesn't have any children).  This is implemented
     * to return an element of type
     * <code>HTMLDocument.RunElement</code>.
     *
     * @ param parent the parent element
     * @ param a the attributes for the element
     * @ param p0 the beginning of the range (must be at least 0)
     * @ param p1 the end of the range (must be at least p0)
     * @ return the new element
     */
/*    protected Element createLeafElement(Element parent, AttributeSet a, int p0, int p1) {
        return new RunElement(parent, a, p0, p1);
    }

    /**
     * Creates a document branch element, that can contain other elements.
     * This is implemented to return an element of type
     * <code>HTMLDocument.BlockElement</code>.
     *
     * @ param parent the parent element
     * @ param a the attributes
     * @ return the element
     */
/*    protected Element createBranchElement(Element parent, AttributeSet a) {
        return new BlockElement(parent, a);
    }

    /**
     * Creates the root element to be used to represent the
     * default document structure.
     *
     * @ return the element base
     */
/*    protected AbstractElement createDefaultRoot()
    {
        // grabs a write-lock for this initialization and
        // abandon it during initialization so in normal
        // operation we can detect an illegitimate attempt
        // to mutate attributes.
        writeLock();
        MutableAttributeSet a = new SimpleAttributeSet();
        a.addAttribute(StyleConstants.NameAttribute, HTML.Tag.HTML);
        BlockElement html = new BlockElement(null, a.copyAttributes());
        a.removeAttributes(a);
        a.addAttribute(StyleConstants.NameAttribute, HTML.Tag.BODY);
        BlockElement body = new BlockElement(html, a.copyAttributes());
        a.removeAttributes(a);
        a.addAttribute(StyleConstants.NameAttribute, HTML.Tag.P);
        BlockElement paragraph = new BlockElement(body, a.copyAttributes());
        a.removeAttributes(a);
        a.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
        RunElement brk = new RunElement(paragraph, a, 0, 1);
        Element[] buff = new Element[1];
        buff[0] = brk;
        paragraph.replace(0, 0, buff);
        buff[0] = paragraph;
        body.replace(0, 0, buff);
        buff[0] = body;
        html.replace(0, 0, buff);
        writeUnlock();
        return html;
    }*/

    /******************************
     * Replaces the children of the given element with the contents
     * specified as an HTML string.
     * <p>This will be seen as at least two events, n inserts followed by
     * a remove.
     * <p>For this to work correcty, the receiver must have an
     * HTMLEditorKit.Parser set. This will be the case if the receiver
     * was created from an HTMLEditorKit via the
     * <code>createDefaultDocument</code> method.
     * @since 1.3
     *
     * @param elem Element
     * @param htmlText String
     * @throws IOException
     * @throws BadLocationException
     * @throws IllegalArgumentException is <code>elem</code> is a leaf
     */
    public void setInnerHTML(Element elem, String htmlText) throws BadLocationException, IOException
    {
//System.out.println("setInnerHTML entered elem "+elem);
        if (elem != null && elem.isLeaf()) {
            throw new IllegalArgumentException("Can not set inner HTML of a leaf");
        }
        if (elem != null && htmlText != null) {
            int oldCount = elem.getElementCount();
            int insertPosition = elem.getStartOffset();
            x_insertHTML(elem, insertPosition, htmlText, true);
            if (elem.getElementCount() > oldCount)
            {
                // Elements were inserted, do the cleanup.
                x_removeElements(elem, elem.getElementCount() - oldCount, oldCount);
            }
        }
    }

    /************
    * This is used to insert new list items.  The 'extra' list item generated by splitting
    * the structure is removed.  This also allows the parser to properly handle <ol><li>
    * and not insert the ol in the list.  Passing '<li></li>' fails the parser because li must
    * be in an enclosing <ol> or <ul> structure.  This solves the problem by specifying the
    * tag to begin insertion with, ignoring the enclosing <ol> or ul.
    * @param parent Element
    * @param insertTag HTML.Tag
    * @param html String
    * @param offset int
    * @throws IOException
    * @throws BadLocationException
    */
    public void addInnerHTML(Element parent, HTML.Tag insertTag, String html,
            int offset) throws BadLocationException, IOException
    {
//System.out.println("addInnerHTML entered parent: "+parent+" insertag "+insertTag+" html: "+html);
        if (parent != null && parent.isLeaf()) {
            throw new IllegalArgumentException("Can not set inner HTML of a leaf");
        }
        if (parent != null && html != null)
        {
            int childIndex = parent.getElementIndex(offset);
            int oldCount = parent.getElementCount();

            HTMLEditorKit.Parser hparser = getParser();
            if (hparser != null)
            {
                int lastOffset = Math.max(0, offset - 1);
                Element charElement = getCharacterElement(lastOffset);
                Element commonParent = parent;
                int pop = 0;
                int push = 0;

                if (parent.getStartOffset() > lastOffset)
                {
                    while (commonParent != null && commonParent.getStartOffset() > lastOffset)
                    {
                        commonParent = commonParent.getParentElement();
                        push++;
                    }
                    if (commonParent == null) {
                        throw new BadLocationException("No common parent", offset);
                    }
                }
                while (charElement != null && charElement != commonParent) {
                    pop++;
                    charElement = charElement.getParentElement();
                }
                if (charElement != null)
                {
/*
     * @param popDepth   the number of ElementSpec.EndTagTypes to generate before
     *        inserting
     * @param pushDepth  the number of ElementSpec.StartTagTypes with a direction
     *        of ElementSpec.JoinNextDirection that should be generated
     *        before inserting, but after the end tags have been generated
     * @param insertTag  the first tag to start inserting into document
     * @param insertInsertTag  false if all the Elements after insertTag should
     *        be inserted; otherwise insertTag will be inserted
*/
                    // Found it, do the insert.
                    XMLReader reader = new XMLReader(offset, pop - 1, push,
                        insertTag,//null, // force skipping ol or ul
                        true,//false,  // insertInsertTag
                        false,
                        true); //wantsTrailingNewline

                    hparser.parse(new StringReader(html), reader, true);
                    reader.flush();

                    // if more than one element was added, remove the extra one
                    // this happens when structure is split, an extra list item
                    // is inserted.. remove it
                    if (parent.getElementCount() - oldCount>1)
                    {
                        // error with list item creation at offset=0 and list contains pre or p as first element
                        if (offset==0 &&
                            parent.getElement(childIndex).getName().equals(HTML.Tag.LI.toString()) &&
                            parent.getElement(childIndex).getEndOffset()-parent.getElement(childIndex).getStartOffset()==1 &&
                            (parent.getElement(childIndex).getElement(0).getName().equals(HTML.Tag.P.toString()) ||
                                parent.getElement(childIndex).getElement(0).getName().equals(HTML.Tag.PRE.toString())))
                        {
                            System.err.println("addInnerHTML handling special case of new list item and list has p or pre");
                            x_removeElements(parent, childIndex, 1);
                        }
                        else {
                            // Too many elements were inserted, do the cleanup.
                            x_removeElements(parent, childIndex+1, 1); }
                    }
                }
            }
        }
    }

    /**
    * Replaces the given element in the parent with the contents
    * specified as an HTML string.
    * <p>This will be seen as at least two events, n inserts followed by
    * a remove.
    * <p>When replacing a leaf this will attempt to make sure there is
    * a newline present if one is needed. This may result in an additional
    * element being inserted. Consider, if you were to replace a character
    * element that contained a newline with &lt;img&gt; this would create
    * two elements, one for the image, ane one for the newline.
    * <p>If you try to replace the element at length you will most likely
    * end up with two elements, eg setOuterHTML(getCharacterElement
    * (getLength()), "blah") will result in two leaf elements at the end,
    * one representing 'blah', and the other representing the end element.
    * <p>For this to work correcty, the receiver must have an
    * HTMLEditorKit.Parser set. This will be the case if the receiver
    * was created from an HTMLEditorKit via the
    * <code>createDefaultDocument</code> method.
    *
    * @param elem Element
    * @param htmlText String
    * @throws BadLocationException
    * @throws IOException
    * @since 1.3
    */
    public void setOuterHTML(Element elem, String htmlText) throws
        BadLocationException, IOException
    {
//System.out.println("setOuterHTML entered htmlText: "+htmlText+" elem to replace: "+elem);

        if (elem != null && elem.getParentElement() != null && htmlText != null)
        {
            Element parent = null;
            int oldCount = 0;
            int newLength =0;
            int start = elem.getStartOffset();
            int end = elem.getEndOffset();
            int startLength = getLength();
            // We don't want a newline if elem is a leaf, and doesn't contain
            // a newline.
            boolean wantsNewline = !elem.isLeaf();
            if (!wantsNewline && (end > startLength || getText(end - 1, 1).charAt(0) == NEWLINE[0]))
            {
                wantsNewline = true;
            }
            parent = elem.getParentElement();
            oldCount = parent.getElementCount();

            x_insertHTML(parent, start, htmlText, wantsNewline);
            // Remove old.
            newLength = getLength();

            if (oldCount != parent.getElementCount())
            {
                int removeIndex = parent.getElementIndex(start + newLength - startLength);
                x_removeElements(parent, removeIndex, 1);
            }
        }
    }

    /**
     * Inserts the HTML specified as a string at the start
     * of the element.
     * <p>For this to work correcty, the receiver must have an
     * HTMLEditorKit.Parser set. This will be the case if the receiver
     * was created from an HTMLEditorKit via the
     * <code>createDefaultDocument</code> method.
     * @param elem Element
     * @param htmlText String
     * @throws IOException
     * @throws BadLocationException
     *
     * @since 1.3
     */
    public void insertAfterStart(Element elem, String htmlText) throws  BadLocationException, IOException
    {
//System.out.println("insertAfterStart entered");

        if (elem != null && elem.isLeaf()) {
            throw new IllegalArgumentException("Can not insert HTML after start of a leaf");
        }
        x_insertHTML(elem, elem.getStartOffset(), htmlText, false);
    }

    /**
     * Inserts the HTML specified as a string at the end of
     * the element.
     * <p> If <code>elem</code>'s children are leafs, at the
     * character at a <code>elem.getEndOffset() - 1</code> is a newline,
     * this will insert before the newline so that there isn't text after
     * the newline.
     * <p>For this to work correcty, the receiver must have an
     * HTMLEditorKit.Parser set. This will be the case if the receiver
     * was created from an HTMLEditorKit via the
     * <code>createDefaultDocument</code> method.
     *
     * @param elem Element
     * @param htmlText String
     * @throws IOException
     * @throws BadLocationException
     * @since 1.3
     */
    public void insertBeforeEnd(Element elem, String htmlText) throws BadLocationException, IOException
    {
//System.out.println("insertBeforeEnd entered");
        int offset = 0;
        if (elem != null && elem.isLeaf()) {
            throw new IllegalArgumentException("Can not set inner HTML before end of leaf");
        }
        offset = elem.getEndOffset();
        if (elem.getElement(elem.getElementIndex(offset - 1)).isLeaf() &&
            getText(offset - 1, 1).charAt(0) == NEWLINE[0]) {
            offset--;
        }
        x_insertHTML(elem, offset, htmlText, false);
    }

    /**
     * Inserts the HTML specified as string before the start of
     * the given element.
     * <p>For this to work correcty, the receiver must have an
     * HTMLEditorKit.Parser set. This will be the case if the receiver
     * was created from an HTMLEditorKit via the
     * <code>createDefaultDocument</code> method.
     *
     * @param elem Element
     * @param htmlText String
     * @throws IOException
     * @throws BadLocationException
     * @since 1.3
     */
    public void insertBeforeStart(Element elem, String htmlText) throws BadLocationException, IOException
    {
        if (elem != null) {
            Element parent = elem.getParentElement();

            if (parent != null) {
                x_insertHTML(parent, elem.getStartOffset(), htmlText, false);
            }
        }
    }

    /**
     * Inserts the HTML specified as a string after the
     * the end of the given element.
     * <p>For this to work correcty, the receiver must have an
     * HTMLEditorKit.Parser set. This will be the case if the receiver
     * was created from an HTMLEditorKit via the
     * <code>createDefaultDocument</code> method.
     *
     * @param elem Element
     * @param htmlText String
     * @throws IOException
     * @throws BadLocationException
     * @since 1.3
     */
    public void insertAfterEnd(Element elem, String htmlText) throws BadLocationException, IOException
    {
//System.out.println("insertAfterEnd entered elem: "+elem);

        if (elem != null)
        {
            Element parent = elem.getParentElement();

            if (parent != null)
            {
                int offset = elem.getEndOffset();
                if (elem.isLeaf() && getText(offset - 1, 1).
                    charAt(0) == NEWLINE[0]) {
                    offset--;
                }
                // if trying to insert at document terminating new line, offset must be reduced wss
                if (offset>getLength())  //wss
                {
                    offset--;
//System.err.println("********trying to insert at doc term new line********");
                }

                x_insertHTML(parent, offset, htmlText, false);
            }
        }
    }

    /**
     * Fetches the element that has the given id attribute.
     * If the element can't be found, null is returned. This is not
     * thread-safe.
     *
     * @since 1.3
     */
/*    public Element getElement(String id) {
        if (id == null) {
            return null;
        }
        return getElement(getDefaultRootElement(), HTML.Attribute.ID, id,
              true);
    }

    /**
     * Returns the child element of <code>e</code> that contains the
     * attribute, <code>attribute</code> with value <code>value</code>, or
     * null if one isn't found. This is not thread-safe.
     *
     * @since 1.3
     */
/*    public Element getElement(Element e, Object attribute, Object value) {
        return getElement(e, attribute, value, false);
    }

    /**
     * Returns the child element of <code>e</code> that contains the
     * attribute, <code>attribute</code> with value <code>value</code>, or
     * null if one isn't found. This is not thread-safe.
     * If <code>searchLeafAttributes</code> is true, and <code>e</code> is
     * a leaf, any attributes that are instances of HTML.Tag with a
     * value that is an AttributeSet will also be checked.
     */
/*    private Element getElement(Element e, Object attribute, Object value,
                   boolean searchLeafAttributes) {
        AttributeSet attr = e.getAttributes();

        if (attr != null && attr.isDefined(attribute)) {
            if (value.equals(attr.getAttribute(attribute))) {
            return e;
            }
        }
        if (!e.isLeaf()) {
            for (int counter = 0, maxCounter = e.getElementCount();
             counter < maxCounter; counter++) {
            Element retValue = getElement(e.getElement(counter), attribute,
                              value, searchLeafAttributes);

            if (retValue != null) {
                return retValue;
            }
            }
        }
        else if (searchLeafAttributes && attr != null) {
            // For some leaf elements we store the actual attributes inside
            // the AttributeSet of the Element (such as anchors).
            Enumeration names = attr.getAttributeNames();
            if (names != null) {
            while (names.hasMoreElements()) {
                Object name = names.nextElement();
                if ((name instanceof HTML.Tag) &&
                (attr.getAttribute(name) instanceof AttributeSet)) {

                AttributeSet check = (AttributeSet)attr.
                                 getAttribute(name);
                if (check.isDefined(attribute) &&
                    value.equals(check.getAttribute(attribute))) {
                    return e;
                }
                }
            }
            }
        }
        return null;
    }

    /**
     * Inserts a string of HTML into the document at the given position.
     * <code>parent</code> is used to identify the location to insert the
     * <code>html</code>. If <code>parent</code> is a leaf this can have
     * unexpected results.
     */
    private void x_insertHTML(Element parent, int offset, String html, boolean wantsTrailingNewline)
        throws BadLocationException, IOException
    {
//System.out.println("insertHTML entered parent: "+parent+" offset: "+offset+" html: "+html+
//" wantsnewline: "+wantsTrailingNewline);

        if (parent != null && html != null)
        {
            HTMLEditorKit.Parser hparser = getParser();
            if (hparser != null)
            {
                int lastOffset = Math.max(0, offset - 1);
                Element charElement = getCharacterElement(lastOffset);
//System.out.println("insertHTML lastoffset: "+lastOffset+" prev charElem: "+charElement);
                Element commonParent = parent;
//System.out.println("insertHTML commonParent: "+commonParent);
                int pop = 0;
                int push = 0;

                if (parent.getStartOffset() > lastOffset)
                {
                    while (commonParent != null && commonParent.getStartOffset() > lastOffset)
                    {
                        commonParent = commonParent.getParentElement();
                        push++;
//System.out.println("insertHTML commonParent: "+commonParent+" push: "+push);
                    }
                    if (commonParent == null) {
                        throw new BadLocationException("No common parent", offset);
                    }
                }
                while (charElement != null && charElement != commonParent) {
                    pop++;
                    charElement = charElement.getParentElement();
//System.out.println("insertHTML charelem: "+charElement+" pop: "+pop);
                }
                if (charElement != null) {
//System.out.println("doing insert: length: "+getLength()+" offset: "+offset+" pop: "+(pop-1)+" push: "+push+
//" wantsnewline: "+wantsTrailingNewline);

//dump(System.out);
                    // Found it, do the insert.
                    XMLReader reader = new XMLReader(offset, pop - 1, push,
                        null, false,
                        false, //true,?? insertAfterImplied??wss
                        // setting to false avoids insert of the extra <html><body>
                        // select h3, then pick h1
                        //setOuterHTML(paragraph, html);  // get<html><body><h1> inside other <html><body>
                        //insertBeforeStart(elem, html);  // get<html><body><h1> inside other <html><body>
                        //insertAfterStart(elem, html);  // get<h3><html><body><h1> inside other <html><body>
                        //setInnerHTML(elem, html); get<h3><html><body><h1>.. inside other <html><body>
                        wantsTrailingNewline);

                    hparser.parse(new StringReader(html), reader, true);
                    reader.flush();
//dump(System.out);
                }
            }
        }
    }

    /**
     * Removes child Elements of the passed in Element <code>e</code>. This
     * will do the necessary cleanup to ensure the element representing the
     * end character is correctly created.
     * <p>This is not a general purpose method, it assumes that <code>e</code>
     * will still have at least one child after the remove, and it assumes
     * the character at <code>e.getStartOffset() - 1</code> is a newline and
     * is of length 1.
     */
    private void x_removeElements(Element e, int index, int count) throws BadLocationException
    {
//System.err.println("XMLDocument::removeElements: element "+e+" index: "+index+" count: "+count);
        writeLock();
        try {
            int start = e.getElement(index).getStartOffset();
            int end = e.getElement(index + count - 1).getEndOffset();
            if (end > getLength()) {
                x_removeElementsAtEnd(e, index, count, start, end);
            }
            else {
                x_removeElements(e, index, count, start, end);
            }
        } finally {
            writeUnlock();
        }
    }

    /**
     * Called to remove child elements of <code>e</code> when one of the
     * elements to remove is representing the end character.
     * <p>Since the Content will not allow a removal to the end character
     * this will do a remove from <code>start - 1</code> to <code>end</code>.
     * The end Element(s) will be removed, and the element representing
     * <code>start - 1</code> to <code>start</code> will be recreated. This
     * Element has to be recreated as after the content removal its offsets
     * become <code>start - 1</code> to <code>start - 1</code>.
     */
    private void x_removeElementsAtEnd(Element e, int index, int count,
        int start, int end) throws BadLocationException
    {
//System.out.println("**removeElementsAtEnd: element "+e+" index: "+index+" count: "+count);
        Element[] added = new Element[0];
        Element[] removed = new Element[count];
        DefaultDocumentEvent dde = null;
        UndoableEdit u;

        // index must be > 0 otherwise no insert would have happened.
        boolean isLeaf = (e.getElement(index - 1).isLeaf());
        if (isLeaf) {
            index--;
        }

        for (int counter = 0; counter < count; counter++) {
            removed[counter] = e.getElement(counter + index);
        }
        dde = new DefaultDocumentEvent
            (start - 1, end - start + 1, DocumentEvent.EventType.REMOVE);
        dde.addEdit(new ElementEdit(e, index, removed, added));
        ((AbstractDocument.BranchElement)e).replace(index, removed.length, added);

        if (isLeaf) {
            // start - 1 was a leaf, simply remove it.
            u = getContent().remove(start - 1, end - start);
        }
        else {
            Element[] oRemoved = new Element[1];
            Element[] oAdded = new Element[1];
            AttributeSet attrs = null;
            // Not a leaf, descend until we find the leaf representing
            // start - 1 and remove it.
            Element newLineE = e.getElement(index - 1);
            while (!newLineE.isLeaf()) {
                newLineE = newLineE.getElement(newLineE.getElementCount() - 1);
            }

            oRemoved[0] = newLineE;
            attrs = newLineE.getAttributes();
            newLineE = newLineE.getParentElement();
            u = getContent().remove(start - 1, end - start);
            // Now recreate the Element for start - 1.
            oAdded[0] = createLeafElement(newLineE, attrs, start - 1, start);
            ((AbstractDocument.BranchElement)newLineE).replace(newLineE.getElementCount() - 1, 1, oAdded);
            dde.addEdit(new ElementEdit(newLineE, newLineE.getElementCount() - 1, oRemoved, oAdded));
        }
        if (u != null) {
            dde.addEdit(u);
        }
        postRemoveUpdate(dde);
        dde.end();
        fireRemoveUpdate(dde);
        if (u != null) {
            fireUndoableEditUpdate(new UndoableEditEvent(this, dde));
        }
    }

    /**
     * Called to remove child Elements when the end is not touched.
     */
    private void x_removeElements(Element e, int index, int count,
        int start, int end) throws BadLocationException
    {
//System.out.println("**removeElements2: element "+e+" index: "+index+" count: "+count);
        Element[] removed = new Element[count];
        Element[] added = new Element[0];
        DefaultDocumentEvent dde = null;
        UndoableEdit u = null;
        for (int counter = 0; counter < count; counter++) {
            removed[counter] = e.getElement(counter + index);
        }
        dde = new DefaultDocumentEvent(start, end - start, DocumentEvent.EventType.REMOVE);
        ((AbstractDocument.BranchElement)e).replace(index, removed.length, added);
        dde.addEdit(new ElementEdit(e, index, removed, added));
        u = getContent().remove(start, end - start);
        if (u != null) {
            dde.addEdit(u);
        }
        postRemoveUpdate(dde);
        dde.end();
        fireRemoveUpdate(dde);
        if (u != null) {
            fireUndoableEditUpdate(new UndoableEditEvent(this, dde));
        }
    }

    // These two are provided for inner class access. They are named different
    // than the super class as the super class implementations are final.
    void obtainLock() {
        writeLock();
    }

    void releaseLock() {
        writeUnlock();
    }

    /*********************************************************************
    * add listener
    * XMLErrorListener is used to display errors found in IDL'd or pasted XML
    * @param l XMLErrorListener
    */
    public void addXMLErrorListener(XMLErrorListener l) {
        listenerList.add(XMLErrorListener.class, l);
    }

    /*********************************************************************
    * remove listener
    * @param l XMLErrorListener
    */
    public void removeXMLErrorListener(XMLErrorListener l) {
        listenerList.remove(XMLErrorListener.class, l);
    }

    // Notify all listeners that have registered interest for
    // notification on this event type.  The event instance
    // is lazily created using the parameters passed into
    // the fire method.
    private void fireXMLError(String errorMsg, int pos)
    {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==XMLErrorListener.class) {
                ((XMLErrorListener)listeners[i+1]).handleError(errorMsg, pos);
            }
        }
    }

//Basically the entire HTMLReader copied here and modified!
    /**
     * An HTML reader to load an HTML document with an HTML
     * element structure.  This is a set of callbacks from
     * the parser, implemented to create a set of elements
     * tagged with attributes.  The parse builds up tokens
     * (ElementSpec) that describe the element subtree desired,
     * and burst it into the document under the protection of
     * a write lock using the insert method on the document
     * outer class.
     * <p>
     * The reader can be configured by registering actions
     * (of type <code>HTMLDocument.XMLReader.TagAction</code>)
     * that describe how to handle the action.  The idea behind
     * the actions provided is that the most natural text editing
     * operations can be provided if the element structure boils
     * down to paragraphs with runs of some kind of style
     * in them.  Some things are more naturally specified
     * structurally, so arbitrary structure should be allowed
     * above the paragraphs, but will need to be edited with structural
     * actions.  The implication of this is that some of the
     * HTML elements specified in the stream being parsed will
     * be collapsed into attributes, and in some cases paragraphs
     * will be synthesized.  When HTML elements have been
     * converted to attributes, the attribute key will be of
     * type HTML.Tag, and the value will be of type AttributeSet
     * so that no information is lost.  This enables many of the
     * existing actions to work so that the user can type input,
     * hit the return key, backspace, delete, etc and have a
     * reasonable result.  Selections can be created, and attributes
     * applied or removed, etc.  With this in mind, the work done
     * by the reader can be categorized into the following kinds
     * of tasks:
     * <dl>
     * <dt>Block
     * <dd>Build the structure like it's specified in the stream.
     * This produces elements that contain other elements.
     * <dt>Paragraph
     * <dd>Like block except that it's expected that the element
     * will be used with a paragraph view so a paragraph element
     * won't need to be synthesized.
     * <dt>Character
     * <dd>Contribute the element as an attribute that will start
     * and stop at arbitrary text locations.  This will ultimately
     * be mixed into a run of text, with all of the currently
     * flattened HTML character elements.
     * <dt>Special
     * <dd>Produce an embedded graphical element.
     * <dt>Form
     * <dd>Produce an element that is like the embedded graphical
     * element, except that it also has a component model associated
     * with it.
     * <dt>Hidden
     * <dd>Create an element that is hidden from view when the
     * document is being viewed read-only, and visible when the
     * document is being edited.  This is useful to keep the
     * model from losing information, and used to store things
     * like comments and unrecognized tags.
     *
     * </dl>
     * <p>
     * Currently, &lt;APPLET&gt;, &lt;PARAM&gt;, &lt;MAP&gt;, &lt;AREA&gt;, &lt;LINK&gt;,
     * &lt;SCRIPT&gt; and &lt;STYLE&gt; are unsupported.
     *
     * <p>
     * The assignment of the actions described is shown in the
     * following table for the tags defined in <code>HTML.Tag</code>.
     *
     * HTML.Tag.B            CharacterAction
     * HTML.Tag.BIG          CharacterAction
     * HTML.Tag.BODY         BlockAction
     * HTML.Tag.BR           SpecialAction
     * HTML.Tag.DD           BlockAction
     * HTML.Tag.DFN          CharacterAction
     * HTML.Tag.DL           BlockAction
     * HTML.Tag.DT           ParagraphAction
     * HTML.Tag.EM           CharacterAction
     * HTML.Tag.H1           ParagraphAction
     * HTML.Tag.H2           ParagraphAction
     * HTML.Tag.H3           ParagraphAction
     * HTML.Tag.H4           ParagraphAction
     * HTML.Tag.H5           ParagraphAction
     * HTML.Tag.H6           ParagraphAction
     * HTML.Tag.HR           SpecialAction
     * HTML.Tag.HTML         BlockAction
     * HTML.Tag.I            CharacterAction
     * HTML.Tag.LI           BlockAction
     * HTML.Tag.OL           BlockAction
     * HTML.Tag.P            ParagraphAction
     * HTML.Tag.PRE          PreAction
     * HTML.Tag.STRIKE       CharacterAction
     * HTML.Tag.S            CharacterAction
     * HTML.Tag.STRONG       CharacterAction
     * HTML.Tag.SUB          CharacterAction
     * HTML.Tag.SUP          CharacterAction
     * HTML.Tag.TABLE        BlockAction
     * HTML.Tag.TD           BlockAction
     * HTML.Tag.TH           BlockAction
     * HTML.Tag.TR           BlockAction
     * HTML.Tag.TT           CharacterAction
     * HTML.Tag.U            CharacterAction
     * HTML.Tag.UL           BlockAction
     *
     */
    private class XMLReader extends HTMLEditorKit.ParserCallback
    {
        private static final int HASHTBL_SIZE=57;
        // check if publish is part of the attribute set
        // this could be expanded to check if the attributes and values specified are supported
        // by the dtd.. WSS
        private MutableAttributeSet checkAttributes(HTML.Tag t,MutableAttributeSet a)
        {
            Vector<Object> removedVct = null;  // save list of attributes to remove
            XMLParserDelegator hparser = null;
            javax.swing.text.html.parser.Element elem =null;
            MutableAttributeSet aCopy = a;
            Enumeration<?> eNum = null;

            if (a.getAttributeCount()>0) { //  attributes to check
                // is this a valid tag
                // the XMLParserDelegator is not instantiated until XMLEditor.loadDocument()
                // if the editor is used to display temporary messages, the default parser is
                // used.
                if ((XMLDocument.this.getParser() instanceof XMLParserDelegator)) {
                    hparser = (XMLParserDelegator)XMLDocument.this.getParser();
                    elem = hparser.getElement(t.toString());
                    if (elem!=null) {
                        removedVct = new Vector<Object>();

                        // verify each attribute found in the tag is supported by the dtd
                        eNum = a.getAttributeNames();
                        outterloop: while(eNum.hasMoreElements())
                        {
                            Object o = eNum.nextElement();
                            javax.swing.text.html.parser.AttributeList attlist = elem.getAttribute(o.toString());
                            if (attlist==null)
                            {
                                // attribute is not supported by the dtd
                                removedVct.addElement(o);
                            }
                            else  // is the value supported?
                            {
                                //String attrValue = a.getAttribute(o).toString();
                                Enumeration valEnum = attlist.getValues();
                                if (valEnum==null) { // any value is valid?? more work here to verify type??
                                    continue outterloop;}
                                while(valEnum.hasMoreElements())
                                {
                                    Object value = valEnum.nextElement();
                                    if (value.equals(a.getAttribute(o))) { // value is supported
                                        continue outterloop; }
                                }
                                // value not supported by dtd, remove the attribute
                                removedVct.addElement(o);
                            }
                        }

                        if (removedVct.size()>0) {
                            // build a new attribute set without unsupported attributes
                            // this way doesn't always seem to work with the StyleConstants checks.
                            // things like isBold() fail on the new AttributeSet but work on the set used in the constructor
                //          SimpleAttributeSet aCopy = new SimpleAttributeSet(a);
                            // this method works on both attributesets, new and old
                            /* Just creating a SimpleAttributeSet and copying attributes does not work because
                            * the CSS values like "font-weight"="bold" are not properly checked by the
                            * StyleConstants.isBold() method.  A ViewAttributeSet or a NamedStyle do the conversion,
                            * but I can't create one of them.*/
                            aCopy = (MutableAttributeSet)a.copyAttributes();
                            aCopy.removeAttributes(removedVct.elements());
                        }
                    }
                }
            }

            return aCopy;
        }

        /*****************
         * constructor
         * @param offset
         */
        XMLReader(int offset) {
            this(offset, 0, 0, null);
        }

        /******************
         * constructor
         * @param offset
         * @param popDepth
         * @param pushDepth
         * @param insertTag
         */
        XMLReader(int offset, int popDepth, int pushDepth, HTML.Tag insertTag) {
            this(offset, popDepth, pushDepth, insertTag, true, false, true);
        }

        /**
         * Generates a RuntimeException (will eventually generate
         * a BadLocationException when API changes are alloced) if inserting
         *  into non
         * empty document, <code>insertTag</code> is non null, and
         * <code>offset</code> is not in the body.
         */
        // PENDING(sky): Add throws BadLocationException and remove
        // RuntimeException
        XMLReader(int offset, int popDepth, int pushDepth,
            HTML.Tag insertTag,
            boolean insertInsertTag,
            boolean insertAfterImplied, boolean wantsTrailingNewline)
        {
    //System.out.println("XMLReader constructor() offset: "+offset+" popDepth: "+popDepth+" pushDepth: "+
    //pushDepth+" insertTag: "+insertTag+" insertInsertTag: "+insertInsertTag+" insertAfterImplied: "+insertAfterImplied+" wantsTrailingNewline: "+wantsTrailingNewline);

            //TagAction na = new TagAction();
            TagAction ba = new BlockAction();
            TagAction pa = new ParagraphAction();
            //TagAction ca = new CharacterAction();
            TagAction sa = new SpecialAction();
            TagAction conv = new ConvertAction();

            emptyDocument = (getLength() == 0);
            this.offset = offset;
            threshold = XMLDocument.this.getTokenThreshold();
            tagMap = new Hashtable<Tag, TagAction>(HASHTBL_SIZE);

            // register handlers for the well known tags
            tagMap.put(HTML.Tag.B, conv);
            tagMap.put(HTML.Tag.BODY, ba);
            tagMap.put(HTML.Tag.BR, sa);
            tagMap.put(HTML.Tag.DD, ba);
    //      tagMap.put(HTML.Tag.DFN, ca);
            tagMap.put(HTML.Tag.DL, ba);
            tagMap.put(HTML.Tag.DT, pa);
            tagMap.put(HTML.Tag.EM, conv);//ca);
            tagMap.put(HTML.Tag.H1, pa);
            tagMap.put(HTML.Tag.H2, pa);
            tagMap.put(HTML.Tag.H3, pa);
            tagMap.put(HTML.Tag.H4, pa);
            tagMap.put(HTML.Tag.H5, pa);
            tagMap.put(HTML.Tag.H6, pa);
            // preventing the head and the element (0,1)
            tagMap.put(HTML.Tag.HEAD, new TagAction());  // prevent element for head
    //      tagMap.put(HTML.Tag.HR, sa);
            tagMap.put(HTML.Tag.HTML, ba);
            tagMap.put(HTML.Tag.I, conv);
            tagMap.put(HTML.Tag.LI, ba);
            tagMap.put(HTML.Tag.OL, ba);
            tagMap.put(HTML.Tag.P, pa);
            tagMap.put(HTML.Tag.PRE, new PreAction());//v11
    //      tagMap.put(HTML.Tag.STRIKE, conv);
    //      tagMap.put(HTML.Tag.S, ca);
            tagMap.put(HTML.Tag.STRONG, conv);//ca);
    //      tagMap.put(HTML.Tag.SUB, conv);
    //      tagMap.put(HTML.Tag.SUP, conv);
            tagMap.put(HTML.Tag.TABLE, ba);
            tagMap.put(HTML.Tag.TD, ba);
            tagMap.put(HTML.Tag.TH, ba);
            tagMap.put(HTML.Tag.TR, ba);
    //      tagMap.put(HTML.Tag.TT, ca);
            tagMap.put(HTML.Tag.U, conv);
            tagMap.put(HTML.Tag.UL, ba);
            // Clear out the old comments.
            //putProperty(AdditionalComments, null);

            if (insertTag != null)
            {
                this.insertTag = insertTag;
                this.popDepth = popDepth;
                this.pushDepth = pushDepth;
                this.insertInsertTag = insertInsertTag;
                foundInsertTag = false;
            }
            else {
                foundInsertTag = true;
            }
            if (insertAfterImplied)
            {
                this.popDepth = popDepth;
                this.pushDepth = pushDepth;
                this.insertAfterImplied = true;
                foundInsertTag = false;
                midInsert = false;
                this.insertInsertTag = true;
                this.wantsTrailingNewline = wantsTrailingNewline;
            }
            else {
                midInsert = (!emptyDocument && insertTag == null);
                if (midInsert) {
                    generateEndsSpecsForMidInsert();
                }
            }
        }

        /**
         * Generates an initial batch of end ElementSpecs in parseBuffer
         * to position future inserts into the body.
         */
        private void generateEndsSpecsForMidInsert()
        {
    //System.out.println("generateEndsSpecsForMidInsert() entered doc.length: "+getLength());
            int count = heightToElementWithName(HTML.Tag.BODY,Math.max(0, offset - 1));
            boolean joinNext = false;

            if (count == -1 && offset > 0) {
                count = heightToElementWithName(HTML.Tag.BODY, offset);
                if (count != -1) {
                    // Previous isn't in body, but current is. Have to
                    // do some end specs, followed by join next.
                    count = depthTo(offset - 1) - 1;
                    joinNext = true;
                }
            }
            if (count == -1) {
                throw new RuntimeException("Must insert new content into body element-");
            }
            if (count != -1)
            {
                // Insert a newline, if necessary.
                try {
                    if (!joinNext && offset > 0 &&
                        !getText(offset - 1, 1).equals(NEWLINE_STR))
                    {
                        ElementSpec spec;
                        SimpleAttributeSet newAttrs = new SimpleAttributeSet();
                        newAttrs.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
                        spec = new ElementSpec(newAttrs, ElementSpec.ContentType, NEWLINE, 0, 1);
    //System.out.println("generateEndsSpecsForMidInsert() adding NEWLINE");
                        parseBuffer.addElement(spec);
                    }
                    // Should never throw, but will catch anyway.
                } catch (BadLocationException ble) {
                    System.out.println(ble.getMessage());  // jtest req
                }
                while (count-- > 0) {
                    parseBuffer.addElement(new ElementSpec(null, ElementSpec.EndTagType));
                }
                if (joinNext) {
                    ElementSpec spec = new ElementSpec(null, ElementSpec.StartTagType);

                    spec.setDirection(ElementSpec.JoinNextDirection);
                    parseBuffer.addElement(spec);
                }
            }
            // We should probably throw an exception if (count == -1)
            // Or look for the body and reset the offset.
        }

        /**
         * @return number of parents to reach the child at offset.
         */
        private int depthTo(int offset)
        {
            Element e = getDefaultRootElement();
            int count = 0;

            while (!e.isLeaf()) {
                count++;
                e = e.getElement(e.getElementIndex(offset));
            }
            return count;
        }

        /**
         * @return number of parents of the leaf at <code>offset</code>
         *         until a parent with name, <code>name</code> has been
         *         found. -1 indicates no matching parent with
         *         <code>name</code>.
         */
        private int heightToElementWithName(Object name, int offset)
        {
            Element e = getCharacterElement(offset).getParentElement();
            int count = 0;

            while (e != null && e.getAttributes().getAttribute(StyleConstants.NameAttribute) != name)
            {
                count++;
                e = e.getParentElement();
            }
            return (e == null) ? -1 : count;
        }

        /**
         * This will make sure the fake element (path at getLength())
         * has the form HTML BODY P.
         */
        private void adjustEndElement()
        {
            int length = getLength();
            if (length > 0) {
                obtainLock();
                try {
                    Element[] pPath = getPathTo(length - 1);
                    if (pPath.length > 1 &&
                        pPath[1].getAttributes().getAttribute
                        (StyleConstants.NameAttribute) == HTML.Tag.BODY &&
                        pPath[1].getEndOffset() == length)
                    {
                        ElementEdit firstEdit = null;
                        String lastText = getText(length - 1, 1);
                        DefaultDocumentEvent event;
                        Element[] added;
                        Element[] removed;
                        int index;
                        // Remove the fake second body.
                        added = new Element[0];
                        removed = new Element[1];
                        index = pPath[0].getElementIndex(length);
                        removed[0] = pPath[0].getElement(index);
                        ((BranchElement)pPath[0]).replace(index, 1, added);
                        firstEdit = new ElementEdit(pPath[0], index, removed, added);

                        // And then add paragraph, or adjust deepest leaf.
                        if (pPath.length == 3 &&
                            (pPath[2].getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.P ||
                                pPath[2].getAttributes().getAttribute(StyleConstants.NameAttribute) == HTML.Tag.IMPLIED) &&
                            !lastText.equals(NEWLINE_STR))
                        {
                            AttributeSet attrs = null;
                            index = pPath[2].getElementIndex(length - 1);
                            attrs = pPath[2].getElement(index).getAttributes();
                            if (attrs.getAttributeCount() == 1 &&
                                attrs.getAttribute(StyleConstants.NameAttribute)== HTML.Tag.CONTENT)
                            {
                                int start = 0;
                                // Can extend existing one.
                                added = new Element[1];
                                removed = new Element[1];
                                removed[0] = pPath[2].getElement(index);
                                start = removed[0].getStartOffset();
                                added[0] = createLeafElement(pPath[2], attrs, start, length + 1);
                                ((BranchElement)pPath[2]).replace(index, 1, added);
                                event = new DefaultDocumentEvent(start, length - start + 1,
                                    DocumentEvent.EventType.CHANGE);
                                event.addEdit(new ElementEdit(pPath[2], index, removed, added));
                            }
                            else {
                                // Create new leaf.
                                SimpleAttributeSet sas = new SimpleAttributeSet();
                                sas.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
                                added = new Element[1];
                                added[0] = createLeafElement(pPath[2], sas, length, length + 1);
                                ((BranchElement)pPath[2]).replace(index + 1, 0, added);
                                event = new DefaultDocumentEvent(length, 1, DocumentEvent.EventType.CHANGE);
                                removed = new Element[0];
                                event.addEdit(new ElementEdit(pPath[2], index + 1, removed, added));
                            }
                        }
                        else {
                        // Create paragraph
                        // If the previous sibling (element at
                        // ppPath[1].getElement(length - 1)) is a leaf we
                        // should really remove it and combine the two, as
                        // leafs of the body aren't a good idea, but this only
                        // happens when reading an empty document, so it isn't
                        // all that common.
                            SimpleAttributeSet sas = new SimpleAttributeSet();
                            BranchElement newP = null;
                            sas.addAttribute(StyleConstants.NameAttribute, HTML.Tag.P);
                            newP = (BranchElement)createBranchElement(pPath[1],sas);
                            added = new Element[1];
                            added[0] = newP;
                            removed = new Element[0];
                            index = pPath[1].getElementIndex(length - 1) + 1;
                            ((BranchElement)pPath[1]).replace(index, 0, added);
                            event = new DefaultDocumentEvent(length, 1, DocumentEvent.EventType.CHANGE);
                            event.addEdit(new ElementEdit(pPath[1], index, removed, added));
                            added = new Element[1];
                            sas = new SimpleAttributeSet();
                            sas.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
                            added[0] = createLeafElement(newP, sas, length, length + 1);
                            newP.replace(0, 0, added);
                        }
                        event.addEdit(firstEdit);
                        event.end();
                        // And finally post the event.
                        fireChangedUpdate(event);
                        fireUndoableEditUpdate(new UndoableEditEvent(this, event));
                    }
                }
                catch (BadLocationException ble) {
                    System.out.println(ble.getMessage());  // jtest req
                }
                finally {
                    releaseLock();
                }
            }
        }

        private Element[] getPathTo(int offset)
        {
            Stack<Element> elements = new Stack<Element>();
            Element[] retValue = null;
            Element e = getDefaultRootElement();
            //int index;
            while (!e.isLeaf()) {
                elements.push(e);
                e = e.getElement(e.getElementIndex(offset));
            }
            retValue = new Element[elements.size()];
            elements.copyInto(retValue);
            return retValue;
        }

        // -- HTMLEditorKit.ParserCallback methods --------------------

        /**
         * The last method called on the reader.  It allows any pending changes to be flushed
         * into the document.  Since this is currently loading synchronously, the entire
         * set of changes are pushed in at this point.
         * @throws BadLocationException
         */
        public void flush() throws BadLocationException
        {
            flushBuffer(true);
            if (emptyDocument && !insertAfterImplied) {
                adjustEndElement();
            }
        }

        /**
        * Error messages are very poor. XMLDocumentParser.error(...) attempts to correct this.
        * @param errorMsg String
        * @param pos int
        */
        public void handleError(String errorMsg, int pos)
        {
            boolean firemsg = true;
    //System.err.println("in handleError msg: "+errorMsg+" at pos: "+pos);
            // NOTE: endtag error msgs have pos off by +1
            if (errorMsg.startsWith("Unexpected tag"))
            {
                int startId = 0;
                int endId = 0;
                String invalidTag = null;
                if (errorMsg.indexOf("<body>")!=-1) { // body found again..only possible in pasted xml from ext src
                    firemsg = false;//return;
                }
                else {
                    // a tag was in an invalid location, such as <br> is not inside a <p> or <li> for ls.dtd
                    startId = errorMsg.indexOf("<");
                    endId = errorMsg.indexOf(">");
//                    invalidTag = errorMsg.substring(startId+1,endId);
                    invalidTag = XMLGenerator.getSubString(errorMsg,startId+1,endId);
                    invalidTags.push(invalidTag);
                }
            }

            if (firemsg){
                fireXMLError(errorMsg, pos);
            }
        }
        /**
        * Called by the parser to indicate a block of text was encountered.
        * @param data char[]
        * @param pos int
        */
        public void handleText(char[] data, int pos)
        {
    //System.err.println("in handletext pos: "+pos+" data:"+(new String(data))+":");
            if (!verifyXMLOnly) { // if we are only verifying the xml, do not actually insert it
                if (!invalidTags.empty()&&  // a tag was not in a valid place
                    // title is not handled properly.. this is only possible in pasted xml from another src
                    (invalidTags.peek().toString().equals(HTML.Tag.TITLE.toString()))) {
                        //return;
                }else {
                    if (midInsert && !inBody) {
                        //return;
                    }else {
                        if (inPre) {
                            preContent(data);//v11
                        }
                        else if (inBlock > 0)
                        {
                            if (!foundInsertTag && insertAfterImplied) {
                                // Assume content should be added.
                                foundInsertTag(false);
                                foundInsertTag = true;
                                inParagraph = true;
                                impliedP = true;
                            }
                            if (data.length >= 1) {
                                addContent(data, 0, data.length,pos);
                            }
                        }
                    }
                }
            }
        }

        /**
        * Callback from the parser.  Route to the appropriate handler for the tag.
        * @param t HTML.Tag
        * @param a  MutableAttributeSet
        * @param pos int
        */
        public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos)
        {
//    System.err.println("in handlestart inbody: "+inBody+" tag: "+t+"  attr: "+a+" pos: "+pos);
    //showAttributes(System.err,a);
            MutableAttributeSet a2 =null;
            TagAction action = null;
            boolean checkrest =true;

            if (!invalidTags.empty())  // a tag was not in a valid place, ignore all until end tag is found
            {
                if (!invalidTags.peek().toString().equals(t.toString()))  // was this one already pushed by the error handler
                {
                    if (!(t==HTML.Tag.B||t==HTML.Tag.U||t==HTML.Tag.I))  // allow styles to go thru
                    {
                        invalidTags.push(t.toString());
                        checkrest=false;//return;
                    }
                }
                else { // it was on top.. <b> in <h3> would be flagged as an error
                    checkrest=false;//return;
                }
            }
            if (checkrest) {
                // WSS check if there are any unsupported attributes
                a2 = checkAttributes(t,a);

                if (!verifyXMLOnly) { // if we are only verifying the xml, do not actually insert it
                    // from here down is from base class
                    if (midInsert && !inBody)
                    {
                        if (t == HTML.Tag.BODY) {
                            inBody = true;
                            // Increment inBlock since we know we are in the body,
                            // this is needed incase an implied-p is needed. If
                            // inBlock isn't incremented, and an implied-p is
                            // encountered, addContent won't be called!
                            inBlock++;
                        }
                        //return;
                    }
                    else {
                        if (!inBody && t == HTML.Tag.BODY) {
                            inBody = true;
                        }
                        action = (TagAction) tagMap.get(t);

                        if (action != null)
                        {
                            // check for nested lists without text between li and ul
                            // if text is not present, the nested list is not properly rendered
                            // in the view, a p-implied with a new line is required
                            if (t== HTML.Tag.UL || t == HTML.Tag.OL)
                            {
                                ElementSpec prev = (parseBuffer.size() > 0)?(ElementSpec) parseBuffer.lastElement() : null;
                                if (prev != null && prev.getType() == ElementSpec.StartTagType)
                                {
                                    if (prev.getAttributes().getAttribute(StyleConstants.NameAttribute)==
                                        HTML.Tag.LI)
                                    {
                                        blockOpen(HTML.Tag.IMPLIED, new SimpleAttributeSet());
                                        impliedP = true;
                                        inParagraph = true;
                                        System.err.println("*********ul or ol nested inside li forced - impliedP");
                                    }
                                }
                            }

                            action.start(t, a2);
                        }
                    }
                }
            }
        }

        /**
        * Callback from the parser.  Route to the appropriate handler for the tag.
        * @param data char[]
        * @param pos int
        */
        public void handleComment(char[] data, int pos)
        {
        /*  if (getPreservesUnknownTags()) {
            if (inBlock == 0) {
                // Comment outside of body, will not be able to show it,
                // but can add it as a property on the Document.
                Object comments = getProperty(AdditionalComments);
                if (comments != null && !(comments instanceof Vector)) {
                // No place to put comment.
                return;
                }
                if (comments == null) {
                comments = new Vector();
                putProperty(AdditionalComments, comments);
                }
                ((Vector)comments).addElement(new String(data));
                return;
            }
            SimpleAttributeSet sas = new SimpleAttributeSet();
            sas.addAttribute(HTML.Attribute.COMMENT, new String(data));
            addSpecialElement(HTML.Tag.COMMENT, sas);
            }*/
        }

        /**
        * Callback from the parser.  Route to the appropriate handler for the tag.
        * @param t HTML.Tag
        * @param pos int
        */
        public void handleEndTag(HTML.Tag t, int pos)
        {
            TagAction action = null;
            boolean checkrest = true;
    //System.err.println("in handleend tag: "+t+" pos: "+pos);
            if (!invalidTags.empty())  // <ol> may have been in an invalid location
            {
    /* this doesn't work if dtd incorrectly specifies contents as (%text) instead of (%text)*
                if ((t!=HTML.Tag.B && t!=HTML.Tag.U &&t!=HTML.Tag.I) ||  // allow styles to go thru
                    (invalidTags.peek().toString().equals(HTML.Tag.B.toString())))  // b not allowed in h1-6
                {
                    invalidTags.pop();  // remove top
                    return;
                }*/

                if (invalidTags.peek().toString().equals(t.toString()))
                {
                    invalidTags.pop();  // remove top
                    checkrest=false;//return;
                }
    //          else
    //System.err.println("handleEndTag:: ******TAG "+t+" did not match top of invalid tag stack!!! "+invalidTags.peek());

            }

            if (!verifyXMLOnly && checkrest) { // if we are only verifying the xml, do not actually insert it
                if (midInsert && !inBody) {
                    //return;
                }
                else {
                    if (t == HTML.Tag.BODY) {
                        inBody = false;
                        if (midInsert) {
                            inBlock--;
                        }
                    }
                    action = (TagAction) tagMap.get(t);
                    if (action != null) {
                        action.end(t);
                    }
                }
            }
        }

        /**
        * Callback from the parser.  Route to the appropriate handler for the tag.
        * @param t HTML.Tag
        * @param a  MutableAttributeSet
        * @param pos int
        */
        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos)
        {
            XMLParserDelegator hparser = null;
            MutableAttributeSet a2 = null;
            TagAction action = null;
            boolean checkrest = true;
    //System.err.println("in handlesimple tag: "+t+"  attr: "+a+" pos: "+pos);
    //showAttributes(System.err,a);

            // th tag will get here if the dtd does not support it, change to td tag to prevent building
            // a table as tr xxxxxx /tr
            if (t == HTML.Tag.TH)
            {
                if ((XMLDocument.this.getParser() instanceof XMLParserDelegator))
                {
                    // WSS make sure table tag is supported by the dtd
                    hparser = (XMLParserDelegator)XMLDocument.this.getParser();
                    if (hparser.elementExists("table"))  // table is not supported
                    {
                        // force it to be a td
                        if (a.getAttribute(HTML.Attribute.ENDTAG)!=null) {// has end tag
                            handleEndTag(HTML.Tag.TD, pos); }
                        else {
                            handleStartTag(HTML.Tag.TD, a, pos); }
                        checkrest=false;//return;
                    }
                }
            }

            if (!invalidTags.empty() && checkrest)  // <br> may have been in an invalid location
            {
                // if br was the invalid tag, pop and return.. it may have been inside another invalid
                // so allow that one to go thru.. <h3><p>mmm<br>nnn</p></h3>
                if (invalidTags.peek().toString().equals(t.toString()))
                {
                    invalidTags.pop();  // remove top
                    checkrest=false;//return;
                }
            }

            // if editor.setText() is called with an error, the dtd may not yet be
            // set and the parser will not be the XMLParserDelegator
            if (checkrest && (XMLDocument.this.getParser() instanceof XMLParserDelegator)) {
                // WSS make sure this tag is supported by the dtd
                hparser = (XMLParserDelegator)XMLDocument.this.getParser();
                if (hparser.elementExists(t.toString())) { // tag is not supported?
                    // WSS check if there are any unsupported attributes
                    a2 = checkAttributes(t,a);

                    if (!verifyXMLOnly) { // if we are only verifying the xml, do not actually insert it
                        // from here down is base class code
                        if (midInsert && !inBody) {
                            //return;
                        }
                        else {
                            action = (TagAction) tagMap.get(t);
                            if (action != null) {
                                action.start(t, a2);
                                action.end(t);
                            }
                            else if (getPreservesUnknownTags()) {
                                // unknown tag, only add if should preserve it.
                                addSpecialElement(t, a2);
                            }
                        }
                    }
                }
            }
        }

        /**
         * This is invoked after the stream has been parsed, but before
         * <code>flush</code>. <code>eol</code> will be one of \n, \r
         * or \r\n, which ever is encountered the most in parsing the
         * stream.
         * @param eol String
         * @since 1.3
         */
        public void handleEndOfLineString(String eol)
        {
            if (emptyDocument && eol != null) {
                putProperty(DefaultEditorKit.EndOfLineStringProperty, eol);
            }
        }

        // ---- tag handling support ------------------------------

        /**
         * Registers a handler for the given tag.  By default
         * all of the well-known tags will have been registered.
         * This can be used to change the handling of a particular
         * tag or to add support for custom tags.
         * @param t HTML.Tag
         * @param a TagAction
         */
        protected void registerTag(HTML.Tag t, TagAction a) {
            tagMap.put(t, a);
        }

        /**
         * An action to be performed in response to parsing a tag.  This allows customization
         * of how each tag is handled and avoids a large switch statement.
         */
        private class TagAction
        {
            /**
             * Called when a start tag is seen for the type of tag this action was registered
             * to.  The tag argument indicates the actual tag for those actions that are shared across
             * many tags.  By default this does nothing and completely ignores the tag.
             * @param t HTML.Tag
             * @param a MutableAttributeSet
            */
            public void start(HTML.Tag t, MutableAttributeSet a) {
                a=null;
                t=null; // jtest req
            }

            /**
             * Called when an end tag is seen for the type of tag this action was registered
             * to.  The tag argument indicates the actual tag for those actions that are shared across
             * many tags.  By default this does nothing and completely ignores the tag.
             * @param t HTML.Tag
             */
            public void end(HTML.Tag t) {
                t=null; // jtest req
            }

        }

        private class BlockAction extends TagAction {
            public void start(HTML.Tag t, MutableAttributeSet attr) {
                blockOpen(t, attr);
            }

            public void end(HTML.Tag t) {
                blockClose(t);
            }
        }

        private class ParagraphAction extends BlockAction {

            public void start(HTML.Tag t, MutableAttributeSet a) {
                super.start(t, a);
                inParagraph = true;
            }

            public void end(HTML.Tag t) {
                super.end(t);
                inParagraph = false;
            }
        }

        private class SpecialAction extends TagAction
        {
            public void start(HTML.Tag t, MutableAttributeSet a) {
                addSpecialElement(t, a);
            }
        }

    /*v11*/
        private class PreAction extends BlockAction
        {
            public void start(HTML.Tag t, MutableAttributeSet attr)
            {
                inPre = true;
                blockOpen(t, attr);
                attr.addAttribute(CSS.Attribute.WHITE_SPACE, "pre");
                blockOpen(HTML.Tag.IMPLIED, attr);
            }

            public void end(HTML.Tag t)
            {
                blockClose(HTML.Tag.IMPLIED);
                // set inPre to false after closing, so that if a newline
                // is added it won't generate a blockOpen.
                inPre = false;
                blockClose(t);
            }
        }
    /*v11
        private class CharacterAction extends TagAction
        {
            public void start(HTML.Tag t, MutableAttributeSet attr)
            {
                pushCharacterStyle();
                if (!foundInsertTag)
                {
                    // Note that the third argument should really be based off
                    // inParagraph and impliedP. If we're wrong (that is
                    // insertTagDepthDelta shouldn't be changed), we'll end up
                    // removing an extra EndSpec, which won't matter anyway.
                    boolean insert = canInsertTag(t, attr, false);
                    if (foundInsertTag)
                    {
                        if (!inParagraph) {
                            inParagraph = true;
                            impliedP = true;
                        }
                    }
                    if (!insert) {
                        return;
                    }
                }
                if (attr.isDefined(IMPLIED)) {
                    attr.removeAttribute(IMPLIED);
                }
                charAttr.addAttribute(t, attr.copyAttributes());
            }

            public void end(HTML.Tag t) {
                popCharacterStyle();
            }
        }*/

        /**
         * Provides conversion of HTML tag/attribute
         * mappings that have a corresponding StyleConstants
         * and CSS mapping.  The conversion is to CSS attributes.
         */
        private class ConvertAction extends TagAction
        {
            public void start(HTML.Tag t, MutableAttributeSet attr)
            {
                StyleSheet sheet = null;
                boolean checkrest = true;
                pushCharacterStyle();
                if (!foundInsertTag)
                {
                    // Note that the third argument should really be based off
                    // inParagraph and impliedP. If we're wrong (that is
                    // insertTagDepthDelta shouldn't be changed), we'll end up
                    // removing an extra EndSpec, which won't matter anyway.
                    boolean insert = canInsertTag(t, attr, false);
                    if (foundInsertTag) {
                        if (!inParagraph) {
                            inParagraph = true;
                            impliedP = true;
                        }
                    }
                    if (!insert) {
                        checkrest = false;//return;
                    }
                }
                if (checkrest) {
                    if (attr.isDefined(IMPLIED)) {
                        attr.removeAttribute(IMPLIED);
                    }
                    // We also need to add attr, otherwise we lose custom
                    // attributes, including class/id for style lookups, and
                    // further confuse style lookup (doesn't have tag).
                    charAttr.addAttribute(t, attr.copyAttributes());
                    sheet = getStyleSheet();
                    if (t == HTML.Tag.B) {
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.FONT_WEIGHT, "bold");
                    } else if (t == HTML.Tag.STRONG) {//use this to allow StyleConstants.isX & .setX() to work
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.FONT_WEIGHT, "bold");
                    } else if (t == HTML.Tag.I) {
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.FONT_STYLE, "italic");
                    } else if (t == HTML.Tag.EM) { //use this to allow StyleConstants.isX & .setX() to work
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.FONT_STYLE, "italic");
                    } else if (t == HTML.Tag.U) {
                        Object v = charAttr.getAttribute(CSS.Attribute.TEXT_DECORATION);
                        String value = "underline";
                        value = (v != null) ? value + "," + v.toString() : value;
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.TEXT_DECORATION, value);
                    } else if (t == HTML.Tag.STRIKE) {
                        Object v = charAttr.getAttribute(CSS.Attribute.TEXT_DECORATION);
                        String value = "line-through";
                        value = (v != null) ? value + "," + v.toString() : value;
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.TEXT_DECORATION, value);
                    } else if (t == HTML.Tag.SUP) {
                        Object v = charAttr.getAttribute(CSS.Attribute.VERTICAL_ALIGN);
                        String value = "sup";
                        value = (v != null) ? value + "," + v.toString() : value;
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.VERTICAL_ALIGN, value);
                    } else if (t == HTML.Tag.SUB) {
                        Object v = charAttr.getAttribute(CSS.Attribute.VERTICAL_ALIGN);
                        String value = "sub";
                        value = (v != null) ? value + "," + v.toString() : value;
                        sheet.addCSSAttribute(charAttr, CSS.Attribute.VERTICAL_ALIGN, value);
                    } else if (t == HTML.Tag.FONT) {
                        String color = (String) attr.getAttribute(HTML.Attribute.COLOR);
                        String face = (String) attr.getAttribute(HTML.Attribute.FACE);
                        String size = (String) attr.getAttribute(HTML.Attribute.SIZE);
                        if (color != null) {
                            sheet.addCSSAttribute(charAttr, CSS.Attribute.COLOR, color);
                        }
                        if (face != null) {
                            sheet.addCSSAttribute(charAttr, CSS.Attribute.FONT_FAMILY, face);
                        }
                        if (size != null) {
                            sheet.addCSSAttributeFromHTML(charAttr, CSS.Attribute.FONT_SIZE, size);
                        }
                    }
                }
            }

            public void end(HTML.Tag t)
            {
                popCharacterStyle();
            }
        }

        // --- utility methods used by the reader ------------------

        /**
         * Pushes the current character style on a stack in preparation
         * for forming a new nested character style.
         */
        protected void pushCharacterStyle()
        {
            charAttrStack.push(charAttr.copyAttributes());
        }

        /**
         * Pops a previously pushed character style off the stack
         * to return to a previous style.
         */
        protected void popCharacterStyle()
        {
            if (!charAttrStack.empty()) {
                charAttr = (MutableAttributeSet) charAttrStack.peek();
                charAttrStack.pop();
            }
        }

        /**
         * Adds the given content that was encountered in a
         * PRE element.  This synthesizes lines to hold the
         * runs of text, and makes calls to addContent to
         * actually add the text.
         * @param data
         */
        protected void preContent(char[] data)
        {
            int last = 0;
            for (int i = 0; i < data.length; i++) {
                if (data[i] == '\n') {
                    MutableAttributeSet a = null;
                    addContent(data, last, i - last + 1);
                    blockClose(HTML.Tag.IMPLIED);
                    a = new SimpleAttributeSet();
                    a.addAttribute(CSS.Attribute.WHITE_SPACE, "pre");
                    blockOpen(HTML.Tag.IMPLIED, a);
                    last = i + 1;
                }
            }
            if (last < data.length) {
                addContent(data, last, data.length - last);
            }
        }/*v11*/

        /**
         * Adds an instruction to the parse buffer to create a
         * block element with the given attributes.
         *
         * @param t
         * @param attr
         */
        protected void blockOpen(HTML.Tag t, MutableAttributeSet attr)
        {
            ElementSpec es = null;
            if (impliedP) {
                //impliedP = false;                                        /*ibm@28478*/
                //inParagraph = false;                                     /*ibm@28478*/
                blockClose(HTML.Tag.IMPLIED);
            }

            inBlock++;

            if (canInsertTag(t, attr, true)) {
                if (attr.isDefined(IMPLIED)) {
                    attr.removeAttribute(IMPLIED);
                }
                lastWasNewline = false;
                attr.addAttribute(StyleConstants.NameAttribute, t);
                es = new ElementSpec(attr.copyAttributes(), ElementSpec.StartTagType);
                parseBuffer.addElement(es);
            }
        }

        /**
         * Adds an instruction to the parse buffer to close out
         * a block element of the given type.
         *
         * @param t
         */
        protected void blockClose(HTML.Tag t)
        {
            ElementSpec prev = null;
            ElementSpec es = null;

            inBlock--;

            if (foundInsertTag) {
                // Add a new line, if the last character wasn't one. This is
                                                                     /*start ibm@28478*/
                // needed for proper positioning of the cursor. addContent
                // with true will force an implied paragraph to be generated if
                // there isn't one. This may result in a rather bogus structure
                // (perhaps a table with a child paragraph), but the paragraph
                // is needed for proper positioning and display.
                                                                       /*end ibm@28478*/

                if(!lastWasNewline) {
                    //addContent(NEWLINE, 0, 1, false);                        /*ibm@28478*/
                    addContent(NEWLINE, 0, 1, true);                           /*ibm@28478*/
                    lastWasNewline = true;
                }

                if (impliedP) {
                    impliedP = false;
                    inParagraph = false;
                    if (t != HTML.Tag.IMPLIED) {                           /*ibm@28478*/
                        blockClose(HTML.Tag.IMPLIED);
                    }                                                      /*ibm@28478*/
                }
                // an open/close with no content will be removed, so we
                // add a space of content to keep the element being formed.
                prev = (parseBuffer.size() > 0) ? (ElementSpec) parseBuffer.lastElement() : null;
                if (prev != null && prev.getType() == ElementSpec.StartTagType)
                {
                    char[] one = new char[1];
                    one[0] = ' ';
                    addContent(one, 0, 1);
                }
                es = new ElementSpec(null, ElementSpec.EndTagType);
                parseBuffer.addElement(es);
            }
        }

        /**
         * Adds some text with the current character attributes.
         *
         * @param data char[] embedded the attributes of an embedded object.
         * @param offs int
         * @param length int
         */
        protected void addContent(char[] data, int offs, int length) {
            addContent(data, offs, length, true,-1);
        }
        /**
         * Adds some text with the current character attributes.
         *
         * @param data char[] embedded the attributes of an embedded object.
         * @param offs int
         * @param length int
         * @param pos int
         */
        protected void addContent(char[] data, int offs, int length, int pos) {
            addContent(data, offs, length, true, pos);
        }
        /**
         * Adds some text with the current character attributes.
         *
         * @param data char[] embedded the attributes of an embedded object.
         * @param offs int
         * @param length int
         * @param generateImpliedPIfNecessary boolean
         */
        protected void addContent(char[] data, int offs, int length,
            boolean generateImpliedPIfNecessary)
        {
            addContent(data,offs, length,generateImpliedPIfNecessary, -1);
        }
        /**
         * Adds some text with the current character attributes.
         *
         * @param data char[] embedded the attributes of an embedded object.
         * @param offs int
         * @param length int
         * @param generateImpliedPIfNecessary boolean
         * @param pos int
         */
        protected void addContent(char[] data, int offs, int length,
            boolean generateImpliedPIfNecessary, int pos)
        {
            AttributeSet a = null;
            ElementSpec es = null;
            if (foundInsertTag) {
                if (generateImpliedPIfNecessary && (! inParagraph) && (! inPre))
                {
                    boolean usePtag = false;
                    ElementSpec prev = (parseBuffer.size() > 0) ?(ElementSpec) parseBuffer.lastElement() : null;

        //System.err.println("addContent: data: "+(new String(data)));
        //System.err.println("***prev spec: "+prev);
        //showAttributes(System.err,prev.getAttributes());

                    // this is used to prevent text in the editor without being enclosed in <p>..</p>
                    // without this, the text is enclosed in <p-implied>..</p-implied>
                    // a problem for selection calculations and html manipulation
                    if (prev!=null)
                    {
                        Stack<ElementSpec> testStk = new Stack<ElementSpec>();
                        for(int i=0; i<parseBuffer.size(); i++)
                        {
                            es=(ElementSpec) parseBuffer.elementAt(i);
                            if (es.getType() == ElementSpec.StartTagType) {
                                testStk.push(es);
                            }
                            else {
                                if (es.getType() == ElementSpec.EndTagType && !testStk.empty()) {
                                    testStk.pop(); }
                            }
                        }
                        if (!testStk.empty())
                        {
                            es=(ElementSpec)testStk.pop();
                            if ((es.getType() == ElementSpec.StartTagType)&& es.getAttributes().getAttribute(StyleConstants.NameAttribute)
                                == HTML.Tag.BODY) {
                                usePtag = true;
                            }

                        }
                        testStk.clear();
                        testStk=null;
                    }

                    if (usePtag)
                    {
        //System.err.println("***********Forcing p tag data: <"+(new String(data))+"> "+pos);
                        fireXMLError("Text found outside of structure. Paragraph was added",pos);
                        blockOpen(HTML.Tag.P, new SimpleAttributeSet());
                        // v11 if <pre> is in data but not in dtd extra whitespace must be eliminated
                        // when parser encounters a <p> it strips all new lines and extra white space
                        // from the data.. it must be done here because we are forcing a <p> tag
                        for (int i=0; i<data.length; i++)
                        {
                            if (data[i]=='\n') {
                                data[i] = ' '; }
                        }
                    }
                    else
                    {
                        blockOpen(HTML.Tag.IMPLIED, new SimpleAttributeSet());
                    }
                    impliedP = true;
                    inParagraph = true;
                }
                charAttr.addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
                a = charAttr.copyAttributes();
                es = new ElementSpec(a, ElementSpec.ContentType, data, offs, length);
                parseBuffer.addElement(es);

                if (parseBuffer.size() > threshold) {
                    try {
                        flushBuffer(false);
                    } catch (BadLocationException ble) {
                        System.out.println(ble.getMessage());  // jtest req
                    }
                }
                if(length > 0) {
                    lastWasNewline = (data[offs + length - 1] == '\n');
                }
            }
        }

        /**
         * Adds content that is basically specified entirely
         * in the attribute set.
         * @param t HTML.Tag
         * @param a MutableAttributeSet
         */
        protected void addSpecialElement(HTML.Tag t, MutableAttributeSet a)
        {
            ElementSpec es = null;
            char[] one = new char[1];
            one[0] = ' ';
            if ((! inParagraph) && (! inPre))
            {
                blockOpen(HTML.Tag.IMPLIED, new SimpleAttributeSet());
                inParagraph = true;
                impliedP = true;
            }
            if (canInsertTag(t, a, true)) {
                if (a.isDefined(IMPLIED)) {
                    a.removeAttribute(IMPLIED);
                }
                a.addAttributes(charAttr);
                a.addAttribute(StyleConstants.NameAttribute, t);
                es = new ElementSpec(a.copyAttributes(), ElementSpec.ContentType, one, 0, 1);
                parseBuffer.addElement(es);
            }
        }

        /**
         * Flushes the current parse buffer into the document.
         * @param endOfStream true if there is no more content to parser
         */
        void flushBuffer(boolean endOfStream) throws BadLocationException
        {
            int oldLength = 0;
            int size = 0;
            ElementSpec[] spec = null;
            if (verifyXMLOnly)  // if we are only verifying the xml, do not actually insert it
            {
                parseBuffer.removeAllElements();
                //return;
            }else{
                oldLength = XMLDocument.this.getLength();
                size = parseBuffer.size();
                if (endOfStream && (insertTag != null || insertAfterImplied) && size > 0)
                {
                    adjustEndSpecsForPartialInsert();
                    size = parseBuffer.size();
                }
                spec = new ElementSpec[size];
                parseBuffer.copyInto(spec);

                if (oldLength == 0 && (insertTag == null && !insertAfterImplied)) {
                    create(spec);
                } else {
                    insert(offset, spec);
                }
                parseBuffer.removeAllElements();
                offset += XMLDocument.this.getLength() - oldLength;
                flushCount++;
            }
        }

        /**
         * This will be invoked for the last flush, if <code>insertTag</code>
         * is non null.
         */
        private void adjustEndSpecsForPartialInsert()
        {
            int size = parseBuffer.size();
            if (insertTagDepthDelta < 0)
            {
                // When inserting via an insertTag, the depths (of the tree
                // being read in, and existing hiearchy) may not match up.
                // This attemps to clean it up.
                int removeCounter = insertTagDepthDelta;
                while (removeCounter < 0 && size >= 0 &&
                    ((ElementSpec)parseBuffer.elementAt(size - 1)).getType() == ElementSpec.EndTagType)
                {
                    parseBuffer.removeElementAt(--size);
                    removeCounter++;
                }
            }
            if (flushCount == 0 && (!insertAfterImplied || !wantsTrailingNewline))
            {
                // If this starts with content (or popDepth > 0 &&
                // pushDepth > 0) and ends with EndTagTypes, make sure
                // the last content isn't a \n, otherwise will end up with
                // an extra \n in the middle of content.
                int index = 0;
                int cCount = 0;
                int cStart = 0;
                if (pushDepth > 0) {
                    if (((ElementSpec)parseBuffer.elementAt(0)).getType() ==
                        ElementSpec.ContentType)
                    {
                        index++;
                    }
                }
                index += (popDepth + pushDepth);
                cStart = index;
                while (index < size && ((ElementSpec)parseBuffer.elementAt
                        (index)).getType() == ElementSpec.ContentType) {
                    index++;
                    cCount++;
                }
                if (cCount > 1) {
                    while (index < size && ((ElementSpec)parseBuffer.elementAt(index)).getType() == ElementSpec.EndTagType)
                    {
                        index++;
                    }
                    if (index == size) {
                        char[] lastText = ((ElementSpec)parseBuffer.elementAt(cStart + cCount - 1)).getArray();
                        if (lastText.length == 1 && lastText[0] == NEWLINE[0]){
                            index = cStart + cCount - 1;
                            while (size > index) {
                                parseBuffer.removeElementAt(--size);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Returns true if can insert starting at <code>t</code>. This
         * will return false if the insert tag is set, and hasn't been found
         * yet.
         */
        private boolean canInsertTag(HTML.Tag t, AttributeSet attr, boolean isBlockTag)
        {
            boolean ok = true;
            if (!foundInsertTag)
            {
                if ((insertTag != null && !isInsertTag(t)) ||
                    (insertAfterImplied && (attr == null || attr.isDefined(IMPLIED))))
                {
                    ok=false;//return false;
                }
                else {
                    foundInsertTag(isBlockTag);
                    if (!insertInsertTag) {
                        ok=false;//return false;
                    }
                }
            }

            return ok;//true;
        }

        private boolean isInsertTag(HTML.Tag tag) {
            return (insertTag == tag);
        }

        private void foundInsertTag(boolean isBlockTag)
        {
    //System.err.println("foundInsertTag entered isBlockTag: "+isBlockTag+" insertAfterImplied: "+insertAfterImplied+
    //" popDepth: "+popDepth+" pushDepth: "+pushDepth);
            foundInsertTag = true;
            if (!insertAfterImplied && (popDepth > 0 || pushDepth > 0))
            {
                try {
                    if (offset ==0 && insertTag==HTML.Tag.TR){
                        // when inserting a table row at offset=0 don't do a joinP (WSS)
                    }
                    else
                    if (offset == 0 || !getText(offset - 1, 1).equals(NEWLINE_STR)) {
                        ElementSpec es = null;
                        // Need to insert a newline.
                        AttributeSet newAttrs = null;
                        boolean joinP = true;

                        if (offset != 0) {
                            // Determine if we can use JoinPrevious, we can't
                            // if the Element has some attributes that are
                            // not meant to be duplicated.
                            Element charElement = getCharacterElement(offset - 1);
                            AttributeSet attrs = charElement.getAttributes();

                            if (attrs.isDefined(StyleConstants.ComposedTextAttribute)) {
                                joinP = false;
                            }
                            else {
                                Object name = attrs.getAttribute(StyleConstants.NameAttribute);
                                if (name instanceof HTML.Tag)
                                {
                                    HTML.Tag tag = (HTML.Tag)name;
                                    if (tag == HTML.Tag.IMG ||
                                        tag == HTML.Tag.HR ||
                                        tag == HTML.Tag.COMMENT ||
                                        (tag instanceof HTML.UnknownTag)) {
                                        joinP = false;
                                    }
                                }
                            }
                        }
                        if (!joinP) {
                            // If not joining with the previous element, be
                            // sure and set the name (otherwise it will be
                            // inherited).
                            newAttrs = new SimpleAttributeSet();
                            ((SimpleAttributeSet)newAttrs).addAttribute(StyleConstants.NameAttribute, HTML.Tag.CONTENT);
                        }
                        es = new ElementSpec(newAttrs, ElementSpec.ContentType, NEWLINE, 0, NEWLINE.length);
                        if (joinP) {
                            es.setDirection(ElementSpec.JoinPreviousDirection);
                        }
                        parseBuffer.addElement(es);
                    }
                } catch (BadLocationException ble) {
                    System.out.println("foundInsertTag exception "+ble.getMessage());  // jtest req
                }
            }
            // pops
            for (int counter = 0; counter < popDepth; counter++) {
                parseBuffer.addElement(new ElementSpec(null, ElementSpec.EndTagType));
            }
            // pushes
            for (int counter = 0; counter < pushDepth; counter++) {
                ElementSpec es = new ElementSpec(null, ElementSpec.StartTagType);
                es.setDirection(ElementSpec.JoinNextDirection);
                parseBuffer.addElement(es);
            }
            insertTagDepthDelta = depthTo(Math.max(0, offset - 1)) - popDepth + pushDepth - inBlock;
            if (isBlockTag)
            {
                // A start spec will be added (for this tag), so we account
                // for it here.
                insertTagDepthDelta++;
            }
            else {
                // An implied paragraph close (end spec) is going to be added,
                // so we account for it here.
                insertTagDepthDelta--;
            }
        }

        private Stack<String> invalidTags = new Stack<String>();  // used to prevent tags that are not supported by the dtd
                                            // when found in an invalid location, like nested lists or br
                                            // outside of structure

        /** Number of times <code>flushBuffer</code> has been invoked. */
        private int flushCount=0;
        /** If true, behavior is similiar to insertTag, but instead of
         * waiting for insertTag will wait for first Element without
         * an 'implied' attribute and begin inserting then. */
        private boolean insertAfterImplied=false;
        /** This is only used if insertAfterImplied is true. If false, only
         * inserting content, and there is a trailing newline it is removed. */
        private boolean wantsTrailingNewline=false;
        private int threshold=0;
        private int offset=0;
        private boolean inParagraph = false;
        private boolean impliedP = false;
        private boolean inPre = false;
        private boolean lastWasNewline = true;
        /** True if (!emptyDocument && insertTag == null), this is used so
         * much it is cached. */
        private boolean midInsert=false;
        /** True when the body has been encountered. */
        private boolean inBody=false;
        /** If non null, gives parent Tag that insert is to happen at. */
        private HTML.Tag insertTag=null;
        /** If true, the insertTag is inserted, otherwise elements after
         * the insertTag is found are inserted. */
        private boolean insertInsertTag=false;
        /** Set to true when insertTag has been found. */
        private boolean foundInsertTag=false;
        /** When foundInsertTag is set to true, this will be updated to
         * reflect the delta between the two structures. That is, it
         * will be the depth the inserts are happening at minus the
         * depth of the tags being passed in. A value of 0 (the common
         * case) indicates the structures match, a value greater than 0 indicates
         * the insert is happening at a deeper depth than the stream is
         * parsing, and a value less than 0 indicates the insert is happening earlier
         * in the tree that the parser thinks and that we will need to remove
         * EndTagType specs in the flushBuffer method.
         */
        private int insertTagDepthDelta=0;
        /** How many parents to ascend before insert new elements. */
        private int popDepth=0;
        /** How many parents to descend (relative to popDepth) before
         * inserting. */
        private int pushDepth=0;

        /** True if inserting into an empty document. */
        private boolean emptyDocument=false;

        private Vector<ElementSpec> parseBuffer = new Vector<ElementSpec>();    // Vector<ElementSpec>
        private MutableAttributeSet charAttr = new SimpleAttributeSet();
        private Stack<AttributeSet> charAttrStack = new Stack<AttributeSet>();
        private Hashtable<Tag, TagAction> tagMap=null;
        private int inBlock = 0;
    }

/*    static boolean is1dot2;

    static {
        is1dot2 = false;
        try {
            // Test if method introduced in 1.2 is available.
            java.lang.reflect.Method m = Toolkit.class.getMethod("getMaximumCursorColors", null);
            is1dot2 = (m != null);
        } catch (NoSuchMethodException e) {
            is1dot2 = false;
        }
    }
    static boolean isComposedTextElement(Element elem) {
        AttributeSet as = elem.getAttributes();
        return isComposedTextAttributeDefined(as);
    }

    static boolean isComposedTextAttributeDefined(AttributeSet as) {
        return ((as != null) &&
                (as.isDefined(StyleConstants.ComposedTextAttribute)));
    }
*/
    /*
     * Updates document structure as a result of text removal.
     *
     * @param chng a description of the document change
     */
//    protected void removeUpdate(DefaultDocumentEvent chng) {
//System.out.println("\n\nin MY XMLDocument:removeUpdate!! ");
//System.out.println("buffer is: "+buffer);

//System.out.println("remove offset "+chng.getOffset()+" len: "+chng.getLength());
//        super.removeUpdate(chng);
        //base handles this -> buffer.remove(chng.getOffset(), chng.getLength(), chng);
//    }

    private boolean deleteTblCell = false; //v111
    void setDeleteTableCell(boolean b) { deleteTblCell=b;} //v111

    /******************************************************************************
    * This is used to remove cell content from a table.  Starting and ending with
    * rows and columns specified. v111
    * offs may be at the start of the table or in the table
    * offs+len may be in the table or after the table
    */
    private void removeCellContent(Element table, int leftTrId, int rightTrId,
        int leftTdId, int rightTdId, int offs, int len) throws BadLocationException
    {
//System.err.print("removeCellContent: entered offs: "+offs+" len: "+len+" table: "+table);
//System.err.println("leftTrId: "+leftTrId+" rightTrId: "+rightTrId+" leftTdId: "+leftTdId+" rightTdId: "+rightTdId);

        Element leftTd = null;
        Element rightTd = null;
        boolean endsInCell = false;
        int rightTdDelLen =0;

        if (len>0) {
            // len=0 could happen if press del with selection starting at end of table
            leftTd = table.getElement(leftTrId).getElement(leftTdId);
            rightTd = table.getElement(rightTrId).getElement(rightTdId);
    //System.err.print("removeCellContent: leftTd: "+leftTd+" rightTd: "+rightTd);

            // must know if starting or ending in a cell.. makes a difference if you remove the contents before
            // or after the start or end of selection
            endsInCell = offs+len<table.getEndOffset()-1;

            rightTdDelLen = Math.min((offs+len)-rightTd.getStartOffset(),
                rightTd.getEndOffset()-rightTd.getStartOffset()-1);
    //System.err.println("************* rightTdDelLen: "+rightTdDelLen);

            // do not delete cell's term new line, table structure must be maintained
            rowloop:for(int r=leftTrId; r<=rightTrId; r++) // delete upto and including row with offset
            {
                Element tr = table.getElement(r); // get row in the table
                for (int c=0; c<tr.getElementCount(); c++) // delete contents of every cell up to tdId
                {
                    Element curElem = null;
                    int delLen = 0;
                    if (r==rightTrId && c>rightTdId) { // don't go past cell with offset+len
                        break rowloop; }
                    if (r==leftTrId && c<leftTdId) { // find first cell
                        continue; }
                    curElem = tr.getElement(c); // get cell in the row
    //System.err.print("removeCellContent: deleting curelem: "+curElem);
                    // remove contents
                    delLen = curElem.getEndOffset()-curElem.getStartOffset()-1;
                    // if at first cell, delete from offset to end of cell
                    if (r==leftTrId && c==leftTdId) // first cell
                    {
    //System.err.print("in first cell "+curElem);
                        // first cell may also be the last cell
                        // so delete from start to end of selection
                        if (r==rightTrId && c==rightTdId && endsInCell)  // last cell
                        {   //System.err.println("and in last cell*** ");
                            delLen=rightTdDelLen;
                        }
                        else {
                            delLen = curElem.getEndOffset()-offs-1;
                        }
                    }
                    else
                    {
                        // delete from start of cell
                        offs = curElem.getStartOffset();
                        // if at end table or selection use calculated length
                        if (r==rightTrId && c==rightTdId)  // last cell
                        {   //System.err.print("in last cell "+curElem);
                            delLen=rightTdDelLen;
                        }
                    }

                    // remove contents of each cell up to len
    //System.err.println("removeCellContent: deleting offs "+offs+" delLen: "+delLen);
                    remove(offs,delLen);
    //System.err.println("removeCellContent: td elem after remove "+tr.getElement(c));
                }
            }
        }
    }

    /******************************************************************************
    * This is used when the selection starts in a table and ends after the table.
    * It may only be the entire table.  offs+len will be outside of table when entire
    * table has been selected.
    */
    private void removeTableStartContent(Element leftTd, int offs, int len) throws BadLocationException
    {
        Element tr = leftTd.getParentElement();
        int tdId = tr.getElementIndex(offs);
        Element table = tr.getParentElement();
        int trId = table.getElementIndex(offs);
        boolean removeCompleteRightElem = false;
//System.err.println("removeTableStartContent: entered offs "+offs+" len: "+len);

        Element rightElem = getParagraphElement(offs+len);
        if (rightElem.getEndOffset()==offs+len || // the entire post elem is to be deleted
            rightElem.getEndOffset()-1==offs+len) // term new line not in selection
        {
            removeCompleteRightElem = true;
//System.err.println("ENTIRE post elem is to be removed");
        }
        else  // if elem is p-implied that means it is a subset of other structure
        {
            if (rightElem.getName().equals(HTML.Tag.IMPLIED.toString()))
            {
                // but if entire parent will be deleted, allow deletion
                Element parent = rightElem.getParentElement();
                while(!parent.getName().equals(HTML.Tag.BODY.toString()))
                {
                    if (parent.getName().equals(HTML.Tag.OL.toString()) ||
                        parent.getName().equals(HTML.Tag.UL.toString()) ||
                        parent.getName().equals(HTML.Tag.PRE.toString()) ||
                        parent.getName().equals(HTML.Tag.TABLE.toString())) {
                        break;
                    }
                    parent =parent.getParentElement();
                }
                if (parent.getEndOffset()==offs+len || // the entire parent elem is to be deleted
                    parent.getEndOffset()-1==offs+len) // term new line not in selection
                {
                    removeCompleteRightElem = true;
//System.err.print("ENTIRE parent elem will be removed parent "+parent);
                }
//              else System.err.print("ENTIRE parent elem will not be removed!! parent "+parent);
            }
        }

        len -=(table.getEndOffset()-offs);

        removeCellContent(table, trId, table.getElementCount()-1, tdId, tr.getElementCount()-1, offs,
            table.getEndOffset()-offs-1);

        offs=table.getEndOffset();

        if (removeCompleteRightElem &&
            offs+len<getLength()) // do not remove doc trailing new line
        {
//System.err.println("REMOVE trailing element!!");
            len++;
            offs--;
        }

        if (len>0) // remove whatever is left
        {
//System.err.println("removeTableStartContent: deleting leftover offs "+offs+" len: "+len);
            remove(offs,len);
        }
    }

    /******************************************************************************
    * This is used when the selection starts before a table and ends in the table.
    * Must not remove term new line of preceding structure unless all of the preceding structure
    * is to be removed. v111
    */
    private void removeTableEndContent(Element rightTd, int offs, int len) throws BadLocationException
    {
//System.out.print("removeTableEndContent: offs: "+offs+" len: "+len+" deletion is ending in a TD: "+rightTd);
        Element tr = rightTd.getParentElement();
        int tdId = tr.getElementIndex(offs+len);
        Element table = tr.getParentElement();
        int trId = table.getElementIndex(offs+len);
//System.err.print("removeTableEndContent: right tdid: "+tdId+" trid: "+trId+" tr: "+tr);
//System.err.print("removeTableEndContent: right table: "+table);

        // do not delete cell's term new line, table structure must be maintained
        // remove contents before the table
        Element leftElem = getParagraphElement(offs);
        // if offs+len includes leftElem term new line but is not entire leftElem
        // must not delete leftElem's term new line
//System.err.print("prev elem "+leftElem);
        int prevDelLen = table.getStartOffset()-offs;
        len-=prevDelLen;

        if (leftElem.getStartOffset()!=offs) // the entire prev elem is not deleted
        {
            prevDelLen--;  // don't remove term character
//System.err.println("ENTIRE elem not removed, so avoid term new line");
        }
        else  // if elem is p-implied that means it is a subset of other structure, and will
        {   // not be deleted, the table cell will try to merge.. don't do this
            if (leftElem.getName().equals(HTML.Tag.IMPLIED.toString()))
            {
                // but if entire parent will be deleted, allow deletion
                Element parent = leftElem.getParentElement();
                while(!parent.getName().equals(HTML.Tag.BODY.toString()))
                {
                    if (parent.getName().equals(HTML.Tag.OL.toString()) ||
                        parent.getName().equals(HTML.Tag.UL.toString()) ||
                        parent.getName().equals(HTML.Tag.PRE.toString()) ||
                        parent.getName().equals(HTML.Tag.TABLE.toString()))
                    {
                        break;
                    }
                    parent =parent.getParentElement();
                }
                if (parent.getStartOffset()!=offs) // the entire parent elem is not deleted
                {
                    prevDelLen--;  // don't remove term character
//System.err.println("ENTIRE elem would be removed, but it is PIMPLIED so avoid term new line");
                }
//              else System.err.print("ENTIRE parent elem will be removed!! parent "+parent);
            }
        }

//System.err.println("removeTableEndContent: deleting preceding content offs "+offs+" len: "+prevDelLen);
        // use super to prevent recursion problems.. this will be a problem
        // if nesting is allowed and code from this.remove is needed
        remove(offs,prevDelLen);
//System.err.print("removeTableEndContent: after del preceding - right table: "+table);

        if (len>0) { // len=0 possible if the only thing to be deleted was term new line of preceding structure
            offs=table.getStartOffset();

            removeCellContent(table, 0, trId, 0, tdId, offs, len);
        }
    }

    /******************************************************************************
    * This is used when the selection affects a table.
    * If in a table, must not delete cell boundaries unless entire table is deleted or
    * an action is deleting a row or column.
    * If tables are ever nested, this needs much more work
    * Returning false will allow the normal remove() to run. v111
    */
    private boolean removeTable(int offs, int len, Element leftTd, Element rightTd) throws BadLocationException
    {
        boolean ok = false;
//System.out.print("removeTable: ***** entered offs: "+offs+" len: "+len+" leftTd: "+leftTd+" rightTd: "+rightTd);
        // TD will be a parent of something besides content.. such as p-implied
        if (leftTd!=null && rightTd!=null) // selection started in a table, and is ending in one.. not necessarily the same one
        {
//System.out.print("removeTable: deletion is starting in a TD: "+leftTd+" and is ending in a TD: "+rightTd);
            Element rightTr = rightTd.getParentElement();
            int rightTdId = rightTr.getElementIndex(offs+len);
            Element rightTable = rightTr.getParentElement();
            int rightTrId = rightTable.getElementIndex(offs+len);
//System.err.print("removeTable: right tr: "+rightTr);
//System.err.print("removeTable: right table: "+rightTable);
            Element leftTr = leftTd.getParentElement();
            int leftTdId = leftTr.getElementIndex(offs);
            Element leftTable = leftTr.getParentElement();
            int leftTrId = leftTable.getElementIndex(offs);
//System.err.print("removeTable: left tr: "+leftTr);
//System.err.print("removeTable: left table: "+leftTable);

            // group edits for single undo
            boolean wasReplaceEdit = undoEditMgr.isReplaceEdit();
            if (!wasReplaceEdit) {
                undoEditMgr.setStartReplaceEdit("deletion");
            }

            deleteTblCell = true;  // prevent recursion
            if (leftTable==rightTable) // simplest case is if cells are in the same table
            {
                removeCellContent(leftTable, leftTrId, rightTrId, leftTdId, rightTdId, offs, len);
            }
            else  // not in the same table
            {
                // must account for selecting entire first table and deleting it
                if (offs>leftTable.getStartOffset())
                {
                    int leftTblDelLen = leftTable.getEndOffset()-offs-1; // amt to delete from left table
//System.err.println("removeTable: different tables!! offs: "+offs+" len: "+len+" lefttblEnd: "
//      +leftTable.getEndOffset()+" leftTblDelLen: "+leftTblDelLen);
                    // adjust len to account for removal of lefttable contents, newlines are in count
                    len -=leftTblDelLen;

//System.err.println("removeTable: different tables! removing end of left table");
                    // delete content of left table cells
                    removeTableStartContent(leftTd, offs, leftTblDelLen);

                    offs = leftTable.getEndOffset(); // start at end of this table
                    len--;  // account for leaving left table term new line
                }
//System.err.println("removeTable: different tables! removing content between tables and start of right table");

                // delete content of right table cells and any content before this table
                removeTableEndContent(rightTd, offs, len);
            }
            deleteTblCell = false;  // restore default

            if (!wasReplaceEdit) {
                undoEditMgr.setEndReplaceEdit();
            }

            //return true;
            ok=true;
        }
        else
        if (leftTd!=null) // starting in a table, but len exceeds the table
        {
//System.out.println("removeTable: deletion is starting in a TD: "+leftTd);
            Element tr = leftTd.getParentElement();
            Element table = tr.getParentElement();
            boolean wasReplaceEdit = false;
            // does remove cover the entire table?
            if (table.getStartOffset()==offs) { // entire table will be deleted
                ok=false;//return false;
            }else {
                // group edits for single undo
                wasReplaceEdit = undoEditMgr.isReplaceEdit();
                if (!wasReplaceEdit){
                    undoEditMgr.setStartReplaceEdit("deletion");
                }

                deleteTblCell = true;  // prevent recursion
                // if user presses backspace at structure after end of table with no selection
                // delete the table.  can't distinguish backspace or delete so don't do this
                // now.. a delete at end of table will do the same thing
    //          if (table.getEndOffset()-1==offs && len==1)
    //              remove(table.getStartOffset(),table.getEndOffset()-table.getStartOffset());
    //          else
                removeTableStartContent(leftTd, offs, len);
                deleteTblCell = false;  // restore default

                if (!wasReplaceEdit) {
                    undoEditMgr.setEndReplaceEdit();
                }

                //return true;
                ok = true;
            }
        }
        else
        if (rightTd!=null) // selection started in something besides a table, but is ending in one
        {
//System.out.print("removeTable: deletion is ending in a TD: "+rightTd);
            // group edits for single undo
            boolean wasReplaceEdit = undoEditMgr.isReplaceEdit();
            if (!wasReplaceEdit) {
                undoEditMgr.setStartReplaceEdit("deletion");
            }

            deleteTblCell = true;  // prevent recursion
            // if user presses delete at end of structure before table with no selection
            // delete the table. can't distinguish backspace or delete so don't do this
            // now.. a backspace at start of table will do the same thing
//          Element table = rightTd.getParentElement().getParentElement();
//          if (table.getStartOffset()==(offs+len) && len==1 && offs!=0)
//              remove(offs+len,table.getEndOffset()-table.getStartOffset());
//          else
            removeTableEndContent(rightTd, offs, len);
            deleteTblCell = false;  // restore default

            if (!wasReplaceEdit) {
                undoEditMgr.setEndReplaceEdit();
            }

            //return true;
            ok=true;
        }

        return ok;//false;  // allow normal remove to continue
    }

    /**
     * Removes some content from the document.
     * Removing content causes a write lock to be held while the
     * actual changes are taking place.  Observers are notified
     * of the change on the thread that called this method.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param offs the starting offset >= 0
     * @param len the number of characters to remove >= 0
     * @exception BadLocationException  the given remove position is not a valid
     *   position within the document
     * @see Document#remove
     */
    public void remove(int offs, int len) throws BadLocationException
    {
//System.out.println("in MY REMOVE!! curlen: "+getLength()+" offs: "+offs+" len: "+len);
        Element leftElem = null;
        Element rightElem = null;
        int newlineId=-1;
        boolean done = false;
        boolean checkForTabs = false;
        boolean wasReplaceEdit = false;

        if (len>0) { // len<=0 nothing to do.. JEditorPane.setText() will try to clear doc before adding new text
            leftElem = getParagraphElement(offs);
            rightElem = getParagraphElement(offs+len);
    //System.out.println("remove: left elem: "+leftElem+" right: "+rightElem);

            if (!deleteTblCell)  // user is deleting or cutting   v111
            {
    // if in a table, must not delete cell boundaries unless entire table is deleted or
    // an action is deleting a row or column
    // TD will be a parent of something besides content.. such as p-implied
                // if term new line is in selection, it will not be same TD, end offset of left
                // is start offset of right
                Element leftTd = leftElem.getParentElement();
                Element rightTd = null;
                while(leftTd!=null && !leftTd.getName().equals(HTML.Tag.TD.toString()) &&
                    !leftTd.getName().equals(HTML.Tag.TH.toString()))
                {
                    leftTd = leftTd.getParentElement();
                }
                rightTd = rightElem.getParentElement();
                while(rightTd!=null && !rightTd.getName().equals(HTML.Tag.TD.toString()) &&
                    !rightTd.getName().equals(HTML.Tag.TH.toString()))
                {
                    rightTd = rightTd.getParentElement();
                }

                if  (leftTd!=rightTd) // if both are null or are the same, default delete is ok
                {
                    if (removeTable(offs, len, leftTd, rightTd)) {
                        //return;
                        done = true;
                    }
                }
            } // v111 end user is deleting, not an action delete

            if (!done) {
                // must check if you are deleting a p inside a li and it is not the last element in li
                // if is last, there is no extra new line
                if(leftElem.getName().equals(HTML.Tag.IMPLIED.toString()) &&
                    rightElem.getName().equals(HTML.Tag.P.toString()) &&
                    (leftElem.getStartOffset()<offs))  // and left won't be deleted
                {
                    Element leftParent = leftElem.getParentElement();
                    Element rightParent = rightElem.getParentElement();
                    if (leftParent.getName().equals(rightParent.getName()) &&
                        leftParent.getName().equals("li"))  // both share an li parent
                    {
                        int rightId = rightParent.getElementIndex(offs+len);
                        if (rightId+1<rightParent.getElementCount())  // p is not the last element in li parent
                        {
                            // if element following the p is p-implied then there is an extra new line
                            Element next = rightParent.getElement(rightId+1);
                            if (next.getName().equals(HTML.Tag.IMPLIED.toString()))
                            {
                                newlineId =  rightElem.getEndOffset()-1-len;
                                undoEditMgr.setStartReplaceEdit("deletion");
                            }
                        }
                    }
                }
                else
                // must check if you are deleting a pre inside a li and it is not the last element in li
                // if is last, there is no extra new line
                    if(leftElem.getName().equals(HTML.Tag.IMPLIED.toString()) &&
                        rightElem.getParentElement().getName().equals(HTML.Tag.PRE.toString()) &&
                        (leftElem.getStartOffset()<offs))  // and left won't be deleted
                    {
                        Element leftParent = leftElem.getParentElement();
                        Element rightParent = rightElem.getParentElement().getParentElement();
                        if (leftParent.getName().equals(rightParent.getName()) &&
                            leftParent.getName().equals("li"))  // both share an li parent
                        {
                            int rightPreId = rightElem.getParentElement().getElementIndex(offs+len); // index into pre
                            int rightId = rightParent.getElementIndex(offs+len);  // index into li structure
                            if (rightPreId==rightElem.getParentElement().getElementCount()-1 &&
                                rightId+1<rightParent.getElementCount())  // pre is not the last element in li parent
                            {
                                // if element following the p is p-implied then there is an extra new line
                                Element next = rightParent.getElement(rightId+1);
                                if (next.getName().equals(HTML.Tag.IMPLIED.toString()))
                                {
                                    newlineId =  rightElem.getEndOffset()-1-len;
                                    undoEditMgr.setStartReplaceEdit("deletion");
                                }
                            }
                        }
                    }

                //v11
                checkForTabs = (rightElem.getParentElement().getName().equals(HTML.Tag.PRE.toString())&&
                        !leftElem.getParentElement().getName().equals(HTML.Tag.PRE.toString()));

                wasReplaceEdit = undoEditMgr.isReplaceEdit(); //v11
                if (checkForTabs && !wasReplaceEdit)  {//v11
                    undoEditMgr.setStartReplaceEdit("deletion");
                }

                // do the initial remove
                super.remove(offs,len);
                if (newlineId==-1)  // same type (no p in li) don't need to check
                { //start v11
                    // if joining <pre> content into something else, tabs must be removed
                    if (checkForTabs)
                    {
                        String addedText = getText(offs, getParagraphElement(offs).getEndOffset() - offs);
                        if (addedText.indexOf('\t')!=-1)
                        {
                            //replace every tab with a space
                            char[]cArray = addedText.toCharArray();
                            for (int i=0; i<cArray.length; i++)
                            {
                                if (cArray[i]=='\t')
                                {
                                    AttributeSet attr = getCharacterElement(offs+i).getAttributes();
                                    super.remove(offs+i,1);
                                    super.insertString(offs+i," ",attr);
                                }
                            }

                        }
                        if (!wasReplaceEdit) {
                            undoEditMgr.setEndReplaceEdit(); // end v11
                        }
                    }
                    //return;
                }else {
                    System.err.println("2 new lines found in one element .. removing char from: "+(newlineId));
                    super.remove(newlineId,1);
                    undoEditMgr.setEndReplaceEdit();
                }
            }
        }
    }
/*        if (len > 0) {
            DefaultDocumentEvent chng = null;                        //ibm@31922
            UndoableEdit u = null;                                   //ibm@31922
            boolean isComposedTextElement = false;                   //ibm@31922

            writeLock();
            try {
                chng = new DefaultDocumentEvent(offs, len, DocumentEvent.EventType.REMOVE); //ibm@31922

                // boolean isComposedTextElement = false;            //ibm@31922
                if (is1dot2) {
                    // Check whether the position of interest is the composed text
                    Element elem = getDefaultRootElement();
                    while (!elem.isLeaf()) {
                        elem = elem.getElement(elem.getElementIndex(offs));
                    }
                    isComposedTextElement = isComposedTextElement(elem);
                }

                removeUpdate(chng);
                u = getContent().remove(offs, len);                          //ibm@31922
                if (u != null) {
                    chng.addEdit(u);
                }
                postRemoveUpdate(chng);
                // Mark the edit as done.
                chng.end();
            } finally {                                              //ibm@31922
                writeUnlock();                                       //ibm@31922
            }                                                        //ibm@31922
            fireRemoveUpdate(chng);
            // only fire undo if Content implementation supports it
            // undo for the composed text is not supported for now
            if ((u != null) && !isComposedTextElement) {
                fireUndoableEditUpdate(new UndoableEditEvent(this, chng));
            }
            // ibm@31922 finally block moved from here to before fireRemoveUpdate(chng);
        }
    }
*/
    /**
     * Inserts some content into the document.
     * Inserting content causes a write lock to be held while the
     * actual changes are taking place, followed by notification
     * to the observers on the thread that grabbed the write lock.
     * <p>
     * This method is thread safe, although most Swing methods
     * are not. Please see
     * <A HREF="http://java.sun.com/products/jfc/swingdoc-archive/threads.html">Threads
     * and Swing</A> for more information.
     *
     * @param offs the starting offset >= 0
     * @param str the string to insert; does nothing with null/empty strings
     * @param a the attributes for the inserted content
     * @exception BadLocationException  the given insert position is not a valid
     *   position within the document
     * @see Document#insertString
     */
//    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException
//    {
//System.out.println("in my insertSTring!! offs: "+offs+" str: "+str);//+" attr: "+a);
//super.insertString(offs,str,a);
//System.err.println("i18n property: "+ getProperty("i18n"));
//  }
/*
        if ((str == null) || (str.length() == 0)) {
            return;
        }

        UndoableEdit u = null;                                       //ibm@31922
        DefaultDocumentEvent e = null;                               //ibm@31922

        writeLock();
        try {
            u = getContent().insertString(offs, str);                        //ibm@31922
            e = new DefaultDocumentEvent(offs, str.length(), DocumentEvent.EventType.INSERT); //ibm@31922
            if (u != null) {
                e.addEdit(u);
            }

            // see if complex glyph layout support is needed
            if( getProperty("i18n").equals( Boolean.FALSE ) ) {
                // if a default direction of right-to-left has been specified,
                // we want complex layout even if the text is all left to right.
                Object d = getProperty(java.awt.font.TextAttribute.RUN_DIRECTION);
                if ((d != null) && (d.equals(java.awt.font.TextAttribute.RUN_DIRECTION_RTL))) {
                    putProperty( "i18n", Boolean.TRUE);
                } else {
                    int len = str.length();
                    for (int i = 0; i < len; i++) {
                        char c = str.charAt(i);
                    //  inline isComplex                             //ibm.26901
                    //  if (isComplex(c)) {                          //ibm.26901
                        if (c >= '\u0590' && c < '\u10A0') {         //ibm.26901
                            putProperty( "i18n", Boolean.TRUE);
                            break;
                        }
                    }
                }
            }

            insertUpdate(e, a);
            // Mark the edit as done.
            e.end();
        } finally {                                                  //ibm@31922
            writeUnlock();                                           //ibm@31922
        }                                                            //ibm@31922

        fireInsertUpdate(e);
        // only fire undo if Content implementation supports it
        // undo for the composed text is not supported for now
        if (u != null &&
            (a == null || !a.isDefined(StyleConstants.ComposedTextAttribute))) {
            fireUndoableEditUpdate(new UndoableEditEvent(this, e));
        }
        // ibm@31922 finally block moved from here to before fireInsertUpdate(e);
    }*/

    /*******************************
    * Converts list structure to a different type of list or into paragraphs.  Either the entire list
    * is converted or selected list items are converted to a new parent of a different
    * type.
    */
    void changeListStructure(Element elem, int startPos, int endPos, HTML.Tag newTag)
    {
        try {
            DefaultDocumentEvent changes = null;
            writeLock();
            changes = new DefaultDocumentEvent(startPos, 0, DocumentEvent.EventType.CHANGE);

            // convert elements that need it
            if (newTag!=HTML.Tag.P) {
                ((XMLElementBuffer)buffer).changeListStructure(elem, startPos, endPos, newTag, changes);
            }
            else {
                ((XMLElementBuffer)buffer).removeListStructure(elem, startPos, endPos, changes);
            }

            changes.end();
            fireChangedUpdate(changes);
            fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
        } finally {
            writeUnlock();
        }
    }

    /*******************************
    * Creates list structure from lists or paragraphs.  Either the entire list
    * is converted or selected list items are converted to a new parent of a different
    * list type.
    */
    void createListStructure(Element elem, int startPos, int endPos, HTML.Tag newTag)
    {
        try {
            DefaultDocumentEvent changes = null;
            writeLock();
            changes = new DefaultDocumentEvent(startPos, 0, DocumentEvent.EventType.CHANGE);

            // convert elements that need it
            ((XMLElementBuffer)buffer).createListStructure(elem, startPos, endPos, newTag, changes);

            changes.end();
            fireChangedUpdate(changes);
            fireUndoableEditUpdate(new UndoableEditEvent(this, changes));
        } finally {
            writeUnlock();
        }
    }

    /**
     * Class to manage changes to the element hierarchy.
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases.  The current serialization support is appropriate
     * for short term storage or RMI between applications running the same
     * version of Swing.  A future release of Swing will provide support for
     * long term persistence.
     */
    private class XMLElementBuffer extends ElementBuffer
    {
    	private static final long serialVersionUID = 1L;
    	/**
         * Creates a new ElementBuffer.
         *
         * @param root the root element
         */
        XMLElementBuffer(Element root)
        {
            super(root);
            this.x_root = root;
            x_changes = new Vector<ElemChanges>();
            x_path = new Stack();
            using131 = (System.getProperty("java.version").compareTo("1.3.1")>=0);
        }

        /**
         * Gets the root element.
         *
         * @return the root element
         */
        public Element getRootElement() {
            return x_root;
        }

        /**
         * Inserts new content.
         *
         * @param offset the starting offset >= 0
         * @param length the length >= 0
         * @param data the data to insert
         * @param de the event capturing this edit
         */
        public void insert(int offset, int length, ElementSpec[] data, DefaultDocumentEvent de)
        {
/*System.out.println("ElementBuffer::insert() offset: "+offset+" len: "+length+" specs.len: "+data.length);
for(int i=0; i<data.length; i++)
{
System.out.println("ElementBuffer::insert() elemspec["+i+"] "+data[i]+" offset: "+data[i].getOffset());
System.out.println("ElementBuffer::insert() attributes");
AttributeSet as = data[i].getAttributes();
showAttributes(System.out,as);
}*/

            if (length > 0) {
                // len=0 Nothing was inserted, no structure change.
                x_insertOp = true;
                beginEdits(offset, length);
                insertUpdate(data);
                endEdits(de);

                x_insertOp = false;
            }
        }


// the ElementBuffer.create() is not public but is only pkg level so base is used and not this one!!!!!!!
// it should be public like the rest of the interface used by the DefaultStyledDocument class
// I must bring the method that invokes create() into the XMLDocument object and rename this method
// for access
/*        public void createStructure(int length, ElementSpec[] data, DefaultDocumentEvent de)
        {
//System.out.println("ElementBuffer:: createStructure entered len: "+length+" data.len: "+data.length);
            x_insertOp = true;
            beginEdits(offset, length);

//System.out.println("after beginEdits changes.size "+x_changes.size());
            // PENDING(prinz) this needs to be fixed to create a new
            // root element as well, but requires changes to the
            // DocumentEvent to inform the views that there is a new
            // root element.

            // Recreate the ending fake element to have the correct offsets.
            Element elem = root;
            int index = elem.getElementIndex(0);
            while (! elem.isLeaf()) {
                Element child = elem.getElement(index);
                push(elem, index);
                elem = child;
                index = elem.getElementIndex(0);
            }
            ElemChanges ec = (ElemChanges) x_path.peek();
            Element child = ec.parent.getElement(ec.index);
            ec.added.addElement(createLeafElement(ec.parent,
                                child.getAttributes(), getLength(),
                                child.getEndOffset()));
            ec.removed.addElement(child);
            while (x_path.size() > 1) {
                pop();
            }

//System.out.println("after recreate ending fake element changes.size "+x_changes.size());
//x_changes.clear();

            int n = data.length;

            // Reset the root elements attributes.
            AttributeSet newAttrs = null;
            if (n > 0 && data[0].getType() == ElementSpec.StartTagType) {
                newAttrs = data[0].getAttributes();
            }
            if (newAttrs == null) {
                newAttrs = SimpleAttributeSet.EMPTY;
            }
            MutableAttributeSet attr = (MutableAttributeSet)root.
                                       getAttributes();
            de.addEdit(new AttributeUndoableEdit(root, newAttrs, true));
            attr.removeAttributes(attr);
            attr.addAttributes(newAttrs);

            // fold in the specified subtree
            for (int i = 1; i < n; i++) {
                insertElement(data[i]);
            }

            // pop the remaining path
            while (x_path.size() != 0) {
                pop();
            }

            endEdits(de);
            x_insertOp = false;
        }
*/

        /************************************************************************
        * Converts children of the parent into one list.
        */
        void createListStructure(Element parent, int startPos, int endPos, HTML.Tag newTag,
                DefaultDocumentEvent de)
        {
            Element unusedChild = null; // used for portion of last list if that wasn't selected
            int leftId = 0;
            int rightId = 0;
            ElemChanges ec = null;
            SimpleAttributeSet attrSet = null;
            Element newList = null;
            Vector<Element> children = new Vector<Element>();

            beginEdits(startPos, endPos-startPos);
//System.err.print("elembuff::createListStructure entered startpos "+startPos+" endPos: "+endPos+" tag: "+newTag+" parent: "+parent);

            leftId = parent.getElementIndex(startPos);
            rightId = parent.getElementIndex(endPos-1); // endpos is set to end of selected elem, -1 to get it
            push(parent, leftId);  // create ElemChanges and push onto path
            ec = (ElemChanges) x_path.peek();

            for (int i=leftId; i<=rightId; i++)
            {
                Element child = ec.parent.getElement(i); // child is the elem passed in
                ec.removed.addElement(child); // remove old structures
            }

            // create new list
            attrSet = new SimpleAttributeSet();//child.getAttributes());
            attrSet.addAttribute(StyleConstants.NameAttribute, newTag);
            newList = createBranchElement(ec.parent, attrSet);

            //convert each child
            for (int i=leftId; i<=rightId; i++) // get each list
            {
                Element child = parent.getElement(i);
                if (child.getName().equals(HTML.Tag.P.toString())  ||
                    child.getName().equals(HTML.Tag.H1.toString()) ||
                    child.getName().equals(HTML.Tag.H2.toString()) ||
                    child.getName().equals(HTML.Tag.H3.toString()) ||
                    child.getName().equals(HTML.Tag.H4.toString()) ||
                    child.getName().equals(HTML.Tag.H5.toString()) ||
                    child.getName().equals(HTML.Tag.H6.toString()))
                {
                    Vector<Element> gchildren = new Vector<Element>();
                    Element newListItem = null;
                    Element newPimplied = null;
                    attrSet = new SimpleAttributeSet(child.getAttributes());
                    attrSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.LI);
                    // create new list item
                    newListItem = createBranchElement(newList, attrSet);
                    children.addElement(newListItem);

                    attrSet = new SimpleAttributeSet();
                    attrSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.IMPLIED);
                    newPimplied = createBranchElement(newListItem, attrSet);

                    for (int x=0; x<child.getElementCount(); x++)
                    {
                        gchildren.addElement(clone(newPimplied, child.getElement(x)));
                    }
                    if (gchildren.size()>0)
                    {
                        Element[] c = new Element[gchildren.size()];
                        gchildren.copyInto(c);
                        ((BranchElement)newPimplied).replace(0, 0, c);
                    }
                    ((BranchElement)newListItem).replace(0, 0, new Element[]{newPimplied});
                }
                else  // children must be other lists, combine them
                {
                    int lastChildId = 0;
                    int x=0;
                    if (i==leftId) // check that all of the first list is selected
                    {
                        int gchildId = child.getElementIndex(startPos);
                        if (gchildId!=0)  // a subset of the list is selected
                        {
                            // create a new list and add to the parent
                            Vector<Element> gchildren = new Vector<Element>();
                            Element newChild = null;
                            Element[] c = null;
                            attrSet = new SimpleAttributeSet(child.getAttributes());

                            newChild = createBranchElement(ec.parent, attrSet);
                            for (; x<gchildId; x++)
                            {
                                gchildren.addElement(clone(newChild, child.getElement(x)));
                            }

                            c = new Element[gchildren.size()];
                            gchildren.copyInto(c);
                            ((BranchElement)newChild).replace(0, 0, c);
                            ec.added.addElement(newChild);
                        }
                    }

                    lastChildId = child.getElementCount();
                    if (i==rightId) // check that all of the last list is selected
                    {
                        int gchildId = child.getElementIndex(endPos-1);
                        if (gchildId!=child.getElementCount())  // a subset of the list is selected
                        {
                            lastChildId = gchildId+1;
                            // restore this list after merging the newly created list
                        }
                    }

                    // copy list items to new list
                    for (; x<lastChildId; x++)
                    {
                        children.addElement(clone(newList, child.getElement(x)));
                    }

                    if (x<child.getElementCount())  // a subset of the list is selected
                    {
                        Element[] c = null;
                        // create a new list and add to the parent
                        Vector<Element> gchildren = new Vector<Element>();
                        attrSet = new SimpleAttributeSet(child.getAttributes());
                        unusedChild = createBranchElement(ec.parent, attrSet);
                        for (; x<child.getElementCount(); x++)
                        {
                            gchildren.addElement(clone(unusedChild, child.getElement(x)));
                        }
                        c = new Element[gchildren.size()];
                        gchildren.copyInto(c);
                        ((BranchElement)unusedChild).replace(0, 0, c);
                    }
                }
            }

            // xfer new list items to list
            if (children.size()>0)
            {
                Element[] c = new Element[children.size()];
                children.copyInto(c);
                ((BranchElement)newList).replace(0, 0, c);
                ec.added.addElement(newList); // add new list
            }
            if (unusedChild != null) {
                ec.added.addElement(unusedChild);
            }

            pop();  // get onto changes list to get into the DocumentEvent in endEdits()
            endEdits(de);
        }

        /***********************************************************************
        * Converts list structure to a different type of list.  Either the entire list
        * is converted or selected list items are converted to a new parent of a different
        * type.
        */
        void changeListStructure(Element elem, int startPos, int endPos, HTML.Tag newTag,
                DefaultDocumentEvent de)
        {
            Element parent = null;
            ElemChanges ec = null;
            Element child = null;
            int index = 0;
            beginEdits(startPos, endPos-startPos);
            parent = elem.getParentElement();
//System.err.print("elembuff::changeListStructure entered startpos "+startPos+" endPos: "+endPos+" tag: "+newTag+" elem: "+elem);
            index = parent.getElementIndex(startPos);
            push(parent, index);  // create ElemChanges and push onto path
            ec = (ElemChanges) x_path.peek();
            child = ec.parent.getElement(ec.index); // child is the elem passed in
            ec.removed.addElement(child); // remove old list structure

            // if same element, replace elem in parent using new tag as name attribute
            if (elem.getStartOffset()==startPos && elem.getEndOffset()==endPos)
            {
                Vector<Element> children = new Vector<Element>();
                Element newChild = null;
                Element[] c = null;
                // can't change name attribute.. javax.swing.text.StateInvariantError: Illegal cast to MutableAttributeSet
                //((AbstractElement)elem).addAttribute(StyleConstants.NameAttribute, newTag);
                // must rebuild elem with new name tag
                // create new list
                SimpleAttributeSet attrSet = new SimpleAttributeSet(elem.getAttributes());
                attrSet.addAttribute(StyleConstants.NameAttribute, newTag);
                newChild = createBranchElement(ec.parent, attrSet);
                // xfer original children (list items)
                for (int x=0; x<child.getElementCount(); x++) {
                    children.addElement(clone(newChild, child.getElement(x)));
                }

                c = new Element[children.size()];
                children.copyInto(c);
                ((BranchElement)newChild).replace(0, 0, c);

                ec.added.addElement(newChild); // add new list
            }
            else  // convert part of the list to another list
            {
                int leftId = child.getElementIndex(startPos);
                int rightId = child.getElementIndex(endPos-1); // endpos is set to end of selected elem, -1 to get it
                // xfer from 0 to leftid to a new child with same name attribute as original child
                Vector<Element> children = new Vector<Element>();
                int x=0;
                // create new list with original name
                SimpleAttributeSet attrSet = new SimpleAttributeSet(child.getAttributes());
                Element newChild = null;

                if (leftId>0)  // move left children into new parent with same name
                {
                    newChild = createBranchElement(ec.parent, attrSet);

                    for (; x<leftId; x++) {
                        children.addElement(clone(newChild, child.getElement(x)));
                    }
                    if (children.size()>0)
                    {
                        Element[] c = new Element[children.size()];
                        children.copyInto(c);
                        ((BranchElement)newChild).replace(0, 0, c);

                        ec.added.addElement(newChild); // add old list
                        children.removeAllElements();
                    }
                }

                // xfer from leftid to rightId to new child with new tag as name
                // create new list using new name
                attrSet.addAttribute(StyleConstants.NameAttribute, newTag);
                newChild = createBranchElement(ec.parent, attrSet);

                for (; x<=rightId; x++) {
                    children.addElement(clone(newChild, child.getElement(x)));
                }
                if (children.size()>0)
                {
                    Element[] c = new Element[children.size()];
                    children.copyInto(c);
                    ((BranchElement)newChild).replace(0, 0, c);

                    ec.added.addElement(newChild); // add new list
                    children.removeAllElements();
                }

                // if more children left, move to new child with old child name
                if (rightId+1<child.getElementCount())
                {
                    attrSet.addAttribute(StyleConstants.NameAttribute, child.getAttributes().getAttribute(StyleConstants.NameAttribute));
                    newChild = createBranchElement(ec.parent, attrSet);

                    // xfer from rightId to elemcount to new child
                    for (; x<child.getElementCount(); x++) {
                        children.addElement(clone(newChild, child.getElement(x)));
                    }

                    if (children.size()>0)
                    {
                        Element[] c = new Element[children.size()];
                        children.copyInto(c);
                        ((BranchElement)newChild).replace(0, 0, c);

                        ec.added.addElement(newChild); // add new list
                        children.removeAllElements();
                    }
                }
            }

            pop();  // get onto changes list to get into the DocumentEvent in endEdits()
            endEdits(de);
        }

        /*************************************************************************
        * Converts list structure to a paragraphs.  Either the entire list
        * is converted or selected list items are converted to a paragraph and unselected list items
        * are put into new parent of a same list type.
        */
        void removeListStructure(Element elem, int startPos, int endPos, DefaultDocumentEvent de)
        {
            Element parent = null;
            ElemChanges ec = null;
            Element child = null;
            int index = 0;
            beginEdits(startPos, endPos-startPos);
//System.err.print("elembuff::removeListStructure entered startpos "+startPos+" endPos: "+endPos+" elem: "+elem);
            parent = elem.getParentElement();
            index = parent.getElementIndex(startPos);
            push(parent, index);  // create ElemChanges and push onto path
            ec = (ElemChanges) x_path.peek();
            child = ec.parent.getElement(ec.index); // child is the elem passed in
            ec.removed.addElement(child); // remove old list structure

            // if same element, convert each list item into a p and add to parent
            if (elem.getStartOffset()==startPos && elem.getEndOffset()==endPos)
            {
                removeListStructure(child,ec);
            }
            else  // convert part of the list to paragraph
            {
                int leftId = child.getElementIndex(startPos);
                int rightId = child.getElementIndex(endPos-1); // endpos is set to end of selected elem, -1 to get it
                // xfer from 0 to leftid to a new child with same name attribute as original child
                Vector<Element> children = new Vector<Element>();
                int x=0;
                // create new list with original name
                SimpleAttributeSet attrSet = new SimpleAttributeSet(child.getAttributes());
                Element newChild = null;

                if (leftId>0)  // move left children into new parent with same name
                {
                    newChild = createBranchElement(ec.parent, attrSet);

                    for (; x<leftId; x++) {
                        children.addElement(clone(newChild, child.getElement(x)));
                    }
                    if (children.size()>0)
                    {
                        Element[] c = new Element[children.size()];
                        children.copyInto(c);
                        ((BranchElement)newChild).replace(0, 0, c);

                        ec.added.addElement(newChild); // add old list
                        children.removeAllElements();
                    }
                }

                // convert from leftid to rightId to paragraph
                for (; x<=rightId; x++) {
                    removeListItemStructure(child.getElement(x), ec);
                }

                // if more children left, move to new child with old child name
                if (rightId+1<child.getElementCount())
                {
                    attrSet.addAttribute(StyleConstants.NameAttribute, child.getAttributes().getAttribute(StyleConstants.NameAttribute));
                    newChild = createBranchElement(ec.parent, attrSet);

                    // xfer from rightId to elemcount to new child
                    for (; x<child.getElementCount(); x++) {
                        children.addElement(clone(newChild, child.getElement(x)));
                    }

                    if (children.size()>0)
                    {
                        Element[] c = new Element[children.size()];
                        children.copyInto(c);
                        ((BranchElement)newChild).replace(0, 0, c);

                        ec.added.addElement(newChild); // add new list
                        children.removeAllElements();
                    }
                }
            }

            pop();  // get onto changes list to get into the DocumentEvent in endEdits()
            endEdits(de);
        }

        // convert entire list to paragraphs and add to the parent specified in ec
        // this will flatten all lists below the parent (paragraphs can not have nested lists)
        private void removeListStructure(Element list, ElemChanges ec)
        {
            for (int x=0; x<list.getElementCount(); x++)  // each li
            {
                // for each list item, convert to p
                removeListItemStructure(list.getElement(x), ec);
            }
        }

        // convert all list items to paragraphs and add to the parent specified in ec
        // this will flatten all lists below the parent (paragraphs can not have nested lists)
        private void removeListItemStructure(Element gchild, ElemChanges ec)
        {
            // pull attributes from the list item to maintain publish setting
            SimpleAttributeSet attrSet = new SimpleAttributeSet(gchild.getAttributes());
            attrSet.addAttribute(StyleConstants.NameAttribute, HTML.Tag.P);

            // xfer original children (list item children)
            for (int x2=0; x2<gchild.getElementCount(); x2++)
            {
                Element ggchild = gchild.getElement(x2);
                Element[] c = null;
                if (ggchild.getName().equals(HTML.Tag.IMPLIED.toString()) ||
                    ggchild.getName().equals(HTML.Tag.P.toString()))
                {
                    Element newChild = createBranchElement(ec.parent, attrSet);
                    Vector<Element> children = new Vector<Element>();

                    for (int x3=0; x3<ggchild.getElementCount(); x3++) {
                        children.addElement(clone(newChild, ggchild.getElement(x3)));
                    }

                    c = new Element[children.size()];
                    children.copyInto(c);
                    ((BranchElement)newChild).replace(0, 0, c);
                    ec.added.addElement(newChild); // add new p..
                }
                else
                {
                    removeListStructure(ggchild, ec);
                }
            }
        }

        /**
         * Removes content.
         *
         * @param offset the starting offset >= 0
         * @param length the length >= 0
         * @param de the event capturing this edit
         */
        public void remove(int offset, int length, DefaultDocumentEvent de)
        {
//System.err.println("ElementBuffer::remove offset: "+offset+" len: "+length);
            beginEdits(offset, length);
            removeUpdate();
            endEdits(de);
        }

        /**
         * Changes content.
         *
         * @param offset the starting offset >= 0
         * @param length the length >= 0
         * @param de the event capturing this edit
         */
        public void change(int offset, int length, DefaultDocumentEvent de) {
//System.err.println("ElementBuffer::change offset: "+offset+" len: "+length);
            beginEdits(offset, length);
            changeUpdate();
            endEdits(de);
        }

        /**
         * Updates the element structure in response to a removal from the
         * associated sequence in the document.  Any elements consumed by the
         * span of the removal are removed.
         */
        protected void removeUpdate() {
//System.out.println("ElementBuffer::removeUpdate() calling x_removeElements() offset1: "+offset
//+" offset2: "+(offset+length)+" root: "+x_root);
            x_removeElements(x_root, x_offset, x_offset + x_length);
        }

        /**
         * Updates the element structure in response to a change in the
         * document.
         */
        protected void changeUpdate() {
            boolean didEnd = split(x_offset, x_length);
            if (! didEnd) {
                // need to do the other end
                while (x_path.size() != 0) {
                    pop();
                }
                split(x_offset + x_length, 0);
            }
            while (x_path.size() != 0) {
                pop();
            }
        }

        boolean split(int offs, int len) {
            ElemChanges ec = null;
            Element child = null;
            boolean splitEnd = false;
            // push the path
            Element e = x_root;
            int index = e.getElementIndex(offs);
            while (! e.isLeaf()) {
                push(e, index);
                e = e.getElement(index);
                index = e.getElementIndex(offs);
            }

            ec = (ElemChanges) x_path.peek();
            child = ec.parent.getElement(ec.index);
            // make sure there is something to do... if the
            // offset is already at a boundary then there is
            // nothing to do.
            if (child.getStartOffset() != offs) {
                boolean done = false;
                // we need to split, now see if the other end is within
                // the same parent.
                int index0 = ec.index;
                int index1 = index0;
                if (((offs + len) < ec.parent.getEndOffset()) && (len != 0)) {
                    // it's a range split in the same parent
                    index1 = ec.parent.getElementIndex(offs+len);
                    if (index1 == index0) {
                        // it's a three-way split
                        ec.removed.addElement(child);
                        e = createLeafElement(ec.parent, child.getAttributes(),
                            child.getStartOffset(), offs);
                        ec.added.addElement(e);
                        e = createLeafElement(ec.parent, child.getAttributes(),
                            offs, offs + len);
                        ec.added.addElement(e);
                        e = createLeafElement(ec.parent, child.getAttributes(),
                            offs + len, child.getEndOffset());
                        ec.added.addElement(e);
                        //return true;
                        done = true;
                    } else {
                        child = ec.parent.getElement(index1);
                        if ((offs + len) == child.getStartOffset()) {
                            // end is already on a boundary
                            index1 = index0;
                        }
                    }
                    splitEnd = true;
                }
                if (!done) {
                    // split the first location
                    x_pos = offs;
                    child = ec.parent.getElement(index0);
                    ec.removed.addElement(child);
                    e = createLeafElement(ec.parent, child.getAttributes(),
                        child.getStartOffset(), x_pos);
                    ec.added.addElement(e);
                    e = createLeafElement(ec.parent, child.getAttributes(),
                        x_pos, child.getEndOffset());
                    ec.added.addElement(e);

                    // pick up things in the middle
                    for (int i = index0 + 1; i < index1; i++) {
                        child = ec.parent.getElement(i);
                        ec.removed.addElement(child);
                        ec.added.addElement(child);
                    }

                    if (index1 != index0) {
                        child = ec.parent.getElement(index1);
                        x_pos = offs + len;
                        ec.removed.addElement(child);
                        e = createLeafElement(ec.parent, child.getAttributes(),
                            child.getStartOffset(), x_pos);
                        ec.added.addElement(e);
                        e = createLeafElement(ec.parent, child.getAttributes(),
                            x_pos, child.getEndOffset());
                        ec.added.addElement(e);
                    }
                }
            }
            return splitEnd;
        }


        /**
         * Creates the UndoableEdit record for the edits made
         * in the buffer.
         */
        void endEdits(DefaultDocumentEvent de)
        {
            int n = x_changes.size();
//System.out.println("ElementBuffer:endEdits changes.size: "+n);
            for (int i = 0; i < n; i++)
            {
                ElementEdit ee = null;
                ElemChanges ec = (ElemChanges) x_changes.elementAt(i);
                Element[] removed = new Element[ec.removed.size()];
                Element[] added = new Element[ec.added.size()];
                int index = ec.index;
                ec.removed.copyInto(removed);
                ec.added.copyInto(added);

                // this is where the actual buffer is updated
                ((BranchElement) ec.parent).replace(index, removed.length, added);
//System.out.println("endEdits: ec.index "+ec.index+" ec.parent: "+ec.parent);

                ee = new ElementEdit((BranchElement) ec.parent,index, removed, added);
                de.addEdit(ee);
            }
            /*
            for (int i = 0; i < n; i++) {
                ElemChanges ec = (ElemChanges) x_changes.elementAt(i);
                System.out.print("endEdit:: edited parent: " + ec.parent + " child index: " + ec.index +
                    " removed " + ec.removed.size()+"\n");
                for(int u=0; u<ec.removed.size(); u++)
                    System.out.print("removed["+u+"] "+(Element)ec.removed.elementAt(u));
               // if (ec.removed.size() > 0) {
               //     int r0 = ((Element) ec.removed.firstElement()).getStartOffset();
               //     int r1 = ((Element) ec.removed.lastElement()).getEndOffset();
               //     //System.out.print(" [" + r0 + "," + r1 + "]");
               // }
                System.out.println(" added " + ec.added.size());
                for(int u=0; u<ec.added.size(); u++)
                    System.out.print("added["+u+"] "+(Element)ec.added.elementAt(u));
//                if (ec.added.size() > 0) {
//                    int p0 = ((Element) ec.added.firstElement()).getStartOffset();
//                    int p1 = ((Element) ec.added.lastElement()).getEndOffset();
//                    //System.out.print(" [" + p0 + "," + p1 + "]");
//                }
                System.out.println("");
            }
            */

            x_changes.removeAllElements();
            x_path.removeAllElements();
        }

        /**
         * Initialize the buffer
         */
        @SuppressWarnings("unchecked")
		void beginEdits(int offset, int length)
        {
//System.out.println("ElementBuffer::beginEdits offset: "+offset+" len: "+length);
            this.x_offset = offset;
            this.x_length = length;
            this.x_endOffset = x_offset + x_length;
            x_pos = x_offset;
            if (x_changes == null) {
                x_changes = new Vector();
            } else {
                x_changes.removeAllElements();
            }
            if (x_path == null) {
                x_path = new Stack();
            } else {
                x_path.removeAllElements();
            }
            x_fracturedParent = null;
            x_fracturedChild = null;
            x_offsetLastIndex = false;
            x_offsetLastIndexOnReplace = false;
        }

        /**
         * Pushes a new element onto the stack that represents
         * the current path.
         * isFracture true if pushing on an element that was created
         * as the result of a fracture.
         */
        @SuppressWarnings("unchecked")
		void push(Element e, int index, boolean isFracture) {
            ElemChanges ec = new ElemChanges(e, index, isFracture);
            x_path.push(ec);
        }

        void push(Element e, int index) {
            push(e, index, false);
        }

        void pop() {
            ElemChanges ec = (ElemChanges) x_path.pop();
//System.out.println("pop() entered ec: ");//+ec.parent);
            if ((ec.added.size() > 0) || (ec.removed.size() > 0)) {
                x_changes.addElement(ec);
//System.out.println("pop() ec had added or removed so adding to changes vct");
            } else if (! x_path.isEmpty()) {
                Element e = ec.parent;
                if(e.getElementCount() == 0) {
                    // if we pushed a branch element that didn't get
                    // used, make sure its not marked as having been added.
                    ec = (ElemChanges) x_path.peek();
                    ec.added.removeElement(e);
//System.out.println("pop() path!empty and parent.elemcount=0 so ec adds removed elem: "+e.getName());
                }
            }
        }

        void insertElement(ElementSpec es)
        {
/*System.out.println("insertElement:: entered: es: "+es);
showAttributes(System.out,es.getAttributes());
*/
            ElemChanges ec = (ElemChanges) x_path.peek();
            switch(es.getType())
            {
            case ElementSpec.StartTagType:
                switch(es.getDirection())
                {
                case ElementSpec.JoinNextDirection:
                    {
                        // Don't create a new element, use the existing one
                        // at the specified location.
                        Element parent = ec.parent.getElement(ec.index);
                        if(parent.isLeaf()) {
                            // This happens if inserting into a leaf, followed
                            // by a join next where next sibling is not a leaf.
                            if((ec.index + 1) < ec.parent.getElementCount()) {
                                parent = ec.parent.getElement(ec.index + 1);
                            }
                            else {
                                throw new RuntimeException("Join next to leaf");
                            }
                        }
                        // Not really a fracture, but need to treat it like
                        // one so that content join next will work correctly.
                        // We can do this because there will never be a join
                        // next followed by a join fracture.
                        push(parent, 0, true);
                        break;
                    }
                case ElementSpec.JoinFractureDirection:
                    if(!x_createdFracture) {
                        // Should always be something on the stack!
                        fracture(x_path.size() - 1);
                    }
                    // If parent isn't a fracture, fracture will be
                    // fracturedChild.
                    if(!ec.isFracture) {
                        push(x_fracturedChild, 0, true);
                    }
                    else {
                        // Parent is a fracture, use 1st element.
                        push(ec.parent.getElement(0), 0, true);
                    }
                    break;
                default:
                    {
//System.err.println("creating new ELEM!!! with attr dropPublishAttr: "+dropPublishAttr);
                        Element belem = null;
                        // prevent transfer of publish tag (when enter is pressed in a paragraph <p>)
                        SimpleAttributeSet attrSet = new SimpleAttributeSet(es.getAttributes());
                        if (dropPublishAttr)
                        {
                            Object obj = attrSet.getAttribute(PUBLISH_TAG_ATTR);
                            if (obj!=null) {
                                attrSet.removeAttribute(PUBLISH_TAG_ATTR);
                            }
                        }

                        belem = createBranchElement(ec.parent, attrSet);
                        ec.added.addElement(belem);
                        push(belem, 0);
                        break;
                    }
                }
                break;
            case ElementSpec.EndTagType:
                pop();
                break;
            case ElementSpec.ContentType:
                {
                    int len = es.getLength();
                    if (es.getDirection() != ElementSpec.JoinNextDirection) {
    //System.out.println("es direction is !joinNext pos: "+pos+" len:"+len);
                        Element leaf = createLeafElement(ec.parent, es.getAttributes(), x_pos, x_pos + len);
                        ec.added.addElement(leaf);
                    }
                    else {
    // must not do any join if element is br
                        // JoinNext on tail is only applicable if last element
                        // and attributes come from that of first element.
                        // With a little extra testing it would be possible
                        // to NOT due this again, as more than likely fracture()
                        // created this element.
                        if(!ec.isFracture)
                        {
    //System.out.println("es direction is joinNext and !isFracture x_pos: "+x_pos+" len:"+len);
                            Element first = null;
                            Element leaf = null;
                            if(x_insertPath != null) {
                                for(int counter = x_insertPath.length - 1; counter >= 0; counter--) {
                                    if(x_insertPath[counter] == ec) {
                                        if(counter != (x_insertPath.length - 1)) {
                                            first = ec.parent.getElement(ec.index);
                                        }
                                        break;
                                    }
                                }
                            }
                            if(first == null) {
                                first = ec.parent.getElement(ec.index + 1);
                            }
                            leaf = createLeafElement(ec.parent, first.getAttributes(), x_pos, first.getEndOffset());
                            ec.added.addElement(leaf);
                            ec.removed.addElement(first);
                        }
                        else {
                            // br must not be joined

    //System.out.println("es direction is joinNext and isFracture pos: "+pos+" len:"+len);
                            // Parent was fractured element.
                            Element first = ec.parent.getElement(0);
    //System.out.println("first: "+first);
                            Element leaf = null;
                            if (first.getName().equals(HTML.Tag.BR.toString()))
                            {
                                // add new leaf
                                // use attributes from br to get bold, etc
                                // but use CONTENT instead of BR
                                SimpleAttributeSet sas = new SimpleAttributeSet(first.getAttributes());
                                sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.CONTENT);
                                leaf = createLeafElement(ec.parent, sas, x_pos, first.getStartOffset());
                                ec.added.addElement(leaf);
                                // restore break
                                leaf = createLeafElement(ec.parent, first.getAttributes(),
                                    first.getStartOffset(), first.getEndOffset());
                            }
                            else
                            {
                                leaf = createLeafElement(ec.parent, first.getAttributes(), x_pos, first.getEndOffset());
                            }
                            ec.added.addElement(leaf);
                            ec.removed.addElement(first);
                        }
                    }
                    x_pos += len;
                    break;
                }
            default:
                break;
            }
        }

        /**
         * Remove the elements from <code>elem</code> in range
         * <code>rmOffs0</code>, <code>rmOffs1</code>. This uses
         * <code>canJoin</code> and <code>join</code> to handle joining
         * the endpoints of the insertion.
         * The path taken to merge the content depends on the left and
         * right side.  canJoin() determines the path taken.
         *
         * @return true if elem will no longer have any elements.
         */
        boolean x_removeElements(Element elem, int rmOffs0, int rmOffs1)
        {
            boolean done = false;
//System.out.print("ElementBuffer:removeElements: entered offs0: "+rmOffs0+" offs1: "+rmOffs1+" elem: "+elem);
            if (!elem.isLeaf())  // must drill down farther
            {
                ElemChanges ec;
                // update path for changes
                int index0 = elem.getElementIndex(rmOffs0);
                int index1 = elem.getElementIndex(rmOffs1);
//System.out.println("removeElement:: elemid for offset0: "+index0+" elemid for offset1: "+index1);
//System.out.println("removeElement:: Pushing parent elem: "+elem+" and left index: "+index0+" onto path (creating new ElemChanges)");
                push(elem, index0);
                ec = (ElemChanges)x_path.peek();// get ElemChanges we just created and pushed

                // if the range is contained by one element, drill down farther
                // this will be true at first because of the hierarchical nature of elements
                // html will contain all, body will contain all..
                if (index0 == index1)
                {
                    Element child0 = elem.getElement(index0);
//System.out.println("removeElement:: indexes match: range is in one element: "+child0);
                    if(rmOffs0 <= child0.getStartOffset() && rmOffs1 >= child0.getEndOffset())
                    {
                        // Element totally removed.. completely contained in the offset range
                        // add to removed vector in parent ElemChanges object
//System.out.println("removeElement:: child0 "+child0+
//" is completely contained by the range.. adding a removed elem to ElemChanges");
                        ec.removed.addElement(child0);
                    }
                    else
                    {
                        // element was not totally removed
//System.out.print("removeElement:: Range does not completely remove the elem "+child0);
                        if(x_removeElements(child0, rmOffs0, rmOffs1))
                        {
                            // if recursion has removed all children of this element then mark it as removed
//System.out.print("removeElement::   recursive call result returned true so removed this elem: "+child0+" adding a removed elem");
                            ec.removed.addElement(child0);
                        }
//else System.out.print("removeElement::   recursive call result returned false elem: "+child0);
                    }
                }
                else
                {
                    // the removal range spans elements.  If we can join
                    // the two endpoints, do it.  Otherwise we remove the
                    // interior and forward to the endpoints.
                    Element child0 = elem.getElement(index0);
                    Element child1 = elem.getElement(index1);
//System.out.println("removeElement:: range spans elements index0: "+index0+" child0 "+child0+
//" index1: "+index1+" child1: "+child1);
                    boolean containsOffs1 = (rmOffs1 < elem.getEndOffset());
//System.out.println("removeElement:: This parent element "+elem.getName()+" endoffset: "+
//elem.getEndOffset()+" contains offs1: "+rmOffs1+" ? "+containsOffs1);
                    // if this parent element contains the last remove offset and can join children
                    if (containsOffs1 && canJoin(child0, child1))
                    {
                        Element e = null;
//System.out.println("removeElement:: canjoin child0 "+child0+" child1: "+child1);
                        // remove from left child upto and including right child and join
//System.out.println("removeElement:: remove from left child upto and including right child");
                        for (int i = index0; i <= index1; i++) {
//System.out.println("removeElement:: ec.add to removed elem: "+elem.getElement(i));
                            ec.removed.addElement(elem.getElement(i));
                        }
//System.out.println("removeElement:: call join for child0 and child1");
                        // get newly joined element
                        e = join(elem, child0, child1, rmOffs0, rmOffs1);
//System.out.println("removeElement:: ec.add to added elem: "+e);
                        ec.added.addElement(e);
                    } else
                    {
                        // join the right content to the left content, recursing either
                        // the left and/or right side to find content to join.
                        if (!child0.isLeaf() && !child1.isLeaf())
                        {
                            mergeChildren(ec, elem, index0, index1, rmOffs0, rmOffs1);
                        }
                        else
                        {
                            // this only seems to get entered if both children are leaves when
                            // deletion is done at format boundaries, like between bold and normal
                            // no joining is needed in this case
                            // also entered for delete pressed at <br>
//System.out.println("removeElement:: can't join .. child0 "+child0+" child1: "+child1+" in parent: "+elem);

                            // remove interior and forward
                            int rmIndex0 = index0 + 1;  // move to next inner left child
                            int rmIndex1 = index1 - 1;  // move to next inner right child
                            // first child is the start of the removed range or
                            // first child and its start is > then the start of removed range and
                            // its end offset is inside the removed range it is completed consumed
                            if (child0.getStartOffset() == rmOffs0 ||
                                (index0 == 0 &&
                                    child0.getStartOffset() > rmOffs0 &&
                                    child0.getEndOffset() <= rmOffs1))
                            {
                                // start element completely consumed
                                child0 = null;
                                rmIndex0 = index0;  // reset to left child
//System.out.println("removeElement:: can't join child0 completely removed, set to null");
                            }
                            // if this parent element does not contain the last remove offset
                            if (!containsOffs1)
                            {
                                child1 = null;
                                rmIndex1++;  // reset to right child
//System.out.println("removeElement:: can't join parent does not contain offs1; child1 completely removed, set to null");
                            }
                            else if (child1.getStartOffset() == rmOffs1)
                            { // remove range ends where child1 starts
                                // end element not touched
                                child1 = null;
//System.out.println("removeElement:: can't join child1 startoffset==rmoffs1, set to null");
                            }
                            if (rmIndex0 <= rmIndex1) {
                                ec.index = rmIndex0;  // index of removed child
                            }
                            // remove inner children
                            for (int i = rmIndex0; i <= rmIndex1; i++) {
//System.out.print("removeElement:: can't join remove inner children ec.removed: "+elem.getElement(i));
                                ec.removed.addElement(elem.getElement(i));
                            }
                            // remove left child?
                            if (child0 != null) {
//System.out.print("removeElement:: can't join recursive call to removeElement with child0: "+child0);
                                if(x_removeElements(child0, rmOffs0, rmOffs1)) {
                                    ec.removed.insertElementAt(child0, 0);
                                    ec.index = index0;  // reset index to left child
                                }
                            }
                            // remove right child?
                            if (child1 != null) {
//System.out.print("removeElement:: can't join recursive call to removeElement with child1: "+child1);
                                if(x_removeElements(child1, rmOffs0, rmOffs1)) {
                                    ec.removed.addElement(child1);
                                }
                            }
                        }
                    }
                }

                // publish changes
                pop();

//System.out.println("removeElement::*********done** elem "+elem.getName()+" element cnt: "+elem.getElementCount()+
//" numremoved: "+ec.removed.size()+" numadded: "+ec.added.size());
                // Return true if we no longer have any children.
                if(elem.getElementCount() == (ec.removed.size() - ec.added.size())) {
//System.out.println("removeElement:: exiting true.. no longer have children? elem: "+elem+
//" elemcnt: "+elem.getElementCount()+" removed "+ec.removed+" added: "+ec.added);
                    done=true;//return true;
                }
//System.out.println("removeElement:: exiting false ");
            }
//else System.out.println("removeElement:: exiting false WAS LEAF");

            return done;//false;
        }

        // this will merge 2 separate children, either leaf2branch, branch2leaf or branch2branch
        private void mergeChildren(ElemChanges ec0, Element parent, int index0, int index1,
            int rmOffs0, int rmOffs1)
        {
            Element child0 = parent.getElement(index0);
            Element child1 = parent.getElement(index1);
            Element newChild=null;
            boolean nested = false;

            // get the affected grandchildren
            //int ljIndex = child0.getElementIndex(rmOffs0);
            int rjIndex = child1.getElementIndex(rmOffs1);
            //Element lj = child0.getElement(ljIndex);
            Element rj = child1.getElement(rjIndex);
//System.out.print("mergechildren:: index of the left grandchild affected ljIndex: "+ljIndex+" lj: "+lj);
//System.out.print("mergechildren:: index of the right grandchild affected rjIndex: "+rjIndex+" rj: "+rj);

            // remove from left child upto and including the right child
            for (int i = index0; i <= index1; i++)
            {
//System.out.print("mergechildren:: ec.removed elem: "+parent.getElement(i));
                ec0.removed.addElement(parent.getElement(i));
            }

            // if the entire left child is removed, the right side must get rebuilt using the startoffset
            // of the left child
            // unless the structure is nested, then the left side needs to be rebuilt and
            // maintain the top level structure
            nested = (parent.getName().equals("li") &&
//allow for <p> or <pre> child0.getName().equals("p-implied") &&
                (child1.getName().equals("ul") ||
                    child1.getName().equals("ol")));

            // this will allow delete pressed in an empty <p> to pull contents of following structure up into
            // the p instead of deleting the p and keeping following structure
            // but do not do this at offset=0, no way to delete the first structure if i do that
            // this is needed to prevent mergeRightSide() for required (child0.getEndOffset()<=rmOffs1) instead
            // of (child0.getEndOffset()<rmOffs1)..mergeRightSide() is needed to prevent transfer of tr, td to p
            // or li to p when entire table or list is deleted before a p
            if (rmOffs0!=0 && child0.getEndOffset()-child0.getStartOffset()==1 &&
                !child1.getName().equals("table"))  // do not pull table contents into left side v111
            {
//System.err.println("*****forcing merge for left side element len=1***");
                nested=true;
            }

            if(!nested &&(rmOffs0==0 ||
//          (child0.getStartOffset()==rmOffs0 && child0.getEndOffset()<rmOffs1)))
                    (child0.getStartOffset()==rmOffs0 && child0.getEndOffset()<=rmOffs1)))
            // when using <rmOffs1 instead of <=rmOffs1
            // ol on left and p on right.. select entire ol and delete.. get
            // li under p!!! table and p will end up with tr and td under p!!!
            {
//System.out.println("mergechildren:: rj.isLeaf ? "+rj.isLeaf()+" and lj.isLeaf ? "+lj.isLeaf()+" calling mergeRightSide****");
                newChild = mergeRightSide(parent, child1, rmOffs0,rmOffs1);
            }
            else
            {
//System.out.println("mergechildren:: rj.isLeaf ? "+rj.isLeaf()+" and lj.isLeaf ? "+lj.isLeaf()+" calling merge");
                newChild = merge(ec0,parent, child0, child1, rmOffs0, rmOffs1);
            }

//System.out.println("mergechildren:: ec0.parent: "+ec0.parent+" ec.added elem: "+newChild);

            if (rj.isLeaf()) {
                ec0.added.addElement(newChild);
            }
            else {  // must move in front of child created for right side recursion
                ec0.added.insertElementAt(newChild,0);
            }
        }

        // this will handle joining 2 leaves or walking down the left side until the content is found
        // 2 leaves occur when delete is between h3 and p
        // recursion is needed when delete is between ol and p
        // if the right side is not a leaf, the right side will be walked down after the left side is traversed
        // this happens when deletion is done between p and ol
        private Element merge(ElemChanges rec, Element parent, Element child0, Element child1,
            int rmOffs0, int rmOffs1)
        {
//System.out.println("merge entered rmoffs0: "+rmOffs0+" rmOffs1: "+rmOffs1+" for parent "+parent+" child0: "+child0+" child1: "+child1);
            // create new child0 type element
            // join two branch elements.  This copies the content children before
            // the removal range on the left element, and after the removal
            // range on the right element.  The two elements on the edge of the removal range
            // are joined if possible.  If not joined, they are added directly.
            // Any elements completely contained in the removed range are skipped
            Element newChild = null;
            int ljIndex = 0;
            int rjIndex = 0;
            Element lj = null;
            Element rj = null;
            Element[] c;
            Vector<Element> children = new Vector<Element>();

            AttributeSet attr = child0.getAttributes();
            if (child0.getStartOffset()==rmOffs0&& child0.getEndOffset()<=rmOffs1 && // child0 is completely removed
                (child0.getEndOffset()-child0.getStartOffset()>1) &&   // a single bullet will not be removed
                child0.getParentElement().getName().equals("body")) { // only use child1 attr if child0 is a top level element (parent is body)
                attr = child1.getAttributes();
            }

            newChild = createBranchElement(parent, attr);  // children added later
//System.out.println("merge:: created new branchelem "+newChild.getName()+" children will be added later");// no children at this point so toString() crashes with null ptr

            // get affected grandchildren
            ljIndex = child0.getElementIndex(rmOffs0);
            rjIndex = child1.getElementIndex(rmOffs1);
            lj = child0.getElement(ljIndex);
//System.out.println("merge:: index of the left grandchild affected ljIndex: "+ljIndex+" lj: "+lj);
            // left startoffset is greater than or equal to removeOffset0
            // is the entire left child going to be deleted?
            // this is the case if delete is pressed at end of structure and the last content element contains a \n
            if (lj.isLeaf()&& lj.getStartOffset() == rmOffs0 && lj.getEndOffset() == rmOffs1)
            {
//System.out.println("merge:: ********* entire leaf will be deleted startoffset of the left grandchild => start of removed range");
                if (ljIndex>0)
                {
//System.out.println("merge:: lj before: "+lj);
                    ljIndex--;
                    lj = child0.getElement(ljIndex);
//System.out.println("merge:: lj after: "+lj)
                }
//else System.out.println("merge:: ****.. was first element *************** entire elem will be deleted startoffset of the left grandchild => start of removed range");
            }
            rj = child1.getElement(rjIndex);
//System.out.println("merge:: index of the right grandchild affected rjIndex: "+rjIndex+" rj: "+rj);

            // transfer the left side prior to the affected element
            for (int i = 0; i < ljIndex; i++)
            {
//System.out.println("merge:: xferring left grandchildren upto the affected grandchild xfer elem: "+child0.getElement(i));
                children.addElement(clone(newChild, child0.getElement(i)));
            }

            // have we recursed down to the left sides leaves
            if (lj.isLeaf())
            {
                boolean isBR = false;
                int n = 0;
                // must get down to content on right side
                // take rj, find where rmoffs1 falls and get content from there to end of listitem structure
                // rebuild child1 with remaining listitems
                if(!rj.isLeaf())  // p-implied or worse if nested elements exist like p in li
                {
//System.out.println("merge:: rj!leaf rj.elementcnt: "+rj.getElementCount());
                    Element newChild1 = createBranchElement(rec.parent, child1.getAttributes()); // for ol
//System.out.println("merge:: rj!leaf created right new branchelem "+newChild1.getName()+" children will be added later");// no children at this point so toString() crashes with null ptr

//System.out.println("merge:: rj!leaf rj.endoffset: "+rj.getEndOffset()+" rj.startoffset: "+rj.getStartOffset()+" rmoffs1: "+rmOffs1+" rj: "+rj);
                    // rebuild right side
                    Vector<Element> children1 = new Vector<Element>();
                    // is the entire affected element been deleted?
                    // if not, xfer remaining content to a new child
                    // don't call this for li->p-implied, only for tr->td->p-implied
                    if (rj.getEndOffset()>rmOffs1)  // not entire child will be removed
                    {
                        Vector elemVct = rebuildRightElement(child1, rj, rmOffs1);
                        for (int x=0; x<elemVct.size(); x++) {
                            children1.addElement(clone(newChild1, (Element)elemVct.elementAt(x)));
                        }
                    }

                    // transfer the right side after the affected element
                    n = child1.getElementCount();
                    // more children exist and nothing was just created before these children
                    if (n>rjIndex+1 && children1.size()==0)
                    {
                        //make sure we are not creating a li->ul|ol without p-implied between
                        Element grandChild= child1.getElement(rjIndex+1);
                        if (newChild1.getName().equals(HTML.Tag.LI.toString()) &&
                            (grandChild.getName().equals(HTML.Tag.UL.toString()) ||
                                grandChild.getName().equals(HTML.Tag.OL.toString())))
                        {
                            Element childlist = grandChild.getElement(0);
                            System.err.println("merge:: rj!leaf ***** special handling needed for nested list");
                            // move lists children into parent
                            for (int i=0; i<childlist.getElementCount(); i++) {
                                children1.addElement(clone(newChild1, childlist.getElement(i)));
                            }

                            if (grandChild.getElementCount()==1){ // only one list item
                                rjIndex++; // skip this grandchild
                            }
                            else
                            {
                                // remove the first child and rebuild rest of list
                                Vector<Element> children2 = new Vector<Element>();
                                c = null;
                                for (int i=1; i<grandChild.getElementCount(); i++) {
                                    children2.addElement(grandChild.getElement(i));
                                }

                                c = new Element[children2.size()];
                                children2.copyInto(c);
                                ((BranchElement)grandChild).replace(0, grandChild.getElementCount(), c);
                            }
                        }
                    }

                    for (int i = rjIndex+1; i < n; i++) {
//System.out.println("merge:: rj!leaf xferring right gc starting with gc after affected gc xfer elem: "+child1.getElement(i));
                        children1.addElement(clone(newChild1, child1.getElement(i)));
                    }

                    // install the children
                    if (children1.size()>0)
                    {
                        c = new Element[children1.size()];
                        children1.copyInto(c);
                        // new right side child and its grandchildren
//for(int i=0; i<c.length; i++) System.out.println("merge:: rj!leaf created "+c[i]);
                        ((BranchElement)newChild1).replace(0, 0, c);

//System.out.println("merge:: rj!leaf rec.parent: "+rec.parent+" rec.added: "+newChild1);
                        rec.added.addElement(newChild1);
                    }
                    while(!rj.isLeaf())
                    {
                        child1 = rj;
                        rjIndex = child1.getElementIndex(rmOffs1);
                        rj = child1.getElement(rjIndex);
                    }
                }

//at this point i need the right child that has the content to be moved to the left side...

                // merge the left child and the right child :: both of them must be content!!
                // transfer the join/middle
//System.out.println("merge:: try to join affected left and right grandchildren");
                // if attributes differ, don't combine them or you will lose things like formatting, bold, italic
                // don't join <br> leaves!!
                isBR = lj.getName().equals("br");
                if (!isBR && lj.getAttributes().isEqual(rj.getAttributes())) // attributes match
                {
                    // if attrib match, then create a new element with the left and right grandchildren merged
                    Element e = createLeafElement(newChild, lj.getAttributes(), lj.getStartOffset(),
                        rj.getEndOffset());
//System.out.println("merge:: xferring newly joined grandchild: "+e);
                    children.addElement(e);
                } else {
                    // if left side will be completely removed, use attributes from right side and join
                    // this prevents empty <i></i> tags because of an empty content element
                    if(!isBR && lj.getStartOffset()>=rmOffs0 && lj.getEndOffset()<=rmOffs1)
                    {
                        // create a new element with the left and right grandchildren merged using right attribs
                        Element e = createLeafElement(newChild, rj.getAttributes(), lj.getStartOffset(),
                            rj.getEndOffset());
//System.out.println("merge:: left will be removed xferring newly joined grandchild: "+e);
                        children.addElement(e);
                    }
                    else
                    {
                        // can't join so add each separately
                        Element e = clone(newChild, lj);
//System.out.println("merge:: xferring left grandchild directly  lj: "+lj+" e: "+e);
                        children.addElement(e);
                        e = clone(newChild, rj);
//System.out.println("merge:: xferring right grandchild directly  rj: "+rj+" e: "+e);
                        children.addElement(e);
                    }
                }

                // transfer the right side after the affected element
                n = child1.getElementCount();
                for (int i = rjIndex+1; i < n; i++)
                {
//System.out.println("merge:: xferring right grandchildren starting with gc after affected gc xfer elem: "+child1.getElement(i));
                    children.addElement(clone(newChild, child1.getElement(i)));
                }
            }
            else
            {
//System.out.println("merge:: left side not a leaf.. recursing down");
                // left side is not a leaf yet, drill down farther
                Element e = merge(rec, child0, lj, child1, rmOffs0, rmOffs1);
                children.addElement(clone(newChild, e));
            }

            // install the new children
            c = new Element[children.size()];
//System.out.println("merge:: created "+c.length+" children");
            children.copyInto(c);
//for(int i=0; i<c.length; i++) System.out.println("new child: "+c[i]);
            ((BranchElement)newChild).replace(0, 0, c);

//System.out.println("merge:: *********** returning elem: "+newChild);
            return newChild;
        }

        /*********************************************************************************
        * When the left side is completely removed the right side must be rebuilt using
        * startoffset of left side.  This is needed to remove the left structure, unfortunately
        * at this level we can't distinguish if the user pressed delete and wanted to pull up the
        * following contents or selected the new line and pressed delete to remove the entire structure.
        */
//private int level=0;
        private Element mergeRightSide(Element parent, Element child1, int rmOffs0, int rmOffs1)
        {
    //System.out.println((level++)+" mergeRightSide entered rmoffs0: "+rmOffs0+" rmOffs1: "+rmOffs1+" for parent "+parent+" child1: "+child1);
            // if left elements are completely removed a new left child must not be created with left attributes
            // the right child must be rebuilt using the start offset of the left child..

            Element newChild = createBranchElement(parent, child1.getAttributes());  // children added later
    //System.out.println(level+" mergeRightSide:: created new branchelem "+newChild.getName()+" children will be added later");// no children at this point so toString() crashes with null ptr

            // get affected grandchildren
            int rjIndex = child1.getElementIndex(rmOffs1);
            Element rj = child1.getElement(rjIndex);
            int n = 0;
    //System.out.println(level+" mergeRightSide:: index of the right grandchild affected rjIndex: "+rjIndex+" rj: "+rj);

            // must set startoffset of left side on right side
            // must get down to content on right side
            // take rj, find where rmoffs1 falls and get content from there to end of listitem structure
            // rebuild child1 with remaining listitems
            if(!rj.isLeaf())  // li or p-implied? or tr
            {
    //System.out.println(level+" mergeRightSide:: rj!leaf rj.endoffset: "+rj.getEndOffset()+" rj.startoffset: "+rj.getStartOffset()+" rmoffs1: "+rmOffs1+" rj: "+rj);
                // rebuild right side
                Vector<Element> children1 = new Vector<Element>();

                Element newGC = mergeRightSide(child1, rj, rmOffs0, rmOffs1);
    //System.err.println(level+" newGC "+newGC);

                // special handling for li->ol|ul
                if (newChild.getName().equals(HTML.Tag.LI.toString()) &&
                    (newGC.getName().equals(HTML.Tag.OL.toString()) ||
                        newGC.getName().equals(HTML.Tag.UL.toString())))
                {
                    //make sure we are not creating a li->ul|ol without p-implied between
                    Element childlist = newGC.getElement(0);
                    System.err.println("mergeRightSide:: rj!leaf ***** special handling needed for nested list");
                    // move lists children into parent
                    for (int i=0; i<childlist.getElementCount(); i++) {
                        children1.addElement(clone(newChild, childlist.getElement(i)));
                    }

                    if (newGC.getElementCount()>1) // more than one list item
                    {
                        // remove the first child and rebuild rest of list
                        Vector<Element> children2 = new Vector<Element>();
                        Element[] c = null;
                        for (int i=1; i<newGC.getElementCount(); i++) {
                            children2.addElement(newGC.getElement(i));
                        }

                        c = new Element[children2.size()];
                        children2.copyInto(c);
                        ((BranchElement)newGC).replace(0, newGC.getElementCount(), c);

                        children1.addElement(clone(newChild, newGC));
                    }
                }
                else // not special case nested list
                {
                    children1.addElement(clone(newChild, newGC));
                }

                // transfer the right side after the affected element
                n = child1.getElementCount();
                for (int i = rjIndex+1; i < n; i++) {
    //System.out.println(level+" mergeRightSide:: rj!leaf xferring right gc starting with gc after affected gc xfer elem: "+child1.getElement(i));
                    children1.addElement(clone(newChild, child1.getElement(i)));
                }

                // install the children
                if (children1.size()>0)
                {
                    Element[] c = new Element[children1.size()];
                    children1.copyInto(c);
    //System.out.println(level+" mergeRightSide:: rj!leaf created "+c.length+" children1 "+c[0]);
                    ((BranchElement)newChild).replace(0, 0, c);
                }
            }
            else
            {
    //System.out.println(level+" mergeRightSide:: join affected left.startoffset and right grandchildren");
                Vector<Element> children = new Vector<Element>();
                Element[] c = null;

                // create a new element with the left start offset and right attrib and end offset
                Element e = createLeafElement(newChild, rj.getAttributes(), rmOffs0,
                    rj.getEndOffset());
    //System.out.println(level+" mergeRightSide:: xferring newly joined grandchild: "+e);
                children.addElement(e);

                // transfer the right side after the affected element
                n = child1.getElementCount();
                for (int i = rjIndex+1; i < n; i++)
                {
    //System.out.println(level+" mergeRightSide:: xferring right grandchildren starting with gc after affected gc xfer elem: "+child1.getElement(i));
                    children.addElement(clone(newChild, child1.getElement(i)));
                }

                // install the new children
                c = new Element[children.size()];
    //System.out.println(level+" mergeRightSide:: created "+c.length+" children");
                children.copyInto(c);
    //for(int i=0; i<c.length; i++) System.out.println(level+" mergeRightSide:: new child: "+c[i]);
                ((BranchElement)newChild).replace(0, 0, c);
            }
    //System.out.println((level--)+" mergeRightSide:: *********** returning elem: "+newChild);
            return newChild;
        }

        /*********************************************************************************
        * When the right side is a branch, it must be rebuilt after content is merged
        */
        private Vector rebuildRightElement(Element parent, Element child1, int rmOffs1)
        {
            Vector<Element> elemVct = new Vector<Element>();
    //System.out.print("bre:: entered parent: "+parent+" child1: "+child1);
            // return parent of the leaf
            int rjIndex = child1.getElementIndex(rmOffs1);
            Element rj = child1.getElement(rjIndex);
            Element newChild = null;
            Vector<Element> children = new Vector<Element>();
            int endIndex =0;
            int n=0;

            if (rj==null || rj.getElementCount()==0 )
            {
    //System.out.println("bre:: returning null rj");
                // don't build anymore, the grandchild is a leaf
                //return elemVct;
            }else{
                // if a paragraph is part of the affected list item child1 will have more than 1 element
                // and can't be merged directly.. allow the content to merge but take rest of elements
                // and rebuild parent with new children
        // problems with nested lists, because <li><ul> requires content with newline between li and ul
        // deletion of newline breaks structure requirements.
                if ((rjIndex+1)<child1.getElementCount()&&rj.getElement(0).isLeaf())
                {
                    Vector<Element> children2 = new Vector<Element>();
                    // create a new rj
                    Element newChild2 = createBranchElement(parent, child1.getAttributes()); // for li

                    int nextId = rjIndex+1;
                    // if nested lists, must not copy child list if p-implied was removed
                    if (newChild2.getName().equals(HTML.Tag.LI.toString()) &&  // new child is li
                        rj.getName().equals(HTML.Tag.IMPLIED.toString())&&  // removing p-implied
                        (child1.getElement(nextId).getName().equals(HTML.Tag.UL.toString())|| // next child is
                            child1.getElement(nextId).getName().equals(HTML.Tag.OL.toString()))) // ul or ol
                    {
                        Element childlist = child1.getElement(nextId);
                        // move lists children into parent
                        for (int i=0; i<childlist.getElementCount(); i++) {
                            elemVct.addElement(clone(parent, childlist.getElement(i)));
                        }
                        nextId++; // skip this child, already handled
                        System.err.println("bre:: specialcase handling to merge nested lists");
                    }

        //System.out.println("bre:: created new branchelem2 "+newChild2.getName()+" children will be added later");// no children at this point so toString() crashes with null ptr
                    n = child1.getElementCount();
                    for (int i = nextId; i < n; i++) {
        //System.out.println("bre:: xferring right c starting with c after affected c xfer elem: "+child1.getElement(i));
                        children2.addElement(clone(newChild2, child1.getElement(i)));
                    }
                    // install the children
                    if (children2.size()>0)
                    {
                        Element[] c = new Element[children2.size()];
                        children2.copyInto(c);
        //System.out.println("bre:: created "+c.length+" children2 "+c[0]);
                        ((BranchElement)newChild2).replace(0, 0, c);

                        elemVct.addElement(newChild2);
                    }
                    //return elemVct;
                }else{
                    if (rj.getElement(0).isLeaf())
                    {
            //System.out.println("bre:: returning null rj is leaf");
                        // don't build anymore, the grandchild is a leaf
                        //return elemVct;
                    }else{
                        // make sure we need to create this branch, if all of its children will be deleted or
                        // merged, it is not needed
                        // if rj is the last child and part of it is in the range, this branch is not needed
                        if (rjIndex+1==child1.getElementCount() &&
                            rj.getStartOffset()<=rmOffs1)
                        {
                //System.out.print("bre:: rj is last child and will be merged rj: "+rj);
                            // but does rj have children??
                            // if nested lists, must copy child listitems if p-implied was removed
                            if (child1.getName().equals(HTML.Tag.LI.toString()) &&  // affected child is li
                                (rj.getName().equals(HTML.Tag.UL.toString())|| // grandchild is
                                    rj.getName().equals(HTML.Tag.OL.toString()))) // ul or ol
                            {
                                int rj2index = rj.getElementIndex(rmOffs1);
                                // move lists children into parent
                                for (int i=rj2index+1; i<rj.getElementCount(); i++) {
                                    elemVct.addElement(clone(parent, rj.getElement(i)));
                                }
                //System.err.println("bre:: specialcase handling2 to merge nested lists");
                            }

                            // don't build anymore, the grandchild is last child
                            //return elemVct;
                        }else{
                            newChild = createBranchElement(parent, child1.getAttributes());
                    //System.out.println("bre:: created new branchelem "+newChild.getName()+" children will be added later");// no children at this point so toString() crashes with null ptr

                            // rebuild right side
                            // is the entire affected element been deleted?
                            // if not, xfer remaining content to a new child
                    //System.out.println("bre:: child1.endoffset: "+child1.getEndOffset()+" startoffset: "+
                    //child1.getStartOffset()+" rmoffs1: "+rmOffs1+" child1: "+child1);

                            if (rj.getEndOffset()>rmOffs1)  // not entire child will be removed, rebuild structure
                            {
                    //System.out.println("bre will recurse");
                                //must rebuild this structure
                                Vector elemVct2 = rebuildRightElement(child1, rj, rmOffs1);
                                for(int x=0; x<elemVct2.size(); x++) {
                                    children.addElement(clone(newChild,(Element)elemVct2.elementAt(x)));
                                }
                            }

                            // transfer the right side after the affected element
                            n = child1.getElementCount();
                            endIndex = rjIndex;
                            if (children.size()>0|| rmOffs1>=rj.getStartOffset()) { // already added the affected child
                                endIndex++;
                            }
                            for (int i = endIndex; i < n; i++) {
                    //System.out.println("bre:: xferring right gc starting with gc after affected gc xfer elem: "+child1.getElement(i));
                                children.addElement(clone(newChild, child1.getElement(i)));
                            }

                            // install the children
                            if (children.size()>0)
                            {
                                Element[] c = new Element[children.size()];
                                children.copyInto(c);
                    //System.out.println("bre:: created "+c.length+" children "+c[0]);
                                ((BranchElement)newChild).replace(0, 0, c);
                            }
                            elemVct.addElement(newChild);
                        }
                    }
                }
            }
            return elemVct;
        }

        boolean canJoin(Element e0, Element e1)
        {
            boolean leaf0 = false;
            boolean leaf1 = false;
            String name0 = null;
            String name1 = null;
            int id0 = 0;
            int id1 = 0;
            Element child0 = null;
            Element child1=null;
            boolean join = false;
    //System.out.println("canJoin:: entered e0: "+e0+" e1: "+e1);
            if ((e0 == null) || (e1 == null)) {
                join = false;//return false;
            }else{
                // Don't join a leaf to a branch.
                leaf0 = e0.isLeaf();
                leaf1 = e1.isLeaf();
                if(leaf0 != leaf1) {
                    join = false;//return false;
                }else{
                    if (leaf0)
                    {
                        // Only join leaves if the attributes match, otherwise
                        // style information will be lost. leaves will be concatenated instead of joined
                        // WSS prevent join if it will result in losing the terminating new line content leaf
                        Element parent = e1.getParentElement();
                        if(parent.getEndOffset()==e1.getEndOffset())
                        {
            //System.out.println("canJoin:: *********** preventing join of terminating new line");
                            join = false; //return false;
                        }else {
            //System.out.println("leaf0 attr");
            //showAttributes(System.out,e0.getAttributes());
            //System.out.println("leaf1 attr");
            //showAttributes(System.out,e1.getAttributes());
                            //return e0.getAttributes().isEqual(e1.getAttributes());
                            join = e0.getAttributes().isEqual(e1.getAttributes());
                        }
                    }else{
                        // recurse down non-leafs to leaf to verify join-ability
                        // find problems now to force code to use the mergexx() path and handle
                        // join problems in one place
                        name0 = e0.getName();
                        name1 = e1.getName();
                //System.out.println("canJoin: checking e0 name: "+name0+" e1 name: "+name1);

                        // get affected children
                        id0 = e0.getElementIndex(x_offset);
                        id1 = e1.getElementIndex(x_offset+x_length);
                        child0 = e0.getElement(id0);
                        child1 = e1.getElement(id1);
                        // if entire child1 will be removed don't join it v111
                        // if this isn't done it is possible to create elements with 0 length
                        // and other following actions can do strange things, like insert another body element
                        if (x_offset+x_length==getLength()) //v111
                        {
                //System.err.println("**** prevent join.. all of right child will be deleted");
                            join = false;//return false;
                        }else{
                            // if children are leaves, they are content elements and can be joined
                            // make sure parent(e0, e1) can be joined
                            if (child0.isLeaf()&& child1.isLeaf())
                            {
                                if (name0!=null)
                                {
                                    if (name0.equals(name1)) {
                                        //return true;
                                        join = true;
                                    }else{
                                        // allow p and p-implied to be a match for nested structure at the
                                        // same level
                                        if (name1!=null && name0.startsWith("p")&& name1.startsWith("p"))
                                        {
                        //System.err.println("canjoin:: ALLOWING P and PIMPLIED to join!!");
                                        //    return true;
                                            join = true;
                                        }
                                        else {
                                        //    return false;
                                            join = false;
                                        }
                                    }
                                }else {
                                    //return false;
                                    join = false;
                                }
                            }else{
                                //return canJoin(child0,child1);
                                join = canJoin(child0,child1);
                            }
                        }
                    }
                }
            }
            return join;
        }

        /**
         * Joins the two elements carving out a hole for the
         * given removed range.
         */
        Element join(Element p, Element left, Element right, int rmOffs0, int rmOffs1)
        {
            Element newjoinElem;
            if (left.isLeaf() && right.isLeaf())
            {
//System.out.print("join p: "+p+" left: "+left+" right: "+right);
                // only content and possibly br,hr can be leaves???
                // attributes from left side are propagated across entire leaf,
                // any attributes on right side are lost
                // create a new leaf element combining left and right using
                // left element attributes, spanning the original offset of both leaves
                //return
                newjoinElem = createLeafElement(p, left.getAttributes(), left.getStartOffset(),
                    right.getEndOffset());
            } else if ((!left.isLeaf()) && (!right.isLeaf()))
            {
                // this will now get entered if the branches have different names but the children
                // are the same.. such as ol and ul branches or h3 and p branches

                // join two branch elements.  This copies the children before
                // the removal range on the left element, and after the removal
                // range on the right element.  The two elements on the edge
                // are joined if possible and needed.
                // if left startoffset==rmOffs0, then all of left side will be removed so use the attributes from the right
// not true, right side will be pulled up into left side so use left attributes???
//                Element to = createBranchElement(p, (left.getStartOffset()==rmOffs0 ?
//                          right.getAttributes():left.getAttributes()));
                AttributeSet attrs = left.getAttributes();
                Element to = null;
                int ljIndex = 0;
                int rjIndex = 0;
                Element lj = null;
                Element rj = null;
                Vector<Element> children = new Vector<Element>();
                int n = 0;
                Element[] c = null;

//System.out.println("join:: rmOffs0: "+rmOffs0+" rmOffs1: "+rmOffs1+" left attrs");
//showAttributes(System.out,attrs);
                // if p-implied, use right attributes.. this will preserve p getting joined to p-implied
                if ((left.getName().equals(HTML.Tag.IMPLIED.toString()) &&
                        !right.getName().equals(HTML.Tag.IMPLIED.toString()) &&  // don't replace ol with ul, but preserve p vs p-implied
                        left.getStartOffset()==rmOffs0) ||
                    rmOffs0==0 ||    // at start of doc
                    (left.getStartOffset()==rmOffs0 && left.getEndOffset()<=rmOffs1)) { // left side is removed
                    attrs = right.getAttributes();
                }
//System.out.println("join:: using attrs");
//showAttributes(System.out,attrs);

                to = createBranchElement(p, attrs);
//System.out.println("created new to (parent) element "+to.getName());
                ljIndex = left.getElementIndex(rmOffs0);
                rjIndex = right.getElementIndex(rmOffs1);
                lj = left.getElement(ljIndex);
                rj = right.getElement(rjIndex);
//System.out.println("join: ljIndex: "+ljIndex+" lj: "+lj+" rjIndex: "+rjIndex+" rj: "+rj);
                // left startoffset is greater than or equal to removeOffset0
                // is it part of the deleted range then set to null
                if (lj.getStartOffset() >= rmOffs0) {
                    lj = null;
                }
                // right startoffset is equal to removeOffset1
                // if it's start == the end of the deleted range then set to null
                if (rj.getStartOffset() == rmOffs1) {
                   // rj = null;  WSS don't do this, it prevents merge of deletion of trailing newline
                }

                // transfer the left
                for (int i = 0; i < ljIndex; i++) {
//System.out.println("join: xferring left: "+left.getElement(i));
                    children.addElement(clone(to, left.getElement(i)));
                }

                // transfer the join/middle
                if (canJoin(lj, rj)) {
                    // if canjoin, then create a new element with the left and right grandchildren merged
                    Element e = join(to, lj, rj, rmOffs0, rmOffs1);
                    children.addElement(e);
//System.out.println("join: joined middle: "+e);
                } else {
/* this code fails for nested structure but shouldn't get here anymore after canjoin() recursive chg*/
// this section runs when two leafs attributes don't match and content is copied into a p-implied
                    if (lj != null) {
                        children.addElement(clone(to, lj));
//System.out.println("join: can't join xferring left: "+lj);
                    }
                    if (rj != null)
                    {
// this is wrong if p is nested and p-implied tries to join with p, got around it
// by allowing p and p-implied to join in canjoin() but fails here with deletion of
// nested list's term new line and another list follows.. structure is wrong, ok if p
// follows
                        // if nested content is here, i must not just clone it, the structure
                        // must be rebuilt with out the deleted structure
                        if (!rj.isLeaf())
                        {
                            int rj2Index = rj.getElementIndex(rmOffs1);
                            //Element rj2 = rj.getElement(rj2Index);
//System.out.println("join:: rj2Index: "+rj2Index+" rj2: "+rj2);
                            if (rj2Index>0)
                            {
                                Vector<Element> children2 = new Vector<Element>();
                                // create a new rj
                                Element newRj2 = createBranchElement(to,rj.getAttributes()); // for li
                                n = rj.getElementCount();
                                for (int i = rj2Index; i < n; i++) {
//System.out.println("join:: xferring right c starting with c after affected c xfer elem: "+rj.getElement(i));
                                    children2.addElement(clone(newRj2, rj.getElement(i)));
                                }
                                // install the children
                                if (children2.size()>0)
                                {
                                    c = new Element[children2.size()];
                                    children2.copyInto(c);
//System.out.println("join:: created "+c.length+" children2 "+c[0]);
                                    ((BranchElement)newRj2).replace(0, 0, c);

                                    children.addElement(newRj2);
                                }
                            }
                            else
                            {
                                children.addElement(clone(to, rj));
//System.out.println("join: rj2index=0 can't join xferring right: "+rj);
                            }
                        }
                        else
                        {
                            children.addElement(clone(to, rj));
//System.out.println("join: rj is leaf can't join xferring right: "+rj);
                        }
                    }
                }

                // transfer the right
                n = right.getElementCount();
                for (int i = (rj == null) ? rjIndex : rjIndex + 1; i < n; i++) {
//System.out.println("join: xferring right: "+right.getElement(i));
                    children.addElement(clone(to, right.getElement(i)));
                }

                // install the children
                c = new Element[children.size()];
                children.copyInto(c);
//for(int i=0; i<c.length; i++) System.out.println("adding child "+c[i]+" to "+to.getName());
                ((BranchElement)to).replace(0, 0, c);
                //return
                newjoinElem = to;
            } else {
                throw new RuntimeException(
                    "No support to join leaf element with non-leaf element");
            }
            return newjoinElem;
        }

        /**
         * Creates a copy of this element, with a different
         * parent.
         *
         * @param parent the parent element
         * @param clonee the element to be cloned
         * @return the copy
         */
        public Element clone(Element parent, Element clonee)
        {
            Element e = null;
            int n = 0;
            Element[] children = null;

            if (clonee.isLeaf()) {
                e = createLeafElement(parent, clonee.getAttributes(),
                    clonee.getStartOffset(),
                    clonee.getEndOffset());
            }else {
                e = createBranchElement(parent, clonee.getAttributes());
                n = clonee.getElementCount();
                children = new Element[n];
                for (int i = 0; i < n; i++) {
                    children[i] = clone(e, clonee.getElement(i));
                }
                ((BranchElement)e).replace(0, 0, children);
            }
            return e;
        }

        /**
         * Inserts an update into the document.
         *
         * @param data the elements to insert
         */
        protected void insertUpdate(ElementSpec[] data)
        {
/*System.out.println("\nElementBuffer::insertUpdate entered doc.len: "+getLength()+" data.len "+data.length);
System.out.println("offset: "+x_offset+" length: "+length+" endoffset: "+x_endOffset);
StringBuffer sb = new StringBuffer();
for(int i=0; i<data.length; i++)
{
System.out.println("elemBuf:insertUpdate() elemspec["+i+"] "+data[i]+" x_offset: "+
                data[i].getOffset());
showAttributes(System.out,data[i].getAttributes());
ElementSpec es = data[i];
if (es.getLength() > 0) {
    String str = null;
    if (es.getArray()!=null)// seems to be null if typed in, not entered as html
        str=new String(es.getArray(), es.getOffset(),  es.getLength());
    System.out.println("content *"+str+"*");
  sb.append(str);
}
}

System.out.println("elemBuf:insertUpdate(): total content to be inserted *"+sb.toString()+"*");
*/
            // push the path
            Element elem = x_root;
            int i=0;
            int n =0;
//System.out.println("\n\ninsertUpdate: find index of elem with offset: "+x_offset);
            int index = elem.getElementIndex(x_offset);
            while (! elem.isLeaf()) {
                Element child = elem.getElement(index);
//System.out.print("insertUpdate: elem: "+elem+" elem.cnt: "+elem.getElementCount()+" child at index: "+index+" "+child);
//System.out.println("insertUpdate: pushing index: "+(child.isLeaf() ? index : index+1));
// index +1 is pushed to keep track of the element count, fracture() uses it
                push(elem, (child.isLeaf() ? index : index+1));
                elem = child;
                index = elem.getElementIndex(x_offset);
            }

            // Build a copy of the original path.
            x_insertPath = new ElemChanges[x_path.size()];
            x_path.copyInto(x_insertPath);
/*System.out.println("insertUpdate: original path: ");
for(int i=0; i<x_path.size(); i++)
{
    ElemChanges ec = x_insertPath[i];
    System.out.println("insertUpdate: ec parent: " + ec.parent + " child index: " + ec.index);
}*/

            // Haven't created the fracture yet.
            x_createdFracture = false;

            // Insert the first content.
            x_recreateLeafs = false;
            if(data[0].getType() == ElementSpec.ContentType)
            {
//System.out.println("insertUpdate: first ElemSpec was content: insertFirstContent()");
                insertFirstContent(data);
                x_pos += data[0].getLength();
                i = 1;
            }
            else
            {
//System.out.println("insertUpdate: first ElemSpec was not content: fractureDeepestLeaf()");
                fractureDeepestLeaf(data);
                i = 0;
            }

            // fold in the specified subtree
            n = data.length;
            for (; i < n; i++) {
                insertElement(data[i]);
            }

            // Fracture, if we haven't yet.
            if(!x_createdFracture)
            {
//System.out.println("insertUpdate: calling fracture()");
                fracture(-1);
            }

//System.out.println("insertUpdate: calling pop() for path.size(): "+x_path.size());
            // pop the remaining path
            while (x_path.size() != 0) {
                pop();
            }

            // Offset the last index if necessary.
            if(x_offsetLastIndex && x_offsetLastIndexOnReplace) {
//ElemChanges ec = x_insertPath[x_insertPath.length - 1];
//System.out.println("insertUpdate: adjusting last index from: "+ ec.parent + " child index: " + ec.index +
//                    " removed " + ec.removed.size());
                x_insertPath[x_insertPath.length - 1].index++;
//System.out.println("insertUpdate: adjusting last index to: "+ ec.parent + " child index: " + ec.index +
//                    " removed " + ec.removed.size());
            }

//System.out.println("insertUpdate: fracturedChild: "+x_fracturedChild+" fracturedParent "+x_fracturedParent);

            // Make sure an edit is going to be created for each of the
            // original path items that have a change.
            for(int counter = x_insertPath.length - 1; counter >= 0; counter--)
            {
                ElemChanges change = x_insertPath[counter];
                if(change.parent == x_fracturedParent)
                {
                    change.added.addElement(x_fracturedChild);
//System.out.println("insertUpdate: adding child: "+x_fracturedChild.getName()+" to change.parent "+change.parent);
                }
                if((change.added.size() > 0 ||
                        change.removed.size() > 0) && !x_changes.contains(change))
                {
                    // PENDING(sky): Do I need to worry about order here?
                    x_changes.addElement(change);
//System.out.println("insertUpdate: adding change to changes vector: "+change);
                }
            }

            // An insert at 0 with an initial end implies some elements
            // will have no children (the bottom most leaf would have length 0)
            // this will find what element need to be removed and remove it.
            if (x_offset == 0 && x_fracturedParent != null &&
                data[0].getType() == ElementSpec.EndTagType)
            {
                ElemChanges change = null;
                int counter = 0;
                while (counter < data.length &&
                    data[counter].getType() == ElementSpec.EndTagType)
                {
                    counter++;
                }
                change = x_insertPath[x_insertPath.length - counter - 1];
//System.out.println("insertUpdate: adding "+change.parent.getElement(change.index-1).getName()+
//" to removed for: "+change.parent.getName());
                change.removed.insertElementAt(change.parent.getElement(--change.index), 0);
            }

//System.out.println("insertUpdate exiting");
        }

        /**
         * Determines if a fracture needs to be performed. A fracture
         * can be thought of as moving the right part of a tree to a
         * new location, where the right part is determined by what has
         * been inserted. <code>depth</code> is used to indicate a
         * JoinToFracture is needed to an element at a depth
         * of <code>depth</code>. Where the root is 0, 1 is the children
         * of the root...
         * <p>This will invoke <code>fractureFrom</code> if it is determined
         * a fracture needs to happen.
         */
        void fracture(int depth)
        {
//System.out.println("fracture:: entered depth: "+depth+" needRecreate: "+x_recreateLeafs);
            int cLength = x_insertPath.length;
            int lastIndex = -1;
            boolean needRecreate = x_recreateLeafs;
            ElemChanges lastChange = x_insertPath[cLength - 1];
//System.out.println("fracture:: lastChange.index: "+lastChange.index
//+" lastChange.parent: "+lastChange.parent+
//" lastChange.parent.getElementCount(): "+lastChange.parent.getElementCount());
            // Use childAltered to determine when a child has been altered,
            // that is the child at point of insertion is less than the element count.
            boolean childAltered = ((lastChange.index + 1) < lastChange.parent.getElementCount());
            int deepestAlteredIndex = (needRecreate) ? cLength : -1;
            int lastAlteredIndex = cLength - 1;

            x_createdFracture = true;
            // Determine where to start recreating from.
            // Start at - 2, as first one is indicated by recreateLeafs and
            // childAltered.
            for(int counter = cLength - 2; counter >= 0; counter--)
            {
                ElemChanges change = x_insertPath[counter];
//System.out.println("fracture loop: change.added.size(): "+
//change.added.size()+" change.parent: "+change.parent);
                if(change.added.size() > 0 || counter == depth)
                {
                    lastIndex = counter;
                    if(!needRecreate && childAltered)
                    {
                        needRecreate = true;
                        if(deepestAlteredIndex == -1) {
                            deepestAlteredIndex = lastAlteredIndex + 1;
                        }
                    }
                }
                if(!childAltered && change.index < change.parent.getElementCount())
                {
                    childAltered = true;
                    lastAlteredIndex = counter;
                }
            }
            if(needRecreate)
            {
                // Recreate all children to right of parent starting
                // at lastIndex.
                if(lastIndex == -1) {
                    lastIndex = cLength - 1;
                }
                fractureFrom(x_insertPath, lastIndex, deepestAlteredIndex);
            }
        }

        /**
         * Recreates the elements to the right of the insertion point.
         * This starts at <code>startIndex</code> in <code>changed</code>,
         * and calls duplicate to duplicate existing elements.
         * This will also duplicate the elements along the insertion
         * point, until a depth of <code>endFractureIndex</code> is
         * reached, at which point only the elements to the right of
         * the insertion point are duplicated.
         */
        void fractureFrom(ElemChanges[] changed, int startIndex, int endFractureIndex)
        {
//System.out.println("fractureFrom entered doc.len "+getLength()+" endoffset: "+x_endOffset+
//" offset: "+x_offset+" startIndex: "+startIndex+" endFractureIndex "+endFractureIndex);
            Element parent = null;
            // Recreate the element representing the inserted index.
            ElemChanges change = changed[startIndex];
//System.out.print("fractureFrom: starting parent "+change.parent);
            Element child, newChild;
            int changeLength = changed.length;

            if((startIndex + 1) == changeLength) {// last change element, use current child(will be leaf)
                child = change.parent.getElement(change.index);
            }
            else {
                child = change.parent.getElement(change.index - 1); // use previous child
            }
//System.out.print("fractureFrom: starting child "+child);
            if(child.isLeaf())
            {
                AttributeSet attrSet = child.getAttributes();
                // if text is typed after a br (after inserting a <p> right after the br)
                // the br gets recreated after the text, wrong
                // so prevent that here.. also this does not happen if br is at offset 0
                // so allow the recreation of br in this case
                if (child.getName().equals(HTML.Tag.BR.toString()) && child.getStartOffset() !=0)
                {
//System.out.println("********Not recreating br leaf in fractureFrom()");
                    // use attributes from br to get bold, etc
                    // but use CONTENT instead of BR
                    SimpleAttributeSet sas = new SimpleAttributeSet(attrSet);
                    sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.CONTENT);
                    attrSet = sas;
                }

                newChild = createLeafElement(change.parent, attrSet, Math.max(x_endOffset,
                        child.getStartOffset()), child.getEndOffset());
            }
            else {
                // prevent transfer of publish tag
                SimpleAttributeSet attrSet = new SimpleAttributeSet(child.getAttributes());
                Object obj = attrSet.getAttribute(PUBLISH_TAG_ATTR);
                if (obj!=null) {
                    attrSet.removeAttribute(PUBLISH_TAG_ATTR);
                }
                newChild = createBranchElement(change.parent, attrSet);
//System.out.println("fractureFrom: created branch newchild "+newChild.getName());
            }
            // save elements directly affected by the fracture, they will be used later
            x_fracturedParent = change.parent;
            x_fracturedChild = newChild;  // if not a leaf, children will be added later

//System.out.print("fractureFrom: fracturedParent "+x_fracturedParent);
//System.out.println("fractureFrom: fracturedChild "+x_fracturedChild.getName());

            // Recreate all the elements to the right of the insertion point.
            parent = newChild; // transfer children
//System.out.println("fractureFrom: setting parent to newchild "+parent.getName());
            // loop starting with change after parent
            while(++startIndex < endFractureIndex)
            {
                int kidsToMove = 0;
                Element[] kids =null;
                int moveStartIndex =0;
                int kidStartIndex = 1;
                boolean isEnd = ((startIndex + 1) == endFractureIndex);
                boolean isEndLeaf = ((startIndex + 1) == changeLength);

                // Create the newChild, a duplicate of the element at
                // index. This isn't done if isEnd and offsetLastIndex are true
                // indicating a join previous was done.
                change = changed[startIndex];
//System.out.print("fractureFrom: loop isEnd: "+isEnd+" isendleaf: "+isEndLeaf+" change.parent "+change.parent);

                // Determine the child to duplicate, won't have to duplicate
                // if at end of fracture, or offseting index.
                if(isEnd) {
                    if(x_offsetLastIndex || !isEndLeaf) {
                        child = null;
                    }
                    else {
                        child = change.parent.getElement(change.index);
                    }
                }
                else {
                    child = change.parent.getElement(change.index - 1);
                }
//System.out.println("fractureFrom: child to duplicate: "+child);
                // Duplicate it.
                if(child != null)
                {
                    if(child.isLeaf())
                    {
                        newChild = createLeafElement(parent, child.getAttributes(),
                            Math.max(x_endOffset, child.getStartOffset()),
                            child.getEndOffset());
                    }
                    else
                    {
                        newChild = createBranchElement(parent, child.getAttributes());
                    }
                }
                else {
                    newChild = null;
                }

                // Recreate the remaining children (there may be none).
                kidsToMove = change.parent.getElementCount() - change.index;
                if(newChild == null)
                {
                    // Last part of fracture.
                    if(isEndLeaf) {
                        kidsToMove--;
                        moveStartIndex = change.index + 1;
                    }
                    else {
                        moveStartIndex = change.index;
                    }
                    kidStartIndex = 0;
                    kids = new Element[kidsToMove];
                }
                else
                {
                    if(!isEnd) {
                        // Branch.
                        kidsToMove++;
                        moveStartIndex = change.index;
                    }
                    else {
                        // Last leaf, need to recreate part of it.
                        moveStartIndex = change.index + 1;
                    }
                    kids = new Element[kidsToMove];
                    kids[0] = newChild;
                }

                for(int counter = kidStartIndex; counter < kidsToMove; counter++)
                {
                    Element toMove =change.parent.getElement(moveStartIndex++);
//System.out.println("fractureFrom:  calling recreate for parent "+parent.getName()+" tomove: "+toMove.getName());
                    kids[counter] = recreateFracturedElement(parent, toMove);
                    change.removed.addElement(toMove);
                }
                ((BranchElement)parent).replace(0, 0, kids);
//for(int i=0; i<kids.length; i++) System.out.println("fractureFrom: added "+kids[i].getName()+" to parent: "+parent.getName());
                parent = newChild;
//if (parent!=null)
//System.out.println("fractureFrom: end of loop:  setting parent to newchild "+parent.getName());
            }  // end of loop

            // (WSS) if nested list!! p-implied must exist between li and ul/ol
            // check newly xferred structure and making modifications for invalid list structure there
            correctNestedStructure(x_fracturedChild);
        }

        /*****************************************************************************
        * look for nested structure improperly recreated by fracturing caused by insertion
        * nested lists must have a p-implied between li and ol/ul to be properly rendered
        */
        private void correctNestedStructure(Element parent)
        {
//System.out.print("correctNestedStructure:: entered parent: "+parent);

            Element child = null;
            Element c[] = null;
            // nested structure problem has parent of li
            if (!parent.getName().equals(HTML.Tag.LI.toString()))  // parent list item
            {
                // check to see if the parent is a list that needs to be checked
                if (parent.getName().equals(HTML.Tag.OL.toString()) ||
                    parent.getName().equals(HTML.Tag.UL.toString())) { // parent list
                    correctNestedStructure(parent.getElement(0));
                }
                //return;
            }else{
                child = parent.getElement(0);
    //System.out.print("correctNestedStructure:: numchildren: "+parent.getElementCount()+" child(0): "+child);
                // is there nested ul or ol without p-implied or p
                if (child.getName().equals(HTML.Tag.UL.toString()) ||
                    child.getName().equals(HTML.Tag.OL.toString()))
                {
                    int gcCount = 0;
                    Vector<Element> children = new Vector<Element>();
                    Vector<Element> grandChildren = new Vector<Element>();
                    // transfer first grandchild to parent as p-implied
                    // move p-implied to parent li from ul->li->pimplied
                    // and any other grandchildren
                    for (int i=0; i<child.getElement(0).getElementCount(); i++)
                    {
                        children.addElement(clone(parent, child.getElement(0).getElement(i)));
    //System.out.println("correctNestedStructure:: cloning parent: "+parent+" and grandchild[0]["+i+"]: "+child.getElement(0).getElement(i));
                    }
                    // basically remove first grandchild
                    gcCount = child.getElementCount();
    //System.out.println("correctNestedStructure:: child elem count "+gcCount);
                    for(int i=1; i<gcCount; i++)
                    {
                        grandChildren.addElement(child.getElement(i));
                    }

                    // reinstall the rest of the grandchildren
                    if (grandChildren.size()>0)
                    {
                        c= new Element[grandChildren.size()];
                        grandChildren.copyInto(c);
    //for(int i=0; i<c.length; i++) System.out.println("correctNestedStructure:: added "+c[i].getName()+" to child: "+child.getName());
                        ((BranchElement)child).replace(0, child.getElementCount(), c);
                    }

                    if (gcCount>1) { // put child back, it had more than one list item
                        children.addElement(child);
                    }

                    // transfer the rest of children
                    for (int i=1; i<parent.getElementCount(); i++)
                    {
                        children.addElement(parent.getElement(i));
                    }
                    c = new Element[children.size()];
                    children.copyInto(c);
    //for(int i=0; i<c.length; i++) System.out.println("correctNestedStructure:: added "+c[i].getName()+" to parent: "+parent.getName());
                    // replace all children in the parent
                    ((BranchElement)parent).replace(0, parent.getElementCount(), c);
                }
                else // first child was not a nested list
                {
                    // if it was pre or p and len==1 it was incorrectly xferred
                    if ((child.getName().equals(HTML.Tag.PRE.toString()) ||
                            child.getName().equals(HTML.Tag.P.toString())) &&
    //                   parent.getEndOffset()-parent.getStartOffset()==1) // li len=1
                        child.getEndOffset()-child.getStartOffset()==1)
                    {
                        System.err.println("correctNestedStructure:: li child was created with p or pre****************");
                        // remove p or pre and replace with pimplied
                        if (child.getName().equals(HTML.Tag.P.toString()))
                        {
                            MutableAttributeSet sas = (MutableAttributeSet)child.getAttributes();
                            sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.IMPLIED);
                        }
                        else
                        {
                            Vector<Element> children = new Vector<Element>();
                            // transfer first child to parent as p-implied
                            children.addElement(clone(parent, child.getElement(0)));
    //System.out.println("correctNestedStructure:: cloning parent: "+parent+" and grandchild[0]["+i+"]: "+child.getElement(0).getElement(i));

                            // transfer the rest of children
                            for (int i=1; i<parent.getElementCount(); i++)
                            {
                                children.addElement(parent.getElement(i));
                            }
                            c = new Element[children.size()];
                            children.copyInto(c);
    //for(int i=0; i<c.length; i++) System.out.println("correctNestedStructure:: added "+c[i].getName()+" to parent: "+parent.getName());
                            // replace all children in the parent
                            ((BranchElement)parent).replace(0, parent.getElementCount(), c);
                        }
                    }

                }

                // account for nesting more than one level deep, check to see that li->ol|ul does not exist
                if (parent.getElement(0).getName().equals(HTML.Tag.UL.toString()) ||
                    parent.getElement(0).getName().equals(HTML.Tag.OL.toString())) {
                    correctNestedStructure(parent);
                }
            }
        }

        /**
         * Recreates <code>toDuplicate</code>. This is called when an
         * element needs to be created as the result of an insertion. This
         * will recurse and create all the children. This is similiar to
         * <code>clone</code>, but determines the offsets differently.
         */
        Element recreateFracturedElement(Element parent, Element toDuplicate)
        {
            Element elem;
//System.out.println("recreateFracturedElement: entered parent: "+parent.getName()+" duplicate: "+toDuplicate.getName());
            if(toDuplicate.isLeaf())
            {
                elem= createLeafElement(parent, toDuplicate.getAttributes(),
                    Math.max(toDuplicate.getStartOffset(),x_endOffset),
                    toDuplicate.getEndOffset());
//System.out.print("recreateFracturedElement: new leaf: "+elem);
                //return elem;
            }
            else {
                // Not a leaf
                Element newParent = createBranchElement(parent, toDuplicate.getAttributes());
    //System.out.println("recreateFracturedElement: new parent: "+newParent.getName());
                int childCount = toDuplicate.getElementCount();
                Element[] newKids = new Element[childCount];
                for(int counter = 0; counter < childCount; counter++) {
                    newKids[counter] = recreateFracturedElement(newParent,toDuplicate.getElement(counter));
                }
                ((BranchElement)newParent).replace(0, 0, newKids);
                elem = newParent;//return newParent;
            }
            return elem;
        }

        /**
         * Splits the bottommost leaf in <code>path</code>.
         * This is called from insert when the first element is NOT content.
         */
        void fractureDeepestLeaf(ElementSpec[] specs)
        {
            // Split the bottommost leaf. It will be recreated elsewhere.
            ElemChanges ec = (ElemChanges) x_path.peek();
            Element child = ec.parent.getElement(ec.index);
            //System.out.print("fractureDeepestLeaf: parent "+ec.parent);
            //System.out.print("fractureDeepestLeaf: child "+child);
            // Inserts at offset 0 do not need to recreate child (it would
            // have a length of 0! because offset=0 and child.getStartOffset()=0).
            if (x_offset != 0) //WSS insert of <p><br></p> at 0 crashes with invalid children but this is needed when typing at start of ol following a p
            {
                Element newChild = createLeafElement(ec.parent, child.getAttributes(), child.getStartOffset(), x_offset);
            //System.out.print("fractureDeepestLeaf: created new leaf: "+newChild);
                ec.added.addElement(newChild);
            }
            //System.out.print("fractureDeepestLeaf: endoffset: "+x_endOffset+" removing leaf: "+child);
            ec.removed.addElement(child);
            if(child.getEndOffset() != x_endOffset) {
                x_recreateLeafs = true;
            }
            else {
                x_offsetLastIndex = true;
            }
//System.out.println("fractureDeepestLeaf: recreateLeafs: "+x_recreateLeafs+" offsetLastIndex: "+x_offsetLastIndex);
        }

        /**
         * Inserts the first content. This needs to be separate to handle
         * joining.
         */
        void insertFirstContent(ElementSpec[] specs)
        {
            ElementSpec firstSpec = specs[0];
//System.out.println("\ninsertFirstContent:: offset: "+x_offset+" specs.length: "+specs.length+" firstSpec "+firstSpec);
            ElemChanges ec = (ElemChanges) x_path.peek();
//System.out.print("insertFirstContent:: ec.parent: "+ec.parent);
            Element child = ec.parent.getElement(ec.index);
//System.out.print("insertFirstContent:: ec.index: "+ec.index+" child: "+child);
            int firstEndOffset = x_offset + firstSpec.getLength();
            boolean isOnlyContent = (specs.length == 1);
            boolean done = false;
//System.out.println("insertFirstContent:: firstEndOffset "+firstEndOffset+" isOnlyContent: "+isOnlyContent);

/*for(int i=0; i<specs.length;i++)
{
    System.out.println("specs["+i+"] "+specs[i]);
    showAttributes(System.out, specs[i].getAttributes());
}
*/
//          if (firstSpec.getDirection()==ElementSpec.OriginateDirection &&
//              child.getName().equals(HTML.Tag.BR.toString()))
//          {
//System.err.println("insertFirstContent::*********BR is first content O.. special handling to split content");
//}
            // must handle the condition where something is entered (typed) after a break.. the content
            // initially gets inserted into the br element.. br can not have anything but the space it
            // was created with
            if (firstSpec.getDirection()==ElementSpec.JoinPreviousDirection &&
                child.getName().equals(HTML.Tag.BR.toString()))
            {
//System.out.println("insertFirstContent::*********BR is first content JP.. special handling to split content");
/*
this code is a must for inserting <p> after <br>!!!!!!
but wrong if chars are typed after a <br>, it must be JoinNext in that case!!!!
1.3.0 incorrectly gives joinprev for that!!!
*/
                if (isOnlyContent)
                {
                    if (!using131)
                    {
                        // 1.3.0.r13a comes in with a JoinPrevious, 1.3.1sr1 comes in with JoinNext and works properly
                        // when chars are typed after a break
                        SimpleAttributeSet sas = new SimpleAttributeSet(firstSpec.getAttributes());
                        sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.CONTENT);
                        firstSpec = new ElementSpec(sas, firstSpec.getType());
                        // if two br are in a row, and user types between them, it can not
                        // be a join next, must check for contents and set direction based on that
                        // if br entered at offset=0, then type at 0 firstspec must be originate too
                        if(x_offset==0 || (ec.parent.getElementCount()>ec.index &&
                                ec.parent.getElement(ec.index+1).getName().equals(HTML.Tag.BR.toString())))
                        {
//System.out.println("insertFirstContent:: forcing Originate1");
                            firstSpec.setDirection(ElementSpec.OriginateDirection);
                        }
                        else
                        {
//System.out.println("insertFirstContent:: forcing JoinNext");
                            firstSpec.setDirection(ElementSpec.JoinNextDirection);
                        }
                    }
                }
                else{
                    // if enter pressed after break, 1.3.0 comes in with JoinPrevious, wrong
                    // 1.3.1 used Originate and works
                    if (specs.length==3 && !using131)  // ugly check
                    {
                        // replace firstspec, the attribute is br, needs to be content
//System.out.println("insertFirstContent:: forcing Originate2");
                        SimpleAttributeSet sas = new SimpleAttributeSet(firstSpec.getAttributes());
                        sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.CONTENT);
                        firstSpec = new ElementSpec(sas, firstSpec.getType());
                        firstSpec.setDirection(ElementSpec.OriginateDirection);
                    }
                    else
                    {
                        if (x_offset==0)
                        {
                            // inserting at start of doc infront of br, only called when offset=0?
                            // otherwise it the first ElementSpec is not Content and insertElement() gets called
                            // create content

                            // use attributes from br to get bold, etc
                            // but use CONTENT instead of BR
                            Element newE;
                            SimpleAttributeSet sas = new SimpleAttributeSet(firstSpec.getAttributes());
                            sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.CONTENT);

                            // new content
                            newE = createLeafElement(ec.parent, sas, x_offset, firstEndOffset);
                            ec.added.addElement(newE);
                            // restore br
                            newE = createLeafElement(ec.parent,
                                    child.getAttributes(), firstEndOffset, child.getEndOffset());

                            ec.added.addElement(newE);

                            ec.removed.addElement(child);
                            x_offsetLastIndex = true;//??
                            //return;
                            done = true;
                        }else{
                            if(child.getEndOffset() != firstEndOffset &&
                                !isOnlyContent)
                            {
    //System.out.println("insertFirstContent:: firstendoffset: "+firstEndOffset+" child "+child);
                                // Create the left split part with just original br
                                Element newE = createLeafElement(ec.parent,
                                    child.getAttributes(), child.getStartOffset(),
                                    child.getStartOffset()+1);//firstEndOffset);
    //System.out.println("insertFirstContent:: JoinPreviousDirection new leftchild "+newE);
                                ec.added.addElement(newE);
                                ec.removed.addElement(child);
                                // add rest of content, but not as BR
                                if (child.getStartOffset()+1!=firstEndOffset)
                                {
                                    // use attributes from br to get bold, etc
                                    // but use CONTENT instead of BR
                                    SimpleAttributeSet sas = new SimpleAttributeSet(child.getAttributes());
                                    sas.addAttribute(StyleConstants.NameAttribute,HTML.Tag.CONTENT);
                                    newE = createLeafElement(ec.parent,
                                        sas, child.getStartOffset()+1, firstEndOffset);
    //System.out.println("insertFirstContent:: JoinPreviousDirection new leftchild2 "+newE);
                                    ec.added.addElement(newE);
                                }
                                // Remainder will be created later.
                                if(child.getEndOffset() != x_endOffset)
                                {
                                    x_recreateLeafs = true;
    //System.out.println("insertFirstContent:: JoinPreviousDirection recreateLeafs: "+x_recreateLeafs);
                                }
                                else
                                {
                                    x_offsetLastIndex = true;
    //System.out.println("insertFirstContent:: JoinPreviousDirection offsetLastIndex: "+x_offsetLastIndex);
                                }
                            }
                            else {
                                x_offsetLastIndex = true;
                                x_offsetLastIndexOnReplace = true;
    //System.out.println("insertFirstContent:: JoinPreviousDirection else.. offsetLastIndex: "+x_offsetLastIndex+
    //" offsetLastIndexOnReplace: "+x_offsetLastIndexOnReplace);
                            }
                            //return;
                            done = true;
                        }
                    }
                }
            }

            if (!done) {
                switch(firstSpec.getDirection())
                {
                case ElementSpec.JoinPreviousDirection:
    //System.err.println("insertFirstContent:: JoinPreviousDirection");
                    if(child.getEndOffset() != firstEndOffset && !isOnlyContent)
                    {
                        // Create the left split part containing new content.
                        Element newE = createLeafElement(ec.parent,
                            child.getAttributes(), child.getStartOffset(),
                            firstEndOffset);
    //System.out.println("insertFirstContent:: JoinPreviousDirection new leftchild "+newE);
                        ec.added.addElement(newE);
                        ec.removed.addElement(child);
                        // Remainder will be created later.
                        if(child.getEndOffset() != x_endOffset)
                        {
                            x_recreateLeafs = true;
    //System.out.println("insertFirstContent:: JoinPreviousDirection recreateLeafs: "+x_recreateLeafs);
                        }
                        else
                        {
                            x_offsetLastIndex = true;
    //System.out.println("insertFirstContent:: JoinPreviousDirection offsetLastIndex: "+x_offsetLastIndex);
                        }
                    }
                    else {
                        x_offsetLastIndex = true;
                        x_offsetLastIndexOnReplace = true;
    //System.out.println("insertFirstContent:: JoinPreviousDirection else.. offsetLastIndex: "+x_offsetLastIndex+
    //" offsetLastIndexOnReplace: "+x_offsetLastIndexOnReplace);
                    }
                    // else Inserted at end, and is total length.
                    // Update index incase something added/removed.
                    break;
                case ElementSpec.JoinNextDirection:
    //System.out.println("insertFirstContent:: JoinNextDirection: offset: "+x_offset);
                    if(x_offset != 0) {
                        Element nextChild = null;
                        // Recreate the first element, its offset will have
                        // changed.
                        Element newE = createLeafElement(ec.parent,
                            child.getAttributes(), child.getStartOffset(),  x_offset);
    //System.out.println("insertFirstContent:: JoinNextDirection new leftchild "+newE);
                        ec.added.addElement(newE);
                        // Recreate the second, merge part. We do no checking
                        // to see if JoinNextDirection is valid here!
                        nextChild = ec.parent.getElement(ec.index + 1);
                        if(isOnlyContent)
                        {
                            newE = createLeafElement(ec.parent, nextChild.
                                getAttributes(), x_offset, nextChild.getEndOffset());
    //System.out.println("insertFirstContent:: JoinNextDirection isonlycontent new child "+newE);
                        }
                        else
                        {
                            newE = createLeafElement(ec.parent, nextChild.
                                getAttributes(), x_offset, firstEndOffset);
    //System.out.println("insertFirstContent:: JoinNextDirection !isonlycontent new child "+newE);
                        }
                        ec.added.addElement(newE);
                        ec.removed.addElement(child);
                        ec.removed.addElement(nextChild);
                    }
                    // else nothin to do.
                    // PENDING: if !isOnlyContent could raise here!
                    break;
                default:
                    {
                        Element newE = null;
                        // Inserted into middle, need to recreate split left
                        // new content, and split right.
                        if(child.getStartOffset() != x_offset) {
                            newE = createLeafElement(ec.parent,
                                child.getAttributes(), child.getStartOffset(),  x_offset);
                            // this builds leaf upto newly inserted content
                            ec.added.addElement(newE);
    //System.out.println("insertFirstContent:: inserted in middle.. split left child "+newE);
                        }
    //System.out.println("insertFirstContent:: removed child "+child);
                        // remove the original child
                        ec.removed.addElement(child);
                        // new content from insert point to calculated end point(offset+spec.content.length)
                        newE = createLeafElement(ec.parent, firstSpec.getAttributes(), x_offset, firstEndOffset);
    //System.out.println("insertFirstContent:: inserted in middle.. new child "+newE);
                        ec.added.addElement(newE);
                        // was all content inserted into this child? if so, offsetLastIndex
                        if(child.getEndOffset() != x_endOffset) {
                            // Signals need to recreate right split later.
                            x_recreateLeafs = true;
    //System.out.println("insertFirstContent:: inserted in middle.. recreateLeafs: "+x_recreateLeafs);
                        }
                        else {
                            x_offsetLastIndex = true;
    //System.out.println("insertFirstContent:: inserted in middle.. offsetLastIndex: "+x_offsetLastIndex);
                        }
                        break;
                    }
                }
            }
        }


        // tell buffer to allow xfer of publish attribute or not
        private void keybdUpdate(boolean b)
        {
            dropPublishAttr = b;
        }

        private Element x_root=null;
        private boolean using131=false;  // level of jre used
        private boolean dropPublishAttr = false;  // indicator for transfer of publish attr
                // it should not be xferred if user hits enter in a structure with publish set
                // because of base class code design i can't prevent it when ElemSpecs are generated
        private transient int x_pos=0;          // current position
        private transient int x_offset=0;
        private transient int x_length=0;
        private transient int x_endOffset=0;
        private transient Vector<ElemChanges> x_changes=null;  // Vector<ElemChanges>
        private transient Stack x_path=null;      // Stack<ElemChanges>
        private transient boolean x_insertOp=false;

        private transient boolean x_recreateLeafs=false; // For insert.

        /** For insert, path to inserted elements. */
        private transient ElemChanges[] x_insertPath=null;
        /** Only for insert, set to true when the fracture has been created. */
        private transient boolean x_createdFracture=false;
        /** Parent that contains the fractured child. */
        private transient Element x_fracturedParent=null;
        /** Fractured child. */
        private transient Element x_fracturedChild=null;
        /** Used to indicate when fracturing that the last leaf should be
         * skipped. */
        private transient boolean x_offsetLastIndex=false;
        /** Used to indicate that the parent of the deepest leaf should
         * offset the index by 1 when adding/removing elements in an
         * insert. */
        private transient boolean x_offsetLastIndexOnReplace=false;

        /*
         * Internal record used to hold element change specifications
         */
        private class ElemChanges {

            ElemChanges(Element parent, int index, boolean isFracture) {
                this.parent = parent;
                this.index = index;
                this.isFracture = isFracture;
                added = new Vector<Element>();
                removed = new Vector<Element>();
            }

            /**
            * info
            * @return String
            */
            public String toString() {
                return "added: " + added +NEWLINE_STR+ "removed: " + removed + NEWLINE_STR;
            }

            private Element parent;
            private int index;
            private Vector<Element> added;
            private Vector<Element> removed;
            private boolean isFracture;
        }

    }  // end ElementBuffer

    /**
     * debugging
     * @param ps
     * @param as
     */
    static public void showAttributes(PrintStream ps,AttributeSet as)
    {
        if (as==null)
        {
            ps.println("attributeset was null");
            //return;
        }else {
        //System.err.println(" Attrsetclass "+as.getClass().toString());
            if (as.getAttributeCount()==0) {
                ps.println("{ no attributes }");
            }
            else
            {
                Enumeration eNum = as.getAttributeNames();
                while(eNum.hasMoreElements())
                {
                    Object o = eNum.nextElement();
                    ps.println("  { Attr "+o.toString()+" = "+
                        as.getAttribute(o).toString()+ "}");
                    //ps.println("  { Attrclass "+o.getClass().toString()+" = "+
                    //  as.getAttribute(o).getClass().toString()+ "}");
                }
            }
        }
    }

/*void listall()
{
    System.err.println("this document: "+this);
    System.err.println("listenerList "+listenerList);
    System.err.println("listenerList  total listeners: "+listenerList.getListenerCount());

//   int  getListenerCount(Class t) // Returns the total number of listeners of the supplied type for this listener list.
    Object[] listeners = listenerList.getListenerList();
    // Process the listeners last to first, notifying
    // those that are interested in this event
    for (int i = listeners.length-2; i>=0; i-=2)
    {
System.err.println("listener["+i+"] "+listeners[i]);
    }

}*/
}
