//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.EANEntity;
import COM.ibm.eannounce.objects.EANFlagAttribute;
import COM.ibm.eannounce.objects.EANList;
import COM.ibm.eannounce.objects.EANMetaAttribute;
import COM.ibm.eannounce.objects.EntityGroup;
import COM.ibm.eannounce.objects.EntityItem;
import COM.ibm.eannounce.objects.EntityList;
import COM.ibm.eannounce.objects.ExtractActionItem;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.transform.oim.eacm.util.PokUtils;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

/**********************************************************************************
* From "QSM Extract.doc" 
**********************************************************************************/
//$Log: QSMFULLABRSTATUS.java,v $
//Revision 1.127  2020/04/20 01:58:11  xujianbo
//Recover QSM code
//
//Revision 1.125  2020/03/18 10:20:21  xujianbo
//Recover QSM code
//
//Revision 1.123  2020/03/11 07:39:33  xujianbo
//Fixed the issue for stroy     EACM-1199  Improve send to QSM
//
//Revision 1.122  2020/03/03 09:14:31  xujianbo
// OIM_EACMEACM-1199   -  Improve send to QSM
//
//Revision 1.121  2020/02/19 09:57:30  xujianbo
//Add  debug for QSM FULL
//
//Revision 1.120  2020/01/13 08:39:13  whwyue1
//Change code for WD ModelConvert part
//
//Revision 1.120  2019/12/23 08:27:53  whwyue
//Model WD
//
//Revision 1.115  2019/08/15 09:32:39  whwyue
//fix model part
//
//Revision 1.114  2019/08/15 08:41:41  whwyue
//change contains to indexof
//
//Revision 1.113  2019/08/15 07:29:32  whwyue
//Fix DSLMWDN logic for model part
//
//Revision 1.111  2019/07/17 12:11:16  whwyue
//Fix DSLMWDN logic for model part
//
//Revision 1.110  2019/07/15 12:42:35  whwyue
//Fix DSLMWDN logic for model part
//
//Revision 1.109  2019/07/08 09:20:20  whwyue
//roll back code for MTP
//
//Revision 1.107  2018/11/14 07:04:17  whwyue
//rock back code for MTP
//
//Revision 1.105  2018/10/18 07:28:27  whwyue
//Roll back code
//
//Revision 1.99  2018/03/13 06:47:04  whwyue
//Fix defect CINCC
//
//Revision 1.97  2018/03/07 15:25:02  whwyue
//Fix defect 1809759
//
//Revision 1.95  2018/03/05 08:58:57  whwyue
//Add debug check FUSAICC
//
//Revision 1.94  2018/03/02 13:10:31  whwyue
//Fix defect FUSAICC
//
//Revision 1.93  2018/02/06 11:52:52  whwyue
//Change special char
//
//Revision 1.90  2018/02/05 09:28:24  whwyue
//Fix for ISLMRFA fields where IFTYPE='T'
//
//Revision 1.88  2018/02/02 08:39:00  whwyue
//Fix ISLMRFA
//
//Revision 1.87  2018/02/02 06:27:26  whwyue
//Fix for ISLMRFA fields where IFTYPE='T'
//
//Revision 1.86  2018/01/22 14:39:03  mmiotto
//Updates to CSLMWCD attribute
//
//Revision 1.84  2018/01/18 20:07:27  mmiotto
//Fix for CPDXA, CSLMWCD, ISLMRFA and FAGRMBE fields
//
//Revision 1.83  2018/01/04 19:30:07  mmiotto
//Update to feature.CSLMWCD code
//
//Revision 1.82  2017/12/21 17:56:36  mmiotto
//Fix to CSLMWCD field
//
//Revision 1.81  2017/12/13 18:01:23  mmiotto
//Restore the code to the most recent version with the removal of special chars and defect fixes
//
//Revision 1.79  2017/12/05 17:25:47  mmiotto
//Restore the most current version, remove special chars
//
//Revision 1.77  2017/09/06 13:47:31  mmiotto
//Remove special chars
//Changes requested by the team
//
//Revision 1.76  2017/06/21 19:02:38  mmiotto
//Minor fix to removeSpecialChars method
//
//Revision 1.75  2017/06/21 19:01:04  mmiotto
//Fix to removeSpecialChars method
//
//Revision 1.74  2017/06/20 18:07:30  mmiotto
//Update to the removal of special chars from invname
//
//Revision 1.73  2017/06/14 19:56:02  mmiotto
//Remove special chars from INVNAME
//
//Revision 1.72  2017/03/22 14:44:26  mmiotto
//Fix for EPIC
//
//Revision 1.71  2017/03/22 13:54:19  mmiotto
//Code fix for EPIC for US and CCN - add EPIC logic
//
//Revision 1.70  2017/03/16 01:17:00  mmiotto
//Updates to support AVAIL EPIC
//
//Revision 1.69  2017/03/03 13:15:17  mmiotto
//Tests for t006 feature
//
//Revision 1.68  2017/03/02 20:42:54  mmiotto
//Add tests to t006Feature
//
//Revision 1.67  2017/02/23 17:17:15  mmiotto
//DSLMWDN fixed for T006FEATURE, remove debug statements
//Code changes to T020NPMesUpgrade.DSLMWDN
//
//Revision 1.66  2017/02/23 14:43:03  mmiotto
//Code updates for validateProdstructs
//
//Revision 1.65  2017/02/23 13:35:18  mmiotto
//Updates to validateProdstructs method
//
//Revision 1.64  2017/02/22 18:48:22  mmiotto
//Code tests
//
//Revision 1.63  2017/02/22 18:11:43  mmiotto
//Code test
//
//Revision 1.62  2017/02/22 17:17:31  mmiotto
//Test different way to pull PRODSTRUCTs from a FEATURE
//
//Revision 1.61  2017/02/22 14:14:37  mmiotto
//Add debug statements
//
//Revision 1.60  2017/02/21 17:55:20  mmiotto
//Add debug statements
//
//Revision 1.59  2017/02/21 15:42:26  mmiotto
//Updates to MODEL.QSMEDMW and MODEL.DUSALRW
//
//Revision 1.58  2017/02/20 18:58:17  mmiotto
//Updates to FEATURE.DSLDSLMWDN
//
//Revision 1.56  2017/02/17 15:19:08  mmiotto
//Updates to FEATURE.PRODSTRUCT withdrawal date
//
//Revision 1.53  2017/02/09 19:55:32  mmiotto
//Withdrawal and Last Order RPQs
//
//Revision 1.52  2017/01/19 19:19:16  mmiotto
//Updates to End of Service and Last Order logic
//
//Revision 1.50  2016/12/20 10:12:08  mmiotto
//Add End of Service and Last Order avail
//
//Revision 1.49  2016/12/02 15:53:07  mmiotto
//Remove debug statements
//
//Revision 1.48  2016/12/01 14:18:23  mmiotto
//Major changes requested by the user
//
//Revision 1.47  2016/11/30 16:22:56  mmiotto
//Updates to FAGRMBE as requested by the user
//
//Revision 1.46  2016/11/29 17:01:10  mmiotto
//Fix DSLMMES logic
//
//Revision 1.45  2016/11/29 16:51:44  mmiotto
//Fix DSLMMES logic
//
//Revision 1.44  2016/11/29 14:38:02  mmiotto
//Add debug for DSLMMES
//
//Revision 1.43  2016/11/28 21:12:12  mmiotto
//Fix for some attributes
//Added file handling - create in one dir, copy to ftp dir when complete
//
//Revision 1.42  2016/11/11 18:50:01  mmiotto
//Fix output file name
//
//Revision 1.41  2016/11/11 16:03:09  mmiotto
//Update output file name.
//Remove checks on GENAREA and replace by checks on QSMGEO
//
//Revision 1.40  2016/11/11 00:55:19  mmiotto
//Updates requested by the user to:
//SYSDES
//DSLMMES
//and QSMGEO attribute
//
//Revision 1.39  2016/11/04 15:15:17  mmiotto
//Changes requested by the user
//
//Revision 1.38  2016/11/03 20:32:47  mmiotto
//Changes requested by the user
//
//Revision 1.37  2016/11/03 11:21:37  mmiotto
//Updates requested by the user to FSLMMES, QSLMCSU, DSLMGAD and SYSDES
//
//Revision 1.36  2016/10/20 22:36:05  mmiotto
//Minor fix for t006 feature
//
//Revision 1.35  2016/10/20 21:34:10  mmiotto
//Fix to a date attribute
//
//Revision 1.34  2016/10/20 19:15:02  mmiotto
//Changes requested by the user
//
//Revision 1.33  2016/10/19 21:04:47  mmiotto
//Last set of updates requested by the user
//Add logic for PRODID new flag values
//Add logic for METHPROD attribute
//
//Revision 1.32  2016/10/19 19:09:00  mmiotto
//Changes requested by the user for INSTALL attribute
//
//Revision 1.31  2016/10/19 17:41:56  mmiotto
//Fix to model and geomod attribute mapping
//
//Revision 1.30  2016/10/19 17:14:51  mmiotto
//Fix for GEOMOD attributes and getNumValue method
//
//Revision 1.29  2016/10/19 01:08:45  mmiotto
//Code changes to t017
//
//Revision 1.28  2016/10/19 00:23:44  mmiotto
//Code fix to t017 record
//
//Revision 1.27  2016/10/18 23:19:52  mmiotto
//Added debug statements
//
//Revision 1.26  2016/10/18 22:34:00  mmiotto
//Code reorg to work with new VEs
//
//Revision 1.25  2016/10/18 19:40:30  mmiotto
//Test version - updates to use EntityGroup instead of vector
//
//Revision 1.24  2016/10/18 13:02:50  mmiotto
//Test version with some fixes
//
//Revision 1.23  2016/10/18 12:33:36  mmiotto
//Test version
//
//Revision 1.22  2016/10/11 17:01:23  mmiotto
//Remove debug statements - code cleanup
//
//Revision 1.21  2016/10/11 13:42:01  mmiotto
//User requested changes
//
//Revision 1.20  2016/10/08 22:46:40  mmiotto
//Code cleanup
//
//Revision 1.19  2016/09/30 20:42:47  mmiotto
//Debug WARR entity and Type Feature FUSAEDE attribute
//
//Revision 1.18  2016/09/30 00:04:41  mmiotto
//Fix Release To date field
//
//Revision 1.17  2016/09/27 02:41:15  mmiotto
//Remove lock when completed
//
//Revision 1.16  2016/09/26 23:59:16  mmiotto
//Fix geoModVect for loop
//
//Revision 1.15  2016/09/26 23:08:44  mmiotto
//Add debug PokUtils.outputList to display what is returned by each VE
//
//Revision 1.14  2016/09/26 21:32:14  mmiotto
//Minor changes to the code, analysing the empty report issue
//
//Revision 1.13  2016/09/26 15:24:20  mmiotto
//Remove debug statements
//
//Revision 1.12  2016/09/25 02:16:42  mmiotto
//Fixes to GEOMOD entity selection
//Multiple fixes to T002 MODEL
//Multiple fixes to T006 FEATURE
//Minor fixes to MODELCONVERT
//Minor fixes to T632 TMF
//
//Revision 1.11  2016/09/24 04:26:46  mmiotto
//Fix Warranty ID to be ignored
//Changes to MODELGEOMOD logic for T002
//
//Revision 1.10  2016/09/24 03:14:37  mmiotto
//Fixed MODEL to WARR, STDMAINT and SGMNTACRNYM relators
//Fix to MODELCONVERT
//Fix to FUSAEDE field
//
//Revision 1.9  2016/09/22 06:02:30  mmiotto
//List of fixes
//- Multiple GEOs selected for an AVAIL for all record types
//- ISLMRFA value for all record types
//- Multiple fixes for Type Model
//
//Revision 1.8  2016/09/11 21:49:28  mmiotto
//Update the code to use AVAIL.GENAREASELECTION
//Code update to handle Worlwide GEO
//
//Revision 1.7  2016/09/08 19:57:25  mmiotto
//Add cvs log
//Following changes according to ePIMS code
//1 - Type R is only sent for AP
//2 - Type T is only sent for US
//
// Revision 1.1  2016-05-27 16:38:00  mmiotto
// OIM Simplification project is to sunset ePIMS HW   and MMLC (SAP) systems. In order to sunset ePIMS HW we need to replace ePIMS HW -> QSM feed to EACM.
//
//
/**
 * @author mmiotto
 *
 */
public class QSMFULLABRSTATUS extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);

	public static int DEBUG_LVL = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
			.getABRDebugLevel("QSMFULLABRSTATUS");

	private ResourceBundle rsBundle = null;
	private Hashtable metaTbl = new Hashtable();
	private String navName = "";

	private String ffFileName = null;
	private String ffPathName = null;
	private String ffFTPPathName = null;
	private String dir = null;
	private String dirDest = null;
	private final String QSMRPTPATH = "_rptpath";
	private final String QSMGENPATH = "_genpath";
	private final String QSMFTPPATH = "_ftppath";
	private final String CHUNKSIZE = "_chunksize";
	private int abr_debuglvl = D.EBUG_ERR;
	private static final String CREFINIPATH = "_inipath";
	private static final String FTPSCRPATH = "_script";
	private static final String TARGETFILENAME = "_targetfilename";
	private static final String LOGPATH = "_logpath";
	private static final String BACKUPPATH = "_backuppath";
	private String fileprefix = null;
	private String lineStr = "";
	private int chunkSize = 0;
	private Set availSet = null;
	private static final String FAILD = "FAILD";
	private static final String IFTYPE = "IFTYPE";
	private static final String IOPUCTY = "IOPUCTY";
	private static final String ISLMPAL = "ISLMPAL";
	private static final String ISLMRFA = "ISLMRFA";
	private static final String ISLMPRN = "ISLMPRN";
	private static final String CSLMPCI = "CSLMPCI";
	private static final String IPRTNUM = "IPRTNUM";
	private static final String FPUNINC = "FPUNINC";
	private static final String CAOAV = "CAOAV";
	private static final String DSLMCPA = "DSLMCPA";
	private static final String DSLMCPO = "DSLMCPO";
	private static final String DSLMGAD = "DSLMGAD";
	private static final String DSLMMVA = "DSLMMVA";
	private static final String DSLMOPD = "DSLMOPD";
	private static final String DSLMWDN = "DSLMWDN";
	private static final String QSMEDMW = "QSMEDMW";
	private static final String ASLMMVP = "ASLMMVP";
	private static final String CCUOICR = "CCUOICR";
	private static final String CICIB = "CICIB";
	private static final String CICIC = "CICIC";
	private static final String CICRY = "CICRY";
	private static final String CIDCJ = "CIDCJ";
	private static final String CIDXF = "CIDXF";
	private static final String CINCA = "CINCA";
	private static final String CINCB = "CINCB";
	private static final String CINCC = "CINCC";
	private static final String CINPM = "CINPM";
	private static final String CISUP = "CISUP";
	private static final String CITEM = "CITEM";
	private static final String CJLBIC1 = "CJLBIC1";
	private static final String CJLBIDS = "CJLBIDS";
	private static final String CJLBOEM = "CJLBOEM";
	private static final String CJLBPOF = "CJLBPOF";
	private static final String CJLBSAC = "CJLBSAC";
	private static final String CLASSPT = "CLASSPT";
	private static final String CPDAA = "CPDAA";
	private static final String CSLMFCC = "CSLMFCC";
	private static final String CSLMGGC = "CSLMGGC";
	private static final String CSLMIDP = "CSLMIDP";
	private static final String CSLMLRP = "CSLMLRP";
	private static final String CSLMSAS = "CSLMSAS";
	private static final String CSLMSYT = "CSLMSYT";
	private static final String CSLMWCD = "CSLMWCD";
	private static final String FAGRMBE = "FAGRMBE";
	private static final String FCUOCNF = "FCUOCNF";
	private static final String FSLMCLS = "FSLMCLS";
	private static final String FSLMCPU = "FSLMCPU";
	private static final String FSLMIOP = "FSLMIOP";
	private static final String FSLMLGS = "FSLMLGS";
	private static final String FSLMMLC = "FSLMMLC";
	private static final String FSLMPOP = "FSLMPOP";
	private static final String FSLMVDE = "FSLMVDE";
	private static final String FSLMVTS = "FSLMVTS";
	private static final String FSLM2CF = "FSLM2CF";
	private static final String ICESPCC = "ICESPCC";
	private static final String IDORIG = "IDORIG";
	private static final String IOLCPLM = "IOLCPLM";
	private static final String PCUAHEA = "PCUAHEA";
	private static final String PCUASEA = "PCUASEA";
	private static final String PCUAUEA = "PCUAUEA";
	private static final String QSLMCSU = "QSLMCSU";
	private static final String QSMXANN = "QSMXANN";
	private static final String QSMXESA = "QSMXESA";
	private static final String QSMXSSA = "QSMXSSA";
	private static final String SYSDES = "SYSDES";
	private static final String TSLMDES = "TSLMDES";
	private static final String TSLTDES = "TSLTDES";
	private static final String TIMSTMP = "TIMSTMP";
	private static final String USERID = "USERID";
	private static final String FBRAND = "FBRAND";
	private static final String FSLMHVP = "FSLMHVP";
	private static final String FSLMCVP = "FSLMCVP";
	private static final String FSLMMES = "FSLMMES";
	private static final String CSLMTM1 = "CSLMTM1";
	private static final String CSLMTM2 = "CSLMTM2";
	private static final String CSLMTM3 = "CSLMTM3";
	private static final String CSLMTM4 = "CSLMTM4";
	private static final String CSLMTM5 = "CSLMTM5";
	private static final String CSLMTM6 = "CSLMTM6";
	private static final String CSLMTM7 = "CSLMTM7";
	private static final String CSLMTM8 = "CSLMTM8";
	private static final String FSAPRES = "FSAPRES";
	private static final String CUSAPMS = "CUSAPMS";
	private static final String DUSALRW = "DUSALRW";
	private static final String DUSAMDW = "DUSAMDW";
	private static final String DUSAWUW = "DUSAWUW";
	private static final String FSLMCBL = "FSLMCBL";
	private static final String FSLMMRR = "FSLMMRR";
	private static final String FUSAAAS = "FUSAAAS";
	private static final String FUSAADM = "FUSAADM";
	private static final String FUSAEDE = "FUSAEDE";
	private static final String FUSAICC = "FUSAICC";
	private static final String FUSALEP = "FUSALEP";
	private static final String FUSAMRS = "FUSAMRS";
	private static final String FUSAVLM = "FUSAVLM";
	private static final String FUSAXMO = "FUSAXMO";
	private static final String QUSAPOP = "QUSAPOP";
	private static final String DSLMEOD = "DSLMEOD";
	private static final String FSLMRFM = "FSLMRFM";
	private static final Hashtable COLUMN_LENGTH;

	private static final String DSLMMES = "DSLMMES";
	private static final String CIDXC = "CIDXC";
	private static final String CSLMFTY = "CSLMFTY";
	private static final String CVOAT = "CVOAT";
	private static final String FSLMPIO = "FSLMPIO";
	private static final String FSLMSTK = "FSLMSTK";
	private static final String PCUAEAP = "PCUAEAP";
	private static final String POGMES = "POGMES";
	private static final String STSPCFT = "STSPCFT";
	private static final String FUSAIRR = "FUSAIRR";
	private static final String CPDXA = "CPDXA";
	private static final String DSLMEFF = "DSLMEFF";
	private static final String CSLMRCH = "CSLMRCH";
	private static final String CSLMNUM = "CSLMNUM";
	private static final String FSLMAPG = "FSLMAPG";
	private static final String FSLMASP = "FSLMASP";
	private static final String FSLMJAP = "FSLMJAP";
	private static final String FSLMAUS = "FSLMAUS";
	private static final String FSLMBGL = "FSLMBGL";
	private static final String FSLMBRU = "FSLMBRU";
	private static final String FSLMHKG = "FSLMHKG";
	private static final String FSLMIDN = "FSLMIDN";
	private static final String FSLMIND = "FSLMIND";
	private static final String FSLMKOR = "FSLMKOR";
	private static final String FSLMMAC = "FSLMMAC";
	private static final String FSLMMAL = "FSLMMAL";
	private static final String FSLMMYA = "FSLMMYA";
	private static final String FSLMNZL = "FSLMNZL";
	private static final String FSLMPHI = "FSLMPHI";
	private static final String FSLMPRC = "FSLMPRC";
	private static final String FSLMSLA = "FSLMSLA";
	private static final String FSLMSNG = "FSLMSNG";
	private static final String FSLMTAI = "FSLMTAI";
	private static final String FSLMTHA = "FSLMTHA";
	private static final String ISLMTYP = "ISLMTYP";
	private static final String ISLMMOD = "ISLMMOD";
	private static final String ISLMFTR = "ISLMFTR";
	private static final String ISLMXX1 = "ISLMXX1";
	private static final String QSMNPMT = "QSMNPMT";
	private static final String QSMNPMM = "QSMNPMM";
	private HashMap fidMap = new HashMap();
	private static final List geoWWList = Collections.unmodifiableList(new ArrayList() {
		{
			add("Asia Pacific");
			add("Canada and Caribbean North");
			add("Europe/Middle East/Africa");
			add("Latin America");
			add("US Only");
		}
	});

	/**
	 * Record format defintion Form (record name), (record length), <starting
	 * postion>, <type>
	 */
	static {
		COLUMN_LENGTH = new Hashtable();
		COLUMN_LENGTH.put(IFTYPE, "1");
		COLUMN_LENGTH.put(IOPUCTY, "3");
		COLUMN_LENGTH.put(ISLMPAL, "8");
		COLUMN_LENGTH.put(ISLMRFA, "6");
		COLUMN_LENGTH.put(ISLMPRN, "14");
		COLUMN_LENGTH.put(CSLMPCI, "2");
		COLUMN_LENGTH.put(IPRTNUM, "12");
		COLUMN_LENGTH.put(FPUNINC, "1");
		COLUMN_LENGTH.put(CAOAV, "1");
		COLUMN_LENGTH.put(DSLMCPA, "10");
		COLUMN_LENGTH.put(DSLMCPO, "10");
		COLUMN_LENGTH.put(DSLMGAD, "10");
		COLUMN_LENGTH.put(DSLMMVA, "10");
		COLUMN_LENGTH.put(DSLMOPD, "10");
		COLUMN_LENGTH.put(DSLMWDN, "10");
		COLUMN_LENGTH.put(QSMEDMW, "10");
		COLUMN_LENGTH.put(ASLMMVP, "4");
		COLUMN_LENGTH.put(CCUOICR, "1");
		COLUMN_LENGTH.put(CICIB, "1");
		COLUMN_LENGTH.put(CICIC, "1");
		COLUMN_LENGTH.put(CICRY, "1");
		COLUMN_LENGTH.put(CIDCJ, "1");
		COLUMN_LENGTH.put(CIDXF, "1");
		COLUMN_LENGTH.put(CINCA, "1");
		COLUMN_LENGTH.put(CINCB, "1");
		COLUMN_LENGTH.put(CINCC, "1");
		COLUMN_LENGTH.put(CINPM, "1");
		COLUMN_LENGTH.put(CISUP, "1");
		COLUMN_LENGTH.put(CITEM, "1");
		COLUMN_LENGTH.put(CJLBIC1, "2");
		COLUMN_LENGTH.put(CJLBIDS, "1");
		COLUMN_LENGTH.put(CJLBOEM, "1");
		COLUMN_LENGTH.put(CJLBPOF, "1");
		COLUMN_LENGTH.put(CJLBSAC, "3");
		COLUMN_LENGTH.put(CLASSPT, "3");
		COLUMN_LENGTH.put(CPDAA, "1");
		COLUMN_LENGTH.put(CSLMFCC, "4");
		COLUMN_LENGTH.put(CSLMGGC, "2");
		COLUMN_LENGTH.put(CSLMIDP, "1");
		COLUMN_LENGTH.put(CSLMLRP, "1");
		COLUMN_LENGTH.put(CSLMSAS, "1");
		COLUMN_LENGTH.put(CSLMSYT, "5");
		COLUMN_LENGTH.put(CSLMWCD, "1");
		COLUMN_LENGTH.put(FAGRMBE, "1");
		COLUMN_LENGTH.put(FCUOCNF, "1");
		COLUMN_LENGTH.put(FSLMCLS, "1");
		COLUMN_LENGTH.put(FSLMCPU, "1");
		COLUMN_LENGTH.put(FSLMIOP, "1");
		COLUMN_LENGTH.put(FSLMLGS, "1");
		COLUMN_LENGTH.put(FSLMMLC, "1");
		COLUMN_LENGTH.put(FSLMPOP, "1");
		COLUMN_LENGTH.put(FSLMVDE, "1");
		COLUMN_LENGTH.put(FSLMVTS, "1");
		COLUMN_LENGTH.put(FSLM2CF, "1");
		COLUMN_LENGTH.put(ICESPCC, "1");
		COLUMN_LENGTH.put(IDORIG, "3");
		COLUMN_LENGTH.put(IOLCPLM, "2");
		COLUMN_LENGTH.put(PCUAHEA, "3");
		COLUMN_LENGTH.put(PCUASEA, "3");
		COLUMN_LENGTH.put(PCUAUEA, "3");
		COLUMN_LENGTH.put(QSLMCSU, "2");
		COLUMN_LENGTH.put(QSMXANN, "1");
		COLUMN_LENGTH.put(QSMXESA, "1");
		COLUMN_LENGTH.put(QSMXSSA, "1");
		COLUMN_LENGTH.put(SYSDES, "30");
		COLUMN_LENGTH.put(TSLMDES, "30");
		COLUMN_LENGTH.put(TSLTDES, "56");
		COLUMN_LENGTH.put(TIMSTMP, "26");
		COLUMN_LENGTH.put(USERID, "8");
		COLUMN_LENGTH.put(FBRAND, "1");
		COLUMN_LENGTH.put(FSLMHVP, "1");
		COLUMN_LENGTH.put(FSLMCVP, "1");
		COLUMN_LENGTH.put(FSLMMES, "1");
		COLUMN_LENGTH.put(CSLMTM1, "3");
		COLUMN_LENGTH.put(CSLMTM2, "3");
		COLUMN_LENGTH.put(CSLMTM3, "3");
		COLUMN_LENGTH.put(CSLMTM4, "3");
		COLUMN_LENGTH.put(CSLMTM5, "3");
		COLUMN_LENGTH.put(CSLMTM6, "3");
		COLUMN_LENGTH.put(CSLMTM7, "3");
		COLUMN_LENGTH.put(CSLMTM8, "3");
		COLUMN_LENGTH.put(FSAPRES, "1");
		COLUMN_LENGTH.put(CUSAPMS, "1");
		COLUMN_LENGTH.put(DUSALRW, "10");
		COLUMN_LENGTH.put(DUSAMDW, "10");
		COLUMN_LENGTH.put(DUSAWUW, "10");
		COLUMN_LENGTH.put(FSLMCBL, "1");
		COLUMN_LENGTH.put(FSLMMRR, "1");
		COLUMN_LENGTH.put(FUSAAAS, "1");
		COLUMN_LENGTH.put(FUSAADM, "1");
		COLUMN_LENGTH.put(FUSAEDE, "1");
		COLUMN_LENGTH.put(FUSAICC, "1");
		COLUMN_LENGTH.put(FUSALEP, "1");
		COLUMN_LENGTH.put(FUSAMRS, "1");
		COLUMN_LENGTH.put(FUSAVLM, "1");
		COLUMN_LENGTH.put(FUSAXMO, "1");
		COLUMN_LENGTH.put(QUSAPOP, "4");
		COLUMN_LENGTH.put(DSLMEOD, "10");
		COLUMN_LENGTH.put(FSLMRFM, "1");
		COLUMN_LENGTH.put(DSLMMES, "10");
		COLUMN_LENGTH.put(CIDXC, "1");
		COLUMN_LENGTH.put(CSLMFTY, "2");
		COLUMN_LENGTH.put(CVOAT, "1");
		COLUMN_LENGTH.put(FSLMPIO, "1");
		COLUMN_LENGTH.put(FSLMSTK, "1");
		COLUMN_LENGTH.put(PCUAEAP, "3");
		COLUMN_LENGTH.put(POGMES, "10");
		COLUMN_LENGTH.put(STSPCFT, "4");
		COLUMN_LENGTH.put(FUSAIRR, "1");
		COLUMN_LENGTH.put(CPDXA, "2");
		COLUMN_LENGTH.put(DSLMEFF, "10");
		COLUMN_LENGTH.put(CSLMRCH, "1");
		COLUMN_LENGTH.put(CSLMNUM, "6");
		COLUMN_LENGTH.put(FSLMAPG, "1");
		COLUMN_LENGTH.put(FSLMASP, "1");
		COLUMN_LENGTH.put(FSLMJAP, "1");
		COLUMN_LENGTH.put(FSLMAUS, "1");
		COLUMN_LENGTH.put(FSLMBGL, "1");
		COLUMN_LENGTH.put(FSLMBRU, "1");
		COLUMN_LENGTH.put(FSLMHKG, "1");
		COLUMN_LENGTH.put(FSLMIDN, "1");
		COLUMN_LENGTH.put(FSLMIND, "1");
		COLUMN_LENGTH.put(FSLMKOR, "1");
		COLUMN_LENGTH.put(FSLMMAC, "1");
		COLUMN_LENGTH.put(FSLMMAL, "1");
		COLUMN_LENGTH.put(FSLMMYA, "1");
		COLUMN_LENGTH.put(FSLMNZL, "1");
		COLUMN_LENGTH.put(FSLMPHI, "1");
		COLUMN_LENGTH.put(FSLMPRC, "1");
		COLUMN_LENGTH.put(FSLMSLA, "1");
		COLUMN_LENGTH.put(FSLMSNG, "1");
		COLUMN_LENGTH.put(FSLMTAI, "1");
		COLUMN_LENGTH.put(FSLMTHA, "1");
		COLUMN_LENGTH.put(ISLMTYP, "4");
		COLUMN_LENGTH.put(ISLMMOD, "3");
		COLUMN_LENGTH.put(ISLMFTR, "6");
		COLUMN_LENGTH.put(ISLMXX1, "1");
		COLUMN_LENGTH.put(QSMNPMT, "4");
		COLUMN_LENGTH.put(QSMNPMM, "3");
	}

	/**********************************
	 * get the resource bundle
	 */
	protected ResourceBundle getBundle() {
		return rsBundle;
	}

	/**********************************
	 * Execute ABR.
	 *
	 */
	public void execute_run() {
		String HEADER = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE
				+ EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">"
				+ EACustom.getMastheadDiv() + NEWLINE
				+ "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
		String HEADER2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE
				+ "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>"
				+ NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE
				+ "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->"
				+ NEWLINE;

		String header1 = "";
		boolean creatFile = true;
		boolean sentFile = true;

		MessageFormat msgf;
		String abrversion = "";

		Object[] args = new String[10];

		try {
			msgf = new MessageFormat(HEADER);
			args[0] = getShortClassName(getClass());
			args[1] = "ABR";
			header1 = msgf.format(args);
			// Default set to pass
			setReturnCode(PASS);

			try {
				String chunkStr = ABRServerProperties.getValue(m_abri.getABRCode(), CHUNKSIZE, "500");
				chunkSize = Integer.parseInt(chunkStr.trim());

			} catch (Exception e) {
				// TODO: handle exception
				chunkSize = 500;
			}

			start_ABRBuild(false); // don't pull the VE yet

			abr_debuglvl = COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties
					.getABRDebugLevel(m_abri.getABRCode());

			// get the root entity using current timestamp, need this to get the
			// timestamps or info for VE pulls
			m_elist = getEntityList(getT002ModelVEName());

			EntityItem rootEntity = m_elist.getParentEntityGroup().getEntityItem(0);

			if (m_elist.getEntityGroupCount() > 0) {
				// NAME is navigate attributes - only used if error rpt is
				// generated
				navName = getNavigationName();
				setDGTitle(navName);
				setDGString(getABRReturnCode());
				setDGRptName("QSMFULLABRSTATUS"); // Set the report name
				setDGRptClass(getABRCode()); // Set the report class
				generateFlatFile(rootEntity);
				exeFtpShell(ffPathName);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// println(e.toString());
			setReturnCode(FAIL);
			java.io.StringWriter exBuf = new java.io.StringWriter();
			String Error_EXCEPTION = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
			String Error_STACKTRACE = "<pre>{0}</pre>";
			msgf = new MessageFormat(Error_EXCEPTION);
			setReturnCode(INTERNAL_ERROR);
			e.printStackTrace(new java.io.PrintWriter(exBuf));
			// Put exception into document
			args[0] = e.getMessage();
			rptSb.append(msgf.format(args) + NEWLINE);
			msgf = new MessageFormat(Error_STACKTRACE);
			args[0] = exBuf.getBuffer().toString();
			rptSb.append(msgf.format(args) + NEWLINE);
			logError("Exception: " + e.getMessage());
			logError(exBuf.getBuffer().toString());
			// was an error make sure user gets report
			setCreateDGEntity(true);
			creatFile = false;
			// sentFile=exeFtpShell(ffPathName);
		} finally {
			if (!isReadOnly()) {
				clearSoftLock();
			}

			StringBuffer sb = new StringBuffer();
			msgf = new MessageFormat(HEADER2);
			args[0] = m_prof.getOPName();
			args[1] = m_prof.getRoleDescription();
			args[2] = m_prof.getWGName();
			args[3] = getNow();
			sb.append(creatFile ? "generated the QSM report file successful " : "generated the QSM report file faild");
			sb.append(",");
			sb.append(sentFile ? "send the QSM report file successful " : "sent the QSM report file faild");
			args[4] = sb.toString();
			args[5] = abrversion + " " + getABRVersion();

			rptSb.insert(0, header1 + msgf.format(args) + NEWLINE);

			println(EACustom.getDocTypeHtml()); // Output the doctype and html
			println(rptSb.toString()); // Output the Report
			printDGSubmitString();

			println(EACustom.getTOUDiv());
			buildReportFooter(); // Print </html>
		}
	}

	/********************************************
	 * CQ00016165 The Files will be named as follows: 1. 'EACMFEEDQSMABR_' 2.
	 * The DB2 DTS for T2 with spaces and special characters replaced with an
	 * underscore 3. '.txt'
	 */
	private void setFileName(EntityItem rootEntity) {
		// FILE_PREFIX=EACMFEEDQSMFULLABR_
		fileprefix = ABRServerProperties.getFilePrefix(m_abri.getABRCode());
		// ABRServerProperties.getOutputPath()
		// ABRServerProperties.get
		StringBuffer sb = new StringBuffer(fileprefix.trim());
		String dts = getNow();
		// replace special characters
		dts = dts.replace(' ', '_');
		sb.append(rootEntity.getEntityType() + rootEntity.getEntityID() + "_");
		sb.append(dts + ".txt");
		dir = ABRServerProperties.getValue(m_abri.getABRCode(), QSMGENPATH, "/Dgq");
		if (!dir.endsWith("/")) {
			dir = dir + "/";
		}
		ffFileName = sb.toString();
		ffPathName = dir + ffFileName;
	}

	/********************************************
	 * Build a text file using all The records are data based on the type of
	 * data described above.
	 * 
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 * @throws ParseException
	 */
	private void generateFlatFile(EntityItem rootEntity)
			throws IOException, SQLException, MiddlewareException, ParseException {

		FileChannel sourceChannel = null;
		FileChannel destChannel = null;

		// generate file name
		setFileName(rootEntity);

		FileOutputStream fos = new FileOutputStream(ffPathName);
		// OutputStreamWriter will convert from characters to bytes using
		// the specified character encoding or the platform default if none
		// is specified. Output as unicode
		OutputStreamWriter wOut = new OutputStreamWriter(fos, "UTF-8");

		// build the text file
		long current = System.currentTimeMillis();

		addDebug("Processing MODEL:" + new Date().toLocaleString());
		createT002Model(rootEntity, wOut);
		addDebug("Processing T006Feature" + new Date().toLocaleString());
		createT006Feature(rootEntity, wOut);
		addDebug("Processing T017ProductCategory" + new Date().toLocaleString());
		createT017ProductCategory(rootEntity, wOut);
		addDebug("Processing T020NPMesUpgrade" + new Date().toLocaleString());
		createT020NPMesUpgrade(rootEntity, wOut);
		addDebug("Processing 512ReleaseTo" + new Date().toLocaleString());
		createT512ReleaseTo(rootEntity, wOut);
		addDebug("Processing TypeModelFeatureRelation" + new Date().toLocaleString());
		createT632TypeModelFeatureRelation(rootEntity, wOut);
		addDebug("Processing Done" + new Date().toLocaleString());
		wOut.close();

		dirDest = ABRServerProperties.getValue(m_abri.getABRCode(), QSMFTPPATH, "/Dgq");
		if (!dirDest.endsWith("/")) {
			dirDest = dirDest + "/";
		}

		ffFTPPathName = dirDest + ffFileName;
		addDebug("******* " + ffFTPPathName);

		try {
			sourceChannel = new FileInputStream(ffPathName).getChannel();
			destChannel = new FileOutputStream(ffFTPPathName).getChannel();
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
		} finally {
			sourceChannel.close();
			destChannel.close();
		}

	}

	/*
	 * Create T002 model records
	 * 
	 * @throws IOException
	 */
	private void createT002Model(EntityItem rootEntity, OutputStreamWriter wOut) throws IOException {

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		String strAvailType = "";
		String strAvailAnnType = "";
		boolean isEpic = false;

		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {

			EntityItem availEI = availGrp.getEntityItem(availI);

			strAvailType = PokUtils.getAttributeValue(availEI, "AVAILTYPE", "", "");
			strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")) {
				isEpic = true;
			}

			// ******* NEW *******
			if ((strAvailType.equals("Planned Availability")) || (strAvailType.equals("End of Service"))
					|| (strAvailType.equals("Last Order"))) {
				EANFlagAttribute qsmGeoList = (EANFlagAttribute) availEI.getAttribute("QSMGEO");

				if (qsmGeoList != null) {
					if (qsmGeoList.isSelected("6199")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Asia Pacific", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6200")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Canada and Caribbean North", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6198")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Europe/Middle East/Africa", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6204")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "Latin America", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6221")) {
						createT002ModelRecords(rootEntity, wOut, availEI, "US Only", strAvailType, isEpic);
					}
				}
			}
		}
	}

	/*
	 * Create records for ANNOUNCEMENT -> AVAIL -> MODELAVAIL -> MODEL
	 * 
	 * @throws IOException
	 */
	private void createT002ModelRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem availEI,
			String strAvailGenAreaSel, String strMainAvailType, boolean isEpic) throws IOException {

		StringBuffer sb;
		String geoModGraduatedCharge;
		String ccuoicrValue;
		String geoModPurchOnly;
		String geoModPlntofmr;
		String geoModIntegratedmodel;
		String geoModPercallcls;
		String geoModAnnualMaint;
		String geoModEmeaBrandCode;
		String strIOPUCTY;
		String strISLMPAL;
		String strFSLM2CF;
		String strQSLMCSU;
		String strFUSAAAS;
		String strFUSAICC;
		String strCSLMTM1;
		String strCSLMTM2;
		String strCSLMTM3;
		String strCSLMTM4;
		String strCSLMTM5;
		String strCSLMTM6;
		String strCSLMTM7;
		String strCSLMTM8;
		String strFAGRMBE;
		String strPCUAHEA;
		String strPCUASEA;
		String strPCUAUEA;
		String strPrcIndc;
		String strISLMRFA;
		String strFUSAEDE;
		String strCSLMIDP;
		String geoModMethodProd;
		String strFSLMHVP;
		// String strCINCC;
		String strQSMEDMW;
		String strDUSALRW;
		String strDSLMWDN;
		String strDSLMOPD;
		String strCPDAA;
		String strFSLMCVP;
		String strCSLMWCD;
		String strWARRSVCCOVR;
		String strIBMCREDIT;
		String strWTHDRWEFFCTVDATE;

		Vector modelAvailVect = PokUtils.getAllLinkedEntities(availEI, "MODELAVAIL", "MODEL");

		for (int i = 0; i < modelAvailVect.size(); i++) {
			sb = new StringBuffer();
			geoModGraduatedCharge = "";
			ccuoicrValue = "";
			geoModPurchOnly = "";
			geoModPlntofmr = "";
			geoModIntegratedmodel = "";
			geoModPercallcls = "";
			geoModAnnualMaint = "";
			geoModEmeaBrandCode = "";
			strIOPUCTY = "";
			strISLMPAL = "";
			strFSLM2CF = "";
			strQSLMCSU = "";
			strFUSAAAS = "";
			strFUSAICC = "";
			strCSLMTM1 = "";
			strCSLMTM2 = "";
			strCSLMTM3 = "";
			strCSLMTM4 = "";
			strCSLMTM5 = "";
			strCSLMTM6 = "";
			strCSLMTM7 = "";
			strCSLMTM8 = "";
			strFAGRMBE = "";
			strPCUAHEA = "";
			strPCUASEA = "";
			strPCUAUEA = "";
			strPrcIndc = "";
			strISLMRFA = "";
			strFUSAEDE = "";
			strCSLMIDP = "";
			geoModMethodProd = "";
			strFSLMHVP = "";
			// strCINCC = "";
			strQSMEDMW = "";
			strDUSALRW = "";
			strDSLMWDN = "";
			strDSLMOPD = "";
			strCPDAA = "";
			strFSLMCVP = "";
			strCSLMWCD = "";
			strWARRSVCCOVR = "";
			strIBMCREDIT = "";
			strWTHDRWEFFCTVDATE = "";

			EntityItem eiModel = (EntityItem) modelAvailVect.elementAt(i);

			EntityItem eiSearchAvail = null;

			if (strAvailGenAreaSel.equals("Asia Pacific") || strAvailGenAreaSel.equals("US Only")
					|| strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strFAGRMBE = "N";
				// strCINCC = "N";
			} else {
				Vector modelStdMaintVect = PokUtils.getAllLinkedEntities(eiModel, "MODELSTDMAINT", "STDMAINT");
				if (!modelStdMaintVect.isEmpty()) {
					EntityItem eiStdMaint = (EntityItem) modelStdMaintVect.elementAt(0);
					if (eiStdMaint != null) {
						strFAGRMBE = PokUtils.getAttributeValue(eiStdMaint, "MAINTELIG", "", "");
					}
				}
				// if(strFAGRMBE.equals("Yes")){
				// strCINCC = "N";
				// } else if (strFAGRMBE.equals("No")){
				// strCINCC = "Y";
				// }
			}

			sb.append(getValue(IFTYPE, "M"));

			String strGenArea = "";
			String strDSLMWDNLA = "";
			String strDSLMOPDLA = "";
			String strDSLMWDNEMEA = "";
			String strDSLMOPDEMEA = "";
			String strDSLMWDNAP = "";
			String strDSLMOPDAP = "";
			String strDSLMWDNUS = "";
			String strDSLMOPDUS = "";
			String strDSLMWDNCCN = "";
			String strDSLMOPDCCN = "";
			EntityItem eiSearchAvail1 = null;
			String strDSLMOPD1 = "";
			String strDSLMWDN1 = "";

			List eiSearchAvailList = searchForAvailType(eiModel, "Last Order");
			for (int j = 0; j < eiSearchAvailList.size(); j++) {
				eiSearchAvail1 = (EntityItem) eiSearchAvailList.get(j);
				addDebug("*****avail list= " + eiSearchAvail1);
				if (eiSearchAvail1 != null) {
					strGenArea = PokUtils.getAttributeValue(eiSearchAvail1, "GENAREASELECTION", ",", "");
					addDebug("*****GenArea list= " + strGenArea);
					if (strGenArea.indexOf("Latin America") != -1) {
						strDSLMWDNLA = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strDSLMOPDLA = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strCPDAA = "O";
						addDebug("*****WD date LA= " + strDSLMWDNLA);
						addDebug("*****WD date LA= " + strDSLMOPDLA);
					}
					if (strGenArea.indexOf("Europe/Middle East/Africa") != -1) {
						strDSLMWDNEMEA = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strDSLMOPDEMEA = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strCPDAA = "O";
						addDebug("*****WD date EMEA= " + strDSLMWDNEMEA);
						addDebug("*****WD date EMEA= " + strDSLMOPDEMEA);
					}
					if (strGenArea.indexOf("Asia Pacific") != -1) {
						strDSLMWDNAP = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strDSLMOPDAP = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strCPDAA = "O";
						addDebug("*****WD date AP= " + strDSLMWDNAP);
						addDebug("*****WD date AP= " + strDSLMOPDAP);
					}
					if (strGenArea.indexOf("US Only") != -1) {
						strDSLMWDNUS = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strDSLMOPDUS = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strCPDAA = "O";
						addDebug("*****WD date US= " + strDSLMWDNUS);
						addDebug("*****WD date US= " + strDSLMOPDUS);
					}
					if (strGenArea.indexOf("Canada and Caribbean North") != -1) {
						strDSLMWDNCCN = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strDSLMOPDCCN = PokUtils.getAttributeValue(eiSearchAvail1, "EFFECTIVEDATE", "", "");
						strCPDAA = "O";
						addDebug("*****WD date CCN= " + strDSLMWDNCCN);
						addDebug("*****WD date US= " + strDSLMOPDCCN);
					}
				} else {
					strDSLMWDN = "2050-12-31";
					strDSLMOPD = "2050-12-31";
					strCPDAA = "N";
				}
			}

			if (strAvailGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "LDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
				strDSLMWDN1 = strDSLMWDNLA;
				addDebug("*****WD LA= " + strDSLMWDN1);
				strDSLMOPD1 = strDSLMOPDLA;
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "EDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
				strDSLMWDN1 = strDSLMWDNEMEA;
				addDebug("*****WD EMEA= " + strDSLMWDN1);
				strDSLMOPD1 = strDSLMOPDEMEA;
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "ADOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
				strDSLMWDN1 = strDSLMWDNAP;
				addDebug("*****WD AP= " + strDSLMWDN1);
				strDSLMOPD1 = strDSLMOPDAP;
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
				strDSLMWDN1 = strDSLMWDNUS;
				addDebug("*****WD US= " + strDSLMWDN1);
				strDSLMOPD1 = strDSLMOPDUS;

			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "CDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
				strDSLMWDN1 = strDSLMWDNCCN;
				addDebug("*****WD CCN= " + strDSLMWDN1);
				strDSLMOPD1 = strDSLMOPDCCN;

			}
			sb.append(getValue(IOPUCTY, strIOPUCTY));

			sb.append(getValue(ISLMPAL, strISLMPAL));
			sb.append(getValue(ISLMRFA, strISLMRFA));
			String description = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
			description += PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");
			sb.append(getValue(ISLMPRN, description));
			sb.append(getValue(CSLMPCI, "MM"));
			sb.append(getValue(IPRTNUM, "            "));
			sb.append(getValue(FPUNINC, "2"));
			sb.append(getValue(CAOAV, ""));

			sb.append(getValue(DSLMCPA, PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false)));
			sb.append(getValue(DSLMCPO, ""));
			sb.append(getValue(DSLMGAD, PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false)));
			sb.append(getValue(DSLMMVA, PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false)));

			// strWTHDRWEFFCTVDATE = PokUtils.getAttributeValue(eiModel,
			// "WTHDRWEFFCTVDATE", "", "");

			// if (strWTHDRWEFFCTVDATE != null){
			// if (strWTHDRWEFFCTVDATE.equals("")){
			// strDSLMWDN = "2050-12-31";
			// } else {
			// strDSLMWDN = strWTHDRWEFFCTVDATE;
			// }
			// } else {
			// strDSLMWDN = "2050-12-31";
			// }
			if (strDSLMOPD1 != "") {
				strDSLMOPD = strDSLMOPD1;
			} else {
				strDSLMOPD = "2050-12-31";
			}

			if (strDSLMWDN1 != "") {
				strDSLMWDN = strDSLMWDN1;
			} else {
				strDSLMWDN = "2050-12-31";
			}
			sb.append(getValue(DSLMOPD, strDSLMOPD));
			sb.append(getValue(DSLMWDN, strDSLMWDN));
			addDebug("*****= strDSLMWDN" + strDSLMWDN);

			eiSearchAvail = searchForAvailType2(eiModel, "End of Service");
			if (eiSearchAvail != null) {
				strQSMEDMW = PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false);
				strDUSALRW = PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false);
			} else {
				strQSMEDMW = "2050-12-31";
				strDUSALRW = "2050-12-31";
			}

			sb.append(getValue(QSMEDMW, strQSMEDMW));

			sb.append(getValue(ASLMMVP, "01.0"));
			ccuoicrValue = PokUtils.getAttributeValue(eiModel, "ICRCATEGORY", "", "");
			sb.append(getValue(CCUOICR, ccuoicrValue));
			sb.append(getValue(CICIB, "N"));
			sb.append(getValue(CICIC, "N"));
			sb.append(getValue(CICRY, "N"));
			sb.append(getValue(CIDCJ, "N"));
			sb.append(getValue(CIDXF, PokUtils.getAttributeValue(eiModel, "LICNSINTERCD", "", "")));

			String strCINCA = "";
			String geoModGeo = "";
			EntityItem eiModGeoMod = null;
			Vector geoModVect = PokUtils.getAllLinkedEntities(eiModel, "MODELGEOMOD", "GEOMOD");
			if (geoModVect.size() > 0) {
				for (int igm = 0; igm < geoModVect.size(); igm++) {
					eiModGeoMod = (EntityItem) geoModVect.elementAt(igm);
					geoModGeo = PokUtils.getAttributeValue(eiModGeoMod, "GENAREASELECTION", "", "");
					if (geoModGeo.equals(strAvailGenAreaSel)) {
						strCINCA = PokUtils.getAttributeValue(eiModGeoMod, "NOCHRGRENT", "", "");
						geoModGraduatedCharge = PokUtils.getAttributeValue(eiModGeoMod, "GRADUATEDCHARGE", "", "");
						geoModPurchOnly = PokUtils.getAttributeValue(eiModGeoMod, "PURCHONLY", "", "");
						geoModPlntofmr = PokUtils.getAttributeValue(eiModGeoMod, "PLNTOFMFR", "", "");
						geoModIntegratedmodel = PokUtils.getAttributeValue(eiModGeoMod, "INTEGRATEDMODEL", "", "");
						geoModPercallcls = PokUtils.getAttributeValue(eiModGeoMod, "PERCALLCLS", "", "");
						geoModEmeaBrandCode = PokUtils.getAttributeValue(eiModGeoMod, "EMEABRANDCD", "", "");
						geoModAnnualMaint = PokUtils.getAttributeValue(eiModGeoMod, "ANNUALMAINT", "", "");
						geoModMethodProd = PokUtils.getAttributeValue(eiModGeoMod, "METHODPROD", "", "");
						strFUSAEDE = PokUtils.getAttributeValue(eiModGeoMod, "EDUCPURCHELIG", "", "");

						igm = geoModVect.size();
					} else {
						eiModGeoMod = null;
					}
				}
			}

			sb.append(getValue(CINCA, strCINCA));

			strPrcIndc = PokUtils.getAttributeValue(eiModel, "PRCINDC", "", "");
			if (strPrcIndc.equals("Yes")) {
				sb.append(getValue(CINCB, "N"));
			} else if (strPrcIndc.equals("No")) {
				sb.append(getValue(CINCB, "Y"));
			} else {
				sb.append(getValue(CINCB, "N"));
			}

			sb.append(getValue(CINCC, "N"));
			sb.append(getValue(CINPM, PokUtils.getAttributeValue(eiModel, "NETPRICEMES", "", "")));

			sb.append(getValue(CISUP, "N"));

			sb.append(getValue(CITEM, "N"));
			String annIndDefnCatg = PokUtils.getAttributeValue(rootEntity, "INDDEFNCATG", ",", "", false);
			if (annIndDefnCatg.length() >= 2) {
				sb.append(getValue(CJLBIC1, annIndDefnCatg.substring(0, 2)));
			} else {
				sb.append(getValue(CJLBIC1, ""));
			}
			if (annIndDefnCatg.length() >= 3) {
				sb.append(getValue(CJLBIDS, annIndDefnCatg.substring(2)));
			} else {
				sb.append(getValue(CJLBIDS, ""));
			}
			sb.append(getValue(CJLBOEM, PokUtils.getAttributeValue(eiModel, "SPECMODDESGN", "", "")));

			sb.append(getValue(CJLBPOF, ""));

			Vector sgmntAcrnymVect = PokUtils.getAllLinkedEntities(eiModel, "MODELSGMTACRONYMA", "SGMNTACRNYM");
			if (!sgmntAcrnymVect.isEmpty()) {
				EntityItem sgmntAcrnymEI = (EntityItem) sgmntAcrnymVect.elementAt(0);
				if (sgmntAcrnymEI != null) {
					sb.append(getValue(CJLBSAC, PokUtils.getAttributeValue(sgmntAcrnymEI, "ACRNYM", "", "")));
				} else {
					sb.append(getValue(CJLBSAC, "   "));
				}
			} else {
				sb.append(getValue(CJLBSAC, "   "));
			}

			sb.append(getValue(CLASSPT, "IHW"));

			if (strMainAvailType.equals("Last Order")) {
				sb.append(getValue(CPDAA, "O"));
			} else {
				sb.append(getValue(CPDAA, "N"));
			}

			addDebug("*****= CPDAA" + CPDAA);

			sb.append(getValue(CSLMFCC, PokUtils.getAttributeValue(eiModel, "FUNCCLS", "", "")));

			if (strAvailGenAreaSel.equals("Asia Pacific")) {
				sb.append(getValue(CSLMGGC, geoModGraduatedCharge));
			} else {
				sb.append(getValue(CSLMGGC, " "));
			}

			String strProdID = PokUtils.getAttributeValue(eiModel, "PRODID", "", "");
			if (strProdID.equals("0-CPU")) {
				strCSLMIDP = "0";
			} else if (strProdID.equals("1-Unit Record Equipm.")) {
				strCSLMIDP = "1";
			} else if (strProdID.equals("2-System Component")) {
				strCSLMIDP = "2";
			} else if (strProdID.equals("3-Stand Alone Material")) {
				strCSLMIDP = "3";
			} else if (strProdID.equals("4-System Control")) {
				strCSLMIDP = "4";
			} else if (strProdID.equals("5-Program Product")) {
				strCSLMIDP = "5";
			} else if (strProdID.equals("6-Special Program")) {
				strCSLMIDP = "6";
			} else if (strProdID.equals("7-Control Unit")) {
				strCSLMIDP = "7";
			} else if (strProdID.equals("8-Disk Packs")) {
				strCSLMIDP = "8";
			} else {
				strCSLMIDP = "";
			}

			sb.append(getValue(CSLMIDP, strCSLMIDP));
			sb.append(getValue(CSLMLRP, "0"));
			sb.append(getValue(CSLMSAS, "0"));
			sb.append(getValue(CSLMSYT, PokUtils.getAttributeValue(eiModel, "SYSTEMTYPE", "", "")));

			EntityItem eiWarr = null;
			strWARRSVCCOVR = PokUtils.getAttributeValue(eiModel, "WARRSVCCOVR", "", "");
			if (strWARRSVCCOVR != null) {
				if (strWARRSVCCOVR.equals("No Warranty") || strWARRSVCCOVR.equals("")) {
					strCSLMWCD = "Z";
				} else {
					Vector warrVect = PokUtils.getAllLinkedEntities(eiModel, "MODELWARR", "WARR");
					if (!warrVect.isEmpty()) {
						eiWarr = (EntityItem) warrVect.elementAt(0);
						if (eiWarr != null) {
							String strWarrID = PokUtils.getAttributeValue(eiWarr, "WARRID", "", "");
							if (strWarrID.equals("WTY0000")) {
								if (warrVect.size() > 1) {
									eiWarr = (EntityItem) warrVect.elementAt(1);
								} else {
									eiWarr = null;
								}
							}
						}
						/** code changed as requested */
						if (eiWarr != null) {
							strCSLMWCD = PokUtils.getAttributeValue(eiWarr, "WARRCATG", "", "");
						} else {
							strCSLMWCD = "";
						}
					} else {
						strCSLMWCD = "";
					}
				}
			} else {
				strCSLMWCD = "Z";
			}
			sb.append(getValue(CSLMWCD, strCSLMWCD));

			sb.append(getValue(FAGRMBE, strFAGRMBE));

			if (ccuoicrValue.equals("1") || ccuoicrValue.equals("2")) {
				sb.append(getValue(FCUOCNF, "N"));
			} else if (ccuoicrValue.equals("3")) {
				sb.append(getValue(FCUOCNF, "Y"));
			} else {
				sb.append(getValue(FCUOCNF, "N"));
			}

			sb.append(getValue(FSLMCLS, "N"));

			String modelSysIdUnit = PokUtils.getAttributeValue(eiModel, "SYSIDUNIT", "", "");
			if (modelSysIdUnit.equals("SIU-CPU")) {
				sb.append(getValue(FSLMCPU, "Y"));
			} else {
				sb.append(getValue(FSLMCPU, "N"));
			}

			sb.append(getValue(FSLMIOP, geoModIntegratedmodel));
			sb.append(getValue(FSLMLGS, "N"));
			sb.append(getValue(FSLMMLC, PokUtils.getAttributeValue(eiModel, "MACHLVLCNTRL", "", "")));

			if (strAvailGenAreaSel.equals("Latin America") || strAvailGenAreaSel.equals("US Only")
					|| strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				sb.append(getValue(FSLMPOP, "No"));
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				sb.append(getValue(FSLMPOP, "Yes"));
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				if (strPrcIndc.equals("Yes")) {
					sb.append(getValue(FSLMPOP, "Y"));
				} else if (strPrcIndc.equals("No")) {
					sb.append(getValue(FSLMPOP, "N"));
				} else {
					sb.append(getValue(FSLMPOP, " "));
				}
			} else {
				sb.append(getValue(FSLMPOP, " "));
			}

			sb.append(getValue(FSLMVDE, PokUtils.getAttributeValue(eiModel, "VOLUMEDISCOUNTELIG", "", "")));
			sb.append(getValue(FSLMVTS, "N"));

			ArrayList warrTypeArray = new ArrayList();
			if (eiWarr != null) {
				EANFlagAttribute warrTypeList = (EANFlagAttribute) eiWarr.getAttribute("WARRTYPE");
				if (warrTypeList != null) {
					if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
						if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")
								|| warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
								|| warrTypeList.isSelected("W0250")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strAvailGenAreaSel.equals("Latin America")) {
						if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")
								|| warrTypeList.isSelected("W0560") || warrTypeList.isSelected("W0570")
								|| warrTypeList.isSelected("W0580")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strAvailGenAreaSel.equals("Asia Pacific")) {
						if (warrTypeList.isSelected("W0550") || warrTypeList.isSelected("W0390")
								|| warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
								|| warrTypeList.isSelected("W0250") || warrTypeList.isSelected("W0310")
								|| warrTypeList.isSelected("W0330") || warrTypeList.isSelected("W0590")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strAvailGenAreaSel.equals("Canada and Caribbean North")
							|| strAvailGenAreaSel.equals("US Only")) {
						strFSLM2CF = "N";
					}
				}
			} else {
				strFSLM2CF = "N";
			}
			sb.append(getValue(FSLM2CF, strFSLM2CF));

			sb.append(getValue(ICESPCC, geoModPercallcls));
			sb.append(getValue(IDORIG, "IBM"));
			sb.append(getValue(IOLCPLM, geoModPlntofmr));

			strPCUAHEA = "000";
			strPCUASEA = "000";
			strPCUAUEA = "000";

			if (eiModGeoMod != null) {
				if (strAvailGenAreaSel.equals("Latin America") || strAvailGenAreaSel.equals("Asia Pacific")
						|| strAvailGenAreaSel.equals("Canada and Caribbean North")) {
					strPCUAHEA = getNumValue(PCUAHEA,
							PokUtils.getAttributeValue(eiModGeoMod, "EDUCALLOWMHGHSCH", ",", "", false));
					strPCUAUEA = getNumValue(PCUAUEA,
							PokUtils.getAttributeValue(eiModGeoMod, "EDUCALLOWMUNVRSTY", ",", "", false));
					strPCUASEA = getNumValue(PCUASEA,
							PokUtils.getAttributeValue(eiModGeoMod, "EDUCALLOWMSECONDRYSCH", ",", "", false));
				}
			}

			sb.append(getValue(PCUAHEA, strPCUAHEA));
			sb.append(getValue(PCUASEA, strPCUASEA));
			sb.append(getValue(PCUAUEA, strPCUAUEA));

			String mdlInstall = PokUtils.getAttributeValue(eiModel, "INSTALL", "", "");
			if (strAvailGenAreaSel.equals("Latin America")) {
				if (mdlInstall.equals("CIF")) {
					strQSLMCSU = "01";
				} else if (mdlInstall.equals("CE") || mdlInstall.equals("N/A") || mdlInstall.equals("Does not apply")) {
					strQSLMCSU = "";
				}
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				if (mdlInstall.equals("CIF")) {
					strQSLMCSU = "01";
				} else if (mdlInstall.equals("CE") || mdlInstall.equals("N/A") || mdlInstall.equals("Does not apply")) {
					strQSLMCSU = "";
				}
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				if (mdlInstall.equals("CIF")) {
					strQSLMCSU = "10";
				} else if (mdlInstall.equals("CE") || mdlInstall.equals("N/A") || mdlInstall.equals("Does not apply")) {
					strQSLMCSU = "";
				}
			} else if (strAvailGenAreaSel.equals("US Only")) {
				if (mdlInstall.equals("CIF")) {
					strQSLMCSU = "01";
				} else if (mdlInstall.equals("CE") || mdlInstall.equals("N/A") || mdlInstall.equals("Does not apply")) {
					strQSLMCSU = "00";
				}
			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				if (mdlInstall.equals("CIF")) {
					strQSLMCSU = "01";
				} else if (mdlInstall.equals("CE") || mdlInstall.equals("N/A") || mdlInstall.equals("Does not apply")) {
					strQSLMCSU = "";
				}
			}
			sb.append(getValue(QSLMCSU, strQSLMCSU));

			sb.append(getValue(QSMXANN, geoModAnnualMaint));
			sb.append(getValue(QSMXESA, "N"));
			sb.append(getValue(QSMXSSA, "N"));

			if (modelSysIdUnit.equals("SIU-CPU") || modelSysIdUnit.equals("U-System Unit")) {
				sb.append(getValue(SYSDES, PokUtils.getAttributeValue(eiModel, "MODMKTGDESC", "", "")));
			} else {
				sb.append(getValue(SYSDES, "   "));
			}

			String strInvname = PokUtils.getAttributeValue(eiModel, "INVNAME", "", "");
			sb.append(getValue(TSLMDES, removeSpecialChars(strInvname)));
			sb.append(getValue(TSLTDES, " "));
			sb.append(getValue(TIMSTMP, " "));
			sb.append(getValue(USERID, " "));
			sb.append(getValue(FBRAND, geoModEmeaBrandCode));

			if (geoModMethodProd.equals("BTP")) {
				strFSLMHVP = "Y";
			} else if (geoModMethodProd.equals("BTO")) {
				strFSLMHVP = "N";
			} else {
				strFSLMHVP = "";
			}

			sb.append(getValue(FSLMHVP, strFSLMHVP));

			if (strAvailGenAreaSel.equals("US Only")) {
				strFSLMCVP = "Y";
			} else if (strAvailGenAreaSel.equals("Latin America")
					|| strAvailGenAreaSel.equals("Europe/Middle East/Africa")
					|| strAvailGenAreaSel.equals("Asia Pacific")
					|| strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				if (geoModMethodProd.equals("BTO")) {
					strFSLMCVP = "Y";
				} else if (geoModMethodProd.equals("BTP")) {
					strFSLMCVP = "N";
				} else {
					strFSLMCVP = " ";
				}
			}

			sb.append(getValue(FSLMCVP, strFSLMCVP));

			sb.append(getValue(FSLMMES, "N"));

			warrTypeArray = new ArrayList();
			strCSLMTM1 = "";
			strCSLMTM2 = "";
			strCSLMTM3 = "";
			strCSLMTM4 = "";
			strCSLMTM5 = "";
			strCSLMTM6 = "";
			strCSLMTM7 = "";
			strCSLMTM8 = "";

			if (eiWarr != null) {
				EANFlagAttribute warrTypeList = (EANFlagAttribute) eiWarr.getAttribute("WARRTYPE");
				if (warrTypeList != null) {
					if (warrTypeList.isSelected("W0560") || warrTypeList.isSelected("W0570")
							|| warrTypeList.isSelected("W0580")) {
						warrTypeArray.add("IOR");
					}
					if (warrTypeList.isSelected("W0550")) {
						warrTypeArray.add("IOE");
					}
					if (warrTypeList.isSelected("W0390")) {
						warrTypeArray.add("COE");
					}
					if (warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
							|| warrTypeList.isSelected("W0250")) {
						warrTypeArray.add("CCE");
					}
					if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")) {
						warrTypeArray.add("CCR");
					}
					if (warrTypeList.isSelected("W0590")) {
						warrTypeArray.add("IOS");
					}
					// TODO - ICE - IBM Courier Exchange - VALUE DOES NOT EXIST
					// IN EACM
					// if (warrTypeList.isSelected("W0310") ||
					// warrTypeList.isSelected("W0330")) {
					// warrTypeArray.add("ICE");
					// }
					// TODO - ICS - IBM Courier Service - VALUE DOES NOT EXIST
					// IN EACM
					// if (warrTypeList.isSelected("W0310") ||
					// warrTypeList.isSelected("W0330")) {
					// warrTypeArray.add("ICS");
					// }
					// TODO - IE8 - Annual Minimum Maintenance Next Day 9x5 -
					// VALUE DOES NOT EXIST IN EACM
					// if (warrTypeList.isSelected("W0310") ||
					// warrTypeList.isSelected("W0330")) {
					// warrTypeArray.add("IE8");
					// }
					// TODO - CES - Depot Exchange Offering - VALUE DOES NOT
					// EXIST IN EACM
					// if (warrTypeList.isSelected("W0310") ||
					// warrTypeList.isSelected("W0330")) {
					// warrTypeArray.add("CES");
					// }
					// TODO - CFM - Central Facility Maintenance - VALUE DOES
					// NOT EXIST IN EACM
					// if (warrTypeList.isSelected("W0310") ||
					// warrTypeList.isSelected("W0330")) {
					// warrTypeArray.add("CFM");
					// }

					for (int w = 0; w < warrTypeArray.size(); w++) {
						if (w == 0) {
							strCSLMTM1 = (String) warrTypeArray.get(w);
						} else if (w == 1) {
							strCSLMTM2 = (String) warrTypeArray.get(w);
						} else if (w == 2) {
							strCSLMTM3 = (String) warrTypeArray.get(w);
						} else if (w == 3) {
							strCSLMTM4 = (String) warrTypeArray.get(w);
						} else if (w == 4) {
							strCSLMTM5 = (String) warrTypeArray.get(w);
						} else if (w == 5) {
							strCSLMTM6 = (String) warrTypeArray.get(w);
						} else if (w == 6) {
							strCSLMTM7 = (String) warrTypeArray.get(w);
						} else if (w == 7) {
							strCSLMTM8 = (String) warrTypeArray.get(w);
						}
					}
				}
			}

			sb.append(getValue(CSLMTM1, strCSLMTM1));
			sb.append(getValue(CSLMTM2, strCSLMTM2));
			sb.append(getValue(CSLMTM3, strCSLMTM3));
			sb.append(getValue(CSLMTM4, strCSLMTM4));
			sb.append(getValue(CSLMTM5, strCSLMTM5));
			sb.append(getValue(CSLMTM6, strCSLMTM6));
			sb.append(getValue(CSLMTM7, strCSLMTM7));
			sb.append(getValue(CSLMTM8, strCSLMTM8));
			sb.append(getValue(FSAPRES, "N"));

			if (strAvailGenAreaSel.equals("US Only")) {
				String strCUSAPMS = PokUtils.getAttributeValue(eiModel, "MAINTANNBILLELIGINDC", ",", "", false);
				if (strCUSAPMS.equals("Yes")) {
					sb.append(getValue(CUSAPMS, "Y"));
				} else if (strCUSAPMS.equals("No")) {
					sb.append(getValue(CUSAPMS, "X"));
				} else {
					sb.append(getValue(CUSAPMS, ""));
				}
			} else {
				sb.append(getValue(CUSAPMS, ""));
			}

			sb.append(getValue(DUSALRW, strDUSALRW));

			sb.append(getValue(DUSAMDW, "2050-12-31"));
			sb.append(getValue(DUSAWUW, "2050-12-31"));
			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(getValue(FSLMCBL, "N"));
			} else {
				sb.append(getValue(FSLMCBL, " "));
			}
			sb.append(getValue(FSLMMRR, "N"));
			strFUSAAAS = "";
			// strFUSAICC = "N";
			if (strAvailGenAreaSel.equals("US Only")) {
				strFUSAAAS = "N";
				// strFUSAICC = "Y";
				String strOrderSysName = PokUtils.getAttributeValue(availEI, "ORDERSYSNAME", ",", "", false);
				if (strOrderSysName.equals("AAS")) {
					strFUSAAAS = "Y";
				}
			}
			sb.append(getValue(FUSAAAS, strFUSAAAS));
			sb.append(getValue(FUSAADM, "N"));

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(getValue(FUSAEDE, strFUSAEDE));
			} else {
				sb.append(getValue(FUSAEDE, " "));
			}

			if (strAvailGenAreaSel.equals("US Only")) {
				strIBMCREDIT = PokUtils.getAttributeValue(eiModel, "IBMCREDIT", ",", "", false);
				addDebug("*****mlm IBMCREDIT=" + strIBMCREDIT);
				if (strIBMCREDIT != null) {
					if (strIBMCREDIT.equals("Yes")) {
						strFUSAICC = "Y";
					} else if (strIBMCREDIT.equals("No")) {
						strFUSAICC = "N";
					}
				}
				sb.append(getValue(FUSAICC, strFUSAICC));
			} else {
				sb.append(getValue(FUSAICC, " "));
			}

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(
						getValue(FUSALEP, PokUtils.getAttributeValue(eiModel, "MAINTANNBILLELIGINDC", ",", "", false)));
			} else {
				sb.append(getValue(FUSALEP, " "));
			}

			sb.append(getValue(FUSAMRS, "N"));
			sb.append(getValue(FUSAVLM, "N"));
			sb.append(getValue(FUSAXMO, "N"));
			sb.append(getValue(QUSAPOP, "00.0"));
			sb.append(getValue(DSLMEOD, "1950-01-01"));
			sb.append(getValue(FSLMRFM, " "));

			sb.append(NEWLINE);
			wOut.write(sb.toString());
			wOut.flush();

		}
	}

	private String removeSpecialChars(String in) {

		String out = "";
		out = in.replaceAll("#", "");
		out = out.replaceAll("$", "");
		out = out.replaceAll("%", "");
		out = out.replaceAll("@", "");
		out = out.replaceAll("/", "");
		out = out.replaceAll("'", "");
		out = out.replaceAll("\"", "");
		out = out.replaceAll("â€“", "");

		return out;

	}

	private List searchForAvailType(EntityItem eiModel, String strSearchAvailType) {

		ArrayList eiReturn = new ArrayList();
		String strAvailType;

		Vector availVect = PokUtils.getAllLinkedEntities(eiModel, "MODELAVAIL", "AVAIL");

		addDebug("*****mlm searchforavail AVAIL " + availVect);

		for (int i = 0; i < availVect.size(); i++) {
			EntityItem eiAvail = (EntityItem) availVect.elementAt(i);

			strAvailType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", ",", "", false);
			addDebug("*****mlm searchforavail model = " + eiModel.getEntityType() + eiModel.getEntityID()
					+ "avail entity type = " + eiAvail.getEntityType() + " avail type = " + strAvailType);

			if (strSearchAvailType.equals(strAvailType)) {
				eiReturn.add(eiAvail);
				// break;
			}
		}

		return eiReturn;
	}

	private EntityItem searchForAvailType2(EntityItem eiModel, String strSearchAvailType2) {

		EntityItem eiReturn = null;
		String strAvailType;

		Vector availVect = PokUtils.getAllLinkedEntities(eiModel, "MODELAVAIL", "AVAIL");

		addDebug("*****mlm searchforavail AVAIL2 " + availVect);

		for (int i = 0; i < availVect.size(); i++) {
			EntityItem eiAvail = (EntityItem) availVect.elementAt(i);

			strAvailType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", ",", "", false);
			addDebug("*****mlm searchforavail model2 = " + eiModel.getEntityType() + eiModel.getEntityID()
					+ "avail entity type = " + eiAvail.getEntityType() + " avail type = " + strAvailType);

			if (strSearchAvailType2.equals(strAvailType)) {
				eiReturn = eiAvail;
				break;
			}
		}

		return eiReturn;
	}

	private String validateProdstructs2(EntityItem eiFeature)
			throws MiddlewareRequestException, SQLException, MiddlewareException {

		String strReturnDate = "";
		Date oldestDate = null;
		Date psDate = null;

		ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, getT006FeatureVEName());

		EntityList list = m_db.getEntityList(m_prof, eaItem,
				new EntityItem[] { new EntityItem(null, m_prof, eiFeature.getEntityType(), eiFeature.getEntityID()) });

		EntityGroup prodStructGrp = list.getEntityGroup("PRODSTRUCT");
		addDebug("*****mlm feature.id=" + eiFeature.getEntityType() + eiFeature.getEntityID() + " prodstructcount="
				+ prodStructGrp.getEntityItemCount());

		for (int i = 0; i < prodStructGrp.getEntityItemCount(); i++) {

			EntityItem eiProdstruct = prodStructGrp.getEntityItem(i);
			addDebug("*****mlm prodstruct=" + eiProdstruct.getEntityType() + eiProdstruct.getEntityID());

			String psWdDate = PokUtils.getAttributeValue(eiProdstruct, "WTHDRWEFFCTVDATE", ",", "", false);
			addDebug("*****mlm oldestdate=" + oldestDate);
			addDebug("*****mlm psWdDate=" + psWdDate);
			if (!psWdDate.equals("")) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					psDate = df.parse(psWdDate);
					if ((oldestDate == null) || (psDate.after(oldestDate))) {
						addDebug("*****mlm setting odlestdate to psWdDate");
						oldestDate = psDate;
						strReturnDate = psWdDate;
					}
				} catch (ParseException e) {
					addDebug(e.toString());
					addDebug("*****mlm error: ParseException, setting date to 2050-12-31 - end");
					strReturnDate = "2050-12-31";
					i = prodStructGrp.getEntityItemCount();
					break;
				}
			} else {
				addDebug("*****mlm psWdDate is blank, set date to 2050-12-31 - end");
				strReturnDate = "2050-12-31";
				i = prodStructGrp.getEntityItemCount();
				break;
			}
		}

		return strReturnDate;
	}

	private void validateProdstructsSQL(String ids)
			throws MiddlewareRequestException, SQLException, MiddlewareException {
		fidMap.clear();
		if(ids.length()<1)
			return;
		String strReturnDate = "2050-12-31";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date oldestDate = null;
		Date psDate = null;
		// String sql = "select prod.ATTRIBUTEVALUE from opicm.text prod join
		// opicm.Relator R on R.entitytype=prod.entitytype and prod.entityid =
		// R.entityid where R.entitytype='PRODSTRUCT' AND R.ENTITY1ID =
		// "+eiFeature.getEntityID()+" AND prod.ATTRIBUTECODE =
		// 'WTHDRWEFFCTVDATE' AND R.EFFTO >CURRENT TIMESTAMP AND R.valto>current
		// TIMESTAMP and prod.valto>current timestamp and prod.EFFTO >current
		// timestamp";
		String sql = "select R.entity1id as entityid,prod.WTHDRWEFFCTVDATE as ATTRIBUTEVALUE from  opicm.Relator R left join price.prodstruct prod  on prod.entityid= R.entityid where R.entitytype='PRODSTRUCT' AND R.ENTITY1ID in ("
				+ ids + ")  AND R.EFFTO >CURRENT TIMESTAMP AND R.valto>current TIMESTAMP  and prod.nlsid=1";
		 addDebug("sql:"+sql);
		Connection conn = m_db.getPDHConnection();
		PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		// ps.setInt(1, eiFeature.getEntityID());
		ResultSet rs = ps.executeQuery();

		List list = new ArrayList();
		while (rs.next()) {
			String date = rs.getString("ATTRIBUTEVALUE");
			String id = rs.getString("entityid");
			if (date == null || date.trim().equals(""))
				fidMap.put(id, "2050-12-31");
			else if (fidMap.get(id) == null) {
				fidMap.put(id, date);
			} else {

				try {
					oldestDate = df.parse(fidMap.get(id).toString());
					psDate = df.parse(date);
					if ((oldestDate == null) || (psDate.after(oldestDate))) {
						addDebug("*****mlm setting odlestdate to psWdDate");
						fidMap.put(id, date);
					}
				} catch (ParseException e) {
					addDebug(e.toString());
					addDebug("*****mlm error: ParseException, setting date to 2050-12-31 - end");

					fidMap.put(id, "2050-12-31");
					break;
				}
			}
			// list.add(rs.getString("ATTRIBUTEVALUE"));
		}
		rs.close();

	}

	private String validateProdstructsDate(EntityItem eiFeature) {
		String resutl = fidMap.get(eiFeature.getEntityID() + "")==null?"":fidMap.get(eiFeature.getEntityID() + "").toString();

		return resutl;
	
	}

	private String validateProdstructs(EntityItem eiFeature)
			throws MiddlewareRequestException, SQLException, MiddlewareException {

		String strReturnDate = "";
		Date oldestDate = null;
		Date psDate = null;

		/*
		 * ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof,
		 * getT006FeatureVEName());
		 * 
		 * EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[]
		 * { new EntityItem(null, m_prof, eiFeature.getEntityType(),
		 * eiFeature.getEntityID()) });
		 */
		Vector prodStructGrp = getTMFFromFeature(eiFeature);
		addDebug("Featue:" + eiFeature.getEntityID() + "-PRODUCT size:" + prodStructGrp.size());
		addDebug("Featue uplink:" + eiFeature.getUpLinkCount());
		String idString = "";
		for (int i = 0; i < prodStructGrp.size(); i++) {
			idString += ((EntityItem) prodStructGrp.get(i)).getEntityID() + ":";
		}
		addDebug("*****mlm idString=" + idString);
		for (int i = 0; i < prodStructGrp.size(); i++) {

			EntityItem eiProdstruct = (EntityItem) prodStructGrp.get(i);

			String psWdDate = PokUtils.getAttributeValue(eiProdstruct, "WTHDRWEFFCTVDATE", ",", "", false);
			addDebug("*****mlm oldestdate=" + oldestDate);
			addDebug("*****mlm psWdDate=" + psWdDate);
			if (!psWdDate.equals("")) {
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				try {
					psDate = df.parse(psWdDate);
					if ((oldestDate == null) || (psDate.after(oldestDate))) {
						addDebug("*****mlm setting odlestdate to psWdDate");
						oldestDate = psDate;
						strReturnDate = psWdDate;
					}
				} catch (ParseException e) {
					addDebug(e.toString());
					addDebug("*****mlm error: ParseException, setting date to 2050-12-31 - end");
					strReturnDate = "2050-12-31";
					i = prodStructGrp.size();
					break;
				}
			} else {
				addDebug("*****mlm psWdDate is blank, set date to 2050-12-31 - end");
				strReturnDate = "2050-12-31";
				i = prodStructGrp.size();
				break;
			}
		}

		return strReturnDate;
	}

	/*
	 * Create records for ANNOUNCEMENT -> AVAIL -> OOFAVAIL -> PRODSTRUCT ->
	 * MODEL
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006Feature(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		EANFlagAttribute qsmGeoList;
		String strAvailType;
		String strAvailAnnType = "";
		boolean isEpic = false;

		m_elist = getEntityList(getT006ProdstructVEName());

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		availSet = new HashSet();
		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {
			availSet.add(availGrp.getEntityItem(availI).getEntityID() + "");
		}
		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {
			qsmGeoList = null;
			strAvailType = "";

			EntityItem availEI = availGrp.getEntityItem(availI);

			strAvailType = PokUtils.getAttributeValue(availEI, "AVAILTYPE", "", "");
			strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")) {
				isEpic = true;
			}

			// ******* NEW *******
			if ((strAvailType.equals("Planned Availability")) || (strAvailType.equals("End of Service"))
					|| (strAvailType.equals("Last Order"))) {
				qsmGeoList = (EANFlagAttribute) availEI.getAttribute("QSMGEO");
				if (qsmGeoList != null) {
					if (qsmGeoList.isSelected("6199")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Asia Pacific", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6200")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Canada and Caribbean North", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6198")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Europe/Middle East/Africa", strAvailType,
								isEpic);
					}
					if (qsmGeoList.isSelected("6204")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "Latin America", strAvailType, isEpic);
					}
					if (qsmGeoList.isSelected("6221")) {
						createT006FeatureRecords(rootEntity, wOut, availEI, "US Only", strAvailType, isEpic);
					}
				}
			}
		}
	}

	/*
	 * Create records for ANNOUNCEMENT -> AVAIL -> OOFAVAIL -> PRODSTRUCT ->
	 * MODEL
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006FeatureRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem availEI,
			String strAvailGenAreaSel, String strMainAvailType, boolean isEpic)
			throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strIOPUCTY;
		String strISLMRFA;
		String strCSLMPCI;
		String strFCTYPE;
		String strDSLMWDN;
		String strFAGRMBE;
		String strISLMPAL;
		String strPCUAEAP;
		String strPCUAHEA;
		String strPCUASEA;
		String strPCUAUEA;
		String strCSLMTM1;
		String strCSLMTM2;
		String strCSLMTM3;
		String strCSLMTM4;
		String strCSLMTM5;
		String strCSLMTM6;
		String strCSLMTM7;
		String strCSLMTM8;
		String geoModPurchOnly;
		String strASLMMVP;
		String strFeatureType;
		String strPricedFeature;
		String strCSLMFTY;
		String strSTSPCFT;
		String strFSLMPIO;
		String strQSLMCSU;
		String strPrcIndc;
		String strFUSAEDE;
		String strMaintElig;
		String strCINCC;
		String strAvailType;
		String strDSLMMES;
		String strCSLMWCD;
		String strWARRSVCCOVR;

		// int batch =0;

		// int total = prodVect.size() / chunkSize;
		// addDebug("Toal:" + total);
		ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, getFeatureVEName());
		EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] { availEI });

		EntityItem avail = list.getParentEntityGroup().getEntityItem(0);
		Vector prodVect = PokUtils.getAllLinkedEntities(avail, "OOFAVAIL", "PRODSTRUCT");
		addDebug("prodVect:" + (prodVect == null ? 0 : prodVect.size()));

		addDebug("list:" + PokUtils.outputList(list));

		/*
		 * for (int batch = 0; batch <= total; batch++) { int from = batch *
		 * chunkSize; int to = (batch + 1) * chunkSize > prodVect.size() ?
		 * prodVect.size() : (batch + 1) * chunkSize; List batchProds =
		 * prodVect.subList(from, to);
		 * 
		 * EntityItem[] tmfs = new EntityItem[batchProds.size()];
		 * 
		 * addDebug("Prodstruct size:" + batchProds.size());
		 * 
		 * if (batchProds.size() != 0) { for (int i = 0; i < batchProds.size();
		 * i++) { EntityItem eiProdstruct = (EntityItem) batchProds.get(i);
		 * tmfs[i] = eiProdstruct; // addDebug("Prodstruct, " + i + " id:" + //
		 * eiProdstruct.getEntityID());
		 * 
		 * } EntityList list = m_db.getEntityList(m_prof, eaItem, tmfs);
		 * EntityGroup productGroup = list.getParentEntityGroup(); int
		 * groupCount = productGroup.getEntityItemCount();
		 * addDebug("groupCount:" + groupCount);
		 */
		// sb = new StringBuffer();
		EntityGroup feature = list.getEntityGroup("FEATURE");
		String fids = "";
		for (int fi = 0; fi < feature.getEntityItemCount(); fi++) {
			if (fids.length() > 0)
				fids += ',';
			fids += feature.getEntityItem(fi).getEntityID();
		}
		validateProdstructsSQL(fids);

		for (int i = 0; i < prodVect.size(); i++) {

			EntityItem eiProdstruct = (EntityItem) prodVect.get(i);

			// EntityItem eiFeature = (EntityItem)
			// eiProdstruct.getUpLink(0);// featureGrp.getEntityItem(0);
			// EntityItem eiModel = (EntityItem)
			// eiProdstruct.getDownLink(0);

			/*
			 * Vector eiModels = PokUtils.getAllLinkedEntities(eiProdstruct,
			 * "PRODSTRUCT", "MODEL"); // (EntityItem)
			 * eiProdstruct.getDownLink(0);
			 * 
			 * Vector eiFeatures = PokUtils.getAllLinkedEntities(eiProdstruct,
			 * "PRODSTRUCT", "FEATURE");
			 */
			EntityItem eiModel = getModelEntityFromTmf(eiProdstruct);
			/*
			 * (EntityItem) PokUtils.getAllLinkedEntities(eiProdstruct,
			 * "PRODSTRUCT", "MODEL") .get(0);
			 */

			EntityItem eiFeature = /*
									 * (EntityItem) PokUtils
									 * .getAllLinkedEntities(eiProdstruct,
									 * "PRODSTRUCT", "FEATURE").get(0);
									 */

					(EntityItem) eiProdstruct.getUpLink(0);
			Vector psStdMaintVect = PokUtils.getAllLinkedEntities(eiProdstruct, "PRODSTSTDMT", "STDMAINT");

			/*
			 * addDebug("eiProdstruct:" + eiProdstruct.getEntityID() + ":" +
			 * eiProdstruct.getEntityType()); addDebug("eiFeature:" +
			 * eiFeature.getEntityID() + ":" + eiFeature.getEntityType());
			 * addDebug("eiModel:" + eiModel.getEntityID() + ":" +
			 * eiModel.getEntityType()); addDebug("eiFeature uplink:" +
			 * eiFeature.getUpLinkCount()); addDebug("eiModel downlink:" +
			 * eiModel.getDownLinkCount());
			 * 
			 * addDebug("eiFeature downlink:" + eiFeature.getDownLinkCount());
			 * addDebug("eiModel uplink:" + eiModel.getUpLinkCount());
			 * 
			 * addDebug("eiFeatures:" + eiFeatures); addDebug("eiModels:" +
			 * eiModels);
			 * 
			 * ExtractActionItem eaItem1 = new ExtractActionItem(null, m_db,
			 * m_prof, getT006ModelLinksVEName());
			 * 
			 * EntityList list1 = m_db.getEntityList(m_prof, eaItem1, new
			 * EntityItem[] { new EntityItem(null, m_prof,
			 * eiModel.getEntityType(), eiModel.getEntityID()) });
			 * 
			 * addDebug("EntityList for " + m_prof.getValOn() +
			 * " extract QSMFULL5 contains the following entities: \n" +
			 * PokUtils.outputList(list1));
			 */

			// EntityGroup sgmntAcrnymGrp =
			// list1.getEntityGroup("SGMNTACRNYM");
			// EntityGroup geoModGrp = list1.getEntityGroup("GEOMOD");
			// EntityGroup modelWarrGrp = list1.getEntityGroup("WARR");
			// EntityGroup modelStdMaintGrp =
			// list1.getEntityGroup("STDMAINT");

			Vector sgmntAcrnymGrp = PokUtils.getAllLinkedEntities(eiModel, "MODELSGMTACRONYMA", "SGMNTACRNYM");
			Vector geoModGrp = PokUtils.getAllLinkedEntities(eiModel, "MODELGEOMOD", "GEOMOD");
			Vector modelWarrGrp = PokUtils.getAllLinkedEntities(eiModel, "MODELWARR", "WARR");
			Vector modelStdMaintGrp = PokUtils.getAllLinkedEntities(eiModel, "MODELSTDMAINT", "STDMAINT");

			EntityGroup sgmntAcrnymGrp1 = list.getEntityGroup("SGMNTACRNYM");
			EntityGroup geoModGrp1 = list.getEntityGroup("GEOMOD");
			EntityGroup modelWarrGrp1 = list.getEntityGroup("WARR");
			EntityGroup modelStdMaintGrp1 = list.getEntityGroup("STDMAINT");

			/*
			 * addDebug("sgmntAcrnymGrp sizeï¼š" + (sgmntAcrnymGrp == null ?
			 * 0 : sgmntAcrnymGrp.size())); addDebug("geoModGrp" + (geoModGrp ==
			 * null ? 0 : geoModGrp.size())); addDebug("modelWarrGrp" +
			 * (modelWarrGrp == null ? 0 : modelWarrGrp.size()));
			 * addDebug("modelStdMaintGrp" + (modelStdMaintGrp == null ? 0 :
			 * modelStdMaintGrp.size()));
			 * 
			 * addDebug("sgmntAcrnymGrp1 sizeï¼š" +
			 * sgmntAcrnymGrp1.getEntityItemCount()); addDebug("geoModGrp1 size"
			 * + geoModGrp1.getEntityItemCount()); addDebug("modelWarrGrp1 size"
			 * + modelWarrGrp1.getEntityItemCount());
			 * addDebug("modelStdMaintGrp1 size" +
			 * modelStdMaintGrp1.getEntityItemCount());
			 */
			sb = new StringBuffer();
			strIOPUCTY = "";
			strISLMRFA = "";
			strCSLMPCI = "";
			strFCTYPE = "";
			strDSLMWDN = "";
			strFAGRMBE = "";
			strISLMPAL = "";
			strPCUAEAP = "";
			strPCUAHEA = "";
			strPCUASEA = "";
			strPCUAUEA = "";
			strCSLMTM1 = "";
			strCSLMTM2 = "";
			strCSLMTM3 = "";
			strCSLMTM4 = "";
			strCSLMTM5 = "";
			strCSLMTM6 = "";
			strCSLMTM7 = "";
			strCSLMTM8 = "";
			geoModPurchOnly = "";
			strASLMMVP = "";
			strFeatureType = "";
			strPricedFeature = "";
			strCSLMFTY = "";
			strSTSPCFT = "";
			strFSLMPIO = "";
			strQSLMCSU = "";
			strPrcIndc = "";
			strFUSAEDE = "";
			strMaintElig = "";
			strCINCC = "";
			strAvailType = "";
			strDSLMMES = "";
			strCSLMWCD = "";
			strWARRSVCCOVR = "";

			sb.append(getValue(IFTYPE, "F"));

			if (strAvailGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "LDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "EDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "ADOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "CDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
			}
			sb.append(getValue(IOPUCTY, strIOPUCTY));
			sb.append(getValue(ISLMPAL, strISLMPAL));
			sb.append(getValue(ISLMRFA, strISLMRFA));
			String strISLMPRN = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", ",", "", false);
			strISLMPRN += PokUtils.getAttributeValue(eiFeature, "FEATURECODE", ",", "", false);
			sb.append(getValue(ISLMPRN, strISLMPRN));

			strFCTYPE = PokUtils.getAttributeValue(eiFeature, "FCTYPE", ",", "", false);
			strCSLMPCI = "MF";
			if (strFCTYPE.equals("RPQ-RLISTED") || strFCTYPE.equals("RPQ-ILISTED") || strFCTYPE.equals("RPQ-PLISTED")) {
				strCSLMPCI = "MQ";
			}

			sb.append(getValue(CSLMPCI, strCSLMPCI));
			sb.append(getValue(IPRTNUM, ""));
			sb.append(getValue(FPUNINC, "2"));
			sb.append(getValue(CAOAV, ""));
			sb.append(getValue(DSLMCPA, PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false)));
			sb.append(getValue(DSLMCPO, ""));

			sb.append(getValue(DSLMGAD, PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false)));

			String strOrderCode = PokUtils.getAttributeValue(eiProdstruct, "ORDERCODE", ",", "", false);
			EntityItem oofAvail = null;
			EANFlagAttribute availQSMGeoList;
			StringBuffer idString = new StringBuffer();
			if (strOrderCode.equals("Both") || strOrderCode.equals("MES")) {
				Vector availVec = PokUtils.getAllLinkedEntities(eiProdstruct, "OOFAVAIL", "AVAIL");
				addDebug("availVec" + (availVec == null ? 0 : availVec.size()));
				// addDebug("list:"+availVec);
				for (int iA = 0; iA < availVec.size(); iA++) {
					idString.append(((EntityItem) availVec.get(iA)).getEntityID() + ",");
				}

				idString.append("||||");
				for (int iA = 0; iA < availVec.size(); iA++) {

					availQSMGeoList = null;
					oofAvail = (EntityItem) availVec.elementAt(iA);

					idString.append(oofAvail.getEntityID() + "");
					strAvailType = PokUtils.getAttributeValue(oofAvail, "AVAILTYPE", ",", "", false);
					availQSMGeoList = (EANFlagAttribute) oofAvail.getAttribute("QSMGEO");
					if (isQSMGeoSelected(strAvailGenAreaSel, availQSMGeoList)
							&& strAvailType.equals("MES Planned Availability")
							&& availSet.contains(oofAvail.getEntityID() + "")) {
						strDSLMMES = PokUtils.getAttributeValue(oofAvail, "EFFECTIVEDATE", ",", "", false);
						iA = availVec.size();
					}
				}
				if (strDSLMMES.equals("")) {
					strDSLMMES = PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false);
				}
			} else if (strOrderCode.equals("Initial")) {
				strDSLMMES = "2050-12-31";
			}

			if (strDSLMMES.equals("")) {
				strDSLMMES = "2050-12-31";
			}
			addDebug("Prod id:" + eiProdstruct.getEntityID() + "   idString:" + idString);

			sb.append(getValue(DSLMMES, strDSLMMES));

			sb.append(getValue(QSMEDMW, "2050-12-31"));
			sb.append(getValue(DSLMMVA, PokUtils.getAttributeValue(rootEntity, "ANNDATE", ",", "", false)));

			strDSLMWDN = validateProdstructsDate(eiFeature);
			sb.append(getValue(DSLMWDN, strDSLMWDN));

			strPricedFeature = PokUtils.getAttributeValue(eiFeature, "PRICEDFEATURE", ",", "", false);

			if (strFCTYPE.equals("Primary") && strPricedFeature.equals("No")) {
				strFeatureType = "S";
			}

			if (strAvailGenAreaSel.equals("Asia Pacific")) {
				if (strPricedFeature.equals("No")) {
					strASLMMVP = "0.00";
				} else if (strPricedFeature.equals("Yes")) {
					strASLMMVP = "1.00";
				}
			} else {
				strASLMMVP = "1.00";
			}

			sb.append(getValue(ASLMMVP, strASLMMVP));

			sb.append(getValue(CICRY, "N"));
			sb.append(getValue(CIDCJ, "N"));
			sb.append(getValue(CIDXC, "N"));

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(getValue(CINCA, "N"));
			} else {
				sb.append(getValue(CINCA, "Y"));
			}

			String strCINCB = "";
			strPrcIndc = PokUtils.getAttributeValue(eiFeature, "PRICEDFEATURE", "", "");
			if (strAvailGenAreaSel.equals("US Only")) {
				strCINCB = "N";
			} else {
				if (strPrcIndc.equals("Yes")) {
					strCINCB = "N";
				} else if (strPrcIndc.equals("No")) {
					strCINCB = "Y";
				} else {
					strCINCB = "N";
				}
			}

			sb.append(getValue(CINCB, strCINCB));

			EntityItem eiStdMaint = null;
			// addDebug("psStdMaintVect is empty:" + psStdMaintVect.isEmpty());
			if (!psStdMaintVect.isEmpty()) {
				eiStdMaint = (EntityItem) psStdMaintVect.elementAt(0);
				if (eiStdMaint != null) {
					strMaintElig = PokUtils.getAttributeValue(eiStdMaint, "MAINTELIG", "", "");
				} else {
					if (modelStdMaintGrp != null && modelStdMaintGrp.size() > 0) {
						eiStdMaint = (EntityItem) modelStdMaintGrp.get(0);
					}
				}
			} else {
				if (modelStdMaintGrp != null && modelStdMaintGrp.size() > 0) {
					eiStdMaint = (EntityItem) modelStdMaintGrp.get(0);
				}
			}

			if (eiStdMaint != null) {
				strMaintElig = PokUtils.getAttributeValue(eiStdMaint, "MAINTELIG", "", "");
			}

			// addDebug("strAvailGenAreaSelï¼š" + strAvailGenAreaSel);
			if (strAvailGenAreaSel.equals("Asia Pacific")) {
				strCINCC = "Y";
			} else if (strAvailGenAreaSel.equals("US Only")
					|| strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strCINCC = "N";
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")
					|| strAvailGenAreaSel.equals("Latin America")) {
				if (strMaintElig.equals("Yes")) {
					strCINCC = "N";
				} else if (strMaintElig.equals("No")) {
					strCINCC = "Y";
				}
			}

			sb.append(getValue(CINCC, strCINCC));

			sb.append(getValue(CINPM, "N"));
			sb.append(getValue(CITEM, "N"));
			sb.append(getValue(CISUP, "N"));
			if (sgmntAcrnymGrp != null && sgmntAcrnymGrp.size() > 0) {
				EntityItem sgmntAcrnymEI = (EntityItem) sgmntAcrnymGrp.get(0);
				sb.append(getValue(CJLBSAC, PokUtils.getAttributeValue(sgmntAcrnymEI, "ACRNYM", "", "")));
			} else {
				sb.append(getValue(CJLBSAC, "   "));
			}
			sb.append(getValue(CLASSPT, "IHW"));

			strCSLMFTY = "";

			if (strAvailGenAreaSel.equals("Europe/Middle East/Africa") || strAvailGenAreaSel.equals("Latin America")) {
				if (strFeatureType.equals("S")) {
					strCSLMFTY = "CM";
				}
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				if (strFeatureType.equals("S")) {
					strCSLMFTY = "CM";
				}
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strCSLMFTY = "NF";
			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strCSLMFTY = "";
			}

			sb.append(getValue(CSLMFTY, strCSLMFTY));
			sb.append(getValue(CVOAT, ""));

			// PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", ",",
			// "",
			// false);

			if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strFAGRMBE = "Y";
			} else if (strAvailGenAreaSel.equals("Asia Pacific") || strAvailGenAreaSel.equals("US Only")) {
				strFAGRMBE = "N";
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")
					|| strAvailGenAreaSel.equals("Latin America")) {
				strFAGRMBE = strMaintElig;
			}

			sb.append(getValue(FAGRMBE, strFAGRMBE));

			String geoModGeo = "";
			EntityItem eiModGeoMod = null;
			if (geoModGrp != null && geoModGrp.size() > 0) {
				for (int igm = 0; igm < geoModGrp.size(); igm++) {
					eiModGeoMod = (EntityItem) geoModGrp.get(igm);
					geoModGeo = PokUtils.getAttributeValue(eiModGeoMod, "GENAREASELECTION", "", "");
					if (geoModGeo.equals(strAvailGenAreaSel)) {
						geoModPurchOnly = PokUtils.getAttributeValue(eiModGeoMod, "PURCHONLY", "", "");
						strFUSAEDE = PokUtils.getAttributeValue(eiModGeoMod, "EDUCPURCHELIG", "", "");
						igm = geoModGrp.size();
					} else {
						eiModGeoMod = null;
					}
				}
			}

			if (strAvailGenAreaSel.equals("Latin America") || strAvailGenAreaSel.equals("Europe/Middle East/Africa")
					|| strAvailGenAreaSel.equals("Asia Pacific")
					|| strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				if (strOrderCode.equals("Initial")) {
					strFSLMPIO = "Y";
				} else {
					strFSLMPIO = "N";
				}
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strFSLMPIO = "N";
			}
			sb.append(getValue(FSLMPIO, strFSLMPIO));

			/*
			 * if (strAvailGenAreaSel.equals("US Only") ||
			 * strAvailGenAreaSel.equals("Asia Pacific") ||
			 * strAvailGenAreaSel.equals("Canada and Caribbean North")) {
			 * strFSLMPOP = "N"; } else { if (eiModGeoMod != null){ strFSLMPOP =
			 * geoModPurchOnly; } else { strFSLMPOP = ""; } }
			 */

			if (strAvailGenAreaSel.equals("Latin America") || strAvailGenAreaSel.equals("US Only")
					|| strAvailGenAreaSel.equals("Canada and Caribbean North")
					|| strAvailGenAreaSel.equals("Asia Pacific")) {
				sb.append(getValue(FSLMPOP, "No"));
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				sb.append(getValue(FSLMPOP, "Yes"));
			} else {
				sb.append(getValue(FSLMPOP, geoModPurchOnly));
			}

			sb.append(getValue(FSLMSTK, "N"));

			String strFSLM2CF = "";

			ArrayList warrTypeArray = new ArrayList();

			EntityItem eiWarr = null;
			Vector warrVect = null;
			String strWarrID = "";

			warrVect = PokUtils.getAllLinkedEntities(eiProdstruct, "PRODSTRUCTWARR", "WARR");

			// addDebug("warrVect size:" + (warrVect == null ? 0 :
			// warrVect.size()));
			if (!warrVect.isEmpty()) {
				eiWarr = (EntityItem) warrVect.elementAt(0);
				if (eiWarr == null) {
					if (modelWarrGrp != null && modelWarrGrp.size() > 0) {
						eiWarr = (EntityItem) modelWarrGrp.get(0);
						strWarrID = PokUtils.getAttributeValue(eiWarr, "WARRID", "", "");
						if (strWarrID.equals("WTY0000")) {
							if (modelWarrGrp.size() > 1) {
								eiWarr = (EntityItem) modelWarrGrp.get(1);
							} else {
								eiWarr = null;
							}
						}
					}
				} else {
					strWarrID = PokUtils.getAttributeValue(eiWarr, "WARRID", "", "");
					if (strWarrID.equals("WTY0000")) {
						if (warrVect.size() > 1) {
							eiWarr = (EntityItem) warrVect.elementAt(1);
						} else {
							eiWarr = null;
						}
					}
				}
			} else {
				if (modelWarrGrp != null && modelWarrGrp.size() > 0) {
					eiWarr = (EntityItem) modelWarrGrp.get(0);
					strWarrID = PokUtils.getAttributeValue(eiWarr, "WARRID", "", "");
					if (strWarrID.equals("WTY0000")) {
						if (modelWarrGrp.size() > 1) {
							eiWarr = (EntityItem) modelWarrGrp.get(1);
						} else {
							eiWarr = null;
						}
					}
				}
			}

			if (eiWarr != null) {
				EANFlagAttribute warrTypeList = (EANFlagAttribute) eiWarr.getAttribute("WARRTYPE");
				if (warrTypeList != null) {
					if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
						if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")
								|| warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
								|| warrTypeList.isSelected("W0250")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strAvailGenAreaSel.equals("Latin America")) {
						if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")
								|| warrTypeList.isSelected("W0560") || warrTypeList.isSelected("W0570")
								|| warrTypeList.isSelected("W0580")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strAvailGenAreaSel.equals("Asia Pacific")) {
						if (warrTypeList.isSelected("W0550") || warrTypeList.isSelected("W0390")
								|| warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
								|| warrTypeList.isSelected("W0250") || warrTypeList.isSelected("W0310")
								|| warrTypeList.isSelected("W0330") || warrTypeList.isSelected("W0590")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strAvailGenAreaSel.equals("Canada and Caribbean North")
							|| strAvailGenAreaSel.equals("US Only")) {
						strFSLM2CF = "N";
					}
				}
			} else {
				strFSLM2CF = "N";
			}
			sb.append(getValue(FSLM2CF, strFSLM2CF));
			// sb.append(getValue(IDORIG,
			// PokUtils.getAttributeValue(rootEntity,
			// "ANNTYPE", ",", "", false)));
			sb.append(getValue(IDORIG, "IBM"));
			strPCUAEAP = "000";
			strPCUAHEA = "000";
			strPCUASEA = "000";
			strPCUAUEA = "000";

			if (strAvailGenAreaSel.equals("US Only") || strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strPCUAEAP = "000";
				strPCUAHEA = "000";
				strPCUASEA = "000";
				strPCUAUEA = "000";
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				strPCUAEAP = " @@";
				strPCUAHEA = " @@";
				strPCUASEA = " @@";
				strPCUAUEA = " @@";
			} else {
				if (eiModGeoMod != null) {
					// strPCUAEAP =
					// PokUtils.getAttributeValue(eiModGeoMod,
					// "EDUCPURCHELIG", ",", "", false);
					strPCUAEAP = getNumValue(PCUAEAP,
							PokUtils.getAttributeValue(eiModGeoMod, "EDUCALLOWMHGHSCH", ",", "", false));
					strPCUAHEA = getNumValue(PCUAHEA,
							PokUtils.getAttributeValue(eiModGeoMod, "EDUCALLOWMHGHSCH", ",", "", false));
					strPCUASEA = getNumValue(PCUASEA,
							PokUtils.getAttributeValue(eiModGeoMod, "EDUCALLOWMSECONDRYSCH", ",", "", false));
					strPCUAUEA = getNumValue(PCUAUEA,
							PokUtils.getAttributeValue(eiModGeoMod, "EDUCALLOWMUNVRSTY", ",", "", false));
				}
			}

			sb.append(getValue(PCUAEAP, strPCUAEAP));
			sb.append(getValue(PCUAHEA, strPCUAHEA));
			sb.append(getValue(PCUASEA, strPCUASEA));
			sb.append(getValue(PCUAUEA, strPCUAUEA));

			sb.append(getValue(POGMES, ""));

			String psInstall = PokUtils.getAttributeValue(eiProdstruct, "INSTALL", "", "");
			if (psInstall.equals("CIF")) {
				if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")
						|| strAvailGenAreaSel.equals("Latin America")) {
					strQSLMCSU = "01";
				} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
					strQSLMCSU = "10";
				} else if (strAvailGenAreaSel.equals("US Only")
						|| strAvailGenAreaSel.equals("Canada and Caribbean North")) {
					strQSLMCSU = "";
				}
			} else if (psInstall.equals("CE") || psInstall.equals("N/A") || psInstall.equals("Does not apply")) {
				strQSLMCSU = "";
			}
			sb.append(getValue(QSLMCSU, strQSLMCSU));

			sb.append(getValue(QSMXESA, "N"));
			sb.append(getValue(QSMXSSA, "N"));

			String strInvname = PokUtils.getAttributeValue(eiFeature, "INVNAME", ",", "", false);
			sb.append(getValue(TSLMDES, removeSpecialChars(strInvname)));

			strSTSPCFT = "";

			if (strFeatureType.equals("S")) {
				strSTSPCFT = "OTH";
			}

			sb.append(getValue(STSPCFT, strSTSPCFT));
			sb.append(getValue(TIMSTMP, ""));
			sb.append(getValue(USERID, ""));

			warrTypeArray = new ArrayList();
			strCSLMTM1 = "";
			strCSLMTM2 = "";
			strCSLMTM3 = "";
			strCSLMTM4 = "";
			strCSLMTM5 = "";
			strCSLMTM6 = "";
			strCSLMTM7 = "";
			strCSLMTM8 = "";

			if (strAvailGenAreaSel.equals("Asia Pacific")) {
				if (eiWarr != null) {
					EANFlagAttribute warrTypeList = (EANFlagAttribute) eiWarr.getAttribute("WARRTYPE");
					if (warrTypeList != null) {
						if (warrTypeList.isSelected("W0560") || warrTypeList.isSelected("W0570")
								|| warrTypeList.isSelected("W0580")) {
							warrTypeArray.add("IOR");
						}
						if (warrTypeList.isSelected("W0550")) {
							warrTypeArray.add("IOE");
						}
						if (warrTypeList.isSelected("W0390")) {
							warrTypeArray.add("COE");
						}
						if (warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
								|| warrTypeList.isSelected("W0250")) {
							warrTypeArray.add("CCE");
						}
						if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")) {
							warrTypeArray.add("CCR");
						}
						if (warrTypeList.isSelected("W0590")) {
							warrTypeArray.add("IOS");
						}
						// TODO - ICE - IBM Courier Exchange - VALUE
						// DOES
						// NOT
						// EXIST IN EACM
						// if (warrTypeList.isSelected("W0310") ||
						// warrTypeList.isSelected("W0330")) {
						// warrTypeArray.add("ICE");
						// }
						// TODO - ICS - IBM Courier Service - VALUE DOES
						// NOT
						// EXIST IN EACM
						// if (warrTypeList.isSelected("W0310") ||
						// warrTypeList.isSelected("W0330")) {
						// warrTypeArray.add("ICS");
						// }
						// TODO - IE8 - Annual Minimum Maintenance Next
						// Day
						// 9x5
						// - VALUE DOES NOT EXIST IN EACM
						// if (warrTypeList.isSelected("W0310") ||
						// warrTypeList.isSelected("W0330")) {
						// warrTypeArray.add("IE8");
						// }
						// TODO - CES - Depot Exchange Offering - VALUE
						// DOES
						// NOT
						// EXIST IN EACM
						// if (warrTypeList.isSelected("W0310") ||
						// warrTypeList.isSelected("W0330")) {
						// warrTypeArray.add("CES");
						// }
						// TODO - CFM - Central Facility Maintenance -
						// VALUE
						// DOES NOT EXIST IN EACM
						// if (warrTypeList.isSelected("W0310") ||
						// warrTypeList.isSelected("W0330")) {
						// warrTypeArray.add("CFM");
						// }

						for (int w = 0; w < warrTypeArray.size(); w++) {
							if (w == 0) {
								strCSLMTM1 = (String) warrTypeArray.get(w);
							} else if (w == 1) {
								strCSLMTM2 = (String) warrTypeArray.get(w);
							} else if (w == 2) {
								strCSLMTM3 = (String) warrTypeArray.get(w);
							} else if (w == 3) {
								strCSLMTM4 = (String) warrTypeArray.get(w);
							} else if (w == 4) {
								strCSLMTM5 = (String) warrTypeArray.get(w);
							} else if (w == 5) {
								strCSLMTM6 = (String) warrTypeArray.get(w);
							} else if (w == 6) {
								strCSLMTM7 = (String) warrTypeArray.get(w);
							} else if (w == 7) {
								strCSLMTM8 = (String) warrTypeArray.get(w);
							}
						}
					}
				}
			}

			sb.append(getValue(CSLMTM1, strCSLMTM1));
			sb.append(getValue(CSLMTM2, strCSLMTM2));
			sb.append(getValue(CSLMTM3, strCSLMTM3));
			sb.append(getValue(CSLMTM4, strCSLMTM4));
			sb.append(getValue(CSLMTM5, strCSLMTM5));
			sb.append(getValue(CSLMTM6, strCSLMTM6));
			sb.append(getValue(CSLMTM7, strCSLMTM7));
			sb.append(getValue(CSLMTM8, strCSLMTM8));

			sb.append(getValue(FSAPRES, "N"));

			/*
			 * Code changed as requested if (eiWarr != null){ if
			 * (strAvailGenAreaSel.equals("US Only")) {
			 * sb.append(getValue(CSLMWCD, PokUtils.getAttributeValue(eiWarr,
			 * "WARRCATG", "", ""))); } else { sb.append(getValue(CSLMWCD,
			 * " ")); } } else { sb.append(getValue(CSLMWCD, " ")); }
			 */

			if (strAvailGenAreaSel.equals("US Only")) {
				EntityItem eiWarr1 = null;
				strWARRSVCCOVR = PokUtils.getAttributeValue(eiModel, "WARRSVCCOVR", "", "");
				if (strWARRSVCCOVR != null) {
					if (strWARRSVCCOVR.equals("No Warranty") || strWARRSVCCOVR.equals("")) {
						strCSLMWCD = "Z";
					} else {
						if (modelWarrGrp != null && modelWarrGrp.size() > 0) {
							eiWarr1 = (EntityItem) modelWarrGrp.get(0);
							// Vector warrVect1 =
							// PokUtils.getAllLinkedEntities(eiModel,
							// "MODELWARR", "WARR");
							if (eiWarr1 != null) {
								String strWarrID1 = PokUtils.getAttributeValue(eiWarr1, "WARRID", "", "");
								if (strWarrID1.equals("WTY0000")) {
									if (modelWarrGrp.size() > 1) {
										eiWarr1 = (EntityItem) modelWarrGrp.get(1);
									} else {
										eiWarr1 = null;
									}
								}
							}
							/** code changed as requested */
							if (eiWarr1 != null) {
								strCSLMWCD = PokUtils.getAttributeValue(eiWarr1, "WARRCATG", "", "");
							} else {
								strCSLMWCD = "";
							}
						} else {
							strCSLMWCD = "";
						}
					}
				} else {
					strCSLMWCD = "Z";
				}
			} else {
				strCSLMWCD = "";
			}

			sb.append(getValue(CSLMWCD, strCSLMWCD));

			if (strAvailGenAreaSel.equals("US Only")) {
				String strCUSAPMS = PokUtils.getAttributeValue(eiModel, "MAINTANNBILLELIGINDC", ",", "", false);
				if (strCUSAPMS.equals("Yes")) {
					sb.append(getValue(CUSAPMS, "Y"));
				} else if (strCUSAPMS.equals("No")) {
					sb.append(getValue(CUSAPMS, "X"));
				} else {
					sb.append(getValue(CUSAPMS, ""));
				}
			} else {
				sb.append(getValue(CUSAPMS, ""));
			}

			sb.append(getValue(DUSALRW, "2050-12-31"));
			sb.append(getValue(DUSAMDW, "2050-12-31"));
			sb.append(getValue(DUSAWUW, "2050-12-31"));

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(getValue(FSLMCBL, "N"));
			} else {
				sb.append(getValue(FSLMCBL, ""));
			}

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(getValue(FUSAAAS, "Y"));
			} else {
				sb.append(getValue(FUSAAAS, ""));
			}

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(getValue(FUSAEDE, strFUSAEDE));
			} else {
				sb.append(getValue(FUSAEDE, ""));
			}

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(
						getValue(FUSALEP, PokUtils.getAttributeValue(eiModel, "MAINTANNBILLELIGINDC", ",", "", false)));
			} else {
				sb.append(getValue(FUSALEP, " "));
			}

			if (strAvailGenAreaSel.equals("US Only")) {
				sb.append(getValue(FUSAIRR, "N"));
			} else {
				sb.append(getValue(FUSAIRR, ""));
			}
			// sb.append(getValue(FUSAIRR,
			// PokUtils.getAttributeValue(eiProdstruct, "RETURNEDPARTS",
			// ",",
			// "", false)));

			if (eiModGeoMod != null) {
				sb.append(getValue(ICESPCC, PokUtils.getAttributeValue(eiModGeoMod, "PERCALLCLS", ",", "", false)));
			} else {
				sb.append(getValue(ICESPCC, ""));
			}
			sb.append(getValue(QUSAPOP, "00.0"));
			sb.append(getValue(FSLMRFM, ""));
			addDebug(sb.toString() + " AVAIL：" + avail.getEntityID() + " FEATURE：" + eiFeature.getEntityID()
					+ " PRODUCT:" + eiProdstruct.getEntityID());
			sb.append(NEWLINE);
			wOut.write(sb.toString());
			wOut.flush();

		}

	}

	private EntityItem getModelEntityFromTmf(EntityItem tmfEntity) {
		EntityItem modelItem = null;
		Vector linkVct = tmfEntity.getDownLink();
		if (linkVct != null && linkVct.size() > 0) {
			for (int k = 0; k < linkVct.size(); k++) {
				EntityItem item = (EntityItem) linkVct.get(k);
				if ("MODEL".equals(item.getEntityType())) {
					modelItem = item;
					// addDebug("tmf linked model info:"+modelItem.getKey());
					break;
				}
			}
		}
		return modelItem;
	}

	private Vector getTMFFromFeature(EntityItem eiFeature) {

		Vector vct = new Vector();
		Vector linkVct = eiFeature.getDownLink();
		if (linkVct != null && linkVct.size() > 0) {
			for (int k = 0; k < linkVct.size(); k++) {
				EntityItem item = (EntityItem) linkVct.get(k);
				if ("PRODSTRUCT".equals(item.getEntityType()) && !vct.contains(item)) {
					vct.add(item);
				}
			}
		}

		return vct;

	}

	private boolean isQSMGeoSelected(String strAvailGeo, EANFlagAttribute availGeoList) {

		if (availGeoList != null) {
			if (strAvailGeo.equals("Asia Pacific") && availGeoList.isSelected("6199")) {
				return true;
			}

			if (strAvailGeo.equals("Canada and Caribbean North") && availGeoList.isSelected("6200")) {
				return true;
			}

			if (strAvailGeo.equals("Europe/Middle East/Africa") && availGeoList.isSelected("6198")) {
				return true;
			}

			if (strAvailGeo.equals("Latin America") && availGeoList.isSelected("6204")) {
				return true;
			}

			if (strAvailGeo.equals("US Only") && availGeoList.isSelected("6221")) {
				return true;
			}
		}

		addDebug("***** isQSMGeoSelected false");
		return false;

	}

	/********
	 * Create T017 Records - ANNOUNCEMENT -> AVAIL -> MODELAVAIL -> MODEL ->
	 * PRODSTRUCT -> FEATURE
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void createT017ProductCategory(EntityItem rootEntity, OutputStreamWriter wOut)
			throws SQLException, MiddlewareException, IOException {

		EANFlagAttribute qsmGeoList;

		m_elist = getEntityList(getModelProdstructVEName());

		EntityGroup availGrp = (EntityGroup) m_elist.getEntityGroup("AVAIL");

		for (int iAvail = 0; iAvail < availGrp.getEntityItemCount(); iAvail++) {
			qsmGeoList = null;

			EntityItem eiAvail = availGrp.getEntityItem(iAvail);

			qsmGeoList = (EANFlagAttribute) eiAvail.getAttribute("QSMGEO");
			if (qsmGeoList != null) {
				if (qsmGeoList.isSelected("6199")) {
					createT017ProductCategoryRecords(rootEntity, wOut, eiAvail, "Asia Pacific");
				}
				if (qsmGeoList.isSelected("6200")) {
					createT017ProductCategoryRecords(rootEntity, wOut, eiAvail, "Canada and Caribbean North");
				}
				if (qsmGeoList.isSelected("6198")) {
					createT017ProductCategoryRecords(rootEntity, wOut, eiAvail, "Europe/Middle East/Africa");
				}
				if (qsmGeoList.isSelected("6204")) {
					createT017ProductCategoryRecords(rootEntity, wOut, eiAvail, "Latin America");
				}
				if (qsmGeoList.isSelected("6221")) {
					createT017ProductCategoryRecords(rootEntity, wOut, eiAvail, "US Only");
				}
			}
		}
	}

	/********
	 * Create T017 Records - ANNOUNCEMENT -> AVAIL -> MODELAVAIL -> MODEL ->
	 * PRODSTRUCT -> FEATURE
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void createT017ProductCategoryRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem eiAvail,
			String strAvailGenAreaSel) throws SQLException, MiddlewareException, IOException {

		StringBuffer sb;
		String strISLMPAL;
		String strISLMPRN;
		String tmp;

		EntityGroup modelGrp = m_elist.getEntityGroup("MODEL");

		for (int i = 0; i < modelGrp.getEntityItemCount(); i++) {
			sb = new StringBuffer();
			strISLMPAL = "";
			strISLMPRN = "";
			tmp = "";
			// EntityItem eiModel = (EntityItem) modelVec.elementAt(i);
			EntityItem eiModel = modelGrp.getEntityItem(i);

			tmp = PokUtils.getAttributeValue(rootEntity, "PRODCATEGORY", ",", "", false);

			if (tmp != null && tmp.length() > 0) {
				String[] prodCategoryArray = tmp.split(",");
				for (int j = 0; j < prodCategoryArray.length; j++) {

					sb.append(getValue(IFTYPE, "P"));
					sb.append(getValue(CPDXA, prodCategoryArray[j]));
					if (strAvailGenAreaSel.equals("Latin America")) {
						strISLMPAL = PokUtils.getAttributeValue(rootEntity, "LDOCNO", "", "");
					} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
						strISLMPAL = PokUtils.getAttributeValue(rootEntity, "EDOCNO", "", "");
					} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
						strISLMPAL = PokUtils.getAttributeValue(rootEntity, "ADOCNO", "", "");
					} else if (strAvailGenAreaSel.equals("US Only")) {
						strISLMPAL = PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
					} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
						strISLMPAL = PokUtils.getAttributeValue(rootEntity, "CDOCNO", "", "");
					}
					sb.append(getValue(ISLMPAL, strISLMPAL));

					strISLMPRN = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
					strISLMPRN += PokUtils.getAttributeValue(eiModel, "MODELATR", "", "");
					sb.append(getValue(ISLMPRN, strISLMPRN));

					sb.append(getValue(TIMSTMP, ""));
					sb.append(getValue(USERID, ""));

					sb.append(NEWLINE);
					wOut.write(sb.toString());
					wOut.flush();

					Vector featVect = PokUtils.getAllLinkedEntities(eiModel, "PRODSTRUCT", "FEATURE");
					for (int iFeat = 0; iFeat < featVect.size(); iFeat++) {
						EntityItem eiFeature = (EntityItem) featVect.elementAt(iFeat);
						sb = new StringBuffer();
						// strISLMPAL = "";
						strISLMPRN = "";

						sb.append(getValue(IFTYPE, "P"));
						sb.append(getValue(CPDXA,
								PokUtils.getAttributeValue(rootEntity, "PRODCATEGORY", ",", "", false)));

						sb.append(getValue(ISLMPAL, strISLMPAL));

						strISLMPRN = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "");
						strISLMPRN += PokUtils.getAttributeValue(eiFeature, "FEATURECODE", "", "");
						sb.append(getValue(ISLMPRN, strISLMPRN));

						sb.append(getValue(TIMSTMP, ""));
						sb.append(getValue(USERID, ""));

						sb.append(NEWLINE);
						wOut.write(sb.toString());
						wOut.flush();
					}
				}
			}
		}
	}

	/******
	 * Create T020 NP Mes Upgrade records
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void createT020NPMesUpgrade(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		EANFlagAttribute qsmGeoList;

		m_elist = getEntityList(getNPMesUpgradeVEName());

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");

		String strAvailAnnType = "";
		boolean isEpic = false;

		for (int iAvail = 0; iAvail < availGrp.getEntityItemCount(); iAvail++) {
			qsmGeoList = null;

			EntityItem eiAvail = (EntityItem) availGrp.getEntityItem(iAvail);

			strAvailAnnType = PokUtils.getAttributeValue(eiAvail, "AVAILANNTYPE", "", "");
			if (strAvailAnnType.equals("EPIC")) {
				isEpic = true;
			}

			qsmGeoList = (EANFlagAttribute) eiAvail.getAttribute("QSMGEO");
			if (qsmGeoList != null) {
				if (qsmGeoList.isSelected("6199")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Asia Pacific", isEpic);
				}
				if (qsmGeoList.isSelected("6200")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Canada and Caribbean North", isEpic);
				}
				if (qsmGeoList.isSelected("6198")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Europe/Middle East/Africa", isEpic);
				}
				if (qsmGeoList.isSelected("6204")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "Latin America", isEpic);
				}
				if (qsmGeoList.isSelected("6221")) {
					createT020NPMesUpgradeRecords(rootEntity, wOut, eiAvail, "US Only", isEpic);
				}
			}
		}
	}

	/******
	 * Create T020 NP Mes Upgrade records
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws MiddlewareException
	 * @throws SQLException
	 */
	private void createT020NPMesUpgradeRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem eiAvail,
			String strAvailGenAreaSel, boolean isEpic) throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strISLMPAL;
		String strISLMPRN;
		String strIOPUCTY;
		String strISLMRFA;
		String strFromMachType;
		String strFromModel;

		sb = new StringBuffer();
		strISLMPAL = "";
		strISLMPRN = "";
		strIOPUCTY = "";
		strISLMRFA = "";

		Vector modelConvertVect = PokUtils.getAllLinkedEntities(eiAvail, "MODELCONVERTAVAIL", "MODELCONVERT");

		for (int indM = 0; indM < modelConvertVect.size(); indM++) {
			strFromMachType = "";
			strFromModel = "";

			EntityItem eiModelConvert = (EntityItem) modelConvertVect.elementAt(indM);

			sb.append(getValue(IFTYPE, "N"));
			EntityItem searchAvail1 = null;
			String strGenArea = "";
			String strDSLMWDNLA = "";
			String strDSLMWDNEMEA = "";
			String strDSLMWDNAP = "";
			String strDSLMWDNUS = "";
			String strDSLMWDNCCN = "";
			String strDSLMWDN = "";
			String strDSLMWDN1 = "";
			List searchAvail = searchForAvailTypeLO(eiModelConvert, "Last Order");
			if (searchAvail != null) {

				for (int j = 0; j < searchAvail.size(); j++) {
					searchAvail1 = (EntityItem) searchAvail.get(j);
					addDebug("***** modelconvert avail list= " + searchAvail1);
					strGenArea = PokUtils.getAttributeValue(searchAvail1, "GENAREASELECTION", ",", "");
					if (strGenArea.indexOf("Latin America") != -1) {
						strDSLMWDNLA = PokUtils.getAttributeValue(searchAvail1, "EFFECTIVEDATE", "", "");
						// addDebug("***** modelconvert wd LA= " +
						// strDSLMWDNLA);
					}
					if (strGenArea.indexOf("Europe/Middle East/Africa") != -1) {
						strDSLMWDNEMEA = PokUtils.getAttributeValue(searchAvail1, "EFFECTIVEDATE", "", "");
						// addDebug("***** modelconvert wd EMEA= " +
						// strDSLMWDNEMEA);
					}
					if (strGenArea.indexOf("Asia Pacific") != -1) {
						strDSLMWDNAP = PokUtils.getAttributeValue(searchAvail1, "EFFECTIVEDATE", "", "");
						// addDebug("***** modelconvert wd AP= " +
						// strDSLMWDNAP);
					}
					if (strGenArea.indexOf("US Only") != -1) {
						strDSLMWDNUS = PokUtils.getAttributeValue(searchAvail1, "EFFECTIVEDATE", "", "");
						// addDebug("***** modelconvert wd US= " +
						// strDSLMWDNUS);
					}
					if (strGenArea.indexOf("Canada and Caribbean North") != -1) {
						strDSLMWDNCCN = PokUtils.getAttributeValue(searchAvail1, "EFFECTIVEDATE", "", "");
						// addDebug("***** modelconvert wd CCN= " +
						// strDSLMWDNCCN);
					}
				}
			} else {
				strDSLMWDN = "2050-12-31";
			}

			if (strAvailGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "LDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
				strDSLMWDN1 = strDSLMWDNLA;
				addDebug("***** modelconvert LA= " + strDSLMWDN1);
			} else if (strAvailGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "EDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
				strDSLMWDN1 = strDSLMWDNEMEA;
				addDebug("***** modelconvert EMEA= " + strDSLMWDN1);
			} else if (strAvailGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "ADOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
				strDSLMWDN1 = strDSLMWDNAP;
				addDebug("***** modelconvert AP= " + strDSLMWDN1);
			} else if (strAvailGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
				strDSLMWDN1 = strDSLMWDNUS;
				addDebug("***** modelconvert US= " + strDSLMWDN1);

			} else if (strAvailGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "CDOCNO", "", "");
				strISLMRFA = isEpic ? PokUtils.getAttributeValue(eiAvail, "EPICNUMBER", "", "")
						: PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
				strDSLMWDN1 = strDSLMWDNCCN;
				addDebug("***** modelconvert CCN= " + strDSLMWDN1);
			}

			sb.append(getValue(IOPUCTY, strIOPUCTY));
			sb.append(getValue(ISLMPAL, strISLMPAL));
			sb.append(getValue(ISLMRFA, strISLMRFA));

			strISLMPRN = PokUtils.getAttributeValue(eiModelConvert, "TOMACHTYPE", "", "");
			strISLMPRN += PokUtils.getAttributeValue(eiModelConvert, "TOMODEL", "", "");
			sb.append(getValue(ISLMPRN, strISLMPRN));
			sb.append(getValue(CSLMPCI, "NP"));
			sb.append(getValue(FPUNINC, "2"));
			sb.append(getValue(CAOAV, ""));
			sb.append(getValue(DSLMCPA, PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", "")));
			sb.append(getValue(DSLMCPO, PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", "")));
			if (strDSLMWDN1 != "") {
				strDSLMWDN = strDSLMWDN1;
			} else {
				strDSLMWDN = "2050-12-31";
			}
			sb.append(getValue(DSLMWDN, strDSLMWDN));

			addDebug("***** modelconvert WD date= " + strDSLMWDN);

			strFromMachType = PokUtils.getAttributeValue(eiModelConvert, "FROMMACHTYPE", "", "");
			strFromModel = PokUtils.getAttributeValue(eiModelConvert, "FROMMODEL", "", "");
			sb.append(getValue(QSMNPMT, strFromMachType));
			sb.append(getValue(QSMNPMM, strFromModel));
			sb.append(getValue(TIMSTMP, ""));
			sb.append(getValue(USERID, ""));

			sb.append(NEWLINE);
			wOut.write(sb.toString());
			wOut.flush();
		}
	}

	private List searchForAvailTypeLO(EntityItem eiModelConvert, String strSearchAvailType) {

		ArrayList eiReturn = new ArrayList();
		String strAvailType;

		Vector availVect = PokUtils.getAllLinkedEntities(eiModelConvert, "MODELCONVERTAVAIL", "AVAIL");

		for (int i = 0; i < availVect.size(); i++) {
			EntityItem eiAvail = (EntityItem) availVect.elementAt(i);

			strAvailType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", ",", "", false);

			if (strSearchAvailType.equals(strAvailType)) {
				eiReturn.add(eiAvail);
				break;
			}
		}

		return eiReturn;
	}

	private void createT512ReleaseTo(EntityItem rootEntity, OutputStreamWriter wOut) throws IOException {

		StringBuffer sb = new StringBuffer();
		String strISLMPAL = "";
		String strISLMRFA = "";

		// Create T512 / R records only for AP and Worldwide Geos
		EANFlagAttribute genAreaList = (EANFlagAttribute) rootEntity.getAttribute("GENAREASELECTION");
		if (genAreaList != null) {
			if (genAreaList.isSelected("6199") || genAreaList.isSelected("1999")) {
				sb.append(getValue(IFTYPE, "R"));
				sb.append(getValue(IOPUCTY, "872"));
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "ADOCNO", "", "");
				sb.append(getValue(ISLMPAL, strISLMPAL));

				strISLMRFA = PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
				sb.append(getValue(ISLMRFA, strISLMRFA));
				sb.append(getValue(DSLMCPA, PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", "")));
				sb.append(getValue(DSLMEFF, PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", "")));
				sb.append(getValue(CSLMRCH, ""));
				sb.append(getValue(CSLMNUM, strISLMPAL));
				sb.append(getValue(FSLMAPG, "Y"));
				sb.append(getValue(FSLMASP, "N"));
				sb.append(getValue(FSLMJAP, "N"));
				if (genAreaList != null) {

					EANFlagAttribute cntryList = (EANFlagAttribute) rootEntity.getAttribute("COUNTRYLIST");

					if (cntryList.isSelected("1439")) {
						sb.append(getValue(FSLMAUS, "Y"));
					} else {
						sb.append(getValue(FSLMAUS, "N"));
					}

					if (cntryList.isSelected("1444")) {
						sb.append(getValue(FSLMBGL, "Y"));
					} else {
						sb.append(getValue(FSLMBGL, "N"));
					}
					// TODO countrylist Brunai doesn't exit, please check - set
					// to N
					// if (cntryList.isSelected("?")) {
					sb.append(getValue(FSLMBRU, "N"));
					// } else {
					// sb.append(getValue(FSLMBRU, "N"));
					// }
					if (cntryList.isSelected("1524")) {
						sb.append(getValue(FSLMHKG, "Y"));
					} else {
						sb.append(getValue(FSLMHKG, "N"));
					}
					if (cntryList.isSelected("1528")) {
						sb.append(getValue(FSLMIDN, "Y"));
					} else {
						sb.append(getValue(FSLMIDN, "N"));
					}
					if (cntryList.isSelected("1527")) {
						sb.append(getValue(FSLMIND, "Y"));
					} else {
						sb.append(getValue(FSLMIND, "N"));
					}
					if (cntryList.isSelected("1541")) {
						sb.append(getValue(FSLMKOR, "Y"));
					} else {
						sb.append(getValue(FSLMKOR, "N"));
					}
					if (cntryList.isSelected("1553")) {
						sb.append(getValue(FSLMMAC, "Y"));
					} else {
						sb.append(getValue(FSLMMAC, "N"));
					}
					if (cntryList.isSelected("1557")) {
						sb.append(getValue(FSLMMAL, "Y"));
					} else {
						sb.append(getValue(FSLMMAL, "N"));
					}
					if (cntryList.isSelected("1574")) {
						sb.append(getValue(FSLMMYA, "Y"));
					} else {
						sb.append(getValue(FSLMMYA, "N"));
					}
					if (cntryList.isSelected("1581")) {
						sb.append(getValue(FSLMNZL, "Y"));
					} else {
						sb.append(getValue(FSLMNZL, "N"));
					}
					if (cntryList.isSelected("1597")) {
						sb.append(getValue(FSLMPHI, "Y"));
					} else {
						sb.append(getValue(FSLMPHI, "N"));
					}
					if (cntryList.isSelected("1470")) {
						sb.append(getValue(FSLMPRC, "Y"));
					} else {
						sb.append(getValue(FSLMPRC, "N"));
					}
					if (cntryList.isSelected("1627")) {
						sb.append(getValue(FSLMSLA, "Y"));
					} else {
						sb.append(getValue(FSLMSLA, "N"));
					}
					if (cntryList.isSelected("1619")) {
						sb.append(getValue(FSLMSNG, "Y"));
					} else {
						sb.append(getValue(FSLMSNG, "N"));
					}
					if (cntryList.isSelected("1635")) {
						sb.append(getValue(FSLMTAI, "Y"));
					} else {
						sb.append(getValue(FSLMTAI, "N"));
					}
					if (cntryList.isSelected("1638")) {
						sb.append(getValue(FSLMTHA, "Y"));
					} else {
						sb.append(getValue(FSLMTHA, "N"));
					}
				}
				sb.append(getValue(TIMSTMP, " "));
				sb.append(getValue(USERID, " "));

				sb.append(NEWLINE);
				wOut.write(sb.toString());
				wOut.flush();
			}
		}
	}

	/******
	 * Create T632 records
	 * 
	 * @param rootEntity
	 * @param wOut
	 * @throws IOException
	 * @throws SQLException
	 * @throws MiddlewareException
	 */
	private void createT632TypeModelFeatureRelation(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strISLMRFA;
		String strISLMPAL;
		String strOrderCode;
		String strQSLMCSU;
		String strAvailType;
		String strAvailAnnType = "";
		boolean isEpic = false;

		m_elist = getEntityList(getT006ProdstructVEName());

		EntityGroup availGrp = m_elist.getEntityGroup("AVAIL");
		for (int availI = 0; availI < availGrp.getEntityItemCount(); availI++) {
			EntityItem availEI = availGrp.getEntityItem(availI);
			
			// Create T632 records only for US ONLY and WorldWide GEOs
			EANFlagAttribute qsmGeoList = (EANFlagAttribute) availEI.getAttribute("QSMGEO");
			//addDebug("qsmGeoList != null:"+(qsmGeoList != null)+"  AVAIL:"+availEI.getEntityID());
			if (qsmGeoList != null) {
				//addDebug("qsmGeoList.isSelected 6221:"+(qsmGeoList.isSelected("6221")));
				if (qsmGeoList.isSelected("6221")) {

					Vector prodstructVect = PokUtils.getAllLinkedEntities(availEI, "OOFAVAIL", "PRODSTRUCT");

					/*Vector lVector = new Vector();
					getLinkedEntities(availEI,  "OOFAVAIL", "PRODSTRUCT",lVector);*/
					strAvailType = "";
					strAvailType = PokUtils.getAttributeValue(availEI, "AVAILTYPE", "", "");
					strAvailAnnType = PokUtils.getAttributeValue(availEI, "AVAILANNTYPE", "", "");
					if (strAvailAnnType.equals("EPIC")) {
						isEpic = true;
					}

					for (int i = 0; i < prodstructVect.size(); i++) {
						
						sb = new StringBuffer();
						EntityItem eiProdstruct = (EntityItem) prodstructVect.elementAt(i);
						//addDebug("PRODSTRUCT ID:"+eiProdstruct.getEntityID());
						ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, getT006FeatureVEName());

						EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] { new EntityItem(null,
								m_prof, eiProdstruct.getEntityType(), eiProdstruct.getEntityID()) });

						EntityGroup featureGrp = list.getEntityGroup("FEATURE");
						EntityGroup modelGrp = list.getEntityGroup("MODEL");
						EntityItem eiFeature = featureGrp.getEntityItem(0);
						EntityItem eiModel = modelGrp.getEntityItem(0);

						sb = new StringBuffer();
						strISLMRFA = "";
						strISLMPAL = "";
						strOrderCode = "";
						strQSLMCSU = "";

						sb.append(getValue(IFTYPE, "T"));
						strISLMPAL = PokUtils.getAttributeValue(rootEntity, "USDOCNO", "", "");
						// strISLMRFA = PokUtils.getAttributeValue(rootEntity,
						// "USDOCNO", "", "");
						// Code updated as requested
						// strISLMRFA = PokUtils.getAttributeValue(rootEntity,
						// "EPICNUMBER", "", "");
						strISLMRFA = getRFANumber(rootEntity, isEpic, availEI);
						addDebug("*****mlm ISLMRFA=" + strISLMRFA);
						sb.append(getValue(IOPUCTY, "897"));
						sb.append(getValue(ISLMPAL, strISLMPAL));
						sb.append(getValue(ISLMRFA, strISLMRFA));
						sb.append(getValue(ISLMTYP, PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "")));
						sb.append(getValue(ISLMMOD, PokUtils.getAttributeValue(eiModel, "MODELATR", "", "")));
						sb.append(getValue(ISLMFTR, PokUtils.getAttributeValue(eiFeature, "FEATURECODE", "", "")));
						sb.append(getValue(ISLMXX1, ""));
						sb.append(getValue(CSLMPCI, "TR"));
						sb.append(getValue(FPUNINC, "2"));
						sb.append(getValue(CAOAV, ""));
						sb.append(getValue(DSLMCPA, PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", "")));
						sb.append(getValue(DSLMCPO, PokUtils.getAttributeValue(rootEntity, "ANNDATE", "", "")));
						
						/*if (strAvailType.equals("Last Order")) {
							sb.append(PokUtils.getAttributeValue(availEI, "EFFECTIVEDATE", ",", "", false));
						} else {
							sb.append(getValue(DSLMWDN, "2050-12-31"));
						}*/
						sb.append(getValue(DSLMWDN,getTMFWDDate(eiProdstruct)));
						strOrderCode = PokUtils.getAttributeValue(eiProdstruct, "ORDERCODE", "", "");

						if (strOrderCode.equals("MES")) {
							sb.append(getValue(FSLMMES, "Y"));
						} else {
							sb.append(getValue(FSLMMES, "N"));
						}

						if (strOrderCode.equals("Initial")) {
							sb.append(getValue(FSLMPIO, "Y"));
						} else {
							sb.append(getValue(FSLMPIO, "N"));
						}

						String psInstall = PokUtils.getAttributeValue(eiProdstruct, "INSTALL", "", "");

						if (psInstall.equals("CIF")) {
							strQSLMCSU = "01";
						} else {
							strQSLMCSU = "00";
						}
						//addDebug(availEI.getEntityID()+":"+eiProdstruct.getEntityID()+":"+sb.toString());
						sb.append(getValue(QSLMCSU, strQSLMCSU));
						sb.append(getValue(TIMSTMP, ""));
						sb.append(getValue(USERID, ""));
						sb.append(getValue(FSLMRFM, ""));

						sb.append(NEWLINE);
						wOut.write(sb.toString());
						wOut.flush();

					}
				}
			}
		}
	}

	public String  getTMFWDDate(EntityItem tmfEI){
		Vector availVect = PokUtils.getAllLinkedEntities(tmfEI, "OOFAVAIL", "AVAIL");

		
		
		addDebug("TMF id "+tmfEI.getEntityID()+" link AVALI size:"+availVect.size());
		if(availVect.size()>0){
			for (int i = 0; i < availVect.size(); i++) {
				EntityItem eiAvail = (EntityItem) availVect.elementAt(i);
				

				String strAvailType = PokUtils.getAttributeValue(eiAvail, "AVAILTYPE", "", "");
				
				if (strAvailType.equals("Last Order")) {
					EANFlagAttribute qsmGeoList = (EANFlagAttribute) eiAvail.getAttribute("QSMGEO");
					if (qsmGeoList != null) {
						if (qsmGeoList.isSelected("6221")) {
					return PokUtils.getAttributeValue(eiAvail, "EFFECTIVEDATE", ",", "", false);
						}
					}
				} 
			}
		}
		return "2050-12-31";
	}
	private String getRFANumber(EntityItem rootEntity, boolean isEpic, EntityItem availEI) {
		String strISLMRFA;
		if (isEpic) {
			strISLMRFA = PokUtils.getAttributeValue(availEI, "EPICNUMBER", "", "");
		} else {
			strISLMRFA = "R" + PokUtils.getAttributeValue(rootEntity, "ANNNUMBER", "", "");
		}

		// strISLMRFA = isEpic ? PokUtils.getAttributeValue(availEI,
		// "EPICNUMBER", "", "") : PokUtils.getAttributeValue(rootEntity,
		// "R"+"ANNNUMBER", "", "");
		return strISLMRFA;
	}

	protected String getValue(String column, String columnValue) {
		if (columnValue == null)
			columnValue = "";
		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnLength = Integer.parseInt(COLUMN_LENGTH.get(column).toString());
		if (columnValueLength == columnLength)
			return columnValue;
		if (columnValueLength > columnLength)
			return columnValue.substring(0, columnLength);

		return columnValue + getBlank(columnLength - columnValueLength);
	}

	protected String getBlank(int count) {
		StringBuffer sb = new StringBuffer();
		while (count > 0) {
			sb.append(" ");
			count--;
		}
		return sb.toString();
	}

	private String getNumValue(String column, String columnValue) {

		if (columnValue == null)
			columnValue = "";
		int columnValueLength = columnValue == null ? 0 : columnValue.length();
		int columnLength = Integer.parseInt(COLUMN_LENGTH.get(column).toString());
		if (columnValueLength == columnLength)
			return columnValue;
		if (columnValueLength > columnLength)
			return columnValue.substring(0, columnLength);

		columnValue = columnValue.trim();
		int count = columnValueLength;
		while (count < columnLength) {
			columnValue = "0" + columnValue;
			count++;
		}

		return columnValue;

	}

	/******************************************
	 * get entitylist used for compares
	 */
	private EntityList getEntityList(String veName)
			throws java.sql.SQLException, COM.ibm.opicmpdh.middleware.MiddlewareException {
		ExtractActionItem eaItem = new ExtractActionItem(null, m_db, m_prof, veName);

		EntityList list = m_db.getEntityList(m_prof, eaItem,
				new EntityItem[] { new EntityItem(null, m_prof, getEntityType(), getEntityID()) });

		addDebug("EntityList for " + m_prof.getValOn() + " extract " + veName + " contains the following entities: \n"
				+ PokUtils.outputList(list));

		return list;
	}

	/**********************************
	 * get the name of the VE to use for MODEL records
	 * 
	 * @return java.lang.String
	 */
	public String getT002ModelVEName() {
		return "QSMFULL";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getT006FeatureVEName() {
		return "QSMFULL2";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getT006ProdstructVEName() {
		return "QSMFULL1";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getModelProdstructVEName() {
		return "QSMFULL3";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getNPMesUpgradeVEName() {
		return "QSMFULL4";
	}

	/**********************************
	 * get the name of the VE to use for FEATURE records
	 * 
	 * @return java.lang.String
	 */
	public String getT006ModelLinksVEName() {
		return "QSMFULL5";
	}

	/**********************************
	 * get the name of the VE to use for all records
	 * 
	 * @return java.lang.String
	 */
	public String getFeatureVEName() {
		return "QSMFULL6";
	}

	public boolean exeFtpShell(String fileName) {
		// String cmd =
		// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log
		// testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String cmd = ABRServerProperties.getValue(m_abri.getABRCode(), FTPSCRPATH, null) + " -f " + fileName;
		String ibiinipath = ABRServerProperties.getValue(m_abri.getABRCode(), CREFINIPATH, null);
		if (ibiinipath != null)
			cmd += " -i " + ibiinipath;
		if (dir != null)
			cmd += " -d " + dir;
		if (fileprefix != null)
			cmd += " -p " + fileprefix;
		String targetFilePath = ABRServerProperties.getValue(m_abri.getABRCode(), TARGETFILENAME, null);
		if (targetFilePath != null)
			cmd += " -t " + targetFilePath;
		String logPath = ABRServerProperties.getValue(m_abri.getABRCode(), LOGPATH, null);
		if (logPath != null)
			cmd += " -l " + logPath;
		String backupPath = ABRServerProperties.getValue(m_abri.getABRCode(), BACKUPPATH, null);
		if (backupPath != null)
			cmd += " -b " + backupPath;
		Runtime run = Runtime.getRuntime();
		String result = "";
		BufferedReader br = null;
		BufferedInputStream in = null;
		// addDebug("cmd:" + cmd);
		try {
			Process p = run.exec(cmd);
			if (p.waitFor() != 0) {
				return false;
			}
			in = new BufferedInputStream(p.getInputStream());
			br = new BufferedReader(new InputStreamReader(in));
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
				if (lineStr.indexOf(FAILD) > -1) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result == null ? false : true;
	}

	/**********************************
	 * add msg to report output
	 */
	protected void addOutput(String msg) {
		rptSb.append("<p>" + msg + "</p>" + NEWLINE);
	}

	/**********************************
	 * used for output
	 *
	 */
	protected void addOutput(String resrcCode, Object args[]) {
		String msg = getBundle().getString(resrcCode);
		if (args != null) {
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msg);
	}

	/**********************************
	 * add debug info as html comment
	 */
	protected void addDebug(String msg) {
		if (D.EBUG_DETAIL <= DEBUG_LVL) {

			rptSb.append("<!-- " + msg + " -->" + NEWLINE);
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
	 * used for error output Prefix with LD(EntityType) NDN(EntityType) of the
	 * EntityType that the ABR is checking (root EntityType)
	 *
	 * The entire message should be prefixed with 'Error: '
	 *
	 */
	protected void addError(String errCode, Object args[]) {
		EntityGroup eGrp = m_elist.getParentEntityGroup();
		setReturnCode(FAIL);

		// ERROR_PREFIX = Error: &quot;{0} {1}&quot;
		MessageFormat msgf = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
		Object args2[] = new Object[2];
		args2[0] = eGrp.getLongDescription();
		args2[1] = navName;

		addMessage(msgf.format(args2), errCode, args);
	}

	/**********************************
	 * used for warning or error output
	 *
	 */
	private void addMessage(String msgPrefix, String errCode, Object args[]) {
		String msg = getBundle().getString(errCode);
		// get message to output
		if (args != null) {
			MessageFormat msgf = new MessageFormat(msg);
			msg = msgf.format(args);
		}

		addOutput(msgPrefix + " " + msg);
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for root entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName() throws java.sql.SQLException, MiddlewareException {
		return getNavigationName(m_elist.getParentEntityGroup().getEntityItem(0));
	}

	/**********************************************************************************
	 * Get Name based on navigation attributes for specified entity
	 *
	 * @return java.lang.String
	 */
	private String getNavigationName(EntityItem theItem) throws java.sql.SQLException, MiddlewareException {
		StringBuffer navName = new StringBuffer();
		// NAME is navigate attributes
		// check hashtable to see if we already got this meta
		EANList metaList = (EANList) metaTbl.get(theItem.getEntityType());
		if (metaList == null) {
			EntityGroup eg = new EntityGroup(null, m_db, m_prof, theItem.getEntityType(), "Navigate");
			metaList = eg.getMetaAttribute(); // iterator does not maintain
												// navigate order
			metaTbl.put(theItem.getEntityType(), metaList);
		}
		for (int ii = 0; ii < metaList.size(); ii++) {
			EANMetaAttribute ma = (EANMetaAttribute) metaList.getAt(ii);
			navName.append(PokUtils.getAttributeValue(theItem, ma.getAttributeCode(), ", ", "", false));
			if (ii + 1 < metaList.size()) {
				navName.append(" ");
			}
		}

		return navName.toString();
	}

	/***********************************************
	 * Get the version
	 *
	 * @return java.lang.String
	 */
	public String getABRVersion() {
		return "1.0";
	}

	/***********************************************
	 * Get ABR description
	 *
	 * @return java.lang.String
	 */
	public String getDescription() {
		return "QSMFULLABRSTATUS";
	}
	/* private  void getLinkedEntities(EntityItem entityItem, String linkType, String destType,
		        Vector destVct)
		    {
		 addDebug("AVAIL ID:"+entityItem.getEntityID()+":linktype:"+linkType+"destype"+destType);
		        if (entityItem==null) {
		            return; }

		        addDebug("UpLinkCout:"+entityItem.getUpLinkCount());
		        addDebug("DownLinkCout:"+entityItem.getDownLinkCount());
		        // see if this relator is used as an uplink
		        for (int ui=0; ui<entityItem.getUpLinkCount(); ui++)
		        {
		            EANEntity entityLink = entityItem.getUpLink(ui);
		            addDebug(entityLink.getEntityType()+":"+entityLink.getEntityID());
		            addDebug(entityLink.getEntityType()+":getUpLinkCount"+entityLink.getUpLinkCount());
		            if (entityLink.getEntityType().equals(linkType))
		            {
		                // check for destination entity as an uplink
		                for (int i=0; i<entityLink.getUpLinkCount(); i++)
		                {
		                    EANEntity entity = entityLink.getUpLink(i);
		                    addDebug("entitytype:"+entity.getEntityType()+entity.getEntityID());
		                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
		                        destVct.addElement(entity); }
		                }
		                // check for destination entity as a downlink
		                for (int i=0; i<entityLink.getDownLinkCount(); i++)
		                {
		                    EANEntity entity = entityLink.getDownLink(i);
		                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
		                        destVct.addElement(entity);
		                }
		            }
		        }

		        // see if this relator is used as a downlink
		        for (int ui=0; ui<entityItem.getDownLinkCount(); ui++)
		        {
		        	 
		            EANEntity entityLink = entityItem.getDownLink(ui);
		            addDebug(entityLink.getEntityType()+":"+entityLink.getEntityID());
		            addDebug(entityLink.getEntityType()+":getDownLinkCount"+entityLink.getUpLinkCount());
		            if (entityLink.getEntityType().equals(linkType))
		            {
		                // check for destination entity as an uplink
		                for (int i=0; i<entityLink.getUpLinkCount(); i++)
		                {
		                    EANEntity entity = entityLink.getUpLink(i);
		                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity))
		                        destVct.addElement(entity);
		                }
		                // check for destination entity as a downlink
		                for (int i=0; i<entityLink.getDownLinkCount(); i++)
		                {
		                    EANEntity entity = entityLink.getDownLink(i);
		                    addDebug("entitytype:"+entity.getEntityType()+entity.getEntityID());
		                    if (entity.getEntityType().equals(destType) && !destVct.contains(entity)) {
		                        destVct.addElement(entity); }
		                }
		            }
		        }
		    }*/
}