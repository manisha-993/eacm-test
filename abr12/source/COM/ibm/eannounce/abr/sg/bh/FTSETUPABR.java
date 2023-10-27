/*      */ package COM.ibm.eannounce.abr.sg.bh;
/*      */ 
/*      */ import COM.ibm.eannounce.abr.util.ABRUtil;
/*      */ import COM.ibm.eannounce.abr.util.EACustom;
/*      */ import COM.ibm.eannounce.abr.util.PokBaseABR;
/*      */ import COM.ibm.eannounce.objects.CreateActionItem;
/*      */ import COM.ibm.eannounce.objects.DeleteActionItem;
/*      */ import COM.ibm.eannounce.objects.EANBusinessRuleException;
/*      */ import COM.ibm.eannounce.objects.EANEntity;
/*      */ import COM.ibm.eannounce.objects.EANList;
/*      */ import COM.ibm.eannounce.objects.EANMetaAttribute;
/*      */ import COM.ibm.eannounce.objects.EANMetaFlagAttribute;
/*      */ import COM.ibm.eannounce.objects.EntityGroup;
/*      */ import COM.ibm.eannounce.objects.EntityItem;
/*      */ import COM.ibm.eannounce.objects.EntityList;
/*      */ import COM.ibm.eannounce.objects.ExtractActionItem;
/*      */ import COM.ibm.eannounce.objects.LinkActionItem;
/*      */ import COM.ibm.eannounce.objects.MetaFlag;
/*      */ import COM.ibm.eannounce.objects.SBRException;
/*      */ import COM.ibm.eannounce.objects.WorkflowException;
/*      */ import COM.ibm.opicmpdh.middleware.D;
/*      */ import COM.ibm.opicmpdh.middleware.DatePackage;
/*      */ import COM.ibm.opicmpdh.middleware.LockException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareBusinessRuleException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareRequestException;
/*      */ import COM.ibm.opicmpdh.middleware.MiddlewareShutdownInProgressException;
/*      */ import COM.ibm.opicmpdh.middleware.ReturnEntityKey;
/*      */ import COM.ibm.opicmpdh.middleware.Stopwatch;
/*      */ import COM.ibm.opicmpdh.middleware.taskmaster.ABRServerProperties;
/*      */ import COM.ibm.opicmpdh.objects.ControlBlock;
/*      */ import COM.ibm.opicmpdh.objects.SingleFlag;
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
/*      */ import java.sql.SQLException;
/*      */ import java.text.MessageFormat;
/*      */ import java.util.Collections;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.ResourceBundle;
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
/*      */ public class FTSETUPABR
/*      */   extends PokBaseABR
/*      */ {
/*      */   private static final int MAXFILE_SIZE = 5000000;
/*  112 */   private StringBuffer rptSb = new StringBuffer();
/*  113 */   private static final char[] FOOL_JTEST = new char[] { '\n' };
/*  114 */   static final String NEWLINE = new String(FOOL_JTEST);
/*      */   
/*  116 */   private Object[] args = (Object[])new String[10];
/*      */   
/*  118 */   private ResourceBundle rsBundle = null;
/*  119 */   private Hashtable metaTbl = new Hashtable<>();
/*  120 */   private String navName = "";
/*      */   private Hashtable fcTransComboTbl;
/*  122 */   private int skippedFinalCnt = 0;
/*  123 */   private int skippedManualCnt = 0;
/*  124 */   private int skippedNotActiveCnt = 0;
/*  125 */   private int createdCnt = 0;
/*  126 */   private int updatedCnt = 0;
/*  127 */   private int notUpdatedCnt = 0;
/*  128 */   private int deletedCnt = 0;
/*  129 */   private int statusSetCnt = 0;
/*  130 */   private int xmlabrSetCnt = 0;
/*  131 */   private String xmlabrSetTo = "Queued";
/*  132 */   private int abr_debuglvl = 0;
/*      */   
/*      */   private boolean metaError = false;
/*  135 */   private String fctstatus = null; private static final String TO_MODEL_SRCHACTION_NAME = "SRDMODEL4"; private static final String FROM_MODEL_SRCHACTION_NAME = "SRDMODEL6";
/*      */   private static final String FCTRANSAVAIL_DELETEACTION_NAME = "DELFCTRANSACTIONAVAIL";
/*      */   private static final String FCTRANSAVAIL_LINKACTION_NAME = "LINKAVAILFCTRANSACTION2";
/*      */   private static final String FCTRANS_DELETEACTION_NAME = "DELFCTRANSACTION";
/*      */   private static final String FCTRANS_CREATEACTION_NAME = "CRFCTRANSACTION2";
/*      */   private static final String FTCLASS_Manual = "MAN";
/*      */   private static final String FTVACTIVE_No = "N";
/*      */   private static final int UPDATE_SIZE;
/*      */   private static final String CAPUNIT_MB = "C0010";
/*      */   private static final String CAPUNIT_GB = "C0040";
/*      */   private static final String FCTSTATUS_Set2Draft = "FCT1";
/*      */   private static final String FCTSTATUS_Draft = "FCT2";
/*      */   private static final String FCTSTATUS_Final = "FCT4";
/*      */   private static final String HWFCCAT_Memory = "213";
/*      */   private static final String HWFCCAT_CSET = "305";
/*      */   private static final String FCTRANAPP_Yes = "FCY";
/*      */   private static final String FTRULE_All = "10";
/*      */   private static final String FTRULE_AllButSelf = "20";
/*      */   private static final String FTRULE_OnlyGreater = "30";
/*      */   private static final String FTRULE_EqualGreater = "40";
/*      */   private static final String ABR_QUEUED = "0020";
/*      */   
/*      */   static {
/*  158 */     String str = ABRServerProperties.getValue("FTSETUPABR", "_updatesize", "500");
/*      */     
/*  160 */     UPDATE_SIZE = Integer.parseInt(str);
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
/*  203 */   private static final Hashtable HWFCCAT_VE_VCT = new Hashtable<>(); static {
/*  204 */     HWFCCAT_VE_VCT.put("213", "EXFTMDLMEM");
/*      */     
/*  206 */     HWFCCAT_VE_VCT.put("305", "EXFTMDLCSET");
/*      */   }
/*      */   
/*  209 */   private static final String[] FCTRANS_KEYS = new String[] { "FROMMACHTYPE", "FROMMODEL", "FROMFEATURECODE", "TOMACHTYPE", "TOMODEL", "TOFEATURECODE", "COMMENT" };
/*      */   
/*      */   private static final String STATUS_FINAL = "0020";
/*      */   
/*      */   private static final String STATUS_DRAFT = "0010";
/*      */   
/*      */   private static final String DQ_DRAFT = "DRAFT";
/*      */   
/*      */   private static final String DQ_FINAL = "FINAL";
/*      */   private static final String LIFECYCLE_Develop = "LF02";
/*      */   private static final String LIFECYCLE_Plan = "LF01";
/*      */   private static final String LIFECYCLE_Launch = "LF03";
/*  221 */   private static final String[] FCTRANSLIST_ATTR = new String[] { "ANNDATE", "BOXSWAP", "FTCAT", "GENAVAILDATE", "INSTALL", "PARTSSHIPPED", "RETURNEDPARTS", "UPGRADETYPE", "WITHDRAWDATE", "WTHDRWEFFCTVDATE", "ZEROPRICE" };
/*      */   
/*      */   private static final String XMLATTRCODE = "ADSABRSTATUS";
/*      */   
/*      */   private boolean outputMetaMsg = false;
/*      */   
/*  227 */   private PrintWriter dbgPw = null;
/*  228 */   private String dbgfn = null;
/*  229 */   private int dbgLen = 0;
/*      */   
/*      */   private void setupPrintWriter() {
/*  232 */     String str = this.m_abri.getFileName();
/*  233 */     int i = str.lastIndexOf(".");
/*  234 */     this.dbgfn = str.substring(0, i + 1) + "dbg";
/*      */     try {
/*  236 */       this.dbgPw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.dbgfn, true), "UTF-8"));
/*  237 */     } catch (Exception exception) {
/*  238 */       this.dbgfn = null;
/*  239 */       D.ebug(0, "trouble creating debug PrintWriter " + exception);
/*      */     } 
/*      */   }
/*      */   private void closePrintWriter() {
/*  243 */     if (this.dbgPw != null) {
/*  244 */       this.dbgPw.flush();
/*  245 */       this.dbgPw.close();
/*  246 */       this.dbgPw = null;
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
/*      */   public void execute_run() {
/*  270 */     String str1 = "<head>" + EACustom.getMetaTags(getDescription()) + NEWLINE + EACustom.getCSS() + NEWLINE + EACustom.getTitle("{0} {1}") + NEWLINE + "</head>" + NEWLINE + "<body id=\"ibm-com\">" + EACustom.getMastheadDiv() + NEWLINE + "<p class=\"ibm-intro ibm-alternate-three\"><em>{0}: {1}</em></p>" + NEWLINE;
/*      */     
/*  272 */     String str2 = "<table>" + NEWLINE + "<tr><th>Userid: </th><td>{0}</td></tr>" + NEWLINE + "<tr><th>Role: </th><td>{1}</td></tr>" + NEWLINE + "<tr><th>Workgroup: </th><td>{2}</td></tr>" + NEWLINE + "<tr><th>Date: </th><td>{3}</td></tr>" + NEWLINE + "<tr><th>Description: </th><td>{4}</td></tr>" + NEWLINE + "<tr><th>Return code: </th><td>{5}</td></tr>" + NEWLINE + "</table>" + NEWLINE + "<!-- {6} -->" + NEWLINE;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  283 */     String str3 = "";
/*  284 */     String str4 = "";
/*      */     
/*  286 */     println(EACustom.getDocTypeHtml());
/*      */ 
/*      */     
/*      */     try {
/*  290 */       long l = System.currentTimeMillis();
/*  291 */       start_ABRBuild();
/*      */       
/*  293 */       this.abr_debuglvl = ABRServerProperties.getABRDebugLevel(this.m_abri.getABRCode());
/*      */       
/*  295 */       setupPrintWriter();
/*      */ 
/*      */       
/*  298 */       this.rsBundle = ResourceBundle.getBundle(getClass().getName(), ABRUtil.getLocale(this.m_prof.getReadLanguage().getNLSID()));
/*      */       
/*  300 */       EntityItem entityItem = this.m_elist.getParentEntityGroup().getEntityItem(0);
/*      */       
/*  302 */       addDebug("DEBUG: " + getShortClassName(getClass()) + " entered for " + entityItem.getKey() + " extract: " + this.m_abri
/*  303 */           .getVEName() + " using DTS: " + this.m_prof.getValOn() + NEWLINE + PokUtils.outputList(this.m_elist));
/*      */       
/*  305 */       addDebug("Time to pull root VE: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */       
/*  308 */       setReturnCode(0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  313 */       this.navName = getNavigationName(entityItem);
/*  314 */       str3 = this.m_elist.getParentEntityGroup().getLongDescription() + " &quot;" + this.navName + "&quot;";
/*      */ 
/*      */       
/*  317 */       this.fctstatus = PokUtils.getAttributeFlagValue(entityItem, "FCTSTATUS");
/*  318 */       if (this.fctstatus == null) {
/*  319 */         this.fctstatus = "FCT2";
/*      */       }
/*  321 */       addDebug(entityItem.getKey() + " fctstatus " + this.fctstatus);
/*      */       
/*  323 */       if (this.fctstatus.equals("FCT2") || this.fctstatus.equals("FCT4")) {
/*      */         
/*  325 */         Vector vector = avoidInactiveFCTRANS();
/*  326 */         addDebug("skippedKeysVct: " + vector);
/*      */ 
/*      */         
/*  329 */         validateSetup(entityItem, vector);
/*      */         
/*  331 */         vector.clear();
/*      */         
/*  333 */         if (getReturnCode() == 0) {
/*  334 */           LinkActionItem linkActionItem = new LinkActionItem(null, this.m_db, this.m_prof, "LINKAVAILFCTRANSACTION2");
/*  335 */           Vector vector1 = PokUtils.getAllLinkedEntities(entityItem, "FTSETUPAVAIL", "AVAIL");
/*      */           
/*  337 */           Vector vector2 = checkCurrentFctrans(entityItem, vector1, linkActionItem);
/*      */           
/*  339 */           System.gc();
/*      */ 
/*      */           
/*  342 */           createFctrans(entityItem, vector1, linkActionItem);
/*      */           
/*  344 */           vector1.clear();
/*      */ 
/*      */           
/*  347 */           if (getReturnCode() == 0 && this.fctstatus.equals("FCT4")) {
/*  348 */             moveToFinalAndQueue(vector2, (Vector)null);
/*      */           }
/*  350 */           vector2.clear();
/*  351 */           vector2 = null;
/*      */         }
/*  353 */         else if (!this.metaError) {
/*      */           
/*  355 */           EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*  356 */           if (entityGroup.getEntityItemCount() > 0) {
/*  357 */             Vector<EntityItem> vector1 = new Vector();
/*  358 */             for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  359 */               vector1.add(entityGroup.getEntityItem(b));
/*      */             }
/*  361 */             deleteFctrans(vector1);
/*  362 */             vector1.clear();
/*      */           }
/*      */         
/*      */         } 
/*      */       } else {
/*      */         
/*  368 */         handleSetTo(entityItem);
/*      */       } 
/*  370 */       addDebug("Total Time: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     }
/*  372 */     catch (Throwable throwable) {
/*  373 */       StringWriter stringWriter = new StringWriter();
/*  374 */       String str6 = "<h3><span style=\"color:#c00; font-weight:bold;\">Error: {0}</span></h3>";
/*  375 */       String str7 = "<pre>{0}</pre>";
/*  376 */       MessageFormat messageFormat1 = new MessageFormat(str6);
/*  377 */       setReturnCode(-3);
/*  378 */       throwable.printStackTrace(new PrintWriter(stringWriter));
/*      */       
/*  380 */       this.args[0] = throwable.getMessage();
/*  381 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  382 */       messageFormat1 = new MessageFormat(str7);
/*  383 */       this.args[0] = stringWriter.getBuffer().toString();
/*  384 */       this.rptSb.append(messageFormat1.format(this.args) + NEWLINE);
/*  385 */       logError("Exception: " + throwable.getMessage());
/*  386 */       logError(stringWriter.getBuffer().toString());
/*      */     }
/*      */     finally {
/*      */       
/*  390 */       setDGTitle(this.navName);
/*  391 */       setDGRptName(getShortClassName(getClass()));
/*  392 */       setDGRptClass(getABRCode());
/*      */       
/*  394 */       if (!isReadOnly())
/*      */       {
/*  396 */         clearSoftLock();
/*      */       }
/*  398 */       closePrintWriter();
/*      */     } 
/*      */ 
/*      */     
/*  402 */     this.rptSb.insert(0, getCountOutput() + NEWLINE);
/*      */ 
/*      */ 
/*      */     
/*  406 */     MessageFormat messageFormat = new MessageFormat(str1);
/*  407 */     this.args[0] = getDescription();
/*  408 */     this.args[1] = this.navName;
/*  409 */     String str5 = messageFormat.format(this.args);
/*  410 */     messageFormat = new MessageFormat(str2);
/*  411 */     this.args[0] = this.m_prof.getOPName();
/*  412 */     this.args[1] = this.m_prof.getRoleDescription();
/*  413 */     this.args[2] = this.m_prof.getWGName();
/*  414 */     this.args[3] = getNow();
/*  415 */     this.args[4] = str3;
/*  416 */     this.args[5] = (getReturnCode() == 0) ? "Passed" : "Failed";
/*  417 */     this.args[6] = str4 + " " + getABRVersion();
/*      */     
/*  419 */     restoreXtraContent();
/*      */     
/*  421 */     this.rptSb.insert(0, str5 + messageFormat.format(this.args) + NEWLINE);
/*      */     
/*  423 */     println(this.rptSb.toString());
/*  424 */     printDGSubmitString();
/*  425 */     println(EACustom.getTOUDiv());
/*  426 */     buildReportFooter();
/*      */     
/*  428 */     this.metaTbl.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   private void restoreXtraContent() {
/*  433 */     if (this.dbgfn != null && this.dbgLen + this.rptSb.length() < 5000000) {
/*      */       
/*  435 */       BufferedInputStream bufferedInputStream = null;
/*  436 */       FileInputStream fileInputStream = null;
/*  437 */       BufferedReader bufferedReader = null;
/*      */       try {
/*  439 */         fileInputStream = new FileInputStream(this.dbgfn);
/*  440 */         bufferedInputStream = new BufferedInputStream(fileInputStream);
/*      */         
/*  442 */         String str = null;
/*  443 */         StringBuffer stringBuffer = new StringBuffer();
/*  444 */         bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
/*      */         
/*  446 */         while ((str = bufferedReader.readLine()) != null) {
/*  447 */           stringBuffer.append(str + NEWLINE);
/*      */         }
/*  449 */         this.rptSb.append("<!-- " + stringBuffer.toString() + " -->" + NEWLINE);
/*      */ 
/*      */         
/*  452 */         File file = new File(this.dbgfn);
/*  453 */         if (file.exists()) {
/*  454 */           file.delete();
/*      */         }
/*  456 */       } catch (Exception exception) {
/*  457 */         exception.printStackTrace();
/*      */       } finally {
/*  459 */         if (bufferedInputStream != null) {
/*      */           try {
/*  461 */             bufferedInputStream.close();
/*  462 */           } catch (Exception exception) {
/*  463 */             exception.printStackTrace();
/*      */           } 
/*      */         }
/*  466 */         if (fileInputStream != null) {
/*      */           try {
/*  468 */             fileInputStream.close();
/*  469 */           } catch (Exception exception) {
/*  470 */             exception.printStackTrace();
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
/*      */   private String getCountOutput() {
/*  482 */     StringBuffer stringBuffer = new StringBuffer();
/*  483 */     if (this.fctstatus.equals("FCT2") || this.fctstatus.equals("FCT4")) {
/*  484 */       stringBuffer.append("<table><tr><th>Skipped: </th><td colspan='2'>&nbsp;</td></tr>");
/*  485 */       stringBuffer.append("<tr><td colspan='2'>&nbsp;&nbsp;Status = Final:</td><td> " + this.skippedFinalCnt + "</td></tr><tr><td colspan='2'>&nbsp;&nbsp;Feature Transaction Class = Manual:</td><td> " + this.skippedManualCnt + "</td></tr><tr><td colspan='2'>&nbsp;&nbsp;FT Active = No:</td><td> " + this.skippedNotActiveCnt + "</td></tr>");
/*      */ 
/*      */       
/*  488 */       stringBuffer.append("<tr><th>Created: </th><td>" + this.createdCnt + "</td><td>&nbsp;</td></tr>");
/*  489 */       stringBuffer.append("<tr><th>Updated data attributes: </th><td>" + this.updatedCnt + "</td><td>&nbsp;</td></tr>");
/*  490 */       stringBuffer.append("<tr><th>No Update to data attributes needed: </th><td>" + this.notUpdatedCnt + "</td><td>&nbsp;</td></tr>");
/*  491 */       if (this.fctstatus.equals("FCT4")) {
/*  492 */         String str = "ADS XML FEED ABR";
/*      */         
/*  494 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*  495 */         if (entityGroup != null) {
/*  496 */           str = PokUtils.getAttributeDescription(entityGroup, "ADSABRSTATUS", "ADSABRSTATUS");
/*      */         }
/*  498 */         stringBuffer.append("<tr><th>Set " + str + " to " + this.xmlabrSetTo + ": </th><td>" + this.xmlabrSetCnt + "</td></tr>");
/*  499 */         stringBuffer.append("<tr><th>Set Status to Final: </th><td>" + this.statusSetCnt + "</td></tr>");
/*      */       } 
/*  501 */       stringBuffer.append("<tr><th>Deleted: </th><td>" + this.deletedCnt + "</td><td>&nbsp;</td></tr></table>");
/*      */     } else {
/*  503 */       stringBuffer.append("<table>");
/*  504 */       if (this.fctstatus.equals("FCT1")) {
/*  505 */         stringBuffer.append("<tr><th>Set Status to Draft: </th><td>" + this.statusSetCnt + "</td></tr></table>");
/*      */       } else {
/*  507 */         String str = "ADS XML FEED ABR";
/*      */         
/*  509 */         EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*  510 */         if (entityGroup != null) {
/*  511 */           str = PokUtils.getAttributeDescription(entityGroup, "ADSABRSTATUS", "ADSABRSTATUS");
/*      */         }
/*  513 */         stringBuffer.append("<tr><th>Set " + str + " to " + this.xmlabrSetTo + ": </th><td>" + this.xmlabrSetCnt + "</td></tr>");
/*  514 */         stringBuffer.append("<tr><th>Set Status to Final: </th><td>" + this.statusSetCnt + "</td></tr></table>");
/*      */       } 
/*      */     } 
/*      */     
/*  518 */     return stringBuffer.toString();
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
/*      */   private Vector avoidInactiveFCTRANS() throws SQLException, MiddlewareException {
/*  535 */     Vector<String> vector = new Vector();
/*  536 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*  537 */     if (entityGroup.getEntityItemCount() > 0) {
/*  538 */       EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/*  539 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/*  540 */         EntityItem entityItem = arrayOfEntityItem[b];
/*  541 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "FTCLASS");
/*  542 */         String str2 = PokUtils.getAttributeFlagValue(entityItem, "FTVACTIVE");
/*  543 */         String str3 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  544 */         addDebug("avoidInactiveFCTRANS Checking " + entityItem.getKey() + " FTCLASS: " + str1 + " FTVACTIVE:" + str2 + " STATUS:" + str3);
/*      */ 
/*      */         
/*  547 */         if ("N".equals(str2)) {
/*  548 */           addDebug("avoidInactiveFCTRANS Removing " + entityItem.getKey() + " from group, it is marked inactive");
/*  549 */           removeItems(entityItem, true);
/*  550 */           vector.add(buildKey(entityItem));
/*  551 */           this.skippedNotActiveCnt++;
/*      */         
/*      */         }
/*  554 */         else if ("MAN".equals(str1)) {
/*  555 */           addDebug("avoidInactiveFCTRANS Removing " + entityItem.getKey() + " from group, it is marked manual");
/*  556 */           removeItems(entityItem, true);
/*  557 */           vector.add(buildKey(entityItem));
/*  558 */           this.skippedManualCnt++;
/*      */         
/*      */         }
/*  561 */         else if ("0020".equals(str3)) {
/*  562 */           addDebug("avoidInactiveFCTRANS Removing " + entityItem.getKey() + " from group, status is final");
/*  563 */           removeItems(entityItem, true);
/*  564 */           vector.add(buildKey(entityItem));
/*  565 */           this.skippedFinalCnt++;
/*      */         } 
/*      */       } 
/*      */     } 
/*  569 */     return vector;
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
/*      */   private void moveToFinalAndQueue(Vector<Integer> paramVector1, Vector<Integer> paramVector2) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  604 */     addDebug(0, "moveToFinalAndQueue entered curFctransIdVct: " + ((paramVector1 == null) ? "null" : ("" + paramVector1
/*  605 */         .size())) + " createdFctransIdVct: " + ((paramVector2 == null) ? "null" : ("" + paramVector2
/*  606 */         .size())));
/*      */     
/*  608 */     String str = getQueuedValueForItem("ADSABRSTATUS");
/*  609 */     if (!str.equals("0020")) {
/*  610 */       this.xmlabrSetTo = str;
/*      */     }
/*      */     
/*  613 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*      */     
/*  615 */     Vector vector = new Vector();
/*      */     
/*  617 */     if (this.m_cbOn == null) {
/*  618 */       DatePackage datePackage = this.m_db.getDates();
/*  619 */       this.m_strNow = datePackage.getNow();
/*  620 */       this.m_cbOn = new ControlBlock(this.m_strNow, this.m_strForever, this.m_strNow, this.m_strForever, this.m_prof.getOPWGID(), this.m_prof.getTranID());
/*      */     } 
/*      */     
/*  623 */     this.m_prof.setValOnEffOn(this.m_strNow, this.m_strNow);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  629 */     if (paramVector1 != null) {
/*  630 */       for (byte b = 0; b < paramVector1.size(); b++) {
/*  631 */         int i = ((Integer)paramVector1.elementAt(b)).intValue();
/*  632 */         EntityItem entityItem = entityGroup.getEntityItem("FCTRANSACTION" + i);
/*      */         
/*  634 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  635 */         addDebug(2, "moveToFinalAndQueue Checking " + entityItem.getKey() + " STATUS:" + str1);
/*      */         
/*  637 */         if (!"0020".equals(str1)) {
/*  638 */           setFlagValue("STATUS", "0020", i, vector);
/*  639 */           setFlagValue("DATAQUALITY", "FINAL", i, vector);
/*  640 */           this.statusSetCnt++;
/*      */         } 
/*      */         
/*  643 */         if (setFlagValue("ADSABRSTATUS", str, i, vector)) {
/*  644 */           this.xmlabrSetCnt++;
/*      */         }
/*      */         
/*  647 */         String str2 = PokUtils.getAttributeFlagValue(entityItem, "LIFECYCLE");
/*  648 */         addDebug(2, "moveToFinalAndQueue " + entityItem.getKey() + " LIFECYCLE: " + str2);
/*  649 */         if (str2 == null || str2.length() == 0) {
/*  650 */           str2 = "LF01";
/*      */         }
/*      */ 
/*      */         
/*  654 */         if ("LF01".equals(str2) || "LF02".equals(str2)) {
/*  655 */           setFlagValue("LIFECYCLE", "LF03", i, vector);
/*      */         }
/*      */         
/*  658 */         if (vector.size() >= UPDATE_SIZE) {
/*  659 */           updatePDH(vector);
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  666 */     if (paramVector2 != null) {
/*  667 */       for (byte b = 0; b < paramVector2.size(); b++) {
/*  668 */         int i = ((Integer)paramVector2.elementAt(b)).intValue();
/*  669 */         setFlagValue("STATUS", "0020", i, vector);
/*  670 */         this.statusSetCnt++;
/*      */         
/*  672 */         setFlagValue("DATAQUALITY", "FINAL", i, vector);
/*  673 */         if (setFlagValue("ADSABRSTATUS", str, i, vector)) {
/*  674 */           this.xmlabrSetCnt++;
/*      */         }
/*  676 */         setFlagValue("LIFECYCLE", "LF03", i, vector);
/*  677 */         if (vector.size() >= UPDATE_SIZE) {
/*  678 */           updatePDH(vector);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*  683 */     if (vector.size() > 0) {
/*  684 */       updatePDH(vector);
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
/*      */   private void setToFinal(EntityItem paramEntityItem, Vector<Integer> paramVector1, Vector<Integer> paramVector2, Vector paramVector3) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  705 */     String str = getQueuedValueForItem("ADSABRSTATUS");
/*  706 */     if (!str.equals("0020")) {
/*  707 */       this.xmlabrSetTo = str;
/*      */     }
/*      */     
/*  710 */     Vector vector = new Vector();
/*      */     
/*      */     byte b;
/*  713 */     for (b = 0; b < paramVector1.size(); b++) {
/*  714 */       Integer integer = paramVector1.elementAt(b);
/*  715 */       int i = integer.intValue();
/*  716 */       if (setFlagValue("ADSABRSTATUS", str, i, vector)) {
/*  717 */         this.xmlabrSetCnt++;
/*      */       }
/*      */       
/*  720 */       if (paramVector3.contains(integer)) {
/*  721 */         setFlagValue("LIFECYCLE", "LF03", i, vector);
/*      */       }
/*  723 */       if (vector.size() >= UPDATE_SIZE) {
/*  724 */         updatePDH(vector);
/*      */       }
/*      */     } 
/*      */     
/*  728 */     for (b = 0; b < paramVector2.size(); b++) {
/*  729 */       Integer integer = paramVector2.elementAt(b);
/*  730 */       int i = integer.intValue();
/*  731 */       setFlagValue("STATUS", "0020", i, vector);
/*  732 */       setFlagValue("DATAQUALITY", "FINAL", i, vector);
/*  733 */       if (setFlagValue("ADSABRSTATUS", str, i, vector)) {
/*  734 */         this.xmlabrSetCnt++;
/*      */       }
/*      */       
/*  737 */       if (paramVector3.contains(integer)) {
/*  738 */         setFlagValue("LIFECYCLE", "LF03", i, vector);
/*      */       }
/*  740 */       if (vector.size() >= UPDATE_SIZE) {
/*  741 */         updatePDH(vector);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  746 */     advanceFCTSTATUS(paramEntityItem, "FCT4", vector);
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
/*      */   private void advanceFCTSTATUS(EntityItem paramEntityItem, String paramString, Vector<ReturnEntityKey> paramVector) throws MiddlewareException, RemoteException, SQLException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  763 */     ReturnEntityKey returnEntityKey = new ReturnEntityKey(getEntityType(), getEntityID(), true);
/*  764 */     returnEntityKey.m_vctAttributes = new Vector();
/*  765 */     paramVector.addElement(returnEntityKey);
/*  766 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), getEntityType(), getEntityID(), "FCTSTATUS", paramString, 1, this.m_cbOn);
/*      */     
/*  768 */     returnEntityKey.m_vctAttributes.addElement(singleFlag);
/*      */     
/*  770 */     updatePDH(paramVector);
/*      */ 
/*      */     
/*  773 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("ATTR_SET"));
/*  774 */     this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "FCTSTATUS", "FCTSTATUS");
/*      */     
/*  776 */     this.args[1] = paramString;
/*      */     
/*  778 */     EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)paramEntityItem.getEntityGroup().getMetaAttribute("FCTSTATUS");
/*  779 */     if (eANMetaFlagAttribute != null) {
/*  780 */       MetaFlag metaFlag = eANMetaFlagAttribute.getMetaFlag(paramString);
/*  781 */       if (metaFlag != null) {
/*  782 */         this.args[1] = metaFlag.toString();
/*      */       }
/*      */     } 
/*  785 */     this.args[2] = paramEntityItem.getEntityGroup().getLongDescription();
/*  786 */     this.args[3] = getNavigationName(paramEntityItem);
/*  787 */     addOutput(messageFormat.format(this.args));
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
/*      */   private void setToDraft(EntityItem paramEntityItem, Vector<Integer> paramVector) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  802 */     Vector vector = new Vector();
/*      */ 
/*      */     
/*  805 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  806 */       int i = ((Integer)paramVector.elementAt(b)).intValue();
/*  807 */       setFlagValue("STATUS", "0010", i, vector);
/*  808 */       setFlagValue("DATAQUALITY", "DRAFT", i, vector);
/*  809 */       if (vector.size() >= UPDATE_SIZE) {
/*  810 */         updatePDH(vector);
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  815 */     advanceFCTSTATUS(paramEntityItem, "FCT2", vector);
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
/*      */   private void updatePDH(Vector paramVector) throws SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  829 */     logMessage(getDescription() + " updating PDH");
/*  830 */     addDebug("updatePDH entered for vctReturnsEntityKeys: " + paramVector.size());
/*      */     
/*  832 */     if (paramVector.size() > 0) {
/*      */       try {
/*  834 */         this.m_db.update(this.m_prof, paramVector, false, false);
/*      */       } finally {
/*      */         
/*  837 */         paramVector.clear();
/*  838 */         this.m_db.commit();
/*  839 */         this.m_db.freeStatement();
/*  840 */         this.m_db.isPending("finally after updatePDH");
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getQueuedValueForItem(String paramString) {
/*  850 */     String str1 = "FCTRANABRSTATUS";
/*  851 */     String str2 = "_queuedValue";
/*  852 */     return ABRServerProperties.getValue(str1, "_" + paramString + str2, "0020");
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
/*      */   private boolean setFlagValue(String paramString1, String paramString2, int paramInt, Vector<ReturnEntityKey> paramVector) throws SQLException, MiddlewareException {
/*  870 */     addDebug(2, "setFlagValue entered FCTRANSACTION" + paramInt + " for " + paramString1 + " set to: " + paramString2);
/*      */ 
/*      */     
/*  873 */     if (paramString2 == null || paramString2.trim().length() == 0) {
/*  874 */       addDebug(0, "setFlagValue " + paramString1 + " value was empty for FCTRANSACTION" + paramInt + ". nothing to do");
/*      */       
/*  876 */       return false;
/*      */     } 
/*      */ 
/*      */     
/*  880 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*  881 */     EANMetaAttribute eANMetaAttribute = entityGroup.getMetaAttribute(paramString1);
/*  882 */     if (eANMetaAttribute == null) {
/*  883 */       if (!this.outputMetaMsg) {
/*      */         
/*  885 */         this.args[0] = paramString1;
/*  886 */         this.args[1] = entityGroup.getEntityType();
/*  887 */         addWarning("META_ERR", this.args);
/*  888 */         this.outputMetaMsg = true;
/*      */       } 
/*  890 */       return false;
/*      */     } 
/*      */     
/*  893 */     Vector<SingleFlag> vector = null;
/*      */     
/*  895 */     for (byte b = 0; b < paramVector.size(); b++) {
/*  896 */       ReturnEntityKey returnEntityKey = paramVector.elementAt(b);
/*  897 */       if (returnEntityKey.getEntityID() == paramInt && returnEntityKey
/*  898 */         .getEntityType().equals("FCTRANSACTION")) {
/*  899 */         vector = returnEntityKey.m_vctAttributes;
/*      */         break;
/*      */       } 
/*      */     } 
/*  903 */     if (vector == null) {
/*  904 */       ReturnEntityKey returnEntityKey = new ReturnEntityKey("FCTRANSACTION", paramInt, true);
/*  905 */       vector = new Vector();
/*  906 */       returnEntityKey.m_vctAttributes = vector;
/*  907 */       paramVector.addElement(returnEntityKey);
/*      */     } 
/*      */     
/*  910 */     SingleFlag singleFlag = new SingleFlag(this.m_prof.getEnterprise(), "FCTRANSACTION", paramInt, paramString1, paramString2, 1, this.m_cbOn);
/*      */ 
/*      */     
/*  913 */     vector.addElement(singleFlag);
/*      */     
/*  915 */     return true;
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
/*      */   private void handleSetTo(EntityItem paramEntityItem) throws RemoteException, SQLException, MiddlewareException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/*  959 */     if (this.m_cbOn == null) {
/*  960 */       setControlBlock();
/*      */     }
/*      */     
/*  963 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*  964 */     if (this.fctstatus.equals("FCT1")) {
/*  965 */       Vector<Integer> vector = new Vector();
/*  966 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  967 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  968 */         String str = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  969 */         addDebug(2, "handleSetTo Draft Checking " + entityItem.getKey() + " STATUS:" + str);
/*      */         
/*  971 */         if (!"0010".equals(str)) {
/*  972 */           vector.add(new Integer(entityItem.getEntityID()));
/*  973 */           this.statusSetCnt++;
/*      */         } 
/*      */       } 
/*      */       
/*  977 */       setToDraft(paramEntityItem, vector);
/*      */       
/*  979 */       vector.clear();
/*      */     } else {
/*  981 */       Vector<Integer> vector1 = new Vector();
/*  982 */       Vector<Integer> vector2 = new Vector();
/*  983 */       Vector<Integer> vector3 = new Vector();
/*  984 */       for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/*  985 */         EntityItem entityItem = entityGroup.getEntityItem(b);
/*  986 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "STATUS");
/*  987 */         String str2 = PokUtils.getAttributeFlagValue(entityItem, "LIFECYCLE");
/*  988 */         addDebug(2, "handleSetTo Final Checking " + entityItem.getKey() + " STATUS:" + str1 + " LIFECYCLE: " + str2);
/*      */         
/*  990 */         if (str2 == null || str2.length() == 0) {
/*  991 */           str2 = "LF01";
/*      */         }
/*  993 */         Integer integer = new Integer(entityItem.getEntityID());
/*      */         
/*  995 */         if ("LF01".equals(str2) || "LF02".equals(str2)) {
/*  996 */           vector3.add(integer);
/*      */         }
/*  998 */         if ("0020".equals(str1)) {
/*  999 */           vector1.add(integer);
/*      */         } else {
/* 1001 */           vector2.add(integer);
/* 1002 */           this.statusSetCnt++;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1007 */       setToFinal(paramEntityItem, vector1, vector2, vector3);
/*      */       
/* 1009 */       vector1.clear();
/* 1010 */       vector2.clear();
/* 1011 */       vector3.clear();
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
/*      */   private Vector checkCurrentFctrans(EntityItem paramEntityItem, Vector paramVector, LinkActionItem paramLinkActionItem) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, RemoteException, WorkflowException {
/* 1037 */     Vector<EntityItem> vector1 = new Vector(1);
/* 1038 */     Vector<EntityItem> vector2 = new Vector(1);
/* 1039 */     Vector<Integer> vector = new Vector(0);
/*      */     
/* 1041 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/* 1042 */     if (entityGroup.getEntityItemCount() > 0) {
/* 1043 */       EntityItem[] arrayOfEntityItem = entityGroup.getEntityItemsAsArray();
/* 1044 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 1045 */         EntityItem entityItem = arrayOfEntityItem[b];
/* 1046 */         String str = buildKey(entityItem);
/* 1047 */         if (this.fcTransComboTbl.containsKey(str)) {
/* 1048 */           addDebug("checkCurrentFctrans " + str + " matches " + entityItem.getKey());
/* 1049 */           vector1.add(entityItem);
/* 1050 */           vector.add(new Integer(entityItem.getEntityID()));
/* 1051 */           this.fcTransComboTbl.remove(str);
/*      */         } else {
/* 1053 */           addDebug("checkCurrentFctrans NO match for " + str + " deleting " + entityItem.getKey());
/* 1054 */           vector2.add(entityItem);
/* 1055 */           removeItems(entityItem, false);
/*      */         } 
/*      */       } 
/*      */       
/* 1059 */       deleteFctrans(vector2);
/* 1060 */       updateFctrans(vector1, paramEntityItem);
/*      */     } 
/*      */     
/* 1063 */     vector2.clear();
/* 1064 */     vector1.clear();
/*      */     
/* 1066 */     addDebug("checkCurrentFctrans:  List after checks: " + PokUtils.outputList(this.m_elist));
/*      */ 
/*      */     
/* 1069 */     updateFCTRANSAvails(paramEntityItem, paramVector, paramLinkActionItem);
/*      */     
/* 1071 */     return vector;
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
/*      */   private void deleteFctrans(Vector<EntityItem> paramVector) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 1088 */     addDebug("deleteFctrans cnt " + paramVector.size());
/* 1089 */     if (paramVector.size() == 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1093 */     DeleteActionItem deleteActionItem = new DeleteActionItem(null, this.m_db, this.m_prof, "DELFCTRANSACTION");
/*      */     
/* 1095 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()];
/* 1096 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1097 */       EntityItem entityItem = paramVector.elementAt(b);
/*      */       
/* 1099 */       arrayOfEntityItem[b] = (EntityItem)entityItem.getUpLink(0);
/* 1100 */       addDebug("deleteFctrans " + entityItem.getKey() + " " + arrayOfEntityItem[b].getKey());
/*      */     } 
/* 1102 */     long l = System.currentTimeMillis();
/*      */ 
/*      */     
/* 1105 */     deleteActionItem.setEntityItems(arrayOfEntityItem);
/* 1106 */     this.m_db.executeAction(this.m_prof, deleteActionItem);
/* 1107 */     this.deletedCnt = arrayOfEntityItem.length;
/*      */     
/* 1109 */     addDebug("Time to delete unmatched fctrans: " + Stopwatch.format(System.currentTimeMillis() - l));
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
/*      */   private void updateFctrans(Vector<EntityItem> paramVector, EntityItem paramEntityItem) throws SQLException, MiddlewareException, EANBusinessRuleException, RemoteException, MiddlewareShutdownInProgressException {
/* 1126 */     addDebug("updateFctrans cnt " + paramVector.size());
/* 1127 */     if (paramVector.size() == 0) {
/*      */       return;
/*      */     }
/* 1130 */     AttrSet attrSet = new AttrSet(paramEntityItem, false);
/*      */     
/* 1132 */     byte b1 = 0;
/* 1133 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/* 1134 */       EntityItem entityItem = paramVector.elementAt(b2);
/*      */       
/* 1136 */       boolean bool = updateFctrans(entityItem, attrSet.getAttrCodes(), attrSet.getAttrValues());
/* 1137 */       if (bool) {
/* 1138 */         addDebug("updateFctrans " + entityItem.getKey() + " has changes");
/* 1139 */         b1++;
/*      */       } else {
/* 1141 */         addDebug("updateFctrans " + entityItem.getKey() + " does not need changes, but must check avails");
/* 1142 */         this.notUpdatedCnt++;
/*      */       } 
/*      */     } 
/*      */     
/* 1146 */     if (b1 > 0) {
/* 1147 */       long l = System.currentTimeMillis();
/*      */       
/* 1149 */       this.m_elist.getEntityGroup("FTSETUPFCTRAN").commit(this.m_db, null);
/* 1150 */       this.updatedCnt = b1;
/* 1151 */       addDebug("Time to commit changed fctrans: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     } 
/*      */     
/* 1154 */     attrSet.dereference();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void removeItems(EntityItem paramEntityItem, boolean paramBoolean) {
/* 1162 */     paramEntityItem.getEntityGroup().removeEntityItem(paramEntityItem);
/* 1163 */     EntityItem entityItem = (EntityItem)paramEntityItem.getUpLink(0);
/* 1164 */     entityItem.getEntityGroup().removeEntityItem(entityItem);
/* 1165 */     if (paramBoolean) {
/* 1166 */       paramEntityItem.removeUpLink((EANEntity)entityItem);
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
/*      */   private boolean updateFctrans(EntityItem paramEntityItem, Vector<String> paramVector, Hashtable paramHashtable) throws MiddlewareRequestException, EANBusinessRuleException {
/* 1198 */     boolean bool1 = false;
/* 1199 */     boolean bool2 = (paramEntityItem.getEntityID() < 0) ? true : false;
/*      */ 
/*      */     
/* 1202 */     for (byte b = 0; b < paramVector.size(); b++) {
/* 1203 */       String str = paramVector.elementAt(b);
/* 1204 */       StringBuffer stringBuffer = new StringBuffer();
/*      */       
/* 1206 */       EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(str);
/* 1207 */       if (eANMetaAttribute == null) {
/* 1208 */         addDebug("MetaAttribute cannot be found " + paramEntityItem.getEntityGroup().getEntityType() + "." + str + "\n");
/*      */       } else {
/*      */         String str1; boolean bool;
/* 1211 */         Object object = paramHashtable.get(str);
/* 1212 */         switch (eANMetaAttribute.getAttributeType().charAt(0)) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 'L':
/*      */           case 'T':
/*      */           case 'X':
/* 1219 */             str1 = "";
/* 1220 */             if (!bool2) {
/* 1221 */               str1 = PokUtils.getAttributeValue(paramEntityItem, str, "", "", false);
/*      */             }
/* 1223 */             if (!object.equals(str1)) {
/* 1224 */               if (!bool2) {
/* 1225 */                 addDebug(2, "updateFctrans " + paramEntityItem.getKey() + " Updating " + str + " was: " + str1 + " newval " + object);
/*      */               }
/*      */               
/* 1228 */               ABRUtil.setText(paramEntityItem, str, (String)object, stringBuffer);
/* 1229 */               bool1 = true;
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1312 */             if (stringBuffer.length() > 0)
/* 1313 */               addDebug(2, stringBuffer.toString());  break;case 'U': str1 = ""; if (!bool2) str1 = PokUtils.getAttributeFlagValue(paramEntityItem, str);  if (!object.equals(str1)) { if (str1 == null && object.equals("")) break;  if (!bool2) addDebug(2, "updateFctrans " + paramEntityItem.getKey() + " Updating " + str + " was: " + str1 + " newval " + object);  ABRUtil.setUniqueFlag(paramEntityItem, str, (String)object, stringBuffer); bool1 = true; }  if (stringBuffer.length() > 0) addDebug(2, stringBuffer.toString());  break;case 'F': str1 = ""; if (!bool2) str1 = PokUtils.getAttributeFlagValue(paramEntityItem, str);  bool = false; if (str1 == null) { if (object instanceof String && object.equals("")) break;  if (!bool2) addDebug(2, "updateFctrans " + paramEntityItem.getKey() + " Updating " + str + " was: " + str1 + " newval " + object);  bool = true; } else if (object instanceof String) { if (!object.equals(str1)) { if (!bool2) addDebug(2, "updateFctrans " + paramEntityItem.getKey() + " " + str + " needs to be updated, " + str1 + " newval " + object);  bool = true; }  } else { Vector<?> vector = (Vector)object; String[] arrayOfString = PokUtils.convertToArray(str1); Vector<String> vector1 = new Vector(arrayOfString.length); for (byte b1 = 0; b1 < arrayOfString.length; b1++) vector1.addElement(arrayOfString[b1]);  if (!vector1.containsAll(vector) || !vector.containsAll(vector1)) { if (!bool2) addDebug(2, "updateFctrans " + paramEntityItem.getKey() + " " + str + " needs to be updated");  bool = true; }  }  if (bool) { Vector<Object> vector = null; if (object instanceof String) { vector = new Vector(); if (!object.equals("")) vector.addElement(object);  } else { vector = (Vector<Object>)object; }  ABRUtil.setMultiFlag(paramEntityItem, str, vector, stringBuffer); bool1 = true; }  if (stringBuffer.length() > 0) addDebug(2, stringBuffer.toString());  break;default: addDebug("MetaAttribute Type=" + eANMetaAttribute.getAttributeType() + " is not supported yet " + paramEntityItem.getEntityGroup().getEntityType() + "." + str + "\n"); if (stringBuffer.length() > 0) addDebug(2, stringBuffer.toString());  break;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1317 */     return bool1;
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
/*      */   private void createFctrans(EntityItem paramEntityItem, Vector paramVector, LinkActionItem paramLinkActionItem) throws RemoteException, SQLException, MiddlewareException, EANBusinessRuleException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/* 1339 */     addDebug("createFctrans cnt " + this.fcTransComboTbl.size());
/*      */     
/* 1341 */     if (this.fcTransComboTbl.size() > 0) {
/* 1342 */       AttrSet attrSet = new AttrSet(paramEntityItem, true);
/*      */       
/* 1344 */       CreateActionItem createActionItem = new CreateActionItem(null, this.m_db, this.m_prof, "CRFCTRANSACTION2");
/*      */       
/* 1346 */       Vector<FctransSet> vector = new Vector();
/*      */       
/* 1348 */       for (Enumeration<FctransSet> enumeration = this.fcTransComboTbl.elements(); enumeration.hasMoreElements(); ) {
/* 1349 */         FctransSet fctransSet = enumeration.nextElement();
/* 1350 */         vector.add(fctransSet);
/*      */         
/* 1352 */         if (vector.size() == UPDATE_SIZE || !enumeration.hasMoreElements()) {
/* 1353 */           Vector vector1 = createAndCommitFctrans(paramEntityItem, attrSet, vector, paramVector, paramLinkActionItem, createActionItem);
/* 1354 */           this.createdCnt += vector1.size();
/*      */           
/* 1356 */           vector.clear();
/*      */         } 
/*      */       } 
/*      */       
/* 1360 */       attrSet.dereference();
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
/*      */   private Vector createAndCommitFctrans(EntityItem paramEntityItem, AttrSet paramAttrSet, Vector<FctransSet> paramVector1, Vector paramVector2, LinkActionItem paramLinkActionItem, CreateActionItem paramCreateActionItem) throws EANBusinessRuleException, SQLException, MiddlewareException, RemoteException, MiddlewareShutdownInProgressException, LockException, WorkflowException {
/* 1386 */     addDebug("createAndCommitFctrans cnt " + paramVector1.size());
/* 1387 */     Vector<Integer> vector = new Vector(0);
/* 1388 */     paramCreateActionItem.setNumberOfItems(paramVector1.size());
/* 1389 */     long l = System.currentTimeMillis();
/* 1390 */     EntityList entityList = createFctrans(paramEntityItem, paramCreateActionItem);
/* 1391 */     addDebug("Time to get entitylist " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     
/* 1393 */     EntityGroup entityGroup = entityList.getEntityGroup("FCTRANSACTION");
/* 1394 */     if (entityGroup != null && entityGroup.getEntityItemCount() > 0) {
/* 1395 */       addDebug("createFctrans set cnt " + paramVector1.size() + " new item cnt:" + entityGroup.getEntityItemCount());
/*      */ 
/*      */       
/* 1398 */       byte b1 = 0;
/*      */       
/* 1400 */       for (byte b2 = 0; b2 < paramVector1.size(); b2++) {
/* 1401 */         FctransSet fctransSet = paramVector1.elementAt(b2);
/*      */         
/* 1403 */         Vector<String> vector1 = new Vector(paramAttrSet.getAttrCodes().size() + FCTRANS_KEYS.length);
/* 1404 */         Hashtable<Object, Object> hashtable = new Hashtable<>(paramAttrSet.getAttrValues().size() + FCTRANS_KEYS.length);
/* 1405 */         vector1.addAll(paramAttrSet.getAttrCodes());
/* 1406 */         hashtable.putAll(paramAttrSet.getAttrValues());
/*      */         
/* 1408 */         for (byte b = 0; b < FCTRANS_KEYS.length; b++) {
/* 1409 */           vector1.add(FCTRANS_KEYS[b]);
/* 1410 */           hashtable.put(FCTRANS_KEYS[b], fctransSet.get(FCTRANS_KEYS[b]));
/*      */         } 
/*      */         
/* 1413 */         EntityItem entityItem = entityGroup.getEntityItem(b1);
/* 1414 */         if (entityItem == null) {
/* 1415 */           addDebug("createFctrans: CODE ERROR fctransItem was null for tmpCreateCnt: " + b1);
/*      */           break;
/*      */         } 
/* 1418 */         addDebug(4, "createFctrans: setting attributes for " + entityItem.getKey());
/* 1419 */         updateFctrans(entityItem, vector1, hashtable);
/* 1420 */         b1++;
/*      */       } 
/*      */       
/* 1423 */       if (b1 > 0) {
/*      */         
/*      */         try {
/* 1426 */           long l1 = System.currentTimeMillis();
/* 1427 */           entityList.getEntityGroup("FTSETUPFCTRAN").commit(this.m_db, null);
/*      */           
/* 1429 */           addDebug("createFctrans: created List after commit: " + PokUtils.outputList(entityList));
/* 1430 */           addDebug("Time to commit " + b1 + " new fctrans: " + Stopwatch.format(System.currentTimeMillis() - l1));
/* 1431 */           if (paramVector2.size() > 0) {
/* 1432 */             linkAvails(entityGroup, paramVector2, paramLinkActionItem);
/*      */           } else {
/* 1434 */             addDebug("WARNING: no AVAILs linked to " + paramEntityItem.getKey());
/*      */           } 
/* 1436 */           EntityGroup entityGroup1 = entityList.getEntityGroup("FCTRANSACTION");
/*      */           
/* 1438 */           for (byte b = 0; b < entityGroup1.getEntityItemCount(); b++) {
/* 1439 */             EntityItem entityItem = entityGroup1.getEntityItem(b);
/* 1440 */             addDebug(4, "createFctrans created " + entityItem.getKey());
/* 1441 */             vector.add(new Integer(entityItem.getEntityID()));
/*      */           } 
/*      */           
/* 1444 */           if (this.fctstatus.equals("FCT4")) {
/* 1445 */             moveToFinalAndQueue((Vector)null, vector);
/*      */           }
/*      */           
/* 1448 */           addDebug("Time to create " + vector.size() + " fctrans and relators: " + Stopwatch.format(System.currentTimeMillis() - l));
/* 1449 */         } catch (MiddlewareBusinessRuleException middlewareBusinessRuleException) {
/*      */           
/* 1451 */           this.args[0] = PokUtils.convertToHTML(middlewareBusinessRuleException.toString());
/* 1452 */           addError("MBRE_ERR", this.args);
/*      */         } 
/*      */       }
/*      */       
/* 1456 */       entityList.dereference();
/*      */     } else {
/*      */       
/* 1459 */       this.args[0] = this.m_elist.getEntityGroup("FCTRANSACTION").getLongDescription();
/* 1460 */       addError("CREATE_ERR", this.args);
/*      */     } 
/*      */     
/* 1463 */     return vector;
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
/*      */   private EntityList createFctrans(EntityItem paramEntityItem, CreateActionItem paramCreateActionItem) throws SQLException, MiddlewareException {
/* 1478 */     EntityList entityList = new EntityList(this.m_db, this.m_prof, paramCreateActionItem, new EntityItem[] { paramEntityItem });
/* 1479 */     addDebug("createFctrans create List: " + PokUtils.outputList(entityList));
/* 1480 */     return entityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String buildKey(EntityItem paramEntityItem) {
/* 1489 */     StringBuffer stringBuffer = new StringBuffer();
/* 1490 */     for (byte b = 0; b < FCTRANS_KEYS.length; b++) {
/*      */       
/* 1492 */       if (!"COMMENT".equals(FCTRANS_KEYS[b])) {
/*      */ 
/*      */         
/* 1495 */         if (b > 0) {
/* 1496 */           stringBuffer.append(":");
/*      */         }
/* 1498 */         stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, FCTRANS_KEYS[b], "", "", false));
/*      */       } 
/* 1500 */     }  return stringBuffer.toString();
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
/*      */   private void validateSetup(EntityItem paramEntityItem, Vector<E> paramVector) throws MiddlewareRequestException, SQLException, MiddlewareException, EANBusinessRuleException, MiddlewareShutdownInProgressException {
/* 1585 */     EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, "MODEL", "Edit");
/*      */     
/* 1587 */     EANMetaFlagAttribute eANMetaFlagAttribute = (EANMetaFlagAttribute)entityGroup.getMetaAttribute("MACHTYPEATR");
/* 1588 */     String str1 = PokUtils.getAttributeValue(paramEntityItem, "TOMT", "", "", false);
/* 1589 */     String str2 = PokUtils.getAttributeValue(paramEntityItem, "FROMMT", "", "", false);
/* 1590 */     addDebug("validateSetup " + paramEntityItem.getKey() + " TOMT:" + str1 + " FROMMT:" + str2);
/* 1591 */     if (!eANMetaFlagAttribute.containsMetaFlag(str1)) {
/*      */       
/* 1593 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "TOMT", "TOMT");
/* 1594 */       this.args[1] = str1;
/* 1595 */       addError("INVALID_MT_ERR", this.args);
/*      */     } 
/* 1597 */     if (!eANMetaFlagAttribute.containsMetaFlag(str2)) {
/*      */       
/* 1599 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "FROMMT", "FROMMT");
/* 1600 */       this.args[1] = str2;
/* 1601 */       addError("INVALID_MT_ERR", this.args);
/*      */     } 
/*      */     
/* 1604 */     String str3 = PokUtils.getAttributeFlagValue(paramEntityItem, "FTRULE");
/* 1605 */     if (str3 == null) {
/*      */       
/* 1607 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "FTRULE", "FTRULE");
/* 1608 */       addError("NO_VALUE_ERR", this.args);
/*      */     } 
/* 1610 */     String str4 = PokUtils.getAttributeFlagValue(paramEntityItem, "HWFCCAT");
/* 1611 */     if (str4 == null) {
/*      */       
/* 1613 */       this.args[0] = PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), "HWFCCAT", "HWFCCAT");
/* 1614 */       addError("NO_VALUE_ERR", this.args);
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
/* 1625 */     if ((!"213".equals(str4) || !"40".equals(str3)) && (
/*      */       
/* 1627 */       !"305".equals(str4) || !"20".equals(str3))) {
/*      */ 
/*      */ 
/*      */       
/* 1631 */       this.args[0] = getLD_Value(paramEntityItem, "HWFCCAT");
/* 1632 */       this.args[1] = getLD_Value(paramEntityItem, "FTRULE");
/* 1633 */       addError("INVALID_COMBO_ERR", this.args);
/*      */     } 
/*      */     
/* 1636 */     for (byte b1 = 0; b1 < FCTRANSLIST_ATTR.length; b1++) {
/* 1637 */       EANMetaAttribute eANMetaAttribute = paramEntityItem.getEntityGroup().getMetaAttribute(FCTRANSLIST_ATTR[b1]);
/* 1638 */       if (eANMetaAttribute == null) {
/* 1639 */         this.metaError = true;
/* 1640 */         addOutput("ERROR: " + FCTRANSLIST_ATTR[b1] + " not found in " + paramEntityItem.getEntityGroup() + " meta!");
/* 1641 */         setReturnCode(-1);
/*      */       } 
/*      */     } 
/*      */     
/* 1645 */     if (getReturnCode() != 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1649 */     String str5 = PokUtils.getAttributeValue(paramEntityItem, "TOMDL", "", "", false);
/* 1650 */     String str6 = PokUtils.getAttributeValue(paramEntityItem, "FROMMDL", "", "", false);
/* 1651 */     String str7 = PokUtils.getAttributeValue(paramEntityItem, "TOFC", "", "", false);
/* 1652 */     String str8 = PokUtils.getAttributeValue(paramEntityItem, "FROMFC", "", "", false);
/*      */     
/* 1654 */     addDebug("validateSetup " + paramEntityItem.getKey() + " TOMDL:" + str5 + " FROMMDL:" + str6 + " TOFC:" + str7 + " FROMFC:" + str8 + " HWFCCAT:" + str4);
/*      */ 
/*      */     
/* 1657 */     long l = System.currentTimeMillis();
/*      */     
/* 1659 */     EntityItem[] arrayOfEntityItem1 = searchForModels(str1, str5, "SRDMODEL4");
/* 1660 */     EntityItem[] arrayOfEntityItem2 = searchForModels(str2, str6, "SRDMODEL6");
/* 1661 */     addDebug("Time to do searches: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     
/* 1663 */     if (arrayOfEntityItem1 == null || arrayOfEntityItem1.length == 0) {
/*      */       
/* 1665 */       this.args[0] = getLD_Value(paramEntityItem, "TOMT");
/* 1666 */       this.args[1] = getLD_Value(paramEntityItem, "TOMDL");
/* 1667 */       addError("MDL_NOTFOUND_ERR", this.args);
/*      */     } 
/* 1669 */     if (arrayOfEntityItem2 == null || arrayOfEntityItem2.length == 0) {
/*      */       
/* 1671 */       this.args[0] = getLD_Value(paramEntityItem, "FROMMT");
/* 1672 */       this.args[1] = getLD_Value(paramEntityItem, "FROMMDL");
/* 1673 */       addError("MDL_NOTFOUND_ERR", this.args);
/*      */     } 
/*      */     
/* 1676 */     if (getReturnCode() != 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1680 */     String str9 = (String)HWFCCAT_VE_VCT.get(str4);
/* 1681 */     if (str9 == null) {
/* 1682 */       str9 = "EXFTMODEL";
/*      */     }
/*      */     
/* 1685 */     l = System.currentTimeMillis();
/*      */     
/* 1687 */     String str10 = PokUtils.getAttributeFlagValue(paramEntityItem, "FCTRANAPP");
/* 1688 */     addDebug("validateSetup FCTRANAPP " + str10);
/* 1689 */     boolean bool = "FCY".equals(str10);
/*      */ 
/*      */     
/* 1692 */     EntityList entityList1 = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str9), arrayOfEntityItem1);
/*      */     
/* 1694 */     addDebug("validateSetup " + str9 + " toList: " + PokUtils.outputList(entityList1));
/* 1695 */     addDebug("Time to pull TO VE: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */ 
/*      */     
/* 1698 */     Vector vector1 = buildMTMFCSet(entityList1, "TO", str7, str4, bool);
/* 1699 */     addDebug("toVct: " + vector1);
/* 1700 */     entityList1.dereference();
/* 1701 */     if (vector1.size() == 0) {
/* 1702 */       String str = "HWFCCAT_NOTFOUND_ERR";
/*      */ 
/*      */       
/* 1705 */       this.args[0] = getLD_Value(paramEntityItem, "HWFCCAT");
/* 1706 */       this.args[1] = getLD_Value(paramEntityItem, "TOFC");
/* 1707 */       if (bool) {
/*      */         
/* 1709 */         this.args[2] = getLD_Value(paramEntityItem, "FCTRANAPP");
/* 1710 */         str = "HWFCCAT_FCTRANAPP_NOTFOUND_ERR";
/*      */       } 
/* 1712 */       addError(str, this.args);
/*      */     } 
/*      */     
/* 1715 */     l = System.currentTimeMillis();
/* 1716 */     EntityList entityList2 = this.m_db.getEntityList(this.m_prof, new ExtractActionItem(null, this.m_db, this.m_prof, str9), arrayOfEntityItem2);
/*      */     
/* 1718 */     addDebug("validateSetup " + str9 + " fromList: " + PokUtils.outputList(entityList2));
/* 1719 */     addDebug("Time to pull FROM VE: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     
/* 1721 */     Vector vector2 = buildMTMFCSet(entityList2, "FROM", str8, str4, bool);
/* 1722 */     addDebug("fromVct: " + vector2);
/* 1723 */     entityList2.dereference();
/* 1724 */     if (vector2.size() == 0) {
/* 1725 */       String str = "HWFCCAT_NOTFOUND_ERR";
/*      */ 
/*      */       
/* 1728 */       this.args[0] = getLD_Value(paramEntityItem, "HWFCCAT");
/* 1729 */       this.args[1] = getLD_Value(paramEntityItem, "FROMFC");
/* 1730 */       if (bool) {
/*      */         
/* 1732 */         this.args[2] = getLD_Value(paramEntityItem, "FCTRANAPP");
/* 1733 */         str = "HWFCCAT_FCTRANAPP_NOTFOUND_ERR";
/*      */       } 
/* 1735 */       addError(str, this.args);
/*      */     } 
/*      */     
/* 1738 */     if (getReturnCode() != 0) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1743 */     this.fcTransComboTbl = new Hashtable<>();
/*      */     
/* 1745 */     if ("40".equals(str3)) {
/* 1746 */       joinEqualOrGreater(vector1, vector2);
/* 1747 */     } else if ("30".equals(str3)) {
/* 1748 */       joinOnlyGreater(vector1, vector2);
/* 1749 */     } else if ("10".equals(str3)) {
/* 1750 */       joinAll(vector1, vector2);
/* 1751 */     } else if ("20".equals(str3)) {
/* 1752 */       joinAllButSelf(vector1, vector2);
/*      */     } 
/*      */     
/* 1755 */     addDebug("fcTransComboTbl " + this.fcTransComboTbl);
/* 1756 */     if (this.fcTransComboTbl.size() == 0) {
/*      */       
/* 1758 */       this.args[0] = getLD_Value(paramEntityItem, "FROMMT");
/* 1759 */       this.args[1] = getLD_Value(paramEntityItem, "FROMMDL");
/* 1760 */       this.args[2] = getLD_Value(paramEntityItem, "FROMFC");
/* 1761 */       this.args[3] = getLD_Value(paramEntityItem, "TOMT");
/* 1762 */       this.args[4] = getLD_Value(paramEntityItem, "TOMDL");
/* 1763 */       this.args[5] = getLD_Value(paramEntityItem, "TOFC");
/* 1764 */       addError("NOTFOUND_ERR", this.args);
/*      */     } 
/*      */     
/* 1767 */     for (byte b2 = 0; b2 < paramVector.size(); b2++) {
/* 1768 */       String str = paramVector.elementAt(b2).toString();
/* 1769 */       if (this.fcTransComboTbl.containsKey(str)) {
/* 1770 */         this.fcTransComboTbl.remove(str);
/* 1771 */         addDebug("validateSetup: removing " + str + " from fcTransComboTbl because it exists and will be skipped");
/*      */       } 
/*      */     } 
/*      */     
/* 1775 */     vector1.clear();
/* 1776 */     vector2.clear();
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
/*      */   private void joinEqualOrGreater(Vector<MTMFCSet> paramVector1, Vector<MTMFCSet> paramVector2) {
/* 1872 */     addDebug("Joining equal or greater");
/* 1873 */     for (byte b = 0; b < paramVector2.size(); b++) {
/* 1874 */       MTMFCSet mTMFCSet = paramVector2.elementAt(b);
/* 1875 */       for (byte b1 = 0; b1 < paramVector1.size(); b1++) {
/* 1876 */         MTMFCSet mTMFCSet1 = paramVector1.elementAt(b1);
/* 1877 */         if (mTMFCSet1.getCapacity() >= mTMFCSet.getCapacity()) {
/* 1878 */           FctransSet fctransSet = new FctransSet(mTMFCSet, mTMFCSet1);
/* 1879 */           this.fcTransComboTbl.put(fctransSet.getKey(), fctransSet);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void joinOnlyGreater(Vector<MTMFCSet> paramVector1, Vector<MTMFCSet> paramVector2) {
/* 1885 */     addDebug("Joining only greater");
/* 1886 */     for (byte b = 0; b < paramVector2.size(); b++) {
/* 1887 */       MTMFCSet mTMFCSet = paramVector2.elementAt(b);
/* 1888 */       for (byte b1 = 0; b1 < paramVector1.size(); b1++) {
/* 1889 */         MTMFCSet mTMFCSet1 = paramVector1.elementAt(b1);
/* 1890 */         if (mTMFCSet1.getCapacity() > mTMFCSet.getCapacity()) {
/* 1891 */           FctransSet fctransSet = new FctransSet(mTMFCSet, mTMFCSet1);
/* 1892 */           this.fcTransComboTbl.put(fctransSet.getKey(), fctransSet);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void joinAll(Vector<MTMFCSet> paramVector1, Vector<MTMFCSet> paramVector2) {
/* 1898 */     addDebug("Joining all ");
/* 1899 */     for (byte b = 0; b < paramVector2.size(); b++) {
/* 1900 */       MTMFCSet mTMFCSet = paramVector2.elementAt(b);
/* 1901 */       for (byte b1 = 0; b1 < paramVector1.size(); b1++) {
/* 1902 */         MTMFCSet mTMFCSet1 = paramVector1.elementAt(b1);
/* 1903 */         FctransSet fctransSet = new FctransSet(mTMFCSet, mTMFCSet1);
/* 1904 */         this.fcTransComboTbl.put(fctransSet.getKey(), fctransSet);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   private void joinAllButSelf(Vector<MTMFCSet> paramVector1, Vector<MTMFCSet> paramVector2) {
/* 1909 */     addDebug("Joining all but self ");
/* 1910 */     for (byte b = 0; b < paramVector2.size(); b++) {
/* 1911 */       MTMFCSet mTMFCSet = paramVector2.elementAt(b);
/* 1912 */       for (byte b1 = 0; b1 < paramVector1.size(); b1++) {
/* 1913 */         MTMFCSet mTMFCSet1 = paramVector1.elementAt(b1);
/* 1914 */         if (!mTMFCSet1.fcode.equals(mTMFCSet.fcode)) {
/*      */ 
/*      */           
/* 1917 */           FctransSet fctransSet = new FctransSet(mTMFCSet, mTMFCSet1);
/* 1918 */           this.fcTransComboTbl.put(fctransSet.getKey(), fctransSet);
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
/*      */   private Vector buildMTMFCSet(EntityList paramEntityList, String paramString1, String paramString2, String paramString3, boolean paramBoolean) throws SQLException, MiddlewareException {
/* 1936 */     Vector<MTMFCSet> vector = new Vector();
/* 1937 */     EntityGroup entityGroup = paramEntityList.getParentEntityGroup();
/* 1938 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 1939 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 1940 */       Vector<EntityItem> vector1 = PokUtils.getAllLinkedEntities(entityItem, "PRODSTRUCT", "FEATURE");
/* 1941 */       for (byte b1 = 0; b1 < vector1.size(); b1++) {
/* 1942 */         EntityItem entityItem1 = vector1.elementAt(b1);
/*      */         
/* 1944 */         if (paramBoolean) {
/* 1945 */           String str = PokUtils.getAttributeFlagValue(entityItem1, "FCTRANAPP");
/* 1946 */           addDebug("buildMTMFCSet " + entityItem1.getKey() + " FCTRANAPP " + str);
/* 1947 */           if (!"FCY".equals(str)) {
/* 1948 */             addDebug("buildMTMFCSet: Skipping " + entityItem1.getKey() + " because FCTRANAPP must be Yes");
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/* 1953 */         if (paramString2.length() > 0) {
/* 1954 */           String str = PokUtils.getAttributeValue(entityItem1, "FEATURECODE", "", "", false);
/* 1955 */           if (!str.startsWith(paramString2)) {
/* 1956 */             addDebug("buildMTMFCSet: Skipping " + entityItem1.getKey() + " fc " + str + " doesnt match " + paramString1 + " " + paramString2 + " filter");
/*      */             
/*      */             continue;
/*      */           } 
/*      */         } 
/* 1961 */         MTMFCSet mTMFCSet = null;
/*      */ 
/*      */         
/* 1964 */         if (paramString3.equals("213")) {
/*      */           
/* 1966 */           Vector vector2 = PokUtils.getAllLinkedEntities(entityItem1, "FEATUREMEMORY", "MEMORY");
/* 1967 */           if (vector2.size() > 0) {
/* 1968 */             Vector<EANEntity> vector3 = new Vector();
/* 1969 */             for (byte b2 = 0; b2 < entityItem1.getDownLinkCount(); b2++) {
/* 1970 */               EANEntity eANEntity = entityItem1.getDownLink(b2);
/* 1971 */               if (eANEntity.getEntityType().equals("FEATUREMEMORY")) {
/* 1972 */                 vector3.add(eANEntity);
/*      */               }
/*      */             } 
/* 1975 */             mTMFCSet = new MTMFCMemSet(entityItem, entityItem1, vector3);
/* 1976 */             vector3.clear();
/*      */           } else {
/*      */             
/* 1979 */             addError("MEM_NOTFOUND_ERR", (Object[])null);
/*      */             continue;
/*      */           } 
/*      */         } else {
/* 1983 */           mTMFCSet = new MTMFCSet(entityItem, entityItem1);
/*      */         } 
/*      */         
/* 1986 */         vector.add(mTMFCSet); continue;
/*      */       } 
/* 1988 */       vector1.clear();
/*      */     } 
/*      */ 
/*      */     
/* 1992 */     Collections.sort(vector);
/*      */     
/* 1994 */     return vector;
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
/*      */   private EntityItem[] searchForModels(String paramString1, String paramString2, String paramString3) throws SQLException, MiddlewareException, MiddlewareShutdownInProgressException {
/* 2009 */     addDebug("searchForModels entered machtype " + paramString1 + " modelatr " + paramString2);
/* 2010 */     EntityItem[] arrayOfEntityItem = null;
/* 2011 */     Vector<String> vector1 = new Vector(2);
/* 2012 */     vector1.addElement("MACHTYPEATR");
/* 2013 */     if (paramString2.length() > 0) {
/* 2014 */       vector1.addElement("MODELATR");
/*      */     }
/* 2016 */     Vector<String> vector2 = new Vector(2);
/* 2017 */     vector2.addElement(paramString1);
/* 2018 */     if (paramString2.length() > 0) {
/* 2019 */       vector2.addElement(paramString2);
/*      */     }
/*      */     
/*      */     try {
/* 2023 */       StringBuffer stringBuffer = new StringBuffer();
/* 2024 */       arrayOfEntityItem = ABRUtil.doSearch(getDatabase(), this.m_prof, paramString3, "MODEL", false, vector1, vector2, stringBuffer);
/*      */       
/* 2026 */       if (stringBuffer.length() > 0) {
/* 2027 */         addDebug(stringBuffer.toString());
/*      */       }
/* 2029 */     } catch (SBRException sBRException) {
/*      */       
/* 2031 */       StringWriter stringWriter = new StringWriter();
/* 2032 */       sBRException.printStackTrace(new PrintWriter(stringWriter));
/* 2033 */       addDebug("searchForModels SBRException: " + stringWriter.getBuffer().toString());
/*      */     } 
/* 2035 */     if (arrayOfEntityItem != null && arrayOfEntityItem.length > 0) {
/* 2036 */       for (byte b = 0; b < arrayOfEntityItem.length; b++) {
/* 2037 */         addDebug("searchForModels found " + arrayOfEntityItem[b].getKey());
/*      */       }
/*      */     }
/* 2040 */     vector1.clear();
/* 2041 */     vector2.clear();
/* 2042 */     return arrayOfEntityItem;
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
/*      */   private void updateFCTRANSAvails(EntityItem paramEntityItem, Vector<?> paramVector, LinkActionItem paramLinkActionItem) throws SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, RemoteException, WorkflowException {
/* 2119 */     Hashtable<Object, Object> hashtable = new Hashtable<>();
/* 2120 */     Vector<EANEntity> vector = new Vector();
/* 2121 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*      */ 
/*      */     
/* 2124 */     for (byte b = 0; b < entityGroup.getEntityItemCount(); b++) {
/* 2125 */       EntityItem entityItem = entityGroup.getEntityItem(b);
/* 2126 */       Vector<?> vector1 = PokUtils.getAllLinkedEntities(entityItem, "FEATURETRNAVAIL", "AVAIL");
/* 2127 */       if (vector1.containsAll(paramVector) && paramVector.containsAll(vector1)) {
/* 2128 */         addDebug("updateFCTRANSAvails all avails match for " + entityItem.getKey());
/*      */       } else {
/*      */         byte b1;
/*      */         
/* 2132 */         for (b1 = 0; b1 < paramVector.size(); b1++) {
/* 2133 */           EntityItem entityItem1 = (EntityItem)paramVector.elementAt(b1);
/* 2134 */           if (!vector1.contains(entityItem1)) {
/* 2135 */             addDebug("updateFCTRANSAvails  " + entityItem.getKey() + " is missing " + entityItem1.getKey());
/* 2136 */             Vector<EntityItem> vector2 = (Vector)hashtable.get(entityItem.getKey());
/* 2137 */             if (vector2 == null) {
/* 2138 */               vector2 = new Vector();
/* 2139 */               hashtable.put(entityItem.getKey(), vector2);
/*      */             } 
/* 2141 */             vector2.add(entityItem1);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 2146 */         for (b1 = 0; b1 < vector1.size(); b1++) {
/* 2147 */           EntityItem entityItem1 = (EntityItem)vector1.elementAt(b1);
/* 2148 */           if (!paramVector.contains(entityItem1)) {
/* 2149 */             addDebug("updateFCTRANSAvails  " + entityItem.getKey() + " has extra " + entityItem1.getKey() + " will remove " + entityItem1
/* 2150 */                 .getUpLink(0).getKey());
/* 2151 */             vector.add(entityItem1.getUpLink(0));
/*      */           } 
/*      */         } 
/*      */         
/* 2155 */         vector1.clear();
/*      */       } 
/*      */     } 
/*      */     
/* 2159 */     if (vector.size() > 0) {
/*      */       
/* 2161 */       unlinkAvails(vector);
/* 2162 */       vector.clear();
/*      */     } 
/*      */ 
/*      */     
/* 2166 */     if (hashtable.size() > 0) {
/* 2167 */       linkAvails(hashtable, paramVector, paramLinkActionItem);
/*      */     }
/*      */ 
/*      */     
/* 2171 */     hashtable.clear();
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
/*      */   private String getLD_Value(EntityItem paramEntityItem, String paramString) {
/* 2190 */     return PokUtils.getAttributeDescription(paramEntityItem.getEntityGroup(), paramString, paramString) + ": " + 
/* 2191 */       PokUtils.getAttributeValue(paramEntityItem, paramString, ",", "", false);
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
/*      */   private void unlinkAvails(Vector paramVector) throws MiddlewareRequestException, SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException {
/* 2206 */     long l = System.currentTimeMillis();
/* 2207 */     DeleteActionItem deleteActionItem = new DeleteActionItem(null, this.m_db, this.m_prof, "DELFCTRANSACTIONAVAIL");
/* 2208 */     addDebug("unlinkAvails cnt " + paramVector.size());
/*      */     
/* 2210 */     EntityItem[] arrayOfEntityItem = new EntityItem[paramVector.size()];
/* 2211 */     paramVector.copyInto((Object[])arrayOfEntityItem);
/*      */     
/* 2213 */     deleteActionItem.setEntityItems(arrayOfEntityItem);
/* 2214 */     this.m_db.executeAction(this.m_prof, deleteActionItem);
/*      */     
/* 2216 */     addDebug("Time to unlink existing fctrans from avail: " + Stopwatch.format(System.currentTimeMillis() - l));
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
/*      */   private void linkAvails(Hashtable paramHashtable, Vector paramVector, LinkActionItem paramLinkActionItem) throws SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/* 2237 */     long l = System.currentTimeMillis();
/* 2238 */     EntityGroup entityGroup = this.m_elist.getEntityGroup("FCTRANSACTION");
/*      */ 
/*      */     
/* 2241 */     for (Enumeration<String> enumeration = paramHashtable.keys(); enumeration.hasMoreElements(); ) {
/*      */       
/* 2243 */       String str = enumeration.nextElement();
/* 2244 */       Vector vector = (Vector)paramHashtable.get(str);
/* 2245 */       EntityItem entityItem = entityGroup.getEntityItem(str);
/* 2246 */       EntityItem[] arrayOfEntityItem1 = { entityItem };
/* 2247 */       EntityItem[] arrayOfEntityItem2 = new EntityItem[vector.size()];
/* 2248 */       vector.copyInto((Object[])arrayOfEntityItem2);
/*      */ 
/*      */       
/* 2251 */       paramLinkActionItem.setParentEntityItems(arrayOfEntityItem1);
/* 2252 */       paramLinkActionItem.setChildEntityItems(arrayOfEntityItem2);
/* 2253 */       this.m_db.executeAction(this.m_prof, paramLinkActionItem);
/*      */       
/* 2255 */       StringBuffer stringBuffer = new StringBuffer();
/* 2256 */       for (byte b = 0; b < arrayOfEntityItem2.length; b++) {
/* 2257 */         if (b > 0) {
/* 2258 */           stringBuffer.append(",");
/*      */         }
/* 2260 */         stringBuffer.append(arrayOfEntityItem2[b].getKey());
/*      */       } 
/* 2262 */       addDebug("linkAvails: linked " + entityItem.getKey() + " to " + stringBuffer.toString());
/*      */       
/* 2264 */       vector.clear();
/*      */     } 
/* 2266 */     addDebug("Time to link existing fctrans to avail: " + Stopwatch.format(System.currentTimeMillis() - l));
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
/*      */   private void linkAvails(EntityGroup paramEntityGroup, Vector paramVector, LinkActionItem paramLinkActionItem) throws SQLException, MiddlewareException, LockException, MiddlewareShutdownInProgressException, EANBusinessRuleException, WorkflowException, RemoteException {
/* 2286 */     long l = System.currentTimeMillis();
/* 2287 */     EntityItem[] arrayOfEntityItem1 = paramEntityGroup.getEntityItemsAsArray();
/* 2288 */     EntityItem[] arrayOfEntityItem2 = new EntityItem[paramVector.size()];
/* 2289 */     paramVector.copyInto((Object[])arrayOfEntityItem2);
/*      */ 
/*      */     
/* 2292 */     paramLinkActionItem.setParentEntityItems(arrayOfEntityItem1);
/* 2293 */     paramLinkActionItem.setChildEntityItems(arrayOfEntityItem2);
/* 2294 */     this.m_db.executeAction(this.m_prof, paramLinkActionItem);
/*      */     
/* 2296 */     addDebug("Time to link new fctrans to avail: " + Stopwatch.format(System.currentTimeMillis() - l));
/*      */     
/* 2298 */     StringBuffer stringBuffer1 = new StringBuffer();
/* 2299 */     for (byte b1 = 0; b1 < arrayOfEntityItem1.length; b1++) {
/* 2300 */       if (b1 > 0) {
/* 2301 */         stringBuffer1.append(",");
/*      */       }
/* 2303 */       stringBuffer1.append(arrayOfEntityItem1[b1].getKey());
/*      */     } 
/* 2305 */     StringBuffer stringBuffer2 = new StringBuffer();
/* 2306 */     for (byte b2 = 0; b2 < arrayOfEntityItem2.length; b2++) {
/* 2307 */       if (b2 > 0) {
/* 2308 */         stringBuffer2.append(",");
/*      */       }
/* 2310 */       stringBuffer2.append(arrayOfEntityItem2[b2].getKey());
/*      */     } 
/* 2312 */     addDebug("linkAvails: linked " + stringBuffer1.toString() + " to " + stringBuffer2.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dereference() {
/* 2319 */     super.dereference();
/*      */     
/* 2321 */     this.rsBundle = null;
/* 2322 */     if (this.fcTransComboTbl != null) {
/* 2323 */       for (Enumeration<FctransSet> enumeration = this.fcTransComboTbl.elements(); enumeration.hasMoreElements(); ) {
/* 2324 */         FctransSet fctransSet = enumeration.nextElement();
/* 2325 */         fctransSet.dereference();
/*      */       } 
/*      */       
/* 2328 */       this.fcTransComboTbl.clear();
/* 2329 */       this.fcTransComboTbl = null;
/*      */     } 
/*      */     
/* 2332 */     this.rptSb = null;
/* 2333 */     this.args = null;
/* 2334 */     this.dbgPw = null;
/* 2335 */     this.dbgfn = null;
/*      */     
/* 2337 */     this.metaTbl = null;
/* 2338 */     this.navName = null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getABRVersion() {
/* 2344 */     return "1.6";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDescription() {
/* 2351 */     return "FTSETUPABR";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void addOutput(String paramString) {
/* 2357 */     this.rptSb.append("<p>" + paramString + "</p>" + NEWLINE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(String paramString) {
/* 2364 */     if (this.dbgPw != null) {
/* 2365 */       this.dbgLen += paramString.length();
/* 2366 */       this.dbgPw.println(paramString);
/* 2367 */       this.dbgPw.flush();
/*      */     } else {
/* 2369 */       this.rptSb.append("<!-- " + paramString + " -->" + NEWLINE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addDebug(int paramInt, String paramString) {
/* 2378 */     if (paramInt <= this.abr_debuglvl) {
/* 2379 */       addDebug(paramString);
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
/*      */   private void addError(String paramString, Object[] paramArrayOfObject) {
/* 2411 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/* 2412 */     setReturnCode(-1);
/*      */ 
/*      */     
/* 2415 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("ERROR_PREFIX"));
/* 2416 */     Object[] arrayOfObject = new Object[2];
/* 2417 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 2418 */     arrayOfObject[1] = this.navName;
/*      */     
/* 2420 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
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
/*      */   private void addWarning(String paramString, Object[] paramArrayOfObject) {
/* 2433 */     EntityGroup entityGroup = this.m_elist.getParentEntityGroup();
/*      */ 
/*      */     
/* 2436 */     MessageFormat messageFormat = new MessageFormat(this.rsBundle.getString("WARNING_PREFIX"));
/* 2437 */     Object[] arrayOfObject = new Object[2];
/* 2438 */     arrayOfObject[0] = entityGroup.getLongDescription();
/* 2439 */     arrayOfObject[1] = this.navName;
/*      */     
/* 2441 */     addMessage(messageFormat.format(arrayOfObject), paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMessage(String paramString1, String paramString2, Object[] paramArrayOfObject) {
/* 2450 */     String str = this.rsBundle.getString(paramString2);
/*      */     
/* 2452 */     if (paramArrayOfObject != null) {
/* 2453 */       MessageFormat messageFormat = new MessageFormat(str);
/* 2454 */       str = messageFormat.format(paramArrayOfObject);
/*      */     } 
/*      */     
/* 2457 */     addOutput(paramString1 + " " + str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getNavigationName(EntityItem paramEntityItem) throws SQLException, MiddlewareException {
/* 2467 */     StringBuffer stringBuffer = new StringBuffer();
/*      */ 
/*      */     
/* 2470 */     EANList eANList = (EANList)this.metaTbl.get(paramEntityItem.getEntityType());
/* 2471 */     if (eANList == null) {
/*      */       
/* 2473 */       EntityGroup entityGroup = new EntityGroup(null, this.m_db, this.m_prof, paramEntityItem.getEntityType(), "Navigate");
/* 2474 */       eANList = entityGroup.getMetaAttribute();
/* 2475 */       this.metaTbl.put(paramEntityItem.getEntityType(), eANList);
/*      */     } 
/* 2477 */     for (byte b = 0; b < eANList.size(); b++) {
/*      */       
/* 2479 */       EANMetaAttribute eANMetaAttribute = (EANMetaAttribute)eANList.getAt(b);
/* 2480 */       stringBuffer.append(PokUtils.getAttributeValue(paramEntityItem, eANMetaAttribute.getAttributeCode(), ", ", "", false));
/* 2481 */       if (b + 1 < eANList.size()) {
/* 2482 */         stringBuffer.append(" ");
/*      */       }
/*      */     } 
/*      */     
/* 2486 */     return stringBuffer.toString();
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
/*      */   private class AttrSet
/*      */   {
/* 2511 */     private Vector attrCodeVct = new Vector();
/* 2512 */     private Hashtable attrValTbl = new Hashtable<>();
/*      */     
/*      */     protected void addSingle(EntityItem param1EntityItem, String param1String) {
/* 2515 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 2516 */       if (str == null) {
/* 2517 */         str = "";
/*      */       }
/* 2519 */       this.attrCodeVct.addElement(param1String);
/* 2520 */       this.attrValTbl.put(param1String, str);
/*      */     }
/*      */     
/*      */     protected void addText(EntityItem param1EntityItem, String param1String) {
/* 2524 */       String str = PokUtils.getAttributeValue(param1EntityItem, param1String, "", "", false);
/* 2525 */       this.attrCodeVct.addElement(param1String);
/* 2526 */       this.attrValTbl.put(param1String, str);
/*      */     }
/*      */     protected void addMult(EntityItem param1EntityItem, String param1String) {
/* 2529 */       String str = PokUtils.getAttributeFlagValue(param1EntityItem, param1String);
/* 2530 */       if (str == null) {
/* 2531 */         str = "";
/*      */       }
/* 2533 */       String[] arrayOfString = PokUtils.convertToArray(str);
/* 2534 */       Vector<String> vector = new Vector(arrayOfString.length);
/* 2535 */       for (byte b = 0; b < arrayOfString.length; b++) {
/* 2536 */         vector.addElement(arrayOfString[b]);
/*      */       }
/* 2538 */       this.attrCodeVct.addElement(param1String);
/* 2539 */       this.attrValTbl.put(param1String, vector);
/*      */     }
/*      */     AttrSet(EntityItem param1EntityItem, boolean param1Boolean) {
/* 2542 */       if (param1Boolean) {
/*      */ 
/*      */         
/* 2545 */         this.attrCodeVct.addElement("FTCAT");
/* 2546 */         this.attrValTbl.put("FTCAT", "406");
/*      */         
/* 2548 */         this.attrCodeVct.addElement("FTSUBCAT");
/* 2549 */         this.attrValTbl.put("FTSUBCAT", "010");
/*      */         
/* 2551 */         this.attrCodeVct.addElement("FTCLASS");
/* 2552 */         this.attrValTbl.put("FTCLASS", "GEN");
/*      */         
/* 2554 */         this.attrCodeVct.addElement("FTVACTIVE");
/* 2555 */         this.attrValTbl.put("FTVACTIVE", "Y");
/*      */         
/* 2557 */         this.attrCodeVct.addElement("TRANSACTQTY");
/* 2558 */         this.attrValTbl.put("TRANSACTQTY", "1");
/*      */       } 
/*      */       
/* 2561 */       for (byte b = 0; b < FTSETUPABR.FCTRANSLIST_ATTR.length; b++) {
/* 2562 */         EANMetaAttribute eANMetaAttribute = param1EntityItem.getEntityGroup().getMetaAttribute(FTSETUPABR.FCTRANSLIST_ATTR[b]);
/* 2563 */         if (eANMetaAttribute != null)
/*      */         {
/*      */ 
/*      */           
/* 2567 */           if (eANMetaAttribute.getAttributeType().equals("F")) {
/* 2568 */             addMult(param1EntityItem, FTSETUPABR.FCTRANSLIST_ATTR[b]);
/* 2569 */           } else if (eANMetaAttribute.getAttributeType().equals("U")) {
/* 2570 */             addSingle(param1EntityItem, FTSETUPABR.FCTRANSLIST_ATTR[b]);
/*      */           } else {
/* 2572 */             addText(param1EntityItem, FTSETUPABR.FCTRANSLIST_ATTR[b]);
/*      */           } 
/*      */         }
/*      */       } 
/* 2576 */       FTSETUPABR.this.addDebug((param1Boolean ? "Create" : "Update") + " attrCodeVct " + this.attrCodeVct);
/* 2577 */       FTSETUPABR.this.addDebug((param1Boolean ? "Create" : "Update") + " attrValTbl " + this.attrValTbl);
/*      */     }
/* 2579 */     Vector getAttrCodes() { return this.attrCodeVct; } Hashtable getAttrValues() {
/* 2580 */       return this.attrValTbl;
/*      */     }
/*      */     
/*      */     void dereference() {
/* 2584 */       this.attrCodeVct.clear();
/* 2585 */       this.attrValTbl.clear();
/* 2586 */       this.attrCodeVct = null;
/* 2587 */       this.attrValTbl = null;
/*      */     }
/*      */   }
/*      */   
/*      */   private class MTMFCSet implements Comparable {
/*      */     protected String machtype;
/*      */     protected String model;
/*      */     protected String fcode;
/*      */     protected String mktgname;
/*      */     
/*      */     MTMFCSet(EntityItem param1EntityItem1, EntityItem param1EntityItem2) {
/* 2598 */       this.machtype = PokUtils.getAttributeValue(param1EntityItem1, "MACHTYPEATR", "", "", false);
/* 2599 */       this.model = PokUtils.getAttributeValue(param1EntityItem1, "MODELATR", "", "", false);
/* 2600 */       this.fcode = PokUtils.getAttributeValue(param1EntityItem2, "FEATURECODE", "", "", false);
/* 2601 */       this.mktgname = PokUtils.getAttributeValue(param1EntityItem2, "MKTGNAME", "", "", false);
/* 2602 */       FTSETUPABR.this.addDebug(2, "MTMFCSet " + param1EntityItem1.getKey() + " mt:" + this.machtype + " mdl:" + this.model + " " + param1EntityItem2.getKey() + " fc:" + this.fcode + " mktgname:" + this.mktgname);
/*      */     }
/*      */     
/*      */     String getKey() {
/* 2606 */       return this.machtype + ":" + this.model + ":" + this.fcode;
/*      */     } public String toString() {
/* 2608 */       return this.machtype + ":" + this.model + ":" + this.fcode;
/*      */     } long getCapacity() {
/* 2610 */       return 1L;
/*      */     } void dereference() {
/* 2612 */       this.machtype = null;
/* 2613 */       this.model = null;
/* 2614 */       this.fcode = null;
/* 2615 */       this.mktgname = null;
/*      */     }
/*      */     
/*      */     public int compareTo(Object param1Object) {
/* 2619 */       MTMFCSet mTMFCSet = (MTMFCSet)param1Object;
/* 2620 */       return toString().compareTo(mTMFCSet.toString());
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
/*      */   private class MTMFCMemSet
/*      */     extends MTMFCSet
/*      */   {
/*      */     protected long memory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MTMFCMemSet(EntityItem param1EntityItem1, EntityItem param1EntityItem2, Vector<EANEntity> param1Vector) {
/* 2650 */       super(param1EntityItem1, param1EntityItem2);
/* 2651 */       long l = 0L;
/*      */       
/* 2653 */       for (byte b = 0; b < param1Vector.size(); b++) {
/* 2654 */         EANEntity eANEntity = param1Vector.elementAt(b);
/* 2655 */         EntityItem entityItem = (EntityItem)eANEntity.getDownLink(0);
/* 2656 */         String str1 = PokUtils.getAttributeFlagValue(entityItem, "CAPUNIT");
/* 2657 */         String str2 = PokUtils.getAttributeValue(entityItem, "MEMRYCAP", "", "1", false);
/* 2658 */         long l1 = 1L;
/* 2659 */         if ("C0010".equals(str1)) {
/* 2660 */           l1 = 1000L;
/* 2661 */         } else if ("C0040".equals(str1)) {
/* 2662 */           l1 = 1000000L;
/*      */         } 
/* 2664 */         l += Long.parseLong(str2) * l1;
/* 2665 */         FTSETUPABR.this.addDebug(2, "MTMFCMemSet " + entityItem.getKey() + " capunits:" + str1 + " memcap:" + str2 + " memory:" + l);
/*      */       } 
/* 2667 */       this.memory = l;
/* 2668 */       FTSETUPABR.this.addDebug(2, "MTMFCMemSet memory:" + this.memory);
/*      */     }
/*      */     public String toString() {
/* 2671 */       return this.machtype + ":" + this.model + ":" + this.fcode + ":" + this.memory;
/*      */     } long getCapacity() {
/* 2673 */       return this.memory;
/*      */     } }
/*      */   private class FctransSet { protected FTSETUPABR.MTMFCSet fromSet; protected FTSETUPABR.MTMFCSet toSet;
/*      */     
/*      */     FctransSet(FTSETUPABR.MTMFCSet param1MTMFCSet1, FTSETUPABR.MTMFCSet param1MTMFCSet2) {
/* 2678 */       this.fromSet = param1MTMFCSet1;
/* 2679 */       this.toSet = param1MTMFCSet2;
/*      */     }
/*      */     
/*      */     String get(String param1String) {
/* 2683 */       if ("FROMMACHTYPE".equals(param1String)) {
/* 2684 */         return this.fromSet.machtype;
/*      */       }
/* 2686 */       if ("FROMMODEL".equals(param1String)) {
/* 2687 */         return this.fromSet.model;
/*      */       }
/* 2689 */       if ("FROMFEATURECODE".equals(param1String)) {
/* 2690 */         return this.fromSet.fcode;
/*      */       }
/* 2692 */       if ("TOMACHTYPE".equals(param1String)) {
/* 2693 */         return this.toSet.machtype;
/*      */       }
/* 2695 */       if ("TOMODEL".equals(param1String)) {
/* 2696 */         return this.toSet.model;
/*      */       }
/* 2698 */       if ("TOFEATURECODE".equals(param1String)) {
/* 2699 */         return this.toSet.fcode;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2705 */       if ("COMMENT".equals(param1String)) {
/* 2706 */         return this.fromSet.mktgname + " To " + this.toSet.mktgname;
/*      */       }
/* 2708 */       return null;
/*      */     }
/*      */     
/*      */     public String toString() {
/* 2712 */       return this.fromSet.toString() + ":" + this.toSet.toString();
/*      */     } String getKey() {
/* 2714 */       return this.fromSet.getKey() + ":" + this.toSet.getKey();
/*      */     } void dereference() {
/* 2716 */       this.fromSet.dereference();
/* 2717 */       this.toSet.dereference();
/*      */     } }
/*      */ 
/*      */ }


/* Location:              C:\Users\06490K744\Documents\fromServer\deployments\codeSync2\abr.jar!\COM\ibm\eannounce\abr\sg\bh\FTSETUPABR.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */