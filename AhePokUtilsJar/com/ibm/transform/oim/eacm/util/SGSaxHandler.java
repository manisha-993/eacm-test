// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.util;
//import com.ibm.transform.oim.eacm.util.*;

import java.text.*;
import java.util.*;
import java.io.*;
import org.xml.sax.*;

/**********************************************************************************
 * This class uses the SAX interface to convert EANNetChanges.genVEChangeXML() XML into HTML
 * It can have several possible roots
 *  "MODEL", "RPTTMFSRD1" // VE with filters on IMG, FB, MM and WARR
 *  "WWSEO", "RPTWWSEOSRD1" // VE with filters on IMG, FB, MM and WARR
 *  "LSEO",  "RPTLSEOSRD"
 *  "LSEOBUNDLE", "RPTLSEOBUNDLESRD"
 *
 * LSEO and LSEOBUNDLE roots can have a countryfilter specified, if user doesn't select specific
 * countries, the value of COUNTRYLIST attribute on root is used
 * 	Must check for country list on "MM" "IMG" "FB" "WARR" if country filter is specified.
 * One match is sufficient.
 *
 * If an LSEOBUNDLE's WARR, FB, IMG or MM has ALL of the same countries as the WWSEO's WARR, FB, IMG or MM
 * don't display the entity linked to the WWSEO
 * The set of countries used is based on the ctryList specified by the user.
 */
// $Log: SGSaxHandler.java,v $
// Revision 1.3  2008/01/22 18:28:17  wendy
// Cleanup RSA warnings
//
// Revision 1.2  2007/06/26 19:32:24  wendy
// *** empty log message ***
//
// Revision 1.1  2006/07/12 18:51:21  wendy
// SAX replacement for DOM XSL for change api to improve performance
//
//
public class SGSaxHandler extends org.xml.sax.helpers.DefaultHandler
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.3 $";

    private static final String ATTR_UNC ="Unchanged";
    private static final String ENTITY_UNC ="Unchanged";
    private static final String ENTITY_DEL ="Deleted";
	private static final int FLUSH_CNT=20;  // flush on this number of emit calls
	private static final int FLUSH_LEN=200;  // flush on this string length

    private boolean showAll = false;
    private boolean debug = false;  // output trace low level debug msgs
    private StringBuffer indentsb = new StringBuffer();
	private int emitcount=0;  // prevent flushing on every emit

	private Writer outWriter;
	private StringBuffer contentSb = null;// holds xml attributes as they are parsed
	private int entityCnt =0;  // used for this run to make unique id and header tag attributes for accessibility and Webking
	private String curCountStr = "";  // indicates x of y count
	private Vector ctryList = null;  // if not null, it has country filter for MM, IMG, FB and WARR
	private Stack entityStk = new Stack();  // used for SAX parser callbacks to build structure
	private Vector overrideWwseoMMVct = new Vector(1);    // used for override of WWSEO's "MM"
	private Vector overrideLseobMMVct = new Vector(1);    // used for override of WWSEO's "MM"
	private Vector overrideWwseoIMGVct = new Vector(1);   // used for override of WWSEO's "IMG"
	private Vector overrideLseobIMGVct = new Vector(1);   // used for override of WWSEO's "IMG"
	private Vector overrideWwseoFBVct = new Vector(1);    // used for override of WWSEO's "FB"
	private Vector overrideLseobFBVct = new Vector(1);    // used for override of WWSEO's "FB"
	private Vector overrideWwseoWARRVct = new Vector(1);  // used for override of WWSEO's "WARR"
	private Vector overrideLseobWARRVct = new Vector(1);  // used for override of WWSEO's "WARR"
	private ResourceBundle bundle;
	private String propertiesList[];

	private static Hashtable BUNDLE_TBL = new Hashtable();
	static
	{
		BUNDLE_TBL.put("Text_NoDataFound", "No Data Found");
		BUNDLE_TBL.put("Label_EndOf", "End of");
		BUNDLE_TBL.put("Text_Change_Properties", "Change Type|Attribute|Previous Value|Current Value|Date Changed|Changed By");
	}

    private String getBundleString(String txt) {
		String val = "";
		if (bundle==null) {
			val = (String)BUNDLE_TBL.get(txt);
		}else {
			val = bundle.getString(txt);
		}
        return val;
    }

    /***************************************
    * Constructor
    *@param out Writer output stream for HTML converted from XML
    *@param rbundle ResourceBundle
    */
	public SGSaxHandler(Writer out,ResourceBundle rbundle)
	{
		String properties;
		outWriter=out;
		bundle = rbundle;

		//Get the header row properties
		properties = getBundleString("Text_Change_Properties");
		propertiesList = PokUtils.convertToArray(properties);
	}
    /***************************************
    * Set showall structure for debug
    *@param b boolean
    */
	public void setShowAll(boolean b)
	{
		showAll=b;
	}
    /***************************************
    * Set debug, only debug=all will turn this low level debug on
    *@param b boolean
    */
	public void setDebug(boolean b)
	{
		debug=b;
	}
    /***************************************
    * Render the XML into HTML onto the output stream used in the constructor
    * one root per call
    *
    *@param xmlsb StringBuffer with XML from change api
    *@param countStr String with info for root heading
    *@param ctryfilter String list of countries to filter on, may be ""
    *@throws java.io.IOException
    *@throws SAXException
    *@throws javax.xml.parsers.ParserConfigurationException
    */
	public void renderXML(StringBuffer xmlsb, String countStr,
		String ctryfilter) throws java.io.IOException, SAXException,
		javax.xml.parsers.ParserConfigurationException
	{
	    // Use an instance of ourselves as the SAX event handler
		// Use the default (non-validating) parser
		javax.xml.parsers.SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();
		javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
		curCountStr = countStr;
		reset();  // make sure variables are reset if called again (if more than one root selected by user)

		//  if filter specified, "MM" "IMG" "FB" "WARR" must have one of these countries in COUNTRYLIST
		if (ctryfilter != null && ctryfilter.length()>0) {
			StringTokenizer st = new StringTokenizer(ctryfilter, "*");
			ctryList = new Vector(1);
			while (st.hasMoreTokens()) {
				ctryList.add(st.nextToken());
			}
			emitln("<!-- Using country filter: "+ctryfilter+" -->");
		}

		// Parse the input
		saxParser.parse(new InputSource(new StringReader(xmlsb.toString())), this);
	}

    /***************************************
    * Reset variables for new run
    */
	private void reset() {
		contentSb = null;// holds xml attributes/content as they are parsed
		if (ctryList !=null) {
			ctryList.clear();
			ctryList = null;
		}
		entityStk.clear();
		overrideWwseoMMVct.clear();
		overrideLseobMMVct.clear();
		overrideWwseoIMGVct.clear();
		overrideLseobIMGVct.clear();
		overrideWwseoFBVct.clear();
		overrideLseobFBVct.clear();
		overrideWwseoWARRVct.clear();
		overrideLseobWARRVct.clear();
		emitcount=0;
	}

    /***************************************
    * Release memory
    */
	public void dereference() {
		reset();
		outWriter = null;
		entityStk = null;
		overrideWwseoMMVct= null;
		overrideLseobMMVct= null;
		overrideWwseoIMGVct= null;
		overrideLseobIMGVct= null;
		overrideWwseoFBVct= null;
		overrideLseobFBVct= null;
		overrideWwseoWARRVct= null;
		overrideLseobWARRVct= null;
	}

	//===========================================================
	// SAX DocumentHandler methods
	//===========================================================

	/*************************************************************
	* Receive notification of the start of an element.
	*
	* @param namespaceURI String
	* @param lName String The local name (without prefix), or the empty string if
	*	Namespace processing is not being performed.
	* @param qName String The qualified name (with prefix), or the empty string if qualified names
	*	are not available.
	* @param attrs  Attributes  The specified or defaulted attributes.
	* @throws SAXException - Any SAX exception, possibly wrapping another exception.
	*/
	public void startElement(String namespaceURI,
							 String lName, // local name
							 String qName, // qualified name
							 Attributes attrs)	throws SAXException
	{
		String eName = lName; // element name
		EntityInfo curEntity = null;

		if ("".equals(eName)) {
			eName = qName; // namespaceAware = false
		}

		if (eName.equals("entity")){
			EntityInfo parentElem = null;
			if (!entityStk.empty()){
				parentElem = (EntityInfo)entityStk.peek();
			}
			curEntity = new EntityInfo(attrs, parentElem,entityCnt++);
			// hang onto relators for override check, have to do check after all entities have been parsed
			if (curEntity.type.equals("WWSEOMM")){
				overrideWwseoMMVct.add(curEntity);
			}else if (curEntity.type.equals("WWSEOFB")){
				overrideWwseoFBVct.add(curEntity);
			}else if (curEntity.type.equals("WWSEOIMG")){
				overrideWwseoIMGVct.add(curEntity);
			}else if (curEntity.type.equals("WWSEOWARR")){
				overrideWwseoWARRVct.add(curEntity);
			}else if (curEntity.type.equals("LSEOBUNDLEMM")){
				overrideLseobMMVct.add(curEntity);
			}else if (curEntity.type.equals("LSEOBUNDLEFB")){
				overrideLseobFBVct.add(curEntity);
			}else if (curEntity.type.equals("LSEOBUNDLEIMG")){
				overrideLseobIMGVct.add(curEntity);
			}else if (curEntity.type.equals("LSEOBUNDLEWARR")){
				overrideLseobWARRVct.add(curEntity);
			}

			//"MM" "IMG" "FB" "WARR"
			emitTrace("pushed entity "+curEntity);
			indentsb.append("   ");
			if (parentElem!=null){
				parentElem.addNestedEntity(curEntity);
			}
			entityStk.push(curEntity);
		}

		if (eName.equals("attribute")){
			if (!entityStk.empty()){
				curEntity = (EntityInfo)entityStk.peek();
				curEntity.addAttribute(attrs);
			}
		}

		if (contentSb!=null) {  // hang onto inner tags with their attributes
			contentSb.append("<"+eName);
			if (attrs != null) {
				for (int i = 0; i < attrs.getLength(); i++) {
					String aName = attrs.getLocalName(i); // Attr name
					if ("".equals(aName)) {
						aName = attrs.getQName(i);
					}
					contentSb.append(" "+aName+"=\""+attrs.getValue(i)+"\"");
				}
			}
			contentSb.append(">");
		}

		// a value may contain other tags, so content and inner tags must be accumulated
		if (eName.equals("currentvalue") || eName.equals("priorvalue")){
			contentSb = new StringBuffer();
		}
	}

	/*************************************************************
	* Receive notification of the end of an element.
	*
	* @param namespaceURI String
	* @param lName String The local name (without prefix), or the empty string if
	*	Namespace processing is not being performed.
	* @param qName String The qualified XML 1.0 name (with prefix), or the empty string
	*   if qualified names are not available
	* @throws SAXException - Any SAX exception, possibly wrapping another exception.
	*/
	public void endElement(String namespaceURI,
						   String lName, // local name
						   String qName) throws SAXException
	{
		String eName = lName; // element name
		if ("".equals(eName)) {
			eName = qName; // namespaceAware = false
		}

		if(eName.equals("entity")){
			indentsb.delete(0,3);
			if (entityStk.size()>1){ // leave last one.. it is root
				EntityInfo tmp = (EntityInfo)entityStk.pop();
				emitTrace("popped entity "+tmp);
			}else{
				EntityInfo tmp = (EntityInfo)entityStk.peek();
				emitTrace("endElement entity BUT only one left in stck "+tmp);
			}
		}else { // not an entity
			// a value may contain other tags, so content and inner tags must be accumulated
			if (eName.equals("priorvalue") || eName.equals("currentvalue")){
				if (!entityStk.empty()){
					EntityInfo curEntity = (EntityInfo)entityStk.peek();
					if (eName.equals("currentvalue")){
						curEntity.addCurrentVal(contentSb.toString());
					}else if (eName.equals("priorvalue")){
						curEntity.addPriorVal(contentSb.toString());
					}
				}
				contentSb=null;
			}

			// still inside prior or current value element
			if (contentSb!=null) {
				contentSb.append("</"+eName+">");
			}
		}
	}
	/*************************************************************
	* Receive notification of character data inside an element.
	*
	* @param buf char[]  The characters.
	* @param offset int	The start position in the character array.
	* @param len int	The number of characters to use from the character array.
	* @throws SAXException - Any SAX exception, possibly wrapping another exception.
	*/
	public void characters(char buf[], int offset, int len)	throws SAXException
	{
		// a value may contain other tags, so content and inner tags must be accumulated
		if (contentSb!=null) {
			String s = new String(buf, offset, len);
			s = convertToHTML(s);
			contentSb.append(s);
		}
	}
	/*************************************************************
	* Receive notification of the end of the document.
	* @throws SAXException - Any SAX exception, possibly wrapping another exception.
	*/
	public void endDocument()    throws SAXException
	{
		if (!entityStk.empty()){ // last one.. it is root
			EntityInfo tmp = (EntityInfo)entityStk.pop();
			emitTrace("Root:? "+tmp.display(""));
			if (ctryList!=null && tmp.type.equals("LSEOBUNDLE")){  // override some WWSEO's links
				overrideWWSEO(overrideWwseoMMVct,overrideLseobMMVct);
				overrideWWSEO(overrideWwseoIMGVct,overrideLseobIMGVct);
				overrideWWSEO(overrideWwseoFBVct,overrideLseobFBVct);
				overrideWWSEO(overrideWwseoWARRVct,overrideLseobWARRVct);
			}
			tmp.outputHtml();

    		emitln("<p>"+getBundleString("Label_EndOf")+" "+tmp.description+"</p>");

			tmp.dereference();
		}else {
			throw new SAXException("Error parsing XML.  No Root on Stack");
		}

		//fixme comment this out when run for real.. just my local tests!!
		//outputfooter();

		try {
			outWriter.flush();  // make sure last line is flushed
		}catch(IOException ioe) {
			System.err.println(ioe);  // jtest req
		}
	}
//fixme comment this out when run for real.. just my local tests!!
/**	private void outputfooter() throws SAXException
	{
		//outputWKfooter();

		emitln("</body></html>	");
	}
	private void outputWKfooter() throws SAXException
	{
		emitln("<div id=\"popup-footer\">");
		emitln("  <div class=\"hrule-dots\">&nbsp;</div>");
		emitln("    <div class=\"content\">");
		emitln("        <script type=\"text/javascript\" language=\"JavaScript\">");
		emitln("function terminate() { top.window.close();}function printReport() { top.window.print();}function saveReport() { document.execCommand('SaveAs',null,'report.html')}</script>");
		emitln("<div style=\"padding:0 1em 0.4em 34px; float:right;\"><a class=\"popup-print-link\" style=\"float:none;background:url(//w3.ibm.com/ui/v8/images/icon-link-download.gif) 7px 2px no-repeat;\" href=\"javascript:saveReport();\">Save As...</a><a class=\"popup-print-link\" style=\"float:none;\" href=\"javascript:printReport();\">Print</a><a style=\"padding:0 1em 0.4em 4px;\" href=\"javascript:terminate();\">Close Window</a></div>");
		emitln("    </div>");
		emitln("  <div style=\"clear:both;\">&nbsp;</div>");
		emitln("</div>");
		emitln("<script language=\"JavaScript\" type=\"text/javaScript\">");
		emitln("<!--");
		emitln("    function openTOU() {");
		emitln("    window.open(\"http://w3.ibm.com/w3/info_terms_of_use.html\", \"TOU\", \"dependent,width=800,height=600,screenX=100,screenY=100,left=100,top=100,titlebar,scrollbars,status,resizable\");");
		emitln("    }");
		emitln("//-->");
		emitln("</script>");
		emitln("        <p class=\"terms\">");
		emitln("            <a href=\"javascript:openTOU();\" id=\"tou\">Terms of use</a>");
		emitln("        </p>");
		emitln("    </div>");
		emitln("</div>");
	}/**/
	/*************************************************************
	* Receive notification of the start of the document.
	*
	* @throws SAXException - Any SAX exception, possibly wrapping another exception.
	*/
	public void startDocument()   throws SAXException
	{
		//fixme comment this out when run for real.. just for my local tests!!
		//outputStart();
	}
//fixme comment this out when run for real.. just my local tests!!
/** /	private void outputStart() throws SAXException
	{
		//WK version
		//outputWKStart();

		//short version
		emitln("<html><head>");
		emitln("<style type=\"text/css\" media=\"all\">");
		emitln("<!--");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/screen.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/icons.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/interior.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/popup-window.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/tables.css\");");
		emitln("-->");
		emitln("</style>");
		emitln("</head><body>");
	}

/**	private void outputWKStart() throws SAXException
	{
		emitln("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		emitln("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
		emitln("<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
		emitln("<meta name=\"Description\" content=\"SG_ChangeReport.jsp\" />");
		emitln("<meta name=\"Keywords\" content=\"EACM\" />");
		emitln("<meta name=\"Owner\" content=\"opicma@us.ibm.com\" />");
		emitln("<meta name=\"Robots\" content=\"noindex,nofollow\" />");
		emitln("<meta name=\"Security\" content=\"internal use only\" />");
		emitln("<meta name=\"Source\" content=\"v8 Template Generator\" />");
		emitln("<meta name=\"IBM.Country\" content=\"ZZ\" />");
		emitln("<meta name=\"DC.Date\" scheme=\"iso8601\" content=\"2006-06-29\" />");
		emitln("<meta name=\"DC.Language\" scheme=\"rfc1766\" content=\"en-US\" />");
		emitln("<meta name=\"DC.Rights\" content=\"Copyright (c) 2000,2006 by IBM Corporation\"  />");
		emitln("<meta name=\"feedback\" content=\"opicma@us.ibm.com\" />");

		emitln("<style type=\"text/css\" media=\"all\">");
		emitln("<!--");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/screen.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/icons.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/interior.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/popup-window.css\");");
		emitln("@import url(\"http://w3.ibm.com/ui/v8/css/tables.css\");");
		emitln("-->");
		emitln("</style>");

		emitln("<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"http://w3.ibm.com/ui/v8/css/print.css\" />");
		emitln("<title>EACM pokxea6 Migration Verification | TMF Change Report</title>");
		emitln("</head>");
		emitln("<body id=\"w3-ibm-com\">");
		emitln("<div id=\"popup-masthead\">");
		emitln("    <img id=\"popup-w3-sitemark\" src=\"http://w3.ibm.com/ui/v8/images/id-w3-sitemark-small.gif\" alt=\"\" width=\"182\" height=\"26\" />");
		emitln("</div>");
		emitln("<div id=\"content\">");
		emitln("    <div id=\"content-main\">");
	}
	/**/
	//===========================================================
	// Utility Methods ...
	//===========================================================
    /********************************************************************************
    * Put string on output stream
    * @param s    String to output
    */
	void emit(String s) throws SAXException
	{
		try {
			emitcount++;
			outWriter.write(s);
			if (emitcount>FLUSH_CNT || s.length()>FLUSH_LEN) {
				outWriter.flush();
				emitcount=0;
			}
		} catch (IOException e) {
			// Wrap I/O exceptions in SAX exceptions, to suit handler signature requirements
			throw new SAXException("I/O error", e);
		}
	}

    /********************************************************************************
    * Put string on output stream appending a new line
    * @param s    String to output
    */
	void emitln(String s) throws SAXException
	{
		emit(s+NEWLINE);
	}

    /********************************************************************************
    * Put string on output stream inside an html comment
    * @param s    String to output
    */
	void emitWarning(String s) {
		try{
			emitln("<!-- WARNING: "+s+" -->");
		}catch(SAXException se) {
			System.err.println(se);
		}
	}
    /********************************************************************************
    * Put string on output stream inside an html comment
    * these are lowlevel debug info
    * @param s    String to output
    */
	void emitTrace(String s) {
		try{
			if (debug) {
				emitln("<!--  "+indentsb+s+" -->");
			}else {
				// output an empty string to keep browser active
				outWriter.write(NEWLINE);
				outWriter.flush();
			}
			//	System.err.println(indentsb+s);  // fixme uncomment and redirect to file when tested locally
		}catch(Exception se) {
			System.err.println(se);
		}
	}
    /********************************************************************************
    * Convert string into valid html.  Special HTML characters are converted.
    * SAX parser outputs the character, not the character entity reference;
    *
    * @param txt    String to convert
    * @return String
    */
    private static String convertToHTML(String txt)
    {
        String retVal=null;
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<': // could be saved as &lt; also. this will be &#60;
                case '>': // could be saved as &gt; also. this will be &#62;
                case '"': // could be saved as &quot; also. this will be &#34;
                case '&': // ignore entity references such as &lt; if user typed it, user will see it
                          // could be saved as &amp; also. this will be &#38;
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                default:
                    htmlSB.append(ch);
                    break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }

    /*************************************************************************************
    * Verifies if LSEOBUNDLE is an override of WWSEO.  If an LSEOBUNDLE's WARR, FB, IMG or MM
    * has the same countries as the WWSEO's WARR, FB, IMG or MM don't display the entity linked to the WWSEO
    * The set of countries used is based on the ctryList specified by the user.
    * All countries that meet the filter in the WWSEO's child entity
    *  must exist in the LSEOBUNDLE's child entity to suppress the WWSEO child
    */
    private void overrideWWSEO(Vector wwseoVct,Vector lseobVct)  throws SAXException
    {
		//look at WWSEOxx for matching LSEOBUNDLExx.. get their children,
		for (int wr = 0; wr<wwseoVct.size(); wr++) {
			EntityInfo wwseoRel = (EntityInfo)wwseoVct.elementAt(wr);
			EntityInfo wwseoxx = wwseoRel.getDownLink();
			if(wwseoxx!=null){
				// get countries that match the filter
				Vector wwseoCtry = wwseoxx.getMatchingCountries();
				for (int lr = 0; lr<lseobVct.size(); lr++) {
					EntityInfo lseobRel = (EntityInfo)lseobVct.elementAt(lr);
					EntityInfo lseobxx = lseobRel.getDownLink();
					if (lseobxx!=null) {
						// get countries that match the filter
						Vector lseobundleCtry = lseobxx.getMatchingCountries();
						//if there is a complete match between wwseoCtry and lseoBundleCtry then its an override
						if (lseobundleCtry.size()>0 && // must match at least one country
							lseobundleCtry.containsAll(wwseoCtry) && wwseoCtry.containsAll(lseobundleCtry)) {
							wwseoxx.setOverridden();
							emitln("<!-- "+lseobRel.key+":"+lseobxx.key+" override of "+
								wwseoRel.key+":"+wwseoxx.key+" with ctryVct:"+lseobundleCtry+" -->");
						}
						lseobundleCtry.clear();
					}
				}
				wwseoCtry.clear();
			}
		}
	}

	/*************************************************************************************
	* Convenience classes
	**************************************************************************************/
	/*************************************************************************************
	* This class is used to hold Entity info
	*<entity description="Model" type="MODEL" eid="84628" key="MODEL84628" depth="0" level="0" direction="Root" class="E" status="Unchanged">
	*/
	private class EntityInfo implements Comparable
	{
		private String description, eid,type, eclass, direction, status, parentkey="", key;
		private int level=0, depth=0;
		private int uniqueid=0;
		// the same entityinfo can be displayed more than once if it is both an up and dn link to another ei, need this to make WK happy
		private int displayCnt=0;
		private boolean isoverridden = false;  // WWSEO's WARR, IMG, FB or MM might be overridden and should not be displayed
		private Vector attrVct = new Vector();
		private StringBuffer navNameSb = new StringBuffer(); // will be appended to with nav attributes
    	private Vector downLinkVct; // EntityInfo thru "D" direction
    	private Vector upLinkVct;// EntityInfo thru "U" direction
    	private EntityInfo parentElement;

    	String getKeys() {
			StringBuffer sb = new StringBuffer(key+"["+direction+"]["+eclass+"]");
			if (parentElement!=null){
				sb.append(":"+parentElement.getKeys());
			}

			return sb.toString();
		}

		/********************************************************************************
		* Constructor
		* @param attrs  Attributes for this entity
		* @param pelem  EntityInfo that is parent element in the XML for this entity
		* @param uid   int used to make this table unique for WK
		*/
		EntityInfo(Attributes attrs, EntityInfo pelem, int uid) {
			parentElement = pelem;  // will be null for root
			uniqueid = uid;
			downLinkVct = new Vector(1);
			upLinkVct = new Vector(1);

			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i); // Attr name
				String value = attrs.getValue(i);
				if ("".equals(aName)) {
					aName = attrs.getQName(i);
				}
				if (aName.equals("description")) {
					description = value;
				}else if (aName.equals("eid")) {
					eid = value;
				}else if (aName.equals("type")) {
					type = value;
				}else if (aName.equals("class")) {
					eclass = value;
				}else if (aName.equals("direction")) {
					direction = value;
				}else if (aName.equals("status")) {
					status = value;
				}else if (aName.equals("level")) {
					level = Integer.parseInt(value)+1;
				}else if (aName.equals("depth")) {
					depth = Integer.parseInt(value)+1;
				}else if (aName.equals("parentkey")) {  // root does not have a parentkey
					parentkey = value;
				}else if (aName.equals("key")) {
					key = value;
				}
			}
		}

		/********************************************************************************
		* A nested attribute was parsed, add it to the attribute vector
		* @param attrs Attributes just parsed for the <attribute> element
		*/
		void addAttribute(Attributes attrs) { // add all here, check for unchanged on output and ABR too
			SortedAttr sa = new SortedAttr(attrs);
			// can't discard ABR here because priorvalue and currentvalue elements have not been read yet
			attrVct.add(sa);
		}

		/********************************************************************************
		* A <currentvalue> was parsed, add it to last attribute in the vector
		* @param val String
		*/
		void addCurrentVal(String val) {
			if (attrVct.size()>0){
				SortedAttr sa = (SortedAttr) attrVct.lastElement();
				// only use currentvalue for nav name
				if (sa.isNav()){
					if (navNameSb.length()>0) {
						navNameSb.append(" - ");
					}
					navNameSb.append(val);
				}
				sa.setCurValue(val);
			}
		}
		/********************************************************************************
		* A <priorvalue> was parsed, add it to last attribute in the vector
		* @param val String
		*/
		void addPriorVal(String val) {
			if (attrVct.size()>0){
				SortedAttr sa = (SortedAttr) attrVct.lastElement();
				sa.setPrevValue(val);
			}
		}

		/********************************************************************************
		* A nested entity was parsed, add it as a downlink or uplink based on it's direction
		* @param ei EntityInfo just parsed
		*/
		void addNestedEntity(EntityInfo ei) {
			if ("D".equals(ei.direction)){
				emitTrace("  added dnlink "+ei.key+" with parentkey: "+ei.parentkey+" TO "+key+
					" lvl: "+level+" dir "+direction+" class "+eclass);
				downLinkVct.add(ei);
			}else {
				emitTrace("  added uplink "+ei.key+" with parentkey: "+ei.parentkey+" TO "+key+
					" lvl: "+level+" dir "+direction+" class "+eclass);
				upLinkVct.add(ei);
			}
			// if ei.parentkey does not match this key something is wrong with XML structure
			// it has something nested at the wrong level
			if (!ei.parentkey.equals(key)){
				emitWarning("XML structure problem!! Adding nested entity with parentkey that does not match entity key: "+key+
						" nested.parentkey: "+ei.parentkey+" nested.key: "+ei.key);
			}
		}

		/********************************************************************************
		* Get downlink from this relator
		* used for WWSEO override, assumes this entity is a Relator and return first downlink
		* @return EntityInfo
		*/
		EntityInfo getDownLink() {
			EntityInfo child = null;
			if (!eclass.equals("R")){
				emitWarning("Downlink needed but this is not a relator!! key: "+key+" class: "+eclass);
			}else if (!direction.equals("D")){
				emitWarning("Downlink needed but direction was not D!! key: "+key+" direction: "+direction);
			}else {
				if (downLinkVct.size()>0){
					child = (EntityInfo)downLinkVct.firstElement();
				}
			}

			if (child==null){
				emitWarning("Downlink needed but did not exist for key: "+key+" class: "+eclass);
			}
			return child;
		}

		/********************************************************************************
		* Get entity from this relator (or association), try D first, else U
		* assumption XML is <relator><entity></entity></relator> or
		* <relator><relator><entity></entity></relator></relator>
		* @return EntityInfo
		* /
		private EntityInfo getEntity() {
			EntityInfo link = null;
			Vector tmp = new Vector(downLinkVct); // add 'D' links
			tmp.addAll(upLinkVct);				// add 'U' links
			if (tmp.size()>0){
				link = (EntityInfo)tmp.firstElement();
				if (link!=null && !link.eclass.equals("E")){ // could be R to R.. so check again
					tmp.clear();
					tmp.addAll(link.downLinkVct);				// add children 'D' links
					tmp.addAll(link.upLinkVct);				// add parents 'U' links
					if (tmp.size()>0) {
						link = (EntityInfo)tmp.firstElement();
					}
				}
			}

			if (eclass.equals("E")){
				emitWarning("Entity needed but this is not a relator!! "+this);
			}

			tmp.clear();

			return link;
		}

		/********************************************************************************
		* Return list of COUNTRYLIST attribute values that match the country filter, current or prior
		*/
		Vector getMatchingCountries() {
			Vector matched = new Vector(1);
			// check attr for COUNTRYLIST
			for (int i=0; i<attrVct.size(); i++) {
				SortedAttr sa = (SortedAttr)attrVct.elementAt(i);
				if (sa.getCode().equals("COUNTRYLIST")){
					if (ctryList.contains(sa.getCurValue())) {
						matched.add(sa.getCurValue());
					}else if (ctryList.contains(sa.getPrevValue())) {
						matched.add(sa.getPrevValue());
					}
				}
			}

			return matched;
		}

		/********************************************************************************
		* This entity was overridden by a match found linked to LSEOBUNDLE
		*/
		void setOverridden() { isoverridden = true; }

		/********************************************************************************
		* check for root entity
		*/
		private boolean isRoot() {
			return ("Root".equals(direction));
		}

		/********************************************************************************
		* must check for country list on "MM" "IMG" "FB" "WARR" if country filter is specified
		*/
		private boolean checkCtry() {
			boolean ok = false;
			if (eclass.equals("E") &&   // is Entity class
				(type.equals("MM") || type.equals("IMG") || type.equals("FB") || type.equals("WARR"))) {
				// check attr for COUNTRYLIST
				for (int i=0; i<attrVct.size(); i++) {
					SortedAttr sa = (SortedAttr)attrVct.elementAt(i);
					if (sa.getCode().equals("COUNTRYLIST")){
						// look for match on country filter using current and prior values
						if (ctryList.contains(sa.getCurValue()) ||
							ctryList.contains(sa.getPrevValue())) {
							ok = true;  // one match is sufficient
							break;
						}
					}
				}
			}else{  // not one of the entity types to verify
				ok = true;
			}

			return ok;
		}

		/********************************************************************************
		* get column headers for attributes
		*/
		private String getColHeads()
		{
			StringBuffer sb = new StringBuffer();
			sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE);
			sb.append("<th style=\"text-align: center\" id=\"chgtype"+uniqueid+"_"+displayCnt+"\">"+propertiesList[0]+"</th>"+NEWLINE);//Change Type
			sb.append("<th style=\"text-align: center\" id=\"attr"+uniqueid+"_"+displayCnt+"\">"+propertiesList[1]+"</th>"+NEWLINE);//Attribute
			sb.append("<th style=\"text-align: center\" id=\"prev"+uniqueid+"_"+displayCnt+"\">"+propertiesList[2]+"</th>"+NEWLINE);//Previous Value
			sb.append("<th style=\"text-align: center\" id=\"curr"+uniqueid+"_"+displayCnt+"\">"+propertiesList[3]+"</th>"+NEWLINE);//Current Value
			sb.append("<th style=\"text-align: center\" id=\"dateChgd"+uniqueid+"_"+displayCnt+"\">"+propertiesList[4]+"</th>"+NEWLINE);//Date Changed
			sb.append("<th style=\"text-align: center\" id=\"user"+uniqueid+"_"+displayCnt+"\">"+propertiesList[5]+"</th></tr>"+NEWLINE);//Changed By
	    	return sb.toString();
		}

		/********************************************************************************
		* if this is the root, generate the html that would be used if no changes are found
		*/
		String getNoChgsHtml() {
			String html = null;

			if (isRoot()) {
				StringBuffer sb = new StringBuffer();
				// output each entity its own table to improve performance, IE can render right away
				sb.append("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"1\" class=\"basic-table\""+
					" summary=\"Structure and attribute changes\">"+NEWLINE);
				sb.append("<colgroup width=\"8%\"/>"+NEWLINE); // <!-- change type -->
      			sb.append("<colgroup width=\"20%\" span=\"3\"/>"+NEWLINE);// <!-- attribute, prev value and current value -->
      			sb.append("<colgroup width=\"15%\"/>"+NEWLINE);// <!-- date changed -->
      			sb.append("<colgroup width=\"17%\"/>"+NEWLINE); // <!-- changed by -->
				sb.append("<tr "+PokUtils.getTableHeaderRowCSS()+" title=\"(root) "+key+"\">"+NEWLINE);
				sb.append("<th colspan=\"6\" id=\""+key+"_"+uniqueid+"\">"+description+": ("+curCountStr+
						") "+navNameSb.toString()+"</th></tr>"+NEWLINE);
				sb.append(getColHeads());
				sb.append("<tr class=\"odd\">"+NEWLINE);
				sb.append("<td colspan=\"6\" headers=\"chgtype"+uniqueid+"_"+displayCnt+
					" attr"+uniqueid+"_"+displayCnt+" prev"+uniqueid+"_"+displayCnt+" curr"+uniqueid+"_"+displayCnt+
					" dateChgd"+uniqueid+"_"+displayCnt+" user"+uniqueid+"_"+displayCnt+"\">");
				sb.append(getBundleString("Text_NoDataFound")+"</td></tr></table>"+NEWLINE);
				html = sb.toString();
			}

			return html;
		}

		/********************************************************************************
		* check for an attribute change
		*/
		private boolean isAttrChanged() {
			boolean chgd = false;
			if (showAll) {
				chgd = true;
			}else{
				// check attr status
				for (int i=0; i<attrVct.size(); i++) {
					SortedAttr sa = (SortedAttr)attrVct.elementAt(i);
					chgd = sa.isChanged() || chgd;
				}
			}
			return chgd;
		}

		/********************************************************************************
		* If a relator attribute has been changed, but the child of that relator has not,
		* both the relator AND the child entity of that relator need to be displayed.
		* Must do this check because the child or its attributes may not have changed
		*/
		private boolean mustDisplay() {
			boolean display = false;
			EntityInfo relator = parentElement;
			while(relator !=null && relator.eclass.equals("R")){
				if(relator.isChanged()){
					display=true;
					break;
				}
				relator = relator.parentElement;
			}

			return display;
		}

		/********************************************************************************
		* should this entity/relator be displayed? if it is an entity, must go back
		* and check relators to see if they have changed attr.  The relators are output
		* after entities (per design requirement, not xml structure) so need to look at
		* them before deciding to not output an entity
		*/
		boolean isChanged() {
			boolean chgd = false;
			if (showAll) {  // always display the root
				chgd = true;
			}else{
				// Do not output Associations at all
    			if (eclass.equals("A")){
					chgd=false;
				}
    			// only output relators if they have changed attributes!
    			else if (eclass.equals("R")){
					chgd = isAttrChanged();
				}else {  // must be E class
					chgd = !ENTITY_UNC.equals(status); // check entity status

					// check attr status
					chgd = chgd || isAttrChanged();

					// if ctrylist is specified, check for type and that COUNTRYLIST attr has one of these ctry
					if (ctryList != null){
						// if no match, return false so this isn't included in output
						if (!checkCtry()) {
							try{
								emitln("<!-- "+key+" did not meet country filter -->");
							}catch(Exception ignore){
								System.out.println(ignore);  // jtest req
							}
							chgd=false;
						}
					}
					// If a relator attribute has been changed, but the child of that relator has not,
					// both the relator AND the child entity of that relator need to be displayed.
					if (mustDisplay()){
						chgd=true;
					}
				}
			}
			return chgd;
		}

		/********************************************************************************
		* check entire structure for any changes (that will be displayed)
		*/
		private boolean hasChanges() {
			boolean chgsFnd = isChanged();
			// look at links one change is enough
			if (!chgsFnd){
				for(int i=0; i<upLinkVct.size(); i++){
					EntityInfo ei = (EntityInfo)upLinkVct.elementAt(i);
					chgsFnd =  chgsFnd || ei.hasChanges();
				}
			}

			if (!chgsFnd){
				for(int i=0; i<downLinkVct.size(); i++) {
					EntityInfo ei = (EntityInfo)downLinkVct.elementAt(i);
					chgsFnd =  chgsFnd || ei.hasChanges();
				}
			}
			return chgsFnd;
		}

		/********************************************************************************
		* output the html for this root and links to the output stream
		*/
		void outputHtml() throws SAXException {
			boolean chgsFnd = hasChanges();
			if (!chgsFnd) {
				String nochgsFndStr = getNoChgsHtml();
				emitln(nochgsFndStr);
			}else {
				// does root have changes?
				if (!isChanged()) {	// always need root info
					emitln("<!-- "+key+" depth:"+(depth-1)+" level:"+(level-1)+"-->");
					// output each entity its own table to improve performance, IE can render right away
					emitln("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"1\" class=\"basic-table\""+
						" summary=\"Structure and attribute changes\">");

					emitln("<tr "+PokUtils.getTableHeaderRowCSS()+" title=\"(root) "+key+"\">");
					emitln("<td colspan=\"6\"><b>"+description+": ("+curCountStr+
							") "+navNameSb.toString()+"</b></td></tr>");
				}

				outputStructureHtml();
			}
		}
		/********************************************************************************
		* output the html for this entity and its linked structure to the output stream
		*/
		private void outputStructureHtml() throws SAXException
		{
			// Output the entity, attributes and then relator.  Parents and children are then displayed
			// output this entity
			// a relator may be a leaf, so if it has no links, put it out now
			if (eclass.equals("E") ||
				(eclass.equals("R") && upLinkVct.size()==0 && downLinkVct.size()==0))
			{
				EntityInfo relator = parentElement;

				outputEntityHtml();

				// XML has some structure errors, output a warning if <entity><entity>.. is found
				if (eclass.equals("E") && relator !=null && relator.eclass.equals("E")) {
					emitWarning("Parent element for entity key: "+key+" class: "+eclass+
							" is not a relator!! relator.key: "+relator.key+" class: "+relator.eclass);
				}
				// output the relator and any relator that is linked to it
				// associations are not displayed
				while(relator !=null && relator.eclass.equals("R")){
					relator.outputEntityHtml();
					relator = relator.parentElement;
				}
			}

			// Parents and children are then displayed
		    // If an entity is deleted, it's attributes SHOULD be displayed, but it's child entities do NOT need to be displayed
            if (!status.equals(ENTITY_DEL)) {
				Collections.sort(upLinkVct);  // sort the parents
				for(int i=0; i<upLinkVct.size(); i++){
					EntityInfo ei = (EntityInfo)upLinkVct.elementAt(i);
					ei.outputStructureHtml();
				}

				Collections.sort(downLinkVct);  // sort the children
				for(int i=0; i<downLinkVct.size(); i++) {
					EntityInfo ei = (EntityInfo)downLinkVct.elementAt(i);
					ei.outputStructureHtml();
				}
		    }
		}

		/********************************************************************************
		* output the html for this entity to the output stream
		*/
		private void outputEntityHtml() throws SAXException
		{
			if (!isoverridden && isChanged()){
				String uniqueStr = key+"_"+parentkey+"_"+uniqueid+"_"+displayCnt;
				int lineCnt = 0;
				displayCnt++;
				emitln("<!-- "+key+" depth:"+(depth-1)+" level:"+(level-1)+" parentkey:"+parentkey+
					" xmlparent:"+(parentElement!=null?parentElement.key:"")+"-->");
				// output each entity its own table to improve performance, IE can render right away
				emitln("<table width=\"100%\" cellpadding=\"0\" cellspacing=\"1\" class=\"basic-table\""+
					" summary=\"Structure and attribute changes\">");
				emitln("<colgroup width=\"8%\"/>"); // <!-- change type -->
				emitln("<colgroup width=\"20%\" span=\"3\"/>");// <!-- attribute, prev value and current value -->
				emitln("<colgroup width=\"15%\"/>");// <!-- date changed -->
				emitln("<colgroup width=\"17%\"/>"); // <!-- changed by -->

				if(isRoot()){  // output the root
					emitln("<tr "+PokUtils.getTableHeaderRowCSS()+" title=\"(root) "+key+"\">");
					emitln("<th colspan=\"6\" id=\""+uniqueStr+"\">"+description+": ("+curCountStr+
							") "+navNameSb.toString()+"</th></tr>");
				}else{
					emitln("<tr "+PokUtils.getRowLevelCSS(level)+" title=\"("+(level-1)+") "+getKeys()+"\" >");

					if(!status.equals(ENTITY_UNC)){
						// do it like this to meet web accessibility requirements of th-id and td-headers attributes
						emitln("<th id=\""+uniqueStr+"\">"+status+"</th>");
						emit("<td colspan=\"5\" headers=\""+uniqueStr+"\"><b>"+description+": "+
							navNameSb.toString());
						if("R".equals(eclass)){
							emit(" (Relator)");
						}
						emitln("</b></td>");
					}else{
						if (isAttrChanged()) {
							emit("<th colspan=\"6\" id=\""+uniqueStr+"\">");
							emit(description+": "+navNameSb.toString());
							if("R".equals(eclass)){
								emit(" (Relator)");
							}
							emitln("</th>");
						}else {
							// do it like this to meet web accessibility requirements of th-id and td-headers attributes
							emit("<td colspan=\"6\"><b>");
							emit(description+": "+navNameSb.toString());
							if("R".equals(eclass)){
								emit(" (Relator)");
							}
							emitln("</b></td>");
						}
					}
					emitln("</tr>");
				}
				// and row headings in each table if any attributes were changed
				if (isAttrChanged()) {
					emit(getColHeads());
				}

				Collections.sort(attrVct);  // sort the attributes

				// output attributes
				for (int i=0; i<attrVct.size(); i++) {
					SortedAttr sa = (SortedAttr)attrVct.elementAt(i);
					if (sa.isChanged()) {
						sa.outputHtml(lineCnt++, uniqueid+"_"+displayCnt, uniqueStr);
					}
				}

				emitln("</table>");
			}
		}
		
		String getEID() { return eid; }

		/********************************************************************************
		* release memory
		*/
		void dereference() {
			parentElement = null;
			description=null;
			eid=null;
			type=null;
			eclass=null;
			direction=null;
			for (int i=0; i<attrVct.size(); i++) {
				SortedAttr sa = (SortedAttr)attrVct.elementAt(i);
				sa.dereference();
			}
			attrVct.clear();
			attrVct = null;
			parentkey=null;
			key=null;
			for(int i=0; i<downLinkVct.size(); i++) {
				EntityInfo ei = (EntityInfo)downLinkVct.elementAt(i);
				ei.dereference();
			}
			downLinkVct.clear();
			for(int i=0; i<upLinkVct.size(); i++) {
				EntityInfo ei = (EntityInfo)upLinkVct.elementAt(i);
				ei.dereference();
			}
			upLinkVct.clear();
		}
		/********************************************************************************
		* used by Collections.sort()
		* changed the sort to look for E.. but it expects relator then entity or relator relator then entity
		* problem is that a relator in pdh can go to more than one thing.. model<-ps<-feature and wwseo->wsps->ps, same ps
		* so how to find correct entity to sort on.. or to display?  xml probably returns these at different
		* levels, so this might not be a problem.. but the same ps will be displayed more than once
		*@param o Object
		*@return int
		*/
		public int compareTo(Object o)
		{
			EntityInfo sma = (EntityInfo)o;
			EntityInfo curEI = this;
			/* DOM sorted on relator.. so do that for now to maintain the same output
			// sort description of the Entity, not the Relator
			// assumption is that an R or and A can only have 1 link, it may be U or D
			/*if (!curEI.eclass.equals("E")) {
				curEI = curEI.getEntity();
				if (curEI==null){ // something wrong.. so just use this
					curEI=this;
				}
			}
			if (!sma.eclass.equals("E")) {
				sma = sma.getEntity();
				if (sma==null){ // something wrong.. so just use (EntityInfo)o;
					sma=(EntityInfo)o;
				}
			}*/

			return (curEI.description.compareTo(sma.description));
		}

		/********************************************************************************
		* display this entity and structure for debug
		* @param indent
		* @return String
		*/
		public String display(String indent)
		{
			StringBuffer sb = new StringBuffer(this+NEWLINE);

			for(int i=0; i<upLinkVct.size(); i++){
				sb.append(indent+key+" p["+i+"]= "+((EntityInfo)upLinkVct.elementAt(i)).display("  "+indent));
			}

			for(int i=0; i<downLinkVct.size(); i++) {
				sb.append(indent+key+" c["+i+"]= "+((EntityInfo)downLinkVct.elementAt(i)).display("  "+indent));
			}

			return sb.toString();
		}
		/********************************************************************************
		* display this entity for debug
		*@return String
		*/
		public String toString()
		{
			String msg = ("key: "+key+" class: "+eclass+" dir: "+direction+" status: "+status+
				" #uplink: "+upLinkVct.size()+
				" #dnlink: "+downLinkVct.size()+" parentkey: "+parentkey+
				" xmlparent:"+(parentElement!=null?parentElement.key:""));
			return msg;
		}
	}

	/*************************************************************************************
	* This class is used to sort output based on description
	* <attribute nav = "1" description = "Feature Code" shortdescription ="FC" parentkey="SWFEATURE12068" code="FEATURECODE" type="T" changedatetime="2005-08-01 18:10:05.109561" changeperson="POWERUSER - sgv30b@us.ibm.com" status="Unchanged">
	*/
	private class SortedAttr implements Comparable
	{
		private String description, code, type, changedatetime, changeperson, status;
		private String prevVal="&nbsp;", curVal="&nbsp;", navVal=null;

		/********************************************************************************
		* display this attribute for debug
		*@return String
		*/
		public String toString()
		{
			String msg = ("code: "+code+" type: "+type+" status: "+status);
			return msg;
		}

		/********************************************************************************
		* Constructor
		* @param attrs  Attributes for this attribute element
		*/
		SortedAttr(Attributes attrs) {
			for (int i = 0; i < attrs.getLength(); i++) {
				String aName = attrs.getLocalName(i); // Attr name
				String value = attrs.getValue(i);
				if ("".equals(aName)) {
					aName = attrs.getQName(i);
				}
				if (aName.equals("description")) {
					description = value;
				}else if (aName.equals("code")) {
					code = value;
				}else if (aName.equals("type")) {
					type = value;
				}else if (aName.equals("changedatetime")) {
					changedatetime = value;
				}else if (aName.equals("changeperson")) {
					changeperson = value;
				}else if (aName.equals("status")) {
					status = value;
				}else if (aName.equals("nav")) {
					navVal = value;
				}
			}
		}
		boolean isABR() { return "A".equals(type);}
		String getCode() { return code;}
		String getCurValue() { return curVal;}
		String getPrevValue() { return prevVal;}
		boolean isNav() { return navVal!=null;}
		/********************************************************************************
		* Should this be displayed?
		*/
		boolean isChanged() {
			boolean chgd = false;
			if (showAll) {
				chgd = true;
			}else{
				if (!isABR()) {  // don't want A type attributes, needed to create one to capture
					// prev and cur values from SAX parser
					chgd = !ATTR_UNC.equals(status);
				}
			}
			return chgd;
		}
		/********************************************************************************
		* output to the output stream
		* If the entity has a multi flag attribute, all changed flag values should be displayed.
		* Each changed flag value should be displayed in its own row under the entity.  This is controlled by xml coming in.
		*/
		void outputHtml(int lineCnt, String uid, String uniqueStr) throws SAXException
		{
			emitln("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">");
			emitln("<td headers=\"chgtype"+uid+" "+uniqueStr+"\">"+status+"</td>");
			emitln("<td headers=\"attr"+uid+" "+uniqueStr+"\">"+description+"</td>");
			emitln("<td headers=\"prev"+uid+" "+uniqueStr+"\">"+prevVal+"</td>");
			emitln("<td headers=\"curr"+uid+" "+uniqueStr+"\">"+curVal+"</td>");
			emitln("<td headers=\"dateChgd"+uid+" "+uniqueStr+"\">"+changedatetime+"</td>");
			emitln("<td headers=\"user"+uid+" "+uniqueStr+"\">"+changeperson+"</td></tr>");
		}

		/********************************************************************************
		* A <currentvalue> was just parsed
		*/
		void setCurValue(String d) {
			if(d.length()>0) {  // don't replace nbsp with empty string
				curVal=d;
			}
		}
		/********************************************************************************
		* A <priorvalue> was just parsed
		*/
		void setPrevValue(String d) {
			if(d.length()>0) {  // don't replace nbsp with empty string
				prevVal=d;
			}
		}
		/********************************************************************************
		* Release memory
		*/
		void dereference() {
			description=null;
			code=null;
			type=null;
			changedatetime=null;
			changeperson=null;
			status=null;
			prevVal=null;
			curVal=null;
		}
		/********************************************************************************
		* used by Collections.sort()
		*@param o Object
		*@return int
		*/
		public int compareTo(Object o)
		{
			SortedAttr sma = (SortedAttr)o;
			// sort description
			return (description.toLowerCase().compareTo(sma.description.toLowerCase()));
		}
	}


//=============================================================================
// used for testing on desktop with xml file
//=============================================================================
    private static StringBuffer readXML(String fn) {
		StringBuffer sb = new StringBuffer();
		BufferedReader rdr =null;
		InputStream is = null;
		FileInputStream fis = null;
		String userDir = System.getProperty("user.dir");
		try{
			// if just a file name is specified, look in current directory
			if (fn.indexOf(System.getProperty("file.separator"))==-1){
				fn = userDir+System.getProperty("file.separator")+fn;
			}
			fis = new FileInputStream(fn);
			is = new BufferedInputStream(fis);
		}
		catch(FileNotFoundException fe)
		{
			System.err.println(fe.getMessage());
			System.exit(0);
		}

		try {
			String s=null;
			// Note: character encoding used with inputstreams can not
			// be determined by the inputstream alone.  If the encoding is
			// not specified, it assumes the input is in the default encoding
			// of the platform.
			rdr = new BufferedReader(new InputStreamReader(is, "UTF8"));
			// append lines until done
			while((s=rdr.readLine()) !=null){
				sb.append(s+NEWLINE);  // duplicate the original string
			}
		}
		catch(Exception t)
		{
			System.err.println("Error while loading document from stream: " + t);
			t.printStackTrace(System.err);
		}
		finally{
			if (rdr!=null){
				try{
					rdr.close();
				}catch(Exception x){
					System.out.println(x.getMessage()); // jtest req
				}
			}
			if (is!=null){
				try{
					is.close();
				}catch(Exception x){
					System.out.println(x.getMessage()); // jtest req
				}
			}
			if (fis!=null){
				try{
					fis.close();
				}catch(Exception x){
					System.out.println(x.getMessage()); // jtest req
				}
			}
		}

		return sb;
	}

	/*************************************************************************************
	* Main used for debug testing
	*@param args String[] can pass in xml file name, must be in current dir or fully qualified name
	*/
    public static void main(String args[])
    {
		long startTime = System.currentTimeMillis();
		long lastTime =startTime;
		long runTime = 0;
		Writer out = null;
        try {
			SGSaxHandler handler;
			String fn = "atmfchg.xml";  // get this from a arg if specified
	        StringBuffer sb;
			// Set up output stream
			//ByteArrayOutputStream bs = new ByteArrayOutputStream();
            if (args.length>0)
            {
                fn = args[0];
            }

			sb = readXML(fn);

			runTime = System.currentTimeMillis();
			System.err.println("read file time: "+(runTime-lastTime));

			//out = new OutputStreamWriter(bs, "UTF8");
	        out = new FileWriter("saxtest.htm");

	        handler = new SGSaxHandler(out,null);

	        // just for debug of structure
	        //handler.setShowAll(true);
			lastTime = System.currentTimeMillis();

	        handler.renderXML(sb,"1 of 1",""); //remove ctrylist for MODEL or WWSEO root

			runTime = System.currentTimeMillis();
			System.err.println("transform time: "+(runTime-lastTime));

	        //System.out.println(bs.toString());
	        handler.dereference();
        } catch (Throwable t) {
            t.printStackTrace();
        }finally{
			try{
				if (out !=null) {
					out.close();
				}
			}catch(IOException ioe){
				System.err.println(ioe);
			}
		}
        System.exit(0);
    }
}
