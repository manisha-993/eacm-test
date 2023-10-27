/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANEntity;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.PDGUtility;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
/*      */ import COM.ibm.opicmpdh.transactions.OPICMList;
/*      */ import com.ibm.transform.oim.eacm.util.PokUtils;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
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
/*      */ public class WWSEOABRDD
/*      */   extends PokBaseABR
/*      */ {
/*  134 */   private StringBuffer rptSb = new StringBuffer();
/*  135 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  136 */   private static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   private static final String INDENT1 = "&nbsp;&nbsp;";
/*      */   
/*      */   private static final String DELIMITER = "|";
/*      */   
/*      */   private static final long KB = 1000L;
/*      */   private static final long MB = 1000000L;
/*      */   private static final long GB = 1000000000L;
/*      */   private static final String HARDWARE = "100";
/*      */   private static final String SYSTEM = "126";
/*      */   private static final String DRAWER = "162";
/*      */   private static final String BASE = "150";
/*      */   private static final String PLANAR_SLOTAVAIL = "0020";
/*      */   private static final String MEMORYCARD_SLOTAVAIL = "0010";
/*      */   private static final String EXPDUNIT_SLOTAVAIL = "0030";
/*      */   private static final String SYSTEM_UNIT_BASE = "273";
/*      */   private static final String CUSTOMER_FEATURE_CHOICE = "230";
/*      */   private static final String UNSELECTED_FEAT_ATTACHMNT = "240";
/*      */   private static final String SELECTABLE_FEAT_MECH = "238";
/*      */   private static final String STATUS_FINAL = "0020";
/*      */   private static final String STATUS_R4REVIEW = "0040";
/*      */   private static final String FOREVER_DATE = "9999-12-31";
/*      */   private static final String ABR_INPROCESS = "0050";
/*      */   private static final String ABR_QUEUED = "0020";
/*      */   private static final String LIFECYCLE_Develop = "LF02";
/*      */   private static final String LIFECYCLE_Plan = "LF01";
/*  163 */   private ResourceBundle rsBundle = null;
/*  164 */   private PDGUtility pdgUtil = new PDGUtility();
/*  165 */   private OPICMList derivedDataAttList = new OPICMList();
/*  166 */   private Hashtable metaTbl = new Hashtable<>();
/*  167 */   private int totAvailBay = 0;
/*  168 */   private int totAvailCardSlot = 0;
/*  169 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  171 */   private Hashtable fcElemTbl = new Hashtable<>();
/*  172 */   private Vector errMsgVct = new Vector(1);
/*  173 */   private String strNow = null;
/*      */ 
/*      */ 
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
/*  186 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE + "<p><b>Date: </b>{2}<br /><b>User: </b>{3} ({4})<br /><b>Description: </b>{5}</p>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  192 */     String str2 = "";
/*      */     
/*  194 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  198 */       start_ABRBuild();
/*      */       
/*  200 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  202 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  203 */       addDebug("WWSEOABRDD entered for " + entityItem.getKey() + " extract " + this.m_abri
/*  204 */           .getVEName() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  207 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  213 */       str2 = getNavigationName(entityItem);
/*      */       
/*  215 */       this.rptSb.append("<h2>" + this.m_elist.getParentEntityGroup().getLongDescription() + " " + str2 + "</h2>" + NEWLINE);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  220 */       addHeading(3, " Domain and Model verification:");
/*  221 */       verifyDomainAndModel(entityItem);
/*  222 */       if (getReturnCode() == 0) {
/*      */ 
/*      */         
/*  225 */         performChecking(entityItem);
/*      */         
/*  227 */         this.strNow = this.m_db.getDates().getNow().substring(0, 10);
/*      */ 
/*      */         
/*  230 */         updateDerivedDataEntity(entityItem);
/*      */         
/*  232 */         if (getReturnCode() == 0)
/*      */         {
/*  234 */           postProcess(entityItem);
/*      */           
/*  236 */           String str = this.rsBundle.getString("SUCCESS");
/*  237 */           this.args[0] = this.m_elist.getEntityGroup("DERIVEDDATA").getLongDescription();
/*  238 */           MessageFormat messageFormat1 = new MessageFormat(str);
/*  239 */           this.rptSb.append("<p>" + messageFormat1.format(this.args) + "</p>" + NEWLINE);
/*      */         }
/*      */       
/*      */       } 
/*  243 */     } catch (Throwable throwable) {
/*      */       
/*  245 */       StringWriter stringWriter = new StringWriter();
/*  246 */       String str3 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  247 */       String str4 = "<pre>{0}</pre>";
/*  248 */       MessageFormat messageFormat1 = new MessageFormat(str3);
/*  249 */       setReturnCode(-1);
/*  250 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  252 */       this.args[0] = throwable.getMessage();
/*  253 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  254 */       messageFormat1 = new MessageFormat(str4);
/*  255 */       this.args[0] = stringWriter.getBuffer().toString();
/*  256 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  257 */       logError("Exception: " + throwable.getMessage());
/*  258 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  262 */       cleanUp();
/*  263 */       setDGTitle(str2);
/*  264 */       setDGRptName(getShortClassName(getClass()));
/*  265 */       setDGRptClass("WWSEOABRDD");
/*      */       
/*  267 */       if (!isReadOnly()) {
/*  268 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  274 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  275 */     this.args[0] = getShortClassName(getClass());
/*  276 */     this.args[1] = str2 + ((getReturnCode() == 0) ? " Passed" : " Failed");
/*  277 */     this.args[2] = getNow();
/*  278 */     this.args[3] = this.m_prof.getOPName();
/*  279 */     this.args[4] = this.m_prof.getRoleDescription();
/*  280 */     this.args[5] = getDescription();
/*  281 */     this.args[6] = getABRVersion();
/*      */     
/*  283 */     this.rptSb.insert(0, messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  285 */     println(this.rptSb.toString());
/*  286 */     printDGSubmitString();
/*  287 */     println(EACustom.getTOUDiv());
/*  288 */     buildReportFooter();
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
/*      */   private void postProcess(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  319 */     String str1 = PokUtils.getAttributeFlagValue(paramEntityItem, "STATUS");
/*  320 */     String str2 = PokUtils.getAttributeFlagValue(paramEntityItem, "LIFECYCLE");
/*  321 */     if (str2 == null || str2.length() == 0) {
/*  322 */       str2 = "LF01";
/*      */     }
/*  324 */     addDebug("postProcess: " + paramEntityItem.getKey() + " status " + str1 + " lifecycle " + str2);
/*      */     
/*  326 */     if ("0020".equals(str1) || ("0040"
/*  327 */       .equals(str1) && ("LF01"
/*  328 */       .equals(str2) || "LF02"
/*  329 */       .equals(str2)))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  335 */       boolean bool = "0020".equals(str1);
/*      */ 
/*      */       
/*  338 */       EntityList entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "DQVEWWSEOLSEO"), new EntityItem[] { paramEntityItem });
/*      */ 
/*      */ 
/*      */       
/*  342 */       addDebug("postProcess DQVEWWSEOLSEO: " + PokUtils.outputList(entityList));
/*  343 */       Vector vector = new Vector();
/*  344 */       String str3 = getQueuedValueForItem("ADSABRSTATUS");
/*  345 */       String str4 = getRFRQueuedValueForItem("ADSABRSTATUS");
/*  346 */       EntityGroup entityGroup = entityList.getEntityGroup("LSEO");
/*  347 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  348 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  349 */         str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  350 */         String str = PokUtils.getAttributeValue(entityItem, "LSEOUNPUBDATEMTRGT", "", "9999-12-31", false);
/*  351 */         addDebug("postProcess: " + entityItem.getKey() + " status " + str1 + " wdDate " + str);
/*      */         
/*  353 */         if (this.strNow.compareTo(str) < 0) {
/*      */           
/*  355 */           if ("0020".equals(str1)) {
/*  356 */             if (bool) {
/*      */               
/*  358 */               setFlagValue("ADSABRSTATUS", str3, entityItem, vector);
/*      */             } else {
/*  360 */               addDebug("postProcess: skipping final " + entityItem.getKey() + " wwseo is not final");
/*      */             } 
/*  362 */           } else if ("0040".equals(str1)) {
/*      */ 
/*      */ 
/*      */             
/*  366 */             str2 = PokUtils.getAttributeFlagValue(entityItem, "LIFECYCLE");
/*  367 */             addDebug("postProcess: " + entityItem.getKey() + " lifecycle " + str2);
/*  368 */             if (str2 == null || str2.length() == 0) {
/*  369 */               str2 = "LF01";
/*      */             }
/*  371 */             if ("LF01".equals(str2) || "LF02"
/*  372 */               .equals(str2)) {
/*      */               
/*  374 */               setFlagValue("ADSABRSTATUS", str4, entityItem, vector);
/*      */             } else {
/*  376 */               addDebug("postProcess: skipping rfr " + entityItem.getKey() + " lifecycle is not plan or develop");
/*      */             } 
/*      */           } 
/*      */         } else {
/*  380 */           addDebug("postProcess: skipping withdrawn " + entityItem.getKey());
/*      */         } 
/*      */       } 
/*      */       
/*  384 */       if (vector.size() > 0) {
/*  385 */         updatePDH(vector);
/*      */       }
/*  387 */       entityList.dereference();
/*      */       return;
/*      */     } 
/*      */     addDebug("postProcess: " + paramEntityItem.getKey() + " status and/or lifecycle criteria not met");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updatePDH(Vector paramVector) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  401 */     logMessage(getDescription() + " updating PDH");
/*  402 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + paramVector.size());
/*      */     
/*      */     try {
/*  405 */       this.m_db.update(this.m_prof, paramVector, false, false);
/*      */     } finally {
/*      */       
/*  408 */       paramVector.clear();
/*  409 */       this.m_db.commit();
/*  410 */       this.m_db.freeStatement();
/*  411 */       this.m_db.isPending("finally after updatePDH");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/*  418 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getQueuedValueForItem(String paramString) {
/*  426 */     String str1 = "LSEOABRSTATUS";
/*  427 */     String str2 = "_queuedValue";
/*  428 */     return ABRServerProperties.getValue(str1, "_" + paramString + str2, "0020");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getRFRQueuedValueForItem(String paramString) {
/*  437 */     String str1 = "LSEOABRSTATUS";
/*  438 */     String str2 = "_RFRqueuedValue";
/*  439 */     return ABRServerProperties.getValue(str1, "_" + paramString + str2, "0020");
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
/*      */   private void setFlagValue(String paramString1, String paramString2, EntityItem paramEntityItem, Vector<ReturnEntityKey> paramVector) throws SQLException, MiddlewareException {
/*  455 */     addDebug("setFlagValue entered " + paramEntityItem.getKey() + " for " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/*  458 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/*  459 */       addDebug("setFlagValue: " + paramString1 + " was blank for " + paramEntityItem.getKey() + ", it will be ignored");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  464 */     String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/*  465 */     if (paramString2.equals(str)) {
/*  466 */       addDebug("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  471 */     checkForInProcess(paramEntityItem, paramString1);
/*      */     
/*  473 */     if (this.m_cbOn == null) {
/*  474 */       setControlBlock();
/*      */     }
/*      */     
/*  477 */     ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/*  478 */     returnEntityKey.m_vctAttributes = new Vector();
/*  479 */     paramVector.addElement(returnEntityKey);
/*      */     
/*  481 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */     
/*  484 */     returnEntityKey.m_vctAttributes.addElement(singleFlag);
/*      */ 
/*      */     
/*  487 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("ATTR_SET"));
/*      */     
/*  489 */     this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString1, paramString1);
/*  490 */     this.args[1] = paramString2;
/*      */     
/*  492 */     EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/*  493 */     if (eANMetaFlagAttribute != null) {
/*  494 */       MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(paramString2);
/*  495 */       if (metaFlag != null) {
/*  496 */         this.args[1] = metaFlag.toString();
/*      */       }
/*      */     } else {
/*  499 */       addDebug("Error: " + paramString1 + " not found in META for " + paramEntityItem.getEntityType());
/*      */     } 
/*      */     
/*  502 */     this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*  503 */     this.args[3] = getNavigationName(paramEntityItem);
/*      */     
/*  505 */     addOutput(messageFormat.format(this.args));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkForInProcess(EntityItem paramEntityItem, String paramString) {
/*      */     try {
/*  516 */       byte b = 0;
/*      */       
/*  518 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*      */       
/*  520 */       addDebug("checkForInProcess:  entered " + paramEntityItem.getKey() + " " + paramString + " is " + str);
/*      */       
/*  522 */       if ("0050".equals(str)) {
/*  523 */         DatePackage datePackage = this.m_db.getDates();
/*      */         
/*  525 */         this.m_prof.setValOnEffOn(datePackage.getEndOfDay(), datePackage.getEndOfDay());
/*      */         
/*  527 */         while ("0050".equals(str) && b < 20) {
/*  528 */           b++;
/*  529 */           addDebug("checkForInProcess: " + paramString + " is " + str + " sleeping 30 secs");
/*  530 */           Thread.sleep(30000L);
/*      */           
/*  532 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Edit", false);
/*  533 */           EntityItem entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/*  534 */           str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/*  535 */           addDebug("checkForInProcess: " + paramString + " is now " + str + " after sleeping");
/*      */         } 
/*      */       } 
/*  538 */     } catch (Exception exception) {
/*  539 */       System.err.println("Exception in checkForInProcess " + exception);
/*  540 */       exception.printStackTrace();
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
/*      */   private void verifyDomainAndModel(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException {
/*  556 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("MODEL");
/*      */ 
/*      */ 
/*      */     
/*  560 */     EntityItem entityItem = null;
/*  561 */     EntityList entityList = null;
/*  562 */     boolean bool = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  567 */     if (entityGroup.getEntityItemCount() == 0) {
/*      */       
/*  569 */       entityList = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, "WWSEODDABRVE2"), new EntityItem[] { paramEntityItem });
/*      */ 
/*      */       
/*  572 */       entityGroup = entityList.getEntityGroup("MODEL");
/*  573 */       addDebug("DEBUG WWSEODDABRVE2: " + PokUtils.outputList(entityList));
/*      */     } 
/*      */     
/*  576 */     entityItem = entityGroup.getEntityItem(0);
/*      */     
/*  578 */     String str1 = getAttributeFlagEnabledValue(entityItem, "COFCAT");
/*  579 */     String str2 = getAttributeFlagEnabledValue(entityItem, "COFSUBCAT");
/*  580 */     String str3 = getAttributeFlagEnabledValue(entityItem, "COFGRP");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  585 */     bool = ("100".equals(str1) && ("126".equals(str2) || "162".equals(str2)) && "150".equals(str3)) ? true : false;
/*      */     
/*  587 */     if (!bool) {
/*  588 */       this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  589 */       printError("MODEL_CLASSIFICATION_ERROR", this.args);
/*  590 */       print3a(entityItem);
/*      */     } else {
/*  592 */       checkDomain(paramEntityItem);
/*      */     } 
/*      */     
/*  595 */     if (entityList != null) {
/*  596 */       entityList.dereference();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void addDebug(String paramString) {
/*  602 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */   
/*      */   private void addHeading(int paramInt, String paramString) {
/*  606 */     this.rptSb.append("<h" + paramInt + ">" + paramString + "</h" + paramInt + ">" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkDomain(EntityItem paramEntityItem) {
/*  616 */     boolean bool = false;
/*  617 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/*  618 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/*  619 */     if (str.equals("all")) {
/*  620 */       bool = true;
/*      */     } else {
/*  622 */       HashSet<String> hashSet = new HashSet();
/*  623 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/*  624 */       while (stringTokenizer.hasMoreTokens()) {
/*  625 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/*  627 */       bool = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/*  628 */       hashSet.clear();
/*      */     } 
/*      */     
/*  631 */     if (!bool) {
/*  632 */       addDebug("PDHDOMAIN did not include " + str + ", [" + 
/*  633 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*  634 */       this.args[0] = paramEntityItem.getEntityGroup().getLongDescription();
/*  635 */       this.args[1] = PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false);
/*  636 */       printError("DOMAIN_ERROR", this.args);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  647 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */ 
/*      */     
/*  651 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/*  652 */     if (eANList == null) {
/*      */       
/*  654 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/*  655 */       eANList = entityGroup.getMetaAttribute();
/*  656 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/*  658 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/*  660 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/*  661 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/*  662 */       if (b + 1 < eANList.size()) {
/*  663 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/*  667 */     return stringBuffer.toString().trim();
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
/*      */   private static Vector getAllLinkedEntities(EntityItem paramEntityItem, String paramString1, String paramString2) {
/*  684 */     Vector vector = new Vector(1);
/*  685 */     getLinkedEntities(paramEntityItem, paramString1, paramString2, vector);
/*  686 */     return vector;
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
/*      */   private String getLD_NDN(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  698 */     return paramEntityItem.getEntityGroup().getLongDescription() + " &quot;" + getNavigationName(paramEntityItem) + "&quot;";
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
/*      */   private static void getLinkedEntities(EntityItem paramEntityItem, String paramString1, String paramString2, Vector<EANEntity> paramVector) {
/*  713 */     if (paramEntityItem != null) {
/*      */       byte b;
/*  715 */       for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/*      */         
/*  717 */         EANEntity eANEntity = paramEntityItem.getUpLink(b);
/*  718 */         if (eANEntity.getEntityType().equals(paramString1))
/*      */         {
/*      */           
/*  721 */           for (byte b1 = 0; b1 < eANEntity.getUpLinkCount(); b1++) {
/*      */             
/*  723 */             EANEntity eANEntity1 = eANEntity.getUpLink(b1);
/*  724 */             if (eANEntity1.getEntityType().equals(paramString2)) {
/*  725 */               paramVector.addElement(eANEntity1);
/*      */             }
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/*  731 */       for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/*      */         
/*  733 */         EANEntity eANEntity = paramEntityItem.getDownLink(b);
/*  734 */         if (eANEntity.getEntityType().equals(paramString1))
/*      */         {
/*      */           
/*  737 */           for (byte b1 = 0; b1 < eANEntity.getDownLinkCount(); b1++) {
/*      */             
/*  739 */             EANEntity eANEntity1 = eANEntity.getDownLink(b1);
/*  740 */             if (eANEntity1.getEntityType().equals(paramString2)) {
/*  741 */               paramVector.addElement(eANEntity1);
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
/*      */   public String getDescription() {
/*  755 */     return "The ABR will derive a subset of the attributes for the WWSEO DERIVEDDATA and the available SLOTs and available BAYs.";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/*  765 */     return "1.29";
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
/*      */   private void performChecking(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  777 */     addHeading(3, this.m_elist.getEntityGroup("SLOTSAVAIL").getLongDescription() + " verification:");
/*  778 */     Hashtable hashtable = verifySlotsAvail(paramEntityItem);
/*  779 */     Vector vector = new Vector();
/*      */     
/*  781 */     addDebug("-----------------");
/*  782 */     getElements();
/*      */ 
/*      */     
/*  785 */     addDebug("-----------------");
/*  786 */     addHeading(3, this.m_elist.getEntityGroup("PLANAR").getLongDescription() + " checks:");
/*  787 */     checkPlanar(hashtable, vector);
/*      */ 
/*      */     
/*  790 */     addDebug("-----------------");
/*  791 */     addHeading(3, this.m_elist.getEntityGroup("MEMORYCARD").getLongDescription() + " checks:");
/*  792 */     checkMemoryCard(hashtable, vector);
/*      */ 
/*      */     
/*  795 */     addDebug("-----------------");
/*  796 */     addHeading(3, this.m_elist.getEntityGroup("EXPDUNIT").getLongDescription() + " checks:");
/*  797 */     checkExpansionUnit(hashtable, vector);
/*      */ 
/*      */     
/*  800 */     addHeading(3, this.m_elist.getEntityGroup("MECHPKG").getLongDescription() + " checks:");
/*  801 */     addDebug("-----------------");
/*  802 */     checkMechPkg(paramEntityItem);
/*      */ 
/*      */     
/*  805 */     addHeading(3, this.m_elist.getEntityGroup("SLOTSAVAIL").getLongDescription() + " checks:");
/*  806 */     Enumeration<String> enumeration = hashtable.keys();
/*  807 */     while (enumeration.hasMoreElements()) {
/*  808 */       String str = enumeration.nextElement();
/*  809 */       if (vector.contains(str)) {
/*      */         continue;
/*      */       }
/*  812 */       EntityItem entityItem = (EntityItem)hashtable.get(str);
/*  813 */       addDebug("performChecking: No match found for " + entityItem.getKey() + " with " + str);
/*      */ 
/*      */ 
/*      */       
/*  817 */       this.args[0] = entityItem.getEntityGroup().getLongDescription();
/*  818 */       this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "ELEMENTTYPE", "ELEMENTTYPE");
/*  819 */       this.args[2] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
/*  820 */       this.args[3] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "SLOTSZE", "SLOTSZE");
/*      */       
/*  822 */       printError("INVALID_SLOTAVAIL_ERROR", this.args);
/*  823 */       print3a(entityItem);
/*      */     } 
/*      */     
/*  826 */     vector.clear();
/*  827 */     hashtable.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Hashtable verifySlotsAvail(EntityItem paramEntityItem) {
/*  837 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "WWSEOSLOTSAVAIL", "SLOTSAVAIL");
/*  838 */     addDebug("verifySlotsAvail: Found " + vector.size() + " SLOTSAVAIL for " + paramEntityItem.getKey());
/*      */ 
/*      */     
/*  841 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/*  842 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("SLOTSAVAIL");
/*  843 */     for (byte b = 0; b < vector.size(); b++) {
/*  844 */       EntityItem entityItem = vector.elementAt(b);
/*  845 */       String str1 = getAttributeFlagEnabledValue(entityItem, "ELEMENTTYPE");
/*  846 */       String str2 = getAttributeFlagEnabledValue(entityItem, "SLOTTYPE");
/*  847 */       String str3 = getAttributeFlagEnabledValue(entityItem, "SLOTSZE");
/*  848 */       addDebug("verifySlotsAvail: Checking " + entityItem.getKey() + " elemtype: " + str1 + " slotType: " + str2 + " slotSze: " + str3);
/*      */       
/*  850 */       if (str1 == null) {
/*      */ 
/*      */ 
/*      */         
/*  854 */         this.args[0] = entityGroup.getLongDescription();
/*  855 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "ELEMENTTYPE", "ELEMENTTYPE");
/*  856 */         printError("ATTR_EMPTY_ERR", this.args);
/*  857 */         print3a(entityItem);
/*  858 */       } else if (str2 == null) {
/*      */ 
/*      */ 
/*      */         
/*  862 */         this.args[0] = entityGroup.getLongDescription();
/*  863 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "SLOTTYPE", "SLOTTYPE");
/*  864 */         printError("ATTR_EMPTY_ERR", this.args);
/*  865 */         print3a(entityItem);
/*  866 */       } else if (str3 == null) {
/*      */ 
/*      */ 
/*      */         
/*  870 */         this.args[0] = entityGroup.getLongDescription();
/*  871 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "SLOTSZE", "SLOTSZE");
/*  872 */         printError("ATTR_EMPTY_ERR", this.args);
/*  873 */         print3a(entityItem);
/*      */       } else {
/*  875 */         EntityItem entityItem1 = (EntityItem)hashtable.get(str1 + str2 + str3);
/*  876 */         if (entityItem1 != null) {
/*      */ 
/*      */ 
/*      */           
/*  880 */           this.args[0] = entityGroup.getLongDescription();
/*  881 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "ELEMENTTYPE", "ELEMENTTYPE");
/*  882 */           this.args[2] = PokUtils.getAttributeDescription(entityGroup, "SLOTTYPE", "SLOTTYPE");
/*  883 */           this.args[3] = PokUtils.getAttributeDescription(entityGroup, "SLOTSZE", "SLOTSZE");
/*      */           
/*  885 */           printError("DUPLICATE_SLOTAVAIL_ERROR", this.args);
/*  886 */           print3a(entityItem, false);
/*  887 */           print3a(entityItem1);
/*      */         } else {
/*  889 */           hashtable.put(str1 + str2 + str3, entityItem);
/*      */           
/*  891 */           String str = PokUtils.getAttributeValue(entityItem, "SLOTAVAIL", "", "0", false);
/*  892 */           int i = Integer.parseInt(str);
/*  893 */           this.totAvailCardSlot += i;
/*  894 */           addDebug("verifySlotsAvail: Adding " + entityItem.getKey() + " SLOTAVAIL:" + str + " to total:" + this.totAvailCardSlot);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  899 */     vector.clear();
/*      */     
/*  901 */     return hashtable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getElements() {
/*  910 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("WWSEOPRODSTRUCT");
/*      */     
/*  912 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  913 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/*  914 */       EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(0);
/*      */       
/*  916 */       for (byte b1 = 0; b1 < entityItem2.getUpLinkCount(); b1++) {
/*  917 */         EntityItem entityItem = (EntityItem)entityItem2.getUpLink(b1);
/*  918 */         if (entityItem.getEntityType().equals("FEATURE")) {
/*  919 */           EntityItem entityItem3 = entityItem;
/*  920 */           addDebug("getElements: Checking " + entityItem1.getKey() + " " + entityItem2.getKey() + " " + entityItem3
/*  921 */               .getKey());
/*  922 */           for (byte b2 = 0; b2 < entityItem3.getDownLinkCount(); b2++) {
/*  923 */             EntityItem entityItem4 = (EntityItem)entityItem3.getDownLink(b2);
/*  924 */             if (!entityItem4.getEntityType().equals("PRODSTRUCT"))
/*      */             {
/*      */               
/*  927 */               for (byte b3 = 0; b3 < entityItem4.getDownLinkCount(); b3++) {
/*  928 */                 EntityItem entityItem5 = (EntityItem)entityItem4.getDownLink(b3);
/*  929 */                 FCElement fCElement = new FCElement(entityItem1, entityItem2, entityItem3, entityItem4, entityItem5);
/*  930 */                 addDebug("getElements: Created " + fCElement + " confqty:" + fCElement.getConfQty() + " qty:" + fCElement.getQty());
/*  931 */                 Vector<FCElement> vector = (Vector)this.fcElemTbl.get(entityItem5.getEntityType());
/*  932 */                 if (vector == null) {
/*  933 */                   vector = new Vector(1);
/*  934 */                   this.fcElemTbl.put(entityItem5.getEntityType(), vector);
/*      */                 } 
/*  936 */                 vector.addElement(fCElement);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkPlanar(Hashtable paramHashtable, Vector paramVector) throws SQLException, MiddlewareException {
/*  971 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("PLANAR");
/*  972 */     if (entityGroup.getEntityItemCount() == 0) {
/*      */ 
/*      */       
/*  975 */       this.args[0] = entityGroup.getLongDescription();
/*      */       
/*  977 */       printError("ELEM_NOTFOUND_ERROR", this.args, true);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  984 */       Vector<FCElement> vector = (Vector)this.fcElemTbl.get("PLANAR");
/*  985 */       addDebug("checkPlanar entered for " + vector.size() + " PLANAR");
/*  986 */       for (byte b = 0; b < vector.size(); b++) {
/*  987 */         FCElement fCElement = vector.elementAt(b);
/*  988 */         EntityItem entityItem = fCElement.getElement();
/*  989 */         Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "PLANARSLOT", "SLOT");
/*  990 */         String str = PokUtils.getAttributeValue(entityItem, "TOTCARDSLOT", "", "0", false);
/*  991 */         if (vector1.size() == 0) {
/*  992 */           addDebug("checkPlanar " + entityItem.getKey() + " did not have any SLOTs TOTCARDSLOT=" + str);
/*  993 */           if (!str.equals("0")) {
/*      */             
/*  995 */             this.args[0] = entityGroup.getLongDescription();
/*  996 */             this.args[1] = this.m_elist.getEntityGroup("SLOT").getLongDescription();
/*  997 */             this.args[2] = PokUtils.getAttributeDescription(entityGroup, "TOTCARDSLOT", "TOTCARDSLOT");
/*  998 */             printError("INVALID_TOTCARDSLOT_ERR", this.args);
/*  999 */             print3a(entityItem);
/*      */           } else {
/* 1001 */             addHeading(4, getLD_NDN(entityItem) + " has No SLOTs");
/*      */           } 
/*      */         } else {
/*      */           
/* 1005 */           int i = Integer.parseInt(str);
/* 1006 */           int j = fCElement.checkSlots(paramHashtable, "0020", paramVector);
/* 1007 */           addDebug("checkplanar: " + fCElement.getElement().getKey() + ".TOTCARDSLOT=" + i + " totalSlotTot:" + j);
/*      */ 
/*      */           
/* 1010 */           if (j > i) {
/* 1011 */             String str1 = fCElement.getElement().getKey() + ":SLOT";
/*      */             
/* 1013 */             if (!this.errMsgVct.contains(str1)) {
/* 1014 */               this.errMsgVct.add(str1);
/* 1015 */               this.args[0] = this.m_elist.getEntityGroup("SLOT").getLongDescription();
/* 1016 */               this.args[1] = this.m_elist.getEntityGroup("PLANAR").getLongDescription();
/* 1017 */               printError("TOO_MANY_CHILDREN_ERR", this.args);
/* 1018 */               print3a(fCElement.getElement());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkMemoryCard(Hashtable paramHashtable, Vector paramVector) throws SQLException, MiddlewareException {
/* 1104 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get("MEMORYCARD");
/* 1105 */     addDebug("checkMemoryCard entered for " + ((vector == null) ? 0 : vector.size()) + " MEMORYCARD");
/* 1106 */     if (vector != null) {
/* 1107 */       for (byte b = 0; b < vector.size(); b++) {
/* 1108 */         FCElement fCElement = vector.elementAt(b);
/* 1109 */         EntityItem entityItem = fCElement.getElement();
/*      */         
/* 1111 */         String str = PokUtils.getAttributeValue(entityItem, "MEMRYCRDTOTALSLOTS", "", "0", false);
/* 1112 */         int i = Integer.parseInt(str);
/* 1113 */         int j = fCElement.checkSlots(paramHashtable, "0010", paramVector);
/* 1114 */         addDebug("checkMemoryCard " + entityItem.getKey() + ".MEMRYCRDTOTALSLOTS=" + i + " slotTotal:" + j);
/* 1115 */         if (j > i) {
/* 1116 */           this.args[0] = this.m_elist.getEntityGroup("SLOT").getLongDescription();
/* 1117 */           this.args[1] = this.m_elist.getEntityGroup("MEMORYCARD").getLongDescription();
/* 1118 */           printError("TOO_MANY_CHILDREN_ERR", this.args);
/* 1119 */           print3a(entityItem);
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
/*      */   private void checkExpansionUnit(Hashtable paramHashtable, Vector paramVector) throws SQLException, MiddlewareException {
/* 1137 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get("EXPDUNIT");
/* 1138 */     addDebug("checkExpansionUnit entered for " + ((vector == null) ? 0 : vector.size()) + " EXPDUNIT");
/* 1139 */     if (vector != null) {
/* 1140 */       for (byte b = 0; b < vector.size(); b++) {
/* 1141 */         FCElement fCElement = vector.elementAt(b);
/* 1142 */         EntityItem entityItem = fCElement.getElement();
/*      */         
/* 1144 */         String str = PokUtils.getAttributeValue(entityItem, "EXPNDUNITSOLTSTOT", "", "0", false);
/* 1145 */         int i = Integer.parseInt(str);
/*      */         
/* 1147 */         int j = fCElement.checkSlots(paramHashtable, "0030", paramVector);
/* 1148 */         addDebug("checkExpansionUnit " + entityItem.getKey() + ".EXPNDUNITSOLTSTOT=" + i + " slotTotal " + j);
/* 1149 */         if (j > i) {
/* 1150 */           this.args[0] = this.m_elist.getEntityGroup("SLOT").getLongDescription();
/* 1151 */           this.args[1] = this.m_elist.getEntityGroup("EXPDUNIT").getLongDescription();
/* 1152 */           printError("TOO_MANY_CHILDREN_ERR", this.args);
/* 1153 */           print3a(entityItem);
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
/*      */   private void checkMechPkg(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1190 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("BAYSAVAIL");
/*      */     
/* 1192 */     Hashtable hashtable = verifyBaysAvail(paramEntityItem);
/* 1193 */     Vector vector = new Vector();
/*      */     
/* 1195 */     Vector<FCElement> vector1 = new Vector(1);
/* 1196 */     Vector<FCElement> vector2 = new Vector();
/* 1197 */     Vector<FCElement> vector3 = (Vector)this.fcElemTbl.get("MECHPKG");
/* 1198 */     addDebug("checkMechPkg: entered for " + ((vector3 == null) ? 0 : vector3.size()) + " MECHPKG");
/* 1199 */     if (vector3 != null) {
/* 1200 */       for (byte b1 = 0; b1 < vector3.size(); b1++) {
/* 1201 */         FCElement fCElement = vector3.elementAt(b1);
/* 1202 */         EntityItem entityItem1 = fCElement.getElement();
/* 1203 */         EntityItem entityItem2 = fCElement.getFeature();
/* 1204 */         String str = getAttributeFlagEnabledValue(entityItem2, "HWFCCAT");
/* 1205 */         if ("273".equals(str) || "230"
/* 1206 */           .equals(str) || "240"
/* 1207 */           .equals(str) || "238"
/* 1208 */           .equals(str)) {
/* 1209 */           vector2.addElement(fCElement);
/* 1210 */           if ("273".equals(str)) {
/* 1211 */             vector1.add(fCElement);
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1220 */           addDebug("checkMechPkg: " + entityItem2.getKey() + " had " + entityItem1.getKey() + " but HWFCCAT was " + 
/* 1221 */               PokUtils.getAttributeValue(entityItem2, "HWFCCAT", "", "", false));
/* 1222 */           this.args[0] = this.m_elist.getEntityGroup("MECHPKG").getLongDescription();
/* 1223 */           printError("INVALID_HWFCCAT_ERR", this.args, true);
/* 1224 */           print3b(entityItem2, entityItem1);
/* 1225 */           this.rptSb.append("</p>" + NEWLINE);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1230 */     if (vector2.size() == 0) {
/*      */ 
/*      */ 
/*      */       
/* 1234 */       this.args[0] = this.m_elist.getEntityGroup("MECHPKG").getLongDescription();
/*      */       
/* 1236 */       printError("ELEM_NOTFOUND_ERROR", this.args, true);
/*      */     } 
/* 1238 */     if (vector1.size() > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1243 */       this.args[0] = this.m_elist.getEntityGroup("MECHPKG").getLongDescription();
/* 1244 */       printError("MAX_1_ERROR", this.args);
/* 1245 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1246 */         FCElement fCElement = vector1.elementAt(b1);
/* 1247 */         print3b(fCElement.getFeature(), fCElement.getElement());
/*      */       } 
/* 1249 */       this.rptSb.append("</p>" + NEWLINE);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1258 */     for (byte b = 0; b < vector2.size(); b++) {
/* 1259 */       FCElement fCElement = vector2.elementAt(b);
/* 1260 */       Vector<EntityItem> vector4 = getAllLinkedEntities(fCElement.getElement(), "MECHPKGBAY", "BAY");
/* 1261 */       if (vector4.size() > 0) {
/* 1262 */         String str = PokUtils.getAttributeValue(fCElement.getElement(), "TOTBAY", "", "0", false);
/* 1263 */         int i = Integer.parseInt(str);
/*      */         
/* 1265 */         int j = fCElement.checkBays(hashtable, vector);
/* 1266 */         addDebug("checkMechPkg: " + fCElement + " TOTBAY=" + str + " totalBayTot=" + j);
/* 1267 */         if (j > i) {
/* 1268 */           this.args[0] = this.m_elist.getEntityGroup("BAY").getLongDescription();
/* 1269 */           this.args[1] = fCElement.getElement().getEntityGroup().getLongDescription();
/* 1270 */           printError("TOO_MANY_CHILDREN_ERR", this.args);
/* 1271 */           print3a(fCElement.getElement(), false);
/* 1272 */           for (byte b1 = 0; b1 < vector4.size(); b1++) {
/* 1273 */             print3a(vector4.get(b1), false);
/*      */           }
/*      */           
/* 1276 */           this.rptSb.append("</p>" + NEWLINE);
/*      */         } 
/*      */       } 
/* 1279 */       vector4.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1283 */     for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/*      */       
/* 1285 */       String str = enumeration.nextElement();
/* 1286 */       if (vector.contains(str)) {
/*      */         continue;
/*      */       }
/*      */       
/* 1290 */       EntityItem entityItem = (EntityItem)hashtable.get(str);
/* 1291 */       EntityGroup entityGroup1 = this.m_elist.getEntityGroup("BAY");
/* 1292 */       this.args[0] = entityGroup.getLongDescription();
/* 1293 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup1, "BAYTYPE", "BAYTYPE");
/* 1294 */       this.args[2] = PokUtils.getAttributeDescription(entityGroup1, "ACCSS", "ACCSS");
/* 1295 */       this.args[3] = PokUtils.getAttributeDescription(entityGroup1, "BAYFF", "BAYFF");
/*      */       
/* 1297 */       printError("INVALID_BAYSAVAIL_ERROR", this.args);
/* 1298 */       print3a(entityItem);
/* 1299 */       addDebug("No BAY for " + entityItem.getKey() + " " + str);
/*      */     } 
/*      */     
/* 1302 */     vector.clear();
/* 1303 */     vector2.clear();
/* 1304 */     hashtable.clear();
/* 1305 */     vector1.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Hashtable verifyBaysAvail(EntityItem paramEntityItem) {
/* 1314 */     Vector<EntityItem> vector = getAllLinkedEntities(paramEntityItem, "WWSEOBAYSAVAIL", "BAYSAVAIL");
/* 1315 */     addDebug("verifyBaysAvail: Found " + vector.size() + " BAYSAVAIL for " + paramEntityItem.getKey());
/*      */ 
/*      */ 
/*      */     
/* 1319 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 1320 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("BAYSAVAIL");
/* 1321 */     for (byte b = 0; b < vector.size(); b++) {
/* 1322 */       EntityItem entityItem = vector.elementAt(b);
/* 1323 */       String str1 = getAttributeFlagEnabledValue(entityItem, "BAYAVAILTYPE");
/* 1324 */       String str2 = getAttributeFlagEnabledValue(entityItem, "ACCSS");
/* 1325 */       String str3 = getAttributeFlagEnabledValue(entityItem, "BAYFF");
/*      */       
/* 1327 */       addDebug("verifyBaysAvail: Checking " + entityItem.getKey() + " bayAvailType: " + str1 + " bayAccss: " + str2 + " bayFF:" + str3);
/* 1328 */       if (str1 == null || str2 == null || str3 == null) {
/*      */ 
/*      */ 
/*      */         
/* 1332 */         this.args[0] = entityGroup.getLongDescription();
/* 1333 */         if (str1 == null) {
/* 1334 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "BAYAVAILTYPE", "BAYAVAILTYPE");
/* 1335 */           printError("ATTR_EMPTY_ERR", this.args);
/*      */         } 
/* 1337 */         if (str2 == null) {
/* 1338 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "ACCSS", "ACCSS");
/* 1339 */           printError("ATTR_EMPTY_ERR", this.args);
/*      */         } 
/* 1341 */         if (str3 == null) {
/* 1342 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "BAYFF", "BAYFF");
/* 1343 */           printError("ATTR_EMPTY_ERR", this.args);
/*      */         } 
/* 1345 */         print3a(entityItem);
/*      */       } else {
/*      */         
/* 1348 */         String str = str1 + str2 + str3;
/* 1349 */         EntityItem entityItem1 = (EntityItem)hashtable.get(str);
/* 1350 */         if (entityItem1 != null) {
/*      */           
/* 1352 */           this.args[0] = entityGroup.getLongDescription();
/* 1353 */           this.args[1] = PokUtils.getAttributeDescription(entityGroup, "BAYAVAILTYPE", "BAYAVAILTYPE");
/* 1354 */           this.args[2] = PokUtils.getAttributeDescription(entityGroup, "ACCSS", "ACCSS");
/* 1355 */           this.args[3] = PokUtils.getAttributeDescription(entityGroup, "BAYFF", "BAYFF");
/*      */           
/* 1357 */           printError("DUPLICATE_BAYSAVAIL_ERROR", this.args);
/* 1358 */           print3a(entityItem, false);
/* 1359 */           print3a(entityItem1);
/*      */         }
/*      */         else {
/*      */           
/* 1363 */           String str4 = PokUtils.getAttributeValue(entityItem, "BAYAVAIL", "", "0", false);
/* 1364 */           hashtable.put(str, entityItem);
/* 1365 */           int i = Integer.parseInt(str4);
/* 1366 */           this.totAvailBay += i;
/* 1367 */           addDebug("verifyBaysAvail: Adding " + entityItem.getKey() + " BAYAVAIL:" + str4 + " to total:" + this.totAvailBay);
/*      */         } 
/*      */       } 
/*      */     } 
/* 1371 */     vector.clear();
/* 1372 */     return hashtable;
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
/*      */   private void updateDerivedDataEntity(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 1391 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/* 1392 */     if (entityGroup.getEntityItemCount() == 0) {
/*      */       
/* 1394 */       EntityItem entityItem = createDerivedDataEntity(paramEntityItem);
/*      */ 
/*      */       
/* 1397 */       setDerivedData(entityItem);
/*      */     }
/* 1399 */     else if (entityGroup.getEntityItemCount() == 1) {
/* 1400 */       EntityItem entityItem = entityGroup.getEntityItem(0);
/*      */       
/* 1402 */       setDerivedData(entityItem);
/*      */     }
/* 1404 */     else if (entityGroup.getEntityItemCount() > 1) {
/*      */       
/* 1406 */       this.args[0] = entityGroup.getLongDescription();
/* 1407 */       printError("MAX_1_ERROR", this.args);
/* 1408 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1409 */         print3a(entityGroup.getEntityItem(b), false);
/*      */       }
/* 1411 */       this.rptSb.append("</p>" + NEWLINE);
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
/*      */   private EntityItem createDerivedDataEntity(EntityItem paramEntityItem) throws MiddlewareRequestException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, SBRException {
/* 1434 */     EntityItem entityItem = null;
/*      */     
/*      */     try {
/* 1437 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, "DERIVEDDATA", "Edit", false);
/* 1438 */       String str1 = PokUtils.getAttributeValue(paramEntityItem, "COMNAME", "", "", false);
/* 1439 */       String str2 = getAttributeFlagValue(paramEntityItem, "PDHDOMAIN", ",");
/*      */       
/* 1441 */       this.derivedDataAttList.put("DERIVEDDATA:COMNAME", "COMNAME=" + str1);
/* 1442 */       this.derivedDataAttList.put("DERIVEDDATA:PDHDOMAIN", "PDHDOMAIN=" + str2);
/*      */       
/* 1444 */       entityItem = this.pdgUtil.createEntity(this.m_db, this.m_prof, "DERIVEDDATA", this.derivedDataAttList);
/*      */       
/* 1446 */       entityItem = new EntityItem(entityGroup, this.m_prof, this.m_db, entityItem.getEntityType(), entityItem.getEntityID());
/* 1447 */       this.pdgUtil.linkEntities(this.m_db, this.m_prof, paramEntityItem, new EntityItem[] { entityItem }, "WWSEODERIVEDDATA");
/*      */     } finally {
/* 1449 */       this.derivedDataAttList.remove("DERIVEDDATA:COMNAME");
/* 1450 */       this.derivedDataAttList.remove("DERIVEDDATA:PDHDOMAIN");
/*      */     } 
/* 1452 */     return entityItem;
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
/*      */   private void setDerivedData(EntityItem paramEntityItem) throws MiddlewareRequestException, MiddlewareException, SQLException, SBRException {
/* 1465 */     addDebug("-----------------");
/* 1466 */     getDDAvailSlotsTotalSlots();
/* 1467 */     addDebug("-----------------");
/* 1468 */     getDDAvailBaysTotalBays();
/* 1469 */     addDebug("-----------------");
/* 1470 */     getDDMemoryRAMStandard();
/* 1471 */     addDebug("-----------------");
/* 1472 */     getDDTotalL2CacheStandard();
/* 1473 */     addDebug("-----------------");
/* 1474 */     getDDNoOfProcStandard();
/* 1475 */     addDebug("-----------------");
/* 1476 */     getDDNoOfInstHardDrvs();
/* 1477 */     addDebug("-----------------");
/*      */     
/* 1479 */     displayDD();
/*      */     
/* 1481 */     this.pdgUtil.updateAttribute(this.m_db, this.m_prof, paramEntityItem, this.derivedDataAttList);
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
/*      */   private void getDDAvailSlotsTotalSlots() {
/* 1497 */     this.derivedDataAttList.put("DERIVEDDATA:TOTAVAILCARDSLOT", "TOTAVAILCARDSLOT=" + this.totAvailCardSlot);
/* 1498 */     addDebug("Calculate TOTDERIVEDSLOT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1505 */     int i = getSlotCardCount("MEMORYCARD", "MEMRYCRDTOTALSLOTS") + getSlotCardCount("PLANAR", "TOTCARDSLOT") + getSlotCardCount("EXPDUNIT", "TOTCARDSLOT");
/*      */     
/* 1507 */     this.derivedDataAttList.put("DERIVEDDATA:TOTDERIVEDSLOT", "TOTDERIVEDSLOT=" + i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getSlotCardCount(String paramString1, String paramString2) {
/* 1516 */     int i = 0;
/* 1517 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get(paramString1);
/* 1518 */     addDebug("getSlotCardCount for " + ((vector == null) ? 0 : vector.size()) + " " + paramString1);
/* 1519 */     if (vector != null) {
/* 1520 */       for (byte b = 0; b < vector.size(); b++) {
/* 1521 */         FCElement fCElement = vector.elementAt(b);
/* 1522 */         EntityItem entityItem = fCElement.getElement();
/*      */         
/* 1524 */         EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(paramString2);
/* 1525 */         if (eANMetaAttribute == null) {
/* 1526 */           setReturnCode(-1);
/* 1527 */           this.rptSb.append("<p><span style=\"color:#c00; font-weight:bold;\">Attribute &quot;" + paramString2 + "&quot; NOT found in &quot;" + entityItem
/*      */               
/* 1529 */               .getEntityType() + "&quot; META data.</span></p>");
/* 1530 */           return 0;
/*      */         } 
/*      */         
/* 1533 */         String str = PokUtils.getAttributeValue(entityItem, paramString2, "", "0", false);
/* 1534 */         int j = Integer.parseInt(str);
/* 1535 */         int k = fCElement.getQuantity();
/* 1536 */         addDebug("getSlotCardCount[" + b + "]: " + fCElement + " " + paramString2 + ":" + j + " qty:" + k + " element total:" + (j * k));
/*      */         
/* 1538 */         i += j * k;
/*      */       } 
/*      */     }
/* 1541 */     return i;
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
/*      */   private void getDDAvailBaysTotalBays() {
/* 1555 */     this.derivedDataAttList.put("DERIVEDDATA:TOTAVAILBAY", "TOTAVAILBAY=" + this.totAvailBay);
/*      */     
/* 1557 */     int i = 0;
/* 1558 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get("MECHPKG");
/* 1559 */     addDebug("Calculate TOTDERIVEDBAY for " + ((vector == null) ? 0 : vector.size()) + " MECHPKG");
/* 1560 */     if (vector != null) {
/* 1561 */       for (byte b = 0; b < vector.size(); b++) {
/* 1562 */         FCElement fCElement = vector.elementAt(b);
/* 1563 */         EntityItem entityItem = fCElement.getElement();
/* 1564 */         String str = PokUtils.getAttributeValue(entityItem, "TOTBAY", "", "0", false);
/* 1565 */         int j = Integer.parseInt(str);
/* 1566 */         int k = fCElement.getQuantity();
/* 1567 */         i += j * k;
/* 1568 */         addDebug(fCElement + " TOTBAY:" + j + " qty:" + k + " totderivedbay:" + i);
/*      */       } 
/*      */     }
/* 1571 */     this.derivedDataAttList.put("DERIVEDDATA:TOTDERIVEDBAY", "TOTDERIVEDBAY=" + i);
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
/*      */   private void getDDMemoryRAMStandard() {
/* 1646 */     long l1 = 0L;
/* 1647 */     long l2 = 0L;
/*      */     
/* 1649 */     String str1 = "";
/* 1650 */     String str2 = "0";
/* 1651 */     String str3 = "";
/*      */ 
/*      */     
/* 1654 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get("MEMORY");
/* 1655 */     addDebug("Calculate MEMRYRAMSTD for " + ((vector == null) ? 0 : vector.size()) + " MEMORY");
/* 1656 */     if (vector != null) {
/* 1657 */       for (byte b = 0; b < vector.size(); b++) {
/* 1658 */         FCElement fCElement = vector.elementAt(b);
/* 1659 */         addDebug("checking memory for " + fCElement);
/* 1660 */         EntityItem entityItem = fCElement.getElement();
/* 1661 */         String str4 = PokUtils.getAttributeValue(entityItem, "MEMRYCAP", "", "0", false);
/* 1662 */         String str5 = PokUtils.getAttributeValue(entityItem, "CAPUNIT", "", "", false);
/* 1663 */         addDebug(entityItem.getKey() + " MEMRYCAP=" + str4 + " CAPUNIT=" + str5 + " quantity:" + fCElement.getQuantity());
/*      */         
/* 1665 */         int i = Integer.parseInt(str4);
/* 1666 */         if (i > 0) {
/* 1667 */           if ("".equals(str5) || "--"
/* 1668 */             .equals(str5) || "M"
/* 1669 */             .equals(str5)) {
/*      */ 
/*      */             
/* 1672 */             this.args[0] = entityItem.getEntityGroup().getLongDescription();
/* 1673 */             this.args[1] = PokUtils.getAttributeDescription(entityItem.getEntityGroup(), "MEMRYCAP", "MEMRYCAP");
/* 1674 */             printError("INVALID_FLAG_VALUE_ERR", this.args);
/* 1675 */             print3b(fCElement.getFeature(), entityItem);
/* 1676 */             this.rptSb.append("</p>" + NEWLINE);
/*      */           } else {
/* 1678 */             str5 = str5.trim().toUpperCase();
/* 1679 */             l1 = getUnit(str5, "MEMORY.CAPUNIT", entityItem);
/* 1680 */             l2 += (fCElement.getQuantity() * i) * l1;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1687 */     if (l2 > 0L) {
/* 1688 */       str2 = convertResultToString(l2);
/* 1689 */       str1 = getUnitStr(l2);
/* 1690 */       addDebug("Total memory bytes: " + l2 + " converted:" + str2 + " units " + str1);
/*      */ 
/*      */       
/* 1693 */       str3 = getFlagCodeForDesc("MEMRYRAMSTDUNIT", str1);
/*      */       
/* 1695 */       if (null == str3)
/*      */       {
/* 1697 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/*      */         
/* 1699 */         str3 = "0030";
/* 1700 */         str2 = "0";
/* 1701 */         this.args[0] = entityGroup.getLongDescription();
/* 1702 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "MEMRYRAMSTDUNIT", "MEMRYRAMSTDUNIT");
/* 1703 */         this.args[2] = str1;
/* 1704 */         printError("INVALID_FLAGS_ERR", this.args, true);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1709 */       str3 = getFlagCodeForDesc("MEMRYRAMSTDUNIT", "MB");
/* 1710 */       if (null == str3) {
/*      */         
/* 1712 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/* 1713 */         str3 = "0030";
/* 1714 */         this.args[0] = entityGroup.getLongDescription();
/* 1715 */         this.args[1] = PokUtils.getAttributeDescription(entityGroup, "MEMRYRAMSTDUNIT", "MEMRYRAMSTDUNIT");
/* 1716 */         this.args[2] = str1;
/* 1717 */         printError("INVALID_FLAGS_ERR", this.args, true);
/*      */       } 
/*      */     } 
/*      */     
/* 1721 */     this.derivedDataAttList.put("DERIVEDDATA:MEMRYRAMSTD", "MEMRYRAMSTD=" + str2);
/* 1722 */     this.derivedDataAttList.put("DERIVEDDATA:MEMRYRAMSTDUNIT", "MEMRYRAMSTDUNIT=" + str3);
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
/* 1752 */   private String procL2CacheStr = null;
/* 1753 */   private String procL2CacheUnitStr = null;
/*      */ 
/*      */   
/*      */   private void getDDTotalL2CacheStandard() {
/* 1757 */     String str1 = "";
/* 1758 */     String str2 = "";
/*      */ 
/*      */     
/* 1761 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get("PROC");
/* 1762 */     addDebug("Calculate TOTL2CACHESTD for " + ((vector == null) ? 0 : vector.size()) + " PROC");
/* 1763 */     if (vector != null) {
/* 1764 */       for (byte b = 0; b < vector.size(); b++) {
/*      */ 
/*      */ 
/*      */         
/* 1768 */         FCElement fCElement = vector.elementAt(b);
/* 1769 */         checkProcL2Cache(fCElement);
/*      */       } 
/*      */     }
/*      */     
/* 1773 */     if (this.procL2CacheStr == null) {
/* 1774 */       this.procL2CacheStr = "0";
/*      */     }
/* 1776 */     if (this.procL2CacheUnitStr == null) {
/* 1777 */       this.procL2CacheUnitStr = "KB";
/*      */     }
/*      */     
/* 1780 */     str1 = getFlagCodeForDesc("TOTL2CACHESTD", this.procL2CacheStr);
/* 1781 */     if (null == str1) {
/* 1782 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/* 1783 */       str1 = "0010";
/* 1784 */       this.args[0] = entityGroup.getLongDescription();
/* 1785 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup, "TOTL2CACHESTD", "TOTL2CACHESTD");
/* 1786 */       this.args[2] = this.procL2CacheStr;
/* 1787 */       printError("INVALID_FLAGS_ERR", this.args, true);
/*      */     } 
/*      */     
/* 1790 */     str2 = getFlagCodeForDesc("TOTL2CACHESTDUNIT", this.procL2CacheUnitStr);
/* 1791 */     if (null == str2) {
/* 1792 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/* 1793 */       str2 = "0010";
/* 1794 */       this.args[0] = entityGroup.getLongDescription();
/* 1795 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup, "TOTL2CACHESTDUNIT", "TOTL2CACHESTDUNIT");
/* 1796 */       this.args[2] = this.procL2CacheUnitStr;
/* 1797 */       printError("INVALID_FLAGS_ERR", this.args, true);
/*      */     } 
/*      */     
/* 1800 */     this.derivedDataAttList.put("DERIVEDDATA:TOTL2CACHESTD", "TOTL2CACHESTD=" + str1);
/* 1801 */     this.derivedDataAttList.put("DERIVEDDATA:TOTL2CACHESTDUNIT", "TOTL2CACHESTDUNIT=" + str2);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkProcL2Cache(FCElement paramFCElement) {
/* 1806 */     addDebug("checkProcL2Cache entered for " + paramFCElement);
/* 1807 */     EntityItem entityItem1 = paramFCElement.getElement();
/* 1808 */     EntityItem entityItem2 = paramFCElement.getFeature();
/* 1809 */     String str1 = PokUtils.getAttributeValue(entityItem1, "PROCL2CACHE", "", "0", false);
/* 1810 */     String str2 = PokUtils.getAttributeValue(entityItem1, "PROCL2CACHEUNIT", "", "", false);
/* 1811 */     addDebug(entityItem1.getKey() + " PROCL2CACHE=" + str1 + " PROCL2CACHEUNIT " + str2);
/*      */ 
/*      */     
/* 1814 */     if ("".equals(str2)) {
/*      */ 
/*      */       
/* 1817 */       EntityGroup entityGroup = entityItem1.getEntityGroup();
/* 1818 */       this.args[0] = entityGroup.getLongDescription();
/* 1819 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup, "PROCL2CACHEUNIT", "PROCL2CACHEUNIT");
/* 1820 */       printError("ATTR_EMPTY_ERR", this.args);
/* 1821 */       print3b(paramFCElement.getFeature(), entityItem1);
/* 1822 */       this.rptSb.append("</p>" + NEWLINE);
/* 1823 */       str2 = "KB";
/*      */     } 
/* 1825 */     if (this.procL2CacheUnitStr == null) {
/* 1826 */       this.procL2CacheStr = str1;
/* 1827 */       this.procL2CacheUnitStr = str2.trim().toUpperCase();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1833 */     else if (!str1.equals(this.procL2CacheStr) || 
/* 1834 */       !str2.equals(this.procL2CacheUnitStr)) {
/*      */       
/* 1836 */       this.args[0] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "PROCL2CACHE", "PROCL2CACHE");
/* 1837 */       this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "PROCL2CACHEUNIT", "PROCL2CACHEUNIT");
/* 1838 */       this.args[2] = this.m_elist.getEntityGroup("PROC").getLongDescription();
/* 1839 */       printError("INVALID_L2CACHE_ERR", this.args);
/* 1840 */       print3b(entityItem2, entityItem1);
/* 1841 */       this.rptSb.append("</p>" + NEWLINE);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1846 */     Vector<EntityItem> vector = getAllLinkedEntities(entityItem2, "FEATUREPROC", "PROC");
/* 1847 */     if (vector.size() > 1) {
/* 1848 */       entityItem1 = vector.get(0);
/* 1849 */       String str = entityItem2.getKey() + ":PROC";
/* 1850 */       if (!this.errMsgVct.contains(str)) {
/* 1851 */         this.errMsgVct.add(str);
/* 1852 */         this.args[0] = this.m_elist.getEntityGroup("PROC").getLongDescription();
/* 1853 */         this.args[1] = this.m_elist.getEntityGroup("FEATURE").getLongDescription();
/* 1854 */         printError("TOO_MANY_CHILDREN_ERR", this.args);
/* 1855 */         print3b(entityItem2, entityItem1);
/* 1856 */         addDebug(entityItem2.getKey() + " Too many PROC: " + entityItem1.getKey());
/*      */         
/* 1858 */         for (byte b = 1; b < vector.size(); b++) {
/* 1859 */           entityItem1 = vector.get(b);
/* 1860 */           print3a(entityItem1, false);
/* 1861 */           addDebug(entityItem2.getKey() + " Too many PROC: " + entityItem1.getKey());
/*      */         } 
/* 1863 */         this.rptSb.append("</p>" + NEWLINE);
/*      */       } 
/*      */     } 
/*      */     
/* 1867 */     vector.clear();
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
/*      */   private long getUnit(String paramString1, String paramString2, EntityItem paramEntityItem) {
/* 1879 */     long l = 0L;
/*      */     
/* 1881 */     if ("KB".equals(paramString1)) {
/* 1882 */       l = 1000L;
/*      */     }
/* 1884 */     else if ("MB".equals(paramString1)) {
/* 1885 */       l = 1000000L;
/*      */     }
/* 1887 */     else if ("GB".equals(paramString1)) {
/* 1888 */       l = 1000000000L;
/*      */     } else {
/*      */       
/* 1891 */       this.args[0] = paramString2;
/*      */       
/* 1893 */       MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("INVALID_UNIT"));
/* 1894 */       this.rptSb.append("<p>" + messageFormat.format(this.args) + "<br />" + NEWLINE);
/* 1895 */       print3a(paramEntityItem);
/*      */     } 
/*      */     
/* 1898 */     return l;
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
/*      */   private String convertResultToString(long paramLong) {
/* 1916 */     String str = Long.toString(paramLong);
/* 1917 */     int i = str.length();
/*      */     
/* 1919 */     if (paramLong >= 1000000000L) {
/* 1920 */       str = str.substring(0, i - 8);
/* 1921 */       i = str.length();
/* 1922 */       str = str.substring(0, i - 1) + "." + str.substring(i - 1);
/*      */     }
/* 1924 */     else if (paramLong >= 1000000L) {
/* 1925 */       str = str.substring(0, i - 5);
/* 1926 */       i = str.length();
/* 1927 */       str = str.substring(0, i - 1) + "." + str.substring(i - 1);
/*      */     }
/* 1929 */     else if (paramLong >= 1000L) {
/* 1930 */       str = str.substring(0, i - 2);
/* 1931 */       i = str.length();
/* 1932 */       str = str.substring(0, i - 1) + "." + str.substring(i - 1);
/*      */     } 
/*      */     
/* 1935 */     i = str.length();
/* 1936 */     if (str.lastIndexOf("0") == i - 1) {
/* 1937 */       str = str.substring(0, i - 2);
/*      */     }
/*      */     
/* 1940 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getUnitStr(long paramLong) {
/* 1950 */     String str = "";
/*      */     
/* 1952 */     if (paramLong >= 1000000000L) {
/* 1953 */       str = "GB";
/*      */     }
/* 1955 */     else if (paramLong >= 1000000L) {
/* 1956 */       str = "MB";
/*      */     }
/* 1958 */     else if (paramLong >= 1000L) {
/* 1959 */       str = "KB";
/*      */     } 
/*      */     
/* 1962 */     return str;
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
/*      */   private void getDDNoOfProcStandard() {
/* 1994 */     int i = 0;
/* 1995 */     int j = 0;
/* 1996 */     int k = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2001 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get("PROC");
/* 2002 */     addDebug("Calculate NOOFPROCSTD for " + ((vector == null) ? 0 : vector.size()) + " PROC");
/* 2003 */     if (vector != null) {
/* 2004 */       for (byte b = 0; b < vector.size(); b++) {
/* 2005 */         FCElement fCElement = vector.elementAt(b);
/* 2006 */         i += fCElement.getQuantity();
/* 2007 */         addDebug("checking PROC[" + b + "] for " + fCElement + " quantity:" + fCElement.getQuantity() + " noOfProcStandard:" + i);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2013 */     vector = (Vector<FCElement>)this.fcElemTbl.get("EXPDUNIT");
/* 2014 */     addDebug("Calculate qtysum1 for " + ((vector == null) ? 0 : vector.size()) + " EXPDUNIT");
/* 2015 */     if (vector != null) {
/* 2016 */       for (byte b = 0; b < vector.size(); b++) {
/* 2017 */         FCElement fCElement = vector.elementAt(b);
/* 2018 */         addDebug("checking EXPDUNIT[" + b + "] for " + fCElement);
/*      */         
/* 2020 */         EANMetaAttribute eANMetaAttribute = fCElement.getElement().getEntityGroup().getMetaAttribute("NOOFPROCMAX");
/* 2021 */         if (eANMetaAttribute == null) {
/* 2022 */           setReturnCode(-1);
/* 2023 */           this.rptSb.append("<p><span style=\"color:#c00; font-weight:bold;\">Attribute &quot;NOOFPROCMAX&quot; NOT found in &quot;" + fCElement
/*      */               
/* 2025 */               .getElement().getEntityType() + "&quot; META data.</span></p>");
/* 2026 */           j = 0;
/*      */           break;
/*      */         } 
/* 2029 */         String str = PokUtils.getAttributeValue(fCElement.getElement(), "NOOFPROCMAX", "", "0", false);
/* 2030 */         int m = Integer.parseInt(str);
/* 2031 */         j += fCElement.getQuantity() * m;
/* 2032 */         addDebug(fCElement.getElement().getKey() + " NOOFPROCMAX=" + str + " quantity:" + fCElement.getQuantity() + " procQtySum1=" + j);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 2038 */     vector = (Vector<FCElement>)this.fcElemTbl.get("PLANAR");
/* 2039 */     addDebug("Calculate qtysum2 for " + ((vector == null) ? 0 : vector.size()) + " PLANAR");
/* 2040 */     if (vector != null) {
/* 2041 */       for (byte b = 0; b < vector.size(); b++) {
/* 2042 */         FCElement fCElement = vector.elementAt(b);
/* 2043 */         addDebug("checking PLANAR[" + b + "] for " + fCElement);
/* 2044 */         String str = PokUtils.getAttributeValue(fCElement.getElement(), "NOOFPROCMAX", "", "0", false);
/* 2045 */         int m = Integer.parseInt(str);
/* 2046 */         k += fCElement.getQuantity() * m;
/* 2047 */         addDebug(fCElement.getElement().getKey() + " NOOFPROCMAX=" + str + " quantity:" + fCElement
/* 2048 */             .getQuantity() + " procQtySum2=" + k);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2056 */     if (i > j + k) {
/* 2057 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/* 2058 */       this.args[0] = entityGroup.getLongDescription();
/* 2059 */       this.args[1] = PokUtils.getAttributeDescription(entityGroup, "NOOFPROCSTD", "NOOFPROCSTD");
/* 2060 */       entityGroup = this.m_elist.getEntityGroup("PLANAR");
/* 2061 */       this.args[2] = entityGroup.getLongDescription();
/* 2062 */       this.args[3] = PokUtils.getAttributeDescription(entityGroup, "NOOFPROCMAX", "NOOFPROCMAX");
/* 2063 */       entityGroup = this.m_elist.getEntityGroup("EXPDUNIT");
/* 2064 */       this.args[4] = entityGroup.getLongDescription();
/* 2065 */       this.args[5] = PokUtils.getAttributeDescription(entityGroup, "NOOFPROCMAX", "NOOFPROCMAX");
/* 2066 */       printError("NOOFPROCSTD_1_ERROR", this.args);
/* 2067 */       vector = (Vector<FCElement>)this.fcElemTbl.get("PLANAR");
/* 2068 */       if (vector != null) {
/* 2069 */         for (byte b = 0; b < vector.size(); b++) {
/* 2070 */           FCElement fCElement = vector.elementAt(b);
/* 2071 */           print3b(fCElement.getFeature(), fCElement.getElement());
/*      */         } 
/*      */       }
/* 2074 */       vector = (Vector<FCElement>)this.fcElemTbl.get("EXPDUNIT");
/* 2075 */       if (vector != null) {
/* 2076 */         for (byte b = 0; b < vector.size(); b++) {
/* 2077 */           FCElement fCElement = vector.elementAt(b);
/* 2078 */           print3b(fCElement.getFeature(), fCElement.getElement());
/*      */         } 
/*      */       }
/* 2081 */       vector = (Vector<FCElement>)this.fcElemTbl.get("PROC");
/* 2082 */       if (vector != null) {
/* 2083 */         for (byte b = 0; b < vector.size(); b++) {
/* 2084 */           FCElement fCElement = vector.elementAt(b);
/* 2085 */           print3b(fCElement.getFeature(), fCElement.getElement());
/*      */         } 
/*      */       }
/*      */       
/* 2089 */       this.rptSb.append("</p>" + NEWLINE);
/*      */     } 
/*      */ 
/*      */     
/* 2093 */     this.derivedDataAttList.put("DERIVEDDATA:NOOFPROCSTD", "NOOFPROCSTD=" + i);
/*      */     
/* 2095 */     this.derivedDataAttList.put("DERIVEDDATA:NOOFPROCMAX", "NOOFPROCMAX=" + (j + k));
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
/*      */   private void getDDNoOfInstHardDrvs() {
/* 2113 */     int i = 0;
/* 2114 */     Vector<FCElement> vector = (Vector)this.fcElemTbl.get("HDD");
/* 2115 */     addDebug("Calculate NOOFINSTHARDDRVS for " + ((vector == null) ? 0 : vector.size()) + " HDD");
/* 2116 */     if (vector != null) {
/* 2117 */       for (byte b = 0; b < vector.size(); b++) {
/* 2118 */         FCElement fCElement = vector.elementAt(b);
/* 2119 */         EntityItem entityItem = fCElement.getElement();
/* 2120 */         String str = PokUtils.getAttributeValue(entityItem, "HDDCAP", "", "0", false);
/* 2121 */         float f = Float.parseFloat(str);
/* 2122 */         if (f > 0.0F) {
/* 2123 */           i += fCElement.getQuantity();
/*      */         }
/* 2125 */         addDebug(entityItem.getKey() + ".HDDCAP=" + f + " quantity: " + fCElement.getQuantity() + " noOfInstHardDrvs: " + i);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 2130 */     this.derivedDataAttList.put("DERIVEDDATA:NOOFINSTHARDDRVS", "NOOFINSTHARDDRVS=" + i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationNameWithoutCountryList(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 2140 */     StringBuffer stringBuffer = new StringBuffer();
/*      */     
/* 2142 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 2143 */     if (eANList == null) {
/*      */       
/* 2145 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 2146 */       eANList = entityGroup.getMetaAttribute();
/* 2147 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 2149 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 2151 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 2152 */       if (!"COUNTRYLIST".equals(eANMetaAttribute.getAttributeCode())) {
/*      */         
/* 2154 */         if (stringBuffer.length() > 0) {
/* 2155 */           stringBuffer.append(", ");
/*      */         }
/* 2157 */         stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), "|", "", false));
/*      */       } 
/*      */     } 
/*      */     
/* 2161 */     return stringBuffer.toString();
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
/*      */   private void print3a(EntityItem paramEntityItem, boolean paramBoolean) {
/* 2175 */     StringBuffer stringBuffer = new StringBuffer("&nbsp;&nbsp;");
/* 2176 */     String str = paramEntityItem.getEntityType();
/* 2177 */     stringBuffer.append(paramEntityItem.getEntityGroup().getLongDescription());
/*      */     
/*      */     try {
/* 2180 */       str = getNavigationNameWithoutCountryList(paramEntityItem);
/* 2181 */     } catch (Exception exception) {
/* 2182 */       logMessage("print3a: " + paramEntityItem.getKey() + " Got exception " + exception);
/* 2183 */       exception.printStackTrace();
/*      */     } 
/*      */     
/* 2186 */     stringBuffer.append(" " + str);
/* 2187 */     if (paramBoolean) {
/* 2188 */       stringBuffer.append("</p>" + NEWLINE);
/*      */     } else {
/*      */       
/* 2191 */       stringBuffer.append("<br />" + NEWLINE);
/*      */     } 
/*      */     
/* 2194 */     this.rptSb.append(stringBuffer.toString());
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
/*      */   private void print3a(EntityItem paramEntityItem) {
/* 2207 */     print3a(paramEntityItem, true);
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
/*      */   private void print3b(EntityItem paramEntityItem1, EntityItem paramEntityItem2) {
/* 2221 */     StringBuffer stringBuffer = new StringBuffer("&nbsp;&nbsp;");
/* 2222 */     String str = paramEntityItem2.getEntityType();
/* 2223 */     stringBuffer.append(paramEntityItem1.getEntityGroup().getLongDescription() + " " + 
/* 2224 */         PokUtils.getAttributeValue(paramEntityItem1, "FEATURECODE", ", ", "", false));
/*      */     
/* 2226 */     stringBuffer.append(": " + paramEntityItem2.getEntityGroup().getLongDescription());
/*      */     
/*      */     try {
/* 2229 */       str = getNavigationNameWithoutCountryList(paramEntityItem2);
/* 2230 */     } catch (Exception exception) {
/* 2231 */       logMessage("print3b: Got exception " + exception);
/*      */     } 
/*      */     
/* 2234 */     stringBuffer.append(": " + str + "<br />" + NEWLINE);
/*      */     
/* 2236 */     this.rptSb.append(stringBuffer.toString());
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
/*      */   private String getFlagCodeForDesc(String paramString1, String paramString2) {
/* 2248 */     String str = null;
/*      */     
/* 2250 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("DERIVEDDATA");
/* 2251 */     EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)entityGroup.getMetaAttribute(paramString1);
/*      */     
/* 2253 */     if (null == eANMetaFlagAttribute) {
/*      */       
/* 2255 */       String str1 = this.rsBundle.getString("BAD_META");
/* 2256 */       this.args[0] = "DERIVEDDATA." + paramString1;
/* 2257 */       MessageFormat messageFormat = new MessageFormat(str1);
/* 2258 */       this.rptSb.append("<p>" + messageFormat.format(this.args) + "</p>" + NEWLINE);
/*      */     } else {
/*      */       
/* 2261 */       for (byte b = 0; b < eANMetaFlagAttribute.getMetaFlagCount(); b++) {
/* 2262 */         MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(b);
/* 2263 */         String str1 = metaFlag.toString().trim().toUpperCase();
/*      */         
/* 2265 */         if (paramString2.equals(str1)) {
/* 2266 */           str = metaFlag.getFlagCode();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2273 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Locale getLocale(int paramInt) {
/* 2283 */     Locale locale = null;
/* 2284 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 2287 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2311 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/*      */   public static String getAttributeFlagValue(EntityItem paramEntityItem, String paramString1, String paramString2) {
/* 2324 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/*      */     
/* 2326 */     EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 2327 */     String str = null;
/* 2328 */     if (eANAttribute != null && 
/* 2329 */       eANAttribute instanceof COM.ibm.eannounce.objects.EANFlagAttribute) {
/*      */       
/* 2331 */       StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */       
/* 2334 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANAttribute.get();
/* 2335 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */ 
/*      */         
/* 2338 */         if (arrayOfMetaFlag[b].isSelected()) {
/*      */           
/* 2340 */           if (stringBuffer.length() > 0)
/* 2341 */             stringBuffer.append(paramString2); 
/* 2342 */           stringBuffer.append(arrayOfMetaFlag[b].getFlagCode());
/* 2343 */           if (eANMetaAttribute.getAttributeType().equals("U"))
/*      */             break; 
/*      */         } 
/*      */       } 
/* 2347 */       str = stringBuffer.toString();
/*      */     } 
/*      */ 
/*      */     
/* 2351 */     return str;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayDD() {
/* 2360 */     Set set = this.derivedDataAttList.keySet();
/* 2361 */     Iterator<String> iterator = set.iterator();
/*      */     
/* 2363 */     this.rptSb.append("<!-- Content of derivedDataAttList" + NEWLINE);
/* 2364 */     while (iterator.hasNext()) {
/*      */       
/* 2366 */       String str = iterator.next();
/* 2367 */       this.rptSb.append(str + ", " + this.derivedDataAttList.get(str) + NEWLINE);
/*      */     } 
/* 2369 */     this.rptSb.append("-->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void cleanUp() {
/* 2378 */     if (this.fcElemTbl != null) {
/* 2379 */       for (Enumeration<Vector> enumeration = this.fcElemTbl.elements(); enumeration.hasMoreElements(); ) {
/* 2380 */         Vector<FCElement> vector = enumeration.nextElement();
/* 2381 */         for (byte b = 0; b < vector.size(); b++) {
/* 2382 */           FCElement fCElement = vector.elementAt(b);
/* 2383 */           fCElement.dereference();
/*      */         } 
/* 2385 */         vector.clear();
/*      */       } 
/* 2387 */       this.fcElemTbl.clear();
/* 2388 */       this.fcElemTbl = null;
/*      */     } 
/*      */     
/* 2391 */     this.errMsgVct.clear();
/* 2392 */     this.errMsgVct = null;
/* 2393 */     this.pdgUtil = null;
/* 2394 */     this.derivedDataAttList.clear();
/* 2395 */     this.derivedDataAttList = null;
/* 2396 */     this.metaTbl.clear();
/* 2397 */     this.metaTbl = null;
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
/*      */   private void printError(String paramString, Object[] paramArrayOfObject) {
/* 2409 */     printError(paramString, paramArrayOfObject, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void printError(String paramString, Object[] paramArrayOfObject, boolean paramBoolean) {
/* 2419 */     String str = this.rsBundle.getString(paramString);
/* 2420 */     MessageFormat messageFormat = new MessageFormat(str);
/* 2421 */     this.rptSb.append("<p>" + messageFormat.format(paramArrayOfObject));
/* 2422 */     if (paramBoolean) {
/* 2423 */       this.rptSb.append("</p>" + NEWLINE);
/*      */     } else {
/* 2425 */       this.rptSb.append("<br />" + NEWLINE);
/*      */     } 
/* 2427 */     setReturnCode(-1);
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
/*      */   private class FCElement
/*      */   {
/*      */     protected String key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2461 */     private int confQty = 1;
/* 2462 */     private int qty = 1;
/* 2463 */     private EntityItem elementItem = null;
/* 2464 */     private EntityItem featureItem = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     FCElement(EntityItem param1EntityItem1, EntityItem param1EntityItem2, EntityItem param1EntityItem3, EntityItem param1EntityItem4, EntityItem param1EntityItem5) {
/* 2475 */       this.key = param1EntityItem1.getKey() + ":" + param1EntityItem2.getKey() + ":" + param1EntityItem3.getKey() + ":" + param1EntityItem4.getKey() + ":" + param1EntityItem5.getKey();
/* 2476 */       this.elementItem = param1EntityItem5;
/* 2477 */       this.featureItem = param1EntityItem3;
/*      */       
/* 2479 */       String str = PokUtils.getAttributeValue(param1EntityItem1, "CONFQTY", "", "1", false);
/* 2480 */       this.confQty = Integer.parseInt(str);
/*      */       
/* 2482 */       EANMetaAttribute eANMetaAttribute = param1EntityItem4.getEntityGroup().getMetaAttribute("QTY");
/* 2483 */       if (eANMetaAttribute == null) {
/* 2484 */         WWSEOABRDD.this.addDebug("QTY not found in meta for " + param1EntityItem4.getKey());
/*      */       } else {
/*      */         
/* 2487 */         str = PokUtils.getAttributeValue(param1EntityItem4, "QTY", "", "1", false);
/* 2488 */         this.qty = Integer.parseInt(str);
/*      */       } 
/*      */     }
/*      */     
/*      */     void dereference() {
/* 2493 */       this.key = null;
/* 2494 */       this.elementItem = null;
/* 2495 */       this.featureItem = null;
/*      */     }
/* 2497 */     EntityItem getElement() { return this.elementItem; } EntityItem getFeature() {
/* 2498 */       return this.featureItem;
/*      */     } int getQuantity() {
/* 2500 */       return this.confQty * this.qty;
/*      */     }
/* 2502 */     int getConfQty() { return this.confQty; } int getQty() {
/* 2503 */       return this.qty;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int checkSlots(Hashtable param1Hashtable, String param1String, Vector<String> param1Vector) throws SQLException, MiddlewareException {
/* 2524 */       int i = 0;
/* 2525 */       String str1 = "SLOTQTY";
/* 2526 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2527 */       WWSEOABRDD.this.addDebug("checkSlots: entered for " + this);
/* 2528 */       String str2 = WWSEOABRDD.this.getLD_NDN(getElement());
/* 2529 */       if (getElement().getDownLinkCount() > 0) {
/*      */         
/* 2531 */         str2 = str2 + " " + ((EntityItem)getElement().getDownLink(0)).getEntityGroup().getLongDescription() + " checks:";
/*      */       } else {
/* 2533 */         str2 = str2 + " has No SLOTs";
/*      */       } 
/* 2535 */       WWSEOABRDD.this.addHeading(4, str2);
/*      */ 
/*      */       
/* 2538 */       for (byte b = 0; b < getElement().getDownLinkCount(); b++) {
/* 2539 */         EntityItem entityItem = (EntityItem)getElement().getDownLink(b);
/* 2540 */         EANMetaAttribute eANMetaAttribute = entityItem.getEntityGroup().getMetaAttribute(str1);
/* 2541 */         if (eANMetaAttribute == null) {
/*      */           
/* 2543 */           WWSEOABRDD.this.addDebug("checkSlots[" + b + "]: " + getElement().getKey() + ":" + entityItem.getKey() + " 'Quantity' not found in meta");
/*      */         } else {
/* 2545 */           String str = PokUtils.getAttributeValue(entityItem, str1, "", "1", false);
/* 2546 */           WWSEOABRDD.this.addDebug("checkSlots[" + b + "]: " + getElement().getKey() + ":" + entityItem.getKey() + " qty: " + str);
/* 2547 */           int j = Integer.parseInt(str);
/* 2548 */           if (j > 1) {
/* 2549 */             WWSEOABRDD.this.addDebug("checkSlots: Error qty>1 qty: " + str + " on " + entityItem.getKey());
/* 2550 */             EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/*      */ 
/*      */ 
/*      */             
/* 2554 */             WWSEOABRDD.this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 2555 */             WWSEOABRDD.this.printError("INVALID_QTY_ERROR", WWSEOABRDD.this.args);
/* 2556 */             WWSEOABRDD.this.print3b(getFeature(), getElement());
/* 2557 */             WWSEOABRDD.this.print3a(entityItem1);
/*      */           } 
/*      */         } 
/* 2560 */         for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 2561 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/*      */           
/* 2563 */           String str3 = WWSEOABRDD.this.getAttributeFlagEnabledValue(entityItem1, "SLOTTYPE");
/* 2564 */           String str4 = WWSEOABRDD.this.getAttributeFlagEnabledValue(entityItem1, "SLOTSZE");
/*      */           
/* 2566 */           WWSEOABRDD.this.addDebug("checkSlots: Checking " + getElement().getKey() + ":" + entityItem.getKey() + ":" + entityItem1
/* 2567 */               .getKey() + " slotType:" + str3 + " slotSze:" + str4);
/* 2568 */           if (str3 == null) {
/*      */ 
/*      */ 
/*      */             
/* 2572 */             WWSEOABRDD.this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 2573 */             WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
/* 2574 */             WWSEOABRDD.this.printError("ATTR_EMPTY_ERR", WWSEOABRDD.this.args);
/* 2575 */             WWSEOABRDD.this.print3a(entityItem1);
/* 2576 */           } else if (str4 == null) {
/*      */ 
/*      */ 
/*      */             
/* 2580 */             WWSEOABRDD.this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 2581 */             WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "SLOTSZE", "SLOTSZE");
/* 2582 */             WWSEOABRDD.this.printError("ATTR_EMPTY_ERR", WWSEOABRDD.this.args);
/* 2583 */             WWSEOABRDD.this.print3a(entityItem1);
/*      */           } else {
/* 2585 */             EntityItem entityItem2 = (EntityItem)hashtable.get(str3 + str4);
/* 2586 */             if (entityItem2 == null) {
/* 2587 */               hashtable.put(str3 + str4, entityItem1);
/* 2588 */               EntityItem entityItem3 = (EntityItem)param1Hashtable.get(param1String + str3 + str4);
/* 2589 */               if (entityItem3 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2594 */                 WWSEOABRDD.this.args[0] = WWSEOABRDD.this.m_elist.getEntityGroup("SLOTSAVAIL").getLongDescription();
/* 2595 */                 WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(WWSEOABRDD.this.m_elist.getEntityGroup("SLOTSAVAIL"), "ELEMENTTYPE", "ELEMENTTYPE");
/* 2596 */                 WWSEOABRDD.this.args[2] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
/* 2597 */                 WWSEOABRDD.this.args[3] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "SLOTSZE", "SLOTSZE");
/*      */                 
/* 2599 */                 WWSEOABRDD.this.printError("NO_SLOTSAVAIL_ERROR", WWSEOABRDD.this.args);
/* 2600 */                 WWSEOABRDD.this.print3b(getFeature(), getElement());
/* 2601 */                 WWSEOABRDD.this.print3a(entityItem1);
/*      */               } else {
/* 2603 */                 param1Vector.add(param1String + str3 + str4);
/* 2604 */                 String str = PokUtils.getAttributeValue(entityItem1, "SLOTTOT", "", "0", false);
/* 2605 */                 int j = Integer.parseInt(str);
/* 2606 */                 i += j;
/* 2607 */                 WWSEOABRDD.this.addDebug("checkSlots: adding " + entityItem1.getKey() + ".SLOTTOT=" + str + " totalSlotTot:" + i);
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/* 2612 */               WWSEOABRDD.this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 2613 */               WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "SLOTTYPE", "SLOTTYPE");
/* 2614 */               WWSEOABRDD.this.args[2] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "SLOTSZE", "SLOTSZE");
/*      */               
/* 2616 */               WWSEOABRDD.this.printError("DUPLICATE_SLOT_ERROR", WWSEOABRDD.this.args);
/* 2617 */               WWSEOABRDD.this.print3b(getFeature(), getElement());
/* 2618 */               WWSEOABRDD.this.print3a(entityItem1, false);
/* 2619 */               WWSEOABRDD.this.print3a(entityItem2);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2625 */       hashtable.clear();
/* 2626 */       return i;
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
/*      */     int checkBays(Hashtable param1Hashtable, Vector<String> param1Vector) throws SQLException, MiddlewareException {
/* 2638 */       int i = 0;
/* 2639 */       String str1 = "BAYQTY";
/* 2640 */       Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2641 */       WWSEOABRDD.this.addDebug("checkBays: entered for " + this);
/* 2642 */       String str2 = WWSEOABRDD.this.getLD_NDN(getElement());
/* 2643 */       if (getElement().getDownLinkCount() > 0) {
/*      */         
/* 2645 */         str2 = str2 + " " + ((EntityItem)getElement().getDownLink(0)).getEntityGroup().getLongDescription() + " checks:";
/*      */       } else {
/* 2647 */         str2 = str2 + " has No BAYs";
/*      */       } 
/* 2649 */       WWSEOABRDD.this.addHeading(4, str2);
/*      */       
/* 2651 */       for (byte b = 0; b < getElement().getDownLinkCount(); b++) {
/* 2652 */         EntityItem entityItem = (EntityItem)getElement().getDownLink(b);
/* 2653 */         String str = PokUtils.getAttributeValue(entityItem, str1, "", "1", false);
/* 2654 */         WWSEOABRDD.this.addDebug("checkBays[" + b + "]: " + getElement().getKey() + ":" + entityItem.getKey() + " qty: " + str);
/* 2655 */         int j = Integer.parseInt(str);
/* 2656 */         if (j > 1) {
/* 2657 */           WWSEOABRDD.this.addDebug("checkBays: Error qty>1 qty: " + str + " on " + entityItem.getKey());
/* 2658 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(0);
/*      */ 
/*      */           
/* 2661 */           WWSEOABRDD.this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 2662 */           WWSEOABRDD.this.printError("INVALID_QTY_ERROR", WWSEOABRDD.this.args);
/* 2663 */           WWSEOABRDD.this.print3b(getFeature(), getElement());
/* 2664 */           WWSEOABRDD.this.print3a(entityItem1);
/*      */         } 
/*      */         
/* 2667 */         for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 2668 */           EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/*      */           
/* 2670 */           String str3 = WWSEOABRDD.this.getAttributeFlagEnabledValue(entityItem1, "BAYTYPE");
/* 2671 */           String str4 = WWSEOABRDD.this.getAttributeFlagEnabledValue(entityItem1, "ACCSS");
/* 2672 */           String str5 = WWSEOABRDD.this.getAttributeFlagEnabledValue(entityItem1, "BAYFF");
/*      */           
/* 2674 */           WWSEOABRDD.this.addDebug("checkBays: Checking " + getElement().getKey() + " " + entityItem.getKey() + " " + entityItem1
/* 2675 */               .getKey() + " bayType:" + str3 + " bayAccss:" + str4 + " bayFF:" + str5);
/* 2676 */           if (str3 == null || str4 == null || str5 == null) {
/*      */ 
/*      */ 
/*      */             
/* 2680 */             WWSEOABRDD.this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 2681 */             if (str3 == null) {
/* 2682 */               WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "BAYTYPE", "BAYTYPE");
/* 2683 */               WWSEOABRDD.this.printError("ATTR_EMPTY_ERR", WWSEOABRDD.this.args);
/*      */             } 
/* 2685 */             if (str4 == null) {
/* 2686 */               WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "ACCSS", "ACCSS");
/* 2687 */               WWSEOABRDD.this.printError("ATTR_EMPTY_ERR", WWSEOABRDD.this.args);
/*      */             } 
/* 2689 */             if (str5 == null) {
/* 2690 */               WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "BAYFF", "BAYFF");
/* 2691 */               WWSEOABRDD.this.printError("ATTR_EMPTY_ERR", WWSEOABRDD.this.args);
/*      */             } 
/* 2693 */             WWSEOABRDD.this.print3a(entityItem1);
/*      */           } else {
/*      */             
/* 2696 */             String str6 = str3 + str4 + str5;
/* 2697 */             EntityItem entityItem2 = (EntityItem)hashtable.get(str6);
/* 2698 */             if (entityItem2 == null) {
/* 2699 */               hashtable.put(str6, entityItem1);
/* 2700 */               EntityItem entityItem3 = (EntityItem)param1Hashtable.get(str6);
/* 2701 */               if (entityItem3 == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/* 2706 */                 WWSEOABRDD.this.args[0] = WWSEOABRDD.this.m_elist.getEntityGroup("BAYSAVAIL").getLongDescription();
/* 2707 */                 WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "BAYTYPE", "BAYTYPE");
/* 2708 */                 WWSEOABRDD.this.args[2] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "ACCSS", "ACCSS");
/* 2709 */                 WWSEOABRDD.this.args[3] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "BAYFF", "BAYFF");
/*      */ 
/*      */                 
/* 2712 */                 WWSEOABRDD.this.printError("NO_BAYSAVAIL_ERROR", WWSEOABRDD.this.args);
/* 2713 */                 WWSEOABRDD.this.print3b(getFeature(), getElement());
/* 2714 */                 WWSEOABRDD.this.print3a(entityItem1);
/*      */               } else {
/* 2716 */                 param1Vector.add(str6);
/* 2717 */                 String str7 = PokUtils.getAttributeValue(entityItem1, "BAYTOT", "", "0", false);
/* 2718 */                 int k = Integer.parseInt(str7);
/* 2719 */                 i += k;
/* 2720 */                 WWSEOABRDD.this.addDebug("checkBays: adding " + entityItem1.getKey() + ".BAYTOT=" + str7 + " totalBayTot:" + i);
/*      */               
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 2727 */               WWSEOABRDD.this.args[0] = entityItem1.getEntityGroup().getLongDescription();
/* 2728 */               WWSEOABRDD.this.args[1] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "BAYTYPE", "BAYTYPE");
/* 2729 */               WWSEOABRDD.this.args[2] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "ACCSS", "ACCSS");
/* 2730 */               WWSEOABRDD.this.args[3] = PokUtils.getAttributeDescription(entityItem1.getEntityGroup(), "BAYFF", "BAYFF");
/*      */ 
/*      */               
/* 2733 */               WWSEOABRDD.this.printError("DUPLICATE_BAYS_ERROR", WWSEOABRDD.this.args);
/* 2734 */               WWSEOABRDD.this.print3b(getFeature(), getElement());
/* 2735 */               WWSEOABRDD.this.print3a(entityItem1, false);
/* 2736 */               WWSEOABRDD.this.print3a(entityItem2);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/* 2741 */       hashtable.clear();
/* 2742 */       return i;
/*      */     }
/*      */     public String toString() {
/* 2745 */       return this.key;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\WWSEOABRDD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */