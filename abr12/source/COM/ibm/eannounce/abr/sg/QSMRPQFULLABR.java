/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.nio.channels.FileChannel;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class QSMRPQFULLABR
/*      */   extends PokBaseABR
/*      */ {
/*  101 */   private StringBuffer rptSb = new StringBuffer();
/*  102 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  103 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*  105 */   private ResourceBundle rsBundle = null;
/*  106 */   private Hashtable metaTbl = new Hashtable<>();
/*  107 */   private String navName = "";
/*      */   
/*  109 */   private String ffFileName = null;
/*  110 */   private String ffPathName = null;
/*  111 */   private String ffFTPPathName = null;
/*  112 */   private String dir = null;
/*  113 */   private String dirDest = null;
/*  114 */   private final String QSMRPTPATH = "_rptpath";
/*  115 */   private final String QSMGENPATH = "_genpath";
/*  116 */   private final String QSMFTPPATH = "_ftppath";
/*  117 */   private int abr_debuglvl = 0;
/*      */   
/*      */   private static final String CREFINIPATH = "_inipath";
/*      */   private static final String FTPSCRPATH = "_script";
/*      */   private static final String TARGETFILENAME = "_targetfilename";
/*      */   private static final String LOGPATH = "_logpath";
/*      */   private static final String BACKUPPATH = "_backuppath";
/*  124 */   private String fileprefix = null;
/*  125 */   private String lineStr = "";
/*      */   private static final String FAILD = "FAILD";
/*      */   private static final String IFTYPE = "IFTYPE";
/*      */   private static final String IOPUCTY = "IOPUCTY";
/*      */   private static final String ISLMPAL = "ISLMPAL";
/*      */   private static final String ISLMRFA = "ISLMRFA";
/*      */   private static final String ISLMPRN = "ISLMPRN";
/*      */   private static final String CSLMPCI = "CSLMPCI";
/*      */   private static final String IPRTNUM = "IPRTNUM";
/*      */   private static final String FPUNINC = "FPUNINC";
/*      */   private static final String CAOAV = "CAOAV";
/*      */   private static final String DSLMCPA = "DSLMCPA";
/*      */   private static final String DSLMCPO = "DSLMCPO";
/*      */   private static final String DSLMGAD = "DSLMGAD";
/*      */   private static final String DSLMMVA = "DSLMMVA";
/*      */   private static final String DSLMOPD = "DSLMOPD";
/*      */   private static final String DSLMWDN = "DSLMWDN";
/*      */   private static final String QSMEDMW = "QSMEDMW";
/*      */   private static final String ASLMMVP = "ASLMMVP";
/*      */   private static final String CCUOICR = "CCUOICR";
/*      */   private static final String CICIB = "CICIB";
/*      */   private static final String CICIC = "CICIC";
/*      */   private static final String CICRY = "CICRY";
/*      */   private static final String CIDCJ = "CIDCJ";
/*      */   private static final String CIDXF = "CIDXF";
/*      */   private static final String CINCA = "CINCA";
/*      */   private static final String CINCB = "CINCB";
/*      */   private static final String CINCC = "CINCC";
/*      */   private static final String CINPM = "CINPM";
/*      */   private static final String CISUP = "CISUP";
/*      */   private static final String CITEM = "CITEM";
/*      */   private static final String CJLBIC1 = "CJLBIC1";
/*      */   private static final String CJLBIDS = "CJLBIDS";
/*      */   private static final String CJLBOEM = "CJLBOEM";
/*      */   private static final String CJLBPOF = "CJLBPOF";
/*      */   private static final String CJLBSAC = "CJLBSAC";
/*      */   private static final String CLASSPT = "CLASSPT";
/*      */   private static final String CPDAA = "CPDAA";
/*      */   private static final String CSLMFCC = "CSLMFCC";
/*      */   private static final String CSLMGGC = "CSLMGGC";
/*      */   private static final String CSLMIDP = "CSLMIDP";
/*      */   private static final String CSLMLRP = "CSLMLRP";
/*      */   private static final String CSLMSAS = "CSLMSAS";
/*      */   private static final String CSLMSYT = "CSLMSYT";
/*      */   private static final String CSLMWCD = "CSLMWCD";
/*      */   private static final String FAGRMBE = "FAGRMBE";
/*      */   private static final String FCUOCNF = "FCUOCNF";
/*      */   private static final String FSLMCLS = "FSLMCLS";
/*      */   private static final String FSLMCPU = "FSLMCPU";
/*      */   private static final String FSLMIOP = "FSLMIOP";
/*      */   private static final String FSLMLGS = "FSLMLGS";
/*      */   private static final String FSLMMLC = "FSLMMLC";
/*      */   private static final String FSLMPOP = "FSLMPOP";
/*      */   private static final String FSLMVDE = "FSLMVDE";
/*      */   private static final String FSLMVTS = "FSLMVTS";
/*      */   private static final String FSLM2CF = "FSLM2CF";
/*      */   private static final String ICESPCC = "ICESPCC";
/*      */   private static final String IDORIG = "IDORIG";
/*      */   private static final String IOLCPLM = "IOLCPLM";
/*      */   private static final String PCUAHEA = "PCUAHEA";
/*      */   private static final String PCUASEA = "PCUASEA";
/*      */   private static final String PCUAUEA = "PCUAUEA";
/*      */   private static final String QSLMCSU = "QSLMCSU";
/*      */   private static final String QSMXANN = "QSMXANN";
/*      */   private static final String QSMXESA = "QSMXESA";
/*      */   private static final String QSMXSSA = "QSMXSSA";
/*      */   private static final String SYSDES = "SYSDES";
/*      */   private static final String TSLMDES = "TSLMDES";
/*      */   private static final String TSLTDES = "TSLTDES";
/*      */   private static final String TIMSTMP = "TIMSTMP";
/*      */   private static final String USERID = "USERID";
/*      */   private static final String FBRAND = "FBRAND";
/*      */   private static final String FSLMHVP = "FSLMHVP";
/*      */   private static final String FSLMCVP = "FSLMCVP";
/*      */   private static final String FSLMMES = "FSLMMES";
/*      */   private static final String CSLMTM1 = "CSLMTM1";
/*      */   private static final String CSLMTM2 = "CSLMTM2";
/*      */   private static final String CSLMTM3 = "CSLMTM3";
/*      */   private static final String CSLMTM4 = "CSLMTM4";
/*      */   private static final String CSLMTM5 = "CSLMTM5";
/*      */   private static final String CSLMTM6 = "CSLMTM6";
/*      */   private static final String CSLMTM7 = "CSLMTM7";
/*      */   private static final String CSLMTM8 = "CSLMTM8";
/*      */   private static final String FSAPRES = "FSAPRES";
/*      */   private static final String CUSAPMS = "CUSAPMS";
/*      */   private static final String DUSALRW = "DUSALRW";
/*      */   private static final String DUSAMDW = "DUSAMDW";
/*      */   private static final String DUSAWUW = "DUSAWUW";
/*      */   private static final String FSLMCBL = "FSLMCBL";
/*      */   private static final String FSLMMRR = "FSLMMRR";
/*      */   private static final String FUSAAAS = "FUSAAAS";
/*      */   private static final String FUSAADM = "FUSAADM";
/*      */   private static final String FUSAEDE = "FUSAEDE";
/*      */   private static final String FUSAICC = "FUSAICC";
/*      */   private static final String FUSALEP = "FUSALEP";
/*      */   private static final String FUSAMRS = "FUSAMRS";
/*      */   private static final String FUSAVLM = "FUSAVLM";
/*      */   private static final String FUSAXMO = "FUSAXMO";
/*      */   private static final String QUSAPOP = "QUSAPOP";
/*      */   private static final String DSLMEOD = "DSLMEOD";
/*      */   private static final String FSLMRFM = "FSLMRFM";
/*      */   private static final Hashtable COLUMN_LENGTH;
/*  227 */   private String abrcode = "";
/*      */   
/*      */   private EntityItem rootEntity;
/*      */   private static final String DSLMMES = "DSLMMES";
/*      */   private static final String CIDXC = "CIDXC";
/*      */   private static final String CSLMFTY = "CSLMFTY";
/*      */   private static final String CVOAT = "CVOAT";
/*      */   private static final String FSLMPIO = "FSLMPIO";
/*      */   private static final String FSLMSTK = "FSLMSTK";
/*      */   private static final String PCUAEAP = "PCUAEAP";
/*      */   private static final String POGMES = "POGMES";
/*      */   private static final String STSPCFT = "STSPCFT";
/*      */   private static final String FUSAIRR = "FUSAIRR";
/*      */   private static final String CPDXA = "CPDXA";
/*      */   private static final String DSLMEFF = "DSLMEFF";
/*      */   private static final String CSLMRCH = "CSLMRCH";
/*      */   private static final String CSLMNUM = "CSLMNUM";
/*      */   private static final String FSLMAPG = "FSLMAPG";
/*      */   private static final String FSLMASP = "FSLMASP";
/*      */   private static final String FSLMJAP = "FSLMJAP";
/*      */   private static final String FSLMAUS = "FSLMAUS";
/*      */   private static final String FSLMBGL = "FSLMBGL";
/*      */   private static final String FSLMBRU = "FSLMBRU";
/*      */   private static final String FSLMHKG = "FSLMHKG";
/*      */   private static final String FSLMIDN = "FSLMIDN";
/*      */   private static final String FSLMIND = "FSLMIND";
/*      */   private static final String FSLMKOR = "FSLMKOR";
/*      */   private static final String FSLMMAC = "FSLMMAC";
/*      */   private static final String FSLMMAL = "FSLMMAL";
/*      */   private static final String FSLMMYA = "FSLMMYA";
/*      */   private static final String FSLMNZL = "FSLMNZL";
/*      */   private static final String FSLMPHI = "FSLMPHI";
/*      */   private static final String FSLMPRC = "FSLMPRC";
/*      */   private static final String FSLMSLA = "FSLMSLA";
/*      */   private static final String FSLMSNG = "FSLMSNG";
/*      */   private static final String FSLMTAI = "FSLMTAI";
/*      */   private static final String FSLMTHA = "FSLMTHA";
/*      */   private static final String ISLMTYP = "ISLMTYP";
/*      */   private static final String ISLMMOD = "ISLMMOD";
/*      */   private static final String ISLMFTR = "ISLMFTR";
/*      */   private static final String ISLMXX1 = "ISLMXX1";
/*      */   private static final String QSMNPMT = "QSMNPMT";
/*      */   private static final String QSMNPMM = "QSMNPMM";
/*  270 */   private static final List geoWWList = Collections.unmodifiableList(new ArrayList()
/*      */       {
/*      */       
/*      */       });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  285 */     COLUMN_LENGTH = new Hashtable<>();
/*  286 */     COLUMN_LENGTH.put("IFTYPE", "1");
/*  287 */     COLUMN_LENGTH.put("IOPUCTY", "3");
/*  288 */     COLUMN_LENGTH.put("ISLMPAL", "8");
/*  289 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/*  290 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  291 */     COLUMN_LENGTH.put("CSLMPCI", "2");
/*  292 */     COLUMN_LENGTH.put("IPRTNUM", "12");
/*  293 */     COLUMN_LENGTH.put("FPUNINC", "1");
/*  294 */     COLUMN_LENGTH.put("CAOAV", "1");
/*  295 */     COLUMN_LENGTH.put("DSLMCPA", "10");
/*  296 */     COLUMN_LENGTH.put("DSLMCPO", "10");
/*  297 */     COLUMN_LENGTH.put("DSLMGAD", "10");
/*  298 */     COLUMN_LENGTH.put("DSLMMVA", "10");
/*  299 */     COLUMN_LENGTH.put("DSLMOPD", "10");
/*  300 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/*  301 */     COLUMN_LENGTH.put("QSMEDMW", "10");
/*  302 */     COLUMN_LENGTH.put("ASLMMVP", "4");
/*  303 */     COLUMN_LENGTH.put("CCUOICR", "1");
/*  304 */     COLUMN_LENGTH.put("CICIB", "1");
/*  305 */     COLUMN_LENGTH.put("CICIC", "1");
/*  306 */     COLUMN_LENGTH.put("CICRY", "1");
/*  307 */     COLUMN_LENGTH.put("CIDCJ", "1");
/*  308 */     COLUMN_LENGTH.put("CIDXF", "1");
/*  309 */     COLUMN_LENGTH.put("CINCA", "1");
/*  310 */     COLUMN_LENGTH.put("CINCB", "1");
/*  311 */     COLUMN_LENGTH.put("CINCC", "1");
/*  312 */     COLUMN_LENGTH.put("CINPM", "1");
/*  313 */     COLUMN_LENGTH.put("CISUP", "1");
/*  314 */     COLUMN_LENGTH.put("CITEM", "1");
/*  315 */     COLUMN_LENGTH.put("CJLBIC1", "2");
/*  316 */     COLUMN_LENGTH.put("CJLBIDS", "1");
/*  317 */     COLUMN_LENGTH.put("CJLBOEM", "1");
/*  318 */     COLUMN_LENGTH.put("CJLBPOF", "1");
/*  319 */     COLUMN_LENGTH.put("CJLBSAC", "3");
/*  320 */     COLUMN_LENGTH.put("CLASSPT", "3");
/*  321 */     COLUMN_LENGTH.put("CPDAA", "1");
/*  322 */     COLUMN_LENGTH.put("CSLMFCC", "4");
/*  323 */     COLUMN_LENGTH.put("CSLMGGC", "2");
/*  324 */     COLUMN_LENGTH.put("CSLMIDP", "1");
/*  325 */     COLUMN_LENGTH.put("CSLMLRP", "1");
/*  326 */     COLUMN_LENGTH.put("CSLMSAS", "1");
/*  327 */     COLUMN_LENGTH.put("CSLMSYT", "5");
/*  328 */     COLUMN_LENGTH.put("CSLMWCD", "1");
/*  329 */     COLUMN_LENGTH.put("FAGRMBE", "1");
/*  330 */     COLUMN_LENGTH.put("FCUOCNF", "1");
/*  331 */     COLUMN_LENGTH.put("FSLMCLS", "1");
/*  332 */     COLUMN_LENGTH.put("FSLMCPU", "1");
/*  333 */     COLUMN_LENGTH.put("FSLMIOP", "1");
/*  334 */     COLUMN_LENGTH.put("FSLMLGS", "1");
/*  335 */     COLUMN_LENGTH.put("FSLMMLC", "1");
/*  336 */     COLUMN_LENGTH.put("FSLMPOP", "1");
/*  337 */     COLUMN_LENGTH.put("FSLMVDE", "1");
/*  338 */     COLUMN_LENGTH.put("FSLMVTS", "1");
/*  339 */     COLUMN_LENGTH.put("FSLM2CF", "1");
/*  340 */     COLUMN_LENGTH.put("ICESPCC", "1");
/*  341 */     COLUMN_LENGTH.put("IDORIG", "3");
/*  342 */     COLUMN_LENGTH.put("IOLCPLM", "2");
/*  343 */     COLUMN_LENGTH.put("PCUAHEA", "3");
/*  344 */     COLUMN_LENGTH.put("PCUASEA", "3");
/*  345 */     COLUMN_LENGTH.put("PCUAUEA", "3");
/*  346 */     COLUMN_LENGTH.put("QSLMCSU", "2");
/*  347 */     COLUMN_LENGTH.put("QSMXANN", "1");
/*  348 */     COLUMN_LENGTH.put("QSMXESA", "1");
/*  349 */     COLUMN_LENGTH.put("QSMXSSA", "1");
/*  350 */     COLUMN_LENGTH.put("SYSDES", "30");
/*  351 */     COLUMN_LENGTH.put("TSLMDES", "30");
/*  352 */     COLUMN_LENGTH.put("TSLTDES", "56");
/*  353 */     COLUMN_LENGTH.put("TIMSTMP", "26");
/*  354 */     COLUMN_LENGTH.put("USERID", "8");
/*  355 */     COLUMN_LENGTH.put("FBRAND", "1");
/*  356 */     COLUMN_LENGTH.put("FSLMHVP", "1");
/*  357 */     COLUMN_LENGTH.put("FSLMCVP", "1");
/*  358 */     COLUMN_LENGTH.put("FSLMMES", "1");
/*  359 */     COLUMN_LENGTH.put("CSLMTM1", "3");
/*  360 */     COLUMN_LENGTH.put("CSLMTM2", "3");
/*  361 */     COLUMN_LENGTH.put("CSLMTM3", "3");
/*  362 */     COLUMN_LENGTH.put("CSLMTM4", "3");
/*  363 */     COLUMN_LENGTH.put("CSLMTM5", "3");
/*  364 */     COLUMN_LENGTH.put("CSLMTM6", "3");
/*  365 */     COLUMN_LENGTH.put("CSLMTM7", "3");
/*  366 */     COLUMN_LENGTH.put("CSLMTM8", "3");
/*  367 */     COLUMN_LENGTH.put("FSAPRES", "1");
/*  368 */     COLUMN_LENGTH.put("CUSAPMS", "1");
/*  369 */     COLUMN_LENGTH.put("DUSALRW", "10");
/*  370 */     COLUMN_LENGTH.put("DUSAMDW", "10");
/*  371 */     COLUMN_LENGTH.put("DUSAWUW", "10");
/*  372 */     COLUMN_LENGTH.put("FSLMCBL", "1");
/*  373 */     COLUMN_LENGTH.put("FSLMMRR", "1");
/*  374 */     COLUMN_LENGTH.put("FUSAAAS", "1");
/*  375 */     COLUMN_LENGTH.put("FUSAADM", "1");
/*  376 */     COLUMN_LENGTH.put("FUSAEDE", "1");
/*  377 */     COLUMN_LENGTH.put("FUSAICC", "1");
/*  378 */     COLUMN_LENGTH.put("FUSALEP", "1");
/*  379 */     COLUMN_LENGTH.put("FUSAMRS", "1");
/*  380 */     COLUMN_LENGTH.put("FUSAVLM", "1");
/*  381 */     COLUMN_LENGTH.put("FUSAXMO", "1");
/*  382 */     COLUMN_LENGTH.put("QUSAPOP", "4");
/*  383 */     COLUMN_LENGTH.put("DSLMEOD", "10");
/*  384 */     COLUMN_LENGTH.put("FSLMRFM", "1");
/*  385 */     COLUMN_LENGTH.put("DSLMMES", "10");
/*  386 */     COLUMN_LENGTH.put("CIDXC", "1");
/*  387 */     COLUMN_LENGTH.put("CSLMFTY", "2");
/*  388 */     COLUMN_LENGTH.put("CVOAT", "1");
/*  389 */     COLUMN_LENGTH.put("FSLMPIO", "1");
/*  390 */     COLUMN_LENGTH.put("FSLMSTK", "1");
/*  391 */     COLUMN_LENGTH.put("PCUAEAP", "3");
/*  392 */     COLUMN_LENGTH.put("POGMES", "10");
/*  393 */     COLUMN_LENGTH.put("STSPCFT", "4");
/*  394 */     COLUMN_LENGTH.put("FUSAIRR", "1");
/*  395 */     COLUMN_LENGTH.put("CPDXA", "2");
/*  396 */     COLUMN_LENGTH.put("DSLMEFF", "10");
/*  397 */     COLUMN_LENGTH.put("CSLMRCH", "1");
/*  398 */     COLUMN_LENGTH.put("CSLMNUM", "6");
/*  399 */     COLUMN_LENGTH.put("FSLMAPG", "1");
/*  400 */     COLUMN_LENGTH.put("FSLMASP", "1");
/*  401 */     COLUMN_LENGTH.put("FSLMJAP", "1");
/*  402 */     COLUMN_LENGTH.put("FSLMAUS", "1");
/*  403 */     COLUMN_LENGTH.put("FSLMBGL", "1");
/*  404 */     COLUMN_LENGTH.put("FSLMBRU", "1");
/*  405 */     COLUMN_LENGTH.put("FSLMHKG", "1");
/*  406 */     COLUMN_LENGTH.put("FSLMIDN", "1");
/*  407 */     COLUMN_LENGTH.put("FSLMIND", "1");
/*  408 */     COLUMN_LENGTH.put("FSLMKOR", "1");
/*  409 */     COLUMN_LENGTH.put("FSLMMAC", "1");
/*  410 */     COLUMN_LENGTH.put("FSLMMAL", "1");
/*  411 */     COLUMN_LENGTH.put("FSLMMYA", "1");
/*  412 */     COLUMN_LENGTH.put("FSLMNZL", "1");
/*  413 */     COLUMN_LENGTH.put("FSLMPHI", "1");
/*  414 */     COLUMN_LENGTH.put("FSLMPRC", "1");
/*  415 */     COLUMN_LENGTH.put("FSLMSLA", "1");
/*  416 */     COLUMN_LENGTH.put("FSLMSNG", "1");
/*  417 */     COLUMN_LENGTH.put("FSLMTAI", "1");
/*  418 */     COLUMN_LENGTH.put("FSLMTHA", "1");
/*  419 */     COLUMN_LENGTH.put("ISLMTYP", "4");
/*  420 */     COLUMN_LENGTH.put("ISLMMOD", "3");
/*  421 */     COLUMN_LENGTH.put("ISLMFTR", "6");
/*  422 */     COLUMN_LENGTH.put("ISLMXX1", "1");
/*  423 */     COLUMN_LENGTH.put("QSMNPMT", "4");
/*  424 */     COLUMN_LENGTH.put("QSMNPMM", "3");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  431 */     return this.rsBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(QSMRPQFULLABRSTATUS paramQSMRPQFULLABRSTATUS, Profile paramProfile, Database paramDatabase, String paramString, StringBuffer paramStringBuffer, EntityItem paramEntityItem) throws Exception {
/*  441 */     String str1 = "";
/*  442 */     boolean bool1 = true;
/*  443 */     boolean bool2 = true;
/*      */ 
/*      */     
/*  446 */     String str2 = "";
/*      */     
/*  448 */     String[] arrayOfString = new String[10];
/*      */     
/*      */     try {
/*  451 */       this.m_prof = paramProfile;
/*  452 */       this.m_db = paramDatabase;
/*  453 */       this.abrcode = paramString;
/*  454 */       this.rootEntity = paramEntityItem;
/*  455 */       this.rptSb = paramStringBuffer;
/*  456 */       this.m_elist = getEntityList(getFeatureVEName());
/*  457 */       setDGTitle(this.navName);
/*  458 */       setDGString(getABRReturnCode());
/*  459 */       setDGRptName("QSMRPQFULLABRSTATUS");
/*  460 */       setDGRptClass("QSMRPQFULLABRSTATUS");
/*  461 */       if (this.m_elist.getEntityGroupCount() > 0)
/*      */       {
/*      */         
/*  464 */         this.navName = getNavigationName();
/*  465 */         generateFlatFile(paramEntityItem);
/*  466 */         exeFtpShell(this.ffPathName);
/*      */       }
/*      */     
/*  469 */     } catch (IOException iOException) {
/*      */       
/*  471 */       iOException.printStackTrace();
/*  472 */     } catch (ParseException parseException) {
/*      */       
/*  474 */       parseException.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setFileName(EntityItem paramEntityItem) {
/*  485 */     this.fileprefix = ABRServerProperties.getFilePrefix(this.abrcode);
/*      */ 
/*      */     
/*  488 */     StringBuffer stringBuffer = new StringBuffer(this.fileprefix.trim());
/*      */     
/*      */     try {
/*  491 */       DatePackage datePackage = this.m_db.getDates();
/*  492 */       String str = datePackage.getNow();
/*      */       
/*  494 */       str = str.replace(' ', '_');
/*  495 */       stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/*  496 */       stringBuffer.append(str + ".txt");
/*  497 */       this.dir = ABRServerProperties.getValue(this.abrcode, "_genpath", "/Dgq");
/*  498 */       if (!this.dir.endsWith("/")) {
/*  499 */         this.dir += "/";
/*      */       }
/*  501 */       this.ffFileName = stringBuffer.toString();
/*  502 */       this.ffPathName = this.dir + this.ffFileName;
/*  503 */     } catch (MiddlewareException middlewareException) {
/*      */       
/*  505 */       middlewareException.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/*  523 */     FileChannel fileChannel1 = null;
/*  524 */     FileChannel fileChannel2 = null;
/*      */ 
/*      */     
/*  527 */     setFileName(paramEntityItem);
/*      */     
/*  529 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*      */ 
/*      */ 
/*      */     
/*  533 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */ 
/*      */ 
/*      */     
/*  537 */     createT006Feature(paramEntityItem, outputStreamWriter);
/*  538 */     createT632TypeModelFeatureRelation(paramEntityItem, outputStreamWriter);
/*      */     
/*  540 */     outputStreamWriter.close();
/*      */     
/*  542 */     this.dirDest = ABRServerProperties.getValue(this.abrcode, "_ftppath", "/Dgq");
/*  543 */     if (!this.dirDest.endsWith("/")) {
/*  544 */       this.dirDest += "/";
/*      */     }
/*      */     
/*  547 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/*      */     
/*      */     try {
/*  550 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/*  551 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/*  552 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*      */     } finally {
/*  554 */       fileChannel1.close();
/*  555 */       fileChannel2.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT006Feature(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/*  574 */     EANFlagAttribute eANFlagAttribute = null;
/*      */     
/*  576 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*      */     
/*  578 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/*  580 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  582 */       eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/*  583 */       if (eANFlagAttribute != null) {
/*  584 */         if (eANFlagAttribute.isSelected("1999")) {
/*  585 */           for (byte b1 = 0; b1 < geoWWList.size(); b1++) {
/*  586 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, geoWWList.get(b1));
/*      */           }
/*      */         } else {
/*  589 */           if (eANFlagAttribute.isSelected("6199")) {
/*  590 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific");
/*      */           }
/*  592 */           if (eANFlagAttribute.isSelected("6200")) {
/*  593 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North");
/*      */           }
/*  595 */           if (eANFlagAttribute.isSelected("6198")) {
/*  596 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa");
/*      */           }
/*  598 */           if (eANFlagAttribute.isSelected("6204")) {
/*  599 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America");
/*      */           }
/*  601 */           if (eANFlagAttribute.isSelected("6221")) {
/*  602 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only");
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT006FeatureRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString) throws IOException, SQLException, MiddlewareException {
/*  654 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem1, "PRODSTRUCT", "MODEL");
/*      */     
/*  656 */     for (byte b = 0; b < vector.size(); b++) {
/*  657 */       StringBuffer stringBuffer = new StringBuffer();
/*  658 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  660 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "MODELGEOMOD", "GEOMOD");
/*      */ 
/*      */       
/*  663 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SGMNTACRNYM");
/*      */       
/*  665 */       stringBuffer = new StringBuffer();
/*  666 */       String str1 = "";
/*  667 */       String str2 = "";
/*  668 */       String str3 = "";
/*  669 */       String str4 = "";
/*  670 */       String str5 = "";
/*  671 */       String str6 = "";
/*  672 */       String str7 = "";
/*  673 */       String str8 = "";
/*  674 */       String str9 = "";
/*  675 */       String str10 = "";
/*  676 */       String str11 = "";
/*  677 */       String str12 = "";
/*  678 */       String str13 = "";
/*  679 */       String str14 = "";
/*  680 */       String str15 = "";
/*  681 */       String str16 = "";
/*  682 */       String str17 = "";
/*  683 */       String str18 = "";
/*  684 */       String str19 = "";
/*  685 */       String str20 = "";
/*  686 */       String str21 = "";
/*  687 */       String str22 = "";
/*  688 */       String str23 = "";
/*  689 */       String str24 = "";
/*  690 */       String str25 = "";
/*  691 */       String str26 = "";
/*  692 */       String str27 = "";
/*  693 */       String str28 = "";
/*  694 */       String str29 = "";
/*  695 */       String str30 = "";
/*  696 */       String str31 = "";
/*  697 */       str4 = "";
/*      */       
/*  699 */       stringBuffer.append(getValue("IFTYPE", "F"));
/*      */       
/*  701 */       if (paramString.equals("Latin America")) {
/*  702 */         str1 = "601";
/*  703 */         str6 = "LA" + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  704 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/*  705 */         str1 = "999";
/*  706 */         str6 = "ZG" + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  707 */       } else if (paramString.equals("Asia Pacific")) {
/*  708 */         str1 = "872";
/*  709 */         str6 = "AP" + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  710 */       } else if (paramString.equals("US Only")) {
/*  711 */         str1 = "897";
/*  712 */         str6 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  713 */       } else if (paramString.equals("Canada and Caribbean North")) {
/*  714 */         str1 = "649";
/*  715 */         str6 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*      */       } 
/*      */       
/*  718 */       stringBuffer.append(getValue("IOPUCTY", str1));
/*  719 */       stringBuffer.append(getValue("ISLMPAL", str6));
/*  720 */       stringBuffer.append(getValue("ISLMRFA", PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "")));
/*  721 */       String str32 = PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", ",", "", false);
/*  722 */       str32 = str32 + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", ",", "", false);
/*  723 */       stringBuffer.append(getValue("ISLMPRN", str32));
/*      */       
/*  725 */       str3 = PokUtils.getAttributeValue(paramEntityItem1, "FCTYPE", ",", "", false);
/*  726 */       str2 = "MF";
/*  727 */       if (str3.equals("RPQ-RLISTED") || str3.equals("RPQ-ILISTED") || str3.equals("RPQ-PLISTED")) {
/*  728 */         str2 = "MQ";
/*      */       }
/*      */       
/*  731 */       stringBuffer.append(getValue("CSLMPCI", str2));
/*  732 */       stringBuffer.append(getValue("IPRTNUM", ""));
/*  733 */       stringBuffer.append(getValue("FPUNINC", "2"));
/*  734 */       stringBuffer.append(getValue("CAOAV", ""));
/*  735 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", ",", "", false)));
/*  736 */       stringBuffer.append(getValue("DSLMCPO", ""));
/*      */       
/*  738 */       stringBuffer.append(getValue("DSLMGAD", PokUtils.getAttributeValue(paramEntityItem2, "GENAVAILDATE", ",", "", false)));
/*      */       
/*  740 */       if (paramString.equals("Latin America") || paramString.equals("Europe/Middle East/Africa")) {
/*  741 */         stringBuffer.append(getValue("DSLMMES", PokUtils.getAttributeValue(paramEntityItem2, "GENAVAILDATE", ",", "", false)));
/*      */       } else {
/*  743 */         stringBuffer.append(getValue("DSLMMES", "2050-12-31"));
/*      */       } 
/*      */       
/*  746 */       stringBuffer.append(getValue("QSMEDMW", "2050-12-31"));
/*  747 */       stringBuffer.append(getValue("DSLMMVA", PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", ",", "", false)));
/*      */       
/*  749 */       str4 = PokUtils.getAttributeValue(paramEntityItem1, "WITHDRAWDATEEFF_T", ",", "", false);
/*  750 */       if (str4.equals("")) {
/*  751 */         str4 = "2050-12-31";
/*      */       }
/*  753 */       stringBuffer.append(getValue("DSLMWDN", str4));
/*      */       
/*  755 */       str22 = PokUtils.getAttributeValue(paramEntityItem1, "PRICEDFEATURE", ",", "", false);
/*      */       
/*  757 */       if (str3.equals("Primary") && str22.equals("No")) {
/*  758 */         str21 = "S";
/*      */       }
/*      */       
/*  761 */       if (paramString.equals("US Only") || paramString.equals("Canada and Caribbean North")) {
/*  762 */         str20 = "1.00";
/*  763 */       } else if (paramString.equals("Europe/Middle East/Africa") || paramString
/*  764 */         .equals("Latin America")) {
/*  765 */         if (str21.equals("S")) {
/*  766 */           str20 = "0.00";
/*      */         } else {
/*  768 */           str20 = "1.00";
/*      */         } 
/*  770 */       } else if (paramString.equals("Asia Pacific")) {
/*  771 */         if (str21.equals("S")) {
/*  772 */           str20 = "0.00";
/*  773 */         } else if (str22.equals("No")) {
/*  774 */           str20 = "1.00";
/*      */         } else {
/*  776 */           str20 = "0.00";
/*      */         } 
/*      */       } 
/*      */       
/*  780 */       stringBuffer.append(getValue("ASLMMVP", str20));
/*      */       
/*  782 */       stringBuffer.append(getValue("CICRY", "N"));
/*  783 */       stringBuffer.append(getValue("CIDCJ", "N"));
/*  784 */       stringBuffer.append(getValue("CIDXC", "N"));
/*      */       
/*  786 */       if (paramString.equals("US Only")) {
/*  787 */         stringBuffer.append(getValue("CINCA", "N"));
/*      */       } else {
/*  789 */         stringBuffer.append(getValue("CINCA", "Y"));
/*      */       } 
/*      */       
/*  792 */       String str33 = "";
/*  793 */       str27 = PokUtils.getAttributeValue(paramEntityItem1, "PRICEDFEATURE", "", "");
/*  794 */       if (paramString.equals("US Only")) {
/*  795 */         str33 = "N";
/*      */       }
/*  797 */       else if (str27.equals("Yes")) {
/*  798 */         str33 = "N";
/*  799 */       } else if (str27.equals("No")) {
/*  800 */         str33 = "Y";
/*      */       } else {
/*  802 */         str33 = "N";
/*      */       } 
/*      */ 
/*      */       
/*  806 */       stringBuffer.append(getValue("CINCB", str33));
/*      */       
/*  808 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem2, "PRODSTSTDMT", "STDMAINT");
/*  809 */       Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "MODELSTDMAINT", "STDMAINT");
/*  810 */       EntityItem entityItem2 = null;
/*  811 */       if (!vector2.isEmpty()) {
/*  812 */         entityItem2 = vector2.elementAt(0);
/*  813 */         if (entityItem2 != null) {
/*  814 */           str29 = PokUtils.getAttributeValue(entityItem2, "MAINTELIG", "", "");
/*  815 */           if (str29.equals("") && 
/*  816 */             !vector3.isEmpty()) {
/*  817 */             entityItem2 = vector3.elementAt(0);
/*      */           
/*      */           }
/*      */         }
/*  821 */         else if (!vector3.isEmpty()) {
/*  822 */           entityItem2 = vector3.elementAt(0);
/*      */         }
/*      */       
/*      */       }
/*  826 */       else if (!vector3.isEmpty()) {
/*  827 */         entityItem2 = vector3.elementAt(0);
/*      */       } 
/*      */ 
/*      */       
/*  831 */       if (entityItem2 != null) {
/*  832 */         str29 = PokUtils.getAttributeValue(entityItem2, "MAINTELIG", "", "");
/*      */       }
/*      */       
/*  835 */       str31 = PokUtils.getAttributeValue(paramEntityItem1, "MAINTPRICE", "", "");
/*      */       
/*  837 */       if (paramString.equals("Asia Pacific")) {
/*  838 */         str30 = "Y";
/*  839 */       } else if (paramString.equals("US Only") || paramString.equals("Canada and Caribbean North")) {
/*  840 */         str30 = "N";
/*  841 */       } else if (paramString.equals("Europe/Middle East/Africa") || paramString
/*  842 */         .equals("Latin America")) {
/*  843 */         if (str31.equals("Yes")) {
/*  844 */           str30 = "N";
/*  845 */         } else if (str31.equals("No")) {
/*  846 */           str30 = "Y";
/*      */         } 
/*      */       } 
/*      */       
/*  850 */       stringBuffer.append(getValue("CINCC", str30));
/*      */       
/*  852 */       stringBuffer.append(getValue("CINPM", "N"));
/*  853 */       stringBuffer.append(getValue("CITEM", "N"));
/*  854 */       stringBuffer.append(getValue("CISUP", "N"));
/*  855 */       if (entityGroup != null && entityGroup.hasData()) {
/*  856 */         EntityItem entityItem = entityGroup.getEntityItem(0);
/*  857 */         stringBuffer.append(getValue("CJLBSAC", PokUtils.getAttributeValue(entityItem, "ACRNYM", "", "")));
/*      */       } else {
/*  859 */         stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */       } 
/*  861 */       stringBuffer.append(getValue("CLASSPT", "IHW"));
/*      */       
/*  863 */       str23 = "";
/*      */       
/*  865 */       if (paramString.equals("Europe/Middle East/Africa") || paramString.equals("Latin America")) {
/*  866 */         if (str21.equals("S")) {
/*  867 */           str23 = "CM";
/*      */         }
/*  869 */       } else if (paramString.equals("Asia Pacific")) {
/*  870 */         if (str21.equals("S")) {
/*  871 */           str23 = "CM";
/*      */         }
/*  873 */       } else if (paramString.equals("US Only")) {
/*  874 */         str23 = "NF";
/*  875 */       } else if (paramString.equals("Canada and Caribbean North")) {
/*  876 */         str23 = "";
/*      */       } 
/*      */       
/*  879 */       stringBuffer.append(getValue("CSLMFTY", str23));
/*  880 */       stringBuffer.append(getValue("CVOAT", ""));
/*      */       
/*  882 */       if (paramString.equals("Asia Pacific") || paramString.equals("US Only")) {
/*  883 */         str5 = "N";
/*  884 */       } else if (paramString.equals("Canada and Caribbean North")) {
/*  885 */         str5 = "Y";
/*  886 */       } else if (paramString.equals("Europe/Middle East/Africa") || paramString
/*  887 */         .equals("Latin America")) {
/*  888 */         if (str31.equals("Yes")) {
/*  889 */           str5 = "Y";
/*  890 */         } else if (str31.equals("No")) {
/*  891 */           str5 = "N";
/*      */         } 
/*      */       } 
/*      */       
/*  895 */       stringBuffer.append(getValue("FAGRMBE", str5));
/*      */       
/*  897 */       String str34 = "";
/*  898 */       EntityItem entityItem3 = null;
/*  899 */       if (vector1.size() > 0) {
/*  900 */         for (int i = 0; i < vector1.size(); i++) {
/*  901 */           entityItem3 = vector1.elementAt(i);
/*  902 */           str34 = PokUtils.getAttributeValue(entityItem3, "GENAREASELECTION", "", "");
/*  903 */           if (str34.equals(paramString)) {
/*  904 */             str19 = PokUtils.getAttributeValue(entityItem3, "PURCHONLY", "", "");
/*  905 */             str28 = PokUtils.getAttributeValue(entityItem3, "EDUCPURCHELIG", "", "");
/*  906 */             i = vector1.size();
/*      */           } else {
/*  908 */             entityItem3 = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  913 */       String str35 = PokUtils.getAttributeValue(paramEntityItem2, "ORDERCODE", ",", "", false);
/*  914 */       if (str35.equals("Initial")) {
/*  915 */         if (paramString.equals("Latin America") || paramString.equals("Europe/Middle East/Africa") || paramString
/*  916 */           .equals("Canada and Caribbean North") || paramString
/*  917 */           .equals("Asia Pacific")) {
/*  918 */           str25 = "Y";
/*  919 */         } else if (paramString.equals("US Only")) {
/*  920 */           str25 = "N";
/*      */         } 
/*      */       } else {
/*  923 */         str25 = "N";
/*      */       } 
/*  925 */       stringBuffer.append(getValue("FSLMPIO", str25));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  935 */       if (paramString.equals("Latin America") || paramString.equals("US Only") || paramString
/*  936 */         .equals("Canada and Caribbean North") || paramString
/*  937 */         .equals("Asia Pacific")) {
/*  938 */         stringBuffer.append(getValue("FSLMPOP", "No"));
/*  939 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/*  940 */         stringBuffer.append(getValue("FSLMPOP", "Yes"));
/*      */       } else {
/*  942 */         stringBuffer.append(getValue("FSLMPOP", str19));
/*      */       } 
/*      */       
/*  945 */       stringBuffer.append(getValue("FSLMSTK", "N"));
/*      */       
/*  947 */       String str36 = "";
/*      */       
/*  949 */       ArrayList<String> arrayList = new ArrayList();
/*      */       
/*  951 */       EntityItem entityItem4 = null;
/*  952 */       Vector<EntityItem> vector4 = null;
/*  953 */       String str37 = "";
/*      */       
/*  955 */       vector4 = PokUtils.getAllLinkedEntities(paramEntityItem2, "PRODSTRUCTWARR", "WARR");
/*      */       
/*  957 */       if (!vector4.isEmpty()) {
/*  958 */         entityItem4 = vector4.elementAt(0);
/*  959 */         if (entityItem4 == null) {
/*  960 */           vector4 = PokUtils.getAllLinkedEntities(entityItem1, "MODELWARR", "WARR");
/*  961 */           if (!vector4.isEmpty()) {
/*  962 */             entityItem4 = vector4.elementAt(0);
/*      */           }
/*      */         } 
/*      */       } else {
/*  966 */         vector4 = PokUtils.getAllLinkedEntities(entityItem1, "MODELWARR", "WARR");
/*  967 */         if (!vector4.isEmpty()) {
/*  968 */           entityItem4 = vector4.elementAt(0);
/*      */         }
/*      */       } 
/*      */       
/*  972 */       if (entityItem4 != null) {
/*  973 */         str37 = PokUtils.getAttributeValue(entityItem4, "WARRID", "", "");
/*  974 */         if (str37.equals("WTY0000")) {
/*  975 */           if (vector4.size() > 1) {
/*  976 */             entityItem4 = vector4.elementAt(1);
/*      */           } else {
/*  978 */             entityItem4 = null;
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  983 */       if (entityItem4 != null) {
/*  984 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("WARRTYPE");
/*  985 */         if (eANFlagAttribute != null) {
/*  986 */           if (paramString.equals("Europe/Middle East/Africa")) {
/*  987 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/*  988 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/*  989 */               .isSelected("W0250")) {
/*  990 */               str36 = "Y";
/*      */             } else {
/*  992 */               str36 = "N";
/*      */             } 
/*      */           }
/*      */           
/*  996 */           if (paramString.equals("Latin America")) {
/*  997 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/*  998 */               .isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute
/*  999 */               .isSelected("W0580")) {
/* 1000 */               str36 = "Y";
/*      */             } else {
/* 1002 */               str36 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1006 */           if (paramString.equals("Asia Pacific")) {
/* 1007 */             if (eANFlagAttribute.isSelected("W0550") || eANFlagAttribute.isSelected("W0390") || eANFlagAttribute
/* 1008 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 1009 */               .isSelected("W0250") || eANFlagAttribute.isSelected("W0310") || eANFlagAttribute
/* 1010 */               .isSelected("W0330") || eANFlagAttribute.isSelected("W0590")) {
/* 1011 */               str36 = "Y";
/*      */             } else {
/* 1013 */               str36 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1017 */           if (paramString.equals("Canada and Caribbean North") || paramString.equals("US Only")) {
/* 1018 */             str36 = "N";
/*      */           }
/*      */         } 
/*      */       } else {
/* 1022 */         str36 = "N";
/*      */       } 
/* 1024 */       stringBuffer.append(getValue("FSLM2CF", str36));
/*      */ 
/*      */       
/* 1027 */       stringBuffer.append(getValue("IDORIG", "IBM"));
/* 1028 */       str7 = "000";
/* 1029 */       str8 = "000";
/* 1030 */       str9 = "000";
/* 1031 */       str10 = "000";
/* 1032 */       if (paramString.equals("US Only") || paramString.equals("Canada and Caribbean North") || paramString
/* 1033 */         .equals("Asia Pacific")) {
/* 1034 */         str7 = "000";
/* 1035 */         str8 = "000";
/* 1036 */         str9 = "000";
/* 1037 */         str10 = "000";
/* 1038 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/* 1039 */         str7 = " @@";
/* 1040 */         str8 = " @@";
/* 1041 */         str9 = " @@";
/* 1042 */         str10 = " @@";
/*      */       }
/* 1044 */       else if (entityItem3 != null) {
/*      */ 
/*      */         
/* 1047 */         str7 = getNumValue("PCUAEAP", 
/* 1048 */             PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMHGHSCH", ",", "", false));
/* 1049 */         str8 = getNumValue("PCUAHEA", 
/* 1050 */             PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMHGHSCH", ",", "", false));
/* 1051 */         str9 = getNumValue("PCUASEA", 
/* 1052 */             PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMSECONDRYSCH", ",", "", false));
/* 1053 */         str10 = getNumValue("PCUAUEA", 
/* 1054 */             PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMUNVRSTY", ",", "", false));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1059 */       stringBuffer.append(getValue("PCUAEAP", str7));
/* 1060 */       stringBuffer.append(getValue("PCUAHEA", str8));
/* 1061 */       stringBuffer.append(getValue("PCUASEA", str9));
/* 1062 */       stringBuffer.append(getValue("PCUAUEA", str10));
/*      */       
/* 1064 */       stringBuffer.append(getValue("POGMES", ""));
/*      */       
/* 1066 */       String str38 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/* 1067 */       if (str38.equals("CIF")) {
/* 1068 */         if (paramString.equals("Europe/Middle East/Africa") || paramString
/* 1069 */           .equals("Latin America")) {
/* 1070 */           str26 = "01";
/* 1071 */         } else if (paramString.equals("Asia Pacific")) {
/* 1072 */           str26 = "10";
/* 1073 */         } else if (paramString.equals("US Only") || paramString
/* 1074 */           .equals("Canada and Caribbean North")) {
/* 1075 */           str26 = "";
/*      */         } 
/* 1077 */       } else if (str38.equals("CE") || str38.equals("N/A") || str38.equals("Does not apply")) {
/* 1078 */         str26 = "";
/*      */       } 
/*      */       
/* 1081 */       stringBuffer.append(getValue("QSLMCSU", str26));
/* 1082 */       stringBuffer.append(getValue("QSMXESA", "N"));
/* 1083 */       stringBuffer.append(getValue("QSMXSSA", "N"));
/*      */       
/* 1085 */       String str39 = PokUtils.getAttributeValue(paramEntityItem1, "INVNAME", ",", "", false);
/* 1086 */       stringBuffer.append(getValue("TSLMDES", removeSpecialChars(str39)));
/*      */       
/* 1088 */       str24 = "";
/*      */       
/* 1090 */       if (str21.equals("S")) {
/* 1091 */         str24 = "OTH";
/*      */       }
/*      */       
/* 1094 */       stringBuffer.append(getValue("STSPCFT", str24));
/* 1095 */       stringBuffer.append(getValue("TIMSTMP", ""));
/* 1096 */       stringBuffer.append(getValue("USERID", ""));
/*      */       
/* 1098 */       arrayList = new ArrayList();
/* 1099 */       str11 = "";
/* 1100 */       str12 = "";
/* 1101 */       str13 = "";
/* 1102 */       str14 = "";
/* 1103 */       str15 = "";
/* 1104 */       str16 = "";
/* 1105 */       str17 = "";
/* 1106 */       str18 = "";
/*      */       
/* 1108 */       if (paramString.equals("Asia Pacific") && 
/* 1109 */         entityItem4 != null) {
/* 1110 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("WARRTYPE");
/* 1111 */         if (eANFlagAttribute != null) {
/* 1112 */           if (eANFlagAttribute.isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute
/* 1113 */             .isSelected("W0580")) {
/* 1114 */             arrayList.add("IOR");
/*      */           }
/* 1116 */           if (eANFlagAttribute.isSelected("W0550")) {
/* 1117 */             arrayList.add("IOE");
/*      */           }
/* 1119 */           if (eANFlagAttribute.isSelected("W0390")) {
/* 1120 */             arrayList.add("COE");
/*      */           }
/* 1122 */           if (eANFlagAttribute.isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 1123 */             .isSelected("W0250")) {
/* 1124 */             arrayList.add("CCE");
/*      */           }
/* 1126 */           if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330")) {
/* 1127 */             arrayList.add("CCR");
/*      */           }
/* 1129 */           if (eANFlagAttribute.isSelected("W0590")) {
/* 1130 */             arrayList.add("IOS");
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1163 */           for (byte b1 = 0; b1 < arrayList.size(); b1++) {
/* 1164 */             if (b1 == 0) {
/* 1165 */               str11 = arrayList.get(b1);
/* 1166 */             } else if (b1 == 1) {
/* 1167 */               str12 = arrayList.get(b1);
/* 1168 */             } else if (b1 == 2) {
/* 1169 */               str13 = arrayList.get(b1);
/* 1170 */             } else if (b1 == 3) {
/* 1171 */               str14 = arrayList.get(b1);
/* 1172 */             } else if (b1 == 4) {
/* 1173 */               str15 = arrayList.get(b1);
/* 1174 */             } else if (b1 == 5) {
/* 1175 */               str16 = arrayList.get(b1);
/* 1176 */             } else if (b1 == 6) {
/* 1177 */               str17 = arrayList.get(b1);
/* 1178 */             } else if (b1 == 7) {
/* 1179 */               str18 = arrayList.get(b1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1186 */       stringBuffer.append(getValue("CSLMTM1", str11));
/* 1187 */       stringBuffer.append(getValue("CSLMTM2", str12));
/* 1188 */       stringBuffer.append(getValue("CSLMTM3", str13));
/* 1189 */       stringBuffer.append(getValue("CSLMTM4", str14));
/* 1190 */       stringBuffer.append(getValue("CSLMTM5", str15));
/* 1191 */       stringBuffer.append(getValue("CSLMTM6", str16));
/* 1192 */       stringBuffer.append(getValue("CSLMTM7", str17));
/* 1193 */       stringBuffer.append(getValue("CSLMTM8", str18));
/*      */       
/* 1195 */       stringBuffer.append(getValue("FSAPRES", "N"));
/*      */       
/* 1197 */       if (entityItem4 != null) {
/* 1198 */         if (paramString.equals("US Only")) {
/* 1199 */           stringBuffer.append(getValue("CSLMWCD", PokUtils.getAttributeValue(entityItem4, "WARRCATG", "", "")));
/*      */         } else {
/* 1201 */           stringBuffer.append(getValue("CSLMWCD", " "));
/*      */         } 
/*      */       } else {
/* 1204 */         stringBuffer.append(getValue("CSLMWCD", " "));
/*      */       } 
/*      */       
/* 1207 */       if (paramString.equals("US Only")) {
/* 1208 */         String str = PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false);
/* 1209 */         if (str.equals("Yes")) {
/* 1210 */           stringBuffer.append(getValue("CUSAPMS", "Y"));
/* 1211 */         } else if (str.equals("No")) {
/* 1212 */           stringBuffer.append(getValue("CUSAPMS", "X"));
/*      */         } else {
/* 1214 */           stringBuffer.append(getValue("CUSAPMS", ""));
/*      */         } 
/*      */       } else {
/* 1217 */         stringBuffer.append(getValue("CUSAPMS", ""));
/*      */       } 
/*      */       
/* 1220 */       stringBuffer.append(getValue("DUSALRW", "2050-12-31"));
/* 1221 */       stringBuffer.append(getValue("DUSAMDW", "2050-12-31"));
/* 1222 */       stringBuffer.append(getValue("DUSAWUW", "2050-12-31"));
/*      */       
/* 1224 */       if (paramString.equals("US Only")) {
/* 1225 */         stringBuffer.append(getValue("FSLMCBL", "N"));
/*      */       } else {
/* 1227 */         stringBuffer.append(getValue("FSLMCBL", ""));
/*      */       } 
/*      */       
/* 1230 */       if (paramString.equals("US Only")) {
/* 1231 */         stringBuffer.append(getValue("FUSAAAS", "Y"));
/*      */       } else {
/* 1233 */         stringBuffer.append(getValue("FUSAAAS", ""));
/*      */       } 
/*      */       
/* 1236 */       if (paramString.equals("US Only")) {
/* 1237 */         stringBuffer.append(getValue("FUSAEDE", str28));
/*      */       } else {
/* 1239 */         stringBuffer.append(getValue("FUSAEDE", ""));
/*      */       } 
/*      */       
/* 1242 */       if (paramString.equals("US Only")) {
/* 1243 */         stringBuffer.append(
/* 1244 */             getValue("FUSALEP", PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false)));
/*      */       } else {
/* 1246 */         stringBuffer.append(getValue("FUSALEP", " "));
/*      */       } 
/*      */       
/* 1249 */       if (paramString.equals("US Only")) {
/* 1250 */         stringBuffer.append(getValue("FUSAIRR", "N"));
/*      */       } else {
/* 1252 */         stringBuffer.append(getValue("FUSAIRR", ""));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1258 */       if (entityItem3 != null) {
/* 1259 */         stringBuffer.append(getValue("ICESPCC", PokUtils.getAttributeValue(entityItem3, "PERCALLCLS", ",", "", false)));
/*      */       } else {
/* 1261 */         stringBuffer.append(getValue("ICESPCC", ""));
/*      */       } 
/* 1263 */       stringBuffer.append(getValue("QUSAPOP", "00.0"));
/* 1264 */       stringBuffer.append(getValue("FSLMRFM", ""));
/*      */       
/* 1266 */       stringBuffer.append(NEWLINE);
/* 1267 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 1268 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String removeSpecialChars(String paramString) {
/* 1275 */     String str = "";
/* 1276 */     str = paramString.replaceAll("#", "");
/* 1277 */     str = str.replaceAll("$", "");
/* 1278 */     str = str.replaceAll("%", "");
/* 1279 */     str = str.replaceAll("@", "");
/* 1280 */     str = str.replaceAll("/", "");
/* 1281 */     str = str.replaceAll("'", "");
/* 1282 */     str = str.replaceAll("\"", "");
/* 1283 */     str = str.replaceAll("é¥ï¿½", "");
/*      */     
/* 1285 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT632TypeModelFeatureRelation(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 1306 */     this.m_elist = getEntityList(getFeatureVEName());
/*      */ 
/*      */     
/* 1309 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/* 1310 */     if (eANFlagAttribute != null && (
/* 1311 */       eANFlagAttribute.isSelected("6221") || eANFlagAttribute.isSelected("1999"))) {
/*      */       
/* 1313 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*      */       
/* 1315 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1316 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/* 1318 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */         
/* 1320 */         Vector<EntityItem> vector = entityItem.getDownLink();
/*      */         
/* 1322 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*      */           
/* 1324 */           EntityItem entityItem1 = vector.elementAt(b1);
/*      */           
/* 1326 */           stringBuffer = new StringBuffer();
/* 1327 */           String str1 = "";
/* 1328 */           String str2 = "";
/* 1329 */           String str3 = "";
/*      */           
/* 1331 */           stringBuffer.append(getValue("IFTYPE", "T"));
/* 1332 */           stringBuffer.append(getValue("IOPUCTY", "897"));
/* 1333 */           stringBuffer.append(getValue("ISLMPAL", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 1334 */           stringBuffer.append(getValue("ISLMRFA", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 1335 */           stringBuffer.append(getValue("ISLMTYP", PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "")));
/* 1336 */           stringBuffer.append(getValue("ISLMMOD", PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "")));
/* 1337 */           stringBuffer.append(getValue("ISLMFTR", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 1338 */           stringBuffer.append(getValue("ISLMXX1", ""));
/* 1339 */           stringBuffer.append(getValue("CSLMPCI", "TR"));
/* 1340 */           stringBuffer.append(getValue("FPUNINC", "2"));
/* 1341 */           stringBuffer.append(getValue("CAOAV", ""));
/* 1342 */           stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(entityItem, "ANNDATE", "", "")));
/* 1343 */           stringBuffer.append(getValue("DSLMCPO", PokUtils.getAttributeValue(entityItem, "ANNDATE", "", "")));
/*      */           
/* 1345 */           str3 = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "");
/* 1346 */           if (str3.equals("")) {
/* 1347 */             str3 = "2050-12-31";
/*      */           }
/*      */           
/* 1350 */           stringBuffer.append(getValue("DSLMWDN", str3));
/*      */           
/* 1352 */           str1 = PokUtils.getAttributeValue(entityItem, "ORDERCODE", "", "");
/*      */           
/* 1354 */           if (str1.equals("Both") || str1.equals("Initial")) {
/* 1355 */             stringBuffer.append(getValue("FSLMMES", "N"));
/* 1356 */           } else if (str1.equals("MES")) {
/* 1357 */             stringBuffer.append(getValue("FSLMMES", "Y"));
/*      */           } 
/*      */           
/* 1360 */           if (str1.equals("Initial")) {
/* 1361 */             stringBuffer.append(getValue("FSLMPIO", "Y"));
/*      */           } else {
/* 1363 */             stringBuffer.append(getValue("FSLMPIO", "N"));
/*      */           } 
/*      */           
/* 1366 */           String str4 = PokUtils.getAttributeValue(entityItem, "INSTALL", "", "");
/* 1367 */           if (str4.equals("CIF")) {
/* 1368 */             str2 = "01";
/*      */           } else {
/* 1370 */             str2 = "00";
/*      */           } 
/*      */           
/* 1373 */           stringBuffer.append(getValue("QSLMCSU", str2));
/* 1374 */           stringBuffer.append(getValue("TIMSTMP", ""));
/* 1375 */           stringBuffer.append(getValue("USERID", ""));
/* 1376 */           stringBuffer.append(getValue("FSLMRFM", ""));
/*      */           
/* 1378 */           stringBuffer.append(NEWLINE);
/* 1379 */           paramOutputStreamWriter.write(stringBuffer.toString());
/* 1380 */           paramOutputStreamWriter.flush();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getValue(String paramString1, String paramString2) {
/* 1388 */     if (paramString2 == null)
/* 1389 */       paramString2 = ""; 
/* 1390 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 1391 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1).toString());
/* 1392 */     if (i == j)
/* 1393 */       return paramString2; 
/* 1394 */     if (i > j) {
/* 1395 */       return paramString2.substring(0, j);
/*      */     }
/* 1397 */     return paramString2 + getBlank(j - i);
/*      */   }
/*      */   
/*      */   protected String getBlank(int paramInt) {
/* 1401 */     StringBuffer stringBuffer = new StringBuffer();
/* 1402 */     while (paramInt > 0) {
/* 1403 */       stringBuffer.append(" ");
/* 1404 */       paramInt--;
/*      */     } 
/* 1406 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private String getNumValue(String paramString1, String paramString2) {
/* 1411 */     if (paramString2 == null)
/* 1412 */       paramString2 = ""; 
/* 1413 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 1414 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1).toString());
/* 1415 */     if (i == j)
/* 1416 */       return paramString2; 
/* 1417 */     if (i > j) {
/* 1418 */       return paramString2.substring(0, j);
/*      */     }
/* 1420 */     paramString2 = paramString2.trim();
/* 1421 */     int k = i;
/* 1422 */     while (k < j) {
/* 1423 */       paramString2 = "0" + paramString2;
/* 1424 */       k++;
/*      */     } 
/*      */     
/* 1427 */     return paramString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 1436 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*      */     
/* 1438 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, this.rootEntity
/* 1439 */             .getEntityType(), this.rootEntity.getEntityID()) });
/*      */ 
/*      */     
/* 1442 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 1443 */         PokUtils.outputList(entityList));
/*      */     
/* 1445 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFeatureVEName() {
/* 1454 */     return "QSMRPQFULLVE1";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exeFtpShell(String paramString) {
/* 1462 */     String str1 = ABRServerProperties.getValue(this.abrcode, "_script", null) + " -f " + paramString;
/* 1463 */     String str2 = ABRServerProperties.getValue(this.abrcode, "_inipath", null);
/* 1464 */     if (str2 != null)
/* 1465 */       str1 = str1 + " -i " + str2; 
/* 1466 */     if (this.dir != null)
/* 1467 */       str1 = str1 + " -d " + this.dir; 
/* 1468 */     if (this.fileprefix != null)
/* 1469 */       str1 = str1 + " -p " + this.fileprefix; 
/* 1470 */     String str3 = ABRServerProperties.getValue(this.abrcode, "_targetfilename", null);
/* 1471 */     if (str3 != null)
/* 1472 */       str1 = str1 + " -t " + str3; 
/* 1473 */     String str4 = ABRServerProperties.getValue(this.abrcode, "_logpath", null);
/* 1474 */     if (str4 != null)
/* 1475 */       str1 = str1 + " -l " + str4; 
/* 1476 */     String str5 = ABRServerProperties.getValue(this.abrcode, "_backuppath", null);
/* 1477 */     if (str5 != null)
/* 1478 */       str1 = str1 + " -b " + str5; 
/* 1479 */     Runtime runtime = Runtime.getRuntime();
/* 1480 */     String str6 = "";
/* 1481 */     BufferedReader bufferedReader = null;
/* 1482 */     BufferedInputStream bufferedInputStream = null;
/* 1483 */     addDebug("cmd:" + str1);
/*      */     try {
/* 1485 */       Process process = runtime.exec(str1);
/* 1486 */       if (process.waitFor() != 0) {
/* 1487 */         return false;
/*      */       }
/* 1489 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 1490 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 1491 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 1492 */         str6 = str6 + this.lineStr;
/* 1493 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 1494 */           return false;
/*      */         }
/*      */       } 
/* 1497 */     } catch (Exception exception) {
/* 1498 */       exception.printStackTrace();
/* 1499 */       return false;
/*      */     } finally {
/* 1501 */       if (bufferedReader != null) {
/*      */         try {
/* 1503 */           bufferedReader.close();
/* 1504 */           bufferedInputStream.close();
/* 1505 */         } catch (IOException iOException) {
/* 1506 */           iOException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 1510 */     return !(str6 == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1517 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 1525 */     String str = getBundle().getString(paramString);
/* 1526 */     if (paramArrayOfObject != null) {
/* 1527 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1528 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1531 */     addOutput(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1538 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1545 */     addOutput(paramString);
/* 1546 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 1557 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 1558 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1561 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 1562 */     Object[] arrayOfObject = new Object[2];
/* 1563 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1564 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1566 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1574 */     String str = getBundle().getString(paramString2);
/*      */     
/* 1576 */     if (paramArrayOfObject != null) {
/* 1577 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1578 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1581 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 1590 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1599 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1602 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1603 */     if (eANList == null) {
/* 1604 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1605 */       eANList = entityGroup.getMetaAttribute();
/*      */       
/* 1607 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1609 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1610 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1611 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1612 */       if (b + 1 < eANList.size()) {
/* 1613 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1617 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1626 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1635 */     return "QSMRPQFULLABR";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMRPQFULLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */