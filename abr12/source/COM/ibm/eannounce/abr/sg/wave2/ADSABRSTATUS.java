/*      */ package COM.ibm.eannounce.abr.sg.wave2;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.XMLElem;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANEntity;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MQUsage;
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
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.StringCharacterIterator;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  104 */   private StringBuffer rptSb = new StringBuffer();
/*  105 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  106 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   protected static final String ADSMQSERIES = "ADSMQSERIES";
/*      */   
/*      */   protected static final String SYSFEEDRESEND_YES = "Yes";
/*      */   protected static final String SYSFEEDRESEND_NO = "No";
/*  112 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  113 */   private PrintWriter dbgPw = null;
/*  114 */   private PrintWriter userxmlPw = null;
/*  115 */   private String dbgfn = null;
/*  116 */   private String userxmlfn = null;
/*  117 */   private int userxmlLen = 0;
/*  118 */   private int dbgLen = 0;
/*      */   
/*      */   private static final int MAXFILE_SIZE = 5000000;
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
/*      */   
/*      */   protected static final String CHEAT = "@@";
/*      */   
/*  136 */   protected static final String[][] VE_Filter_Array = new String[][] { { "ADSLSEO", "AVAIL", "RFR Final" }, { "ADSLSEO", "ANNOUNCEMENT", "RFR Final" }, { "ADSMODEL", "AVAIL", "RFR Final" }, { "ADSMODEL", "ANNOUNCEMENT", "RFR Final" }, { "ADSMODEL", "IMG", "Final" }, { "ADSMODEL", "MM", "Final" }, { "ADSSVCMOD", "AVAIL", "RFR Final" }, { "ADSSVCMOD", "ANNOUNCEMENT", "RFR Final" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  148 */   private ResourceBundle rsBundle = null;
/*  149 */   private String priorStatus = "&nbsp;";
/*  150 */   private String curStatus = "&nbsp;";
/*      */   
/*      */   private boolean isPeriodicABR = false;
/*      */   private boolean isSystemResend = false;
/*      */   private boolean RFRPassedFinal = false;
/*  155 */   private String actionTaken = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Hashtable READ_LANGS_TBL;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  169 */   private static final Hashtable ABR_TBL = new Hashtable<>(); protected static final Hashtable ADSTYPES_TBL; protected static final Hashtable ITEM_STATUS_ATTR_TBL; static {
/*  170 */     ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.wave2.ADSMODELABR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  179 */     ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.wave2.ADSLSEOABR");
/*  180 */     ABR_TBL.put("SVCMOD", "COM.ibm.eannounce.abr.sg.wave2.ADSSVCMODABR");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  185 */     ABR_TBL.put("GENAREA", "COM.ibm.eannounce.abr.sg.wave2.ADSGENAREAABR");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  197 */     READ_LANGS_TBL = new Hashtable<>();
/*      */     
/*  199 */     READ_LANGS_TBL.put("" + Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
/*  200 */     READ_LANGS_TBL.put("" + Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
/*  201 */     READ_LANGS_TBL.put("" + Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
/*  202 */     READ_LANGS_TBL.put("" + Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
/*  203 */     READ_LANGS_TBL.put("" + Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
/*  204 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
/*  205 */     READ_LANGS_TBL.put("" + Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
/*  206 */     READ_LANGS_TBL.put("" + Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
/*  207 */     READ_LANGS_TBL.put("" + Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
/*  208 */     READ_LANGS_TBL.put("" + Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
/*  209 */     READ_LANGS_TBL.put("" + Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
/*  210 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
/*  211 */     READ_LANGS_TBL.put("" + Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);
/*      */     
/*  213 */     ADSTYPES_TBL = new Hashtable<>();
/*  214 */     ADSTYPES_TBL.put("10", "CATNAV");
/*  215 */     ADSTYPES_TBL.put("100", "SVCPRODSTRUCT");
/*  216 */     ADSTYPES_TBL.put("110", "SWFEATURE");
/*  217 */     ADSTYPES_TBL.put("120", "SWFPRODSTRUCT");
/*  218 */     ADSTYPES_TBL.put("130", "MODELCG");
/*  219 */     ADSTYPES_TBL.put("140", "LSEO");
/*  220 */     ADSTYPES_TBL.put("150", "SVCMOD");
/*  221 */     ADSTYPES_TBL.put("20", "GENAREA");
/*  222 */     ADSTYPES_TBL.put("30", "FEATURE");
/*  223 */     ADSTYPES_TBL.put("40", "FCTRANSACTION");
/*  224 */     ADSTYPES_TBL.put("50", "XLATE");
/*  225 */     ADSTYPES_TBL.put("60", "MODEL");
/*  226 */     ADSTYPES_TBL.put("70", "MODELCONVERT");
/*  227 */     ADSTYPES_TBL.put("80", "PRODSTRUCT");
/*  228 */     ADSTYPES_TBL.put("90", "SVCFEATURE");
/*      */ 
/*      */ 
/*      */     
/*  232 */     ITEM_STATUS_ATTR_TBL = new Hashtable<>();
/*  233 */     ITEM_STATUS_ATTR_TBL.put("AVAIL", "STATUS");
/*  234 */     ITEM_STATUS_ATTR_TBL.put("ANNOUNCEMENT", "ANNSTATUS");
/*  235 */     ITEM_STATUS_ATTR_TBL.put("IMG", "STATUS");
/*  236 */     ITEM_STATUS_ATTR_TBL.put("MM", "MMSTATUS");
/*      */ 
/*      */     
/*  239 */     ABR_ATTR_TBL = new Hashtable<>();
/*  240 */     ABR_ATTR_TBL.put("ANNOUNCEMENT", "ANNABRSTATUS");
/*  241 */     ABR_ATTR_TBL.put("AVAIL", "AVAILABRSTATUS");
/*  242 */     ABR_ATTR_TBL.put("FEATURE", "FCABRSTATUS");
/*  243 */     ABR_ATTR_TBL.put("FCTRANSACTION", "FCTRANSABRSTATUS");
/*  244 */     ABR_ATTR_TBL.put("LSEO", "LSEOABRSTATUS");
/*  245 */     ABR_ATTR_TBL.put("LSEOBUNDLE", "LSEOBDLABRSTATUS");
/*  246 */     ABR_ATTR_TBL.put("MODELCONVERT", "MDLCNTABRSTATTUS");
/*  247 */     ABR_ATTR_TBL.put("SWFEATURE", "SWFCABRSTATUS");
/*  248 */     ABR_ATTR_TBL.put("IPSCFEAT", "IPSCFEATABRSTATUS");
/*  249 */     ABR_ATTR_TBL.put("IPSCSTRUC", "IPSCSTRUCABRSTATUS");
/*  250 */     ABR_ATTR_TBL.put("SVCMOD", "SVCMODABRSTATUS");
/*  251 */     ABR_ATTR_TBL.put("WWSEO", "WWSEOABRSTATUS");
/*  252 */     ABR_ATTR_TBL.put("MODEL", "MODELABRSTATUS");
/*  253 */     ABR_ATTR_TBL.put("PRODSTRUCT", "PRODSTRUCTABRSTATUS");
/*  254 */     ABR_ATTR_TBL.put("SWPRODSTRUCT", "SWPRODSTRUCTABRSTATUS");
/*      */   }
/*      */   private static final Hashtable ABR_ATTR_TBL; protected static final String SYSFeedResendValue = "_SYSFeedResendValue";
/*  257 */   private Object[] args = (Object[])new String[10];
/*  258 */   private String abrversion = "";
/*  259 */   private String t2DTS = "&nbsp;";
/*  260 */   private String t1DTS = "&nbsp;";
/*  261 */   private StringBuffer userxmlSb = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  295 */     String str = (String)ABR_TBL.get(paramString);
/*  296 */     addDebug("creating instance of ADSABR  = '" + str + "' for " + paramString);
/*  297 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  306 */     String str1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  313 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  318 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  321 */       long l = System.currentTimeMillis();
/*      */       
/*  323 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  326 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  329 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  335 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  337 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*      */       try {
/*  341 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */         
/*  343 */         this.isPeriodicABR = getEntityType().equals("ADSXMLSETUP");
/*      */         
/*  345 */         String str6 = getEntityType();
/*      */         
/*  347 */         String str7 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*  348 */         String str8 = PokUtils.getAttributeFlagValue(entityItem, "ADSENTITY");
/*      */         
/*  350 */         if (this.isPeriodicABR) {
/*      */           
/*  352 */           if (str7 != null) {
/*  353 */             str6 = (String)ADSTYPES_TBL.get(str7);
/*      */           }
/*  355 */           if ("20".equals(str8)) {
/*  356 */             str6 = "DEL" + str6;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  362 */         String str9 = getSimpleABRName(str6);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  367 */         boolean bool = checkSVCMOD(entityItem);
/*  368 */         if (str9 != null && !bool) {
/*      */           
/*  370 */           boolean bool1 = true;
/*  371 */           XMLMQ xMLMQ = (XMLMQ)Class.forName(str9).newInstance();
/*      */           
/*  373 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/*      */           
/*  375 */           if (!this.isPeriodicABR) {
/*  376 */             String str10 = xMLMQ.getStatusAttr();
/*  377 */             String str11 = getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*  378 */             String str12 = getAttributeFlagEnabledValue(entityItem, str10);
/*  379 */             this.isSystemResend = "Yes".equals(str11);
/*  380 */             addDebug("execute: " + entityItem.getKey() + " " + str10 + ": " + 
/*  381 */                 PokUtils.getAttributeValue(entityItem, str10, ", ", "", false) + " [" + str12 + "] sysfeedFlag: " + str11);
/*      */ 
/*      */             
/*  384 */             if (this.isSystemResend) {
/*      */               
/*  386 */               String str13 = sysFeedResendStatus(this.m_abri.getABRCode(), "_SYSFeedResendValue", "Both");
/*      */               
/*  388 */               if ("0020".equals(str13)) {
/*  389 */                 if ("0020".equals(str12)) {
/*  390 */                   this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */                 } else {
/*  392 */                   addDebug(entityItem.getKey() + " is not Final");
/*      */                   
/*  394 */                   addError(this.rsBundle.getString("RESEND_ONLY_FINAL"));
/*      */                 } 
/*  396 */               } else if ("0040".equals(str13)) {
/*  397 */                 if ("0040".equals(str12)) {
/*      */                   
/*  399 */                   AttributeChangeHistoryGroup attributeChangeHistoryGroup3 = getADSABRSTATUSHistory();
/*      */                   
/*  401 */                   AttributeChangeHistoryGroup attributeChangeHistoryGroup4 = getSTATUSHistory(xMLMQ);
/*      */                   
/*  403 */                   if (existPassedFinal(attributeChangeHistoryGroup3, attributeChangeHistoryGroup4)) {
/*  404 */                     addDebug(entityItem.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
/*  405 */                     addError(this.rsBundle.getString("RESEND_R4R_PASSEDFINAL"));
/*      */                   } else {
/*  407 */                     this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */                   } 
/*      */                 } else {
/*  410 */                   addDebug(entityItem.getKey() + " is not RFR");
/*      */                   
/*  412 */                   addError(this.rsBundle.getString("RESEND_ONLY_R4REVIEW"));
/*      */                 }
/*      */               
/*      */               }
/*  416 */               else if (!"0020".equals(str12) && !"0040".equals(str12)) {
/*  417 */                 addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */                 
/*  419 */                 addError(this.rsBundle.getString("RESEND_NOT_R4RFINAL"));
/*      */               }
/*  421 */               else if ("0020".equals(str12)) {
/*  422 */                 this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */               } else {
/*      */                 
/*  425 */                 AttributeChangeHistoryGroup attributeChangeHistoryGroup3 = getADSABRSTATUSHistory();
/*      */                 
/*  427 */                 AttributeChangeHistoryGroup attributeChangeHistoryGroup4 = getSTATUSHistory(xMLMQ);
/*  428 */                 if (existPassedFinal(attributeChangeHistoryGroup3, attributeChangeHistoryGroup4)) {
/*  429 */                   addDebug(entityItem.getKey() + " was queued to resend data, however there is Passed Final before. so do not resend.");
/*  430 */                   addError(this.rsBundle.getString("RESEND_R4R_PASSEDFINAL"));
/*      */                 } else {
/*  432 */                   this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */                 } 
/*      */               } 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  439 */               this.curStatus = PokUtils.getAttributeValue(entityItem, str10, ", ", "", false);
/*  440 */               this.priorStatus = this.curStatus;
/*      */             }
/*  442 */             else if (!"0020".equals(str12) && !"0040".equals(str12)) {
/*  443 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */               
/*  445 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */             } else {
/*  447 */               bool1 = xMLMQ.createXML(entityItem);
/*  448 */               if (!bool1) {
/*  449 */                 addDebug(entityItem.getKey() + " will not have XML generated, createXML=false");
/*      */               }
/*      */             } 
/*      */           } else {
/*      */             
/*  454 */             addDebug("execute: periodic " + entityItem.getKey());
/*      */           } 
/*      */           
/*  457 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = getADSABRSTATUSHistory();
/*      */           
/*  459 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(xMLMQ);
/*      */           
/*  461 */           String str = getDTFS(entityItem, xMLMQ);
/*  462 */           setT2DTS(attributeChangeHistoryGroup1, str);
/*  463 */           setT1DTS(xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, str);
/*      */           
/*  465 */           if (getReturnCode() == 0 && bool1) {
/*      */ 
/*      */             
/*  468 */             Profile profile = switchRole(xMLMQ.getRoleCode());
/*  469 */             if (profile != null) {
/*  470 */               profile.setValOnEffOn(this.t2DTS, this.t2DTS);
/*  471 */               profile.setEndOfDay(this.t2DTS);
/*  472 */               profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  474 */               Profile profile1 = profile.getNewInstance(this.m_db);
/*  475 */               profile1.setValOnEffOn(this.t1DTS, this.t1DTS);
/*  476 */               profile1.setEndOfDay(this.t2DTS);
/*  477 */               profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  479 */               String str10 = "";
/*      */               try {
/*  481 */                 if (this.isPeriodicABR) {
/*      */                   
/*  483 */                   String str11 = "";
/*  484 */                   if (str7 != null) {
/*  485 */                     str11 = (String)ADSTYPES_TBL.get(str7);
/*      */                   }
/*  487 */                   str10 = "Periodic " + str11;
/*  488 */                   if ("20".equals(str8)) {
/*  489 */                     str10 = "Deleted " + str11;
/*      */                   }
/*  491 */                   setupPrintWriters();
/*  492 */                   xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                 } else {
/*  494 */                   str10 = entityItem.getKey();
/*      */                   
/*  496 */                   if (domainNeedsChecks(entityItem)) {
/*  497 */                     if (!this.RFRPassedFinal) {
/*  498 */                       xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                     }
/*      */                   } else {
/*      */                     
/*  502 */                     addXMLGenMsg("DOMAIN_NOT_LISTED", str10);
/*      */                   } 
/*      */                 } 
/*  505 */               } catch (IOException iOException) {
/*      */ 
/*      */                 
/*  508 */                 MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("REQ_ERROR"));
/*  509 */                 this.args[0] = iOException.getMessage();
/*  510 */                 addError(messageFormat1.format(this.args));
/*  511 */                 addXMLGenMsg("FAILED", str10);
/*  512 */               } catch (SQLException sQLException) {
/*  513 */                 addXMLGenMsg("FAILED", str10);
/*  514 */                 throw sQLException;
/*  515 */               } catch (MiddlewareRequestException middlewareRequestException) {
/*  516 */                 addXMLGenMsg("FAILED", str10);
/*  517 */                 throw middlewareRequestException;
/*  518 */               } catch (MiddlewareException middlewareException) {
/*  519 */                 addXMLGenMsg("FAILED", str10);
/*  520 */                 throw middlewareException;
/*  521 */               } catch (ParserConfigurationException parserConfigurationException) {
/*  522 */                 addXMLGenMsg("FAILED", str10);
/*  523 */                 throw parserConfigurationException;
/*  524 */               } catch (TransformerException transformerException) {
/*  525 */                 addXMLGenMsg("FAILED", str10);
/*  526 */                 throw transformerException;
/*  527 */               } catch (MissingResourceException missingResourceException) {
/*  528 */                 addXMLGenMsg("FAILED", str10);
/*  529 */                 throw missingResourceException;
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*  534 */         } else if (bool) {
/*  535 */           addError(getShortClassName(getClass()) + " exclude SVCMOD where SVCMODCATG = âProductized Serviceâ (SCSC0004) " + str6);
/*      */         } else {
/*  537 */           addError(getShortClassName(getClass()) + " does not support " + str6);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  542 */         str1 = getNavigationName(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  548 */         if (this.isPeriodicABR && getReturnCode() == 0) {
/*  549 */           PDGUtility pDGUtility = new PDGUtility();
/*  550 */           OPICMList oPICMList = new OPICMList();
/*  551 */           oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/*  552 */           pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */         } 
/*      */         
/*  555 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*  556 */       } catch (Exception exception) {
/*  557 */         throw exception;
/*      */       } finally {
/*  559 */         if (this.isSystemResend) {
/*  560 */           setFlagValue("SYSFEEDRESEND", "No");
/*      */         }
/*      */       }
/*      */     
/*  564 */     } catch (Throwable throwable) {
/*  565 */       StringWriter stringWriter = new StringWriter();
/*  566 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  567 */       String str7 = "<pre>{0}</pre>";
/*  568 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  569 */       setReturnCode(-3);
/*  570 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  572 */       this.args[0] = throwable.getMessage();
/*  573 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  574 */       messageFormat1 = new MessageFormat(str7);
/*  575 */       this.args[0] = stringWriter.getBuffer().toString();
/*  576 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  577 */       logError("Exception: " + throwable.getMessage());
/*  578 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  582 */       setDGTitle(str1);
/*  583 */       setDGRptName(getShortClassName(getClass()));
/*  584 */       setDGRptClass(getABRCode());
/*      */       
/*  586 */       if (!isReadOnly()) {
/*  587 */         clearSoftLock();
/*      */       }
/*  589 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  594 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  595 */     this.args[0] = getShortClassName(getClass());
/*  596 */     this.args[1] = str1;
/*  597 */     String str3 = messageFormat.format(this.args);
/*      */     
/*  599 */     String str4 = null;
/*  600 */     if (this.isPeriodicABR) {
/*  601 */       str4 = buildPeriodicRptHeader();
/*  602 */       restoreXtraContent();
/*      */     } else {
/*  604 */       str4 = buildDQTriggeredRptHeader();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  610 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  611 */     this.rptSb.insert(0, str5);
/*      */     
/*  613 */     println(this.rptSb.toString());
/*  614 */     printDGSubmitString();
/*  615 */     println(EACustom.getTOUDiv());
/*  616 */     buildReportFooter();
/*      */   }
/*      */   
/*      */   private boolean checkSVCMOD(EntityItem paramEntityItem) {
/*  620 */     boolean bool = false;
/*  621 */     String str1 = "";
/*  622 */     String str2 = paramEntityItem.getEntityType();
/*  623 */     if (str2.equals("SVCMOD")) {
/*  624 */       str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "SVCMODSUBCATG");
/*  625 */       if (str1.equals("SCSC0004")) {
/*  626 */         bool = true;
/*      */       }
/*      */     } 
/*  629 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDTFS(EntityItem paramEntityItem, XMLMQ paramXMLMQ) {
/*  638 */     String str1 = paramXMLMQ.getStatusAttr();
/*  639 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/*  640 */     String str3 = "";
/*  641 */     if ("0020".equals(str2)) {
/*  642 */       str3 = getQueuedValue("ADSABRSTATUS");
/*      */     } else {
/*      */       
/*  645 */       str3 = getRFRQueuedValue("ADSABRSTATUS");
/*      */     } 
/*  647 */     addDebug("getDTFS " + getEntityType() + str2 + " from properties file is " + str3);
/*  648 */     return str3;
/*      */   }
/*      */   
/*      */   private void setupPrintWriters() {
/*  652 */     String str = this.m_abri.getFileName();
/*  653 */     int i = str.lastIndexOf(".");
/*  654 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*  655 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/*  657 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  658 */     } catch (Exception exception) {
/*  659 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/*  662 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/*  663 */     } catch (Exception exception) {
/*  664 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriters() {
/*  668 */     if (this.dbgPw != null) {
/*  669 */       this.dbgPw.flush();
/*  670 */       this.dbgPw.close();
/*  671 */       this.dbgPw = null;
/*      */     } 
/*  673 */     if (this.userxmlPw != null) {
/*  674 */       this.userxmlPw.flush();
/*  675 */       this.userxmlPw.close();
/*  676 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  682 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/*  684 */       BufferedInputStream bufferedInputStream = null;
/*  685 */       FileInputStream fileInputStream = null;
/*  686 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  688 */         fileInputStream = new FileInputStream(this.userxmlfn);
/*  689 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  691 */         String str = null;
/*  692 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  694 */         while ((str = bufferedReader.readLine()) != null) {
/*  695 */           this.userxmlSb.append(convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/*  698 */         File file = new File(this.userxmlfn);
/*  699 */         if (file.exists()) {
/*  700 */           file.delete();
/*      */         }
/*  702 */       } catch (Exception exception) {
/*  703 */         exception.printStackTrace();
/*      */       } finally {
/*  705 */         if (bufferedInputStream != null) {
/*      */           try {
/*  707 */             bufferedInputStream.close();
/*  708 */           } catch (Exception exception) {
/*  709 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  712 */         if (fileInputStream != null) {
/*      */           try {
/*  714 */             fileInputStream.close();
/*  715 */           } catch (Exception exception) {
/*  716 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  721 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/*  724 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  726 */       BufferedInputStream bufferedInputStream = null;
/*  727 */       FileInputStream fileInputStream = null;
/*  728 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  730 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  731 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  733 */         String str = null;
/*  734 */         StringBuffer stringBuffer = new StringBuffer();
/*  735 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  737 */         while ((str = bufferedReader.readLine()) != null) {
/*  738 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  740 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  743 */         File file = new File(this.dbgfn);
/*  744 */         if (file.exists()) {
/*  745 */           file.delete();
/*      */         }
/*  747 */       } catch (Exception exception) {
/*  748 */         exception.printStackTrace();
/*      */       } finally {
/*  750 */         if (bufferedInputStream != null) {
/*      */           try {
/*  752 */             bufferedInputStream.close();
/*  753 */           } catch (Exception exception) {
/*  754 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  757 */         if (fileInputStream != null) {
/*      */           try {
/*  759 */             fileInputStream.close();
/*  760 */           } catch (Exception exception) {
/*  761 */             exception.printStackTrace();
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
/*  773 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/*  774 */     Object[] arrayOfObject = { paramString2 };
/*  775 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/*  792 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Status: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Prior Status: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  803 */     MessageFormat messageFormat = new MessageFormat(str);
/*  804 */     this.args[0] = this.m_prof.getOPName();
/*  805 */     this.args[1] = this.m_prof.getRoleDescription();
/*  806 */     this.args[2] = this.m_prof.getWGName();
/*  807 */     this.args[3] = this.t2DTS;
/*  808 */     this.args[4] = this.curStatus;
/*  809 */     this.args[5] = this.t1DTS;
/*  810 */     this.args[6] = this.priorStatus;
/*  811 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  812 */     this.args[8] = this.abrversion + " " + getABRVersion();
/*      */     
/*  814 */     return messageFormat.format(this.args);
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
/*  826 */     String str = "<table>" + NEWLINE + "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{2}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {3} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  832 */     MessageFormat messageFormat = new MessageFormat(str);
/*  833 */     this.args[0] = this.t2DTS;
/*  834 */     this.args[1] = this.t1DTS;
/*  835 */     this.args[2] = this.xmlgenSb.toString();
/*  836 */     this.args[3] = this.abrversion + " " + getABRVersion();
/*      */     
/*  838 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityList getEntityListForDiff(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  848 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, paramProfile, paramString);
/*      */     
/*  850 */     EntityList entityList = this.m_db.getEntityList(paramProfile, extractActionItem, new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*  851 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */ 
/*      */     
/*  854 */     addDebug("EntityList for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  855 */         PokUtils.outputList(entityList));
/*      */ 
/*      */     
/*  858 */     if (isVEFiltered(paramString)) {
/*      */       
/*  860 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  861 */       String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  862 */       addDebug("The status of the root for VE " + paramString + " is: " + str);
/*      */ 
/*      */ 
/*      */       
/*  866 */       for (byte b = 0; b < VE_Filter_Array.length; b++) {
/*  867 */         addDebug("Looking at VE_filter_Array" + VE_Filter_Array[b][0] + " " + VE_Filter_Array[b][1] + " " + VE_Filter_Array[b][2]);
/*      */         
/*  869 */         if (VE_Filter_Array[b][0].equals(paramString)) {
/*  870 */           EntityGroup entityGroup = entityList.getEntityGroup(VE_Filter_Array[b][1]);
/*  871 */           addDebug("Found " + entityList.getEntityGroup(VE_Filter_Array[b][1]));
/*      */ 
/*      */           
/*  874 */           if (entityGroup != null) {
/*      */ 
/*      */             
/*  877 */             EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */ 
/*      */             
/*  880 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/*  882 */               String str1 = null;
/*  883 */               boolean bool = true;
/*  884 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/*  885 */               String str2 = entityItem1.getEntityType();
/*      */               
/*  887 */               addDebug("Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */ 
/*      */               
/*  890 */               String str3 = VE_Filter_Array[b][2];
/*      */ 
/*      */               
/*  893 */               str1 = PokUtils.getAttributeFlagValue(entityItem1, (String)ITEM_STATUS_ATTR_TBL.get(str2));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  901 */               addDebug((String)ITEM_STATUS_ATTR_TBL.get(str2) + " is " + str1);
/*  902 */               if (str1 == null) { bool = false; }
/*  903 */               else if (str1.equals("0020")) { bool = false; }
/*  904 */               else if (str1.equals("0040") && (str.equals("0040") || str3
/*  905 */                 .equals("RFR Final"))) { bool = false; }
/*      */               
/*  907 */               if (bool == true) {
/*      */                 
/*  909 */                 addDebug("Removing " + str2 + " " + entityItem1.getEntityID() + " " + str1 + " from list");
/*  910 */                 addDebug("Filter criteria is " + str3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  941 */                 EntityItem[] arrayOfEntityItem1 = new EntityItem[entityItem1.getUpLinkCount()];
/*  942 */                 entityItem1.getUpLink().copyInto((Object[])arrayOfEntityItem1);
/*      */ 
/*      */                 
/*  945 */                 for (byte b2 = 0; b2 < arrayOfEntityItem1.length; b2++) {
/*      */                   
/*  947 */                   EntityItem entityItem2 = arrayOfEntityItem1[b2];
/*  948 */                   addDebug("uplink: " + entityItem2.getKey());
/*      */                   
/*  950 */                   entityItem1.removeUpLink((EANEntity)entityItem2);
/*      */ 
/*      */                   
/*  953 */                   if (entityItem2.getEntityGroup() != null)
/*      */                   {
/*      */                     
/*  956 */                     if (!entityItem2.hasDownLinks()) {
/*      */                       int j;
/*  958 */                       for (j = entityItem2.getAttributeCount() - 1; j >= 0; j--) {
/*  959 */                         EANAttribute eANAttribute = entityItem2.getAttribute(j);
/*  960 */                         entityItem2.removeAttribute(eANAttribute);
/*      */                       } 
/*      */                       
/*  963 */                       entityItem2.getEntityGroup().removeEntityItem(entityItem2);
/*      */ 
/*      */                       
/*  966 */                       for (j = entityItem2.getUpLinkCount() - 1; j >= 0; j--) {
/*  967 */                         EntityItem entityItem3 = (EntityItem)entityItem2.getUpLink(j);
/*  968 */                         entityItem2.removeUpLink((EANEntity)entityItem3);
/*      */                       } 
/*      */                       
/*  971 */                       entityItem2.setParent(null);
/*      */                     } 
/*      */                   }
/*      */                   
/*  975 */                   arrayOfEntityItem1[b2] = null;
/*      */                 } 
/*      */                 
/*  978 */                 arrayOfEntityItem1 = null;
/*      */                 
/*  980 */                 EntityItem[] arrayOfEntityItem2 = new EntityItem[entityItem1.getDownLinkCount()];
/*  981 */                 entityItem1.getDownLink().copyInto((Object[])arrayOfEntityItem2);
/*      */                 
/*      */                 int i;
/*  984 */                 for (i = 0; i < arrayOfEntityItem2.length; i++) {
/*      */                   
/*  986 */                   EntityItem entityItem2 = arrayOfEntityItem2[i];
/*  987 */                   addDebug("Downlink: " + entityItem2.getKey());
/*      */                   
/*  989 */                   entityItem1.removeDownLink((EANEntity)entityItem2);
/*      */ 
/*      */                   
/*  992 */                   if (entityItem2.getEntityGroup() != null)
/*      */                   {
/*      */                     
/*  995 */                     if (!entityItem2.hasUpLinks()) {
/*      */                       int j;
/*  997 */                       for (j = entityItem2.getAttributeCount() - 1; j >= 0; j--) {
/*  998 */                         EANAttribute eANAttribute = entityItem2.getAttribute(j);
/*  999 */                         entityItem2.removeAttribute(eANAttribute);
/*      */                       } 
/*      */                       
/* 1002 */                       entityItem2.getEntityGroup().removeEntityItem(entityItem2);
/*      */ 
/*      */                       
/* 1005 */                       for (j = entityItem2.getUpLinkCount() - 1; j >= 0; j--) {
/* 1006 */                         EntityItem entityItem3 = (EntityItem)entityItem2.getUpLink(j);
/* 1007 */                         entityItem2.removeDownLink((EANEntity)entityItem3);
/*      */                       } 
/*      */                       
/* 1010 */                       entityItem2.setParent(null);
/*      */                     } 
/*      */                   }
/*      */                   
/* 1014 */                   arrayOfEntityItem2[i] = null;
/*      */                 } 
/*      */                 
/* 1017 */                 arrayOfEntityItem2 = null;
/*      */                 
/* 1019 */                 entityItem1.setParent(null);
/*      */                 
/* 1021 */                 for (i = entityItem1.getAttributeCount() - 1; i >= 0; i--) {
/* 1022 */                   EANAttribute eANAttribute = entityItem1.getAttribute(i);
/* 1023 */                   entityItem1.removeAttribute(eANAttribute);
/*      */                 } 
/*      */                 
/* 1026 */                 entityGroup.removeEntityItem(entityItem1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1034 */       addDebug("EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/* 1035 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */ 
/*      */     
/* 1039 */     return entityList;
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
/*      */   private void setT1DTS(XMLMQ paramXMLMQ, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) throws MiddlewareRequestException, MiddlewareException {
/* 1096 */     this.t1DTS = this.m_strEpoch;
/* 1097 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1099 */     if (this.isPeriodicABR) {
/* 1100 */       addDebug("getT1 entered for Periodic ABR " + entityItem.getKey());
/*      */       
/* 1102 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/* 1103 */       if (eANMetaAttribute == null) {
/* 1104 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*      */       
/* 1107 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */     } else {
/* 1109 */       String str = paramXMLMQ.getStatusAttr();
/* 1110 */       addDebug("getT1 entered for DQ ABR " + entityItem.getKey() + " " + str + " isSystemResend:" + this.isSystemResend);
/* 1111 */       if (!this.isSystemResend) {
/*      */         
/* 1113 */         String str1 = getT2Status(paramAttributeChangeHistoryGroup2);
/*      */         
/* 1115 */         if (existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/*      */           
/* 1117 */           if (str1.equals("0040")) {
/* 1118 */             this.t1DTS = getTQRFR(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/* 1119 */             if (this.t1DTS.equals(this.m_strEpoch)) {
/* 1120 */               this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/* 1121 */             } else if (this.RFRPassedFinal != true) {
/* 1122 */               this.actionTaken = this.rsBundle.getString("ACTION_R4R_CHANGES");
/*      */             }
/*      */           
/* 1125 */           } else if (str1.equals("0020")) {
/* 1126 */             this.t1DTS = getTQFinal(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/* 1127 */             if (this.t1DTS.equals(this.m_strEpoch)) {
/* 1128 */               this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */             } else {
/* 1130 */               this.actionTaken = this.rsBundle.getString("ACTION_FINAL_CHANGES");
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/* 1135 */           if (str1.equals("0040")) {
/* 1136 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/* 1137 */           } else if (str1.equals("0020")) {
/* 1138 */             this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */           } 
/* 1140 */           addDebug("getT1 for " + entityItem.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 1150 */     if (paramAttributeChangeHistoryGroup != null) {
/* 1151 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1152 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1153 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1154 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/* 1158 */     return false;
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
/*      */   private void setT2DTS(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws MiddlewareException {
/* 1171 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*      */       
/* 1173 */       int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount();
/*      */ 
/*      */       
/* 1176 */       AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 2);
/* 1177 */       if (attributeChangeHistoryItem != null) {
/* 1178 */         addDebug("getT2Time [" + (i - 2) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1179 */             .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1180 */         if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1181 */           this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */         } else {
/* 1183 */           addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
/*      */           
/* 1185 */           this.t2DTS = getNow();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1190 */       attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 3);
/* 1191 */       if (attributeChangeHistoryItem != null) {
/* 1192 */         addDebug("getT2Time [" + (i - 3) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1193 */             .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1194 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1195 */           this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */         } else {
/* 1197 */           addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not &DTFS " + paramString + " return valfrom of queued.");
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1202 */       this.t2DTS = getNow();
/* 1203 */       addDebug("getT2Time for ADSABRSTATUS changedHistoryGroup has no history,set t2 getNow().");
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
/*      */   private String getT2Status(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) throws MiddlewareRequestException {
/* 1216 */     String str = "";
/* 1217 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1218 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 1220 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1221 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1222 */         if (attributeChangeHistoryItem != null) {
/* 1223 */           addDebug("getT2Status [" + i + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1224 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*      */ 
/*      */           
/* 1227 */           if (attributeChangeHistoryItem.getChangeDate().compareTo(this.t2DTS) < 0) {
/*      */ 
/*      */             
/* 1230 */             if (!"0020".equals(attributeChangeHistoryItem.getFlagCode()) && !"0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 1231 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/* 1232 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */               break;
/*      */             } 
/* 1235 */             this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1236 */             str = attributeChangeHistoryItem.getFlagCode();
/* 1237 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */             
/* 1239 */             if (attributeChangeHistoryItem != null) {
/* 1240 */               this.priorStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1241 */               addDebug("getT2Status [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem.getChangeDate() + " flagcode: " + attributeChangeHistoryItem
/* 1242 */                   .getFlagCode());
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1250 */       addDebug("getT2Status for " + entityItem.getKey() + " getChangeHistoryItemCount less than 0.");
/*      */     } 
/* 1252 */     return str;
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
/*      */   private String getTQFinal(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) throws MiddlewareRequestException {
/* 1266 */     String str = this.m_strEpoch;
/* 1267 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/* 1268 */       boolean bool = false;
/*      */       
/* 1270 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/* 1271 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 1272 */         if (attributeChangeHistoryItem != null) {
/* 1273 */           addDebug("getTQFinalDTS [" + i + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1274 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1275 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 1277 */             bool = true;
/*      */           }
/* 1279 */           if (bool && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1280 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 1282 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/* 1284 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1285 */               str = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/* 1287 */               addDebug("getPreveTQFinalDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/* 1289 */             String str1 = getTQStatus(paramAttributeChangeHistoryGroup2, str);
/* 1290 */             if (str1.equals("0020")) {
/*      */               break;
/*      */             }
/* 1293 */             bool = false;
/* 1294 */             str = this.m_strEpoch;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1300 */       addDebug("getTQFinalDTS for ADSABRSTATUS has no changed history");
/*      */     } 
/* 1302 */     return str;
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
/*      */   private String getTQRFR(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) throws MiddlewareRequestException {
/* 1316 */     String str1 = this.m_strEpoch;
/* 1317 */     String str2 = this.m_strEpoch;
/*      */     
/* 1319 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/* 1320 */       boolean bool1 = false;
/* 1321 */       boolean bool2 = false;
/*      */       
/* 1323 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/* 1324 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 1325 */         if (attributeChangeHistoryItem != null) {
/* 1326 */           addDebug("getTQRFRDTS [" + i + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1327 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1328 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 1330 */             bool1 = true;
/*      */           }
/* 1332 */           if (bool1 && attributeChangeHistoryItem.getFlagCode().equals("0020"))
/*      */           {
/* 1334 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 1336 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/* 1338 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1339 */               str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/* 1341 */               addDebug("getPreveTQRFRDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/* 1343 */             if (!bool2) {
/* 1344 */               bool2 = true;
/* 1345 */               str2 = str1;
/*      */             } 
/* 1347 */             String str = getTQStatus(paramAttributeChangeHistoryGroup2, str1);
/* 1348 */             if (str.equals("0020")) {
/* 1349 */               this.RFRPassedFinal = true;
/* 1350 */               this.actionTaken = this.rsBundle.getString("ACTION_R4R_PASSEDFINAL");
/* 1351 */               return str1;
/*      */             } 
/* 1353 */             bool1 = false;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1360 */       addDebug("getTQRFRDTS for ADSABRSTATUS has no changed history");
/*      */     } 
/* 1362 */     return str2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTQStatus(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws MiddlewareRequestException {
/* 1368 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 1370 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1371 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1372 */         if (attributeChangeHistoryItem != null) {
/* 1373 */           addDebug("getTQStatus [" + i + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1374 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1375 */           if (paramString.compareTo(attributeChangeHistoryItem.getChangeDate()) > 0) {
/* 1376 */             return attributeChangeHistoryItem.getFlagCode();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1381 */       addDebug("getTQStatus for STATUS has no changed history!");
/*      */     } 
/* 1383 */     return "@@";
/*      */   }
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getADSABRSTATUSHistory() throws MiddlewareException {
/* 1388 */     String str = "ADSABRSTATUS";
/* 1389 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1391 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1392 */     if (eANAttribute != null) {
/* 1393 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1395 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ paramXMLMQ) throws MiddlewareException {
/* 1401 */     String str = paramXMLMQ.getStatusAttr();
/* 1402 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1403 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1404 */     if (eANAttribute != null) {
/* 1405 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1407 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existPassedFinal(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2) throws MiddlewareRequestException {
/* 1414 */     boolean bool1 = false;
/* 1415 */     boolean bool2 = false;
/*      */     
/* 1417 */     if (paramAttributeChangeHistoryGroup1 != null) {
/* 1418 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/*      */         
/* 1420 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 1421 */         if (attributeChangeHistoryItem != null) {
/* 1422 */           addDebug("existPassedFinal [" + i + "] isActive: " + attributeChangeHistoryItem
/* 1423 */               .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1424 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1425 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 1427 */             bool1 = true;
/*      */           }
/* 1429 */           if (bool1 && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1430 */             String str = getTQStatus(paramAttributeChangeHistoryGroup2, attributeChangeHistoryItem.getChangeDate());
/* 1431 */             if (str.equals("0020")) {
/* 1432 */               bool2 = true;
/*      */               break;
/*      */             } 
/* 1435 */             bool1 = false;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1441 */     return bool2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVEFiltered(String paramString) {
/* 1447 */     for (byte b = 0; b < VE_Filter_Array.length; b++) {
/* 1448 */       if (VE_Filter_Array[b][0].equals(paramString))
/* 1449 */         return true; 
/*      */     } 
/* 1451 */     return false;
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
/* 1466 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 1467 */     if (profile == null) {
/* 1468 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 1470 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 1472 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 1473 */       addDebug("switchRole nlsids: " + str);
/* 1474 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1475 */       while (stringTokenizer.hasMoreTokens()) {
/* 1476 */         String str1 = stringTokenizer.nextToken();
/* 1477 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 1478 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 1479 */           profile.getReadLanguages().addElement(nLSItem);
/* 1480 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1485 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1495 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1497 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1498 */     EANList eANList = entityGroup.getMetaAttribute();
/* 1499 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1501 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1502 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1503 */       stringBuffer.append(" ");
/*      */     } 
/*      */     
/* 1506 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1512 */     return this.m_db;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/* 1517 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1522 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1528 */     if (this.dbgPw != null) {
/* 1529 */       this.dbgLen += paramString.length();
/* 1530 */       this.dbgPw.println(paramString);
/* 1531 */       this.dbgPw.flush();
/*      */     } else {
/* 1533 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1541 */     addOutput(paramString);
/* 1542 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/* 1549 */     return this.rsBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void notify(XMLMQ paramXMLMQ, String paramString1, String paramString2) throws MissingResourceException {
/* 1558 */     MessageFormat messageFormat = null;
/* 1559 */     Vector<String> vector = paramXMLMQ.getMQPropertiesFN();
/* 1560 */     byte b1 = 0;
/* 1561 */     boolean bool = false;
/*      */ 
/*      */     
/* 1564 */     for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 1565 */       String str = vector.elementAt(b2);
/* 1566 */       ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/* 1567 */           getLocale(getProfile().getReadLanguage().getNLSID()));
/* 1568 */       Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 1569 */       boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/* 1570 */       hashtable.put("MQCID", paramXMLMQ.getMQCID());
/* 1571 */       hashtable.put("XMLTYPE", "ADS");
/* 1572 */       if (bool1) {
/*      */         try {
/* 1574 */           MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable);
/*      */           
/* 1576 */           messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/* 1577 */           this.args[0] = str;
/* 1578 */           this.args[1] = paramString1;
/* 1579 */           addOutput(messageFormat.format(this.args));
/* 1580 */           b1++;
/* 1581 */           if (!bool)
/*      */           {
/* 1583 */             addXMLGenMsg("SUCCESS", paramString1);
/*      */           }
/* 1585 */         } catch (MQException mQException) {
/*      */ 
/*      */           
/* 1588 */           addXMLGenMsg("FAILED", paramString1);
/* 1589 */           bool = true;
/* 1590 */           messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/* 1591 */           this.args[0] = str + " " + paramString1;
/* 1592 */           this.args[1] = "" + mQException.completionCode;
/* 1593 */           this.args[2] = "" + mQException.reasonCode;
/* 1594 */           addError(messageFormat.format(this.args));
/* 1595 */           mQException.printStackTrace(System.out);
/* 1596 */         } catch (IOException iOException) {
/*      */           
/* 1598 */           addXMLGenMsg("FAILED", paramString1);
/* 1599 */           bool = true;
/* 1600 */           messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/* 1601 */           this.args[0] = str + " " + paramString1;
/* 1602 */           this.args[1] = iOException.toString();
/* 1603 */           addError(messageFormat.format(this.args));
/* 1604 */           iOException.printStackTrace(System.out);
/*      */         } 
/*      */       } else {
/*      */         
/* 1608 */         messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/* 1609 */         this.args[0] = str;
/* 1610 */         addError(messageFormat.format(this.args));
/*      */         
/* 1612 */         addXMLGenMsg("NOT_SENT", paramString1);
/*      */       } 
/*      */     } 
/*      */     
/* 1616 */     if (b1 > 0 && b1 != vector.size()) {
/* 1617 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
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
/* 1629 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 1630 */     Transformer transformer = transformerFactory.newTransformer();
/* 1631 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 1633 */     transformer.setOutputProperty("indent", "no");
/* 1634 */     transformer.setOutputProperty("method", "xml");
/* 1635 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 1638 */     StringWriter stringWriter = new StringWriter();
/* 1639 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 1640 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 1641 */     transformer.transform(dOMSource, streamResult);
/* 1642 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */     
/* 1645 */     transformer.setOutputProperty("indent", "yes");
/* 1646 */     stringWriter = new StringWriter();
/* 1647 */     streamResult = new StreamResult(stringWriter);
/* 1648 */     transformer.transform(dOMSource, streamResult);
/* 1649 */     addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     
/* 1651 */     return str;
/*      */   }
/*      */   protected void addUserXML(String paramString) {
/* 1654 */     if (this.userxmlPw != null) {
/* 1655 */       this.userxmlLen += paramString.length();
/* 1656 */       this.userxmlPw.println(paramString);
/* 1657 */       this.userxmlPw.flush();
/*      */     } else {
/* 1659 */       this.userxmlSb.append(convertToHTML(paramString) + NEWLINE);
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
/* 1673 */     boolean bool = false;
/* 1674 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 1675 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 1676 */     if (str.equals("all")) {
/* 1677 */       bool = true;
/*      */     } else {
/* 1679 */       HashSet<String> hashSet = new HashSet();
/* 1680 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1681 */       while (stringTokenizer.hasMoreTokens()) {
/* 1682 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 1684 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 1685 */       hashSet.clear();
/*      */     } 
/*      */     
/* 1688 */     if (!bool) {
/* 1689 */       addDebug("PDHDOMAIN for " + paramEntityItem.getKey() + " did not include " + str + ", execution is bypassed [" + 
/* 1690 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/* 1692 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale getLocale(int paramInt) {
/* 1702 */     Locale locale = null;
/* 1703 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 1706 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1730 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1740 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1749 */     return "ADSABRSTATUS";
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
/* 1760 */     String str = "";
/* 1761 */     StringBuffer stringBuffer = new StringBuffer();
/* 1762 */     StringCharacterIterator stringCharacterIterator = null;
/* 1763 */     char c = ' ';
/* 1764 */     if (paramString != null) {
/* 1765 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1766 */       c = stringCharacterIterator.first();
/* 1767 */       while (c != '￿') {
/*      */         
/* 1769 */         switch (c) {
/*      */           
/*      */           case '<':
/* 1772 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 1775 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/* 1781 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */           default:
/* 1784 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1787 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1789 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1792 */     return str;
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
/* 1810 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 1811 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/* 1812 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 1815 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1816 */     if (eANMetaAttribute == null) {
/* 1817 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/* 1818 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + entityItem
/* 1819 */           .getEntityType() + ", nothing to do");
/*      */       return;
/*      */     } 
/* 1822 */     if (paramString2 != null)
/*      */     {
/* 1824 */       if (paramString2.equals(getAttributeFlagEnabledValue(entityItem, paramString1))) {
/* 1825 */         addDebug("setFlagValue " + entityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/* 1830 */           if (this.m_cbOn == null) {
/* 1831 */             setControlBlock();
/*      */           }
/* 1833 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*      */           
/* 1835 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */           
/* 1837 */           Vector<SingleFlag> vector = new Vector();
/* 1838 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 1839 */           vector.addElement(singleFlag);
/* 1840 */           returnEntityKey.m_vctAttributes = vector;
/* 1841 */           vector1.addElement(returnEntityKey);
/*      */           
/* 1843 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 1844 */           addDebug(entityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*      */         } finally {
/*      */           
/* 1847 */           this.m_db.commit();
/* 1848 */           this.m_db.freeStatement();
/* 1849 */           this.m_db.isPending("finally after update in setflag value");
/*      */         } 
/*      */       }  } 
/*      */   }
/*      */   
/*      */   private String sysFeedResendStatus(String paramString1, String paramString2, String paramString3) {
/* 1855 */     return ABRServerProperties.getValue(paramString1, paramString2, paramString3);
/*      */   }
/*      */   
/*      */   protected String getQueuedValue(String paramString) {
/* 1859 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 1860 */     if (str == null) {
/* 1861 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 1862 */       return "0020";
/*      */     } 
/* 1864 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 1865 */     return ABRServerProperties.getABRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getRFRQueuedValue(String paramString) {
/* 1870 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 1871 */     if (str == null) {
/* 1872 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 1873 */       return "0020";
/*      */     } 
/* 1875 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 1876 */     return ABRServerProperties.getABRRFRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */   
/*      */   protected boolean fullMode() {
/* 1880 */     String str = ABRServerProperties.getValue("ADSABRSTATUS", "_FullMode");
/* 1881 */     if (str.equals("true") || str.equals("True") || str.equals("TRUE")) {
/* 1882 */       addDebug("ADSABRSTATUS is running in FullMode!");
/* 1883 */       return true;
/*      */     } 
/* 1885 */     addDebug("ADSABRSTATUS is not running in FullMode!");
/* 1886 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isPeriodicABR() {
/* 1890 */     return this.isPeriodicABR;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\wave2\ADSABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */