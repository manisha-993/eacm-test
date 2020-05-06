// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2004, 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
//
package com.ibm.transform.oim.eacm.util;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;


/**********************************************************************************
* This class contains utility methods used by IGS Interwoven reports.
* CR0213034730 and CR0213034336
*
*/
// $Log: IGSUtils.java,v $
// Revision 1.10  2007/04/11 18:24:07  frosam
// WebKing Fixes
//
// Revision 1.9  2006/02/24 19:08:35  couto
// Fixed id duplication.
//
// Revision 1.8  2006/01/25 18:42:29  wendy
// AHE copyright
//
// Revision 1.7  2005/12/21 18:04:29  wendy
// More DQA layout table changes
//
// Revision 1.6  2005/12/21 14:12:33  wendy
// WebKing requires 'layout' in table summary attribute for layout tables
//
// Revision 1.5  2005/12/16 20:49:45  wendy
// DQA changes for th id attributes and td headers attributes
//
// Revision 1.4  2005/10/03 20:09:31  wendy
// Conform to new jtest config
//
// Revision 1.3  2005/09/25 22:13:26  wendy
// Convert to AHE format
//
// Revision 1.2  2005/09/12 16:54:43  wendy
// Add .wss to servlet name
//
// Revision 1.1  2005/09/08 18:17:02  couto
// Init OIM3.0b
//
// Revision 1.8  2005/07/14 13:43:41  couto
// Types and languages added to the script tags.
// Closed the input tags.
// Fix the javascript events character cases.
// The disabled inside the input tags were modified to disabled="true"
// All these changes were WebKing fixes.
//
// Revision 1.7  2005/06/13 13:05:43  wendy
// jtest chgs
//
// Revision 1.6  2004/07/07 15:57:21  wendy
// CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
//
// Revision 1.5  2004/04/12 18:34:02  wendy
// CR030404672 add check for 'required attr'
//
// Revision 1.4  2004/02/23 19:13:20  wendy
// IGS CR0219041917 and CR0220045238
//
// Revision 1.3  2004/02/17 19:40:46  wendy
// Removed '-' from display name CR2041
//
// Revision 1.2  2004/02/04 13:46:37  wendy
// Disable blob download buttons if role does not have access.
//
// Revision 1.1.1.1  2004/01/26 17:40:02  chris
// Latest East Coast Source
//
// Revision 1.1  2004/01/15 19:56:24  stimpsow
// Initial
//
public class IGSUtils
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2004, 2006  All Rights Reserved.";

    /* A utility class only contains static methods and static variables. Since an
    implicit default constructor is "public" and a utility class is not designed to
    be instantiated, the "public" default constructor of a utility class should be
    declared "private".  */
    private IGSUtils() {}

    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.10 $";
    /**  attrib desc col width */
    public static final int COL1_WIDTH_1UP = 20;
    /**  attrib value col width */
    public static final int COL2_WIDTH_1UP = 80;

    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);
    private static final String DESCRIPTION_ID="description";
    private static final String VALUE_ID="value";

    private static final String[] ANNOUNCEMENT_ATTR = new String[]
    {
        "ANNNUMBER","ANNTITLE","ANNTYPE","ANNDATE","ATAGLANCE"
    };
    private static final String[] AVAIL_ATTR = new String[]
    {
        "AVAILTYPE","EFFECTIVEDATE","GENAREASELECTION","COUNTRYLIST"
    };

    // CR030404672 output 'required attribute is missing' in bold red for the following entity/attr
    private static final Hashtable REQ_ANN_TBL;
    static
    {
        REQ_ANN_TBL = new Hashtable();
        REQ_ANN_TBL.put("ANNTITLE", "Label_ReqAttrMissing");
        REQ_ANN_TBL.put("ANNDATE", "Label_ReqAttrMissing");
        REQ_ANN_TBL.put("ATAGLANCE", "Label_ReqAttrMissing");
    }
    private static final Hashtable REQ_AVAIL_TBL;
    static
    {
        REQ_AVAIL_TBL = new Hashtable();
        REQ_AVAIL_TBL.put("AVAILTYPE", "Label_ReqAttrMissing");
        REQ_AVAIL_TBL.put("EFFECTIVEDATE", "Label_ReqAttrMissing");
        REQ_AVAIL_TBL.put("GENAREASELECTION", "Label_ReqAttrMissing");
        REQ_AVAIL_TBL.put("COUNTRYLIST", "Label_ReqAttrMissing");
    }
    /** most attr use 'Label_ReqAttrMissing' if missing but some are different, specify it here
    * changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
    */
    public static final Hashtable REQ_SOF_ATTR_TBL;
    static
    {
        REQ_SOF_ATTR_TBL = new Hashtable();
        REQ_SOF_ATTR_TBL.put("OFIDNUMBER", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("DESCRIPTION", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("OVERVIEWABSTRACT", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("BUSTECHISSUE", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("COMPETITIVEOF", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("CROSSELL", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("DELIVERYMETHOD", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("FEATUREBENEFIT", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("INDUSTRYSOLUTIONS", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("ITCAPABILITY", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("PREREQCOREQ", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("RESOURSKILLSET", "Label_ReqAttrMissing");
        //CR0527045730
        REQ_SOF_ATTR_TBL.put("DIFFEATURESBENEFITS", "Label_ReqAttrMissing");
        REQ_SOF_ATTR_TBL.put("VENDORCONSID","Label_OptionalButValuable");
        REQ_SOF_ATTR_TBL.put("WHYIBM","Label_OptionalButValuable");
        REQ_SOF_ATTR_TBL.put("BPFAQS","Label_OptionalButValuable");
        REQ_SOF_ATTR_TBL.put("BPAVAILINFRASTRCT","Label_RequiredThruBP");

        REQ_SOF_ATTR_TBL.put("SOFCATEGORY","Label_ReqAttrMissing"); // cr 5730 says this is already in the report, John Gardner said add it
    }
    /** most attr use 'Label_ReqAttrMissing' if missing but some are different, specify it here
    * changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
    */
    public static final Hashtable REQ_CMPNT_ATTR_TBL;
    static
    {
        REQ_CMPNT_ATTR_TBL = new Hashtable();
        REQ_CMPNT_ATTR_TBL.put("COMPONENTID", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("DESCRIPTION", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("OVERVIEWABSTRACT", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("BUSTECHISSUE", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("COMPETITIVEOF", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("CROSSELL", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("DELIVERYMETHOD", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("FEATUREBENEFIT", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("INDUSTRYSOLUTIONS", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("ITCAPABILITY", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("PREREQCOREQ", "Label_ReqAttrMissing");
        REQ_CMPNT_ATTR_TBL.put("RESOURSKILLSET", "Label_ReqAttrMissing");
        //CR0527046459
        REQ_CMPNT_ATTR_TBL.put("VENDORCONSID","Label_OptionalButValuable");
        REQ_CMPNT_ATTR_TBL.put("WHYIBM","Label_OptionalButValuable");
        REQ_CMPNT_ATTR_TBL.put("BPFAQS","Label_OptionalButValuable");
        REQ_CMPNT_ATTR_TBL.put("PURCHASINGPROCESS","Label_OptionalButValuable");
    }
    /** most attr use 'Label_ReqAttrMissing' if missing but some are different, specify it here
    * changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
    */
    public static final Hashtable REQ_FEATURE_ATTR_TBL;
    static
    {
        REQ_FEATURE_ATTR_TBL = new Hashtable();
        REQ_FEATURE_ATTR_TBL.put("FEATURENUMBER", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("DESCRIPTION", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("OVERVIEWABSTRACT", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("BUSTECHISSUE", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("COMPETITIVEOF", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("CROSSELL", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("DELIVERYMETHOD", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("FEATUREBENEFIT", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("INDUSTRYSOLUTIONS", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("ITCAPABILITY", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("PREREQCOREQ", "Label_ReqAttrMissing");
        REQ_FEATURE_ATTR_TBL.put("RESOURSKILLSET", "Label_ReqAttrMissing");
        //CR0527047046
        REQ_FEATURE_ATTR_TBL.put("WHYIBM","Label_ReqAttrMissing");
    }

    /** most attr use 'Label_ReqAttrMissing' if missing but some are different, specify it here
    * changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
    */
    public static final Hashtable REQ_PRICEFININFO_ATTR_TBL;
    static
    {
        REQ_PRICEFININFO_ATTR_TBL = new Hashtable();
        REQ_PRICEFININFO_ATTR_TBL.put("BPDISTRIBINCENTIVE", "Label_ReqAttrMissing");
        REQ_PRICEFININFO_ATTR_TBL.put("BPTIER1INCENTIVE", "Label_ReqAttrMissing");
        REQ_PRICEFININFO_ATTR_TBL.put("BPTIER2INCENTIVE", "Label_ReqAttrMissing");
        REQ_PRICEFININFO_ATTR_TBL.put("CHARGES", "Label_ReqAttrMissing");
        REQ_PRICEFININFO_ATTR_TBL.put("PRICINGDETAILS", "Label_OptionalButValuable");
    }
    /** most attr use 'Label_ReqAttrMissing' if missing but some are different, specify it here
    * changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
    */
    public static final Hashtable REQ_CHANNEL_ATTR_TBL;
    static
    {
        REQ_CHANNEL_ATTR_TBL = new Hashtable();
        REQ_CHANNEL_ATTR_TBL.put("CHANNELNAME", "Label_ReqAttrMissing");
        REQ_CHANNEL_ATTR_TBL.put("CHANNELTYPE", "Label_ReqAttrMissing");
        REQ_CHANNEL_ATTR_TBL.put("COUNTRYLIST", "Label_ReqAttrMissing");
    }
    /** most attr use 'Label_ReqAttrMissing' if missing but some are different, specify it here
    * changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
    */
    public static final Hashtable REQ_OFDEVLPROJ_ATTR_TBL;
    static
    {
        REQ_OFDEVLPROJ_ATTR_TBL = new Hashtable();
        REQ_OFDEVLPROJ_ATTR_TBL.put("PROJNUMBER", "Label_ReqAttrMissing");
    }
    /** most attr use 'Label_ReqAttrMissing' if missing but some are different, specify it here
    * changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
    */
    public static final Hashtable REQ_BPPROCESS_ATTR_TBL;
    static
    {
        REQ_BPPROCESS_ATTR_TBL = new Hashtable();
        REQ_BPPROCESS_ATTR_TBL.put("BPPROCESSNAME", "Label_RequiredIfSOFThruBP");
        REQ_BPPROCESS_ATTR_TBL.put("BPPROCESSDESCRIPTION", "Label_RequiredIfSOFThruBP");
    }

    /********************************************************************************
    * Get Entity and Attribute information as 2 column table, description is column 1, value is column 2
    *
    * @param noneStr String with NLS representation for 'None'
    * @param descStr String with NLS representation for 'Description'
    * @param valStr String with NLS representation for 'Value'
    * @param eVct Vector with entities
    * @param sectionTitle String with text for section
    * @param sectionCss String with style for section row
    * @param tableData String array with attribute codes
    * @param reqAttrTbl Hashtable with required attr codes, may be null
    * @param metaTbl Hashtable with navigate attributes for display name
    * @param dbCurrent Database
    * @param profile Profile
    * @param bundle ResourceBundle for NLS text
    * @returns String
    * @throws Exception
    */
    public static String getAttrAsRows(String noneStr, String descStr, String valStr,
        Vector eVct, String sectionTitle, String sectionCss, String[] tableData,
        Hashtable reqAttrTbl, Hashtable metaTbl, Database dbCurrent, Profile profile, ResourceBundle bundle)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();

        if (eVct.size()==0)
        {
            sb.append(getNoEntityContent(noneStr, sectionTitle, sectionCss));
        }
        else
        {
            sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Offering information\">"+NEWLINE);
            for(int ie=0; ie<eVct.size();ie++)
            {
                EntityItem entityItem = (EntityItem)eVct.elementAt(ie);
                int lineCnt=0;

                String displayName = sectionTitle+" "+
                    getDisplayName(entityItem, metaTbl, dbCurrent, profile," ",", ");
                sb.append("<!-- "+entityItem.getKey()+" -->"+NEWLINE);

                sb.append("<tr "+sectionCss+">"+NEWLINE+
                    "<th colspan=\"2\" id=\""+entityItem.getKey()+"\">"+displayName+"</th></tr>"+NEWLINE);

                sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE+
                    "<th style=\"text-align:center;\" id=\""+entityItem.getKey()+DESCRIPTION_ID+"\">"+descStr+"</th>"+
                    "<th style=\"text-align:center;\" id=\""+entityItem.getKey()+VALUE_ID+"\">"+valStr+"</th></tr>"+NEWLINE);

                for (int i=0; i<tableData.length; i++)
                {
                    String desc = PokUtils.getAttributeDescription(entityItem.getEntityGroup(),tableData[i],tableData[i]);
                    String notPopulated = getNotPopulatedStr(reqAttrTbl,tableData[i],bundle);
                    String value = PokUtils.getAttributeValue(entityItem, tableData[i],", ",notPopulated,true);
                    // CR0220045238
                    if (entityItem.getEntityType().equals("CMPNT")&& tableData[i].equals("MKTGNAME"))
                    {
                        String catName = PokUtils.getAttributeValue(entityItem, "ITSCMPNTCATNAME", ", ", "",false);
                        value = catName+" "+value;
                    }

                    sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                    sb.append("<td width=\""+COL1_WIDTH_1UP+"%\" headers=\""+entityItem.getKey()+DESCRIPTION_ID+" "+entityItem.getKey()+"\">"+desc+"</td>"+
                        "<td width=\""+COL2_WIDTH_1UP+"%\" headers=\""+entityItem.getKey()+VALUE_ID+" "+entityItem.getKey()+"\">"+value+"</td>"+NEWLINE);
                    sb.append("</tr>"+NEWLINE);
                }
            }
            sb.append("</table>"+NEWLINE);
        }

        return sb.toString();
    }

    /********************************************************************************
    * Get Entity and Attribute information as 2 column table, description is column 1, value is column 2
    *
    * @param noneStr String with NLS representation for 'None'
    * @param descStr String with NLS representation for 'Description'
    * @param valStr String with NLS representation for 'Value'
    * @param eGrp EntityGroup with entities
    * @param sectionTitle String with text for section
    * @param sectionCss String with style for section row
    * @param tableData String array with attribute codes
    * @param reqAttrTbl Hashtable with required attr codes, may be null
    * @param metaTbl Hashtable with navigate attributes for display name
    * @param dbCurrent Database
    * @param profile Profile
    * @param bundle ResourceBundle for NLS text
    * @returns String
    * @throws Exception
    */
    public static String getAttrAsRows(String noneStr, String descStr, String valStr,
            EntityGroup eGrp, String sectionTitle, String sectionCss, String[] tableData,
            Hashtable reqAttrTbl, Hashtable metaTbl, Database dbCurrent, Profile profile, ResourceBundle bundle)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        if (eGrp==null || eGrp.getEntityItemCount()==0)
        {
            sb.append(getNoEntityContent(noneStr, sectionTitle, sectionCss));
        }
        else
        {
            sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Offering information\">"+NEWLINE);
            for(int ie=0; ie<eGrp.getEntityItemCount();ie++)
            {
                EntityItem entityItem = eGrp.getEntityItem(ie);
                int lineCnt=0;
                String displayName = sectionTitle+" "+
                    getDisplayName(entityItem, metaTbl, dbCurrent, profile," ",", ");
                sb.append("<!-- "+entityItem.getKey()+" -->"+NEWLINE);

                sb.append("<tr "+sectionCss+">"+NEWLINE+
                    "<th colspan=\"2\" id=\""+entityItem.getKey()+"\">"+displayName+"</th></tr>"+NEWLINE);

                sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE+
                    "<th style=\"text-align:center;\" id=\""+entityItem.getKey()+DESCRIPTION_ID+"\">"+descStr+"</th>"+
                    "<th style=\"text-align:center;\" id=\""+entityItem.getKey()+VALUE_ID+"\">"+valStr+"</th></tr>"+NEWLINE);

                for (int i=0; i<tableData.length; i++)
                {
                    String desc = PokUtils.getAttributeDescription(eGrp,tableData[i],tableData[i]);
                    String notPopulated = getNotPopulatedStr(reqAttrTbl,tableData[i],bundle);
                    String value = PokUtils.getAttributeValue(entityItem, tableData[i],", ",notPopulated,true);
                    // CR0220045238
                    if (entityItem.getEntityType().equals("CMPNT")&& tableData[i].equals("MKTGNAME"))
                    {
                        String catName = PokUtils.getAttributeValue(entityItem, "ITSCMPNTCATNAME", ", ", "",false);
                        value = catName+" "+value;
                    }

                    sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                    sb.append("<td width=\""+COL1_WIDTH_1UP+"%\" headers=\""+entityItem.getKey()+DESCRIPTION_ID+" "+entityItem.getKey()+"\">"+desc+"</td>"+
                        "<td width=\""+COL2_WIDTH_1UP+"%\" headers=\""+entityItem.getKey()+VALUE_ID+" "+entityItem.getKey()+"\">"+value+"</td>"+NEWLINE);
                    sb.append("</tr>"+NEWLINE);
                }
            }
            sb.append("</table>"+NEWLINE);
        }

        return sb.toString();
    }

    /********************************************************************************
    * Get Entity and Attribute information with each column is an attribute value
    *
    * @param noneStr String with NLS representation for 'None'
    * @param eGrp EntityGroup with entities
    * @param sectionTitle String with text for section
    * @param sectionCss String with style for section row
    * @param tableData String array with attribute codes
    * @param reqAttrTbl Hashtable with required attr codes, may be null
    * @param colWidth int array with column widths
    * @param metaTbl Hashtable with navigate attributes for display name
    * @param dbCurrent Database
    * @param profile Profile
    * @param bundle ResourceBundle for NLS text
    * @returns String
    * @throws Exception
    */
    public static String getAttrAsCols(String noneStr, EntityGroup eGrp, String sectionTitle,
        String sectionCss, String[] tableData, Hashtable reqAttrTbl, int[] colWidth,
        Hashtable metaTbl, Database dbCurrent, Profile profile, ResourceBundle bundle)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        if (eGrp==null || eGrp.getEntityItemCount()==0)
        {
            sb.append(getNoEntityContent(noneStr, sectionTitle, sectionCss));
        }
        else
        {
            int lineCnt=0;
            String headerkey="";
            sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Offering information\">"+NEWLINE);
            for(int ie=0; ie<eGrp.getEntityItemCount();ie++)
            {
                EntityItem entityItem = eGrp.getEntityItem(ie);
                if (ie==0) // output once
                {
                    String displayName = sectionTitle+" "+
                        getDisplayName(entityItem, metaTbl, dbCurrent, profile," ",", ");

                    headerkey = entityItem.getKey();
                    sb.append("<tr "+sectionCss+">"+NEWLINE+
                        "<th colspan=\""+tableData.length+"\" id=\""+headerkey+"\">"+displayName+
                        "</th></tr>"+NEWLINE);

                    sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE);
                    for (int i=0; i<tableData.length; i++)
                    {
                        String desc = PokUtils.getAttributeDescription(eGrp,tableData[i],tableData[i]);
                        sb.append("<th style=\"text-align:center;\" width=\""+colWidth[i]+
                            "%\" id=\""+tableData[i]+"\">"+desc+"</th>");
                    }
                    sb.append("</tr>"+NEWLINE);
                }
                sb.append("<!-- "+entityItem.getKey()+" -->"+NEWLINE);

                sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                for (int i=0; i<tableData.length; i++)
                {
                    String notPopulated = getNotPopulatedStr(reqAttrTbl,tableData[i],bundle);
                    String value = PokUtils.getAttributeValue(entityItem, tableData[i],", ",notPopulated,true);
                    // CR0220045238
                    if (entityItem.getEntityType().equals("CMPNT")&& tableData[i].equals("MKTGNAME"))
                    {
                        String catName = PokUtils.getAttributeValue(entityItem, "ITSCMPNTCATNAME", ", ", "",false);
                        value = catName+" "+value;
                    }

                    sb.append("<td width=\""+colWidth[i]+"%\" headers=\""+tableData[i]+" "+headerkey+"\">"+value+"</td>");
                }
                sb.append("</tr>"+NEWLINE);
            }
            sb.append("</table>"+NEWLINE);
        }

        return sb.toString();
    }

    /********************************************************************************
    * Get Entity and relator information with one attribute per row
    *
    * @param noneStr String with NLS representation for 'None'
    * @param descStr String with NLS representation for 'Description'
    * @param valStr String with NLS representation for 'Value'
    * @param relGrp EntityGroup with relators
    * @param sectionTitle String with text for section
    * @param sectionCss String with style for section row
    * @param tableData String array with attribute codes
    * @param relData String array with relator attribute codes
    * @param reqAttrTbl Hashtable with required attr codes, may be null
    * @param metaTbl Hashtable with navigate attributes for display name
    * @param dbCurrent Database
    * @param profile Profile
    * @param bundle ResourceBundle for NLS text
    * @returns String
    * @throws Exception
    */
    public static String getChildInfo(String noneStr, String descStr, String valStr,
        EntityGroup relGrp, String sectionTitle, String sectionCss, String[] tableData, String[] relData,
        Hashtable reqAttrTbl, Hashtable metaTbl, Database dbCurrent, Profile profile, ResourceBundle bundle)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        if (relGrp==null || relGrp.getEntityItemCount()==0)
        {
            sb.append(getNoEntityContent(noneStr, sectionTitle, sectionCss));
        }
        else
        {
            int lineCnt=0;
            sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Offering child information\">"+NEWLINE);
            for(int ie=0; ie<relGrp.getEntityItemCount();ie++)
            {
                EntityItem relItem = relGrp.getEntityItem(ie);
                for (int ui=0; ui<relItem.getDownLinkCount(); ui++)
                {
                    EntityItem entityItem = (EntityItem)relItem.getDownLink(ui);
                    String displayName = sectionTitle+" "+
                        getDisplayName(entityItem, metaTbl, dbCurrent, profile," ",", ");
                    sb.append("<tr "+sectionCss+">"+NEWLINE+
                        "<th colspan=\"2\" id=\""+relItem.getKey()+entityItem.getKey()+"\">"+displayName+"</th></tr>"+NEWLINE);

                    sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE+
                        "<th style=\"text-align:center;\" id=\""+relItem.getKey()+entityItem.getKey()+DESCRIPTION_ID+"\">"+descStr+"</th>"+NEWLINE+
                        "<th style=\"text-align:center;\" id=\""+relItem.getKey()+entityItem.getKey()+VALUE_ID+"\">"+valStr+"</th></tr>"+NEWLINE);

                    sb.append("<!-- "+entityItem.getEntityType()+":"+entityItem.getEntityID()+" -->"+NEWLINE);
                    for (int i=0; i<tableData.length; i++)
                    {
                        String desc = PokUtils.getAttributeDescription(entityItem.getEntityGroup(),tableData[i],tableData[i]);
                        String notPopulated = getNotPopulatedStr(reqAttrTbl,tableData[i],bundle);
                        String value = PokUtils.getAttributeValue(entityItem, tableData[i],", ",notPopulated,true);
                        // CR0220045238
                        if (entityItem.getEntityType().equals("CMPNT")&& tableData[i].equals("MKTGNAME"))
                        {
                            String catName = PokUtils.getAttributeValue(entityItem, "ITSCMPNTCATNAME", ", ", "",false);
                            value = catName+" "+value;
                        }

                        sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                        sb.append("<td width=\""+COL1_WIDTH_1UP+"%\" headers=\""+relItem.getKey()+entityItem.getKey()+DESCRIPTION_ID+" "+relItem.getKey()+entityItem.getKey()+"\">"+desc+"</td>"+
                            "<td width=\""+COL2_WIDTH_1UP+"%\" headers=\""+relItem.getKey()+entityItem.getKey()+VALUE_ID+" "+relItem.getKey()+entityItem.getKey()+"\">"+value+"</td>"+NEWLINE);
                        sb.append("</tr>"+NEWLINE);
                    }
                    for (int i=0; i<relData.length; i++)
                    {
                        String desc = PokUtils.getAttributeDescription(relItem.getEntityGroup(),relData[i],relData[i]);
                        String value = PokUtils.getAttributeValue(relItem, relData[i],", ","&nbsp;",true);
                        sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                        sb.append("<td width=\""+COL1_WIDTH_1UP+"%\" headers=\""+relItem.getKey()+entityItem.getKey()+DESCRIPTION_ID+" "+relItem.getKey()+entityItem.getKey()+"\">"+desc+"</td>"+
                            "<td width=\""+COL2_WIDTH_1UP+"%\" headers=\""+relItem.getKey()+entityItem.getKey()+VALUE_ID+" "+relItem.getKey()+entityItem.getKey()+"\">"+value+"</td>"+NEWLINE);
                        sb.append("</tr>"+NEWLINE);
                    }
                }
            }
            sb.append("</table>"+NEWLINE);
        }

        return sb.toString();
    }

    /********************************************************************************
    * Get announcement information with Avails
    *
    * @param noneStr String with NLS representation for 'None'
    * @param descStr String with NLS representation for 'Description'
    * @param valStr String with NLS representation for 'Value'
    * @param eGrp EntityGroup with ANNOUNCEMENT entities
    * @param sectionTitle String with text for Announcement section
    * @param sectionCss String with style for Announcement section row
    * @param avlSectionTitle String with text for Avail section
    * @param avlSectionCss String with style for Avail section row
    * @param metaTbl Hashtable with navigate attributes for display name
    * @param dbCurrent Database
    * @param profile Profile
    * @param bundle ResourceBundle for NLS text
    * @returns String
    * @throws Exception
    */
    public static String getAnnouncements(String noneStr, String descStr, String valStr,
            EntityGroup eGrp, String sectionTitle, String sectionCss, String avlSectionTitle, String avlSectionCss,
            Hashtable metaTbl, Database dbCurrent, Profile profile, ResourceBundle bundle)
        throws Exception
    {
        StringBuffer sb = new StringBuffer();
        if (eGrp==null || eGrp.getEntityItemCount()==0)
        {
            sb.append(getNoEntityContent(noneStr, sectionTitle, sectionCss));
        }
        else
        {
            for(int ie=0; ie<eGrp.getEntityItemCount();ie++)
            {
				Vector availVct = null;
				EntityItem annItem = eGrp.getEntityItem(ie);  // ANNOUNCEMENT
				int lineCnt=0;
				String displayName = sectionTitle+" "+
					getDisplayName(annItem, metaTbl, dbCurrent, profile," ",", ");
            	
                sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Announcement information\">"+NEWLINE);

                sb.append("<!-- ANNOUNCEMENT:"+annItem.getEntityID()+"-->"+NEWLINE);
                sb.append("<tr "+sectionCss+">"+NEWLINE+
                    "<th colspan=\"2\" id=\""+annItem.getKey()+"\">"+displayName+"</th></tr>"+NEWLINE);

                sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE+
                    "<th style=\"text-align:center;\" id=\""+annItem.getKey()+DESCRIPTION_ID+"\">"+descStr+"</th>"+NEWLINE+
                    "<th style=\"text-align:center;\" id=\""+annItem.getKey()+VALUE_ID+"\">"+valStr+"</th></tr>"+NEWLINE);

                // layout as 2 cols, desc and value
                for (int i=0; i<ANNOUNCEMENT_ATTR.length; i++)
                {
                    String desc = PokUtils.getAttributeDescription(eGrp,ANNOUNCEMENT_ATTR[i],ANNOUNCEMENT_ATTR[i]);
                    String notPopulated = getNotPopulatedStr(REQ_ANN_TBL,
                            ANNOUNCEMENT_ATTR[i],bundle);
                    String value = PokUtils.getAttributeValue(annItem, ANNOUNCEMENT_ATTR[i],", ",notPopulated,true);
                    sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                    sb.append("<td width=\""+COL1_WIDTH_1UP+"%\" headers=\""+annItem.getKey()+DESCRIPTION_ID+" "+annItem.getKey()+"\">"+desc+"</td>"+
                        "<td width=\""+COL2_WIDTH_1UP+"%\" headers=\""+annItem.getKey()+VALUE_ID+" "+annItem.getKey()+"\">"+value+"</td>"+NEWLINE);
                    sb.append("</tr>"+NEWLINE);
                }
                lineCnt=0;

                sb.append("</table>"+NEWLINE); // web acessibility can't nest tables
                // output avail in 4 col table
                // get AVAILS
                availVct = PokUtils.getAllLinkedEntities(annItem, "AVAILANNA", "AVAIL");
                if (availVct.size()>0) // VE is rootTypeAVAIL and ANN is thru association so if got this far AVAILs exist
                {
                    String headerkey="";
                    sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Avail information\">"+NEWLINE);
                    for (int v=0; v<availVct.size(); v++)
                    {
                        EntityItem availItem = (EntityItem)availVct.elementAt(v);
                        if (v==0) // output once
                        {
                            headerkey = availItem.getKey();
                            sb.append("<tr "+avlSectionCss+">"+NEWLINE+
                                "<th colspan=\""+AVAIL_ATTR.length+"\" id=\""+headerkey+"\">"+
                                avlSectionTitle+"</th></tr>"+NEWLINE);
                            sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE);
                            for (int i=0; i<AVAIL_ATTR.length; i++)
                            {
                                String desc = PokUtils.getAttributeDescription(availItem.getEntityGroup(),AVAIL_ATTR[i],AVAIL_ATTR[i]);
                                sb.append("<th style=\"text-align:center;\" width=\"25%\" id=\""+AVAIL_ATTR[i]+"\">"+
                                        desc+"</th>");
                            }
                            sb.append("</tr>"+NEWLINE); // end label row
                        }
                        sb.append("<!-- "+availItem.getEntityType()+":"+availItem.getEntityID()+" "+
                            getDisplayName(availItem, metaTbl, dbCurrent, profile," ",", ")+"-->"+NEWLINE);
                        sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                        for (int i=0; i<AVAIL_ATTR.length; i++)
                        {
                            String notPopulated = getNotPopulatedStr(REQ_AVAIL_TBL, AVAIL_ATTR[i],bundle);
                            String value = PokUtils.getAttributeValue(availItem, AVAIL_ATTR[i],", ",notPopulated,true);
                            sb.append("<td width=\"25%\" headers=\""+AVAIL_ATTR[i]+" "+headerkey+"\">"+value+"</td>");
                        }
                        sb.append("</tr>"+NEWLINE);
                    }
                    availVct.clear();
                    sb.append("</table>"+NEWLINE);
                }

            } // end each ANN
        } // end ANN found


        return sb.toString();
    }

    /********************************************************************************
    * Get information about the sales contact using the xxxSALESCNTCTOP relator and OP entity
    *
    * @param nameStr String with NLS representation for 'Name'
    * @param noneStr String with NLS representation for 'None'
    * @param sectionTitle String with text for section
    * @param sectionCss String with style for section row
    * @param relGrp EntityGroup with xxxSALESCNTCTOP relators
    * @param bundle ResourceBundle for NLS text
    * @returns String
    */
    public static String getSalesContactInfo(String nameStr, String noneStr, String sectionTitle,
        String sectionCss, EntityGroup relGrp, ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer();
        if (relGrp==null || relGrp.getEntityItemCount()==0)
        {
            sb.append(getNoEntityContent(noneStr, sectionTitle, sectionCss));
        }
        else
        {
            String reqAttrMissing = null;
            String reqMsg = "Required attribute is Missing";
            int lineCnt=0;
            sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"Sales contact information\">"+NEWLINE);
            sb.append("<tr "+sectionCss+">"+NEWLINE+
                "<th colspan=\"5\" id=\"section\">"+sectionTitle+"</th></tr>"+NEWLINE);

            try{
                reqMsg = bundle.getString("Label_ReqAttrMissing");
            }catch(Exception e) {
                System.out.println(e.getMessage());}
            reqAttrMissing = "<span class=\"igs_req_attr\">"+reqMsg+"</span>";

            for(int ie=0; ie<relGrp.getEntityItemCount();ie++)
            {
                EntityItem relItem = relGrp.getEntityItem(ie); // SOFSALESCNTCTOP
                for (int ui=0; ui<relItem.getDownLinkCount(); ui++)
                {
                    String value = null;
                    EntityItem entityItem = (EntityItem)relItem.getDownLink(ui); // OP
                    if (ie==0) // output once
                    {
                        String desc = PokUtils.getAttributeDescription(entityItem.getEntityGroup(),"JOBTITLE","JOBTITLE");
                        sb.append("<tr "+PokUtils.getColumnHeaderRowCSS()+">"+NEWLINE+
                            "<th style=\"text-align:center;\" id=\"name\">"+nameStr+"</th>"+NEWLINE); // Name
                        sb.append("<th style=\"text-align:center;\" id=\"jobtitle\">"+desc+"</th>"+NEWLINE); // jobtitle
                        desc = PokUtils.getAttributeDescription(entityItem.getEntityGroup(),"USERTOKEN","USERTOKEN");
                        sb.append("<th style=\"text-align:center;\" id=\"usertoken\">"+desc+"</th>"+NEWLINE); // internet
                        desc = PokUtils.getAttributeDescription(relGrp,"GENAREASELECTION","GENAREASELECTION");
                        sb.append("<th style=\"text-align:center;\" id=\"genarea\">"+desc+"</th>"+NEWLINE); // genarea
                        desc = PokUtils.getAttributeDescription(relGrp,"COUNTRYLIST","COUNTRYLIST");
                        sb.append("<th style=\"text-align:center;\" id=\"country\">"+desc+"</th>"+NEWLINE); // COUNTRYLIST
                        sb.append("</tr>"+NEWLINE);
                    }
                    sb.append("<!-- "+entityItem.getEntityType()+":"+entityItem.getEntityID()+" -->"+NEWLINE);

//changes for CR0527045730 for SOF, CR0527046459 for CMPNT, CR0527047046 for FEATURE
                    // SOF and CMPNT now have required msg for UserName, USERTOKEN, GENAREASELECTION and COUNTRYLIST
// John Gardner says FEATURE also has this requirement

                    value = PokUtils.getAttributeValue(entityItem, "FIRSTNAME","","",true)+" "+
                        PokUtils.getAttributeValue(entityItem, "MIDDLENAME","","",true)+" "+
                        PokUtils.getAttributeValue(entityItem, "LASTNAME","","&nbsp;",true);
                    if (value.trim().equals("&nbsp;")) {
                        value = reqAttrMissing; };

                    sb.append("<tr class=\""+(lineCnt++%2!=0?"even":"odd")+"\">"+NEWLINE);
                    sb.append("<td headers=\"name section\">"+value+"</td>"+NEWLINE); // name
                    value = PokUtils.getAttributeValue(entityItem,"JOBTITLE",", ","&nbsp;",true);
                    sb.append("<td headers=\"jobtitle section\">"+value+"</td>"+NEWLINE); // job
                    value = PokUtils.getAttributeValue(entityItem,"USERTOKEN",", ",reqAttrMissing,true);
                    sb.append("<td headers=\"usertoken section\">"+value+"</td>"+NEWLINE); // USERTOKEN
                    value = PokUtils.getAttributeValue(relItem,"GENAREASELECTION",", ",reqAttrMissing,true);
                    sb.append("<td headers=\"genarea section\">"+value+"</td>"+NEWLINE); // genarea
                    value = PokUtils.getAttributeValue(relItem,"COUNTRYLIST",", ",reqAttrMissing,true);
                    sb.append("<td headers=\"country section\">"+value+"</td>"+NEWLINE); // COUNTRYLIST
                    sb.append("</tr>"+NEWLINE);
                }
            }
			sb.append("</table>"+NEWLINE);
        }

        return sb.toString();
    }

    /********************************************************************************
    * Get No entities found table body content
    *
    * @param noneStr String with NLS representation for 'None'
    * @param sectionTitle String with text for section
    * @param sectionCss String with style for section row
    * @returns String
    */
    private static String getNoEntityContent(String noneStr, String sectionTitle, String sectionCss)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\" class=\"basic-table\" summary=\"layout\">"+NEWLINE);
        sb.append("<tr "+sectionCss+"><td><b>"+sectionTitle+"</b></td></tr>"+NEWLINE);
        sb.append("<tr class=\"odd\"><td>"+noneStr+"</td></tr>"+NEWLINE);
        sb.append("</table>"+NEWLINE);

        return sb.toString();
    }

    /********************************************************************************
    * If an atttribute is required by IGS, and it is missing, output the appropriate error msg
    *
    * @param reqAttrTbl Hashtable with list of required attr and its resource
    * @param attrCode String with attribute code
    * @param bundle ResourceBundle for NLS text
    * @returns String
    */
    private static String getNotPopulatedStr(Hashtable reqAttrTbl, String attrCode, ResourceBundle bundle)
    {
        String notpopStr="&nbsp;";
        if (reqAttrTbl!=null)
        {
            String missingRes = (String)reqAttrTbl.get(attrCode);
            if (missingRes !=null) {
                notpopStr= "<span class=\"igs_req_attr\">"+bundle.getString(missingRes)+"</span>"; }

        }
        return notpopStr;
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
    * @throws Exception
    */
    public static String getDisplayName(EntityItem entityItem, Hashtable metaTbl,
        Database dbCurrent, Profile profile, String attrDelimiter, String flagDelimiter)
        throws Exception
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

    /********************************************************************************
    * Get Scripts for xml download verification and getting a blob
    *
    * @param bundle ResourceBundle for NLS text
    * @returns String
    */
    public static String getScript(ResourceBundle bundle)
    {
        StringBuffer sb = new StringBuffer("<script type=\"text/javascript\" language=\"javascript\">"+NEWLINE);
        sb.append("<!--"+NEWLINE+" function checkInput(e) {"+NEWLINE);
        sb.append("if (e.value.length ==0) {"+NEWLINE);
        //sb.append("   alert(\"Please enter a file name.\");"+NEWLINE);
        sb.append(" alert(\""+bundle.getString("Error_EnterFileName")+"\");"+NEWLINE);
        sb.append(" e.focus();"+NEWLINE);
        sb.append(" return false; }"+NEWLINE);
        sb.append("else {"+NEWLINE);
        sb.append(" var blankcnt=0;"+NEWLINE);
        sb.append(" for (cntr=0; cntr<e.value.length; cntr++) {"+NEWLINE);
        sb.append("  if (e.value.charAt(cntr)==\" \") {"+NEWLINE+"   blankcnt++;"+NEWLINE+"  }"+NEWLINE+" }"+NEWLINE);
        sb.append(" if (blankcnt==e.value.length) {"+NEWLINE);
    //  sb.append("   alert(\"Blanks are invalid.  Please enter a file name.\");"+NEWLINE);
        sb.append("   alert(\""+bundle.getString("Error_InvalidBlanksFN")+"\");"+NEWLINE);
        sb.append("   e.focus();"+NEWLINE);
        sb.append("   e.select();"+NEWLINE);
        sb.append("   return false;"+NEWLINE+" }"+NEWLINE+"}"+NEWLINE);
        sb.append(" return true;}"+NEWLINE);
        sb.append(" function getBlob(strAttributeCode) {"+NEWLINE+
            "   document.blobForm.attributeCode.value = strAttributeCode;"+NEWLINE+
            "   document.blobForm.submit();}"+NEWLINE);
        sb.append("//  -->"+NEWLINE+"</script>"+NEWLINE);

        return sb.toString();
    }

    /********************************************************************************
    * Get xml download form
    *
    * @param bundle ResourceBundle for NLS text
    * @param applicationName String with application name for action url
    * @param entityType String with entity type
    * @param entityId int
    * @param debugStr String used as flag for debug output in the XML
    * @returns String
    */
    public static String getXMLDownloadForm(ResourceBundle bundle, String applicationName,
        String entityType, int entityId, String debugStr)
    {
        StringBuffer sb = new StringBuffer();
        // get the xml for user download
        sb.append("<form action=\"/"+applicationName+"/PokXMLDownload.wss\" name=\"xmldownload\" method=\"post\" "+NEWLINE);
        sb.append("onsubmit=\"return checkInput(this.filename);\">"+NEWLINE);
        sb.append("<input type=\"hidden\" name=\"entityType\" value=\"" + entityType +"\"/>"+NEWLINE);
        sb.append("<input type=\"hidden\" name=\"entityID\" value=\"" + entityId +"\"/>"+NEWLINE);
        sb.append("<input type=\"hidden\" name=\"downloadType\" value=\"igsxml\"/>"+NEWLINE);
        if (debugStr !=null) {
            sb.append("<input type=\"hidden\" name=\"debug\" value=\""+debugStr+"\"/>"+NEWLINE); }

        sb.append("<table cellpadding=\"2\" summary=\"layout\">"+NEWLINE);
        sb.append("<tr>"+NEWLINE+"<td>"+NEWLINE);
    //      sb.append("Please enter a file name to use for the download: </td>"+NEWLINE);
        sb.append(bundle.getString("Label_EnterFileName")+"</td>"+NEWLINE);
        sb.append("<td>"+NEWLINE);
        sb.append("<label for=\"filename\"> </label><input type=\"text\" id=\"filename\" name=\"filename\" value=\"\"/>"+NEWLINE);
    //  sb.append("<input type=\"Submit\" name=\"download\" value=\"Download XML\">"+NEWLINE);
        sb.append("<input type=\"submit\" name=\"download\" value=\""+bundle.getString("Label_DownloadXML")+"\"/>"+NEWLINE);
        sb.append("</td>  </tr></table>"+NEWLINE+"</form>"+NEWLINE);

        return sb.toString();
    }

    /********************************************************************************
    * Get blob download form
    *
    * @param bundle ResourceBundle for NLS text
    * @param applicationName String with application name for action url
    * @param entityItem EntityItem to get blobs for
    * @param attrData String array with Blob attr codes
    * @returns String
    */
    public static String getBlobDownloadForm(ResourceBundle bundle, String applicationName, EntityItem entityItem,
        String []attrData)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<form action=\"/"+applicationName+"/PokXMLDownload.wss\" name=\"blobForm\" method=\"post\"> "+NEWLINE);
        sb.append("<input type=\"hidden\" name=\"entityType\" value=\"" + entityItem.getEntityType() +"\"/>"+NEWLINE);
        sb.append("<input type=\"hidden\" name=\"entityID\" value=\"" + entityItem.getEntityID() +"\"/>"+NEWLINE);
        sb.append("<input type=\"hidden\" name=\"downloadType\" value=\"blob\"/>"+NEWLINE);
        sb.append("<input type=\"hidden\" name=\"attributeCode\" value=\"\"/>"+NEWLINE);

        sb.append("<table summary=\"layout\">"+NEWLINE);
        sb.append("<tr>"+NEWLINE);
        for (int i=0; i<attrData.length; i++)
        {
            String desc = PokUtils.getAttributeDescription(entityItem.getEntityGroup(),attrData[i],attrData[i]);
            String value = PokUtils.getAttributeValue(entityItem, attrData[i],"",null);
            EANMetaAttribute metaAttr = entityItem.getEntityGroup().getMetaAttribute(attrData[i]);
            if (metaAttr==null) {// role does not have access to this attribute
                value = null;}

            sb.append("<td><input type=\"button\" name=\""+attrData[i]+"\" value=\""+
                bundle.getString("Label_Download")+""+NEWLINE+desc+"\" onclick=\"javascript:getBlob('"+attrData[i]+"')\" "+
                (value==null?"disabled=\"disabled\"":"")+"/></td>"+NEWLINE);
        }
        sb.append("</tr></table>"+NEWLINE);
        sb.append("</form>"+NEWLINE);

        return sb.toString();
    }

}
