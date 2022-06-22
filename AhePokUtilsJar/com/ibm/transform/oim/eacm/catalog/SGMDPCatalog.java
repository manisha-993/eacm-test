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
 * This class supports SG MDP Catalog reports
 *
 */
// $Log: SGMDPCatalog.java,v $
// Revision 1.2  2006/10/02 18:57:54  wendy
// Pass application name for images
//
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
public class SGMDPCatalog extends SGCatalog
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";

    private String partnum;
    private SGOffering theOff;
	private Vector catGrpAttrVct = new Vector();

    /********************************************************************************
    * Constructor for MDP report
    *
    *@param  list       EntityList for data
    *@param  nls        int id for selected NLS
    *@param  ctry       String flagcode for selected country
    *@param  code       String ctryCode for selected country
    *@param  aud        String flagcode for selected audience
    *@param  curtime    String current time in ISO format
    *@param  pn    		String partnumber
    */
    public SGMDPCatalog(EntityList list,int nls, String ctry, String code, String aud, String curtime,
    	String pn)
    {
        super(list,nls,ctry, code, aud, curtime);
        partnum = pn;
    }

    /********************************************************************************
    * Release memory
    */
    public void dereference()
    {
        super.dereference();
        partnum = null;
        if (theOff!=null) {
			theOff.dereference();
		}
		for (int i=0; i<catGrpAttrVct.size(); i++) {
			GrpAttrTableName grp = (GrpAttrTableName)catGrpAttrVct.elementAt(i);
			grp.dereference();
		}
		catGrpAttrVct = null;
    }

    /********************************************************************************
    * Get MDP info for display of this catalog
    * Wendy   is the model details page rpt only for LSEO or LSEOBUNDLE?
    * Rupal   I think either one
    * Wendy   but not MODEL, correct?
    * Rupal   right
    * Wendy   ok
    * Wendy   and is this only for Active offerings?  something that would have ended up in the VAM page?
    * Rupal   yes
    * Rupal   that's when they click on the id
    *
    *@param out javax.servlet.jsp.JspWriter for output
    *@param applicationName
    *@throws java.io.IOException
    *@throws java.sql.SQLException
    */
    protected void outputReport(javax.servlet.jsp.JspWriter out, String applicationName) throws java.sql.SQLException, java.io.IOException
    {
		findAllOfferings(false, out);

		// look for a match on this partnumber
		for (int i=0; i<getActiveOfferings().size(); i++){
			SGOffering tmpOff = (SGOffering)getActiveOfferings().elementAt(i);
			if (tmpOff.getPartNumber().equals(partnum)){
				theOff = tmpOff;
				break;
			}
		}

		// check partnumber
		if (getActiveOfferings().size()==0 || theOff==null) {
			throw new SGCatException("No Active offerings found for partnumber "+partnum+" in "+
				getEntityList().getParentEntityGroup());
		}

		out.println("<!--getMDPInfo() setup layout -->");
		setupMDPLayout(out);

		out.println("<!--getMDPInfo() output offeringt -->");
		outputMDPTable(out);
    }

    /********************************************************************************
    * output MDP table
    *@param out javax.servlet.jsp.JspWriter for output
    *@throws java.io.IOException
    *@throws java.sql.SQLException
    */
    private void outputMDPTable(javax.servlet.jsp.JspWriter out) throws java.sql.SQLException, java.io.IOException
    {
		// rows are built from other tables
		// get all featentityid for these LSEO
		Vector featIdVct = getFeatIds("LSEO", theOff.getLseoIds(), out);

		// get all featentityid for these WWSEO
		Vector tmp = getFeatIds("WWSEO", theOff.getWwseoIds(), out);
		featIdVct.addAll(tmp);
		tmp.clear();

		// open table
		out.println("<br /><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"3\">");
		out.println("<tr><td class=\"v14-header-1\" colspan=\"2\">Model details</td></tr>");
		out.println("<tr><td colspan=\"2\">");
		out.println("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">");
		out.println("<tr valign=\"top\"><td height=\"20\" valign=\"middle\" nowrap=\"nowrap\">Display currency:&nbsp;");
		out.println("<span class=\"small\">National Currency without Tax</span></td></tr>");
		out.println("</table></td></tr>");
		out.println("<tr valign=\"top\"><td width=\"100%\" colspan=\"2\">"+
			"<img src=\"http://www-132.ibm.com/content/misc_images/graypixel.gif\" width=\"100%\" height=\"1\" alt=\"\" /></td></tr>");

		// output each group
		for (int i=0; i<catGrpAttrVct.size(); i++){
			if (i>0) {  // output divider
				out.println("<tr><td class=\"bgdash\" colspan=\"2\"></td></tr>");
			}
			outputCatGrp((GrpAttrTableName)catGrpAttrVct.elementAt(i),featIdVct, out);
		}

		// close table
		out.println("</table>");

		// release memory
		featIdVct.clear();
	}

    /********************************************************************************
    * sort CATGROUP for layout
    * Group Layout controlled by CATATTR attr:
    *
    * CATGROUP  CATSEQ
    * CATGROUP  GROUPDESCRIPTION
    *
    * CATATTR   ATTRDESCRIPTION
    * CATATTR   CATATTRIBUTECODE
    * CATATTR   CATENTITYTYPE
    * CATATTR   CATSEQ
    *
    *@param out
    *@throws java.io.IOException
    */
    private void setupMDPLayout(javax.servlet.jsp.JspWriter out) throws IOException,java.sql.SQLException
    {
        EntityGroup eGrp = getEntityList().getEntityGroup("CATGROUP");
        if (eGrp.getEntityItemCount()==0){
            throw new SGCatException("No CATGROUP found");
        }

        // take a CATGROUP and tie each CATATTR to a BASICRULE tablename
        for (int i =0; i<eGrp.getEntityItemCount(); i++){
            EntityItem catGrpItem = eGrp.getEntityItem(i);
            Vector catAttrVct = PokUtils.getAllLinkedEntities(catGrpItem, "CATGROUPATTR", "CATATTR");
            GrpAttrTableName grpTbl = new GrpAttrTableName(catGrpItem);
            catGrpAttrVct.add(grpTbl);
            for (int g=0; g<catAttrVct.size(); g++){
                EntityItem mdpItem = (EntityItem)catAttrVct.elementAt(g);
                String catType = PokUtils.getAttributeValue(mdpItem, "CATENTITYTYPE", "", null, false);
                String catCode = PokUtils.getAttributeValue(mdpItem, "CATATTRIBUTECODE", "", null, false);
                if (catCode!=null&&catType!=null){
                    String tblname=getCatTableName(catType, catCode,out);
                    grpTbl.addCatAttr(new CatAttrTableName(mdpItem, tblname));
                }else{
                    grpTbl.addCatAttr(new CatAttrTableName(mdpItem, null));
                    out.println("<!--setupMDPLayout() "+mdpItem.getKey()+" has null CATENTITYTYPE:"+catType+
                    	" or CATATTRIBUTECODE:"+catCode+" -->");
                }
            }
            // sort the CATATTR by CATSEQ
            Collections.sort(grpTbl.getCatAttrVct(), new CATDETAILComparator());
        }

		//sort groups by seq..
		Collections.sort(catGrpAttrVct, new CATDETAILComparator());
    }

    /********************************************************************************
    * output info for this CATGROUP one CATATTR per row
    *
    * CATGROUP  CATSEQ
    * CATGROUP  GROUPDESCRIPTION
    *
    * CATATTR   ATTRDESCRIPTION
    * CATATTR   CATATTRIBUTECODE
    * CATATTR   CATENTITYTYPE
    * CATATTR   CATSEQ
    *
    *@param catGrpTbl GrpAttrTableName
    *@param featIdVct Vector of featentityid
    *@param out javax.servlet.jsp.JspWriter for debug
    *@throws IOException
    *@throws java.sql.SQLException
    */
    private void outputCatGrp(GrpAttrTableName catGrpTbl, Vector featIdVct, javax.servlet.jsp.JspWriter out)
    throws IOException, java.sql.SQLException
    {
        EntityItem catGrpItem = catGrpTbl.getGroupItem();
        boolean isGeneral = false;  // 'General' CATGROUP is hardcoded
        String seq = PokUtils.getAttributeValue(catGrpItem, "CATSEQ", "", "",false);

        // output the group description
        String grpDesc = PokUtils.getAttributeValue(catGrpItem, "GROUPDESCRIPTION", "", null,false);
        if (grpDesc==null) {
            grpDesc = getErrorHtml("GROUPDESCRIPTION", "Error null");
        }

        isGeneral= grpDesc.equalsIgnoreCase("General");

        out.println("<tr><td colspan=\"2\" title=\"["+seq+"]"+catGrpItem.getKey()+"\"><span class=\"small\"><strong>"+grpDesc+
            "</strong></span></td></tr>");

		if (isGeneral) {
			String price = getPrice(theOff.getPartNumber(), theOff.getVarCondType(), out);
			out.println("<tr valign=\"top\"><td><span class=\"small\">Model name</span></td>");
			out.println("<td><span class=\"small\">"+theOff.getPartNumber()+"</span></td></tr>");

			out.println("<tr valign=\"top\"><td><span class=\"small\">Description</span></td>");
			out.println("<td><span class=\"small\">"+theOff.getDescription()+"</span></td></tr>");

			out.println("<tr valign=\"top\"><td><span class=\"small\">IBM Web Price*</span></td>");
			out.println("<td><span class=\"price\">"+price+"</span></td></tr>");
		}else{
			if (catGrpTbl.getCatAttrVct().size()==0){
				out.println("<tr><td colspan=\"2\"><span class=\"small\">"+getErrorHtml("No CATATTR found")+
					"</span></td></tr>");
			}
			// output each CATATTR one per row
			for (int i=0; i<catGrpTbl.getCatAttrVct().size(); i++){
				String value=null;
				String tooltip=null;
				String title="";
				CatAttrTableName mdp = (CatAttrTableName)catGrpTbl.getCatAttrVct().elementAt(i);
				EntityItem catAttrItem = mdp.getCatAttrItem();
				String catType = PokUtils.getAttributeValue(catAttrItem, "CATENTITYTYPE", "", null,false);
				String catCode = PokUtils.getAttributeValue(catAttrItem, "CATATTRIBUTECODE", "", null,false);
				String attrDesc = PokUtils.getAttributeValue(catAttrItem, "ATTRDESCRIPTION", "", null,false);
				if (attrDesc==null) {
					attrDesc = getErrorHtml("ATTRDESCRIPTION", "Error null");
				}

				out.println("<tr valign=\"top\"><td title=\""+catAttrItem.getKey()+"\"><span class=\"small\">"+
					attrDesc+"</span></td>");
				if (catCode!=null && catType!=null){
					String catTblName = mdp.getTableName();
					String[] cellval = new String[2];
					cellval[0]= null;
					cellval[1] = null;
					if (catTblName==null){
						out.println("<!-- outputCatGrp(): get seq["+(i+1)+"] for "+catAttrItem.getKey()+" catType: "+catType+
							" catCode: "+catCode+" NO GBLI.BASICRULE found -->");
						catTblName="BRNF";
					}else if ("WWATTRIBUTES".equals(catTblName)){
						out.println("<!-- outputCatGrp(): get seq["+(i+1)+"] for "+catAttrItem.getKey()+" catType: "+catType+
							" catCode: "+catCode+" catTblName "+catTblName+"-->");
						cellval = getCellFromWWAttr(theOff,catCode,out);
					}else if ("FEATUREDETAIL".equals(catTblName)){
						// look at each featureid found for the lseos and wwseos
						out.println("<!-- outputCatGrp(): get seq["+(i+1)+"] for "+catAttrItem.getKey()+" catType: "+catType+
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
				}else{ // CATVAMATTR has missing values
					if (catCode==null) {
						value = getErrorHtml("CATATTRIBUTECODE","Error null");
					}
					if (catType==null) {
						value = getErrorHtml("CATENTITYTYPE","Error null");
					}
				}

				if (tooltip!=null){
					seq = PokUtils.getAttributeValue(catAttrItem, "CATSEQ", "", "",false);
					title=" title=\"["+seq+"]"+tooltip+"\"";
				}
				String pltStr = "<td"+title+"><span class=\"small\">"+value+"</span></td></tr>";
				if(validate(pltStr))
                  out.println(pltStr);
			}
		}
    }

    /********************************************************************************
    * used to tie CATGROUP, CATATTR and BASICRULE.CATTABLENAME together
    */
    private static class GrpAttrTableName
    {
        private EntityItem groupItem;
        private Vector attrVct;
        GrpAttrTableName(EntityItem parent) {
            groupItem = parent;
            attrVct = new Vector(1);
        }
        void dereference(){
            groupItem = null;
            for (int i=0; i<attrVct.size(); i++){
                CatAttrTableName m = (CatAttrTableName)attrVct.elementAt(i);
                m.dereference();
            }
            attrVct.clear();
        }
        EntityItem getGroupItem() { return groupItem;}
        void addCatAttr(CatAttrTableName mdp) { attrVct.add(mdp);}
        Vector getCatAttrVct() { return attrVct;}
    }

    /********************************************************************************
    * used to tie CATATTR and BASICRULE.CATTABLENAME together
    */
    private static class CatAttrTableName
    {
        private EntityItem catattrItem;
        private String tablename;
        CatAttrTableName(EntityItem mdp, String tname) {
            catattrItem = mdp;
            tablename = tname;
        }
        void dereference(){
            catattrItem = null;
            tablename = null;
        }
        EntityItem getCatAttrItem() { return catattrItem;}
        String getTableName() { return tablename;}
    }

    /********************************************************************************
    * used to put CATGROUP or CATATTR in display order by CATSEQ
    */
    private static class CATDETAILComparator implements java.util.Comparator
    {
        public int compare(Object o1, Object o2)
        {
            Integer ofPN1;
            Integer ofPN2;
            EntityItem e1;
            EntityItem e2;
            if (o1 instanceof GrpAttrTableName){
                e1 = ((GrpAttrTableName)o1).getGroupItem();
                e2 = ((GrpAttrTableName)o2).getGroupItem();
            }else{  // assume it is CatAttrTableName
                e1 = ((CatAttrTableName)o1).getCatAttrItem();
                e2 = ((CatAttrTableName)o2).getCatAttrItem();
            }
            ofPN1 = new Integer(PokUtils.getAttributeValue(e1, "CATSEQ","", "9999",false));
            ofPN2 = new Integer(PokUtils.getAttributeValue(e2, "CATSEQ","", "9999",false));

	       	return ofPN1.compareTo(ofPN2);
        }
    }
}
