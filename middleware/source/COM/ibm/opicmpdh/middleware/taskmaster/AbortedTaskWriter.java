//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2013  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.opicmpdh.middleware.taskmaster;

/***********
 * Create a report for an ABR that was aborted because taskmaster terminated and left the ABR in
 * inprocess state.  The ABR was not read-only and could not be restarted.
 * This is a copy of COM.ibm.eannounce.abr.util.EACustom to get AHE report format
 */
//$Log: AbortedTaskWriter.java,v $
//Revision 1.3  2014/01/13 13:38:06  wendy
//migration to V17
//
//Revision 1.2  2013/05/24 20:06:47  wendy
//change user msg
//
//Revision 1.1  2013/05/24 14:12:50  wendy
//taskmaster perf enhancements include:
// - Made idler classes case sensitive - can have 52 now
// - Added callback to attempt to run another ABR as soon as one completes.
// - Replaced string lookups with hash set
// - if ABR is inprocess status when taskmaster starts, attempt to run the ABR again if readonly, if not readonly then fail it in the pdh and notify user that ABR was aborted
// 
public class AbortedTaskWriter {
    /* A utility class only contains static methods and static variables. Since an
    implicit default constructor is "public" and a utility class is not designed to
    be instantiated, the "public" default constructor of a utility class should be
    declared "private".  */
    private AbortedTaskWriter() {}

    private static final char[] FOOL_JTEST = {'\n'};
    /**  New line separator, use Unicode separator because rest of report is unicode */
    public static final String NEWLINE = new String(FOOL_JTEST);

	/**********************************************************************************
	 * build aborted report for ABR
	 * @param m_at 
	 */
	public static void buildAbortedReport(AbstractTask m_at)
	{
		String title = m_at.getABRItem().getEntityType()+":"+m_at.getABRItem().getEntityID()+" "+
				m_at.getABRItem().getABRDescription(); 

		m_at.println(AbortedTaskWriter.getHeaderBody(m_at.getABRItem().getABRCode(), title));

		String clsnm = m_at.getClass().getName().substring(m_at.getClass().getName().lastIndexOf(".") + 1);
		m_at.setDGTitle(title);
		m_at.setDGRptName("ABORTED:"+clsnm);
		m_at.setDGRptClass(m_at.getABRItem().getABRCode());

		m_at.println("<p class=\"ibm-intro ibm-alternate-three\"><em>"+m_at.getABRItem().getABRCode()+": "+title+"</em></p>");
		m_at.println("<table>");
		m_at.println("<tr><th>Userid: </th><td>"+m_at.getProfile().getOPName()+"</td></tr>");
		m_at.println("<tr><th>Role: </th><td>"+m_at.getProfile().getRoleDescription()+"</td></tr>");
		m_at.println("<tr><th>Workgroup: </th><td>"+ m_at.getProfile().getWGName()+"</td></tr>");
		m_at.println("<tr><th>Date: </th><td>"+m_at.getProfile().getValOn()+"</td></tr>");
		m_at.println("<tr><th>Description: </th><td>"+m_at.getABRItem().getABRDescription()+"</td></tr></table>");

		m_at.println("<h3><span style=\"color:#c00; font-weight:bold;\">Error: ABR "+m_at.getABRItem().getABRCode()+
				" for "+m_at.getABRItem().getEntityType()+" EntityId: "+
				m_at.getABRItem().getEntityID()+" was aborted because taskmaster terminated</span></h3>");	
		
		m_at.println(AbortedTaskWriter.getTOUDiv());
		m_at.printDGSubmitString();
		m_at.println("\n</body>\n</html>");

	}
    /**********************************************************************************
     *  Get AHE compliant header and body
     *
     *@return    java.lang.String
     */
    public static String getHeaderBody(String desc, String title){
    	  StringBuffer sb = new StringBuffer(getDocTypeHtml()+"<head>");
    	  sb.append(getMetaTags(desc) + NEWLINE);
    	  sb.append(getCSS() + NEWLINE);
    	  sb.append(getTitle(title) + NEWLINE);
    	  sb.append("</head>" + NEWLINE + "<body id=\"ibm-com\">");
    	  sb.append(getMastheadDiv() + NEWLINE);
    	  return sb.toString();
    }
    /**********************************************************************************
    *  Get AHE compliant stylesheets to make it look like the report from the BUI
    *
    *@return    java.lang.String
    */
    public static String getCSS()
    {
    	/*v17
        StringBuffer sb = new StringBuffer();
        sb.append("<style type=\"text/css\" media=\"all\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/screen.css\");"+NEWLINE);
        sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/interior.css\");"+NEWLINE);
        sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/popup-window.css\");"+NEWLINE);
        sb.append("@import url(\"http://w3.ibm.com/ui/v8/css/tables.css\");"+NEWLINE);

        sb.append("-->"+NEWLINE);
        sb.append("</style>"+NEWLINE);
		sb.append("<!-- print stylesheet MUST follow imported stylesheets -->"+NEWLINE);
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" media=\"print\" href=\"http://w3.ibm.com/ui/v8/css/print.css\" />"+NEWLINE);
		*/
        return "";//v17sb.toString();
    }
    /**********************************************************************************
    * Get AHE compliant DOCTYPE and HTML
    *
    *@return    java.lang.String
    */
    public static String getDocTypeHtml()
    {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "+
            "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"+NEWLINE+
            "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">";
    }

    /**********************************************************************************
    *  Get AHE compliant title tag
    *
    *@param title String
    *@return    java.lang.String
    */
    public static String getTitle(String title)
    {
        return "<title>EACM | "+title+"</title>";
    }

    /**********************************************************************************
    *  Get AHE compliant meta tags
    *
    *@param description String with description for meta tag
    *@return    java.lang.String
    */
    public static String getMetaTags(String description)
    {
        StringBuffer sb = new StringBuffer();
        java.text.SimpleDateFormat oSimpleDateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
        sb.append(NEWLINE+"<base href=\"http://\" />"+NEWLINE);
        sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+NEWLINE);
        sb.append("<link rel=\"schema.DC\" href=\"http://purl.org/DC/elements/1.0/\"/>"+NEWLINE);
        sb.append("<link rel=\"SHORTCUT ICON\" href=\"http://w3.ibm.com/favicon.ico\"/>"+NEWLINE);
        sb.append("<meta name=\"description\" content=\""+description+"\" />"+NEWLINE);
        sb.append("<meta name=\"keywords\" content=\"EACM\" />"+NEWLINE);
        sb.append("<meta name=\"owner\" content=\"opicma@us.ibm.com\" />"+NEWLINE);
        sb.append("<meta name=\"robots\" content=\"noindex,nofollow\" />"+NEWLINE);
        sb.append("<meta name=\"security\" content=\"internal use only\" />"+NEWLINE);
        sb.append("<meta name=\"source\" content=\"Template Generator\" />"+NEWLINE);
        sb.append("<meta name=\"IBM.Country\" content=\"ZZ\" />"+NEWLINE);
        sb.append("<meta name=\"dc.date\" scheme=\"iso8601\" content=\""+
            oSimpleDateFormat.format(new java.util.Date())+"\" />"+NEWLINE);
        sb.append("<meta name=\"dc.language\" scheme=\"rfc1766\" content=\"en-US\" />"+NEWLINE);
        sb.append("<meta name=\"dc.rights\" content=\"Copyright (c) 2000,2014 by IBM Corporation\"  />"+NEWLINE);
        sb.append("<meta name=\"feedback\" content=\"opicma@us.ibm.com\" />"+NEWLINE);
        sb.append("<link href=\"//1.w3.s81c.com/common/v17/css/w3.css\" rel=\"stylesheet\" title=\"w3\" type=\"text/css\" />"+NEWLINE);
        sb.append("<script src=\"//1.w3.s81c.com/common/js/dojo/w3.js\" type=\"text/javascript\">//</script>"+NEWLINE);
        
        return sb.toString();
    }
    /**********************************************************************************
    *  Get AHE compliant masthead and division tags
    *
    *@return    java.lang.String
    */
    public static String getMastheadDiv()
    {
        StringBuffer sb = new StringBuffer();
        /*
        v17
        sb.append("<!-- start popup masthead //////////////////////////////////////////// -->"+NEWLINE);
        sb.append("<div id=\"popup-masthead\">"+NEWLINE);
        sb.append("    <img id=\"popup-w3-sitemark\" src=\"http://w3.ibm.com/ui/v8/images/id-w3-sitemark-small.gif\" alt=\"\" width=\"182\" height=\"26\" />"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("<!-- stop popup masthead //////////////////////////////////////////// -->"+NEWLINE);
        sb.append("<!-- start content //////////////////////////////////////////// -->"+NEWLINE);
        sb.append("<div id=\"content\">"+NEWLINE);
        sb.append("    <!-- start main content -->"+NEWLINE);
        sb.append("    <div id=\"content-main\">"+NEWLINE);
        */
        
        sb.append("<div id=\"ibm-top\" class=\"ibm-popup\">"+NEWLINE);
        sb.append("<!-- MASTHEAD_BEGIN -->"+NEWLINE);
        sb.append("<div id=\"ibm-masthead\">"+NEWLINE);
        sb.append("<div id=\"ibm-mast-options\"><ul>"+NEWLINE);
        sb.append("<li id=\"ibm-home\"><a href=\"http://w3.ibm.com\">w3</a></li>"+NEWLINE);
        sb.append("<li id=\"ibm-title\">EACM | ABR</li>"+NEWLINE);
        sb.append("</ul></div>"+NEWLINE);
        sb.append("<div id=\"ibm-universal-nav\">"+NEWLINE);
        sb.append("<p id=\"ibm-site-title\"><em>site title</em></p>"+NEWLINE);
        sb.append("<ul id=\"ibm-menu-links\">"+NEWLINE);
        sb.append("<li><a href=\"http://w3.ibm.com/sitemap/us/en/\">Site map</a></li>"+NEWLINE);
        sb.append("</ul>"+NEWLINE);
        sb.append("<div id=\"ibm-search-module\">"+NEWLINE);	       
        sb.append("<form method=\"get\" action=\"http://w3.ibm.com/search/do/search\" id=\"ibm-search-form\">"+NEWLINE);
        sb.append("<p>"+NEWLINE);
        sb.append("<label for=\"q\"><span class=\"ibm-access\">Search</span></label>"+NEWLINE);
        sb.append("<input id=\"q\" maxlength=\"100\" name=\"qt\" type=\"text\" value=\"\" />"+NEWLINE);
        sb.append("<input name=\"v\" type=\"hidden\" value=\"17\"/>"+NEWLINE);
        sb.append("<input value=\"Submit\" class=\"ibm-btn-search\" id=\"ibm-search\" type=\"submit\"/>"+NEWLINE);
        sb.append("</p>"+NEWLINE);
        sb.append("</form>"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("<!-- MASTHEAD_END -->"+NEWLINE);
        sb.append("<!-- LEADSPACE_BEGIN -->"+NEWLINE);
        sb.append("<div id=\"ibm-leadspace-head\" class=\"ibm-alternate\">"+NEWLINE);
        sb.append("<div id=\"ibm-leadspace-body\">"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("<!-- LEADSPACE_END -->"+NEWLINE);
        sb.append("<div id=\"ibm-pcon\">"+NEWLINE);
        sb.append("<!-- CONTENT_BEGIN -->"+NEWLINE);
        sb.append("<div id=\"ibm-content\">"+NEWLINE);
        sb.append("<!-- CONTENT_BODY -->"+NEWLINE);
        sb.append("<div id=\"ibm-content-body\">"+NEWLINE);
        sb.append("<div id=\"ibm-content-main\">"+NEWLINE);
        return sb.toString();
    }
    /**********************************************************************************
    *  Get AHE footer, terms of use and close division tags
    *
    *@return    java.lang.String
    */
    public static String getTOUDiv()
    {

        StringBuffer sb = new StringBuffer();
        /* v17
        sb.append("<!-- start popup footer //////////////////////////////////////////// -->"+NEWLINE);
        sb.append("<div id=\"popup-footer\">"+NEWLINE);
        sb.append("  <div class=\"hrule-dots\">&nbsp;</div>"+NEWLINE);
        sb.append("    <div class=\"content\">"+NEWLINE);

        sb.append("      <div style=\"padding:0 1em 0.4em 34px; float:right;\">"+NEWLINE);
        sb.append("        <a class=\"popup-print-link\" style=\"float:none;\" href=\"javascript:print();\">"+
        	"Print</a><a style=\"padding:0 1em 0.4em 4px;\" href=\"javascript:close();\">Close Window</a>"+NEWLINE);
        sb.append("      </div>"+NEWLINE);
        sb.append("    </div>"+NEWLINE);
        sb.append("  <div style=\"clear:both;\">&nbsp;</div>"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("<!-- stop popup footer //////////////////////////////////////////// -->"+NEWLINE);

        sb.append("<script language=\"JavaScript\" type=\"text/javaScript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("    function openTOU() {"+NEWLINE);
        sb.append("    window.open(\"http://w3.ibm.com/w3/info_terms_of_use.html\", \"TOU\", \"dependent,width=800,height=600,screenX=100,screenY=100,left=100,top=100,titlebar,scrollbars,status,resizable\");"+NEWLINE);
        sb.append("    }"+NEWLINE);
        sb.append("//-->"+NEWLINE);
        sb.append("</script>"+NEWLINE);
        sb.append("        <p class=\"terms\">"+NEWLINE);
        sb.append("            <a href=\"javascript:openTOU();\">Terms of use</a>"+NEWLINE);
        sb.append("        </p>"+NEWLINE);
        sb.append("    </div>"+NEWLINE);
        sb.append("    <!-- stop main content -->"+NEWLINE);
        sb.append("</div>"+NEWLINE);
        sb.append("<!-- stop content //////////////////////////////////////////// -->"+NEWLINE);
        */
       	sb.append("<div id=\"popup-footer\">"+NEWLINE);
    	sb.append("  <div class=\"hrule-dots\">&nbsp;</div>"+NEWLINE);
    	sb.append("    <div class=\"content\">"+NEWLINE);
    	sb.append("      <script type=\"text/javascript\" language=\"JavaScript\">"+NEWLINE);
    	sb.append("       <!--"+NEWLINE);
    	sb.append("       function printReport() { top.window.print();}"+NEWLINE);
    	sb.append("       //-->"+NEWLINE);
    	sb.append("      </script>"+NEWLINE);
    	sb.append("	     <div style=\"padding:0 1em 0.4em 34px; float:right;\">"+NEWLINE);
    	sb.append("	       <a class=\"ibm-print-link\" href=\"javascript:printReport();\">Print</a>"+NEWLINE);
    	sb.append("        <a style=\"padding:0 1em 0.4em 4px;\" href=\"javascript:close();\">Close Window</a>"+NEWLINE);
    	sb.append(" 	 </div>"+NEWLINE);
    	sb.append("    </div>"+NEWLINE);
    	sb.append("    <div style=\"clear:both;\">&nbsp;</div>"+NEWLINE);
    	sb.append("  </div>"+NEWLINE);
    	sb.append("</div>"+NEWLINE);
    	
    	sb.append("</div>"+NEWLINE);
    	sb.append("</div>"+NEWLINE);
    	sb.append("<!-- CONTENT_BODY_END -->"+NEWLINE);
    	
    	sb.append("<!-- NAVIGATION_BEGIN -->"+NEWLINE);
    	sb.append("<div id=\"ibm-navigation\">"+NEWLINE);
    	sb.append("<!-- hidden in popup-page -->"+NEWLINE);
    	sb.append("</div>"+NEWLINE);
    	sb.append("<!-- NAVIGATION_END -->"+NEWLINE);
    	sb.append("</div>"+NEWLINE);
    	sb.append("<div id=\"ibm-related-content\"></div>"+NEWLINE);
    	sb.append("</div>"+NEWLINE);
    	sb.append("<!-- CONTENT_END -->"+NEWLINE);
    	sb.append("<!-- FOOTER_BEGIN -->"+NEWLINE);
    	sb.append("<div id=\"ibm-footer-module\"></div>"+NEWLINE);
    	sb.append("<div id=\"ibm-footer\">"+NEWLINE);
    	sb.append("<h2 class=\"ibm-access\">Footer links</h2>"+NEWLINE);
    	sb.append("<ul>"+NEWLINE);
    	sb.append("<li><a href=\"http://w3.ibm.com/w3/info_terms_of_use.html\">Terms of use</a></li>"+NEWLINE);
    	sb.append("</ul>"+NEWLINE);
    	sb.append("</div>"+NEWLINE);
    	sb.append("<!-- FOOTER_END -->"+NEWLINE);
    	sb.append("<div id=\"ibm-metrics\">"+NEWLINE);
    	sb.append("<script src=\"//w3.ibm.com/w3webmetrics/js/ntpagetag.js\" type=\"text/javascript\">//</script>"+NEWLINE);
    	sb.append("</div>"+NEWLINE);

        return sb.toString();
    }
 }
