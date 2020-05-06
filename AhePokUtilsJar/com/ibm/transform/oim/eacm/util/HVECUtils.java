// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.util;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import javax.servlet.http.*;

/**********************************************************************************
* This class contains utility methods used by HVEC reports.
*
*/
// $Log: HVECUtils.java,v $
// Revision 1.19  2014/01/13 13:48:36  wendy
// migration to V17
//
// Revision 1.18  2008/10/30 23:56:04  wendy
// CI162, Webking and JAWS updates
//
// Revision 1.17  2007/06/26 19:32:07  wendy
// Look locally for static files
//
// Revision 1.16  2006/02/23 18:32:47  couto
// Fixed selected default value.
//
// Revision 1.15  2006/02/21 17:04:50  couto
// WebKing changes: putting script if between { }.
//
// Revision 1.14  2006/02/20 15:38:13  sergio
// Updated for HPR compliance
//
// Revision 1.13  2006/02/17 22:37:05  wendy
// Aligned labels and input fields for AHE
//
// Revision 1.12  2006/02/17 19:35:43  wendy
// Restore version 1.10 and remove http from img for AHE
//
// Revision 1.10  2006/01/27 12:41:41  couto
// Changed calendar control.
//
// Revision 1.9  2006/01/25 18:42:28  wendy
// AHE copyright
//
// Revision 1.8  2005/12/29 14:38:15  sergio
// DQA changes for the id attributes.
//
// Revision 1.7  2005/12/21 14:12:32  wendy
// WebKing requires 'layout' in table summary attribute for layout tables
//
// Revision 1.6  2005/12/18 02:21:54  wendy
// DQA changes for th id attributes and td headers attributes
//
// Revision 1.5  2005/11/15 17:11:20  couto
// Updating script files location.
//
// Revision 1.4  2005/10/06 19:35:26  wendy
// Add http to img src attribute
//
// Revision 1.3  2005/09/25 20:45:40  wendy
// Convert to AHE format
//
// Revision 1.2  2005/09/12 16:54:43  wendy
// Add .wss to servlet name
//
// Revision 1.1  2005/09/08 18:16:52  couto
// Init OIM3.0b
//
// Revision 1.7  2005/07/20 19:35:45  couto
// Closed some input tags.
//
// Revision 1.6  2005/07/08 17:49:40  couto
// WebKing fixes.
//
// Revision 1.5  2005/07/08 16:43:33  couto
// WebKing fixes.
//
// Revision 1.4  2005/07/07 19:33:14  couto
// WebKing fixes.
//
// Revision 1.3  2005/06/13 13:05:43  wendy
// jtest chgs
//
// Revision 1.2  2004/02/05 16:48:42  wendy
// CR0120043148 updates
//
// Revision 1.1.1.1  2004/01/26 17:40:02  chris
// Latest East Coast Source
//
// Revision 1.9  2003/11/10 20:04:02  stimpsow
// Added methods to get date form for DG
//
// Revision 1.8  2003/11/03 14:37:35  stimpsow
// Enhance date check for FB 52807:7F014F
//
// Revision 1.7  2003/07/15 15:15:27  stimpsow
// Corrected submit check when dates are not involved.
//
// Revision 1.6  2003/06/26 16:56:32  stimpsow
// Added using resource bundles.
//
// Revision 1.5  2003/06/18 16:08:49  stimpsow
// Added application name to form generation
//
// Revision 1.4  2003/06/12 14:04:42  stimpsow
// Added ability to suppress number of levels or suppress date selection.
//
// Revision 1.3  2003/06/11 14:23:59  stimpsow
// Enhanced date validation.
//
// Revision 1.2  2003/06/06 12:14:07  stimpsow
// Added number of levels to date collection form.
//
// Revision 1.1  2003/05/09 13:39:04  stimpsow
// Initial
//
public class HVECUtils
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2003, 2006  All Rights Reserved.";

    /* A utility class only contains static methods and static variables. Since an
    implicit default constructor is "public" and a utility class is not designed to
    be instantiated, the "public" default constructor of a utility class should be
    declared "private".  */
    private HVECUtils() {}

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.19 $";
    /** from date used with javascript */
    public static final String FROM_DATE_STR = "FROM_DATE";
    /** to date used with javascript */
    public static final String TO_DATE_STR = "TO_DATE";
    /** number of levels used with javascript */
    public static final String NUM_LEVELS_STR = "NUM_LEVELS";
    private static final int MAX_NUM_LEVELS=4;  // HVEC only wants to see a max of 4 levels
    private static final Hashtable LITERAL_TBL;
    static {
        LITERAL_TBL = new Hashtable();
        LITERAL_TBL.put("Error_FromDate","Please enter a valid 'From' date in YYYY-MM-DD format.");
        LITERAL_TBL.put("Error_ToDate","Please enter a valid 'To' date in YYYY-MM-DD format.");
        LITERAL_TBL.put("Error_DateRange","'From' must be before 'To'. Please enter valid dates in YYYY-MM-DD format.");
        LITERAL_TBL.put("Label_DateRange","Please specify a date range");
        LITERAL_TBL.put("Label_From","From");
        LITERAL_TBL.put("Label_To","To");
        LITERAL_TBL.put("Label_OK","OK");
        LITERAL_TBL.put("Label_Cancel","Cancel");
        LITERAL_TBL.put("Label_All","All");
        LITERAL_TBL.put("Label_NumLevels","How many levels of entities do you want to include in your report?");
    }
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /********************************************************************************
    * Build the form used to gather dates for change reports
    *
    *@param  request     HttpServletRequest with information to pass on
    *@param  appName     String with application name
    *@param  actionName  Name of jsp to invoke when form is submitted
    *@param  curTime     Date for initial display
    *@return String with javascript and form to collect date range and number of levels
    */
    public static String getDateRangeEntryForm(
		String staticContentUrl,
		HttpServletRequest request, String appName,
        String actionName,String curTime)
    {
        return getDateRangeEntryForm(staticContentUrl, request, appName, actionName, curTime, MAX_NUM_LEVELS);
    }
    /********************************************************************************
    * Build the form used to gather dates for change reports
    *
    *@param  request     HttpServletRequest with information to pass on
    *@param  appName     String with application name
    *@param  actionName  Name of jsp to invoke when form is submitted
    *@param  curTime     Date for initial display, null will prevent display
    *@param  numLevels   Maximum number of levels, 0 will prevent display
    *@return String with javascript and form to collect date range
    */
    public static String getDateRangeEntryForm(
		String staticContentUrl,
		HttpServletRequest request, String appName,
        String actionName, String curTime, int numLevels)
    {
        return getDateRangeEntryForm(staticContentUrl, request, appName,
            actionName, curTime, numLevels, null);
    }
    /********************************************************************************
    * Build the form used to gather dates for change reports
    *
    *@param  request     HttpServletRequest with information to pass on
    *@param  appName     String with application name
    *@param  url         Name of jsp to invoke when form is submitted
    *@param  curTime     Date for initial display, null will prevent display
    *@param  numLevels   Maximum number of levels, 0 will prevent display
    *@param  bundle      Resource bundle for literals, if null hashtable will be used
    *@return String with javascript and form to collect date range
    */
    public static String getDateRangeEntryForm(
		String staticContentUrl,
		HttpServletRequest request, String appName,
        String url, String curTime, int numLevels, ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        Enumeration e = request.getParameterNames();
        String actionName = "/"+appName+"/DG.wss"; // DG servlet as action

        if (curTime!=null)
        {
            // build javascript to validate dates
            sb.append(getDateValidationScript(bundle));

            // must output msg about javascript
            sb.append(PokUtils.getJavaScriptWarning());
        }

        if (request.getParameter(PokUtils.DG_INDICATOR)==null) {// not from DGServlet, from cmd line
            actionName = "/"+appName+url;
        }

        // build date entry form
        sb.append("<form method=\"post\" name=\"dateLevelForm\" action=\""+actionName+"\" ");
        if (curTime!=null) {
            sb.append("onsubmit=\"return checkInput(this."+FROM_DATE_STR+", this."+TO_DATE_STR+");\">");
        }
        else {
            sb.append(">"); }

        // output all request parameters as hidden fields
        while(e.hasMoreElements())
        {
            String name = (String)e.nextElement();
            String vals[] = request.getParameterValues(name);
            if(vals != null)  {
                for(int i = 0; i<vals.length; i++) {
                    sb.append("<input type=\"hidden\" name=\""+name+"\" value=\""+vals[i]+"\"/>"+NEWLINE); }
            }
        }

        if (curTime!=null) {
            sb.append("<p>"+PokUtils.REQUIRED_FLD_TXT+"<br /></p>"+NEWLINE);
        }

        sb.append("<table border=\"0\" summary=\"layout\">"+NEWLINE);
        if (curTime!=null)
        {
            sb.append("  <tr><td colspan=\"2\" style=\"vertical-align:top;\"  >");
            sb.append(getLiteral(bundle,"Label_DateRange")+":");
            sb.append("</td></tr>"+NEWLINE+
                "  <tr><td> <label for=\""+FROM_DATE_STR+"\">*"+
                getLiteral(bundle,"Label_From")+":</label></td>"+NEWLINE);
            sb.append("            <td style=\"vertical-align:top;\" >"+NEWLINE);
            sb.append("              <table summary=\"layout\">"+NEWLINE);
            sb.append("                <tr><td><input name=\""+FROM_DATE_STR+"\" id=\""+ FROM_DATE_STR +"\" type=\"text\" "+
                "size=\"12\" maxlength=\"10\" value=\""+curTime+"\"/>");
			sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document.dateLevelForm."+FROM_DATE_STR+"', document.dateLevelForm."+FROM_DATE_STR+".value);\">");
			//v17 sb.append("<img height=\"14\" width=\"13\" src=\"http:"+staticContentUrl+"images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
			sb.append("</a>");
            sb.append("</td><td>&nbsp;(yyyy-mm-dd)</td></tr>"+NEWLINE+
                "              </table>"+NEWLINE+
                "            </td></tr>"+NEWLINE);
            sb.append("        <tr><td><label for=\""+TO_DATE_STR+"\">*"+
                getLiteral(bundle,"Label_To")+":</label></td>"+NEWLINE);
            sb.append("            <td style=\"vertical-align:top;\" >"+NEWLINE+
                "              <table summary=\"layout\">"+NEWLINE);
            sb.append("                <tr><td><input name=\""+TO_DATE_STR+ "\" id=\""+ TO_DATE_STR + "\" type=\"text\" "+
                "size=\"12\" maxlength=\"10\" value=\""+curTime+"\"/>");
			sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document.dateLevelForm."+TO_DATE_STR+"', document.dateLevelForm."+TO_DATE_STR+".value);\">");
			//v17 sb.append("<img height=\"14\" width=\"13\" src=\"http:"+staticContentUrl+"images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
			sb.append("</a>");
            sb.append("</td><td>&nbsp;(yyyy-mm-dd)</td></tr>"+NEWLINE+
                "              </table>"+NEWLINE+
                "            </td></tr>"+NEWLINE);
        }
        // add level selection
        if (numLevels>0)
        {
            if (numLevels<MAX_NUM_LEVELS) { // HVEC always wants 4 levels, this allows jsp to set a higher number
                numLevels = MAX_NUM_LEVELS; }

            sb.append("   <tr><td><label for=\""+NUM_LEVELS_STR+"\">"+
                getLiteral(bundle,"Label_NumLevels")+"</label></td>"+NEWLINE);
            sb.append("             <td style=\"vertical-align:top;\" >"+NEWLINE+
                "               <select name=\""+NUM_LEVELS_STR+"\" id=\""+ NUM_LEVELS_STR +"\" onchange=\"dateLevelForm.okButton.focus();\">"+NEWLINE);
            for (int i=1; i<numLevels;i++) {
                sb.append("                <option value=\""+i+"\">&nbsp;"+i+"</option>"+NEWLINE); }
            sb.append("                <option value=\""+numLevels+"\" selected=\"selected\">&nbsp;"+getLiteral(bundle,"Label_All")+"</option>"+NEWLINE);
            sb.append("               </select>"+NEWLINE+"</td></tr>");
        }
        // add buttons
        sb.append("        <tr><td style=\"text-align:right;\" colspan=\"2\">"+NEWLINE+
            "             <table summary=\"layout\" cellspacing=\"3\">"+NEWLINE+
            "              <tr><td><span class=\"button-blue\"><input type=\"submit\" name=\"okButton\" value=\" "+
            getLiteral(bundle,"Label_OK")+" \"/></span></td>"+NEWLINE+
            "                  <td><span class=\"button-blue\"><input type=\"button\" name=\"cancelButton\" value=\""+
            getLiteral(bundle,"Label_Cancel")+"\" onclick=\"window.close();\"/>"+NEWLINE+
            "                  </span></td></tr>"+NEWLINE+
            "             </table>"+NEWLINE);
        sb.append("        </td></tr>"+NEWLINE+"      </table>"+NEWLINE);
        sb.append("</form>");

        return sb.toString();
    }

    // build javascript to validate dates
    private static String getDateValidationScript(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("function checkInput(fromDate, toDate) {"+NEWLINE);
        sb.append("if (!checkDate(fromDate.value)) {"+NEWLINE);
        sb.append("   alert(\""+getLiteral(bundle,"Error_FromDate")+"\");"+NEWLINE);
        sb.append("   fromDate.focus();"+NEWLINE);
        sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
        sb.append("if (!checkDate(toDate.value)) {"+NEWLINE);
        sb.append("   alert(\""+getLiteral(bundle,"Error_ToDate")+"\");"+NEWLINE);
        sb.append("   toDate.focus();"+NEWLINE);
        sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
        sb.append("if (fromDate.value > toDate.value) {"+NEWLINE);
        sb.append("   alert(\""+getLiteral(bundle,"Error_DateRange")+"\");"+NEWLINE);
        sb.append("   fromDate.focus();"+NEWLINE);
        sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
        sb.append("return true;"+NEWLINE+"}"+NEWLINE);
        sb.append("function checkDate(value) {"+NEWLINE);
        sb.append("if (value.length<10) {"+NEWLINE);
        sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
        sb.append("if (isNaN(value.substr(0,4)) ||"+NEWLINE);
        sb.append("    isNaN(value.substr(5,2)) || "+NEWLINE);
        sb.append("    isNaN(value.substr(8,2)) ||"+NEWLINE);
        sb.append("    value.charAt(4)!=\"-\" ||"+NEWLINE);
        sb.append("    value.charAt(7)!=\"-\" ){"+NEWLINE);
        sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
        sb.append("if (value.substr(5,2)>\"12\" ||"+NEWLINE);
        sb.append("    value.substr(5,2)<\"01\" ||"+NEWLINE);
        sb.append("    value.substr(8,2)>\"31\" ||"+NEWLINE);
        sb.append("    value.substr(8,2)<\"01\" ||"+NEWLINE);
        sb.append("    value.substr(0,4)<\"1900\") {"+NEWLINE);
        sb.append("   return false;"+NEWLINE+"}"+NEWLINE);

        // FB 52807:7F014F prevent invalid dates
        sb.append("// check if date is valid, 4/31 would be invalid"+NEWLINE);
        sb.append("// build a date format the Date parser will recognize"+NEWLINE);
        sb.append("valDate = value.substr(5,2)+\"/\"+value.substr(8,2)+\"/\"+value.substr(0,4);"+NEWLINE);
        sb.append("calDate = new Date(valDate);"+NEWLINE);
        sb.append("day     = calDate.getDate();"+NEWLINE);
        sb.append("month   = calDate.getMonth()+1;"+NEWLINE);
        sb.append("year    = calDate.getFullYear();"+NEWLINE);
        sb.append(NEWLINE);
        sb.append("// if 4/31 is entered, Date will convert to 5/01, check if conversion occured"+NEWLINE);
        sb.append("if (month != value.substr(5,2) ||"+NEWLINE);
        sb.append("    day != value.substr(8,2) ||"+NEWLINE);
        sb.append("    year != value.substr(0,4)) {"+NEWLINE);
        sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
        // end FB 52807:7F014F

        sb.append("return true;"+NEWLINE+"}"+NEWLINE);
        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }

    private static String getLiteral(ResourceBundle bundle, String name)
    {
        String result = null;
        if (bundle!=null) {
            result = bundle.getString(name); }
        else {
            result = (String)LITERAL_TBL.get(name); }
        if (result==null) {
            result=name; }
        return result;
    }

    /*********************************************************************
    * Get the resource bundle for literals
    * @param profile Profile
    * @param resourceName String
    * @return ResourceBundle
    * @throws MissingResourceException
    */
    public static ResourceBundle getResourceBundle(COM.ibm.opicmpdh.middleware.Profile profile,
            String resourceName) throws MissingResourceException
    {
        // get the resource bundle for this nls id
        ResourceBundle resourceBundle = null;
        HVECUtils temp = new HVECUtils(); // this is needed to avoid hardcoding packagename
        // this is the only way to load the resource file, it must reside here in the jar file
        resourceName = temp.getClass().getPackage().getName()+"."+resourceName;
        switch(profile.getReadLanguage().getNLSID())
        {
        case 2:
            resourceBundle = ResourceBundle.getBundle(resourceName, Locale.GERMAN);
            break;
        case 3:
            resourceBundle = ResourceBundle.getBundle(resourceName, Locale.ITALIAN);
            break;
        case 4:
            resourceBundle = ResourceBundle.getBundle(resourceName, Locale.JAPANESE);
            break;
        case 5:
            resourceBundle = ResourceBundle.getBundle(resourceName, Locale.FRENCH);
            break;
        case 6: // Spanish
            resourceBundle = ResourceBundle.getBundle(resourceName, new Locale("es", "ES"));
            break;
        case 7:
            resourceBundle = ResourceBundle.getBundle(resourceName, Locale.UK);
            break;
        default: // US English
            resourceBundle = ResourceBundle.getBundle(resourceName, Locale.US);
            break;
        }

        return resourceBundle;
    }

    /**************************************************************************************
    * Get navigate attributes and return display name in order indicated.
    *
    * @param entityItem EntityItem to get display name for
    * @param metaTbl    Hashtable to save meta attributes, attempt to be more efficient
    * @param dbCurrent Database object
    * @param profile Profile object
    * @param attrDelimiter String to use between display attributes
    * @param flagDelimiter String to use between display flag attribute
    * @return String with display name
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    * @throws COM.ibm.opicmpdh.middleware.MiddlewareException,
    * @throws java.sql.SQLException
    */
    public static String getDisplayName(EntityItem entityItem, Hashtable metaTbl,
        COM.ibm.opicmpdh.middleware.Database dbCurrent,
        COM.ibm.opicmpdh.middleware.Profile profile,
        String attrDelimiter, String flagDelimiter)
        throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        java.sql.SQLException
    {
        StringBuffer sb = new StringBuffer();

        // check hashtable to see if we already got this meta
        EANList metaList = (EANList)metaTbl.get(entityItem.getEntityType());
        if (metaList==null)
        {
            EntityGroup eg =  new EntityGroup(null,dbCurrent,profile,entityItem.getEntityType(),"Navigate");
            metaList = eg.getMetaAttribute();
            metaTbl.put(entityItem.getEntityType(), metaList);
        }

        for (int ii=0; ii<metaList.size(); ii++)
        {
            EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
            String value = PokUtils.getAttributeValue(entityItem, ma.getAttributeCode(), flagDelimiter, null);
            if (value !=null)
            {
                if (sb.length()>0) {
                    sb.append(attrDelimiter); }
                sb.append(value);
            }
        }

        return sb.toString();
    }

    // CANNOT use GWA standard colors in Lenovo, they key off of these for other tools
    public static final int LEVEL1 = 1;
    public static final int LEVEL2 = 2;
    public static final int LEVEL3 = 3;
    public static final int LEVEL4 = 4;
    /********************************************************************************
    * Get style to use for the specified row
    *
    * @param level int
    * @returns String
    */
    public static String getRowLevelCSS(int level)
    {
        String css=null;
        switch(level) {
        case LEVEL1:
            css=" style=\"background-color:#9999ff;\"";  //blue-med?
            break;
        case LEVEL2:
            css=" style=\"background-color:#ffcc00;\"";  //orange
            break;
        case LEVEL3:
            css=" style=\"background-color:#99cc00;\"";  //green
            break;
        case LEVEL4:
            css=" style=\"background-color:#ff6600;\"";  //orange-med?
            break;
        default:
            css=" style=\"background-color:#cc9999;\"";  //pink-med?
            break;
        }
        return css;
    }
    /********************************************************************************
    * Get style to use for table header row
    *
    * @returns String
    */
    public static String getTableHeaderRowCSS()
    {
        return "style=\"background-color:#cccc33;\"";  // dark tan
    }
    /********************************************************************************
    * Get style to use for column header row
    *
    * @returns String
    */
    public static String getColumnHeaderRowCSS()
    {
        return "style=\"background-color:#99ffff;\""; //skyblue-light
    }
}
