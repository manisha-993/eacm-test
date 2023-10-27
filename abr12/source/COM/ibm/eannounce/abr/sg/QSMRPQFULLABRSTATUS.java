/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
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
/*      */ 
/*      */ public class QSMRPQFULLABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  102 */   private StringBuffer rptSb = new StringBuffer();
/*  103 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  104 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*  106 */   private ResourceBundle rsBundle = null;
/*  107 */   private Hashtable metaTbl = new Hashtable<>();
/*  108 */   private String navName = "";
/*      */   
/*  110 */   private String ffFileName = null;
/*  111 */   private String ffPathName = null;
/*  112 */   private String ffFTPPathName = null;
/*  113 */   private String dir = null;
/*  114 */   private String dirDest = null;
/*  115 */   private final String QSMRPTPATH = "_rptpath";
/*  116 */   private final String QSMGENPATH = "_genpath";
/*  117 */   private final String QSMFTPPATH = "_ftppath";
/*  118 */   private int abr_debuglvl = 0;
/*      */   
/*      */   private static final String CREFINIPATH = "_inipath";
/*      */   private static final String FTPSCRPATH = "_script";
/*      */   private static final String TARGETFILENAME = "_targetfilename";
/*      */   private static final String LOGPATH = "_logpath";
/*      */   private static final String BACKUPPATH = "_backuppath";
/*  125 */   private String fileprefix = null;
/*  126 */   private String lineStr = "";
/*      */   
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
/*  269 */   private static final List geoWWList = Collections.unmodifiableList(new ArrayList()
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
/*  284 */     COLUMN_LENGTH = new Hashtable<>();
/*  285 */     COLUMN_LENGTH.put("IFTYPE", "1");
/*  286 */     COLUMN_LENGTH.put("IOPUCTY", "3");
/*  287 */     COLUMN_LENGTH.put("ISLMPAL", "8");
/*  288 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/*  289 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  290 */     COLUMN_LENGTH.put("CSLMPCI", "2");
/*  291 */     COLUMN_LENGTH.put("IPRTNUM", "12");
/*  292 */     COLUMN_LENGTH.put("FPUNINC", "1");
/*  293 */     COLUMN_LENGTH.put("CAOAV", "1");
/*  294 */     COLUMN_LENGTH.put("DSLMCPA", "10");
/*  295 */     COLUMN_LENGTH.put("DSLMCPO", "10");
/*  296 */     COLUMN_LENGTH.put("DSLMGAD", "10");
/*  297 */     COLUMN_LENGTH.put("DSLMMVA", "10");
/*  298 */     COLUMN_LENGTH.put("DSLMOPD", "10");
/*  299 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/*  300 */     COLUMN_LENGTH.put("QSMEDMW", "10");
/*  301 */     COLUMN_LENGTH.put("ASLMMVP", "4");
/*  302 */     COLUMN_LENGTH.put("CCUOICR", "1");
/*  303 */     COLUMN_LENGTH.put("CICIB", "1");
/*  304 */     COLUMN_LENGTH.put("CICIC", "1");
/*  305 */     COLUMN_LENGTH.put("CICRY", "1");
/*  306 */     COLUMN_LENGTH.put("CIDCJ", "1");
/*  307 */     COLUMN_LENGTH.put("CIDXF", "1");
/*  308 */     COLUMN_LENGTH.put("CINCA", "1");
/*  309 */     COLUMN_LENGTH.put("CINCB", "1");
/*  310 */     COLUMN_LENGTH.put("CINCC", "1");
/*  311 */     COLUMN_LENGTH.put("CINPM", "1");
/*  312 */     COLUMN_LENGTH.put("CISUP", "1");
/*  313 */     COLUMN_LENGTH.put("CITEM", "1");
/*  314 */     COLUMN_LENGTH.put("CJLBIC1", "2");
/*  315 */     COLUMN_LENGTH.put("CJLBIDS", "1");
/*  316 */     COLUMN_LENGTH.put("CJLBOEM", "1");
/*  317 */     COLUMN_LENGTH.put("CJLBPOF", "1");
/*  318 */     COLUMN_LENGTH.put("CJLBSAC", "3");
/*  319 */     COLUMN_LENGTH.put("CLASSPT", "3");
/*  320 */     COLUMN_LENGTH.put("CPDAA", "1");
/*  321 */     COLUMN_LENGTH.put("CSLMFCC", "4");
/*  322 */     COLUMN_LENGTH.put("CSLMGGC", "2");
/*  323 */     COLUMN_LENGTH.put("CSLMIDP", "1");
/*  324 */     COLUMN_LENGTH.put("CSLMLRP", "1");
/*  325 */     COLUMN_LENGTH.put("CSLMSAS", "1");
/*  326 */     COLUMN_LENGTH.put("CSLMSYT", "5");
/*  327 */     COLUMN_LENGTH.put("CSLMWCD", "1");
/*  328 */     COLUMN_LENGTH.put("FAGRMBE", "1");
/*  329 */     COLUMN_LENGTH.put("FCUOCNF", "1");
/*  330 */     COLUMN_LENGTH.put("FSLMCLS", "1");
/*  331 */     COLUMN_LENGTH.put("FSLMCPU", "1");
/*  332 */     COLUMN_LENGTH.put("FSLMIOP", "1");
/*  333 */     COLUMN_LENGTH.put("FSLMLGS", "1");
/*  334 */     COLUMN_LENGTH.put("FSLMMLC", "1");
/*  335 */     COLUMN_LENGTH.put("FSLMPOP", "1");
/*  336 */     COLUMN_LENGTH.put("FSLMVDE", "1");
/*  337 */     COLUMN_LENGTH.put("FSLMVTS", "1");
/*  338 */     COLUMN_LENGTH.put("FSLM2CF", "1");
/*  339 */     COLUMN_LENGTH.put("ICESPCC", "1");
/*  340 */     COLUMN_LENGTH.put("IDORIG", "3");
/*  341 */     COLUMN_LENGTH.put("IOLCPLM", "2");
/*  342 */     COLUMN_LENGTH.put("PCUAHEA", "3");
/*  343 */     COLUMN_LENGTH.put("PCUASEA", "3");
/*  344 */     COLUMN_LENGTH.put("PCUAUEA", "3");
/*  345 */     COLUMN_LENGTH.put("QSLMCSU", "2");
/*  346 */     COLUMN_LENGTH.put("QSMXANN", "1");
/*  347 */     COLUMN_LENGTH.put("QSMXESA", "1");
/*  348 */     COLUMN_LENGTH.put("QSMXSSA", "1");
/*  349 */     COLUMN_LENGTH.put("SYSDES", "30");
/*  350 */     COLUMN_LENGTH.put("TSLMDES", "30");
/*  351 */     COLUMN_LENGTH.put("TSLTDES", "56");
/*  352 */     COLUMN_LENGTH.put("TIMSTMP", "26");
/*  353 */     COLUMN_LENGTH.put("USERID", "8");
/*  354 */     COLUMN_LENGTH.put("FBRAND", "1");
/*  355 */     COLUMN_LENGTH.put("FSLMHVP", "1");
/*  356 */     COLUMN_LENGTH.put("FSLMCVP", "1");
/*  357 */     COLUMN_LENGTH.put("FSLMMES", "1");
/*  358 */     COLUMN_LENGTH.put("CSLMTM1", "3");
/*  359 */     COLUMN_LENGTH.put("CSLMTM2", "3");
/*  360 */     COLUMN_LENGTH.put("CSLMTM3", "3");
/*  361 */     COLUMN_LENGTH.put("CSLMTM4", "3");
/*  362 */     COLUMN_LENGTH.put("CSLMTM5", "3");
/*  363 */     COLUMN_LENGTH.put("CSLMTM6", "3");
/*  364 */     COLUMN_LENGTH.put("CSLMTM7", "3");
/*  365 */     COLUMN_LENGTH.put("CSLMTM8", "3");
/*  366 */     COLUMN_LENGTH.put("FSAPRES", "1");
/*  367 */     COLUMN_LENGTH.put("CUSAPMS", "1");
/*  368 */     COLUMN_LENGTH.put("DUSALRW", "10");
/*  369 */     COLUMN_LENGTH.put("DUSAMDW", "10");
/*  370 */     COLUMN_LENGTH.put("DUSAWUW", "10");
/*  371 */     COLUMN_LENGTH.put("FSLMCBL", "1");
/*  372 */     COLUMN_LENGTH.put("FSLMMRR", "1");
/*  373 */     COLUMN_LENGTH.put("FUSAAAS", "1");
/*  374 */     COLUMN_LENGTH.put("FUSAADM", "1");
/*  375 */     COLUMN_LENGTH.put("FUSAEDE", "1");
/*  376 */     COLUMN_LENGTH.put("FUSAICC", "1");
/*  377 */     COLUMN_LENGTH.put("FUSALEP", "1");
/*  378 */     COLUMN_LENGTH.put("FUSAMRS", "1");
/*  379 */     COLUMN_LENGTH.put("FUSAVLM", "1");
/*  380 */     COLUMN_LENGTH.put("FUSAXMO", "1");
/*  381 */     COLUMN_LENGTH.put("QUSAPOP", "4");
/*  382 */     COLUMN_LENGTH.put("DSLMEOD", "10");
/*  383 */     COLUMN_LENGTH.put("FSLMRFM", "1");
/*  384 */     COLUMN_LENGTH.put("DSLMMES", "10");
/*  385 */     COLUMN_LENGTH.put("CIDXC", "1");
/*  386 */     COLUMN_LENGTH.put("CSLMFTY", "2");
/*  387 */     COLUMN_LENGTH.put("CVOAT", "1");
/*  388 */     COLUMN_LENGTH.put("FSLMPIO", "1");
/*  389 */     COLUMN_LENGTH.put("FSLMSTK", "1");
/*  390 */     COLUMN_LENGTH.put("PCUAEAP", "3");
/*  391 */     COLUMN_LENGTH.put("POGMES", "10");
/*  392 */     COLUMN_LENGTH.put("STSPCFT", "4");
/*  393 */     COLUMN_LENGTH.put("FUSAIRR", "1");
/*  394 */     COLUMN_LENGTH.put("CPDXA", "2");
/*  395 */     COLUMN_LENGTH.put("DSLMEFF", "10");
/*  396 */     COLUMN_LENGTH.put("CSLMRCH", "1");
/*  397 */     COLUMN_LENGTH.put("CSLMNUM", "6");
/*  398 */     COLUMN_LENGTH.put("FSLMAPG", "1");
/*  399 */     COLUMN_LENGTH.put("FSLMASP", "1");
/*  400 */     COLUMN_LENGTH.put("FSLMJAP", "1");
/*  401 */     COLUMN_LENGTH.put("FSLMAUS", "1");
/*  402 */     COLUMN_LENGTH.put("FSLMBGL", "1");
/*  403 */     COLUMN_LENGTH.put("FSLMBRU", "1");
/*  404 */     COLUMN_LENGTH.put("FSLMHKG", "1");
/*  405 */     COLUMN_LENGTH.put("FSLMIDN", "1");
/*  406 */     COLUMN_LENGTH.put("FSLMIND", "1");
/*  407 */     COLUMN_LENGTH.put("FSLMKOR", "1");
/*  408 */     COLUMN_LENGTH.put("FSLMMAC", "1");
/*  409 */     COLUMN_LENGTH.put("FSLMMAL", "1");
/*  410 */     COLUMN_LENGTH.put("FSLMMYA", "1");
/*  411 */     COLUMN_LENGTH.put("FSLMNZL", "1");
/*  412 */     COLUMN_LENGTH.put("FSLMPHI", "1");
/*  413 */     COLUMN_LENGTH.put("FSLMPRC", "1");
/*  414 */     COLUMN_LENGTH.put("FSLMSLA", "1");
/*  415 */     COLUMN_LENGTH.put("FSLMSNG", "1");
/*  416 */     COLUMN_LENGTH.put("FSLMTAI", "1");
/*  417 */     COLUMN_LENGTH.put("FSLMTHA", "1");
/*  418 */     COLUMN_LENGTH.put("ISLMTYP", "4");
/*  419 */     COLUMN_LENGTH.put("ISLMMOD", "3");
/*  420 */     COLUMN_LENGTH.put("ISLMFTR", "6");
/*  421 */     COLUMN_LENGTH.put("ISLMXX1", "1");
/*  422 */     COLUMN_LENGTH.put("QSMNPMT", "4");
/*  423 */     COLUMN_LENGTH.put("QSMNPMM", "3");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  430 */     return this.rsBundle;
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
/*      */   public void execute_run() {
/*  444 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  446 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  455 */     String str3 = "";
/*  456 */     boolean bool1 = true;
/*  457 */     boolean bool2 = true;
/*      */ 
/*      */     
/*  460 */     String str4 = "";
/*      */     
/*  462 */     String[] arrayOfString = new String[10];
/*      */     
/*      */     try {
/*  465 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  466 */       arrayOfString[0] = getShortClassName(getClass());
/*  467 */       arrayOfString[1] = "ABR";
/*  468 */       str3 = messageFormat.format(arrayOfString);
/*  469 */       setDGTitle("QSMRPQFULLABRSTATUS report");
/*  470 */       setDGString(getABRReturnCode());
/*  471 */       setDGRptName("QSMRPQFULLABRSTATUS");
/*  472 */       setDGRptClass("QSMRPQFULLABRSTATUS");
/*      */       
/*  474 */       setReturnCode(0);
/*      */       
/*  476 */       start_ABRBuild(false);
/*      */       
/*  478 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */ 
/*      */       
/*  481 */       this.m_elist = getEntityList(getFeatureVEName());
/*      */       
/*  483 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  485 */       if (this.m_elist.getEntityGroupCount() > 0)
/*      */       {
/*  487 */         this.navName = getNavigationName();
/*  488 */         generateFlatFile(entityItem);
/*  489 */         exeFtpShell(this.ffPathName);
/*      */       }
/*      */     
/*  492 */     } catch (Exception exception) {
/*      */       
/*  494 */       exception.printStackTrace();
/*      */       
/*  496 */       setReturnCode(-1);
/*  497 */       StringWriter stringWriter = new StringWriter();
/*  498 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  499 */       String str6 = "<pre>{0}</pre>";
/*  500 */       MessageFormat messageFormat = new MessageFormat(str5);
/*  501 */       setReturnCode(-3);
/*  502 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  504 */       arrayOfString[0] = exception.getMessage();
/*  505 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  506 */       messageFormat = new MessageFormat(str6);
/*  507 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  508 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  509 */       logError("Exception: " + exception.getMessage());
/*  510 */       logError(stringWriter.getBuffer().toString());
/*      */       
/*  512 */       setCreateDGEntity(true);
/*  513 */       bool1 = false;
/*      */     } finally {
/*      */       
/*  516 */       StringBuffer stringBuffer = new StringBuffer();
/*  517 */       MessageFormat messageFormat = new MessageFormat(str2);
/*  518 */       arrayOfString[0] = this.m_prof.getOPName();
/*  519 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/*  520 */       arrayOfString[2] = this.m_prof.getWGName();
/*  521 */       arrayOfString[3] = getNow();
/*  522 */       stringBuffer.append(bool1 ? "generated the QSM report file successful " : "generated the QSM report file faild");
/*  523 */       stringBuffer.append(",");
/*  524 */       stringBuffer.append(bool2 ? "send the QSM report file successful " : "sent the QSM report file faild");
/*  525 */       arrayOfString[4] = stringBuffer.toString();
/*  526 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*      */       
/*  528 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */       
/*  530 */       println(EACustom.getDocTypeHtml());
/*  531 */       println(this.rptSb.toString());
/*  532 */       printDGSubmitString();
/*      */       
/*  534 */       println(EACustom.getTOUDiv());
/*  535 */       buildReportFooter();
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
/*      */   private void setFileName(EntityItem paramEntityItem) {
/*  548 */     this.fileprefix = ABRServerProperties.getFilePrefix(this.m_abri
/*  549 */         .getABRCode());
/*      */ 
/*      */     
/*  552 */     StringBuffer stringBuffer = new StringBuffer(this.fileprefix.trim());
/*  553 */     stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/*  554 */     String str = getNow();
/*      */     
/*  556 */     str = str.replace(' ', '_');
/*  557 */     stringBuffer.append(str + ".txt");
/*  558 */     this.dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_genpath", "/Dgq");
/*  559 */     if (!this.dir.endsWith("/")) {
/*  560 */       this.dir += "/";
/*      */     }
/*  562 */     this.ffFileName = stringBuffer.toString();
/*  563 */     this.ffPathName = this.dir + this.ffFileName;
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
/*      */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/*  576 */     FileChannel fileChannel1 = null;
/*  577 */     FileChannel fileChannel2 = null;
/*      */ 
/*      */     
/*  580 */     setFileName(paramEntityItem);
/*      */     
/*  582 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*      */ 
/*      */ 
/*      */     
/*  586 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */ 
/*      */ 
/*      */     
/*  590 */     createT006Feature(paramEntityItem, outputStreamWriter);
/*  591 */     createT632TypeModelFeatureRelation(paramEntityItem, outputStreamWriter);
/*      */     
/*  593 */     outputStreamWriter.close();
/*      */     
/*  595 */     this.dirDest = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_ftppath", "/Dgq");
/*  596 */     if (!this.dirDest.endsWith("/")) {
/*  597 */       this.dirDest += "/";
/*      */     }
/*      */     
/*  600 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/*      */     
/*      */     try {
/*  603 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/*  604 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/*  605 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*      */     } finally {
/*  607 */       fileChannel1.close();
/*  608 */       fileChannel2.close();
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
/*      */   private void createT006Feature(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/*  623 */     EANFlagAttribute eANFlagAttribute = null;
/*      */     
/*  625 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*      */     
/*  627 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/*  629 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  631 */       eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/*  632 */       if (eANFlagAttribute != null) {
/*  633 */         if (eANFlagAttribute.isSelected("1999")) {
/*  634 */           for (byte b1 = 0; b1 < geoWWList.size(); b1++) {
/*  635 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, geoWWList.get(b1));
/*      */           }
/*      */         } else {
/*  638 */           if (eANFlagAttribute.isSelected("6199")) {
/*  639 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific");
/*      */           }
/*  641 */           if (eANFlagAttribute.isSelected("6200")) {
/*  642 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North");
/*      */           }
/*  644 */           if (eANFlagAttribute.isSelected("6198")) {
/*  645 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa");
/*      */           }
/*  647 */           if (eANFlagAttribute.isSelected("6204")) {
/*  648 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America");
/*      */           }
/*  650 */           if (eANFlagAttribute.isSelected("6221")) {
/*  651 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only");
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
/*      */   private void createT006FeatureRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString) throws IOException, SQLException, MiddlewareException {
/*  699 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem1, "PRODSTRUCT", "MODEL");
/*      */     
/*  701 */     for (byte b = 0; b < vector.size(); b++) {
/*  702 */       StringBuffer stringBuffer = new StringBuffer();
/*  703 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  705 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "MODELGEOMOD", "GEOMOD");
/*      */ 
/*      */       
/*  708 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("SGMNTACRNYM");
/*      */       
/*  710 */       stringBuffer = new StringBuffer();
/*  711 */       String str1 = "";
/*  712 */       String str2 = "";
/*  713 */       String str3 = "";
/*  714 */       String str4 = "";
/*  715 */       String str5 = "";
/*  716 */       String str6 = "";
/*  717 */       String str7 = "";
/*  718 */       String str8 = "";
/*  719 */       String str9 = "";
/*  720 */       String str10 = "";
/*  721 */       String str11 = "";
/*  722 */       String str12 = "";
/*  723 */       String str13 = "";
/*  724 */       String str14 = "";
/*  725 */       String str15 = "";
/*  726 */       String str16 = "";
/*  727 */       String str17 = "";
/*  728 */       String str18 = "";
/*  729 */       String str19 = "";
/*  730 */       String str20 = "";
/*  731 */       String str21 = "";
/*  732 */       String str22 = "";
/*  733 */       String str23 = "";
/*  734 */       String str24 = "";
/*  735 */       String str25 = "";
/*  736 */       String str26 = "";
/*  737 */       String str27 = "";
/*  738 */       String str28 = "";
/*  739 */       String str29 = "";
/*  740 */       String str30 = "";
/*  741 */       String str31 = "";
/*  742 */       str4 = "";
/*      */       
/*  744 */       stringBuffer.append(getValue("IFTYPE", "F"));
/*      */       
/*  746 */       if (paramString.equals("Latin America")) {
/*  747 */         str1 = "601";
/*  748 */         str6 = "LA" + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  749 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/*  750 */         str1 = "999";
/*  751 */         str6 = "ZG" + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  752 */       } else if (paramString.equals("Asia Pacific")) {
/*  753 */         str1 = "872";
/*  754 */         str6 = "AP" + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  755 */       } else if (paramString.equals("US Only")) {
/*  756 */         str1 = "897";
/*  757 */         str6 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*  758 */       } else if (paramString.equals("Canada and Caribbean North")) {
/*  759 */         str1 = "649";
/*  760 */         str6 = PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "");
/*      */       } 
/*      */       
/*  763 */       stringBuffer.append(getValue("IOPUCTY", str1));
/*  764 */       stringBuffer.append(getValue("ISLMPAL", str6));
/*  765 */       stringBuffer.append(getValue("ISLMRFA", PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", "", "")));
/*  766 */       String str32 = PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", ",", "", false);
/*  767 */       str32 = str32 + PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", ",", "", false);
/*  768 */       stringBuffer.append(getValue("ISLMPRN", str32));
/*      */       
/*  770 */       str3 = PokUtils.getAttributeValue(paramEntityItem1, "FCTYPE", ",", "", false);
/*  771 */       str2 = "MF";
/*  772 */       if (str3.equals("RPQ-RLISTED") || str3
/*  773 */         .equals("RPQ-ILISTED") || str3
/*  774 */         .equals("RPQ-PLISTED")) {
/*  775 */         str2 = "MQ";
/*      */       }
/*      */       
/*  778 */       stringBuffer.append(getValue("CSLMPCI", str2));
/*  779 */       stringBuffer.append(getValue("IPRTNUM", ""));
/*  780 */       stringBuffer.append(getValue("FPUNINC", "2"));
/*  781 */       stringBuffer.append(getValue("CAOAV", ""));
/*  782 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", ",", "", false)));
/*  783 */       stringBuffer.append(getValue("DSLMCPO", ""));
/*      */       
/*  785 */       stringBuffer.append(getValue("DSLMGAD", PokUtils.getAttributeValue(paramEntityItem2, "GENAVAILDATE", ",", "", false)));
/*      */       
/*  787 */       if (paramString.equals("Latin America") || paramString
/*  788 */         .equals("Europe/Middle East/Africa")) {
/*  789 */         stringBuffer.append(getValue("DSLMMES", PokUtils.getAttributeValue(paramEntityItem2, "GENAVAILDATE", ",", "", false)));
/*      */       } else {
/*  791 */         stringBuffer.append(getValue("DSLMMES", "2050-12-31"));
/*      */       } 
/*      */       
/*  794 */       stringBuffer.append(getValue("QSMEDMW", "2050-12-31"));
/*  795 */       stringBuffer.append(getValue("DSLMMVA", PokUtils.getAttributeValue(paramEntityItem2, "ANNDATE", ",", "", false)));
/*      */       
/*  797 */       str4 = PokUtils.getAttributeValue(paramEntityItem1, "WITHDRAWDATEEFF_T", ",", "", false);
/*  798 */       if (str4.equals("")) {
/*  799 */         str4 = "2050-12-31";
/*      */       }
/*  801 */       stringBuffer.append(getValue("DSLMWDN", str4));
/*      */       
/*  803 */       str22 = PokUtils.getAttributeValue(paramEntityItem1, "PRICEDFEATURE", ",", "", false);
/*      */       
/*  805 */       if (str3.equals("Primary") && str22.equals("No")) {
/*  806 */         str21 = "S";
/*      */       }
/*      */       
/*  809 */       if (paramString.equals("US Only") || paramString.equals("Canada and Caribbean North")) {
/*  810 */         str20 = "1.00";
/*  811 */       } else if (paramString.equals("Europe/Middle East/Africa") || paramString.equals("Latin America")) {
/*  812 */         if (str21.equals("S")) {
/*  813 */           str20 = "0.00";
/*      */         } else {
/*  815 */           str20 = "1.00";
/*      */         } 
/*  817 */       } else if (paramString.equals("Asia Pacific")) {
/*  818 */         if (str21.equals("S")) {
/*  819 */           str20 = "0.00";
/*  820 */         } else if (str22.equals("No")) {
/*  821 */           str20 = "1.00";
/*      */         } else {
/*  823 */           str20 = "0.00";
/*      */         } 
/*      */       } 
/*      */       
/*  827 */       stringBuffer.append(getValue("ASLMMVP", str20));
/*      */       
/*  829 */       stringBuffer.append(getValue("CICRY", "N"));
/*  830 */       stringBuffer.append(getValue("CIDCJ", "N"));
/*  831 */       stringBuffer.append(getValue("CIDXC", "N"));
/*      */       
/*  833 */       if (paramString.equals("US Only")) {
/*  834 */         stringBuffer.append(getValue("CINCA", "N"));
/*      */       } else {
/*  836 */         stringBuffer.append(getValue("CINCA", "Y"));
/*      */       } 
/*      */       
/*  839 */       String str33 = "";
/*  840 */       str27 = PokUtils.getAttributeValue(paramEntityItem1, "PRICEDFEATURE", "", "");
/*  841 */       if (paramString.equals("US Only")) {
/*  842 */         str33 = "N";
/*      */       }
/*  844 */       else if (str27.equals("Yes")) {
/*  845 */         str33 = "N";
/*  846 */       } else if (str27.equals("No")) {
/*  847 */         str33 = "Y";
/*      */       } else {
/*  849 */         str33 = "N";
/*      */       } 
/*      */ 
/*      */       
/*  853 */       stringBuffer.append(getValue("CINCB", str33));
/*      */       
/*  855 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem2, "PRODSTSTDMT", "STDMAINT");
/*  856 */       Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "MODELSTDMAINT", "STDMAINT");
/*  857 */       EntityItem entityItem2 = null;
/*  858 */       if (!vector2.isEmpty()) {
/*  859 */         entityItem2 = vector2.elementAt(0);
/*  860 */         if (entityItem2 != null) {
/*  861 */           str29 = PokUtils.getAttributeValue(entityItem2, "MAINTELIG", "", "");
/*  862 */           if (str29.equals("") && 
/*  863 */             !vector3.isEmpty()) {
/*  864 */             entityItem2 = vector3.elementAt(0);
/*      */           
/*      */           }
/*      */         }
/*  868 */         else if (!vector3.isEmpty()) {
/*  869 */           entityItem2 = vector3.elementAt(0);
/*      */         }
/*      */       
/*      */       }
/*  873 */       else if (!vector3.isEmpty()) {
/*  874 */         entityItem2 = vector3.elementAt(0);
/*      */       } 
/*      */ 
/*      */       
/*  878 */       if (entityItem2 != null) {
/*  879 */         str29 = PokUtils.getAttributeValue(entityItem2, "MAINTELIG", "", "");
/*      */       }
/*      */       
/*  882 */       str31 = PokUtils.getAttributeValue(paramEntityItem1, "MAINTPRICE", "", "");
/*      */       
/*  884 */       if (paramString.equals("Asia Pacific")) {
/*  885 */         str30 = "Y";
/*  886 */       } else if (paramString.equals("US Only") || paramString.equals("Canada and Caribbean North")) {
/*  887 */         str30 = "N";
/*  888 */       } else if (paramString.equals("Europe/Middle East/Africa") || paramString.equals("Latin America")) {
/*  889 */         if (str31.equals("Yes")) {
/*  890 */           str30 = "N";
/*  891 */         } else if (str31.equals("No")) {
/*  892 */           str30 = "Y";
/*      */         } 
/*      */       } 
/*      */       
/*  896 */       stringBuffer.append(getValue("CINCC", str30));
/*      */ 
/*      */       
/*  899 */       stringBuffer.append(getValue("CINPM", "N"));
/*  900 */       stringBuffer.append(getValue("CITEM", "N"));
/*  901 */       stringBuffer.append(getValue("CISUP", "N"));
/*  902 */       if (entityGroup != null && entityGroup.hasData()) {
/*  903 */         EntityItem entityItem = entityGroup.getEntityItem(0);
/*  904 */         stringBuffer.append(getValue("CJLBSAC", PokUtils.getAttributeValue(entityItem, "ACRNYM", "", "")));
/*      */       } else {
/*  906 */         stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */       } 
/*  908 */       stringBuffer.append(getValue("CLASSPT", "IHW"));
/*      */       
/*  910 */       str23 = "";
/*      */       
/*  912 */       if (paramString.equals("Europe/Middle East/Africa") || paramString.equals("Latin America")) {
/*  913 */         if (str21.equals("S")) {
/*  914 */           str23 = "CM";
/*      */         }
/*  916 */       } else if (paramString.equals("Asia Pacific")) {
/*  917 */         if (str21.equals("S")) {
/*  918 */           str23 = "CM";
/*      */         }
/*  920 */       } else if (paramString.equals("US Only")) {
/*  921 */         str23 = "NF";
/*  922 */       } else if (paramString.equals("Canada and Caribbean North")) {
/*  923 */         str23 = "";
/*      */       } 
/*      */       
/*  926 */       stringBuffer.append(getValue("CSLMFTY", str23));
/*  927 */       stringBuffer.append(getValue("CVOAT", ""));
/*      */       
/*  929 */       if (paramString.equals("Asia Pacific") || paramString
/*  930 */         .equals("US Only")) {
/*  931 */         str5 = "N";
/*  932 */       } else if (paramString.equals("Canada and Caribbean North")) {
/*  933 */         str5 = "Y";
/*  934 */       } else if (paramString.equals("Europe/Middle East/Africa") || paramString.equals("Latin America")) {
/*  935 */         if (str31.equals("Yes")) {
/*  936 */           str5 = "Y";
/*  937 */         } else if (str31.equals("No")) {
/*  938 */           str5 = "N";
/*      */         } 
/*      */       } 
/*      */       
/*  942 */       stringBuffer.append(getValue("FAGRMBE", str5));
/*      */       
/*  944 */       String str34 = "";
/*  945 */       EntityItem entityItem3 = null;
/*  946 */       if (vector1.size() > 0) {
/*  947 */         for (int i = 0; i < vector1.size(); i++) {
/*  948 */           entityItem3 = vector1.elementAt(i);
/*  949 */           str34 = PokUtils.getAttributeValue(entityItem3, "GENAREASELECTION", "", "");
/*  950 */           if (str34.equals(paramString)) {
/*  951 */             str19 = PokUtils.getAttributeValue(entityItem3, "PURCHONLY", "", "");
/*  952 */             str28 = PokUtils.getAttributeValue(entityItem3, "EDUCPURCHELIG", "", "");
/*  953 */             i = vector1.size();
/*      */           } else {
/*  955 */             entityItem3 = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/*  960 */       String str35 = PokUtils.getAttributeValue(paramEntityItem2, "ORDERCODE", ",", "", false);
/*  961 */       if (str35.equals("Initial")) {
/*  962 */         if (paramString.equals("Latin America") || paramString
/*  963 */           .equals("Europe/Middle East/Africa") || paramString
/*  964 */           .equals("Canada and Caribbean North") || paramString
/*  965 */           .equals("Asia Pacific")) {
/*  966 */           str25 = "Y";
/*  967 */         } else if (paramString.equals("US Only")) {
/*  968 */           str25 = "N";
/*      */         } 
/*      */       } else {
/*  971 */         str25 = "N";
/*      */       } 
/*  973 */       stringBuffer.append(getValue("FSLMPIO", str25));
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
/*  988 */       if (paramString.equals("Latin America") || paramString
/*  989 */         .equals("US Only") || paramString
/*  990 */         .equals("Canada and Caribbean North") || paramString
/*  991 */         .equals("Asia Pacific")) {
/*  992 */         stringBuffer.append(getValue("FSLMPOP", "No"));
/*  993 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/*  994 */         stringBuffer.append(getValue("FSLMPOP", "Yes"));
/*      */       } else {
/*  996 */         stringBuffer.append(getValue("FSLMPOP", str19));
/*      */       } 
/*      */       
/*  999 */       stringBuffer.append(getValue("FSLMSTK", "N"));
/*      */       
/* 1001 */       String str36 = "";
/*      */       
/* 1003 */       ArrayList<String> arrayList = new ArrayList();
/*      */       
/* 1005 */       EntityItem entityItem4 = null;
/* 1006 */       Vector<EntityItem> vector4 = null;
/* 1007 */       String str37 = "";
/*      */       
/* 1009 */       vector4 = PokUtils.getAllLinkedEntities(paramEntityItem2, "PRODSTRUCTWARR", "WARR");
/*      */       
/* 1011 */       if (!vector4.isEmpty()) {
/* 1012 */         entityItem4 = vector4.elementAt(0);
/* 1013 */         if (entityItem4 == null) {
/* 1014 */           vector4 = PokUtils.getAllLinkedEntities(entityItem1, "MODELWARR", "WARR");
/* 1015 */           if (!vector4.isEmpty()) {
/* 1016 */             entityItem4 = vector4.elementAt(0);
/*      */           }
/*      */         } 
/*      */       } else {
/* 1020 */         vector4 = PokUtils.getAllLinkedEntities(entityItem1, "MODELWARR", "WARR");
/* 1021 */         if (!vector4.isEmpty()) {
/* 1022 */           entityItem4 = vector4.elementAt(0);
/*      */         }
/*      */       } 
/*      */       
/* 1026 */       if (entityItem4 != null) {
/* 1027 */         str37 = PokUtils.getAttributeValue(entityItem4, "WARRID", "", "");
/* 1028 */         if (str37.equals("WTY0000")) {
/* 1029 */           if (vector4.size() > 1) {
/* 1030 */             entityItem4 = vector4.elementAt(1);
/*      */           } else {
/* 1032 */             entityItem4 = null;
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1037 */       if (entityItem4 != null) {
/* 1038 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("WARRTYPE");
/* 1039 */         if (eANFlagAttribute != null) {
/* 1040 */           if (paramString.equals("Europe/Middle East/Africa")) {
/* 1041 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1042 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250")) {
/* 1043 */               str36 = "Y";
/*      */             } else {
/* 1045 */               str36 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1049 */           if (paramString.equals("Latin America")) {
/* 1050 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1051 */               .isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute.isSelected("W0580")) {
/* 1052 */               str36 = "Y";
/*      */             } else {
/* 1054 */               str36 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1058 */           if (paramString.equals("Asia Pacific")) {
/* 1059 */             if (eANFlagAttribute.isSelected("W0550") || eANFlagAttribute
/* 1060 */               .isSelected("W0390") || eANFlagAttribute
/* 1061 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250") || eANFlagAttribute
/* 1062 */               .isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1063 */               .isSelected("W0590")) {
/* 1064 */               str36 = "Y";
/*      */             } else {
/* 1066 */               str36 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1070 */           if (paramString.equals("Canada and Caribbean North") || paramString
/* 1071 */             .equals("US Only")) {
/* 1072 */             str36 = "N";
/*      */           }
/*      */         } 
/*      */       } else {
/* 1076 */         str36 = "N";
/*      */       } 
/* 1078 */       stringBuffer.append(getValue("FSLM2CF", str36));
/*      */       
/* 1080 */       stringBuffer.append(getValue("IDORIG", "IBM"));
/* 1081 */       str7 = "000";
/* 1082 */       str8 = "000";
/* 1083 */       str9 = "000";
/* 1084 */       str10 = "000";
/* 1085 */       if (paramString.equals("US Only") || paramString.equals("Canada and Caribbean North") || paramString
/* 1086 */         .equals("Asia Pacific")) {
/* 1087 */         str7 = "000";
/* 1088 */         str8 = "000";
/* 1089 */         str9 = "000";
/* 1090 */         str10 = "000";
/* 1091 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/* 1092 */         str7 = " @@";
/* 1093 */         str8 = " @@";
/* 1094 */         str9 = " @@";
/* 1095 */         str10 = " @@";
/*      */       }
/* 1097 */       else if (entityItem3 != null) {
/*      */         
/* 1099 */         str7 = getNumValue("PCUAEAP", PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMHGHSCH", ",", "", false));
/* 1100 */         str8 = getNumValue("PCUAHEA", PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMHGHSCH", ",", "", false));
/* 1101 */         str9 = getNumValue("PCUASEA", PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMSECONDRYSCH", ",", "", false));
/* 1102 */         str10 = getNumValue("PCUAUEA", PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMUNVRSTY", ",", "", false));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1107 */       stringBuffer.append(getValue("PCUAEAP", str7));
/* 1108 */       stringBuffer.append(getValue("PCUAHEA", str8));
/* 1109 */       stringBuffer.append(getValue("PCUASEA", str9));
/* 1110 */       stringBuffer.append(getValue("PCUAUEA", str10));
/*      */       
/* 1112 */       stringBuffer.append(getValue("POGMES", ""));
/*      */       
/* 1114 */       String str38 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/* 1115 */       if (str38.equals("CIF")) {
/* 1116 */         if (paramString.equals("Europe/Middle East/Africa") || paramString
/* 1117 */           .equals("Latin America")) {
/* 1118 */           str26 = "01";
/* 1119 */         } else if (paramString.equals("Asia Pacific")) {
/* 1120 */           str26 = "10";
/* 1121 */         } else if (paramString.equals("US Only") || paramString
/* 1122 */           .equals("Canada and Caribbean North")) {
/* 1123 */           str26 = "";
/*      */         } 
/* 1125 */       } else if (str38.equals("CE") || str38.equals("N/A") || str38.equals("Does not apply")) {
/* 1126 */         str26 = "";
/*      */       } 
/*      */       
/* 1129 */       stringBuffer.append(getValue("QSLMCSU", str26));
/* 1130 */       stringBuffer.append(getValue("QSMXESA", "N"));
/* 1131 */       stringBuffer.append(getValue("QSMXSSA", "N"));
/*      */       
/* 1133 */       String str39 = PokUtils.getAttributeValue(paramEntityItem1, "INVNAME", ",", "", false);
/* 1134 */       stringBuffer.append(getValue("TSLMDES", removeSpecialChars(str39)));
/*      */       
/* 1136 */       str24 = "";
/*      */       
/* 1138 */       if (str21.equals("S")) {
/* 1139 */         str24 = "OTH";
/*      */       }
/*      */       
/* 1142 */       stringBuffer.append(getValue("STSPCFT", str24));
/* 1143 */       stringBuffer.append(getValue("TIMSTMP", ""));
/* 1144 */       stringBuffer.append(getValue("USERID", ""));
/*      */       
/* 1146 */       arrayList = new ArrayList();
/* 1147 */       str11 = "";
/* 1148 */       str12 = "";
/* 1149 */       str13 = "";
/* 1150 */       str14 = "";
/* 1151 */       str15 = "";
/* 1152 */       str16 = "";
/* 1153 */       str17 = "";
/* 1154 */       str18 = "";
/*      */       
/* 1156 */       if (paramString.equals("Asia Pacific") && 
/* 1157 */         entityItem4 != null) {
/* 1158 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("WARRTYPE");
/* 1159 */         if (eANFlagAttribute != null) {
/* 1160 */           if (eANFlagAttribute.isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute.isSelected("W0580")) {
/* 1161 */             arrayList.add("IOR");
/*      */           }
/* 1163 */           if (eANFlagAttribute.isSelected("W0550")) {
/* 1164 */             arrayList.add("IOE");
/*      */           }
/* 1166 */           if (eANFlagAttribute.isSelected("W0390")) {
/* 1167 */             arrayList.add("COE");
/*      */           }
/* 1169 */           if (eANFlagAttribute.isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250")) {
/* 1170 */             arrayList.add("CCE");
/*      */           }
/* 1172 */           if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330")) {
/* 1173 */             arrayList.add("CCR");
/*      */           }
/* 1175 */           if (eANFlagAttribute.isSelected("W0590")) {
/* 1176 */             arrayList.add("IOS");
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
/* 1199 */           for (byte b1 = 0; b1 < arrayList.size(); b1++) {
/* 1200 */             if (b1 == 0) {
/* 1201 */               str11 = arrayList.get(b1);
/* 1202 */             } else if (b1 == 1) {
/* 1203 */               str12 = arrayList.get(b1);
/* 1204 */             } else if (b1 == 2) {
/* 1205 */               str13 = arrayList.get(b1);
/* 1206 */             } else if (b1 == 3) {
/* 1207 */               str14 = arrayList.get(b1);
/* 1208 */             } else if (b1 == 4) {
/* 1209 */               str15 = arrayList.get(b1);
/* 1210 */             } else if (b1 == 5) {
/* 1211 */               str16 = arrayList.get(b1);
/* 1212 */             } else if (b1 == 6) {
/* 1213 */               str17 = arrayList.get(b1);
/* 1214 */             } else if (b1 == 7) {
/* 1215 */               str18 = arrayList.get(b1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1222 */       stringBuffer.append(getValue("CSLMTM1", str11));
/* 1223 */       stringBuffer.append(getValue("CSLMTM2", str12));
/* 1224 */       stringBuffer.append(getValue("CSLMTM3", str13));
/* 1225 */       stringBuffer.append(getValue("CSLMTM4", str14));
/* 1226 */       stringBuffer.append(getValue("CSLMTM5", str15));
/* 1227 */       stringBuffer.append(getValue("CSLMTM6", str16));
/* 1228 */       stringBuffer.append(getValue("CSLMTM7", str17));
/* 1229 */       stringBuffer.append(getValue("CSLMTM8", str18));
/*      */       
/* 1231 */       stringBuffer.append(getValue("FSAPRES", "N"));
/*      */       
/* 1233 */       if (entityItem4 != null) {
/* 1234 */         if (paramString.equals("US Only")) {
/* 1235 */           stringBuffer.append(getValue("CSLMWCD", PokUtils.getAttributeValue(entityItem4, "WARRCATG", "", "")));
/*      */         } else {
/* 1237 */           stringBuffer.append(getValue("CSLMWCD", " "));
/*      */         } 
/*      */       } else {
/* 1240 */         stringBuffer.append(getValue("CSLMWCD", " "));
/*      */       } 
/*      */       
/* 1243 */       if (paramString.equals("US Only")) {
/* 1244 */         String str = PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false);
/* 1245 */         if (str.equals("Yes")) {
/* 1246 */           stringBuffer.append(getValue("CUSAPMS", "Y"));
/* 1247 */         } else if (str.equals("No")) {
/* 1248 */           stringBuffer.append(getValue("CUSAPMS", "X"));
/*      */         } else {
/* 1250 */           stringBuffer.append(getValue("CUSAPMS", ""));
/*      */         } 
/*      */       } else {
/* 1253 */         stringBuffer.append(getValue("CUSAPMS", ""));
/*      */       } 
/*      */       
/* 1256 */       stringBuffer.append(getValue("DUSALRW", "2050-12-31"));
/* 1257 */       stringBuffer.append(getValue("DUSAMDW", "2050-12-31"));
/* 1258 */       stringBuffer.append(getValue("DUSAWUW", "2050-12-31"));
/*      */       
/* 1260 */       if (paramString.equals("US Only")) {
/* 1261 */         stringBuffer.append(getValue("FSLMCBL", "N"));
/*      */       } else {
/* 1263 */         stringBuffer.append(getValue("FSLMCBL", ""));
/*      */       } 
/*      */       
/* 1266 */       if (paramString.equals("US Only")) {
/* 1267 */         stringBuffer.append(getValue("FUSAAAS", "Y"));
/*      */       } else {
/* 1269 */         stringBuffer.append(getValue("FUSAAAS", ""));
/*      */       } 
/*      */       
/* 1272 */       if (paramString.equals("US Only")) {
/* 1273 */         stringBuffer.append(getValue("FUSAEDE", str28));
/*      */       } else {
/* 1275 */         stringBuffer.append(getValue("FUSAEDE", ""));
/*      */       } 
/*      */       
/* 1278 */       if (paramString.equals("US Only")) {
/* 1279 */         stringBuffer.append(getValue("FUSALEP", PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false)));
/*      */       } else {
/* 1281 */         stringBuffer.append(getValue("FUSALEP", " "));
/*      */       } 
/*      */       
/* 1284 */       if (paramString.equals("US Only")) {
/* 1285 */         stringBuffer.append(getValue("FUSAIRR", "N"));
/*      */       } else {
/* 1287 */         stringBuffer.append(getValue("FUSAIRR", ""));
/*      */       } 
/*      */ 
/*      */       
/* 1291 */       if (entityItem3 != null) {
/* 1292 */         stringBuffer.append(getValue("ICESPCC", PokUtils.getAttributeValue(entityItem3, "PERCALLCLS", ",", "", false)));
/*      */       } else {
/* 1294 */         stringBuffer.append(getValue("ICESPCC", ""));
/*      */       } 
/* 1296 */       stringBuffer.append(getValue("QUSAPOP", "00.0"));
/* 1297 */       stringBuffer.append(getValue("FSLMRFM", ""));
/*      */       
/* 1299 */       stringBuffer.append(NEWLINE);
/* 1300 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 1301 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String removeSpecialChars(String paramString) {
/* 1308 */     String str = "";
/* 1309 */     str = paramString.replaceAll("#", "");
/* 1310 */     str = str.replaceAll("$", "");
/* 1311 */     str = str.replaceAll("%", "");
/* 1312 */     str = str.replaceAll("@", "");
/* 1313 */     str = str.replaceAll("/", "");
/* 1314 */     str = str.replaceAll("'", "");
/* 1315 */     str = str.replaceAll("\"", "");
/* 1316 */     str = str.replaceAll("â", "");
/*      */     
/* 1318 */     return str;
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
/*      */   private void createT632TypeModelFeatureRelation(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 1337 */     this.m_elist = getEntityList(getFeatureVEName());
/*      */ 
/*      */     
/* 1340 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/* 1341 */     if (eANFlagAttribute != null && (
/* 1342 */       eANFlagAttribute.isSelected("6221") || eANFlagAttribute.isSelected("1999"))) {
/*      */       
/* 1344 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("PRODSTRUCT");
/*      */       
/* 1346 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1347 */         StringBuffer stringBuffer = new StringBuffer();
/*      */         
/* 1349 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */         
/* 1351 */         Vector<EntityItem> vector = entityItem.getDownLink();
/*      */         
/* 1353 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/*      */           
/* 1355 */           EntityItem entityItem1 = vector.elementAt(b1);
/*      */           
/* 1357 */           stringBuffer = new StringBuffer();
/* 1358 */           String str1 = "";
/* 1359 */           String str2 = "";
/* 1360 */           String str3 = "";
/*      */           
/* 1362 */           stringBuffer.append(getValue("IFTYPE", "T"));
/* 1363 */           stringBuffer.append(getValue("IOPUCTY", "897"));
/* 1364 */           stringBuffer.append(getValue("ISLMPAL", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 1365 */           stringBuffer.append(getValue("ISLMRFA", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 1366 */           stringBuffer.append(getValue("ISLMTYP", PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "")));
/* 1367 */           stringBuffer.append(getValue("ISLMMOD", PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "")));
/* 1368 */           stringBuffer.append(getValue("ISLMFTR", PokUtils.getAttributeValue(paramEntityItem, "FEATURECODE", "", "")));
/* 1369 */           stringBuffer.append(getValue("ISLMXX1", ""));
/* 1370 */           stringBuffer.append(getValue("CSLMPCI", "TR"));
/* 1371 */           stringBuffer.append(getValue("FPUNINC", "2"));
/* 1372 */           stringBuffer.append(getValue("CAOAV", ""));
/* 1373 */           stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(entityItem, "ANNDATE", "", "")));
/* 1374 */           stringBuffer.append(getValue("DSLMCPO", PokUtils.getAttributeValue(entityItem, "ANNDATE", "", "")));
/*      */           
/* 1376 */           str3 = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", "", "");
/* 1377 */           if (str3.equals("")) {
/* 1378 */             str3 = "2050-12-31";
/*      */           }
/*      */           
/* 1381 */           stringBuffer.append(getValue("DSLMWDN", str3));
/*      */           
/* 1383 */           str1 = PokUtils.getAttributeValue(entityItem, "ORDERCODE", "", "");
/*      */           
/* 1385 */           if (str1.equals("Both") || str1.equals("Initial")) {
/* 1386 */             stringBuffer.append(getValue("FSLMMES", "N"));
/* 1387 */           } else if (str1.equals("MES")) {
/* 1388 */             stringBuffer.append(getValue("FSLMMES", "Y"));
/*      */           } 
/*      */           
/* 1391 */           if (str1.equals("Initial")) {
/* 1392 */             stringBuffer.append(getValue("FSLMPIO", "Y"));
/*      */           } else {
/* 1394 */             stringBuffer.append(getValue("FSLMPIO", "N"));
/*      */           } 
/*      */           
/* 1397 */           String str4 = PokUtils.getAttributeValue(entityItem, "INSTALL", "", "");
/* 1398 */           if (str4.equals("CIF")) {
/* 1399 */             str2 = "01";
/*      */           } else {
/* 1401 */             str2 = "00";
/*      */           } 
/*      */           
/* 1404 */           stringBuffer.append(getValue("QSLMCSU", str2));
/* 1405 */           stringBuffer.append(getValue("TIMSTMP", ""));
/* 1406 */           stringBuffer.append(getValue("USERID", ""));
/* 1407 */           stringBuffer.append(getValue("FSLMRFM", ""));
/*      */           
/* 1409 */           stringBuffer.append(NEWLINE);
/* 1410 */           paramOutputStreamWriter.write(stringBuffer.toString());
/* 1411 */           paramOutputStreamWriter.flush();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getValue(String paramString1, String paramString2) {
/* 1419 */     if (paramString2 == null)
/* 1420 */       paramString2 = ""; 
/* 1421 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 1422 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1)
/* 1423 */         .toString());
/* 1424 */     if (i == j)
/* 1425 */       return paramString2; 
/* 1426 */     if (i > j) {
/* 1427 */       return paramString2.substring(0, j);
/*      */     }
/* 1429 */     return paramString2 + getBlank(j - i);
/*      */   }
/*      */   
/*      */   protected String getBlank(int paramInt) {
/* 1433 */     StringBuffer stringBuffer = new StringBuffer();
/* 1434 */     while (paramInt > 0) {
/* 1435 */       stringBuffer.append(" ");
/* 1436 */       paramInt--;
/*      */     } 
/* 1438 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private String getNumValue(String paramString1, String paramString2) {
/* 1443 */     if (paramString2 == null)
/* 1444 */       paramString2 = ""; 
/* 1445 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 1446 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1)
/* 1447 */         .toString());
/* 1448 */     if (i == j)
/* 1449 */       return paramString2; 
/* 1450 */     if (i > j) {
/* 1451 */       return paramString2.substring(0, j);
/*      */     }
/* 1453 */     paramString2 = paramString2.trim();
/* 1454 */     int k = i;
/* 1455 */     while (k < j) {
/* 1456 */       paramString2 = "0" + paramString2;
/* 1457 */       k++;
/*      */     } 
/*      */     
/* 1460 */     return paramString2;
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
/*      */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 1477 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*      */     
/* 1479 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, getEntityType(), getEntityID()) });
/*      */ 
/*      */     
/* 1482 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 1483 */         PokUtils.outputList(entityList));
/*      */     
/* 1485 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFeatureVEName() {
/* 1493 */     return "QSMRPQFULLVE1";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exeFtpShell(String paramString) {
/* 1501 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_script", null) + " -f " + paramString;
/* 1502 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_inipath", null);
/* 1503 */     if (str2 != null)
/* 1504 */       str1 = str1 + " -i " + str2; 
/* 1505 */     if (this.dir != null)
/* 1506 */       str1 = str1 + " -d " + this.dir; 
/* 1507 */     if (this.fileprefix != null)
/* 1508 */       str1 = str1 + " -p " + this.fileprefix; 
/* 1509 */     String str3 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_targetfilename", null);
/* 1510 */     if (str3 != null)
/* 1511 */       str1 = str1 + " -t " + str3; 
/* 1512 */     String str4 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_logpath", null);
/* 1513 */     if (str4 != null)
/* 1514 */       str1 = str1 + " -l " + str4; 
/* 1515 */     String str5 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_backuppath", null);
/* 1516 */     if (str5 != null)
/* 1517 */       str1 = str1 + " -b " + str5; 
/* 1518 */     Runtime runtime = Runtime.getRuntime();
/* 1519 */     String str6 = "";
/* 1520 */     BufferedReader bufferedReader = null;
/* 1521 */     BufferedInputStream bufferedInputStream = null;
/* 1522 */     addDebug("cmd:" + str1);
/*      */     try {
/* 1524 */       Process process = runtime.exec(str1);
/* 1525 */       if (process.waitFor() != 0) {
/* 1526 */         return false;
/*      */       }
/* 1528 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 1529 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 1530 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 1531 */         str6 = str6 + this.lineStr;
/* 1532 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 1533 */           return false;
/*      */         }
/*      */       } 
/* 1536 */     } catch (Exception exception) {
/* 1537 */       exception.printStackTrace();
/* 1538 */       return false;
/*      */     } finally {
/* 1540 */       if (bufferedReader != null) {
/*      */         try {
/* 1542 */           bufferedReader.close();
/* 1543 */           bufferedInputStream.close();
/* 1544 */         } catch (IOException iOException) {
/* 1545 */           iOException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 1549 */     return !(str6 == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1556 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 1564 */     String str = getBundle().getString(paramString);
/* 1565 */     if (paramArrayOfObject != null) {
/* 1566 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1567 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1570 */     addOutput(str);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1575 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1581 */     addOutput(paramString);
/* 1582 */     setReturnCode(-1);
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
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 1595 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 1596 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1599 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 1600 */     Object[] arrayOfObject = new Object[2];
/* 1601 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1602 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1604 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1613 */     String str = getBundle().getString(paramString2);
/*      */     
/* 1615 */     if (paramArrayOfObject != null) {
/* 1616 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1617 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1620 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 1630 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1640 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 1643 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1644 */     if (eANList == null) {
/*      */       
/* 1646 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1647 */       eANList = entityGroup.getMetaAttribute();
/* 1648 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1650 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1652 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1653 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1654 */       if (b + 1 < eANList.size()) {
/* 1655 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 1659 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1669 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1678 */     return "QSMRPQFULLABRSTATUS";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMRPQFULLABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */