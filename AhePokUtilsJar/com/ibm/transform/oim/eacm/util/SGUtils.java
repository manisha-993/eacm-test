//Licensed Materials -- Property of IBM
//
//(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.util;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;
import javax.servlet.http.*;

/**********************************************************************************
* This class contains utility methods used by OIM3.0b SG reports.
*
*/
// $Log: SGUtils.java,v $
// Revision 1.25  2014/01/13 13:48:36  wendy
// migration to V17
//
// Revision 1.24  2008/10/22 12:42:22  wendy
// JAWS and CI162 updates
//
// Revision 1.23  2008/01/22 18:30:13  wendy
// Cleanup RSA warnings
//
// Revision 1.22  2008/01/22 15:46:18  wendy
// Changed variable names to remove RSA warnings
//
// Revision 1.21  2006/11/29 18:32:44  couto
// New getMTMname2 method, for uplink entities without FEATURECODE attribute.
//
// Revision 1.20  2006/03/08 22:36:14  sergio
// Change in specs for a missing field label
//
// Revision 1.19  2006/02/28 13:44:09  sergio
// fixed attribute values, moved some label tags for better consistency and  added some brackets to close out some if statements
//
// Revision 1.18  2006/02/17 21:21:43  wendy
// Corrected layout
//
// Revision 1.17  2006/01/26 19:25:12  couto
// Changed calendar control and copyright info.
//
// Revision 1.16  2006/01/25 14:45:50  couto
// New method getOptionsDateEntryForm to be used by Withdrawal and Launch Summary Report.
//
// Revision 1.15  2006/01/10 15:50:30  sergio
// Updates for HPR compliance.
//
// Revision 1.14  2005/12/29 14:38:32  sergio
// DQA changes for the id attributes.
//
// Revision 1.13  2005/12/21 14:12:33  wendy
// WebKing requires 'layout' in table summary attribute for layout tables
//
// Revision 1.12  2005/12/19 15:21:37  wendy
// DQA changes for th id attributes and td headers attributes
//
// Revision 1.11  2005/12/07 14:18:30  couto
// Fixed getCountryForm method to complain with WebKing.
//
// Revision 1.10  2005/11/15 18:34:55  couto
// Updated getCtryFormJavaScript and getCountryForm methods...
//
// Revision 1.9  2005/11/02 22:37:44  wendy
// Added FC where used rpt
//
// Revision 1.8  2005/10/13 17:29:43  wendy
// Change from dropdown list to radio buttons for reporttype
//
// Revision 1.7  2005/10/06 19:35:26  wendy
// Add http to img src attribute
//
// Revision 1.6  2005/10/03 20:09:31  wendy
// Conform to new jtest config
//
// Revision 1.5  2005/09/26 16:45:05  couto
// Fixed the place where the form tag was being closed.
//
// Revision 1.4  2005/09/26 14:36:49  couto
// WebKing fix: getDateTypeCtryForm method: needed to close the <form> tag.
//
// Revision 1.3  2005/09/24 22:12:14  wendy
// Comply with AHE req
//
// Revision 1.2  2005/09/12 16:54:43  wendy
// Add .wss to servlet name
//
// Revision 1.1  2005/09/08 18:11:20  wendy
// Init OIM3.0b
//
//
public class SGUtils
{
	/** object copyright statement */
	public static final String COPYRIGHT="(C) Copyright IBM Corp. 2005, 2006  All Rights Reserved.";
    /* A utility class only contains static methods and static variables. Since an
    implicit default constructor is "public" and a utility class is not designed to
    be instantiated, the "public" default constructor of a utility class should be
    declared "private".  */
    private SGUtils() {}

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.25 $";
    /** from date used with javascript */
    public static final String FROM_DATE_STR = "FROM_DATE";
    /** to date used with javascript */
    public static final String TO_DATE_STR = "TO_DATE";
    /** country list used with javascript */
    public static final String CTRY_LIST = "ctry_list";
    /** report type used with javascript */
    public static final String REPORT_TYPE = "report_type";
    /** report type used with javascript */
    public static final String SUMMARY = "summary";
    /** report type used with javascript */
    public static final String DETAIL = "detail";

    private static final String CTRY_FORM = "countryForm";
    private static final String ORIGCOUNTRYLIST = "origCtryList";
    private static final String USER_SELECTED = "selected";
    private static final String USER_DESELECTED = "deselected";

	private static final String WD_LAUNCH_MODELS = "1";
	private static final String WD_LAUNCH_LSEOS = "2";

    /** general area list used with javascript */
    public static final String GENAREASELECTION = "GENAREASELECTION";
    /** countrylist list used with javascript */
    public static final String COUNTRYLIST = "COUNTRYLIST";
    /** command action used with javascript */
    public static final String CASCADE = "cascade";
    /** command action used with javascript */
    public static final String COMMAND = "command";
    /** list separator used with javascript */
    public static final String SEPARATOR = "-------";

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    /********************************************************************************
    * Allow user to type and find a country
    * from http://www.oreillynet.com/pub/a/javascript/2003/09/03/dannygoodman.html?page=last&x-maxdepth=0
    *@return String
    */
    public static String getTypeAheadScript()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("// global storage object for type-ahead info, including reset() method"+NEWLINE);
        sb.append("var typeAheadInfo = {last:0, "+NEWLINE);
        sb.append("                     accumString:\"\", "+NEWLINE);
        sb.append("                     delay:500,"+NEWLINE);
        sb.append("                     timeout:null, "+NEWLINE);
        sb.append("                     reset:function() {this.last=0; this.accumString=\"\"}"+NEWLINE);
        sb.append("                    };"+NEWLINE);
        sb.append("// function invoked by select element's onkeydown event handler"+NEWLINE);
        sb.append("function typeAhead() {"+NEWLINE);
        sb.append("   // limit processing to IE event model supporter; don't trap Ctrl+keys"+NEWLINE);
        sb.append("   if (window.event && !window.event.ctrlKey) {"+NEWLINE);
        sb.append("      // timer for current event"+NEWLINE);
        sb.append("      var now = new Date();"+NEWLINE);
        sb.append("      // process for an empty accumString or an event within [delay] ms of last"+NEWLINE);
        sb.append("      if (typeAheadInfo.accumString == \"\" || now - typeAheadInfo.last < typeAheadInfo.delay) {"+NEWLINE);
        sb.append("         // make shortcut event object reference"+NEWLINE);
        sb.append("         var evt = window.event;"+NEWLINE);
        sb.append("         // get reference to the select element"+NEWLINE);
        sb.append("         var selectElem = evt.srcElement;"+NEWLINE);
        sb.append("         // get typed character ASCII value"+NEWLINE);
        sb.append("         var charCode = evt.keyCode;"+NEWLINE);
        sb.append("         // get the actual character, converted to uppercase"+NEWLINE);
        sb.append("         var newChar =  String.fromCharCode(charCode).toUpperCase();"+NEWLINE);
        sb.append("         // append new character to accumString storage"+NEWLINE);
        sb.append("         typeAheadInfo.accumString += newChar;"+NEWLINE);
        sb.append("         // grab all select element option objects as an array"+NEWLINE);
        sb.append("         var selectOptions = selectElem.options;"+NEWLINE);
        sb.append("         // prepare local variables for use inside loop"+NEWLINE);
        sb.append("         var txt, nearest;"+NEWLINE);
        sb.append("         // look through all options for a match starting with accumString"+NEWLINE);
        sb.append("         for (var i = 0; i < selectOptions.length; i++) {"+NEWLINE);
        sb.append("            // convert each item's text to uppercase to facilitate comparison"+NEWLINE);
        sb.append("            // (use value property if you want match to be for hidden option value)"+NEWLINE);
        sb.append("            txt = selectOptions[i].text.toUpperCase();"+NEWLINE);
        sb.append("            // record nearest lowest index, if applicable"+NEWLINE);
        sb.append("            nearest = (typeAheadInfo.accumString > txt.substr(0, typeAheadInfo.accumString.length)) ? i : nearest;"+NEWLINE);
        sb.append("            // process if accumString is at start of option text"+NEWLINE);
        sb.append("            if (txt.indexOf(typeAheadInfo.accumString) == 0) {"+NEWLINE);
        sb.append("               // stop any previous timeout timer"+NEWLINE);
        sb.append("               clearTimeout(typeAheadInfo.timeout);"+NEWLINE);
        sb.append("               // store current event's time in object "+NEWLINE);
        sb.append("               typeAheadInfo.last = now;"+NEWLINE);
        sb.append("               // reset typeAhead properties in [delay] ms unless cleared beforehand"+NEWLINE);
        sb.append("               typeAheadInfo.timeout = setTimeout(\"typeAheadInfo.reset()\", typeAheadInfo.delay);"+NEWLINE);
        sb.append("               // visibly select the matching item"+NEWLINE);
        sb.append("               selectElem.selectedIndex = i;"+NEWLINE);
        sb.append("               // prevent default event actions and propagation"+NEWLINE);
        sb.append("               evt.cancelBubble = true;"+NEWLINE);
        sb.append("               evt.returnValue = false;"+NEWLINE);
        sb.append("               // exit function"+NEWLINE);
        sb.append("               return false;   "+NEWLINE);
        sb.append("            }            "+NEWLINE);
        sb.append("         }"+NEWLINE);
        sb.append("         // if a next lowest match exists, select it"+NEWLINE);
        sb.append("         if (nearest != null) {"+NEWLINE);
        sb.append("            selectElem.selectedIndex = nearest;"+NEWLINE);
        sb.append("         }"+NEWLINE);
        sb.append("      } else {"+NEWLINE);
        sb.append("         // not a desired event, so clear timeout"+NEWLINE);
        sb.append("         clearTimeout(typeAheadInfo.timeout);"+NEWLINE);
        sb.append("      }"+NEWLINE);
        sb.append("      // reset global object"+NEWLINE);
        sb.append("      typeAheadInfo.reset();"+NEWLINE);
        sb.append("   }"+NEWLINE);
        sb.append("   return true;"+NEWLINE);
        sb.append("}"+NEWLINE);
        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }

    /********************************************************************************
     * Build the form used to get report type, or date range and country list if needed
     *
     *@param  request     HttpServletRequest with information to pass on
     *@param  appName     String with application name
     *@param  curTime     String if null, no dates will be on the form
     *@param  entityType  String
     *@param  useCtry     boolean if true, display country list selection
     *@param  useType     boolean if true, display type of report selection
     *@param  url         String with url for action
     *@param  dbCurrent   Database
     *@param  profile     Profile
     *@param bundle
     *@return String with javascript and form to select report type
     *@throws Exception
     */
    public static String getDateTypeCtryForm(HttpServletRequest request, String appName, String curTime,
        String entityType, boolean useCtry, boolean useType, String url, Database dbCurrent, Profile profile,
        ResourceBundle bundle) throws Exception
    {
        StringBuffer sb = new StringBuffer();
        Enumeration e = request.getParameterNames();
        String actionName = "/"+appName+"/DG.wss"; // DG servlet as action

        // build javascript to validate dates, ctry selection not needed now
        if (useCtry)
        {
            if (curTime!=null) {
                sb.append(getDateValidationScript(bundle));
                // must output msg about javascript
                sb.append(PokUtils.getJavaScriptWarning());
            }
        } else {  // must want dates here
            sb.append(getDateValidationScript(bundle));
            // must output msg about javascript
            sb.append(PokUtils.getJavaScriptWarning());
        }

        if (request.getParameter(PokUtils.DG_INDICATOR)==null) {// not from DGServlet, from cmd line
            actionName = "/"+appName+url;
        }

        // build date entry form using DG servlet as action if was from DG
        sb.append("<form method=\"post\" name=\"dateTypeCtryForm\" action=\""+actionName+"\" ");
        if (useCtry)
        {
            if (curTime!=null) {
                sb.append("onsubmit=\"return checkInput(this."+FROM_DATE_STR+", this."+TO_DATE_STR+");\">"); // dont force ctry
                //sb.append("onsubmit=\"return checkInput(this."+FROM_DATE_STR+", this."+TO_DATE_STR+", this."+CTRY_LIST+");\">");
            }else {
                //sb.append("onsubmit=\"return checkInput(this."+CTRY_LIST+");\">");
                sb.append(">");
            }
        } else {
            sb.append("onsubmit=\"return checkInput(this."+FROM_DATE_STR+", this."+TO_DATE_STR+");\">");
        }
        sb.append(NEWLINE);

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

        if (useCtry) {
            if (curTime!=null) {
                sb.append("<p>"+PokUtils.REQUIRED_FLD_TXT+"<br /></p>"+NEWLINE);
            }
        } else {
            sb.append("<p>"+PokUtils.REQUIRED_FLD_TXT+"<br /></p>"+NEWLINE);
        }

        sb.append("<table border=\"0\" summary=\"layout\">"+NEWLINE);
        if (curTime != null)     // add date ranges
        {
            sb.append("<tr><td colspan=\"2\">"+bundle.getString("Label_DateRange")+"</td></tr>"+NEWLINE);
            sb.append("<tr><td><label for=\""+FROM_DATE_STR+"\">*" + bundle.getString("Label_FromDate") + "</label></td>"+NEWLINE);
            sb.append("<td style=\"vertical-align:top;\">"+NEWLINE);
            sb.append("<table summary=\"layout\">"+NEWLINE);
            sb.append("<tr><td><input name=\""+FROM_DATE_STR+"\" id=\""+ FROM_DATE_STR +"\" type=\"text\" "+
                "size=\"12\" maxlength=\"10\" value=\""+curTime+"\"/>");
			sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document.dateTypeCtryForm."+FROM_DATE_STR+"', document.dateTypeCtryForm."+FROM_DATE_STR+".value);\">");
			//v17 sb.append("<img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
			sb.append("</a>");
            sb.append("</td><td>&nbsp;(yyyy-mm-dd)</td></tr></table></td></tr>"+NEWLINE);

            sb.append("<tr><td><label for=\""+TO_DATE_STR+"\">*" + bundle.getString("Label_ToDate") + "</label></td>"+NEWLINE);
            sb.append("<td style=\"vertical-align:top;\"><table summary=\"layout\">"+NEWLINE);
            sb.append("<tr><td><input name=\""+TO_DATE_STR+"\" id=\""+ TO_DATE_STR + "\" type=\"text\" "+
                "size=\"12\" maxlength=\"10\" value=\""+curTime+"\"/>");
			sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document.dateTypeCtryForm."+TO_DATE_STR+"', document.dateTypeCtryForm."+TO_DATE_STR+".value);\">");
			//v17 sb.append("<img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
			sb.append("</a>");
            sb.append("</td><td>&nbsp;(yyyy-mm-dd)</td></tr></table></td></tr>"+NEWLINE);
        }

        if (useType)
        {
            // add report type selection
            sb.append("<tr><td style=\"text-align:right;\" colspan=\"2\"><fieldset><legend>"+
                bundle.getString("Label_ReportType") + "</legend>"+NEWLINE);
            sb.append("<input type=\"radio\" name=\""+REPORT_TYPE+"\" checked=\"checked\""+
                " value=\""+SUMMARY+"\" id=\""+SUMMARY+"\" /><label for=\""+SUMMARY+"\">"+bundle.getString("Label_Summary")+"</label>"+NEWLINE);
            sb.append("<input type=\"radio\" name=\""+REPORT_TYPE+"\""+
                " value=\""+DETAIL+"\" id=\""+DETAIL+"\" /><label for=\""+DETAIL+"\">"+bundle.getString("Label_Detail")+"</label>"+NEWLINE);
            /*
                "<label for=\""+REPORT_TYPE+"\"><select id=\""+REPORT_TYPE+"\" name=\""+REPORT_TYPE+"\" onchange=\"dateTypeCtryForm."+CTRY_LIST+".focus();\">"+NEWLINE);
            sb.append("<option value=\""+SUMMARY+"\" selected=\"true\">" + bundle.getString("Label_Summary") + "</option>"+NEWLINE);
            sb.append("<option value=\""+DETAIL+"\">" + bundle.getString("Label_Detail") + "</option>"+NEWLINE);
            sb.append("</select></label>");*/
            sb.append("</fieldset></td></tr>");
        }

        // add countries selection for LSEO and LSEOBUNDLE reports
        if (useCtry)
        {
            EntityList list = dbCurrent.getEntityList(profile,
                new ExtractActionItem(null, dbCurrent, profile, "DUMMY"),
                new EntityItem[] { new EntityItem(null, profile, entityType,0)});

            String metaflags[] = PokUtils.getMetaFlags(list.getParentEntityGroup(), "COUNTRYLIST");
            Vector mfVct = sort(metaflags);

            sb.append("<tr><td style=\"vertical-align:top;\"><label for=\""+CTRY_LIST+"\">"+
                bundle.getString("Label_CountryFilter") + "&nbsp;&nbsp;</label></td>"+NEWLINE);
            sb.append("<td>"+NEWLINE+
//                "<select multiple=\"multiple\" size=\"8\" id=\""+CTRY_LIST+"\" name=\""+CTRY_LIST+"\" onkeydown=\"typeAhead()\">"+NEWLINE);
                "<select multiple=\"multiple\" size=\"8\" id=\""+CTRY_LIST+"\" name=\""+CTRY_LIST+"\">"+NEWLINE);
            // get all possible countries
//            for (int x=0; x<metaflags.length; x++)
            for (int x=0; x<mfVct.size(); x++)
            {
                // comes back as 0010|Under Development, parse it
//                String flag[] = PokUtils.convertToArray(metaflags[x]);
                String flag[] = PokUtils.convertToArray((String)mfVct.elementAt(x));
                sb.append("<option value=\""+flag[0]+"|"+flag[1]+"\">");
                sb.append(flag[1]);
                sb.append("</option>"+NEWLINE);
            }

            sb.append("</select></td></tr>");
            sb.append(NEWLINE);
        }

        // add buttons
        sb.append("<tr><td style=\"text-align:right;\" colspan=\"2\">"+NEWLINE+
            "<table summary=\"layout\" cellspacing=\"3\">"+NEWLINE+
            "<tr><td><span class=\"button-blue\"><input type=\"submit\" name=\"okButton\" value=\" " + bundle.getString("Label_Ok") + " \"/></span></td>"+NEWLINE+
            "    <td><span class=\"button-blue\"><input type=\"button\" name=\"cancelButton\" value=\"" + bundle.getString("Label_Cancel") + "\" onclick=\"window.close();\"/>"+NEWLINE+
            "</span></td></tr></table>"+NEWLINE);
        sb.append("</td></tr></table>"+NEWLINE);
        sb.append("</form>");

        return sb.toString();
    }
    /********************************************************************************
    * Sort on flag value
    *
    * @param metaflags String[]
    * @return Vector
    */
    private static Vector sort(String metaflags[])
    {
        Vector sortedVct = new Vector(metaflags.length);  // limit size needed
            // look at each element in the vector
            for (int x=0; x<metaflags.length; x++)
            {
				// comes back as 0010|Under Development, parse it
                String flag[] = PokUtils.convertToArray(metaflags[x]);
                int id=0;
                for(; id<sortedVct.size(); id++)
                {
                    String flag2[] = PokUtils.convertToArray((String)sortedVct.elementAt(id));
                    if (flag2[1].compareTo(flag[1])>0) {
                        break;
                    }
                }
                // store them in lexical order
                sortedVct.insertElementAt(metaflags[x],id);
            }

        return sortedVct;
    }
    /********************************************************************************
    * Generate name based on MTM
    *
    *@param  linkItem    PRODSTRUCT relator
    *@return String
    */
    public static String getMTMname(EntityItem linkItem)
    {
        String mtmname="";
        if (linkItem.getEntityType().equals("PRODSTRUCT"))
        {
            // build name using child MODEL.MACHTYPEATR+MODEL.MODELATR and parent FEATURE.FEATURECODE
            outerloop:for (int i=0; i<linkItem.getDownLinkCount(); i++) {
                EntityItem mitem = (EntityItem)linkItem.getDownLink(i);  // MODEL
                if (mitem.getEntityType().equals("MODEL")) {
                    for (int u=0; u<linkItem.getUpLinkCount(); u++) {
                        EntityItem fitem = (EntityItem)linkItem.getUpLink(u);  // FEATURE
                        if (fitem.getEntityType().equals("FEATURE"))
                        {
                            mtmname= PokUtils.getAttributeValue(mitem, "MACHTYPEATR", "", "",true) +
                            PokUtils.getAttributeValue(mitem, "MODELATR", "", "",true) +" "+
                            PokUtils.getAttributeValue(fitem, "FEATURECODE", "", "",true)+" : ";
                            break outerloop;
                        }
                    }
                }
            }
        }
        else if (linkItem.getEntityType().equals("SWPRODSTRUCT"))
        {
            // build name using child MODEL.MACHTYPEATR+MODEL.MODELATR and parent SWFEATURE.FEATURECODE
            outerloop:for (int i=0; i<linkItem.getDownLinkCount(); i++) {
                EntityItem mitem = (EntityItem)linkItem.getDownLink(i);  // MODEL
                if (mitem.getEntityType().equals("MODEL")) {
                    for (int u=0; u<linkItem.getUpLinkCount(); u++) {
                        EntityItem fitem = (EntityItem)linkItem.getUpLink(u);  // SWFEATURE
                        if (fitem.getEntityType().equals("SWFEATURE"))
                        {
                            mtmname= PokUtils.getAttributeValue(mitem, "MACHTYPEATR", "", "",true) +
                            PokUtils.getAttributeValue(mitem, "MODELATR", "", "",true) +" "+
                            PokUtils.getAttributeValue(fitem, "FEATURECODE", "", "",true)+" : ";
                            break outerloop;
                        }
                    }
                }
            }

        }
        return mtmname;
    }


    /********************************************************************************
    * Generate name based on MTM for uplink entities who doesn't have FEATURECODE
    *
    *@param  linkItem    PRODSTRUCT relator
    *@return String
    */
    public static String getMTMname2(EntityItem linkItem)
    {
        String mtmname="";
        if (linkItem.getEntityType().equals("PRODSTRUCT"))
        {
            // build name using child MODEL.MACHTYPEATR+MODEL.MODELATR and parent FEATURE.FEATURECODE
            outerloop:for (int i=0; i<linkItem.getDownLinkCount(); i++) {
                EntityItem mitem = (EntityItem)linkItem.getDownLink(i);  // MODEL
                if (mitem.getEntityType().equals("MODEL")) {
                    for (int u=0; u<linkItem.getUpLinkCount(); u++) {
                        EntityItem fitem = (EntityItem)linkItem.getUpLink(u);  // FEATURE
                        if (fitem.getEntityType().equals("FEATURE"))
                        {
                            mtmname= PokUtils.getAttributeValue(mitem, "MACHTYPEATR", "", "",true) +
                            PokUtils.getAttributeValue(mitem, "MODELATR", "", "",true) +" : ";
                            break outerloop;
                        }
                    }
                }
            }
        }
        else if (linkItem.getEntityType().equals("SWPRODSTRUCT"))
        {
            // build name using child MODEL.MACHTYPEATR+MODEL.MODELATR and parent SWFEATURE.FEATURECODE
            outerloop:for (int i=0; i<linkItem.getDownLinkCount(); i++) {
                EntityItem mitem = (EntityItem)linkItem.getDownLink(i);  // MODEL
                if (mitem.getEntityType().equals("MODEL")) {
                    for (int u=0; u<linkItem.getUpLinkCount(); u++) {
                        EntityItem fitem = (EntityItem)linkItem.getUpLink(u);  // SWFEATURE
                        if (fitem.getEntityType().equals("SWFEATURE"))
                        {
                            mtmname= PokUtils.getAttributeValue(mitem, "MACHTYPEATR", "", "",true) +
                            PokUtils.getAttributeValue(mitem, "MODELATR", "", "",true) +" : ";
                            break outerloop;
                        }
                    }
                }
            }

        }
        return mtmname;
    }


    // build javascript to validate ctry list
    /*private static String getCtryValidationScript(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("function checkInput(ctryList) {"+NEWLINE);
        sb.append("if (ctryList.options.selectedIndex == -1) {"+NEWLINE);
        sb.append("   alert(\"" + bundle.getString("Label_SelectCountry") + "\");"+NEWLINE);
        sb.append("   ctryList.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);

        sb.append("return true;}"+NEWLINE);
        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }*/
    // build javascript to validate dates
    private static String getDateValidationScript(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("function checkInput(fromDate, toDate) {"+NEWLINE);
        sb.append(getDateChecks(bundle));
        sb.append("return true;}"+NEWLINE);
        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }
    // build javascript to validate dates and ctry list
   /* private static String getDateCtryValidationScript(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);
        sb.append("function checkInput(fromDate, toDate, ctryList) {"+NEWLINE);
        sb.append("if (ctryList.options.selectedIndex == -1) {"+NEWLINE);
        sb.append("   alert(\"" + bundle.getString("Label_SelectCountry") + "\");"+NEWLINE);
        sb.append("   ctryList.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);

        sb.append(getDateChecks(bundle));

        sb.append("return true;}"+NEWLINE);
        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }*/
    // build javascript to validate dates
    private static String getDateChecks(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("if (!checkDate(fromDate.value)) {"+NEWLINE);
        sb.append("   alert(\"" + bundle.getString("Label_ValidFromDate") + "\");"+NEWLINE);
        sb.append("   fromDate.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);
        sb.append("if (!checkDate(toDate.value)) {"+NEWLINE);
        sb.append("   alert(\"" + bundle.getString("Label_ValidToDate") + "\");"+NEWLINE);
        sb.append("   toDate.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);
        sb.append("if (fromDate.value > toDate.value) {"+NEWLINE);
        sb.append("   alert(\"" + bundle.getString("Label_FromBeforeTo") + "\");"+NEWLINE);
        sb.append("   fromDate.focus();"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);
        sb.append("function checkDate(value) {"+NEWLINE);
        sb.append("if (value.length<10) {"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);
        sb.append("if (isNaN(value.substr(0,4)) ||"+NEWLINE);
        sb.append("    isNaN(value.substr(5,2)) || "+NEWLINE);
        sb.append("    isNaN(value.substr(8,2)) ||"+NEWLINE);
        sb.append("    value.charAt(4)!=\"-\" ||"+NEWLINE);
        sb.append("    value.charAt(7)!=\"-\" ){"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);
        sb.append("if (value.substr(5,2)>\"12\" ||"+NEWLINE);
        sb.append("    value.substr(5,2)<\"01\" ||"+NEWLINE);
        sb.append("    value.substr(8,2)>\"31\" ||"+NEWLINE);
        sb.append("    value.substr(8,2)<\"01\" ||"+NEWLINE);
        sb.append("    value.substr(0,4)<\"1900\") {"+NEWLINE);
        sb.append("   return false;}"+NEWLINE);

        // prevent invalid dates
        sb.append("// check if date is valid, 4/31 would be invalid"+NEWLINE);
        sb.append("// build a date format the Date parser will recognize"+NEWLINE);
        sb.append("valDate = value.substr(5,2)+\"/\"+value.substr(8,2)+\"/\"+value.substr(0,4);"+NEWLINE);
        sb.append("calDate = new Date(valDate);"+NEWLINE);
        sb.append("day     = calDate.getDate();"+NEWLINE);
        sb.append("month   = calDate.getMonth()+1;"+NEWLINE);
        sb.append("year    = calDate.getFullYear();"+NEWLINE);
        sb.append(""+NEWLINE);
        sb.append("// if 4/31 is entered, Date will convert to 5/01, check if conversion occured"+NEWLINE);
        sb.append("if (month != value.substr(5,2) ||"+NEWLINE);
        sb.append("    day != value.substr(8,2) ||"+NEWLINE);
        sb.append("    year != value.substr(0,4)) {"+NEWLINE);
        sb.append("return false;}"+NEWLINE);
        sb.append("return true;}"+NEWLINE);
        return sb.toString();
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
        SGUtils temp = new SGUtils(); // this is needed to avoid hardcoding packagename
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

    /********************************************************************************
    * build javascript to cascade flags and verify selection
    *@return String
    */
    private static String getCtryFormJavaScript(ResourceBundle bundle, String fromDate)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE);

        sb.append("function triggerCascade() {"+NEWLINE);
        // check that at least genarea is selected
        sb.append("  var cnt=0;"+NEWLINE);
        sb.append("  for (cntr=0; cntr<document."+CTRY_FORM+"."+GENAREASELECTION+".length; cntr++)"+NEWLINE);
        sb.append("  {"+NEWLINE+
                  "     if(document."+CTRY_FORM+"."+GENAREASELECTION+".options[cntr].selected && "+NEWLINE+
                  "        document."+CTRY_FORM+"."+GENAREASELECTION+".options[cntr].value != \"\"){ //skip separator"+NEWLINE+
                  "       cnt++;}"+NEWLINE+
                  "  }"+NEWLINE);
        sb.append("  if(cnt == 0) { // no genarea selected so don't try to cascade"+NEWLINE);
        sb.append("    document."+CTRY_FORM+"."+COUNTRYLIST+".focus();"+NEWLINE);
        sb.append("    return; }"+NEWLINE);

        // tell jsp that this is a cascade
        sb.append("   document."+CTRY_FORM+"."+COMMAND+".value=\""+CASCADE+"\";"+NEWLINE);
        sb.append("   document."+CTRY_FORM+".submit();"+NEWLINE);
        // prevent other selections until this returns, the form will be replaced
        sb.append("   document."+CTRY_FORM+"."+GENAREASELECTION+".disabled=\"true\";"+NEWLINE);
        sb.append("   document."+CTRY_FORM+"."+COUNTRYLIST+".disabled=\"true\";"+NEWLINE);
        sb.append("   document."+CTRY_FORM+".okButton.disabled=\"true\";"+NEWLINE);
        sb.append("   document."+CTRY_FORM+".cancelButton.disabled=\"true\";"+NEWLINE);
        sb.append("   var TOUEnabled = document.getElementById(\"TOUEnabled\");"+NEWLINE);
        sb.append("   var TOUDisabled = document.getElementById(\"TOUDisabled\");"+NEWLINE);
        sb.append("   TOUEnabled.style.display = \"none\";"+NEWLINE);
        sb.append("   TOUDisabled.style.display = \"inline\";"+NEWLINE);
        sb.append("   TOUDisabled.disabled = \"true\";"+NEWLINE);
        if (fromDate != null) {
            sb.append("   var calendarFromEnabled = document.getElementById(\"calendarFromEnabled\");"+NEWLINE);
            sb.append("   var calendarFromDisabled = document.getElementById(\"calendarFromDisabled\");"+NEWLINE);
            sb.append("   var calendarToEnabled = document.getElementById(\"calendarToEnabled\");"+NEWLINE);
            sb.append("   var calendarToDisabled = document.getElementById(\"calendarToDisabled\");"+NEWLINE);
            sb.append("   calendarFromEnabled.style.display = \"none\";"+NEWLINE);
            sb.append("   calendarFromDisabled.style.display = \"inline\";"+NEWLINE);
            sb.append("   calendarToEnabled.style.display = \"none\";"+NEWLINE);
            sb.append("   calendarToDisabled.style.display = \"inline\";"+NEWLINE);
        }
        sb.append("}"+NEWLINE);

        // check on submit that at least one country is selected
        sb.append("function checkInput() {"+NEWLINE);
        sb.append("  var cnt=0;"+NEWLINE);
        sb.append("  for (cntr=0; cntr<document."+CTRY_FORM+"."+COUNTRYLIST+".length; cntr++)"+NEWLINE);
        sb.append("  {"+NEWLINE+
                  "     if(document."+CTRY_FORM+"."+COUNTRYLIST+".options[cntr].selected && "+NEWLINE+
                  "        document."+CTRY_FORM+"."+COUNTRYLIST+".options[cntr].value != \"\"){ //skip separator"+NEWLINE+
                  "       cnt++;;}"+NEWLINE+
                  "  }"+NEWLINE);
        sb.append("  if(cnt == 0) { // no country selected"+NEWLINE);
        sb.append("    alert(\"" +
        // Please select one to ten Countries prior to submitting the report."
            bundle.getString("Error_SelectCountryLimit")+ "\");"+NEWLINE);
        sb.append("    document."+CTRY_FORM+"."+COUNTRYLIST+".focus();"+NEWLINE);
        sb.append("    return false;}"+NEWLINE);
        sb.append("  if(cnt >10) { // too many country selected"+NEWLINE);
        sb.append("    alert(\"" + bundle.getString("Error_SelectedTooManyCountry")
            //Please select a maximum of ten Countries prior to submitting the report.
            + "\");"+NEWLINE);
        sb.append("    document."+CTRY_FORM+"."+COUNTRYLIST+".focus();"+NEWLINE);
        sb.append("    return false;}"+NEWLINE);

        //Dates also present in the form
        if (fromDate != null) {
            sb.append("if (!checkDate(document."+CTRY_FORM+"."+FROM_DATE_STR+".value)) {"+NEWLINE);
            sb.append("   alert(\"" + bundle.getString("Label_ValidFromDate") + "\");"+NEWLINE);
            sb.append("   document."+CTRY_FORM+"."+FROM_DATE_STR+".focus();"+NEWLINE);
            sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
            sb.append("if (!checkDate(document."+CTRY_FORM+"."+TO_DATE_STR+".value)) {"+NEWLINE);
            sb.append("   alert(\"" + bundle.getString("Label_ValidToDate") + "\");"+NEWLINE);
            sb.append("   document."+CTRY_FORM+"."+TO_DATE_STR+".focus();"+NEWLINE);
            sb.append("   return false;"+NEWLINE+"}"+NEWLINE);
            sb.append("if (document."+CTRY_FORM+"."+FROM_DATE_STR+".value > document."+CTRY_FORM+"."+TO_DATE_STR+".value) {"+NEWLINE);
            sb.append("   alert(\"" + bundle.getString("Label_FromBeforeTo") + "\");"+NEWLINE);
            sb.append("   document."+CTRY_FORM+"."+FROM_DATE_STR+".focus();"+NEWLINE);
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
            sb.append(""+NEWLINE);
            sb.append("// if 4/31 is entered, Date will convert to 5/01, check if conversion occured"+NEWLINE);
            sb.append("if (month != value.substr(5,2) ||"+NEWLINE);
            sb.append("    day != value.substr(8,2) ||"+NEWLINE);
            sb.append("    year != value.substr(0,4)){"+NEWLINE);
            sb.append("return false;}"+NEWLINE);
        }

        sb.append(" return true;}"+NEWLINE);

        sb.append("//  -->"+NEWLINE);
        sb.append("</script>"+NEWLINE);

        return sb.toString();
    }

	/********************************************************************************
	* Build the form used to select Options and Dates
	*
	*@param  request    HttpServletRequest with information to pass on
	*@param  appName    String with application name
	*@param  curTime	String with the current time
	*@param  withdrawn	Flag used to know if the report is Withdrawn or Launch
	*@param  url        String
	*@param  bundle     ResourceBundle
	*@return String with javascript and form to select options and dates
	*/
	public static String getOptionsDateEntryForm(HttpServletRequest request, String appName, String curTime,
		boolean withdrawn, String url, ResourceBundle bundle)
	{
		StringBuffer sb = new StringBuffer();
		Enumeration e = request.getParameterNames();

		// build date entry form using DG servlet as action
		String actionName = "/"+appName+"/DG.wss";
		if (request.getParameter(PokUtils.DG_INDICATOR)==null) {// not from DGServlet, from cmd line
			actionName = "/" + appName + url;
		}

		// build javascript to validate dates
		sb.append(getDateValidationScript(bundle));
		// must output msg about javascript
		sb.append(PokUtils.getJavaScriptWarning());

		sb.append("<form method=\"post\" name=\"optionsForm\" action=\""+actionName+"\" ");
		sb.append("onsubmit=\"return checkInput(this."+FROM_DATE_STR+", this."+TO_DATE_STR+");\">" + NEWLINE);

		//output all request parameters as hidden fields
		while(e.hasMoreElements()) {
			String name = (String)e.nextElement();
		 	String vals[] = request.getParameterValues(name);
		 	if(vals != null) {
			 	for(int i = 0; i<vals.length; i++) {
				 	sb.append("<input type=\"hidden\" name=\""+name+"\" value=\""+vals[i]+"\"/>" + NEWLINE);
			 	}
		 	}
	 	}

		sb.append("<p>"+PokUtils.REQUIRED_FLD_TXT+"<br /></p>" + NEWLINE);
		sb.append("<br />" + NEWLINE);

		sb.append("  <table summary=\"layout\" width=\"560\" border=\"0\" cellpadding=\"1\" cellspacing=\"0\">" + NEWLINE);
		sb.append("    <tr>" + NEWLINE + "    <td colspan=\"2\"><fieldset><legend>" );
		sb.append(bundle.getString("Label_Report_Options"));
		sb.append("</legend>");

		sb.append("      <table ><tr><td width='48%'>&nbsp;</td><td>");
		sb.append("<input type =\"radio\" name=\"displayOptions\" checked=\"checked\" value=\"" + WD_LAUNCH_MODELS + "\" id=\"option1\"/>");
		sb.append("<label for=\"option1\">");
		if (withdrawn) {
		 	sb.append(bundle.getString("Label_WithdrawnModels"));
		}
		else {
			sb.append(bundle.getString("Label_AnnouncedModels"));
		}
		sb.append("</label></td></tr>" + NEWLINE);
		sb.append("    <tr>" + NEWLINE);
		sb.append("<td>&nbsp;</td>");
		sb.append("      <td><input type =\"radio\" name=\"displayOptions\" value=\"" + WD_LAUNCH_LSEOS + "\" id=\"option2\"/>");
		sb.append("<label for=\"option2\">");
		if (withdrawn) {
			sb.append(bundle.getString("Label_WDLSEOAndLSEOBundle"));
		}
		else {
			sb.append(bundle.getString("Label_AnnLSEOAndLSEOBundle"));
		}
		sb.append("</label></td>" + NEWLINE);
		sb.append("    </tr></table></fieldset></td></tr>" + NEWLINE);

		sb.append("<tr><td width='42%'><label for=\""+FROM_DATE_STR+"\">*");
		if (withdrawn) {
		 	sb.append(bundle.getString("Label_WithdrawalFromDate"));
		}
		else {
			sb.append(bundle.getString("Label_AnnouncementFromDate"));
		}
		sb.append("</label></td>" + NEWLINE);
		sb.append("    <td><input name=\""+FROM_DATE_STR+"\" id=\""+ FROM_DATE_STR +"\" type=\"text\" "+
						"size=\"12\" maxlength=\"10\" value=\""+curTime+"\"/>");
		sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document.optionsForm."+FROM_DATE_STR+"', document.optionsForm."+FROM_DATE_STR+".value);\">");
		//v17 sb.append("<img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
		sb.append("</a>");
		sb.append("&nbsp;(yyyy-mm-dd)</td></tr>" + NEWLINE);

		sb.append("<tr><td width='42%'><label for=\""+TO_DATE_STR+"\">*");
		if (withdrawn) {
			sb.append(bundle.getString("Label_WithdrawalToDate"));
		}
		else {
			sb.append(bundle.getString("Label_AnnouncementToDate"));
		}
		sb.append("</label></td>" + NEWLINE);
		sb.append("   <td><input name=\""+TO_DATE_STR+"\" id=\""+ TO_DATE_STR +"\" type=\"text\" "+
					   "size=\"12\" maxlength=\"10\" value=\""+curTime+"\"/>");
		sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document.optionsForm."+TO_DATE_STR+"', document.optionsForm."+TO_DATE_STR+".value);\">");
		//v17 sb.append("<img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
		sb.append("</a>");
		sb.append("&nbsp;(yyyy-mm-dd)</td></tr>" + NEWLINE);

		// add buttons
		sb.append("   <tr><td style=\"text-align:right;\" colspan=\"2\">" + NEWLINE +
				"     <table summary=\"layout\" cellspacing=\"3\">" + NEWLINE +
				"     <tr><td><span class=\"button-blue\"><input type=\"submit\" name=\"okButton\" value=\" "+
				bundle.getString("Label_Ok") + " \"/></span></td>" + NEWLINE +
				"     <td><span class=\"button-blue\"><input type=\"button\" name=\"cancelButton\" value=\""+
				bundle.getString("Label_Cancel") + "\" onclick=\"window.close();\"/></span>" + NEWLINE +
				"     </td></tr>" + NEWLINE +
				"     </table>" + NEWLINE);
		sb.append("   </td></tr>" + NEWLINE + "  </table>" + NEWLINE);
		sb.append("</form>");

		return sb.toString();
	}

    /********************************************************************************
    * Build the form used to select countries(s) and cascade the values
    *
    *@param  request    HttpServletRequest with information to pass on
    *@param  dbCurrent  Database
    *@param  profile    Profile
    *@param  appName    String with application name
    *@param  bundle     ResourceBundle
    *@param  url        String
    *@return String with javascript and form to select countries
    *@throws java.sql.SQLException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
    *@throws COM.ibm.eannounce.objects.EANBusinessRuleException
    */
    public static String getCountryForm(HttpServletRequest request, Database dbCurrent, Profile profile,
        String appName, ResourceBundle bundle, String url) //throws Exception
        throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.EANBusinessRuleException

    {
        return getCountryForm(request, dbCurrent, profile, appName, bundle, url, null, null);
    }

    /********************************************************************************
    * Build the form used to select countries(s) and cascade the values
    *
    *@param  request    HttpServletRequest with information to pass on
    *@param  dbCurrent  Database
    *@param  profile    Profile
    *@param  appName    String with application name
    *@param  bundle     ResourceBundle
    *@param  url        String
    *@param  fromDate   String
    *@param  toDate     String
    *@return String with javascript and form to select countries
    *@throws java.sql.SQLException
    *@throws COM.ibm.opicmpdh.middleware.MiddlewareException
    *@throws COM.ibm.eannounce.objects.EANBusinessRuleException
    */
    public static String getCountryForm(HttpServletRequest request, Database dbCurrent, Profile profile,
        String appName, ResourceBundle bundle, String url, String fromDate, String toDate) //throws Exception
        throws java.sql.SQLException,
        COM.ibm.opicmpdh.middleware.MiddlewareException,
        COM.ibm.eannounce.objects.EANBusinessRuleException

    {
        String actionName = "/"+appName+"/DG.wss"; // DG servlet as action
        StringBuffer sb = new StringBuffer();
        Enumeration reqEnum = request.getParameterNames();
        boolean isCascade = false;
        CreateActionItem cai;
        EntityItem wgArray[];
        EntityList list;
        EntityItem featItem;
        RowSelectableTable curItemTable;
        String keyStr;
        int genrow,row;
        EANFlagAttribute genareaAtt,ctrylistAtt;
        EANMetaFlagAttribute metaFlagAtt;
        CtrySelection addCtrySel, delCtrySel,newCtrySel;
        Vector selectedVct = new Vector();
        Vector notSelectedVct = new Vector();
        MetaFlagComparator mfComparator = new MetaFlagComparator();

        // build javascript to force cascading flags
        sb.append(getCtryFormJavaScript(bundle, fromDate));

        // must output msg about javascript
        sb.append(PokUtils.getJavaScriptWarning());

        if (request.getParameter(PokUtils.DG_INDICATOR)==null) {// not from DGServlet, from cmd line
            actionName = "/"+appName+url;
        }

        // build entry form using DG servlet as action if was from DG
        sb.append("<form method=\"post\" name=\""+CTRY_FORM+"\" action=\""+actionName+"\" "+NEWLINE);
        sb.append("onsubmit=\"return checkInput();\">"+NEWLINE);

        sb.append("<input type=\"hidden\" name=\""+COMMAND+"\" value=\"\"/>"+NEWLINE);  // has 'cascade' in it when genarea is chgd

        // output all request parameters as hidden fields
        while(reqEnum.hasMoreElements())
        {
            String vals[];
            String name = (String)reqEnum.nextElement();
            if (name.equals(COMMAND)) { // is this a cascade request? don't put command value back
                String cascade = request.getParameter(name);
                isCascade=cascade.equals(CASCADE);
                continue;
            }
            // don't put selection or locked flag values back into form
            if (name.equals(GENAREASELECTION) || name.equals(COUNTRYLIST) || name.equals(ORIGCOUNTRYLIST)
                || name.equals(USER_DESELECTED) || name.equals(USER_SELECTED) || name.equals(FROM_DATE_STR)
                || name.equals(TO_DATE_STR)) {
                continue;
            }
            vals = request.getParameterValues(name);
            if(vals != null)  {
                for(int i = 0; i<vals.length; i++) {
                    sb.append("<input type=\"hidden\" name=\""+name+"\" value=\""+vals[i]+"\"/>"+NEWLINE);
                }
            }
        }

        // get lists to use for general area and country
        // if user has selected a general area, use that to filter the countries
        // get a new feature so it has a clear GENAREASELECTION, manipulate this to get the cascading COUNTRYLIST
        cai = new CreateActionItem(null,dbCurrent, profile, "CRFEATURE");
        wgArray = new EntityItem[] { new EntityItem(null, profile, "WG", profile.getWGID()) };
        list = new EntityList(dbCurrent, profile, cai, wgArray);

        featItem = list.getEntityGroup("FEATURE").getEntityItem(0);

        curItemTable = featItem.getEntityItemTable();
        keyStr = "FEATURE:GENAREASELECTION";
        genrow = curItemTable.getRowIndex(keyStr);
        if (genrow < 0) {
            genrow = curItemTable.getRowIndex(keyStr + ":C");
        }
        if (genrow < 0) {
            genrow = curItemTable.getRowIndex(keyStr + ":R");
        }

        // get general area values
        genareaAtt = (EANFlagAttribute) curItemTable.getEANObject(genrow, 1);
        metaFlagAtt = (EANMetaFlagAttribute) genareaAtt.getMetaAttribute();

        //keep an added and deleted parameter to maintain many cascade chgs
        addCtrySel = new CtrySelection(request.getParameter(USER_SELECTED)); // this is list of ctry added by user, may be null
        delCtrySel = new CtrySelection(request.getParameter(USER_DESELECTED)); // this is list of ctry removed by user, may be null

        // if user has selected some general area then use it to get countrylist
        if (isCascade){
            String[] genValues;
            MetaFlag[] metaFlags;
            // keep track of countries user selects or deselects specifically, remember these and don't
            // allow genareaselection cascading values overwrite them
            CtrySelection curCtrySel = new CtrySelection(request.getParameterValues(COUNTRYLIST)); // this is current list of ctry from input page
            CtrySelection origCtrySel = new CtrySelection(request.getParameter(ORIGCOUNTRYLIST));  // this is list of orig ctry sent to user
            curCtrySel.buildChgLists(origCtrySel, addCtrySel, delCtrySel);

            genValues = request.getParameterValues(GENAREASELECTION);
            metaFlags = new MetaFlag[metaFlagAtt.getMetaFlagCount()];
            for (int i = 0; i < metaFlags.length; i++) {
                try {
                    metaFlags[i] = metaFlagAtt.getMetaFlag(i);
                    metaFlags[i].setSelected(false);
                } catch (Exception em) {
                    System.out.println(em.getMessage());
                }
            }

            for (int i = 0; i < genValues.length; i++) {
                try {
                    MetaFlag tempMetaFlag = metaFlagAtt.getMetaFlag(genValues[i]);
                    if (tempMetaFlag != null) {
                        tempMetaFlag.setSelected(true);
                    }
                } catch (Exception em) {
                    System.out.println(em.getMessage());}
            }

            curItemTable.put(genrow, 1, metaFlags);
            curItemTable.refresh();    // modify countrylist based on genareaselection
        }

        // get the country list now, it may have been modified by genareaselection
        keyStr = "FEATURE:COUNTRYLIST";
        row = curItemTable.getRowIndex(keyStr);
        if (row < 0) {
            row = curItemTable.getRowIndex(keyStr + ":C");
        }
        if (row < 0) {
            row = curItemTable.getRowIndex(keyStr + ":R");
        }

        ctrylistAtt = (EANFlagAttribute) curItemTable.getEANObject(row, 1);

        sb.append("<p>"+PokUtils.REQUIRED_FLD_TXT+"<br /></p>"+NEWLINE);
        sb.append("<p>"+ bundle.getString("Label_CountrySelectLimit")+"</p>"+NEWLINE);

        sb.append("<table summary=\"layout\" border=\"0\">"+NEWLINE);
        sb.append("<tr><td style=\"vertical-align:bottom;\">"+NEWLINE);
//        sb.append("General Area:</td><td>*Country:");
        sb.append("<label for=\""+GENAREASELECTION+"\">" + bundle.getString("Label_GeneralArea")+"</label>"+
        	"&nbsp;<span class=\"button-gray\"><input type=\"button\" name=\"filterButton\" value=\"Filter for countries-->\" onclick=\"javascript:triggerCascade();\"/></span>\n"+
        	"</td><td style=\"vertical-align:bottom;\"><label for=\""+COUNTRYLIST+"\"> *"+ bundle.getString("Label_CountryTitle"));
        sb.append("</label></td></tr>"+NEWLINE);
        // add generalarea selection
        sb.append("<tr><td>"+NEWLINE);
        sb.append("<select multiple=\"multiple\" size=\"12\" ");//onchange=\"javascript:triggerCascade();\"");
        sb.append(" id=\""+GENAREASELECTION+"\" name=\""+GENAREASELECTION+"\">"+NEWLINE);
        // if not cascading, no default should be set in GENAREASELECTION or COUNTRYLIST
        metaFlagAtt = (EANMetaFlagAttribute) genareaAtt.getMetaAttribute();
        for (int i = 0; i < metaFlagAtt.getMetaFlagCount(); i++) {
            try {
                MetaFlag mflag = metaFlagAtt.getMetaFlag(i);
                // get selection
                if (isCascade && genareaAtt.isSelected(mflag)) {
                    selectedVct.addElement(mflag);
                }
                else {
                    notSelectedVct.addElement(mflag);
                }
            } catch (Exception em) {
                System.out.println(em.getMessage());}
        }
        Collections.sort(selectedVct,mfComparator);
        Collections.sort(notSelectedVct,mfComparator);
        for (int i=0; i<selectedVct.size(); i++) {
            MetaFlag mflag = (MetaFlag)selectedVct.elementAt(i);
            sb.append("<option selected=\"true\" value=\""+mflag.getFlagCode()+"\">" + mflag.toString() + "</option>"+NEWLINE);
        }
        if (notSelectedVct.size()>0) {
            if(selectedVct.size()>0) { // there is something to separate
                sb.append("<option>"+SEPARATOR+"</option>");
            }
            for (int i=0; i<notSelectedVct.size(); i++) {
                MetaFlag mflag = (MetaFlag)notSelectedVct.elementAt(i);
                sb.append("<option value=\""+mflag.getFlagCode()+"\">" + mflag.toString() + "</option>"+NEWLINE);
            }
            notSelectedVct.clear();
        }
        selectedVct.clear();

        sb.append("</select>"+NEWLINE);
        sb.append("</td><td>");
        // add country selection
        sb.append("<select multiple=\"multiple\" size=\"12\"");
        sb.append(" id=\""+COUNTRYLIST+"\" name=\""+COUNTRYLIST+"\">"+NEWLINE);
        metaFlagAtt = (EANMetaFlagAttribute) ctrylistAtt.getMetaAttribute();
        for (int i = 0; i < metaFlagAtt.getMetaFlagCount(); i++) {
            try {
                MetaFlag mflag = metaFlagAtt.getMetaFlag(i);
                if (!isCascade) {
                   notSelectedVct.addElement(mflag);
                }else {
                    // get selection
                    if (ctrylistAtt.isSelected(mflag)) {
                        // did user specifically remove this one?
                        if (delCtrySel.get(mflag.getFlagCode())!=null){
                            notSelectedVct.addElement(mflag);
                        }
                        else{
                            selectedVct.addElement(mflag);
                        }
                    }
                    else {
                        // did user specifically add this one?
                        if (addCtrySel.get(mflag.getFlagCode())!=null){
                            selectedVct.addElement(mflag);
                        }
                        else{
                            notSelectedVct.addElement(mflag);
                        }
                    }
                }
            } catch (Exception em) {
                System.out.println(em.getMessage());}
        }

        Collections.sort(selectedVct,mfComparator);
        Collections.sort(notSelectedVct,mfComparator);

        newCtrySel = new CtrySelection(); // this is list of ctry to be sent to user
        for (int i=0; i<selectedVct.size(); i++) {
            MetaFlag mflag = (MetaFlag)selectedVct.elementAt(i);
            sb.append("<option selected=\"true\" value=\""+mflag.getFlagCode()+"\">" + mflag.toString() + "</option>"+NEWLINE);
            newCtrySel.put(mflag.getFlagCode());
        }
        if (notSelectedVct.size()>0) {
            if(selectedVct.size()>0) { // there is something to separate
                sb.append("<option>"+SEPARATOR+"</option>");
            }
            for (int i=0; i<notSelectedVct.size(); i++) {
                MetaFlag mflag = (MetaFlag)notSelectedVct.elementAt(i);
                sb.append("<option value=\""+mflag.getFlagCode()+"\">" + mflag.toString() + "</option>"+NEWLINE);
            }
            notSelectedVct.clear();
        }
        selectedVct.clear();
        sb.append("</select></td></tr>"+NEWLINE);
        //From Date and To Date fields
        if (fromDate != null) {
	        sb.append("<tr><td colspan=\"2\">" +NEWLINE);
        	sb.append("<table summary=\"layout\" border=\"0\">"+NEWLINE);
            //sb.append("*From Withdrawal Date:</td><td>*To Withdrawal Date:");
            sb.append("<tr><td><label for=\""+SGUtils.FROM_DATE_STR+"\">*" + bundle.getString("Label_WithdrawalFromDate") + "</label></td>"+ NEWLINE);  //modified section to deal with HPR 1/6/06
            sb.append("    <td><input name=\""+SGUtils.FROM_DATE_STR +
            	"\" id=\""+ SGUtils.FROM_DATE_STR + "\" type=\"text\" size=\"12\" maxlength=\"10\" value=\""+fromDate+"\"/>");
            sb.append("      <span id=\"CalendarFromEnabled\" style=\"display:inline\">"+NEWLINE);
			sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document."+CTRY_FORM+"."+FROM_DATE_STR+"', document."+CTRY_FORM+"."+FROM_DATE_STR+".value);\">");
			//v17 sb.append("<img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
			sb.append("</a>");
            sb.append("      </span><span class=\"ibm-calendar-link\" id=\"CalendarFromDisabled\" style=\"display:none\">"+NEWLINE);
            //v17 sb.append("      <img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/>"+NEWLINE);
            sb.append("      </span>"+NEWLINE);
            sb.append("    </td><td>&nbsp;(yyyy-mm-dd)</td></tr>"+NEWLINE);

			sb.append("<tr><td><label for=\""+SGUtils.TO_DATE_STR+"\">*" + bundle.getString("Label_WithdrawalToDate") + "</label></td>"+ NEWLINE);
            sb.append("    <td><input name=\""+SGUtils.TO_DATE_STR +
                "\" id=\""+ SGUtils.TO_DATE_STR + "\" type=\"text\" size=\"12\" maxlength=\"10\" value=\""+toDate+"\"/>");
            sb.append("      <span id=\"CalendarToEnabled\" style=\"display:inline\">"+NEWLINE);
			sb.append("&nbsp;<a href=\"javascript:doNothing()\" class=\"ibm-calendar-link\" onclick=\"pickDate('document."+CTRY_FORM+"."+TO_DATE_STR+"', document."+CTRY_FORM+"."+TO_DATE_STR+".value);\">");
			//v17 sb.append("<img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/></a>");
			sb.append("</a>");
            sb.append("      </span><span class=\"ibm-calendar-link\" id=\"CalendarToDisabled\" style=\"display:none\">"+NEWLINE);
            //v17 sb.append("      <img height=\"14\" width=\"13\" src=\"//w3.ibm.com/ui/v8/images/icon-select-date.gif\" alt=\"" + bundle.getString("Label_Calendar") + "\"/>"+NEWLINE);
            sb.append("      </span>"+NEWLINE);
			sb.append("    </td><td>&nbsp;(yyyy-mm-dd)</td></tr>"+NEWLINE);
            sb.append("</table>"+NEWLINE);
            sb.append("</td></tr>"+NEWLINE);
        }

        // add buttons
        sb.append("<tr><td style=\"text-align:right;\" colspan=\"2\">"+NEWLINE+
            "<table summary=\"layout\" cellspacing=\"3\">"+NEWLINE+
            "<tr><td><span class=\"button-blue\"><input type=\"submit\" name=\"okButton\" value=\" " + bundle.getString("Label_Ok") + " \"/></span></td>"+NEWLINE+
            "    <td><span class=\"button-blue\"><input type=\"button\" name=\"cancelButton\" value=\"" + bundle.getString("Label_Cancel") + "\" onclick=\"window.close();\"/>"+NEWLINE+
            "</span></td></tr></table>"+NEWLINE);
        sb.append("</td></tr></table>"+NEWLINE);

        sb.append("<input type=\"hidden\" name=\""+ORIGCOUNTRYLIST+"\" value=\""+newCtrySel.toString()+"\"/>"+NEWLINE);
        // keep building these parameters to maintain user ctry selections between genareaselection cascades
        sb.append("<input type=\"hidden\" name=\""+USER_DESELECTED+"\" value=\""+delCtrySel.toString()+"\"/>"+NEWLINE);
        sb.append("<input type=\"hidden\" name=\""+USER_SELECTED+"\" value=\""+addCtrySel.toString()+"\"/>"+NEWLINE);

        newCtrySel.clear();
        addCtrySel.clear();
        delCtrySel.clear();
        sb.append("</form>");
        list.dereference();

        return sb.toString();
    }

    // used to control country selection during cascade based on user selecting and deselecting countries
    private static class CtrySelection extends Hashtable
    {
        /**
		 *
		 */
		private static final long serialVersionUID = 1L;
		CtrySelection() {}
        CtrySelection(String[] ctryList) {
            if (ctryList != null) {
                for (int i=0; i<ctryList.length; i++) {
                    put(ctryList[i]);
                }
            }
        }
        CtrySelection(String ctryStr) {
            if (ctryStr != null) {
                StringTokenizer st = new StringTokenizer(ctryStr,"*");
                while(st.hasMoreTokens())
                {
                    String token = st.nextToken();
                    put(token);
                }
            }
        }
        void put(String flag) {
            if (!flag.equals(SEPARATOR)) {
                put(flag,flag);
            }
        }

        void buildChgLists(CtrySelection other, CtrySelection added, CtrySelection deleted) {
            // find newly selected values, if other doesn't have it, it was added
            Enumeration reqEnum = keys();
            while(reqEnum.hasMoreElements())
            {
                String ctryFlag = (String)reqEnum.nextElement();
                Object otherflag = other.remove(ctryFlag);
                if (otherflag == null) {
                    if (added.get(ctryFlag)==null) {
                        added.put(ctryFlag);
                    }
                }
                remove(ctryFlag);  //  remove here too
            }
            // find newly deselected values, if this didn't have it, it was deleted
            reqEnum = other.keys();
            while(reqEnum.hasMoreElements())
            {
                String ctryFlag = (String)reqEnum.nextElement();
                if (deleted.get(ctryFlag)==null) {
                    deleted.put(ctryFlag);
                }
                other.remove(ctryFlag);  //  remove there too
            }
        }
        public String toString() {
            StringBuffer sb = new StringBuffer();
            Enumeration reqEnum = keys();
            while(reqEnum.hasMoreElements())
            {
                String ctryFlag = (String)reqEnum.nextElement();
                if(sb.length()>0) {
                    sb.append("*");
                }
                sb.append(ctryFlag);
            }
            return sb.toString();
        }
    }

    // used to control display order for METAFlags
    private static class MetaFlagComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2) {
            return o1.toString().compareTo(o2.toString());
        }
    }

}
