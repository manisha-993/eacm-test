// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import java.io.*;
import java.util.*;

/******************************************************************************
* This is used for loading a DTD to use for parsing XML.  One is instantiated
* for each DTD used.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
// $Log: XMLParserDelegator.java,v $
// Revision 1.2  2013/07/18 18:43:51  wendy
// fix compiler warnings
//
// Revision 1.1  2012/09/27 19:39:19  wendy
// Initial code
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
class XMLParserDelegator extends HTMLEditorKit.Parser
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.2 $";
    private DTD dtd = null;

    static final Vector<String> IGNORED_ELEM_VCT;
    private static final Hashtable<String, String> ENTITY_DEF_TBL;
    static {
        // some elements are created by the base DTD class, we will not support them as actions
        String ignoredElements[] ={
            "html","meta","base","isindex","head","plaintext",
            "body","applet","param","title","style","link",
            "unknown","#pcdata"};

        IGNORED_ELEM_VCT = new Vector<String>();
        for (int i = 0; i < ignoredElements.length; i++ ) {
            IGNORED_ELEM_VCT.addElement(ignoredElements[i]);
        }
        ignoredElements = null;

        ENTITY_DEF_TBL = new Hashtable<String, String>();
        ENTITY_DEF_TBL.put("nbsp","no-break space");
        ENTITY_DEF_TBL.put("iexcl","inverted exclamation mark");
        ENTITY_DEF_TBL.put("cent","cent sign");
        ENTITY_DEF_TBL.put("pound","pound sterling sign");
        ENTITY_DEF_TBL.put("curren","general currency sign");
        ENTITY_DEF_TBL.put("yen","yen sign");
        ENTITY_DEF_TBL.put("brvbar","broken (vertical) bar");
        ENTITY_DEF_TBL.put("sect","section sign");
        ENTITY_DEF_TBL.put("uml","umlaut (dieresis)");
        ENTITY_DEF_TBL.put("copy","copyright sign");
        ENTITY_DEF_TBL.put("ordf","ordinal indicator, feminine");
        ENTITY_DEF_TBL.put("laquo","angle quotation mark, left");
        ENTITY_DEF_TBL.put("not","not sign");
        ENTITY_DEF_TBL.put("shy","soft hyphen");
        ENTITY_DEF_TBL.put("reg","registered sign");
        ENTITY_DEF_TBL.put("macr","macron");
        ENTITY_DEF_TBL.put("deg","degree sign");
        ENTITY_DEF_TBL.put("plusmn","plus-or-minus sign");
        ENTITY_DEF_TBL.put("sup2","superscript two");
        ENTITY_DEF_TBL.put("sup3","superscript three");
        ENTITY_DEF_TBL.put("acute","acute accent");
        ENTITY_DEF_TBL.put("micro","micro sign");
        ENTITY_DEF_TBL.put("para","pilcrow (paragraph sign)");
        ENTITY_DEF_TBL.put("middot","middle dot");
        ENTITY_DEF_TBL.put("cedil","cedilla");
        ENTITY_DEF_TBL.put("sup1","superscript one");
        ENTITY_DEF_TBL.put("ordm","ordinal indicator, masculine");
        ENTITY_DEF_TBL.put("raquo","angle quotation mark, right");
        ENTITY_DEF_TBL.put("frac14","fraction one-quarter");
        ENTITY_DEF_TBL.put("frac12","fraction one-half");
        ENTITY_DEF_TBL.put("frac34","fraction three-quarters");
        ENTITY_DEF_TBL.put("iquest","inverted question mark");
        ENTITY_DEF_TBL.put("Agrave","capital A, grave accent");
        ENTITY_DEF_TBL.put("Aacute","capital A, acute accent");
        ENTITY_DEF_TBL.put("Acirc","capital A, circumflex accent");
        ENTITY_DEF_TBL.put("Atilde","capital A, tilde");
        ENTITY_DEF_TBL.put("Auml","capital A, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("Aring","capital A, ring");
        ENTITY_DEF_TBL.put("AElig","capital AE diphthong (ligature)");
        ENTITY_DEF_TBL.put("Ccedil","capital C, cedilla");
        ENTITY_DEF_TBL.put("Egrave","capital E, grave accent");
        ENTITY_DEF_TBL.put("Eacute","capital E, acute accent");
        ENTITY_DEF_TBL.put("Ecirc","capital E, circumflex accent");
        ENTITY_DEF_TBL.put("Euml","capital E, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("Igrave","capital I, grave accent");
        ENTITY_DEF_TBL.put("Iacute","capital I, acute accent");
        ENTITY_DEF_TBL.put("Icirc","capital I, circumflex accent");
        ENTITY_DEF_TBL.put("Iuml","capital I, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("ETH","capital Eth, Icelandic");
        ENTITY_DEF_TBL.put("Ntilde","capital N, tilde");
        ENTITY_DEF_TBL.put("Ograve","capital O, grave accent");
        ENTITY_DEF_TBL.put("Oacute","capital O, acute accent");
        ENTITY_DEF_TBL.put("Ocirc","capital O, circumflex accent");
        ENTITY_DEF_TBL.put("Otilde","capital O, tilde");
        ENTITY_DEF_TBL.put("Ouml","capital O, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("times","multiply sign");
        ENTITY_DEF_TBL.put("Oslash","capital O, slash");
        ENTITY_DEF_TBL.put("Ugrave","capital U, grave accent");
        ENTITY_DEF_TBL.put("Uacute","capital U, acute accent");
        ENTITY_DEF_TBL.put("Ucirc","capital U, circumflex accent");
        ENTITY_DEF_TBL.put("Uuml","capital U, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("Yacute","capital Y, acute accent");
        ENTITY_DEF_TBL.put("THORN","capital THORN, Icelandic");
        ENTITY_DEF_TBL.put("szlig","small sharp s, German (sz ligature)");
        ENTITY_DEF_TBL.put("agrave","small a, grave accent");
        ENTITY_DEF_TBL.put("aacute","small a, acute accent");
        ENTITY_DEF_TBL.put("acirc","small a, circumflex accent");
        ENTITY_DEF_TBL.put("atilde","small a, tilde");
        ENTITY_DEF_TBL.put("auml","small a, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("aring","small a, ring");
        ENTITY_DEF_TBL.put("aelig","small ae diphthong (ligature)");
        ENTITY_DEF_TBL.put("ccedil","small c, cedilla");
        ENTITY_DEF_TBL.put("egrave","small e, grave accent");
        ENTITY_DEF_TBL.put("eacute","small e, acute accent");
        ENTITY_DEF_TBL.put("ecirc","small e, circumflex accent");
        ENTITY_DEF_TBL.put("euml","small e, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("igrave","small i, grave accent");
        ENTITY_DEF_TBL.put("iacute","small i, acute accent");
        ENTITY_DEF_TBL.put("icirc","small i, circumflex accent");
        ENTITY_DEF_TBL.put("iuml","small i, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("eth","small eth, Icelandic");
        ENTITY_DEF_TBL.put("ntilde","small n, tilde");
        ENTITY_DEF_TBL.put("ograve","small o, grave accent");
        ENTITY_DEF_TBL.put("oacute","small o, acute accent");
        ENTITY_DEF_TBL.put("ocirc","small o, circumflex accent");
        ENTITY_DEF_TBL.put("otilde","small o, tilde");
        ENTITY_DEF_TBL.put("ouml","small o, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("divide","divide sign");
        ENTITY_DEF_TBL.put("oslash","small o, slash");
        ENTITY_DEF_TBL.put("ugrave","small u, grave accent");
        ENTITY_DEF_TBL.put("uacute","small u, acute accent");
        ENTITY_DEF_TBL.put("ucirc","small u, circumflex accent");
        ENTITY_DEF_TBL.put("uuml","small u, dieresis or umlaut mark");
        ENTITY_DEF_TBL.put("yacute","small y, acute accent");
        ENTITY_DEF_TBL.put("thorn","small thorn, Icelandic");
        ENTITY_DEF_TBL.put("yuml","small y, dieresis or umlaut mark");
    }

    XMLParserDelegator(String dtdName)
    {
        try {
            // if a dtd could not be found, the default will be used but the name used by the editor kit
            // is not reset at this point
            // can't just throw an exception because it must be an IOException to override base
            // class method and IOException can occur for different reasons.
            dtd = XMLDTD.getXMLDTD(dtdName);
        }
        catch(IOException ioe)
        {
            System.err.println("XMLParserDelegator:: Could not get dtd: " + dtdName);
            ioe.printStackTrace(System.err);
        }
    }

    // get the name of the dtd used for this parser
    String getDTDName() {
        return (dtd==null?null:dtd.getName());
    }

    // get any allowed content for the specified element
    Vector<Element> getContentElements(String elemName)
    {
        ContentModel content;
        // this could be a problem if called for unsupported elements.. they will be created!
        Element elem = getElement(elemName);

        Vector<Element> elemVct = new Vector<Element>(1);
        if (elem!=null) {
            content = elem.content;
            if (content!=null) {
                content.getElements(elemVct);
            }

            // remove any excluded elements from the vector
            if (elem.exclusions!=null)
            {
                for (int i=0; i<elem.exclusions.length(); i++)
                {
                    if (elem.exclusions.get(i))  // bit is set
                    {
                        Element excludedElem = dtd.getElement(i);
                        elemVct.remove(excludedElem);
                    }
                }
            }
        }
        return elemVct;
    }

    // get the entity that corresponds to that character, may return null
    javax.swing.text.html.parser.Entity getEntity(int c)
    {
        return dtd.getEntity(c);
    }

    // get the element that corresponds to the name
    javax.swing.text.html.parser.Element getElement(String name)
    {
        javax.swing.text.html.parser.Element elem=null;
        // the dtd will create elements when getElement() is called.. avoid this when
        // element is not part of the DTD
        for (Enumeration<?> e = dtd.elementHash.keys(); e.hasMoreElements();)
        {
            String key = (String)e.nextElement();
            if (name.equals(key)) {
                elem= (javax.swing.text.html.parser.Element) dtd.elementHash.get(key);
                break;
            }
        }

        return elem;
    }

    boolean elementExists(String name)
    {
        return (dtd.elementHash.get(name) != null);
    }

    boolean isAttributeSupported(String attrName)
    {
        boolean found=false;
        for (Enumeration<?> e = dtd.elementHash.keys(); e.hasMoreElements();)
        {
            Element elem = null;
            AttributeList attrList;
            String key = (String)e.nextElement();
            if (IGNORED_ELEM_VCT.contains(key)) {
                continue;
            }
            elem = (Element) dtd.elementHash.get(key);
            // get attributelist
            attrList = elem.getAttribute(attrName);
            if (attrList!=null) {
                found=true;//return true;
                break;
            }
        }
        return found;
    }

    // get list of supported elements for this dtd
    String[] getSupportedElements()
    {
        Vector<String> vct = new Vector<String>(1);
        String array[];
        // check if any elements have the publish attribute
        if(isAttributeSupported(XMLEditor.PUBLISH_TAG_ATTR)) {
            vct.addElement(XMLEditor.PUBLISH_TAG_ATTR);  // support publish as a tag attr
        }
        // if dtd specified any character entity elements, allow menu action to be displayed
// see getSupportedEntities() for info, if the following is true the
// user will be able to insert all default character entities
        if (((XMLDTD)dtd).getSupportSpecialChars()) {// flag set in parse, if dtd has: <!ENTITY sc CDATA "&#000;" -- turn on special char support -->
            vct.addElement("sc"); // specialchar
        }

        for (Enumeration<?> e = dtd.elementHash.keys(); e.hasMoreElements();)
        {
            String key = (String)e.nextElement();
            if (IGNORED_ELEM_VCT.contains(key)) {
                continue;
            }
//          Element elem = (Element) dtd.elementHash.get(key);
            vct.addElement(key);
        }
        array= new String[vct.size()];
        vct.copyInto(array);
        return array;
    }

    // get number of nested levels allowed
    int getNumberNestedLevels() {
        return ((XMLDTD)dtd).getNumberNestedLevels();
    }
    /***************************************
     * parse contents
     * @param r Reader
     * @param cb HTMLEditorKit.ParserCallback
     * @param ignoreCharSet boolean
     * @throws IOException
     */
    public void parse(Reader r, HTMLEditorKit.ParserCallback cb, boolean ignoreCharSet)
        throws IOException
    {
        new XMLDocumentParser(dtd).parse(r, cb, ignoreCharSet);
    }

    // this gets all character entities and allows user to insert them
    // it is not restricted at all, this could be handled by addind specific ones
    // to the dtd and only showing those.. but then the xmlwriter would have to
    // verify any &#xxx; values it generated to make sure they were in the dtd
    // user could paste one of these characters into the text.. not prevented now!
	javax.swing.DefaultListModel getSupportedEntities()
    {
        Vector<XMLSpecialChar> entVct = new Vector<XMLSpecialChar>(1);
        javax.swing.DefaultListModel entMdl;

        for (Enumeration<?> e = dtd.entityHash.keys(); e.hasMoreElements();)
        {
            Object key = e.nextElement();
            if (!(key instanceof Integer))
            {
                String def;
//System.out.println("dtd "+name+" entityhash key: "+key);
                Entity ent = (Entity)dtd.entityHash.get(key);
                if (ent.getName().startsWith("#")||  // avoid #SPACE, #RE, #RS
                    ent.getName().equals("amp")||
                    ent.getName().equals("lt")||
                    ent.getName().equals("gt")) {
                    continue;
                }
                def = (String)ENTITY_DEF_TBL.get(ent.getName());
                if (def==null) {
                    def=ent.getName();
                }
                entVct.addElement(new XMLSpecialChar(ent.getString(),ent.getName(),def));
//System.out.println("  ent.name: "+ent.getName()+" str: "+ent.getString());
            }
        }

        // sort this vector
        Collections.sort(entVct);

        entMdl = new javax.swing.DefaultListModel();
        for (int i=0; i<entVct.size(); i++){
            entMdl.addElement(entVct.elementAt(i));
        }

        return entMdl;
    }
}
