/*      */ package COM.ibm.eannounce.abr.ln.adsxmlbh1;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.DeleteActionItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.Database;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.Profile;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
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
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Properties;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import javax.xml.parsers.ParserConfigurationException;
/*      */ import javax.xml.transform.TransformerException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class COMPATGENABRSTATUS
/*      */   extends ADSABRSTATUS
/*      */ {
/*  120 */   private StringBuffer rptSb = new StringBuffer();
/*      */   
/*  122 */   private StringBuffer xmlgenSb = new StringBuffer();
/*      */   
/*  124 */   private PrintWriter dbgPw = null;
/*      */   
/*  126 */   private String dbgfn = null;
/*      */   
/*  128 */   private int dbgLen = 0;
/*      */   
/*  130 */   private ResourceBundle rsBundle = null;
/*      */   
/*  132 */   private String priorStatus = "&nbsp;";
/*      */   
/*  134 */   private String curStatus = "&nbsp;";
/*      */ 
/*      */   
/*      */   private boolean isPeriodicABR = false;
/*      */ 
/*      */   
/*      */   private boolean isXMLIDLABR = false;
/*      */   
/*      */   private boolean RFRPassedFinal = false;
/*      */   
/*  144 */   private String actionTaken = "";
/*      */   
/*  146 */   private String navName = "";
/*      */   
/*  148 */   private String rootDesc = "";
/*      */   
/*  150 */   private static Hashtable CompatABR_TBL = new Hashtable<>();
/*      */   
/*      */   private static final String MODELCG_DELETEACTION_NAME = "DELMODELCG";
/*      */   
/*      */   private static final String SEOCG_DELETEACTION_NAME = "DELSEOCG";
/*      */   
/*      */   private static final String MDLCGOSMDL_DELETEACTION_NAME = "DELMDLCGOSMDL";
/*      */   
/*      */   private static final String SEOCGOSSEO_DELETEACTION_NAME = "DELSEOCGOSSEO";
/*      */   
/*      */   private static final String SEOCGOSBDL_DELETEACTION_NAME = "DELSEOCGOSBDL";
/*      */   
/*      */   private static final String SEOCGOSSVCSEO_DELETEACTION_NAME = "DELSEOCGOSSVCSEO";
/*      */   
/*      */   private static final String STATUS_INPROCESS = "0050";
/*      */   
/*      */   static {
/*  167 */     CompatABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.ln.adsxmlbh1.WWCOMPATMODABR");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  172 */     CompatABR_TBL.put("SEOCG", "COM.ibm.eannounce.abr.ln.adsxmlbh1.WWCOMPATSEOCGABR");
/*  173 */     CompatABR_TBL.put("SEOCGOS", "COM.ibm.eannounce.abr.ln.adsxmlbh1.WWCOMPATSEOCGOSABR");
/*  174 */     CompatABR_TBL.put("SEOCGOSBDL", "COM.ibm.eannounce.abr.ln.adsxmlbh1.WWCOMPATSEOCGOSBDLABR");
/*  175 */     CompatABR_TBL.put("SEOCGOSSEO", "COM.ibm.eannounce.abr.ln.adsxmlbh1.WWCOMPATSEOCGOSSEOABR");
/*      */     
/*  177 */     CompatABR_TBL.put("WWSEO", "COM.ibm.eannounce.abr.ln.adsxmlbh1.WWCOMPATWWSEOABR");
/*  178 */     CompatABR_TBL.put("LSEOBUNDLE", "COM.ibm.eannounce.abr.ln.adsxmlbh1.WWCOMPATLSEOBUNDLEABR");
/*      */   }
/*      */   
/*  181 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  183 */   private String abrversion = "";
/*      */   
/*  185 */   private String t2DTS = "&nbsp;";
/*      */   
/*  187 */   private String t1DTS = "&nbsp;";
/*      */   
/*  189 */   private StringBuffer userxmlSb = new StringBuffer();
/*      */   
/*  191 */   private static Properties wwprt_propABR = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PROPERTIES_FILENAME = "wwcompat.properties";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  225 */     String str = (String)CompatABR_TBL.get(paramString);
/*  226 */     addDebug("creating instance of CompatABR_TBL  = '" + str + "' for " + paramString);
/*  227 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  238 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */     
/*  242 */     println(EACustom.getDocTypeHtml());
/*      */     try {
/*  244 */       long l = System.currentTimeMillis();
/*      */       
/*  246 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  249 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  252 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  258 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*  259 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*      */       try {
/*  263 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  264 */         this.rootDesc = this.m_elist.getParentEntityGroup().getLongDescription();
/*  265 */         this.isPeriodicABR = getEntityType().equals("ADSXMLSETUP");
/*      */         
/*  267 */         String str5 = getEntityType();
/*      */         
/*  269 */         String str6 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*  270 */         String str7 = PokUtils.getAttributeFlagValue(entityItem, "ADSENTITY");
/*      */         
/*  272 */         if (this.isPeriodicABR) {
/*      */           
/*  274 */           if (str6 != null) {
/*  275 */             str5 = (String)ADSTYPES_TBL.get(str6);
/*      */           }
/*  277 */           if ("20".equals(str7)) {
/*  278 */             str5 = "DEL" + str5;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  284 */         String str8 = getSimpleABRName(str5);
/*  285 */         if (str8 != null) {
/*  286 */           boolean bool = true;
/*  287 */           XMLMQ xMLMQ = (XMLMQ)Class.forName(str8).newInstance();
/*      */           
/*  289 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  296 */           if (!this.isPeriodicABR) {
/*  297 */             String str9 = xMLMQ.getStatusAttr();
/*      */             
/*  299 */             String str10 = getAttributeFlagEnabledValue(entityItem, str9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  306 */             if (!"0020".equals(str10) && !"0040".equals(str10)) {
/*  307 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */               
/*  309 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */             } else {
/*  311 */               bool = xMLMQ.createXML(entityItem);
/*      */               
/*  313 */               if (!bool) {
/*  314 */                 addDebug(entityItem.getKey() + " will not have XML generated, createXML=false");
/*      */               }
/*      */             } 
/*      */           } else {
/*      */             
/*  319 */             addDebug("execute: periodic " + entityItem.getKey());
/*      */           } 
/*      */           
/*  322 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = null;
/*      */ 
/*      */           
/*  325 */           attributeChangeHistoryGroup1 = getADSABRSTATUSHistory();
/*      */           
/*  327 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(xMLMQ);
/*  328 */           setT2DTS(entityItem, xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, "@@");
/*  329 */           setT1DTS(xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, "@@");
/*      */           
/*  331 */           if (getReturnCode() == 0 && bool) {
/*      */ 
/*      */             
/*  334 */             Profile profile = switchRole(xMLMQ.getRoleCode());
/*  335 */             if (profile != null) {
/*  336 */               profile.setValOnEffOn(this.t2DTS, this.t2DTS);
/*  337 */               profile.setEndOfDay(this.t2DTS);
/*  338 */               profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  340 */               Profile profile1 = profile.getNewInstance(this.m_db);
/*  341 */               profile1.setValOnEffOn(this.t1DTS, this.t1DTS);
/*  342 */               profile1.setEndOfDay(this.t2DTS);
/*  343 */               profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  345 */               String str = "";
/*      */               try {
/*  347 */                 if (this.isPeriodicABR) {
/*      */                   
/*  349 */                   String str9 = "";
/*  350 */                   if (str6 != null) {
/*  351 */                     str9 = (String)ADSTYPES_TBL.get(str6);
/*      */                   }
/*  353 */                   str = "Periodic " + str9;
/*  354 */                   if ("20".equals(str7)) {
/*  355 */                     str = "Deleted " + str9;
/*      */                   }
/*  357 */                   setupPrintWriters();
/*  358 */                   xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                 } else {
/*  360 */                   str = entityItem.getKey();
/*      */                   
/*  362 */                   if (domainNeedsChecks(entityItem)) {
/*  363 */                     if (!this.RFRPassedFinal) {
/*      */                       
/*  365 */                       setupPrintWriters();
/*  366 */                       xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                       
/*  368 */                       if (isDeactivatedEntity(entityItem)) {
/*  369 */                         deactivateEntities(entityItem);
/*      */                       }
/*      */                     } 
/*      */                   } else {
/*  373 */                     addXMLGenMsg("DOMAIN_NOT_LISTED", str);
/*      */                   } 
/*      */                 } 
/*  376 */               } catch (IOException iOException) {
/*      */ 
/*      */                 
/*  379 */                 MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("REQ_ERROR"));
/*  380 */                 this.args[0] = iOException.getMessage();
/*  381 */                 addError(messageFormat1.format(this.args));
/*  382 */                 addXMLGenMsg("FAILED", str);
/*  383 */               } catch (SQLException sQLException) {
/*  384 */                 addXMLGenMsg("FAILED", str);
/*  385 */                 throw sQLException;
/*  386 */               } catch (MiddlewareRequestException middlewareRequestException) {
/*  387 */                 addXMLGenMsg("FAILED", str);
/*  388 */                 throw middlewareRequestException;
/*  389 */               } catch (MiddlewareException middlewareException) {
/*  390 */                 addXMLGenMsg("FAILED", str);
/*  391 */                 throw middlewareException;
/*  392 */               } catch (ParserConfigurationException parserConfigurationException) {
/*  393 */                 addXMLGenMsg("FAILED", str);
/*  394 */                 throw parserConfigurationException;
/*  395 */               } catch (TransformerException transformerException) {
/*  396 */                 addXMLGenMsg("FAILED", str);
/*  397 */                 throw transformerException;
/*  398 */               } catch (MissingResourceException missingResourceException) {
/*  399 */                 addXMLGenMsg("FAILED", str);
/*  400 */                 throw missingResourceException;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*  405 */           addError(getShortClassName(getClass()) + " does not support " + str5);
/*      */         } 
/*      */         
/*  408 */         this.navName = getNavigationName(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  414 */         if (this.isPeriodicABR && !this.isXMLIDLABR && getReturnCode() == 0) {
/*  415 */           PDGUtility pDGUtility = new PDGUtility();
/*  416 */           OPICMList oPICMList = new OPICMList();
/*  417 */           oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/*  418 */           pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */         } 
/*      */         
/*  421 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*  422 */       } catch (Exception exception) {
/*  423 */         throw exception;
/*      */       } 
/*  425 */     } catch (Throwable throwable) {
/*  426 */       StringWriter stringWriter = new StringWriter();
/*  427 */       String str5 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  428 */       String str6 = "<pre>{0}</pre>";
/*  429 */       MessageFormat messageFormat1 = new MessageFormat(str5);
/*  430 */       setReturnCode(-3);
/*  431 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  433 */       this.args[0] = throwable.getMessage();
/*  434 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  435 */       messageFormat1 = new MessageFormat(str6);
/*  436 */       this.args[0] = stringWriter.getBuffer().toString();
/*  437 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  438 */       logError("Exception: " + throwable.getMessage());
/*  439 */       logError(stringWriter.getBuffer().toString());
/*      */     } finally {
/*  441 */       setDGTitle(this.navName);
/*  442 */       setDGRptName(getShortClassName(getClass()));
/*  443 */       setDGRptClass(getABRCode());
/*      */       
/*  445 */       if (!isReadOnly()) {
/*  446 */         clearSoftLock();
/*      */       }
/*  448 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  453 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  454 */     this.args[0] = getShortClassName(getClass());
/*  455 */     this.args[1] = this.navName;
/*  456 */     String str2 = messageFormat.format(this.args);
/*      */     
/*  458 */     String str3 = null;
/*  459 */     if (this.isPeriodicABR) {
/*  460 */       str3 = buildPeriodicRptHeader();
/*  461 */       restoreXtraContent();
/*      */     } else {
/*  463 */       str3 = buildDQTriggeredRptHeader();
/*  464 */       restoreXtraContent();
/*      */     } 
/*      */ 
/*      */     
/*  468 */     String str4 = str2 + str3 + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*      */     
/*  470 */     this.rptSb.insert(0, str4);
/*      */     
/*  472 */     println(this.rptSb.toString());
/*  473 */     printDGSubmitString();
/*  474 */     println(EACustom.getTOUDiv());
/*  475 */     buildReportFooter();
/*      */   }
/*      */   
/*      */   private void setupPrintWriters() {
/*  479 */     String str = this.m_abri.getFileName();
/*  480 */     int i = str.lastIndexOf(".");
/*  481 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*      */     try {
/*  483 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  484 */     } catch (Exception exception) {
/*  485 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void closePrintWriters() {
/*  490 */     if (this.dbgPw != null) {
/*  491 */       this.dbgPw.flush();
/*  492 */       this.dbgPw.close();
/*  493 */       this.dbgPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  499 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  501 */       BufferedInputStream bufferedInputStream = null;
/*  502 */       FileInputStream fileInputStream = null;
/*  503 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  505 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  506 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  508 */         String str = null;
/*  509 */         StringBuffer stringBuffer = new StringBuffer();
/*  510 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  512 */         while ((str = bufferedReader.readLine()) != null) {
/*  513 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  515 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  518 */         File file = new File(this.dbgfn);
/*  519 */         if (file.exists()) {
/*  520 */           file.delete();
/*      */         }
/*  522 */       } catch (Exception exception) {
/*  523 */         exception.printStackTrace();
/*      */       } finally {
/*  525 */         if (bufferedInputStream != null) {
/*      */           try {
/*  527 */             bufferedInputStream.close();
/*  528 */           } catch (Exception exception) {
/*  529 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  532 */         if (fileInputStream != null) {
/*      */           try {
/*  534 */             fileInputStream.close();
/*  535 */           } catch (Exception exception) {
/*  536 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addXMLGenMsg(String paramString1, String paramString2) {
/*  547 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/*  548 */     Object[] arrayOfObject = { paramString2 };
/*  549 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/*  566 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Status: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Prior Status: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{7}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{8}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {9} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  575 */     MessageFormat messageFormat = new MessageFormat(str);
/*  576 */     this.args[0] = this.m_prof.getOPName();
/*  577 */     this.args[1] = this.m_prof.getRoleDescription();
/*  578 */     this.args[2] = this.m_prof.getWGName();
/*  579 */     this.args[3] = this.t2DTS;
/*  580 */     this.args[4] = this.curStatus;
/*  581 */     this.args[5] = this.t1DTS;
/*  582 */     this.args[6] = this.priorStatus;
/*  583 */     this.args[7] = this.rootDesc + ": " + this.navName;
/*  584 */     this.args[8] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  585 */     this.args[9] = this.abrversion + " " + getABRVersion();
/*      */     
/*  587 */     return messageFormat.format(this.args);
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
/*  599 */     String str = "<table>" + NEWLINE + "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{2}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {3} -->" + NEWLINE;
/*      */ 
/*      */     
/*  602 */     MessageFormat messageFormat = new MessageFormat(str);
/*  603 */     this.args[0] = this.t2DTS;
/*  604 */     this.args[1] = this.t1DTS;
/*  605 */     this.args[2] = this.xmlgenSb.toString();
/*  606 */     this.args[3] = this.abrversion + " " + getABRVersion();
/*      */     
/*  608 */     return messageFormat.format(this.args);
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
/*  665 */     this.t1DTS = this.m_strEpoch;
/*  666 */     String str = this.m_strEpoch;
/*  667 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  668 */     if (this.isPeriodicABR && !this.isXMLIDLABR) {
/*  669 */       addDebug("getT1 entered for Periodic ABR " + entityItem.getKey());
/*      */       
/*  671 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/*  672 */       if (eANMetaAttribute == null) {
/*  673 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*      */       
/*  676 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */ 
/*      */     
/*      */     }
/*  680 */     else if (!this.isXMLIDLABR) {
/*      */       
/*  682 */       String str1 = getT2Status(paramAttributeChangeHistoryGroup2);
/*      */       
/*  684 */       if (existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/*      */         
/*  686 */         if (str1.equals("0040")) {
/*  687 */           this.t1DTS = getTQRFR(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/*  688 */           if (this.t1DTS.equals(this.m_strEpoch)) {
/*  689 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*  690 */           } else if (this.RFRPassedFinal != true) {
/*  691 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_CHANGES");
/*      */           }
/*      */         
/*      */         }
/*  695 */         else if (str1.equals("0020")) {
/*  696 */           this.t1DTS = getTQFinal(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/*  697 */           if (this.t1DTS.equals(this.m_strEpoch)) {
/*  698 */             this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */           } else {
/*  700 */             this.actionTaken = this.rsBundle.getString("ACTION_FINAL_CHANGES");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  705 */         if (str1.equals("0040")) {
/*  706 */           this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*  707 */         } else if (str1.equals("0020")) {
/*  708 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */         } 
/*  710 */         addDebug("getT1 for " + entityItem.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
/*      */       } 
/*  712 */       str = getTimeofIDL();
/*  713 */       if (this.t1DTS.compareTo(str) < 0) {
/*  714 */         this.t1DTS = str;
/*  715 */         addOutput("Found T1 DTS is earlier than the time of initializing WWTECHCOMPAT, the time of last run initializing WWTECHCOMPAT be used as T1");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/*  725 */     if (paramAttributeChangeHistoryGroup != null) {
/*  726 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  727 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  728 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  729 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  734 */     return false;
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
/*      */   private void setT2DTS(EntityItem paramEntityItem, XMLMQ paramXMLMQ, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup1, AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup2, String paramString) throws MiddlewareException {
/*  750 */     addDebug("getT2 entered for The ADS ABR handles this as an IDL:" + this.isXMLIDLABR);
/*  751 */     if (!this.isXMLIDLABR) {
/*  752 */       if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/*      */         
/*  754 */         int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount();
/*      */ 
/*      */         
/*  757 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*  758 */         if (attributeChangeHistoryItem != null) {
/*  759 */           addDebug("getT2Time [" + (i - 1) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  760 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*  761 */           if (attributeChangeHistoryItem.getFlagCode().equals("0050")) {
/*  762 */             this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */           } else {
/*      */             
/*  765 */             addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
/*      */             
/*  767 */             this.t2DTS = getNow();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  774 */         attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 3);
/*  775 */         if (attributeChangeHistoryItem != null) {
/*  776 */           addDebug("getT2Time [" + (i - 3) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  777 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*  778 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  779 */             this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */           } else {
/*      */             
/*  782 */             addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not &DTFS " + paramString + " return valfrom of queued.");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  787 */         this.t2DTS = getNow();
/*  788 */         addDebug("getT2Time for COMPATGENABRSTATUS changedHistoryGroup has no history, set getNow to t2DTS");
/*      */       } 
/*      */     } else {
/*  791 */       EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("STATUS");
/*  792 */       if (eANMetaAttribute != null) {
/*  793 */         if (existBefore(paramAttributeChangeHistoryGroup2, "0020")) {
/*  794 */           for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  795 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/*  796 */             if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/*  797 */               this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*  798 */               this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/*      */               
/*  800 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/*      */               
/*  802 */               if (attributeChangeHistoryItem1 != null) {
/*  803 */                 this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/*  804 */                 addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/*  805 */                     .getFlagCode());
/*      */               } 
/*      */               break;
/*      */             } 
/*      */           } 
/*  810 */         } else if (existBefore(paramAttributeChangeHistoryGroup2, "0040")) {
/*  811 */           for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  812 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/*  813 */             if (attributeChangeHistoryItem.getFlagCode().equals("0040")) {
/*  814 */               this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*  815 */               this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/*      */               
/*  817 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/*      */               
/*  819 */               if (attributeChangeHistoryItem1 != null) {
/*  820 */                 this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/*  821 */                 addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/*  822 */                     .getFlagCode());
/*      */               } 
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  828 */           addError(this.rsBundle.getString("IDL_NOT_R4RFINAL"));
/*  829 */           addDebug("getT2Time for IDL ABR, the Status never being RFR or Final");
/*      */         } 
/*      */       } else {
/*  832 */         this.t2DTS = getNow();
/*  833 */         addDebug(paramEntityItem.getKey() + " , There is not such attribute STATUS, set t2DTS is getNow().");
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
/*  847 */     String str = "";
/*  848 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  849 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/*  851 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  852 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  853 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */ 
/*      */           
/*  857 */           if (attributeChangeHistoryItem.getChangeDate().compareTo(this.t2DTS) < 0) {
/*      */             
/*  859 */             if (!"0020".equals(attributeChangeHistoryItem.getFlagCode()) && !"0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/*  860 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/*  861 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */               break;
/*      */             } 
/*  864 */             this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/*  865 */             str = attributeChangeHistoryItem.getFlagCode();
/*  866 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */             
/*  868 */             if (attributeChangeHistoryItem != null) {
/*  869 */               this.priorStatus = attributeChangeHistoryItem.getAttributeValue();
/*  870 */               addDebug("getT2Status [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem.getChangeDate() + " flagcode: " + attributeChangeHistoryItem
/*  871 */                   .getFlagCode());
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  879 */       addDebug("getT2Status for " + entityItem.getKey() + " getChangeHistoryItemCount less than 0.");
/*      */     } 
/*  881 */     return str;
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
/*  896 */     String str = this.m_strEpoch;
/*  897 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/*  898 */       boolean bool = false;
/*      */       
/*  900 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/*  901 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/*  902 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */           
/*  905 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/*  907 */             bool = true;
/*      */           }
/*  909 */           if (bool && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/*  910 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             
/*  912 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/*  914 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  915 */               str = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/*  917 */               addDebug("getPreveTQFinalDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/*      */ 
/*      */             
/*  921 */             String str1 = getTQStatus(paramAttributeChangeHistoryGroup2, str);
/*  922 */             if (str1.equals("0020")) {
/*      */               break;
/*      */             }
/*  925 */             bool = false;
/*  926 */             str = this.m_strEpoch;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  932 */       addDebug("getTQFinalDTS for COMPATGENABRSTATUS has no changed history");
/*      */     } 
/*  934 */     return str;
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
/*  948 */     String str1 = this.m_strEpoch;
/*  949 */     String str2 = this.m_strEpoch;
/*      */     
/*  951 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/*  952 */       boolean bool1 = false;
/*  953 */       boolean bool2 = false;
/*      */       
/*  955 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/*  956 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/*  957 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */           
/*  960 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/*  962 */             bool1 = true;
/*      */           }
/*  964 */           if (bool1 && attributeChangeHistoryItem.getFlagCode().equals("0020"))
/*      */           {
/*  966 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/*  968 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/*  970 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  971 */               str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/*  973 */               addDebug("getPreveTQRFRDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/*  975 */             if (!bool2) {
/*  976 */               bool2 = true;
/*  977 */               str2 = str1;
/*      */             } 
/*      */ 
/*      */             
/*  981 */             String str = getTQStatus(paramAttributeChangeHistoryGroup2, str1);
/*  982 */             if (str.equals("0020")) {
/*  983 */               this.RFRPassedFinal = true;
/*  984 */               this.actionTaken = this.rsBundle.getString("ACTION_R4R_PASSEDFINAL");
/*  985 */               return str1;
/*      */             } 
/*  987 */             bool1 = false;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  994 */       addDebug("getTQRFRDTS for COMPATGENABRSTATUS has no changed history");
/*      */     } 
/*  996 */     return str2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTQStatus(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws MiddlewareRequestException {
/* 1002 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 1004 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1005 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1006 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 1009 */           if (paramString.compareTo(attributeChangeHistoryItem.getChangeDate()) > 0) {
/* 1010 */             return attributeChangeHistoryItem.getFlagCode();
/*      */           }
/*      */         }
/*      */       } 
/*      */     } else {
/* 1015 */       addDebug("getTQStatus for STATUS has no changed history!");
/*      */     } 
/* 1017 */     return "@@";
/*      */   }
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getADSABRSTATUSHistory() throws MiddlewareException {
/* 1022 */     String str = "COMPATGENABR";
/* 1023 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1025 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1026 */     if (eANAttribute != null) {
/* 1027 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1029 */     addDebug(" COMPATGENABR of " + entityItem.getKey() + "  was null");
/* 1030 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ paramXMLMQ) throws MiddlewareException {
/* 1036 */     String str = paramXMLMQ.getStatusAttr();
/* 1037 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1038 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1039 */     if (eANAttribute != null) {
/* 1040 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1042 */     addDebug(" STATUS of " + entityItem.getKey() + "  was null");
/* 1043 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Profile switchRole(String paramString) throws EANBusinessRuleException, SQLException, MiddlewareBusinessRuleException, MiddlewareRequestException, RemoteException, IOException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 1052 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 1053 */     if (profile == null) {
/* 1054 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 1056 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 1058 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 1059 */       addDebug("switchRole nlsids: " + str);
/* 1060 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1061 */       while (stringTokenizer.hasMoreTokens()) {
/* 1062 */         String str1 = stringTokenizer.nextToken();
/* 1063 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 1064 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 1065 */           profile.getReadLanguages().addElement(nLSItem);
/* 1066 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1071 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1080 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1082 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1083 */     EANList eANList = entityGroup.getMetaAttribute();
/* 1084 */     for (byte b = 0; b < eANList.size(); b++) {
/* 1085 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1086 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1087 */       stringBuffer.append(" ");
/*      */     } 
/*      */     
/* 1090 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1097 */     return this.m_db;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/* 1104 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1111 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1118 */     if (this.dbgPw != null) {
/* 1119 */       this.dbgLen += paramString.length();
/* 1120 */       this.dbgPw.println(paramString);
/* 1121 */       this.dbgPw.flush();
/*      */     } else {
/* 1123 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1131 */     addOutput(paramString);
/* 1132 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/* 1139 */     return this.rsBundle;
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
/*      */   protected boolean domainNeedsChecks(EntityItem paramEntityItem) {
/* 1151 */     boolean bool = false;
/* 1152 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 1153 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 1154 */     if (str.equals("all")) {
/* 1155 */       bool = true;
/*      */     } else {
/* 1157 */       HashSet<String> hashSet = new HashSet();
/* 1158 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1159 */       while (stringTokenizer.hasMoreTokens()) {
/* 1160 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 1162 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 1163 */       hashSet.clear();
/*      */     } 
/*      */     
/* 1166 */     if (!bool) {
/* 1167 */       addDebug("PDHDOMAIN for " + paramEntityItem.getKey() + " did not include " + str + ", execution is bypassed [" + 
/* 1168 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/* 1170 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1180 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1189 */     return "COMPATGENABRSTATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static String convertToHTML(String paramString) {
/* 1199 */     String str = "";
/* 1200 */     StringBuffer stringBuffer = new StringBuffer();
/* 1201 */     StringCharacterIterator stringCharacterIterator = null;
/* 1202 */     char c = ' ';
/* 1203 */     if (paramString != null) {
/* 1204 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1205 */       c = stringCharacterIterator.first();
/* 1206 */       while (c != '￿') {
/* 1207 */         switch (c) {
/*      */           case '<':
/* 1209 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 1212 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/* 1218 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */           default:
/* 1221 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1224 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1226 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1229 */     return str;
/*      */   }
/*      */ 
/*      */   
/*      */   private void deactivateEntities(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1234 */     String str1 = paramEntityItem.getEntityType();
/* 1235 */     String str2 = null;
/* 1236 */     if ("MODELCG".equals(str1)) {
/* 1237 */       str2 = "DELMODELCG";
/* 1238 */     } else if ("SEOCG".equals(str1)) {
/* 1239 */       str2 = "DELSEOCG";
/* 1240 */     } else if ("MDLCGOSMDL".equals(str1)) {
/* 1241 */       str2 = "DELMDLCGOSMDL";
/* 1242 */     } else if ("SEOCGOSSEO".equals(str1)) {
/* 1243 */       str2 = "DELSEOCGOSSEO";
/* 1244 */     } else if ("SEOCGOSBDL".equals(str1)) {
/* 1245 */       str2 = "DELSEOCGOSBDL";
/* 1246 */     } else if ("SEOCGOSSVCSEO".equals(str1)) {
/* 1247 */       str2 = "DELSEOCGOSSVCSEO";
/*      */     } else {
/* 1249 */       addError("Delete Action Not support " + str1);
/*      */     } 
/* 1251 */     if (paramEntityItem == null) {
/*      */       return;
/*      */     }
/* 1254 */     addDebug("delete " + paramEntityItem.getKey());
/* 1255 */     EntityItem[] arrayOfEntityItem = new EntityItem[1];
/* 1256 */     DeleteActionItem deleteActionItem = new DeleteActionItem(null, this.m_db, this.m_prof, str2);
/*      */ 
/*      */     
/* 1259 */     arrayOfEntityItem[0] = paramEntityItem;
/*      */     
/* 1261 */     long l = System.currentTimeMillis();
/*      */ 
/*      */ 
/*      */     
/* 1265 */     deleteActionItem.setEntityItems(arrayOfEntityItem);
/* 1266 */     this.m_db.executeAction(this.m_prof, deleteActionItem);
/*      */ 
/*      */     
/* 1269 */     addDebug("Time to delete : " + Stopwatch.format(System.currentTimeMillis() - l));
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
/*      */   private boolean isDeactivatedEntity(EntityItem paramEntityItem) {
/* 1284 */     boolean bool = false;
/* 1285 */     String str = paramEntityItem.getEntityType();
/* 1286 */     if ("MODELCG".equals(str) || "SEOCG".equals(str)) {
/* 1287 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "OKTOPUB", ", ", "@@", false);
/* 1288 */       addDebug(paramEntityItem.getKey() + " attrbute OKTOPUB: " + str1);
/* 1289 */       if ("Delete".equals(str1)) {
/* 1290 */         bool = true;
/*      */       }
/* 1292 */     } else if ("MDLCGOSMDL".equals(str) || "SEOCGOSSEO".equals(str) || "SEOCGOSBDL".equals(str) || "SEOCGOSSVCSEO"
/* 1293 */       .equals(str)) {
/* 1294 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "COMPATPUBFLG", ", ", "@@", false);
/*      */ 
/*      */ 
/*      */       
/* 1298 */       addDebug(paramEntityItem.getKey() + " attribute COMPATPUBFLG: " + str1);
/* 1299 */       if ("Delete".equals(str1)) {
/* 1300 */         bool = true;
/*      */       }
/*      */     } 
/* 1303 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTimeofIDL() {
/* 1312 */     String str = this.m_strEpoch;
/*      */ 
/*      */     
/*      */     try {
/* 1316 */       if (wwprt_propABR == null) {
/* 1317 */         wwprt_propABR = new Properties();
/* 1318 */         FileInputStream fileInputStream = new FileInputStream("./wwcompat.properties");
/* 1319 */         wwprt_propABR.load(fileInputStream);
/* 1320 */         fileInputStream.close();
/* 1321 */         str = wwprt_propABR.getProperty("TIMEOFIDL", this.m_strEpoch);
/*      */       } else {
/* 1323 */         str = wwprt_propABR.getProperty("TIMEOFIDL", this.m_strEpoch);
/*      */       } 
/* 1325 */       addDebug("loadProperties for wwcompat.properties " + str);
/*      */     
/*      */     }
/* 1328 */     catch (Exception exception) {
/* 1329 */       addDebug("Unable to loadProperties for wwcompat.properties " + exception);
/*      */     } 
/*      */ 
/*      */     
/* 1333 */     return str;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\ln\adsxmlbh1\COMPATGENABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */