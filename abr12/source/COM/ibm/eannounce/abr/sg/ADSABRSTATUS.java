/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.abr.util.XMLElem;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ADSABRSTATUS
/*      */   extends PokBaseABR
/*      */ {
/*  107 */   private StringBuffer rptSb = new StringBuffer();
/*  108 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  109 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   protected static final String ADSMQSERIES = "ADSMQSERIES";
/*      */   
/*      */   protected static final String SYSFEEDRESEND_YES = "Yes";
/*      */   protected static final String SYSFEEDRESEND_NO = "No";
/*  115 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  116 */   private PrintWriter dbgPw = null;
/*  117 */   private PrintWriter userxmlPw = null;
/*  118 */   private String dbgfn = null;
/*  119 */   private String userxmlfn = null;
/*  120 */   private int userxmlLen = 0;
/*  121 */   private int dbgLen = 0;
/*      */   
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*      */   protected static final String STATUS_DRAFT = "0010";
/*      */   protected static final String STATUS_FINAL = "0020";
/*      */   protected static final String STATUS_R4REVIEW = "0040";
/*      */   protected static final String STATUS_CHGREQ = "0050";
/*  128 */   private ResourceBundle rsBundle = null;
/*  129 */   private String priorStatus = "&nbsp;";
/*  130 */   private String curStatus = "&nbsp;";
/*      */   private boolean isPeriodicABR = false;
/*      */   private boolean isSystemResend = false;
/*  133 */   private String actionTaken = "";
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Hashtable READ_LANGS_TBL;
/*      */ 
/*      */ 
/*      */   
/*  141 */   private static final Hashtable ABR_TBL = new Hashtable<>(); protected static final Hashtable ADSTYPES_TBL; static {
/*  142 */     ABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.ADSMODELABR");
/*  143 */     ABR_TBL.put("FEATURE", "COM.ibm.eannounce.abr.sg.ADSFEATUREABR");
/*  144 */     ABR_TBL.put("SWFEATURE", "COM.ibm.eannounce.abr.sg.ADSSWFEATUREABR");
/*  145 */     ABR_TBL.put("SVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSSVCFEATUREABR");
/*  146 */     ABR_TBL.put("FCTRANSACTION", "COM.ibm.eannounce.abr.sg.ADSFCTRANSABR");
/*  147 */     ABR_TBL.put("MODELCONVERT", "COM.ibm.eannounce.abr.sg.ADSMODELCONVERTABR");
/*  148 */     ABR_TBL.put("PRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSPRODSTRUCTABR");
/*  149 */     ABR_TBL.put("SWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSSWPRODSTRUCTABR");
/*  150 */     ABR_TBL.put("SVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSSVCPRODSTRUCTABR");
/*  151 */     ABR_TBL.put("LSEO", "COM.ibm.eannounce.abr.sg.ADSLSEOABR");
/*  152 */     ABR_TBL.put("SVCMOD", "COM.ibm.eannounce.abr.sg.ADSSVCMODABR");
/*      */     
/*  154 */     ABR_TBL.put("CATNAV", "COM.ibm.eannounce.abr.sg.ADSCATNAVABR");
/*  155 */     ABR_TBL.put("XLATE", "COM.ibm.eannounce.abr.sg.ADSXLATEABR");
/*  156 */     ABR_TBL.put("MODELCG", "COM.ibm.eannounce.abr.sg.ADSWWCOMPATABR");
/*  157 */     ABR_TBL.put("GENAREA", "COM.ibm.eannounce.abr.sg.ADSGENAREAABR");
/*      */     
/*  159 */     ABR_TBL.put("DELFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELFEATUREABR");
/*  160 */     ABR_TBL.put("DELFCTRANSACTION", "COM.ibm.eannounce.abr.sg.ADSDELFCTRANSABR");
/*  161 */     ABR_TBL.put("DELMODEL", "COM.ibm.eannounce.abr.sg.ADSDELMODELABR");
/*  162 */     ABR_TBL.put("DELMODELCONVERT", "COM.ibm.eannounce.abr.sg.ADSDELMODELCONVERTABR");
/*  163 */     ABR_TBL.put("DELPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELPRODSTRUCTABR");
/*  164 */     ABR_TBL.put("DELSVCFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSVCFEATUREABR");
/*  165 */     ABR_TBL.put("DELSVCPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSVCPRODSTRUCTABR");
/*  166 */     ABR_TBL.put("DELSWFEATURE", "COM.ibm.eannounce.abr.sg.ADSDELSWFEATUREABR");
/*  167 */     ABR_TBL.put("DELSWPRODSTRUCT", "COM.ibm.eannounce.abr.sg.ADSDELSWPRODSTRUCTABR");
/*      */     
/*  169 */     READ_LANGS_TBL = new Hashtable<>();
/*      */     
/*  171 */     READ_LANGS_TBL.put("" + Profile.ENGLISH_LANGUAGE.getNLSID(), Profile.ENGLISH_LANGUAGE);
/*  172 */     READ_LANGS_TBL.put("" + Profile.GERMAN_LANGUAGE.getNLSID(), Profile.GERMAN_LANGUAGE);
/*  173 */     READ_LANGS_TBL.put("" + Profile.ITALIAN_LANGUAGE.getNLSID(), Profile.ITALIAN_LANGUAGE);
/*  174 */     READ_LANGS_TBL.put("" + Profile.JAPANESE_LANGUAGE.getNLSID(), Profile.JAPANESE_LANGUAGE);
/*  175 */     READ_LANGS_TBL.put("" + Profile.FRENCH_LANGUAGE.getNLSID(), Profile.FRENCH_LANGUAGE);
/*  176 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LANGUAGE.getNLSID(), Profile.SPANISH_LANGUAGE);
/*  177 */     READ_LANGS_TBL.put("" + Profile.UK_ENGLISH_LANGUAGE.getNLSID(), Profile.UK_ENGLISH_LANGUAGE);
/*  178 */     READ_LANGS_TBL.put("" + Profile.KOREAN_LANGUAGE.getNLSID(), Profile.KOREAN_LANGUAGE);
/*  179 */     READ_LANGS_TBL.put("" + Profile.CHINESE_LANGUAGE.getNLSID(), Profile.CHINESE_LANGUAGE);
/*  180 */     READ_LANGS_TBL.put("" + Profile.FRENCH_CANADIAN_LANGUAGE.getNLSID(), Profile.FRENCH_CANADIAN_LANGUAGE);
/*  181 */     READ_LANGS_TBL.put("" + Profile.CHINESE_SIMPLIFIED_LANGUAGE.getNLSID(), Profile.CHINESE_SIMPLIFIED_LANGUAGE);
/*  182 */     READ_LANGS_TBL.put("" + Profile.SPANISH_LATINAMERICAN_LANGUAGE.getNLSID(), Profile.SPANISH_LATINAMERICAN_LANGUAGE);
/*  183 */     READ_LANGS_TBL.put("" + Profile.PORTUGUESE_BRAZILIAN_LANGUAGE.getNLSID(), Profile.PORTUGUESE_BRAZILIAN_LANGUAGE);
/*      */     
/*  185 */     ADSTYPES_TBL = new Hashtable<>();
/*  186 */     ADSTYPES_TBL.put("10", "CATNAV");
/*  187 */     ADSTYPES_TBL.put("100", "SVCPRODSTRUCT");
/*  188 */     ADSTYPES_TBL.put("110", "SWFEATURE");
/*  189 */     ADSTYPES_TBL.put("120", "SWFPRODSTRUCT");
/*  190 */     ADSTYPES_TBL.put("130", "MODELCG");
/*  191 */     ADSTYPES_TBL.put("140", "LSEO");
/*  192 */     ADSTYPES_TBL.put("150", "SVCMOD");
/*  193 */     ADSTYPES_TBL.put("20", "GENAREA");
/*  194 */     ADSTYPES_TBL.put("30", "FEATURE");
/*  195 */     ADSTYPES_TBL.put("40", "FCTRANSACTION");
/*  196 */     ADSTYPES_TBL.put("50", "XLATE");
/*  197 */     ADSTYPES_TBL.put("60", "MODEL");
/*  198 */     ADSTYPES_TBL.put("70", "MODELCONVERT");
/*  199 */     ADSTYPES_TBL.put("80", "PRODSTRUCT");
/*  200 */     ADSTYPES_TBL.put("90", "SVCFEATURE");
/*      */   }
/*      */   protected static final String SYSFeedResendValue = "_SYSFeedResendValue";
/*  203 */   private Object[] args = (Object[])new String[10];
/*  204 */   private String abrversion = "";
/*  205 */   private String t2DTS = null;
/*  206 */   private String t1DTS = "&nbsp;";
/*  207 */   private StringBuffer userxmlSb = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  241 */     String str = (String)ABR_TBL.get(paramString);
/*  242 */     addDebug("creating instance of ADSABR  = '" + str + "' for " + paramString);
/*  243 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  252 */     String str1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  259 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  264 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  267 */       long l = System.currentTimeMillis();
/*      */       
/*  269 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  272 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  275 */       setReturnCode(0);
/*      */ 
/*      */       
/*  278 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  280 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*      */       try {
/*  284 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */         
/*  286 */         this.isPeriodicABR = getEntityType().equals("ADSXMLSETUP");
/*      */         
/*  288 */         String str6 = getEntityType();
/*      */         
/*  290 */         String str7 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*  291 */         String str8 = PokUtils.getAttributeFlagValue(entityItem, "ADSENTITY");
/*      */         
/*  293 */         if (this.isPeriodicABR) {
/*      */           
/*  295 */           if (str7 != null) {
/*  296 */             str6 = (String)ADSTYPES_TBL.get(str7);
/*      */           }
/*  298 */           if ("20".equals(str8)) {
/*  299 */             str6 = "DEL" + str6;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  305 */         String str9 = getSimpleABRName(str6);
/*  306 */         if (str9 != null) {
/*  307 */           boolean bool = true;
/*  308 */           XMLMQ xMLMQ = (XMLMQ)Class.forName(str9).newInstance();
/*      */           
/*  310 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/*      */           
/*  312 */           if (!this.isPeriodicABR) {
/*  313 */             String str10 = xMLMQ.getStatusAttr();
/*  314 */             String str11 = getAttributeFlagEnabledValue(entityItem, "SYSFEEDRESEND");
/*  315 */             String str12 = getAttributeFlagEnabledValue(entityItem, str10);
/*  316 */             this.isSystemResend = "Yes".equals(str11);
/*  317 */             addDebug("execute: " + entityItem.getKey() + " " + str10 + ": " + 
/*  318 */                 PokUtils.getAttributeValue(entityItem, str10, ", ", "", false) + " [" + str12 + "] sysfeedFlag: " + str11);
/*      */ 
/*      */             
/*  321 */             if (this.isSystemResend) {
/*      */               
/*  323 */               String str = sysFeedResendStatus(this.m_abri.getABRCode(), "_SYSFeedResendValue", "Both");
/*  324 */               if (str.equals("0020")) {
/*  325 */                 if (str12.equals("0020")) {
/*  326 */                   this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */                 } else {
/*  328 */                   addDebug(entityItem.getKey() + " is not Final");
/*      */                   
/*  330 */                   addError(this.rsBundle.getString("RESEND_ONLY_FINAL"));
/*      */                 } 
/*  332 */               } else if (str.equals("0040")) {
/*  333 */                 if (str12.equals("0040")) {
/*  334 */                   this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */                 } else {
/*  336 */                   addDebug(entityItem.getKey() + " is not RFR");
/*      */                   
/*  338 */                   addError(this.rsBundle.getString("RESEND_ONLY_R4REVIEW"));
/*      */                 }
/*      */               
/*      */               }
/*  342 */               else if (!"0020".equals(str12) && !"0040".equals(str12)) {
/*  343 */                 addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */                 
/*  345 */                 addError(this.rsBundle.getString("RESEND_NOT_R4RFINAL"));
/*      */               }
/*  347 */               else if ("0020".equals(str12)) {
/*  348 */                 this.actionTaken = this.rsBundle.getString("ACTION_FINAL_RESEND");
/*      */               } else {
/*  350 */                 this.actionTaken = this.rsBundle.getString("ACTION_R4R_RESEND");
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/*  355 */               this.curStatus = PokUtils.getAttributeValue(entityItem, str10, ", ", "", false);
/*  356 */               this.priorStatus = this.curStatus;
/*      */             }
/*  358 */             else if (!"0020".equals(str12) && !"0040".equals(str12)) {
/*  359 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */               
/*  361 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */             } else {
/*  363 */               bool = xMLMQ.createXML(entityItem);
/*  364 */               if (!bool) {
/*  365 */                 addDebug(entityItem.getKey() + " will not have XML generated, createXML=false");
/*      */               }
/*      */             } 
/*      */           } else {
/*      */             
/*  370 */             addDebug("execute: periodic " + entityItem.getKey());
/*      */           } 
/*      */           
/*  373 */           this.t2DTS = getABRQueuedTime();
/*      */           
/*  375 */           if (getReturnCode() == 0 && bool) {
/*      */             
/*  377 */             this.t1DTS = getT1(xMLMQ);
/*      */ 
/*      */             
/*  380 */             Profile profile = switchRole(xMLMQ.getRoleCode());
/*  381 */             if (profile != null) {
/*  382 */               profile.setValOnEffOn(this.t2DTS, this.t2DTS);
/*  383 */               profile.setEndOfDay(this.t2DTS);
/*  384 */               profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  386 */               Profile profile1 = profile.getNewInstance(this.m_db);
/*  387 */               profile1.setValOnEffOn(this.t1DTS, this.t1DTS);
/*  388 */               profile1.setEndOfDay(this.t2DTS);
/*  389 */               profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  391 */               String str = "";
/*      */               try {
/*  393 */                 if (this.isPeriodicABR) {
/*      */                   
/*  395 */                   String str10 = "";
/*  396 */                   if (str7 != null) {
/*  397 */                     str10 = (String)ADSTYPES_TBL.get(str7);
/*      */                   }
/*  399 */                   str = "Periodic " + str10;
/*  400 */                   if ("20".equals(str8)) {
/*  401 */                     str = "Deleted " + str10;
/*      */                   }
/*  403 */                   setupPrintWriters();
/*  404 */                   xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                 } else {
/*  406 */                   str = entityItem.getKey();
/*      */                   
/*  408 */                   if (domainNeedsChecks(entityItem)) {
/*  409 */                     xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                   } else {
/*      */                     
/*  412 */                     addXMLGenMsg("DOMAIN_NOT_LISTED", str);
/*      */                   } 
/*      */                 } 
/*  415 */               } catch (IOException iOException) {
/*      */ 
/*      */                 
/*  418 */                 MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("REQ_ERROR"));
/*  419 */                 this.args[0] = iOException.getMessage();
/*  420 */                 addError(messageFormat1.format(this.args));
/*  421 */                 addXMLGenMsg("FAILED", str);
/*  422 */               } catch (SQLException sQLException) {
/*  423 */                 addXMLGenMsg("FAILED", str);
/*  424 */                 throw sQLException;
/*  425 */               } catch (MiddlewareRequestException middlewareRequestException) {
/*  426 */                 addXMLGenMsg("FAILED", str);
/*  427 */                 throw middlewareRequestException;
/*  428 */               } catch (MiddlewareException middlewareException) {
/*  429 */                 addXMLGenMsg("FAILED", str);
/*  430 */                 throw middlewareException;
/*  431 */               } catch (ParserConfigurationException parserConfigurationException) {
/*  432 */                 addXMLGenMsg("FAILED", str);
/*  433 */                 throw parserConfigurationException;
/*  434 */               } catch (TransformerException transformerException) {
/*  435 */                 addXMLGenMsg("FAILED", str);
/*  436 */                 throw transformerException;
/*  437 */               } catch (MissingResourceException missingResourceException) {
/*  438 */                 addXMLGenMsg("FAILED", str);
/*  439 */                 throw missingResourceException;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*  444 */           addError(getShortClassName(getClass()) + " does not support " + str6);
/*      */         } 
/*      */         
/*  447 */         str1 = getNavigationName(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  453 */         if (this.isPeriodicABR && getReturnCode() == 0) {
/*  454 */           PDGUtility pDGUtility = new PDGUtility();
/*  455 */           OPICMList oPICMList = new OPICMList();
/*  456 */           oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/*  457 */           pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */         } 
/*      */         
/*  460 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*  461 */       } catch (Exception exception) {
/*  462 */         throw exception;
/*      */       } finally {
/*  464 */         if (this.isSystemResend) {
/*  465 */           setFlagValue("SYSFEEDRESEND", "No");
/*      */         }
/*      */       }
/*      */     
/*  469 */     } catch (Throwable throwable) {
/*  470 */       StringWriter stringWriter = new StringWriter();
/*  471 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  472 */       String str7 = "<pre>{0}</pre>";
/*  473 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  474 */       setReturnCode(-3);
/*  475 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  477 */       this.args[0] = throwable.getMessage();
/*  478 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  479 */       messageFormat1 = new MessageFormat(str7);
/*  480 */       this.args[0] = stringWriter.getBuffer().toString();
/*  481 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  482 */       logError("Exception: " + throwable.getMessage());
/*  483 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  487 */       setDGTitle(str1);
/*  488 */       setDGRptName(getShortClassName(getClass()));
/*  489 */       setDGRptClass(getABRCode());
/*      */       
/*  491 */       if (!isReadOnly()) {
/*  492 */         clearSoftLock();
/*      */       }
/*  494 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  499 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  500 */     this.args[0] = getShortClassName(getClass());
/*  501 */     this.args[1] = str1;
/*  502 */     String str3 = messageFormat.format(this.args);
/*      */     
/*  504 */     String str4 = null;
/*  505 */     if (this.isPeriodicABR) {
/*  506 */       str4 = buildPeriodicRptHeader();
/*  507 */       restoreXtraContent();
/*      */     } else {
/*  509 */       str4 = buildDQTriggeredRptHeader();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  515 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  516 */     this.rptSb.insert(0, str5);
/*      */     
/*  518 */     println(this.rptSb.toString());
/*  519 */     printDGSubmitString();
/*  520 */     println(EACustom.getTOUDiv());
/*  521 */     buildReportFooter();
/*      */   }
/*      */   
/*      */   private void setupPrintWriters() {
/*  525 */     String str = this.m_abri.getFileName();
/*  526 */     int i = str.lastIndexOf(".");
/*  527 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*  528 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/*  530 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  531 */     } catch (Exception exception) {
/*  532 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/*  535 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/*  536 */     } catch (Exception exception) {
/*  537 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriters() {
/*  541 */     if (this.dbgPw != null) {
/*  542 */       this.dbgPw.flush();
/*  543 */       this.dbgPw.close();
/*  544 */       this.dbgPw = null;
/*      */     } 
/*  546 */     if (this.userxmlPw != null) {
/*  547 */       this.userxmlPw.flush();
/*  548 */       this.userxmlPw.close();
/*  549 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  555 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/*  557 */       BufferedInputStream bufferedInputStream = null;
/*  558 */       FileInputStream fileInputStream = null;
/*  559 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  561 */         fileInputStream = new FileInputStream(this.userxmlfn);
/*  562 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  564 */         String str = null;
/*  565 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  567 */         while ((str = bufferedReader.readLine()) != null) {
/*  568 */           this.userxmlSb.append(convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/*  571 */         File file = new File(this.userxmlfn);
/*  572 */         if (file.exists()) {
/*  573 */           file.delete();
/*      */         }
/*  575 */       } catch (Exception exception) {
/*  576 */         exception.printStackTrace();
/*      */       } finally {
/*  578 */         if (bufferedInputStream != null) {
/*      */           try {
/*  580 */             bufferedInputStream.close();
/*  581 */           } catch (Exception exception) {
/*  582 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  585 */         if (fileInputStream != null) {
/*      */           try {
/*  587 */             fileInputStream.close();
/*  588 */           } catch (Exception exception) {
/*  589 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  594 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/*  597 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  599 */       BufferedInputStream bufferedInputStream = null;
/*  600 */       FileInputStream fileInputStream = null;
/*  601 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  603 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  604 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  606 */         String str = null;
/*  607 */         StringBuffer stringBuffer = new StringBuffer();
/*  608 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  610 */         while ((str = bufferedReader.readLine()) != null) {
/*  611 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  613 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  616 */         File file = new File(this.dbgfn);
/*  617 */         if (file.exists()) {
/*  618 */           file.delete();
/*      */         }
/*  620 */       } catch (Exception exception) {
/*  621 */         exception.printStackTrace();
/*      */       } finally {
/*  623 */         if (bufferedInputStream != null) {
/*      */           try {
/*  625 */             bufferedInputStream.close();
/*  626 */           } catch (Exception exception) {
/*  627 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  630 */         if (fileInputStream != null) {
/*      */           try {
/*  632 */             fileInputStream.close();
/*  633 */           } catch (Exception exception) {
/*  634 */             exception.printStackTrace();
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
/*  646 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/*  647 */     Object[] arrayOfObject = { paramString2 };
/*  648 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/*  665 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Status: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Prior Status: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  676 */     MessageFormat messageFormat = new MessageFormat(str);
/*  677 */     this.args[0] = this.m_prof.getOPName();
/*  678 */     this.args[1] = this.m_prof.getRoleDescription();
/*  679 */     this.args[2] = this.m_prof.getWGName();
/*  680 */     this.args[3] = this.t2DTS;
/*  681 */     this.args[4] = this.curStatus;
/*  682 */     this.args[5] = this.t1DTS;
/*  683 */     this.args[6] = this.priorStatus;
/*  684 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  685 */     this.args[8] = this.abrversion + " " + getABRVersion();
/*      */     
/*  687 */     return messageFormat.format(this.args);
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
/*  699 */     String str = "<table>" + NEWLINE + "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{2}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {3} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  705 */     MessageFormat messageFormat = new MessageFormat(str);
/*  706 */     this.args[0] = this.t2DTS;
/*  707 */     this.args[1] = this.t1DTS;
/*  708 */     this.args[2] = this.xmlgenSb.toString();
/*  709 */     this.args[3] = this.abrversion + " " + getABRVersion();
/*      */     
/*  711 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityList getEntityListForDiff(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  721 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, paramProfile, paramString);
/*      */     
/*  723 */     EntityList entityList = this.m_db.getEntityList(paramProfile, extractActionItem, new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*  724 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */ 
/*      */     
/*  727 */     addDebug("EntityList for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  728 */         PokUtils.outputList(entityList));
/*      */     
/*  730 */     return entityList;
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
/*      */   private String getT1(XMLMQ paramXMLMQ) throws MiddlewareRequestException, MiddlewareException {
/*  763 */     String str = this.m_strEpoch;
/*  764 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  766 */     if (this.isPeriodicABR) {
/*  767 */       addDebug("getT1 entered for Periodic ABR " + entityItem.getKey());
/*      */       
/*  769 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/*  770 */       if (eANMetaAttribute == null) {
/*  771 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*      */       
/*  774 */       str = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */     } else {
/*  776 */       String str1 = paramXMLMQ.getStatusAttr();
/*  777 */       addDebug("getT1 entered for DQ ABR " + entityItem.getKey() + " " + str1 + " isSystemResend:" + this.isSystemResend);
/*  778 */       if (!this.isSystemResend) {
/*  779 */         EANAttribute eANAttribute = entityItem.getAttribute(str1);
/*  780 */         if (eANAttribute != null) {
/*  781 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*  782 */           if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */             
/*  784 */             int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1;
/*  785 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  786 */             addDebug("getT1 [" + i + "] isActive: " + attributeChangeHistoryItem
/*  787 */                 .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  788 */                 .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*      */             
/*  790 */             this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/*      */             
/*  792 */             if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/*      */               
/*  794 */               attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*  795 */               if (attributeChangeHistoryItem != null) {
/*  796 */                 this.priorStatus = attributeChangeHistoryItem.getAttributeValue();
/*  797 */                 addDebug("getT1 [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem
/*  798 */                     .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*      */ 
/*      */                 
/*  801 */                 if (!firstTimeFinalR4R(attributeChangeHistoryGroup, "0020")) {
/*      */ 
/*      */                   
/*  804 */                   str = getPrevQueuedDTS();
/*  805 */                   this.actionTaken = this.rsBundle.getString("ACTION_FINAL_CHANGES");
/*      */                 } else {
/*  807 */                   this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */                 } 
/*      */               } else {
/*  810 */                 addDebug("getT1: WARNING NO changehistory found prior to Final");
/*      */               } 
/*  812 */             } else if (attributeChangeHistoryItem.getFlagCode().equals("0040")) {
/*  813 */               attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*  814 */               if (attributeChangeHistoryItem != null) {
/*  815 */                 addDebug("getT1 [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem
/*  816 */                     .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*      */ 
/*      */                 
/*  819 */                 if (!firstTimeFinalR4R(attributeChangeHistoryGroup, "0040")) {
/*      */ 
/*      */                   
/*  822 */                   str = getPrevQueuedDTS();
/*  823 */                   this.actionTaken = this.rsBundle.getString("ACTION_R4R_CHANGES");
/*      */                 } else {
/*  825 */                   this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*      */                 } 
/*      */               } else {
/*  828 */                 addDebug("getT1: WARNING NO changehistory found prior to R4R");
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  833 */           addDebug("getT1 for " + entityItem.getKey() + " " + str);
/*      */         } else {
/*  835 */           addDebug("getT1 for " + entityItem.getKey() + " " + str1 + "  was null");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  840 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getPrevQueuedDTS() throws MiddlewareRequestException {
/*  850 */     String str1 = this.m_strEpoch;
/*  851 */     String str2 = "ADSABRSTATUS";
/*  852 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  854 */     addDebug("getPrevQueuedDTS entered for " + entityItem.getKey() + " " + str2);
/*  855 */     EANAttribute eANAttribute = entityItem.getAttribute(str2);
/*  856 */     if (eANAttribute != null) {
/*  857 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*  858 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*  859 */         boolean bool = false;
/*      */         
/*  861 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 2; i >= 0; i--) {
/*      */           
/*  863 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  864 */           addDebug("getPrevQueuedDTS [" + i + "] isActive: " + attributeChangeHistoryItem
/*  865 */               .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  866 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*  867 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/*  869 */             bool = true;
/*      */           }
/*  871 */           if (bool && attributeChangeHistoryItem.getFlagCode().equals("0020"))
/*      */           {
/*  873 */             return attributeChangeHistoryItem.getChangeDate();
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  878 */         addDebug("getPrevQueuedDTS for " + entityItem.getKey() + " " + str2 + " has no history");
/*      */       } 
/*      */     } else {
/*  881 */       addDebug("getPrevQueuedDTS for " + entityItem.getKey() + " " + str2 + " was null");
/*      */     } 
/*      */     
/*  884 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean firstTimeFinalR4R(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/*  891 */     byte b = 0;
/*  892 */     for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*      */       
/*  894 */       AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  895 */       if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  896 */         b++;
/*      */       }
/*      */     } 
/*  899 */     return (b == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getABRQueuedTime() throws MiddlewareRequestException {
/*  908 */     String str = "ADSABRSTATUS";
/*  909 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/*  911 */     addDebug("getABRQueuedTime entered for " + entityItem.getKey() + " " + str);
/*  912 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/*  913 */     if (eANAttribute != null) {
/*  914 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*  915 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 1) {
/*      */         
/*  917 */         int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 2;
/*  918 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  919 */         addDebug("getABRQueuedTime [" + i + "] isActive: " + attributeChangeHistoryItem
/*  920 */             .isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  921 */             .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*      */         
/*  923 */         return attributeChangeHistoryItem.getChangeDate();
/*      */       } 
/*      */       
/*  926 */       addDebug("getABRQueuedTime for " + entityItem.getKey() + " " + str + " has no history");
/*      */     } else {
/*      */       
/*  929 */       addDebug("getABRQueuedTime for " + entityItem.getKey() + " " + str + " was null");
/*      */     } 
/*      */     
/*  932 */     return getNow();
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
/*      */   private Profile switchRole(String paramString) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/*  946 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/*  947 */     if (profile == null) {
/*  948 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/*  950 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/*  952 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/*  953 */       addDebug("switchRole nlsids: " + str);
/*  954 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*  955 */       while (stringTokenizer.hasMoreTokens()) {
/*  956 */         String str1 = stringTokenizer.nextToken();
/*  957 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/*  958 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/*  959 */           profile.getReadLanguages().addElement(nLSItem);
/*  960 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  965 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  975 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/*  977 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/*  978 */     EANList eANList = entityGroup.getMetaAttribute();
/*  979 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/*  981 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/*  982 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/*  983 */       stringBuffer.append(" ");
/*      */     } 
/*      */     
/*  986 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/*  992 */     return this.m_db;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/*  997 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1002 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1008 */     if (this.dbgPw != null) {
/* 1009 */       this.dbgLen += paramString.length();
/* 1010 */       this.dbgPw.println(paramString);
/* 1011 */       this.dbgPw.flush();
/*      */     } else {
/* 1013 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1021 */     addOutput(paramString);
/* 1022 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/* 1029 */     return this.rsBundle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void notify(XMLMQ paramXMLMQ, String paramString1, String paramString2) throws MissingResourceException {
/* 1038 */     MessageFormat messageFormat = null;
/* 1039 */     Vector<String> vector = paramXMLMQ.getMQPropertiesFN();
/* 1040 */     byte b1 = 0;
/* 1041 */     boolean bool = false;
/*      */ 
/*      */     
/* 1044 */     for (byte b2 = 0; b2 < vector.size(); b2++) {
/* 1045 */       String str = vector.elementAt(b2);
/* 1046 */       ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/* 1047 */           getLocale(getProfile().getReadLanguage().getNLSID()));
/* 1048 */       Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 1049 */       boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/* 1050 */       hashtable.put("MQCID", paramXMLMQ.getMQCID());
/* 1051 */       if (bool1) {
/*      */         try {
/* 1053 */           MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable);
/*      */           
/* 1055 */           messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/* 1056 */           this.args[0] = str;
/* 1057 */           this.args[1] = paramString1;
/* 1058 */           addOutput(messageFormat.format(this.args));
/* 1059 */           b1++;
/* 1060 */           if (!bool)
/*      */           {
/* 1062 */             addXMLGenMsg("SUCCESS", paramString1);
/*      */           }
/* 1064 */         } catch (MQException mQException) {
/*      */ 
/*      */           
/* 1067 */           addXMLGenMsg("FAILED", paramString1);
/* 1068 */           bool = true;
/* 1069 */           messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/* 1070 */           this.args[0] = str + " " + paramString1;
/* 1071 */           this.args[1] = "" + mQException.completionCode;
/* 1072 */           this.args[2] = "" + mQException.reasonCode;
/* 1073 */           addError(messageFormat.format(this.args));
/* 1074 */           mQException.printStackTrace(System.out);
/* 1075 */         } catch (IOException iOException) {
/*      */           
/* 1077 */           addXMLGenMsg("FAILED", paramString1);
/* 1078 */           bool = true;
/* 1079 */           messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/* 1080 */           this.args[0] = str + " " + paramString1;
/* 1081 */           this.args[1] = iOException.toString();
/* 1082 */           addError(messageFormat.format(this.args));
/* 1083 */           iOException.printStackTrace(System.out);
/*      */         } 
/*      */       } else {
/*      */         
/* 1087 */         messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/* 1088 */         this.args[0] = str;
/* 1089 */         addError(messageFormat.format(this.args));
/*      */         
/* 1091 */         addXMLGenMsg("NOT_SENT", paramString1);
/*      */       } 
/*      */     } 
/*      */     
/* 1095 */     if (b1 > 0 && b1 != vector.size()) {
/* 1096 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
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
/* 1108 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 1109 */     Transformer transformer = transformerFactory.newTransformer();
/* 1110 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 1112 */     transformer.setOutputProperty("indent", "no");
/* 1113 */     transformer.setOutputProperty("method", "xml");
/* 1114 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 1117 */     StringWriter stringWriter = new StringWriter();
/* 1118 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 1119 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 1120 */     transformer.transform(dOMSource, streamResult);
/* 1121 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */     
/* 1124 */     transformer.setOutputProperty("indent", "yes");
/* 1125 */     stringWriter = new StringWriter();
/* 1126 */     streamResult = new StreamResult(stringWriter);
/* 1127 */     transformer.transform(dOMSource, streamResult);
/* 1128 */     addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     
/* 1130 */     return str;
/*      */   }
/*      */   protected void addUserXML(String paramString) {
/* 1133 */     if (this.userxmlPw != null) {
/* 1134 */       this.userxmlLen += paramString.length();
/* 1135 */       this.userxmlPw.println(paramString);
/* 1136 */       this.userxmlPw.flush();
/*      */     } else {
/* 1138 */       this.userxmlSb.append(convertToHTML(paramString) + NEWLINE);
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
/* 1152 */     boolean bool = false;
/* 1153 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 1154 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 1155 */     if (str.equals("all")) {
/* 1156 */       bool = true;
/*      */     } else {
/* 1158 */       HashSet<String> hashSet = new HashSet();
/* 1159 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1160 */       while (stringTokenizer.hasMoreTokens()) {
/* 1161 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 1163 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 1164 */       hashSet.clear();
/*      */     } 
/*      */     
/* 1167 */     if (!bool) {
/* 1168 */       addDebug("PDHDOMAIN for " + paramEntityItem.getKey() + " did not include " + str + ", execution is bypassed [" + 
/* 1169 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/* 1171 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale getLocale(int paramInt) {
/* 1181 */     Locale locale = null;
/* 1182 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 1185 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1209 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1219 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1228 */     return "ADSABRSTATUS";
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
/* 1239 */     String str = "";
/* 1240 */     StringBuffer stringBuffer = new StringBuffer();
/* 1241 */     StringCharacterIterator stringCharacterIterator = null;
/* 1242 */     char c = ' ';
/* 1243 */     if (paramString != null) {
/* 1244 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1245 */       c = stringCharacterIterator.first();
/* 1246 */       while (c != '￿') {
/*      */         
/* 1248 */         switch (c) {
/*      */           
/*      */           case '<':
/* 1251 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 1254 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/* 1260 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */           default:
/* 1263 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1266 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1268 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1271 */     return str;
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
/* 1289 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 1290 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/* 1291 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 1294 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1295 */     if (eANMetaAttribute == null) {
/* 1296 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/* 1297 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + entityItem
/* 1298 */           .getEntityType() + ", nothing to do");
/*      */       return;
/*      */     } 
/* 1301 */     if (paramString2 != null)
/*      */     {
/* 1303 */       if (paramString2.equals(getAttributeFlagEnabledValue(entityItem, paramString1))) {
/* 1304 */         addDebug("setFlagValue " + entityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/* 1309 */           if (this.m_cbOn == null) {
/* 1310 */             setControlBlock();
/*      */           }
/* 1312 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*      */           
/* 1314 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */           
/* 1316 */           Vector<SingleFlag> vector = new Vector();
/* 1317 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 1318 */           vector.addElement(singleFlag);
/* 1319 */           returnEntityKey.m_vctAttributes = vector;
/* 1320 */           vector1.addElement(returnEntityKey);
/*      */           
/* 1322 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 1323 */           addDebug(entityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*      */         } finally {
/*      */           
/* 1326 */           this.m_db.commit();
/* 1327 */           this.m_db.freeStatement();
/* 1328 */           this.m_db.isPending("finally after update in setflag value");
/*      */         } 
/*      */       }  } 
/*      */   }
/*      */   
/*      */   private String sysFeedResendStatus(String paramString1, String paramString2, String paramString3) {
/* 1334 */     return ABRServerProperties.getValue(paramString1, paramString2, paramString3);
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\ADSABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */