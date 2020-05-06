// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.catalog;

import java.util.*;
import COM.ibm.eannounce.objects.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 * class to generate output for one LSEOBUNDLE offering
 *@author     Wendy Stimpson
 *@created    Sept 12, 2006
 */
// $Log: SGLseoBundleOffering.java,v $
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
class SGLseoBundleOffering extends SGOffering implements Comparable
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.1 $";

    private int[] lseoIds;
    private int[] wwseoIds;

    /********************************************************************************
    * Constructor
    * @param cat SGCatalog
    * @param item EntityItem for LSEOBUNDLE
    */
    SGLseoBundleOffering(SGCatalog cat, EntityItem item, String audienceFlag, String ctryFlag,
    String curTime,javax.servlet.jsp.JspWriter out) throws java.io.IOException
    {
        super(cat, item,audienceFlag,ctryFlag,curTime,out);
        init();
    }
    /********************************************************************************
    * jtest requirement, must declare local variables at top of method
    */
    private void init(){
		Vector wwseoVct;
		// get LSEO
        Vector lseoVct = PokUtils.getAllLinkedEntities(getOffering(), "LSEOBUNDLELSEO", "LSEO");
        lseoIds = new int[lseoVct.size()];
        for (int i=0; i<lseoVct.size(); i++){
			lseoIds[i]= ((EntityItem)lseoVct.elementAt(i)).getEntityID();
		}
        // get WWSEO parents
        wwseoVct = PokUtils.getAllLinkedEntities(lseoVct, "WWSEOLSEO", "WWSEO");
        wwseoIds = new int[wwseoVct.size()];
        for (int i=0; i<wwseoVct.size(); i++){
			wwseoIds[i]= ((EntityItem)wwseoVct.elementAt(i)).getEntityID();
		}

        lseoVct.clear();
        wwseoVct.clear();
	}

    /********************************************************************************
    * get the WWENTITYTYPE  LSEOBUNDLE
    *@return String
    */
	protected String getWWENTITYTYPE(){
        return getOffering().getEntityType();
	}
    /********************************************************************************
    * get the WWENTITYTYPE  LSEOBUNDLE
    *@return int
    */
	protected int getWWENTITYID(){
        return getOffering().getEntityID();
	}

    /********************************************************************************
    * get the partnumber
    *@return String
    */
	protected String getPartNumber(){
        return PokUtils.getAttributeValue(getOffering(), "SEOID", "", "");
	}

    /********************************************************************************
    * get column1 - the marketing description
    *@return String
    */
    protected String getMktDesc() {
        return "<a href=\"javascript:doNothing()\"><b>"+
        	PokUtils.getAttributeValue(getOffering(), "BUNDLMKTGDESC", "",
        		SGCatalog.getErrorHtml("BUNDLMKTGDESC","Error null"))+
        	"</b><br /><span style=\"font-weight:normal\">"+getPartNumber()+
        	"</span></a>";

    }
    /********************************************************************************
    * get desc for MDP General.Description
    *@return String
    */
    protected String getDescription() {
		return PokUtils.getAttributeValue(getOffering(), "BUNDLMKTGDESC", "",
	        	SGCatalog.getErrorHtml("BUNDLMKTGDESC","Error null"));
	}

    /********************************************************************************
    * get PRODENTITYID for LSEO, all LSEO children
    *@return int[]
    */
    protected int[] getLseoIds() {return lseoIds; }
    /********************************************************************************
    * get PRODENTITYID for WWSEO - all LSEO->WWSEO parents
    *@return int[]
    */
    protected int[] getWwseoIds() {return wwseoIds; }
    /********************************************************************************
    * get the sort key, used for sort of inactive offerings when that section is output
    *@return String
    */
	protected String getInactiveSortKey() { return "CC"+getPartNumber();}  // do LSEOBUNDLE after LSEO
    /********************************************************************************
    * get string rep
    *@return String
    */
    public String toString() {
		StringBuffer sb = new StringBuffer(getKey());
		for (int i=0; i<lseoIds.length; i++){
			sb.append("/LSEO"+lseoIds[i]);
		}
		for (int i=0; i<wwseoIds.length; i++){
			sb.append("/WWSEO"+wwseoIds[i]);
		}
		return sb.toString();
	}
}
