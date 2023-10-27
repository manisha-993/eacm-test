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
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
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
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class QSMFULLABR
/*      */   extends PokBaseABR
/*      */ {
/*  313 */   private StringBuffer rptSb = new StringBuffer();
/*  314 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  315 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*  317 */   private ResourceBundle rsBundle = null;
/*  318 */   private Hashtable metaTbl = new Hashtable<>();
/*  319 */   private String navName = "";
/*      */   
/*  321 */   private String ffFileName = null;
/*  322 */   private String ffPathName = null;
/*  323 */   private String ffFTPPathName = null;
/*  324 */   private String dir = null;
/*  325 */   private String dirDest = null;
/*  326 */   private final String QSMRPTPATH = "_rptpath";
/*  327 */   private final String QSMGENPATH = "_genpath";
/*  328 */   private final String QSMFTPPATH = "_ftppath";
/*  329 */   private int abr_debuglvl = 0;
/*      */   
/*      */   private static final String CREFINIPATH = "_inipath";
/*      */   private static final String FTPSCRPATH = "_script";
/*      */   private static final String TARGETFILENAME = "_targetfilename";
/*      */   private static final String LOGPATH = "_logpath";
/*      */   private static final String BACKUPPATH = "_backuppath";
/*  336 */   private String fileprefix = null;
/*  337 */   private String lineStr = "";
/*  338 */   private String abrcode = "";
/*      */   
/*      */   private EntityItem rootEntity;
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
/*  482 */   private static final List geoWWList = Collections.unmodifiableList(new ArrayList()
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
/*  497 */     COLUMN_LENGTH = new Hashtable<>();
/*  498 */     COLUMN_LENGTH.put("IFTYPE", "1");
/*  499 */     COLUMN_LENGTH.put("IOPUCTY", "3");
/*  500 */     COLUMN_LENGTH.put("ISLMPAL", "8");
/*  501 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/*  502 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  503 */     COLUMN_LENGTH.put("CSLMPCI", "2");
/*  504 */     COLUMN_LENGTH.put("IPRTNUM", "12");
/*  505 */     COLUMN_LENGTH.put("FPUNINC", "1");
/*  506 */     COLUMN_LENGTH.put("CAOAV", "1");
/*  507 */     COLUMN_LENGTH.put("DSLMCPA", "10");
/*  508 */     COLUMN_LENGTH.put("DSLMCPO", "10");
/*  509 */     COLUMN_LENGTH.put("DSLMGAD", "10");
/*  510 */     COLUMN_LENGTH.put("DSLMMVA", "10");
/*  511 */     COLUMN_LENGTH.put("DSLMOPD", "10");
/*  512 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/*  513 */     COLUMN_LENGTH.put("QSMEDMW", "10");
/*  514 */     COLUMN_LENGTH.put("ASLMMVP", "4");
/*  515 */     COLUMN_LENGTH.put("CCUOICR", "1");
/*  516 */     COLUMN_LENGTH.put("CICIB", "1");
/*  517 */     COLUMN_LENGTH.put("CICIC", "1");
/*  518 */     COLUMN_LENGTH.put("CICRY", "1");
/*  519 */     COLUMN_LENGTH.put("CIDCJ", "1");
/*  520 */     COLUMN_LENGTH.put("CIDXF", "1");
/*  521 */     COLUMN_LENGTH.put("CINCA", "1");
/*  522 */     COLUMN_LENGTH.put("CINCB", "1");
/*  523 */     COLUMN_LENGTH.put("CINCC", "1");
/*  524 */     COLUMN_LENGTH.put("CINPM", "1");
/*  525 */     COLUMN_LENGTH.put("CISUP", "1");
/*  526 */     COLUMN_LENGTH.put("CITEM", "1");
/*  527 */     COLUMN_LENGTH.put("CJLBIC1", "2");
/*  528 */     COLUMN_LENGTH.put("CJLBIDS", "1");
/*  529 */     COLUMN_LENGTH.put("CJLBOEM", "1");
/*  530 */     COLUMN_LENGTH.put("CJLBPOF", "1");
/*  531 */     COLUMN_LENGTH.put("CJLBSAC", "3");
/*  532 */     COLUMN_LENGTH.put("CLASSPT", "3");
/*  533 */     COLUMN_LENGTH.put("CPDAA", "1");
/*  534 */     COLUMN_LENGTH.put("CSLMFCC", "4");
/*  535 */     COLUMN_LENGTH.put("CSLMGGC", "2");
/*  536 */     COLUMN_LENGTH.put("CSLMIDP", "1");
/*  537 */     COLUMN_LENGTH.put("CSLMLRP", "1");
/*  538 */     COLUMN_LENGTH.put("CSLMSAS", "1");
/*  539 */     COLUMN_LENGTH.put("CSLMSYT", "5");
/*  540 */     COLUMN_LENGTH.put("CSLMWCD", "1");
/*  541 */     COLUMN_LENGTH.put("FAGRMBE", "1");
/*  542 */     COLUMN_LENGTH.put("FCUOCNF", "1");
/*  543 */     COLUMN_LENGTH.put("FSLMCLS", "1");
/*  544 */     COLUMN_LENGTH.put("FSLMCPU", "1");
/*  545 */     COLUMN_LENGTH.put("FSLMIOP", "1");
/*  546 */     COLUMN_LENGTH.put("FSLMLGS", "1");
/*  547 */     COLUMN_LENGTH.put("FSLMMLC", "1");
/*  548 */     COLUMN_LENGTH.put("FSLMPOP", "1");
/*  549 */     COLUMN_LENGTH.put("FSLMVDE", "1");
/*  550 */     COLUMN_LENGTH.put("FSLMVTS", "1");
/*  551 */     COLUMN_LENGTH.put("FSLM2CF", "1");
/*  552 */     COLUMN_LENGTH.put("ICESPCC", "1");
/*  553 */     COLUMN_LENGTH.put("IDORIG", "3");
/*  554 */     COLUMN_LENGTH.put("IOLCPLM", "2");
/*  555 */     COLUMN_LENGTH.put("PCUAHEA", "3");
/*  556 */     COLUMN_LENGTH.put("PCUASEA", "3");
/*  557 */     COLUMN_LENGTH.put("PCUAUEA", "3");
/*  558 */     COLUMN_LENGTH.put("QSLMCSU", "2");
/*  559 */     COLUMN_LENGTH.put("QSMXANN", "1");
/*  560 */     COLUMN_LENGTH.put("QSMXESA", "1");
/*  561 */     COLUMN_LENGTH.put("QSMXSSA", "1");
/*  562 */     COLUMN_LENGTH.put("SYSDES", "30");
/*  563 */     COLUMN_LENGTH.put("TSLMDES", "30");
/*  564 */     COLUMN_LENGTH.put("TSLTDES", "56");
/*  565 */     COLUMN_LENGTH.put("TIMSTMP", "26");
/*  566 */     COLUMN_LENGTH.put("USERID", "8");
/*  567 */     COLUMN_LENGTH.put("FBRAND", "1");
/*  568 */     COLUMN_LENGTH.put("FSLMHVP", "1");
/*  569 */     COLUMN_LENGTH.put("FSLMCVP", "1");
/*  570 */     COLUMN_LENGTH.put("FSLMMES", "1");
/*  571 */     COLUMN_LENGTH.put("CSLMTM1", "3");
/*  572 */     COLUMN_LENGTH.put("CSLMTM2", "3");
/*  573 */     COLUMN_LENGTH.put("CSLMTM3", "3");
/*  574 */     COLUMN_LENGTH.put("CSLMTM4", "3");
/*  575 */     COLUMN_LENGTH.put("CSLMTM5", "3");
/*  576 */     COLUMN_LENGTH.put("CSLMTM6", "3");
/*  577 */     COLUMN_LENGTH.put("CSLMTM7", "3");
/*  578 */     COLUMN_LENGTH.put("CSLMTM8", "3");
/*  579 */     COLUMN_LENGTH.put("FSAPRES", "1");
/*  580 */     COLUMN_LENGTH.put("CUSAPMS", "1");
/*  581 */     COLUMN_LENGTH.put("DUSALRW", "10");
/*  582 */     COLUMN_LENGTH.put("DUSAMDW", "10");
/*  583 */     COLUMN_LENGTH.put("DUSAWUW", "10");
/*  584 */     COLUMN_LENGTH.put("FSLMCBL", "1");
/*  585 */     COLUMN_LENGTH.put("FSLMMRR", "1");
/*  586 */     COLUMN_LENGTH.put("FUSAAAS", "1");
/*  587 */     COLUMN_LENGTH.put("FUSAADM", "1");
/*  588 */     COLUMN_LENGTH.put("FUSAEDE", "1");
/*  589 */     COLUMN_LENGTH.put("FUSAICC", "1");
/*  590 */     COLUMN_LENGTH.put("FUSALEP", "1");
/*  591 */     COLUMN_LENGTH.put("FUSAMRS", "1");
/*  592 */     COLUMN_LENGTH.put("FUSAVLM", "1");
/*  593 */     COLUMN_LENGTH.put("FUSAXMO", "1");
/*  594 */     COLUMN_LENGTH.put("QUSAPOP", "4");
/*  595 */     COLUMN_LENGTH.put("DSLMEOD", "10");
/*  596 */     COLUMN_LENGTH.put("FSLMRFM", "1");
/*  597 */     COLUMN_LENGTH.put("DSLMMES", "10");
/*  598 */     COLUMN_LENGTH.put("CIDXC", "1");
/*  599 */     COLUMN_LENGTH.put("CSLMFTY", "2");
/*  600 */     COLUMN_LENGTH.put("CVOAT", "1");
/*  601 */     COLUMN_LENGTH.put("FSLMPIO", "1");
/*  602 */     COLUMN_LENGTH.put("FSLMSTK", "1");
/*  603 */     COLUMN_LENGTH.put("PCUAEAP", "3");
/*  604 */     COLUMN_LENGTH.put("POGMES", "10");
/*  605 */     COLUMN_LENGTH.put("STSPCFT", "4");
/*  606 */     COLUMN_LENGTH.put("FUSAIRR", "1");
/*  607 */     COLUMN_LENGTH.put("CPDXA", "2");
/*  608 */     COLUMN_LENGTH.put("DSLMEFF", "10");
/*  609 */     COLUMN_LENGTH.put("CSLMRCH", "1");
/*  610 */     COLUMN_LENGTH.put("CSLMNUM", "6");
/*  611 */     COLUMN_LENGTH.put("FSLMAPG", "1");
/*  612 */     COLUMN_LENGTH.put("FSLMASP", "1");
/*  613 */     COLUMN_LENGTH.put("FSLMJAP", "1");
/*  614 */     COLUMN_LENGTH.put("FSLMAUS", "1");
/*  615 */     COLUMN_LENGTH.put("FSLMBGL", "1");
/*  616 */     COLUMN_LENGTH.put("FSLMBRU", "1");
/*  617 */     COLUMN_LENGTH.put("FSLMHKG", "1");
/*  618 */     COLUMN_LENGTH.put("FSLMIDN", "1");
/*  619 */     COLUMN_LENGTH.put("FSLMIND", "1");
/*  620 */     COLUMN_LENGTH.put("FSLMKOR", "1");
/*  621 */     COLUMN_LENGTH.put("FSLMMAC", "1");
/*  622 */     COLUMN_LENGTH.put("FSLMMAL", "1");
/*  623 */     COLUMN_LENGTH.put("FSLMMYA", "1");
/*  624 */     COLUMN_LENGTH.put("FSLMNZL", "1");
/*  625 */     COLUMN_LENGTH.put("FSLMPHI", "1");
/*  626 */     COLUMN_LENGTH.put("FSLMPRC", "1");
/*  627 */     COLUMN_LENGTH.put("FSLMSLA", "1");
/*  628 */     COLUMN_LENGTH.put("FSLMSNG", "1");
/*  629 */     COLUMN_LENGTH.put("FSLMTAI", "1");
/*  630 */     COLUMN_LENGTH.put("FSLMTHA", "1");
/*  631 */     COLUMN_LENGTH.put("ISLMTYP", "4");
/*  632 */     COLUMN_LENGTH.put("ISLMMOD", "3");
/*  633 */     COLUMN_LENGTH.put("ISLMFTR", "6");
/*  634 */     COLUMN_LENGTH.put("ISLMXX1", "1");
/*  635 */     COLUMN_LENGTH.put("QSMNPMT", "4");
/*  636 */     COLUMN_LENGTH.put("QSMNPMM", "3");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  643 */     return this.rsBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processThis(QSMFULLABRSTATUS paramQSMFULLABRSTATUS, Profile paramProfile, Database paramDatabase, String paramString, EntityItem paramEntityItem, StringBuffer paramStringBuffer) throws Exception {
/*      */     try {
/*  654 */       this.m_prof = paramProfile;
/*  655 */       this.m_db = paramDatabase;
/*  656 */       this.abrcode = paramString;
/*  657 */       this.rootEntity = paramEntityItem;
/*      */       
/*  659 */       setReturnCode(0);
/*      */       
/*  661 */       this.rptSb = paramStringBuffer;
/*  662 */       this.m_elist = getEntityList(getT002ModelVEName());
/*      */ 
/*      */       
/*  665 */       if (this.m_elist.getEntityGroupCount() > 0) {
/*  666 */         addDebug("QSMfull abr running");
/*      */         
/*  668 */         this.navName = getNavigationName();
/*  669 */         setDGTitle(this.navName);
/*  670 */         setDGString(getABRReturnCode());
/*  671 */         setDGRptName("QSMFULLABR");
/*  672 */         setDGRptClass(getABRCode());
/*  673 */         generateFlatFile(paramEntityItem);
/*  674 */         exeFtpShell(this.ffPathName);
/*      */       }
/*      */     
/*  677 */     } catch (IOException iOException) {
/*      */       
/*  679 */       iOException.printStackTrace();
/*  680 */     } catch (ParseException parseException) {
/*      */       
/*  682 */       parseException.printStackTrace();
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
/*  695 */     this.fileprefix = ABRServerProperties.getFilePrefix(this.abrcode);
/*      */ 
/*      */     
/*  698 */     StringBuffer stringBuffer = new StringBuffer(this.fileprefix.trim());
/*      */     
/*      */     try {
/*  701 */       DatePackage datePackage = this.m_db.getDates();
/*  702 */       String str = datePackage.getNow();
/*      */       
/*  704 */       str = str.replace(' ', '_');
/*  705 */       stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/*  706 */       stringBuffer.append(str + ".txt");
/*  707 */       this.dir = ABRServerProperties.getValue(this.abrcode, "_genpath", "/Dgq");
/*  708 */       if (!this.dir.endsWith("/")) {
/*  709 */         this.dir += "/";
/*      */       }
/*  711 */       this.ffFileName = stringBuffer.toString();
/*  712 */       this.ffPathName = this.dir + this.ffFileName;
/*  713 */     } catch (MiddlewareException middlewareException) {
/*      */       
/*  715 */       middlewareException.printStackTrace();
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
/*      */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/*  730 */     FileChannel fileChannel1 = null;
/*  731 */     FileChannel fileChannel2 = null;
/*      */ 
/*      */     
/*  734 */     setFileName(paramEntityItem);
/*      */     
/*  736 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*      */ 
/*      */ 
/*      */     
/*  740 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */ 
/*      */ 
/*      */     
/*  744 */     createT002Model(paramEntityItem, outputStreamWriter);
/*  745 */     createT006Feature(paramEntityItem, outputStreamWriter);
/*  746 */     createT017ProductCategory(paramEntityItem, outputStreamWriter);
/*  747 */     createT020NPMesUpgrade(paramEntityItem, outputStreamWriter);
/*  748 */     createT512ReleaseTo(paramEntityItem, outputStreamWriter);
/*  749 */     createT632TypeModelFeatureRelation(paramEntityItem, outputStreamWriter);
/*      */     
/*  751 */     outputStreamWriter.close();
/*      */     
/*  753 */     this.dirDest = ABRServerProperties.getValue(this.abrcode, "_ftppath", "/Dgq");
/*  754 */     if (!this.dirDest.endsWith("/")) {
/*  755 */       this.dirDest += "/";
/*      */     }
/*      */     
/*  758 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/*  759 */     addDebug("******* " + this.ffFTPPathName);
/*      */     
/*      */     try {
/*  762 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/*  763 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/*  764 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*      */     } finally {
/*  766 */       fileChannel1.close();
/*  767 */       fileChannel2.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT002Model(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException {
/*  777 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  778 */     String str1 = "";
/*  779 */     String str2 = "";
/*  780 */     boolean bool = false;
/*      */     
/*  782 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/*  784 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  786 */       str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*  787 */       str2 = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*  788 */       if (str2.equals("EPIC")) {
/*  789 */         bool = true;
/*      */       }
/*      */ 
/*      */       
/*  793 */       if (str1.equals("Planned Availability") || str1
/*  794 */         .equals("End of Service") || str1
/*  795 */         .equals("Last Order")) {
/*  796 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/*      */         
/*  798 */         if (eANFlagAttribute != null) {
/*  799 */           if (eANFlagAttribute.isSelected("6199")) {
/*  800 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", str1, bool);
/*      */           }
/*  802 */           if (eANFlagAttribute.isSelected("6200")) {
/*  803 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", str1, bool);
/*      */           }
/*  805 */           if (eANFlagAttribute.isSelected("6198")) {
/*  806 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", str1, bool);
/*      */           }
/*  808 */           if (eANFlagAttribute.isSelected("6204")) {
/*  809 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", str1, bool);
/*      */           }
/*  811 */           if (eANFlagAttribute.isSelected("6221")) {
/*  812 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", str1, bool);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT002ModelRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString1, String paramString2, boolean paramBoolean) throws IOException {
/*  870 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELAVAIL", "MODEL");
/*      */     
/*  872 */     for (byte b = 0; b < vector.size(); b++) {
/*  873 */       StringBuffer stringBuffer = new StringBuffer();
/*  874 */       String str1 = "";
/*  875 */       String str2 = "";
/*  876 */       String str3 = "";
/*  877 */       String str4 = "";
/*  878 */       String str5 = "";
/*  879 */       String str6 = "";
/*  880 */       String str7 = "";
/*  881 */       String str8 = "";
/*  882 */       String str9 = "";
/*  883 */       String str10 = "";
/*  884 */       String str11 = "";
/*  885 */       String str12 = "";
/*  886 */       String str13 = "";
/*  887 */       String str14 = "";
/*  888 */       String str15 = "";
/*  889 */       String str16 = "";
/*  890 */       String str17 = "";
/*  891 */       String str18 = "";
/*  892 */       String str19 = "";
/*  893 */       String str20 = "";
/*  894 */       String str21 = "";
/*  895 */       String str22 = "";
/*  896 */       String str23 = "";
/*  897 */       String str24 = "";
/*  898 */       String str25 = "";
/*  899 */       String str26 = "";
/*  900 */       String str27 = "";
/*  901 */       String str28 = "";
/*  902 */       String str29 = "";
/*  903 */       String str30 = "";
/*  904 */       String str31 = "";
/*  905 */       String str32 = "";
/*      */       
/*  907 */       String str33 = "";
/*  908 */       String str34 = "";
/*  909 */       String str35 = "";
/*  910 */       String str36 = "";
/*  911 */       String str37 = "";
/*  912 */       String str38 = "";
/*  913 */       String str39 = "";
/*  914 */       String str40 = "";
/*  915 */       String str41 = "";
/*  916 */       String str42 = "";
/*      */       
/*  918 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/*  920 */       EntityItem entityItem2 = null;
/*      */       
/*  922 */       if (paramString1.equals("Asia Pacific") || paramString1
/*  923 */         .equals("US Only") || paramString1
/*  924 */         .equals("Canada and Caribbean North")) {
/*  925 */         str23 = "N";
/*      */       } else {
/*      */         
/*  928 */         Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "MODELSTDMAINT", "STDMAINT");
/*  929 */         if (!vector3.isEmpty()) {
/*  930 */           EntityItem entityItem = vector3.elementAt(0);
/*  931 */           if (entityItem != null) {
/*  932 */             str23 = PokUtils.getAttributeValue(entityItem, "MAINTELIG", "", "");
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  942 */       stringBuffer.append(getValue("IFTYPE", "M"));
/*      */       
/*  944 */       if (paramString1.equals("Latin America")) {
/*  945 */         str9 = "601";
/*  946 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/*  947 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  948 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/*  949 */         str9 = "999";
/*  950 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/*  951 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  952 */       } else if (paramString1.equals("Asia Pacific")) {
/*  953 */         str9 = "872";
/*  954 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/*  955 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*  956 */       } else if (paramString1.equals("US Only")) {
/*  957 */         str9 = "897";
/*  958 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*  959 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*  960 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/*  961 */         str9 = "649";
/*  962 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/*  963 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */       } 
/*  965 */       stringBuffer.append(getValue("IOPUCTY", str9));
/*      */       
/*  967 */       stringBuffer.append(getValue("ISLMPAL", str10));
/*  968 */       stringBuffer.append(getValue("ISLMRFA", str28));
/*  969 */       String str43 = PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "");
/*  970 */       str43 = str43 + PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "");
/*  971 */       stringBuffer.append(getValue("ISLMPRN", str43));
/*  972 */       stringBuffer.append(getValue("CSLMPCI", "MM"));
/*  973 */       stringBuffer.append(getValue("IPRTNUM", "            "));
/*  974 */       stringBuffer.append(getValue("FPUNINC", "2"));
/*  975 */       stringBuffer.append(getValue("CAOAV", ""));
/*      */       
/*  977 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/*  978 */       stringBuffer.append(getValue("DSLMCPO", ""));
/*  979 */       stringBuffer.append(getValue("DSLMGAD", PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false)));
/*  980 */       stringBuffer.append(getValue("DSLMMVA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/*      */       
/*  982 */       entityItem2 = searchForAvailType(entityItem1, "Last Order");
/*  983 */       if (entityItem2 != null) {
/*      */         
/*  985 */         str36 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*  986 */         str37 = "O";
/*      */       } else {
/*      */         
/*  989 */         str36 = "2050-12-31";
/*  990 */         str37 = "N";
/*      */       } 
/*      */       
/*  993 */       str42 = PokUtils.getAttributeValue(entityItem1, "WTHDRWEFFCTVDATE", "", "");
/*      */       
/*  995 */       if (str42 != null) {
/*  996 */         if (str42.equals("")) {
/*  997 */           str35 = "2050-12-31";
/*      */         } else {
/*  999 */           str35 = str42;
/*      */         } 
/*      */       } else {
/* 1002 */         str35 = "2050-12-31";
/*      */       } 
/*      */       
/* 1005 */       stringBuffer.append(getValue("DSLMOPD", str36));
/* 1006 */       stringBuffer.append(getValue("DSLMWDN", str35));
/*      */       
/* 1008 */       entityItem2 = searchForAvailType(entityItem1, "End of Service");
/* 1009 */       if (entityItem2 != null) {
/* 1010 */         str33 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/* 1011 */         str34 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/*      */       } else {
/* 1013 */         str33 = "2050-12-31";
/* 1014 */         str34 = "2050-12-31";
/*      */       } 
/*      */       
/* 1017 */       stringBuffer.append(getValue("QSMEDMW", str33));
/*      */       
/* 1019 */       stringBuffer.append(getValue("ASLMMVP", "01.0"));
/* 1020 */       str2 = PokUtils.getAttributeValue(entityItem1, "ICRCATEGORY", "", "");
/* 1021 */       stringBuffer.append(getValue("CCUOICR", str2));
/* 1022 */       stringBuffer.append(getValue("CICIB", "N"));
/* 1023 */       stringBuffer.append(getValue("CICIC", "N"));
/* 1024 */       stringBuffer.append(getValue("CICRY", "N"));
/* 1025 */       stringBuffer.append(getValue("CIDCJ", "N"));
/* 1026 */       stringBuffer.append(getValue("CIDXF", PokUtils.getAttributeValue(entityItem1, "LICNSINTERCD", "", "")));
/*      */       
/* 1028 */       String str44 = "";
/* 1029 */       String str45 = "";
/* 1030 */       EntityItem entityItem3 = null;
/* 1031 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "MODELGEOMOD", "GEOMOD");
/* 1032 */       if (vector1.size() > 0) {
/* 1033 */         for (int i = 0; i < vector1.size(); i++) {
/* 1034 */           entityItem3 = vector1.elementAt(i);
/* 1035 */           str45 = PokUtils.getAttributeValue(entityItem3, "GENAREASELECTION", "", "");
/* 1036 */           if (str45.equals(paramString1)) {
/* 1037 */             str44 = PokUtils.getAttributeValue(entityItem3, "NOCHRGRENT", "", "");
/* 1038 */             str1 = PokUtils.getAttributeValue(entityItem3, "GRADUATEDCHARGE", "", "");
/* 1039 */             str3 = PokUtils.getAttributeValue(entityItem3, "PURCHONLY", "", "");
/* 1040 */             str4 = PokUtils.getAttributeValue(entityItem3, "PLNTOFMFR", "", "");
/* 1041 */             str5 = PokUtils.getAttributeValue(entityItem3, "INTEGRATEDMODEL", "", "");
/* 1042 */             str6 = PokUtils.getAttributeValue(entityItem3, "PERCALLCLS", "", "");
/* 1043 */             str8 = PokUtils.getAttributeValue(entityItem3, "EMEABRANDCD", "", "");
/* 1044 */             str7 = PokUtils.getAttributeValue(entityItem3, "ANNUALMAINT", "", "");
/* 1045 */             str31 = PokUtils.getAttributeValue(entityItem3, "METHODPROD", "", "");
/* 1046 */             str29 = PokUtils.getAttributeValue(entityItem3, "EDUCPURCHELIG", "", "");
/*      */             
/* 1048 */             i = vector1.size();
/*      */           } else {
/* 1050 */             entityItem3 = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1055 */       stringBuffer.append(getValue("CINCA", str44));
/*      */       
/* 1057 */       str27 = PokUtils.getAttributeValue(entityItem1, "PRCINDC", "", "");
/* 1058 */       if (str27.equals("Yes")) {
/* 1059 */         stringBuffer.append(getValue("CINCB", "N"));
/* 1060 */       } else if (str27.equals("No")) {
/* 1061 */         stringBuffer.append(getValue("CINCB", "Y"));
/*      */       } else {
/* 1063 */         stringBuffer.append(getValue("CINCB", "N"));
/*      */       } 
/*      */       
/* 1066 */       stringBuffer.append(getValue("CINCC", "N"));
/* 1067 */       stringBuffer.append(getValue("CINPM", PokUtils.getAttributeValue(entityItem1, "NETPRICEMES", "", "")));
/*      */       
/* 1069 */       stringBuffer.append(getValue("CISUP", "N"));
/*      */       
/* 1071 */       stringBuffer.append(getValue("CITEM", "N"));
/* 1072 */       String str46 = PokUtils.getAttributeValue(paramEntityItem1, "INDDEFNCATG", ",", "", false);
/* 1073 */       if (str46.length() >= 2) {
/* 1074 */         stringBuffer.append(getValue("CJLBIC1", str46.substring(0, 2)));
/*      */       } else {
/* 1076 */         stringBuffer.append(getValue("CJLBIC1", ""));
/*      */       } 
/* 1078 */       if (str46.length() >= 3) {
/* 1079 */         stringBuffer.append(getValue("CJLBIDS", str46.substring(2)));
/*      */       } else {
/* 1081 */         stringBuffer.append(getValue("CJLBIDS", ""));
/*      */       } 
/* 1083 */       stringBuffer.append(getValue("CJLBOEM", PokUtils.getAttributeValue(entityItem1, "SPECMODDESGN", "", "")));
/*      */       
/* 1085 */       stringBuffer.append(getValue("CJLBPOF", ""));
/*      */       
/* 1087 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem1, "MODELSGMTACRONYMA", "SGMNTACRNYM");
/* 1088 */       if (!vector2.isEmpty()) {
/* 1089 */         EntityItem entityItem = vector2.elementAt(0);
/* 1090 */         if (entityItem != null) {
/* 1091 */           stringBuffer.append(getValue("CJLBSAC", PokUtils.getAttributeValue(entityItem, "ACRNYM", "", "")));
/*      */         } else {
/* 1093 */           stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */         } 
/*      */       } else {
/* 1096 */         stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */       } 
/*      */       
/* 1099 */       stringBuffer.append(getValue("CLASSPT", "IHW"));
/*      */       
/* 1101 */       if (paramString2.equals("Last Order")) {
/* 1102 */         stringBuffer.append(getValue("CPDAA", "O"));
/*      */       } else {
/* 1104 */         stringBuffer.append(getValue("CPDAA", "N"));
/*      */       } 
/*      */       
/* 1107 */       stringBuffer.append(getValue("CSLMFCC", PokUtils.getAttributeValue(entityItem1, "FUNCCLS", "", "")));
/*      */       
/* 1109 */       if (paramString1.equals("Asia Pacific")) {
/* 1110 */         stringBuffer.append(getValue("CSLMGGC", str1));
/*      */       } else {
/* 1112 */         stringBuffer.append(getValue("CSLMGGC", " "));
/*      */       } 
/*      */       
/* 1115 */       String str47 = PokUtils.getAttributeValue(entityItem1, "PRODID", "", "");
/* 1116 */       if (str47.equals("0-CPU")) {
/* 1117 */         str30 = "0";
/* 1118 */       } else if (str47.equals("1-Unit Record Equipm.")) {
/* 1119 */         str30 = "1";
/* 1120 */       } else if (str47.equals("2-System Component")) {
/* 1121 */         str30 = "2";
/* 1122 */       } else if (str47.equals("3-Stand Alone Material")) {
/* 1123 */         str30 = "3";
/* 1124 */       } else if (str47.equals("4-System Control")) {
/* 1125 */         str30 = "4";
/* 1126 */       } else if (str47.equals("5-Program Product")) {
/* 1127 */         str30 = "5";
/* 1128 */       } else if (str47.equals("6-Special Program")) {
/* 1129 */         str30 = "6";
/* 1130 */       } else if (str47.equals("7-Control Unit")) {
/* 1131 */         str30 = "7";
/* 1132 */       } else if (str47.equals("8-Disk Packs")) {
/* 1133 */         str30 = "8";
/*      */       } else {
/* 1135 */         str30 = "";
/*      */       } 
/*      */       
/* 1138 */       stringBuffer.append(getValue("CSLMIDP", str30));
/* 1139 */       stringBuffer.append(getValue("CSLMLRP", "0"));
/* 1140 */       stringBuffer.append(getValue("CSLMSAS", "0"));
/* 1141 */       stringBuffer.append(getValue("CSLMSYT", PokUtils.getAttributeValue(entityItem1, "SYSTEMTYPE", "", "")));
/*      */       
/* 1143 */       EntityItem entityItem4 = null;
/* 1144 */       str40 = PokUtils.getAttributeValue(entityItem1, "WARRSVCCOVR", "", "");
/* 1145 */       if (str40 != null) {
/* 1146 */         if (str40.equals("No Warranty") || str40.equals("")) {
/* 1147 */           str39 = "Z";
/*      */         } else {
/* 1149 */           Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "MODELWARR", "WARR");
/* 1150 */           if (!vector3.isEmpty()) {
/* 1151 */             entityItem4 = vector3.elementAt(0);
/* 1152 */             if (entityItem4 != null) {
/* 1153 */               String str = PokUtils.getAttributeValue(entityItem4, "WARRID", "", "");
/* 1154 */               if (str.equals("WTY0000")) {
/* 1155 */                 if (vector3.size() > 1) {
/* 1156 */                   entityItem4 = vector3.elementAt(1);
/*      */                 } else {
/* 1158 */                   entityItem4 = null;
/*      */                 } 
/*      */               }
/*      */             } 
/*      */             
/* 1163 */             if (entityItem4 != null) {
/* 1164 */               str39 = PokUtils.getAttributeValue(entityItem4, "WARRCATG", "", "");
/*      */             } else {
/* 1166 */               str39 = "";
/*      */             } 
/*      */           } else {
/* 1169 */             str39 = "";
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1173 */         str39 = "Z";
/*      */       } 
/* 1175 */       stringBuffer.append(getValue("CSLMWCD", str39));
/*      */       
/* 1177 */       stringBuffer.append(getValue("FAGRMBE", str23));
/*      */       
/* 1179 */       if (str2.equals("1") || str2.equals("2")) {
/* 1180 */         stringBuffer.append(getValue("FCUOCNF", "N"));
/* 1181 */       } else if (str2.equals("3")) {
/* 1182 */         stringBuffer.append(getValue("FCUOCNF", "Y"));
/*      */       } else {
/* 1184 */         stringBuffer.append(getValue("FCUOCNF", "N"));
/*      */       } 
/*      */       
/* 1187 */       stringBuffer.append(getValue("FSLMCLS", "N"));
/*      */       
/* 1189 */       String str48 = PokUtils.getAttributeValue(entityItem1, "SYSIDUNIT", "", "");
/* 1190 */       if (str48.equals("SIU-CPU")) {
/* 1191 */         stringBuffer.append(getValue("FSLMCPU", "Y"));
/*      */       } else {
/* 1193 */         stringBuffer.append(getValue("FSLMCPU", "N"));
/*      */       } 
/*      */       
/* 1196 */       stringBuffer.append(getValue("FSLMIOP", str5));
/* 1197 */       stringBuffer.append(getValue("FSLMLGS", "N"));
/* 1198 */       stringBuffer.append(getValue("FSLMMLC", PokUtils.getAttributeValue(entityItem1, "MACHLVLCNTRL", "", "")));
/*      */       
/* 1200 */       if (paramString1.equals("Latin America") || paramString1
/* 1201 */         .equals("US Only") || paramString1
/* 1202 */         .equals("Canada and Caribbean North")) {
/* 1203 */         stringBuffer.append(getValue("FSLMPOP", "No"));
/* 1204 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1205 */         stringBuffer.append(getValue("FSLMPOP", "Yes"));
/* 1206 */       } else if (paramString1.equals("Asia Pacific")) {
/* 1207 */         if (str27.equals("Yes")) {
/* 1208 */           stringBuffer.append(getValue("FSLMPOP", "Y"));
/* 1209 */         } else if (str27.equals("No")) {
/* 1210 */           stringBuffer.append(getValue("FSLMPOP", "N"));
/*      */         } else {
/* 1212 */           stringBuffer.append(getValue("FSLMPOP", " "));
/*      */         } 
/*      */       } else {
/* 1215 */         stringBuffer.append(getValue("FSLMPOP", " "));
/*      */       } 
/*      */       
/* 1218 */       stringBuffer.append(getValue("FSLMVDE", PokUtils.getAttributeValue(entityItem1, "VOLUMEDISCOUNTELIG", "", "")));
/* 1219 */       stringBuffer.append(getValue("FSLMVTS", "N"));
/*      */       
/* 1221 */       ArrayList<String> arrayList = new ArrayList();
/* 1222 */       if (entityItem4 != null) {
/* 1223 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("WARRTYPE");
/* 1224 */         if (eANFlagAttribute != null) {
/* 1225 */           if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1226 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1227 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250")) {
/* 1228 */               str11 = "Y";
/*      */             } else {
/* 1230 */               str11 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1234 */           if (paramString1.equals("Latin America")) {
/* 1235 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1236 */               .isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute.isSelected("W0580")) {
/* 1237 */               str11 = "Y";
/*      */             } else {
/* 1239 */               str11 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1243 */           if (paramString1.equals("Asia Pacific")) {
/* 1244 */             if (eANFlagAttribute.isSelected("W0550") || eANFlagAttribute
/* 1245 */               .isSelected("W0390") || eANFlagAttribute
/* 1246 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250") || eANFlagAttribute
/* 1247 */               .isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1248 */               .isSelected("W0590")) {
/* 1249 */               str11 = "Y";
/*      */             } else {
/* 1251 */               str11 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1255 */           if (paramString1.equals("Canada and Caribbean North") || paramString1
/* 1256 */             .equals("US Only")) {
/* 1257 */             str11 = "N";
/*      */           }
/*      */         } 
/*      */       } else {
/* 1261 */         str11 = "N";
/*      */       } 
/* 1263 */       stringBuffer.append(getValue("FSLM2CF", str11));
/*      */ 
/*      */       
/* 1266 */       stringBuffer.append(getValue("ICESPCC", str6));
/* 1267 */       stringBuffer.append(getValue("IDORIG", "IBM"));
/* 1268 */       stringBuffer.append(getValue("IOLCPLM", str4));
/*      */       
/* 1270 */       str24 = "000";
/* 1271 */       str25 = "000";
/* 1272 */       str26 = "000";
/*      */       
/* 1274 */       if (entityItem3 != null && (
/* 1275 */         paramString1.equals("Latin America") || paramString1.equals("Asia Pacific") || paramString1
/* 1276 */         .equals("Canada and Caribbean North"))) {
/* 1277 */         str24 = getNumValue("PCUAHEA", PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMHGHSCH", ",", "", false));
/* 1278 */         str26 = getNumValue("PCUAUEA", PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMUNVRSTY", ",", "", false));
/* 1279 */         str25 = getNumValue("PCUASEA", PokUtils.getAttributeValue(entityItem3, "EDUCALLOWMSECONDRYSCH", ",", "", false));
/*      */       } 
/*      */ 
/*      */       
/* 1283 */       stringBuffer.append(getValue("PCUAHEA", str24));
/* 1284 */       stringBuffer.append(getValue("PCUASEA", str25));
/* 1285 */       stringBuffer.append(getValue("PCUAUEA", str26));
/*      */       
/* 1287 */       String str49 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/* 1288 */       if (paramString1.equals("Latin America")) {
/* 1289 */         if (str49.equals("CIF")) {
/* 1290 */           str12 = "01";
/* 1291 */         } else if (str49.equals("CE") || str49.equals("N/A") || str49.equals("Does not apply")) {
/* 1292 */           str12 = "";
/*      */         } 
/* 1294 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1295 */         if (str49.equals("CIF")) {
/* 1296 */           str12 = "01";
/* 1297 */         } else if (str49.equals("CE") || str49.equals("N/A") || str49.equals("Does not apply")) {
/* 1298 */           str12 = "";
/*      */         } 
/* 1300 */       } else if (paramString1.equals("Asia Pacific")) {
/* 1301 */         if (str49.equals("CIF")) {
/* 1302 */           str12 = "10";
/* 1303 */         } else if (str49.equals("CE") || str49.equals("N/A") || str49.equals("Does not apply")) {
/* 1304 */           str12 = "";
/*      */         } 
/* 1306 */       } else if (paramString1.equals("US Only")) {
/* 1307 */         if (str49.equals("CIF")) {
/* 1308 */           str12 = "01";
/* 1309 */         } else if (str49.equals("CE") || str49.equals("N/A") || str49.equals("Does not apply")) {
/* 1310 */           str12 = "00";
/*      */         } 
/* 1312 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/* 1313 */         if (str49.equals("CIF")) {
/* 1314 */           str12 = "01";
/* 1315 */         } else if (str49.equals("CE") || str49.equals("N/A") || str49.equals("Does not apply")) {
/* 1316 */           str12 = "";
/*      */         } 
/*      */       } 
/* 1319 */       stringBuffer.append(getValue("QSLMCSU", str12));
/*      */       
/* 1321 */       stringBuffer.append(getValue("QSMXANN", str7));
/* 1322 */       stringBuffer.append(getValue("QSMXESA", "N"));
/* 1323 */       stringBuffer.append(getValue("QSMXSSA", "N"));
/*      */       
/* 1325 */       if (str48.equals("SIU-CPU") || str48.equals("U-System Unit")) {
/* 1326 */         stringBuffer.append(getValue("SYSDES", PokUtils.getAttributeValue(entityItem1, "MODMKTGDESC", "", "")));
/*      */       } else {
/* 1328 */         stringBuffer.append(getValue("SYSDES", "   "));
/*      */       } 
/*      */       
/* 1331 */       String str50 = PokUtils.getAttributeValue(entityItem1, "INVNAME", "", "");
/* 1332 */       stringBuffer.append(getValue("TSLMDES", removeSpecialChars(str50)));
/* 1333 */       stringBuffer.append(getValue("TSLTDES", " "));
/* 1334 */       stringBuffer.append(getValue("TIMSTMP", " "));
/* 1335 */       stringBuffer.append(getValue("USERID", " "));
/* 1336 */       stringBuffer.append(getValue("FBRAND", str8));
/*      */       
/* 1338 */       if (str31.equals("BTP")) {
/* 1339 */         str32 = "Y";
/* 1340 */       } else if (str31.equals("BTO")) {
/* 1341 */         str32 = "N";
/*      */       } else {
/* 1343 */         str32 = "";
/*      */       } 
/*      */       
/* 1346 */       stringBuffer.append(getValue("FSLMHVP", str32));
/*      */       
/* 1348 */       if (paramString1.equals("US Only")) {
/* 1349 */         str38 = "Y";
/* 1350 */       } else if (paramString1.equals("Latin America") || paramString1.equals("Europe/Middle East/Africa") || paramString1
/* 1351 */         .equals("Asia Pacific") || paramString1.equals("Canada and Caribbean North")) {
/* 1352 */         if (str31.equals("BTO")) {
/* 1353 */           str38 = "Y";
/* 1354 */         } else if (str31.equals("BTP")) {
/* 1355 */           str38 = "N";
/*      */         } else {
/* 1357 */           str38 = " ";
/*      */         } 
/*      */       } 
/*      */       
/* 1361 */       stringBuffer.append(getValue("FSLMCVP", str38));
/*      */       
/* 1363 */       stringBuffer.append(getValue("FSLMMES", "N"));
/*      */       
/* 1365 */       arrayList = new ArrayList();
/* 1366 */       str15 = "";
/* 1367 */       str16 = "";
/* 1368 */       str17 = "";
/* 1369 */       str18 = "";
/* 1370 */       str19 = "";
/* 1371 */       str20 = "";
/* 1372 */       str21 = "";
/* 1373 */       str22 = "";
/*      */       
/* 1375 */       if (entityItem4 != null) {
/* 1376 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("WARRTYPE");
/* 1377 */         if (eANFlagAttribute != null) {
/* 1378 */           if (eANFlagAttribute.isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute.isSelected("W0580")) {
/* 1379 */             arrayList.add("IOR");
/*      */           }
/* 1381 */           if (eANFlagAttribute.isSelected("W0550")) {
/* 1382 */             arrayList.add("IOE");
/*      */           }
/* 1384 */           if (eANFlagAttribute.isSelected("W0390")) {
/* 1385 */             arrayList.add("COE");
/*      */           }
/* 1387 */           if (eANFlagAttribute.isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250")) {
/* 1388 */             arrayList.add("CCE");
/*      */           }
/* 1390 */           if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330")) {
/* 1391 */             arrayList.add("CCR");
/*      */           }
/* 1393 */           if (eANFlagAttribute.isSelected("W0590")) {
/* 1394 */             arrayList.add("IOS");
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
/* 1417 */           for (byte b1 = 0; b1 < arrayList.size(); b1++) {
/* 1418 */             if (b1 == 0) {
/* 1419 */               str15 = arrayList.get(b1);
/* 1420 */             } else if (b1 == 1) {
/* 1421 */               str16 = arrayList.get(b1);
/* 1422 */             } else if (b1 == 2) {
/* 1423 */               str17 = arrayList.get(b1);
/* 1424 */             } else if (b1 == 3) {
/* 1425 */               str18 = arrayList.get(b1);
/* 1426 */             } else if (b1 == 4) {
/* 1427 */               str19 = arrayList.get(b1);
/* 1428 */             } else if (b1 == 5) {
/* 1429 */               str20 = arrayList.get(b1);
/* 1430 */             } else if (b1 == 6) {
/* 1431 */               str21 = arrayList.get(b1);
/* 1432 */             } else if (b1 == 7) {
/* 1433 */               str22 = arrayList.get(b1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1439 */       stringBuffer.append(getValue("CSLMTM1", str15));
/* 1440 */       stringBuffer.append(getValue("CSLMTM2", str16));
/* 1441 */       stringBuffer.append(getValue("CSLMTM3", str17));
/* 1442 */       stringBuffer.append(getValue("CSLMTM4", str18));
/* 1443 */       stringBuffer.append(getValue("CSLMTM5", str19));
/* 1444 */       stringBuffer.append(getValue("CSLMTM6", str20));
/* 1445 */       stringBuffer.append(getValue("CSLMTM7", str21));
/* 1446 */       stringBuffer.append(getValue("CSLMTM8", str22));
/* 1447 */       stringBuffer.append(getValue("FSAPRES", "N"));
/*      */       
/* 1449 */       if (paramString1.equals("US Only")) {
/* 1450 */         String str = PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false);
/* 1451 */         if (str.equals("Yes")) {
/* 1452 */           stringBuffer.append(getValue("CUSAPMS", "Y"));
/* 1453 */         } else if (str.equals("No")) {
/* 1454 */           stringBuffer.append(getValue("CUSAPMS", "X"));
/*      */         } else {
/* 1456 */           stringBuffer.append(getValue("CUSAPMS", ""));
/*      */         } 
/*      */       } else {
/* 1459 */         stringBuffer.append(getValue("CUSAPMS", ""));
/*      */       } 
/*      */       
/* 1462 */       stringBuffer.append(getValue("DUSALRW", str34));
/*      */       
/* 1464 */       stringBuffer.append(getValue("DUSAMDW", "2050-12-31"));
/* 1465 */       stringBuffer.append(getValue("DUSAWUW", "2050-12-31"));
/* 1466 */       if (paramString1.equals("US Only")) {
/* 1467 */         stringBuffer.append(getValue("FSLMCBL", "N"));
/*      */       } else {
/* 1469 */         stringBuffer.append(getValue("FSLMCBL", " "));
/*      */       } 
/* 1471 */       stringBuffer.append(getValue("FSLMMRR", "N"));
/* 1472 */       str13 = "";
/*      */       
/* 1474 */       if (paramString1.equals("US Only")) {
/* 1475 */         str13 = "N";
/*      */         
/* 1477 */         String str = PokUtils.getAttributeValue(paramEntityItem2, "ORDERSYSNAME", ",", "", false);
/* 1478 */         if (str.equals("AAS")) {
/* 1479 */           str13 = "Y";
/*      */         }
/*      */       } 
/* 1482 */       stringBuffer.append(getValue("FUSAAAS", str13));
/* 1483 */       stringBuffer.append(getValue("FUSAADM", "N"));
/*      */       
/* 1485 */       if (paramString1.equals("US Only")) {
/* 1486 */         stringBuffer.append(getValue("FUSAEDE", str29));
/*      */       } else {
/* 1488 */         stringBuffer.append(getValue("FUSAEDE", " "));
/*      */       } 
/*      */       
/* 1491 */       if (paramString1.equals("US Only")) {
/* 1492 */         str41 = PokUtils.getAttributeValue(entityItem1, "IBMCREDIT", ",", "", false);
/* 1493 */         addDebug("*****mlm IBMCREDIT=" + str41);
/* 1494 */         if (str41 != null) {
/* 1495 */           if (str41.equals("Yes")) {
/* 1496 */             str14 = "Y";
/* 1497 */           } else if (str41.equals("No")) {
/* 1498 */             str14 = "N";
/*      */           } 
/*      */         }
/* 1501 */         stringBuffer.append(getValue("FUSAICC", str14));
/*      */       } else {
/* 1503 */         stringBuffer.append(getValue("FUSAICC", " "));
/*      */       } 
/*      */       
/* 1506 */       if (paramString1.equals("US Only")) {
/* 1507 */         stringBuffer.append(getValue("FUSALEP", PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false)));
/*      */       } else {
/* 1509 */         stringBuffer.append(getValue("FUSALEP", " "));
/*      */       } 
/*      */       
/* 1512 */       stringBuffer.append(getValue("FUSAMRS", "N"));
/* 1513 */       stringBuffer.append(getValue("FUSAVLM", "N"));
/* 1514 */       stringBuffer.append(getValue("FUSAXMO", "N"));
/* 1515 */       stringBuffer.append(getValue("QUSAPOP", "00.0"));
/* 1516 */       stringBuffer.append(getValue("DSLMEOD", "1950-01-01"));
/* 1517 */       stringBuffer.append(getValue("FSLMRFM", " "));
/*      */       
/* 1519 */       stringBuffer.append(NEWLINE);
/* 1520 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 1521 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String removeSpecialChars(String paramString) {
/* 1528 */     String str = "";
/* 1529 */     str = paramString.replaceAll("#", "");
/* 1530 */     str = str.replaceAll("$", "");
/* 1531 */     str = str.replaceAll("%", "");
/* 1532 */     str = str.replaceAll("@", "");
/* 1533 */     str = str.replaceAll("/", "");
/* 1534 */     str = str.replaceAll("'", "");
/* 1535 */     str = str.replaceAll("\"", "");
/* 1536 */     str = str.replaceAll("é¥ï¿½", "");
/*      */     
/* 1538 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem searchForAvailType(EntityItem paramEntityItem, String paramString) {
/* 1544 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/* 1547 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/*      */     
/* 1549 */     addDebug("*****mlm searchforavail AVAIL " + vector);
/*      */     
/* 1551 */     for (byte b = 0; b < vector.size(); b++) {
/* 1552 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/* 1554 */       String str = PokUtils.getAttributeValue(entityItem1, "AVAILTYPE", ",", "", false);
/* 1555 */       addDebug("*****mlm searchforavail model = " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "avail entity type = " + entityItem1.getEntityType() + " avail type = " + str);
/* 1556 */       if (paramString.equals(str)) {
/* 1557 */         entityItem = entityItem1;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1562 */     return entityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   private String validateProdstructs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1567 */     String str1 = "";
/* 1568 */     String str2 = null;
/* 1569 */     Date date = null;
/*      */     
/* 1571 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getT006FeatureVEName());
/*      */     
/* 1573 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, paramEntityItem.getEntityType(), paramEntityItem.getEntityID()) });
/*      */     
/* 1575 */     EntityGroup entityGroup = entityList.getEntityGroup("PRODSTRUCT");
/* 1576 */     addDebug("*****mlm feature.id=" + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + " prodstructcount=" + entityGroup.getEntityItemCount());
/*      */     
/* 1578 */     for (int i = 0; i < entityGroup.getEntityItemCount(); i++) {
/*      */       
/* 1580 */       EntityItem entityItem = entityGroup.getEntityItem(i);
/* 1581 */       addDebug("*****mlm prodstruct=" + entityItem.getEntityType() + entityItem.getEntityID());
/*      */       
/* 1583 */       String str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", ",", "", false);
/* 1584 */       addDebug("*****mlm oldestdate=" + str2);
/* 1585 */       addDebug("*****mlm psWdDate=" + str);
/* 1586 */       if (!str.equals("")) {
/* 1587 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*      */         try {
/* 1589 */           date = simpleDateFormat.parse(str);
/* 1590 */           if (str2 == null || date.after((Date)str2)) {
/* 1591 */             addDebug("*****mlm setting odlestdate to psWdDate");
/* 1592 */             Date date1 = date;
/* 1593 */             str1 = str;
/*      */           } 
/* 1595 */         } catch (ParseException parseException) {
/* 1596 */           addDebug(parseException.toString());
/* 1597 */           addDebug("*****mlm error: ParseException, setting date to 2050-12-31 - end");
/* 1598 */           str1 = "2050-12-31";
/* 1599 */           i = entityGroup.getEntityItemCount();
/*      */           break;
/*      */         } 
/*      */       } else {
/* 1603 */         addDebug("*****mlm psWdDate is blank, set date to 2050-12-31 - end");
/* 1604 */         str1 = "2050-12-31";
/* 1605 */         i = entityGroup.getEntityItemCount();
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1610 */     return str1;
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
/*      */   private void createT006Feature(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 1623 */     String str1 = "";
/* 1624 */     boolean bool = false;
/*      */     
/* 1626 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "AVAILTYPE", "", "");
/* 1627 */     this.m_elist = getEntityList(getT006ProdstructVEName());
/* 1628 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 1629 */     int i = entityGroup.getEntityItemCount();
/* 1630 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1631 */       EANFlagAttribute eANFlagAttribute = null;
/* 1632 */       String str = "";
/*      */       
/* 1634 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 1636 */       str = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/* 1637 */       str1 = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/* 1638 */       if (str1.equals("EPIC")) {
/* 1639 */         bool = true;
/*      */       }
/*      */ 
/*      */       
/* 1643 */       if (str.equals("Planned Availability") || str
/* 1644 */         .equals("End of Service") || str
/* 1645 */         .equals("Last Order")) {
/* 1646 */         eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 1647 */         if (eANFlagAttribute != null) {
/* 1648 */           if (eANFlagAttribute.isSelected("6199")) {
/* 1649 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", str, bool);
/*      */           }
/* 1651 */           if (eANFlagAttribute.isSelected("6200")) {
/* 1652 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", str, bool);
/*      */           }
/* 1654 */           if (eANFlagAttribute.isSelected("6198")) {
/* 1655 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", str, bool);
/*      */           }
/* 1657 */           if (eANFlagAttribute.isSelected("6204")) {
/* 1658 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", str, bool);
/*      */           }
/* 1660 */           if (eANFlagAttribute.isSelected("6221")) {
/* 1661 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", str, bool);
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
/*      */   
/*      */   private void createT006FeatureRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString1, String paramString2, boolean paramBoolean) throws IOException, SQLException, MiddlewareException {
/* 1714 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "OOFAVAIL", "PRODSTRUCT");
/*      */     
/* 1716 */     for (byte b = 0; b < vector.size(); b++) {
/* 1717 */       StringBuffer stringBuffer = new StringBuffer();
/* 1718 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/* 1720 */       ExtractActionItem extractActionItem1 = new ExtractActionItem(null, this.m_db, this.m_prof, getT006FeatureVEName());
/*      */       
/* 1722 */       EntityList entityList1 = this.m_db.getEntityList(this.m_prof, extractActionItem1, new EntityItem[] { new EntityItem(null, this.m_prof, entityItem1.getEntityType(), entityItem1.getEntityID()) });
/*      */       
/* 1724 */       addDebug("EntityList for " + this.m_prof.getValOn() + " extract QSMFULL2 contains the following entities: \n" + 
/* 1725 */           PokUtils.outputList(entityList1));
/*      */       
/* 1727 */       EntityGroup entityGroup1 = entityList1.getEntityGroup("FEATURE");
/* 1728 */       EntityGroup entityGroup2 = entityList1.getEntityGroup("MODEL");
/* 1729 */       EntityItem entityItem2 = entityGroup1.getEntityItem(0);
/* 1730 */       EntityItem entityItem3 = entityGroup2.getEntityItem(0);
/* 1731 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "PRODSTSTDMT", "STDMAINT");
/*      */       
/* 1733 */       ExtractActionItem extractActionItem2 = new ExtractActionItem(null, this.m_db, this.m_prof, getT006ModelLinksVEName());
/*      */       
/* 1735 */       EntityList entityList2 = this.m_db.getEntityList(this.m_prof, extractActionItem2, new EntityItem[] { new EntityItem(null, this.m_prof, entityItem3.getEntityType(), entityItem3.getEntityID()) });
/*      */       
/* 1737 */       addDebug("EntityList for " + this.m_prof.getValOn() + " extract QSMFULL5 contains the following entities: \n" + 
/* 1738 */           PokUtils.outputList(entityList2));
/*      */       
/* 1740 */       EntityGroup entityGroup3 = entityList2.getEntityGroup("SGMNTACRNYM");
/* 1741 */       EntityGroup entityGroup4 = entityList2.getEntityGroup("GEOMOD");
/* 1742 */       EntityGroup entityGroup5 = entityList2.getEntityGroup("WARR");
/* 1743 */       EntityGroup entityGroup6 = entityList2.getEntityGroup("STDMAINT");
/*      */       
/* 1745 */       stringBuffer = new StringBuffer();
/* 1746 */       String str1 = "";
/* 1747 */       String str2 = "";
/* 1748 */       String str3 = "";
/* 1749 */       String str4 = "";
/* 1750 */       String str5 = "";
/* 1751 */       String str6 = "";
/* 1752 */       String str7 = "";
/* 1753 */       String str8 = "";
/* 1754 */       String str9 = "";
/* 1755 */       String str10 = "";
/* 1756 */       String str11 = "";
/* 1757 */       String str12 = "";
/* 1758 */       String str13 = "";
/* 1759 */       String str14 = "";
/* 1760 */       String str15 = "";
/* 1761 */       String str16 = "";
/* 1762 */       String str17 = "";
/* 1763 */       String str18 = "";
/* 1764 */       String str19 = "";
/* 1765 */       String str20 = "";
/* 1766 */       String str21 = "";
/* 1767 */       String str22 = "";
/* 1768 */       String str23 = "";
/* 1769 */       String str24 = "";
/* 1770 */       String str25 = "";
/* 1771 */       String str26 = "";
/* 1772 */       String str27 = "";
/* 1773 */       String str28 = "";
/* 1774 */       String str29 = "";
/* 1775 */       String str30 = "";
/* 1776 */       String str31 = "";
/* 1777 */       String str32 = "";
/* 1778 */       String str33 = "";
/* 1779 */       String str34 = "";
/* 1780 */       String str35 = "";
/*      */       
/* 1782 */       stringBuffer.append(getValue("IFTYPE", "F"));
/*      */       
/* 1784 */       if (paramString1.equals("Latin America")) {
/* 1785 */         str1 = "601";
/* 1786 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/* 1787 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 1788 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1789 */         str1 = "999";
/* 1790 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/* 1791 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 1792 */       } else if (paramString1.equals("Asia Pacific")) {
/* 1793 */         str1 = "872";
/* 1794 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/* 1795 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 1796 */       } else if (paramString1.equals("US Only")) {
/* 1797 */         str1 = "897";
/* 1798 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 1799 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 1800 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/* 1801 */         str1 = "649";
/* 1802 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/* 1803 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */       } 
/* 1805 */       stringBuffer.append(getValue("IOPUCTY", str1));
/* 1806 */       stringBuffer.append(getValue("ISLMPAL", str7));
/* 1807 */       stringBuffer.append(getValue("ISLMRFA", str2));
/* 1808 */       String str36 = PokUtils.getAttributeValue(entityItem3, "MACHTYPEATR", ",", "", false);
/* 1809 */       str36 = str36 + PokUtils.getAttributeValue(entityItem2, "FEATURECODE", ",", "", false);
/* 1810 */       stringBuffer.append(getValue("ISLMPRN", str36));
/*      */       
/* 1812 */       str4 = PokUtils.getAttributeValue(entityItem2, "FCTYPE", ",", "", false);
/* 1813 */       str3 = "MF";
/* 1814 */       if (str4.equals("RPQ-RLISTED") || str4
/* 1815 */         .equals("RPQ-ILISTED") || str4
/* 1816 */         .equals("RPQ-PLISTED")) {
/* 1817 */         str3 = "MQ";
/*      */       }
/*      */       
/* 1820 */       stringBuffer.append(getValue("CSLMPCI", str3));
/* 1821 */       stringBuffer.append(getValue("IPRTNUM", ""));
/* 1822 */       stringBuffer.append(getValue("FPUNINC", "2"));
/* 1823 */       stringBuffer.append(getValue("CAOAV", ""));
/* 1824 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/* 1825 */       stringBuffer.append(getValue("DSLMCPO", ""));
/*      */       
/* 1827 */       stringBuffer.append(getValue("DSLMGAD", PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false)));
/*      */       
/* 1829 */       String str37 = PokUtils.getAttributeValue(entityItem1, "ORDERCODE", ",", "", false);
/* 1830 */       EntityItem entityItem4 = null;
/*      */       
/* 1832 */       if (str37.equals("Both") || str37.equals("MES")) {
/* 1833 */         Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/* 1834 */         for (int i = 0; i < vector3.size(); i++) {
/* 1835 */           EANFlagAttribute eANFlagAttribute = null;
/* 1836 */           entityItem4 = vector3.elementAt(i);
/* 1837 */           str32 = PokUtils.getAttributeValue(entityItem4, "AVAILTYPE", ",", "", false);
/* 1838 */           eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("QSMGEO");
/* 1839 */           if (isQSMGeoSelected(paramString1, eANFlagAttribute) && str32.equals("MES Planned Availability")) {
/* 1840 */             str33 = PokUtils.getAttributeValue(entityItem4, "EFFECTIVEDATE", ",", "", false);
/* 1841 */             i = vector3.size();
/*      */           } 
/*      */         } 
/* 1844 */         if (str33.equals("")) {
/* 1845 */           str33 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/*      */         }
/* 1847 */       } else if (str37.equals("Initial")) {
/* 1848 */         str33 = "2050-12-31";
/*      */       } 
/*      */       
/* 1851 */       if (str33.equals("")) {
/* 1852 */         str33 = "2050-12-31";
/*      */       }
/*      */       
/* 1855 */       stringBuffer.append(getValue("DSLMMES", str33));
/*      */       
/* 1857 */       stringBuffer.append(getValue("QSMEDMW", "2050-12-31"));
/* 1858 */       stringBuffer.append(getValue("DSLMMVA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/*      */       
/* 1860 */       str5 = validateProdstructs(entityItem2);
/* 1861 */       stringBuffer.append(getValue("DSLMWDN", str5));
/*      */       
/* 1863 */       str23 = PokUtils.getAttributeValue(entityItem2, "PRICEDFEATURE", ",", "", false);
/*      */       
/* 1865 */       if (str4.equals("Primary") && str23.equals("No")) {
/* 1866 */         str22 = "S";
/*      */       }
/*      */       
/* 1869 */       if (paramString1.equals("Asia Pacific")) {
/* 1870 */         if (str23.equals("No")) {
/* 1871 */           str21 = "0.00";
/* 1872 */         } else if (str23.equals("Yes")) {
/* 1873 */           str21 = "1.00";
/*      */         } 
/*      */       } else {
/* 1876 */         str21 = "1.00";
/*      */       } 
/*      */       
/* 1879 */       stringBuffer.append(getValue("ASLMMVP", str21));
/*      */       
/* 1881 */       stringBuffer.append(getValue("CICRY", "N"));
/* 1882 */       stringBuffer.append(getValue("CIDCJ", "N"));
/* 1883 */       stringBuffer.append(getValue("CIDXC", "N"));
/*      */       
/* 1885 */       if (paramString1.equals("US Only")) {
/* 1886 */         stringBuffer.append(getValue("CINCA", "N"));
/*      */       } else {
/* 1888 */         stringBuffer.append(getValue("CINCA", "Y"));
/*      */       } 
/*      */       
/* 1891 */       String str38 = "";
/* 1892 */       str28 = PokUtils.getAttributeValue(entityItem2, "PRICEDFEATURE", "", "");
/* 1893 */       if (paramString1.equals("US Only")) {
/* 1894 */         str38 = "N";
/*      */       }
/* 1896 */       else if (str28.equals("Yes")) {
/* 1897 */         str38 = "N";
/* 1898 */       } else if (str28.equals("No")) {
/* 1899 */         str38 = "Y";
/*      */       } else {
/* 1901 */         str38 = "N";
/*      */       } 
/*      */ 
/*      */       
/* 1905 */       stringBuffer.append(getValue("CINCB", str38));
/*      */       
/* 1907 */       EntityItem entityItem5 = null;
/* 1908 */       if (!vector1.isEmpty()) {
/* 1909 */         entityItem5 = vector1.elementAt(0);
/* 1910 */         if (entityItem5 != null) {
/* 1911 */           str30 = PokUtils.getAttributeValue(entityItem5, "MAINTELIG", "", "");
/*      */         }
/* 1913 */         else if (entityGroup6 != null && entityGroup6.hasData()) {
/* 1914 */           entityItem5 = entityGroup6.getEntityItem(0);
/*      */         }
/*      */       
/*      */       }
/* 1918 */       else if (entityGroup6 != null && entityGroup6.hasData()) {
/* 1919 */         entityItem5 = entityGroup6.getEntityItem(0);
/*      */       } 
/*      */ 
/*      */       
/* 1923 */       if (entityItem5 != null) {
/* 1924 */         str30 = PokUtils.getAttributeValue(entityItem5, "MAINTELIG", "", "");
/*      */       }
/*      */       
/* 1927 */       if (paramString1.equals("Asia Pacific")) {
/* 1928 */         str31 = "Y";
/* 1929 */       } else if (paramString1.equals("US Only") || paramString1.equals("Canada and Caribbean North")) {
/* 1930 */         str31 = "N";
/* 1931 */       } else if (paramString1.equals("Europe/Middle East/Africa") || paramString1.equals("Latin America")) {
/* 1932 */         if (str30.equals("Yes")) {
/* 1933 */           str31 = "N";
/* 1934 */         } else if (str30.equals("No")) {
/* 1935 */           str31 = "Y";
/*      */         } 
/*      */       } 
/*      */       
/* 1939 */       stringBuffer.append(getValue("CINCC", str31));
/*      */ 
/*      */       
/* 1942 */       stringBuffer.append(getValue("CINPM", "N"));
/* 1943 */       stringBuffer.append(getValue("CITEM", "N"));
/* 1944 */       stringBuffer.append(getValue("CISUP", "N"));
/* 1945 */       if (entityGroup3 != null && entityGroup3.hasData()) {
/* 1946 */         EntityItem entityItem = entityGroup3.getEntityItem(0);
/* 1947 */         stringBuffer.append(getValue("CJLBSAC", PokUtils.getAttributeValue(entityItem, "ACRNYM", "", "")));
/*      */       } else {
/* 1949 */         stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */       } 
/* 1951 */       stringBuffer.append(getValue("CLASSPT", "IHW"));
/*      */       
/* 1953 */       str24 = "";
/*      */       
/* 1955 */       if (paramString1.equals("Europe/Middle East/Africa") || paramString1.equals("Latin America")) {
/* 1956 */         if (str22.equals("S")) {
/* 1957 */           str24 = "CM";
/*      */         }
/* 1959 */       } else if (paramString1.equals("Asia Pacific")) {
/* 1960 */         if (str22.equals("S")) {
/* 1961 */           str24 = "CM";
/*      */         }
/* 1963 */       } else if (paramString1.equals("US Only")) {
/* 1964 */         str24 = "NF";
/* 1965 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/* 1966 */         str24 = "";
/*      */       } 
/*      */       
/* 1969 */       stringBuffer.append(getValue("CSLMFTY", str24));
/* 1970 */       stringBuffer.append(getValue("CVOAT", ""));
/*      */ 
/*      */ 
/*      */       
/* 1974 */       if (paramString1.equals("Canada and Caribbean North")) {
/* 1975 */         str6 = "Y";
/* 1976 */       } else if (paramString1.equals("Asia Pacific") || paramString1.equals("US Only")) {
/* 1977 */         str6 = "N";
/* 1978 */       } else if (paramString1.equals("Europe/Middle East/Africa") || paramString1.equals("Latin America")) {
/* 1979 */         str6 = str30;
/*      */       } 
/*      */       
/* 1982 */       stringBuffer.append(getValue("FAGRMBE", str6));
/*      */       
/* 1984 */       String str39 = "";
/* 1985 */       EntityItem entityItem6 = null;
/* 1986 */       if (entityGroup4 != null && entityGroup4.hasData()) {
/* 1987 */         for (int i = 0; i < entityGroup4.getEntityItemCount(); i++) {
/* 1988 */           entityItem6 = entityGroup4.getEntityItem(i);
/* 1989 */           str39 = PokUtils.getAttributeValue(entityItem6, "GENAREASELECTION", "", "");
/* 1990 */           if (str39.equals(paramString1)) {
/* 1991 */             str20 = PokUtils.getAttributeValue(entityItem6, "PURCHONLY", "", "");
/* 1992 */             str29 = PokUtils.getAttributeValue(entityItem6, "EDUCPURCHELIG", "", "");
/* 1993 */             i = entityGroup4.getEntityItemCount();
/*      */           } else {
/* 1995 */             entityItem6 = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 2000 */       if (paramString1.equals("Latin America") || paramString1.equals("Europe/Middle East/Africa") || paramString1
/* 2001 */         .equals("Asia Pacific") || paramString1.equals("Canada and Caribbean North")) {
/* 2002 */         if (str37.equals("Initial")) {
/* 2003 */           str26 = "Y";
/*      */         } else {
/* 2005 */           str26 = "N";
/*      */         } 
/* 2007 */       } else if (paramString1.equals("US Only")) {
/* 2008 */         str26 = "N";
/*      */       } 
/* 2010 */       stringBuffer.append(getValue("FSLMPIO", str26));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2025 */       if (paramString1.equals("Latin America") || paramString1
/* 2026 */         .equals("US Only") || paramString1
/* 2027 */         .equals("Canada and Caribbean North") || paramString1
/* 2028 */         .equals("Asia Pacific")) {
/* 2029 */         stringBuffer.append(getValue("FSLMPOP", "No"));
/* 2030 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 2031 */         stringBuffer.append(getValue("FSLMPOP", "Yes"));
/*      */       } else {
/* 2033 */         stringBuffer.append(getValue("FSLMPOP", str20));
/*      */       } 
/*      */       
/* 2036 */       stringBuffer.append(getValue("FSLMSTK", "N"));
/*      */       
/* 2038 */       String str40 = "";
/*      */       
/* 2040 */       ArrayList<String> arrayList = new ArrayList();
/*      */       
/* 2042 */       EntityItem entityItem7 = null;
/* 2043 */       Vector<EntityItem> vector2 = null;
/* 2044 */       String str41 = "";
/*      */       
/* 2046 */       vector2 = PokUtils.getAllLinkedEntities(entityItem1, "PRODSTRUCTWARR", "WARR");
/*      */       
/* 2048 */       if (!vector2.isEmpty()) {
/* 2049 */         entityItem7 = vector2.elementAt(0);
/* 2050 */         if (entityItem7 == null) {
/* 2051 */           if (entityGroup5 != null && entityGroup5.hasData()) {
/* 2052 */             entityItem7 = entityGroup5.getEntityItem(0);
/* 2053 */             str41 = PokUtils.getAttributeValue(entityItem7, "WARRID", "", "");
/* 2054 */             if (str41.equals("WTY0000")) {
/* 2055 */               if (entityGroup5.getEntityItemCount() > 1) {
/* 2056 */                 entityItem7 = entityGroup5.getEntityItem(1);
/*      */               } else {
/* 2058 */                 entityItem7 = null;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } else {
/* 2063 */           str41 = PokUtils.getAttributeValue(entityItem7, "WARRID", "", "");
/* 2064 */           if (str41.equals("WTY0000")) {
/* 2065 */             if (vector2.size() > 1) {
/* 2066 */               entityItem7 = vector2.elementAt(1);
/*      */             } else {
/* 2068 */               entityItem7 = null;
/*      */             }
/*      */           
/*      */           }
/*      */         } 
/* 2073 */       } else if (entityGroup5 != null && entityGroup5.hasData()) {
/* 2074 */         entityItem7 = entityGroup5.getEntityItem(0);
/* 2075 */         str41 = PokUtils.getAttributeValue(entityItem7, "WARRID", "", "");
/* 2076 */         if (str41.equals("WTY0000")) {
/* 2077 */           if (entityGroup5.getEntityItemCount() > 1) {
/* 2078 */             entityItem7 = entityGroup5.getEntityItem(1);
/*      */           } else {
/* 2080 */             entityItem7 = null;
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2086 */       if (entityItem7 != null) {
/* 2087 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem7.getAttribute("WARRTYPE");
/* 2088 */         if (eANFlagAttribute != null) {
/* 2089 */           if (paramString1.equals("Europe/Middle East/Africa")) {
/* 2090 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 2091 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250")) {
/* 2092 */               str40 = "Y";
/*      */             } else {
/* 2094 */               str40 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 2098 */           if (paramString1.equals("Latin America")) {
/* 2099 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 2100 */               .isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute.isSelected("W0580")) {
/* 2101 */               str40 = "Y";
/*      */             } else {
/* 2103 */               str40 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 2107 */           if (paramString1.equals("Asia Pacific")) {
/* 2108 */             if (eANFlagAttribute.isSelected("W0550") || eANFlagAttribute
/* 2109 */               .isSelected("W0390") || eANFlagAttribute
/* 2110 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250") || eANFlagAttribute
/* 2111 */               .isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 2112 */               .isSelected("W0590")) {
/* 2113 */               str40 = "Y";
/*      */             } else {
/* 2115 */               str40 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 2119 */           if (paramString1.equals("Canada and Caribbean North") || paramString1
/* 2120 */             .equals("US Only")) {
/* 2121 */             str40 = "N";
/*      */           }
/*      */         } 
/*      */       } else {
/* 2125 */         str40 = "N";
/*      */       } 
/* 2127 */       stringBuffer.append(getValue("FSLM2CF", str40));
/*      */       
/* 2129 */       stringBuffer.append(getValue("IDORIG", "IBM"));
/* 2130 */       str8 = "000";
/* 2131 */       str9 = "000";
/* 2132 */       str10 = "000";
/* 2133 */       str11 = "000";
/*      */       
/* 2135 */       if (paramString1.equals("US Only") || paramString1.equals("Canada and Caribbean North")) {
/* 2136 */         str8 = "000";
/* 2137 */         str9 = "000";
/* 2138 */         str10 = "000";
/* 2139 */         str11 = "000";
/* 2140 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 2141 */         str8 = " @@";
/* 2142 */         str9 = " @@";
/* 2143 */         str10 = " @@";
/* 2144 */         str11 = " @@";
/*      */       }
/* 2146 */       else if (entityItem6 != null) {
/*      */         
/* 2148 */         str8 = getNumValue("PCUAEAP", PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMHGHSCH", ",", "", false));
/* 2149 */         str9 = getNumValue("PCUAHEA", PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMHGHSCH", ",", "", false));
/* 2150 */         str10 = getNumValue("PCUASEA", PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMSECONDRYSCH", ",", "", false));
/* 2151 */         str11 = getNumValue("PCUAUEA", PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMUNVRSTY", ",", "", false));
/*      */       } 
/*      */ 
/*      */       
/* 2155 */       stringBuffer.append(getValue("PCUAEAP", str8));
/* 2156 */       stringBuffer.append(getValue("PCUAHEA", str9));
/* 2157 */       stringBuffer.append(getValue("PCUASEA", str10));
/* 2158 */       stringBuffer.append(getValue("PCUAUEA", str11));
/*      */       
/* 2160 */       stringBuffer.append(getValue("POGMES", ""));
/*      */       
/* 2162 */       String str42 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/* 2163 */       if (str42.equals("CIF")) {
/* 2164 */         if (paramString1.equals("Europe/Middle East/Africa") || paramString1
/* 2165 */           .equals("Latin America")) {
/* 2166 */           str27 = "01";
/* 2167 */         } else if (paramString1.equals("Asia Pacific")) {
/* 2168 */           str27 = "10";
/* 2169 */         } else if (paramString1.equals("US Only") || paramString1
/* 2170 */           .equals("Canada and Caribbean North")) {
/* 2171 */           str27 = "";
/*      */         } 
/* 2173 */       } else if (str42.equals("CE") || str42.equals("N/A") || str42.equals("Does not apply")) {
/* 2174 */         str27 = "";
/*      */       } 
/* 2176 */       stringBuffer.append(getValue("QSLMCSU", str27));
/*      */       
/* 2178 */       stringBuffer.append(getValue("QSMXESA", "N"));
/* 2179 */       stringBuffer.append(getValue("QSMXSSA", "N"));
/*      */       
/* 2181 */       String str43 = PokUtils.getAttributeValue(entityItem2, "INVNAME", ",", "", false);
/* 2182 */       stringBuffer.append(getValue("TSLMDES", removeSpecialChars(str43)));
/*      */       
/* 2184 */       str25 = "";
/*      */       
/* 2186 */       if (str22.equals("S")) {
/* 2187 */         str25 = "OTH";
/*      */       }
/*      */       
/* 2190 */       stringBuffer.append(getValue("STSPCFT", str25));
/* 2191 */       stringBuffer.append(getValue("TIMSTMP", ""));
/* 2192 */       stringBuffer.append(getValue("USERID", ""));
/*      */       
/* 2194 */       arrayList = new ArrayList();
/* 2195 */       str12 = "";
/* 2196 */       str13 = "";
/* 2197 */       str14 = "";
/* 2198 */       str15 = "";
/* 2199 */       str16 = "";
/* 2200 */       str17 = "";
/* 2201 */       str18 = "";
/* 2202 */       str19 = "";
/*      */       
/* 2204 */       if (paramString1.equals("Asia Pacific") && 
/* 2205 */         entityItem7 != null) {
/* 2206 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem7.getAttribute("WARRTYPE");
/* 2207 */         if (eANFlagAttribute != null) {
/* 2208 */           if (eANFlagAttribute.isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute.isSelected("W0580")) {
/* 2209 */             arrayList.add("IOR");
/*      */           }
/* 2211 */           if (eANFlagAttribute.isSelected("W0550")) {
/* 2212 */             arrayList.add("IOE");
/*      */           }
/* 2214 */           if (eANFlagAttribute.isSelected("W0390")) {
/* 2215 */             arrayList.add("COE");
/*      */           }
/* 2217 */           if (eANFlagAttribute.isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute.isSelected("W0250")) {
/* 2218 */             arrayList.add("CCE");
/*      */           }
/* 2220 */           if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330")) {
/* 2221 */             arrayList.add("CCR");
/*      */           }
/* 2223 */           if (eANFlagAttribute.isSelected("W0590")) {
/* 2224 */             arrayList.add("IOS");
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
/* 2247 */           for (byte b1 = 0; b1 < arrayList.size(); b1++) {
/* 2248 */             if (b1 == 0) {
/* 2249 */               str12 = arrayList.get(b1);
/* 2250 */             } else if (b1 == 1) {
/* 2251 */               str13 = arrayList.get(b1);
/* 2252 */             } else if (b1 == 2) {
/* 2253 */               str14 = arrayList.get(b1);
/* 2254 */             } else if (b1 == 3) {
/* 2255 */               str15 = arrayList.get(b1);
/* 2256 */             } else if (b1 == 4) {
/* 2257 */               str16 = arrayList.get(b1);
/* 2258 */             } else if (b1 == 5) {
/* 2259 */               str17 = arrayList.get(b1);
/* 2260 */             } else if (b1 == 6) {
/* 2261 */               str18 = arrayList.get(b1);
/* 2262 */             } else if (b1 == 7) {
/* 2263 */               str19 = arrayList.get(b1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2270 */       stringBuffer.append(getValue("CSLMTM1", str12));
/* 2271 */       stringBuffer.append(getValue("CSLMTM2", str13));
/* 2272 */       stringBuffer.append(getValue("CSLMTM3", str14));
/* 2273 */       stringBuffer.append(getValue("CSLMTM4", str15));
/* 2274 */       stringBuffer.append(getValue("CSLMTM5", str16));
/* 2275 */       stringBuffer.append(getValue("CSLMTM6", str17));
/* 2276 */       stringBuffer.append(getValue("CSLMTM7", str18));
/* 2277 */       stringBuffer.append(getValue("CSLMTM8", str19));
/*      */       
/* 2279 */       stringBuffer.append(getValue("FSAPRES", "N"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2294 */       if (paramString1.equals("US Only")) {
/* 2295 */         EntityItem entityItem = null;
/* 2296 */         str35 = PokUtils.getAttributeValue(entityItem3, "WARRSVCCOVR", "", "");
/* 2297 */         if (str35 != null) {
/* 2298 */           if (str35.equals("No Warranty") || str35.equals("")) {
/* 2299 */             str34 = "Z";
/*      */           }
/* 2301 */           else if (entityGroup5 != null && entityGroup5.hasData()) {
/* 2302 */             entityItem = entityGroup5.getEntityItem(0);
/*      */             
/* 2304 */             if (entityItem != null) {
/* 2305 */               String str = PokUtils.getAttributeValue(entityItem, "WARRID", "", "");
/* 2306 */               if (str.equals("WTY0000")) {
/* 2307 */                 if (entityGroup5.getEntityItemCount() > 1) {
/* 2308 */                   entityItem = entityGroup5.getEntityItem(1);
/*      */                 } else {
/* 2310 */                   entityItem = null;
/*      */                 } 
/*      */               }
/*      */             } 
/*      */             
/* 2315 */             if (entityItem != null) {
/* 2316 */               str34 = PokUtils.getAttributeValue(entityItem, "WARRCATG", "", "");
/*      */             } else {
/* 2318 */               str34 = "";
/*      */             } 
/*      */           } else {
/* 2321 */             str34 = "";
/*      */           } 
/*      */         } else {
/*      */           
/* 2325 */           str34 = "Z";
/*      */         } 
/*      */       } else {
/* 2328 */         str34 = "";
/*      */       } 
/*      */       
/* 2331 */       stringBuffer.append(getValue("CSLMWCD", str34));
/*      */       
/* 2333 */       if (paramString1.equals("US Only")) {
/* 2334 */         String str = PokUtils.getAttributeValue(entityItem3, "MAINTANNBILLELIGINDC", ",", "", false);
/* 2335 */         if (str.equals("Yes")) {
/* 2336 */           stringBuffer.append(getValue("CUSAPMS", "Y"));
/* 2337 */         } else if (str.equals("No")) {
/* 2338 */           stringBuffer.append(getValue("CUSAPMS", "X"));
/*      */         } else {
/* 2340 */           stringBuffer.append(getValue("CUSAPMS", ""));
/*      */         } 
/*      */       } else {
/* 2343 */         stringBuffer.append(getValue("CUSAPMS", ""));
/*      */       } 
/*      */       
/* 2346 */       stringBuffer.append(getValue("DUSALRW", "2050-12-31"));
/* 2347 */       stringBuffer.append(getValue("DUSAMDW", "2050-12-31"));
/* 2348 */       stringBuffer.append(getValue("DUSAWUW", "2050-12-31"));
/*      */       
/* 2350 */       if (paramString1.equals("US Only")) {
/* 2351 */         stringBuffer.append(getValue("FSLMCBL", "N"));
/*      */       } else {
/* 2353 */         stringBuffer.append(getValue("FSLMCBL", ""));
/*      */       } 
/*      */       
/* 2356 */       if (paramString1.equals("US Only")) {
/* 2357 */         stringBuffer.append(getValue("FUSAAAS", "Y"));
/*      */       } else {
/* 2359 */         stringBuffer.append(getValue("FUSAAAS", ""));
/*      */       } 
/*      */       
/* 2362 */       if (paramString1.equals("US Only")) {
/* 2363 */         stringBuffer.append(getValue("FUSAEDE", str29));
/*      */       } else {
/* 2365 */         stringBuffer.append(getValue("FUSAEDE", ""));
/*      */       } 
/*      */       
/* 2368 */       if (paramString1.equals("US Only")) {
/* 2369 */         stringBuffer.append(getValue("FUSALEP", PokUtils.getAttributeValue(entityItem3, "MAINTANNBILLELIGINDC", ",", "", false)));
/*      */       } else {
/* 2371 */         stringBuffer.append(getValue("FUSALEP", " "));
/*      */       } 
/*      */       
/* 2374 */       if (paramString1.equals("US Only")) {
/* 2375 */         stringBuffer.append(getValue("FUSAIRR", "N"));
/*      */       } else {
/* 2377 */         stringBuffer.append(getValue("FUSAIRR", ""));
/*      */       } 
/*      */ 
/*      */       
/* 2381 */       if (entityItem6 != null) {
/* 2382 */         stringBuffer.append(getValue("ICESPCC", PokUtils.getAttributeValue(entityItem6, "PERCALLCLS", ",", "", false)));
/*      */       } else {
/* 2384 */         stringBuffer.append(getValue("ICESPCC", ""));
/*      */       } 
/* 2386 */       stringBuffer.append(getValue("QUSAPOP", "00.0"));
/* 2387 */       stringBuffer.append(getValue("FSLMRFM", ""));
/*      */       
/* 2389 */       stringBuffer.append(NEWLINE);
/* 2390 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 2391 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isQSMGeoSelected(String paramString, EANFlagAttribute paramEANFlagAttribute) {
/* 2399 */     if (paramEANFlagAttribute != null) {
/* 2400 */       if (paramString.equals("Asia Pacific") && paramEANFlagAttribute.isSelected("6199")) {
/* 2401 */         return true;
/*      */       }
/*      */       
/* 2404 */       if (paramString.equals("Canada and Caribbean North") && paramEANFlagAttribute.isSelected("6200")) {
/* 2405 */         return true;
/*      */       }
/*      */       
/* 2408 */       if (paramString.equals("Europe/Middle East/Africa") && paramEANFlagAttribute.isSelected("6198")) {
/* 2409 */         return true;
/*      */       }
/*      */       
/* 2412 */       if (paramString.equals("Latin America") && paramEANFlagAttribute.isSelected("6204")) {
/* 2413 */         return true;
/*      */       }
/*      */       
/* 2416 */       if (paramString.equals("US Only") && paramEANFlagAttribute.isSelected("6221")) {
/* 2417 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2421 */     addDebug("***** isQSMGeoSelected false");
/* 2422 */     return false;
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
/*      */   private void createT017ProductCategory(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws SQLException, MiddlewareException, IOException {
/* 2438 */     this.m_elist = getEntityList(getModelProdstructVEName());
/*      */     
/* 2440 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/* 2442 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2443 */       EANFlagAttribute eANFlagAttribute = null;
/*      */       
/* 2445 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2447 */       eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 2448 */       if (eANFlagAttribute != null) {
/* 2449 */         if (eANFlagAttribute.isSelected("6199")) {
/* 2450 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific");
/*      */         }
/* 2452 */         if (eANFlagAttribute.isSelected("6200")) {
/* 2453 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North");
/*      */         }
/* 2455 */         if (eANFlagAttribute.isSelected("6198")) {
/* 2456 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa");
/*      */         }
/* 2458 */         if (eANFlagAttribute.isSelected("6204")) {
/* 2459 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America");
/*      */         }
/* 2461 */         if (eANFlagAttribute.isSelected("6221")) {
/* 2462 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only");
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
/*      */   private void createT017ProductCategoryRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString) throws SQLException, MiddlewareException, IOException {
/* 2483 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/*      */     
/* 2485 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2486 */       StringBuffer stringBuffer = new StringBuffer();
/* 2487 */       String str1 = "";
/* 2488 */       String str2 = "";
/* 2489 */       String str3 = "";
/*      */       
/* 2491 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2493 */       str3 = PokUtils.getAttributeValue(paramEntityItem1, "PRODCATEGORY", ",", "", false);
/*      */       
/* 2495 */       if (str3 != null && str3.length() > 0) {
/* 2496 */         String[] arrayOfString = str3.split(",");
/* 2497 */         for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*      */           
/* 2499 */           stringBuffer.append(getValue("IFTYPE", "P"));
/* 2500 */           stringBuffer.append(getValue("CPDXA", arrayOfString[b1]));
/* 2501 */           if (paramString.equals("Latin America")) {
/* 2502 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/* 2503 */           } else if (paramString.equals("Europe/Middle East/Africa")) {
/* 2504 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/* 2505 */           } else if (paramString.equals("Asia Pacific")) {
/* 2506 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/* 2507 */           } else if (paramString.equals("US Only")) {
/* 2508 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 2509 */           } else if (paramString.equals("Canada and Caribbean North")) {
/* 2510 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/*      */           } 
/* 2512 */           stringBuffer.append(getValue("ISLMPAL", str1));
/*      */           
/* 2514 */           str2 = PokUtils.getAttributeValue(entityItem, "MACHTYPEATR", "", "");
/* 2515 */           str2 = str2 + PokUtils.getAttributeValue(entityItem, "MODELATR", "", "");
/* 2516 */           stringBuffer.append(getValue("ISLMPRN", str2));
/*      */           
/* 2518 */           stringBuffer.append(getValue("TIMSTMP", ""));
/* 2519 */           stringBuffer.append(getValue("USERID", ""));
/*      */           
/* 2521 */           stringBuffer.append(NEWLINE);
/* 2522 */           paramOutputStreamWriter.write(stringBuffer.toString());
/* 2523 */           paramOutputStreamWriter.flush();
/*      */           
/* 2525 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "PRODSTRUCT", "FEATURE");
/* 2526 */           for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 2527 */             EntityItem entityItem1 = vector.elementAt(b2);
/* 2528 */             stringBuffer = new StringBuffer();
/*      */             
/* 2530 */             str2 = "";
/*      */             
/* 2532 */             stringBuffer.append(getValue("IFTYPE", "P"));
/* 2533 */             stringBuffer.append(getValue("CPDXA", PokUtils.getAttributeValue(paramEntityItem1, "PRODCATEGORY", ",", "", false)));
/*      */             
/* 2535 */             stringBuffer.append(getValue("ISLMPAL", str1));
/*      */             
/* 2537 */             str2 = PokUtils.getAttributeValue(entityItem, "MACHTYPEATR", "", "");
/* 2538 */             str2 = str2 + PokUtils.getAttributeValue(entityItem1, "FEATURECODE", "", "");
/* 2539 */             stringBuffer.append(getValue("ISLMPRN", str2));
/*      */             
/* 2541 */             stringBuffer.append(getValue("TIMSTMP", ""));
/* 2542 */             stringBuffer.append(getValue("USERID", ""));
/*      */             
/* 2544 */             stringBuffer.append(NEWLINE);
/* 2545 */             paramOutputStreamWriter.write(stringBuffer.toString());
/* 2546 */             paramOutputStreamWriter.flush();
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
/*      */   private void createT020NPMesUpgrade(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 2565 */     this.m_elist = getEntityList(getNPMesUpgradeVEName());
/*      */     
/* 2567 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/* 2569 */     String str = "";
/* 2570 */     boolean bool = false;
/*      */     
/* 2572 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2573 */       EANFlagAttribute eANFlagAttribute = null;
/*      */       
/* 2575 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2577 */       str = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/* 2578 */       if (str.equals("EPIC")) {
/* 2579 */         bool = true;
/*      */       }
/*      */       
/* 2582 */       eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 2583 */       if (eANFlagAttribute != null) {
/* 2584 */         if (eANFlagAttribute.isSelected("6199")) {
/* 2585 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", bool);
/*      */         }
/* 2587 */         if (eANFlagAttribute.isSelected("6200")) {
/* 2588 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", bool);
/*      */         }
/* 2590 */         if (eANFlagAttribute.isSelected("6198")) {
/* 2591 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", bool);
/*      */         }
/* 2593 */         if (eANFlagAttribute.isSelected("6204")) {
/* 2594 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", bool);
/*      */         }
/* 2596 */         if (eANFlagAttribute.isSelected("6221")) {
/* 2597 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", bool);
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
/*      */   private void createT020NPMesUpgradeRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString, boolean paramBoolean) throws IOException, SQLException, MiddlewareException {
/* 2621 */     StringBuffer stringBuffer = new StringBuffer();
/* 2622 */     String str1 = "";
/* 2623 */     String str2 = "";
/* 2624 */     String str3 = "";
/* 2625 */     String str4 = "";
/*      */     
/* 2627 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELCONVERTAVAIL", "MODELCONVERT");
/*      */     
/* 2629 */     for (byte b = 0; b < vector.size(); b++) {
/* 2630 */       String str5 = "";
/* 2631 */       String str6 = "";
/*      */       
/* 2633 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/* 2635 */       stringBuffer.append(getValue("IFTYPE", "N"));
/*      */       
/* 2637 */       if (paramString.equals("Latin America")) {
/* 2638 */         str3 = "601";
/* 2639 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/* 2640 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 2641 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/* 2642 */         str3 = "999";
/* 2643 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/* 2644 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 2645 */       } else if (paramString.equals("Asia Pacific")) {
/* 2646 */         str3 = "872";
/* 2647 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/* 2648 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 2649 */       } else if (paramString.equals("US Only")) {
/* 2650 */         str3 = "897";
/* 2651 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 2652 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 2653 */       } else if (paramString.equals("Canada and Caribbean North")) {
/* 2654 */         str3 = "649";
/* 2655 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/* 2656 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */       } 
/* 2658 */       stringBuffer.append(getValue("IOPUCTY", str3));
/* 2659 */       stringBuffer.append(getValue("ISLMPAL", str1));
/* 2660 */       stringBuffer.append(getValue("ISLMRFA", str4));
/*      */       
/* 2662 */       str2 = PokUtils.getAttributeValue(entityItem1, "TOMACHTYPE", "", "");
/* 2663 */       str2 = str2 + PokUtils.getAttributeValue(entityItem1, "TOMODEL", "", "");
/* 2664 */       stringBuffer.append(getValue("ISLMPRN", str2));
/* 2665 */       stringBuffer.append(getValue("CSLMPCI", "NP"));
/* 2666 */       stringBuffer.append(getValue("FPUNINC", "2"));
/* 2667 */       stringBuffer.append(getValue("CAOAV", ""));
/* 2668 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", "")));
/* 2669 */       stringBuffer.append(getValue("DSLMCPO", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", "")));
/*      */ 
/*      */       
/* 2672 */       EntityItem entityItem2 = searchForAvailTypeLO(entityItem1, "Last Order");
/*      */       
/* 2674 */       if (entityItem2 != null) {
/* 2675 */         stringBuffer.append(getValue("DSLMWDN", PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "")));
/*      */       } else {
/* 2677 */         stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/*      */       } 
/*      */       
/* 2680 */       str5 = PokUtils.getAttributeValue(entityItem1, "FROMMACHTYPE", "", "");
/* 2681 */       str6 = PokUtils.getAttributeValue(entityItem1, "FROMMODEL", "", "");
/* 2682 */       stringBuffer.append(getValue("QSMNPMT", str5));
/* 2683 */       stringBuffer.append(getValue("QSMNPMM", str6));
/* 2684 */       stringBuffer.append(getValue("TIMSTMP", ""));
/* 2685 */       stringBuffer.append(getValue("USERID", ""));
/*      */       
/* 2687 */       stringBuffer.append(NEWLINE);
/* 2688 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 2689 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem searchForAvailTypeLO(EntityItem paramEntityItem, String paramString) {
/* 2697 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/* 2700 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELCONVERTAVAIL", "AVAIL");
/*      */     
/* 2702 */     for (byte b = 0; b < vector.size(); b++) {
/* 2703 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/* 2705 */       String str = PokUtils.getAttributeValue(entityItem1, "AVAILTYPE", ",", "", false);
/*      */       
/* 2707 */       if (paramString.equals(str)) {
/* 2708 */         entityItem = entityItem1;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2713 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT512ReleaseTo(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException {
/* 2719 */     StringBuffer stringBuffer = new StringBuffer();
/* 2720 */     String str1 = "";
/* 2721 */     String str2 = "";
/*      */ 
/*      */     
/* 2724 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/* 2725 */     if (eANFlagAttribute != null && (
/* 2726 */       eANFlagAttribute.isSelected("6199") || eANFlagAttribute.isSelected("1999"))) {
/* 2727 */       stringBuffer.append(getValue("IFTYPE", "R"));
/* 2728 */       stringBuffer.append(getValue("IOPUCTY", "872"));
/* 2729 */       str1 = PokUtils.getAttributeValue(paramEntityItem, "ADOCNO", "", "");
/* 2730 */       stringBuffer.append(getValue("ISLMPAL", str1));
/*      */       
/* 2732 */       str2 = PokUtils.getAttributeValue(paramEntityItem, "ANNNUMBER", "", "");
/* 2733 */       stringBuffer.append(getValue("ISLMRFA", str2));
/* 2734 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/* 2735 */       stringBuffer.append(getValue("DSLMEFF", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/* 2736 */       stringBuffer.append(getValue("CSLMRCH", ""));
/* 2737 */       stringBuffer.append(getValue("CSLMNUM", str1));
/* 2738 */       stringBuffer.append(getValue("FSLMAPG", "Y"));
/* 2739 */       stringBuffer.append(getValue("FSLMASP", "N"));
/* 2740 */       stringBuffer.append(getValue("FSLMJAP", "N"));
/* 2741 */       if (eANFlagAttribute != null) {
/*      */         
/* 2743 */         EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/*      */         
/* 2745 */         if (eANFlagAttribute1.isSelected("1439")) {
/* 2746 */           stringBuffer.append(getValue("FSLMAUS", "Y"));
/*      */         } else {
/* 2748 */           stringBuffer.append(getValue("FSLMAUS", "N"));
/*      */         } 
/*      */         
/* 2751 */         if (eANFlagAttribute1.isSelected("1444")) {
/* 2752 */           stringBuffer.append(getValue("FSLMBGL", "Y"));
/*      */         } else {
/* 2754 */           stringBuffer.append(getValue("FSLMBGL", "N"));
/*      */         } 
/*      */ 
/*      */         
/* 2758 */         stringBuffer.append(getValue("FSLMBRU", "N"));
/*      */ 
/*      */ 
/*      */         
/* 2762 */         if (eANFlagAttribute1.isSelected("1524")) {
/* 2763 */           stringBuffer.append(getValue("FSLMHKG", "Y"));
/*      */         } else {
/* 2765 */           stringBuffer.append(getValue("FSLMHKG", "N"));
/*      */         } 
/* 2767 */         if (eANFlagAttribute1.isSelected("1528")) {
/* 2768 */           stringBuffer.append(getValue("FSLMIDN", "Y"));
/*      */         } else {
/* 2770 */           stringBuffer.append(getValue("FSLMIDN", "N"));
/*      */         } 
/* 2772 */         if (eANFlagAttribute1.isSelected("1527")) {
/* 2773 */           stringBuffer.append(getValue("FSLMIND", "Y"));
/*      */         } else {
/* 2775 */           stringBuffer.append(getValue("FSLMIND", "N"));
/*      */         } 
/* 2777 */         if (eANFlagAttribute1.isSelected("1541")) {
/* 2778 */           stringBuffer.append(getValue("FSLMKOR", "Y"));
/*      */         } else {
/* 2780 */           stringBuffer.append(getValue("FSLMKOR", "N"));
/*      */         } 
/* 2782 */         if (eANFlagAttribute1.isSelected("1553")) {
/* 2783 */           stringBuffer.append(getValue("FSLMMAC", "Y"));
/*      */         } else {
/* 2785 */           stringBuffer.append(getValue("FSLMMAC", "N"));
/*      */         } 
/* 2787 */         if (eANFlagAttribute1.isSelected("1557")) {
/* 2788 */           stringBuffer.append(getValue("FSLMMAL", "Y"));
/*      */         } else {
/* 2790 */           stringBuffer.append(getValue("FSLMMAL", "N"));
/*      */         } 
/* 2792 */         if (eANFlagAttribute1.isSelected("1574")) {
/* 2793 */           stringBuffer.append(getValue("FSLMMYA", "Y"));
/*      */         } else {
/* 2795 */           stringBuffer.append(getValue("FSLMMYA", "N"));
/*      */         } 
/* 2797 */         if (eANFlagAttribute1.isSelected("1581")) {
/* 2798 */           stringBuffer.append(getValue("FSLMNZL", "Y"));
/*      */         } else {
/* 2800 */           stringBuffer.append(getValue("FSLMNZL", "N"));
/*      */         } 
/* 2802 */         if (eANFlagAttribute1.isSelected("1597")) {
/* 2803 */           stringBuffer.append(getValue("FSLMPHI", "Y"));
/*      */         } else {
/* 2805 */           stringBuffer.append(getValue("FSLMPHI", "N"));
/*      */         } 
/* 2807 */         if (eANFlagAttribute1.isSelected("1470")) {
/* 2808 */           stringBuffer.append(getValue("FSLMPRC", "Y"));
/*      */         } else {
/* 2810 */           stringBuffer.append(getValue("FSLMPRC", "N"));
/*      */         } 
/* 2812 */         if (eANFlagAttribute1.isSelected("1627")) {
/* 2813 */           stringBuffer.append(getValue("FSLMSLA", "Y"));
/*      */         } else {
/* 2815 */           stringBuffer.append(getValue("FSLMSLA", "N"));
/*      */         } 
/* 2817 */         if (eANFlagAttribute1.isSelected("1619")) {
/* 2818 */           stringBuffer.append(getValue("FSLMSNG", "Y"));
/*      */         } else {
/* 2820 */           stringBuffer.append(getValue("FSLMSNG", "N"));
/*      */         } 
/* 2822 */         if (eANFlagAttribute1.isSelected("1635")) {
/* 2823 */           stringBuffer.append(getValue("FSLMTAI", "Y"));
/*      */         } else {
/* 2825 */           stringBuffer.append(getValue("FSLMTAI", "N"));
/*      */         } 
/* 2827 */         if (eANFlagAttribute1.isSelected("1638")) {
/* 2828 */           stringBuffer.append(getValue("FSLMTHA", "Y"));
/*      */         } else {
/* 2830 */           stringBuffer.append(getValue("FSLMTHA", "N"));
/*      */         } 
/*      */       } 
/* 2833 */       stringBuffer.append(getValue("TIMSTMP", " "));
/* 2834 */       stringBuffer.append(getValue("USERID", " "));
/*      */       
/* 2836 */       stringBuffer.append(NEWLINE);
/* 2837 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 2838 */       paramOutputStreamWriter.flush();
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
/*      */   private void createT632TypeModelFeatureRelation(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 2859 */     String str = "";
/* 2860 */     boolean bool = false;
/*      */     
/* 2862 */     this.m_elist = getEntityList(getT006ProdstructVEName());
/*      */     
/* 2864 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 2865 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2866 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 2869 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 2870 */       if (eANFlagAttribute != null && 
/* 2871 */         eANFlagAttribute.isSelected("6221")) {
/*      */         
/* 2873 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*      */         
/* 2875 */         String str1 = "";
/* 2876 */         str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/* 2877 */         str = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/* 2878 */         if (str.equals("EPIC")) {
/* 2879 */           bool = true;
/*      */         }
/*      */         
/* 2882 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 2883 */           StringBuffer stringBuffer = new StringBuffer();
/* 2884 */           EntityItem entityItem1 = vector.elementAt(b1);
/*      */           
/* 2886 */           ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getT006FeatureVEName());
/*      */           
/* 2888 */           EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, entityItem1.getEntityType(), entityItem1.getEntityID()) });
/*      */           
/* 2890 */           EntityGroup entityGroup1 = entityList.getEntityGroup("FEATURE");
/* 2891 */           EntityGroup entityGroup2 = entityList.getEntityGroup("MODEL");
/* 2892 */           EntityItem entityItem2 = entityGroup1.getEntityItem(0);
/* 2893 */           EntityItem entityItem3 = entityGroup2.getEntityItem(0);
/*      */           
/* 2895 */           stringBuffer = new StringBuffer();
/* 2896 */           String str2 = "";
/* 2897 */           String str3 = "";
/* 2898 */           String str4 = "";
/* 2899 */           String str5 = "";
/*      */           
/* 2901 */           stringBuffer.append(getValue("IFTYPE", "T"));
/* 2902 */           str3 = PokUtils.getAttributeValue(paramEntityItem, "USDOCNO", "", "");
/*      */ 
/*      */ 
/*      */           
/* 2906 */           str2 = getRFANumber(paramEntityItem, bool, entityItem);
/* 2907 */           addDebug("*****mlm ISLMRFA=" + str2);
/* 2908 */           stringBuffer.append(getValue("IOPUCTY", "897"));
/* 2909 */           stringBuffer.append(getValue("ISLMPAL", str3));
/* 2910 */           stringBuffer.append(getValue("ISLMRFA", str2));
/* 2911 */           stringBuffer.append(getValue("ISLMTYP", PokUtils.getAttributeValue(entityItem3, "MACHTYPEATR", "", "")));
/* 2912 */           stringBuffer.append(getValue("ISLMMOD", PokUtils.getAttributeValue(entityItem3, "MODELATR", "", "")));
/* 2913 */           stringBuffer.append(getValue("ISLMFTR", PokUtils.getAttributeValue(entityItem2, "FEATURECODE", "", "")));
/* 2914 */           stringBuffer.append(getValue("ISLMXX1", ""));
/* 2915 */           stringBuffer.append(getValue("CSLMPCI", "TR"));
/* 2916 */           stringBuffer.append(getValue("FPUNINC", "2"));
/* 2917 */           stringBuffer.append(getValue("CAOAV", ""));
/* 2918 */           stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/* 2919 */           stringBuffer.append(getValue("DSLMCPO", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/* 2920 */           if (str1.equals("Last Order")) {
/* 2921 */             stringBuffer.append(PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false));
/*      */           } else {
/* 2923 */             stringBuffer.append(getValue("DSLMWDN", "2050-12-31"));
/*      */           } 
/*      */           
/* 2926 */           str4 = PokUtils.getAttributeValue(entityItem1, "ORDERCODE", "", "");
/*      */           
/* 2928 */           if (str4.equals("MES")) {
/* 2929 */             stringBuffer.append(getValue("FSLMMES", "Y"));
/*      */           } else {
/* 2931 */             stringBuffer.append(getValue("FSLMMES", "N"));
/*      */           } 
/*      */           
/* 2934 */           if (str4.equals("Initial")) {
/* 2935 */             stringBuffer.append(getValue("FSLMPIO", "Y"));
/*      */           } else {
/* 2937 */             stringBuffer.append(getValue("FSLMPIO", "N"));
/*      */           } 
/*      */           
/* 2940 */           String str6 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/*      */           
/* 2942 */           if (str6.equals("CIF")) {
/* 2943 */             str5 = "01";
/*      */           } else {
/* 2945 */             str5 = "00";
/*      */           } 
/*      */           
/* 2948 */           stringBuffer.append(getValue("QSLMCSU", str5));
/* 2949 */           stringBuffer.append(getValue("TIMSTMP", ""));
/* 2950 */           stringBuffer.append(getValue("USERID", ""));
/* 2951 */           stringBuffer.append(getValue("FSLMRFM", ""));
/*      */           
/* 2953 */           stringBuffer.append(NEWLINE);
/* 2954 */           paramOutputStreamWriter.write(stringBuffer.toString());
/* 2955 */           paramOutputStreamWriter.flush();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getRFANumber(EntityItem paramEntityItem1, boolean paramBoolean, EntityItem paramEntityItem2) {
/*      */     String str;
/* 2965 */     if (paramBoolean) {
/* 2966 */       str = PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "");
/*      */     } else {
/* 2968 */       str = "R" + PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*      */     } 
/*      */ 
/*      */     
/* 2972 */     return str;
/*      */   }
/*      */   
/*      */   protected String getValue(String paramString1, String paramString2) {
/* 2976 */     if (paramString2 == null)
/* 2977 */       paramString2 = ""; 
/* 2978 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 2979 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1)
/* 2980 */         .toString());
/* 2981 */     if (i == j)
/* 2982 */       return paramString2; 
/* 2983 */     if (i > j) {
/* 2984 */       return paramString2.substring(0, j);
/*      */     }
/* 2986 */     return paramString2 + getBlank(j - i);
/*      */   }
/*      */   
/*      */   protected String getBlank(int paramInt) {
/* 2990 */     StringBuffer stringBuffer = new StringBuffer();
/* 2991 */     while (paramInt > 0) {
/* 2992 */       stringBuffer.append(" ");
/* 2993 */       paramInt--;
/*      */     } 
/* 2995 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private String getNumValue(String paramString1, String paramString2) {
/* 3000 */     if (paramString2 == null)
/* 3001 */       paramString2 = ""; 
/* 3002 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 3003 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1)
/* 3004 */         .toString());
/* 3005 */     if (i == j)
/* 3006 */       return paramString2; 
/* 3007 */     if (i > j) {
/* 3008 */       return paramString2.substring(0, j);
/*      */     }
/* 3010 */     paramString2 = paramString2.trim();
/* 3011 */     int k = i;
/* 3012 */     while (k < j) {
/* 3013 */       paramString2 = "0" + paramString2;
/* 3014 */       k++;
/*      */     } 
/*      */     
/* 3017 */     return paramString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 3028 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*      */     
/* 3030 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, this.rootEntity.getEntityType(), this.rootEntity.getEntityID()) });
/*      */     
/* 3032 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 3033 */         PokUtils.outputList(entityList));
/* 3034 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT002ModelVEName() {
/* 3042 */     return "QSMFULL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006FeatureVEName() {
/* 3050 */     return "QSMFULL2";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006ProdstructVEName() {
/* 3058 */     return "QSMFULL1";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getModelProdstructVEName() {
/* 3066 */     return "QSMFULL3";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNPMesUpgradeVEName() {
/* 3074 */     return "QSMFULL4";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006ModelLinksVEName() {
/* 3082 */     return "QSMFULL5";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exeFtpShell(String paramString) {
/* 3090 */     String str1 = ABRServerProperties.getValue(this.abrcode, "_script", null) + " -f " + paramString;
/* 3091 */     String str2 = ABRServerProperties.getValue(this.abrcode, "_inipath", null);
/* 3092 */     if (str2 != null)
/* 3093 */       str1 = str1 + " -i " + str2; 
/* 3094 */     if (this.dir != null)
/* 3095 */       str1 = str1 + " -d " + this.dir; 
/* 3096 */     if (this.fileprefix != null)
/* 3097 */       str1 = str1 + " -p " + this.fileprefix; 
/* 3098 */     String str3 = ABRServerProperties.getValue(this.abrcode, "_targetfilename", null);
/* 3099 */     if (str3 != null)
/* 3100 */       str1 = str1 + " -t " + str3; 
/* 3101 */     String str4 = ABRServerProperties.getValue(this.abrcode, "_logpath", null);
/* 3102 */     if (str4 != null)
/* 3103 */       str1 = str1 + " -l " + str4; 
/* 3104 */     String str5 = ABRServerProperties.getValue(this.abrcode, "_backuppath", null);
/* 3105 */     if (str5 != null)
/* 3106 */       str1 = str1 + " -b " + str5; 
/* 3107 */     Runtime runtime = Runtime.getRuntime();
/* 3108 */     String str6 = "";
/* 3109 */     BufferedReader bufferedReader = null;
/* 3110 */     BufferedInputStream bufferedInputStream = null;
/*      */     
/*      */     try {
/* 3113 */       Process process = runtime.exec(str1);
/* 3114 */       if (process.waitFor() != 0) {
/* 3115 */         return false;
/*      */       }
/* 3117 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 3118 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 3119 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 3120 */         str6 = str6 + this.lineStr;
/* 3121 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 3122 */           return false;
/*      */         }
/*      */       } 
/* 3125 */     } catch (Exception exception) {
/* 3126 */       exception.printStackTrace();
/* 3127 */       return false;
/*      */     } finally {
/* 3129 */       if (bufferedReader != null) {
/*      */         try {
/* 3131 */           bufferedReader.close();
/* 3132 */           bufferedInputStream.close();
/* 3133 */         } catch (IOException iOException) {
/* 3134 */           iOException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 3138 */     return !(str6 == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 3145 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 3153 */     String str = getBundle().getString(paramString);
/* 3154 */     if (paramArrayOfObject != null) {
/* 3155 */       MessageFormat messageFormat = new MessageFormat(str);
/* 3156 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 3159 */     addOutput(str);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 3164 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 3170 */     addOutput(paramString);
/* 3171 */     setReturnCode(-1);
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
/* 3184 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 3185 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 3188 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 3189 */     Object[] arrayOfObject = new Object[2];
/* 3190 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 3191 */     arrayOfObject[1] = this.navName;
/*      */     
/* 3193 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 3202 */     String str = getBundle().getString(paramString2);
/*      */     
/* 3204 */     if (paramArrayOfObject != null) {
/* 3205 */       MessageFormat messageFormat = new MessageFormat(str);
/* 3206 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 3209 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 3219 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 3229 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 3232 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 3233 */     if (eANList == null) {
/*      */       
/* 3235 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 3236 */       eANList = entityGroup.getMetaAttribute();
/* 3237 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 3239 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 3241 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 3242 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 3243 */       if (b + 1 < eANList.size()) {
/* 3244 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 3248 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 3258 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 3267 */     return "QSMFULLABR";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMFULLABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */