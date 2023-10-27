/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
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
/*      */ public class ADSABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  387 */   private StringBuffer rptSb = new StringBuffer();
/*  388 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  389 */   static final String NEWLINE = new String(FOOL_JTEST);
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
/*  403 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  404 */   private StringBuffer reason = new StringBuffer();
/*  405 */   private PrintWriter dbgPw = null;
/*  406 */   private PrintWriter userxmlPw = null;
/*  407 */   private String dbgfn = null;
/*  408 */   private String userxmlfn = null;
/*  409 */   private int userxmlLen = 0;
/*  410 */   private int dbgLen = 0;
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
/*  428 */   private Hashtable metaTbl = new Hashtable<>();
/*      */ 
/*      */   
/*  431 */   private static final Hashtable NDN_TBL = new Hashtable<>(); static {
/*  432 */     NDN nDN1 = new NDN("MODEL", "(TM)");
/*  433 */     nDN1.addAttr("MACHTYPEATR");
/*  434 */     nDN1.addAttr("MODELATR");
/*  435 */     nDN1.addAttr("COFCAT");
/*  436 */     nDN1.addAttr("COFSUBCAT");
/*  437 */     nDN1.addAttr("COFGRP");
/*  438 */     nDN1.addAttr("COFSUBGRP");
/*  439 */     NDN nDN2 = new NDN("FEATURE", "(FC)");
/*  440 */     nDN2.addAttr("FEATURECODE");
/*  441 */     nDN1.setNext(nDN2);
/*  442 */     NDN_TBL.put("PRODSTRUCT", nDN1);
/*      */     
/*  444 */     nDN1 = new NDN("MODEL", "(TM)");
/*  445 */     nDN1.addAttr("MACHTYPEATR");
/*  446 */     nDN1.addAttr("MODELATR");
/*  447 */     nDN1.addAttr("COFCAT");
/*  448 */     nDN1.addAttr("COFSUBCAT");
/*  449 */     nDN1.addAttr("COFGRP");
/*  450 */     nDN1.addAttr("COFSUBGRP");
/*  451 */     nDN2 = new NDN("SWFEATURE", "(FC)");
/*  452 */     nDN2.addAttr("FEATURECODE");
/*  453 */     nDN1.setNext(nDN2);
/*  454 */     NDN_TBL.put("SWPRODSTRUCT", nDN1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  463 */   protected static final String[][] VE_Filter_Array = new String[][] { { "ADSLSEO", "AVAIL", "RFR Final" }, { "ADSLSEO", "ANNOUNCEMENT", "RFR Final" }, { "ADSLSEO", "FB", "RFR Final" }, { "ADSLSEO", "MM", "RFR Final" }, { "ADSLSEO", "BHCATLGOR", "Final" }, { "ADSLSEO2", "AVAIL", "RFR Final" }, { "ADSLSEO2", "ANNOUNCEMENT", "RFR Final" }, { "ADSLSEOBUNDLE", "AVAIL", "RFR Final" }, { "ADSLSEOBUNDLE", "ANNOUNCEMENT", "RFR Final" }, { "ADSLSEOBUNDLE", "FB", "RFR Final" }, { "ADSLSEOBUNDLE", "MM", "RFR Final" }, { "ADSLSEOBUNDLE", "BHCATLGOR", "Final" }, { "ADSMODEL", "AVAIL", "RFR Final" }, { "ADSMODEL", "ANNOUNCEMENT", "RFR Final" }, { "ADSMODEL", "FB", "RFR Final" }, { "ADSMODEL", "MM", "RFR Final" }, { "ADSMODEL", "BHCATLGOR", "Final" }, { "ADSPRODSTRUCT", "AVAIL", "RFR Final" }, { "ADSPRODSTRUCT", "ANNOUNCEMENT", "RFR Final" }, { "ADSPRODSTRUCT", "BHCATLGOR", "Final" }, { "ADSSWPRODSTRUCT", "AVAIL", "RFR Final" }, { "ADSSWPRODSTRUCT", "ANNOUNCEMENT", "RFR Final" }, { "ADSSWPRODSTRUCT", "BHCATLGOR", "Final" }, { "ADSSVCMOD", "AVAIL", "RFR Final" }, { "ADSSVCMOD", "ANNOUNCEMENT", "RFR Final" }, { "ADSMODELCONVERT", "AVAIL", "RFR Final" }, { "ADSMODELCONVERT", "ANNOUNCEMENT", "RFR Final" }, { "ADSPRODSTRUCT2", "AVAIL", "RFR Final" }, { "ADSPRODSTRUCT2", "ANNOUNCEMENT", "RFR Final" }, { "ADSSWPRODSTRUCT2", "AVAIL", "RFR Final" }, { "ADSSWPRODSTRUCT2", "ANNOUNCEMENT", "RFR Final" }, { "ADSCATNAV", "IMG", "RFR Final" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  524 */   protected static final String[][] VE_Country_Filter_Array = new String[][] { { "ADSLSEO", "FB" }, { "ADSLSEO", "IMG" }, { "ADSLSEO", "MM" }, { "ADSLSEOBUNDLE", "FB" }, { "ADSLSEOBUNDLE", "IMG" }, { "ADSLSEOBUNDLE", "MM" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  536 */   private static final Hashtable FILTER_TBL = new Hashtable<>(); static {
/*  537 */     FILTER_TBL.put("CATNAV", new String[] { "", "FLFILSYSINDC", "" });
/*  538 */     FILTER_TBL.put("FCTRANSACTION", new String[] { "", "", "WTHDRWEFFCTVDATE" });
/*  539 */     FILTER_TBL.put("FEATURE", new String[] { "", "", "WITHDRAWDATEEFF_T" });
/*  540 */     FILTER_TBL.put("LSEO", new String[] { "COFCAT", "FLFILSYSINDC", "LSEOUNPUBDATEMTRGT" });
/*  541 */     FILTER_TBL.put("LSEOBUNDLE", new String[] { "BUNDLETYPE", "FLFILSYSINDC", "BUNDLUNPUBDATEMTRGT" });
/*  542 */     FILTER_TBL.put("MODEL", new String[] { "COFCAT", "FLFILSYSINDC", "WTHDRWEFFCTVDATE" });
/*  543 */     FILTER_TBL.put("MODELCONVERT", new String[] { "", "", "WTHDRWEFFCTVDATE" });
/*  544 */     FILTER_TBL.put("PRODSTRUCT", new String[] { "", "FLFILSYSINDC", "WTHDRWEFFCTVDATE" });
/*  545 */     FILTER_TBL.put("SVCMOD", new String[] { "SVCMODGRP", "", "WTHDRWEFFCTVDATE" });
/*  546 */     FILTER_TBL.put("SWFEATURE", new String[] { "", "", "WITHDRAWDATEEFF_T" });
/*  547 */     FILTER_TBL.put("SWPRODSTRUCT", new String[] { "", "", "WTHDRWEFFCTVDATE" });
/*  548 */     FILTER_TBL.put("REFOFER", new String[] { "", "", "ENDOFSVC" });
/*  549 */     FILTER_TBL.put("REFOFERFEAT", new String[] { "", "", "ENDOFSVC" });
/*      */   }
/*  551 */   private ResourceBundle rsBundle = null;
/*  552 */   private String priorStatus = "&nbsp;";
/*  553 */   private String curStatus = "&nbsp;";
/*  554 */   private String curStatusvalue = null;
/*      */   
/*      */   private boolean isPeriodicABR = false;
/*      */   private boolean isSystemResend = false;
/*      */   private boolean isSystemResendRFR = false;
/*      */   private boolean isSystemResendCache = false;
/*      */   private boolean isSystemResendCurrent = false;
/*      */   private boolean isSystemResendCacheExist = false;
/*  562 */   private String SystemResendCacheXml = "";
/*      */   
/*      */   private boolean isXMLIDLABR = false;
/*      */   
/*      */   private boolean isXMLCACHE = false;
/*      */   private boolean isXMLADSABR = false;
/*  568 */   private String actionTaken = "";
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
/*  585 */   public static int DEBUG_LVL = ABRServerProperties.getABRDebugLevel("ADSABRSTATUS");
/*  586 */   public static int MAX_LVL = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  592 */   private static String ADSABRSTATUS_USERXML_ADSXMLSETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_ADSXMLSETUP", "ON");
/*      */   
/*  594 */   private static String ADSABRSTATUS_USERXML_XMLPRODPRICESETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLPRODPRICESETUP", "OFF");
/*      */   
/*  596 */   private static String ADSABRSTATUS_USERXML_XMLCOMPATSETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLCOMPATSETUP", "OFF");
/*      */   
/*  598 */   private static String ADSABRSTATUS_USERXML_XMLXLATESETUP = ABRServerProperties.getValue("ADSABRSTATUS", "_USERXML_XMLXLATESETUP", "ON");
/*      */   
/*      */   protected static boolean USERXML_OFF_LOG = false;
/*      */   
/*      */   protected static final Hashtable ABR_VERSION_TBL;
/*      */   
/*      */   private static final Hashtable PERIODIC_ABR_TBL;
/*      */   
/*      */   static {
/*  607 */     ABR_TBL = new Hashtable<>();
/*  608 */     ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSMODELABR");
/*  609 */     ABR_TBL.put("FEATURE", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSFEATUREABR");
/*  610 */     ABR_TBL.put("SWFEATURE", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSSWFEATUREABR");
/*  611 */     ABR_TBL.put("WARR", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSWARRABR");
/*  612 */     ABR_TBL.put("REVUNBUNDCOMP", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSUNBUNDCOMPABR");
/*      */     
/*  614 */     ABR_TBL.put("FCTRANSACTION", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSFCTRANSABR");
/*  615 */     ABR_TBL.put("MODELCONVERT", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSMODELCONVERTABR");
/*  616 */     ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSPRODSTRUCTABR");
/*  617 */     ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSSWPRODSTRUCTABR");
/*      */     
/*  619 */     ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSLSEOABR");
/*  620 */     ABR_TBL.put("SVCMOD", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSSVCMODABR");
/*  621 */     ABR_TBL.put("SLEORGNPLNTCODE", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSSLEORGNPLNTCODEABR");
/*  622 */     ABR_TBL.put("GBT", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSGBTABR");
/*      */     
/*  624 */     ABR_TBL.put("IMG", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSIMGABR");
/*  625 */     ABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSLSEOBUNDLEABR");
/*      */     
/*  627 */     ABR_TBL.put("CATNAV", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSCATNAVABR");
/*      */     
/*  629 */     ABR_TBL.put("SVCLEV", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSSVCLEVABR");
/*      */     
/*  631 */     ABR_TBL.put("GENERALAREA", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSGENAREAABR");
/*      */ 
/*      */     
/*  634 */     ABR_TBL.put("XMLPRODPRICESETUP", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSPRICEABR");
/*  635 */     ABR_TBL.put("XMLCOMPATSETUP", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSWWCOMPATXMLABR");
/*  636 */     ABR_TBL.put("XMLXLATESETUP", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSXLATEABR");
/*      */     
/*  638 */     ABR_TBL.put("REFOFER", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSREFOFERABR");
/*  639 */     ABR_TBL.put("REFOFERFEAT", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSREFOFERFEATABR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  651 */     READ_LANGS_TBL = new Hashtable<>();
/*      */     
/*  653 */     READ_LANGS_TBL.put("" + Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
/*  654 */     READ_LANGS_TBL.put("" + Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
/*  655 */     READ_LANGS_TBL.put("" + Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
/*  656 */     READ_LANGS_TBL.put("" + Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
/*  657 */     READ_LANGS_TBL.put("" + Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
/*  658 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
/*  659 */     READ_LANGS_TBL.put("" + Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
/*  660 */     READ_LANGS_TBL.put("" + Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
/*  661 */     READ_LANGS_TBL.put("" + Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
/*  662 */     READ_LANGS_TBL.put("" + Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
/*  663 */     READ_LANGS_TBL.put("" + Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
/*  664 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
/*  665 */     READ_LANGS_TBL.put("" + Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);
/*      */     
/*  667 */     ADSTYPES_TBL = new Hashtable<>();
/*  668 */     ADSTYPES_TBL.put("10", "CATNAV");
/*  669 */     ADSTYPES_TBL.put("100", "SVCPRODSTRUCT");
/*  670 */     ADSTYPES_TBL.put("110", "SWFEATURE");
/*  671 */     ADSTYPES_TBL.put("120", "SWFPRODSTRUCT");
/*  672 */     ADSTYPES_TBL.put("130", "MODELCG");
/*  673 */     ADSTYPES_TBL.put("140", "LSEO");
/*  674 */     ADSTYPES_TBL.put("150", "SVCMOD");
/*  675 */     ADSTYPES_TBL.put("160", "WARR");
/*  676 */     ADSTYPES_TBL.put("170", "REVUNBUNDCOMP");
/*  677 */     ADSTYPES_TBL.put("20", "GENERALAREA");
/*  678 */     ADSTYPES_TBL.put("30", "FEATURE");
/*      */ 
/*      */ 
/*      */     
/*  682 */     ADSTYPES_TBL.put("60", "MODEL");
/*  683 */     ADSTYPES_TBL.put("70", "MODELCONVERT");
/*  684 */     ADSTYPES_TBL.put("80", "PRODSTRUCT");
/*  685 */     ADSTYPES_TBL.put("90", "SVCFEATURE");
/*      */ 
/*      */ 
/*      */     
/*  689 */     ITEM_STATUS_ATTR_TBL = new Hashtable<>();
/*  690 */     ITEM_STATUS_ATTR_TBL.put("AVAIL", "STATUS");
/*  691 */     ITEM_STATUS_ATTR_TBL.put("ANNOUNCEMENT", "ANNSTATUS");
/*  692 */     ITEM_STATUS_ATTR_TBL.put("IMG", "STATUS");
/*  693 */     ITEM_STATUS_ATTR_TBL.put("MM", "MMSTATUS");
/*  694 */     ITEM_STATUS_ATTR_TBL.put("FB", "FBSTATUS");
/*  695 */     ITEM_STATUS_ATTR_TBL.put("FEATURE", "STATUS");
/*  696 */     ITEM_STATUS_ATTR_TBL.put("SWFEATURE", "STATUS");
/*  697 */     ITEM_STATUS_ATTR_TBL.put("LSEO", "STATUS");
/*  698 */     ITEM_STATUS_ATTR_TBL.put("BHCATLGOR", "STATUS");
/*      */ 
/*      */ 
/*      */     
/*  702 */     ITEM_COUNTRY_ATTR_TBL = new Hashtable<>();
/*  703 */     ITEM_COUNTRY_ATTR_TBL.put("LSEO", "COUNTRYLIST");
/*  704 */     ITEM_COUNTRY_ATTR_TBL.put("LSEOBUNDLE", "COUNTRYLIST");
/*  705 */     ITEM_COUNTRY_ATTR_TBL.put("IMG", "COUNTRYLIST");
/*  706 */     ITEM_COUNTRY_ATTR_TBL.put("MM", "COUNTRYLIST");
/*  707 */     ITEM_COUNTRY_ATTR_TBL.put("FB", "COUNTRYLIST");
/*      */ 
/*      */ 
/*      */     
/*  711 */     ABR_ATTR_TBL = new Hashtable<>();
/*  712 */     ABR_ATTR_TBL.put("ANNOUNCEMENT", "ANNABRSTATUS");
/*  713 */     ABR_ATTR_TBL.put("AVAIL", "AVAILABRSTATUS");
/*  714 */     ABR_ATTR_TBL.put("FEATURE", "FCABRSTATUS");
/*  715 */     ABR_ATTR_TBL.put("FCTRANSACTION", "FCTRANSABRSTATUS");
/*  716 */     ABR_ATTR_TBL.put("LSEO", "LSEOABRSTATUS");
/*  717 */     ABR_ATTR_TBL.put("LSEOBUNDLE", "LSEOBDLABRSTATUS");
/*  718 */     ABR_ATTR_TBL.put("MODELCONVERT", "MDLCNTABRSTATTUS");
/*  719 */     ABR_ATTR_TBL.put("SWFEATURE", "SWFCABRSTATUS");
/*  720 */     ABR_ATTR_TBL.put("IPSCFEAT", "IPSCFEATABRSTATUS");
/*  721 */     ABR_ATTR_TBL.put("IPSCSTRUC", "IPSCSTRUCABRSTATUS");
/*  722 */     ABR_ATTR_TBL.put("SVCMOD", "SVCMODABRSTATUS");
/*  723 */     ABR_ATTR_TBL.put("WWSEO", "WWSEOABRSTATUS");
/*  724 */     ABR_ATTR_TBL.put("MODEL", "MODELABRSTATUS");
/*  725 */     ABR_ATTR_TBL.put("PRODSTRUCT", "PRODSTRUCTABRSTATUS");
/*  726 */     ABR_ATTR_TBL.put("SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
/*      */ 
/*      */ 
/*      */     
/*  730 */     ABR_VERSION_TBL = new Hashtable<>();
/*  731 */     ABR_VERSION_TBL.put("MODEL05", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSMODEL05ABR");
/*  732 */     ABR_VERSION_TBL.put("LSEO05", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSLSEO05ABR");
/*  733 */     ABR_VERSION_TBL.put("SVCMOD05", "COM.ibm.eannounce.abr.ln.adsxmlbh1.ADSSVCMOD05ABR");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  738 */     PERIODIC_ABR_TBL = new Hashtable<>();
/*  739 */     PERIODIC_ABR_TBL.put("ADSXMLSETUP", "ADSABRSTATUS");
/*  740 */     PERIODIC_ABR_TBL.put("XMLPRODPRICESETUP", "ADSPPABRSTATUS");
/*  741 */     PERIODIC_ABR_TBL.put("XMLCOMPATSETUP", "ADSABRSTATUS");
/*  742 */     PERIODIC_ABR_TBL.put("XMLXLATESETUP", "ADSABRSTATUS");
/*      */   }
/*      */   
/*  745 */   private Object[] args = (Object[])new String[10];
/*  746 */   private String abrversion = "";
/*  747 */   private String t2DTS = "&nbsp;";
/*  748 */   private String t1DTS = "&nbsp;";
/*      */   
/*  750 */   private StringBuffer userxmlSb = new StringBuffer();
/*  751 */   private Vector extxmlfeedVct = new Vector();
/*  752 */   private Vector succQueueNameVct = new Vector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  784 */     String str = (String)ABR_TBL.get(paramString);
/*  785 */     addDebug("creating instance of ADSABR  = '" + str + "' for " + paramString);
/*  786 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  795 */     String str1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  802 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  807 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  810 */       String str = "";
/*  811 */       long l = System.currentTimeMillis();
/*      */       
/*  813 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  816 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  819 */       setReturnCode(0);
/*      */ 
/*      */       
/*  822 */       this.extxmlfeedVct.clear();
/*  823 */       this.succQueueNameVct.clear();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  828 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  830 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*      */       try {
/*  834 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */         
/*  837 */         this.isPeriodicABR = PERIODIC_ABR_TBL.containsKey(getEntityType());
/*      */         
/*  839 */         String str6 = getEntityType();
/*  840 */         if (this.isPeriodicABR) {
/*      */           
/*  842 */           if ("ADSXMLSETUP".equals(str6)) {
/*  843 */             if ("OFF".equals(ADSABRSTATUS_USERXML_ADSXMLSETUP)) {
/*  844 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  846 */               USERXML_OFF_LOG = false;
/*      */             } 
/*  848 */           } else if ("XMLPRODPRICESETUP".equals(str6)) {
/*  849 */             if ("OFF".equals(ADSABRSTATUS_USERXML_XMLPRODPRICESETUP)) {
/*  850 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  852 */               USERXML_OFF_LOG = false;
/*      */             } 
/*  854 */           } else if ("XMLCOMPATSETUP".equals(str6)) {
/*  855 */             if ("OFF".equals(ADSABRSTATUS_USERXML_XMLCOMPATSETUP)) {
/*  856 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  858 */               USERXML_OFF_LOG = false;
/*      */             } 
/*  860 */           } else if ("XMLXLATESETUP".equals(str6)) {
/*  861 */             if ("OFF".equals(ADSABRSTATUS_USERXML_XMLXLATESETUP)) {
/*  862 */               USERXML_OFF_LOG = true;
/*      */             } else {
/*  864 */               USERXML_OFF_LOG = false;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  868 */           USERXML_OFF_LOG = false;
/*      */         } 
/*      */         
/*  871 */         String str7 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*  872 */         String str8 = PokUtils.getAttributeFlagValue(entityItem, "ADSENTITY");
/*      */         
/*  874 */         if (this.isPeriodicABR) {
/*      */           
/*  876 */           if (str7 != null) {
/*  877 */             str6 = (String)ADSTYPES_TBL.get(str7);
/*      */           }
/*  879 */           if ("20".equals(str8)) {
/*  880 */             str6 = "DEL" + str6;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  886 */         String str9 = getSimpleABRName(str6);
/*  887 */         if (str9 != null) {
/*  888 */           boolean bool = true;
/*  889 */           XMLMQ xMLMQ = (XMLMQ)Class.forName(str9).newInstance();
/*  890 */           str = xMLMQ.getMQCID();
/*  891 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  898 */           if (this.m_abri.getABRCode().equals("XMLIDLABRSTATUS")) {
/*  899 */             this.isXMLIDLABR = true;
/*  900 */             this.isXMLCACHE = true;
/*  901 */             String str11 = getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*      */             
/*  903 */             this.isSystemResendCurrent = "CUR".equals(str11);
/*  904 */             addDebugComment(2, "isSystemResendCurrent=" + this.isSystemResendCurrent + ";sysfeedFlag=" + str11);
/*  905 */           } else if (this.m_abri.getABRCode().equals("ADSABRSTATUS")) {
/*  906 */             this.isXMLADSABR = true;
/*      */           } 
/*  908 */           if (!this.isPeriodicABR) {
/*  909 */             String str11 = xMLMQ.getStatusAttr();
/*  910 */             String str12 = getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*  911 */             String str13 = getAttributeFlagEnabledValue(entityItem, str11);
/*  912 */             this.isSystemResend = "Yes".equals(str12);
/*  913 */             this.isSystemResendRFR = "RFR".equals(str12);
/*      */             
/*  915 */             this.isSystemResendCache = "CACHE".equals(str12);
/*  916 */             this.isSystemResendCurrent = "CUR".equals(str12);
/*      */             
/*  918 */             addDebugComment(2, "execute: " + entityItem.getKey() + " " + str11 + ": " + 
/*  919 */                 PokUtils.getAttributeValue(entityItem, str11, ", ", "", false) + " [" + str13 + "] sysfeedFlag: " + str12 + "; is XMLIDLABR: " + this.isXMLIDLABR + "; is isXMLCACHE:" + this.isXMLCACHE);
/*      */ 
/*      */             
/*  922 */             addDebugComment(2, "isSystemResend: " + this.isSystemResend + "; isSystemResendRFR: " + this.isSystemResendRFR + "; isSystemResendCache: " + this.isSystemResendCache);
/*      */ 
/*      */             
/*  925 */             if (this.isSystemResendRFR) {
/*  926 */               processSystemResendRFR(entityItem, str11, str13);
/*  927 */             } else if (this.isSystemResend) {
/*  928 */               processSystemResend(entityItem, xMLMQ, str11, str13);
/*  929 */             } else if (this.isSystemResendCache) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  942 */               this.isSystemResendCacheExist = checkSystemResendCache(entityItem);
/*  943 */               if (this.isSystemResendCacheExist) {
/*  944 */                 setReturnCode(2);
/*      */               
/*      */               }
/*      */             }
/*  948 */             else if (!this.isXMLIDLABR) {
/*      */               
/*  950 */               if (!"0020".equals(str13) && !"0040".equals(str13)) {
/*  951 */                 addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */                 
/*  953 */                 addError(this.rsBundle.getString("NOT_R4RFINAL"), entityItem);
/*      */               } else {
/*  955 */                 bool = xMLMQ.createXML(entityItem);
/*  956 */                 if (!bool) {
/*  957 */                   addDebug(entityItem.getKey() + " will not have XML generated, createXML=false");
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } else {
/*  962 */             addDebug("execute: periodic " + entityItem.getKey());
/*      */           } 
/*      */           
/*  965 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = null;
/*  966 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = null;
/*      */           
/*  968 */           if (!this.isXMLIDLABR) {
/*      */             
/*  970 */             if (this.isPeriodicABR) {
/*  971 */               attributeChangeHistoryGroup1 = null;
/*      */             }
/*      */             else {
/*      */               
/*  975 */               attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */             } 
/*      */           } else {
/*  978 */             attributeChangeHistoryGroup2 = getADSABRSTATUSHistory("XMLIDLABRSTATUS");
/*  979 */             attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */           } 
/*      */           
/*  982 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup3 = getSTATUSHistory(xMLMQ);
/*      */           
/*  984 */           String str10 = getDTFS(entityItem, xMLMQ);
/*      */ 
/*      */           
/*  987 */           setT2DTS(entityItem, xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup3, str10, attributeChangeHistoryGroup2);
/*  988 */           setT1DTS(xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup3, str10);
/*      */           
/*  990 */           if ((getReturnCode() == 0 || getReturnCode() == 1 || getReturnCode() == 2) && bool) {
/*      */ 
/*      */             
/*  993 */             Profile profile = switchRole(xMLMQ.getRoleCode());
/*  994 */             if (profile != null) {
/*  995 */               addDebug(entityItem.getKey() + " T1=" + this.t1DTS + ";T2=" + this.t2DTS);
/*  996 */               profile.setValOnEffOn(this.t2DTS, this.t2DTS);
/*  997 */               profile.setEndOfDay(this.t2DTS);
/*  998 */               profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*  999 */               profile.setLoginTime(this.t2DTS);
/*      */               
/* 1001 */               Profile profile1 = profile.getNewInstance(this.m_db);
/* 1002 */               profile1.setValOnEffOn(this.t1DTS, this.t1DTS);
/* 1003 */               profile1.setEndOfDay(this.t2DTS);
/* 1004 */               profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/* 1005 */               profile1.setLoginTime(this.t2DTS);
/*      */               
/* 1007 */               String str11 = "";
/*      */               try {
/* 1009 */                 if (this.isPeriodicABR) {
/*      */                   
/* 1011 */                   String str12 = "";
/* 1012 */                   if (str7 != null) {
/* 1013 */                     str12 = (String)ADSTYPES_TBL.get(str7);
/*      */                   }
/* 1015 */                   str11 = "Periodic " + str12;
/* 1016 */                   if ("20".equals(str8)) {
/* 1017 */                     str11 = "Deleted " + str12;
/*      */                   }
/* 1019 */                   setupPrintWriters();
/* 1020 */                   xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                 } else {
/* 1022 */                   str11 = entityItem.getKey();
/*      */                   
/* 1024 */                   if (domainNeedsChecks(entityItem)) {
/* 1025 */                     xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                   } else {
/*      */                     
/* 1028 */                     addXMLGenMsg("DOMAIN_NOT_LISTED", str11);
/*      */                   } 
/*      */                 } 
/* 1031 */               } catch (IOException iOException) {
/*      */ 
/*      */                 
/* 1034 */                 MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("REQ_ERROR"));
/* 1035 */                 this.args[0] = iOException.getMessage();
/* 1036 */                 addMSGLOGReason(iOException.getMessage());
/* 1037 */                 addError(messageFormat1.format(this.args));
/* 1038 */                 addXMLGenMsg("FAILED", str11);
/* 1039 */               } catch (SQLException sQLException) {
/* 1040 */                 addXMLGenMsg("FAILED", str11);
/* 1041 */                 throw sQLException;
/* 1042 */               } catch (MiddlewareRequestException middlewareRequestException) {
/* 1043 */                 addXMLGenMsg("FAILED", str11);
/* 1044 */                 throw middlewareRequestException;
/* 1045 */               } catch (MiddlewareException middlewareException) {
/* 1046 */                 addXMLGenMsg("FAILED", str11);
/* 1047 */                 throw middlewareException;
/* 1048 */               } catch (ParserConfigurationException parserConfigurationException) {
/* 1049 */                 addXMLGenMsg("FAILED", str11);
/* 1050 */                 throw parserConfigurationException;
/* 1051 */               } catch (TransformerException transformerException) {
/* 1052 */                 addXMLGenMsg("FAILED", str11);
/* 1053 */                 throw transformerException;
/* 1054 */               } catch (MissingResourceException missingResourceException) {
/* 1055 */                 addXMLGenMsg("FAILED", str11);
/* 1056 */                 throw missingResourceException;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1061 */           addError(getShortClassName(getClass()) + " does not support " + str6);
/*      */         } 
/*      */         
/* 1064 */         str1 = getNavigationName(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1070 */         if (this.isPeriodicABR && !this.isXMLIDLABR && getReturnCode() == 0) {
/* 1071 */           PDGUtility pDGUtility = new PDGUtility();
/* 1072 */           OPICMList oPICMList = new OPICMList();
/* 1073 */           oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/* 1074 */           pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */         } 
/*      */ 
/*      */         
/* 1078 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/* 1079 */       } catch (Exception exception) {
/* 1080 */         addMSGLOGReason(exception.getMessage());
/* 1081 */         addError(exception.getMessage());
/* 1082 */         throw exception;
/*      */       } finally {
/* 1084 */         if (this.isSystemResend || this.isSystemResendRFR || this.isSystemResendCache || this.isSystemResendCurrent) {
/* 1085 */           setFlagValue("SYSFEEDRESEND", "No");
/*      */         }
/* 1087 */         if (this.isXMLIDLABR) {
/* 1088 */           deactivateMultiFlagValue("XMLABRPROPFILE");
/*      */         }
/* 1090 */         if (this.t2DTS.equals("&nbsp;")) {
/* 1091 */           this.t2DTS = getNow();
/*      */         }
/* 1093 */         if (this.isPeriodicABR) {
/* 1094 */           EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1095 */           String str6 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*      */ 
/*      */ 
/*      */           
/* 1099 */           String str7 = getNow();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1117 */           String str8 = getEntityLastValfrom(entityItem);
/* 1118 */           if (str8 != null) {
/* 1119 */             str7 = str8;
/* 1120 */             addDebug("setupDTS : " + str7);
/*      */           } else {
/* 1122 */             addDebug(entityItem.getKey() + "can not find last valfrom of the entity! SETUPDTS is getnow().");
/*      */           } 
/*      */           
/* 1125 */           String str9 = "";
/* 1126 */           if (str6 == null) {
/* 1127 */             if ("XMLXLATESETUP".equals(entityItem.getEntityType())) {
/* 1128 */               str9 = getDescription(entityItem, "ADSATTRIBUTE", "short");
/* 1129 */             } else if ("XMLCOMPATSETUP".equals(entityItem.getEntityType())) {
/* 1130 */               str9 = "WWCOMPAT";
/* 1131 */             } else if ("XMLPRODPRICESETUP".equals(entityItem.getEntityType())) {
/* 1132 */               str9 = getDescription(entityItem, "ADSOFFTYPE", "long");
/*      */             } 
/*      */           } else {
/* 1135 */             str9 = (String)ADSTYPES_TBL.get(str6);
/*      */           } 
/*      */           
/* 1138 */           ArrayList<String[]> arrayList = new ArrayList();
/* 1139 */           String str10 = (this.reason.toString().length() < 64) ? this.reason.toString() : this.reason.toString().substring(0, 63);
/* 1140 */           String str11 = PokUtils.getAttributeFlagValue(entityItem, "XMLABRPROPFILE");
/* 1141 */           if (str11 == null) {
/* 1142 */             str11 = "";
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1147 */           StringTokenizer stringTokenizer = new StringTokenizer(str11, "|", false);
/* 1148 */           while (stringTokenizer.hasMoreElements()) {
/* 1149 */             String str12 = stringTokenizer.nextToken();
/* 1150 */             arrayList.add(new String[] { null, getNow(), str, this.t2DTS, this.succQueueNameVct.contains(str12) ? "S" : "F", this.succQueueNameVct.contains(str12) ? "" : str10 });
/* 1151 */             inertMSGLOG(arrayList, entityItem.getEntityType(), entityItem.getEntityID(), str7, str9, str12);
/* 1152 */             arrayList.clear();
/*      */           
/*      */           }
/*      */         
/*      */         }
/* 1157 */         else if (!this.isXMLIDLABR && 
/* 1158 */           this.extxmlfeedVct != null && this.extxmlfeedVct.size() > 0) {
/* 1159 */           EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1160 */           ArrayList<String[]> arrayList = new ArrayList();
/* 1161 */           String str6 = (this.reason.toString().length() < 64) ? this.reason.toString() : this.reason.toString().substring(0, 63);
/* 1162 */           String str7 = getNow();
/* 1163 */           for (byte b = 0; b < this.extxmlfeedVct.size(); b++) {
/* 1164 */             EntityItem entityItem1 = this.extxmlfeedVct.elementAt(b);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1180 */             String str8 = getEntityLastValfrom(entityItem1);
/* 1181 */             if (str8 != null) {
/* 1182 */               str7 = str8;
/* 1183 */               addDebug("setupDTS : " + str7);
/*      */             } else {
/* 1185 */               addDebug(entityItem.getKey() + "can not find last valfrom of the entity! SETUPDTS is getnow().");
/*      */             } 
/*      */ 
/*      */             
/* 1189 */             String str9 = PokUtils.getAttributeFlagValue(entityItem1, "XMLABRPROPFILE");
/* 1190 */             if (str9 == null) {
/* 1191 */               str9 = "";
/*      */             }
/*      */             
/* 1194 */             StringTokenizer stringTokenizer = new StringTokenizer(str9, "|", false);
/* 1195 */             while (stringTokenizer.hasMoreElements()) {
/* 1196 */               String str10 = stringTokenizer.nextToken();
/* 1197 */               arrayList.add(new String[] { Integer.toString(entityItem.getEntityID()), getNow(), str, this.t2DTS, this.succQueueNameVct.contains(str10) ? "S" : "F", this.succQueueNameVct.contains(str10) ? "" : str6 });
/* 1198 */               inertMSGLOG(arrayList, entityItem1.getEntityType(), entityItem1.getEntityID(), str7, entityItem.getEntityType(), str10);
/* 1199 */               arrayList.clear();
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1206 */     } catch (Throwable throwable) {
/* 1207 */       StringWriter stringWriter = new StringWriter();
/* 1208 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/* 1209 */       String str7 = "<pre>{0}</pre>";
/* 1210 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/* 1211 */       setReturnCode(-3);
/* 1212 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/* 1214 */       this.args[0] = throwable.getMessage();
/* 1215 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 1216 */       messageFormat1 = new MessageFormat(str7);
/* 1217 */       this.args[0] = stringWriter.getBuffer().toString();
/* 1218 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/* 1219 */       logError("Exception: " + throwable.getMessage());
/* 1220 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/* 1224 */       setDGTitle(str1);
/* 1225 */       setDGRptName(getShortClassName(getClass()));
/* 1226 */       setDGRptClass(getABRCode());
/*      */       
/* 1228 */       if (!isReadOnly()) {
/* 1229 */         clearSoftLock();
/*      */       }
/* 1231 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */     
/* 1235 */     this.extxmlfeedVct.clear();
/* 1236 */     this.succQueueNameVct.clear();
/*      */ 
/*      */     
/* 1239 */     MessageFormat messageFormat = new MessageFormat(str2);
/* 1240 */     this.args[0] = getShortClassName(getClass());
/* 1241 */     this.args[1] = str1;
/* 1242 */     String str3 = messageFormat.format(this.args);
/*      */     
/* 1244 */     String str4 = null;
/* 1245 */     if (this.isPeriodicABR) {
/* 1246 */       str4 = buildPeriodicRptHeader();
/* 1247 */       restoreXtraContent();
/*      */     } else {
/* 1249 */       str4 = buildDQTriggeredRptHeader();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1255 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/* 1256 */     this.rptSb.insert(0, str5);
/*      */     
/* 1258 */     println(this.rptSb.toString());
/*      */     
/* 1260 */     this.rsBundle = null;
/* 1261 */     this.args = null;
/* 1262 */     this.extxmlfeedVct = null;
/* 1263 */     this.succQueueNameVct = null;
/* 1264 */     messageFormat = null;
/* 1265 */     this.userxmlSb = null;
/* 1266 */     this.rptSb = null;
/* 1267 */     printDGSubmitString();
/* 1268 */     println(EACustom.getTOUDiv());
/* 1269 */     buildReportFooter();
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
/* 1282 */     boolean bool = false;
/* 1283 */     String str = paramEntityItem.getEntityType();
/* 1284 */     int i = paramEntityItem.getEntityID();
/* 1285 */     Statement statement = null;
/*      */     
/* 1287 */     ResultSet resultSet = null;
/* 1288 */     addDebug("rootType :" + str);
/* 1289 */     addDebug("entityID :" + i);
/*      */     
/* 1291 */     StringBuffer stringBuffer = new StringBuffer();
/* 1292 */     stringBuffer.append(" SELECT XMLMESSAGE FROM cache.xmlidlcache");
/* 1293 */     stringBuffer.append(" WHERE xmlentitytype = '" + str + "'");
/* 1294 */     stringBuffer.append(" AND xmlentityID = " + i);
/* 1295 */     stringBuffer.append(" AND xmlcachevalidto > current timestamp with ur");
/*      */     
/* 1297 */     addDebug("query cache sql is :" + stringBuffer.toString());
/*      */ 
/*      */     
/*      */     try {
/* 1301 */       statement = this.m_db.getODSConnection().createStatement();
/* 1302 */       resultSet = statement.executeQuery(stringBuffer.toString());
/*      */       
/* 1304 */       if (resultSet.next()) {
/* 1305 */         this.SystemResendCacheXml = resultSet.getString(1);
/* 1306 */         bool = true;
/*      */       } else {
/* 1308 */         bool = false;
/*      */       } 
/* 1310 */       addDebug("get XMLIDLCache where" + paramEntityItem.getKey() + " is isCached: " + bool);
/* 1311 */     } catch (MiddlewareException middlewareException) {
/* 1312 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 1313 */       middlewareException.printStackTrace();
/* 1314 */       throw middlewareException;
/* 1315 */     } catch (SQLException sQLException) {
/* 1316 */       addDebug("RuntimeException on ? " + sQLException);
/* 1317 */       sQLException.printStackTrace();
/* 1318 */       throw sQLException;
/*      */     } 
/* 1320 */     return bool;
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
/* 1354 */     setReturnCode(1);
/* 1355 */     addDebug("this abr is GroupABR :" + this.m_abri.getABRQueType().equals("GroupABR"));
/* 1356 */     if ("0020".equals(paramString2)) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1362 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */       
/* 1364 */       if (existBefore(attributeChangeHistoryGroup, "0030")) {
/* 1365 */         addDebug(paramEntityItem.getKey() + " Error: A Resend RFR request is not valid since XML was previously created successfully.");
/* 1366 */         addError("Error: A \"Resend RFR\" request is not valid since XML was previously created successfully.", paramEntityItem);
/*      */       } else {
/* 1368 */         this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND_RFR");
/*      */       } 
/*      */     } else {
/*      */       
/* 1372 */       addDebug(paramEntityItem.getKey() + " A \"Resend RFR\" request is not valid since the data must be \"Final\"");
/* 1373 */       addError("Error : A \"Resend RFR\" request is not valid since the data must be \"Final\"", paramEntityItem);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1380 */     this.curStatus = "Ready for Review";
/* 1381 */     this.curStatusvalue = "0040";
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
/* 1397 */     String str = sysFeedResendStatus(this.m_abri.getABRCode(), "_SYSFeedResendValue", "Both");
/*      */     
/* 1399 */     if ("0020".equals(str)) {
/* 1400 */       if ("0020".equals(paramString2)) {
/* 1401 */         this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */       } else {
/* 1403 */         addDebug(paramEntityItem.getKey() + " is not Final");
/*      */ 
/*      */         
/* 1406 */         addError(this.rsBundle.getString("RESEND_ONLY_FINAL"), paramEntityItem);
/*      */       } 
/* 1408 */     } else if ("0040".equals(str)) {
/* 1409 */       if ("0040".equals(paramString2)) {
/*      */         
/* 1411 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */         
/* 1413 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(paramXMLMQ);
/*      */         
/* 1415 */         if (existPassedFinal(attributeChangeHistoryGroup1, attributeChangeHistoryGroup2)) {
/* 1416 */           addDebug(paramEntityItem.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
/* 1417 */           addError(this.rsBundle.getString("RESEND_R4R_PASSEDFINAL"), paramEntityItem);
/*      */         } else {
/* 1419 */           this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */         } 
/*      */       } else {
/* 1422 */         addDebug(paramEntityItem.getKey() + " is not RFR");
/*      */         
/* 1424 */         addError(this.rsBundle.getString("RESEND_ONLY_R4REVIEW"), paramEntityItem);
/*      */       }
/*      */     
/*      */     }
/* 1428 */     else if (!"0020".equals(paramString2) && !"0040".equals(paramString2)) {
/* 1429 */       addDebug(paramEntityItem.getKey() + " is not Final or R4R");
/*      */       
/* 1431 */       addError(this.rsBundle.getString("RESEND_NOT_R4RFINAL"), paramEntityItem);
/*      */     }
/* 1433 */     else if ("0020".equals(paramString2)) {
/* 1434 */       this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */     } else {
/*      */       
/* 1437 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = getADSABRSTATUSHistory("ADSABRSTATUS");
/*      */       
/* 1439 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(paramXMLMQ);
/* 1440 */       if (existPassedFinal(attributeChangeHistoryGroup1, attributeChangeHistoryGroup2)) {
/* 1441 */         addDebug(paramEntityItem.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
/* 1442 */         addError(this.rsBundle.getString("RESEND_R4R_PASSEDFINAL"), paramEntityItem);
/*      */       } else {
/* 1444 */         this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1451 */     this.curStatus = PokUtils.getAttributeValue(paramEntityItem, paramString1, ", ", "", false);
/* 1452 */     this.curStatusvalue = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 1453 */     this.priorStatus = this.curStatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDTFS(EntityItem paramEntityItem, XMLMQ paramXMLMQ) {
/* 1462 */     String str1 = paramXMLMQ.getStatusAttr();
/* 1463 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/* 1464 */     String str3 = "";
/* 1465 */     if ("0020".equals(str2)) {
/* 1466 */       if (getEntityType().equals("XMLPRODPRICESETUP")) {
/* 1467 */         str3 = getQueuedValue("ADSPPABRSTATUS");
/*      */       } else {
/* 1469 */         str3 = getQueuedValue("ADSABRSTATUS");
/*      */       }
/*      */     
/* 1472 */     } else if (getEntityType().equals("XMLPRODPRICESETUP")) {
/* 1473 */       str3 = getRFRQueuedValue("ADSPPABRSTATUS");
/*      */     } else {
/* 1475 */       str3 = getRFRQueuedValue("ADSABRSTATUS");
/*      */     } 
/*      */     
/* 1478 */     addDebug("getDTFS " + getEntityType() + str2 + " from properties file is " + str3);
/* 1479 */     return str3;
/*      */   }
/*      */   
/*      */   private void setupPrintWriters() {
/* 1483 */     String str = this.m_abri.getFileName();
/* 1484 */     int i = str.lastIndexOf(".");
/* 1485 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/* 1486 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/* 1488 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/* 1489 */     } catch (Exception exception) {
/* 1490 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/* 1493 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/* 1494 */     } catch (Exception exception) {
/* 1495 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriters() {
/* 1499 */     if (this.dbgPw != null) {
/* 1500 */       this.dbgPw.flush();
/* 1501 */       this.dbgPw.close();
/* 1502 */       this.dbgPw = null;
/*      */     } 
/* 1504 */     if (this.userxmlPw != null) {
/* 1505 */       this.userxmlPw.flush();
/* 1506 */       this.userxmlPw.close();
/* 1507 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/* 1513 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/* 1515 */       BufferedInputStream bufferedInputStream = null;
/* 1516 */       FileInputStream fileInputStream = null;
/* 1517 */       BufferedReader bufferedReader = null;
/*      */       try {
/* 1519 */         fileInputStream = new FileInputStream(this.userxmlfn);
/* 1520 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/* 1522 */         String str = null;
/* 1523 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/* 1525 */         while ((str = bufferedReader.readLine()) != null) {
/* 1526 */           this.userxmlSb.append(convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/* 1529 */         File file = new File(this.userxmlfn);
/* 1530 */         if (file.exists()) {
/* 1531 */           file.delete();
/*      */         }
/* 1533 */       } catch (Exception exception) {
/* 1534 */         exception.printStackTrace();
/*      */       } finally {
/* 1536 */         if (bufferedInputStream != null) {
/*      */           try {
/* 1538 */             bufferedInputStream.close();
/* 1539 */           } catch (Exception exception) {
/* 1540 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/* 1543 */         if (fileInputStream != null) {
/*      */           try {
/* 1545 */             fileInputStream.close();
/* 1546 */           } catch (Exception exception) {
/* 1547 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 1552 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/* 1555 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/* 1557 */       BufferedInputStream bufferedInputStream = null;
/* 1558 */       FileInputStream fileInputStream = null;
/* 1559 */       BufferedReader bufferedReader = null;
/*      */       try {
/* 1561 */         fileInputStream = new FileInputStream(this.dbgfn);
/* 1562 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/* 1564 */         String str = null;
/* 1565 */         StringBuffer stringBuffer = new StringBuffer();
/* 1566 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/* 1568 */         while ((str = bufferedReader.readLine()) != null) {
/* 1569 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/* 1571 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/* 1574 */         File file = new File(this.dbgfn);
/* 1575 */         if (file.exists()) {
/* 1576 */           file.delete();
/*      */         }
/* 1578 */       } catch (Exception exception) {
/* 1579 */         exception.printStackTrace();
/*      */       } finally {
/* 1581 */         if (bufferedInputStream != null) {
/*      */           try {
/* 1583 */             bufferedInputStream.close();
/* 1584 */           } catch (Exception exception) {
/* 1585 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/* 1588 */         if (fileInputStream != null) {
/*      */           try {
/* 1590 */             fileInputStream.close();
/* 1591 */           } catch (Exception exception) {
/* 1592 */             exception.printStackTrace();
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
/* 1604 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/* 1605 */     Object[] arrayOfObject = { paramString2 };
/* 1606 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/* 1623 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Status: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Prior Status: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1634 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1635 */     this.args[0] = this.m_prof.getOPName();
/* 1636 */     this.args[1] = this.m_prof.getRoleDescription();
/* 1637 */     this.args[2] = this.m_prof.getWGName();
/* 1638 */     this.args[3] = this.t2DTS;
/* 1639 */     this.args[4] = this.curStatus;
/* 1640 */     this.args[5] = this.t1DTS;
/* 1641 */     this.args[6] = this.priorStatus;
/* 1642 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/* 1643 */     this.args[8] = this.abrversion + " " + getABRVersion();
/*      */     
/* 1645 */     return messageFormat.format(this.args);
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
/* 1657 */     String str = "<table>" + NEWLINE + "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{2}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {3} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1663 */     MessageFormat messageFormat = new MessageFormat(str);
/* 1664 */     this.args[0] = this.t2DTS;
/* 1665 */     this.args[1] = this.t1DTS;
/* 1666 */     this.args[2] = this.xmlgenSb.toString();
/* 1667 */     this.args[3] = this.abrversion + " " + getABRVersion();
/*      */     
/* 1669 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityList getEntityListForDiff(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1679 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, paramProfile, paramString);
/*      */     
/* 1681 */     EntityList entityList = this.m_db.getEntityList(paramProfile, extractActionItem, new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/* 1682 */             .getEntityType(), paramEntityItem.getEntityID()) });
/* 1683 */     EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1702 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1712 */     if ("ADSPRODSTRUCT2".equals(paramString) || "ADSSWPRODSTRUCT2".equals(paramString) || "ADSLSEO2".equals(paramString)) {
/* 1713 */       bool = false;
/* 1714 */     } else if (this.t2DTS.equals(paramProfile.getEffOn())) {
/* 1715 */       bool = true;
/*      */     } 
/* 1717 */     if (bool) {
/* 1718 */       String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 1719 */       boolean bool1 = false;
/* 1720 */       boolean bool2 = false;
/* 1721 */       if (entityItem.hasDownLinks()) {
/* 1722 */         for (byte b = 0; b < entityItem.getDownLinkCount(); b++) {
/* 1723 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b);
/* 1724 */           if (entityItem1.hasDownLinks()) {
/* 1725 */             for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/* 1726 */               EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/* 1727 */               if ("AVAIL".equals(entityItem2.getEntityType())) {
/* 1728 */                 EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem2.getAttribute("AVAILTYPE");
/* 1729 */                 if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146")) {
/* 1730 */                   bool1 = true;
/* 1731 */                   String str1 = PokUtils.getAttributeFlagValue(entityItem2, "STATUS");
/* 1732 */                   String str2 = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", "@@", false);
/* 1733 */                   if (str2.compareTo("2010-03-01") <= 0) {
/* 1734 */                     bool2 = true;
/* 1735 */                   } else if ("0040".equals(str) || "0020".equals(str)) {
/* 1736 */                     if ("0020".equals(str1) || "0040".equals(str1)) {
/* 1737 */                       bool2 = true;
/*      */                     
/*      */                     }
/*      */                   }
/* 1741 */                   else if ("0050".equals(str) && (
/* 1742 */                     "0020".equals(str1) || "0040".equals(str1))) {
/* 1743 */                     bool2 = true;
/*      */                   } 
/*      */                   
/* 1746 */                   addDebugComment(3, "Cheking planed AVAIL " + entityItem2.getKey() + " Status is " + str1 + " AVAIL Type: " + 
/* 1747 */                       PokUtils.getAttributeFlagValue(entityItem2, "AVAILTYPE"));
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       }
/* 1754 */       if (bool1 && !bool2) {
/* 1755 */         addError("Error: Invalid Planed AVAIL. There must have at least one Planed AVAIL with Status is " + ("0020".equals(str) ? "Final" : "RFR or Final") + " , else the XML report will not generated", paramEntityItem);
/* 1756 */         return null;
/*      */       } 
/*      */     } 
/*      */     
/* 1760 */     addDebugComment(2, "EntityList for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 1761 */         PokUtils.outputList(entityList));
/*      */     
/* 1763 */     EntityGroup entityGroup = entityList.getParentEntityGroup();
/*      */ 
/*      */     
/* 1766 */     if (isVEFiltered(paramString)) {
/*      */ 
/*      */       
/* 1769 */       String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/* 1770 */       addDebugComment(3, "The status of the root for VE " + paramString + " is: " + str);
/*      */ 
/*      */ 
/*      */       
/* 1774 */       for (byte b = 0; b < VE_Filter_Array.length; b++) {
/* 1775 */         addDebugComment(3, "Looking at VE_filter_Array" + VE_Filter_Array[b][0] + " " + VE_Filter_Array[b][1] + " " + VE_Filter_Array[b][2]);
/*      */         
/* 1777 */         if (VE_Filter_Array[b][0].equals(paramString)) {
/* 1778 */           EntityGroup entityGroup1 = entityList.getEntityGroup(VE_Filter_Array[b][1]);
/* 1779 */           addDebugComment(3, "Found " + entityList.getEntityGroup(VE_Filter_Array[b][1]));
/*      */ 
/*      */           
/* 1782 */           if (entityGroup1 != null) {
/*      */ 
/*      */             
/* 1785 */             EntityItem[] arrayOfEntityItem = entityGroup1.getEntityItemsAsArray();
/*      */ 
/*      */             
/* 1788 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/* 1790 */               String str1 = null;
/* 1791 */               boolean bool1 = true;
/* 1792 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 1793 */               String str2 = entityItem1.getEntityType();
/*      */               
/* 1795 */               addDebugComment(2, "Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */ 
/*      */               
/* 1798 */               String str3 = VE_Filter_Array[b][2];
/*      */ 
/*      */               
/* 1801 */               str1 = PokUtils.getAttributeFlagValue(entityItem1, (String)ITEM_STATUS_ATTR_TBL.get(str2));
/* 1802 */               String str4 = str;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 1909 */               addDebugComment(2, (String)ITEM_STATUS_ATTR_TBL.get(str2) + " is " + str1);
/* 1910 */               if ("AVAIL".equals(str2))
/*      */               
/* 1912 */               { if ("PRODSTRUCT".equals(entityItem.getEntityType()) || "SWPRODSTRUCT".equals(entityItem.getEntityType())) {
/*      */                   
/* 1914 */                   for (byte b2 = 0; b2 < entityItem.getDownLinkCount(); b2++) {
/* 1915 */                     EntityItem entityItem2 = (EntityItem)entityItem.getDownLink(b2);
/* 1916 */                     if ("MODEL".equals(entityItem2.getEntityType())) {
/* 1917 */                       String str6 = PokUtils.getAttributeValue(entityItem2, "ANNDATE", ", ", "@@", false);
/* 1918 */                       addDebugComment(2, "New check PRODSTRUCT ANN DATE is " + str6);
/* 1919 */                       if (str6.compareTo("2010-03-01") <= 0) {
/* 1920 */                         bool1 = false;
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/* 1925 */                   if (!bool1)
/*      */                     continue; 
/* 1927 */                 }  if ("MODEL".equals(entityItem.getEntityType()) || "MODELCONVERT".equals(entityItem.getEntityType())) {
/* 1928 */                   String str6 = PokUtils.getAttributeValue(entityItem, "ANNDATE", ", ", "@@", false);
/* 1929 */                   addDebugComment(2, "New check ROOT ANN DATE is " + str6);
/* 1930 */                   if (str6.compareTo("2010-03-01") <= 0) {
/* 1931 */                     bool1 = false;
/*      */                     continue;
/*      */                   } 
/*      */                 } 
/* 1935 */                 EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem1.getAttribute("AVAILTYPE");
/* 1936 */                 String str5 = PokUtils.getAttributeValue(entityItem1, "EFFECTIVEDATE", ", ", "@@", false);
/* 1937 */                 addDebugComment(2, "New check EFFECTIVEDATE is " + str5);
/*      */                 
/* 1939 */                 if (str1 == null) { bool1 = true; }
/* 1940 */                 else if (eANFlagAttribute != null && eANFlagAttribute.isSelected("146") && str5.compareTo("2010-03-01") <= 0)
/* 1941 */                 { bool1 = false;
/*      */                    }
/*      */                 
/* 1944 */                 else if (str1.equals("0020") || str1.equals("0040"))
/* 1945 */                 { bool1 = false;
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
/* 1959 */               else if (str1 == null) { bool1 = true; }
/* 1960 */               else if (str1.equals("0020")) { bool1 = false; }
/* 1961 */               else if (str1.equals("0040") && str4.equals("0040") && str3
/* 1962 */                 .equals("RFR Final")) { bool1 = false; }
/*      */               
/* 1964 */               if (bool1 == true) {
/*      */                 
/* 1966 */                 addDebugComment(2, "Removing " + str2 + " " + entityItem1.getEntityID() + " " + str1 + " from list");
/* 1967 */                 addDebugComment(2, "Filter criteria is " + str3);
/*      */                 
/* 1969 */                 removeItem(entityGroup, entityItem1);
/*      */               } 
/*      */               
/*      */               continue;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 1977 */       addDebugComment(2, "EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 1978 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */ 
/*      */     
/* 1982 */     if (isVECountryFiltered(paramString)) {
/*      */ 
/*      */       
/* 1985 */       String str1 = entityItem.getEntityType();
/* 1986 */       String str2 = (String)ITEM_COUNTRY_ATTR_TBL.get(str1);
/*      */ 
/*      */       
/* 1989 */       HashSet hashSet = getCountry(entityItem, str2);
/*      */ 
/*      */ 
/*      */       
/* 1993 */       for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/* 1994 */         addDebugComment(3, "Looking at VE_Country_Filter_Array " + VE_Country_Filter_Array[b][0] + " " + VE_Country_Filter_Array[b][1]);
/*      */         
/* 1996 */         if (VE_Country_Filter_Array[b][0].equals(paramString)) {
/* 1997 */           EntityGroup entityGroup1 = entityList.getEntityGroup(VE_Country_Filter_Array[b][1]);
/* 1998 */           addDebugComment(3, "Found " + entityList.getEntityGroup(VE_Country_Filter_Array[b][1]));
/*      */ 
/*      */           
/* 2001 */           if (entityGroup1 != null) {
/*      */ 
/*      */             
/* 2004 */             EntityItem[] arrayOfEntityItem = entityGroup1.getEntityItemsAsArray();
/*      */ 
/*      */             
/* 2007 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/* 2009 */               boolean bool1 = true;
/* 2010 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 2011 */               String str3 = entityItem1.getEntityType();
/*      */               
/* 2013 */               addDebugComment(3, "Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */               
/* 2015 */               String str4 = (String)ITEM_COUNTRY_ATTR_TBL.get(str3);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/* 2020 */               HashSet hashSet1 = getCountry(entityItem1, str4);
/* 2021 */               Iterator<String> iterator = hashSet.iterator();
/* 2022 */               while (iterator.hasNext() && bool1 == true) {
/* 2023 */                 String str = iterator.next();
/* 2024 */                 if (hashSet1.contains(str)) {
/* 2025 */                   bool1 = false;
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/* 2030 */               if (bool1 == true)
/*      */               {
/*      */                 
/* 2033 */                 removeItem(entityGroup, entityItem1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2041 */       addDebugComment(2, "EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 2042 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */     
/* 2045 */     return entityList;
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
/* 2071 */     this.t1DTS = this.m_strEpoch;
/* 2072 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 2073 */     if (this.isPeriodicABR) {
/* 2074 */       addDebug("getT1 entered for Periodic ABR " + entityItem.getKey());
/*      */       
/* 2076 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/* 2077 */       if (eANMetaAttribute == null) {
/* 2078 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/* 2080 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */     } else {
/* 2082 */       String str = paramXMLMQ.getStatusAttr();
/* 2083 */       addDebug("getT1 entered for DQ ABR " + entityItem.getKey() + " " + str + " isSystemResend:" + this.isSystemResend + " isIDLABR:" + this.isXMLIDLABR + " isXMLADSABR=" + this.isXMLADSABR);
/* 2084 */       if (this.isXMLADSABR) {
/* 2085 */         addDebug("getT1 isXMLADSABR");
/* 2086 */         String str1 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0050", (String)null);
/* 2087 */         addDebug("getT1 isXMLADSABR DTS1=" + str1);
/* 2088 */         String str2 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0020", str1);
/* 2089 */         addDebug("getT1 isXMLADSABR DTS9=" + str2);
/*      */         
/* 2091 */         boolean bool = isFullXML(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, str2);
/* 2092 */         addDebug("getT1 isXMLADSABR fullXML=" + bool);
/*      */         
/* 2094 */         if (bool || this.isSystemResend || this.isSystemResendRFR || this.isSystemResendCache || this.isSystemResendCurrent) {
/* 2095 */           addDebug("getT1 isXMLADSABR isSystemResend=" + this.isSystemResend + " || isSystemResendRFR=" + this.isSystemResendRFR + " || isSystemResendCache=" + this.isSystemResendCache + " || isSystemResendCurrent=" + this.isSystemResendCache);
/*      */           
/* 2097 */           this.t1DTS = this.m_strEpoch;
/*      */ 
/*      */ 
/*      */           
/* 2101 */           if (!this.isSystemResend && !this.isSystemResendRFR && !this.isSystemResendCurrent) {
/* 2102 */             setT1Action(entityItem, paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1);
/*      */           }
/* 2104 */         } else if (!bool) {
/*      */           
/* 2106 */           addDebug("getT1 isXMLADSABR fullXML=" + bool + "T2=" + this.t2DTS);
/* 2107 */           String str3 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0030", this.t2DTS);
/* 2108 */           addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS3=" + str3);
/* 2109 */           String str4 = "";
/* 2110 */           String str5 = "";
/* 2111 */           String str6 = "";
/* 2112 */           if (str3 != null && str3.equals(this.m_strEpoch)) {
/* 2113 */             this.t1DTS = this.m_strEpoch;
/* 2114 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "T1=" + this.t1DTS);
/*      */           } else {
/*      */             
/* 2117 */             str4 = getValtoCompareValFromInStatusHistory(entityItem, paramAttributeChangeHistoryGroup2, str3);
/* 2118 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS4=" + str4);
/*      */             
/* 2120 */             str6 = getValFromInStatusHistory("statusHistory", paramAttributeChangeHistoryGroup2, (String)null, str3);
/* 2121 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS6=" + str6);
/*      */             
/* 2123 */             str5 = getValFromInStatusHistory(paramAttributeChangeHistoryGroup1, "0050", str6, str3);
/* 2124 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "DTS5=" + str5);
/*      */             
/* 2126 */             this.t1DTS = getMinTime(str4, str5);
/* 2127 */             addDebug("getT1 isXMLADSABR fullXML=" + bool + "T1=" + this.t1DTS);
/*      */           } 
/* 2129 */           setT1Action(entityItem, paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1);
/*      */         } 
/* 2131 */         addDebug("getT1 isXMLADSABR T1=" + this.t1DTS);
/* 2132 */         addDebug("getT1 isXMLADSABR end");
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
/* 2149 */     String str1 = getT2Status(paramAttributeChangeHistoryGroup1);
/* 2150 */     String str2 = "";
/*      */     
/* 2152 */     if (existBefore(paramAttributeChangeHistoryGroup2, "0030")) {
/*      */       
/* 2154 */       if (str1.equals("0040")) {
/* 2155 */         str2 = getDeltaT1(paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1, "0020");
/* 2156 */         if (str2.equals(this.m_strEpoch)) {
/* 2157 */           addDebug("getT1 CurrentStatus is RFR, there is no Passed Final before, try to find Passed RFR");
/* 2158 */           str2 = getDeltaT1(paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1, "0040");
/* 2159 */           if (str2.equals(this.m_strEpoch)) {
/* 2160 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*      */           } else {
/* 2162 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_CHANGES");
/*      */           } 
/*      */         } else {
/* 2165 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_BEFORE");
/*      */         }
/*      */       
/* 2168 */       } else if (str1.equals("0020")) {
/* 2169 */         str2 = getDeltaT1(paramAttributeChangeHistoryGroup2, paramAttributeChangeHistoryGroup1, "0020");
/* 2170 */         if (str2.equals(this.m_strEpoch)) {
/* 2171 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */         } else {
/* 2173 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_CHANGES");
/*      */         } 
/*      */       } 
/*      */     } else {
/* 2177 */       if (str1.equals("0040")) {
/* 2178 */         this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/* 2179 */       } else if (str1.equals("0020")) {
/* 2180 */         this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */       } 
/* 2182 */       addDebug("getT1 for " + paramEntityItem.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
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
/* 2194 */     boolean bool = false;
/* 2195 */     if (paramAttributeChangeHistoryGroup2 != null && paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() >= 1) {
/* 2196 */       int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount();
/* 2197 */       String str1 = "";
/* 2198 */       String str2 = "";
/* 2199 */       for (int j = i - 1; j >= 0; j--) {
/* 2200 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(j);
/* 2201 */         if (attributeChangeHistoryItem != null) {
/* 2202 */           if ("0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 2203 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 2205 */             if (j == i - 1) {
/* 2206 */               str2 = this.m_strForever;
/*      */             } else {
/* 2208 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(j + 1);
/* 2209 */               if (attributeChangeHistoryItem1 != null) {
/* 2210 */                 str2 = attributeChangeHistoryItem1.getChangeDate();
/*      */               }
/*      */             } 
/* 2213 */             if (str1.compareTo(paramString) < 0 && str2.compareTo(paramString) > 0 && !existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/* 2214 */               this.t1DTS = this.m_strEpoch;
/* 2215 */               bool = true;
/*      */               break;
/*      */             } 
/* 2218 */           } else if ("0020".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 2219 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 2221 */             if (j == i - 1) {
/* 2222 */               str2 = this.m_strForever;
/*      */             } else {
/* 2224 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(j + 1);
/* 2225 */               if (attributeChangeHistoryItem1 != null) {
/* 2226 */                 str2 = attributeChangeHistoryItem1.getChangeDate();
/*      */               }
/*      */             } 
/* 2229 */             if (str1.compareTo(paramString) < 0 && str2.compareTo(paramString) > 0 && !existDeltaT1(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, "0020")) {
/* 2230 */               this.t1DTS = this.m_strEpoch;
/* 2231 */               bool = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2238 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 2245 */     if (paramAttributeChangeHistoryGroup != null) {
/* 2246 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2247 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2248 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 2249 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2254 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int countBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 2260 */     byte b = 0;
/* 2261 */     if (paramAttributeChangeHistoryGroup != null) {
/* 2262 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2263 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2264 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 2265 */           b++;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2270 */     return b;
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
/* 2401 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "SYSFEEDRESEND");
/* 2402 */     addDebug("sysfeedFlag= " + str);
/*      */     
/* 2404 */     if (this.isXMLIDLABR && this.isSystemResendCurrent) {
/* 2405 */       addDebug("CASE B Initialize Cache Current ");
/*      */       
/* 2407 */       String str1 = getValtoInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, (String)null);
/*      */       
/* 2409 */       String str2 = getValFromInStatusHistory("xmlIDLStatusHistory", paramAttributeChangeHistoryGroup3, "0050", (String)null);
/*      */       
/* 2411 */       this.t2DTS = getMinTime(str2, str1);
/* 2412 */       addDebug("CASE B Initialize Cache Current T2=" + this.t2DTS);
/* 2413 */     } else if (this.isXMLIDLABR && this.isXMLCACHE) {
/* 2414 */       if (!existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/* 2415 */         addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is not found ");
/*      */ 
/*      */         
/* 2418 */         String str1 = getValtoInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, (String)null);
/*      */         
/* 2420 */         String str2 = getValFromInStatusHistory("xmlIDLStatusHistory", paramAttributeChangeHistoryGroup3, "0050", (String)null);
/*      */         
/* 2422 */         this.t2DTS = getMinTime(str2, str1);
/* 2423 */         addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is not found T2=" + this.t2DTS);
/*      */       } else {
/* 2425 */         addDebug("CASE A Initialize Cache when (ADSABRSTATUS) = Passed (0030) is found ");
/*      */         
/* 2427 */         String str1 = getValFromInStatusHistory("xmlIDLStatusHistory", paramAttributeChangeHistoryGroup3, "0050", (String)null);
/*      */         
/* 2429 */         String str2 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0030", str1);
/*      */         
/* 2431 */         str1 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0050", str2);
/*      */         
/* 2433 */         str2 = getValtoCompareValFromInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, str1);
/* 2434 */         this.t2DTS = getMinTime(str1, str2);
/* 2435 */         addDebug("CASE A Initialize Cache (ADSABRSTATUS) = Passed (0030) is found T2=" + this.t2DTS);
/*      */       } 
/* 2437 */     } else if (!this.isXMLIDLABR && this.isPeriodicABR) {
/*      */ 
/*      */ 
/*      */       
/* 2441 */       if (this.isPeriodicABR)
/*      */       {
/* 2443 */         this.t2DTS = getEntityT2DTS(paramEntityItem);
/* 2444 */         if (this.t2DTS == null) {
/* 2445 */           this.t2DTS = getNow();
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
/* 2468 */     else if (this.isXMLADSABR) {
/* 2469 */       if ("RFR".equals(str)) {
/* 2470 */         addDebug("CASE F Resend RFR");
/* 2471 */         String str1 = paramXMLMQ.getStatusAttr();
/* 2472 */         String str2 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/* 2473 */         if (!"0020".equals(str2)) {
/* 2474 */           addError("A \"Resend RFR\" request is not valid since the data must be \"Final\".", paramEntityItem);
/*      */         }
/* 2476 */         else if (existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/* 2477 */           addError("A \"Resend RFR\" request is not valid since XML was previously created successfully.", paramEntityItem);
/*      */         } else {
/* 2479 */           setRFRT2DTS(paramEntityItem, paramAttributeChangeHistoryGroup2);
/*      */         } 
/*      */         
/* 2482 */         addDebug("CASE F Resend RFR T2=" + this.t2DTS);
/*      */       } else {
/*      */         
/* 2485 */         addDebug("CASE C Production & D Resend & E Resend Cache");
/*      */         
/* 2487 */         String str1 = getValFromInStatusHistory("adsStatusHistory", paramAttributeChangeHistoryGroup1, "0050", (String)null);
/*      */         
/* 2489 */         String str2 = getValtoCompareValFromInStatusHistory(paramEntityItem, paramAttributeChangeHistoryGroup2, str1);
/* 2490 */         this.t2DTS = getMinTime(str1, str2);
/* 2491 */         addDebug("CASE C Production & D Resend & E Resend Cache T2=" + this.t2DTS);
/*      */       } 
/*      */     } else {
/* 2494 */       addError("There is no such case for the entity.", paramEntityItem);
/*      */     } 
/*      */     
/* 2497 */     if (this.isXMLIDLABR) {
/* 2498 */       setIDLSTATUS(paramEntityItem, paramAttributeChangeHistoryGroup2);
/* 2499 */       addDebug("setIDLSTATUS");
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
/* 2518 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2519 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2520 */       int j = countBefore(paramAttributeChangeHistoryGroup, "0020");
/* 2521 */       if (j > 0) {
/* 2522 */         boolean bool1 = false;
/* 2523 */         boolean bool2 = false;
/* 2524 */         byte b = 0;
/* 2525 */         for (int k = i - 1; k >= 0; k--) {
/* 2526 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(k);
/*      */           
/* 2528 */           b++;
/* 2529 */           if (!bool1 && attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals("0020") && b == j) {
/* 2530 */             bool1 = true;
/*      */           }
/*      */           
/* 2533 */           if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals("0040") && bool1) {
/*      */             
/* 2535 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(k + 1);
/* 2536 */             if (attributeChangeHistoryItem1 != null) {
/* 2537 */               this.t2DTS = attributeChangeHistoryItem1.getChangeDate();
/*      */               
/* 2539 */               this.t2DTS = adjustTimeSecond(this.t2DTS, -30);
/* 2540 */               bool2 = true;
/* 2541 */               this.priorStatus = "Ready for Review";
/*      */               break;
/*      */             } 
/* 2544 */             addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status has no prior history", paramEntityItem);
/*      */           } 
/*      */         } 
/*      */         
/* 2548 */         if (!bool2) {
/* 2549 */           addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status ever being Final but not ever being RFR", paramEntityItem);
/*      */         }
/*      */       } else {
/* 2552 */         addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status never being RFR or Final", paramEntityItem);
/*      */       } 
/*      */     } else {
/* 2555 */       addError("Error: getT2Time for SYSFEEDRESEND RFR ABR, the Status has no history and never being RFR or Final", paramEntityItem);
/*      */     } 
/* 2557 */     addDebug("In the setRFRT2DTS function, the T2 value is " + this.t2DTS);
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
/* 2574 */     String str1 = null;
/* 2575 */     String str2 = "";
/* 2576 */     boolean bool = false;
/* 2577 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2578 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2579 */       for (int j = i - 1; j >= 0; j--) {
/* 2580 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2581 */         if (attributeChangeHistoryItem != null && (
/* 2582 */           "0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode()))) {
/* 2583 */           bool = true;
/* 2584 */           if (j == i - 1) {
/* 2585 */             str2 = this.m_strForever;
/* 2586 */             if (paramString == null) {
/* 2587 */               str1 = str2;
/*      */               
/*      */               break;
/*      */             } 
/* 2591 */             if (str2.compareTo(paramString) < 0) {
/* 2592 */               str1 = str2;
/*      */               
/*      */               break;
/*      */             } 
/*      */           } else {
/* 2597 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j + 1);
/* 2598 */             if (attributeChangeHistoryItem1 != null) {
/* 2599 */               str2 = attributeChangeHistoryItem1.getChangeDate();
/* 2600 */               if (paramString == null) {
/* 2601 */                 str1 = str2;
/* 2602 */                 str1 = adjustTimeSecond(str1, -30);
/*      */                 
/*      */                 break;
/*      */               } 
/* 2606 */               if (str2.compareTo(paramString) < 0) {
/* 2607 */                 str1 = str2;
/* 2608 */                 str1 = adjustTimeSecond(str1, -30);
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2617 */       if (!bool) {
/* 2618 */         addError("Error: A request is not valid since the data was never \"Ready for Review\" or \"Final\"", paramEntityItem);
/*      */       }
/*      */     } else {
/* 2621 */       addError("Error: A request is not valid since the status has no history.", paramEntityItem);
/*      */     } 
/* 2623 */     addDebug("The VALTO - 30s value for the most current value of STATUS = {Final(0020) |Ready for Review(0040)} is " + str1);
/* 2624 */     return str1;
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
/* 2638 */     String str1 = null;
/* 2639 */     String str2 = "";
/* 2640 */     String str3 = "";
/* 2641 */     boolean bool = false;
/* 2642 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2643 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2644 */       for (int j = i - 1; j >= 0; j--) {
/* 2645 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2646 */         if (attributeChangeHistoryItem != null && (
/* 2647 */           "0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode()))) {
/* 2648 */           bool = true;
/* 2649 */           str3 = attributeChangeHistoryItem.getChangeDate();
/* 2650 */           if (str3.compareTo(paramString) < 0) {
/* 2651 */             if (j == i - 1) {
/* 2652 */               str2 = this.m_strForever;
/* 2653 */               str1 = str2;
/*      */               break;
/*      */             } 
/* 2656 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j + 1);
/* 2657 */             if (attributeChangeHistoryItem1 != null) {
/* 2658 */               str2 = attributeChangeHistoryItem1.getChangeDate();
/* 2659 */               str1 = str2;
/* 2660 */               str1 = adjustTimeSecond(str1, -30);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2668 */       if (!bool) {
/* 2669 */         addError("Error: A request is not valid since the data was never \"Ready for Review\" or \"Final\"", paramEntityItem);
/*      */       }
/*      */     } else {
/* 2672 */       addError("Error: A request is not valid since the status has no history.", paramEntityItem);
/*      */     } 
/* 2674 */     addDebug("The VALTO - 30s value for the most current value of STATUS = {Final(0020) |Ready for Review(0040)} is " + str1);
/* 2675 */     return str1;
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
/* 2687 */     String str1 = null;
/* 2688 */     String str2 = "";
/* 2689 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2690 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2691 */       for (int j = i - 1; j >= 0; j--) {
/* 2692 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2693 */         if (attributeChangeHistoryItem != null && 
/* 2694 */           paramString1.equals(attributeChangeHistoryItem.getFlagCode()))
/*      */         {
/* 2696 */           if (paramString2 != null && paramString3 != null) {
/*      */             
/* 2698 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 2699 */             if (str2.compareTo(paramString2) > 0 && str2.compareTo(paramString3) < 0) {
/* 2700 */               str1 = attributeChangeHistoryItem.getChangeDate();
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2708 */     addDebug("The valfrom value of the StatusHistory at " + paramString1 + " which is beteween " + paramString2 + " and " + paramString3 + " is " + str1);
/* 2709 */     return str1;
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
/* 2721 */     String str1 = null;
/* 2722 */     String str2 = "";
/* 2723 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() >= 1) {
/* 2724 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/* 2725 */       for (int j = i - 1; j >= 0; j--) {
/* 2726 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(j);
/* 2727 */         if (attributeChangeHistoryItem != null) {
/* 2728 */           if (paramString2 == null) {
/* 2729 */             if ("0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/*      */               
/* 2731 */               str2 = attributeChangeHistoryItem.getChangeDate();
/* 2732 */               if (paramString3 == null) {
/* 2733 */                 str1 = str2;
/*      */                 
/*      */                 break;
/*      */               } 
/* 2737 */               if (str2.compareTo(paramString3) < 0) {
/* 2738 */                 str1 = str2;
/*      */ 
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/* 2744 */           } else if (paramString2.equals(attributeChangeHistoryItem.getFlagCode())) {
/*      */             
/* 2746 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 2747 */             if (paramString3 == null) {
/* 2748 */               str1 = str2;
/*      */               
/*      */               break;
/*      */             } 
/* 2752 */             if (str2.compareTo(paramString3) < 0) {
/* 2753 */               str1 = str2;
/*      */ 
/*      */ 
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 2763 */       addError("Error: A request is not valid since there is no history of " + paramString1);
/*      */     } 
/* 2765 */     addDebug("The valfrom value of the " + paramString1 + " at " + ((paramString2 == null) ? "0020|0040" : paramString2) + " which earlier than the givingTime: " + paramString3 + " is " + str1);
/*      */     
/* 2767 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setIDLSTATUS(EntityItem paramEntityItem, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) {
/* 2776 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("STATUS");
/* 2777 */     if (eANMetaAttribute != null) {
/* 2778 */       boolean bool = false;
/* 2779 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2780 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2781 */         if (attributeChangeHistoryItem != null && ("0020".equals(attributeChangeHistoryItem.getFlagCode()) || "0040".equals(attributeChangeHistoryItem.getFlagCode()))) {
/* 2782 */           this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 2783 */           this.curStatusvalue = attributeChangeHistoryItem.getFlagCode();
/* 2784 */           AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */           
/* 2786 */           if (attributeChangeHistoryItem1 != null) {
/* 2787 */             this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/* 2788 */             addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/* 2789 */                 .getFlagCode());
/*      */           } 
/* 2791 */           bool = true;
/*      */           break;
/*      */         } 
/*      */       } 
/* 2795 */       if (!bool) {
/* 2796 */         addError(paramEntityItem.getKey() + ", " + this.rsBundle.getString("IDL_NOT_R4RFINAL"));
/*      */       }
/*      */     } else {
/* 2799 */       addError(paramEntityItem.getKey() + " , Error: There is not such attribute STATUS.");
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
/* 2812 */     String str = "";
/* 2813 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 2814 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 2816 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2817 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 2818 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */ 
/*      */           
/* 2822 */           if (attributeChangeHistoryItem.getChangeDate().compareTo(this.t2DTS) < 0) {
/*      */             
/* 2824 */             if (!"0020".equals(attributeChangeHistoryItem.getFlagCode()) && !"0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 2825 */               addError(entityItem.getKey() + " is not Final or R4R");
/* 2826 */               addError(this.rsBundle.getString("NOT_R4RFINAL"), entityItem);
/*      */               break;
/*      */             } 
/* 2829 */             this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 2830 */             this.curStatusvalue = attributeChangeHistoryItem.getFlagCode();
/* 2831 */             str = attributeChangeHistoryItem.getFlagCode();
/* 2832 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */             
/* 2834 */             if (attributeChangeHistoryItem != null) {
/* 2835 */               this.priorStatus = attributeChangeHistoryItem.getAttributeValue();
/* 2836 */               addDebug("getT2Status [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem.getChangeDate() + " flagcode: " + attributeChangeHistoryItem
/* 2837 */                   .getFlagCode());
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 2845 */       addDebug("getT2Status for " + entityItem.getKey() + " getChangeHistoryItemCount less than 0.");
/*      */     } 
/* 2847 */     return str;
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
/* 2859 */     String str1 = this.m_strEpoch;
/* 2860 */     String str2 = null;
/* 2861 */     String str3 = null;
/* 2862 */     String str4 = "";
/*      */ 
/*      */ 
/*      */     
/* 2866 */     boolean bool = false;
/*      */     
/* 2868 */     if (paramAttributeChangeHistoryGroup2 != null && paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() > 0) {
/*      */       
/* 2870 */       for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2871 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/* 2872 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 2875 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString))
/*      */           {
/* 2877 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 2878 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/* 2879 */             if (attributeChangeHistoryItem1 != null) {
/*      */               
/* 2881 */               str3 = attributeChangeHistoryItem1.getChangeDate();
/*      */               
/* 2883 */               str3 = adjustTimeSecond(str3, -30);
/*      */             } else {
/* 2885 */               addDebug("existDeltaT1 for STATUS has no valto value of the stauts of " + paramString);
/*      */             } 
/* 2887 */             addDebug("getDeltaT1 DTS1= " + str2 + " and  DTS2 = " + str3);
/*      */             
/* 2889 */             if (str3 != null && str2 != null) {
/* 2890 */               if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 0) {
/* 2891 */                 for (int j = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/* 2892 */                   AttributeChangeHistoryItem attributeChangeHistoryItem2 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j - 1);
/* 2893 */                   if (attributeChangeHistoryItem2 != null && 
/* 2894 */                     attributeChangeHistoryItem2.getFlagCode().equals("0050")) {
/*      */                     
/* 2896 */                     AttributeChangeHistoryItem attributeChangeHistoryItem3 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j);
/* 2897 */                     if (attributeChangeHistoryItem3.getFlagCode().equals("0030")) {
/* 2898 */                       str4 = attributeChangeHistoryItem2.getChangeDate();
/* 2899 */                       if (str4.compareTo(str2) >= 0 && str4.compareTo(str3) <= 0) {
/* 2900 */                         str1 = str4;
/* 2901 */                         bool = true;
/*      */ 
/*      */ 
/*      */                         
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } else {
/* 2911 */                 addDebug("getDeltaT1 for ADSABRSTATUS has no changed history!");
/*      */               } 
/*      */             }
/*      */             
/* 2915 */             if (bool) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 2924 */       addDebug("getDeltaT1 for STATUS has no changed history!");
/*      */     } 
/* 2926 */     if (str1.equals(this.m_strEpoch)) {
/* 2927 */       addDebug("getDeltaT1 not find the VALFROM of the first ADSABRSTATUS = é¥æ·n processé¥ï¿½(0050) where DTS1 <= VALFROM <= DTS2");
/*      */     }
/* 2929 */     return bool;
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
/* 2942 */     String str1 = this.m_strEpoch;
/* 2943 */     String str2 = null;
/* 2944 */     String str3 = null;
/* 2945 */     String str4 = "";
/*      */ 
/*      */ 
/*      */     
/* 2949 */     boolean bool = false;
/*      */     
/* 2951 */     if (paramAttributeChangeHistoryGroup2 != null && paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() > 0) {
/*      */       
/* 2953 */       for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 2954 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/* 2955 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 2958 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString))
/*      */           {
/* 2960 */             str2 = attributeChangeHistoryItem.getChangeDate();
/* 2961 */             AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/* 2962 */             if (attributeChangeHistoryItem1 != null) {
/*      */               
/* 2964 */               str3 = attributeChangeHistoryItem1.getChangeDate();
/*      */               
/* 2966 */               str3 = adjustTimeSecond(str3, -30);
/*      */             } else {
/* 2968 */               addDebug("getDeltaT1 for STATUS has no valto value of the stauts of " + paramString);
/*      */             } 
/* 2970 */             addDebug("getDeltaT1 DTS1= " + str2 + " and  DTS2 = " + str3);
/*      */             
/* 2972 */             if (str3 != null && str2 != null) {
/* 2973 */               if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 0) {
/* 2974 */                 for (int j = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/* 2975 */                   AttributeChangeHistoryItem attributeChangeHistoryItem2 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j - 1);
/* 2976 */                   if (attributeChangeHistoryItem2 != null && 
/* 2977 */                     attributeChangeHistoryItem2.getFlagCode().equals("0050")) {
/*      */                     
/* 2979 */                     AttributeChangeHistoryItem attributeChangeHistoryItem3 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(j);
/* 2980 */                     if (attributeChangeHistoryItem3.getFlagCode().equals("0030")) {
/* 2981 */                       str4 = attributeChangeHistoryItem2.getChangeDate();
/* 2982 */                       if (str4.compareTo(str2) >= 0 && str4.compareTo(str3) <= 0) {
/* 2983 */                         str1 = str4;
/* 2984 */                         bool = true;
/*      */ 
/*      */ 
/*      */                         
/*      */                         break;
/*      */                       } 
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               } else {
/* 2994 */                 addDebug("getDeltaT1 for ADSABRSTATUS has no changed history!");
/*      */               } 
/*      */             }
/*      */             
/* 2998 */             if (bool) {
/*      */               break;
/*      */             }
/*      */           }
/*      */         
/*      */         }
/*      */       } 
/*      */     } else {
/*      */       
/* 3007 */       addDebug("getDeltaT1 for STATUS has no changed history!");
/*      */     } 
/* 3009 */     if (str1.equals(this.m_strEpoch)) {
/* 3010 */       addDebug("getDeltaT1 not find the VALFROM of the first ADSABRSTATUS = é¥æ·n processé¥ï¿½(0050) where DTS1 <= VALFROM <= DTS2");
/*      */     }
/* 3012 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTQStatus(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws MiddlewareRequestException {
/* 3020 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 3022 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 3023 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 3024 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 3027 */           if (paramString.compareTo(attributeChangeHistoryItem.getChangeDate()) > 0) {
/* 3028 */             return attributeChangeHistoryItem.getFlagCode();
/*      */           }
/*      */         }
/*      */       } 
/*      */     } else {
/* 3033 */       addDebug("getTQStatus for STATUS has no changed history!");
/*      */     } 
/* 3035 */     return "@@";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getADSABRSTATUSHistory(String paramString) throws MiddlewareException {
/* 3041 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 3043 */     EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 3044 */     if (eANAttribute != null) {
/* 3045 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 3047 */     addDebug(paramString + " of " + entityItem.getKey() + "  was null");
/* 3048 */     return null;
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
/* 3059 */     if (paramString1 != null) {
/* 3060 */       if (paramString2 != null) {
/* 3061 */         return (paramString1.compareTo(paramString2) > 0) ? paramString2 : paramString1;
/*      */       }
/* 3063 */       return paramString1;
/*      */     } 
/*      */     
/* 3066 */     if (paramString2 != null) {
/* 3067 */       return paramString2;
/*      */     }
/* 3069 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ paramXMLMQ) throws MiddlewareException {
/* 3076 */     String str = paramXMLMQ.getStatusAttr();
/* 3077 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 3078 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 3079 */     if (eANAttribute != null) {
/* 3080 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 3082 */     addDebug(" STATUS of " + entityItem.getKey() + "  was null");
/* 3083 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existPassedFinal(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2) throws MiddlewareRequestException {
/* 3090 */     boolean bool1 = false;
/* 3091 */     boolean bool2 = false;
/* 3092 */     if (paramAttributeChangeHistoryGroup1 != null) {
/* 3093 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/*      */         
/* 3095 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 3096 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */ 
/*      */           
/* 3100 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 3102 */             bool1 = true;
/*      */           }
/* 3104 */           if (bool1 && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 3105 */             String str = getTQStatus(paramAttributeChangeHistoryGroup2, attributeChangeHistoryItem.getChangeDate());
/* 3106 */             if (str.equals("0020")) {
/* 3107 */               bool2 = true;
/*      */               break;
/*      */             } 
/* 3110 */             bool1 = false;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3117 */     return bool2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVEFiltered(String paramString) {
/* 3123 */     for (byte b = 0; b < VE_Filter_Array.length; b++) {
/* 3124 */       if (VE_Filter_Array[b][0].equals(paramString))
/* 3125 */         return true; 
/*      */     } 
/* 3127 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVECountryFiltered(String paramString) {
/* 3134 */     for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/* 3135 */       if (VE_Country_Filter_Array[b][0].equals(paramString))
/* 3136 */         return true; 
/*      */     } 
/* 3138 */     return false;
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
/* 3153 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 3154 */     if (profile == null) {
/* 3155 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 3157 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 3159 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 3160 */       addDebug("switchRole nlsids: " + str);
/* 3161 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 3162 */       while (stringTokenizer.hasMoreTokens()) {
/* 3163 */         String str1 = stringTokenizer.nextToken();
/* 3164 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 3165 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 3166 */           profile.getReadLanguages().addElement(nLSItem);
/* 3167 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3172 */     return profile;
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
/* 3186 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 3187 */     if (profile == null) {
/* 3188 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 3190 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 3192 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 3193 */       addDebug("switchRole nlsids: " + str);
/* 3194 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 3195 */       while (stringTokenizer.hasMoreTokens()) {
/* 3196 */         String str1 = stringTokenizer.nextToken();
/* 3197 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 3198 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 3199 */           profile.getReadLanguages().addElement(nLSItem);
/* 3200 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3205 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 3211 */     return this.m_db;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/* 3216 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 3221 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addMSGLOGReason(String paramString) {
/* 3226 */     this.reason.append(paramString);
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
/* 3237 */     if (3 <= DEBUG_LVL) {
/* 3238 */       if (this.dbgPw != null) {
/* 3239 */         this.dbgLen += paramString.length();
/* 3240 */         this.dbgPw.println(paramString);
/* 3241 */         this.dbgPw.flush();
/*      */       } else {
/* 3243 */         this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
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
/* 3254 */     if (paramInt <= DEBUG_LVL) {
/* 3255 */       if (this.dbgPw != null) {
/* 3256 */         this.dbgLen += paramString.length();
/* 3257 */         this.dbgPw.println(paramString);
/* 3258 */         this.dbgPw.flush();
/*      */       } else {
/* 3260 */         this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 3268 */     addOutput(paramString);
/* 3269 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 3277 */     String str = getLD_NDN(paramEntityItem);
/* 3278 */     addOutput(str + " " + paramString);
/* 3279 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/* 3285 */     return this.rsBundle;
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
/* 3297 */     MessageFormat messageFormat = null;
/*      */     
/* 3299 */     byte b1 = 0;
/* 3300 */     boolean bool = false;
/*      */ 
/*      */     
/* 3303 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*      */       
/* 3305 */       String str = paramVector.elementAt(b2);
/* 3306 */       addDebug("in notify looking at prop file " + str);
/*      */       try {
/* 3308 */         ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/* 3309 */             getLocale(getProfile().getReadLanguage().getNLSID()));
/* 3310 */         Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 3311 */         boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/* 3312 */         hashtable.put("MQCID", paramXMLMQ.getMQCID());
/* 3313 */         hashtable.put("XMLTYPE", "ADS");
/* 3314 */         Hashtable hashtable1 = MQUsage.getUserProperties(resourceBundle, paramXMLMQ.getMQCID());
/* 3315 */         if (bool1) {
/*      */           try {
/* 3317 */             addDebug("User infor " + hashtable1);
/* 3318 */             MQUsage.putToMQQueueWithRFH2("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable, hashtable1);
/*      */             
/* 3320 */             messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/* 3321 */             this.args[0] = str;
/* 3322 */             this.args[1] = paramString1;
/* 3323 */             addOutput(messageFormat.format(this.args));
/* 3324 */             b1++;
/*      */             
/* 3326 */             this.succQueueNameVct.add(str);
/* 3327 */             if (!bool) {
/*      */               
/* 3329 */               addXMLGenMsg("SUCCESS", paramString1);
/* 3330 */               addDebug("sent successfully to prop file " + str);
/*      */             } 
/* 3332 */           } catch (MQException mQException) {
/*      */ 
/*      */             
/* 3335 */             addXMLGenMsg("FAILED", paramString1);
/* 3336 */             bool = true;
/* 3337 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/* 3338 */             this.args[0] = str + " " + paramString1;
/* 3339 */             this.args[1] = "" + mQException.completionCode;
/* 3340 */             this.args[2] = "" + mQException.reasonCode;
/* 3341 */             addMSGLOGReason(messageFormat.format(this.args));
/* 3342 */             addError(messageFormat.format(this.args));
/* 3343 */             mQException.printStackTrace(System.out);
/* 3344 */             addDebug("failed sending to prop file " + str);
/* 3345 */           } catch (IOException iOException) {
/*      */             
/* 3347 */             addXMLGenMsg("FAILED", paramString1);
/* 3348 */             bool = true;
/* 3349 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/* 3350 */             this.args[0] = str + " " + paramString1;
/* 3351 */             this.args[1] = iOException.toString();
/* 3352 */             addMSGLOGReason(messageFormat.format(this.args));
/* 3353 */             addError(messageFormat.format(this.args));
/* 3354 */             iOException.printStackTrace(System.out);
/* 3355 */             addDebug("failed sending to prop file " + str);
/*      */           } 
/*      */         } else {
/*      */           
/* 3359 */           messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/* 3360 */           this.args[0] = str;
/* 3361 */           addMSGLOGReason("XML was generated but NOTIFY was false in the {0} properties file.");
/* 3362 */           addError(messageFormat.format(this.args));
/*      */           
/* 3364 */           addXMLGenMsg("NOT_SENT", paramString1);
/* 3365 */           addDebug("not sent to prop file " + str + " because Notify not true");
/*      */         }
/*      */       
/* 3368 */       } catch (MissingResourceException missingResourceException) {
/* 3369 */         addXMLGenMsg("FAILED", str + " " + paramString1);
/* 3370 */         bool = true;
/* 3371 */         addMSGLOGReason("Prop file " + str + " " + paramString1 + " not found");
/* 3372 */         addError("Prop file " + str + " " + paramString1 + " not found");
/*      */       } 
/*      */     } 
/*      */     
/* 3376 */     if (b1 > 0 && b1 != paramVector.size()) {
/* 3377 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
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
/* 3389 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 3390 */     Transformer transformer = transformerFactory.newTransformer();
/* 3391 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 3393 */     transformer.setOutputProperty("indent", "no");
/* 3394 */     transformer.setOutputProperty("method", "xml");
/* 3395 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 3398 */     parseAndConvert(paramDocument);
/*      */ 
/*      */     
/* 3401 */     StringWriter stringWriter = new StringWriter();
/* 3402 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 3403 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 3404 */     transformer.transform(dOMSource, streamResult);
/* 3405 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */ 
/*      */     
/* 3409 */     transformer.setOutputProperty("indent", "yes");
/* 3410 */     stringWriter = new StringWriter();
/* 3411 */     streamResult = new StreamResult(stringWriter);
/* 3412 */     transformer.transform(dOMSource, streamResult);
/* 3413 */     if (!USERXML_OFF_LOG) {
/* 3414 */       addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     }
/*      */     
/* 3417 */     return str;
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
/* 3428 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 3429 */     Transformer transformer = transformerFactory.newTransformer();
/* 3430 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 3432 */     transformer.setOutputProperty("indent", "no");
/* 3433 */     transformer.setOutputProperty("method", "xml");
/* 3434 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 3437 */     parseAndConvert(paramDocument);
/*      */ 
/*      */     
/* 3440 */     StringWriter stringWriter = new StringWriter();
/* 3441 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 3442 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 3443 */     transformer.transform(dOMSource, streamResult);
/* 3444 */     return XMLElem.removeCheat(stringWriter.toString());
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
/* 3455 */     if (paramNode.hasChildNodes()) {
/* 3456 */       NodeList nodeList = paramNode.getChildNodes();
/*      */       
/* 3458 */       for (byte b = 0; b < nodeList.getLength(); b++) {
/* 3459 */         Node node = nodeList.item(b);
/* 3460 */         if (node.hasChildNodes()) {
/* 3461 */           parseAndConvert(node);
/*      */         } else {
/*      */           
/* 3464 */           node.setNodeValue(convertquotToHTML(node.getNodeValue()));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void addUserXML(String paramString) {
/* 3471 */     if (this.userxmlPw != null) {
/* 3472 */       this.userxmlLen += paramString.length();
/* 3473 */       this.userxmlPw.println(paramString);
/* 3474 */       this.userxmlPw.flush();
/*      */     } else {
/* 3476 */       this.userxmlSb.append(convertToHTML(paramString) + NEWLINE);
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
/* 3490 */     boolean bool = false;
/* 3491 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 3492 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 3493 */     if (str.equals("all")) {
/* 3494 */       bool = true;
/*      */     } else {
/* 3496 */       HashSet<String> hashSet = new HashSet();
/* 3497 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 3498 */       while (stringTokenizer.hasMoreTokens()) {
/* 3499 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 3501 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 3502 */       hashSet.clear();
/*      */     } 
/*      */     
/* 3505 */     if (!bool) {
/* 3506 */       addDebug("PDHDOMAIN for " + paramEntityItem.getKey() + " did not include " + str + ", execution is bypassed [" + 
/* 3507 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/* 3509 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale getLocale(int paramInt) {
/* 3519 */     Locale locale = null;
/* 3520 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 3523 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 3547 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 3557 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 3566 */     return "ADSABRSTATUS";
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
/* 3577 */     String str = "";
/* 3578 */     StringBuffer stringBuffer = new StringBuffer();
/* 3579 */     StringCharacterIterator stringCharacterIterator = null;
/* 3580 */     char c = ' ';
/* 3581 */     if (paramString != null) {
/* 3582 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 3583 */       c = stringCharacterIterator.first();
/* 3584 */       while (c != '￿') {
/*      */         
/* 3586 */         switch (c) {
/*      */           
/*      */           case '<':
/* 3589 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 3592 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */           
/*      */           case '"':
/* 3597 */             stringBuffer.append("&quot;");
/*      */             break;
/*      */           
/*      */           case '\'':
/* 3601 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 3610 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 3613 */         c = stringCharacterIterator.next();
/*      */       } 
/* 3615 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 3618 */     return str;
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
/* 3632 */     String str = "";
/* 3633 */     StringBuffer stringBuffer = new StringBuffer();
/* 3634 */     StringCharacterIterator stringCharacterIterator = null;
/* 3635 */     char c = ' ';
/* 3636 */     if (paramString != null) {
/* 3637 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 3638 */       c = stringCharacterIterator.first();
/* 3639 */       while (c != '￿') {
/*      */         
/* 3641 */         switch (c) {
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/* 3646 */             stringBuffer.append("@amp;quot;");
/*      */             break;
/*      */           
/*      */           case '\'':
/* 3650 */             stringBuffer.append("@amp;#39;");
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 3659 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 3662 */         c = stringCharacterIterator.next();
/*      */       } 
/* 3664 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 3667 */     return str;
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
/* 3682 */     logMessage(getDescription() + " ***** " + paramString);
/* 3683 */     addDebug("deactivateFlagValue entered for " + paramString);
/* 3684 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 3687 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString);
/* 3688 */     if (eANMetaAttribute == null) {
/* 3689 */       addDebug("deactivateFlagValue: " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/* 3691 */       logMessage(getDescription() + " ***** " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 3695 */     String str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/* 3696 */     if (str != null) {
/*      */       try {
/* 3698 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/* 3699 */         Vector<ReturnEntityKey> vector = new Vector();
/* 3700 */         Vector<MultipleFlag> vector1 = new Vector();
/* 3701 */         EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 3702 */         if (eANAttribute != null) {
/* 3703 */           String str1 = eANAttribute.getEffFrom();
/* 3704 */           ControlBlock controlBlock = new ControlBlock(str1, str1, str1, str1, this.m_prof.getOPWGID());
/* 3705 */           StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/* 3706 */           while (stringTokenizer.hasMoreTokens()) {
/* 3707 */             String str2 = stringTokenizer.nextToken();
/*      */             
/* 3709 */             MultipleFlag multipleFlag = new MultipleFlag(this.m_prof.getEnterprise(), entityItem.getEntityType(), entityItem.getEntityID(), paramString, str2, 1, controlBlock);
/* 3710 */             vector1.addElement(multipleFlag);
/*      */           } 
/* 3712 */           returnEntityKey.m_vctAttributes = vector1;
/* 3713 */           vector.addElement(returnEntityKey);
/* 3714 */           this.m_db.update(this.m_prof, vector, false, false);
/* 3715 */           addDebug(entityItem.getKey() + " had " + paramString + " deactivated");
/*      */         } 
/*      */       } finally {
/*      */         
/* 3719 */         this.m_db.commit();
/* 3720 */         this.m_db.freeStatement();
/* 3721 */         this.m_db.isPending("finally after deactivate value");
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
/* 3741 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 3742 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/* 3743 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 3746 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 3747 */     if (eANMetaAttribute == null) {
/* 3748 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/* 3749 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + entityItem
/* 3750 */           .getEntityType() + ", nothing to do");
/*      */       return;
/*      */     } 
/* 3753 */     if (paramString2 != null)
/*      */     {
/* 3755 */       if (paramString2.equals(getAttributeFlagEnabledValue(entityItem, paramString1))) {
/* 3756 */         addDebug("setFlagValue " + entityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/* 3761 */           if (this.m_cbOn == null) {
/* 3762 */             setControlBlock();
/*      */           }
/* 3764 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*      */           
/* 3766 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */           
/* 3768 */           Vector<SingleFlag> vector = new Vector();
/* 3769 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 3770 */           vector.addElement(singleFlag);
/* 3771 */           returnEntityKey.m_vctAttributes = vector;
/* 3772 */           vector1.addElement(returnEntityKey);
/*      */           
/* 3774 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 3775 */           addDebug(entityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*      */         } finally {
/*      */           
/* 3778 */           this.m_db.commit();
/* 3779 */           this.m_db.freeStatement();
/* 3780 */           this.m_db.isPending("finally after update in setflag value");
/*      */         } 
/*      */       }  } 
/*      */   }
/*      */   
/*      */   private String sysFeedResendStatus(String paramString1, String paramString2, String paramString3) {
/* 3786 */     return ABRServerProperties.getValue(paramString1, paramString2, paramString3);
/*      */   }
/*      */   
/*      */   protected String getQueuedValue(String paramString) {
/* 3790 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 3791 */     if (str == null) {
/* 3792 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 3793 */       return "0020";
/*      */     } 
/* 3795 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 3796 */     return ABRServerProperties.getABRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getRFRQueuedValue(String paramString) {
/* 3801 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 3802 */     if (str == null) {
/* 3803 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 3804 */       return "0020";
/*      */     } 
/* 3806 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 3807 */     return ABRServerProperties.getABRRFRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */   
/*      */   protected boolean fullMode() {
/* 3811 */     String str = ABRServerProperties.getValue("ADSABRSTATUS", "_FullMode");
/* 3812 */     if (str.equals("true") || str.equals("True") || str.equals("TRUE")) {
/* 3813 */       addDebug("ADSABRSTATUS is running in FullMode!");
/* 3814 */       return true;
/*      */     } 
/* 3816 */     addDebug("ADSABRSTATUS is not running in FullMode!");
/* 3817 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isPeriodicABR() {
/* 3821 */     return this.isPeriodicABR;
/*      */   }
/*      */   protected boolean isXMLCACHE() {
/* 3824 */     return this.isXMLCACHE;
/*      */   }
/*      */   protected boolean isSystemResendRFR() {
/* 3827 */     return this.isSystemResendRFR;
/*      */   }
/*      */   
/*      */   protected boolean isSystemResendCache() {
/* 3831 */     return this.isSystemResendCache;
/*      */   }
/*      */   
/*      */   protected boolean isSystemResendCacheExist() {
/* 3835 */     return this.isSystemResendCacheExist;
/*      */   }
/*      */   
/*      */   protected String getSystemResendCacheXml() {
/* 3839 */     return this.SystemResendCacheXml;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeItem(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 3848 */     addDebug("removeItem: " + paramEntityItem.getKey() + " ei.getUpLinkCount() " + paramEntityItem.getUpLinkCount() + " ei.getDownLinkCount() " + paramEntityItem
/* 3849 */         .getDownLinkCount());
/*      */     
/* 3851 */     Vector<EntityItem> vector = new Vector();
/*      */ 
/*      */     
/* 3854 */     EntityItem[] arrayOfEntityItem1 = new EntityItem[paramEntityItem.getUpLinkCount()];
/* 3855 */     paramEntityItem.getUpLink().copyInto((Object[])arrayOfEntityItem1);
/*      */     
/* 3857 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[paramEntityItem.getDownLinkCount()];
/* 3858 */     paramEntityItem.getDownLink().copyInto((Object[])arrayOfEntityItem2);
/*      */     
/*      */     byte b;
/*      */     
/* 3862 */     for (b = 0; b < arrayOfEntityItem1.length; b++) {
/*      */       
/* 3864 */       EntityItem entityItem = arrayOfEntityItem1[b];
/* 3865 */       addDebug("uplink: " + entityItem.getKey());
/*      */ 
/*      */       
/* 3868 */       paramEntityItem.removeUpLink((EANEntity)entityItem);
/*      */ 
/*      */ 
/*      */       
/* 3872 */       if (paramEntityGroup.containsEntityItem(entityItem.getEntityType(), entityItem.getEntityID())) {
/* 3873 */         arrayOfEntityItem1[b] = null;
/* 3874 */         addDebug("uplink: upRelator is root " + entityItem.getKey());
/*      */       
/*      */       }
/* 3877 */       else if (entityItem.getDownLinkCount() > 0) {
/* 3878 */         arrayOfEntityItem1[b] = null;
/* 3879 */         addDebug("uplink: upRelator " + entityItem.getKey() + " has more downlinks ");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 3884 */         EntityItem[] arrayOfEntityItem = new EntityItem[entityItem.getUpLinkCount()];
/* 3885 */         entityItem.getUpLink().copyInto((Object[])arrayOfEntityItem);
/* 3886 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 3887 */           EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 3888 */           addDebug("uplink: parentitem  " + entityItem1.getKey());
/*      */ 
/*      */           
/* 3891 */           entityItem.removeUpLink((EANEntity)entityItem1);
/*      */           
/* 3893 */           if (!paramEntityGroup.containsEntityItem(entityItem1.getEntityType(), entityItem1.getEntityID())) {
/* 3894 */             if (!vector.contains(entityItem1)) {
/* 3895 */               vector.add(entityItem1);
/*      */             }
/*      */           } else {
/* 3898 */             addDebug("uplink: parentitem is root " + entityItem1.getKey());
/*      */           } 
/*      */           
/* 3901 */           arrayOfEntityItem[b1] = null;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3905 */     for (b = 0; b < arrayOfEntityItem2.length; b++) {
/*      */       
/* 3907 */       EntityItem entityItem = arrayOfEntityItem2[b];
/* 3908 */       addDebug("Downlink: " + entityItem.getKey());
/*      */       
/* 3910 */       paramEntityItem.removeDownLink((EANEntity)entityItem);
/*      */ 
/*      */ 
/*      */       
/* 3914 */       if (paramEntityGroup.containsEntityItem(entityItem.getEntityType(), entityItem.getEntityID())) {
/* 3915 */         arrayOfEntityItem2[b] = null;
/* 3916 */         addDebug("Downlink: downRelator is root " + entityItem.getKey());
/*      */       
/*      */       }
/* 3919 */       else if (entityItem.getUpLinkCount() > 0) {
/* 3920 */         arrayOfEntityItem2[b] = null;
/* 3921 */         addDebug("Downlink: downRelator " + entityItem.getKey() + " has more uplinks ");
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 3926 */         EntityItem[] arrayOfEntityItem = new EntityItem[entityItem.getDownLinkCount()];
/* 3927 */         entityItem.getDownLink().copyInto((Object[])arrayOfEntityItem);
/* 3928 */         for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/* 3929 */           EntityItem entityItem1 = arrayOfEntityItem[b1];
/* 3930 */           addDebug("Downlink: childitem " + entityItem1.getKey());
/*      */           
/* 3932 */           entityItem.removeDownLink((EANEntity)entityItem1);
/*      */ 
/*      */           
/* 3935 */           if (!paramEntityGroup.containsEntityItem(entityItem1.getEntityType(), entityItem1.getEntityID())) {
/* 3936 */             if (!vector.contains(entityItem1)) {
/* 3937 */               vector.add(entityItem1);
/*      */             }
/*      */           } else {
/* 3940 */             addDebug("Downlink: childitem is root " + entityItem1.getKey());
/*      */           } 
/* 3942 */           arrayOfEntityItem[b1] = null;
/*      */         } 
/*      */       } 
/*      */     } 
/* 3946 */     removeFilteredEntityItem(paramEntityItem);
/*      */     
/* 3948 */     for (b = 0; b < arrayOfEntityItem1.length; b++) {
/*      */       
/* 3950 */       removeFilteredEntityItem(arrayOfEntityItem1[b]);
/* 3951 */       arrayOfEntityItem1[b] = null;
/*      */     } 
/*      */     
/* 3954 */     for (b = 0; b < arrayOfEntityItem2.length; b++) {
/*      */       
/* 3956 */       removeFilteredEntityItem(arrayOfEntityItem2[b]);
/* 3957 */       arrayOfEntityItem2[b] = null;
/*      */     } 
/*      */ 
/*      */     
/* 3961 */     for (b = 0; b < vector.size(); b++) {
/* 3962 */       EntityItem entityItem = vector.elementAt(b);
/*      */       
/* 3964 */       if (!entityItem.hasDownLinks() && !entityItem.hasUpLinks()) {
/* 3965 */         removeFilteredEntityItem(entityItem);
/*      */       }
/*      */     } 
/* 3968 */     vector.clear();
/* 3969 */     vector = null;
/* 3970 */     arrayOfEntityItem1 = null;
/* 3971 */     arrayOfEntityItem2 = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeFilteredEntityItem(EntityItem paramEntityItem) {
/* 3979 */     if (paramEntityItem != null) {
/* 3980 */       addDebug("removeFilteredEntityItem " + paramEntityItem.getKey());
/* 3981 */       EntityGroup entityGroup = paramEntityItem.getEntityGroup();
/*      */       
/* 3983 */       paramEntityItem.setParent(null);
/*      */       
/* 3985 */       for (int i = paramEntityItem.getAttributeCount() - 1; i >= 0; i--) {
/* 3986 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(i);
/* 3987 */         paramEntityItem.removeAttribute(eANAttribute);
/*      */       } 
/*      */       
/* 3990 */       entityGroup.removeEntityItem(paramEntityItem);
/*      */     } 
/*      */   }
/*      */   private HashSet getCountry(EntityItem paramEntityItem, String paramString) {
/* 3994 */     HashSet<String> hashSet = new HashSet();
/*      */     
/* 3996 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/* 3997 */     addDebug("ADSABRSTATUS.getCountry for entity " + paramEntityItem.getKey() + " fAtt " + 
/* 3998 */         PokUtils.getAttributeFlagValue(paramEntityItem, paramString) + NEWLINE);
/* 3999 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 4001 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 4002 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 4004 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 4005 */           hashSet.add(arrayOfMetaFlag[b].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/* 4009 */     return hashSet;
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
/* 4023 */     String str1 = "yyyy-MM-dd-HH.mm.ss";
/* 4024 */     String str2 = "&nbsp;";
/* 4025 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str1);
/* 4026 */     Calendar calendar = Calendar.getInstance();
/* 4027 */     if (!"&nbsp;".equals(paramString)) {
/*      */       try {
/* 4029 */         calendar.setTime(simpleDateFormat.parse(paramString));
/* 4030 */         calendar.add(13, paramInt);
/* 4031 */         str2 = simpleDateFormat.format(calendar.getTime()) + paramString.substring(19);
/* 4032 */       } catch (ParseException parseException) {
/* 4033 */         addDebug("failed add second to T1 or T2. Second is: " + paramInt);
/* 4034 */       } catch (Exception exception) {
/* 4035 */         addDebug("failed add second to T1 or T2. Second is: " + paramInt);
/*      */       } 
/*      */     }
/*      */     
/* 4039 */     return str2;
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
/* 4061 */     String str1 = this.m_strEpoch;
/* 4062 */     if (this.isXMLIDLABR) {
/* 4063 */       str1 = this.t2DTS;
/*      */     } else {
/*      */       
/* 4066 */       str1 = paramString2;
/*      */     } 
/*      */     
/* 4069 */     addDebug("get XMLCACHEDTS: " + str1);
/* 4070 */     EntityItem entityItem = paramEntityList.getParentEntityGroup().getEntityItem(0);
/* 4071 */     String str2 = entityItem.getEntityType();
/* 4072 */     int i = entityItem.getEntityID();
/* 4073 */     PreparedStatement preparedStatement = null;
/* 4074 */     Statement statement = null;
/*      */     
/* 4076 */     ResultSet resultSet = null;
/* 4077 */     boolean bool1 = false;
/* 4078 */     boolean bool2 = false;
/* 4079 */     addDebug("rootType :" + str2);
/* 4080 */     addDebug("entityID :" + i);
/*      */     
/* 4082 */     StringBuffer stringBuffer = new StringBuffer();
/* 4083 */     stringBuffer.append("SELECT xmlcachedts FROM cache.xmlidlcache");
/* 4084 */     stringBuffer.append(" WHERE xmlentitytype = '" + str2 + "'");
/* 4085 */     stringBuffer.append(" AND xmlentityID = " + i);
/* 4086 */     stringBuffer.append(" AND xmlcachevalidto > current timestamp");
/*      */     
/*      */     try {
/*      */       try {
/* 4090 */         statement = this.m_db.getODSConnection().createStatement();
/* 4091 */         resultSet = statement.executeQuery(stringBuffer.toString());
/*      */         
/* 4093 */         while (resultSet.next()) {
/* 4094 */           bool1 = true;
/* 4095 */           String str = resultSet.getString(1);
/* 4096 */           if (str1.compareTo(str) > 0) {
/* 4097 */             bool2 = true;
/*      */           }
/*      */         } 
/* 4100 */         addDebug("get XMLIDLCache where" + entityItem.getKey() + " is exist: " + bool1 + " is need update: " + bool2);
/* 4101 */       } catch (MiddlewareException middlewareException) {
/* 4102 */         addDebug("MiddlewareException on ? " + middlewareException);
/* 4103 */         middlewareException.printStackTrace();
/* 4104 */         throw middlewareException;
/* 4105 */       } catch (SQLException sQLException) {
/* 4106 */         addDebug("RuntimeException on ? " + sQLException);
/* 4107 */         sQLException.printStackTrace();
/* 4108 */         throw sQLException;
/*      */       } 
/* 4110 */       if (bool1 && bool2) {
/* 4111 */         stringBuffer = new StringBuffer();
/* 4112 */         stringBuffer.append("UPDATE cache.xmlidlcache");
/* 4113 */         stringBuffer.append(" SET xmlcachevalidto = ?");
/* 4114 */         stringBuffer.append(" WHERE xmlentitytype = '" + str2 + "'");
/* 4115 */         stringBuffer.append(" AND xmlentityID = " + i);
/* 4116 */         stringBuffer.append(" AND xmlcachevalidto > current timestamp");
/*      */         try {
/* 4118 */           preparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/* 4119 */           preparedStatement.setString(1, str1);
/* 4120 */           preparedStatement.execute();
/* 4121 */           if (this.m_db.getODSConnection() != null) {
/* 4122 */             this.m_db.getODSConnection().commit();
/*      */           }
/* 4124 */         } catch (MiddlewareException middlewareException) {
/* 4125 */           addDebug("MiddlewareException on ? " + middlewareException);
/* 4126 */           middlewareException.printStackTrace();
/* 4127 */           throw middlewareException;
/* 4128 */         } catch (SQLException sQLException) {
/* 4129 */           addDebug("SQLException on ? " + sQLException);
/* 4130 */           sQLException.printStackTrace();
/* 4131 */           throw sQLException;
/*      */         } finally {
/* 4133 */           if (preparedStatement != null) {
/*      */             try {
/* 4135 */               preparedStatement.close();
/* 4136 */             } catch (SQLException sQLException) {
/* 4137 */               sQLException.printStackTrace();
/*      */             } 
/*      */           }
/*      */         } 
/* 4141 */         insertIntoCache(entityItem, paramString1, str1, str2, i, preparedStatement);
/* 4142 */       } else if (!bool1) {
/*      */ 
/*      */         
/* 4145 */         insertIntoCache(entityItem, paramString1, str1, str2, i, preparedStatement);
/*      */       } 
/*      */     } finally {
/*      */       try {
/* 4149 */         this.m_db.commit();
/* 4150 */         if (statement != null)
/* 4151 */           statement.close(); 
/* 4152 */       } catch (SQLException sQLException) {
/* 4153 */         sQLException.printStackTrace();
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
/* 4173 */     String[] arrayOfString = (String[])FILTER_TBL.get(paramString3);
/*      */     
/* 4175 */     String str1 = "";
/* 4176 */     String str2 = "";
/* 4177 */     String str3 = null;
/* 4178 */     String str4 = "";
/* 4179 */     String str5 = "";
/* 4180 */     long l1 = System.currentTimeMillis();
/* 4181 */     long l2 = 0L;
/*      */     
/* 4183 */     if (arrayOfString != null) {
/* 4184 */       String str6 = arrayOfString[0];
/*      */       
/* 4186 */       String str7 = arrayOfString[1];
/*      */       
/* 4188 */       String str8 = arrayOfString[2];
/* 4189 */       if (!"".equals(str6)) {
/* 4190 */         if (!"LSEO".equals(paramEntityItem.getEntityType())) {
/* 4191 */           str1 = PokUtils.getAttributeFlagValue(paramEntityItem, str6);
/* 4192 */           if (str1 == null) {
/* 4193 */             str1 = "";
/*      */ 
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 4203 */           if (paramEntityItem != null) {
/* 4204 */             Vector<EntityItem> vector1 = new Vector();
/* 4205 */             Vector<EntityItem> vector2 = PokUtils.getAllLinkedEntities(paramEntityItem, "WWSEOLSEO", "WWSEO");
/* 4206 */             for (byte b = 0; b < vector2.size(); b++) {
/* 4207 */               EntityItem entityItem = vector2.elementAt(b);
/* 4208 */               vector1 = PokUtils.getAllLinkedEntities(entityItem, "MODELWWSEO", "MODEL");
/* 4209 */               if (vector1.size() > 0)
/*      */                 break; 
/*      */             } 
/* 4212 */             if (vector1.size() > 0) {
/* 4213 */               EntityItem entityItem = vector1.elementAt(0);
/* 4214 */               str1 = PokUtils.getAttributeFlagValue(entityItem, str6);
/* 4215 */               if (str1 == null) {
/* 4216 */                 str1 = "";
/*      */               }
/*      */             } 
/*      */           } 
/* 4220 */           l2 = System.currentTimeMillis();
/* 4221 */           addDebugComment(3, "Time for get COFCAT from ListT2: " + Stopwatch.format(l2 - l1));
/*      */         } 
/*      */       }
/* 4224 */       if (!"".equals(str7)) {
/* 4225 */         str2 = PokUtils.getAttributeFlagValue(paramEntityItem, str7);
/* 4226 */         if (str2 == null)
/* 4227 */           str2 = ""; 
/*      */       } 
/* 4229 */       if (!"".equals(str8)) {
/*      */         
/* 4231 */         if ("SWPRODSTRUCT".equals(paramEntityItem.getEntityType())) {
/* 4232 */           l1 = System.currentTimeMillis();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 4237 */           if (paramEntityItem.hasDownLinks()) {
/* 4238 */             for (byte b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 4239 */               EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 4240 */               if (entityItem.getEntityType().equals("MODEL")) {
/* 4241 */                 str3 = PokUtils.getAttributeValue(entityItem, str8, ", ", null, false);
/* 4242 */                 addDebugComment(3, "Get withdrweffctvdate through VE ADSSWPRODSTRUCT, date is :" + str3);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } else {
/* 4247 */             addDebugComment(3, "Can not get down link through VE ADSSWPRODSTRUCT");
/*      */           } 
/* 4249 */           addDebugComment(3, "Time for get withdrweffectvdate from VE ADSSWPRODSTRUCT: " + Stopwatch.format(System.currentTimeMillis() - l1));
/* 4250 */         } else if ("REFOFERFEAT".equals(paramEntityItem.getEntityType())) {
/*      */           
/* 4252 */           String str = null;
/* 4253 */           if (paramEntityItem.hasUpLinks()) {
/* 4254 */             for (byte b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 4255 */               EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 4256 */               if (entityItem != null && entityItem.getEntityType().equals("REFOFERREFOFERFEAT")) {
/* 4257 */                 for (byte b1 = 0; b1 < entityItem.getUpLinkCount(); b1++) {
/* 4258 */                   EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(b1);
/* 4259 */                   if (entityItem1 != null && "REFOFER".equals(entityItem1.getEntityType())) {
/* 4260 */                     String str9 = PokUtils.getAttributeValue(entityItem1, str8, ", ", null, false);
/* 4261 */                     addDebugComment(3, "Get withdrweffctvdate through VE ADSREFOFERFEAT, date is :" + str9);
/* 4262 */                     if (str9 != null) {
/*      */                       
/* 4264 */                       if (str == null) {
/* 4265 */                         str = str9;
/*      */                       
/*      */                       }
/* 4268 */                       else if (str9.compareTo(str) > 0) {
/* 4269 */                         str = str9;
/*      */                       } 
/*      */                     } else {
/*      */                       
/* 4273 */                       str = this.m_strForever.substring(0, 10);
/*      */ 
/*      */                       
/*      */                       // Byte code: goto -> 677
/*      */                     } 
/*      */                   } 
/*      */                 } 
/*      */               }
/*      */             } 
/*      */           } else {
/* 4283 */             addDebugComment(3, "Can not get up link through VE ADSREFOFERFEAT");
/*      */           } 
/* 4285 */           str3 = str;
/*      */         } else {
/* 4287 */           str3 = PokUtils.getAttributeValue(paramEntityItem, str8, ", ", null, false);
/*      */         } 
/*      */         
/* 4290 */         if (str3 == null) {
/* 4291 */           str3 = this.m_strForever.substring(0, 10);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4297 */     str4 = PokUtils.getAttributeFlagValue(paramEntityItem, "PDHDOMAIN");
/* 4298 */     if (str4 == null) {
/* 4299 */       str4 = "";
/*      */     }
/* 4301 */     str5 = this.curStatusvalue;
/*      */     
/* 4303 */     if (str5 == null) {
/* 4304 */       str5 = "";
/*      */     }
/* 4306 */     StringBuffer stringBuffer = new StringBuffer();
/* 4307 */     stringBuffer.append("INSERT INTO cache.xmlidlcache (XMLENTITYTYPE, XMLENTITYID, XMLCACHEDTS, XMLCACHEVALIDTO, XMLOFFTYPE, FLFILSYSINDC, WTHDRWEFFCTVDATE, STATUS, XMLMESSAGE, PDHDOMAIN) ");
/* 4308 */     stringBuffer.append(" VALUES (?,?,?,?,?,?,?,?,?,? )");
/* 4309 */     addDebug("T2 :" + paramString2);
/* 4310 */     addDebug("offtype :" + str1);
/* 4311 */     addDebug("flfilsysindc :" + str2);
/* 4312 */     addDebug("withdrweffctvdate :" + str3);
/* 4313 */     addDebug("status :" + str5);
/* 4314 */     addDebug("pdhdomain :" + str4);
/*      */     
/*      */     try {
/* 4317 */       paramPreparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/* 4318 */       paramPreparedStatement.setString(1, paramString3);
/* 4319 */       paramPreparedStatement.setInt(2, paramInt);
/* 4320 */       paramPreparedStatement.setString(3, paramString2);
/* 4321 */       paramPreparedStatement.setString(4, "9999-12-31-00.00.00.000000");
/* 4322 */       paramPreparedStatement.setString(5, str1);
/* 4323 */       paramPreparedStatement.setString(6, str2);
/* 4324 */       paramPreparedStatement.setString(7, str3);
/* 4325 */       paramPreparedStatement.setString(8, str5);
/* 4326 */       paramPreparedStatement.setString(9, paramString1);
/* 4327 */       paramPreparedStatement.setString(10, str4);
/* 4328 */       paramPreparedStatement.execute();
/* 4329 */       if (this.m_db.getODSConnection() != null) {
/* 4330 */         this.m_db.getODSConnection().commit();
/*      */       }
/* 4332 */       addDebugComment(2, " record was inserted into table xmlidlcache.");
/* 4333 */     } catch (MiddlewareException middlewareException) {
/* 4334 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4335 */       middlewareException.printStackTrace();
/* 4336 */       throw middlewareException;
/* 4337 */     } catch (SQLException sQLException) {
/* 4338 */       addDebug("SQLException on ? " + sQLException);
/* 4339 */       sQLException.printStackTrace();
/* 4340 */       throw sQLException;
/*      */     } finally {
/* 4342 */       if (paramPreparedStatement != null) {
/*      */         try {
/* 4344 */           paramPreparedStatement.close();
/* 4345 */         } catch (SQLException sQLException) {
/* 4346 */           sQLException.printStackTrace();
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
/* 4363 */     long l = System.currentTimeMillis();
/* 4364 */     Iterator<String[]> iterator = paramArrayList.iterator();
/*      */     
/* 4366 */     PreparedStatement preparedStatement = null;
/* 4367 */     StringBuffer stringBuffer = new StringBuffer();
/* 4368 */     stringBuffer
/* 4369 */       .append("INSERT INTO cache.XMLMSGLOG (SETUPENTITYTYPE, SETUPENTITYID, SETUPDTS, SENDMSGDTS, MSGTYPE, MSGCOUNT, ENTITYTYPE, ENTITYID, DTSOFMSG, MSGSTATUS, REASON, MQPROPFILE) ");
/* 4370 */     stringBuffer.append(" VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
/*      */     
/*      */     try {
/* 4373 */       preparedStatement = this.m_db.getODSConnection().prepareStatement(new String(stringBuffer));
/* 4374 */       while (iterator.hasNext()) {
/* 4375 */         String[] arrayOfString = iterator.next();
/* 4376 */         boolean bool = (arrayOfString[0] == null) ? false : Integer.parseInt(arrayOfString[0]);
/* 4377 */         preparedStatement.setString(1, paramString1);
/* 4378 */         preparedStatement.setInt(2, paramInt);
/* 4379 */         preparedStatement.setString(3, paramString2);
/* 4380 */         preparedStatement.setString(4, arrayOfString[1]);
/* 4381 */         preparedStatement.setString(5, arrayOfString[2]);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 4387 */         if ("XMLCOMPATSETUP".equals(paramString1)) {
/* 4388 */           preparedStatement.setInt(6, ADSWWCOMPATXMLABR.WWCOMPAT_MESSAGE_COUNT);
/* 4389 */         } else if ("XMLPRODPRICESETUP".equals(paramString1)) {
/* 4390 */           preparedStatement.setInt(6, ADSPRICEABR.PRICE_MESSAGE_COUNT);
/*      */         } else {
/* 4392 */           preparedStatement.setInt(6, 1);
/*      */         } 
/* 4394 */         preparedStatement.setString(7, paramString3);
/* 4395 */         preparedStatement.setInt(8, bool);
/* 4396 */         preparedStatement.setString(9, arrayOfString[3]);
/* 4397 */         preparedStatement.setString(10, arrayOfString[4]);
/* 4398 */         preparedStatement.setString(11, arrayOfString[5]);
/* 4399 */         preparedStatement.setString(12, paramString4);
/* 4400 */         preparedStatement.addBatch();
/*      */       } 
/*      */       
/* 4403 */       preparedStatement.executeBatch();
/* 4404 */       if (this.m_db.getODSConnection() != null) {
/* 4405 */         this.m_db.getODSConnection().commit();
/*      */       }
/*      */       
/* 4408 */       addDebugComment(2, paramArrayList.size() + " records was inserted into table XMLMSGLOG. Total Time: " + 
/*      */           
/* 4410 */           Stopwatch.format(System.currentTimeMillis() - l));
/* 4411 */     } catch (MiddlewareException middlewareException) {
/* 4412 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4413 */       middlewareException.printStackTrace();
/* 4414 */       throw middlewareException;
/* 4415 */     } catch (SQLException sQLException) {
/* 4416 */       addDebug("SQLException on ? " + sQLException);
/* 4417 */       sQLException.printStackTrace();
/* 4418 */       throw sQLException;
/*      */     } finally {
/* 4420 */       if (preparedStatement != null) {
/*      */         try {
/* 4422 */           preparedStatement.close();
/* 4423 */         } catch (SQLException sQLException) {
/* 4424 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setExtxmlfeedVct(Vector paramVector) {
/* 4432 */     this.extxmlfeedVct = paramVector;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDescription(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 4441 */     String str = "";
/* 4442 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString1);
/* 4443 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 4445 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 4446 */       StringBuffer stringBuffer = new StringBuffer();
/* 4447 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 4449 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 4451 */           if (stringBuffer.length() > 0) {
/* 4452 */             stringBuffer.append(",");
/*      */           }
/* 4454 */           if (paramString2.equals("short")) {
/* 4455 */             stringBuffer.append(arrayOfMetaFlag[b].getShortDescription());
/* 4456 */           } else if (paramString2.equals("long")) {
/* 4457 */             stringBuffer.append(arrayOfMetaFlag[b].getLongDescription());
/* 4458 */           } else if (paramString2.equals("flag")) {
/* 4459 */             stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/*      */           } else {
/*      */             
/* 4462 */             stringBuffer.append(arrayOfMetaFlag[b].toString());
/*      */           } 
/*      */         } 
/*      */       } 
/* 4466 */       str = stringBuffer.toString();
/*      */     } 
/* 4468 */     return str;
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
/* 4485 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getNDNitem(EntityItem paramEntityItem, String paramString) {
/*      */     byte b;
/* 4495 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 4496 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 4497 */       if (entityItem.getEntityType().equals(paramString)) {
/* 4498 */         return entityItem;
/*      */       }
/*      */     } 
/* 4501 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 4502 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 4503 */       if (entityItem.getEntityType().equals(paramString)) {
/* 4504 */         return entityItem;
/*      */       }
/*      */     } 
/* 4507 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 4517 */     StringBuffer stringBuffer = new StringBuffer();
/* 4518 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem.getEntityType());
/*      */ 
/*      */     
/* 4521 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 4522 */     if (eANList == null) {
/* 4523 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 4524 */       eANList = entityGroup.getMetaAttribute();
/* 4525 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 4527 */     for (byte b = 0; b < eANList.size(); b++) {
/* 4528 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 4529 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 4530 */       if (b + 1 < eANList.size()) {
/* 4531 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/* 4534 */     if (nDN != null) {
/* 4535 */       EntityList entityList = null;
/* 4536 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 4537 */       EntityItem entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       
/* 4539 */       if (entityItem == null && 
/* 4540 */         paramEntityItem.getEntityType().endsWith("PRODSTRUCT")) {
/* 4541 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType() + " pulling small VE for this " + paramEntityItem
/* 4542 */             .getKey());
/*      */         
/* 4544 */         String str = "EXRPT3FM";
/* 4545 */         if (paramEntityItem.getEntityType().equals("SWPRODSTRUCT")) {
/* 4546 */           str = "EXRPT3SWFM";
/*      */         }
/*      */ 
/*      */         
/* 4550 */         entityList = this.m_db.getEntityList(paramEntityItem.getProfile(), new ExtractActionItem(null, this.m_db, paramEntityItem
/* 4551 */               .getProfile(), str), new EntityItem[] { new EntityItem(null, paramEntityItem
/* 4552 */                 .getProfile(), paramEntityItem.getEntityType(), paramEntityItem
/* 4553 */                 .getEntityID()) });
/*      */         
/* 4555 */         paramEntityItem = entityList.getParentEntityGroup().getEntityItem(0);
/* 4556 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       } 
/*      */ 
/*      */       
/* 4560 */       if (entityItem != null) {
/* 4561 */         stringBuffer1.append("(" + nDN.getTag());
/* 4562 */         for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 4563 */           String str = nDN.getAttr().elementAt(b1).toString();
/* 4564 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 4565 */           if (b1 + 1 < nDN.getAttr().size()) {
/* 4566 */             stringBuffer1.append(" ");
/*      */           }
/*      */         } 
/* 4569 */         stringBuffer1.append(") ");
/*      */       } else {
/* 4571 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType());
/*      */       } 
/* 4573 */       nDN = nDN.getNext();
/* 4574 */       if (nDN != null) {
/* 4575 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/* 4576 */         if (entityItem != null) {
/* 4577 */           stringBuffer1.append("(" + nDN.getTag());
/* 4578 */           for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 4579 */             String str = nDN.getAttr().elementAt(b1).toString();
/* 4580 */             stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 4581 */             if (b1 + 1 < nDN.getAttr().size()) {
/* 4582 */               stringBuffer1.append(" ");
/*      */             }
/*      */           } 
/* 4585 */           stringBuffer1.append(") ");
/*      */         } else {
/* 4587 */           addDebug("NO entity found for next ndn.getEntityType(): " + nDN.getEntityType());
/*      */         } 
/*      */       } 
/* 4590 */       stringBuffer.insert(0, stringBuffer1.toString());
/*      */       
/* 4592 */       if (entityList != null) {
/* 4593 */         entityList.dereference();
/*      */       }
/*      */     } 
/*      */     
/* 4597 */     return stringBuffer.toString().trim();
/*      */   }
/*      */   
/*      */   private static class NDN { private String etype;
/*      */     private String tag;
/*      */     private NDN next;
/* 4603 */     private Vector attrVct = new Vector();
/*      */     NDN(String param1String1, String param1String2) {
/* 4605 */       this.etype = param1String1;
/* 4606 */       this.tag = param1String2;
/*      */     }
/* 4608 */     String getTag() { return this.tag; }
/* 4609 */     String getEntityType() { return this.etype; } Vector getAttr() {
/* 4610 */       return this.attrVct;
/*      */     } void addAttr(String param1String) {
/* 4612 */       this.attrVct.addElement(param1String);
/*      */     }
/* 4614 */     void setNext(NDN param1NDN) { this.next = param1NDN; } NDN getNext() {
/* 4615 */       return this.next;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getEntityLastValfrom(EntityItem paramEntityItem) {
/* 4625 */     String str1 = null;
/* 4626 */     PreparedStatement preparedStatement = null;
/* 4627 */     String str2 = "SELECT VALFROM FROM OPICM.ENTITY WHERE ENTITYTYPE = ? AND ENTITYID = ? AND VALTO > CURRENT TIMESTAMP AND EFFTO > CURRENT TIMESTAMP with ur";
/*      */     try {
/* 4629 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(str2);
/* 4630 */       preparedStatement.setString(1, paramEntityItem.getEntityType());
/* 4631 */       preparedStatement.setInt(2, paramEntityItem.getEntityID());
/* 4632 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 4633 */       while (resultSet.next()) {
/* 4634 */         str1 = resultSet.getString(1);
/*      */       }
/*      */     }
/* 4637 */     catch (MiddlewareException middlewareException) {
/* 4638 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4639 */       middlewareException.printStackTrace();
/* 4640 */     } catch (SQLException sQLException) {
/* 4641 */       addDebug("SQLException on ? " + sQLException);
/* 4642 */       sQLException.printStackTrace();
/*      */     } finally {
/* 4644 */       if (preparedStatement != null) {
/*      */         try {
/* 4646 */           preparedStatement.close();
/* 4647 */         } catch (SQLException sQLException) {
/* 4648 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 4652 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getEntityT2DTS(EntityItem paramEntityItem) {
/* 4662 */     getEntityType();
/* 4663 */     String str1 = null;
/* 4664 */     PreparedStatement preparedStatement = null;
/* 4665 */     String str2 = null;
/* 4666 */     if (getEntityType().equals("XMLPRODPRICESETUP")) {
/* 4667 */       str2 = "ADSPPABRSTATUS";
/*      */     } else {
/* 4669 */       str2 = "ADSABRSTATUS";
/*      */     } 
/*      */ 
/*      */     
/* 4673 */     String str3 = "SELECT MAX(VALFROM) FROM OPICM.FLAG WHERE ENTITYTYPE = ? AND ENTITYID = ? and ATTRIBUTECODE= ? AND ATTRIBUTEVALUE='0050' AND EFFTO > CURRENT TIMESTAMP with ur";
/*      */     try {
/* 4675 */       preparedStatement = this.m_db.getPDHConnection().prepareStatement(str3);
/* 4676 */       preparedStatement.setString(1, paramEntityItem.getEntityType());
/* 4677 */       preparedStatement.setInt(2, paramEntityItem.getEntityID());
/* 4678 */       preparedStatement.setString(3, str2);
/* 4679 */       ResultSet resultSet = preparedStatement.executeQuery();
/* 4680 */       while (resultSet.next()) {
/* 4681 */         str1 = resultSet.getString(1);
/* 4682 */         addDebug("getEntity 0050 Valfrom " + str1);
/*      */       } 
/* 4684 */     } catch (MiddlewareException middlewareException) {
/* 4685 */       addDebug("MiddlewareException on ? " + middlewareException);
/* 4686 */       middlewareException.printStackTrace();
/* 4687 */     } catch (SQLException sQLException) {
/* 4688 */       addDebug("SQLException on ? " + sQLException);
/* 4689 */       sQLException.printStackTrace();
/*      */     } finally {
/* 4691 */       if (preparedStatement != null) {
/*      */         try {
/* 4693 */           preparedStatement.close();
/* 4694 */         } catch (SQLException sQLException) {
/* 4695 */           sQLException.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/* 4699 */     return str1;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\ADSABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */