// Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2007, 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//
package COM.ibm.eannounce.objects;

import java.io.Serializable;
import java.util.*;
/**********************************************************************************
 * This class contains Hide, Buyable, AddToCart and Custimize for a particular
 * salestatus value
 *
 */
// $Log: SalesStatusInfo.java,v $
// Revision 1.3  2008/03/04 18:22:15  wendy
// Based on "SG FS xSeries CatalogRole ABR_PDG 20071217.doc", column order of hide and buyable is
// different between the two tables, corrected in code
//
// Revision 1.2  2008/02/01 22:10:08  wendy
// Cleanup RSA warnings
//
// Revision 1.1  2007/12/18 20:36:13  wendy
// Init for MN33416775 handling of salesstatus
//

public class SalesStatusInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String hide = null;
    private String buyable = null;
    private String custimize = null;
    private String addToCart = null;
	// CATHIDE,CATCUSTIMIZE,CATBUYABLE,CATADDTOCART have these flag values
	public static final String  NO = "No";
	public static final String  YES = "Yes";

/*
The following table is used for a Sales Status of: n/a; N/A; YA; Z0; blank.

Note 1  Note 2  Note 3  Hide    Buy AddtoCart   Customize
Hardware        Model   N   N   N   Y
Software        Model   N   N   N   Y
Servicepac      Model   Y   N   N   N

Hardware    Priced  GA SEO/BUNDLE   N   Y   Y   Y
Software    Priced  GA SEO/BUNDLE   N   Y   Y   N
Servicepac  Priced  GA SEO/BUNDLE   N   Y   Y   N

Hardware    Priced  Custom SEO/BUNDLE   N   Y   Y   Y
Software    Priced  Custom SEO/BUNDLE   N   Y   Y   N


Hardware    Not Priced  GA SEO  Y   N   N   N
Software    Not Priced  GA SEO  Y   N   N   N
Servicepac  Not Priced  GA SEO  Y   N   N   N


Software    Not Priced  Custom SEO  Y   N   N   N
Hardware    Not Priced  Custom SEO  Y   N   N   N

============================
for the other ss values: ***IMPORTANT, the order is switched, other tbl is hide, buyable, add2cart, customize

Code    Buyable Hide    Cart


Z2  No  No  No
Z3  No  Yes No
Z4  Yes No  Yes
ZJ  No  Yes No
Z7  No  Yes No
ZB  No  No  No
ZE  Yes No  Yes
ZF  No  No  No
ZG  No  Yes No
ZH  No  Yes No
ZM  Yes No  Yes
ZN  Yes No  Yes
ZQ  Yes No  Yes
ZV  No  Yes No
ZZ  No  Yes No

COFCAT	100		Hardware
COFCAT	101		Software
COFCAT	102		Service

PRCINDC	no		No
PRCINDC	yes		Yes

SPECBID	11457		No
SPECBID	11458		Yes

*/
	// CATHIDE,CATBUYABLE,CATADDTOCART,CATCUSTIMIZE have these flag values
	private static final SalesStatusInfo NNN = new SalesStatusInfo(NO, NO, NO);

	private static final Hashtable SALESSTATUS_TBL;
    static {
		SALESSTATUS_TBL = new Hashtable();

		// CATBUYABLE,CATHIDE,CATADDTOCART - *** order is switched in first 2 columns!
		SalesStatusInfo NYN = new SalesStatusInfo(NO, YES, NO);
		SalesStatusInfo YNY = new SalesStatusInfo(YES, NO,YES);

        SALESSTATUS_TBL.put("Z2",NNN);
        SALESSTATUS_TBL.put("Z3",NYN);
        SALESSTATUS_TBL.put("Z4",YNY);
        SALESSTATUS_TBL.put("ZJ",NYN);
        SALESSTATUS_TBL.put("Z7",NYN);
        SALESSTATUS_TBL.put("ZB",NNN);
        SALESSTATUS_TBL.put("ZE",YNY);
        SALESSTATUS_TBL.put("ZF",NNN);
        SALESSTATUS_TBL.put("ZG",NYN);
        SALESSTATUS_TBL.put("ZH",NYN);
        SALESSTATUS_TBL.put("ZM",YNY);
        SALESSTATUS_TBL.put("ZN",YNY);
        SALESSTATUS_TBL.put("ZQ",YNY);
        SALESSTATUS_TBL.put("ZV",NYN);
        SALESSTATUS_TBL.put("ZZ",NYN);

 		SalesStatusInfo NNNY = new SalesStatusInfo(NO, NO, NO,YES);
 		SalesStatusInfo YNNN = new SalesStatusInfo(YES, NO, NO, NO);
 		SalesStatusInfo NYYY = new SalesStatusInfo(NO, YES, YES, YES);
 		SalesStatusInfo NYYN = new SalesStatusInfo(NO, YES, YES, NO);

//The following table is used for a Sales Status of: n/a; N/A; YA; Z0; blank.

//Note 1  Note 2  Note 3  Hide    Buy AddtoCart   Customize
//Hardware        Model   N   N   N   Y
//Software        Model   N   N   N   Y
//Servicepac      Model   Y   N   N   N

		// hardware model
        SALESSTATUS_TBL.put("n/a:100",NNNY);
        SALESSTATUS_TBL.put("N/A:100",NNNY);
        SALESSTATUS_TBL.put("YA:100",NNNY);
        SALESSTATUS_TBL.put("Z0:100",NNNY);
        SALESSTATUS_TBL.put(":100",NNNY);
		// software model
        SALESSTATUS_TBL.put("n/a:101",NNNY);
        SALESSTATUS_TBL.put("N/A:101",NNNY);
        SALESSTATUS_TBL.put("YA:101",NNNY);
        SALESSTATUS_TBL.put("Z0:101",NNNY);
        SALESSTATUS_TBL.put(":101",NNNY);
		// Service model
        SALESSTATUS_TBL.put("n/a:102",YNNN);
        SALESSTATUS_TBL.put("N/A:102",YNNN);
        SALESSTATUS_TBL.put("YA:102",YNNN);
        SALESSTATUS_TBL.put("Z0:102",YNNN);
        SALESSTATUS_TBL.put(":102",YNNN);

//Hardware    Priced  GA SEO/BUNDLE   N   Y   Y   Y
//Software    Priced  GA SEO/BUNDLE   N   Y   Y   N
//Servicepac  Priced  GA SEO/BUNDLE   N   Y   Y   N

		// hardware priced ga seo/bundle
        SALESSTATUS_TBL.put("n/a:100:yes:11457",NYYY);
        SALESSTATUS_TBL.put("N/A:100:yes:11457",NYYY);
        SALESSTATUS_TBL.put("YA:100:yes:11457",NYYY);
        SALESSTATUS_TBL.put("Z0:100:yes:11457",NYYY);
        SALESSTATUS_TBL.put(":100:yes:11457",NYYY);
		// software priced ga seo/bundle
        SALESSTATUS_TBL.put("n/a:101:yes:11457",NYYN);
        SALESSTATUS_TBL.put("N/A:101:yes:11457",NYYN);
        SALESSTATUS_TBL.put("YA:101:yes:11457",NYYN);
        SALESSTATUS_TBL.put("Z0:101:yes:11457",NYYN);
        SALESSTATUS_TBL.put(":101:yes:11457",NYYN);
		// service priced ga seo/bundle
        SALESSTATUS_TBL.put("n/a:102:yes:11457",NYYN);
        SALESSTATUS_TBL.put("N/A:102:yes:11457",NYYN);
        SALESSTATUS_TBL.put("YA:102:yes:11457",NYYN);
        SALESSTATUS_TBL.put("Z0:102:yes:11457",NYYN);
        SALESSTATUS_TBL.put(":102:yes:11457",NYYN);

//Hardware    Priced  Custom SEO/BUNDLE   N   Y   Y   Y
//Software    Priced  Custom SEO/BUNDLE   N   Y   Y   N
		// hardware priced custom seo/bundle
        SALESSTATUS_TBL.put("n/a:100:yes:11458",NYYY);
        SALESSTATUS_TBL.put("N/A:100:yes:11458",NYYY);
        SALESSTATUS_TBL.put("YA:100:yes:11458",NYYY);
        SALESSTATUS_TBL.put("Z0:100:yes:11458",NYYY);
        SALESSTATUS_TBL.put(":100:yes:11458",NYYY);
		// software priced custom seo/bundle
        SALESSTATUS_TBL.put("n/a:101:yes:11458",NYYN);
        SALESSTATUS_TBL.put("N/A:101:yes:11458",NYYN);
        SALESSTATUS_TBL.put("YA:101:yes:11458",NYYN);
        SALESSTATUS_TBL.put("Z0:101:yes:11458",NYYN);
        SALESSTATUS_TBL.put(":101:yes:11458",NYYN);

//Hardware    Not Priced  GA SEO  Y   N   N   N
//Software    Not Priced  GA SEO  Y   N   N   N
//Servicepac  Not Priced  GA SEO  Y   N   N   N
		// hardware notpriced ga seo
        SALESSTATUS_TBL.put("n/a:100:no:11457",YNNN);
        SALESSTATUS_TBL.put("N/A:100:no:11457",YNNN);
        SALESSTATUS_TBL.put("YA:100:no:11457",YNNN);
        SALESSTATUS_TBL.put("Z0:100:no:11457",YNNN);
        SALESSTATUS_TBL.put(":100:no:11457",YNNN);
		// software notpriced ga seo
        SALESSTATUS_TBL.put("n/a:101:no:11457",YNNN);
        SALESSTATUS_TBL.put("N/A:101:no:11457",YNNN);
        SALESSTATUS_TBL.put("YA:101:no:11457",YNNN);
        SALESSTATUS_TBL.put("Z0:101:no:11457",YNNN);
        SALESSTATUS_TBL.put(":101:no:11457",YNNN);
		// Servicepac notpriced ga seo
        SALESSTATUS_TBL.put("n/a:102:no:11457",YNNN);
        SALESSTATUS_TBL.put("N/A:102:no:11457",YNNN);
        SALESSTATUS_TBL.put("YA:102:no:11457",YNNN);
        SALESSTATUS_TBL.put("Z0:102:no:11457",YNNN);
        SALESSTATUS_TBL.put(":102:no:11457",YNNN);

//Hardware    Not Priced  Custom SEO  Y   N   N   N
//Software    Not Priced  Custom SEO  Y   N   N   N
		// hardware notpriced custom seo/bundle
        SALESSTATUS_TBL.put("n/a:100:no:11458",YNNN);
        SALESSTATUS_TBL.put("N/A:100:no:11458",YNNN);
        SALESSTATUS_TBL.put("YA:100:no:11458",YNNN);
        SALESSTATUS_TBL.put("Z0:100:no:11458",YNNN);
        SALESSTATUS_TBL.put(":100:no:11458",YNNN);
		// Software notpriced custom seo/bundle
        SALESSTATUS_TBL.put("n/a:101:no:11458",YNNN);
        SALESSTATUS_TBL.put("N/A:101:no:11458",YNNN);
        SALESSTATUS_TBL.put("YA:101:no:11458",YNNN);
        SALESSTATUS_TBL.put("Z0:101:no:11458",YNNN);
        SALESSTATUS_TBL.put(":101:no:11458",YNNN);

	}

    /********************************************************************************
    * find sales status record
    *
    *1.	MODEL.COFCAT = {Hardware, Software, Service}
	*2.	LSEO.PRCINDC = 'Yes' implies Priced
	*	LSEOBUNDLE assumed to be Priced
	*3.	SPECBID = 'Yes' implies Custom
	*	LSEO found on WWSEO; LSEOBUNDLE
	*
    * @param ss String with salesstatus
    * @param key String with model.cofcat:priced:specbid
    * @return SalesStatusInfo
    */
    public static SalesStatusInfo getSalesStatusInfo(String ss, String key){
		SalesStatusInfo sshbac = (SalesStatusInfo)SALESSTATUS_TBL.get(ss); // look for simple case first
		if (sshbac==null){
			sshbac = (SalesStatusInfo)SALESSTATUS_TBL.get(ss+":"+key);
		}
		if (sshbac==null){
			System.err.println("ERROR no SalesStatusInfo found for "+ss+":"+key);
			sshbac = NNN;
		}
		return sshbac;
	}

    /********************************************************************************
    * check sales status table to see if this is a simple or complex ss
    *complex need this info:
    *1.	MODEL.COFCAT = {Hardware, Software, Service}
	*2.	LSEO.PRCINDC = 'Yes' implies Priced
	*	LSEOBUNDLE assumed to be Priced
	*3.	SPECBID = 'Yes' implies Custom
	*	LSEO found on WWSEO; LSEOBUNDLE
	*
    * @param ss String with just salesstatus
    * @return boolean
    */
    public static boolean isSimpleSS(String ss){
		SalesStatusInfo sshbac = (SalesStatusInfo)SALESSTATUS_TBL.get(ss); // look for simple case
		return (sshbac!=null);
	}
    /**
     * SalesStatusInfo
     *
     * @param h hide value
     * @param b buyable value
     * @param a addtocart value
     * @param c custimize value
     */
    public SalesStatusInfo(String h, String b, String a, String c) {
		hide = h;
		buyable = b;
		addToCart = a;
		custimize = c;
    }

    /**
     * SalesStatusInfo - order in tables is switched
     *
     * @param b buyable value
     * @param h hide value
     * @param a addtocart value
     */
    public SalesStatusInfo(String b, String h, String a) {
		this(h,b,a,NO);
    }
    /**
     * get buyable flag for this SalesStatus
     * @return String
     */
    public String getBuyable() {
        return buyable;
    }

    /**
     * get hide flag for this SalesStatus
     * @return String
     */
    public String getHide() {
        return hide;
    }

    /**
     * get addtocart flag for this SalesStatus
     * @return String
     */
    public String getAddToCart() {
        return addToCart;
    }

    /**
     * get custimize flag for this SalesStatus
     * @return String
     */
    public String getCustimize() {
        return custimize;
    }

    /**
     * String value used for debug
     * @return String
     */
	public String toString()
	{
		StringBuffer sb = new StringBuffer("Hide:"+hide);
		sb.append(" Buyable:"+buyable);
		sb.append(" AddToCart:"+addToCart);
		sb.append(" Custimize:"+custimize);

		return sb.toString();
	}
}
