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
 * class to generate output for one LSEO offering
 *@author     Wendy Stimpson
 *@created    Sept 12, 2006
 */
// $Log: SGLseoOffering.java,v $
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
class SGLseoOffering extends SGOffering implements Comparable
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.1 $";

    private int wwseoId=0;

    /********************************************************************************
    * Constructor
	*
    * @param cat SGCatalog
    * @param item EntityItem for LSEO
    */
    SGLseoOffering(SGCatalog cat, EntityItem item, String audienceFlag, String ctryFlag,
    String curTime,javax.servlet.jsp.JspWriter out) throws java.io.IOException
    {
        super(cat, item,audienceFlag,ctryFlag,curTime,out);
        init();
    }
    /********************************************************************************
    * jtest requirement, must declare local variables at top of method
    */
    private void init(){
        // get WWSEO parent
        Vector wwseoVct = PokUtils.getAllLinkedEntities(getOffering(), "WWSEOLSEO", "WWSEO");
        wwseoId = ((EntityItem)wwseoVct.firstElement()).getEntityID();
        wwseoVct.clear();
	}

    /********************************************************************************
    * get the WWENTITYTYPE  WWSEO for LSEO
    *@return String
    */
	protected String getWWENTITYTYPE(){
        return "WWSEO";
	}
    /********************************************************************************
    * get the WWENTITYTYPE  WWSEO for LSEO
    *@return int
    */
	protected int getWWENTITYID(){
        return wwseoId;
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
        	PokUtils.getAttributeValue(getOffering(), "LSEOMKTGDESC", "",
	        	SGCatalog.getErrorHtml("LSEOMKTGDESC","Error null"))+
        	"</b><br /><span style=\"font-weight:normal\">"+getPartNumber()+
        	"</span></a>";
    }
    /********************************************************************************
    * get desc for MDP General.Description
    *@return String
    */
    protected String getDescription() {
		return PokUtils.getAttributeValue(getOffering(), "LSEOMKTGDESC", "",
	        	SGCatalog.getErrorHtml("LSEOMKTGDESC","Error null"));
	}

    /********************************************************************************
    * get PRODENTITYID for LSEO, just one for this
    *@return int[]
    */
    protected int[] getLseoIds() {return new int[]{getOffering().getEntityID()}; }
    /********************************************************************************
    * get PRODENTITYID for WWSEO - just parent
    *@return int[]
    */
    protected int[] getWwseoIds() {return new int[]{wwseoId}; }

    /********************************************************************************
    * get the sort key, used for sort of inactive offerings when that section is output
    *@return String
    */
	protected String getInactiveSortKey() { return "BB"+getPartNumber();}  // do LSEO after MODEL

    /********************************************************************************
    * get string rep
    *@return String
    */
    public String toString() {
		return getKey()+"/WWSEO"+wwseoId;
	}
}
