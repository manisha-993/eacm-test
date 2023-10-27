/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.XMLElem;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.DeleteActionItem;
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
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
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
/*      */ import COM.ibm.opicmpdh.transactions.NLSItem;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class COMPATGENABR
/*      */   extends ADSABRSTATUS
/*      */ {
/*  100 */   private StringBuffer rptSb = new StringBuffer();
/*      */   
/*  102 */   private StringBuffer xmlgenSb = new StringBuffer();
/*  103 */   private PrintWriter dbgPw = null;
/*  104 */   private PrintWriter userxmlPw = null;
/*  105 */   private String dbgfn = null;
/*  106 */   private String userxmlfn = null;
/*  107 */   private int userxmlLen = 0;
/*  108 */   private int dbgLen = 0;
/*  109 */   private ResourceBundle rsBundle = null;
/*  110 */   private String priorStatus = "&nbsp;";
/*  111 */   private String curStatus = "&nbsp;";
/*      */   
/*      */   private boolean isPeriodicABR = false;
/*      */   
/*      */   private boolean isXMLIDLABR = false;
/*      */   private boolean RFRPassedFinal = false;
/*  117 */   private String actionTaken = "";
/*  118 */   private static Hashtable CompatABR_TBL = new Hashtable<>();
/*      */   
/*      */   private static final String MODELCG_DELETEACTION_NAME = "DELMODELCG";
/*      */   private static final String SEOCG_DELETEACTION_NAME = "DELSEOCG";
/*      */   private static final String MDLCGOSMDL_DELETEACTION_NAME = "DELMDLCGOSMDL";
/*      */   private static final String SEOCGOSSEO_DELETEACTION_NAME = "DELSEOCGOSSEO";
/*      */   private static final String SEOCGOSBDL_DELETEACTION_NAME = "DELSEOCGOSBDL";
/*      */   private static final String SEOCGOSSVCSEO_DELETEACTION_NAME = "DELSEOCGOSSVCSEO";
/*      */   
/*      */   static {
/*  128 */     CompatABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMODABR");
/*  129 */     CompatABR_TBL.put("MODELCG", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMODCGABR");
/*  130 */     CompatABR_TBL.put("MODELCGOS", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMODCGOSABR");
/*  131 */     CompatABR_TBL.put("MDLCGOSMDL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATMDLCGOSMDLABR");
/*  132 */     CompatABR_TBL.put("SEOCG", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGABR");
/*  133 */     CompatABR_TBL.put("SEOCGOS", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSABR");
/*  134 */     CompatABR_TBL.put("SEOCGOSBDL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSBDLABR");
/*  135 */     CompatABR_TBL.put("SEOCGOSSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSSEOABR");
/*  136 */     CompatABR_TBL.put("SEOCGOSSVCSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATSEOCGOSSVCSEOABR");
/*  137 */     CompatABR_TBL.put("WWSEO", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATWWSEOABR");
/*  138 */     CompatABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.sg.adsxmlbh1.WWCOMPATLSEOBUNDLEABR");
/*      */   }
/*      */   
/*  141 */   protected static final String[][] COMPATVE_Filter_Array = new String[][] { { "ADSWWCOMPATMOD", "MODELCG", "RFR Final" }, { "ADSWWCOMPATMOD", "MODELCGOS", "RFR Final" }, { "ADSWWCOMPATMOD", "MDLCGOSMDL", "RFR Final" }, { "ADSWWCOMPATMOD", "MODEL", "RFR Final" }, { "ADSWWCOMPATMOD", "SEOCG", "RFR Final" }, { "ADSWWCOMPATMOD", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATMOD", "SEOCGOSSEO", "RFR Final" }, { "ADSWWCOMPATMOD", "SEOCGOSBDL", "RFR Final" }, { "ADSWWCOMPATMOD", "SEOCGOSSVCSEO", "RFR Final" }, { "ADSWWCOMPATMOD", "WWSEO", "RFR Final" }, { "ADSWWCOMPATMOD", "LSEOBUNDLE", "RFR Final" }, { "ADSWWCOMPATMOD", "SVCSEO", "RFR Final" }, { "ADSWWCOMPATMODCG", "MODEL", "RFR Final" }, { "ADSWWCOMPATMODCG", "MODELCGOS", "RFR Final" }, { "ADSWWCOMPATMODCG", "MDLCGOSMDL", "RFR Final" }, { "ADSWWCOMPATMODCGOS", "MODEL", "RFR Final" }, { "ADSWWCOMPATMODCGOS", "MODELCG", "RFR Final" }, { "ADSWWCOMPATMODCGOS", "MDLCGOSMDL", "RFR Final" }, { "ADSWWCOMPATMDLCGOSMDL1", "MODELCGOS", "RFR Final" }, { "ADSWWCOMPATMDLCGOSMDL1", "MODEL", "RFR Final" }, { "ADSWWCOMPATMDLCGOSMDL2", "MODEL", "RFR Final" }, { "ADSWWCOMPATMDLCGOSMDL2", "MODELCG", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "SEOCG", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "SEOCGOSSEO", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "SEOCGOSBDL", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "SEOCGOSSVCSEO", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "WWSEO", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "LSEOBUNDEL", "RFR Final" }, { "ADSWWCOMPATWWSEO1", "SVCSEO", "RFR Final" }, { "ADSWWCOMPATWWSEO2", "SEOCGOSSEO", "RFR Final" }, { "ADSWWCOMPATWWSEO2", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATWWSEO2", "SEOCG", "RFR Final" }, { "ADSWWCOMPATWWSEO2", "WWSEO", "RFR Final" }, { "ADSWWCOMPATWWSEO2", "MODEL", "RFR Final" }, { "ADSWWCOMPATWWSEO2", "LSEOBUNDEL", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "SEOCG", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "SEOCGOSSEO", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "SEOCGOSBDL", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "SEOCGOSSVCSEO", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "WWSEO", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "LSEOBUNDEL", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE1", "SVCSEO", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE2", "SEOCGOSSEO", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE2", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE2", "SEOCG", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE2", "WWSEO", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE2", "MODEL", "RFR Final" }, { "ADSWWCOMPATLSEOBUNDLE2", "LSEOBUNDEL", "RFR Final" }, { "ADSWWCOMPATSEOCG", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATSEOCG", "SEOCGOSSEO", "RFR Final" }, { "ADSWWCOMPATSEOCG", "SEOCGOSBDL", "RFR Final" }, { "ADSWWCOMPATSEOCG", "SEOCGOSSVCSEO", "RFR Final" }, { "ADSWWCOMPATSEOCG", "MODEL", "RFR Final" }, { "ADSWWCOMPATSEOCG", "LSEOBUNDEL", "RFR Final" }, { "ADSWWCOMPATSEOCG", "WWSEO", "RFR Final" }, { "ADSWWCOMPATSEOCG", "SVCSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "SEOCG", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "SEOCGOSSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "SEOCGOSBDL", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "SEOCGOSSVCSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "MODEL", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "LSEOBUNDEL", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "WWSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOS", "SVCSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOSBDL", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATSEOCGOSBDL", "LSEOBUNDEL", "RFR Final" }, { "ADSWWCOMPATSEOCGOSSEO", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATSEOCGOSSEO", "WWSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOSSVCSEO", "SEOCGOS", "RFR Final" }, { "ADSWWCOMPATSEOCGOSSVCSEO", "SVCSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOS2", "SEOCG", "RFR Final" }, { "ADSWWCOMPATSEOCGOS2", "WWSEO", "RFR Final" }, { "ADSWWCOMPATSEOCGOS2", "MODEL", "RFR Final" }, { "ADSWWCOMPATSEOCGOS2", "LSEOBUNDEL", "RFR Final" } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  237 */   protected static final Hashtable COMPATITEM_STATUS_ATTR_TBL = new Hashtable<>(); static {
/*  238 */     COMPATITEM_STATUS_ATTR_TBL.put("MODEL", "STATUS");
/*  239 */     COMPATITEM_STATUS_ATTR_TBL.put("MODELCG", "STATUS");
/*  240 */     COMPATITEM_STATUS_ATTR_TBL.put("MODELCGOS", "STATUS");
/*  241 */     COMPATITEM_STATUS_ATTR_TBL.put("MDLCGOSMDL", "STATUS");
/*  242 */     COMPATITEM_STATUS_ATTR_TBL.put("WWSEO", "STATUS");
/*  243 */     COMPATITEM_STATUS_ATTR_TBL.put("LSEOBUNDLE", "STATUS");
/*  244 */     COMPATITEM_STATUS_ATTR_TBL.put("SEOCG", "STATUS");
/*  245 */     COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOS", "STATUS");
/*  246 */     COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOSSEO", "STATUS");
/*  247 */     COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOSBDL", "STATUS");
/*  248 */     COMPATITEM_STATUS_ATTR_TBL.put("SEOCGOSSVCSEO", "STATUS");
/*      */   }
/*  250 */   private Object[] args = (Object[])new String[10];
/*  251 */   private String abrversion = "";
/*  252 */   private String t2DTS = "&nbsp;";
/*  253 */   private String t1DTS = "&nbsp;";
/*  254 */   private StringBuffer userxmlSb = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  288 */     String str = (String)CompatABR_TBL.get(paramString);
/*  289 */     addDebug("creating instance of CompatABR_TBL  = '" + str + "' for " + paramString);
/*  290 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  299 */     String str1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  306 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  311 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  314 */       long l = System.currentTimeMillis();
/*      */       
/*  316 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  319 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  322 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  328 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  330 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*      */       try {
/*  334 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */         
/*  336 */         this.isPeriodicABR = getEntityType().equals("ADSXMLSETUP");
/*      */         
/*  338 */         String str6 = getEntityType();
/*      */         
/*  340 */         String str7 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*  341 */         String str8 = PokUtils.getAttributeFlagValue(entityItem, "ADSENTITY");
/*      */         
/*  343 */         if (this.isPeriodicABR) {
/*      */           
/*  345 */           if (str7 != null) {
/*  346 */             str6 = (String)ADSTYPES_TBL.get(str7);
/*      */           }
/*  348 */           if ("20".equals(str8)) {
/*  349 */             str6 = "DEL" + str6;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  355 */         String str9 = getSimpleABRName(str6);
/*  356 */         if (str9 != null) {
/*  357 */           boolean bool = true;
/*  358 */           XMLMQ xMLMQ = (XMLMQ)Class.forName(str9).newInstance();
/*      */           
/*  360 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  367 */           if (!this.isPeriodicABR) {
/*  368 */             String str10 = xMLMQ.getStatusAttr();
/*      */             
/*  370 */             String str11 = getAttributeFlagEnabledValue(entityItem, str10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  377 */             if (!"0020".equals(str11) && !"0040".equals(str11)) {
/*  378 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */               
/*  380 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */             } else {
/*  382 */               bool = xMLMQ.createXML(entityItem);
/*      */               
/*  384 */               if (!bool) {
/*  385 */                 addDebug(entityItem.getKey() + " will not have XML generated, createXML=false");
/*      */               }
/*      */             } 
/*      */           } else {
/*      */             
/*  390 */             addDebug("execute: periodic " + entityItem.getKey());
/*      */           } 
/*      */           
/*  393 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = null;
/*      */ 
/*      */           
/*  396 */           attributeChangeHistoryGroup1 = getADSABRSTATUSHistory();
/*      */           
/*  398 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(xMLMQ);
/*  399 */           setT2DTS(entityItem, xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, "@@");
/*  400 */           setT1DTS(xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, "@@");
/*      */           
/*  402 */           if (getReturnCode() == 0 && bool) {
/*      */ 
/*      */             
/*  405 */             Profile profile = switchRole(xMLMQ.getRoleCode());
/*  406 */             if (profile != null) {
/*  407 */               profile.setValOnEffOn(this.t2DTS, this.t2DTS);
/*  408 */               profile.setEndOfDay(this.t2DTS);
/*  409 */               profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  411 */               Profile profile1 = profile.getNewInstance(this.m_db);
/*  412 */               profile1.setValOnEffOn(this.t1DTS, this.t1DTS);
/*  413 */               profile1.setEndOfDay(this.t2DTS);
/*  414 */               profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  416 */               String str = "";
/*      */               try {
/*  418 */                 if (this.isPeriodicABR) {
/*      */                   
/*  420 */                   String str10 = "";
/*  421 */                   if (str7 != null) {
/*  422 */                     str10 = (String)ADSTYPES_TBL.get(str7);
/*      */                   }
/*  424 */                   str = "Periodic " + str10;
/*  425 */                   if ("20".equals(str8)) {
/*  426 */                     str = "Deleted " + str10;
/*      */                   }
/*  428 */                   setupPrintWriters();
/*  429 */                   xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                 } else {
/*  431 */                   str = entityItem.getKey();
/*      */                   
/*  433 */                   if (domainNeedsChecks(entityItem)) {
/*  434 */                     if (!this.RFRPassedFinal) {
/*  435 */                       xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                       
/*  437 */                       if (isDeactivatedEntity(entityItem)) {
/*  438 */                         deactivateEntities(entityItem);
/*      */                       }
/*      */                     } 
/*      */                   } else {
/*  442 */                     addXMLGenMsg("DOMAIN_NOT_LISTED", str);
/*      */                   } 
/*      */                 } 
/*  445 */               } catch (IOException iOException) {
/*      */ 
/*      */                 
/*  448 */                 MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("REQ_ERROR"));
/*  449 */                 this.args[0] = iOException.getMessage();
/*  450 */                 addError(messageFormat1.format(this.args));
/*  451 */                 addXMLGenMsg("FAILED", str);
/*  452 */               } catch (SQLException sQLException) {
/*  453 */                 addXMLGenMsg("FAILED", str);
/*  454 */                 throw sQLException;
/*  455 */               } catch (MiddlewareRequestException middlewareRequestException) {
/*  456 */                 addXMLGenMsg("FAILED", str);
/*  457 */                 throw middlewareRequestException;
/*  458 */               } catch (MiddlewareException middlewareException) {
/*  459 */                 addXMLGenMsg("FAILED", str);
/*  460 */                 throw middlewareException;
/*  461 */               } catch (ParserConfigurationException parserConfigurationException) {
/*  462 */                 addXMLGenMsg("FAILED", str);
/*  463 */                 throw parserConfigurationException;
/*  464 */               } catch (TransformerException transformerException) {
/*  465 */                 addXMLGenMsg("FAILED", str);
/*  466 */                 throw transformerException;
/*  467 */               } catch (MissingResourceException missingResourceException) {
/*  468 */                 addXMLGenMsg("FAILED", str);
/*  469 */                 throw missingResourceException;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*  474 */           addError(getShortClassName(getClass()) + " does not support " + str6);
/*      */         } 
/*      */         
/*  477 */         str1 = getNavigationName(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  483 */         if (this.isPeriodicABR && !this.isXMLIDLABR && getReturnCode() == 0) {
/*  484 */           PDGUtility pDGUtility = new PDGUtility();
/*  485 */           OPICMList oPICMList = new OPICMList();
/*  486 */           oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/*  487 */           pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */         } 
/*      */         
/*  490 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*  491 */       } catch (Exception exception) {
/*  492 */         throw exception;
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/*  497 */         if (this.isXMLIDLABR) {
/*  498 */           deactivateMultiFlagValue("XMLABRPROPFILE");
/*      */         }
/*      */       }
/*      */     
/*  502 */     } catch (Throwable throwable) {
/*  503 */       StringWriter stringWriter = new StringWriter();
/*  504 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  505 */       String str7 = "<pre>{0}</pre>";
/*  506 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  507 */       setReturnCode(-3);
/*  508 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  510 */       this.args[0] = throwable.getMessage();
/*  511 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  512 */       messageFormat1 = new MessageFormat(str7);
/*  513 */       this.args[0] = stringWriter.getBuffer().toString();
/*  514 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  515 */       logError("Exception: " + throwable.getMessage());
/*  516 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  520 */       setDGTitle(str1);
/*  521 */       setDGRptName(getShortClassName(getClass()));
/*  522 */       setDGRptClass(getABRCode());
/*      */       
/*  524 */       if (!isReadOnly()) {
/*  525 */         clearSoftLock();
/*      */       }
/*  527 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  532 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  533 */     this.args[0] = getShortClassName(getClass());
/*  534 */     this.args[1] = str1;
/*  535 */     String str3 = messageFormat.format(this.args);
/*      */     
/*  537 */     String str4 = null;
/*  538 */     if (this.isPeriodicABR) {
/*  539 */       str4 = buildPeriodicRptHeader();
/*  540 */       restoreXtraContent();
/*      */     } else {
/*  542 */       str4 = buildDQTriggeredRptHeader();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  548 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  549 */     this.rptSb.insert(0, str5);
/*      */     
/*  551 */     println(this.rptSb.toString());
/*  552 */     printDGSubmitString();
/*  553 */     println(EACustom.getTOUDiv());
/*  554 */     buildReportFooter();
/*      */   }
/*      */   
/*      */   private void setupPrintWriters() {
/*  558 */     String str = this.m_abri.getFileName();
/*  559 */     int i = str.lastIndexOf(".");
/*  560 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*  561 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/*  563 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  564 */     } catch (Exception exception) {
/*  565 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/*  568 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/*  569 */     } catch (Exception exception) {
/*  570 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriters() {
/*  574 */     if (this.dbgPw != null) {
/*  575 */       this.dbgPw.flush();
/*  576 */       this.dbgPw.close();
/*  577 */       this.dbgPw = null;
/*      */     } 
/*  579 */     if (this.userxmlPw != null) {
/*  580 */       this.userxmlPw.flush();
/*  581 */       this.userxmlPw.close();
/*  582 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  588 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/*  590 */       BufferedInputStream bufferedInputStream = null;
/*  591 */       FileInputStream fileInputStream = null;
/*  592 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  594 */         fileInputStream = new FileInputStream(this.userxmlfn);
/*  595 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  597 */         String str = null;
/*  598 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  600 */         while ((str = bufferedReader.readLine()) != null) {
/*  601 */           this.userxmlSb.append(convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/*  604 */         File file = new File(this.userxmlfn);
/*  605 */         if (file.exists()) {
/*  606 */           file.delete();
/*      */         }
/*  608 */       } catch (Exception exception) {
/*  609 */         exception.printStackTrace();
/*      */       } finally {
/*  611 */         if (bufferedInputStream != null) {
/*      */           try {
/*  613 */             bufferedInputStream.close();
/*  614 */           } catch (Exception exception) {
/*  615 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  618 */         if (fileInputStream != null) {
/*      */           try {
/*  620 */             fileInputStream.close();
/*  621 */           } catch (Exception exception) {
/*  622 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  627 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/*  630 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  632 */       BufferedInputStream bufferedInputStream = null;
/*  633 */       FileInputStream fileInputStream = null;
/*  634 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  636 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  637 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  639 */         String str = null;
/*  640 */         StringBuffer stringBuffer = new StringBuffer();
/*  641 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  643 */         while ((str = bufferedReader.readLine()) != null) {
/*  644 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  646 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  649 */         File file = new File(this.dbgfn);
/*  650 */         if (file.exists()) {
/*  651 */           file.delete();
/*      */         }
/*  653 */       } catch (Exception exception) {
/*  654 */         exception.printStackTrace();
/*      */       } finally {
/*  656 */         if (bufferedInputStream != null) {
/*      */           try {
/*  658 */             bufferedInputStream.close();
/*  659 */           } catch (Exception exception) {
/*  660 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  663 */         if (fileInputStream != null) {
/*      */           try {
/*  665 */             fileInputStream.close();
/*  666 */           } catch (Exception exception) {
/*  667 */             exception.printStackTrace();
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
/*  679 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/*  680 */     Object[] arrayOfObject = { paramString2 };
/*  681 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/*  698 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Status: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Prior Status: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  709 */     MessageFormat messageFormat = new MessageFormat(str);
/*  710 */     this.args[0] = this.m_prof.getOPName();
/*  711 */     this.args[1] = this.m_prof.getRoleDescription();
/*  712 */     this.args[2] = this.m_prof.getWGName();
/*  713 */     this.args[3] = this.t2DTS;
/*  714 */     this.args[4] = this.curStatus;
/*  715 */     this.args[5] = this.t1DTS;
/*  716 */     this.args[6] = this.priorStatus;
/*  717 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  718 */     this.args[8] = this.abrversion + " " + getABRVersion();
/*      */     
/*  720 */     return messageFormat.format(this.args);
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
/*  732 */     String str = "<table>" + NEWLINE + "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{2}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {3} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  738 */     MessageFormat messageFormat = new MessageFormat(str);
/*  739 */     this.args[0] = this.t2DTS;
/*  740 */     this.args[1] = this.t1DTS;
/*  741 */     this.args[2] = this.xmlgenSb.toString();
/*  742 */     this.args[3] = this.abrversion + " " + getABRVersion();
/*      */     
/*  744 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityList getEntityListForDiff(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  754 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, paramProfile, paramString);
/*      */     
/*  756 */     EntityList entityList = this.m_db.getEntityList(paramProfile, extractActionItem, new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*  757 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */ 
/*      */     
/*  760 */     addDebug("EntityList for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  761 */         PokUtils.outputList(entityList));
/*      */ 
/*      */     
/*  764 */     if (isVEFiltered(paramString)) {
/*      */       
/*  766 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  767 */       String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  768 */       addDebug("The status of the root for VE " + paramString + " is: " + str);
/*      */ 
/*      */ 
/*      */       
/*  772 */       for (byte b = 0; b < COMPATVE_Filter_Array.length; b++) {
/*      */ 
/*      */         
/*  775 */         if (COMPATVE_Filter_Array[b][0].equals(paramString)) {
/*  776 */           EntityGroup entityGroup = entityList.getEntityGroup(COMPATVE_Filter_Array[b][1]);
/*  777 */           addDebug("Found " + entityList.getEntityGroup(COMPATVE_Filter_Array[b][1]));
/*      */ 
/*      */           
/*  780 */           if (entityGroup != null) {
/*      */ 
/*      */             
/*  783 */             EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */ 
/*      */             
/*  786 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/*  788 */               String str1 = null;
/*  789 */               boolean bool = true;
/*  790 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/*  791 */               String str2 = entityItem1.getEntityType();
/*      */               
/*  793 */               addDebug("Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */ 
/*      */               
/*  796 */               String str3 = COMPATVE_Filter_Array[b][2];
/*      */ 
/*      */               
/*  799 */               str1 = PokUtils.getAttributeFlagValue(entityItem1, (String)COMPATITEM_STATUS_ATTR_TBL.get(str2));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  807 */               addDebug((String)COMPATITEM_STATUS_ATTR_TBL.get(str2) + " is " + str1);
/*  808 */               if (str1 == null) { bool = false; }
/*  809 */               else if (str1.equals("0020")) { bool = false; }
/*  810 */               else if (str1.equals("0040") && (str.equals("0040") || str3
/*  811 */                 .equals("RFR Final"))) { bool = false; }
/*      */               
/*  813 */               if (bool == true) {
/*      */                 
/*  815 */                 addDebug("Removing " + str2 + " " + entityItem1.getEntityID() + " " + str1 + " from list");
/*  816 */                 addDebug("Filter criteria is " + str3);
/*  817 */                 removeItem(entityGroup, entityItem1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  825 */       addDebug("EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  826 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  831 */     if (isVECountryFiltered(paramString)) {
/*      */       
/*  833 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  834 */       String str1 = entityItem.getEntityType();
/*  835 */       String str2 = (String)ITEM_COUNTRY_ATTR_TBL.get(str1);
/*      */ 
/*      */       
/*  838 */       HashSet hashSet = getCountry(entityItem, str2);
/*      */ 
/*      */ 
/*      */       
/*  842 */       for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/*  843 */         addDebug("Looking at VE_Country_Filter_Array " + VE_Country_Filter_Array[b][0] + " " + VE_Country_Filter_Array[b][1]);
/*      */         
/*  845 */         if (VE_Country_Filter_Array[b][0].equals(paramString)) {
/*  846 */           EntityGroup entityGroup = entityList.getEntityGroup(VE_Country_Filter_Array[b][1]);
/*  847 */           addDebug("Found " + entityList.getEntityGroup(VE_Country_Filter_Array[b][1]));
/*      */ 
/*      */           
/*  850 */           if (entityGroup != null) {
/*      */ 
/*      */             
/*  853 */             EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */ 
/*      */             
/*  856 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/*  858 */               boolean bool = true;
/*  859 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/*  860 */               String str3 = entityItem1.getEntityType();
/*      */               
/*  862 */               addDebug("Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */               
/*  864 */               String str4 = (String)ITEM_COUNTRY_ATTR_TBL.get(str3);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  869 */               HashSet hashSet1 = getCountry(entityItem1, str4);
/*  870 */               Iterator<String> iterator = hashSet.iterator();
/*  871 */               while (iterator.hasNext() && bool == true) {
/*  872 */                 String str = iterator.next();
/*  873 */                 if (hashSet1.contains(str)) {
/*  874 */                   bool = false;
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/*  879 */               if (bool == true)
/*      */               {
/*  881 */                 removeItem(entityGroup, entityItem1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  889 */       addDebug("EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  890 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  895 */     return entityList;
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
/*  952 */     this.t1DTS = this.m_strEpoch;
/*  953 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  954 */     if (this.isPeriodicABR && !this.isXMLIDLABR) {
/*  955 */       addDebug("getT1 entered for Periodic ABR " + entityItem.getKey());
/*      */       
/*  957 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/*  958 */       if (eANMetaAttribute == null) {
/*  959 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*      */       
/*  962 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */ 
/*      */     
/*      */     }
/*  966 */     else if (!this.isXMLIDLABR) {
/*      */       
/*  968 */       String str = getT2Status(paramAttributeChangeHistoryGroup2);
/*      */       
/*  970 */       if (existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/*      */         
/*  972 */         if (str.equals("0040")) {
/*  973 */           this.t1DTS = getTQRFR(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/*  974 */           if (this.t1DTS.equals(this.m_strEpoch)) {
/*  975 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*  976 */           } else if (this.RFRPassedFinal != true) {
/*  977 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_CHANGES");
/*      */           }
/*      */         
/*      */         }
/*  981 */         else if (str.equals("0020")) {
/*  982 */           this.t1DTS = getTQFinal(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/*  983 */           if (this.t1DTS.equals(this.m_strEpoch)) {
/*  984 */             this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */           } else {
/*  986 */             this.actionTaken = this.rsBundle.getString("ACTION_FINAL_CHANGES");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  991 */         if (str.equals("0040")) {
/*  992 */           this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*  993 */         } else if (str.equals("0020")) {
/*  994 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */         } 
/*  996 */         addDebug("getT1 for " + entityItem.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/* 1006 */     if (paramAttributeChangeHistoryGroup != null) {
/* 1007 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1008 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1009 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1010 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1015 */     return false;
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
/*      */   private void setT2DTS(EntityItem paramEntityItem, XMLMQ paramXMLMQ, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) throws MiddlewareException {
/* 1030 */     addDebug("getT2 entered for The ADS ABR handles this as an IDL:" + this.isXMLIDLABR);
/* 1031 */     if (!this.isXMLIDLABR) {
/* 1032 */       if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/*      */         
/* 1034 */         int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount();
/*      */ 
/*      */         
/* 1037 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 2);
/* 1038 */         if (attributeChangeHistoryItem != null) {
/* 1039 */           addDebug("getT2Time [" + (i - 2) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1040 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1041 */           if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1042 */             this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */           } else {
/*      */             
/* 1045 */             addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
/*      */             
/* 1047 */             this.t2DTS = getNow();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1054 */         attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 3);
/* 1055 */         if (attributeChangeHistoryItem != null) {
/* 1056 */           addDebug("getT2Time [" + (i - 3) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/* 1057 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1058 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1059 */             this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */           } else {
/*      */             
/* 1062 */             addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not &DTFS " + paramString + " return valfrom of queued.");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/* 1067 */         this.t2DTS = getNow();
/* 1068 */         addDebug("getT2Time for COMPATGENABRSTATUS changedHistoryGroup has no history, set getNow to t2DTS");
/*      */       } 
/*      */     } else {
/* 1071 */       EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("STATUS");
/* 1072 */       if (eANMetaAttribute != null) {
/* 1073 */         if (existBefore(paramAttributeChangeHistoryGroup2, "0020")) {
/* 1074 */           for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1075 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/* 1076 */             if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1077 */               this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/* 1078 */               this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1079 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/*      */               
/* 1081 */               if (attributeChangeHistoryItem1 != null) {
/* 1082 */                 this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/* 1083 */                 addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/* 1084 */                     .getFlagCode());
/*      */               } 
/*      */               break;
/*      */             } 
/*      */           } 
/* 1089 */         } else if (existBefore(paramAttributeChangeHistoryGroup2, "0040")) {
/* 1090 */           for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1091 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/* 1092 */             if (attributeChangeHistoryItem.getFlagCode().equals("0040")) {
/* 1093 */               this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/* 1094 */               this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1095 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/*      */               
/* 1097 */               if (attributeChangeHistoryItem1 != null) {
/* 1098 */                 this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/* 1099 */                 addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/* 1100 */                     .getFlagCode());
/*      */               } 
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/* 1106 */           addError(this.rsBundle.getString("IDL_NOT_R4RFINAL"));
/* 1107 */           addDebug("getT2Time for IDL ABR, the Status never being RFR or Final");
/*      */         } 
/*      */       } else {
/* 1110 */         this.t2DTS = getNow();
/* 1111 */         addDebug(paramEntityItem.getKey() + " , There is not such attribute STATUS, set t2DTS is getNow().");
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
/*      */   private String getT2Status(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup) throws MiddlewareRequestException {
/* 1125 */     String str = "";
/* 1126 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1127 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 1129 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1130 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1131 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */ 
/*      */           
/* 1135 */           if (attributeChangeHistoryItem.getChangeDate().compareTo(this.t2DTS) < 0) {
/*      */             
/* 1137 */             if (!"0020".equals(attributeChangeHistoryItem.getFlagCode()) && !"0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/* 1138 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/* 1139 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */               break;
/*      */             } 
/* 1142 */             this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1143 */             str = attributeChangeHistoryItem.getFlagCode();
/* 1144 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */             
/* 1146 */             if (attributeChangeHistoryItem != null) {
/* 1147 */               this.priorStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1148 */               addDebug("getT2Status [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem.getChangeDate() + " flagcode: " + attributeChangeHistoryItem
/* 1149 */                   .getFlagCode());
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 1157 */       addDebug("getT2Status for " + entityItem.getKey() + " getChangeHistoryItemCount less than 0.");
/*      */     } 
/* 1159 */     return str;
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
/*      */   private String getTQFinal(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) throws MiddlewareRequestException {
/* 1174 */     String str = this.m_strEpoch;
/* 1175 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/* 1176 */       boolean bool = false;
/*      */       
/* 1178 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/* 1179 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 1180 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */           
/* 1183 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 1185 */             bool = true;
/*      */           }
/* 1187 */           if (bool && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1188 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 1190 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/* 1192 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1193 */               str = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/* 1195 */               addDebug("getPreveTQFinalDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/*      */ 
/*      */             
/* 1199 */             String str1 = getTQStatus(paramAttributeChangeHistoryGroup2, str);
/* 1200 */             if (str1.equals("0020")) {
/*      */               break;
/*      */             }
/* 1203 */             bool = false;
/* 1204 */             str = this.m_strEpoch;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1210 */       addDebug("getTQFinalDTS for COMPATGENABRSTATUS has no changed history");
/*      */     } 
/* 1212 */     return str;
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
/* 1226 */     String str1 = this.m_strEpoch;
/* 1227 */     String str2 = this.m_strEpoch;
/*      */     
/* 1229 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/* 1230 */       boolean bool1 = false;
/* 1231 */       boolean bool2 = false;
/*      */       
/* 1233 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/* 1234 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 1235 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */           
/* 1238 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 1240 */             bool1 = true;
/*      */           }
/* 1242 */           if (bool1 && attributeChangeHistoryItem.getFlagCode().equals("0020"))
/*      */           {
/* 1244 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 1246 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/* 1248 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1249 */               str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/* 1251 */               addDebug("getPreveTQRFRDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/* 1253 */             if (!bool2) {
/* 1254 */               bool2 = true;
/* 1255 */               str2 = str1;
/*      */             } 
/*      */ 
/*      */             
/* 1259 */             String str = getTQStatus(paramAttributeChangeHistoryGroup2, str1);
/* 1260 */             if (str.equals("0020")) {
/* 1261 */               this.RFRPassedFinal = true;
/* 1262 */               this.actionTaken = this.rsBundle.getString("ACTION_R4R_PASSEDFINAL");
/* 1263 */               return str1;
/*      */             } 
/* 1265 */             bool1 = false;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1272 */       addDebug("getTQRFRDTS for COMPATGENABRSTATUS has no changed history");
/*      */     } 
/* 1274 */     return str2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTQStatus(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws MiddlewareRequestException {
/* 1280 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 1282 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1283 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1284 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 1287 */           if (paramString.compareTo(attributeChangeHistoryItem.getChangeDate()) > 0) {
/* 1288 */             return attributeChangeHistoryItem.getFlagCode();
/*      */           }
/*      */         }
/*      */       } 
/*      */     } else {
/* 1293 */       addDebug("getTQStatus for STATUS has no changed history!");
/*      */     } 
/* 1295 */     return "@@";
/*      */   }
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getADSABRSTATUSHistory() throws MiddlewareException {
/* 1300 */     String str = "COMPATGENABR";
/* 1301 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1303 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1304 */     if (eANAttribute != null) {
/* 1305 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1307 */     addDebug(" COMPATGENABR of " + entityItem.getKey() + "  was null");
/* 1308 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ paramXMLMQ) throws MiddlewareException {
/* 1314 */     String str = paramXMLMQ.getStatusAttr();
/* 1315 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1316 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1317 */     if (eANAttribute != null) {
/* 1318 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1320 */     addDebug(" STATUS of " + entityItem.getKey() + "  was null");
/* 1321 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVEFiltered(String paramString) {
/* 1329 */     for (byte b = 0; b < COMPATVE_Filter_Array.length; b++) {
/* 1330 */       if (COMPATVE_Filter_Array[b][0].equals(paramString))
/* 1331 */         return true; 
/*      */     } 
/* 1333 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVECountryFiltered(String paramString) {
/* 1340 */     for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/* 1341 */       if (VE_Country_Filter_Array[b][0].equals(paramString))
/* 1342 */         return true; 
/*      */     } 
/* 1344 */     return false;
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
/* 1359 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 1360 */     if (profile == null) {
/* 1361 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 1363 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 1365 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 1366 */       addDebug("switchRole nlsids: " + str);
/* 1367 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1368 */       while (stringTokenizer.hasMoreTokens()) {
/* 1369 */         String str1 = stringTokenizer.nextToken();
/* 1370 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 1371 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 1372 */           profile.getReadLanguages().addElement(nLSItem);
/* 1373 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1378 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1388 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1390 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1391 */     EANList eANList = entityGroup.getMetaAttribute();
/* 1392 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1394 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1395 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1396 */       stringBuffer.append(" ");
/*      */     } 
/*      */     
/* 1399 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1405 */     return this.m_db;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/* 1410 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1415 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1421 */     if (this.dbgPw != null) {
/* 1422 */       this.dbgLen += paramString.length();
/* 1423 */       this.dbgPw.println(paramString);
/* 1424 */       this.dbgPw.flush();
/*      */     } else {
/* 1426 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1434 */     addOutput(paramString);
/* 1435 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/* 1442 */     return this.rsBundle;
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
/* 1453 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 1454 */     Transformer transformer = transformerFactory.newTransformer();
/* 1455 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 1457 */     transformer.setOutputProperty("indent", "no");
/* 1458 */     transformer.setOutputProperty("method", "xml");
/* 1459 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 1462 */     StringWriter stringWriter = new StringWriter();
/* 1463 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 1464 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 1465 */     transformer.transform(dOMSource, streamResult);
/* 1466 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */     
/* 1469 */     transformer.setOutputProperty("indent", "yes");
/* 1470 */     stringWriter = new StringWriter();
/* 1471 */     streamResult = new StreamResult(stringWriter);
/* 1472 */     transformer.transform(dOMSource, streamResult);
/* 1473 */     addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     
/* 1475 */     return str;
/*      */   }
/*      */   protected void addUserXML(String paramString) {
/* 1478 */     if (this.userxmlPw != null) {
/* 1479 */       this.userxmlLen += paramString.length();
/* 1480 */       this.userxmlPw.println(paramString);
/* 1481 */       this.userxmlPw.flush();
/*      */     } else {
/* 1483 */       this.userxmlSb.append(convertToHTML(paramString) + NEWLINE);
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
/* 1497 */     boolean bool = false;
/* 1498 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 1499 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 1500 */     if (str.equals("all")) {
/* 1501 */       bool = true;
/*      */     } else {
/* 1503 */       HashSet<String> hashSet = new HashSet();
/* 1504 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1505 */       while (stringTokenizer.hasMoreTokens()) {
/* 1506 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 1508 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 1509 */       hashSet.clear();
/*      */     } 
/*      */     
/* 1512 */     if (!bool) {
/* 1513 */       addDebug("PDHDOMAIN for " + paramEntityItem.getKey() + " did not include " + str + ", execution is bypassed [" + 
/* 1514 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/* 1516 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale getLocale(int paramInt) {
/* 1526 */     Locale locale = null;
/* 1527 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 1530 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1554 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1564 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1573 */     return "COMPATGENABR";
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
/* 1584 */     String str = "";
/* 1585 */     StringBuffer stringBuffer = new StringBuffer();
/* 1586 */     StringCharacterIterator stringCharacterIterator = null;
/* 1587 */     char c = ' ';
/* 1588 */     if (paramString != null) {
/* 1589 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1590 */       c = stringCharacterIterator.first();
/* 1591 */       while (c != '￿') {
/*      */         
/* 1593 */         switch (c) {
/*      */           
/*      */           case '<':
/* 1596 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 1599 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/* 1605 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */           default:
/* 1608 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1611 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1613 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1616 */     return str;
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
/* 1631 */     logMessage(getDescription() + " ***** " + paramString);
/* 1632 */     addDebug("deactivateFlagValue entered for " + paramString);
/* 1633 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 1636 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString);
/* 1637 */     if (eANMetaAttribute == null) {
/* 1638 */       addDebug("deactivateFlagValue: " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/* 1640 */       logMessage(getDescription() + " ***** " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1644 */     String str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/* 1645 */     if (str != null) {
/*      */       try {
/* 1647 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/* 1648 */         Vector<ReturnEntityKey> vector = new Vector();
/* 1649 */         Vector<MultipleFlag> vector1 = new Vector();
/* 1650 */         EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 1651 */         if (eANAttribute != null) {
/* 1652 */           String str1 = eANAttribute.getEffFrom();
/* 1653 */           ControlBlock controlBlock = new ControlBlock(str1, str1, str1, str1, this.m_prof.getOPWGID());
/* 1654 */           StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/* 1655 */           while (stringTokenizer.hasMoreTokens()) {
/* 1656 */             String str2 = stringTokenizer.nextToken();
/*      */             
/* 1658 */             MultipleFlag multipleFlag = new MultipleFlag(this.m_prof.getEnterprise(), entityItem.getEntityType(), entityItem.getEntityID(), paramString, str2, 1, controlBlock);
/* 1659 */             vector1.addElement(multipleFlag);
/*      */           } 
/* 1661 */           returnEntityKey.m_vctAttributes = vector1;
/* 1662 */           vector.addElement(returnEntityKey);
/* 1663 */           this.m_db.update(this.m_prof, vector, false, false);
/* 1664 */           addDebug(entityItem.getKey() + " had " + paramString + " deactivated");
/*      */         } 
/*      */       } finally {
/*      */         
/* 1668 */         this.m_db.commit();
/* 1669 */         this.m_db.freeStatement();
/* 1670 */         this.m_db.isPending("finally after deactivate value");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPeriodicABR() {
/* 1678 */     return this.isPeriodicABR;
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
/*      */   private void removeItem(EntityGroup paramEntityGroup, EntityItem paramEntityItem) {
/* 1711 */     EntityItem[] arrayOfEntityItem1 = new EntityItem[paramEntityItem.getUpLinkCount()];
/* 1712 */     paramEntityItem.getUpLink().copyInto((Object[])arrayOfEntityItem1);
/*      */ 
/*      */     
/* 1715 */     for (byte b = 0; b < arrayOfEntityItem1.length; b++) {
/*      */       
/* 1717 */       EntityItem entityItem = arrayOfEntityItem1[b];
/* 1718 */       addDebug("uplink: " + entityItem.getKey());
/*      */       
/* 1720 */       paramEntityItem.removeUpLink((EANEntity)entityItem);
/*      */ 
/*      */       
/* 1723 */       if (entityItem.getEntityGroup() != null)
/*      */       {
/*      */         
/* 1726 */         if (!entityItem.hasDownLinks()) {
/*      */           int j;
/* 1728 */           for (j = entityItem.getAttributeCount() - 1; j >= 0; j--) {
/* 1729 */             EANAttribute eANAttribute = entityItem.getAttribute(j);
/* 1730 */             entityItem.removeAttribute(eANAttribute);
/*      */           } 
/*      */           
/* 1733 */           entityItem.getEntityGroup().removeEntityItem(entityItem);
/*      */ 
/*      */           
/* 1736 */           for (j = entityItem.getUpLinkCount() - 1; j >= 0; j--) {
/* 1737 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(j);
/* 1738 */             entityItem.removeUpLink((EANEntity)entityItem1);
/*      */           } 
/*      */           
/* 1741 */           entityItem.setParent(null);
/*      */         } 
/*      */       }
/*      */       
/* 1745 */       arrayOfEntityItem1[b] = null;
/*      */     } 
/*      */     
/* 1748 */     arrayOfEntityItem1 = null;
/*      */     
/* 1750 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[paramEntityItem.getDownLinkCount()];
/* 1751 */     paramEntityItem.getDownLink().copyInto((Object[])arrayOfEntityItem2);
/*      */     
/*      */     int i;
/* 1754 */     for (i = 0; i < arrayOfEntityItem2.length; i++) {
/*      */       
/* 1756 */       EntityItem entityItem = arrayOfEntityItem2[i];
/* 1757 */       addDebug("Downlink: " + entityItem.getKey());
/*      */       
/* 1759 */       paramEntityItem.removeDownLink((EANEntity)entityItem);
/*      */ 
/*      */       
/* 1762 */       if (entityItem.getEntityGroup() != null)
/*      */       {
/*      */         
/* 1765 */         if (!entityItem.hasUpLinks()) {
/*      */           int j;
/* 1767 */           for (j = entityItem.getAttributeCount() - 1; j >= 0; j--) {
/* 1768 */             EANAttribute eANAttribute = entityItem.getAttribute(j);
/* 1769 */             entityItem.removeAttribute(eANAttribute);
/*      */           } 
/*      */           
/* 1772 */           entityItem.getEntityGroup().removeEntityItem(entityItem);
/*      */ 
/*      */           
/* 1775 */           for (j = entityItem.getUpLinkCount() - 1; j >= 0; j--) {
/* 1776 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(j);
/* 1777 */             entityItem.removeDownLink((EANEntity)entityItem1);
/*      */           } 
/*      */           
/* 1780 */           entityItem.setParent(null);
/*      */         } 
/*      */       }
/*      */       
/* 1784 */       arrayOfEntityItem2[i] = null;
/*      */     } 
/*      */     
/* 1787 */     arrayOfEntityItem2 = null;
/*      */     
/* 1789 */     paramEntityItem.setParent(null);
/*      */     
/* 1791 */     for (i = paramEntityItem.getAttributeCount() - 1; i >= 0; i--) {
/* 1792 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(i);
/* 1793 */       paramEntityItem.removeAttribute(eANAttribute);
/*      */     } 
/*      */     
/* 1796 */     paramEntityGroup.removeEntityItem(paramEntityItem);
/*      */   }
/*      */   private HashSet getCountry(EntityItem paramEntityItem, String paramString) {
/* 1799 */     HashSet<String> hashSet = new HashSet();
/*      */     
/* 1801 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/* 1802 */     addDebug("COMPATGENABRSTATUS.getCountry for entity " + paramEntityItem.getKey() + " fAtt " + 
/* 1803 */         PokUtils.getAttributeFlagValue(paramEntityItem, paramString) + NEWLINE);
/* 1804 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 1806 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1807 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 1809 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 1810 */           hashSet.add(arrayOfMetaFlag[b].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/* 1814 */     return hashSet;
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
/*      */   private void deactivateEntities(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1831 */     String str1 = paramEntityItem.getEntityType();
/* 1832 */     String str2 = null;
/* 1833 */     if ("MODELCG".equals(str1)) {
/* 1834 */       str2 = "DELMODELCG";
/* 1835 */     } else if ("SEOCG".equals(str1)) {
/* 1836 */       str2 = "DELSEOCG";
/* 1837 */     } else if ("MDLCGOSMDL".equals(str1)) {
/* 1838 */       str2 = "DELMDLCGOSMDL";
/* 1839 */     } else if ("SEOCGOSSEO".equals(str1)) {
/* 1840 */       str2 = "DELSEOCGOSSEO";
/* 1841 */     } else if ("SEOCGOSBDL".equals(str1)) {
/* 1842 */       str2 = "DELSEOCGOSBDL";
/* 1843 */     } else if ("SEOCGOSSVCSEO".equals(str1)) {
/* 1844 */       str2 = "DELSEOCGOSSVCSEO";
/*      */     } 
/*      */     
/* 1847 */     DeleteActionItem deleteActionItem = new DeleteActionItem(null, this.m_db, this.m_prof, str2);
/*      */     
/* 1849 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/*      */     
/* 1851 */     arrayOfEntityItem[0] = paramEntityItem;
/* 1852 */     addDebug("deleteEntity " + paramEntityItem.getKey() + " " + arrayOfEntityItem[0].getKey());
/* 1853 */     long l = System.currentTimeMillis();
/*      */ 
/*      */     
/* 1856 */     deleteActionItem.setEntityItems(arrayOfEntityItem);
/* 1857 */     this.m_db.executeAction(this.m_prof, deleteActionItem);
/*      */     
/* 1859 */     addDebug("Time to delete : " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isDeactivatedEntity(EntityItem paramEntityItem) {
/* 1867 */     boolean bool = false;
/* 1868 */     String str = paramEntityItem.getEntityType();
/* 1869 */     if ("MODELCG".equals(str) || "SEOCG".equals(str)) {
/* 1870 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "OKTOPUB", ", ", "@@", false);
/* 1871 */       if ("Delete".equals(str1)) {
/* 1872 */         bool = true;
/*      */       }
/* 1874 */     } else if ("MDLCGOSMDL".equals(str) || "SEOCGOSSEO".equals(str) || "SEOCGOSBDL".equals(str) || "SEOCGOSSVCSEO".equals(str)) {
/* 1875 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "COMPATPUBFLG", ", ", "@@", false);
/* 1876 */       if ("Delete".equals(str1)) {
/* 1877 */         bool = true;
/*      */       }
/*      */     } 
/* 1880 */     return bool;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\COMPATGENABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */