// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.text.html.parser.*;
import java.util.Hashtable;

/**********************************************************************
* This is used to provide more meaningful error messages
*
* @author Wendy Stimpson
* @version 1.0
*
* Notes from Base class (DocumentParser.java):
* A Parser for HTML Documents (actually, you can specify a DTD, but
* you should really only use this class with the html dtd in swing).
* Reads an InputStream of HTML and
* invokes the appropriate methods in the ParserCallback class. This
* is the default parser used by HTMLEditorKit to parse HTML url's.
* <p>This will message the callback for all valid tags, as well as
* tags that are implied but not explicitly specified. For example, the
* html string (&lt;p&gt;blah) only has a p tag defined. The callback
* will see the following methods:
* <ol><li><i>handleStartTag(html, ...)</i></li>
*     <li><i>handleStartTag(head, ...)</i></li>
*     <li><i>handleEndTag(head)</i></li>
*     <li><i>handleStartTag(body, ...)</i></li>
*     <li>handleStartTag(p, ...)</i></li>
*     <li>handleText(...)</li>
*     <li><i>handleEndTag(p)</i></li>
*     <li><i>handleEndTag(body)</i></li>
*     <li><i>handleEndTag(html)</i></li>
* </ol>
* The items in <i>italic</i> are implied, that is, although they were not
* explicitly specified, to be correct html they should have been present
* (head isn't necessary, but it is still generated). For tags that
* are implied, the AttributeSet argument will have a value of
* <code>Boolean.TRUE</code> for the key
* <code>HTMLEditorKit.ParserCallback.IMPLIED</code>.
* <p>HTML.Attributes defines a type safe enumeration of html attributes.
* If an attribute key of a tag is defined in HTML.Attribute, the
* HTML.Attribute will be used as the key, otherwise a String will be used.
* For example &lt;p foo=bar class=neat&gt; has two attributes. foo is
* not defined in HTML.Attribute, where as class is, therefore the
* AttributeSet will have two values in it, HTML.Attribute.CLASS with
* a String value of 'neat' and the String key 'foo' with a String value of
* 'bar'.
* <p>The position argument will indicate the start of the tag, comment
* or text. Similiar to arrays, the first character in the stream has a
* position of 0. For tags that are
* implied the position will indicate
* the location of the next encountered tag. In the first example,
* the implied start body and html tags will have the same position as the
* p tag, and the implied end p, html and body tags will all have the same
* position.
* <p>As html skips whitespace the position for text will be the position
* of the first valid character, eg in the string '\n\n\nblah'
* the text 'blah' will have a position of 3, the newlines are skipped.
* <p>
* For attributes that do not have a value, eg in the html
* string <code>&lt;foo blah&gt;</code> the attribute <code>blah</code>
* does not have a value, there are two possible values that will be
* placed in the AttributeSet's value:
* <ul>
* <li>If the DTD does not contain an definition for the element, or the
*     definition does not have an explicit value then the value in the
*     AttributeSet will be <code>HTML.NULL_ATTRIBUTE_VALUE</code>.
* <li>If the DTD contains an explicit value, as in:
*     <code>&lt;!ATTLIST OPTION selected (selected) #IMPLIED&gt;</code>
*     this value from the dtd (in this case selected) will be used.
* </ul>
* <p>
* Once the stream has been parsed, the callback is notified of the most
* likely end of line string. The end of line string will be one of
* \n, \r or \r\n, which ever is encountered the most in parsing the
* stream.
*
* Change History:
*/
// $Log: XMLDocumentParser.java,v $
// Revision 1.1  2007/04/18 19:47:47  wendy
// Reorganized JUI module
//
// Revision 1.3  2006/01/25 18:59:03  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:57  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:17  tony
// This is the initial load of OPICM
//
//
class XMLDocumentParser extends DocumentParser
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    private static final Hashtable ERR_MSG_TBL;
    static {
        ERR_MSG_TBL = new Hashtable();
        ERR_MSG_TBL.put("start.missing","Missing start tag <%>");
        ERR_MSG_TBL.put("end.missing","Missing end tag </%>");
        ERR_MSG_TBL.put("end.unexpected","Required content is missing for tag </%>");
        ERR_MSG_TBL.put("tag.unrecognized","Unsupported start tag <%>");
        ERR_MSG_TBL.put("end.unrecognized","Unsupported end tag </%>");
        ERR_MSG_TBL.put("unmatched.endtag","Unmatched end tag </%>");
        ERR_MSG_TBL.put("tag.unexpected","Unexpected tag <%>");
        ERR_MSG_TBL.put("tag.ignore","Invalid or extra tag <%>");
        ERR_MSG_TBL.put("end.extra.tag","Invalid or extra end tag </%>");
        ERR_MSG_TBL.put("expected.endtagname","Invalid end tag"); //?
        ERR_MSG_TBL.put("invalid.shortend","Invalid end tag");//? find tag?
        ERR_MSG_TBL.put("expected.tagname","Invalid start tag");  //?
        ERR_MSG_TBL.put("eof","Invalid start tag");  //?
        ERR_MSG_TBL.put("invalid.tagchar","Invalid character '%' in tag <%>");
        ERR_MSG_TBL.put("expected","Expected %");
        ERR_MSG_TBL.put("invalid.tagatt","Unsupported attribute (%) on tag <%>");
        ERR_MSG_TBL.put("multi.tagatt","Multiple attributes (%) on tag <%>");
        ERR_MSG_TBL.put("invalid.tagattval","Invalid value for attribute (%) on tag <%>");
        ERR_MSG_TBL.put("attvalerr","Attribute value must be in quotes");
        ERR_MSG_TBL.put("invalid.entref","Invalid character entity reference '%'");
        ERR_MSG_TBL.put("req.att","Required attribute (%) not found for tag <%>");
        ERR_MSG_TBL.put("eof.comment","Comment was not closed");
        ERR_MSG_TBL.put("ident.expected","Invalid identifier");
        ERR_MSG_TBL.put("invalid.commentchar","Invalid comment character '%'");
        ERR_MSG_TBL.put("unexpected.pcdata","Text is not allowed");
    }

    XMLDocumentParser(DTD dtd)
    {
        super(dtd);
        // if strict = false (the default) and the DTD has content (LI)? the parser will loop
        // but if strict = true it will not loop but will not render things properly if end
        // tags are missing.. strict=true will not automatically insert the missing tags

        // also seems that for any content (XX)? which means 0 or 1 occurrance, the parser
        // will not accept any XX content!

        strict=true;  // if false and the dtd specifies that <br> must be in <p> but isn't
                      // the parser generates false tags to correct it.. don't allow this!
    }

/* possible errors not in table
Parser.java(189): error("ioexception");
Parser.java(989): error("eof.literal", stack.elem.getName());
Parser.java(1335): error("invalid.markup");
Parser.java(1728): error("javascript.unsupported");
Parser.java(1997): error("exception", e.getClass().getName(), e.getMessage());
Parser.java(2001): error("terminated");
*/
    /**
    * Error handling.
    * @param err String
    * @param arg1 String
    * @param arg2 String
    * @param arg3 String
    */
    protected void error(String err, String arg1, String arg2, String arg3)
    {
        // build a more meaningful error message
        String errorMsg = err +"|"+ arg1 +"|"+ arg2 +"|"+ arg3;

        String msg = (String)ERR_MSG_TBL.get(err.trim());
        if (msg ==null) {
            System.err.println("XMLDocumentParser:: NEW message not found for "+err);
        }
        else
        {
            StringBuffer sb = new StringBuffer(msg);
            fillInMsg(sb,arg1);
            fillInMsg(sb,arg2);
            fillInMsg(sb,arg3);
            errorMsg = sb.toString();
        }

        super.handleError(0, errorMsg);
        //callback.handleError(errorMsg, getCurrentPos());
    }

    private void fillInMsg(StringBuffer sb, String arg)
    {
        int subId =-1;
        if (!arg.equals("?")) {
            // find %
            subId = sb.toString().indexOf("%");
            if (subId!=-1) {
                // replace % with agr
                sb.replace(subId, subId+1, arg);
            }
        }
    }
}
