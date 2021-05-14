// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.text.html.*;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.*;

import java.io.*;
import java.util.*;

/******************************************************************************
* This is derived to provide a modified HTMLWriter class to prevent writing
* HTML, HEAD and BODY tags.  As well as properly writing empty tags and avoiding
* pretty printing the contents.  The output from this class is used by the Spell
* checker and a 1:1 correspondence to the new lines in the rendered document is
* required to match the misspelled text in the document and on the view.
* The text given to spell check contains tags.. the caret location of the text
* must be calculated to account for the inserted tags.  Tags are attributes on the
* element and have no length.  They are held in a separate structure buffer, the
* ElementBuffer.
*
* Code was taken from HTMLWriter and modified.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLWriter.java,v $
// Revision 1.2  2013/07/18 18:43:51  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:20  wendy
// Initial code
//
// Revision 1.1  2007/04/18 19:47:48  wendy
// Reorganized JUI module
//
// Revision 1.5  2006/01/25 18:59:05  wendy
// AHE copyright
//
// Revision 1.4  2006/01/10 15:00:45  wendy
// Changes for table accessibility DQA requirements
//
// Revision 1.3  2005/12/16 15:52:14  wendy
// Changes for DQA for td, th tags
//
// Revision 1.2  2005/10/12 12:48:58  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:21  tony
// This is the initial load of OPICM
//
//
public class XMLWriter extends AbstractWriter implements XMLEditorGlobals
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    private static final char[] FOOL_JTEST = {'\n'};
    private static final String NEWLINE = new String(FOOL_JTEST);
    private static final int NBSP_CHAR = 160;
    private static final int ISO_8859_MIN_CHAR = 127;
    private static final int ISO_8859_MAX_CHAR = 256;
    private static final int LINE_LEN = 80;

    // Stores all elements for which end tags have to be emitted.
    private Stack<Element> blockElementStack = new Stack<Element>();
    private boolean inContent = false;
    private boolean inPre = false;
    /** When inPre is true, this will indicate the end offset of the pre
     * element. */
    private int preEndOffset=0;
    private boolean completeDoc =false;
    private Vector<StringBuffer> formattedVct=null;     // v111

    /*
     * Stores all embedded tags. Embedded tags are tags that are
     * stored as attributes in other tags. Generally they're
     * character level attributes.  Examples include
     * &lt;b&gt;, &lt;i&gt;, &lt;font&gt;, and &lt;a&gt;.
     */
    private Vector<Tag> tags=null;

    // Values for the tags.
    private Vector<Object> tagValues=null;

    // Used when writing out content.
    private Segment segment=null;

    // This is used in closeOutUnwantedEmbeddedTags.
    private Vector<Tag> tagsToRemove=null;

    // Set to true when entities (such as &lt;) should be replaced.
    private boolean replaceEntities=false;

    // Temporary buffer.
    private char[] tempChars=null;
    private Element rangeElement=null;

    /**
     * Creates a new XMLWriter.
     *
     * @param w   a Writer
     * @param doc  an HTMLDocument
     *
     */
    public XMLWriter(Writer w, HTMLDocument doc)
    {
        this(w, doc, 0, doc.getLength());
    }
    /**
     * Creates a new XMLWriter.
     *
     * @param doc an HTMLDocument
     * @param pos the document location from which to fetch the content
     * @param len the amount to write out
     */
    public XMLWriter(HTMLDocument doc, int pos, int len)
    {
        this(null,doc,pos,len);
    }

    /**
     * Creates a new XMLWriter.
     *
     * @param w  a Writer
     * @param doc an HTMLDocument
     * @param pos the document location from which to fetch the content
     * @param len the amount to write out
     */
    public XMLWriter(Writer w, HTMLDocument doc, int pos, int len)
    {
        super(w, doc, pos, len);
        init(doc, pos, len);
    }

    private void init(HTMLDocument doc, int pos, int len)
    {
//System.err.println("init pos: "+pos+" len "+len);
        int end = 0;
        Element left = null;
        Element right = null;

        formattedVct = new Vector<StringBuffer>();
        tags = new Vector<Tag>(10);
        tagValues = new Vector<Object>(10);
        tagsToRemove = new Vector<Tag>(10);

        completeDoc = (pos == 0 && len == doc.getLength());
        setLineLength(LINE_LEN);

        if (!completeDoc) {
            // find element that controls this range (needed for copy of nested structure)
            rangeElement = doc.getDefaultRootElement();

            end = pos+len-1;

            left = doc.getParagraphElement(pos);
            right = doc.getParagraphElement(end);
            // Need to find the element that contains pos and end
            if((left == right) &&
                (!left.getName().equals(HTML.Tag.IMPLIED.toString())))
            {
                rangeElement=left;
//System.err.println("range same elem "+rangeElement);
                //return;
            }else{
                if(left.getParentElement() == right.getParentElement())
                {
                    rangeElement = left.getParentElement();
                }
                else
                {
                    // Will only happen for text with more than 2 levels.
                    Vector<Element> leftParents = new Vector<Element>();
                    Vector<Element> rightParents = new Vector<Element>();
                    Element e = left;
                    int leftIndex = -1;
                    while(e != null) {
                        leftParents.addElement(e);
                        e = e.getParentElement();
                    }
                    e = right;
                    while(e != null && (leftIndex = leftParents.indexOf(e)) == -1) {
                        rightParents.addElement(e);
                        e = e.getParentElement();
                    }
                    if(e != null) {
                        // e identifies the common parent.
                        rangeElement = e;
                    }
                }
                if (rangeElement.getName().equals(HTML.Tag.LI.toString())) {
                    rangeElement = rangeElement.getParentElement();
                }
                // must get to structure that can be pasted, a th or td requires the tr and table
                if (rangeElement.getName().equals(HTML.Tag.TH.toString())) {
                    rangeElement = rangeElement.getParentElement();
                }
                if (rangeElement.getName().equals(HTML.Tag.TD.toString())) {
                    rangeElement = rangeElement.getParentElement();
                }
                // must get to structure that can be pasted, a tr requires the table
                if (rangeElement.getName().equals(HTML.Tag.TR.toString())) {
                    rangeElement = rangeElement.getParentElement();
                }
            }
        }
//System.err.println("range elem "+rangeElement);
    }

    /**
     * Iterates over the
     * Element tree and controls the writing out of
     * all the tags and its attributes.
     *
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     *
     */
    public void write() throws IOException, BadLocationException
    {
        Element current = null;
        Element next = null;
        ElementIterator it = null;

        formattedVct = null;  // v111 make sure this is not set
        if (getWriter()==null) {
            throw new IOException("Writer is null!");
        }

        it = getElementIterator();

        setCurrentLineLength(0);
        replaceEntities = false;
        setCanWrapLines(false);
        if (segment == null) {
            segment = new Segment();
        }

        inPre = false;
        while ((next = it.next()) != null)
        {
            // if we want to limit what is written out inRange() will verify that
            // the element is within the boundaries set when the writer was instantiated
            if (!inRange(next)) { // this also prevents writing out the extra <p>\n</p> added for final newline
                continue;
            }

//System.out.println("\ntop of loop (inRange)");
//System.out.print("Element: "+next);

            if (current != null)
            {
                // if next is child of current increment indent
                if (indentNeedsIncrementing(current, next)) {
//System.out.println(next.getName()+"(next) is a child of "+current.getName()+"(current)");
                }
                else
                if (current.getParentElement() != next.getParentElement())
                {
//System.out.println(next.getName()+"(next) and "+current.getName()+"(current) do not have the same parent");
//if (next.getParentElement()!=null)
//System.out.println(next.getParentElement().getName()+" (nextparent)");
//else
//System.out.println(next.getName()+"(next) parent is null");
//if (next.getParentElement()!=null)
//System.out.println(current.getParentElement().getName()+" (currentparent)");
//else
//System.out.println(current.getName()+"(current) parent is null");
                    // next and current are not siblings so emit end tags for items
                    // on the stack until the item on top of the stack, is the parent of the
                    // next.
                    Element top = (Element)blockElementStack.peek();
                    while (top != next.getParentElement())
                    {
                        // pop() will return top.
                        blockElementStack.pop();
                        if (!synthesizedElement(top)) {
                            endTag(top);
                        }
                        top = (Element)blockElementStack.peek();
                    }
                }
                else
                if (current.getParentElement() == next.getParentElement())
                {
//System.out.println(next.getName()+"(next) and "+current.getName()+"(current) are siblings");
                    // if next and current are siblings the indent level
                    // is correct.  But, we need to make sure that if current is
                    // on the stack, we pop it off, and put out its end tag.
                    Element top = (Element)blockElementStack.peek();
                    if (top == current)
                    {
                        blockElementStack.pop();
                        endTag(top);
                    }
                }
            }
            if (!next.isLeaf())
            {
//System.out.println(next.getName()+" is not a leaf");
                blockElementStack.push(next);
                startTag(next);
            } else {
//System.out.println(next.getName()+" is a leaf");
                emptyTag(next);
            }
            current = next;
        }  // end loop

        /* Emit all remaining end tags */

        /* A null parameter ensures that all embedded tags
           currently in the tags vector have their
           corresponding end tags written out.
        */
        closeOutUnwantedEmbeddedTags(null);

        while (!blockElementStack.empty())
        {
            current = (Element)blockElementStack.pop();
//System.out.println("clearing blockelemstack name: "+current.getName());
            if (!synthesizedElement(current))
            {
                endTag(current);
            }
        }

        segment.array = null;
    }

    /** v111
     * Iterates over the Element tree and controls the writing out of text with attributes.  NO structure tags
     * gets formatted text without structure, (will have bold, italic, etc)
     * the array will have one element for each p-implied or p structure's text
     * each array element will end in a new line, these will be discarded
     * for p and h1-h6 by parser, pre will maintain them
     *
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    StringBuffer[] writeFormattedText() throws IOException, BadLocationException
    {
        Element current = null;
        Element next = null;
        ElementIterator it = null;
        StringBuffer array[]= null;

        if (formattedVct==null) {
            formattedVct = new Vector<StringBuffer>();
        }
        formattedVct.removeAllElements();

        it = getElementIterator();

        setCurrentLineLength(0);
        replaceEntities = false;
        setCanWrapLines(false);
        if (segment == null) {
            segment = new Segment();
        }

        inPre = false;
        while ((next = it.next()) != null)
        {
            // if we want to limit what is written out inRange() will verify that
            // the element is within the boundaries set when the writer was instantiated
            if (!inRange(next)) { // this also prevents writing out the extra <p>\n</p> added for final newline
                continue;
            }

//System.out.println("\ntop of loop (inRange)");
//System.out.print("Element: "+next);

            if (current != null)
            {
                // if next is child of current increment indent
                if (indentNeedsIncrementing(current, next)) {
//System.out.println(next.getName()+"(next) is a child of "+current.getName()+"(current)");
                }
                else
                if (current.getParentElement() != next.getParentElement())
                {
//System.out.println(next.getName()+"(next) and "+current.getName()+"(current) do not have the same parent");
//if (next.getParentElement()!=null)
//System.out.println(next.getParentElement().getName()+" (nextparent)");
//else
//System.out.println(next.getName()+"(next) parent is null");
//if (next.getParentElement()!=null)
//System.out.println(current.getParentElement().getName()+" (currentparent)");
//else
//System.out.println(current.getName()+"(current) parent is null");
                    // next and current are not siblings so emit end tags for items
                    // on the stack until the item on top of the stack, is the parent of the
                    // next.
                    Element top = (Element)blockElementStack.peek();
                    while (top != next.getParentElement())
                    {
                        // pop() will return top.
                        blockElementStack.pop();
                        if (!synthesizedElement(top)) {
                            endTag(top);
                        }
                        top = (Element)blockElementStack.peek();
                    }
                }
                else
                if (current.getParentElement() == next.getParentElement())
                {
//System.out.println(next.getName()+"(next) and "+current.getName()+"(current) are siblings");
                    // if next and current are siblings the indent level
                    // is correct.  But, we need to make sure that if current is
                    // on the stack, we pop it off, and put out its end tag.
                    Element top = (Element)blockElementStack.peek();
                    if (top == current)
                    {
                        blockElementStack.pop();
                        endTag(top);
                    }
                }
            }
            if (!next.isLeaf())
            {
//System.out.println(next.getName()+" is not a leaf");
                blockElementStack.push(next);
                startTag(next);
            } else {
//System.out.println(next.getName()+" is a leaf");
                emptyTag(next);
            }
            current = next;
        }  // end loop

        /* Emit all remaining end tags */

        /* A null parameter ensures that all embedded tags
           currently in the tags vector have their
           corresponding end tags written out.
        */
        closeOutUnwantedEmbeddedTags(null);

        while (!blockElementStack.empty())
        {
            current = (Element)blockElementStack.pop();
//System.out.println("clearing blockelemstack name: "+current.getName());
            if (!synthesizedElement(current))
            {
                endTag(current);
            }
        }

        segment.array = null;

        array= new StringBuffer[formattedVct.size()];
        formattedVct.copyInto(array);
        formattedVct.clear();
        formattedVct = null;
        // any text copied from <pre> may not end in new line, add one if missing
        // skip last one, new line not needed there
        for (int i=0; i<array.length-1; i++) {
            if (!array[i].toString().endsWith(NEWLINE)) {
                array[i].append(NEWLINE);
            }
        }

        return array;
    }

    /**
     * This method determines whether the current element
     * is in the range specified.  When no range is specified,
     * the range is initialized to be the entire document.
     * inRange() returns true if the range specified intersects
     * with the element's range.
     *
     * @param  next Element.
     * @return boolean that indicates whether the element
     *         is in the range.
     */
    protected boolean inRange(Element next)
    {
        boolean found=false;
//System.err.print("inRange entered for elem "+next);
        if (completeDoc)
        {
            // allow calculation to avoid terminating new line structure
            // will get structure hierarchy all the way back to body, not just selected nested structure
            int startOffset = getStartOffset();
            int endOffset = getEndOffset();
            //return
            found = ((next.getStartOffset() >= startOffset && next.getStartOffset() < endOffset) ||
                (startOffset >= next.getStartOffset() && startOffset < next.getEndOffset()));
        }else{
            // modified to copy just nested structure
            if (next.getStartOffset()>= rangeElement.getStartOffset()&&
                next.getEndOffset()<=rangeElement.getEndOffset() &&
                next.getStartOffset()<getEndOffset() &&
                next.getEndOffset()>getStartOffset()) // prevent children of range that weren't selected
            {
    //System.err.println("inrange startOffset: "+getStartOffset()+" elem start: "+next.getStartOffset()
    //+" endOffset: "+getEndOffset()+" elem end: "+next.getEndOffset()+" elem name "+next.getName());
                // can't add check for next.getStartOffset()>=getStartOffset() because
                // subsets list and tables do not output <table> or <ol>
    //          if (next.getStartOffset()<getStartOffset()&&  //element is before selected range
    //              next.g){
    //              return false;}

                //return true;
                found = true;
            }
        }
        return found;//false;

/*orig code, will get structure hierarchy all the way back to body, not just selected nested structure
        int startOffset = getStartOffset();
        int endOffset = getEndOffset();
        if ((next.getStartOffset() >= startOffset && next.getStartOffset() < endOffset) ||
            (startOffset >= next.getStartOffset() && startOffset < next.getEndOffset()))
        {
            return true;
        }
        return false;
        */
    }

    /**
     * Writes out the attribute set.  Ignores all
     * attributes with a key of type HTML.Tag,
     * attributes with a key of type StyleConstants,
     * and attributes with a key of type
     * HTML.Attribute.ENDTAG.
     *
     * @param attr   an AttributeSet
     * @exception IOException on any I/O error
     *
     */
    protected void writeAttributes(AttributeSet attr) throws IOException
    {
        Enumeration<?> names = null;
        // translate css attributes to html
        convAttr.removeAttributes(convAttr);
        convertToHTML32(attr, convAttr);

        names = convAttr.getAttributeNames();
        while (names.hasMoreElements()) {
            Object name = names.nextElement();
            if (name instanceof HTML.Tag ||
                name instanceof StyleConstants ||
                name == HTML.Attribute.ENDTAG) {
                continue;
            }
            // to remove a publish attribute setting, the publish attribute is set to none, ignore it
            if (name.equals("publish") &&
                convAttr.getAttribute(name).equals("none")) {
                continue;
            }
           
            write(" " + name + "=\"" + convAttr.getAttribute(name) + "\"");
        }
    }

    /**
     * Writes out all empty elements (all tags that have no
     * corresponding end tag).
     *
     * @param elem   an Element
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void emptyTag(Element elem) throws BadLocationException, IOException
    {
        AttributeSet attr = elem.getAttributes();
        closeOutUnwantedEmbeddedTags(attr);
        writeEmbeddedTags(attr);
        if (matchNameAttribute(attr, HTML.Tag.CONTENT)) {
            inContent = true;
            text(elem);
        }
        else if (matchNameAttribute(attr, HTML.Tag.COMMENT)) {
        }
        else
        {
            //boolean isBlock = isBlockTag(elem.getAttributes());
            Object nameTag = (attr != null) ? attr.getAttribute(StyleConstants.NameAttribute) : null;
            Object endTag = (attr != null) ? attr.getAttribute(HTML.Attribute.ENDTAG) : null;

            boolean outputEndTag = false;
            // If an instance of an UNKNOWN Tag, or an instance of a
            // tag that is only visible during editing
            //
            if (nameTag != null && endTag != null &&
                (endTag instanceof String) &&
                ((String)endTag).equals("true"))
            {
                outputEndTag = true;
            }

//System.out.println("Writing empty "+elem.getName());
            write('<');
            if (formattedVct!=null && formattedVct.size()>0) // v111
            {
                // use last element
                StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                sb.append("<");
//System.err.println("emptyTag sb "+sb);
            }

            if (outputEndTag)
            {
                write('/');
                if (formattedVct!=null && formattedVct.size()>0) // v111
                {
                    // use last element
                    StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                    sb.append("/");
//System.err.println("emptyTag2 sb "+sb);
                }
            }

            write(elem.getName());
            if (formattedVct!=null && formattedVct.size()>0) // v111
            {
                // closing out tags so use last element
                StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                sb.append(elem.getName());
//System.err.println("emptyTag3 sb "+sb);
            }

            writeAttributes(attr);
            if (!outputEndTag) { // empty content use XML syntax
                write(" /");
            }

            write('>');
            if (formattedVct!=null && formattedVct.size()>0) // v111
            {
                // use last element
                StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                sb.append(">");
//System.err.println("emptyTag4 sb "+sb);
            }

            if (!inPre) { // <pre> honors all white space, do not add any extra, must correct in spellchkwrapper
                write('\n');  // allow spell checker to account for extra new line when <br> is rendered
            }
        }
    }

    /**
     * Determines if the HTML.Tag associated with the
     * element is a block tag.
     *
     * @param attr  an AttributeSet
     * @return  true if tag is block tag, false otherwise.
     */
    protected boolean isBlockTag(AttributeSet attr)
    {
        boolean found = false;
        Object o = attr.getAttribute(StyleConstants.NameAttribute);
        if (o instanceof HTML.Tag) {
            HTML.Tag name = (HTML.Tag) o;
            //return
            found = name.isBlock();
        }
        return found;//false;
    }

    /**
     * Writes out a start tag for the element.
     * Ignores all synthesized elements.
     *
     * @param elem   an Element
     * @throws IOException on any I/O error
     * @throws BadLocationException
     */
    protected void startTag(Element elem) throws IOException, BadLocationException
    {
        AttributeSet attr = null;
        Object nameAttribute = null;
        HTML.Tag name = null;

        if (synthesizedElement(elem))
        {
            // only create one entry for <pre>
            if (formattedVct!=null && !inPre) // v111
            {
                // add new element
                formattedVct.addElement(new StringBuffer());
//System.err.println("P-IMPLIED startTag new sb ");
            }

            //return;
        }else{
            // Determine the name, as an HTML.Tag.
            attr = elem.getAttributes();
            nameAttribute = attr.getAttribute(StyleConstants.NameAttribute);
            if (nameAttribute instanceof HTML.Tag)
            {
                name = (HTML.Tag)nameAttribute;
            }

            // do not output html, head or body tags.. they are always created in the document
            if (name == HTML.Tag.HTML || name==HTML.Tag.HEAD|| name==HTML.Tag.BODY) {
                //return;
            }else{
                if (name == HTML.Tag.PRE)
                {
                    inPre = true;
                    preEndOffset = elem.getEndOffset();
                }

                // any tag that has content elements under it needs a separate element in the vector
                // td, th, li have p-implied between them and the content, so they are covered
                // under synthesizedElement()==true
                if ((name == HTML.Tag.P ||
                        name == HTML.Tag.PRE || // only create one entry for <pre>
                        name == HTML.Tag.H1 ||
                        name == HTML.Tag.H2 ||
                        name == HTML.Tag.H3 ||
                        name == HTML.Tag.H4 ||
                        name == HTML.Tag.H5 ||
                        name == HTML.Tag.H6) && formattedVct!=null) // v111
                {
                    // add new element
                    formattedVct.addElement(new StringBuffer());
        //System.err.println(name.toString()+" startTag new sb ");
                }

                // write out end tags for item on stack
                closeOutUnwantedEmbeddedTags(attr);

                if (inContent)
                {
                    inContent = false;
                }
        //System.out.println("Writing start tag "+elem.getName());

                write('<');
                write(elem.getName());
                // accessibility requires that if this table does not have th, it is a layout table
                // and must have a summary attribute='layout'
                if (name == HTML.Tag.TABLE){
                    String summary = (String)attr.getAttribute(TABLE_SUMMARY);
                    Element tr = elem.getElement(0);
                    Element thtd = tr.getElement(0);

                    if (thtd.getName().equals("th")){  // is th, so cannot be layout table
                        // if summary wasn't set at all or it was a generated string, create a new one
                        if (generateSummary(summary)){
                            // generate a summary, if this isn't acceptable.. user must be prompted for it, someplace else!
                            // this is the index of the table element in the body element
                            int tableId = (1+tr.getParentElement().getParentElement().getElementIndex(tr.getStartOffset()));
                            summary="table "+tableId;
                        }

                        write(" "+TABLE_SUMMARY+"=\""+summary+"\"");
                    }else{  // is td so summary='layout'
                        write(" "+TABLE_SUMMARY+"=\""+TABLE_LAYOUT+"\"");
                    }

                    // don't output summary more than once, so remove it from a copy of the attr
                    // leave master alone so other attributes are maintained
                    attr = copyAndRemoveAttribute(attr, TABLE_SUMMARY);
                }
                if (name == HTML.Tag.TH){
                    Element tr = elem.getParentElement();
                    // generate an id using index of this th cell
                    int thIndex = tr.getElementIndex(elem.getStartOffset());
                    // add id attribute
                    write(" "+TH_ID+"=\"c"+thIndex+"\"");
                    // don't output more than once, so remove it from a copy of the attr
                    // leave master alone so other attributes are maintained
                    attr = copyAndRemoveAttribute(attr, HTML.Attribute.ID);
                    // make sure a headers isn't left around from a td->th conversion
                    attr = copyAndRemoveAttribute(attr, TD_HEADERS);
                }
                if (name == HTML.Tag.TD){
                    // add headers attribute
                    Element tr = elem.getParentElement();
                    Element table = tr.getParentElement();
                    Element firstTrCell = table.getElement(0).getElement(0);
                    if (firstTrCell.getName().equals("th")){  // cannot be a layout table
                        // generate a headers
                        int tdIndex = tr.getElementIndex(elem.getStartOffset());
                        write(" "+TD_HEADERS+"=\"c"+tdIndex+"\"");
                    }
                    // don't output more than once, so remove it from a copy of the attr
                    // leave master alone so other attributes are maintained
                    attr = copyAndRemoveAttribute(attr, TD_HEADERS);
                    // make sure a id isn't left around from a th->td conversion
                    attr = copyAndRemoveAttribute(attr, HTML.Attribute.ID);
                }

                writeAttributes(attr);
                write('>');

                // must maintain table integrity v111
                if (!completeDoc &&
                    name==HTML.Tag.TR && // not outputting a complete row
                    getStartOffset()>elem.getStartOffset()&& getStartOffset()<elem.getEndOffset())
                {
        //System.err.println("prepend forcing valid table "+elem);
                    int tdId = elem.getElementIndex(getStartOffset());
                    if (tdId!=0)
                    {
                        Element td = elem.getElement(tdId);
                        // must prepend output with missing cells
                        // if this is a th cell then add the id attribute
                        boolean isTh = td.getName().equals("th");
                        StringBuffer sb = new StringBuffer();
                        for (int i=0; i<tdId; i++) {
                            sb.append("<"+td.getName()+(isTh?" id=\"c"+i+"\"":"")+"></"+td.getName()+">");
                        }
                        write(sb.toString());
        //System.err.println("prepending "+sb);
                    }
                }
            }
        }
    }
    /**
     * check to see if this summary needs to be (re)generated
     *
     * @param summary String may be null
     * @return boolean
     */
    private boolean generateSummary(String summary) {
        boolean generate = false;
        if (summary==null){
            generate = true;
        }
        else if (summary.startsWith("table ")){
            generate = true;
            // check that rest of text is digits
            for (int c=6; c<summary.length(); c++){
                if(!Character.isDigit(summary.charAt(c))){
                    generate = false;
                    break;
                }
            }
        }

        return generate;
    }
    /**
     * Removes an attribute from an AttributeSet by making a copy
     * is invoked, then only the appropriate range of text is written
     * out.
     *
     * @param attr1 AttributeSet to remove key from
     * @param key  Object to remove
     */
    private AttributeSet copyAndRemoveAttribute(AttributeSet attr1, Object key) {
        AttributeSet attrCopy  = attr1;
        if(attr1.getAttribute(key)!=null){
            // remove it from a copy of the attr
            // leave master alone so all attributes are maintained
            attrCopy = new SimpleAttributeSet();
            ((MutableAttributeSet)attrCopy).addAttributes(attr1);
            ((MutableAttributeSet)attrCopy).removeAttribute(key);
        }
        return attrCopy;
    }

    /**
     * Writes out text.  If a range is specified when the constructor
     * is invoked, then only the appropriate range of text is written
     * out.
     *
     * @param elem   an Element
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void text(Element elem) throws BadLocationException, IOException
    {
        int start = 0;
        int end =0;
        // don't write out any head content
        Element top = (Element)blockElementStack.peek();
        if (matchNameAttribute(top.getAttributes(), HTML.Tag.HEAD)) {
            //return;
        }else{
            start = Math.max(getStartOffset(), elem.getStartOffset());
            end = Math.min(getEndOffset(), elem.getEndOffset());
            if (start < end) {
                if (segment == null) {
                    segment = new Segment();
                }
                getDocument().getText(start, end - start, segment);
                if (segment.count > 0)
                {
                    boolean done = false;
                    if (inPre && end == preEndOffset)
                    {
                        if (segment.count > 1) {
                            segment.count--;
                        }
                        else {
                            //return;
                            done = true;
                        }
                    }
                    if (!done){
                        replaceEntities = true;
                        setCanWrapLines(!inPre);
        //System.out.println("Writing text ");
        //for (int i=segment.offset; i<(segment.offset+segment.count); i++)
        //System.out.print(segment.array[i]);
        //System.out.println();
                        write(segment.array, segment.offset, segment.count);
                        if (formattedVct!=null && formattedVct.size()>0) // v111
                        {
                            // new tag so use last element
                            StringBuffer sb = (StringBuffer)formattedVct.lastElement();
        //System.err.println("write text append to sb \""+sb+"\" new txt: "+(new String(segment.array, segment.offset, segment.count)));
                            outputFormatted(segment.array, segment.offset, segment.count, sb);
                        }

                        setCanWrapLines(false);
                        replaceEntities = false;
                    }
                }
            }
        }
    }

    /**
     * Writes out an end tag for the element.
     *
     * @param elem    an Element
     * @exception IOException on any I/O error
     */
    protected void endTag(Element elem) throws IOException
    {
        if (!synthesizedElement(elem)) {
            // do not output html, head or body tags.. they are always created in the document
            if (matchNameAttribute(elem.getAttributes(), HTML.Tag.HTML) ||
                matchNameAttribute(elem.getAttributes(), HTML.Tag.HEAD) ||
                matchNameAttribute(elem.getAttributes(), HTML.Tag.BODY)){
                //return;
            }else{
                if (matchNameAttribute(elem.getAttributes(), HTML.Tag.PRE)) {
                    inPre = false;
                }

                // write out end tags for item on stack
                closeOutUnwantedEmbeddedTags(elem.getAttributes());
                if (inContent) {
                    inContent = false;
                }

        //System.out.println("Writing end tag "+elem.getName());

                // must maintain table integrity v111
                if (!completeDoc &&
                    matchNameAttribute(elem.getAttributes(), HTML.Tag.TR)&& // not outputting a complete row
                    getEndOffset()>elem.getStartOffset()&& getEndOffset()<elem.getEndOffset())
                {
                    //int tdId = elem.getElementIndex(getEndOffset());
        //System.err.println("new append tdid "+tdId+" elem.getElementCount(): "+elem.getElementCount());
                    for (int r=0; r<elem.getElementCount();r++)
                    {
                        StringBuffer sb = null;
                        Element td = elem.getElement(r);
                        if (inRange(td) || getEndOffset()>td.getEndOffset()) { // don't output missing leading cells
                            continue;
                        }

                        // must append output with missing cells
                        sb = new StringBuffer();
                        for (int i=r; i<elem.getElementCount(); i++) {
                            sb.append("<"+td.getName()+"></"+td.getName()+">");
                        }

                        write(sb.toString());
        //System.err.println("appending "+sb);
                        break;
                    }
                }

                write('<');
                write('/');
                write(elem.getName());
                write('>');
            }
        }
    }

    /**
     * Returns true if the element is a
     * synthesized element.  Currently we are only testing
     * for the p-implied tag.
     * @param elem Element
     * @return boolean
     */
    protected boolean synthesizedElement(Element elem) {
        boolean fake=false;
        if (matchNameAttribute(elem.getAttributes(), HTML.Tag.IMPLIED)) {
            fake = true; // return true;
        }
        return fake;//false;
    }


    /**
     * Returns true if the StyleConstants.NameAttribute is
     * equal to the tag that is passed in as a parameter.
     * @param attr AttributeSet
     * @param tag HTML.Tag
     * @return boolean
     */
    protected boolean matchNameAttribute(AttributeSet attr, HTML.Tag tag)
    {
        boolean found = false;
        Object o = attr.getAttribute(StyleConstants.NameAttribute);
        if (o instanceof HTML.Tag) {
            HTML.Tag name = (HTML.Tag) o;
            if (name == tag) {
                //return true;
                found = true;
            }
        }
        return found;//false;
    }

    /**
     * Searches for embedded tags in the AttributeSet
     * and writes them out.  It also stores these tags in a vector
     * so that when appropriate the corresponding end tags can be
     * written out. tags like <b>,<i>,<u>
     * @param attr AttributeSet
     * @exception IOException on any I/O error
     */
    protected void writeEmbeddedTags(AttributeSet attr) throws IOException
    {
        Enumeration<?> names = null;
        // translate css attributes to html
        attr = convertToHTML(attr, oConvAttr);

        names = attr.getAttributeNames();
        while (names.hasMoreElements())
        {
            Object name = names.nextElement();
            if (name instanceof HTML.Tag)
            {
                Object o = null;
                HTML.Tag tag = (HTML.Tag)name;
                if (tag == HTML.Tag.FORM || tags.contains(tag)) {
                    continue;
                }
//System.out.println("Writing embedded tags "+tag);

                write('<');
                write(tag.toString());
                if (formattedVct!=null && formattedVct.size()>0) // v111
                {
                    // new tag so use last element
                    StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                    sb.append("<"+tag.toString());
//System.err.println("open embedtag sb "+sb);
                }

                o = attr.getAttribute(tag);
                if (o != null && o instanceof AttributeSet) {
                    writeAttributes((AttributeSet)o);
                }
                write('>');
                if (formattedVct!=null && formattedVct.size()>0) // v111
                {
                    // new tag so use last element
                    StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                    sb.append(">");
//System.err.println("open embedtag2 sb "+sb);
                }

                tags.addElement(tag);
                tagValues.addElement(o);
            }
        }
    }

    /**
     * Searches the attribute set for a tag, both of which
     * are passed in as a parameter.  Returns true if no match is found
     * and false otherwise.
     */
    private boolean noMatchForTagInAttributes(AttributeSet attr, HTML.Tag t,
        Object tagValue)
    {
        boolean found = true;
        if (attr != null && attr.isDefined(t))
        {
            Object newValue = attr.getAttribute(t);

            if ((tagValue == null) ? (newValue == null):(newValue != null && tagValue.equals(newValue))) {
                found = false;//return false;
            }
        }
        return found;//true;
    }

    /**
     * Searches the attribute set and for each tag
     * that is stored in the tag vector.  If the tag isnt found,
     * then the tag is removed from the vector and a corresponding
     * end tag is written out.
     * @param attr AttributeSet
     * @exception IOException on any I/O error
     */
    protected void closeOutUnwantedEmbeddedTags(AttributeSet attr) throws IOException
    {
        HTML.Tag t;
        Object tValue;
        int firstIndex = -1;
        int size = 0;

        tagsToRemove.removeAllElements();

        // translate css attributes to html
        attr = convertToHTML(attr, null);

        size = tags.size();
        // First, find all the tags that need to be removed.
        for (int i = size - 1; i >= 0; i--)
        {
            t = (HTML.Tag)tags.elementAt(i);
            tValue = tagValues.elementAt(i);
            if ((attr == null) || noMatchForTagInAttributes(attr, t, tValue))
            {
                firstIndex = i;
                tagsToRemove.addElement(t);
            }
        }
        if (firstIndex != -1)
        {
            // Then close them out.
            boolean removeAll = ((size - firstIndex) == tagsToRemove.size());
            for (int i = size - 1; i >= firstIndex; i--)
            {
                t = (HTML.Tag)tags.elementAt(i);
                if (removeAll || tagsToRemove.contains(t))
                {
                    tags.removeElementAt(i);
                    tagValues.removeElementAt(i);
                }
//System.out.println("Closing unwanted embed tag?? "+t);
                write('<');
                write('/');
                write(t.toString());
                write('>');
                if (formattedVct!=null && formattedVct.size()>0) // v111
                {
                    // closing out tags so use last element
                    StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                    sb.append("</"+t.toString()+">");
//System.err.println("close embedtag sb "+sb);
                }
            }
            // Have to output any tags after firstIndex that still remain,
            // as we closed them out, but they should remain open.
            size = tags.size();
            for (int i = firstIndex; i < size; i++)
            {
                Object o = null;
                t = (HTML.Tag)tags.elementAt(i);
//System.out.println("Writing unwanted embed tags start tag "+t);

                write('<');
                write(t.toString());
                if (formattedVct!=null && formattedVct.size()>0) // v111
                {
                    // closing out tags so use last element
                    StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                    sb.append("<"+t.toString());
//System.err.println("close embedtag2 sb "+sb);
                }

                o = tagValues.elementAt(i);
                if (o != null && o instanceof AttributeSet) {
                    writeAttributes((AttributeSet)o);
                }
                write('>');
                if (formattedVct!=null && formattedVct.size()>0) // v111
                {
                    // closing out tags so use last element
                    StringBuffer sb = (StringBuffer)formattedVct.lastElement();
                    sb.append(">");
//System.err.println("close embedtag3 sb "+sb);
                }
            }
        }
    }

    /**
     * Determines whether a the indentation needs to be
     * incremented.  Basically, if next is a child of current, and
     * next is NOT a synthesized element, the indent level will be
     * incremented.  If there is a parent-child relationship and "next"
     * is a synthesized element, then its children must be indented.
     * This state is maintained by the indentNext boolean.
     *
     * @return boolean that's true if indent level
     *         needs incrementing.
     */
    private boolean indentNext = false;
    private boolean indentNeedsIncrementing(Element current, Element next)
    {
        boolean inc = false;
        if ((next.getParentElement() == current) && !inPre)
        {
            if (indentNext)
            {
                indentNext = false;
                inc = true;//return true;
            } else if (synthesizedElement(next))
            {
                indentNext = true;
            } else if (!synthesizedElement(current))
            {
                inc = true;//return true;
            }
        }
        return inc;//false;
    }

    // --- conversion support ---------------------------

    /**
     * Convert the given set of attributes to be html for
     * the purpose of writing them out.  Any keys that
     * have been converted will not appear in the resultant
     * set.  Any keys not converted will appear in the
     * resultant set the same as the received set.<p>
     * This will put the converted values into <code>to</code>, unless
     * it is null in which case a temporary AttributeSet will be returned.
     */
    AttributeSet convertToHTML(AttributeSet from, MutableAttributeSet to)
    {
        if (to == null) {
            to = convAttr;
        }
        to.removeAttributes(to);
        convertToHTML32(from, to);
        return to;
    }

    /**
     * Buffer for the purpose of attribute conversion
     */
    private MutableAttributeSet convAttr = new SimpleAttributeSet();

    /**
     * Buffer for the purpose of attribute conversion. This can be
     * used if convAttr is being used.
     */
    private MutableAttributeSet oConvAttr = new SimpleAttributeSet();

    /**
     * Create an older style of HTML attributes.  This will
     * convert character level attributes that have a StyleConstants
     * mapping over to an HTML tag/attribute.  Other CSS attributes
     * will be placed in an HTML style attribute.
     */
    private void convertToHTML32(AttributeSet from, MutableAttributeSet to)
    {
        Element elem = null;
        String value = "";
        Enumeration<?> keys = null;

        if (from == null || blockElementStack.isEmpty()) {
            //return;
        }else{
            elem = (Element)blockElementStack.peek();  // get parent element
            // must prevent bold from getting written out as part of a heading tag
            // this could happen if content is joined into a heading and the content had bold
            // tags.  It is very difficult to prevent the merge when bold content is one of the
            // xferred children so correct it here.
            keys = from.getAttributeNames();
            while (keys.hasMoreElements())
            {
                Object key = keys.nextElement();
                if (key instanceof CSS.Attribute)
                {
                    if (key == CSS.Attribute.FONT_WEIGHT)
                    {
                        String s = null;
                        if (elem!=null &&
                            (elem.getName().equals("h1")||elem.getName().equals("h2")||elem.getName().equals("h3")||
                                elem.getName().equals("h4")||elem.getName().equals("h5")||elem.getName().equals("h6"))) {
                            continue;  // don't write bold for heading tags or table heading
                        }

                        if (elem.getName().equals(HTML.Tag.IMPLIED.toString())&&
                            elem.getParentElement().getName().equals("th")) {
                            continue;
                        }

                        // add a bold tag if weight is bold
            // can't do it this way.. FontWeight is pkg level access only, don't need it if we don't support
            // font-weight numbers
            //          CSS.FontWeight weightValue = (CSS.FontWeight)
            //          from.getAttribute(CSS.Attribute.FONT_WEIGHT);
            //          if ((weightValue != null) && (weightValue.getValue() > 400)) {
            //          to.addAttribute(HTML.Tag.B, SimpleAttributeSet.EMPTY);
            //      }
                        s = from.getAttribute(key).toString();  //WSS
                        if (s.indexOf("bold") >= 0)  //WSS
                        {
                            Object obj = from.getAttribute(HTML.Tag.STRONG);
                            if (obj == null) {
                                to.addAttribute(HTML.Tag.B, SimpleAttributeSet.EMPTY); //WSS
                            }
                            else {
                                to.addAttribute(HTML.Tag.STRONG, SimpleAttributeSet.EMPTY); //WSS
                            }
                        }
                    } else if (key == CSS.Attribute.FONT_STYLE) {
                        String s = from.getAttribute(key).toString();
                        if (s.indexOf("italic") >= 0) {
                            Object obj = from.getAttribute(HTML.Tag.EM);
                            if (obj == null) {
                                to.addAttribute(HTML.Tag.I, SimpleAttributeSet.EMPTY);
                            }
                            else {
                                to.addAttribute(HTML.Tag.EM, SimpleAttributeSet.EMPTY);
                            }
                        }
                    } else if (key == CSS.Attribute.TEXT_DECORATION) {
                        String decor = from.getAttribute(key).toString();
                        if (decor.indexOf("underline") >= 0) {
                            to.addAttribute(HTML.Tag.U, SimpleAttributeSet.EMPTY);
                        }
                        if (decor.indexOf("line-through") >= 0) {
                            to.addAttribute(HTML.Tag.STRIKE, SimpleAttributeSet.EMPTY);
                        }
                    } else if (key == CSS.Attribute.VERTICAL_ALIGN) {
                        String vAlign = from.getAttribute(key).toString();
                        if (vAlign.indexOf("sup") >= 0) {
                            to.addAttribute(HTML.Tag.SUP, SimpleAttributeSet.EMPTY);
                        }
                        if (vAlign.indexOf("sub") >= 0) {
                            to.addAttribute(HTML.Tag.SUB, SimpleAttributeSet.EMPTY);
                        }
                    } else if (key == CSS.Attribute.TEXT_ALIGN) {
                        to.addAttribute(HTML.Attribute.ALIGN,from.getAttribute(key).toString());
                    } else if (key == CSS.Attribute.MARGIN_TOP) {
                    	// p tag now has style="margin-top:0" - drop it
                    }else {
                        // default is to store in a HTML style attribute
                        if (value.length() > 0) {
                            value = value + "; ";
                        }
                        value = value + key + ": " + from.getAttribute(key);
                    }
                }
                else
                {
                    if ((key.toString().equals("b") ||key.toString().equals("strong"))&& elem!=null &&
                        (elem.getName().equals("h1")||elem.getName().equals("h2")||elem.getName().equals("h3")||
                            elem.getName().equals("h4")||elem.getName().equals("h5")||elem.getName().equals("h6")||
                            elem.getName().equals("th"))) {
                        continue;  // don't write bold for heading tags or table heading
                    }

                    if ((key.toString().equals("b") ||key.toString().equals("strong"))&& elem!=null &&
                        (elem.getName().equals(HTML.Tag.IMPLIED.toString())&&
                            elem.getParentElement().getName().equals("th"))) {
                        continue;
                    }
                    
                    // if text styles are turned off, do not write out the tag
                    if (key == HTML.Tag.B || key == HTML.Tag.STRONG)
                    {
                        // get the font-weight, if normal do not add 'b' or 'strong' to output attrset
                        Object obj = from.getAttribute(CSS.Attribute.FONT_WEIGHT);
                        if (obj !=null)
                        {
                            if (obj.toString().indexOf("normal") !=-1) {
                                continue;
                            }
                        }
                    }
                    else if (key == HTML.Tag.I || key == HTML.Tag.EM)
                    {
                        Object obj = from.getAttribute(CSS.Attribute.FONT_STYLE);
                        {
                            // if italic is not specified, do not output i or em
                            if (obj.toString().indexOf("italic") ==-1) {
                                continue;
                            }
                        }
                    }
                    else if (key == HTML.Tag.U)
                    {
                        Object obj = from.getAttribute(CSS.Attribute.TEXT_DECORATION);
                        {
                            // if underline is not specified, do not output u
                            if (obj.toString().indexOf("underline") ==-1) {
                                continue;
                            }
                        }
                    }

                    to.addAttribute(key, from.getAttribute(key));
                }
            }
        
            if (value.length() > 0) {
                to.addAttribute(HTML.Attribute.STYLE, value);
            }
        }
    }

    /**
     * Writes the line separator. This is overriden to make sure we don't
     * replace the newline content in case it is outside normal ascii.
     * @throws IOException
     */
    protected void writeLineSeparator() throws IOException
    {
        // do not allow any pretty printing.. the only new lines in the output will
        // be those in the content itself
        return;
    }

    /**
     * Map any character entities, such as &lt; to &amp;lt;.
     * put output into stringbuffer NOT the output writer
     */
    private void outputFormatted(char[] chars, int start, int length, StringBuffer sb)
    {
        int blankId=Integer.MIN_VALUE;
        int last = start;

        if (!replaceEntities) {
            sb.append(chars, start, length);
            //return;
        }else{
            // must support wysiwyg blanks.. so if blanks are consecutive, write them as &nbsp;

            length += start;
            for (int counter = start; counter < length; counter++)
            {
                // check for consecutive blanks
                if (chars[counter] == ' ')
                {
                    if (blankId==counter-1)
                    // || counter==start)  // first text was a blank, it will be dropped when rendered
                    //but also counter=start after a <b> tag.. so don't do this
                    {
                        if (counter > last) {
                            // output any chars that didn't need conversion
                            sb.append(chars, last, counter - last);
                        }
                        last = counter + 1;
                        sb.append("&nbsp;");
                        blankId=counter;
                        continue;
                    }
                    blankId=counter;
                }
                else {
                    blankId = Integer.MIN_VALUE;
                }

                switch(chars[counter])
                {
                // Character level entities.
                case '<':
                    if (counter > last) {
                        // output any chars that didn't need conversion
                        sb.append(chars, last, counter - last);
                    }
                    last = counter + 1;
                    sb.append("&lt;");
                    break;
                case '>':
                    if (counter > last) {
                        // output any chars that didn't need conversion
                        sb.append(chars, last, counter - last);
                    }
                    last = counter + 1;
                    sb.append("&gt;");
                    break;
                case '&':
                    if (counter > last) {
                        // output any chars that didn't need conversion
                        sb.append(chars, last, counter - last);
                    }
                    last = counter + 1;
                    sb.append("&amp;");
                    break;
                case '"':
                    if (counter > last) {
                        // output any chars that didn't need conversion
                        sb.append(chars, last, counter - last);
                    }
                    last = counter + 1;
                    sb.append("&quot;");
                    break;
                // Special characters
                case '\n':
                case '\t':
                case '\r':
                    break;
                default:
    // Japanese is written out as numeric character references!!! instead of unicode!
    //Java&#12475;&#12461;&#12517;&#12522;&#12486;
                    if (chars[counter] < ' ' ||
                        (chars[counter] > ISO_8859_MIN_CHAR
                            && chars[counter] < ISO_8859_MAX_CHAR) ||  // Character entity references for ISO 8859-1 characters
                        hasCharacterMnemonic(chars[counter]))
                    {
                        if (counter > last) {
                            // output any chars that didn't need conversion
                            sb.append(chars, last, counter - last);
                        }
                        last = counter + 1;
                        if (chars[counter]==NBSP_CHAR)
                        {
                            sb.append("&nbsp;");
                            blankId=counter;  // use this as a blank
                        }
                        else
                        {
                            // If the character is outside of ascii, write the numeric value.
                            sb.append("&#");
                            sb.append(String.valueOf((int)chars[counter]));
                            sb.append(";");
                        }
                    }
                    break;
                }
            }
            if (last < length) {
                // output any chars that didn't need conversion
                sb.append(chars, last, length - last);
            }
        }
    }

    /**
     * This method is overriden to map any character entities, such as
     * &lt; to &amp;lt;. <code>super.output</code> will be invoked to
     * write the content.
     * @param chars char[]
     * @param start int
     * @param length int
     * @throws IOException
     */
    protected void output(char[] chars, int start, int length) throws IOException
    {
        int blankId=Integer.MIN_VALUE;
        int last = start;

        if(formattedVct!=null) { // writer is null, no output needed v111
            //return;
        }else{
            if (!replaceEntities) {
                super.output(chars, start, length);
                //return;
            }else{
                // must support wysiwyg blanks.. so if blanks are consecutive, write them as &nbsp;
                length += start;
                for (int counter = start; counter < length; counter++)
                {
                    // check for consecutive blanks
                    if (chars[counter] == ' ')
                    {
                        if (blankId==counter-1)
                        // || counter==start)  // first text was a blank, it will be dropped when rendered
                        //but also counter=start after a <b> tag.. so don't do this
                        {
                            if (counter > last) {
                                // output any chars that didn't need conversion
                                super.output(chars, last, counter - last);
                            }
                            last = counter + 1;
                            output("&nbsp;");
                            blankId=counter;
                            continue;
                        }
                        blankId=counter;
                    }
                    else {
                        blankId = Integer.MIN_VALUE;
                    }

                    switch(chars[counter])
                    {
                    // Character level entities.
                    case '<':
                        if (counter > last) {
                            // output any chars that didn't need conversion
                            super.output(chars, last, counter - last);
                        }
                        last = counter + 1;
                        output("&lt;");
                        break;
                    case '>':
                        if (counter > last) {
                            // output any chars that didn't need conversion
                            super.output(chars, last, counter - last);
                        }
                        last = counter + 1;
                        output("&gt;");
                        break;
                    case '&':
                        if (counter > last) {
                            // output any chars that didn't need conversion
                            super.output(chars, last, counter - last);
                        }
                        last = counter + 1;
                        output("&amp;");
                        break;
                    case '"':
                        if (counter > last) {
                            // output any chars that didn't need conversion
                            super.output(chars, last, counter - last);
                        }
                        last = counter + 1;
                        output("&quot;");
                        break;
                    // Special characters
                    case '\n':
                    case '\t':
                    case '\r':
                        break;
                    default:
        // Japanese is written out as numeric character references!!! instead of unicode!
        //Java&#12475;&#12461;&#12517;&#12522;&#12486;
                        if (chars[counter] < ' ' ||
                            (chars[counter] > ISO_8859_MIN_CHAR
                                && chars[counter] < ISO_8859_MAX_CHAR) ||  // Character entity references for ISO 8859-1 characters
                            hasCharacterMnemonic(chars[counter]))
                        {
                            if (counter > last) {
                                // output any chars that didn't need conversion
                                super.output(chars, last, counter - last);
                            }
                            last = counter + 1;
                            if (chars[counter]==NBSP_CHAR)
                            {
                                output("&nbsp;");
                                blankId=counter;  // use this as a blank
                            }
                            else
                            {
                                // If the character is outside of ascii, write the numeric value.
                                output("&#");
                                output(String.valueOf((int)chars[counter]));
                                output(";");
                            }
                        }
                        break;
                    }
                }
                if (last < length) {
                    // output any chars that didn't need conversion
                    super.output(chars, last, length - last);
                }
            }
        }
    }

    /**
    * If user can enter these characters, make sure they are converted to character entity references on output
    *@param c char to check
    */
    private boolean hasCharacterMnemonic(char c)
    {
/*
This is the proper way to handle character entity references.. if they are in the dtd, they
would be written out properly.. could use the name or the numeric value
*/
        boolean hasit = false;
        HTMLEditorKit.Parser xmlparser = ((HTMLDocument)getDocument()).getParser();
        if (xmlparser instanceof XMLParserDelegator)
        {
            javax.swing.text.html.parser.Entity charRef = ((XMLParserDelegator)xmlparser).getEntity(c);
            if (charRef !=null)
            {
                //could use the name or the numeric value
//              System.err.println("found char ref for ch: "+(int)c+" name: "+charRef.getName());
                hasit=true;//return true;
            }
        }
        return hasit;//false;
    }
    /**
     * This directly invokes super's <code>output</code> after converting
     * <code>string</code> to a char[].
     */
    private void output(String string) throws IOException
    {
        int length = string.length();

        if(formattedVct==null) { // formattedVct!=null.. writer is null, no output needed v111
            if (tempChars == null || tempChars.length < length) {
                tempChars = new char[length];
            }
            string.getChars(0, length, tempChars, 0);
            super.output(tempChars, 0, length);
        }
    }
    /**
    * Override base class. No pretty printing is allowed.  It must be one for one
    * with the document with only the added element attributes (tags and tag attributes).
    * This is required for spell check to work properly.
    * @param chars char[]
    * @param startIndex int
    * @param length int
    * @throws IOException
    */
    protected void write(char[] chars, int startIndex, int length) throws IOException
    {
        output(chars,startIndex,length);
    }
}
