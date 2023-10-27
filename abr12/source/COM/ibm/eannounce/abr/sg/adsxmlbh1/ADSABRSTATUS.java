/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.UpdateXML;
/*      */ import COM.ibm.eannounce.abr.util.XMLElem;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANEntity;
/*      */ import COM.ibm.eannounce.objects.EANFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MQUsage;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.MultipleFlag;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import com.ibm.mq.MQException;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.sql.Statement;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.ParseException;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.text.StringCharacterIterator;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.Vector;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.TransformerException;
/*      */ import javax.xml.transform.TransformerFactory;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ADSABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  394 */   private StringBuffer rptSb = new StringBuffer();
/*  395 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  396 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   protected static final String ADSMQSERIES = "ADSMQSERIES";
/*      */   
/*      */   protected static final String SYSFEEDRESEND_YES = "Yes";
/*      */   
/*      */   protected static final String SYSFEEDRESEND_NO = "No";
/*      */   
/*      */   protected static final String SYSFEEDRESEND_RFR = "RFR";
/*      */   protected static final String SYSFEEDRESEND_CACHE = "CACHE";
/*      */   protected static final String SYSFEEDRESEND_CURRENT = "CUR";
/*      */   protected static final String MQUEUE_ATTR = "XMLABRPROPFILE";
/*      */   protected static final String ADSATTRIBUTE = "ADSATTRIBUTE";
/*      */   protected static final String OLDEFFECTDATE = "2010-03-01";
/*  410 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  411 */   private StringBuffer reason = new StringBuffer();
/*  412 */   private PrintWriter dbgPw = null;
/*  413 */   private PrintWriter userxmlPw = null;
/*  414 */   private String dbgfn = null;
/*  415 */   private String userxmlfn = null;
/*  416 */   private int userxmlLen = 0;
/*  417 */   private int dbgLen = 0;
/*      */   
/*      */   public static final int MAXFILE_SIZE = 5000000;
/*      */   
/*      */   protected static final String STATUS_DRAFT = "0010";
/*      */   
/*      */   protected static final String STATUS_FINAL = "0020";
/*      */   
/*      */   protected static final String STATUS_R4REVIEW = "0040";
/*      */   
/*      */   protected static final String STATUS_CHGREQ = "0050";
/*      */   
/*      */   protected static final String STATUS_QUEUE = "0020";
/*      */   
/*      */   protected static final String STATUS_PASSED = "0030";
/*      */   protected static final String STATUS_PASSEDRESENDRFR = "XMLRFR";
/*      */   protected static final String STATUS_PASSEDRESENDCACHE = "XMLCACHE";
/*      */   protected static final String CHEAT = "@@";
/*  435 */   private Hashtable metaTbl = new Hashtable<>();
/*      */ 
/*      */   
/*  438 */   private static final Hashtable NDN_TBL = new Hashtable<>(); static {
/*  439 */     NDN nDN1 = new NDN("MODEL", "(TM)");
/*  440 */     nDN1.addAttr("MACHTYPEATR");
/*  441 */     nDN1.addAttr("MODELATR");
/*  442 */     nDN1.addAttr("COFCAT");
/*  443 */     nDN1.addAttr("COFSUBCAT");
/*  444 */     nDN1.addAttr("COFGRP");
/*  445 */     nDN1.addAttr("COFSUBGRP");
/*  446 */     NDN nDN2 = new NDN("FEATURE", "(FC)");
/*  447 */     nDN2.addAttr("FEATURECODE");
/*  448 */     nDN1.setNext(nDN2);
/*  449 */     NDN_TBL.put("PRODSTRUCT", nDN1);
/*      */     
/*  451 */     nDN1 = new NDN("MODEL", "(TM)");
/*  452 */     nDN1.addAttr("MACHTYPEATR");
/*  453 */     nDN1.addAttr("MODELATR");
/*  454 */     nDN1.addAttr("COFCAT");
/*  455 */     nDN1.addAttr("COFSUBCAT");
/*  456 */     nDN1.addAttr("COFGRP");
/*  457 */     nDN1.addAttr("COFSUBGRP");
/*  458 */     nDN2 = new NDN("SWFEATURE", "(FC)");
/*  459 */     nDN2.addAttr("FEATURECODE");
/*  460 */     nDN1.setNext(nDN2);
/*  461 */     NDN_TBL.put("SWPRODSTRUCT", nDN1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  470 */   protected static final String[][] VE_Filter_Array = new String[][] { { "ADSLSEO", "AVAIL", "RFR Final" }, { "ADSLSEO", "ANNOUNCEMENT", "RFR Final" }, { "ADSLSEO", "FB", "RFR Final" }, { "ADSLSEO", "MM", "RFR Final" }, { "ADSLSEO", "BHCATLGOR", "Final" }, { "ADSLSEO2", "AVAIL", "RFR Final" }, { "ADSLSEO2", "ANNOUNCEMENT", "RFR Final" }, { "ADSLSEOBUNDLE", "AVAIL", "RFR Final" }, { "ADSLSEOBUNDLE", "ANNOUNCEMENT", "RFR Final" }, { "ADSLSEOBUNDLE", "FB", "RFR Final" }, { "ADSLSEOBUNDLE", "MM", "RFR Final" }, { "ADSLSEOBUNDLE", "BHCATLGOR", "Final" }, { "ADSMODEL", "AVAIL", "RFR Final" }, { "ADSMODEL", "ANNOUNCEMENT", "RFR Final" }, { "ADSMODEL", "FB", "RFR Final" }, { "ADSMODEL", "MM", "RFR Final" }, { "ADSMODEL", "BHCATLGOR", "Final" }, { "ADSPRODSTRUCT", "AVAIL", "RFR Final" }, { "ADSPRODSTRUCT", "ANNOUNCEMENT", "RFR Final" }, { "ADSPRODSTRUCT", "BHCATLGOR", "Final" }, { "ADSSWPRODSTRUCT", "AVAIL", "RFR Final" }, { "ADSSWPRODSTRUCT", "ANNOUNCEMENT", "RFR Final" }, { "ADSSWPRODSTRUCT", "BHCATLGOR", "Final" }, { "ADSSVCMOD", "AVAIL", "RFR Final" }, { "ADSSVCMOD", "ANNOUNCEMENT", "RFR Final" }, { "ADSMODELCONVERT", "AVAIL", "RFR Final" }, { "ADSMODELCONVERT", "ANNOUNCEMENT", "RFR Final" }, { "ADSPRODSTRUCT2", "AVAIL", "RFR Final" }, { "ADSPRODSTRUCT2", "ANNOUNCEMENT", "RFR Final" }, { "ADSSWPRODSTRUCT2", "AVAIL", "RFR Final" }, { "ADSSWPRODSTRUCT2", "ANNOUNCEMENT", "RFR Final" }, { "ADSCATNAV", "IMG", "RFR Final" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  531 */   protected static final String[][] VE_Country_Filter_Array = new String[][] { { "ADSLSEO", "FB" }, { "ADSLSEO", "IMG" }, { "ADSLSEO", "MM" }, { "ADSLSEOBUNDLE", "FB" }, { "ADSLSEOBUNDLE", "IMG" }, { "ADSLSEOBUNDLE", "MM" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  543 */   private static final Hashtable FILTER_TBL = new Hashtable<>(); static {
/*  544 */     FILTER_TBL.put("CATNAV", new String[] { "", "FLFILSYSINDC", "" });
/*  545 */     FILTER_TBL.put("FCTRANSACTION", new String[] { "", "", "WTHDRWEFFCTVDATE" });
/*  546 */     FILTER_TBL.put("FEATURE", new String[] { "", "", "WITHDRAWDATEEFF_T" });
/*  547 */     FILTER_TBL.put("LSEO", new String[] { "COFCAT", "FLFILSYSINDC", "LSEOUNPUBDATEMTRGT" });
/*  548 */     FILTER_TBL.put("LSEOBUNDLE", new String[] { "BUNDLETYPE", "FLFILSYSINDC", "BUNDLUNPUBDATEMTRGT" });
/*  549 */     FILTER_TBL.put("MODEL", new String[] { "COFCAT", "FLFILSYSINDC", "WTHDRWEFFCTVDATE" });
/*  550 */     FILTER_TBL.put("MODELCONVERT", new String[] { "", "", "WTHDRWEFFCTVDATE" });
/*  551 */     FILTER_TBL.put("PRODSTRUCT", new String[] { "", "FLFILSYSINDC", "WTHDRWEFFCTVDATE" });
/*  552 */     FILTER_TBL.put("SVCMOD", new String[] { "SVCMODGRP", "", "WTHDRWEFFCTVDATE" });
/*  553 */     FILTER_TBL.put("SWFEATURE", new String[] { "", "", "WITHDRAWDATEEFF_T" });
/*  554 */     FILTER_TBL.put("SWPRODSTRUCT", new String[] { "", "", "WTHDRWEFFCTVDATE" });
/*  555 */     FILTER_TBL.put("REFOFER", new String[] { "", "", "ENDOFSVC" });
/*  556 */     FILTER_TBL.put("REFOFERFEAT", new String[] { "", "", "ENDOFSVC" });
/*      */   }
/*  558 */   private ResourceBundle rsBundle = null;
/*  559 */   private String priorStatus = "&nbsp;";
/*  560 */   private String curStatus = "&nbsp;";
/*  561 */   private String curStatusvalue = null;
/*      */   
/*      */   private boolean isPeriodicABR = false;
/*      */   private boolean isSystemResend = false;
/*      */   private boolean isSystemResendRFR = false;
/*      */   private boolean isSystemResendCache = false;
/*      */   private boolean isSystemResendCurrent = false;
/*      */   private boolean isSystemResendCacheExist = false;
/*  569 */   private String SystemResendCacheXml = "";
/*      */   
/*      */   private boolean isXMLIDLABR = false;
/*      */   
/*      */   private boolean isXMLCACHE = false;
/*      */   private boolean isXMLADSABR = false;
/*      */   private boolean isIERPADSABR = false;
/*  576 */   private String actionTaken = "";
/*      */ 
/*      */   
/*      */   protected static final Hashtable READ_LANGS_TBL;
/*      */   
/*      */   private static final Hashtable ABR_TBL;
/*      */   
/*      */   protected static final Hashtable ADSTYPES_TBL;
/*      */   
/*      */   protected static final Hashtable ITEM_STATUS_ATTR_TBL;
/*      */   
/*      */   protected static final Hashtable ITEM_COUNTRY_ATTR_TBL;
/*      */   
/*      */   protected static final Hashtable ABR_ATTR_TBL;
/*      */   
/*      */   protected static final String SYSFeedResendValue = "_SYSFeedResendValue";
/*      */   
/*  593 */   public static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("ADSABRSTATUS");
/*  594 */   public static int MAX_LVL = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  600 */   private static String ADSABRSTATUS_USERXML_ADSXMLSETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_ADSXMLSETUP", "ON");
/*      */   
/*  602 */   private static String ADSABRSTATUS_USERXML_XMLPRODPRICESETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLPRODPRICESETUP", "OFF");
/*      */   
/*  604 */   private static String ADSABRSTATUS_USERXML_XMLCOMPATSETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLCOMPATSETUP", "OFF");
/*      */   
/*  606 */   private static String ADSABRSTATUS_USERXML_XMLXLATESETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLXLATESETUP", "ON");
/*      */   
/*      */   protected static boolean USERXML_OFF_LOG = false;
/*      */   
/*      */   protected static final Hashtable ABR_VERSION_TBL;
/*      */   
/*      */   private static final Hashtable PERIODIC_ABR_TBL;
/*      */   
/*      */   static {
/*  615 */     ABR_TBL = new Hashtable<>();
/*  616 */     ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSMODELABR");
/*  617 */     ABR_TBL.put("FEATURE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSFEATUREABR");
/*  618 */     ABR_TBL.put("SWFEATURE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSWFEATUREABR");
/*  619 */     ABR_TBL.put("WARR", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSWARRABR");
/*  620 */     ABR_TBL.put("REVUNBUNDCOMP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSUNBUNDCOMPABR");
/*      */     
/*  622 */     ABR_TBL.put("FCTRANSACTION", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSFCTRANSABR");
/*  623 */     ABR_TBL.put("MODELCONVERT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSMODELCONVERTABR");
/*  624 */     ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSPRODSTRUCTABR");
/*  625 */     ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSWPRODSTRUCTABR");
/*      */     
/*  627 */     ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSLSEOABR");
/*  628 */     ABR_TBL.put("SVCMOD", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSVCMODABR");
/*  629 */     ABR_TBL.put("SLEORGNPLNTCODE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSLEORGNPLNTCODEABR");
/*  630 */     ABR_TBL.put("GBT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSGBTABR");
/*      */     
/*  632 */     ABR_TBL.put("IMG", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSIMGABR");
/*  633 */     ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSLSEOBUNDLEABR");
/*  634 */     ABR_TBL.put("CATNAV", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSCATNAVABR");
/*      */     
/*  636 */     ABR_TBL.put("SVCLEV", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSVCLEVABR");
/*      */     
/*  638 */     ABR_TBL.put("GENERALAREA", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSGENAREAABR");
/*      */ 
/*      */     
/*  641 */     ABR_TBL.put("XMLPRODPRICESETUP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSPRICEABR");
/*  642 */     ABR_TBL.put("XMLCOMPATSETUP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSWWCOMPATXMLABR");
/*  643 */     ABR_TBL.put("XMLXLATESETUP", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSXLATEABR");
/*      */     
/*  645 */     ABR_TBL.put("REFOFER", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSREFOFERABR");
/*  646 */     ABR_TBL.put("REFOFERFEAT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSREFOFERFEATABR");
/*      */     
/*  648 */     ABR_TBL.put("SWSPRODSTRUCT", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSWSPRODSTRUCTABR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  661 */     READ_LANGS_TBL = new Hashtable<>();
/*      */     
/*  663 */     READ_LANGS_TBL.put("" + Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
/*  664 */     READ_LANGS_TBL.put("" + Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
/*  665 */     READ_LANGS_TBL.put("" + Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
/*  666 */     READ_LANGS_TBL.put("" + Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
/*  667 */     READ_LANGS_TBL.put("" + Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
/*  668 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
/*  669 */     READ_LANGS_TBL.put("" + Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
/*  670 */     READ_LANGS_TBL.put("" + Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
/*  671 */     READ_LANGS_TBL.put("" + Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
/*  672 */     READ_LANGS_TBL.put("" + Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
/*  673 */     READ_LANGS_TBL.put("" + Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
/*  674 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
/*  675 */     READ_LANGS_TBL.put("" + Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);
/*      */     
/*  677 */     ADSTYPES_TBL = new Hashtable<>();
/*  678 */     ADSTYPES_TBL.put("10", "CATNAV");
/*  679 */     ADSTYPES_TBL.put("100", "SVCPRODSTRUCT");
/*  680 */     ADSTYPES_TBL.put("110", "SWFEATURE");
/*  681 */     ADSTYPES_TBL.put("120", "SWFPRODSTRUCT");
/*  682 */     ADSTYPES_TBL.put("130", "MODELCG");
/*  683 */     ADSTYPES_TBL.put("140", "LSEO");
/*  684 */     ADSTYPES_TBL.put("150", "SVCMOD");
/*  685 */     ADSTYPES_TBL.put("160", "WARR");
/*  686 */     ADSTYPES_TBL.put("170", "REVUNBUNDCOMP");
/*  687 */     ADSTYPES_TBL.put("180", "SWSPRODSTRUCT");
/*  688 */     ADSTYPES_TBL.put("20", "GENERALAREA");
/*  689 */     ADSTYPES_TBL.put("30", "FEATURE");
/*      */ 
/*      */ 
/*      */     
/*  693 */     ADSTYPES_TBL.put("60", "MODEL");
/*  694 */     ADSTYPES_TBL.put("70", "MODELCONVERT");
/*  695 */     ADSTYPES_TBL.put("80", "PRODSTRUCT");
/*  696 */     ADSTYPES_TBL.put("90", "SVCFEATURE");
/*      */ 
/*      */ 
/*      */     
/*  700 */     ITEM_STATUS_ATTR_TBL = new Hashtable<>();
/*  701 */     ITEM_STATUS_ATTR_TBL.put("AVAIL", "STATUS");
/*  702 */     ITEM_STATUS_ATTR_TBL.put("ANNOUNCEMENT", "ANNSTATUS");
/*  703 */     ITEM_STATUS_ATTR_TBL.put("IMG", "STATUS");
/*  704 */     ITEM_STATUS_ATTR_TBL.put("MM", "MMSTATUS");
/*  705 */     ITEM_STATUS_ATTR_TBL.put("FB", "FBSTATUS");
/*  706 */     ITEM_STATUS_ATTR_TBL.put("FEATURE", "STATUS");
/*  707 */     ITEM_STATUS_ATTR_TBL.put("SWFEATURE", "STATUS");
/*  708 */     ITEM_STATUS_ATTR_TBL.put("LSEO", "STATUS");
/*  709 */     ITEM_STATUS_ATTR_TBL.put("BHCATLGOR", "STATUS");
/*      */ 
/*      */ 
/*      */     
/*  713 */     ITEM_COUNTRY_ATTR_TBL = new Hashtable<>();
/*  714 */     ITEM_COUNTRY_ATTR_TBL.put("LSEO", "COUNTRYLIST");
/*  715 */     ITEM_COUNTRY_ATTR_TBL.put("LSEOBUNDLE", "COUNTRYLIST");
/*  716 */     ITEM_COUNTRY_ATTR_TBL.put("IMG", "COUNTRYLIST");
/*  717 */     ITEM_COUNTRY_ATTR_TBL.put("MM", "COUNTRYLIST");
/*  718 */     ITEM_COUNTRY_ATTR_TBL.put("FB", "COUNTRYLIST");
/*      */ 
/*      */ 
/*      */     
/*  722 */     ABR_ATTR_TBL = new Hashtable<>();
/*  723 */     ABR_ATTR_TBL.put("ANNOUNCEMENT", "ANNABRSTATUS");
/*  724 */     ABR_ATTR_TBL.put("AVAIL", "AVAILABRSTATUS");
/*  725 */     ABR_ATTR_TBL.put("FEATURE", "FCABRSTATUS");
/*  726 */     ABR_ATTR_TBL.put("FCTRANSACTION", "FCTRANSABRSTATUS");
/*  727 */     ABR_ATTR_TBL.put("LSEO", "LSEOABRSTATUS");
/*  728 */     ABR_ATTR_TBL.put("LSEOBUNDLE", "LSEOBDLABRSTATUS");
/*  729 */     ABR_ATTR_TBL.put("MODELCONVERT", "MDLCNTABRSTATTUS");
/*  730 */     ABR_ATTR_TBL.put("SWFEATURE", "SWFCABRSTATUS");
/*  731 */     ABR_ATTR_TBL.put("IPSCFEAT", "IPSCFEATABRSTATUS");
/*  732 */     ABR_ATTR_TBL.put("IPSCSTRUC", "IPSCSTRUCABRSTATUS");
/*  733 */     ABR_ATTR_TBL.put("SVCMOD", "SVCMODABRSTATUS");
/*  734 */     ABR_ATTR_TBL.put("WWSEO", "WWSEOABRSTATUS");
/*  735 */     ABR_ATTR_TBL.put("MODEL", "MODELABRSTATUS");
/*  736 */     ABR_ATTR_TBL.put("PRODSTRUCT", "PRODSTRUCTABRSTATUS");
/*  737 */     ABR_ATTR_TBL.put("SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
/*  738 */     ABR_ATTR_TBL.put("SWSPRODSTRUCT", "SWSPRODSTRUCTABRSTATUS");
/*      */ 
/*      */ 
/*      */     
/*  742 */     ABR_VERSION_TBL = new Hashtable<>();
/*  743 */     ABR_VERSION_TBL.put("MODEL05", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSMODEL05ABR");
/*  744 */     ABR_VERSION_TBL.put("LSEO05", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSLSEO05ABR");
/*  745 */     ABR_VERSION_TBL.put("SVCMOD05", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSSVCMOD05ABR");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  750 */     PERIODIC_ABR_TBL = new Hashtable<>();
/*  751 */     PERIODIC_ABR_TBL.put("ADSXMLSETUP", "ADSABRSTATUS");
/*  752 */     PERIODIC_ABR_TBL.put("XMLPRODPRICESETUP", "ADSPPABRSTATUS");
/*  753 */     PERIODIC_ABR_TBL.put("XMLCOMPATSETUP", "ADSABRSTATUS");
/*  754 */     PERIODIC_ABR_TBL.put("XMLXLATESETUP", "ADSABRSTATUS");
/*      */   }
/*      */   
/*  757 */   private Object[] args = (Object[])new String[10];
/*  758 */   private String abrversion = "";
/*  759 */   private String t2DTS = "&nbsp;";
/*  760 */   private String t1DTS = "&nbsp;";
/*      */   
/*  762 */   private StringBuffer userxmlSb = new StringBuffer();
/*  763 */   private Vector extxmlfeedVct = new Vector();
/*  764 */   private Vector succQueueNameVct = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getSimpleABRName(String paramString) {
/*  797 */     String str = (String)ABR_TBL.get(paramString);
/*  798 */     addDebug("creating instance of ADSABR  = '" + str + "' for " + paramString);
/*  799 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  808 */     String str1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  815 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  820 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  823 */       String str = "";
/*  824 */       long l = System.currentTimeMillis();
/*      */       
/*  826 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  829 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  832 */       setReturnCode(0);
/*      */ 
/*      */       
/*  835 */       this.extxmlfeedVct.clear();
/*  836 */       this.succQueueNameVct.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  841 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  843 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*      */       try {
/*  847 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  852 */         String str6 = getEntityType();
/*  853 */         if (this.m_abri.getABRCode().equals("SVCMODIERPABRSTATUS") && "SVCMOD".equals(str6)) {
/*  854 */           str6 = "SVCMODIERPABRSTATUS";
/*      */         }
/*  856 */         this.isPeriodicABR = PERIODIC_ABR_TBL.containsKey(str6);
/*  857 */         if (this.isPeriodicABR) {
/*      */           
/*  859 */           if ("ADSXMLSETUP".equals(str6)) {
/*  860 */             if ("OFF".equals(ADSABRSTATUS_USERXML_ADSXMLSETUP)) {
/*  861 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  863 */               USERXML_OFF_LOG = false;
/*      */             } 
/*  865 */           } else if ("XMLPRODPRICESETUP".equals(str6)) {
/*  866 */             if ("OFF".equals(ADSABRSTATUS_USERXML_XMLPRODPRICESETUP)) {
/*  867 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  869 */               USERXML_OFF_LOG = false;
/*      */             } 
/*  871 */           } else if ("XMLCOMPATSETUP".equals(str6)) {
/*  872 */             if ("OFF".equals(ADSABRSTATUS_USERXML_XMLCOMPATSETUP)) {
/*  873 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  875 */               USERXML_OFF_LOG = false;
/*      */             } 
/*  877 */           } else if ("XMLXLATESETUP".equals(str6)) {
/*  878 */             if ("OFF".equals(ADSABRSTATUS_USERXML_XMLXLATESETUP)) {
/*  879 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  881 */               USERXML_OFF_LOG = false;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  885 */           USERXML_OFF_LOG = false;
/*      */         } 
/*      */         
/*  888 */         String str7 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*  889 */         String str8 = PokUtils.getAttributeFlagValue(entityItem, "ADSENTITY");
/*      */         
/*  891 */         if (this.isPeriodicABR) {
/*      */           
/*  893 */           if (str7 != null) {
/*  894 */             str6 = (String)ADSTYPES_TBL.get(str7);
/*      */           }
/*  896 */           if ("20".equals(str8)) {
/*  897 */             str6 = "DEL" + str6;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  904 */         String str9 = getSimpleABRName(str6);
/*  905 */         if (str9 != null) {
/*  906 */           boolean bool = true;
/*  907 */           XMLMQ xMLMQ = (XMLMQ)Class.forName(str9).newInstance();
/*  908 */           str = xMLMQ.getMQCID();
/*  909 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  916 */           if (this.m_abri.getABRCode().equals("XMLIDLABRSTATUS")) {
/*  917 */             this.isXMLIDLABR = true;
/*  918 */             this.isXMLCACHE = true;
/*  919 */             String str11 = getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*      */             
/*  921 */             this.isSystemResendCurrent = "CUR".equals(str11);
/*  922 */             addDebugComment(2, "isSystemResendCurrent=" + this.isSystemResendCurrent + ";sysfeedFlag=" + str11);
/*  923 */           } else if (this.m_abri.getABRCode().equals("ADSABRSTATUS")) {
/*  924 */             this.isXMLADSABR = true;
/*      */           } 
/*  926 */           if (!this.isPeriodicABR) {
/*  927 */             String str11 = xMLMQ.getStatusAttr();
/*  928 */             String str12 = getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*  929 */             String str13 = getAttributeFlagEnabledValue(entityItem, str11);
/*  930 */             this.isSystemResend = "Yes".equals(str12);
/*  931 */             this.isSystemResendRFR = "RFR".equals(str12);
/*      */             
/*  933 */             this.isSystemResendCache = "CACHE".equals(str12);
/*  934 */             this.isSystemResendCurrent = "CUR".equals(str12);
/*      */             
/*  936 */             addDebugComment(2, "execute: " + entityItem.getKey() + " " + str11 + ": " + 
/*  937 */                 PokUtils.getAttributeValue(entityItem, str11, ", ", "", false) + " [" + str13 + "] sysfeedFlag: " + str12 + "; is XMLIDLABR: " + this.isXMLIDLABR + "; is isXMLCACHE:" + this.isXMLCACHE);
/*      */ 
/*      */             
/*  940 */             addDebugComment(2, "isSystemResend: " + this.isSystemResend + "; isSystemResendRFR: " + this.isSystemResendRFR + "; isSystemResendCache: " + this.isSystemResendCache);
/*      */ 
/*      */             
/*  943 */             if (this.isSystemResendRFR) {
/*  944 */               processSystemResendRFR(entityItem, str11, str13);
/*  945 */             } else if (this.isSystemResend) {
/*  946 */               processSystemResend(entityItem, xMLMQ, str11, str13);
/*  947 */             } else if (this.isSystemResendCache) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  960 */               this.isSystemResendCacheExist = checkSystemResendCache(entityItem);
/*  961 */               if (this.isSystemResendCacheExist) {
/*  962 */                 setReturnCode(2);
/*      */               
/*      */               }
/*      */             }
/*  966 */             else if (!this.isXMLIDLABR) {
/*      */               
/*  968 */               if (!"0020".equals(str13) && !"0040".equals(str13)) {
/*  969 */                 addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */                 
/*  971 */                 addError(this.rsBundle.getString("NOT_R4RFINAL"), entityItem);
/*      */               } else {
/*  973 */                 bool = xMLMQ.createXML(entityItem);
/*  974 */                 if (!bool) {
/*  975 */                   addDebug(entityItem.getKey() + " will not have XML generated, createXML=false");
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } else {
/*  980 */             addDebug("execute: periodic " + entityItem.getKey());
/*      */           } 
/*      */           
/*  983 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = null;
/*  984 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = null;
/*      */           
/*  986 */           if (!this.isXMLIDLABR) {
/*      */             
/*  988 */             if (this.isPeriodicABR) {
/*  989 */               attributeChangeHistoryGroup1 = null;
/*      */             }
/*      */             else {
/*      */               
/*  993 */               attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */             } 
/*      */           } else {
/*  996 */             attributeChangeHistoryGroup2 = getADSABRSTATUSHistory("XMLIDLABRSTATUS");
/*  997 */             attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */           } 
/*      */           
/* 1000 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup3 = getSTATUSHistory(xMLMQ);
/*      */           
/* 1002 */           String str10 = getDTFS(entityItem, xMLMQ);
/*      */ 
/*      */           
/* 1005 */           setT2DTS(entityItem, xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup3, str10, attributeChangeHistoryGroup2);
/* 1006 */           setT1DTS(xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup3, str10);
/*      */           
/* 1008 */           if ((getReturnCode() == 0 || getReturnCode() == 1 || getReturnCode() == 2) && bool) {
/*      */ 
/*      */             
/* 1011 */             Profile profile = switchRole(xMLMQ.getRoleCode());
/* 1012 */             if (profile != null) {
/* 1013 */               addDebug(entityItem.getKey() + " T1=" + this.t1DTS + ";T2=" + this.t2DTS);
/* 1014 */               profile.setValOnEffOn(this.t2DTS, this.t2DTS);
/* 1015 */               profile.setEndOfDay(this.t2DTS);
/* 1016 */               profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 1017 */               profile.setLoginTime(this.t2DTS);
/*      */               
/* 1019 */               Profile profile1 = profile.getNewInstance(this.m_db);
/* 1020 */               profile1.setValOnEffOn(this.t1DTS, this.t1DTS);
/* 1021 */               profile1.setEndOfDay(this.t2DTS);
/* 1022 */               profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 1023 */               profile1.setLoginTime(this.t2DTS);
/*      */               
/* 1025 */               String str11 = "";
/*      */               try {
/* 1027 */                 if (this.isPeriodicABR) {
/*      */                   
/* 1029 */                   String str12 = "";
/* 1030 */                   if (str7 != null) {
/* 1031 */                     str12 = (String)ADSTYPES_TBL.get(str7);
/*      */                   }
/* 1033 */                   str11 = "Periodic " + str12;
/* 1034 */                   if ("20".equals(str8)) {
/* 1035 */                     str11 = "Deleted " + str12;
/*      */                   }
/* 1037 */                   setupPrintWriters();
/* 1038 */                   xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                 } else {
/* 1040 */                   str11 = entityItem.getKey();
/*      */                   
/* 1042 */                   if (domainNeedsChecks(entityItem)) {
/* 1043 */                     xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                   } else {
/*      */                     
/* 1046 */                     addXMLGenMsg("DOMAIN_NOT_LISTED", str11);
/*      */                   } 
/*      */                 } 
/* 1049 */               } catch (IOException iOException) {
/*      */ 
/*      */                 
/* 1052 */                 MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("REQ_ERROR"));
/* 1053 */                 this.args[0] = iOException.getMessage();
/* 1054 */                 addMSGLOGReason(iOException.getMessage());
/* 1055 */                 addError(messageFormat1.format(this.args));
/* 1056 */                 addXMLGenMsg("FAILED", str11);
/* 1057 */               } catch (SQLException sQLException) {
/* 1058 */                 addXMLGenMsg("FAILED", str11);
/* 1059 */                 throw sQLException;
/* 1060 */               } catch (MiddlewareRequestException middlewareRequestException) {
/* 1061 */                 addXMLGenMsg("FAILED", str11);
/* 1062 */                 throw middlewareRequestException;
/* 1063 */               } catch (MiddlewareException middlewareException) {
/* 1064 */                 addXMLGenMsg("FAILED", str11);
/* 1065 */                 throw middlewareException;
/* 1066 */               } catch (ParserConfigurationException parserConfigurationException) {
/* 1067 */                 addXMLGenMsg("FAILED", str11);
/* 1068 */                 throw parserConfigurationException;
/* 1069 */               } catch (TransformerException transformerException) {
/* 1070 */                 addXMLGenMsg("FAILED", str11);
/* 1071 */                 throw transformerException;
/* 1072 */               } catch (MissingResourceException missingResourceException) {
/* 1073 */                 addXMLGenMsg("FAILED", str11);
/* 1074 */                 throw missingResourceException;
/*      */               } 
/*      */             } 
/*      */           } 
/* 1078 */         } else if ("SVCMODIERPABRSTATUS".equals(str6)) {
/*      */           
/* 1080 */           XMLMQ xMLMQ = (XMLMQ)Class.forName("COM.ibm.eannounce.abr.sg.adsxmlbh1.SVCMODIERPABRSTATUSABR").newInstance();
/* 1081 */           str = xMLMQ.getMQCID();
/* 1082 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/* 1083 */           Profile profile1 = switchRole(xMLMQ.getRoleCode());
/* 1084 */           Profile profile2 = null;
/* 1085 */           if (profile1 != null) {
/* 1086 */             addDebug(entityItem.getKey() + " T1=" + this.t1DTS + ";T2=" + this.t2DTS);
/* 1087 */             profile1.setValOnEffOn(this.t2DTS, this.t2DTS);
/* 1088 */             profile1.setEndOfDay(this.t2DTS);
/* 1089 */             profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 1090 */             profile1.setLoginTime(this.t2DTS);
/*      */             
/* 1092 */             profile2 = profile1.getNewInstance(this.m_db);
/* 1093 */             profile2.setValOnEffOn(this.t1DTS, this.t1DTS);
/* 1094 */             profile2.setEndOfDay(this.t2DTS);
/* 1095 */             profile2.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 1096 */             profile2.setLoginTime(this.t2DTS);
/*      */           } 
/* 1098 */           xMLMQ.processThis(this, profile2, profile1, entityItem);
/*      */         } else {
/* 1100 */           addError(getShortClassName(getClass()) + " does not support " + str6);
/*      */         } 
/*      */         
/* 1103 */         str1 = getNavigationName(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1110 */         if (this.isPeriodicABR && !this.isXMLIDLABR && getReturnCode() == 0) {
/* 1111 */           PDGUtility pDGUtility = new PDGUtility();
/* 1112 */           OPICMList oPICMList = new OPICMList();
/* 1113 */           oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/* 1114 */           addDebug("update lastran date:" + this.t2DTS);
/* 1115 */           pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */         } 
/*      */ 
/*      */         
/* 1119 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/* 1120 */       } catch (Exception exception) {
/* 1121 */         addMSGLOGReason(exception.getMessage());
/* 1122 */         addError(exception.getMessage());
/* 1123 */         throw exception;
/*      */       } finally {
/* 1125 */         if (this.isSystemResend || this.isSystemResendRFR || this.isSystemResendCache || this.isSystemResendCurrent) {
/* 1126 */           setFlagValue("SYSFEEDRESEND", "No");
/*      */         }
/* 1128 */         if (this.isXMLIDLABR) {
/* 1129 */           deactivateMultiFlagValue("XMLABRPROPFILE");
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1136 */         if (this.t2DTS.equals("&nbsp;")) {
/* 1137 */           this.t2DTS = getNow();
/*      */         }
/* 1139 */         addDebug("isPeriodicABR:" + this.isPeriodicABR);
/* 1140 */         if (this.isPeriodicABR) {
/* 1141 */           EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1142 */           String str6 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*      */ 
/*      */ 
/*      */           
/* 1146 */           String str7 = getNow();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1164 */           String str8 = getEntityLastValfrom(entityItem);
/* 1165 */           if (str8 != null) {
/* 1166 */             str7 = str8;
/* 1167 */             addDebug("setupDTS : " + str7);
/*      */           } else {
/* 1169 */             addDebug(entityItem.getKey() + "can not find last valfrom of the entity! SETUPDTS is getnow().");
/*      */           } 
/*      */           
/* 1172 */           String str9 = "";
/* 1173 */           if (str6 == null) {
/* 1174 */             if ("XMLXLATESETUP".equals(entityItem.getEntityType())) {
/* 1175 */               str9 = getDescription(entityItem, "ADSATTRIBUTE", "short");
/* 1176 */             } else if ("XMLCOMPATSETUP".equals(entityItem.getEntityType())) {
/* 1177 */               str9 = "WWCOMPAT";
/* 1178 */             } else if ("XMLPRODPRICESETUP".equals(entityItem.getEntityType())) {
/* 1179 */               str9 = getDescription(entityItem, "ADSOFFTYPE", "long");
/*      */             } 
/*      */           } else {
/* 1182 */             str9 = (String)ADSTYPES_TBL.get(str6);
/*      */           } 
/*      */           
/* 1185 */           ArrayList<String[]> arrayList = new ArrayList();
/* 1186 */           String str10 = (this.reason.toString().length() < 64) ? this.reason.toString() : this.reason.toString().substring(0, 63);
/* 1187 */           String str11 = PokUtils.getAttributeFlagValue(entityItem, "XMLABRPROPFILE");
/* 1188 */           if (str11 == null) {
/* 1189 */             str11 = "";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1194 */           StringTokenizer stringTokenizer = new StringTokenizer(str11, "|", false);
/* 1195 */           while (stringTokenizer.hasMoreElements()) {
/* 1196 */             String str12 = stringTokenizer.nextToken();
/* 1197 */             arrayList.add(new String[] { null, getNow(), str, this.t2DTS, this.succQueueNameVct.contains(str12) ? "S" : "F", this.succQueueNameVct.contains(str12) ? "" : str10 });
/* 1198 */             inertMSGLOG(arrayList, entityItem.getEntityType(), entityItem.getEntityID(), str7, str9, str12);
/* 1199 */             arrayList.clear();
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 1204 */         else if (!this.isXMLIDLABR && 
/* 1205 */           this.extxmlfeedVct != null && this.extxmlfeedVct.size() > 0) {
/* 1206 */           EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1207 */           ArrayList<String[]> arrayList = new ArrayList();
/* 1208 */           String str6 = (this.reason.toString().length() < 64) ? this.reason.toString() : this.reason.toString().substring(0, 63);
/* 1209 */           String str7 = getNow();
/* 1210 */           for (byte b = 0; b < this.extxmlfeedVct.size(); b++) {
/* 1211 */             EntityItem entityItem1 = this.extxmlfeedVct.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1227 */             String str8 = getEntityLastValfrom(entityItem1);
/* 1228 */             if (str8 != null) {
/* 1229 */               str7 = str8;
/* 1230 */               addDebug("setupDTS : " + str7);
/*      */             } else {
/* 1232 */               addDebug(entityItem.getKey() + "can not find last valfrom of the entity! SETUPDTS is getnow().");
/*      */             } 
/*      */ 
/*      */             
/* 1236 */             String str9 = PokUtils.getAttributeFlagValue(entityItem1, "XMLABRPROPFILE");
/* 1237 */             if (str9 == null) {
/* 1238 */               str9 = "";
/*      */             }
/*      */             
/* 1241 */             StringTokenizer stringTokenizer = new StringTokenizer(str9, "|", false);
/* 1242 */             while (stringTokenizer.hasMoreElements()) {
/* 1243 */               String str10 = stringTokenizer.nextToken();
/* 1244 */               arrayList.add(new String[] { Integer.toString(entityItem.getEntityID()), getNow(), str, this.t2DTS, this.succQueueNameVct.contains(str10) ? "S" : "F", this.succQueueNameVct.contains(str10) ? "" : str6 });
/* 1245 */               inertMSGLOG(arrayList, entityItem1.getEntityType(), entityItem1.getEntityID(), str7, entityItem.getEntityType(), str10);
/* 1246 */               arrayList.clear();
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1253 */     } catch (Throwable throwable) {
/* 1254 */       StringWriter stringWriter = new StringWriter();
/* 1255 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 1256 */       String str7 = "<pre>{0}</pre>";
/* 1257 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/* 1258 */       setReturnCode(-3);
/* 1259 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/* 1261 */       this.args[0] = throwable.getMessage();
/* 1262 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 1263 */       messageFormat1 = new MessageFormat(str7);
/* 1264 */       this.args[0] = stringWriter.getBuffer().toString();
/* 1265 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 1266 */       logError("Exception: " + throwable.getMessage());
/* 1267 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/* 1271 */       setDGTitle(str1);
/* 1272 */       setDGRptName(getShortClassName(getClass()));
/* 1273 */       setDGRptClass(getABRCode());
/*      */       
/* 1275 */       if (!isReadOnly()) {
/* 1276 */         clearSoftLock();
/*      */       }
/* 1278 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */     
/* 1282 */     this.extxmlfeedVct.clear();
/* 1283 */     this.succQueueNameVct.clear();
/*      */ 
/*      */     
/* 1286 */     MessageFormat messageFormat = new MessageFormat(str2);
/* 1287 */     this.args[0] = getShortClassName(getClass());
/* 1288 */     this.args[1] = str1;
/* 1289 */     String str3 = messageFormat.format(this.args);
/*      */     
/* 1291 */     String str4 = null;
/* 1292 */     if (this.isPeriodicABR) {
/* 1293 */       str4 = buildPeriodicRptHeader();
/* 1294 */       restoreXtraContent();
/*      */     } else {
/* 1296 */       str4 = buildDQTriggeredRptHeader();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1302 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/* 1303 */     this.rptSb.insert(0, str5);
/*      */     
/* 1305 */     println(this.rptSb.toString());
/*      */     
/* 1307 */     this.rsBundle = null;
/* 1308 */     this.args = null;
/* 1309 */     this.extxmlfeedVct = null;
/* 1310 */     this.succQueueNameVct = null;
/* 1311 */     messageFormat = null;
/* 1312 */     this.userxmlSb = null;
/* 1313 */     this.rptSb = null;
/* 1314 */     printDGSubmitString();
/* 1315 */     println(EACustom.getTOUDiv());
/* 1316 */     buildReportFooter();
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
/*      */   private boolean checkSystemResendCache(EntityItem paramEntityItem) throws MiddlewareException, SQLException {
/* 1329 */     boolean bool = false;
/* 1330 */     String str = paramEntityItem.getEntityType();
/* 1331 */     int i = paramEntityItem.getEntityID();
/* 1332 */     Statement statement = null;
/*      */     
/* 1334 */     ResultSet resultSet = null;
/* 1335 */     addDebug("rootType :" + str);
/* 1336 */     addDebug("entityID :" + i);
/*      */     
/* 1338 */     StringBuffer stringBuffer = new StringBuffer();
/* 1339 */     stringBuffer.append(" SELECT XMLMESSAGE FROM cache.xmlidlcache");
/* 1340 */     stringBuffer.append(" WHERE xmlentitytype = '" + str + "'");
/* 1341 */     stringBuffer.append(" AND xmlentityID = " + i);
/* 1342 */     stringBuffer.append(" AND xmlcachevalidto > current timestamp with ur");
/*      */     
/* 1344 */     addDebug("query cache sql is :" + stringBuffer.toString());
/*      */ 
/*      */     
/*      */     try {
/* 1348 */       statement = this.m_db.getODSConnection().createStatement();
/* 1349 */       resultSet = statement.executeQuery(stringBuffer.toString());
/*      */       
/* 1351 */       if (resultSet.next()) {
/* 1352 */         this.SystemResendCacheXml = resultSet.getString(1);
/* 1353 */         bool = true;
/*      */       } else {
/* 1355 */         bool = false;
/*      */       } 
/* 1357 */       addDebug("get XMLIDLCache where" + paramEntityItem.getKey() + " is isCached: " + bool);
/* 1358 */     } catch (MiddlewareException middlewareException) {
/* 1359 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 1360 */       middlewareException.printStackTrace();
/* 1361 */       throw middlewareException;
/* 1362 */     } catch (SQLException sQLException) {
/* 1363 */       addDebug("RuntimeException on ? " + sQLException);
/* 1364 */       sQLException.printStackTrace();
/* 1365 */       throw sQLException;
/*      */     } 
/* 1367 */     return bool;
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
/*      */   private void processSystemResendRFR(EntityItem paramEntityItem, String paramString1, String paramString2) throws MiddlewareException, SQLException {
/* 1401 */     setReturnCode(1);
/* 1402 */     addDebug("this abr is GroupABR :" + this.m_abri.getABRQueType().equals("GroupABR"));
/* 1403 */     if ("0020".equals(paramString2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1409 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */       
/* 1411 */       if (existBefore(attributeChangeHistoryGroup, "0030")) {
/* 1412 */         addDebug(paramEntityItem.getKey() + " Error: A Resend RFR request is not valid since XML was previously created successfully.");
/* 1413 */         addError("Error: A \"Resend RFR\" request is not valid since XML was previously created successfully.", paramEntityItem);
/*      */       } else {
/* 1415 */         this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND_RFR");
/*      */       } 
/*      */     } else {
/*      */       
/* 1419 */       addDebug(paramEntityItem.getKey() + " A \"Resend RFR\" request is not valid since the data must be \"Final\"");
/* 1420 */       addError("Error : A \"Resend RFR\" request is not valid since the data must be \"Final\"", paramEntityItem);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1427 */     this.curStatus = "Ready for Review";
/* 1428 */     this.curStatusvalue = "0040";
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
/*      */   private void processSystemResend(EntityItem paramEntityItem, XMLMQ paramXMLMQ, String paramString1, String paramString2) throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 1444 */     String str = sysFeedResendStatus(this.m_abri.getABRCode(), "_SYSFeedResendValue", "Both");
/*      */     
/* 1446 */     if ("0020".equals(str)) {
/* 1447 */       if ("0020".equals(paramString2)) {
/* 1448 */         this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */       } else {
/* 1450 */         addDebug(paramEntityItem.getKey() + " is not Final");
/*      */ 
/*      */         
/* 1453 */         addError(this.rsBundle.getString("RESEND_ONLY_FINAL"), paramEntityItem);
/*      */       } 
/* 1455 */     } else if ("0040".equals(str)) {
/* 1456 */       if ("0040".equals(paramString2)) {
/*      */         
/* 1458 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */         
/* 1460 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(paramXMLMQ);
/*      */         
/* 1462 */         if (existPassedFinal(attributeChangeHistoryGroup1, attributeChangeHistoryGroup2)) {
/* 1463 */           addDebug(paramEntityItem.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
/* 1464 */           addError(this.rsBundle.getString("RESEND_R4R_PASSEDFINAL"), paramEntityItem);
/*      */         } else {
/* 1466 */           this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */         } 
/*      */       } else {
/* 1469 */         addDebug(paramEntityItem.getKey() + " is not RFR");
/*      */         
/* 1471 */         addError(this.rsBundle.getString("RESEND_ONLY_R4REVIEW"), paramEntityItem);
/*      */       }
/*      */     
/*      */     }
/* 1475 */     else if (!"0020".equals(paramString2) && !"0040".equals(paramString2)) {
/* 1476 */       addDebug(paramEntityItem.getKey() + " is not Final or R4R");
/*      */       
/* 1478 */       addError(this.rsBundle.getString("RESEND_NOT_R4RFINAL"), paramEntityItem);
/*      */     }
/* 1480 */     else if ("0020".equals(paramString2)) {
/* 1481 */       this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */     } else {
/*      */       
/* 1484 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */       
/* 1486 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(paramXMLMQ);
/* 1487 */       if (existPassedFinal(attributeChangeHistoryGroup1, attributeChangeHistoryGroup2)) {
/* 1488 */         addDebug(paramEntityItem.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
/* 1489 */         addError(this.rsBundle.getString("RESEND_R4R_PASSEDFINAL"), paramEntityItem);
/*      */       } else {
/* 1491 */         this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1498 */     this.curStatus = PokUtils.getAttributeValue(paramEntityItem, paramString1, ", ", "", false);
/* 1499 */     this.curStatusvalue = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 1500 */     this.priorStatus = this.curStatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDTFS(EntityItem paramEntityItem, XMLMQ paramXMLMQ) {
/* 1509 */     String str1 = paramXMLMQ.getStatusAttr();
/* 1510 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/* 1511 */     String str3 = "";
/* 1512 */     if ("0020".equals(str2)) {
/* 1513 */       if (getEntityType().equals("XMLPRODPRICESETUP")) {
/* 1514 */         str3 = getQueuedValue("ADSPPABRSTATUS");
/*      */       } else {
/* 1516 */         str3 = getQueuedValue("ADSABRSTATUS");
/*      */       }
/*      */     
/* 1519 */     } else if (getEntityType().equals("XMLPRODPRICESETUP")) {
/* 1520 */       str3 = getRFRQueuedValue("ADSPPABRSTATUS");
/*      */     } else {
/* 1522 */       str3 = getRFRQueuedValue("ADSABRSTATUS");
/*      */     } 
/*      */     
/* 1525 */     addDebug("getDTFS " + getEntityType() + str2 + " from properties file is " + str3);
/* 1526 */     return str3;
/*      */   }
/*      */   
/*      */   private void setupPrintWriters() {
/* 1530 */     String str = this.m_abri.getFileName();
/* 1531 */     int i = str.lastIndexOf(".");
/* 1532 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/* 1533 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/* 1535 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/* 1536 */     } catch (Exception exception) {
/* 1537 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/* 1540 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/* 1541 */     } catch (Exception exception) {
/* 1542 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriters() {
/* 1546 */     if (this.dbgPw != null) {
/* 1547 */       this.dbgPw.flush();
/* 1548 */       this.dbgPw.close();
/* 1549 */       this.dbgPw = null;
/*      */     } 
/* 1551 */     if (this.userxmlPw != null) {
/* 1552 */       this.userxmlPw.flush();
/* 1553 */       this.userxmlPw.close();
/* 1554 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/* 1560 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/* 1562 */       BufferedInputStream bufferedInputStream = null;
/* 1563 */       FileInputStream fileInputStream = null;
/* 1564 */       BufferedReader bufferedReader = null;
/*      */       try {
/* 1566 */         fileInputStream = new FileInputStream(this.userxmlfn);
/* 1567 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/* 1569 */         String str = null;
/* 1570 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/* 1572 */         while ((str = bufferedReader.readLine()) != null) {
/* 1573 */           this.userxmlSb.append(convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/* 1576 */         File file = new File(this.userxmlfn);
/* 1577 */         if (file.exists()) {
/* 1578 */           file.delete();
/*      */         }
/* 1580 */       } catch (Exception exception) {
/* 1581 */         exception.printStackTrace();
/*      */       } finally {
/* 1583 */         if (bufferedInputStream != null) {
/*      */           try {
/* 1585 */             bufferedInputStream.close();
/* 1586 */           } catch (Exception exception) {
/* 1587 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/* 1590 */         if (fileInputStream != null) {
/*      */           try {
/* 1592 */             fileInputStream.close();
/* 1593 */           } catch (Exception exception) {
/* 1594 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 1599 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/* 1602 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/* 1604 */       BufferedInputStream bufferedInputStream = null;
/* 1605 */       FileInputStream fileInputStream = null;
/* 1606 */       BufferedReader bufferedReader = null;
/*      */       try {
/* 1608 */         fileInputStream = new FileInputStream(this.dbgfn);
/* 1609 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/* 1611 */         String str = null;
/* 1612 */         StringBuffer stringBuffer = new StringBuffer();
/* 1613 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/* 1615 */         while ((str = bufferedReader.readLine()) != null) {
/* 1616 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/* 1618 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/* 1621 */         File file = new File(this.dbgfn);
/* 1622 */         if (file.exists()) {
/* 1623 */           file.delete();
/*      */         }
/* 1625 */       } catch (Exception exception) {
/* 1626 */         exception.printStackTrace();
/*      */       } finally {
/* 1628 */         if (bufferedInputStream != null) {
/*      */           try {
/* 1630 */             bufferedInputStream.close();
/* 1631 */           } catch (Exception exception) {
/* 1632 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/* 1635 */         if (fileInputStream != null) {
/*      */           try {
/* 1637 */             fileInputStream.close();
/* 1638 */           } catch (Exception exception) {
/* 1639 */             exception.printStackTrace();
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
/*      */   protected void addXMLGenMsg(String paramString1, String paramString2) {
/* 1651 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/* 1652 */     Object[] arrayOfObject = { paramString2 };
/* 1653 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/*      */   private String buildDQTriggeredRptHeader() {
/* 1670 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Status: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Prior Status: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1681 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1682 */     this.args[0] = this.m_prof.getOPName();
/* 1683 */     this.args[1] = this.m_prof.getRoleDescription();
/* 1684 */     this.args[2] = this.m_prof.getWGName();
/* 1685 */     this.args[3] = this.t2DTS;
/* 1686 */     this.args[4] = this.curStatus;
/* 1687 */     this.args[5] = this.t1DTS;
/* 1688 */     this.args[6] = this.priorStatus;
/* 1689 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/* 1690 */     this.args[8] = this.abrversion + " " + getABRVersion();
/*      */     
/* 1692 */     return messageFormat.format(this.args);
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
/*      */   private String buildPeriodicRptHeader() {
/* 1704 */     String str = "<table>" + NEWLINE + "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{2}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {3} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1710 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1711 */     this.args[0] = this.t2DTS;
/* 1712 */     this.args[1] = this.t1DTS;
/* 1713 */     this.args[2] = this.xmlgenSb.toString();
/* 1714 */     this.args[3] = this.abrversion + " " + getABRVersion();
/*      */     
/* 1716 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityList getEntityListForDiff(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1726 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, paramProfile, paramString);
/*      */     
/* 1728 */     EntityList entityList = this.m_db.getEntityList(paramProfile, extractActionItem, new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/* 1729 */             .getEntityType(), paramEntityItem.getEntityID()) });
/* 1730 */     EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1749 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1759 */     if ("ADSPRODSTRUCT2".equals(paramString) || "ADSSWPRODSTRUCT2".equals(paramString) || "ADSLSEO2".equals(paramString)) {
/* 1760 */       bool = false;
/* 1761 */     } else if (this.t2DTS.equals(paramProfile.getEffOn())) {
/* 1762 */       bool = true;
/*      */     } 
/* 1764 */     if (bool) {
/* 1765 */       String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 1766 */       boolean bool1 = false;
/* 1767 */       boolean bool2 = false;
/* 1768 */       if (entityItem.hasDownLinks()) {
/* 1769 */         for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/* 1770 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b);
/* 1771 */           if (entityItem1.hasDownLinks()) {
/* 1772 */             for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/* 1773 */               EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/* 1774 */               if ("AVAIL".equals(entityItem2.getEntityType())) {
/* 1775 */                 EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 1776 */                 if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1777 */                   bool1 = true;
/* 1778 */                   String str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 1779 */                   String str2 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", "@@", false);
/* 1780 */                   if (str2.compareTo("2010-03-01") <= 0) {
/* 1781 */                     bool2 = true;
/* 1782 */                   } else if ("0040".equals(str) || "0020".equals(str)) {
/* 1783 */                     if ("0020".equals(str1) || "0040".equals(str1)) {
/* 1784 */                       bool2 = true;
/*      */                     
/*      */                     }
/*      */                   }
/* 1788 */                   else if ("0050".equals(str) && (
/* 1789 */                     "0020".equals(str1) || "0040".equals(str1))) {
/* 1790 */                     bool2 = true;
/*      */                   } 
/*      */                   
/* 1793 */                   addDebugComment(3, "Cheking planed AVAIL " + entityItem2.getKey() + " Status is " + str1 + " AVAIL Type: " + 
/* 1794 */                       PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE"));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       }
/* 1801 */       if (bool1 && !bool2) {
/* 1802 */         addError("Error: Invalid Planed AVAIL. There must have at least one Planed AVAIL with Status is " + ("0020".equals(str) ? "Final" : "RFR or Final") + " , else the XML report will not generated", paramEntityItem);
/* 1803 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/* 1807 */     addDebugComment(2, "EntityList for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 1808 */         PokUtils.outputList(entityList));
/*      */     
/* 1810 */     EntityGroup entityGroup = entityList.getParentEntityGroup();
/*      */ 
/*      */     
/* 1813 */     if (isVEFiltered(paramString)) {
/*      */ 
/*      */       
/* 1816 */       String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 1817 */       addDebugComment(3, "The status of the root for VE " + paramString + " is: " + str);
/*      */ 
/*      */ 
/*      */       
/* 1821 */       for (byte b = 0; b < VE_Filter_Array.length; b++) {
/* 1822 */         addDebugComment(3, "Looking at VE_filter_Array" + VE_Filter_Array[b][0] + " " + VE_Filter_Array[b][1] + " " + VE_Filter_Array[b][2]);
/*      */         
/* 1824 */         if (VE_Filter_Array[b][0].equals(paramString)) {
/* 1825 */           EntityGroup entityGroup1 = entityList.getEntityGroup(VE_Filter_Array[b][1]);
/* 1826 */           addDebugComment(3, "Found " + entityList.getEntityGroup(VE_Filter_Array[b][1]));
/*      */ 
/*      */           
/* 1829 */           if (entityGroup1 != null) {
/*      */ 
/*      */             
/* 1832 */             EntityItem[] arrayOfEntityItem = entityGroup1.getEntityItemsAsArray();
/*      */ 
/*      */             
/* 1835 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/* 1837 */               String str1 = null;
/* 1838 */               boolean bool1 = true;
/* 1839 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 1840 */               String str2 = entityItem1.getEntityType();
/*      */               
/* 1842 */               addDebugComment(2, "Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */ 
/*      */               
/* 1845 */               String str3 = VE_Filter_Array[b][2];
/*      */ 
/*      */               
/* 1848 */               str1 = PokUtils.getAttributeFlagValue(entityItem1, (String)ITEM_STATUS_ATTR_TBL.get(str2));
/* 1849 */               String str4 = str;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1956 */               addDebugComment(2, (String)ITEM_STATUS_ATTR_TBL.get(str2) + " is " + str1);
/* 1957 */               if ("AVAIL".equals(str2))
/*      */               
/* 1959 */               { if ("PRODSTRUCT".equals(entityItem.getEntityType()) || "SWPRODSTRUCT".equals(entityItem.getEntityType())) {
/*      */                   
/* 1961 */                   for (byte b2 = 0; b2 < entityItem.getDownLinkCount(); b2++) {
/* 1962 */                     EntityItem entityItem2 = (EntityItem)entityItem.getDownLink(b2);
/* 1963 */                     if ("MODEL".equals(entityItem2.getEntityType())) {
/* 1964 */                       String str6 = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 1965 */                       addDebugComment(2, "New check PRODSTRUCT ANN DATE is " + str6);
/* 1966 */                       if (str6.compareTo("2010-03-01") <= 0) {
/* 1967 */                         bool1 = false;
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/* 1972 */                   if (!bool1)
/*      */                     continue; 
/* 1974 */                 }  if ("MODEL".equals(entityItem.getEntityType()) || "MODELCONVERT".equals(entityItem.getEntityType())) {
/* 1975 */                   String str6 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/* 1976 */                   addDebugComment(2, "New check ROOT ANN DATE is " + str6);
/* 1977 */                   if (str6.compareTo("2010-03-01") <= 0) {
/* 1978 */                     bool1 = false;
/*      */                     continue;
/*      */                   } 
/*      */                 } 
/* 1982 */                 EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 1983 */                 String str5 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "@@", false);
/* 1984 */                 addDebugComment(2, "New check EFFECTIVEDATE is " + str5);
/*      */                 
/* 1986 */                 if (str1 == null) { bool1 = true; }
/* 1987 */                 else if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146") && str5.compareTo("2010-03-01") <= 0)
/* 1988 */                 { bool1 = false;
/*      */                    }
/*      */                 
/* 1991 */                 else if (str1.equals("0020") || str1.equals("0040"))
/* 1992 */                 { bool1 = false;
/*      */ 
/*      */ 
/*      */ 
/*      */                   
/*      */                    }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*      */                  }
/*      */               
/* 2006 */               else if (str1 == null) { bool1 = true; }
/* 2007 */               else if (str1.equals("0020")) { bool1 = false; }
/* 2008 */               else if (str1.equals("0040") && str4.equals("0040") && str3
/* 2009 */                 .equals("RFR Final")) { bool1 = false; }
/*      */               
/* 2011 */               if (bool1 == true) {
/*      */                 
/* 2013 */                 addDebugComment(2, "Removing " + str2 + " " + entityItem1.getEntityID() + " " + str1 + " from list");
/* 2014 */                 addDebugComment(2, "Filter criteria is " + str3);
/*      */                 
/* 2016 */                 removeItem(entityGroup, entityItem1);
/*      */               } 
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2024 */       addDebugComment(2, "EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 2025 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */ 
/*      */     
/* 2029 */     if (isVECountryFiltered(paramString)) {
/*      */ 
/*      */       
/* 2032 */       String str1 = entityItem.getEntityType();
/* 2033 */       String str2 = (String)ITEM_COUNTRY_ATTR_TBL.get(str1);
/*      */ 
/*      */       
/* 2036 */       HashSet hashSet = getCountry(entityItem, str2);
/*      */ 
/*      */ 
/*      */       
/* 2040 */       for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/* 2041 */         addDebugComment(3, "Looking at VE_Country_Filter_Array " + VE_Country_Filter_Array[b][0] + " " + VE_Country_Filter_Array[b][1]);
/*      */         
/* 2043 */         if (VE_Country_Filter_Array[b][0].equals(paramString)) {
/* 2044 */           EntityGroup entityGroup1 = entityList.getEntityGroup(VE_Country_Filter_Array[b][1]);
/* 2045 */           addDebugComment(3, "Found " + entityList.getEntityGroup(VE_Country_Filter_Array[b][1]));
/*      */ 
/*      */           
/* 2048 */           if (entityGroup1 != null) {
/*      */ 
/*      */             
/* 2051 */             EntityItem[] arrayOfEntityItem = entityGroup1.getEntityItemsAsArray();
/*      */ 
/*      */             
/* 2054 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/* 2056 */               boolean bool1 = true;
/* 2057 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 2058 */               String str3 = entityItem1.getEntityType();
/*      */               
/* 2060 */               addDebugComment(3, "Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */               
/* 2062 */               String str4 = (String)ITEM_COUNTRY_ATTR_TBL.get(str3);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2067 */               HashSet hashSet1 = getCountry(entityItem1, str4);
/* 2068 */               Iterator<String> iterator = hashSet.iterator();
/* 2069 */               while (iterator.hasNext() && bool1 == true) {
/* 2070 */                 String str = iterator.next();
/* 2071 */                 if (hashSet1.contains(str)) {
/* 2072 */                   bool1 = false;
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/* 2077 */               if (bool1 == true)
/*      */               {
/*      */                 
/* 2080 */                 removeItem(entityGroup, entityItem1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2088 */       addDebugComment(2, "EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 2089 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */     
/* 2092 */     return entityList;
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
/*      */   private void setT1DTS(XMLMQ paramXMLMQ, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) throws MiddlewareRequestException, MiddlewareException, SQLException {
/* 2118 */     this.t1DTS = this.m_strEpoch;
/* 2119 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 2120 */     if (this.isPeriodicABR) {
/* 2121 */       addDebug("getT1 entered for Periodic ABR " + entityItem.getKey());
/*      */       
/* 2123 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/* 2124 */       if (eANMetaAttribute == null) {
/* 2125 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/* 2127 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */     } else {
/* 2129 */       String str = paramXMLMQ.getStatusAttr();
/* 2130 */       addDebug("getT1 entered for DQ ABR " + entityItem.getKey() + " " + str + " isSystemResend:" + this.isSystemResend + " isIDLABR:" + this.isXMLIDLABR + " isXMLADSABR=" + this.isXMLADSABR);
/* 2131 */       if (this.isXMLADSABR) {
/* 2132 */         addDebug("getT1 isXMLADSABR");
/* 2133 */         String str1 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0050", (String)null);
/* 2134 */         addDebug("getT1 isXMLADSABR DTS1=" + str1);
/* 2135 */         String str2 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0020", str1);
/* 2136 */         addDebug("getT1 isXMLADSABR DTS9=" + str2);
/*      */         
/* 2138 */         boolean bool = isFullXML(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, str2);
/* 2139 */         addDebug("getT1 isXMLADSABR fullXML=" + bool);
/*      */         
/* 2141 */         if (bool || this.isSystemResend || this.isSystemResendRFR || this.isSystemResendCache || this.isSystemResendCurrent) {
/* 2142 */           addDebug("getT1 isXMLADSABR isSystemResend=" + this.isSystemResend + " || isSystemResendRFR=" + this.isSystemResendRFR + " || isSystemResendCache=" + this.isSystemResendCache + " || isSystemResendCurrent=" + this.isSystemResendCache);
/*      */           
/* 2144 */           this.t1DTS = this.m_strEpoch;
/*      */ 
/*      */ 
/*      */           
/* 2148 */           if (!this.isSystemResend && !this.isSystemResendRFR && !this.isSystemResendCurrent) {
/* 2149 */             setT1Action(entityItem, paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1);
/*      */           }
/* 2151 */         } else if (!bool) {
/*      */           
/* 2153 */           addDebug("getT1 isXMLADSABR fullXML=" + bool + "T2=" + this.t2DTS);
/* 2154 */           String str3 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0030", this.t2DTS);
/* 2155 */           addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS3=" + str3);
/* 2156 */           String str4 = "";
/* 2157 */           String str5 = "";
/* 2158 */           String str6 = "";
/* 2159 */           if (str3 != null && str3.equals(this.m_strEpoch)) {
/* 2160 */             this.t1DTS = this.m_strEpoch;
/* 2161 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "T1=" + this.t1DTS);
/*      */           } else {
/*      */             
/* 2164 */             str4 = getValtoCompareValFromInStatusHistory(entityItem, paramAttributeChangeHistoryGroup2, str3);
/* 2165 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS4=" + str4);
/*      */             
/* 2167 */             str6 = getValFromInStatusHistory("statusHistory", paramAttributeChangeHistoryGroup2, (String)null, str3);
/* 2168 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS6=" + str6);
/*      */             
/* 2170 */             str5 = getValFromInStatusHistory(paramAttributeChangeHistoryGroup1, "0050", str6, str3);
/* 2171 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS5=" + str5);
/*      */             
/* 2173 */             this.t1DTS = getMinTime(str4, str5);
/* 2174 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "T1=" + this.t1DTS);
/*      */           } 
/* 2176 */           setT1Action(entityItem, paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1);
/*      */         } 
/* 2178 */         addDebug("getT1 isXMLADSABR T1=" + this.t1DTS);
/* 2179 */         addDebug("getT1 isXMLADSABR end");
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
/*      */   private void setT1Action(EntityItem paramEntityItem, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2) throws SQLException, MiddlewareException {
/* 2196 */     String str1 = getT2Status(paramAttributeChangeHistoryGroup1);
/* 2197 */     String str2 = "";
/*      */     
/* 2199 */     if (existBefore(paramAttributeChangeHistoryGroup2, "0030")) {
/*      */       
/* 2201 */       if (str1.equals("0040")) {
/* 2202 */         str2 = getDeltaT1(paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1, "0020");
/* 2203 */         if (str2.equals(this.m_strEpoch)) {
/* 2204 */           addDebug("getT1 CurrentStatus is RFR, there is no Passed Final before, try to find Passed RFR");
/* 2205 */           str2 = getDeltaT1(paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1, "0040");
/* 2206 */           if (str2.equals(this.m_strEpoch)) {
/* 2207 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*      */           } else {
/* 2209 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_CHANGES");
/*      */           } 
/*      */         } else {
/* 2212 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_BEFORE");
/*      */         }
/*      */       
/* 2215 */       } else if (str1.equals("0020")) {
/* 2216 */         str2 = getDeltaT1(paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1, "0020");
/* 2217 */         if (str2.equals(this.m_strEpoch)) {
/* 2218 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */         } else {
/* 2220 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_CHANGES");
/*      */         } 
/*      */       } 
/*      */     } else {
/* 2224 */       if (str1.equals("0040")) {
/* 2225 */         this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/* 2226 */       } else if (str1.equals("0020")) {
/* 2227 */         this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */       } 
/* 2229 */       addDebug("getT1 for " + paramEntityItem.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
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
/*      */   private boolean isFullXML(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) {
/* 2241 */     boolean bool = false;
/* 2242 */     if (paramAttributeChangeHistoryGroup2 != null && paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() >= 1) {
/* 2243 */       int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount();
/* 2244 */       String str1 = "";
/* 2245 */       String str2 = "";
/* 2246 */       for (int j = i - 1; j >= 0; j--) {
/* 2247 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(j);
/* 2248 */         if (attributeChangeHistoryItem != null) {
/* 2249 */           if ("0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 2250 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 2252 */             if (j == i - 1) {
/* 2253 */               str2 = this.m_strForever;
/*      */             } else {
/* 2255 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(j + 1);
/* 2256 */               if (attributeChangeHistoryItem1 != null) {
/* 2257 */                 str2 = attributeChangeHistoryItem1.getChangeDate();
/*      */               }
/*      */             } 
/* 2260 */             if (str1.compareTo(paramString) < 0 && str2.compareTo(paramString) > 0 && !existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/* 2261 */               this.t1DTS = this.m_strEpoch;
/* 2262 */               bool = true;
/*      */               break;
/*      */             } 
/* 2265 */           } else if ("0020".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 2266 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 2268 */             if (j == i - 1) {
/* 2269 */               str2 = this.m_strForever;
/*      */             } else {
/* 2271 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(j + 1);
/* 2272 */               if (attributeChangeHistoryItem1 != null) {
/* 2273 */                 str2 = attributeChangeHistoryItem1.getChangeDate();
/*      */               }
/*      */             } 
/* 2276 */             if (str1.compareTo(paramString) < 0 && str2.compareTo(paramString) > 0 && !existDeltaT1(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, "0020")) {
/* 2277 */               this.t1DTS = this.m_strEpoch;
/* 2278 */               bool = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2285 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 2292 */     if (paramAttributeChangeHistoryGroup != null) {
/* 2293 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2294 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2295 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 2296 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2301 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int countBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 2307 */     byte b = 0;
/* 2308 */     if (paramAttributeChangeHistoryGroup != null) {
/* 2309 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2310 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2311 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 2312 */           b++;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2317 */     return b;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setT2DTS(EntityItem paramEntityItem, XMLMQ paramXMLMQ, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup3) throws MiddlewareException, SQLException {
/* 2448 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "SYSFEEDRESEND");
/* 2449 */     addDebug("sysfeedFlag= " + str);
/*      */     
/* 2451 */     if (this.isXMLIDLABR && this.isSystemResendCurrent) {
/* 2452 */       addDebug("CASE B Initialize Cache Current ");
/*      */       
/* 2454 */       String str1 = getValtoInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, (String)null);
/*      */       
/* 2456 */       String str2 = getValFromInStatusHistory("xmlIDLStatusHistory", paramAttributeChangeHistoryGroup3, "0050", (String)null);
/*      */       
/* 2458 */       this.t2DTS = getMinTime(str2, str1);
/* 2459 */       addDebug("CASE B Initialize Cache Current T2=" + this.t2DTS);
/* 2460 */     } else if (this.isXMLIDLABR && this.isXMLCACHE) {
/* 2461 */       if (!existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/* 2462 */         addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is not found ");
/*      */ 
/*      */         
/* 2465 */         String str1 = getValtoInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, (String)null);
/*      */         
/* 2467 */         String str2 = getValFromInStatusHistory("xmlIDLStatusHistory", paramAttributeChangeHistoryGroup3, "0050", (String)null);
/*      */         
/* 2469 */         this.t2DTS = getMinTime(str2, str1);
/* 2470 */         addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is not found T2=" + this.t2DTS);
/*      */       } else {
/* 2472 */         addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is found ");
/*      */         
/* 2474 */         String str1 = getValFromInStatusHistory("xmlIDLStatusHistory", paramAttributeChangeHistoryGroup3, "0050", (String)null);
/*      */         
/* 2476 */         String str2 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0030", str1);
/*      */         
/* 2478 */         str1 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0050", str2);
/*      */         
/* 2480 */         str2 = getValtoCompareValFromInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, str1);
/* 2481 */         this.t2DTS = getMinTime(str1, str2);
/* 2482 */         addDebug("CASE A Initialize Cache (ADSABRSTATUS) = Passed (0030) is found T2=" + this.t2DTS);
/*      */       } 
/* 2484 */     } else if (!this.isXMLIDLABR && this.isPeriodicABR) {
/*      */ 
/*      */ 
/*      */       
/* 2488 */       if (this.isPeriodicABR)
/*      */       {
/* 2490 */         this.t2DTS = getEntityT2DTS(paramEntityItem);
/* 2491 */         if (this.t2DTS == null) {
/* 2492 */           this.t2DTS = getNow();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 2515 */     else if (this.isXMLADSABR) {
/* 2516 */       if ("RFR".equals(str)) {
/* 2517 */         addDebug("CASE F Resend RFR");
/* 2518 */         String str1 = paramXMLMQ.getStatusAttr();
/* 2519 */         String str2 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/* 2520 */         if (!"0020".equals(str2)) {
/* 2521 */           addError("A \"Resend RFR\" request is not valid since the data must be \"Final\".", paramEntityItem);
/*      */         }
/* 2523 */         else if (existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/* 2524 */           addError("A \"Resend RFR\" request is not valid since XML was previously created successfully.", paramEntityItem);
/*      */         } else {
/* 2526 */           setRFRT2DTS(paramEntityItem, paramAttributeChangeHistoryGroup2);
/*      */         } 
/*      */         
/* 2529 */         addDebug("CASE F Resend RFR T2=" + this.t2DTS);
/*      */       } else {
/*      */         
/* 2532 */         addDebug("CASE C Production & D Resend & E Resend Cache");
/*      */         
/* 2534 */         String str1 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0050", (String)null);
/*      */         
/* 2536 */         String str2 = getValtoCompareValFromInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, str1);
/* 2537 */         this.t2DTS = getMinTime(str1, str2);
/* 2538 */         addDebug("CASE C Production & D Resend & E Resend Cache T2=" + this.t2DTS);
/*      */       } 
/*      */     } else {
/* 2541 */       addError("There is no such case for the entity.", paramEntityItem);
/*      */     } 
/*      */     
/* 2544 */     if (this.isXMLIDLABR) {
/* 2545 */       setIDLSTATUS(paramEntityItem, paramAttributeChangeHistoryGroup2);
/* 2546 */       addDebug("setIDLSTATUS");
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
/*      */   private void setRFRT2DTS(EntityItem paramEntityItem, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) throws SQLException, MiddlewareException {
/* 2565 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2566 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2567 */       int j = countBefore(paramAttributeChangeHistoryGroup, "0020");
/* 2568 */       if (j > 0) {
/* 2569 */         boolean bool1 = false;
/* 2570 */         boolean bool2 = false;
/* 2571 */         byte b = 0;
/* 2572 */         for (int k = i - 1; k >= 0; k--) {
/* 2573 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(k);
/*      */           
/* 2575 */           b++;
/* 2576 */           if (!bool1 && attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals("0020") && b == j) {
/* 2577 */             bool1 = true;
/*      */           }
/*      */           
/* 2580 */           if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals("0040") && bool1) {
/*      */             
/* 2582 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(k + 1);
/* 2583 */             if (attributeChangeHistoryItem1 != null) {
/* 2584 */               this.t2DTS = attributeChangeHistoryItem1.getChangeDate();
/*      */               
/* 2586 */               this.t2DTS = adjustTimeSecond(this.t2DTS, -30);
/* 2587 */               bool2 = true;
/* 2588 */               this.priorStatus = "Ready for Review";
/*      */               break;
/*      */             } 
/* 2591 */             addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status has no prior history", paramEntityItem);
/*      */           } 
/*      */         } 
/*      */         
/* 2595 */         if (!bool2) {
/* 2596 */           addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status ever being Final but not ever being RFR", paramEntityItem);
/*      */         }
/*      */       } else {
/* 2599 */         addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status never being RFR or Final", paramEntityItem);
/*      */       } 
/*      */     } else {
/* 2602 */       addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status has no history and never being RFR or Final", paramEntityItem);
/*      */     } 
/* 2604 */     addDebug("In the setRFRT2DTS function, the T2 value is " + this.t2DTS);
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
/*      */   private String getValtoInStatusHistory(EntityItem paramEntityItem, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws SQLException, MiddlewareException {
/* 2621 */     String str1 = null;
/* 2622 */     String str2 = "";
/* 2623 */     boolean bool = false;
/* 2624 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2625 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2626 */       for (int j = i - 1; j >= 0; j--) {
/* 2627 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2628 */         if (attributeChangeHistoryItem != null && (
/* 2629 */           "0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode()))) {
/* 2630 */           bool = true;
/* 2631 */           if (j == i - 1) {
/* 2632 */             str2 = this.m_strForever;
/* 2633 */             if (paramString == null) {
/* 2634 */               str1 = str2;
/*      */               
/*      */               break;
/*      */             } 
/* 2638 */             if (str2.compareTo(paramString) < 0) {
/* 2639 */               str1 = str2;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } else {
/* 2644 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j + 1);
/* 2645 */             if (attributeChangeHistoryItem1 != null) {
/* 2646 */               str2 = attributeChangeHistoryItem1.getChangeDate();
/* 2647 */               if (paramString == null) {
/* 2648 */                 str1 = str2;
/* 2649 */                 str1 = adjustTimeSecond(str1, -30);
/*      */                 
/*      */                 break;
/*      */               } 
/* 2653 */               if (str2.compareTo(paramString) < 0) {
/* 2654 */                 str1 = str2;
/* 2655 */                 str1 = adjustTimeSecond(str1, -30);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2664 */       if (!bool) {
/* 2665 */         addError("Error: A request is not valid since the data was never \"Ready for Review\" or \"Final\"", paramEntityItem);
/*      */       }
/*      */     } else {
/* 2668 */       addError("Error: A request is not valid since the status has no history.", paramEntityItem);
/*      */     } 
/* 2670 */     addDebug("The VALTO - 30s value for the most current value of STATUS = {Final(0020) |Ready for Review(0040)} is " + str1);
/* 2671 */     return str1;
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
/*      */   private String getValtoCompareValFromInStatusHistory(EntityItem paramEntityItem, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws SQLException, MiddlewareException {
/* 2685 */     String str1 = null;
/* 2686 */     String str2 = "";
/* 2687 */     String str3 = "";
/* 2688 */     boolean bool = false;
/* 2689 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2690 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2691 */       for (int j = i - 1; j >= 0; j--) {
/* 2692 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2693 */         if (attributeChangeHistoryItem != null && (
/* 2694 */           "0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode()))) {
/* 2695 */           bool = true;
/* 2696 */           str3 = attributeChangeHistoryItem.getChangeDate();
/* 2697 */           if (str3.compareTo(paramString) < 0) {
/* 2698 */             if (j == i - 1) {
/* 2699 */               str2 = this.m_strForever;
/* 2700 */               str1 = str2;
/*      */               break;
/*      */             } 
/* 2703 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j + 1);
/* 2704 */             if (attributeChangeHistoryItem1 != null) {
/* 2705 */               str2 = attributeChangeHistoryItem1.getChangeDate();
/* 2706 */               str1 = str2;
/* 2707 */               str1 = adjustTimeSecond(str1, -30);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2715 */       if (!bool) {
/* 2716 */         addError("Error: A request is not valid since the data was never \"Ready for Review\" or \"Final\"", paramEntityItem);
/*      */       }
/*      */     } else {
/* 2719 */       addError("Error: A request is not valid since the status has no history.", paramEntityItem);
/*      */     } 
/* 2721 */     addDebug("The VALTO - 30s value for the most current value of STATUS = {Final(0020) |Ready for Review(0040)} is " + str1);
/* 2722 */     return str1;
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
/*      */   private String getValFromInStatusHistory(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString1, String paramString2, String paramString3) {
/* 2734 */     String str1 = null;
/* 2735 */     String str2 = "";
/* 2736 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2737 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2738 */       for (int j = i - 1; j >= 0; j--) {
/* 2739 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2740 */         if (attributeChangeHistoryItem != null && 
/* 2741 */           paramString1.equals(attributeChangeHistoryItem.getFlagCode()))
/*      */         {
/* 2743 */           if (paramString2 != null && paramString3 != null) {
/*      */             
/* 2745 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 2746 */             if (str2.compareTo(paramString2) > 0 && str2.compareTo(paramString3) < 0) {
/* 2747 */               str1 = attributeChangeHistoryItem.getChangeDate();
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2755 */     addDebug("The valfrom value of the StatusHistory at " + paramString1 + " which is beteween " + paramString2 + " and " + paramString3 + " is " + str1);
/* 2756 */     return str1;
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
/*      */   private String getValFromInStatusHistory(String paramString1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString2, String paramString3) {
/* 2768 */     String str1 = null;
/* 2769 */     String str2 = "";
/* 2770 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2771 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2772 */       for (int j = i - 1; j >= 0; j--) {
/* 2773 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2774 */         if (attributeChangeHistoryItem != null) {
/* 2775 */           if (paramString2 == null) {
/* 2776 */             if ("0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/*      */               
/* 2778 */               str2 = attributeChangeHistoryItem.getChangeDate();
/* 2779 */               if (paramString3 == null) {
/* 2780 */                 str1 = str2;
/*      */                 
/*      */                 break;
/*      */               } 
/* 2784 */               if (str2.compareTo(paramString3) < 0) {
/* 2785 */                 str1 = str2;
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 2791 */           } else if (paramString2.equals(attributeChangeHistoryItem.getFlagCode())) {
/*      */             
/* 2793 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 2794 */             if (paramString3 == null) {
/* 2795 */               str1 = str2;
/*      */               
/*      */               break;
/*      */             } 
/* 2799 */             if (str2.compareTo(paramString3) < 0) {
/* 2800 */               str1 = str2;
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 2810 */       addError("Error: A request is not valid since there is no history of " + paramString1);
/*      */     } 
/* 2812 */     addDebug("The valfrom value of the " + paramString1 + " at " + ((paramString2 == null) ? "0020|0040" : paramString2) + " which earlier than the givingTime: " + paramString3 + " is " + str1);
/*      */     
/* 2814 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setIDLSTATUS(EntityItem paramEntityItem, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) {
/* 2823 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("STATUS");
/* 2824 */     if (eANMetaAttribute != null) {
/* 2825 */       boolean bool = false;
/* 2826 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2827 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2828 */         if (attributeChangeHistoryItem != null && ("0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode()))) {
/* 2829 */           this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 2830 */           this.curStatusvalue = attributeChangeHistoryItem.getFlagCode();
/* 2831 */           AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */           
/* 2833 */           if (attributeChangeHistoryItem1 != null) {
/* 2834 */             this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/* 2835 */             addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/* 2836 */                 .getFlagCode());
/*      */           } 
/* 2838 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2842 */       if (!bool) {
/* 2843 */         addError(paramEntityItem.getKey() + ", " + this.rsBundle.getString("IDL_NOT_R4RFINAL"));
/*      */       }
/*      */     } else {
/* 2846 */       addError(paramEntityItem.getKey() + " , Error: There is not such attribute STATUS.");
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
/*      */   private String getT2Status(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) throws SQLException, MiddlewareException {
/* 2859 */     String str = "";
/* 2860 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 2861 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 2863 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2864 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2865 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */ 
/*      */           
/* 2869 */           if (attributeChangeHistoryItem.getChangeDate().compareTo(this.t2DTS) < 0) {
/*      */             
/* 2871 */             if (!"0020".equals(attributeChangeHistoryItem.getFlagCode()) && !"0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 2872 */               addError(entityItem.getKey() + " is not Final or R4R");
/* 2873 */               addError(this.rsBundle.getString("NOT_R4RFINAL"), entityItem);
/*      */               break;
/*      */             } 
/* 2876 */             this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 2877 */             this.curStatusvalue = attributeChangeHistoryItem.getFlagCode();
/* 2878 */             str = attributeChangeHistoryItem.getFlagCode();
/* 2879 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */             
/* 2881 */             if (attributeChangeHistoryItem != null) {
/* 2882 */               this.priorStatus = attributeChangeHistoryItem.getAttributeValue();
/* 2883 */               addDebug("getT2Status [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem.getChangeDate() + " flagcode: " + attributeChangeHistoryItem
/* 2884 */                   .getFlagCode());
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 2892 */       addDebug("getT2Status for " + entityItem.getKey() + " getChangeHistoryItemCount less than 0.");
/*      */     } 
/* 2894 */     return str;
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
/*      */   private boolean existDeltaT1(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) {
/* 2906 */     String str1 = this.m_strEpoch;
/* 2907 */     String str2 = null;
/* 2908 */     String str3 = null;
/* 2909 */     String str4 = "";
/*      */ 
/*      */ 
/*      */     
/* 2913 */     boolean bool = false;
/*      */     
/* 2915 */     if (paramAttributeChangeHistoryGroup2 != null && paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() > 0) {
/*      */       
/* 2917 */       for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2918 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/* 2919 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 2922 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString))
/*      */           {
/* 2924 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 2925 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/* 2926 */             if (attributeChangeHistoryItem1 != null) {
/*      */               
/* 2928 */               str3 = attributeChangeHistoryItem1.getChangeDate();
/*      */               
/* 2930 */               str3 = adjustTimeSecond(str3, -30);
/*      */             } else {
/* 2932 */               addDebug("existDeltaT1 for STATUS has no valto value of the stauts of " + paramString);
/*      */             } 
/* 2934 */             addDebug("getDeltaT1 DTS1= " + str2 + " and  DTS2 = " + str3);
/*      */             
/* 2936 */             if (str3 != null && str2 != null) {
/* 2937 */               if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 0) {
/* 2938 */                 for (int j = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/* 2939 */                   AttributeChangeHistoryItem attributeChangeHistoryItem2 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j - 1);
/* 2940 */                   if (attributeChangeHistoryItem2 != null && 
/* 2941 */                     attributeChangeHistoryItem2.getFlagCode().equals("0050")) {
/*      */                     
/* 2943 */                     AttributeChangeHistoryItem attributeChangeHistoryItem3 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j);
/* 2944 */                     if (attributeChangeHistoryItem3.getFlagCode().equals("0030")) {
/* 2945 */                       str4 = attributeChangeHistoryItem2.getChangeDate();
/* 2946 */                       if (str4.compareTo(str2) >= 0 && str4.compareTo(str3) <= 0) {
/* 2947 */                         str1 = str4;
/* 2948 */                         bool = true;
/*      */ 
/*      */ 
/*      */                         
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } else {
/* 2958 */                 addDebug("getDeltaT1 for ADSABRSTATUS has no changed history!");
/*      */               } 
/*      */             }
/*      */             
/* 2962 */             if (bool) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 2971 */       addDebug("getDeltaT1 for STATUS has no changed history!");
/*      */     } 
/* 2973 */     if (str1.equals(this.m_strEpoch)) {
/* 2974 */       addDebug("getDeltaT1 not find the VALFROM of the first ADSABRSTATUS = ÃÂ©Ã¢â¬âÃ¯Â¿Â½ÃÂ§Ã¢â¬Å¡ÃÂ½ÃÂ¥Ã¢â¬Å¾ÃÂ²ÃÂ§ÃÂ»Ã¯Â¿Â½ÃÂ¦Ã¢â¬ï¿½Ã¯Â¿Â½ processÃÂ©Ã¢â¬âÃ¯Â¿Â½ÃÂ§Ã¢â¬Å¡ÃÂ½ÃÂ¥Ã¢â¬Å¾ÃÂ»ÃÂ©ÃÂ½ÃÂ·ÃÂ¯ÃÂ¿ÃÂ½(0050) where DTS1 <= VALFROM <= DTS2");
/*      */     }
/* 2976 */     return bool;
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
/*      */   private String getDeltaT1(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) {
/* 2989 */     String str1 = this.m_strEpoch;
/* 2990 */     String str2 = null;
/* 2991 */     String str3 = null;
/* 2992 */     String str4 = "";
/*      */ 
/*      */ 
/*      */     
/* 2996 */     boolean bool = false;
/*      */     
/* 2998 */     if (paramAttributeChangeHistoryGroup2 != null && paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() > 0) {
/*      */       
/* 3000 */       for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 3001 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/* 3002 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 3005 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString))
/*      */           {
/* 3007 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 3008 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/* 3009 */             if (attributeChangeHistoryItem1 != null) {
/*      */               
/* 3011 */               str3 = attributeChangeHistoryItem1.getChangeDate();
/*      */               
/* 3013 */               str3 = adjustTimeSecond(str3, -30);
/*      */             } else {
/* 3015 */               addDebug("getDeltaT1 for STATUS has no valto value of the stauts of " + paramString);
/*      */             } 
/* 3017 */             addDebug("getDeltaT1 DTS1= " + str2 + " and  DTS2 = " + str3);
/*      */             
/* 3019 */             if (str3 != null && str2 != null) {
/* 3020 */               if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 0) {
/* 3021 */                 for (int j = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/* 3022 */                   AttributeChangeHistoryItem attributeChangeHistoryItem2 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j - 1);
/* 3023 */                   if (attributeChangeHistoryItem2 != null && 
/* 3024 */                     attributeChangeHistoryItem2.getFlagCode().equals("0050")) {
/*      */                     
/* 3026 */                     AttributeChangeHistoryItem attributeChangeHistoryItem3 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j);
/* 3027 */                     if (attributeChangeHistoryItem3.getFlagCode().equals("0030")) {
/* 3028 */                       str4 = attributeChangeHistoryItem2.getChangeDate();
/* 3029 */                       if (str4.compareTo(str2) >= 0 && str4.compareTo(str3) <= 0) {
/* 3030 */                         str1 = str4;
/* 3031 */                         bool = true;
/*      */ 
/*      */ 
/*      */                         
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } else {
/* 3041 */                 addDebug("getDeltaT1 for ADSABRSTATUS has no changed history!");
/*      */               } 
/*      */             }
/*      */             
/* 3045 */             if (bool) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 3054 */       addDebug("getDeltaT1 for STATUS has no changed history!");
/*      */     } 
/* 3056 */     if (str1.equals(this.m_strEpoch)) {
/* 3057 */       addDebug("getDeltaT1 not find the VALFROM of the first ADSABRSTATUS = ÃÂ©Ã¢â¬âÃ¯Â¿Â½ÃÂ§Ã¢â¬Å¡ÃÂ½ÃÂ¥Ã¢â¬Å¾ÃÂ²ÃÂ§ÃÂ»Ã¯Â¿Â½ÃÂ¦Ã¢â¬ï¿½Ã¯Â¿Â½ processÃÂ©Ã¢â¬âÃ¯Â¿Â½ÃÂ§Ã¢â¬Å¡ÃÂ½ÃÂ¥Ã¢â¬Å¾ÃÂ»ÃÂ©ÃÂ½ÃÂ·ÃÂ¯ÃÂ¿ÃÂ½(0050) where DTS1 <= VALFROM <= DTS2");
/*      */     }
/* 3059 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTQStatus(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws MiddlewareRequestException {
/* 3067 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 3069 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 3070 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 3071 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 3074 */           if (paramString.compareTo(attributeChangeHistoryItem.getChangeDate()) > 0) {
/* 3075 */             return attributeChangeHistoryItem.getFlagCode();
/*      */           }
/*      */         }
/*      */       } 
/*      */     } else {
/* 3080 */       addDebug("getTQStatus for STATUS has no changed history!");
/*      */     } 
/* 3082 */     return "@@";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getADSABRSTATUSHistory(String paramString) throws MiddlewareException {
/* 3088 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 3090 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 3091 */     if (eANAttribute != null) {
/* 3092 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 3094 */     addDebug(paramString + " of " + entityItem.getKey() + "  was null");
/* 3095 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getMinTime(String paramString1, String paramString2) {
/* 3106 */     if (paramString1 != null) {
/* 3107 */       if (paramString2 != null) {
/* 3108 */         return (paramString1.compareTo(paramString2) > 0) ? paramString2 : paramString1;
/*      */       }
/* 3110 */       return paramString1;
/*      */     } 
/*      */     
/* 3113 */     if (paramString2 != null) {
/* 3114 */       return paramString2;
/*      */     }
/* 3116 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ paramXMLMQ) throws MiddlewareException {
/* 3123 */     String str = paramXMLMQ.getStatusAttr();
/* 3124 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 3125 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 3126 */     if (eANAttribute != null) {
/* 3127 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 3129 */     addDebug(" STATUS of " + entityItem.getKey() + "  was null");
/* 3130 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existPassedFinal(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2) throws MiddlewareRequestException {
/* 3137 */     boolean bool1 = false;
/* 3138 */     boolean bool2 = false;
/* 3139 */     if (paramAttributeChangeHistoryGroup1 != null) {
/* 3140 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/*      */         
/* 3142 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 3143 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */ 
/*      */           
/* 3147 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 3149 */             bool1 = true;
/*      */           }
/* 3151 */           if (bool1 && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 3152 */             String str = getTQStatus(paramAttributeChangeHistoryGroup2, attributeChangeHistoryItem.getChangeDate());
/* 3153 */             if (str.equals("0020")) {
/* 3154 */               bool2 = true;
/*      */               break;
/*      */             } 
/* 3157 */             bool1 = false;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3164 */     return bool2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVEFiltered(String paramString) {
/* 3170 */     for (byte b = 0; b < VE_Filter_Array.length; b++) {
/* 3171 */       if (VE_Filter_Array[b][0].equals(paramString))
/* 3172 */         return true; 
/*      */     } 
/* 3174 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVECountryFiltered(String paramString) {
/* 3181 */     for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/* 3182 */       if (VE_Country_Filter_Array[b][0].equals(paramString))
/* 3183 */         return true; 
/*      */     } 
/* 3185 */     return false;
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
/*      */   private Profile switchRole(String paramString) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 3200 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 3201 */     if (profile == null) {
/* 3202 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 3204 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 3206 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 3207 */       addDebug("switchRole nlsids: " + str);
/* 3208 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 3209 */       while (stringTokenizer.hasMoreTokens()) {
/* 3210 */         String str1 = stringTokenizer.nextToken();
/* 3211 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 3212 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 3213 */           profile.getReadLanguages().addElement(nLSItem);
/* 3214 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3219 */     return profile;
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
/*      */   public Profile switchRoles(String paramString) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 3233 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 3234 */     if (profile == null) {
/* 3235 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 3237 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 3239 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 3240 */       addDebug("switchRole nlsids: " + str);
/* 3241 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 3242 */       while (stringTokenizer.hasMoreTokens()) {
/* 3243 */         String str1 = stringTokenizer.nextToken();
/* 3244 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 3245 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 3246 */           profile.getReadLanguages().addElement(nLSItem);
/* 3247 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3252 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 3258 */     return this.m_db;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/* 3263 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 3268 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addMSGLOGReason(String paramString) {
/* 3273 */     this.reason.append(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 3284 */     if (3 <= DEBUG_LVL) {
/* 3285 */       if (this.dbgPw != null) {
/* 3286 */         this.dbgLen += paramString.length();
/* 3287 */         this.dbgPw.println(paramString);
/* 3288 */         this.dbgPw.flush();
/*      */       } else {
/* 3290 */         this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDebugComment(int paramInt, String paramString) {
/* 3301 */     if (paramInt <= DEBUG_LVL) {
/* 3302 */       if (this.dbgPw != null) {
/* 3303 */         this.dbgLen += paramString.length();
/* 3304 */         this.dbgPw.println(paramString);
/* 3305 */         this.dbgPw.flush();
/*      */       } else {
/* 3307 */         this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 3315 */     addOutput(paramString);
/* 3316 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 3324 */     String str = getLD_NDN(paramEntityItem);
/* 3325 */     addOutput(str + " " + paramString);
/* 3326 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/* 3332 */     return this.rsBundle;
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
/*      */   protected void notify(XMLMQ paramXMLMQ, String paramString1, String paramString2, Vector<String> paramVector) throws MissingResourceException {
/* 3344 */     MessageFormat messageFormat = null;
/*      */     
/* 3346 */     byte b1 = 0;
/* 3347 */     boolean bool = false;
/*      */     
/* 3349 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*      */       
/* 3351 */       String str = paramVector.elementAt(b2);
/* 3352 */       addDebug("in notify looking at prop file " + str);
/*      */       try {
/* 3354 */         ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/* 3355 */             getLocale(getProfile().getReadLanguage().getNLSID()));
/* 3356 */         Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 3357 */         boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/* 3358 */         hashtable.put("MQCID", paramXMLMQ.getMQCID());
/* 3359 */         hashtable.put("XMLTYPE", "ADS");
/* 3360 */         Hashtable hashtable1 = MQUsage.getUserProperties(resourceBundle, paramXMLMQ.getMQCID());
/* 3361 */         if (bool1) {
/*      */           try {
/* 3363 */             addDebug("User infor " + hashtable1);
/* 3364 */             MQUsage.putToMQQueueWithRFH2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable, hashtable1);
/*      */             
/* 3366 */             messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/* 3367 */             this.args[0] = str;
/* 3368 */             this.args[1] = paramString1;
/* 3369 */             addOutput(messageFormat.format(this.args));
/* 3370 */             b1++;
/*      */             
/* 3372 */             this.succQueueNameVct.add(str);
/* 3373 */             if (!bool) {
/*      */               
/* 3375 */               addXMLGenMsg("SUCCESS", paramString1);
/* 3376 */               addDebug("sent successfully to prop file " + str);
/*      */             } 
/* 3378 */           } catch (MQException mQException) {
/*      */ 
/*      */             
/* 3381 */             addXMLGenMsg("FAILED", paramString1);
/* 3382 */             bool = true;
/* 3383 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/* 3384 */             this.args[0] = str + " " + paramString1;
/* 3385 */             this.args[1] = "" + mQException.completionCode;
/* 3386 */             this.args[2] = "" + mQException.reasonCode;
/* 3387 */             addMSGLOGReason(messageFormat.format(this.args));
/* 3388 */             addError(messageFormat.format(this.args));
/* 3389 */             mQException.printStackTrace(System.out);
/* 3390 */             addDebug("failed sending to prop file " + str);
/* 3391 */           } catch (IOException iOException) {
/*      */             
/* 3393 */             addXMLGenMsg("FAILED", paramString1);
/* 3394 */             bool = true;
/* 3395 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/* 3396 */             this.args[0] = str + " " + paramString1;
/* 3397 */             this.args[1] = iOException.toString();
/* 3398 */             addMSGLOGReason(messageFormat.format(this.args));
/* 3399 */             addError(messageFormat.format(this.args));
/* 3400 */             iOException.printStackTrace(System.out);
/* 3401 */             addDebug("failed sending to prop file " + str);
/*      */           } 
/*      */         } else {
/*      */           
/* 3405 */           messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/* 3406 */           this.args[0] = str;
/* 3407 */           addMSGLOGReason("XML was generated but NOTIFY was false in the {0} properties file.");
/* 3408 */           addError(messageFormat.format(this.args));
/*      */           
/* 3410 */           addXMLGenMsg("NOT_SENT", paramString1);
/* 3411 */           addDebug("not sent to prop file " + str + " because Notify not true");
/*      */         }
/*      */       
/* 3414 */       } catch (MissingResourceException missingResourceException) {
/* 3415 */         addXMLGenMsg("FAILED", str + " " + paramString1);
/* 3416 */         bool = true;
/* 3417 */         addMSGLOGReason("Prop file " + str + " " + paramString1 + " not found");
/* 3418 */         addError("Prop file " + str + " " + paramString1 + " not found");
/*      */       } 
/*      */     } 
/*      */     
/* 3422 */     if (b1 > 0 && b1 != paramVector.size()) {
/* 3423 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
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
/*      */   protected String transformXML(XMLMQ paramXMLMQ, Document paramDocument) throws ParserConfigurationException, TransformerException {
/* 3435 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 3436 */     Transformer transformer = transformerFactory.newTransformer();
/* 3437 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 3439 */     transformer.setOutputProperty("indent", "no");
/* 3440 */     transformer.setOutputProperty("method", "xml");
/* 3441 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */ 
/*      */     
/* 3445 */     String str1 = paramDocument.getDocumentElement().getNodeName();
/*      */     
/* 3447 */     if (str1.contains("MODEL_UPDATE")) {
/* 3448 */       UpdateXML updateXML = new UpdateXML();
/* 3449 */       paramDocument = updateXML.update(paramDocument, updateXML.store(paramDocument));
/*      */     } 
/*      */ 
/*      */     
/* 3453 */     StringWriter stringWriter = new StringWriter();
/* 3454 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 3455 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 3456 */     transformer.transform(dOMSource, streamResult);
/* 3457 */     String str2 = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */ 
/*      */     
/* 3461 */     transformer.setOutputProperty("indent", "yes");
/* 3462 */     stringWriter = new StringWriter();
/* 3463 */     streamResult = new StreamResult(stringWriter);
/* 3464 */     transformer.transform(dOMSource, streamResult);
/*      */ 
/*      */     
/* 3467 */     if (!USERXML_OFF_LOG) {
/* 3468 */       addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     }
/*      */     
/* 3471 */     return str2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String transformCacheXML(XMLMQ paramXMLMQ, Document paramDocument) throws ParserConfigurationException, TransformerException {
/* 3482 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 3483 */     Transformer transformer = transformerFactory.newTransformer();
/* 3484 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 3486 */     transformer.setOutputProperty("indent", "no");
/* 3487 */     transformer.setOutputProperty("method", "xml");
/* 3488 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 3491 */     parseAndConvert(paramDocument);
/*      */ 
/*      */     
/* 3494 */     StringWriter stringWriter = new StringWriter();
/* 3495 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 3496 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 3497 */     transformer.transform(dOMSource, streamResult);
/* 3498 */     return XMLElem.removeCheat(stringWriter.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void parseAndConvert(Node paramNode) {
/* 3509 */     if (paramNode.hasChildNodes()) {
/* 3510 */       NodeList nodeList = paramNode.getChildNodes();
/*      */       
/* 3512 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 3513 */         Node node = nodeList.item(b);
/* 3514 */         if (node.hasChildNodes()) {
/* 3515 */           parseAndConvert(node);
/*      */         } else {
/*      */           
/* 3518 */           node.setNodeValue(convertquotToHTML(node.getNodeValue()));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void addUserXML(String paramString) {
/* 3525 */     if (this.userxmlPw != null) {
/* 3526 */       this.userxmlLen += paramString.length();
/* 3527 */       this.userxmlPw.println(paramString);
/* 3528 */       this.userxmlPw.flush();
/*      */     } else {
/* 3530 */       this.userxmlSb.append(convertToHTML(paramString) + NEWLINE);
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
/*      */   protected boolean domainNeedsChecks(EntityItem paramEntityItem) {
/* 3544 */     boolean bool = false;
/* 3545 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 3546 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 3547 */     if (str.equals("all")) {
/* 3548 */       bool = true;
/*      */     } else {
/* 3550 */       HashSet<String> hashSet = new HashSet();
/* 3551 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 3552 */       while (stringTokenizer.hasMoreTokens()) {
/* 3553 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 3555 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 3556 */       hashSet.clear();
/*      */     } 
/*      */     
/* 3559 */     if (!bool) {
/* 3560 */       addDebug("PDHDOMAIN for " + paramEntityItem.getKey() + " did not include " + str + ", execution is bypassed [" + 
/* 3561 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/* 3563 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale getLocale(int paramInt) {
/* 3573 */     Locale locale = null;
/* 3574 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 3577 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3601 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 3611 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 3620 */     return "ADSABRSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String convertToHTML(String paramString) {
/* 3631 */     String str = "";
/* 3632 */     StringBuffer stringBuffer = new StringBuffer();
/* 3633 */     StringCharacterIterator stringCharacterIterator = null;
/* 3634 */     char c = ' ';
/* 3635 */     if (paramString != null) {
/* 3636 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 3637 */       c = stringCharacterIterator.first();
/* 3638 */       while (c != '￿') {
/*      */         
/* 3640 */         switch (c) {
/*      */           
/*      */           case '<':
/* 3643 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 3646 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */           
/*      */           case '"':
/* 3651 */             stringBuffer.append("&quot;");
/*      */             break;
/*      */           
/*      */           case '\'':
/* 3655 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 3664 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 3667 */         c = stringCharacterIterator.next();
/*      */       } 
/* 3669 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 3672 */     return str;
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
/*      */   protected static String convertquotToHTML(String paramString) {
/* 3686 */     String str = "";
/* 3687 */     StringBuffer stringBuffer = new StringBuffer();
/* 3688 */     StringCharacterIterator stringCharacterIterator = null;
/* 3689 */     char c = ' ';
/* 3690 */     if (paramString != null) {
/* 3691 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 3692 */       c = stringCharacterIterator.first();
/* 3693 */       while (c != '￿') {
/*      */         
/* 3695 */         switch (c) {
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/* 3700 */             stringBuffer.append("@amp;quot;");
/*      */             break;
/*      */           
/*      */           case '\'':
/* 3704 */             stringBuffer.append("@amp;#39;");
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 3713 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 3716 */         c = stringCharacterIterator.next();
/*      */       } 
/* 3718 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 3721 */     return str;
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
/*      */   private void deactivateMultiFlagValue(String paramString) throws SQLException, MiddlewareException {
/* 3736 */     logMessage(getDescription() + " ***** " + paramString);
/* 3737 */     addDebug("deactivateFlagValue entered for " + paramString);
/* 3738 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 3741 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString);
/* 3742 */     if (eANMetaAttribute == null) {
/* 3743 */       addDebug("deactivateFlagValue: " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/* 3745 */       logMessage(getDescription() + " ***** " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 3749 */     String str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/* 3750 */     if (str != null) {
/*      */       try {
/* 3752 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/* 3753 */         Vector<ReturnEntityKey> vector = new Vector();
/* 3754 */         Vector<MultipleFlag> vector1 = new Vector();
/* 3755 */         EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 3756 */         if (eANAttribute != null) {
/* 3757 */           String str1 = eANAttribute.getEffFrom();
/* 3758 */           ControlBlock controlBlock = new ControlBlock(str1, str1, str1, str1, this.m_prof.getOPWGID());
/* 3759 */           StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/* 3760 */           while (stringTokenizer.hasMoreTokens()) {
/* 3761 */             String str2 = stringTokenizer.nextToken();
/*      */             
/* 3763 */             MultipleFlag multipleFlag = new MultipleFlag(this.m_prof.getEnterprise(), entityItem.getEntityType(), entityItem.getEntityID(), paramString, str2, 1, controlBlock);
/* 3764 */             vector1.addElement(multipleFlag);
/*      */           } 
/* 3766 */           returnEntityKey.m_vctAttributes = vector1;
/* 3767 */           vector.addElement(returnEntityKey);
/* 3768 */           this.m_db.update(this.m_prof, vector, false, false);
/* 3769 */           addDebug(entityItem.getKey() + " had " + paramString + " deactivated");
/*      */         } 
/*      */       } finally {
/*      */         
/* 3773 */         this.m_db.commit();
/* 3774 */         this.m_db.freeStatement();
/* 3775 */         this.m_db.isPending("finally after deactivate value");
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
/*      */   private void setFlagValue(String paramString1, String paramString2) throws SQLException, MiddlewareException {
/* 3795 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 3796 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/* 3797 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 3800 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 3801 */     if (eANMetaAttribute == null) {
/* 3802 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/* 3803 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + entityItem
/* 3804 */           .getEntityType() + ", nothing to do");
/*      */       return;
/*      */     } 
/* 3807 */     if (paramString2 != null)
/*      */     {
/* 3809 */       if (paramString2.equals(getAttributeFlagEnabledValue(entityItem, paramString1))) {
/* 3810 */         addDebug("setFlagValue " + entityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/* 3815 */           if (this.m_cbOn == null) {
/* 3816 */             setControlBlock();
/*      */           }
/* 3818 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*      */           
/* 3820 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */           
/* 3822 */           Vector<SingleFlag> vector = new Vector();
/* 3823 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 3824 */           vector.addElement(singleFlag);
/* 3825 */           returnEntityKey.m_vctAttributes = vector;
/* 3826 */           vector1.addElement(returnEntityKey);
/*      */           
/* 3828 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 3829 */           addDebug(entityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*      */         } finally {
/*      */           
/* 3832 */           this.m_db.commit();
/* 3833 */           this.m_db.freeStatement();
/* 3834 */           this.m_db.isPending("finally after update in setflag value");
/*      */         } 
/*      */       }  } 
/*      */   }
/*      */   
/*      */   private String sysFeedResendStatus(String paramString1, String paramString2, String paramString3) {
/* 3840 */     return ABRServerProperties.getValue(paramString1, paramString2, paramString3);
/*      */   }
/*      */   
/*      */   protected String getQueuedValue(String paramString) {
/* 3844 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 3845 */     if (str == null) {
/* 3846 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 3847 */       return "0020";
/*      */     } 
/* 3849 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 3850 */     return ABRServerProperties.getABRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getRFRQueuedValue(String paramString) {
/* 3855 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 3856 */     if (str == null) {
/* 3857 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 3858 */       return "0020";
/*      */     } 
/* 3860 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 3861 */     return ABRServerProperties.getABRRFRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */   
/*      */   protected boolean fullMode() {
/* 3865 */     String str = ABRServerProperties.getValue("ADSABRSTATUS", "_FullMode");
/* 3866 */     if (str.equals("true") || str.equals("True") || str.equals("TRUE")) {
/* 3867 */       addDebug("ADSABRSTATUS is running in FullMode!");
/* 3868 */       return true;
/*      */     } 
/* 3870 */     addDebug("ADSABRSTATUS is not running in FullMode!");
/* 3871 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isPeriodicABR() {
/* 3875 */     return this.isPeriodicABR;
/*      */   }
/*      */   protected boolean isXMLCACHE() {
/* 3878 */     return this.isXMLCACHE;
/*      */   }
/*      */   protected boolean isSystemResendRFR() {
/* 3881 */     return this.isSystemResendRFR;
/*      */   }
/*      */   
/*      */   protected boolean isSystemResendCache() {
/* 3885 */     return this.isSystemResendCache;
/*      */   }
/*      */   
/*      */   protected boolean isSystemResendCacheExist() {
/* 3889 */     return this.isSystemResendCacheExist;
/*      */   }
/*      */   
/*      */   protected String getSystemResendCacheXml() {
/* 3893 */     return this.SystemResendCacheXml;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeItem(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 3902 */     addDebug("removeItem: " + paramEntityItem.getKey() + " ei.getUpLinkCount() " + paramEntityItem.getUpLinkCount() + " ei.getDownLinkCount() " + paramEntityItem
/* 3903 */         .getDownLinkCount());
/*      */     
/* 3905 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 3908 */     EntityItem[] arrayOfEntityItem1 = new EntityItem[paramEntityItem.getUpLinkCount()];
/* 3909 */     paramEntityItem.getUpLink().copyInto((Object[])arrayOfEntityItem1);
/*      */     
/* 3911 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[paramEntityItem.getDownLinkCount()];
/* 3912 */     paramEntityItem.getDownLink().copyInto((Object[])arrayOfEntityItem2);
/*      */     
/*      */     byte b;
/*      */     
/* 3916 */     for (b = 0; b < arrayOfEntityItem1.length; b++) {
/*      */       
/* 3918 */       EntityItem entityItem = arrayOfEntityItem1[b];
/* 3919 */       addDebug("uplink: " + entityItem.getKey());
/*      */ 
/*      */       
/* 3922 */       paramEntityItem.removeUpLink((EANEntity)entityItem);
/*      */ 
/*      */ 
/*      */       
/* 3926 */       if (paramEntityGroup.containsEntityItem(entityItem.getEntityType(), entityItem.getEntityID())) {
/* 3927 */         arrayOfEntityItem1[b] = null;
/* 3928 */         addDebug("uplink: upRelator is root " + entityItem.getKey());
/*      */       
/*      */       }
/* 3931 */       else if (entityItem.getDownLinkCount() > 0) {
/* 3932 */         arrayOfEntityItem1[b] = null;
/* 3933 */         addDebug("uplink: upRelator " + entityItem.getKey() + " has more downlinks ");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 3938 */         EntityItem[] arrayOfEntityItem = new EntityItem[entityItem.getUpLinkCount()];
/* 3939 */         entityItem.getUpLink().copyInto((Object[])arrayOfEntityItem);
/* 3940 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 3941 */           EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 3942 */           addDebug("uplink: parentitem  " + entityItem1.getKey());
/*      */ 
/*      */           
/* 3945 */           entityItem.removeUpLink((EANEntity)entityItem1);
/*      */           
/* 3947 */           if (!paramEntityGroup.containsEntityItem(entityItem1.getEntityType(), entityItem1.getEntityID())) {
/* 3948 */             if (!vector.contains(entityItem1)) {
/* 3949 */               vector.add(entityItem1);
/*      */             }
/*      */           } else {
/* 3952 */             addDebug("uplink: parentitem is root " + entityItem1.getKey());
/*      */           } 
/*      */           
/* 3955 */           arrayOfEntityItem[b1] = null;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3959 */     for (b = 0; b < arrayOfEntityItem2.length; b++) {
/*      */       
/* 3961 */       EntityItem entityItem = arrayOfEntityItem2[b];
/* 3962 */       addDebug("Downlink: " + entityItem.getKey());
/*      */       
/* 3964 */       paramEntityItem.removeDownLink((EANEntity)entityItem);
/*      */ 
/*      */ 
/*      */       
/* 3968 */       if (paramEntityGroup.containsEntityItem(entityItem.getEntityType(), entityItem.getEntityID())) {
/* 3969 */         arrayOfEntityItem2[b] = null;
/* 3970 */         addDebug("Downlink: downRelator is root " + entityItem.getKey());
/*      */       
/*      */       }
/* 3973 */       else if (entityItem.getUpLinkCount() > 0) {
/* 3974 */         arrayOfEntityItem2[b] = null;
/* 3975 */         addDebug("Downlink: downRelator " + entityItem.getKey() + " has more uplinks ");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 3980 */         EntityItem[] arrayOfEntityItem = new EntityItem[entityItem.getDownLinkCount()];
/* 3981 */         entityItem.getDownLink().copyInto((Object[])arrayOfEntityItem);
/* 3982 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 3983 */           EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 3984 */           addDebug("Downlink: childitem " + entityItem1.getKey());
/*      */           
/* 3986 */           entityItem.removeDownLink((EANEntity)entityItem1);
/*      */ 
/*      */           
/* 3989 */           if (!paramEntityGroup.containsEntityItem(entityItem1.getEntityType(), entityItem1.getEntityID())) {
/* 3990 */             if (!vector.contains(entityItem1)) {
/* 3991 */               vector.add(entityItem1);
/*      */             }
/*      */           } else {
/* 3994 */             addDebug("Downlink: childitem is root " + entityItem1.getKey());
/*      */           } 
/* 3996 */           arrayOfEntityItem[b1] = null;
/*      */         } 
/*      */       } 
/*      */     } 
/* 4000 */     removeFilteredEntityItem(paramEntityItem);
/*      */     
/* 4002 */     for (b = 0; b < arrayOfEntityItem1.length; b++) {
/*      */       
/* 4004 */       removeFilteredEntityItem(arrayOfEntityItem1[b]);
/* 4005 */       arrayOfEntityItem1[b] = null;
/*      */     } 
/*      */     
/* 4008 */     for (b = 0; b < arrayOfEntityItem2.length; b++) {
/*      */       
/* 4010 */       removeFilteredEntityItem(arrayOfEntityItem2[b]);
/* 4011 */       arrayOfEntityItem2[b] = null;
/*      */     } 
/*      */ 
/*      */     
/* 4015 */     for (b = 0; b < vector.size(); b++) {
/* 4016 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 4018 */       if (!entityItem.hasDownLinks() && !entityItem.hasUpLinks()) {
/* 4019 */         removeFilteredEntityItem(entityItem);
/*      */       }
/*      */     } 
/* 4022 */     vector.clear();
/* 4023 */     vector = null;
/* 4024 */     arrayOfEntityItem1 = null;
/* 4025 */     arrayOfEntityItem2 = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeFilteredEntityItem(EntityItem paramEntityItem) {
/* 4033 */     if (paramEntityItem != null) {
/* 4034 */       addDebug("removeFilteredEntityItem " + paramEntityItem.getKey());
/* 4035 */       EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */       
/* 4037 */       paramEntityItem.setParent(null);
/*      */       
/* 4039 */       for (int i = paramEntityItem.getAttributeCount() - 1; i >= 0; i--) {
/* 4040 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(i);
/* 4041 */         paramEntityItem.removeAttribute(eANAttribute);
/*      */       } 
/*      */       
/* 4044 */       entityGroup.removeEntityItem(paramEntityItem);
/*      */     } 
/*      */   }
/*      */   private HashSet getCountry(EntityItem paramEntityItem, String paramString) {
/* 4048 */     HashSet<String> hashSet = new HashSet();
/*      */     
/* 4050 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/* 4051 */     addDebug("ADSABRSTATUS.getCountry for entity " + paramEntityItem.getKey() + " fAtt " + 
/* 4052 */         PokUtils.getAttributeFlagValue(paramEntityItem, paramString) + NEWLINE);
/* 4053 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 4055 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 4056 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 4058 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 4059 */           hashSet.add(arrayOfMetaFlag[b].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/* 4063 */     return hashSet;
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
/*      */   private String adjustTimeSecond(String paramString, int paramInt) {
/* 4077 */     String str1 = "yyyy-MM-dd-HH.mm.ss";
/* 4078 */     String str2 = "&nbsp;";
/* 4079 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str1);
/* 4080 */     Calendar calendar = Calendar.getInstance();
/* 4081 */     if (!"&nbsp;".equals(paramString)) {
/*      */       try {
/* 4083 */         calendar.setTime(simpleDateFormat.parse(paramString));
/* 4084 */         calendar.add(13, paramInt);
/* 4085 */         str2 = simpleDateFormat.format(calendar.getTime()) + paramString.substring(19);
/* 4086 */       } catch (ParseException parseException) {
/* 4087 */         addDebug("failed add second to T1 or T2. Second is: " + paramInt);
/* 4088 */       } catch (Exception exception) {
/* 4089 */         addDebug("failed add second to T1 or T2. Second is: " + paramInt);
/*      */       } 
/*      */     }
/*      */     
/* 4093 */     return str2;
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
/*      */   protected void putXMLIDLCache(EntityList paramEntityList, String paramString1, String paramString2) throws MiddlewareException, SQLException {
/* 4115 */     String str1 = this.m_strEpoch;
/* 4116 */     if (this.isXMLIDLABR) {
/* 4117 */       str1 = this.t2DTS;
/*      */     } else {
/*      */       
/* 4120 */       str1 = paramString2;
/*      */     } 
/*      */     
/* 4123 */     addDebug("get XMLCACHEDTS: " + str1);
/* 4124 */     EntityItem entityItem = paramEntityList.getParentEntityGroup().getEntityItem(0);
/* 4125 */     String str2 = entityItem.getEntityType();
/* 4126 */     String str3 = PokUtils.getAttributeFlagValue(entityItem, "OLDINDC");
/*      */     
/* 4128 */     int i = entityItem.getEntityID();
/* 4129 */     PreparedStatement preparedStatement = null;
/* 4130 */     Statement statement = null;
/*      */     
/* 4132 */     ResultSet resultSet = null;
/* 4133 */     boolean bool1 = false;
/* 4134 */     boolean bool2 = false;
/* 4135 */     addDebug("rootType :" + str2);
/* 4136 */     addDebug("entityID :" + i);
/*      */     
/* 4138 */     StringBuffer stringBuffer = new StringBuffer();
/* 4139 */     stringBuffer.append("SELECT xmlcachedts FROM cache.xmlidlcache");
/* 4140 */     stringBuffer.append(" WHERE xmlentitytype = '" + str2 + "'");
/* 4141 */     stringBuffer.append(" AND xmlentityID = " + i);
/* 4142 */     stringBuffer.append(" AND xmlcachevalidto > current timestamp");
/*      */     
/*      */     try {
/*      */       try {
/* 4146 */         statement = this.m_db.getODSConnection().createStatement();
/* 4147 */         resultSet = statement.executeQuery(stringBuffer.toString());
/*      */         
/* 4149 */         while (resultSet.next()) {
/* 4150 */           bool1 = true;
/* 4151 */           String str = resultSet.getString(1);
/* 4152 */           if (str1.compareTo(str) > 0) {
/* 4153 */             bool2 = true;
/*      */           }
/*      */         } 
/* 4156 */         addDebug("get XMLIDLCache where" + entityItem.getKey() + " is exist: " + bool1 + " is need update: " + bool2);
/* 4157 */       } catch (MiddlewareException middlewareException) {
/* 4158 */         addDebug("MiddlewareException on ? " + middlewareException);
/* 4159 */         middlewareException.printStackTrace();
/* 4160 */         throw middlewareException;
/* 4161 */       } catch (SQLException sQLException) {
/* 4162 */         addDebug("RuntimeException on ? " + sQLException);
/* 4163 */         sQLException.printStackTrace();
/* 4164 */         throw sQLException;
/*      */       } 
/* 4166 */       if (bool1 && bool2) {
/* 4167 */         stringBuffer = new StringBuffer();
/* 4168 */         stringBuffer.append("UPDATE cache.xmlidlcache");
/* 4169 */         stringBuffer.append(" SET xmlcachevalidto = ?");
/* 4170 */         if (str3 != null && !"".equals(str3.trim())) {
/* 4171 */           stringBuffer.append(" , OLDINDC = ? ");
/*      */         }
/* 4173 */         stringBuffer.append(" WHERE xmlentitytype = '" + str2 + "'");
/* 4174 */         stringBuffer.append(" AND xmlentityID = " + i);
/* 4175 */         stringBuffer.append(" AND xmlcachevalidto > current timestamp");
/*      */         
/*      */         try {
/* 4178 */           preparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/* 4179 */           preparedStatement.setString(1, str1);
/* 4180 */           if (str3 != null && !"".equals(str3.trim())) {
/* 4181 */             preparedStatement.setString(2, str3);
/*      */           }
/* 4183 */           preparedStatement.execute();
/* 4184 */           if (this.m_db.getODSConnection() != null) {
/* 4185 */             this.m_db.getODSConnection().commit();
/*      */           }
/* 4187 */         } catch (MiddlewareException middlewareException) {
/* 4188 */           addDebug("MiddlewareException on ? " + middlewareException);
/* 4189 */           middlewareException.printStackTrace();
/* 4190 */           throw middlewareException;
/* 4191 */         } catch (SQLException sQLException) {
/* 4192 */           addDebug("SQLException on ? " + sQLException);
/* 4193 */           sQLException.printStackTrace();
/* 4194 */           throw sQLException;
/*      */         } finally {
/* 4196 */           if (preparedStatement != null) {
/*      */             try {
/* 4198 */               preparedStatement.close();
/* 4199 */             } catch (SQLException sQLException) {
/* 4200 */               sQLException.printStackTrace();
/*      */             } 
/*      */           }
/*      */         } 
/* 4204 */         insertIntoCache(entityItem, paramString1, str1, str2, i, preparedStatement);
/* 4205 */       } else if (!bool1) {
/*      */ 
/*      */         
/* 4208 */         insertIntoCache(entityItem, paramString1, str1, str2, i, preparedStatement);
/*      */       } 
/* 4210 */       addDebug("put cache:" + this.m_abri.getABRCode() + "-" + getEntityType());
/* 4211 */       if ("SVCMOD".equals(getEntityType()) && !this.m_abri.getABRCode().equals("SVCMODIERPABRSTATUS")) {
/* 4212 */         setFlagValue("SVCMODIERPABRSTATUS", "0020");
/*      */       }
/* 4214 */       else if ("MODEL".equals(getEntityType())) {
/*      */         
/* 4216 */         String str4 = PokUtils.getAttributeValue(entityItem, "COFSUBCAT", "", "");
/* 4217 */         String str5 = PokUtils.getAttributeValue(entityItem, "OLDINDC", "", "");
/*      */         
/* 4219 */         addDebug("subCat:" + str4 + " oldindc:" + str5);
/*      */         
/* 4221 */         if ("Shadow".equals(str4) || "Y".equals(str5)) {
/* 4222 */           addDebug("Skip trigger IERP caller for MODEL :subCat:" + str4 + " oldindc:" + str5);
/*      */         } else {
/*      */           
/* 4225 */           setFlagValue("MODELIERPABRSTATUS", "0020");
/* 4226 */           String str6 = PokUtils.getAttributeValue(entityItem, "COFCAT", "", "");
/* 4227 */           addDebug("MODELCOFCAT:" + str6);
/* 4228 */           D.ebug(0, "MODELCOFCAT:" + str6);
/* 4229 */           if ("Hardware".equals(str6)) {
/* 4230 */             setFlagValue("MODELGARSABRSTATUS", "0020");
/*      */           }
/*      */ 
/*      */           
/* 4234 */           String str7 = PokUtils.getAttributeFlagValue(entityItem, "WARRSVCCOVR");
/* 4235 */           addDebug("WARRSVCCOVR:" + str7);
/* 4236 */           if ("WSVC02".equals(str7))
/*      */           {
/* 4238 */             setFlagValue("MODELWARRABRSTATUS", "0020");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 4261 */       else if ("PRODSTRUCT".equals(getEntityType())) {
/* 4262 */         String str = PokUtils.getAttributeValue(entityItem, "OLDINDC", "", "");
/* 4263 */         addDebug("oldindc:" + str);
/* 4264 */         if ("Y".equals(str)) {
/* 4265 */           addDebug("Skip trigger IERP caller for TMF : oldindc:" + str);
/*      */         } else {
/*      */           
/* 4268 */           setFlagValue("TMFIERPABRSTATUS", "0020");
/* 4269 */           String str4 = PokUtils.getAttributeFlagValue(entityItem, "WARRSVCCOVR");
/* 4270 */           addDebug("WARRSVCCOVR:" + str4);
/* 4271 */           if ("WSVC02".equals(str4)) {
/* 4272 */             setFlagValue("TMFWARRABRSTATUS", "0020");
/*      */           }
/*      */           
/* 4275 */           String str5 = PokUtils.getAttributeFlagValue(entityItem, "BULKMESINDC");
/* 4276 */           if ("MES0001".equals(str5))
/*      */           {
/* 4278 */             setFlagValue("TMFBULKABRSTATUS", "0020");
/*      */           }
/*      */         } 
/* 4281 */       } else if ("FEATURE".equals(getEntityType())) {
/* 4282 */         setFlagValue("FEATUREIERPABRSTATUS", "0020");
/*      */       }
/* 4284 */       else if ("FCTRANSACTION".equals(getEntityType())) {
/* 4285 */         setFlagValue("FCTRANSACTIONIERPABRSTATUS", "0020");
/*      */       }
/* 4287 */       else if ("MODELCONVERT".equals(getEntityType())) {
/* 4288 */         setFlagValue("MODELCONVERTIERPABRSTATUS", "0020");
/* 4289 */       } else if ("WARR".equals(getEntityType())) {
/* 4290 */         setFlagValue("WARRIERPABRSTATUS", "0020");
/*      */       } 
/*      */     } finally {
/*      */       
/*      */       try {
/* 4295 */         this.m_db.commit();
/* 4296 */         if (statement != null)
/* 4297 */           statement.close(); 
/* 4298 */       } catch (SQLException sQLException) {
/* 4299 */         sQLException.printStackTrace();
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
/*      */   private void insertIntoCache(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, int paramInt, PreparedStatement paramPreparedStatement) throws SQLException, MiddlewareException {
/* 4319 */     String[] arrayOfString = (String[])FILTER_TBL.get(paramString3);
/*      */     
/* 4321 */     String str1 = "";
/* 4322 */     String str2 = "";
/* 4323 */     String str3 = null;
/* 4324 */     String str4 = "";
/* 4325 */     String str5 = "";
/* 4326 */     long l1 = System.currentTimeMillis();
/* 4327 */     long l2 = 0L;
/*      */     
/* 4329 */     if (arrayOfString != null) {
/* 4330 */       String str7 = arrayOfString[0];
/*      */       
/* 4332 */       String str8 = arrayOfString[1];
/*      */       
/* 4334 */       String str9 = arrayOfString[2];
/* 4335 */       if (!"".equals(str7)) {
/* 4336 */         if (!"LSEO".equals(paramEntityItem.getEntityType())) {
/* 4337 */           str1 = PokUtils.getAttributeFlagValue(paramEntityItem, str7);
/* 4338 */           if (str1 == null) {
/* 4339 */             str1 = "";
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 4349 */           if (paramEntityItem != null) {
/* 4350 */             Vector<EntityItem> vector1 = new Vector();
/* 4351 */             Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "WWSEO");
/* 4352 */             for (byte b = 0; b < vector2.size(); b++) {
/* 4353 */               EntityItem entityItem = vector2.elementAt(b);
/* 4354 */               vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELWWSEO", "MODEL");
/* 4355 */               if (vector1.size() > 0)
/*      */                 break; 
/*      */             } 
/* 4358 */             if (vector1.size() > 0) {
/* 4359 */               EntityItem entityItem = vector1.elementAt(0);
/* 4360 */               str1 = PokUtils.getAttributeFlagValue(entityItem, str7);
/* 4361 */               if (str1 == null) {
/* 4362 */                 str1 = "";
/*      */               }
/*      */             } 
/*      */           } 
/* 4366 */           l2 = System.currentTimeMillis();
/* 4367 */           addDebugComment(3, "Time for get COFCAT from ListT2: " + Stopwatch.format(l2 - l1));
/*      */         } 
/*      */       }
/* 4370 */       if (!"".equals(str8)) {
/* 4371 */         str2 = PokUtils.getAttributeFlagValue(paramEntityItem, str8);
/* 4372 */         if (str2 == null)
/* 4373 */           str2 = ""; 
/*      */       } 
/* 4375 */       if (!"".equals(str9)) {
/*      */         
/* 4377 */         if ("SWPRODSTRUCT".equals(paramEntityItem.getEntityType())) {
/* 4378 */           l1 = System.currentTimeMillis();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4383 */           if (paramEntityItem.hasDownLinks()) {
/* 4384 */             for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 4385 */               EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 4386 */               if (entityItem.getEntityType().equals("MODEL")) {
/* 4387 */                 str3 = PokUtils.getAttributeValue(entityItem, str9, ", ", null, false);
/* 4388 */                 addDebugComment(3, "Get withdrweffctvdate through VE ADSSWPRODSTRUCT, date is :" + str3);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } else {
/* 4393 */             addDebugComment(3, "Can not get down link through VE ADSSWPRODSTRUCT");
/*      */           } 
/* 4395 */           addDebugComment(3, "Time for get withdrweffectvdate from VE ADSSWPRODSTRUCT: " + Stopwatch.format(System.currentTimeMillis() - l1));
/* 4396 */         } else if ("REFOFERFEAT".equals(paramEntityItem.getEntityType())) {
/*      */           
/* 4398 */           String str = null;
/* 4399 */           if (paramEntityItem.hasUpLinks()) {
/* 4400 */             for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 4401 */               EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 4402 */               if (entityItem != null && entityItem.getEntityType().equals("REFOFERREFOFERFEAT")) {
/* 4403 */                 for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 4404 */                   EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/* 4405 */                   if (entityItem1 != null && "REFOFER".equals(entityItem1.getEntityType())) {
/* 4406 */                     String str10 = PokUtils.getAttributeValue(entityItem1, str9, ", ", null, false);
/* 4407 */                     addDebugComment(3, "Get withdrweffctvdate through VE ADSREFOFERFEAT, date is :" + str10);
/* 4408 */                     if (str10 != null) {
/*      */                       
/* 4410 */                       if (str == null) {
/* 4411 */                         str = str10;
/*      */                       
/*      */                       }
/* 4414 */                       else if (str10.compareTo(str) > 0) {
/* 4415 */                         str = str10;
/*      */                       } 
/*      */                     } else {
/*      */                       
/* 4419 */                       str = this.m_strForever.substring(0, 10);
/*      */ 
/*      */                       
/*      */                       // Byte code: goto -> 677
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } else {
/* 4429 */             addDebugComment(3, "Can not get up link through VE ADSREFOFERFEAT");
/*      */           } 
/* 4431 */           str3 = str;
/*      */         } else {
/* 4433 */           str3 = PokUtils.getAttributeValue(paramEntityItem, str9, ", ", null, false);
/*      */         } 
/*      */         
/* 4436 */         if (str3 == null) {
/* 4437 */           str3 = this.m_strForever.substring(0, 10);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4443 */     str4 = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/*      */     
/* 4445 */     String str6 = PokUtils.getAttributeFlagValue(paramEntityItem, "OLDINDC");
/*      */     
/* 4447 */     if (str4 == null) {
/* 4448 */       str4 = "";
/*      */     }
/* 4450 */     if (str6 == null) {
/* 4451 */       str6 = "";
/*      */     }
/* 4453 */     str5 = this.curStatusvalue;
/*      */     
/* 4455 */     if (str5 == null) {
/* 4456 */       str5 = "";
/*      */     }
/* 4458 */     StringBuffer stringBuffer = new StringBuffer();
/* 4459 */     stringBuffer.append("INSERT INTO cache.xmlidlcache (XMLENTITYTYPE, XMLENTITYID, XMLCACHEDTS, XMLCACHEVALIDTO, XMLOFFTYPE, FLFILSYSINDC, WTHDRWEFFCTVDATE, STATUS, XMLMESSAGE, PDHDOMAIN, OLDINDC) ");
/* 4460 */     stringBuffer.append(" VALUES (?,?,?,?,?,?,?,?,?,?,? )");
/* 4461 */     addDebug("T2 :" + paramString2);
/* 4462 */     addDebug("offtype :" + str1);
/* 4463 */     addDebug("flfilsysindc :" + str2);
/* 4464 */     addDebug("withdrweffctvdate :" + str3);
/* 4465 */     addDebug("status :" + str5);
/* 4466 */     addDebug("pdhdomain :" + str4);
/* 4467 */     addDebug("OLDINDC :" + str6);
/*      */     
/*      */     try {
/* 4470 */       paramPreparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/* 4471 */       paramPreparedStatement.setString(1, paramString3);
/* 4472 */       paramPreparedStatement.setInt(2, paramInt);
/* 4473 */       paramPreparedStatement.setString(3, paramString2);
/* 4474 */       paramPreparedStatement.setString(4, "9999-12-31-00.00.00.000000");
/* 4475 */       paramPreparedStatement.setString(5, str1);
/* 4476 */       paramPreparedStatement.setString(6, str2);
/* 4477 */       paramPreparedStatement.setString(7, str3);
/* 4478 */       paramPreparedStatement.setString(8, str5);
/* 4479 */       paramPreparedStatement.setString(9, paramString1);
/* 4480 */       paramPreparedStatement.setString(10, str4);
/* 4481 */       paramPreparedStatement.setString(11, str6);
/* 4482 */       paramPreparedStatement.execute();
/* 4483 */       if (this.m_db.getODSConnection() != null) {
/* 4484 */         this.m_db.getODSConnection().commit();
/*      */       }
/* 4486 */       addDebugComment(2, " record was inserted into table xmlidlcache.");
/* 4487 */     } catch (MiddlewareException middlewareException) {
/* 4488 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4489 */       middlewareException.printStackTrace();
/* 4490 */       throw middlewareException;
/* 4491 */     } catch (SQLException sQLException) {
/* 4492 */       addDebug("SQLException on ? " + sQLException);
/* 4493 */       sQLException.printStackTrace();
/* 4494 */       throw sQLException;
/*      */     } finally {
/* 4496 */       if (paramPreparedStatement != null) {
/*      */         try {
/* 4498 */           paramPreparedStatement.close();
/* 4499 */         } catch (SQLException sQLException) {
/* 4500 */           sQLException.printStackTrace();
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
/*      */   protected void inertMSGLOG(ArrayList paramArrayList, String paramString1, int paramInt, String paramString2, String paramString3, String paramString4) throws MiddlewareException, SQLException {
/* 4517 */     long l = System.currentTimeMillis();
/* 4518 */     Iterator<String[]> iterator = paramArrayList.iterator();
/*      */     
/* 4520 */     PreparedStatement preparedStatement = null;
/* 4521 */     StringBuffer stringBuffer = new StringBuffer();
/* 4522 */     stringBuffer
/* 4523 */       .append("INSERT INTO cache.XMLMSGLOG (SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, SENDMSGDTS, MSGTYPE, MSGCOUNT, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGSTATUS, REASON, MQPROPFILE) ");
/* 4524 */     stringBuffer.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */     
/*      */     try {
/* 4527 */       preparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/* 4528 */       while (iterator.hasNext()) {
/* 4529 */         String[] arrayOfString = iterator.next();
/* 4530 */         boolean bool = (arrayOfString[0] == null) ? false : Integer.parseInt(arrayOfString[0]);
/* 4531 */         preparedStatement.setString(1, paramString1);
/* 4532 */         preparedStatement.setInt(2, paramInt);
/* 4533 */         preparedStatement.setString(3, paramString2);
/* 4534 */         preparedStatement.setString(4, arrayOfString[1]);
/* 4535 */         preparedStatement.setString(5, arrayOfString[2]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4541 */         if ("XMLCOMPATSETUP".equals(paramString1)) {
/* 4542 */           preparedStatement.setInt(6, ADSWWCOMPATXMLABR.WWCOMPAT_MESSAGE_COUNT);
/* 4543 */         } else if ("XMLPRODPRICESETUP".equals(paramString1)) {
/* 4544 */           preparedStatement.setInt(6, ADSPRICEABR.PRICE_MESSAGE_COUNT);
/*      */         } else {
/* 4546 */           preparedStatement.setInt(6, 1);
/*      */         } 
/* 4548 */         preparedStatement.setString(7, paramString3);
/* 4549 */         preparedStatement.setInt(8, bool);
/* 4550 */         preparedStatement.setString(9, arrayOfString[3]);
/* 4551 */         preparedStatement.setString(10, arrayOfString[4]);
/* 4552 */         preparedStatement.setString(11, arrayOfString[5]);
/* 4553 */         preparedStatement.setString(12, paramString4);
/* 4554 */         preparedStatement.addBatch();
/*      */       } 
/*      */       
/* 4557 */       preparedStatement.executeBatch();
/* 4558 */       if (this.m_db.getODSConnection() != null) {
/* 4559 */         this.m_db.getODSConnection().commit();
/*      */       }
/*      */       
/* 4562 */       addDebugComment(2, paramArrayList.size() + " records was inserted into table XMLMSGLOG. Total Time: " + 
/*      */           
/* 4564 */           Stopwatch.format(System.currentTimeMillis() - l));
/* 4565 */     } catch (MiddlewareException middlewareException) {
/* 4566 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4567 */       middlewareException.printStackTrace();
/* 4568 */       throw middlewareException;
/* 4569 */     } catch (SQLException sQLException) {
/* 4570 */       addDebug("SQLException on ? " + sQLException);
/* 4571 */       sQLException.printStackTrace();
/* 4572 */       throw sQLException;
/*      */     } finally {
/* 4574 */       if (preparedStatement != null) {
/*      */         try {
/* 4576 */           preparedStatement.close();
/* 4577 */         } catch (SQLException sQLException) {
/* 4578 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExtxmlfeedVct(Vector paramVector) {
/* 4586 */     this.extxmlfeedVct = paramVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDescription(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 4595 */     String str = "";
/* 4596 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1);
/* 4597 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 4599 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 4600 */       StringBuffer stringBuffer = new StringBuffer();
/* 4601 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 4603 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 4605 */           if (stringBuffer.length() > 0) {
/* 4606 */             stringBuffer.append(",");
/*      */           }
/* 4608 */           if (paramString2.equals("short")) {
/* 4609 */             stringBuffer.append(arrayOfMetaFlag[b].getShortDescription());
/* 4610 */           } else if (paramString2.equals("long")) {
/* 4611 */             stringBuffer.append(arrayOfMetaFlag[b].getLongDescription());
/* 4612 */           } else if (paramString2.equals("flag")) {
/* 4613 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*      */           } else {
/*      */             
/* 4616 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */           } 
/*      */         } 
/*      */       } 
/* 4620 */       str = stringBuffer.toString();
/*      */     } 
/* 4622 */     return str;
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
/*      */   protected String getLD_NDN(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 4639 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getNDNitem(EntityItem paramEntityItem, String paramString) {
/*      */     byte b;
/* 4649 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 4650 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 4651 */       if (entityItem.getEntityType().equals(paramString)) {
/* 4652 */         return entityItem;
/*      */       }
/*      */     } 
/* 4655 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 4656 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 4657 */       if (entityItem.getEntityType().equals(paramString)) {
/* 4658 */         return entityItem;
/*      */       }
/*      */     } 
/* 4661 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 4671 */     StringBuffer stringBuffer = new StringBuffer();
/* 4672 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem.getEntityType());
/*      */ 
/*      */     
/* 4675 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 4676 */     if (eANList == null) {
/* 4677 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 4678 */       eANList = entityGroup.getMetaAttribute();
/* 4679 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 4681 */     for (byte b = 0; b < eANList.size(); b++) {
/* 4682 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 4683 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 4684 */       if (b + 1 < eANList.size()) {
/* 4685 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/* 4688 */     if (nDN != null) {
/* 4689 */       EntityList entityList = null;
/* 4690 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 4691 */       EntityItem entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       
/* 4693 */       if (entityItem == null && 
/* 4694 */         paramEntityItem.getEntityType().endsWith("PRODSTRUCT")) {
/* 4695 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType() + " pulling small VE for this " + paramEntityItem
/* 4696 */             .getKey());
/*      */         
/* 4698 */         String str = "EXRPT3FM";
/* 4699 */         if (paramEntityItem.getEntityType().equals("SWPRODSTRUCT")) {
/* 4700 */           str = "EXRPT3SWFM";
/*      */         }
/*      */ 
/*      */         
/* 4704 */         entityList = this.m_db.getEntityList(paramEntityItem.getProfile(), new ExtractActionItem(null, this.m_db, paramEntityItem
/* 4705 */               .getProfile(), str), new EntityItem[] { new EntityItem(null, paramEntityItem
/* 4706 */                 .getProfile(), paramEntityItem.getEntityType(), paramEntityItem
/* 4707 */                 .getEntityID()) });
/*      */         
/* 4709 */         paramEntityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 4710 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       } 
/*      */ 
/*      */       
/* 4714 */       if (entityItem != null) {
/* 4715 */         stringBuffer1.append("(" + nDN.getTag());
/* 4716 */         for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 4717 */           String str = nDN.getAttr().elementAt(b1).toString();
/* 4718 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 4719 */           if (b1 + 1 < nDN.getAttr().size()) {
/* 4720 */             stringBuffer1.append(" ");
/*      */           }
/*      */         } 
/* 4723 */         stringBuffer1.append(") ");
/*      */       } else {
/* 4725 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType());
/*      */       } 
/* 4727 */       nDN = nDN.getNext();
/* 4728 */       if (nDN != null) {
/* 4729 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/* 4730 */         if (entityItem != null) {
/* 4731 */           stringBuffer1.append("(" + nDN.getTag());
/* 4732 */           for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 4733 */             String str = nDN.getAttr().elementAt(b1).toString();
/* 4734 */             stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 4735 */             if (b1 + 1 < nDN.getAttr().size()) {
/* 4736 */               stringBuffer1.append(" ");
/*      */             }
/*      */           } 
/* 4739 */           stringBuffer1.append(") ");
/*      */         } else {
/* 4741 */           addDebug("NO entity found for next ndn.getEntityType(): " + nDN.getEntityType());
/*      */         } 
/*      */       } 
/* 4744 */       stringBuffer.insert(0, stringBuffer1.toString());
/*      */       
/* 4746 */       if (entityList != null) {
/* 4747 */         entityList.dereference();
/*      */       }
/*      */     } 
/*      */     
/* 4751 */     return stringBuffer.toString().trim();
/*      */   }
/*      */   
/*      */   private static class NDN { private String etype;
/*      */     private String tag;
/*      */     private NDN next;
/* 4757 */     private Vector attrVct = new Vector();
/*      */     NDN(String param1String1, String param1String2) {
/* 4759 */       this.etype = param1String1;
/* 4760 */       this.tag = param1String2;
/*      */     }
/* 4762 */     String getTag() { return this.tag; }
/* 4763 */     String getEntityType() { return this.etype; } Vector getAttr() {
/* 4764 */       return this.attrVct;
/*      */     } void addAttr(String param1String) {
/* 4766 */       this.attrVct.addElement(param1String);
/*      */     }
/* 4768 */     void setNext(NDN param1NDN) { this.next = param1NDN; } NDN getNext() {
/* 4769 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getEntityLastValfrom(EntityItem paramEntityItem) {
/* 4779 */     String str1 = null;
/* 4780 */     PreparedStatement preparedStatement = null;
/* 4781 */     String str2 = "SELECT VALFROM FROM OPICM.ENTITY WHERE ENTITYTYPE = ? AND ENTITYID = ? AND VALTO > CURRENT TIMESTAMP AND EFFTO > CURRENT TIMESTAMP with ur";
/*      */     try {
/* 4783 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(str2);
/* 4784 */       preparedStatement.setString(1, paramEntityItem.getEntityType());
/* 4785 */       preparedStatement.setInt(2, paramEntityItem.getEntityID());
/* 4786 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 4787 */       while (resultSet.next()) {
/* 4788 */         str1 = resultSet.getString(1);
/*      */       }
/*      */     }
/* 4791 */     catch (MiddlewareException middlewareException) {
/* 4792 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4793 */       middlewareException.printStackTrace();
/* 4794 */     } catch (SQLException sQLException) {
/* 4795 */       addDebug("SQLException on ? " + sQLException);
/* 4796 */       sQLException.printStackTrace();
/*      */     } finally {
/* 4798 */       if (preparedStatement != null) {
/*      */         try {
/* 4800 */           preparedStatement.close();
/* 4801 */         } catch (SQLException sQLException) {
/* 4802 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 4806 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getEntityT2DTS(EntityItem paramEntityItem) {
/* 4816 */     getEntityType();
/* 4817 */     String str1 = null;
/* 4818 */     PreparedStatement preparedStatement = null;
/* 4819 */     String str2 = null;
/* 4820 */     if (getEntityType().equals("XMLPRODPRICESETUP")) {
/* 4821 */       str2 = "ADSPPABRSTATUS";
/*      */     } else {
/* 4823 */       str2 = "ADSABRSTATUS";
/*      */     } 
/*      */ 
/*      */     
/* 4827 */     String str3 = "SELECT MAX(VALFROM) FROM OPICM.FLAG WHERE ENTITYTYPE = ? AND ENTITYID = ? and ATTRIBUTECODE= ? AND ATTRIBUTEVALUE='0050' AND EFFTO > CURRENT TIMESTAMP with ur";
/*      */     try {
/* 4829 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(str3);
/* 4830 */       preparedStatement.setString(1, paramEntityItem.getEntityType());
/* 4831 */       preparedStatement.setInt(2, paramEntityItem.getEntityID());
/* 4832 */       preparedStatement.setString(3, str2);
/* 4833 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 4834 */       while (resultSet.next()) {
/* 4835 */         str1 = resultSet.getString(1);
/* 4836 */         addDebug("getEntity 0050 Valfrom " + str1);
/*      */       } 
/* 4838 */     } catch (MiddlewareException middlewareException) {
/* 4839 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4840 */       middlewareException.printStackTrace();
/* 4841 */     } catch (SQLException sQLException) {
/* 4842 */       addDebug("SQLException on ? " + sQLException);
/* 4843 */       sQLException.printStackTrace();
/*      */     } finally {
/* 4845 */       if (preparedStatement != null) {
/*      */         try {
/* 4847 */           preparedStatement.close();
/* 4848 */         } catch (SQLException sQLException) {
/* 4849 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 4853 */     return str1;
/*      */   }
/*      */   public void setT2DTS(String paramString) {
/* 4856 */     addDebug("ADSABRSTATUS setT2DTS:" + paramString);
/* 4857 */     this.t2DTS = paramString;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\ADSABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */