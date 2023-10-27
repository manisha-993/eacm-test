/*      */ package COM.ibm.eannounce.abr.sg;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryGroup;
/*      */ import COM.ibm.eannounce.objects.AttributeChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.ChangeHistoryItem;
/*      */ import COM.ibm.eannounce.objects.EANAttribute;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
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
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.rmi.RemoteException;
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.ResourceBundle;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  183 */   private StringBuffer rptSb = new StringBuffer();
/*  184 */   private Hashtable metaTbl = new Hashtable<>();
/*      */   
/*  186 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  187 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*      */   private static final Hashtable STATUS_TBL;
/*      */   private static final Hashtable DQ_TBL;
/*  191 */   private ResourceBundle dqBundle = null;
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
/*      */   protected static final String ABR_INPROCESS = "0050";
/*      */ 
/*      */   
/*      */   protected static final String SAPLABR_QUEUED = "0020";
/*      */ 
/*      */   
/*      */   protected static final String ABRWAITODS = "0020";
/*      */ 
/*      */   
/*      */   protected static final String ABRWAITODS2 = "0020";
/*      */ 
/*      */   
/*      */   protected static final String SAPL_NOTREADY = "10";
/*      */   
/*      */   protected static final String SAPL_NA = "90";
/*      */   
/*      */   protected static final String PLANNEDAVAIL = "146";
/*      */   
/*      */   protected static final String LASTORDERAVAIL = "149";
/*      */   
/*      */   protected static final String FIRSTORDERAVAIL = "143";
/*      */   
/*      */   private static final int PAUSE_TIME = 5000;
/*      */   
/*      */   private static final int DATE_ID = 11;
/*      */   
/*  262 */   private String failedStr = "Failed";
/*  263 */   private String dqCheck = "Failed";
/*      */   
/*  265 */   private Vector vctReturnsEntityKeys = new Vector();
/*      */   
/*      */   private static final Hashtable SAPL_TRANS_TBL;
/*  268 */   private String navName = ""; private boolean bdomainInList = false;
/*      */   protected boolean domainInList() {
/*  270 */     return this.bdomainInList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getQueuedValue(String paramString) {
/*  278 */     return 
/*  279 */       ABRServerProperties.getABRQueuedValue(this.m_abri
/*  280 */         .getABRCode() + "_" + paramString);
/*      */   }
/*      */   
/*      */   protected String getRFRQueuedValue(String paramString) {
/*  284 */     return 
/*  285 */       ABRServerProperties.getABRRFRQueuedValue(this.m_abri
/*  286 */         .getABRCode() + "_" + paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  291 */   private static final Hashtable NDN_TBL = new Hashtable<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String PROD_FIX_DTS = "2008-03-12-00.00.00.000000";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  302 */     NDN nDN1 = new NDN("MODEL", "(TM)");
/*  303 */     nDN1.addAttr("MACHTYPEATR");
/*  304 */     nDN1.addAttr("MODELATR");
/*  305 */     nDN1.addAttr("COFCAT");
/*  306 */     nDN1.addAttr("COFSUBCAT");
/*  307 */     nDN1.addAttr("COFGRP");
/*  308 */     nDN1.addAttr("COFSUBGRP");
/*  309 */     NDN nDN2 = new NDN("FEATURE", "(FC)");
/*  310 */     nDN2.addAttr("FEATURECODE");
/*  311 */     nDN1.setNext(nDN2);
/*  312 */     NDN_TBL.put("PRODSTRUCT", nDN1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  324 */     nDN1 = new NDN("MODEL", "(TM)");
/*  325 */     nDN1.addAttr("MACHTYPEATR");
/*  326 */     nDN1.addAttr("MODELATR");
/*  327 */     nDN1.addAttr("COFCAT");
/*  328 */     nDN1.addAttr("COFSUBCAT");
/*  329 */     nDN1.addAttr("COFGRP");
/*  330 */     nDN1.addAttr("COFSUBGRP");
/*  331 */     nDN2 = new NDN("SWFEATURE", "(FC)");
/*  332 */     nDN2.addAttr("FEATURECODE");
/*  333 */     nDN1.setNext(nDN2);
/*  334 */     NDN_TBL.put("SWPRODSTRUCT", nDN1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  346 */     nDN1 = new NDN("MODEL", "(TM)");
/*  347 */     nDN1.addAttr("MACHTYPEATR");
/*  348 */     nDN1.addAttr("MODELATR");
/*  349 */     nDN1.addAttr("COFCAT");
/*  350 */     nDN1.addAttr("COFSUBCAT");
/*  351 */     nDN1.addAttr("COFGRP");
/*  352 */     nDN1.addAttr("COFSUBGRP");
/*  353 */     nDN2 = new NDN("SVCFEATURE", "(FC)");
/*  354 */     nDN2.addAttr("FEATURECODE");
/*  355 */     nDN1.setNext(nDN2);
/*  356 */     NDN_TBL.put("SVCPRODSTRUCT", nDN1);
/*      */ 
/*      */     
/*  359 */     SAPL_TRANS_TBL = new Hashtable<>();
/*  360 */     SAPL_TRANS_TBL.put("20", "40");
/*  361 */     SAPL_TRANS_TBL.put("30", "40");
/*  362 */     SAPL_TRANS_TBL.put("40", "80");
/*  363 */     SAPL_TRANS_TBL.put("80", "70");
/*      */     
/*  365 */     STATUS_TBL = new Hashtable<>();
/*  366 */     STATUS_TBL.put("DRAFT", "0010");
/*  367 */     STATUS_TBL.put("FINAL", "0020");
/*  368 */     STATUS_TBL.put("REVIEW", "0040");
/*  369 */     STATUS_TBL.put("0050", "0050");
/*      */     
/*  371 */     DQ_TBL = new Hashtable<>();
/*  372 */     DQ_TBL.put("0010", "DRAFT");
/*  373 */     DQ_TBL.put("0020", "FINAL");
/*  374 */     DQ_TBL.put("0040", "REVIEW");
/*  375 */     DQ_TBL.put("0050", "0050");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ResourceBundle getBundle() {
/*  384 */     return this.dqBundle;
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
/*      */   public void execute_run() {
/*  411 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  413 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Data quality check: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  425 */     String str3 = null;
/*  426 */     String str4 = null;
/*  427 */     String str5 = null;
/*  428 */     String[] arrayOfString = new String[10];
/*  429 */     println(EACustom.getDocTypeHtml());
/*      */     
/*      */     try {
/*  432 */       String str = "";
/*  433 */       long l = System.currentTimeMillis();
/*      */       
/*  435 */       start_ABRBuild(false);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  440 */       EntityItem entityItem = checkTimestamps();
/*      */       
/*  442 */       str5 = getAttributeFlagEnabledValue(entityItem, "DATAQUALITY");
/*  443 */       str4 = getAttributeFlagEnabledValue(entityItem, getStatusAttrCode());
/*  444 */       addDebug(getStatusAttrCode() + ": " + 
/*  445 */           PokUtils.getAttributeValue(entityItem, getStatusAttrCode(), ", ", "", false) + " [" + str4 + "] DATAQUALITY: " + 
/*      */           
/*  447 */           PokUtils.getAttributeValue(entityItem, "DATAQUALITY", ", ", "", false) + " [" + str5 + "] ");
/*      */       
/*  449 */       if (null == str4 || str4.length() == 0) {
/*  450 */         str4 = "0020";
/*      */       }
/*  452 */       if (null == str5 || str5.length() == 0) {
/*  453 */         str5 = "0020";
/*      */       }
/*      */ 
/*      */       
/*  457 */       domainNeedsChecks(entityItem);
/*      */ 
/*      */       
/*  460 */       if (isVEneeded(str4)) {
/*  461 */         str = this.m_abri.getVEName();
/*      */         
/*  463 */         this.m_elist = this.m_db.getEntityList(entityItem.getProfile(), new ExtractActionItem(null, this.m_db, entityItem
/*  464 */               .getProfile(), str), new EntityItem[] { new EntityItem(null, entityItem
/*  465 */                 .getProfile(), getEntityType(), getEntityID()) });
/*      */       } else {
/*      */         
/*  468 */         str = "dummy";
/*      */       } 
/*      */       
/*  471 */       addDebug("Time to get VE: " + (System.currentTimeMillis() - l) + " (mseconds)");
/*      */       
/*  473 */       setControlBlock();
/*      */ 
/*      */       
/*  476 */       this.dqBundle = ResourceBundle.getBundle(DQABRSTATUS.class.getName(), getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */ 
/*      */       
/*  479 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + getEntityType() + ":" + getEntityID() + " extract: " + str + " valon: " + entityItem
/*  480 */           .getProfile().getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */       
/*  483 */       setReturnCode(0);
/*      */       
/*  485 */       this.failedStr = this.dqBundle.getString("FAILED");
/*  486 */       this.dqCheck = this.failedStr;
/*      */ 
/*      */       
/*  489 */       this.navName = getNavigationName();
/*      */ 
/*      */       
/*  492 */       entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*  493 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  503 */       if (str4.equals("0020")) {
/*  504 */         addDebug("Status already final, bypassing checks");
/*      */         
/*  506 */         this.dqCheck = this.dqBundle.getString("NOT_REQ");
/*  507 */         doAlreadyFinalProcessing();
/*      */       } else {
/*      */         
/*  510 */         if (domainInList()) {
/*  511 */           doDQChecking(entityItem, str4);
/*      */         } else {
/*  513 */           addDebug("No checking required for domain");
/*      */         } 
/*      */         
/*  516 */         if (getReturnCode() == 0) {
/*      */           
/*  518 */           String str7 = (String)STATUS_TBL.get(str5);
/*  519 */           setFlagValue(this.m_elist.getProfile(), getStatusAttrCode(), str7);
/*  520 */           this.dqCheck = this.dqBundle.getString("PASSED");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  525 */           if (str7.equals("0020")) {
/*  526 */             completeNowFinalProcessing();
/*      */           
/*      */           }
/*  529 */           else if (str7.equals("0040")) {
/*  530 */             completeNowR4RProcessing();
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
/*  542 */           String str7 = (String)DQ_TBL.get(str4);
/*      */           
/*  544 */           for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/*  545 */             ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*  546 */             if (returnEntityKey.getEntityID() == getEntityID() && returnEntityKey
/*  547 */               .getEntityType().equals(getEntityType())) {
/*  548 */               returnEntityKey.m_vctAttributes.clear();
/*      */               break;
/*      */             } 
/*      */           } 
/*  552 */           setFlagValue(this.m_elist.getProfile(), "DATAQUALITY", str7);
/*      */         } 
/*      */       } 
/*      */       
/*  556 */       updatePDH(this.m_elist.getProfile());
/*      */     }
/*  558 */     catch (Throwable throwable) {
/*      */       
/*  560 */       StringWriter stringWriter = new StringWriter();
/*  561 */       String str7 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  562 */       String str8 = "<pre>{0}</pre>";
/*  563 */       MessageFormat messageFormat1 = new MessageFormat(str7);
/*  564 */       setReturnCode(-3);
/*  565 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  567 */       arrayOfString[0] = throwable.getMessage();
/*  568 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/*  569 */       messageFormat1 = new MessageFormat(str8);
/*  570 */       arrayOfString[0] = stringWriter.getBuffer().toString();
/*  571 */       this.rptSb.append(messageFormat1.format(arrayOfString) + NEWLINE);
/*  572 */       logError("Exception: " + throwable.getMessage());
/*  573 */       logError(stringWriter.getBuffer().toString());
/*      */ 
/*      */       
/*  576 */       if (str4 != null) {
/*  577 */         String str = (String)DQ_TBL.get(str4);
/*      */         
/*      */         try {
/*  580 */           for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/*  581 */             ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*  582 */             if (returnEntityKey.getEntityID() == getEntityID() && returnEntityKey
/*  583 */               .getEntityType().equals(getEntityType())) {
/*  584 */               returnEntityKey.m_vctAttributes.clear();
/*      */               break;
/*      */             } 
/*      */           } 
/*  588 */           setFlagValue(this.m_prof, "DATAQUALITY", str);
/*  589 */           updatePDH(this.m_elist.getProfile());
/*  590 */         } catch (Exception exception) {}
/*      */       }
/*      */     
/*      */     } finally {
/*      */       
/*  595 */       setDGTitle(this.navName);
/*  596 */       setDGRptName(getShortClassName(getClass()));
/*  597 */       setDGRptClass(getABRCode());
/*      */       
/*  599 */       if (!isReadOnly())
/*      */       {
/*  601 */         clearSoftLock();
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  607 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  608 */     arrayOfString[0] = getShortClassName(getClass());
/*  609 */     arrayOfString[1] = this.navName;
/*  610 */     String str6 = messageFormat.format(arrayOfString);
/*  611 */     messageFormat = new MessageFormat(str2);
/*  612 */     arrayOfString[0] = this.m_prof.getOPName();
/*  613 */     arrayOfString[1] = this.m_prof.getRoleDescription();
/*  614 */     arrayOfString[2] = this.m_prof.getWGName();
/*  615 */     arrayOfString[3] = getNow();
/*  616 */     arrayOfString[4] = str3;
/*      */     
/*  618 */     arrayOfString[5] = this.dqCheck;
/*  619 */     arrayOfString[6] = getABRVersion();
/*      */     
/*  621 */     this.rptSb.insert(0, str6 + messageFormat.format(arrayOfString) + NEWLINE);
/*      */     
/*  623 */     println(this.rptSb.toString());
/*  624 */     printDGSubmitString();
/*  625 */     println(EACustom.getTOUDiv());
/*  626 */     buildReportFooter();
/*      */     
/*  628 */     this.metaTbl.clear();
/*      */   }
/*      */ 
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
/*      */ 
/*      */   
/*      */   protected void completeNowR4RProcessing() throws SQLException, MiddlewareException, MiddlewareRequestException {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void doAlreadyFinalProcessing() {}
/*      */ 
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
/*      */   
/*      */   protected String getStatusAttrCode() {
/*  673 */     return "STATUS";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isVEneeded(String paramString) {
/*  679 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addOutput(String paramString) {
/*  685 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
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
/*  697 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*  698 */     setReturnCode(-1);
/*      */ 
/*      */     
/*  701 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("ERROR_PREFIX"));
/*  702 */     Object[] arrayOfObject = new Object[2];
/*  703 */     arrayOfObject[0] = entityGroup.getLongDescription();
/*  704 */     arrayOfObject[1] = this.navName;
/*      */     
/*  706 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
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
/*      */   protected void addWarning(String paramString, Object[] paramArrayOfObject) {
/*  720 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*      */ 
/*      */     
/*  723 */     MessageFormat messageFormat = new MessageFormat(getBundle().getString("WARNING_PREFIX"));
/*  724 */     Object[] arrayOfObject = new Object[2];
/*  725 */     arrayOfObject[0] = entityGroup.getLongDescription();
/*  726 */     arrayOfObject[1] = this.navName;
/*      */     
/*  728 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/*  737 */     String str = getBundle().getString(paramString2);
/*      */     
/*  739 */     if (paramArrayOfObject != null) {
/*  740 */       MessageFormat messageFormat = new MessageFormat(str);
/*  741 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/*  744 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void addDebug(String paramString) {
/*  750 */     this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getRootEntityItem(Profile paramProfile) throws SQLException, MiddlewareException {
/*  757 */     this.m_elist = this.m_db.getEntityList(paramProfile, new ExtractActionItem(null, this.m_db, paramProfile, "dummy"), new EntityItem[] { new EntityItem(null, paramProfile, 
/*      */             
/*  759 */             getEntityType(), getEntityID()) });
/*      */     
/*  761 */     return this.m_elist.getParentEntityGroup().getEntityItem(0);
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
/*  773 */     EntityItem entityItem = getRootEntityItem(getProfile());
/*      */     
/*  775 */     if (!checkTimestamps(entityItem)) {
/*      */       
/*      */       try {
/*  778 */         addDebug("Pausing for 5000 ms");
/*  779 */         Thread.sleep(5000L);
/*  780 */       } catch (InterruptedException interruptedException) {
/*  781 */         System.out.println(interruptedException);
/*      */       } 
/*      */ 
/*      */       
/*  785 */       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS000");
/*  786 */       String str = simpleDateFormat.format(Calendar.getInstance().getTime()).substring(0, 11) + "23.59.59.999990";
/*      */       
/*  788 */       Profile profile = this.m_prof.getNewInstance(this.m_db);
/*  789 */       profile.setValOnEffOn(str, str);
/*  790 */       addDebug("ABR started before DATAQUALITY was updated, pull extract again using valon: " + profile
/*  791 */           .getValOn());
/*      */       
/*  793 */       entityItem = getRootEntityItem(profile);
/*      */     } 
/*      */     
/*  796 */     return entityItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean checkTimestamps(EntityItem paramEntityItem) throws Exception {
/*  805 */     boolean bool = true;
/*      */     
/*  807 */     Profile profile = paramEntityItem.getProfile();
/*      */ 
/*      */ 
/*      */     
/*  811 */     String str3 = profile.getValOn();
/*      */     
/*  813 */     String str1 = getTimestamp(paramEntityItem, getABRCode());
/*  814 */     String str2 = getTimestamp(paramEntityItem, "DATAQUALITY");
/*      */     
/*  816 */     addDebug("DTS for " + getABRCode() + ": " + str1 + " DATAQUALITY: " + str2);
/*  817 */     addDebug("DTS for valon: " + str3 + " effon " + profile.getEffOn());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  822 */     if (str2.length() > 0 && str3
/*  823 */       .compareTo(str2) < 0)
/*      */     {
/*  825 */       bool = false;
/*      */     }
/*      */     
/*  828 */     return bool;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getTimestamp(EntityItem paramEntityItem, String paramString) throws Exception {
/*  838 */     RowSelectableTable rowSelectableTable = new RowSelectableTable((EANTableWrapper)paramEntityItem, paramEntityItem.getLongDescription());
/*      */     
/*  840 */     String str1 = "";
/*  841 */     String str2 = paramEntityItem.getEntityType() + ":" + paramString;
/*  842 */     int i = rowSelectableTable.getRowIndex(str2);
/*  843 */     if (i < 0) {
/*  844 */       i = rowSelectableTable.getRowIndex(str2 + ":C");
/*      */     }
/*  846 */     if (i < 0) {
/*  847 */       i = rowSelectableTable.getRowIndex(str2 + ":R");
/*      */     }
/*  849 */     if (i != -1) {
/*      */       
/*  851 */       EANAttribute eANAttribute = (EANAttribute)rowSelectableTable.getEANObject(i, 1);
/*  852 */       if (eANAttribute != null) {
/*      */         
/*  854 */         byte b = 0;
/*  855 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_prof, eANAttribute);
/*  856 */         this.rptSb.append("<!--" + paramEntityItem.getKey() + " " + paramString + " change history "); int j;
/*  857 */         for (j = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; j >= 0; j--) {
/*      */           
/*  859 */           ChangeHistoryItem changeHistoryItem = attributeChangeHistoryGroup.getChangeHistoryItem(j);
/*  860 */           this.rptSb.append(NEWLINE + "AttrChangeHistoryItem[" + j + "] chgDate: " + changeHistoryItem.getChangeDate() + " value: " + changeHistoryItem
/*      */               
/*  862 */               .get("ATTVAL", true).toString() + " flagcode: " + changeHistoryItem
/*  863 */               .getFlagCode() + " user: " + changeHistoryItem
/*  864 */               .getUser());
/*  865 */           b++;
/*  866 */           if (b > 3) {
/*      */             break;
/*      */           }
/*      */         } 
/*  870 */         this.rptSb.append(" -->" + NEWLINE);
/*  871 */         if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/*  872 */           j = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1;
/*  873 */           str1 = attributeChangeHistoryGroup.getChangeHistoryItem(j).getChangeDate();
/*      */         } 
/*      */       } else {
/*      */         
/*  877 */         addDebug("EANAttribute was null for " + paramString + " in " + paramEntityItem.getKey());
/*      */       } 
/*      */     } else {
/*      */       
/*  881 */       addDebug("Row for " + paramString + " was not found for " + paramEntityItem.getKey());
/*      */     } 
/*      */     
/*  884 */     return str1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName() throws SQLException, MiddlewareException {
/*  894 */     return getNavigationName(this.m_elist.getParentEntityGroup().getEntityItem(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/*  904 */     StringBuffer stringBuffer = new StringBuffer();
/*  905 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem.getEntityType());
/*      */ 
/*      */     
/*  908 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/*  909 */     if (eANList == null) {
/*      */       
/*  911 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/*  912 */       eANList = entityGroup.getMetaAttribute();
/*  913 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/*  915 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/*  917 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/*  918 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/*  919 */       if (b + 1 < eANList.size()) {
/*  920 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*  923 */     if (nDN != null) {
/*  924 */       EntityList entityList = null;
/*  925 */       StringBuffer stringBuffer1 = new StringBuffer();
/*  926 */       EntityItem entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       
/*  928 */       if (paramEntityItem.getEntityType().equals("PRODSTRUCT") && 
/*  929 */         entityItem == null) {
/*  930 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType() + " pulling small VE for this " + paramEntityItem
/*  931 */             .getKey());
/*      */         
/*  933 */         String str = "EXRPT3FM";
/*  934 */         entityList = this.m_db.getEntityList(paramEntityItem.getProfile(), new ExtractActionItem(null, this.m_db, paramEntityItem
/*  935 */               .getProfile(), str), new EntityItem[] { new EntityItem(null, paramEntityItem
/*  936 */                 .getProfile(), paramEntityItem.getEntityType(), paramEntityItem
/*  937 */                 .getEntityID()) });
/*      */         
/*  939 */         paramEntityItem = entityList.getParentEntityGroup().getEntityItem(0);
/*  940 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*      */       } 
/*      */ 
/*      */       
/*  944 */       if (entityItem != null) {
/*  945 */         stringBuffer1.append("(" + nDN.getTag());
/*  946 */         for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/*  947 */           String str = nDN.getAttr().elementAt(b1).toString();
/*  948 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/*  949 */           if (b1 + 1 < nDN.getAttr().size()) {
/*  950 */             stringBuffer1.append(" ");
/*      */           }
/*      */         } 
/*  953 */         stringBuffer1.append(") ");
/*      */       } else {
/*  955 */         addDebug("NO entity found for ndn.getEntityType(): " + nDN.getEntityType());
/*      */       } 
/*  957 */       nDN = nDN.getNext();
/*  958 */       if (nDN != null) {
/*  959 */         entityItem = getNDNitem(paramEntityItem, nDN.getEntityType());
/*  960 */         if (entityItem != null) {
/*  961 */           stringBuffer1.append("(" + nDN.getTag());
/*  962 */           for (byte b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/*  963 */             String str = nDN.getAttr().elementAt(b1).toString();
/*  964 */             stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/*  965 */             if (b1 + 1 < nDN.getAttr().size()) {
/*  966 */               stringBuffer1.append(" ");
/*      */             }
/*      */           } 
/*  969 */           stringBuffer1.append(") ");
/*      */         } else {
/*  971 */           addDebug("NO entity found for next ndn.getEntityType(): " + nDN.getEntityType());
/*      */         } 
/*      */       } 
/*  974 */       stringBuffer.insert(0, stringBuffer1.toString());
/*      */       
/*  976 */       if (entityList != null) {
/*  977 */         entityList.dereference();
/*      */       }
/*      */     } 
/*      */     
/*  981 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getTMFNavigationName(EntityItem paramEntityItem1, EntityItem paramEntityItem2, EntityItem paramEntityItem3) throws SQLException, MiddlewareException {
/*  991 */     StringBuffer stringBuffer = new StringBuffer();
/*  992 */     NDN nDN = (NDN)NDN_TBL.get(paramEntityItem1.getEntityType());
/*      */ 
/*      */     
/*  995 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem1.getEntityType());
/*  996 */     if (eANList == null) {
/*      */       
/*  998 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem1.getEntityType(), "Navigate");
/*  999 */       eANList = entityGroup.getMetaAttribute();
/* 1000 */       this.metaTbl.put(paramEntityItem1.getEntityType(), eANList);
/*      */     } 
/* 1002 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 1004 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 1005 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem1, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 1006 */       if (b + 1 < eANList.size()) {
/* 1007 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/* 1010 */     if (nDN != null) {
/* 1011 */       StringBuffer stringBuffer1 = new StringBuffer();
/* 1012 */       EntityItem entityItem = paramEntityItem2;
/* 1013 */       stringBuffer1.append("(" + nDN.getTag()); byte b1;
/* 1014 */       for (b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1015 */         String str = nDN.getAttr().elementAt(b1).toString();
/* 1016 */         stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1017 */         if (b1 + 1 < nDN.getAttr().size()) {
/* 1018 */           stringBuffer1.append(" ");
/*      */         }
/*      */       } 
/* 1021 */       stringBuffer1.append(") ");
/*      */       
/* 1023 */       nDN = nDN.getNext();
/* 1024 */       if (nDN != null) {
/* 1025 */         entityItem = paramEntityItem3;
/* 1026 */         stringBuffer1.append("(" + nDN.getTag());
/* 1027 */         for (b1 = 0; b1 < nDN.getAttr().size(); b1++) {
/* 1028 */           String str = nDN.getAttr().elementAt(b1).toString();
/* 1029 */           stringBuffer1.append(PokUtils.getAttributeValue(entityItem, str, ", ", "", false));
/* 1030 */           if (b1 + 1 < nDN.getAttr().size()) {
/* 1031 */             stringBuffer1.append(" ");
/*      */           }
/*      */         } 
/* 1034 */         stringBuffer1.append(") ");
/*      */       } 
/* 1036 */       stringBuffer.insert(0, stringBuffer1.toString());
/*      */     } 
/*      */     
/* 1039 */     return stringBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private EntityItem getNDNitem(EntityItem paramEntityItem, String paramString) {
/*      */     byte b;
/* 1049 */     for (b = 0; b < paramEntityItem.getDownLinkCount(); b++) {
/* 1050 */       EntityItem entityItem = (EntityItem)paramEntityItem.getDownLink(b);
/* 1051 */       if (entityItem.getEntityType().equals(paramString)) {
/* 1052 */         return entityItem;
/*      */       }
/*      */     } 
/* 1055 */     for (b = 0; b < paramEntityItem.getUpLinkCount(); b++) {
/* 1056 */       EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(b);
/* 1057 */       if (entityItem.getEntityType().equals(paramString)) {
/* 1058 */         return entityItem;
/*      */       }
/*      */     } 
/* 1061 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 1071 */     return "1.31";
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
/* 1085 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 1086 */     addDebug("setTextValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/* 1089 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1090 */     if (eANMetaAttribute == null) {
/* 1091 */       addDebug("setTextValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 1092 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 1093 */           .getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1097 */     if (paramString2 != null) {
/* 1098 */       if (this.m_cbOn == null) {
/* 1099 */         setControlBlock();
/*      */       }
/* 1101 */       ControlBlock controlBlock = this.m_cbOn;
/* 1102 */       if (paramString2.length() == 0) {
/* 1103 */         EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 1104 */         String str = eANAttribute.getEffFrom();
/* 1105 */         controlBlock = new ControlBlock(str, str, str, str, paramProfile.getOPWGID());
/* 1106 */         paramString2 = eANAttribute.toString();
/* 1107 */         addDebug("setTextValue deactivating " + paramString1);
/*      */       } 
/* 1109 */       Vector<Text> vector = null;
/*      */       
/* 1111 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1112 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1113 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1114 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1115 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1119 */       if (vector == null) {
/*      */         
/* 1121 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1122 */         vector = new Vector();
/* 1123 */         returnEntityKey.m_vctAttributes = vector;
/* 1124 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/* 1127 */       Text text = new Text(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, controlBlock);
/* 1128 */       vector.addElement(text);
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
/* 1142 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2);
/* 1143 */     addDebug("setFlagValue entered " + paramEntityItem.getKey() + " for " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/* 1146 */     if (paramString2 != null && paramString2.trim().length() == 0) {
/* 1147 */       addDebug("setFlagValue: " + paramString1 + " was blank for " + paramEntityItem.getKey() + ", it will be ignored");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1152 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1153 */     if (eANMetaAttribute == null) {
/* 1154 */       addDebug("setFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 1155 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 1156 */           .getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1160 */     if (paramString2 != null) {
/*      */ 
/*      */ 
/*      */       
/* 1164 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString1);
/* 1165 */       if (paramString2.equals(str)) {
/* 1166 */         addDebug("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/* 1167 */         logMessage("setFlagValue: " + paramString1 + " was already set to " + str + " for " + paramEntityItem.getKey() + ", nothing to do");
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1172 */       if (eANMetaAttribute.getAttributeType().equals("A"))
/*      */       {
/* 1174 */         checkForInProcess(paramProfile, paramEntityItem, paramString1);
/*      */       }
/*      */       
/* 1177 */       if (this.m_cbOn == null) {
/* 1178 */         setControlBlock();
/*      */       }
/* 1180 */       Vector<SingleFlag> vector = null;
/*      */       
/* 1182 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1183 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1184 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1185 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1186 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1190 */       if (vector == null) {
/* 1191 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1192 */         vector = new Vector();
/* 1193 */         returnEntityKey.m_vctAttributes = vector;
/* 1194 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/* 1197 */       SingleFlag singleFlag = new SingleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */       
/* 1200 */       vector.addElement(singleFlag);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkForInProcess(Profile paramProfile, EntityItem paramEntityItem, String paramString) {
/*      */     try {
/* 1207 */       byte b = 0;
/*      */       
/* 1209 */       String str = PokUtils.getAttributeFlagValue(paramEntityItem, paramString);
/*      */       
/* 1211 */       addDebug("checkForInProcess:  entered " + paramEntityItem.getKey() + " " + paramString + " is " + str);
/*      */       
/* 1213 */       if ("0050".equals(str)) {
/* 1214 */         DatePackage datePackage = this.m_db.getDates();
/*      */         
/* 1216 */         paramProfile.setValOnEffOn(datePackage.getEndOfDay(), datePackage.getEndOfDay());
/*      */         
/* 1218 */         while ("0050".equals(str) && b < 20) {
/* 1219 */           b++;
/* 1220 */           addDebug("checkForInProcess: " + paramString + " is " + str + " sleeping 30 secs");
/* 1221 */           Thread.sleep(30000L);
/*      */           
/* 1223 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, paramProfile, paramEntityItem.getEntityType(), "Edit", false);
/* 1224 */           EntityItem entityItem = new EntityItem(entityGroup, paramProfile, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/* 1225 */           str = PokUtils.getAttributeFlagValue(entityItem, paramString);
/* 1226 */           addDebug("checkForInProcess: " + paramString + " is now " + str + " after sleeping");
/*      */         } 
/*      */       } 
/* 1229 */     } catch (Exception exception) {
/* 1230 */       System.err.println("Exception in checkForInProcess " + exception);
/* 1231 */       exception.printStackTrace();
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
/*      */   protected void setFlagValue(Profile paramProfile, String paramString1, String paramString2) {
/* 1244 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1245 */     setFlagValue(paramProfile, paramString1, paramString2, entityItem);
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
/* 1260 */     logMessage(getDescription() + " ***** " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2 + " oldval " + paramString3);
/*      */     
/* 1262 */     addDebug("setMultiFlagValue entered for " + paramEntityItem.getKey() + " " + paramString1 + " set to: " + paramString2 + " oldval " + paramString3);
/*      */ 
/*      */ 
/*      */     
/* 1266 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(paramString1);
/* 1267 */     if (eANMetaAttribute == null) {
/* 1268 */       addDebug("setMultiFlagValue: " + paramString1 + " was not in meta for " + paramEntityItem.getEntityType() + ", nothing to do");
/* 1269 */       logMessage(getDescription() + " ***** " + paramString1 + " was not in meta for " + paramEntityItem
/* 1270 */           .getEntityType() + ", nothing to do");
/*      */       
/*      */       return;
/*      */     } 
/* 1274 */     if (paramString2 == null) {
/* 1275 */       paramString2 = "";
/*      */     }
/* 1277 */     if (paramString3 == null) {
/* 1278 */       paramString3 = "";
/*      */     }
/* 1280 */     if (!paramString2.equals(paramString3)) {
/* 1281 */       if (this.m_cbOn == null) {
/* 1282 */         setControlBlock();
/*      */       }
/*      */       
/* 1285 */       Vector<MultipleFlag> vector = null;
/*      */       
/* 1287 */       for (byte b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1288 */         ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1289 */         if (returnEntityKey.getEntityID() == paramEntityItem.getEntityID() && returnEntityKey
/* 1290 */           .getEntityType().equals(paramEntityItem.getEntityType())) {
/* 1291 */           vector = returnEntityKey.m_vctAttributes;
/*      */           break;
/*      */         } 
/*      */       } 
/* 1295 */       if (vector == null) {
/*      */         
/* 1297 */         ReturnEntityKey returnEntityKey = new ReturnEntityKey(paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), true);
/* 1298 */         vector = new Vector();
/* 1299 */         returnEntityKey.m_vctAttributes = vector;
/* 1300 */         this.vctReturnsEntityKeys.addElement(returnEntityKey);
/*      */       } 
/*      */       
/* 1303 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString2, "|");
/* 1304 */       MultipleFlag multipleFlag = null;
/* 1305 */       while (stringTokenizer.hasMoreTokens()) {
/* 1306 */         String str = stringTokenizer.nextToken();
/*      */         
/* 1308 */         multipleFlag = new MultipleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, str, 1, this.m_cbOn);
/* 1309 */         vector.addElement(multipleFlag);
/*      */       } 
/*      */ 
/*      */       
/* 1313 */       EANAttribute eANAttribute = paramEntityItem.getAttribute(paramString1);
/* 1314 */       if (eANAttribute != null) {
/* 1315 */         String str = eANAttribute.getEffFrom();
/* 1316 */         ControlBlock controlBlock = new ControlBlock(str, str, str, str, paramProfile.getOPWGID());
/*      */         
/* 1318 */         stringTokenizer = new StringTokenizer(paramString3, "|");
/* 1319 */         while (stringTokenizer.hasMoreTokens()) {
/* 1320 */           String str1 = stringTokenizer.nextToken();
/* 1321 */           if (paramString2.indexOf(str1) < 0) {
/*      */             
/* 1323 */             multipleFlag = new MultipleFlag(paramProfile.getEnterprise(), paramEntityItem.getEntityType(), paramEntityItem.getEntityID(), paramString1, str1, 1, controlBlock);
/* 1324 */             vector.addElement(multipleFlag);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1329 */       addDebug(paramEntityItem.getKey() + " old and new values were the same for " + paramString1);
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
/* 1345 */     logMessage(getDescription() + " updating PDH");
/* 1346 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + this.vctReturnsEntityKeys);
/* 1347 */     if (this.vctReturnsEntityKeys.size() > 0) {
/*      */ 
/*      */       
/* 1350 */       String[] arrayOfString = new String[6];
/*      */       
/*      */       try {
/* 1353 */         this.m_db.update(paramProfile, this.vctReturnsEntityKeys, false, false);
/*      */         byte b;
/* 1355 */         for (b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1356 */           ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/*      */           
/* 1358 */           for (byte b1 = 0; b1 < returnEntityKey.m_vctAttributes.size(); b1++) {
/* 1359 */             Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(b1);
/* 1360 */             if (attribute instanceof Text) {
/* 1361 */               EntityGroup entityGroup = null;
/* 1362 */               if (returnEntityKey.getEntityType().equals(getEntityType())) {
/* 1363 */                 entityGroup = this.m_elist.getParentEntityGroup();
/*      */               } else {
/*      */                 
/* 1366 */                 entityGroup = this.m_elist.getEntityGroup(returnEntityKey.getEntityType());
/*      */               } 
/*      */               
/* 1369 */               EntityItem entityItem = entityGroup.getEntityItem(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/*      */               
/* 1371 */               entityItem.commit(this.m_db, null);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*      */         try {
/* 1377 */           for (b = 0; b < this.vctReturnsEntityKeys.size(); b++) {
/* 1378 */             ReturnEntityKey returnEntityKey = this.vctReturnsEntityKeys.elementAt(b);
/* 1379 */             EntityGroup entityGroup = null;
/* 1380 */             if (returnEntityKey.getEntityType().equals(getEntityType())) {
/* 1381 */               entityGroup = this.m_elist.getParentEntityGroup();
/*      */             } else {
/*      */               
/* 1384 */               entityGroup = this.m_elist.getEntityGroup(returnEntityKey.getEntityType());
/*      */             } 
/*      */             
/* 1387 */             EntityItem entityItem = entityGroup.getEntityItem(returnEntityKey.getEntityType() + returnEntityKey.getEntityID());
/* 1388 */             Hashtable<Object, Object> hashtable = new Hashtable<>();
/*      */             
/* 1390 */             for (byte b1 = 0; b1 < returnEntityKey.m_vctAttributes.size(); b1++) {
/* 1391 */               MessageFormat messageFormat; Attribute attribute = returnEntityKey.m_vctAttributes.elementAt(b1);
/* 1392 */               String str = attribute.getAttributeCode();
/* 1393 */               if (str.equals("DATAQUALITY")) {
/*      */                 
/* 1395 */                 messageFormat = new MessageFormat(this.dqBundle.getString("DQ_RESET"));
/* 1396 */                 arrayOfString[0] = PokUtils.getAttributeValue(entityItem, getStatusAttrCode(), ", ", "", false);
/* 1397 */               } else if (str.equals(getStatusAttrCode())) {
/*      */                 
/* 1399 */                 messageFormat = new MessageFormat(this.dqBundle.getString("STATUS_SET"));
/* 1400 */                 arrayOfString[0] = PokUtils.getAttributeValue(entityItem, "DATAQUALITY", ", ", "", false);
/*      */               } else {
/*      */                 
/* 1403 */                 messageFormat = new MessageFormat(this.dqBundle.getString("ATTR_SET"));
/* 1404 */                 arrayOfString[0] = PokUtils.getAttributeDescription(entityGroup, str, str);
/* 1405 */                 if (attribute instanceof Text) {
/* 1406 */                   ControlBlock controlBlock = attribute.getControlBlock();
/* 1407 */                   if (controlBlock.getValFrom().equals(controlBlock.getValTo()) && controlBlock
/* 1408 */                     .getValTo().equals(controlBlock.getEffFrom()) && controlBlock
/* 1409 */                     .getEffFrom().equals(controlBlock.getEffTo())) {
/*      */                     
/* 1411 */                     arrayOfString[1] = "Null";
/*      */                   } else {
/* 1413 */                     arrayOfString[1] = attribute.getAttributeValue();
/*      */                   } 
/*      */                 } else {
/* 1416 */                   arrayOfString[1] = attribute.getAttributeValue();
/*      */                   
/* 1418 */                   EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)entityGroup.getMetaAttribute(str);
/* 1419 */                   if (eANMetaFlagAttribute != null) {
/* 1420 */                     MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(attribute.getAttributeValue());
/* 1421 */                     if (metaFlag != null) {
/* 1422 */                       arrayOfString[1] = metaFlag.toString();
/*      */                     }
/*      */                   } else {
/* 1425 */                     addDebug("Error: " + str + " not found in META for " + entityGroup.getEntityType());
/*      */                   } 
/*      */                 } 
/*      */                 
/* 1429 */                 if (attribute instanceof MultipleFlag) {
/* 1430 */                   StringBuffer stringBuffer = (StringBuffer)hashtable.get(str);
/* 1431 */                   if (stringBuffer == null) {
/* 1432 */                     stringBuffer = new StringBuffer();
/* 1433 */                     hashtable.put(str, stringBuffer);
/*      */                   } 
/* 1435 */                   if (stringBuffer.length() > 0) {
/* 1436 */                     stringBuffer.append(",");
/*      */                   }
/*      */                   
/* 1439 */                   ControlBlock controlBlock = attribute.getControlBlock();
/* 1440 */                   if (!controlBlock.getValFrom().equals(controlBlock.getValTo()) || 
/* 1441 */                     !controlBlock.getValTo().equals(controlBlock.getEffFrom()) || 
/* 1442 */                     !controlBlock.getEffFrom().equals(controlBlock.getEffTo()))
/*      */                   {
/*      */                     
/* 1445 */                     stringBuffer.append(arrayOfString[1].toString());
/*      */                   }
/*      */                 } else {
/* 1448 */                   arrayOfString[2] = entityGroup.getLongDescription();
/* 1449 */                   arrayOfString[3] = getNavigationName(entityItem);
/*      */                 } 
/*      */               } 
/* 1452 */               if (!(attribute instanceof MultipleFlag)) {
/* 1453 */                 addOutput(messageFormat.format(arrayOfString));
/*      */               }
/*      */             } 
/* 1456 */             if (hashtable.size() > 0) {
/* 1457 */               for (Enumeration<String> enumeration = hashtable.keys(); enumeration.hasMoreElements(); ) {
/*      */                 
/* 1459 */                 String str = enumeration.nextElement();
/* 1460 */                 StringBuffer stringBuffer = (StringBuffer)hashtable.get(str);
/*      */                 
/* 1462 */                 MessageFormat messageFormat = new MessageFormat(this.dqBundle.getString("ATTR_SET"));
/* 1463 */                 arrayOfString[0] = PokUtils.getAttributeDescription(entityGroup, str, str);
/* 1464 */                 if (stringBuffer.length() == 0) {
/*      */                   
/* 1466 */                   arrayOfString[1] = "Null";
/*      */                 } else {
/* 1468 */                   arrayOfString[1] = stringBuffer.toString();
/*      */                 } 
/* 1470 */                 arrayOfString[2] = entityGroup.getLongDescription();
/* 1471 */                 arrayOfString[3] = getNavigationName(entityItem);
/* 1472 */                 addOutput(messageFormat.format(arrayOfString));
/*      */               } 
/* 1474 */               hashtable.clear();
/*      */             } 
/*      */           } 
/* 1477 */         } catch (Exception exception) {
/* 1478 */           exception.printStackTrace();
/* 1479 */           addDebug("exception trying to output msg " + exception);
/*      */         } 
/*      */       } finally {
/*      */         
/* 1483 */         this.vctReturnsEntityKeys.clear();
/* 1484 */         this.m_db.commit();
/* 1485 */         this.m_db.freeStatement();
/* 1486 */         this.m_db.isPending("finally after updatePDH");
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
/*      */   public static Locale getLocale(int paramInt) {
/* 1498 */     Locale locale = null;
/* 1499 */     switch (paramInt)
/*      */     
/*      */     { case 1:
/* 1502 */         locale = Locale.US;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1526 */         return locale;case 2: locale = Locale.GERMAN; return locale;case 3: locale = Locale.ITALIAN; return locale;case 4: locale = Locale.JAPANESE; return locale;case 5: locale = Locale.FRENCH; return locale;case 6: locale = new Locale("es", "ES"); return locale;case 7: locale = Locale.UK; return locale; }  locale = Locale.US; return locale;
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
/*      */   private void domainNeedsChecks(EntityItem paramEntityItem) {
/* 1539 */     String str = ABRServerProperties.getDomains(this.m_abri.getABRCode());
/* 1540 */     addDebug("domainNeedsChecks pdhdomains needing checks: " + str);
/* 1541 */     if (str.equals("all")) {
/* 1542 */       this.bdomainInList = true;
/*      */     } else {
/* 1544 */       HashSet<String> hashSet = new HashSet();
/* 1545 */       StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
/* 1546 */       while (stringTokenizer.hasMoreTokens()) {
/* 1547 */         hashSet.add(stringTokenizer.nextToken());
/*      */       }
/* 1549 */       this.bdomainInList = PokUtils.contains(paramEntityItem, "PDHDOMAIN", hashSet);
/* 1550 */       hashSet.clear();
/*      */     } 
/*      */     
/* 1553 */     if (!this.bdomainInList) {
/* 1554 */       addDebug("PDHDOMAIN did not include " + str + ", checking is bypassed [" + 
/* 1555 */           PokUtils.getAttributeValue(paramEntityItem, "PDHDOMAIN", ", ", "", false) + "]");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkStatus(String paramString) throws SQLException, MiddlewareException {
/* 1565 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString);
/* 1566 */     if (entityGroup == null) {
/* 1567 */       throw new MiddlewareException(paramString + " is missing from extract for " + this.m_abri.getVEName());
/*      */     }
/* 1569 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1570 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1571 */       checkStatus(entityItem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void checkStatus(Vector<EntityItem> paramVector) throws SQLException, MiddlewareException {
/* 1581 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1582 */       EntityItem entityItem = paramVector.elementAt(b);
/* 1583 */       checkStatus(entityItem);
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
/*      */   private boolean wasUpdatedSinceDTS(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1604 */     boolean bool = false;
/* 1605 */     EntityChangeHistoryGroup entityChangeHistoryGroup = new EntityChangeHistoryGroup(this.m_db, paramEntityItem.getProfile(), paramEntityItem);
/* 1606 */     if (entityChangeHistoryGroup == null || entityChangeHistoryGroup.getChangeHistoryItemCount() == 0) {
/* 1607 */       addDebug(paramEntityItem.getKey() + " No Change history found");
/*      */     } else {
/*      */       
/* 1610 */       String str = entityChangeHistoryGroup.getChangeHistoryItem(entityChangeHistoryGroup.getChangeHistoryItemCount() - 1).getChangeDate();
/* 1611 */       addDebug(paramEntityItem.getKey() + " last chgDate: " + str + " vs. " + "2008-03-12-00.00.00.000000");
/* 1612 */       if (str.compareTo("2008-03-12-00.00.00.000000") >= 0) {
/* 1613 */         bool = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1623 */     return bool;
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
/*      */   protected void checkStatus(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 1636 */     Object[] arrayOfObject = new Object[2];
/* 1637 */     String str = getAttributeFlagEnabledValue(paramEntityItem, "STATUS");
/* 1638 */     addDebug(paramEntityItem.getKey() + " check status " + str);
/* 1639 */     if (str == null) {
/* 1640 */       str = "0020";
/*      */     }
/*      */ 
/*      */     
/* 1644 */     if (paramEntityItem.getEntityType().equals("PRODSTRUCT") && str.equals("0010")) {
/* 1645 */       addDebug(paramEntityItem.getKey() + " must get entityhistory and check date");
/* 1646 */       if (!wasUpdatedSinceDTS(paramEntityItem)) {
/* 1647 */         addDebug(paramEntityItem.getKey() + " using Final for status because it was not updated after " + "2008-03-12-00.00.00.000000");
/* 1648 */         str = "0020";
/*      */       } 
/*      */     } 
/* 1651 */     if (!"0020".equals(str)) {
/* 1652 */       addDebug(paramEntityItem.getKey() + " is not Final");
/*      */ 
/*      */       
/* 1655 */       arrayOfObject[0] = paramEntityItem.getEntityGroup().getLongDescription();
/* 1656 */       arrayOfObject[1] = getNavigationName(paramEntityItem);
/* 1657 */       addError("NOT_FINAL_ERR", arrayOfObject);
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
/*      */   protected int getCount(String paramString) throws MiddlewareException {
/* 1676 */     int i = 0;
/*      */     
/* 1678 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString);
/* 1679 */     if (entityGroup == null) {
/* 1680 */       throw new MiddlewareException(paramString + " is missing from extract for " + this.m_abri.getVEName());
/*      */     }
/* 1682 */     if (entityGroup.getEntityItemCount() > 0) {
/* 1683 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1684 */         int j = 1;
/* 1685 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1686 */         EANAttribute eANAttribute = entityItem.getAttribute("QTY");
/* 1687 */         if (eANAttribute != null) {
/* 1688 */           j = Integer.parseInt(eANAttribute.get().toString());
/*      */         }
/* 1690 */         i += j;
/* 1691 */         addDebug("getCount " + entityItem.getKey() + " qty " + j);
/*      */       } 
/*      */     }
/*      */     
/* 1695 */     addDebug("getCount Total count found for " + paramString + " = " + i);
/* 1696 */     return i;
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
/* 1713 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/* 1714 */     String str1 = "SAPASSORTMODULE";
/* 1715 */     String str2 = "SAPASSORTMODULEPRIOR";
/*      */     
/* 1717 */     EANMetaAttribute eANMetaAttribute = this.m_elist.getParentEntityGroup().getMetaAttribute(str2);
/* 1718 */     if (eANMetaAttribute == null) {
/* 1719 */       throw new MiddlewareException(str2 + " not found in META for " + entityItem.getEntityType());
/*      */     }
/* 1721 */     eANMetaAttribute = this.m_elist.getParentEntityGroup().getMetaAttribute(str1);
/* 1722 */     if (eANMetaAttribute == null) {
/* 1723 */       throw new MiddlewareException(str1 + " not found in META for " + entityItem.getEntityType());
/*      */     }
/*      */ 
/*      */     
/* 1727 */     EANAttribute eANAttribute = entityItem.getAttribute("STATUS");
/* 1728 */     if (eANAttribute != null) {
/*      */       
/* 1730 */       String str = null;
/* 1731 */       AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute);
/* 1732 */       addDebug("checkAssortModule: ChangeHistoryGroup for " + entityItem.getKey() + " Attribute: STATUS");
/* 1733 */       if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 1734 */         for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*      */           
/* 1736 */           AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1737 */           addDebug("checkAssortModule: AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 1738 */               .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1739 */           if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1740 */             str = attributeChangeHistoryItem.getChangeDate();
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       }
/* 1745 */       if (str != null) {
/* 1746 */         String str3 = PokUtils.getAttributeValue(entityItem, str1, ", ", "", false).trim();
/* 1747 */         String str4 = PokUtils.getAttributeValue(entityItem, str2, ", ", "", false).trim();
/*      */         
/* 1749 */         Profile profile = this.m_elist.getProfile().getNewInstance(this.m_db);
/* 1750 */         profile.setValOnEffOn(str, str);
/* 1751 */         EntityGroup entityGroup = new EntityGroup(null, this.m_db, profile, entityItem.getEntityType(), "Edit", false);
/* 1752 */         EntityItem entityItem1 = new EntityItem(entityGroup, profile, this.m_db, entityItem.getEntityType(), entityItem.getEntityID());
/* 1753 */         String str5 = PokUtils.getAttributeValue(entityItem1, str1, ", ", "", false).trim();
/* 1754 */         addDebug("checkAssortModule: " + entityItem.getKey() + " lastfinaldate " + str + " " + str1 + " curr:" + str3 + " prev:" + str5 + " " + str2 + " curr: " + str4);
/*      */ 
/*      */ 
/*      */         
/* 1758 */         if (!str4.equals(str5)) {
/* 1759 */           setTextValue(this.m_elist.getProfile(), str2, str5, entityItem);
/*      */         } else {
/* 1761 */           addDebug("checkAssortModule: " + str2 + " current:" + str4 + " already matches " + str1 + " previous:" + str5);
/*      */         } 
/*      */       } else {
/*      */         
/* 1765 */         addDebug("checkAssortModule: must be first time " + entityItem.getKey() + " status went final");
/*      */       } 
/*      */     } else {
/*      */       
/* 1769 */       addDebug("checkAssortModule: Could not get AttributeHistory for " + entityItem.getKey() + " STATUS, it was null");
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
/*      */   protected void checkCountry(String paramString1, String paramString2, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 1784 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1786 */     ArrayList<String> arrayList = new ArrayList();
/*      */     
/* 1788 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 1789 */     if (eANFlagAttribute != null) {
/*      */       
/* 1791 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1792 */       for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */         
/* 1794 */         if (arrayOfMetaFlag[b1].isSelected()) {
/* 1795 */           arrayList.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1800 */     addDebug("checkCountry " + entityItem.getKey() + " countries " + arrayList);
/*      */ 
/*      */     
/* 1803 */     EntityGroup entityGroup = this.m_elist.getEntityGroup(paramString1);
/* 1804 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1805 */       EntityItem entityItem1 = entityGroup.getEntityItem(b);
/* 1806 */       if (paramString2.equals("D")) {
/* 1807 */         for (byte b1 = 0; b1 < entityItem1.getDownLinkCount(); b1++) {
/* 1808 */           EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b1);
/* 1809 */           checkCountry(entityItem, entityItem2, arrayList, paramBoolean);
/*      */         } 
/*      */       } else {
/* 1812 */         for (byte b1 = 0; b1 < entityItem1.getUpLinkCount(); b1++) {
/* 1813 */           EntityItem entityItem2 = (EntityItem)entityItem1.getUpLink(b1);
/* 1814 */           checkCountry(entityItem, entityItem2, arrayList, paramBoolean);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1819 */     arrayList.clear();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkCountry(EntityItem paramEntityItem1, EntityItem paramEntityItem2, ArrayList<?> paramArrayList, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 1825 */     String[] arrayOfString = new String[6];
/* 1826 */     EntityGroup entityGroup = paramEntityItem2.getEntityGroup();
/* 1827 */     ArrayList<String> arrayList = new ArrayList();
/*      */     
/* 1829 */     EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)paramEntityItem2.getAttribute("COUNTRYLIST");
/* 1830 */     if (eANFlagAttribute != null) {
/*      */       
/* 1832 */       MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 1833 */       for (byte b = 0; b < arrayOfMetaFlag.length; b++) {
/*      */         
/* 1835 */         if (arrayOfMetaFlag[b].isSelected()) {
/* 1836 */           arrayList.add(arrayOfMetaFlag[b].getFlagCode());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1841 */     addDebug("checkCountry " + paramEntityItem2.getKey() + " chkItemIsSubsetRoot: " + paramBoolean + " countries " + arrayList);
/*      */ 
/*      */     
/* 1844 */     boolean bool = true;
/* 1845 */     if (paramBoolean) {
/*      */       
/* 1847 */       bool = paramArrayList.containsAll(arrayList);
/*      */     } else {
/*      */       
/* 1850 */       bool = arrayList.containsAll(paramArrayList);
/*      */     } 
/* 1852 */     if (!bool) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1871 */       if (paramBoolean) {
/* 1872 */         arrayOfString[0] = entityGroup.getLongDescription();
/* 1873 */         arrayOfString[1] = getNavigationName(paramEntityItem2);
/* 1874 */         arrayOfString[2] = PokUtils.getAttributeDescription(entityGroup, "COUNTRYLIST", "COUNTRYLIST");
/* 1875 */         arrayOfString[3] = paramEntityItem1.getEntityGroup().getLongDescription();
/* 1876 */         arrayOfString[4] = getNavigationName(paramEntityItem1);
/* 1877 */         arrayOfString[5] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/*      */       } else {
/* 1879 */         arrayOfString[0] = "";
/* 1880 */         arrayOfString[1] = "";
/* 1881 */         arrayOfString[2] = PokUtils.getAttributeDescription(paramEntityItem1.getEntityGroup(), "COUNTRYLIST", "COUNTRYLIST");
/* 1882 */         arrayOfString[3] = entityGroup.getLongDescription();
/* 1883 */         arrayOfString[4] = getNavigationName(paramEntityItem2);
/* 1884 */         arrayOfString[5] = PokUtils.getAttributeDescription(entityGroup, "COUNTRYLIST", "COUNTRYLIST");
/*      */       } 
/* 1886 */       addError("COUNTRY_MISMATCH_ERR", (Object[])arrayOfString);
/*      */     } 
/*      */     
/* 1889 */     arrayList.clear();
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
/*      */   protected void queueSapl() {
/* 1904 */     EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */     
/* 1906 */     EANMetaAttribute eANMetaAttribute = this.m_elist.getParentEntityGroup().getMetaAttribute("SAPL");
/* 1907 */     if (eANMetaAttribute == null) {
/* 1908 */       addDebug("queueSapl Status=Final but SAPL was not in meta, nothing to do");
/*      */     } else {
/*      */       
/* 1911 */       String str1 = getAttributeFlagEnabledValue(entityItem, "SAPL");
/* 1912 */       if (str1 == null) {
/* 1913 */         str1 = "10";
/*      */       }
/*      */ 
/*      */       
/* 1917 */       String str2 = (String)SAPL_TRANS_TBL.get(str1);
/* 1918 */       if (str2 == null) {
/* 1919 */         String[] arrayOfString = new String[1];
/* 1920 */         addDebug("queueSapl Status=Final but SAPL current value is not in list [" + str1 + "]");
/*      */         
/* 1922 */         MessageFormat messageFormat = new MessageFormat(this.dqBundle.getString("SAPL_WRONG_VALUE"));
/* 1923 */         arrayOfString[0] = PokUtils.getAttributeValue(entityItem, "SAPL", ", ", "", false);
/* 1924 */         addOutput(messageFormat.format(arrayOfString));
/*      */       } else {
/* 1926 */         setFlagValue(this.m_elist.getProfile(), "SAPLABRSTATUS", "0020");
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
/*      */   protected void propagateOStoWWSEO(EntityItem paramEntityItem, EntityGroup paramEntityGroup) throws SQLException, MiddlewareException, MiddlewareRequestException {
/* 1951 */     String str1 = "OS";
/*      */     
/* 1953 */     if (paramEntityGroup == null) {
/* 1954 */       throw new MiddlewareException("WWSEO is missing from extract for " + this.m_abri.getVEName());
/*      */     }
/*      */     
/* 1957 */     if (paramEntityItem == null) {
/* 1958 */       addDebug("propagateOStoWWSEO: MODEL was null!!");
/*      */       
/*      */       return;
/*      */     } 
/* 1962 */     String str2 = getAttributeFlagEnabledValue(paramEntityItem, "COFCAT");
/* 1963 */     String str3 = getAttributeFlagEnabledValue(paramEntityItem, "COFGRP");
/* 1964 */     addDebug("propagateOStoWWSEO: " + paramEntityItem.getKey() + " COFGRP: " + str3 + " wwseocnt: " + paramEntityGroup.getEntityItemCount());
/* 1965 */     if (paramEntityGroup.getEntityItemCount() == 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1970 */     EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str1);
/* 1971 */     if (eANMetaAttribute == null) {
/* 1972 */       addDebug("propagateOStoWWSEO ERROR:Attribute " + str1 + " NOT found in MODEL META data.");
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1977 */     if ("100".equals(str2) && "150".equals(str3)) {
/*      */       
/* 1979 */       EANAttribute eANAttribute = paramEntityItem.getAttribute("MODELABRSTATUS");
/* 1980 */       if (eANAttribute != null) {
/*      */         
/* 1982 */         String str = null;
/* 1983 */         boolean bool = false;
/* 1984 */         AttributeChangeHistoryGroup attributeChangeHistoryGroup = this.m_db.getAttributeChangeHistoryGroup(this.m_elist.getProfile(), eANAttribute);
/* 1985 */         addDebug("propagateOStoWWSEO: ChangeHistoryGroup for " + paramEntityItem.getKey() + " Attribute: MODELABRSTATUS");
/* 1986 */         if (attributeChangeHistoryGroup.getChangeHistoryItemCount() > 0) {
/* 1987 */           for (int i = attributeChangeHistoryGroup.getChangeHistoryItemCount() - 1; i >= 0; i--) {
/*      */             
/* 1989 */             AttributeChangeHistoryItem attributeChangeHistoryItem = (AttributeChangeHistoryItem)attributeChangeHistoryGroup.getChangeHistoryItem(i);
/* 1990 */             addDebug("propagateOStoWWSEO: AttrChangeHistoryItem[" + i + "] chgDate: " + attributeChangeHistoryItem.getChangeDate() + " user: " + attributeChangeHistoryItem.getUser() + " isValid: " + attributeChangeHistoryItem
/* 1991 */                 .isValid() + " value: " + attributeChangeHistoryItem.get("ATTVAL", true) + " flagcode: " + attributeChangeHistoryItem.getFlagCode());
/* 1992 */             if (attributeChangeHistoryItem.getFlagCode().equals("0020")) {
/* 1993 */               if (str != null)
/*      */               {
/* 1995 */                 bool = true;
/*      */               }
/* 1997 */               str = attributeChangeHistoryItem.getChangeDate();
/* 1998 */               if (bool) {
/*      */                 break;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         }
/*      */         
/* 2005 */         if (str != null) {
/* 2006 */           String str4 = getAttributeFlagEnabledValue(paramEntityItem, str1);
/* 2007 */           if (str4 == null) {
/* 2008 */             str4 = "";
/*      */           }
/*      */ 
/*      */           
/* 2012 */           Profile profile = this.m_elist.getProfile().getNewInstance(this.m_db);
/* 2013 */           profile.setValOnEffOn(str, str);
/* 2014 */           EntityGroup entityGroup = new EntityGroup(null, this.m_db, profile, paramEntityItem.getEntityType(), "Edit", false);
/* 2015 */           EntityItem entityItem = new EntityItem(entityGroup, profile, this.m_db, paramEntityItem.getEntityType(), paramEntityItem.getEntityID());
/* 2016 */           String str5 = getAttributeFlagEnabledValue(entityItem, str1);
/* 2017 */           if (str5 == null) {
/* 2018 */             str5 = "";
/*      */           }
/*      */           
/* 2021 */           addDebug("propagateOStoWWSEO: " + paramEntityItem.getKey() + " lastqueueddate " + str + " " + str1 + " curr: " + str4 + " prev:" + str5);
/*      */ 
/*      */           
/* 2024 */           if (!str4.equals(str5)) {
/*      */             
/* 2026 */             for (byte b = 0; b < paramEntityGroup.getEntityItemCount(); b++) {
/* 2027 */               EntityItem entityItem1 = paramEntityGroup.getEntityItem(b);
/* 2028 */               addDebug("propagateOStoWWSEO: updating " + entityItem1.getKey() + " SEOID " + 
/* 2029 */                   PokUtils.getAttributeValue(entityItem1, "SEOID", ", ", "", false));
/* 2030 */               setFlagValue(this.m_elist.getProfile(), str1, str4, entityItem1);
/*      */             } 
/*      */           } else {
/* 2033 */             addDebug("propagateOStoWWSEO: no changes in " + paramEntityItem.getKey() + " os since last queued");
/*      */           } 
/*      */         } else {
/* 2036 */           addDebug("propagateOStoWWSEO: Could not get queued DTS for " + paramEntityItem.getKey());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2041 */         addDebug("propagateOStoWWSEO: Could not get AttributeHistory for " + paramEntityItem.getKey() + " MODELABRSTATUS, it was null");
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
/*      */   protected void checkWDAvails(EntityItem paramEntityItem, String paramString1, String paramString2, String paramString3, String paramString4) throws SQLException, MiddlewareException {
/* 2061 */     String[] arrayOfString = new String[2];
/*      */     
/* 2063 */     Vector<EntityItem> vector = PokUtils.getAllLinkedEntities(paramEntityItem, paramString1, paramString2);
/* 2064 */     addDebug("checkWDAvails entered go thru: " + paramString1 + " to " + paramString2 + " then thru " + paramString3 + " found: " + vector.size() + " " + paramString2);
/* 2065 */     for (byte b = 0; b < vector.size(); b++) {
/* 2066 */       EntityItem entityItem = vector.elementAt(b);
/* 2067 */       addDebug("checkWDAvails checking entity: " + entityItem.getKey() + " for " + paramString3);
/* 2068 */       for (byte b1 = 0; b1 < entityItem.getDownLinkCount(); b1++) {
/* 2069 */         EntityItem entityItem1 = (EntityItem)entityItem.getDownLink(b1);
/* 2070 */         if (entityItem1.getEntityType().equals(paramString3))
/*      */         {
/* 2072 */           for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/* 2073 */             EntityItem entityItem2 = (EntityItem)entityItem1.getDownLink(b2);
/* 2074 */             addDebug("checkWDAvails checking  " + entityItem2.getKey() + " for lastorder");
/* 2075 */             if (PokUtils.isSelected(entityItem2, "AVAILTYPE", "149")) {
/* 2076 */               String str = PokUtils.getAttributeValue(entityItem2, "EFFECTIVEDATE", ", ", "", false);
/* 2077 */               addDebug("checkWDAvails lastorder " + entityItem2.getKey() + " EFFECTIVEDATE: " + str);
/* 2078 */               if (str.length() > 0 && str.compareTo(paramString4) <= 0) {
/*      */                 
/* 2080 */                 arrayOfString[0] = entityItem.getEntityGroup().getLongDescription();
/* 2081 */                 arrayOfString[1] = getNavigationName(entityItem);
/* 2082 */                 addError("WITHDRAWN_ERR", (Object[])arrayOfString);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 2089 */     vector.clear();
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
/*      */   protected void checkLastOrderAvailCountry(EntityItem paramEntityItem, String paramString) throws SQLException, MiddlewareException {
/* 2111 */     String str = PokUtils.getAttributeValue(paramEntityItem, paramString, ", ", null, false);
/* 2112 */     addDebug("checkLastOrderAvailCountry " + paramEntityItem.getKey() + " " + paramString + ": " + str);
/* 2113 */     if (str != null) {
/* 2114 */       Object[] arrayOfObject = new Object[4];
/*      */       
/* 2116 */       EntityGroup entityGroup = this.m_elist.getEntityGroup("AVAIL");
/* 2117 */       if (entityGroup == null) {
/* 2118 */         throw new MiddlewareException("AVAIL is missing from extract for " + this.m_abri.getVEName());
/*      */       }
/*      */       
/* 2121 */       ArrayList<String> arrayList1 = new ArrayList();
/* 2122 */       ArrayList<String> arrayList2 = new ArrayList();
/*      */       
/* 2124 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2125 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2126 */         addDebug("checkLastOrderAvailCountry checking avail: " + entityItem.getKey());
/* 2127 */         if (PokUtils.isSelected(entityItem, "AVAILTYPE", "149")) {
/* 2128 */           String str1 = PokUtils.getAttributeValue(entityItem, "EFFECTIVEDATE", ", ", "", false);
/* 2129 */           addDebug("checkLastOrderAvailCountry lastorder " + entityItem.getKey() + " EFFECTIVEDATE: " + str1);
/*      */ 
/*      */           
/* 2132 */           if (str1.length() > 0 && str1.compareTo(str) > 0) {
/*      */ 
/*      */             
/* 2135 */             arrayOfObject[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString);
/* 2136 */             arrayOfObject[1] = entityGroup.getLongDescription();
/* 2137 */             arrayOfObject[2] = getNavigationName(entityItem);
/* 2138 */             arrayOfObject[3] = "";
/* 2139 */             addError("EARLY_DATE_ERR", arrayOfObject);
/*      */           } 
/* 2141 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 2142 */           if (eANFlagAttribute != null) {
/*      */             
/* 2144 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 2145 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++)
/*      */             {
/* 2147 */               if (arrayOfMetaFlag[b1].isSelected() && !arrayList1.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 2148 */                 arrayList1.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */               }
/*      */             }
/*      */           
/*      */           } 
/* 2153 */         } else if (PokUtils.isSelected(entityItem, "AVAILTYPE", "146")) {
/* 2154 */           addDebug("checkLastOrderAvailCountry plannedavail " + entityItem.getKey());
/* 2155 */           EANFlagAttribute eANFlagAttribute = (EANFlagAttribute)entityItem.getAttribute("COUNTRYLIST");
/* 2156 */           if (eANFlagAttribute != null) {
/*      */             
/* 2158 */             MetaFlag[] arrayOfMetaFlag = (MetaFlag[])eANFlagAttribute.get();
/* 2159 */             for (byte b1 = 0; b1 < arrayOfMetaFlag.length; b1++) {
/*      */               
/* 2161 */               if (arrayOfMetaFlag[b1].isSelected() && !arrayList2.contains(arrayOfMetaFlag[b1].getFlagCode())) {
/* 2162 */                 arrayList2.add(arrayOfMetaFlag[b1].getFlagCode());
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2172 */       addDebug("checkLastOrderAvailCountry all plannedavail countries " + arrayList2);
/* 2173 */       addDebug("checkLastOrderAvailCountry all lastorderavail countries " + arrayList1);
/*      */       
/* 2175 */       if (!arrayList1.containsAll(arrayList2)) {
/* 2176 */         arrayOfObject[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString);
/* 2177 */         arrayOfObject[1] = entityGroup.getLongDescription();
/*      */ 
/*      */         
/* 2180 */         addError("NO_LASTORDER_ERR2", arrayOfObject);
/*      */       } 
/* 2182 */       arrayList1.clear();
/* 2183 */       arrayList2.clear();
/*      */     } 
/*      */   }
/*      */   protected abstract void doDQChecking(EntityItem paramEntityItem, String paramString) throws Exception;
/*      */   
/*      */   private static class NDN { private String etype;
/*      */     private String tag;
/*      */     private NDN next;
/* 2191 */     private Vector attrVct = new Vector();
/*      */     NDN(String param1String1, String param1String2) {
/* 2193 */       this.etype = param1String1;
/* 2194 */       this.tag = param1String2;
/*      */     }
/* 2196 */     String getTag() { return this.tag; }
/* 2197 */     String getEntityType() { return this.etype; } Vector getAttr() {
/* 2198 */       return this.attrVct;
/*      */     } void addAttr(String param1String) {
/* 2200 */       this.attrVct.addElement(param1String);
/*      */     }
/* 2202 */     void setNext(NDN param1NDN) { this.next = param1NDN; } NDN getNext() {
/* 2203 */       return this.next;
/*      */     } }
/*      */ 
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\DQABRSTATUS.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */