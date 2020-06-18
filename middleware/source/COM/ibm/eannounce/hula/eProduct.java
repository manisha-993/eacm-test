//
// Copyright (c) 2004, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: eProduct.java,v $
// Revision 1.146  2005/03/03 21:25:15  dave
// NEW_LINE on EAN Foundation
//
// Revision 1.145  2005/02/09 22:13:43  dave
// more JTest Cleanup
//
// Revision 1.144  2005/01/18 21:33:08  dave
// removing the debug parms from code (sp internalized them)
//
// Revision 1.143  2005/01/04 22:45:10  gregg
// product detail relator stuff
//
// Revision 1.142  2004/12/09 18:53:35  gregg
// use eRecordCollection.getProductDetail
//
// Revision 1.141  2004/12/01 22:49:45  gregg
// more fix
//
// Revision 1.140  2004/12/01 22:22:16  gregg
// more fixes
//
// Revision 1.139  2004/12/01 21:58:23  gregg
// fixes
//
// Revision 1.138  2004/12/01 21:49:47  gregg
// adapting to the whole ProductDetailRec paradigm
//
// Revision 1.137  2004/11/10 17:49:38  gregg
// some rules iterating in update
//
// Revision 1.136  2004/11/09 22:56:35  gregg
// remove debugs
//
// Revision 1.135  2004/11/09 22:46:37  gregg
// more isEquivalent, etc
//
// Revision 1.134  2004/11/09 19:30:28  gregg
// isEquivalent != equals!!!
//
// Revision 1.133  2004/11/09 00:19:52  gregg
// slowly adding in debugs
//
// Revision 1.132  2004/11/08 21:09:38  gregg
// fix
//
// Revision 1.131  2004/11/08 20:52:07  gregg
// to many debugs these days ...
// we need to start over on these to see whats goin on
//
// Revision 1.130  2004/11/05 19:31:07  gregg
// productDetail update logic is a changin' ...
//
// Revision 1.129  2004/11/05 18:43:42  gregg
// update fix
//
// Revision 1.128  2004/11/04 21:36:03  gregg
// null ptr/ constructor fix
//
// Revision 1.127  2004/10/29 21:20:54  gregg
// some fixes
//
// Revision 1.126  2004/10/29 21:08:39  gregg
// working on new product update logic
//
// Revision 1.125  2004/10/28 21:46:12  gregg
// new constructor
//
// Revision 1.124  2004/10/27 20:30:50  gregg
// some updates - think we're working again w/ eDocAdapter class
//
// Revision 1.123  2004/10/15 21:02:11  gregg
// focus on loops: settin temp variables to save access times
//
// Revision 1.122  2004/10/15 17:25:25  gregg
// dropping in some finals
//
// Revision 1.121  2004/10/14 20:02:17  dave
// speed up
//
// Revision 1.120  2004/09/30 02:58:36  dave
// less trace
//
// Revision 1.119  2004/09/30 02:29:17  dave
// stopwatch
//
// Revision 1.118  2004/09/30 02:17:11  dave
// more trace and chunky monkey display
//
// Revision 1.117  2004/09/30 01:47:04  dave
// more trace
//
// Revision 1.116  2004/09/30 01:11:55  dave
// need to free pending
//
// Revision 1.115  2004/09/30 00:45:30  dave
// more syntax
//
// Revision 1.114  2004/09/30 00:36:00  dave
// more syntax
//
// Revision 1.113  2004/09/30 00:22:39  dave
// syntax
//
// Revision 1.112  2004/09/30 00:02:20  dave
// new SP's for performance run testing
//
// Revision 1.111  2004/09/29 21:53:40  gregg
// more self correcting logic .... in case of an exception updating, we try for a new complete snapshot of our product
//
// Revision 1.110  2004/09/29 21:04:36  gregg
// logging some PROD updates for timing purposes
//
// Revision 1.109  2004/09/29 17:55:05  gregg
// auto-fix missing product header row.
//
// Revision 1.108  2004/09/28 23:29:37  gregg
// debugs and other stuff
//
// Revision 1.107  2004/09/28 20:49:58  gregg
// eProduct.deleteProductDetails method
//
// Revision 1.106  2004/09/28 17:56:28  gregg
// more tuning
//
// Revision 1.105  2004/09/24 22:00:19  gregg
// de-singletonize PreparedStatementCollection.
// This is now accessed through eProductUpdater.
//
// Revision 1.104  2004/09/24 02:24:36  dave
// more trace
//
// Revision 1.103  2004/09/24 01:56:03  dave
// more SPEW
//
// Revision 1.102  2004/09/24 00:58:27  dave
// setting spew logging
//
// Revision 1.101  2004/09/24 00:54:29  dave
// more trace
//
// Revision 1.100  2004/09/23 18:31:53  dave
// some syntax
//
// Revision 1.99  2004/09/23 18:22:11  dave
// shoring up Layout Context
//
// Revision 1.98  2004/09/23 18:06:14  dave
// going for layout context in preperation for VAR, CTO, etc
//
// Revision 1.97  2004/09/23 16:38:39  gregg
// remving an err
//
// Revision 1.96  2004/09/23 15:58:25  gregg
// remove debugs
//
// Revision 1.95  2004/09/22 21:56:52  gregg
// wereSequencesUpdated() on eProductDetail + fix isEquivalent
//
// Revision 1.94  2004/09/22 18:51:30  gregg
// some Database passing for Steve
//
// Revision 1.93  2004/09/22 17:49:59  gregg
// some updates
//
// Revision 1.92  2004/09/22 15:48:25  gregg
// some cleanup
//
// Revision 1.91  2004/09/21 21:39:14  gregg
// remove NLS constraint on gbl9989
//
// Revision 1.90  2004/09/21 21:36:57  gregg
// remove nlsid constraint from gbl9990
//
// Revision 1.89  2004/09/21 21:19:36  gregg
// derived detail null ptr fix
//
// Revision 1.88  2004/09/21 18:18:37  gregg
// SQL fix
//
// Revision 1.87  2004/09/21 17:09:29  gregg
// some PreparedStatement reuse
//
// Revision 1.86  2004/09/21 16:02:57  gregg
// break out of constructor before trying to build any detail if product is not in database
//
// Revision 1.85  2004/09/20 21:54:01  gregg
// yet another getProductDetail(strKey)
//
// Revision 1.84  2004/09/17 19:18:15  gregg
// AUDIENCE, CCECONTROLOVERRIDES
//
// Revision 1.83  2004/09/17 17:48:08  gregg
// 12 new control flags - init to 0
//
// Revision 1.82  2004/09/16 17:21:41  gregg
// limit redundant updateSequence calls.
//
// Revision 1.81  2004/09/15 18:48:16  gregg
// removing debugs, conserving object creation, re-route logging
//
// Revision 1.80  2004/09/15 17:19:11  gregg
// add NLSID to gbl9990
//
// Revision 1.79  2004/09/14 23:53:33  dave
// more fixes
//
// Revision 1.78  2004/09/14 23:41:29  dave
// some small adjustments
//
// Revision 1.77  2004/09/14 22:11:23  gregg
// freeStatements
//
// Revision 1.76  2004/09/14 21:33:58  gregg
// converting to SPs
//
// Revision 1.75  2004/09/14 21:06:43  gregg
// remove build pricing group in constructor
//
// Revision 1.74  2004/09/14 19:33:28  gregg
// more timings
//
// Revision 1.73  2004/09/14 19:16:48  gregg
// some timings
//
// Revision 1.72  2004/09/14 18:23:46  gregg
// avoid closing stored PreparedStatements
//
// Revision 1.71  2004/09/14 18:19:12  gregg
// getProductCommitChunk(), store more PreparedStatements.
//
// Revision 1.70  2004/09/13 20:54:29  gregg
// some getKey() stuff fer performance
//
// Revision 1.69  2004/09/09 21:14:11  gregg
// LEAVE BLOB VALUES OUT OF CONSTRUCTOR
//
// Revision 1.68  2004/09/09 20:09:32  gregg
// removeAllProductDetails()
//
// Revision 1.67  2004/09/09 19:48:59  gregg
// delete
//
// Revision 1.66  2004/09/09 19:48:02  gregg
// delete method
//
// Revision 1.65  2004/09/09 18:40:32  gregg
// more queueUnpublishAllDetails(
//
// Revision 1.64  2004/09/09 18:15:46  gregg
// fixx unpublishAllDetails(
//
// Revision 1.63  2004/09/09 18:06:35  gregg
// queueAllDetailForUnpublish
//
// Revision 1.62  2004/09/09 17:02:15  gregg
// unpublishAllProductDetails()
//
// Revision 1.61  2004/09/09 16:24:50  gregg
// required detail check logic
//
// Revision 1.60  2004/09/09 05:48:03  dave
// abstract rule broken
//
// Revision 1.59  2004/09/09 05:30:20  dave
// testing out block commit
//
// Revision 1.58  2004/09/07 21:46:08  dave
// must catch the ball on constructor
//
// Revision 1.57  2004/09/07 21:07:28  dave
// fixes
//
// Revision 1.56  2004/09/07 21:01:04  dave
// suintechs
//
// Revision 1.55  2004/09/07 20:49:25  dave
// two flavors of layoutgroups (single, multi)
//
// Revision 1.54  2004/09/03 21:27:28  dave
// removing NLS contraint
//
// Revision 1.53  2004/09/03 19:18:11  gregg
// more blob stuff
//
// Revision 1.52  2004/09/03 18:24:32  gregg
// fix
//
// Revision 1.51  2004/09/03 18:22:25  gregg
// blobs
//
// Revision 1.50  2004/09/03 00:00:56  gregg
// more
//
// Revision 1.49  2004/09/02 23:53:54  gregg
// wasLongValueChanged
//
// Revision 1.48  2004/09/02 23:31:03  gregg
// trim down update logic
//
// Revision 1.47  2004/09/02 23:17:33  gregg
// some update lt logic fix
//
// Revision 1.46  2004/09/02 23:04:25  gregg
// some long detail updating trickery
//
// Revision 1.45  2004/09/02 21:55:31  gregg
// collectionID passing for Longs
//
// Revision 1.44  2004/09/02 21:03:29  gregg
// use eRecordCollection to manage longtext records
//
// Revision 1.43  2004/09/01 22:26:24  gregg
// some long text fishing
//
// Revision 1.42  2004/09/01 19:09:16  gregg
// fix
//
// Revision 1.41  2004/09/01 19:04:19  gregg
// remove eProductUpdater param in eProduct constructor
//
// Revision 1.40  2004/09/01 19:02:01  gregg
// null ptr fix -- get now time from ePtoduct parent...
//
// Revision 1.39  2004/09/01 18:49:17  gregg
// debug
//
// Revision 1.38  2004/09/01 16:18:51  gregg
// changing blob/longtext tables
//
// Revision 1.37  2004/09/01 16:05:51  gregg
// changing blob/longtext tables
//
// Revision 1.36  2004/09/01 15:40:55  gregg
// more columns!
//
// Revision 1.35  2004/08/31 23:39:25  gregg
// catch some Exceptions
//
// Revision 1.34  2004/08/31 23:26:29  gregg
// new columns added
//
// Revision 1.33  2004/08/31 23:08:43  gregg
// SQL fix
//
// Revision 1.32  2004/08/31 22:47:50  gregg
// load ProductDetails from blob, longtext tables
//
// Revision 1.31  2004/08/31 22:31:12  gregg
// classCastFix
//
// Revision 1.30  2004/08/31 22:16:31  gregg
// add table name in eTableRecord constructor
//
// Revision 1.29  2004/08/31 21:55:53  gregg
// one mo fix
//
// Revision 1.28  2004/08/31 21:49:59  gregg
// getProductDetail(eLayoutItem)
//
// Revision 1.27  2004/08/31 21:29:52  gregg
// Constructor now throws generic Exception
//
// Revision 1.26  2004/08/31 17:39:07  gregg
// removing some debugs
//
// Revision 1.25  2004/08/31 17:04:26  gregg
// add PricingGroup member
//
// Revision 1.24  2004/08/30 23:28:49  gregg
// be brief on toString
//
// Revision 1.23  2004/08/30 21:55:02  gregg
// working on rollup rules
//
// Revision 1.22  2004/08/30 20:17:51  gregg
// more inbound/outbound rules
//
// Revision 1.21  2004/08/30 19:33:25  gregg
// inbound/outbound rule implementation
//
// Revision 1.20  2004/08/30 18:11:40  gregg
// fix
//
// Revision 1.19  2004/08/30 18:03:34  gregg
// setting up for outbound/inbound rules processing
//
// Revision 1.18  2004/08/26 21:18:59  gregg
// toString method
//
// Revision 1.17  2004/08/25 20:44:19  gregg
// some more update logic
//
// Revision 1.16  2004/08/25 20:32:32  gregg
// some update vs. unpublish logic
//
// Revision 1.15  2004/08/25 20:08:56  gregg
// sql fix
//
// Revision 1.14  2004/08/25 19:46:08  gregg
// let's also pull Unpublished ProductDetails
//
// Revision 1.13  2004/08/25 18:58:08  gregg
// more logging
//
// Revision 1.12  2004/08/25 18:48:42  gregg
// sum logging
//
// Revision 1.11  2004/08/24 22:35:49  gregg
// implements EANComparable in eTableRecord for nifty column sorting
//
// Revision 1.10  2004/08/24 18:12:09  gregg
// set Profile's NLSID
//
// Revision 1.9  2004/08/23 23:39:29  gregg
// getVersion() method
//
// Revision 1.8  2004/08/23 23:14:03  gregg
// more rules
//
// Revision 1.7  2004/08/23 22:28:33  gregg
// some debugs
//
// Revision 1.6  2004/08/23 21:34:48  gregg
// some ConcatRules
//
// Revision 1.5  2004/08/23 21:03:32  gregg
// compile fix
//
// Revision 1.4  2004/08/23 20:58:06  gregg
// some rearranging to decouple eProductUpdater/eProduct objects a bit
//
// Revision 1.3  2004/08/23 20:34:37  gregg
// change signature of eProduct constructor -- pass in Database object
//
// Revision 1.2  2004/08/23 16:44:25  gregg
// update
//
//

package COM.ibm.eannounce.hula;

import COM.ibm.eannounce.objects.EANComparator;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.ReturnStatus;
import COM.ibm.opicmpdh.middleware.Stopwatch;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * eProduct
 *
 * @author David Bigelow
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class eProduct extends eTableRecord {

    static final long serialVersionUID = 20011106L;
    
    /**
     * FIELD
     */
    public static final String TABLE_NAME = "PRODUCT";

    /**
     * FIELD
     */
    public static final String ENTERPRISE = "ENTERPRISE";
    /**
     * FIELD
     */
    public static final String ENTITYTYPE = "ENTITYTYPE";
    /**
     * FIELD
     */
    public static final String ENTITYID = "ENTITYID";
    /**
     * FIELD
     */
    public static final String NLSID = "NLSID";
    /**
     * FIELD
     */
    public static final String COUNTRYCODE = "COUNTRYCODE";
    /**
     * FIELD
     */
    public static final String GENAREANAME = "GENAREANAME";
    /**
     * FIELD
     */
    public static final String GENAREANAME_FC = "GENAREANAME_FC";
    /**
     * FIELD
     */
    public static final String GENAREANAMEREGION = "GENAREANAMEREGION";
    /**
     * FIELD
     */
    public static final String GENAREANAMEREGION_FC = "GENAREANAMEREGION_FC";
    /**
     * FIELD
     */
    public static final String STATUS = "STATUS";
    /**
     * FIELD
     */
    public static final String STATUS_FC = "STATUS_FC";
    /**
     * FIELD
     */
    public static final String MODELNAME = "MODELNAME";
    /**
     * FIELD
     */
    public static final String WWPARTNUMBER = "WWPARTNUMBER";
    /**
     * FIELD
     */
    public static final String CONTRACTINVTITLE = "CONTRACTINVTITLE";
    /**
     * FIELD
     */
    public static final String TYPE = "TYPE";
    /**
     * FIELD
     */
    public static final String TYPE_FC = "TYPE_FC";
    /**
     * FIELD
     */
    public static final String INSTALLOPT = "INSTALLOPT";
    /**
     * FIELD
     */
    public static final String INSTALLOPT_FC = "INSTALLOPT_FC";
    /**
     * FIELD
     */
    public static final String PARTNUMBER = "PARTNUMBER";
    /**
     * FIELD
     */
    public static final String PARTNUMBERDESC = "PARTNUMBERDESC";
    /**
     * FIELD
     */
    public static final String SPECIALBID = "SPECIALBID";
    /**
     * FIELD
     */
    public static final String SPECIALBID_FC = "SPECIALBID_FC";
    /**
     * FIELD
     */
    public static final String RATECARDCODE = "RATECARDCODE";
    /**
     * FIELD
     */
    public static final String RATECARDCODE_FC = "RATECARDCODE_FC";
    /**
     * FIELD
     */
    public static final String UNSPSC = "UNSPSC";
    /**
     * FIELD
     */
    public static final String UNSPSC_FC = "UNSPSC_FC";
    /**
     * FIELD
     */
    public static final String UNUOM = "UNUOM";
    /**
     * FIELD
     */
    public static final String UNUOM_FC = "UNUOM_FC";
    /**
     * FIELD
     */
    public static final String ANNOUNCEMENTDATE = "ANNOUNCEMENTDATE";
    /**
     * FIELD
     */
    public static final String WITHDRAWLDATE = "WITHDRAWLDATE";
    /**
     * FIELD
     */
    public static final String FOTDATE = "FOTDATE";
    /**
     * FIELD
     */
    public static final String PROJECTID = "PROJECTID";
    /**
     * FIELD
     */
    public static final String PUBLISHFLAG = "PUBLISHFLAG";
    /**
     * FIELD
     */
    public static final String VALFROM = "VALFROM";
    /**
     * FIELD
     */
    public static final String VALID_FLAG = "VALID_FLAG";
    /**
     * FIELD
     */
    public static final String FOTQHECK = "FOTQHECK";
    /**
     * FIELD
     */
    public static final String FINALQCHECK = "FINALQCHECK";
    /**
     * FIELD
     */
    public static final String FOTQCHECKOVERRIDE = "FOTQCHECKOVERRIDE";
    /**
     * FIELD
     */
    public static final String FINALQCHECKOVERRIDE = "FINALQCHECKOVERRIDE";
    /**
     * FIELD
     */
    public static final String LEAUD = "LEAUD";
    /**
     * FIELD
     */
    public static final String LENEWFLAG = "LENEWFLAG";
    /**
     * FIELD
     */
    public static final String LEBUYABLE = "LEBUYABLE";
    /**
     * FIELD
     */
    public static final String LECUSTCART = "LECUSTCART";
    /**
     * FIELD
     */
    public static final String SHOPAUD = "SHOPAUD";
    /**
     * FIELD
     */
    public static final String SHOPNEWFLAG = "SHOPNEWFLAG";
    /**
     * FIELD
     */
    public static final String SHOPBUYABLE = "SHOPBUYABLE";
    /**
     * FIELD
     */
    public static final String SHOPCUSTCART = "SHOPCUSTCART";
    /**
     * FIELD
     */
    public static final String DACAUD = "DACAUD";
    /**
     * FIELD
     */
    public static final String DACNEWFLAG = "DACNEWFLAG";
    /**
     * FIELD
     */
    public static final String DACBUYABLE = "DACBUYABLE";
    /**
     * FIELD
     */
    public static final String DACCUSTCART = "DACCUSTCART";
    /**
     * FIELD
     */
    public static final String AUDIENCE = "AUDIENCE";
    /**
     * FIELD
     */
    public static final String CCECONTROLOVERRIDES = "CCECONTROLOVERRIDES";
    /**
     * FIELD
     */
    public static final String RECID = "RECID";

    static {
        eTable.getETable(TABLE_NAME).makeStringColumn(ENTERPRISE, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(ENTITYTYPE, 32);
        eTable.getETable(TABLE_NAME).makeIntColumn(ENTITYID);
        eTable.getETable(TABLE_NAME).makeIntColumn(NLSID);
        eTable.getETable(TABLE_NAME).makeStringColumn(COUNTRYCODE, 2);
        eTable.getETable(TABLE_NAME).makeStringColumn(GENAREANAME, 32);
        eTable.getETable(TABLE_NAME).makeStringColumn(GENAREANAME_FC, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(GENAREANAMEREGION, 45);
        eTable.getETable(TABLE_NAME).makeStringColumn(GENAREANAMEREGION_FC, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(STATUS, 32);
        eTable.getETable(TABLE_NAME).makeStringColumn(STATUS_FC, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(MODELNAME, 16);
        eTable.getETable(TABLE_NAME).makeStringColumn(WWPARTNUMBER, 16);
        eTable.getETable(TABLE_NAME).makeStringColumn(CONTRACTINVTITLE, 50);
        eTable.getETable(TABLE_NAME).makeStringColumn(TYPE, 20);
        eTable.getETable(TABLE_NAME).makeStringColumn(TYPE_FC, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(INSTALLOPT, 3);
        eTable.getETable(TABLE_NAME).makeStringColumn(INSTALLOPT_FC, 4);
        eTable.getETable(TABLE_NAME).makeStringColumn(PARTNUMBER, 16);
        eTable.getETable(TABLE_NAME).makeStringColumn(PARTNUMBERDESC, 50);
        eTable.getETable(TABLE_NAME).makeStringColumn(SPECIALBID, 6);
        eTable.getETable(TABLE_NAME).makeStringColumn(SPECIALBID_FC, 4);
        eTable.getETable(TABLE_NAME).makeStringColumn(RATECARDCODE, 64);
        eTable.getETable(TABLE_NAME).makeStringColumn(RATECARDCODE_FC, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(UNSPSC, 125);
        eTable.getETable(TABLE_NAME).makeStringColumn(UNSPSC_FC, 8);
        eTable.getETable(TABLE_NAME).makeStringColumn(UNUOM, 25);
        eTable.getETable(TABLE_NAME).makeStringColumn(UNUOM_FC, 25);
        eTable.getETable(TABLE_NAME).makeStringColumn(ANNOUNCEMENTDATE, 10);
        eTable.getETable(TABLE_NAME).makeStringColumn(WITHDRAWLDATE, 10);
        eTable.getETable(TABLE_NAME).makeStringColumn(FOTDATE, 10);
        eTable.getETable(TABLE_NAME).makeIntColumn(PROJECTID);
        eTable.getETable(TABLE_NAME).makeStringColumn(PUBLISHFLAG, 1);
        eTable.getETable(TABLE_NAME).makeStringColumn(VALFROM, 26);
        eTable.getETable(TABLE_NAME).makeIntColumn(VALID_FLAG);
        eTable.getETable(TABLE_NAME).makeIntColumn(FOTQHECK);
        eTable.getETable(TABLE_NAME).makeIntColumn(FINALQCHECK);
        eTable.getETable(TABLE_NAME).makeIntColumn(FOTQCHECKOVERRIDE);
        eTable.getETable(TABLE_NAME).makeIntColumn(FINALQCHECKOVERRIDE);
        eTable.getETable(TABLE_NAME).makeIntColumn(LEAUD);
        eTable.getETable(TABLE_NAME).makeIntColumn(LENEWFLAG);
        eTable.getETable(TABLE_NAME).makeIntColumn(LEBUYABLE);
        eTable.getETable(TABLE_NAME).makeIntColumn(LECUSTCART);
        eTable.getETable(TABLE_NAME).makeIntColumn(SHOPAUD);
        eTable.getETable(TABLE_NAME).makeIntColumn(SHOPNEWFLAG);
        eTable.getETable(TABLE_NAME).makeIntColumn(SHOPBUYABLE);
        eTable.getETable(TABLE_NAME).makeIntColumn(SHOPCUSTCART);
        eTable.getETable(TABLE_NAME).makeIntColumn(DACAUD);
        eTable.getETable(TABLE_NAME).makeIntColumn(DACNEWFLAG);
        eTable.getETable(TABLE_NAME).makeIntColumn(DACBUYABLE);
        eTable.getETable(TABLE_NAME).makeIntColumn(DACCUSTCART);
        eTable.getETable(TABLE_NAME).makeIntColumn(RECID);
    }

    /**
     * FIELD
     */
    public static final String UNPUBLISH_VAL = "N";
    /**
     * FIELD
     */
    public static final String PUBLISH_VAL = "Y";
    /**
     * FIELD
     */
    public static final int VALID_FLAG_YES_VAL = 1;
    /**
     * FIELD
     */
    public static final int VALID_FLAG_NO_VAL = 0;

    private eProductUpdater m_productUpdaterParent = null;
    private ePricingGroup m_pricingGroup = null;
    private Hashtable m_hashLayoutKeys = null;
    private Hashtable m_hshCounts = null;

    // Lets assume that this Product is not in the
    // ods yet
    private boolean m_bInsert = false;

    /**
     * eProduct
     *
     * @param _prof
     * @param _strEntityType
     * @param _iEntityID
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public eProduct(Profile _prof, String _strEntityType, int _iEntityID) throws Exception {
        super(null, _prof, (_prof.getEnterprise() + ":" + _strEntityType + ":" + _iEntityID + ":" + _prof.getReadLanguage().getNLSID()), TABLE_NAME);
        m_hashLayoutKeys = new Hashtable();
        m_hshCounts = new Hashtable();

        putStringVal(ENTERPRISE, _prof.getEnterprise());
        putStringVal(ENTITYTYPE, _strEntityType);
        putIntVal(ENTITYID, _iEntityID);
        putIntVal(NLSID, getProfile().getReadLanguage().getNLSID());
    }

    /**
     * eProduct
     *
     * @param _dbODS
     * @param _prod
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public eProduct(Database _dbODS, eProduct _prod) throws Exception {
        this(_dbODS, _prod.getProfile(), _prod.getStringVal(ENTITYTYPE), _prod.getIntVal(ENTITYID));
    }

    /**
     * eProduct
     *
     * @param _dbODS
     * @param _prof
     * @param _strEntityType
     * @param _iEntityID
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public eProduct(Database _dbODS, Profile _prof, String _strEntityType, int _iEntityID) throws Exception {

        this(_prof, _strEntityType, _iEntityID);
        
        try {
            
            ResultSet rsProduct = null;
            ReturnDataResultSet rdrsProduct = null;
            ResultSet rsProdDetail = null;
            ReturnDataResultSet rdrsProdDetail = null;
        
            int iBlobCount = 0;
            int iLTCount = 0;

            try {
                rsProduct = _dbODS.callGBL9990(new ReturnStatus(-1), _prof.getEnterprise(), _strEntityType, _iEntityID, getIntVal(NLSID));
                rdrsProduct = new ReturnDataResultSet(rsProduct);
            } finally {
                rsProduct.close();
                rsProduct = null;
                _dbODS.freeStatement();
                _dbODS.isPending();
            }
            if (rdrsProduct.getRowCount() > 0) {
                m_bInsert = false;
                for (int i = 0; i < getColumnCount(); i++) {
                    String strColumnKey = getColumnKey(i);
                    if (strColumnKey.equals(ENTERPRISE) || strColumnKey.equals(ENTITYTYPE) || strColumnKey.equals(ENTITYID) || strColumnKey.equals(NLSID)) {
                        continue;
                    }
                    if (getColumnType(i) == INT) {
                        putIntVal(getColumnKey(i), rdrsProduct.getColumnInt(0, i));
                    } else {
                        putStringVal(getColumnKey(i), rdrsProduct.getColumn(0, i));
                    }
                }
            } else {
                // this is to fix the case where there is no PRODUCT header record, but may be some details...
                // ... just in case....
                deleteProductDetails(_dbODS);
                return;
            }
    
            // populate most recent PRODUCTDETAILS from ODS...if getDetails is true
    
            try {
                rsProdDetail = _dbODS.callGBL9989(new ReturnStatus(-1), _prof.getEnterprise(), _strEntityType, _iEntityID, getIntVal(NLSID));
                rdrsProdDetail = new ReturnDataResultSet(rsProdDetail);
            } finally {
                rsProdDetail.close();
                rsProdDetail = null;
                _dbODS.freeStatement();
                _dbODS.isPending();
                _dbODS.commit();
            }
            
            for (int i = 0; i < rdrsProdDetail.getRowCount(); i++) {
    
                int iNLSID_pd = rdrsProdDetail.getColumnInt(i, 1);
                String strChildType_pd = rdrsProdDetail.getColumn(i, 3);
                int iChildID_pd = rdrsProdDetail.getColumnInt(i, 4);
                int iGSequence_pd = rdrsProdDetail.getColumnInt(i, 5);
                int iGVisible_pd = rdrsProdDetail.getColumnInt(i, 6);
                int iISequence_pd = rdrsProdDetail.getColumnInt(i, 7);
                int iIVisible_pd = rdrsProdDetail.getColumnInt(i, 8);
                String strHeritage_pd = rdrsProdDetail.getColumn(i, 9);
                String strAttributeCode_pd = rdrsProdDetail.getColumn(i, 10);
                String strAttributeType_pd = rdrsProdDetail.getColumn(i, 11);
                String strFlagCode_pd = rdrsProdDetail.getColumn(i, 12);
                String strAttributeValue_pd = rdrsProdDetail.getColumn(i, 13);
                String strPublishFlag_pd = rdrsProdDetail.getColumn(i, 14);
                String strValFrom_pd = rdrsProdDetail.getColumn(i, 15);
                int iRecID = rdrsProdDetail.getColumnInt(i, 16);
    
                eProductDetail prodDetail = null;
    
                D.ebug(D.EBUG_SPEW, "gbl9989 answer:" + rdrsProdDetail.getRow(i));
    
                if (strAttributeType_pd.equals("L")) {
                    ResultSet rsProdDetailLong = null;
                    ReturnDataResultSet rdrsProdDetailLong = null;
                    String strAttValLong = "";
    
                    iLTCount++;
                    try {
                        rsProdDetailLong = _dbODS.callGBL9988(new ReturnStatus(-1), _prof.getEnterprise(), strChildType_pd, iChildID_pd, strAttributeCode_pd, iNLSID_pd);
                        rdrsProdDetailLong = new ReturnDataResultSet(rsProdDetailLong);
                    } finally {
                        rsProdDetailLong.close();
                        rsProdDetailLong = null;
                        _dbODS.freeStatement();
                        _dbODS.isPending();
                    }
                    if (rdrsProdDetailLong.getRowCount() > 0) {
                        strAttValLong = rdrsProdDetailLong.getColumn(0, 0);
                    }
                    prodDetail =
                        eRecordCollection.getRecordCollection().getProductDetail(
                            this,
                            eRecordCollection.FROM_ODS,
                            strChildType_pd,
                            iChildID_pd,
                            iGSequence_pd,
                            iGVisible_pd,
                            iISequence_pd,
                            iIVisible_pd,
                            strHeritage_pd,
                            strAttributeCode_pd,
                            strAttValLong,
                            strPublishFlag_pd,
                            strValFrom_pd,
                            eRecordCollection.FROM_ODS,
                            iRecID);
                } else if (strAttributeType_pd.equals("B")) {
    
                    ResultSet rsProdDetailBlob = null;
                    byte[] baAttValBlob = new byte[0];
                    COM.ibm.opicmpdh.objects.Blob blob = null;
                    String strAttFileName = null;
                    iBlobCount++;
                    
                    try {
                        rsProdDetailBlob = _dbODS.callGBL9987(new ReturnStatus(-1), _prof.getEnterprise(), strChildType_pd, iChildID_pd, strAttributeCode_pd, iNLSID_pd);
                        while (rsProdDetailBlob.next()) {
                            strAttFileName = rsProdDetailBlob.getString(1);
                        }
                    } finally {
                        rsProdDetailBlob.close();
                        rsProdDetailBlob = null;
                        _dbODS.freeStatement();
                        _dbODS.isPending();
                    }
                    
                    blob = new COM.ibm.opicmpdh.objects.Blob(getProfile().getEnterprise(), strChildType_pd, iChildID_pd, strAttributeCode_pd, baAttValBlob, strAttFileName, getProfile().getReadLanguage().getNLSID());
    
                    prodDetail =
                        eRecordCollection.getRecordCollection().getProductDetail(
                            this,
                            eRecordCollection.FROM_ODS,
                            strChildType_pd,
                            iChildID_pd,
                            iGSequence_pd,
                            iGVisible_pd,
                            iISequence_pd,
                            iIVisible_pd,
                            strHeritage_pd,
                            strAttributeCode_pd,
                            strAttFileName,
                            blob,
                            strPublishFlag_pd,
                            strValFrom_pd,
                            eRecordCollection.FROM_ODS,
                            iRecID);
                } else {
    
                    prodDetail =
                        eRecordCollection.getRecordCollection().getProductDetail(
                            this,
                            eRecordCollection.FROM_ODS,
                            strChildType_pd,
                            iChildID_pd,
                            iGSequence_pd,
                            iGVisible_pd,
                            iISequence_pd,
                            iIVisible_pd,
                            strHeritage_pd,
                            strAttributeCode_pd,
                            strAttributeType_pd,
                            strFlagCode_pd,
                            strAttributeValue_pd,
                            strPublishFlag_pd,
                            strValFrom_pd,
                            iRecID);
                }
    
                putProductDetail(prodDetail);
            }
    
            // these might fail when building a Product from scratch
            try {
                processOutBoundRules(_dbODS);
            } catch (Exception x) {
                x.printStackTrace(System.err);
                eProductUpdater.err("ERROR a in Constructor:" + x.toString());
            }
            try {
                updateSequences(_dbODS);
            } catch (Exception x) {
                eProductUpdater.err("ERROR b in Constructor:" + x.toString());
            }
        } finally {
            D.ebug(D.EBUG_SPEW,"eProduct Complete");
        }
    }

    /**
     * i.e. rules to be processed which will be in Object, but NOT in the database.
     *
     * @return EANList
     * @param _db
     * @throws java.lang.Exception 
     */
    public final EANList processOutBoundRules(Database _db) throws Exception {
        return processRules(_db, false);
    }
    /**
     * i.e. rules to be processed and inserted into the database as "real" data.
     *
     * @return EANList
     * @param _db
     * @throws java.lang.Exception 
     */
    public final EANList processInBoundRules(Database _db) throws Exception {
        return processRules(_db, true);
    }

    /**
     * updateSequences
     *
     * @param _db
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public final void updateSequences(Database _db) throws Exception {
        for (int j = 0; j < getProductDetailCount(); j++) {
            eProductDetail prodDetail = getProductDetail(j);
            if (!prodDetail.wereSequencesUpdated()) {
                prodDetail.updateSequences(_db);
            }
        }
    }

    /**
     * @return the list of derived eProductDetails, if any. we want this for inBound rules.
     */
    private final EANList processRules(Database _db, boolean _bInBound) throws Exception {

        EANList elDerived = new EANList();
        boolean bRequiredMet = true;
        
        eRulesCollection erc = eRulesCollection.getRulesCollection(_db, getProfile(), getIntVal(PROJECTID), getStringVal(GENAREANAME_FC), getStringVal(COUNTRYCODE), getLayoutContext());
        // 1) ConcatRules
        for (int i = 0; i < erc.getConcatRuleCount(); i++) {
            eConcatRule concatRule = erc.getConcatRule(i);
            if ((concatRule.isInBoundTrigger() && _bInBound) || (concatRule.isOutBoundTrigger() && !_bInBound)) {
                eProductDetail pdDerived = putDerivedProductDetail(_db, concatRule);
                if (pdDerived != null) {
                    elDerived.put(pdDerived);
                } else {
                }
            }
        }

        // 2) RollupRules
        for (int i = 0; i < erc.getRollupRuleCount(); i++) {
            eRollupRule rollupRule = erc.getRollupRule(i);
            if ((rollupRule.isInBoundTrigger() && _bInBound) || (rollupRule.isOutBoundTrigger() && !_bInBound)) {
                eProductDetail pdDerived = putDerivedProductDetail(_db, rollupRule);
                if (pdDerived != null) {
                    elDerived.put(pdDerived);
                } else {
                }
            }
        }

        // 3) Required LAST - these could themselves be dervived atts!!
        REQUIRED_LOOP : 
        for (int i = 0; i < erc.getRequiredRuleCount(); i++) {
            eRequiredRule requiredRule = erc.getRequiredRule(i);
            if ((requiredRule.isInBoundTrigger() && _bInBound) || (requiredRule.isOutBoundTrigger() && !_bInBound)) {
                eProductDetail ePDReqCheck = getProductDetail(requiredRule);
                if (ePDReqCheck == null) {
                    bRequiredMet = false;
                    continue REQUIRED_LOOP;
                }
            }
        }
        putIntVal(VALID_FLAG, (bRequiredMet ? VALID_FLAG_YES_VAL : VALID_FLAG_NO_VAL));
        return elDerived;
    }

    /**
     * (non-Javadoc)
     * toString
     *
     * @see java.lang.Object#toString()
     */
    public final String toString() {
        return dump(true);
    }

    /**
     * (non-Javadoc)
     * dump
     *
     * @see COM.ibm.eannounce.objects.EANObject#dump(boolean)
     */
    public final String dump(boolean _b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < getColumnCount(); i++) {
            sb.append(getStringVal(i) + ":");
        }
        sb.append(NEW_LINE);
        if (!_b) {
            sb.append("-- productDetailCount:" + getProductDetailCount() + NEW_LINE);
            for (int i = 0; i < getProductDetailCount(); i++) {
                sb.append("[" + i + "] " + getProductDetail(i).dump(_b) + NEW_LINE);
            }
        }
        return sb.toString();
    }

    /**
     * getProductDetailCount
     *
     * @return
     *  @author David Bigelow
     */
    public final int getProductDetailCount() {
        return getMetaCount();
    }

    private final void putProductDetailRel(eProductDetailRel _epdr) {
        D.ebug(D.EBUG_SPEW, "TRACK put eProductDetailRel:" + _epdr.dump(false));
        putData(_epdr);
    }

    private final boolean containsProductDetailRel(String _strKey) {
        return (getProductDetailRel(_strKey) != null);
    }

    private final eProductDetailRel getProductDetailRel(String _strKey) {
        return (eProductDetailRel) getData(_strKey);
    }



    /**
     * putProductDetail
     *
     * @param _epd
     *  @author David Bigelow
     */
    public final void putProductDetail(eProductDetail _epd) {
        String strRelKey = null;
        String strLayoutKey = _epd.getStringVal(eProductDetail.HERITAGE) + "." + _epd.getStringVal(eProductDetail.ENTITYTYPE) + "." + _epd.getStringVal(eProductDetail.ATTRIBUTECODE);
        String strKey = _epd.getStringVal(eProductDetail.HERITAGE) + "." + _epd.getStringVal(eProductDetail.ENTITYTYPE);
        String str1 = (String) m_hshCounts.get(strKey);

        putMeta(_epd);
        m_hashLayoutKeys.put(strLayoutKey, _epd);
        // ok.. lets see if we cannot bump up the counts here..
        if (str1 == null) {
            m_hshCounts.put(strKey, "" + _epd.getIntVal(eProductDetail.ENTITYID));
        } else {
            m_hshCounts.put(strKey, str1 + ":" + _epd.getIntVal(eProductDetail.ENTITYID));
        }
        strRelKey = eProductDetailRel.buildKey(this, _epd);
        if (!containsProductDetailRel(strRelKey)) {
            String strRelPublish = _epd.getStringVal(eProductDetailRel.PUBLISHFLAG);
            String strRelValFrom = _epd.getStringVal(eProductDetailRel.VALFROM);
            try {
                eProductDetailRel pdRel = new eProductDetailRel(this, _epd, strRelPublish, strRelValFrom);
                putProductDetailRel(pdRel);
            } catch (Exception exc) {
                eProductUpdater.err("ERROR while creating relator for product detail:" + _epd.getKey() + ":" + exc.getMessage());
            }
        }
    }

    /**
     * removeAllProductDetails
     *
     *  @author David Bigelow
     */
    public final void removeAllProductDetails() {
        resetMeta();
        m_hashLayoutKeys = new Hashtable();
        m_hshCounts = new Hashtable();
    }

    /**
     * getProductDetail
     *
     * @param _i
     * @return
     *  @author David Bigelow
     */
    public final eProductDetail getProductDetail(int _i) {
        return (eProductDetail) getMeta(_i);
    }

    /**
     * getProductDetail
     *
     * @param _s
     * @return
     *  @author David Bigelow
     */
    public final eProductDetail getProductDetail(String _s) {
        return (eProductDetail) getMeta(_s);
    }

    private final eProductDetail putDerivedProductDetail(Database _db, eBasicRule _basicRule) {
        eProductDetail prodDetail = null;
        try {
            prodDetail = eRulesProcessor.getDerivedProductDetail(_db, this, _basicRule);
        } catch (Exception exc) {
            eProductUpdater.err("Error in putDerivedProductDetail:" + exc.toString());
            exc.printStackTrace(System.err);
        }
        if (prodDetail != null) {
            putProductDetail(_db, prodDetail);
        } else {
        }
        return prodDetail;
    }

    /**
     * putProductDetail
     *
     * @param _db
     * @param _prodDetail
     *  @author David Bigelow
     */
    public final void putProductDetail(Database _db, eProductDetail _prodDetail) {
        // if our list does NOT contain this ProductDetail --> put it
        if (!_prodDetail.wereSequencesUpdated()) {
            _prodDetail.updateSequences(_db);
        }
        this.putProductDetail(_prodDetail);
    }

    /**
     * Note: this does NOT rely on strict equality.
     * We are reporting on whether or not our list contians a ProductDetail w/ the same key columns as the passed ProductDetail.
     *
     * @return boolean
     * @param _prodDetail 
     */
    public final boolean contains(eProductDetail _prodDetail) {
        return (getCorrespondingProductDetailByKey(_prodDetail) != null);
    }

    /**
     * Get the correspinding ProductDEtail from the list, if it exists.
     *
     * @return eProductDetail
     * @param _prodDetail 
     */
    public final eProductDetail getCorrespondingProductDetailByKey(eProductDetail _prodDetail) {
        return (eProductDetail) getMeta(_prodDetail.getKey());
    }

    /**
     *  This guy uses an SP now
     *
     * @param _dbODS
     * @throws java.lang.Exception 
     */
    public final void deleteProductDetails(Database _dbODS) throws Exception {
        _dbODS.callGBL9983(new ReturnStatus(-1), getStringVal(ENTERPRISE), getIntVal(RECID));
        _dbODS.freeStatement();
        _dbODS.isPending();

    }

    /**
     * delete
     *
     * @param _conODS
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public final void delete(Connection _conODS) throws Exception {

        PreparedStatement psDelete = null;
        
        try {
            psDelete = getPreparedStatementCollection().getPS(PreparedStatementCollection.PRODUCT_DELETE);
            D.ebug(D.EBUG_SPEW, "TRACK eProduct.delete for:" + getStringVal(ENTITYTYPE) + ":" + getIntVal(ENTITYID));

            if (psDelete == null) {
    
                String strDeleteSQL = "DELETE FROM " + m_strODSSchema + ".PRODUCT " + " WHERE " + " ENTERPRISE = ? AND " + " ENTITYTYPE = ? AND " + " ENTITYID = ?";
                psDelete = _conODS.prepareStatement(strDeleteSQL);
                getPreparedStatementCollection().putPS(PreparedStatementCollection.PRODUCT_DELETE, psDelete);
            }
            
            psDelete.clearParameters();
            psDelete.setString(1, getStringVal(ENTERPRISE));
            psDelete.setString(2, getStringVal(ENTITYTYPE));
            psDelete.setInt(3, getIntVal(ENTITYID));
    
            psDelete.executeUpdate();
        } finally {
            if (psDelete != null) {
                psDelete.close();
                psDelete = null;
            }
        }
    }

    /**
     * (non-Javadoc)
     * update
     *
     * @see COM.ibm.eannounce.hula.eTableRecord#update(COM.ibm.opicmpdh.middleware.Database)
     */
    public final void update(Database _db) throws Exception {
        update(_db, true);
    }

    /**
     * update
     *
     * @param _db
     * @param _commit
     * @throws java.lang.Exception
     *  @author David Bigelow
     */
    public final void update(Database _db, boolean _commit) throws Exception {

        int iHeaderRecID = 0;
        Vector vctPDUpdates = new Vector();
        Vector vctPDDeletes = new Vector();
        eProduct prod_db = null;
        
        // Since this was passed in .. we do not want to close it
        Connection conODS = _db.getODSConnection();
        Stopwatch sw = new Stopwatch();

        sw.start();
        
        D.ebug(D.EBUG_SPEW, "TRACK eProduct.update for:" + getStringVal(ENTITYTYPE) + ":" + getIntVal(ENTITYID) + ", isInsert?" + isInsert());

        // IS THIS NECESSARY HERE???
        this.processInBoundRules(_db);

        prod_db = new eProduct(_db, this);

        iHeaderRecID = getIntVal(RECID);

        if (isInsert()) {
            
            PreparedStatement psInsert = null;
            ResultSet rs = null;
            ReturnDataResultSet rdrs = null;
            
            if (iHeaderRecID == eTableRecord.NO_INT_VAL) {
                try {
                    D.ebug(D.EBUG_SPEW, "call GBL9979(" + getStringVal(ENTERPRISE) + ")");
                    rs = _db.callGBL9979(new ReturnStatus(-1), getStringVal(ENTERPRISE));
                    rdrs = new ReturnDataResultSet(rs);
                } finally  {
                    rs.close();
                    rs = null;
                    _db.freeStatement();
                    _db.isPending();
                }
                iHeaderRecID = rdrs.getColumnInt(0, 0);
                D.ebug(D.EBUG_SPEW, "call GBL9979 answer:" + iHeaderRecID);
            }

            try {
                psInsert = getPreparedStatementCollection().getPS(PreparedStatementCollection.PRODUCT_INSERT);
    
                if (psInsert == null) {
                    StringBuffer sbCols = new StringBuffer();
                    StringBuffer sbQuestions = new StringBuffer();
                    String strInsertSQL = null;
                    for (int i = 0; i < getColumnCount(); i++) {
                        if (i > 0) {
                            sbCols.append(",");
                            sbQuestions.append(",");
                        }
                        sbCols.append(getColumnKey(i));
                        sbQuestions.append("?");
                    }
                    strInsertSQL = "INSERT INTO " + m_strODSSchema + ".PRODUCT " + "( " + sbCols.toString() + ") " + "VALUES (" + sbQuestions.toString() + ")";
                    psInsert = conODS.prepareStatement(strInsertSQL);
                    getPreparedStatementCollection().putPS(PreparedStatementCollection.PRODUCT_INSERT, psInsert);
                }
            
                psInsert.clearParameters();
                for (int i = 0; i < getColumnCount(); i++) {
                    if (getColumnType(i) == INT) {
                        psInsert.setInt(i + 1, getIntVal(i));
                    } else {
                        psInsert.setString(i + 1, getStringVal(i));
                    }
                }
                psInsert.executeUpdate();
            } finally {
                if (psInsert != null) {
                    psInsert.close();
                    psInsert = null;
                }
            }
            
            for (int i = 0; i < prod_db.getProductDetailCount(); i++) {
                vctPDDeletes.addElement(prod_db);
            }
            for (int i = 0; i < this.getProductDetailCount(); i++) {
                this.getProductDetail(i).setInsert(true);
                vctPDUpdates.addElement(this.getProductDetail(i));
            }

        } else {

            int iParmNum = 1;
            String strUpdateSQL = null;
            PreparedStatement psUpdate = null;
            StringBuffer sbUpdateCols = new StringBuffer();
 
            for (int i = 0; i < prod_db.getProductDetailCount(); i++) {
                eProductDetail pd_db = prod_db.getProductDetail(i);
                if (!this.contains(pd_db)) {
                    vctPDDeletes.addElement(pd_db);
                }
            }
            for (int i = 0; i < this.getProductDetailCount(); i++) {
                eProductDetail pd_this = this.getProductDetail(i);
                eProductDetail pd_db = prod_db.getCorrespondingProductDetailByKey(pd_this);

                // THIS SHOULD BE ABLE tO BE DONE EARLIER...!?!?!?
                pd_this.updateSequences(_db);

                if (pd_db == null || !pd_this.isEquivalent(pd_db)) {

                    if (pd_db == null) {
                        pd_this.setInsert(true);
                    }
                    vctPDUpdates.addElement(pd_this);
                }
            }

            for (int i = 0; i < getColumnCount(); i++) {
                if (wasColumnValSet(i)) {
                    sbUpdateCols.append(" " + getColumnKey(i));
                    sbUpdateCols.append(" = ");
                    sbUpdateCols.append(" ?,");
                }
            }

            sbUpdateCols.deleteCharAt(sbUpdateCols.length() - 1);

            strUpdateSQL = "UPDATE " + m_strODSSchema + ".PRODUCT SET " + sbUpdateCols.toString() + " WHERE ENTERPRISE = '" + getStringVal(ENTERPRISE) + "' " + " AND ENTITYTYPE = '" + getStringVal(ENTITYTYPE) + "' " + " AND ENTITYID = " + getIntVal(ENTITYID) + " " + " AND NLSID = " + getIntVal(NLSID);
            try {
                psUpdate = conODS.prepareStatement(strUpdateSQL);
                psUpdate.clearParameters();
                for (int i = 0; i < getColumnCount(); i++) {
                    if (!wasColumnValSet(i)) {
                        continue;
                    }
                    if (getColumnType(i) == INT) {
                        psUpdate.setInt(iParmNum++, getIntVal(i));
                    } else {
                        psUpdate.setString(iParmNum++, getStringVal(i));
                    }
                }
                psUpdate.executeUpdate();
            } finally {
                psUpdate.close();
            }
        }

        for (int i = 0; i < vctPDUpdates.size(); i++) {
            ((eProductDetail) vctPDUpdates.elementAt(i)).update(_db);
        }
        for (int i = 0; i < vctPDDeletes.size(); i++) {
            ((eProductDetail) vctPDDeletes.elementAt(i)).unpublish(_db);
        }

        // commit now!!
        if (_commit) {
            conODS.commit();
        }

    }

    /**
     * getProductDetail
     *
     * @param _layoutItem
     * @return
     *  @author David Bigelow
     */
    public final eProductDetail getProductDetail(eLayoutItem _layoutItem) {
        String strKey = _layoutItem.getKey();
        return (eProductDetail) m_hashLayoutKeys.get(strKey);
    }

    /**
     * getProductDetail
     *
     * @param _rule
     * @return
     *  @author David Bigelow
     */
    public final eProductDetail getProductDetail(eBasicRule _rule) {
        // CAREFUL!  we are assuming rule shares key w/ layout item keys....
        String strKey = _rule.getKey();
        return (eProductDetail) m_hashLayoutKeys.get(strKey);
    }

    //
    // Get a product detail by LayoutItem and Child ID
    //
    /**
     * getProductDetail
     *
     * @param _li
     * @param _iChildID
     * @return
     *  @author David Bigelow
     */
    public final eProductDetail getProductDetail(eLayoutItem _li, int _iChildID) {
        String strChildType = _li.getType();
        String strHeritage = _li.getHeritage();
        String strAttCode = _li.getSubType();
        String strFlagCode = "N/A";
        String strProdKey = getProfile().getEnterprise() + ":" + this.getStringVal(ENTITYTYPE) + ":" + this.getIntVal(ENTITYID) + ":" + this.getIntVal(NLSID) + ":" + strChildType + ":" + _iChildID + ":" + strHeritage + ":" + strAttCode + ":" + strFlagCode;
        return (eProductDetail) getMeta(strProdKey);
    }

    /**
     * rearrange the ProductDetail columns (EANComparable) so that it is sorted alphabetically by the specified type
     *
     * @param  _bAscending   Description of the Parameter
     * @concurrency $none
     * @param _strColKey 
     */
    public final synchronized void sortByColumn(String _strColKey, boolean _bAscending) {
        eProductDetail[] aeProdDetail = new eProductDetail[getProductDetailCount()];
        EANComparator ec = new EANComparator(_bAscending);
        for (int i = 0; i < getProductDetailCount(); i++) {
            eProductDetail prodDetail = getProductDetail(i);
            prodDetail.setCompareField(_strColKey);
            aeProdDetail[i] = prodDetail;
        }
        Arrays.sort(aeProdDetail, ec);
        resetMeta();
        for (int i = 0; i < aeProdDetail.length; i++) {
            putProductDetail(aeProdDetail[i]);
        }
    }

    /**
     * getPricingGroup
     *
     * @param _db
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     *  @author David Bigelow
     */
    public final ePricingGroup getPricingGroup(Database _db) throws SQLException, MiddlewareRequestException, MiddlewareException {
        if (m_pricingGroup != null) {
            return m_pricingGroup;
        }
        if (getStringVal(PARTNUMBER) != null && getStringVal(COUNTRYCODE) != null) {
            m_pricingGroup = new ePricingGroup(_db, getProfile(), getStringVal(PARTNUMBER), getStringVal(COUNTRYCODE));
        }
        return m_pricingGroup;
    }

    /*
     * Version info
     */
    /**
     * (non-Javadoc)
     * getVersion
     *
     * @see COM.ibm.eannounce.objects.EANFoundation#getVersion()
     */
    public String getVersion() {
        return "$Id: eProduct.java,v 1.146 2005/03/03 21:25:15 dave Exp $";
    }

    /**
     * Here we will kick back a Vector of expanded layout items for a given group
     *
     * @return Vector
     * @param _lg 
     */
    public final Vector getExpandedLayoutItems(eLayoutGroup _lg) {

        Vector vctReturn = new Vector();
        eLayoutItem li = null;

        String strKey = null;
        String strSubGroup = null;
        StringTokenizer st = null;

        // for now .. we get the first one and see if there is a
        // a repeating pattern
        // lets do a simple case here
        //
        if (_lg.getLayoutItemCount() == 0) {
            return vctReturn;
        }

        if (!_lg.isMulti()) {
            for (int i = 0; i < _lg.getLayoutItemCount(); i++) {
                try {
                    li = new eLayoutItem(_lg.getLayoutItem(0));
                    li.setProductDetail(getProductDetail(li));
                } catch (Exception x) {
                    x.printStackTrace();
                }
                vctReturn.add(li);
            }
            return vctReturn;
        }

        //
        // Its a multi layout.. and is restricted to a single child type
        //
        strKey = li.getHeritage() + "." + li.getType();
        strSubGroup = (String) m_hshCounts.get(strKey);
        st = new StringTokenizer(strSubGroup, ":");

        //
        //  If no tokens.. we return a blank.
        //
        if (!st.hasMoreTokens()) {
            for (int i = 0; i < _lg.getLayoutItemCount(); i++) {
                try {
                    li = new eLayoutItem(_lg.getLayoutItem(0));
                    li.setProductDetail(getProductDetail(li));
                } catch (Exception x) {
                    x.printStackTrace();
                }
                vctReturn.add(li);
            }

            return vctReturn;
        }

        //
        // ok.. we have at least one to report on
        //

        while (st.hasMoreTokens()) {
            int iChildID = Integer.parseInt(st.nextToken());
            for (int i = 0; i < _lg.getLayoutItemCount(); i++) {
                try {
                    li = new eLayoutItem(_lg.getLayoutItem(i));
                    li.setProductDetail(getProductDetail(li, iChildID));
                } catch (Exception x) {
                    x.printStackTrace();
                }
                vctReturn.add(li);
            }
        }

        return vctReturn;
    }

    /**
     * getLayoutContext
     *
     * @return
     *  @author David Bigelow
     */
    public final String getLayoutContext() {
        String strEntityType = getStringVal(ENTITYTYPE);
        if (strEntityType.equals("CSOL")) {
            return eRulesCollection.CONTEXT_MTM;
        }
        if (strEntityType.equals("CCTO")) {
            return eRulesCollection.CONTEXT_CTO;
        }
        if (strEntityType.equals("CVAR")) {
            return eRulesCollection.CONTEXT_VAR;
        }
        if (strEntityType.equals("SSB")) {
            return eRulesCollection.CONTEXT_SBB;
        }
        return eRulesCollection.CONTEXT_NONE;
    }

    /**
     * getProductUpdater
     *
     * @return
     *  @author David Bigelow
     */
    protected final eProductUpdater getProductUpdater() {
        return m_productUpdaterParent;
    }
    /**
     * setProductUpdater
     *
     * @param _pu
     *  @author David Bigelow
     */
    protected final void setProductUpdater(eProductUpdater _pu) {
        m_productUpdaterParent = _pu;
    }
    /**
     * getPreparedStatementCollection
     *
     * @return
     *  @author David Bigelow
     */
    protected final PreparedStatementCollection getPreparedStatementCollection() {
        return getProductUpdater().getPreparedStatementCollection();
    }

    /**
     * isInsert
     *
     * @return
     *  @author David Bigelow
     */
    public final boolean isInsert() {
        return m_bInsert;
    }
    /**
     * setInsert
     *
     * @param _b
     *  @author David Bigelow
     */
    public final void setInsert(boolean _b) {
        m_bInsert = _b;
    }
}
