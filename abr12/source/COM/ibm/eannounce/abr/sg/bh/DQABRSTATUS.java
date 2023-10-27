/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.AttrComparator;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.darule.DARuleEngineMgr;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.ChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANEntity;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANTableWrapper;
/*      */ import COM.ibm.eannounce.objects.EntityChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.RowSelectableTable;
/*      */ import COM.ibm.eannounce.objects.WorkflowActionItem;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.Attribute;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.MultipleFlag;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.objects.Text;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.Date;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
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
/*      */ public abstract class DQABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*  166 */   private StringBuffer rptSb = new StringBuffer();
/*  167 */   private Hashtable metaTbl = new Hashtable<>();
/*      */   
/*  169 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  170 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   private static final Hashtable STATUS_TBL;
/*      */   private static final Hashtable DQ_TBL;
/*  174 */   private ResourceBundle dqBundle = null;
/*      */ 
/*      */   
/*      */   protected static final String STATUS_DRAFT = "0010";
/*      */ 
/*      */   
/*      */   protected static final String STATUS_FINAL = "0020";
/*      */ 
/*      */   
/*      */   protected static final String STATUS_R4REVIEW = "0040";
/*      */ 
/*      */   
/*      */   protected static final String STATUS_CHGREQ = "0050";
/*      */ 
/*      */   
/*      */   protected static final String DQ_DRAFT = "DRAFT";
/*      */ 
/*      */   
/*      */   protected static final String DQ_FINAL = "FINAL";
/*      */ 
/*      */   
/*      */   protected static final String DQ_R4REVIEW = "REVIEW";
/*      */ 
/*      */   
/*      */   protected static final String DQ_CHGREQ = "0050";
/*      */ 
/*      */   
/*      */   protected static final String FOREVER_DATE = "9999-12-31";
/*      */ 
/*      */   
/*      */   protected static final String EPOCH_DATE = "1980-01-01";
/*      */ 
/*      */   
/*      */   protected static final String HARDWARE = "100";
/*      */ 
/*      */   
/*      */   protected static final String SOFTWARE = "101";
/*      */ 
/*      */   
/*      */   protected static final String SERVICE = "102";
/*      */ 
/*      */   
/*      */   protected static final String SYSTEM = "126";
/*      */ 
/*      */   
/*      */   protected static final String BASE = "150";
/*      */ 
/*      */   
/*      */   protected static final String ABR_QUEUED = "0020";
/*      */ 
/*      */   
/*      */   protected static final String ABR_PASSED = "0030";
/*      */ 
/*      */   
/*      */   protected static final String ABR_INPROCESS = "0050";
/*      */ 
/*      */   
/*      */   protected static final String SAPLABR_QUEUED = "0020";
/*      */ 
/*      */   
/*      */   protected static final String DEFWARR_Yes = "Y1";
/*      */ 
/*      */   
/*      */   protected static final String DEFWARR_No = "N1";
/*      */ 
/*      */   
/*      */   protected static final String DEFWARR_No_Desc = "No";
/*      */ 
/*      */   
/*      */   protected static final String BHPRODHIERCD_No_ProdHCode = "BHPH0000";
/*      */ 
/*      */   
/*      */   protected static final String ABRWAITODS = "0020";
/*      */ 
/*      */   
/*      */   protected static final String ABRWAITODS2 = "0020";
/*      */   
/*      */   protected static final String SAPL_NOTREADY = "10";
/*      */   
/*      */   protected static final String SAPL_NA = "90";
/*      */   
/*      */   protected static final String PLANNEDAVAIL = "146";
/*      */   
/*      */   protected static final String MESPLANNEDAVAIL = "171";
/*      */   
/*      */   protected static final String LASTORDERAVAIL = "149";
/*      */   
/*      */   protected static final String MESLASTORDERAVAIL = "172";
/*      */   
/*      */   protected static final String FIRSTORDERAVAIL = "143";
/*      */   
/*      */   protected static final String EOSAVAIL = "151";
/*      */   
/*      */   protected static final String EOMAVAIL = "200";
/*      */   
/*      */   protected static final String EODAVAIL = "201";
/*      */   
/*      */   protected static final String SPECBID_NO = "11457";
/*      */   
/*      */   private static final String RPQ_ILISTED = "120";
/*      */   
/*      */   private static final String RPQ_PLISTED = "130";
/*      */   
/*      */   private static final String RPQ_RLISTED = "0140";
/*      */   
/*      */   private static final String PRIMARY_FC = "100";
/*      */   
/*      */   private static final String SECONDARY_FC = "110";
/*      */   
/*      */   private static final Set FCTYPE_SET;
/*      */   
/*      */   private static final Set RPQ_FCTYPE_SET;
/*      */   
/*      */   protected static final String DOMAIN_NOT_IN_LIST = "DOMAIN_NOT_IN_LIST";
/*      */   
/*      */   protected static final String ANNTYPE_EOL = "14";
/*      */   
/*      */   protected static final String ANNTYPE_NEW = "19";
/*      */   
/*      */   protected static final String ANNTYPE_EOLDS = "13";
/*      */   
/*      */   protected static final String LIFECYCLE_Develop = "LF02";
/*      */   
/*      */   protected static final String LIFECYCLE_Plan = "LF01";
/*      */   
/*      */   protected static final String LIFECYCLE_Launch = "LF03";
/*      */   
/*      */   protected static final String AVAILANNTYPE_RFA = "RFA";
/*      */   
/*      */   protected static final String AVAILANNTYPE_EPIC = "EPIC";
/*      */   
/*      */   protected static final String AVAILANNTYPE_UN = "UnAnnounced";
/*      */   
/*      */   protected static final String SWFCCAT_VALUE_METRIC = "319";
/*      */   
/*      */   private static final int PAUSE_TIME = 5000;
/*      */   
/*      */   private static final int DATE_ID = 11;
/*      */   
/*      */   protected static final String OLD_DATA_ANNDATE = "2010-03-01";
/*      */   
/*  315 */   private String failedStr = "Failed";
/*  316 */   private String dqCheck = "Failed";
/*      */   
/*  318 */   private Vector vctReturnsEntityKeys = new Vector();
/*  319 */   private Vector errMsgVct = new Vector();
/*      */   
/*      */   private static final Hashtable STATUS_ORDER_TBL;
/*      */   
/*      */   private static final Hashtable STATUS_ATTR_TBL;
/*      */   
/*      */   private static final String GENAREASELECTION_WW = "1999";
/*  326 */   private String navName = "";
/*      */   private boolean bdomainInList = false;
/*  328 */   private EntityList otherList = null;
/*      */   protected boolean domainInList() {
/*  330 */     return this.bdomainInList;
/*  331 */   } private String strNow = null; protected String getCurrentDate() {
/*  332 */     return this.strNow;
/*      */   }
/*      */   
/*      */   private boolean hasWarning = false;
/*  336 */   private static String[] warningMsg = new String[] { "CAT2W" };
/*      */   
/*      */   private static final Vector CHECKFIRSTFINALVEC;
/*      */   
/*      */   private static final Hashtable SAPL_TRANS_TBL;
/*      */   
/*      */   private static final Hashtable ABR_ATTR_TBL;
/*      */   
/*      */   private static final Hashtable NDN_TBL;
/*      */   private static final String NULL_DTS = "2009-01-01-00.00.00.000000";
/*      */   
/*      */   protected String getQueuedValue(String paramString) {
/*  348 */     return 
/*  349 */       ABRServerProperties.getABRQueuedValue(this.m_abri
/*  350 */         .getABRCode() + "_" + paramString);
/*      */   }
/*      */   
/*      */   protected String getRFRQueuedValue(String paramString) {
/*  354 */     return 
/*  355 */       ABRServerProperties.getABRRFRQueuedValue(this.m_abri
/*  356 */         .getABRCode() + "_" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  363 */     FCTYPE_SET = new HashSet();
/*  364 */     FCTYPE_SET.add("100");
/*  365 */     FCTYPE_SET.add("110");
/*      */     
/*  367 */     RPQ_FCTYPE_SET = new HashSet();
/*  368 */     RPQ_FCTYPE_SET.add("120");
/*  369 */     RPQ_FCTYPE_SET.add("130");
/*  370 */     RPQ_FCTYPE_SET.add("0140");
/*      */     
/*  372 */     NDN_TBL = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  383 */     NDN nDN1 = new NDN("MODEL", "(TM)");
/*  384 */     nDN1.addAttr("MACHTYPEATR");
/*  385 */     nDN1.addAttr("MODELATR");
/*  386 */     nDN1.addAttr("COFCAT");
/*  387 */     nDN1.addAttr("COFSUBCAT");
/*  388 */     nDN1.addAttr("COFGRP");
/*  389 */     nDN1.addAttr("COFSUBGRP");
/*  390 */     NDN nDN2 = new NDN("FEATURE", "(FC)");
/*  391 */     nDN2.addAttr("FEATURECODE");
/*  392 */     nDN1.setNext(nDN2);
/*  393 */     NDN_TBL.put("PRODSTRUCT", nDN1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  405 */     nDN1 = new NDN("MODEL", "(TM)");
/*  406 */     nDN1.addAttr("MACHTYPEATR");
/*  407 */     nDN1.addAttr("MODELATR");
/*  408 */     nDN1.addAttr("COFCAT");
/*  409 */     nDN1.addAttr("COFSUBCAT");
/*  410 */     nDN1.addAttr("COFGRP");
/*  411 */     nDN1.addAttr("COFSUBGRP");
/*  412 */     nDN2 = new NDN("SWFEATURE", "(FC)");
/*  413 */     nDN2.addAttr("FEATURECODE");
/*  414 */     nDN1.setNext(nDN2);
/*  415 */     NDN_TBL.put("SWPRODSTRUCT", nDN1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  440 */     SAPL_TRANS_TBL = new Hashtable<>();
/*  441 */     SAPL_TRANS_TBL.put("20", "40");
/*  442 */     SAPL_TRANS_TBL.put("30", "40");
/*  443 */     SAPL_TRANS_TBL.put("40", "80");
/*  444 */     SAPL_TRANS_TBL.put("80", "70");
/*      */     
/*  446 */     STATUS_TBL = new Hashtable<>();
/*  447 */     STATUS_TBL.put("DRAFT", "0010");
/*  448 */     STATUS_TBL.put("FINAL", "0020");
/*  449 */     STATUS_TBL.put("REVIEW", "0040");
/*  450 */     STATUS_TBL.put("0050", "0050");
/*      */     
/*  452 */     DQ_TBL = new Hashtable<>();
/*  453 */     DQ_TBL.put("0010", "DRAFT");
/*  454 */     DQ_TBL.put("0020", "FINAL");
/*  455 */     DQ_TBL.put("0040", "REVIEW");
/*  456 */     DQ_TBL.put("0050", "0050");
/*  457 */     STATUS_ORDER_TBL = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  464 */     STATUS_ORDER_TBL.put("0010", "1");
/*  465 */     STATUS_ORDER_TBL.put("0050", "2");
/*  466 */     STATUS_ORDER_TBL.put("0040", "3");
/*  467 */     STATUS_ORDER_TBL.put("0020", "4");
/*      */     
/*  469 */     STATUS_ATTR_TBL = new Hashtable<>();
/*  470 */     STATUS_ATTR_TBL.put("ANNOUNCEMENT", "ANNSTATUS");
/*  471 */     STATUS_ATTR_TBL.put("FB", "FBSTATUS");
/*  472 */     STATUS_ATTR_TBL.put("MM", "MMSTATUS");
/*      */     
/*  474 */     ABR_ATTR_TBL = new Hashtable<>();
/*  475 */     ABR_ATTR_TBL.put("ANNOUNCEMENT", "ANNABRSTATUS");
/*  476 */     ABR_ATTR_TBL.put("AVAIL", "AVAILABRSTATUS");
/*  477 */     ABR_ATTR_TBL.put("CATNAV", "CATNAVABRSTATUS");
/*  478 */     ABR_ATTR_TBL.put("CHRGCOMP", "CHRGCOMPABRSTATUS");
/*  479 */     ABR_ATTR_TBL.put("CVM", "CVMABRSTATUS");
/*  480 */     ABR_ATTR_TBL.put("CVMSPEC", "CVMSPECABRSTATUS");
/*  481 */     ABR_ATTR_TBL.put("FB", "FBABRSTATUS");
/*  482 */     ABR_ATTR_TBL.put("FEATURE", "FCABRSTATUS");
/*  483 */     ABR_ATTR_TBL.put("FCTRANSACTION", "FCTRANSABRSTATUS");
/*  484 */     ABR_ATTR_TBL.put("GBT", "GBTABRSTATUS");
/*  485 */     ABR_ATTR_TBL.put("IMG", "IMGABRSTATUS");
/*  486 */     ABR_ATTR_TBL.put("LSEO", "LSEOABRSTATUS");
/*  487 */     ABR_ATTR_TBL.put("LSEOBUNDLE", "LSEOBDLABRSTATUS");
/*  488 */     ABR_ATTR_TBL.put("MM", "MMABRSTATUS");
/*  489 */     ABR_ATTR_TBL.put("MODEL", "MODELABRSTATUS");
/*  490 */     ABR_ATTR_TBL.put("MODELCONVERT", "MDLCNTABRSTATTUS");
/*  491 */     ABR_ATTR_TBL.put("PRCPT", "PRCPTABRSTATUS");
/*  492 */     ABR_ATTR_TBL.put("PRODSTRUCT", "PRODSTRUCTABRSTATUS");
/*  493 */     ABR_ATTR_TBL.put("SVCMOD", "SVCMODABRSTATUS");
/*  494 */     ABR_ATTR_TBL.put("SVCSEO", "SVCSEOABRSTATUS");
/*  495 */     ABR_ATTR_TBL.put("SVCLEV", "SVCLEVABRSTATUS");
/*  496 */     ABR_ATTR_TBL.put("SLCNTRYCOND", "SLCNTRYABRSTATUS");
/*  497 */     ABR_ATTR_TBL.put("SWFEATURE", "SWFCABRSTATUS");
/*  498 */     ABR_ATTR_TBL.put("SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
/*  499 */     ABR_ATTR_TBL.put("WARR", "WARRABRSTATUS");
/*  500 */     ABR_ATTR_TBL.put("REVUNBUNDCOMP", "REVABRSTATUS");
/*  501 */     ABR_ATTR_TBL.put("SLEORGNPLNTCODE", "SLEORGABRSTATUS");
/*  502 */     ABR_ATTR_TBL.put("WWSEO", "WWSEOABRSTATUS");
/*      */     
/*  504 */     CHECKFIRSTFINALVEC = new Vector();
/*      */ 
/*      */     
/*  507 */     CHECKFIRSTFINALVEC.add("LSEO");
/*  508 */     CHECKFIRSTFINALVEC.add("LSEOBUNDLE");
/*  509 */     CHECKFIRSTFINALVEC.add("PRODSTRUCT");
/*  510 */     CHECKFIRSTFINALVEC.add("SWPRODSTRUCT");
/*  511 */     CHECKFIRSTFINALVEC.add("SVCMOD");
/*  512 */     CHECKFIRSTFINALVEC.add("MODEL");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  518 */   protected Object[] args = (Object[])new String[10];
/*  519 */   private Hashtable updatedTbl = new Hashtable<>();
/*      */   
/*      */   protected static final int CHECKLEVEL_NOOP = -1;
/*      */   
/*      */   protected static final int CHECKLEVEL_W = 1;
/*      */   
/*      */   protected static final int CHECKLEVEL_RW = 2;
/*      */   
/*      */   protected static final int CHECKLEVEL_RE = 3;
/*      */   
/*      */   protected static final int CHECKLEVEL_E = 4;
/*      */   protected static final int DATE_GR_EQ = 1;
/*      */   protected static final int DATE_LT_EQ = 2;
/*      */   protected static final int DATE_GR = 3;
/*      */   protected static final int DATE_LT = 4;
/*      */   protected static final int DATE_EQ = 5;
/*  535 */   private PrintWriter dbgPw = null;
/*  536 */   private String dbgfn = null;
/*  537 */   private int dbgLen = 0;
/*  538 */   private int abr_debuglvl = 0;
/*      */   
/*      */   private void setupPrintWriter() {
/*  541 */     String str = this.m_abri.getFileName();
/*  542 */     int i = str.lastIndexOf(".");
/*  543 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*      */     try {
/*  545 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  546 */     } catch (Exception exception) {
/*  547 */       this.dbgfn = null;
/*  548 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriter() {
/*  552 */     if (this.dbgPw != null) {
/*  553 */       this.dbgPw.flush();
/*  554 */       this.dbgPw.close();
/*  555 */       this.dbgPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  563 */     return this.dqBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isRPQ(EntityItem paramEntityItem) {
/*  572 */     return !PokUtils.contains(paramEntityItem, "FCTYPE", FCTYPE_SET);
/*      */   }
/*      */   
/*      */   protected boolean isQSMRPQ(EntityItem paramEntityItem) {
/*  576 */     return PokUtils.contains(paramEntityItem, "FCTYPE", RPQ_FCTYPE_SET);
/*      */   }
/*      */   
/*      */   protected boolean isQsmANNTYPE(String paramString) {
/*  580 */     addDebug("isQsmANNTYPE annType " + paramString);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  588 */     return true;
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
/*      */   public void execute_run() {
/*  614 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  616 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Data quality check: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} --><br />" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  628 */     String str3 = null;
/*  629 */     String str4 = null;
/*  630 */     String str5 = null;
/*  631 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  634 */       String str = "";
/*  635 */       long l = System.currentTimeMillis();
/*      */       
/*  637 */       start_ABRBuild(false);
/*      */       
/*  639 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  641 */       setupPrintWriter();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  646 */       EntityItem entityItem = checkTimestamps();
/*      */       
/*  648 */       str5 = getAttributeFlagEnabledValue(entityItem, "DATAQUALITY");
/*  649 */       str4 = getAttributeFlagEnabledValue(entityItem, getStatusAttrCode());
/*  650 */       addDebug(getStatusAttrCode() + ": " + 
/*  651 */           PokUtils.getAttributeValue(entityItem, getStatusAttrCode(), ", ", "", false) + " [" + str4 + "] DATAQUALITY: " + 
/*      */           
/*  653 */           PokUtils.getAttributeValue(entityItem, "DATAQUALITY", ", ", "", false) + " [" + str5 + "] ");
/*      */       
/*  655 */       if (null == str4 || str4.length() == 0) {
/*  656 */         str4 = "0020";
/*      */       }
/*  658 */       if (null == str5 || str5.length() == 0) {
/*  659 */         str5 = "FINAL";
/*      */       }
/*      */ 
/*      */       
/*  663 */       domainNeedsChecks(entityItem);
/*      */ 
/*      */       
/*  666 */       if (isVEneeded(str4)) {
/*      */         
/*  668 */         str = getVEName(str4, str5);
/*      */         
/*  670 */         this.m_elist = this.m_db.getEntityList(entityItem.getProfile(), new ExtractActionItem(null, this.m_db, entityItem
/*  671 */               .getProfile(), str), new EntityItem[] { new EntityItem(null, entityItem
/*  672 */                 .getProfile(), getEntityType(), getEntityID()) });
/*      */       } else {
/*      */         
/*  675 */         str = "dummy";
/*      */       } 
/*      */       
/*  678 */       addDebug("Time to get VE: " + (System.currentTimeMillis() - l) + " (mseconds)");
/*      */       
/*  680 */       setControlBlock();
/*      */ 
/*      */       
/*  683 */       this.dqBundle = ResourceBundle.getBundle(DQABRSTATUS.class.getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  686 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + getEntityType() + ":" + getEntityID() + " extract: " + str + " valon: " + entityItem
/*  687 */           .getProfile().getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  690 */       setReturnCode(0);
/*      */       
/*  692 */       this.failedStr = this.dqBundle.getString("FAILED");
/*  693 */       this.dqCheck = this.failedStr;
/*      */ 
/*      */       
/*  696 */       this.navName = getNavigationName();
/*      */ 
/*      */       
/*  699 */       entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  700 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  705 */       this.strNow = this.m_db.getDates().getNow().substring(0, 10);
/*  706 */       boolean bool = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  713 */       if (str4.equals("0020")) {
/*  714 */         addDebug("Status already final, bypassing checks");
/*      */         
/*  716 */         this.dqCheck = this.dqBundle.getString("NOT_REQ");
/*  717 */         doAlreadyFinalProcessing(entityItem);
/*  718 */         bool = false;
/*  719 */       } else if (str4.equals("0040") && str5.equals("REVIEW")) {
/*  720 */         addDebug("Status already rfr, bypassing checks");
/*      */         
/*  722 */         this.dqCheck = this.dqBundle.getString("NOT_REQ");
/*  723 */         doAlreadyRFRProcessing(entityItem);
/*  724 */         bool = false;
/*      */       } else {
/*  726 */         if (domainInList()) {
/*  727 */           doDQChecking(entityItem, str4);
/*      */         } else {
/*  729 */           addDebug("No checking required for domain and will not advance status");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  735 */           this.args[0] = getLD_Value(entityItem, "PDHDOMAIN");
/*  736 */           addError("DOMAIN_NOT_SUPPORTED", this.args);
/*      */         } 
/*      */         
/*  739 */         String str7 = (String)STATUS_TBL.get(str5);
/*      */         
/*  741 */         if (getReturnCode() == 0)
/*      */         {
/*  743 */           if (doDARULEs() && str7.equals("0040")) {
/*  744 */             updateDerivedData(entityItem);
/*      */           }
/*      */         }
/*  747 */         if (getReturnCode() == 0) {
/*      */ 
/*      */           
/*  750 */           setFlagValue(this.m_elist.getProfile(), getStatusAttrCode(), str7);
/*  751 */           this.dqCheck = this.dqBundle.getString("PASSED");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  756 */           if (str7.equals("0020")) {
/*  757 */             addDebug("completeNowFinalProcessing ");
/*  758 */             completeNowFinalProcessing();
/*      */           
/*      */           }
/*  761 */           else if (str7.equals("0040")) {
/*  762 */             addDebug("completeNowR4RProcessing ");
/*  763 */             completeNowR4RProcessing();
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/*  775 */           String str8 = (String)DQ_TBL.get(str4);
/*      */           
/*  777 */           for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/*  778 */             ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*  779 */             if (returnEntityKey.getEntityID() == getEntityID() && returnEntityKey
/*  780 */               .getEntityType().equals(getEntityType())) {
/*      */               
/*  782 */               removeAttrBeforeCommit(returnEntityKey);
/*      */               break;
/*      */             } 
/*      */           } 
/*  786 */           setFlagValue(this.m_elist.getProfile(), "DATAQUALITY", str8);
/*      */         } 
/*      */       } 
/*      */       
/*  790 */       updatePDH(this.m_elist.getProfile());
/*      */       
/*  792 */       if (bool && getReturnCode() == 0) {
/*  793 */         addDebug("doPostProcessing ");
/*      */         
/*  795 */         doPostProcessing(entityItem, str4, str5);
/*      */       }
/*      */     
/*  798 */     } catch (Throwable throwable) {
/*      */       
/*  800 */       StringWriter stringWriter = new StringWriter();
/*  801 */       String str7 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  802 */       String str8 = "<pre>{0}</pre>";
/*  803 */       MessageFormat messageFormat1 = new MessageFormat(str7);
/*  804 */       setReturnCode(-3);
/*  805 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  807 */       this.args[0] = throwable.getMessage();
/*  808 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  809 */       messageFormat1 = new MessageFormat(str8);
/*  810 */       this.args[0] = stringWriter.getBuffer().toString();
/*  811 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  812 */       logError("Exception: " + throwable.getMessage());
/*  813 */       logError(stringWriter.getBuffer().toString());
/*      */ 
/*      */       
/*  816 */       if (str4 != null) {
/*  817 */         String str = (String)DQ_TBL.get(str4);
/*      */         
/*      */         try {
/*  820 */           for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/*  821 */             ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*  822 */             if (returnEntityKey.getEntityID() == getEntityID() && returnEntityKey
/*  823 */               .getEntityType().equals(getEntityType())) {
/*  824 */               returnEntityKey.m_vctAttributes.clear();
/*      */               break;
/*      */             } 
/*      */           } 
/*  828 */           setFlagValue(this.m_prof, "DATAQUALITY", str);
/*  829 */           setFlagValue(this.m_prof, getStatusAttrCode(), str4);
/*  830 */           this.dqCheck = this.dqBundle.getString("FAILED");
/*  831 */           updatePDH(this.m_elist.getProfile());
/*  832 */         } catch (Exception exception) {}
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  837 */       setDGTitle(this.navName);
/*  838 */       setDGRptName(getShortClassName(getClass()));
/*  839 */       setDGRptClass(getABRCode());
/*      */ 
/*      */ 
/*      */       
/*  843 */       if (getReturnCode() == 0 && this.hasWarning) {
/*  844 */         setDGCat2(warningMsg);
/*  845 */         addDebug("DG infor: " + this.navName + " : " + getShortClassName(getClass()) + " : " + getABRCode() + " : " + warningMsg.toString());
/*      */       } 
/*      */ 
/*      */       
/*  849 */       if (!isReadOnly()) {
/*  850 */         clearSoftLock();
/*      */       }
/*  852 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  857 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  858 */     this.args[0] = getShortClassName(getClass());
/*  859 */     this.args[1] = this.navName;
/*  860 */     String str6 = messageFormat.format(this.args);
/*  861 */     messageFormat = new MessageFormat(str2);
/*  862 */     this.args[0] = this.m_prof.getOPName();
/*  863 */     this.args[1] = this.m_prof.getRoleDescription();
/*  864 */     this.args[2] = this.m_prof.getWGName();
/*  865 */     this.args[3] = getNow();
/*  866 */     this.args[4] = str3 + ": " + this.navName;
/*      */     
/*  868 */     this.args[5] = this.dqCheck;
/*  869 */     this.args[6] = getABRVersion();
/*      */     
/*  871 */     restoreXtraContent();
/*      */     
/*  873 */     this.rptSb.insert(0, str6 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  875 */     println(this.rptSb.toString());
/*  876 */     printDGSubmitString();
/*  877 */     println(EACustom.getTOUDiv());
/*  878 */     buildReportFooter();
/*      */     
/*  880 */     this.metaTbl.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  888 */     if (this.dbgfn != null && this.dbgLen + this.rptSb.length() < 5000000) {
/*      */       
/*  890 */       BufferedInputStream bufferedInputStream = null;
/*  891 */       FileInputStream fileInputStream = null;
/*  892 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  894 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  895 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  897 */         String str = null;
/*  898 */         StringBuffer stringBuffer = new StringBuffer();
/*  899 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  901 */         while ((str = bufferedReader.readLine()) != null) {
/*  902 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  904 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  907 */         File file = new File(this.dbgfn);
/*  908 */         if (file.exists()) {
/*  909 */           file.delete();
/*      */         }
/*  911 */       } catch (Exception exception) {
/*  912 */         exception.printStackTrace();
/*      */       } finally {
/*  914 */         if (bufferedInputStream != null) {
/*      */           try {
/*  916 */             bufferedInputStream.close();
/*  917 */           } catch (Exception exception) {
/*  918 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  921 */         if (fileInputStream != null) {
/*      */           try {
/*  923 */             fileInputStream.close();
/*  924 */           } catch (Exception exception) {
/*  925 */             exception.printStackTrace();
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
/*      */   protected void removeAttrBeforeCommit(ReturnEntityKey paramReturnEntityKey) {
/*  937 */     paramReturnEntityKey.m_vctAttributes.clear();
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
/*      */   protected boolean updateDerivedData(EntityItem paramEntityItem) throws Exception {
/*  962 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doDARULEs() {
/*  971 */     String str = ABRServerProperties.getValue("DARULE", "_updates", "true");
/*      */     
/*  973 */     addDebug("doDARULEs " + str);
/*  974 */     return str.equalsIgnoreCase("true");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean execDerivedData(EntityItem paramEntityItem) {
/*  981 */     StringBuffer stringBuffer = new StringBuffer();
/*  982 */     boolean bool = false;
/*      */     try {
/*  984 */       bool = DARuleEngineMgr.updateCatData(this.m_db, this.m_prof, paramEntityItem, stringBuffer);
/*  985 */       this.rptSb.append(stringBuffer.toString() + NEWLINE);
/*  986 */     } catch (Exception exception) {
/*  987 */       addUserAndErrorMsg(stringBuffer.toString(), exception.getMessage());
/*      */       
/*  989 */       StringWriter stringWriter = new StringWriter();
/*  990 */       exception.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  992 */       addDebug(stringWriter.getBuffer().toString());
/*      */     } 
/*  994 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLCRFRWFName() {
/* 1001 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getLCFinalWFName() {
/* 1006 */     return null;
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
/*      */   protected void doPostProcessing(EntityItem paramEntityItem, String paramString1, String paramString2) throws Exception {
/* 1025 */     if (doR10processing()) {
/* 1026 */       String str1 = getLCRFRWFName();
/* 1027 */       String str2 = getLCFinalWFName();
/* 1028 */       if (str1 == null || str2 == null) {
/*      */         return;
/*      */       }
/* 1031 */       EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("LIFECYCLE");
/* 1032 */       if (eANMetaAttribute == null) {
/* 1033 */         addDebug("doPostProcessing: " + paramEntityItem.getKey() + " LIFECYCLE not in " + paramEntityItem.getEntityGroup() + " meta ");
/*      */         
/*      */         return;
/*      */       } 
/* 1037 */       String str3 = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/* 1038 */       addDebug("doPostProcessing: " + paramEntityItem.getKey() + " statusFlag " + paramString1 + " dataQualityFlag " + paramString2 + " lifecycle " + str3);
/*      */       
/* 1040 */       if ("0010".equals(paramString1)) {
/* 1041 */         triggerWorkFlow(paramEntityItem, str1);
/* 1042 */       } else if ("0040".equals(paramString1)) {
/* 1043 */         if (!"LF03".equals(str3)) {
/* 1044 */           triggerWorkFlow(paramEntityItem, str2);
/*      */         }
/* 1046 */       } else if ("0050".equals(paramString1) && "REVIEW".equals(paramString2)) {
/*      */ 
/*      */ 
/*      */         
/* 1050 */         addDebug("doPostProcessing: " + paramEntityItem.getKey() + " status was chgreq and dq was rfr lifecycle " + str3);
/* 1051 */         if (str3 == null || str3.length() == 0) {
/* 1052 */           str3 = "LF01";
/*      */         }
/* 1054 */         if ("LF01".equals(str3)) {
/* 1055 */           triggerWorkFlow(paramEntityItem, str1);
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
/*      */   protected void completeNowFinalProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doAlreadyFinalProcessing(EntityItem paramEntityItem) throws Exception {
/* 1082 */     addOutput("Status and Data Quality were already Final.  No action taken.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doAlreadyRFRProcessing(EntityItem paramEntityItem) throws Exception {
/* 1089 */     addOutput("Status and Data Quality were already Ready For Review.  No action taken.");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void completeNowFinalProcessingForOtherDomains() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getStatusAttrCode() {
/* 1105 */     return "STATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/* 1111 */     return true;
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
/*      */   protected String getVEName(String paramString1, String paramString2) {
/* 1123 */     return this.m_abri.getVEName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addResourceMsg(String paramString, Object[] paramArrayOfObject) {
/* 1134 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*      */     
/* 1136 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("INFO_PREFIX"));
/* 1137 */     Object[] arrayOfObject = new Object[2];
/* 1138 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1139 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1141 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1147 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addHeading(int paramInt, String paramString) {
/* 1152 */     this.rptSb.append("<h" + paramInt + ">" + paramString + "</h" + paramInt + ">" + NEWLINE);
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
/*      */   protected void addError(String paramString, Object[] paramArrayOfObject) {
/* 1164 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 1165 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 1168 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/* 1169 */     Object[] arrayOfObject = new Object[2];
/* 1170 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1171 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1173 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */ 
/*      */     
/* 1176 */     String str = getBundle().getString(paramString);
/* 1177 */     if (paramArrayOfObject != null) {
/* 1178 */       messageFormat = new MessageFormat(str);
/* 1179 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/* 1181 */     addDebug("ERROR: " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addUserAndErrorMsg(String paramString1, String paramString2) {
/* 1189 */     setReturnCode(-1);
/*      */     
/* 1191 */     this.rptSb.append(paramString1 + NEWLINE);
/*      */     
/* 1193 */     String str = paramString2;
/* 1194 */     if (str != null && str.toLowerCase().startsWith("error")) {
/* 1195 */       str = str.substring("error".length());
/*      */     }
/* 1197 */     if (str != null) {
/* 1198 */       addOutput("<b>Error:</b>" + str);
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
/*      */   protected void addWarning(String paramString, Object[] paramArrayOfObject) {
/* 1212 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*      */ 
/*      */     
/* 1215 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("WARNING_PREFIX"));
/* 1216 */     Object[] arrayOfObject = new Object[2];
/* 1217 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 1218 */     arrayOfObject[1] = this.navName;
/*      */     
/* 1220 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */ 
/*      */     
/* 1223 */     String str = getBundle().getString(paramString);
/* 1224 */     if (paramArrayOfObject != null) {
/* 1225 */       messageFormat = new MessageFormat(str);
/* 1226 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/* 1228 */     addDebug("WARNING: " + str);
/* 1229 */     this.hasWarning = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 1238 */     String str = getBundle().getString(paramString2);
/*      */     
/* 1240 */     if (paramArrayOfObject != null) {
/* 1241 */       MessageFormat messageFormat = new MessageFormat(str);
/* 1242 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 1245 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(int paramInt, String paramString) {
/* 1254 */     if (paramInt <= this.abr_debuglvl) {
/* 1255 */       addDebug(paramString);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1263 */     if (this.dbgPw != null) {
/* 1264 */       this.dbgLen += paramString.length();
/* 1265 */       this.dbgPw.println(paramString);
/* 1266 */       this.dbgPw.flush();
/*      */     } else {
/* 1268 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getRootEntityItem(Profile paramProfile) throws SQLException, MiddlewareException {
/* 1277 */     this.m_elist = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, 
/*      */             
/* 1279 */             getEntityType(), getEntityID()) });
/*      */     
/* 1281 */     return this.m_elist.getParentEntityGroup().getEntityItem(0);
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
/*      */   private EntityItem checkTimestamps() throws Exception {
/* 1293 */     EntityItem entityItem = getRootEntityItem(getProfile());
/*      */     
/* 1295 */     if (!checkTimestamps(entityItem)) {
/*      */       
/*      */       try {
/* 1298 */         addDebug("Pausing for 5000 ms");
/* 1299 */         Thread.sleep(5000L);
/* 1300 */       } catch (InterruptedException interruptedException) {
/* 1301 */         System.out.println(interruptedException);
/*      */       } 
/*      */ 
/*      */       
/* 1305 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
/* 1306 */       String str = simpleDateFormat.format(Calendar.getInstance().getTime()).substring(0, 11) + "23.59.59.999990";
/*      */       
/* 1308 */       Profile profile = this.m_prof.getNewInstance(this.m_db);
/* 1309 */       profile.setValOnEffOn(str, str);
/* 1310 */       addDebug("ABR started before DATAQUALITY was updated, pull extract again using valon: " + profile
/* 1311 */           .getValOn());
/*      */       
/* 1313 */       entityItem = getRootEntityItem(profile);
/*      */     } 
/*      */     
/* 1316 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkTimestamps(EntityItem paramEntityItem) throws Exception {
/* 1325 */     boolean bool = true;
/*      */     
/* 1327 */     Profile profile = paramEntityItem.getProfile();
/*      */ 
/*      */ 
/*      */     
/* 1331 */     String str3 = profile.getValOn();
/*      */     
/* 1333 */     String str1 = getTimestamp(paramEntityItem, getABRCode());
/* 1334 */     String str2 = getTimestamp(paramEntityItem, "DATAQUALITY");
/*      */     
/* 1336 */     addDebug("DTS for " + getABRCode() + ": " + str1 + " DATAQUALITY: " + str2);
/* 1337 */     addDebug("DTS for valon: " + str3 + " effon " + profile.getEffOn());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1342 */     if (str2.length() > 0 && str3
/* 1343 */       .compareTo(str2) < 0)
/*      */     {
/* 1345 */       bool = false;
/*      */     }
/*      */     
/* 1348 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getTimestamp(EntityItem paramEntityItem, String paramString) throws Exception {
/* 1358 */     RowSelectableTable rowSelectableTable = new RowSelectableTable((EANTableWrapper)paramEntityItem, paramEntityItem.getLongDescription());
/*      */     
/* 1360 */     String str1 = "";
/* 1361 */     String str2 = paramEntityItem.getEntityType() + ":" + paramString;
/* 1362 */     int i = rowSelectableTable.getRowIndex(str2);
/* 1363 */     if (i < 0) {
/* 1364 */       i = rowSelectableTable.getRowIndex(str2 + ":C");
/*      */     }
/* 1366 */     if (i < 0) {
/* 1367 */       i = rowSelectableTable.getRowIndex(str2 + ":R");
/*      */     }
/* 1369 */     if (i != -1) {
/* 1370 */       EANAttribute eANAttribute = (EANAttribute)rowSelectableTable.getEANObject(i, 1);
/* 1371 */       if (eANAttribute != null) {
/* 1372 */         byte b = 0;
/* 1373 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_prof, eANAttribute);
/* 1374 */         this.rptSb.append("<!--" + paramEntityItem.getKey() + " " + paramString + " change history "); int j;
/* 1375 */         for (j = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/*      */           
/* 1377 */           ChangeHistoryItem changeHistoryItem = attributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 1378 */           this.rptSb.append(NEWLINE + "AttrChangeHistoryItem[" + j + "] chgDate: " + changeHistoryItem.getChangeDate() + " value: " + changeHistoryItem
/*      */               
/* 1380 */               .get("ATTVAL", true).toString() + " flagcode: " + changeHistoryItem
/* 1381 */               .getFlagCode() + " user: " + changeHistoryItem
/* 1382 */               .getUser());
/* 1383 */           b++;
/* 1384 */           if (b > 3) {
/*      */             break;
/*      */           }
/*      */         } 
/* 1388 */         this.rptSb.append(" -->" + NEWLINE);
/* 1389 */         if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 1390 */           j = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1;
/* 1391 */           str1 = attributeChangeHistoryGroup.getChangeHistoryItem(j).getChangeDate();
/*      */         } 
/*      */       } else {
/*      */         
/* 1395 */         addDebug("EANAttribute was null for " + paramString + " in " + paramEntityItem.getKey());
/*      */       } 
/*      */     } else {
/*      */       
/* 1399 */       addDebug("Row for " + paramString + " was not found for " + paramEntityItem.getKey());
/*      */     } 
/*      */     
/* 1402 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/* 1412 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1422 */     StringBuffer stringBuffer = new StringBuffer();
/* 1423 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem.getEntityType());
/*      */ 
/*      */     
/* 1426 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 1427 */     if (eANList == null) {
/* 1428 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1429 */       eANList = entityGroup.getMetaAttribute();
/* 1430 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 1432 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1433 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1434 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1435 */       if (b + 1 < eANList.size()) {
/* 1436 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/* 1439 */     if (nDN != null) {
/* 1440 */       EntityList entityList = null;
/* 1441 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 1442 */       EntityItem entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       
/* 1444 */       if (entityItem == null && 
/* 1445 */         paramEntityItem.getEntityType().endsWith("PRODSTRUCT")) {
/* 1446 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType() + " pulling small VE for this " + paramEntityItem
/* 1447 */             .getKey());
/*      */         
/* 1449 */         String str = "EXRPT3FM";
/* 1450 */         if (paramEntityItem.getEntityType().equals("SWPRODSTRUCT")) {
/* 1451 */           str = "EXRPT3SWFM";
/*      */         }
/*      */ 
/*      */         
/* 1455 */         entityList = this.m_db.getEntityList(paramEntityItem.getProfile(), new ExtractActionItem(null, this.m_db, paramEntityItem
/* 1456 */               .getProfile(), str), new EntityItem[] { new EntityItem(null, paramEntityItem
/* 1457 */                 .getProfile(), paramEntityItem.getEntityType(), paramEntityItem
/* 1458 */                 .getEntityID()) });
/*      */         
/* 1460 */         paramEntityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 1461 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       } 
/*      */ 
/*      */       
/* 1465 */       if (entityItem != null) {
/* 1466 */         stringBuffer1.append("(" + nDN.getTag());
/* 1467 */         for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1468 */           String str = nDN.getAttr().elementAt(b1).toString();
/* 1469 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1470 */           if (b1 + 1 < nDN.getAttr().size()) {
/* 1471 */             stringBuffer1.append(" ");
/*      */           }
/*      */         } 
/* 1474 */         stringBuffer1.append(") ");
/*      */       } else {
/* 1476 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType());
/*      */       } 
/* 1478 */       nDN = nDN.getNext();
/* 1479 */       if (nDN != null) {
/* 1480 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/* 1481 */         if (entityItem != null) {
/* 1482 */           stringBuffer1.append("(" + nDN.getTag());
/* 1483 */           for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1484 */             String str = nDN.getAttr().elementAt(b1).toString();
/* 1485 */             stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1486 */             if (b1 + 1 < nDN.getAttr().size()) {
/* 1487 */               stringBuffer1.append(" ");
/*      */             }
/*      */           } 
/* 1490 */           stringBuffer1.append(") ");
/*      */         } else {
/* 1492 */           addDebug("NO entity found for next ndn.getEntityType(): " + nDN.getEntityType());
/*      */         } 
/*      */       } 
/* 1495 */       stringBuffer.insert(0, stringBuffer1.toString());
/*      */       
/* 1497 */       if (entityList != null) {
/* 1498 */         entityList.dereference();
/*      */       }
/*      */     } 
/*      */     
/* 1502 */     return stringBuffer.toString().trim();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getTMFNavigationName(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) throws SQLException, MiddlewareException {
/* 1512 */     StringBuffer stringBuffer = new StringBuffer();
/* 1513 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem1.getEntityType());
/*      */ 
/*      */     
/* 1516 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem1.getEntityType());
/* 1517 */     if (eANList == null) {
/*      */       
/* 1519 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem1.getEntityType(), "Navigate");
/* 1520 */       eANList = entityGroup.getMetaAttribute();
/* 1521 */       this.metaTbl.put(paramEntityItem1.getEntityType(), eANList);
/*      */     } 
/* 1523 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1525 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1526 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem1, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1527 */       if (b + 1 < eANList.size()) {
/* 1528 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/* 1531 */     if (nDN != null) {
/* 1532 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 1533 */       EntityItem entityItem = paramEntityItem2;
/* 1534 */       stringBuffer1.append("(" + nDN.getTag()); byte b1;
/* 1535 */       for (b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1536 */         String str = nDN.getAttr().elementAt(b1).toString();
/* 1537 */         stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1538 */         if (b1 + 1 < nDN.getAttr().size()) {
/* 1539 */           stringBuffer1.append(" ");
/*      */         }
/*      */       } 
/* 1542 */       stringBuffer1.append(") ");
/*      */       
/* 1544 */       nDN = nDN.getNext();
/* 1545 */       if (nDN != null) {
/* 1546 */         entityItem = paramEntityItem3;
/* 1547 */         stringBuffer1.append("(" + nDN.getTag());
/* 1548 */         for (b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1549 */           String str = nDN.getAttr().elementAt(b1).toString();
/* 1550 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1551 */           if (b1 + 1 < nDN.getAttr().size()) {
/* 1552 */             stringBuffer1.append(" ");
/*      */           }
/*      */         } 
/* 1555 */         stringBuffer1.append(") ");
/*      */       } 
/* 1557 */       stringBuffer.insert(0, stringBuffer1.toString());
/*      */     } 
/*      */     
/* 1560 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getNDNitem(EntityItem paramEntityItem, String paramString) {
/*      */     byte b;
/* 1570 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 1571 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 1572 */       if (entityItem.getEntityType().equals(paramString)) {
/* 1573 */         return entityItem;
/*      */       }
/*      */     } 
/* 1576 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1577 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 1578 */       if (entityItem.getEntityType().equals(paramString)) {
/* 1579 */         return entityItem;
/*      */       }
/*      */     } 
/* 1582 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1592 */     return "1.63";
/*      */   }
/*      */   public void dereference() {
/* 1595 */     super.dereference();
/* 1596 */     this.strNow = null;
/* 1597 */     this.rptSb = null;
/* 1598 */     this.metaTbl = null;
/* 1599 */     this.dqBundle = null;
/* 1600 */     this.failedStr = null;
/* 1601 */     this.dqCheck = null;
/* 1602 */     this.errMsgVct.clear();
/* 1603 */     this.errMsgVct = null;
/* 1604 */     this.vctReturnsEntityKeys = null;
/* 1605 */     this.navName = null;
/* 1606 */     if (this.otherList != null) {
/* 1607 */       this.otherList.dereference();
/* 1608 */       this.otherList = null;
/*      */     } 
/* 1610 */     this.updatedTbl.clear();
/* 1611 */     this.updatedTbl = null;
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
/*      */   protected void setTextValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 1625 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 1626 */     addDebug("setTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/* 1629 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1630 */     if (eANMetaAttribute == null) {
/* 1631 */       addDebug("setTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 1632 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 1633 */           .getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1637 */     this.updatedTbl.put(paramEntityItem.getKey(), paramEntityItem);
/*      */     
/* 1639 */     if (paramString2 != null) {
/* 1640 */       if (this.m_cbOn == null) {
/* 1641 */         setControlBlock();
/*      */       }
/* 1643 */       ControlBlock controlBlock = this.m_cbOn;
/* 1644 */       if (paramString2.length() == 0) {
/* 1645 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 1646 */         String str = eANAttribute.getEffFrom();
/* 1647 */         controlBlock = new ControlBlock(str, str, str, str, paramProfile.getOPWGID());
/* 1648 */         paramString2 = eANAttribute.toString();
/* 1649 */         addDebug("setTextValue deactivating " + paramString1);
/*      */       } 
/* 1651 */       Vector<Text> vector = null;
/*      */       
/* 1653 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1654 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1655 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1656 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1657 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1661 */       if (vector == null) {
/*      */         
/* 1663 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1664 */         vector = new Vector();
/* 1665 */         returnEntityKey.m_vctAttributes = vector;
/* 1666 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/* 1669 */       Text text = new Text(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, controlBlock);
/* 1670 */       vector.addElement(text);
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
/*      */   protected void setFlagValue(Profile paramProfile, String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 1684 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 1685 */     addDebug("setFlagValue entered " + paramEntityItem.getKey() + " for " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/* 1688 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/* 1689 */       addDebug("setFlagValue: " + paramString1 + " was blank for " + paramEntityItem.getKey() + ", it will be ignored");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1694 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1695 */     if (eANMetaAttribute == null) {
/* 1696 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 1697 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 1698 */           .getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1702 */     this.updatedTbl.put(paramEntityItem.getKey(), paramEntityItem);
/*      */     
/* 1704 */     if (paramString2 != null) {
/*      */ 
/*      */ 
/*      */       
/* 1708 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 1709 */       if (paramString2.equals(str) && 
/* 1710 */         !paramString1.equals(getStatusAttrCode())) {
/* 1711 */         addDebug("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/* 1712 */         logMessage("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1717 */       if (eANMetaAttribute.getAttributeType().equals("A"))
/*      */       {
/* 1719 */         checkForInProcess(paramProfile, paramEntityItem, paramString1);
/*      */       }
/*      */       
/* 1722 */       if (this.m_cbOn == null) {
/* 1723 */         setControlBlock();
/*      */       }
/* 1725 */       Vector<Attribute> vector = null;
/*      */       
/* 1727 */       for (byte b1 = 0; b1 < this.vctReturnsEntityKeys.size(); b1++) {
/* 1728 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b1);
/* 1729 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1730 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1731 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1735 */       if (vector == null) {
/* 1736 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1737 */         vector = new Vector();
/* 1738 */         returnEntityKey.m_vctAttributes = vector;
/* 1739 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/* 1742 */       SingleFlag singleFlag = new SingleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */ 
/*      */       
/* 1746 */       for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 1747 */         Attribute attribute = vector.elementAt(b2);
/* 1748 */         if (attribute.getAttributeCode().equals(paramString1)) {
/* 1749 */           singleFlag = null;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1753 */       if (singleFlag != null) {
/* 1754 */         vector.addElement(singleFlag);
/*      */       } else {
/* 1756 */         addDebug("setFlagValue:  " + paramEntityItem.getKey() + " " + paramString1 + " was already added for updates ");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkForInProcess(Profile paramProfile, EntityItem paramEntityItem, String paramString) {
/*      */     try {
/* 1764 */       byte b = 0;
/*      */       
/* 1766 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*      */       
/* 1768 */       addDebug("checkForInProcess:  entered " + paramEntityItem.getKey() + " " + paramString + " is " + str);
/*      */       
/* 1770 */       if ("0050".equals(str)) {
/* 1771 */         DatePackage datePackage = this.m_db.getDates();
/*      */         
/* 1773 */         paramProfile.setValOnEffOn(datePackage.getEndOfDay(), datePackage.getEndOfDay());
/*      */         
/* 1775 */         while ("0050".equals(str) && b < 20) {
/* 1776 */           b++;
/* 1777 */           addDebug("checkForInProcess: " + paramString + " is " + str + " sleeping 30 secs");
/* 1778 */           Thread.sleep(30000L);
/*      */           
/* 1780 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, paramProfile, paramEntityItem.getEntityType(), "Edit", false);
/* 1781 */           EntityItem entityItem = new EntityItem(entityGroup, paramProfile, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/* 1782 */           str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/* 1783 */           addDebug("checkForInProcess: " + paramString + " is now " + str + " after sleeping");
/*      */         } 
/*      */       } 
/* 1786 */     } catch (Exception exception) {
/* 1787 */       System.err.println("Exception in checkForInProcess " + exception);
/* 1788 */       exception.printStackTrace();
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
/*      */   protected void triggerWorkFlow(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException, WorkflowException, MiddlewareShutdownInProgressException {
/* 1824 */     WorkflowActionItem workflowActionItem = null;
/* 1825 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 1826 */     arrayOfEntityItem[0] = paramEntityItem;
/*      */     
/* 1828 */     Profile profile = this.m_prof.getNewInstance(this.m_db);
/* 1829 */     String str = this.m_db.getDates().getNow();
/* 1830 */     profile.setValOnEffOn(str, str);
/*      */     
/* 1832 */     addDebug("Triggering workflow " + paramString + " for " + paramEntityItem.getKey());
/*      */     
/* 1834 */     workflowActionItem = new WorkflowActionItem(null, this.m_db, profile, paramString);
/* 1835 */     workflowActionItem.setEntityItems(arrayOfEntityItem);
/* 1836 */     this.m_db.executeAction(profile, workflowActionItem);
/* 1837 */     arrayOfEntityItem[0] = null;
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
/*      */   protected void setFlagValue(Profile paramProfile, String paramString1, String paramString2) {
/* 1849 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1850 */     setFlagValue(paramProfile, paramString1, paramString2, entityItem);
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
/*      */   protected void setMultiFlagValue(Profile paramProfile, String paramString1, String paramString2, String paramString3, EntityItem paramEntityItem) {
/* 1865 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2 + " oldval " + paramString3);
/*      */     
/* 1867 */     addDebug("setMultiFlagValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2 + " oldval " + paramString3);
/*      */ 
/*      */ 
/*      */     
/* 1871 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1872 */     if (eANMetaAttribute == null) {
/* 1873 */       addDebug("setMultiFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 1874 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 1875 */           .getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1879 */     if (paramString2 == null) {
/* 1880 */       paramString2 = "";
/*      */     }
/* 1882 */     if (paramString3 == null) {
/* 1883 */       paramString3 = "";
/*      */     }
/* 1885 */     this.updatedTbl.put(paramEntityItem.getKey(), paramEntityItem);
/*      */     
/* 1887 */     if (!paramString2.equals(paramString3)) {
/* 1888 */       if (this.m_cbOn == null) {
/* 1889 */         setControlBlock();
/*      */       }
/*      */       
/* 1892 */       Vector<MultipleFlag> vector = null;
/*      */       
/* 1894 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1895 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1896 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1897 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1898 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1902 */       if (vector == null) {
/*      */         
/* 1904 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1905 */         vector = new Vector();
/* 1906 */         returnEntityKey.m_vctAttributes = vector;
/* 1907 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/* 1910 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString2, "|");
/* 1911 */       MultipleFlag multipleFlag = null;
/* 1912 */       while (stringTokenizer.hasMoreTokens()) {
/* 1913 */         String str = stringTokenizer.nextToken();
/*      */         
/* 1915 */         multipleFlag = new MultipleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, str, 1, this.m_cbOn);
/* 1916 */         vector.addElement(multipleFlag);
/*      */       } 
/*      */ 
/*      */       
/* 1920 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 1921 */       if (eANAttribute != null) {
/* 1922 */         String str = eANAttribute.getEffFrom();
/* 1923 */         ControlBlock controlBlock = new ControlBlock(str, str, str, str, paramProfile.getOPWGID());
/*      */         
/* 1925 */         stringTokenizer = new StringTokenizer(paramString3, "|");
/* 1926 */         while (stringTokenizer.hasMoreTokens()) {
/* 1927 */           String str1 = stringTokenizer.nextToken();
/* 1928 */           if (paramString2.indexOf(str1) < 0) {
/*      */             
/* 1930 */             multipleFlag = new MultipleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, str1, 1, controlBlock);
/* 1931 */             vector.addElement(multipleFlag);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1936 */       addDebug(paramEntityItem.getKey() + " old and new values were the same for " + paramString1);
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
/*      */   private void updatePDH(Profile paramProfile) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1952 */     logMessage(getDescription() + " updating PDH");
/* 1953 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys);
/*      */     
/* 1955 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */       try {
/* 1957 */         this.m_db.update(paramProfile, this.vctReturnsEntityKeys, false, false);
/*      */         
/* 1959 */         for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1960 */           ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*      */           
/* 1962 */           for (byte b1 = 0; b1 < returnEntityKey.m_vctAttributes.size(); b1++) {
/* 1963 */             Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(b1);
/* 1964 */             addDebug("updatePDH : attribute: " + attribute.getAttributeCode() + " -  " + attribute.getAttributeValue());
/* 1965 */             if (attribute instanceof Text) {
/* 1966 */               EntityItem entityItem = (EntityItem)this.updatedTbl.get(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/*      */ 
/*      */               
/* 1969 */               entityItem.commit(this.m_db, null);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         try {
/* 1974 */           Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */           
/* 1976 */           for (byte b1 = 0; b1 < this.vctReturnsEntityKeys.size(); b1++) {
/* 1977 */             ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b1);
/* 1978 */             String str = returnEntityKey.getEntityType();
/* 1979 */             if (NDN_TBL.get(str) != null) {
/* 1980 */               Vector<ReturnEntityKey> vector = (Vector)hashtable.get(str);
/* 1981 */               if (vector == null) {
/* 1982 */                 vector = new Vector();
/* 1983 */                 hashtable.put(str, vector);
/*      */               } 
/* 1985 */               vector.add(returnEntityKey);
/*      */             } else {
/*      */               
/* 1988 */               EntityItem entityItem = (EntityItem)this.updatedTbl.get(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/* 1989 */               outputUpdatedMsg(returnEntityKey, entityItem);
/*      */             } 
/*      */           } 
/*      */           
/* 1993 */           for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/* 1994 */             String str = enumeration.nextElement();
/* 1995 */             Vector<ReturnEntityKey> vector = (Vector)hashtable.get(str);
/* 1996 */             if (vector.size() == 1) {
/* 1997 */               ReturnEntityKey returnEntityKey = vector.elementAt(0);
/*      */               
/* 1999 */               EntityItem entityItem = (EntityItem)this.updatedTbl.get(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/* 2000 */               outputUpdatedMsg(returnEntityKey, entityItem);
/*      */             } else {
/*      */               
/* 2003 */               EntityList entityList = null;
/* 2004 */               EntityItem[] arrayOfEntityItem = new EntityItem[vector.size()];
/* 2005 */               for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 2006 */                 ReturnEntityKey returnEntityKey = vector.elementAt(b2);
/* 2007 */                 arrayOfEntityItem[b2] = new EntityItem(null, getProfile(), returnEntityKey.getEntityType(), returnEntityKey
/* 2008 */                     .getEntityID());
/*      */               } 
/*      */ 
/*      */               
/* 2012 */               addDebug(" pulling small VE for this " + str);
/*      */               
/* 2014 */               String str1 = "EXRPT3FM";
/* 2015 */               if (str.equals("SWPRODSTRUCT")) {
/* 2016 */                 str1 = "EXRPT3SWFM";
/*      */               }
/* 2018 */               entityList = this.m_db.getEntityList(getProfile(), new ExtractActionItem(null, this.m_db, 
/* 2019 */                     getProfile(), str1), arrayOfEntityItem);
/*      */ 
/*      */               
/* 2022 */               EntityGroup entityGroup = entityList.getParentEntityGroup();
/* 2023 */               for (byte b3 = 0; b3 < vector.size(); b3++) {
/* 2024 */                 ReturnEntityKey returnEntityKey = vector.elementAt(b3);
/* 2025 */                 EntityItem entityItem = entityGroup.getEntityItem(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/* 2026 */                 outputUpdatedMsg(returnEntityKey, entityItem);
/*      */               } 
/*      */               
/* 2029 */               entityList.dereference();
/*      */             } 
/* 2031 */             vector.clear();
/*      */           } 
/*      */           
/* 2034 */           hashtable.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 2042 */         catch (Exception exception) {
/* 2043 */           exception.printStackTrace();
/* 2044 */           addDebug("exception trying to output msg " + exception);
/*      */         } 
/*      */       } finally {
/*      */         
/* 2048 */         this.vctReturnsEntityKeys.clear();
/* 2049 */         this.m_db.commit();
/* 2050 */         this.m_db.freeStatement();
/* 2051 */         this.m_db.isPending("finally after updatePDH");
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void outputUpdatedMsg(ReturnEntityKey paramReturnEntityKey, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 2057 */     MessageFormat messageFormat = null;
/*      */     
/* 2059 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/* 2061 */     for (byte b = 0; b < paramReturnEntityKey.m_vctAttributes.size(); b++) {
/* 2062 */       Attribute attribute = paramReturnEntityKey.m_vctAttributes.elementAt(b);
/* 2063 */       String str = attribute.getAttributeCode();
/* 2064 */       if (str.equals("DATAQUALITY")) {
/*      */         
/* 2066 */         messageFormat = new MessageFormat(this.dqBundle.getString("DQ_RESET"));
/* 2067 */         this.args[0] = PokUtils.getAttributeValue(paramEntityItem, getStatusAttrCode(), ", ", "", false);
/* 2068 */       } else if (str.equals(getStatusAttrCode())) {
/*      */         
/* 2070 */         if (getReturnCode() == 0) {
/* 2071 */           messageFormat = new MessageFormat(this.dqBundle.getString("STATUS_SET"));
/* 2072 */           this.args[0] = PokUtils.getAttributeValue(paramEntityItem, "DATAQUALITY", ", ", "", false);
/*      */         } else {
/* 2074 */           messageFormat = null;
/*      */         } 
/*      */       } else {
/*      */         
/* 2078 */         messageFormat = new MessageFormat(this.dqBundle.getString("ATTR_SET"));
/* 2079 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), str, str);
/* 2080 */         if (attribute instanceof Text) {
/* 2081 */           ControlBlock controlBlock = attribute.getControlBlock();
/* 2082 */           if (controlBlock.getValFrom().equals(controlBlock.getValTo()) && controlBlock
/* 2083 */             .getValTo().equals(controlBlock.getEffFrom()) && controlBlock
/* 2084 */             .getEffFrom().equals(controlBlock.getEffTo())) {
/*      */             
/* 2086 */             this.args[1] = "Null";
/*      */           } else {
/* 2088 */             this.args[1] = attribute.getAttributeValue();
/*      */           } 
/*      */         } else {
/* 2091 */           this.args[1] = attribute.getAttributeValue();
/*      */           
/* 2093 */           EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)paramEntityItem.getEntityGroup().getMetaAttribute(str);
/* 2094 */           if (eANMetaFlagAttribute != null) {
/* 2095 */             MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(attribute.getAttributeValue());
/* 2096 */             if (metaFlag != null) {
/* 2097 */               this.args[1] = metaFlag.toString();
/*      */             }
/*      */           } else {
/* 2100 */             addDebug("Error: " + str + " not found in META for " + paramEntityItem.getEntityType());
/*      */           } 
/*      */         } 
/*      */         
/* 2104 */         if (attribute instanceof MultipleFlag) {
/* 2105 */           StringBuffer stringBuffer = (StringBuffer)hashtable.get(str);
/* 2106 */           if (stringBuffer == null) {
/* 2107 */             stringBuffer = new StringBuffer();
/* 2108 */             hashtable.put(str, stringBuffer);
/*      */           } 
/* 2110 */           if (stringBuffer.length() > 0) {
/* 2111 */             stringBuffer.append(",");
/*      */           }
/*      */           
/* 2114 */           ControlBlock controlBlock = attribute.getControlBlock();
/* 2115 */           if (!controlBlock.getValFrom().equals(controlBlock.getValTo()) || 
/* 2116 */             !controlBlock.getValTo().equals(controlBlock.getEffFrom()) || 
/* 2117 */             !controlBlock.getEffFrom().equals(controlBlock.getEffTo()))
/*      */           {
/*      */             
/* 2120 */             stringBuffer.append(this.args[1].toString());
/*      */           }
/*      */         } else {
/* 2123 */           this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/* 2124 */           this.args[3] = getNavigationName(paramEntityItem);
/*      */         } 
/*      */       } 
/* 2127 */       if (!(attribute instanceof MultipleFlag) && messageFormat != null) {
/* 2128 */         addOutput(messageFormat.format(this.args));
/*      */       }
/*      */     } 
/* 2131 */     if (hashtable.size() > 0) {
/* 2132 */       for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/*      */         
/* 2134 */         String str = enumeration.nextElement();
/* 2135 */         StringBuffer stringBuffer = (StringBuffer)hashtable.get(str);
/*      */         
/* 2137 */         messageFormat = new MessageFormat(this.dqBundle.getString("ATTR_SET"));
/* 2138 */         this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), str, str);
/* 2139 */         if (stringBuffer.length() == 0) {
/*      */           
/* 2141 */           this.args[1] = "Null";
/*      */         } else {
/* 2143 */           this.args[1] = stringBuffer.toString();
/*      */         } 
/* 2145 */         this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/* 2146 */         this.args[3] = getNavigationName(paramEntityItem);
/* 2147 */         addOutput(messageFormat.format(this.args));
/*      */       } 
/* 2149 */       hashtable.clear();
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
/*      */   private void domainNeedsChecks(EntityItem paramEntityItem) {
/* 2164 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 2165 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 2166 */     if (str.equals("all") || str.trim().length() == 0) {
/* 2167 */       this.bdomainInList = true;
/*      */     } else {
/* 2169 */       HashSet<String> hashSet = new HashSet();
/* 2170 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 2171 */       while (stringTokenizer.hasMoreTokens()) {
/* 2172 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 2174 */       this.bdomainInList = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 2175 */       hashSet.clear();
/*      */     } 
/*      */     
/* 2178 */     if (!this.bdomainInList) {
/* 2179 */       addDebug("PDHDOMAIN did not include " + str + ", checking is bypassed " + 
/* 2180 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "[" + 
/* 2181 */           getAttributeFlagEnabledValue(paramEntityItem, "PDHDOMAIN") + "]");
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
/*      */   private boolean wasUpdatedSinceDTS(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 2202 */     boolean bool = false;
/* 2203 */     EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.m_db, paramEntityItem.getProfile(), paramEntityItem);
/* 2204 */     if (entityChangeHistoryGroup == null || entityChangeHistoryGroup.getChangeHistoryItemCount() == 0) {
/* 2205 */       addDebug(paramEntityItem.getKey() + " No Change history found");
/*      */     } else {
/*      */       
/* 2208 */       String str = entityChangeHistoryGroup.getChangeHistoryItem(entityChangeHistoryGroup.getChangeHistoryItemCount() - 1).getChangeDate();
/* 2209 */       addDebug(paramEntityItem.getKey() + " last chgDate: " + str + " vs. " + paramString);
/* 2210 */       if (str.compareTo(paramString) >= 0) {
/* 2211 */         bool = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2221 */     return bool;
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
/*      */   protected int getCount(String paramString) throws MiddlewareException {
/* 2239 */     int i = 0;
/*      */     
/* 2241 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString);
/* 2242 */     if (entityGroup == null) {
/* 2243 */       throw new MiddlewareException(paramString + " is missing from extract for " + this.m_abri.getVEName());
/*      */     }
/* 2245 */     if (entityGroup.getEntityItemCount() > 0) {
/* 2246 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2247 */         int j = 1;
/* 2248 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2249 */         EANAttribute eANAttribute = entityItem.getAttribute("QTY");
/* 2250 */         if (eANAttribute != null) {
/* 2251 */           j = Integer.parseInt(eANAttribute.get().toString());
/*      */         }
/* 2253 */         i += j;
/* 2254 */         addDebug(2, "getCount " + entityItem.getKey() + " qty " + j);
/*      */       } 
/*      */     }
/*      */     
/* 2258 */     addDebug("getCount Total count found for " + paramString + " = " + i);
/* 2259 */     return i;
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
/*      */   protected void checkAssortModule() throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 2276 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 2277 */     String str1 = "SAPASSORTMODULE";
/* 2278 */     String str2 = "SAPASSORTMODULEPRIOR";
/*      */     
/* 2280 */     EANMetaAttribute eANMetaAttribute = this.m_elist.getParentEntityGroup().getMetaAttribute(str2);
/* 2281 */     if (eANMetaAttribute == null) {
/* 2282 */       throw new MiddlewareException(str2 + " not found in META for " + entityItem.getEntityType());
/*      */     }
/* 2284 */     eANMetaAttribute = this.m_elist.getParentEntityGroup().getMetaAttribute(str1);
/* 2285 */     if (eANMetaAttribute == null) {
/* 2286 */       throw new MiddlewareException(str1 + " not found in META for " + entityItem.getEntityType());
/*      */     }
/*      */ 
/*      */     
/* 2290 */     EANAttribute eANAttribute = entityItem.getAttribute("STATUS");
/* 2291 */     if (eANAttribute != null) {
/*      */       
/* 2293 */       String str = null;
/* 2294 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute);
/* 2295 */       addDebug("checkAssortModule: ChangeHistoryGroup for " + entityItem.getKey() + " Attribute: STATUS");
/* 2296 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 2297 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*      */           
/* 2299 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2300 */           addDebug("checkAssortModule: AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 2301 */               .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 2302 */           if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 2303 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 2308 */       if (str != null) {
/* 2309 */         String str3 = PokUtils.getAttributeValue(entityItem, str1, ", ", "", false).trim();
/* 2310 */         String str4 = PokUtils.getAttributeValue(entityItem, str2, ", ", "", false).trim();
/*      */         
/* 2312 */         Profile profile = this.m_elist.getProfile().getNewInstance(this.m_db);
/* 2313 */         profile.setValOnEffOn(str, str);
/* 2314 */         EntityGroup entityGroup = new EntityGroup(null, this.m_db, profile, entityItem.getEntityType(), "Edit", false);
/* 2315 */         EntityItem entityItem1 = new EntityItem(entityGroup, profile, this.m_db, entityItem.getEntityType(), entityItem.getEntityID());
/* 2316 */         String str5 = PokUtils.getAttributeValue(entityItem1, str1, ", ", "", false).trim();
/* 2317 */         addDebug("checkAssortModule: " + entityItem.getKey() + " lastfinaldate " + str + " " + str1 + " curr:" + str3 + " prev:" + str5 + " " + str2 + " curr: " + str4);
/*      */ 
/*      */ 
/*      */         
/* 2321 */         if (!str4.equals(str5)) {
/* 2322 */           setTextValue(this.m_elist.getProfile(), str2, str5, entityItem);
/*      */         } else {
/* 2324 */           addDebug("checkAssortModule: " + str2 + " current:" + str4 + " already matches " + str1 + " previous:" + str5);
/*      */         } 
/*      */       } else {
/*      */         
/* 2328 */         addDebug("checkAssortModule: must be first time " + entityItem.getKey() + " status went final");
/*      */       } 
/*      */     } else {
/*      */       
/* 2332 */       addDebug("checkAssortModule: Could not get AttributeHistory for " + entityItem.getKey() + " STATUS, it was null");
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected void propagateOStoWWSEO(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 2395 */     String str1 = "OS";
/*      */     
/* 2397 */     if (paramEntityGroup == null) {
/* 2398 */       throw new MiddlewareException("WWSEO is missing from extract for " + this.m_abri.getVEName());
/*      */     }
/*      */     
/* 2401 */     if (paramEntityItem == null) {
/* 2402 */       addDebug("propagateOStoWWSEO: MODEL was null!!");
/*      */       
/*      */       return;
/*      */     } 
/* 2406 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, "COFCAT");
/* 2407 */     String str3 = getAttributeFlagEnabledValue(paramEntityItem, "COFGRP");
/* 2408 */     addDebug("propagateOStoWWSEO: " + paramEntityItem.getKey() + " COFGRP: " + str3 + " wwseocnt: " + paramEntityGroup.getEntityItemCount());
/* 2409 */     if (paramEntityGroup.getEntityItemCount() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 2414 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str1);
/* 2415 */     if (eANMetaAttribute == null) {
/* 2416 */       addDebug("propagateOStoWWSEO ERROR:Attribute " + str1 + " NOT found in MODEL META data.");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 2421 */     if ("100".equals(str2) && "150".equals(str3)) {
/*      */       
/* 2423 */       EANAttribute eANAttribute = paramEntityItem.getAttribute("MODELABRSTATUS");
/* 2424 */       if (eANAttribute != null) {
/* 2425 */         String str = null;
/* 2426 */         boolean bool = false;
/* 2427 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute);
/* 2428 */         addDebug("propagateOStoWWSEO: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: MODELABRSTATUS");
/* 2429 */         if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 2430 */           for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2431 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2432 */             addDebug("propagateOStoWWSEO: AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 2433 */                 .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 2434 */             if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 2435 */               if (str != null)
/*      */               {
/* 2437 */                 bool = true;
/*      */               }
/* 2439 */               str = attributeChangeHistoryItem.getChangeDate();
/* 2440 */               if (bool) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 2447 */         if (str != null) {
/* 2448 */           String str4 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/* 2449 */           if (str4 == null) {
/* 2450 */             str4 = "";
/*      */           }
/*      */ 
/*      */           
/* 2454 */           Profile profile = this.m_elist.getProfile().getNewInstance(this.m_db);
/* 2455 */           profile.setValOnEffOn(str, str);
/* 2456 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, profile, paramEntityItem.getEntityType(), "Edit", false);
/* 2457 */           EntityItem entityItem = new EntityItem(entityGroup, profile, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/* 2458 */           String str5 = getAttributeFlagEnabledValue(entityItem, str1);
/* 2459 */           if (str5 == null) {
/* 2460 */             str5 = "";
/*      */           }
/*      */           
/* 2463 */           addDebug("propagateOStoWWSEO: " + paramEntityItem.getKey() + " lastqueueddate " + str + " " + str1 + " curr: " + str4 + " prev:" + str5);
/*      */ 
/*      */           
/* 2466 */           if (!str4.equals(str5)) {
/*      */             
/* 2468 */             for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 2469 */               EntityItem entityItem1 = paramEntityGroup.getEntityItem(b);
/* 2470 */               addDebug("propagateOStoWWSEO: updating " + entityItem1.getKey() + " SEOID " + 
/* 2471 */                   PokUtils.getAttributeValue(entityItem1, "SEOID", ", ", "", false));
/* 2472 */               setFlagValue(this.m_elist.getProfile(), str1, str4, entityItem1);
/*      */             } 
/*      */           } else {
/* 2475 */             addDebug("propagateOStoWWSEO: no changes in " + paramEntityItem.getKey() + " os since last queued");
/*      */           } 
/*      */         } else {
/* 2478 */           addDebug("propagateOStoWWSEO: Could not get queued DTS for " + paramEntityItem.getKey());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2483 */         addDebug("propagateOStoWWSEO: Could not get AttributeHistory for " + paramEntityItem.getKey() + " MODELABRSTATUS, it was null");
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
/*      */   protected String getPrevPassedDTS(EntityItem paramEntityItem, String paramString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 2500 */     String str = null;
/*      */     
/* 2502 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/* 2503 */     if (eANAttribute != null) {
/* 2504 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute);
/* 2505 */       addDebug("getPrevPassedDTS: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: " + paramString);
/* 2506 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 2507 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2508 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2509 */           addDebug("getPrevPassedDTS: AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 2510 */               .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 2511 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030")) {
/* 2512 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 2518 */     return str;
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
/*      */   protected boolean changeOfInterest(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, Hashtable paramHashtable) throws SQLException, MiddlewareException {
/* 2538 */     boolean bool = false;
/*      */     
/* 2540 */     if (paramString2.equals(paramString1)) {
/* 2541 */       addDebug("changeOfInterest current and prior dts are the same, no changes can exist.");
/* 2542 */       return bool;
/*      */     } 
/*      */ 
/*      */     
/* 2546 */     Profile profile1 = this.m_elist.getProfile().getNewInstance(this.m_db);
/* 2547 */     profile1.setValOnEffOn(paramString2, paramString2);
/*      */ 
/*      */ 
/*      */     
/* 2551 */     EntityList entityList1 = this.m_db.getEntityList(profile1, new ExtractActionItem(null, this.m_db, profile1, paramString3), new EntityItem[] { new EntityItem(null, profile1, paramEntityItem
/*      */             
/* 2553 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */ 
/*      */     
/* 2556 */     addDebug("changeOfInterest dts: " + paramString2 + " extract: " + paramString3 + NEWLINE + 
/* 2557 */         PokUtils.outputList(entityList1));
/*      */ 
/*      */     
/* 2560 */     Profile profile2 = this.m_elist.getProfile().getNewInstance(this.m_db);
/* 2561 */     profile2.setValOnEffOn(paramString1, paramString1);
/*      */ 
/*      */     
/* 2564 */     EntityList entityList2 = this.m_db.getEntityList(profile2, new ExtractActionItem(null, this.m_db, profile2, paramString3), new EntityItem[] { new EntityItem(null, profile2, paramEntityItem
/*      */             
/* 2566 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */ 
/*      */     
/* 2569 */     addDebug("changeOfInterest dts: " + paramString1 + " extract: " + paramString3 + NEWLINE + 
/* 2570 */         PokUtils.outputList(entityList2));
/*      */ 
/*      */     
/* 2573 */     Hashtable hashtable1 = getStringRep(entityList1, paramHashtable);
/* 2574 */     Hashtable hashtable2 = getStringRep(entityList2, paramHashtable);
/*      */     
/* 2576 */     bool = changeOfInterest(hashtable1, hashtable2);
/* 2577 */     hashtable1.clear();
/* 2578 */     hashtable2.clear();
/*      */     
/* 2580 */     entityList2.dereference();
/* 2581 */     entityList1.dereference();
/*      */     
/* 2583 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Hashtable getStringRep(EntityList paramEntityList, Hashtable paramHashtable) {
/* 2593 */     addDebug("getStringRep: entered for profile.valon: " + paramEntityList.getProfile().getValOn());
/* 2594 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2595 */     if (paramHashtable == null) {
/* 2596 */       addDebug("getStringRep: coding ERROR attrOfInterest hashtable was null");
/* 2597 */       return hashtable;
/*      */     } 
/* 2599 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 2600 */     String[] arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 2601 */     if (arrayOfString == null)
/* 2602 */       addDebug("getStringRep: No list of 'attr of interest' found for " + entityGroup.getEntityType()); 
/*      */     byte b;
/* 2604 */     for (b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2605 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2606 */       String str = generateString(entityItem, arrayOfString);
/*      */ 
/*      */       
/* 2609 */       addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 2610 */       hashtable.put(entityItem.getKey(), str);
/*      */     } 
/*      */ 
/*      */     
/* 2614 */     for (b = 0; b < paramEntityList.getEntityGroupCount(); b++) {
/* 2615 */       entityGroup = paramEntityList.getEntityGroup(b);
/* 2616 */       arrayOfString = (String[])paramHashtable.get(entityGroup.getEntityType());
/* 2617 */       if (arrayOfString == null) {
/* 2618 */         addDebug("getStringRep: No list of 'attr of interest' found for " + entityGroup.getEntityType());
/*      */       }
/* 2620 */       for (byte b1 = 0; b1 < entityGroup.getEntityItemCount(); b1++) {
/* 2621 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 2622 */         String str = generateString(entityItem, arrayOfString);
/*      */ 
/*      */         
/* 2625 */         addDebug("getStringRep: put " + entityItem.getKey() + " " + str);
/* 2626 */         hashtable.put(entityItem.getKey(), str);
/*      */       } 
/*      */     } 
/* 2629 */     return hashtable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String generateString(EntityItem paramEntityItem, String[] paramArrayOfString) {
/* 2638 */     StringBuffer stringBuffer = new StringBuffer(paramEntityItem.getKey());
/* 2639 */     if (paramArrayOfString != null) {
/* 2640 */       for (byte b = 0; b < paramArrayOfString.length; b++) {
/* 2641 */         stringBuffer.append(":" + PokUtils.getAttributeValue(paramEntityItem, paramArrayOfString[b], ", ", "", false));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/* 2646 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean changeOfInterest(Hashtable paramHashtable1, Hashtable paramHashtable2) {
/* 2656 */     boolean bool = false;
/* 2657 */     if (paramHashtable1.keySet().containsAll(paramHashtable2.keySet()) && paramHashtable2
/* 2658 */       .keySet().containsAll(paramHashtable1.keySet())) {
/*      */       
/* 2660 */       addDebug("changeOfInterest: no change in structure found");
/*      */ 
/*      */       
/* 2663 */       if (!paramHashtable1.values().containsAll(paramHashtable2.values()) || 
/* 2664 */         !paramHashtable2.values().containsAll(paramHashtable1.values())) {
/* 2665 */         bool = true;
/* 2666 */         addDebug("changeOfInterest: difference in values found: " + NEWLINE + "prev " + paramHashtable2.values() + NEWLINE + "curr " + paramHashtable1
/* 2667 */             .values());
/*      */       } else {
/* 2669 */         addDebug("changeOfInterest: no change in values found");
/*      */       } 
/*      */     } else {
/*      */       
/* 2673 */       bool = true;
/* 2674 */       addDebug("changeOfInterest: difference in keysets(structure) found: " + NEWLINE + "prev " + paramHashtable2.keySet() + NEWLINE + "curr " + paramHashtable1
/* 2675 */           .keySet());
/*      */     } 
/*      */     
/* 2678 */     return bool;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkModelDates(EntityItem paramEntityItem, Vector<EntityItem> paramVector, String paramString1, String paramString2, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 2750 */     if (paramString2.length() == 0) {
/*      */       return;
/*      */     }
/* 2753 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 2754 */       EntityItem entityItem = paramVector.elementAt(b);
/* 2755 */       String str1 = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt1);
/* 2756 */       addDebug("checkModelDates  " + entityItem.getKey() + " EFFECTIVEDATE:" + str1);
/* 2757 */       String str2 = null;
/* 2758 */       if (paramInt2 == 1) {
/*      */ 
/*      */ 
/*      */         
/* 2762 */         str2 = "CANNOT_BE_LATER_ERR";
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 2767 */         str2 = "CANNOT_BE_EARLIER_ERR2";
/*      */       } 
/*      */       
/* 2770 */       boolean bool = checkDates(paramString2, str1, paramInt2);
/* 2771 */       if (!bool) {
/* 2772 */         this.args[0] = getLD_NDN(entityItem);
/* 2773 */         this.args[1] = getLD_Value(entityItem, "EFFECTIVEDATE");
/* 2774 */         this.args[2] = getLD_NDN(paramEntityItem);
/* 2775 */         this.args[3] = getLD_Value(paramEntityItem, paramString1);
/* 2776 */         createMessage(paramInt1, str2, this.args);
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
/*      */   protected void checkPsModelAvail(EntityList paramEntityList, EntityItem paramEntityItem, String paramString1, Vector paramVector, String paramString2) throws MiddlewareException, SQLException {
/* 2816 */     EntityGroup entityGroup = paramEntityList.getEntityGroup("AVAIL");
/* 2817 */     if (entityGroup == null) {
/* 2818 */       throw new MiddlewareException("AVAIL is missing from extract for " + paramEntityList.getParentActionItem().getActionItemKey());
/*      */     }
/* 2820 */     if (paramVector.size() > 0) {
/* 2821 */       Vector vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "146");
/* 2822 */       Hashtable hashtable = getAvailByCountry(vector, 4);
/*      */       
/* 2824 */       addDebug("checkPsModelAvail  mdlplannedAvailVct.size " + vector.size() + " mdlplaAvailCtryTbl " + hashtable
/* 2825 */           .keySet());
/*      */       
/* 2827 */       if (vector.size() > 0) {
/*      */ 
/*      */         
/* 2830 */         int i = getCheck_W_W_E(paramString1);
/*      */         
/* 2832 */         int j = getCheck_W_RW_RE(paramString1);
/* 2833 */         if (getEntityType().equals("SWPRODSTRUCT")) {
/* 2834 */           j = getCheck_W_E_E(paramString1);
/* 2835 */           i = j;
/*      */         } else {
/* 2837 */           j = getCheckLevel(j, paramEntityItem, "ANNDATE");
/* 2838 */           i = getCheckLevel(i, paramEntityItem, "ANNDATE");
/*      */         } 
/*      */         
/* 2841 */         checkPsAndModelAvails(paramEntityItem, j, i, hashtable, paramVector, paramString2, false);
/*      */       } 
/*      */       
/* 2844 */       vector.clear();
/* 2845 */       hashtable.clear();
/*      */     } else {
/* 2847 */       addDebug("checkPsModelAvail no PS-plannedAvailVct to check");
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
/*      */   protected void checkPsAndModelAvails(EntityItem paramEntityItem, int paramInt1, int paramInt2, Hashtable paramHashtable, Vector<EntityItem> paramVector, String paramString, boolean paramBoolean) throws MiddlewareException, SQLException {
/* 2867 */     addDebug("checkPsAndModelAvails mdlAvailCtryTbl ctrys " + paramHashtable.keySet());
/*      */     
/* 2869 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 2870 */       EntityItem entityItem1 = paramVector.elementAt(b);
/* 2871 */       EntityItem entityItem2 = getAvailPS(entityItem1, paramString);
/*      */ 
/*      */ 
/*      */       
/* 2875 */       EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem1, "COUNTRYLIST", paramInt2);
/* 2876 */       if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */         
/* 2878 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */         
/* 2880 */         StringBuffer stringBuffer = new StringBuffer();
/* 2881 */         Vector<EntityItem> vector = new Vector();
/* 2882 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 2883 */           if (arrayOfMetaFlag[b1].isSelected()) {
/*      */             
/* 2885 */             EntityItem entityItem = (EntityItem)paramHashtable.get(arrayOfMetaFlag[b1].getFlagCode());
/* 2886 */             if (entityItem == null) {
/* 2887 */               addDebug("checkPsAndModelAvails " + entityItem2.getKey() + " PS-plannedavail " + entityItem1.getKey() + " no MODEL-plannedavail for this ctry " + arrayOfMetaFlag[b1]
/* 2888 */                   .getFlagCode());
/*      */ 
/*      */               
/* 2891 */               if (stringBuffer.length() > 0) {
/* 2892 */                 stringBuffer.append(", ");
/*      */               }
/* 2894 */               stringBuffer.append(arrayOfMetaFlag[b1].toString());
/*      */             }
/* 2896 */             else if (!vector.contains(entityItem)) {
/* 2897 */               vector.add(entityItem);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2903 */         String str = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", paramInt1);
/* 2904 */         for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 2905 */           EntityItem entityItem = vector.elementAt(b2);
/*      */ 
/*      */           
/* 2908 */           String str1 = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt1);
/* 2909 */           addDebug("checkPsAndModelAvails " + entityItem2.getKey() + " PS-plannedavail " + entityItem1.getKey() + " EFFECTIVEDATE:" + str + " cannot be earlier than " + paramEntityItem
/* 2910 */               .getKey() + " plannedavail " + entityItem
/* 2911 */               .getKey() + " EFFECTIVEDATE:" + str1);
/* 2912 */           boolean bool = checkDates(str1, str, 2);
/* 2913 */           if (!bool) {
/*      */ 
/*      */ 
/*      */             
/* 2917 */             if (paramBoolean) {
/* 2918 */               this.args[0] = getLD_NDN(entityItem2) + " " + getLD_NDN(entityItem1);
/* 2919 */               this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*      */             } else {
/* 2921 */               this.args[0] = getLD_NDN(entityItem1);
/* 2922 */               this.args[2] = getLD_NDN(paramEntityItem);
/*      */             } 
/*      */             
/* 2925 */             this.args[1] = getLD_Value(entityItem1, "EFFECTIVEDATE");
/* 2926 */             this.args[3] = getLD_NDN(entityItem);
/* 2927 */             this.args[4] = getLD_Value(entityItem, "EFFECTIVEDATE");
/* 2928 */             createMessage(paramInt1, "CANNOT_BE_EARLIER_ERR3", this.args);
/*      */           } 
/*      */         } 
/* 2931 */         vector.clear();
/*      */         
/* 2933 */         if (stringBuffer.length() > 0) {
/* 2934 */           addDebug("checkPsAndModelAvails PS-plannedavail " + entityItem1.getKey() + " COUNTRYLIST had extra [" + stringBuffer + "]");
/*      */ 
/*      */           
/* 2937 */           if (paramBoolean) {
/* 2938 */             this.args[0] = getLD_NDN(entityItem2) + " " + getLD_NDN(entityItem1);
/* 2939 */             this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*      */           } else {
/* 2941 */             this.args[0] = getLD_NDN(entityItem1);
/* 2942 */             this.args[2] = getLD_NDN(paramEntityItem);
/*      */           } 
/* 2944 */           this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 2945 */           this.args[3] = stringBuffer.toString();
/* 2946 */           createMessage(paramInt2, "MODEL_AVAIL_CTRY_ERR", this.args);
/*      */         } 
/*      */       } else {
/*      */         
/* 2950 */         addDebug("checkPsAndModelAvails PS-plannedavail " + entityItem1.getKey() + " COUNTRYLIST was null");
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
/*      */   protected void checkPsModelLastOrderAvail(EntityList paramEntityList, EntityItem paramEntityItem, String paramString, Vector<EntityItem> paramVector1, Vector<EntityItem> paramVector2) throws MiddlewareException, SQLException {
/* 2983 */     EntityGroup entityGroup = paramEntityList.getEntityGroup("AVAIL");
/* 2984 */     if (entityGroup == null) {
/* 2985 */       throw new MiddlewareException("AVAIL is missing from extract for " + paramEntityList.getParentActionItem().getActionItemKey());
/*      */     }
/*      */     
/* 2988 */     if (paramVector1.size() > 0) {
/* 2989 */       Vector vector = PokUtils.getEntitiesWithMatchedAttr(entityGroup, "AVAILTYPE", "149");
/* 2990 */       Hashtable hashtable = getAvailByCountry(vector, 
/* 2991 */           getCheckLevel(4, paramEntityItem, "ANNDATE"));
/*      */       
/* 2993 */       addDebug("checkPsModelLastOrderAvail mdlLoAvailCtryTbl: " + hashtable);
/*      */       
/* 2995 */       int i = 0;
/* 2996 */       int j = 0;
/* 2997 */       if (getEntityType().equals("SWPRODSTRUCT")) {
/* 2998 */         i = getCheck_W_E_E(paramString);
/* 2999 */         j = getCheckLevel(4, paramEntityItem, "ANNDATE");
/*      */       } else {
/* 3001 */         i = getCheckLevel(getCheck_W_W_E(paramString), paramEntityItem, "ANNDATE");
/* 3002 */         j = getCheckLevel(4, paramEntityItem, "ANNDATE");
/*      */       } 
/* 3004 */       for (byte b = 0; b < paramVector1.size(); b++) {
/* 3005 */         EntityItem entityItem = paramVector1.elementAt(b);
/*      */         
/* 3007 */         EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem, "COUNTRYLIST", j);
/* 3008 */         if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */           
/* 3010 */           MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */           
/* 3012 */           StringBuffer stringBuffer = new StringBuffer();
/* 3013 */           Vector<EntityItem> vector1 = new Vector();
/* 3014 */           for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 3015 */             if (arrayOfMetaFlag[b1].isSelected()) {
/*      */               
/* 3017 */               EntityItem entityItem1 = (EntityItem)hashtable.get(arrayOfMetaFlag[b1].getFlagCode());
/* 3018 */               if (entityItem1 == null) {
/* 3019 */                 addDebug("checkPsModelLastOrderAvail PS-lastorder:" + entityItem.getKey() + " No MODEL-lastorderavail for ctry " + arrayOfMetaFlag[b1]
/* 3020 */                     .getFlagCode());
/*      */                 
/* 3022 */                 stringBuffer.append(arrayOfMetaFlag[b1].getFlagCode() + " ");
/*      */               }
/* 3024 */               else if (!vector1.contains(entityItem1)) {
/* 3025 */                 vector1.add(entityItem1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 3030 */           String str = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", i);
/*      */           
/* 3032 */           for (byte b2 = 0; b2 < vector1.size(); b2++) {
/* 3033 */             EntityItem entityItem1 = vector1.elementAt(b2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3041 */             String str1 = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", i);
/* 3042 */             addDebug("checkPsModelLastOrderAvail  model lastorder: " + entityItem1
/* 3043 */                 .getKey() + " EFFECTIVEDATE:" + str1 + " PS-lastorder:" + entityItem
/* 3044 */                 .getKey() + " EFFECTIVEDATE:" + str);
/* 3045 */             boolean bool = checkDates(str1, str, 1);
/* 3046 */             if (!bool) {
/*      */ 
/*      */               
/* 3049 */               this.args[0] = getLD_NDN(entityItem);
/* 3050 */               this.args[1] = getLD_Value(entityItem, "EFFECTIVEDATE");
/* 3051 */               this.args[2] = getLD_NDN(paramEntityItem);
/* 3052 */               this.args[3] = getLD_NDN(entityItem1);
/* 3053 */               this.args[4] = getLD_Value(entityItem1, "EFFECTIVEDATE");
/* 3054 */               createMessage(i, "CANNOT_BE_LATER_ERR2", this.args);
/*      */             } 
/*      */           } 
/* 3057 */           vector1.clear();
/* 3058 */           if (stringBuffer.length() > 0) {
/* 3059 */             addDebug("checkPsModelLastOrderAvail PS-lastorder:" + entityItem.getKey() + " COUNTRYLIST had ctry [" + stringBuffer + "] that were not in any lastorder MODELAVAIL");
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 3064 */           addDebug("checkPsModelLastOrderAvail PS-lastorder: " + entityItem.getKey() + " COUNTRYLIST was null");
/*      */         } 
/*      */       } 
/*      */       
/* 3068 */       if (paramVector2.size() > 0 && hashtable.size() > 0) {
/*      */         
/* 3070 */         ArrayList arrayList = getCountriesAsList(paramVector1, j);
/* 3071 */         addDebug("checkPsModelLastOrderAvail PS-lastOrderAvlCtry " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3084 */         for (byte b1 = 0; b1 < paramVector2.size(); b1++) {
/* 3085 */           EntityItem entityItem = paramVector2.elementAt(b1);
/* 3086 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(entityItem, "COUNTRYLIST", j);
/* 3087 */           if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */             
/* 3089 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/*      */             
/* 3091 */             Vector<EntityItem> vector1 = new Vector(); byte b2;
/* 3092 */             for (b2 = 0; b2 < arrayOfMetaFlag.length; b2++) {
/* 3093 */               if (arrayOfMetaFlag[b2].isSelected() && 
/* 3094 */                 !arrayList.contains(arrayOfMetaFlag[b2].getFlagCode())) {
/* 3095 */                 addDebug("checkPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " No PS lastorderavail for ctry " + arrayOfMetaFlag[b2]
/* 3096 */                     .getFlagCode());
/*      */                 
/* 3098 */                 EntityItem entityItem1 = (EntityItem)hashtable.get(arrayOfMetaFlag[b2].getFlagCode());
/* 3099 */                 if (entityItem1 != null) {
/* 3100 */                   addDebug("checkPsModelLastOrderAvail PS-plannedavail:" + entityItem.getKey() + " MODEL-lastorderavail for ctry " + arrayOfMetaFlag[b2]
/* 3101 */                       .getFlagCode());
/* 3102 */                   if (!vector1.contains(entityItem1)) {
/* 3103 */                     vector1.add(entityItem1);
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/* 3109 */             for (b2 = 0; b2 < vector1.size(); b2++) {
/* 3110 */               EntityItem entityItem1 = vector1.elementAt(b2);
/*      */ 
/*      */               
/* 3113 */               this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 3114 */               this.args[1] = getLD_NDN(paramEntityItem);
/* 3115 */               this.args[2] = getLD_NDN(entityItem1);
/* 3116 */               createMessage(j, "PS_LAST_ORDER_ERR", this.args);
/*      */             } 
/* 3118 */             vector1.clear();
/*      */           } 
/*      */         } 
/*      */         
/* 3122 */         arrayList.clear();
/*      */       } 
/*      */       
/* 3125 */       vector.clear();
/* 3126 */       hashtable.clear();
/*      */     } else {
/* 3128 */       addDebug("checkPsModelLastOrderAvail no PS-lastorderAvailVct to check");
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
/*      */   protected boolean getAvailByOSN(Hashtable<String, Vector> paramHashtable, Vector<EntityItem> paramVector, boolean paramBoolean, int paramInt) throws SQLException, MiddlewareException {
/* 3162 */     boolean bool = false;
/* 3163 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 3164 */       EntityItem entityItem = paramVector.elementAt(b);
/* 3165 */       String str = null;
/* 3166 */       if (paramBoolean) {
/* 3167 */         if (domainInRuleList(entityItem, "XCC_LIST")) {
/* 3168 */           str = PokUtils.getAttributeFlagValue(entityItem, "ORDERSYSNAME");
/* 3169 */           addDebug("getAvailByOSN " + entityItem.getKey() + " is in the xcclist osn: " + str);
/* 3170 */           if (str == null) {
/* 3171 */             if (!this.errMsgVct.contains(entityItem.getKey())) {
/* 3172 */               this.errMsgVct.add(entityItem.getKey());
/*      */               
/* 3174 */               this.args[0] = getLD_NDN(entityItem);
/* 3175 */               this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "ORDERSYSNAME", "ORDERSYSNAME");
/* 3176 */               createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/*      */             } 
/* 3178 */             bool = true;
/*      */ 
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } else {
/* 3184 */           addDebug("getAvailByOSN " + entityItem.getKey() + " is not in the xcclist");
/* 3185 */           str = "DOMAIN_NOT_IN_LIST";
/*      */         } 
/*      */       } else {
/*      */         
/* 3189 */         str = PokUtils.getAttributeFlagValue(entityItem, "ORDERSYSNAME");
/* 3190 */         addDebug("getAvailByOSN " + entityItem.getKey() + " no xcclist osn: " + str);
/* 3191 */         if (str == null) {
/* 3192 */           if (!this.errMsgVct.contains(entityItem.getKey())) {
/* 3193 */             this.errMsgVct.add(entityItem.getKey());
/*      */             
/* 3195 */             this.args[0] = getLD_NDN(entityItem);
/* 3196 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "ORDERSYSNAME", "ORDERSYSNAME");
/* 3197 */             createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/*      */           } 
/* 3199 */           bool = true;
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/* 3204 */       Vector<EntityItem> vector = (Vector)paramHashtable.get(str);
/* 3205 */       if (vector == null) {
/* 3206 */         vector = new Vector();
/* 3207 */         paramHashtable.put(str, vector);
/*      */       } 
/* 3209 */       vector.add(entityItem);
/*      */       continue;
/*      */     } 
/* 3212 */     return bool;
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
/*      */   protected void checkAvailCtryByOSN(Hashtable paramHashtable1, Hashtable paramHashtable2, String paramString, EntityItem paramEntityItem, boolean paramBoolean, int paramInt) throws SQLException, MiddlewareException {
/* 3237 */     addDebug("checkAvailCtryByOSN useXCClistName " + paramBoolean);
/* 3238 */     String str = "";
/* 3239 */     if (paramEntityItem != null) {
/* 3240 */       str = getLD_NDN(paramEntityItem) + " ";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 3245 */     Set set = paramHashtable1.keySet();
/* 3246 */     Iterator<String> iterator = set.iterator();
/* 3247 */     while (iterator.hasNext()) {
/* 3248 */       String str1 = iterator.next();
/*      */       
/* 3250 */       Vector<EntityItem> vector = (Vector)paramHashtable1.get(str1);
/* 3251 */       Vector vector1 = (Vector)paramHashtable2.get(str1);
/*      */       
/* 3253 */       if (vector1 == null) {
/* 3254 */         addDebug("checkAvailCtryByOSN no rightside avails to check for osn " + str1);
/*      */ 
/*      */         
/* 3257 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 3258 */           EntityItem entityItem = vector.elementAt(b1);
/* 3259 */           addDebug("checkAvailCtryByOSN " + entityItem.getKey() + " did not have any osn match in rightside set");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3266 */           if (paramString.equals("MODEL_AVAIL_OSNCTRY_ERR")) {
/* 3267 */             this.args[0] = getLD_NDN(entityItem);
/* 3268 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 3269 */             this.args[2] = str;
/* 3270 */             if (str1.equals("DOMAIN_NOT_IN_LIST")) {
/* 3271 */               this.args[3] = "";
/*      */             } else {
/* 3273 */               this.args[3] = "for " + getLD_Value(entityItem, "ORDERSYSNAME");
/*      */             } 
/* 3275 */             this.args[4] = PokUtils.getAttributeValue(entityItem, "COUNTRYLIST", ", ", "");
/* 3276 */           } else if (paramString.equals("MODELROOT_AVAIL_OSNCTRY_ERR")) {
/*      */ 
/*      */             
/* 3279 */             this.args[0] = str;
/* 3280 */             this.args[1] = getLD_NDN(entityItem);
/* 3281 */             this.args[2] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */             
/* 3283 */             if (str1.equals("DOMAIN_NOT_IN_LIST")) {
/* 3284 */               this.args[3] = "";
/*      */             } else {
/* 3286 */               this.args[3] = "for " + getLD_Value(entityItem, "ORDERSYSNAME");
/*      */             } 
/* 3288 */             this.args[4] = PokUtils.getAttributeValue(entityItem, "COUNTRYLIST", ", ", "");
/*      */           } else {
/* 3290 */             this.args[0] = str + getLD_NDN(entityItem);
/* 3291 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 3292 */             if (str1.equals("DOMAIN_NOT_IN_LIST")) {
/* 3293 */               this.args[2] = "";
/*      */             } else {
/* 3295 */               this.args[2] = "for " + getLD_Value(entityItem, "ORDERSYSNAME");
/*      */             } 
/* 3297 */             this.args[3] = PokUtils.getAttributeValue(entityItem, "COUNTRYLIST", ", ", "");
/*      */           } 
/*      */           
/* 3300 */           createMessage(paramInt, paramString, this.args);
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/* 3305 */       ArrayList arrayList = getCountriesAsList(vector1, paramInt);
/* 3306 */       addDebug("checkAvailCtryByOSN osn " + str1 + " rightside osnctrylist " + arrayList);
/* 3307 */       for (byte b = 0; b < vector.size(); b++) {
/* 3308 */         EntityItem entityItem = vector.elementAt(b);
/* 3309 */         String str2 = checkCtryMismatch(entityItem, arrayList, paramInt);
/* 3310 */         if (str2.length() > 0) {
/* 3311 */           addDebug("checkAvailCtryByOSN " + entityItem.getKey() + " COUNTRYLIST had extra [" + str2 + "]");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3318 */           if (paramString.equals("MODEL_AVAIL_OSNCTRY_ERR")) {
/* 3319 */             this.args[0] = getLD_NDN(entityItem);
/* 3320 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 3321 */             this.args[2] = str;
/* 3322 */             if (str1.equals("DOMAIN_NOT_IN_LIST")) {
/* 3323 */               this.args[3] = "";
/*      */             } else {
/* 3325 */               this.args[3] = "for " + getLD_Value(entityItem, "ORDERSYSNAME");
/*      */             } 
/* 3327 */             this.args[4] = str2;
/* 3328 */           } else if (paramString.equals("MODELROOT_AVAIL_OSNCTRY_ERR")) {
/*      */ 
/*      */             
/* 3331 */             this.args[0] = str;
/* 3332 */             this.args[1] = getLD_NDN(entityItem);
/* 3333 */             this.args[2] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */             
/* 3335 */             if (str1.equals("DOMAIN_NOT_IN_LIST")) {
/* 3336 */               this.args[3] = "";
/*      */             } else {
/* 3338 */               this.args[3] = "for " + getLD_Value(entityItem, "ORDERSYSNAME");
/*      */             } 
/* 3340 */             this.args[4] = str2;
/*      */           } else {
/* 3342 */             this.args[0] = str + getLD_NDN(entityItem);
/* 3343 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 3344 */             if (str1.equals("DOMAIN_NOT_IN_LIST")) {
/* 3345 */               this.args[2] = "";
/*      */             } else {
/* 3347 */               this.args[2] = "for " + getLD_Value(entityItem, "ORDERSYSNAME");
/*      */             } 
/* 3349 */             this.args[3] = str2;
/*      */           } 
/* 3351 */           createMessage(paramInt, paramString, this.args);
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
/*      */   protected void checkAvailDatesByCtryByOSN(Hashtable paramHashtable1, Hashtable paramHashtable2, EntityItem paramEntityItem, int paramInt1, int paramInt2, String paramString, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 3374 */     String str = "";
/* 3375 */     if (paramEntityItem != null) {
/* 3376 */       str = getLD_NDN(paramEntityItem) + " ";
/*      */     }
/*      */ 
/*      */     
/* 3380 */     Set set = paramHashtable1.keySet();
/* 3381 */     Iterator<String> iterator = set.iterator();
/* 3382 */     while (iterator.hasNext()) {
/* 3383 */       String str1 = iterator.next();
/*      */       
/* 3385 */       Vector<EntityItem> vector = (Vector)paramHashtable1.get(str1);
/* 3386 */       Vector vector1 = (Vector)paramHashtable2.get(str1);
/*      */       
/* 3388 */       if (vector1 == null) {
/* 3389 */         addDebug("checkAvailDatesByCtryByOSN no rightside avails to check for osn " + str1);
/*      */         
/*      */         continue;
/*      */       } 
/* 3393 */       Hashtable hashtable = getAvailByCountry(vector1, paramInt2);
/* 3394 */       addDebug("checkAvailDatesByCtryByOSN osn " + str1 + " rightsideAvailCtryTbl " + hashtable.keySet());
/* 3395 */       for (byte b = 0; b < vector.size(); b++) {
/* 3396 */         EntityItem entityItem = vector.elementAt(b);
/* 3397 */         addDebug("checkAvailDatesByCtryByOSN leftside " + entityItem.getKey() + " ctrys " + PokUtils.getAttributeFlagValue(entityItem, "COUNTRYLIST"));
/* 3398 */         Vector<EntityItem> vector2 = new Vector();
/* 3399 */         getMatchingAvails(entityItem, hashtable, vector2, paramInt2);
/*      */         
/* 3401 */         for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 3402 */           EntityItem entityItem1 = vector2.elementAt(b1);
/* 3403 */           int i = paramInt2;
/* 3404 */           if (paramBoolean) {
/* 3405 */             i = getAvailCheckLevel(i, entityItem1);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 3411 */           String str2 = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt2);
/* 3412 */           String str3 = getAttrValueAndCheckLvl(entityItem1, "EFFECTIVEDATE", i);
/* 3413 */           addDebug("checkAvailDatesByCtryByOSN " + ((paramEntityItem == null) ? "" : paramEntityItem.getKey()) + " " + entityItem
/* 3414 */               .getKey() + " EFFECTIVEDATE:" + str2 + ((paramInt1 == 1) ? " GR_EQ " : " LT_EQ ") + " " + entityItem1
/* 3415 */               .getKey() + " EFFECTIVEDATE:" + str3);
/*      */           
/* 3417 */           boolean bool = checkDates(str2, str3, paramInt1);
/* 3418 */           if (!bool) {
/* 3419 */             String str4 = "CANNOT_BE_EARLIER_ERR3";
/* 3420 */             if (paramInt1 != 1) {
/* 3421 */               str4 = "CANNOT_BE_LATER_ERR2";
/*      */             }
/*      */ 
/*      */ 
/*      */             
/* 3426 */             this.args[0] = str + getLD_NDN(entityItem);
/* 3427 */             this.args[1] = getLD_Value(entityItem, "EFFECTIVEDATE");
/* 3428 */             this.args[2] = paramString;
/* 3429 */             this.args[3] = getLD_NDN(entityItem1);
/* 3430 */             this.args[4] = getLD_Value(entityItem1, "EFFECTIVEDATE");
/* 3431 */             createMessage(i, str4, this.args);
/*      */           } 
/*      */         } 
/* 3434 */         vector2.clear();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Hashtable getAvailByCountry(Vector<EntityItem> paramVector, int paramInt) {
/* 3445 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 3446 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 3447 */       EntityItem entityItem = paramVector.elementAt(b);
/* 3448 */       EANAttribute eANAttribute = entityItem.getAttribute("COUNTRYLIST");
/* 3449 */       if (eANAttribute != null && eANAttribute.toString().length() > 0) {
/*      */         
/* 3451 */         MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 3452 */         for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/* 3453 */           if (arrayOfMetaFlag[b1].isSelected()) {
/*      */             
/* 3455 */             if (hashtable.containsKey(arrayOfMetaFlag[b1].getFlagCode())) {
/* 3456 */               String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/* 3457 */               addDebug("getAvailByCountry already found " + ((EntityItem)hashtable
/* 3458 */                   .get(arrayOfMetaFlag[b1].getFlagCode())).getKey() + " for ctry[" + arrayOfMetaFlag[b1]
/* 3459 */                   .getFlagCode() + "] on " + entityItem
/* 3460 */                   .getKey() + " with AVAILTYPE " + str);
/*      */             } 
/* 3462 */             hashtable.put(arrayOfMetaFlag[b1].getFlagCode(), entityItem);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/* 3467 */     return hashtable;
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
/*      */   protected String checkCtryMismatch(EntityItem paramEntityItem, Collection paramCollection, int paramInt) throws SQLException, MiddlewareException {
/* 3482 */     StringBuffer stringBuffer = new StringBuffer();
/* 3483 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(paramEntityItem, "COUNTRYLIST", paramInt);
/* 3484 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/* 3485 */       StringBuffer stringBuffer1 = new StringBuffer();
/*      */       
/* 3487 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 3488 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 3489 */         if (arrayOfMetaFlag[b].isSelected() && 
/* 3490 */           !paramCollection.contains(arrayOfMetaFlag[b].getFlagCode())) {
/* 3491 */           stringBuffer1.append(arrayOfMetaFlag[b].getFlagCode() + " ");
/* 3492 */           if (stringBuffer.length() > 0) {
/* 3493 */             stringBuffer.append(", ");
/*      */           }
/* 3495 */           stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */         } 
/*      */       } 
/*      */       
/* 3499 */       if (stringBuffer1.length() > 0) {
/* 3500 */         addDebug("checkCtryMismatch1 " + paramEntityItem.getKey() + " missingflags:" + stringBuffer1.toString());
/*      */       }
/*      */     } 
/* 3503 */     return stringBuffer.toString();
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
/*      */   protected String checkCtryMismatch(EntityItem paramEntityItem, Hashtable paramHashtable, int paramInt) throws SQLException, MiddlewareException {
/* 3517 */     StringBuffer stringBuffer = new StringBuffer();
/* 3518 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(paramEntityItem, "COUNTRYLIST", paramInt);
/* 3519 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/* 3520 */       StringBuffer stringBuffer1 = new StringBuffer();
/*      */       
/* 3522 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 3523 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 3524 */         if (arrayOfMetaFlag[b].isSelected() && 
/* 3525 */           !paramHashtable.containsKey(arrayOfMetaFlag[b].getFlagCode())) {
/* 3526 */           stringBuffer1.append(arrayOfMetaFlag[b].getFlagCode() + " ");
/*      */           
/* 3528 */           if (stringBuffer.length() > 0) {
/* 3529 */             stringBuffer.append(", ");
/*      */           }
/* 3531 */           stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */         } 
/*      */       } 
/*      */       
/* 3535 */       if (stringBuffer1.length() > 0) {
/* 3536 */         addDebug("checkCtryMismatch2 " + paramEntityItem.getKey() + " missingflags:" + stringBuffer1.toString());
/*      */       }
/*      */     } 
/* 3539 */     return stringBuffer.toString();
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
/*      */   protected void getMatchingAvails(EntityItem paramEntityItem, Hashtable paramHashtable, Vector<EntityItem> paramVector, int paramInt) throws SQLException, MiddlewareException {
/* 3553 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(paramEntityItem, "COUNTRYLIST", paramInt);
/* 3554 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 3556 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 3557 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 3558 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 3560 */           EntityItem entityItem = (EntityItem)paramHashtable.get(arrayOfMetaFlag[b].getFlagCode());
/* 3561 */           if (entityItem != null && 
/* 3562 */             !paramVector.contains(entityItem)) {
/* 3563 */             paramVector.add(entityItem);
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
/*      */   protected String checkCtryMismatch(EntityItem paramEntityItem, Hashtable paramHashtable, Vector<EntityItem> paramVector, int paramInt) throws SQLException, MiddlewareException {
/* 3583 */     StringBuffer stringBuffer = new StringBuffer();
/* 3584 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(paramEntityItem, "COUNTRYLIST", paramInt);
/* 3585 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/* 3586 */       StringBuffer stringBuffer1 = new StringBuffer();
/*      */       
/* 3588 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 3589 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/* 3590 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 3592 */           EntityItem entityItem = (EntityItem)paramHashtable.get(arrayOfMetaFlag[b].getFlagCode());
/* 3593 */           if (entityItem == null) {
/* 3594 */             stringBuffer1.append(arrayOfMetaFlag[b].getFlagCode() + " ");
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 3599 */             if (stringBuffer.length() > 0) {
/* 3600 */               stringBuffer.append(", ");
/*      */             }
/* 3602 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */           }
/* 3604 */           else if (!paramVector.contains(entityItem)) {
/* 3605 */             paramVector.add(entityItem);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 3610 */       if (stringBuffer1.length() > 0) {
/* 3611 */         addDebug("checkCtryMismatch3 " + paramEntityItem.getKey() + " missingflags:" + stringBuffer1.toString());
/*      */       }
/*      */     } 
/*      */     
/* 3615 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityItem getUpLinkEntityItem(EntityItem paramEntityItem, String paramString) {
/* 3626 */     EANEntity eANEntity = null;
/* 3627 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 3628 */       EANEntity eANEntity1 = paramEntityItem.getUpLink(b);
/* 3629 */       if (eANEntity1.getEntityType().equals(paramString)) {
/* 3630 */         eANEntity = eANEntity1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 3634 */     return (EntityItem)eANEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityItem getDownLinkEntityItem(EntityItem paramEntityItem, String paramString) {
/* 3645 */     EANEntity eANEntity = null;
/* 3646 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 3647 */       EANEntity eANEntity1 = paramEntityItem.getDownLink(b);
/* 3648 */       if (eANEntity1.getEntityType().equals(paramString)) {
/* 3649 */         eANEntity = eANEntity1;
/*      */         break;
/*      */       } 
/*      */     } 
/* 3653 */     return (EntityItem)eANEntity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Vector getDownLinkEntityItems(EntityItem paramEntityItem, String paramString) {
/* 3664 */     Vector<EANEntity> vector = new Vector();
/* 3665 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 3666 */       EANEntity eANEntity = paramEntityItem.getDownLink(b);
/* 3667 */       if (eANEntity.getEntityType().equals(paramString)) {
/* 3668 */         vector.add(eANEntity);
/*      */       }
/*      */     } 
/* 3671 */     return vector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkPlannedAvailsExist(Vector paramVector, int paramInt) {
/* 3680 */     if (paramVector.size() == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3685 */       this.args[0] = "Planned Availability";
/* 3686 */       createMessage(paramInt, "MINIMUM_ERR", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkPlannedAvailsOrFirstOrderAvailsExist(Vector paramVector1, Vector paramVector2, int paramInt) {
/* 3697 */     if (paramVector1.size() == 0 && paramVector2.size() == 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3702 */       this.args[0] = "Planned Availability or First Order Availability";
/* 3703 */       createMessage(paramInt, "MINIMUM_ERR", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkPlannedAvailsStatus(Vector paramVector, EntityItem paramEntityItem, int paramInt) {
/* 3713 */     boolean bool = "FINAL".equals(getAttributeFlagEnabledValue(paramEntityItem, "DATAQUALITY"));
/* 3714 */     addDebug("check plannedavail status: isDQFinal: " + bool);
/* 3715 */     if (bool && 
/* 3716 */       paramVector != null && paramVector.size() > 0) {
/* 3717 */       boolean bool1 = false;
/* 3718 */       for (EntityItem entityItem : paramVector) {
/*      */         
/* 3720 */         String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 3721 */         addDebug("check plannedavail status " + entityItem.getKey() + " STATUS: " + str);
/* 3722 */         if ("0040".equalsIgnoreCase(str) || "0020".equalsIgnoreCase(str)) {
/* 3723 */           bool1 = true;
/*      */           break;
/*      */         } 
/*      */       } 
/* 3727 */       if (!bool1) {
/* 3728 */         this.args[0] = "Planned Availability";
/* 3729 */         createMessage(paramInt, "AVAILSTATUS_ERROR", this.args);
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
/*      */   protected void checkLOAvailForCtryExists(EntityItem paramEntityItem, Collection paramCollection, int paramInt) throws SQLException, MiddlewareException {
/* 3746 */     checkLOAvailForCtryExists((EntityItem)null, paramEntityItem, paramCollection, paramInt);
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
/*      */   protected void checkLOAvailForCtryExists(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Collection paramCollection, int paramInt) throws SQLException, MiddlewareException {
/* 3760 */     String str = checkCtryMismatch(paramEntityItem2, paramCollection, paramInt);
/* 3761 */     if (str.length() > 0) {
/* 3762 */       addDebug("checkLOAvailForCtryExists eosavail " + paramEntityItem2.getKey() + " COUNTRYLIST had extra [" + str + "]");
/*      */ 
/*      */ 
/*      */       
/* 3766 */       String str1 = "";
/* 3767 */       if (paramEntityItem1 != null)
/*      */       {
/* 3769 */         str1 = getLD_NDN(paramEntityItem1) + " ";
/*      */       }
/* 3771 */       this.args[0] = str1 + getLD_NDN(paramEntityItem2);
/* 3772 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 3773 */       this.args[2] = str;
/* 3774 */       createMessage(paramInt, "MISSING_LO_CTRY_ERR", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeNonRFAAVAIL(Vector paramVector) {
/* 3784 */     if (paramVector.size() > 0) {
/*      */       
/* 3786 */       EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()];
/* 3787 */       paramVector.copyInto((Object[])arrayOfEntityItem);
/* 3788 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 3789 */         EntityItem entityItem = arrayOfEntityItem[b];
/* 3790 */         String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 3791 */         if (str == null) {
/* 3792 */           str = "RFA";
/*      */         }
/* 3794 */         if (!"RFA".equals(str)) {
/* 3795 */           paramVector.remove(entityItem);
/* 3796 */           addDebug("removeNonRFAAVAIL removing non RFA " + entityItem.getKey() + " availtype:" + 
/* 3797 */               getAttributeFlagEnabledValue(entityItem, "AVAILTYPE"));
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
/*      */   protected void removeNonRFAAndEPICAVAIL(Vector paramVector) {
/* 3810 */     if (paramVector.size() > 0) {
/*      */       
/* 3812 */       EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()];
/* 3813 */       paramVector.copyInto((Object[])arrayOfEntityItem);
/* 3814 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 3815 */         EntityItem entityItem = arrayOfEntityItem[b];
/* 3816 */         String str = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 3817 */         if (str == null) {
/* 3818 */           str = "RFA";
/*      */         }
/* 3820 */         if (!"RFA".equals(str) && !"EPIC".equals(str)) {
/* 3821 */           paramVector.remove(entityItem);
/* 3822 */           addDebug("removeNonRFAAVAIL removing non RFA and EPIC " + entityItem.getKey() + " AVAILANNTYPE: " + str + " availtype:" + 
/* 3823 */               getAttributeFlagEnabledValue(entityItem, "AVAILTYPE"));
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
/*      */   protected void checkAvailAnnType() throws SQLException, MiddlewareException {
/* 3837 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 3838 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 3839 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 3840 */       Vector vector = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 3841 */       if (vector.size() == 0)
/*      */       {
/* 3843 */         vector = PokUtils.getAllLinkedEntities(entityItem, "ANNAVAILA", "ANNOUNCEMENT");
/*      */       }
/* 3845 */       checkAvailAnnType(entityItem, vector, 4);
/* 3846 */       vector.clear();
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
/*      */   protected boolean checkAvailAnnType(EntityItem paramEntityItem, Vector<EntityItem> paramVector, int paramInt) throws SQLException, MiddlewareException {
/* 3864 */     boolean bool = true;
/* 3865 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "AVAILANNTYPE");
/* 3866 */     addDebug("checkAvailAnnType " + paramEntityItem.getKey() + " annCnt " + paramVector.size() + " availAnntypeFlag " + str);
/*      */     
/* 3868 */     if (str == null) {
/* 3869 */       str = "RFA";
/*      */     }
/*      */     
/* 3872 */     if (paramVector.size() > 0 && !"RFA".equals(str) && !"EPIC".equals(str)) {
/*      */       
/* 3874 */       this.args[0] = getLD_NDN(paramEntityItem);
/* 3875 */       if (paramEntityItem.getEntityID() == getEntityID() && paramEntityItem.getEntityType().equals(getEntityType())) {
/* 3876 */         this.args[0] = "";
/*      */       }
/* 3878 */       for (byte b = 0; b < paramVector.size(); b++) {
/* 3879 */         this.args[1] = getLD_Value(paramVector.elementAt(b), "ANNTYPE");
/* 3880 */         this.args[2] = getLD_NDN(paramVector.elementAt(b));
/* 3881 */         createMessage(paramInt, "MUST_NOT_BE_IN_THIS_ERR", this.args);
/*      */       } 
/* 3883 */       bool = false;
/*      */     } 
/* 3885 */     return bool;
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
/*      */   protected void checkPlannedAvailForCtryExists(EntityItem paramEntityItem, Collection paramCollection, int paramInt) throws SQLException, MiddlewareException {
/* 3898 */     checkPlannedAvailForCtryExists((EntityItem)null, paramEntityItem, paramCollection, paramInt);
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
/*      */   protected void checkPlannedAvailForCtryExists(EntityItem paramEntityItem1, EntityItem paramEntityItem2, Collection paramCollection, int paramInt) throws SQLException, MiddlewareException {
/* 3913 */     String str = checkCtryMismatch(paramEntityItem2, paramCollection, paramInt);
/* 3914 */     if (str.length() > 0) {
/* 3915 */       addDebug("checkPlannedAvailForCtryExists lofoavail " + paramEntityItem2.getKey() + " COUNTRYLIST had extra [" + str + "]");
/*      */ 
/*      */ 
/*      */       
/* 3919 */       String str1 = "";
/* 3920 */       if (paramEntityItem1 != null)
/*      */       {
/* 3922 */         str1 = getLD_NDN(paramEntityItem1) + " ";
/*      */       }
/* 3924 */       this.args[0] = str1 + getLD_NDN(paramEntityItem2);
/* 3925 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 3926 */       this.args[2] = str;
/* 3927 */       createMessage(paramInt, "MISSING_PLA_CTRY_ERR", this.args);
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
/*      */   protected void checkAvailCtryInEntity(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3, Collection paramCollection, int paramInt) throws SQLException, MiddlewareException {
/* 3946 */     String str = checkCtryMismatch(paramEntityItem2, paramCollection, paramInt);
/* 3947 */     if (str.length() > 0) {
/* 3948 */       addDebug("checkAvailCtryInEntity " + paramEntityItem2.getKey() + " COUNTRYLIST had extra [" + str + "]");
/*      */ 
/*      */       
/* 3951 */       String str1 = "";
/* 3952 */       if (paramEntityItem1 != null)
/*      */       {
/* 3954 */         str1 = getLD_NDN(paramEntityItem1) + " ";
/*      */       }
/* 3956 */       String str2 = getLD_NDN(paramEntityItem2);
/*      */       
/* 3958 */       if (paramEntityItem2.getEntityID() == getEntityID() && paramEntityItem2.getEntityType().equals(getEntityType())) {
/* 3959 */         str2 = "";
/*      */       }
/* 3961 */       this.args[0] = str1 + str2;
/* 3962 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 3963 */       this.args[2] = getLD_NDN(paramEntityItem3);
/* 3964 */       this.args[3] = this.args[1];
/* 3965 */       this.args[4] = str;
/*      */ 
/*      */       
/* 3968 */       createMessage(4, "INCLUDE_ERR2", this.args);
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
/*      */   protected void checkCanNotBeLater(EntityItem paramEntityItem, String paramString1, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 3988 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem, paramString1, paramInt);
/* 3989 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem, paramString2, paramInt);
/* 3990 */     addDebug("checkCanNotBeLater " + paramEntityItem.getKey() + " " + paramString1 + ":" + str1 + " " + paramString2 + ":" + str2);
/* 3991 */     boolean bool = checkDates(str1, str2, 2);
/* 3992 */     if (!bool) {
/* 3993 */       String str = "";
/*      */ 
/*      */       
/* 3996 */       if (!paramEntityItem.getEntityType().equals(getEntityType()) && paramEntityItem
/* 3997 */         .getEntityID() != getEntityID())
/*      */       {
/* 3999 */         str = getLD_NDN(paramEntityItem) + " ";
/*      */       }
/* 4001 */       this.args[0] = str + getLD_Value(paramEntityItem, paramString1);
/* 4002 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 4003 */       this.args[2] = getLD_Value(paramEntityItem, paramString2);
/* 4004 */       createMessage(paramInt, "CANNOT_BE_LATER_ERR1", this.args);
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
/*      */   protected void checkCanNotBeLater(EntityItem paramEntityItem1, String paramString1, EntityItem paramEntityItem2, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 4022 */     checkCanNotBeLater((EntityItem)null, paramEntityItem1, paramString1, paramEntityItem2, paramString2, paramInt);
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
/*      */   protected void checkCanNotBeLater(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, EntityItem paramEntityItem3, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 4040 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem2, paramString1, paramInt);
/* 4041 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem3, paramString2, paramInt);
/* 4042 */     addDebug("checkCanNotBeLater " + ((paramEntityItem1 == null) ? "" : paramEntityItem1.getKey()) + "  " + paramEntityItem2
/* 4043 */         .getKey() + " " + paramString1 + ":" + str1 + " " + paramEntityItem3.getKey() + " " + paramString2 + ":" + str2);
/*      */     
/* 4045 */     boolean bool = checkDates(str1, str2, 2);
/* 4046 */     if (bool) {
/* 4047 */       if (str1.length() > 0 && !Character.isDigit(str1.charAt(0))) {
/* 4048 */         bool = false;
/*      */       }
/* 4050 */       if (str2.length() > 0 && !Character.isDigit(str2.charAt(0))) {
/* 4051 */         bool = false;
/*      */       }
/*      */     } 
/* 4054 */     if (!bool) {
/*      */ 
/*      */       
/* 4057 */       String str3 = "";
/* 4058 */       if (paramEntityItem1 != null) {
/* 4059 */         str3 = getLD_NDN(paramEntityItem1) + " ";
/*      */       }
/* 4061 */       String str4 = getLD_NDN(paramEntityItem2);
/* 4062 */       if (paramEntityItem2.getEntityType().equals(getEntityType()) && paramEntityItem2
/* 4063 */         .getEntityID() == getEntityID())
/*      */       {
/* 4065 */         str4 = "";
/*      */       }
/* 4067 */       this.args[0] = str3 + " " + str4;
/* 4068 */       this.args[1] = getLD_Value(paramEntityItem2, paramString1);
/* 4069 */       if (paramEntityItem3.getEntityType().equals(getEntityType()) && paramEntityItem3.getEntityID() == getEntityID()) {
/* 4070 */         this.args[2] = paramEntityItem3.getEntityGroup().getLongDescription();
/*      */       } else {
/* 4072 */         this.args[2] = getLD_NDN(paramEntityItem3);
/*      */       } 
/*      */       
/* 4075 */       this.args[3] = getLD_Value(paramEntityItem3, paramString2);
/* 4076 */       createMessage(paramInt, "CANNOT_BE_LATER_ERR", this.args);
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
/*      */   protected void checkCanNotBeEarlier(EntityItem paramEntityItem, String paramString1, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 4096 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem, paramString1, paramInt);
/* 4097 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem, paramString2, paramInt);
/* 4098 */     addDebug("checkCanNotBeEarlier " + paramEntityItem.getKey() + " " + paramString1 + ":" + str1 + " " + paramString2 + ":" + str2);
/* 4099 */     boolean bool = checkDates(str1, str2, 1);
/* 4100 */     if (!bool) {
/* 4101 */       String str = "";
/*      */ 
/*      */       
/* 4104 */       if (!paramEntityItem.getEntityType().equals(getEntityType()) && paramEntityItem
/* 4105 */         .getEntityID() != getEntityID())
/*      */       {
/* 4107 */         str = getLD_NDN(paramEntityItem) + " ";
/*      */       }
/* 4109 */       this.args[0] = str + getLD_Value(paramEntityItem, paramString1);
/* 4110 */       this.args[1] = getLD_Value(paramEntityItem, paramString2);
/* 4111 */       createMessage(paramInt, "CANNOT_BE_EARLIER_ERR", this.args);
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
/*      */   protected void checkCanNotBeEarlier(EntityItem paramEntityItem1, String paramString1, EntityItem paramEntityItem2, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 4130 */     checkCanNotBeEarlier((EntityItem)null, paramEntityItem1, paramString1, paramEntityItem2, paramString2, paramInt);
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
/*      */   protected void checkCanNotBeEarlier(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, EntityItem paramEntityItem3, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 4148 */     String str1 = getAttrValueAndCheckLvl(paramEntityItem2, paramString1, paramInt);
/* 4149 */     String str2 = getAttrValueAndCheckLvl(paramEntityItem3, paramString2, paramInt);
/* 4150 */     addDebug("checkCanNotBeEarlier " + ((paramEntityItem1 == null) ? "" : paramEntityItem1.getKey()) + " " + paramEntityItem2
/* 4151 */         .getKey() + " " + paramString1 + ":" + str1 + " " + paramEntityItem3.getKey() + " " + paramString2 + ":" + str2);
/*      */     
/* 4153 */     boolean bool = checkDates(str1, str2, 1);
/* 4154 */     if (bool) {
/* 4155 */       if (str1.length() > 0 && !Character.isDigit(str1.charAt(0))) {
/* 4156 */         bool = false;
/*      */       }
/* 4158 */       if (str2.length() > 0 && !Character.isDigit(str2.charAt(0))) {
/* 4159 */         bool = false;
/*      */       }
/*      */     } 
/* 4162 */     if (!bool) {
/*      */ 
/*      */       
/* 4165 */       String str3 = "";
/* 4166 */       if (paramEntityItem1 != null) {
/* 4167 */         str3 = getLD_NDN(paramEntityItem1) + " ";
/*      */       }
/*      */       
/* 4170 */       String str4 = getLD_NDN(paramEntityItem2);
/* 4171 */       if (paramEntityItem2.getEntityType().equals(getEntityType()) && paramEntityItem2
/* 4172 */         .getEntityID() == getEntityID())
/*      */       {
/* 4174 */         str4 = "";
/*      */       }
/* 4176 */       this.args[0] = str3 + " " + str4;
/* 4177 */       this.args[1] = getLD_Value(paramEntityItem2, paramString1);
/* 4178 */       if (paramEntityItem3.getEntityType().equals(getEntityType()) && paramEntityItem3.getEntityID() == getEntityID()) {
/* 4179 */         this.args[2] = paramEntityItem3.getEntityGroup().getLongDescription();
/*      */       } else {
/* 4181 */         this.args[2] = getLD_NDN(paramEntityItem3);
/*      */       } 
/* 4183 */       this.args[3] = getLD_Value(paramEntityItem3, paramString2);
/* 4184 */       createMessage(paramInt, "CANNOT_BE_EARLIER_ERR2", this.args);
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
/*      */   protected void checkWDAvails(EntityItem paramEntityItem, String paramString, int paramInt, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 4196 */     addDebug("checkWDAvails checking entity: " + paramEntityItem.getKey() + " for " + paramString);
/* 4197 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 4198 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 4199 */       if (entityItem.getEntityType().equals(paramString))
/*      */       {
/* 4201 */         for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 4202 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/* 4203 */           addDebug("checkWDAvails checking " + paramEntityItem.getKey() + ":" + entityItem.getKey() + ":" + entityItem1.getKey() + " for lastorder");
/* 4204 */           if (PokUtils.isSelected(entityItem1, "AVAILTYPE", "149")) {
/* 4205 */             String str = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "", false);
/* 4206 */             addDebug("checkWDAvails lastorder " + entityItem1.getKey() + " EFFECTIVEDATE: " + str);
/* 4207 */             if (str.length() > 0 && str.compareTo(getCurrentDate()) <= 0) {
/* 4208 */               if (paramBoolean) {
/*      */ 
/*      */                 
/* 4211 */                 this.args[0] = getLD_NDN(paramEntityItem);
/* 4212 */                 this.args[1] = getLD_NDN(entityItem1);
/* 4213 */                 createMessage(paramInt, "WITHDRAWN_ERR", this.args);
/*      */               }
/*      */               else {
/*      */                 
/* 4217 */                 this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/* 4218 */                 this.args[1] = getLD_NDN(entityItem1);
/* 4219 */                 createMessage(paramInt, "WITHDRAWN_ITEM_ERR", this.args);
/*      */               } 
/*      */             }
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
/*      */   protected boolean checkDates(String paramString1, String paramString2, int paramInt) throws SQLException, MiddlewareException {
/* 4238 */     boolean bool = true;
/*      */     
/* 4240 */     if (paramString1.length() > 0 && paramString2.length() > 0) {
/* 4241 */       int i = paramString1.compareTo(paramString2);
/* 4242 */       switch (paramInt) {
/*      */         case 1:
/* 4244 */           if (i < 0) {
/* 4245 */             bool = false;
/*      */           }
/*      */           break;
/*      */         case 2:
/* 4249 */           if (i > 0) {
/* 4250 */             bool = false;
/*      */           }
/*      */           break;
/*      */         case 3:
/* 4254 */           if (i <= 0) {
/* 4255 */             bool = false;
/*      */           }
/*      */           break;
/*      */         case 4:
/* 4259 */           if (i >= 0) {
/* 4260 */             bool = false;
/*      */           }
/*      */           break;
/*      */         case 5:
/* 4264 */           if (i != 0) {
/* 4265 */             bool = false;
/*      */           }
/*      */           break;
/*      */       } 
/*      */     } 
/* 4270 */     return bool;
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
/*      */   protected void checkWithdrawnDate(EntityItem paramEntityItem, String paramString, int paramInt, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 4288 */     String str = getAttrValueAndCheckLvl(paramEntityItem, paramString, paramInt);
/* 4289 */     addDebug("checkWithdrawnDate: " + paramEntityItem.getKey() + " " + paramString + " =" + str);
/* 4290 */     if (str.length() > 0 && 
/* 4291 */       str.compareTo(getCurrentDate()) < 0)
/*      */     {
/*      */       
/* 4294 */       if (paramBoolean) {
/* 4295 */         this.args[0] = getLD_NDN(paramEntityItem);
/* 4296 */         this.args[1] = getLD_Value(paramEntityItem, paramString);
/* 4297 */         createMessage(paramInt, "WITHDRAWN_ERR", this.args);
/*      */       }
/*      */       else {
/*      */         
/* 4301 */         this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/* 4302 */         this.args[1] = getLD_Value(paramEntityItem, paramString);
/* 4303 */         createMessage(paramInt, "WITHDRAWN_ITEM_ERR", this.args);
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
/*      */   protected void createMessage(int paramInt, String paramString, Object[] paramArrayOfObject) {
/* 4316 */     switch (paramInt) {
/*      */       case 1:
/*      */       case 2:
/* 4319 */         addWarning(paramString, paramArrayOfObject);
/*      */         break;
/*      */       case 3:
/*      */       case 4:
/* 4323 */         addError(paramString, paramArrayOfObject);
/*      */         break;
/*      */       case -1:
/* 4326 */         addDebug("createMessage: Bypassing msg output for " + paramString);
/*      */         break;
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
/*      */   protected void checkStatusVsDQ(EntityItem paramEntityItem1, String paramString, EntityItem paramEntityItem2, int paramInt) throws SQLException, MiddlewareException {
/* 4354 */     boolean bool = true;
/* 4355 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem1, paramString);
/*      */     
/* 4357 */     if (str1 == null) {
/* 4358 */       addDebug("checkStatusVsDQ " + paramEntityItem1.getKey() + " " + paramString + " is null, must get entityhistory and check date");
/* 4359 */       if (!wasUpdatedSinceDTS(paramEntityItem1, "2009-01-01-00.00.00.000000")) {
/* 4360 */         addDebug(paramEntityItem1.getKey() + " using Final for status because it was not updated after " + "2009-01-01-00.00.00.000000");
/* 4361 */         str1 = "0020";
/*      */       } else {
/* 4363 */         str1 = "0010";
/*      */       } 
/*      */     } 
/* 4366 */     String str2 = (String)STATUS_ORDER_TBL.get(str1);
/* 4367 */     String str3 = PokUtils.getAttributeFlagValue(paramEntityItem2, "DATAQUALITY");
/*      */     
/* 4369 */     if (str3 == null) {
/* 4370 */       addDebug("checkStatusVsDQ " + paramEntityItem2.getKey() + " DATAQUALITY is null, must get entityhistory and check date");
/* 4371 */       if (!wasUpdatedSinceDTS(paramEntityItem2, "2009-01-01-00.00.00.000000")) {
/* 4372 */         addDebug(paramEntityItem2.getKey() + " using Final for DATAQUALITY because it was not updated after " + "2009-01-01-00.00.00.000000");
/* 4373 */         str3 = "FINAL";
/*      */       } else {
/* 4375 */         str3 = "REVIEW";
/*      */       } 
/*      */     } 
/* 4378 */     String str4 = (String)STATUS_TBL.get(str3);
/* 4379 */     String str5 = (String)STATUS_ORDER_TBL.get(str4);
/*      */ 
/*      */     
/* 4382 */     bool = (str2.compareTo(str5) >= 0) ? true : false;
/* 4383 */     addDebug("checkStatusDQ " + paramEntityItem1.getKey() + " " + paramString + "[" + str1 + "] statusOrder:" + str2 + " " + paramEntityItem2
/* 4384 */         .getKey() + " DATAQUALITY[" + str3 + "] dqAsStatus " + str4 + " dqOrder:" + str5 + " isok " + bool);
/*      */     
/* 4386 */     if (!bool) {
/*      */ 
/*      */       
/* 4389 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), paramString, paramString);
/* 4390 */       this.args[1] = getLD_NDN(paramEntityItem1);
/* 4391 */       this.args[2] = getLD_Value(paramEntityItem1, paramString);
/* 4392 */       createMessage(paramInt, "STATUS_CHECK_ERR", this.args);
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
/*      */   protected boolean domainInRuleList(EntityItem paramEntityItem, String paramString) {
/* 4411 */     boolean bool = false;
/* 4412 */     String str = getDomainList(paramEntityItem, paramString);
/* 4413 */     if (str.trim().length() == 0) {
/*      */ 
/*      */       
/* 4416 */       bool = false;
/* 4417 */     } else if (str.equals("all")) {
/*      */ 
/*      */ 
/*      */       
/* 4421 */       bool = true;
/*      */     } else {
/* 4423 */       bool = domainInList(paramEntityItem, str);
/*      */     } 
/*      */     
/* 4426 */     if (!bool) {
/* 4427 */       addDebug("domainInRuleList: " + paramString + " checking is bypassed for " + paramEntityItem.getKey() + " " + 
/* 4428 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "[" + 
/* 4429 */           getAttributeFlagEnabledValue(paramEntityItem, "PDHDOMAIN") + "]");
/*      */     }
/* 4431 */     return bool;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean domainInList(EntityItem paramEntityItem, String paramString) {
/* 4436 */     HashSet<String> hashSet = new HashSet();
/* 4437 */     StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 4438 */     while (stringTokenizer.hasMoreTokens()) {
/* 4439 */       hashSet.add(stringTokenizer.nextToken());
/*      */     }
/* 4441 */     boolean bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 4442 */     hashSet.clear();
/* 4443 */     return bool;
/*      */   }
/*      */   
/*      */   protected String getDomainList(EntityItem paramEntityItem, String paramString) {
/* 4447 */     String str = "";
/* 4448 */     if (paramEntityItem.getEntityType().equals(getEntityType())) {
/*      */       
/* 4450 */       str = ABRServerProperties.getDomainList(this.m_abri.getABRCode(), "_" + paramString);
/*      */     } else {
/*      */       
/* 4453 */       String str1 = (String)ABR_ATTR_TBL.get(paramEntityItem.getEntityType());
/* 4454 */       if (str1 == null) {
/* 4455 */         addDebug("WARNING: cant find ABR attribute code for " + paramEntityItem.getEntityType());
/*      */         
/* 4457 */         str = ABRServerProperties.getDomainList(this.m_abri.getABRCode(), "_" + paramString);
/*      */       }
/*      */       else {
/*      */         
/* 4461 */         str = ABRServerProperties.getDomainList(str1, "_" + paramString);
/*      */       } 
/*      */     } 
/* 4464 */     addDebug("domainInRuleList: " + paramEntityItem.getKey() + " pdhdomain: " + 
/* 4465 */         PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN") + " list " + paramString + ": " + str);
/* 4466 */     return str;
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
/*      */   protected String getAttrValueAndCheckLvl(EntityItem paramEntityItem, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 4482 */     String str = PokUtils.getAttributeValue(paramEntityItem, paramString, ", ", "", false);
/*      */     
/* 4484 */     if (str.length() == 0 && (paramInt == 3 || paramInt == 4)) {
/* 4485 */       paramInt = overrideCheckLvl(paramEntityItem, paramInt);
/* 4486 */       if (paramInt != 3 && paramInt != 4) {
/* 4487 */         addDebug("OVERRODE checklevel for " + paramString + " because " + paramEntityItem
/* 4488 */             .getKey() + " was not FINAL and updated after " + "2009-01-01-00.00.00.000000");
/*      */       }
/*      */     } 
/* 4491 */     if (str.length() == 0 && (paramInt == 3 || paramInt == 2)) {
/*      */ 
/*      */       
/* 4494 */       this.args[0] = getLD_NDN(paramEntityItem);
/* 4495 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString);
/* 4496 */       createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/*      */     } 
/* 4498 */     return str;
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
/*      */   private int overrideCheckLvl(EntityItem paramEntityItem, int paramInt) throws SQLException, MiddlewareException {
/* 4515 */     if (!paramEntityItem.getEntityType().equals(getEntityType()) && paramEntityItem.getEntityID() != getEntityID()) {
/*      */       
/* 4517 */       String str1 = (String)STATUS_ATTR_TBL.get(paramEntityItem.getEntityType());
/* 4518 */       if (str1 == null) {
/* 4519 */         str1 = "STATUS";
/*      */       }
/* 4521 */       String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, str1);
/* 4522 */       if (!"0020".equals(str2) || !wasUpdatedSinceDTS(paramEntityItem, "2009-01-01-00.00.00.000000"))
/*      */       {
/*      */         
/* 4525 */         paramInt = 1;
/*      */       }
/*      */     } 
/* 4528 */     return paramInt;
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
/*      */   protected EANAttribute getAttrAndCheckLvl(EntityItem paramEntityItem, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 4542 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString);
/*      */     
/* 4544 */     if ((eANAttribute == null || eANAttribute.toString().length() == 0) && (paramInt == 3 || paramInt == 2)) {
/*      */ 
/*      */ 
/*      */       
/* 4548 */       this.args[0] = getLD_NDN(paramEntityItem);
/* 4549 */       this.args[1] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString);
/* 4550 */       createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/*      */     } 
/* 4552 */     return eANAttribute;
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
/*      */   protected ArrayList getCountriesAsList(Vector<EntityItem> paramVector, int paramInt) throws SQLException, MiddlewareException {
/* 4565 */     ArrayList arrayList = new ArrayList();
/*      */     
/* 4567 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 4568 */       EntityItem entityItem = paramVector.elementAt(b);
/* 4569 */       getCountriesAsList(entityItem, arrayList, paramInt);
/*      */     } 
/*      */     
/* 4572 */     return arrayList;
/*      */   }
/*      */   
/*      */   protected ArrayList getCountriesAsList(EntityGroup paramEntityGroup, int paramInt) throws SQLException, MiddlewareException {
/* 4576 */     ArrayList arrayList = new ArrayList();
/*      */     
/* 4578 */     for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 4579 */       EntityItem entityItem = paramEntityGroup.getEntityItem(b);
/* 4580 */       getCountriesAsList(entityItem, arrayList, paramInt);
/*      */     } 
/*      */     
/* 4583 */     return arrayList;
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
/*      */   protected void getCountriesAsList(EntityItem paramEntityItem, ArrayList paramArrayList, int paramInt) throws SQLException, MiddlewareException {
/* 4596 */     getAttributeAsList(paramEntityItem, paramArrayList, "COUNTRYLIST", paramInt);
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
/*      */   protected ArrayList getAttributeAsList(Vector<EntityItem> paramVector, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 4610 */     ArrayList arrayList = new ArrayList();
/*      */     
/* 4612 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 4613 */       EntityItem entityItem = paramVector.elementAt(b);
/* 4614 */       getAttributeAsList(entityItem, arrayList, paramString, paramInt);
/*      */     } 
/*      */     
/* 4617 */     return arrayList;
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
/*      */   protected void getAttributeAsList(EntityItem paramEntityItem, ArrayList<String> paramArrayList, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 4630 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)getAttrAndCheckLvl(paramEntityItem, paramString, paramInt);
/* 4631 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 4633 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 4634 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 4636 */         if (arrayOfMetaFlag[b].isSelected() && !paramArrayList.contains(arrayOfMetaFlag[b].getFlagCode())) {
/* 4637 */           paramArrayList.add(arrayOfMetaFlag[b].getFlagCode());
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
/*      */   protected boolean hasAnyCountryMatch(EntityItem paramEntityItem, ArrayList paramArrayList) {
/* 4650 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute("COUNTRYLIST");
/* 4651 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 4653 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 4654 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 4656 */         if (arrayOfMetaFlag[b].isSelected() && paramArrayList.contains(arrayOfMetaFlag[b].getFlagCode())) {
/* 4657 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/* 4661 */     return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkLseoPSLOAvail(EntityItem paramEntityItem, String paramString1, String paramString2, Vector<EntityItem> paramVector, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 4750 */     addDebug("checkLseoPSLOAvail: " + paramEntityItem.getKey());
/* 4751 */     if (paramVector.size() > 0) {
/* 4752 */       Vector vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString2, "AVAIL");
/* 4753 */       Vector<EntityItem> vector1 = PokUtils.getEntitiesWithMatchedAttr(vector, "AVAILTYPE", "149");
/* 4754 */       Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, "CATLGOR");
/* 4755 */       addDebug("checkLseoPSLOAvail: availvct " + vector.size() + " loavailvct " + vector1
/* 4756 */           .size() + " catlgorVct " + vector2.size());
/* 4757 */       if (vector2.size() > 0) {
/* 4758 */         Vector<EntityItem> vector3 = new Vector();
/* 4759 */         for (byte b = 0; b < vector2.size(); b++) {
/* 4760 */           EntityItem entityItem = vector2.elementAt(b);
/* 4761 */           String str = PokUtils.getAttributeValue(entityItem, "PUBTO", "", null, false);
/* 4762 */           if (str != null) {
/* 4763 */             vector3.add(entityItem);
/*      */           }
/*      */         } 
/* 4766 */         vector2.clear();
/* 4767 */         vector2 = vector3;
/* 4768 */         addDebug("checkLseoPSLOAvail: catlgorVct with pubto values: " + vector2.size());
/*      */       } 
/*      */       
/* 4771 */       if (vector1.size() > 0) {
/* 4772 */         Vector<String> vector3 = new Vector();
/* 4773 */         for (byte b = 0; b < vector1.size(); b++) {
/* 4774 */           EntityItem entityItem = vector1.elementAt(b);
/* 4775 */           String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false); byte b1;
/* 4776 */           label59: for (b1 = 0; b1 < paramVector.size(); b1++) {
/* 4777 */             EntityItem entityItem1 = paramVector.elementAt(b1);
/* 4778 */             String str1 = PokUtils.getAttributeValue(entityItem1, "LSEOUNPUBDATEMTRGT", ", ", "", false);
/*      */             
/* 4780 */             ArrayList arrayList = new ArrayList();
/* 4781 */             getCountriesAsList(entityItem1, arrayList, 4);
/* 4782 */             addDebug("checkLseoPSLOAvail " + entityItem.getKey() + " " + entityItem1.getKey() + " lseoCtry " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 4788 */             if (hasAnyCountryMatch(entityItem, arrayList)) {
/* 4789 */               addDebug("checkLseoPSLOAvail lseo and avail ctryexist LSEOUNPUBDATEMTRGT: " + str1 + " EFFECTIVEDATE: " + str);
/* 4790 */               ArrayList arrayList1 = new ArrayList();
/* 4791 */               getCountriesAsList(entityItem, arrayList1, 4);
/* 4792 */               addDebug("checkLseoPSLOAvail availCtry " + arrayList1);
/*      */ 
/*      */               
/* 4795 */               for (byte b2 = 0; b2 < vector2.size(); b2++) {
/* 4796 */                 EntityItem entityItem2 = vector2.elementAt(b2);
/* 4797 */                 String str2 = PokUtils.getAttributeValue(entityItem2, "PUBTO", "", "", false);
/* 4798 */                 String str3 = PokUtils.getAttributeFlagValue(entityItem2, "OFFCOUNTRY");
/* 4799 */                 addDebug("checkLseoPSLOAvail " + entityItem2.getKey() + " pubto " + str2 + " offctry " + str3);
/*      */ 
/*      */                 
/* 4802 */                 if (str.compareTo(str2) < 0) {
/*      */ 
/*      */                   
/* 4805 */                   if (arrayList1.contains(str3)) {
/*      */ 
/*      */                     
/* 4808 */                     if (str1.length() > 0) { if (str2.compareTo(str1) < 0) {
/* 4809 */                         if (!vector3.contains(paramEntityItem.getKey() + entityItem.getKey())) {
/* 4810 */                           vector3.add(paramEntityItem.getKey() + entityItem.getKey());
/*      */ 
/*      */ 
/*      */ 
/*      */                           
/* 4815 */                           this.args[0] = getLD_Value(entityItem1, "LSEOUNPUBDATEMTRGT");
/* 4816 */                           this.args[1] = getLD_NDN(paramEntityItem);
/* 4817 */                           this.args[2] = getLD_NDN(entityItem2) + " " + getLD_Value(entityItem2, "PUBTO");
/* 4818 */                           createMessage(paramInt2, "CANNOT_BE_LATER_ERR1", this.args); continue label59;
/*      */                         }  continue label59;
/*      */                       }  continue label59; }
/*      */                      continue label59;
/*      */                   } 
/* 4823 */                   addDebug("checkLseoPSLOAvail offctry not in availctry");
/*      */                 } 
/*      */               } 
/*      */ 
/*      */               
/* 4828 */               addDebug("checkLseoPSLOAvail chk lseodate vs pslo effdate");
/*      */ 
/*      */               
/* 4831 */               if (str1.length() == 0) {
/* 4832 */                 addDebug("checkLseoPSLOAvail " + entityItem1.getKey() + " LSEOUNPUBDATEMTRGT not populated");
/*      */                 
/* 4834 */                 str1 = "9999-12-31";
/* 4835 */                 addDebug("checkLseoPSLOAvail " + entityItem1.getKey() + " LSEOUNPUBDATEMTRGT was set to " + str1);
/*      */               } 
/*      */               
/* 4838 */               if (str1.length() > 0 && str.compareTo(str1) < 0 && 
/* 4839 */                 !vector3.contains(paramEntityItem.getKey() + entityItem.getKey())) {
/* 4840 */                 vector3.add(paramEntityItem.getKey() + entityItem.getKey());
/*      */ 
/*      */ 
/*      */                 
/* 4844 */                 this.args[0] = getLD_Value(entityItem1, "LSEOUNPUBDATEMTRGT");
/* 4845 */                 this.args[1] = getLD_NDN(paramEntityItem);
/* 4846 */                 this.args[2] = getLD_NDN(entityItem) + " " + getLD_Value(entityItem, "EFFECTIVEDATE");
/* 4847 */                 createMessage(paramInt1, "CANNOT_BE_LATER_ERR1", this.args);
/*      */               
/*      */               }
/*      */ 
/*      */             
/*      */             }
/*      */             else {
/*      */ 
/*      */               
/* 4856 */               addDebug("checkLseoPSLOAvail no country match");
/*      */             } 
/* 4858 */             arrayList.clear();
/*      */           } 
/*      */         } 
/* 4861 */         vector1.clear();
/* 4862 */         vector3.clear();
/*      */       } 
/* 4864 */       vector.clear();
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
/*      */   protected String getLD_NDN(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 4879 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLD_Value(EntityItem paramEntityItem, String paramString) {
/* 4888 */     return getLD_Value(paramEntityItem, paramString, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLD_Value(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 4898 */     if (paramString2 == null) {
/* 4899 */       paramString2 = PokUtils.getAttributeValue(paramEntityItem, paramString1, ",", "", false);
/*      */     }
/* 4901 */     return PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString1, paramString1) + ": " + paramString2;
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
/*      */   protected EntityItem getAvailPS(EntityItem paramEntityItem, String paramString) {
/* 4914 */     EANEntity eANEntity = null;
/* 4915 */     for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 4916 */       EANEntity eANEntity1 = paramEntityItem.getUpLink(b);
/* 4917 */       if (eANEntity1.getEntityType().equals(paramString)) {
/* 4918 */         eANEntity = eANEntity1.getUpLink(0);
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/* 4923 */     return (EntityItem)eANEntity;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doLSEOSectionTwo(EntityItem[] paramArrayOfEntityItem, String paramString) throws MiddlewareRequestException, SQLException, MiddlewareException {
/* 5042 */     if (paramArrayOfEntityItem == null || paramArrayOfEntityItem.length == 0) {
/* 5043 */       addDebug("doLSEOSectionTwo: entered with no LSEOs");
/*      */       
/*      */       return;
/*      */     } 
/* 5047 */     for (byte b1 = 0; b1 < paramArrayOfEntityItem.length; b1++) {
/* 5048 */       EntityItem entityItem = paramArrayOfEntityItem[b1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5059 */       if (statusIsFinal(entityItem)) {
/*      */         
/* 5061 */         setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", getQueuedValueForItem(entityItem, "EPIMSABRSTATUS"), entityItem);
/*      */         
/* 5063 */         if ("11457".equals(paramString)) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 5068 */           Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "LSEOAVAIL", "AVAIL");
/* 5069 */           for (byte b = 0; b < vector.size(); b++) {
/* 5070 */             EntityItem entityItem1 = vector.elementAt(b);
/* 5071 */             String str2 = PokUtils.getAttributeFlagValue(entityItem1, "AVAILTYPE");
/* 5072 */             addDebug("doLSEOSectionTwo: lseo.status=final specbid=no annchk " + entityItem1.getKey() + " type " + str2);
/*      */ 
/*      */             
/* 5075 */             Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem1, "AVAILANNA", "ANNOUNCEMENT");
/* 5076 */             for (byte b4 = 0; b4 < vector1.size(); b4++) {
/* 5077 */               EntityItem entityItem2 = vector1.elementAt(b4);
/* 5078 */               String str3 = getAttributeFlagEnabledValue(entityItem2, "ANNTYPE");
/* 5079 */               addDebug("doLSEOSectionTwo: " + entityItem2.getKey() + " type " + str3);
/*      */ 
/*      */ 
/*      */               
/* 5083 */               if (statusIsFinal(entityItem2, "ANNSTATUS")) {
/* 5084 */                 if ("19".equals(str3)) {
/* 5085 */                   addDebug("doLSEOSectionTwo: annche " + entityItem2.getKey() + " is Final and New");
/*      */                   
/* 5087 */                   setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValueForItem(entityItem2, "WWPRTABRSTATUS"), entityItem2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/* 5093 */                   if ("146".equals(str2))
/*      */                   {
/* 5095 */                     setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(entityItem2, "QSMRPTABRSTATUS"), entityItem2);
/*      */                   }
/*      */                 } 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 5102 */                 if ("14".equals(str3)) {
/* 5103 */                   addDebug("doLSEOSectionTwo: " + entityItem2.getKey() + " is Final and EOL");
/* 5104 */                   if ("149".equals(str2))
/*      */                   {
/* 5106 */                     setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(entityItem2, "QSMRPTABRSTATUS"), entityItem2);
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/* 5112 */             vector1.clear();
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 5117 */           String str1 = PokUtils.getAttributeValue(entityItem, "LSEOPUBDATEMTRGT", "", null, false);
/* 5118 */           addDebug("doLSEOSectionTwo: " + entityItem.getKey() + " lseopubdate " + str1);
/* 5119 */           if (str1 == null || str1.compareTo("2010-03-01") > 0) {
/* 5120 */             for (byte b4 = 0; b4 < vector.size(); b4++) {
/* 5121 */               EntityItem entityItem1 = vector.elementAt(b4);
/* 5122 */               String str2 = PokUtils.getAttributeFlagValue(entityItem1, "AVAILANNTYPE");
/* 5123 */               if (str2 == null) {
/* 5124 */                 str2 = "RFA";
/*      */               }
/* 5126 */               addDebug("doLSEOSectionTwo: lseo.status=final specbid=no " + entityItem1.getKey() + " availanntype " + str2);
/*      */ 
/*      */               
/* 5129 */               if (statusIsFinal(entityItem1)) {
/*      */                 
/* 5131 */                 setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } else {
/* 5159 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } else {
/* 5165 */           addDebug("doLSEOSectionTwo: lseo.status=final specbid=yes");
/*      */           
/* 5167 */           setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValueForItem(entityItem, "WWPRTABRSTATUS"), entityItem);
/*      */           
/* 5169 */           setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(entityItem, "QSMRPTABRSTATUS"), entityItem);
/*      */           
/* 5171 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 5176 */       if (statusIsRFR(entityItem))
/*      */       {
/*      */         
/* 5179 */         if ("11457".equals(paramString)) {
/*      */           
/* 5181 */           doLSEOSect2R4R_R10Processing(entityItem, "LSEOAVAIL");
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 5187 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(entityItem, "ADSABRSTATUS"), entityItem);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5198 */     String str = "EXRPT3LSEODQ";
/* 5199 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramArrayOfEntityItem.length];
/* 5200 */     for (byte b2 = 0; b2 < paramArrayOfEntityItem.length; b2++) {
/* 5201 */       arrayOfEntityItem[b2] = new EntityItem(null, this.m_elist.getProfile(), "LSEO", paramArrayOfEntityItem[b2].getEntityID());
/*      */     }
/* 5203 */     this.otherList = this.m_db.getEntityList(this.m_elist.getProfile(), new ExtractActionItem(null, this.m_db, this.m_elist
/* 5204 */           .getProfile(), str), arrayOfEntityItem);
/*      */     
/* 5206 */     addDebug("doLSEOSectionTwo2: Extract " + str + NEWLINE + PokUtils.outputList(this.otherList));
/*      */     
/* 5208 */     EntityGroup entityGroup = this.otherList.getParentEntityGroup();
/* 5209 */     for (byte b3 = 0; b3 < entityGroup.getEntityItemCount(); b3++) {
/* 5210 */       EntityItem entityItem = entityGroup.getEntityItem(b3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5218 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "LSEOBUNDLELSEO", "LSEOBUNDLE");
/*      */       
/* 5220 */       for (byte b = 0; b < vector.size(); b++) {
/* 5221 */         EntityItem entityItem1 = vector.elementAt(b);
/* 5222 */         String str1 = getAttributeFlagEnabledValue(entityItem1, "SPECBID");
/*      */         
/* 5224 */         addDebug("doLSEOSectionTwo2: " + entityItem.getKey() + " " + entityItem1.getKey() + " bdl.SPECBID: " + str1);
/*      */ 
/*      */         
/* 5227 */         if (statusIsFinal(entityItem)) {
/*      */           
/* 5229 */           if (statusIsFinal(entityItem1))
/*      */           {
/* 5231 */             if ("11457".equals(str1)) {
/* 5232 */               Vector vector1 = PokUtils.getAllLinkedEntities(entityItem1, "LSEOBUNDLEAVAIL", "AVAIL");
/* 5233 */               Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "STATUS", "0020");
/*      */ 
/*      */               
/* 5236 */               addDebug("doLSEOSectionTwo2: " + entityItem1.getKey() + " availVct " + vector1.size() + " finalAvailVct " + vector2
/* 5237 */                   .size());
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5242 */               if (vector2.size() > 0)
/*      */               {
/* 5244 */                 doLSEOBDLSectionOne(entityItem1);
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5265 */               vector1.clear();
/* 5266 */               vector2.clear();
/*      */             }
/*      */             else {
/*      */               
/* 5270 */               doLSEOBDLSectionOne(entityItem1);
/*      */             } 
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 5277 */           doR10BundleChecks(entityItem1, str1);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 5286 */         if (statusIsRFR(entityItem))
/*      */         {
/*      */ 
/*      */           
/* 5290 */           doR10RFRBundleChecks(entityItem1, str1);
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 5295 */       vector.clear();
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
/*      */   private void doR10BundleChecks(EntityItem paramEntityItem, String paramString) {
/* 5324 */     if (!doR10processing()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5338 */     if (statusIsRFR(paramEntityItem))
/*      */     {
/* 5340 */       if ("11457".equals(paramString)) {
/* 5341 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLEAVAIL", "AVAIL");
/* 5342 */         for (byte b = 0; b < vector.size(); b++) {
/* 5343 */           EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */           
/* 5346 */           if (statusIsRFRorFinal(entityItem)) {
/*      */             
/* 5348 */             doLSEOBDLSectionOne(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 5380 */         doLSEOBDLSectionOne(paramEntityItem);
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
/*      */   private void doLSEOSect2R4R_R10Processing(EntityItem paramEntityItem, String paramString) {
/* 5415 */     String str = PokUtils.getAttributeValue(paramEntityItem, "LSEOPUBDATEMTRGT", "", null, false);
/* 5416 */     addDebug("doLSEOSect2R4R_R10Processing: " + paramEntityItem.getKey() + " lseopubdate " + str);
/*      */     
/* 5418 */     if (str == null || str.compareTo("2010-03-01") > 0) {
/* 5419 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString, "AVAIL");
/* 5420 */       for (byte b = 0; b < vector.size(); b++) {
/* 5421 */         EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */         
/* 5424 */         if (statusIsRFRorFinal(entityItem)) {
/*      */ 
/*      */           
/* 5427 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 5457 */       vector.clear();
/*      */     }
/*      */     else {
/*      */       
/* 5461 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
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
/*      */   private void doR10RFRBundleChecks(EntityItem paramEntityItem, String paramString) {
/* 5496 */     if (!doR10processing()) {
/*      */       return;
/*      */     }
/*      */     
/* 5500 */     addDebug("doR10RFRBundleChecks: " + paramEntityItem.getKey() + " bdlspecbid " + paramString);
/* 5501 */     if (statusIsRFR(paramEntityItem))
/*      */     {
/*      */       
/* 5504 */       if ("11457".equals(paramString)) {
/* 5505 */         Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLEAVAIL", "AVAIL");
/* 5506 */         for (byte b = 0; b < vector.size(); b++) {
/* 5507 */           EntityItem entityItem = vector.elementAt(b);
/*      */           
/* 5509 */           if (statusIsRFRorFinal(entityItem)) {
/*      */             
/* 5511 */             doLSEOBDLSectionOne(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } else {
/* 5544 */         doLSEOBDLSectionOne(paramEntityItem);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getQueuedValueForItem(EntityItem paramEntityItem, String paramString) {
/* 5552 */     if (paramEntityItem.getEntityType().equals(getEntityType())) {
/* 5553 */       return getQueuedValue(paramString);
/*      */     }
/*      */     
/* 5556 */     String str1 = (String)ABR_ATTR_TBL.get(paramEntityItem.getEntityType());
/* 5557 */     if (str1 == null) {
/* 5558 */       addDebug("WARNING: cant find ABR attribute code for " + paramEntityItem.getEntityType());
/* 5559 */       return getQueuedValue(paramString);
/*      */     } 
/*      */     
/* 5562 */     String str2 = "_queuedValue";
/* 5563 */     return ABRServerProperties.getValue(str1, "_" + paramString + str2, "0020");
/*      */   }
/*      */   
/*      */   protected String getRFRQueuedValueForItem(EntityItem paramEntityItem, String paramString) {
/* 5567 */     if (paramEntityItem.getEntityType().equals(getEntityType())) {
/* 5568 */       return getRFRQueuedValue(paramString);
/*      */     }
/*      */     
/* 5571 */     String str1 = (String)ABR_ATTR_TBL.get(paramEntityItem.getEntityType());
/* 5572 */     if (str1 == null) {
/* 5573 */       addDebug("WARNING: cant find ABR attribute code for " + paramEntityItem.getEntityType());
/* 5574 */       return getRFRQueuedValue(paramString);
/*      */     } 
/* 5576 */     String str2 = "_RFRqueuedValue";
/* 5577 */     return ABRServerProperties.getValue(str1, "_" + paramString + str2, "0020");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getRFRFIRSTQueuedValueForItem(EntityItem paramEntityItem, String paramString) {
/* 5586 */     String str1 = (String)ABR_ATTR_TBL.get(paramEntityItem.getEntityType());
/* 5587 */     if (str1 == null) {
/* 5588 */       addDebug("WARNING: cant find ABR attribute code for " + paramEntityItem.getEntityType());
/* 5589 */       return getRFRQueuedValue(paramString);
/*      */     } 
/* 5591 */     String str2 = "_RFRFIRSTqueuedValue";
/*      */ 
/*      */     
/* 5594 */     return ABRServerProperties.getValue(str1, "_" + paramString + str2, 
/* 5595 */         getRFRQueuedValueForItem(paramEntityItem, paramString));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getFINALFIRSTQueuedValueForItem(EntityItem paramEntityItem, String paramString) {
/* 5604 */     String str1 = (String)ABR_ATTR_TBL.get(paramEntityItem.getEntityType());
/* 5605 */     if (str1 == null) {
/* 5606 */       addDebug("WARNING: cant find ABR attribute code for " + paramEntityItem.getEntityType());
/* 5607 */       return getRFRQueuedValue(paramString);
/*      */     } 
/* 5609 */     String str2 = "_FINALFIRSTqueuedValue";
/* 5610 */     return ABRServerProperties.getValue(str1, "_" + paramString + str2, 
/* 5611 */         getQueuedValueForItem(paramEntityItem, paramString));
/*      */   }
/*      */   
/*      */   protected boolean statusIsRFRorFinal(EntityItem paramEntityItem) {
/* 5615 */     return statusIsRFRorFinal(paramEntityItem, "STATUS");
/*      */   }
/*      */   protected boolean statusIsRFRorFinal(EntityItem paramEntityItem, String paramString) {
/* 5618 */     String str = getAttributeFlagEnabledValue(paramEntityItem, paramString);
/*      */     
/* 5620 */     boolean bool = (paramEntityItem.getEntityType().equals(getEntityType()) && paramEntityItem.getEntityID() == getEntityID()) ? true : false;
/* 5621 */     boolean bool1 = false;
/* 5622 */     boolean bool2 = false;
/* 5623 */     if (bool) {
/* 5624 */       bool1 = "FINAL".equals(getAttributeFlagEnabledValue(paramEntityItem, "DATAQUALITY"));
/* 5625 */       bool2 = "REVIEW".equals(getAttributeFlagEnabledValue(paramEntityItem, "DATAQUALITY"));
/*      */     } 
/*      */     
/* 5628 */     addDebug("statusIsRFRorFinal: " + paramEntityItem.getKey() + " status " + str + " isRoot " + bool + (bool ? (" isRootRFR " + bool2 + " isRootFinal " + bool1) : ""));
/*      */     
/* 5630 */     if (str == null || str.length() == 0) {
/* 5631 */       str = "0020";
/*      */     }
/*      */     
/* 5634 */     return (bool1 || bool2 || "0040".equals(str) || "0020".equals(str));
/*      */   }
/*      */   protected boolean statusIsRFR(EntityItem paramEntityItem) {
/* 5637 */     return statusIsRFR(paramEntityItem, "STATUS");
/*      */   }
/*      */   protected boolean statusIsRFR(EntityItem paramEntityItem, String paramString) {
/* 5640 */     String str = getAttributeFlagEnabledValue(paramEntityItem, paramString);
/*      */     
/* 5642 */     boolean bool = (paramEntityItem.getEntityType().equals(getEntityType()) && paramEntityItem.getEntityID() == getEntityID()) ? true : false;
/* 5643 */     boolean bool1 = false;
/* 5644 */     if (bool) {
/* 5645 */       bool1 = "REVIEW".equals(getAttributeFlagEnabledValue(paramEntityItem, "DATAQUALITY"));
/*      */     }
/* 5647 */     addDebug("statusIsRFR: " + paramEntityItem.getKey() + " status " + str + " isRoot " + bool + (bool ? (" isRootRFR " + bool1) : ""));
/*      */ 
/*      */     
/* 5650 */     return (bool1 || "0040".equals(str));
/*      */   }
/*      */   protected boolean statusIsFinal(EntityItem paramEntityItem) {
/* 5653 */     return statusIsFinal(paramEntityItem, "STATUS");
/*      */   }
/*      */   protected boolean statusIsFinal(EntityItem paramEntityItem, String paramString) {
/* 5656 */     String str = getAttributeFlagEnabledValue(paramEntityItem, paramString);
/*      */     
/* 5658 */     boolean bool = (paramEntityItem.getEntityType().equals(getEntityType()) && paramEntityItem.getEntityID() == getEntityID()) ? true : false;
/* 5659 */     boolean bool1 = false;
/* 5660 */     if (bool) {
/* 5661 */       bool1 = "FINAL".equals(getAttributeFlagEnabledValue(paramEntityItem, "DATAQUALITY"));
/*      */     }
/* 5663 */     addDebug("statusIsFinal: " + paramEntityItem.getKey() + " status " + str + " isRoot " + bool + (bool ? (" isRootFinal " + bool1) : ""));
/*      */     
/* 5665 */     if (str == null || str.length() == 0) {
/* 5666 */       str = "0020";
/*      */     }
/*      */     
/* 5669 */     return (bool1 || "0020".equals(str));
/*      */   }
/*      */   protected boolean doR10processing() {
/* 5672 */     String str = ABRServerProperties.getValue("BH", "_R10", "false");
/*      */     
/* 5674 */     addDebug("doR10processing " + str);
/*      */     
/* 5676 */     return str.equalsIgnoreCase("true");
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
/*      */   protected void doR4R_RFAProcessing(String paramString) {
/* 5713 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5733 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, paramString, "AVAIL");
/* 5734 */     for (byte b = 0; b < vector.size(); b++) {
/* 5735 */       EntityItem entityItem1 = vector.elementAt(b);
/* 5736 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "AVAILANNTYPE");
/* 5737 */       if (str == null) {
/* 5738 */         str = "RFA";
/*      */       }
/* 5740 */       addDebug("doR4R_RFAProcessing: " + entityItem1.getKey() + " availAnntypeFlag " + str);
/*      */       
/* 5742 */       if (statusIsRFRorFinal(entityItem1)) {
/*      */         
/* 5744 */         doRFR_MODADSXML(entityItem);
/*      */ 
/*      */         
/* 5747 */         addDebug("doR4R_RFAProcessing: " + entityItem.getKey() + " ADSABRSTATUS is queued");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5778 */     vector.clear();
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
/*      */   protected void doR4R_R10Processing(EntityItem paramEntityItem, String paramString) {
/* 5817 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 5824 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString, "AVAIL");
/* 5825 */     for (byte b = 0; b < vector.size(); b++) {
/* 5826 */       EntityItem entityItem = vector.elementAt(b);
/*      */ 
/*      */       
/* 5829 */       if (statusIsRFRorFinal(entityItem)) {
/*      */         
/* 5831 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */         break;
/*      */       } 
/*      */     } 
/* 5835 */     vector.clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doLSEOBDLSectionOne(EntityItem paramEntityItem) {
/* 5920 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "SPECBID");
/*      */     
/* 5922 */     addDebug("doLSEOBDLSectionOne: " + paramEntityItem.getKey() + " SPECBID: " + str);
/*      */ 
/*      */     
/* 5925 */     if (statusIsFinal(paramEntityItem)) {
/*      */       
/* 5927 */       setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getQueuedValueForItem(paramEntityItem, "COMPATGENABR"), paramEntityItem);
/*      */       
/* 5929 */       setFlagValue(this.m_elist.getProfile(), "EPIMSABRSTATUS", getQueuedValueForItem(paramEntityItem, "EPIMSABRSTATUS"), paramEntityItem);
/*      */     }
/* 5931 */     else if (statusIsRFR(paramEntityItem)) {
/*      */       
/* 5933 */       setFlagValue(this.m_elist.getProfile(), "COMPATGENABR", getRFRQueuedValueForItem(paramEntityItem, "COMPATGENABR"), paramEntityItem);
/*      */     } 
/*      */ 
/*      */     
/* 5937 */     if ("11457".equals(str)) {
/* 5938 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, "LSEOBUNDLEAVAIL", "AVAIL");
/*      */       
/* 5940 */       if (statusIsFinal(paramEntityItem)) {
/* 5941 */         for (byte b = 0; b < vector.size(); b++) {
/* 5942 */           EntityItem entityItem = vector.elementAt(b);
/* 5943 */           String str2 = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/*      */           
/* 5945 */           addDebug("doLSEOBDLSectionOne: specbid=no annchks " + entityItem.getKey() + " type: " + str2);
/* 5946 */           if (statusIsFinal(entityItem)) {
/* 5947 */             Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "AVAILANNA", "ANNOUNCEMENT");
/* 5948 */             Vector vector2 = PokUtils.getEntitiesWithMatchedAttr(vector1, "ANNSTATUS", "0020");
/* 5949 */             addDebug("doLSEOBDLSectionOne: annchks lseoannVct " + vector1.size() + " lseoannFinalVct " + vector2.size());
/* 5950 */             for (byte b1 = 0; b1 < vector2.size(); b1++) {
/* 5951 */               EntityItem entityItem1 = vector1.elementAt(b1);
/* 5952 */               String str3 = getAttributeFlagEnabledValue(entityItem1, "ANNTYPE");
/* 5953 */               addDebug("doLSEOBDLSectionOne: annchks final " + entityItem1.getKey() + " type " + str3);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5958 */               if ("19".equals(str3)) {
/* 5959 */                 addDebug("doLSEOBDLSectionOne: annchks " + entityItem1.getKey() + " is Final and New");
/*      */                 
/* 5961 */                 setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValueForItem(entityItem1, "WWPRTABRSTATUS"), entityItem1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 5968 */                 if ("146".equals(str2))
/*      */                 {
/* 5970 */                   setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(entityItem1, "QSMRPTABRSTATUS"), entityItem1);
/*      */                 }
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 5979 */               if ("14".equals(str3)) {
/* 5980 */                 addDebug("doLSEOBDLSectionOne: annchks " + entityItem1.getKey() + " is Final and EOL");
/* 5981 */                 if ("149".equals(str2))
/*      */                 {
/* 5983 */                   setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(entityItem1, "QSMRPTABRSTATUS"), entityItem1);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */             
/* 5988 */             vector2.clear();
/* 5989 */             vector1.clear();
/*      */           } 
/*      */         } 
/*      */       }
/*      */       
/* 5994 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "BUNDLPUBDATEMTRGT", "", null, false);
/* 5995 */       addDebug("doLSEOBDLSectionOne: bdlchks " + paramEntityItem.getKey() + " bdlpubdate " + str1);
/*      */ 
/*      */       
/* 5998 */       if (str1 == null || str1.compareTo("2010-03-01") > 0) {
/* 5999 */         for (byte b = 0; b < vector.size(); b++) {
/* 6000 */           EntityItem entityItem = vector.elementAt(b);
/* 6001 */           String str2 = PokUtils.getAttributeFlagValue(entityItem, "AVAILTYPE");
/* 6002 */           String str3 = PokUtils.getAttributeFlagValue(entityItem, "AVAILANNTYPE");
/* 6003 */           if (str3 == null) {
/* 6004 */             str3 = "RFA";
/*      */           }
/*      */           
/* 6007 */           addDebug("doLSEOBDLSectionOne: specbid=no bdlchks " + entityItem.getKey() + " type: " + str2 + " availAnntypeFlag " + str3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 6015 */           if (statusIsFinal(paramEntityItem) && statusIsFinal(entityItem)) {
/*      */             
/* 6017 */             setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", 
/* 6018 */                 getQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */ 
/*      */           
/* 6025 */           if (statusIsRFR(paramEntityItem) && statusIsRFRorFinal(entityItem)) {
/*      */             
/* 6027 */             doRFR_ADSXML(paramEntityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6071 */         vector.clear();
/*      */       } else {
/* 6073 */         addDebug("doLSEOBDLSectionOne: " + paramEntityItem.getKey() + " is olddata " + str1);
/*      */         
/* 6075 */         if (statusIsRFR(paramEntityItem))
/*      */         {
/*      */           
/* 6078 */           doRFR_ADSXML(paramEntityItem);
/*      */         }
/*      */ 
/*      */         
/* 6082 */         if (statusIsFinal(paramEntityItem))
/*      */         {
/*      */           
/* 6085 */           setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", 
/* 6086 */               getQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */         }
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 6092 */       setFlagValue(this.m_elist.getProfile(), "WWPRTABRSTATUS", getQueuedValueForItem(paramEntityItem, "WWPRTABRSTATUS"), paramEntityItem);
/*      */       
/* 6094 */       setFlagValue(this.m_elist.getProfile(), "QSMRPTABRSTATUS", getQueuedValueForItem(paramEntityItem, "QSMRPTABRSTATUS"), paramEntityItem);
/*      */       
/* 6096 */       if (statusIsRFR(paramEntityItem)) {
/*      */ 
/*      */ 
/*      */         
/* 6100 */         doRFR_ADSXML(paramEntityItem);
/* 6101 */       } else if (statusIsFinal(paramEntityItem)) {
/*      */ 
/*      */ 
/*      */         
/* 6105 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", 
/* 6106 */             getQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static int getCheck_W_E_E(String paramString) {
/* 6117 */     return "0010".equals(paramString) ? 1 : 4;
/*      */   }
/*      */   protected static int getCheck_W_RE_RE(String paramString) {
/* 6120 */     return "0010".equals(paramString) ? 1 : 3;
/*      */   }
/*      */   protected static int getCheck_RE_RE_E(String paramString) {
/* 6123 */     return "0040".equals(paramString) ? 4 : 3;
/*      */   }
/*      */   protected static int getCheck_RW_RE_RE(String paramString) {
/* 6126 */     return "0010".equals(paramString) ? 2 : 3;
/*      */   }
/*      */   protected static int getCheck_W_W_E(String paramString) {
/* 6129 */     return "0040".equals(paramString) ? 4 : 1;
/*      */   }
/*      */   protected static int getCheck_RW_RW_RE(String paramString) {
/* 6132 */     return "0040".equals(paramString) ? 3 : 2;
/*      */   }
/*      */   
/*      */   protected static int getCheck_W_RW_RW(String paramString) {
/* 6136 */     return "0010".equals(paramString) ? 1 : 2;
/*      */   }
/*      */   
/*      */   protected static int getCheck_W_RW_RE(String paramString) {
/* 6140 */     byte b = 0;
/* 6141 */     if ("0010".equals(paramString)) {
/* 6142 */       b = 1;
/* 6143 */     } else if ("0050".equals(paramString)) {
/* 6144 */       b = 2;
/* 6145 */     } else if ("0040".equals(paramString)) {
/* 6146 */       b = 3;
/*      */     } 
/* 6148 */     return b;
/*      */   }
/*      */   
/*      */   protected static int doCheck_W_N_N(String paramString) {
/* 6152 */     if (!"0010".equals(paramString)) {
/* 6153 */       return -1;
/*      */     }
/* 6155 */     return 1;
/*      */   }
/*      */   
/*      */   protected static int doCheck_N_W_E(String paramString) {
/* 6159 */     if ("0040".equals(paramString)) {
/* 6160 */       return 4;
/*      */     }
/* 6162 */     if ("0050".equals(paramString)) {
/* 6163 */       return 1;
/*      */     }
/* 6165 */     return -1;
/*      */   }
/*      */   protected static int doCheck_N_RW_RE(String paramString) {
/* 6168 */     if ("0040".equals(paramString)) {
/* 6169 */       return 3;
/*      */     }
/* 6171 */     if ("0050".equals(paramString)) {
/* 6172 */       return 2;
/*      */     }
/* 6174 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean sevReducedMsgDisplayed = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getCheckLevel(int paramInt, EntityItem paramEntityItem, String paramString) {
/* 6193 */     if (paramInt == 4 || paramInt == 3) {
/* 6194 */       String str = PokUtils.getAttributeValue(paramEntityItem, paramString, "", "", false);
/* 6195 */       if (str.length() > 0 && "2010-03-01".compareTo(str) >= 0) {
/* 6196 */         addDebug("getCheckLevel: OVERRIDING severity for " + paramEntityItem.getKey() + " " + paramString + ": " + str + " it is not greater than " + "2010-03-01");
/*      */         
/* 6198 */         if (!this.sevReducedMsgDisplayed) {
/* 6199 */           this.sevReducedMsgDisplayed = true;
/*      */           
/*      */           try {
/* 6202 */             this.args[0] = getLD_NDN(paramEntityItem);
/* 6203 */           } catch (Exception exception) {
/* 6204 */             exception.printStackTrace();
/* 6205 */             this.args[0] = "";
/*      */           } 
/* 6207 */           this.args[1] = getLD_Value(paramEntityItem, paramString);
/* 6208 */           this.args[2] = "2010-03-01";
/* 6209 */           addMessage("", "SEV_REDUCED", this.args);
/*      */         } 
/* 6211 */         if (paramInt == 3) {
/* 6212 */           return 2;
/*      */         }
/* 6214 */         return 1;
/*      */       } 
/*      */     } 
/* 6217 */     return paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isOldData(EntityItem paramEntityItem, String paramString) {
/* 6227 */     boolean bool = false;
/* 6228 */     String str = PokUtils.getAttributeValue(paramEntityItem, paramString, "", "", false);
/* 6229 */     addDebug("isOldData: " + paramEntityItem.getKey() + " " + paramString + ": " + str);
/* 6230 */     if (str.length() > 0 && "2010-03-01".compareTo(str) >= 0) {
/* 6231 */       addDebug("isOldData: " + paramString + " is not greater than " + "2010-03-01");
/* 6232 */       bool = true;
/*      */     } 
/* 6234 */     return bool;
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
/*      */   protected int getAvailCheckLevel(int paramInt, EntityItem paramEntityItem) {
/* 6249 */     String str = "EFFECTIVEDATE";
/* 6250 */     HashSet<String> hashSet = new HashSet();
/* 6251 */     hashSet.add("1999");
/*      */     
/* 6253 */     if ((paramInt == 4 || paramInt == 3) && 
/* 6254 */       PokUtils.contains(paramEntityItem, "GENAREASELECTION", hashSet)) {
/* 6255 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, str, "", "", false);
/* 6256 */       if (str1.length() > 0 && "2010-03-01".compareTo(str1) >= 0) {
/* 6257 */         addDebug("getAvailCheckLevel: OVERRIDING severity for " + paramEntityItem.getKey() + " " + str + ": " + str1 + " it is not greater than " + "2010-03-01" + " and genarea has ww ");
/*      */ 
/*      */ 
/*      */         
/*      */         try {
/* 6262 */           this.args[0] = getLD_NDN(paramEntityItem);
/* 6263 */         } catch (Exception exception) {
/* 6264 */           exception.printStackTrace();
/* 6265 */           this.args[0] = "";
/*      */         } 
/* 6267 */         this.args[1] = getLD_Value(paramEntityItem, str);
/* 6268 */         this.args[2] = "2010-03-01";
/* 6269 */         this.args[3] = getLD_Value(paramEntityItem, "GENAREASELECTION");
/* 6270 */         addMessage("", "AVAIL_SEV_REDUCED", this.args);
/*      */         
/* 6272 */         hashSet.clear();
/*      */         
/* 6274 */         if (paramInt == 3) {
/* 6275 */           return 2;
/*      */         }
/*      */         
/* 6278 */         return 1;
/*      */       } 
/*      */     } 
/* 6281 */     hashSet.clear();
/* 6282 */     return paramInt;
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
/*      */   protected void doRFR_ADSXML(EntityItem paramEntityItem) {
/* 6294 */     doRFR_ADSXML(paramEntityItem, "STATUS");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doRFR_ADSXML(EntityItem paramEntityItem, String paramString) {
/* 6303 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/* 6304 */     addDebug("doRFR_ADSXML: " + paramEntityItem.getKey() + " lifecycle " + str1);
/* 6305 */     if (str1 == null || str1.length() == 0) {
/* 6306 */       str1 = "LF01";
/*      */     }
/*      */     
/* 6309 */     String str2 = paramEntityItem.getEntityType();
/* 6310 */     addDebug("doRFR_ADSXML: " + CHECKFIRSTFINALVEC + " //\titem.getEntityType() = " + paramEntityItem.getEntityType());
/* 6311 */     if (statusIsRFR(paramEntityItem, paramString)) {
/* 6312 */       if ("MODEL".equals(str2)) {
/* 6313 */         addDebug("doRFR_ADSXML: MODEL");
/* 6314 */         doRFR_MODADSXML(paramEntityItem);
/*      */       
/*      */       }
/* 6317 */       else if (CHECKFIRSTFINALVEC.contains(str2) || "LF01".equals(str1) || "LF02"
/* 6318 */         .equals(str1)) {
/*      */ 
/*      */         
/* 6321 */         addDebug("doRFR_ADSXML: //\tSET ADSABRSTATUS\t&ADSFEEDRFR");
/* 6322 */         setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doRFR_MODADSXML(EntityItem paramEntityItem) {
/* 6330 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/* 6331 */     addDebug("doRFR_MODADSXML: " + paramEntityItem.getKey() + " lifecycle " + str);
/* 6332 */     if (str == null || str.length() == 0) {
/* 6333 */       str = "LF01";
/*      */     }
/*      */ 
/*      */     
/* 6337 */     if (!"LF01".equals(str) && 
/* 6338 */       !"LF02".equals(str)) {
/*      */       
/* 6340 */       addDebug("doRFR_MODADSXML: set ADSABRSTATUS\t&ADSFEEDRFR");
/* 6341 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */     } else {
/* 6343 */       addDebug("doRFR_MODADSXML:setRFRSinceFirstRFR");
/* 6344 */       setRFRSinceFirstRFR(paramEntityItem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRFRSinceFirstRFR(EntityItem paramEntityItem) {
/* 6354 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, "ADSABRSTATUS");
/* 6355 */     addDebug("setRFRSinceFirstRFR: adsabrstatus " + paramEntityItem.getKey() + " " + str);
/* 6356 */     boolean bool = (!"0030".equals(str) && !"XMLRFR".equals(str)) ? true : false;
/*      */     
/* 6358 */     if (bool) {
/* 6359 */       addDebug("setRFRSinceFirstRFR: getRFRFIRSTQueuedValueForItem");
/* 6360 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRFIRSTQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
/*      */     } else {
/*      */       
/* 6363 */       addDebug("setRFRSinceFirstRFR: SET ADSABRSTATUS\t&ADSFEEDRFR");
/* 6364 */       setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getRFRQueuedValueForItem(paramEntityItem, "ADSABRSTATUS"), paramEntityItem);
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
/*      */   protected boolean isSendSinceFirstFinal(EntityItem paramEntityItem) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 6394 */     String str = null;
/*      */     
/* 6396 */     EANAttribute eANAttribute1 = paramEntityItem.getAttribute("STATUS");
/* 6397 */     if (eANAttribute1 != null) {
/* 6398 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute1);
/* 6399 */       addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: STATUS");
/* 6400 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 6401 */         for (byte b = 0; b < attributeChangeHistoryGroup.getChangeHistoryItemCount(); b++) {
/* 6402 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(b);
/* 6403 */           addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem[" + b + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 6404 */               .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 6405 */           if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 6406 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 6413 */     EANAttribute eANAttribute2 = paramEntityItem.getAttribute("ADSABRSTATUS");
/* 6414 */     if (eANAttribute2 != null && str != null) {
/* 6415 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute2);
/* 6416 */       addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: ADSABRSTATUS - count:" + attributeChangeHistoryGroup.getChangeHistoryItemCount());
/* 6417 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 6418 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 6419 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 6420 */           addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 6421 */               .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 6422 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030") || attributeChangeHistoryItem.getFlagCode().equals("XMLRFR")) {
/* 6423 */             addDebug("isSendSinceFirstFinal: Match:AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 6424 */                 .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 6425 */             if (attributeChangeHistoryItem.getChangeDate().compareTo(str) > 0) {
/* 6426 */               return true;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 6434 */     return false;
/*      */   }
/*      */   
/*      */   protected void setSinceFirstFinal(EntityItem paramEntityItem, String paramString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 6438 */     if (paramString.equals("ADSABRSTATUS") && !isSendSinceFirstFinal(paramEntityItem)) {
/* 6439 */       addDebug("setSinceFirstFinal: getFINALFIRSTQueuedValueForItem isSendSinceFirstFinal(item): false");
/* 6440 */       setFlagValue(this.m_elist.getProfile(), paramString, getFINALFIRSTQueuedValueForItem(paramEntityItem, paramString), paramEntityItem);
/*      */     } else {
/* 6442 */       addDebug("setSinceFirstFinal: getQueuedValueForItem");
/* 6443 */       setFlagValue(this.m_elist.getProfile(), paramString, getQueuedValueForItem(paramEntityItem, paramString), paramEntityItem);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void setRFCSinceFirstFinal(EntityItem paramEntityItem, String paramString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 6448 */     if (paramString.equals("RFCABRSTATUS") && !isSinceFirstFinal(paramEntityItem, paramString)) {
/* 6449 */       addDebug("setSinceFirstFinal: getFINALFIRSTQueuedValueForItem isSendSinceFirstFinal(item): false");
/* 6450 */       setFlagValue(this.m_elist.getProfile(), paramString, getQueuedValueForItem(paramEntityItem, paramString), paramEntityItem);
/*      */     } else {
/* 6452 */       addDebug("setSinceFirstFinal: getQueuedValueForItem");
/* 6453 */       setFlagValue(this.m_elist.getProfile(), paramString, getQueuedValueForItem(paramEntityItem, paramString), paramEntityItem);
/*      */     } 
/*      */   }
/*      */   private boolean isSinceFirstFinal(EntityItem paramEntityItem, String paramString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 6457 */     String str = null;
/*      */     
/* 6459 */     EANAttribute eANAttribute1 = paramEntityItem.getAttribute("STATUS");
/* 6460 */     if (eANAttribute1 != null) {
/* 6461 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute1);
/* 6462 */       addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: STATUS");
/* 6463 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 6464 */         for (byte b = 0; b < attributeChangeHistoryGroup.getChangeHistoryItemCount(); b++) {
/* 6465 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(b);
/* 6466 */           addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem[" + b + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 6467 */               .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 6468 */           if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 6469 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 6476 */     EANAttribute eANAttribute2 = paramEntityItem.getAttribute(paramString);
/* 6477 */     if (eANAttribute2 != null && str != null) {
/* 6478 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute2);
/* 6479 */       addDebug("isSendSinceFirstFinal: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: " + paramString + " - count:" + attributeChangeHistoryGroup.getChangeHistoryItemCount());
/* 6480 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 6481 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 6482 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 6483 */           addDebug("isSendSinceFirstFinal: AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 6484 */               .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 6485 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030")) {
/* 6486 */             addDebug("isSendSinceFirstFinal: Match:AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 6487 */                 .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 6488 */             if (attributeChangeHistoryItem.getChangeDate().compareTo(str) > 0) {
/* 6489 */               return true;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/* 6497 */     return false;
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
/*      */   protected int getAttributeValueHistoryCount(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 6511 */     byte b = 0;
/* 6512 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 6513 */     if (eANAttribute != null) {
/* 6514 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute);
/* 6515 */       addDebug("getAttributeValueHistoryCount: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: " + paramString1);
/* 6516 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 6517 */         for (byte b1 = 0; b1 < attributeChangeHistoryGroup.getChangeHistoryItemCount(); b1++) {
/* 6518 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(b1);
/*      */ 
/*      */           
/* 6521 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString2)) {
/* 6522 */             b++;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/* 6527 */     addDebug("getAttributeValueHistoryCount: attValueCount " + b + " for Attribute: " + paramString1 + " value " + paramString2);
/* 6528 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doRFR_ADSXML(String paramString) {
/* 6539 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString);
/* 6540 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 6541 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/*      */ 
/*      */ 
/*      */       
/* 6545 */       doRFR_ADSXML(entityItem);
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
/*      */   protected void checkRelatedItems(String paramString, boolean paramBoolean) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 6558 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString);
/*      */     
/* 6560 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 6561 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 6562 */       if (statusIsFinal(entityItem)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 6567 */         if (paramBoolean)
/*      */         {
/*      */           
/* 6570 */           if ("MODEL".equals(paramString)) {
/* 6571 */             setSinceFirstFinal(entityItem, "ADSABRSTATUS");
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 6580 */         doRFR_ADSXML(entityItem);
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
/*      */   protected boolean anyIn(EntityItem paramEntityItem1, String paramString1, int paramInt1, EntityItem paramEntityItem2, String paramString2, int paramInt2) throws SQLException, MiddlewareException {
/* 6606 */     boolean bool = false;
/*      */     
/* 6608 */     ArrayList arrayList = new ArrayList();
/* 6609 */     ArrayList<?> arrayList1 = new ArrayList();
/* 6610 */     getAttributeAsList(paramEntityItem1, arrayList, paramString1, paramInt1);
/* 6611 */     getAttributeAsList(paramEntityItem2, arrayList1, paramString2, paramInt2);
/* 6612 */     addDebug("anyIn " + paramEntityItem1.getKey() + " list1: " + arrayList + " " + paramEntityItem2.getKey() + " list2: " + arrayList1);
/* 6613 */     arrayList.retainAll(arrayList1);
/* 6614 */     addDebug("anyIn after retainall list1: " + arrayList);
/* 6615 */     bool = (arrayList.size() > 0) ? true : false;
/* 6616 */     arrayList.clear();
/* 6617 */     arrayList1.clear();
/* 6618 */     return bool;
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
/*      */   protected void checkWwseoLseo(boolean paramBoolean, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 6662 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEO");
/*      */     
/* 6664 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 6665 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 6666 */       Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(entityItem, "WWSEOLSEO", "LSEO");
/* 6667 */       if (statusIsFinal(entityItem)) {
/*      */ 
/*      */ 
/*      */         
/* 6671 */         if (paramBoolean) {
/* 6672 */           for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 6673 */             EntityItem entityItem1 = vector.elementAt(b1);
/*      */             
/* 6675 */             if (statusIsFinal(entityItem1))
/*      */             {
/* 6677 */               if (anyIn(entityItem1, "COUNTRYLIST", -1, paramEntityItem, "COUNTRYLIST", -1))
/*      */               {
/* 6679 */                 setFlagValue(this.m_elist.getProfile(), "ADSABRSTATUS", getQueuedValueForItem(entityItem1, "ADSABRSTATUS"), entityItem1);
/*      */               }
/*      */             }
/*      */           }
/*      */         
/*      */         }
/* 6685 */       } else if (statusIsRFR(entityItem)) {
/*      */ 
/*      */         
/* 6688 */         for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 6689 */           EntityItem entityItem1 = vector.elementAt(b1);
/*      */           
/* 6691 */           if (anyIn(entityItem1, "COUNTRYLIST", -1, paramEntityItem, "COUNTRYLIST", -1))
/*      */           {
/*      */ 
/*      */             
/* 6695 */             doRFR_ADSXML(entityItem1);
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
/*      */   protected void oneValidOverTime(EntityItem paramEntityItem, String[] paramArrayOfString, int paramInt) {
/* 6732 */     StringBuffer stringBuffer = new StringBuffer(paramEntityItem.getEntityGroup().getLongDescription());
/* 6733 */     Vector<EntityItem> vector = new Vector();
/* 6734 */     vector.add(paramEntityItem);
/* 6735 */     for (byte b1 = 0; b1 < paramArrayOfString.length; b1 += 2) {
/* 6736 */       String str6 = paramArrayOfString[b1];
/* 6737 */       String str7 = paramArrayOfString[b1 + 1];
/*      */       
/* 6739 */       vector = PokUtils.getAllLinkedEntities(vector, str6, str7);
/* 6740 */       addDebug("oneValidOverTime entered for " + paramEntityItem.getKey() + " linkType " + str6 + " childtype " + str7 + " childVct " + vector
/* 6741 */           .size());
/* 6742 */       stringBuffer.append(" and " + this.m_elist.getEntityGroup(str7).getLongDescription());
/*      */     } 
/*      */     
/* 6745 */     addHeading(3, stringBuffer.toString() + " validity checks:");
/*      */     
/* 6747 */     if (vector.size() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 6752 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 6753 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "ENDDATE", "", "9999-12-31", false);
/* 6754 */     addDebug("oneValidOverTime parentEffFrom " + str1 + " parentEffTo " + str2);
/*      */     
/* 6756 */     Collections.sort(vector, (Comparator<? super EntityItem>)new AttrComparator("EFFECTIVEDATE"));
/*      */ 
/*      */     
/* 6759 */     EntityItem entityItem = vector.firstElement();
/* 6760 */     String str3 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 6761 */     String str4 = PokUtils.getAttributeValue(entityItem, "ENDDATE", "", "9999-12-31", false);
/* 6762 */     addDebug("oneValidOverTime first " + entityItem.getKey() + " childEffFrom " + str3 + " prevEffTo " + str4);
/* 6763 */     if (str3.compareTo(str1) > 0) {
/*      */ 
/*      */ 
/*      */       
/* 6767 */       this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 6768 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 6769 */       createMessage(4, "INVALID_CHILD_ERR", this.args);
/* 6770 */       vector.clear();
/*      */       
/*      */       return;
/*      */     } 
/* 6774 */     entityItem = vector.lastElement();
/* 6775 */     String str5 = PokUtils.getAttributeValue(entityItem, "ENDDATE", "", "9999-12-31", false);
/* 6776 */     addDebug("oneValidOverTime last " + entityItem.getKey() + " childEffTo " + str5);
/* 6777 */     if (str5.compareTo(str2) < 0) {
/*      */       
/* 6779 */       this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 6780 */       this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 6781 */       createMessage(4, "INVALID_CHILD_ERR", this.args);
/* 6782 */       vector.clear();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 6787 */     for (byte b2 = 1; b2 < vector.size(); b2++) {
/* 6788 */       entityItem = vector.elementAt(b2);
/* 6789 */       String str6 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 6790 */       String str7 = PokUtils.getAttributeValue(entityItem, "ENDDATE", "", "9999-12-31", false);
/* 6791 */       addDebug("oneValidOverTime gaps " + entityItem.getKey() + " prevEffTo " + str4 + " nextEffFrom " + str6 + " nextEffTo " + str7);
/*      */       
/* 6793 */       if (str6.compareTo(str4) > 0) {
/*      */         
/* 6795 */         this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 6796 */         this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 6797 */         createMessage(4, "INVALID_CHILD_ERR", this.args);
/* 6798 */         vector.clear();
/*      */         return;
/*      */       } 
/* 6801 */       str4 = str7;
/*      */     } 
/*      */     
/* 6804 */     vector.clear();
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
/*      */   protected void checkUniqueCoverage(EntityItem paramEntityItem, String paramString1, String paramString2, Vector<EntityItem> paramVector1, Vector<EntityItem> paramVector2, int paramInt, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 6825 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/* 6826 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString2);
/* 6827 */     if (paramString1.equals(paramString2)) {
/* 6828 */       vector.clear();
/*      */       
/* 6830 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 6831 */         vector.add(entityGroup.getEntityItem(b));
/*      */       }
/*      */     } 
/* 6834 */     addDebug("checkUniqueCoverage entered for " + paramString2 + " cnt " + vector.size());
/* 6835 */     if (vector.size() == 0) {
/* 6836 */       if (paramBoolean) {
/*      */         
/* 6838 */         this.args[0] = entityGroup.getLongDescription();
/* 6839 */         createMessage(paramInt, "MINIMUM_ERR", this.args);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 6844 */     ArrayList<?> arrayList = new ArrayList();
/* 6845 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/* 6847 */     for (byte b3 = 0; b3 < vector.size(); b3++) {
/* 6848 */       EntityItem entityItem = vector.elementAt(b3);
/* 6849 */       String str = PokUtils.getAttributeValue(entityItem, "PUBFROM", "", null, false);
/* 6850 */       if (str == null) {
/*      */         
/* 6852 */         this.args[0] = getLD_NDN(entityItem);
/* 6853 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "PUBFROM", "PUBFROM");
/* 6854 */         createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/*      */       }
/*      */       else {
/*      */         
/* 6858 */         CoverageData coverageData = new CoverageData(entityItem, paramInt);
/* 6859 */         Iterator<String> iterator = coverageData.ctryList.iterator();
/* 6860 */         while (iterator.hasNext()) {
/* 6861 */           String str1 = iterator.next();
/* 6862 */           Vector<CoverageData> vector1 = (Vector)hashtable.get(str1);
/* 6863 */           if (vector1 == null) {
/* 6864 */             vector1 = new Vector();
/* 6865 */             hashtable.put(str1, vector1);
/*      */           } 
/* 6867 */           vector1.add(coverageData);
/*      */         } 
/* 6869 */         getCountriesAsList(entityItem, arrayList, paramInt);
/*      */       } 
/*      */     } 
/* 6872 */     if (hashtable.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 6876 */     for (Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements();) {
/* 6877 */       Collections.sort((Vector)hashtable.get(enumeration.nextElement()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6886 */     addDebug(paramString2 + "  allCtryList " + arrayList);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6892 */     for (byte b2 = 0; b2 < paramVector1.size(); b2++) {
/* 6893 */       EntityItem entityItem = paramVector1.elementAt(b2);
/* 6894 */       ArrayList<?> arrayList1 = new ArrayList();
/* 6895 */       getCountriesAsList(entityItem, arrayList1, paramInt);
/* 6896 */       addDebug("checkUniqueCoverage plannedavail[" + b2 + "] " + entityItem.getKey() + " ctryList " + arrayList1);
/* 6897 */       if (!arrayList.containsAll(arrayList1)) {
/*      */         
/* 6899 */         this.args[0] = entityGroup.getLongDescription();
/* 6900 */         this.args[1] = paramEntityItem.getEntityGroup().getLongDescription();
/* 6901 */         this.args[2] = getLD_NDN(entityItem) + " " + 
/* 6902 */           PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */         
/* 6904 */         ArrayList arrayList2 = new ArrayList();
/* 6905 */         getCountriesAsList(entityItem, arrayList2, paramInt);
/* 6906 */         arrayList2.removeAll(arrayList);
/* 6907 */         this.args[3] = getUnmatchedDescriptions(entityItem, "COUNTRYLIST", arrayList2);
/* 6908 */         createMessage(paramInt, "MISSING_CTRY_ERR2", this.args);
/* 6909 */         arrayList2.clear();
/*      */       } 
/*      */ 
/*      */       
/* 6913 */       String str = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt);
/* 6914 */       addDebug("checkUniqueCoverage plannedavail " + entityItem.getKey() + " EFFECTIVEDATE " + str);
/* 6915 */       boolean bool = true;
/* 6916 */       Iterator<?> iterator = arrayList1.iterator();
/* 6917 */       while (iterator.hasNext() && bool) {
/* 6918 */         String str1 = (String)iterator.next();
/* 6919 */         Vector<CoverageData> vector1 = (Vector)hashtable.get(str1);
/* 6920 */         if (vector1 != null) {
/* 6921 */           CoverageData coverageData = vector1.firstElement();
/* 6922 */           String str2 = coverageData.pubfrom;
/* 6923 */           addDebug("checkUniqueCoverage " + coverageData.item.getKey() + " had minpubfrom " + str2 + " for country " + str1);
/*      */ 
/*      */           
/* 6926 */           bool = checkDates(str2, str, 2); continue;
/*      */         } 
/* 6928 */         addDebug("checkUniqueCoverage: No " + paramString2 + " found for country " + str1);
/*      */       } 
/*      */ 
/*      */       
/* 6932 */       if (!bool) {
/*      */         
/* 6934 */         this.args[0] = entityGroup.getLongDescription();
/* 6935 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "PUBFROM", "PUBFROM");
/* 6936 */         this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/* 6937 */         this.args[3] = getLD_NDN(entityItem);
/* 6938 */         createMessage(paramInt, "PUBFROM_ERR", this.args);
/*      */       } 
/* 6940 */       arrayList1.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6951 */     if (entityGroup.getEntityItemCount() > 1) {
/* 6952 */       HashSet<EntityItem> hashSet = new HashSet();
/*      */ 
/*      */       
/* 6955 */       for (Enumeration<String> enumeration3 = hashtable.keys(); enumeration3.hasMoreElements(); ) {
/* 6956 */         String str = enumeration3.nextElement();
/* 6957 */         Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/*      */         
/* 6959 */         CoverageData coverageData = null;
/*      */         
/* 6961 */         for (byte b = 0; b < vector1.size(); b++) {
/* 6962 */           CoverageData coverageData1 = vector1.elementAt(b);
/* 6963 */           if (b != 0) {
/*      */             
/* 6965 */             Date date1 = Date.valueOf(coverageData.pubto);
/* 6966 */             long l1 = date1.getTime();
/* 6967 */             long l2 = l1 + 86400000L;
/* 6968 */             Date date2 = new Date(l2);
/*      */             
/* 6970 */             if (!date2.toString().equals(coverageData1.pubfrom)) {
/* 6971 */               if (!hashSet.contains(coverageData.item) || 
/* 6972 */                 !hashSet.contains(coverageData1.item)) {
/*      */                 
/* 6974 */                 hashSet.add(coverageData.item);
/* 6975 */                 hashSet.add(coverageData1.item);
/* 6976 */                 addDebug("checkUniqueCoverage output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 6977 */                     .getKey());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 6982 */                 this.args[0] = getLD_NDN(coverageData.item);
/* 6983 */                 this.args[1] = getLD_NDN(coverageData1.item);
/* 6984 */                 createMessage(paramInt, "DATE_RANGE_ERR", this.args);
/*      */               } else {
/* 6986 */                 addDebug("checkUniqueCoverage already output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 6987 */                     .getKey());
/*      */               } 
/*      */             }
/*      */           } 
/* 6991 */           coverageData = coverageData1;
/*      */         } 
/*      */       } 
/* 6994 */       hashSet.clear();
/*      */     } 
/*      */ 
/*      */     
/* 6998 */     for (Enumeration<String> enumeration2 = hashtable.keys(); enumeration2.hasMoreElements(); ) {
/* 6999 */       String str = enumeration2.nextElement();
/* 7000 */       Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/* 7001 */       for (byte b = 0; b < vector1.size(); b++) {
/* 7002 */         CoverageData coverageData = vector1.elementAt(b);
/* 7003 */         coverageData.setPubFromSort(false);
/*      */       } 
/* 7005 */       Collections.sort(vector1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7011 */     for (byte b1 = 0; b1 < paramVector2.size(); b1++) {
/* 7012 */       EntityItem entityItem = paramVector2.elementAt(b1);
/* 7013 */       String str = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt);
/* 7014 */       addDebug("checkUniqueCoverage mdllastorder[" + b1 + "] " + entityItem.getKey() + " EFFECTIVEDATE " + str);
/*      */       
/* 7016 */       ArrayList arrayList1 = new ArrayList();
/* 7017 */       getCountriesAsList(entityItem, arrayList1, paramInt);
/*      */       
/* 7019 */       boolean bool = true;
/* 7020 */       Iterator<String> iterator = arrayList1.iterator();
/* 7021 */       while (iterator.hasNext() && bool) {
/* 7022 */         String str1 = iterator.next();
/* 7023 */         Vector<CoverageData> vector1 = (Vector)hashtable.get(str1);
/* 7024 */         if (vector1 != null) {
/* 7025 */           CoverageData coverageData = vector1.lastElement();
/* 7026 */           String str2 = coverageData.pubto;
/* 7027 */           addDebug("checkUniqueCoverage " + coverageData.item.getKey() + " had maxPubto " + str2 + " for country " + str1);
/*      */ 
/*      */           
/* 7030 */           bool = checkDates(str2, str, 1); continue;
/*      */         } 
/* 7032 */         addDebug("checkUniqueCoverage: No " + paramString2 + " found for country " + str1);
/*      */       } 
/*      */ 
/*      */       
/* 7036 */       if (!bool) {
/*      */ 
/*      */         
/* 7039 */         this.args[0] = entityGroup.getLongDescription();
/* 7040 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "PUBTO", "PUBTO");
/* 7041 */         this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/* 7042 */         this.args[3] = getLD_NDN(entityItem);
/* 7043 */         createMessage(paramInt, "PUBTO_ERR", this.args);
/*      */       } 
/* 7045 */       arrayList1.clear();
/*      */     } 
/*      */ 
/*      */     
/* 7049 */     for (Enumeration<String> enumeration1 = hashtable.keys(); enumeration1.hasMoreElements(); ) {
/* 7050 */       String str = enumeration1.nextElement();
/* 7051 */       Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/* 7052 */       for (byte b = 0; b < vector1.size(); b++) {
/* 7053 */         CoverageData coverageData = vector1.elementAt(b);
/* 7054 */         coverageData.dereference();
/*      */       } 
/* 7056 */       vector1.clear();
/*      */     } 
/* 7058 */     hashtable.clear();
/* 7059 */     vector.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkSystemMaxAndConfqty(EntityItem paramEntityItem, String paramString, int paramInt) throws SQLException, MiddlewareException {
/* 7064 */     for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 7065 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 7066 */       if (entityItem.getEntityType().equals(paramString)) {
/* 7067 */         String str = PokUtils.getAttributeValue(entityItem, "CONFQTY", ", ", "1", false);
/* 7068 */         addDebug("SEOPS " + entityItem.getKey() + " CONFQTY: " + str);
/* 7069 */         int i = Integer.parseInt(str);
/* 7070 */         for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 7071 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/* 7072 */           if (entityItem1.getEntityType().equals("PRODSTRUCT")) {
/* 7073 */             String str1 = PokUtils.getAttributeValue(entityItem1, "SYSTEMMAX", ", ", "0", false);
/* 7074 */             addDebug("PS " + entityItem1.getKey() + " SYSTEMMAX: " + str1);
/* 7075 */             int j = Integer.parseInt(str1);
/* 7076 */             if (j < i) {
/*      */ 
/*      */               
/* 7079 */               this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 7080 */               this.args[1] = getLD_Value(entityItem, "CONFQTY");
/* 7081 */               this.args[2] = getLD_NDN(entityItem1);
/* 7082 */               this.args[3] = getLD_Value(entityItem1, "SYSTEMMAX");
/* 7083 */               createMessage(paramInt, "SYSTEMMAX_CONFQTY_ERROR", this.args);
/*      */             } 
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
/*      */   protected void checkUniqueCoverage(EntityItem paramEntityItem1, String paramString1, String paramString2, EntityItem paramEntityItem2, String paramString3, String paramString4, int paramInt, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 7108 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem1, paramString1, paramString2);
/* 7109 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString2);
/*      */     
/* 7111 */     addDebug("checkUniqueCoverage entered rootlinkitem " + paramEntityItem1.getKey() + " offeringItem " + paramEntityItem2.getKey() + " for type " + paramString2 + " thru " + paramString1 + " cnt " + vector
/* 7112 */         .size());
/* 7113 */     if (vector.size() == 0) {
/* 7114 */       if (paramBoolean) {
/*      */         
/* 7116 */         this.args[0] = entityGroup.getLongDescription();
/* 7117 */         createMessage(paramInt, "MINIMUM_ERR", this.args);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 7122 */     ArrayList<?> arrayList1 = new ArrayList();
/* 7123 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */     
/* 7125 */     for (byte b = 0; b < vector.size(); b++) {
/* 7126 */       EntityItem entityItem = vector.elementAt(b);
/* 7127 */       String str = PokUtils.getAttributeValue(entityItem, "PUBFROM", "", null, false);
/* 7128 */       if (str == null) {
/*      */         
/* 7130 */         this.args[0] = getLD_NDN(entityItem);
/* 7131 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "PUBFROM", "PUBFROM");
/* 7132 */         createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/*      */       }
/*      */       else {
/*      */         
/* 7136 */         CoverageData coverageData = new CoverageData(entityItem, paramInt);
/* 7137 */         Iterator<String> iterator1 = coverageData.ctryList.iterator();
/* 7138 */         while (iterator1.hasNext()) {
/* 7139 */           String str3 = iterator1.next();
/* 7140 */           Vector<CoverageData> vector1 = (Vector)hashtable.get(str3);
/* 7141 */           if (vector1 == null) {
/* 7142 */             vector1 = new Vector();
/* 7143 */             hashtable.put(str3, vector1);
/*      */           } 
/* 7145 */           vector1.add(coverageData);
/*      */         } 
/* 7147 */         getCountriesAsList(entityItem, arrayList1, paramInt);
/*      */       } 
/*      */     } 
/* 7150 */     if (hashtable.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 7154 */     for (Enumeration enumeration = hashtable.keys(); enumeration.hasMoreElements();) {
/* 7155 */       Collections.sort((Vector)hashtable.get(enumeration.nextElement()));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7164 */     addDebug(paramString2 + "  allCtryList " + arrayList1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7169 */     String str1 = PokUtils.getAttributeValue(paramEntityItem2, paramString3, "", "1980-01-01", false);
/* 7170 */     String str2 = PokUtils.getAttributeValue(paramEntityItem2, paramString4, "", "9999-12-31", false);
/* 7171 */     ArrayList<?> arrayList2 = new ArrayList();
/* 7172 */     getCountriesAsList(paramEntityItem2, arrayList2, paramInt);
/* 7173 */     addDebug("checkUniqueCoverage offeringItem " + paramEntityItem2.getKey() + " rootpubfrom " + str1 + " rootpubto " + str2 + " ctryList " + arrayList2);
/*      */ 
/*      */     
/* 7176 */     if (!arrayList1.containsAll(arrayList2)) {
/*      */       
/* 7178 */       this.args[0] = entityGroup.getLongDescription();
/* 7179 */       this.args[1] = paramEntityItem2.getEntityGroup().getLongDescription();
/*      */       
/* 7181 */       if (paramEntityItem2.getEntityID() != getEntityID() && !paramEntityItem2.getEntityType().equals(getEntityType())) {
/* 7182 */         this.args[1] = getLD_NDN(paramEntityItem2);
/*      */       }
/*      */       
/* 7185 */       this.args[2] = PokUtils.getAttributeDescription(paramEntityItem2.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */       
/* 7187 */       ArrayList arrayList = new ArrayList();
/* 7188 */       getCountriesAsList(paramEntityItem2, arrayList, paramInt);
/* 7189 */       arrayList.removeAll(arrayList1);
/* 7190 */       this.args[3] = getUnmatchedDescriptions(paramEntityItem2, "COUNTRYLIST", arrayList);
/* 7191 */       createMessage(paramInt, "MISSING_CTRY_ERR2", this.args);
/* 7192 */       arrayList.clear();
/*      */     } 
/*      */ 
/*      */     
/* 7196 */     boolean bool = true;
/* 7197 */     Iterator<?> iterator = arrayList2.iterator();
/* 7198 */     while (iterator.hasNext() && bool) {
/* 7199 */       String str = (String)iterator.next();
/* 7200 */       Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/* 7201 */       if (vector1 != null) {
/* 7202 */         CoverageData coverageData = vector1.firstElement();
/* 7203 */         String str3 = coverageData.pubfrom;
/*      */ 
/*      */         
/* 7206 */         bool = checkDates(str3, str1, 2);
/* 7207 */         if (!bool)
/* 7208 */           addDebug("checkUniqueCoverage " + coverageData.item.getKey() + " had minpubfrom " + str3 + " for country " + str); 
/*      */         continue;
/*      */       } 
/* 7211 */       addDebug("checkUniqueCoverage: No " + paramString2 + " found for country " + str);
/*      */     } 
/*      */ 
/*      */     
/* 7215 */     if (!bool) {
/*      */       
/* 7217 */       this.args[0] = entityGroup.getLongDescription();
/* 7218 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup, "PUBFROM", "PUBFROM");
/* 7219 */       this.args[2] = paramEntityItem2.getEntityGroup().getLongDescription();
/*      */       
/* 7221 */       if (paramEntityItem2.getEntityID() != getEntityID() && !paramEntityItem2.getEntityType().equals(getEntityType())) {
/* 7222 */         this.args[2] = getLD_NDN(paramEntityItem2);
/*      */       }
/* 7224 */       this.args[3] = getLD_Value(paramEntityItem2, paramString3);
/* 7225 */       createMessage(paramInt, "PUBFROM_ERR", this.args);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7236 */     if (entityGroup.getEntityItemCount() > 1) {
/* 7237 */       HashSet<EntityItem> hashSet = new HashSet();
/*      */ 
/*      */       
/* 7240 */       for (Enumeration<String> enumeration2 = hashtable.keys(); enumeration2.hasMoreElements(); ) {
/* 7241 */         String str = enumeration2.nextElement();
/* 7242 */         Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/*      */         
/* 7244 */         CoverageData coverageData = null;
/*      */         
/* 7246 */         for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 7247 */           CoverageData coverageData1 = vector1.elementAt(b1);
/* 7248 */           if (b1 != 0) {
/*      */             
/* 7250 */             Date date1 = Date.valueOf(coverageData.pubto);
/* 7251 */             long l1 = date1.getTime();
/* 7252 */             long l2 = l1 + 86400000L;
/* 7253 */             Date date2 = new Date(l2);
/*      */             
/* 7255 */             if (!date2.toString().equals(coverageData1.pubfrom)) {
/* 7256 */               if (!hashSet.contains(coverageData.item) || 
/* 7257 */                 !hashSet.contains(coverageData1.item)) {
/* 7258 */                 hashSet.add(coverageData.item);
/* 7259 */                 hashSet.add(coverageData1.item);
/* 7260 */                 addDebug("checkUniqueCoverage output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 7261 */                     .getKey());
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 7266 */                 this.args[0] = getLD_NDN(coverageData.item);
/* 7267 */                 this.args[1] = getLD_NDN(coverageData1.item);
/* 7268 */                 createMessage(paramInt, "DATE_RANGE_ERR", this.args);
/*      */               } else {
/* 7270 */                 addDebug("checkUniqueCoverage already output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 7271 */                     .getKey());
/*      */               } 
/*      */             }
/*      */           } 
/* 7275 */           coverageData = coverageData1;
/*      */         } 
/*      */       } 
/* 7278 */       hashSet.clear();
/*      */     } 
/*      */     
/*      */     Enumeration<String> enumeration1;
/* 7282 */     for (enumeration1 = hashtable.keys(); enumeration1.hasMoreElements(); ) {
/* 7283 */       String str = enumeration1.nextElement();
/* 7284 */       Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/* 7285 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 7286 */         CoverageData coverageData = vector1.elementAt(b1);
/* 7287 */         coverageData.setPubFromSort(false);
/*      */       } 
/* 7289 */       Collections.sort(vector1);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7295 */     bool = true;
/* 7296 */     iterator = arrayList2.iterator();
/* 7297 */     while (iterator.hasNext() && bool) {
/* 7298 */       String str = (String)iterator.next();
/* 7299 */       Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/* 7300 */       if (vector1 != null) {
/* 7301 */         CoverageData coverageData = vector1.lastElement();
/* 7302 */         String str3 = coverageData.pubto;
/*      */ 
/*      */ 
/*      */         
/* 7306 */         bool = checkDates(str3, str2, 1);
/* 7307 */         if (!bool)
/* 7308 */           addDebug("checkUniqueCoverage " + coverageData.item.getKey() + " had maxPubto " + str3 + " for country " + str); 
/*      */         continue;
/*      */       } 
/* 7311 */       addDebug("checkUniqueCoverage: No " + paramString2 + " found for country " + str);
/*      */     } 
/*      */ 
/*      */     
/* 7315 */     if (!bool) {
/*      */ 
/*      */       
/* 7318 */       this.args[0] = entityGroup.getLongDescription();
/* 7319 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup, "PUBTO", "PUBTO");
/* 7320 */       this.args[2] = paramEntityItem2.getEntityGroup().getLongDescription();
/*      */       
/* 7322 */       if (paramEntityItem2.getEntityID() != getEntityID() && !paramEntityItem2.getEntityType().equals(getEntityType())) {
/* 7323 */         this.args[2] = getLD_NDN(paramEntityItem2);
/*      */       }
/* 7325 */       this.args[3] = getLD_Value(paramEntityItem2, paramString4);
/* 7326 */       createMessage(paramInt, "PUBTO_ERR", this.args);
/*      */     } 
/* 7328 */     arrayList2.clear();
/*      */ 
/*      */     
/* 7331 */     for (enumeration1 = hashtable.keys(); enumeration1.hasMoreElements(); ) {
/* 7332 */       String str = enumeration1.nextElement();
/* 7333 */       Vector<CoverageData> vector1 = (Vector)hashtable.get(str);
/* 7334 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 7335 */         CoverageData coverageData = vector1.elementAt(b1);
/* 7336 */         coverageData.dereference();
/*      */       } 
/* 7338 */       vector1.clear();
/*      */     } 
/* 7340 */     hashtable.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getUnmatchedDescriptions(EntityItem paramEntityItem, String paramString, ArrayList paramArrayList) {
/* 7351 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/* 7352 */     StringBuffer stringBuffer = new StringBuffer();
/* 7353 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 7355 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 7356 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 7358 */         if (paramArrayList.contains(arrayOfMetaFlag[b].getFlagCode())) {
/* 7359 */           if (stringBuffer.length() > 0) {
/* 7360 */             stringBuffer.append(", ");
/*      */           }
/* 7362 */           stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */         } 
/*      */       } 
/*      */     } 
/* 7366 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getUnmatchedDescriptions(EntityGroup paramEntityGroup, String paramString1, String paramString2) {
/* 7376 */     EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)paramEntityGroup.getMetaAttribute(paramString1);
/* 7377 */     if (eANMetaFlagAttribute != null) {
/* 7378 */       MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(paramString2);
/* 7379 */       if (metaFlag != null) {
/* 7380 */         return metaFlag.toString();
/*      */       }
/*      */     } 
/* 7383 */     return "";
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean doWARRChecks() {
/* 7445 */     String str = ABRServerProperties.getValue("WARR", "_Checks", "false");
/*      */     
/* 7447 */     addDebug("doWARRChecks " + str);
/*      */     
/* 7449 */     return str.equalsIgnoreCase("true");
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
/*      */   protected void checkWarrCoverage(EntityItem paramEntityItem1, EntityItem paramEntityItem2, String paramString1, String paramString2, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 7466 */     if (!doWARRChecks()) {
/* 7467 */       addOutput("Bypassing Warranty coverage checks for now.");
/*      */       
/*      */       return;
/*      */     } 
/* 7471 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString1);
/* 7472 */     Vector vector1 = getDownLinkEntityItems(paramEntityItem2, paramString1);
/* 7473 */     if (vector1.size() == 0) {
/* 7474 */       addDebug("checkWarrCoverage NO " + paramString1 + " found.");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 7479 */     Vector vector2 = PokUtils.getAllLinkedEntities(paramEntityItem1, paramString2, "AVAIL");
/* 7480 */     Vector vector3 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "149");
/* 7481 */     Vector vector4 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "146");
/*      */     
/* 7483 */     addDebug("checkWarrCoverage  availParent " + paramEntityItem1.getKey() + " " + paramString2 + " availVct: " + vector2
/* 7484 */         .size() + " loavailVct: " + vector3.size() + " plannedAvailVct: " + vector4.size());
/*      */     
/* 7486 */     if (vector4.size() == 0) {
/* 7487 */       vector2.clear();
/* 7488 */       vector3.clear();
/* 7489 */       vector4.clear();
/* 7490 */       vector1.clear();
/*      */       
/*      */       return;
/*      */     } 
/* 7494 */     checkMdlWarrWithAvailAAndAvailC(vector4, vector3, paramEntityItem2, paramString1, vector1, vector2, entityGroup, paramEntityItem1, paramInt1, paramInt2);
/*      */ 
/*      */     
/* 7497 */     Vector vector5 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "172");
/* 7498 */     Vector vector6 = PokUtils.getEntitiesWithMatchedAttr(vector2, "AVAILTYPE", "171");
/*      */     
/* 7500 */     addDebug("checkWarrCoverage  availParent " + paramEntityItem1.getKey() + " " + paramString2 + " availVct: " + vector2
/* 7501 */         .size() + " mesLoavailVct: " + vector5.size() + " mesPlannedAvailVct: " + vector6.size());
/*      */     
/* 7503 */     if (vector6.size() == 0) {
/* 7504 */       vector2.clear();
/* 7505 */       vector5.clear();
/* 7506 */       vector6.clear();
/* 7507 */       vector1.clear();
/*      */       return;
/*      */     } 
/* 7510 */     checkMdlWarrWithAvailAAndAvailC(vector6, vector5, paramEntityItem2, paramString1, vector1, vector2, entityGroup, paramEntityItem1, paramInt1, paramInt2);
/*      */ 
/*      */     
/* 7513 */     vector1.clear();
/* 7514 */     vector2.clear();
/* 7515 */     vector3.clear();
/* 7516 */     vector4.clear();
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
/*      */   private void checkMdlWarrWithAvailAAndAvailC(Vector<?> paramVector1, Vector<?> paramVector2, EntityItem paramEntityItem1, String paramString, Vector paramVector3, Vector paramVector4, EntityGroup paramEntityGroup, EntityItem paramEntityItem2, int paramInt1, int paramInt2) throws SQLException, MiddlewareException {
/* 7538 */     ArrayList<?> arrayList = new ArrayList();
/* 7539 */     Hashtable<Object, Object> hashtable1 = new Hashtable<>();
/* 7540 */     Vector<CoverageData> vector = new Vector();
/*      */ 
/*      */     
/* 7543 */     AttrComparator attrComparator = new AttrComparator("EFFECTIVEDATE");
/* 7544 */     Collections.sort(paramVector1, (Comparator<?>)attrComparator);
/* 7545 */     Collections.sort(paramVector2, (Comparator<?>)attrComparator);
/*      */     
/* 7547 */     ArrayList arrayList1 = new ArrayList();
/* 7548 */     Hashtable<Object, Object> hashtable2 = new Hashtable<>(); int i;
/* 7549 */     for (i = paramVector1.size() - 1; i >= 0; i--) {
/* 7550 */       ArrayList arrayList2 = new ArrayList();
/* 7551 */       EntityItem entityItem = (EntityItem)paramVector1.elementAt(i);
/* 7552 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "", false);
/* 7553 */       getCountriesAsList(entityItem, arrayList2, paramInt1);
/* 7554 */       arrayList1.addAll(arrayList2);
/*      */       
/* 7556 */       Iterator<String> iterator1 = arrayList2.iterator();
/* 7557 */       while (iterator1.hasNext()) {
/* 7558 */         String str1 = iterator1.next();
/* 7559 */         TPIC tPIC = (TPIC)hashtable2.get(str1);
/* 7560 */         if (tPIC != null) {
/* 7561 */           tPIC.fromDate = str; continue;
/*      */         } 
/* 7563 */         hashtable2.put(str1, new TPIC(str1, str));
/*      */       } 
/*      */       
/* 7566 */       arrayList2.clear();
/*      */     } 
/* 7568 */     for (i = 0; i < paramVector2.size(); i++) {
/* 7569 */       ArrayList arrayList2 = new ArrayList();
/* 7570 */       EntityItem entityItem = (EntityItem)paramVector2.elementAt(i);
/* 7571 */       String str = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "", false);
/* 7572 */       getCountriesAsList(entityItem, arrayList2, paramInt1);
/*      */       
/* 7574 */       Iterator<String> iterator1 = arrayList2.iterator();
/* 7575 */       while (iterator1.hasNext()) {
/* 7576 */         String str1 = iterator1.next();
/* 7577 */         TPIC tPIC = (TPIC)hashtable2.get(str1);
/* 7578 */         if (tPIC != null) {
/* 7579 */           tPIC.toDate = str;
/*      */         }
/*      */       } 
/* 7582 */       arrayList2.clear();
/*      */     } 
/*      */     
/* 7585 */     addDebug("checkWarrCoverage availByCtryTbl " + hashtable2);
/*      */ 
/*      */     
/* 7588 */     findWarrByCtry(paramEntityItem1, paramString, paramVector3, arrayList1, arrayList, hashtable1, vector, paramInt1);
/*      */     
/* 7590 */     addDebug("checkWarrCoverage warrParent " + paramEntityItem1.getKey() + " ucVct " + vector
/* 7591 */         .size() + " all " + paramString + " warrCtryList " + arrayList + " offeringCtryList " + arrayList1);
/*      */     
/* 7593 */     if (vector.size() == 0) {
/* 7594 */       paramVector4.clear();
/* 7595 */       paramVector2.clear();
/* 7596 */       paramVector1.clear();
/* 7597 */       arrayList1.clear();
/* 7598 */       arrayList.clear();
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 7603 */     for (i = 0; i < paramVector1.size(); i++) {
/* 7604 */       EntityItem entityItem = (EntityItem)paramVector1.elementAt(i);
/* 7605 */       ArrayList<?> arrayList2 = new ArrayList();
/* 7606 */       getCountriesAsList(entityItem, arrayList2, paramInt1);
/* 7607 */       addDebug("checkWarrCoverage plaAvail[" + i + "] " + entityItem.getKey() + " ctryList " + arrayList2);
/*      */ 
/*      */       
/* 7610 */       if (!arrayList.containsAll(arrayList2)) {
/*      */         
/* 7612 */         this.args[0] = paramEntityGroup.getLongDescription();
/* 7613 */         String str1 = getLD_NDN(paramEntityItem2);
/* 7614 */         if (paramEntityItem2.getEntityType().equals(getEntityType()) && paramEntityItem2.getEntityID() == getEntityID()) {
/* 7615 */           str1 = paramEntityItem2.getEntityGroup().getLongDescription();
/*      */         }
/* 7617 */         this.args[1] = str1;
/* 7618 */         this.args[2] = getLD_NDN(entityItem) + " " + 
/* 7619 */           PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */         
/* 7621 */         ArrayList arrayList3 = new ArrayList();
/* 7622 */         getCountriesAsList(entityItem, arrayList3, paramInt1);
/* 7623 */         arrayList3.removeAll(arrayList);
/* 7624 */         this.args[3] = getUnmatchedDescriptions(entityItem, "COUNTRYLIST", arrayList3);
/* 7625 */         createMessage(paramInt1, "MISSING_CTRY_ERR2", this.args);
/* 7626 */         arrayList3.clear();
/*      */       } 
/*      */ 
/*      */       
/* 7630 */       String str = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt1);
/* 7631 */       addDebug("checkWarrCoverage plaAvail " + entityItem.getKey() + " EFFECTIVEDATE " + str);
/* 7632 */       boolean bool = true;
/* 7633 */       Iterator<?> iterator1 = arrayList2.iterator();
/* 7634 */       StringBuffer stringBuffer = new StringBuffer();
/* 7635 */       while (iterator1.hasNext()) {
/* 7636 */         String str1 = (String)iterator1.next();
/* 7637 */         Vector<CoverageData> vector1 = (Vector)hashtable1.get(str1);
/* 7638 */         if (vector1 != null) {
/* 7639 */           CoverageData coverageData = vector1.firstElement();
/* 7640 */           String str2 = coverageData.pubfrom;
/* 7641 */           addDebug("checkWarrCoverage ctry " + str1 + " minEffDate " + str2 + " found on " + coverageData.item.getKey());
/*      */ 
/*      */           
/* 7644 */           bool = checkDates(str2, str, 2);
/* 7645 */           if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 7658 */             if (stringBuffer.length() > 0) {
/* 7659 */               stringBuffer.append(", ");
/*      */             }
/* 7661 */             stringBuffer.append(getUnmatchedDescriptions(paramEntityGroup, "COUNTRYLIST", str1));
/*      */           }  continue;
/*      */         } 
/* 7664 */         addDebug("checkWarrCoverage: No " + paramString + " found for country " + str1);
/*      */       } 
/*      */ 
/*      */       
/* 7668 */       if (stringBuffer.length() > 0) {
/*      */         
/* 7670 */         this.args[0] = paramEntityGroup.getLongDescription();
/* 7671 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityGroup, "EFFECTIVEDATE", "EFFECTIVEDATE");
/* 7672 */         String str1 = getLD_NDN(paramEntityItem2);
/* 7673 */         if (paramEntityItem2.getEntityType().equals(getEntityType()) && paramEntityItem2.getEntityID() == getEntityID()) {
/* 7674 */           str1 = paramEntityItem2.getEntityGroup().getLongDescription();
/*      */         }
/* 7676 */         this.args[2] = str1;
/* 7677 */         this.args[3] = getLD_NDN(entityItem);
/* 7678 */         this.args[4] = stringBuffer.toString();
/* 7679 */         createMessage(paramInt1, "PUBFROM_CTRY_ERR", this.args);
/*      */       } 
/*      */       
/* 7682 */       arrayList2.clear();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 7693 */     HashSet<EntityItem> hashSet = new HashSet();
/*      */     
/*      */     Enumeration<String> enumeration2;
/* 7696 */     for (enumeration2 = hashtable1.keys(); enumeration2.hasMoreElements(); ) {
/* 7697 */       String str1 = enumeration2.nextElement();
/*      */       
/* 7699 */       if (!arrayList1.contains(str1)) {
/* 7700 */         addDebug("checkWarrCoverage skipping gap test for flagCode " + str1 + ". it was not in the offering ctrys " + arrayList1);
/*      */         
/*      */         continue;
/*      */       } 
/* 7704 */       Vector<CoverageData> vector1 = (Vector)hashtable1.get(str1);
/*      */       
/* 7706 */       CoverageData coverageData = null;
/* 7707 */       TPIC tPIC = (TPIC)hashtable2.get(str1);
/* 7708 */       String str2 = tPIC.toDate;
/* 7709 */       String str3 = tPIC.fromDate;
/* 7710 */       addDebug("checkWarrCoverage flagCode " + str1 + " offering minFromCtryDate " + str3 + " maxToCtryDate " + str2);
/*      */ 
/*      */       
/* 7713 */       for (byte b = 0; b < vector1.size(); b++) {
/* 7714 */         CoverageData coverageData1 = vector1.elementAt(b);
/* 7715 */         if (b != 0)
/*      */         {
/* 7717 */           if (coverageData1.pubto.compareTo(str3) < 0 || coverageData1.pubfrom
/* 7718 */             .compareTo(str2) > 0) {
/* 7719 */             addDebug("checkWarrCoverage " + coverageData1 + " is outside offering range for " + str1 + " minFromCtryDate " + str3 + " maxToCtryDate " + str2);
/*      */           }
/*      */           else {
/*      */             
/* 7723 */             Date date1 = Date.valueOf(coverageData.pubto);
/* 7724 */             long l1 = date1.getTime();
/* 7725 */             long l2 = l1 + 86400000L;
/* 7726 */             Date date2 = new Date(l2);
/* 7727 */             Date date3 = Date.valueOf(coverageData1.pubfrom);
/*      */ 
/*      */             
/* 7730 */             if (date2.compareTo(date3) < 0) {
/* 7731 */               if (!hashSet.contains(coverageData.item) || 
/* 7732 */                 !hashSet.contains(coverageData1.item)) {
/* 7733 */                 hashSet.add(coverageData.item);
/* 7734 */                 hashSet.add(coverageData1.item);
/* 7735 */                 addDebug("checkWarrCoverage output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 7736 */                     .getKey());
/*      */ 
/*      */ 
/*      */                 
/* 7740 */                 this.args[0] = getLD_NDN(coverageData.item) + " for " + getLD_NDN(coverageData.warritem);
/* 7741 */                 this.args[1] = getLD_NDN(coverageData1.item) + " for " + getLD_NDN(coverageData1.warritem);
/* 7742 */                 createMessage(paramInt2, "DATE_RANGE_ERR2", this.args);
/*      */               } else {
/* 7744 */                 addDebug("checkWarrCoverage already output date range msg for " + coverageData.item.getKey() + " and " + coverageData1.item
/* 7745 */                     .getKey());
/*      */               } 
/*      */             }
/*      */           } 
/*      */         }
/*      */         
/* 7751 */         if (coverageData == null || coverageData.pubto.compareTo(coverageData1.pubto) < 0) {
/* 7752 */           coverageData = coverageData1;
/*      */         }
/*      */       } 
/*      */     } 
/* 7756 */     hashSet.clear();
/*      */ 
/*      */ 
/*      */     
/* 7760 */     for (enumeration2 = hashtable1.keys(); enumeration2.hasMoreElements(); ) {
/* 7761 */       String str = enumeration2.nextElement();
/* 7762 */       Vector<CoverageData> vector1 = (Vector)hashtable1.get(str);
/* 7763 */       for (byte b = 0; b < vector1.size(); b++) {
/* 7764 */         CoverageData coverageData = vector1.elementAt(b);
/* 7765 */         coverageData.setPubFromSort(false);
/*      */       } 
/* 7767 */       Collections.sort(vector1);
/*      */     } 
/*      */ 
/*      */     
/* 7771 */     for (byte b2 = 0; b2 < paramVector2.size(); b2++) {
/* 7772 */       EntityItem entityItem = (EntityItem)paramVector2.elementAt(b2);
/* 7773 */       String str = getAttrValueAndCheckLvl(entityItem, "EFFECTIVEDATE", paramInt1);
/*      */       
/* 7775 */       ArrayList arrayList2 = new ArrayList();
/* 7776 */       getCountriesAsList(entityItem, arrayList2, paramInt1);
/* 7777 */       addDebug("checkWarrCoverage loAvail[" + b2 + "] " + entityItem.getKey() + " EFFECTIVEDATE " + str + " ctryList " + arrayList2);
/*      */ 
/*      */       
/* 7780 */       boolean bool = true;
/* 7781 */       Iterator<String> iterator1 = arrayList2.iterator();
/* 7782 */       StringBuffer stringBuffer = new StringBuffer();
/* 7783 */       while (iterator1.hasNext()) {
/* 7784 */         String str1 = iterator1.next();
/*      */         
/* 7786 */         if (!arrayList1.contains(str1)) {
/* 7787 */           addDebug("checkWarrCoverage skipping enddate check ctry: " + str1 + ". it was not in the plannedavail offering ctrys " + arrayList1);
/*      */           
/*      */           continue;
/*      */         } 
/* 7791 */         Vector<CoverageData> vector1 = (Vector)hashtable1.get(str1);
/* 7792 */         if (vector1 != null) {
/* 7793 */           CoverageData coverageData = vector1.lastElement();
/* 7794 */           String str2 = coverageData.pubto;
/* 7795 */           addDebug("checkWarrCoverage ctry " + str1 + " maxEndDate " + str2 + " found on " + coverageData.item.getKey());
/*      */ 
/*      */           
/* 7798 */           bool = checkDates(str2, str, 1);
/* 7799 */           if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 7814 */             if (stringBuffer.length() > 0) {
/* 7815 */               stringBuffer.append(", ");
/*      */             }
/* 7817 */             stringBuffer.append(getUnmatchedDescriptions(paramEntityGroup, "COUNTRYLIST", str1));
/*      */           }  continue;
/*      */         } 
/* 7820 */         addDebug("checkWarrCoverage: No " + paramString + " found for country " + str1);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 7825 */       if (stringBuffer.length() > 0) {
/*      */         
/* 7827 */         this.args[0] = paramEntityGroup.getLongDescription();
/* 7828 */         this.args[1] = PokUtils.getAttributeDescription(paramEntityGroup, "ENDDATE", "ENDDATE");
/* 7829 */         String str1 = getLD_NDN(paramEntityItem2);
/* 7830 */         if (paramEntityItem2.getEntityType().equals(getEntityType()) && paramEntityItem2.getEntityID() == getEntityID()) {
/* 7831 */           str1 = paramEntityItem2.getEntityGroup().getLongDescription();
/*      */         }
/* 7833 */         this.args[2] = str1;
/* 7834 */         this.args[3] = getLD_NDN(entityItem);
/* 7835 */         this.args[4] = stringBuffer.toString();
/* 7836 */         createMessage(paramInt1, "PUBTO_CTRY_ERR", this.args);
/*      */       } 
/* 7838 */       arrayList2.clear();
/*      */     } 
/*      */ 
/*      */     
/* 7842 */     for (Enumeration<String> enumeration1 = hashtable1.keys(); enumeration1.hasMoreElements(); ) {
/* 7843 */       String str = enumeration1.nextElement();
/* 7844 */       Vector vector1 = (Vector)hashtable1.get(str);
/* 7845 */       vector1.clear();
/*      */     } 
/*      */     
/* 7848 */     for (byte b1 = 0; b1 < vector.size(); b1++) {
/* 7849 */       CoverageData coverageData = vector.elementAt(b1);
/* 7850 */       coverageData.dereference();
/*      */     } 
/*      */     
/* 7853 */     arrayList1.clear();
/* 7854 */     Iterator<String> iterator = hashtable2.keySet().iterator();
/* 7855 */     while (iterator.hasNext()) {
/* 7856 */       String str = iterator.next();
/* 7857 */       TPIC tPIC = (TPIC)hashtable2.get(str);
/* 7858 */       tPIC.dereference();
/*      */     } 
/* 7860 */     hashtable2.clear();
/*      */     
/* 7862 */     hashtable1.clear();
/* 7863 */     vector.clear();
/*      */     
/* 7865 */     arrayList.clear();
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
/*      */   protected void findWarrByCtry(EntityItem paramEntityItem, String paramString, Vector<EntityItem> paramVector1, ArrayList paramArrayList1, ArrayList paramArrayList2, Hashtable<String, Vector> paramHashtable, Vector<CoverageData> paramVector2, int paramInt) throws SQLException, MiddlewareException {
/* 7887 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString);
/* 7888 */     addDebug("findWarrByCtry entered  warrParent " + paramEntityItem.getKey() + " " + paramString + ".cnt " + paramVector1
/* 7889 */         .size());
/*      */     
/* 7891 */     Vector<EntityItem> vector = new Vector();
/* 7892 */     boolean bool = false;
/*      */     byte b;
/* 7894 */     for (b = 0; b < paramVector1.size(); b++) {
/* 7895 */       EntityItem entityItem1 = paramVector1.elementAt(b);
/* 7896 */       EntityItem entityItem2 = getDownLinkEntityItem(entityItem1, "WARR");
/* 7897 */       String str = PokUtils.getAttributeFlagValue(entityItem1, "DEFWARR");
/* 7898 */       addDebug("findWarrByCtry " + entityItem1.getKey() + " " + entityItem2.getKey() + " defwarr " + str);
/*      */ 
/*      */ 
/*      */       
/* 7902 */       if ("Y1".equals(str) && entityItem2.getUpLinkCount() > 1) {
/* 7903 */         String str1 = PokUtils.getAttributeDescription(entityGroup, "DEFWARR", "DEFWARR");
/*      */ 
/*      */         
/* 7906 */         for (byte b1 = 0; b1 < entityItem2.getUpLinkCount(); b1++) {
/* 7907 */           EntityItem entityItem = (EntityItem)entityItem2.getUpLink(b1);
/* 7908 */           String str2 = PokUtils.getAttributeFlagValue(entityItem, "DEFWARR");
/*      */           
/* 7910 */           this.args[0] = getLD_NDN(entityItem2) + " for " + (
/* 7911 */             "Y1".equals(str2) ? (str1 + " ") : "") + 
/* 7912 */             getLD_NDN(entityItem);
/* 7913 */           createMessage(paramInt, "DUPLICATE_ERR2", this.args);
/*      */         } 
/* 7915 */         bool = true;
/*      */       }
/*      */       else {
/*      */         
/* 7919 */         if (vector.contains(entityItem2)) {
/* 7920 */           if (warrRelatorOverlap(entityItem2, paramInt)) {
/* 7921 */             bool = true;
/*      */           }
/*      */         } else {
/* 7924 */           vector.add(entityItem2);
/*      */         } 
/*      */         
/* 7927 */         String str1 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 7928 */         if (str1.startsWith("<span ")) {
/* 7929 */           addDebug("findWarrByCtry " + entityItem1.getKey() + " EFFECTIVEDATE " + str1);
/*      */           
/* 7931 */           this.args[0] = getLD_NDN(paramEntityItem) + " " + getLD_NDN(entityItem1);
/* 7932 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "EFFECTIVEDATE", "EFFECTIVEDATE");
/* 7933 */           createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/* 7934 */           bool = true;
/*      */         } 
/* 7936 */         String str2 = PokUtils.getAttributeFlagValue(entityItem1, "COUNTRYLIST");
/* 7937 */         if (!"Y1".equals(str) && (str2 == null || str2.length() == 0)) {
/* 7938 */           addDebug("findWarrByCtry " + entityItem1.getKey() + " COUNTRYLIST " + str2);
/*      */           
/* 7940 */           this.args[0] = getLD_NDN(paramEntityItem) + " " + getLD_NDN(entityItem1);
/* 7941 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "COUNTRYLIST", "COUNTRYLIST");
/* 7942 */           createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/* 7943 */           bool = true;
/*      */         } 
/* 7945 */         str2 = PokUtils.getAttributeValue(entityItem1, "ENDDATE", "", "9999-12-31", false);
/* 7946 */         if (str2.startsWith("<span ")) {
/* 7947 */           addDebug("findWarrByCtry " + entityItem1.getKey() + " ENDDATE " + str2);
/*      */           
/* 7949 */           this.args[0] = getLD_NDN(paramEntityItem) + " " + getLD_NDN(entityItem1);
/* 7950 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "ENDDATE", "ENDDATE");
/* 7951 */           createMessage(paramInt, "REQ_NOTPOPULATED_ERR", this.args);
/* 7952 */           bool = true;
/*      */         } 
/*      */         
/* 7955 */         if (!bool) {
/*      */ 
/*      */ 
/*      */           
/* 7959 */           CoverageData coverageData = new CoverageData(entityItem1, paramInt);
/* 7960 */           paramVector2.add(coverageData);
/*      */           
/* 7962 */           if ("Y1".equals(str)) {
/*      */             
/* 7964 */             coverageData.ctryList.addAll(paramArrayList1);
/* 7965 */             paramArrayList2.addAll(paramArrayList1);
/*      */           } 
/*      */           
/* 7968 */           Iterator<String> iterator = coverageData.ctryList.iterator();
/* 7969 */           while (iterator.hasNext()) {
/* 7970 */             String str3 = iterator.next();
/* 7971 */             Vector<CoverageData> vector1 = (Vector)paramHashtable.get(str3);
/* 7972 */             if (vector1 == null) {
/* 7973 */               vector1 = new Vector();
/* 7974 */               paramHashtable.put(str3, vector1);
/*      */             } 
/* 7976 */             vector1.add(coverageData);
/*      */           } 
/* 7978 */           getCountriesAsList(entityItem1, paramArrayList2, paramInt);
/*      */         } 
/*      */       } 
/* 7981 */     }  if (bool) {
/* 7982 */       for (b = 0; b < paramVector2.size(); b++) {
/* 7983 */         CoverageData coverageData = paramVector2.elementAt(b);
/* 7984 */         coverageData.dereference();
/*      */       } 
/* 7986 */       paramVector2.clear();
/*      */     } else {
/*      */       
/* 7989 */       for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements();) {
/* 7990 */         Collections.sort(paramHashtable.get(enumeration.nextElement()));
/*      */       }
/*      */       
/* 7993 */       Collections.sort(paramVector2);
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
/*      */   private boolean warrRelatorOverlap(EntityItem paramEntityItem, int paramInt) throws SQLException, MiddlewareException {
/* 8015 */     boolean bool = false;
/* 8016 */     addDebug("warrRelatorOverlap entered " + paramEntityItem.getKey());
/*      */ 
/*      */     
/* 8019 */     Collections.sort(paramEntityItem.getUpLink(), (Comparator<?>)new AttrComparator("EFFECTIVEDATE"));
/*      */     
/* 8021 */     EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(0);
/* 8022 */     String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 8023 */     String str2 = PokUtils.getAttributeValue(entityItem, "ENDDATE", "", "9999-12-31", false);
/* 8024 */     ArrayList<?> arrayList = new ArrayList();
/* 8025 */     getCountriesAsList(entityItem, arrayList, paramInt);
/*      */     
/* 8027 */     for (byte b = 1; b < paramEntityItem.getUpLinkCount(); b++) {
/* 8028 */       EntityItem entityItem1 = (EntityItem)paramEntityItem.getUpLink(b);
/* 8029 */       String str3 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 8030 */       String str4 = PokUtils.getAttributeValue(entityItem1, "ENDDATE", "", "9999-12-31", false);
/* 8031 */       ArrayList<?> arrayList1 = new ArrayList();
/* 8032 */       getCountriesAsList(entityItem1, arrayList1, paramInt);
/* 8033 */       addDebug("warrRelatorOverlap rel[" + (b - 1) + "] " + entityItem.getKey() + " prevWarrEffdate " + str1 + " prevWarrEnddate " + str2 + " prevCtryList " + arrayList + " rel[" + b + "] " + entityItem1
/*      */           
/* 8035 */           .getKey() + " warrEffdate " + str3 + " warrEnddate " + str4 + " ctryList " + arrayList1);
/*      */ 
/*      */ 
/*      */       
/* 8039 */       arrayList.retainAll(arrayList1);
/* 8040 */       if (arrayList.size() > 0 && str3
/* 8041 */         .compareTo(str2) <= 0) {
/* 8042 */         for (byte b1 = 0; b1 < paramEntityItem.getUpLinkCount(); b1++) {
/* 8043 */           EntityItem entityItem2 = (EntityItem)paramEntityItem.getUpLink(b1);
/*      */           
/* 8045 */           this.args[0] = getLD_NDN(paramEntityItem) + " for " + getLD_NDN(entityItem2);
/* 8046 */           createMessage(paramInt, "DUPLICATE_ERR2", this.args);
/*      */         } 
/* 8048 */         bool = true;
/*      */         break;
/*      */       } 
/* 8051 */       str1 = str3;
/* 8052 */       str2 = str4;
/* 8053 */       arrayList = arrayList1;
/*      */     } 
/*      */     
/* 8056 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isHardwareOrHIPOModel(EntityItem paramEntityItem) {
/* 8067 */     boolean bool = false;
/* 8068 */     if ("MODEL".equals(paramEntityItem.getEntityType())) {
/* 8069 */       String str = getAttributeFlagEnabledValue(paramEntityItem, "COFCAT");
/* 8070 */       if ("100".equals(str) || isHIPOModel(paramEntityItem)) {
/* 8071 */         bool = true;
/*      */       }
/* 8073 */       addDebug("isHardwareOrHIPOModel: " + bool + " for " + paramEntityItem.getKey() + " COFCAT " + str);
/*      */     } 
/* 8075 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isHIPOModel(EntityItem paramEntityItem) {
/* 8084 */     boolean bool = false;
/* 8085 */     if ("MODEL".equals(paramEntityItem.getEntityType())) {
/* 8086 */       String str1 = getAttributeFlagEnabledValue(paramEntityItem, "MACHTYPEATR");
/* 8087 */       String str2 = getAttributeFlagEnabledValue(paramEntityItem, "MODELATR");
/* 8088 */       if (("5313".equals(str1) && "HPO".equals(str2)) || ("5372"
/* 8089 */         .equals(str1) && "IS5".equals(str2))) {
/* 8090 */         bool = true;
/*      */       }
/* 8092 */       addDebug("isHIPOModel: " + bool + " for " + paramEntityItem.getKey() + " MACHTYPEATR " + str1 + " MODELATR " + str2);
/*      */     } 
/*      */     
/* 8095 */     return bool;
/*      */   }
/*      */   
/*      */   protected abstract void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception;
/*      */   
/* 8100 */   protected class CoverageData implements Comparable { EntityItem item = null;
/* 8101 */     EntityItem warritem = null;
/* 8102 */     String pubto = null;
/* 8103 */     String pubfrom = null;
/*      */     boolean pubfromSort = true;
/* 8105 */     ArrayList ctryList = new ArrayList();
/*      */ 
/*      */     
/*      */     CoverageData(EntityItem param1EntityItem, int param1Int) throws SQLException, MiddlewareException {
/* 8109 */       this.item = param1EntityItem;
/* 8110 */       DQABRSTATUS.this.getCountriesAsList(this.item, this.ctryList, param1Int);
/* 8111 */       if (this.item.getEntityType().endsWith("WARR")) {
/* 8112 */         this.pubto = PokUtils.getAttributeValue(this.item, "ENDDATE", "", "9999-12-31", false);
/* 8113 */         this.pubfrom = PokUtils.getAttributeValue(this.item, "EFFECTIVEDATE", "", "1980-01-01", false);
/* 8114 */         this.warritem = DQABRSTATUS.this.getDownLinkEntityItem(this.item, "WARR");
/* 8115 */         DQABRSTATUS.this.addDebug("CoverageData " + this.item.getKey() + " " + this.warritem.getKey() + " effdate " + this.pubfrom + " enddate " + this.pubto + " ctryList " + this.ctryList);
/*      */       } else {
/*      */         
/* 8118 */         this.pubto = PokUtils.getAttributeValue(this.item, "PUBTO", "", "9999-12-31", false);
/* 8119 */         this.pubfrom = PokUtils.getAttributeValue(this.item, "PUBFROM", "", "1980-01-01", false);
/* 8120 */         DQABRSTATUS.this.addDebug("CoverageData " + this.item.getKey() + " pubfrom " + this.pubfrom + " pubto " + this.pubto + " ctryList " + this.ctryList);
/*      */       } 
/*      */     }
/*      */     void setPubFromSort(boolean param1Boolean) {
/* 8124 */       this.pubfromSort = param1Boolean;
/*      */     } void dereference() {
/* 8126 */       this.item = null;
/* 8127 */       this.warritem = null;
/* 8128 */       this.pubto = null;
/* 8129 */       this.pubfrom = null;
/* 8130 */       if (this.ctryList != null) {
/* 8131 */         this.ctryList.clear();
/* 8132 */         this.ctryList = null;
/*      */       } 
/*      */     }
/*      */     public int compareTo(Object param1Object) {
/* 8136 */       if (this.pubfromSort)
/*      */       {
/* 8138 */         return this.pubfrom.compareTo(((CoverageData)param1Object).pubfrom);
/*      */       }
/* 8140 */       return this.pubto.compareTo(((CoverageData)param1Object).pubto);
/*      */     }
/*      */     
/*      */     public String toString() {
/* 8144 */       return this.item.getKey() + " pubfrom " + this.pubfrom + " pubto " + this.pubto + " ctryList " + this.ctryList;
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class NDN {
/*      */     private String etype;
/*      */     private String tag;
/*      */     private NDN next;
/* 8152 */     private Vector attrVct = new Vector();
/*      */     NDN(String param1String1, String param1String2) {
/* 8154 */       this.etype = param1String1;
/* 8155 */       this.tag = param1String2;
/*      */     }
/* 8157 */     String getTag() { return this.tag; }
/* 8158 */     String getEntityType() { return this.etype; } Vector getAttr() {
/* 8159 */       return this.attrVct;
/*      */     } void addAttr(String param1String) {
/* 8161 */       this.attrVct.addElement(param1String);
/*      */     }
/* 8163 */     void setNext(NDN param1NDN) { this.next = param1NDN; } NDN getNext() {
/* 8164 */       return this.next;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected static class TPIC
/*      */     implements Comparable
/*      */   {
/*      */     boolean fromSort = true;
/* 8173 */     String ctry = null;
/* 8174 */     EntityItem fromItem = null;
/* 8175 */     EntityItem toItem = null;
/* 8176 */     String fromDate = null;
/* 8177 */     String toDate = null;
/*      */     TPIC(String param1String1, String param1String2) {
/* 8179 */       this(param1String1, param1String2, "9999-12-31");
/*      */     }
/*      */     TPIC(String param1String1, String param1String2, String param1String3) {
/* 8182 */       this.ctry = param1String1;
/* 8183 */       this.fromDate = param1String2;
/* 8184 */       this.toDate = param1String3;
/*      */     }
/*      */     TPIC getOverlay(TPIC param1TPIC) {
/* 8187 */       TPIC tPIC = null;
/* 8188 */       if (param1TPIC != null && param1TPIC.ctry.equals(this.ctry) && 
/* 8189 */         param1TPIC.fromDate.compareTo(this.toDate) < 0 && param1TPIC.toDate
/* 8190 */         .compareTo(this.fromDate) > 0) {
/*      */         
/* 8192 */         String str1 = this.fromDate;
/* 8193 */         String str2 = this.toDate;
/* 8194 */         if (param1TPIC.fromDate.compareTo(this.fromDate) > 0) {
/* 8195 */           str1 = param1TPIC.fromDate;
/*      */         }
/* 8197 */         if (param1TPIC.toDate.compareTo(this.toDate) < 0) {
/* 8198 */           str2 = param1TPIC.toDate;
/*      */         }
/*      */         
/* 8201 */         tPIC = new TPIC(this.ctry, str1, str2);
/*      */       } 
/*      */       
/* 8204 */       return tPIC;
/*      */     }
/*      */     public String toString() {
/* 8207 */       return "\nctry:" + this.ctry + " fromDate:" + this.fromDate + " toDate:" + this.toDate;
/*      */     } void setFromSort(boolean param1Boolean) {
/* 8209 */       this.fromSort = param1Boolean;
/*      */     } public int compareTo(Object param1Object) {
/* 8211 */       if (this.fromSort)
/*      */       {
/* 8213 */         return this.fromDate.compareTo(((TPIC)param1Object).fromDate);
/*      */       }
/* 8215 */       return this.toDate.compareTo(((TPIC)param1Object).toDate);
/*      */     }
/*      */     
/*      */     void dereference() {
/* 8219 */       this.ctry = null;
/* 8220 */       this.fromDate = null;
/* 8221 */       this.toDate = null;
/* 8222 */       this.fromItem = null;
/* 8223 */       this.toItem = null;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\DQABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */