// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.catalog;

import java.util.*;
import java.io.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 * This class supports SG VAM Catalog reports
 *
 */
// $Log: SGVAMCatalog.java,v $
// Revision 1.2  2006/10/02 18:57:54  wendy
// Pass application name for images
//
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
public class SGVAMCatalog extends SGCatalog
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";

    private Hashtable vamattrTbl = new Hashtable(); // key=column num, value=vector of seq
    private EntityItem seriesItem, imageItem, featBenItem;

    private static final String[] CATLGPUB_ATTR = new String[]
       {"CATOFFTYPE","CATMACHTYPE","CATMODEL","CATSEOID","CATLGMKTGDESC","CATSALESSTATUS","PUBFROM","PUBTO",
       "CATHIDE","CATAUDIENCE","OFFCOUNTRY","CATACTIVE"};

    /********************************************************************************
    * Constructor for VAM report
    *
    *@param  list       EntityList for data
    *@param  nls        int id for selected NLS
    *@param  ctry       String flagcode for selected country
    *@param  code       String ctryCode for selected country
    *@param  aud        String flagcode for selected audience
    *@param  curtime    String current time in ISO format
    */
    public SGVAMCatalog(EntityList list,int nls, String ctry, String code, String aud, String curtime)
    {
		super(list,nls,ctry, code, aud, curtime);
    }

    /********************************************************************************
    * Release memory
    */
    public void dereference()
    {
		super.dereference();
		featBenItem = null;
		imageItem = null;
		seriesItem = null;
        for (Enumeration e = vamattrTbl.elements(); e.hasMoreElements();)
        {
            Vector vct = (Vector)e.nextElement();
            for (int i=0; i<vct.size(); i++){
				((VamAttrTableName)vct.elementAt(i)).dereference();
			}
            vct.clear();
        }
		vamattrTbl.clear();
		vamattrTbl = null;
    }

    /********************************************************************************
    * Get VAM info for display of this catalog
    *@param out javax.servlet.jsp.JspWriter for output
    *@param applicationName
    *@throws java.io.IOException
    *@throws java.sql.SQLException
    */
    protected void outputReport(javax.servlet.jsp.JspWriter out, String applicationName) throws java.sql.SQLException, java.io.IOException
    {
		findAllOfferings(true,out);

		// get series heading and image in one table
		outputSeriesImageTable(out,applicationName);
		out.flush();

		// get feature benefit and build your own models in one table
		outputFBAndMdlTable(out);

		out.println("<!--getVAMInfo() setup layout -->");
		setupVAMLayout(out);
		out.flush();

		// get lseo and lseobundle (all models) in one table (tables nest for display)
		out.println("<!--getVAMInfo() begin all models -->");
		outputVAMTable(out);
		out.flush();

		// get inactive model, lseo and lseobundle in one table (tables nest for display)
		out.println("<!--getVAMInfo() begin all inactive models -->");
		outputInactiveTable(out);
    }

    /********************************************************************************
    * output VAM table
    *@param out javax.servlet.jsp.JspWriter for output
    *@throws java.io.IOException
    *@throws java.sql.SQLException
    */
    private void outputVAMTable(javax.servlet.jsp.JspWriter out) throws java.sql.SQLException, java.io.IOException
    {
        out.println("<table width=\"700\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >");
        out.println("<tr><td colspan=\"3\" bgcolor=\"#EEEEEE\" height=\"1\"></td></tr>");
        out.println("<tr><td align=\"left\" class=\"tblue\" colspan=\"3\">&nbsp;<b>All models</b></td></tr>");
        out.println("<tr><td>");

        out.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        getVAMHeader(out);
        if (getActiveOfferings().size()==0){
            out.println("<tr><td colspan=\"11\">No Active Offerings found</td></tr>");
        }else{
            for (int i=0; i<getActiveOfferings().size(); i++){
                Vector featIdVct, tmp;
                SGOffering theOff = (SGOffering)getActiveOfferings().elementAt(i);
                StringBuffer titleSb = new StringBuffer(theOff.toString());
                Vector catlgpubVct = theOff.getValidCatlgpub();  // should be only one in here, wont get here if 0
                String multmsg="";
                String price = getPrice(theOff.getPartNumber(), theOff.getVarCondType(), out);

                if (catlgpubVct.size()>1){
                    multmsg="<br />"+getErrorHtml("Multiple active CATLGPUB found");
                }
                // build title
                for (int v=0; v<catlgpubVct.size(); v++){
                    EntityItem catlg = (EntityItem)catlgpubVct.elementAt(v);
                    titleSb.append("/"+catlg.getKey());
                }

                if (i>0){ // add row separator
                    out.println("<tr><td colspan=\"11\">&nbsp;</td></tr>");
                    out.println("<tr><td valign=\"top\" colspan=\"11\" height=\"4\" class=\"bgdash\"></td></tr>");
                }
                out.println("<tr align=\"left\" valign=\"top\"><th title=\""+titleSb+"\">"+theOff.getMktDesc()+
                    multmsg+"<br /></th>");

                out.println(SPACER_COL);
                out.println("<td><span class=\"price\">"+price+"</span><br /></td>");

                // columns 3-6 are built from other tables

                // get all featentityid for these LSEO
                featIdVct = getFeatIds("LSEO", theOff.getLseoIds(), out);

                // get all featentityid for these WWSEO
                tmp = getFeatIds("WWSEO", theOff.getWwseoIds(), out);
                featIdVct.addAll(tmp);
                tmp.clear();

                for (int ic=1; ic<=4; ic++) {
                    String colVal = "";
                    String title="";
                    String tooltip=null;
                    String [] cellval;
                    if (theOff.getLseoIds().length==0) {  // this means the offering didn't have any LSEOs
                        colVal= getErrorHtml("No LSEO");
                    }
                    else{
                        cellval = getVAMCell(theOff,featIdVct,(Vector)vamattrTbl.get(""+ic), out);
                        colVal = cellval[0];
                        tooltip= cellval[1];
                        cellval[0]=null;
                        cellval[1]=null;
                    }
                    out.println(SPACER_COL);
                    if (tooltip!=null){
                        title=" title=\""+tooltip+"\"";
                    }
                	String pltStr = "<td headers=\"colseq"+ic+"\""+title+">"+colVal+"<br /></td>";
    				if(validate(pltStr))
                     out.println();
                }

                out.println("</tr>");
                featIdVct.clear();
            }
        }
        out.println("</table>");

        out.print("</td><td>&nbsp;</td>");
        out.println("<td width=\"1\" height=\"1\" bgcolor=\"#EEEEEE\"></td></tr>");
        out.println("</table>");
    }

    /********************************************************************************
    * VIII. Inactive Offerings
    * This should appear at the end of the report with the heading 'Inactive Offerings'.
    * It is a list of offerings identified earlier in the criteria and should display attributes from CATLGPUB as follows:
    * CATOFFTYPE
    * CATMACHTYPE
    * CATMODEL
    * CATSEOID
    * CATLGMKTGDESC
    * CATSALESSTATUS
    * PUBFROM
    * PUBTO
    *
    * output inactive offerings table and those without CATLGPUB
    *@param out javax.servlet.jsp.JspWriter for output
    *@throws java.io.IOException
    */
    private void outputInactiveTable(javax.servlet.jsp.JspWriter out) throws java.io.IOException
    {
        int colspan = CATLGPUB_ATTR.length+CATLGPUB_ATTR.length-1;
        out.println("<br /><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" >");
        out.println("<tr><td colspan=\"3\" height=\"1\" bgcolor=\"#EEEEEE\" ></td></tr>");
        out.println("<tr><td align=\"left\" class=\"tblue\" colspan=\"3\">&nbsp;<b>Inactive Offerings</b></td></tr>");
        out.println("<tr><td >");

        out.println("<table  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        getInactiveHeader(out);
        if (getInactiveOfferings().size()==0){
            out.println("<tr><td colspan=\""+colspan+"\">No Inactive Offerings found</td></tr>");
        }else{
            for (int i=0; i<getInactiveOfferings().size(); i++){
                SGOffering theOff = (SGOffering)getInactiveOfferings().elementAt(i);
                theOff.setInactiveSort();
            }
            Collections.sort(getInactiveOfferings());// sort on type and pn

            for (int i=0; i<getInactiveOfferings().size(); i++){
                SGOffering theOff = (SGOffering)getInactiveOfferings().elementAt(i);
                Vector invalCatlgVct = theOff.getInvalidCatlgpub();
                /*
                If no matching CATLGPUB was found, then display attributes in the preceding order as follows:
                    CATOFFTYPE: 'Model'
                    CATMACHTYPE: MACHTYPEATR
                    CATMODEL: MODELATR

                    CATOFFTYPE: 'LSEO'
                    CATSEOID: SEOID

                    CATOFFTYPE: 'LSEOBUNDLE'
                    CATSEOID: SEOID
                */
                if (invalCatlgVct.size()==0){
                    int tmpspan=colspan;
                    EntityItem offitem = theOff.getOffering();
                    if (i>0){ // add row separator
                        out.println("<tr><td colspan=\""+colspan+"\">&nbsp;</td></tr>");
                        out.println("<tr><td valign=\"top\" colspan=\""+colspan+"\" height=\"4\" class=\"bgdash\"></td></tr>");
                    }
                    out.println("<tr align=\"left\" valign=\"top\">");
                    if (offitem.getEntityType().equals("MODEL")){
                        out.println("<td headers=\"CATOFFTYPE\" title=\""+theOff+"\">Model</td>");
                        out.println(SPACER_COL);
                        out.println("<td headers=\"CATMACHTYPE\">"+
                            PokUtils.getAttributeValue(offitem, "MACHTYPEATR", "", "&nbsp;")+"</td>");
                        out.println(SPACER_COL);
                        out.println("<td headers=\"CATMODEL\">"+
                            PokUtils.getAttributeValue(offitem, "MODELATR", "", "&nbsp;")+"</td>");
                        out.println(SPACER_COL);
                        out.println("<td headers=\"CATSEOID\">&nbsp;</td>");
                        out.println(SPACER_COL);
                    }else{
                        out.println("<td headers=\"CATOFFTYPE\" title=\""+theOff+"\">"+offitem.getEntityType()+"</td>");
                        out.println(SPACER_COL);
                        out.println("<td headers=\"CATMACHTYPE\">&nbsp;</td>");
                        out.println(SPACER_COL);
                        out.println("<td headers=\"CATMODEL\">&nbsp;</td>");
                        out.println(SPACER_COL);
                        out.println("<td headers=\"CATSEOID\">"+
                            PokUtils.getAttributeValue(offitem, "SEOID", "", "&nbsp;")+"</td>");
                        out.println(SPACER_COL);
                    }
                    tmpspan-=8;
                    out.println("<td colspan=\""+(tmpspan)+"\">"+
                        getErrorHtml("No "+getEntityList().getEntityGroup("CATLGPUB")+" found")+"</td>");
                    out.println("</tr>");
                }else{
                    for (int v=0; v<invalCatlgVct.size(); v++){
                        EntityItem catlgItem = (EntityItem)invalCatlgVct.elementAt(v);
                        String title = "title=\""+theOff+"/"+catlgItem.getKey()+"\"";

                        if (i>0){ // add row separator
                            out.println("<tr><td colspan=\""+colspan+"\">&nbsp;</td></tr>");
                            out.println("<tr><td valign=\"top\" colspan=\""+colspan+"\" height=\"4\" class=\"bgdash\">"+
                                "</td></tr>");
                        }
                        out.println("<tr align=\"left\" valign=\"top\">");

                        for (int a=0; a<CATLGPUB_ATTR.length; a++) {
                            String attrCode = CATLGPUB_ATTR[a];
                            if (a==0){
                                out.print("<td headers=\""+attrCode+"\" "+title+">");
                            }else{
                                out.print("<td headers=\""+attrCode+"\">");
                            }
                            out.println(PokUtils.getAttributeValue(catlgItem, attrCode, ", ", "&nbsp;")+"</td>");
                            if ((a+1)<CATLGPUB_ATTR.length){
                                out.println(SPACER_COL);
                            }
                        }

                        out.println("</tr>");
                    }
                }
            }
        }
        out.println("</table>");

        out.print("</td><td>&nbsp;</td>");
        out.println("<td width=\"1\" height=\"1\" bgcolor=\"#EEEEEE\"></td></tr>");
        out.println("</table>");
    }

    /********************************************************************************
    * Build inactive table headers
    *@param out javax.servlet.jsp.JspWriter for output
    *@throws IOException
    */
    private void getInactiveHeader(javax.servlet.jsp.JspWriter out) throws IOException
    {
        EntityGroup catlgrp = getEntityList().getEntityGroup("CATLGPUB");
        int colspan = CATLGPUB_ATTR.length+CATLGPUB_ATTR.length-1;
        out.println("<tr><td colspan=\""+colspan+"\" height=\"4\" bgcolor=\"#EEEEEE\"></td></tr>");
        out.println("<tr valign=\"top\" bgcolor=\"#EEEEEE\">");
        for (int i=0; i<CATLGPUB_ATTR.length; i++) {
            String attrCode = CATLGPUB_ATTR[i];
            out.println("<th id=\""+attrCode+"\">"+
                PokUtils.getAttributeDescription(catlgrp,CATLGPUB_ATTR[i],CATLGPUB_ATTR[i])
                +"</th>");
            if ((i+1)<CATLGPUB_ATTR.length){
                out.println(SPACER_DESC);
            }
        }
        out.println("</tr>");

        out.println("<tr><td colspan=\""+colspan+"\" height=\"6\" bgcolor=\"#EEEEEE\"></td></tr>");
    }

    /********************************************************************************
    * The 'Legal Series Name' LGLSERNAM is shown first followed by the image.
    * The 'Legal Series Name' is found as follows:
    *   Association CATNAVPROJA
    *   Association PROJSERA
    *
    *   Result:  SER.LGLSERNAM
    *
    *@param  out
    *@param applicationName
    *@throws IOException
    */
    private void outputSeriesImageTable(javax.servlet.jsp.JspWriter out, String applicationName) throws IOException
    {
        // find one series, output err msg if 0 or >1
        String value=null;
        String image;
        String title="";
        EntityGroup serGrp = getEntityList().getEntityGroup("SER");
        if (serGrp.getEntityItemCount()>0) {
            for(int i=0; i<serGrp.getEntityItemCount(); i++){
                seriesItem = serGrp.getEntityItem(0);
                value = PokUtils.getAttributeValue(seriesItem, "LGLSERNAM", "", null);
                if (value==null) {  // find one with this set
                    continue;
                }
            }
            if (serGrp.getEntityItemCount()>1) {
                if (value!=null){
                    value = getErrorHtml("More than one Legal Series Names found, showing just one")+"<br />"+value;
                }else{
                    value = getErrorHtml("No Legal Series Name found");
                }
            }
        }else{
            value = getErrorHtml("No Legal Series Name found");
        }

        out.println("<!--outputSeriesImageTable() begin Series and Image table -->");

        image = getImage(out,applicationName);

        out.println("<table width=\"443\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tr><td height=\"5\"></td></tr>");
        if (seriesItem!=null) {
            title=" title=\""+seriesItem.getKey()+"\"";
        }
        out.println("<tr><td"+title+"><h1>"+value+"</h1></td></tr>");
        title="";
        if (imageItem!=null) {
            title=" title=\""+imageItem.getKey()+"\"";
        }

        out.println("<tr><td"+title+">"+image+"</td></tr>");
        out.println("</table>");
    }


    /********************************************************************************
    * The next section shows the 'Series features'. This is found in the 'Feature Benefits'
    * (FB) as follows for the 'Series' (SER):
    * SERFB
    *
    * Filter based on FB attributes as follows:
    * 	FB.CATAUDIENCE = CATNAV.CATAUDIENCE
    * 	FB.CATPAGETYPE =
    * 		CATPAGETYPE	evp		Overview Page (EVP)
    * 		CATPAGETYPE	hpg		Home Page
    * 		CATPAGETYPE	mdp		Model Details Page
    * 		CATPAGETYPE	vam		View All Models
    * 	FB.COUNTRYLIST contains the selected Country
    * 	FB.FBSTATUS = 'Final' (0020)
    * 	FB.PUBFROM <= Current Timestamp <= FB.PUBTO
    *
    * Results:
    * 	FB.FBSTMT is the Feature Benefits (XML).
    *
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@return  String
    *@throws IOException
    */
	private String getFB(String pageType, javax.servlet.jsp.JspWriter out) throws IOException
	{
		String value =null;
		int count=0;
		// go from seriesItem, not group
		if (seriesItem!=null) {
			Vector fbVct = PokUtils.getAllLinkedEntities(seriesItem, "SERFB", "FB");
			EntityItem catnavItem = getEntityList().getParentEntityGroup().getEntityItem(0);
			EANFlagAttribute attr = (EANFlagAttribute)catnavItem.getAttribute("CATAUDIENCE");
			for (int i=0; i<fbVct.size(); i++)
			{
				EntityItem fbItem = (EntityItem)fbVct.elementAt(i);
				// FB.CATAUDIENCE = CATNAV.CATAUDIENCE
				// this is an F flag.. one match is sufficient
				EANFlagAttribute fbattr = (EANFlagAttribute)fbItem.getAttribute("CATAUDIENCE");
				if (fbattr!=null)
				{
					if (fbattr.isSelected(getAudienceFlag())) {
						String pubFrom = PokUtils.getAttributeValue(fbItem, "PUBFROM", "", "", false);
						String pubTo = PokUtils.getAttributeValue(fbItem, "PUBTO", "", "", false);
						//FB.PUBFROM <= Current Timestamp <= FB.PUBTO
						if (pubFrom.compareTo(getNow())<=0 && getNow().compareTo(pubTo)<=0){
							// FB.COUNTRYLIST contains the selected Country
							EANFlagAttribute fAtt = (EANFlagAttribute)fbItem.getAttribute("COUNTRYLIST");
							if (fAtt!=null && fAtt.isSelected(getCountryFlag())) {
								// FB.FBSTATUS = 'Final' (0020).. VE filter should control this..
								String status = PokUtils.getAttributeFlagValue(fbItem, "FBSTATUS");
								if (status!=null && status.equals("0020")){
									//CATPAGETYPE = pageType
									fAtt = (EANFlagAttribute)fbItem.getAttribute("CATPAGETYPE");
									if (fAtt!=null && fAtt.isSelected(pageType)) {
										value = PokUtils.getAttributeValue(fbItem, "FBSTMT", "", null);
										out.println("<!--getFB() "+fbItem.getKey()+" meets criteria -->");
										count++;
										featBenItem = fbItem;
									}else{ // wrong pagetype
										out.println("<!--getFB() "+fbItem.getKey()+" CATPAGETYPE not "+pageType+", ["+fAtt+"] -->");
									}
								}else{	// status not final
									out.println("<!--getFB() "+fbItem.getKey()+" in not Final, ["+status+"] -->");
								}
							}else{	// country not in list
								out.println("<!--getFB() "+fbItem.getKey()+" does not have COUNTRYLIST["+getCountryFlag()+"] -->");
							}
						}else{	// date not in range
							out.println("<!--getFB() "+fbItem.getKey()+" fails date check "+pubFrom+" <= "+getNow()+" <= "+pubTo+" -->");
						}
					}else{	//audience mismatch
						out.println("<!--getFB() "+fbItem.getKey()+" CATAUDIENCE ["+fbattr+"] does not match audience "+
							" ["+attr.getFlagLongDescription(getAudienceFlag())+"] -->");
					}
				}else{	// null audience
					out.println("<!--getFB() NULL CATAUDIENCE "+fbItem.getKey()+" -->");
				}
			}
		}

		if (value==null) {
			value =getErrorHtml("No Feature Benefits found");
		}else{
			if (count>1){
				value =getErrorHtml("More than one Feature Benefits found, showing just one")+"<br />"+value;
			}
		}
		return value;
	}

    /********************************************************************************
    * The 'Image' (IMG) for the 'Series' (SER) is found as follows:
    * Relator SERIMG
    *
    * Filter based on IMG attributes as follows:
    *   IMG.COUNTRYLIST contains the selected Country
    *   IMG.STATUS = 'Final' (0020)
    *   IMG.PUBFROM <= Current Timestamp <= IMG.PUBTO
    * Results:
    *   IMG.MKTGCOLORIMG is the Image
    *
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@param applicationName
    *@return  String
    *@throws IOException
    */
    private String getImage(javax.servlet.jsp.JspWriter out, String applicationName) throws IOException
    {
        String value =null;
        int count=0;
        // go from this seriesItem
        if (seriesItem!=null) {
            Vector imgVct = PokUtils.getAllLinkedEntities(seriesItem, "SERIMG", "IMG");
            for(int i=0; i<imgVct.size(); i++) {
                EntityItem imgItem = (EntityItem)imgVct.elementAt(i);
                String pubFrom = PokUtils.getAttributeValue(imgItem, "PUBFROM", "", "", false);
                String pubTo = PokUtils.getAttributeValue(imgItem, "PUBTO", "", "", false);
                //IMG.PUBFROM <= Current Timestamp <= IMG.PUBTO
                if (pubFrom.compareTo(getNow())<=0 && getNow().compareTo(pubTo)<=0){
                    // IMG.COUNTRYLIST contains the selected Country
                    EANFlagAttribute fAtt = (EANFlagAttribute)imgItem.getAttribute("COUNTRYLIST");
                    if (fAtt!=null && fAtt.isSelected(getCountryFlag())) {
                        // IMG.STATUS = 'Final' (0020).. VE filter should control this..
                        String status = PokUtils.getAttributeFlagValue(imgItem, "STATUS");
                        if (status!=null && status.equals("0020")){
                            imageItem = imgItem;
                            count++;
                            value = PokUtils.getAttributeValue(imgItem, "MKTGCOLORIMG", "", null,false,applicationName);
                            if (value!=null){
                                out.println("<!--getImage() "+imgItem.getKey()+" meets criteria -->");
                            }else{
                                out.println("<!--getImage() "+imgItem.getKey()+" met criteria BUT MKTGCOLORIMG is null! -->");
                            }
                        }else{
                            // status not final
                            out.println("<!--getImage() "+imgItem.getKey()+" in not Final, ["+status+"] -->");
                        }
                    }else{
                        // country doesn't match
                        out.println("<!--getImage() "+imgItem.getKey()+" does not have COUNTRYLIST["+getCountryFlag()+"] -->");
                    }
                }else{
                    // date not in range
                    out.println("<!--getImage() "+imgItem.getKey()+" fails date check "+pubFrom+" <= "+getNow()+" <= "+pubTo+" -->");
                }
            }
            imgVct.clear();
        }

        if (value==null) {
            value =getErrorHtml("No Image found");
        }else{
            if (count>1){
                value =getErrorHtml("More than one Image found, showing just one")+"<br />"+value;
            }
        }

        return value;
    }

    /********************************************************************************
    * Output FB and 'build your own' Models in one table for VAM rpt
    *
    *@param  out  javax.servlet.jsp.JspWriter for output
    *@throws IOException
    */
    private void outputFBAndMdlTable(javax.servlet.jsp.JspWriter out) throws IOException
    {
        String value =getFB("vam",out);
        String title="";

        out.println("<!--outputFBAndMdlTable() begin FB and Models table -->");
        out.println("<table width=\"442\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tr><td valign=\"top\">");
        out.println("<table width=\"442\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tr><td valign=\"top\" width=\"133\"><b>Series features:</b></td>");
        out.println("<td width=\"7\" height=\"1\">&nbsp;</td>");
        if (featBenItem!=null) {
            title=" title=\""+featBenItem.getKey()+"\"";
        }

        out.println("<td valign=\"top\" "+title+">"+value+"</td></tr></table></td></tr>");
        out.println("<tr><td>&nbsp;</td></tr>");
        out.println("<tr><td>");
        out.println("<table width=\"442\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tr bgcolor=\"#EEEEEE\"><td width=\"441\" height=\"2\" colspan=\"2\"></td></tr>");
        out.println("<tr><td width=\"442\">");
        out.println("<table width=\"441\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        out.println("<tr><td align=\"left\" class=\"tblue\" colspan=\"2\">&nbsp;<b>Build your own</b><br /></td></tr>");
        // model loop here
        if(getActiveModels().size()==0){
            out.println("<tr><td colspan=\"2\">"+"No Active Models found"+"</td></tr>");
        }else{
            for (int i=0; i<getActiveModels().size(); i++){
                SGOffering theOff = (SGOffering)getActiveModels().elementAt(i);
                StringBuffer titleSb = new StringBuffer(theOff.toString());
                Vector catlgpubVct = theOff.getValidCatlgpub();  // should be only one in here, wont get here if 0
                String multmsg="";
                if (catlgpubVct.size()>1){
                    multmsg="<br />"+getErrorHtml("Multiple active CATLGPUB found");
                }
                // build title
                for (int v=0; v<catlgpubVct.size(); v++){
                    EntityItem catlg = (EntityItem)catlgpubVct.elementAt(v);
                    titleSb.append("/"+catlg.getKey());
                }

                out.println("<tr valign=\"top\" title=\""+titleSb+"\"><td width=\"35\" height=\"14\">"+
                    "<img src=\"http://www.ibm.com/i/v14/icons/fw_bold.gif\" alt=\"\" width=\"16\" height=\"16\" align=\"top\" /></td>");
                out.println("<td width=\"404\" height=\"14\">"+theOff.getMktDesc()+multmsg+"</td></tr>");
            }
        }

        out.println("<tr><td height=\"2\" bgcolor=\"#FFFFFF\" colspan=\"2\">&nbsp;</td></tr>");
        out.println("<tr><td height=\"2\" bgcolor=\"#FFFFFF\" colspan=\"2\">&nbsp;</td></tr>");
        out.println("</table></td>");
        out.println("<td bgcolor=\"#EEEEEE\" height=\"1\"></td></tr>");
        out.println("<tr bgcolor=\"#EEEEEE\"><td width=\"441\" colspan=\"2\" height=\"1\"></td>");
        out.println("</tr></table>");
        out.println("</td></tr></table>");
    }

    /********************************************************************************
    * group CATVAMATTR for column 3-6 layout
    * Layout controlled by CATVAMATTR attr:
    *
    * CATVAMATTR    ATTRDESCRIPTION
    * CATVAMATTR    CATATTRIBUTECODE
    * CATVAMATTR    CATCOLUMN
    * CATVAMATTR    CATENTITYTYPE
    * CATVAMATTR    CATSEQ
    *
    *@param out javax.servlet.jsp.JspWriter for debug
    *@throws IOException
    */
    private void setupVAMLayout(javax.servlet.jsp.JspWriter out) throws IOException,java.sql.SQLException
    {
        // get all CATVAMATTR,
        EntityGroup vamattrGrp = getEntityList().getEntityGroup("CATVAMATTR");
        if (vamattrGrp.getEntityItemCount()>0){
            StringBuffer colsb = new StringBuffer();
            Vector vamattrVct = new Vector();
            String lastCol = "";
            for (int i =0; i<vamattrGrp.getEntityItemCount(); i++){
                vamattrVct.add(vamattrGrp.getEntityItem(i));
            }
            //sort them on col and seq.. NOTE: assumption that there are 4 columns
            Collections.sort(vamattrVct, new CATVAMATTRComparator());
            for (int i=0; i<vamattrVct.size(); i++){
                EntityItem vamItem = (EntityItem)vamattrVct.elementAt(i);
                String tblname=null;
                String curCol = PokUtils.getAttributeValue(vamItem, "CATCOLUMN", "", null, false);
                String catType = PokUtils.getAttributeValue(vamItem, "CATENTITYTYPE", "", null, false);
                String catCode = PokUtils.getAttributeValue(vamItem, "CATATTRIBUTECODE", "", null, false);
                if (curCol==null){
                    out.println("<!--setupVAMLayout() "+vamItem.getKey()+" did not have CATCOLUMN value -->");
                    continue;
                }
                if (catCode!=null&&catType!=null){
                    tblname=getCatTableName(catType, catCode,out);
                }

                if (!lastCol.equals(curCol)){ // new column started
                    Vector tmp = new Vector(1);
                    tmp.addElement(new VamAttrTableName(vamItem,tblname));
                    vamattrTbl.put(curCol,tmp);
                    colsb.append(" "+curCol);

                    lastCol = curCol;
                }else{  // must be next in sequence
                    Vector tmp = (Vector)vamattrTbl.get(curCol);
                    tmp.addElement(new VamAttrTableName(vamItem,tblname));
                }
            }

            vamattrVct.clear();

            if (vamattrTbl.size()<4){ // this is an error..need a vector for each column
                throw new SGCatException("CATVAMATTR does not have 4 columns. Found columns:"+colsb);
            }
            // check that there is an entity for each column
            for (int colNum=1; colNum<=4; colNum++){
                Vector tmp = (Vector)vamattrTbl.get(""+colNum);
                if (tmp==null){
                    throw new SGCatException("Column "+colNum+" was not found in any CATVAMATTR");
                }
            }
        }else{
            throw new SGCatException("No CATVAMATTR found");
        }
    }

    /********************************************************************************
    * Build column headers
    * Layout controlled by CATVAMATTR attr:
    *
    * CATVAMATTR    ATTRDESCRIPTION
    * CATVAMATTR    CATATTRIBUTECODE
    * CATVAMATTR    CATCOLUMN
    * CATVAMATTR    CATENTITYTYPE
    * CATVAMATTR    CATSEQ
    *
    *@param out javax.servlet.jsp.JspWriter for debug
    *@throws IOException
    */
    private void getVAMHeader(javax.servlet.jsp.JspWriter out) throws IOException
    {
        out.println("<tr><td colspan=\"11\" height=\"4\" width=\"699\" bgcolor=\"#EEEEEE\"></td></tr>");
        out.println("<tr valign=\"top\">");
        out.println("<th id=\"partnum\" width=\"88\" bgcolor=\"#EEEEEE\">Model</th>");
        out.println(SPACER_DESC);
        out.println("<th id=\"pla\" width=\"105\" bgcolor=\"#EEEEEE\">"+
            "IBM&nbsp;Web&nbsp;Price</th>");

        // columns 3-6 are built from other tables
        for (int colNum=1; colNum<=4; colNum++) {
            Vector tmp = (Vector)vamattrTbl.get(""+colNum);
            out.println(SPACER_DESC);
            out.println("<th id=\"colseq"+colNum+"\" width=\"18%\" bgcolor=\"#EEEEEE\">");

            // get each sequence
            for (int i=0; i<tmp.size(); i++) {
                EntityItem vamItem = ((VamAttrTableName)tmp.elementAt(i)).getVamItem();
                String seq = PokUtils.getAttributeValue(vamItem, "CATSEQ", "", "", false);
                String desc = PokUtils.getAttributeValue(vamItem, "ATTRDESCRIPTION", "", null, false);
                if (desc==null) {
                    desc = getErrorHtml("ATTRDESCRIPTION","Error null");
                    out.println("<!--getVAMHeader() get column["+colNum+"] seq["+seq+"] for "+vamItem.getKey()+" ATTRDESCRIPTION is null -->");
                }
                out.print(desc);
                if ((i+1)<tmp.size()){  // more sequences left
                    out.print("<br />");
                }
            }
            out.println("</th>");
        }
        out.println("</tr>");

        out.println("<tr><td colspan=\"11\" height=\"6\" width=\"699\" bgcolor=\"#EEEEEE\"></td></tr>");
    }

    /********************************************************************************
    * Get column 3-6 info
    * Use CATVAMATTR for this column and find value for each sequence
    *
    * Finding Attribute Values based on CATENTITYTYPE and CATATTRIBUTE:
    * Find the corresponding BASICRULE where CATENTITYTYPE = CATITEMTYPE and CATATTRIBUTE = CATATTRIBUTECODE.
    * Results:
    * Catalog DB Table Name (CATTABLENAME)
    * Entity Type (CATENTITYTYPE) - not used in this report yet
    *
    *@param  featIdVct  Vector of int featentityid
    *@param  vamattrVct  Vector of CATVAMATTR sorted by CATSEQ for this column
    *@param  out  javax.servlet.jsp.JspWriter for debug
    *@return  String[] [0]=value [1]=tooltip
    *@throws java.sql.SQLException
    *@throws IOException
    */
    private String[] getVAMCell(SGOffering theOff,Vector featIdVct, Vector vamattrVct, javax.servlet.jsp.JspWriter out)
        throws java.sql.SQLException, IOException
    {
        StringBuffer sb = new StringBuffer();
        String[] retvalue = new String[2];
        StringBuffer ttsb = new StringBuffer();

        // get each sequence for this column
        for (int i=0; i<vamattrVct.size(); i++) {
            String tooltip=null;
            String value=null;
            VamAttrTableName vamAttr = (VamAttrTableName)vamattrVct.elementAt(i);
            String catType = vamAttr.getCatType();
            String catCode = vamAttr.getCatCode();
            if (catCode!=null && catType!=null){
                String catTblName = vamAttr.getTableName();
                String[] cellval = new String[2];
                cellval[0]= null;
                cellval[1] = null;
                if (catTblName==null){
                    out.println("<!-- getVAMCell(): get seq["+(i+1)+"] for "+vamAttr.getKey()+" catType: "+catType+
                        " catCode: "+catCode+" NO GBLI.BASICRULE found -->");
                    catTblName="BRNF";
                }else if ("WWATTRIBUTES".equals(catTblName)){
                    out.println("<!-- getVAMCell(): get seq["+(i+1)+"] for "+vamAttr.getKey()+" catType: "+catType+
                        " catCode: "+catCode+" catTblName "+catTblName+"-->");
                    cellval = getCellFromWWAttr(theOff,catCode,out);
                }else if ("FEATUREDETAIL".equals(catTblName)){
                    // look at each featureid found for the lseos and wwseos
                    out.println("<!-- getVAMCell(): get seq["+(i+1)+"] for "+vamAttr.getKey()+" catType: "+catType+
                        " catCode: "+catCode+" catTblName "+catTblName+" num.featid: "+featIdVct.size()+"-->");
                    cellval = getCellFromFeatDetail(featIdVct, catType, catCode,out);
                }

                value = cellval[0];
                tooltip=cellval[1];

                cellval[0]= null;
                cellval[1] = null;

                if (value==null) {
                    value = getErrorHtml(catTblName+":"+catType+":"+catCode, "Error no value found");
                }
                if (tooltip!=null){
                    ttsb.append("["+(i+1)+"]"+tooltip+NEWLINE);
                }
            }else{ // CATVAMATTR has missing values
                if (catCode==null) {
                    value = getErrorHtml("CATATTRIBUTECODE","Error null");
                }
                if (catType==null) {
                    value = getErrorHtml("CATENTITYTYPE","Error null");
                }
            }

            if (sb.length()>0){
                sb.append("<br />");
            }
            sb.append(value);
        }

        retvalue[0]=sb.toString();
        if (ttsb.length()>0){
            retvalue[1]=ttsb.toString().trim();
        }else{
            retvalue[1]=null;
        }
        return retvalue;
    }

    /********************************************************************************
    * used to put CATVAMATTR in display order
    */
    private static class CATVAMATTRComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2)
        {
            EntityItem e1 = (EntityItem)o1;
            EntityItem e2 = (EntityItem)o2;

			// first check CATCOLUMN
            Integer ofPN1 = new Integer(PokUtils.getAttributeValue(e1, "CATCOLUMN","", "9999",false));
            Integer ofPN2 = new Integer(PokUtils.getAttributeValue(e2, "CATCOLUMN","", "9999",false));
            int diff = ofPN1.compareTo(ofPN2);
            if (diff==0) {  // same so check CATSEQ
	            ofPN1 = new Integer(PokUtils.getAttributeValue(e1, "CATSEQ","", "9999",false));
	            ofPN2 = new Integer(PokUtils.getAttributeValue(e2, "CATSEQ","", "9999",false));
	            diff = ofPN1.compareTo(ofPN2);
			}

            return diff;
        }
    }

    /********************************************************************************
    * used to tie CATVAMATTR and BASICRULE.CATTABLENAME together
    */
    private static class VamAttrTableName
    {
        private EntityItem vamattrItem;
        private String tablename;
        VamAttrTableName(EntityItem vam, String tname) {
            vamattrItem = vam;
            tablename = tname;
        }
        void dereference(){
            vamattrItem = null;
            tablename = null;
        }
        EntityItem getVamItem() { return vamattrItem;}
        String getKey() { return vamattrItem.getKey();}
        String getTableName() { return tablename;}
        String getCatType() {
            return PokUtils.getAttributeValue(vamattrItem, "CATENTITYTYPE", "", null, false);
        }
        String getCatCode() {
            return PokUtils.getAttributeValue(vamattrItem, "CATATTRIBUTECODE", "", null, false);
        }
    }
}
