//
// Copyright (c) 2001, International Business Machines Corp., Ltd.
// All Rights Reserved.
// Licensed for use in connection with IBM business only.
//
// $Log: Translation.java,v $
// Revision 1.193  2010/04/13 17:35:16  praveen
// use OdsLength()
//
// Revision 1.192  2010/04/13 15:17:35  praveen
// get maxLength only for non flag attributes
//
// Revision 1.191  2010/04/07 18:10:17  praveen
// Fix maxLength call
//
// Revision 1.190  2010/03/25 18:48:26  lucasrg
// added validateMaxLength parameter in postETSPackage method
//
// Revision 1.189  2010/03/25 14:47:58  lucasrg
// added MaxTranslationException
//
// Revision 1.188  2010/03/16 17:45:10  lucasrg
// Translation using attribute's max length to truncate the translated value
//
// Revision 1.187  2008/01/31 21:32:03  wendy
// Cleanup RSA warnings
//
// Revision 1.186  2007/09/21 04:08:29  yang
// fixing log
//
// Revision 1.185  2007/09/17 12:06:03  yang
// adding additional log
//
// Revision 1.184  2007/09/17 09:54:30  yang
// minor change
//
// Revision 1.183  2007/09/17 03:23:10  yang
// skipping attributes based on role capability
//
// Revision 1.182  2006/02/01 17:34:17  yang
// Updated so translations will post XML attributes
//
// Revision 1.181  2005/03/03 18:59:53  yang
// Another case to filter on SBB
//
// Revision 1.180  2005/02/08 21:56:51  dave
// more cvs cleanup
//
// Revision 1.179  2005/01/27 00:34:55  dave
// Jtest on Translation area (formatting and clean up)
//
// Revision 1.178  2005/01/20 22:37:11  yang
// more changes to skipSBBData
//
// Revision 1.177  2005/01/20 20:41:00  steve
// modifed skipSBBData method
//
// Revision 1.176  2005/01/20 20:35:44  steve
// added the skipSBBData method
//
// Revision 1.175  2005/01/20 17:21:36  yang
// minor change
//
// Revision 1.174  2005/01/19 17:49:43  yang
// syntax
//
// Revision 1.173  2005/01/18 16:29:43  yang
// logs
//
// Revision 1.172  2005/01/18 00:34:26  steve
// removed specialized filter SBB data method and put similar code back in generatePDHPackage method.
//
// Revision 1.171  2005/01/17 22:34:09  yang
// minor fix
//
// Revision 1.170  2005/01/17 22:00:15  yang
// adding filterEntityListBySBBDate for BUI translation rpt
//
// Revision 1.169  2005/01/17 19:48:11  yang
// minor changes
//
// Revision 1.168  2005/01/14 18:16:08  yang
// cleanning up logs
//
// Revision 1.167  2005/01/14 01:16:16  yang
// minor fixes
//
// Revision 1.166  2005/01/14 01:05:58  yang
// more logs
//
// Revision 1.165  2005/01/14 00:37:18  yang
// fix
//
// Revision 1.164  2005/01/13 17:28:47  yang
// more syntax
//
// Revision 1.163  2005/01/13 01:33:17  yang
// syntax
//
// Revision 1.162  2005/01/13 01:13:42  yang
// more fixes
//
// Revision 1.161  2005/01/13 01:03:50  yang
// more logs
//
// Revision 1.160  2005/01/13 00:26:39  yang
// deleting some logs
//
// Revision 1.159  2005/01/13 00:09:26  yang
// changed attribute to SBBUNPUBLISHCTDATE
//
// Revision 1.158  2005/01/12 23:46:30  roger
// Test logging
//
// Revision 1.157  2005/01/12 22:57:07  yang
// Fix
//
// Revision 1.156  2005/01/12 22:26:22  yang
// adding SBBPUBLISHCTDATE check
//
// Revision 1.155  2005/01/12 18:15:18  yang
// backout CR4412 per nancy's request
//
// Revision 1.154  2005/01/11 17:26:05  yang
// more changes
//
// Revision 1.153  2005/01/11 17:04:12  yang
// minor fix
//
// Revision 1.152  2004/12/06 21:49:03  yang
// another case on filter
//
// Revision 1.151  2004/11/23 16:59:49  roger
// Make it more robust
//
// Revision 1.150  2004/11/22 22:27:05  roger
// Fix
//
// Revision 1.149  2004/11/22 22:23:36  roger
// Fix
//
// Revision 1.148  2004/11/22 22:16:38  roger
// SBB filter
//
// Revision 1.147  2004/11/22 18:54:34  roger
// Fix again
//
// Revision 1.146  2004/11/22 18:49:27  roger
// Show the values being compared
//
// Revision 1.145  2004/11/22 18:33:59  roger
// Fix
//
// Revision 1.144  2004/11/22 18:25:53  roger
// Another filter case
//
// Revision 1.143  2004/11/04 18:57:36  dave
// OK Billingcode is now an attribute on the translation entity
// itself and is now part of the PackageID constructor
//
// Revision 1.142  2004/09/21 20:27:12  roger
// The "translation filter" on entities
//
// Revision 1.141  2004/09/07 22:23:14  dave
// first boolean means do not update deactivated
// second boolean means attributeonly
// should only be true, false
// this is for translation
//
// Revision 1.140  2004/09/01 16:54:03  roger
// Clean up for final test
//
// Revision 1.139  2004/08/31 17:17:46  roger
// BillingCode support
//
// Revision 1.138  2004/07/27 22:09:03  dave
// change true to false
//
// Revision 1.137  2004/06/14 21:28:21  gregg
// more debugs
//
// Revision 1.136  2004/06/14 17:26:26  gregg
// use EXTXLATEGRP1 extract if flags TRANSEXTRACTATTR not defined (i.e. meta is not in).
//
// Revision 1.135  2004/05/28 17:33:44  gregg
// add CR5530 logic into postPDHPackage logic + throw Exception
//
// Revision 1.134  2004/05/27 23:21:13  gregg
// first pass at CR5530 for generatePDHPackage
//
// Revision 1.133  2004/05/26 22:01:59  gregg
// debugging
//
// Revision 1.132  2003/09/14 02:49:45  dave
// syntax
//
// Revision 1.131  2003/09/14 02:37:54  dave
// do not forget "I"
//
// Revision 1.130  2003/09/12 22:00:45  dave
// needed UnsupportedEncodingException import
//
// Revision 1.129  2003/09/12 21:45:34  dave
// catch thrown exception
//
// Revision 1.128  2003/09/12 21:32:17  dave
// minor correction
//
// Revision 1.127  2003/09/12 21:15:24  dave
// Checking translation length limit
//
// Revision 1.126  2003/09/12 20:57:19  dave
// truncati
//
// Revision 1.125  2003/09/10 21:41:22  dave
// Translation post problem
//
// Revision 1.124  2003/09/05 05:33:27  dave
// syntax
//
// Revision 1.123  2003/09/05 05:04:55  dave
// Adding trace statements
//
// Revision 1.122  2003/09/04 21:57:44  dave
// fixing meta override
//
// Revision 1.121  2003/09/04 17:05:58  dave
// changing PHD to PDH
//
// Revision 1.120  2003/09/03 17:03:54  dave
// minor changes based upon Hans comments
//
// Revision 1.119  2003/08/29 00:29:48  dave
// null pointer fix
//
// Revision 1.118  2003/08/28 23:52:47  dave
// final trace
//
// Revision 1.117  2003/08/28 23:41:45  dave
// more trace
//
// Revision 1.115  2003/08/28 23:10:09  dave
// one more change
//
// Revision 1.114  2003/08/28 23:09:22  dave
// more trace
//
// Revision 1.113  2003/08/28 22:43:30  dave
// More trace
//
// Revision 1.112  2003/08/28 22:04:30  dave
// Adding more trace statements to Tranlsation
//
// Revision 1.111  2003/08/28 20:21:54  dave
// timing issue
//
// Revision 1.110  2003/08/28 20:03:05  dave
// null pointer fix
//
// Revision 1.109  2003/08/28 19:19:12  dave
// Trace
//
// Revision 1.108  2003/08/27 21:41:20  dave
// more minor display mods
//
// Revision 1.107  2003/08/27 21:14:15  dave
// save it
//
// Revision 1.105  2003/08/27 17:42:04  dave
// syntax fix
//
// Revision 1.104  2003/08/27 16:51:32  dave
// postETSTranslation VE pre compare
//
// Revision 1.103  2003/08/26 22:26:34  dave
// returning a nul value when no translation package can be found
//
// Revision 1.102  2003/08/21 21:11:06  dave
// adding 11,12,13 language translations
//
// Revision 1.101  2003/08/20 16:29:53  dave
// private to public
//
// Revision 1.100  2003/08/20 16:17:20  dave
// exposing setTranslationPackageStatus to remote interface
//
// Revision 1.99  2003/08/14 18:32:12  dave
// more getBlobNow
//
// Revision 1.98  2003/08/14 17:49:56  dave
// Fine tuning putBlobNow
//
// Revision 1.97  2003/08/14 01:00:46  dave
// null pointer protection
//
// Revision 1.96  2003/08/14 00:39:10  dave
// making sure parent entity item is in the array for VE Extract
//
// Revision 1.95  2003/08/14 00:10:46  dave
// dumping tranlsation contents
//
// Revision 1.94  2003/08/13 22:46:18  dave
// minor syntax
//
// Revision 1.93  2003/08/13 22:31:17  dave
// making changes for set status in TranslationII
//
// Revision 1.92  2003/08/11 21:11:51  dave
// Needed a return statement
//
// Revision 1.91  2003/08/11 20:58:46  dave
// fixing PackageID reference
//
// Revision 1.90  2003/08/11 20:39:48  dave
// Syntax
//
// Revision 1.89  2003/08/11 20:27:20  dave
// adding remote Translation Interface
//
// Revision 1.88  2003/08/11 18:13:52  dave
// more comments to Tranlsation
//
// Revision 1.87  2003/08/08 19:07:36  dave
// Hope this wraps syntax up
//
// Revision 1.86  2003/08/08 18:04:25  dave
// o.k. Meta API Conv II
//
// Revision 1.85  2003/08/08 17:40:02  dave
// Meta Translation I
//
// Revision 1.84  2003/08/08 17:25:26  dave
// adding comments and prep for Meta Translation piece
//
// Revision 1.83  2003/08/07 19:37:12  dave
// removing the abstract thing
//
// Revision 1.82  2003/07/10 18:35:54  dave
// fixes for null pointer
//
// Revision 1.81  2003/07/08 21:13:57  dave
// Minor comment change
//
// Revision 1.80  2003/07/08 21:06:53  dave
// some minor modifications
//
// Revision 1.79  2003/07/08 21:00:43  dave
// driving to the net
//
// Revision 1.78  2003/07/08 20:14:00  dave
// translation package changes II
//
// Revision 1.77  2003/07/08 19:24:37  dave
// syntax
//
// Revision 1.76  2003/07/08 18:32:57  dave
// First attempt at getting Package ID's and Info
// from the outbound queues
//
// Revision 1.75  2003/07/08 01:45:27  dave
// closing in on it
//
// Revision 1.74  2003/07/08 01:23:04  dave
// seperation for 1.2 Action retrofit
//
// Revision 1.73  2003/07/08 00:55:49  dave
// more revisions to fit into 1.2
//
// Revision 1.72  2003/07/07 23:56:38  dave
// my syntax cleaned up
//
// Revision 1.71  2003/07/07 23:38:01  dave
// more syntax
//
// Revision 1.70  2003/07/07 23:19:21  dave
// more fixes to syntax.. and retrofit into 1.2 action items
//
// Revision 1.69  2003/07/07 23:01:19  dave
// more res
//
// Revision 1.68  2003/07/07 22:32:42  dave
// kinstac
//
// Revision 1.67  2003/07/07 22:11:34  dave
// more syntax fixes
//
// Revision 1.66  2003/07/07 21:53:07  dave
// fixed a bunch
//
// Revision 1.65  2003/07/07 21:35:22  dave
// more syntax
//
// Revision 1.64  2003/07/07 21:23:46  dave
// Cleanup
//
// Revision 1.63  2003/07/07 21:10:27  dave
// fin techs errors
//
// Revision 1.62  2003/07/07 20:57:57  dave
// some initial syntax fixes
//
// Revision 1.61  2003/07/07 20:39:06  dave
// translation for 1.2 retrofit I
//
// Revision 1.50.4.5  2002/08/29 19:35:01  dave
// removed a call to the XML thingie for translation where
// it is attempting to print out the XML version
// and that method seems to be in an infinite loop
//
// Revision 1.50.4.4  2002/06/12 16:48:42  gregg
// added NLSID param for callGBL2910
//
// Revision 1.50.4.3  2001/12/18 18:29:28  dave
// pull Packages based upon the NLS that was used to put it.
// (the NLS in package info)
//
// Revision 1.50.4.2  2001/12/17 18:59:31  dave
// bad constant.. fixed
//
// Revision 1.50.4.1  2001/12/11 23:43:05  dave
// misc changes to support translation
//
// Revision 1.50  2001/10/12 18:53:17  dave
// only post entity changes if any attributes apply
//
// Revision 1.49  2001/09/24 17:05:02  roger
// Use accessor
//
// Revision 1.48  2001/09/20 20:21:31  roger
// Use accessors of objects
//
// Revision 1.47  2001/09/20 17:16:24  roger
// Fixes
//
// Revision 1.46  2001/09/20 16:51:43  roger
// Use accessors for objects
//
// Revision 1.45  2001/09/17 22:35:30  roger
// More undo
//
// Revision 1.44  2001/09/17 22:28:13  roger
// Undo more
//
// Revision 1.43  2001/09/13 16:39:59  roger
// Profile changes
//
// Revision 1.42  2001/09/13 16:25:59  roger
// Profile changes
//
// Revision 1.41  2001/09/13 16:17:52  roger
// Profile changes
//
// Revision 1.40  2001/09/12 23:21:40  roger
// Profile changes
//
// Revision 1.39  2001/09/12 23:05:13  roger
// Profile changes
//
// Revision 1.38  2001/09/12 22:53:51  roger
// Parm fix
//
// Revision 1.37  2001/09/12 21:56:45  roger
// Profile changes
//
// Revision 1.36  2001/09/12 18:09:46  roger
// Profile changes
//
// Revision 1.35  2001/08/23 19:40:17  roger
// Fixes
//
// Revision 1.34  2001/08/23 19:32:58  roger
// Fixes
//
// Revision 1.33  2001/08/23 19:19:47  roger
// Needed variables mostly static methods
//
// Revision 1.32  2001/08/23 19:11:22  roger
// Needed variable for static method
//
// Revision 1.31  2001/08/23 19:04:30  roger
// Use the DatePackage
//
// Revision 1.30  2001/08/22 16:53:58  roger
// Removed author RM
//
// Revision 1.29  2001/06/18 19:51:08  dave
// Fix from .html to .htm
//
// Revision 1.28  2001/06/11 16:56:12  roger
// Added ShortDescription parm to GBL2909 call
//
// Revision 1.27  2001/06/11 15:45:53  roger
// Replaced call to WHY2951 (MFV Insert) with GBL2909 (MetaDescription Insert)
//
// Revision 1.26  2001/04/17 16:36:33  roger
// Global search and destory worked too well!
//
// Revision 1.25  2001/04/17 15:51:05  roger
// Fixed keyword in Log
//
// Revision 1.24  2001/04/17 14:44:26  roger
// Added assertions for null attributes from VE
//
// Revision 1.23  2001/04/16 21:26:11  roger
// Check for nulls and handle them
//
// Revision 1.22  2001/04/16 21:16:21  roger
// Gate dumping of tpReturn if null
//
// Revision 1.21  2001/03/26 16:35:46  roger
// Misc formatting clean up
//
// Revision 1.20  2001/03/22 01:18:14  roger
// Comment out call to GBL2951, needs to be GBL2091
//
// Revision 1.19  2001/03/21 21:16:41  roger
// Make the GBL####A SPs named GBL####
//
// Revision 1.18  2001/03/21 19:27:35  roger
// Put the link call back in
//
// Revision 1.17  2001/03/20 21:50:46  roger
// Removed call to link method
//
// Revision 1.16  2001/03/20 21:36:51  roger
// Removed ConnectionItem from declarePackage
//
// Revision 1.15  2001/03/20 21:32:27  roger
// Include ConnectionItem for declarePackage
//
// Revision 1.14  2001/03/20 21:15:04  roger
// Insert keyword Id in getVersion method for testing ident of java source and class files
//
// Revision 1.13  2001/03/20 21:06:43  roger
// Include ConnectionItem to declarePackage for link method
//
// Revision 1.12  2001/03/17 01:08:19  roger
// Dummy the ConnectionItem for link
//
// Revision 1.11  2001/03/17 00:53:54  roger
// Import transactions
//
// Revision 1.10  2001/03/17 00:18:14  roger
// Pass a ConnectionItem to link
//
// Revision 1.9  2001/03/16 21:53:13  roger
// Created DUMMY_INT to take place of missing parameter for TranID or OPENID
//
// Revision 1.8  2001/03/16 21:29:46  roger
// VE changed method name
//
// Revision 1.7  2001/03/16 17:02:05  roger
// Changed GBL2933 to GBL2933A and GBL2054 to GBL2054A
//
// Revision 1.6  2001/03/16 15:52:28  roger
// Added Log keyword
//


package COM.ibm.opicmpdh.translation;


import COM.ibm.eannounce.objects.EANAttribute;

import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.TextAttribute;
import COM.ibm.opicmpdh.middleware.D;
import COM.ibm.opicmpdh.middleware.Database;
import COM.ibm.opicmpdh.middleware.DatePackage;
import COM.ibm.opicmpdh.middleware.MiddlewareException;
import COM.ibm.opicmpdh.middleware.Profile;
import COM.ibm.opicmpdh.middleware.ReturnDataResultSet;
import COM.ibm.opicmpdh.middleware.T;
import COM.ibm.opicmpdh.objects.Blob;
import COM.ibm.opicmpdh.objects.ControlBlock;
import COM.ibm.opicmpdh.objects.LongText;
import COM.ibm.opicmpdh.objects.SingleFlag;
import COM.ibm.opicmpdh.objects.Text;
import java.io.UnsupportedEncodingException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
import COM.ibm.opicmpdh.middleware.ReturnStatus;

import COM.ibm.opicmpdh.transactions.NLSItem;


import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.eannounce.objects.MetaTranslationGroup;
import COM.ibm.eannounce.objects.MetaTranslationItem;
import COM.ibm.eannounce.objects.MultiFlagAttribute;


/**
* A class which supports the functions for eTS Translation
* @version @date
*/
public final class Translation {

    /**
     * TRAN_ID
     */
    public final static int TRAN_ID = 22;

    /**
     * TYPE_DATA
     */
    public final static String TYPE_DATA = "DATA";
    /**
     * TYPE_META
     */
    public final static String TYPE_META = "META";

    /**
     * ENTITY_TYPE_FOR_DATA
     */
    public final static String ENTITY_TYPE_FOR_DATA = "XLATEGRP";
    /**
     * ENTITY_TYPE_FOR_META
     */
    public final static String ENTITY_TYPE_FOR_META = "METAXLATEGRP";

    /**
     * STATUS_NEW
     */
    public final static PackageStatus STATUS_NEW = new PackageStatus("XL10", "Untranslated");
    /**
     * STATUS_XLATE_OUT
     */
    public final static PackageStatus STATUS_XLATE_OUT = new PackageStatus("XL20", "Awaiting Translation");
    /**
     * STATUS_XLATE_INPROC
     */
    public final static PackageStatus STATUS_XLATE_INPROC = new PackageStatus("XL30", "In Translation");
    /**
     * STATUS_XLATE_BACK
     */
    public final static PackageStatus STATUS_XLATE_BACK = new PackageStatus("XL40", "Awaiting Validation");
    /**
     * STATUS_XLATE_VALIDATED
     */
    public final static PackageStatus STATUS_XLATE_VALIDATED = new PackageStatus("XL50", "Validated");
    /**
     * STATUS_XLATE_COMPLETE
     */
    public final static PackageStatus STATUS_XLATE_COMPLETE = new PackageStatus("XL60", "Complete & Posted");
    /**
     * STATUS_XLATE_CANCELED
     */
    public final static PackageStatus STATUS_XLATE_CANCELED = new PackageStatus("XL70", "Canceled");
    /**
     * STATUS_XLATE_REJECTED
     */
    public final static PackageStatus STATUS_XLATE_REJECTED = new PackageStatus("XL80", "Rejected");

    /**
     * c_vctOutLanguageQueue
     */
    public static Vector c_vctOutLanguageQueue = new Vector();
    /**
     * c_hshOutLanguageQueueMap
     */
    public static Hashtable c_hshOutLanguageQueueMap = new Hashtable();

    static {

        // The Group Queues - last character is the NLS..

        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX1");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX2");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX3");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX4");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX5");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX6");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX7");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX8");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX9");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX10");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX11");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX12");
        c_vctOutLanguageQueue.add("TRANSOUTBOUNDX13");

        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX1");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX2");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX3");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX4");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX5");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX6");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX7");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX8");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX9");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX10");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX11");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX12");
        c_vctOutLanguageQueue.add("METATRANSOUTBOUNDX13");

        // The Queue To NLSID map

        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX1", "1");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX2", "2");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX3", "3");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX4", "4");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX5", "5");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX6", "6");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX7", "7");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX8", "8");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX9", "9");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX10", "10");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX11", "11");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX12", "12");
        c_hshOutLanguageQueueMap.put("TRANSOUTBOUNDX13", "13");

        // The Meta Queue to the NLSID map

        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX1", "1");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX2", "2");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX3", "3");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX4", "4");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX5", "5");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX6", "6");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX7", "7");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX8", "8");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX9", "9");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX10", "10");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX11", "11");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX12", "12");
        c_hshOutLanguageQueueMap.put("METATRANSOUTBOUNDX13", "13");

    }

    /**
    * Don't let anyone instantiate this class.
    */
    private Translation() {
    }

    /**
     * This returns a vector of packageInfo's awaiting translation
     * This will have both Meta And Data Packages
     *
     * @param _db
     * @param _prof
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return Vector
     */
    public final static Vector getPackagesAwaitingTranslation(Database _db, Profile _prof)
        throws SQLException, MiddlewareException {

        Vector vctReturn = new Vector();
        ReturnStatus returnStatus = new ReturnStatus(-1);
        ResultSet rs = null;
        ReturnDataResultSet rdrs = null;
        String strEnterprise = _prof.getEnterprise();

        // Simple testing
        T.est(_db != null, "_db is null");
        T.est(_prof != null, "prof is null");

        // lets get all of them from all the language outbound Queues.
        // The language outbound queue vectors have been changed
        // to hold the metaxlateoutbound inforation.
        // The package info is like mini-entites that do not have
        // all the added bulk for a full blown entity
        // It only has things ETS is interested in.

        for (int ii = 0; ii < c_vctOutLanguageQueue.size(); ii++) {
            String strQueue = (String) c_vctOutLanguageQueue.elementAt(ii);
            int iRequestedNLS = Integer.parseInt((String) c_hshOutLanguageQueueMap.get(strQueue));
            try {
                rs = _db.callGBL2911(returnStatus, strEnterprise, strQueue);
                rdrs = new ReturnDataResultSet(rs);
            } finally {
                rs.close();
                rs = null;
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }
            for (int iy = 0; iy < rdrs.size(); iy++) {
                String strEntityType = rdrs.getColumn(iy, 0);
                int iEntityID = rdrs.getColumnInt(iy, 1);
                String strQueueTime = rdrs.getColumnDate(iy, 2);
                String strBillingCode = rdrs.getColumn(iy, 3);
                PackageID pkID = new PackageID(strEntityType, iEntityID, iRequestedNLS, strQueue, strQueueTime, strBillingCode);
                if (getPDHPackage(_db, _prof, pkID) != null) {
                    vctReturn.add(new PackageInfo(pkID, _db, _prof));
                } else {
                    _db.debug(D.EBUG_ERR, "getPackagesAwaitingTranslation.TRACE:missing PDH Package Image for: " + pkID);
                }
            }
        }

        return vctReturn;
    }

    /**
     * pullPDHPackageForTranslation
     *
     * @param _db
     * @param _prof
     * @param _pkID
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     * @author Dave
     */
    public final static TranslationPackage pullPDHPackageForTranslation(Database _db, Profile _prof, PackageID _pkID)
        throws SQLException, MiddlewareException {
        TranslationPackage tpReturn = getPDHPackage(_db, _prof, _pkID);
        return tpReturn;
    }

    /**
     * putETSTranslatedPackage
     *
     * @param _db
     * @param _prof
     * @param _trnp
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @author Dave
     */
    public final static void putETSTranslatedPackage(Database _db, Profile _prof, TranslationPackage _trnp)
        throws SQLException, MiddlewareException {

        // First.. put the package back to the Entity
        putETSPackage(_db, _prof, _trnp);
        setStatus(_db, _prof, _trnp.getPackageID(), STATUS_XLATE_BACK);

    }

    /**
     * this method returns true if the entity item contains specific SBB data, and therefore should
     * be excluded from the translation package.  it is used by the BUI translation report and
     * also the generate pdh package method.
     *
     * @param _ei
     * @return
     * @author Dave
     */
    public final static boolean skipSBBData(EntityItem _ei) {

        java.text.SimpleDateFormat sdfYYYYMMDD = new java.text.SimpleDateFormat("yyyy-MM-dd");
        String strCurrentDate = sdfYYYYMMDD.format(new java.util.Date());

        // Another case where we exclude SBB based on SBBUNPUBLISHCTDATE
        if (_ei.getEntityType().equals("SBB")) {
            for (int i = 0; i < _ei.getUpLinkCount(); i++) {
                EntityItem eiRelator = (EntityItem) _ei.getUpLink(i);
                D.ebug(D.EBUG_SPEW, "CCTOSBB eiRelator:" + eiRelator.getEntityType() + eiRelator.getEntityID());

                if (eiRelator.containsAttribute("SBBUNPUBLISHCTDATE")) {
                    String taDate = "" + (TextAttribute) eiRelator.getAttribute("SBBUNPUBLISHCTDATE");
                    D.ebug(
                        D.EBUG_SPEW,
                        "Relator CCTOSBB Compare current date = '" + strCurrentDate + "' to text attribute = '" + taDate + "'");
                    // skip any entity with text attribute date value before or equal to today
                    if (taDate.length() == 10 && strCurrentDate.length() == 10 && !(strCurrentDate.compareTo(taDate) < 0)) {
                        D.ebug(D.EBUG_SPEW, "SBBs skipped from CCTOSBB: " + _ei.getEntityType() + _ei.getEntityID());
                        return true;
                    }
                }
            }
        }

        // Another case where we exclude CCTOSBB based on SBBUNPUBLISHCTDATE
        if (_ei.containsAttribute("SBBUNPUBLISHCTDATE")) {
            String taDate1 = "" + (TextAttribute) _ei.getAttribute("SBBUNPUBLISHCTDATE");
            D.ebug(
                D.EBUG_SPEW,
                "SBBUNPUBLISHCTDATE Compare current date = '" + strCurrentDate + "' to text attribute = '" + taDate1 + "'");
            // skip any entity with text attribute date value before or equal to today
            if (taDate1.length() == 10 && strCurrentDate.length() == 10 && !(strCurrentDate.compareTo(taDate1) < 0)) {
                for (int i = 0; i < _ei.getDownLinkCount(); i++) {
                    EntityItem eiRelator = (EntityItem) _ei.getDownLink(i);
                    if (eiRelator.getEntityType().equals("SBB")) {
                        D.ebug(D.EBUG_SPEW, "CCTOSBB skipped: " + _ei.getEntityType() + _ei.getEntityID());
                        // Skip this entity ...
                        return true;
                    }
                }
            }
        }

        // Another case where we exclude SBB based on SBBUNPUBLISHWWDATE
        if (_ei.getEntityType().equals("SBB")) {
            for (int i = 0; i < _ei.getUpLinkCount(); i++) {
                EntityItem eiRelator = (EntityItem) _ei.getUpLink(i);
                D.ebug(D.EBUG_SPEW, "CTOSBB eiRelator:" + eiRelator.getEntityType() + eiRelator.getEntityID());

                if (eiRelator.containsAttribute("SBBUNPUBLISHWWDATE")) {
                    String taDate = "" + (TextAttribute) eiRelator.getAttribute("SBBUNPUBLISHWWDATE");
                    D.ebug(
                        D.EBUG_SPEW,
                        "Relator CTOSBB Compare current date = '" + strCurrentDate + "' to text attribute = '" + taDate + "'");
                    // skip any entity with text attribute date value before or equal to today
                    if (taDate.length() == 10 && strCurrentDate.length() == 10 && !(strCurrentDate.compareTo(taDate) < 0)) {
                        D.ebug(D.EBUG_SPEW, "SBBs skipped from CTOSBB: " + _ei.getEntityType() + _ei.getEntityID());
                        return true;
                    }
                }
            }
        }

        // Another case where we exclude CTOSBB based on SBBUNPUBLISHWWDATE
        if (_ei.containsAttribute("SBBUNPUBLISHWWDATE")) {
            String taDate1 = "" + (TextAttribute) _ei.getAttribute("SBBUNPUBLISHWWDATE");
            D.ebug(
                D.EBUG_SPEW,
                "SBBUNPUBLISHWWDATE Compare current date = '" + strCurrentDate + "' to text attribute = '" + taDate1 + "'");
            // skip any entity with text attribute date value before or equal to today
            if (taDate1.length() == 10 && strCurrentDate.length() == 10 && !(strCurrentDate.compareTo(taDate1) < 0)) {
                for (int i = 0; i < _ei.getDownLinkCount(); i++) {
                    EntityItem eiRelator = (EntityItem) _ei.getDownLink(i);
                    if (eiRelator.getEntityType().equals("SBB")) {
                        D.ebug(D.EBUG_SPEW, "CTOSBB skipped: " + _ei.getEntityType() + _ei.getEntityID());
                        // Skip this entity ...
                        return true;
                    }
                }
            }
        }

        // Another case where we exclude SBB based on SBBWITHDRAWLDATE
        if (_ei.containsAttribute("SBBWITHDRAWLDATE")) {
            String taDate = "" + (TextAttribute) _ei.getAttribute("SBBWITHDRAWLDATE");
            D.ebug(
                D.EBUG_SPEW,
                "SBBWITHDRAWLDATE Compare current date = '" + strCurrentDate + "' to text attribute = '" + taDate + "'");
            // skip any entity with text attribute date value before or equal to today
            if (taDate.length() == 10 && strCurrentDate.length() == 10 && !(strCurrentDate.compareTo(taDate) < 0)) {
                D.ebug(D.EBUG_SPEW, "SBBWITHDRAWLDATE skip" + _ei.getEntityType() + _ei.getEntityID());
                // Skip this entity ...
                return true;
            }
        }

        // Another case where we exclude SBB that are only linked to CTO and not CCTO
        if (_ei.getEntityType().equals("SBB")) {
            boolean CTOSBBresult = false;
            boolean CCTOSBBresult = false;

            for (int i= 0; i < _ei.getUpLinkCount(); i++) {
                EntityItem eiParent = (EntityItem) _ei.getUpLink(i);
                if (eiParent.getEntityType().equals("CTOSBB")) {
                   D.ebug(D.EBUG_SPEW, "CTOSBB Linked to SBB: " + eiParent.getEntityType() + eiParent.getEntityID());
                    CTOSBBresult = true;
                }
                if (eiParent.getEntityType().equals("CCTOSBB")) {
                   D.ebug(D.EBUG_SPEW, "CCTOSBB Linked to SBB: " + eiParent.getEntityType() + eiParent.getEntityID());
                    CCTOSBBresult = true;
                }
            }
            if (CTOSBBresult && !CCTOSBBresult) {
                D.ebug(D.EBUG_SPEW, "SBB skipped linked only to CTO" + _ei.getEntityType() + _ei.getEntityID());
                return true;
            }

        }
        return false;


    }

    //skip based on RoleCode
    public final static boolean skipBasedonProf(EntityItem _ei, Database _db, Profile _prof) {
 //       java.text.SimpleDateFormat sdfYYYYMMDD = new java.text.SimpleDateFormat("yyyy-MM-dd");
       // String strCurrentDate = sdfYYYYMMDD.format(new java.util.Date());
        EANAttribute Attr = null;
		String RoleCode = _prof.getRoleCode();

        D.ebug(D.EBUG_SPEW, "skipBasedonProf RoleCode" + RoleCode);

            for (int i = 0; i < _ei.getAttributeCount(); i++) {
            	Attr = _ei.getAttribute(i);
                D.ebug(D.EBUG_SPEW, "skipBasedonProf Attr:" + Attr);
                if (Attr !=null && Attr.isEditable()){
                	System.out.println(Attr.toString());
                	return false;
                } else {
                	System.out.println(Attr.toString());
                	return true;
                }
            }
      return false;
    }

    /**
     * This guy generates a Translation Package for the PackageID.  It will be called exclusively from the ABR's that drive
     * package creation and initial queueing
     *
     * @param _db
     * @param _prof
     * @param _pkID
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return TranslationPackage
     */
    public final static TranslationPackage generatePDHPackage(Database _db, Profile _prof, PackageID _pkID)
        throws SQLException, MiddlewareException {

        String strEnterprise = _prof.getEnterprise();
        int iTargetNLSID = _pkID.getNLSID();
        int iNLSID = _prof.getReadLanguage().getNLSID();
        String strMemoryHint = "BRAND|FAMILY|SERIES|LANGUAGE";

        TranslationPackage tpReturn = new TranslationPackage(_pkID, strMemoryHint);

        java.text.SimpleDateFormat sdfYYYYMMDD = new java.text.SimpleDateFormat("yyyy-MM-dd");

        sdfYYYYMMDD.format(new java.util.Date());

        if (_pkID.isDataType()) {

            String strExtract = null;
            EntityGroup egXLATEGRP = null;
            EntityItem eiParent = null;
            EntityItem[] aei = null;
            EANFlagAttribute flagExt = null;
            ExtractActionItem eai = null;
            EntityList el = null;

            EntityGroup egBrand = null;
            EntityGroup egFamily = null;
            EntityGroup egSeries = null;
            EntityGroup egXlategrp = null;

            EntityItem eiBrand = null;
            EntityItem eiFamily = null;
            EntityItem eiSeries = null;
            EntityItem eiXlategrp = null;
            boolean bSkipThis = false;

            TranslationDataRequest tdr = new TranslationDataRequest();

            tpReturn.setDataRequest(tdr);

            // GAB - 052804 - begin CR5530
            egXLATEGRP = new EntityGroup(null, _db, _prof, _pkID.getEntityType(), "Edit");
            eiParent = new EntityItem(egXLATEGRP, _prof, _db, _pkID.getEntityType(), _pkID.getEntityID());
            aei = new EntityItem[] { eiParent };

            flagExt = (EANFlagAttribute) eiParent.getAttribute("TRANSEXTRACTATTR");
            if (flagExt == null) {
                _db.debug(D.EBUG_DETAIL, "flagExt is null for:");
                _db.debug(D.EBUG_DETAIL, eiParent.dump(false));
                _db.debug(D.EBUG_DETAIL, "reverting to EXTXLATEGRP1 extract.");
                strExtract = "EXTXLATEGRP1";
            } else {
                _db.debug(D.EBUG_DETAIL, "everythings good");
                strExtract = flagExt.getFirstActiveFlagCode();
            }

            _db.debug(D.EBUG_DETAIL, "using strExtract = " + strExtract);

            eai = new ExtractActionItem(null, _db, _prof, strExtract);
            el = new EntityList(_db, _prof, eai, aei, "XLATEGRP");

            // GAB - 052804 - end CR5530

            egBrand = el.getEntityGroup("BR");
            egFamily = el.getEntityGroup("FAM");
            egSeries = el.getEntityGroup("SE");

            _db.debug(D.EBUG_DETAIL, "egBrand = " + egBrand + " egFamily = " + egFamily + " egSeries = " + egSeries);

            if (egBrand !=null && egFamily !=null && egSeries !=null) {

            eiBrand = egBrand.getEntityItem(0);
            eiFamily = egFamily.getEntityItem(0);
            eiSeries = egSeries.getEntityItem(0);

            strMemoryHint = (eiBrand == null ? "BR" : getEntityName(egBrand.getEntityItem(0))) + "|";
            strMemoryHint = strMemoryHint + (eiFamily == null ? "FAM" : getEntityName(egFamily.getEntityItem(0))) + "|";
            strMemoryHint = strMemoryHint + (eiSeries == null ? "SER" : getEntityName(egSeries.getEntityItem(0))) + "|";
            strMemoryHint = strMemoryHint + _pkID.getLanguage();
            tpReturn.setMemoryHint(strMemoryHint);
            } else {
            egXlategrp = el.getEntityGroup("XLATEGRP");

            _db.debug(D.EBUG_DETAIL, "We are using egXlategrp = " + egXlategrp);

            eiXlategrp = egXlategrp.getEntityItem(0);

            strMemoryHint = (eiXlategrp == null ? "XLATEGRP" : getFamily(egXlategrp.getEntityItem(0))) + "|";
            strMemoryHint = strMemoryHint + (eiXlategrp == null ? "XLATEGRP" : getXlateBillingCode(egXlategrp.getEntityItem(0))) + "|";
            strMemoryHint = strMemoryHint + _pkID.getLanguage();
            tpReturn.setMemoryHint(strMemoryHint);

            }

            bSkipThis = false;

            // Loop through the VE and pull the stuff into the translation package
            for (int ii = 0; ii < el.getEntityGroupCount(); ii++) {
                EntityGroup eg = el.getEntityGroup(ii);
                bSkipThis = false;

                for (int iy = 0; iy < eg.getEntityItemCount(); iy++) {
                    TranslationEntity teData = null;
                    EntityItem ei = eg.getEntityItem(iy);

                    // If TRANSLATIONATTR is set, we ~may~ need to skip this entity depending on NLSID value & flag code setting
                    if (ei.containsAttribute("TRANSLATIONATTR")) {
                        MultiFlagAttribute mfa = (MultiFlagAttribute) ei.getAttribute("TRANSLATIONATTR");
                        if (mfa.isSelected("0") || mfa.isSelected("" + iTargetNLSID)) {
                            // Skip this entity ...
                            continue;
                        }
                    }

                    bSkipThis = skipSBBData(ei);

                    if (bSkipThis) {
                        continue;
                    }

                    teData = new TranslationEntity(strEnterprise, ei.getEntityType(), ei.getEntityID(), eg.getLongDescription());
                    tdr.addEntity(teData);
                	System.out.println("**************************************Starting translation Data Attr Skipping Process**************************************");
                	System.out.println("Profile: " + _prof.getRoleCode());
                	for (int iz = 0; iz < eg.getMetaAttributeCount(); iz++) {
                        EANMetaAttribute ma = eg.getMetaAttribute(iz);
                        EANAttribute att = ei.getAttribute(ma.getAttributeCode());
                        if (!ma.isEditable()) {
                        	System.out.println("Attr skipped based on Role: " + ma.getAttributeCode() + " - " + ma.toString());
                        	continue;
                        } else {
                        tdr.addAttribute(
                            teData,
                            new TranslationAttribute(
                                strEnterprise,
                                ei.getEntityType(),
                                ei.getEntityID(),
                                ma.getAttributeType(),
                                ma.getAttributeCode(),
                                ma.getLongDescription(),
                                (att == null ? "" : att.toString()),
                                iNLSID,
                                iTargetNLSID,
                                ma.isEditable()));
                        }
                    }
                	System.out.println("**************************************Done with translation Data Attr Skipping Process**************************************");
                }
            }
        } else {
            //
            // Here is where you make the Meta Translation Package
            //
            MetaTranslationGroup mtg = null;
            NLSItem[] anls = new NLSItem[1];
            TranslationMetaRequest tmr = new TranslationMetaRequest();

            tpReturn.setMetaRequest(tmr);

            anls[0] = new NLSItem(iTargetNLSID, "Dont Know");
            mtg = new MetaTranslationGroup(_db, _prof, anls);
            for (int i = 0; i < mtg.getMetaTranslationItemCount(); i++) {
                MetaTranslationItem mti = mtg.getMetaTranslationItem(i);
                TranslationMetaAttribute tmaFlag = new TranslationMetaAttribute(mti, iNLSID, iTargetNLSID);
                D.ebug(D.EBUG_SPEW, "" + tmaFlag);
                tmr.addAttribute(tmaFlag);
            }
        }
        D.isplay("XXX BillingCode = " + tpReturn.getBillingCode());
        return tpReturn;

    }

    /**
     * getETSPackage
     *
     * @param _db
     * @param _prof
     * @param _pkID
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     * @author Dave
     */
    public final static TranslationPackage getETSPackage(Database _db, Profile _prof, PackageID _pkID)
        throws SQLException, MiddlewareException {

        // This is what was the ETS Package is and was when it was sent
        // String strMemoryHint = "BRAND|FAMILY|SERIES|LANGUAGE";
        TranslationPackage tpReturn = null;

        //  TranslationPackage tpReturn = new TranslationPackage(_pkID, strMemoryHint);
        //tpReturn.setDataRequest(new TranslationDataRequest());
        String strAttributeCode = (_pkID.isDataType()) ? "XLETSPACKAGE" : "XLMETSPACKAGE";
        try {
            // Always need NLSID to match what the package was stored as.. not what the user is lookng at
            Blob blobTranslation =
                _db.getBlobNow(_prof, _pkID.getEntityType(), _pkID.getEntityID(), strAttributeCode, _pkID.getNLSID());
            if (blobTranslation != null) {
                tpReturn = (TranslationPackage) blobTranslation.asObject();
            }
        } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "Trouble with blob in getETSPackage" + x.getMessage());
            //x.printStackTrace();
        }

        return tpReturn;

    }

    /**
     * getPDHPackage
     *
     * @param _db
     * @param _prof
     * @param _pkID
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @return
     * @author Dave
     */
    public final static TranslationPackage getPDHPackage(Database _db, Profile _prof, PackageID _pkID)
        throws SQLException, MiddlewareException {

        TranslationPackage tpReturn = null;

        String strAttributeCode = (_pkID.isDataType()) ? "XLPDHPACKAGE" : "XLMPDHPACKAGE";
        try {
            // Always need NLSID to match what the package was stored as.. not what the user is lookng at
            Blob blobTranslation =
                _db.getBlobNow(_prof, _pkID.getEntityType(), _pkID.getEntityID(), strAttributeCode, _pkID.getNLSID());
            if (blobTranslation != null) {
                tpReturn = (TranslationPackage) blobTranslation.asObject();
            }
        } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "Trouble with blob in getPDHPackage" + x.getMessage());
            x.printStackTrace();
        }

        return tpReturn;
    }

    /**
     * putETSPackage
     *
     * @param _db
     * @param _prof
     * @param _trnp
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @author Dave
     */
    public final static void putETSPackage(Database _db, Profile _prof, TranslationPackage _trnp)
        throws SQLException, MiddlewareException {

        Vector vctTransactions = new Vector();

        // Set up the dates here
        DatePackage dpCurrent = _db.getDates();
        String strNow = dpCurrent.getNow();
        String strForever = dpCurrent.getForever();

        String strEnterprise = _prof.getEnterprise();
        String strAttributeCode = (_trnp.isDataType()) ? "XLETSPACKAGE" : "XLMETSPACKAGE";
        String strBlobExtension = "SERIALIZEDOBJECT";
        String strEntityType = _trnp.getPackageID().getEntityType();

        // Remember.. the blob values are stored in the NLSID variant of the XLTRANSPACKAGE attribute code
        int iEntityID = _trnp.getPackageID().getEntityID();
        int iNLSID = _trnp.getPackageID().getNLSID();
        int iOPWGID = _prof.getOPWGID();

        ControlBlock cbControlBlock = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID);
        ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);

        Blob blobTranslation = null;

        rek.m_vctAttributes = new Vector();

        try {
            blobTranslation =
                new Blob(
                    strEnterprise,
                    strEntityType,
                    iEntityID,
                    strAttributeCode,
                    _trnp,
                    strBlobExtension,
                    iNLSID,
                    cbControlBlock);
            rek.m_vctAttributes.addElement(blobTranslation);
        } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "Trouble with blob in putETSPackage" + x.getMessage());
            x.printStackTrace();
        }

        vctTransactions.addElement(rek);
        _db.update(_prof, vctTransactions, true, false);
        _db.freeStatement();
        _db.isPending();

    }

    /**
     * putPDHPackage
     *
     * @param _db
     * @param _prof
     * @param _trnp
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @author Dave
     */
    public final static void putPDHPackage(Database _db, Profile _prof, TranslationPackage _trnp)
        throws SQLException, MiddlewareException {

        Vector vctTransactions = new Vector();

        // Set up the dates here
        DatePackage dpCurrent = _db.getDates();
        String strNow = dpCurrent.getNow();
        String strForever = dpCurrent.getForever();

        String strEnterprise = _prof.getEnterprise();
        String strAttributeCode = (_trnp.isDataType()) ? "XLPDHPACKAGE" : "XLMPDHPACKAGE";
        String strBlobExtension = "SERIALIZEDOBJECT";
        String strEntityType = _trnp.getPackageID().getEntityType();

        // Remember.. the blob values are stored in the NLSID variant of the XLTRANSPACKAGE attribute code
        int iEntityID = _trnp.getPackageID().getEntityID();
        int iTargetNLSID = _trnp.getPackageID().getNLSID();
        int iOPWGID = _prof.getOPWGID();

        ControlBlock cbControlBlock = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID);
        ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);
        Blob blobTranslation = null;

        rek.m_vctAttributes = new Vector();
        try {
            blobTranslation =
                new Blob(
                    strEnterprise,
                    strEntityType,
                    iEntityID,
                    strAttributeCode,
                    _trnp,
                    strBlobExtension,
                    iTargetNLSID,
                    cbControlBlock);
            rek.m_vctAttributes.addElement(blobTranslation);
        } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "Trouble with blob in putPDHPackage" + x.getMessage());
            x.printStackTrace();
        }

        vctTransactions.addElement(rek);
        _db.update(_prof, vctTransactions, true, false);
        _db.freeStatement();
        _db.isPending();

    }

    /**
     * postETSPackage
     *
     * @param _db
     * @param _prof
     * @param _pkID
     * @throws java.sql.SQLException
     * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
     * @author Dave
     * @throws MaxTranslationException
     */
    public final static void postETSPackage(Database _db, Profile _prof, PackageID _pkID, boolean validateMaxLength)
        throws SQLException, MiddlewareException, MaxTranslationException {

    	//Store translation errors and throw an exception in the end (if has errors)
    	MaxTranslationException maxTranslationException = new MaxTranslationException();

        // This gets invoked by an ABR.. we need to post the status..
        Vector vctTransactions = new Vector();
        int iOPWGID = _prof.getOPWGID();

        DatePackage dpCurrent = _db.getDates();
        String strNow = dpCurrent.getNow();
        String strForever = dpCurrent.getForever();
        String strEnterprise = _prof.getEnterprise();
        ControlBlock cbControlBlock = null;
        TranslationPackage tpPost = null;

        _prof.setValOn(strNow);
        _prof.setEffOn(strNow);

        cbControlBlock = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID);

        // This retrieves the package from the Entity.. for the given package info
        tpPost = getETSPackage(_db, _prof, _pkID);

        if (tpPost.isDataType()) {
            // Lets get the data from the PDH
            // GAB - 052804 - begin CR5530
            String strExtract = null;
            ExtractActionItem eai = null;
            EntityList el = null;
            TranslationDataRequest tdr = null;

            EntityGroup egXLATEGRP = new EntityGroup(null, _db, _prof, _pkID.getEntityType(), "Edit");
            EntityItem eiParent = new EntityItem(egXLATEGRP, _prof, _db, _pkID.getEntityType(), _pkID.getEntityID());
            EntityItem[] aei = new EntityItem[] { eiParent };
            EANFlagAttribute flagExt = (EANFlagAttribute) eiParent.getAttribute("TRANSEXTRACTATTR");

            if (flagExt == null) {
                _db.debug(D.EBUG_DETAIL, "flagExt is null for:");
                _db.debug(D.EBUG_DETAIL, eiParent.dump(false));
                _db.debug(D.EBUG_DETAIL, "reverting to EXTXLATEGRP1 extract.");
                strExtract = "EXTXLATEGRP1";
                //throw new MiddlewareException("generatePDHPackage:No Extract Flag (TRANSEXTRACTATTR) set for Translation Package:" + _pkID.toString());
            } else {
                _db.debug(D.EBUG_DETAIL, "everythings good");
                strExtract = flagExt.getFirstActiveFlagCode();
            }

            _db.debug(D.EBUG_DETAIL, "using strExtract = " + strExtract);

            eai = new ExtractActionItem(null, _db, _prof, strExtract);
            el = new EntityList(_db, _prof, eai, aei, "XLATEGRP");
            // GAB - 052804 - end CR5530

            //
            // Set the appropriate profile here...
            //
            el.getProfile().setReadLanguage(new NLSItem(_pkID.getNLSID(), _pkID.getLanguage()));
            D.ebug(tpPost.dump());

            tdr = tpPost.getDataRequest();
            for (int i = 0; i < tdr.m_vctEntities.size(); i++) {
                TranslationEntity te = (TranslationEntity) tdr.m_vctEntities.elementAt(i);
                String strEntityType = te.getEntityType();
                int iEntityID = te.getEntityID();
                ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);
                EntityGroup eg = el.getEntityGroup(strEntityType);
                EntityItem ei = eg.getEntityItem(strEntityType, iEntityID);

                rek.m_vctAttributes = new Vector();

                if (ei == null) {
                    D.ebug(
                        D.EBUG_DETAIL,
                        "Translation.postETSTranslation.Skipping because Entity cannot be found" + strEntityType + ":" + iEntityID);
                } else {
                    D.ebug(ei.dump(false));
                    for (int iy = 0; iy < te.m_vctAttributes.size(); iy++) {
                        TranslationAttribute ta = (TranslationAttribute) te.m_vctAttributes.elementAt(iy);
                        String strAttributeType = ta.getAttributeType();
                        String strAttributeCode = ta.getAttributeCode();
                        String strOriginalTranslatedValue = ta.hasTransDescOverride() ? ta.getTransDescOverride() : ta.getTranslatedDescription();
                        int maxLength;
                        int defaultLength = (strAttributeType.equals("T") || strAttributeType.equals("I") ? 254 : 31000);
                        if (validateMaxLength) {
                        //&& (strAttributeType.equals("T") || strAttributeType.equals("I") || strAttributeType.equals("L"))) {
                            EANMetaAttribute ma = eg.getMetaAttribute(strAttributeCode);
                            maxLength = ma.getOdsLength() > 0 ? ma.getOdsLength() : defaultLength;
                        } else {
							maxLength = defaultLength;
						}

                        String strTranslatedValue = clip(strAttributeCode, strOriginalTranslatedValue, maxLength);
                        int iNLSID = ta.getTranslatedNLSID();

                        if (validateMaxLength && !strOriginalTranslatedValue.equals(strTranslatedValue)) {
                        	maxTranslationException.addError(te.getEntityID(),
                        			te.getEntityType(), te.getEntityDescription(),strAttributeCode, iNLSID,
                        			strOriginalTranslatedValue, strTranslatedValue);
                        }

                        if (!ta.getTranslatable()) {
                            D.ebug(
                                D.EBUG_DETAIL,
                                "Translation.postETSTranslation.Skipping Translation because this is a read only Attribute:"
                                    + strEntityType
                                    + ":"
                                    + iEntityID
                                    + ":"
                                    + strAttributeCode
                                    + ":"
                                    + strTranslatedValue
                                    + ":"
                                    + iNLSID);
                        } else if (strTranslatedValue == null) {
                            D.ebug(
                                D.EBUG_DETAIL,
                                "Translation.postETSTranslation.Skipping Translation because no Translation was performed (null):"
                                    + strEntityType
                                    + ":"
                                    + iEntityID
                                    + ":"
                                    + strAttributeCode
                                    + ":"
                                    + strTranslatedValue
                                    + ":"
                                    + iNLSID);
                        } else if (strTranslatedValue.length() == 0) {
                            D.ebug(
                                D.EBUG_DETAIL,
                                "Translation.postETSTranslation.Skipping Translation because no Translation was performed (empty):"
                                    + strEntityType
                                    + ":"
                                    + iEntityID
                                    + ":"
                                    + strAttributeCode
                                    + ":"
                                    + strTranslatedValue
                                    + ":"
                                    + iNLSID);
                        } else {
                            EANAttribute att = (EANAttribute) ei.getEANObject(strEntityType + ":" + strAttributeCode);
                            D.ebug(
                                D.EBUG_DETAIL,
                                "Translation.postETSTranslation.We have a value:"
                                    + strEntityType
                                    + ":"
                                    + iEntityID
                                    + ":"
                                    + strAttributeCode
                                    + ":"
                                    + strTranslatedValue
                                    + ":"
                                    + iNLSID);

                            if (att != null) {
                                String strPDHValue = att.toString();
                                // Lets look at override here...
                                if (!strPDHValue.equals(strTranslatedValue)) {
                                    // Its a go.. lets add stuff to the vector
                                    if (strAttributeType.equals("T") || strAttributeType.equals("I")) {
                                        D.ebug(
                                            D.EBUG_DETAIL,
                                            "Translation.postETSTranslation.Posting Text:"
                                                + strEntityType
                                                + ":"
                                                + iEntityID
                                                + ":"
                                                + strAttributeCode
                                                + ":"
                                                + strPDHValue
                                                + ":"
                                                + strTranslatedValue
                                                + ":"
                                                + iNLSID);
                                        rek.m_vctAttributes.addElement(
                                            new Text(
                                                strEnterprise,
                                                strEntityType,
                                                iEntityID,
                                                strAttributeCode,
                                                strTranslatedValue,
                                                iNLSID,
                                                cbControlBlock));
                                    } else if (strAttributeType.equals("L") || strAttributeType.equals("X")) {
                                        D.ebug(
                                            D.EBUG_DETAIL,
                                            "Translation.postETSTranslation.Posting Long Text:"
                                                + strEntityType
                                                + ":"
                                                + iEntityID
                                                + ":"
                                                + strAttributeCode
                                                + ":"
                                                + strPDHValue
                                                + ":"
                                                + strTranslatedValue
                                                + ":"
                                                + iNLSID);
                                        rek.m_vctAttributes.addElement(
                                            new LongText(
                                                strEnterprise,
                                                strEntityType,
                                                iEntityID,
                                                strAttributeCode,
                                                strTranslatedValue,
                                                iNLSID,
                                                cbControlBlock));
                                    }
                                } else {
                                    D.ebug(
                                        D.EBUG_DETAIL,
                                        "Translation.postETSTranslation.Skipping Translation value. Already Posted:"
                                            + strEntityType
                                            + ":"
                                            + iEntityID
                                            + ":"
                                            + strAttributeCode
                                            + ":"
                                            + strPDHValue
                                            + ":"
                                            + strTranslatedValue
                                            + ":"
                                            + iNLSID);
                                }
                            } else {
                                D.ebug(
                                    D.EBUG_DETAIL,
                                    "Translation.postETSTranslation.Skipping Attribute. No AttributeCode is found in that Entity"
                                        + strEntityType
                                        + ":"
                                        + iEntityID
                                        + ":"
                                        + strAttributeCode
                                        + ":"
                                        + strTranslatedValue
                                        + ":"
                                        + iNLSID);
                            }
                        }
                    }
                }
                if (rek.m_vctAttributes.size() > 0) {
                    vctTransactions.addElement(rek);
                }
            }

            //_db.update(_prof, vctTransactions, true, true);
            _db.update(_prof, vctTransactions, true, false);
            _db.freeStatement();
            _db.isPending();

        } else {

            // Post META type by grabbing each attribute and calling GBL2951 to post
            TranslationMetaRequest tmrThis = tpPost.getMetaRequest();
            Enumeration eTest1 = tmrThis.getAttributeKeys();

            while (eTest1.hasMoreElements()) {
                String strKey = (String) eTest1.nextElement();
                TranslationMetaAttribute tmaTest = tmrThis.getAttributeElement(strKey);
                int iNLSID = tmaTest.getTranslatedNLSID();
                String strAttributeCode = tmaTest.getAttributeCode();
                String strAttributeValue = tmaTest.getAttributeValue();
                String strLongDescription =
                    clip(
                        strAttributeCode,
                        (tmaTest.hasTransDescOverride() ? tmaTest.getTransDescOverride() : tmaTest.getTranslatedDescription()),
                        128);
                ;
                _db.debug(D.EBUG_SPEW, "POSTING Meta Description attribute");
                _db.debug(D.EBUG_SPEW, "META: ky '" + strKey + "'");
                _db.debug(D.EBUG_SPEW, "META: ac '" + strAttributeCode + "'");
                _db.debug(D.EBUG_SPEW, "META: av '" + strAttributeValue + "'");
                _db.debug(D.EBUG_SPEW, "META: ld '" + strLongDescription + "'");
                _db.debug(D.EBUG_SPEW, "META: ni '" + iNLSID + "'");

                // Need to the update
                _db.callGBL2909(
                    new ReturnStatus(-1),
                    _prof.getOPWGID(),
                    _prof.getEnterprise(),
                    strAttributeCode,
                    strAttributeValue,
                    "",
                    strLongDescription,
                    iNLSID,
                    TRAN_ID,
                    strNow,
                    strForever);
                _db.freeStatement();
                _db.isPending();
            }
        }

        setStatus(_db, _prof, _pkID, STATUS_XLATE_COMPLETE);

        if (maxTranslationException.hasErrors())
        	throw maxTranslationException;
    }

    /**
     * setStatus
     *
     * @param _db
     * @param _prof
     * @param _pkid
     * @param _ps
     * @author Dave
     */
    public final static void setStatus(Database _db, Profile _prof, PackageID _pkid, PackageStatus _ps) {

        // Based upon the language.
        // We need to update the status of the appropriate attributecode.
        // We need to use workflow here .. because
        // we need to also ensure that the Queue table gets updated appropriately
        // We take it out of
        // base

        int iOPWGID = _prof.getOPWGID();
        String strEnterprise = _prof.getEnterprise();
        String strEntityType = _pkid.getEntityType();
        int iEntityID = _pkid.getEntityID();
        String strAttributeCode = _pkid.getMetaStatusCode();
        String strAttributeValue = _ps.getFlagCode();
        String strQueueName = _pkid.getGroupQueue();
        Vector vctTransactions = new Vector();
        Vector vctAttributes = new Vector();

        try {

            DatePackage dpDates = new DatePackage(_db);
            String strNow = dpDates.m_strNow;
            String strForever = dpDates.m_strForever;
            ControlBlock cb = new ControlBlock(strNow, strForever, strNow, strForever, iOPWGID);

            ReturnEntityKey rek = new ReturnEntityKey(strEntityType, iEntityID, true);
            vctAttributes.addElement(
                new SingleFlag(strEnterprise, strEntityType, iEntityID, strAttributeCode, strAttributeValue, 1, cb));
            rek.m_vctAttributes = vctAttributes;
            vctTransactions.addElement(rek);
            // Change status in database
            _db.update(_prof, vctTransactions);
            _db.commit();
            _db.debug(
                D.EBUG_DETAIL,
                "setStatus:"
                    + strEnterprise
                    + ":"
                    + strEntityType
                    + ":"
                    + iEntityID
                    + ":"
                    + strAttributeCode
                    + ":"
                    + strAttributeValue);

            if (_ps.equals(STATUS_XLATE_INPROC)) {
                // If it moved to inprocess.. we need to attempt to take it out of the group queue
                int iNextStatus = 1;
                int iCurrentStatus = 0;
                _db.callGBL7422(
                    new ReturnStatus(-1),
                    strEnterprise,
                    strQueueName,
                    strEntityType,
                    iEntityID,
                    iCurrentStatus,
                    iNextStatus);
                _db.commit();
                _db.freeStatement();
                _db.isPending();
            }

        } catch (Exception x) {
            _db.debug(D.EBUG_ERR, " trouble updating Translation Status status " + x);
        } finally {
            _db.freeStatement();
            _db.isPending("finally after update in seStatusof Translation");
        }
    }

    private final static String getEntityName(EntityItem _ei) throws MiddlewareException {
        EANAttribute att = null;
        if (_ei == null) {
            return "UNKNOWN";
        }
        att = _ei.getAttribute("NAME");
        return (att == null ? "UNKNOWN" : att.toString());
    }

    private final static String getFamily(EntityItem _ei) throws MiddlewareException {
        EANAttribute att = null;
        if (_ei == null) {
            return "UNKNOWN";
        }
        att = _ei.getAttribute("FAMILY");
        return (att == null ? "UNKNOWN" : att.toString());
    }

    private final static String getXlateBillingCode(EntityItem _ei) throws MiddlewareException {
        EANAttribute att = null;
        if (_ei == null) {
            return "UNKNOWN";
        }
        att = _ei.getAttribute("BILLINGCODE");
        return (att == null ? "UNKNOWN" : att.toString());
    }

    /**
     * clip
     *
     * @param _strCode
     * @param _strValue
     * @param _i
     * @return
     * @author Dave
     */
    public static final String clip(String _strCode, String _strValue, int _i) {

        try {
            if (_strValue != null) {
                int iMaxLength = _i;
                int iLength = _strValue.getBytes("UTF8").length;

                if (iLength > iMaxLength) {
                    int iExceededByteCount = 0;
                    while (iLength > iMaxLength) {
                        iExceededByteCount = iLength - iMaxLength;
                        _strValue = _strValue.substring(0, _strValue.length() - iExceededByteCount);
                        iLength = _strValue.getBytes("UTF8").length;
                    }
                    D.ebug(D.EBUG_DETAIL, "Translation.clip. Truncated:" + _strCode + ":Len:" + _i + ", Value :" + _strValue);
                }
            }
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return _strValue;
    }

    /**
     * getVersion
     * @return
     * @author Dave
     */
    public final static String getVersion() {
        return "$Id: Translation.java,v 1.193 2010/04/13 17:35:16 praveen Exp $";
    }
}
