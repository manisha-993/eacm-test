/*      */ package COM.ibm.eannounce.abr.sg.adsxmlbh1;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
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
/*      */ public class WWCOMPATABR extends ADSABRSTATUS {
/*   66 */   private StringBuffer rptSb = new StringBuffer();
/*      */   
/*   68 */   private StringBuffer xmlgenSb = new StringBuffer();
/*   69 */   private PrintWriter dbgPw = null;
/*   70 */   private PrintWriter userxmlPw = null;
/*   71 */   private String dbgfn = null;
/*   72 */   private String userxmlfn = null;
/*   73 */   private int userxmlLen = 0;
/*   74 */   private int dbgLen = 0;
/*   75 */   private ResourceBundle rsBundle = null;
/*   76 */   private String priorStatus = "&nbsp;";
/*   77 */   private String curStatus = "&nbsp;";
/*      */   
/*      */   private boolean isPeriodicABR = false;
/*      */   
/*      */   private boolean isXMLIDLABR = false;
/*      */   private boolean RFRPassedFinal = false;
/*   83 */   private String actionTaken = "";
/*   84 */   private static Hashtable CompatABR_TBL = new Hashtable<>();
/*      */   static {
/*   86 */     CompatABR_TBL.put("MODEL", "COM.ibm.eannounce.abr.sg.adsxmlbh1.ADSCOMPATGENABR");
/*      */   }
/*      */ 
/*      */   
/*   90 */   private Object[] args = (Object[])new String[10];
/*   91 */   private String abrversion = "";
/*   92 */   private String t2DTS = "&nbsp;";
/*   93 */   private String t1DTS = "&nbsp;";
/*   94 */   private StringBuffer userxmlSb = new StringBuffer();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  128 */     String str = (String)CompatABR_TBL.get(paramString);
/*  129 */     addDebug("creating instance of CompatABR_TBL  = '" + str + "' for " + paramString);
/*  130 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void execute_run() {
/*  139 */     String str1 = "";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  146 */     String str2 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  151 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  154 */       long l = System.currentTimeMillis();
/*      */       
/*  156 */       start_ABRBuild(false);
/*      */ 
/*      */       
/*  159 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  162 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  168 */       this.m_elist = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "dummy"), new EntityItem[] { new EntityItem(null, this.m_prof, 
/*      */               
/*  170 */               getEntityType(), getEntityID()) });
/*      */ 
/*      */       
/*      */       try {
/*  174 */         EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */         
/*  176 */         this.isPeriodicABR = getEntityType().equals("ADSXMLSETUP");
/*      */         
/*  178 */         String str6 = getEntityType();
/*      */         
/*  180 */         String str7 = PokUtils.getAttributeFlagValue(entityItem, "ADSTYPE");
/*  181 */         String str8 = PokUtils.getAttributeFlagValue(entityItem, "ADSENTITY");
/*      */         
/*  183 */         if (this.isPeriodicABR) {
/*      */           
/*  185 */           if (str7 != null) {
/*  186 */             str6 = (String)ADSTYPES_TBL.get(str7);
/*      */           }
/*  188 */           if ("20".equals(str8)) {
/*  189 */             str6 = "DEL" + str6;
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  195 */         String str9 = getSimpleABRName(str6);
/*  196 */         if (str9 != null) {
/*  197 */           boolean bool = true;
/*  198 */           XMLMQ xMLMQ = (XMLMQ)Class.forName(str9).newInstance();
/*      */           
/*  200 */           this.abrversion = getShortClassName(xMLMQ.getClass()) + " " + xMLMQ.getVersion();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  207 */           if (!this.isPeriodicABR) {
/*  208 */             String str10 = xMLMQ.getStatusAttr();
/*      */             
/*  210 */             String str11 = getAttributeFlagEnabledValue(entityItem, str10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  217 */             if (!"0020".equals(str11) && !"0040".equals(str11)) {
/*  218 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/*      */               
/*  220 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */             } else {
/*  222 */               bool = xMLMQ.createXML(entityItem);
/*  223 */               if (!bool) {
/*  224 */                 addDebug(entityItem.getKey() + " will not have XML generated, createXML=false");
/*      */               }
/*      */             } 
/*      */           } else {
/*      */             
/*  229 */             addDebug("execute: periodic " + entityItem.getKey());
/*      */           } 
/*      */           
/*  232 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup1 = null;
/*      */ 
/*      */           
/*  235 */           attributeChangeHistoryGroup1 = getADSABRSTATUSHistory();
/*      */           
/*  237 */           AttributeChangeHistoryGroup attributeChangeHistoryGroup2 = getSTATUSHistory(xMLMQ);
/*      */           
/*  239 */           String str = getDTFS(entityItem, xMLMQ);
/*  240 */           setT2DTS(entityItem, xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, str);
/*  241 */           setT1DTS(xMLMQ, attributeChangeHistoryGroup1, attributeChangeHistoryGroup2, str);
/*      */           
/*  243 */           if (getReturnCode() == 0 && bool) {
/*      */ 
/*      */             
/*  246 */             Profile profile = switchRole(xMLMQ.getRoleCode());
/*  247 */             if (profile != null) {
/*  248 */               profile.setValOnEffOn(this.t2DTS, this.t2DTS);
/*  249 */               profile.setEndOfDay(this.t2DTS);
/*  250 */               profile.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  252 */               Profile profile1 = profile.getNewInstance(this.m_db);
/*  253 */               profile1.setValOnEffOn(this.t1DTS, this.t1DTS);
/*  254 */               profile1.setEndOfDay(this.t2DTS);
/*  255 */               profile1.setReadLanguage(Profile.ENGLISH_LANGUAGE);
/*      */               
/*  257 */               String str10 = "";
/*      */               try {
/*  259 */                 if (this.isPeriodicABR) {
/*      */                   
/*  261 */                   String str11 = "";
/*  262 */                   if (str7 != null) {
/*  263 */                     str11 = (String)ADSTYPES_TBL.get(str7);
/*      */                   }
/*  265 */                   str10 = "Periodic " + str11;
/*  266 */                   if ("20".equals(str8)) {
/*  267 */                     str10 = "Deleted " + str11;
/*      */                   }
/*  269 */                   setupPrintWriters();
/*  270 */                   xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                 } else {
/*  272 */                   str10 = entityItem.getKey();
/*      */                   
/*  274 */                   if (domainNeedsChecks(entityItem)) {
/*  275 */                     if (!this.RFRPassedFinal) {
/*  276 */                       xMLMQ.processThis(this, profile1, profile, entityItem);
/*      */                     }
/*      */                   } else {
/*      */                     
/*  280 */                     addXMLGenMsg("DOMAIN_NOT_LISTED", str10);
/*      */                   } 
/*      */                 } 
/*  283 */               } catch (IOException iOException) {
/*      */ 
/*      */                 
/*  286 */                 MessageFormat messageFormat1 = new MessageFormat(this.rsBundle.getString("REQ_ERROR"));
/*  287 */                 this.args[0] = iOException.getMessage();
/*  288 */                 addError(messageFormat1.format(this.args));
/*  289 */                 addXMLGenMsg("FAILED", str10);
/*  290 */               } catch (SQLException sQLException) {
/*  291 */                 addXMLGenMsg("FAILED", str10);
/*  292 */                 throw sQLException;
/*  293 */               } catch (MiddlewareRequestException middlewareRequestException) {
/*  294 */                 addXMLGenMsg("FAILED", str10);
/*  295 */                 throw middlewareRequestException;
/*  296 */               } catch (MiddlewareException middlewareException) {
/*  297 */                 addXMLGenMsg("FAILED", str10);
/*  298 */                 throw middlewareException;
/*  299 */               } catch (ParserConfigurationException parserConfigurationException) {
/*  300 */                 addXMLGenMsg("FAILED", str10);
/*  301 */                 throw parserConfigurationException;
/*  302 */               } catch (TransformerException transformerException) {
/*  303 */                 addXMLGenMsg("FAILED", str10);
/*  304 */                 throw transformerException;
/*  305 */               } catch (MissingResourceException missingResourceException) {
/*  306 */                 addXMLGenMsg("FAILED", str10);
/*  307 */                 throw missingResourceException;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*  312 */           addError(getShortClassName(getClass()) + " does not support " + str6);
/*      */         } 
/*      */         
/*  315 */         str1 = getNavigationName(entityItem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  321 */         if (this.isPeriodicABR && !this.isXMLIDLABR && getReturnCode() == 0) {
/*  322 */           PDGUtility pDGUtility = new PDGUtility();
/*  323 */           OPICMList oPICMList = new OPICMList();
/*  324 */           oPICMList.put("ADSDTS", "ADSDTS=" + this.t2DTS);
/*  325 */           pDGUtility.updateAttribute(this.m_db, this.m_prof, entityItem, oPICMList);
/*      */         } 
/*      */         
/*  328 */         addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*  329 */       } catch (Exception exception) {
/*  330 */         throw exception;
/*      */       
/*      */       }
/*      */       finally {
/*      */         
/*  335 */         if (this.isXMLIDLABR) {
/*  336 */           deactivateMultiFlagValue("XMLABRPROPFILE");
/*      */         }
/*      */       }
/*      */     
/*  340 */     } catch (Throwable throwable) {
/*  341 */       StringWriter stringWriter = new StringWriter();
/*  342 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  343 */       String str7 = "<pre>{0}</pre>";
/*  344 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  345 */       setReturnCode(-3);
/*  346 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  348 */       this.args[0] = throwable.getMessage();
/*  349 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  350 */       messageFormat1 = new MessageFormat(str7);
/*  351 */       this.args[0] = stringWriter.getBuffer().toString();
/*  352 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  353 */       logError("Exception: " + throwable.getMessage());
/*  354 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  358 */       setDGTitle(str1);
/*  359 */       setDGRptName(getShortClassName(getClass()));
/*  360 */       setDGRptClass(getABRCode());
/*      */       
/*  362 */       if (!isReadOnly()) {
/*  363 */         clearSoftLock();
/*      */       }
/*  365 */       closePrintWriters();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  370 */     MessageFormat messageFormat = new MessageFormat(str2);
/*  371 */     this.args[0] = getShortClassName(getClass());
/*  372 */     this.args[1] = str1;
/*  373 */     String str3 = messageFormat.format(this.args);
/*      */     
/*  375 */     String str4 = null;
/*  376 */     if (this.isPeriodicABR) {
/*  377 */       str4 = buildPeriodicRptHeader();
/*  378 */       restoreXtraContent();
/*      */     } else {
/*  380 */       str4 = buildDQTriggeredRptHeader();
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  386 */     String str5 = str3 + str4 + "<pre>" + this.rsBundle.getString("XML_MSG") + "<br />" + this.userxmlSb.toString() + "</pre>" + NEWLINE;
/*  387 */     this.rptSb.insert(0, str5);
/*      */     
/*  389 */     println(this.rptSb.toString());
/*  390 */     printDGSubmitString();
/*  391 */     println(EACustom.getTOUDiv());
/*  392 */     buildReportFooter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getDTFS(EntityItem paramEntityItem, XMLMQ paramXMLMQ) {
/*  401 */     String str1 = paramXMLMQ.getStatusAttr();
/*  402 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/*  403 */     String str3 = "";
/*  404 */     if ("0020".equals(str2)) {
/*  405 */       str3 = getQueuedValue("COMPATGENABRSTATUS");
/*      */     } else {
/*      */       
/*  408 */       str3 = getRFRQueuedValue("COMPATGENABRSTATUS");
/*      */     } 
/*  410 */     addDebug("getDTFS " + getEntityType() + str2 + " from properties file is " + str3);
/*  411 */     return str3;
/*      */   }
/*      */   
/*      */   private void setupPrintWriters() {
/*  415 */     String str = this.m_abri.getFileName();
/*  416 */     int i = str.lastIndexOf(".");
/*  417 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*  418 */     this.userxmlfn = str.substring(0, i + 1) + "userxml";
/*      */     try {
/*  420 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  421 */     } catch (Exception exception) {
/*  422 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */     try {
/*  425 */       this.userxmlPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.userxmlfn, true), "UTF-8"));
/*  426 */     } catch (Exception exception) {
/*  427 */       D.ebug(0, "trouble creating xmlgen PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriters() {
/*  431 */     if (this.dbgPw != null) {
/*  432 */       this.dbgPw.flush();
/*  433 */       this.dbgPw.close();
/*  434 */       this.dbgPw = null;
/*      */     } 
/*  436 */     if (this.userxmlPw != null) {
/*  437 */       this.userxmlPw.flush();
/*  438 */       this.userxmlPw.close();
/*  439 */       this.userxmlPw = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  445 */     if (this.userxmlLen + this.rptSb.length() < 5000000) {
/*      */       
/*  447 */       BufferedInputStream bufferedInputStream = null;
/*  448 */       FileInputStream fileInputStream = null;
/*  449 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  451 */         fileInputStream = new FileInputStream(this.userxmlfn);
/*  452 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  454 */         String str = null;
/*  455 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  457 */         while ((str = bufferedReader.readLine()) != null) {
/*  458 */           this.userxmlSb.append(convertToHTML(str) + NEWLINE);
/*      */         }
/*      */         
/*  461 */         File file = new File(this.userxmlfn);
/*  462 */         if (file.exists()) {
/*  463 */           file.delete();
/*      */         }
/*  465 */       } catch (Exception exception) {
/*  466 */         exception.printStackTrace();
/*      */       } finally {
/*  468 */         if (bufferedInputStream != null) {
/*      */           try {
/*  470 */             bufferedInputStream.close();
/*  471 */           } catch (Exception exception) {
/*  472 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  475 */         if (fileInputStream != null) {
/*      */           try {
/*  477 */             fileInputStream.close();
/*  478 */           } catch (Exception exception) {
/*  479 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/*  484 */       this.userxmlSb.append("XML generated was too large for this file");
/*      */     } 
/*      */     
/*  487 */     if (this.dbgLen + this.userxmlSb.length() + this.rptSb.length() < 5000000) {
/*      */       
/*  489 */       BufferedInputStream bufferedInputStream = null;
/*  490 */       FileInputStream fileInputStream = null;
/*  491 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  493 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  494 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  496 */         String str = null;
/*  497 */         StringBuffer stringBuffer = new StringBuffer();
/*  498 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  500 */         while ((str = bufferedReader.readLine()) != null) {
/*  501 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  503 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  506 */         File file = new File(this.dbgfn);
/*  507 */         if (file.exists()) {
/*  508 */           file.delete();
/*      */         }
/*  510 */       } catch (Exception exception) {
/*  511 */         exception.printStackTrace();
/*      */       } finally {
/*  513 */         if (bufferedInputStream != null) {
/*      */           try {
/*  515 */             bufferedInputStream.close();
/*  516 */           } catch (Exception exception) {
/*  517 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  520 */         if (fileInputStream != null) {
/*      */           try {
/*  522 */             fileInputStream.close();
/*  523 */           } catch (Exception exception) {
/*  524 */             exception.printStackTrace();
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
/*  536 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString(paramString1));
/*  537 */     Object[] arrayOfObject = { paramString2 };
/*  538 */     this.xmlgenSb.append(messageFormat.format(arrayOfObject) + "<br />");
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
/*  555 */     String str = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date/Time: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Status: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Prior feed Date/Time: </th><td>{5}</td></tr>" + NEWLINE + "<tr><th>Prior Status: </th><td>{6}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{7}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {8} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  566 */     MessageFormat messageFormat = new MessageFormat(str);
/*  567 */     this.args[0] = this.m_prof.getOPName();
/*  568 */     this.args[1] = this.m_prof.getRoleDescription();
/*  569 */     this.args[2] = this.m_prof.getWGName();
/*  570 */     this.args[3] = this.t2DTS;
/*  571 */     this.args[4] = this.curStatus;
/*  572 */     this.args[5] = this.t1DTS;
/*  573 */     this.args[6] = this.priorStatus;
/*  574 */     this.args[7] = this.actionTaken + "<br />" + this.xmlgenSb.toString();
/*  575 */     this.args[8] = this.abrversion + " " + getABRVersion();
/*      */     
/*  577 */     return messageFormat.format(this.args);
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
/*  589 */     String str = "<table>" + NEWLINE + "<tr><th>Date/Time of this Run: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Last Ran Date/Time Stamp: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Action Taken: </th><td>{2}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {3} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  595 */     MessageFormat messageFormat = new MessageFormat(str);
/*  596 */     this.args[0] = this.t2DTS;
/*  597 */     this.args[1] = this.t1DTS;
/*  598 */     this.args[2] = this.xmlgenSb.toString();
/*  599 */     this.args[3] = this.abrversion + " " + getABRVersion();
/*      */     
/*  601 */     return messageFormat.format(this.args);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected EntityList getEntityListForDiff(Profile paramProfile, String paramString, EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  611 */     ExtractActionItem extractActionItem = new ExtractActionItem(null, this.m_db, paramProfile, paramString);
/*      */     
/*  613 */     EntityList entityList = this.m_db.getEntityList(paramProfile, extractActionItem, new EntityItem[] { new EntityItem(null, paramProfile, paramEntityItem
/*  614 */             .getEntityType(), paramEntityItem.getEntityID()) });
/*      */ 
/*      */     
/*  617 */     addDebug("EntityList for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  618 */         PokUtils.outputList(entityList));
/*      */ 
/*      */     
/*  621 */     if (isVEFiltered(paramString)) {
/*      */       
/*  623 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  624 */       String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  625 */       addDebug("The status of the root for VE " + paramString + " is: " + str);
/*      */ 
/*      */ 
/*      */       
/*  629 */       for (byte b = 0; b < VE_Filter_Array.length; b++) {
/*  630 */         addDebug("Looking at VE_filter_Array" + VE_Filter_Array[b][0] + " " + VE_Filter_Array[b][1] + " " + VE_Filter_Array[b][2]);
/*      */         
/*  632 */         if (VE_Filter_Array[b][0].equals(paramString)) {
/*  633 */           EntityGroup entityGroup = entityList.getEntityGroup(VE_Filter_Array[b][1]);
/*  634 */           addDebug("Found " + entityList.getEntityGroup(VE_Filter_Array[b][1]));
/*      */ 
/*      */           
/*  637 */           if (entityGroup != null) {
/*      */ 
/*      */             
/*  640 */             EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */ 
/*      */             
/*  643 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/*  645 */               String str1 = null;
/*  646 */               boolean bool = true;
/*  647 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/*  648 */               String str2 = entityItem1.getEntityType();
/*      */               
/*  650 */               addDebug("Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */ 
/*      */               
/*  653 */               String str3 = VE_Filter_Array[b][2];
/*      */ 
/*      */               
/*  656 */               str1 = PokUtils.getAttributeFlagValue(entityItem1, (String)ITEM_STATUS_ATTR_TBL.get(str2));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  664 */               addDebug((String)ITEM_STATUS_ATTR_TBL.get(str2) + " is " + str1);
/*  665 */               if (str1 == null) { bool = false; }
/*  666 */               else if (str1.equals("0020")) { bool = false; }
/*  667 */               else if (str1.equals("0040") && (str.equals("0040") || str3
/*  668 */                 .equals("RFR Final"))) { bool = false; }
/*      */               
/*  670 */               if (bool == true) {
/*      */                 
/*  672 */                 addDebug("Removing " + str2 + " " + entityItem1.getEntityID() + " " + str1 + " from list");
/*  673 */                 addDebug("Filter criteria is " + str3);
/*  674 */                 removeItem(entityGroup, entityItem1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  682 */       addDebug("EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  683 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  688 */     if (isVECountryFiltered(paramString)) {
/*      */       
/*  690 */       EntityItem entityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  691 */       String str1 = entityItem.getEntityType();
/*  692 */       String str2 = (String)ITEM_COUNTRY_ATTR_TBL.get(str1);
/*      */ 
/*      */       
/*  695 */       HashSet hashSet = getCountry(entityItem, str2);
/*      */ 
/*      */ 
/*      */       
/*  699 */       for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/*  700 */         addDebug("Looking at VE_Country_Filter_Array " + VE_Country_Filter_Array[b][0] + " " + VE_Country_Filter_Array[b][1]);
/*      */         
/*  702 */         if (VE_Country_Filter_Array[b][0].equals(paramString)) {
/*  703 */           EntityGroup entityGroup = entityList.getEntityGroup(VE_Country_Filter_Array[b][1]);
/*  704 */           addDebug("Found " + entityList.getEntityGroup(VE_Country_Filter_Array[b][1]));
/*      */ 
/*      */           
/*  707 */           if (entityGroup != null) {
/*      */ 
/*      */             
/*  710 */             EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*      */ 
/*      */             
/*  713 */             for (byte b1 = 0; b1 < arrayOfEntityItem.length; b1++) {
/*      */               
/*  715 */               boolean bool = true;
/*  716 */               EntityItem entityItem1 = arrayOfEntityItem[b1];
/*  717 */               String str3 = entityItem1.getEntityType();
/*      */               
/*  719 */               addDebug("Looking at entity " + entityItem1.getEntityType() + " " + entityItem1.getEntityID());
/*      */               
/*  721 */               String str4 = (String)ITEM_COUNTRY_ATTR_TBL.get(str3);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  726 */               HashSet hashSet1 = getCountry(entityItem1, str4);
/*  727 */               Iterator<String> iterator = hashSet.iterator();
/*  728 */               while (iterator.hasNext() && bool == true) {
/*  729 */                 String str = iterator.next();
/*  730 */                 if (hashSet1.contains(str)) {
/*  731 */                   bool = false;
/*      */                 }
/*      */               } 
/*      */ 
/*      */               
/*  736 */               if (bool == true)
/*      */               {
/*  738 */                 removeItem(entityGroup, entityItem1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  746 */       addDebug("EntityList after filtering for " + paramProfile.getValOn() + " extract " + paramString + " contains the following entities: \n" + 
/*  747 */           PokUtils.outputList(entityList));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  752 */     return entityList;
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
/*  809 */     this.t1DTS = this.m_strEpoch;
/*  810 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  811 */     if (this.isPeriodicABR && !this.isXMLIDLABR) {
/*  812 */       addDebug("getT1 entered for Periodic ABR " + entityItem.getKey());
/*      */       
/*  814 */       EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute("ADSDTS");
/*  815 */       if (eANMetaAttribute == null) {
/*  816 */         throw new MiddlewareException("ADSDTS not in meta for Periodic ABR " + entityItem.getKey());
/*      */       }
/*      */       
/*  819 */       this.t1DTS = PokUtils.getAttributeValue(entityItem, "ADSDTS", ", ", this.m_strEpoch, false);
/*      */ 
/*      */     
/*      */     }
/*  823 */     else if (!this.isXMLIDLABR) {
/*      */       
/*  825 */       String str = getT2Status(paramAttributeChangeHistoryGroup2);
/*      */       
/*  827 */       if (existBefore(paramAttributeChangeHistoryGroup1, "0030")) {
/*      */         
/*  829 */         if (str.equals("0040")) {
/*  830 */           this.t1DTS = getTQRFR(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/*  831 */           if (this.t1DTS.equals(this.m_strEpoch)) {
/*  832 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*  833 */           } else if (this.RFRPassedFinal != true) {
/*  834 */             this.actionTaken = this.rsBundle.getString("ACTION_R4R_CHANGES");
/*      */           }
/*      */         
/*      */         }
/*  838 */         else if (str.equals("0020")) {
/*  839 */           this.t1DTS = getTQFinal(paramAttributeChangeHistoryGroup1, paramAttributeChangeHistoryGroup2, paramString);
/*  840 */           if (this.t1DTS.equals(this.m_strEpoch)) {
/*  841 */             this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */           } else {
/*  843 */             this.actionTaken = this.rsBundle.getString("ACTION_FINAL_CHANGES");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  848 */         if (str.equals("0040")) {
/*  849 */           this.actionTaken = this.rsBundle.getString("ACTION_R4R_FIRSTTIME");
/*  850 */         } else if (str.equals("0020")) {
/*  851 */           this.actionTaken = this.rsBundle.getString("ACTION_FINAL_FIRSTTIME");
/*      */         } 
/*  853 */         addDebug("getT1 for " + entityItem.getKey() + " never was passed before, set T1 = 1980-01-01 00:00:00.00000");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean existBefore(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) {
/*  863 */     if (paramAttributeChangeHistoryGroup != null) {
/*  864 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  865 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  866 */         if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  867 */           return true;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  872 */     return false;
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
/*  887 */     addDebug("getT2 entered for The ADS ABR handles this as an IDL:" + this.isXMLIDLABR);
/*  888 */     if (!this.isXMLIDLABR) {
/*  889 */       if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/*      */         
/*  891 */         int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount();
/*      */ 
/*      */         
/*  894 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 2);
/*  895 */         if (attributeChangeHistoryItem != null) {
/*  896 */           addDebug("getT2Time [" + (i - 2) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  897 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*  898 */           if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/*  899 */             this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */           } else {
/*      */             
/*  902 */             addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not Queued, set getNow() to t2DTS and find the prior &DTFS!");
/*      */             
/*  904 */             this.t2DTS = getNow();
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  911 */         attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 3);
/*  912 */         if (attributeChangeHistoryItem != null) {
/*  913 */           addDebug("getT2Time [" + (i - 3) + "] isActive: " + attributeChangeHistoryItem.isActive() + " isValid: " + attributeChangeHistoryItem.isValid() + " chgdate: " + attributeChangeHistoryItem
/*  914 */               .getChangeDate() + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/*  915 */           if (attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/*  916 */             this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*      */           } else {
/*      */             
/*  919 */             addDebug("getT2Time for the value of " + attributeChangeHistoryItem.getFlagCode() + "is not &DTFS " + paramString + " return valfrom of queued.");
/*      */           } 
/*      */         } 
/*      */       } else {
/*      */         
/*  924 */         this.t2DTS = getNow();
/*  925 */         addDebug("getT2Time for COMPATGENABRSTATUS changedHistoryGroup has no history, set getNow to t2DTS");
/*      */       } 
/*      */     } else {
/*  928 */       EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute("STATUS");
/*  929 */       if (eANMetaAttribute != null) {
/*  930 */         if (existBefore(paramAttributeChangeHistoryGroup2, "0020")) {
/*  931 */           for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  932 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/*  933 */             if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/*  934 */               this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*  935 */               this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/*  936 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/*      */               
/*  938 */               if (attributeChangeHistoryItem1 != null) {
/*  939 */                 this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/*  940 */                 addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/*  941 */                     .getFlagCode());
/*      */               } 
/*      */               break;
/*      */             } 
/*      */           } 
/*  946 */         } else if (existBefore(paramAttributeChangeHistoryGroup2, "0040")) {
/*  947 */           for (int i = paramAttributeChangeHistoryGroup2.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  948 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i);
/*  949 */             if (attributeChangeHistoryItem.getFlagCode().equals("0040")) {
/*  950 */               this.t2DTS = attributeChangeHistoryItem.getChangeDate();
/*  951 */               this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/*  952 */               AttributeChangeHistoryItem attributeChangeHistoryItem1 = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup2.getChangeHistoryItem(i - 1);
/*      */               
/*  954 */               if (attributeChangeHistoryItem1 != null) {
/*  955 */                 this.priorStatus = attributeChangeHistoryItem1.getAttributeValue();
/*  956 */                 addDebug("priorStatus [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem1.getChangeDate() + " flagcode: " + attributeChangeHistoryItem1
/*  957 */                     .getFlagCode());
/*      */               } 
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } else {
/*  963 */           addError(this.rsBundle.getString("IDL_NOT_R4RFINAL"));
/*  964 */           addDebug("getT2Time for IDL ABR, the Status never being RFR or Final");
/*      */         } 
/*      */       } else {
/*  967 */         this.t2DTS = getNow();
/*  968 */         addDebug(paramEntityItem.getKey() + " , There is not such attribute STATUS, set t2DTS is getNow().");
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
/*  982 */     String str = "";
/*  983 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  984 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/*  986 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*  987 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/*  988 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */ 
/*      */           
/*  992 */           if (attributeChangeHistoryItem.getChangeDate().compareTo(this.t2DTS) < 0) {
/*      */             
/*  994 */             if (!"0020".equals(attributeChangeHistoryItem.getFlagCode()) && !"0040".equals(attributeChangeHistoryItem.getFlagCode())) {
/*  995 */               addDebug(entityItem.getKey() + " is not Final or R4R");
/*  996 */               addError(this.rsBundle.getString("NOT_R4RFINAL"));
/*      */               break;
/*      */             } 
/*  999 */             this.curStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1000 */             str = attributeChangeHistoryItem.getFlagCode();
/* 1001 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i - 1);
/*      */             
/* 1003 */             if (attributeChangeHistoryItem != null) {
/* 1004 */               this.priorStatus = attributeChangeHistoryItem.getAttributeValue();
/* 1005 */               addDebug("getT2Status [" + (i - 1) + "] chgdate: " + attributeChangeHistoryItem.getChangeDate() + " flagcode: " + attributeChangeHistoryItem
/* 1006 */                   .getFlagCode());
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } else {
/* 1014 */       addDebug("getT2Status for " + entityItem.getKey() + " getChangeHistoryItemCount less than 0.");
/*      */     } 
/* 1016 */     return str;
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
/* 1031 */     String str = this.m_strEpoch;
/* 1032 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/* 1033 */       boolean bool = false;
/*      */       
/* 1035 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/* 1036 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 1037 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */           
/* 1040 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 1042 */             bool = true;
/*      */           }
/* 1044 */           if (bool && attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1045 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 1047 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/* 1049 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1050 */               str = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/* 1052 */               addDebug("getPreveTQFinalDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/*      */ 
/*      */             
/* 1056 */             String str1 = getTQStatus(paramAttributeChangeHistoryGroup2, str);
/* 1057 */             if (str1.equals("0020")) {
/*      */               break;
/*      */             }
/* 1060 */             bool = false;
/* 1061 */             str = this.m_strEpoch;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1067 */       addDebug("getTQFinalDTS for COMPATGENABRSTATUS has no changed history");
/*      */     } 
/* 1069 */     return str;
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
/* 1083 */     String str1 = this.m_strEpoch;
/* 1084 */     String str2 = this.m_strEpoch;
/*      */     
/* 1086 */     if (paramAttributeChangeHistoryGroup1 != null && paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() > 1) {
/* 1087 */       boolean bool1 = false;
/* 1088 */       boolean bool2 = false;
/*      */       
/* 1090 */       for (int i = paramAttributeChangeHistoryGroup1.getChangeHistoryItemCount() - 3; i >= 0; i--) {
/* 1091 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i);
/* 1092 */         if (attributeChangeHistoryItem != null) {
/*      */ 
/*      */           
/* 1095 */           if (attributeChangeHistoryItem.getFlagCode().equals("0030"))
/*      */           {
/* 1097 */             bool1 = true;
/*      */           }
/* 1099 */           if (bool1 && attributeChangeHistoryItem.getFlagCode().equals("0020"))
/*      */           {
/* 1101 */             str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             
/* 1103 */             attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup1.getChangeHistoryItem(i - 1);
/*      */             
/* 1105 */             if (attributeChangeHistoryItem != null && attributeChangeHistoryItem.getFlagCode().equals(paramString)) {
/* 1106 */               str1 = attributeChangeHistoryItem.getChangeDate();
/*      */             } else {
/* 1108 */               addDebug("getPreveTQRFRDTS[" + (i - 1) + "]. there is no a Preceding &DTFS :" + paramString);
/*      */             } 
/* 1110 */             if (!bool2) {
/* 1111 */               bool2 = true;
/* 1112 */               str2 = str1;
/*      */             } 
/*      */ 
/*      */             
/* 1116 */             String str = getTQStatus(paramAttributeChangeHistoryGroup2, str1);
/* 1117 */             if (str.equals("0020")) {
/* 1118 */               this.RFRPassedFinal = true;
/* 1119 */               this.actionTaken = this.rsBundle.getString("ACTION_R4R_PASSEDFINAL");
/* 1120 */               return str1;
/*      */             } 
/* 1122 */             bool1 = false;
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1129 */       addDebug("getTQRFRDTS for COMPATGENABRSTATUS has no changed history");
/*      */     } 
/* 1131 */     return str2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTQStatus(AttributeChangeHistoryGroup paramAttributeChangeHistoryGroup, String paramString) throws MiddlewareRequestException {
/* 1137 */     if (paramAttributeChangeHistoryGroup != null && paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*      */       
/* 1139 */       for (int i = paramAttributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/* 1140 */         AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)paramAttributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1141 */         if (attributeChangeHistoryItem != null)
/*      */         {
/*      */           
/* 1144 */           if (paramString.compareTo(attributeChangeHistoryItem.getChangeDate()) > 0) {
/* 1145 */             return attributeChangeHistoryItem.getFlagCode();
/*      */           }
/*      */         }
/*      */       } 
/*      */     } else {
/* 1150 */       addDebug("getTQStatus for STATUS has no changed history!");
/*      */     } 
/* 1152 */     return "@@";
/*      */   }
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getADSABRSTATUSHistory() throws MiddlewareException {
/* 1157 */     String str = "COMPATGENABRSTATUS";
/* 1158 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1160 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1161 */     if (eANAttribute != null) {
/* 1162 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1164 */     addDebug(" COMPATGENABRSTATUS of " + entityItem.getKey() + "  was null");
/* 1165 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private AttributeChangeHistoryGroup getSTATUSHistory(XMLMQ paramXMLMQ) throws MiddlewareException {
/* 1171 */     String str = paramXMLMQ.getStatusAttr();
/* 1172 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1173 */     EANAttribute eANAttribute = entityItem.getAttribute(str);
/* 1174 */     if (eANAttribute != null) {
/* 1175 */       return new AttributeChangeHistoryGroup(this.m_db, this.m_prof, eANAttribute);
/*      */     }
/* 1177 */     addDebug(" STATUS of " + entityItem.getKey() + "  was null");
/* 1178 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVEFiltered(String paramString) {
/* 1186 */     for (byte b = 0; b < VE_Filter_Array.length; b++) {
/* 1187 */       if (VE_Filter_Array[b][0].equals(paramString))
/* 1188 */         return true; 
/*      */     } 
/* 1190 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVECountryFiltered(String paramString) {
/* 1197 */     for (byte b = 0; b < VE_Country_Filter_Array.length; b++) {
/* 1198 */       if (VE_Country_Filter_Array[b][0].equals(paramString))
/* 1199 */         return true; 
/*      */     } 
/* 1201 */     return false;
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
/* 1216 */     Profile profile = this.m_prof.getProfileForRoleCode(this.m_db, paramString, paramString, 1);
/* 1217 */     if (profile == null) {
/* 1218 */       addError("Could not switch to " + paramString + " role");
/*      */     } else {
/* 1220 */       addDebug("Switched role from " + this.m_prof.getRoleCode() + " to " + profile.getRoleCode());
/*      */       
/* 1222 */       String str = ABRServerProperties.getNLSIDs(this.m_abri.getABRCode());
/* 1223 */       addDebug("switchRole nlsids: " + str);
/* 1224 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1225 */       while (stringTokenizer.hasMoreTokens()) {
/* 1226 */         String str1 = stringTokenizer.nextToken();
/* 1227 */         NLSItem nLSItem = (NLSItem)READ_LANGS_TBL.get(str1);
/* 1228 */         if (!profile.getReadLanguages().contains(nLSItem)) {
/* 1229 */           profile.getReadLanguages().addElement(nLSItem);
/* 1230 */           addDebug("added nlsitem " + nLSItem + " to new prof");
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1235 */     return profile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1245 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 1247 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 1248 */     EANList eANList = entityGroup.getMetaAttribute();
/* 1249 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1251 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1252 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1253 */       stringBuffer.append(" ");
/*      */     } 
/*      */     
/* 1256 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Database getDB() {
/* 1262 */     return this.m_db;
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getABRAttrCode() {
/* 1267 */     return this.m_abri.getABRCode();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/* 1272 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/* 1278 */     if (this.dbgPw != null) {
/* 1279 */       this.dbgLen += paramString.length();
/* 1280 */       this.dbgPw.println(paramString);
/* 1281 */       this.dbgPw.flush();
/*      */     } else {
/* 1283 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addError(String paramString) {
/* 1291 */     addOutput(paramString);
/* 1292 */     setReturnCode(-1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/* 1299 */     return this.rsBundle;
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
/* 1311 */     MessageFormat messageFormat = null;
/*      */     
/* 1313 */     byte b1 = 0;
/* 1314 */     boolean bool = false;
/*      */ 
/*      */     
/* 1317 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/*      */       
/* 1319 */       String str = paramVector.elementAt(b2);
/* 1320 */       addDebug("in notify looking at prop file " + str);
/*      */       try {
/* 1322 */         ResourceBundle resourceBundle = ResourceBundle.getBundle(str, 
/* 1323 */             getLocale(getProfile().getReadLanguage().getNLSID()));
/* 1324 */         Hashtable<String, String> hashtable = MQUsage.getMQSeriesVars(resourceBundle);
/* 1325 */         boolean bool1 = ((Boolean)hashtable.get("NOTIFY")).booleanValue();
/* 1326 */         hashtable.put("MQCID", paramXMLMQ.getMQCID());
/* 1327 */         hashtable.put("XMLTYPE", "ADS");
/* 1328 */         if (bool1) {
/*      */           try {
/* 1330 */             MQUsage.putToMQQueue("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + paramString2, hashtable);
/*      */             
/* 1332 */             messageFormat = new MessageFormat(this.rsBundle.getString("SENT_SUCCESS"));
/* 1333 */             this.args[0] = str;
/* 1334 */             this.args[1] = paramString1;
/* 1335 */             addOutput(messageFormat.format(this.args));
/* 1336 */             b1++;
/* 1337 */             if (!bool) {
/*      */               
/* 1339 */               addXMLGenMsg("SUCCESS", paramString1);
/* 1340 */               addDebug("sent successfully to prop file " + str);
/*      */             } 
/* 1342 */           } catch (MQException mQException) {
/*      */ 
/*      */             
/* 1345 */             addXMLGenMsg("FAILED", paramString1);
/* 1346 */             bool = true;
/* 1347 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQ_ERROR"));
/* 1348 */             this.args[0] = str + " " + paramString1;
/* 1349 */             this.args[1] = "" + mQException.completionCode;
/* 1350 */             this.args[2] = "" + mQException.reasonCode;
/* 1351 */             addError(messageFormat.format(this.args));
/* 1352 */             mQException.printStackTrace(System.out);
/* 1353 */             addDebug("failed sending to prop file " + str);
/* 1354 */           } catch (IOException iOException) {
/*      */             
/* 1356 */             addXMLGenMsg("FAILED", paramString1);
/* 1357 */             bool = true;
/* 1358 */             messageFormat = new MessageFormat(this.rsBundle.getString("MQIO_ERROR"));
/* 1359 */             this.args[0] = str + " " + paramString1;
/* 1360 */             this.args[1] = iOException.toString();
/* 1361 */             addError(messageFormat.format(this.args));
/* 1362 */             iOException.printStackTrace(System.out);
/* 1363 */             addDebug("failed sending to prop file " + str);
/*      */           } 
/*      */         } else {
/*      */           
/* 1367 */           messageFormat = new MessageFormat(this.rsBundle.getString("NO_NOTIFY"));
/* 1368 */           this.args[0] = str;
/* 1369 */           addError(messageFormat.format(this.args));
/*      */           
/* 1371 */           addXMLGenMsg("NOT_SENT", paramString1);
/* 1372 */           addDebug("not sent to prop file " + str + " because Notify not true");
/*      */         }
/*      */       
/* 1375 */       } catch (MissingResourceException missingResourceException) {
/* 1376 */         addXMLGenMsg("FAILED", str + " " + paramString1);
/* 1377 */         bool = true;
/* 1378 */         addError("Prop file " + str + " " + paramString1 + " not found");
/*      */       } 
/*      */     } 
/*      */     
/* 1382 */     if (b1 > 0 && b1 != paramVector.size()) {
/* 1383 */       addXMLGenMsg("ALL_NOT_SENT", paramString1);
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
/* 1395 */     TransformerFactory transformerFactory = TransformerFactory.newInstance();
/* 1396 */     Transformer transformer = transformerFactory.newTransformer();
/* 1397 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*      */     
/* 1399 */     transformer.setOutputProperty("indent", "no");
/* 1400 */     transformer.setOutputProperty("method", "xml");
/* 1401 */     transformer.setOutputProperty("encoding", "UTF-8");
/*      */ 
/*      */     
/* 1404 */     StringWriter stringWriter = new StringWriter();
/* 1405 */     StreamResult streamResult = new StreamResult(stringWriter);
/* 1406 */     DOMSource dOMSource = new DOMSource(paramDocument);
/* 1407 */     transformer.transform(dOMSource, streamResult);
/* 1408 */     String str = XMLElem.removeCheat(stringWriter.toString());
/*      */ 
/*      */     
/* 1411 */     transformer.setOutputProperty("indent", "yes");
/* 1412 */     stringWriter = new StringWriter();
/* 1413 */     streamResult = new StreamResult(stringWriter);
/* 1414 */     transformer.transform(dOMSource, streamResult);
/* 1415 */     addUserXML(XMLElem.removeCheat(stringWriter.toString()));
/*      */     
/* 1417 */     return str;
/*      */   }
/*      */   protected void addUserXML(String paramString) {
/* 1420 */     if (this.userxmlPw != null) {
/* 1421 */       this.userxmlLen += paramString.length();
/* 1422 */       this.userxmlPw.println(paramString);
/* 1423 */       this.userxmlPw.flush();
/*      */     } else {
/* 1425 */       this.userxmlSb.append(convertToHTML(paramString) + NEWLINE);
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
/* 1439 */     boolean bool = false;
/* 1440 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 1441 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 1442 */     if (str.equals("all")) {
/* 1443 */       bool = true;
/*      */     } else {
/* 1445 */       HashSet<String> hashSet = new HashSet();
/* 1446 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1447 */       while (stringTokenizer.hasMoreTokens()) {
/* 1448 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 1450 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 1451 */       hashSet.clear();
/*      */     } 
/*      */     
/* 1454 */     if (!bool) {
/* 1455 */       addDebug("PDHDOMAIN for " + paramEntityItem.getKey() + " did not include " + str + ", execution is bypassed [" + 
/* 1456 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/* 1458 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Locale getLocale(int paramInt) {
/* 1468 */     Locale locale = null;
/* 1469 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 1472 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1496 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1506 */     return "1.12";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 1515 */     return "COMPATGENABRSTATUS";
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
/* 1526 */     String str = "";
/* 1527 */     StringBuffer stringBuffer = new StringBuffer();
/* 1528 */     StringCharacterIterator stringCharacterIterator = null;
/* 1529 */     char c = ' ';
/* 1530 */     if (paramString != null) {
/* 1531 */       stringCharacterIterator = new StringCharacterIterator(paramString);
/* 1532 */       c = stringCharacterIterator.first();
/* 1533 */       while (c != '￿') {
/*      */         
/* 1535 */         switch (c) {
/*      */           
/*      */           case '<':
/* 1538 */             stringBuffer.append("&lt;");
/*      */             break;
/*      */           case '>':
/* 1541 */             stringBuffer.append("&gt;");
/*      */             break;
/*      */ 
/*      */ 
/*      */           
/*      */           case '"':
/* 1547 */             stringBuffer.append("&#" + c + ";");
/*      */             break;
/*      */           default:
/* 1550 */             stringBuffer.append(c);
/*      */             break;
/*      */         } 
/* 1553 */         c = stringCharacterIterator.next();
/*      */       } 
/* 1555 */       str = stringBuffer.toString();
/*      */     } 
/*      */     
/* 1558 */     return str;
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
/* 1573 */     logMessage(getDescription() + " ***** " + paramString);
/* 1574 */     addDebug("deactivateFlagValue entered for " + paramString);
/* 1575 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 1578 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString);
/* 1579 */     if (eANMetaAttribute == null) {
/* 1580 */       addDebug("deactivateFlagValue: " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/* 1582 */       logMessage(getDescription() + " ***** " + paramString + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1586 */     String str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/* 1587 */     if (str != null) {
/*      */       try {
/* 1589 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/* 1590 */         Vector<ReturnEntityKey> vector = new Vector();
/* 1591 */         Vector<MultipleFlag> vector1 = new Vector();
/* 1592 */         EANAttribute eANAttribute = entityItem.getAttribute(paramString);
/* 1593 */         if (eANAttribute != null) {
/* 1594 */           String str1 = eANAttribute.getEffFrom();
/* 1595 */           ControlBlock controlBlock = new ControlBlock(str1, str1, str1, str1, this.m_prof.getOPWGID());
/* 1596 */           StringTokenizer stringTokenizer = new StringTokenizer(str, "|");
/* 1597 */           while (stringTokenizer.hasMoreTokens()) {
/* 1598 */             String str2 = stringTokenizer.nextToken();
/*      */             
/* 1600 */             MultipleFlag multipleFlag = new MultipleFlag(this.m_prof.getEnterprise(), entityItem.getEntityType(), entityItem.getEntityID(), paramString, str2, 1, controlBlock);
/* 1601 */             vector1.addElement(multipleFlag);
/*      */           } 
/* 1603 */           returnEntityKey.m_vctAttributes = vector1;
/* 1604 */           vector.addElement(returnEntityKey);
/* 1605 */           this.m_db.update(this.m_prof, vector, false, false);
/* 1606 */           addDebug(entityItem.getKey() + " had " + paramString + " deactivated");
/*      */         } 
/*      */       } finally {
/*      */         
/* 1610 */         this.m_db.commit();
/* 1611 */         this.m_db.freeStatement();
/* 1612 */         this.m_db.isPending("finally after deactivate value");
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
/* 1632 */     logMessage(getDescription() + " ***** " + paramString1 + " set to: " + paramString2);
/* 1633 */     addDebug("setFlagValue entered for " + paramString1 + " set to: " + paramString2);
/* 1634 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */ 
/*      */     
/* 1637 */     EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1638 */     if (eANMetaAttribute == null) {
/* 1639 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + entityItem.getEntityType() + ", nothing to do");
/* 1640 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + entityItem
/* 1641 */           .getEntityType() + ", nothing to do");
/*      */       return;
/*      */     } 
/* 1644 */     if (paramString2 != null)
/*      */     {
/* 1646 */       if (paramString2.equals(getAttributeFlagEnabledValue(entityItem, paramString1))) {
/* 1647 */         addDebug("setFlagValue " + entityItem.getKey() + " " + paramString1 + " already matches: " + paramString2);
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/* 1652 */           if (this.m_cbOn == null) {
/* 1653 */             setControlBlock();
/*      */           }
/* 1655 */           ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*      */           
/* 1657 */           SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), returnEntityKey.getEntityType(), returnEntityKey.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */           
/* 1659 */           Vector<SingleFlag> vector = new Vector();
/* 1660 */           Vector<ReturnEntityKey> vector1 = new Vector();
/* 1661 */           vector.addElement(singleFlag);
/* 1662 */           returnEntityKey.m_vctAttributes = vector;
/* 1663 */           vector1.addElement(returnEntityKey);
/*      */           
/* 1665 */           this.m_db.update(this.m_prof, vector1, false, false);
/* 1666 */           addDebug(entityItem.getKey() + " had " + paramString1 + " set to: " + paramString2);
/*      */         } finally {
/*      */           
/* 1669 */           this.m_db.commit();
/* 1670 */           this.m_db.freeStatement();
/* 1671 */           this.m_db.isPending("finally after update in setflag value");
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected String getQueuedValue(String paramString) {
/* 1678 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 1679 */     if (str == null) {
/* 1680 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 1681 */       return "0020";
/*      */     } 
/* 1683 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 1684 */     return ABRServerProperties.getABRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getRFRQueuedValue(String paramString) {
/* 1689 */     String str = (String)ABR_ATTR_TBL.get(getEntityType());
/* 1690 */     if (str == null) {
/* 1691 */       addDebug("WARNING: cant find ABR attribute code for " + getEntityType());
/* 1692 */       return "0020";
/*      */     } 
/* 1694 */     addDebug("find ABR attribute code for " + getEntityType() + "abrAttrCode is " + str + "_" + paramString);
/* 1695 */     return ABRServerProperties.getABRRFRQueuedValue(str + "_" + paramString);
/*      */   }
/*      */   
/*      */   protected boolean fullMode() {
/* 1699 */     String str = ABRServerProperties.getValue("COMPATGENABRSTATUS", "_FullMode");
/* 1700 */     if (str.equals("true") || str.equals("True") || str.equals("TRUE")) {
/* 1701 */       addDebug("COMPATGENABRSTATUS is running in FullMode!");
/* 1702 */       return true;
/*      */     } 
/* 1704 */     addDebug("COMPATGENABRSTATUS is not running in FullMode!");
/* 1705 */     return false;
/*      */   }
/*      */   
/*      */   protected boolean isPeriodicABR() {
/* 1709 */     return this.isPeriodicABR;
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
/* 1742 */     EntityItem[] arrayOfEntityItem1 = new EntityItem[paramEntityItem.getUpLinkCount()];
/* 1743 */     paramEntityItem.getUpLink().copyInto((Object[])arrayOfEntityItem1);
/*      */ 
/*      */     
/* 1746 */     for (byte b = 0; b < arrayOfEntityItem1.length; b++) {
/*      */       
/* 1748 */       EntityItem entityItem = arrayOfEntityItem1[b];
/* 1749 */       addDebug("uplink: " + entityItem.getKey());
/*      */       
/* 1751 */       paramEntityItem.removeUpLink((EANEntity)entityItem);
/*      */ 
/*      */       
/* 1754 */       if (entityItem.getEntityGroup() != null)
/*      */       {
/*      */         
/* 1757 */         if (!entityItem.hasDownLinks()) {
/*      */           int j;
/* 1759 */           for (j = entityItem.getAttributeCount() - 1; j >= 0; j--) {
/* 1760 */             EANAttribute eANAttribute = entityItem.getAttribute(j);
/* 1761 */             entityItem.removeAttribute(eANAttribute);
/*      */           } 
/*      */           
/* 1764 */           entityItem.getEntityGroup().removeEntityItem(entityItem);
/*      */ 
/*      */           
/* 1767 */           for (j = entityItem.getUpLinkCount() - 1; j >= 0; j--) {
/* 1768 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(j);
/* 1769 */             entityItem.removeUpLink((EANEntity)entityItem1);
/*      */           } 
/*      */           
/* 1772 */           entityItem.setParent(null);
/*      */         } 
/*      */       }
/*      */       
/* 1776 */       arrayOfEntityItem1[b] = null;
/*      */     } 
/*      */     
/* 1779 */     arrayOfEntityItem1 = null;
/*      */     
/* 1781 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[paramEntityItem.getDownLinkCount()];
/* 1782 */     paramEntityItem.getDownLink().copyInto((Object[])arrayOfEntityItem2);
/*      */     
/*      */     int i;
/* 1785 */     for (i = 0; i < arrayOfEntityItem2.length; i++) {
/*      */       
/* 1787 */       EntityItem entityItem = arrayOfEntityItem2[i];
/* 1788 */       addDebug("Downlink: " + entityItem.getKey());
/*      */       
/* 1790 */       paramEntityItem.removeDownLink((EANEntity)entityItem);
/*      */ 
/*      */       
/* 1793 */       if (entityItem.getEntityGroup() != null)
/*      */       {
/*      */         
/* 1796 */         if (!entityItem.hasUpLinks()) {
/*      */           int j;
/* 1798 */           for (j = entityItem.getAttributeCount() - 1; j >= 0; j--) {
/* 1799 */             EANAttribute eANAttribute = entityItem.getAttribute(j);
/* 1800 */             entityItem.removeAttribute(eANAttribute);
/*      */           } 
/*      */           
/* 1803 */           entityItem.getEntityGroup().removeEntityItem(entityItem);
/*      */ 
/*      */           
/* 1806 */           for (j = entityItem.getUpLinkCount() - 1; j >= 0; j--) {
/* 1807 */             EntityItem entityItem1 = (EntityItem)entityItem.getUpLink(j);
/* 1808 */             entityItem.removeDownLink((EANEntity)entityItem1);
/*      */           } 
/*      */           
/* 1811 */           entityItem.setParent(null);
/*      */         } 
/*      */       }
/*      */       
/* 1815 */       arrayOfEntityItem2[i] = null;
/*      */     } 
/*      */     
/* 1818 */     arrayOfEntityItem2 = null;
/*      */     
/* 1820 */     paramEntityItem.setParent(null);
/*      */     
/* 1822 */     for (i = paramEntityItem.getAttributeCount() - 1; i >= 0; i--) {
/* 1823 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(i);
/* 1824 */       paramEntityItem.removeAttribute(eANAttribute);
/*      */     } 
/*      */     
/* 1827 */     paramEntityGroup.removeEntityItem(paramEntityItem);
/*      */   }
/*      */   private HashSet getCountry(EntityItem paramEntityItem, String paramString) {
/* 1830 */     HashSet<String> hashSet = new HashSet();
/*      */     
/* 1832 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem.getAttribute(paramString);
/* 1833 */     addDebug("COMPATGENABRSTATUS.getCountry for entity " + paramEntityItem.getKey() + " fAtt " + 
/* 1834 */         PokUtils.getAttributeFlagValue(paramEntityItem, paramString) + NEWLINE);
/* 1835 */     if (eANFlagAttribute != null && eANFlagAttribute.toString().length() > 0) {
/*      */       
/* 1837 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1838 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 1840 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 1841 */           hashSet.add(arrayOfMetaFlag[b].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/* 1845 */     return hashSet;
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\adsxmlbh1\WWCOMPATABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */