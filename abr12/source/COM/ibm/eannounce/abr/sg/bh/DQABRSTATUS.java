//Licensed Materials -- Property of IBM

//(C) Copyright IBM Corp. 2007,2011  All Rights Reserved.
//The source code for this program is not published or otherwise divested of
//its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.


package COM.ibm.eannounce.abr.sg.bh;

import COM.ibm.opicmpdh.middleware.*;
import COM.ibm.opicmpdh.objects.*;

import com.ibm.transform.oim.eacm.util.*;

import java.util.*;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.darule.*;
import COM.ibm.eannounce.objects.*;
 
/**********************************************************************************
 * DQABRSTATUS class 
 * This class is an abstract base class for DataQuality ABRs
 * derived classes must implement doDQChecking()
 *  
 * BH FS ABR Data Quality 20120306.doc  - EOS date chk chgs, ORDERSYSNAME added to avail filtering, 
 * chg XCC_LIST processing
 * 
 * BH FS ABR Data Qualtity Sets 20111020e.xls - sets chgs BH Defect 67890 (support for old data)
 *  
 * MNIN454169 - BH FS ABR Data Quality 20110303.doc
 *
 * From "SG FS ABR Data Quality 20091104.doc"
 *
 * DATAQUALITY transitions from 'Draft to Ready for Review',
 *   'Change Request to Ready for Review' and from 'Ready for Review to Final' will queue the ABR.
 * If the ABR requested promotion of STATUS fails, the ABR will reset the DATAQUALITY to the
 * prior value (see below).
 *
 * STATUS:  This is a state machine that has the following values which are only set by the ABR
 *   Draft (default value on create)
 *   Ready for Review
 *   Change Request
 *   Final
 *
 * Results:
 *   The ABR will run and take action based on the Current Value of 'STATUS'.
 *   The ABR will produce a report that is processed by 'Subscription/Notification' as described in the ADD for 30b.
 *
 * If there is no criteria specified, then the ABR should consider the checking is 'Passed'
 *
 * If the ABR checking is 'Passed', then the ABR will set STATUS = DATAQUALITY.
 *
 * If the ABR checking is 'Failed', then the ABR will set DATAQUALTY = STATUS.
 *
 * Although the LONGDESCRIPTIONs for these attributes are identical, the FLAG codes are not.
 *   AttributeCode   FlagCode    LongDescription
 *   ANNSTATUS       0010        Draft
 *   ANNSTATUS       0020        Final
 *   ANNSTATUS       0040        Ready for Review
 *   ANNSTATUS       0050        Change Request
 *   DATAQUALITY     CHANGE      Change Request
 *   DATAQUALITY     DRAFT       Draft
 *   DATAQUALITY     FINAL       Final
 *   DATAQUALITY     REVIEW      Ready for Review
 *   FBSTATUS        0010        Draft
 *   FBSTATUS        0020        Final
 *   FBSTATUS        0040        Ready for Review
 *   FBSTATUS        0050        Change Request
 *   MMSTATUS        0010        Draft
 *   MMSTATUS        0020        Final
 *   MMSTATUS        0040        Ready for Review
 *   MMSTATUS        0050        Change Request
 *   STATUS          0010        Draft
 *   STATUS          0020        Final
 *   STATUS          0040        Ready for Review
 *   STATUS          0050        Change Request
 *
 * Domain support is specified in abr.server.properties
 *Consider    Code    LongDescription
 *Yes 0050    xSeries
 *Yes 0090    SAN
 *Yes 0150    CSP
 *Yes 0190    Midrange Disk
 *Yes 0210    SANB
 *Yes 0230    Mid Range Tape
 *Yes 0240    Complementary Stor Products
 *Yes 0310    SANM
 *Yes 0330    Mid Range Tape 2
 *Yes 0340    Midrange Disk 2
 *Yes 0360    Complementary Stor Products 2
 *Yes 0390    Converged Products
 *
 *ANNABRSTATUS_domains=0050,0090,0150,0190,0210,0230,0240,0310,0330,0340,0360,0390
 */
// DQABRSTATUS.java,v
// Revision 1.63  2011/07/28 15:15:05  wendy
// split debug into separate file to reduce memory needs
//
// Revision 1.56  2011/03/05 00:59:58  wendy
// MNIN454169 - BH FS ABR Data Quality 20110303.doc
//
//Revision 1.49  2011/01/06 13:54:03  wendy
//BH FS ABR Data Quality 20110105 updates
//
//Revision 1.40  2010/07/12 14:58:26  wendy
//chg SVCMOD NDN attr to SMACHTYPEATR

//Revision 1.31  2010/03/18 12:14:19  wendy
//BH FS ABR Data Quality 20100313.doc updates

//Revision 1.27  2010/02/04 12:35:32  wendy
//fix key 85.38

//Revision 1.24  2010/01/22 16:31:13  wendy
//allow for root in sectionone or sectiontwo to not be in final or rfr yet

//Revision 1.22  2010/01/11 19:33:08  wendy
//cvs failure again

//Revision 1.13  2009/12/08 12:16:49  wendy
//cvs failure - restore version and logging

//Revision 1.9  2009/12/02 18:56:32  wendy
//Updated for spec chg BH FS ABR Data Qualtity 20091120.xls

//Revision 1.8  2009/11/10 00:35:16  wendy
//more debug

//Revision 1.7  2009/11/04 15:06:29  wendy
//SR 76 - OTC - Support pre-configured/configured/sw/services offerings

//Revision 1.6  2009/08/18 18:00:15  wendy
//SR10, 11, 12, 15, 17 BH updates

//Revision 1.5  2009/08/17 15:03:31  wendy
//Added headings

//Revision 1.4  2009/08/14 22:27:02  wendy
//SR10, 11, 12, 15, 17 BH updates phase 4

//Revision 1.3  2009/08/06 22:24:31  wendy
//SR10, 11, 12, 15, 17 BH updates phase 3

//Revision 1.2  2009/08/02 19:08:10  wendy
//SR10, 11, 12, 15, 17 BH updates phase 2

//Revision 1.1  2009/07/30 18:54:36  wendy
//Moved to new pkg for BH SR10, 11, 12, 15, 17

public abstract class DQABRSTATUS extends PokBaseABR
{
	private static final int MAXFILE_SIZE=5000000;
	private StringBuffer rptSb = new StringBuffer();
	private Hashtable metaTbl = new Hashtable();

	private static final char[] FOOL_JTEST = {'\n'};
	static final String NEWLINE = new String(FOOL_JTEST);

	private static final Hashtable STATUS_TBL;
	private static final Hashtable DQ_TBL;
	private ResourceBundle dqBundle = null;

	protected static final String STATUS_DRAFT = "0010";
	protected static final String STATUS_FINAL = "0020";
	protected static final String STATUS_R4REVIEW   = "0040";
	protected static final String STATUS_CHGREQ = "0050";
	protected static final String DQ_DRAFT = "DRAFT";
	protected static final String DQ_FINAL = "FINAL";
	protected static final String DQ_R4REVIEW   = "REVIEW";
	protected static final String DQ_CHGREQ = "0050";
	
	protected final static String FOREVER_DATE = "9999-12-31";
	protected final static String EPOCH_DATE = "1980-01-01";

	/*
DATAQUALITY 0050    Change Request
DATAQUALITY DRAFT   Draft
DATAQUALITY FINAL   Final
DATAQUALITY REVIEW  Ready for Review

STATUS  0010    Draft
STATUS  0020    Final
STATUS  0040    Ready for Review
STATUS  0050    Change Request

	 */
	/*
COFCAT	100		Hardware
COFCAT	101		Software
COFCAT	102		Service

COFSUBCAT	126		System
	 */
	/*
SAPLABRSTATUS	0010	Untried
SAPLABRSTATUS	0020	Queued
SAPLABRSTATUS	0030	Passed
SAPLABRSTATUS	0040	Failed
SAPLABRSTATUS	0050	In Process
SAPLABRSTATUS	0060	Error - Update Unsuccessful
SAPLABRSTATUS	0070	Error - DG Not Available
SAPLABRSTATUS	0080	Error - ABR Internal Error
SAPLABRSTATUS	ABRENDOFDAY	Wait until End of the Day
SAPLABRSTATUS	ABRENDOFWEEK	Wait until End of the Week
SAPLABRSTATUS	ABRWAITODS 	Wait for Approved Data ODS
SAPLABRSTATUS	ABRWAITODS2	Wait for Data ODS

	 */

	protected static final String HARDWARE = "100";
	protected static final String SOFTWARE = "101";
	protected static final String SERVICE = "102";
	protected static final String SYSTEM = "126";
	protected static final String BASE = "150";

	protected static final String ABR_QUEUED = "0020";
	protected static final String ABR_PASSED = "0030";
	protected static final String ABR_INPROCESS = "0050";
	protected static final String SAPLABR_QUEUED = "0020";
	
    protected static final String DEFWARR_Yes = "Y1";
    protected static final String DEFWARR_No = "N1";
    protected static final String DEFWARR_No_Desc = "No";
/*
DEFWARR	Y1		Yes
DEFWARR	N1		No
 */
    //BHPRODHIERCD	BHPH0000	00 - No Product Hierarchy Code
    protected static final String BHPRODHIERCD_No_ProdHCode = "BHPH0000";
    
	// hold off on setting ABRWAITODSx, QUEUE immediately for now
	protected static final String ABRWAITODS = ABR_QUEUED;//"ABRWAITODS";
	protected static final String ABRWAITODS2 = ABR_QUEUED;//"ABRWAITODS2";

	//CQ00016165 protected static final String QSM2XPERWEEK = "0090"; // CQ00006066-WI

	protected static final String SAPL_NOTREADY = "10";
	protected static final String SAPL_NA = "90";

	protected static final String PLANNEDAVAIL = "146";
	protected static final String MESPLANNEDAVAIL = "171";
	protected static final String LASTORDERAVAIL = "149";
	protected static final String MESLASTORDERAVAIL = "172";
	protected static final String FIRSTORDERAVAIL = "143";
	protected static final String EOSAVAIL = "151";//	"End of Service" (151)
	protected static final String EOMAVAIL = "200"; //AVAILTYPE	200	End of Marketing
	protected static final String EODAVAIL = "201";//   "End of Development"(201)
	
	protected static final String SPECBID_NO="11457";

	/*FCTYPE	100		Primary FC
	FCTYPE	110		Secondary FC
	FCTYPE	120		RPQ-ILISTED
	FCTYPE	130		RPQ-PLISTED
	FCTYPE	402		Placeholder
	 */
	private static final String RPQ_ILISTED = "120";
	private static final String RPQ_PLISTED = "130";
	private static final String RPQ_RLISTED = "0140";
//	protected static final String PLACEHOLDER = "402";
	
	private static final String PRIMARY_FC = "100";
	private static final String SECONDARY_FC = "110";
	
	private static final Set FCTYPE_SET;
	private static final Set RPQ_FCTYPE_SET;

	protected static final String DOMAIN_NOT_IN_LIST="DOMAIN_NOT_IN_LIST"; // used for OSN checks
	
	protected static final String ANNTYPE_EOL ="14";//		End Of Life - Withdrawal from mktg
	protected static final String ANNTYPE_NEW ="19"; // New
	protected static final String ANNTYPE_EOLDS ="13";//	"End Of Life - Discontinuance of service" (13)

	/*
LIFECYCLE	LF01	Plan
LIFECYCLE	LF02	Develop
LIFECYCLE	LF03	Launch
LIFECYCLE	LF04	Life Cycle
LIFECYCLE	LF05	Archieve
LIFECYCLE	LF06	History

	 */
	protected static final String LIFECYCLE_Develop	= "LF02";// LIFECYCLE	=	"Develop" (LF02)
	protected static final String LIFECYCLE_Plan = "LF01";// LIFECYCLE	=	LF01	Plan
	protected static final String LIFECYCLE_Launch = "LF03";// LIFECYCLE	=	LF01	Plan

	/*
	AVAILANNTYPE	RFA
	AVAILANNTYPE	UnAnnounced
	 */
	protected static final String AVAILANNTYPE_RFA ="RFA";		//	RFA
	protected static final String AVAILANNTYPE_EPIC ="EPIC";		//	RFA
	protected static final String AVAILANNTYPE_UN ="UnAnnounced"; // UnAnnounced
	
	protected static final String SWFCCAT_VALUE_METRIC ="319"; // ValueMetric

	private static final int PAUSE_TIME=5000;
	private static final int DATE_ID = 11;

	protected static final String OLD_DATA_ANNDATE = "2010-03-01";

	private String failedStr = "Failed";  // hang onto this so dont have to get over and over
	private String dqCheck = "Failed";  // Passed, Failed, or Not Required

	private Vector vctReturnsEntityKeys = new Vector();
	private Vector errMsgVct = new Vector(); // prevent duplicate error messages when OSN check is called multiple times

	private static final Hashtable STATUS_ORDER_TBL; //SR10, 11, 12, 15, 17
	private static final Hashtable STATUS_ATTR_TBL;

	private static final String GENAREASELECTION_WW="1999";
	
	private String navName = "";
	private boolean bdomainInList = false;
	private EntityList otherList = null;	

	protected boolean domainInList() {return bdomainInList;}
	private String strNow=null;
	protected String getCurrentDate() {	return strNow;}
	
	private boolean hasWarning = false; // track if we have warning message, default is false
	
	private static String[] warningMsg = {"CAT2W"}; // will set to CAT2 of DGENTITY 
	
	// will not check if it was final before if the entity in the vector
	private static final Vector CHECKFIRSTFINALVEC;
	
	/**
	 * CQ00016165 
	 * ANNABRSTATUS_QSMRPTABRSTATUS_queuedValue=0090
	 * @param abrcode
	 * @return
	 */
	protected String getQueuedValue(String abrcode){
		return
		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRQueuedValue(
				m_abri.getABRCode()+"_"+abrcode);

	}
	protected String getRFRQueuedValue(String abrcode){
		return
		COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRRFRQueuedValue(
				m_abri.getABRCode()+"_"+abrcode);

	}
	private static final Hashtable SAPL_TRANS_TBL;
	private static final Hashtable ABR_ATTR_TBL;
	private static final Hashtable NDN_TBL;
	static{
		FCTYPE_SET = new HashSet();
		FCTYPE_SET.add(PRIMARY_FC);
		FCTYPE_SET.add(SECONDARY_FC);
		
		RPQ_FCTYPE_SET = new HashSet();
		RPQ_FCTYPE_SET.add(RPQ_ILISTED);
		RPQ_FCTYPE_SET.add(RPQ_PLISTED);
		RPQ_FCTYPE_SET.add(RPQ_RLISTED);
		
		NDN_TBL = new Hashtable();
		/*
The NDN of PRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
FEATURE.FEATURECODE
		 */
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
		/*
The NDN of SWPRODSTRUCT is:
MODEL.MACHTYPEATR
MODEL.MODELATR
MODEL.COFCAT
MODEL.COFSUBCAT
MODEL.COFGRP
MODEL.COFSUBGRP
SWFEATURE.FEATURECODE

		 */
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

		/*
The NDN of IPSCSTRUC is:
SVCMOD.MACHTYPEATR
SVCMOD.MODELATR
SVCMOD.SVCMODCATG
SVCMOD.SVCMODSUBCATG
SVCMOD.SVCMODGRP
SVCMOD.SVCMODSUBGRP
IPSCFEAT.FEATURECODE

		ndnMdl = new NDN("SVCMOD", "(TM)");
		ndnMdl.addAttr("SMACHTYPEATR");
		ndnMdl.addAttr("MODELATR");
		ndnMdl.addAttr("SVCMODCATG");
		ndnMdl.addAttr("SVCMODSUBCATG");
		ndnMdl.addAttr("SVCMODGRP");
		ndnMdl.addAttr("SVCMODSUBGRP");
		ndnFc = new NDN("IPSCFEAT", "(FC)");
		ndnFc.addAttr("FEATURECODE");
		ndnMdl.setNext(ndnFc);
		NDN_TBL.put("IPSCSTRUC",ndnMdl); */


		SAPL_TRANS_TBL = new Hashtable();
		SAPL_TRANS_TBL.put("20", "40");  // Ready->Sent
		SAPL_TRANS_TBL.put("30", "40");  // Send->Sent
		SAPL_TRANS_TBL.put("40", "80");  // Sent->Update Sent
		SAPL_TRANS_TBL.put("80", "70");  // Update Sent->Update

		STATUS_TBL = new Hashtable();
		STATUS_TBL.put(DQ_DRAFT, STATUS_DRAFT);
		STATUS_TBL.put(DQ_FINAL, STATUS_FINAL);
		STATUS_TBL.put(DQ_R4REVIEW, STATUS_R4REVIEW);
		STATUS_TBL.put(DQ_CHGREQ, STATUS_CHGREQ);

		DQ_TBL = new Hashtable();
		DQ_TBL.put(STATUS_DRAFT, DQ_DRAFT);
		DQ_TBL.put(STATUS_FINAL, DQ_FINAL);
		DQ_TBL.put(STATUS_R4REVIEW, DQ_R4REVIEW);
		DQ_TBL.put(STATUS_CHGREQ, DQ_CHGREQ);
		STATUS_ORDER_TBL = new Hashtable();
		/*size order
1.	Draft
2.	Change Request
3.	Ready for Review
4.	Final
		 */
		STATUS_ORDER_TBL.put(STATUS_DRAFT, "1");
		STATUS_ORDER_TBL.put(STATUS_CHGREQ, "2");
		STATUS_ORDER_TBL.put(STATUS_R4REVIEW, "3");
		STATUS_ORDER_TBL.put(STATUS_FINAL, "4");

		STATUS_ATTR_TBL = new Hashtable();
		STATUS_ATTR_TBL.put("ANNOUNCEMENT", "ANNSTATUS");
		STATUS_ATTR_TBL.put("FB", "FBSTATUS");
		STATUS_ATTR_TBL.put("MM", "MMSTATUS");

		ABR_ATTR_TBL = new Hashtable(); // needed when looking up values to queue on other entities
		ABR_ATTR_TBL.put("ANNOUNCEMENT", "ANNABRSTATUS");
		ABR_ATTR_TBL.put("AVAIL", "AVAILABRSTATUS");
		ABR_ATTR_TBL.put("CATNAV", "CATNAVABRSTATUS");
		ABR_ATTR_TBL.put("CHRGCOMP", "CHRGCOMPABRSTATUS");
		ABR_ATTR_TBL.put("CVM", "CVMABRSTATUS");
		ABR_ATTR_TBL.put("CVMSPEC", "CVMSPECABRSTATUS");
		ABR_ATTR_TBL.put("FB", "FBABRSTATUS");
		ABR_ATTR_TBL.put("FEATURE", "FCABRSTATUS");
		ABR_ATTR_TBL.put("FCTRANSACTION", "FCTRANSABRSTATUS");
		ABR_ATTR_TBL.put("GBT", "GBTABRSTATUS");
		ABR_ATTR_TBL.put("IMG", "IMGABRSTATUS");
		ABR_ATTR_TBL.put("LSEO", "LSEOABRSTATUS");
		ABR_ATTR_TBL.put("LSEOBUNDLE", "LSEOBDLABRSTATUS");
		ABR_ATTR_TBL.put("MM", "MMABRSTATUS");
		ABR_ATTR_TBL.put("MODEL", "MODELABRSTATUS");
		ABR_ATTR_TBL.put("MODELCONVERT", "MDLCNTABRSTATTUS");
		ABR_ATTR_TBL.put("PRCPT", "PRCPTABRSTATUS");
		ABR_ATTR_TBL.put("PRODSTRUCT", "PRODSTRUCTABRSTATUS");
		ABR_ATTR_TBL.put("SVCMOD", "SVCMODABRSTATUS");
		ABR_ATTR_TBL.put("SVCSEO", "SVCSEOABRSTATUS");
		ABR_ATTR_TBL.put("SVCLEV", "SVCLEVABRSTATUS");
		ABR_ATTR_TBL.put("SLCNTRYCOND", "SLCNTRYABRSTATUS");
		ABR_ATTR_TBL.put("SWFEATURE", "SWFCABRSTATUS");
		ABR_ATTR_TBL.put("SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
		ABR_ATTR_TBL.put("WARR", "WARRABRSTATUS");
		ABR_ATTR_TBL.put("REVUNBUNDCOMP", "REVABRSTATUS");
		ABR_ATTR_TBL.put("SLEORGNPLNTCODE", "SLEORGABRSTATUS");
		ABR_ATTR_TBL.put("WWSEO", "WWSEOABRSTATUS");
		
		CHECKFIRSTFINALVEC = new Vector();
//		if the entity is one of these ("LSEO","LSEOBUNDLE","PRODSTRUCT","SWPRODSTRUCT","SVCMOD","MODEL"), 
//		it will not check if it is final first
		CHECKFIRSTFINALVEC.add("LSEO");
		CHECKFIRSTFINALVEC.add("LSEOBUNDLE");
		CHECKFIRSTFINALVEC.add("PRODSTRUCT");
		CHECKFIRSTFINALVEC.add("SWPRODSTRUCT");
		CHECKFIRSTFINALVEC.add("SVCMOD");
		CHECKFIRSTFINALVEC.add("MODEL");

	}

	private static final String NULL_DTS="2009-01-01-00.00.00.000000";

	protected Object[] args = new String[10];
	private Hashtable updatedTbl = new Hashtable();

	// level of error or warning if check fails
	protected static final int CHECKLEVEL_NOOP = -1;
	protected static final int CHECKLEVEL_W = 1;
	protected static final int CHECKLEVEL_RW = 2;
	protected static final int CHECKLEVEL_RE = 3;
	protected static final int CHECKLEVEL_E = 4;

	// date1 versus date2 check
	protected static final int DATE_GR_EQ = 1; // date1 => date2
	protected static final int DATE_LT_EQ = 2; // date1 <= date2
	protected static final int DATE_GR = 3;    // date1 > date2
	protected static final int DATE_LT = 4;    // date1 < date2
	protected static final int DATE_EQ = 5;    // date1 = date2

	private PrintWriter dbgPw=null;
	private String dbgfn = null;
	private int dbgLen=0;
	private int abr_debuglvl=D.EBUG_ERR; //ANNABRSTATUS_debugLevel=0
	
	private void setupPrintWriter(){
		String fn = m_abri.getFileName();
		int extid = fn.lastIndexOf(".");
		dbgfn = fn.substring(0,extid+1)+"dbg";
		try {
			dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(dbgfn, true), "UTF-8"));
		} catch (Exception x) {
			dbgfn = null;
			D.ebug(D.EBUG_ERR, "trouble creating debug PrintWriter " + x);
		}
	}
	private void closePrintWriter() {
		if (dbgPw != null){
			dbgPw.flush();
			dbgPw.close();
			dbgPw = null;
		}
	}
	
	/**********************************
	 * get the resource bundle
	 */
	protected ResourceBundle getBundle() {
		return dqBundle;
	}

	/**
	 * IF		FCTYPE	=	Primary FC (100) | "Secondary FC" (110)		-> not RPQ
	 * @param featitem
	 * @return
	 */
	protected boolean isRPQ(EntityItem featitem){
		return !PokUtils.contains(featitem, "FCTYPE", FCTYPE_SET);
	}
	
	protected boolean isQSMRPQ(EntityItem featitem){
		return PokUtils.contains(featitem, "FCTYPE", RPQ_FCTYPE_SET);
	}
	
	protected boolean isQsmANNTYPE(String annType){
		addDebug("isQsmANNTYPE annType " + annType);
//		return (!ANNTYPE_EOL.equals(annType)) && (!ANNTYPE_EOLDS.equals(annType));
		/* From Tatiana in chat group
		 	these are the types that should trigger it: 
 			End Of Life - Discontinuance of service  
			End Of Life - Withdrawal from mktg  
			New  
		 */
		return true;
	}
	/**
	 *  Execute ABR.
	 *
	 */
	public void execute_run()
	{
		/*
        The Report should identify:
        �	USERID (USERTOKEN)
		�	Role
		�	Workgroup
		�	Date/Time
		�	The Long Description of the Entity Type being checked
		�	The Navigation Display Name of the entity being checked
		�	Data Quality Checking = {Passed or Failed or Not Required}
		�	Data Quality Errors (if any) as described in other sections

		 */
		// must split because too many arguments for messageformat, max of 10.. this was 11
		String HEADER = "<head>"+
		EACustom.getMetaTags(getDescription()) + NEWLINE +
		EACustom.getCSS() + NEWLINE +
		EACustom.getTitle("{0} {1}") + NEWLINE +
		"</head>" + NEWLINE + "<body id=\"ibm-com\">" +
		EACustom.getMastheadDiv() + NEWLINE +
		"<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>"+NEWLINE +
		"<tr><th>Userid: </th><td>{0}</td></tr>"+NEWLINE +
		"<tr><th>Role: </th><td>{1}</td></tr>"+NEWLINE +
		"<tr><th>Workgroup: </th><td>{2}</td></tr>"+NEWLINE +
		"<tr><th>Date: </th><td>{3}</td></tr>"+NEWLINE +
		"<tr><th>Description: </th><td>{4}</td></tr>"+NEWLINE +
		"<tr><th>Data quality check: </th><td>{5}</td></tr>"+NEWLINE +
		"</table>"+NEWLINE+
		"<!-- {6} --><br />" + NEWLINE;

		MessageFormat msgf;
		EntityItem rootEntity;
		String rootDesc=null;
		String statusFlag=null;
		String dataQualityFlag=null;
		println(EACustom.getDocTypeHtml()); //Output the doctype and html
		try
		{
			String VEname = "";
			long startTime = System.currentTimeMillis();

			start_ABRBuild(false); // dont pull VE yet

	        abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getABRDebugLevel(m_abri.getABRCode());

			setupPrintWriter();
				
			// there seems to be a race condition, the abr is running before all of the
			// attributes are saved
			// if ABR history is before DATAQUALITY,do extract with later profile
			rootEntity = checkTimestamps();

			dataQualityFlag = getAttributeFlagEnabledValue(rootEntity, "DATAQUALITY");
			statusFlag = getAttributeFlagEnabledValue(rootEntity, getStatusAttrCode());
			addDebug(getStatusAttrCode()+": "+
					PokUtils.getAttributeValue(rootEntity, getStatusAttrCode(),", ", "", false)+" ["+statusFlag+"] "+
					"DATAQUALITY: "+
					PokUtils.getAttributeValue(rootEntity, "DATAQUALITY",", ", "", false)+" ["+dataQualityFlag+"] ");

			if(null == statusFlag || statusFlag.length()==0)  {
				statusFlag = STATUS_FINAL; // default to final
			}
			if(null == dataQualityFlag || dataQualityFlag.length()==0){
				dataQualityFlag = DQ_FINAL; // default to final
			}

			// check if pdhdomain is in domain list for this ABR
			domainNeedsChecks(rootEntity);

			// attempt to improve performance, dont pull VE if it isnt needed
			if (isVEneeded(statusFlag)) {
				//VEname = m_abri.getVEName(); this is the default, allow derived classes to override
				VEname = getVEName(statusFlag, dataQualityFlag);
				// checking timestamps may have changed profile, so get it from the rootEntity
				m_elist = m_db.getEntityList(rootEntity.getProfile(),
						new ExtractActionItem(null, m_db, rootEntity.getProfile(),VEname),
						new EntityItem[] { new EntityItem(null, rootEntity.getProfile(), getEntityType(), getEntityID()) });
			}else{
				// just need a list with root entity
				VEname = "dummy";
			}

			addDebug("Time to get VE: "+(System.currentTimeMillis()-startTime)+" (mseconds)");

			setControlBlock(); // needed for attribute updates

			//get properties file for the base class
			dqBundle = ResourceBundle.getBundle(DQABRSTATUS.class.getName(), ABRUtil.getLocale(m_prof.getReadLanguage().getNLSID()));

			// debug display list of groups
			addDebug("DEBUG: "+getShortClassName(getClass())+" entered for " + getEntityType() + ":" + getEntityID()+
					" extract: "+VEname+" valon: "+rootEntity.getProfile().getValOn()+NEWLINE + PokUtils.outputList(m_elist));

			//Default set to pass
			setReturnCode(PASS);

			failedStr = dqBundle.getString("FAILED"); // "Failed"
			dqCheck = failedStr;  // overwrite local copy with bundle version

			//NAME is navigate attributes
			navName = getNavigationName();

			// get root from VE
			rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
			rootDesc = m_elist.getParentEntityGroup().getLongDescription();

//			fixme remove this.. avoid msgs to test userid
//			setCreateDGEntity(false);	
			
			strNow = m_db.getDates().getNow().substring(0, 10);
			boolean dopostprocessing = true; // only do post processing if status is chgd
			/* Note the ABR is only called when
			 * DATAQUALITY transitions from 'Draft to Ready for Review',
			 *   'Change Request to Ready for Review' and from 'Ready for Review to Final'
			 * it can also be queued when ELEMENTSTATUS goes to 'Final' 
			 * Both DQ and STATUS may be Final or RFR
			 */
			if (statusFlag.equals(STATUS_FINAL)){
				addDebug("Status already final, bypassing checks");
				// already final, so handle this processing
				dqCheck= dqBundle.getString("NOT_REQ"); //"Not Required";
				doAlreadyFinalProcessing(rootEntity);
				dopostprocessing = false;
			}else if (statusFlag.equals(STATUS_R4REVIEW) && dataQualityFlag.equals(DQ_R4REVIEW)){
				addDebug("Status already rfr, bypassing checks");
				// already rfr, so handle this processing
				dqCheck= dqBundle.getString("NOT_REQ"); //"Not Required";
				doAlreadyRFRProcessing(rootEntity);
				dopostprocessing = false;
			}else{
				if(domainInList()){
					doDQChecking(rootEntity,statusFlag);
				}else{
					addDebug("No checking required for domain and will not advance status");
					//Wayne Kehrli: ok - verified the following
					//Global list - if not on the list, fail
					//XCC_List - currently limited to Checks - may add to Sets in future release
					//and the global list can be empty which means all PDHDOMAINs
					//DOMAIN_NOT_SUPPORTED= {} is not supported.  Status will not advance.
					args[0] = getLD_Value(rootEntity, "PDHDOMAIN");
					addError("DOMAIN_NOT_SUPPORTED",args);
				}

				String flagCode = (String) STATUS_TBL.get(dataQualityFlag);
				// update any derived data when moving to ready for review
				if(getReturnCode() == PASS){
					//prevent this from running if mtpd early
					if (doDARULEs() && flagCode.equals(STATUS_R4REVIEW)){
						updateDerivedData(rootEntity);
					}
				}
				if(getReturnCode() == PASS){
					//Advance STATUS to match DATAQUALITY (diff flag codes)
					// do this before posting any other abrs, status must have a timestamp before they do
					setFlagValue(m_elist.getProfile(),getStatusAttrCode(), flagCode);
					dqCheck=dqBundle.getString("PASSED"); // "Passed";

					//if (domainInList()){ // only do this processing if domain was in the list
					//always do the post-processing now, queued abrs will now check if domain is in their list
					// if status=final - do other processing
					if (flagCode.equals(STATUS_FINAL)){
						addDebug("completeNowFinalProcessing ");
						completeNowFinalProcessing();
					}
					// if status=readyForReview - do other processing
					else if (flagCode.equals(STATUS_R4REVIEW)){
						addDebug("completeNowR4RProcessing ");
						completeNowR4RProcessing();
					}
					/*}else{
        				addDebug("Domain processing bypassed because domain was not in the list of domains requiring processing");
						// if status=final - do processing for other domains
						if (flagCode.equals(STATUS_FINAL)){
	        				completeNowFinalProcessingForOtherDomains();
						}
					}*/
				}else{
					// error msg was generated when set to fail
					//Reset DATAQUALITY to match STATUS (diff flag codes)
					String dqflagCode = (String) DQ_TBL.get(statusFlag);
					// remove any previous values
					for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						if (rek.getEntityID() == getEntityID() &&
								rek.getEntityType().equals(getEntityType())){
							//rek.m_vctAttributes.clear();
							removeAttrBeforeCommit(rek);
							break;
						}
					}
					setFlagValue(m_elist.getProfile(),"DATAQUALITY", dqflagCode);
				}
			}
			// handle all pdh updates at once.. all work or all fail
			updatePDH(m_elist.getProfile());

			if(dopostprocessing && getReturnCode()==PASS){
				addDebug("doPostProcessing ");
				//do it here so all updates will be seen - user will not be notified of this change
				doPostProcessing(rootEntity,statusFlag,dataQualityFlag);
			}
		}
		catch(Throwable exc)
		{
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

			// attempt to reset DATAQUALITY to match STATUS (diff flag codes)
			if (statusFlag!=null){
				String flagCode = (String) DQ_TBL.get(statusFlag);
				try{
					// remove any previous values
					for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						if (rek.getEntityID() == getEntityID() &&
								rek.getEntityType().equals(getEntityType())){
							rek.m_vctAttributes.clear();
							break;
						}
					}
					setFlagValue(m_prof,"DATAQUALITY", flagCode);
					setFlagValue(m_prof,getStatusAttrCode(), statusFlag);
					dqCheck=dqBundle.getString("FAILED"); // "Failed";
					updatePDH(m_elist.getProfile());
				}catch(Exception x){}
			}
		}
		finally
		{
			setDGTitle(navName);
			setDGRptName(getShortClassName(getClass()));
			setDGRptClass(getABRCode());
//			If the checking produces one or more “Warning” messages but no “Error” messages, it will set CAT2 = “Warning”. 
//			Otherwise it will not set CAT2 (i.e. leave it empty – aka null). 
//			CAT2 is an attribute in DGENTITY used by Subscription/Notification.
			if(getReturnCode() == PASS && hasWarning){
				setDGCat2(warningMsg);//TODO change the msg to flag
				addDebug("DG infor: " + navName + " : " + getShortClassName(getClass()) + " : " + getABRCode() +" : " + warningMsg.toString());
			}
			
			// make sure the lock is released
			if(!isReadOnly()) {
				clearSoftLock();
			}
			closePrintWriter();
		}

		//Print everything up to </html>
		//Insert Header into beginning of report
		msgf = new MessageFormat(HEADER);
		args[0] = getShortClassName(getClass());
		args[1] = navName;
		String header1 = msgf.format(args);
		msgf = new MessageFormat(HEADER2);
		args[0] = m_prof.getOPName();
		args[1] = m_prof.getRoleDescription();
		args[2] = m_prof.getWGName();
		args[3] = getNow();
		args[4] = rootDesc+": "+navName;

		args[5] = dqCheck;
		args[6] = getABRVersion();

		restoreXtraContent();
		
		rptSb.insert(0, header1+msgf.format(args) + NEWLINE);

		println(rptSb.toString()); // Output the Report
		printDGSubmitString();
		println(EACustom.getTOUDiv());
		buildReportFooter(); // Print </html>

		metaTbl.clear();
	}

	/**
	 * if debug content will fit into report, restore it
	 */
	private void restoreXtraContent(){
		// if written to file and still small enough, restore debug to the abr rpt and delete the file
		if (dbgfn!=null && dbgLen+rptSb.length()<MAXFILE_SIZE){
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
	
	/**
	 * SWFEATURE and FEATURE may derive BHINVNAME that must be committed even though ABR fails
	 * @param rek
	 */
	protected void removeAttrBeforeCommit(ReturnEntityKey rek){
		rek.m_vctAttributes.clear();
	}
	/**********************************
	 * check for dataquality
	 * each derived class must handle its own checking
	 */
	protected abstract void doDQChecking(EntityItem rootEntity,String statusFlag) throws Exception;

	/**
	 * from BH FS ABR Catalog Attr Derivation 20100819.doc
	 * C.	Data Quality
	 * As part of the normal process for offering information, a user first creates data in �Draft�. The user 
	 * then asserts the Data Quality (DATAQUALITY) as being �Ready for Review� which queues the Data Quality ABR. 
	 * The DQ ABR checks to ensure that the data is �Ready for Review� and then advances Status (STATUS) to 
	 * �Ready for Review�.
	 * 
	 * The Data Quality ABR will be enhanced such that if the checks pass, then the DQ ABR will process the 
	 * corresponding DARULE. If DARULE is processed successfully, then the DQ ABR will set 
	 * STATUS = �Ready for Review�. If DARULE is not processed successfully, then the DQ ABR will �Fail� 
	 * and return Data Quality to the prior state (Draft or Change Request).
	 * @param rootEntity
	 * @return
	 * @throws Exception
	 */
	protected boolean updateDerivedData(EntityItem rootEntity)throws Exception {
		return false;
	}

	/**
	 * allow a way to turn off catdata generation
	 * @return
	 */
	protected boolean doDARULEs(){
		//remove when DARULEs are ready DARULE_updates=true - only here to prevent this from mtp when other updates go
		String darulesUpdatesStr =  COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("DARULE",
				"_updates","true");
		addDebug("doDARULEs "+darulesUpdatesStr);
		return darulesUpdatesStr.equalsIgnoreCase("true");
	}
	/** 
	 * execute derived data - VE must have CATDATA
	 * @param rootEntity
	 */
	protected boolean execDerivedData(EntityItem rootEntity){
		StringBuffer msgSb = new StringBuffer();
		boolean updatesmade = false;
		try{
			updatesmade = DARuleEngineMgr.updateCatData(m_db, m_prof, rootEntity, msgSb);
			rptSb.append(msgSb.toString()+NEWLINE);
		}catch(Exception e){
			addUserAndErrorMsg(msgSb.toString(),e.getMessage());
			
			java.io.StringWriter exBuf = new java.io.StringWriter();
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into rpt
			addDebug(exBuf.getBuffer().toString());
		}
		return updatesmade;
	}
	
	/**
	 * get workflow action name to use when moving from draft to rfr
	 * @return
	 */
	protected String getLCRFRWFName(){ return null;}
	/**
	 * get workflow action name to use when moving from rfr to final
	 * @return
	 */
	protected String getLCFinalWFName(){ return null;}

	/**********************************
	 * do any post processing like triggering workflow
	 * B.	LIFECYCLE
	 * If the checks pass and the ABR updates STATUS to match DATAQUALITY, then the ABR will also 
	 * update LIFECYCLE based on the change/update to STATUS
	 * -    Draft -> Ready for Review
	 * Set LIFECYCLE = "Develop" (LF02)
	 * -    Ready for Review  ->  Final
	 * Set LIFECYCLE = "Launch" (LF03)
	 * Change Request -> RFR
	 * -	If LIFECYCLE = "Plan" (LF01), Set LIFECYCLE = "Develop" (LF02)
	 * @param rootEntity
	 * @param statusFlag
	 * @throws Exception
	 */
	protected void doPostProcessing(EntityItem rootEntity,String statusFlag, String dataQualityFlag) throws Exception 
	{ 
		if(doR10processing()){ //LIFECYCLE is R1.0
			String rfrwfname = getLCRFRWFName();
			String finalwfname = getLCFinalWFName();
			if (rfrwfname==null || finalwfname==null){
				return;
			}
			EANMetaAttribute metaAttr = rootEntity.getEntityGroup().getMetaAttribute("LIFECYCLE");
			if (metaAttr==null) {
				addDebug("doPostProcessing: "+rootEntity.getKey()+" LIFECYCLE not in "+rootEntity.getEntityGroup()+" meta ");
				return;
			}

			String lifecycle = PokUtils.getAttributeFlagValue(rootEntity, "LIFECYCLE");
			addDebug("doPostProcessing: "+rootEntity.getKey()+" statusFlag "+statusFlag+
					" dataQualityFlag "+dataQualityFlag+" lifecycle "+lifecycle);
			if (STATUS_DRAFT.equals(statusFlag)){ // status was draft, it was moved to rfr
				triggerWorkFlow(rootEntity, rfrwfname); // this can only happen once
			}else if (STATUS_R4REVIEW.equals(statusFlag)){ // status was RFR, it was moved to final
				if (!LIFECYCLE_Launch.equals(lifecycle)){ // may move from RFR to final multiple times, dont set it over and over
					triggerWorkFlow(rootEntity, finalwfname);
				}
			}else if(STATUS_CHGREQ.equals(statusFlag) && DQ_R4REVIEW.equals(dataQualityFlag)){
				// Change Request -> RFR
				// -	If LIFECYCLE = "Plan" (LF01), Set LIFECYCLE = "Develop" (LF02)
				// must handle the case were it was moved to RFR before LIFECYCLE existed
				addDebug("doPostProcessing: "+rootEntity.getKey()+" status was chgreq and dq was rfr lifecycle "+lifecycle);
				if (lifecycle==null || lifecycle.length()==0){ 
					lifecycle = LIFECYCLE_Plan;
				}
				if (LIFECYCLE_Plan.equals(lifecycle)){
					triggerWorkFlow(rootEntity, rfrwfname);
				}
			}
		} // end R10 
	}
	/**********************************
	 * complete abr processing after status moved to final; (status was r4r)
	 */
	protected void completeNowFinalProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{}

	/**********************************
	 * complete abr processing after status moved to readyForReview; (status was chgreq or draft)
	 */
	protected void completeNowR4RProcessing() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{}

	/**********************************
	 * complete abr processing when status is already final; (dq was final too)
	 */
	protected void doAlreadyFinalProcessing(EntityItem rootEntity) throws Exception {
		addOutput("Status and Data Quality were already Final.  No action taken.");
	}
	
	/**********************************
	 * complete abr processing when status is already rfr; (dq was rfr too)
	 */
	protected void doAlreadyRFRProcessing(EntityItem rootEntity) throws Exception {
		addOutput("Status and Data Quality were already Ready For Review.  No action taken.");	
	}

	/**********************************
	 * complete abr processing after status moved to final; (status was r4r) for
	 * those ABRs that have domains that are not in the list
	 */
	protected void completeNowFinalProcessingForOtherDomains() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{}

	/**********************************
	 * override if derived class has a different status attribute
	 */
	protected String getStatusAttrCode() { return "STATUS";}

	/**********************************
	 * override if derived class has different criteria
	 */
	protected boolean isVEneeded(String statusFlag) {
		return true;
	}

	/**
	 * allow derived classes a way to override the VE used when status and dq are the same and checks
	 * are not needed, but some structure may be needed
	 * 
	 * @param statusFlag
	 * @param dataQualityFlag
	 * @return
	 */
	protected String getVEName(String statusFlag, String dataQualityFlag){
		return 	m_abri.getVEName();
	}
	
	/**********************************
	 * add message using resource
	 *
	 * @param resrcCode
	 * @param args
	 */
	protected void addResourceMsg(String resrcCode, Object args[])
	{
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		//INFO_PREFIX = &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("INFO_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), resrcCode, args);
	}

	/**********************************
	 * stringbuffer used for report output
	 */
	protected void addOutput(String msg) { rptSb.append("<p>"+msg+"</p>"+NEWLINE);}

	/**********************************
	 * stringbuffer used for report output
	 */
	protected void addHeading(int level, String msg) { rptSb.append("<h"+level+">"+msg+"</h"+level+">"+NEWLINE);}

	/**********************************
	 * used for error output
	 * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
	 * (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	protected void addError(String errCode, Object args[])
	{
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		//ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
		
		// add this to the debug listing too- so it is located near the cause
		String errmsg = getBundle().getString(errCode);
		if (args!=null){
			msgf = new MessageFormat(errmsg);
			errmsg = msgf.format(args);
		}
		addDebug("ERROR: "+errmsg);
	}
	
	/**
	 * @param usermsg
	 * @param errmsg
	 */
	protected void addUserAndErrorMsg(String usermsg, String errmsg){
		setReturnCode(FAIL);		
		
		rptSb.append(usermsg+NEWLINE);
		
		String msg = errmsg;
		if (msg != null && msg.toLowerCase().startsWith("error")){
			msg = msg.substring("error".length());
		}
		if(msg !=null){ 
			addOutput("<b>Error:</b>"+msg);
		}
	}

	/**********************************
	 * used for warning output
	 * Prefix with LD(EntityType) NDN(EntityType) of the EntityType that the ABR is checking
	 * (root EntityType)
	 *
	 * The entire message should be prefixed with 'Warning: '
	 *
	 */
	protected void addWarning(String errCode, Object args[])
	{
		EntityGroup eGrp = m_elist.getParentEntityGroup();

		//WARNING_PREFIX = Warning: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("WARNING_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
		
		// add this to the debug listing too- so it is located near the cause
		String errmsg = getBundle().getString(errCode);
		if (args!=null){
			msgf = new MessageFormat(errmsg);
			errmsg = msgf.format(args);
		}
		addDebug("WARNING: "+errmsg);
		hasWarning = true;// indicate that we have warning msg
	}

	/**********************************
	 * used for warning or error output
	 *
	 */
	private void addMessage(String msgPrefix, String errCode, Object args[])
	{
		String msg = getBundle().getString(errCode);
		// get message to output
		if (args!=null){
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix+" "+msg);
	}

	/**********************
	 * support conditional msgs
	 * @param level
	 * @param msg
	 */
	protected void addDebug(int level,String msg) { 
		if (level <= abr_debuglvl) {
			addDebug(msg);
		}
	}
	
	/**********************************
	 * stringbuffer used for report output
	 */
	protected void addDebug(String msg) { 
		if(dbgPw!=null){
			dbgLen+=msg.length();
			dbgPw.println(msg);
			dbgPw.flush();
		}else{
			rptSb.append("<!-- "+msg+" -->"+NEWLINE);
		}
	}

	/**********************************
	 * Get root entity item, may not need to pull entire VE
	 */
	private EntityItem getRootEntityItem(Profile profile) throws java.sql.SQLException, MiddlewareException
	{
		m_elist = m_db.getEntityList(profile,
				new ExtractActionItem(null, m_db, profile,"dummy"),
				new EntityItem[] { new EntityItem(null, profile, getEntityType(), getEntityID()) });

		EntityItem  item  = m_elist.getParentEntityGroup().getEntityItem(0);
		return item;
	}

	/**********************************
	 * there seems to be a race condition, the abr is running before all of the
	 * attributes are saved
	 * if ABR timestamp is before DATAQUALITY,do another extract
	 * give PDH time to catch up to taskmaster
	 */
	private EntityItem checkTimestamps() throws Exception
	{
		EntityItem theItem = getRootEntityItem(getProfile());

		if(!checkTimestamps(theItem))   { // if false, timestamps are wrong
			// sleep for 5 seconds to make sure pdh is updated with all values
			try{
				addDebug("Pausing for "+PAUSE_TIME+" ms");
				Thread.sleep(PAUSE_TIME);
			}catch(InterruptedException ie){
				System.out.println(ie);
			}

			// set profile to end of day and try again
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
			String eod = df.format(Calendar.getInstance().getTime()).substring(0,DATE_ID)
			+"23.59.59.999990"; // end of current day
			Profile profile = m_prof.getNewInstance(m_db);
			profile.setValOnEffOn(eod, eod);
			addDebug("ABR started before DATAQUALITY was updated, pull extract again using valon: "+
					profile.getValOn());

			theItem = getRootEntityItem(profile);
		}

		return theItem;
	}

	/**********************************
	 * get timestamps for DATAQUALITY and ABR and compare them
	 * if profile timestamp is before DATAQUALITY return false
	 */
	private boolean checkTimestamps(EntityItem theItem) throws Exception
	{
		boolean isOk = true;
		//EntityItem theItem = theList.getParentEntityGroup().getEntityItem(0);
		Profile profile = theItem.getProfile();

		String abrDts;
		String dqDts;
		String valon = profile.getValOn();

		abrDts = getTimestamp(theItem,getABRCode());
		dqDts  = getTimestamp(theItem,"DATAQUALITY");

		addDebug("DTS for "+getABRCode()+": "+abrDts+" DATAQUALITY: "+dqDts);
		addDebug("DTS for valon: "+valon+" effon "+profile.getEffOn());
		// if dts of valon is before dts of dq, do another pull
//		if (dqDts.length()==0 ||        // DQ isn't set yet!
//		abr may be called on entity that
//		didnt have STATUS or DATAQUALITY attr previously, so assume these are FINAL
		if (dqDts.length()>0 &&        // DQ is set
				valon.compareTo(dqDts)<0)   // current valon is BEFORE DQ
		{
			isOk = false;
		}

		return isOk;
	}

	/**********************************
	 * get last timestamp for specified attribute
	 */
	protected String getTimestamp(EntityItem theItem, String attrCode) throws Exception
	{
		RowSelectableTable itemTable = //theItem.getEntityItemTable(); cant use this because
			// prodstruct can edit parent model and the model isnt in this small extract, so bypass item chks
			new RowSelectableTable(theItem, theItem.getLongDescription());

		String dts = "";
		String keyStr = theItem.getEntityType() + ":" + attrCode;
		int row = itemTable.getRowIndex(keyStr);
		if (row < 0) {
			row = itemTable.getRowIndex(keyStr + ":C");
		}
		if (row < 0) {
			row = itemTable.getRowIndex(keyStr + ":R");
		}
		if (row != -1){
			EANAttribute attStatus = (EANAttribute) itemTable.getEANObject(row, 1);
			if (attStatus != null) {
				int cnt = 0;
				AttributeChangeHistoryGroup ahistGrp = m_db.getAttributeChangeHistoryGroup(m_prof, attStatus);
				rptSb.append("<!--"+theItem.getKey()+" "+attrCode+" change history ");
				for (int ci= ahistGrp.getChangeHistoryItemCount()-1; ci>=0; ci--) // go from most recent to earliest
				{
					ChangeHistoryItem chi = ahistGrp.getChangeHistoryItem(ci);
					rptSb.append(NEWLINE+"AttrChangeHistoryItem["+ci+"] chgDate: "+chi.getChangeDate()+
							" value: "+
							chi.get("ATTVAL",true).toString()+
							" flagcode: "+chi.getFlagCode()+
							" user: "+chi.getUser());
					cnt++;
					if (cnt>3) {  // just last 3 is enough to look at
						break;
					}
				} // each history item
				rptSb.append(" -->"+NEWLINE);
				if (ahistGrp.getChangeHistoryItemCount()>0){
					int last = ahistGrp.getChangeHistoryItemCount()-1;
					dts = ahistGrp.getChangeHistoryItem(last).getChangeDate();
				}
			}
			else {
				addDebug("EANAttribute was null for "+attrCode+" in "+theItem.getKey());
			}
		}
		else {
			addDebug("Row for "+attrCode+" was not found for "+theItem.getKey());
		}

		return dts;
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for root entity
	 *
	 *@return java.lang.String
	 */
	private String getNavigationName() throws java.sql.SQLException, MiddlewareException
	{
		return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
	}

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
	protected String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException
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

	/**********************************************************************************
	 *  Get Name based on navigation attributes for specified entity
	 *
	 *@return java.lang.String
	 */
	protected String getTMFNavigationName(EntityItem theItem, EntityItem mdlItem, EntityItem featItem) throws java.sql.SQLException, MiddlewareException
	{
		StringBuffer navName = new StringBuffer();
		NDN ndn = (NDN)NDN_TBL.get(theItem.getEntityType());
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList)metaTbl.get(theItem.getEntityType());
		if (metaList==null)
		{
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute();  // iterator does not maintain navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii=0; ii<metaList.size(); ii++)
		{
			EANMetaAttribute ma = (EANMetaAttribute)metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(),", ", "", false));
			if (ii+1<metaList.size()){
				navName.append(" ");
			}
		}
		if (ndn!=null){ // must get other attr from parent and child entities
			StringBuffer sb = new StringBuffer();
			EntityItem ei = mdlItem;
			sb.append("("+ndn.getTag());
			for (int y=0; y<ndn.getAttr().size(); y++){
				String attrcode = ndn.getAttr().elementAt(y).toString();
				sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
				if (y+1<ndn.getAttr().size()){
					sb.append(" ");
				}
			}
			sb.append(") ");

			ndn = ndn.getNext();
			if (ndn !=null){
				ei = featItem;
				sb.append("("+ndn.getTag());
				for (int y=0; y<ndn.getAttr().size(); y++){
					String attrcode = ndn.getAttr().elementAt(y).toString();
					sb.append(PokUtils.getAttributeValue(ei, attrcode,", ", "", false));
					if (y+1<ndn.getAttr().size()){
						sb.append(" ");
					}
				}
				sb.append(") ");
			}
			navName.insert(0,sb.toString());
		} // end getting other entity info

		return navName.toString();
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

	/***********************************************
	 *  Get the version
	 *
	 *@return java.lang.String
	 */
	public String getABRVersion()
	{
		return "1.63";
	}
	public void dereference(){
		super.dereference();
		strNow=null;
		rptSb = null;
		metaTbl = null;
		dqBundle = null;
		failedStr = null;
		dqCheck = null;
		errMsgVct.clear();
		errMsgVct = null;
		vctReturnsEntityKeys = null;
		navName = null;
		if (otherList!= null){
			otherList.dereference();
			otherList = null;
		}
		updatedTbl.clear();
		updatedTbl = null;
	}

	/***********************************************
	 * Sets the specified Text Attribute on the specified entity
	 *
	 *@param    profile Profile
	 *@param _sAttributeCode
	 *@param _sAttributeValue
	 *@param eitem
	 */
	protected void setTextValue(Profile profile, String _sAttributeCode, String _sAttributeValue,
			EntityItem eitem)
	{
		logMessage(getDescription()+" ***** "+eitem.getKey()+" "+_sAttributeCode+" set to: " + _sAttributeValue);
		addDebug("setTextValue entered for "+eitem.getKey()+" "+_sAttributeCode+" set to: " + _sAttributeValue);

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(_sAttributeCode);
		if (metaAttr==null) {
			addDebug("setTextValue: "+_sAttributeCode+" was not in meta for "+eitem.getEntityType()+", nothing to do");
			logMessage(getDescription()+" ***** "+_sAttributeCode+" was not in meta for "+
					eitem.getEntityType()+", nothing to do");
			return;
		}

		updatedTbl.put(eitem.getKey(), eitem); // hang onto this for later update msg

		if( _sAttributeValue != null ) {
			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
			}
			ControlBlock cb = m_cbOn;
			if (_sAttributeValue.length()==0){ // deactivation is now needed
				EANAttribute att = eitem.getAttribute(_sAttributeCode);
				String efffrom = att.getEffFrom();
				cb = new ControlBlock(efffrom, efffrom, efffrom, efffrom, profile.getOPWGID());
				_sAttributeValue = att.toString();
				addDebug("setTextValue deactivating "+_sAttributeCode);
			}
			Vector vctAtts = null;
			// look at each key to see if this item is there yet
			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
				if (rek.getEntityID() == eitem.getEntityID() &&
						rek.getEntityType().equals(eitem.getEntityType())){
					vctAtts = rek.m_vctAttributes;
					break;
				}
			}
			if (vctAtts ==null){
				ReturnEntityKey rek = new ReturnEntityKey(eitem.getEntityType(),
						eitem.getEntityID(), true);
				vctAtts = new Vector();
				rek.m_vctAttributes = vctAtts;
				vctReturnsEntityKeys.addElement(rek);
			}
			COM.ibm.opicmpdh.objects.Text sf = new COM.ibm.opicmpdh.objects.Text(profile.getEnterprise(),
					eitem.getEntityType(), eitem.getEntityID(), _sAttributeCode, _sAttributeValue, 1, cb);
			vctAtts.addElement(sf);
		}
	}

	/***********************************************
	 *  Sets the specified Flag Attribute on the specified Entity
	 *
	 *@param    profile Profile
	 *@param    strAttributeCode The Flag Attribute Code
	 *@param    strAttributeValue The Flag Attribute Value
	 */
	protected void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue,
			EntityItem item)
	{
		logMessage(getDescription()+" ***** "+item.getKey()+" "+strAttributeCode+" set to: " + strAttributeValue);
		addDebug("setFlagValue entered "+item.getKey()+" for "+strAttributeCode+" set to: " +
				strAttributeValue);

		if (strAttributeValue!=null && strAttributeValue.trim().length()==0) {
			addDebug("setFlagValue: "+strAttributeCode+" was blank for "+item.getKey()+", it will be ignored");
			return;
		}

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = item.getEntityGroup().getMetaAttribute(strAttributeCode);
		if (metaAttr==null) {
			addDebug("setFlagValue: "+strAttributeCode+" was not in meta for "+item.getEntityType()+", nothing to do");
			logMessage(getDescription()+" ***** "+strAttributeCode+" was not in meta for "+
					item.getEntityType()+", nothing to do");
			return;
		}

		updatedTbl.put(item.getKey(), item); // hang onto this for later update msg

		if(strAttributeValue != null)
		{
			//get the current value
			String curval = //getAttributeFlagEnabledValue(item,strAttributeCode);
				PokUtils.getAttributeFlagValue(item,strAttributeCode);
			if (strAttributeValue.equals(curval) && 
					!strAttributeCode.equals(getStatusAttrCode())){ // allow reset of STATUS if hard fail in update
				addDebug("setFlagValue: "+strAttributeCode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
				logMessage("setFlagValue: "+strAttributeCode+" was already set to "+curval+" for "+item.getKey()+", nothing to do");
				return;
			}

			// if this is queueing an abr, make sure it isnt 'in process' now
			if (metaAttr.getAttributeType().equals("A")){
				// if the specified abr is inprocess, wait
				checkForInProcess(profile,item,strAttributeCode);
			}

			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
			}
			Vector vctAtts = null;
			// look at each key to see if root is there yet
			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
				if (rek.getEntityID() == item.getEntityID() &&
						rek.getEntityType().equals(item.getEntityType())){
					vctAtts = rek.m_vctAttributes;
					break;
				}
			}
			if (vctAtts ==null){
				ReturnEntityKey rek = new ReturnEntityKey(item.getEntityType(),item.getEntityID(), true);
				vctAtts = new Vector();
				rek.m_vctAttributes = vctAtts;
				vctReturnsEntityKeys.addElement(rek);
			}

			SingleFlag sf = new SingleFlag (profile.getEnterprise(), item.getEntityType(), item.getEntityID(),
					strAttributeCode, strAttributeValue, 1, m_cbOn);

			// look at each attr to see if this is there yet
			for (int i=0; i<vctAtts.size(); i++){
				Attribute attr = (Attribute)vctAtts.elementAt(i);
				if (attr.getAttributeCode().equals(strAttributeCode)){
					sf = null;
					break;
				}
			}
			if(sf != null){
				vctAtts.addElement(sf);
			}else{
				addDebug("setFlagValue:  "+item.getKey()+" "+strAttributeCode+" was already added for updates ");
			}
		}
	}

	// if the specified abr is inprocess, wait
	private void checkForInProcess(Profile profile,EntityItem item,String strAttributeCode){
		try{
			int maxTries = 0;
			String curval = // doesnt work on 'A' type attr getAttributeFlagEnabledValue(item,strAttributeCode);
				PokUtils.getAttributeFlagValue(item,strAttributeCode);

			addDebug("checkForInProcess:  entered "+item.getKey()+" "+strAttributeCode+" is "+curval);

			if (ABR_INPROCESS.equals(curval)){
				DatePackage dpNow = m_db.getDates();
				// get current updates by setting to endofday
				profile.setValOnEffOn(dpNow.getEndOfDay(),dpNow.getEndOfDay());

				while(ABR_INPROCESS.equals(curval) && maxTries<20){ // allow 10 minutes
					maxTries++;
					addDebug("checkForInProcess: "+strAttributeCode+" is "+curval+" sleeping 30 secs");
					Thread.sleep(30000);
					// get entity again
					EntityGroup eg = new EntityGroup(null,m_db, profile, item.getEntityType(), "Edit", false);
					EntityItem curItem = new EntityItem(eg, profile, m_db, item.getEntityType(), item.getEntityID());
					curval = PokUtils.getAttributeFlagValue(curItem,strAttributeCode);
					addDebug("checkForInProcess: "+strAttributeCode+" is now "+curval+" after sleeping");
				}
			}
		}catch(Exception exc){
			System.err.println("Exception in checkForInProcess "+exc);
			exc.printStackTrace();
		}
	}

	/**
	 * Triggers the specified workflow
	 * @param item
	 * @param actionName
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 * @throws COM.ibm.eannounce.objects.WorkflowException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	 * 
	 * if action is not defined you get this:
Error: (callGBL8140) Exception: COM.ibm.opicmpdh.middleware.MiddlewareRequestException: GBL8140:EntityType is null

COM.ibm.opicmpdh.middleware.MiddlewareException: (callGBL8140) Exception: COM.ibm.opicmpdh.middleware.MiddlewareRequestException: GBL8140:EntityType is null
	at COM.ibm.opicmpdh.middleware.Database.callGBL8140(Database.java)
	at COM.ibm.eannounce.objects.EntityGroup.buildEntityGroupFromScratch(EntityGroup.java:2349)
	at COM.ibm.eannounce.objects.EntityGroup.(EntityGroup.java:2212)
	at COM.ibm.eannounce.objects.EntityGroup.(EntityGroup.java:2082)
	at COM.ibm.eannounce.objects.WorkflowActionItem.shortWayExecuteAction(WorkflowActionItem.java:1145)
	at COM.ibm.eannounce.objects.WorkflowActionItem.executeAction(WorkflowActionItem.java:1410)
	at COM.ibm.opicmpdh.middleware.Database.executeAction(Database.java:8755)
	at COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS.triggerWorkFlow(DQABRSTATUS.java:1348)
	at COM.ibm.eannounce.abr.sg.bh.SVCMODABRSTATUS.doPostProcessing(SVCMODABRSTATUS.java:1117)
	at COM.ibm.eannounce.abr.sg.bh.DQABRSTATUS.execute_run(DQABRSTATUS.java:585)

	 */
	protected void triggerWorkFlow(EntityItem item, String actionName)
	throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.eannounce.objects.WorkflowException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException
	{
		WorkflowActionItem wfai = null;
		EntityItem[] aItems = new EntityItem[1];
		aItems[0] = item;
		// create a new profile and change timestamp to allow picking up and updated attribute values
		Profile profile = m_prof.getNewInstance(m_db);
		String now = m_db.getDates().getNow();
		profile.setValOnEffOn(now, now);

		addDebug("Triggering workflow "+actionName+" for "+item.getKey());

		wfai = new WorkflowActionItem(null, m_db, profile, actionName);
		wfai.setEntityItems(aItems);
		m_db.executeAction(profile, wfai);
		aItems[0] = null;
	}

	/***********************************************
	 *  Sets the specified Flag Attribute on the Root Entity
	 *
	 *@param    profile Profile
	 *@param    strAttributeCode The Flag Attribute Code
	 *@param    strAttributeValue The Flag Attribute Value
	 */
	protected void setFlagValue(Profile profile, String strAttributeCode, String strAttributeValue)
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		setFlagValue(profile, strAttributeCode, strAttributeValue, rootEntity);
	}

	/***********************************************
	 * Sets the specified Multiflag Attribute on the specified entity
	 *
	 *@param    profile Profile
	 *@param sAttributeCode
	 *@param _sAttributeValue
	 *@param strOldValue
	 *@param eitem
	 */
	protected void setMultiFlagValue(Profile profile, String sAttributeCode, String _sAttributeValue,
			String strOldValue,	EntityItem eitem)
	{
		logMessage(getDescription()+" ***** "+eitem.getKey()+" "+sAttributeCode+" set to: " +
				_sAttributeValue+" oldval "+strOldValue);
		addDebug("setMultiFlagValue entered for "+eitem.getKey()+" "+sAttributeCode+" set to: " +
				_sAttributeValue+" oldval "+strOldValue);

		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = eitem.getEntityGroup().getMetaAttribute(sAttributeCode);
		if (metaAttr==null) {
			addDebug("setMultiFlagValue: "+sAttributeCode+" was not in meta for "+eitem.getEntityType()+", nothing to do");
			logMessage(getDescription()+" ***** "+sAttributeCode+" was not in meta for "+
					eitem.getEntityType()+", nothing to do");
			return;
		}

		if (_sAttributeValue == null){
			_sAttributeValue = "";
		}
		if (strOldValue ==null) {
			strOldValue="";
		}
		updatedTbl.put(eitem.getKey(), eitem); // hang onto this for later update msg

		if (!_sAttributeValue.equals(strOldValue)) {
			if (m_cbOn==null){
				setControlBlock(); // needed for attribute updates
			}

			Vector vctAtts = null;
			// look at each key to see if this item is there yet
			for (int i=0; i<vctReturnsEntityKeys.size(); i++){
				ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
				if (rek.getEntityID() == eitem.getEntityID() &&
						rek.getEntityType().equals(eitem.getEntityType())){
					vctAtts = rek.m_vctAttributes;
					break;
				}
			}
			if (vctAtts ==null){
				ReturnEntityKey rek = new ReturnEntityKey(eitem.getEntityType(),
						eitem.getEntityID(), true);
				vctAtts = new Vector();
				rek.m_vctAttributes = vctAtts;
				vctReturnsEntityKeys.addElement(rek);
			}

			StringTokenizer st = new StringTokenizer(_sAttributeValue, "|");
			MultipleFlag mf = null;
			while (st.hasMoreTokens()) {
				String s = st.nextToken();
				mf = new MultipleFlag(profile.getEnterprise(),
						eitem.getEntityType(), eitem.getEntityID(), sAttributeCode, s, 1, m_cbOn);
				vctAtts.addElement(mf);
			}

			// to remove unwanted flags
			EANAttribute att = eitem.getAttribute(sAttributeCode);
			if (att!=null){ // if null, then was never set
				String efffrom = att.getEffFrom();
				ControlBlock	cbOff = new ControlBlock(efffrom, efffrom, efffrom, efffrom, profile.getOPWGID());

				st = new StringTokenizer(strOldValue, "|");
				while (st.hasMoreTokens()) {
					String s = st.nextToken();
					if (_sAttributeValue.indexOf(s) < 0) {
						mf = new MultipleFlag(profile.getEnterprise(),
								eitem.getEntityType(), eitem.getEntityID(), sAttributeCode, s, 1, cbOff);
						vctAtts.addElement(mf);
					}
				}
			}
		}else{
			addDebug(eitem.getKey()+" old and new values were the same for "+sAttributeCode);
		}
	}

	/***********************************************
	 * Update the PDH with the values in the vector, do all at once
	 *
	 *@param    profile Profile
	 */
	private void updatePDH(Profile profile)
	throws java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	java.rmi.RemoteException,
	COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException,
	COM.ibm.eannounce.objects.EANBusinessRuleException
	{
		logMessage(getDescription()+" updating PDH");
		addDebug("updatePDH entered for vctReturnsEntityKeys: "+vctReturnsEntityKeys);

		if(vctReturnsEntityKeys.size()>0) {
			try {
				m_db.update(profile, vctReturnsEntityKeys, false, false);

				for (int i=0; i<vctReturnsEntityKeys.size(); i++){
					ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
					// must commit text chgs.. not sure why
					for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
						Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
						addDebug("updatePDH : attribute: " + attr.getAttributeCode()+" -  " + attr.getAttributeValue());
						if (attr instanceof Text){
							EntityItem item = (EntityItem)updatedTbl.get(rek.getEntityType()+rek.getEntityID());

							// must commit changes, not really sure why though
							item.commit(m_db, null);
						}
					}
				}
				try{  // dont allow an exception here to reset dataquality
					Hashtable ndnItemTbl = new Hashtable();
					// group all NDN items and do pulls once
					for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						String etype = rek.getEntityType();
						if(NDN_TBL.get(etype) != null){
							Vector vct = (Vector)ndnItemTbl.get(etype);
							if (vct==null){
								vct = new Vector();
								ndnItemTbl.put(etype, vct);
							}
							vct.add(rek);
						}else{
							// output a message for each thing set
							EntityItem item = (EntityItem)updatedTbl.get(rek.getEntityType()+rek.getEntityID());
							outputUpdatedMsg(rek,item);
						}
					}

					for (Enumeration e = ndnItemTbl.keys(); e.hasMoreElements();){
						String etype = (String)e.nextElement();
						Vector vct = (Vector)ndnItemTbl.get(etype);
						if (vct.size()==1){
							ReturnEntityKey rek = (ReturnEntityKey)vct.elementAt(0);
							// output a message for each thing set
							EntityItem item = (EntityItem)updatedTbl.get(rek.getEntityType()+rek.getEntityID());
							outputUpdatedMsg(rek,item);
						}else{
							//pull extract for all and then output them
							EntityList psList = null;
							EntityItem[] eia = new EntityItem[vct.size()];
							for (int ii=0; ii<vct.size(); ii++){
								ReturnEntityKey rek = (ReturnEntityKey)vct.elementAt(ii);
								eia[ii] = new EntityItem(null, getProfile(), rek.getEntityType(),
										rek.getEntityID());
							}

							// performance issue with bringing back too many features and models for prodstruct error msg
							addDebug(" pulling small VE for this "+etype);
							// pull VE with just this prodstruct
							String PS_VE = "EXRPT3FM";  // just feature->ps->model
							if (etype.equals("SWPRODSTRUCT")){	
								PS_VE = "EXRPT3SWFM";  // just swfeature->swps->model
							}
							psList = m_db.getEntityList(getProfile(),
									new ExtractActionItem(null, m_db, getProfile(),PS_VE),
									eia);

							EntityGroup peg = psList.getParentEntityGroup();
							for (int ii=0; ii<vct.size(); ii++){
								ReturnEntityKey rek = (ReturnEntityKey)vct.elementAt(ii);
								EntityItem item = peg.getEntityItem(rek.getEntityType()+rek.getEntityID());
								outputUpdatedMsg(rek,item);
							}	

							psList.dereference();
						}
						vct.clear();
					}

					ndnItemTbl.clear();

					// output a message for each thing set
					/*for (int i=0; i<vctReturnsEntityKeys.size(); i++){
						ReturnEntityKey rek = (ReturnEntityKey)vctReturnsEntityKeys.elementAt(i);
						EntityItem item = (EntityItem)updatedTbl.get(rek.getEntityType()+rek.getEntityID());
						outputUpdatedMsg(rek,item);
					}*/
				}catch(Exception exc){
					exc.printStackTrace();
					addDebug("exception trying to output msg "+exc);
				}
			}
			finally {
				vctReturnsEntityKeys.clear();
				m_db.commit();
				m_db.freeStatement();
				m_db.isPending("finally after updatePDH");
			}
		}
	}

	private void outputUpdatedMsg(ReturnEntityKey rek,EntityItem item) throws SQLException, MiddlewareException{
		MessageFormat msgf = null;

		Hashtable mftbl = new Hashtable();

		for (int ii=0; ii<rek.m_vctAttributes.size(); ii++){
			Attribute attr = (Attribute)rek.m_vctAttributes.elementAt(ii);
			String attrCode = attr.getAttributeCode();
			if (attrCode.equals("DATAQUALITY")){ // must be resetting DQ
				//DQ_RESET = Data quality was reset to {0}
				msgf = new MessageFormat(dqBundle.getString("DQ_RESET"));
				args[0] = PokUtils.getAttributeValue(item, getStatusAttrCode(),", ", "", false);
			}else if (attrCode.equals(getStatusAttrCode())){ // must be setting status
				//STATUS_SET = Status was set to {0}
				if(getReturnCode()==PASS){ // may be restoring status if update error
					msgf = new MessageFormat(dqBundle.getString("STATUS_SET"));
					args[0] = PokUtils.getAttributeValue(item, "DATAQUALITY",", ", "", false);
				}else{
					msgf = null;
				}
			}else{
				//ATTR_SET = {0} was set to {1} for {2} {3}
				msgf = new MessageFormat(dqBundle.getString("ATTR_SET"));
				args[0] = PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode);
				if (attr instanceof Text){
					ControlBlock attrCB = attr.getControlBlock();
					if (attrCB.getValFrom().equals(attrCB.getValTo()) &&
							attrCB.getValTo().equals(attrCB.getEffFrom()) &&
							attrCB.getEffFrom().equals(attrCB.getEffTo())){
						// attribute is getting deactivated
						args[1] = "Null";
					}else{
						args[1] = attr.getAttributeValue();
					}
				}else{
					args[1] = attr.getAttributeValue();
					// get flag description
					EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) item.getEntityGroup().getMetaAttribute(attrCode);
					if (mfa!=null){
						MetaFlag mf = mfa.getMetaFlag(attr.getAttributeValue());
						if (mf!=null){
							args[1] = mf.toString();
						}
					}else{
						addDebug("Error: "+attrCode+" not found in META for "+item.getEntityType());
					}
				}
				// build string of multipleflag values, only output once
				if (attr instanceof MultipleFlag){
					StringBuffer mfSb = (StringBuffer)mftbl.get(attrCode);
					if (mfSb==null){
						mfSb = new StringBuffer();
						mftbl.put(attrCode, mfSb);
					}
					if (mfSb.length()>0){
						mfSb.append(",");
					}

					ControlBlock attrCB = attr.getControlBlock();
					if (attrCB.getValFrom().equals(attrCB.getValTo()) &&
							attrCB.getValTo().equals(attrCB.getEffFrom()) &&
							attrCB.getEffFrom().equals(attrCB.getEffTo())){
						// attribute is getting deactivated leave mfsb empty
					}else{
						mfSb.append(args[1].toString());
					}
				}else{
					args[2] = item.getEntityGroup().getLongDescription();
					args[3] = getNavigationName(item);
				}
			}
			if (!(attr instanceof MultipleFlag) && msgf!=null){
				addOutput(msgf.format(args));
			}
		}// end all rek attributes
		if (mftbl.size()>0){ // output multiflags now
			for (Enumeration e = mftbl.keys(); e.hasMoreElements();)
			{
				String attrCode = (String)e.nextElement();
				StringBuffer mfsb = (StringBuffer)mftbl.get(attrCode);
				//ATTR_SET = {0} was set to {1} for {2} {3}
				msgf = new MessageFormat(dqBundle.getString("ATTR_SET"));
				args[0] = PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode);
				if(mfsb.length()==0){
					// attribute is getting deactivated
					args[1] = "Null";
				}else{
					args[1] = mfsb.toString();
				}
				args[2] = item.getEntityGroup().getLongDescription();
				args[3] = getNavigationName(item);
				addOutput(msgf.format(args));
			}
			mftbl.clear();
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
	private void domainNeedsChecks(EntityItem item)
	{
		String domains = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomains(m_abri.getABRCode());
		addDebug("domainNeedsChecks pdhdomains needing checks: "+domains);
		if (domains.equals("all") || domains.trim().length()==0){
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
			addDebug("PDHDOMAIN did not include "+domains+", checking is bypassed "+
					PokUtils.getAttributeValue(item, "PDHDOMAIN",", ", "", false)+"["+
					getAttributeFlagEnabledValue(item, "PDHDOMAIN")+"]");
		}
	}

	/*
	Wayne Kehrli: how hard is it to add the following
	Wayne Kehrli: if STATUS = Draft and entity last updated before 3/11/2008 - assume STATUS = Final
	Wendy: is this for all status checks?
	Wendy: all entity types?
	Wendy: biggest impact will be performance, i have to go get the timestamp in a separate api
	Wendy: would it be entityhistory or status-attributehistory?
	Wayne Kehrli: entity history
	Wayne Kehrli: current valfrom
	Wayne Kehrli: PRODSTRUCT only
	is STATUS=Final if the prodstruct entitySTATUS=Draft and was updated ON 3/11/2008 or just before 3/11/2008?
	before 3/12/2008
	Wayne Kehrli: all entity types:  if STATUS is not available, assume STATUS = Final
	for PRODSTRUCT only:  if STATUS = Draft and PRODSTRUCT entity last updated before 3/12/2008, then assume STATUS = Final
	 */
	private boolean wasUpdatedSinceDTS(EntityItem psitem, String dts) throws java.sql.SQLException, MiddlewareException
	{
		boolean wasUpdated = false;
		EntityChangeHistoryGroup eChg = new EntityChangeHistoryGroup(m_db, psitem.getProfile(), psitem);
		if (eChg ==null || eChg.getChangeHistoryItemCount()==0) {
			addDebug(psitem.getKey()+" No Change history found");
		}
		else{
			String lastchgdate = eChg.getChangeHistoryItem(eChg.getChangeHistoryItemCount()-1).getChangeDate();
			addDebug(psitem.getKey()+" last chgDate: "+lastchgdate+" vs. "+dts);
			if (lastchgdate.compareTo(dts)>=0){
				wasUpdated = true;
			}

			/*for (int i=0; i<eChg.getChangeHistoryItemCount(); i++)
			{
				ChangeHistoryItem chi = eChg.getChangeHistoryItem(i);
				addDebug(psitem.getKey()+" ChangeHistoryItem["+i+"] user: "+chi.getUser()+" chgDate: "+chi.getChangeDate()+" isValid: "+chi.isValid());
			}*/
		}

		return wasUpdated;
	}

	/**********************************************************************************
	 * Given a root entity type, the path including the relative entity type is given.
	 * This counts the number of Relatives of the EntityType specified via the path and
	 * performs the comparison of this count to the # specified. This is a count of paths
	 * to entities of the type specified so that even if the same instance of the entity
	 * is linked multiple times it is counted multiple times.
	 *
	 * e.g.  FEATUREMON.QTY * CountOf( FEATUREMON-d: MON)  < = 1
	 *
	 * Note:  if FEATUREMON.QTY does not exist, then assume a default of 1
	 *		A Feature can have a maximum of 1 Monitor.
	 *
	 */
	protected int getCount(String relatorType) throws MiddlewareException
	{
		int count = 0;

		EntityGroup relGrp = m_elist.getEntityGroup(relatorType);
		if (relGrp ==null){
			throw new MiddlewareException(relatorType+" is missing from extract for "+m_abri.getVEName());
		}
		if (relGrp.getEntityItemCount()>0){
			for(int i=0; i<relGrp.getEntityItemCount(); i++){
				int qty = 1;
				EntityItem relItem = relGrp.getEntityItem(i);
				EANAttribute attr = relItem.getAttribute("QTY");
				if (attr != null){
					qty = Integer.parseInt(attr.get().toString());
				}
				count+=qty;
				addDebug(D.EBUG_INFO,"getCount "+relItem.getKey()+" qty "+qty);
			}
		}

		addDebug("getCount Total count found for "+relatorType+" = "+count);
		return count;
	}

	/**********************************
	 * RQ022507238
	 * If STATUS is promoted to Final,
	 *   then obtain the history of values for STATUS in order to determine the last date/time stamp (DTS)
	 * that STATUS was Final.
	 * If this is the first time that STATUS is Final, there is nothing to do; otherwise obtain the value
	 * for SAPASSORTMODULE that was in effect at the "last DTS that STATUS was Final" and set SAPASSORTMODULEPRIOR
	 * equal to that value.
	 */
	protected void checkAssortModule() throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		String sapAttr = "SAPASSORTMODULE";
		String sapAttrPrior = "SAPASSORTMODULEPRIOR";

		EANMetaAttribute metaAttr = m_elist.getParentEntityGroup().getMetaAttribute(sapAttrPrior);
		if (metaAttr==null) {
			throw new MiddlewareException(sapAttrPrior+" not found in META for "+rootEntity.getEntityType());
		}
		metaAttr = m_elist.getParentEntityGroup().getMetaAttribute(sapAttr);
		if (metaAttr==null) {
			throw new MiddlewareException(sapAttr+" not found in META for "+rootEntity.getEntityType());
		}

		// status will be final, just isn't yet.. find last occurance
		EANAttribute attStatus = rootEntity.getAttribute("STATUS");
		if (attStatus != null)
		{
			String lastfinaldate=null;
			AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), attStatus);
			addDebug("checkAssortModule: ChangeHistoryGroup for "+rootEntity.getKey()+" Attribute: STATUS");
			if (histGrp.getChangeHistoryItemCount()>0){
				for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--)
				{
					AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
					addDebug("checkAssortModule: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
							" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
					if (chi.getFlagCode().equals(STATUS_FINAL)){
						lastfinaldate = chi.getChangeDate();
						break;
					}
				}
			}
			if (lastfinaldate !=null){ // get value of SAPASSORTMODULE at this DTS
				String curSAPASSORTMODULE = PokUtils.getAttributeValue(rootEntity, sapAttr,", ", "", false).trim();
				String curSAPASSORTMODULEPRIOR = PokUtils.getAttributeValue(rootEntity, sapAttrPrior,", ", "", false).trim();
				// get entityitem for this time
				Profile profile = m_elist.getProfile().getNewInstance(m_db);
				profile.setValOnEffOn(lastfinaldate, lastfinaldate);
				EntityGroup eg = new EntityGroup(null,m_db, profile, rootEntity.getEntityType(), "Edit", false);
				EntityItem oldItem = new EntityItem(eg, profile, m_db, rootEntity.getEntityType(), rootEntity.getEntityID());
				String prevSAPASSORTMODULE = PokUtils.getAttributeValue(oldItem, sapAttr,", ", "", false).trim();
				addDebug("checkAssortModule: "+rootEntity.getKey()+" lastfinaldate "+lastfinaldate+
						" "+sapAttr+" curr:"+curSAPASSORTMODULE+" prev:"+prevSAPASSORTMODULE+
						" "+sapAttrPrior+" curr: "+curSAPASSORTMODULEPRIOR);

				if (!curSAPASSORTMODULEPRIOR.equals(prevSAPASSORTMODULE)){
					setTextValue(m_elist.getProfile(),sapAttrPrior,prevSAPASSORTMODULE, rootEntity);
				}else{
					addDebug("checkAssortModule: "+sapAttrPrior+" current:"+curSAPASSORTMODULEPRIOR+
							" already matches "+sapAttr+" previous:"+prevSAPASSORTMODULE);
				}
			}else{
				addDebug("checkAssortModule: must be first time "+rootEntity.getKey()+" status went final");
			}
		}
		else {
			addDebug("checkAssortModule: Could not get AttributeHistory for "+rootEntity.getKey()+
			" STATUS, it was null");
		}
	}

	/**********************************
	 * If the attribute STATUS is 'Final' and the attribute SAPL is any one of the 'Current Value'
	 * in the table below, then SAPLABRSTATUS is queued
	 * QueueSAPL
	 *
	 * IF SAPL = 20 (Ready) or 30 (Send) or 40 (Sent) or 80 (Update Sent) THEN
	 * Set SAPLABRSTATUS = 0020 (Queued)
	 * ELSE
	 * END IF
	 * /
	protected void queueSapl()
	{
		EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);
		// if meta does not have this attribute, there is nothing to do
		EANMetaAttribute metaAttr = m_elist.getParentEntityGroup().getMetaAttribute("SAPL");
		if (metaAttr==null) {
			addDebug("queueSapl Status=Final but SAPL was not in meta, nothing to do");
		}else{
			// check value of SAPL attribute
			String curVal = getAttributeFlagEnabledValue(rootEntity, "SAPL");
			if (curVal==null){
				curVal = "10"; // default to 'Not Ready'
			}

			// if current val is not in table then don't process
			String setValue = (String)SAPL_TRANS_TBL.get(curVal);
			if (setValue==null){
				addDebug("queueSapl Status=Final but SAPL current value is not in list ["+curVal+"]");
				//SAPL_WRONG_VALUE = Status was &quot;Final&quot; but SAPL was &quot;{0}&quot; so SAPLABRSTATUS was not queued.
				MessageFormat msgf = new MessageFormat(dqBundle.getString("SAPL_WRONG_VALUE"));
				args[0] = PokUtils.getAttributeValue(rootEntity, "SAPL",", ", "", false);
				addOutput(msgf.format(args));
			}else{
				setFlagValue(m_elist.getProfile(),"SAPLABRSTATUS", getQueuedValue("SAPLABRSTATUS"));
			}
		}
	}*/

	/****************************************
	 *I.	PropagateOStoWWSEO
	 *
	 *Syntax:  PropagateOStoWWSEOs
	 *
	 *For Hardware MODELs, propagate OS to all children WWSEOs.
	 *
	 *IF ValueOf(MODELWWSEO:MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODELWWSEO:MODEL.COFGRP) = 150 (Base)
	 *THEN
	 *If MODEL.OS changed since the last time this ABR was queued, then
	 *MODELWWSEO:WWSEO.OS = MODEL.OS
	 *END IF
	 *END IF
	 *
	 */
	protected void propagateOStoWWSEO(EntityItem mdlItem, EntityGroup wwseoGrp) throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException,
	COM.ibm.opicmpdh.middleware.MiddlewareRequestException
	{
		String osAttr = "OS";

		if (wwseoGrp ==null){
			throw new MiddlewareException("WWSEO is missing from extract for "+m_abri.getVEName());
		}

		if (mdlItem ==null){ // could only happen if checks werent done
			addDebug("propagateOStoWWSEO: MODEL was null!!");
			return;
		}

		String modelCOFCAT = getAttributeFlagEnabledValue(mdlItem, "COFCAT");
		String modelCOFGRP = getAttributeFlagEnabledValue(mdlItem, "COFGRP");
		addDebug("propagateOStoWWSEO: "+mdlItem.getKey()+" COFGRP: "+modelCOFGRP+" wwseocnt: "+wwseoGrp.getEntityItemCount());
		if (wwseoGrp.getEntityItemCount()==0){
			// no WWSEO, nothing to do
			return;
		}

		EANMetaAttribute metaAttr = mdlItem.getEntityGroup().getMetaAttribute(osAttr);
		if (metaAttr==null) {
			addDebug("propagateOStoWWSEO ERROR:Attribute "+osAttr+" NOT found in MODEL META data.");
			return;
		}

		//IF ValueOf(MODELWWSEO:MODEL.COFCAT) = 100 (Hardware) & ValueOf(MODELWWSEO:MODEL.COFGRP) = 150 (Base)
		if(HARDWARE.equals(modelCOFCAT) && BASE.equals(modelCOFGRP)) {
			//get attribute history
			EANAttribute attStatus = mdlItem.getAttribute("MODELABRSTATUS");
			if (attStatus != null)	{
				String lastqueueddate=null;
				boolean breakNow = false;
				AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), attStatus);
				addDebug("propagateOStoWWSEO: ChangeHistoryGroup for "+mdlItem.getKey()+" Attribute: MODELABRSTATUS");
				if (histGrp.getChangeHistoryItemCount()>0){
					for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--){
						AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
						addDebug("propagateOStoWWSEO: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
								" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
						if (chi.getFlagCode().equals(ABR_QUEUED)){
							if (lastqueueddate != null){
								// want the queue time before this one
								breakNow = true;
							}
							lastqueueddate = chi.getChangeDate();
							if (breakNow){
								break;
							}
						}
					}
				}

				if (lastqueueddate !=null){ // get value of OS at this DTS
					String curMdlOs = getAttributeFlagEnabledValue(mdlItem, osAttr);
					if (curMdlOs==null){
						curMdlOs = "";
					}

					// get entityitem for this time
					Profile profile = m_elist.getProfile().getNewInstance(m_db);
					profile.setValOnEffOn(lastqueueddate, lastqueueddate);
					EntityGroup eg = new EntityGroup(null,m_db, profile, mdlItem.getEntityType(), "Edit", false);
					EntityItem oldItem = new EntityItem(eg, profile, m_db, mdlItem.getEntityType(), mdlItem.getEntityID());
					String prevMdlOs = getAttributeFlagEnabledValue(oldItem , osAttr);
					if (prevMdlOs==null){
						prevMdlOs = "";
					}

					addDebug("propagateOStoWWSEO: "+mdlItem.getKey()+" lastqueueddate "+lastqueueddate+
							" "+osAttr+" curr: "+curMdlOs+" prev:"+prevMdlOs);

					if (!curMdlOs.equals(prevMdlOs)){
						// look at each WWSEO
						for (int i=0; i<wwseoGrp.getEntityItemCount(); i++){
							EntityItem wwseoItem = wwseoGrp.getEntityItem(i);
							addDebug("propagateOStoWWSEO: updating "+wwseoItem.getKey()+" SEOID "+
									PokUtils.getAttributeValue(wwseoItem, "SEOID",", ", "", false));
							setFlagValue(m_elist.getProfile(),osAttr, curMdlOs,wwseoItem);
						}
					}else{
						addDebug("propagateOStoWWSEO: no changes in "+mdlItem.getKey()+" os since last queued");
					}
				}else{
					addDebug("propagateOStoWWSEO: Could not get queued DTS for "+mdlItem.getKey());
				}

			}
			else {
				addDebug("propagateOStoWWSEO: Could not get AttributeHistory for "+mdlItem.getKey()+
				" MODELABRSTATUS, it was null");
			}
		}
	}
	
	/**
	 * get DTS when this abr previously passed
	 * @param item
	 * @param attrcode
	 * @return
	 * @throws MiddlewareRequestException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	protected String getPrevPassedDTS(EntityItem item, String attrcode) throws MiddlewareRequestException, MiddlewareException, SQLException
	{
		String lastpasseddate=null;
		//get attribute history
		EANAttribute att = item.getAttribute(attrcode);
		if (att != null)	{
			AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), att);
			addDebug("getPrevPassedDTS: ChangeHistoryGroup for "+item.getKey()+" Attribute: "+attrcode);
			if (histGrp.getChangeHistoryItemCount()>0){
				for (int i= histGrp.getChangeHistoryItemCount()-1; i>=0; i--){
					AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
					addDebug("getPrevPassedDTS: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
							" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
					if (chi.getFlagCode().equals(ABR_PASSED)){
						lastpasseddate = chi.getChangeDate();
						break;
					}
				}
			}
		}
		return lastpasseddate;
	}
	
	/**********************************
	 * check for any changes in structure or specified attr between two timestamps
	 * @param root
	 * @param priordts
	 * @param curdts
	 * @param VEname
	 * @param attrOfInterest
	 * @return
	 * @throws java.sql.SQLException
	 * @throws COM.ibm.opicmpdh.middleware.MiddlewareException
	 */
	protected boolean changeOfInterest(EntityItem root, String priordts, String curdts, 
			String VEname, Hashtable attrOfInterest)
	throws
	java.sql.SQLException,
	COM.ibm.opicmpdh.middleware.MiddlewareException
    {
        boolean hadChgs = false;

        if (curdts.equals(priordts)){
            addDebug("changeOfInterest current and prior dts are the same, no changes can exist.");
            return hadChgs;
        }

        // set lastfinal date in profile
        Profile lastprofile = m_elist.getProfile().getNewInstance(m_db);
        lastprofile.setValOnEffOn(curdts, curdts);

        // pull VE
        // create VE for lastfinal time
        EntityList lastFinalList = m_db.getEntityList(lastprofile,
            new ExtractActionItem(null, m_db,lastprofile,VEname),
            new EntityItem[] { new EntityItem(null, lastprofile, root.getEntityType(), root.getEntityID()) });

         // debug display list of groups
        addDebug("changeOfInterest dts: "+curdts+" extract: "+VEname+NEWLINE +
            PokUtils.outputList(lastFinalList));

        // set prior date in profile
        Profile profile = m_elist.getProfile().getNewInstance(m_db);
        profile.setValOnEffOn(priordts, priordts);

        // create VE for prior time
        EntityList list = m_db.getEntityList(profile,
        		new ExtractActionItem(null, m_db,profile,VEname),
        		new EntityItem[] { new EntityItem(null, profile, root.getEntityType(), root.getEntityID()) });

        // debug display list of groups
        addDebug("changeOfInterest dts: "+priordts+" extract: "+VEname+NEWLINE +
        		PokUtils.outputList(list));

        // get the attributes as one string for each entityitem key
        Hashtable currlistRep = getStringRep(lastFinalList, attrOfInterest);
        Hashtable prevlistRep = getStringRep(list, attrOfInterest);

        hadChgs = changeOfInterest(currlistRep, prevlistRep);
        currlistRep.clear();
        prevlistRep.clear();

        list.dereference();
        lastFinalList.dereference();

        return hadChgs;
    }
    /**********************************
     * get hashtable with entitylist converted to strings.  key is entityitem key
     * value is the concatenated list of all attributes of interest.
     * @param list
     * @param attrOfInterest
     * @return
     */
    private Hashtable getStringRep(EntityList list, Hashtable attrOfInterest) {
    	addDebug("getStringRep: entered for profile.valon: "+list.getProfile().getValOn());
    	Hashtable listTbl = new Hashtable();
    	if (attrOfInterest==null){
    		addDebug("getStringRep: coding ERROR attrOfInterest hashtable was null");
    		return listTbl;
    	}
    	EntityGroup eg =list.getParentEntityGroup();
    	String attrlist[] = (String[])attrOfInterest.get(eg.getEntityType());
    	if (attrlist ==null){
    		addDebug("getStringRep: No list of 'attr of interest' found for "+eg.getEntityType());
    	}
    	for (int e=0; e<eg.getEntityItemCount(); e++){
    		EntityItem theItem = eg.getEntityItem(e);
    		String str = generateString(theItem, attrlist);

    		// add empty string for those items without 'attr of interest' for structure chk
    		addDebug("getStringRep: put "+theItem.getKey()+" "+str);
    		listTbl.put(theItem.getKey(),str);
    	}


    	for (int i=0; i<list.getEntityGroupCount(); i++){
    		eg =list.getEntityGroup(i);
    		attrlist = (String[])attrOfInterest.get(eg.getEntityType());
    		if (attrlist ==null){
    			addDebug("getStringRep: No list of 'attr of interest' found for "+eg.getEntityType());
    		}
    		for (int e=0; e<eg.getEntityItemCount(); e++){
    			EntityItem theItem = eg.getEntityItem(e);
    			String str = generateString(theItem, attrlist);

    			// add empty string for those items without 'attr of interest' for structure chk
    			addDebug("getStringRep: put "+theItem.getKey()+" "+str);
    			listTbl.put(theItem.getKey(),str);
    		}
    	}
    	return listTbl;
    }
    /**********************************
     * generate string representation of attributes in the list for this entity
 	 * @param theItem
 	 * @param attrlist
 	 * @return
 	 */
 	protected String generateString(EntityItem theItem, String[] attrlist){
 		StringBuffer sb = new StringBuffer(theItem.getKey());
 		if (attrlist !=null){
 			for (int a=0; a<attrlist.length; a++){
 				sb.append(":"+PokUtils.getAttributeValue(theItem, attrlist[a],", ", "", false));
 			}
 		}else{
            // addDebug("generateString: No list of 'attr of interest' found for "+theItem.getEntityType());
 		}
 		return sb.toString();
 	}
    /**********************************
     * Look at hashtables representing the Entitylists from time1 and time2
     * all we need to know is if there was a difference, not exactly what it was
 	 * @param currlistRep
 	 * @param prevlistRep
 	 * @return
 	 */
 	private boolean changeOfInterest(Hashtable currlistRep,Hashtable prevlistRep){
 		boolean hadChgs = false;
 		if (currlistRep.keySet().containsAll(prevlistRep.keySet()) &&
 			prevlistRep.keySet().containsAll(currlistRep.keySet())) {
 			// structure matches
 			addDebug("changeOfInterest: no change in structure found");

 			// look at attributes
 			if (!(currlistRep.values().containsAll(prevlistRep.values()) &&
 				prevlistRep.values().containsAll(currlistRep.values()))) {
 				hadChgs = true;
 				addDebug("changeOfInterest: difference in values found: "+NEWLINE+"prev "+prevlistRep.values()+
 					NEWLINE+"curr "+currlistRep.values());
 			}else{
 				addDebug("changeOfInterest: no change in values found");
 			}
 		}else{
 			// structure changed
 			hadChgs = true;
 			addDebug("changeOfInterest: difference in keysets(structure) found: "+NEWLINE+"prev "+prevlistRep.keySet()+
 				NEWLINE+"curr "+currlistRep.keySet());
 		}

 		return hadChgs;
 	}
 	
	/****************************************
	 * Check for withdrawn date on the avail
	 *6.	AllValuesOf(LSEOPRODSTRUCT-d: PRODSTRUCT: OOFAVAIL:AVAIL.EFFECTIVEDATE > NOW()
	 *	WHERE AVAIL.AVAILTYPE = 149 (Last Order).
	 *ErrorMessage 'references a withdrawn' LD(PRODSTRUCT) NDN(PRODSTRUCT)
				checkWDAvails("LSEOPRODSTRUCT","PRODSTRUCT","OOFAVAIL",strNow);
	 *
	 *7.	AllValuesOf(LSEOSWPRODSTRUCT-d: SWPRODSTRUCT: SWPRODSTRUCTAVAIL:AVAIL.EFFECTIVEDATE > NOW()
	 *	WHERE AVAIL.AVAILTYPE = 149 (Last Order).
	 *ErrorMessage 'references a withdrawn' LD(SWPRODSTRUCT) NDN(SWPRODSTRUCT)
	 * /
	protected void checkWDAvails(EntityItem rootEntity, String linktype,String etype, String rtype, String strNow)
	throws java.sql.SQLException, MiddlewareException
	{
		Vector psVct = PokUtils.getAllLinkedEntities(rootEntity, linktype, etype);
		addDebug("checkWDAvails entered go thru: "+linktype+" to "+etype+" then thru "+rtype+" found: "+psVct.size()+" "+etype);
		for (int e=0; e<psVct.size(); e++){ // look at each entity
			EntityItem theItem = (EntityItem)psVct.elementAt(e);
			addDebug("checkWDAvails checking entity: "+theItem.getKey()+" for "+rtype);
			for (int i=0; i<theItem.getDownLinkCount(); i++){ // look at relators
				EntityItem link = (EntityItem)theItem.getDownLink(i);
				if (link.getEntityType().equals(rtype)){ // right relator
					// get AVAILS
					for (int ai=0; ai<link.getDownLinkCount(); ai++){ // look at avails
						EntityItem avail = (EntityItem)link.getDownLink(ai);
						addDebug("checkWDAvails checking  "+avail.getKey()+" for lastorder");
						if(PokUtils.isSelected(avail, "AVAILTYPE", LASTORDERAVAIL)){
							String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
							addDebug("checkWDAvails lastorder "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
							if (effDate.length()>0 && effDate.compareTo(strNow)<=0){
								//WITHDRAWN_ERR = references a withdrawn {0} {1}
								args[0] = theItem.getEntityGroup().getLongDescription();
								args[1] = getNavigationName(theItem);
								addError("WITHDRAWN_ERR",args);
							}
						}
					} // end avails
				}
			}
		}
		psVct.clear();
	}

	/************************************
	 * Check that all AVAILs dates comply with the MODEL dates
	 * 
	 * @param mdlItem  MODEL item
	 * @param availVct Vector of a particular type of AVAIL
	 * @param attrCode MODEL attribute code
	 * @param mdlDate value of MODEL attribute code
	 * @param checklvl error or warning msg
	 * @param daterule type of date check to make
	 * @throws SQLException
	 * @throws MiddlewareException
	 * 
	 * 	AVAIL	A								
		AVAILTYPE	=	"Planned Availability" 

		AVAIL	B										
		AVAILTYPE	=	"Last Order"	

        MODEL.ANNDATE	<=	A: AVAIL.EFFECTIVEDATE		W	E	E		
		{LD: AVAIL} {NDN: AVAIL}  can not be earlier than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
		MODEL.WITHDRAWDATE	=>	B: AVAIL.EFFECTIVEDATE		W	E	E		
		{LD: AVAIL} {NDN: AVAIL}   can not be later  than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} 
	 */
	protected void checkModelDates(EntityItem mdlItem, Vector availVct, String attrCode, String mdlDate, 
			int checklvl, int daterule) throws SQLException, MiddlewareException
	{
		if (mdlDate.length()==0){
			return;
		}
		for (int i=0; i<availVct.size(); i++){
			EntityItem avail = (EntityItem)availVct.elementAt(i);
			String availdate = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", checklvl);
			addDebug("checkModelDates  "+avail.getKey()+" EFFECTIVEDATE:"+availdate);
			String errCode=null;
			if (daterule== DATE_GR_EQ){	//date1=>date2	
				//WITHDRAWDATE	=>	B: AVAIL	EFFECTIVEDATE		W	E	E		
				//{LD: AVAIL} {NDN: AVAIL} {LD:EFFECTIVEDATE}{EFFECTIVEDATE} can not be later than the {LD: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} 	
				//CANNOT_BE_LATER_ERR = {0} {1} can not be later than the {2} {3}
				errCode = "CANNOT_BE_LATER_ERR";
			}else{ // date1<=date2
				//ANNDATE	<=	A: AVAIL	EFFECTIVEDATE		W	E	E		
				//{LD: AVAIL} {NDN: AVAIL} {LD:EFFECTIVEDATE}{EFFECTIVEDATE} can not be earlier than the {LD: MODEL} {LD: ANNDATE} {ANNDATE}
				//	CANNOT_BE_EARLIER_ERR2 = {0} {1} can not be earlier than the {2} {3} 
				errCode = "CANNOT_BE_EARLIER_ERR2";
			}

			boolean isok = checkDates(mdlDate, availdate, daterule);
			if (!isok){
				args[0]=getLD_NDN(avail);
				args[1]=this.getLD_Value(avail, "EFFECTIVEDATE");
				args[2] = getLD_NDN(mdlItem);//.getEntityGroup().getLongDescription();
				args[3]=this.getLD_Value(mdlItem, attrCode);
				createMessage(checklvl,errCode,args);
			}
		}// end avail loop  	
    }

	/***************************************
	 * Check each PlannedAvail MODEL->AVAIL.effdate is <= than the PlannedAvail PS->AVAIL.effdate for
	 * the same country
	 * 
	 * Check that each PlannedAvail PS->AVAIL.ctry is a subset of all MODEL->PlannedAvail.ctrys
	 * 
	 * @param mdlItem
	 * @param statusFlag
	 * @param plannedAvailVct
	 * @throws MiddlewareException
	 * @throws SQLException 

modelavail ctry A, B (one or more avails) superset
ps ctry A, svcps ctry B  must be subsets
for plannedavail only

but lastorder doesnt need to be a modelavail

 H.	By Cty
If yes, then both D and G vary by country and the comparison needs to be made for matching countries.

-------------------------------------------------								
27.00	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d								MODEL's AVAIL	
28.00	WHEN		AVAILTYPE	=	"Planned Availability"							
29.00			EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	E	E	SWFEATURE can not be available before the MODEL is announced by country	
{LD: AVAIL} {NDN: A:AVAIL} must not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
30.00		COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST	Yes	W	E	E		
{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a country that the {LD: MODEL} is not available in.
31.00	END	28.00										
	 */
	protected void checkPsModelAvail(EntityList mdlList, EntityItem mdlItem, String statusFlag, 
			Vector plannedAvailVct, String psRelType) 
	throws MiddlewareException, SQLException
	{
		// get avails from mdl extract
		EntityGroup mdlAvailGrp = mdlList.getEntityGroup("AVAIL");
		if (mdlAvailGrp ==null){
			throw new MiddlewareException("AVAIL is missing from extract for "+mdlList.getParentActionItem().getActionItemKey());
		}
		if (plannedAvailVct.size()>0){
			Vector mdlPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", PLANNEDAVAIL);
			Hashtable mdlplaAvailCtryTbl = getAvailByCountry(mdlPlannedAvailVct, CHECKLEVEL_E);

			addDebug("checkPsModelAvail  mdlplannedAvailVct.size "+mdlPlannedAvailVct.size()+
					" mdlplaAvailCtryTbl "+mdlplaAvailCtryTbl.keySet());

			if(mdlPlannedAvailVct.size()>0){
				// PRODSTRUCT 		W	W	E
				// SWPRODSTRUCT 	W	E	E
				int checklvl2 = getCheck_W_W_E(statusFlag);  //PRODSTRUCT root

				int checklvl = getCheck_W_RW_RE(statusFlag);  //PRODSTRUCT root
				if (this.getEntityType().equals("SWPRODSTRUCT")){
					checklvl = getCheck_W_E_E(statusFlag);  //SWPRODSTRUCT root
					checklvl2 = checklvl;
				}else{ //PRODSTRUCT now has date check override
					checklvl = getCheckLevel(checklvl, mdlItem, "ANNDATE");
					checklvl2 = getCheckLevel(checklvl2, mdlItem, "ANNDATE");
				}

				checkPsAndModelAvails(mdlItem, checklvl, checklvl2,
						mdlplaAvailCtryTbl, plannedAvailVct,psRelType, false);
			}
			mdlPlannedAvailVct.clear();
			mdlplaAvailCtryTbl.clear();
		}else{
			addDebug("checkPsModelAvail no PS-plannedAvailVct to check");
		}
	}

	/******************************
	 * look at all ps plannedavail, they must have a model planned (/firstorder)avail for the same ctry
	 * and the ps plannedavail effdate cannot be earlier than the model plannedavail (/firstorder)effdate for same ctry
	 * @param mdlItem
	 * @param dateChklvl
	 * @param ctryChklvl
	 * @param mdlplaAvailCtryTbl
	 * @param psPlaAvailVct
	 * @param psRelType
	 * @param isModelRoot
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	protected void checkPsAndModelAvails(EntityItem mdlItem, int dateChklvl, int ctryChklvl,
			Hashtable mdlAvailCtryTbl, Vector psPlaAvailVct,String psRelType, boolean isModelRoot) throws MiddlewareException, SQLException
	{
		addDebug("checkPsAndModelAvails mdlAvailCtryTbl ctrys "+mdlAvailCtryTbl.keySet());

		for (int i=0; i<psPlaAvailVct.size(); i++){
			EntityItem psPlaAvail = (EntityItem)psPlaAvailVct.elementAt(i); 
			EntityItem psitem = getAvailPS(psPlaAvail, psRelType);

			//SWPRODSTRUCTAVAIL.COUNTRYLIST	in	MODELAVAILs A: AVAIL	COUNTRYLIST		for model roots
			//MODELPLANNEDAVAIL.COUNTRYLIST	Contains	PSAVAILs A: AVAIL	COUNTRYLIST for prodstruct roots
			EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(psPlaAvail, "COUNTRYLIST", ctryChklvl);
			if (ctrylist != null && ctrylist.toString().length()>0) {
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();

				StringBuffer missingCtrySb = new StringBuffer();
				Vector mdlplaVct = new Vector(); // hold onto model plannedavail for date checks incase same avail for mult ctrys
				for (int im = 0; im < mfArray.length; im++) {						
					if (mfArray[im].isSelected()){
						// get the MODEL-plannedavail for this ctry
						EntityItem mdlplaAvail = (EntityItem)mdlAvailCtryTbl.get(mfArray[im].getFlagCode());
						if (mdlplaAvail==null){
							addDebug("checkPsAndModelAvails "+psitem.getKey()+" PS-plannedavail "+psPlaAvail.getKey()+
									" no MODEL-plannedavail for this ctry "+mfArray[im].getFlagCode());
							// no model plannedavail for this ctry
							//missingCtrySb.append(mfArray[im].getFlagCode()+" ");
							if(missingCtrySb.length()>0){
								missingCtrySb.append(", ");
							}
							missingCtrySb.append(mfArray[im].toString());// get ctry desc
						}else{
							if (!mdlplaVct.contains(mdlplaAvail)){
								mdlplaVct.add(mdlplaAvail);
							}
						}
					}
				}
				// do the date checks now
				String date2 = getAttrValueAndCheckLvl(psPlaAvail, "EFFECTIVEDATE", dateChklvl);
				for (int m=0; m<mdlplaVct.size(); m++){
					EntityItem mdlplaAvail = (EntityItem)mdlplaVct.elementAt(m);
					//32	MODELAVAIL.EFFECTIVEDATE	<=	A: AVAIL	EFFECTIVEDATE	Yes	W	RW	RE		
					//{LD: AVAIL} {NDN: A:AVAIL} {EFFECTIVEDATE} can not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL} {EFFECTIVEDATE}
					String mdldate1 = getAttrValueAndCheckLvl(mdlplaAvail, "EFFECTIVEDATE", dateChklvl);
					addDebug("checkPsAndModelAvails "+psitem.getKey()+" PS-plannedavail "+psPlaAvail.getKey()+" EFFECTIVEDATE:"+date2+
							" cannot be earlier than "+mdlItem.getKey()+" plannedavail "+
							mdlplaAvail.getKey()+" EFFECTIVEDATE:"+mdldate1);
					boolean isok = checkDates(mdldate1, date2, DATE_LT_EQ);	//date1<=date2	
					if (!isok){
						//CANNOT_BE_EARLIER_ERR3 = {0} {1} can not be earlier than the {2} {3} {4}
						//from model {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} can not be earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A: AVAIL}
						//from ps    {LD: AVAIL} {NDN: A:AVAIL} can not be earlier than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
						if (isModelRoot){
							args[0]=getLD_NDN(psitem)+" "+getLD_NDN(psPlaAvail);
							args[2]=mdlItem.getEntityGroup().getLongDescription();
						}else{
							args[0]=getLD_NDN(psPlaAvail);
							args[2]=this.getLD_NDN(mdlItem);
						}

						args[1]=this.getLD_Value(psPlaAvail, "EFFECTIVEDATE");
						args[3]=this.getLD_NDN(mdlplaAvail);
						args[4]=this.getLD_Value(mdlplaAvail,  "EFFECTIVEDATE");
						createMessage(dateChklvl,"CANNOT_BE_EARLIER_ERR3",args);
					}
				}
				mdlplaVct.clear();

				if (missingCtrySb.length()>0){
					addDebug("checkPsAndModelAvails PS-plannedavail "+psPlaAvail.getKey()+" COUNTRYLIST had extra ["+missingCtrySb+"]");
					//MODEL_AVAIL_CTRY_ERR = {0} {1} includes a Country that the {2} is not available in. Extra countries: {3}
					//{LD: AVAIL} {NDN: A: AVAIL} {LD: COUNTRYLIST} includes a country that the {LD: MODEL} is not available in.
					if (isModelRoot){
						args[0]=getLD_NDN(psitem)+" "+getLD_NDN(psPlaAvail);
						args[2]=mdlItem.getEntityGroup().getLongDescription();
					}else{
						args[0]=getLD_NDN(psPlaAvail);
						args[2]=getLD_NDN(mdlItem);
					}
					args[1]=PokUtils.getAttributeDescription(psPlaAvail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
					args[3]=missingCtrySb.toString();
					createMessage(ctryChklvl,"MODEL_AVAIL_CTRY_ERR",args);
				}
			}
			else{
				addDebug("checkPsAndModelAvails PS-plannedavail "+psPlaAvail.getKey()+" COUNTRYLIST was null");
			}
		}// end PS-plannedavail loop
	}

	/***************************************
	 * Check each LastOrderAvail MODEL->AVAIL.effdate is => than the LastOrderAvail PS->AVAIL.effdate for
	 * the same country
	 * 
	 * For each PS->plannedAvail.ctry that matches the MODEL->LastOrderAvail.ctry
	 * there must be a PS->LastOrderAvail.ctry
	 * 
	 * @param mdlList
	 * @param mdlItem
	 * @param statusFlag
	 * @param lastOrderAvailVct - ps lastorder avails
	 * @param plannedAvailVct - ps planned avails
	 * @throws MiddlewareException
	 * @throws SQLException 
	 * 

32.0	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d								
33.0	WHEN		AVAILTYPE	=	"Last Order"						
34.0			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	E	E	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
35.0	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST					
36.0	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		E*1	E*1	E*1 	must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
37.0	END	33										

	 */
	protected void checkPsModelLastOrderAvail(EntityList mdlList, EntityItem mdlItem, String statusFlag, Vector lastOrderAvailVct,
			Vector plannedAvailVct) throws MiddlewareException, SQLException
	{
		// get avails from mdl extract
		EntityGroup mdlAvailGrp = mdlList.getEntityGroup("AVAIL");
		if (mdlAvailGrp ==null){
			throw new MiddlewareException("AVAIL is missing from extract for "+mdlList.getParentActionItem().getActionItemKey());
		}

		if (lastOrderAvailVct.size()>0){
			Vector mdlLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(mdlAvailGrp, "AVAILTYPE", LASTORDERAVAIL);
			Hashtable mdlLoAvailCtryTbl = getAvailByCountry(mdlLoAvailVct,
					getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE"));

			addDebug("checkPsModelLastOrderAvail mdlLoAvailCtryTbl: "+mdlLoAvailCtryTbl);

			int datechecklvl = 0;
			int ctrychklvl = 0;
			if (this.getEntityType().equals("SWPRODSTRUCT")){
				datechecklvl = getCheck_W_E_E(statusFlag);
				ctrychklvl = getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE");  //SWPRODSTRUCT root
			}else{
				datechecklvl = getCheckLevel(getCheck_W_W_E(statusFlag),mdlItem,"ANNDATE");  //PRODSTRUCT root
				ctrychklvl = getCheckLevel(CHECKLEVEL_E,mdlItem,"ANNDATE");
			}
			for (int i=0; i<lastOrderAvailVct.size(); i++){
				EntityItem avail = (EntityItem)lastOrderAvailVct.elementAt(i); 

				EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(avail, "COUNTRYLIST", ctrychklvl);
				if (ctrylist != null && ctrylist.toString().length()>0) {
					// Get the selected Flag codes.
					MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();

					StringBuffer missingCtrySb = new StringBuffer();
					Vector mdlloVct = new Vector(); // hold onto model lastorderavail for date checks incase same avail for mult ctrys
					for (int im = 0; im < mfArray.length; im++) {						
						if (mfArray[im].isSelected()){
							// get the MODEL-lastorderavail for this ctry
							EntityItem mdlloAvail = (EntityItem)mdlLoAvailCtryTbl.get(mfArray[im].getFlagCode());
							if (mdlloAvail==null){
								addDebug("checkPsModelLastOrderAvail PS-lastorder:"+avail.getKey()+
										" No MODEL-lastorderavail for ctry "+mfArray[im].getFlagCode());
								// no model lastorderavail for this ctry
								missingCtrySb.append(mfArray[im].getFlagCode()+" ");
							}else{
								if (!mdlloVct.contains(mdlloAvail)){
									mdlloVct.add(mdlloAvail);
								}
							}
						}
					}
					String date2 = getAttrValueAndCheckLvl(avail, "EFFECTIVEDATE", datechecklvl);
					// do the date checks now
					for (int m=0; m<mdlloVct.size(); m++){
						EntityItem mdlloAvail = (EntityItem)mdlloVct.elementAt(m);
						//32.0	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d								
						//33.0	WHEN		AVAILTYPE	=	"Last Order"						
						//34.0			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	E	E	{LD: AVAIL} {NDN: B:AVAIL} must not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
						//-------------------------------------
						//77.0	AVAIL		PRODSTRUCT-d: MODELAVAIL-d								MODEL's AVAIL	
						//78.0	WHEN		AVAILTYPE	=	"Last Order"							
						//79.0			EFFECTIVEDATE	=>	B: AVAIL	EFFECTIVEDATE	Yes	W	W	E*1	{LD: AVAIL} {NDN: A: AVAIL} must not be later than the {LD: MODEL} {LD: AVAIL} {NDN: AVAIL}
						String mdldate1 = getAttrValueAndCheckLvl(mdlloAvail, "EFFECTIVEDATE", datechecklvl);
						addDebug("checkPsModelLastOrderAvail  model lastorder: "+
								mdlloAvail.getKey()+" EFFECTIVEDATE:"+mdldate1+" PS-lastorder:"+
								avail.getKey()+" EFFECTIVEDATE:"+date2);
						boolean isok = checkDates(mdldate1, date2, DATE_GR_EQ);	//date1=>date2	
						if (!isok){
							//CANNOT_BE_LATER_ERR2 = {0} {1} can not be later than the {2} {3} {4}
							//{LD: AVAIL} {NDN: B:AVAIL} {EFFECTIVEDATE} can not be later than the {LD: MODEL} {NDN: MODEL} {LD: AVAIL} {NDN: AVAIL}
							args[0]=getLD_NDN(avail);
							args[1]=this.getLD_Value(avail, "EFFECTIVEDATE");
							args[2]=this.getLD_NDN(mdlItem);
							args[3]=this.getLD_NDN(mdlloAvail);
							args[4]=this.getLD_Value(mdlloAvail,  "EFFECTIVEDATE");
							createMessage(datechecklvl,"CANNOT_BE_LATER_ERR2",args);
						}
					}
					mdlloVct.clear();
					if (missingCtrySb.length()>0){
						addDebug("checkPsModelLastOrderAvail PS-lastorder:"+avail.getKey()+
								" COUNTRYLIST had ctry ["+missingCtrySb+"] that were not in any lastorder MODELAVAIL");
					}
				}
				else{
					addDebug("checkPsModelLastOrderAvail PS-lastorder: "+avail.getKey()+" COUNTRYLIST was null");
				}
			}// end avail loop

			if (plannedAvailVct.size()>0 && mdlLoAvailCtryTbl.size()>0){

				ArrayList lastOrderAvlCtry = getCountriesAsList(lastOrderAvailVct, ctrychklvl);
				addDebug("checkPsModelLastOrderAvail PS-lastOrderAvlCtry "+lastOrderAvlCtry);
				//32.0	AVAIL		SWPRODSTRUCT-d: MODELAVAIL-d
				//35.0	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST					
				//36.0	THEN		TheMatch	IN	B: AVAIL	COUNTRYLIST		E*1	E*1	E*1 	must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
				//37.0	END	33	
				//-------------------------------------
				//77.00	AVAIL		PRODSTRUCT-d: MODELAVAIL-d								MODEL's AVAIL	
				//80.00	IF		COUNTRYLIST	Match	A: AVAIL	COUNTRYLIST	Yes  {LD: AVAIL} {NDN: AVAIL} for a {LD: FEATURE} can not be withdrawn from a country that it is not available in
				//81.00	THEN		TheMatch	IN	B: AVAIL	EFFECTIVEDATE	Yes	E*1	E*1	E*1 	must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
				//82.00	END	78			

				// For each PS->plannedAvail.ctry that matches the MODEL->LastOrderAvail.ctry
				// there must be a PS->LastOrderAvail.ctry
				for (int i=0; i<plannedAvailVct.size(); i++){
					EntityItem avail = (EntityItem)plannedAvailVct.elementAt(i); 
					EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(avail, "COUNTRYLIST", ctrychklvl);
					if (ctrylist != null && ctrylist.toString().length()>0) {
						// Get the selected Flag codes.
						MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();

						Vector mdlloVct = new Vector(); // hold onto model lastorderavail in case mult ctrys match
						for (int im = 0; im < mfArray.length; im++) {						
							if (mfArray[im].isSelected() &&
									!lastOrderAvlCtry.contains(mfArray[im].getFlagCode())){
								addDebug("checkPsModelLastOrderAvail PS-plannedavail:"+avail.getKey()+
										" No PS lastorderavail for ctry "+mfArray[im].getFlagCode());
								// get the MODEL-lastorderavail for this ctry
								EntityItem mdlloAvail = (EntityItem)mdlLoAvailCtryTbl.get(mfArray[im].getFlagCode());
								if (mdlloAvail!=null){
									addDebug("checkPsModelLastOrderAvail PS-plannedavail:"+avail.getKey()+
											" MODEL-lastorderavail for ctry "+mfArray[im].getFlagCode());
									if (!mdlloVct.contains(mdlloAvail)){
										mdlloVct.add(mdlloAvail);
									}
								}
							}
						}
						// output msg for all mdl lastorder that didnt have an ps lastorder
						for (int m=0; m<mdlloVct.size(); m++){
							EntityItem mdlloAvail = (EntityItem)mdlloVct.elementAt(m);
							//PS_LAST_ORDER_ERR = must have a "Last Order" {0} corresponding to {1} {2}
							//must have a "Last Order" {LD: AVAIL} corresponding to {LD: MODEL} {NDN:MODEL} {LD: AVAIL} {NDN: AVAIL}
							args[0]=mdlloAvail.getEntityGroup().getLongDescription();
							args[1]=getLD_NDN(mdlItem);//.getEntityGroup().getLongDescription();
							args[2]=getLD_NDN(mdlloAvail);
							createMessage(ctrychklvl,"PS_LAST_ORDER_ERR",args);
						}
						mdlloVct.clear();           				
					}
				}

				lastOrderAvlCtry.clear();
			}

			mdlLoAvailVct.clear();
			mdlLoAvailCtryTbl.clear();
		}else{
			addDebug("checkPsModelLastOrderAvail no PS-lastorderAvailVct to check");
		}
	}    
	//===========================================================
	//====== Utilities =======================
	//===========================================================
	/**
	 * Get hashtable key=ORDERSYSNAME, value=availitem
	 * 
	 * List Name is optional and if supplied, then this check is only applicable to the PDHDOMAINs found in the list. 
	 * If a List Name is not specified, then OSN applies to all PDHDOMAINs and is required.
	 * 
	 * Although ORDERSYSNAME does not have an EXIST local rule, it is required for this check if the check is 
	 * applicable. If required and empty, then report an error. 
	 * {LD: AVAIL} {NDN: AVAIL} must have an {LD: ORDERSYSNAME}.
	 * REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
	 * 
	 * If the AVAIL PDHDOMAIN is on the List, then compare AVAILS where ORDERSYSNAME match. All AVAILs with PDHDOMAIN 
	 * on the list must have an ORDERSYSNAME.
	 * 
	 * If the AVAIL PDHDOMAIN is not on the List, then ignore ORDERSYSNAME. All AVAILs with PDHDOMAIN not on the list 
	 * are compared with each other. 
	 * 
	 * @param availOSNTbl
	 * @param availVct
	 * @param useXCClistName
	 * @param checklvl
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected boolean getAvailByOSN(Hashtable availOSNTbl,Vector availVct, boolean useXCClistName, int checklvl) 
	throws SQLException, MiddlewareException
	{
		boolean hasErrors = false;
		for (int i=0; i<availVct.size(); i++){
			EntityItem avail = (EntityItem)availVct.elementAt(i);
			String ordersysname = null;
			if(useXCClistName){
				if (domainInRuleList(avail, "XCC_LIST")){ 
					ordersysname = PokUtils.getAttributeFlagValue(avail, "ORDERSYSNAME");
					addDebug("getAvailByOSN "+avail.getKey()+" is in the xcclist osn: "+ordersysname);
					if (ordersysname == null) {
						if(!errMsgVct.contains(avail.getKey())){
							errMsgVct.add(avail.getKey());
							//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
							args[0]=getLD_NDN(avail);
							args[1]=PokUtils.getAttributeDescription(avail.getEntityGroup(), "ORDERSYSNAME", "ORDERSYSNAME");
							createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);
						}
						hasErrors = true;
						continue;
					}
				}else{
					//If the AVAIL PDHDOMAIN is not on the List, then ignore ORDERSYSNAME. All AVAILs with 
					//PDHDOMAIN not on the list are compared with each other. 
					addDebug("getAvailByOSN "+avail.getKey()+" is not in the xcclist");
					ordersysname = DOMAIN_NOT_IN_LIST; //use this as the key for AVAILs that do not match domain
				}
			}else{
				//If a List Name is not specified, then OSN applies to all PDHDOMAINs and is required.
				ordersysname = PokUtils.getAttributeFlagValue(avail, "ORDERSYSNAME");
				addDebug("getAvailByOSN "+avail.getKey()+" no xcclist osn: "+ordersysname);
				if (ordersysname == null) {
					if(!errMsgVct.contains(avail.getKey())){
						errMsgVct.add(avail.getKey());
						//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
						args[0]=getLD_NDN(avail);
						args[1]=PokUtils.getAttributeDescription(avail.getEntityGroup(), "ORDERSYSNAME", "ORDERSYSNAME");
						createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);
					}
					hasErrors = true;
					continue;
				}
			}

			Vector tmp = (Vector)availOSNTbl.get(ordersysname);
			if(tmp == null){
				tmp = new Vector();
				availOSNTbl.put(ordersysname,tmp);
			}
			tmp.add(avail);		
		}
		
		return hasErrors;
	}
	
	/**
	 * 24.00	AVAIL	H	PRODSTRUCT-u:OOFAVAIL-d		
	 * 25.00	WHEN		AVAILTYPE	=	"First Order"
	 * 26.00	COUNTRYLIST	in	A: AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
	 * and
	 * 111.00	AVAIL	K	PRODSTRUCT-u: OOFAVAIL-d
	 * 112.00	WHEN		AVAILTYPE	=	"End of Service" (151)
	 * 114.00	COUNTRYLIST	IN	C:AVAIL	COUNTRYLIST	OSN:XCC_LIST	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT}{LD: AVAIL} {NDN: AVAIL}  {LD: COUNTRYLIST} includes a country that does not have a "Last Order"
	 * 
	 * @param leftsideAvailTbl
	 * @param rightsideAvailTbl
	 * @param errcode
	 * @param psitem
	 * @param useXCClistName
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkAvailCtryByOSN(Hashtable leftsideAvailTbl,Hashtable rightsideAvailTbl, String errcode, 
			EntityItem psitem, boolean useXCClistName, int checklvl) 
	throws SQLException, MiddlewareException
	{
		addDebug("checkAvailCtryByOSN useXCClistName "+useXCClistName);
		String psinfo = "";
		if(psitem !=null){
			psinfo = getLD_NDN(psitem)+" ";
		}
		
		// all leftside OSN must have country lists that are in the rightside
		// use the set of leftside keys 
		Set leftsideKeys = leftsideAvailTbl.keySet();
		Iterator itr = leftsideKeys.iterator();
		while(itr.hasNext()){
			String osn = (String)itr.next();
			// get avails from each table for this osn
			Vector leftsideOsnVct = (Vector)leftsideAvailTbl.get(osn);
			Vector rightsideOsnVct = (Vector)rightsideAvailTbl.get(osn);
			// countrylist of avails in leftside vector must be in the countrylists of the avails in the rightside vector
			if(rightsideOsnVct == null){
				addDebug("checkAvailCtryByOSN no rightside avails to check for osn "+osn);

				//add error	output each avail with its countrylist
				for(int i=0;i<leftsideOsnVct.size();i++){
					EntityItem avail = (EntityItem)leftsideOsnVct.elementAt(i);
					addDebug("checkAvailCtryByOSN "+avail.getKey()+" did not have any osn match in rightside set");
					//MISSING_PLA_OSNCTRY_ERR = {0} {1} includes a Country that does not have a &quot;Planned Availability&quot; {2} Extra countries: {3}
					//MISSING_LO_OSNCTRY_ERR = {0} {1} includes a Country that does not have a &quot;Last Order Availability&quot; {2} Extra countries: {3}
					//"{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"" 
				 
					//MODEL_AVAIL_OSNCTRY_ERR = {0} {1} includes a Country that the {2} is not available in {3} Extra countries: {4}
		  		    //{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
					if(errcode.equals("MODEL_AVAIL_OSNCTRY_ERR")){
						args[0]= getLD_NDN(avail);
						args[1]= PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
						args[2]= psinfo; // really model info here
						if(osn.equals(DOMAIN_NOT_IN_LIST)){
							args[3]= "";
						}else{
							args[3]= "for "+getLD_Value(avail, "ORDERSYSNAME");
						}
						args[4]= PokUtils.getAttributeValue(avail, "COUNTRYLIST", ", ", "");
					}else if(errcode.equals("MODELROOT_AVAIL_OSNCTRY_ERR")){
						//MODELROOT_AVAIL_OSNCTRY_ERR = {0} {1} {2} includes a Country that the Model is not available in {3} Extra countries: {4}
						//{LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} is not available in.
						args[0]= psinfo; 
						args[1]= getLD_NDN(avail);
						args[2]=PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");

						if(osn.equals(DOMAIN_NOT_IN_LIST)){
							args[3]= "";
						}else{
							args[3]= "for "+getLD_Value(avail, "ORDERSYSNAME");
						} 
						args[4]= PokUtils.getAttributeValue(avail, "COUNTRYLIST", ", ", "");
					}else{
						args[0]= psinfo+getLD_NDN(avail);
						args[1]= PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
						if(osn.equals(DOMAIN_NOT_IN_LIST)){
							args[2]= "";
						}else{
							args[2]= "for "+getLD_Value(avail, "ORDERSYSNAME");
						}
						args[3]= PokUtils.getAttributeValue(avail, "COUNTRYLIST", ", ", "");		
					}
					
					createMessage(checklvl,errcode,args);
				}
				continue;
			}
			// get the countrylists for these avails
			ArrayList osnctrylist = getCountriesAsList(rightsideOsnVct, checklvl);
			addDebug("checkAvailCtryByOSN osn "+osn+" rightside osnctrylist "+osnctrylist);
			for(int i=0;i<leftsideOsnVct.size();i++){
				EntityItem avail = (EntityItem)leftsideOsnVct.elementAt(i);
				String missingCtry = checkCtryMismatch(avail, osnctrylist, checklvl);
				if (missingCtry.length()>0){
					addDebug("checkAvailCtryByOSN "+avail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");
					//MISSING_PLA_OSNCTRY_ERR = {0} {1} includes a Country that does not have a &quot;Planned Availability&quot; {2} Extra countries: {3}
					//MISSING_LO_OSNCTRY_ERR = {0} {1} includes a Country that does not have a &quot;Last Order Availability&quot; {2} Extra countries: {3}
					//"{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"" 
					
					//MODEL_AVAIL_OSNCTRY_ERR = {0} {1} includes a Country that the {2} is not available in {3} Extra countries: {4}
		  		    //{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} {NDN: MODEL} is not available in.
					if(errcode.equals("MODEL_AVAIL_OSNCTRY_ERR")){
						args[0]= getLD_NDN(avail);
						args[1]= PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
						args[2]= psinfo; // really model info here
						if(osn.equals(DOMAIN_NOT_IN_LIST)){
							args[3]= "";
						}else{
							args[3]= "for "+getLD_Value(avail, "ORDERSYSNAME");
						}
						args[4]= missingCtry;
					}else if(errcode.equals("MODELROOT_AVAIL_OSNCTRY_ERR")){
						//MODELROOT_AVAIL_OSNCTRY_ERR = {0} {1} {2} includes a Country that the Model is not available in {3} Extra countries: {4}
						//{LD: PRODSTRUCT} {NDN: PRODSTRUCT}{LD: AVAIL} {NDN: A: AVAIL}  {LD: COUNTRYLIST} includes a Country that the {LD: MODEL} is not available in.
						args[0]= psinfo; 
						args[1]= getLD_NDN(avail);
						args[2]=PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");

						if(osn.equals(DOMAIN_NOT_IN_LIST)){
							args[3]= "";
						}else{
							args[3]= "for "+getLD_Value(avail, "ORDERSYSNAME");
						} 
						args[4]= missingCtry;
					}else{
						args[0]=psinfo+getLD_NDN(avail);
						args[1]=PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
						if(osn.equals(DOMAIN_NOT_IN_LIST)){
							args[2]="";
						}else{
							args[2]= "for "+getLD_Value(avail, "ORDERSYSNAME");
						}
						args[3]= missingCtry;
					}
					createMessage(checklvl,errcode,args);
				}
			}
		}
	}
	
	/**
	 * 113.00			EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		
	 * {LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
	 * @param leftsideAvailTbl
	 * @param rightsideAvailTbl
	 * @param psitem prodstruct for the leftside avail
	 * @param checktype DATE_GR_EQ or DATE_LT_EQ
	 * @param checklvl
	 * @param parentDesc parent info for rightside
	 * @param useE3checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkAvailDatesByCtryByOSN(Hashtable leftsideAvailTbl,Hashtable rightsideAvailTbl, 
			EntityItem psitem, int checktype, int checklvl, String parentDesc, boolean useE3checklvl) 
	throws SQLException, MiddlewareException
	{
		String psinfo = "";
		if(psitem !=null){
			psinfo = getLD_NDN(psitem)+" ";
		}
		// all leftside OSN must have country lists that are in the rightside
		// use the set of leftside keys 
		Set leftsideKeys = leftsideAvailTbl.keySet();
		Iterator itr = leftsideKeys.iterator();
		while(itr.hasNext()){
			String osn = (String)itr.next();
			// get avails from each table for this osn
			Vector leftsideOsnVct = (Vector)leftsideAvailTbl.get(osn);
			Vector rightsideOsnVct = (Vector)rightsideAvailTbl.get(osn);
			// countrylist of avails in leftside vector must be in the countrylists of the avails in the rightside vector
			if(rightsideOsnVct == null){
				addDebug("checkAvailDatesByCtryByOSN no rightside avails to check for osn "+osn);
				continue;
			}
			
			Hashtable rightsideAvailCtryTbl = getAvailByCountry(rightsideOsnVct, checklvl);
			addDebug("checkAvailDatesByCtryByOSN osn "+osn+" rightsideAvailCtryTbl "+rightsideAvailCtryTbl.keySet());
			for (int i=0; i<leftsideOsnVct.size(); i++){
				EntityItem leftavail = (EntityItem)leftsideOsnVct.elementAt(i);
				addDebug("checkAvailDatesByCtryByOSN leftside "+leftavail.getKey()+" ctrys "+PokUtils.getAttributeFlagValue(leftavail, "COUNTRYLIST"));
				Vector rightVct = new Vector(); // hold onto avail for date checks incase same avail for mult ctrys
				getMatchingAvails(leftavail, rightsideAvailCtryTbl, rightVct, checklvl);
				// do the date checks now
				for (int m=0; m<rightVct.size(); m++){
					EntityItem rightAvail = (EntityItem)rightVct.elementAt(m);
					int chklvl = checklvl;
					if(useE3checklvl){
						chklvl = getAvailCheckLevel(chklvl, rightAvail); // key 52.00 avail is checked
					}
//		Change 20111216	113.00		EFFECTIVEDATE	=>	C:AVAIL	EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*1		{LD:PRODSTRUCT}{NDN:PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL) must not be earlier than the {LD: AVAIL} {NDN: J:AVAIL)
					//56.00			EFFECTIVEDATE	=>	A:AVAIL EFFECTIVEDATE	"Cty OSN:XCC_LIST"	W	W	E*3		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A: AVAIL}

					// do the date checks now E*3 is only on psavail 
					String date1 = getAttrValueAndCheckLvl(leftavail, "EFFECTIVEDATE", checklvl);
					String date2 = getAttrValueAndCheckLvl(rightAvail, "EFFECTIVEDATE", chklvl);
					addDebug("checkAvailDatesByCtryByOSN "+(psitem==null?"":psitem.getKey())+" "+
							leftavail.getKey()+" EFFECTIVEDATE:"+date1+
							(checktype==DATE_GR_EQ?" GR_EQ ":" LT_EQ ")+" "+rightAvail.getKey()+
							" EFFECTIVEDATE:"+date2);
					boolean isok = checkDates(date1, date2, checktype);	//DATE_GR_EQ date1=>date2	
					if (!isok){
						String errCode = "CANNOT_BE_EARLIER_ERR3";
						if(checktype!=DATE_GR_EQ){
							errCode = "CANNOT_BE_LATER_ERR2";
						}
						//CANNOT_BE_LATER_ERR2 = {0} {1} must not be later than the {2} {3} {4}
						//CANNOT_BE_EARLIER_ERR3 = {0} {1} can not be earlier than the {2} {3} {4}
//						{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} must not be earlier than the {LD: MODEL} {LD:AVAIL} {NDN: A: AVAIL}
						args[0]=psinfo+getLD_NDN(leftavail);
						args[1]=this.getLD_Value(leftavail, "EFFECTIVEDATE");
						args[2]=parentDesc;      
						args[3]=this.getLD_NDN(rightAvail);
						args[4]=this.getLD_Value(rightAvail,  "EFFECTIVEDATE");
						createMessage(chklvl,errCode,args);
					}
				} 
				rightVct.clear();
			}
		}
	}
	/**
	 * Get hashtable key=countryflag, value=availitem
	 * @param availVct these should be AVAILs of the same type
	 * @param checklvl
	 * @return
	 */
	protected Hashtable getAvailByCountry(Vector availVct, int checklvl){
		Hashtable availCtryTbl = new Hashtable();
		for (int i=0; i<availVct.size(); i++){
			EntityItem avail = (EntityItem)availVct.elementAt(i);
			EANAttribute ctrylist = avail.getAttribute("COUNTRYLIST");
			if (ctrylist != null && ctrylist.toString().length()>0) {
				// Get the selected Flag codes.
				MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
				for (int im = 0; im < mfArray.length; im++) {						
					if (mfArray[im].isSelected()) {
						// add error check here, for each offering & availtype, values of countrylist must be unique
						if (availCtryTbl.containsKey(mfArray[im].getFlagCode())){
							String availtype = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");
							addDebug("getAvailByCountry already found "+
									((EntityItem)availCtryTbl.get(mfArray[im].getFlagCode())).getKey()
									+" for ctry["+mfArray[im].getFlagCode()+
									"] on "+avail.getKey()+" with AVAILTYPE "+availtype);
						}
						availCtryTbl.put(mfArray[im].getFlagCode(), avail);
					}
				} //end for
			}
		}
		return availCtryTbl;
	}

	/******************
	 * Check for any missing countries
	 * @param item
	 * @param ctryList
	 * @param checklvl
	 * @return  String of missing country flags
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected String checkCtryMismatch(EntityItem item, Collection ctryList, int checklvl)
	throws SQLException, MiddlewareException
	{
		StringBuffer missingCtrySb = new StringBuffer();
		EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(item, "COUNTRYLIST", checklvl);
		if (ctrylist != null && ctrylist.toString().length()>0) {
			StringBuffer missingCtryFlagSb = new StringBuffer();
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
			for (int im = 0; im < mfArray.length; im++) {						
				if (mfArray[im].isSelected()) {
					if(!ctryList.contains(mfArray[im].getFlagCode())) {
						missingCtryFlagSb.append(mfArray[im].getFlagCode()+" ");
						if(missingCtrySb.length()>0){
							missingCtrySb.append(", ");
						}
						missingCtrySb.append(mfArray[im].toString());
					}
				}
			} //end for
			if(missingCtryFlagSb.length()>0){
				addDebug("checkCtryMismatch1 "+item.getKey()+" missingflags:"+missingCtryFlagSb.toString());
			}
		}
		return missingCtrySb.toString();
	}
	/************
	 * Fill in a string of missing country flagcodes
	 * @param item
	 * @param ctryList
	 * @param checklvl
	 * @return String of missing country flags
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected String checkCtryMismatch(EntityItem item, Hashtable ctryList, int checklvl)
	throws SQLException, MiddlewareException
	{
		StringBuffer missingCtrySb = new StringBuffer();
		EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(item, "COUNTRYLIST", checklvl);
		if (ctrylist != null && ctrylist.toString().length()>0) {
			StringBuffer missingCtryFlagSb = new StringBuffer();
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();
			for (int im = 0; im < mfArray.length; im++) {						
				if (mfArray[im].isSelected()) {
					if(!ctryList.containsKey(mfArray[im].getFlagCode())) {
						missingCtryFlagSb.append(mfArray[im].getFlagCode()+" ");
						//missingCtrySb.append(mfArray[im].getFlagCode()+" ");
						if(missingCtrySb.length()>0){
							missingCtrySb.append(", ");
						}
						missingCtrySb.append(mfArray[im].toString());
					}
				}
			} //end for
			if(missingCtryFlagSb.length()>0){
				addDebug("checkCtryMismatch2 "+item.getKey()+" missingflags:"+missingCtryFlagSb.toString());
			}
		}
		return missingCtrySb.toString();
	}
	/*********************
	 * Fill in the vector with avails that match the countries in testavail item
	 * @param testavail
	 * @param ctryList
	 * @param matchVct
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void getMatchingAvails(EntityItem testavail, Hashtable ctryList, Vector matchVct, int checklvl)
	throws SQLException, MiddlewareException
	{
		EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(testavail, "COUNTRYLIST", checklvl);
		if (ctrylist != null && ctrylist.toString().length()>0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();			
			for (int im = 0; im < mfArray.length; im++) {						
				if (mfArray[im].isSelected()){
					// get avail for this ctry
					EntityItem ctryAvail = (EntityItem)ctryList.get(mfArray[im].getFlagCode());
					if (ctryAvail!=null){
						if (!matchVct.contains(ctryAvail)){
							matchVct.add(ctryAvail);
						}
					}
				}
			} //end for
		}
	}
	/*********************
	 * Fill in the vector with avails that match the countries in testavail item
	 * @param testavail
	 * @param ctryList
	 * @param matchVct
	 * @param checklvl
	 * @return String of missing country flags
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected String checkCtryMismatch(EntityItem testavail, Hashtable ctryList, Vector matchVct, int checklvl)
	throws SQLException, MiddlewareException
	{
		StringBuffer missingCtrySb = new StringBuffer();
		EANFlagAttribute ctrylist = (EANFlagAttribute)getAttrAndCheckLvl(testavail, "COUNTRYLIST", checklvl);
		if (ctrylist != null && ctrylist.toString().length()>0) {
			StringBuffer missingCtryFlagSb = new StringBuffer();
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) ctrylist.get();			
			for (int im = 0; im < mfArray.length; im++) {						
				if (mfArray[im].isSelected()){
					// get avail for this ctry
					EntityItem ctryAvail = (EntityItem)ctryList.get(mfArray[im].getFlagCode());
					if (ctryAvail==null){
						missingCtryFlagSb.append(mfArray[im].getFlagCode()+" ");
						//addDebug("checkCtryMismatch3  testavail:"+testavail.getKey()+
						//	" No avail in hashtbl for ctry "+mfArray[im].getFlagCode());
						// no avail for this ctry
						//missingCtrySb.append(mfArray[im].getFlagCode()+" ");
						if(missingCtrySb.length()>0){
							missingCtrySb.append(", ");
						}
						missingCtrySb.append(mfArray[im].toString());
					}else{
						if (!matchVct.contains(ctryAvail)){
							matchVct.add(ctryAvail);
						}
					}
				}
			} //end for
			if(missingCtryFlagSb.length()>0){
				addDebug("checkCtryMismatch3 "+testavail.getKey()+" missingflags:"+missingCtryFlagSb.toString());
			}
		}

		return missingCtrySb.toString();
	}
	/********************************************************************************
	 * Find matching uplink
	 *
	 * @param r EntityItem
	 * @param destType String
	 * @returns EntityItem
	 */
	protected EntityItem getUpLinkEntityItem(EntityItem r, String destType)
	{
		EANEntity entity = null;
		for(int i = 0; i < r.getUpLinkCount(); i++) {
			EANEntity eai = r.getUpLink(i);
			if(eai.getEntityType().equals(destType)) {
				entity = eai;
				break;
			}
		}
		return (EntityItem)entity;
	}
	/********************************************************************************
	 * Find matching dnlink
	 *
	 * @param r EntityItem
	 * @param destType String
	 * @returns EntityItem
	 */
	protected EntityItem getDownLinkEntityItem(EntityItem r, String destType)
	{
		EANEntity entity = null;
		for(int i = 0; i < r.getDownLinkCount(); i++) {
			EANEntity eai = r.getDownLink(i);
			if(eai.getEntityType().equals(destType)) {
				entity = eai;
				break;
			}
		}
		return (EntityItem)entity;
	}
	/********************************************************************************
	 * Find all matching dnlinks
	 *
	 * @param r EntityItem
	 * @param destType String
	 * @returns Vector
	 */
	protected Vector getDownLinkEntityItems(EntityItem r, String destType)
	{
		Vector dnVct = new Vector();
		for(int i = 0; i < r.getDownLinkCount(); i++) {
			EANEntity eai = r.getDownLink(i);
			if(eai.getEntityType().equals(destType)) {
				dnVct.add(eai);
			}
		}
		return dnVct;
	}
	/********************
	 * create message if no plannedavails exist
	 * @param plannedAvailVct
	 * @param checklvl
	 */
	protected void checkPlannedAvailsExist(Vector plannedAvailVct, int checklvl)
	{
		if (plannedAvailVct.size()==0){
			//9	WHEN		AVAILTYPE	=	"Planned Availability"							
			//10			Count Of	=>	1			W	E	E		
			//must have at least one "Planned Availability"
			//MINIMUM_ERR = must have at least one {0}
			args[0] = "Planned Availability";
			createMessage(checklvl,"MINIMUM_ERR",args);
		}
	}
	
	/********************
	 * create message if no plannedavails and firstorderavails exist
	 * @param plannedAvailVct
	 * @param checklvl
	 */
	protected void checkPlannedAvailsOrFirstOrderAvailsExist(Vector plannedAvailVct, Vector firstOrderAvailVct, int checklvl)
	{
		if (plannedAvailVct.size()==0 && firstOrderAvailVct.size()==0){
			//	IF		PRODSTRUCT-u:OOFAVAIL-d	=	"Planned Availability" or "First Order"							
			// 18.00			CountOf	=>	1			RE*1	RE*1	RE*1	
			//must have at least one "Planned Availability" or "First Order Availability"
			//MINIMUM_ERR = must have at least one {0}
			args[0] = "Planned Availability or First Order Availability";
			createMessage(checklvl,"MINIMUM_ERR",args);
		}
	}
	
	/**
	 * require at least one Planned Availability that is either RFR or Final in order for the offering to move to Final.
	 * @param plannedAvailVct
	 * @param root
	 */
	protected void checkPlannedAvailsStatus(Vector plannedAvailVct, EntityItem root,int checkLevel){
		boolean isDQFinal = DQ_FINAL.equals(getAttributeFlagEnabledValue(root, "DATAQUALITY"));
		addDebug("check plannedavail status: isDQFinal: "+ isDQFinal);
		if(isDQFinal){
			if(plannedAvailVct != null && plannedAvailVct.size() > 0){
				boolean ishasRFRorFINALAvail = false;
				for (Iterator it = plannedAvailVct.iterator(); it.hasNext();) {
					EntityItem avail = (EntityItem) it.next();
					String avail_status = PokUtils.getAttributeFlagValue(avail, "STATUS");
					addDebug("check plannedavail status "+avail.getKey()+" STATUS: "+ avail_status);
					if(STATUS_R4REVIEW.equalsIgnoreCase(avail_status) ||STATUS_FINAL.equalsIgnoreCase(avail_status)){
						ishasRFRorFINALAvail = true;
						break;
					}				
				}
				if(!ishasRFRorFINALAvail){
					args[0] = "Planned Availability";
					createMessage(checkLevel,"AVAILSTATUS_ERROR",args);
				}
			}
		}
	}
	
	/***********************
	 * Check that there is a LastOrderAvail for this Avail
	 * @param eosavail - a EndOfService avail
	 * @param loAvailCtry - list of lo avail countries
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkLOAvailForCtryExists(EntityItem eosavail, Collection loAvailCtry, int checklvl) 
	throws SQLException, MiddlewareException
	{
		checkLOAvailForCtryExists(null,eosavail, loAvailCtry, checklvl);
	}
	/***********************
	 * Check that there is a LO Avail for this Avail
	 * @param psitem
	 * @param eosavail - a EndOfService avail
	 * @param loAvailCtry - list of lo avail countries
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkLOAvailForCtryExists(EntityItem psitem, EntityItem eosavail, Collection loAvailCtry, int checklvl) 
	throws SQLException, MiddlewareException
	{	
		String missingCtry = checkCtryMismatch(eosavail, loAvailCtry, checklvl);
		if (missingCtry.length()>0){
			addDebug("checkLOAvailForCtryExists eosavail "+eosavail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");

			//MISSING_LO_CTRY_ERR = {0} {1} includes a Country that does not have a &quot;Last Order Availability&quot; Extra countries are: {2}
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			String psinfo = "";
			if (psitem!=null){
				//prepend {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT}, {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} or {LD: PRODSTRUCT} {NDN: PRODSTRUCT}
				psinfo = getLD_NDN(psitem)+" ";
			}
			args[0]=psinfo+getLD_NDN(eosavail);
			args[1] = PokUtils.getAttributeDescription(eosavail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			args[2] = missingCtry;
			createMessage(checklvl,"MISSING_LO_CTRY_ERR",args);
		}
	}

	/**********
	 * remove any AVAIL from the vector that do not have AVAILANNTYPE=RFA
	 * @param availVct
	 */
	protected void removeNonRFAAVAIL(Vector availVct)
	{
		if (availVct.size()>0){
			// remove any that are not AVAILANNTYPE=RFA
			EntityItem tmp[] = new EntityItem[availVct.size()];
			availVct.copyInto(tmp);
			for (int i=0; i<tmp.length; i++){
				EntityItem avail = tmp[i];
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
				if (availAnntypeFlag==null){
					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
				}
				if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
					availVct.remove(avail);
					addDebug("removeNonRFAAVAIL removing non RFA "+avail.getKey()+" availtype:"+
							getAttributeFlagEnabledValue(avail, "AVAILTYPE"));
				}
			}
		}
	}
	

	/**********
	 * remove any AVAIL from the vector that do not have AVAILANNTYPE=RFA and EPIC
	 * @param availVct
	 */
	protected void removeNonRFAAndEPICAVAIL(Vector availVct)
	{
		if (availVct.size()>0){
			// remove any that are not AVAILANNTYPE=RFA
			EntityItem tmp[] = new EntityItem[availVct.size()];
			availVct.copyInto(tmp);
			for (int i=0; i<tmp.length; i++){
				EntityItem avail = tmp[i];
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
				if (availAnntypeFlag==null){
					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
				}
				if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag) && !AVAILANNTYPE_EPIC.equals(availAnntypeFlag)){
					availVct.remove(avail);
					addDebug("removeNonRFAAVAIL removing non RFA and EPIC "+avail.getKey() + " AVAILANNTYPE: " + availAnntypeFlag +" availtype:"+
							getAttributeFlagEnabledValue(avail, "AVAILTYPE"));
				}
			}
		}
	}

	/**********
	 * check all avails for AVAILANNTYPE	<>	"RFA" (RFA)	
	 * 
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkAvailAnnType() throws SQLException, MiddlewareException
	{
		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		for (int i=0; i<availGrp.getEntityItemCount(); i++){
			EntityItem avail = availGrp.getEntityItem(i);
			Vector annVct = PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
			if (annVct.size()==0){
				//try the other direction thru the assoc
				annVct = PokUtils.getAllLinkedEntities(avail, "ANNAVAILA", "ANNOUNCEMENT");
			}
			checkAvailAnnType(avail,annVct, CHECKLEVEL_E);
			annVct.clear();	
		}
	}

	/**
	 * check if the AVAIL.AVAILANNTYPE should be in an ANNOUNCEMENT
	 * WHEN		"RFA" (RFA)	<>	E: AVAIL	AVAILANNTYPE AND "EPIC" (EPIC)	<>	E: AVAIL	AVAILANNTYPE						
	 *	  Count of	=	0			E	E	E		
	 *    {LD: AVAIL} {NDN: E:AVAIL} must not be in this {LD: ANNTYPE} {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}
	 * @param avail
	 * @param annVct
	 * @param checklvl
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected boolean checkAvailAnnType(EntityItem avail, Vector annVct, int checklvl) throws SQLException, MiddlewareException
	{
		boolean isok = true;
		String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
		addDebug("checkAvailAnnType "+avail.getKey()+" annCnt "+annVct.size()+
				" availAnntypeFlag "+availAnntypeFlag);
		if (availAnntypeFlag==null){
			availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
		}
		// 1674783: DQ ABR update for an AVAILABRSTATUS
		if (annVct.size()>0 && !AVAILANNTYPE_RFA.equals(availAnntypeFlag) && !AVAILANNTYPE_EPIC.equals(availAnntypeFlag)){
			//MUST_NOT_BE_IN_THIS_ERR= {0} must not be in this {1} {2}
			args[0] = getLD_NDN(avail);
			if (avail.getEntityID()==getEntityID() && avail.getEntityType().equals(getEntityType())){
				args[0]="";
			}
			for (int i=0; i<annVct.size(); i++){
				args[1] = this.getLD_Value((EntityItem)annVct.elementAt(i),"ANNTYPE");
				args[2] = getLD_NDN((EntityItem)annVct.elementAt(i));
				createMessage(checklvl,"MUST_NOT_BE_IN_THIS_ERR",args);
			}
			isok = false;
		}
		return isok;
	}
	/***********************
	 * Check that there is a PlannedAvail for this Avail
	 * @param lofoavail - a LastOrder or FirstOrder avail
	 * @param plannedAvailCtry - list of planned avail countries
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkPlannedAvailForCtryExists(EntityItem lofoavail, Collection plannedAvailCtry, int checklvl) 
	throws SQLException, MiddlewareException
	{
		checkPlannedAvailForCtryExists(null,lofoavail, plannedAvailCtry, checklvl);
	}
	/***********************
	 * Check that there is a PlannedAvail for this Avail
	 * @param psitem
	 * @param lofoavail - a LastOrder or FirstOrder avail
	 * @param plannedAvailCtry - list of planned avail countries
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkPlannedAvailForCtryExists(EntityItem psitem, EntityItem lofoavail, Collection plannedAvailCtry, int checklvl) 
	throws SQLException, MiddlewareException
	{
		//18	B: AVAIL.COUNTRYLIST	in	A: AVAIL	COUNTRYLIST		W	W	E	
		String missingCtry = checkCtryMismatch(lofoavail, plannedAvailCtry, checklvl);
		if (missingCtry.length()>0){
			addDebug("checkPlannedAvailForCtryExists lofoavail "+lofoavail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");

			//MISSING_PLA_CTRY_ERR = {0} {1} includes a Country that does not have a &quot;Planned Availability&quot; Extra countries are: {2}
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} includes a country that does not have a "Planned Availability"
			String psinfo = "";
			if (psitem!=null){
				//prepend {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT}, {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} or {LD: PRODSTRUCT} {NDN: PRODSTRUCT}
				psinfo = getLD_NDN(psitem)+" ";
			}
			args[0]=psinfo+getLD_NDN(lofoavail);
			args[1] = PokUtils.getAttributeDescription(lofoavail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			args[2]= missingCtry;
			createMessage(checklvl,"MISSING_PLA_CTRY_ERR",args);
		}
	}

	/********************
	 * Check that the avail ctrys are a subset of the Entity's ctrys
	 * @param psitem
	 * @param avail
	 * @param ctryItem
	 * @param ctryList
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkAvailCtryInEntity(EntityItem psitem, EntityItem avail, EntityItem ctryItem,
			Collection ctryList, int checklvl) 
	throws SQLException, MiddlewareException
	{
		//26.00	 COUNTRYLIST	in	FEATURE	COUNTRYLIST		W	W	E	
		String missingCtry = checkCtryMismatch(avail, ctryList, checklvl);
		if (missingCtry.length()>0){
			addDebug("checkAvailCtryInEntity "+avail.getKey()+" COUNTRYLIST had extra ["+missingCtry+"]");
			//26.00			COUNTRYLIST	in	FEATURE	COUNTRYLIST		W	W	E	{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} must not include a country that is not in the {LD: FEATURE} {LD: COUNTRYLIST}
			//INCLUDE_ERR2 = {0} {1} must not include a Country that is not in the {2} {3}. Extra countries are: {4}
			String psinfo = "";
			if (psitem!=null){
				//prepend {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT}, {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} or {LD: PRODSTRUCT} {NDN: PRODSTRUCT}
				psinfo = getLD_NDN(psitem)+" ";
			}
			String availinfo = getLD_NDN(avail);
			// if this is the root, dont repeat the info
			if(avail.getEntityID()==this.getEntityID() && avail.getEntityType().equals(this.getEntityType())){
				availinfo = "";
			}
			args[0]=psinfo+availinfo;
			args[1]=PokUtils.getAttributeDescription(avail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			args[2]=getLD_NDN(ctryItem);//ctryItem.getEntityGroup().getLongDescription();
			args[3]=args[1];
			args[4]=missingCtry;
			createMessage(checklvl,"INCLUDE_ERR2",args);
		}
	}

	/**
	 * test 2 attributes on same entity
	 * 	10			WITHDRAWDATE	<=	FEATURE	WITHDRAWANNDATE_T		W	W	E		
	 * {LD: WITHDRAWDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
	 *	11			WTHDRWEFFCTVDATE	<=	FEATURE	WITHDRAWDATEEFF_T		W	W	E		
	 *	{LD: WTHDRWEFFCTVDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
	 * @param item1
	 * @param attrCode1
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkCanNotBeLater(EntityItem item1, String attrCode1, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		String date1 = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		String date2 = getAttrValueAndCheckLvl(item1, attrCode2, checkLvl);
		addDebug("checkCanNotBeLater "+item1.getKey()+" "+attrCode1+":"+date1+" "+attrCode2+":"+date2);
		boolean isok = checkDates(date1, date2, DATE_LT_EQ);	//date1<=date2	
		if (!isok){
			String info = "";
			//{LD: WTHDRWEFFCTVDATE} must not be later than {LD: FEATURE} {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}
			//CANNOT_BE_LATER_ERR1 = {0} must not be later than the {1} {2}
			if (!item1.getEntityType().equals(getEntityType()) &&
					item1.getEntityID()!=this.getEntityID()){
				// msg is not on the root
				info = this.getLD_NDN(item1)+" ";
			}
			args[0]=info+this.getLD_Value(item1, attrCode1);
			args[1]=item1.getEntityGroup().getLongDescription();
			args[2]=this.getLD_Value(item1, attrCode2);
			createMessage(checkLvl,"CANNOT_BE_LATER_ERR1",args);
		}
	}

	/**
	 * ANNDATE	<=	SWFEATURE	WITHDRAWANNDATE_T		W	E	E		
	 * {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT}{LD:ANNDATE}{ANNDATE} can not be later than the  {LD:SWFEATURE} {LD:WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
	 * @param item1
	 * @param attrCode1
	 * @param item2
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkCanNotBeLater(EntityItem item1, String attrCode1, EntityItem item2, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		checkCanNotBeLater(null, item1, attrCode1, item2, attrCode2, checkLvl);
	}

	/**
	 * EFFECTIVEDATE	<=	SWFEATURE	WITHDRAWDATEEFF_T		W	E	E		
	 * {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD:EFFECTIVEDATE} {EFFECTIVEDATE} can not be later than the {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T}		
	 * @param psitem
	 * @param item1
	 * @param attrCode1
	 * @param item2
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkCanNotBeLater(EntityItem psitem, EntityItem item1, String attrCode1, EntityItem item2, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		String date1 = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		String date2 = getAttrValueAndCheckLvl(item2, attrCode2, checkLvl);
		addDebug("checkCanNotBeLater "+(psitem==null?"":psitem.getKey())+" "+
				" "+item1.getKey()+" "+attrCode1+":"+date1+" "+item2.getKey()+" "+
				attrCode2+":"+date2);
		boolean isok = checkDates(date1, date2, DATE_LT_EQ);	//date1<=date2	
		if(isok){ // look for missing meta
			if(date1.length()>0 && !Character.isDigit(date1.charAt(0))){
				isok=false;
			}
			if(date2.length()>0 && !Character.isDigit(date2.charAt(0))){
				isok=false;
			}
		}
		if (!isok){
			//CANNOT_BE_LATER_ERR = {0} {1} must not be later than the {2} {3}
			//{LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD:EFFECTIVEDATE} {EFFECTIVEDATE} can not be later than the {LD:WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
			String psinfo = "";
			if (psitem!=null){
				psinfo = getLD_NDN(psitem)+" ";
			}
			String info=this.getLD_NDN(item1);
			if (item1.getEntityType().equals(getEntityType()) &&
					item1.getEntityID()==this.getEntityID()){
				// msg is on the root
				info = "";
			}
			args[0]=psinfo+" "+info;
			args[1]=this.getLD_Value(item1, attrCode1);
			if(item2.getEntityType().equals(getEntityType()) && item2.getEntityID()==getEntityID()){
				args[2] =item2.getEntityGroup().getLongDescription();
			}else{
				args[2]=this.getLD_NDN(item2);
			}

			args[3]=this.getLD_Value(item2, attrCode2);
			createMessage(checkLvl,"CANNOT_BE_LATER_ERR",args);
		}
	}

	/**
	 * test 2 attributes on same entity
	 * WITHDRAWDATEEFF_T	=>	SWFEATURE	WITHDRAWANNDATE_T		W	E	E		
	 * {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} can not be earlier than the {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}	
	 * WITHDRAWDATEEFF_T	=>	SVCFEATURE.FIRSTANNDATE		W	W	E		
	 * {LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} can not be earlier than the {LD: FIRSTANNDATE} {FIRSTANNDATE}
	 * @param item1
	 * @param attrCode1
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkCanNotBeEarlier(EntityItem item1, String attrCode1, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		String date1 = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		String date2 = getAttrValueAndCheckLvl(item1, attrCode2, checkLvl);
		addDebug("checkCanNotBeEarlier "+item1.getKey()+" "+attrCode1+":"+date1+" "+attrCode2+":"+date2);
		boolean isok = checkDates(date1, date2, DATE_GR_EQ);	//date1=>date2	
		if (!isok){
			String info = "";
			//CANNOT_BE_EARLIER_ERR = {0} can not be earlier than the {1} 
			//{LD: WITHDRAWDATEEFF_T} {WITHDRAWDATEEFF_T} can not be earlier than the {LD: WITHDRAWANNDATE_T} {WITHDRAWANNDATE_T}
			if (!item1.getEntityType().equals(getEntityType()) &&
					item1.getEntityID()!=this.getEntityID()){
				// msg is not on the root
				info = this.getLD_NDN(item1)+" ";
			}
			args[0]=info+this.getLD_Value(item1, attrCode1);
			args[1]=this.getLD_Value(item1, attrCode2);
			createMessage(checkLvl,"CANNOT_BE_EARLIER_ERR",args);
		}
	}	
	/**
	 * test 2 attributes, one on each entity
	 * EFFECTIVEDATE	=>	SVCFEATURE	FIRSTANNDATE
	 * {LD: AVAIL} {NDN: AVAIL}  {LD: EFFECTIVEDATE} {EFFECTIVEDATE} can not be earlier than the {LD: SVCFEATURE} {NDN: SVCFEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
	 * {LD: ANNOUNCEMENT} {NDN: ANNOUNCEMENT} {LD: ANNDATE} {ANNDATE} can not be earlier than the {LD: SVCFEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
	 * @param item1
	 * @param attrCode1
	 * @param item2
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkCanNotBeEarlier(EntityItem item1, String attrCode1, EntityItem item2, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		checkCanNotBeEarlier(null, item1, attrCode1, item2, attrCode2, checkLvl);
	}
	/**
	 * test 2 attributes, 1 on each entity, use ps entity in msg
	 * EFFECTIVEDATE	=>	SVCFEATURE	FIRSTANNDATE		W	W	E
	 * {LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD: EFFECTIVEDATE} {EFFECTIVEDATE} can not be earlier than the {LD: SVCFEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
	 * @param psitem
	 * @param item1
	 * @param attrCode1
	 * @param item2
	 * @param attrCode2
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkCanNotBeEarlier(EntityItem psitem, EntityItem item1, String attrCode1, EntityItem item2, String attrCode2,
			int checkLvl) throws SQLException, MiddlewareException
	{
		String date1 = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		String date2 = getAttrValueAndCheckLvl(item2, attrCode2, checkLvl);
		addDebug("checkCanNotBeEarlier "+(psitem==null?"":psitem.getKey())+" "+
				item1.getKey()+" "+attrCode1+":"+date1+" "+item2.getKey()+" "+
				attrCode2+":"+date2);
		boolean isok = checkDates(date1, date2, DATE_GR_EQ);	//date1=>date2	
		if(isok){ // look for missing meta
			if(date1.length()>0 && !Character.isDigit(date1.charAt(0))){
				isok=false;
			}
			if(date2.length()>0 && !Character.isDigit(date2.charAt(0))){
				isok=false;
			}
		}
		if (!isok){
			//CANNOT_BE_EARLIER_ERR2 = {0} {1} can not be earlier than the {2} {3} 
			//{LD: SVCPRODSTRUCT} {NDN: SVCPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} {LD:EFFECTIVEDATE}{EFFECTIVEDATE} is earlier than the {LD: SVCFEATURE} {LD: FIRSTANNDATE} {FIRSTANNDATE}
			String psinfo = "";
			if (psitem!=null){
				psinfo = getLD_NDN(psitem)+" ";
			}

			String info=getLD_NDN(item1);
			if (item1.getEntityType().equals(getEntityType()) &&
					item1.getEntityID()==this.getEntityID()){
				// msg is on the root
				info = "";
			}
			args[0]=psinfo+" "+info;
			args[1]=this.getLD_Value(item1, attrCode1);
			if(item2.getEntityType().equals(getEntityType()) && item2.getEntityID()==getEntityID()){
				args[2] =item2.getEntityGroup().getLongDescription();
			}else{
				args[2] =getLD_NDN(item2);
			}
			args[3]=this.getLD_Value(item2, attrCode2);
			createMessage(checkLvl,"CANNOT_BE_EARLIER_ERR2",args);
		}
	}

	/****************************************
	 * SR10, 11, 12, 15, 17
	 * Check for withdrawn date on the avail 
	 * AVAIL.EFFECTIVEDATE	=>	NOW() 
	 */
	protected void checkWDAvails(EntityItem theItem, String rtype, int checkLvl, boolean useAvailMsg)
	throws java.sql.SQLException, MiddlewareException
	{
		addDebug("checkWDAvails checking entity: "+theItem.getKey()+" for "+rtype);
		for (int i=0; i<theItem.getDownLinkCount(); i++){ // look at relators
			EntityItem link = (EntityItem)theItem.getDownLink(i);
			if (link.getEntityType().equals(rtype)){ // right relator
				// get AVAILS
				for (int ai=0; ai<link.getDownLinkCount(); ai++){ // look at avails
					EntityItem avail = (EntityItem)link.getDownLink(ai);
					addDebug("checkWDAvails checking "+theItem.getKey()+":"+link.getKey()+":"+avail.getKey()+" for lastorder");
					if(PokUtils.isSelected(avail, "AVAILTYPE", LASTORDERAVAIL)){
						String effDate = PokUtils.getAttributeValue(avail, "EFFECTIVEDATE",", ", "", false);
						addDebug("checkWDAvails lastorder "+avail.getKey()+" EFFECTIVEDATE: "+effDate);
						if (effDate.length()>0 && effDate.compareTo(getCurrentDate())<=0){
							if (useAvailMsg){
								//{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}  is withdrawn and can not be referenced.
								//WITHDRAWN_ERR = {0} {1} is withdrawn and can not be referenced.	
								args[0] = getLD_NDN(theItem);
								args[1] = getLD_NDN(avail);
								createMessage(checkLvl,"WITHDRAWN_ERR",args);
							}else{
								//WITHDRAWN_ITEM_ERR = can not be updated for a {0} that has a {1} 
								//can not be updated for a {LD: MODEL} that has an {LD: AVAIL} {NDN: AVAIL}
								args[0] = theItem.getEntityGroup().getLongDescription();
								args[1] = this.getLD_NDN(avail);
								createMessage(checkLvl,"WITHDRAWN_ITEM_ERR",args);
							}
						}
					}
				} // end avails
			}
		}
	}   
	/**********************************************************************************
	 * check the dates SR10, 11, 12, 15, 17
	 * @param date1
	 * @param date2
	 * @param dateRule
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected boolean checkDates(String date1, String date2, int dateRule) throws SQLException, MiddlewareException 
	{
		boolean isok = true;

		if (date1.length()>0 && date2.length()>0){
			int diff = date1.compareTo(date2);
			switch(dateRule){
			case DATE_GR_EQ: // date1 => date2
				if (diff<0){
					isok = false;
				}
				break;
			case DATE_LT_EQ: // date1 <= date2
				if (diff>0){
					isok = false;
				}
				break;
			case DATE_GR:    // date1 > date2
				if (diff<=0){
					isok = false;
				}
				break;
			case DATE_LT:    // date1 < date2
				if (diff>=0){
					isok = false;
				}
				break;
			case DATE_EQ:    // date1 = date2
				if (diff !=0){
					isok = false;
				}
				break;
			}
		}
		return isok;
	}

	/**
	 * Check that a referenced item is not withdrawn
	 *  SR10, 11, 12, 15, 17
	 * WITHDRAWDATE	=>	NOW()
	 * @param item1
	 * @param attrCode1 WITHDRAWDATE
	 * @param checkLvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkWithdrawnDate(EntityItem item1, String attrCode1, int checkLvl,
			boolean useRefMsg) 
	throws SQLException, MiddlewareException 
	{
		//String wddate = PokUtils.getAttributeValue(item1, attrCode1, "", "", false);
		String wddate = getAttrValueAndCheckLvl(item1, attrCode1, checkLvl);
		addDebug("checkWithdrawnDate: "+item1.getKey()+" "+attrCode1+" ="+wddate);
		if (wddate.length()>0){
			if (wddate.compareTo(getCurrentDate())<0){
				// WITHDRAWN_ERR = {0} {1} is withdrawn and can not be referenced.
				//{LD: MODEL} {NDN: MODEL} {LD: WITHDRAWDATE} {WITHDRAWDATE} is withdrawn and can not be referenced.
				if (useRefMsg){
					args[0] = getLD_NDN(item1);
					args[1] = getLD_Value(item1, attrCode1);
					createMessage(checkLvl,"WITHDRAWN_ERR",args);
				}else{
					// WITHDRAWN_ITEM_ERR = can not be updated for a {0} that has a {1} 
					//can not be updated for a {LD: MODEL} that has a {LD: WITHDRAWDATE} {WITHDRAWDATE}
					args[0]=item1.getEntityGroup().getLongDescription();
					args[1]=getLD_Value(item1, attrCode1);
					createMessage(checkLvl,"WITHDRAWN_ITEM_ERR",args);
				}
			}
		}
	}

	/**
	 *  SR10, 11, 12, 15, 17
	 * @param checkLvl
	 * @param msgCode
	 * @param args
	 */
	protected void createMessage(int checkLvl,String msgCode, Object args[]){
		switch(checkLvl){
		case CHECKLEVEL_W:
		case CHECKLEVEL_RW:
			addWarning(msgCode,args);
			break;
		case CHECKLEVEL_E:
		case CHECKLEVEL_RE:
			addError(msgCode,args);
			break;
		case CHECKLEVEL_NOOP:
			addDebug("createMessage: Bypassing msg output for "+msgCode);
			break;
		}
	}
	/*************
	 * Check the STATUS of the item1 is >= the DATAQUALITY of the item2
	 * SR10, 11, 12, 15, 17
	 * Comparison:
When checking within the rules compares STATUS (and/or DATAQUALITY) the following is 
an ordered list of lowest (smallest) to highest (largest) values:
1.	Draft
2.	Change Request
3.	Ready for Review
4.	Final
For example,
Change Request < Ready for Review
is TRUE.

	 * @param itemStatus
	 * @param statusAttr
	 * @param itemDQ
	 * @param checkLvl
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	protected void checkStatusVsDQ(EntityItem itemStatus, String statusAttr, 
			EntityItem itemDQ,int checkLvl) throws SQLException, MiddlewareException
			{
		boolean isok = true;
		String statusFlag = PokUtils.getAttributeFlagValue(itemStatus, statusAttr);

		if (statusFlag==null){
			addDebug("checkStatusVsDQ "+itemStatus.getKey()+" "+statusAttr+" is null, must get entityhistory and check date");
			if (!wasUpdatedSinceDTS(itemStatus,NULL_DTS)){
				addDebug(itemStatus.getKey()+" using Final for status because it was not updated after "+NULL_DTS);
				statusFlag = STATUS_FINAL;
			}else{
				statusFlag = STATUS_DRAFT;
			}
		}
		String statusOrd = (String)STATUS_ORDER_TBL.get(statusFlag);
		String dqFlag = PokUtils.getAttributeFlagValue(itemDQ, "DATAQUALITY");
		// cant really happen, but just in case...
		if (dqFlag==null){
			addDebug("checkStatusVsDQ "+itemDQ.getKey()+" DATAQUALITY is null, must get entityhistory and check date");
			if (!wasUpdatedSinceDTS(itemDQ,NULL_DTS)){
				addDebug(itemDQ.getKey()+" using Final for DATAQUALITY because it was not updated after "+NULL_DTS);
				dqFlag = DQ_FINAL;
			}else{
				dqFlag = DQ_R4REVIEW;
			}
		}
		String dqAsStatus = (String)STATUS_TBL.get(dqFlag);
		String dqOrd = (String)STATUS_ORDER_TBL.get(dqAsStatus);

		// compare ordinal values
		isok = (statusOrd.compareTo(dqOrd)>=0);
		addDebug("checkStatusDQ "+itemStatus.getKey()+" "+statusAttr+"["+statusFlag+"] statusOrder:"+statusOrd+
				" "+itemDQ.getKey()+" DATAQUALITY["+dqFlag+"] dqAsStatus "+dqAsStatus+" dqOrder:"+dqOrd+" isok "+isok);

		if (!isok){
			//STATUS_CHECK_ERR= {0} can not be higher than the {1} {2} 
			//{LD: STATUS} can not be higher than the {LD: MODEL} {NDN: MODEL} {LD: STATUS} {STATUS}
			args[0]=PokUtils.getAttributeDescription(itemStatus.getEntityGroup(), statusAttr, statusAttr);
			args[1]=this.getLD_NDN(itemStatus);
			args[2]=this.getLD_Value(itemStatus, statusAttr);
			createMessage(checkLvl,"STATUS_CHECK_ERR",args);
		}
			}

	/*************************************************************************************
	 * Check the PDHDOMAIN list SR10, 11, 12, 15, 17
	 *
	 * The following spreadsheet provides the lists of PDHDOMAINs which identifies those to be included in 
	 * domainList XCC_LIST and XCC_LIST2. The lists are used in both the CHECKS and SETS spreadsheets.
	 * 
	 * If the identified domainList is not supplied in the properties file or it is empty, then the logic that 
	 * tests for the PDHDOMAIN to be in the List passes (i.e. true).
	 * 
	 * @param item
	 * @param ruleNumber
	 * @return
	 */
	protected boolean domainInRuleList(EntityItem item, String ruleNumber)
	{
		boolean inlist = false;
		String domains = getDomainList(item, ruleNumber);
		if (domains.trim().length()==0){
			//this condition will happen if there is an entry in abr props but no values in the list
			//this gives a way to completely turn off the checks
			inlist = false;
		}else if (domains.equals("all")){
			//changed in BH FS ABR Data Quality 20120116.doc
			// If the identified domainList is not supplied in the properties file or it is empty, then the logic that 
			// tests for the PDHDOMAIN to be in the List passes (i.e. true).
			inlist = true;//false; // default for nothing is 'all'
		}else{
			inlist = domainInList(item, domains);
		}

		if (!inlist){
			addDebug("domainInRuleList: "+ruleNumber+" checking is bypassed for "+item.getKey()+" "+
					PokUtils.getAttributeValue(item, "PDHDOMAIN",", ", "", false)+"["+
					getAttributeFlagEnabledValue(item, "PDHDOMAIN")+"]");
		}
		return inlist;
	}
	
	protected boolean domainInList(EntityItem item, String domains) {
		boolean inlist;
		Set testSet = new HashSet();
		StringTokenizer st1 = new StringTokenizer(domains,",");
		while (st1.hasMoreTokens()) {
			testSet.add(st1.nextToken());
		}
		inlist = PokUtils.contains(item, "PDHDOMAIN", testSet);
		testSet.clear();
		return inlist;
	}
	
	protected String getDomainList(EntityItem item, String ruleNumber) {
		String domains = "";
		if (item.getEntityType().equals(getEntityType())){
			domains = 
				COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomainList(m_abri.getABRCode(),"_"+ruleNumber);
		}else{
			// find abr attr, then get rule value from properties for that
			String abrAttrCode = (String)ABR_ATTR_TBL.get(item.getEntityType());
			if(abrAttrCode==null){
				addDebug("WARNING: cant find ABR attribute code for "+item.getEntityType());
				domains = 
					COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomainList(m_abri.getABRCode(),"_"+ruleNumber);

			}else{
				domains = 
					COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getDomainList(abrAttrCode,"_"+ruleNumber);
			}
		}
		addDebug("domainInRuleList: "+item.getKey()+" pdhdomain: "+
				PokUtils.getAttributeFlagValue(item, "PDHDOMAIN")+" list "+ruleNumber+": "+domains);
		return domains;
	}    

	/*************************************
	 * Get attr value and add message if it was required
	 *
	 * @param item
	 * @param attrCode
	 * @param checklvl
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected String getAttrValueAndCheckLvl(EntityItem item, String attrCode, int checklvl) 
	throws SQLException, MiddlewareException
	{
		String val = PokUtils.getAttributeValue(item, attrCode,", ", "", false);
		//addDebug("getAttrValueAndCheckLvl "+item.getKey()+" "+attrCode+": "+val);
		if (val.length()==0 && (checklvl==CHECKLEVEL_RE || checklvl==CHECKLEVEL_E)){
			checklvl = overrideCheckLvl(item, checklvl);
			if(checklvl!=CHECKLEVEL_RE && checklvl!=CHECKLEVEL_E){
				addDebug("OVERRODE checklevel for "+attrCode+
						" because "+item.getKey()+" was not FINAL and updated after "+NULL_DTS);
			}
		}
		if (val.length()==0 && (checklvl==CHECKLEVEL_RE || checklvl==CHECKLEVEL_RW)){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} is required and does not have a value.
			args[0]=getLD_NDN(item);
			args[1]=PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode);
			createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args); 
		} 
		return val;
	}
	/**
	 *  If a value in a related entity is being checked does not exist (aka empty or null) the check is skipped
unless the status of that entity is "Final" and the VALFROM  > 2009-01-01.  If the "error indicator" in 
columns I, J and K indicate that a missing value is an error via a prefix character value of "R", then 
report the error based on the second character (W = Warning; E = Error). If "R" is not specified, then 
the check is skipped for missing values. If the "error indicator" has a suffix of "*" followed by an 
integer value, then there is a secondary process that determines if the severity should be raised or 
lowered. See the section on "Old Data".
	 * @param item
	 * @param checklvl
	 * @return
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 */
	private int overrideCheckLvl(EntityItem item, int checklvl) throws SQLException, MiddlewareException{
		if(!item.getEntityType().equals(getEntityType())&& item.getEntityID()!= getEntityID()){
			// checking related entity - so look at its status
			String statusAttr = (String)STATUS_ATTR_TBL.get(item.getEntityType());
			if (statusAttr==null){
				statusAttr = "STATUS";
			}
			String statusFlag = PokUtils.getAttributeFlagValue(item, statusAttr);
			if (STATUS_FINAL.equals(statusFlag) && wasUpdatedSinceDTS(item, NULL_DTS)){
				// do not skip this check
			}else{
				checklvl = CHECKLEVEL_W;
			}
		}
		return checklvl;
	}
	/*************************************
	 * Get attr and add message if it was required
	 * @param item
	 * @param attrCode
	 * @param checklvl
	 * @return 
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected EANAttribute getAttrAndCheckLvl(EntityItem item, String attrCode, int checklvl) 
	throws SQLException, MiddlewareException
	{
		EANAttribute attr = item.getAttribute(attrCode);
		//addDebug("getAttrAndCheckLvl "+item.getKey()+" "+attrCode+": "+attr);
		if ((attr==null || attr.toString().length()==0) && 
				(checklvl==CHECKLEVEL_RE || checklvl==CHECKLEVEL_RW)){
			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
			//{LD: AVAIL} {NDN: AVAIL} {LD: COUNTRYLIST} is required and does not have a value.
			args[0]=getLD_NDN(item);
			args[1]=PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode);
			createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);    				
		} 
		return attr;
	}
	/*************************************
	 * Get all countries for all items in the vector as a list for set operations, 
	 * add message if COUNTRYLIST is required
	 * @param eiVct
	 * @param checklvl
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected ArrayList getCountriesAsList(Vector eiVct, int checklvl) throws SQLException, MiddlewareException
	{
		ArrayList listCtry = new ArrayList(); 
		// get the set of countries
		for (int i=0; i<eiVct.size(); i++){
			EntityItem item = (EntityItem)eiVct.elementAt(i);
			getCountriesAsList(item ,listCtry, checklvl);
		}// end item loop

		return listCtry;
	}
	protected ArrayList getCountriesAsList(EntityGroup eg, int checklvl) throws SQLException, MiddlewareException
	{
		ArrayList listCtry = new ArrayList(); 
		// get the set of countries
		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem item = eg.getEntityItem(i);
			getCountriesAsList(item ,listCtry, checklvl);
		}// end item loop

		return listCtry;
	}

	/*************************************
	 * Get all countries as a list for set operations, add message if COUNTRYLIST is required
	 * @param item
	 * @param listCtry
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void getCountriesAsList(EntityItem item ,ArrayList listCtry, int checklvl) throws SQLException, MiddlewareException
	{
		getAttributeAsList(item,listCtry,  "COUNTRYLIST", checklvl);
	}  

	/*************************************
	 * Get all values for the specified attribute as a list
	 * @param eiVct
	 * @param attrcode
	 * @param checklvl
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected ArrayList getAttributeAsList(Vector eiVct, String attrcode, int checklvl) throws SQLException, MiddlewareException
	{
		ArrayList listCtry = new ArrayList(); 
		// get the set of countries
		for (int i=0; i<eiVct.size(); i++){
			EntityItem item = (EntityItem)eiVct.elementAt(i);
			getAttributeAsList(item ,listCtry, attrcode,checklvl);
		}// end item loop

		return listCtry;
	}
	/*************************************
	 * Get all values for the attribte as a list for set operations, add message if attr is required
	 * @param item
	 * @param listAttr
	 * @param attrcode
	 * @param checklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void getAttributeAsList(EntityItem item, ArrayList listAttr, String attrcode, int checklvl) throws SQLException, MiddlewareException
	{
		EANFlagAttribute att = (EANFlagAttribute)getAttrAndCheckLvl(item, attrcode, checklvl);
		if (att != null && att.toString().length()>0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) att.get();
			for (int im = 0; im < mfArray.length; im++) {
				// get selection
				if (mfArray[im].isSelected()&& !listAttr.contains(mfArray[im].getFlagCode())) {
					listAttr.add(mfArray[im].getFlagCode());
				}
			} //end for
		} //end of null chk
	} 
	/**
	 * check to see if the entity passed in has any matching COUNTRY in its COUNTRYLIST attribute
	 * @param item
	 * @param ctryList
	 * @return
	 */
	protected boolean hasAnyCountryMatch(EntityItem item,ArrayList ctryList) 
	{
		EANFlagAttribute att = (EANFlagAttribute)item.getAttribute("COUNTRYLIST");
		if (att != null && att.toString().length()>0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) att.get();
			for (int im = 0; im < mfArray.length; im++) {
				// get selection
				if (mfArray[im].isSelected()&& ctryList.contains(mfArray[im].getFlagCode())) {
					return true;
				}
			} //end for
		} //end of null chk
		return false;
	} 
	/**  
    WWSEO
	82.200	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST					if the AVAIL has a country in its COUNTRYLIST that is in the LSEO.COUNTRYLIST, then do the following checks	
	82.220	IF		F: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
	82.222	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
	82.224	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
	82.226	THEN											
	82.228			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
	82.230	ELSE	82.226										
	82.300			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	82.320	END	82.226										
	82.340	END	82.200										

	--------------------------------------------
	WWSEO
	104.040	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST					if the AVAIL has a country in its COUNTRYLIST that is in the LSEO.COUNTRYLIST, then do the following checks	
	104.060	IF		G: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
	104.080	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
	104.100	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
	104.120	THEN											
	104.140			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
	104.160	ELSE	104.120										
	105.000			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	105.020	END	104.120										
	105.040	END	104.040																			
=======================
LSEO
	120.00	AVAIL		J: + OOFAVAIL-d								LSEO: PRODSTRUCT AVAIL	
    121.00	WHEN		AVAILTYPE	=	"Last Order"							
    122.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
    122.02	AND		J: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
    122.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
    122.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    122.08	THEN											
    122.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    122.12	ELSE	122.08										
    123.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
    123.02	END	122.08										
    124.00	END	121.00	
--------
   	130.00	AVAIL		K: + OOFAVAIL-d								WWSEO: PRODSTRUCT AVAIL	
	131.00	WHEN		AVAILTYPE	=	"Last Order"							
	132.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
	132.02	AND		K: + PRODSTRUCTCATLGOR-d		NotNull(PUBTO)							
	132.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
	132.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
	132.08	THEN											
	132.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
	132.12	ELSE	132.08										
	133.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
---------
   	143.00	AVAIL	S	LSEOSWPRODSTRUCT-d:								LSEO: SWPRODSTRUCT	
    143.04			S: + SWPRODSTRUCTAVAIL-d									
    144.00	WHEN		AVAILTYPE	=	"Last Order"							
    145.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
    145.02	AND		S: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
    145.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
    145.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    145.08	THEN											
    145.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    145.40	ELSE	145.08										
    146.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
---------
   	153.00	AVAIL	T	WWSEOLSEO-u: WWSEOSWPRODSTRUCT-d:								WWSEO: SWPRODSTRUCT	
    153.02			T: + SWPRODSTRUCTAVAIL-d									
    154.00	WHEN		AVAILTYPE	=	"Last Order"							
    155.00	IF		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST						
    155.02	AND		T: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
    155.04	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST						
    155.06	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO						
    155.08	THEN											
    155.10			PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
    155.12	ELSE	155.08
    156.00			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	RE	RE	RE		{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must  not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
	
	 * @param psitem
	 * @param catlgorRel
	 * @param availRel
	 * @param lseoVct 
	 * @param checklvl
	 * @param pubtochklvl
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkLseoPSLOAvail(EntityItem psitem, String catlgorRel, String availRel, 
			Vector lseoVct, int checklvl, int pubtochklvl) throws SQLException, MiddlewareException
	{
		addDebug("checkLseoPSLOAvail: "+psitem.getKey());
		if(lseoVct.size()>0){
			Vector availvct = PokUtils.getAllLinkedEntities(psitem, availRel, "AVAIL");
			Vector loavailvct = PokUtils.getEntitiesWithMatchedAttr(availvct, "AVAILTYPE", LASTORDERAVAIL);
			Vector catlgorVct = PokUtils.getAllLinkedEntities(psitem, catlgorRel, "CATLGOR");
			addDebug("checkLseoPSLOAvail: availvct "+availvct.size()+
					" loavailvct "+loavailvct.size()+" catlgorVct "+catlgorVct.size());
			if(catlgorVct.size()>0){
				Vector tmp = new Vector();
				for (int i=0;i<catlgorVct.size();i++){
					EntityItem catlgor = (EntityItem)catlgorVct.elementAt(i);
					String pubto = PokUtils.getAttributeValue(catlgor, "PUBTO", "", null, false);
					if(pubto!=null){
						tmp.add(catlgor);
					}
				}
				catlgorVct.clear();
				catlgorVct = tmp;
				addDebug("checkLseoPSLOAvail: catlgorVct with pubto values: "+catlgorVct.size());
			}

			if(loavailvct.size()>0){
				Vector errmsgVct = new Vector();  // prevent dup msgs for same ps and avail
				for(int a=0; a<loavailvct.size(); a++){
					EntityItem psLoAvail = (EntityItem)loavailvct.elementAt(a);
					String effDate = PokUtils.getAttributeValue(psLoAvail, "EFFECTIVEDATE",", ", "", false);
					lseoloop:for(int i=0; i<lseoVct.size(); i++){
						EntityItem lseo = (EntityItem)lseoVct.elementAt(i);
						String lseoDate = PokUtils.getAttributeValue(lseo, "LSEOUNPUBDATEMTRGT",", ", "", false);
						
						ArrayList lseoCtry = new ArrayList();
						getCountriesAsList(lseo, lseoCtry, CHECKLEVEL_E);
						addDebug("checkLseoPSLOAvail "+psLoAvail.getKey()+" "+lseo.getKey()+" lseoCtry "+lseoCtry);
						//82.20	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST		
						//104.040	WHEN		COUNTRYLIST	AnyIn	LSEO	COUNTRYLIST
						//if the AVAIL has a country in its COUNTRYLIST that is in the LSEO.COUNTRYLIST, then do the following checks	
						// this is by country
						//This is only checked when a "Last Order" AVAIL has a country common to AVAIL.COUNTRYLIST and LSEO.COUNTRYLIST	
						if(hasAnyCountryMatch(psLoAvail,lseoCtry)){
							addDebug("checkLseoPSLOAvail lseo and avail ctryexist LSEOUNPUBDATEMTRGT: "+lseoDate+" EFFECTIVEDATE: "+effDate);
							ArrayList availCtry = new ArrayList();
							getCountriesAsList(psLoAvail, availCtry, CHECKLEVEL_E);
							addDebug("checkLseoPSLOAvail availCtry "+availCtry);
							//82.220	IF		F: + PRODSTRUCTCATLGOR-d	NotNull(PUBTO)	
							//104.060	IF		G: + SWPRODSTRCATLGOR-d		NotNull(PUBTO)							
							for (int c=0;c<catlgorVct.size();c++){// these have pubto dates
								EntityItem catlgor = (EntityItem)catlgorVct.elementAt(c);
								String pubto = PokUtils.getAttributeValue(catlgor, "PUBTO", "", "", false);
								String offctry = PokUtils.getAttributeFlagValue(catlgor, "OFFCOUNTRY");
								addDebug("checkLseoPSLOAvail "+catlgor.getKey()+" pubto "+pubto+" offctry "+offctry);
								//82.224	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO	
								//104.100	AND		EFFECTIVEDATE	<	CATLGOR	PUBTO
								if(effDate.compareTo(pubto)<0){
									//82.222	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST			
									//104.080	AND		OFFCOUNTRY	IN	AVAIL	COUNTRYLIST							
									if(availCtry.contains(offctry)){	
										//104.120	THEN
										//82.226	THEN											
										if (lseoDate.length()>0 && pubto.compareTo(lseoDate)<0){
											if(!errmsgVct.contains(psitem.getKey()+psLoAvail.getKey())){
												errmsgVct.add(psitem.getKey()+psLoAvail.getKey());
												//104.140		PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		
												//{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: SWPRODSTRUCT} {NDN: SWPRODSTRUCT} {LD: CATLGOR} {NDN: CATLGOR}
												//82.228		PUBTO	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E		{LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL} is withdrawn and can not be referenced.
												//CANNOT_BE_LATER_ERR1 = {0} must not be later than the {1} {2}
												args[0] = getLD_Value(lseo, "LSEOUNPUBDATEMTRGT");
												args[1] = getLD_NDN(psitem);
												args[2] = getLD_NDN(catlgor)+" "+getLD_Value(catlgor, "PUBTO");
												createMessage(pubtochklvl,"CANNOT_BE_LATER_ERR1",args);
											}
										}
										continue lseoloop;
									}else{
										addDebug("checkLseoPSLOAvail offctry not in availctry");
									}
								} // end effdate < pubto
							}    // end catlgor loop with pubto exist										

							addDebug("checkLseoPSLOAvail chk lseodate vs pslo effdate");
							//82.230	ELSE	82.226	
							//104.160	ELSE	104.120	
							if(lseoDate.length()==0){
								addDebug("checkLseoPSLOAvail "+lseo.getKey()+" LSEOUNPUBDATEMTRGT not populated");
								//fix Defect 719537 - add the default value
								lseoDate = FOREVER_DATE;
								addDebug("checkLseoPSLOAvail "+lseo.getKey()+" LSEOUNPUBDATEMTRGT was set to " + lseoDate);
//								continue;
							}
							if (lseoDate.length()>0 && effDate.compareTo(lseoDate)<0){
								if(!errmsgVct.contains(psitem.getKey()+psLoAvail.getKey())){
									errmsgVct.add(psitem.getKey()+psLoAvail.getKey());
//									82.300			EFFECTIVEDATE	=>	LSEO	LSEOUNPUBDATEMTRGT	Yes	E	E	E	{LD: LSEOUNPUBDATEMTRGT} {LSEOUNPUBDATEMTRGT}  must not be later than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: AVAIL} {NDN: AVAIL}
//									args[0] = getLD_NDN(psitem);
//									args[1] = getLD_NDN(psLoAvail);
									args[0] = getLD_Value(lseo, "LSEOUNPUBDATEMTRGT");
									args[1] = getLD_NDN(psitem);
									args[2] = getLD_NDN(psLoAvail)+" "+getLD_Value(psLoAvail, "EFFECTIVEDATE");
									createMessage(checklvl,"CANNOT_BE_LATER_ERR1",args);
//									createMessage(checklvl,"WITHDRAWN_ERR",args);
								}
							}
							//82.320	END	82.226
							//82.340	END	82.200
							//105.020	END	104.120										
							//105.040	END	104.040	
						}else{
							addDebug("checkLseoPSLOAvail no country match");
						}
						lseoCtry.clear();
					}
				}
				loavailvct.clear();
				errmsgVct.clear();
			}
			availvct.clear();
		}
		//82.360	END	82.100	
		//105.060	END	104.020
	}
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
	/**************************************
	 * Get description and value for specified attribute
	 * @param item
	 * @param attrCode
	 * @return
	 */
	protected String getLD_Value(EntityItem item, String attrCode)   {
		return getLD_Value(item, attrCode, null);
	}
	/**************************************
	 * Get description and value for specified attribute if value is null
	 * @param item
	 * @param attrCode
	 * @param value
	 * @return
	 */
	protected String getLD_Value(EntityItem item, String attrCode, String value)   {
		if (value==null){
			value = PokUtils.getAttributeValue(item, attrCode, ",", "", false);
		}
		return PokUtils.getAttributeDescription(item.getEntityGroup(), attrCode, attrCode)+": "+
		value;
	}

	/****************
	 * go thru specified relator type to find the prodstruct
	 * multiple ps may be linked, this is for msg purposes only when the avail has an error
	 * the first ps found is sufficient to find the avail
	 * @param avail
	 * @param psreltype
	 * @return
	 */
	protected EntityItem getAvailPS(EntityItem avail, String psreltype){
		EANEntity entity = null;
		for(int i = 0; i < avail.getUpLinkCount(); i++) {
			EANEntity eai = avail.getUpLink(i);
			if(eai.getEntityType().equals(psreltype)) {
				entity = eai.getUpLink(0);
				break;
			}
		}

		return (EntityItem)entity;
	}

	/**********************************
	 * complete LSEO DQ abr processing after status moved to final 
	 * LSEOAVAIL, AVAILANNA must be in extract
	 * @param lseoArray 
	 * @param specbid
	 * @throws MiddlewareException 
	 * @throws SQLException 
	 * @throws MiddlewareRequestException 
	 * 
	71.00	Section		Two							
	72.00	SET			LSEO				EPIMSABRSTATUS	norfr	&ABRWAITODS
	74.00	IF		WWSEOLSEO-u	WWSEO	SPECBID	=	"No" (11457)			
	75.00	IF		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
	76.00	AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	WWPRTABRSTATUS		&ABRWAITODS2 
	77.00	END	75								
	78.00	IF		LSEOAVAIL-d	AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
	79.00	AND		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
	80.00	AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	QSMRPTABRSTATUS 		&QSMWAITODS 
	81.00	END	78								
	82.00	IF		LSEOAVAIL-d	AVAIL	AVAILTYPE	=	"Last Order" (149)			
	83.00	AND		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
	84.00	AND			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)	QSMRPTABRSTATUS 		&QSMWAITODS 
	85.00	END	82								
		85.200	IF			LSEO	STATUS	=	"Ready for Review" (0040)		
20130904 Delete		85.220	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
Add 2011-10-20		85.222	IF			LSEO	LSEOPUBDATEMTRGT	>	"2010-03-01" | Empty		
Change 2011-10-20		85.240	IF		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		85.250	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
		85.260	OR		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		85.280	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
		85.290	END	85.250							
Add 2011-10-20		85.292	END	85.240							
Add 2011-10-20		85.294	ELSE	85.222							
Add 2011-10-20		85.296	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
Add 2011-10-20		85.298	END	85.222							
		85.300	END	85.200							
							
		85.320	IF			LSEO	STATUS	=	"Final" (0020)			
Add 2011-10-20		85.322	IF			LSEO	LSEOPUBDATEMTRGT	>	"2010-03-01" | Empty			
Change 2011-10-20		85.340	IF		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
		85.350	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)			
		85.360	OR		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)			
		85.380	SET			LSEO				ADSABRSTATUS		&ADSFEED
		85.390	END	85.350								
Add 2011-10-20		85.392	END	85.340								
Add 2011-10-20		85.394	ELSE	85.322								
Add 2011-10-20		85.396	SET			LSEO				ADSABRSTATUS		&ADSFEED
Add 2011-10-20		85.398	END	85.322								
		85.400	END	85.320								

							
	86.00	ELSE	74		LSEO				WWPRTABRSTATUS	norfr	&ABRWAITODS2 
	87.00	SET			LSEO				QSMRPTABRSTATUS 	norfr	&QSMWAITODS 
R1.0	87.10	IF			LSEO	STATUS	=	"Ready for Review" (0040)			
20130904 Delete R1.0	87.11	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)	| "Plan" (LF01)			
R1.0 	87.12	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
R1.0	87.13	END	87.1								
	87.20	IF			LSEO	STATUS	=	"Final" (0020)			
	87.23	SET			LSEO				ADSABRSTATUS		&ADSFEED
	87.24	END	87.2								
	88.00	END	74								
	89.00	COMMENT		The following from LSEOBUNDLE so that we do not have to queue DQ ABR for LSEOBUNDLE							
	90.00	IF			LSEO	STATUS	=	"Final" (0020)			
	91.00	IF			LSEOBUNDLE	STATUS	=	"Final" (0020)			
	92.00	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)			
	93.00	IF	A	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020)		
		
93.02		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
94.00		OR		A: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)
95.00		Perform		SETS_Section	LSEOBUNDLE	One		
95.02		END	93.02					
				
	96.00	END	93								
	97.00	ELSE	92								
	98.00	Perform		SETS_Section	LSEOBUNDLE	One					
	99.00	END	92								
	100.00	END	91								
100.20	R1.0	IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)
20130904 Delete 100.22	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  
100.24	R1.0	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)
100.26	R1.0	IF	E	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)

100.27	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
100.28	R1.0	OR		E: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
100.30	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
100.31	R1.0	END	100.27					
	
100.32	R1.0	END	100.26					
100.34	R1.0	ELSE	100.24					
100.36	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
100.38	R1.0	END	100.24					
100.40	R1.0	END	100.20												
	101.00	END	90								
R1.0	101.20	COMMENT		The following from LSEOBUNDLE so that we do not have to queue DQ ABR for LSEOBUNDLE							
101.22	R1.0	IF			LSEO	STATUS	=	"Ready for Review" (0040)
20130904 Delete 101.24	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
101.26	R1.0	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)
101.28	R1.0	IF			LSEOBUNDLE	SPECBID	=	"No" (11457)
101.30	R1.0	IF	B	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
101.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
101.32	R1.0	OR		B: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
101.34	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
101.35	R1.0	END	101.31					

101.36	R1.0	END	101.30					
101.38	R1.0	ELSE	101.28				
101.40	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
101.42	R1.0	END	101.28					
101.44	R1.0	END	101.26					
101.46	R1.0	END	101.22					

	102.00	END	71	Section Two	
	 */
	protected void doLSEOSectionTwo(EntityItem[] lseoArray,String specbid) throws 
	MiddlewareRequestException, SQLException, MiddlewareException
	{			
		if (lseoArray==null || lseoArray.length==0){
			addDebug("doLSEOSectionTwo: entered with no LSEOs");
			return;
		}

		for (int x=0; x<lseoArray.length; x++){
			EntityItem lseoitem = lseoArray[x];
//			String lifecycle = PokUtils.getAttributeFlagValue(lseoitem, "LIFECYCLE");
//
//			addDebug("doLSEOSectionTwo: "+lseoitem.getKey()+" SPECBID: "+specbid+
//					" lifecycle "+lifecycle);
//
//			if (lifecycle==null || lifecycle.length()==0){
//				lifecycle = LIFECYCLE_Plan;
//			}

			//71.00	Section		Two							
			if (statusIsFinal(lseoitem)){
				//72.00	SET			LSEO	EPIMSABRSTATUS	norfr	&ABRWAITODS
				setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", getQueuedValueForItem(lseoitem,"EPIMSABRSTATUS"),lseoitem);

				if (SPECBID_NO.equals(specbid)){  // is No
					//74.00	IF		WWSEOLSEO-u	WWSEO	SPECBID	=	"No" (11457)	
					
					//do announcement checks	
					// go thru relator
					Vector lseoavailVct= PokUtils.getAllLinkedEntities(lseoitem, "LSEOAVAIL", "AVAIL");
					for (int ai=0; ai<lseoavailVct.size(); ai++){//no check on availtype to get here
						EntityItem avail = (EntityItem)lseoavailVct.elementAt(ai);
						String availtypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");
						addDebug("doLSEOSectionTwo: lseo.status=final specbid=no annchk "+avail.getKey()+" type "+
								availtypeFlag);

						Vector lseoannVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
						for (int i=0; i<lseoannVct.size(); i++){
							EntityItem annItem = (EntityItem)lseoannVct.elementAt(i);
							String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
							addDebug("doLSEOSectionTwo: "+annItem.getKey()+" type "+anntype);

							//75.000	IF		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
							//76.000	AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	WWPRTABRSTATUS		&ABRWAITODS2 
							if (statusIsFinal(annItem,"ANNSTATUS")){ 
								if(ANNTYPE_NEW.equals(anntype)){
									addDebug("doLSEOSectionTwo: annche "+annItem.getKey()+" is Final and New");
									//	WWPRTABRSTATUS		&ABRWAITODS2 
									setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValueForItem(annItem,"WWPRTABRSTATUS"),annItem);	
									//77.000	END	75.000

									//78.000	IF		LSEOAVAIL-d	AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
									//79.000	AND		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
									//80.000	AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	QSMRPTABRSTATUS 		&QSMWAITODS 
									if(PLANNEDAVAIL.equals(availtypeFlag)){
										//QSMRPTABRSTATUS 		&QSMWAITODS
										setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(annItem,"QSMRPTABRSTATUS"),annItem);
									}
									//81.000	END	78.000
								}
								//82.000	IF		LSEOAVAIL-d	AVAIL	AVAILTYPE	=	"Last Order" (149)			
								//83.000	AND		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
								//84.000	AND			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)	QSMRPTABRSTATUS 		&QSMWAITODS 
								if(ANNTYPE_EOL.equals(anntype)){
									addDebug("doLSEOSectionTwo: "+annItem.getKey()+" is Final and EOL");
									if(LASTORDERAVAIL.equals(availtypeFlag)){
										//QSMRPTABRSTATUS 		&QSMWAITODS
										setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(annItem,"QSMRPTABRSTATUS"),annItem);
									}
								}
								//85.000	END	82.000
							}// end ann = final
						}// end ann loop
						lseoannVct.clear();
					}//end avail loop
					
					//85.320	IF			LSEO	STATUS	=	"Final" (0020)			
					//Add 2011-10-20		85.322	IF			LSEO	LSEOPUBDATEMTRGT	>	"2010-03-01" | Empty			
					String lseopubdate = PokUtils.getAttributeValue(lseoitem, "LSEOPUBDATEMTRGT","", null, false);
					addDebug("doLSEOSectionTwo: "+lseoitem.getKey()+" lseopubdate "+lseopubdate);
					if(lseopubdate==null || lseopubdate.compareTo(OLD_DATA_ANNDATE)>0){	
						for (int ai=0; ai<lseoavailVct.size(); ai++){//no check on availtype to get here
							EntityItem avail = (EntityItem)lseoavailVct.elementAt(ai);
							String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
							if (availAnntypeFlag==null){
								availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
							}
							addDebug("doLSEOSectionTwo: lseo.status=final specbid=no "+avail.getKey()
									+" availanntype "+availAnntypeFlag);

							if (statusIsFinal(avail)){
								//85.380		SET			LSEO				ADSABRSTATUS		&ADSFEED
								setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
								break;
//								//85.340	IF		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)	
//								if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//									//85.350	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
//									//85.380		SET			LSEO				ADSABRSTATUS		&ADSFEED
//									setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
//									break;
//								}
//								//85.390	END	85.350	
//								else{ // avail is RFA							
//									//85.360		OR		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)			
//									Vector lseoannVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//									Vector lseoannFinalVct= PokUtils.getEntitiesWithMatchedAttr(lseoannVct, "ANNSTATUS", STATUS_FINAL);
//									addDebug("doLSEOSectionTwo: lseoannVct "+lseoannVct.size()+" lseoannFinalVct "+lseoannFinalVct.size());
//									if(lseoannFinalVct.size()>0){
//										//85.380		SET			LSEO				ADSABRSTATUS		&ADSFEED
//										setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
//										break;
//										//	85.390	R1.0	END	85.350	
//									}
//								} // end is RFA AVAIL
								//85.392	R1.0	END	85.340
							} // avail is final
						}// end avail loop
					}else{
						//Add 2011-10-20		85.394	ELSE	85.322								
						//Add 2011-10-20		85.396	SET			LSEO				ADSABRSTATUS		&ADSFEED
						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
						//Add 2011-10-20		85.398	END	85.322	
						break;
					}
				}// end specbid=no
				else{
					addDebug("doLSEOSectionTwo: lseo.status=final specbid=yes");
					//86.00	ELSE	74						WWPRTABRSTATUS	norfr	&ABRWAITODS2 
					setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValueForItem(lseoitem,"WWPRTABRSTATUS"),lseoitem);
					//87.00	SET							QSMRPTABRSTATUS 	norfr	&QSMWAITODS 
					setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(lseoitem,"QSMRPTABRSTATUS"),lseoitem);
					//87.23	SET			LSEO				ADSABRSTATUS		&ADSFEED
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
					//87.24	END	87.2
				}// end specbid=yes
			}// end lseo = final

			if (statusIsRFR(lseoitem)){
//				if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//						LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
					if (SPECBID_NO.equals(specbid)){  // is No
						//74.00	IF		WWSEOLSEO-u	WWSEO	SPECBID	=	"No" (11457)	
						doLSEOSect2R4R_R10Processing(lseoitem, "LSEOAVAIL");
						//88.00	END	74	
					}else{ // specbid=yes
						//R1.0	87.10   IF			LSEO	STATUS	=	"Ready for Review" (0040)			
						//20130904 Delete R1.0	87.11	AND			LSEO	LIFECYCLE	=	"Develop" (LF02) | "Plan" (LF01)
						//R1.0	87.12	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
						//R1.0	87.13	END	87.1	
					} //end specbid=yes
//				}// end lifecycle=dev or plan
				//88.00	END	74
			}// end rfr and r10 loop
		} // end lseoitem array loop

		//89.00 The following from LSEOBUNDLE when Final so that we do not have to queue DQ ABR for LSEOBUNDLE	
		// pull a new VE LSEOBUNDLEAVAIL, AVAILANNA
		// entityitem passed in is adjusted in extract, we don't want that so create new one
		String VeName = "EXRPT3LSEODQ";
		EntityItem lseos[] = new EntityItem[lseoArray.length];
		for (int x=0; x<lseoArray.length; x++){
			lseos[x] = new EntityItem(null, m_elist.getProfile(), "LSEO", lseoArray[x].getEntityID());
		}
		otherList = m_db.getEntityList(m_elist.getProfile(),
				new ExtractActionItem(null, m_db, m_elist.getProfile(), VeName),
				lseos);
		addDebug("doLSEOSectionTwo2: Extract "+VeName+NEWLINE+PokUtils.outputList(otherList));

		EntityGroup eg = otherList.getParentEntityGroup();
		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem lseoItem = eg.getEntityItem(i);
//			String lifecycle = PokUtils.getAttributeFlagValue(lseoItem, "LIFECYCLE");
//			addDebug("doLSEOSectionTwo2: "+lseoItem.getKey()+" lifecycle "+lifecycle);
//
//			if (lifecycle==null || lifecycle.length()==0){
//				lifecycle = LIFECYCLE_Plan;
//			}

			Vector bdlVct = PokUtils.getAllLinkedEntities(lseoItem, "LSEOBUNDLELSEO", "LSEOBUNDLE");

			for (int i2=0; i2<bdlVct.size(); i2++){
				EntityItem lseobdlItem = (EntityItem)bdlVct.elementAt(i2);
				String bdlspecbid = getAttributeFlagEnabledValue(lseobdlItem, "SPECBID");

				addDebug("doLSEOSectionTwo2: "+lseoItem.getKey()+" "+lseobdlItem.getKey()+
						" bdl.SPECBID: "+bdlspecbid);		
				//90.00	IF			LSEO	STATUS	=	"Final" (0020)	
				if (statusIsFinal(lseoItem)){	
					//91.00	IF			LSEOBUNDLE	STATUS	=	"Final" (0020)			
					if (statusIsFinal(lseobdlItem)){
						//92.00	 IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)
						if (SPECBID_NO.equals(bdlspecbid)){
							Vector availVct = PokUtils.getAllLinkedEntities(lseobdlItem, "LSEOBUNDLEAVAIL", "AVAIL");
							Vector finalAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "STATUS", STATUS_FINAL);
//							Vector annVct = PokUtils.getAllLinkedEntities(finalAvailVct, "AVAILANNA", "ANNOUNCEMENT");
//							Vector finalAnnVct = PokUtils.getEntitiesWithMatchedAttr(annVct, "ANNSTATUS", STATUS_FINAL);
							addDebug("doLSEOSectionTwo2: "+lseobdlItem.getKey()+" availVct "+availVct.size()+
									" finalAvailVct "+finalAvailVct.size());
							//93.00	IF	A	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
							//93.02		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
							//94.00		OR		A: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)
							//If there is an AVAIL that is Final that is in an ANNOUNCEMENT that is Final, then this is TRUE
							if (finalAvailVct.size()>0){
								//95.00	Perform		SETS_Section	LSEOBUNDLE	One	
								doLSEOBDLSectionOne(lseobdlItem);
//							}else{
//								//look at avails to see if any are not RFA
//								for (int ai2=0; ai2<finalAvailVct.size(); ai2++){
//									EntityItem availItem = (EntityItem)finalAvailVct.elementAt(ai2);
//									String availAnntypeFlag = PokUtils.getAttributeFlagValue(availItem, "AVAILANNTYPE");
//									if (availAnntypeFlag==null){
//										availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//									}
//									addDebug("doLSEOSectionTwo2:  "+availItem.getKey()+" availanntype "+availAnntypeFlag);
//									
//									//93.02		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
//									if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//										//95.00		Perform		SETS_Section	LSEOBUNDLE	One		
//										doLSEOBDLSectionOne(lseobdlItem);
//										break;
//									}
//								}
							}
							//	95.02		END	93.02
							//96.00	END	93
							availVct.clear();
							finalAvailVct.clear();						
						}else{// specbid=yes
							//97.00	ELSE	92								
							//98.00	Perform		SETS_Section	LSEOBUNDLE	One					
							doLSEOBDLSectionOne(lseobdlItem);
							//99.00	END	92
						}
						//100.00	END	91	
					}// end bdlstatus=final

					//R1.0	100.20
					doR10BundleChecks(lseobdlItem, bdlspecbid);
					//R1.0	100.40	END	100.2

					// 101.00	END	90
				}// end lseo.status=final 

				//R1.0	101.20	COMMENT		The following from LSEOBUNDLE so that we do not have to queue DQ ABR for LSEOBUNDLE							
				//R1.0	101.22	IF			LSEO	STATUS	=	"Ready for Review" (0040)
				//20130904 Delete R1.0	101.24	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)	
				if (statusIsRFR(lseoItem)){
//				&& 
//						(LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//								LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
					doR10RFRBundleChecks(lseobdlItem, bdlspecbid);
				} // end lseo.status=rfr
				//R1.0	101.46	END	101.22
			}

			bdlVct.clear();
		} // end lseo loop
		//102.00	END	109	Section Two		
	}

	/*
	 *     	
90.00	IF			LSEO	STATUS	=	"Final" (0020)			
...
100.20	R1.0	IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)
100.22	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  
100.24	R1.0	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)
100.26	R1.0	IF	E	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)

100.27	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
100.28	R1.0	OR		E: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
100.30	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
100.31	R1.0	END	100.27					
	
100.32	R1.0	END	100.26					
100.34	R1.0	ELSE	100.24					
100.36	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
100.38	R1.0	END	100.24					
100.40	R1.0	END	100.20									
	101.00	END	90								

	 */
	private void doR10BundleChecks(EntityItem lseobdlItem, String bdlspecbid)
	{
		if(!doR10processing()){
			return;
		}
//		String lifecycle = PokUtils.getAttributeFlagValue(lseobdlItem, "LIFECYCLE");
//		addDebug("doR10BundleChecks: "+lseobdlItem.getKey()+" lifecycle "+lifecycle);
//
//		if (lifecycle==null || lifecycle.length()==0){
//			lifecycle = LIFECYCLE_Plan;
//		}
//
//		//R1.0	100.20	IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)
//		//20130904 Delete R1.0  100.22	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02) | "Plan" (LF01)
//		if ((LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//				LIFECYCLE_Develop.equals(lifecycle)) // been RFR before
				if(statusIsRFR(lseobdlItem)){
			//R1.0	100.24	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)
			if (SPECBID_NO.equals(bdlspecbid)){
				Vector availVct = PokUtils.getAllLinkedEntities(lseobdlItem, "LSEOBUNDLEAVAIL", "AVAIL");
				for (int av=0; av<availVct.size(); av++){
					EntityItem availItem = (EntityItem)availVct.elementAt(av);

					//R1.0	100.26	IF	E	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
					if (statusIsRFRorFinal(availItem)){
						//100.30	R1.0	Perform		SETS_Section	LSEOBUNDLE	One	
						doLSEOBDLSectionOne(lseobdlItem);
						break;
					}
//						String availAnntypeFlag = PokUtils.getAttributeFlagValue(availItem, "AVAILANNTYPE");
//						if (availAnntypeFlag==null){
//							availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//						}
//						addDebug("doR10BundleChecks "+availItem.getKey()+" availanntype "+availAnntypeFlag);
//						if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//							//100.27	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
//							//100.30	R1.0	Perform		SETS_Section	LSEOBUNDLE	One	
//							doLSEOBDLSectionOne(lseobdlItem);
//							continue;
//						}
//
//						Vector annVct = PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", "ANNOUNCEMENT");
//						for (int av2=0; av2<annVct.size(); av2++){
//							EntityItem annItem = (EntityItem)annVct.elementAt(av2);
//							//100.28	R1.0	OR		E: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
//							if (statusIsRFRorFinal(annItem,"ANNSTATUS")){
//								//R1.0	100.30	Perform		SETS_Section	LSEOBUNDLE	One	
//								doLSEOBDLSectionOne(lseobdlItem);
//							}
//						}
						//100.31	R1.0	END	100.27	
//					}
					//R1.0	100.32	END	100.26
					//R1.0	101.36	END	101.3
				} //end avail loop
			}else{ // specbid=yes
				//R1.0	100.34	ELSE	100.24					
				//R1.0	100.36	Perform		SETS_Section	LSEOBUNDLE	One						
				doLSEOBDLSectionOne(lseobdlItem);
				//R1.0	100.38	END	100.24
			} // endspecbid=yes
		}// lseobdl is rfr and dev
		//R1.0	100.40	END	100.2	
	}
	/**
	 * 
			85.200	IF			LSEO	STATUS	=	"Ready for Review" (0040)		
			85.220	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
	Add 2011-10-20		85.222	IF			LSEO	LSEOPUBDATEMTRGT	>	"2010-03-01" | Empty		
	Change 2011-10-20		85.240	IF		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
			85.250	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
			85.260	OR		LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020) | "Ready for Review" (0040)		
			85.280	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
			85.290	END	85.250							
	Add 2011-10-20		85.292	END	85.240							
	Add 2011-10-20		85.294	ELSE	85.222							
	Add 2011-10-20		85.296	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
	Add 2011-10-20		85.298	END	85.222							
			85.300	END	85.200	
	 * @param theItem
	 * @param availRel
	 */
	private void doLSEOSect2R4R_R10Processing(EntityItem lseoItem,String availRel) 
	{
		//85.200	IF			LSEO	STATUS	=	"Ready for Review" (0040)		
//		//20130904 Delete 85.220	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
//		String lifecycle = PokUtils.getAttributeFlagValue(lseoItem, "LIFECYCLE");
//		addDebug("doLSEOSect2R4R_R10Processing: "+lseoItem.getKey()+" availRel "+availRel+" lifecycle "+lifecycle);
//		if (lifecycle==null || lifecycle.length()==0){ 
//			lifecycle = LIFECYCLE_Plan;
//		}
//		if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//				LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
			String lseopubdate = PokUtils.getAttributeValue(lseoItem, "LSEOPUBDATEMTRGT","", null, false);
			addDebug("doLSEOSect2R4R_R10Processing: "+lseoItem.getKey()+" lseopubdate "+lseopubdate);
			//Add 2011-10-20		85.222	IF			LSEO	LSEOPUBDATEMTRGT	>	"2010-03-01" | Empty	
			if(lseopubdate==null || lseopubdate.compareTo(OLD_DATA_ANNDATE)>0){
				Vector availVct= PokUtils.getAllLinkedEntities(lseoItem, availRel, "AVAIL");
				availloop:for (int ai=0; ai<availVct.size(); ai++){
					EntityItem avail = (EntityItem)availVct.elementAt(ai);

					//85.240	IF		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
					if (statusIsRFRorFinal(avail)){
						
						//85.280	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR			
						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoItem,"ADSABRSTATUS"),lseoItem);
						break availloop;
//						String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
//						if (availAnntypeFlag==null){
//							availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//						}
//						addDebug("doLSEOSect2R4R_R10Processing: "+avail.getKey()+" availanntype "+availAnntypeFlag);
//
//						//85.250	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)	
//						if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//							//85.280	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
//							setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoItem,"ADSABRSTATUS"),lseoItem);
//							break availloop;
//						}
//
//						Vector annVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//						for (int i=0; i<annVct.size(); i++){
//							EntityItem annItem = (EntityItem)annVct.elementAt(i);
//							//85.260 OR	LSEOAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020) | "Ready for Review" (0040)			
//							if(statusIsRFRorFinal(annItem, "ANNSTATUS")){
//								//85.280	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR			
//								setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoItem,"ADSABRSTATUS"),lseoItem);
//								break availloop;
//							}
						}// end ann loop
//						annVct.clear();
						//85.290	END	85.250	
//					}
					//	Add 2011-10-20		85.292	END	85.240
				}// end avail loop
				availVct.clear();
			}else{
				//Add 2011-10-20		85.294	ELSE	85.222							
				//Add 2011-10-20		85.296	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(lseoItem,"ADSABRSTATUS"),lseoItem);
				//Add 2011-10-20		85.298	END	85.222	
			}			
//		}

		//	85.300	END	85.200		
	}
	/****************
	 * 		
101.20	COMMENT		The following from LSEOBUNDLE so that we do not have to queue DQ ABR for LSEOBUNDLE				
101.22	R1.0	IF			LSEO	STATUS	=	"Ready for Review" (0040)
101.24	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
101.26	R1.0	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)
101.28	R1.0	IF			LSEOBUNDLE	SPECBID	=	"No" (11457)
101.30	R1.0	IF	B	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
101.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
101.32	R1.0	OR		B: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
101.34	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
101.35	R1.0	END	101.31					
	
101.36	R1.0	END	101.30					
101.38	R1.0	ELSE	101.28					
101.40	R1.0	Perform		SETS_Section	LSEOBUNDLE	One		
101.42	R1.0	END	101.28					
101.44	R1.0	END	101.26					
101.46	R1.0	END	101.22					

	102.00	END	71	Section Two				
	103.00	END	69	LSEO				

	 * @param lseobdlItem
	 * @param bdlspecbid
	 */
	private void doR10RFRBundleChecks(EntityItem lseobdlItem, String bdlspecbid)
	{
		if(!doR10processing()){
			return;
		}

		addDebug("doR10RFRBundleChecks: "+lseobdlItem.getKey()+" bdlspecbid "+bdlspecbid);
		if(statusIsRFR(lseobdlItem)){
			//R1.0	100.24	IF		LSEOBUNDLELSEO-u	LSEOBUNDLE	SPECBID	=	"No" (11457)
			//R1.0	101.28	IF			LSEOBUNDLE	SPECBID	=	"No" (11457)
			if (SPECBID_NO.equals(bdlspecbid)){
				Vector availVct = PokUtils.getAllLinkedEntities(lseobdlItem, "LSEOBUNDLEAVAIL", "AVAIL");
				for (int av=0; av<availVct.size(); av++){
					EntityItem availItem = (EntityItem)availVct.elementAt(av);
					//R1.0	101.30	IF	B	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
					if (statusIsRFRorFinal(availItem)){
						//101.34	R1.0	Perform		SETS_Section	LSEOBUNDLE	One	
						doLSEOBDLSectionOne(lseobdlItem);
						break;
//						//101.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
//						String availAnntypeFlag = PokUtils.getAttributeFlagValue(availItem, "AVAILANNTYPE");
//						if (availAnntypeFlag==null){
//							availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
//						}
//						addDebug("doR10RFRBundleChecks: "+availItem.getKey()+" availanntype "+availAnntypeFlag);
//						
//						//	85.34	AND		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020)
//						if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//							//101.34	R1.0	Perform		SETS_Section	LSEOBUNDLE	One	
//							doLSEOBDLSectionOne(lseobdlItem);
//							break;
//						}
//
//						Vector annVct = PokUtils.getAllLinkedEntities(availItem, "AVAILANNA", "ANNOUNCEMENT");
//						for (int av2=0; av2<annVct.size(); av2++){
//							EntityItem annItem = (EntityItem)annVct.elementAt(av2);
//							//101.32	R1.0	OR		B: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
//							if (statusIsRFRorFinal(annItem,"ANNSTATUS")){
//								//101.34	R1.0	Perform		SETS_Section	LSEOBUNDLE	One	
//								doLSEOBDLSectionOne(lseobdlItem);
//								break;
//							}
//						}
						//101.35	R1.0	END	101.31	
					}
					//R1.0	101.36	END	101.3
				} //end avail loop
			}else{ // specbid=yes
				//R1.0	101.38	ELSE	101.28					
				//R1.0	101.40	Perform		SETS_Section	LSEOBUNDLE	One			
				doLSEOBDLSectionOne(lseobdlItem);
				//R1.0	101.42	END	101.28
			} // endspecbid=yes
			//R1.0	101.44	END	101.26
		}// lseobdl is rfr
	}

	protected String getQueuedValueForItem(EntityItem item, String attrcode){
		if (item.getEntityType().equals(getEntityType())){
			return getQueuedValue(attrcode);
		}
		// find abr attr, then get queued value from properties for that
		String abrAttrCode = (String)ABR_ATTR_TBL.get(item.getEntityType());
		if(abrAttrCode==null){
			addDebug("WARNING: cant find ABR attribute code for "+item.getEntityType());
			return getQueuedValue(attrcode);
		}
		//ANNABRSTATUS_QSMRPTABRSTATUS_queuedValue=0090
		String queueKey = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.QUEUEDVALUE;
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(abrAttrCode,
				"_"+attrcode+queueKey,ABR_QUEUED);
	}
	protected String getRFRQueuedValueForItem(EntityItem item, String attrcode){
		if (item.getEntityType().equals(getEntityType())){
			return getRFRQueuedValue(attrcode);
		}
		// find abr attr, then get queued value from properties for that
		String abrAttrCode = (String)ABR_ATTR_TBL.get(item.getEntityType());
		if(abrAttrCode==null){
			addDebug("WARNING: cant find ABR attribute code for "+item.getEntityType());
			return getRFRQueuedValue(attrcode);
		}
		String queueKey = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.RFRQUEUEDVALUE;
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(abrAttrCode,
				"_"+attrcode+queueKey,ABR_QUEUED);
	}
	
	protected String getRFRFIRSTQueuedValueForItem(EntityItem item, String attrcode){
//		if (item.getEntityType().equals(getEntityType())){
//			return getRFRQueuedValue(attrcode);
//		}
		// find abr attr, then get queued value from properties for that
		String abrAttrCode = (String)ABR_ATTR_TBL.get(item.getEntityType());
		if(abrAttrCode==null){
			addDebug("WARNING: cant find ABR attribute code for "+item.getEntityType());
			return getRFRQueuedValue(attrcode);
		}
		String queueKey = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.RFRFIRSTQUEUEVALUE;
		// If &ADSFEEDRFRFIRST is not specified in the property file, then use the value specified for &ADSFEEDRFR. 
				// If &ADSFEEDRFR is not specified in the property file, then use 0020.
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(abrAttrCode,
						"_"+attrcode+queueKey,getRFRQueuedValueForItem(item,attrcode));
		
	}
	
	protected String getFINALFIRSTQueuedValueForItem(EntityItem item, String attrcode){
//		if (item.getEntityType().equals(getEntityType())){
//			return getRFRQueuedValue(attrcode);
//		}
		// find abr attr, then get queued value from properties for that
		String abrAttrCode = (String)ABR_ATTR_TBL.get(item.getEntityType());
		if(abrAttrCode==null){
			addDebug("WARNING: cant find ABR attribute code for "+item.getEntityType());
			return getRFRQueuedValue(attrcode);
		}
		String queueKey = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.FINALFIRSTQUEUEVALUE;
		return COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue(abrAttrCode,
				"_"+attrcode+queueKey,getQueuedValueForItem(item,attrcode));
	}

	protected boolean statusIsRFRorFinal(EntityItem item){
		return statusIsRFRorFinal(item, "STATUS");
	}
	protected boolean statusIsRFRorFinal(EntityItem item, String statusAttrCode){
		String status = getAttributeFlagEnabledValue(item, statusAttrCode);
		// must allow for running on root and root is not moved to final or rfr yet
		boolean isRoot = item.getEntityType().equals(getEntityType()) && item.getEntityID()==getEntityID();
		boolean isRootFinal = false;
		boolean isRootRFR = false;
		if (isRoot){
			isRootFinal = DQ_FINAL.equals(getAttributeFlagEnabledValue(item, "DATAQUALITY"));
			isRootRFR = DQ_R4REVIEW.equals(getAttributeFlagEnabledValue(item, "DATAQUALITY"));
		}

		addDebug("statusIsRFRorFinal: "+item.getKey()+" status "+status+" isRoot "+isRoot+
				(isRoot?(" isRootRFR "+isRootRFR+" isRootFinal "+isRootFinal):""));
		if (status==null || status.length()==0){
			status = STATUS_FINAL;
		}

		return (isRootFinal || isRootRFR || STATUS_R4REVIEW.equals(status) || STATUS_FINAL.equals(status));
	}
	protected boolean statusIsRFR(EntityItem item){
		return statusIsRFR(item, "STATUS");
	}
	protected boolean statusIsRFR(EntityItem item, String statusAttrCode){
		String status = getAttributeFlagEnabledValue(item, statusAttrCode);
		// must allow for running on root and root is not moved to final or rfr yet
		boolean isRoot = item.getEntityType().equals(getEntityType()) && item.getEntityID()==getEntityID();
		boolean isRootRFR = false;
		if (isRoot){
			isRootRFR = DQ_R4REVIEW.equals(getAttributeFlagEnabledValue(item, "DATAQUALITY"));
		}
		addDebug("statusIsRFR: "+item.getKey()+" status "+status+" isRoot "+isRoot+
				(isRoot?(" isRootRFR "+isRootRFR):""));

		return (isRootRFR || STATUS_R4REVIEW.equals(status));
	}
	protected boolean statusIsFinal(EntityItem item){
		return statusIsFinal(item, "STATUS");
	}
	protected boolean statusIsFinal(EntityItem item, String statusAttrCode){
		String status = getAttributeFlagEnabledValue(item, statusAttrCode);
		// must allow for running on root and root is not moved to final or rfr yet
		boolean isRoot = item.getEntityType().equals(getEntityType()) && item.getEntityID()==getEntityID();
		boolean isRootFinal = false;
		if (isRoot){
			isRootFinal = DQ_FINAL.equals(getAttributeFlagEnabledValue(item, "DATAQUALITY"));
		}
		addDebug("statusIsFinal: "+item.getKey()+" status "+status+" isRoot "+isRoot+
				(isRoot?(" isRootFinal "+isRootFinal):""));
		if (status==null || status.length()==0){
			status = STATUS_FINAL;
		}

		return (isRootFinal || STATUS_FINAL.equals(status));
	}
	protected boolean doR10processing(){
		String r10Str =  COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("BH",
				"_R10","false");
		addDebug("doR10processing "+r10Str);

		return r10Str.equalsIgnoreCase("true");
	}

	/**
	 * used when entities are going to rfr and must check RFA
	 * 
MODEL
	136.02		IF			MODEL	STATUS	=	"Ready for Review" (0040)			
	136.04		AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	136.06		AND		MODELAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
	136.08		IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
	136.10		IF			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)	
	136.11	R1.0	OR			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
		
	136.12		SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
	136.13		END	136.10
	136.14		ELSE	136.08								
	136.16		SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
	136.18		END	136.08								
	136.20		END	136.02	
---------
SVCMOD
175.02	R1.0	IF			SVCMOD	STATUS	=	"Ready for Review" (0040)			
175.04	R1.0	AND			SVCMOD	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
175.06	R1.0	AND		SVCMODAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
175.08	R1.0	IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)			
175.10	R1.0	IF			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
175.12	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
175.14	R1.0	ELSE	175.08								
175.16	R1.0	SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	
175.17	R1.0	END	175.10
175.18	R1.0	END	175.08								
175.20	R1.0	END	175.02	
	 * @param availRel
	 */
	protected void doR4R_RFAProcessing(String availRel) 
	{
		EntityItem rootItem= m_elist.getParentEntityGroup().getEntityItem(0);
//		//136.02		IF			MODEL	STATUS	=	"Ready for Review" (0040)				
//		String lifecycle = PokUtils.getAttributeFlagValue(rootItem, "LIFECYCLE");
//		addDebug("doR4R_RFAProcessing: "+rootItem.getKey()+" lifecycle "+lifecycle);
//		if (lifecycle==null || lifecycle.length()==0){ 
//			lifecycle = LIFECYCLE_Plan;
//		}
//		// 136.04	R1.0	AND			MODEL	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
//		if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//				LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
//		20130904 Delete	handled by what follows	175.000		SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR	&ADSFEED		
//		175.020		IF			SVCMOD	STATUS	=	"Ready for Review" (0040)					
//20130904 Change	Column E & J & O	175.040		IF			SVCMOD	LIFECYCLE	<>	"Develop" (LF02)  | "Plan" (LF01)				if true, then was once Final	
//		175.060		AND		SVCMODAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)					
//20130904 Add		175.068		SET			SVCMOD				ADSABRSTATUS	&ADSFEEDRFR		Was once Final	
//20130904 Add		175.100		ELSE	175.060										
//20130904 Change	421	175.120		SET			SVCMOD	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS	&ADSFEEDRFR		XML was sent	
//20130904 Add		175.122		SET			SVCMOD	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS	&ADSFEEDRFRFIRST		XML was never sent	
//20130904 Add		175.190		END	175.060										

			Vector availVct= PokUtils.getAllLinkedEntities(rootItem, availRel, "AVAIL");
			availloop:for (int ai=0; ai<availVct.size(); ai++){
				EntityItem avail = (EntityItem)availVct.elementAt(ai);
				String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
				if (availAnntypeFlag==null){
					availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
				}
				addDebug("doR4R_RFAProcessing: "+avail.getKey()+" availAnntypeFlag "+availAnntypeFlag);
				// 136.06		AND		MODELAVAIL-d: 	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)	
				if (statusIsRFRorFinal(avail)){
					//136.16		SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
					doRFR_MODADSXML(rootItem);
//					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(rootItem,"ADSABRSTATUS"),rootItem);
					//136.18		END	136.08	
					addDebug("doR4R_RFAProcessing: "+rootItem.getKey()+" ADSABRSTATUS is queued");
					break availloop;
//					//136.08		IF			AVAIL	AVAILANNTYPE	=	"RFA" (RFA)	
//					if (AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//						Vector annVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//						for (int i=0; i<annVct.size(); i++){
//							EntityItem annItem = (EntityItem)annVct.elementAt(i);
//							addDebug("doR4R_RFAProcessing: "+annItem.getKey());
//							// 136.10		IF			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)	
//							if(statusIsRFRorFinal(annItem, "ANNSTATUS")){
//								// 136.12		SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR			
//								setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(rootItem,"ADSABRSTATUS"),rootItem);
//								addDebug("doR4R_RFAProcessing: RFA done RFR or Final ann "+rootItem.getKey()+" ADSABRSTATUS is queued");
//								annVct.clear();
//								break availloop;
//							}
//						}// end ann loop
//						annVct.clear();
//						//136.13		END	136.10
//					}else{
//						//avail was not RFA
//						//136.14		ELSE	136.08								
//						//136.16		SET			MODEL				ADSABRSTATUS	&ADSFEEDRFR	
//						setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(rootItem,"ADSABRSTATUS"),rootItem);
//						//136.18		END	136.08	
//						addDebug("doR4R_RFAProcessing: not RFA "+rootItem.getKey()+" ADSABRSTATUS is queued");
//						break availloop;
//						//136.20		END	136.02	
//					}
				}
			}// end avail loop
			availVct.clear();
//		}	
	}
	/**
	 * @param availRel
	 *
-----------------
PRODSTRUCT
151.20		IF			PRODSTRUCT	STATUS	=	"Ready for Review" (0040)			
151.22		AND			PRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
151.24		IF		PRODSTRUCT-u	FEATURE	FCTYPE	=	""RPQ-ILISTED" (120) |"RPQ-PLISTED" (130) |"Placeholder" (402)"			
151.26		SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR	
151.28		ELSE	151.24								
151.30		IF		OOFAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
151.31	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
151.32		OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
151.34		SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
151.35	R1.0	END	151.31							

151.36		END	151.30								
151.38		END	151.24								
151.40		END	151.20
------------------------
SWPRODSTRUCT
181.02	R1.0	IF			SWPRODSTRUCT	STATUS	=	"Ready for Review" (0040)		
181.04	R1.0	AND			SWPRODSTRUCT	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
181.06	R1.0	IF		SWPRODSTRUCTAVAIL-d: AVAILANNA	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
181.07	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
181.08	R1.0	OR			ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
181.10	R1.0	SET			SWPRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR
181.11	R1.0	END	181.07							
181.12		END	181.06							
181.14	R1.0	END	181.02							

	 */
	protected void doR4R_R10Processing(EntityItem theItem,String availRel) 
	{
		//85.20	IF			LSEO	STATUS	=	"Ready for Review" (0040)			
		//85.22	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)	| "Plan" (LF01)	
		String lifecycle = PokUtils.getAttributeFlagValue(theItem, "LIFECYCLE");
//		addDebug("doR4R_R10Processing: "+theItem.getKey()+" availRel "+availRel+" lifecycle "+lifecycle);
//		if (lifecycle==null || lifecycle.length()==0){ 
//			lifecycle = LIFECYCLE_Plan;
//		}
//		if (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
//				LIFECYCLE_Develop.equals(lifecycle)){ // been RFR before
			Vector availVct= PokUtils.getAllLinkedEntities(theItem, availRel, "AVAIL");
			availloop:for (int ai=0; ai<availVct.size(); ai++){
				EntityItem avail = (EntityItem)availVct.elementAt(ai);

				//85.24	AND		LSEOAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
				if (statusIsRFRorFinal(avail)){			
//					151.340		SET			PRODSTRUCT				ADSABRSTATUS	&ADSFEEDRFR			
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(theItem,"ADSABRSTATUS"),theItem);
					break availloop;
				}
			}// end avail loop
			availVct.clear();
//		}

		//85.30	END	85.2	
	}
	/**********************************
	 * complete LSEOBUNDLE DQ abr processing after status moved to final 
	 * LSEOBUNDLEAVAIL, AVAILANNA must be in extract
	 * @param lseobdlItem
	 * 
106.00		Section		One							
107.00	Defer	SET			LSEOBUNDLE				COMPATGENABR	&COMPATGENRFR	&COMPATGEN
109.00		SET			LSEOBUNDLE				EPIMSABRSTATUS		&ABRWAITODS
110.00		IF			LSEOBUNDLE	SPECBID	=	"No" (11457)			
old111.00		IF		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
old112.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	WWPRTABRSTATUS		&ABRWAITODS2 
old113.00		END	111.00	

111.00		IF		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
112.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	
112.20		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)			
112.22		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)			
112.24		SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2 
113.00		END	111.00								

114.00		IF		LSEOBUNDLEAVAIL-d	AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
115.00		AND		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
116.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19) 
116.20		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)			
116.22		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)			
116.24		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
117.00		END	114.00									

118.00		IF		LSEOBUNDLEAVAIL-d	AVAIL	AVAILTYPE	=	"Last Order" (149)			
119.00		AND		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
120.00		AND			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)	QSMRPTABRSTATUS 		&QSMWAITODS 
120.02		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)			
120.04		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)			
120.06		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
121.00		END	118.00								


Change 2011-10-20		121.240	IF			LSEOBUNDLE	STATUS	=	"Final" (0020)			
Add 2011-10-20		121.242	IF			LSEOBUNDLE	BUNDLPUBDATEMTRGT	>	"2010-03-01" | Empty			
Add 2011-10-20		121.244	IF	A	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020)			
		121.250	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)			
		121.260	OR		A: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)			
		121.270	SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
Add 2011-10-20		121.271	END	121.250								
Add 2011-10-20		121.272	END	121.244								
Add 2011-10-20		121.273	ELSE	121.242								
Add 2011-10-20		121.274	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEED	
Add 2011-10-20		121.275	END	121.242								
Change 2011-10-20		121.280	END	121.240								
							
		121.500	IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
		121.520	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
Add 2011-10-20		121.522	IF			LSEOBUNDLE	BUNDLPUBDATEMTRGT	>	"2010-03-01" | Empty		
Change 2011-10-20		121.540	IF		LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		121.550	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)		
		121.560	OR		A: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020) | "Ready for Review" (0040)		
		121.580	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
Add 2011-10-20		121.582	END	121.550							
Add 2011-10-20		121.584	END	121.540							
Add 2011-10-20		121.586	ELSE	121.522							
Add 2011-10-20		121.588	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
Add 2011-10-20		121.590	END	121.522							
		121.600	END	121.500							
					
							
122.00		ELSE	110.00		LSEOBUNDLE				WWPRTABRSTATUS		&ABRWAITODS2 
123.00		SET			LSEOBUNDLE				QSMRPTABRSTATUS 		&QSMWAITODS 
123.18		IF			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
123.19		IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
123.20	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
123.22		ELSE	123.18								
123.23		IF			LSEOBUNDLE	STATUS	=	"Final"			
123.24	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
123.26		END	123.18								
124.00		END	110.00								
125.00		END	106.00	Section One							

	 */
	protected void doLSEOBDLSectionOne(EntityItem lseobdlItem)
	{
		String specbid = getAttributeFlagEnabledValue(lseobdlItem, "SPECBID");

		addDebug("doLSEOBDLSectionOne: "+lseobdlItem.getKey()+" SPECBID: "+specbid);

		//106.00		Section		One		
		if (statusIsFinal(lseobdlItem)){						
			//Defer	107.00	SET							COMPATGENABR	&COMPATGENRFR	&COMPATGEN done in props file
			setFlagValue(m_elist.getProfile(),"COMPATGENABR", getQueuedValueForItem(lseobdlItem,"COMPATGENABR"),lseobdlItem);
			//	109.00	SET							EPIMSABRSTATUS		&ABRWAITODS
			setFlagValue(m_elist.getProfile(),"EPIMSABRSTATUS", getQueuedValueForItem(lseobdlItem,"EPIMSABRSTATUS"),lseobdlItem);
		}// end bdlstatus=final
		else if (statusIsRFR(lseobdlItem)){
			//Defer	107.00	SET			COMPATGENABR	&COMPATGENRFR	&COMPATGEN deferred in prop file
			setFlagValue(m_elist.getProfile(),"COMPATGENABR", getRFRQueuedValueForItem(lseobdlItem,"COMPATGENABR"),lseobdlItem);
		}

		//110.00		IF			LSEOBUNDLE	SPECBID	=	"No" (11457)	
		if (SPECBID_NO.equals(specbid)){  // is No
			Vector lseobdlavailVct= PokUtils.getAllLinkedEntities(lseobdlItem, "LSEOBUNDLEAVAIL", "AVAIL");
			// these checks do not look at the pubdate
			if(statusIsFinal(lseobdlItem)){
				for (int ai=0; ai<lseobdlavailVct.size(); ai++){//no check on availtype to get here
					EntityItem avail = (EntityItem)lseobdlavailVct.elementAt(ai);
					String availtypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");

					addDebug("doLSEOBDLSectionOne: specbid=no annchks "+avail.getKey()+" type: "+availtypeFlag);	
					if(statusIsFinal(avail)){
						Vector lseoannVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
						Vector lseoannFinalVct= PokUtils.getEntitiesWithMatchedAttr(lseoannVct, "ANNSTATUS", STATUS_FINAL);
						addDebug("doLSEOBDLSectionOne: annchks lseoannVct "+lseoannVct.size()+" lseoannFinalVct "+lseoannFinalVct.size());
						for (int i=0; i<lseoannFinalVct.size(); i++){
							EntityItem annItem = (EntityItem)lseoannVct.elementAt(i);
							String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
							addDebug("doLSEOBDLSectionOne: annchks final "+annItem.getKey()+" type "+anntype);
							//111.000		IF		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)			
							//112.000		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)			
							//112.200		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)			
							//112.220		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)			
							if(ANNTYPE_NEW.equals(anntype)){
								addDebug("doLSEOBDLSectionOne: annchks "+annItem.getKey()+" is Final and New");
								//112.24		SET			ANNOUNCEMENT				WWPRTABRSTATUS		&ABRWAITODS2 
								setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValueForItem(annItem,"WWPRTABRSTATUS"),annItem);	
								//113.00	END	111.00
								//114.00		IF		LSEOBUNDLEAVAIL-d	AVAIL	AVAILTYPE	=	"Planned Availability" (146)			
								//115.00		AND		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
								//116.00		AND			ANNOUNCEMENT	ANNTYPE	=	"New" (19)	 
								//116.20		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)			
								//116.22		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)			
								if(PLANNEDAVAIL.equals(availtypeFlag)){
									//116.24		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
									setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(annItem,"QSMRPTABRSTATUS"),annItem);
								}
								//117.00	END	114.00	
							}// end new ann	
							//118.00		IF		LSEOBUNDLEAVAIL-d	AVAIL	AVAILTYPE	=	"Last Order" (149)			
							//119.00		AND		LSEOBUNDLEAVAIL-d: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)			
							//120.00		AND			ANNOUNCEMENT	ANNTYPE	=	"End Of Life - Withdrawal from mktg" (14)	QSMRPTABRSTATUS 		&QSMWAITODS 
							//120.02		AND			LSEOBUNDLE	STATUS	=	"Final" (0020)			
							//120.04		AND		LSEOBUNDLEAVAIL-d:	AVAIL	STATUS	=	"Final" (0020)			
							if(ANNTYPE_EOL.equals(anntype)){
								addDebug("doLSEOBDLSectionOne: annchks "+annItem.getKey()+" is Final and EOL");
								if(LASTORDERAVAIL.equals(availtypeFlag)){
									//120.06		SET			ANNOUNCEMENT				QSMRPTABRSTATUS 		&QSMWAITODS 
									setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(annItem,"QSMRPTABRSTATUS"),annItem);
								}
							}
							//121.00	END	118.00	
						}
						lseoannFinalVct.clear();
						lseoannVct.clear();
					}
				}
			}
			
			String bdlpubdate = PokUtils.getAttributeValue(lseobdlItem, "BUNDLPUBDATEMTRGT","", null, false);
			addDebug("doLSEOBDLSectionOne: bdlchks "+lseobdlItem.getKey()+" bdlpubdate "+bdlpubdate);
			//Add 2011-10-20		121.242	IF			LSEOBUNDLE	BUNDLPUBDATEMTRGT	>	"2010-03-01" | Empty
			//Add 2011-10-20		121.522	IF			LSEOBUNDLE	BUNDLPUBDATEMTRGT	>	"2010-03-01" | Empty
			if(bdlpubdate==null ||  bdlpubdate.compareTo(OLD_DATA_ANNDATE)>0){	
				for (int ai=0; ai<lseobdlavailVct.size(); ai++){//no check on availtype to get here
					EntityItem avail = (EntityItem)lseobdlavailVct.elementAt(ai);
					String availtypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILTYPE");
					String availAnntypeFlag = PokUtils.getAttributeFlagValue(avail, "AVAILANNTYPE");
					if (availAnntypeFlag==null){
						availAnntypeFlag = AVAILANNTYPE_RFA; // if not set, default to RFA
					}

					addDebug("doLSEOBDLSectionOne: specbid=no bdlchks "+avail.getKey()+" type: "+availtypeFlag+
							" availAnntypeFlag "+availAnntypeFlag);				
					
//					if(!AVAILANNTYPE_RFA.equals(availAnntypeFlag)){
//					20121210 Delete		121.250		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)					
//					20121210 Delete		121.260		OR		A: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020)					
//							121.270		SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED		
//					20121210 Delete		121.271		END	121.250	
						if(statusIsFinal(lseobdlItem) && statusIsFinal(avail)){
							//121.270	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
							setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", 
									getQueuedValueForItem(lseobdlItem,"ADSABRSTATUS"),lseobdlItem);
							break;
						}
//						20121210 Delete		121.550		IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)					
//						20121210 Delete		121.560		OR		A: AVAILANNA	ANNOUNCEMENT	ANNSTATUS	=	"Final" (0020) | "Ready for Review" (0040)					
//								121.580		SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR			
//						20121210 Delete		121.582		END	121.550			
						if(statusIsRFR(lseobdlItem) && statusIsRFRorFinal(avail)){	
							//121.580	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
							doRFR_ADSXML(lseobdlItem);
							break;
						}
						//121.600	END	121.500
//					}

					//121.56	R1.0	OR		A: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)		
//					Vector lseoannVct= PokUtils.getAllLinkedEntities(avail, "AVAILANNA", "ANNOUNCEMENT");
//					for (int i=0; i<lseoannVct.size(); i++){
//						EntityItem annItem = (EntityItem)lseoannVct.elementAt(i);
//						String anntype = getAttributeFlagEnabledValue(annItem, "ANNTYPE");
//						addDebug("doLSEOBDLSectionOne: bdlchks "+annItem.getKey()+" type "+anntype);			
//						if (statusIsFinal(annItem, "ANNSTATUS")){ 
//							if(statusIsFinal(lseobdlItem) && 
//									statusIsFinal(avail)){					
//								//121.20	IF	A	LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020)					
//								//121.24	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)			
//								//121.26	R1.0	OR		A: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020)	
//
//								//121.27	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
//								setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", 
//										getQueuedValueForItem(lseobdlItem,"ADSABRSTATUS"),lseobdlItem);
//								break;
//								//121.28	END	121.20	
//							}	// end bundle and avail are final
//						}// end ann is final
//						if(this.statusIsRFRorFinal(annItem,"ANNSTATUS")){
//							addDebug("doLSEOBDLSectionOne: bdlchks "+annItem.getKey()+" is RFR or final");
//
//							if (statusIsRFR(lseobdlItem) && statusIsRFRorFinal(avail)){								
//								//121.50	R1.0	IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
//								//121.52	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
//								//121.54	R1.0	AND		LSEOBUNDLEAVAIL-d	AVAIL	STATUS	=	"Final" (0020) | "Ready for Review" (0040)			
//								//121.55	R1.0	IF			AVAIL	AVAILANNTYPE	<>	"RFA" (RFA)
//								//121.56	R1.0	OR		A: AVAILANNA	ANNOUNCEMENT	STATUS	=	"Final" (0020) | "Ready for Review" (0040)
//								//121.58	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
//								doRFR_ADSXML(lseobdlItem);
//								//121.60	R1.0	END	121.50	
//								break;
//							}
//						} // end ann is rfr
//					} //end ann loop
//					lseoannVct.clear();
				} // end avail loop
				lseobdlavailVct.clear();	
			}else{
				addDebug("doLSEOBDLSectionOne: "+lseobdlItem.getKey()+" is olddata "+bdlpubdate);
				// pubdate is before 2010-03-01
				if(statusIsRFR(lseobdlItem)){
					//Add 2011-10-20		121.586	ELSE	121.522							
					//Add 2011-10-20		121.588	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
					doRFR_ADSXML(lseobdlItem); // this checks lifecycle
					//Add 2011-10-20		121.590	END	121.522
				}
				
				if(statusIsFinal(lseobdlItem)){
					//Add 2011-10-20		121.273	ELSE	121.242								
					//	Add 2011-10-20		121.274	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEED
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", 
								getQueuedValueForItem(lseobdlItem,"ADSABRSTATUS"),lseobdlItem);
					//Add 2011-10-20		121.275	END	121.242	
				}
			}
		}else{ // lseobdl specbid = yes
			//122.00		ELSE	110.00		LSEOBUNDLE				WWPRTABRSTATUS		&ABRWAITODS2 
			setFlagValue(m_elist.getProfile(),"WWPRTABRSTATUS", getQueuedValueForItem(lseobdlItem,"WWPRTABRSTATUS"),lseobdlItem);
			//123.00	SET			LSEOBUNDLE				QSMRPTABRSTATUS 		&QSMWAITODS 
			setFlagValue(m_elist.getProfile(),"QSMRPTABRSTATUS", getQueuedValueForItem(lseobdlItem,"QSMRPTABRSTATUS"),lseobdlItem);

			if(this.statusIsRFR(lseobdlItem)){
				//20130904 Delete 123.18		IF			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
				//123.19		IF			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)			
				//123.20	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR	
				doRFR_ADSXML(lseobdlItem);
			}else if (this.statusIsFinal(lseobdlItem)){
				//123.22		ELSE	123.18								
				//123.23		IF			LSEOBUNDLE	STATUS	=	"Final"			
				//123.24	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS		&ADSFEED
				setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", 
						getQueuedValueForItem(lseobdlItem,"ADSABRSTATUS"),lseobdlItem);
				//123.26		END	123.18	
			}
			//124.00		END	110.00			
		}
		//125.00		END	106.00	Section One							
	}

//	------------------------------------------------------
//	get check level based on status
	protected static int getCheck_W_E_E(String statusFlag){
		return (STATUS_DRAFT.equals(statusFlag)?CHECKLEVEL_W:CHECKLEVEL_E);
	}
	protected static int getCheck_W_RE_RE(String statusFlag){
		return (STATUS_DRAFT.equals(statusFlag)?CHECKLEVEL_W:CHECKLEVEL_RE);
	}
	protected static int getCheck_RE_RE_E(String statusFlag){
		return (STATUS_R4REVIEW.equals(statusFlag)?CHECKLEVEL_E:CHECKLEVEL_RE);
	}
	protected static int getCheck_RW_RE_RE(String statusFlag){
		return (STATUS_DRAFT.equals(statusFlag)?CHECKLEVEL_RW:CHECKLEVEL_RE);
	}
	protected static int getCheck_W_W_E(String statusFlag){
		return (STATUS_R4REVIEW.equals(statusFlag)?CHECKLEVEL_E:CHECKLEVEL_W);
	}
	protected static int getCheck_RW_RW_RE(String statusFlag){
		return (STATUS_R4REVIEW.equals(statusFlag)?CHECKLEVEL_RE:CHECKLEVEL_RW);
	}
	protected static int getCheck_W_RW_RE(String statusFlag){
		int checklvlWRWRE = 0;
		if (STATUS_DRAFT.equals(statusFlag)){
			checklvlWRWRE=CHECKLEVEL_W;
		}else if (STATUS_CHGREQ.equals(statusFlag)){
			checklvlWRWRE=CHECKLEVEL_RW; 			
		}else if (STATUS_R4REVIEW.equals(statusFlag)){
			checklvlWRWRE=CHECKLEVEL_RE; 			
		}
		return checklvlWRWRE;
	}

	protected static int doCheck_W_N_N(String statusFlag){
		if (!STATUS_DRAFT.equals(statusFlag)){
			return CHECKLEVEL_NOOP;
		}
		return CHECKLEVEL_W;
	}

	protected static int doCheck_N_W_E(String statusFlag){
		if (STATUS_R4REVIEW.equals(statusFlag)){
			return CHECKLEVEL_E;
		}
		if (STATUS_CHGREQ.equals(statusFlag)){
			return CHECKLEVEL_W;
		}
		return CHECKLEVEL_NOOP;
	}
	protected static int doCheck_N_RW_RE(String statusFlag){
		if (STATUS_R4REVIEW.equals(statusFlag)){
			return CHECKLEVEL_RE;
		}
		if (STATUS_CHGREQ.equals(statusFlag)){
			return CHECKLEVEL_RW;
		}
		return CHECKLEVEL_NOOP;
	}

	/*
	 * 
E.	Old Data Criteria

If ANNDATE <= �2010-03-01�, then a severity of �E� is reduced to �W�.
If ANNDATE is empty (aka Null), then the severity is not reduced.
1.	*1
MODEL.ANNDATE is used for SWFEATURE, PRODSTRUCT, and SWPRODSTRUCT. MODEL is the child entity type for these relators and SWFEATURE is the parent entity type for SWPRODSTRUCT.

Note: if SWFEATURE is not related to a MODEL, then the checks are not applicable since there cannot be any AVAILs which would be related to SWPRODSTRUCT.
2.	*2
ANNDATE on the entity itself is used for MODEL, FCTRANSACTION, and MODELCONVERT.

	 */
	private boolean sevReducedMsgDisplayed = false;
	protected int getCheckLevel(int checklvl, EntityItem item, String attrcode){
		if(checklvl==CHECKLEVEL_E || checklvl==CHECKLEVEL_RE){
			String annDate = PokUtils.getAttributeValue(item, attrcode, "", "", false);
			if(annDate.length()>0 && OLD_DATA_ANNDATE.compareTo(annDate)>=0){
				addDebug("getCheckLevel: OVERRIDING severity for "+item.getKey()+" "+attrcode+": "+annDate+
						" it is not greater than "+OLD_DATA_ANNDATE);
				if (!sevReducedMsgDisplayed){
					sevReducedMsgDisplayed = true;
					//SEV_REDUCED = Severity for some checks will be reduced to Warning because {0} {1} is before {2}
					try {
						args[0] = getLD_NDN(item);
					} catch (Exception e) {
						e.printStackTrace();
						args[0] ="";
					}
					args[1] =getLD_Value(item, attrcode);
					args[2] = OLD_DATA_ANNDATE;
					addMessage("","SEV_REDUCED",args);
				}
				if(checklvl==CHECKLEVEL_RE){
					return CHECKLEVEL_RW;
				}
				return CHECKLEVEL_W;
			}
		}
		return checklvl;
	}

	/**
	 * check to see if this entity was announced prior to the OLD_DATA_ANNDATE
	 * @param item
	 * @param attrcode
	 * @return
	 */
	protected boolean isOldData(EntityItem item, String attrcode){
		boolean olddata = false;
		String annDate = PokUtils.getAttributeValue(item, attrcode, "", "", false);
		addDebug("isOldData: "+item.getKey()+" "+attrcode+": "+annDate);
		if(annDate.length()>0 && OLD_DATA_ANNDATE.compareTo(annDate)>=0){
			addDebug("isOldData: "+attrcode+" is not greater than "+OLD_DATA_ANNDATE);
			olddata = true;
		}
		return olddata;
	}
	/**
	 * The following only considers the AVAIL that is being looked at as follows:
3.	*3
If GENAREASELECTION = �Worldwide� (1999) and EFFECTIVEDATE <= �2010-03-01�, then a severity of �E� is reduced to �W�.

e.g. MODEL tab Key = 56.00
The AVAIL found via Key 52.00 is checked based on the preceding.

	 * @param checklvl
	 * @param avail
	 * @return
	 */
	protected int getAvailCheckLevel(int checklvl, EntityItem avail){
		String attrcode = "EFFECTIVEDATE";
		Set genareaSet = new HashSet();
		genareaSet.add(GENAREASELECTION_WW);

		if((checklvl==CHECKLEVEL_E || checklvl==CHECKLEVEL_RE) &&
				PokUtils.contains(avail, "GENAREASELECTION", genareaSet)){
			String annDate = PokUtils.getAttributeValue(avail, attrcode, "", "", false);
			if(annDate.length()>0 && OLD_DATA_ANNDATE.compareTo(annDate)>=0){
				addDebug("getAvailCheckLevel: OVERRIDING severity for "+avail.getKey()+" "+attrcode+": "+annDate+
						" it is not greater than "+OLD_DATA_ANNDATE+" and genarea has ww ");

				//AVAIL_SEV_REDUCED = Severity for {0} date check will be reduced to Warning because {1} is before {2} and {3}
				try {
					args[0] = getLD_NDN(avail);
				} catch (Exception e) {
					e.printStackTrace();
					args[0] ="";
				}
				args[1] =getLD_Value(avail, attrcode);
				args[2] = OLD_DATA_ANNDATE;
				args[3] =getLD_Value(avail, "GENAREASELECTION");
				addMessage("","AVAIL_SEV_REDUCED",args);

				genareaSet.clear();

				if(checklvl==CHECKLEVEL_RE){
					return CHECKLEVEL_RW;
				}

				return CHECKLEVEL_W;
			}
		}
		genareaSet.clear();
		return checklvl;
	}   

	/**
	
53.24	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
53.26	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
53.28	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR

	 * @param item
	 */
	protected void doRFR_ADSXML(EntityItem item) {
		doRFR_ADSXML(item, "STATUS");
	}
	

	/**
	 * @param item
	 * @param statusAttr
	 */
	protected void doRFR_ADSXML(EntityItem item, String statusAttr) {
		String lifecycle = PokUtils.getAttributeFlagValue(item, "LIFECYCLE");
		addDebug("doRFR_ADSXML: "+item.getKey()+" lifecycle "+lifecycle);
		if (lifecycle==null || lifecycle.length()==0){ 
			lifecycle = LIFECYCLE_Plan;
		}
		
		String entitytype = item.getEntityType();
		addDebug("doRFR_ADSXML: "+CHECKFIRSTFINALVEC + " //	item.getEntityType() = " + item.getEntityType());
		if (statusIsRFR(item,statusAttr)){	
			if("MODEL".equals(entitytype)){
				addDebug("doRFR_ADSXML: MODEL");
				doRFR_MODADSXML(item);
			}else		
			// IF LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
			if ((CHECKFIRSTFINALVEC.contains(entitytype)) || (LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
					LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
				
					//	SET ADSABRSTATUS	&ADSFEEDRFR
					addDebug("doRFR_ADSXML: //	SET ADSABRSTATUS	&ADSFEEDRFR");
					setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(item,"ADSABRSTATUS"),item);
				
			}
		}
		
	}
	
	protected void doRFR_MODADSXML(EntityItem item){
		String lifecycle = PokUtils.getAttributeFlagValue(item, "LIFECYCLE");
		addDebug("doRFR_MODADSXML: "+item.getKey()+" lifecycle "+lifecycle);
		if (lifecycle==null || lifecycle.length()==0){ 
			lifecycle = LIFECYCLE_Plan;
		}
		
		// IF LIFECYCLE	<>	"Develop" (LF02)  | "Plan" (LF01)
		if (!(LIFECYCLE_Plan.equals(lifecycle) ||  // first time moving to RFR
				LIFECYCLE_Develop.equals(lifecycle))){ // been RFR before
//			SET ADSABRSTATUS	&ADSFEEDRFR
			addDebug("doRFR_MODADSXML: set ADSABRSTATUS	&ADSFEEDRFR");
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(item,"ADSABRSTATUS"),item);
		}else{
			addDebug("doRFR_MODADSXML:setRFRSinceFirstRFR");
			setRFRSinceFirstRFR(item);
		}
	}
	
	/**
	 * if adsabrstatus != 0030/XMLRFR, will queue the adsabrstatus as firstrfrqueue, otherwise, rfrqueue
	 * don't check if it is first rfr
	 * @param item
	 */
	protected void setRFRSinceFirstRFR(EntityItem item){
		String adsabrstatus = PokUtils.getAttributeFlagValue(item, "ADSABRSTATUS");
		addDebug("setRFRSinceFirstRFR: adsabrstatus " + item.getKey() + " " + adsabrstatus);
		boolean isXMLfirst = !(ABR_PASSED.equals(adsabrstatus) || "XMLRFR".equals(adsabrstatus));
	
		if(isXMLfirst){//if first queue as rfr - set ADSABRSTATUS	&ADSFEEDRFRFIRST
			addDebug("setRFRSinceFirstRFR: getRFRFIRSTQueuedValueForItem");
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRFIRSTQueuedValueForItem(item,"ADSABRSTATUS"),item);
		}else{
			//	SET ADSABRSTATUS	&ADSFEEDRFR
			addDebug("setRFRSinceFirstRFR: SET ADSABRSTATUS	&ADSFEEDRFR");
			setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getRFRQueuedValueForItem(item,"ADSABRSTATUS"),item);
		}
	}
	
	/**
	 * This is similar to a SET statement. 
	 * It will check an Attribute for a value since the first (earliest) value of “Status” (STATUS) = “Final” (0020)

		e.g.
		Col	Content
		E	SetSinceFinal
		F	 
		G	 
		H	MODEL
		I	ADSABRSTATUS
		J	=
		K	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)
		L	ADSABRSTATUS
		M	 
		N	&ADSFEED
		
		This is interpreted as: If the MODEL XML ABR (ADSABRSTATUS) ever successfully sent XML 
		since it first went “Final”, then set ADSABRSTATUS = “&ADSFEED”
	 * @param item
	 * @return
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareRequestException 
	 */
	protected boolean isSendSinceFirstFinal(EntityItem item) throws MiddlewareRequestException, MiddlewareException, SQLException{
		String firstfinaldate=null;
		//get attribute history
		EANAttribute att = item.getAttribute("STATUS");
		if (att != null)	{
			AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), att);
			addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for "+item.getKey()+" Attribute: "+"STATUS");
			if (histGrp.getChangeHistoryItemCount()>0){
				for (int i= 0;i < histGrp.getChangeHistoryItemCount();i++){
					AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
					addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
							" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
					if (chi.getFlagCode().equals(STATUS_FINAL)){//get the first final history
						firstfinaldate = chi.getChangeDate();
						break;
					}
				}
			}
		}
		
		EANAttribute adsatt = item.getAttribute("ADSABRSTATUS");
		if (adsatt != null && firstfinaldate!=null)	{//get history for ADSABRSTATUS
			AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), adsatt);
			addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for "+item.getKey()+" Attribute: "+"ADSABRSTATUS - count:" + histGrp.getChangeHistoryItemCount());
			if (histGrp.getChangeHistoryItemCount()>0){
				for (int i= histGrp.getChangeHistoryItemCount()-1;i>=0;i--){
					AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
					addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
							" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
					if (chi.getFlagCode().equals(ABR_PASSED) || chi.getFlagCode().equals("XMLRFR")){// only need to check the biggest valfrom
						addDebug("isSendSinceFirstFinal: Match:AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
								" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
						if(chi.getChangeDate().compareTo(firstfinaldate) > 0){
							return true;
						}
						break;
					}
				}
			}
		}
		
		return false;
	}
	
	protected void setSinceFirstFinal(EntityItem item, String attr) throws MiddlewareRequestException, MiddlewareException, SQLException{
		if(attr.equals("ADSABRSTATUS") && !isSendSinceFirstFinal(item)){
			addDebug("setSinceFirstFinal: getFINALFIRSTQueuedValueForItem " + "isSendSinceFirstFinal(item): false");
			setFlagValue(m_elist.getProfile(),attr, getFINALFIRSTQueuedValueForItem(item,attr), item);
		}else{
			addDebug("setSinceFirstFinal: getQueuedValueForItem");
			setFlagValue(m_elist.getProfile(),attr, getQueuedValueForItem(item,attr), item);
		}
	}

	protected void setRFCSinceFirstFinal(EntityItem item, String attr) throws MiddlewareRequestException, MiddlewareException, SQLException{
		if(attr.equals("RFCABRSTATUS") && !isSinceFirstFinal(item, attr)){
			addDebug("setSinceFirstFinal: getFINALFIRSTQueuedValueForItem " + "isSendSinceFirstFinal(item): false");
			setFlagValue(m_elist.getProfile(),attr, getQueuedValueForItem(item,attr), item);
		}else{
			addDebug("setSinceFirstFinal: getQueuedValueForItem");
			setFlagValue(m_elist.getProfile(),attr, getQueuedValueForItem(item,attr), item);
		}
	}
	private boolean isSinceFirstFinal(EntityItem item, String attr) throws MiddlewareRequestException, MiddlewareException, SQLException{
		String firstfinaldate=null;
		//get attribute history
		EANAttribute att = item.getAttribute("STATUS");
		if (att != null)	{
			AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), att);
			addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for "+item.getKey()+" Attribute: "+"STATUS");
			if (histGrp.getChangeHistoryItemCount()>0){
				for (int i= 0;i < histGrp.getChangeHistoryItemCount();i++){
					AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
					addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
							" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
					if (chi.getFlagCode().equals(STATUS_FINAL)){//get the first final history
						firstfinaldate = chi.getChangeDate();
						break;
					}
				}
			}
		}
		
		EANAttribute attribute = item.getAttribute(attr);
		if (attribute != null && firstfinaldate!=null)	{//get history for attr
			AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), attribute);
			addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for "+item.getKey()+" Attribute: "+ attr + " - count:" + histGrp.getChangeHistoryItemCount());
			if (histGrp.getChangeHistoryItemCount()>0){
				for (int i= histGrp.getChangeHistoryItemCount()-1;i>=0;i--){
					AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
					addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
							" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
					if (chi.getFlagCode().equals(ABR_PASSED)){
						addDebug("isSendSinceFirstFinal: Match:AttrChangeHistoryItem["+i+"] chgDate: "+chi.getChangeDate()+" user: "+chi.getUser()+
								" isValid: "+chi.isValid()+" value: "+chi.get("ATTVAL",true)+" flagcode: "+chi.getFlagCode());
						if(chi.getChangeDate().compareTo(firstfinaldate) > 0){
							return true;
						}
						break;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Get the attrValue history count for attrCode
	 * @param item
	 * @param attrCode
	 * @param attrValue
	 * @return
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareRequestException 
	 */
	protected int getAttributeValueHistoryCount(EntityItem item, String attrCode, String attrValue) throws MiddlewareRequestException, MiddlewareException, SQLException {
		int attValueCount = 0;
		EANAttribute att = item.getAttribute(attrCode);
		if (att != null) {
			AttributeChangeHistoryGroup histGrp = m_db.getAttributeChangeHistoryGroup(m_elist.getProfile(), att);
			addDebug("getAttributeValueHistoryCount: ChangeHistoryGroup for " + item.getKey() + " Attribute: " + attrCode);
			if (histGrp.getChangeHistoryItemCount() > 0) {
				for (int i = 0; i < histGrp.getChangeHistoryItemCount(); i++) {
					AttributeChangeHistoryItem chi = (AttributeChangeHistoryItem)histGrp.getChangeHistoryItem(i);
//					addDebug("getAttributeValueHistoryCount: AttrChangeHistoryItem["+i+"] chgDate: " + chi.getChangeDate() + " user: " + chi.getUser() +
//							" isValid: " + chi.isValid() + " value: " + chi.get("ATTVAL", true) + " flagcode: " + chi.getFlagCode());
					if (chi.getFlagCode().equals(attrValue)){
						attValueCount++;
					}
				}
			}		
		}
		addDebug("getAttributeValueHistoryCount: attValueCount " + attValueCount + " for Attribute: " + attrCode + " value " + attrValue);
		return attValueCount;
	}
	
	/**
	49.20	R1.0	IF		LSEOAVAIL-u	LSEO	STATUS	=	"Ready for Review" (0040)			
	49.22	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	49.24	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	49.26	R1.0	END	49.20
	 * @param entitytype
	 */
	protected void doRFR_ADSXML(String entitytype){
		EntityGroup eg = m_elist.getEntityGroup(entitytype);
		for (int i = 0; i<eg.getEntityItemCount(); i++){
			EntityItem item = eg.getEntityItem(i);
			//IF		STATUS	=	"Ready for Review" (0040)			
			//AND			LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
			//SET			ADSABRSTATUS	&ADSFEEDRFR	
			doRFR_ADSXML(item);
		}
	}
	/**
	 * check status of related entity and queue ADS based on that and root status
	 * 
	 * @param entitytype
	 * @param rootIsFinal
	 * @throws SQLException 
	 * @throws MiddlewareException 
	 * @throws MiddlewareRequestException 
	 */
	protected void checkRelatedItems(String entitytype, boolean rootIsFinal) throws MiddlewareRequestException, MiddlewareException, SQLException{
		EntityGroup eg = m_elist.getEntityGroup(entitytype);				

		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem item = eg.getEntityItem(i);
			if (statusIsFinal(item)){		
				//53.30	R1.0	ELSE	53.22							
				//53.32	R1.0	IF			FB	STATUS	=	"Final" (0020)		
				//53.34	R1.0	AND			LSEOBUNDLE	STATUS	=	"Final" (0020)		
				//53.36	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	
				if(rootIsFinal){
//					20130904b Change	Columns E, I, J, K	130.380		SetSinceFinal			MODEL	ADSABRSTATUS	=	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEED		
//					20130904b Add		46.242		SetSinceFinal			MODEL	ADSABRSTATUS	<>	"Passed" (0030) | "Passed Resend RFR" (XMLRFR)	ADSABRSTATUS		&ADSFEEDFINALFIRST		
					if("MODEL".equals(entitytype)){
						setSinceFirstFinal(item, "ADSABRSTATUS");
					}
				}
				//53.38	R1.0	END	53.22	
			}else{				
				//53.22	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)		
				//53.24	R1.0	AND			LSEOBUNDLE	STATUS	=	"Ready for Review" (0040)		
				//53.26	R1.0	AND			LSEOBUNDLE	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)		
				//53.28	R1.0	SET			LSEOBUNDLE				ADSABRSTATUS	&ADSFEEDRFR
				doRFR_ADSXML(item);		
			}
		}	
	}

	/**
	 * 
FF.	AnyIn
Syntax:  [Path] EntityType AttributeCode AnyIn [EntityType2] AttributeCode2

If any value in EntityType AttributeCode is in EntityType2 AttributeCode2, then the test is True

Example:  IF LSEO COUNTRYLIST AnyIn FB COUNTRYLIST THEN

	 * @param item1
	 * @param attr1
	 * @param checklvl1
	 * @param item2
	 * @param attr2
	 * @param checklvl2
	 * @return
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected boolean anyIn(EntityItem item1, String attr1, int checklvl1,EntityItem item2, 
			String attr2, int checklvl2) throws SQLException, MiddlewareException{
		boolean intersect = false;
		// are any item1.attr1 values in item2.attr2 values?
       	ArrayList list1 = new ArrayList();
     	ArrayList list2 = new ArrayList();
		getAttributeAsList(item1, list1, attr1, checklvl1);
		getAttributeAsList(item2, list2, attr2, checklvl2);
		addDebug("anyIn "+item1.getKey()+" list1: "+list1+" "+item2.getKey()+" list2: "+list2);
		list1.retainAll(list2);
		addDebug("anyIn after retainall list1: "+list1);
		intersect = list1.size()>0;
		list1.clear();
		list2.clear();
		return intersect;
	}
	
	/**
	 * 
	53.60	R1.0			WWSEOFB-u	WWSEO						
	53.62	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
	53.64	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
	53.65	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
	53.66	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
	53.67	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
	53.68	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
	53.70	R1.0	ELSE	53.62								
	53.72	R1.0	IF			FB	STATUS	=	"Final" (0020)			
	53.74	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
	53.75	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
	53.76	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
	53.77	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
	53.78	R1.0	END	53.72								
	53.79	R1.0	END	53.62
	---------------------------
	130.42	R1.0			WWSEOMM-u	WWSEO						
130.44	R1.0	IF			MM	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
130.46	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
		AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
130.48	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)			
		AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
130.50	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
130.52	R1.0	ELSE	130.44								
130.54	R1.0	IF			MM	STATUS	=	"Final" (0020)			
130.56	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
130.57	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)			
130.58	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
130.59	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
130.60	R1.0	END	130.54								
130.61	R1.0	END	130.44								

	 * @param rootIsFinal
	 * @param rootItem
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkWwseoLseo(boolean rootIsFinal, EntityItem rootItem) 
	throws SQLException, MiddlewareException {
		EntityGroup eg = m_elist.getEntityGroup("WWSEO");				

		for (int i=0; i<eg.getEntityItemCount(); i++){
			EntityItem wwseoitem = eg.getEntityItem(i);
			Vector lseoVct = PokUtils.getAllLinkedEntities(wwseoitem, "WWSEOLSEO", "LSEO");
			if (statusIsFinal(wwseoitem)){		
				//53.70	R1.0	ELSE	53.62
				//53.72	R1.0	IF			FB	STATUS	=	"Final" (0020)			
				//53.74	R1.0	AND			WWSEO	STATUS	=	"Final" (0020)			
				if(rootIsFinal){
					for(int l=0; l<lseoVct.size(); l++){
						EntityItem lseoitem = (EntityItem)lseoVct.elementAt(l);
						//53.75	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Final" (0020)		
						if (statusIsFinal(lseoitem)){
							//53.76	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST			
							if(anyIn(lseoitem, "COUNTRYLIST", CHECKLEVEL_NOOP,rootItem,	"COUNTRYLIST", CHECKLEVEL_NOOP)){
								//53.77	R1.0	SET			LSEO				ADSABRSTATUS		&ADSFEED
								setFlagValue(m_elist.getProfile(),"ADSABRSTATUS", getQueuedValueForItem(lseoitem,"ADSABRSTATUS"),lseoitem);
							}
						}
					}
				}
				//53.78	R1.0	END	53.72
			}else if (statusIsRFR(wwseoitem)){					
				//53.62	R1.0	IF			FB	STATUS	=	"Ready for Review" (0040) | "Final" (0020)			
				//53.64	R1.0	AND			WWSEO	STATUS	=	"Ready for Review" (0040)			
				for(int l=0; l<lseoVct.size(); l++){
					EntityItem lseoitem = (EntityItem)lseoVct.elementAt(l);
					//	53.67	R1.0	AND			LSEO	COUNTRYLIST	AnyIn	FB.COUNTRYLIST				
					if(anyIn(lseoitem, "COUNTRYLIST", CHECKLEVEL_NOOP,rootItem,	"COUNTRYLIST", CHECKLEVEL_NOOP)){
						//53.65	R1.0	AND		WWSEOLSEO-d	LSEO	STATUS	=	"Ready for Review" (0040)			
						//53.66	R1.0	AND			LSEO	LIFECYCLE	=	"Develop" (LF02)  | "Plan" (LF01)
						//53.68	R1.0	SET			LSEO				ADSABRSTATUS	&ADSFEEDRFR	
						doRFR_ADSXML(lseoitem);	
					}
				}
			}
		}	
	}	
	
	/**
	 * 
BB.	OneValidOverTime

This function checks that there is at least one valid child during the period that the parent is valid.

Column F:  �OneValidOverTime� or �ParentFrom� or �ParentTo� or �ChildFrom� or �ChildTo�
Column G: EntityType or Relator Type if Column F is not �OneValidOverTime�
Column H: Attribute Code that is a date. There are two rows for the Parent and two rows for the Child 
corresponding to �from� and �to� dates.

Example follows:
F	G	H
OneValidOverTime		
ParentFrom	CHRGCOMP	EFFECTIVEDATE
ParentTo	CHRGCOMP	ENDDATE
ChildFrom	CHRGCOMPCVM-d: CVM	EFFECTIVEDATE
ChildTo	CHRGCOMPCVM-d: CVM	ENDDATE

The CHRGCOMP is valid from CHRGCOMP.EFFECTIVEDATE to CHRGCOMP.ENDDATE. There must be at least one CVM 
that is valid throughout this period of time. The CVMs may overlap; however, there cannot be gaps in time. A 
CVM EFFECTIVEDATE must be at least as early as the CHRGCOMP EFFECTIVEDATE and a CVM must have an ENDDATE 
at least as late as the CHRGCOMP ENDDATE.

	 * @param parent
	 * @param path2child
	 * @param checklvl
	 * @param datesOnRelator
	 */
	protected void oneValidOverTime(EntityItem parent, String[] path2child, int checklvl){
		StringBuffer chksSb = new StringBuffer(parent.getEntityGroup().getLongDescription());
		Vector childVct = new Vector();
		childVct.add(parent);//load starting point
		for(int i=0; i<path2child.length; i+=2){
			String linkType = path2child[i];
			String childtype = path2child[i+1];
			// keep walking down the relators
			childVct = PokUtils.getAllLinkedEntities(childVct, linkType, childtype);
			addDebug("oneValidOverTime entered for "+parent.getKey()+" linkType "+linkType+
					" childtype "+childtype+" childVct "+childVct.size());
			chksSb.append(" and "+m_elist.getEntityGroup(childtype).getLongDescription());
		}

	 	addHeading(3,chksSb.toString()+" validity checks:");
	 	
		if (childVct.size()==0){
			// caller already output a msg if no children exist
			return;
		}

		String parentEffFrom = PokUtils.getAttributeValue(parent, "EFFECTIVEDATE", "", EPOCH_DATE, false);// this has EXIST rule
		String parentEffTo = PokUtils.getAttributeValue(parent, "ENDDATE", "", FOREVER_DATE, false); 
		addDebug("oneValidOverTime parentEffFrom "+parentEffFrom+" parentEffTo "+parentEffTo);
		// sort children by efffrom
		Collections.sort(childVct, new AttrComparator("EFFECTIVEDATE"));

		// look at first one
		EntityItem child = (EntityItem)childVct.firstElement();
		String childEffFrom = PokUtils.getAttributeValue(child, "EFFECTIVEDATE", "", EPOCH_DATE, false);// has EXIST rule
		String prevEffTo = PokUtils.getAttributeValue(child, "ENDDATE", "", FOREVER_DATE, false);
		addDebug("oneValidOverTime first "+child.getKey()+" childEffFrom "+childEffFrom+" prevEffTo "+prevEffTo);
		if(childEffFrom.compareTo(parentEffFrom)>0){
			//must have at least one valid {LD: CVM} during the time that the {LD: CHRGCOMP} is valid.
			//must have at least one valid {LD: CVMSPEC} during the time that the {LD: CVM is valid.
			//INVALID_CHILD_ERR= must have at least one valid {0} during the time that the {1} is valid.
			args[0]= child.getEntityGroup().getLongDescription();
			args[1]= parent.getEntityGroup().getLongDescription();
			createMessage(CHECKLEVEL_E,"INVALID_CHILD_ERR",args);
			childVct.clear();
			return;
		}
		// look at last one
		child = (EntityItem)childVct.lastElement();
		String childEffTo = PokUtils.getAttributeValue(child, "ENDDATE", "", FOREVER_DATE, false);
		addDebug("oneValidOverTime last "+child.getKey()+" childEffTo "+childEffTo);
		if(childEffTo.compareTo(parentEffTo)<0){
			//INVALID_CHILD_ERR= must have at least one valid {0} during the time that the {1} is valid.
			args[0]= child.getEntityGroup().getLongDescription();
			args[1]= parent.getEntityGroup().getLongDescription();
			createMessage(CHECKLEVEL_E,"INVALID_CHILD_ERR",args);
			childVct.clear();
			return;
		}

		// look for gaps
		for (int i=1; i<childVct.size(); i++){
			child = (EntityItem)childVct.elementAt(i);
			String nextEffFrom = PokUtils.getAttributeValue(child, "EFFECTIVEDATE", "", EPOCH_DATE, false); // has EXIST rule
			String nextEffTo = PokUtils.getAttributeValue(child, "ENDDATE", "", FOREVER_DATE, false);
			addDebug("oneValidOverTime gaps "+child.getKey()+" prevEffTo "+prevEffTo+" nextEffFrom "+
					nextEffFrom+" nextEffTo "+nextEffTo);
			if(nextEffFrom.compareTo(prevEffTo)>0){
				//INVALID_CHILD_ERR= must have at least one valid {0} during the time that the {1} is valid.
				args[0]= child.getEntityGroup().getLongDescription();
				args[1]= parent.getEntityGroup().getLongDescription();
				createMessage(CHECKLEVEL_E,"INVALID_CHILD_ERR",args);
				childVct.clear();
				return;
			}
			prevEffTo= nextEffTo;
		}

		childVct.clear();
	}
	
	/**
	 * check unique coverage for specified entitytype linked to rootitem thru linkType
	 * 
	 * @param rootitem
	 * @param linkType
	 * @param entityType
	 * @param plaAvailVctA
	 * @param LOAvailVctC
	 * @param checklvl
	 * @param isRequired
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkUniqueCoverage(EntityItem rootitem, String linkType,
			String entityType, Vector plaAvailVctA, Vector LOAvailVctC, int checklvl, boolean isRequired) 
	throws SQLException, MiddlewareException
	{
		// get items to check
		Vector eiVct = PokUtils.getAllLinkedEntities(rootitem, linkType, entityType);
		EntityGroup eGrp = m_elist.getEntityGroup(entityType);
		if(linkType.equals(entityType)){
			eiVct.clear(); // cant have anything but do it anyway
			// MODELWARR has attributes on the relator, so just use the group
			for (int i=0; i<eGrp.getEntityItemCount(); i++){
				eiVct.add(eGrp.getEntityItem(i));
			}
		}
		addDebug("checkUniqueCoverage entered for "+entityType+" cnt "+eiVct.size());
		if (eiVct.size()==0){
			if(isRequired){
				//MINIMUM_ERR = must have at least one {0}
				args[0]=eGrp.getLongDescription();
				createMessage(checklvl,"MINIMUM_ERR",args);
			}
			return;
		}

		ArrayList allCtryList = new ArrayList(); // get all countries for all imgs/warrs, only use valid ones
		Hashtable ucTbl = new Hashtable();
		//sort by PUBFROM 
		for (int i=0; i<eiVct.size(); i++){
			EntityItem ei = (EntityItem)eiVct.elementAt(i);
			String pubfrom = PokUtils.getAttributeValue(ei, "PUBFROM", "", null, false);
			if (pubfrom ==null){
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0]=getLD_NDN(ei);
				args[1]=PokUtils.getAttributeDescription(eGrp, "PUBFROM", "PUBFROM");
				createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);
				continue;
			}

			CoverageData uc = new CoverageData(ei,checklvl);
			Iterator itr = uc.ctryList.iterator();
			while (itr.hasNext()) {
				String ctryflag = (String) itr.next();
				Vector tmp = (Vector)ucTbl.get(ctryflag);
				if (tmp==null){
					tmp = new Vector();
					ucTbl.put(ctryflag,tmp);
				}
				tmp.add(uc);
			}
			getCountriesAsList(ei, allCtryList,checklvl); // accumulate all the different countries
		}

		if(ucTbl.size()==0){
			return;
		}
		// sort each countries vectors using the pubfrom attr
		for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
			Collections.sort((Vector)ucTbl.get(e.nextElement()));
		}

		/*  By Country in ID 2, if there are multiple, check that they do not overlap and that they do not have gaps. 
        This may be done by ordering them in increasing values for PUBFROM. Then
        MIN(PUBFROM) = the first PUBFROM
        MAX(PUBTO) = the last PUBTO
		 */

		addDebug(entityType+"  allCtryList "+allCtryList);

		//88.00         COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST         RW  RE      
		//must have a {LD: IMG} for every country in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL} 
		//93.00         COUNTRYLIST "Contains aggregate E"  A: AVAIL    COUNTRYLIST     ?   RW  RE      
		//must have a {LD: WARR} for every country in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
		for (int i=0; i<plaAvailVctA.size(); i++){
			EntityItem mdlplaAvail = (EntityItem)plaAvailVctA.elementAt(i);
			ArrayList ctryList = new ArrayList();
			getCountriesAsList(mdlplaAvail, ctryList,checklvl);
			addDebug("checkUniqueCoverage plannedavail["+i+"] "+mdlplaAvail.getKey()+" ctryList "+ctryList);
			if(!allCtryList.containsAll(ctryList)){
				//MISSING_CTRY_ERR2 = must have a {0} for every Country in the {1} {2}, missing {3}
				args[0]=eGrp.getLongDescription();
				args[1]=rootitem.getEntityGroup().getLongDescription();
				args[2]=getLD_NDN(mdlplaAvail)+" "+
					PokUtils.getAttributeDescription(mdlplaAvail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
				//Note: identify the country that does not have an Image"	
				ArrayList ctryflagList = new ArrayList();
				getCountriesAsList(mdlplaAvail, ctryflagList,checklvl);
				ctryflagList.removeAll(allCtryList); // remove all matches
				args[3]=getUnmatchedDescriptions(mdlplaAvail, "COUNTRYLIST",ctryflagList);
				createMessage(checklvl,"MISSING_CTRY_ERR2",args);
				ctryflagList.clear();
			}

			//check by country
			String effdate = getAttrValueAndCheckLvl(mdlplaAvail, "EFFECTIVEDATE", checklvl);
			addDebug("checkUniqueCoverage plannedavail "+mdlplaAvail.getKey()+" EFFECTIVEDATE "+effdate);
			boolean isok = true;
			Iterator itr = ctryList.iterator();
			while (itr.hasNext() && isok) {
				String ctryflag = (String) itr.next();
				Vector tmpVct = (Vector)ucTbl.get(ctryflag); // get the IMG/WARR that have this country
				if (tmpVct!=null){
					CoverageData uc = (CoverageData)tmpVct.firstElement();
					String minPubfrom =uc.pubfrom;
					addDebug("checkUniqueCoverage "+uc.item.getKey()+" had minpubfrom "+minPubfrom+" for country "+ctryflag);
					//89            MIN(PUBFROM)    <=  A: AVAIL    EFFECTIVEDATE           RW  RE      
					//must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
					isok = checkDates(minPubfrom, effdate, DATE_LT_EQ); //date1<=date2
				}else{
					addDebug("checkUniqueCoverage: No "+entityType+" found for country "+ctryflag);
				}
			}

			if(!isok){
				//PUBFROM_ERR = must have a {0} with a {1} as early as or earlier than the {2} {3}
				args[0]=eGrp.getLongDescription();
				args[1]=PokUtils.getAttributeDescription(eGrp, "PUBFROM", "PUBFROM");
				args[2]=rootitem.getEntityGroup().getLongDescription();
				args[3]=getLD_NDN(mdlplaAvail);
				createMessage(checklvl,"PUBFROM_ERR",args);
			}
			ctryList.clear();
		}

		/*  By Country in ID 2, if there are multiple, check that they do not overlap and that they do not have gaps. 
        This may be done by ordering them in increasing values for PUBFROM. Then
        MIN(PUBFROM) = the first PUBFROM
        MAX(PUBTO) = the last PUBTO

        Check that there are no gaps in the date range by checking that Ith PUBTO = Ith+1 PUBFROM + 1. 
        The error text is found in the spreadsheet for ID 1.
		 */
		if (eGrp.getEntityItemCount()>1){
			HashSet badRangeSet = new HashSet();

			// look for overlap or gaps by country
			for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
				String flagCode = (String)e.nextElement();
				Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
				//addDebug("GAPTEST ctry "+flagCode+" ctryUcVct.size "+ctryUcVct.size());
				CoverageData prevUc = null;
				// look at all IMG/WARR for this country
				for (int u=0; u<ctryUcVct.size(); u++){
					CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
					if (u!=0){
						//addDebug("GAPTEST["+u+"] ctry "+flagCode+" \nprevUc "+prevUc+" \ncurrUc "+uc);
						Date pubToDate = Date.valueOf(prevUc.pubto);
						long pubto = pubToDate.getTime();
						long pubtoPlus1 = pubto + 24 * 60 * 60*1000; //pubto+ 1 day
						Date pubtoPlus1Date = new Date(pubtoPlus1);
						//addDebug("GAPTEST["+u+"] prev pubto "+prevUc.pubto+" pubtoPlus1Date "+pubtoPlus1Date+" cur pubfrom "+uc.pubfrom);
						if (!pubtoPlus1Date.toString().equals(uc.pubfrom)){
							if (!badRangeSet.contains(prevUc.item) ||
									!badRangeSet.contains(uc.item))
							{
								badRangeSet.add(prevUc.item);
								badRangeSet.add(uc.item);
								addDebug("checkUniqueCoverage output date range msg for "+prevUc.item.getKey()+
										" and "+uc.item.getKey());
								//  86.00   IMG     MODELIMG-d                              IMG 
								//  87.00           CoverageData      IMG             W   E       
								//  {LD: IMG} {NDN: IMG} have gaps in the data range or they overlap.
								// DATE_RANGE_ERR={0} and {1} have gaps in the date range or they overlap.
								args[0]=getLD_NDN(prevUc.item);
								args[1]=getLD_NDN(uc.item);
								createMessage(checklvl,"DATE_RANGE_ERR",args);
							}else{
								addDebug("checkUniqueCoverage already output date range msg for "+prevUc.item.getKey()+
										" and "+uc.item.getKey());
							}
						}
					}
					prevUc = uc;
				}
			}
			badRangeSet.clear();
		}// end has more than 1 item for gap tests

		// sort each countries vectors using PUBTO now
		for (Enumeration e = ucTbl.keys(); e.hasMoreElements();) {
			String flagCode = (String)e.nextElement();
			Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
			for (int u=0; u<ctryUcVct.size(); u++){
				CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
				uc.setPubFromSort(false); // sort by pubto now
			}
			Collections.sort(ctryUcVct);
		}

		//check by country
		//90.00         MAX(PUBTO)  =>  C: AVAIL    EFFECTIVEDATE           W   E       
		//must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
		for (int i=0; i<LOAvailVctC.size(); i++){
			EntityItem mdlloAvail = (EntityItem)LOAvailVctC.elementAt(i);
			String effdate = getAttrValueAndCheckLvl(mdlloAvail, "EFFECTIVEDATE", checklvl);
			addDebug("checkUniqueCoverage mdllastorder["+i+"] "+mdlloAvail.getKey()+" EFFECTIVEDATE "+effdate);

			ArrayList ctryList = new ArrayList();
			getCountriesAsList(mdlloAvail, ctryList,checklvl);

			boolean isok = true;
			Iterator itr = ctryList.iterator();
			while (itr.hasNext() && isok) {
				String ctryflag = (String) itr.next();
				Vector tmpVct = (Vector)ucTbl.get(ctryflag);
				if (tmpVct!=null){
					CoverageData uc = (CoverageData)tmpVct.lastElement();
					String maxPubto =uc.pubto;
					addDebug("checkUniqueCoverage "+uc.item.getKey()+" had maxPubto "+maxPubto+" for country "+ctryflag);
					//90.00         MAX(PUBTO)  =>  C: AVAIL    EFFECTIVEDATE           W   E       
					//must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
					isok = checkDates(maxPubto, effdate, DATE_GR_EQ);   //date1=>date2
				}else{
					addDebug("checkUniqueCoverage: No "+entityType+" found for country "+ctryflag);
				}
			}

			if(!isok){
				//PUBTO_ERR = must have a {0} with a {1} as late as or later than the {2} {3}
				//must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
				args[0]=eGrp.getLongDescription();
				args[1]=PokUtils.getAttributeDescription(eGrp, "PUBTO", "PUBTO");
				args[2]=rootitem.getEntityGroup().getLongDescription();
				args[3]=getLD_NDN(mdlloAvail);
				createMessage(checklvl,"PUBTO_ERR",args);
			}
			ctryList.clear();
		}

		// release memory
		for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
			String flagCode = (String)e.nextElement();
			Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
			for (int u=0; u<ctryUcVct.size(); u++){
				CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
				uc.dereference();
			}  
			ctryUcVct.clear();
		}
		ucTbl.clear();
		eiVct.clear();
	}
//	20121106 Add	30.60			SYSTEMMAX		LSEOPRODSTRUCT	CONFQTY		E	E	E		
//	{LD: LSEOPRODSTRUCT} {LD: CONFQTY} {CONFQTY} can not be greater than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: SYSTEMMAX} {SYSTEMMAX}																																																																																																																																																																																																																																								
	protected void checkSystemMaxAndConfqty(EntityItem rootEntity,String linktype, int checklvl) throws SQLException, MiddlewareException{
		for (int i=0; i<rootEntity.getDownLinkCount(); i++){
			EntityItem seopsItem = (EntityItem)rootEntity.getDownLink(i);
			if (seopsItem.getEntityType().equals(linktype)) {
				String confQty = PokUtils.getAttributeValue(seopsItem, "CONFQTY",", ", "1", false);
				addDebug("SEOPS "+seopsItem.getKey()+" CONFQTY: "+confQty);
				int iconfQty = Integer.parseInt(confQty);
				for(int j=0; j< seopsItem.getDownLinkCount();j++){
					EntityItem psitem = (EntityItem)seopsItem.getDownLink(j);
					if(psitem.getEntityType().equals("PRODSTRUCT")){
						String sysmax = PokUtils.getAttributeValue(psitem, "SYSTEMMAX",", ", "0", false); 
						addDebug("PS "+psitem.getKey()+" SYSTEMMAX: "+sysmax);
						int isysmax = Integer.parseInt(sysmax);
						if(isysmax < iconfQty){
//							SYSTEMMAX_CONFQTY_ERROR = {0} {1} can not be greater than {2} {3}
//							{LD: LSEOPRODSTRUCT} {LD: CONFQTY} {CONFQTY} can not be greater than {LD: PRODSTRUCT} {NDN: PRODSTRUCT} {LD: SYSTEMMAX} {SYSTEMMAX}
							args[0]=seopsItem.getEntityGroup().getLongDescription();
							args[1]=getLD_Value(seopsItem, "CONFQTY");
							args[2]=getLD_NDN(psitem);
							args[3]=getLD_Value(psitem, "SYSTEMMAX");
							createMessage(checklvl,"SYSTEMMAX_CONFQTY_ERROR",args);
						}
					}
				}
			}
		}		
	}
	/**
	 * check uniqueness using attributes on the dateentity with items linked to rootlinkitem
	 * @param rootlinkitem
	 * @param linkType
	 * @param entityType
	 * @param offeringItem
	 * @param fromAttr
	 * @param toAttr
	 * @param checklvl
	 * @param isRequired
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	protected void checkUniqueCoverage(EntityItem rootlinkitem, String linkType,
			String entityType, EntityItem offeringItem,String fromAttr, String toAttr, int checklvl, boolean isRequired) 
	throws SQLException, MiddlewareException
	{
		// get items to check
		Vector eiVct = PokUtils.getAllLinkedEntities(rootlinkitem, linkType, entityType);
		EntityGroup eGrp = m_elist.getEntityGroup(entityType);

		addDebug("checkUniqueCoverage entered rootlinkitem "+rootlinkitem.getKey()+" offeringItem "+offeringItem.getKey()+
				" for type "+entityType+" thru "+linkType+" cnt "+eiVct.size());
		if (eiVct.size()==0){
			if(isRequired){
				//MINIMUM_ERR = must have at least one {0}
				args[0]=eGrp.getLongDescription();
				createMessage(checklvl,"MINIMUM_ERR",args);
			}
			return;
		}

		ArrayList allCtryList = new ArrayList(); // get all countries for all imgs/warrs, only use valid ones
		Hashtable ucTbl = new Hashtable();
		//sort by PUBFROM 
		for (int i=0; i<eiVct.size(); i++){
			EntityItem ei = (EntityItem)eiVct.elementAt(i);
			String pubfrom = PokUtils.getAttributeValue(ei, "PUBFROM", "", null, false);
			if (pubfrom ==null){
				//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0]=getLD_NDN(ei);
				args[1]=PokUtils.getAttributeDescription(eGrp, "PUBFROM", "PUBFROM");
				createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);
				continue;
			}

			CoverageData uc = new CoverageData(ei,checklvl);
			Iterator itr = uc.ctryList.iterator();
			while (itr.hasNext()) {
				String ctryflag = (String) itr.next();
				Vector tmp = (Vector)ucTbl.get(ctryflag);
				if (tmp==null){
					tmp = new Vector();
					ucTbl.put(ctryflag,tmp);
				}
				tmp.add(uc);
			}
			getCountriesAsList(ei, allCtryList,checklvl); // accumulate all the different countries
		}

		if(ucTbl.size()==0){
			return;
		}
		// sort each countries vectors using the pubfrom attr
		for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
			Collections.sort((Vector)ucTbl.get(e.nextElement()));
		}

		/*  By Country in ID 2, if there are multiple, check that they do not overlap and that they do not have gaps. 
        This may be done by ordering them in increasing values for PUBFROM. Then
        MIN(PUBFROM) = the first PUBFROM
        MAX(PUBTO) = the last PUBTO
		 */

		addDebug(entityType+"  allCtryList "+allCtryList);

		//75.04			COUNTRYLIST	"Contains aggregate E"	LSEOBUNDLE	COUNTRYLIST			W	E	if one or more exists, then there must be one for every country	must have a {LD: IMG} for every country in the {LD: LSEOBUNDLE} {LD: COUNTRYLIST}
		//75.06			MIN(PUBFROM)	<=	LSEOBUNDLE	BUNDLPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: LSEOBUNDLE} {LD: BUNDLPUBDATEMTRGT {BUNDLPUBDATEMTRGT
		//75.08			MAX(PUBTO)	=>	LSEOBUNDLE	BUNDLUNPUBDATEMTRGT	Yes		W	E		must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: LSEOBUNDLE} {LD: BUNDLUNPUBDATEMTRGT} {BUNDLUNPUBDATEMTRGT}
		String rootpubfrom = PokUtils.getAttributeValue(offeringItem, fromAttr, "", EPOCH_DATE, false);
		String rootpubto = PokUtils.getAttributeValue(offeringItem, toAttr, "", FOREVER_DATE, false);
		ArrayList ctryList = new ArrayList();
		getCountriesAsList(offeringItem, ctryList,checklvl);
		addDebug("checkUniqueCoverage offeringItem "+offeringItem.getKey()+" rootpubfrom "+rootpubfrom+" rootpubto "+rootpubto+
				" ctryList "+ctryList);

		if(!allCtryList.containsAll(ctryList)){
			//MISSING_CTRY_ERR2 = must have a {0} for every Country in the {1} {2}, missing {3}
			args[0]=eGrp.getLongDescription();
			args[1]=offeringItem.getEntityGroup().getLongDescription();
			// offeringItem is not the root
			if(offeringItem.getEntityID()!=this.getEntityID() && !offeringItem.getEntityType().equals(this.getEntityType())){
				args[1]=this.getLD_NDN(offeringItem);
			}
			//args[2]=getLD_Value(offeringItem, "COUNTRYLIST");
			args[2]=PokUtils.getAttributeDescription(offeringItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
			//Note: identify the country that does not have an Image"	
			ArrayList ctryflagList = new ArrayList();
			getCountriesAsList(offeringItem, ctryflagList,checklvl);
			ctryflagList.removeAll(allCtryList); // remove all matches
			args[3]=getUnmatchedDescriptions(offeringItem, "COUNTRYLIST",ctryflagList);
			createMessage(checklvl,"MISSING_CTRY_ERR2",args);
			ctryflagList.clear();
		}

		//check by country
		boolean isok = true;
		Iterator itr = ctryList.iterator();
		while (itr.hasNext() && isok) {
			String ctryflag = (String) itr.next();
			Vector tmpVct = (Vector)ucTbl.get(ctryflag); // get the IMG/WARR that have this country
			if (tmpVct!=null){
				CoverageData uc = (CoverageData)tmpVct.firstElement();
				String minPubfrom =uc.pubfrom;
				//89            MIN(PUBFROM)    <=  A: AVAIL    EFFECTIVEDATE           RW  RE      
				//must have a {LD: IMG} with a PUBFROM as early as or earlier than the  {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
				isok = checkDates(minPubfrom, rootpubfrom, DATE_LT_EQ); //date1<=date2
				if(!isok){
					addDebug("checkUniqueCoverage "+uc.item.getKey()+" had minpubfrom "+minPubfrom+" for country "+ctryflag);					
				}
			}else{
				addDebug("checkUniqueCoverage: No "+entityType+" found for country "+ctryflag);
			}
		}

		if(!isok){
			//PUBFROM_ERR = must have a {0} with a {1} as early as or earlier than the {2} {3}
			args[0]=eGrp.getLongDescription();
			args[1]=PokUtils.getAttributeDescription(eGrp, "PUBFROM", "PUBFROM");
			args[2]=offeringItem.getEntityGroup().getLongDescription();
			// offeringItem is not the root
			if(offeringItem.getEntityID()!=this.getEntityID() && !offeringItem.getEntityType().equals(this.getEntityType())){
				args[2]=this.getLD_NDN(offeringItem);
			}
			args[3]=this.getLD_Value(offeringItem, fromAttr);
			createMessage(checklvl,"PUBFROM_ERR",args);
		}

		/*  By Country in ID 2, if there are multiple, check that they do not overlap and that they do not have gaps. 
        This may be done by ordering them in increasing values for PUBFROM. Then
        MIN(PUBFROM) = the first PUBFROM
        MAX(PUBTO) = the last PUBTO

        Check that there are no gaps in the date range by checking that Ith PUBTO = Ith+1 PUBFROM + 1. 
        The error text is found in the spreadsheet for ID 1.
		 */
		if (eGrp.getEntityItemCount()>1){
			HashSet badRangeSet = new HashSet();

			// look for overlap or gaps by country
			for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
				String flagCode = (String)e.nextElement();
				Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
				//addDebug("GAPTEST ctry "+flagCode+" ctryUcVct.size "+ctryUcVct.size());
				CoverageData prevUc = null;
				// look at all IMG/WARR for this country
				for (int u=0; u<ctryUcVct.size(); u++){
					CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
					if (u!=0){
						//addDebug("GAPTEST["+u+"] ctry "+flagCode+" \nprevUc "+prevUc+" \ncurrUc "+uc);
						Date pubToDate = Date.valueOf(prevUc.pubto);
						long pubto = pubToDate.getTime();
						long pubtoPlus1 = pubto + 24 * 60 * 60*1000; //pubto+ 1 day
						Date pubtoPlus1Date = new Date(pubtoPlus1);
						//addDebug("GAPTEST["+u+"] prev pubto "+prevUc.pubto+" pubtoPlus1Date "+pubtoPlus1Date+" cur pubfrom "+uc.pubfrom);
						if (!pubtoPlus1Date.toString().equals(uc.pubfrom)){
							if (!badRangeSet.contains(prevUc.item) ||
									!badRangeSet.contains(uc.item))	{
								badRangeSet.add(prevUc.item);
								badRangeSet.add(uc.item);
								addDebug("checkUniqueCoverage output date range msg for "+prevUc.item.getKey()+
										" and "+uc.item.getKey());
								//  86.00   IMG     MODELIMG-d                              IMG 
								//  87.00           CoverageData      IMG             W   E       
								//  {LD: IMG} {NDN: IMG} have gaps in the data range or they overlap.
								// DATE_RANGE_ERR={0} and {1} have gaps in the date range or they overlap.
								args[0]=getLD_NDN(prevUc.item);
								args[1]=getLD_NDN(uc.item);
								createMessage(checklvl,"DATE_RANGE_ERR",args);
							}else{
								addDebug("checkUniqueCoverage already output date range msg for "+prevUc.item.getKey()+
										" and "+uc.item.getKey());
							}
						}
					}
					prevUc = uc;
				}
			}
			badRangeSet.clear();
		}// end has more than 1 item for gap tests

		// sort each countries vectors using PUBTO now
		for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
			String flagCode = (String)e.nextElement();
			Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
			for (int u=0; u<ctryUcVct.size(); u++){
				CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
				uc.setPubFromSort(false); // sort by pubto now
			}
			Collections.sort(ctryUcVct);
		}

		//check by country
		//90.00         MAX(PUBTO)  =>  C: AVAIL    EFFECTIVEDATE           W   E       
		//must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
		isok = true;
		itr = ctryList.iterator();
		while (itr.hasNext() && isok) {
			String ctryflag = (String) itr.next();
			Vector tmpVct = (Vector)ucTbl.get(ctryflag);
			if (tmpVct!=null){
				CoverageData uc = (CoverageData)tmpVct.lastElement();
				String maxPubto =uc.pubto;
				//addDebug("checkUniqueCoverage "+uc.item.getKey()+" had maxPubto "+maxPubto+" for country "+ctryflag);
				//90.00         MAX(PUBTO)  =>  C: AVAIL    EFFECTIVEDATE           W   E       
				//must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
				isok = checkDates(maxPubto, rootpubto, DATE_GR_EQ);   //date1=>date2
				if(!isok){
					addDebug("checkUniqueCoverage "+uc.item.getKey()+" had maxPubto "+maxPubto+" for country "+ctryflag);
				}
			}else{
				addDebug("checkUniqueCoverage: No "+entityType+" found for country "+ctryflag);
			}
		}

		if(!isok){
			//PUBTO_ERR = must have a {0} with a {1} as late as or later than the {2} {3}
			//must have a {LD: IMG} with a PUBTO as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
			args[0]=eGrp.getLongDescription();
			args[1]=PokUtils.getAttributeDescription(eGrp, "PUBTO", "PUBTO");
			args[2]=offeringItem.getEntityGroup().getLongDescription();
			// offeringItem is not the root
			if(offeringItem.getEntityID()!=this.getEntityID() && !offeringItem.getEntityType().equals(this.getEntityType())){
				args[2]=this.getLD_NDN(offeringItem);
			}
			args[3]=this.getLD_Value(offeringItem, toAttr);
			createMessage(checklvl,"PUBTO_ERR",args);
		}
		ctryList.clear();

		// release memory
		for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
			String flagCode = (String)e.nextElement();
			Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
			for (int u=0; u<ctryUcVct.size(); u++){
				CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
				uc.dereference();
			}  
			ctryUcVct.clear();
		}
		ucTbl.clear();
	}

	/**
	 * get flag descriptions for the flagcodes
	 * @param item
	 * @param attrCode
	 * @param unmatchedFlags
	 * @return
	 */
	protected String getUnmatchedDescriptions(EntityItem item, String attrCode,ArrayList unmatchedFlags){
		EANFlagAttribute att = (EANFlagAttribute)item.getAttribute(attrCode);
		StringBuffer sb = new StringBuffer();
		if (att != null && att.toString().length()>0) {
			// Get the selected Flag codes.
			MetaFlag[] mfArray = (MetaFlag[]) att.get();
			for (int im = 0; im < mfArray.length; im++) {
				// get selection
				if (unmatchedFlags.contains(mfArray[im].getFlagCode())) {
					if(sb.length()>0){
						sb.append(", ");
					}
					sb.append(mfArray[im].toString());
				}
			} //end for
		} //end of null chk
		return sb.toString();
	}
	/**
	 * get flag description for the flagcode
	 * @param grp
	 * @param attrCode
	 * @param unmatchedFlag
	 * @return
	 */
	protected String getUnmatchedDescriptions(EntityGroup grp, String attrCode,String unmatchedFlag){
	    EANMetaFlagAttribute mfa = (EANMetaFlagAttribute) grp.getMetaAttribute(attrCode);
		if (mfa != null) {
			MetaFlag mf = mfa.getMetaFlag(unmatchedFlag);
			if(mf!=null){
				return mf.toString();
			}
		} //end of null chk
		return "";
	}
    /*************
     * Check the WARR against the avail
     * 	 - All xxAVAIL-plannedavail countries must be a subset of xxWARR countries.
     *   - BY country the MIN(xxWARR.EFFECTIVEDATE) must be <= then all xxAVAIL-plannedavail effdates
     *   - BY country the MAX(xxWARR.ENDDATE)	must be => then all xxAVAIL-lastorderavail effdates

PRODSTRUCT
140.50	IF		FCTYPE	<>	"RPQ-ILISTED" (120) | "RPQ-PLISTED" (130) | "RPQ-RLISTED" (0140)						If not RPQ	
140.60			WARRCoverage				Yes	E	E	E	Not Required	{LD: WARR} {NDN: WARR} have gaps in the date range.
140.70			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		E	E	E	Column E - attributes are on PRODSTRUCTWARR	must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
140.80			MIN(EFFECTIVEDATE)	<=	A: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes are on PRODSTRUCTWARR	must have a {LD: WARR} with an EFFECTIVEDATE  as early as or earlier than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
140.90			MAX(ENDDATE)	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes are on PRODSTRUCTWARR	must have a {LD: WARR} with an ENDATE as late as or later than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: B: AVAIL}
---------
MODEL
92.00			WARRCoverage		EntityType		Yes	RW	RE	RE	Required if XCC	{LD: WARR} {NDN: WARR} have gaps in the date range.
92.10			COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		E	E	E	Column E - attributes being moved to MODELWARR	must have a {LD: WARR} for every country in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
92.20			MIN(EFFECTIVEDATE)	<=	A: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes being moved to MODELWARR	must have a {LD: WARR} with an EFFECTIVE DATE as early as or earlier than the  {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
92.30			MAX(ENDDATE)	=>	C: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes being moved to MODELWARR	must have a {LD: WARR} with a ENDATE as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
							
	 *
CC.	WARRCoverage

ID	What	Op	Data to be Checked
1	WARRCoverage		EntityType	
2	COUNTRYLIST	Contains	Path: AVAIL	COUNTRYLIST
3	MIN(EFFECTIVEDATE)	<=	Path: AVAIL	EFFECTIVEDATE
4	MAX(ENDDATE)	=>	Path: AVAIL	EFFECTIVEDATE

The relator to the WARR has the following attributes (see the column above labeled �What�):
1.	EFFECTIVEDATE
2.	ENDDATE
3.	COUNTRYLIST

If ENDDATE is not specified, then assume a date of "9999-12-31".

By Country in ID 2, if there are multiple, check that they do not have gaps in time by Country. 
This may be done by ordering them by Country in increasing values for PUBFROM. Then
MIN(EFFECTIVEDATE) = the first EFFECTIVEDATE
MAX(ENDDATE) = the last ENDDATE

Check that there are no gaps in the date range by checking that Ith ENDDATE => Ith+1 EFFECTIVEDATE + 1. 
The error text is found in the spreadsheet for ID 1.

This check is different than UniqueCoverage in the following ways:
�	For a Country, there can be more than one WARR in effect at the same time.
�	The attributes are on the relator to the WARR
�	The attribute DEFWARR with a value of �Yes� implies that it is the World Wide Default Warranty. 
If one exists, then this rule passes without any other checking assuming that the date range is consistent 
with the availability of the offering.
	 *
     * @param availParent
     * @param warrParent
     * @param warrRelator
     * @param checklvl
     * @param warrchecklvl
     * @throws SQLException
     * @throws MiddlewareException
     */
	protected boolean doWARRChecks(){
		//fixme remove when warranty is ready WARR_Checks=true
		String warrchecksStr =  COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties.getValue("WARR",
				"_Checks","false");
		addDebug("doWARRChecks "+warrchecksStr);

		return warrchecksStr.equalsIgnoreCase("true");
	}
    /**
     * @param availParent
     * @param warrParent
     * @param warrRelator
     * @param availRelator
     * @param checklvl
     * @param warrchecklvl
     * @throws SQLException
     * @throws MiddlewareException
     */
    protected void checkWarrCoverage(EntityItem availParent, EntityItem warrParent, String warrRelator,
    		String availRelator, int checklvl, int warrchecklvl) 
    throws SQLException, MiddlewareException
    {
		//fixme bypass coverage check and msg
		if(!doWARRChecks()){
			this.addOutput("Bypassing Warranty coverage checks for now.");
			return;
		}
		
    	EntityGroup eGrp = m_elist.getEntityGroup(warrRelator);
      	Vector warrRelVct = getDownLinkEntityItems(warrParent, warrRelator);
      	if(warrRelVct.size()==0){
        	addDebug("checkWarrCoverage NO "+warrRelator+" found.");
      		return;
      	}
      	
    	
    	Vector availVct = PokUtils.getAllLinkedEntities(availParent, availRelator, "AVAIL");
    	Vector loAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", LASTORDERAVAIL);
    	Vector plannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", PLANNEDAVAIL);
    	
    	addDebug("checkWarrCoverage  availParent "+availParent.getKey()+" "+availRelator+" availVct: "+
    			availVct.size()+" loavailVct: "+loAvailVct.size()+" plannedAvailVct: "+plannedAvailVct.size());
    	
    	if(plannedAvailVct.size()==0){ // loavail not required
    		availVct.clear();
    		loAvailVct.clear();
    		plannedAvailVct.clear();
    		warrRelVct.clear();
    		return;
    	}
    	
    	checkMdlWarrWithAvailAAndAvailC(plannedAvailVct, loAvailVct, warrParent, warrRelator, warrRelVct, availVct, eGrp, availParent, checklvl, warrchecklvl);
    	
    	// MES AVAIL
    	Vector mesLoAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESLASTORDERAVAIL);
    	Vector mesPlannedAvailVct = PokUtils.getEntitiesWithMatchedAttr(availVct, "AVAILTYPE", MESPLANNEDAVAIL);
    	
    	addDebug("checkWarrCoverage  availParent "+availParent.getKey()+" "+availRelator+" availVct: "+
    			availVct.size()+" mesLoavailVct: "+mesLoAvailVct.size()+" mesPlannedAvailVct: "+mesPlannedAvailVct.size());
    	
    	if(mesPlannedAvailVct.size()==0){ // loavail not required
    		availVct.clear();
    		mesLoAvailVct.clear();
    		mesPlannedAvailVct.clear();
    		warrRelVct.clear();
    		return;
    	}
    	checkMdlWarrWithAvailAAndAvailC(mesPlannedAvailVct, mesLoAvailVct, warrParent, warrRelator, warrRelVct, availVct, eGrp, availParent, checklvl, warrchecklvl);
		
    	// release memory    	
		warrRelVct.clear();
    	availVct.clear();
    	loAvailVct.clear();
    	plannedAvailVct.clear();
    }
    
    /**
     * Check MODEL WARR with A:AVAIL and C:AVAIL
     * @param plannedAvailVct
     * @param loAvailVct
     * @param warrParent
     * @param warrRelator
     * @param warrRelVct
     * @param availVct
     * @param eGrp
     * @param availParent
     * @param checklvl
     * @param warrchecklvl
     * @throws SQLException
     * @throws MiddlewareException
     */
    private void checkMdlWarrWithAvailAAndAvailC(Vector plannedAvailVct, Vector loAvailVct, EntityItem warrParent, 
    		String warrRelator, Vector warrRelVct, Vector availVct,
    		EntityGroup eGrp, EntityItem availParent, int checklvl, int warrchecklvl) throws SQLException, MiddlewareException{
    	
    	ArrayList warrCtryList = new ArrayList(); // get all countries for all warrs, only use valid ones
    	Hashtable ucTbl = new Hashtable();
    	Vector ucVct = new Vector();    	
    	
    	//sort avails find earliest date by ctry and latest date by ctry
    	AttrComparator attrComp = new AttrComparator("EFFECTIVEDATE");
		Collections.sort(plannedAvailVct, attrComp);
		Collections.sort(loAvailVct, attrComp);

		ArrayList offeringCtryList = new ArrayList(); // get all countries for the offering
		Hashtable availByCtryTbl = new Hashtable();
    	for (int i=plannedAvailVct.size()-1; i>=0; i--){ // go in reverse, getting earliest date last
    		ArrayList ctryList = new ArrayList();
    		EntityItem plaAvail = (EntityItem)plannedAvailVct.elementAt(i);
			String fromdate = PokUtils.getAttributeValue(plaAvail, "EFFECTIVEDATE", "", "", false);
    		getCountriesAsList(plaAvail, ctryList,checklvl);
    		offeringCtryList.addAll(ctryList);
    		
     		Iterator itr = ctryList.iterator();
    		while (itr.hasNext()) {
    			String ctryflag = (String) itr.next();
    			TPIC tpic = (TPIC)availByCtryTbl.get(ctryflag);
    			if(tpic!=null){
    				tpic.fromDate=fromdate;
    			}else{
    				availByCtryTbl.put(ctryflag,new TPIC(ctryflag,fromdate));
    			}
    		}
    		ctryList.clear();
    	}
    	for (int i=0; i<loAvailVct.size(); i++){ // go forward, getting latest date last
    		ArrayList ctryList = new ArrayList();
    		EntityItem loAvail = (EntityItem)loAvailVct.elementAt(i);
			String todate = PokUtils.getAttributeValue(loAvail, "EFFECTIVEDATE", "", "", false);
    		getCountriesAsList(loAvail, ctryList,checklvl);
    		
     		Iterator itr = ctryList.iterator();
    		while (itr.hasNext()) {
    			String ctryflag = (String) itr.next();
     			TPIC tpic = (TPIC)availByCtryTbl.get(ctryflag);
    			if(tpic!=null){
    				tpic.toDate=todate;
    			}
    		}
    		ctryList.clear();
    	}
    	
    	addDebug("checkWarrCoverage availByCtryTbl "+availByCtryTbl);
       	
     	//WARR will be grouped by country and sorted by EFFECTIVEDATE
    	findWarrByCtry(warrParent, warrRelator, warrRelVct, offeringCtryList,warrCtryList, 
    			ucTbl,ucVct,checklvl);
    	addDebug("checkWarrCoverage warrParent "+warrParent.getKey()+
    			" ucVct "+ucVct.size()+" all "+warrRelator+" warrCtryList "+warrCtryList+" offeringCtryList "+offeringCtryList);

    	if(ucVct.size()==0){
    		availVct.clear();
    		loAvailVct.clear();
    		plannedAvailVct.clear();
    		offeringCtryList.clear();
    		warrCtryList.clear();
    		return; // no valid WARR to check
    	}
    	
    	// check ctry and pubfrom dates
    	for (int i=0; i<plannedAvailVct.size(); i++){
    		EntityItem plaAvail = (EntityItem)plannedAvailVct.elementAt(i);
    		ArrayList ctryList = new ArrayList();
    		getCountriesAsList(plaAvail, ctryList,checklvl);
    		addDebug("checkWarrCoverage plaAvail["+i+"] "+plaAvail.getKey()+" ctryList "+ctryList);
    		//140.70		pswarr.COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		E	E	E	Column E - attributes are on PRODSTRUCTWARR	must have a {LD: WARR} for every country in the {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
    		//92.10			modelwarr.COUNTRYLIST	"Contains aggregate E"	A: AVAIL	COUNTRYLIST		E	E	E	Column E - attributes being moved to MODELWARR	must have a {LD: WARR} for every country in the {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
    		if(!warrCtryList.containsAll(ctryList)){
    			//MISSING_CTRY_ERR2 = must have a {0} for every Country in the {1} {2}, missing {3}
    			args[0]=eGrp.getLongDescription();
    			String info2=getLD_NDN(availParent);
				if (availParent.getEntityType().equals(getEntityType()) && availParent.getEntityID()==getEntityID()){
					info2=availParent.getEntityGroup().getLongDescription();
				}
    			args[1]=info2;
    			args[2]=getLD_NDN(plaAvail)+" "+
    				PokUtils.getAttributeDescription(plaAvail.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
    			//Note: identify the country that does not have an WARR	
    			ArrayList ctryflagList = new ArrayList();
    			getCountriesAsList(plaAvail, ctryflagList,checklvl);
    			ctryflagList.removeAll(warrCtryList); // remove all matches
    			args[3]=getUnmatchedDescriptions(plaAvail, "COUNTRYLIST",ctryflagList);
    			createMessage(checklvl,"MISSING_CTRY_ERR2",args);
    			ctryflagList.clear();
    		}

    		//check by country
    		String effdate = getAttrValueAndCheckLvl(plaAvail, "EFFECTIVEDATE", checklvl);
    		addDebug("checkWarrCoverage plaAvail "+plaAvail.getKey()+" EFFECTIVEDATE "+effdate);
    		boolean isok = true;
    		Iterator itr = ctryList.iterator();
    		StringBuffer allMissingCtrySb = new StringBuffer();
    		while (itr.hasNext()){// && isok) {
    			String ctryflag = (String) itr.next();
    			Vector tmpVct = (Vector)ucTbl.get(ctryflag); // get the PRODSTRUCTWARR that have this country
    			if (tmpVct!=null){
    				CoverageData uc = (CoverageData)tmpVct.firstElement(); // first one is the earliest
    				String minEffDate =uc.pubfrom;
    				addDebug("checkWarrCoverage ctry "+ctryflag+" minEffDate "+minEffDate+" found on "+uc.item.getKey());
    				//140.80		MIN(prodstructwarr.EFFECTIVEDATE)	<=	A: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes are on PRODSTRUCTWARR	must have a {LD: WARR} with an EFFECTIVEDATE  as early as or earlier than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: A: AVAIL}
    				//92.20			MIN(modelwarr.EFFECTIVEDATE)	<=	A: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes being moved to MODELWARR	must have a {LD: WARR} with an EFFECTIVE DATE as early as or earlier than the  {LD: MODEL} {LD: AVAIL} {NDN: A: AVAIL}
    				isok = checkDates(minEffDate, effdate, DATE_LT_EQ);	//date1<=date2
    				if(!isok){
    					//PUBFROM_ERR = must have a {0} with a {1} as early as or earlier than the {2} {3}
    					/*args[0]=eGrp.getLongDescription();
    					args[1]=PokUtils.getAttributeDescription(eGrp, "EFFECTIVEDATE", "EFFECTIVEDATE");
    					String info2=getLD_NDN(availParent);
    					if (availParent.getEntityType().equals(getEntityType()) && availParent.getEntityID()==getEntityID()){
    						info2=availParent.getEntityGroup().getLongDescription();
    					}
    					args[2]=info2;
    					args[3]=getLD_NDN(plaAvail)+" for Country: "+
                			getUnmatchedDescriptions(eGrp, "COUNTRYLIST", ctryflag);
    					createMessage(checklvl,"PUBFROM_ERR",args);*/
    					
    					if(allMissingCtrySb.length()>0){
    						allMissingCtrySb.append(", ");
    					}
    					allMissingCtrySb.append(getUnmatchedDescriptions(eGrp, "COUNTRYLIST", ctryflag));
    				} 
    			}else{
    				addDebug("checkWarrCoverage: No "+warrRelator+" found for country "+ctryflag);
    			}
    		}
    		// list all countries affected
    		if(allMissingCtrySb.length()>0){
    			//PUBFROM_CTRY_ERR = must have a {0} with a &quot;{1}&quot; as early as or earlier than the {2} {3} for Country: {4}
    			args[0]=eGrp.getLongDescription();
    			args[1]=PokUtils.getAttributeDescription(eGrp, "EFFECTIVEDATE", "EFFECTIVEDATE");
    			String info2=getLD_NDN(availParent);
    			if (availParent.getEntityType().equals(getEntityType()) && availParent.getEntityID()==getEntityID()){
    				info2=availParent.getEntityGroup().getLongDescription();
    			}
    			args[2]=info2;
    			args[3]=getLD_NDN(plaAvail);
    			args[4]=allMissingCtrySb.toString();
    			createMessage(checklvl,"PUBFROM_CTRY_ERR",args);
    		}

    		ctryList.clear();
    	}

    	/* By Country in ID 2, if there are multiple, check that they do not have gaps in time by Country. 
			This may be done by ordering them by Country in increasing values for PUBFROM. Then
			MIN(EFFECTIVEDATE) = the first EFFECTIVEDATE
			MAX(ENDDATE) = the last ENDDATE

	    	Check that there are no gaps in the date range by checking that Ith ENDDATE => Ith+1 EFFECTIVEDATE + 1. 
	        The error text is found in the spreadsheet for ID 1.
    	 */
    	HashSet badRangeSet = new HashSet();

    	// look for gaps by country
    	for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
    		String flagCode = (String)e.nextElement();
    		// keys are based on countries on the warr relator, possibly with countries from the avail added
    		if (!offeringCtryList.contains(flagCode)){
    			addDebug("checkWarrCoverage skipping gap test for flagCode "+flagCode+". it was not in the offering ctrys "+offeringCtryList);
    			continue;
    		}
    		
    		Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
    		//addDebug("GAPTEST ctry "+flagCode+" ctryUcVct.size "+ctryUcVct.size());
    		CoverageData prevUc = null;
    		TPIC tpic = (TPIC)availByCtryTbl.get(flagCode);
    		String maxToCtryDate = tpic.toDate;
    		String minFromCtryDate = tpic.fromDate;
    		addDebug("checkWarrCoverage flagCode "+flagCode+" offering minFromCtryDate "+minFromCtryDate+
    				" maxToCtryDate "+maxToCtryDate);
    		// look at all WARR for this country
    		for (int u=0; u<ctryUcVct.size(); u++){
    			CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
    			if (u!=0){
    				//addDebug("GAPTEST["+u+"] ctry "+flagCode+" \nprevUc "+prevUc+" \ncurrUc "+uc);
    				if((uc.pubto.compareTo(minFromCtryDate)<0) ||
    						uc.pubfrom.compareTo(maxToCtryDate)>0){
    					addDebug("checkWarrCoverage "+uc+" is outside offering range for "+
    							flagCode+" minFromCtryDate "+minFromCtryDate+
    		    				" maxToCtryDate "+maxToCtryDate);
    				}else{
    					Date pubToDate = Date.valueOf(prevUc.pubto);
    					long pubto = pubToDate.getTime();
    					long pubtoPlus1 = pubto + 24 * 60 * 60*1000; //pubto+ 1 day
    					Date pubtoPlus1Date = new Date(pubtoPlus1);
    					Date pubFromDate = Date.valueOf(uc.pubfrom);
    					//addDebug("GAPTEST["+u+"] prev pubto "+prevUc.pubto+" pubtoPlus1Date "+pubtoPlus1Date+" cur pubfrom "+uc.pubfrom);
    					//if (!pubtoPlus1Date.toString().equals(uc.pubfrom)){
    					if (pubtoPlus1Date.compareTo(pubFromDate)<0){
    						if (!badRangeSet.contains(prevUc.item) ||
    								!badRangeSet.contains(uc.item))	{
    							badRangeSet.add(prevUc.item);
    							badRangeSet.add(uc.item);
    							addDebug("checkWarrCoverage output date range msg for "+prevUc.item.getKey()+
    									" and "+uc.item.getKey());
    				    		//140.60		WARRCoverage				Yes	E	E	E	Not Required	{LD: WARR} {NDN: WARR} have gaps in the date range.
    				    		//92.00			WARRCoverage				Yes	RW	RE	RE	Required if XCC	{LD: WARR} {NDN: WARR} have gaps in the date range.
    							// DATE_RANGE_ERR2={0} and {1} have gaps in the date range.
    							args[0]=getLD_NDN(prevUc.item)+" for "+getLD_NDN(prevUc.warritem);
    							args[1]=getLD_NDN(uc.item)+" for "+getLD_NDN(uc.warritem);
    							createMessage(warrchecklvl,"DATE_RANGE_ERR2",args);
    						}else{
    							addDebug("checkWarrCoverage already output date range msg for "+prevUc.item.getKey()+
    									" and "+uc.item.getKey());
    						}
    					}
    				}
    			}
    			// check for prevUc ending after current uc
    			if (prevUc == null || prevUc.pubto.compareTo(uc.pubto)<0){
    				prevUc = uc;
    			}
    		}
    	}
    	badRangeSet.clear();

    	// end has gap tests
    	// sort each countries vectors using PUBTO now
    	for (Enumeration e = ucTbl.keys(); e.hasMoreElements();){
    		String flagCode = (String)e.nextElement();
    		Vector ctryUcVct = (Vector)ucTbl.get(flagCode);
    		for (int u=0; u<ctryUcVct.size(); u++){
    			CoverageData uc = (CoverageData)ctryUcVct.elementAt(u);
    			uc.setPubFromSort(false); // sort by pubto now
    		}
    		Collections.sort(ctryUcVct);
    	}

    	//check pubto by country
    	for (int i=0; i<loAvailVct.size(); i++){
    		EntityItem loAvail = (EntityItem)loAvailVct.elementAt(i);
    		String effdate = getAttrValueAndCheckLvl(loAvail, "EFFECTIVEDATE", checklvl);

    		ArrayList ctryList = new ArrayList();
    		getCountriesAsList(loAvail, ctryList,checklvl);
    		addDebug("checkWarrCoverage loAvail["+i+"] "+loAvail.getKey()+" EFFECTIVEDATE "+effdate+
    				" ctryList "+ctryList);

    		boolean isok = true;
    		Iterator itr = ctryList.iterator();
    		StringBuffer allMissingCtrySb = new StringBuffer();
    		while (itr.hasNext()){// && isok) {
    			String ctryflag = (String) itr.next();
        		// only look at ctry's in the plannedavail lists
        		if (!offeringCtryList.contains(ctryflag)){
        			addDebug("checkWarrCoverage skipping enddate check ctry: "+ctryflag+". it was not in the plannedavail offering ctrys "+offeringCtryList);
        			continue;
        		}
        		
    			Vector tmpVct = (Vector)ucTbl.get(ctryflag);
    			if (tmpVct!=null){
    				CoverageData uc = (CoverageData)tmpVct.lastElement(); // last one is the latest
    				String maxEndDate =uc.pubto;
    				addDebug("checkWarrCoverage ctry "+ctryflag+" maxEndDate "+maxEndDate+" found on "+uc.item.getKey());
    				//140.90		MAX(pswarr.ENDDATE)	=>	B: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes are on PRODSTRUCTWARR	must have a {LD: WARR} with an ENDATE as late as or later than the  {LD: PRODSTRUCT} {LD: AVAIL} {NDN: B: AVAIL}
    				//92.30			MAX(modelwarr.ENDDATE)	=>	C: AVAIL	EFFECTIVEDATE	Yes	E	E	E	Column E - attributes being moved to MODELWARR	must have a {LD: WARR} with a ENDATE as late as or later than the  {LD: MODEL} {LD: AVAIL} {NDN: C: AVAIL}
    				isok = checkDates(maxEndDate, effdate, DATE_GR_EQ);	//date1=>date2
    				if(!isok){
    					/*
    					//PUBTO_ERR = must have a {0} with a {1} as late as or later than the {2} {3}
    					args[0]=eGrp.getLongDescription();
    					args[1]=PokUtils.getAttributeDescription(eGrp, "ENDDATE", "ENDDATE");
    					String info2=getLD_NDN(availParent);
    					if (availParent.getEntityType().equals(getEntityType()) && availParent.getEntityID()==getEntityID()){
    						info2=availParent.getEntityGroup().getLongDescription();
    					}
    					args[2]=info2;
    					args[3]=getLD_NDN(loAvail)+" for Country: "+
            				getUnmatchedDescriptions(eGrp, "COUNTRYLIST", ctryflag);
    					createMessage(checklvl,"PUBTO_ERR",args);
    					*/
    					
    					if(allMissingCtrySb.length()>0){
    						allMissingCtrySb.append(", ");
    					}
    					allMissingCtrySb.append(getUnmatchedDescriptions(eGrp, "COUNTRYLIST", ctryflag));
    				}
    			}else{
    				addDebug("checkWarrCoverage: No "+warrRelator+" found for country "+ctryflag);
    			}
    		}
    		
    		// list all countries affected
			if(allMissingCtrySb.length()>0){
				//PUBTO_CTRY_ERR = must have a {0} with a &quot;{1}&quot; as late as or later than the {2} {3} for Country: {4}
				args[0]=eGrp.getLongDescription();
				args[1]=PokUtils.getAttributeDescription(eGrp, "ENDDATE", "ENDDATE");
				String info2=getLD_NDN(availParent);
				if (availParent.getEntityType().equals(getEntityType()) && availParent.getEntityID()==getEntityID()){
					info2=availParent.getEntityGroup().getLongDescription();
				}
				args[2]=info2;
				args[3]=getLD_NDN(loAvail);
				args[4]=allMissingCtrySb.toString();
				createMessage(checklvl,"PUBTO_CTRY_ERR",args);
			}
    		ctryList.clear();
    	}
    	
    	// release memory
    	for (Enumeration e = ucTbl.keys(); e.hasMoreElements();) {
    		String flagCode = (String)e.nextElement();
    		Vector ctryUcVct = (Vector)ucTbl.get(flagCode); 
    		ctryUcVct.clear();
    	}

		for (int u=0; u<ucVct.size(); u++){
			CoverageData uc = (CoverageData)ucVct.elementAt(u);
			uc.dereference();
		} 
		
		offeringCtryList.clear();
		Iterator itr = availByCtryTbl.keySet().iterator();
		while (itr.hasNext()) {
			String ctryflag = (String) itr.next();
			TPIC tpic = (TPIC)availByCtryTbl.get(ctryflag);
			tpic.dereference();
		}
		availByCtryTbl.clear();
		
		ucTbl.clear();
    	ucVct.clear();
    	
    	warrCtryList.clear();
    }
    
    /**
     * look for default warr, group warr by country and sort by effective date
     * 
     * @param warrParent
     * @param warrRelator
     * @param warrRelVct
     * @param offeringCtryList
     * @param warrCtryList - filled in by this method
     * @param ucTbl - filled in by this method
     * @param ucVct - filled in by this method
     * @param checklvl
     * 
     * @throws SQLException
     * @throws MiddlewareException
     */
    protected void findWarrByCtry(EntityItem warrParent, String warrRelator, Vector warrRelVct,
    		ArrayList offeringCtryList,	ArrayList warrCtryList, Hashtable ucTbl, Vector ucVct, 
    		int checklvl) throws SQLException, MiddlewareException
    {    	
     	EntityGroup eGrp = m_elist.getEntityGroup(warrRelator);
    	addDebug("findWarrByCtry entered  warrParent "+warrParent.getKey()+
    			" "+warrRelator+".cnt "+warrRelVct.size());
    	// get all countries for all warrs, only use valid ones
    	Vector warrVct = new Vector();
    	boolean stopCheck = false;
    	//build coveragedata, one for each warrrelator/warr, build hashtable key=ctry, value=coveragedata
    	for (int i=0; i<warrRelVct.size(); i++){
    		EntityItem ei = (EntityItem)warrRelVct.elementAt(i);
    		EntityItem warritem = this.getDownLinkEntityItem(ei, "WARR");
    		String defwarr = PokUtils.getAttributeFlagValue(ei, "DEFWARR");
    		addDebug("findWarrByCtry "+ei.getKey()+" "+warritem.getKey()+" defwarr "+defwarr);
    		
   			// Multiple WARRs with the attribute DEFWARR on the relator from either MODEL or PRODSTRUCT set to �Yes� 
			//is legal as long as the entityids are not identical (see the preceding paragraph).
			if(DEFWARR_Yes.equals(defwarr) && warritem.getUpLinkCount()>1){
				String defwarrdesc = PokUtils.getAttributeDescription(eGrp, "DEFWARR", "DEFWARR");
				// {LD: WARR} {NDN: WARR} is a duplicate.
				//List all WARRs that are duplicate.
				for (int i2=0; i2<warritem.getUpLinkCount(); i2++){
					EntityItem rel = (EntityItem)warritem.getUpLink(i2);
					String reldefwarr = PokUtils.getAttributeFlagValue(rel, "DEFWARR");
					//DUPLICATE_ERR2 = {0} is a duplicate
					args[0]=getLD_NDN(warritem)+" for "+
						(DEFWARR_Yes.equals(reldefwarr)?defwarrdesc+" ":"")+
						getLD_NDN(rel);
					createMessage(checklvl,"DUPLICATE_ERR2",args);
				}
				stopCheck = true;
				continue;
			}
			
    		if(warrVct.contains(warritem)){	// something was already linked to this warr
    			if(warrRelatorOverlap(warritem,checklvl)){
    				stopCheck = true;
    			}
    		}else{
    			warrVct.add(warritem);
    		}
    		// check for bad meta or bad data
    		String effdate = PokUtils.getAttributeValue(ei, "EFFECTIVEDATE", "", EPOCH_DATE, false);
    		if (effdate.startsWith("<span ")){ //"<span  means not in meta
            	addDebug("findWarrByCtry "+ei.getKey()+" EFFECTIVEDATE "+effdate);
       			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
        		args[0]= getLD_NDN(warrParent) +" "+getLD_NDN(ei);
    			args[1]=PokUtils.getAttributeDescription(eGrp, "EFFECTIVEDATE", "EFFECTIVEDATE");
				createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);
				stopCheck = true;
    		}
    		String tst = PokUtils.getAttributeFlagValue(ei, "COUNTRYLIST");
    		if(!DEFWARR_Yes.equals(defwarr) && (tst ==null || tst.length()==0)){
    	   		addDebug("findWarrByCtry "+ei.getKey()+" COUNTRYLIST "+tst);
       			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
				args[0]= getLD_NDN(warrParent) +" "+getLD_NDN(ei);
    			args[1]=PokUtils.getAttributeDescription(eGrp, "COUNTRYLIST", "COUNTRYLIST");
				createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);
				stopCheck = true;
    		}
    		tst = PokUtils.getAttributeValue(ei, "ENDDATE", "", FOREVER_DATE, false);
    		if (tst.startsWith("<span ")){ //"<span  means not in meta
    	 		addDebug("findWarrByCtry "+ei.getKey()+" ENDDATE "+tst);
       			//REQ_NOTPOPULATED_ERR = {0} {1} is required and does not have a value.
    			args[0]= getLD_NDN(warrParent) +" "+getLD_NDN(ei);
    			args[1]=PokUtils.getAttributeDescription(eGrp, "ENDDATE", "ENDDATE");
				createMessage(checklvl,"REQ_NOTPOPULATED_ERR",args);
				stopCheck = true;
    		}
    		
    		if(stopCheck){ // dont use bad data
    			continue;
    		}
    		
    		CoverageData uc = new CoverageData(ei,checklvl);
    		ucVct.add(uc);
    		
    		if(DEFWARR_Yes.equals(defwarr)){
    			// if this is a default warr, add all countries from the offering
    			uc.ctryList.addAll(offeringCtryList);
    			warrCtryList.addAll(offeringCtryList);
    		}
    		
    		Iterator itr = uc.ctryList.iterator();
    		while (itr.hasNext()) {
    			String ctryflag = (String) itr.next();
    			Vector tmp = (Vector)ucTbl.get(ctryflag);
    			if (tmp==null){
    				tmp = new Vector();
    				ucTbl.put(ctryflag,tmp);
    			}
    			tmp.add(uc);
    		}
    		getCountriesAsList(ei, warrCtryList,checklvl); // accumulate all the different countries
    	}
    	
   		if(stopCheck){ // dont use bad data
   			for (int u=0; u<ucVct.size(); u++){
   				CoverageData uc = (CoverageData)ucVct.elementAt(u);
   				uc.dereference();
   			}
   			ucVct.clear();
		}else{
			// sort each countries vectors using the xxxWARR.EFFECTIVEDATE attr
			for (Enumeration e = ucTbl.keys(); e.hasMoreElements();) {
				Collections.sort((Vector)ucTbl.get(e.nextElement()));
			}
			// sort the CoverageData by dates ignoring country here
			Collections.sort(ucVct);
		}
    }
    
    /**
     * look at all warrrelators to a specific warr, dates must not overlap for a country, if default
     * no dates can overlap
     * If identical WARRs (i.e. entityid) are linked to the offering and have overlapping dates for a Country, 
     * then this is an error. The error message is:
     * {LD: WARR} {NDN: WARR} is a duplicate.
     * List all WARRs that are duplicate.
     * 
     * @param warritem
     * @param isDefault
     * @param checklvl
     * @return
     * @throws SQLException
     * @throws MiddlewareException
     */
    private boolean warrRelatorOverlap(EntityItem warritem, int checklvl) 
    throws SQLException, MiddlewareException
    {
    	boolean overlap = false;
    	addDebug("warrRelatorOverlap entered "+warritem.getKey());
    			
    	// sort the relators to this warr by effective date
    	Collections.sort(warritem.getUpLink(),new AttrComparator("EFFECTIVEDATE"));
    	
    	EntityItem prevRelator = (EntityItem)warritem.getUpLink(0);
    	String prevWarrEffdate = PokUtils.getAttributeValue(prevRelator, "EFFECTIVEDATE", "", EPOCH_DATE, false);
    	String prevWarrEnddate = PokUtils.getAttributeValue(prevRelator, "ENDDATE", "", FOREVER_DATE, false);
    	ArrayList prevCtryList = new ArrayList();
    	getCountriesAsList(prevRelator, prevCtryList, checklvl);
    	
    	for (int i=1; i<warritem.getUpLinkCount(); i++){
    		EntityItem warrRelator = (EntityItem)warritem.getUpLink(i);
    		String warrEffdate = PokUtils.getAttributeValue(warrRelator, "EFFECTIVEDATE", "", EPOCH_DATE, false);
    		String warrEnddate = PokUtils.getAttributeValue(warrRelator, "ENDDATE", "", FOREVER_DATE, false);
    		ArrayList ctryList = new ArrayList();
        	getCountriesAsList(warrRelator, ctryList, checklvl);
			addDebug("warrRelatorOverlap rel["+(i-1)+"] "+prevRelator.getKey()+" prevWarrEffdate "+prevWarrEffdate+
					" prevWarrEnddate "+prevWarrEnddate+" prevCtryList "+prevCtryList+
					" rel["+i+"] "+warrRelator.getKey()+" warrEffdate "+warrEffdate+
					" warrEnddate "+warrEnddate+" ctryList "+ctryList);

    		//check date overlap by ctry
			prevCtryList.retainAll(ctryList); // keep any matches
    		if(prevCtryList.size()>0 && // some country matches
    			warrEffdate.compareTo(prevWarrEnddate)<=0){ // effdate before or equal to prev enddate
    			for (int i2=0; i2<warritem.getUpLinkCount(); i2++){
    				EntityItem rel = (EntityItem)warritem.getUpLink(i2);
    				//DUPLICATE_ERR2 = {0} is a duplicate
    				args[0]=getLD_NDN(warritem)+" for "+getLD_NDN(rel);
    				createMessage(checklvl,"DUPLICATE_ERR2",args);
    			}
    			overlap = true;	
    			break;
    		}
    		prevWarrEffdate = warrEffdate;
    		prevWarrEnddate = warrEnddate;
    		prevCtryList = ctryList;
    	}

    	return overlap;
    }
    
    /**
     *  	 	 	 	MODEL	COFCAT	=	"Hardware(100)	 	 	 	 	 	 
 	 	 	 	 	 	OR	 	 	 	 	 	 	 	 	 
 	 	 	 	 	 	MODEL	MACHTYPEATR+MODELATR	IN	5313-HPO and 5372-IS5	 	 	 	
     * @param item
     * @return
     */
    protected boolean isHardwareOrHIPOModel(EntityItem item) {
    	boolean isHardwareOrHIPOModel = false;
    	if ("MODEL".equals(item.getEntityType())) {
    		String modelCOFCAT = getAttributeFlagEnabledValue(item, "COFCAT");
    		if (HARDWARE.equals(modelCOFCAT) || isHIPOModel(item)) {
    			isHardwareOrHIPOModel = true;
    		}
    		addDebug("isHardwareOrHIPOModel: " + isHardwareOrHIPOModel + " for " + item.getKey() + " COFCAT " + modelCOFCAT);
    	}
    	return isHardwareOrHIPOModel;
    }
    
    /**
     * MODEL	MACHTYPEATR+MODELATR	IN	5313-HPO and 5372-IS5	 	 	 	
     * @param item
     * @return
     */
    protected boolean isHIPOModel(EntityItem item) {
    	boolean isHIPOModel = false;
    	if ("MODEL".equals(item.getEntityType())) {
    		String machType = getAttributeFlagEnabledValue(item, "MACHTYPEATR");
    		String model = getAttributeFlagEnabledValue(item, "MODELATR");
    		if (("5313".equals(machType) && "HPO".equals(model)) || 
    				("5372".equals(machType) && "IS5".equals(model))) {
    			isHIPOModel = true;
    		}
    		addDebug("isHIPOModel: " + isHIPOModel + " for " + item.getKey() +
    				" MACHTYPEATR " + machType + " MODELATR " + model);
    	}
    	return isHIPOModel;
    }
    
	protected class CoverageData implements Comparable
	{
		EntityItem item = null;
		EntityItem warritem = null; // only used for WARR coverage, item is the relator to this WARR
		String pubto = null;
		String pubfrom = null;
		boolean pubfromSort = true;
		ArrayList ctryList = new ArrayList();
		CoverageData(EntityItem ei, int checklvl)
		throws SQLException, MiddlewareException
		{  
			item = ei;
			getCountriesAsList(item, ctryList, checklvl);
			if(item.getEntityType().endsWith("WARR")){ // MODELWARR,PRODSTRUCTWARR
				pubto = PokUtils.getAttributeValue(item, "ENDDATE", "", FOREVER_DATE, false);
				pubfrom = PokUtils.getAttributeValue(item, "EFFECTIVEDATE", "", EPOCH_DATE,false); 
				warritem = DQABRSTATUS.this.getDownLinkEntityItem(item, "WARR");
				addDebug("CoverageData "+item.getKey()+" "+warritem.getKey()+
						" effdate "+pubfrom+" enddate "+pubto+" ctryList "+ctryList);
			}else{
				pubto = PokUtils.getAttributeValue(item, "PUBTO", "", FOREVER_DATE, false);
				pubfrom = PokUtils.getAttributeValue(item, "PUBFROM", "", EPOCH_DATE, false); 
				addDebug("CoverageData "+item.getKey()+
						" pubfrom "+pubfrom+" pubto "+pubto+" ctryList "+ctryList);
			}
		}
		void setPubFromSort(boolean b) { pubfromSort = b;} // allow override to PUBTO
		void dereference(){
			item = null;
			warritem = null;
			pubto = null;
			pubfrom = null;
			if(ctryList!=null){
				ctryList.clear();
				ctryList = null;
			}
		}
		public int compareTo(Object o) {
			if (pubfromSort){
				// order by pubfrom
				return pubfrom.compareTo(((CoverageData)o).pubfrom);
			}else{
				return pubto.compareTo(((CoverageData)o).pubto);
			}
		}
		public String toString(){
			return item.getKey()+" pubfrom "+pubfrom+" pubto "+pubto+" ctryList "+ctryList;
		}
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
	 * Time Period of Interest by Country (TPIC) 
	 * hold onto a pair of dates, one object per country
	 *
	 */
	protected static class TPIC implements Comparable{
		boolean fromSort = true;
		String ctry = null;
		EntityItem fromItem = null; // prodstruct abr needs this for msg - PLA avail
		EntityItem toItem = null; // prodstruct abr needs this for msg - LO avail
		String fromDate=null; // from planned avail - or feature offering/fallback
		String toDate=null;   // from last order avail - or feature offering/fallback
		TPIC(String c, String fd){
			this(c, fd,FOREVER_DATE);
		}
		TPIC(String c, String fd, String td){
			ctry = c;
			fromDate = fd;
			toDate = td;
		}
		TPIC getOverlay(TPIC other){
			TPIC overlay = null;
			if(other!=null && other.ctry.equals(ctry)){ // ctry must match
				if((other.fromDate.compareTo(toDate)>=0) || // other.from is after to
					other.toDate.compareTo(fromDate)<=0){ // other.to is before from
				}else{
					String fd = fromDate;
					String td = toDate;
					if(other.fromDate.compareTo(fromDate)>0){
						fd = other.fromDate; // pick latest
					}
					if(other.toDate.compareTo(toDate)<0){
						td = other.toDate; // pick earliest
					}
					// create TPIC with overlay dates
					overlay = new TPIC(ctry,fd,td);
				}
			}
			return overlay;
		}
		public String toString() {
			return "\nctry:"+ctry+" fromDate:"+fromDate+" toDate:"+toDate;
		}
		void setFromSort(boolean b) { fromSort = b;} // allow override to TODATE
		public int compareTo(Object o) {
			if (fromSort){
				// order by from date
				return fromDate.compareTo(((TPIC)o).fromDate);
			}else{
				return toDate.compareTo(((TPIC)o).toDate);
			}
		}
		void dereference(){
			ctry = null;
			fromDate = null;
			toDate = null;
			fromItem = null;
			toItem = null;
		}
	}
}
