// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.catalog;

import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 * class to generate output for one MODEL offering
 *@author     Wendy Stimpson
 *@created    Sept 12, 2006
 */
// $Log: SGMdlOffering.java,v $
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
class SGMdlOffering extends SGOffering implements Comparable
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.1 $";

    /********************************************************************************
    * Constructor
    * @param cat SGCatalog
    * @param item EntityItem for MODEL
    */
    SGMdlOffering(SGCatalog cat, EntityItem item, String audienceFlag, String ctryFlag,
    	String curTime,javax.servlet.jsp.JspWriter out) throws java.io.IOException
    {
        super(cat, item,audienceFlag,ctryFlag,curTime,out);
	}

    /********************************************************************************
    * get the WWENTITYTYPE  not applicable here
    *@return String
    */
	protected String getWWENTITYTYPE(){
        return "";
	}
    /********************************************************************************
    * get the WWENTITYTYPE  not applicable here
    *@return int
    */
	protected int getWWENTITYID(){
        return 0;
	}
    /********************************************************************************
    * get the partnumber
    *@return String
    */
	protected String getPartNumber(){
        return PokUtils.getAttributeValue(getOffering(), "MACHTYPEATR", "", "")+
	    	PokUtils.getAttributeValue(getOffering(), "MODELATR", "", "");
	}
    /********************************************************************************
    * get the marketing description
    *@return String
    */
    protected String getMktDesc() {
        return "<a href=\"javascript:doNothing()\" class=\"smallplainlink\"><b>"+
        	PokUtils.getAttributeValue(getOffering(), "MODMKTGDESC", "",
					SGCatalog.getErrorHtml("MODMKTGDESC","Error null"))+"<br />"+
        			getPartNumber()+"</b></a>";
	}
    /********************************************************************************
    * this doesn't apply for MODEL, not part of offerings table
    *@return int[]
    */
    protected int[] getLseoIds() {return new int[]{0}; }
    /********************************************************************************
    * this doesn't apply for MODEL, not part of offerings table
    *@return int[]
    */
    protected int[] getWwseoIds() {return new int[]{0}; }

    /********************************************************************************
    * get the sort key, used for sort of inactive offerings when that section is output
    *@return String
    */
	protected String getInactiveSortKey() { return "AA"+getPartNumber();}  // do MODELs first

    /********************************************************************************
    * get string rep
    *@return String
    */
    public String toString() {
		return getKey();
	}
}
