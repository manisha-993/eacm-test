//Licensed Materials -- Property of IBM
//
// (C) Copyright IBM Corp. 2008  All Rights Reserved.
// The source code for this program is not published or otherwise divested of
// its trade secrets, irrespective of what has been deposited with the U.S. Copyright office.
//

package COM.ibm.eannounce.abr.sg;

import java.io.*;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.*;

import COM.ibm.eannounce.abr.util.*;
import COM.ibm.eannounce.objects.*;
import COM.ibm.opicmpdh.middleware.*;

import com.ibm.transform.oim.eacm.util.PokUtils;
import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;

/**********************************************************************************
* From "QSM Extract.doc" 
**********************************************************************************/
//$Log: QSMRPQFULLABR.java,v $
//Revision 1.2  2018/09/19 13:08:38  whwyue
//Update the report title
//
//Revision 1.1  2018/09/14 08:28:47  whwyue
//QSM RPQ ABR for non withdrawal data
//
//Revision 1.18  2018/01/22 14:39:39  mmiotto
//Updates to FAGRMBE attribute
//
//Revision 1.17  2018/01/22 13:05:30  mmiotto
//Updates to FAGRMBE attribute.
//
//Revision 1.16  2017/09/06 13:47:32  mmiotto
//Remove special chars
//Changes requested by the team
//
//Revision 1.15  2017/06/21 19:01:04  mmiotto
//Fix to removeSpecialChars method
//
//Revision 1.14  2017/06/20 18:10:47  mmiotto
//Remove special chars from invname, create a method for it
//
//Revision 1.13  2016/11/30 16:22:56  mmiotto
//Updates to FAGRMBE as requested by the user
//
//Revision 1.12  2016/11/30 12:47:35  mmiotto
//Fix file handling for FTP
//
//Revision 1.11  2016/11/28 21:12:12  mmiotto
//Fix for some attributes
//Added file handling - create in one dir, copy to ftp dir when complete
//
//Revision 1.10  2016/11/11 18:50:01  mmiotto
//Fix output file name
//
//Revision 1.9  2016/11/11 16:03:09  mmiotto
//Update output file name.
//Remove checks on GENAREA and replace by checks on QSMGEO
//
//Revision 1.8  2016/11/04 15:15:17  mmiotto
//Changes requested by the user
//
//Revision 1.7  2016/11/03 20:32:47  mmiotto
//Changes requested by the user
//
//Revision 1.6  2016/11/03 11:21:37  mmiotto
//Updates requested by the user to FSLMMES, QSLMCSU, DSLMGAD and SYSDES
//
//Revision 1.5  2016/10/20 19:15:43  mmiotto
//Changes requested by the user
//
//Revision 1.4  2016/10/11 17:01:23  mmiotto
//Remove debug statements - code cleanup
//
//Revision 1.3  2016/10/11 13:42:01  mmiotto
//User requested changes
//
//Revision 1.2  2016/10/08 22:46:40  mmiotto
//Code cleanup
//
//Revision 1.1  2016/10/06 19:33:08  mmiotto
//First commit, working program
//
//
// Revision 1.1  2016-05-27 16:38:00  mmiotto
// OIM Simplification project is to sunset ePIMS HW   and MMLC (SAP) systems. In order to sunset ePIMS HW we need to replace ePIMS HW -> QSM feed to EACM.
//
//
/**
 * @author mmiotto
 *
 */
public class QSMRPQFULLABR extends PokBaseABR {
	private StringBuffer rptSb = new StringBuffer();
	private static final char[] FOOL_JTEST = { '\n' };
	static final String NEWLINE = new String(FOOL_JTEST);

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
	private int abr_debuglvl = D.EBUG_ERR;

	private static final String CREFINIPATH = "_inipath";
	private static final String FTPSCRPATH = "_script";
	private static final String TARGETFILENAME = "_targetfilename";
	private static final String LOGPATH = "_logpath";
	private static final String BACKUPPATH = "_backuppath";
	private String fileprefix = null;
	private String lineStr = "";
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
	private String abrcode = "";
	private EntityItem rootEntity;

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
	public void processThis(QSMRPQFULLABRSTATUS abr, Profile m_prof, Database m_db, String abri, StringBuffer rpt,
			EntityItem rootEntity) throws Exception {

		String header1 = "";
		boolean creatFile = true;
		boolean sentFile = true;

		MessageFormat msgf;
		String abrversion = "";

		Object[] args = new String[10];

		try {
			this.m_prof = m_prof;
			this.m_db = m_db;
			abrcode = abri;
			this.rootEntity = rootEntity;
			rptSb = rpt;
			m_elist = getEntityList(getFeatureVEName());
			setDGTitle(navName);
			setDGString(getABRReturnCode());
			setDGRptName("QSMRPQFULLABRSTATUS"); // Set the report name
			setDGRptClass("QSMRPQFULLABRSTATUS"); // Set the report class
			if (m_elist.getEntityGroupCount() > 0) {
				// NAME is navigate attributes - only used if error rpt is
				// generated
				navName = getNavigationName();
				generateFlatFile(rootEntity);
				exeFtpShell(ffPathName);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/********************************************
	 * CQ00016165 The Files will be named as follows: 1. 'EACMFEEDQSMABR_' 2.
	 * The DB2 DTS for T2 with spaces and special characters replaced with an
	 * underscore 3. '.txt'
	 */
	 private void setFileName(EntityItem rootEntity){
			// FILE_PREFIX=EACMFEEDQSMFULLABR_
			fileprefix = ABRServerProperties.getFilePrefix(abrcode);
			// ABRServerProperties.getOutputPath()
			// ABRServerProperties.get
			StringBuffer sb = new StringBuffer(fileprefix.trim());
			 DatePackage dbNow;
			try {
				dbNow = m_db.getDates();
				String dts = dbNow.getNow();
				// replace special characters
				dts = dts.replace(' ', '_');
				sb.append(rootEntity.getEntityType() + rootEntity.getEntityID() + "_");
				sb.append(dts + ".txt");
			    dir = ABRServerProperties.getValue(abrcode, QSMGENPATH, "/Dgq");
			    if (!dir.endsWith("/")){
			    	dir=dir+"/";
			    }
				ffFileName = sb.toString();
				ffPathName = dir + ffFileName;
			} catch (MiddlewareException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
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

		createT006Feature(rootEntity, wOut);
		createT632TypeModelFeatureRelation(rootEntity, wOut);

		wOut.close();

		dirDest = ABRServerProperties.getValue(abrcode, QSMFTPPATH, "/Dgq");
		if (!dirDest.endsWith("/")) {
			dirDest = dirDest + "/";
		}

		ffFTPPathName = dirDest + ffFileName;

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
	 * Create records for T006 FEATURE
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006Feature(EntityItem rootEntity, OutputStreamWriter wOut)
			throws IOException, SQLException, MiddlewareException {

		EANFlagAttribute featGeoList;

		featGeoList = null;

		EntityGroup prodstructGrp = m_elist.getEntityGroup("PRODSTRUCT");

		for (int pI = 0; pI < prodstructGrp.getEntityItemCount(); pI++) {

			EntityItem eiProdstruct = prodstructGrp.getEntityItem(pI);

			featGeoList = (EANFlagAttribute) rootEntity.getAttribute("GENAREASELECTION");
			if (featGeoList != null) {
				if (featGeoList.isSelected("1999")) {
					for (int i = 0; i < geoWWList.size(); i++) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, (String) geoWWList.get(i));
					}
				} else {
					if (featGeoList.isSelected("6199")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Asia Pacific");
					}
					if (featGeoList.isSelected("6200")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Canada and Caribbean North");
					}
					if (featGeoList.isSelected("6198")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Europe/Middle East/Africa");
					}
					if (featGeoList.isSelected("6204")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "Latin America");
					}
					if (featGeoList.isSelected("6221")) {
						createT006FeatureRecords(rootEntity, wOut, eiProdstruct, "US Only");
					}
				}
			}
		}
	}

	/*
	 * Create records for T006 FEATURE
	 * 
	 * @throws IOException
	 * 
	 * @throws SQLException
	 * 
	 * @throws MiddlewareException
	 */
	private void createT006FeatureRecords(EntityItem rootEntity, OutputStreamWriter wOut, EntityItem eiProdstruct,
			String strRootGenAreaSel) throws IOException, SQLException, MiddlewareException {

		StringBuffer sb;
		String strIOPUCTY;
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
		String strMaintPrice;

		Vector modelVect = PokUtils.getAllLinkedEntities(rootEntity, "PRODSTRUCT", "MODEL");

		for (int i = 0; i < modelVect.size(); i++) {
			sb = new StringBuffer();
			EntityItem eiModel = (EntityItem) modelVect.elementAt(i);

			Vector geoModVect = PokUtils.getAllLinkedEntities(eiModel, "MODELGEOMOD", "GEOMOD");
			// EntityItem eiModGeoMod = (EntityItem) modGeoModVect.elementAt(i);

			EntityGroup sgmntAcrnymGrp = m_elist.getEntityGroup("SGMNTACRNYM");

			sb = new StringBuffer();
			strIOPUCTY = "";
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
			strMaintPrice = "";
			strDSLMWDN = "";

			sb.append(getValue(IFTYPE, "F"));

			if (strRootGenAreaSel.equals("Latin America")) {
				strIOPUCTY = "601";
				strISLMPAL = "LA" + PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("Europe/Middle East/Africa")) {
				strIOPUCTY = "999";
				strISLMPAL = "ZG" + PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("Asia Pacific")) {
				strIOPUCTY = "872";
				strISLMPAL = "AP" + PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("US Only")) {
				strIOPUCTY = "897";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			} else if (strRootGenAreaSel.equals("Canada and Caribbean North")) {
				strIOPUCTY = "649";
				strISLMPAL = PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "");
			}

			sb.append(getValue(IOPUCTY, strIOPUCTY));
			sb.append(getValue(ISLMPAL, strISLMPAL));
			sb.append(getValue(ISLMRFA, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
			String strISLMPRN = PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", ",", "", false);
			strISLMPRN += PokUtils.getAttributeValue(rootEntity, "FEATURECODE", ",", "", false);
			sb.append(getValue(ISLMPRN, strISLMPRN));

			strFCTYPE = PokUtils.getAttributeValue(rootEntity, "FCTYPE", ",", "", false);
			strCSLMPCI = "MF";
			if (strFCTYPE.equals("RPQ-RLISTED") || strFCTYPE.equals("RPQ-ILISTED") || strFCTYPE.equals("RPQ-PLISTED")) {
				strCSLMPCI = "MQ";
			}

			sb.append(getValue(CSLMPCI, strCSLMPCI));
			sb.append(getValue(IPRTNUM, ""));
			sb.append(getValue(FPUNINC, "2"));
			sb.append(getValue(CAOAV, ""));
			sb.append(getValue(DSLMCPA, PokUtils.getAttributeValue(eiProdstruct, "ANNDATE", ",", "", false)));
			sb.append(getValue(DSLMCPO, ""));

			sb.append(getValue(DSLMGAD, PokUtils.getAttributeValue(eiProdstruct, "GENAVAILDATE", ",", "", false)));

			if (strRootGenAreaSel.equals("Latin America") || strRootGenAreaSel.equals("Europe/Middle East/Africa")) {
				sb.append(getValue(DSLMMES, PokUtils.getAttributeValue(eiProdstruct, "GENAVAILDATE", ",", "", false)));
			} else {
				sb.append(getValue(DSLMMES, "2050-12-31"));
			}

			sb.append(getValue(QSMEDMW, "2050-12-31"));
			sb.append(getValue(DSLMMVA, PokUtils.getAttributeValue(eiProdstruct, "ANNDATE", ",", "", false)));

			strDSLMWDN = PokUtils.getAttributeValue(rootEntity, "WITHDRAWDATEEFF_T", ",", "", false);
			if (strDSLMWDN.equals("")) {
				strDSLMWDN = "2050-12-31";
			}
			sb.append(getValue(DSLMWDN, strDSLMWDN));

			strPricedFeature = PokUtils.getAttributeValue(rootEntity, "PRICEDFEATURE", ",", "", false);

			if (strFCTYPE.equals("Primary") && strPricedFeature.equals("No")) {
				strFeatureType = "S";
			}

			if (strRootGenAreaSel.equals("US Only") || strRootGenAreaSel.equals("Canada and Caribbean North")) {
				strASLMMVP = "1.00";
			} else if (strRootGenAreaSel.equals("Europe/Middle East/Africa")
					|| strRootGenAreaSel.equals("Latin America")) {
				if (strFeatureType.equals("S")) {
					strASLMMVP = "0.00";
				} else {
					strASLMMVP = "1.00";
				}
			} else if (strRootGenAreaSel.equals("Asia Pacific")) {
				if (strFeatureType.equals("S")) {
					strASLMMVP = "0.00";
				} else if (strPricedFeature.equals("No")) {
					strASLMMVP = "1.00";
				} else {
					strASLMMVP = "0.00";
				}
			}

			sb.append(getValue(ASLMMVP, strASLMMVP));

			sb.append(getValue(CICRY, "N"));
			sb.append(getValue(CIDCJ, "N"));
			sb.append(getValue(CIDXC, "N"));

			if (strRootGenAreaSel.equals("US Only")) {
				sb.append(getValue(CINCA, "N"));
			} else {
				sb.append(getValue(CINCA, "Y"));
			}

			String strCINCB = "";
			strPrcIndc = PokUtils.getAttributeValue(rootEntity, "PRICEDFEATURE", "", "");
			if (strRootGenAreaSel.equals("US Only")) {
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

			Vector psStdMaintVect = PokUtils.getAllLinkedEntities(eiProdstruct, "PRODSTSTDMT", "STDMAINT");
			Vector modelStdMaintVect = PokUtils.getAllLinkedEntities(eiModel, "MODELSTDMAINT", "STDMAINT");
			EntityItem eiStdMaint = null;
			if (!psStdMaintVect.isEmpty()) {
				eiStdMaint = (EntityItem) psStdMaintVect.elementAt(0);
				if (eiStdMaint != null) {
					strMaintElig = PokUtils.getAttributeValue(eiStdMaint, "MAINTELIG", "", "");
					if (strMaintElig.equals("")) {
						if (!modelStdMaintVect.isEmpty()) {
							eiStdMaint = (EntityItem) modelStdMaintVect.elementAt(0);
						}
					}
				} else {
					if (!modelStdMaintVect.isEmpty()) {
						eiStdMaint = (EntityItem) modelStdMaintVect.elementAt(0);
					}
				}
			} else {
				if (!modelStdMaintVect.isEmpty()) {
					eiStdMaint = (EntityItem) modelStdMaintVect.elementAt(0);
				}
			}

			if (eiStdMaint != null) {
				strMaintElig = PokUtils.getAttributeValue(eiStdMaint, "MAINTELIG", "", "");
			}

			strMaintPrice = PokUtils.getAttributeValue(rootEntity, "MAINTPRICE", "", "");

			if (strRootGenAreaSel.equals("Asia Pacific")) {
				strCINCC = "Y";
			} else if (strRootGenAreaSel.equals("US Only") || strRootGenAreaSel.equals("Canada and Caribbean North")) {
				strCINCC = "N";
			} else if (strRootGenAreaSel.equals("Europe/Middle East/Africa")
					|| strRootGenAreaSel.equals("Latin America")) {
				if (strMaintPrice.equals("Yes")) {
					strCINCC = "N";
				} else if (strMaintPrice.equals("No")) {
					strCINCC = "Y";
				}
			}

			sb.append(getValue(CINCC, strCINCC));

			sb.append(getValue(CINPM, "N"));
			sb.append(getValue(CITEM, "N"));
			sb.append(getValue(CISUP, "N"));
			if (sgmntAcrnymGrp != null && sgmntAcrnymGrp.hasData()) {
				EntityItem sgmntAcrnymEI = sgmntAcrnymGrp.getEntityItem(0);
				sb.append(getValue(CJLBSAC, PokUtils.getAttributeValue(sgmntAcrnymEI, "ACRNYM", "", "")));
			} else {
				sb.append(getValue(CJLBSAC, "   "));
			}
			sb.append(getValue(CLASSPT, "IHW"));

			strCSLMFTY = "";

			if (strRootGenAreaSel.equals("Europe/Middle East/Africa") || strRootGenAreaSel.equals("Latin America")) {
				if (strFeatureType.equals("S")) {
					strCSLMFTY = "CM";
				}
			} else if (strRootGenAreaSel.equals("Asia Pacific")) {
				if (strFeatureType.equals("S")) {
					strCSLMFTY = "CM";
				}
			} else if (strRootGenAreaSel.equals("US Only")) {
				strCSLMFTY = "NF";
			} else if (strRootGenAreaSel.equals("Canada and Caribbean North")) {
				strCSLMFTY = "";
			}

			sb.append(getValue(CSLMFTY, strCSLMFTY));
			sb.append(getValue(CVOAT, ""));

			if (strRootGenAreaSel.equals("Asia Pacific") || strRootGenAreaSel.equals("US Only")) {
				strFAGRMBE = "N";
			} else if (strRootGenAreaSel.equals("Canada and Caribbean North")) {
				strFAGRMBE = "Y";
			} else if (strRootGenAreaSel.equals("Europe/Middle East/Africa")
					|| strRootGenAreaSel.equals("Latin America")) {
				if (strMaintPrice.equals("Yes")) {
					strFAGRMBE = "Y";
				} else if (strMaintPrice.equals("No")) {
					strFAGRMBE = "N";
				}
			}

			sb.append(getValue(FAGRMBE, strFAGRMBE));

			String geoModGeo = "";
			EntityItem eiModGeoMod = null;
			if (geoModVect.size() > 0) {
				for (int igm = 0; igm < geoModVect.size(); igm++) {
					eiModGeoMod = (EntityItem) geoModVect.elementAt(igm);
					geoModGeo = PokUtils.getAttributeValue(eiModGeoMod, "GENAREASELECTION", "", "");
					if (geoModGeo.equals(strRootGenAreaSel)) {
						geoModPurchOnly = PokUtils.getAttributeValue(eiModGeoMod, "PURCHONLY", "", "");
						strFUSAEDE = PokUtils.getAttributeValue(eiModGeoMod, "EDUCPURCHELIG", "", "");
						igm = geoModVect.size();
					} else {
						eiModGeoMod = null;
					}
				}
			}

			String strOrderCode = PokUtils.getAttributeValue(eiProdstruct, "ORDERCODE", ",", "", false);
			if (strOrderCode.equals("Initial")) {
				if (strRootGenAreaSel.equals("Latin America") || strRootGenAreaSel.equals("Europe/Middle East/Africa")
						|| strRootGenAreaSel.equals("Canada and Caribbean North")
						|| strRootGenAreaSel.equals("Asia Pacific")) {
					strFSLMPIO = "Y";
				} else if (strRootGenAreaSel.equals("US Only")) {
					strFSLMPIO = "N";
				}
			} else {
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

			if (strRootGenAreaSel.equals("Latin America") || strRootGenAreaSel.equals("US Only")
					|| strRootGenAreaSel.equals("Canada and Caribbean North")
					|| strRootGenAreaSel.equals("Asia Pacific")) {
				sb.append(getValue(FSLMPOP, "No"));
			} else if (strRootGenAreaSel.equals("Europe/Middle East/Africa")) {
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

			if (!warrVect.isEmpty()) {
				eiWarr = (EntityItem) warrVect.elementAt(0);
				if (eiWarr == null) {
					warrVect = PokUtils.getAllLinkedEntities(eiModel, "MODELWARR", "WARR");
					if (!warrVect.isEmpty()) {
						eiWarr = (EntityItem) warrVect.elementAt(0);
					}
				}
			} else {
				warrVect = PokUtils.getAllLinkedEntities(eiModel, "MODELWARR", "WARR");
				if (!warrVect.isEmpty()) {
					eiWarr = (EntityItem) warrVect.elementAt(0);
				}
			}

			if (eiWarr != null) {
				strWarrID = PokUtils.getAttributeValue(eiWarr, "WARRID", "", "");
				if (strWarrID.equals("WTY0000")) {
					if (warrVect.size() > 1) {
						eiWarr = (EntityItem) warrVect.elementAt(1);
					} else {
						eiWarr = null;
					}
				}
			}

			if (eiWarr != null) {
				EANFlagAttribute warrTypeList = (EANFlagAttribute) eiWarr.getAttribute("WARRTYPE");
				if (warrTypeList != null) {
					if (strRootGenAreaSel.equals("Europe/Middle East/Africa")) {
						if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")
								|| warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
								|| warrTypeList.isSelected("W0250")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strRootGenAreaSel.equals("Latin America")) {
						if (warrTypeList.isSelected("W0310") || warrTypeList.isSelected("W0330")
								|| warrTypeList.isSelected("W0560") || warrTypeList.isSelected("W0570")
								|| warrTypeList.isSelected("W0580")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strRootGenAreaSel.equals("Asia Pacific")) {
						if (warrTypeList.isSelected("W0550") || warrTypeList.isSelected("W0390")
								|| warrTypeList.isSelected("W0200") || warrTypeList.isSelected("W0240")
								|| warrTypeList.isSelected("W0250") || warrTypeList.isSelected("W0310")
								|| warrTypeList.isSelected("W0330") || warrTypeList.isSelected("W0590")) {
							strFSLM2CF = "Y";
						} else {
							strFSLM2CF = "N";
						}
					}

					if (strRootGenAreaSel.equals("Canada and Caribbean North") || strRootGenAreaSel.equals("US Only")) {
						strFSLM2CF = "N";
					}
				}
			} else {
				strFSLM2CF = "N";
			}
			sb.append(getValue(FSLM2CF, strFSLM2CF));
			// sb.append(getValue(IDORIG, PokUtils.getAttributeValue(rootEntity,
			// "ANNTYPE", ",", "", false)));
			sb.append(getValue(IDORIG, "IBM"));
			strPCUAEAP = "000";
			strPCUAHEA = "000";
			strPCUASEA = "000";
			strPCUAUEA = "000";
			if (strRootGenAreaSel.equals("US Only") || strRootGenAreaSel.equals("Canada and Caribbean North")
					|| strRootGenAreaSel.equals("Asia Pacific")) {
				strPCUAEAP = "000";
				strPCUAHEA = "000";
				strPCUASEA = "000";
				strPCUAUEA = "000";
			} else if (strRootGenAreaSel.equals("Europe/Middle East/Africa")) {
				strPCUAEAP = " @@";
				strPCUAHEA = " @@";
				strPCUASEA = " @@";
				strPCUAUEA = " @@";
			} else {
				if (eiModGeoMod != null) {
					// strPCUAEAP = PokUtils.getAttributeValue(eiModGeoMod,
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

			String mdlInstall = PokUtils.getAttributeValue(eiModel, "INSTALL", "", "");
			if (mdlInstall.equals("CIF")) {
				if (strRootGenAreaSel.equals("Europe/Middle East/Africa")
						|| strRootGenAreaSel.equals("Latin America")) {
					strQSLMCSU = "01";
				} else if (strRootGenAreaSel.equals("Asia Pacific")) {
					strQSLMCSU = "10";
				} else if (strRootGenAreaSel.equals("US Only")
						|| strRootGenAreaSel.equals("Canada and Caribbean North")) {
					strQSLMCSU = "";
				}
			} else if (mdlInstall.equals("CE") || mdlInstall.equals("N/A") || mdlInstall.equals("Does not apply")) {
				strQSLMCSU = "";
			}

			sb.append(getValue(QSLMCSU, strQSLMCSU));
			sb.append(getValue(QSMXESA, "N"));
			sb.append(getValue(QSMXSSA, "N"));

			String strInvname = PokUtils.getAttributeValue(rootEntity, "INVNAME", ",", "", false);
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

			if (strRootGenAreaSel.equals("Asia Pacific")) {
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
						// TODO - ICE - IBM Courier Exchange - VALUE DOES NOT
						// EXIST IN EACM
						// if (warrTypeList.isSelected("W0310") ||
						// warrTypeList.isSelected("W0330")) {
						// warrTypeArray.add("ICE");
						// }
						// TODO - ICS - IBM Courier Service - VALUE DOES NOT
						// EXIST IN EACM
						// if (warrTypeList.isSelected("W0310") ||
						// warrTypeList.isSelected("W0330")) {
						// warrTypeArray.add("ICS");
						// }
						// TODO - IE8 - Annual Minimum Maintenance Next Day 9x5
						// - VALUE DOES NOT EXIST IN EACM
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
						// TODO - CFM - Central Facility Maintenance - VALUE
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

			if (eiWarr != null) {
				if (strRootGenAreaSel.equals("US Only")) {
					sb.append(getValue(CSLMWCD, PokUtils.getAttributeValue(eiWarr, "WARRCATG", "", "")));
				} else {
					sb.append(getValue(CSLMWCD, " "));
				}
			} else {
				sb.append(getValue(CSLMWCD, " "));
			}

			if (strRootGenAreaSel.equals("US Only")) {
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

			if (strRootGenAreaSel.equals("US Only")) {
				sb.append(getValue(FSLMCBL, "N"));
			} else {
				sb.append(getValue(FSLMCBL, ""));
			}

			if (strRootGenAreaSel.equals("US Only")) {
				sb.append(getValue(FUSAAAS, "Y"));
			} else {
				sb.append(getValue(FUSAAAS, ""));
			}

			if (strRootGenAreaSel.equals("US Only")) {
				sb.append(getValue(FUSAEDE, strFUSAEDE));
			} else {
				sb.append(getValue(FUSAEDE, ""));
			}

			if (strRootGenAreaSel.equals("US Only")) {
				sb.append(
						getValue(FUSALEP, PokUtils.getAttributeValue(eiModel, "MAINTANNBILLELIGINDC", ",", "", false)));
			} else {
				sb.append(getValue(FUSALEP, " "));
			}

			if (strRootGenAreaSel.equals("US Only")) {
				sb.append(getValue(FUSAIRR, "N"));
			} else {
				sb.append(getValue(FUSAIRR, ""));
			}
			// sb.append(getValue(FUSAIRR,
			// PokUtils.getAttributeValue(eiProdstruct, "RETURNEDPARTS", ",",
			// "", false)));

			if (eiModGeoMod != null) {
				sb.append(getValue(ICESPCC, PokUtils.getAttributeValue(eiModGeoMod, "PERCALLCLS", ",", "", false)));
			} else {
				sb.append(getValue(ICESPCC, ""));
			}
			sb.append(getValue(QUSAPOP, "00.0"));
			sb.append(getValue(FSLMRFM, ""));

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
		out = out.replaceAll("鈥�", "");

		return out;

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
		String strOrderCode;
		String strQSLMCSU;
		String strDSLMWDN;

		m_elist = getEntityList(getFeatureVEName());

		// Create T632 records only for US ONLY and WorldWide GEOs
		EANFlagAttribute featGenAreaList = (EANFlagAttribute) rootEntity.getAttribute("GENAREASELECTION");
		if (featGenAreaList != null) {
			if (featGenAreaList.isSelected("6221") || featGenAreaList.isSelected("1999")) {

				EntityGroup prodstructGrp = m_elist.getEntityGroup("PRODSTRUCT");

				for (int i = 0; i < prodstructGrp.getEntityItemCount(); i++) {
					sb = new StringBuffer();

					EntityItem eiProdstruct = prodstructGrp.getEntityItem(i);

					Vector modelVect = eiProdstruct.getDownLink();

					for (int im = 0; im < modelVect.size(); im++) {

						EntityItem eiModel = (EntityItem) modelVect.elementAt(im);

						sb = new StringBuffer();
						strOrderCode = "";
						strQSLMCSU = "";
						strDSLMWDN = "";

						sb.append(getValue(IFTYPE, "T"));
						sb.append(getValue(IOPUCTY, "897"));
						sb.append(getValue(ISLMPAL, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
						sb.append(getValue(ISLMRFA, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
						sb.append(getValue(ISLMTYP, PokUtils.getAttributeValue(eiModel, "MACHTYPEATR", "", "")));
						sb.append(getValue(ISLMMOD, PokUtils.getAttributeValue(eiModel, "MODELATR", "", "")));
						sb.append(getValue(ISLMFTR, PokUtils.getAttributeValue(rootEntity, "FEATURECODE", "", "")));
						sb.append(getValue(ISLMXX1, ""));
						sb.append(getValue(CSLMPCI, "TR"));
						sb.append(getValue(FPUNINC, "2"));
						sb.append(getValue(CAOAV, ""));
						sb.append(getValue(DSLMCPA, PokUtils.getAttributeValue(eiProdstruct, "ANNDATE", "", "")));
						sb.append(getValue(DSLMCPO, PokUtils.getAttributeValue(eiProdstruct, "ANNDATE", "", "")));

						strDSLMWDN = PokUtils.getAttributeValue(eiProdstruct, "WTHDRWEFFCTVDATE", "", "");
						if (strDSLMWDN.equals("")) {
							strDSLMWDN = "2050-12-31";
						}

						sb.append(getValue(DSLMWDN, strDSLMWDN));

						strOrderCode = PokUtils.getAttributeValue(eiProdstruct, "ORDERCODE", "", "");

						if (strOrderCode.equals("Both") || strOrderCode.equals("Initial")) {
							sb.append(getValue(FSLMMES, "N"));
						} else if (strOrderCode.equals("MES")) {
							sb.append(getValue(FSLMMES, "Y"));
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

		EntityList list = m_db.getEntityList(m_prof, eaItem, new EntityItem[] {
				new EntityItem(null, m_prof, rootEntity.getEntityType(), rootEntity.getEntityID()) });

		// debug display list of groups
		addDebug("EntityList for " + m_prof.getValOn() + " extract " + veName + " contains the following entities: \n"
				+ PokUtils.outputList(list));

		return list;
	}

	/**********************************
	 * get the name of the VE that returns all data
	 * 
	 * @return java.lang.String
	 */
	public String getFeatureVEName() {
		return "QSMRPQFULLVE1";
	}

	public boolean exeFtpShell(String fileName) {
		// String cmd =
		// "/usr/bin/rsync -av /var/log/www.solive.kv/access_log
		// testuser@10.0.1.219::store --password-file=/etc/client/rsync.pwd";

		String cmd = ABRServerProperties.getValue(abrcode, FTPSCRPATH, null) + " -f " + fileName;
		String ibiinipath = ABRServerProperties.getValue(abrcode, CREFINIPATH, null);
		if (ibiinipath != null)
			cmd += " -i " + ibiinipath;
		if (dir != null)
			cmd += " -d " + dir;
		if (fileprefix != null)
			cmd += " -p " + fileprefix;
		String targetFilePath = ABRServerProperties.getValue(abrcode, TARGETFILENAME, null);
		if (targetFilePath != null)
			cmd += " -t " + targetFilePath;
		String logPath = ABRServerProperties.getValue(abrcode, LOGPATH, null);
		if (logPath != null)
			cmd += " -l " + logPath;
		String backupPath = ABRServerProperties.getValue(abrcode, BACKUPPATH, null);
		if (backupPath != null)
			cmd += " -b " + backupPath;
		Runtime run = Runtime.getRuntime();
		String result = "";
		BufferedReader br = null;
		BufferedInputStream in = null;
		addDebug("cmd:" + cmd);
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
		rptSb.append("<!-- " + msg + " -->" + NEWLINE);
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
		return "QSMRPQFULLABR";
	}

}
