// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2006  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package com.ibm.transform.oim.eacm.catalog;

import COM.ibm.eannounce.objects.*;
import java.util.*;
import com.ibm.transform.oim.eacm.util.*;

/**********************************************************************************
 * Abstract class to generate output for one offering (MODEL, LSEO or LSEOBUNDLE)
 *@author     Wendy Stimpson
 *@created    Sept 12, 2006
 */
// $Log: SGOffering.java,v $
// Revision 1.2  2008/01/22 16:54:27  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2006/09/22 14:52:03  wendy
// Init for XCC Catalog reports
//
//
abstract class SGOffering implements Comparable
{
    /** object copyright statement */
    public static final String COPYRIGHT="(C) Copyright IBM Corp. 2006  All Rights Reserved.";
    /** cvs revision number */
    public static final String VERSION = "$Revision: 1.2 $";

    private EntityItem ofItem;      // MODEL, LSEO or LSEOBUNDLE
    private Vector noVamCatLgPubVct = new Vector(1);      // CATLGPUB for inactive table
    private Vector validCatLgPubVct = new Vector(1);      // CATLGPUB for VAM or MDP table
    private SGCatalog sgCat;
    private boolean inactiveSort=false;

    /********************************************************************************
    * Used for collections.sort()
    * @param o Object
    * @return int
    */
    public int compareTo(Object o) // used by Collection.sort()
   	{
		int cmpVal = 0;
		if (!inactiveSort){ // group according to partnumber, default behavior
			cmpVal = getPartNumber().compareTo(((SGOffering)o).getPartNumber());
		}else{  // sort for inactive section
			cmpVal = getInactiveSortKey().compareTo(((SGOffering)o).getInactiveSortKey());
		}
      	return cmpVal;
    }

    /********************************************************************************
    * Constructor
    * @param cat SGCatalog
    * @param item EntityItem for MODEL, LSEO or LSEOBUNDLE
    * @param audienceFlag
    * @param ctryFlag
    * @param curTime
    * @param out for debug
    *@throws java.io.IOException
    */
    protected SGOffering(SGCatalog cat, EntityItem item, String audienceFlag, String ctryFlag,
    	String curTime,	javax.servlet.jsp.JspWriter out) throws java.io.IOException
    {
		sgCat = cat;
        ofItem = item;
        // find CATLGPUB match CATAUDIENCE, OFFCOUNTRY, CATHIDE=No, CATACTIVE=Active
        // PUBFROM<now() and PUBTO>now()
        findCatlgpub(audienceFlag, ctryFlag, curTime,out);
        out.println("<!--SGOffering() "+getKey()+" noVamCatLgPubVct.size() "+noVamCatLgPubVct.size()+
        	" validCatLgPubVct.size() "+validCatLgPubVct.size()+" -->");

	}

    /********************************************************************************
    * find CATLGPUB match CATAUDIENCE, OFFCOUNTRY, CATHIDE=No, CATACTIVE=Active
    * PUBFROM<now() and PUBTO>now()
    * find CATLGPUB where:
    * CATLGPUB.CATAUDIENCE = selected audience
    * CATLGPUB.COUNTRYLIST = selected country
    * If no match is found, then do not display it in this section; however, list it at the end
    * of the report under Inactive Offerings.
    *
    * If a matching CATLGPUB is found, then do not display it in this section and list it at
    * the end of the report under Inactive Offerings IF
    * CATLGPUB.CATACTIVE = 'Inactive' (Inactive) or
    * CATLGPUB.HIDE = 'Yes' (Yes) or
    * CATLGPUB.PUBFROM > Current TimeStamp or
    * CATLGPUB.PUBTO < Current TimeStamp
    *
    *
    * Wendy	hi another question.. i look at CATLGPUB for a match on CATAUDIENCE, OFFCOUNTRY, CATHIDE=No, CATACTIVE=Active
    *     PUBFROM<now() and PUBTO>now() to determine if an offering goes into the VAM table.. my question is what CATLGPUB should be displayed in the Inactive section of the report?  is it one that meets all of the above except for CATACTIVE?
    * Wayne Kehrli	if it fails to meet the criteria to be on the VAM page
    * Wayne Kehrli	just sent you the revision
    * Wendy	what CATLGPUB is selected for the offering in the 'inactive' section if there is more than one catlgpub?
    *
    * Wayne Kehrli	we should only have one valid CATLGPUB for an "offering" given the selected CATNAV
    * so - if there are two valid one's , then list them both on the VAM page
    * if one and one - valid one on the VAM; the other one in Inactive
	*
    */
    private void findCatlgpub(String audienceFlag, String ctryFlag, String curTime,
    	javax.servlet.jsp.JspWriter out) throws java.io.IOException
    {
        // find correct catlgpub
        Vector catlgpubVct = PokUtils.getAllLinkedEntities(ofItem, ofItem.getEntityType()+"CATLGPUB", "CATLGPUB");
        out.println("<!-- findCatlgpub() "+getKey()+" catlgpubVct.size() "+catlgpubVct.size()+" -->");
        for (int i=0; i<catlgpubVct.size(); i++){
			boolean ctryMatch=false;
			boolean audienceMatch=false;
			StringBuffer sb = new StringBuffer();
        	EntityItem catItem = (EntityItem)catlgpubVct.elementAt(i);
        	// check country
			EANFlagAttribute fAtt = (EANFlagAttribute)catItem.getAttribute("OFFCOUNTRY");
			ctryMatch =(fAtt!=null && fAtt.isSelected(ctryFlag));
			sb.append("OFFCOUNTRY:"+fAtt);
        	// check audience
			fAtt = (EANFlagAttribute)catItem.getAttribute("CATAUDIENCE");
			audienceMatch =(fAtt!=null && fAtt.isSelected(audienceFlag));
			sb.append(" CATAUDIENCE:"+fAtt);
			if (ctryMatch && audienceMatch){
				boolean hideMatch=false;
				boolean pubMatch=false;
				boolean isActive = false;
				// check dates
				String pubfrom = PokUtils.getAttributeValue(catItem, "PUBFROM", "", "9999-12-31",false);
				String pubto = PokUtils.getAttributeValue(catItem, "PUBTO", "", "9999-12-31",false);
				// check hide
				fAtt = (EANFlagAttribute)catItem.getAttribute("CATHIDE");
				hideMatch =(fAtt!=null && fAtt.isSelected("No"));
				sb.append(" CATHIDE:"+fAtt);
				pubMatch = (curTime.compareTo(pubfrom)>=0) &&
					(curTime.compareTo(pubto)<=0);
				sb.append(" PUBFROM:"+pubfrom+" PUBTO:"+pubto);
				fAtt = (EANFlagAttribute)catItem.getAttribute("CATACTIVE");
				sb.append(" CATACTIVE:"+fAtt);
				isActive =(fAtt!=null && fAtt.isSelected("Active"));

				if (hideMatch && pubMatch && isActive) {
					validCatLgPubVct.add(catItem);
				}else{
					out.println("<!--findCatlgpub() "+getKey()+" "+catItem.getKey()+" does not meet all criteria: "+sb+" -->");
					noVamCatLgPubVct.add(catItem);
				}
			}else{
				out.println("<!--findCatlgpub() "+getKey()+" "+catItem.getKey()+" does not meet ctry or audience: "+sb+" -->");
				noVamCatLgPubVct.add(catItem);
			}
		}
		catlgpubVct.clear();
	}

    /********************************************************************************
    * Release memory
    */
    protected void dereference()
    {
        ofItem = null;
        sgCat = null;
        if (noVamCatLgPubVct!=null) {
    	    noVamCatLgPubVct.clear();
    	    noVamCatLgPubVct = null;
		}
		if (validCatLgPubVct!=null){
        	validCatLgPubVct.clear();
       	 	validCatLgPubVct = null;
		}
    }

    /********************************************************************************
    * set the sort key, used for sort of inactive offerings when that section is output
    */
	protected void setInactiveSort() { inactiveSort=true; }

    /********************************************************************************
    * get is active or not
    *@return boolean
    */
    protected boolean showInVAMSection() { return validCatLgPubVct.size()>0;}
    /********************************************************************************
    * get the valid CATLGPUB
    *@return Vector
    */
    protected Vector getValidCatlgpub() {return validCatLgPubVct;}      // may be empty
    /********************************************************************************
    * get the invalid CATLGPUB
    *@return Vector
    */
    protected Vector getInvalidCatlgpub() {return noVamCatLgPubVct;}      // may be empty

    /********************************************************************************
    * get the offering
    *@return EntityItem
    */
    protected EntityItem getOffering() {return ofItem;}      // MODEL, LSEO or LSEOBUNDLE
    /********************************************************************************
    * get the parent catalog
    *@return SGCatalog
    */
    protected SGCatalog getSGCatalog() { return sgCat;}
    /********************************************************************************
    * get the offering key
    *@return String
    */
	protected String getKey() { return ofItem.getKey(); }

    /********************************************************************************
    * get varcondtype for column2 - price query
    *@return String
    */
    protected String getVarCondType() { return "SEO";}

    /********************************************************************************
    * get desc for MDP General.Description
    *@return String
    */
    protected String getDescription() { return ""; }

    /********************************************************************************
    * get the sort key, used for sort of inactive offerings when that section is output
    *@return String
    */
	abstract protected String getInactiveSortKey();
    /********************************************************************************
    * get the partnumber
    *@return String
    */
    abstract protected String getPartNumber();
    /********************************************************************************
    * get marketdesc for VAM column1
    *@return String
    */
    abstract protected String getMktDesc();
    /********************************************************************************
    * get LSEO ids for column3-6
    *@return int[]
    */
    abstract protected int[] getLseoIds();
    /********************************************************************************
    * get WWSEO ids for column3-6
    *@return int[]
    */
    abstract protected int[] getWwseoIds();
    /********************************************************************************
    * get the WWENTITYTYPE = WWSEO (or LSEOBUNDLE)
    *@return String
    */
	abstract protected String getWWENTITYTYPE();
    /********************************************************************************
    * get the WWENTITYTYPE  WWENTITYID = entityid of the WWSEO (or LSEOBUNDLE)
    *@return int
    */
	abstract protected int getWWENTITYID();
}
