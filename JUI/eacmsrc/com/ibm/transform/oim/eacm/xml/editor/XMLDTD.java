// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.xml.editor;

import javax.swing.text.html.parser.*;

import java.util.*;
import java.io.*;
import javax.swing.*;

/******************************************************************************
* This is used for loading a DTD other than the default html32 DTD provided
* with the Java HTML package.
*
* @author Wendy Stimpson
* @version 1.0
*
* Change History:
*/
/*
* Issues:
*   How will the DTD to be used be specified?  Will the entire string be held in OPICM or
* would there be specific types associated with the role and the DTD string would be part of the
* editor jar file?
*/
// $Log: XMLDTD.java,v $
// Revision 1.1  2007/04/18 19:47:47  wendy
// Reorganized JUI module
//
// Revision 1.4  2006/05/10 14:43:00  wendy
// Change e-announce to EACM
//
// Revision 1.3  2006/01/25 18:59:03  wendy
// AHE copyright
//
// Revision 1.2  2005/10/12 12:48:56  wendy
// Conform to new jtest configuration
//
// Revision 1.1.1.1  2005/09/09 20:39:17  tony
// This is the initial load of OPICM
//
//
public class XMLDTD extends DTD
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /** cvs version */
    public static final String VERSION = "$Revision: 1.1 $";
    // track loaded DTDs; the hashtable in the base class has an entry even if the DTD is not completely loaded
    private static Vector dtdVct = null;

    private boolean supportSpecialChars=false;
    private int numNestedLevels=1;  // if number is not specified in dtd, default to one level of nesting (dtd must allow nesting too)

    // new dtds must define body.content!!
    private static final String REQ_DTD_STR =
    "<!--=== The following is required by the DTD parser supplied by the Java HTML pkg ==-->"+
    "<!ELEMENT BODY O O  %body.content>"+
    "<!ELEMENT TITLE - - (#pcdata*)>"+
    "<!ELEMENT head O O  (TITLE?)>"+
    "<!-- parser loops with , delimited content if not terminated by something with null content "+
    "Use plaintext defined in the default DTD -->"+
    "<!-- Also if content is (LI)? which means one or zero the parser will not accept "+
    "any.  AND if the parser attempts to correct for it, it loops if 'strict=false' (the default) -->"+
    "<!ELEMENT HTML O O  (head, body, plaintext?)>";

    static {
        dtdVct = new Vector(1);
        // load the base DTD used as default and for comparision v1.2
        try{
            getXMLDTD(XMLEditor.DEFAULT_DTDNAME);
        }catch(IOException ioe)
        {
            System.err.println("XMLDTD static load of default.dtd exc: "+ioe.getMessage());
            ioe.printStackTrace(System.err);
        }
    }

    int getNumberNestedLevels() { return numNestedLevels; }
    boolean getSupportSpecialChars() { return supportSpecialChars;}
    private XMLDTD(String name) throws IOException
    {
        super(name);
        init();
    }
    private void init() throws IOException
    {
        BufferedReader in =null;
        String s=null;
        DTDParser dtdp = null;
        StringBuffer sb = new StringBuffer();
        DTD html32DTD = XMLEditorKit.DEFAULT_DTD;
        String[] mnemonicTypes = { //"HTMLlat1.ent", these are in the binary dtd that is part of Java
            "HTMLsymbol.ent","HTMLspecial.ent" };

        // copy entity references from default dtd to this dtd subset
        for (Enumeration e = html32DTD.entityHash.keys(); e.hasMoreElements();)
        {
            Object key = e.nextElement();
            entityHash.put(key, html32DTD.entityHash.get(key));
        }
        // parser needs this defined or it loops with , delimited content
        elementHash.put("plaintext",html32DTD.getElement("plaintext"));
/*Element elem = html32DTD.getElement("plaintext");
System.out.println("plaintext: oStart: "+elem.oStart+" oEnd: "+elem.oEnd+" type: "+elem.type+
" content: "+elem.content+" attlist: "+elem.atts+" inclusions: "+elem.inclusions+" exclusions: "+
elem.exclusions);

elem = html32DTD.getElement("html");
System.out.println("orig html: oStart: "+elem.oStart+" oEnd: "+elem.oEnd+" type: "+elem.type+
" content: "+elem.content+" attlist: "+elem.atts+" inclusions: "+elem.inclusions+" exclusions: "+
elem.exclusions);
System.out.println("content type "+(char)elem.content.type+" content: "+elem.content.content
+" next: "+elem.content.next);
Vector elemVec = new Vector(1);
elem.content.getElements(elemVec);
System.out.println("content elements: "+elemVec);
for (int i=0;i<elemVec.size(); i++)
{
    Element ee = (Element)elemVec.elementAt(i);
System.out.println("orig "+ee.name+" oStart: "+ee.oStart+" oEnd: "+ee.oEnd+" type: "+ee.type+
" content: "+ee.content+" attlist: "+ee.atts+" inclusions: "+ee.inclusions+" exclusions: "+
ee.exclusions);
Vector elemVec2 = new Vector(1);
if (ee.content!=null)
{ee.content.getElements(elemVec2);
System.out.println("content type "+(char)ee.content.type+" content: "+ee.content.content+" next: "+ee.content.next);
System.out.println("content elements: "+elemVec2);
}
}*/

/*
Each dtd elementHash table will start with these elements:
key: unknown
key: style
key: plaintext
key: p
key: base
key: applet
key: param
key: #pcdata
key: html
key: body
key: isindex
key: link
key: head
key: title
key: meta
*/
        // parse this dtd to create elements, attributelists and contentmodel
        // somehow get the DTD string.. from a file or stream resource at the moment
        // for now get it from a file
        //String dtdname = "c:\\work\\opicm\\editor23\\"+name;
        in = getDTDResource(name);//new BufferedReader(new FileReader(dtdname));
        // append lines until done
        while((s=in.readLine()) !=null) {
            sb.append(s);
        }

        // read in all Character mnemonic entities from these files
        /* these should really be part of the DTD but applet can't open other URI so this syntax is useless
        From http://www.w3.org/TR/REC-html40/sgml/entities.html#h-24.2.1
        Character entity references in HTML 4

        http://www.w3.org/TR/REC-html40/HTMLlat1.ent
        http://www.w3.org/TR/REC-html40/HTMLsymbol.ent
        http://www.w3.org/TR/REC-html40/HTMLspecial.ent
<!--================ Character mnemonic entities =========================-->
<!ENTITY % HTMLlat1 PUBLIC
   "-//W3C//ENTITIES Latin1//EN//HTML"
   "HTMLlat1.ent">
%HTMLlat1;

<!ENTITY % HTMLsymbol PUBLIC
   "-//W3C//ENTITIES Symbols//EN//HTML"
   "HTMLsymbol.ent">
%HTMLsymbol;

<!ENTITY % HTMLspecial PUBLIC
   "-//W3C//ENTITIES Special//EN//HTML"
   "HTMLspecial.ent">
%HTMLspecial;
        */
        for (int i=0; i<mnemonicTypes.length; i++)
        {
            in = getDTDResource(mnemonicTypes[i]);
            if (in !=null)
            {
                while((s=in.readLine()) !=null) {
                    sb.append(s);
                }
            }
            else {
                System.err.println("Error: Can't open Character mnemonic entities file: "+mnemonicTypes[i]); }
        }

        // always append required <body><head> and <html> used by html parser
        sb.append(REQ_DTD_STR);

        dtdp = new DTDParser();
        dtdp.parse(sb.toString());
        dtdp=null;
    }

    private BufferedReader getDTDResource(String name)
    {
        BufferedReader reader = null;
        InputStream in = getClass().getResourceAsStream("/"+name);
        if(in==null) {
            System.err.println("Warning: could not get DTD resource: "+name);
        }
        else {
            reader = new BufferedReader(new InputStreamReader(in));
        }
        return reader;
    }

    private static void checkDTDResource(String name) throws IOException
    {
        XMLEditorDialogAppletBeanInfo objToGetClassLoader = new XMLEditorDialogAppletBeanInfo();
        InputStream in = objToGetClassLoader.getClass().getResourceAsStream("/"+name);

        if (in==null) {
            throw new IOException("The DTD specified \""+name+"\" cannot be found. "+XMLEditor.NEWLINE+
                "Using \""+ XMLEditor.DEFAULT_DTDNAME+"\" instead. "+XMLEditor.NEWLINE+
                "The text will not be editable.");
        }
    }

    /*********************************************************************
    * Get the DTD for this name
    * @param name String with name of DTD
    * @return DTD
    * @throws IOException
    */
    public static DTD getXMLDTD(String name) throws IOException
    {
        DTD dtd = null;
        name = name.toLowerCase();

        try {
            if (dtdVct.contains(name)) {
                dtd = DTD.getDTD(name);
            }
            else {
                // is this a recognized dtd? if not use the default dtd
                checkDTDResource(name);

                // create one of our own
                dtd = new XMLDTD(name);
                // if eannounce is going to generate dtds, they must be verified before usage
                // idea is load default.dtd statically.. then load and check all others against it
                // don't save a reference to it if it exceeds the maximum
                // output error msg dialog and return the default dtd
                // test it against the maximum set of elements in the default.dtd
                // issues.. will a name be provided so i can hang onto a reference to this dtd?.. it needs a name!
                // default.dtd will be part of jar file, will need new api with dtd and dtdname
                // won't need to check resources for dtd anymore?
                if (!name.equals(XMLEditor.DEFAULT_DTDNAME)) //v1.2
                {
                    verifyDTD(dtd);
                }

                // save the reference to the DTD in the private hashtable
                DTD.putDTDHash(name,dtd);
                // keep track of the fact that we have already instantiated one; the getDTD() will instantiate
                dtdVct.addElement(name);
            }
        }
        catch(IOException ioe)
        {
            final String errMsg = ioe.getMessage();
            // display error msg
            // must be in a separate thread to allow Accessibility engine to run
            SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        String accdesc[] = {
                            "Press OK to close dialog."
                            };
                        String msgs[]= null;
                        Vector vct = new Vector(1);
                        // if errmsg contains new lines, split it into separate strings
                        StringTokenizer st = new StringTokenizer(errMsg,XMLEditor.NEWLINE);
                        java.awt.Toolkit.getDefaultToolkit().beep();
                        while (st.hasMoreTokens())
                        {
                            vct.addElement(st.nextToken());
                        }

                        msgs= new String[vct.size()];
                        vct.copyInto(msgs);
                        vct.clear();

                        XMLEditor.showAccessibleDialog(null, XMLEditorGlobals.APP_NAME+" XML Editor DTD error",
                                JOptionPane.ERROR_MESSAGE, JOptionPane.YES_OPTION,
                                "Error loading DTD", msgs, accdesc);

                        msgs = null;
                        accdesc = null;
                    }
                });

            // use default dtd
            dtd = DTD.getDTD(XMLEditor.DEFAULT_DTDNAME);

            //ioe.printStackTrace(System.err);
        }

        return dtd;
    }

    // v1.2 verify new dtd does not exceed structure allowed by base dtd
    private static void verifyDTD(DTD dtd) throws IOException
    {
        StringBuffer sb = new StringBuffer();
        try {
            XMLDTD baseDTD = (XMLDTD)DTD.getDTD(XMLEditor.DEFAULT_DTDNAME);
            // get each element in the new dtd and compare against same element in the base dtd
            for (Enumeration e = dtd.elementHash.keys(); e.hasMoreElements();)
            {
                Element elem = null;
                Element baseElem = null;
                String key = (String)e.nextElement();
                if (!key.equals("body") && XMLParserDelegator.IGNORED_ELEM_VCT.contains(key)) {
                    continue;
                }

                elem = (Element) dtd.elementHash.get(key);
                baseElem = (Element) baseDTD.elementHash.get(key);
                if (baseElem==null)
                {
                    sb.append("Element <"+elem.getName()+"> is not supported by the base DTD."+XMLEditor.NEWLINE);
                    continue;
                }
                // body must have <p>...
                if (key.equals("body"))
                {
                    Element pElem = (Element) dtd.elementHash.get("p");
                    if (pElem==null)
                    {
                        sb.append("Element <"+elem.getName()+"> must support <p>."+XMLEditor.NEWLINE);
                    }
                }
                if (elem.getContent() !=null)  // content is defined, make sure it matches base dtd
                {
/*System.out.println("\nBaseElement name: <"+baseElem.name+"> omitStart: "+baseElem.oStart+" omitEnd: "+baseElem.oEnd+
                    "\nitype: "+baseElem.type+" \ncontentmodel: <"+baseElem.content+"> \ninclusions: "+baseElem.inclusions+
                    " \nexclusions: "+baseElem.exclusions);
System.out.println("\nElement name: <"+elem.name+"> omitStart: "+elem.oStart+" omitEnd: "+elem.oEnd+
                    "\nitype: "+elem.type+" \ncontentmodel: <"+elem.content+"> \ninclusions: "+elem.inclusions+
                    " \nexclusions: "+elem.exclusions);
*/
                    Vector elemVec = new Vector(1);
                    Vector baseElemVec = new Vector(1);
                    Vector badContentVct = new Vector(1);

                    elem.getContent().getElements(elemVec);
                    // if base has exclusions, apply them to the new Element too
                    // base exclusions are needed to make sure editor behaves properly
                    // like bold is not allowed in headings
                    if (baseElem.exclusions!=null)
                    {
                        BitSet excl = new BitSet();
                        // add the base exclusions
                        for (int i = 0; i < baseElem.exclusions.length(); i++)
                        {
                            if (baseElem.exclusions.get(i))  // bit is set
                            {
                                Element excludedElem = baseDTD.getElement(i);

                                //elem.exclusions.set(dtd.getElement(excludedElem.getName()).getIndex());
                                for (int x=0; x<elemVec.size();x++)
                                {
                                    Element contentElem = (Element)elemVec.elementAt(x);
                                    if (contentElem.getName().equals(excludedElem.getName()))
                                    {
//System.err.println("excludedelem is in content, flag it "+contentElem.getName());
                                        excl.set(dtd.getElement(excludedElem.getName()).getIndex());
                                        break;
                                    }
                                }
                            }
                        }
                        if (excl.length()>0)
                        {
                            if (elem.exclusions==null) {
                                elem.exclusions=(BitSet)excl.clone(); }
                            else {// add the base exclusions
                                elem.exclusions.or(excl);}
                        }

/*System.out.println("\nAFTER Element name: <"+elem.name+"> omitStart: "+elem.oStart+" omitEnd: "+elem.oEnd+
                    "\nitype: "+elem.type+" \ncontentmodel: <"+elem.content+"> \ninclusions: "+elem.inclusions+
                    " \nexclusions: "+elem.exclusions);*/
                    }

                    // remove any excluded elements from the vector
                    if (elem.exclusions!=null)
                    {
                        for (int i=0; i<elem.exclusions.length(); i++)
                        {
                            if (elem.exclusions.get(i))  // bit is set
                            {
                                Element excludedElem = dtd.getElement(i);
                                elemVec.remove(excludedElem);
                            }
                        }
                    }

                    if (baseElem.getContent() == null) // base dtd does not specify content for this element
                    {
                        sb.append("Element <"+elem.getName()+"> specifies content "+
                                elemVec+" that is not supported by the base DTD."+XMLEditor.NEWLINE);
                        continue;
                    }
                    baseElem.getContent().getElements(baseElemVec);
                    // remove any excluded elements from the vector
                    if (baseElem.exclusions!=null)
                    {
                        for (int i=0; i<baseElem.exclusions.length(); i++)
                        {
                            if (baseElem.exclusions.get(i))  // bit is set
                            {
                                Element excludedElem = baseDTD.getElement(i);
                                baseElemVec.remove(excludedElem);
                            }
                        }
                    }

                    // check each content specified in the new element against content in base element
                    outterloop:for (int i=0; i<elemVec.size(); i++)
                    {
                        for(int b=0; b<baseElemVec.size(); b++)
                        {
                            if (elemVec.elementAt(i).toString().equals(baseElemVec.elementAt(b).toString())) {
                                continue outterloop; }
                        }
                        badContentVct.addElement(elemVec.elementAt(i));
//                      sb.append("Element <"+elem.getName()+"> specifies content <"+
//                              elemVec.elementAt(i)+"> that is not supported by the base DTD "+baseElemVec+"\n");
                    }
                    if(badContentVct.size()>0) {
                        sb.append("Element <"+elem.getName()+"> specifies content "+
                            badContentVct+" that is not supported by the base DTD "+baseElemVec+XMLEditor.NEWLINE);
                    }
                    badContentVct.clear();

                    // check that attributes specified are supported
                    if (elem.atts != null)
                    {
                        AttributeList alist = null;
                        AttributeList baselist = null;
                        if (baseElem.atts == null) // base dtd does not specify attributes for this element
                        {
                            sb.append("Element <"+elem.getName()+"> specifies attributes "+
                                    "that are not supported by the base DTD."+XMLEditor.NEWLINE);
                            continue;
                        }

                        // more work needed here if more attributes are supported
                        // this assumes definition in same order
                        alist = elem.atts;
                        baselist = baseElem.atts;
                        while(alist !=null)
                        {
//System.out.println("name: "+alist+" type: "+alist.type+" modifier: "+alist.modifier+
//" value: "+alist.value+" values: "+alist.values);
                            if (alist.getName().equals(baselist.getName()))
                            {
                                if (baselist.values==null)
                                {
                                    if (alist.values !=null) {
                                        sb.append("Element <"+elem.getName()+"> specifies values "+
                                            alist.values+" for attribute ["+alist.getName()+"] that are not supported by the base DTD "+
                                            baselist.values+XMLEditor.NEWLINE);
                                    }
                                    break;
                                }
                                else
                                if (!baselist.values.containsAll(alist.values))
                                {
                                    sb.append("Element <"+elem.getName()+"> specifies values "+
                                        alist.values+" for attribute ["+alist.getName()+"] that are not supported by the base DTD "+
                                        baselist.values+XMLEditor.NEWLINE);
                                    break;
                                }
                            }
                            else
                            {
                                sb.append("Element <"+elem.getName()+"> specifies attributes ["+
                                    alist.getName()+"] that are not supported by the base DTD."+XMLEditor.NEWLINE);
                                break;
                            }
                            alist = alist.next;
                            baselist = baselist.next;
                            if (baselist==null && alist!=null)
                            {
                                sb.append("Element <"+elem.getName()+"> specifies attributes ["+
                                    alist.getName()+"] that are not supported by the base DTD."+XMLEditor.NEWLINE);
                                break;
                            }
                        }
                    }
                }
            }
        }
        catch(IOException ioe)
        {
            System.err.println("XMLDTD:verifyDTD exc: "+ioe.getMessage());
            ioe.printStackTrace(System.err);
        }

        // if errors were found, put them in a dialog
        if (sb.length()>0)
        {
            throw new IOException("The DTD \""+dtd.getName()+
                "\" is invalid.  It does not comply with supported DTD elements."+XMLEditor.NEWLINE+XMLEditor.NEWLINE+
                sb.toString());
        }
    }

    // XML DTDParser section
    private class DTDParser
    {
        private Hashtable parmEntityTbl = new Hashtable();

        // parse the DTD to get information into the DTD representation in the Java HTML package
        void parse(String DTDstring) throws IOException
        {
            Vector badElems = new Vector(1);

//System.err.println("STARTING parse for dtd "+XMLDTD.this.name);
            boolean skipit=false;
            // tokenize on XML markup character '<'
            StringTokenizer defSt = new StringTokenizer(DTDstring,"<");
            while (defSt.hasMoreTokens())
            {
                int IDdefault = -1;
                String def[] = null;
                String str = defSt.nextToken().toLowerCase().trim();  // !Entity or !Element or !attlist or !doctype or !--
                if (str.charAt(0)!='!') {
                    continue;
                }

                if (str.startsWith("!--"))  {  // its a comment
                    continue;
                }
                if (skipit)
                {
                    // have we reached the end of the deprecation list
                    if (str.endsWith("]>")) {
                        skipit=false;
                    }
                    continue;
                }
                if (str.endsWith(">")) {
                    str = str.substring(0, str.length()-1);
                }
                IDdefault = str.indexOf("#");
                if (IDdefault!=-1)
                {
                    // make sure a space preceeds it.  error (...)#IMPLIED
                    if (str.charAt(IDdefault-1) == ')')
                    {
                        StringBuffer sb = new StringBuffer(str);
                        sb.insert(IDdefault,' ');
                        str = sb.toString();
                    }
                }
                def =getDTDDef(str);
                // get any character references or substitution parms
                if (def[0].startsWith("!entity")){
                    parseEntity(def);
                }
                else
                if (def[0].startsWith("!element")) {
                    parseElement(def);
                }
                else
                if (def[0].startsWith("!attlist")) {
                    parseAttlist(def);
                }
                else  // must be doctype or?
                {
                    if (str.startsWith("!["))  // deprecated
                    {
                        if (!str.endsWith("]>")) {
                            skipit=true;
                        }
                    }
/*
<![ %HTML.Deprecated [

<!ENTITY % literal "CDATA"
        -- historical, non-conforming parsing mode where
           the only markup signal is the end tag
           in full
        -->

<!ELEMENT (XMP|LISTING) - -  %literal>
<!ELEMENT PLAINTEXT - O %literal>

]]>
*/
                    continue;
                }
            }

    /*
    if (!name.equals(XMLEditor.DEFAULT_DTDNAME))
            // check entity tables
            for (Enumeration e = entityHash.keys(); e.hasMoreElements();)
            {
                Object key = e.nextElement();
                //Entity ent = (Entity) entityHash.get(key);
    System.out.println("Found entity key: "+key);//+" entity: "+ent);
            }

            for (Enumeration e = parmEntityTbl.keys(); e.hasMoreElements();)
            {
                String key = (String)e.nextElement();
                String expansion = (String) parmEntityTbl.get(key);
    System.out.println("Found parm entity name: "+key+" expansion: "+expansion);
            }

            // look at the elements this dtd has built
            for (Enumeration e = elementHash.keys(); e.hasMoreElements();)
            {
                String key = (String)e.nextElement();
                Element elem = (Element) elementHash.get(key);
    System.out.println("\nElement name: <"+elem.name+"> omitStart: "+elem.oStart+" omitEnd: "+elem.oEnd+
                    "\nitype: "+elem.type+" \ncontentmodel: <"+elem.content+"> \ninclusions: "+elem.inclusions+
                    " \nexclusions: "+elem.exclusions);
                if (elem.content != null)
                {
                    // debug class cast exc
                    Vector elemVec = new Vector();
                    elem.content.getElements(elemVec);
                    for (Enumeration v = elemVec.elements(); v.hasMoreElements();) {
                        Object obj = v.nextElement();
System.out.println("obj from vec name: "+obj+" class: "+obj.getClass().getName());
                        Element ee = (Element)obj;
System.out.println("element from vec "+ee);
                    }
                }
                if (elem.atts != null)
                {
                    System.out.println("Attribute list:");
                    AttributeList al = elem.atts;
                    while(al !=null)
                    {
                        System.out.println("name: "+al+" type: "+al.type+" modifier: "+al.modifier+
                        " value: "+al.value+" values: "+al.values);
                        al = al.next;
                    }
                }
            }
    */

            // were any elements defined that were not used as content? if so, remove them from the hashtable
            // look at the elements this dtd has built
            outterloop:for (Enumeration e = elementHash.keys(); e.hasMoreElements();)
            {
                Element elem = null;
                String key = (String)e.nextElement();
                if (XMLParserDelegator.IGNORED_ELEM_VCT.contains(key)) {
                    continue;
                }

                elem = (Element) elementHash.get(key);
                // inner loop
                for (Enumeration e2 = elementHash.keys(); e2.hasMoreElements();)
                {
                    String key2 = (String)e2.nextElement();
                    Element elem2 = (Element) elementHash.get(key2);
                    if (elem2.getContent() != null)
                    {
                        Vector elemVec = new Vector(1);
                        elem2.getContent().getElements(elemVec);
                        if (elemVec.contains(elem)) {
                            continue outterloop;
                        }
                    }
                }
                // if dtd did not specify a <p> as body content it is an error, allow this to remove
                // it from base definition created for each dtd, but don't output a message
                if (!elem.name.equals("p")) {
                    System.err.println("XMLDTD:: Element <"+elem.name+"> not in any other content.  It will be removed!");
                }
                // match not found
                badElems.addElement(key);
            }

            if (badElems.contains("ul")&&badElems.contains("ol"))
            {
                badElems.addElement("li");
            }
            for(int i=0; i<badElems.size(); i++)
            {
                elementHash.remove(badElems.elementAt(i));
            }
            badElems.clear();
        }

        // get any macro parameter entity definitions or character references
        // parameter entity is used for expansion in the DTD, not in HTML.  char ref are in HTML
        private void parseEntity(String[] def) throws IOException
        {
            //parameter entity definition.. the %ref indicates substitution location
            //<!ENTITY % text "#PCDATA | %font | %phrase | %special | %form">
            //<!ENTITY % URI "CDATA" >
            // character reference
            //<!ENTITY nbsp   CDATA "&#160;" -- no-break space -->
            if (def[1].equals("%"))
            {
                // remove leading and trailing quotes
                String data = def[3];
                if (data.startsWith("\"")) {
                    data = data.substring(1);
                }
                if (data.endsWith("\"")) {
                    data = data.substring(0,data.length()-1).trim();
                }
                // convert any data references now
                data=convertRef(data);
                // save for further substitution
                parmEntityTbl.put(def[2], data);
            }
            else
            {
                // for now, use sc to turn on special character support
                // <!ENTITY sc CDATA "&#000;">
                if (def[1].equalsIgnoreCase("sc"))
                {
                    supportSpecialChars = true;
                } else {
                    // for now, use numlevels to control nesting depth
                    // <!ENTITY numlevels    NUMBER  1   -- nested limit on ol|ul  -->
                    if (def[1].equalsIgnoreCase("numlevels"))
                    {
    //System.err.println("found numlevels [2]:"+def[2]+" [3]:"+def[3]);
                        numNestedLevels = Integer.parseInt(def[3]);
                    }else {
                        if (def[2].equalsIgnoreCase("cdata"))
                        {
                            // this is in the format "&#160;" remove the quotes, &, ; and #
                            String data = def[3].substring(3, def[3].length()-2);
                            // define the character entity .. most have been defined by the default dtd
                            int type=DTD.CDATA | DTD.GENERAL;  // required by Entity class to correctly define a char entity ref
                            defEntity(def[1], type, Integer.parseInt(data)); // changed to passing an int for data
                        }
                        else
                        {
                            System.err.println("ERROR: entity ref [2]:"+def[2]+" [3]:"+def[3]+" is NOT supported!");
                        }
                    } // not 'numlevels'
                }  // not 'sc'
            }
        }

        // get the tag and its contents
        private void parseElement(String[] def) throws IOException
        {
/*System.err.println("parseElement entered def.len "+def.length);
for (int i=0;i<def.length; i++)
System.err.println("def["+i+"] "+def[i]);
*/          // "element's name"  "start tag req" "end tag req"  "element's content" "-exclusions"
            // - means required, O means optional for tags
            // element content is the content model. empty elements have EMPTY as a keyword
            // comments are delimited by double hyphens
            //<!ELEMENT P  - O (%text)*>
            //<!ELEMENT BR - O EMPTY    -- forced line break -->
            //<!ELEMENT UL - -  (LI)+>
            //<!ELEMENT LI - O %flow -- list item -->
            //<!ELEMENT OL - -  (LI)+>
            //<!ELEMENT (%font) - - (%text)*>

            ContentModel content = null;
            String[] inclusions=null;
            String[] exclusions=null;
            AttributeList atts = null;  // this will be defined later if an ATTLIST is found
            String contentStr = null;

            boolean omitStart = false; // hyphen means required;
            boolean omitEnd = false;     // 'O' means optional
            int contentId = 4;
            int exclId=5;
            int type=DTD.MODEL;  // EMPTY, MODEL, ANY, CDATA
            String name = def[1];

            // allow for element without start and end definitions, they are not
            // used in xhtml or html4.0, all elements that are not EMPTY must have an end tag
            // this parser must support it because of the base code requirement for
            // html, head and title definitions
            if (def[2].equals("-") || def[2].equals("o"))
            {
//System.err.println("using 3.2 definition ");
                omitStart = !def[2].equals("-"); // hyphen means required;
                omitEnd = !def[3].equals("-");   // 'O' means optional
            }
            else
            {
//System.err.println("using 4.0 definition ");
                contentId = 2;
                exclId=3;
            }

            contentStr = convertRef(def[contentId]); //[4]
            if (contentStr.equals("empty"))
            {
                type = DTD.EMPTY;
                omitEnd=true;
            }
            else if (contentStr.equals("cdata")) {
                type = DTD.CDATA; }
            else if (contentStr.equals("any")) {
                type = DTD.ANY; }
            else
            {
                content = parseContentModel(contentStr);
            }

            if (def.length > exclId)//5)
            {
                //<!ELEMENT HEAD O O  (%head.content) +(%head.misc)>
                //<!ELEMENT TITLE - -  (#PCDATA)* -(%head.misc)>
                // create and convert any exclusions or inclusions
                String data = convertRef(def[exclId]);//5]);
                char ctype = data.charAt(0);
                String[] array = null;
                data = data.substring(1);
                if (data.startsWith("(")) {
                    data = data.substring(1,data.length()-1);
                }

                // parse the string into substrings
                array = convertToArray(data,"|");
                if (ctype=='-') {
                    exclusions = array;
                }
                else {
                    inclusions=array;
                }
            }

            // convert any references
            if (name.indexOf("%")!=-1) {
                name = convertRef(name);
            }

            // if the name is a group, split it and store one element for each name
            if (!name.startsWith("(")) {
                defElement(name, type, omitStart, omitEnd, content, exclusions, inclusions, atts);
            }
            else
            {
                StringTokenizer st = null;
                name = name.substring(1,name.length()-1);
                st = new StringTokenizer(name,"|");
                while(st.hasMoreTokens())
                {
                    String token = st.nextToken().trim();
                    defElement(token, type, omitStart, omitEnd, content, exclusions, inclusions, atts);
                }
            }
        }

        // content will always be in () but may have a trailing qualifier of ? * +
        // can be nested  ((#pcdata | i | b | u | br) | p)*
        private ContentModel parseContentModel(String str)
        {
            ContentModel content = null;
            // type is ? for 0 or 1; * for 0 or more; + for 1 or more;
            // default to 0 for no type specification
            int cmType = 0;
            int openId = -1;
            char typeCh = str.charAt(str.length()-1);
            if (typeCh=='?'||typeCh=='*'||typeCh=='+')
            {
                cmType = (int)typeCh;
                str = str.substring(1,str.length()-2); // remove ()*
            }
            else    // remove ()
            {
                // content should be enclosed in ()
                if (typeCh==')' && str.charAt(0)=='(') {
                    str = str.substring(1,str.length()-1);
                }
                else {
                    System.err.println("CONTENT not in ()");
                }
            }

            openId = str.indexOf("(");
            if (openId !=-1) // nested ()
            {
                String CMobj = "CM";
                String nested = null;
                StringBuffer sb = null;
                int closeId = str.lastIndexOf(")");
                typeCh = str.charAt(closeId+1);
                if (typeCh=='?'||typeCh=='*'||typeCh=='+') {
                    closeId++;
                }

                nested = str.substring(openId,closeId);
                // recursively descend until no more nesting
                content = parseContentModel(nested);
                // remove the nested portion of the string
                sb = new StringBuffer(str);
                sb.delete(openId,closeId+1);
                if (typeCh=='?'||typeCh=='*'||typeCh=='+') {
                    CMobj=CMobj+str.charAt(closeId);
                }
                // replace () with CM.. This will be a flag in getContentModel
                sb.insert(openId,CMobj);
                str = sb.toString();
            }

            // finish the rest of the group now
            content = getContentModel(str, content);

            if (cmType !=0)
            {
                content = new ContentModel(cmType, content, null);
            }

            return content;
        }
        // content at this point will be the elements of a group delimited by | , &
        private ContentModel getContentModel(String groupStr, ContentModel prev)
        {
            String[] array = null;
            ContentModel next = null;
            // build a content model for this group
            //<!ENTITY % address.content "((%text;) | P)*">
            //<!ELEMENT ADDRESS - - %address.content>
            // content can be delimited by A|B for either A or B but not both
            //                             A,B for both A and B in that order
            //                             A&B for both A and B in any order
            String delim = null;
            int delimType=0;
            if (groupStr.indexOf("|")!=-1)
            {
                delim = "|";
                delimType = (int)delim.charAt(0);
            }
            else if (groupStr.indexOf(",")!=-1)
            {
                delim = ",";
                delimType = (int)delim.charAt(0);
            }
            else if (groupStr.indexOf("&")!=-1)
            {
                delim = "&";
                delimType = (int)delim.charAt(0);
            }

            // get the individual elements in the group and process in reverse order
            array = convertToArray(groupStr,delim);
            for (int i=array.length-1; i >= 0; i--)
            {
                int elemType = 0;
                String elemStr = array[i];
                // does element end with a type: ? * +
                char typeCh = elemStr.charAt(elemStr.length()-1);
                if (typeCh=='?'||typeCh=='*'||typeCh=='+')
                {
                    elemType = (int)typeCh;
                    elemStr = elemStr.substring(0,elemStr.length()-1);
                    if (elemStr.equals("CM"))  {// use a prev defined content model
                        next = new ContentModel(elemType, prev, next);
                    }
                    else
                    {
                        ContentModel cur = new ContentModel(0, getElement(elemStr), next);
                        next = new ContentModel(elemType, cur, next);
                    }
                }
                else
                {
                    if (elemStr.equals("CM")) {// use a prev defined content model
                        next = new ContentModel(elemType, prev, next);
                    }
                    else {
                        next = new ContentModel(elemType, getElement(elemStr), next);
                    }
                }
            }
            if (delimType!=0)
            {
                next = new ContentModel(delimType, next, null);
            }

            return next;
        }

        // get allowed attributes for an element
        private void parseAttlist(String[] def) throws IOException
        {
            // each attribute definition is triplet
            // 1) the name of the attribute (can be a parameter reference,
            //          if so type and value are part of it)
            // 2) the type of the attr value or explicit (set of possible values in parens)
            // 3) whether the default value is implicit (#IMPLIED) def value must be supplied???
            //          or always required (#REQUIRED) or fixed to the given value (#FIXED)
            // <!ATTLIST UL -- unordered lists --
            //      type    (%ULStyle)   #IMPLIED   -- bullet style --
            //      compact (compact)    #IMPLIED   -- reduced interitem spacing --
            //  >
            // <!ATTLIST LI
            //      type   (%LIStyle)    #IMPLIED   -- list item style --
            //  >

            // def[1] can be a reference or multiple tags
            String elemName = convertRef(def[1]);
            AttributeList next = null;
            for (int i=2; i<def.length; i++)
            {
                String typeVal = null;
                String mod = null;
                int type=DTD.NMTOKEN;
                int modifier=0;  // unknown =0
                String defValue = null;
                Vector values = null;

                // each one is a set of three
                String name = def[i];
                if (name.startsWith("%"))
                {
                    String triplets[] = null;
                    // expand the name into triplets
                    name = convertRef(name);
                    triplets = getDTDDef(name);

                    for (int x=0; x<triplets.length; x++)
                    {
                        typeVal=null;
                        mod = null;
                        type=DTD.NMTOKEN;
                        modifier=0;  // unknown =0
                        defValue = null;
                        values = null;

                        name = triplets[x++].trim();
                        typeVal = convertRef(triplets[x++]).trim();
                        mod = triplets[x].trim();
                        if (mod.startsWith("#"))
                        {
                            modifier = AttributeList.name2type(mod.substring(1));
                        }
                        else  {// it is a default value
                            defValue = mod;
                        }

                        // build vector of possible values delimited by (...)
                        if (typeVal.startsWith("("))
                        {
                            StringTokenizer st = null;
                            values = new Vector(1);
                            typeVal = typeVal.substring(1,typeVal.length()-1);  // remove ( )
                            st = new StringTokenizer(typeVal,"|");
                            while(st.hasMoreTokens()) {
                                values.addElement(st.nextToken().trim());
                            }
                        }
                        else {
                            type=AttributeList.name2type(typeVal.toUpperCase());
                        }

                        next = new AttributeList(name,type,modifier,defValue,values,next);
                        values = null;
                    }
                    continue;
                }
                else {
                    i++;
                }

                typeVal = convertRef(def[i++]);
                mod = def[i];
                type=DTD.NMTOKEN;
                modifier=0;  // unknown =0
                defValue = null;
                values = null;
                if (mod.startsWith("#"))
                {
                    modifier = AttributeList.name2type(mod.substring(1));
                }
                else  {// it is a default value
                    defValue = mod;
                }

                // build vector of possible values delimited by (...)
                if (typeVal.startsWith("("))
                {
                    StringTokenizer st = null;
                    values = new Vector(1);
                    typeVal = typeVal.substring(1,typeVal.length()-1);  // remove ( )
                    st = new StringTokenizer(typeVal,"|");
                    while(st.hasMoreTokens()) {
                        values.addElement(st.nextToken().trim());
                    }
                }
                else {
                    type=AttributeList.name2type(typeVal.toUpperCase());
                }

                next = new AttributeList(name,type,modifier,defValue,values,next);
            }

            // if the name is a group, split it and store one element for each name
            if (!elemName.startsWith("("))
            {
                defineAttributes(elemName, next);
            }
            else
            {
                StringTokenizer st = null;
                elemName = elemName.substring(1,elemName.length()-1);
                st = new StringTokenizer(elemName,"|");
                while(st.hasMoreTokens())
                {
                    String token = st.nextToken().trim();
                    defineAttributes(token, next);
                }
            }
        }

        /***********************************************************************************/
        // Get tokens
        private String convertRef(String name) throws IOException
        {
            String converted ="";
            int minId =0;
            String token = null;
            String val = null;
            // element delimiters
            String[] delims = { "|", ",", "&", ")",";"," "};
            int refId = name.indexOf("%");
            if (refId==-1) {
                converted = name.trim();
            }else {
                name = name.trim();
                if (name.endsWith(";")) {
                    name = name.substring(0,name.length()-1);
                }

                minId = name.length();
                // find the end of this token, there could be several tokens in one name
                for (int i=0; i<delims.length; i++)
                {
                    int id = name.indexOf(delims[i],refId);
                    if (id!= -1)
                    {
                        // find closest delimiter
                        minId=Math.min(minId,id);
                    }
                }

                token = name.substring(refId+1,minId).trim();
                // is token a key in the parameter entity table
                val = (String)parmEntityTbl.get(token);
                if (val==null)
                {
                    //throw an exception
                    throw new IOException("Parameter Entity reference was not found for ["+token+"].");
                }
                else
                {
                    // replace name with value from table
                    StringBuffer newNameSb = new StringBuffer(name.substring(0,refId));
                    newNameSb.append(val);
                    if (minId < name.length() && name.charAt(minId)==';') { // remove ;
                        minId++;
                    }

                    newNameSb.append(name.substring(minId));
                    converted= convertRef(newNameSb.toString()).trim();
                }
            }
            return converted;
        }

        private String removeComments(String msg)
        {
            int cmtId = msg.indexOf("--");
            while(cmtId!=-1)
            {
                StringBuffer sb = null;
                int cmtId2 = msg.indexOf("--",cmtId+1);
                if (cmtId2==-1){
                    break;
                }
                sb = new StringBuffer(msg);
                sb.delete(cmtId, cmtId2+2);
                msg = sb.toString();
                cmtId = msg.indexOf("--");
            }
            return msg;
        }

        // get the entire dtd definition as an array.  array[0] will be attlist, entity or element
        private String[] getDTDDef(String s)
        {
            s = removeComments(s);
            return convertToArray(s,null);
        }

        private String[] convertToArray(String s, String delimiter)
        {
            Vector vct = new Vector(1);
            StringTokenizer st = null;
            String array[]= null;
            if (delimiter==null) {
                st = new StringTokenizer(s);
            }
            else {
                st = new StringTokenizer(s,delimiter);
            }
            while(st.hasMoreTokens())
            {
                vct.addElement(getToken(st).trim());
            }
            array= new String[vct.size()];
            vct.copyInto(array);
            return array;
        }

        // get each entry into the dtd definition.. grouping "" and ()
        private String getToken(StringTokenizer st)
        {
            int openId =0;
            String token = st.nextToken();
            // stringtokenizer finds whitespace but must accumulate "" and ()
            if (token.startsWith("\"") && token.endsWith("\"")&& token.length()>1) {
                //return token;
            }
            else{
                if (token.startsWith("\""))
                {
                    StringBuffer sb = new StringBuffer(token);
                    while(st.hasMoreTokens())
                    {
                        token = st.nextToken();
                        sb.append(" "+token);
                        if (token.indexOf("\"")!=-1) {
                            //return sb.toString();
                            break;
                        }
                    }
                    token = sb.toString();//return sb.toString();  // closing " wasn't found!!
                }
                else {
                    openId = token.indexOf("(");
                    // check for nested ((
                    if (openId!=-1 && token.indexOf(")")!=-1 && (token.indexOf("(",openId+1)==-1))
                    {
                        //return token;
                    }
                    else {
                        if (openId!=-1)
                        {
                            StringBuffer sb = null;
                            int openCnt = 0;
                            openId= token.indexOf("(");
                            while(openId!=-1)
                            {
                                openCnt++;
                                openId= token.indexOf("(", openId+1);
                            }
                            sb = new StringBuffer(token);
                            while(st.hasMoreTokens())
                            {
                                token = st.nextToken();
                                openId= token.indexOf("(");
                                while(openId!=-1)
                                {
                                    openCnt++;
                                    openId= token.indexOf("(", openId+1);
                                }
                                sb.append(" "+token);
                                if (token.indexOf(")")!=-1)
                                {
                                    int closeId=token.indexOf(")");
                                    while(closeId!=-1)
                                    {
                                        openCnt--;
                                        closeId= token.indexOf(")", closeId+1);
                                    }

                                    if (openCnt==0) {
                                        token = sb.toString();//return sb.toString();
                                        break;
                                    }
                                }
                            }
                            token = sb.toString();//return sb.toString();  // closing ) wasn't found!!
                        }
                    }// wasn't nested ((
                } // didn't start with a quote
            } // wasn't quoted token

            return token;
        }
    }
}
