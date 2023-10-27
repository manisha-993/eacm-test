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
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
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
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class QSMFULLABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  359 */   private StringBuffer rptSb = new StringBuffer();
/*  360 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  361 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */ 
/*      */   
/*  364 */   public static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("QSMFULLABRSTATUS");
/*      */   
/*  366 */   private ResourceBundle rsBundle = null;
/*  367 */   private Hashtable metaTbl = new Hashtable<>();
/*  368 */   private String navName = "";
/*      */   
/*  370 */   private String ffFileName = null;
/*  371 */   private String ffPathName = null;
/*  372 */   private String ffFTPPathName = null;
/*  373 */   private String dir = null;
/*  374 */   private String dirDest = null;
/*  375 */   private final String QSMRPTPATH = "_rptpath";
/*  376 */   private final String QSMGENPATH = "_genpath";
/*  377 */   private final String QSMFTPPATH = "_ftppath";
/*  378 */   private final String CHUNKSIZE = "_chunksize";
/*  379 */   private int abr_debuglvl = 0;
/*      */   private static final String CREFINIPATH = "_inipath";
/*      */   private static final String FTPSCRPATH = "_script";
/*      */   private static final String TARGETFILENAME = "_targetfilename";
/*      */   private static final String LOGPATH = "_logpath";
/*      */   private static final String BACKUPPATH = "_backuppath";
/*  385 */   private String fileprefix = null;
/*  386 */   private String lineStr = "";
/*  387 */   private int chunkSize = 0;
/*  388 */   private Set availSet = null;
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
/*  531 */   private HashMap fidMap = new HashMap<>();
/*  532 */   private static final List geoWWList = Collections.unmodifiableList(new ArrayList()
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
/*  547 */     COLUMN_LENGTH = new Hashtable<>();
/*  548 */     COLUMN_LENGTH.put("IFTYPE", "1");
/*  549 */     COLUMN_LENGTH.put("IOPUCTY", "3");
/*  550 */     COLUMN_LENGTH.put("ISLMPAL", "8");
/*  551 */     COLUMN_LENGTH.put("ISLMRFA", "6");
/*  552 */     COLUMN_LENGTH.put("ISLMPRN", "14");
/*  553 */     COLUMN_LENGTH.put("CSLMPCI", "2");
/*  554 */     COLUMN_LENGTH.put("IPRTNUM", "12");
/*  555 */     COLUMN_LENGTH.put("FPUNINC", "1");
/*  556 */     COLUMN_LENGTH.put("CAOAV", "1");
/*  557 */     COLUMN_LENGTH.put("DSLMCPA", "10");
/*  558 */     COLUMN_LENGTH.put("DSLMCPO", "10");
/*  559 */     COLUMN_LENGTH.put("DSLMGAD", "10");
/*  560 */     COLUMN_LENGTH.put("DSLMMVA", "10");
/*  561 */     COLUMN_LENGTH.put("DSLMOPD", "10");
/*  562 */     COLUMN_LENGTH.put("DSLMWDN", "10");
/*  563 */     COLUMN_LENGTH.put("QSMEDMW", "10");
/*  564 */     COLUMN_LENGTH.put("ASLMMVP", "4");
/*  565 */     COLUMN_LENGTH.put("CCUOICR", "1");
/*  566 */     COLUMN_LENGTH.put("CICIB", "1");
/*  567 */     COLUMN_LENGTH.put("CICIC", "1");
/*  568 */     COLUMN_LENGTH.put("CICRY", "1");
/*  569 */     COLUMN_LENGTH.put("CIDCJ", "1");
/*  570 */     COLUMN_LENGTH.put("CIDXF", "1");
/*  571 */     COLUMN_LENGTH.put("CINCA", "1");
/*  572 */     COLUMN_LENGTH.put("CINCB", "1");
/*  573 */     COLUMN_LENGTH.put("CINCC", "1");
/*  574 */     COLUMN_LENGTH.put("CINPM", "1");
/*  575 */     COLUMN_LENGTH.put("CISUP", "1");
/*  576 */     COLUMN_LENGTH.put("CITEM", "1");
/*  577 */     COLUMN_LENGTH.put("CJLBIC1", "2");
/*  578 */     COLUMN_LENGTH.put("CJLBIDS", "1");
/*  579 */     COLUMN_LENGTH.put("CJLBOEM", "1");
/*  580 */     COLUMN_LENGTH.put("CJLBPOF", "1");
/*  581 */     COLUMN_LENGTH.put("CJLBSAC", "3");
/*  582 */     COLUMN_LENGTH.put("CLASSPT", "3");
/*  583 */     COLUMN_LENGTH.put("CPDAA", "1");
/*  584 */     COLUMN_LENGTH.put("CSLMFCC", "4");
/*  585 */     COLUMN_LENGTH.put("CSLMGGC", "2");
/*  586 */     COLUMN_LENGTH.put("CSLMIDP", "1");
/*  587 */     COLUMN_LENGTH.put("CSLMLRP", "1");
/*  588 */     COLUMN_LENGTH.put("CSLMSAS", "1");
/*  589 */     COLUMN_LENGTH.put("CSLMSYT", "5");
/*  590 */     COLUMN_LENGTH.put("CSLMWCD", "1");
/*  591 */     COLUMN_LENGTH.put("FAGRMBE", "1");
/*  592 */     COLUMN_LENGTH.put("FCUOCNF", "1");
/*  593 */     COLUMN_LENGTH.put("FSLMCLS", "1");
/*  594 */     COLUMN_LENGTH.put("FSLMCPU", "1");
/*  595 */     COLUMN_LENGTH.put("FSLMIOP", "1");
/*  596 */     COLUMN_LENGTH.put("FSLMLGS", "1");
/*  597 */     COLUMN_LENGTH.put("FSLMMLC", "1");
/*  598 */     COLUMN_LENGTH.put("FSLMPOP", "1");
/*  599 */     COLUMN_LENGTH.put("FSLMVDE", "1");
/*  600 */     COLUMN_LENGTH.put("FSLMVTS", "1");
/*  601 */     COLUMN_LENGTH.put("FSLM2CF", "1");
/*  602 */     COLUMN_LENGTH.put("ICESPCC", "1");
/*  603 */     COLUMN_LENGTH.put("IDORIG", "3");
/*  604 */     COLUMN_LENGTH.put("IOLCPLM", "2");
/*  605 */     COLUMN_LENGTH.put("PCUAHEA", "3");
/*  606 */     COLUMN_LENGTH.put("PCUASEA", "3");
/*  607 */     COLUMN_LENGTH.put("PCUAUEA", "3");
/*  608 */     COLUMN_LENGTH.put("QSLMCSU", "2");
/*  609 */     COLUMN_LENGTH.put("QSMXANN", "1");
/*  610 */     COLUMN_LENGTH.put("QSMXESA", "1");
/*  611 */     COLUMN_LENGTH.put("QSMXSSA", "1");
/*  612 */     COLUMN_LENGTH.put("SYSDES", "30");
/*  613 */     COLUMN_LENGTH.put("TSLMDES", "30");
/*  614 */     COLUMN_LENGTH.put("TSLTDES", "56");
/*  615 */     COLUMN_LENGTH.put("TIMSTMP", "26");
/*  616 */     COLUMN_LENGTH.put("USERID", "8");
/*  617 */     COLUMN_LENGTH.put("FBRAND", "1");
/*  618 */     COLUMN_LENGTH.put("FSLMHVP", "1");
/*  619 */     COLUMN_LENGTH.put("FSLMCVP", "1");
/*  620 */     COLUMN_LENGTH.put("FSLMMES", "1");
/*  621 */     COLUMN_LENGTH.put("CSLMTM1", "3");
/*  622 */     COLUMN_LENGTH.put("CSLMTM2", "3");
/*  623 */     COLUMN_LENGTH.put("CSLMTM3", "3");
/*  624 */     COLUMN_LENGTH.put("CSLMTM4", "3");
/*  625 */     COLUMN_LENGTH.put("CSLMTM5", "3");
/*  626 */     COLUMN_LENGTH.put("CSLMTM6", "3");
/*  627 */     COLUMN_LENGTH.put("CSLMTM7", "3");
/*  628 */     COLUMN_LENGTH.put("CSLMTM8", "3");
/*  629 */     COLUMN_LENGTH.put("FSAPRES", "1");
/*  630 */     COLUMN_LENGTH.put("CUSAPMS", "1");
/*  631 */     COLUMN_LENGTH.put("DUSALRW", "10");
/*  632 */     COLUMN_LENGTH.put("DUSAMDW", "10");
/*  633 */     COLUMN_LENGTH.put("DUSAWUW", "10");
/*  634 */     COLUMN_LENGTH.put("FSLMCBL", "1");
/*  635 */     COLUMN_LENGTH.put("FSLMMRR", "1");
/*  636 */     COLUMN_LENGTH.put("FUSAAAS", "1");
/*  637 */     COLUMN_LENGTH.put("FUSAADM", "1");
/*  638 */     COLUMN_LENGTH.put("FUSAEDE", "1");
/*  639 */     COLUMN_LENGTH.put("FUSAICC", "1");
/*  640 */     COLUMN_LENGTH.put("FUSALEP", "1");
/*  641 */     COLUMN_LENGTH.put("FUSAMRS", "1");
/*  642 */     COLUMN_LENGTH.put("FUSAVLM", "1");
/*  643 */     COLUMN_LENGTH.put("FUSAXMO", "1");
/*  644 */     COLUMN_LENGTH.put("QUSAPOP", "4");
/*  645 */     COLUMN_LENGTH.put("DSLMEOD", "10");
/*  646 */     COLUMN_LENGTH.put("FSLMRFM", "1");
/*  647 */     COLUMN_LENGTH.put("DSLMMES", "10");
/*  648 */     COLUMN_LENGTH.put("CIDXC", "1");
/*  649 */     COLUMN_LENGTH.put("CSLMFTY", "2");
/*  650 */     COLUMN_LENGTH.put("CVOAT", "1");
/*  651 */     COLUMN_LENGTH.put("FSLMPIO", "1");
/*  652 */     COLUMN_LENGTH.put("FSLMSTK", "1");
/*  653 */     COLUMN_LENGTH.put("PCUAEAP", "3");
/*  654 */     COLUMN_LENGTH.put("POGMES", "10");
/*  655 */     COLUMN_LENGTH.put("STSPCFT", "4");
/*  656 */     COLUMN_LENGTH.put("FUSAIRR", "1");
/*  657 */     COLUMN_LENGTH.put("CPDXA", "2");
/*  658 */     COLUMN_LENGTH.put("DSLMEFF", "10");
/*  659 */     COLUMN_LENGTH.put("CSLMRCH", "1");
/*  660 */     COLUMN_LENGTH.put("CSLMNUM", "6");
/*  661 */     COLUMN_LENGTH.put("FSLMAPG", "1");
/*  662 */     COLUMN_LENGTH.put("FSLMASP", "1");
/*  663 */     COLUMN_LENGTH.put("FSLMJAP", "1");
/*  664 */     COLUMN_LENGTH.put("FSLMAUS", "1");
/*  665 */     COLUMN_LENGTH.put("FSLMBGL", "1");
/*  666 */     COLUMN_LENGTH.put("FSLMBRU", "1");
/*  667 */     COLUMN_LENGTH.put("FSLMHKG", "1");
/*  668 */     COLUMN_LENGTH.put("FSLMIDN", "1");
/*  669 */     COLUMN_LENGTH.put("FSLMIND", "1");
/*  670 */     COLUMN_LENGTH.put("FSLMKOR", "1");
/*  671 */     COLUMN_LENGTH.put("FSLMMAC", "1");
/*  672 */     COLUMN_LENGTH.put("FSLMMAL", "1");
/*  673 */     COLUMN_LENGTH.put("FSLMMYA", "1");
/*  674 */     COLUMN_LENGTH.put("FSLMNZL", "1");
/*  675 */     COLUMN_LENGTH.put("FSLMPHI", "1");
/*  676 */     COLUMN_LENGTH.put("FSLMPRC", "1");
/*  677 */     COLUMN_LENGTH.put("FSLMSLA", "1");
/*  678 */     COLUMN_LENGTH.put("FSLMSNG", "1");
/*  679 */     COLUMN_LENGTH.put("FSLMTAI", "1");
/*  680 */     COLUMN_LENGTH.put("FSLMTHA", "1");
/*  681 */     COLUMN_LENGTH.put("ISLMTYP", "4");
/*  682 */     COLUMN_LENGTH.put("ISLMMOD", "3");
/*  683 */     COLUMN_LENGTH.put("ISLMFTR", "6");
/*  684 */     COLUMN_LENGTH.put("ISLMXX1", "1");
/*  685 */     COLUMN_LENGTH.put("QSMNPMT", "4");
/*  686 */     COLUMN_LENGTH.put("QSMNPMM", "3");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  693 */     return this.rsBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  703 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  705 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {5} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  711 */     String str3 = "";
/*  712 */     boolean bool1 = true;
/*  713 */     boolean bool2 = true;
/*      */ 
/*      */     
/*  716 */     String str4 = "";
/*      */     
/*  718 */     String[] arrayOfString = new String[10];
/*      */     
/*      */     try {
/*  721 */       MessageFormat messageFormat = new MessageFormat(str1);
/*  722 */       arrayOfString[0] = getShortClassName(getClass());
/*  723 */       arrayOfString[1] = "ABR";
/*  724 */       str3 = messageFormat.format(arrayOfString);
/*      */       
/*  726 */       setReturnCode(0);
/*      */       
/*      */       try {
/*  729 */         String str = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_chunksize", "500");
/*  730 */         this.chunkSize = Integer.parseInt(str.trim());
/*      */       }
/*  732 */       catch (Exception exception) {
/*      */         
/*  734 */         this.chunkSize = 500;
/*      */       } 
/*      */       
/*  737 */       start_ABRBuild(false);
/*      */       
/*  739 */       this
/*  740 */         .abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */ 
/*      */ 
/*      */       
/*  744 */       this.m_elist = getEntityList(getT002ModelVEName());
/*      */       
/*  746 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  748 */       if (this.m_elist.getEntityGroupCount() > 0)
/*      */       {
/*      */         
/*  751 */         this.navName = getNavigationName();
/*  752 */         setDGTitle(this.navName);
/*  753 */         setDGString(getABRReturnCode());
/*  754 */         setDGRptName("QSMFULLABRSTATUS");
/*  755 */         setDGRptClass(getABRCode());
/*  756 */         generateFlatFile(entityItem);
/*  757 */         exeFtpShell(this.ffPathName);
/*      */       }
/*      */     
/*  760 */     } catch (Exception exception) {
/*      */       
/*  762 */       exception.printStackTrace();
/*      */       
/*  764 */       setReturnCode(-1);
/*  765 */       StringWriter stringWriter = new StringWriter();
/*  766 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  767 */       String str6 = "<pre>{0}</pre>";
/*  768 */       MessageFormat messageFormat = new MessageFormat(str5);
/*  769 */       setReturnCode(-3);
/*  770 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  772 */       arrayOfString[0] = exception.getMessage();
/*  773 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  774 */       messageFormat = new MessageFormat(str6);
/*  775 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  776 */       this.rptSb.append(messageFormat.format(arrayOfString) + NEWLINE);
/*  777 */       logError("Exception: " + exception.getMessage());
/*  778 */       logError(stringWriter.getBuffer().toString());
/*      */       
/*  780 */       setCreateDGEntity(true);
/*  781 */       bool1 = false;
/*      */     } finally {
/*      */       
/*  784 */       if (!isReadOnly()) {
/*  785 */         clearSoftLock();
/*      */       }
/*      */       
/*  788 */       StringBuffer stringBuffer = new StringBuffer();
/*  789 */       MessageFormat messageFormat = new MessageFormat(str2);
/*  790 */       arrayOfString[0] = this.m_prof.getOPName();
/*  791 */       arrayOfString[1] = this.m_prof.getRoleDescription();
/*  792 */       arrayOfString[2] = this.m_prof.getWGName();
/*  793 */       arrayOfString[3] = getNow();
/*  794 */       stringBuffer.append(bool1 ? "generated the QSM report file successful " : "generated the QSM report file faild");
/*  795 */       stringBuffer.append(",");
/*  796 */       stringBuffer.append(bool2 ? "send the QSM report file successful " : "sent the QSM report file faild");
/*  797 */       arrayOfString[4] = stringBuffer.toString();
/*  798 */       arrayOfString[5] = str4 + " " + getABRVersion();
/*      */       
/*  800 */       this.rptSb.insert(0, str3 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */       
/*  802 */       println(EACustom.getDocTypeHtml());
/*  803 */       println(this.rptSb.toString());
/*  804 */       printDGSubmitString();
/*      */       
/*  806 */       println(EACustom.getTOUDiv());
/*  807 */       buildReportFooter();
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
/*  818 */     this.fileprefix = ABRServerProperties.getFilePrefix(this.m_abri.getABRCode());
/*      */ 
/*      */     
/*  821 */     StringBuffer stringBuffer = new StringBuffer(this.fileprefix.trim());
/*  822 */     String str = getNow();
/*      */     
/*  824 */     str = str.replace(' ', '_');
/*  825 */     stringBuffer.append(paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "_");
/*  826 */     stringBuffer.append(str + ".txt");
/*  827 */     this.dir = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_genpath", "/Dgq");
/*  828 */     if (!this.dir.endsWith("/")) {
/*  829 */       this.dir += "/";
/*      */     }
/*  831 */     this.ffFileName = stringBuffer.toString();
/*  832 */     this.ffPathName = this.dir + this.ffFileName;
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
/*      */   private void generateFlatFile(EntityItem paramEntityItem) throws IOException, SQLException, MiddlewareException, ParseException {
/*  847 */     FileChannel fileChannel1 = null;
/*  848 */     FileChannel fileChannel2 = null;
/*      */ 
/*      */     
/*  851 */     setFileName(paramEntityItem);
/*      */     
/*  853 */     FileOutputStream fileOutputStream = new FileOutputStream(this.ffPathName);
/*      */ 
/*      */ 
/*      */     
/*  857 */     OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
/*      */ 
/*      */     
/*  860 */     long l = System.currentTimeMillis();
/*      */     
/*  862 */     addDebug("Processing MODEL:" + (new Date()).toLocaleString());
/*  863 */     createT002Model(paramEntityItem, outputStreamWriter);
/*  864 */     addDebug("Processing T006Feature" + (new Date()).toLocaleString());
/*  865 */     createT006Feature(paramEntityItem, outputStreamWriter);
/*  866 */     addDebug("Processing T017ProductCategory" + (new Date()).toLocaleString());
/*  867 */     createT017ProductCategory(paramEntityItem, outputStreamWriter);
/*  868 */     addDebug("Processing T020NPMesUpgrade" + (new Date()).toLocaleString());
/*  869 */     createT020NPMesUpgrade(paramEntityItem, outputStreamWriter);
/*  870 */     addDebug("Processing 512ReleaseTo" + (new Date()).toLocaleString());
/*  871 */     createT512ReleaseTo(paramEntityItem, outputStreamWriter);
/*  872 */     addDebug("Processing TypeModelFeatureRelation" + (new Date()).toLocaleString());
/*  873 */     createT632TypeModelFeatureRelation(paramEntityItem, outputStreamWriter);
/*  874 */     addDebug("Processing Done" + (new Date()).toLocaleString());
/*  875 */     outputStreamWriter.close();
/*      */     
/*  877 */     this.dirDest = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_ftppath", "/Dgq");
/*  878 */     if (!this.dirDest.endsWith("/")) {
/*  879 */       this.dirDest += "/";
/*      */     }
/*      */     
/*  882 */     this.ffFTPPathName = this.dirDest + this.ffFileName;
/*  883 */     addDebug("******* " + this.ffFTPPathName);
/*      */     
/*      */     try {
/*  886 */       fileChannel1 = (new FileInputStream(this.ffPathName)).getChannel();
/*  887 */       fileChannel2 = (new FileOutputStream(this.ffFTPPathName)).getChannel();
/*  888 */       fileChannel2.transferFrom(fileChannel1, 0L, fileChannel1.size());
/*      */     } finally {
/*  890 */       fileChannel1.close();
/*  891 */       fileChannel2.close();
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
/*      */   private void createT002Model(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException {
/*  903 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*  904 */     String str1 = "";
/*  905 */     String str2 = "";
/*  906 */     boolean bool = false;
/*      */     
/*  908 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*      */       
/*  910 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/*  912 */       str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*  913 */       str2 = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/*  914 */       if (str2.equals("EPIC")) {
/*  915 */         bool = true;
/*      */       }
/*      */ 
/*      */       
/*  919 */       if (str1.equals("Planned Availability") || str1.equals("End of Service") || str1
/*  920 */         .equals("Last Order")) {
/*  921 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/*      */         
/*  923 */         if (eANFlagAttribute != null) {
/*  924 */           if (eANFlagAttribute.isSelected("6199")) {
/*  925 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", str1, bool);
/*      */           }
/*  927 */           if (eANFlagAttribute.isSelected("6200")) {
/*  928 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", str1, bool);
/*      */           }
/*      */           
/*  931 */           if (eANFlagAttribute.isSelected("6198")) {
/*  932 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", str1, bool);
/*      */           }
/*      */           
/*  935 */           if (eANFlagAttribute.isSelected("6204")) {
/*  936 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", str1, bool);
/*      */           }
/*  938 */           if (eANFlagAttribute.isSelected("6221")) {
/*  939 */             createT002ModelRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", str1, bool);
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
/*      */ 
/*      */   
/*      */   private void createT002ModelRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString1, String paramString2, boolean paramBoolean) throws IOException {
/*  999 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELAVAIL", "MODEL");
/*      */     
/* 1001 */     for (byte b = 0; b < vector.size(); b++) {
/* 1002 */       StringBuffer stringBuffer = new StringBuffer();
/* 1003 */       String str1 = "";
/* 1004 */       String str2 = "";
/* 1005 */       String str3 = "";
/* 1006 */       String str4 = "";
/* 1007 */       String str5 = "";
/* 1008 */       String str6 = "";
/* 1009 */       String str7 = "";
/* 1010 */       String str8 = "";
/* 1011 */       String str9 = "";
/* 1012 */       String str10 = "";
/* 1013 */       String str11 = "";
/* 1014 */       String str12 = "";
/* 1015 */       String str13 = "";
/* 1016 */       String str14 = "";
/* 1017 */       String str15 = "";
/* 1018 */       String str16 = "";
/* 1019 */       String str17 = "";
/* 1020 */       String str18 = "";
/* 1021 */       String str19 = "";
/* 1022 */       String str20 = "";
/* 1023 */       String str21 = "";
/* 1024 */       String str22 = "";
/* 1025 */       String str23 = "";
/* 1026 */       String str24 = "";
/* 1027 */       String str25 = "";
/* 1028 */       String str26 = "";
/* 1029 */       String str27 = "";
/* 1030 */       String str28 = "";
/* 1031 */       String str29 = "";
/* 1032 */       String str30 = "";
/* 1033 */       String str31 = "";
/* 1034 */       String str32 = "";
/*      */       
/* 1036 */       String str33 = "";
/* 1037 */       String str34 = "";
/* 1038 */       String str35 = "";
/* 1039 */       String str36 = "";
/* 1040 */       String str37 = "";
/* 1041 */       String str38 = "";
/* 1042 */       String str39 = "";
/* 1043 */       String str40 = "";
/* 1044 */       String str41 = "";
/* 1045 */       String str42 = "";
/*      */       
/* 1047 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/* 1049 */       EntityItem entityItem2 = null;
/*      */       
/* 1051 */       if (paramString1.equals("Asia Pacific") || paramString1.equals("US Only") || paramString1
/* 1052 */         .equals("Canada and Caribbean North")) {
/* 1053 */         str23 = "N";
/*      */       } else {
/*      */         
/* 1056 */         Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "MODELSTDMAINT", "STDMAINT");
/* 1057 */         if (!vector3.isEmpty()) {
/* 1058 */           EntityItem entityItem = vector3.elementAt(0);
/* 1059 */           if (entityItem != null) {
/* 1060 */             str23 = PokUtils.getAttributeValue(entityItem, "MAINTELIG", "", "");
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1070 */       stringBuffer.append(getValue("IFTYPE", "M"));
/*      */       
/* 1072 */       String str43 = "";
/* 1073 */       String str44 = "";
/* 1074 */       String str45 = "";
/* 1075 */       String str46 = "";
/* 1076 */       String str47 = "";
/* 1077 */       String str48 = "";
/* 1078 */       String str49 = "";
/* 1079 */       String str50 = "";
/* 1080 */       String str51 = "";
/* 1081 */       String str52 = "";
/* 1082 */       String str53 = "";
/* 1083 */       EntityItem entityItem3 = null;
/* 1084 */       String str54 = "";
/* 1085 */       String str55 = "";
/*      */       
/* 1087 */       List<EntityItem> list = searchForAvailType(entityItem1, "Last Order");
/* 1088 */       for (byte b1 = 0; b1 < list.size(); b1++) {
/* 1089 */         entityItem3 = list.get(b1);
/* 1090 */         addDebug("*****avail list= " + entityItem3);
/* 1091 */         if (entityItem3 != null) {
/* 1092 */           str43 = PokUtils.getAttributeValue(entityItem3, "GENAREASELECTION", ",", "");
/* 1093 */           addDebug("*****GenArea list= " + str43);
/* 1094 */           if (str43.indexOf("Latin America") != -1) {
/* 1095 */             str44 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1096 */             str45 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1097 */             str37 = "O";
/* 1098 */             addDebug("*****WD date LA= " + str44);
/* 1099 */             addDebug("*****WD date LA= " + str45);
/*      */           } 
/* 1101 */           if (str43.indexOf("Europe/Middle East/Africa") != -1) {
/* 1102 */             str46 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1103 */             str47 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1104 */             str37 = "O";
/* 1105 */             addDebug("*****WD date EMEA= " + str46);
/* 1106 */             addDebug("*****WD date EMEA= " + str47);
/*      */           } 
/* 1108 */           if (str43.indexOf("Asia Pacific") != -1) {
/* 1109 */             str48 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1110 */             str49 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1111 */             str37 = "O";
/* 1112 */             addDebug("*****WD date AP= " + str48);
/* 1113 */             addDebug("*****WD date AP= " + str49);
/*      */           } 
/* 1115 */           if (str43.indexOf("US Only") != -1) {
/* 1116 */             str50 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1117 */             str51 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1118 */             str37 = "O";
/* 1119 */             addDebug("*****WD date US= " + str50);
/* 1120 */             addDebug("*****WD date US= " + str51);
/*      */           } 
/* 1122 */           if (str43.indexOf("Canada and Caribbean North") != -1) {
/* 1123 */             str52 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1124 */             str53 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1125 */             str37 = "O";
/* 1126 */             addDebug("*****WD date CCN= " + str52);
/* 1127 */             addDebug("*****WD date US= " + str53);
/*      */           }
/* 1129 */           else if (str43.indexOf("Worldwide") != -1) {
/* 1130 */             str44 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1131 */             str46 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1132 */             str48 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1133 */             str50 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1134 */             str52 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1135 */             str45 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1136 */             str47 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1137 */             str49 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1138 */             str51 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1139 */             str53 = PokUtils.getAttributeValue(entityItem3, "EFFECTIVEDATE", "", "");
/* 1140 */             str37 = "O";
/*      */           } 
/*      */         } else {
/* 1143 */           str35 = "2050-12-31";
/* 1144 */           str36 = "2050-12-31";
/* 1145 */           str37 = "N";
/*      */         } 
/*      */       } 
/*      */       
/* 1149 */       if (paramString1.equals("Latin America")) {
/* 1150 */         str9 = "601";
/* 1151 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/*      */         
/* 1153 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 1154 */         str55 = str44;
/* 1155 */         addDebug("*****WD LA= " + str55);
/* 1156 */         str54 = str45;
/* 1157 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1158 */         str9 = "999";
/* 1159 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/*      */         
/* 1161 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 1162 */         str55 = str46;
/* 1163 */         addDebug("*****WD EMEA= " + str55);
/* 1164 */         str54 = str47;
/* 1165 */       } else if (paramString1.equals("Asia Pacific")) {
/* 1166 */         str9 = "872";
/* 1167 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/*      */         
/* 1169 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 1170 */         str55 = str48;
/* 1171 */         addDebug("*****WD AP= " + str55);
/* 1172 */         str54 = str49;
/* 1173 */       } else if (paramString1.equals("US Only")) {
/* 1174 */         str9 = "897";
/* 1175 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */         
/* 1177 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 1178 */         str55 = str50;
/* 1179 */         addDebug("*****WD US= " + str55);
/* 1180 */         str54 = str51;
/*      */       }
/* 1182 */       else if (paramString1.equals("Canada and Caribbean North")) {
/* 1183 */         str9 = "649";
/* 1184 */         str10 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/*      */         
/* 1186 */         str28 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 1187 */         str55 = str52;
/* 1188 */         addDebug("*****WD CCN= " + str55);
/* 1189 */         str54 = str53;
/*      */       } 
/*      */       
/* 1192 */       stringBuffer.append(getValue("IOPUCTY", str9));
/*      */       
/* 1194 */       stringBuffer.append(getValue("ISLMPAL", str10));
/* 1195 */       stringBuffer.append(getValue("ISLMRFA", str28));
/* 1196 */       String str56 = PokUtils.getAttributeValue(entityItem1, "MACHTYPEATR", "", "");
/* 1197 */       str56 = str56 + PokUtils.getAttributeValue(entityItem1, "MODELATR", "", "");
/* 1198 */       stringBuffer.append(getValue("ISLMPRN", str56));
/* 1199 */       stringBuffer.append(getValue("CSLMPCI", "MM"));
/* 1200 */       stringBuffer.append(getValue("IPRTNUM", "            "));
/* 1201 */       stringBuffer.append(getValue("FPUNINC", "2"));
/* 1202 */       stringBuffer.append(getValue("CAOAV", ""));
/*      */       
/* 1204 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/* 1205 */       stringBuffer.append(getValue("DSLMCPO", ""));
/* 1206 */       stringBuffer.append(getValue("DSLMGAD", PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false)));
/* 1207 */       stringBuffer.append(getValue("DSLMMVA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1221 */       if (str54 != "") {
/* 1222 */         str36 = str54;
/*      */       } else {
/* 1224 */         str36 = "2050-12-31";
/*      */       } 
/*      */       
/* 1227 */       if (str55 != "") {
/* 1228 */         str35 = str55;
/*      */       } else {
/* 1230 */         str35 = "2050-12-31";
/*      */       } 
/* 1232 */       stringBuffer.append(getValue("DSLMOPD", str36));
/* 1233 */       stringBuffer.append(getValue("DSLMWDN", str35));
/* 1234 */       addDebug("*****= strDSLMWDN" + str35);
/*      */       
/* 1236 */       entityItem2 = searchForAvailType2(entityItem1, "End of Service");
/* 1237 */       if (entityItem2 != null) {
/* 1238 */         str33 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/* 1239 */         str34 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/*      */       } else {
/* 1241 */         str33 = "2050-12-31";
/* 1242 */         str34 = "2050-12-31";
/*      */       } 
/*      */       
/* 1245 */       stringBuffer.append(getValue("QSMEDMW", str33));
/*      */       
/* 1247 */       stringBuffer.append(getValue("ASLMMVP", "01.0"));
/* 1248 */       str2 = PokUtils.getAttributeValue(entityItem1, "ICRCATEGORY", "", "");
/* 1249 */       stringBuffer.append(getValue("CCUOICR", str2));
/* 1250 */       stringBuffer.append(getValue("CICIB", "N"));
/* 1251 */       stringBuffer.append(getValue("CICIC", "N"));
/* 1252 */       stringBuffer.append(getValue("CICRY", "N"));
/* 1253 */       stringBuffer.append(getValue("CIDCJ", "N"));
/* 1254 */       stringBuffer.append(getValue("CIDXF", PokUtils.getAttributeValue(entityItem1, "LICNSINTERCD", "", "")));
/*      */       
/* 1256 */       String str57 = "";
/* 1257 */       String str58 = "";
/* 1258 */       EntityItem entityItem4 = null;
/* 1259 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "MODELGEOMOD", "GEOMOD");
/* 1260 */       if (vector1.size() > 0) {
/* 1261 */         for (int i = 0; i < vector1.size(); i++) {
/* 1262 */           entityItem4 = vector1.elementAt(i);
/* 1263 */           str58 = PokUtils.getAttributeValue(entityItem4, "GENAREASELECTION", "", "");
/* 1264 */           if (str58.equals(paramString1)) {
/* 1265 */             str57 = PokUtils.getAttributeValue(entityItem4, "NOCHRGRENT", "", "");
/* 1266 */             str1 = PokUtils.getAttributeValue(entityItem4, "GRADUATEDCHARGE", "", "");
/* 1267 */             str3 = PokUtils.getAttributeValue(entityItem4, "PURCHONLY", "", "");
/* 1268 */             str4 = PokUtils.getAttributeValue(entityItem4, "PLNTOFMFR", "", "");
/* 1269 */             str5 = PokUtils.getAttributeValue(entityItem4, "INTEGRATEDMODEL", "", "");
/* 1270 */             str6 = PokUtils.getAttributeValue(entityItem4, "PERCALLCLS", "", "");
/* 1271 */             str8 = PokUtils.getAttributeValue(entityItem4, "EMEABRANDCD", "", "");
/* 1272 */             str7 = PokUtils.getAttributeValue(entityItem4, "ANNUALMAINT", "", "");
/* 1273 */             str31 = PokUtils.getAttributeValue(entityItem4, "METHODPROD", "", "");
/* 1274 */             str29 = PokUtils.getAttributeValue(entityItem4, "EDUCPURCHELIG", "", "");
/*      */             
/* 1276 */             i = vector1.size();
/*      */           } else {
/* 1278 */             entityItem4 = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 1283 */       stringBuffer.append(getValue("CINCA", str57));
/*      */       
/* 1285 */       str27 = PokUtils.getAttributeValue(entityItem1, "PRCINDC", "", "");
/* 1286 */       if (str27.equals("Yes")) {
/* 1287 */         stringBuffer.append(getValue("CINCB", "N"));
/* 1288 */       } else if (str27.equals("No")) {
/* 1289 */         stringBuffer.append(getValue("CINCB", "Y"));
/*      */       } else {
/* 1291 */         stringBuffer.append(getValue("CINCB", "N"));
/*      */       } 
/*      */       
/* 1294 */       stringBuffer.append(getValue("CINCC", "N"));
/* 1295 */       stringBuffer.append(getValue("CINPM", PokUtils.getAttributeValue(entityItem1, "NETPRICEMES", "", "")));
/*      */       
/* 1297 */       stringBuffer.append(getValue("CISUP", "N"));
/*      */       
/* 1299 */       stringBuffer.append(getValue("CITEM", "N"));
/* 1300 */       String str59 = PokUtils.getAttributeValue(paramEntityItem1, "INDDEFNCATG", ",", "", false);
/* 1301 */       if (str59.length() >= 2) {
/* 1302 */         stringBuffer.append(getValue("CJLBIC1", str59.substring(0, 2)));
/*      */       } else {
/* 1304 */         stringBuffer.append(getValue("CJLBIC1", ""));
/*      */       } 
/* 1306 */       if (str59.length() >= 3) {
/* 1307 */         stringBuffer.append(getValue("CJLBIDS", str59.substring(2)));
/*      */       } else {
/* 1309 */         stringBuffer.append(getValue("CJLBIDS", ""));
/*      */       } 
/* 1311 */       stringBuffer.append(getValue("CJLBOEM", PokUtils.getAttributeValue(entityItem1, "SPECMODDESGN", "", "")));
/*      */       
/* 1313 */       stringBuffer.append(getValue("CJLBPOF", ""));
/*      */       
/* 1315 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem1, "MODELSGMTACRONYMA", "SGMNTACRNYM");
/* 1316 */       if (!vector2.isEmpty()) {
/* 1317 */         EntityItem entityItem = vector2.elementAt(0);
/* 1318 */         if (entityItem != null) {
/* 1319 */           stringBuffer.append(getValue("CJLBSAC", PokUtils.getAttributeValue(entityItem, "ACRNYM", "", "")));
/*      */         } else {
/* 1321 */           stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */         } 
/*      */       } else {
/* 1324 */         stringBuffer.append(getValue("CJLBSAC", "   "));
/*      */       } 
/*      */       
/* 1327 */       stringBuffer.append(getValue("CLASSPT", "IHW"));
/*      */       
/* 1329 */       if (paramString2.equals("Last Order")) {
/* 1330 */         stringBuffer.append(getValue("CPDAA", "O"));
/*      */       } else {
/* 1332 */         stringBuffer.append(getValue("CPDAA", "N"));
/*      */       } 
/*      */       
/* 1335 */       addDebug("*****= CPDAACPDAA");
/*      */       
/* 1337 */       stringBuffer.append(getValue("CSLMFCC", PokUtils.getAttributeValue(entityItem1, "FUNCCLS", "", "")));
/*      */       
/* 1339 */       if (paramString1.equals("Asia Pacific")) {
/* 1340 */         stringBuffer.append(getValue("CSLMGGC", str1));
/*      */       } else {
/* 1342 */         stringBuffer.append(getValue("CSLMGGC", " "));
/*      */       } 
/*      */       
/* 1345 */       String str60 = PokUtils.getAttributeValue(entityItem1, "PRODID", "", "");
/* 1346 */       if (str60.equals("0-CPU")) {
/* 1347 */         str30 = "0";
/* 1348 */       } else if (str60.equals("1-Unit Record Equipm.")) {
/* 1349 */         str30 = "1";
/* 1350 */       } else if (str60.equals("2-System Component")) {
/* 1351 */         str30 = "2";
/* 1352 */       } else if (str60.equals("3-Stand Alone Material")) {
/* 1353 */         str30 = "3";
/* 1354 */       } else if (str60.equals("4-System Control")) {
/* 1355 */         str30 = "4";
/* 1356 */       } else if (str60.equals("5-Program Product")) {
/* 1357 */         str30 = "5";
/* 1358 */       } else if (str60.equals("6-Special Program")) {
/* 1359 */         str30 = "6";
/* 1360 */       } else if (str60.equals("7-Control Unit")) {
/* 1361 */         str30 = "7";
/* 1362 */       } else if (str60.equals("8-Disk Packs")) {
/* 1363 */         str30 = "8";
/*      */       } else {
/* 1365 */         str30 = "";
/*      */       } 
/*      */       
/* 1368 */       stringBuffer.append(getValue("CSLMIDP", str30));
/* 1369 */       stringBuffer.append(getValue("CSLMLRP", "0"));
/* 1370 */       stringBuffer.append(getValue("CSLMSAS", "0"));
/* 1371 */       stringBuffer.append(getValue("CSLMSYT", PokUtils.getAttributeValue(entityItem1, "SYSTEMTYPE", "", "")));
/*      */       
/* 1373 */       EntityItem entityItem5 = null;
/* 1374 */       str40 = PokUtils.getAttributeValue(entityItem1, "WARRSVCCOVR", "", "");
/* 1375 */       if (str40 != null) {
/* 1376 */         if (str40.equals("No Warranty") || str40.equals("")) {
/* 1377 */           str39 = "Z";
/*      */         } else {
/* 1379 */           Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem1, "MODELWARR", "WARR");
/* 1380 */           if (!vector3.isEmpty()) {
/* 1381 */             entityItem5 = vector3.elementAt(0);
/* 1382 */             if (entityItem5 != null) {
/* 1383 */               String str = PokUtils.getAttributeValue(entityItem5, "WARRID", "", "");
/* 1384 */               if (str.equals("WTY0000")) {
/* 1385 */                 if (vector3.size() > 1) {
/* 1386 */                   entityItem5 = vector3.elementAt(1);
/*      */                 } else {
/* 1388 */                   entityItem5 = null;
/*      */                 } 
/*      */               }
/*      */             } 
/*      */             
/* 1393 */             if (entityItem5 != null) {
/* 1394 */               str39 = PokUtils.getAttributeValue(entityItem5, "WARRCATG", "", "");
/*      */             } else {
/* 1396 */               str39 = "";
/*      */             } 
/*      */           } else {
/* 1399 */             str39 = "";
/*      */           } 
/*      */         } 
/*      */       } else {
/* 1403 */         str39 = "Z";
/*      */       } 
/* 1405 */       stringBuffer.append(getValue("CSLMWCD", str39));
/*      */       
/* 1407 */       stringBuffer.append(getValue("FAGRMBE", str23));
/*      */       
/* 1409 */       if (str2.equals("1") || str2.equals("2")) {
/* 1410 */         stringBuffer.append(getValue("FCUOCNF", "N"));
/* 1411 */       } else if (str2.equals("3")) {
/* 1412 */         stringBuffer.append(getValue("FCUOCNF", "Y"));
/*      */       } else {
/* 1414 */         stringBuffer.append(getValue("FCUOCNF", "N"));
/*      */       } 
/*      */       
/* 1417 */       stringBuffer.append(getValue("FSLMCLS", "N"));
/*      */       
/* 1419 */       String str61 = PokUtils.getAttributeValue(entityItem1, "SYSIDUNIT", "", "");
/* 1420 */       if (str61.equals("SIU-CPU")) {
/* 1421 */         stringBuffer.append(getValue("FSLMCPU", "Y"));
/*      */       } else {
/* 1423 */         stringBuffer.append(getValue("FSLMCPU", "N"));
/*      */       } 
/*      */       
/* 1426 */       stringBuffer.append(getValue("FSLMIOP", str5));
/* 1427 */       stringBuffer.append(getValue("FSLMLGS", "N"));
/* 1428 */       stringBuffer.append(getValue("FSLMMLC", PokUtils.getAttributeValue(entityItem1, "MACHLVLCNTRL", "", "")));
/*      */       
/* 1430 */       if (paramString1.equals("Latin America") || paramString1.equals("US Only") || paramString1
/* 1431 */         .equals("Canada and Caribbean North")) {
/* 1432 */         stringBuffer.append(getValue("FSLMPOP", "No"));
/* 1433 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1434 */         stringBuffer.append(getValue("FSLMPOP", "Yes"));
/* 1435 */       } else if (paramString1.equals("Asia Pacific")) {
/* 1436 */         if (str27.equals("Yes")) {
/* 1437 */           stringBuffer.append(getValue("FSLMPOP", "Y"));
/* 1438 */         } else if (str27.equals("No")) {
/* 1439 */           stringBuffer.append(getValue("FSLMPOP", "N"));
/*      */         } else {
/* 1441 */           stringBuffer.append(getValue("FSLMPOP", " "));
/*      */         } 
/*      */       } else {
/* 1444 */         stringBuffer.append(getValue("FSLMPOP", " "));
/*      */       } 
/*      */       
/* 1447 */       stringBuffer.append(getValue("FSLMVDE", PokUtils.getAttributeValue(entityItem1, "VOLUMEDISCOUNTELIG", "", "")));
/* 1448 */       stringBuffer.append(getValue("FSLMVTS", "N"));
/*      */       
/* 1450 */       ArrayList<String> arrayList = new ArrayList();
/* 1451 */       if (entityItem5 != null) {
/* 1452 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem5.getAttribute("WARRTYPE");
/* 1453 */         if (eANFlagAttribute != null) {
/* 1454 */           if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1455 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1456 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 1457 */               .isSelected("W0250")) {
/* 1458 */               str11 = "Y";
/*      */             } else {
/* 1460 */               str11 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1464 */           if (paramString1.equals("Latin America")) {
/* 1465 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 1466 */               .isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute
/* 1467 */               .isSelected("W0580")) {
/* 1468 */               str11 = "Y";
/*      */             } else {
/* 1470 */               str11 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1474 */           if (paramString1.equals("Asia Pacific")) {
/* 1475 */             if (eANFlagAttribute.isSelected("W0550") || eANFlagAttribute.isSelected("W0390") || eANFlagAttribute
/* 1476 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 1477 */               .isSelected("W0250") || eANFlagAttribute.isSelected("W0310") || eANFlagAttribute
/* 1478 */               .isSelected("W0330") || eANFlagAttribute.isSelected("W0590")) {
/* 1479 */               str11 = "Y";
/*      */             } else {
/* 1481 */               str11 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 1485 */           if (paramString1.equals("Canada and Caribbean North") || paramString1
/* 1486 */             .equals("US Only")) {
/* 1487 */             str11 = "N";
/*      */           }
/*      */         } 
/*      */       } else {
/* 1491 */         str11 = "N";
/*      */       } 
/* 1493 */       stringBuffer.append(getValue("FSLM2CF", str11));
/*      */       
/* 1495 */       stringBuffer.append(getValue("ICESPCC", str6));
/* 1496 */       stringBuffer.append(getValue("IDORIG", "IBM"));
/* 1497 */       stringBuffer.append(getValue("IOLCPLM", str4));
/*      */       
/* 1499 */       str24 = "000";
/* 1500 */       str25 = "000";
/* 1501 */       str26 = "000";
/*      */       
/* 1503 */       if (entityItem4 != null && (
/* 1504 */         paramString1.equals("Latin America") || paramString1.equals("Asia Pacific") || paramString1
/* 1505 */         .equals("Canada and Caribbean North"))) {
/* 1506 */         str24 = getNumValue("PCUAHEA", 
/* 1507 */             PokUtils.getAttributeValue(entityItem4, "EDUCALLOWMHGHSCH", ",", "", false));
/* 1508 */         str26 = getNumValue("PCUAUEA", 
/* 1509 */             PokUtils.getAttributeValue(entityItem4, "EDUCALLOWMUNVRSTY", ",", "", false));
/* 1510 */         str25 = getNumValue("PCUASEA", 
/* 1511 */             PokUtils.getAttributeValue(entityItem4, "EDUCALLOWMSECONDRYSCH", ",", "", false));
/*      */       } 
/*      */ 
/*      */       
/* 1515 */       stringBuffer.append(getValue("PCUAHEA", str24));
/* 1516 */       stringBuffer.append(getValue("PCUASEA", str25));
/* 1517 */       stringBuffer.append(getValue("PCUAUEA", str26));
/*      */       
/* 1519 */       String str62 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/* 1520 */       if (paramString1.equals("Latin America")) {
/* 1521 */         if (str62.equals("CIF")) {
/* 1522 */           str12 = "01";
/* 1523 */         } else if (str62.equals("CE") || str62.equals("N/A") || str62.equals("Does not apply")) {
/* 1524 */           str12 = "";
/*      */         } 
/* 1526 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 1527 */         if (str62.equals("CIF")) {
/* 1528 */           str12 = "01";
/* 1529 */         } else if (str62.equals("CE") || str62.equals("N/A") || str62.equals("Does not apply")) {
/* 1530 */           str12 = "";
/*      */         } 
/* 1532 */       } else if (paramString1.equals("Asia Pacific")) {
/* 1533 */         if (str62.equals("CIF")) {
/* 1534 */           str12 = "10";
/* 1535 */         } else if (str62.equals("CE") || str62.equals("N/A") || str62.equals("Does not apply")) {
/* 1536 */           str12 = "";
/*      */         } 
/* 1538 */       } else if (paramString1.equals("US Only")) {
/* 1539 */         if (str62.equals("CIF")) {
/* 1540 */           str12 = "01";
/* 1541 */         } else if (str62.equals("CE") || str62.equals("N/A") || str62.equals("Does not apply")) {
/* 1542 */           str12 = "00";
/*      */         } 
/* 1544 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/* 1545 */         if (str62.equals("CIF")) {
/* 1546 */           str12 = "01";
/* 1547 */         } else if (str62.equals("CE") || str62.equals("N/A") || str62.equals("Does not apply")) {
/* 1548 */           str12 = "";
/*      */         } 
/*      */       } 
/* 1551 */       stringBuffer.append(getValue("QSLMCSU", str12));
/*      */       
/* 1553 */       stringBuffer.append(getValue("QSMXANN", str7));
/* 1554 */       stringBuffer.append(getValue("QSMXESA", "N"));
/* 1555 */       stringBuffer.append(getValue("QSMXSSA", "N"));
/*      */       
/* 1557 */       if (str61.equals("SIU-CPU") || str61.equals("U-System Unit")) {
/* 1558 */         stringBuffer.append(getValue("SYSDES", PokUtils.getAttributeValue(entityItem1, "MODMKTGDESC", "", "")));
/*      */       } else {
/* 1560 */         stringBuffer.append(getValue("SYSDES", "   "));
/*      */       } 
/*      */       
/* 1563 */       String str63 = PokUtils.getAttributeValue(entityItem1, "INVNAME", "", "");
/* 1564 */       stringBuffer.append(getValue("TSLMDES", removeSpecialChars(str63)));
/* 1565 */       stringBuffer.append(getValue("TSLTDES", " "));
/* 1566 */       stringBuffer.append(getValue("TIMSTMP", " "));
/* 1567 */       stringBuffer.append(getValue("USERID", " "));
/* 1568 */       stringBuffer.append(getValue("FBRAND", str8));
/*      */       
/* 1570 */       if (str31.equals("BTP")) {
/* 1571 */         str32 = "Y";
/* 1572 */       } else if (str31.equals("BTO")) {
/* 1573 */         str32 = "N";
/*      */       } else {
/* 1575 */         str32 = "";
/*      */       } 
/*      */       
/* 1578 */       stringBuffer.append(getValue("FSLMHVP", str32));
/*      */       
/* 1580 */       if (paramString1.equals("US Only")) {
/* 1581 */         str38 = "Y";
/* 1582 */       } else if (paramString1.equals("Latin America") || paramString1
/* 1583 */         .equals("Europe/Middle East/Africa") || paramString1
/* 1584 */         .equals("Asia Pacific") || paramString1
/* 1585 */         .equals("Canada and Caribbean North")) {
/* 1586 */         if (str31.equals("BTO")) {
/* 1587 */           str38 = "Y";
/* 1588 */         } else if (str31.equals("BTP")) {
/* 1589 */           str38 = "N";
/*      */         } else {
/* 1591 */           str38 = " ";
/*      */         } 
/*      */       } 
/*      */       
/* 1595 */       stringBuffer.append(getValue("FSLMCVP", str38));
/*      */       
/* 1597 */       stringBuffer.append(getValue("FSLMMES", "N"));
/*      */       
/* 1599 */       arrayList = new ArrayList();
/* 1600 */       str15 = "";
/* 1601 */       str16 = "";
/* 1602 */       str17 = "";
/* 1603 */       str18 = "";
/* 1604 */       str19 = "";
/* 1605 */       str20 = "";
/* 1606 */       str21 = "";
/* 1607 */       str22 = "";
/*      */       
/* 1609 */       if (entityItem5 != null) {
/* 1610 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem5.getAttribute("WARRTYPE");
/* 1611 */         if (eANFlagAttribute != null) {
/* 1612 */           if (eANFlagAttribute.isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute
/* 1613 */             .isSelected("W0580")) {
/* 1614 */             arrayList.add("IOR");
/*      */           }
/* 1616 */           if (eANFlagAttribute.isSelected("W0550")) {
/* 1617 */             arrayList.add("IOE");
/*      */           }
/* 1619 */           if (eANFlagAttribute.isSelected("W0390")) {
/* 1620 */             arrayList.add("COE");
/*      */           }
/* 1622 */           if (eANFlagAttribute.isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 1623 */             .isSelected("W0250")) {
/* 1624 */             arrayList.add("CCE");
/*      */           }
/* 1626 */           if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330")) {
/* 1627 */             arrayList.add("CCR");
/*      */           }
/* 1629 */           if (eANFlagAttribute.isSelected("W0590")) {
/* 1630 */             arrayList.add("IOS");
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
/* 1663 */           for (byte b2 = 0; b2 < arrayList.size(); b2++) {
/* 1664 */             if (b2 == 0) {
/* 1665 */               str15 = arrayList.get(b2);
/* 1666 */             } else if (b2 == 1) {
/* 1667 */               str16 = arrayList.get(b2);
/* 1668 */             } else if (b2 == 2) {
/* 1669 */               str17 = arrayList.get(b2);
/* 1670 */             } else if (b2 == 3) {
/* 1671 */               str18 = arrayList.get(b2);
/* 1672 */             } else if (b2 == 4) {
/* 1673 */               str19 = arrayList.get(b2);
/* 1674 */             } else if (b2 == 5) {
/* 1675 */               str20 = arrayList.get(b2);
/* 1676 */             } else if (b2 == 6) {
/* 1677 */               str21 = arrayList.get(b2);
/* 1678 */             } else if (b2 == 7) {
/* 1679 */               str22 = arrayList.get(b2);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1685 */       stringBuffer.append(getValue("CSLMTM1", str15));
/* 1686 */       stringBuffer.append(getValue("CSLMTM2", str16));
/* 1687 */       stringBuffer.append(getValue("CSLMTM3", str17));
/* 1688 */       stringBuffer.append(getValue("CSLMTM4", str18));
/* 1689 */       stringBuffer.append(getValue("CSLMTM5", str19));
/* 1690 */       stringBuffer.append(getValue("CSLMTM6", str20));
/* 1691 */       stringBuffer.append(getValue("CSLMTM7", str21));
/* 1692 */       stringBuffer.append(getValue("CSLMTM8", str22));
/* 1693 */       stringBuffer.append(getValue("FSAPRES", "N"));
/*      */       
/* 1695 */       if (paramString1.equals("US Only")) {
/* 1696 */         String str = PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false);
/* 1697 */         if (str.equals("Yes")) {
/* 1698 */           stringBuffer.append(getValue("CUSAPMS", "Y"));
/* 1699 */         } else if (str.equals("No")) {
/* 1700 */           stringBuffer.append(getValue("CUSAPMS", "X"));
/*      */         } else {
/* 1702 */           stringBuffer.append(getValue("CUSAPMS", ""));
/*      */         } 
/*      */       } else {
/* 1705 */         stringBuffer.append(getValue("CUSAPMS", ""));
/*      */       } 
/*      */       
/* 1708 */       stringBuffer.append(getValue("DUSALRW", str34));
/*      */       
/* 1710 */       stringBuffer.append(getValue("DUSAMDW", "2050-12-31"));
/* 1711 */       stringBuffer.append(getValue("DUSAWUW", "2050-12-31"));
/* 1712 */       if (paramString1.equals("US Only")) {
/* 1713 */         stringBuffer.append(getValue("FSLMCBL", "N"));
/*      */       } else {
/* 1715 */         stringBuffer.append(getValue("FSLMCBL", " "));
/*      */       } 
/* 1717 */       stringBuffer.append(getValue("FSLMMRR", "N"));
/* 1718 */       str13 = "";
/*      */       
/* 1720 */       if (paramString1.equals("US Only")) {
/* 1721 */         str13 = "N";
/*      */         
/* 1723 */         String str = PokUtils.getAttributeValue(paramEntityItem2, "ORDERSYSNAME", ",", "", false);
/* 1724 */         if (str.equals("AAS")) {
/* 1725 */           str13 = "Y";
/*      */         }
/*      */       } 
/* 1728 */       stringBuffer.append(getValue("FUSAAAS", str13));
/* 1729 */       stringBuffer.append(getValue("FUSAADM", "N"));
/*      */       
/* 1731 */       if (paramString1.equals("US Only")) {
/* 1732 */         stringBuffer.append(getValue("FUSAEDE", str29));
/*      */       } else {
/* 1734 */         stringBuffer.append(getValue("FUSAEDE", " "));
/*      */       } 
/*      */       
/* 1737 */       if (paramString1.equals("US Only")) {
/* 1738 */         str41 = PokUtils.getAttributeValue(entityItem1, "IBMCREDIT", ",", "", false);
/* 1739 */         addDebug("*****mlm IBMCREDIT=" + str41);
/* 1740 */         if (str41 != null) {
/* 1741 */           if (str41.equals("Yes")) {
/* 1742 */             str14 = "Y";
/* 1743 */           } else if (str41.equals("No")) {
/* 1744 */             str14 = "N";
/*      */           } 
/*      */         }
/* 1747 */         stringBuffer.append(getValue("FUSAICC", str14));
/*      */       } else {
/* 1749 */         stringBuffer.append(getValue("FUSAICC", " "));
/*      */       } 
/*      */       
/* 1752 */       if (paramString1.equals("US Only")) {
/* 1753 */         stringBuffer.append(
/* 1754 */             getValue("FUSALEP", PokUtils.getAttributeValue(entityItem1, "MAINTANNBILLELIGINDC", ",", "", false)));
/*      */       } else {
/* 1756 */         stringBuffer.append(getValue("FUSALEP", " "));
/*      */       } 
/*      */       
/* 1759 */       stringBuffer.append(getValue("FUSAMRS", "N"));
/* 1760 */       stringBuffer.append(getValue("FUSAVLM", "N"));
/* 1761 */       stringBuffer.append(getValue("FUSAXMO", "N"));
/* 1762 */       stringBuffer.append(getValue("QUSAPOP", "00.0"));
/* 1763 */       stringBuffer.append(getValue("DSLMEOD", "1950-01-01"));
/* 1764 */       stringBuffer.append(getValue("FSLMRFM", " "));
/*      */       
/* 1766 */       stringBuffer.append(NEWLINE);
/* 1767 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 1768 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String removeSpecialChars(String paramString) {
/* 1775 */     String str = "";
/* 1776 */     str = paramString.replaceAll("#", "");
/* 1777 */     str = str.replaceAll("$", "");
/* 1778 */     str = str.replaceAll("%", "");
/* 1779 */     str = str.replaceAll("@", "");
/* 1780 */     str = str.replaceAll("/", "");
/* 1781 */     str = str.replaceAll("'", "");
/* 1782 */     str = str.replaceAll("\"", "");
/* 1783 */     str = str.replaceAll("â", "");
/*      */     
/* 1785 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private List searchForAvailType(EntityItem paramEntityItem, String paramString) {
/* 1791 */     ArrayList<EntityItem> arrayList = new ArrayList();
/*      */ 
/*      */     
/* 1794 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/*      */     
/* 1796 */     addDebug("*****mlm searchforavail AVAIL " + vector);
/*      */     
/* 1798 */     for (byte b = 0; b < vector.size(); b++) {
/* 1799 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 1801 */       String str = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", ",", "", false);
/* 1802 */       addDebug("*****mlm searchforavail model = " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "avail entity type = " + entityItem
/* 1803 */           .getEntityType() + " avail type = " + str);
/*      */       
/* 1805 */       if (paramString.equals(str)) {
/* 1806 */         arrayList.add(entityItem);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1811 */     return arrayList;
/*      */   }
/*      */ 
/*      */   
/*      */   private EntityItem searchForAvailType2(EntityItem paramEntityItem, String paramString) {
/* 1816 */     EntityItem entityItem = null;
/*      */ 
/*      */     
/* 1819 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELAVAIL", "AVAIL");
/*      */     
/* 1821 */     addDebug("*****mlm searchforavail AVAIL2 " + vector);
/*      */     
/* 1823 */     for (byte b = 0; b < vector.size(); b++) {
/* 1824 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/* 1826 */       String str = PokUtils.getAttributeValue(entityItem1, "AVAILTYPE", ",", "", false);
/* 1827 */       addDebug("*****mlm searchforavail model2 = " + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + "avail entity type = " + entityItem1
/* 1828 */           .getEntityType() + " avail type = " + str);
/*      */       
/* 1830 */       if (paramString.equals(str)) {
/* 1831 */         entityItem = entityItem1;
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1836 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String validateProdstructs2(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1842 */     String str1 = "";
/* 1843 */     String str2 = null;
/* 1844 */     Date date = null;
/*      */     
/* 1846 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getT006FeatureVEName());
/*      */     
/* 1848 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, paramEntityItem
/* 1849 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */     
/* 1851 */     EntityGroup entityGroup = entityList.getEntityGroup("PRODSTRUCT");
/* 1852 */     addDebug("*****mlm feature.id=" + paramEntityItem.getEntityType() + paramEntityItem.getEntityID() + " prodstructcount=" + entityGroup
/* 1853 */         .getEntityItemCount());
/*      */     
/* 1855 */     for (int i = 0; i < entityGroup.getEntityItemCount(); i++) {
/*      */       
/* 1857 */       EntityItem entityItem = entityGroup.getEntityItem(i);
/* 1858 */       addDebug("*****mlm prodstruct=" + entityItem.getEntityType() + entityItem.getEntityID());
/*      */       
/* 1860 */       String str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", ",", "", false);
/* 1861 */       addDebug("*****mlm oldestdate=" + str2);
/* 1862 */       addDebug("*****mlm psWdDate=" + str);
/* 1863 */       if (!str.equals("")) {
/* 1864 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*      */         try {
/* 1866 */           date = simpleDateFormat.parse(str);
/* 1867 */           if (str2 == null || date.after((Date)str2)) {
/* 1868 */             addDebug("*****mlm setting odlestdate to psWdDate");
/* 1869 */             Date date1 = date;
/* 1870 */             str1 = str;
/*      */           } 
/* 1872 */         } catch (ParseException parseException) {
/* 1873 */           addDebug(parseException.toString());
/* 1874 */           addDebug("*****mlm error: ParseException, setting date to 2050-12-31 - end");
/* 1875 */           str1 = "2050-12-31";
/* 1876 */           i = entityGroup.getEntityItemCount();
/*      */           break;
/*      */         } 
/*      */       } else {
/* 1880 */         addDebug("*****mlm psWdDate is blank, set date to 2050-12-31 - end");
/* 1881 */         str1 = "2050-12-31";
/* 1882 */         i = entityGroup.getEntityItemCount();
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 1887 */     return str1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void validateProdstructsSQL(String paramString1, String paramString2) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 1892 */     this.fidMap.clear();
/* 1893 */     if (paramString1.length() < 1)
/*      */       return; 
/* 1895 */     String str1 = "2050-12-31";
/* 1896 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 1897 */     Date date1 = null;
/* 1898 */     Date date2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1928 */     String str2 = "SELECT  FR.ENTITY1ID AS ENTITYID ,FR.ENTITYID as RENTITYID,AT.ATTRIBUTEVALUE AS EFFECTIVEDATE ,T1.ATTRIBUTEVALUE as GEO,F1.ATTRIBUTEVALUE as type,mf.ATTRIBUTEVALUE as MACHTYPEATR FROM OPICM.RELATOR FR \nJOIN OPICM.FLAG MF ON MF.ENTITYTYPE ='MODEL' AND mf.attributecode='MACHTYPEATR'  AND mf.entityid=fr.ENTITY2ID AND mf.VALTO >CURRENT TIMESTAMP  AND mf.EFFTO >CURRENT TIMESTAMP \nLEFT JOIN OPICM.RELATOR ar ON AR.ENTITYTYPE ='OOFAVAIL' AND FR.ENTITYID = AR.ENTITY1ID AND AR.VALTO>CURRENT TIMESTAMP AND AR.EFFTO > CURRENT TIMESTAMP\nLEFT JOIN OPICM.flag T1 ON T1.ATTRIBUTECODE ='QSMGEO' AND T1.ATTRIBUTEVALUE ='" + paramString2 + "' AND T1.ENTITYID =AR.ENTITY2ID AND T1.ENTITYTYPE ='AVAIL' AND T1.VALTO >CURRENT  timestamp AND T1.EFFTO > CURRENT timestamp \nLEFT JOIN opicm.FLAG f1  ON f1.ENTITYTYPE='AVAIL' AND ar.ENTITY2ID =f1.ENTITYID AND F1.ATTRIBUTECODE ='AVAILTYPE' AND  F1.ATTRIBUTEVALUE ='149' AND F1.VALTO >CURRENT  timestamp AND F1.EFFTO > CURRENT timestamp \nLEFT JOIN OPICM.TEXT AT ON AT.ENTITYID =ar.ENTITY2ID AND AT.ENTITYTYPE ='AVAIL' and at.attributecode='EFFECTIVEDATE' AND AT.VALTO >CURRENT  timestamp AND AT.EFFTO > CURRENT timestamp \nWHERE FR.ENTITYTYPE ='PRODSTRUCT' AND FR.ENTITY1ID IN (" + paramString1 + ") AND FR.VALTO >CURRENT TIMESTAMP AND FR.EFFTO > CURRENT TIMESTAMP WITH UR";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1935 */     addDebug("sql:" + str2);
/* 1936 */     Connection connection = this.m_db.getPDHConnection();
/* 1937 */     PreparedStatement preparedStatement = connection.prepareStatement(str2, 1004, 1007);
/*      */ 
/*      */     
/* 1940 */     ResultSet resultSet = preparedStatement.executeQuery();
/*      */     
/* 1942 */     ArrayList arrayList = new ArrayList();
/* 1943 */     HashSet<String> hashSet1 = new HashSet();
/* 1944 */     HashSet<String> hashSet2 = new HashSet();
/* 1945 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 1946 */     while (resultSet.next()) {
/* 1947 */       String str3 = resultSet.getString("EFFECTIVEDATE");
/* 1948 */       String str4 = resultSet.getString("entityid");
/* 1949 */       String str5 = resultSet.getString("MACHTYPEATR").trim();
/* 1950 */       String str6 = resultSet.getString("GEO");
/* 1951 */       String str7 = resultSet.getString("TYPE");
/* 1952 */       String str8 = resultSet.getString("RENTITYID");
/* 1953 */       hashMap.put(str8, str4 + str5);
/*      */       
/* 1955 */       if (str3 == null || str3.trim().equals("")) {
/*      */         continue;
/*      */       }
/*      */       
/* 1959 */       if (paramString2.equals(str6) && "149".equals(str7)) {
/* 1960 */         hashSet1.add(str8);
/* 1961 */         if (this.fidMap.get(str4 + str5) == null) {
/*      */           
/* 1963 */           this.fidMap.put(str4 + str5, str3);
/*      */           
/*      */           continue;
/*      */         } 
/*      */         try {
/* 1968 */           date1 = simpleDateFormat.parse(this.fidMap.get(str4 + str5).toString());
/* 1969 */           date2 = simpleDateFormat.parse(str3);
/* 1970 */           if (date1 == null || date2.after(date1)) {
/* 1971 */             addDebug("*****mlm setting odlestdate to psWdDate");
/* 1972 */             this.fidMap.put(str4 + str5, str3);
/*      */           }  continue;
/* 1974 */         } catch (ParseException parseException) {
/* 1975 */           addDebug(parseException.toString());
/* 1976 */           addDebug("*****mlm error: ParseException, setting date to 2050-12-31 - end");
/*      */           
/* 1978 */           this.fidMap.put(str4 + str5, "2050-12-31");
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */       
/* 1984 */       hashSet2.add(str8);
/*      */     } 
/*      */ 
/*      */     
/* 1988 */     hashSet2.removeAll(hashSet1);
/* 1989 */     if (hashSet2.size() > 0) {
/* 1990 */       Iterator<String> iterator = hashSet2.iterator();
/* 1991 */       while (iterator.hasNext()) {
/* 1992 */         String str = iterator.next();
/* 1993 */         this.fidMap.put(hashMap.get(str), "2050-12-31");
/*      */       } 
/*      */     } 
/*      */     
/* 1997 */     resultSet.close();
/*      */   }
/*      */ 
/*      */   
/*      */   private String validateProdstructsDate(String paramString) {
/* 2002 */     return (this.fidMap.get(paramString) == null) ? "2050-12-31" : this.fidMap.get(paramString).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String validateProdstructs3(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 2011 */     String str1 = "";
/* 2012 */     String str2 = null;
/* 2013 */     Date date = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2023 */     Vector<EntityItem> vector = getTMFFromFeature(paramEntityItem);
/* 2024 */     addDebug("Featue:" + paramEntityItem.getEntityID() + "-PRODUCT size:" + vector.size());
/* 2025 */     addDebug("Featue uplink:" + paramEntityItem.getUpLinkCount());
/* 2026 */     String str3 = ""; int i;
/* 2027 */     for (i = 0; i < vector.size(); i++) {
/* 2028 */       str3 = str3 + ((EntityItem)vector.get(i)).getEntityID() + ":";
/*      */     }
/* 2030 */     addDebug("*****mlm idString=" + str3);
/* 2031 */     for (i = 0; i < vector.size(); i++) {
/*      */       
/* 2033 */       EntityItem entityItem = vector.get(i);
/*      */       
/* 2035 */       String str = PokUtils.getAttributeValue(entityItem, "WTHDRWEFFCTVDATE", ",", "", false);
/* 2036 */       addDebug("*****mlm oldestdate=" + str2);
/* 2037 */       addDebug("*****mlm psWdDate=" + str);
/* 2038 */       if (!str.equals("")) {
/* 2039 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*      */         try {
/* 2041 */           date = simpleDateFormat.parse(str);
/* 2042 */           if (str2 == null || date.after((Date)str2)) {
/* 2043 */             addDebug("*****mlm setting odlestdate to psWdDate");
/* 2044 */             Date date1 = date;
/* 2045 */             str1 = str;
/*      */           } 
/* 2047 */         } catch (ParseException parseException) {
/* 2048 */           addDebug(parseException.toString());
/* 2049 */           addDebug("*****mlm error: ParseException, setting date to 2050-12-31 - end");
/* 2050 */           str1 = "2050-12-31";
/* 2051 */           i = vector.size();
/*      */           break;
/*      */         } 
/*      */       } else {
/* 2055 */         addDebug("*****mlm psWdDate is blank, set date to 2050-12-31 - end");
/* 2056 */         str1 = "2050-12-31";
/* 2057 */         i = vector.size();
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 2062 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String validateProdstructs(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 2068 */     String str = null;
/* 2069 */     Object object1 = null;
/* 2070 */     Object object2 = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2080 */     Vector<EntityItem> vector = getTMFFromFeature(paramEntityItem);
/* 2081 */     addDebug("Featue:" + paramEntityItem.getEntityID() + "-PRODUCT size:" + vector.size());
/* 2082 */     addDebug("Featue uplink:" + paramEntityItem.getUpLinkCount());
/* 2083 */     for (byte b = 0; b < vector.size(); b++) {
/* 2084 */       if (str == null) {
/*      */         
/* 2086 */         str = getTMFWDDate(vector.get(b));
/*      */       } else {
/*      */         
/* 2089 */         String str1 = getTMFWDDate(vector.get(b));
/* 2090 */         str = (str.compareTo(str1) > 0) ? str : str1;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2096 */     return str;
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
/* 2114 */     String str = "";
/* 2115 */     boolean bool = false;
/*      */     
/* 2117 */     this.m_elist = getEntityList(getT006ProdstructVEName());
/*      */     
/* 2119 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 2120 */     this.availSet = new HashSet(); byte b;
/* 2121 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2122 */       this.availSet.add(entityGroup.getEntityItem(b).getEntityID() + "");
/*      */     }
/* 2124 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2125 */       EANFlagAttribute eANFlagAttribute = null;
/* 2126 */       String str1 = "";
/*      */       
/* 2128 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 2130 */       str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/* 2131 */       str = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/* 2132 */       if (str.equals("EPIC")) {
/* 2133 */         bool = true;
/*      */       }
/*      */ 
/*      */       
/* 2137 */       if (str1.equals("Planned Availability") || str1.equals("End of Service") || str1
/* 2138 */         .equals("Last Order")) {
/* 2139 */         eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 2140 */         if (eANFlagAttribute != null) {
/* 2141 */           if (eANFlagAttribute.isSelected("6199")) {
/* 2142 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", str1, bool, "6199");
/*      */           }
/* 2144 */           if (eANFlagAttribute.isSelected("6200")) {
/* 2145 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", str1, bool, "6200");
/*      */           }
/*      */           
/* 2148 */           if (eANFlagAttribute.isSelected("6198")) {
/* 2149 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", str1, bool, "6198");
/*      */           }
/*      */           
/* 2152 */           if (eANFlagAttribute.isSelected("6204")) {
/* 2153 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", str1, bool, "6204");
/*      */           }
/* 2155 */           if (eANFlagAttribute.isSelected("6221")) {
/* 2156 */             createT006FeatureRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", str1, bool, "6221");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createT006FeatureRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString1, String paramString2, boolean paramBoolean, String paramString3) throws IOException, SQLException, MiddlewareException {
/* 2219 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getFeatureVEName());
/* 2220 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { paramEntityItem2 });
/*      */     
/* 2222 */     EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 2223 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/* 2224 */     addDebug("prodVect:" + ((vector == null) ? 0 : vector.size()));
/*      */     
/* 2226 */     addDebug("list:" + PokUtils.outputList(entityList));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2249 */     EntityGroup entityGroup = entityList.getEntityGroup("FEATURE");
/* 2250 */     String str = ""; byte b;
/* 2251 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2252 */       if (str.length() > 0)
/* 2253 */         str = str + ','; 
/* 2254 */       str = str + entityGroup.getEntityItem(b).getEntityID();
/*      */     } 
/* 2256 */     validateProdstructsSQL(str, paramString3);
/*      */     
/* 2258 */     for (b = 0; b < vector.size(); b++) {
/*      */       
/* 2260 */       EntityItem entityItem1 = vector.get(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2275 */       EntityItem entityItem2 = getModelEntityFromTmf(entityItem1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2287 */       EntityItem entityItem3 = (EntityItem)entityItem1.getUpLink(0);
/* 2288 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "PRODSTSTDMT", "STDMAINT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2324 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(entityItem2, "MODELSGMTACRONYMA", "SGMNTACRNYM");
/* 2325 */       Vector<EntityItem> vector3 = PokUtils.getAllLinkedEntities(entityItem2, "MODELGEOMOD", "GEOMOD");
/* 2326 */       Vector<EntityItem> vector4 = PokUtils.getAllLinkedEntities(entityItem2, "MODELWARR", "WARR");
/* 2327 */       Vector<EntityItem> vector5 = PokUtils.getAllLinkedEntities(entityItem2, "MODELSTDMAINT", "STDMAINT");
/*      */       
/* 2329 */       EntityGroup entityGroup1 = entityList.getEntityGroup("SGMNTACRNYM");
/* 2330 */       EntityGroup entityGroup2 = entityList.getEntityGroup("GEOMOD");
/* 2331 */       EntityGroup entityGroup3 = entityList.getEntityGroup("WARR");
/* 2332 */       EntityGroup entityGroup4 = entityList.getEntityGroup("STDMAINT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2349 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 2350 */       String str1 = "";
/* 2351 */       String str2 = "";
/* 2352 */       String str3 = "";
/* 2353 */       String str4 = "";
/* 2354 */       String str5 = "";
/* 2355 */       String str6 = "";
/* 2356 */       String str7 = "";
/* 2357 */       String str8 = "";
/* 2358 */       String str9 = "";
/* 2359 */       String str10 = "";
/* 2360 */       String str11 = "";
/* 2361 */       String str12 = "";
/* 2362 */       String str13 = "";
/* 2363 */       String str14 = "";
/* 2364 */       String str15 = "";
/* 2365 */       String str16 = "";
/* 2366 */       String str17 = "";
/* 2367 */       String str18 = "";
/* 2368 */       String str19 = "";
/* 2369 */       String str20 = "";
/* 2370 */       String str21 = "";
/* 2371 */       String str22 = "";
/* 2372 */       String str23 = "";
/* 2373 */       String str24 = "";
/* 2374 */       String str25 = "";
/* 2375 */       String str26 = "";
/* 2376 */       String str27 = "";
/* 2377 */       String str28 = "";
/* 2378 */       String str29 = "";
/* 2379 */       String str30 = "";
/* 2380 */       String str31 = "";
/* 2381 */       String str32 = "";
/* 2382 */       String str33 = "";
/* 2383 */       String str34 = "";
/* 2384 */       String str35 = "";
/*      */       
/* 2386 */       stringBuffer1.append(getValue("IFTYPE", "F"));
/*      */       
/* 2388 */       if (paramString1.equals("Latin America")) {
/* 2389 */         str1 = "601";
/* 2390 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/*      */         
/* 2392 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 2393 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 2394 */         str1 = "999";
/* 2395 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/*      */         
/* 2397 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 2398 */       } else if (paramString1.equals("Asia Pacific")) {
/* 2399 */         str1 = "872";
/* 2400 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/*      */         
/* 2402 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 2403 */       } else if (paramString1.equals("US Only")) {
/* 2404 */         str1 = "897";
/* 2405 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */         
/* 2407 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 2408 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/* 2409 */         str1 = "649";
/* 2410 */         str7 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/*      */         
/* 2412 */         str2 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */       } 
/* 2414 */       stringBuffer1.append(getValue("IOPUCTY", str1));
/* 2415 */       stringBuffer1.append(getValue("ISLMPAL", str7));
/* 2416 */       stringBuffer1.append(getValue("ISLMRFA", str2));
/* 2417 */       String str36 = PokUtils.getAttributeValue(entityItem2, "MACHTYPEATR", ",", "", false);
/* 2418 */       str36 = str36 + PokUtils.getAttributeValue(entityItem3, "FEATURECODE", ",", "", false);
/* 2419 */       stringBuffer1.append(getValue("ISLMPRN", str36));
/*      */       
/* 2421 */       str4 = PokUtils.getAttributeValue(entityItem3, "FCTYPE", ",", "", false);
/* 2422 */       str3 = "MF";
/* 2423 */       if (str4.equals("RPQ-RLISTED") || str4.equals("RPQ-ILISTED") || str4.equals("RPQ-PLISTED")) {
/* 2424 */         str3 = "MQ";
/*      */       }
/*      */       
/* 2427 */       stringBuffer1.append(getValue("CSLMPCI", str3));
/* 2428 */       stringBuffer1.append(getValue("IPRTNUM", ""));
/* 2429 */       stringBuffer1.append(getValue("FPUNINC", "2"));
/* 2430 */       stringBuffer1.append(getValue("CAOAV", ""));
/* 2431 */       stringBuffer1.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/* 2432 */       stringBuffer1.append(getValue("DSLMCPO", ""));
/*      */       
/* 2434 */       stringBuffer1.append(getValue("DSLMGAD", PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false)));
/*      */       
/* 2436 */       String str37 = PokUtils.getAttributeValue(entityItem1, "ORDERCODE", ",", "", false);
/* 2437 */       EntityItem entityItem4 = null;
/*      */       
/* 2439 */       StringBuffer stringBuffer2 = new StringBuffer();
/* 2440 */       if (str37.equals("Both") || str37.equals("MES")) {
/* 2441 */         Vector<EntityItem> vector7 = PokUtils.getAllLinkedEntities(entityItem1, "OOFAVAIL", "AVAIL");
/* 2442 */         addDebug("availVec" + ((vector7 == null) ? 0 : vector7.size()));
/*      */         int i;
/* 2444 */         for (i = 0; i < vector7.size(); i++) {
/* 2445 */           stringBuffer2.append(((EntityItem)vector7.get(i)).getEntityID() + ",");
/*      */         }
/*      */         
/* 2448 */         stringBuffer2.append("||||");
/* 2449 */         for (i = 0; i < vector7.size(); i++) {
/*      */           
/* 2451 */           EANFlagAttribute eANFlagAttribute = null;
/* 2452 */           entityItem4 = vector7.elementAt(i);
/*      */           
/* 2454 */           stringBuffer2.append(entityItem4.getEntityID() + "");
/* 2455 */           str32 = PokUtils.getAttributeValue(entityItem4, "AVAILTYPE", ",", "", false);
/* 2456 */           eANFlagAttribute = (EANFlagAttribute)entityItem4.getAttribute("QSMGEO");
/* 2457 */           if (isQSMGeoSelected(paramString1, eANFlagAttribute) && str32
/* 2458 */             .equals("MES Planned Availability") && this.availSet
/* 2459 */             .contains(entityItem4.getEntityID() + "")) {
/* 2460 */             str33 = PokUtils.getAttributeValue(entityItem4, "EFFECTIVEDATE", ",", "", false);
/* 2461 */             i = vector7.size();
/*      */           } 
/*      */         } 
/* 2464 */         if (str33.equals("")) {
/* 2465 */           str33 = PokUtils.getAttributeValue(paramEntityItem2, "EFFECTIVEDATE", ",", "", false);
/*      */         }
/* 2467 */       } else if (str37.equals("Initial")) {
/* 2468 */         str33 = "2050-12-31";
/*      */       } 
/*      */       
/* 2471 */       if (str33.equals("")) {
/* 2472 */         str33 = "2050-12-31";
/*      */       }
/* 2474 */       addDebug("Prod id:" + entityItem1.getEntityID() + "   idString:" + stringBuffer2);
/*      */       
/* 2476 */       stringBuffer1.append(getValue("DSLMMES", str33));
/*      */       
/* 2478 */       stringBuffer1.append(getValue("QSMEDMW", "2050-12-31"));
/* 2479 */       stringBuffer1.append(getValue("DSLMMVA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", ",", "", false)));
/*      */       
/* 2481 */       str5 = validateProdstructsDate(entityItem3.getEntityID() + PokUtils.getAttributeValue(entityItem2, "MACHTYPEATR", ",", "", false));
/*      */ 
/*      */ 
/*      */       
/* 2485 */       stringBuffer1.append(getValue("DSLMWDN", str5));
/*      */       
/* 2487 */       str23 = PokUtils.getAttributeValue(entityItem3, "PRICEDFEATURE", ",", "", false);
/*      */       
/* 2489 */       if (str4.equals("Primary") && str23.equals("No")) {
/* 2490 */         str22 = "S";
/*      */       }
/*      */       
/* 2493 */       if (paramString1.equals("Asia Pacific")) {
/* 2494 */         if (str23.equals("No")) {
/* 2495 */           str21 = "0.00";
/* 2496 */         } else if (str23.equals("Yes")) {
/* 2497 */           str21 = "1.00";
/*      */         } 
/*      */       } else {
/* 2500 */         str21 = "1.00";
/*      */       } 
/*      */       
/* 2503 */       stringBuffer1.append(getValue("ASLMMVP", str21));
/*      */       
/* 2505 */       stringBuffer1.append(getValue("CICRY", "N"));
/* 2506 */       stringBuffer1.append(getValue("CIDCJ", "N"));
/* 2507 */       stringBuffer1.append(getValue("CIDXC", "N"));
/*      */       
/* 2509 */       if (paramString1.equals("US Only")) {
/* 2510 */         stringBuffer1.append(getValue("CINCA", "N"));
/*      */       } else {
/* 2512 */         stringBuffer1.append(getValue("CINCA", "Y"));
/*      */       } 
/*      */       
/* 2515 */       String str38 = "";
/* 2516 */       str28 = PokUtils.getAttributeValue(entityItem3, "PRICEDFEATURE", "", "");
/* 2517 */       if (paramString1.equals("US Only")) {
/* 2518 */         str38 = "N";
/*      */       }
/* 2520 */       else if (str28.equals("Yes")) {
/* 2521 */         str38 = "N";
/* 2522 */       } else if (str28.equals("No")) {
/* 2523 */         str38 = "Y";
/*      */       } else {
/* 2525 */         str38 = "N";
/*      */       } 
/*      */ 
/*      */       
/* 2529 */       stringBuffer1.append(getValue("CINCB", str38));
/*      */       
/* 2531 */       EntityItem entityItem5 = null;
/*      */       
/* 2533 */       if (!vector1.isEmpty()) {
/* 2534 */         entityItem5 = vector1.elementAt(0);
/* 2535 */         if (entityItem5 != null) {
/* 2536 */           str30 = PokUtils.getAttributeValue(entityItem5, "MAINTELIG", "", "");
/*      */         }
/* 2538 */         else if (vector5 != null && vector5.size() > 0) {
/* 2539 */           entityItem5 = vector5.get(0);
/*      */         }
/*      */       
/*      */       }
/* 2543 */       else if (vector5 != null && vector5.size() > 0) {
/* 2544 */         entityItem5 = vector5.get(0);
/*      */       } 
/*      */ 
/*      */       
/* 2548 */       if (entityItem5 != null) {
/* 2549 */         str30 = PokUtils.getAttributeValue(entityItem5, "MAINTELIG", "", "");
/*      */       }
/*      */ 
/*      */       
/* 2553 */       if (paramString1.equals("Asia Pacific")) {
/* 2554 */         str31 = "Y";
/* 2555 */       } else if (paramString1.equals("US Only") || paramString1
/* 2556 */         .equals("Canada and Caribbean North")) {
/* 2557 */         str31 = "N";
/* 2558 */       } else if (paramString1.equals("Europe/Middle East/Africa") || paramString1
/* 2559 */         .equals("Latin America")) {
/* 2560 */         if (str30.equals("Yes")) {
/* 2561 */           str31 = "N";
/* 2562 */         } else if (str30.equals("No")) {
/* 2563 */           str31 = "Y";
/*      */         } 
/*      */       } 
/*      */       
/* 2567 */       stringBuffer1.append(getValue("CINCC", str31));
/*      */       
/* 2569 */       stringBuffer1.append(getValue("CINPM", "N"));
/* 2570 */       stringBuffer1.append(getValue("CITEM", "N"));
/* 2571 */       stringBuffer1.append(getValue("CISUP", "N"));
/* 2572 */       if (vector2 != null && vector2.size() > 0) {
/* 2573 */         EntityItem entityItem8 = vector2.get(0);
/* 2574 */         stringBuffer1.append(getValue("CJLBSAC", PokUtils.getAttributeValue(entityItem8, "ACRNYM", "", "")));
/*      */       } else {
/* 2576 */         stringBuffer1.append(getValue("CJLBSAC", "   "));
/*      */       } 
/* 2578 */       stringBuffer1.append(getValue("CLASSPT", "IHW"));
/*      */       
/* 2580 */       str24 = "";
/*      */       
/* 2582 */       if (paramString1.equals("Europe/Middle East/Africa") || paramString1.equals("Latin America")) {
/* 2583 */         if (str22.equals("S")) {
/* 2584 */           str24 = "CM";
/*      */         }
/* 2586 */       } else if (paramString1.equals("Asia Pacific")) {
/* 2587 */         if (str22.equals("S")) {
/* 2588 */           str24 = "CM";
/*      */         }
/* 2590 */       } else if (paramString1.equals("US Only")) {
/* 2591 */         str24 = "NF";
/* 2592 */       } else if (paramString1.equals("Canada and Caribbean North")) {
/* 2593 */         str24 = "";
/*      */       } 
/*      */       
/* 2596 */       stringBuffer1.append(getValue("CSLMFTY", str24));
/* 2597 */       stringBuffer1.append(getValue("CVOAT", ""));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2603 */       if (paramString1.equals("Canada and Caribbean North")) {
/* 2604 */         str6 = "Y";
/* 2605 */       } else if (paramString1.equals("Asia Pacific") || paramString1.equals("US Only")) {
/* 2606 */         str6 = "N";
/* 2607 */       } else if (paramString1.equals("Europe/Middle East/Africa") || paramString1
/* 2608 */         .equals("Latin America")) {
/* 2609 */         str6 = str30;
/*      */       } 
/*      */       
/* 2612 */       stringBuffer1.append(getValue("FAGRMBE", str6));
/*      */       
/* 2614 */       String str39 = "";
/* 2615 */       EntityItem entityItem6 = null;
/* 2616 */       if (vector3 != null && vector3.size() > 0) {
/* 2617 */         for (int i = 0; i < vector3.size(); i++) {
/* 2618 */           entityItem6 = vector3.get(i);
/* 2619 */           str39 = PokUtils.getAttributeValue(entityItem6, "GENAREASELECTION", "", "");
/* 2620 */           if (str39.equals(paramString1)) {
/* 2621 */             str20 = PokUtils.getAttributeValue(entityItem6, "PURCHONLY", "", "");
/* 2622 */             str29 = PokUtils.getAttributeValue(entityItem6, "EDUCPURCHELIG", "", "");
/* 2623 */             i = vector3.size();
/*      */           } else {
/* 2625 */             entityItem6 = null;
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 2630 */       if (paramString1.equals("Latin America") || paramString1.equals("Europe/Middle East/Africa") || paramString1
/* 2631 */         .equals("Asia Pacific") || paramString1
/* 2632 */         .equals("Canada and Caribbean North")) {
/* 2633 */         if (str37.equals("Initial")) {
/* 2634 */           str26 = "Y";
/*      */         } else {
/* 2636 */           str26 = "N";
/*      */         } 
/* 2638 */       } else if (paramString1.equals("US Only")) {
/* 2639 */         str26 = "N";
/*      */       } 
/* 2641 */       stringBuffer1.append(getValue("FSLMPIO", str26));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2651 */       if (paramString1.equals("Latin America") || paramString1.equals("US Only") || paramString1
/* 2652 */         .equals("Canada and Caribbean North") || paramString1
/* 2653 */         .equals("Asia Pacific")) {
/* 2654 */         stringBuffer1.append(getValue("FSLMPOP", "No"));
/* 2655 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 2656 */         stringBuffer1.append(getValue("FSLMPOP", "Yes"));
/*      */       } else {
/* 2658 */         stringBuffer1.append(getValue("FSLMPOP", str20));
/*      */       } 
/*      */       
/* 2661 */       stringBuffer1.append(getValue("FSLMSTK", "N"));
/*      */       
/* 2663 */       String str40 = "";
/*      */       
/* 2665 */       ArrayList<String> arrayList = new ArrayList();
/*      */       
/* 2667 */       EntityItem entityItem7 = null;
/* 2668 */       Vector<EntityItem> vector6 = null;
/* 2669 */       String str41 = "";
/*      */       
/* 2671 */       vector6 = PokUtils.getAllLinkedEntities(entityItem1, "PRODSTRUCTWARR", "WARR");
/*      */ 
/*      */ 
/*      */       
/* 2675 */       if (!vector6.isEmpty()) {
/* 2676 */         entityItem7 = vector6.elementAt(0);
/* 2677 */         if (entityItem7 == null) {
/* 2678 */           if (vector4 != null && vector4.size() > 0) {
/* 2679 */             entityItem7 = vector4.get(0);
/* 2680 */             str41 = PokUtils.getAttributeValue(entityItem7, "WARRID", "", "");
/* 2681 */             if (str41.equals("WTY0000")) {
/* 2682 */               if (vector4.size() > 1) {
/* 2683 */                 entityItem7 = vector4.get(1);
/*      */               } else {
/* 2685 */                 entityItem7 = null;
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } else {
/* 2690 */           str41 = PokUtils.getAttributeValue(entityItem7, "WARRID", "", "");
/* 2691 */           if (str41.equals("WTY0000")) {
/* 2692 */             if (vector6.size() > 1) {
/* 2693 */               entityItem7 = vector6.elementAt(1);
/*      */             } else {
/* 2695 */               entityItem7 = null;
/*      */             }
/*      */           
/*      */           }
/*      */         } 
/* 2700 */       } else if (vector4 != null && vector4.size() > 0) {
/* 2701 */         entityItem7 = vector4.get(0);
/* 2702 */         str41 = PokUtils.getAttributeValue(entityItem7, "WARRID", "", "");
/* 2703 */         if (str41.equals("WTY0000")) {
/* 2704 */           if (vector4.size() > 1) {
/* 2705 */             entityItem7 = vector4.get(1);
/*      */           } else {
/* 2707 */             entityItem7 = null;
/*      */           } 
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2713 */       if (entityItem7 != null) {
/* 2714 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem7.getAttribute("WARRTYPE");
/* 2715 */         if (eANFlagAttribute != null) {
/* 2716 */           if (paramString1.equals("Europe/Middle East/Africa")) {
/* 2717 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 2718 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 2719 */               .isSelected("W0250")) {
/* 2720 */               str40 = "Y";
/*      */             } else {
/* 2722 */               str40 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 2726 */           if (paramString1.equals("Latin America")) {
/* 2727 */             if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330") || eANFlagAttribute
/* 2728 */               .isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute
/* 2729 */               .isSelected("W0580")) {
/* 2730 */               str40 = "Y";
/*      */             } else {
/* 2732 */               str40 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 2736 */           if (paramString1.equals("Asia Pacific")) {
/* 2737 */             if (eANFlagAttribute.isSelected("W0550") || eANFlagAttribute.isSelected("W0390") || eANFlagAttribute
/* 2738 */               .isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 2739 */               .isSelected("W0250") || eANFlagAttribute.isSelected("W0310") || eANFlagAttribute
/* 2740 */               .isSelected("W0330") || eANFlagAttribute.isSelected("W0590")) {
/* 2741 */               str40 = "Y";
/*      */             } else {
/* 2743 */               str40 = "N";
/*      */             } 
/*      */           }
/*      */           
/* 2747 */           if (paramString1.equals("Canada and Caribbean North") || paramString1
/* 2748 */             .equals("US Only")) {
/* 2749 */             str40 = "N";
/*      */           }
/*      */         } 
/*      */       } else {
/* 2753 */         str40 = "N";
/*      */       } 
/* 2755 */       stringBuffer1.append(getValue("FSLM2CF", str40));
/*      */ 
/*      */ 
/*      */       
/* 2759 */       stringBuffer1.append(getValue("IDORIG", "IBM"));
/* 2760 */       str8 = "000";
/* 2761 */       str9 = "000";
/* 2762 */       str10 = "000";
/* 2763 */       str11 = "000";
/*      */       
/* 2765 */       if (paramString1.equals("US Only") || paramString1.equals("Canada and Caribbean North")) {
/* 2766 */         str8 = "000";
/* 2767 */         str9 = "000";
/* 2768 */         str10 = "000";
/* 2769 */         str11 = "000";
/* 2770 */       } else if (paramString1.equals("Europe/Middle East/Africa")) {
/* 2771 */         str8 = " @@";
/* 2772 */         str9 = " @@";
/* 2773 */         str10 = " @@";
/* 2774 */         str11 = " @@";
/*      */       }
/* 2776 */       else if (entityItem6 != null) {
/*      */ 
/*      */ 
/*      */         
/* 2780 */         str8 = getNumValue("PCUAEAP", 
/* 2781 */             PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMHGHSCH", ",", "", false));
/* 2782 */         str9 = getNumValue("PCUAHEA", 
/* 2783 */             PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMHGHSCH", ",", "", false));
/* 2784 */         str10 = getNumValue("PCUASEA", 
/* 2785 */             PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMSECONDRYSCH", ",", "", false));
/* 2786 */         str11 = getNumValue("PCUAUEA", 
/* 2787 */             PokUtils.getAttributeValue(entityItem6, "EDUCALLOWMUNVRSTY", ",", "", false));
/*      */       } 
/*      */ 
/*      */       
/* 2791 */       stringBuffer1.append(getValue("PCUAEAP", str8));
/* 2792 */       stringBuffer1.append(getValue("PCUAHEA", str9));
/* 2793 */       stringBuffer1.append(getValue("PCUASEA", str10));
/* 2794 */       stringBuffer1.append(getValue("PCUAUEA", str11));
/*      */       
/* 2796 */       stringBuffer1.append(getValue("POGMES", ""));
/*      */       
/* 2798 */       String str42 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/* 2799 */       if (str42.equals("CIF")) {
/* 2800 */         if (paramString1.equals("Europe/Middle East/Africa") || paramString1
/* 2801 */           .equals("Latin America")) {
/* 2802 */           str27 = "01";
/* 2803 */         } else if (paramString1.equals("Asia Pacific")) {
/* 2804 */           str27 = "10";
/* 2805 */         } else if (paramString1.equals("US Only") || paramString1
/* 2806 */           .equals("Canada and Caribbean North")) {
/* 2807 */           str27 = "";
/*      */         } 
/* 2809 */       } else if (str42.equals("CE") || str42.equals("N/A") || str42.equals("Does not apply")) {
/* 2810 */         str27 = "";
/*      */       } 
/* 2812 */       stringBuffer1.append(getValue("QSLMCSU", str27));
/*      */       
/* 2814 */       stringBuffer1.append(getValue("QSMXESA", "N"));
/* 2815 */       stringBuffer1.append(getValue("QSMXSSA", "N"));
/*      */       
/* 2817 */       String str43 = PokUtils.getAttributeValue(entityItem3, "INVNAME", ",", "", false);
/* 2818 */       stringBuffer1.append(getValue("TSLMDES", removeSpecialChars(str43)));
/*      */       
/* 2820 */       str25 = "";
/*      */       
/* 2822 */       if (str22.equals("S")) {
/* 2823 */         str25 = "OTH";
/*      */       }
/*      */       
/* 2826 */       stringBuffer1.append(getValue("STSPCFT", str25));
/* 2827 */       stringBuffer1.append(getValue("TIMSTMP", ""));
/* 2828 */       stringBuffer1.append(getValue("USERID", ""));
/*      */       
/* 2830 */       arrayList = new ArrayList();
/* 2831 */       str12 = "";
/* 2832 */       str13 = "";
/* 2833 */       str14 = "";
/* 2834 */       str15 = "";
/* 2835 */       str16 = "";
/* 2836 */       str17 = "";
/* 2837 */       str18 = "";
/* 2838 */       str19 = "";
/*      */       
/* 2840 */       if (paramString1.equals("Asia Pacific") && 
/* 2841 */         entityItem7 != null) {
/* 2842 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem7.getAttribute("WARRTYPE");
/* 2843 */         if (eANFlagAttribute != null) {
/* 2844 */           if (eANFlagAttribute.isSelected("W0560") || eANFlagAttribute.isSelected("W0570") || eANFlagAttribute
/* 2845 */             .isSelected("W0580")) {
/* 2846 */             arrayList.add("IOR");
/*      */           }
/* 2848 */           if (eANFlagAttribute.isSelected("W0550")) {
/* 2849 */             arrayList.add("IOE");
/*      */           }
/* 2851 */           if (eANFlagAttribute.isSelected("W0390")) {
/* 2852 */             arrayList.add("COE");
/*      */           }
/* 2854 */           if (eANFlagAttribute.isSelected("W0200") || eANFlagAttribute.isSelected("W0240") || eANFlagAttribute
/* 2855 */             .isSelected("W0250")) {
/* 2856 */             arrayList.add("CCE");
/*      */           }
/* 2858 */           if (eANFlagAttribute.isSelected("W0310") || eANFlagAttribute.isSelected("W0330")) {
/* 2859 */             arrayList.add("CCR");
/*      */           }
/* 2861 */           if (eANFlagAttribute.isSelected("W0590")) {
/* 2862 */             arrayList.add("IOS");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2903 */           for (byte b1 = 0; b1 < arrayList.size(); b1++) {
/* 2904 */             if (b1 == 0) {
/* 2905 */               str12 = arrayList.get(b1);
/* 2906 */             } else if (b1 == 1) {
/* 2907 */               str13 = arrayList.get(b1);
/* 2908 */             } else if (b1 == 2) {
/* 2909 */               str14 = arrayList.get(b1);
/* 2910 */             } else if (b1 == 3) {
/* 2911 */               str15 = arrayList.get(b1);
/* 2912 */             } else if (b1 == 4) {
/* 2913 */               str16 = arrayList.get(b1);
/* 2914 */             } else if (b1 == 5) {
/* 2915 */               str17 = arrayList.get(b1);
/* 2916 */             } else if (b1 == 6) {
/* 2917 */               str18 = arrayList.get(b1);
/* 2918 */             } else if (b1 == 7) {
/* 2919 */               str19 = arrayList.get(b1);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2926 */       stringBuffer1.append(getValue("CSLMTM1", str12));
/* 2927 */       stringBuffer1.append(getValue("CSLMTM2", str13));
/* 2928 */       stringBuffer1.append(getValue("CSLMTM3", str14));
/* 2929 */       stringBuffer1.append(getValue("CSLMTM4", str15));
/* 2930 */       stringBuffer1.append(getValue("CSLMTM5", str16));
/* 2931 */       stringBuffer1.append(getValue("CSLMTM6", str17));
/* 2932 */       stringBuffer1.append(getValue("CSLMTM7", str18));
/* 2933 */       stringBuffer1.append(getValue("CSLMTM8", str19));
/*      */       
/* 2935 */       stringBuffer1.append(getValue("FSAPRES", "N"));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2945 */       if (paramString1.equals("US Only")) {
/* 2946 */         EntityItem entityItem8 = null;
/* 2947 */         str35 = PokUtils.getAttributeValue(entityItem2, "WARRSVCCOVR", "", "");
/* 2948 */         if (str35 != null) {
/* 2949 */           if (str35.equals("No Warranty") || str35.equals("")) {
/* 2950 */             str34 = "Z";
/*      */           }
/* 2952 */           else if (vector4 != null && vector4.size() > 0) {
/* 2953 */             entityItem8 = vector4.get(0);
/*      */ 
/*      */ 
/*      */             
/* 2957 */             if (entityItem8 != null) {
/* 2958 */               String str44 = PokUtils.getAttributeValue(entityItem8, "WARRID", "", "");
/* 2959 */               if (str44.equals("WTY0000")) {
/* 2960 */                 if (vector4.size() > 1) {
/* 2961 */                   entityItem8 = vector4.get(1);
/*      */                 } else {
/* 2963 */                   entityItem8 = null;
/*      */                 } 
/*      */               }
/*      */             } 
/*      */             
/* 2968 */             if (entityItem8 != null) {
/* 2969 */               str34 = PokUtils.getAttributeValue(entityItem8, "WARRCATG", "", "");
/*      */             } else {
/* 2971 */               str34 = "";
/*      */             } 
/*      */           } else {
/* 2974 */             str34 = "";
/*      */           } 
/*      */         } else {
/*      */           
/* 2978 */           str34 = "Z";
/*      */         } 
/*      */       } else {
/* 2981 */         str34 = "";
/*      */       } 
/*      */       
/* 2984 */       stringBuffer1.append(getValue("CSLMWCD", str34));
/*      */       
/* 2986 */       if (paramString1.equals("US Only")) {
/* 2987 */         String str44 = PokUtils.getAttributeValue(entityItem2, "MAINTANNBILLELIGINDC", ",", "", false);
/* 2988 */         if (str44.equals("Yes")) {
/* 2989 */           stringBuffer1.append(getValue("CUSAPMS", "Y"));
/* 2990 */         } else if (str44.equals("No")) {
/* 2991 */           stringBuffer1.append(getValue("CUSAPMS", "X"));
/*      */         } else {
/* 2993 */           stringBuffer1.append(getValue("CUSAPMS", ""));
/*      */         } 
/*      */       } else {
/* 2996 */         stringBuffer1.append(getValue("CUSAPMS", ""));
/*      */       } 
/*      */       
/* 2999 */       stringBuffer1.append(getValue("DUSALRW", "2050-12-31"));
/* 3000 */       stringBuffer1.append(getValue("DUSAMDW", "2050-12-31"));
/* 3001 */       stringBuffer1.append(getValue("DUSAWUW", "2050-12-31"));
/*      */       
/* 3003 */       if (paramString1.equals("US Only")) {
/* 3004 */         stringBuffer1.append(getValue("FSLMCBL", "N"));
/*      */       } else {
/* 3006 */         stringBuffer1.append(getValue("FSLMCBL", ""));
/*      */       } 
/*      */       
/* 3009 */       if (paramString1.equals("US Only")) {
/* 3010 */         stringBuffer1.append(getValue("FUSAAAS", "Y"));
/*      */       } else {
/* 3012 */         stringBuffer1.append(getValue("FUSAAAS", ""));
/*      */       } 
/*      */       
/* 3015 */       if (paramString1.equals("US Only")) {
/* 3016 */         stringBuffer1.append(getValue("FUSAEDE", str29));
/*      */       } else {
/* 3018 */         stringBuffer1.append(getValue("FUSAEDE", ""));
/*      */       } 
/*      */       
/* 3021 */       if (paramString1.equals("US Only")) {
/* 3022 */         stringBuffer1.append(
/* 3023 */             getValue("FUSALEP", PokUtils.getAttributeValue(entityItem2, "MAINTANNBILLELIGINDC", ",", "", false)));
/*      */       } else {
/* 3025 */         stringBuffer1.append(getValue("FUSALEP", " "));
/*      */       } 
/*      */       
/* 3028 */       if (paramString1.equals("US Only")) {
/* 3029 */         stringBuffer1.append(getValue("FUSAIRR", "N"));
/*      */       } else {
/* 3031 */         stringBuffer1.append(getValue("FUSAIRR", ""));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3038 */       if (entityItem6 != null) {
/* 3039 */         stringBuffer1.append(getValue("ICESPCC", PokUtils.getAttributeValue(entityItem6, "PERCALLCLS", ",", "", false)));
/*      */       } else {
/* 3041 */         stringBuffer1.append(getValue("ICESPCC", ""));
/*      */       } 
/* 3043 */       stringBuffer1.append(getValue("QUSAPOP", "00.0"));
/* 3044 */       stringBuffer1.append(getValue("FSLMRFM", ""));
/* 3045 */       addDebug(stringBuffer1.toString() + " AVAIL:" + entityItem.getEntityID() + " FEATURE:" + entityItem3.getEntityID() + " PRODUCT:" + entityItem1
/* 3046 */           .getEntityID());
/* 3047 */       stringBuffer1.append(NEWLINE);
/* 3048 */       paramOutputStreamWriter.write(stringBuffer1.toString());
/* 3049 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getModelEntityFromTmf(EntityItem paramEntityItem) {
/* 3056 */     EntityItem entityItem = null;
/* 3057 */     Vector<EntityItem> vector = paramEntityItem.getDownLink();
/* 3058 */     if (vector != null && vector.size() > 0) {
/* 3059 */       for (byte b = 0; b < vector.size(); b++) {
/* 3060 */         EntityItem entityItem1 = vector.get(b);
/* 3061 */         if ("MODEL".equals(entityItem1.getEntityType())) {
/* 3062 */           entityItem = entityItem1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 3068 */     return entityItem;
/*      */   }
/*      */ 
/*      */   
/*      */   private Vector getTMFFromFeature(EntityItem paramEntityItem) {
/* 3073 */     Vector<EntityItem> vector1 = new Vector();
/* 3074 */     Vector<EntityItem> vector2 = paramEntityItem.getDownLink();
/* 3075 */     if (vector2 != null && vector2.size() > 0) {
/* 3076 */       for (byte b = 0; b < vector2.size(); b++) {
/* 3077 */         EntityItem entityItem = vector2.get(b);
/* 3078 */         if ("PRODSTRUCT".equals(entityItem.getEntityType()) && !vector1.contains(entityItem)) {
/* 3079 */           vector1.add(entityItem);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 3084 */     return vector1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isQSMGeoSelected(String paramString, EANFlagAttribute paramEANFlagAttribute) {
/* 3090 */     if (paramEANFlagAttribute != null) {
/* 3091 */       if (paramString.equals("Asia Pacific") && paramEANFlagAttribute.isSelected("6199")) {
/* 3092 */         return true;
/*      */       }
/*      */       
/* 3095 */       if (paramString.equals("Canada and Caribbean North") && paramEANFlagAttribute.isSelected("6200")) {
/* 3096 */         return true;
/*      */       }
/*      */       
/* 3099 */       if (paramString.equals("Europe/Middle East/Africa") && paramEANFlagAttribute.isSelected("6198")) {
/* 3100 */         return true;
/*      */       }
/*      */       
/* 3103 */       if (paramString.equals("Latin America") && paramEANFlagAttribute.isSelected("6204")) {
/* 3104 */         return true;
/*      */       }
/*      */       
/* 3107 */       if (paramString.equals("US Only") && paramEANFlagAttribute.isSelected("6221")) {
/* 3108 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 3112 */     addDebug("***** isQSMGeoSelected false");
/* 3113 */     return false;
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
/*      */   private void createT017ProductCategory(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws SQLException, MiddlewareException, IOException {
/* 3132 */     this.m_elist = getEntityList(getModelProdstructVEName());
/*      */     
/* 3134 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/* 3136 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3137 */       EANFlagAttribute eANFlagAttribute = null;
/*      */       
/* 3139 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 3141 */       eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 3142 */       if (eANFlagAttribute != null) {
/* 3143 */         if (eANFlagAttribute.isSelected("6199")) {
/* 3144 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific");
/*      */         }
/* 3146 */         if (eANFlagAttribute.isSelected("6200")) {
/* 3147 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North");
/*      */         }
/* 3149 */         if (eANFlagAttribute.isSelected("6198")) {
/* 3150 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa");
/*      */         }
/* 3152 */         if (eANFlagAttribute.isSelected("6204")) {
/* 3153 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America");
/*      */         }
/* 3155 */         if (eANFlagAttribute.isSelected("6221")) {
/* 3156 */           createT017ProductCategoryRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only");
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
/*      */   private void createT017ProductCategoryRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString) throws SQLException, MiddlewareException, IOException {
/* 3180 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/*      */     
/* 3182 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3183 */       StringBuffer stringBuffer = new StringBuffer();
/* 3184 */       String str1 = "";
/* 3185 */       String str2 = "";
/* 3186 */       String str3 = "";
/*      */       
/* 3188 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 3190 */       str3 = PokUtils.getAttributeValue(paramEntityItem1, "PRODCATEGORY", ",", "", false);
/*      */       
/* 3192 */       if (str3 != null && str3.length() > 0) {
/* 3193 */         String[] arrayOfString = str3.split(",");
/* 3194 */         for (byte b1 = 0; b1 < arrayOfString.length; b1++) {
/*      */           
/* 3196 */           stringBuffer.append(getValue("IFTYPE", "P"));
/* 3197 */           stringBuffer.append(getValue("CPDXA", arrayOfString[b1]));
/* 3198 */           if (paramString.equals("Latin America")) {
/* 3199 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/* 3200 */           } else if (paramString.equals("Europe/Middle East/Africa")) {
/* 3201 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/* 3202 */           } else if (paramString.equals("Asia Pacific")) {
/* 3203 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/* 3204 */           } else if (paramString.equals("US Only")) {
/* 3205 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 3206 */           } else if (paramString.equals("Canada and Caribbean North")) {
/* 3207 */             str1 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/*      */           } 
/* 3209 */           stringBuffer.append(getValue("ISLMPAL", str1));
/*      */           
/* 3211 */           str2 = PokUtils.getAttributeValue(entityItem, "MACHTYPEATR", "", "");
/* 3212 */           str2 = str2 + PokUtils.getAttributeValue(entityItem, "MODELATR", "", "");
/* 3213 */           stringBuffer.append(getValue("ISLMPRN", str2));
/*      */           
/* 3215 */           stringBuffer.append(getValue("TIMSTMP", ""));
/* 3216 */           stringBuffer.append(getValue("USERID", ""));
/*      */           
/* 3218 */           stringBuffer.append(NEWLINE);
/* 3219 */           paramOutputStreamWriter.write(stringBuffer.toString());
/* 3220 */           paramOutputStreamWriter.flush();
/*      */           
/* 3222 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "PRODSTRUCT", "FEATURE");
/* 3223 */           for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 3224 */             EntityItem entityItem1 = vector.elementAt(b2);
/* 3225 */             stringBuffer = new StringBuffer();
/*      */             
/* 3227 */             str2 = "";
/*      */             
/* 3229 */             stringBuffer.append(getValue("IFTYPE", "P"));
/* 3230 */             stringBuffer.append(getValue("CPDXA", 
/* 3231 */                   PokUtils.getAttributeValue(paramEntityItem1, "PRODCATEGORY", ",", "", false)));
/*      */             
/* 3233 */             stringBuffer.append(getValue("ISLMPAL", str1));
/*      */             
/* 3235 */             str2 = PokUtils.getAttributeValue(entityItem, "MACHTYPEATR", "", "");
/* 3236 */             str2 = str2 + PokUtils.getAttributeValue(entityItem1, "FEATURECODE", "", "");
/* 3237 */             stringBuffer.append(getValue("ISLMPRN", str2));
/*      */             
/* 3239 */             stringBuffer.append(getValue("TIMSTMP", ""));
/* 3240 */             stringBuffer.append(getValue("USERID", ""));
/*      */             
/* 3242 */             stringBuffer.append(NEWLINE);
/* 3243 */             paramOutputStreamWriter.write(stringBuffer.toString());
/* 3244 */             paramOutputStreamWriter.flush();
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
/*      */   private void createT020NPMesUpgrade(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 3265 */     this.m_elist = getEntityList(getNPMesUpgradeVEName());
/*      */     
/* 3267 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/*      */     
/* 3269 */     String str = "";
/* 3270 */     boolean bool = false;
/*      */     
/* 3272 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3273 */       EANFlagAttribute eANFlagAttribute = null;
/*      */       
/* 3275 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */       
/* 3277 */       str = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/* 3278 */       if (str.equals("EPIC")) {
/* 3279 */         bool = true;
/*      */       }
/*      */       
/* 3282 */       eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 3283 */       if (eANFlagAttribute != null) {
/* 3284 */         if (eANFlagAttribute.isSelected("6199")) {
/* 3285 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Asia Pacific", bool);
/*      */         }
/* 3287 */         if (eANFlagAttribute.isSelected("6200")) {
/* 3288 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Canada and Caribbean North", bool);
/*      */         }
/* 3290 */         if (eANFlagAttribute.isSelected("6198")) {
/* 3291 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Europe/Middle East/Africa", bool);
/*      */         }
/* 3293 */         if (eANFlagAttribute.isSelected("6204")) {
/* 3294 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "Latin America", bool);
/*      */         }
/* 3296 */         if (eANFlagAttribute.isSelected("6221")) {
/* 3297 */           createT020NPMesUpgradeRecords(paramEntityItem, paramOutputStreamWriter, entityItem, "US Only", bool);
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
/*      */   private void createT020NPMesUpgradeRecords(EntityItem paramEntityItem1, OutputStreamWriter paramOutputStreamWriter, EntityItem paramEntityItem2, String paramString, boolean paramBoolean) throws IOException, SQLException, MiddlewareException {
/* 3323 */     StringBuffer stringBuffer = new StringBuffer();
/* 3324 */     String str1 = "";
/* 3325 */     String str2 = "";
/* 3326 */     String str3 = "";
/* 3327 */     String str4 = "";
/*      */     
/* 3329 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem2, "MODELCONVERTAVAIL", "MODELCONVERT");
/*      */     
/* 3331 */     for (byte b = 0; b < vector.size(); b++) {
/* 3332 */       String str5 = "";
/* 3333 */       String str6 = "";
/*      */       
/* 3335 */       EntityItem entityItem1 = vector.elementAt(b);
/*      */       
/* 3337 */       stringBuffer.append(getValue("IFTYPE", "N"));
/* 3338 */       EntityItem entityItem2 = null;
/* 3339 */       String str7 = "";
/* 3340 */       String str8 = "";
/* 3341 */       String str9 = "";
/* 3342 */       String str10 = "";
/* 3343 */       String str11 = "";
/* 3344 */       String str12 = "";
/* 3345 */       String str13 = "";
/* 3346 */       String str14 = "";
/* 3347 */       List<EntityItem> list = searchForAvailTypeLO(entityItem1, "Last Order");
/* 3348 */       if (list != null) {
/*      */         
/* 3350 */         for (byte b1 = 0; b1 < list.size(); b1++) {
/* 3351 */           entityItem2 = list.get(b1);
/* 3352 */           addDebug("***** modelconvert avail list= " + entityItem2);
/* 3353 */           str7 = PokUtils.getAttributeValue(entityItem2, "GENAREASELECTION", ",", "");
/* 3354 */           if (str7.indexOf("Latin America") != -1) {
/* 3355 */             str8 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*      */           }
/*      */ 
/*      */           
/* 3359 */           if (str7.indexOf("Europe/Middle East/Africa") != -1) {
/* 3360 */             str9 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*      */           }
/*      */ 
/*      */           
/* 3364 */           if (str7.indexOf("Asia Pacific") != -1) {
/* 3365 */             str10 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*      */           }
/*      */ 
/*      */           
/* 3369 */           if (str7.indexOf("US Only") != -1) {
/* 3370 */             str11 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*      */           }
/*      */ 
/*      */           
/* 3374 */           if (str7.indexOf("Canada and Caribbean North") != -1) {
/* 3375 */             str12 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*      */           
/*      */           }
/* 3378 */           else if (str7.indexOf("Worldwide") != -1) {
/* 3379 */             str8 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/* 3380 */             str9 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/* 3381 */             str10 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/* 3382 */             str11 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/* 3383 */             str12 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", "", "");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 3388 */         str13 = "2050-12-31";
/*      */       } 
/*      */       
/* 3391 */       if (paramString.equals("Latin America")) {
/* 3392 */         str3 = "601";
/* 3393 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "LDOCNO", "", "");
/*      */         
/* 3395 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 3396 */         str14 = str8;
/* 3397 */         addDebug("***** modelconvert LA= " + str14);
/* 3398 */       } else if (paramString.equals("Europe/Middle East/Africa")) {
/* 3399 */         str3 = "999";
/* 3400 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "EDOCNO", "", "");
/*      */         
/* 3402 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 3403 */         str14 = str9;
/* 3404 */         addDebug("***** modelconvert EMEA= " + str14);
/* 3405 */       } else if (paramString.equals("Asia Pacific")) {
/* 3406 */         str3 = "872";
/* 3407 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "ADOCNO", "", "");
/*      */         
/* 3409 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/* 3410 */         str14 = str10;
/* 3411 */         addDebug("***** modelconvert AP= " + str14);
/* 3412 */       } else if (paramString.equals("US Only")) {
/* 3413 */         str3 = "897";
/* 3414 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/*      */         
/* 3416 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 3417 */         str14 = str11;
/* 3418 */         addDebug("***** modelconvert US= " + str14);
/*      */       }
/* 3420 */       else if (paramString.equals("Canada and Caribbean North")) {
/* 3421 */         str3 = "649";
/* 3422 */         str1 = PokUtils.getAttributeValue(paramEntityItem1, "CDOCNO", "", "");
/*      */         
/* 3424 */         str4 = paramBoolean ? PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "") : PokUtils.getAttributeValue(paramEntityItem1, "USDOCNO", "", "");
/* 3425 */         str14 = str12;
/* 3426 */         addDebug("***** modelconvert CCN= " + str14);
/*      */       } 
/*      */       
/* 3429 */       stringBuffer.append(getValue("IOPUCTY", str3));
/* 3430 */       stringBuffer.append(getValue("ISLMPAL", str1));
/* 3431 */       stringBuffer.append(getValue("ISLMRFA", str4));
/*      */       
/* 3433 */       str2 = PokUtils.getAttributeValue(entityItem1, "TOMACHTYPE", "", "");
/* 3434 */       str2 = str2 + PokUtils.getAttributeValue(entityItem1, "TOMODEL", "", "");
/* 3435 */       stringBuffer.append(getValue("ISLMPRN", str2));
/* 3436 */       stringBuffer.append(getValue("CSLMPCI", "NP"));
/* 3437 */       stringBuffer.append(getValue("FPUNINC", "2"));
/* 3438 */       stringBuffer.append(getValue("CAOAV", ""));
/* 3439 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", "")));
/* 3440 */       stringBuffer.append(getValue("DSLMCPO", PokUtils.getAttributeValue(paramEntityItem1, "ANNDATE", "", "")));
/* 3441 */       if (str14 != "") {
/* 3442 */         str13 = str14;
/*      */       } else {
/* 3444 */         str13 = "2050-12-31";
/*      */       } 
/* 3446 */       stringBuffer.append(getValue("DSLMWDN", str13));
/*      */       
/* 3448 */       addDebug("***** modelconvert WD date= " + str13);
/*      */       
/* 3450 */       str5 = PokUtils.getAttributeValue(entityItem1, "FROMMACHTYPE", "", "");
/* 3451 */       str6 = PokUtils.getAttributeValue(entityItem1, "FROMMODEL", "", "");
/* 3452 */       stringBuffer.append(getValue("QSMNPMT", str5));
/* 3453 */       stringBuffer.append(getValue("QSMNPMM", str6));
/* 3454 */       stringBuffer.append(getValue("TIMSTMP", ""));
/* 3455 */       stringBuffer.append(getValue("USERID", ""));
/*      */       
/* 3457 */       stringBuffer.append(NEWLINE);
/* 3458 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 3459 */       paramOutputStreamWriter.flush();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private List searchForAvailTypeLO(EntityItem paramEntityItem, String paramString) {
/* 3465 */     ArrayList<EntityItem> arrayList = new ArrayList();
/*      */ 
/*      */     
/* 3468 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "MODELCONVERTAVAIL", "AVAIL");
/*      */     
/* 3470 */     for (byte b = 0; b < vector.size(); b++) {
/* 3471 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 3473 */       String str = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", ",", "", false);
/*      */       
/* 3475 */       if (paramString.equals(str)) {
/* 3476 */         arrayList.add(entityItem);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 3481 */     return arrayList;
/*      */   }
/*      */ 
/*      */   
/*      */   private void createT512ReleaseTo(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException {
/* 3486 */     StringBuffer stringBuffer = new StringBuffer();
/* 3487 */     String str1 = "";
/* 3488 */     String str2 = "";
/*      */ 
/*      */     
/* 3491 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("GENAREASELECTION");
/* 3492 */     if (eANFlagAttribute != null && (
/* 3493 */       eANFlagAttribute.isSelected("6199") || eANFlagAttribute.isSelected("1999"))) {
/* 3494 */       stringBuffer.append(getValue("IFTYPE", "R"));
/* 3495 */       stringBuffer.append(getValue("IOPUCTY", "872"));
/* 3496 */       str1 = PokUtils.getAttributeValue(paramEntityItem, "ADOCNO", "", "");
/* 3497 */       stringBuffer.append(getValue("ISLMPAL", str1));
/*      */       
/* 3499 */       str2 = PokUtils.getAttributeValue(paramEntityItem, "ANNNUMBER", "", "");
/* 3500 */       stringBuffer.append(getValue("ISLMRFA", str2));
/* 3501 */       stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/* 3502 */       stringBuffer.append(getValue("DSLMEFF", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/* 3503 */       stringBuffer.append(getValue("CSLMRCH", ""));
/* 3504 */       stringBuffer.append(getValue("CSLMNUM", str1));
/* 3505 */       stringBuffer.append(getValue("FSLMAPG", "Y"));
/* 3506 */       stringBuffer.append(getValue("FSLMASP", "N"));
/* 3507 */       stringBuffer.append(getValue("FSLMJAP", "N"));
/* 3508 */       if (eANFlagAttribute != null) {
/*      */         
/* 3510 */         EANFlagAttribute eANFlagAttribute1 = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/*      */         
/* 3512 */         if (eANFlagAttribute1.isSelected("1439")) {
/* 3513 */           stringBuffer.append(getValue("FSLMAUS", "Y"));
/*      */         } else {
/* 3515 */           stringBuffer.append(getValue("FSLMAUS", "N"));
/*      */         } 
/*      */         
/* 3518 */         if (eANFlagAttribute1.isSelected("1444")) {
/* 3519 */           stringBuffer.append(getValue("FSLMBGL", "Y"));
/*      */         } else {
/* 3521 */           stringBuffer.append(getValue("FSLMBGL", "N"));
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3526 */         stringBuffer.append(getValue("FSLMBRU", "N"));
/*      */ 
/*      */ 
/*      */         
/* 3530 */         if (eANFlagAttribute1.isSelected("1524")) {
/* 3531 */           stringBuffer.append(getValue("FSLMHKG", "Y"));
/*      */         } else {
/* 3533 */           stringBuffer.append(getValue("FSLMHKG", "N"));
/*      */         } 
/* 3535 */         if (eANFlagAttribute1.isSelected("1528")) {
/* 3536 */           stringBuffer.append(getValue("FSLMIDN", "Y"));
/*      */         } else {
/* 3538 */           stringBuffer.append(getValue("FSLMIDN", "N"));
/*      */         } 
/* 3540 */         if (eANFlagAttribute1.isSelected("1527")) {
/* 3541 */           stringBuffer.append(getValue("FSLMIND", "Y"));
/*      */         } else {
/* 3543 */           stringBuffer.append(getValue("FSLMIND", "N"));
/*      */         } 
/* 3545 */         if (eANFlagAttribute1.isSelected("1541")) {
/* 3546 */           stringBuffer.append(getValue("FSLMKOR", "Y"));
/*      */         } else {
/* 3548 */           stringBuffer.append(getValue("FSLMKOR", "N"));
/*      */         } 
/* 3550 */         if (eANFlagAttribute1.isSelected("1553")) {
/* 3551 */           stringBuffer.append(getValue("FSLMMAC", "Y"));
/*      */         } else {
/* 3553 */           stringBuffer.append(getValue("FSLMMAC", "N"));
/*      */         } 
/* 3555 */         if (eANFlagAttribute1.isSelected("1557")) {
/* 3556 */           stringBuffer.append(getValue("FSLMMAL", "Y"));
/*      */         } else {
/* 3558 */           stringBuffer.append(getValue("FSLMMAL", "N"));
/*      */         } 
/* 3560 */         if (eANFlagAttribute1.isSelected("1574")) {
/* 3561 */           stringBuffer.append(getValue("FSLMMYA", "Y"));
/*      */         } else {
/* 3563 */           stringBuffer.append(getValue("FSLMMYA", "N"));
/*      */         } 
/* 3565 */         if (eANFlagAttribute1.isSelected("1581")) {
/* 3566 */           stringBuffer.append(getValue("FSLMNZL", "Y"));
/*      */         } else {
/* 3568 */           stringBuffer.append(getValue("FSLMNZL", "N"));
/*      */         } 
/* 3570 */         if (eANFlagAttribute1.isSelected("1597")) {
/* 3571 */           stringBuffer.append(getValue("FSLMPHI", "Y"));
/*      */         } else {
/* 3573 */           stringBuffer.append(getValue("FSLMPHI", "N"));
/*      */         } 
/* 3575 */         if (eANFlagAttribute1.isSelected("1470")) {
/* 3576 */           stringBuffer.append(getValue("FSLMPRC", "Y"));
/*      */         } else {
/* 3578 */           stringBuffer.append(getValue("FSLMPRC", "N"));
/*      */         } 
/* 3580 */         if (eANFlagAttribute1.isSelected("1627")) {
/* 3581 */           stringBuffer.append(getValue("FSLMSLA", "Y"));
/*      */         } else {
/* 3583 */           stringBuffer.append(getValue("FSLMSLA", "N"));
/*      */         } 
/* 3585 */         if (eANFlagAttribute1.isSelected("1619")) {
/* 3586 */           stringBuffer.append(getValue("FSLMSNG", "Y"));
/*      */         } else {
/* 3588 */           stringBuffer.append(getValue("FSLMSNG", "N"));
/*      */         } 
/* 3590 */         if (eANFlagAttribute1.isSelected("1635")) {
/* 3591 */           stringBuffer.append(getValue("FSLMTAI", "Y"));
/*      */         } else {
/* 3593 */           stringBuffer.append(getValue("FSLMTAI", "N"));
/*      */         } 
/* 3595 */         if (eANFlagAttribute1.isSelected("1638")) {
/* 3596 */           stringBuffer.append(getValue("FSLMTHA", "Y"));
/*      */         } else {
/* 3598 */           stringBuffer.append(getValue("FSLMTHA", "N"));
/*      */         } 
/*      */       } 
/* 3601 */       stringBuffer.append(getValue("TIMSTMP", " "));
/* 3602 */       stringBuffer.append(getValue("USERID", " "));
/*      */       
/* 3604 */       stringBuffer.append(NEWLINE);
/* 3605 */       paramOutputStreamWriter.write(stringBuffer.toString());
/* 3606 */       paramOutputStreamWriter.flush();
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
/*      */   private void createT632TypeModelFeatureRelation(EntityItem paramEntityItem, OutputStreamWriter paramOutputStreamWriter) throws IOException, SQLException, MiddlewareException {
/* 3629 */     String str = "";
/* 3630 */     boolean bool = false;
/*      */     
/* 3632 */     this.m_elist = getEntityList(getT006ProdstructVEName());
/*      */     
/* 3634 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 3635 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3636 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */       
/* 3639 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/*      */       
/* 3641 */       if (eANFlagAttribute != null)
/*      */       {
/* 3643 */         if (eANFlagAttribute.isSelected("6221")) {
/*      */           
/* 3645 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "OOFAVAIL", "PRODSTRUCT");
/*      */ 
/*      */ 
/*      */           
/* 3649 */           String str1 = "";
/* 3650 */           str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/* 3651 */           str = PokUtils.getAttributeValue(entityItem, "AVAILANNTYPE", "", "");
/* 3652 */           if (str.equals("EPIC")) {
/* 3653 */             bool = true;
/*      */           }
/*      */           
/* 3656 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/*      */             
/* 3658 */             StringBuffer stringBuffer = new StringBuffer();
/* 3659 */             EntityItem entityItem1 = vector.elementAt(b1);
/*      */             
/* 3661 */             ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, getT006FeatureVEName());
/*      */             
/* 3663 */             EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, entityItem1
/* 3664 */                     .getEntityType(), entityItem1.getEntityID()) });
/*      */             
/* 3666 */             EntityGroup entityGroup1 = entityList.getEntityGroup("FEATURE");
/* 3667 */             EntityGroup entityGroup2 = entityList.getEntityGroup("MODEL");
/* 3668 */             EntityItem entityItem2 = entityGroup1.getEntityItem(0);
/* 3669 */             EntityItem entityItem3 = entityGroup2.getEntityItem(0);
/*      */             
/* 3671 */             stringBuffer = new StringBuffer();
/* 3672 */             String str2 = "";
/* 3673 */             String str3 = "";
/* 3674 */             String str4 = "";
/* 3675 */             String str5 = "";
/*      */             
/* 3677 */             stringBuffer.append(getValue("IFTYPE", "T"));
/* 3678 */             str3 = PokUtils.getAttributeValue(paramEntityItem, "USDOCNO", "", "");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3684 */             str2 = getRFANumber(paramEntityItem, bool, entityItem);
/* 3685 */             addDebug("*****mlm ISLMRFA=" + str2);
/* 3686 */             stringBuffer.append(getValue("IOPUCTY", "897"));
/* 3687 */             stringBuffer.append(getValue("ISLMPAL", str3));
/* 3688 */             stringBuffer.append(getValue("ISLMRFA", str2));
/* 3689 */             stringBuffer.append(getValue("ISLMTYP", PokUtils.getAttributeValue(entityItem3, "MACHTYPEATR", "", "")));
/* 3690 */             stringBuffer.append(getValue("ISLMMOD", PokUtils.getAttributeValue(entityItem3, "MODELATR", "", "")));
/* 3691 */             stringBuffer.append(getValue("ISLMFTR", PokUtils.getAttributeValue(entityItem2, "FEATURECODE", "", "")));
/* 3692 */             stringBuffer.append(getValue("ISLMXX1", ""));
/* 3693 */             stringBuffer.append(getValue("CSLMPCI", "TR"));
/* 3694 */             stringBuffer.append(getValue("FPUNINC", "2"));
/* 3695 */             stringBuffer.append(getValue("CAOAV", ""));
/* 3696 */             stringBuffer.append(getValue("DSLMCPA", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/* 3697 */             stringBuffer.append(getValue("DSLMCPO", PokUtils.getAttributeValue(paramEntityItem, "ANNDATE", "", "")));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3704 */             stringBuffer.append(getValue("DSLMWDN", getTMFWDDate(entityItem1)));
/* 3705 */             str4 = PokUtils.getAttributeValue(entityItem1, "ORDERCODE", "", "");
/*      */             
/* 3707 */             if (str4.equals("MES")) {
/* 3708 */               stringBuffer.append(getValue("FSLMMES", "Y"));
/*      */             } else {
/* 3710 */               stringBuffer.append(getValue("FSLMMES", "N"));
/*      */             } 
/*      */             
/* 3713 */             if (str4.equals("Initial")) {
/* 3714 */               stringBuffer.append(getValue("FSLMPIO", "Y"));
/*      */             } else {
/* 3716 */               stringBuffer.append(getValue("FSLMPIO", "N"));
/*      */             } 
/*      */             
/* 3719 */             String str6 = PokUtils.getAttributeValue(entityItem1, "INSTALL", "", "");
/*      */             
/* 3721 */             if (str6.equals("CIF")) {
/* 3722 */               str5 = "01";
/*      */             } else {
/* 3724 */               str5 = "00";
/*      */             } 
/*      */             
/* 3727 */             stringBuffer.append(getValue("QSLMCSU", str5));
/* 3728 */             stringBuffer.append(getValue("TIMSTMP", ""));
/* 3729 */             stringBuffer.append(getValue("USERID", ""));
/* 3730 */             stringBuffer.append(getValue("FSLMRFM", ""));
/*      */             
/* 3732 */             stringBuffer.append(NEWLINE);
/* 3733 */             paramOutputStreamWriter.write(stringBuffer.toString());
/* 3734 */             paramOutputStreamWriter.flush();
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getTMFWDDate(EntityItem paramEntityItem) {
/* 3743 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "OOFAVAIL", "AVAIL");
/*      */ 
/*      */ 
/*      */     
/* 3747 */     addDebug("TMF id " + paramEntityItem.getEntityID() + " link AVALI size:" + vector.size());
/* 3748 */     if (vector.size() > 0) {
/* 3749 */       for (byte b = 0; b < vector.size(); b++) {
/* 3750 */         EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */         
/* 3753 */         String str = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*      */         
/* 3755 */         if (str.equals("Last Order")) {
/* 3756 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 3757 */           if (eANFlagAttribute != null && 
/* 3758 */             eANFlagAttribute.isSelected("6221")) {
/* 3759 */             addDebug("EFFECTIVEDATE:" + PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false));
/* 3760 */             return PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 3766 */     return "2050-12-31";
/*      */   }
/*      */   public String getTMFWDDateForFeature(EntityItem paramEntityItem) {
/* 3769 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "OOFAVAIL", "AVAIL");
/* 3770 */     String str = null;
/*      */ 
/*      */     
/* 3773 */     addDebug("TMF id " + paramEntityItem.getEntityID() + " link AVALI size:" + vector.size());
/* 3774 */     if (vector.size() > 0) {
/* 3775 */       for (byte b = 0; b < vector.size(); b++) {
/* 3776 */         EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */         
/* 3779 */         String str1 = PokUtils.getAttributeValue(entityItem, "AVAILTYPE", "", "");
/*      */         
/* 3781 */         if (str1.equals("Last Order")) {
/* 3782 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("QSMGEO");
/* 3783 */           if (eANFlagAttribute != null && 
/* 3784 */             eANFlagAttribute.isSelected("6221")) {
/* 3785 */             if (str == null) {
/* 3786 */               str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false);
/*      */             } else {
/* 3788 */               str = (str.compareTo(PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false)) > 0) ? str : PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ",", "", false);
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3797 */     return (str == null) ? "2050-12-31" : str;
/*      */   }
/*      */   private String getRFANumber(EntityItem paramEntityItem1, boolean paramBoolean, EntityItem paramEntityItem2) {
/*      */     String str;
/* 3801 */     if (paramBoolean) {
/* 3802 */       str = PokUtils.getAttributeValue(paramEntityItem2, "EPICNUMBER", "", "");
/*      */     } else {
/* 3804 */       str = "R" + PokUtils.getAttributeValue(paramEntityItem1, "ANNNUMBER", "", "");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3810 */     return str;
/*      */   }
/*      */   
/*      */   protected String getValue(String paramString1, String paramString2) {
/* 3814 */     if (paramString2 == null)
/* 3815 */       paramString2 = ""; 
/* 3816 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 3817 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1).toString());
/* 3818 */     if (i == j)
/* 3819 */       return paramString2; 
/* 3820 */     if (i > j) {
/* 3821 */       return paramString2.substring(0, j);
/*      */     }
/* 3823 */     return paramString2 + getBlank(j - i);
/*      */   }
/*      */   
/*      */   protected String getBlank(int paramInt) {
/* 3827 */     StringBuffer stringBuffer = new StringBuffer();
/* 3828 */     while (paramInt > 0) {
/* 3829 */       stringBuffer.append(" ");
/* 3830 */       paramInt--;
/*      */     } 
/* 3832 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private String getNumValue(String paramString1, String paramString2) {
/* 3837 */     if (paramString2 == null)
/* 3838 */       paramString2 = ""; 
/* 3839 */     int i = (paramString2 == null) ? 0 : paramString2.length();
/* 3840 */     int j = Integer.parseInt(COLUMN_LENGTH.get(paramString1).toString());
/* 3841 */     if (i == j)
/* 3842 */       return paramString2; 
/* 3843 */     if (i > j) {
/* 3844 */       return paramString2.substring(0, j);
/*      */     }
/* 3846 */     paramString2 = paramString2.trim();
/* 3847 */     int k = i;
/* 3848 */     while (k < j) {
/* 3849 */       paramString2 = "0" + paramString2;
/* 3850 */       k++;
/*      */     } 
/*      */     
/* 3853 */     return paramString2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityList getEntityList(String paramString) throws SQLException, MiddlewareException {
/* 3862 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, this.m_prof, paramString);
/*      */     
/* 3864 */     EntityList entityList = this.m_db.getEntityList(this.m_prof, extractActionItem, new EntityItem[] { new EntityItem(null, this.m_prof, 
/* 3865 */             getEntityType(), getEntityID()) });
/*      */     
/* 3867 */     addDebug("EntityList for " + this.m_prof.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 3868 */         PokUtils.outputList(entityList));
/*      */     
/* 3870 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT002ModelVEName() {
/* 3879 */     return "QSMFULL";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006FeatureVEName() {
/* 3888 */     return "QSMFULL2";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006ProdstructVEName() {
/* 3897 */     return "QSMFULL1";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getModelProdstructVEName() {
/* 3906 */     return "QSMFULL3";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNPMesUpgradeVEName() {
/* 3915 */     return "QSMFULL4";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getT006ModelLinksVEName() {
/* 3924 */     return "QSMFULL5";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFeatureVEName() {
/* 3933 */     return "QSMFULL6";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean exeFtpShell(String paramString) {
/* 3941 */     String str1 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_script", null) + " -f " + paramString;
/* 3942 */     String str2 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_inipath", null);
/* 3943 */     if (str2 != null)
/* 3944 */       str1 = str1 + " -i " + str2; 
/* 3945 */     if (this.dir != null)
/* 3946 */       str1 = str1 + " -d " + this.dir; 
/* 3947 */     if (this.fileprefix != null)
/* 3948 */       str1 = str1 + " -p " + this.fileprefix; 
/* 3949 */     String str3 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_targetfilename", null);
/* 3950 */     if (str3 != null)
/* 3951 */       str1 = str1 + " -t " + str3; 
/* 3952 */     String str4 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_logpath", null);
/* 3953 */     if (str4 != null)
/* 3954 */       str1 = str1 + " -l " + str4; 
/* 3955 */     String str5 = ABRServerProperties.getValue(this.m_abri.getABRCode(), "_backuppath", null);
/* 3956 */     if (str5 != null)
/* 3957 */       str1 = str1 + " -b " + str5; 
/* 3958 */     Runtime runtime = Runtime.getRuntime();
/* 3959 */     String str6 = "";
/* 3960 */     BufferedReader bufferedReader = null;
/* 3961 */     BufferedInputStream bufferedInputStream = null;
/*      */     
/*      */     try {
/* 3964 */       Process process = runtime.exec(str1);
/* 3965 */       if (process.waitFor() != 0) {
/* 3966 */         return false;
/*      */       }
/* 3968 */       bufferedInputStream = new BufferedInputStream(process.getInputStream());
/* 3969 */       bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));
/* 3970 */       while ((this.lineStr = bufferedReader.readLine()) != null) {
/* 3971 */         str6 = str6 + this.lineStr;
/* 3972 */         if (this.lineStr.indexOf("FAILD") > -1) {
/* 3973 */           return false;
/*      */         }
/*      */       } 
/* 3976 */     } catch (Exception exception) {
/* 3977 */       exception.printStackTrace();
/* 3978 */       return false;
/*      */     } finally {
/* 3980 */       if (bufferedReader != null) {
/*      */         try {
/* 3982 */           bufferedReader.close();
/* 3983 */           bufferedInputStream.close();
/* 3984 */         } catch (IOException iOException) {
/* 3985 */           iOException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 3989 */     return !(str6 == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 3996 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString, Object[] paramArrayOfObject) {
/* 4004 */     String str = getBundle().getString(paramString);
/* 4005 */     if (paramArrayOfObject != null) {
/* 4006 */       MessageFormat messageFormat = new MessageFormat(str);
/* 4007 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 4010 */     addOutput(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 4017 */     if (3 <= DEBUG_LVL)
/*      */     {
/* 4019 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 4027 */     addOutput(paramString);
/* 4028 */     setReturnCode(-1);
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
/* 4039 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 4040 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 4043 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 4044 */     Object[] arrayOfObject = new Object[2];
/* 4045 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 4046 */     arrayOfObject[1] = this.navName;
/*      */     
/* 4048 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 4056 */     String str = getBundle().getString(paramString2);
/*      */     
/* 4058 */     if (paramArrayOfObject != null) {
/* 4059 */       MessageFormat messageFormat = new MessageFormat(str);
/* 4060 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 4063 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 4072 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 4081 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 4084 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 4085 */     if (eANList == null) {
/* 4086 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 4087 */       eANList = entityGroup.getMetaAttribute();
/*      */       
/* 4089 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 4091 */     for (byte b = 0; b < eANList.size(); b++) {
/* 4092 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 4093 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 4094 */       if (b + 1 < eANList.size()) {
/* 4095 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 4099 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 4108 */     return "1.0";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 4117 */     return "QSMFULLABRSTATUS";
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\QSMFULLABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */