//Licensed Materials -- Property of IBM
//(C) Copyright IBM Corp. 2008  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
package COM.ibm.eannounce.abr.sg.adsxmlbh1;


import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.transactions.*;
import COM.ibm.opicmpdh.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.*;
import java.io.*;

import javax.xml.parsers.*;

import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**********************************************************************************
 *
 The ABR will use the Role "SYSFEEDADMIN" which identifies the subset of attributes that are of interest.

 If SYSFEEDRESEND = 'Yes' (Yes) THEN
 If Status (STATUS) = 'Ready for Review' (0040) or 'Final' THEN
 T1 = ?980-01-01?
 ELSE
 Set SYSFEEDRESEND = 'No' (No)
 ErrorMessage LD(of the Root Entity Type) NDN(of the Root Entity) 'was queued to resend data; however,
    the data is not Ready for Review or Final'.
 Done
 END IF
 ELSE
 If this is the first time that STATUS (STATUS) = 'Ready for Review' (0040) or 'Final' (0020) THEN
 T1 = ?980-01-01?
 ELSE
 T1 is the DTS of the prior feed. This is the VALFROM for the ABR value of Queued prior to the last value of Success.
 END IF
 END IF

 B. XML

 The Virtual Entity (VE) extract will be performed two times:
 1. T1 is the DTS determined by the prior section
 2. T2 is the Date/Time Stamp (DTS) that the ABR was Queued (0020).


actionTaken
ACTION_R4R_FIRSTTIME = Ready for Review - First Time
ACTION_R4R_CHANGES = Ready for Review - Changes
ACTION_R4R_RESEND = Ready for Review - Resend
ACTION_FINAL_FIRSTTIME = Final - First Time
ACTION_FINAL_CHANGES = Final - Changes
ACTION_FINAL_RESEND = Final - Resend
 *
 */
//$Log: ADSABRSTATUS.java,v $
//Revision 1.105  2019/05/27 12:46:15  xujianbo
//Story 1987505-Add chunk function for EACM price - Development
//
//Revision 1.104  2019/03/08 06:53:58  xujianbo
//Roll back again for the code for  Story 1909631 EACM XML version control by XSLT
//
//Revision 1.102  2019/02/26 07:18:09  xujianbo
//roll back code for Story 1909631 EACM XML version control by XSLT
//
//Revision 1.96  2015/04/27 09:21:02  wangyul
//IN6289031(RTC: 1318946) -GBT STATUS is not getting set for XML Cache table
//
//Revision 1.96  2015/04/20 21:44:01  guobin
//IN6289031 -GBT STATUS is not getting set for XML Cache table
//
//Revision 1.95  2015/03/24 07:31:01  wangyul
//update periodical ABR t2 time for the PR24827 -- heapdump file sound in EACM-SG server
//
//Revision 1.94  2015/01/26 15:51:22  wangyul
//fix the issue PR24222 -- SPF ADS abr string buffer
//
//Revision 1.93  2015/01/15 07:47:41  guobin
//fix outofmemory of XMLPRODPRICE ABR , add new method get latest history record of periodical setup entity.
//
//Revision 1.92  2014/12/12 16:19:47  wangyul
//fix the OutOfMemoryError of the stringbuffer
//
//Revision 1.91  2014/01/17 11:18:26  wangyulo
//the ABR changes needed to comply with V17 standards
//
//Revision 1.90  2014/01/10 14:55:52  wangyulo
//fix defect for the T1 & T2 of the Periodical ABR
//
//Revision 1.89  2014/01/07 08:49:24  wangyulo
// change the DTSOFMSG using the profile.getLoginTime
//
//Revision 1.88  2014/01/07 08:40:51  guobin
//BH FS ABR XML System Feed 20130904.doc updates on T1 and T2 derivation
//
//Revision 1.87  2013/11/12 16:03:17  guobin
//delete XML - Avails RFR Defect: BH 185136 -: VV DOA:REGVVN- 293/390-7906AC1/MC1 The Withdrawn FC A3AN,A3AP are displayed in UI
//
//Revision 1.86  2013/05/29 07:51:29  guobin
//WI 945852 -IN3766131 handle delta when old offering data changes from no AVAIL to having AVAIL.
//
//Revision 1.85  2013/03/29 06:46:08  wangyulo
//support RESEND from CACHE and support initialize CACHE after IDL base on BH FS ABR XML System Feed 20121210.doc
//
//Revision 1.84  2013/02/06 12:27:56  guobin
//RTC defect875877 - PUBTO values in TMF_UPDATE XML incorrect - BHCQ 157335
//
//Revision 1.83  2012/11/27 06:40:06  wangyulo
//fix the Defect 846153-- Cache load of REFOFER failed on LPAR5
//
//Revision 1.82  2012/11/01 13:27:26  wangyulo
//Update for the Defect 799135-- the new update for T1 time of the delta update with the BH FS ABR XML System Feed 20121009b.doc
//
//Revision 1.81  2012/11/01 02:29:17  wangyulo
//Update for the Defect 799135-- the new update for T1 time of the delta update with the BH FS ABR XML System Feed 20121009b.doc
//
//Revision 1.80  2012/10/19 13:48:22  wangyulo
//Update for the Defect 799135-- the new update for T1 & T2 with the BH FS ABR XML System Feed 20121009b.doc
//
//Revision 1.79  2012/09/30 07:04:06  guobin
//fix defect 799135 ( RFR data now flowing to BH prod)
//
//Revision 1.78  2012/08/31 16:05:40  guobin
//fix Sev 1 (126457) where another few FC sent only to WWPRT queue
//
//Revision 1.77  2012/08/27 11:45:06  guobin
//Reconcile CR of BH FS ABR XML System Feed 20120810 insert each row for each profile of MQ
//
//Revision 1.76  2012/08/14 09:38:00  wangyulo
//Fixing DTSOFMSG of 9999-12-31 in cache load, remove debug info
//
//Revision 1.75  2012/08/13 15:38:27  wangyulo
//Fixing DTSOFMSG of 9999-12-31 in cache load
//
//Revision 1.74  2012/08/11 16:22:13  wangyulo
//new update for the T1 and T2 base on the BH FS ABR XML System Feed 20120810.doc
//
//Revision 1.73  2012/08/07 16:17:14  wangyulo
//fix the Defect during UAT of T1 T2 - CRITICAL PROBLEM
//
//Revision 1.72  2012/07/31 14:47:16  wangyulo
//the Change XML design for updated handling of T1 and T2 in full and delta XML generation - DCUT
//
//Revision 1.71  2012/07/23 11:54:40  liuweimi
//Update XML design to use draft AVAILs other than Planned Availability
//
//Revision 1.70  2012/07/17 16:56:03  wangyulo
//add the switch to turn on or turn off to generate the userxml file for the periodic ABR
//
//Revision 1.69  2012/07/03 07:51:48  wangyulo
//Defect 743004  CR xxxx - Change XML design for updated handling of T1 and T2 in full and delta XML generation - DCUT
//
//Revision 1.68  2012/06/08 07:31:35  wangyulo
//Build Request for the wwprt pricexml of the outbound price - defect 737778
//
//Revision 1.67  2012/05/21 12:20:14  wangyulo
//fix the defect 724568 and 726107
//
//Revision 1.66  2012/04/24 13:03:57  wangyulo
//fix the defect 697075 - chg code to limit the number of price records in each MQ msg
//
//Revision 1.65  2012/04/10 15:12:33  wangyulo
//the change for the  WWCOMPAT_UPDATE generation
//
//Revision 1.64  2012/04/06 12:52:11  wangyulo
//the changes to WWCOMPAT_UPDATE generation
//
//Revision 1.63  2012/02/28 08:42:55  guobin
//[Work Item 655030] PCR-1 separate new VE's for Version 0.5 from V1.0
//
//Revision 1.62  2012/01/06 05:44:13  guobin
//Fix for defect 623425 - DM4 Cache Load creates XML where STATUS = 0050 (Change Request) T2 = T2 - 1 milisecond.
//
//Revision 1.61  2011/12/23 05:10:47  guobin
//Remove the adjustment of T2 by 30 seconds for defect 623425. According to the doc BH FS ABR Data Transformation System Feed 20111214.doc
//
//Revision 1.60  2011/12/14 02:19:30  guobin
//New setup entity for the Periodic ABR  WWCOMPAT, GENAREA, XMLPRODPRICESETUP and XLATE
//
//Revision 1.59  2011/11/29 18:50:37  guobin
// fix the DB2 commit issue
//
//Revision 1.58  2011/11/10 07:20:42  guobin
//Fix Defect 598849 Invalid countrylist for PRODSTRUCT with no AVAIL. VE  PRODSTRUCT2 don't need to check Planed AVAIL
//
//Revision 1.57  2011/10/26 08:03:06  guobin
// Final 閿燂拷 support for old data 閿燂拷 CQ 67890  Changed to handle offerings that have an AVAIL left in Draft where the data is older than 閿燂拷2010-03-01閿燂拷.
//
//Revision 1.56  2011/10/17 13:29:18  guobin
//Support both 0.5 and 1.0 XML together
//
//Revision 1.55  2011/09/27 01:26:00  guobin
//corrected filtering. Corrected REFOFER & REFOFERFEAT.  Add Planed AVAIL checking for ID
//
//Revision 1.54  2011/09/08 07:43:50  guobin
//add for REFOFER & REFOFERFEAT ADS ABR
//
//Revision 1.53  2011/08/19 07:16:05  guobin
//change MSGDTS of IDL reconciliation
//
//Revision 1.52  2011/08/10 07:18:43  liuweimi
//add MQRFH v2 header to message
//
//Revision 1.51  2011/07/15 14:35:21  guobin
//improve the performance of IDL
//
//Revision 1.50  2011/06/23 12:32:59  guobin
//Changes to T2 and to XMLCACHEDTS  XML IDL Cache
//
//Revision 1.49  2011/06/17 14:03:50  guobin
//change to getODSConnection and cache.xmlidlcache
//
//Revision 1.48  2011/06/15 14:43:11  guobin
//Status must final or R4R, when run of IDL  Cache
//
//Revision 1.47  2011/06/10 08:01:31  guobin
//delete add error infor e.getStackTrace
//
//Revision 1.46  2011/06/10 07:43:54  guobin
//update IDL function
//
//Revision 1.45  2011/06/07 14:16:38  guobin
//update IDL function
//
//Revision 1.44  2011/05/20 06:54:44  guobin
//full cache xml put and get
//
//Revision 1.43  2011/05/18 06:10:13  guobin
// CR CQ 32199 - Country Filter
//
//Revision 1.42  2011/04/11 12:20:12  guobin
//not convert HTML special characters for the attribute of the tag
//
//Revision 1.41  2011/04/07 13:30:21  guobin
//defect 463888 for HTML special characters
//
//Revision 1.40  2011/03/29 14:04:18  rick
//Change for 20110322 XML Gen VE 20110322.xls
//to not filter FEATURE and SWFEATURE FOR TMF XML
//(ADSPRODSTRUCT and ADSSWPRODSTRUCT VEs)
//
//Revision 1.39  2011/03/23 06:23:49  guobin
////Add ADSLSEO2 filter for AVAILElem of LSEO, if there is no Plan AVAIL, then find MODLE related AVAIL
//
//Revision 1.38  2011/03/21 18:57:48  wendy
//Allow for 1:M Assoc when filtering
//
//Revision 1.37  2011/03/15 16:48:18  wendy
//remove all up and down links when an item is filtered out
//
//Revision 1.36  2011/02/24 09:26:06  guobin
//add XLATE and SVCLEV abr to the abr table

//Revision 1.35  2011/02/09 07:25:49  guobin
//deal with the special character of  document to MQ

//Revision 1.34  2011/01/31 15:31:58  guobin
//update the special character of  document to MQ

//Revision 1.33  2011/01/28 09:19:30  guobin
//add parseAndConvert to deal the special character of docment

//Revision 1.32  2011/01/26 12:16:10  guobin
//IE6 doesn't support &apos; to convert single quotation marks,we can use &#39; instead

//Revision 1.31  2011/01/26 09:37:40  guobin
//update escape special characters in the XML

//Revision 1.30  2011/01/26 09:15:13  guobin
//change escape special characters in the XML

//Revision 1.29  2011/01/25 02:43:30  rick
//adding ADSWWCOMPATXMLABR

//Revision 1.28  2011/01/24 09:33:47  guobin
//Change pravite Static variables to protected

//Revision 1.27  2011/01/10 12:05:22  guobin
//Add image abr

//Revision 1.26  2010/12/10 09:34:27  guobin
//comment out the unnecessary debug.

//Revision 1.25  2010/11/18 06:41:13  guobin
//MQ Prop File not found, set ReturnCode to "Failed".

//Revision 1.24  2010/11/17 00:56:36  rick
//adding country attribute to static lookup table.

//Revision 1.23  2010/11/17 00:21:32  rick
//adding country filtering for IMG,MM and FB on
//LSEOBUNDLE.

//Revision 1.22  2010/11/10 09:07:47  guobin
//Handle as Periodic IDL ABR

//Revision 1.21  2010/11/08 15:34:20  guobin
//Check FlagStatus before runing Sysfeedresend.

//Revision 1.20  2010/11/01 07:53:39  guobin
//Adjust T1 add 1 sec when getting Status of Queue time. This is to reslove the delay of Setting Status value

//Revision 1.19  2010/10/26 19:30:53  rick
//adding CATNAV ABR class to table.

//Revision 1.18  2010/10/15 13:11:11  guobin
//Adjust T1 and T2  by adding 40 second to T1 and T2.

//Revision 1.17  2010/10/13 14:20:27  guobin
//add deactivateMultiFlagValue() in Final part of the excute_run.

//Revision 1.16  2010/10/09 02:23:52  rick
//multiple q for IDL support

//Revision 1.14  2010/09/29 15:07:46  guobin
//Add handle with it as an  IDL which queued by XMLIDLABRSTATUS

//Revision 1.13  2010/09/16 14:27:10  rick
//changing UNBUNDCOMP to REVUNBUNDCOMP.

//Revision 1.12  2010/08/19 08:27:24  yang
//change prodstruct and swprodstruct path  to package adsxmlbh1

//Revision 1.11  2010/08/19 08:25:02  yang
//adding  SWPRODSTRUCT and PRODSTRUCT

//Revision 1.10  2010/08/09 21:34:08  rick
//adding unbundcomp, fctransaction and modelconvert

//Revision 1.9  2010/08/05 20:29:18  rick
//adding UNBUNDCOMP XML for BH 1

//Revision 1.8  2010/08/03 16:50:39  rick
//Adding WARR.

//Revision 1.7  2010/07/29 14:39:35  rick
//adding country filtering for MM, IMG and FB for BH 1.0

//Revision 1.6  2010/06/24 21:33:30  rick
//fixing cvs logging.


//Revision 1.12  2010/01/07 17:58:36  wendy
//cvs failure again

//Revision 1.10  2009/12/08 12:18:54  wendy
//cvs failure - restore version and logging

//Revision 1.8  2009/08/18 19:31:44  wendy
//Allow extractaction to cleanup

//Revision 1.7  2008/05/28 13:51:25  wendy
//put mqcid into hashtable for mq usage

//Revision 1.6  2008/05/27 14:28:58  wendy
//Clean up RSA warnings

//Revision 1.5  2008/05/03 23:29:54  wendy
//Changed to support generation of large XML files

//Revision 1.4  2008/05/01 18:10:16  wendy
//Fix error msgs

//Revision 1.3  2008/05/01 13:58:38  wendy
//output current and prior status in systemresend

//Revision 1.2  2008/05/01 12:12:14  wendy
//Allow access to convertToHTML() from derived class

//Revision 1.1  2008/04/29 14:34:18  wendy
//Init for
//-   CQ00003539-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC
//-   CQ00005096-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Add Category MM and Images
//-   CQ00005046-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Support CRAD in BHC
//-   CQ00005045-WI -  BHC 3.0 Support - Feed of ZIPSRSS product info to BHC - Upgrade/Conversion Support
//-   CQ00006862-WI  - BHC 3.0 Support - Support for Services Data UI

public class ADSABRSTATUS extends PokBaseABR
{
    private StringBuffer rptSb = new StringBuffer();
    private static final char[] FOOL_JTEST = {'\n'};
    static final String NEWLINE = new String(FOOL_JTEST);

    // RQK - this will be used for non-idl case, until SDM filtering is done. Then won't be needed.
    protected final static String ADSMQSERIES = "ADSMQSERIES";
    protected static final String SYSFEEDRESEND_YES = "Yes";
    protected static final String SYSFEEDRESEND_NO = "No";
    protected static final String SYSFEEDRESEND_RFR = "RFR";
    // Add Cache and Current SYSFEEDRESEND
    protected static final String SYSFEEDRESEND_CACHE = "CACHE";
    protected static final String SYSFEEDRESEND_CURRENT = "CUR"; //current    
    
    protected static final String MQUEUE_ATTR = "XMLABRPROPFILE";
    protected static final String ADSATTRIBUTE = "ADSATTRIBUTE";
    protected static final String OLDEFFECTDATE = "2010-03-01";
    private StringBuffer xmlgenSb = new StringBuffer();
    private StringBuffer reason = new StringBuffer();
    private PrintWriter dbgPw=null;
    private PrintWriter userxmlPw=null;
    private String dbgfn = null;
    private String userxmlfn = null;
    private int userxmlLen=0;
    private int dbgLen=0;
    public static final int MAXFILE_SIZE=5000000;

    protected static final String STATUS_DRAFT = "0010";
    protected static final String STATUS_FINAL = "0020";
    protected static final String STATUS_R4REVIEW = "0040";
    protected static final String STATUS_CHGREQ = "0050";
    // ADSABRSTATUS Queued = 0020, Passed = 0030
    protected static final String STATUS_QUEUE = "0020";
    protected static final String STATUS_PASSED = "0030";
    protected static final String STATUS_PASSEDRESENDRFR = "XMLRFR";
    // add new status XMLCACHE for the Passed Resend Cache
    protected static final String STATUS_PASSEDRESENDCACHE = "XMLCACHE";
    protected static final String CHEAT = "@@";
	 /**
     *  If there is a data problem, then the EntityType Long Description 
     *  and the Navigation Display Attributes should be used to identify the data along with a message indicating the problem.  
     */
    private Hashtable metaTbl = new Hashtable();
	private static final Hashtable NDN_TBL;
	static{
		NDN_TBL = new Hashtable();
		NDN ndnMdl = new NDN("MODEL", "(TM)");
		ndnMdl.addAttr("MACHTYPEATR");
		ndnMdl.addAttr("MODELATR");
		ndnMdl.addAttr("COFCAT");
		ndnMdl.addAttr("COFSUBCAT");
		ndnMdl.addAttr("COFGRP");
		ndnMdl.addAttr("COFSUBGRP");
		NDN ndnFc = new NDN("FEATURE", "(FC)");
		ndnFc.addAttr("FEATURECODE");
		ndnMdl.setNext(ndnFc);
		NDN_TBL.put("PRODSTRUCT",ndnMdl);
		
		ndnMdl = new NDN("MODEL", "(TM)");
		ndnMdl.addAttr("MACHTYPEATR");
		ndnMdl.addAttr("MODELATR");
		ndnMdl.addAttr("COFCAT");
		ndnMdl.addAttr("COFSUBCAT");
		ndnMdl.addAttr("COFGRP");
		ndnMdl.addAttr("COFSUBGRP");
		ndnFc = new NDN("SWFEATURE", "(FC)");
		ndnFc.addAttr("FEATURECODE");
		ndnMdl.setNext(ndnFc);
		NDN_TBL.put("SWPRODSTRUCT",ndnMdl);
	}

    // VE_Filter_Array contains the filtering which is applied.
    // The first column contains the VE name.
    // The second column contains the entity subject to filtering.
    // The third column contains the filtering instruction when the root entity is FINAL.
    // IF 'RFR Final' then RFR AND FINAL are allowed (ie - NOT filtered).
    // If 'Final' then only FINAL is allowed. 
    protected static final String[][] VE_Filter_Array = 
    {
    	//configure according to the doc system feed 20111020.doc wooksheet XML Gen VE 20111020.xls
        {"ADSLSEO","AVAIL","RFR Final"},
        {"ADSLSEO","ANNOUNCEMENT","RFR Final"},
        {"ADSLSEO","FB","RFR Final"},
        //{"ADSLSEO","IMG","Final"},
        {"ADSLSEO","MM","RFR Final"},
        //2013-11-05 add BHCATLGOR filter only final 
        {"ADSLSEO","BHCATLGOR","Final"}, 
        //Add ADSLSEO2 filter for AVAILElem of LSEO, if there is no Plan AVAIL, then find MODLE related AVAIL
        {"ADSLSEO2","AVAIL","RFR Final"},
        {"ADSLSEO2","ANNOUNCEMENT","RFR Final"},
        {"ADSLSEOBUNDLE","AVAIL","RFR Final"},
        {"ADSLSEOBUNDLE","ANNOUNCEMENT","RFR Final"},
        {"ADSLSEOBUNDLE","FB","RFR Final"},
        //{"ADSLSEOBUNDLE","IMG","Final"},
        {"ADSLSEOBUNDLE","MM","RFR Final"},
        //{"ADSLSEOBUNDLE","LSEO","Final"},
        //2013-11-05 add BHCATLGOR filter only final 
        {"ADSLSEOBUNDLE","BHCATLGOR","Final"}, 
        {"ADSMODEL","AVAIL","RFR Final"},
        {"ADSMODEL","ANNOUNCEMENT","RFR Final"},
        {"ADSMODEL","FB","RFR Final"},
        //{"ADSMODEL","IMG","Final"},
        {"ADSMODEL","MM","RFR Final"},
        //2013-11-05 add BHCATLGOR filter only final 
        {"ADSMODEL","BHCATLGOR","Final"}, 
        {"ADSPRODSTRUCT","AVAIL","RFR Final"},
        // Change 20110322 XML Gen VE 20110322.xls to not filter FEATURE FOR TMF XML (ADSPRODSTRUCT VE)
        //{"ADSPRODSTRUCT","FEATURE","Final"},
        {"ADSPRODSTRUCT","ANNOUNCEMENT","RFR Final"},
        //2013-11-05 add BHCATLGOR filter only final 
        {"ADSPRODSTRUCT","BHCATLGOR","Final"}, 
        {"ADSSWPRODSTRUCT","AVAIL","RFR Final"},
        // Change 20110322 XML Gen VE 20110322.xls to not filter SWFEATURE FOR TMF XML (ADSSWPRODSTRUCT VE)
        //{"ADSSWPRODSTRUCT","SWFEATURE","Final"},
        {"ADSSWPRODSTRUCT","ANNOUNCEMENT","RFR Final"},
        //2013-11-05 add BHCATLGOR filter only final 
        {"ADSSWPRODSTRUCT","BHCATLGOR","Final"}, 
        {"ADSSVCMOD","AVAIL","RFR Final"},
        {"ADSSVCMOD","ANNOUNCEMENT","RFR Final"},
        // Change 20111020 XML Gen VE 20111020.xls to not filter IMG FOR  XML (ADSCATNAV VE)
        //{"ADSCATNAV","IMG","Final"},
        //ADSMODELCONVERT
        {"ADSMODELCONVERT","AVAIL","RFR Final"},
        {"ADSMODELCONVERT","ANNOUNCEMENT","RFR Final"},
        
        // Pending wait for wayne's response
        {"ADSPRODSTRUCT2","AVAIL","RFR Final"},
        // Change 20110322 XML Gen VE 20110322.xls to not filter FEATURE FOR TMF XML (ADSPRODSTRUCT VE)
        //{"ADSPRODSTRUCT","FEATURE","Final"},
        {"ADSPRODSTRUCT2","ANNOUNCEMENT","RFR Final"},
        {"ADSSWPRODSTRUCT2","AVAIL","RFR Final"},
        // Change 20110322 XML Gen VE 20110322.xls to not filter SWFEATURE FOR TMF XML (ADSSWPRODSTRUCT VE)
        //{"ADSSWPRODSTRUCT","SWFEATURE","Final"},
        {"ADSSWPRODSTRUCT2","ANNOUNCEMENT","RFR Final"},
        //2013-11-05 add CATNAV filter RFR Final 
        {"ADSCATNAV","IMG","RFR Final"},
    };

    protected static final String[][] VE_Country_Filter_Array = 
    {
        {"ADSLSEO","FB"},
        {"ADSLSEO","IMG"},
        {"ADSLSEO","MM"},
        {"ADSLSEOBUNDLE","FB"},
        {"ADSLSEOBUNDLE","IMG"},
        {"ADSLSEOBUNDLE","MM"}
    };
    //BH FS ABR XML IDL 20110421.doc IX IDL
    private static final Hashtable FILTER_TBL; 
    static{
		FILTER_TBL = new Hashtable();
		FILTER_TBL.put("CATNAV", new String[]{"","FLFILSYSINDC",""});
		FILTER_TBL.put("FCTRANSACTION", new String[]{"","","WTHDRWEFFCTVDATE"});
		FILTER_TBL.put("FEATURE", new String[]{"","","WITHDRAWDATEEFF_T"});
		FILTER_TBL.put("LSEO", new String[]{"COFCAT","FLFILSYSINDC","LSEOUNPUBDATEMTRGT"});
		FILTER_TBL.put("LSEOBUNDLE", new String[]{"BUNDLETYPE","FLFILSYSINDC","BUNDLUNPUBDATEMTRGT"});
		FILTER_TBL.put("MODEL", new String[]{"COFCAT","FLFILSYSINDC","WTHDRWEFFCTVDATE"});
		FILTER_TBL.put("MODELCONVERT", new String[]{"","","WTHDRWEFFCTVDATE"});
		FILTER_TBL.put("PRODSTRUCT", new String[]{"","FLFILSYSINDC","WTHDRWEFFCTVDATE"});
		FILTER_TBL.put("SVCMOD", new String[]{"SVCMODGRP","","WTHDRWEFFCTVDATE"});
		FILTER_TBL.put("SWFEATURE", new String[]{"","","WITHDRAWDATEEFF_T"});
		FILTER_TBL.put("SWPRODSTRUCT", new String[]{"","","WTHDRWEFFCTVDATE"});	
		FILTER_TBL.put("REFOFER", new String[]{"","","ENDOFSVC"});
		FILTER_TBL.put("REFOFERFEAT", new String[]{"","","ENDOFSVC"});
	}
    private ResourceBundle rsBundle = null;
    private String priorStatus = "&nbsp;";
    private String curStatus = "&nbsp;";
    private String curStatusvalue = null;
    private boolean isPeriodicABR = false;
    private boolean isSystemResend = false;
    private boolean isSystemResendRFR = false;
    //add Cache and Current SYSFEEDRESEND
    private boolean isSystemResendCache = false;
    private boolean isSystemResendCurrent = false;
    private boolean isSystemResendCacheExist = false;
    private String SystemResendCacheXml = "";
    private boolean isXMLIDLABR = false;
    private boolean isXMLCACHE = false;
    private boolean isXMLADSABR = false;
    private boolean isIERPADSABR = false;
    // when Status goes to RFR, but it has been Final before,it is True; Status never been Final, it is False. 
    //private boolean RFRPassedFinal = false;
    private String actionTaken="";

    protected static final Hashtable READ_LANGS_TBL;  // tbl of NLSitems, 1 for each profile language
    private static final Hashtable ABR_TBL;
    protected static final Hashtable ADSTYPES_TBL;
    // ITEM_STATUS_ATTR_TBL contains the status attribute codes for entities which are 
    // subject to filtering
    protected static final Hashtable ITEM_STATUS_ATTR_TBL;
    // ITEM_COUNTRY_ATTR_TBL contains the country attribute codes for entities which are 
    // subject to filtering
    protected static final Hashtable ITEM_COUNTRY_ATTR_TBL;

    // DQ abr table copy from DQABRSTATUS
    protected static final Hashtable ABR_ATTR_TBL;

    protected static final String SYSFeedResendValue = "_SYSFeedResendValue";
    //ADSABRSTATUS__debugLevel = 4
    public static int DEBUG_LVL = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel("ADSABRSTATUS");
    public static int MAX_LVL =  D.EBUG_SPEW;
    /**
	 * add ADSABRSTATUS_USERXML so We can turn off creation of the .userxml file by property file
	 * ON means turn on creation of the .userxml file, OFF meas turn off the creation of the .userxml file 
	 */
	private static String ADSABRSTATUS_USERXML_ADSXMLSETUP = 
		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_ADSXMLSETUP","ON");
	private static String ADSABRSTATUS_USERXML_XMLPRODPRICESETUP = 
		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLPRODPRICESETUP","OFF");
	private static String ADSABRSTATUS_USERXML_XMLCOMPATSETUP = 
		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLCOMPATSETUP","OFF");
	private static String ADSABRSTATUS_USERXML_XMLXLATESETUP = 
		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLXLATESETUP","ON");
	
	protected static boolean USERXML_OFF_LOG = false;
	
    
    protected static final Hashtable ABR_VERSION_TBL;
    
    
    static{
        ABR_TBL = new Hashtable();
        ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSMODELABR");
        ABR_TBL.put("FEATURE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSFEATUREABR");
        ABR_TBL.put("SWFEATURE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSWFEATUREABR");
        ABR_TBL.put("WARR", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSWARRABR");
        ABR_TBL.put("REVUNBUNDCOMP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSUNBUNDCOMPABR");
//      ABR_TBL.put("SVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSSVCFEATUREABR");
        ABR_TBL.put("FCTRANSACTION", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSFCTRANSABR");
        ABR_TBL.put("MODELCONVERT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSMODELCONVERTABR");
        ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSPRODSTRUCTABR");
        ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSWPRODSTRUCTABR");
//      ABR_TBL.put("SVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSSVCPRODSTRUCTABR");
        ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSLSEOABR");
        ABR_TBL.put("SVCMOD", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSVCMODABR");
        ABR_TBL.put("SLEORGNPLNTCODE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSLEORGNPLNTCODEABR");
        ABR_TBL.put("GBT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSGBTABR");
        //add the IMG
        ABR_TBL.put("IMG", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSIMGABR");
        ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSLSEOBUNDLEABR");
        ABR_TBL.put("CATNAV", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSCATNAVABR");
        //ABR_TBL.put("XLATE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSXLATEABR");
        ABR_TBL.put("SVCLEV", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSVCLEVABR");
        //ABR_TBL.put("WWCOMPAT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSWWCOMPATXMLABR"); 
        ABR_TBL.put("GENERALAREA", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSGENAREAABR");        

        //add for price abr
        ABR_TBL.put("XMLPRODPRICESETUP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSPRICEABR");
        ABR_TBL.put("XMLCOMPATSETUP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSWWCOMPATXMLABR");
        ABR_TBL.put("XMLXLATESETUP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSXLATEABR");
        //add for REFOFER and REFOFERFEAT
        ABR_TBL.put("REFOFER", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSREFOFERABR");
        ABR_TBL.put("REFOFERFEAT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSREFOFERFEATABR");
        
        ABR_TBL.put("SWSPRODSTRUCT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSWSPRODSTRUCTABR");
        
        //ABR_TBL.put("SVCMODIERPABRSTATUSABR", "COM.ibm.eannounce.abr.sg.adsxmlbh1.SVCMODIERPABRSTATUSABR");
//      ABR_TBL.put("DELFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELFEATUREABR");
//      ABR_TBL.put("DELFCTRANSACTION", "COM.ibm.eannounce.abr.sg.ADSDELFCTRANSABR");
//      ABR_TBL.put("DELMODEL", "COM.ibm.eannounce.abr.sg.ADSDELMODELABR");
//      ABR_TBL.put("DELMODELCONVERT", "COM.ibm.eannounce.abr.sg.ADSDELMODELCONVERTABR");
//      ABR_TBL.put("DELPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELPRODSTRUCTABR");
//      ABR_TBL.put("DELSVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSVCFEATUREABR");
//      ABR_TBL.put("DELSVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSVCPRODSTRUCTABR");
//      ABR_TBL.put("DELSWFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSWFEATUREABR");
//      ABR_TBL.put("DELSWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSWPRODSTRUCTABR");

        READ_LANGS_TBL = new Hashtable();
        // fill in with all languages defined in profile, actual languages used is based on properties file
        READ_LANGS_TBL.put(""+Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
        READ_LANGS_TBL.put(""+Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);

        ADSTYPES_TBL = new Hashtable();
        ADSTYPES_TBL.put("10","CATNAV");
        ADSTYPES_TBL.put("100","SVCPRODSTRUCT");
        ADSTYPES_TBL.put("110","SWFEATURE");
        ADSTYPES_TBL.put("120","SWFPRODSTRUCT");
        ADSTYPES_TBL.put("130","MODELCG"); // MODELCG root for WWCOMPAT ve
        ADSTYPES_TBL.put("140","LSEO");
        ADSTYPES_TBL.put("150","SVCMOD");
        ADSTYPES_TBL.put("160","WARR");
        ADSTYPES_TBL.put("170","REVUNBUNDCOMP");
        ADSTYPES_TBL.put("180","SWSPRODSTRUCT");
        ADSTYPES_TBL.put("20","GENERALAREA");
        ADSTYPES_TBL.put("30","FEATURE");
        //remove xlate and wwcompat, use XMLCOMPATSETUP XMLXLATESETUP to process it
        //ADSTYPES_TBL.put("40","XLATE");
        //ADSTYPES_TBL.put("50","WWCOMPAT");  
        ADSTYPES_TBL.put("60","MODEL");
        ADSTYPES_TBL.put("70","MODELCONVERT");
        ADSTYPES_TBL.put("80","PRODSTRUCT");
        ADSTYPES_TBL.put("90","SVCFEATURE");
    }

    static {
        ITEM_STATUS_ATTR_TBL = new Hashtable();
        ITEM_STATUS_ATTR_TBL.put("AVAIL","STATUS");
        ITEM_STATUS_ATTR_TBL.put("ANNOUNCEMENT","ANNSTATUS");
        ITEM_STATUS_ATTR_TBL.put("IMG","STATUS");
        ITEM_STATUS_ATTR_TBL.put("MM","MMSTATUS");
        ITEM_STATUS_ATTR_TBL.put("FB","FBSTATUS");
        ITEM_STATUS_ATTR_TBL.put("FEATURE","STATUS");
        ITEM_STATUS_ATTR_TBL.put("SWFEATURE","STATUS");
        ITEM_STATUS_ATTR_TBL.put("LSEO","STATUS");
        ITEM_STATUS_ATTR_TBL.put("BHCATLGOR","STATUS");
    }

    static {
        ITEM_COUNTRY_ATTR_TBL = new Hashtable();
        ITEM_COUNTRY_ATTR_TBL.put("LSEO","COUNTRYLIST");
        ITEM_COUNTRY_ATTR_TBL.put("LSEOBUNDLE","COUNTRYLIST");
        ITEM_COUNTRY_ATTR_TBL.put("IMG","COUNTRYLIST");
        ITEM_COUNTRY_ATTR_TBL.put("MM","COUNTRYLIST");
        ITEM_COUNTRY_ATTR_TBL.put("FB","COUNTRYLIST");
    }

    static {
        ABR_ATTR_TBL = new Hashtable(); // needed when looking up values to queue on other entities
        ABR_ATTR_TBL.put("ANNOUNCEMENT", "ANNABRSTATUS");
        ABR_ATTR_TBL.put("AVAIL", "AVAILABRSTATUS");
        ABR_ATTR_TBL.put("FEATURE", "FCABRSTATUS");
        ABR_ATTR_TBL.put("FCTRANSACTION", "FCTRANSABRSTATUS");
        ABR_ATTR_TBL.put("LSEO", "LSEOABRSTATUS");
        ABR_ATTR_TBL.put("LSEOBUNDLE", "LSEOBDLABRSTATUS");
        ABR_ATTR_TBL.put("MODELCONVERT", "MDLCNTABRSTATTUS");
        ABR_ATTR_TBL.put("SWFEATURE", "SWFCABRSTATUS");
        ABR_ATTR_TBL.put("IPSCFEAT", "IPSCFEATABRSTATUS");
        ABR_ATTR_TBL.put("IPSCSTRUC", "IPSCSTRUCABRSTATUS");
        ABR_ATTR_TBL.put("SVCMOD", "SVCMODABRSTATUS");
        ABR_ATTR_TBL.put("WWSEO", "WWSEOABRSTATUS");
        ABR_ATTR_TBL.put("MODEL", "MODELABRSTATUS");
        ABR_ATTR_TBL.put("PRODSTRUCT", "PRODSTRUCTABRSTATUS");
        ABR_ATTR_TBL.put("SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
        ABR_ATTR_TBL.put("SWSPRODSTRUCT", "SWSPRODSTRUCTABRSTATUS");
    }
    
    static {
    	ABR_VERSION_TBL = new Hashtable();
    	ABR_VERSION_TBL.put("MODEL05", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSMODEL05ABR");
    	ABR_VERSION_TBL.put("LSEO05", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSLSEO05ABR");
    	ABR_VERSION_TBL.put("SVCMOD05", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSVCMOD05ABR");
    }
    
    private static final Hashtable PERIODIC_ABR_TBL;
	static {
		PERIODIC_ABR_TBL = new Hashtable();
		PERIODIC_ABR_TBL.put("ADSXMLSETUP","ADSABRSTATUS");			//ADS XML Setup(GA)
		PERIODIC_ABR_TBL.put("XMLPRODPRICESETUP","ADSPPABRSTATUS");	//ADS Product Price Setup
		PERIODIC_ABR_TBL.put("XMLCOMPATSETUP","ADSABRSTATUS");		//ADS Compatibility Setup
		PERIODIC_ABR_TBL.put("XMLXLATESETUP","ADSABRSTATUS");		//ADS Translated Values Setup
    }

    private Object[] args = new String[10];
    private String abrversion="";
    private String t2DTS = "&nbsp;";  // T2
    private String t1DTS = "&nbsp;";   // T1
//    private String xmlidlQueueDTS = "&nbsp;"; //the most recent value of 'Queue' of XMLIDLABRSTATUS
    private StringBuffer userxmlSb= new StringBuffer(); // output in the report, not value sent to MQ - diff transform used
    private Vector extxmlfeedVct = new Vector(); // for reconciliation xml report
    private Vector succQueueNameVct = new Vector();// for reconcilaition xml reprot
    /*
ADSTYPE 10  CATNAV
ADSTYPE 100 Service Product Structure
ADSTYPE 110 Software Feature
ADSTYPE 120 Software Product Structure
ADSTYPE 140     Lseo
ADSTYPE 150     Svcmod
ADSTYPE 20  GENAREA
ADSTYPE 30  Feature
ADSTYPE 40  flags translation
ADSTYPE 50  WWCOMPAT
ADSTYPE 60  Model
ADSTYPE 70  Model Conversion
ADSTYPE 80  Product Structure
ADSTYPE 90  Service Feature

ADSENTITY   10  CATNAV
ADSENTITY   20  Deletes
ADSENTITY   30  NA

ADSATTRIBUTE    10  NA
ADSATTRIBUTE    20  OSLEVEL
ADSATTRIBUTE    30  WARRPRIOD
ADSATTRIBUTE    40  WARRTYPE

     */

    protected String getSimpleABRName(String type)
    {
        // find class to instantiate based on entitytype
        // Load the specified ABR class in preparation for execution
    	
        String clsname = (String) ABR_TBL.get(type);
        addDebug("creating instance of ADSABR  = '" + clsname + "' for "+type);
        return clsname;
    }

    /**
     *  Execute ABR.
     *
     */
    public void execute_run()
    {
        String navName = "";
        // must split because too many arguments for messageformat, max of 10.. this was 11
        String HEADER = "<head>"+
        EACustom.getMetaTags(getDescription()) + NEWLINE +
        EACustom.getCSS() + NEWLINE +
        EACustom.getTitle("{0} {1}") + NEWLINE +
        "</head>" + NEWLINE + "<body id=\"ibm-com\">" +
        EACustom.getMastheadDiv() + NEWLINE +
        "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;

        MessageFormat msgf;

        println(EACustom.getDocTypeHtml()); //Output the doctype and html
        try
        {
        	String MQCID = "";
            long startTime = System.currentTimeMillis();

            start_ABRBuild(false); // dont pull VE yet

            //get properties file for the base class
            rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(m_prof.getReadLanguage().getNLSID()));

            //Default set to pass
            setReturnCode(PASS);
            
            //Default clear Vector
            extxmlfeedVct.clear();
            succQueueNameVct.clear();
            //Default set to false
            //RFRPassedFinal = false;

            //get the root entity using current timestamp, need this to get the timestamps or info for VE pulls
            m_elist = m_db.getEntityList(m_prof,
                    new ExtractActionItem(null, m_db, m_prof,"dummy"),
                    new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

            try{
                // get root from VE
                EntityItem rootEntity  = m_elist.getParentEntityGroup().getEntityItem(0);

                //isPeriodicABR = getEntityType().equals("ADSXMLSETUP")||getEntityType().equals("XMLPRODPRICESETUP");//add for price abr
                   

                String etype = getEntityType();     
                if(m_abri.getABRCode().equals("SVCMODIERPABRSTATUS")&&"SVCMOD".equals(etype)){
                	etype="SVCMODIERPABRSTATUS";
               }
                isPeriodicABR = PERIODIC_ABR_TBL.containsKey(etype);          
                if(isPeriodicABR){
                	//all the PeriodicABR need turn on or off the userxml                	
                	if("ADSXMLSETUP".equals(etype)){
                		if("OFF".equals(ADSABRSTATUS_USERXML_ADSXMLSETUP)){
                			USERXML_OFF_LOG =true;
                		}else{
                			USERXML_OFF_LOG =false;
                		}
                	}else if("XMLPRODPRICESETUP".equals(etype)){
                		if("OFF".equals(ADSABRSTATUS_USERXML_XMLPRODPRICESETUP)){
                			USERXML_OFF_LOG =true;
                		}else{
                			USERXML_OFF_LOG =false;
                		}
                	} else if ("XMLCOMPATSETUP".equals(etype)){
                		if("OFF".equals(ADSABRSTATUS_USERXML_XMLCOMPATSETUP)){
                			USERXML_OFF_LOG =true;
                		}else{
                			USERXML_OFF_LOG =false;
                		}
                	} else if("XMLXLATESETUP".equals(etype)){
                		if("OFF".equals(ADSABRSTATUS_USERXML_XMLXLATESETUP)){
                			USERXML_OFF_LOG =true;
                		}else{
                			USERXML_OFF_LOG =false;
                		}
                	}
                }else{
                	USERXML_OFF_LOG =false;
                }
                //int eid = getEntityID();
                String ADSTYPE = PokUtils.getAttributeFlagValue(rootEntity, "ADSTYPE");
                String ADSENTITY = PokUtils.getAttributeFlagValue(rootEntity, "ADSENTITY");

                if (isPeriodicABR){
                    // look at ADSTYPE, ADSENTITY
                    if (ADSTYPE != null){
                        etype = (String)ADSTYPES_TBL.get(ADSTYPE);
                    }
                    if ("20".equals(ADSENTITY)){ // this is deletes
                        etype = "DEL"+etype;
                    }
                }

                // find class to instantiate based on entitytype
                // Load the specified ABR class in preparation for execution
             
                String clsname = getSimpleABRName(etype);
                if (clsname!=null){
                    boolean shouldExecute = true;
                    XMLMQ mqAbr = (XMLMQ) Class.forName(clsname).newInstance();
                    MQCID = mqAbr.getMQCID();
                    abrversion = getShortClassName(mqAbr.getClass())+" "+mqAbr.getVersion();
//                  RQK check to see if MQ propfile name exists on root entity
                    // if it exists then set isXMLIDLABR true
                    // else set it to false
                    // call new checkIDLMQPropertiesFN()
                    //If XMLIDLABRSTATUS was set to 'Queue' and attr XMLABRPROPFILE was set to null. then ran IDL Cache, do not put to MQ.
                    //isXMLIDLABR = mqAbr.checkIDLMQPropertiesFN(rootEntity);
                    if (m_abri.getABRCode().equals("XMLIDLABRSTATUS")){
                    	isXMLIDLABR = true;
                    	isXMLCACHE = true;
                    	String sysfeedFlag = getAttributeFlagEnabledValue(rootEntity, "SYSFEEDRESEND");
                    	
                    	isSystemResendCurrent = SYSFEEDRESEND_CURRENT.equals(sysfeedFlag);                    	
                    	addDebugComment(D.EBUG_INFO, "isSystemResendCurrent=" + isSystemResendCurrent +";sysfeedFlag=" + sysfeedFlag );
                    } else if(m_abri.getABRCode().equals("ADSABRSTATUS")){
                    	isXMLADSABR = true;                    	
                    } 
                    if (!isPeriodicABR){
                        String statusAttr = mqAbr.getStatusAttr();
                        String sysfeedFlag = getAttributeFlagEnabledValue(rootEntity, "SYSFEEDRESEND");
                        String statusFlag = getAttributeFlagEnabledValue(rootEntity, statusAttr);
                        isSystemResend = SYSFEEDRESEND_YES.equals(sysfeedFlag);
                        isSystemResendRFR = SYSFEEDRESEND_RFR.equals(sysfeedFlag);
                        //add Cache and Current SYSFEEDRESEND 
                        isSystemResendCache = SYSFEEDRESEND_CACHE.equals(sysfeedFlag);
                        isSystemResendCurrent = SYSFEEDRESEND_CURRENT.equals(sysfeedFlag);
                        
                        addDebugComment(D.EBUG_INFO, "execute: "+rootEntity.getKey()+" "+statusAttr+": "+
                                PokUtils.getAttributeValue(rootEntity, statusAttr,", ", "", false)+" ["+statusFlag+"] sysfeedFlag: "+
                                sysfeedFlag + "; is XMLIDLABR: " + isXMLIDLABR + "; is isXMLCACHE:" + isXMLCACHE);
                        
                        addDebugComment(D.EBUG_INFO, "isSystemResend: "+isSystemResend
                        		+ "; isSystemResendRFR: " + isSystemResendRFR
                        		+ "; isSystemResendCache: " + isSystemResendCache);
                        if (isSystemResendRFR){
                        	processSystemResendRFR(rootEntity, statusAttr, statusFlag);
                        } else if (isSystemResend){
                            processSystemResend(rootEntity, mqAbr, statusAttr, statusFlag);
                        }else if(isSystemResendCache){
                        	/**
                        	 BH FS ABR XML System Feed 20130904.doc Page 51
                        	 IF the value of 閳ユ穾DS XML Feed ABR閳ワ拷 (ADSABRSTATUS) is set to 閳ユ法ueued閳ワ拷 (0020) and 閳ユ藩ystem Feed Resend閳ワ拷 (SYSFEEDRESEND) = 閳ユ珐esend Cache閳ワ拷 (CACHE) THEN
								IF the XML message is NOT available in CACHE THEN
									This is the same as the preceding section on 閳ユ阀roduction (Triggered)閳ワ拷 when sending 閳ユ窋ull閳ワ拷 XML. It never sends 閳ユ窉elta閳ワ拷 XML.
								ELSE
									Use the XML found in Cache and send it to downstream systems.
									Do NOT update cache
									Set ADSABRSTATUS = 閳ユ阀assed Resend Cache閳ワ拷 (XMLCACHE)
								END IF							
							End of Section E.	Resend Cache
                        	 */
                        	isSystemResendCacheExist = checkSystemResendCache(rootEntity);                    	    
                    	    if(isSystemResendCacheExist){
                    	    	setReturnCode(RESENDCACHE);
                    	    }else{
                    	    	//the same as the preceding section on 閳ユ阀roduction (Triggered)閳ワ拷 when sending 閳ユ窋ull閳ワ拷 XML. It never sends 閳ユ窉elta閳ワ拷 XML.                   	    	
                    	    }                        	
                        } else if (!isXMLIDLABR){
                            // running in ADSABR
                            if (!STATUS_FINAL.equals(statusFlag)&& !STATUS_R4REVIEW.equals(statusFlag)){
                                addDebug(rootEntity.getKey()+" is not Final or R4R");
                                //NOT_R4RFINAL = Status is not Ready for Review or Final.
                                addError(rsBundle.getString("NOT_R4RFINAL"), rootEntity);
                            }else{
                                shouldExecute = mqAbr.createXML(rootEntity);
                                if (!shouldExecute){
                                    addDebug(rootEntity.getKey()+" will not have XML generated, createXML=false");
                                }
                            }
                        }
                    }else{
                        addDebug("execute: periodic "+rootEntity.getKey());
                    }
                    //get ADSABRSTATUS changed history group
                    AttributeChangeHistoryGroup adsStatusHistoy = null;
                    AttributeChangeHistoryGroup xmlIDLStatusHistoy = null;
                    // For the IDL case ADSABRSTATUS may have never been set
                    if (!isXMLIDLABR){
                    	//change the get T1 & T2 time for the isPeriodicABR to avoid OOM 
                    	if(isPeriodicABR){
                    		adsStatusHistoy = null;
                    		//if(getEntityType().equals("XMLPRODPRICESETUP")){
                    		//adsStatusHistoy = getADSABRSTATUSHistory("ADSPPABRSTATUS");
                    	}else{
                    		adsStatusHistoy = getADSABRSTATUSHistory("ADSABRSTATUS");
                    	}                    	
                    }else{
                    	xmlIDLStatusHistoy = getADSABRSTATUSHistory("XMLIDLABRSTATUS"); 
                    	adsStatusHistoy = getADSABRSTATUSHistory("ADSABRSTATUS"); 
                    }   
                    //get STATUS changed history group
                    AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(mqAbr);
                    //get &DTFS from properties file                    
                    String dtfs = getDTFS(rootEntity, mqAbr);
                    
                    //TODO BH FS ABR XML System Feed 20130904.doc change the T1 & T2
                    setT2DTS(rootEntity, mqAbr, adsStatusHistoy, statusHistory, dtfs, xmlIDLStatusHistoy); // T2 timestamp
                    setT1DTS(mqAbr, adsStatusHistoy, statusHistory, dtfs); // T1 timestamp

                    if ((getReturnCode()==PASS || getReturnCode()==RESENDRFR || getReturnCode()==RESENDCACHE) && shouldExecute){

                        // switch the role and use the time2 DTS (current change)
                        Profile profileT2 = switchRole(mqAbr.getRoleCode());
                        if (profileT2!=null){
                        	addDebug(rootEntity.getKey()+ " T1=" + t1DTS + ";T2=" + t2DTS);
                            profileT2.setValOnEffOn(t2DTS, t2DTS);
                            profileT2.setEndOfDay(t2DTS); 
                            profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
                            profileT2.setLoginTime(t2DTS); // used for notification time

                            Profile profileT1 = profileT2.getNewInstance(m_db);
                            profileT1.setValOnEffOn(t1DTS, t1DTS);
                            profileT1.setEndOfDay(t2DTS); 
                            profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
                            profileT1.setLoginTime(t2DTS);// used for notification time

                            String errmsg = "";
                            try{
                                if (isPeriodicABR){
                                    // look at ADSTYPE, ADSENTITY and ADSATTRIBUTE
                                    String typeToChk = "";
                                    if (ADSTYPE != null){
                                        typeToChk = (String)ADSTYPES_TBL.get(ADSTYPE);
                                    }
                                    errmsg = "Periodic "+typeToChk;
                                    if ("20".equals(ADSENTITY)){ // this is deletes
                                        errmsg = "Deleted "+typeToChk;
                                    }
                                    setupPrintWriters();
                                    mqAbr.processThis(this, profileT1, profileT2, rootEntity);
                                }else{ // queued from DQ ABRs
                                    errmsg = rootEntity.getKey();
                                    // check if pdhdomain is in domain list for this entity
                                    if (domainNeedsChecks(rootEntity)){                                        
                                        mqAbr.processThis(this, profileT1, profileT2, rootEntity);                                       
                                    }else{
                                        //DOMAIN_NOT_LISTED = Domain was not in the list of supported Domains. Execution bypassed for {0}.
                                        addXMLGenMsg("DOMAIN_NOT_LISTED",errmsg);
                                    }
                                }
                            }catch(IOException ioe){
                                // only get this if a required node was not populated
                                //REQ_ERROR = Error: {0}
                                msgf = new MessageFormat(rsBundle.getString("REQ_ERROR"));
                                args[0] = ioe.getMessage();
                                addMSGLOGReason(ioe.getMessage());
                                addError(msgf.format(args));
                                addXMLGenMsg("FAILED", errmsg);
                            }catch(java.sql.SQLException x){
                                addXMLGenMsg("FAILED", errmsg);
                                throw x;
                            }catch(COM.ibm.opicmpdh.middleware.MiddlewareRequestException x){
                                addXMLGenMsg("FAILED", errmsg);
                                throw x;
                            }catch(COM.ibm.opicmpdh.middleware.MiddlewareException x){
                                addXMLGenMsg("FAILED", errmsg);
                                throw x;
                            }catch(ParserConfigurationException x){
                                addXMLGenMsg("FAILED", errmsg);
                                throw x;
                            }catch(javax.xml.transform.TransformerException x){
                                addXMLGenMsg("FAILED", errmsg);
                                throw x;
                            }catch(java.util.MissingResourceException x){
                                addXMLGenMsg("FAILED", errmsg);
                                throw x;
                            }
                        }
                    }
                }else if("SVCMODIERPABRSTATUS".equals(etype)){
                	//ABR_TBL.put("SVCMODIERPABRSTATUSABR", "COM.ibm.eannounce.abr.sg.adsxmlbh1.SVCMODIERPABRSTATUSABR");
                	 XMLMQ mqAbr = (XMLMQ) Class.forName( "COM.ibm.eannounce.abr.sg.adsxmlbh1.SVCMODIERPABRSTATUSABR").newInstance();
                     MQCID = mqAbr.getMQCID();
                     abrversion = getShortClassName(mqAbr.getClass())+" "+mqAbr.getVersion();
                     Profile profileT2 = switchRole(mqAbr.getRoleCode());
                     Profile profileT1 = null;
                     if (profileT2!=null){
                     	addDebug(rootEntity.getKey()+ " T1=" + t1DTS + ";T2=" + t2DTS);
                         profileT2.setValOnEffOn(t2DTS, t2DTS);
                         profileT2.setEndOfDay(t2DTS); 
                         profileT2.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
                         profileT2.setLoginTime(t2DTS); // used for notification time

                          profileT1 = profileT2.getNewInstance(m_db);
                         profileT1.setValOnEffOn(t1DTS, t1DTS);
                         profileT1.setEndOfDay(t2DTS); 
                         profileT1.setReadLanguage(Profile.ENGLISH_LANGUAGE); // default to US english
                         profileT1.setLoginTime(t2DTS);// used for notification time
                     }
                     mqAbr.processThis(this, profileT1, profileT2, rootEntity);
                }else{
                    addError(getShortClassName(getClass())+" does not support "+etype);
                }
                //NAME is navigate attributes
                navName = getNavigationName(rootEntity);

//              fixme remove
//              setCreateDGEntity(false);

                // update lastran date
             
                if (isPeriodicABR && !isXMLIDLABR && getReturnCode()==PASS) {
                    PDGUtility pdgUtility = new PDGUtility();
                    OPICMList attList = new OPICMList();
                    attList.put("ADSDTS","ADSDTS=" + t2DTS);
                    addDebug("update lastran date:"+t2DTS);
                    pdgUtility.updateAttribute(m_db, m_prof, rootEntity, attList);
                }
                //need to update ADSABRSTATUS return code

                addDebug("Total Time: "+Stopwatch.format(System.currentTimeMillis()-startTime));
            }catch(Exception e){
            	addMSGLOGReason(e.getMessage());
            	addError(e.getMessage());               
                throw e;
            }finally{
                if (isSystemResend || isSystemResendRFR || isSystemResendCache || isSystemResendCurrent ){
                    setFlagValue("SYSFEEDRESEND", SYSFEEDRESEND_NO); // reset the flag
                }
                if (isXMLIDLABR){
                    deactivateMultiFlagValue("XMLABRPROPFILE");
                }
                if(!isIERPADSABR&&"SVCMOD".equals(getEntityType())&&getReturnCode()==PASS&&!m_abri.getABRCode().equals("SVCMODIERPABRSTATUS")){
                	setFlagValue("SVCMODIERPABRSTATUS", "0020");
                }
                if (t2DTS.equals("&nbsp;")){
                	t2DTS= getNow();
                }
                addDebug("isPeriodicABR:"+isPeriodicABR);
                if (isPeriodicABR){
                	EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
                	String ADSTYPE = PokUtils.getAttributeFlagValue(rootEntity, "ADSTYPE");
                	//Entitytype = XMLXLATESETUP.ADSATTRIBUTE if running Translated Values setup 
                	//String etype = getShortDescription(rootEntity, ADSATTRIBUTE,"short");
                	
                	String setupDTS = getNow();
//                	SETUPDTS = valfrom of setup Entity instance in Entity table
                	/*
        			EntityChangeHistoryGroup historygrop= rootEntity.getThisChangeHistoryGroup(m_db);
        			if (historygrop != null){
        				int ii = historygrop.getChangeHistoryItemCount();
            			addDebug(rootEntity.getKey() + " getChangeHistoryItemCount: " + ii);
            			if (ii > 0){
            				//change setupDTS
            				//rootEntity.
            			    setupDTS = historygrop.getChangeHistoryItem(ii-1).getChangeDate();
            				addDebug("setupDTS : " + setupDTS);
            			}
            			
        			} else{
        				addDebug(rootEntity.getKey() + "can not find EntitychangeHistoryGroup! SETUPDTS is getnow().");
        			}	
        			*/
                	String setupDTSTemp = getEntityLastValfrom(rootEntity);
                	if(setupDTSTemp != null) {
                		setupDTS = setupDTSTemp;
                		addDebug("setupDTS : " + setupDTS);
                	} else {
                		addDebug(rootEntity.getKey() + "can not find last valfrom of the entity! SETUPDTS is getnow().");
                	}
                	
                	String etype = "";
                	if (ADSTYPE == null){
                		if ("XMLXLATESETUP".equals(rootEntity.getEntityType())){
                			etype = getDescription(rootEntity, ADSATTRIBUTE,"short");
                		} else if ("XMLCOMPATSETUP".equals(rootEntity.getEntityType())){
                			etype =  "WWCOMPAT";
                		} else if ("XMLPRODPRICESETUP".equals(rootEntity.getEntityType())){
                			etype = getDescription(rootEntity, "ADSOFFTYPE","long");
                		}	           		
                	}else {
                	    etype = (String)ADSTYPES_TBL.get(ADSTYPE);
                	}
                	
                	ArrayList MSGLOGList  = new ArrayList();
                	String msgreason = reason.toString().length()<64?reason.toString():reason.toString().substring(0,63);
            		String MQPropFile = PokUtils.getAttributeFlagValue(rootEntity, MQUEUE_ATTR);
            		if (MQPropFile == null){
            			MQPropFile = "";
            		}
	                // Changes according to BH FS ABR System Feed20120810.doc Reconciliation XML Message Support.doc Name of MQ Properties File for downstream receiver. There should be a single value per row. There should be an instance (row) for each MQPROPFILE that resulted in the message being sent.
	       			// Change according to BH FS ABR Reconciliation Reports 20111130.doc page 8. SETUPDTS = VALFROM of Setup Entity instance in Enity table 
            		
            		StringTokenizer stString = new StringTokenizer(MQPropFile, "|" , false);
	       			while (stString.hasMoreElements()) {
	       				String sigleMQPropfile = stString.nextToken();
	       				MSGLOGList.add(new String[]{null,getNow(),MQCID,t2DTS, (succQueueNameVct.contains(sigleMQPropfile)?"S":"F"), succQueueNameVct.contains(sigleMQPropfile)?"":msgreason});
	       			    inertMSGLOG(MSGLOGList,rootEntity.getEntityType(),rootEntity.getEntityID(), setupDTS,etype,sigleMQPropfile);               		
	       			    MSGLOGList.clear();
	       			}
            		
            		
            		
                } else if (!isXMLIDLABR){              	
                	if (extxmlfeedVct != null && extxmlfeedVct.size()>0){
                		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
                		ArrayList MSGLOGList  = new ArrayList();
                		String msgreason = reason.toString().length()<64?reason.toString():reason.toString().substring(0,63);
                		String setupDTS = getNow();
                		for (int i=0; i<extxmlfeedVct.size();i++){
                			EntityItem xmlfeedEntity = (EntityItem)extxmlfeedVct.elementAt(i);
                			//SETUPDTS = valfrom of setup Entity instance in Entity table
                			/*
                			EntityChangeHistoryGroup historygrop= xmlfeedEntity.getThisChangeHistoryGroup(m_db);
                			if (historygrop != null){
                				int ii = historygrop.getChangeHistoryItemCount();
                    			addDebug("getChangeHistoryItemCount: " + ii);
                    			if (ii > 0){
                    			    setupDTS = historygrop.getChangeHistoryItem(ii-1).getChangeDate();
                    				addDebug("setupDTS : " + setupDTS);
                    			}
                    			
                			}else{
                				addDebug("Can not find EntitychangeHistoryGroup! SETUPDTS is getnow().");
                			}
                			*/
                			String setupDTSTemp = getEntityLastValfrom(xmlfeedEntity);
                        	if(setupDTSTemp != null) {
                        		setupDTS = setupDTSTemp;
                        		addDebug("setupDTS : " + setupDTS);
                        	} else {
                        		addDebug(rootEntity.getKey() + "can not find last valfrom of the entity! SETUPDTS is getnow().");
                        	}
                			
                			//need to check above code setupDTS value
                			String MQPropFile = PokUtils.getAttributeFlagValue(xmlfeedEntity, MQUEUE_ATTR);
                			if (MQPropFile == null){
                				MQPropFile = "";
                			}
                			//Changes according to BH FS ABR System Feed20120810.doc Reconciliation XML Message Support.doc Name of MQ Properties File for downstream receiver. There should be a single value per row. There should be an instance (row) for each MQPROPFILE that resulted in the message being sent.
                			 StringTokenizer stString = new StringTokenizer(MQPropFile, "|" , false);
                			 while (stString.hasMoreElements()) {
                			     String sigleMQPropfile = stString.nextToken();
                			     MSGLOGList.add(new String[]{Integer.toString(rootEntity.getEntityID()),getNow(),MQCID,t2DTS, (succQueueNameVct.contains(sigleMQPropfile)?"S":"F"),succQueueNameVct.contains(sigleMQPropfile)?"":msgreason});
                	             inertMSGLOG(MSGLOGList,xmlfeedEntity.getEntityType(),xmlfeedEntity.getEntityID(), setupDTS, rootEntity.getEntityType(),sigleMQPropfile);               		
                	             MSGLOGList.clear();
                			 }
                		} 
                	}	
                }
            }
        }
        catch(Throwable exc) {
            java.io.StringWriter exBuf = new java.io.StringWriter();
            String Error_EXCEPTION="<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
            String Error_STACKTRACE="<pre>{0}</pre>";
            msgf = new MessageFormat(Error_EXCEPTION);
            setReturnCode(INTERNAL_ERROR);
            exc.printStackTrace(new java.io.PrintWriter(exBuf));
            // Put exception into document
            args[0] = exc.getMessage();
            rptSb.append(msgf.format(args) + NEWLINE);
            msgf = new MessageFormat(Error_STACKTRACE);
            args[0] = exBuf.getBuffer().toString();
            rptSb.append(msgf.format(args) + NEWLINE);
            logError("Exception: "+exc.getMessage());
            logError(exBuf.getBuffer().toString());
        }
        finally
        {
            setDGTitle(navName);
            setDGRptName(getShortClassName(getClass()));
            setDGRptClass(getABRCode());
            // make sure the lock is released
            if(!isReadOnly()) {
                clearSoftLock();
            }
            closePrintWriters();
        }

        // clear memory
        extxmlfeedVct.clear(); 
        succQueueNameVct.clear();
        //Print everything up to </html>
        //Insert Header into beginning of report
        msgf = new MessageFormat(HEADER);
        args[0] = getShortClassName(getClass());
        args[1] = navName;
        String header1 = msgf.format(args);
        //
        String header2 = null;
        if (isPeriodicABR){
            header2 = buildPeriodicRptHeader();
            restoreXtraContent();
        }else{
            header2 = buildDQTriggeredRptHeader();
        }

        //XML_MSG= XML Message
        String info = header1+header2+"<pre>"+
        rsBundle.getString("XML_MSG")+"<br />"+
        userxmlSb.toString()+"</pre>" + NEWLINE;
        rptSb.insert(0,info);

        println(rptSb.toString()); // Output the Report
        //fix the memory leak for the OOM issue of the SVCMOD 
        rsBundle = null;
        args =null;
        extxmlfeedVct = null;
        succQueueNameVct = null;
        msgf = null;
        userxmlSb = null;
        rptSb = null;
        printDGSubmitString();
        println(EACustom.getTOUDiv());
        buildReportFooter(); // Print </html>
        //println("showMemory debug1:" + ABRUtil.showMemory());
    }

	/**
	 * 
	 * @param rootEntity
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private boolean checkSystemResendCache(EntityItem rootEntity)
			throws MiddlewareException, SQLException {
		
		boolean isCache = false;
		String entityType = rootEntity.getEntityType();
		int entityID = rootEntity.getEntityID();
		Statement statement = null;
		
		ResultSet result = null;
		addDebug("rootType :" + entityType);
		addDebug("entityID :" + entityID);
		
		StringBuffer strbSQL = new StringBuffer();
		strbSQL.append(" SELECT XMLMESSAGE FROM cache.xmlidlcache");
		strbSQL.append(" WHERE xmlentitytype = '" + entityType + "'");
		strbSQL.append(" AND xmlentityID = " + entityID );
		strbSQL.append(" AND xmlcachevalidto > current timestamp with ur");
		
		addDebug("query cache sql is :" + strbSQL.toString());
		
		try {
			
		    statement = m_db.getODSConnection().createStatement();
			result = statement.executeQuery(strbSQL.toString());

			if(result.next()) {
				SystemResendCacheXml = result.getString(1);				 
				isCache = true;                    				
			}else{
				isCache = false;                    				
			}
			addDebug("get XMLIDLCache where" + rootEntity.getKey() + " is isCached: " + isCache);
		} catch (MiddlewareException e){
		    addDebug("MiddlewareException on ? " + e);
		    e.printStackTrace();
		    throw e;
		} catch (SQLException  rx) {
			addDebug("RuntimeException on ? " + rx);
		    rx.printStackTrace();
		    throw rx;
		}
		return isCache;
	}

	/**
	 BH FS ABR XML System Feed 20130904.doc Page52
	 1.	IF root entity STATUS <> 閳ユ窋inal閳ワ拷 (0020) THEN
			a.	Do NOT create XML
			b.	Produce a report with the following message:
				Error: A 閳ユ珐esend RFR閳ワ拷 request is not valid since the data must be "Final".
			c.	Set ABR return code to 閳ユ窋ailed閳ワ拷 (0040)
			d.	Exit
		END IF

	2.	IF STATUS = "Final" (0020) AND "ADS XML Feed ABR" (ADSABRSTATUS) was ever "Passed" (0030) THEN 
			a.	Do NOT create XML
			b.	Produce a report with the following message:
				Error: A 閳ユ珐esend RFR閳ワ拷 request is not valid since XML was previously created successfully.
			c.	Set ABR return code to 閳ユ窋ailed閳ワ拷 (0040)
			d.	Exit
		END IF
	3.	IF STATUS = "Final" (0020) AND "ADS XML Feed ABR" (ADSABRSTATUS) was never "Passed" (0030) THEN
			a.	Find the last time STATUS = Ready for Review"" (0040) just prior to the first time that STATUS = "Final""(0020)"
			b.	Set T2 = VALTO of this STATUS = "Ready for Review" (0040)"
			c.	Set T2 = T2 - 30 seconds	 
	 * @param rootEntity
	 * @param statusAttr
	 * @param statusFlag
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void processSystemResendRFR(EntityItem rootEntity,
			String statusAttr, String statusFlag) throws MiddlewareException,
			SQLException {
			// need to check this value
			setReturnCode(RESENDRFR);
			addDebug("this abr is GroupABR :" + m_abri.getABRQueType().equals("GroupABR"));
			if (STATUS_FINAL.equals(statusFlag)){
				/**
				 * (ADSABRSTATUS) was ever (0030) THEN 
				 *	Do not create XML, set ABR return code to (0040) and exit with the following message in the report:
				 *	Error: A Resend RFR request is not valid since XML was previously created successfully. 
				 */
		        AttributeChangeHistoryGroup adsStatusHistoy = getADSABRSTATUSHistory("ADSABRSTATUS");
		        // if Status is RFR, and has Passed Final before, then exit with Error.            
		        if (existBefore(adsStatusHistoy, STATUS_PASSED)){
		            addDebug(rootEntity.getKey() + " Error: A Resend RFR request is not valid since XML was previously created successfully.");
		            addError("Error: A \"Resend RFR\" request is not valid since XML was previously created successfully." , rootEntity);
		        }else{
		            actionTaken = rsBundle.getString("ACTION_FINAL_RESEND_RFR");
		        }
				
		    }else{
		        addDebug(rootEntity.getKey()+" A \"Resend RFR\" request is not valid since the data must be \"Final\"");
		        addError("Error : A \"Resend RFR\" request is not valid since the data must be \"Final\"" , rootEntity);                              
		    }
			// put values into the status and priorstatus fields
//		    curStatus = PokUtils.getAttributeValue(rootEntity, statusAttr,", ", "", false);
//		    curStatusvalue =  PokUtils.getAttributeFlagValue(rootEntity,statusAttr);
		    
		    // ResendRFR the status of xml will be always "Ready for Review"(0040)
			curStatus = "Ready for Review";
			curStatusvalue = STATUS_R4REVIEW;
	}

	/**
	 * @param rootEntity
	 * @param mqAbr
	 * @param statusAttr
	 * @param statusFlag
	 * @throws SQLException
	 * @throws MiddlewareException
	 * @throws MiddlewareRequestException
	 */
	private void processSystemResend(EntityItem rootEntity, XMLMQ mqAbr,
			String statusAttr, String statusFlag) throws SQLException,
			MiddlewareException, MiddlewareRequestException {
		// get _SYSFeedResendValue from abr.server.properties
		String sysResendStatus= sysFeedResendStatus(m_abri.getABRCode(),SYSFeedResendValue,"Both");
		// if _SYSFeedResendValue is equal to Final, then resend status must be Final.
		if(STATUS_FINAL.equals(sysResendStatus)){
		    if (STATUS_FINAL.equals(statusFlag)){
		        actionTaken = rsBundle.getString("ACTION_FINAL_RESEND");
		    }else{
		        addDebug(rootEntity.getKey()+" is not Final");
		        //RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
		        //addError(rsBundle.getString("RESEND_ONLY_FINAL")); 
		        addError(rsBundle.getString("RESEND_ONLY_FINAL") , rootEntity);  
		    }
		}else if(STATUS_R4REVIEW.equals(sysResendStatus)){
		    if (STATUS_R4REVIEW.equals(statusFlag)){
//                                  get ADSABRSTATUS changed history group
		        AttributeChangeHistoryGroup adsStatusHistoy = getADSABRSTATUSHistory("ADSABRSTATUS");
		        //get STATUS changed history group
		        AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(mqAbr);
		        // if Status is RFR, and has Passed Final before, then exit with Error.            
		        if (existPassedFinal(adsStatusHistoy,statusHistory)){
		            addDebug(rootEntity.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
		            addError(rsBundle.getString("RESEND_R4R_PASSEDFINAL"), rootEntity);
		        }else{
		            actionTaken = rsBundle.getString("ACTION_R4R_RESEND");
		        }
		    }else{
		        addDebug(rootEntity.getKey()+" is not RFR");
		        //RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
		        addError(rsBundle.getString("RESEND_ONLY_R4REVIEW"), rootEntity);                               
		    }
		    // both Final and R4R trigger resend data   
		}else{
		    if (!STATUS_FINAL.equals(statusFlag)&& !STATUS_R4REVIEW.equals(statusFlag)){
		        addDebug(rootEntity.getKey()+" is not Final or R4R");
		        //RESEND_NOT_R4RFINAL = was queued to resend data; however, it is not Ready for Review or Final.
		        addError(rsBundle.getString("RESEND_NOT_R4RFINAL"), rootEntity);
		    }else{
		        if (STATUS_FINAL.equals(statusFlag)){
		            actionTaken = rsBundle.getString("ACTION_FINAL_RESEND");
		        }else{
		            //get ADSABRSTATUS changed history group
		            AttributeChangeHistoryGroup adsStatusHistoy = getADSABRSTATUSHistory("ADSABRSTATUS");
		            //get STATUS changed history group
		            AttributeChangeHistoryGroup statusHistory = getSTATUSHistory(mqAbr);
		            if (existPassedFinal(adsStatusHistoy,statusHistory)){
		                addDebug(rootEntity.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
		                addError(rsBundle.getString("RESEND_R4R_PASSEDFINAL"), rootEntity);
		            }else{
		                actionTaken = rsBundle.getString("ACTION_R4R_RESEND");
		            }

		        }
		    }       
		}           
		// put values into the status and priorstatus fields
		curStatus = PokUtils.getAttributeValue(rootEntity, statusAttr,", ", "", false);
		curStatusvalue =  PokUtils.getAttributeFlagValue(rootEntity,statusAttr);      
		priorStatus = curStatus;
	}

    /**
     * @param rootEntity
     * @param mqAbr
     * Return &DTFS
     */
    private String getDTFS(EntityItem rootEntity, XMLMQ mqAbr) {
        String statusAttr = mqAbr.getStatusAttr();
        String statusFlag = getAttributeFlagEnabledValue(rootEntity, statusAttr);
        String queuedvalue = "";
        if (STATUS_FINAL.equals(statusFlag)) {
        	if(getEntityType().equals("XMLPRODPRICESETUP")){
        		queuedvalue = getQueuedValue("ADSPPABRSTATUS");
        	}else{
        		queuedvalue = getQueuedValue("ADSABRSTATUS");
        	}
        } else {
        	if(getEntityType().equals("XMLPRODPRICESETUP")){
        		queuedvalue = getRFRQueuedValue("ADSPPABRSTATUS");
        	}else{
        		queuedvalue = getRFRQueuedValue("ADSABRSTATUS");
        	}
        }
        addDebug("getDTFS " + getEntityType() + statusFlag + " from properties file is " + queuedvalue);
        return queuedvalue;
    }

    private void setupPrintWriters(){
        String fn = m_abri.getFileName();
        int extid = fn.lastIndexOf(".");
        dbgfn = fn.substring(0,extid+1)+"dbg";
        userxmlfn = fn.substring(0,extid+1)+"userxml";
        try {
            dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dbgfn, true), "UTF-8"));
        } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "trouble creating debug PrintWriter " + x);
        }
        try {
            userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(userxmlfn, true), "UTF-8"));
        } catch (Exception x) {
            D.ebug(D.EBUG_ERR, "trouble creating xmlgen PrintWriter " + x);
        }
    }
    private void closePrintWriters() {
        if (dbgPw != null){
            dbgPw.flush();
            dbgPw.close();
            dbgPw = null;
        }
        if (userxmlPw != null){
            userxmlPw.flush();
            userxmlPw.close();
            userxmlPw = null;
        }
    }

    private void restoreXtraContent(){
        // if written to file and still small enough, restore debug and xmlgen to the abr rpt and delete the file
        if (userxmlLen+rptSb.length()<MAXFILE_SIZE){
            // read the file in and put into the stringbuffer
            InputStream is = null;
            FileInputStream fis = null;
            BufferedReader rdr = null;
            try{
                fis = new FileInputStream(userxmlfn);
                is = new BufferedInputStream(fis);

                String s=null;
                rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // append lines until done
                while((s=rdr.readLine()) !=null){
                    userxmlSb.append(convertToHTML(s)+NEWLINE);
                }
                // remove the file
                File f1 = new File(userxmlfn);
                if (f1.exists()) {
                    f1.delete();
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if (is!=null){
                    try{
                        is.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
                if (fis!=null){
                    try{
                        fis.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
            }
        }else{
            userxmlSb.append("XML generated was too large for this file");
        }
        // if written to file and still small enough, restore debug and xmlgen to the abr rpt and delete the file
        if (dbgLen+userxmlSb.length()+rptSb.length()<MAXFILE_SIZE){
            // read the file in and put into the stringbuffer
            InputStream is = null;
            FileInputStream fis = null;
            BufferedReader rdr = null;
            try{
                fis = new FileInputStream(dbgfn);
                is = new BufferedInputStream(fis);

                String s=null;
                StringBuffer sb = new StringBuffer();
                rdr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // append lines until done
                while((s=rdr.readLine()) !=null){
                    sb.append(s+NEWLINE);
                }
                rptSb.append("<!-- "+sb.toString()+" -->"+NEWLINE);

                // remove the file
                File f1 = new File(dbgfn);
                if (f1.exists()) {
                    f1.delete();
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                if (is!=null){
                    try{
                        is.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
                if (fis!=null){
                    try{
                        fis.close();
                    }catch(Exception x){
                        x.printStackTrace();
                    }
                }
            }
        }
    }

    /******************************************
     * build xml generation msg
     */
    protected void addXMLGenMsg(String rsrc, String info)
    {
        MessageFormat msgf = new MessageFormat(rsBundle.getString(rsrc));
        Object args[] = new Object[]{info};
        xmlgenSb.append(msgf.format(args)+"<br />");
    }

    /******************************************
     * A.   Data Quality Triggered ABRs
     *
     * The Report should identify:
     * -    USERID (USERTOKEN)
     * -    Role
     * -    Workgroup
     * -    Date/Time
     * -    Status
     * -    Prior feed Date/Time
     * -    Prior Status
     * -    Action Taken
     */
    private String buildDQTriggeredRptHeader(){
        String HEADER2 = "<table>"+NEWLINE +
        "<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
        "<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
        "<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
        "<tr><th>Date/Time: </th><td>{3}</td></tr>"+NEWLINE +
        "<tr><th>Status: </th><td>{4}</td></tr>"+NEWLINE +
        "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>"+NEWLINE +
        "<tr><th>Prior Status: </th><td>{6}</td></tr>"+NEWLINE +
        "<tr><th>Action Taken: </th><td>{7}</td></tr>"+NEWLINE+
        "</table>"+NEWLINE+
        "<!-- {8} -->" + NEWLINE;
        MessageFormat msgf = new MessageFormat(HEADER2);
        args[0] = m_prof.getOPName();
        args[1] = m_prof.getRoleDescription();
        args[2] = m_prof.getWGName();
        args[3] = t2DTS;
        args[4] = curStatus;
        args[5] = t1DTS;
        args[6] = priorStatus;
        args[7] = actionTaken+"<br />"+xmlgenSb.toString();
        args[8] = abrversion+" "+getABRVersion();

        return msgf.format(args);
    }

    /******************************************
     * B.   Periodic ABRs
     *
     * The Report should identify:
     * -    Date/Time of this Run
     * -    Last Ran Date/Time Stamp
     * -    Action Taken
     */
    private String buildPeriodicRptHeader(){
        String HEADER2 = "<table>"+NEWLINE +
        "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>"+NEWLINE +
        "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>"+NEWLINE +
        "<tr><th>Action Taken: </th><td>{2}</td></tr>"+NEWLINE+
        "</table>"+NEWLINE+
        "<!-- {3} -->" + NEWLINE;
        MessageFormat msgf = new MessageFormat(HEADER2);
        args[0] = t2DTS;
        args[1] = t1DTS;
        args[2] = xmlgenSb.toString();
        args[3] = abrversion+" "+getABRVersion();

        return msgf.format(args);
    }

    /******************************************
     * get entitylist used for compares
     */
    protected EntityList getEntityListForDiff(Profile prof, String veName, EntityItem item) throws
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        ExtractActionItem xai = new ExtractActionItem(null, m_db, prof, veName);
        //xai.setSkipCleanup(true);
        EntityList list = m_db.getEntityList(prof, xai,
                new EntityItem[] { new EntityItem(null, prof, item.getEntityType(), item.getEntityID())});
        EntityItem root_ei = list.getParentEntityGroup().getEntityItem(0); 
        /* 1) in the spec "BH FS ABR XML IDL 20110901.doc" (note: I will be sending a new version this morning), 
         * p24 # 5 - that you copied above --- this statement is only looking at the offering being considered. 
         * Consider a LSEO (same would be true for LSEOBUNDLE) - A "special bid" does NOT have any AVAILs and hence this criteria does not apply. 
         * The CACHE would be updated to include it. Now consider a LSEO that is a GA product. It must have an AVAIL where AVAILTYPE = "Planned Availability". 
         * The "problem" being handled by this criteria is where the LSEO XML generation is triggered and the AVAIL is for Germany but its STATUS is "Change Request". (So this AVAIL will be filter removed)
         * If the XML were added to CACHE it would appear to be "World Wide" (all countries, becase Planed AVAIL has been removed); however, it is really limited to Germany. 
         * The XML generator does not consider the AVAIL if the STATUS of the AVAIL is not Ready for Review or Final (really based on STATUS of the LSEO). 
         * Now consider a LSEO with two AVAILs - the one for Germany as stated above and one for China that is Final. The XML generator will create a country list of China but not Germany. 
         * This is correct and it will  be updated when the AVAIL for Germany has its STATUS updated.
         * only check the current time(T2)
         * Update following requirement according to ABR XML IDL 20111020.doc 
        5.	If the offering has an AVAIL where AVAILTYPE = Planned Availability then it must have at least one AVAIL where AVAILTYPE = Planned Availability and applies based on the following criterion. The first criterion that applies should be used.
        a.	IF the root entity STATUS = Ready for Review (0040) and the AVAIL STATUS = {Ready for Review (0040) | Final (0020)}, then include the AVAIL
        b.	IF the root entity STATUS = Final (0020) and the AVAIL STATUS = Final (0020), then include the AVAIL
        c.	IF the AVAIL EFFECTIVEDATE <= 2010-03-01, then include the AVAIL
        d.	If none of the above applies, then the AVAIL does not apply and hence do not include the AVAIL
            If the offering after looking at all AVAILs does not have at least one AVAIL that applies (included) where AVAILTYPE = Planned Availability, then do not proceed with creating XML or updating CACHE.
            */
        boolean checkAVAIlStatus = false;
        //ADSSWPRODSTRUCT2 and ADSPRODSTRUCT2need check the avail
        /**
         * 5.	If the offering has an AVAIL where AVAILTYPE = Planned Availability 
         * then it must have at least one AVAIL where AVAILTYPE = Planned Availability and applies based on the following criterion. 
         * The first criterion that applies should be used.
         */
//        //if ("ADSPRODSTRUCT2".equals(veName)||"ADSSWPRODSTRUCT2".equals(veName)) {
        // do not need to consider VE ADSPRODSTRUCT2 and ADSSWPRODSTRUCT2. they are not the root TMF, they are root MODEL.
        //add 2013-11-06 remove the check for the avail base on the BH FS ABR XML System Feed Mapping 20130904b.doc
        if ("ADSPRODSTRUCT2".equals(veName)||"ADSSWPRODSTRUCT2".equals(veName) || "ADSLSEO2".equals(veName)) {
        	checkAVAIlStatus = false;
        } else if (t2DTS.equals(prof.getEffOn())){
        	checkAVAIlStatus = true;
        }     	
        if (checkAVAIlStatus){
        	String root_status  = PokUtils.getAttributeFlagValue(root_ei, "STATUS");
        	boolean hasPlanedAVAIl = false;
            boolean RFRorFinalPlanedAVAIl = false;
            if (root_ei.hasDownLinks()){  
            	for (int ii=0; ii<root_ei.getDownLinkCount(); ii++){
            		EntityItem relator = (EntityItem)root_ei.getDownLink(ii);
            		if (relator.hasDownLinks()){
            			for (int iii=0; iii<relator.getDownLinkCount(); iii++){
            				EntityItem availei = (EntityItem)relator.getDownLink(iii);
            				if ("AVAIL".equals(availei.getEntityType()) ){
            					EANFlagAttribute fAtt = (EANFlagAttribute)availei.getAttribute("AVAILTYPE");	
            					if (fAtt!= null && fAtt.isSelected("146")){
            						hasPlanedAVAIl = true;
            						String item_status = PokUtils.getAttributeFlagValue(availei,"STATUS");
            						String anndate = PokUtils.getAttributeValue(availei, "EFFECTIVEDATE", ", ", CHEAT, false);
            						if(anndate.compareTo(OLDEFFECTDATE) <= 0){
            							RFRorFinalPlanedAVAIl = true;//old data, we think it meet the status checking.
            						}else if(STATUS_R4REVIEW.equals(root_status)||STATUS_FINAL.equals(root_status) ){
	                                    if (STATUS_FINAL.equals(item_status)||STATUS_R4REVIEW.equals(item_status)) {
	                                    	RFRorFinalPlanedAVAIl = true;
	                                     }
            						}
	                                //for IDL Cach load, it allow Change For Request root eneity load into Cache table.     
            						else if(STATUS_CHGREQ.equals(root_status)){
            							 if (STATUS_FINAL.equals(item_status)||STATUS_R4REVIEW.equals(item_status)) {
 	                                    	RFRorFinalPlanedAVAIl = true;
 	                                     }
            						}
            						addDebugComment(D.EBUG_DETAIL,"Cheking planed AVAIL " + availei.getKey() + " Status is " +item_status+ " AVAIL Type: "
            								+ PokUtils.getAttributeFlagValue(availei, "AVAILTYPE"));
            				}
            			}
            		}
            	 }
              }
            }
            if (hasPlanedAVAIl && !RFRorFinalPlanedAVAIl){
    			addError("Error: Invalid Planed AVAIL. There must have at least one Planed AVAIL with Status is " + (STATUS_FINAL.equals(root_status)?"Final":"RFR or Final") + " , else the XML report will not generated", item);
    			return null;		
    		}	
        }
        // debug display list of groups
        addDebugComment(D.EBUG_INFO , "EntityList for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
                PokUtils.outputList(list));

        EntityGroup peg = list.getParentEntityGroup();
        
        // check if this VE is subject to status filtering
        if (isVEFiltered(veName)) {
            // Get the status for the root  
            //EntityItem root_ei = list.getParentEntityGroup().getEntityItem(0); 
            String root_status = PokUtils.getAttributeFlagValue(root_ei,"STATUS");
            addDebugComment(D.EBUG_DETAIL, "The status of the root for VE " + veName + " is: "+ root_status);            

            // For each entity type for the VE in VE_Filter_Array, get the group and 
            // check the entities in the group for filtering.
            for (int i=0; i<VE_Filter_Array.length; i++) {
            	addDebugComment(D.EBUG_DETAIL, "Looking at VE_filter_Array" + VE_Filter_Array[i][0]+" "
                        + VE_Filter_Array[i][1] + " " + VE_Filter_Array[i][2]); 
                if (VE_Filter_Array[i][0].equals(veName)) {
                    EntityGroup eg = list.getEntityGroup(VE_Filter_Array[i][1]);
                    addDebugComment(D.EBUG_DETAIL, "Found "+ list.getEntityGroup(VE_Filter_Array[i][1]));
                    // There should always be a group, unless the VE has been changed and the 
                    // VE_Filter_Array has not been updated. 
                    if (eg != null){                      

                        // Load the EntityItem ojects into an array for processing.
                        EntityItem ei_array[] = eg.getEntityItemsAsArray();

                        // Loop through the entity items in the group.
                        for (int e=0; e<ei_array.length; e++)
                        {
                            String item_status = null;
                            boolean remove_item = true;
                            EntityItem ei = ei_array[e]; 
                            String item_type = ei.getEntityType(); 

                            addDebugComment(D.EBUG_INFO, "Looking at entity "+ei.getEntityType()+" "+ei.getEntityID());

                            // Get the filter criteria
                            String item_filter_status = VE_Filter_Array[i][2];

                            // Get the status of the item
                            item_status = PokUtils.getAttributeFlagValue(ei,(String)ITEM_STATUS_ATTR_TBL.get(item_type));
                        	String filter_status = root_status;
                        	// BH FS ABR XML System Feed Mapping 20121001.doc For TMF Filter AVAIL base on the FEATURE is RPQ or GA
                        	//AVAILs are filtered based on whether the FEATURE is a RPQ (i.e. PRODSTRUCT-u: FEATURE. FCTYPE <> Primary FC (100) | "Secondary FC" (110)) or has General Availability (i.e. not a RPQ)
                        	//If the FEATURE is an RPQ, then the 閳ユ窋ilter Value閳ワ拷 is based on the value of PRODSTRUCT.STATUS.
//                        	IF PRODSTRUCT: PRODSTUCTAVAIL-d: AVAIL.AVAILTPE = 閳ユ阀lanned Availability閳ワ拷
//                        	THEN
//                        	Note: this is the GA logic
//                        	IF the AVAILANNA-d: ANNOUNCEMENT.ANNSTATUS = 閳ユ窋inal閳ワ拷 (0020)
//                        	THEN
//                        	Filter Value = 閳ユ窋inal閳ワ拷 (0020)
//                        	ELSE 
//                        	IF the ANNOUNCEMENT.ANNSTATUS = 閳ユ珐eady for Review閳ワ拷 (0040)
//                        	THEN
//                        	Filter Value = {"Ready for Review" (0040) | "Final" (0020)}
//                        	ELSE
//                        	Note: supports AVAILs without an ANNOUNCEMENT
//                        	IF AVAIL.STATUS = 閳ユ窋inal閳ワ拷 (0020)
//                        	THEN
//                        	Filter Value = 閳ユ窋inal閳ワ拷 (0020)
//                        	ELSE 
//                        	Filter Value = {"Ready for Review" (0040) | "Final" (0020)}
//                        	END
//                        	END
//                        	END
//                        	ELSE
//                        	Note: this is the RPQ or 閳ユ笝ld data閳ワ拷 logic
//                        	IF PRODSTRUCT.STATUS = "Final" (0020)
//                        	THEN
//                        	Filter Value = "Final" (0020)
//                        	ELSE
//                        	Filter Value = {"Ready for Review" (0040) | "Final" (0020)}
//                        	END
//                        	END

                        	// the new change P94 in the BH FS ABR XML System Feed Mapping 20130904b.doc
                        	//wyl remove the filter for the TMF 
//                        	if("ADSPRODSTRUCT".equals(veName) || "ADSSWPRODSTRUCT".equals(veName)){ 
//                        		filter_status = null;
//                        		EntityGroup avagroup = list.getEntityGroup("AVAIL");
//                        	    if (avagroup != null){                      
//                                      // Load the EntityItem ojects into an array for processing.
//                        	    	  Vector planAvaVector = new Vector();
//                                      EntityItem avail_array[] = avagroup.getEntityItemsAsArray();
//                                      for (int ii=0; ii<avail_array.length; ii++){
//                                    	  EntityItem avail = avail_array[ii];
//                                    	  String availtype = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");
//                                    	  if (availtype != null && "146".equals(availtype)){
//                                    		  planAvaVector.add(avail);
//                                    		  Vector relatorVec = avail.getDownLink();
//                                    		  for (int iii = 0; iii < relatorVec.size(); iii++) {
//                  							      EntityItem availanna = (EntityItem) relatorVec.elementAt(iii);
//                  								  if (availanna.hasDownLinks() && availanna.getEntityType().equals("AVAILANNA")) {
//                  								      Vector annVct = availanna.getDownLink();
//                  									  EntityItem anna = (EntityItem) annVct.elementAt(0);
//                  									  String annstatus = PokUtils.getAttributeFlagValue(anna, "ANNSTATUS");
//                  									  if (annstatus != null && (STATUS_FINAL.equals(annstatus)||(STATUS_R4REVIEW.equals(annstatus)))){
//                  									      if (filter_status == null){
//                  									    	 filter_status = annstatus;
//                  									      } else if (STATUS_FINAL.equals(annstatus)){
//                  									    	filter_status = annstatus; 
//                  									      }
//                  									      addDebugComment(D.EBUG_INFO,  "New check Filter_STATUS from ANNOUNCEMENT ANNSTATUS is "+filter_status); 
//                  									 }
//                  								  }
//                                    		  }
//                                    	  }
//                        	         }
//                                     if (filter_status == null){
//                                    	 for (int jj=0; jj<planAvaVector.size(); jj++){
//                                    		 EntityItem pa = (EntityItem)planAvaVector.elementAt(jj);
//                                    		 String avastatus = PokUtils.getAttributeFlagValue(pa, "STATUS");
//                                    		 if (avastatus!=null && (STATUS_FINAL.equals(avastatus)||(STATUS_R4REVIEW.equals(avastatus)))){
//                                    			 if (filter_status == null){
//          									    	 filter_status = avastatus;
//          									      } else if (STATUS_FINAL.equals(avastatus)){
//          									    	filter_status = avastatus; 
//          									      }
//                                    		 } 
//                                    		 addDebugComment(D.EBUG_INFO,  "New check Filter_STATUS from " + pa.getKey() + " AVAILSTATUS is "+filter_status);
//                                    	 } 
//                                    	 
//                                     }
//                                }
//
//                            	if (filter_status == null){
//                                    filter_status = root_status;
//                            	    addDebugComment(D.EBUG_INFO,  "New check Filter_STATUS from Root Status is "+filter_status);
//                            	}
//                        	}
                            
                            
                            
                            // Now determine if the entity should be removed from the list (ie - filtered).
                            // remove it - If the status is null then it is, assumed to be 'old' data that is FINAL and will not filtered. 
                            // If it is FINAL, then it will never be filtered. 
                            // If it is RFR then it will not be filtered if the root is also RFR or the filer criteria is 'RFR Final'.
                            // Therefore - entity IS filtered for the following cases -
                            // - the status is DRAFT or CR
                            // - the status is RFR and the root status is FINAL and 'Final' is epcified as the criteria
                            
                            
                            // update the following requirement according to spec Data Transformation System Feed 20111020.doc
                            //For filtering AVAIL and ANNOUNCEMENT, there is an exception to this filtering.
                            // 閳ワ拷	IF AVAIL EFFECTIVEDATE <= 2010-03-01, then it is included without looking at STATUS
                          //remove this announcement anndate checking based on document update - BH FS ABR XML System Feed Mapping 20120705.doc
                            //閳ワ拷	IF ANNOUNCEMENT ANNDATE <= 2010-03-01, then it is included without looking at STATUS
                            //old data, we think it meet the status checking.
                            addDebugComment(D.EBUG_INFO, (String)ITEM_STATUS_ATTR_TBL.get(item_type)+ " is "+item_status);
                            if ("AVAIL".equals(item_type)){
                            	
                            	if("PRODSTRUCT".equals(root_ei.getEntityType()) || "SWPRODSTRUCT".equals(root_ei.getEntityType())){
                            		//get MODEL to check if it is OLD data
                            		for (int n=0; n<root_ei.getDownLinkCount(); n++){
                        				EntityItem mdlei = (EntityItem)root_ei.getDownLink(n);
                        				if("MODEL".equals(mdlei.getEntityType())){
                        					String root_AnnDate = PokUtils.getAttributeValue(mdlei, "ANNDATE", ", ", CHEAT,false);
                        					addDebugComment(D.EBUG_INFO,  "New check PRODSTRUCT ANN DATE is "+root_AnnDate);
                                        	if(root_AnnDate.compareTo(OLDEFFECTDATE) <= 0){
                                        		remove_item=false;
                                        		break;
                                        	}
                        				}
                            		}
                            		if(!remove_item) continue;
                            	}
                                if("MODEL".equals(root_ei.getEntityType()) || "MODELCONVERT".equals(root_ei.getEntityType())){
                                	String root_AnnDate = PokUtils.getAttributeValue(root_ei, "ANNDATE", ", ", CHEAT,false);
                                	addDebugComment(D.EBUG_INFO,  "New check ROOT ANN DATE is "+root_AnnDate);
                                	if(root_AnnDate.compareTo(OLDEFFECTDATE) <= 0){
                                		remove_item=false;
                                		continue;
                                	}
                                }
                                EANFlagAttribute fAtt = (EANFlagAttribute)ei.getAttribute("AVAILTYPE");	            					
                            	String anndate = PokUtils.getAttributeValue(ei, "EFFECTIVEDATE", ", ", CHEAT, false);
                            	addDebugComment(D.EBUG_INFO,  "New check EFFECTIVEDATE is "+anndate);
                            	
                            	if (item_status == null) remove_item=true;
                            	else if (fAtt!= null && fAtt.isSelected("146") && anndate.compareTo(OLDEFFECTDATE) <= 0){
        							remove_item=false;//old data, we think it meet the status checking.
        						}
                            	//wyl new change P50 in the BH FS ABR XML System Feed Mapping 20130904b.doc
                            	else if (item_status.equals(STATUS_FINAL)||item_status.equals(STATUS_R4REVIEW)) {
        							remove_item=false;
        						}
//        						else if (item_status.equals(STATUS_R4REVIEW) && (filter_status.equals(STATUS_R4REVIEW) && 
//                                    item_filter_status.equals("RFR Final"))) remove_item=false;
//                            } else if ("ANNOUNCEMENT".equals(item_type)){
////                            	String anndate = PokUtils.getAttributeValue(ei, "ANNDATE", ", ", CHEAT, false);
//                            	if (item_status == null) remove_item=true;
////                            	else if (anndate.compareTo(OLDEFFECTDATE) <= 0){
////        							remove_item=false;//old data, we think it meet the status checking.
////        						}
//                            	else if (item_status.equals(STATUS_FINAL)) {remove_item=false;
//        						}else if (item_status.equals(STATUS_R4REVIEW) && (root_status.equals(STATUS_R4REVIEW) && 
//                                    item_filter_status.equals("RFR Final"))) remove_item=false;
                            } else {
                            	if (item_status == null) remove_item=true;
                                else if (item_status.equals(STATUS_FINAL)) remove_item=false;
                                else if (item_status.equals(STATUS_R4REVIEW) && (filter_status.equals(STATUS_R4REVIEW) && 
                                    item_filter_status.equals("RFR Final"))) remove_item=false;
                            }
                            if (remove_item == true) 
                            {
                            	addDebugComment(D.EBUG_INFO, "Removing "+ item_type+" "+ ei.getEntityID() + " " + item_status + " from list");
                            	addDebugComment(D.EBUG_INFO, "Filter criteria is " + item_filter_status);
                                //removeItem(eg,ei); 
                                removeItem(peg,ei);
                            } // end if remove_item is true
                        } // end for loop for Entity Items
                    } // end if eg != null
                } // end if (VE_Filter_Array[i][0].equals(veName))  
            } // end loop through VE_Filter_Array 

//          debug display list of groups after filtering
            addDebugComment(D.EBUG_INFO, "EntityList after filtering for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
                    PokUtils.outputList(list));
        } // end if VE is subject to filtering

        // check if this VE is subject to country filtering
        if (isVECountryFiltered(veName)) {

            //EntityItem root_ei = list.getParentEntityGroup().getEntityItem(0);
            String root_type = root_ei.getEntityType(); 
            String root_country_attr = (String)ITEM_COUNTRY_ATTR_TBL.get(root_type);

            // get hashset of country flagcodes for the root
            HashSet rootSet = getCountry(root_ei, root_country_attr);

            // For each entity type for the VE in VE_Country_Filter_Array, get the group and 
            // check the entities in the group for filtering.
            for (int i=0; i<VE_Country_Filter_Array.length; i++) {
            	addDebugComment(D.EBUG_DETAIL, "Looking at VE_Country_Filter_Array " + VE_Country_Filter_Array[i][0]+" "
                        + VE_Country_Filter_Array[i][1]); 
                if (VE_Country_Filter_Array[i][0].equals(veName)) {
                    EntityGroup eg = list.getEntityGroup(VE_Country_Filter_Array[i][1]);
                    addDebugComment(D.EBUG_DETAIL, "Found "+ list.getEntityGroup(VE_Country_Filter_Array[i][1]));
                    // There should always be a group, unless the VE has been changed and the 
                    // VE_Country_Filter_Array has not been updated. 
                    if (eg != null){                      

                        // Load the EntityItem ojects into an array for processing.
                        EntityItem ei_array[] = eg.getEntityItemsAsArray();

                        // Loop through the entity items in the group.
                        for (int e=0; e<ei_array.length; e++)
                        {
                            boolean remove_item = true;
                            EntityItem ei = ei_array[e]; 
                            String item_type = ei.getEntityType(); 

                            addDebugComment(D.EBUG_DETAIL, "Looking at entity "+ei.getEntityType()+" "+ei.getEntityID());

                            String item_country_attr = (String)ITEM_COUNTRY_ATTR_TBL.get(item_type);

                            // get hashset of country flagcodes for the item        
                            // set remove_item to false if there is an overlap between the root country codes and the item country
                            // codes
                            HashSet itemSet = getCountry(ei, item_country_attr);
                            Iterator rootSet_itr = rootSet.iterator();
                            while(rootSet_itr.hasNext() && remove_item == true) {
                                String ctryVal = (String) rootSet_itr.next();
                                if (itemSet.contains(ctryVal)) {
                                    remove_item = false;
                                }

                            }

                            if (remove_item == true) 
                            {                   
                                //removeItem(eg,ei); 
                                removeItem(peg,ei);
                            } // end if remove_item is true
                        } // end for loop for Entity Items
                    } // end if eg != null
                } // end if (VE_Country_Filter_Array[i][0].equals(veName))  
            } // end loop through VE_Country_Filter_Array 

//          debug display list of groups after filtering
            addDebugComment(D.EBUG_INFO, "EntityList after filtering for "+prof.getValOn()+" extract "+veName+" contains the following entities: \n"+
                    PokUtils.outputList(list));
        } // end if VE is subject to country filtering

        return list;
    }
    
    /**********************************************************************************
    10.	DTS9 = VALFROM of "Queued" (0020) for ADSABRSTATUS < DTS1 (VALFROM of current value 閳ユ窔n Process閳ワ拷 (0050) for ADSABRSTATUS)
	11.	Obtain the value of STATUS where VALFROM <= DTS9 < VALTO					
	12.	If this value of STATUS = 閳ユ珐eady for Review閳ワ拷 (0040) AND the value of ADSABRSTATUS was never Passed (0030), THEN
			Send the Full XML to downstream systems.
		END IF
	13.	If this value STATUS = "Final"  (0020) AND there is no prior value of STATUS = "Final" for which ADSABRSTATUS is "Passed" (0030), THEN
			Send the Full XML to downstream systems.
		END IF					
	14.	DTS3 = VALFROM for most recent value (i.e. latest) "Passed" (0030) for ADSABRSTATUS where VALFROM < T2					
	15.	If DTS3 not found, THEN
		Send the Full XML to downstream systems.
		END IF
	16.	DTS4 = VALTO for the value {"Final"  (0020) | 閳ユ珐eady for Review閳ワ拷 (0040)} of STATUS where VALFROM < DTS3					
	17.	DTS6 = VALFROM for the value {"Final"  (0020) | 閳ユ珐eady for Review閳ワ拷 (0040)} of STATUS where VALFROM < DTS3					
	18.	DTS4 = DTS4 - 30 seconds					
	19.	DTS5 = VALFROM for the most recent value (latest) 閳ユ窔n Process閳ワ拷 (0050) of ADSABRSTATUS where DTS6 < VALFROM < DTS3					
	20.	T1 = MIN(DTS4 | DTS5)  
     * @throws SQLException 
     */
    private void setT1DTS(XMLMQ mqAbr, AttributeChangeHistoryGroup adsStatusHistory, AttributeChangeHistoryGroup statusHistory, String dtfs) 
    throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException, MiddlewareException, SQLException
    {
        t1DTS = m_strEpoch;
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        if (isPeriodicABR){
            addDebug("getT1 entered for Periodic ABR "+rootEntity.getKey());
            // get it from the attribute
            EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("ADSDTS");
            if (metaAttr==null) {
                throw new MiddlewareException("ADSDTS not in meta for Periodic ABR "+rootEntity.getKey());
            }
            t1DTS = PokUtils.getAttributeValue(rootEntity, "ADSDTS", ", ", m_strEpoch, false);
        }else{
            String attCode = mqAbr.getStatusAttr();
            addDebug("getT1 entered for DQ ABR "+rootEntity.getKey()+" "+attCode+" isSystemResend:"+isSystemResend + " isIDLABR:" + isXMLIDLABR +" isXMLADSABR=" + isXMLADSABR);
            if (isXMLADSABR ){
                addDebug("getT1 isXMLADSABR");
            	String DTS1 = getValFromInStatusHistory("adsStatusHistory", adsStatusHistory, STATUS_CHGREQ,  null);
            	addDebug("getT1 isXMLADSABR DTS1=" + DTS1);
            	String DTS9 = getValFromInStatusHistory("adsStatusHistory", adsStatusHistory, STATUS_QUEUE, DTS1);
            	addDebug("getT1 isXMLADSABR DTS9=" + DTS9);
            	
            	boolean fullXML = isFullXML(adsStatusHistory, statusHistory, DTS9); 
            	addDebug("getT1 isXMLADSABR fullXML=" + fullXML);
            	
            	if(fullXML || isSystemResend || isSystemResendRFR || isSystemResendCache || isSystemResendCurrent){
            		addDebug("getT1 isXMLADSABR isSystemResend="+isSystemResend+" || isSystemResendRFR="+isSystemResendRFR
            				+" || isSystemResendCache="+isSystemResendCache+" || isSystemResendCurrent="+isSystemResendCache);
            		t1DTS = m_strEpoch;       
            		// For IN6289031 -GBT STATUS is not getting set for XML Cache table
            		// The defect only when production trigger and full xml
            		// setT1Action do - set status, prior status and action taken for report, set current status for cache table
            		if(!isSystemResend && !isSystemResendRFR && !isSystemResendCurrent) {
            			setT1Action(rootEntity, statusHistory, adsStatusHistory);
            		}
            	} else if(!fullXML){
            		//DTS3 = VALFROM for most recent value (i.e. latest) "Passed" (0030) for ADSABRSTATUS where VALFROM < T2
            		addDebug("getT1 isXMLADSABR fullXML=" + fullXML + "T2=" + t2DTS);
            		String DTS3 = getValFromInStatusHistory("adsStatusHistory", adsStatusHistory, STATUS_PASSED, t2DTS);
            		addDebug("getT1 isXMLADSABR fullXML=" + fullXML + "DTS3=" + DTS3);
            		String DTS4 = "";
            		String DTS5 = "";
            		String DTS6 = "";
            		if(DTS3!= null && DTS3.equals(m_strEpoch)){
            			t1DTS = m_strEpoch; 
            			addDebug("getT1 isXMLADSABR fullXML=" + fullXML + "T1=" + t1DTS);
            		}else{
            			//16.	DTS4 = VALTO for the value {"Final"  (0020) | 閳ユ珐eady for Review閳ワ拷 (0040)} of STATUS where VALFROM < DTS3
            			DTS4 = getValtoCompareValFromInStatusHistory(rootEntity, statusHistory, DTS3);
            			addDebug("getT1 isXMLADSABR fullXML=" + fullXML + "DTS4=" + DTS4);
            			//17.	DTS6 = VALFROM for the value {"Final"  (0020) | 閳ユ珐eady for Review閳ワ拷 (0040)} of STATUS where VALFROM < DTS3
            			DTS6 = getValFromInStatusHistory("statusHistory", statusHistory, null, DTS3);  
            			addDebug("getT1 isXMLADSABR fullXML=" + fullXML + "DTS6=" + DTS6);
            			//19.	DTS5 = VALFROM for the most recent value (latest) 閳ユ窔n Process閳ワ拷 (0050) of ADSABRSTATUS where DTS6 < VALFROM < DTS3
            			DTS5 = getValFromInStatusHistory(adsStatusHistory,STATUS_CHGREQ, DTS6, DTS3);
            			addDebug("getT1 isXMLADSABR fullXML=" + fullXML + "DTS5=" + DTS5);
            			//20.	T1 = MIN(DTS4 | DTS5)
            			t1DTS = getMinTime(DTS4, DTS5);  
            			addDebug("getT1 isXMLADSABR fullXML=" + fullXML + "T1=" + t1DTS);
            		}
            		setT1Action(rootEntity, statusHistory, adsStatusHistory);
            	}  
            	addDebug("getT1 isXMLADSABR T1=" + t1DTS);
            	addDebug("getT1 isXMLADSABR end");            	
            }
        }
    }

	/**
	 * @param rootEntity
	 * @param statusHistory
	 * @param adsStatusHistory
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void setT1Action(EntityItem rootEntity,
			AttributeChangeHistoryGroup statusHistory,
			AttributeChangeHistoryGroup adsStatusHistory) throws SQLException,
			MiddlewareException {
		// get T2Status and set curstatus and priorStatus.            	
		String t2Status = getT2Status(statusHistory);
		String t1Time ="";
		// look back in the ADS history for any prior "Passed" value. if exist ,then return ture.
		if (existBefore(adsStatusHistory, STATUS_PASSED)) {
		    // if T2 status is RFR
			if (t2Status.equals(STATUS_R4REVIEW)) {
				t1Time = getDeltaT1(adsStatusHistory, statusHistory, STATUS_FINAL);
		    	if (t1Time.equals(m_strEpoch)) {
		    		addDebug("getT1 CurrentStatus is RFR, there is no Passed Final before, try to find Passed RFR");
		    		t1Time = getDeltaT1(adsStatusHistory, statusHistory, STATUS_R4REVIEW);
		    		if (t1Time.equals(m_strEpoch)) {
		                actionTaken = rsBundle.getString("ACTION_R4R_FIRSTTIME");
		            } else {                            	
		                actionTaken = rsBundle.getString("ACTION_R4R_CHANGES");
		            }                   		
		    	} else{
		    		actionTaken = rsBundle.getString("ACTION_FINAL_BEFORE");
		    	}
		        // get valfrom of "RFR(0040)" for prior &DTFS.      
		    } else if (t2Status.equals(STATUS_FINAL)) {
		    	t1Time = getDeltaT1(adsStatusHistory, statusHistory, STATUS_FINAL);
		        if (t1Time.equals(m_strEpoch)) {
		            actionTaken = rsBundle.getString("ACTION_FINAL_FIRSTTIME");
		        } else {
		            actionTaken = rsBundle.getString("ACTION_FINAL_CHANGES");
		        }
		    }
		} else {
		    if (t2Status.equals(STATUS_R4REVIEW)) {
		        actionTaken = rsBundle.getString("ACTION_R4R_FIRSTTIME");
		    }else if (t2Status.equals(STATUS_FINAL)){
		        actionTaken = rsBundle.getString("ACTION_FINAL_FIRSTTIME");
		    }
		    addDebug("getT1 for " + rootEntity.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
		}
	}

	/**
	 * @param adsStatusHistory
	 * @param statusHistory
	 * @param DTS9
	 * @param fullXML
	 * @return
	 */
	private boolean isFullXML(AttributeChangeHistoryGroup adsStatusHistory,	AttributeChangeHistoryGroup statusHistory, String DTS9) {
		boolean fullXML = false;
		if(statusHistory != null && statusHistory.getChangeHistoryItemCount()>= 1){
			int iHistoryItemCount = statusHistory.getChangeHistoryItemCount();		
			String valFrom = "";
			String valTo   = "";
			for (int i = iHistoryItemCount - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
				if(achi!=null){
					if(STATUS_R4REVIEW.equals(achi.getFlagCode())){
						valFrom = achi.getChangeDate();
						//get the pre achi for the valto time of STATUS_FLAG;
						if(i == iHistoryItemCount - 1){
							valTo = m_strForever;
						}else{
							AttributeChangeHistoryItem beforeachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i+1);
							if(beforeachi!=null){        								
								valTo = beforeachi.getChangeDate();	        							
							}
						}
						if(valFrom.compareTo(DTS9)<0 && valTo.compareTo(DTS9)>0 && existBefore(adsStatusHistory, STATUS_PASSED)==false){
							t1DTS = m_strEpoch;
							fullXML = true;
							break;
						}
					}else if(STATUS_FINAL.equals(achi.getFlagCode())){
						valFrom = achi.getChangeDate();
						//get the next achi for the valfrom time of STATUS_FLAG;
						if(i == iHistoryItemCount - 1){
							valTo = m_strForever;
						}else{
							AttributeChangeHistoryItem beforeachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i+1);
							if(beforeachi!=null){        								
								valTo = beforeachi.getChangeDate();	        							
							}
						}
						if(valFrom.compareTo(DTS9)<0 && valTo.compareTo(DTS9)>0 && existDeltaT1(adsStatusHistory, statusHistory, STATUS_FINAL)==false){
							t1DTS = m_strEpoch;
							fullXML = true;
							break;
						}
					}
				}
			}//end for		
		}//end DTS
		return fullXML;
	}

    /***************************************************************************
     * checking whether has passed queue in ADSABRSTATUS
     */
    private boolean existBefore(AttributeChangeHistoryGroup achg, String value) {
        if (achg != null) {
            for (int i = achg.getChangeHistoryItemCount() - 1; i >= 0; i--) {
                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) achg.getChangeHistoryItem(i);
                if (achi.getFlagCode().equals(value)) {
                    return true;
                }
            }

        }
        return false;
    }
    /***************************************************************************
     * checking whether has passed queue in ADSABRSTATUS
     */
    private int countBefore(AttributeChangeHistoryGroup achg, String value) {
    	int iCount =0;
        if (achg != null) {
            for (int i = achg.getChangeHistoryItemCount() - 1; i >= 0; i--) {
                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) achg.getChangeHistoryItem(i);
                if (achi.getFlagCode().equals(value)) {
                	iCount ++;
                }
            }

        }
        return iCount;
    }

    /**********************************************************************************
     *  BH FS ABR XML System Feed 20130904.doc from Page 43 to Page 53
     *  get the T2 time for the XML Generation
        //case A.	Initialize Cache with Full XML    	
    	 IF the value of 閳ユ反ML IDL ABRSTATUS閳ワ拷 (XMLIDLABRSTATUS) is set to 閳ユ法ueued閳ワ拷 (0020) 
    	  and "System Feed Resend" (SYSFEEDRESEND) is set to "Initialize Cache" (CACHE) THEN
    	   1.	Find the most recent value of 閳ユ穾DS XML Feed ABR閳ワ拷 (ADSABRSTATUS) = 閳ユ阀assed閳ワ拷 (0030)
			2.	IF not found THEN
				a.	Notes: 
					XML was never created for this.
					This is the same as 閳ユ窔nitialize Cache Current閳ワ拷 (CUR).
				b.	DTS2 = VALTO for the most current value of 閳ユ藩tatus閳ワ拷 (STATUS) = {"Final"  (0020) | 閳ユ珐eady for Review閳ワ拷 (0040)}
				c.	IF DTS2 is not found THEN
						1)	Do not create XML
						2)	Produce a report with the following message:
						Error: A request is not valid since the data was never "Ready for Review" or "Final"
						3)	Set ABR return code 閳ユ穾DS XML Feed ABR閳ワ拷 (ADSABRSTATUS) to 閳ユ窋ailed閳ワ拷 (0040)
						4)	Exit
					END IF
				d.	DTS2 = DTS2 閳ワ拷 30 seconds
				e.	T2 = MIN(DTS2, VALFROM of the current value In Process" (0050) for XMLIDLABRSTATUS)
				f.	Extract the data for T2 using the VE defined in the spreadsheet at the end of this document
				g.	Check 閳ユ穾vailability閳ワ拷 (AVAIL)
				h.	Create Full XML using this data
					See the section 閳ユ阀roduction (Triggered)閳ワ拷
				i.	Update Cache with this XML
				j.	Do NOT send this XML to downstream systems.
				k.	Set XMLIDLABRSTATUS = 閳ユ阀assed閳ワ拷 (0030)
			END IF			
			 
			3.	IF found THEN 
				a.	Notes: 
				XML was created for this and probably sent downstream.
				This is 閳ユ窔nitialize Cache閳ワ拷
				The ABR needs to produce Full XML consistent with the last XML produced and update Cache.
				b.	DTS1 = VALFROM of the current value "In Process" (0050) for XMLIDLABRSTATUS
				c.	DTS2 = VALFROM of the prior value (earlier than DTS1) of "Passed"" (0030) for ADSABRSTATUS
				d.	DTS1 = VALFROM of the prior value (earlier than DTS2) of 闁炽儲绐攏 Process闁炽儻鎷�(0050) for ADSABRSTATUS 
				e.	DTS2 = VALTO of the prior value (earlier than DTS1) of {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)} for STATUS
				f.	DTS2 = VALTO - 30 seconds
				g.	T2 = MIN(DTS1 | DTS2) 
				h.	Extract the data for T2 using the VE defined in the spreadsheet at the end of this document
				i.	Check 闁炽儲绌緑ailability闁炽儻鎷�(AVAIL)
				j.	Create Full XML using this data 
					See the section 闁炽儲闃�roduction (Triggered)闁炽儻鎷�
				k.	Update Cache with this XML
				l.	Do NOT send this XML to downstream systems.
				m.	Set XMLIDLABRSTATUS = 闁炽儲闃�assed闁炽儻鎷�(0030)
			END IF
    	 //case B. Initialize Cache Current
    	 IF the value of 闁炽儲鍙峂L IDL ABRSTATUS闁炽儻鎷�(XMLIDLABRSTATUS) is set to 闁炽儲娉晆eued闁炽儻鎷�(0020) and 闁炽儲钘﹜stem Feed Resend闁炽儻鎷�(SYSFEEDRESEND) = 闁炽儲绐攏itialize Cache Current闁炽儻鎷�(CUR) THEN
			1.	DTS2 = VALTO for most current value of {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)} for STATUS
			2.	If DTS2 not found THEN
					a.	Do not create XML
					b.	Produce a report with the following message:
						Error: A request is not valid since the data was never "Ready for Review" or "Final"
					c.	Set ABR return code to 闁炽儲绐媋iled闁炽儻鎷�(0040)
					d.	Exit
				END IF
			3.	DTS2 = DTS2 - 30 seconds	
			4.	T2 = MIN(DTS2, VALFROM of the current value of "In Process" (0050) for XMLIDLABRSTATUS
			5.	Extract the data for T2 using the VE defined in the spreadsheet at the end of this document 
			6.	Check 闁炽儲绌緑ailability闁炽儻鎷�(AVAIL)
			7.	Create Full XML using this data 
				See the section 闁炽儲闃�roduction (Triggered)闁炽儻鎷�
			8.	Update Cache with this XML
			9.	Do NOT send this XML to downstream systems.
			10.	Set ADSTATUSVAR = 闁炽儲闃�assed闁炽儻鎷�(0030)
    	//case C. Production (Triggered)
    	 IF the value of 闁炽儲绌綝S XML Feed ABR闁炽儻鎷�(ADSABRSTATUS) is set to 闁炽儲娉晆eued闁炽儻鎷�(0020) and 闁炽儲钘﹜stem Feed Resend闁炽儻鎷�(SYSFEEDRESEND) = 闁炽儲缃歰闁炽儻鎷�(No) THEN
    	    1.	DTS1 = VALFROM of current value 闁炽儲绐攏 Process闁炽儻鎷�(0050) for ADSABRSTATUS
			2.	DTS2 = VALTO for most current value (VALFROM earlier that DTS1) of {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)} for STATUS			
			3.	If DTS2 not found THEN 
				a.	Do not create XML
				b.	Produce a report with the following message:
				Error: A request is not valid since the data was never "Ready for Review" or "Final"
				c.	Set ABR return code to 闁炽儲绐媋iled闁炽儻鎷�(0040)
				d.	Exit
				END IF			
			4.	DTS2 = DTS2 闁炽儻鎷�30 seconds			
			5.	T2 = MIN(DTS1 | DTS2)			
			6.	Extract the data for T2 using the VE defined in the spreadsheet at the end of this document			
			7.	Check 闁炽儲绌緑ailability闁炽儻鎷�(AVAIL)			
			8.	Create Full XML using this data  			
			9.	Update Cache with this XML
	    //case D. Resend 
    	 IF the value of 闁炽儲绌綝S XML Feed ABR闁炽儻鎷�(ADSABRSTATUS) is set to 闁炽儲娉晆eued闁炽儻鎷�(0020) and 闁炽儲钘﹜stem Feed Resend闁炽儻鎷�(SYSFEEDRESEND) = 闁炽儲鐝恊send闁炽儻鎷�(Yes) THEN
			This is the same as the preceding section on 闁炽儲闃�roduction (Triggered)闁炽儻鎷穡hen sending 闁炽儲绐媢ll闁炽儻鎷稾ML. It never sends 闁炽儲绐塭lta闁炽儻鎷稾ML.
		 END IF
    	//case E. Resend Cache
    	IF the value of 闁炽儲绌綝S XML Feed ABR闁炽儻鎷�(ADSABRSTATUS) is set to 闁炽儲娉晆eued闁炽儻鎷�(0020) and 闁炽儲钘﹜stem Feed Resend闁炽儻鎷�(SYSFEEDRESEND) = 闁炽儲鐝恊send Cache闁炽儻鎷�(CACHE) THEN
			IF the XML message is NOT available in CACHE THEN
				This is the same as the preceding section on 闁炽儲闃�roduction (Triggered)闁炽儻鎷穡hen sending 闁炽儲绐媢ll闁炽儻鎷稾ML. It never sends 闁炽儲绐塭lta闁炽儻鎷稾ML.
			ELSE
				Use the XML found in Cache and send it to downstream systems.
				Do NOT update cache
				Set ADSABRSTATUS = 闁炽儲闃�assed Resend Cache闁炽儻鎷�(XMLCACHE)
			END IF		
		End of Section E
    	//case F. Resend RFR
    	IF the value of 闁炽儲绌綝S XML Feed ABR闁炽儻鎷�(ADSABRSTATUS) is set to 闁炽儲娉晆eued闁炽儻鎷�(0020) and 闁炽儲钘﹜stem Feed Resend闁炽儻鎷�(SYSFEEDRESEND) = 闁炽儲鐝恊send RFR闁炽儻鎷�(RFR)
    	   1.IF root entity STATUS <> 闁炽儲绐媔nal闁炽儻鎷�(0020) THEN
				a.	Do NOT create XML
				b.	Produce a report with the following message:
				Error: A 闁炽儲鐝恊send RFR闁炽儻鎷穜equest is not valid since the data must be "Final".
				c.	Set ABR return code to 闁炽儲绐媋iled闁炽儻鎷�(0040)
				d.	Exit
			 END IF
				
			2. IF STATUS = "Final" (0020) AND "ADS XML Feed ABR" (ADSABRSTATUS) was ever "Passed" (0030) THEN 
				a.	Do NOT create XML
				b.	Produce a report with the following message:
					Error: A 闁炽儲鐝恊send RFR闁炽儻鎷穜equest is not valid since XML was previously created successfully.
				c.	Set ABR return code to 闁炽儲绐媋iled闁炽儻鎷�(0040)
				d.	Exit
			END IF
				
			3.IF STATUS = "Final" (0020) AND "ADS XML Feed ABR" (ADSABRSTATUS) was never "Passed" (0030) THEN
				a.	Find the last time STATUS = Ready for Review"" (0040) just prior to the first time that STATUS = "Final""(0020)"
				b.	Set T2 = VALTO of this STATUS = "Ready for Review" (0040)"
				c.	Set T2 = T2 - 30 seconds
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    private void setT2DTS(EntityItem rootEntity, XMLMQ mqAbr, AttributeChangeHistoryGroup adsStatusHistory,
            AttributeChangeHistoryGroup statusHistory, String dtfs, AttributeChangeHistoryGroup xmlIDLStatusHistory) 
    throws MiddlewareException, SQLException {    	
    	//set T2 start    	
    	String sysfeedFlag = getAttributeFlagEnabledValue(rootEntity, "SYSFEEDRESEND");
    	addDebug("sysfeedFlag= " + sysfeedFlag);
		//String ADSABRSTATUS = getAttributeFlagEnabledValue(rootEntity, "ADSABRSTATUS");
    	if (isXMLIDLABR && isSystemResendCurrent){  
    		addDebug("CASE B Initialize Cache Current ");
			//DTS2 = VALTO for the most current value of 闁炽儲钘﹖atus闁炽儻鎷�(STATUS) = {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)}    			
			String DTS2 = getValtoInStatusHistory(rootEntity, statusHistory, null);
			//DTS1 = VALFROM of the current value In Process" (0050) for XMLIDLABRSTATUS    			 
			String DTS1 = getValFromInStatusHistory("xmlIDLStatusHistory", xmlIDLStatusHistory, STATUS_CHGREQ, null);
			//T2 = MIN(DTS1, DTS2)
			t2DTS = getMinTime(DTS1, DTS2);
			addDebug("CASE B Initialize Cache Current T2=" + t2DTS);
    	} else if (isXMLIDLABR && isXMLCACHE ){
    		if(!existBefore(adsStatusHistory, STATUS_PASSED)){
    			addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is not found ");
    			//not foud
    			//DTS2 = VALTO for the most current value of 闁炽儲钘﹖atus闁炽儻鎷�(STATUS) = {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)}    			
    			String DTS2 = getValtoInStatusHistory(rootEntity, statusHistory, null);
    			//DTS1 = VALFROM of the current value In Process" (0050) for XMLIDLABRSTATUS    			 
    			String DTS1 = getValFromInStatusHistory("xmlIDLStatusHistory", xmlIDLStatusHistory, STATUS_CHGREQ, null);
    			//T2 = MIN(DTS1, DTS2)
    			t2DTS = getMinTime(DTS1, DTS2); 
    			addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is not found T2=" + t2DTS);
    		}else{   
    			addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is found ");
    			//DTS1 = VALFROM of the current value "In Process" (0050) for XMLIDLABRSTATUS
    			String DTS1 = getValFromInStatusHistory("xmlIDLStatusHistory", xmlIDLStatusHistory, STATUS_CHGREQ, null);
    			//DTS2 = VALFROM of the prior value (valfrom earlier than DTS1) of "Passed" (0030) for ADSABRSTATUS
    			String DTS2 = getValFromInStatusHistory("adsStatusHistory", adsStatusHistory, STATUS_PASSED, DTS1);
    			//DTS1 = VALFROM of the prior value (valfrom earlier than DTS2) of 闁炽儲绐攏 Process闁炽儻鎷�(0050) for ADSABRSTATUS
    			DTS1 =  getValFromInStatusHistory("adsStatusHistory", adsStatusHistory, STATUS_CHGREQ, DTS2);
    			//DTS2 = VALTO of the prior value (VALFROM earlier than DTS1) of {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)} for STATUS
    			DTS2 =  getValtoCompareValFromInStatusHistory(rootEntity, statusHistory, DTS1);
    			t2DTS = getMinTime(DTS1, DTS2);
    			addDebug("CASE A Initialize Cache (ADSABRSTATUS) = Passed (0030) is found T2=" + t2DTS);
    		}
    	} else if(!isXMLIDLABR && isPeriodicABR){
    		//Defect 743004  CR xxxx - Change XML design for updated handling of T1 and T2 in full and delta XML generation - DCUT
        	//New Update --Change XML design for updated handling of T1 and T2 in full and delta XML generation
        	//i-1 is 0050;  i-2 is 0020;  i-3 is 0030 or others 
        	if(isPeriodicABR){   
        		//TODO get the T2 time by query the db
        		t2DTS = this.getEntityT2DTS(rootEntity);
        		if(t2DTS==null){
        			t2DTS = getNow();
        		}
        		
//        		if (adsStatusHistory != null && adsStatusHistory.getChangeHistoryItemCount() > 1){
//        			int i = adsStatusHistory.getChangeHistoryItemCount();
//        			AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(i - 1);
//                    if (achi != null) {
//                        addDebug("getT2Time [" + (i - 1) + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
//                                + " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
//                        if (achi.getFlagCode().equals(STATUS_CHGREQ)) {
//                            t2DTS = achi.getChangeDate();
//                            addDebug("For PeriodicABR: getT2Time from changedate 0050:"+ t2DTS);
//                            //t2DTS = adjustTimeSecond(t2DTS, 40);
//                        } else {
//                        	addDebug("getT2Time for the value of " + achi.getFlagCode()
//                                    + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
//                            t2DTS = getNow();
//                        }
//                    }        			
//        		}else{
//        			t2DTS = getNow(); 
//        		}       		        		
        	}    		
    	} else if(isXMLADSABR){
    		if(SYSFEEDRESEND_RFR.equals(sysfeedFlag)){
    			addDebug("CASE F Resend RFR");
    			String statusAttr = mqAbr.getStatusAttr();
                String statusFlag = getAttributeFlagEnabledValue(rootEntity, statusAttr);
                if (!STATUS_FINAL.equals(statusFlag)){
                    addError("A \"Resend RFR\" request is not valid since the data must be \"Final\".", rootEntity);                    
                }else{
                	if(existBefore(adsStatusHistory, STATUS_PASSED)){
                		addError("A \"Resend RFR\" request is not valid since XML was previously created successfully.", rootEntity);
                	}else{
                		setRFRT2DTS(rootEntity, statusHistory);                	
                	}
                }
                addDebug("CASE F Resend RFR T2=" + t2DTS);    			
    		}else{
    			//processSystemResendRFR and  processSystemResend have handled the difference for the  Resend & Resend Cache
    			addDebug("CASE C Production & D Resend & E Resend Cache");
    			//DTS1 = VALFROM of current value 闁炽儲绐攏 Process闁炽儻鎷�(0050) for ADSABRSTATUS
    			String DTS1 = getValFromInStatusHistory("adsStatusHistory", adsStatusHistory, STATUS_CHGREQ, null);
    			//DTS2 = VALTO for most current value (VALFROM earlier that DTS1) of {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)} for STATUS
    			String DTS2 = getValtoCompareValFromInStatusHistory(rootEntity, statusHistory, DTS1);
    			t2DTS = getMinTime(DTS1, DTS2);
    			addDebug("CASE C Production & D Resend & E Resend Cache T2=" + t2DTS);    			
    		} 
    	} else  {
    		addError("There is no such case for the entity.", rootEntity);
    	}   	
    	//set IDL status
    	if (isXMLIDLABR) {  		
    		setIDLSTATUS(rootEntity, statusHistory);
    		addDebug("setIDLSTATUS");
    	}
    	//set T2 end 
    }  

	/**
	 * BH FS ABR XML System Feed 20130904.doc  Page 52
	 * 3. IF STATUS = "Final" (0020) AND "ADS XML Feed ABR" (ADSABRSTATUS) was never "Passed" (0030) THEN
	 * 		a. Find the last time STATUS = Ready for Review"" (0040) just prior to the first time that STATUS = "Final""(0020)"
	 * 		b. Set T2 = VALTO of this STATUS = "Ready for Review" (0040)"
	 * 		c. Set T2 = T2 - 30 seconds
	 * @param rootEntity
	 * @param statusHistory
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void setRFRT2DTS(EntityItem rootEntity, AttributeChangeHistoryGroup statusHistory) throws SQLException,
			MiddlewareException {
		//set T2
		if(statusHistory != null && statusHistory.getChangeHistoryItemCount()>=1){
			int historyCount = statusHistory.getChangeHistoryItemCount();
			int allCount = countBefore(statusHistory, STATUS_FINAL);
			if(allCount>0){	
				boolean foundFinal    = false;
				boolean foundR4review = false;
				int finalCount = 0;
				for (int i = historyCount - 1; i >= 0; i--) {
					AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
					if (!foundFinal && achi != null && achi.getFlagCode().equals(STATUS_FINAL)) {
						finalCount ++;
						if(finalCount==allCount){
						    foundFinal = true;
						}         									
					}
					if(achi != null && achi.getFlagCode().equals(STATUS_R4REVIEW) && foundFinal){
						//Set T2 = VALTO of this STATUS = 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)						
						AttributeChangeHistoryItem nextachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i + 1);
						if (nextachi != null) {
							t2DTS = nextachi.getChangeDate();
							//Set T2 = T2 闁炽儻鎷�30 seconds
							t2DTS = adjustTimeSecond(t2DTS,-30);
							foundR4review = true;
                            priorStatus = "Ready for Review";
							break;
						}else{
							addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status has no prior history" , rootEntity);
						}											
					}
				}
				if(!foundR4review){
					addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status ever being Final but not ever being RFR" , rootEntity);
				}        					
			}else{
				addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status never being RFR or Final" , rootEntity);
			}			
		}else{
			addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status has no history and never being RFR or Final" , rootEntity);
		}
		addDebug("In the setRFRT2DTS function, the T2 value is " + t2DTS);		
		//end set t2 for RFR
	}

	/**
	 * get the VALTO for the most current value of 闁炽儲钘﹖atus闁炽儻鎷�(STATUS) = {"Final"  (0020) | 闁炽儲鐝恊ady for Review闁炽儻鎷�(0040)} 
	 * get thte VALTO value of the achi, we need get the valfrom of nextachi which is the same value with the valto value of this achi 
	 * @param rootEntity
	 * @param statusHistory
	 * @param minVALTONumber
	 * @param DTS2
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String getValtoInStatusHistory(EntityItem rootEntity, AttributeChangeHistoryGroup statusHistory , String givingTime) 
		throws SQLException, MiddlewareException {
		String DTS = null;
		String valto = "";
		boolean existFinalOrRFR = false;
		if(statusHistory != null && statusHistory.getChangeHistoryItemCount()>=1){
			int iHistoryItemCount = statusHistory.getChangeHistoryItemCount();
			for (int i = iHistoryItemCount - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
				if(achi!=null){
					if(STATUS_FINAL.equals(achi.getFlagCode()) || STATUS_R4REVIEW.equals(achi.getFlagCode())){
						existFinalOrRFR = true;
						if(i==iHistoryItemCount - 1){
							valto = m_strForever;							
							if(givingTime == null){
								DTS = valto;
								break;
							}else{
								//(earlier than givingTime)									
								if(valto.compareTo(givingTime)<0){
									DTS = valto;									
									break;									
								}
							}	
						}else{
							AttributeChangeHistoryItem nextachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i+1);
							if (nextachi != null) {		
								valto = nextachi.getChangeDate();
								if(givingTime == null){
									DTS = valto;
									DTS = adjustTimeSecond(DTS,-30);
									break;
								}else{
									//(earlier than givingTime)									
									if(valto.compareTo(givingTime)<0){
										DTS = valto;
										DTS = adjustTimeSecond(DTS,-30);									
										break;									
									}
								}							
							}
						}				
					}
				}
			}    					
			if(!existFinalOrRFR){
				addError("Error: A request is not valid since the data was never \"Ready for Review\" or \"Final\"" , rootEntity);
			}
		}else{
			addError("Error: A request is not valid since the status has no history." , rootEntity);
		}
		addDebug("The VALTO - 30s value for the most current value of STATUS = {Final(0020) |Ready for Review(0040)} is " + DTS);
		return DTS;
	}
	
	/**
	 * get the valto time which the valfrom is early that the givingTime when the status is 0020 or 0040
	 * @param rootEntity
	 * @param statusHistory
	 * @param givingTime
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private String getValtoCompareValFromInStatusHistory(EntityItem rootEntity, AttributeChangeHistoryGroup statusHistory, String givingTime) 
	throws SQLException, MiddlewareException {
	String DTS = null;
	String valTo = "";
	String valFrom = "";
	boolean existFinalOrRFR = false;
	if(statusHistory != null && statusHistory.getChangeHistoryItemCount()>=1){
		int iHistoryItemCount = statusHistory.getChangeHistoryItemCount();
		for (int i = iHistoryItemCount - 1; i >= 0; i--) {
			AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
			if(achi!=null){
				if(STATUS_FINAL.equals(achi.getFlagCode()) || STATUS_R4REVIEW.equals(achi.getFlagCode())){
					existFinalOrRFR = true;
					valFrom = achi.getChangeDate();
					if(valFrom.compareTo(givingTime)<0){
						if(i==iHistoryItemCount - 1){
							valTo = m_strForever;
							DTS = valTo;
							break;
						}else{
							AttributeChangeHistoryItem nextachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i+1);
							if (nextachi != null) {		
								valTo = nextachi.getChangeDate();
								DTS = valTo;
								DTS = adjustTimeSecond(DTS,-30);
								break;						
							}
						}						
					}									
				}
			}
		}    					
		if(!existFinalOrRFR){
			addError("Error: A request is not valid since the data was never \"Ready for Review\" or \"Final\"" , rootEntity);
		}
	}else{
		addError("Error: A request is not valid since the status has no history." , rootEntity);
	}
	addDebug("The VALTO - 30s value for the most current value of STATUS = {Final(0020) |Ready for Review(0040)} is " + DTS);
	return DTS;
}
	/**
	 * get the DTS of the xStatusHistoy at the STATUS_FLAG
	 * where the  fromTime<DTS< toTime 
	 * @param xStatusHistory
	 * @param STATUS_FLAG
	 * @param minVALFROMNumber
	 * @param givingTime
	 * @return
	 */
	private String getValFromInStatusHistory(AttributeChangeHistoryGroup xStatusHistory, String STATUS_FLAG, String fromTime, String toTime) {
		String DTS = null;
		String valfrom = "";
		if(xStatusHistory != null && xStatusHistory.getChangeHistoryItemCount()>= 1){
			int iHistoryItemCount = xStatusHistory.getChangeHistoryItemCount();					
			for (int i = iHistoryItemCount - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) xStatusHistory.getChangeHistoryItem(i);
				if(achi!=null){
					if(STATUS_FLAG.equals(achi.getFlagCode())){
						//get the achi for the valfrom time of STATUS_FLAG;												
						if(fromTime != null && toTime!=null){ 
							//(earlier than givingTime)
							valfrom = achi.getChangeDate();
							if(valfrom.compareTo(fromTime)>0 && valfrom.compareTo(toTime)<0){
								DTS = achi.getChangeDate();
								break;									
							}
						}
					}
				}
			}//end for		
		}//end DTS
		addDebug("The valfrom value of the StatusHistory at " + STATUS_FLAG + " which is beteween " + fromTime + " and " + toTime + " is " + DTS);
		return DTS;
	}

	/**
	 * get the DTS of the xStatusHistoy at the STATUS_FLAG
	 * if givingTime is not null, then the DTS need earlier than givingTime.
	 * @param xStatusHistory
	 * @param STATUS_FLAG
	 * @param minVALFROMNumber
	 * @return
	 */
	private String getValFromInStatusHistory(String historyType,AttributeChangeHistoryGroup xStatusHistory, String STATUS_FLAG ,String givingTime) {
		String DTS = null;
		String temp = "";
		if(xStatusHistory != null && xStatusHistory.getChangeHistoryItemCount()>= 1){
			int iHistoryItemCount = xStatusHistory.getChangeHistoryItemCount();					
			for (int i = iHistoryItemCount - 1; i >= 0; i--) {
				AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) xStatusHistory.getChangeHistoryItem(i);
				if(achi!=null){
					if(STATUS_FLAG == null){
						if(STATUS_FINAL.equals(achi.getFlagCode()) || STATUS_R4REVIEW.equals(achi.getFlagCode())){
							//get the achi for the valfrom time of STATUS_FLAG;
							temp = achi.getChangeDate();
							if(givingTime == null){
								DTS = temp;
								break;
							}else{
								//(earlier than givingTime)
								if(temp.compareTo(givingTime)<0){	
									DTS = temp;
									break;									
								}
							}
						}
					}else{
						if(STATUS_FLAG.equals(achi.getFlagCode())){
							//get the achi for the valfrom time of STATUS_FLAG;
							temp = achi.getChangeDate();
							if(givingTime == null){
								DTS = temp;
								break;
							}else{
								//(earlier than givingTime)
								if(temp.compareTo(givingTime)<0){
									DTS = temp;
									break;									
								}
							}
						}
					}
					
				}
			}//end for		
		}else{
			addError("Error: A request is not valid since there is no history of " + historyType);
		}//end DTS
		addDebug("The valfrom value of the "+ historyType + " at " + ((STATUS_FLAG==null)? STATUS_FINAL +"|" + STATUS_R4REVIEW : STATUS_FLAG) 
				+ " which earlier than the givingTime: " + givingTime + " is " + DTS);
		return DTS;
	}

	/**
	 * @param rootEntity
	 * @param statusHistory
	 */
	private void setIDLSTATUS(EntityItem rootEntity,
			AttributeChangeHistoryGroup statusHistory) {
		EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("STATUS");           
		if (metaAttr != null) {
			boolean exist_R4REVIEW_FINAL = false;
			for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
	            AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
	            if (achi != null && (STATUS_FINAL.equals(achi.getFlagCode())|| STATUS_R4REVIEW.equals(achi.getFlagCode()))) {
	                curStatus = achi.getAttributeValue();
	                curStatusvalue = achi.getFlagCode();
	                AttributeChangeHistoryItem priorachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i - 1);
	                // set prior Status at the xml header. 
	                if (priorachi != null) {
	                    priorStatus = priorachi.getAttributeValue();
	                    addDebug("priorStatus [" + (i - 1) + "] chgdate: " + priorachi.getChangeDate() + " flagcode: "
	                            + priorachi.getFlagCode());
	                }
	                exist_R4REVIEW_FINAL = true;
	                break;
	            }
	        }
			if(!exist_R4REVIEW_FINAL){
				addError(rootEntity.getKey() + ", "+ rsBundle.getString("IDL_NOT_R4RFINAL"));
			}		    
		} else {
		    addError(rootEntity.getKey() + " , Error: There is not such attribute STATUS." );
		}
	}

    /*
     * Get the history of the ABR (ADSABRSTATUS) in VALFROM order The current
     * value should be In Process?? (0050) The prior value should be Queued??
     * (0020) Find the prior value for &DTFS if it is not null TQ = VALFROM of
     * this row T2 = TQ look into the STATUS history find the value at T2.
     * return STATUS at T2
     */
    private String getT2Status(AttributeChangeHistoryGroup statusHistory)
    throws SQLException, MiddlewareException {
        String status = "";
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
            // last chghistory is the current one
            for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
                if (achi!=null){
                    //addDebug("getT2Status [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
                    //          + " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
                    // Because the time stamp of Status will be prior to T2, find the first item which time stamp less than T2. 
                    if (achi.getChangeDate().compareTo(t2DTS) < 0) {
                        // If Status is not Final or RFR, then addError and break.
                        if (!STATUS_FINAL.equals(achi.getFlagCode()) && !STATUS_R4REVIEW.equals(achi.getFlagCode())) {
                        	addError(rootEntity.getKey() + " is not Final or R4R");
                            addError(rsBundle.getString("NOT_R4RFINAL"),rootEntity);
                            break;
                        }else{
                            curStatus = achi.getAttributeValue();
                            curStatusvalue = achi.getFlagCode();
                            status = achi.getFlagCode();
                            achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i - 1);
                            // set prior Status at the xml header. 
                            if (achi != null) {
                                priorStatus = achi.getAttributeValue();
                                addDebug("getT2Status [" + (i - 1) + "] chgdate: " + achi.getChangeDate() + " flagcode: "
                                        + achi.getFlagCode());
                            }
                            break;
                        }
                    }
                }
            }
        }else{
            addDebug("getT2Status for " + rootEntity.getKey() + " getChangeHistoryItemCount less than 0.");
        }
        return status;
    }

    /**
     * find If this value STATUS = "Final"  (0020) AND there is no prior value of STATUS = "Final" for which ADSABRSTATUS is "Passed" (0030)
     * @param adsStatusHistory
     * @param statusHistory
     * @param tostatus
     * @return
     */
    private boolean existDeltaT1(AttributeChangeHistoryGroup adsStatusHistory, AttributeChangeHistoryGroup statusHistory, String tostatus)
    {
    	String T1 = m_strEpoch;
    	String DTS1 = null;
    	String DTS2 = null;
    	String T_CHGREQ = "";
    	AttributeChangeHistoryItem achi,adsachi;
    	AttributeChangeHistoryItem nextachi,nextadsachi;
    	
    	boolean foundT1 = false;
    	
    	if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
            // last chghistory is the current one
    		for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
                achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i-1);
                if (achi != null) {
                    //addDebug("getTQStatus [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
                    //  + " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
                    if (achi.getFlagCode().equals(tostatus)) {
                    	//DTS1 is valfrom of tostatus
                    	DTS1 = achi.getChangeDate();                    	
                    	nextachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
						if (nextachi != null) {
							//DTS2 is valto of the tostatus
							DTS2 = nextachi.getChangeDate();
							//Set DTS2 = DTS2 闁炽儻鎷�30 seconds
	                    	DTS2 = adjustTimeSecond(DTS2,-30);
						}else{
							addDebug("existDeltaT1 for STATUS has no valto value of the stauts of " + tostatus);
						}
						addDebug("getDeltaT1 DTS1= " + DTS1 + " and  DTS2 = " + DTS2);
						//Let T1 equal to the VALFROM of  the first ADSABRSTATUS = 闁炽儲绐攏 process闁炽儻鎷�(0050) where DTS1 <= VALFROM <= DTS2
						if(DTS2 != null &&  DTS1!= null){
			            	if (adsStatusHistory != null && adsStatusHistory.getChangeHistoryItemCount() > 0) {
			            		for (int j = adsStatusHistory.getChangeHistoryItemCount() - 1; j >= 0; j--) {
			            			adsachi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(j-1);
			                        if (adsachi != null) {
			                        	if (adsachi.getFlagCode().equals(STATUS_CHGREQ)) {
			                        		//check whether the prior ADSABRSTATUS is pass before the STATUS_CHGREQ(0050)  
			                        		nextadsachi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(j);
			                        		if(nextadsachi.getFlagCode().equals(STATUS_PASSED)){
			                        			T_CHGREQ = adsachi.getChangeDate();
			                            		if(T_CHGREQ.compareTo(DTS1) >= 0 && T_CHGREQ.compareTo(DTS2)<=0 ){
			                            			T1 = T_CHGREQ;
			                            			foundT1 = true;
			                            			break;
			                            		}
			                        		}else{
			                        			continue;
			                        		}                        		                        		
			                        	}
			                        }
			            		}            		
			            	}else{
			            		addDebug("getDeltaT1 for ADSABRSTATUS has no changed history!");
			            	}            	
			            }
						//end
						if(foundT1)	{
							break;
						}
                    }
                }
            }
    		
            
        } else {
            addDebug("getDeltaT1 for STATUS has no changed history!");
        }
    	if(T1.equals(m_strEpoch)){
    		addDebug("getDeltaT1 not find the VALFROM of the first ADSABRSTATUS = 闁炽儲绐攏 process闁炽儻鎷�(0050) where DTS1 <= VALFROM <= DTS2");
    	}    	
    	return foundT1;
    }
    
    /**
     * BH FS ABR XML System Feed 20121009b.doc
     * 1. Starting at the current value of STATUS = 闁炽儲鐝恊ady for Review/Final闁炽儻鎷�(0040/0020), Let DTS1 equal to VALFROM and DTS2 = VALTO of 
     * the prior value of  STATUS = 闁炽儲鐝恊ady for Review/Final闁炽儻鎷�(0040/0020) 
     * 2. Set DTS2 = DTS2 闁炽儻鎷�30 seconds
     * Note: this supports Revision Number 5
     * 3. Let T1 equal to the VALFROM of  the first ADSABRSTATUS = 闁炽儲绐攏 process闁炽儻鎷�(0050) where DTS1 <= VALFROM <= DTS2
     */
    private String getDeltaT1(AttributeChangeHistoryGroup adsStatusHistory, AttributeChangeHistoryGroup statusHistory, String tostatus)
    {
    	String T1 = m_strEpoch;
    	String DTS1 = null;
    	String DTS2 = null;
    	String T_CHGREQ = "";
    	AttributeChangeHistoryItem achi,adsachi;
    	AttributeChangeHistoryItem nextachi,nextadsachi;
    	
    	boolean foundT1 = false;
    	
    	if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
            // last chghistory is the current one
    		for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
                achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i-1);
                if (achi != null) {
                    //addDebug("getTQStatus [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
                    //  + " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
                    if (achi.getFlagCode().equals(tostatus)) {
                    	//DTS1 is valfrom of tostatus
                    	DTS1 = achi.getChangeDate();                    	
                    	nextachi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
						if (nextachi != null) {
							//DTS2 is valto of the tostatus
							DTS2 = nextachi.getChangeDate();
							//Set DTS2 = DTS2 闁炽儻鎷�30 seconds
	                    	DTS2 = adjustTimeSecond(DTS2,-30);
						}else{
							addDebug("getDeltaT1 for STATUS has no valto value of the stauts of " + tostatus);
						}
						addDebug("getDeltaT1 DTS1= " + DTS1 + " and  DTS2 = " + DTS2);
						//Let T1 equal to the VALFROM of  the first ADSABRSTATUS = 闁炽儲绐攏 process闁炽儻鎷�(0050) where DTS1 <= VALFROM <= DTS2
						if(DTS2 != null &&  DTS1!= null){
			            	if (adsStatusHistory != null && adsStatusHistory.getChangeHistoryItemCount() > 0) {
			            		for (int j = adsStatusHistory.getChangeHistoryItemCount() - 1; j >= 0; j--) {
			            			adsachi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(j-1);
			                        if (adsachi != null) {
			                        	if (adsachi.getFlagCode().equals(STATUS_CHGREQ)) {
			                        		//check whether the prior ADSABRSTATUS is pass before the STATUS_CHGREQ(0050)  
			                        		nextadsachi = (AttributeChangeHistoryItem) adsStatusHistory.getChangeHistoryItem(j);
			                        		if(nextadsachi.getFlagCode().equals(STATUS_PASSED)){
			                        			T_CHGREQ = adsachi.getChangeDate();
			                            		if(T_CHGREQ.compareTo(DTS1) >= 0 && T_CHGREQ.compareTo(DTS2)<=0 ){
			                            			T1 = T_CHGREQ;
			                            			foundT1 = true;
			                            			break;
			                            		}
			                        		}else{
			                        			continue;
			                        		}                        		                        		
			                        	}
			                        }
			            		}            		
			            	}else{
			            		addDebug("getDeltaT1 for ADSABRSTATUS has no changed history!");
			            	}            	
			            }
						//end
						if(foundT1)	{
							break;
						}
                    }
                }
            }
    		
            
        } else {
            addDebug("getDeltaT1 for STATUS has no changed history!");
        }
    	if(T1.equals(m_strEpoch)){
    		addDebug("getDeltaT1 not find the VALFROM of the first ADSABRSTATUS = 闁炽儲绐攏 process闁炽儻鎷�(0050) where DTS1 <= VALFROM <= DTS2");
    	}    	
    	return T1;
    }
    


    // get valfrom of status Final or R4R at TQ time.
    private String getTQStatus(AttributeChangeHistoryGroup statusHistory, String tqtime)
    throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException {
        if (statusHistory != null && statusHistory.getChangeHistoryItemCount() > 0) {
            // last chghistory is the current one
            for (int i = statusHistory.getChangeHistoryItemCount() - 1; i >= 0; i--) {
                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem) statusHistory.getChangeHistoryItem(i);
                if (achi != null) {
                    //addDebug("getTQStatus [" + i + "] isActive: " + achi.isActive() + " isValid: " + achi.isValid()
                    //  + " chgdate: " + achi.getChangeDate() + " flagcode: " + achi.getFlagCode());
                    if (tqtime.compareTo(achi.getChangeDate()) > 0) {
                        return achi.getFlagCode();
                    }
                }
            }
        } else {
            addDebug("getTQStatus for STATUS has no changed history!");
        }
        return CHEAT;
    }

    // set instance variable ADSABRSTATUSHistory and XMLIDLABRSTATUSHistory
    private AttributeChangeHistoryGroup getADSABRSTATUSHistory(String attCode) throws MiddlewareException {
        //String attCode = "ADSABRSTATUS";
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

        EANAttribute att = rootEntity.getAttribute(attCode);
        if (att != null) {
            return new AttributeChangeHistoryGroup(m_db, m_prof, att);
        } else {
            addDebug( attCode + " of "+rootEntity.getKey()+ "  was null");
            return null;
        }
    }
    
    /**
	 * get the Minimal time of the two time
	 * @param timeI
	 * @param timeII
	 * @return
	 */
	private String getMinTime(String timeI, String timeII){
		if(timeI!= null){
			if(timeII!=null){
				return timeI.compareTo(timeII)>0 ? timeII: timeI;
			}else{
				return timeI;
			}
		}else{
			if(timeII!=null){
				return timeII;
			}else{
				return null;
			}
		}
	}

    // set instance variable ADSABRSTATUSHistory
    private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ mqAbr) throws MiddlewareException {
        String attCode = mqAbr.getStatusAttr();
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
        EANAttribute att = rootEntity.getAttribute(attCode);
        if (att != null) {
            return new AttributeChangeHistoryGroup(m_db, m_prof, att);
        } else {
            addDebug(" STATUS of "+rootEntity.getKey()+ "  was null");
            return null;
        }
    }

//  checking whether has passed final in Status AttributeHistoryGroup before.
    private boolean existPassedFinal(AttributeChangeHistoryGroup adsStatusHistory,AttributeChangeHistoryGroup statusHistory)
    throws COM.ibm.opicmpdh.middleware.MiddlewareRequestException{
        boolean nextQueued = false;
        boolean existPassedFinal = false;
        if (adsStatusHistory != null){
            for (int i=adsStatusHistory.getChangeHistoryItemCount()-3; i>=0; i--)
            {
                AttributeChangeHistoryItem achi = (AttributeChangeHistoryItem)adsStatusHistory.getChangeHistoryItem(i);
                if (achi != null){
//                    addDebug("existPassedFinal ["+i+"] isActive: "+
//                            achi.isActive()+" isValid: "+achi.isValid()+" chgdate: "+
//                            achi.getChangeDate()+" flagcode: "+achi.getFlagCode());
                    if (achi.getFlagCode().equals(STATUS_PASSED)){ //Passed
                        // get previous queued
                        nextQueued = true;
                    }
                    if (nextQueued && achi.getFlagCode().equals(STATUS_QUEUE)){ //can't use &DTFS. you can not make sure it is &DTFS last time.
                        String status = getTQStatus(statusHistory,achi.getChangeDate());
                        if(status.equals(STATUS_FINAL)){
                            existPassedFinal = true;
                            break;
                        } else {
                            nextQueued = false;
                        }                   
                    }
                }
            }
//          last chghistory is the current one(in process), -2 is queued, -3 is &DTFS / passed  
        }   
        return existPassedFinal;
    }
    /**********************************************************************************
     * Checks if the current VE is subject to filtering.
     */
    private boolean isVEFiltered(String name) {
        for (int i = 0; i < VE_Filter_Array.length; i++) {
            if (VE_Filter_Array[i][0].equals(name))
                return true;
        }
        return false;

    }
    /**********************************************************************************
     * Checks if the current VE is subject to country filtering.
     */
    private boolean isVECountryFiltered(String name) {
        for (int i = 0; i < VE_Country_Filter_Array.length; i++) {
            if (VE_Country_Filter_Array[i][0].equals(name))
                return true;
        }
        return false;

    }

    // role must have access to all attributes
    private Profile switchRole(String roleCode)
    throws COM.ibm.eannounce.objects.EANBusinessRuleException,
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    java.rmi.RemoteException,
    IOException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Profile profile2 = m_prof.getProfileForRoleCode(m_db, roleCode, roleCode, 1);
        if (profile2==null) {
            addError("Could not switch to "+roleCode+" role");
        }else {
            addDebug("Switched role from "+m_prof.getRoleCode()+" to "+profile2.getRoleCode());

            String nlsids = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getNLSIDs(m_abri.getABRCode());
            addDebug("switchRole nlsids: "+nlsids);
            StringTokenizer st1 = new StringTokenizer(nlsids,",");
            while (st1.hasMoreTokens()) {
                String nlsid = st1.nextToken();
                NLSItem nlsitem = (NLSItem)READ_LANGS_TBL.get(nlsid);
                if (!profile2.getReadLanguages().contains(nlsitem)){
                    profile2.getReadLanguages().addElement(nlsitem); // this is really cheating
                    addDebug("added nlsitem "+nlsitem+" to new prof");
                }
            }
        }

        return profile2;
    }
    
//  role must have access to all attributes
    public Profile switchRoles(String roleCode)
    throws COM.ibm.eannounce.objects.EANBusinessRuleException,
    java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException,
    COM.ibm.opicmpdh.middleware.MiddlewareRequestException,
    java.rmi.RemoteException,
    IOException,
    COM.ibm.opicmpdh.middleware.MiddlewareException,
    COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
    {
        Profile profile2 = m_prof.getProfileForRoleCode(m_db, roleCode, roleCode, 1);
        if (profile2==null) {
            addError("Could not switch to "+roleCode+" role");
        }else {
            addDebug("Switched role from "+m_prof.getRoleCode()+" to "+profile2.getRoleCode());

            String nlsids = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getNLSIDs(m_abri.getABRCode());
            addDebug("switchRole nlsids: "+nlsids);
            StringTokenizer st1 = new StringTokenizer(nlsids,",");
            while (st1.hasMoreTokens()) {
                String nlsid = st1.nextToken();
                NLSItem nlsitem = (NLSItem)READ_LANGS_TBL.get(nlsid);
                if (!profile2.getReadLanguages().contains(nlsitem)){
                    profile2.getReadLanguages().addElement(nlsitem); // this is really cheating
                    addDebug("added nlsitem "+nlsitem+" to new prof");
                }
            }
        }

        return profile2;
    }

    /**********************************
     * get database
     */
    protected Database getDB() { return m_db; }

    /**********************************
     * get attributecode
     */
    protected String getABRAttrCode() { return m_abri.getABRCode(); }

    /**********************************
     * add msg to report output
     */
    protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}
    
    /**********************************
     * add msg to report output
     */
    protected void addMSGLOGReason(String msg) { reason.append(msg);}

    /**********************************
     * add debug info as html comment
     *    EBUG_ERR = 0;
          EBUG_WARN = 1;
          EBUG_INFO = 2;
          EBUG_DETAIL = 3;
          EBUG_SPEW = 4
     */
    protected void addDebug(String msg) {
    	if (D.EBUG_DETAIL <= DEBUG_LVL) {
		    if (dbgPw != null){
		        dbgLen+=msg.length();
		        dbgPw.println(msg);
		        dbgPw.flush();
		    }else{
		        rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		    }
    	}
    }
    /**
	 * add msg as an html comment if meets debuglevel set in abr.server.properties
	 * @param debuglvl
	 * @param sb
	 * @param msg
	 */
    public void addDebugComment(int debuglvl, String msg){
		if (debuglvl <= DEBUG_LVL) {
			 if (dbgPw != null){
			        dbgLen+=msg.length();
			        dbgPw.println(msg);
			        dbgPw.flush();
			    }else{
			        rptSb.append("<!-- "+msg+" -->"+NEWLINE);
			}
		}
	}
    /**********************************
     * add error info and fail abr
     */
    protected void addError(String msg) {
        addOutput(msg);
        setReturnCode(FAIL);
    }
    /**********************************
     * add error info and fail abr
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    protected void addError(String msg, EntityItem item) throws SQLException, MiddlewareException {
    	String headmsg = getLD_NDN(item); 
        addOutput(headmsg + " " + msg);
        setReturnCode(FAIL);
    }
    /**********************************
     * get the resource bundle
     */
    protected ResourceBundle getBundle() {
        return rsBundle;
    }

    /**********************************
     * feed the xml
     */

    // RQK change to receive mqVct as a parameter

    protected void notify(XMLMQ mqAbr, String rootInfo, String xml, Vector mqVct)
    throws MissingResourceException
    {
        MessageFormat msgf = null;
        // Vector mqVct = mqAbr.getMQPropertiesFN();
        int sendCnt=0;
        boolean hasFailure = false;

        // write to each queue, only one now, but leave this just in case
        for (int i=0; i<mqVct.size(); i++){

            String mqProperties = (String)mqVct.elementAt(i);
            addDebug("in notify looking at prop file "+mqProperties);
            try {
                ResourceBundle rsBundleMQ = ResourceBundle.getBundle(mqProperties,
                        getLocale(getProfile().getReadLanguage().getNLSID()));
                Hashtable ht = MQUsage.getMQSeriesVars(rsBundleMQ);
                boolean bNotify = ((Boolean)ht.get(MQUsage.NOTIFY)).booleanValue();
                ht.put(MQUsage.MQCID,mqAbr.getMQCID()); //add to hashtable for CID to MQ
                ht.put(MQUsage.XMLTYPE,"ADS"); //add to hashtable to indicate ADS msg
				Hashtable userProperties = MQUsage.getUserProperties(rsBundleMQ, mqAbr.getMQCID());
                if (bNotify) {
                    try{
						addDebug("User infor " + userProperties);
                    	MQUsage.putToMQQueueWithRFH2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+xml, ht,userProperties);
                        //SENT_SUCCESS = XML was generated and sent successfully for {0} {1}.
                        msgf = new MessageFormat(rsBundle.getString("SENT_SUCCESS"));
                        args[0] = mqProperties;
                        args[1] = rootInfo;
                        addOutput(msgf.format(args));
                        sendCnt++;
                        //successfully sent vector,  put successfully mqproperties to this vector, for each name of ABRXMLPROPFILE has F or S flag in  Cache.xmlmsglog table.
                        succQueueNameVct.add(mqProperties);
                        if (!hasFailure){  // dont overwrite a failed notice
                            //xmlgen = rsBundle.getString("SUCCESS");//"Success";
                            addXMLGenMsg("SUCCESS", rootInfo);
                            addDebug("sent successfully to prop file "+mqProperties);
                        }
                    }catch (com.ibm.mq.MQException ex) {
                        //MQ_ERROR = Error: An MQSeries error occurred for {0}: Completion code {1} Reason code {2}.
                        //FAILED = Failed sending {0}
                        addXMLGenMsg("FAILED", rootInfo);
                        hasFailure = true;
                        msgf = new MessageFormat(rsBundle.getString("MQ_ERROR"));
                        args[0] = mqProperties+" "+rootInfo;
                        args[1] = ""+ex.completionCode;
                        args[2] = ""+ex.reasonCode;
                        addMSGLOGReason(msgf.format(args));
                        addError(msgf.format(args));
                        ex.printStackTrace(System.out);
                        addDebug("failed sending to prop file "+mqProperties);
                    } catch (java.io.IOException ex) {
                        //MQIO_ERROR = Error: An error occurred when writing to the MQ message buffer for {0}: {1}
                        addXMLGenMsg("FAILED", rootInfo);
                        hasFailure = true;
                        msgf = new MessageFormat(rsBundle.getString("MQIO_ERROR"));
                        args[0] = mqProperties+" "+rootInfo;
                        args[1] = ex.toString();
                        addMSGLOGReason(msgf.format(args));
                        addError(msgf.format(args));
                        ex.printStackTrace(System.out);
                        addDebug("failed sending to prop file "+mqProperties);
                    }
                }else{
                    //NO_NOTIFY = XML was generated but NOTIFY was false in the {0} properties file.
                    msgf = new MessageFormat(rsBundle.getString("NO_NOTIFY"));
                    args[0] = mqProperties;
                    addMSGLOGReason("XML was generated but NOTIFY was false in the {0} properties file.");
                    addError(msgf.format(args));
                    //{0} "Not sent";
                    addXMLGenMsg("NOT_SENT", rootInfo);
                    addDebug("not sent to prop file "+mqProperties+ " because Notify not true");

                }
            } catch (MissingResourceException mre) {
                addXMLGenMsg("FAILED",mqProperties + " " + rootInfo);
                hasFailure = true;
                addMSGLOGReason("Prop file "+mqProperties + " "+rootInfo + " not found");
                addError("Prop file "+mqProperties + " "+rootInfo + " not found");
            }

        } // end mq loop
        if (sendCnt>0 && sendCnt!=mqVct.size()){ // some went but not all
            addXMLGenMsg("ALL_NOT_SENT", rootInfo);// {0} "Not sent to all";
        }
    }

    /**********************************
     * generate the xml
     */
    protected String transformXML(XMLMQ mqAbr, Document document) throws
    ParserConfigurationException,
    javax.xml.transform.TransformerException
    {
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        // OIDH can't handle whitespace.. trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "no");
        trans.setOutputProperty(OutputKeys.METHOD, "xml");
        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        //deal the special character of docment 
        
        String type = document.getDocumentElement().getNodeName();
        
        if(type.contains("MODEL_UPDATE")) {
        	UpdateXML change = new UpdateXML();
        	document = change.update(document, change.store(document));
        }
        
        //create string from xml tree
        java.io.StringWriter sw = new java.io.StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(document);     
        trans.transform(source, result);
        String xmlString = XMLElem.removeCheat(sw.toString());

        //(ADSABRSTATUS)mqAbr.addDebug
        // transform again for user to see in rpt
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        sw = new java.io.StringWriter();
        result = new StreamResult(sw);
        trans.transform(source, result);
        
      
        if(!USERXML_OFF_LOG){
        	addUserXML(XMLElem.removeCheat(sw.toString()));
        }

        return xmlString;
    }
    
    /**********************************
     * generate the xml
     */
    protected String transformCacheXML(XMLMQ mqAbr, Document document) throws
    ParserConfigurationException,
    javax.xml.transform.TransformerException
    {
        //set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        // OIDH can't handle whitespace.. trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "no");
        trans.setOutputProperty(OutputKeys.METHOD, "xml");
        trans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        //deal the special character of docment 
        parseAndConvert(document);

        //create string from xml tree
        java.io.StringWriter sw = new java.io.StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(document);
        trans.transform(source, result);
        String xmlString = XMLElem.removeCheat(sw.toString());

        return xmlString;
    }
    
    /**
     * recursion parse the document and reset the node value with convertToHTML character
     * @param parent
     * @return
     */
    protected void parseAndConvert(Node parent) {
        if(parent.hasChildNodes()){
            NodeList list = parent.getChildNodes();
            Node n;
            for(int i = 0; i < list.getLength(); i++) {
                n = list.item(i);
                if(n.hasChildNodes()) {
                    parseAndConvert(n);
                }
                else{
                    n.setNodeValue(convertquotToHTML(n.getNodeValue()));
                }
            }
        }
    }

    protected void addUserXML(String s){
        if (userxmlPw != null){
            userxmlLen+=s.length();
            userxmlPw.println(s);
            userxmlPw.flush();
        }else{
            userxmlSb.append(convertToHTML(s)+NEWLINE);
        }
    }

    /*************************************************************************************
     * Check the PDHDOMAIN
     * xseries and converged prod need DQ checks in the ABRs but the other domains like iseries don't
     * because those Brands do not want any checking, they do not use STATUS, they want no process
     * criteria apply if PDHDOMAIN = (0050) 'xSeries' or (0390) 'Converged Products'
     *@param item    EntityItem
     * domainInList set to true if matches one of these domains
     */
    protected boolean domainNeedsChecks(EntityItem item)
    {
        boolean bdomainInList = false;
        String domains = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomains(m_abri.getABRCode());
        addDebug("domainNeedsChecks pdhdomains needing checks: "+domains);
        if (domains.equals("all")){
            bdomainInList = true;
        }else{
            Set testSet = new HashSet();
            StringTokenizer st1 = new StringTokenizer(domains,",");
            while (st1.hasMoreTokens()) {
                testSet.add(st1.nextToken());
            }
            bdomainInList = PokUtils.contains(item, "PDHDOMAIN", testSet);
            testSet.clear();
        }

        if (!bdomainInList){
            addDebug("PDHDOMAIN for "+item.getKey()+" did not include "+domains+", execution is bypassed ["+
                    PokUtils.getAttributeValue(item, "PDHDOMAIN",", ", "", false)+"]");
        }
        return bdomainInList;
    }

    /**********************************************************************************
     *  Get Locale based on NLSID
     *
     *@return java.util.Locale
     */
    public static Locale getLocale(int nlsID)
    {
        Locale locale = null;
        switch (nlsID)
        {
        case 1:
            locale = Locale.US;
            break;
        case 2:
            locale = Locale.GERMAN;
            break;
        case 3:
            locale = Locale.ITALIAN;
            break;
        case 4:
            locale = Locale.JAPANESE;
            break;
        case 5:
            locale = Locale.FRENCH;
            break;
        case 6:
            locale = new Locale("es", "ES");
            break;
        case 7:
            locale = Locale.UK;
            break;
        default:
            locale = Locale.US;
        break;
        }
        return locale;
    }
    /***********************************************
     *  Get the version
     *
     *@return java.lang.String
     */
    public String getABRVersion()
    {
        //return "1.8"; cvs failure
        return "1.12";//"1.10";
    }
    /***********************************************
     *  Get ABR description
     *
     *@return java.lang.String
     */
    public String getDescription()
    {
        return "ADSABRSTATUS";
    }

    /********************************************************************************
     * Convert string into valid html.  Special HTML characters are converted.
     *
     * @param txt    String to convert
     * @return String
     */
    protected static String convertToHTML(String txt)
    {
        String retVal="";
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '<':
                    htmlSB.append("&lt;");
                break;
                case '>':
                    htmlSB.append("&gt;");
                    break;
                case '"':
                    // double quotation marks could be saved as &quot; also. this will be &#34;
                    // this should be included too, but left out to be consistent with west coast
                    htmlSB.append("&quot;");
                    break;
                case '\'':
                    //IE6 doesn't support &apos; to convert single quotation marks,we can use &#39; instead
                    htmlSB.append("&#"+((int)ch)+";");
                    break;
                    //case '&': 
                    // ignore entity references such as &lt; if user typed it, user will see it
                    // could be saved as &amp; also. this will be &#38;
                    //htmlSB.append("&#"+((int)ch)+";");
                    //  htmlSB.append("&amp;");
                    //    break;
                default:
                    htmlSB.append(ch);
                break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }
    
    /********************************************************************************
     * Convert string into valid html.  Special HTML characters are converted.
     * convert double quotation into XMLElem.NEWCHEAT + quot; 
     *         sigle  quotation into XMLElem.NEWCHEAT + #39;
     * Then through XMLElem.removeCheat method,
     *             convert @amp; into &
     * @param txt    String to convert
     * @return String
     */
    protected static String convertquotToHTML(String txt)
    {
        String retVal="";
        StringBuffer htmlSB = new StringBuffer();
        StringCharacterIterator sci = null;
        char ch = ' ';
        if (txt != null) {
            sci = new StringCharacterIterator(txt);
            ch = sci.first();
            while(ch != CharacterIterator.DONE)
            {
                switch(ch)
                {
                case '"':
                    // double quotation marks could be saved as &quot; also. this will be &#34;
                    // this should be included too, but left out to be consistent with west coast
                    htmlSB.append(XMLElem.NEWCHEAT+"quot;");
                    break;
                case '\'':
                    //IE6 doesn't support &apos; to convert single quotation marks,we can use &#39; instead
                    htmlSB.append(XMLElem.NEWCHEAT+"#39;");
                    break;
                    //case '&': 
                    // ignore entity references such as &lt; if user typed it, user will see it
                    // could be saved as &amp; also. this will be &#38;
                    //htmlSB.append("&#"+((int)ch)+";");
                    //  htmlSB.append("&amp;");
                    //    break;
                default:
                    htmlSB.append(ch);
                break;
                }
                ch = sci.next();
            }
            retVal = htmlSB.toString();
        }

        return retVal;
    }

//  ========================================================================================
//  == resend support
//  ========================================================================================

    /***************************************************************************
     * deactivate the specified MultiFlag Attribute on the Root Entity
     * 
     * @param strAttributeCode
     *            The Flag Attribute Code
     */
    private void deactivateMultiFlagValue(String strAttributeCode) throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException {
        logMessage(getDescription() + " ***** " + strAttributeCode);
        addDebug("deactivateFlagValue entered for " + strAttributeCode);
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

        // if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr == null) {
            addDebug("deactivateFlagValue: " + strAttributeCode + " was not in meta for " + rootEntity.getEntityType()
                    + ", nothing to do");
            logMessage(getDescription() + " ***** " + strAttributeCode + " was not in meta for " + rootEntity.getEntityType()
                    + ", nothing to do");
            return;
        }
        String strAttributeValue = PokUtils.getAttributeFlagValue(rootEntity, strAttributeCode);
        if (strAttributeValue != null) {
            try {
                ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);
                Vector vctReturnsEntityKeys = new Vector();
                Vector vctAtts = new Vector();
                EANAttribute att = rootEntity.getAttribute(strAttributeCode);
                if (att != null) {
                    String efffrom = att.getEffFrom();
                    ControlBlock cbOff = new ControlBlock(efffrom, efffrom, efffrom, efffrom, m_prof.getOPWGID());
                    StringTokenizer st = new StringTokenizer(strAttributeValue, "|");
                    while (st.hasMoreTokens()) {
                        String s = st.nextToken();
                        MultipleFlag mf = new MultipleFlag(m_prof.getEnterprise(), rootEntity.getEntityType(), rootEntity
                                .getEntityID(), strAttributeCode, s, 1, cbOff);
                        vctAtts.addElement(mf);
                    }
                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);
                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    addDebug(rootEntity.getKey() + " had " + strAttributeCode + " deactivated");
                }

            } finally {
                m_db.commit();
                m_db.freeStatement();
                m_db.isPending("finally after deactivate value");
            }
        }

    }

    /***************************************************************************
     * Sets the specified Flag Attribute on the Root Entity
     * 
     * @param profile
     *            Profile
     * @param strAttributeCode
     *            The Flag Attribute Code
     * @param strAttributeValue
     *            The Flag Attribute Value
     */
    private void setFlagValue(String strAttributeCode, String strAttributeValue)
    throws java.sql.SQLException,
    COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        logMessage(getDescription()+" ***** "+strAttributeCode+" set to: " + strAttributeValue);
        addDebug("setFlagValue entered for "+strAttributeCode+" set to: " + strAttributeValue);
        EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

        // if meta does not have this attribute, there is nothing to do
        EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute(strAttributeCode);
        if (metaAttr==null) {
            addDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+rootEntity.getEntityType()+", nothing to do");
            logMessage(getDescription()+" ***** "+strAttributeCode+" was not in meta for "+
                    rootEntity.getEntityType()+", nothing to do");
            return;
        }
        if(strAttributeValue != null)
        {
            if(strAttributeValue.equals(getAttributeFlagEnabledValue(rootEntity,strAttributeCode))){
                addDebug("setFlagValue "+rootEntity.getKey()+" "+strAttributeCode+
                        " already matches: " + strAttributeValue);
            }else{
                try
                {
                    if (m_cbOn==null){
                        setControlBlock(); // needed for attribute updates
                    }
                    ReturnEntityKey rek = new ReturnEntityKey(getEntityType(), getEntityID(), true);

                    SingleFlag sf = new SingleFlag (m_prof.getEnterprise(), rek.getEntityType(), rek.getEntityID(),
                            strAttributeCode, strAttributeValue, 1, m_cbOn);
                    Vector vctAtts = new Vector();
                    Vector vctReturnsEntityKeys = new Vector();
                    vctAtts.addElement(sf);
                    rek.m_vctAttributes = vctAtts;
                    vctReturnsEntityKeys.addElement(rek);

                    m_db.update(m_prof, vctReturnsEntityKeys, false, false);
                    addDebug(rootEntity.getKey()+" had "+strAttributeCode+" set to: " + strAttributeValue);
                }
                finally {
                    m_db.commit();
                    m_db.freeStatement();
                    m_db.isPending("finally after update in setflag value");
                }
            }
        }
    }
    private String sysFeedResendStatus(String _strABR,String key, String defvale){
        return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(_strABR,key,defvale);
    }

    protected String getQueuedValue(String abrcode) {
        String abrAttrCode = (String) ABR_ATTR_TBL.get(getEntityType());
        if (abrAttrCode == null) {
            addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
            return STATUS_QUEUE;
        } else {
            addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + abrAttrCode + "_" + abrcode);
            return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue(abrAttrCode + "_" + abrcode);
        }
    }

    protected String getRFRQueuedValue(String abrcode) {
        String abrAttrCode = (String) ABR_ATTR_TBL.get(getEntityType());
        if (abrAttrCode == null) {
            addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
            return STATUS_QUEUE;
        } else {
            addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + abrAttrCode + "_" + abrcode);
            return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRRFRQueuedValue(abrAttrCode + "_" + abrcode);
        }
    }
    protected boolean fullMode(){
        String result =  COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("ADSABRSTATUS" , "_FullMode");
        if (result.equals("true")||result.equals("True")||result.equals("TRUE")){
            addDebug("ADSABRSTATUS is running in FullMode!");
            return true;
        }else{
            addDebug("ADSABRSTATUS is not running in FullMode!");
            return false;
        }
    }
    protected boolean isPeriodicABR(){
        return isPeriodicABR;
    }
    protected boolean isXMLCACHE(){
        return isXMLCACHE;
    }
    protected boolean isSystemResendRFR(){
    	return isSystemResendRFR;
    }
    
    protected boolean isSystemResendCache(){
    	return isSystemResendCache;
    }
    
    protected boolean isSystemResendCacheExist(){
    	return isSystemResendCacheExist;
    }
    
    protected String getSystemResendCacheXml(){
    	return SystemResendCacheXml;
    }    

    /**
     * Assumption, item getting removed by this method is an Entity, not a Relator or Assoc
     * @param peg
     * @param ei
     */
    private  void removeItem(EntityGroup peg, EntityItem ei) {
        addDebug("removeItem: "+ei.getKey()+" ei.getUpLinkCount() "+ei.getUpLinkCount()
        		+" ei.getDownLinkCount() "+ei.getDownLinkCount());
        
        Vector entityVct = new Vector(); // these are the entities at the end of the relator, they may need removal
        
        // Load the uplinks for the item to be removed into and array for processing
        EntityItem up_item_array[] = new EntityItem[ei.getUpLinkCount()];
        ei.getUpLink().copyInto(up_item_array);
        // Load the downlinks for the item to be removed into and array for processing
        EntityItem down_item_array[] = new EntityItem[ei.getDownLinkCount()];
        ei.getDownLink().copyInto(down_item_array);
 
        //i.e. MODEL - MODELAVAIL - AVAIL - AVAILANNA - ANNOUNCEMENT and removing an AVAIL
        // Loop through the uplinks
        for (int j=0; j<up_item_array.length; j++){
            // Get the entity on the other side of the uplink - it will be a relator or assoc 
            EntityItem upRelator = up_item_array[j]; // i.e MODELAVAIL
            addDebug("uplink: "+ upRelator.getKey());
            // remove the relator uplink from the item being removed - this will also remove the downlink from the
            // relator to this entity
            ei.removeUpLink(upRelator); //i.e removes AVAIL to MODELAVAIL and MODELAVAIL to AVAIL
                 
    		// make sure this is not a root item
            // must support {"ADSPRODSTRUCT","FEATURE","Final"}, and  {"ADSSWPRODSTRUCT","SWFEATURE","Final"},
            if(peg.containsEntityItem(upRelator.getEntityType(), upRelator.getEntityID())){
            	up_item_array[j] = null;
            	addDebug("uplink: upRelator is root "+upRelator.getKey());
            	continue;
            }
            if(upRelator.getDownLinkCount()>0){// there may be 1:M for Assoc
            	up_item_array[j] = null;
               	addDebug("uplink: upRelator "+upRelator.getKey()+" has more downlinks ");
            	continue;
            }
            
            // remove the uplink from the uprelator being removed
            EntityItem parents[] = new EntityItem[upRelator.getUpLinkCount()];
            upRelator.getUpLink().copyInto(parents);
            for (int x=0; x<parents.length; x++){
            	EntityItem parentitem = parents[x];
            	addDebug("uplink: parentitem  "+ parentitem.getKey());
            	// remove the parent from the uplink relator being removed- this also removes the downlink from 
            	//the parent to the relator
            	upRelator.removeUpLink(parentitem); //i.e removes MODEL to MODELAVAIL and MODELAVAIL to MODEL
            	// make sure this is not a root item
            	if(!peg.containsEntityItem(parentitem.getEntityType(), parentitem.getEntityID())){
            		if (!entityVct.contains(parentitem)){
            			entityVct.add(parentitem);
            		}
            	}else{
            		addDebug("uplink: parentitem is root "+parentitem.getKey());
            	}

            	parents[x]=null;
            }
        }
        // Loop through the downlinks
        for (int j=0; j<down_item_array.length; j++){
            // Get the entity on the other side of the downlink - must be relator or assoc
            EntityItem downRelator = down_item_array[j];
            addDebug("Downlink: "+ downRelator.getKey()); // i.e. AVAILANNA
            // remove the downlink from the item being removed - this also removes the uplink from the relator to the item
            ei.removeDownLink(downRelator);//i.e removes AVAILANNA to AVAIL and AVAIL to AVAILANNA

            // make sure this is not a root item
            // must support {"ADSPRODSTRUCT","FEATURE","Final"}, and  {"ADSSWPRODSTRUCT","SWFEATURE","Final"},
            if(peg.containsEntityItem(downRelator.getEntityType(), downRelator.getEntityID())){
            	down_item_array[j] = null;
            	addDebug("Downlink: downRelator is root "+downRelator.getKey());
            	continue;
            }
            if(downRelator.getUpLinkCount()>0){// there may be 1:M for Assoc
            	down_item_array[j] = null;
               	addDebug("Downlink: downRelator "+downRelator.getKey()+" has more uplinks ");
            	continue;
            }
            
            // remove the downlink from the downlink relator being removed
            EntityItem children[] = new EntityItem[downRelator.getDownLinkCount()];
            downRelator.getDownLink().copyInto(children);
            for (int x=0; x<children.length; x++){
            	EntityItem childitem = children[x]; //i.e ANNOUNCEMENT
            	addDebug("Downlink: childitem "+ childitem.getKey());
            	// remove the child from the downlink relator being removed- also removes the uplink from the child to the relator
            	downRelator.removeDownLink(childitem); //i.e removes AVAILANNA to ANNOUNCEMENT and ANNOUNCEMENT to AVAILANNA

            	// make sure this is not a root item
            	if(!peg.containsEntityItem(childitem.getEntityType(), childitem.getEntityID())){
            		if (!entityVct.contains(childitem)){
            			entityVct.add(childitem);
            		}
            	}else{
            		addDebug("Downlink: childitem is root "+childitem.getKey());
            	}
            	children[x]=null;
            }
        }
        
        removeFilteredEntityItem(ei);
        // Loop through the uplinks
        for (int j=0; j<up_item_array.length; j++){
            // release the relator
        	removeFilteredEntityItem(up_item_array[j]);
        	up_item_array[j]=null;
        }
        // Loop through the downlinks
        for (int j=0; j<down_item_array.length; j++){
            // release the relator
        	removeFilteredEntityItem(down_item_array[j]);
        	down_item_array[j]=null;
        }
        
        // look at all entities that had relator/assoc removed, are they now orphans?
        for (int i=0; i<entityVct.size(); i++){
        	EntityItem entity = (EntityItem)entityVct.elementAt(i);
        	// was this entity orphaned - if so, release it
        	if(!entity.hasDownLinks() && !entity.hasUpLinks()){
              	removeFilteredEntityItem(entity);
        	}
        }
        entityVct.clear();
        entityVct = null;
    	up_item_array=null;
    	down_item_array=null;
    }
    
    /**
     * remove this entity from the group and remove all attributes
     * @param item
     */
    private void removeFilteredEntityItem(EntityItem item){
    	if(item!=null){
    		addDebug("removeFilteredEntityItem "+item.getKey());
    		EntityGroup eg = item.getEntityGroup();
    		// set parent null for the filtered item
    		item.setParent(null);
    		// Remove the attributes for the filtered item
    		for (int z = item.getAttributeCount() - 1; z >= 0; z--) {
    			EANAttribute att = item.getAttribute(z);
    			item.removeAttribute(att);
    		}
    		// Remove the filitered item from the group.
    		eg.removeEntityItem(item);
    	}
    }
    private HashSet getCountry(EntityItem ei, String attr) {
        HashSet Set = new HashSet();
        // get current set of countries
        EANFlagAttribute fAtt = (EANFlagAttribute)ei.getAttribute(attr);
        addDebug("ADSABRSTATUS.getCountry for entity "+ei.getKey()+" fAtt "+
                PokUtils.getAttributeFlagValue(ei, attr)+NEWLINE);
        if (fAtt!=null && fAtt.toString().length()>0){
            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            for (int i = 0; i < mfArray.length; i++){
                // get selection
                if (mfArray[i].isSelected()){
                    Set.add(mfArray[i].getFlagCode());
                }  // metaflag is selected
            }// end of flagcodes
        }
        return Set;
    }
    /***************************************************************************
     * Add 40 second to T1 and T2 according to the Doc 
     *  if T1 = 闁炽儻鎷�980-01-01闁炽儻鎷�(i.e. a complete refresh), then a single extract for T2 is sufficient since the data is all changed?? and is the current values. In order to allow for the ENTITY update (VALFROM) and the ATTRIBTE update (VALFROM) occuring at slightly different times, the T1 and T2 Data Time Stamps from the prior section need to be adjusted as follows:
     * T1 = T1 + 40 Seconds
     * T2 = T2 + 40 Seconds
     * 
     * @param String
     *            valfrom 
     * @return String           
     */
    private String adjustTimeSecond(String datetime, int secs ) {

        String sFormat = "yyyy-MM-dd-HH.mm.ss";
        String newdate = "&nbsp;";
        SimpleDateFormat dateformat = new SimpleDateFormat(sFormat);
        Calendar cal = Calendar.getInstance();
        if (!"&nbsp;".equals(datetime)){
        	try {
                cal.setTime(dateformat.parse(datetime));
                cal.add(Calendar.SECOND, secs); //add 40 second to current date time
                newdate = dateformat.format(cal.getTime()) + datetime.substring(19);
            } catch (ParseException e) {
                addDebug("failed add second to T1 or T2. Second is: " + secs );
            } catch (Exception e) {
            	addDebug("failed add second to T1 or T2. Second is: " + secs );
            }
        }       
        //addDebug("adjust datetime " + datetime + " add " + secs + " second. " + " the new date is:" + newdate);
        return newdate;
    }
    
    /**
     * BH FS ABR XML IDL 20110421.doc 
     * 4.	Creates full XML
       5.	Checks to see if there is already a cache entry using the XMLENTITYTYPE and XMLENTITYID for this instance of the root entity (ENTITYTYPE and ENTITYID). 
		a.	If so, checks the DTS of the replacement versus the current cache DTS
		i.	If it is greater (newer) than the current record, then 
		1.	set XMLCACHEVALIDTO = the DTS found in Step 2 of the XML generation without the addition of the 30 seconds defined in step 3.
		2.	insert a new Record with XMLCACHEVALIDTO = 9999-12-31 00:00:00.000000 and XMLCACHEDTS = VALFROM of XMLIDLABRSTATUS = Queued (0020) (i.e. when this ABR was queued) 闁炽儻鎷穞he value prior to In Process (0050)).
		ii.	If it is not greater (older) than the current record, then do nothing (i.e. do not insert a new record and do not update current record)
		b.	If not, then insert the new Record with XMLCACHEVALIDTO = 9999-12-31 00:00:00.000000  and XMLCACHEDTS = VALFROM of XMLIDLABRSTATUS = Queued (0020) (i.e. when this ABR was queued 闁炽儻鎷穞he value prior to In Process (0050)).


     * @param rootType
     * @param xml
     * @throws MiddlewareException 
     * @throws SQLException 
     */
    protected void putXMLIDLCache(EntityList listT2, String xml, String T2DTS) throws MiddlewareException, SQLException{
    	//
    	String t2 = m_strEpoch; 
    	if (isXMLIDLABR){    		
    		t2 = t2DTS;
    	}else {
    		//t2 = adjustTimeSecond(T2DTS, -40);
    		t2 = T2DTS;
    		//addDebug("adjust XMLCACHEDTS without adding 40 seconds " + t2);
    	}
    	addDebug("get XMLCACHEDTS: " + t2);
    	EntityItem rootEntity  = listT2.getParentEntityGroup().getEntityItem(0);
    	String entityType = rootEntity.getEntityType();
    	String OLDINDC = PokUtils.getAttributeFlagValue(rootEntity, "OLDINDC");
    	
    	int entityID = rootEntity.getEntityID();
    	PreparedStatement ps = null;
    	Statement statement = null;
    	
		ResultSet result = null;
        boolean exist = false;
        boolean update = false;
        addDebug("rootType :" + entityType);
        addDebug("entityID :" + entityID);
        
        StringBuffer strbSQL = new StringBuffer();
        strbSQL.append("SELECT xmlcachedts FROM cache.xmlidlcache");
        strbSQL.append(" WHERE xmlentitytype = '" + entityType + "'");
        strbSQL.append(" AND xmlentityID = " + entityID );
        strbSQL.append(" AND xmlcachevalidto > current timestamp");
        try{
        try {
        	
		    statement = m_db.getODSConnection().createStatement();
			result = statement.executeQuery(strbSQL.toString());

			while (result.next()) {
				exist = true;
				String cachedts = result.getString(1);
				if (t2.compareTo(cachedts) > 0) { // bypass default entities
					update = true;
				}
			}
			addDebug("get XMLIDLCache where" + rootEntity.getKey() + " is exist: " + exist + " is need update: " + update);
		} catch (MiddlewareException e){
		    addDebug("MiddlewareException on ? " + e);
	        e.printStackTrace();
	        throw e;
	    } catch (SQLException  rx) {
			addDebug("RuntimeException on ? " + rx);
            rx.printStackTrace();
            throw rx;
        } 
        if (exist && update){
			strbSQL = new StringBuffer();
		    strbSQL.append("UPDATE cache.xmlidlcache");
		    strbSQL.append(" SET xmlcachevalidto = ?");
		    if(OLDINDC != null && !"".equals(OLDINDC.trim())) {
		    	strbSQL.append(" , OLDINDC = ? ");
		    }
		    strbSQL.append(" WHERE xmlentitytype = '" + entityType + "'");
		    strbSQL.append(" AND xmlentityID = " + entityID );
		    strbSQL.append(" AND xmlcachevalidto > current timestamp");
		    
		    try{
			    ps = m_db.getODSConnection().prepareStatement(new String(strbSQL));
			    ps.setString(1, t2);
			    if(OLDINDC != null && !"".equals(OLDINDC.trim())) {
			    	 ps.setString(2, OLDINDC);
			    }
			    ps.execute();
				if (m_db.getODSConnection() != null){
					m_db.getODSConnection().commit();
				}
           }catch (MiddlewareException e){
			    addDebug("MiddlewareException on ? " + e);
		        e.printStackTrace();
		        throw e;
		   }catch (SQLException rx) {
				addDebug("SQLException on ? " + rx);
			    rx.printStackTrace();
			    throw rx;
			}finally{
        	   if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
           
	        insertIntoCache(rootEntity, xml, t2, entityType, entityID, ps); 
        } else if(!exist){
        	
//    		insertinto XMLIDLCache()
        	insertIntoCache(rootEntity, xml, t2, entityType, entityID, ps);
        }	                      
    }finally{   
		try {
			m_db.commit();
			if (statement != null)
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
    }  	
    }

	/**
	 * 
	 * @param rootType
	 * @param xml
	 * @param t2
	 * @param entityType
	 * @param entityID
	 * @param ps
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareException 
	 */
	private void insertIntoCache(EntityItem rootItem, String xml, String t2, String entityType, int entityID, PreparedStatement ps) throws SQLException, MiddlewareException {
		StringBuffer strbSQL;
		String filters[] = (String[])FILTER_TBL.get(entityType);
		//XMLOFFTYPE
		String offtype = "";
		String flfilsysindc = "";
		String withdrweffctvdate = null;
		String pdhdomain = "";
		String status = "";
		long lastTime =System.currentTimeMillis();
		long runTime = 0;
		
		if (filters != null){
			String offtype_atr = filters[0];
			//FLFILSYSINDC
			String flfilsysindc_atr = filters[1];
			//WTHDRWEFFCTVDATE
			String withdrweffctvdate_atr = filters[2];
			if (!"".equals(offtype_atr)){
				if (!"LSEO".equals(rootItem.getEntityType())){
					offtype = PokUtils.getAttributeFlagValue(rootItem, offtype_atr);
					if (offtype==null){
						offtype = "";
					}
				}else{
				//LSEO-U-WWSEOLSEO -U -WWSEO-U-MODELWWSEO-U- MODEL
		        
		        	//ExtractActionItem xai = new ExtractActionItem(null, m_db, m_prof,"ADSLSEO2");
			        //xai.setSkipCleanup(true);
                    //EntityList list = m_db.getEntityList(m_prof, xai,
                    //new EntityItem[] { new EntityItem(null, m_prof, rootType.getEntityType(), rootType.getEntityID())});
                    //EntityItem rootEntity  = list.getParentEntityGroup().getEntityItem(0);
					if(rootItem != null){
						Vector modelVct = new Vector();
						Vector wwseoVct = PokUtils.getAllLinkedEntities(rootItem, "WWSEOLSEO", "WWSEO");
						for (int i=0; i<wwseoVct.size(); i++){
							EntityItem wwseo = (EntityItem)wwseoVct.elementAt(i);
						    modelVct = PokUtils.getAllLinkedEntities(wwseo, "MODELWWSEO", "MODEL");
							if (modelVct.size()>0)
								break;
						}
						if (modelVct.size()>0){
							EntityItem model = (EntityItem)modelVct.elementAt(0);
							offtype = PokUtils.getAttributeFlagValue(model, offtype_atr);
							if (offtype==null){
								offtype = "";
							}
						}
					}
					runTime = System.currentTimeMillis();
					addDebugComment(D.EBUG_DETAIL, "Time for get COFCAT from ListT2: "+Stopwatch.format(runTime-lastTime));
			   }			
			} 
			if (!"".equals(flfilsysindc_atr)){
				flfilsysindc = PokUtils.getAttributeFlagValue(rootItem,flfilsysindc_atr);
				if (flfilsysindc==null)
					flfilsysindc = "";
			}
			if (!"".equals(withdrweffctvdate_atr)){
				//SWPRODSTRUCT-->MODEL 
				if ("SWPRODSTRUCT".equals(rootItem.getEntityType())){					
					lastTime = System.currentTimeMillis();
					//ExtractActionItem xai = new ExtractActionItem(null, m_db, m_prof,"ADSSWPRODSTRUCT");
					//EntityList list = m_db.getEntityList(m_prof, xai,
				    //    new EntityItem[] { new EntityItem(null, m_prof, rootType.getEntityType(), rootType.getEntityID())});
				    //EntityItem rootEntity  = list.getParentEntityGroup().getEntityItem(0);
				    if (rootItem.hasDownLinks()){
						for (int i=0; i<rootItem.getDownLinkCount(); i++){
							EntityItem entity = (EntityItem)rootItem.getDownLink(i);
							if (entity.getEntityType().equals("MODEL")){
								withdrweffctvdate = PokUtils.getAttributeValue(entity,withdrweffctvdate_atr, ", ", null, false);	
								addDebugComment(D.EBUG_DETAIL, "Get withdrweffctvdate through VE ADSSWPRODSTRUCT, date is :" + withdrweffctvdate);
								break;
							}
						}
		            }else{
		            	addDebugComment(D.EBUG_DETAIL, "Can not get down link through VE ADSSWPRODSTRUCT");
		            }     
					addDebugComment(D.EBUG_DETAIL, "Time for get withdrweffectvdate from VE ADSSWPRODSTRUCT: "+Stopwatch.format(System.currentTimeMillis()-lastTime));
				}else if ("REFOFERFEAT".equals(rootItem.getEntityType())){
					// REFOFSERFEAT get parent REFOFER ENDOFSVC
					String endofsvcdate = null;
					if (rootItem.hasUpLinks()){
					loop: for (int i=0; i<rootItem.getUpLinkCount(); i++){
							EntityItem entity = (EntityItem)rootItem.getUpLink(i);
							if (entity != null && entity.getEntityType().equals("REFOFERREFOFERFEAT")){
								for (int j=0; j<entity.getUpLinkCount(); j++){
									EntityItem refofer = (EntityItem)entity.getUpLink(j);
									if (refofer != null && "REFOFER".equals(refofer.getEntityType())){
										String effctvdate = PokUtils.getAttributeValue(refofer,withdrweffctvdate_atr, ", ", null, false);	
										addDebugComment(D.EBUG_DETAIL, "Get withdrweffctvdate through VE ADSREFOFERFEAT, date is :" + effctvdate);
										if (effctvdate != null) {
											// initial the date
											if (endofsvcdate == null) {
												endofsvcdate = effctvdate;
											} else {
												// find the minimum anndate.
												if (effctvdate.compareTo(endofsvcdate) > 0) {
													endofsvcdate = effctvdate;
												}
											}
										} else{
											endofsvcdate = m_strForever.substring(0,10);
											break loop;
										}
									}
									
								}
								
							}
						}
		            }else{
		            	addDebugComment(D.EBUG_DETAIL, "Can not get up link through VE ADSREFOFERFEAT");
		            } 
					withdrweffctvdate = endofsvcdate;
				}else{
					withdrweffctvdate = PokUtils.getAttributeValue(rootItem,withdrweffctvdate_atr, ", ", null, false);
				}
				//update IDL 20110908.doc If there is no value available for WTHDRWEFFCTVDATE, then set it to 9999-12-31 
				if (withdrweffctvdate == null){
					withdrweffctvdate = m_strForever.substring(0,10);
				}
				
			}
		}
//    		PDHDOMAIN
		pdhdomain = PokUtils.getAttributeFlagValue(rootItem, "PDHDOMAIN");
		
		String OLDINDC = PokUtils.getAttributeFlagValue(rootItem, "OLDINDC");
    	
		if (pdhdomain==null){
			pdhdomain = "";
		}
		if (OLDINDC==null){
			OLDINDC = "";
		}
		status = curStatusvalue;
	
		if (status == null){
			status = "";
		}
		strbSQL = new StringBuffer();
		strbSQL.append("INSERT INTO cache.xmlidlcache (XMLENTITYTYPE, XMLENTITYID, XMLCACHEDTS, XMLCACHEVALIDTO, XMLOFFTYPE, FLFILSYSINDC, WTHDRWEFFCTVDATE, STATUS, XMLMESSAGE, PDHDOMAIN, OLDINDC) ");
		strbSQL.append(" VALUES (?,?,?,?,?,?,?,?,?,?,? )");
		addDebug("T2 :" + t2);
        addDebug("offtype :" + offtype);
        addDebug("flfilsysindc :" + flfilsysindc);
        addDebug("withdrweffctvdate :" + withdrweffctvdate);
        addDebug("status :" + status);
        addDebug("pdhdomain :" + pdhdomain);
        addDebug("OLDINDC :" + OLDINDC);
        
		try{
			ps = m_db.getODSConnection().prepareStatement(new String(strbSQL));
		    ps.setString(1, entityType);
		    ps.setInt(2, entityID);
		    ps.setString(3, t2);
		    ps.setString(4, "9999-12-31-00.00.00.000000");
		    ps.setString(5, offtype);
		    ps.setString(6, flfilsysindc);
		    ps.setString(7, withdrweffctvdate);
		    ps.setString(8, status);
		    ps.setString(9, xml);	
		    ps.setString(10, pdhdomain);
		    ps.setString(11, OLDINDC);
		    ps.execute();   
			if (m_db.getODSConnection() != null){
				m_db.getODSConnection().commit();
			}
			addDebugComment(D.EBUG_INFO, " record was inserted into table xmlidlcache.");
		}catch (MiddlewareException e){
			addDebug("MiddlewareException on ? " + e);
		    e.printStackTrace();
		    throw e;
		}catch (SQLException rx) {
			addDebug("SQLException on ? " + rx);
		    rx.printStackTrace();
		    throw rx;
		}finally{
			 if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}	
	/**
	 * insert MSGLOG for reconciliation xml report.
	 * @param MSGLOGList
	 * @param setupType
	 * @param setupid
	 * @param setupDTS
	 * @param entityType
	 * @param MQPropFile
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	protected void inertMSGLOG(ArrayList MSGLOGList, String setupType,
		int setupid, String setupDTS, String entityType, String MQPropFile) throws MiddlewareException, SQLException {
	long startTime = System.currentTimeMillis();
	Iterator list = MSGLOGList.iterator();

	PreparedStatement ps = null;
	StringBuffer strbSQL = new StringBuffer();
	strbSQL
			.append("INSERT INTO cache.XMLMSGLOG (SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, SENDMSGDTS, MSGTYPE, MSGCOUNT, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGSTATUS, REASON, MQPROPFILE) ");
	strbSQL.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
	try {
		//m_db.setAutoCommit(false);
		ps = m_db.getODSConnection().prepareStatement(new String(strbSQL));
		while (list.hasNext()) {
			String record[] = (String[]) list.next();
			int entityid = (record[0]==null?0:Integer.parseInt(record[0]));
			ps.setString(1, setupType);
			ps.setInt(2, setupid);
			ps.setString(3, setupDTS);
			ps.setString(4, (String) record[1]);
			ps.setString(5, (String) record[2]);
			/** 
			 * When we run WWCOMPAT_UPDATE and PRICE_UPDATE IDL, we create many XML files -- 
			 * but the Reconcile table says there is only 1 message.  
			 * Instead, the row should include the number of XML messages actually sent.
			 */
			if("XMLCOMPATSETUP".equals(setupType)){				
				ps.setInt(6, ADSWWCOMPATXMLABR.WWCOMPAT_MESSAGE_COUNT);
			}else if("XMLPRODPRICESETUP".equals(setupType)){
				ps.setInt(6, ADSPRICEABR.PRICE_MESSAGE_COUNT);
			} else{
				ps.setInt(6, 1);
			}
			ps.setString(7, entityType);
			ps.setInt(8, entityid);
			ps.setString(9, (String) record[3]);
			ps.setString(10, (String) record[4]);
			ps.setString(11, (String) record[5]);
			ps.setString(12, MQPropFile);
			ps.addBatch();
			//addDebug("entityid :" +entityid + "setupType"+setupType +"setupid"+setupid+"setupDTS"+setupDTS+"record1"+record[1]+ "record[2]"+record[2]+" record[3]"+ record[3]+ "record[4]"+record[4]+"record[5]"+record[5]);
		}
        ps.executeBatch();
    	if (m_db.getODSConnection() != null){
			m_db.getODSConnection().commit();
		}
		
    	addDebugComment(D.EBUG_INFO, MSGLOGList.size()
				+ " records was inserted into table XMLMSGLOG. Total Time: "
				+ Stopwatch.format(System.currentTimeMillis() - startTime));
	} catch (MiddlewareException e){
		addDebug("MiddlewareException on ? " + e);
	    e.printStackTrace();
	    throw e;
	}catch (SQLException rx) {
		addDebug("SQLException on ? " + rx);
	    rx.printStackTrace();
	    throw rx;
	} finally {
		if (ps != null)
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

}	/**
       set extxmlfeedVct
    */
	public void setExtxmlfeedVct(Vector extxmlfeedVct){
		this.extxmlfeedVct = extxmlfeedVct;
	}
	/**
     * get the description of the item
     * @param item
     * @param code
     * @return
     */
	private String getDescription(EntityItem item, String code,String type) {
		String value=""; 
		EANFlagAttribute fAtt = (EANFlagAttribute)item.getAttribute(code);
        if (fAtt!=null && fAtt.toString().length()>0){
            // Get the selected Flag codes.
            MetaFlag[] mfArray = (MetaFlag[]) fAtt.get();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mfArray.length; i++){
                // get selection            	
                if (mfArray[i].isSelected())
                {
                	if (sb.length()>0) {
                        sb.append(","); 
                    }
                	if(type.equals("short")) {
                		sb.append(mfArray[i].getShortDescription());
                	} else if(type.equals("long")) {
                		sb.append(mfArray[i].getLongDescription());                	
                	} else if(type.equals("flag")) {
                		sb.append(mfArray[i].getFlagCode());                	
                	}
                	else{
                		sb.append(mfArray[i].toString());
                	}
                }                
            }//
            value = sb.toString();
        }
        return value;
	}
	/**
	 *  If there is a data problem, then the EntityType Long Description 
	 *  and the Navigation Display Attributes should be used to identify the data along with a message indicating the problem. 
	 */
	
	//------------------------------------------------------
	// get string for messages
	/**************************************
	 * Get long description and navigation name for specified entity
	 * @param item
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected String getLD_NDN(EntityItem item) throws SQLException, MiddlewareException    {
		return item.getEntityGroup().getLongDescription()+" &quot;"+getNavigationName(item)+"&quot;";
	}
	
	/**********************************************************************************
	 * Find entity item to use for building the navigation display name when more then
	 * one entity is needed, like for PRODSTRUCT
	 *
	 *@return EntityItem
	 */
	private EntityItem getNDNitem(EntityItem theItem,String etype){
		for (int i=0; i<theItem.getDownLinkCount(); i++){
			EntityItem ent = (EntityItem)theItem.getDownLink(i);
			if (ent.getEntityType().equals(etype)){
				return ent;
			}
		}
		for (int i=0; i<theItem.getUpLinkCount(); i++){
			EntityItem ent = (EntityItem)theItem.getUpLink(i);
			if (ent.getEntityType().equals(etype)){
				return ent;
			}
		}
		return null;
	}
	
	 /**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();
		NDN ndn = (NDN)NDN_TBL.get(theItem.getEntityType());
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
		if (metaList==null)	{
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii=0; ii<metaList.size(); ii++){
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			if (ii+1<metaList.size()){
				navName.append(" ");
			}
		}
		if (ndn!=null){ // must get other attr from parent and child entities
			EntityList psList = null;
			StringBuffer sb = new StringBuffer();
			EntityItem ei = getNDNitem(theItem,ndn.getEntityType());
			// performance issue with bringing back too many features and models for prodstruct error msg
			if (ei==null){
				if (theItem.getEntityType().endsWith("PRODSTRUCT")){	
					addDebug("NO entity found for ndn.getEntityType(): "+ndn.getEntityType()+
							" pulling small VE for this "+theItem.getKey());
					// pull VE with just this prodstruct
					String PS_VE = "EXRPT3FM";  // just feature->ps->model
					if (theItem.getEntityType().equals("SWPRODSTRUCT")){	
						PS_VE = "EXRPT3SWFM";  // just swfeature->swps->model
					}/*else if (theItem.getEntityType().equals("IPSCSTRUC")){
						PS_VE = "EXRPT3IPSCFM";  // just IPSCFEAT->IPSCSTRUC->svcmod
					}*/
					psList = m_db.getEntityList(theItem.getProfile(),
							new ExtractActionItem(null, m_db, theItem.getProfile(),PS_VE),
							new EntityItem[] { new EntityItem(null, theItem.getProfile(), theItem.getEntityType(),
									theItem.getEntityID()) });

					theItem = psList.getParentEntityGroup().getEntityItem(0);
					ei = getNDNitem(theItem,ndn.getEntityType());
				}
			}

			if (ei!=null){
				sb.append("("+ndn.getTag());
				for (int y=0; y<ndn.getAttr().size(); y++){
					String attrcode = ndn.getAttr().elementAt(y).toString();
					sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
					if (y+1<ndn.getAttr().size()){
						sb.append(" ");
					}
				}
				sb.append(") ");
			}else{
				addDebug("NO entity found for ndn.getEntityType(): "+ndn.getEntityType());
			}
			ndn = ndn.getNext();
			if (ndn !=null){
				ei = getNDNitem(theItem,ndn.getEntityType());
				if (ei!=null){
					sb.append("("+ndn.getTag());
					for (int y=0; y<ndn.getAttr().size(); y++){
						String attrcode = ndn.getAttr().elementAt(y).toString();
						sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
						if (y+1<ndn.getAttr().size()){
							sb.append(" ");
						}
					}
					sb.append(") ");
				}else{
					addDebug("NO entity found for next ndn.getEntityType(): "+ndn.getEntityType());
				}
			}
			navName.insert(0,sb.toString());

			if (psList != null){
				psList.dereference();
			}
		} // end getting other entity info

		return navName.toString().trim();
	}
	//	used to support getting navigation display name when other entities are needed
	private static class NDN {
		private String etype, tag;
		private NDN next;
		private Vector attrVct = new Vector();
		NDN(String t,String s){
			etype = t;
			tag = s;
		}
		String getTag() { return tag;}
		String getEntityType() { return etype;}
		Vector getAttr(){ return attrVct;}
		void addAttr(String a){
			attrVct.addElement(a);
		}
		void setNext(NDN n) { next = n;}
		NDN getNext() { return next;}
	}
	
	/**
	 * Get last validfrom date of the entity from entity table
	 * @return String
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private String getEntityLastValfrom(EntityItem entity) {
		String lastValFrom = null;
		PreparedStatement ps = null;
		String sql = "SELECT VALFROM FROM OPICM.ENTITY WHERE ENTITYTYPE = ? AND ENTITYID = ? AND VALTO > CURRENT TIMESTAMP AND EFFTO > CURRENT TIMESTAMP with ur";
		try {
			ps = m_db.getPDHConnection().prepareStatement(sql);
			ps.setString(1, entity.getEntityType());
			ps.setInt(2, entity.getEntityID());
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				lastValFrom = result.getString(1);
//				addDebug("getEntityLastValfrom " + lastValFrom);
			}
		} catch (MiddlewareException e){
			addDebug("MiddlewareException on ? " + e);
		    e.printStackTrace();
		} catch (SQLException rx) {
			addDebug("SQLException on ? " + rx);
		    rx.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lastValFrom;
	}
	
	/**
	 * Get last validfrom date of the entity from entity table
	 * @return String
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private String getEntityT2DTS(EntityItem entity) {
		getEntityType();
		String lastValFrom = null;
		PreparedStatement ps = null;
		String attributeCode = null;
		if(getEntityType().equals("XMLPRODPRICESETUP")){
			attributeCode ="ADSPPABRSTATUS";
    	}else{
    		attributeCode = "ADSABRSTATUS";
    	}
		
		
		String sql = "SELECT MAX(VALFROM) FROM OPICM.FLAG WHERE ENTITYTYPE = ? AND ENTITYID = ? and ATTRIBUTECODE= ? AND ATTRIBUTEVALUE='0050' AND EFFTO > CURRENT TIMESTAMP with ur";
		try {
			ps = m_db.getPDHConnection().prepareStatement(sql);
			ps.setString(1, entity.getEntityType());
			ps.setInt(2, entity.getEntityID());
			ps.setString(3, attributeCode);
			ResultSet result = ps.executeQuery();
			while(result.next()) {
				lastValFrom = result.getString(1);
				addDebug("getEntity 0050 Valfrom " + lastValFrom);
			}
		} catch (MiddlewareException e){
			addDebug("MiddlewareException on ? " + e);
		    e.printStackTrace();
		} catch (SQLException rx) {
			addDebug("SQLException on ? " + rx);
		    rx.printStackTrace();
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return lastValFrom;
	}
	public void setT2DTS(String date) {
		addDebug("ADSABRSTATUS setT2DTS:"+date);
    	t2DTS = date;
    }
}
